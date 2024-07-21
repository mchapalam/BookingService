package com.example.lastProject.service.impl;

import com.example.lastProject.dto.HotelDto;
import com.example.lastProject.dto.PaginatedResponse;
import com.example.lastProject.dto.PaginationInfo;
import com.example.lastProject.dto.UpsertHotel;
import com.example.lastProject.entity.Booking;
import com.example.lastProject.entity.Hotel;
import com.example.lastProject.mapper.HotelMapper;
import com.example.lastProject.repository.HotelRepository;
import com.example.lastProject.service.HotelService;
import com.example.lastProject.utilis.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    public PaginatedResponse<HotelDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Hotel> hotelPage = hotelRepository.findAll(pageable);

        List<HotelDto> hotelDtos = hotelPage.getContent()
                .stream()
                .map(hotelMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                hotelPage.getTotalElements(),
                hotelPage.getTotalPages(),
                page,
                size
        );

        return new PaginatedResponse<>(hotelDtos, paginationInfo);
    }

    @Override
    public HotelDto findById(UUID id) {
        return hotelRepository.findById(id)
                .stream().map(hotelMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Hotel with id " + id + " not found"));
    }

    @Override
    public HotelDto create(UpsertHotel upsertHotel) {
        Hotel hotel = hotelRepository.save(
                hotelMapper.toEntity(upsertHotel));

        return hotelMapper.toDto(hotel);
    }

    @Override
    public HotelDto update(UUID id, UpsertHotel upsertHotel) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hotel with id " + id + " not found"));

        BeanUtils.copyNonNullProperties(hotelMapper
                .toEntity(upsertHotel), hotel);

        return hotelMapper.toDto(
                hotelRepository.save(hotel)
        );
    }

    @Override
    public void deleteById(UUID id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hotel with id " + id + " not found"));

        hotelRepository.deleteById(id);
    }

    @Override
    public HotelDto addRating(UUID id, Short newMark) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hotel with id " + id + " not found"));

        double totalRating = hotel.getRating() * hotel.getNumberRatings();
        totalRating = totalRating + newMark;
        hotel.setNumberRatings(hotel.getNumberRatings() + 1);
        double newRating = totalRating / hotel.getNumberRatings();
        hotel.setRating(Math.round(newRating * 10.0) / 10.0);

        return hotelMapper.toDto(
                hotelRepository.save(hotel
        ));
    }

    @Override
    public PaginatedResponse<HotelDto> filter(UUID id, String name, String title, String city, String address, Double distanceFromCenter, Long rating, Long numberRatings, int page, int size) {
        log.info("call filter hotel service");

        List<Hotel> hotels = hotelRepository.filterHotels(id, name, title, city, address, distanceFromCenter, rating, numberRatings);
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), hotels.size());

        List<HotelDto> hotelDtos = hotels.subList(start, end)
                .stream()
                .map(hotelMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                hotels.size(),
                (int)Math.ceil((double)hotels.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(hotelDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<HotelDto> filterHotelsByDistanceFromCenterASC(int page, int size) {
        List<Hotel> hotels = hotelRepository.filterHotelsByDistanceFromCenterASC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), hotels.size());

        List<HotelDto> hotelDtos = hotels.subList(start, end)
                .stream()
                .map(hotelMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                hotels.size(),
                (int)Math.ceil((double)hotels.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(hotelDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<HotelDto> filterHotelsByRatingASC(int page, int size) {
        List<Hotel> hotels = hotelRepository.filterHotelsByRatingASC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), hotels.size());

        List<HotelDto> hotelDtos = hotels.subList(start, end)
                .stream()
                .map(hotelMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                hotels.size(),
                (int)Math.ceil((double)hotels.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(hotelDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<HotelDto> filterHotelsByNumberRatingsASC(int page, int size) {
        List<Hotel> hotels = hotelRepository.filterHotelsByNumberRatingsASC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), hotels.size());

        List<HotelDto> hotelDtos = hotels.subList(start, end)
                .stream()
                .map(hotelMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                hotels.size(),
                (int)Math.ceil((double)hotels.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(hotelDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<HotelDto> filterHotelsByDistanceFromCenterDESC(int page, int size) {
        List<Hotel> hotels = hotelRepository.filterHotelsByDistanceFromCenterDESC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), hotels.size());

        List<HotelDto> hotelDtos = hotels.subList(start, end)
                .stream()
                .map(hotelMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                hotels.size(),
                (int)Math.ceil((double)hotels.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(hotelDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<HotelDto> filterHotelsByRatingDESC(int page, int size) {
        List<Hotel> hotels = hotelRepository.filterHotelsByRatingDESC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), hotels.size());

        List<HotelDto> hotelDtos = hotels.subList(start, end)
                .stream()
                .map(hotelMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                hotels.size(),
                (int)Math.ceil((double)hotels.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(hotelDtos, paginationInfo);
    }

    @Override
    public PaginatedResponse<HotelDto> filterHotelsByNumberRatingsDESC(int page, int size) {
        List<Hotel> hotels = hotelRepository.filterHotelsByNumberRatingsDESC();
        Pageable pageable = PageRequest.of(page, size);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), hotels.size());

        List<HotelDto> hotelDtos = hotels.subList(start, end)
                .stream()
                .map(hotelMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                hotels.size(),
                (int)Math.ceil((double)hotels.size() / size),
                page,
                size
        );

        return new PaginatedResponse<>(hotelDtos, paginationInfo);
    }
}
