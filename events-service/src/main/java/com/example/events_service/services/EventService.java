package com.example.events_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.example.events_service.entities.Event;
import com.example.events_service.exceptions.EventNotFoundException;
import com.example.events_service.redis.RedisService;
import com.example.events_service.repositories.EventsRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EventService {

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private RedisService redisService;

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public Event createEvent(Event event) {
        logger.info("Saving new event to DB");
        return eventsRepository.save(event);
    }

    // ✅ Fetch all events (not cached – optional based on use case)
    public List<Event> getAllEvents() {
        logger.info("Fetching all events from DB");
        return (List<Event>) eventsRepository.findAll();
    }

    // ✅ Fetch event by ID from cache if available
    public Event getEventById(Long id) {
        Event event = redisService.get("event" + id, Event.class);
        if (event != null) {
            logger.info("Fetching event with ID {} from CACHE", id);
            return event;
        } else {
            logger.info("Fetching event with ID {} from DB (not cache)", id);

            event = eventsRepository.findById(id)
                    .orElseThrow(() -> new EventNotFoundException("Event with ID " + id + " not found"));
            if (event != null) {
                redisService.set("event" + id, event, 500l);
            }
            return event;

        }

    }

    // ✅ Update and refresh the cache
    public Event updateEvent(Long id, Event updatedEvent) {
        logger.info("Updating event with ID {} and updating cache", id);
        Event existingEvent = getEventById(id);
        existingEvent.setEvent_Type(updatedEvent.getEvent_Type());
        existingEvent.setTitle(updatedEvent.getTitle());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setGenre(updatedEvent.getGenre());
        existingEvent.setLanguage(updatedEvent.getLanguage());
        existingEvent.setDuration(updatedEvent.getDuration());
        existingEvent.setReleaseDate(updatedEvent.getReleaseDate());
        existingEvent.setMetadata(updatedEvent.getMetadata());

        Event event = eventsRepository.save(existingEvent);
        redisService.set("event" + id, event, 500l);
        return eventsRepository.save(event);
    }

    // ✅ Delete and evict from cache
    public void deleteEvent(Long id) {

        Event event = getEventById(id);
        eventsRepository.delete(event);
        logger.info("Deleting event with ID {} from DB and evicting from cache", id);
        redisService.delete("event" + id);
    }
}
