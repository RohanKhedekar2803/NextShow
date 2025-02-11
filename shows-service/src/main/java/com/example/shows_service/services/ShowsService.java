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
		existingShow.setEventId(updatedshowDTO.getEventId());
		existingShow.setAuditoriumId(updatedshowDTO.getAuditoriumId());
		existingShow.setBlockprices(updatedshowDTO.getBlockprices());
		existingShow.setStartTime(updatedshowDTO.getStartTime());
		existingShow.setEndTime(updatedshowDTO.getEndTime());
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
		if (showDTO.getAuditoriumId() == null || showDTO.getBlockprices() == null || showDTO.getEventId() == null
				|| showDTO.getStartTime() == null || showDTO.getEndTime() == null ) {
			throw new MissingCompulsoryFeilds("Essential fields are missing in the showDTO.");
		}
	}

	// Convert showDTO to Show entity
	private Shows convertToEntity(showDTO showDTO) {
		Shows shows = new Shows();
		shows.setAuditoriumId(showDTO.getAuditoriumId());
		shows.setEventId(showDTO.getEventId());
		shows.setShowId(showDTO.getShowId());
		shows.setStartTime(showDTO.getStartTime());
		shows.setEndTime(showDTO.getEndTime());
		shows.setBlockprices(showDTO.getBlockprices());
		return shows;
	}
	
	// Convert Show entity to showDTO
	private showDTO convertToDTO(Shows shows) {
		return showDTO.builder().showId(shows.getShowId()).eventId(shows.getEventId()).auditoriumId(shows.getAuditoriumId()).blockprices(shows.getBlockprices())
				.startTime(shows.getStartTime()).endTime(shows.getEndTime()).build();
	}
}