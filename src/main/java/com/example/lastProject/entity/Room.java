package com.example.lastProject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String title;

    private String number;

    private Long price;

    @Column(name = "count_people")
    private Short countPeople;

    @ElementCollection
    @CollectionTable(name = "room_unavailable_dates", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "unavailable_date")
    private List<LocalDate> unavailableDates;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    public void addUnavailableDate(LocalDate date) {
        this.unavailableDates.add(date);
    }

    public void removeUnavailableDate(LocalDate date) {
        this.unavailableDates.remove(date);
    }
}
