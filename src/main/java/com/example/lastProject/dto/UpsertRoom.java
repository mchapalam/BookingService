package com.example.lastProject.dto;

import com.example.lastProject.entity.Hotel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertRoom {
    private String name;

    private String title;

    private String number;

    private Long price;

    private Short countPeople;

    private List<LocalDate> unavailableDates;

    private UUID hotelId;
}
