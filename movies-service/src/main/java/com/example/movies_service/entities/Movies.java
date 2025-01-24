package com.example.movies_service.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Movies {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long movie_id;
	
    @Column(nullable = false) // This field cannot be null
	private String title;
	
	private String Description;
	
	private String Genre;	
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Column(nullable = false) // This field cannot be null
	private List<String> Language;	
	
	private LocalTime Duration;	
	
	@Column(nullable = false) // This field cannot be null
	private LocalDate ReleaseDate;
	
	@OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	@JoinColumn(name = "meta_data")
	private MovieMetadata movieMetadata;	
	
	
}
