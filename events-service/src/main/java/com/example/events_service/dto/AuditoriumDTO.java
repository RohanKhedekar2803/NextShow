package com.example.events_service.dto;

import java.util.List;

import com.example.events_service.utilities.auditoriumType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditoriumDTO {

    private Long auditoriumId;

    private String name;

    private String location;

    private String city;

    private auditoriumType auditoriumType;

    private List<List<Integer>> seatsArrangement;

    private Integer numberofblocks;

}