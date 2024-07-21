package com.example.lastProject.mapper;

import com.example.lastProject.dto.HotelDto;
import com.example.lastProject.dto.UpsertHotel;
import com.example.lastProject.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    HotelDto toDto(Hotel hotel);

    Hotel toEntity(UpsertHotel upsertHotel);
}
