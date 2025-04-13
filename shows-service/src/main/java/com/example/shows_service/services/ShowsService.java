package com.example.shows_service.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.nextshowdto.Seat;
import com.example.shows_service.DTO.showDTO;
import com.example.shows_service.Feign.EvenstsServiceClient;
import com.example.shows_service.Redis.RedisService;
import com.example.shows_service.Repository.ShowRepository;
import com.example.shows_service.entities.Shows;
import com.example.shows_service.exceptions.MissingCompulsoryFeilds;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShowsService {

	@Autowired
	private ShowRepository showsRepository;

	@Autowired
	private EvenstsServiceClient evenstsServiceClient;

	@Autowired
	private RedisService redisService;

	// Create a new show
	public showDTO createShow(showDTO showDTO) {

		// call event service to check shows validity
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
		showDTO show = redisService.get("event" + showId, showDTO.class);
		if (show != null) {
			log.info("Fetching event with ID {} from CACHE", showId);
			return show;
		} else {
			log.info("Fetching event with ID {} from DB", showId);

			Shows showfromDB = showsRepository.findById(showId)
					.orElseThrow(() -> new MissingCompulsoryFeilds("Show with ID " + showId + " not found."));

			if (showfromDB != null) {
				show = convertToDTO(showfromDB);
				redisService.set("show" + showId, show, 1000l);
				return show;
			}

			return null;
		}

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
		showDTO show = convertToDTO(updatedShow);
		log.info("Updating in event with ID {} in DB & CACHE", showId);
		redisService.set("show" + showId, show, 1000l);
		return show;
	}

	// Delete a show
	public void deleteShow(Long showId) {
		if (!showsRepository.existsById(showId)) {
			throw new MissingCompulsoryFeilds("Show with ID " + showId + " not found.");
		}

		log.info("DELETING in event with ID {} in DB & CACHE", showId);
		redisService.delete("show" + showId);
		showsRepository.deleteById(showId);
	}

	// Validate essential fields
	private void validateShow(showDTO showDTO) {
		if (showDTO.getAuditoriumId() == null || showDTO.getBlockprices() == null || showDTO.getEventId() == null
				|| showDTO.getStartTime() == null || showDTO.getEndTime() == null) {
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
		return showDTO.builder().showId(shows.getShowId()).eventId(shows.getEventId())
				.auditoriumId(shows.getAuditoriumId()).blockprices(shows.getBlockprices())
				.startTime(shows.getStartTime()).endTime(shows.getEndTime()).build();
	}

	public Long check_seats_validity_and_get_price(List<com.example.nextshowdto.Seat> seats) {

		// Sxxx-Cxxx-Rxxx this is format
		try {
			HashSet<String> set = new HashSet<>();
			for (Seat seat : seats) {
				if (set.contains(seat.toString())) {
					System.out.println(seat + " is present duplicate.");
					throw new Exception("duplicate seats selected");
				} else {
					set.add(seat.toString());
				}
			}

			String seatId = seats.get(0).getSeatId();

			String numberStr = seatId.substring(1, 4); // "123"

			Long showid = Long.parseLong(numberStr);

			Long auditoriumId = showsRepository.findAuditoriumIdByShowId(showid);
			System.out.println("inhere ......");
			// call event-service to get seats_arrangement array save it in cache (do later)
			// apicall to events-service it will return seat_arrangement
			List<List<Integer>> seat_arrangement = get_seats_arrangement_by_auditorium_id(auditoriumId); // from

			// validate if column and rows are valid
			for (com.example.nextshowdto.Seat seat : seats) {
				check_Seat_validity(seat.getSeatId(), seat_arrangement);
			}
			System.out.println("in this ......");

			Shows show = showsRepository.findById(showid)
					.orElseThrow(() -> new RuntimeException("Show with ID " + showid + " not found."));
			List<Integer> sections = show.getBlockprices();
			Map<Integer, Long> row_price_map = new HashMap<Integer, Long>();
			Integer rowIndex = 1;
			int sectionIndex = 0;

			if (seat_arrangement.size() != sections.size()) {
				throw new RuntimeException("number of sections mismatched");// throw custom eception
			}

			for (List<Integer> section : seat_arrangement) {
				for (Integer row : section) {
					row_price_map.put(rowIndex++, Long.valueOf(sections.get(sectionIndex)));
				}
				sectionIndex++;
			}
			for (Map.Entry<Integer, Long> entry : row_price_map.entrySet()) {
				Integer key = entry.getKey();
				Long value = entry.getValue();
				System.out.println("key: " + key + " value: " + value);
			}

			numberStr = seatId.substring(11); // "123"

			Integer row0 = Integer.parseInt(numberStr); // 123

			for (Seat seat : seats) { // based on all row-ids check if they have same price
				if (!row_price_map.get(Integer.parseInt(seat.getSeatId().substring(11)))
						.equals(row_price_map.get(row0))) {
					throw new RuntimeException("all seats not from same section"); // throw custom eception
				}
			}
			System.out.println("final seat price is for each seat is " + row_price_map.get(row0));
			return row_price_map.get(row0);
		} catch (Exception e) {
			System.out.println("service call failed");
			return -1L;

		}
	}

	void check_Seat_validity(String seat, List<List<Integer>> seat_arrangement) {
		Integer colx = Integer.parseInt(seat.substring(6, 9));
		Integer rowx = Integer.parseInt(seat.substring(11));

		// row check
		if (rowx <= 0 && colx <= 0) {
			throw new RuntimeException("seats are not valid");
		}

		int rowIndex = 1;
		Map<Integer, Integer> row_seatcount_map = new HashMap<Integer, Integer>();
		for (List<Integer> sections : seat_arrangement) { // [ [10,10,10], [10,10,10], [10,10,10] ]
			for (Integer row : sections) { // [10,10,10]
				row_seatcount_map.put(rowIndex++, row);
			}
		}

		if (!row_seatcount_map.containsKey(rowx) || row_seatcount_map.get(rowx) < colx
				|| row_seatcount_map.get(rowx) < (colx - 1)) {
			throw new RuntimeException("seats are not valid");
		}

	}

	List<List<Integer>> get_seats_arrangement_by_auditorium_id(Long auditoriumId) {
		ResponseEntity<List<List<Integer>>> res = evenstsServiceClient
				.get_seats_arrangement_by_auditorium_id(auditoriumId);
		System.out.println(res);
		return res.getBody();
	}
}