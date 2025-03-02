package com.pluto.app;

import org.springframework.web.bind.annotation.*;
import com.pluto.database.*;

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
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "name") String name, 
            @RequestParam(value = "pass") String pass
            ) {

        

        return "{\"name\": \"" + name + "\"},"
                + "{\"pass\": \"" + pass + "\"}";
    }

    /**
     * This method handles user registration requests on localhost:8080/api/register.
     * Reponse messages are sent in a json format.
     */
    public String register(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "pass") String pass
            ) {
      
         
        return "";
            } 
}
