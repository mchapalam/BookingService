package com.example.lastProject.service;

import com.example.lastProject.dto.HotelDto;
import com.example.lastProject.dto.PaginatedResponse;
import com.example.lastProject.dto.UpsertHotel;
import com.example.lastProject.entity.Hotel;

import java.util.List;
import java.util.UUID;

public interface HotelService {

    PaginatedResponse<HotelDto> findAll(int page, int size);

    HotelDto findById(UUID id);

    HotelDto create(UpsertHotel upsertHotel);

    HotelDto update(UUID id, UpsertHotel upsertHotel);

    void deleteById(UUID id);

    HotelDto addRating(UUID id, Short newMark);

    PaginatedResponse<HotelDto> filter(UUID id, String name, String title, String city, String address, Double distanceFromCenter, Long rating, Long numberRatings, int page, int size);

    PaginatedResponse<HotelDto> filterHotelsByDistanceFromCenterASC(int page, int size);

    PaginatedResponse<HotelDto> filterHotelsByRatingASC(int page, int size);

    PaginatedResponse<HotelDto> filterHotelsByNumberRatingsASC(int page, int size);

    PaginatedResponse<HotelDto> filterHotelsByDistanceFromCenterDESC(int page, int size);

    PaginatedResponse<HotelDto> filterHotelsByRatingDESC(int page, int size);

    PaginatedResponse<HotelDto> filterHotelsByNumberRatingsDESC(int page, int size);
}
