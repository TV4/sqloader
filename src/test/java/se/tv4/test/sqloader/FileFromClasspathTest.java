package se.tv4.test.sqloader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for Sqloader.
 */
public class FileFromClasspathTest {

    private Sqloader loader;
    
    @Before
    public void setUp(){
	loader = new Sqloader(this.getClass());
    }
    
    @Test
    public void createsCorrectObjectCreatePath() {
	assertEquals("se/tv4/test/sqloader/FileFromClasspathTest_create.sql", loader.findPathToCreateSql());
    }
    @Test
    public void createsCorrectObjectDropPath() {
	assertEquals("se/tv4/test/sqloader/FileFromClasspathTest_drop.sql", loader.findPathToDropSql());
    }
    @Test
    public void readsFileFromClassPath() throws Exception{
	BufferedReader reader = loader.findFileInClasspath(loader.findPathToCreateSql());
	assertNotNull("This shoud not be null", reader);
	assertEquals("CREATE TABLE \"TEST_SCHEMA\".\"TEST_TABLE\" (ID decimal(10) NOT NULL)", reader.readLine());
    }
}
