package se.tv4.test.sqloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Sqloader {

    @SuppressWarnings("rawtypes")
    private Class klazz;
    private String jdbcDriver;
    private String connectionUrl;

    @SuppressWarnings("rawtypes")
    public Sqloader(Class klass) {
        this.klazz = klass;
    }

    public String baseName() {
        return klazz.getPackage().getName().replace('.', File.separatorChar) + File.separatorChar + klazz.getSimpleName();
    }

    public String findPathToCreateSql() {
        return baseName() + "_create.sql";
    }

    public String findPathToDropSql() {
        return baseName() + "_drop.sql";
    }

    BufferedReader findFileInClasspath(String fileName) {
        return new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName)));
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public void create() throws Exception {
        BufferedReader reader = findFileInClasspath(findPathToCreateSql());
        executeScript(reader);
    }

    public void drop() throws Exception {
        BufferedReader reader = findFileInClasspath(findPathToDropSql());
        executeScript(reader);
    }

    void executeScript(BufferedReader reader) throws ClassNotFoundException, SQLException, IOException {
        Class.forName(getJdbcDriver());
        Connection connection = DriverManager.getConnection(getConnectionUrl());
        while (reader.ready()) {
            String sqlStatement = reader.readLine();
            if (!sqlStatement.matches("\\s*$") && !sqlStatement.matches("--.*")) {
                connection.createStatement().execute(sqlStatement);
            }
        }
        connection.commit();
        connection.close();
    }
}
