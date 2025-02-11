package com.example.events_service.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
public class Event {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventId;
    
    private String event_Type;
	
    @Column(nullable = false) // This field cannot be null
	private String title;
	
	private String Description;
	
	private String Genre;	
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> Language;	
	
	private LocalTime Duration;	
	
	private LocalDate ReleaseDate;
	
	@OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	@JoinColumn(name = "meta_data")
	@JsonManagedReference
	private EventMetadata Metadata;
	
}
