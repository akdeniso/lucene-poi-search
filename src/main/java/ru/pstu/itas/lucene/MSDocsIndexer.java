package ru.pstu.itas.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;

final class MSDocsIndexer {
	private final Path filePath;

	MSDocsIndexer(Path filePath) {
		this.filePath = filePath;
	}

	IndexItem index() throws IOException {
		String content = "";

		String copyPathString = System.getProperty("java.io.tmpdir") + File.separator
				+ filePath.getFileName().toString();
		Path copyPath = Paths.get(copyPathString);
		Files.deleteIfExists(copyPath);
		Files.copy(filePath, copyPath);

		try {
			content = ExtractorFactory.createExtractor(new File(copyPathString)).getText();
			Files.deleteIfExists(copyPath);
		} catch (OpenXML4JException | XmlException e) {
			Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
		}
		return new IndexItem((long) filePath.hashCode(), filePath.getFileName().toString(), content);
	}
}
