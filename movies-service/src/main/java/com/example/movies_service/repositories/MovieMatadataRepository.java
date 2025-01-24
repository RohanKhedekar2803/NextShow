package com.example.movies_service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.movies_service.entities.MovieMetadata;

@Repository
public interface MovieMatadataRepository extends CrudRepository<MovieMetadata, Long>{

}
