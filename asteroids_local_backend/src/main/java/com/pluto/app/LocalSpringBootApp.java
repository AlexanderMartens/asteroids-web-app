package com.pluto.app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class LocalSpringBootApp {
    public static void main(String[] args) {
      SpringApplication.run(LocalSpringBootApp.class, args);
    }
    @GetMapping("/login")
    public String login(@RequestParam(value = "name", defaultValue = "John Doe") String name, 
                        @RequestParam(value = "pass", defaultValue = "123") String pass) {
      return "{\"name\": \"" + name + "\"},"
           + "{\"pass\": \"" + pass + "\"}";
    }
}
