package ru.pstu.itas.lucene;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.apache.poi.extractor.ExtractorFactory;

final class MSDocsIndexer {
	private final Path filePath;

	MSDocsIndexer(Path filePath) {
		this.filePath = filePath;
	}

	IndexItem index() throws IOException {
		String content = "";
		try {
			content = ExtractorFactory.createExtractor(Files.newInputStream(filePath)).getText();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
		}

		return new IndexItem((long) filePath.hashCode(), filePath.getFileName().toString(), content);
	}
}
