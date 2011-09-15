package se.tv4.test.sqloader;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests Sqloader.
 */
public class CreateDropTest {

    private Sqloader loader;
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String CONNECTION_URL = "jdbc:derby:memory:sqlodader-testdb;create=true";;

    @Before
    public void setUp() {
        loader = new Sqloader(this.getClass());
        loader.setJdbcDriver(JDBC_DRIVER);
        loader.setConnectionUrl(CONNECTION_URL);
    }

    @After
    public void tearDown() throws SQLException {
        Connection connection = DriverManager.getConnection(CONNECTION_URL);
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet rs = databaseMetaData.getTables(null, "TEST_SCHEMA", null, null);
        while (rs.next()) {
            connection.createStatement().execute("DROP TABLE TEST_SCHEMA." + rs.getString("TABLE_NAME"));
        }
        connection.close();
    }

    @Test
    public void insertsDataToDatabase() throws Exception {
        loader.create();

        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(CONNECTION_URL);
        float id = getIdFromTable(connection);
        assertEquals(1.0, id, 0.0);
        connection.close();
    }

    @Test
    public void canHandleBlankLinesInSql() throws Exception {
        loader.create();
    }

    @Test
    public void canDropTable() throws Exception {
        loader.create();
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(CONNECTION_URL);
        float id = getIdFromTable(connection);

        assertEquals(1.0, id, 0.0);

        loader.drop();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet databaseRs = databaseMetaData.getTables(null, "TEST_SCHEMA", null, null);
        int numberOfTables = 0;
        while (databaseRs.next()) {
            numberOfTables++;
        }

        assertEquals(0, numberOfTables);
        connection.close();
    }

    float getIdFromTable(Connection connection) throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("select id from TEST_SCHEMA.TEST_TABLE");
        float id = 0;
        while (rs.next()) {
            id = rs.getFloat("id");
        }
        return id;
    }
}
