package com.example.movies_service.entities;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json") // Ensure it's a JSON column
    private List<List<Integer>> seatsArrangement;
}
