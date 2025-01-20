package com.example.movies_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MoviesController {
	
	
	@GetMapping("/name")
    public String getMovieName() {
        return "CT WC WTC ASHES BGT"; // Hardcoded movie name
    }
	
}
