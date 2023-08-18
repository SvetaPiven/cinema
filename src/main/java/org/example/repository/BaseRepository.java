package org.example.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseRepository {

    public static String jdbcDriver;

    private static String jdbcURL;

    private static String username;

    private static String password;

    public BaseRepository() {
        Properties properties = loadProperties();
        jdbcURL = properties.getProperty("database.url");
        username = properties.getProperty("database.login");
        password = properties.getProperty("database.password");
        jdbcDriver = properties.getProperty("driver.name");
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Properties file not found: application.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file: application.properties", e);
        }
        return properties;
    }

    public String getDriver() {
        return jdbcDriver;
    }

    public static String getDatabaseUrl() {
        return jdbcURL;
    }

    public static String getDatabaseUser() {
        return username;
    }

    public static String getDatabasePassword() {
        return password;
    }
}
