package com.example.projecttaskmanagement.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import javax.sql.DataSource;

public class DBConnectionProvider {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/project_task_management?useUnicode=true&amp;characterEncoding=UTF-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @Getter
    private static final DataSource dataSource;

    static {
        try {
            // Загрузка драйвера MariaDB
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MariaDB driver", e);
        }

        // Создание и настройка HikariConfig
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);

        // Создание и инициализация HikariDataSource
        dataSource = new HikariDataSource(config);
    }


}
