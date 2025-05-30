package com.example.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    //	HikariCP DataSource
    @Bean
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver"); // 또는 oracle.jdbc.driver.OracleDriver
        dataSource.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:XE");
        dataSource.setUsername("system");
        dataSource.setPassword("1234");
        return dataSource;
    }
}
