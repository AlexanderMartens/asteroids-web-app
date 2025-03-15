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
     * Uploads a score to the database.
     * 
     * @param profile_id - id of the profile
     * @param score - score to upload
     * @param level - level reached
     * @param duration - duration of the game
     * @return - true if uploaded score, false otherwise
     */
    public boolean uploadScore(int profile_id, int score, int level, int duration) {
        try (
            Connection dbConn = DriverManager.getConnection(
                url + "/Users", dbUser, dbPass
            );
        ) {
            PreparedStatement stmt = dbConn.prepareStatement(
                "INSERT INTO Scores (Profile_id, Score, Level_reached, Duration_seconds) VALUES (?, ?, ?, ?)"
            );
            stmt.setInt(1, profile_id);
            stmt.setInt(2, score);
            stmt.setInt(3, level);
            stmt.setInt(4, duration);
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetches the top n scores from the database. 
     * Score must be one of the following: "Score", "Level_reached", "Duration_seconds".
     * Caller must close the ResultSet and Statement.
     * 
     * @param n - number of scores to fetch
     * @param score - which score to fetch
     * @return - a ResultSet of the top n scores or null if score is invalid or an error occurred
     */
    public ResultSet fetchTopScores(int n, String score) {
        if (!score.equals("Score") && !score.equals("Level_reached") && !score.equals("Duration_seconds")) {
            return null;
        }
            
        try {
            Connection dbConn = DriverManager.getConnection(url + "/Users", dbUser, dbPass);
            PreparedStatement stmt = dbConn.prepareStatement(
                "SELECT Users.User_name, UserProfiles.Profile_name, Scores." + score + ", Scores.Time_played " +
                "FROM Scores " +
                "JOIN UserProfiles ON Scores.Profile_id = UserProfiles.Profile_id " +
                "JOIN Users ON UserProfiles.User_id = Users.User_id " +
                "ORDER BY Scores." + score + " DESC " +
                "LIMIT ?"
            );
            stmt.setInt(1, n);

            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
        
}
