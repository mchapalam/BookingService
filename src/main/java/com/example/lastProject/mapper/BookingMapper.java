package com.example.lastProject.mapper;

import com.example.lastProject.dto.BookingDto;
import com.example.lastProject.dto.UpsertBooking;
import com.example.lastProject.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "user.id", target = "userId")
    BookingDto toDto(Booking booking);

    @Mapping(source = "roomId", target = "room.id")
    @Mapping(source = "userId", target = "user.id")
    Booking toEntity(UpsertBooking upsertBooking);
}
