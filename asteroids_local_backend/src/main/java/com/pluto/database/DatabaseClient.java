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
     * @return - String error message, is empty if method is successful
     * 
     */
    public String createUser(String username, String password) {
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
                "SELECT * FROM Users WHERE user_name = ?"
            );
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "User already exists";
            }

            // If the user does not exist, create the user
            stmt = dbConn.prepareStatement(
                "INSERT INTO Users (user_name, user_password) VALUES (?, ?)"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return "";
            
        } catch (SQLException e) {
            System.out.println(
                "Could not establish connection to MySQL database."
            );
            e.printStackTrace();
            return "Error creating user";
        } 
    }

    /**
     * Method to log in a user into the database.
     * 
     * @param username - username of the User
     * @param password - password of the User
     * @return - String error message, is empty if method is successful
     */
    public String loginUser(String username, String password) {
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
                "SELECT * FROM Users WHERE user_name = ? AND user_password = ?"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "";
            }
            
        } catch (SQLException e) {
            System.out.println(
                "Could not establish connection to MySQL database."
            );
            e.printStackTrace();
            return "Error logging in";
        }

        return "Username or password is incorrect";
    }

    /**
     * Creates a user profile in the database. User cannot have more than four profiles.
     * 
     * @param username - username of the User
     * @param profile_name - name of the profile
     * @return - Empty string if profile created, error message otherwise
     */
    public String createProfile(String username, String profile_name) {
        try (
            Connection dbConn = DriverManager.getConnection(
                url + "/Users", dbUser, dbPass
            );
        ) {
            // Get the user_id from the Users table
            int userId = getUserId(dbConn, username);
            if (userId == -1) {
                return "User does not exist";
            }

            // Check if user already has 4 profiles
            PreparedStatement stmt = dbConn.prepareStatement(
                "SELECT COUNT(*) FROM Profiles " + 
                "WHERE user_id = ?"
            );
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) >= 4) {
                return "Max profiles reached";
            }

            // Check if profile name already exists for that user
            stmt = dbConn.prepareStatement(
                "SELECT * FROM Profiles " + 
                "WHERE user_id = ? AND Profiles.profile_name = ?"
            );
            stmt.setInt(1, userId);
            stmt.setString(2, profile_name);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return "Profile already exists";
            }

            // Create profile
            stmt = dbConn.prepareStatement(
                "INSERT INTO Profiles (user_id, profile_name) VALUES (?, ?)"
            );
            stmt.setInt(1, userId);
            stmt.setString(2, profile_name);
            stmt.executeUpdate();
            return "";
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error creating profile";
        }
    }

    /**
     * Renames a user profile in the database.
     * 
     * @param username - username of the User
     * @param profile_name - name of the profile
     * @param new_profile_name - new name of the profile
     * @return - Empty string if profile edited, error message otherwise
     */
    public String renameProfile(String username, String profile_name, String new_profile_name) {
        try (
            Connection dbConn = DriverManager.getConnection(
                url + "/Users", dbUser, dbPass
            );
        ) {
            // Get the user_id from the Users table
            int userId = getUserId(dbConn, username);
            if (userId == -1) {
                return "User does not exist";
            }

            // Check if profile name exists for that user
            PreparedStatement stmt = dbConn.prepareStatement(
                "SELECT * FROM Profiles " + 
                "WHERE user_id = ? AND Profiles.profile_name = ?"
            );
            stmt.setInt(1, userId);
            stmt.setString(2, profile_name);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return "Profile does not exist";
            }

            // Check if new profile name already exists for that user
            stmt = dbConn.prepareStatement(
                "SELECT * FROM Profiles " + 
                "WHERE user_id = ? AND Profiles.profile_name = ?"
            );
            stmt.setInt(1, userId);
            stmt.setString(2, new_profile_name);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return "Profile already exists";
            }

            // Edit profile
            stmt = dbConn.prepareStatement(
                "UPDATE Profiles SET profile_name = ? " + 
                "WHERE user_id = ? AND Profiles.profile_name = ?"
            );
            stmt.setString(1, new_profile_name);
            stmt.setInt(2, userId);
            stmt.setString(3, profile_name);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "";
            } else {
                return "Error editing profile";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error editing profile";
        }
    }

    /**
     * Deletes a user profile in the database. Cascades to delete all data associated with the profile.
     * 
     * @param username - username of the User
     * @param profile_name - name of the profile
     * @return - Empty string if profile deleted, error message otherwise
     */
    public String deleteProfile(String username, String profile_name) {
        try (
            Connection dbConn = DriverManager.getConnection(
                url + "/Users", dbUser, dbPass
            );
        ) {
            // Get the user_id from the Users table
            int userId = getUserId(dbConn, username);
            if (userId == -1) {
                return "User does not exist";
            }

            // Check if profile name exists for that user
            PreparedStatement stmt = dbConn.prepareStatement(
                "SELECT * FROM Profiles " + 
                "WHERE user_id = ? AND Profiles.profile_name = ?"
            );
            stmt.setInt(1, userId);
            stmt.setString(2, profile_name);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return "Profile does not exist";
            }

            // Delete profile
            stmt = dbConn.prepareStatement(
                "DELETE FROM Profiles " +
                "WHERE user_id = ? AND Profiles.profile_name = ?"
            );
            stmt.setInt(1, userId);
            stmt.setString(2, profile_name);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                return "";
            } else {
                return "Error deleting profile";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error deleting profile";
        }
    }

    /**
     * Fetches all profiles for a user from the database.
     * 
     * @param username - username of the User
     * @return - A string array of all profiles for the user, returns null if an error occurs
     */
    public String[] getProfiles(String username) {
        try (
            // Append Database /Users to the end of the url
            Connection dbConn = DriverManager.getConnection(
                url + "/Users", dbUser, dbPass
            );
        ) {
            // Get the user_id from the Users table
            int userId = getUserId(dbConn, username);
            if (userId == -1) {
                return null;
            }

            // Get all profiles for that user
            PreparedStatement stmt = dbConn.prepareStatement(
                "SELECT profile_name FROM Profiles " + 
                "WHERE user_id = ?",
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            rs.last();
            int numRows = rs.getRow();
            rs.beforeFirst();
            String[] profiles = new String[numRows];
            int i = 0;
            while (rs.next()) {
                profiles[i] = rs.getString("profile_name");
                i++;
            }

            return profiles;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Uploads a score to the database.
     * 
     * @param username - username of the user
     * @param profile_name - profile name of the user
     * @param score - score to upload
     * @param level - level reached
     * @param duration - duration of the game
     * @return - Empty string if successful, error message otherwise
     */
    public String uploadScore(String username, String profile_name, int score, int level, int duration) {
        try (
            Connection dbConn = DriverManager.getConnection(
                url + "/Users", dbUser, dbPass
            );
        ) {
            // Get the user_id from the Users table
            int userId = getUserId(dbConn, username);
            if (userId == -1) {
                return "User does not exist";
            }

            // Get the profile_id from the Profiles table
            int profile_id = getProfileId(dbConn, userId, profile_name);
            if (profile_id == -1) {
                return "Profile does not exist";
            }

            // Upload the score
            PreparedStatement stmt = dbConn.prepareStatement(
                "INSERT INTO Scores (profile_id, score, level, duration_seconds) VALUES (?, ?, ?, ?)"
            );
            stmt.setInt(1, profile_id);
            stmt.setInt(2, score);
            stmt.setInt(3, level);
            stmt.setInt(4, duration);
            stmt.executeUpdate();

            return "";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error uploading score";
        }
    }

    /**
     * Fetches the top n scores from the database. 
     * Score must be one of the following: "score", "level", "duration_seconds".
     * Caller must close the ResultSet and Statement.
     * 
     * @param n - number of scores to fetch, must be a positive integer
     * @param score - which score to fetch
     * @return - a ResultSet of the top n scores or null if score is invalid or an error occurred
     */
    public ResultSet fetchTopScores(int n, String score) {
        if (!score.equals("score") && !score.equals("level") && !score.equals("duration_seconds")) {
            return null;
        }
        if (n <= 0) {
            return null;
        }
            
        try {
            Connection dbConn = DriverManager.getConnection(url + "/Users", dbUser, dbPass);
            PreparedStatement stmt = dbConn.prepareStatement(
                "SELECT * " +
                "FROM Scores " +
                "JOIN Profiles ON Scores.profile_id = Profiles.profile_id " +
                "JOIN Users ON Profiles.user_id = Users.user_id " +
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
        
    /**
     * Fetches the user id from the database.
     * 
     * @param dbConn - Connection to the database
     * @param username - username of the User
     * @return - The user id, returns -1 if user does not exist or an error occurs
     */
    private int getUserId(Connection dbConn, String username) {
        // Use try with resources to close the statement and result set
        try (PreparedStatement stmt = dbConn.prepareStatement("SELECT user_id FROM Users WHERE user_name = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user ID for " + username + ": " + e.getMessage());
            e.printStackTrace();
        }
        
        return -1;
    }

    /**
     * Fetches the profile id from the database.
     * 
     * @param dbConn - Connection to the database
     * @param userId - user id of the User
     * @param profile_name - profile name of the User
     * @return - The profile id, returns -1 if profile does not exist or an error occurs
     */
    private int getProfileId(Connection dbConn, int userId, String profile_name) {
        // Use try with resources to close the statement and result set
        try (PreparedStatement stmt = dbConn.prepareStatement("SELECT profile_id FROM Profiles WHERE user_id = ? AND profile_name = ?")) {
            stmt.setInt(1, userId);
            stmt.setString(2, profile_name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("profile_id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching profile ID for " + profile_name + ": " + e.getMessage());
            e.printStackTrace();
        }
        
        return -1;
    }
}
