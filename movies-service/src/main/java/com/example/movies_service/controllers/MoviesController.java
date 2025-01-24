package com.example.movies_service.controllers;

import java.util.List;

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

import com.example.movies_service.DTO.MovieDTO;
import com.example.movies_service.services.MovieServices;

@RestController
@RequestMapping("/movies")
public class MoviesController {
	
	
	@GetMapping("/name")
    public String getMovieName() {
        return "CT WC WTC ASHES BGT"; // Hardcoded movie name
    }
	
	@Autowired
    private MovieServices movieServices;

    // Get all movies
    @GetMapping("/all")
    public ResponseEntity<List<MovieDTO>> getAllMovies()  {
        List<MovieDTO> movies = movieServices.get_all_movies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable("id") Long id) {
        MovieDTO movie = movieServices.getMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    // Update movie by ID
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovieById(@PathVariable("id") Long id, @RequestBody MovieDTO updatedMovieDTO) {
        MovieDTO updatedMovie = movieServices.updateMovieById(id, updatedMovieDTO);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }

    // Delete movie by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieById(@PathVariable("id") Long id) {
        movieServices.deleteMovieById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    
    @PostMapping("/add")
    public ResponseEntity<MovieDTO> saveMovie(@RequestBody MovieDTO movieDTO) {
        MovieDTO savedMovie = movieServices.saveMovie(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }
}