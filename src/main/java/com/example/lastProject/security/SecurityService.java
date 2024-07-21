package com.example.lastProject.security;


import com.example.lastProject.configuration.model.*;
import com.example.lastProject.dto.UserDto;
import com.example.lastProject.entity.User;
import com.example.lastProject.exception.RefreshTokenException;
import com.example.lastProject.mapper.UserMapper;
import com.example.lastProject.model.RefreshToken;
import com.example.lastProject.model.RoleType;
import com.example.lastProject.repository.UserRepository;
import com.example.lastProject.security.jwt.JwtUtils;
import com.example.lastProject.service.RefreshTokenService;
import com.example.lastProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthResponse authUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUserDetails userDetails = (AppUserDetails)  authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());


        return AuthResponse.builder()
                .id(userDetails.getId())
                .token(jwtUtils.generateTokenFromUsername(userDetails.getUsername()))
                .refreshToken(refreshToken.getToken())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }


    public UserDto register(CreateUserRequest createUserRequest){
        User user = User.builder()
                .username(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .build();
        user.setRolesType(Collections.singleton(RoleType.ROLE_USER));

        return userMapper
                .toDto(userRepository
                        .save(user)
                );
    }

    public RefreshTokenResponse tokenRefresh (RefreshTokenRequest request){
        String refreshTokenRequest = request.getRefreshToken();

        return  refreshTokenService.findByRefreshToken(refreshTokenRequest)
                .map(refreshTokenService::checkRefreshToken)
                .map(RefreshToken::getUserId)
                .map(userId -> {
                    User tokenOwner = userRepository.findById(userId).orElseThrow(() ->
                            new RefreshTokenException("Exception trying" + userId));

                    String token = jwtUtils.generateTokenFromUsername(tokenOwner.getUsername());

                    return  new RefreshTokenResponse(token, refreshTokenService.createRefreshToken(userId).getToken());
                }).orElseThrow(() -> new RefreshTokenException(refreshTokenRequest, "Error")
                );
    }

    public void logout(){
        var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("{}", authentication);
        if (currentPrincipal instanceof  AppUserDetails userDetails){
            UUID userId = userDetails.getId();

            refreshTokenService.deleteByUserId(userId);
        }
    }
}
