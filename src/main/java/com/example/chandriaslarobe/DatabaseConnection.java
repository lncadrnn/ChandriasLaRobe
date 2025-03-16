package com.example.chandriaslarobe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// change the information if different
public class DatabaseConnection {
    private static final String DATABASE_NAME = "demo_db";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "1234";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useSSL=false";

    private static Connection databaseLink;

    public static Connection getConnection() {
        try {
            if (databaseLink == null || databaseLink.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                databaseLink = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                System.out.println("Database connection successful!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found! Make sure you have added the MySQL JDBC Driver.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed! Check your credentials and database status.");
            e.printStackTrace();
        }
        return databaseLink;
    }

    public static void closeConnection() {
        try {
            if (databaseLink != null && !databaseLink.isClosed()) {
                databaseLink.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close database connection.");
            e.printStackTrace();
        }
    }
}
