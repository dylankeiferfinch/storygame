package readers.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import readers.Reader;
import util.Dfl;

public class ReaderTest
{
	@Before
	public void setUp() throws Exception
	{
		
	}

	@Test
	public void testReader()
	{
		Reader r = new Reader("test");
		File f = new File("");
		assertEquals(f.getAbsolutePath() + "\\src\\resources\\test.resource", r.getFile().getAbsolutePath());
		assertEquals("C:\\Users\\dkfin\\Desktop\\Eclipse Workspace\\Story Game REVIVAL - Ver 3\\src\\resources\\test.resource", r.getFile().getAbsolutePath());
		r.gotoStart();
		assertEquals("# 1", r.next().getRaw());
		assertEquals("line1", r.nextInTag().getRaw());
		assertNull(r.nextInTag());
	}
}
