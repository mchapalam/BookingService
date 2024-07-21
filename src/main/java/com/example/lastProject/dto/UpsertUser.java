package com.example.lastProject.dto;

import com.example.lastProject.model.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertUser {
    private String username;

    private String password;

    private String email;
}
