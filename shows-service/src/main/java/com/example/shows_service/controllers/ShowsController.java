package com.example.shows_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shows_service.DTO.showDTO;
import com.example.shows_service.services.ShowsService;

@RestController
@RequestMapping("/shows")
public class ShowsController {

    @Autowired
    private ShowsService showsService;

    // Get movie name
    @GetMapping("/name")
    public String getMovieName() {
        return "CT WC WTC ASHES BGT"; // Hardcoded movie name
    }

    // Create a new show
    @PostMapping
    public ResponseEntity<showDTO> createShow(@RequestBody showDTO showDTO) {
        showDTO createdShow = showsService.createShow(showDTO);
        return ResponseEntity.ok(createdShow);
    }

    // Get all shows
    @GetMapping
    public ResponseEntity<List<showDTO>> getAllShows() {
        List<showDTO> shows = showsService.getAllShows();
        return ResponseEntity.ok(shows);
    }

    // Get show by ID
    @GetMapping("/{showId}")
    public ResponseEntity<showDTO> getShowById(@PathVariable Long showId) {
        showDTO show = showsService.getShowById(showId);
        return ResponseEntity.ok(show);
    }

    // Update a show
    @PutMapping("/{showId}")
    public ResponseEntity<showDTO> updateShow(@PathVariable Long showId, @RequestBody showDTO updatedshowDTO) {
        showDTO show = showsService.updateShow(showId, updatedshowDTO);
        return ResponseEntity.ok(show);
    }

    // Delete a show
    @DeleteMapping("/{showId}")
    public ResponseEntity<String> deleteShow(@PathVariable Long showId) {
        showsService.deleteShow(showId);
        return ResponseEntity.ok("Show with ID " + showId + " deleted successfully.");
    }
}
