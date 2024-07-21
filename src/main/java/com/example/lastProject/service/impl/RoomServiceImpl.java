package com.example.lastProject.service.impl;

import com.example.lastProject.dto.*;
import com.example.lastProject.entity.Hotel;
import com.example.lastProject.entity.Room;
import com.example.lastProject.mapper.RoomMapper;
import com.example.lastProject.repository.HotelRepository;
import com.example.lastProject.repository.RoomRepository;
import com.example.lastProject.service.RoomService;
import com.example.lastProject.utilis.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    @Override
    public PaginatedResponse<RoomDto> findAll(int page,int  size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomRepository.findAll(pageable);

        List<RoomDto> roomDtos = roomPage.getContent()
                .stream().map(roomMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                roomPage.getTotalElements(),
                roomPage.getTotalPages(),
                page,
                size
        );

        return new PaginatedResponse<>(roomDtos, paginationInfo);
    }

    @Override
    public RoomDto findById(UUID id) {
        return roomRepository.findById(id)
                .stream().map(roomMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Room with id " + id + " not found"));
    }

    @Override
    public RoomDto create(UpsertRoom upsertRoom) {
        Room room = roomRepository.save(
                roomMapper.toEntity(upsertRoom));

        return roomMapper.toDto(room);
    }

    @Override
    public RoomDto update(UUID id, UpsertRoom upsertRoom) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Room with id " + id + " not found"));

        Hotel hotel = hotelRepository.findById(room.getHotel().getId())
                .orElseThrow(() -> new NoSuchElementException("Hotel with id " + upsertRoom.getHotelId() + " not found"));

        BeanUtils.copyNonNullProperties(roomMapper
                .toEntity(upsertRoom), room);

        room.setHotel(hotel);

        return roomMapper.toDto(
                roomRepository.save(room)
        );
    }

    @Override
    public void deleteById(UUID id) {
        roomRepository.deleteById(id);
    }

    @Override
    public PaginatedResponse<RoomDto> filterRooms(UUID id, String title,int page, int size) {
        List<Room> rooms = roomRepository.filterRooms(id, title);
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), rooms.size());

        List<RoomDto> roomDtos = rooms.subList(start, end)
                .stream()
                .map(roomMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                rooms.size(),
                (int)Math.ceil((double)rooms.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(roomDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<RoomDto> findRoomsByIdASC(int page, int size) {
        List<Room> rooms = roomRepository.findRoomsByIdASC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), rooms.size());

        List<RoomDto> roomDtos = rooms.subList(start, end)
                .stream()
                .map(roomMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                rooms.size(),
                (int)Math.ceil((double)rooms.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(roomDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<RoomDto> findRoomsByIdDESC(int page, int size) {
        List<Room> rooms = roomRepository.findRoomsByIdDESC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), rooms.size());

        List<RoomDto> roomDtos = rooms.subList(start, end)
                .stream()
                .map(roomMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                rooms.size(),
                (int)Math.ceil((double)rooms.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(roomDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<RoomDto> findRoomsByHotelIdASC(int page, int size) {
        List<Room> rooms = roomRepository.findRoomsByHotelIdASC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), rooms.size());

        List<RoomDto> roomDtos = rooms.subList(start, end)
                .stream()
                .map(roomMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                rooms.size(),
                (int)Math.ceil((double)rooms.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(roomDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<RoomDto> findRoomsByHotelIdDESC(int page, int size) {
        List<Room> rooms = roomRepository.findRoomsByHotelIdDESC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), rooms.size());

        List<RoomDto> roomDtos = rooms.subList(start, end)
                .stream()
                .map(roomMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                rooms.size(),
                (int)Math.ceil((double)rooms.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(roomDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<RoomDto> findRoomsByPriceASC(int page, int size) {
        List<Room> rooms = roomRepository.findRoomsByPriceASC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), rooms.size());

        List<RoomDto> roomDtos = rooms.subList(start, end)
                .stream()
                .map(roomMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                rooms.size(),
                (int)Math.ceil((double)rooms.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(roomDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<RoomDto> findRoomsByPriceDESC(int page, int size) {
        List<Room> rooms = roomRepository.findRoomsByPriceDESC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), rooms.size());

        List<RoomDto> roomDtos = rooms.subList(start, end)
                .stream()
                .map(roomMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                rooms.size(),
                (int)Math.ceil((double)rooms.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(roomDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<RoomDto> findRoomsByCountPeopleASC(int page, int size) {
        List<Room> rooms = roomRepository.findRoomsByCountPeopleASC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), rooms.size());

        List<RoomDto> roomDtos = rooms.subList(start, end)
                .stream()
                .map(roomMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                rooms.size(),
                (int)Math.ceil((double)rooms.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(roomDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<RoomDto> findRoomsByCountPeopleDESC(int page, int size) {
        List<Room> rooms = roomRepository.findRoomsByCountPeopleDESC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), rooms.size());

        List<RoomDto> roomDtos = rooms.subList(start, end)
                .stream()
                .map(roomMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                rooms.size(),
                (int)Math.ceil((double)rooms.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(roomDtos, paginationInfo);
    }
}
