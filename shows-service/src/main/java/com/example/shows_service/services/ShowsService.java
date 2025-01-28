package com.example.shows_service.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shows_service.DTO.showDTO;
import com.example.shows_service.Repository.ShowRepository;
import com.example.shows_service.entities.Shows;
import com.example.shows_service.exceptions.MissingCompulsoryFeilds;

@Service
public class ShowsService {

	@Autowired
	private ShowRepository showsRepository;

	// Create a new show
	public showDTO createShow(showDTO showDTO) {
		validateShow(showDTO);
		Shows shows = convertToEntity(showDTO);
		
		// retrive number of total seats for this screen
		
		
		
		Shows savedShow = showsRepository.save(shows);
		return convertToDTO(savedShow);
	}

	// Get all shows
	public List<showDTO> getAllShows() {
		return showsRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	// Get show by ID
	public showDTO getShowById(Long showId) {
		Shows shows = showsRepository.findById(showId)
				.orElseThrow(() -> new MissingCompulsoryFeilds("Show with ID " + showId + " not found."));
		return convertToDTO(shows);
	}

	// Update a show
	public showDTO updateShow(Long showId, showDTO updatedshowDTO) {
		validateShow(updatedshowDTO);
		Shows existingShow = showsRepository.findById(showId)
				.orElseThrow(() -> new MissingCompulsoryFeilds("Show with ID " + showId + " not found."));
		existingShow.setMovieId(updatedshowDTO.getMovieId());
		existingShow.setScreenId(updatedshowDTO.getScreenId());
		existingShow.setTheaterId(updatedshowDTO.getTheaterId());
		existingShow.setStartTime(updatedshowDTO.getStartTime());
		existingShow.setEndTime(updatedshowDTO.getEndTime());
		existingShow.setPrice(updatedshowDTO.getPrice());
		Shows updatedShow = showsRepository.save(existingShow);
		return convertToDTO(updatedShow);
	}

	// Delete a show
	public void deleteShow(Long showId) {
		if (!showsRepository.existsById(showId)) {
			throw new MissingCompulsoryFeilds("Show with ID " + showId + " not found.");
		}
		showsRepository.deleteById(showId);
	}
	
	// Validate essential fields
	private void validateShow(showDTO showDTO) {
		if (showDTO.getMovieId() == null || showDTO.getScreenId() == null || showDTO.getTheaterId() == null
				|| showDTO.getStartTime() == null || showDTO.getEndTime() == null || showDTO.getPrice() == null) {
			throw new MissingCompulsoryFeilds("Essential fields are missing in the showDTO.");
		}
	}

	// Convert showDTO to Show entity
	private Shows convertToEntity(showDTO showDTO) {
		Shows shows = new Shows();
		shows.setMovieId(showDTO.getMovieId());
		shows.setScreenId(showDTO.getScreenId());
		shows.setTheaterId(showDTO.getTheaterId());
		shows.setStartTime(showDTO.getStartTime());
		shows.setEndTime(showDTO.getEndTime());
		shows.setPrice(showDTO.getPrice());
		return shows;
	}
	
	// Convert Show entity to showDTO
	private showDTO convertToDTO(Shows shows) {
		return showDTO.builder().movieId(shows.getMovieId()).screenId(shows.getScreenId()).theaterId(shows.getTheaterId())
				.startTime(shows.getStartTime()).endTime(shows.getEndTime()).price(shows.getPrice()).showId(shows.getShowId()).build();
	}
}