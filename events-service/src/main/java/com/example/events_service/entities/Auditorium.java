package com.example.events_service.entities;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.example.events_service.utilities.auditoriumType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Auditorium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditoriumId;

    private String name;

    private String location;

    private String city;

    private auditoriumType auditorium;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json") // Ensure it's a JSON column
    private List<List<Integer>> seatsArrangement;

    private Integer numberofblocks;

    Auditorium(String auditorium) {
        this.auditorium = auditoriumType.Ground;
    }
}