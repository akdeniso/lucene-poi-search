package ru.pstu.itas.lucene;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LuceneTest {
	private static final String IDENT = "qwerty1";
	private static final String NAME = "Test.docx";
	private static final String FULL_NAME = IDENT + IdentName.IDENT_NAME_SEPARATOR + NAME;

	private LuceneFrontend lucene;
	private File indexDir;
	private Path documentFilePath;

	@Before
	public void init() throws IOException, URISyntaxException {
		indexDir = new File(System.getProperty("java.io.tmpdir") + File.separator + "lucene");
		lucene = new LuceneFrontend(indexDir);
		documentFilePath = Paths.get("src/test/resources/" + FULL_NAME);
		lucene.index(documentFilePath);
	}

	@After
	public void clear() throws IOException {
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
	public void removeFromIndex() throws CorruptIndexException, IOException, ParseException {
		lucene.removeFromIndex(documentFilePath.getFileName().toString());
		assertTrue(lucene.search("Winnie-the-Pooh").isEmpty());
	}
}
