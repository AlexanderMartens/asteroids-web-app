package com.pluto.app;

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
     * This method handles user login requests on localhost:8080/api/login.
     * Response messages are sent in a json format.
     *
     * @param name - the login name of the user
     * @param pass - the password of the user
     * @return - a json formatted confirmation or error of the login request
     */
    @CrossOrigin(origins="*")
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "name") String name, 
            @RequestParam(value = "pass") String pass
            ) {
        DatabaseClient dbClient = new DatabaseClient();
        String error = dbClient.loginUser(name, pass);
        if (error.equals("")) {
            return "{\"success\":\"" + true + "\","
                    + "\"error\":\"" + "\"}";
        } else {
            return "{\"success\":\"" + false + "\","
                    + "\"error\":\"" + error + "\"}";
        }
    }

    /** 
     * This method handles user registration requests on localhost:8080/api/register.
     * Response messages are sent in a json format.
     * 
     * @param name - the login name of the user
     * @param pass - the password of the user
     * @return - a json formatted confirmation or error of the registration request
     */
    @CrossOrigin(origins="*")
    @GetMapping("/register")
    public String register(
            @RequestParam(value = "name", defaultValue = "John Doe") String name, 
            @RequestParam(value = "pass", defaultValue = "123") String pass
            ) {
        DatabaseClient dbClient = new DatabaseClient();
        String error = dbClient.createUser(name, pass);
        if (error.equals("")) {
            return "{\"success\":\"" + true + "\","
                    + "\"error\":\"" + "\"}";
        } else {
            return "{\"success\":\"" + false + "\","
                    + "\"error\":\"" + error + "\"}";
        }
    }

    /**
     * This method handles user profile creation requests on localhost:8080/api/createProfile.
     * Response messages are sent in a json format.
     * 
     * @param username - the login name of the user
     * @param profile_name - the name of the profile
     * @return - a json formatted confirmation or error of the profile creation request
     */
    @CrossOrigin(origins="*")
    @GetMapping("/createProfile")
    public String createProfile(
            @RequestParam(value = "username", defaultValue = "") String username, 
            @RequestParam(value = "profile_name", defaultValue = "") String profile_name
            ) {
        DatabaseClient dbClient = new DatabaseClient();
        String error = dbClient.createProfile(username, profile_name);
        if (error.equals("")) {
            return "{\"success\":\"" + true + "\","
                    + "\"error\":\"" + "\"}";
        } else {
            return "{\"success\":\"" + false + "\","
                    + "\"error\":\"" + error + "\"}";
        }
    }

    /**
     * This method handles user profile editing requests on localhost:8080/api/editProfile.
     * Response messages are sent in a json format.
     * TODO: Check if new profile name has valid format
     * 
     * @param username - the login name of the user
     * @param profile_name - the name of the profile
     * @param new_profile_name - the new name of the profile
     * @return - a json formatted confirmation or error of the profile editing request
     */
    @CrossOrigin(origins="*")
    @GetMapping("/editProfile")
    public String editProfile(
            @RequestParam(value = "username", defaultValue = "") String username, 
            @RequestParam(value = "profile_name", defaultValue = "") String profile_name,
            @RequestParam(value = "new_profile_name", defaultValue = "") String new_profile_name
            ) {
        DatabaseClient dbClient = new DatabaseClient();
        String error = dbClient.editProfile(username, profile_name, new_profile_name);
        if (error.equals("")) {
            return "{\"success\":\"" + true + "\","
                    + "\"error\":\"" + "\"}";
        } else {
            return "{\"success\":\"" + false + "\","
                    + "\"error\":\"" + error + "\"}";
        }
    }

    /**
     * This method handles user profile deletion requests on localhost:8080/api/deleteProfile.
     * Response messages are sent in a json format.
     * 
     * @param username - the login name of the user
     * @param profile_name - the name of the profile
     * @return - a json formatted confirmation or error of the profile deletion request
     */
    @CrossOrigin(origins="*")
    @GetMapping("/deleteProfile")
    public String deleteProfile(
            @RequestParam(value = "username", defaultValue = "") String username, 
            @RequestParam(value = "profile_name", defaultValue = "") String profile_name
            ) {
        DatabaseClient dbClient = new DatabaseClient();
        String error = dbClient.deleteProfile(username, profile_name);
        if (error.equals("")) {
            return "{\"success\":\"" + true + "\","
                    + "\"error\":\"" + "\"}";
        } else {
            return "{\"success\":\"" + false + "\","
                    + "\"error\":\"" + error + "\"}";
        }
    }

    /**
     * This method handles user profile retrieval requests on localhost:8080/api/getProfiles.
     * Response messages are sent in a json format.
     * 
     * @param username - the login name of the user
     * @return - a json formatted list of profiles or an error message
     */
    @CrossOrigin(origins="*")
    @GetMapping("/getProfiles")
    public String getProfiles(
            @RequestParam(value = "username", defaultValue = "") String username
            ) {
        DatabaseClient dbClient = new DatabaseClient();
        String[] profiles = dbClient.getProfiles(username);
        if (profiles != null) {
            String profilesJson = "[";
            for (int i = 0; i < profiles.length; i++) {
                profilesJson += "\"" + profiles[i] + "\"";
                if (i != profiles.length - 1) {
                    profilesJson += ",";
                }
            }
            profilesJson += "]";
            return "{\"success\":\"" + true + "\","
                    + "\"error\":\"" + "\","
                    + "\"profiles\":" + profilesJson + "}";
        } else {
            return "{\"success\":\"" + false + "\","
                    + "\"error\":\"" + "No profiles found" + "\"}";
        }
    }
}
