package com.example.shows_service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.shows_service.entities.Shows;

@Repository
public interface ShowRepository extends CrudRepository<Shows, Long> {

	public List<Shows> findAll();

	Long findAuditoriumIdByEventId(Long eventId);

	@Query("SELECT s.auditoriumId FROM Shows s WHERE s.showId = :showId")
	Long findAuditoriumIdByShowId(@Param("showId") Long showId);

}
