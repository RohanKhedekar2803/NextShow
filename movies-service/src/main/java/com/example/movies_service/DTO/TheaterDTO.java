package com.example.movies_service.DTO;

import java.util.List;

import com.example.movies_service.entities.Screen;
import com.example.movies_service.entities.Theater;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheaterDTO {

    private Long theaterID;

    private String name;
    private String location;
    private String city;

    private String auditoriumType;

    private List<Screen> screens;
}
