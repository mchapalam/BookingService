package com.example.lastProject.service.impl;

import com.example.lastProject.dto.*;
import com.example.lastProject.entity.Booking;
import com.example.lastProject.entity.Hotel;
import com.example.lastProject.mapper.BookingMapper;
import com.example.lastProject.repository.BookingRepository;
import com.example.lastProject.service.BookingService;
import com.example.lastProject.utilis.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.collection.spi.PersistentList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public PaginatedResponse<BookingDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPage = bookingRepository.findAll(pageable);

        List<BookingDto> bookingDtos = bookingPage.getContent()
                .stream()
                .map(bookingMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                bookingPage.getTotalElements(),
                bookingPage.getTotalPages(),
                page,
                size
        );

        return new PaginatedResponse<>(bookingDtos, paginationInfo);
    }

    @Override
    public BookingDto findById(UUID id) {
        return bookingRepository.findById(id)
                .stream().map(bookingMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Booking with id " + id + " not found"));
    }

    @Override
    public BookingDto create(UpsertBooking upsertBooking) {
        Booking booking = bookingRepository.save(
                bookingMapper.toEntity(upsertBooking));

        kafkaTemplate.send("room-booking", upsertBooking);

        UserInfo userInfo = new UserInfo(upsertBooking.getUserId(), LocalDate.now());

        kafkaTemplate.send("user-registration", userInfo);

        return bookingMapper.toDto(booking);
    }

    @Override
    public BookingDto update(UUID id, UpsertBooking upsertBooking) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Booking with id " + id + " not found"));

        BeanUtils.copyNonNullProperties(bookingMapper
                .toEntity(upsertBooking), booking);

        return bookingMapper.toDto(
                bookingRepository.save(booking)
        );
    }

    @Override
    public void delete(UUID id) {
        bookingRepository.deleteById(id);
    }
}
