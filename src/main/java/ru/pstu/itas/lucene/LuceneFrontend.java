package ru.pstu.itas.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;

public final class LuceneFrontend {
	private static final int DEFAULT_RESULT_SIZE = 100;

	private final File indexDir;

	public LuceneFrontend(File indexDir) {
		this.indexDir = indexDir;
	}

	public Set<IdentName> search(String contentPart, int resultSize) throws IOException, ParseException {
		Set<IdentName> result = new HashSet<IdentName>();
		Searcher search = new Searcher(indexDir);
		for (IndexItem item : search.findByContent(contentPart, resultSize))
			result.add(new IdentName(item.getTitle()));
		search.close();
		return result;
	}

	public Set<IdentName> search(String contentPart) throws IOException, ParseException {
		return search(contentPart, DEFAULT_RESULT_SIZE);
	}

	public void index(Path filePath) throws IOException {
		Indexer index = new Indexer(indexDir);
		index.indexFile(filePath);
		index.close();
	}

	public void removeFromIndex(String fileName) throws CorruptIndexException, IOException {
		Indexer index = new Indexer(indexDir);
		index.removeFromIndex(fileName);
		index.close();
	}
}
