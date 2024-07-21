package com.example.lastProject.service;

import com.example.lastProject.configuration.model.*;
import com.example.lastProject.dto.*;
import com.example.lastProject.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface UserService {
    PaginatedResponse<UserDto> findAll(int page, int size);

    UserDto findById(UUID id);

    UserDto create(User user);

    UserDto update(UUID id, UpsertUser upsertUser);

    void deleteById(UUID id);

    AuthResponse authUser(LoginRequest loginRequest);

    AuthResponse register (CreateUserRequest createUserRequest);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    SimpleResponse logoutUser(UserDetails userDetails);

    UserDto addRoleAdmin(UUID id);
}
