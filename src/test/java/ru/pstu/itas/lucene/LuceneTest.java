package ru.pstu.itas.lucene;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LuceneTest {
	private static final String IDENT = "qwerty1";
	private static final String NAME = "Test.docx";
	private static final String FULL_NAME = IDENT
			+ IdentName.IDENT_NAME_SEPARATOR + NAME;

	private LuceneFrontend lucene;
	private File indexDir;
	private File documentFile;

	@Before
	public void init() throws IOException {
		indexDir = new File(System.getProperty("java.io.tmpdir")
				+ File.pathSeparator + "lucene");
		lucene = new LuceneFrontend(indexDir);
		documentFile = new File("src/test/resources/" + FULL_NAME);
		lucene.index(documentFile);
	}

	@After
	public void clear() throws IOException {
		lucene.close();
		indexDir.delete();
	}

	@Test
	public void goodSearch() throws IOException, ParseException {
		Set<IdentName> searchResult = lucene.search("Winnie-the-Pooh");
		assertTrue(searchResult.size() == 1);

		IdentName doc = searchResult.iterator().next();
		assertEquals(doc.ident, IDENT);
		assertEquals(doc.name, NAME);
	}

	@Test
	public void badSearch() throws IOException, ParseException {
		assertTrue(lucene.search("Franz Kafka").isEmpty());
	}

	@Test
	public void removeFromIndex() throws CorruptIndexException, IOException,
			ParseException {
		lucene.removeFromIndex(documentFile.getName());
		assertTrue(lucene.search("Winnie-the-Pooh").isEmpty());
	}
}
