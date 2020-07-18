package ru.market.domain.config;

import org.hibernate.cfg.Environment;
import org.hibernate.jpa.HibernatePersistenceProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = {"ru.market.domain.repository"})
@EnableTransactionManagement
public class DataConfiguration {
    @Value("${app.market.database.driver.class}")
    private String driverClassName;
    @Value("${app.market.database.url}")
    private String url;
    @Value("${app.market.database.username}")
    private String username;
    @Value("${app.market.database.password}")
    private String password;

    private final String[] packagesToScan = new String[]{"ru.market.domain.data"};

    @Value("${app.market.database.hibernate.dialect}")
    private String dialect;
    @Value("${app.market.database.hibernate.showSql}")
    private String showSql;
    @Value("${app.market.database.hibernate.hbm2dllAuto}")
    private String hbm2ddlAuto;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(packagesToScan);
        entityManagerFactoryBean.setJpaProperties(getJpaProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties getJpaProperties(){
        Properties jpa = new Properties();
        jpa.put(Environment.DIALECT, dialect);
        jpa.put(Environment.SHOW_SQL, showSql);
        jpa.put(Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        return jpa;
    }
}
