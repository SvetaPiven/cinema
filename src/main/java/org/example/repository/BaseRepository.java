package org.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseRepository {

    protected static final String POSTGRES_DRIVER_NAME = "org.postgresql.Driver";

    protected static final String DATABASE_URL = "jdbc:postgresql://localhost:";

    protected static final int DATABASE_PORT = 5432;

    protected static final String DATABASE_NAME = "/cinema";

    protected static final String DATABASE_LOGIN = "development";

    protected static final String DATABASE_PASSWORD = "dev";

    protected Connection getConnection() {
        String jdbcURL = DATABASE_URL + DATABASE_PORT + DATABASE_NAME;
        try {
            return DriverManager.getConnection(jdbcURL, DATABASE_LOGIN, DATABASE_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void registerDriver() {
        try {
            Class.forName(POSTGRES_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }
    }
}
