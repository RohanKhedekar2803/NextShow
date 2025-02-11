package com.example.events_service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.events_service.entities.EventMetadata;

@Repository
public interface EventMetadataRepository extends CrudRepository<EventMetadata, Long>{

}
