package com.example.movies_service.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Screen {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screenID;

    @ManyToOne
    @JoinColumn(name = "theaterID")
    @JsonBackReference
    private Theater theater;
    
    private int screenNumber;

    private int capacity;
}
