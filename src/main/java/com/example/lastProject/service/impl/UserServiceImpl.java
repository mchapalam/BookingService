package com.example.lastProject.service.impl;

import com.example.lastProject.configuration.model.*;
import com.example.lastProject.dto.*;
import com.example.lastProject.entity.Hotel;
import com.example.lastProject.entity.Room;
import com.example.lastProject.entity.User;
import com.example.lastProject.exception.AlreadyExitsException;
import com.example.lastProject.mapper.UserMapper;
import com.example.lastProject.model.RoleType;
import com.example.lastProject.repository.UserRepository;
import com.example.lastProject.security.SecurityService;
import com.example.lastProject.service.UserService;
import com.example.lastProject.utilis.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final SecurityService securityService;
    private final UserRepository userRepository;


    @Override
    public PaginatedResponse<UserDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage = userRepository.findAll(pageable);

        List<UserDto> userDtos = userPage.getContent()
                .stream()
                .map(userMapper::toDto)
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                page,
                size
        );

        return new PaginatedResponse<>(userDtos, paginationInfo);
    }

    @Override
    public UserDto findById(UUID id) {
        return userRepository.findById(id)
                .stream().map(userMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));
    }

    @Override
    public UserDto create(User user) {
        return userMapper.toDto(userRepository
                .save(user));


    }

    @Override
    public UserDto update(UUID id, UpsertUser upsertUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));

        BeanUtils.copyNonNullProperties(userMapper
                .toEntity(upsertUser), user);

        return userMapper.toDto(
                userRepository.save(user)
        );
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public AuthResponse authUser(LoginRequest loginRequest) {
        log.info("Call signin api");
        return securityService.authUser(loginRequest);
    }

    @Override
    public AuthResponse register(CreateUserRequest createUserRequest) {
        log.info("Call register api");
        if (userRepository.existsByUsername(createUserRequest.getUsername()))
            throw new AlreadyExitsException("Username invalid");

        if (userRepository.existsByUsername(createUserRequest.getEmail()))
            throw new AlreadyExitsException("Email invalid");

        securityService.register(createUserRequest);

        LoginRequest loginRequest = new LoginRequest(createUserRequest.getUsername(), createUserRequest.getPassword());

        return securityService.authUser(loginRequest);
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        return securityService.tokenRefresh(request);
    }

    @Override
    public SimpleResponse logoutUser(UserDetails userDetails) {
        securityService.logout();
        return new SimpleResponse("User logout. Username is: " + userDetails.getUsername());
    }

    @Override
    public UserDto addRoleAdmin(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));

        user.addRoleType(RoleType.ROLE_ADMIN);

        return userMapper.toDto(
                userRepository.save(user));
    }


}
