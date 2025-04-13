package com.example.events_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.events_service.entities.Auditorium;
import com.example.events_service.entities.Event;
import com.example.events_service.redis.RedisService;
import com.example.events_service.repositories.Auditoriumrepo;
import com.example.events_service.utilities.auditoriumType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuditoriumService {

    @Autowired
    private Auditoriumrepo auditoriumRepository;

    @Autowired
    private RedisService redisService;

    private static final Logger logger = LoggerFactory.getLogger(AuditoriumService.class);

    // ✅ Create an auditorium
    public Auditorium createAuditorium(Auditorium auditorium) {
        logger.info("Saving auditorium to DB");
        return auditoriumRepository.save(auditorium);
    }

    // ✅ Get all auditoriums
    public List<Auditorium> getAllAuditoriums() {
        logger.info("Fetching ALL auditoriums from DB");
        return (List<Auditorium>) auditoriumRepository.findAll();
    }

    // ✅ Get auditorium by ID with cache
    public Auditorium getAuditoriumById(Long id) {

        Auditorium auditorium = redisService.get("aud" + id, Auditorium.class);
        if (auditorium != null) {
            logger.info("Fetching auditorium with ID {} from CACHE", id);
        } else {

            auditorium = auditoriumRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("auditorium with id " + id.toString() + " not found"));
            redisService.set("aud" + id, auditorium, 1000l);
        }
        return auditorium;
    }

    // ✅ Update an auditorium
    public Auditorium updateAuditorium(Long id, Auditorium updatedAuditorium) {

        Auditorium existingAuditorium = getAuditoriumById(id);
        existingAuditorium.setName(updatedAuditorium.getName());
        existingAuditorium.setLocation(updatedAuditorium.getLocation());
        existingAuditorium.setCity(updatedAuditorium.getCity());
        existingAuditorium.setSeatsArrangement(updatedAuditorium.getSeatsArrangement());
        existingAuditorium.setNumberofblocks(updatedAuditorium.getNumberofblocks());

        Auditorium aud = auditoriumRepository.save(existingAuditorium);
        logger.info("Updating auditorium ID {} in DB and updating cache", id);
        redisService.set("aud" + id, aud, 1000l);
        return aud;
    }

    // ✅ Delete an auditorium
    public void deleteAuditorium(Long id) {

        Auditorium auditorium = getAuditoriumById(id);
        auditoriumRepository.delete(auditorium);
        logger.info("Deleting auditorium ID {} from DB and evicting from cache", id);
        redisService.delete("aud" + id);
    }

    public auditoriumType ConvertToEnum(String audtoriumFromUser) {
        auditoriumType auditorium;
        if (audtoriumFromUser.equalsIgnoreCase("GROUND")) {
            auditorium = auditoriumType.Ground;
        } else {
            throw new IllegalArgumentException("Not a valid Auditorium Type........");
        }
        return auditorium;
    }

    // ✅ Feign call receive
    public List<List<Integer>> get_seats_arrangement_by_auditorium_id(Long auditoriumId) {
        logger.info("Fetching seats arrangement for auditorium ID {} from DB", auditoriumId);
        Auditorium auditorium = auditoriumRepository.findById(auditoriumId)
                .orElseThrow(() -> new RuntimeException("auditoriumId " + auditoriumId + " id not valid"));
        return auditorium.getSeatsArrangement();
    }
}
