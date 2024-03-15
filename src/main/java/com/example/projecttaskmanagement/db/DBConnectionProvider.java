package com.example.projecttaskmanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_task_management?uUnicode=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;

    private DBConnectionProvider() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            createConnection();
        } else {
            try {
                if (connection.isClosed()) {
                    createConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                createConnection();
            }
        }
        return connection;
    }

    private static void createConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
