package se.tv4.test.sqloader;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests Sqloader.
 */
public class SqloaderTest {

    private Sqloader objectLoader;
    
    @Before
    public void setUp(){
	objectLoader = new Sqloader(this.getClass());
    }
    @Test
    public void createsCorrectObjectCreatePath() {
	assertEquals("se/tv4/test/sqloader/SqloaderTest_create.sql", objectLoader.findPathToCreateSql());
    }
    @Test
    public void createsCorrectObjectDropPath() {
	assertEquals("se/tv4/test/sqloader/SqloaderTest_drop.sql", objectLoader.findPathToDropSql());
    }
    @Test
    public void readsFileFromClassPath() throws Exception{
	BufferedReader reader = objectLoader.findFileInClasspath(objectLoader.findPathToCreateSql());
	assertNotNull("This shoud not be null", reader);
	assertEquals("CREATE TABLE \"TEST_SCHEMA\".\"TEST_TABLE\" (ID decimal(10) NOT NULL))", reader.readLine());
    }
}
