package com.example.lastProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private UUID id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private UUID roomId;
    private UUID userId;
}
