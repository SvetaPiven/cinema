package org.example.util;

import org.example.repository.BaseRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseConnection {

    private BaseConnection(){}

    public static void registerDriver() {
        try {
            Class.forName(BaseRepository.jdbcDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        String jdbcURL = BaseRepository.getDatabaseUrl();
        try {
            return DriverManager.getConnection(jdbcURL,
                    BaseRepository.getDatabaseUser(),
                    BaseRepository.getDatabasePassword());

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
