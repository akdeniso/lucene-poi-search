package ru.pstu.itas.lucene;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.poi.extractor.ExtractorFactory;

final class MSDocsIndexer {
	private final File docFile;

	MSDocsIndexer(File docFile) {
		this.docFile = docFile;
	}

	IndexItem index() throws IOException {
		String content = "";
		try {
			content = ExtractorFactory.createExtractor(docFile).getText();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
		}

		return new IndexItem((long) docFile.hashCode(), docFile.getName(),
				content);
	}
}
