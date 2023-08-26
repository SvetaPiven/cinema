package org.example.util;

import org.example.repository.BaseRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseConnection {

    private BaseRepository baseRepository;

    public BaseConnection(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }


    public void registerDriver() {
        try {
            Class.forName(baseRepository.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        String jdbcURL = baseRepository.getDatabaseUrl();
        try {
            return DriverManager.getConnection(jdbcURL,
                    baseRepository.getDatabaseUser(),
                    baseRepository.getDatabasePassword());

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
