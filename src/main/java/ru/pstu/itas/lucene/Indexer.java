package ru.pstu.itas.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

final class Indexer {
	private final IndexWriter writer;

	Indexer(File indexDir) throws IOException {
		writer = new IndexWriter(FSDirectory.open(indexDir),
				new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36)));
	}

	void indexFile(Path filePath) throws IOException {
		index(new MSDocsIndexer(filePath).index());
	}

	void removeFromIndex(String fileName) throws CorruptIndexException, IOException {
		writer.deleteDocuments(new Term(IndexItem.TITLE, fileName));
		writer.commit();
	}

	void close() throws IOException {
		writer.close();
	}

	private void index(IndexItem indexItem) throws IOException {
		writer.deleteDocuments(new Term(IndexItem.ID, indexItem.getId().toString()));

		Document doc = new Document();
		doc.add(new Field(IndexItem.ID, indexItem.getId().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(IndexItem.TITLE, indexItem.getTitle(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(IndexItem.CONTENT, indexItem.getContent(), Field.Store.YES, Field.Index.ANALYZED));
		writer.addDocument(doc);
		writer.commit();
	}
}
