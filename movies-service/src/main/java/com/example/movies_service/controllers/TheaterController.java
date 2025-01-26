package com.example.movies_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.movies_service.DTO.TheaterDTO;
import com.example.movies_service.entities.Screen;
import com.example.movies_service.entities.Theater;
import com.example.movies_service.services.TheaterServices;

@RestController
@RequestMapping("/theaters")
public class TheaterController {

    @Autowired
    private TheaterServices theaterServices;

    // Test endpoint to return a static response
    @GetMapping("/name")
    public String getMovieName() {
        return "CT WC WTC ASHES BGT"; // Hardcoded movie name
    }

    // Get all theaters
    @GetMapping
    public ResponseEntity<List<TheaterDTO>> getAllTheaters() {
        List<TheaterDTO> theaters = theaterServices.getAllTheaters();
        return new ResponseEntity<>(theaters, HttpStatus.OK);
    }

    // Get a theater by ID
    @GetMapping("/{id}")
    public ResponseEntity<TheaterDTO> getTheaterById(@PathVariable Long id) {
        TheaterDTO theater = theaterServices.getTheaterById(id);
        return new ResponseEntity<>(theater, HttpStatus.OK);
    }

    // Add a new theater
    @PostMapping("/add")
    public ResponseEntity<TheaterDTO> addTheater(@RequestBody TheaterDTO theaterDTO) {
        TheaterDTO addedTheater = theaterServices.addTheater(theaterDTO);
        return new ResponseEntity<>(addedTheater, HttpStatus.CREATED);
    }

    // Update an existing theater
    @PutMapping("/{id}")
    public ResponseEntity<TheaterDTO> updateTheater(@PathVariable Long id, @RequestBody Theater theater) {
        TheaterDTO updatedTheater = theaterServices.updateTheater(id, theater);
        return new ResponseEntity<>(updatedTheater, HttpStatus.OK);
    }

    // Delete a theater
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteTheater(@PathVariable Long id) {
        Long deletedId = theaterServices.deleteTheater(id);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }

    // Add screens to a theater
    @PostMapping("/{theaterId}/screens")
    public ResponseEntity<TheaterDTO> addScreensToTheater(@PathVariable Long theaterId, @RequestBody List<Screen> screens) {
        TheaterDTO updatedTheater = theaterServices.addScreensToTheater(theaterId, screens);
        return new ResponseEntity<>(updatedTheater, HttpStatus.OK);
    }
}
