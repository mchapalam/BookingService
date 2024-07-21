package com.example.lastProject.mapper;

import com.example.lastProject.dto.HotelDto;
import com.example.lastProject.dto.UpsertHotel;
import com.example.lastProject.dto.UpsertUser;
import com.example.lastProject.dto.UserDto;
import com.example.lastProject.entity.Hotel;
import com.example.lastProject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roleType", source = "rolesType")
    UserDto toDto(User user);

    User toEntity(UpsertUser upsertUser);
}
