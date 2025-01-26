package com.example.movies_service.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movies_service.DTO.TheaterDTO;
import com.example.movies_service.entities.Screen;
import com.example.movies_service.entities.Theater;
import com.example.movies_service.exceptions.MissingCompulsoryFeilds;
import com.example.movies_service.exceptions.TheaterNotFoundException;
import com.example.movies_service.repositories.ScreenRepository;
import com.example.movies_service.repositories.TheaterRepository;

@Service
public class TheaterServices {

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ScreenRepository screenRepository;

    // Get all theaters and return as DTO
    public List<TheaterDTO> getAllTheaters() {
        List<Theater> theaters = (List<Theater>) theaterRepository.findAll();
        List<TheaterDTO> theaterDTOList = new ArrayList<>();

        for (Theater theater : theaters) {
            theaterDTOList.add(convertToTheaterDTO(theater));
        }

        return theaterDTOList;
    }

    // Get a theater by ID and return as DTO
    public TheaterDTO getTheaterById(Long id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new TheaterNotFoundException("Theater with ID " + id + " not found"));

        return convertToTheaterDTO(theater);
    }

    	
    public TheaterDTO addTheater(TheaterDTO theaterDTO) {
        if (theaterDTO.getName() == null || theaterDTO.getLocation() == null || theaterDTO.getCity() == null || theaterDTO.getScreens() == null) {
            throw new MissingCompulsoryFeilds("Name, location, city, and screens are compulsory fields.");
        }

        List<Screen> screens = theaterDTO.getScreens();

        Theater savedTheater = Theater.builder()
                .name(theaterDTO.getName())
                .location(theaterDTO.getLocation())
                .city(theaterDTO.getCity())
                .build();

        // Set the theater for each screen
        for (Screen screen : screens) {
            screen.setTheater(savedTheater);
        }

        // Set screens in the theater (maintain bidirectional relationship)
        savedTheater.setScreens(screens);

        // Save the theater along with screens (cascade ensures screens are saved)
        theaterRepository.save(savedTheater);
        
        

        
        

        return TheaterDTO.builder()
                .theaterID(savedTheater.getTheaterID())
                .name(savedTheater.getName())
                .location(savedTheater.getLocation())
                .city(savedTheater.getCity())
                .screens(savedTheater.getScreens())
                .build();
    }

    
    public TheaterDTO updateTheater(Long id, Theater t) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new TheaterNotFoundException("Theater with ID " + id + " not found"));
        
        if (theater.getName() == null || theater.getLocation() == null || theater.getCity() == null || theater.getScreens() == null) {
            throw new MissingCompulsoryFeilds("Name, location, city, and screens are compulsory fields.");
        }
        
      List<Screen> scq= screenRepository.findByTheater_TheaterID(id);
      screenRepository.deleteAll(scq);
        
        theater.setCity(t.getCity());
        theater.setLocation(t.getLocation());
        theater.setName(t.getName());
        theater.setScreens(t.getScreens());
        theater.setTheaterID(id);
        
        List<Screen> screens = theater.getScreens();
        for (Screen screen : screens) {
            screen.setTheater(theater);
        }
        
        screenRepository.saveAll(screens);
        theaterRepository.save(theater);


        
        return convertToTheaterDTO(theater); 
    }

    // Delete a theater by ID and return the ID
    public Long deleteTheater(Long id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new TheaterNotFoundException("Theater with ID " + id + " not found"));

        theaterRepository.deleteById(id);
        return id;
    }

    // Add screens to a theater and return updated theater as DTO
    public TheaterDTO addScreensToTheater(Long theaterId, List<Screen> screens) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new TheaterNotFoundException("Theater with ID " + theaterId + " not found"));

        for (Screen screen : screens) {
            screen.setTheater(theater);
        }

        screenRepository.saveAll(screens);
        theater.setScreens(screens);

        Theater updatedTheater = theaterRepository.save(theater);

        // Convert updated Theater entity to DTO
        return convertToTheaterDTO(updatedTheater);
    }

    // Helper method to convert Theater entity to DTO
    private TheaterDTO convertToTheaterDTO(Theater theater) {
        return TheaterDTO.builder()
                .theaterID(theater.getTheaterID())
                .name(theater.getName())
                .location(theater.getLocation())
                .city(theater.getCity())
                .screens(theater.getScreens()) // Directly map screens
                .build();
    }
}