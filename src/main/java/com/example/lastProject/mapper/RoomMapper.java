package com.example.lastProject.mapper;

import com.example.lastProject.dto.RoomDto;
import com.example.lastProject.dto.UpsertRoom;
import com.example.lastProject.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    @Mapping(target = "hotel", source = "hotel")
    RoomDto toDto (Room room);

    @Mapping(target = "hotel.id", source = "hotelId")
    Room toEntity(UpsertRoom upsertRoom);
}
