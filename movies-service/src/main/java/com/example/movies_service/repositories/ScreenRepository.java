package com.example.movies_service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.movies_service.entities.Screen;
import com.example.movies_service.entities.Theater;

@Repository
public interface ScreenRepository extends CrudRepository<Screen, Long>{

	void deleteByTheater(Theater theater);
	
	List<Screen> findByTheater_TheaterID(Long theaterID);

}
