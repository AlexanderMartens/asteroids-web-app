package com.pluto.database;

import java.sql.*;

/**
 * This client class contains static methods to interact with the MySQL 
 * database to fetch and create user data.
 */
public class DatabaseClient {
    private String url;
    private String dbPass;
    private String dbUser;
    

    public DatabaseClient(String url, String dbUser, String dbPass) {
        this.url = url;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

    /**
     * Default constructor for the DatabaseClient.
     */
    public DatabaseClient() {
        this("jdbc:mysql://localhost:53346", "root", "password");
    }
    
    public boolean createUser(String user, String password) {
        try (
            // Append Database /Users to the end of the url
            Connection dbConn = DriverManager.getConnection(
                url + "/Users", dbUser, dbPass
            );
        ) {
            
        } catch (SQLException e) {
            System.out.println(
                "Could not establish connection to MySQL database."
            );
            return false;
        } 

        return false;
    }
}
