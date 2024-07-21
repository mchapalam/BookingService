package com.example.lastProject.dto;

import com.example.lastProject.model.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;

    private String username;

    private String password;

    private String email;

    private Set<RoleType> roleType;
}
