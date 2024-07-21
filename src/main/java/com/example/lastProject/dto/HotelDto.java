package com.example.lastProject.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto {
    private UUID id;

    private String name;

    private String title;

    private String city;

    private String address;

    private String distanceFromCenter;

    private Short rating;

    private Long numberRatings;
}
