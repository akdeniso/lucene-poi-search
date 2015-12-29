package ru.pstu.itas.lucene;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;

public final class LuceneFrontend {
	private static final int DEFAULT_RESULT_SIZE = 100;

	private final File indexDir;

	private Indexer indexer;
	private Searcher searcher;

	public LuceneFrontend(File indexDir) {
		this.indexDir = indexDir;
	}

	public Set<IdentName> search(String contentPart, int resultSize)
			throws IOException, ParseException {
		Set<IdentName> result = new HashSet<IdentName>();
		for (IndexItem item : searcher().findByContent(contentPart, resultSize))
			result.add(new IdentName(item.getTitle()));
		return result;
	}

	public Set<IdentName> search(String contentPart) throws IOException,
			ParseException {
		return search(contentPart, DEFAULT_RESULT_SIZE);
	}

	public void index(File file) throws IOException {
		indexer().indexFile(file);
	}

	public void removeFromIndex(String fileName) throws CorruptIndexException,
			IOException {
		indexer().removeFromIndex(fileName);
	}

	public void close() throws IOException {
		if (indexer != null)
			indexer.close();
		if (searcher != null)
			searcher.close();
	}

	private Searcher searcher() throws IOException {
		if (searcher == null)
			searcher = new Searcher(indexDir);
		return searcher;
	}

	private Indexer indexer() throws IOException {
		if (indexer == null)
			indexer = new Indexer(indexDir);
		return indexer;
	}
}
