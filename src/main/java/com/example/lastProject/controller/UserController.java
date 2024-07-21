package com.example.lastProject.controller;


import com.example.lastProject.configuration.model.*;
import com.example.lastProject.dto.PaginatedResponse;
import com.example.lastProject.dto.UpsertUser;
import com.example.lastProject.dto.UserDto;
import com.example.lastProject.exception.AlreadyExitsException;
import com.example.lastProject.repository.UserRepository;
import com.example.lastProject.security.SecurityService;
import com.example.lastProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public PaginatedResponse<UserDto> findAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size){
        return userService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable UUID id){
        return userService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public UserDto update(@RequestParam UUID id,
                          @RequestBody UpsertUser upsertUser){
        return userService.update(id, upsertUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public void delete(@RequestParam UUID id){
        userService.deleteById(id);
    }

    @PostMapping("/signin")
    public AuthResponse authUser(@RequestBody LoginRequest loginRequest){
        return userService.authUser(loginRequest);
    }

    @PostMapping("/register")
    public AuthResponse register (@RequestBody CreateUserRequest createUserRequest){
        return userService.register(createUserRequest);
    }

    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest request){
        return userService.refreshToken(request);
    }


    @PostMapping("/logout")
    public SimpleResponse logoutUser(@AuthenticationPrincipal UserDetails userDetails){
        return userService.logoutUser(userDetails);
    }

    @GetMapping("/add_role_admin={id}")
    public UserDto addRoleAdmin(@PathVariable UUID id){
        return userService.addRoleAdmin(id);
    }
}
