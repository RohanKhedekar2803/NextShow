package com.example.events_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.events_service.entities.Auditorium;
import com.example.events_service.services.AuditoriumService;

@RestController
@RequestMapping("eventsAPI/auditoriums")
public class AuditoriumController {

    @Autowired
    private AuditoriumService auditoriumService;

    @PostMapping
    public ResponseEntity<Auditorium> createAuditorium(@RequestBody Auditorium auditorium) {
        Auditorium savedAuditorium = auditoriumService.createAuditorium(auditorium);
        return new ResponseEntity<>(savedAuditorium, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Auditorium>> getAllAuditoriums() {
        return ResponseEntity.ok(auditoriumService.getAllAuditoriums());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auditorium> getAuditoriumById(@PathVariable Long id) {
        return ResponseEntity.ok(auditoriumService.getAuditoriumById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auditorium> updateAuditorium(
            @PathVariable Long id,
            @RequestBody Auditorium updatedAuditorium) {
        Auditorium updated = auditoriumService.updateAuditorium(id, updatedAuditorium);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuditorium(@PathVariable Long id) {
        auditoriumService.deleteAuditorium(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("getseats/{auditoriumId}")
    public ResponseEntity<List<List<Integer>>> get_seats_arrangement_by_auditorium_id(@PathVariable Long auditoriumId) {
        return ResponseEntity.ok(auditoriumService.get_seats_arrangement_by_auditorium_id(auditoriumId));

    }

}
