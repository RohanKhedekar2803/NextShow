package com.example.movies_service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.movies_service.entities.Movies;

@Repository
public interface MoviesRepository extends CrudRepository<Movies, Long> {

}
