package com.example.projecttaskmanagement.db;

import org.mariadb.jdbc.Connection;

import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/project_task_management?useUnicode=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    public static  Connection connectionDB(){
        Connection connection = null;
        try{
          Class.forName("org.mariadb.jdbc.Driver");
          connection = (Connection)DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
            return connection;
    }
}
