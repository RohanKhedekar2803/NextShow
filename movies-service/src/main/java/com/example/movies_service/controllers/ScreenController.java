package com.example.movies_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movies_service.entities.Screen;
import com.example.movies_service.services.ScreenService;

@RestController
@RequestMapping("/screens")
public class ScreenController {
	
	@GetMapping("/name")
    public String getMovieName() {
        return "CT WC WTC ASHES BGT"; // Hardcoded movie name
    }

    @Autowired
    private ScreenService screenService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Screen> getScreenById(@PathVariable Long id) {
        Screen screen = screenService.getScreenById(id);
        return new ResponseEntity<>(screen, HttpStatus.OK);
    }

    // Add a new screen
    @PostMapping
    public ResponseEntity<Screen> addScreen(@RequestBody Screen screen) {
        Screen addedScreen = screenService.addScreen(screen);
        return new ResponseEntity<>(addedScreen, HttpStatus.CREATED);
    }

    // Update an existing screen
    @PutMapping("/{id}")
    public ResponseEntity<Screen> updateScreen(@PathVariable Long id, @RequestBody Screen screenDetails) {
        Screen updatedScreen = screenService.updateScreen(id, screenDetails);
        return new ResponseEntity<>(updatedScreen, HttpStatus.OK);
    }

    // Delete a screen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScreen(@PathVariable Long id) {
        screenService.deleteScreen(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
