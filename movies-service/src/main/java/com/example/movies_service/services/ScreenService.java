package com.example.movies_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movies_service.entities.Screen;
import com.example.movies_service.exceptions.ScreenNotFoundException;
import com.example.movies_service.repositories.ScreenRepository;

@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;


    // Get a screen by ID
    public Screen getScreenById(Long id) {
        return screenRepository.findById(id)
                .orElseThrow(() -> new ScreenNotFoundException("Screen with ID " + id + " not found"));
    }

    // Add a new screen
    public Screen addScreen(Screen screen) {
        return screenRepository.save(screen);
    }

    // Update an existing screen
    public Screen updateScreen(Long id, Screen screenDetails) {
    	
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ScreenNotFoundException("Screen with ID " + id + " not found"));
        
        screen.setSeatsArrangement(screenDetails.getSeatsArrangement());
        
        return screenRepository.save(screen);
    }

    // Delete a screen by ID
    public void deleteScreen(Long id) {
    	
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ScreenNotFoundException("Screen with ID " + id + " not found"));
        
        screenRepository.deleteById(id);
    }
}
