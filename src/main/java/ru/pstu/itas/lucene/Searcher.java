package ru.pstu.itas.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

final class Searcher {
	private final IndexSearcher searcher;
	private final QueryParser contentQueryParser;

	Searcher(File indexDir) throws IOException {
		searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(indexDir)));
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		contentQueryParser = new QueryParser(Version.LUCENE_36, IndexItem.CONTENT, analyzer);
	}

	List<IndexItem> findByContent(String queryString, int numOfResults) throws ParseException, IOException {
		Query query = contentQueryParser.parse(queryString);
		ScoreDoc[] queryResults = searcher.search(query, numOfResults).scoreDocs;
		List<IndexItem> results = new ArrayList<>();
		for (ScoreDoc scoreDoc : queryResults) {
			Document doc = searcher.doc(scoreDoc.doc);
			results.add(new IndexItem(Long.parseLong(doc.get(IndexItem.ID)), doc.get(IndexItem.TITLE),
					doc.get(IndexItem.CONTENT)));
		}

		return results;
	}

	void close() throws IOException {
		searcher.close();
	}
}
