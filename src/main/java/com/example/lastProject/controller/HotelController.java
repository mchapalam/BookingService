package com.example.lastProject.controller;

import com.example.lastProject.dto.HotelDto;
import com.example.lastProject.dto.PaginatedResponse;
import com.example.lastProject.dto.UpsertHotel;
import com.example.lastProject.dto.UserDto;
import com.example.lastProject.repository.HotelRepository;
import com.example.lastProject.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public PaginatedResponse<HotelDto> findAll(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size){
        return hotelService.findAll(page, size);
    }

    @GetMapping("/filter")
    public PaginatedResponse<HotelDto> filter(@RequestParam(required = false) UUID id,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) String title,
                                              @RequestParam(required = false) String city,
                                              @RequestParam(required = false) String address,
                                              @RequestParam(required = false) Double distanceFromCenter,
                                              @RequestParam(required = false) Long rating,
                                              @RequestParam(required = false) Long numberRatings,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size) {
        log.info("call filter hotel controler");
        return hotelService.filter(id, name, title, city, address, distanceFromCenter, rating, numberRatings, page ,size);
    }

    @GetMapping("/{id}")
    public HotelDto findById(@PathVariable UUID id){
        return hotelService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public HotelDto create(@RequestBody UpsertHotel upsertHotel){
        return hotelService.create(upsertHotel);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public HotelDto update(@RequestParam UUID id,
                           @RequestBody UpsertHotel upsertHotel){
        return hotelService.update(id, upsertHotel);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public void delete(@RequestParam UUID id){
        hotelService.deleteById(id);
    }

    @GetMapping("/updateRating")
    public HotelDto addRating(@RequestParam UUID id,
                              @RequestParam Short newMark){
        return hotelService.addRating(id, newMark);
    }

    @GetMapping("/filterHotelsByDistanceFromCenterASC")
    public PaginatedResponse<HotelDto> filterHotelsByDistanceFromCenterASC (@RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "5") int size){
        return hotelService.filterHotelsByDistanceFromCenterASC(page, size);
    }

    @GetMapping("/filterHotelsByRatingASC")
    public PaginatedResponse<HotelDto> filterHotelsByRatingASC (@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "5") int size){
        return hotelService.filterHotelsByRatingASC(page, size);
    }

    @GetMapping("/filterHotelsByNumberRatingsASC")
    public PaginatedResponse<HotelDto> filterHotelsByNumberRatingsASC (@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "5") int size){
        return hotelService.filterHotelsByNumberRatingsASC(page, size);
    }

    @GetMapping("/filterHotelsByDistanceFromCenterDESC")
    public PaginatedResponse<HotelDto> filterHotelsByDistanceFromCenterDESC (@RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "5") int size){
        return hotelService.filterHotelsByDistanceFromCenterDESC(page, size);
    }

    @GetMapping("/filterHotelsByRatingDESC")
    public PaginatedResponse<HotelDto> filterHotelsByRatingDESC (@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size){
        return hotelService.filterHotelsByRatingDESC(page, size);
    }

    @GetMapping("/filterHotelsByNumberRatingsDESC")
    public PaginatedResponse<HotelDto> filterHotelsByNumberRatingsDESC (@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "5") int size){
        return hotelService.filterHotelsByNumberRatingsDESC(page, size);
    }
}
