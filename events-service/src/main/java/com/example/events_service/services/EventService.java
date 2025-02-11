package com.example.events_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.events_service.entities.Event;
import com.example.events_service.exceptions.EventNotFoundException;
import com.example.events_service.repositories.EventsRepository;

@Service
public class EventService {

	@Autowired
    private EventsRepository eventsRepository;


    public Event createEvent(Event event) {
        return eventsRepository.save(event);
    }


    public List<Event> getAllEvents() {
        return (List<Event>) eventsRepository.findAll();
    }


    public Event getEventById(Long id) {
        return eventsRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event with ID " + id + " not found"));
    }


    public Event updateEvent(Long id, Event updatedEvent) {
        Event existingEvent = getEventById(id);
        existingEvent.setEvent_Type(updatedEvent.getEvent_Type());
        existingEvent.setTitle(updatedEvent.getTitle());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setGenre(updatedEvent.getGenre());
        existingEvent.setLanguage(updatedEvent.getLanguage());
        existingEvent.setDuration(updatedEvent.getDuration());
        existingEvent.setReleaseDate(updatedEvent.getReleaseDate());
        existingEvent.setMetadata(updatedEvent.getMetadata()); 
        return eventsRepository.save(existingEvent);
    }

   
    public void deleteEvent(Long id) {
        Event event = getEventById(id);
        eventsRepository.delete(event);
    }
}

