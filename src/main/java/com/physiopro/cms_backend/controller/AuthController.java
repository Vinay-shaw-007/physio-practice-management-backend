package com.physiopro.cms_backend.controller;

import com.physiopro.cms_backend.dto.*;
import com.physiopro.cms_backend.model.User;
import com.physiopro.cms_backend.service.AuthService;
import com.physiopro.cms_backend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request,
            HttpServletResponse response
    ) {
        // 1. Perform Login Logic
        String[] tokens = authService.login(request.getEmail(), request.getPassword());
        String accessToken = tokens[0];
        String refreshToken = tokens[1];

        // 2. Create HttpOnly Cookie for Refresh Token
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in Production (HTTPS)
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7); // 7 days

        // 3. Add Cookie to Response
        response.addCookie(cookie);

        // 4. Return Access Token & User Info
        // Note: You might want to fetch the UserDto separately if LoginResponse expects it
        // For now, let's assume LoginResponse just needs the token, or you can fetch the user dto here.
        User user = userService.getUserByEmail(request.getEmail());
        return ResponseEntity.ok(new LoginResponse(accessToken, modelMapper.map(user, UserDto.class)));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        // Spring Security injects the authenticated User object (Principal)
        User user = (User) authentication.getPrincipal();

        // Fetch the full profile using the email from the token
        assert user != null;
        return ResponseEntity.ok(authService.getMyProfile(user.getEmail()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(HttpServletRequest request) {
        // 1. Find the Refresh Token Cookie
        String refreshToken = Arrays.stream(request.getCookies() != null ? request.getCookies() : new Cookie[0])
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the cookies"));

        // 2. Generate New Access Token
        String newAccessToken = authService.refreshToken(refreshToken);

        // 3. Return it
        return ResponseEntity.ok(new LoginResponse(newAccessToken, null));
    }
}