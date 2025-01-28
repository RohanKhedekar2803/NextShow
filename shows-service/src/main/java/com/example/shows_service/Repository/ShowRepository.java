package com.example.shows_service.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.shows_service.entities.Shows;

@Repository
public interface ShowRepository extends CrudRepository<Shows, Long>{
	
	public List<Shows> findAll();
	
}
