package com.example.turistguidewebfrontend.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection connection;

    public ConnectionManager() {}

    public static Connection getConnection(String databaseURL, String username, String password) {
        if (connection != null){
            return connection;

        } else {
            try {
                connection = DriverManager.getConnection(databaseURL, username, password);

            }catch (SQLException sqlException){
                System.out.println("Kan ikke oprette forbindelse til database");
                sqlException.printStackTrace();
            }
        }
        return connection;
    }
}
