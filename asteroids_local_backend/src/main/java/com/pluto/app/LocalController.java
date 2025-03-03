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
        if (dbClient.loginUser(name, pass)) {
            return "{\"success\":\"" + true + "\","
                    + "\"error\":\"" + "\"}";
        } else {
            return "{\"success\":\"" + false + "\","
                    + "\"error\":\"" + "Invalid login credentials" + "\"}";
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
        if (dbClient.createUser(name, pass)) {
            return "{\"success\":\"" + true + "\","
                    + "\"error\":\"" + "\"}";
        } else {
            return "{\"success\":\"" + false + "\","
                    + "\"error\":\"" + "User already exists" + "\"}";
        }
    }

    // /**
    //  * This method handles user registration requests on localhost:8080/api/register.
    //  * Reponse messages are sent in a json format.
    //  */
    // public String register(
    //         @RequestParam(value = "name") String name,
    //         @RequestParam(value = "pass") String pass
    //         ) {
      
         
    //     return "";
    //         } 
}
