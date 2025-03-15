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
        this("jdbc:mysql://project_07-user_database-1:3306", "root", "password");
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
        // Try with resources making a connection to the MySql database
        // If not, close the database connection
        try (
            // Append Database /Users to the end of the url
            Connection dbConn = DriverManager.getConnection(
                url + "/Users", dbUser, dbPass
            );
        ) {
            // Here we create the user
            // Check if the user already exists
            PreparedStatement stmt = dbConn.prepareStatement(
                "SELECT * FROM Login WHERE User_name = ?"
            );
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false;
            }

            // If the user does not exist, create the user
            stmt = dbConn.prepareStatement(
                "INSERT INTO Login (User_name, User_password) VALUES (?, ?)"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println(
                "Could not establish connection to MySQL database."
            );
            return false;
        } 
    }

    /**
     * Method to log in a user into the database.
     * 
     * @param username - username of the User
     * @param password - password of the User
     * @return - true if logged in, false otherwise
     */
    public boolean loginUser(String username, String password) {
        // Try with resources making a connection to the MySql database
        // If not, close the database connection
        try (
            // Append Database /Users to the end of the url
            Connection dbConn = DriverManager.getConnection(
                url + "/Users", dbUser, dbPass
            );
        ) {
            // Here we log in the user
            // Check if the user exists and the password is correct
            PreparedStatement stmt = dbConn.prepareStatement(
                "SELECT * FROM Login WHERE User_name = ? AND User_password = ?"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException e) {
            System.out.println(
                "Could not establish connection to MySQL database."
            );
            e.printStackTrace();
            return false;
        }

        return false;
    }

    /**
     * Creates a user profile in the database. User cannot have more than four profiles.
     * 
     * @param username - username of the User
     * @param profile_name - name of the profile
     * @return - true if created profile, false otherwise
     */
    public boolean createProfile(String username, String profile_name) {
        // Connect to database
        // Check if user already has 4 profiles
        // Check if profile name already exists for that user
        // Create profile
        // Check for SQL exceptions

        return false;
    }

    /**
     * Edits a user profile's name in the database.
     * 
     * @param username - username of the User
     * @param profile_name - name of the profile
     * @param new_profile_name - new name of the profile
     * @return - true if edited profile, false otherwise
     */
    public boolean editProfile(String username, String profile_name, String new_profile_name) {
        // Connect to database
        // Check if profile name exists for that user
        // Check if new profile name already exists for that user
        // Edit profile
        // Check for SQL exceptions

        return false;
    }

    /**
     * Deletes a user profile in the database. Cascades to delete all data associated with the profile.
     * 
     * @param username - username of the User
     * @param profile_name - name of the profile
     * @return - true if deleted profile, false otherwise
     */
    public boolean deleteProfile(String username, String profile_name) {
        // Connect to database
        // Check if profile name exists for that user
        // Delete profile
        // Check for SQL exceptions

        return false;
    }
}
