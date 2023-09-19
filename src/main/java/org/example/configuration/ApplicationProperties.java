package org.example.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Objects;

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

    public ApplicationProperties(String driverName, String databaseUrl, String databaseLogin, String databasePassword, Integer poolSize) {
        this.driverName = driverName;
        this.databaseUrl = databaseUrl;
        this.databaseLogin = databaseLogin;
        this.databasePassword = databasePassword;
        this.poolSize = poolSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationProperties that = (ApplicationProperties) o;
        return Objects.equals(driverName, that.driverName) && Objects.equals(databaseUrl, that.databaseUrl) && Objects.equals(databaseLogin, that.databaseLogin) && Objects.equals(databasePassword, that.databasePassword) && Objects.equals(poolSize, that.poolSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverName, databaseUrl, databaseLogin, databasePassword, poolSize);
    }

    @Override
    public String toString() {
        return "ApplicationProperties{" +
                "driverName='" + driverName + '\'' +
                ", databaseUrl='" + databaseUrl + '\'' +
                ", databaseLogin='" + databaseLogin + '\'' +
                ", databasePassword='" + databasePassword + '\'' +
                ", poolSize=" + poolSize +
                '}';
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
}
