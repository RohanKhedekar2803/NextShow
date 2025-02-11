package com.example.events_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.events_service.entities.Auditorium;
import com.example.events_service.repositories.Auditoriumrepo;

@Service
public class AuditoriumService {

	@Autowired
    private Auditoriumrepo auditoriumRepository;



    // ✅ Create an auditorium
    public Auditorium createAuditorium(Auditorium auditorium) {
        return auditoriumRepository.save(auditorium);
    }

    // ✅ Get all auditoriums
    public List<Auditorium> getAllAuditoriums() {
        return (List<Auditorium>) auditoriumRepository.findAll();
    }

    // ✅ Get auditorium by ID
    public Auditorium getAuditoriumById(Long id) {
        return auditoriumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("auditorium with id " +id.toString()+ " not found" ));
    }

    // ✅ Update an auditorium
    public Auditorium updateAuditorium(Long id, Auditorium updatedAuditorium) {
        Auditorium existingAuditorium = getAuditoriumById(id);
        existingAuditorium.setName(updatedAuditorium.getName());
        existingAuditorium.setLocation(updatedAuditorium.getLocation());
        existingAuditorium.setCity(updatedAuditorium.getCity());
        existingAuditorium.setAuditoriumType(updatedAuditorium.getAuditoriumType());
        existingAuditorium.setSeatsArrangement(updatedAuditorium.getSeatsArrangement());
        existingAuditorium.setNumberofblocks(updatedAuditorium.getNumberofblocks());
        return auditoriumRepository.save(existingAuditorium);
    }

    // ✅ Delete an auditorium
    public void deleteAuditorium(Long id) {
        Auditorium auditorium = getAuditoriumById(id);
        auditoriumRepository.delete(auditorium);
    }
}
