package com.example.movies_service.DTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDTO {
	

	private Long movie_id;
	
	private String title;
	
	private String description;
	
	private String genre;	

	private List<String> language;	
	
	private LocalTime duration;	
	
	private LocalDate releaseDate;
	
    private List<String> cast;

    private String posterURL;
}
