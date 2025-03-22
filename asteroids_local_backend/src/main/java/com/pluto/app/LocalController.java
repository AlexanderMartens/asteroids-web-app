package com.pluto.app;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.*;
import com.pluto.database.DatabaseClient;

/**
 * A controller class for the local backend. It handles HTTP requests from the 
 * front end using RestAPI and the Spring Boot framework. By default, it 
 * listens on port 8080. All requests are located on localhost:8080/api/
 */ 
@RestController
@RequestMapping("/api")
public class LocalController {

    /**
     * Regex pattern for matching usernames that must have 3-16 characters
     * (inclusive) and contain only letters (lowercase and uppercase), numbers,
     * and underscores (_).
     */
    private static final String USERNAME_FORMAT = "^[a-zA-Z0-9_]{3,16}$";

    /**
     * Regex pattern for matching passwords that must have 4-32 characters
     * (inclusive) and contain only letters (lowercase and uppercase), numbers,
     * and the following symbols: -=[]\;',./!@#$%^&*()_+{}|:"<>?`~
     * 
     * Note: the frontend must encode all symbols in form %XX with hex digit XX
     * in order for it to not be interpreted as a special character in the URL
     */
    private static final String PASSWORD_FORMAT = "^[a-zA-Z0-9-=\\[\\]\\\\;',.\\/!@#$%^&*()_+{}|:\"<>?`~]{4,32}$";

    /**
     * This method handles user login requests on localhost:8080/api/login.
     * Response messages are sent in a json format.
     * 
     * @see generateResponse - for json response format
     *
     * @param name - the login name of the user
     * @param pass - the password of the user
     * @return - a json formatted confirmation or error of the login request
     */
    @CrossOrigin(origins="*")
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "name", defaultValue = "") String name, 
            @RequestParam(value = "pass", defaultValue = "") String pass
            ) {
        
        // Check that name and pass are of valid format
        if (!name.matches(USERNAME_FORMAT))
            return generateResponse(false, "Username is invalid");
        if (!pass.matches(PASSWORD_FORMAT))
            return generateResponse(false, "Password is invalid");

        // TODO: rewrite condition block as try-catch block for checking dbClient creation errors
        DatabaseClient dbClient = new DatabaseClient();
        if (dbClient.loginUser(name, pass)) {
            return generateResponse(true);
        } else {
            return generateResponse(false, "Unable to login");
        }
    }

    /** 
     * This method handles user registration requests on localhost:8080/api/register.
     * Response messages are sent in a json format.
     * 
     * @see generateResponse - for json response format
     * 
     * @param name - the login name of the user
     * @param pass - the password of the user
     * @return - a json formatted confirmation or error of the registration request
     */
    @CrossOrigin(origins="*")
    @GetMapping("/register")
    public String register(
            @RequestParam(value = "name", defaultValue = "") String name, 
            @RequestParam(value = "pass", defaultValue = "") String pass
            ) {
        
        // Check that name and pass are of valid format
        if (!name.matches(USERNAME_FORMAT))
            return generateResponse(false, "Username is invalid");
        if (!pass.matches(PASSWORD_FORMAT))
            return generateResponse(false, "Password is invalid");

        // TODO: rewrite condition block as try-catch block for checking dbClient creation errors
        DatabaseClient dbClient = new DatabaseClient();
        if (dbClient.createUser(name, pass)) {
            return generateResponse(true);
        } else {
            return generateResponse(false, "Unable to register");
        }
    }

    /**
     * Generates a JSON formatted string representing a response message.
     * 
     * Overloaded method allowing error message to be specified
     * 
     * @param result - True if the response was sucessful, false otherwise
     * @param errorMsg - A detailed description of any errors, or blank if none
     */
    private String generateResponse(boolean status, String errorMsg) {
        return "{\"success\":\"" + status + "\","
                + "\"error\":\"" + errorMsg + "\"}";
    }

    /**
     * Generates a JSON formatted string representing a response message.
     * 
     * Overloaded method for no error message
     * 
     * @param result - True if the response was sucessful, false otherwise
     */
    private String generateResponse(boolean status) {
        return generateResponse(status, "");
    }

    /**
    * Handles requests related to the leaderboard.
    * This method fetches the top scores from the database view `Leaderboard`
    * and returns them in JSON format.
    *
    * @param limit The number of top scores to fetch (default is 10).
    * @return A JSON-formatted string containing the top scores or an error message.
    */
    @CrossOrigin(origins = "*")
    @GetMapping("/leaderboard")
    public String getLeaderboard(@RequestParam(value = "limit", defaultValue = "10") int limit) {
        DatabaseClient dbClient = new DatabaseClient();
        ResultSet rs = dbClient.fetchTopScores(limit, "Score"); // Fetch top scores ordered by highest Score
        StringBuilder jsonResult = new StringBuilder("{\"leaderboard\":[");

        try {
            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    jsonResult.append(",");
                }
                jsonResult.append("{")
                        .append("\"user\":\"").append(rs.getString("User_name")).append("\",")
                        .append("\"profile\":\"").append(rs.getString("Profile_name")).append("\",")
                        .append("\"score\":").append(rs.getInt("Score")).append(",")
                        .append("\"level\":").append(rs.getInt("Level_reached")).append(",")
                        .append("\"duration\":").append(rs.getInt("Duration_seconds")).append(",")
                        .append("\"time\":\"").append(rs.getTimestamp("Time_played")).append("\"")
                        .append("}");
                first = false;
            }
            jsonResult.append("]}");
            return jsonResult.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"error\":\"Failed to fetch leaderboard\"}";
        }
    }

    /**
    * Handles requests for uploading a new game score.
    * This method inserts a new score record into the `Scores` table.
    *
    * @param username The username of the player.
    * @param profile_name The profile name of the player.
    * @param score The score achieved in the game.
    * @param level The level reached in the game.
    * @param duration The duration of the game session in seconds.
    * @return A JSON response indicating success or failure.
    */
    @CrossOrigin(origins = "*")
    @PostMapping("/uploadScore")
    public String uploadScore(
            @RequestParam("username") String username,
            @RequestParam("profile_name") String profile_name,
            @RequestParam("score") int score,
            @RequestParam("level") int level,
            @RequestParam("duration") int duration
            ) {
        DatabaseClient dbClient = new DatabaseClient();
        String error = dbClient.uploadScore(username, profile_name, score, level, duration);

        if (error.equals("")) {
            return "{\"success\":true}";
        } else {
            return "{\"success\":false, \"error\":\"" + error + "\"}";
        }
    }


}
