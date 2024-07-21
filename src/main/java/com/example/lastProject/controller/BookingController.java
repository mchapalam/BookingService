package com.example.lastProject.controller;

import com.example.lastProject.dto.BookingDto;
import com.example.lastProject.dto.PaginatedResponse;
import com.example.lastProject.dto.PaginationInfo;
import com.example.lastProject.dto.UpsertBooking;
import com.example.lastProject.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public PaginatedResponse<BookingDto> findAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int size){

        return bookingService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public BookingDto findById(@PathVariable UUID id){

        return bookingService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public BookingDto create(@RequestBody UpsertBooking upsertBooking){
        return bookingService.create(upsertBooking);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public BookingDto update(@RequestParam UUID id,
                             @RequestBody UpsertBooking upsertBooking){
        return bookingService.update(id, upsertBooking);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public void delete(@RequestParam UUID id){
        bookingService.delete(id);
    }
}

