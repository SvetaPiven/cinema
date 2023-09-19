package org.example.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "org.example")
@EnableWebMvc
@EnableJpaRepositories(basePackages = "org.example.repository")
public class JpaConfig {

    @Bean
    public DataSource hikariDatasource(ApplicationProperties properties) {
        HikariDataSource hikariDataSource = new HikariDataSource();

        hikariDataSource.setUsername(properties.getDatabaseLogin());
        hikariDataSource.setPassword(properties.getDatabasePassword());
        hikariDataSource.setDriverClassName(properties.getDriverName());
        hikariDataSource.setMaximumPoolSize(properties.getPoolSize());
        hikariDataSource.setJdbcUrl(properties.getDatabaseUrl());

        return hikariDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("org.example.entity");

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.show_sql", "true");
        jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "none");
       // jpaProperties.setProperty("javax.persistence.transactionType", "JTA");
      //  jpaProperties.setProperty("hibernate.transaction.jta.platform", "org.hibernate.service.jta.platform.internal.JBossStandAloneJtaPlatform");

        entityManagerFactory.setJpaProperties(jpaProperties);
        return entityManagerFactory;
    }
//@Bean
//public DataSource dataSource() {
//    DriverManagerDataSource dataSource = new DriverManagerDataSource();
//    dataSource.setDriverClassName("org.postgresql.Driver");
//    dataSource.setUrl("jdbc:postgresql://localhost:5432/cinema");
//    dataSource.setUsername("development");
//    dataSource.setPassword("dev");
//    return dataSource;
//}
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setPackagesToScan("org.example.entity");
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        em.setJpaProperties(jpaProperties());
//        return em;
//    }
//
//    private Properties jpaProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "none");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        properties.setProperty("hibernate.show_sql", "true");
//        return properties;
//    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
