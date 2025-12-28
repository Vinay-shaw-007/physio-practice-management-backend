package com.physiopro.cms_backend.controller;

import com.physiopro.cms_backend.dto.RegisterRequest;
import com.physiopro.cms_backend.dto.UserDto;
import com.physiopro.cms_backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;

    @PostMapping("/onboard-doctor")
    @PreAuthorize("hasRole('ADMIN')") // <--- Only ADMINs can access this
    public ResponseEntity<UserDto> onboardDoctor(@RequestBody @Valid RegisterRequest request) {
        return new ResponseEntity<>(authService.onboardDoctor(request), HttpStatus.CREATED);
    }
}