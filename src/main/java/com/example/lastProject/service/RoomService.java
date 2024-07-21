package com.example.lastProject.service;

import com.example.lastProject.dto.PaginatedResponse;
import com.example.lastProject.dto.RoomDto;
import com.example.lastProject.dto.UpsertRoom;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    PaginatedResponse<RoomDto> findAll(int page, int size);

    RoomDto findById(UUID id);

    RoomDto create(UpsertRoom upsertRoom);

    RoomDto update(UUID id, UpsertRoom upsertRoom);

    void deleteById(UUID id);

    PaginatedResponse<RoomDto> filterRooms(UUID id, String title, int page, int size);

    PaginatedResponse<RoomDto> findRoomsByIdASC(int page, int size);

    PaginatedResponse<RoomDto> findRoomsByIdDESC(int page, int size);

    PaginatedResponse<RoomDto> findRoomsByHotelIdASC(int page, int size);

    PaginatedResponse<RoomDto> findRoomsByHotelIdDESC(int page, int size);

    PaginatedResponse<RoomDto> findRoomsByPriceASC(int page, int size);

    PaginatedResponse<RoomDto> findRoomsByPriceDESC(int page, int size);

    PaginatedResponse<RoomDto> findRoomsByCountPeopleASC(int page, int size);

    PaginatedResponse<RoomDto> findRoomsByCountPeopleDESC(int page, int size);
}
