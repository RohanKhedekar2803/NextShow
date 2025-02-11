package com.example.events_service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.events_service.entities.Event;


@Repository
public interface EventsRepository extends CrudRepository<Event, Long>{

}
