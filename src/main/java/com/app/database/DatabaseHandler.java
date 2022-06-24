package com.app.database;

import com.app.Configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler extends Configs {
    public static Connection getDbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName,
                dbUser, dbPassword);
    }

}
