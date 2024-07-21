package com.example.lastProject.service;

import com.example.lastProject.dto.BookingDto;
import com.example.lastProject.dto.PaginatedResponse;
import com.example.lastProject.dto.UpsertBooking;

import java.util.List;
import java.util.UUID;

public interface BookingService {

    PaginatedResponse<BookingDto> findAll(int page, int size);

    BookingDto findById(UUID id);

    BookingDto create(UpsertBooking upsertBooking);

    BookingDto update(UUID id, UpsertBooking upsertBooking);

    void delete(UUID id);
}
