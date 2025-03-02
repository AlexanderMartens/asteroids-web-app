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
    
    /**
     * Constructor for the DatabaseClient. Note that url is meant to be a 
     * jdbc connection to a MySql database. An example url would be
     * "jdbc:mysql://localhost:53346" which is the default.
     * 
     * @param url - url to use for the JDBC connection
     * @param dbUser - username to the database
     * @param dbPass - password to the database
     */
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

    /**
     * Method to create a connection to the database. The connection must be 
     * closed afterwords.
     *
     * @param database - string with the name to the desired database
     * @return - a Connection object to the desired database
     */
    private Connection getConnection(String database) {
        // Try with resources making a connection to the MySql database
        try (
            // Append Database /Users to the end of the url
            Connection dbConn = DriverManager.getConnection(
                url + "/" + database, dbUser, dbPass
            );
        ) {
            // Successful connection. Return the Connection object
            return dbConn;
        } catch (SQLException e) {
            System.out.println(
                "Could not establish connection to MySQL database."
            );
            return null;
        } 
    }
        
     
    
    /**
     * Method to create a user into the database.
     *
     * @param username - username of the new User
     * @param password - password of the new User
     * @return - true if created user, false otherwise
     * 
     */
    public boolean createUser(String username, String password) {
        // Get connection to database Users
        // Need to free dbConn
        Connection dbConn = getConnection("Users");
        if (dbConn == null) {
            return false;
        }
        // code to create user here

        return false;
    }

    /**
     * Method to login a user into the database.
     *
     * @param username - username of the User
     * @param password - password of the User
     * @return - true if user in database, false otherwise
     */
    public boolean userLogin(String username, String password) {
        // Get connection to database Users
        // need to free dbConn
        Connection dbConn = getConnection("Users");
        if (dbConn == null) {
            return false;
        }
        // code to login user here

        return false;
    }
}
