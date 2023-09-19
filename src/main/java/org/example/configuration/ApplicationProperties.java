package org.example.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

    @Value("${driver.name}")
    private String driverName;

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${database.login}")
    private String databaseLogin;

    @Value("${database.password}")
    private String databasePassword;

    @Value("${POOL_SIZE}")
    private Integer poolSize;

    public ApplicationProperties() {

    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabaseLogin() {
        return databaseLogin;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void setDatabaseLogin(String databaseLogin) {
        this.databaseLogin = databaseLogin;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }
}
