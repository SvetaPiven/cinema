package repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseRepository {
    private String jdbcDriver;
    private String jdbcURL;
    private String username;
    private String password;
    private String dbName;

    public BaseRepository(String propertiesFileName) {
        loadProperties(propertiesFileName);
    }

    private void loadProperties(String propertiesFileName) {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (input == null) {
                throw new RuntimeException("Properties file not found: " + propertiesFileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file: " + propertiesFileName, e);
        }
        jdbcURL = properties.getProperty("database.url");
        username = properties.getProperty("database.login");
        password = properties.getProperty("database.password");
        jdbcDriver = properties.getProperty("driver.name");
        dbName = properties.getProperty("database.name");
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public String getDatabaseUrl() {
        return jdbcURL;
    }

    public String getDatabaseUser() {
        return username;
    }

    public String getDatabasePassword() {
        return password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDatabaseUrl(String jdbcURL) {
        this.jdbcURL = jdbcURL;
    }
}
