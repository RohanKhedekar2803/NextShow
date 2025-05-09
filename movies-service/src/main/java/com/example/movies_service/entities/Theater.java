package com.example.movies_service.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.example.movies_service.Utilities.auditoriumType;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theaterID;

    private String name;
    private String location;
    private String city;
    
    private auditoriumType auditoriumType;
    
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    @JsonManagedReference //to aviod recursive response issue occured due to bidirectional mapping 
    private List<Screen> screens;
    
    Theater(){
    	this.auditoriumType=auditoriumType.Theater;
    }
    
   
}
