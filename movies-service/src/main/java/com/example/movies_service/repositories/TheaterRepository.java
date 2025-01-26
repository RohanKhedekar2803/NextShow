package com.example.movies_service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.movies_service.entities.Screen;
import com.example.movies_service.entities.Theater;

@Repository
public interface TheaterRepository extends CrudRepository<Theater, Long>{

}