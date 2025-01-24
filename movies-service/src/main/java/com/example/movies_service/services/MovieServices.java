package com.example.movies_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movies_service.DTO.MovieDTO;
import com.example.movies_service.entities.MovieMetadata;
import com.example.movies_service.entities.Movies;
import com.example.movies_service.exceptions.MissingCompulsoryFeilds;
import com.example.movies_service.exceptions.MovieNotFoundException;
import com.example.movies_service.repositories.MovieMatadataRepository;
import com.example.movies_service.repositories.MoviesRepository;

import jakarta.transaction.Transactional;

@Service
public class MovieServices {

    @Autowired
    private MoviesRepository moviesRepository;
    
    @Autowired
    private MovieMatadataRepository movieMatadataRepository;
    
    // Get all movies
    public List<MovieDTO> get_all_movies() {
        Iterable<Movies> movies = moviesRepository.findAll();
        
        List<MovieDTO> movieDTOList = new ArrayList<>();
        for (Movies movie : movies) {
            MovieDTO movieDTO = MovieDTO.builder()
                .movie_id(movie.getMovie_id())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .genre(movie.getGenre())
                .language(movie.getLanguage())
                .duration(movie.getDuration())
                .releaseDate(movie.getReleaseDate())
                .cast(movie.getMovieMetadata().getCast())
                .posterURL(movie.getMovieMetadata().getPosterURL())
                .build();
            movieDTOList.add(movieDTO);
        }
        
        return movieDTOList;
    }

    // Get movie by ID
    public MovieDTO getMovieById(Long movieId) {
        if (movieId == null) {
            throw new IllegalArgumentException("Movie ID cannot be null");
        }

        Optional<Movies> movie = moviesRepository.findById(movieId);

        if (movie.isPresent()) {
            Movies m = movie.get();
            return MovieDTO.builder()
                .movie_id(m.getMovie_id())
                .title(m.getTitle())
                .description(m.getDescription())
                .genre(m.getGenre())
                .language(m.getLanguage())
                .duration(m.getDuration())
                .releaseDate(m.getReleaseDate())
                .cast(m.getMovieMetadata().getCast())
                .posterURL(m.getMovieMetadata().getPosterURL())
                .build();
        } else {
            throw new MovieNotFoundException("Movie with id " + movieId + " not found");
        }
    }

    // Update movie by ID
    @Transactional
    public MovieDTO updateMovieById(Long movieId, MovieDTO updatedMovieDTO) {
        if (movieId == null) {
            throw new IllegalArgumentException("Movie ID cannot be null");
        }

        if (updatedMovieDTO.getTitle() == null) {
            throw new MissingCompulsoryFeilds("The field 'Title' is missing and cannot be null.");
        }

        if (updatedMovieDTO.getLanguage() == null) {
            throw new MissingCompulsoryFeilds("The field 'Language' is missing and cannot be null.");
        }

        if (updatedMovieDTO.getReleaseDate() == null) {
            throw new MissingCompulsoryFeilds("The field 'ReleaseDate' is missing and cannot be null.");
        }

        Optional<Movies> movieOptional = moviesRepository.findById(movieId);

        if (movieOptional.isPresent()) {
            Movies movie = movieOptional.get();

            // Update the fields with the new values
            movie.setTitle(updatedMovieDTO.getTitle());
            movie.setDescription(updatedMovieDTO.getDescription());
            movie.setGenre(updatedMovieDTO.getGenre());
            movie.setLanguage(updatedMovieDTO.getLanguage());
            movie.setDuration(updatedMovieDTO.getDuration());
            movie.setReleaseDate(updatedMovieDTO.getReleaseDate());
            movie.getMovieMetadata().setCast(updatedMovieDTO.getCast());
            movie.getMovieMetadata().setPosterURL(updatedMovieDTO.getPosterURL());

            moviesRepository.save(movie);

            return MovieDTO.builder()
                .movie_id(movie.getMovie_id())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .genre(movie.getGenre())
                .language(movie.getLanguage())
                .duration(movie.getDuration())
                .releaseDate(movie.getReleaseDate())
                .cast(movie.getMovieMetadata().getCast())
                .posterURL(movie.getMovieMetadata().getPosterURL())
                .build();
        } else {
            throw new MovieNotFoundException("Movie with id " + movieId + " not found");
        }
    }

    // Delete movie by ID
    public Boolean deleteMovieById(Long movieId) {
        if (movieId == null) {
            throw new IllegalArgumentException("Movie ID cannot be null");
        }

        Optional<Movies> movieOptional = moviesRepository.findById(movieId);

        if (movieOptional.isPresent()) {
            moviesRepository.delete(movieOptional.get());
            return true;
        } else {
            throw new MovieNotFoundException("Movie with id " + movieId + " not found");
        }
    }

    // Save new movie
    public MovieDTO saveMovie(MovieDTO movieDTO) {
        if (movieDTO.getTitle() == null) {
            throw new MissingCompulsoryFeilds("The field 'Title' is missing and cannot be null.");
        }

        if (movieDTO.getLanguage() == null) {
            throw new MissingCompulsoryFeilds("The field 'Language' is missing and cannot be null.");
        }

        if (movieDTO.getReleaseDate() == null) {
            throw new MissingCompulsoryFeilds("The field 'ReleaseDate' is missing and cannot be null.");
        }

        // Create a new Movies entity from the DTO
        MovieMetadata movieMetadata = MovieMetadata.builder()
            .cast(movieDTO.getCast())
            .posterURL(movieDTO.getPosterURL())
            .build();
        
        movieMetadata = movieMatadataRepository.save(movieMetadata);
        

        Movies movie = Movies.builder()
            .title(movieDTO.getTitle())
            .Description(movieDTO.getDescription())
            .Genre(movieDTO.getGenre())
            .Language(movieDTO.getLanguage())
            .Duration(movieDTO.getDuration())
            .ReleaseDate(movieDTO.getReleaseDate())
            .movieMetadata(movieMetadata)
            .build();

        // Save the movie entity
        Movies savedMovie = moviesRepository.save(movie);

        // Convert saved entity back to DTO
        return MovieDTO.builder()
            .movie_id(savedMovie.getMovie_id())
            .title(savedMovie.getTitle())
            .description(savedMovie.getDescription())
            .genre(savedMovie.getGenre())
            .language(savedMovie.getLanguage())
            .duration(savedMovie.getDuration())
            .releaseDate(savedMovie.getReleaseDate())
            .cast(savedMovie.getMovieMetadata().getCast())
            .posterURL(savedMovie.getMovieMetadata().getPosterURL())
            .build();
    }
}
