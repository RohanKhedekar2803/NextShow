package com.example.UserService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserService.feign_client.MoviesClient;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private MoviesClient moviesClient;
	
    @GetMapping("/get")
    public String getUser() {
    	String response = "User: John Doe, ID: 123 is Watching --> ";
        return response+moviesClient.getMovieName();
    }
    
    @GetMapping("/greet")
    public String getdata() {
    	return "Hello ";

    }
    
}
