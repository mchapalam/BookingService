package com.example.lastProject.controller;

import com.example.lastProject.dto.HotelDto;
import com.example.lastProject.dto.PaginatedResponse;
import com.example.lastProject.dto.RoomDto;
import com.example.lastProject.dto.UpsertRoom;
import com.example.lastProject.service.RoomService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
@Slf4j
public class RoomController {
   private final RoomService roomService;

    @GetMapping
    public PaginatedResponse<RoomDto> findAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size){
        return roomService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public RoomDto findById(@PathVariable UUID id){
        return roomService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public RoomDto create(@RequestBody UpsertRoom upsertRoom){
        return roomService.create(upsertRoom);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public RoomDto update(@PathVariable UUID id,
                          @RequestBody UpsertRoom upsertRoom){
        return roomService.update(id, upsertRoom);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public void delete(@RequestParam UUID id){
        roomService.deleteById(id);
    }

    @GetMapping("/filterRooms")
    public PaginatedResponse<RoomDto> filterRooms (@RequestParam(required = false) UUID id,
                                                   @RequestParam(required = false) String title,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size){
        return roomService.filterRooms(id, title, page, size);
    }

    @GetMapping("/findRoomsByIdASC")
    public PaginatedResponse<RoomDto> findRoomsByIdASC (@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size){
        return roomService.findRoomsByIdASC(page, size);
    }

    @GetMapping("/findRoomsByIdDESC")
    public PaginatedResponse<RoomDto> findRoomsByIdDESC (@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size){
        return roomService.findRoomsByIdDESC(page, size);
    }

    @GetMapping("/findRoomsByPriceASC")
    public PaginatedResponse<RoomDto> findRoomsByPriceASC (@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size){
        return roomService.findRoomsByPriceASC(page, size);
    }

    @GetMapping("/findRoomsByHotelIdASC")
    public PaginatedResponse<RoomDto> findRoomsByHotelIdASC (@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5") int size){
        return roomService.findRoomsByHotelIdASC(page, size);
    }

    @GetMapping("/findRoomsByHotelIdDESC")
    public PaginatedResponse<RoomDto> findRoomsByHotelIdDESC (@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size){
        return roomService.findRoomsByHotelIdDESC(page, size);
    }

    @GetMapping("/findRoomsByPriceDESC")
    public PaginatedResponse<RoomDto> findRoomsByPriceDESC (@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int size){
        return roomService.findRoomsByPriceDESC(page, size);
    }

    @GetMapping("/findRoomsByCountPeopleASC")
    public PaginatedResponse<RoomDto> findRoomsByCountPeopleASC (@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size){
        return roomService.findRoomsByCountPeopleASC(page, size);
    }

    @GetMapping("/findRoomsByCountPeopleDESC")
    public PaginatedResponse<RoomDto> findRoomsByCountPeopleDESC (@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size){
        return roomService.findRoomsByCountPeopleDESC(page, size);
    }
}
