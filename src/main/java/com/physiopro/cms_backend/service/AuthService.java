package com.physiopro.cms_backend.service;

import com.physiopro.cms_backend.dto.RegisterRequest;
import com.physiopro.cms_backend.dto.UserDto;
import com.physiopro.cms_backend.model.User;

public interface AuthService {
    String[] login(String email, String password);
    String refreshToken(String refreshToken);
    UserDto getMyProfile(User user);

    UserDto registerPatient(RegisterRequest registerRequest); // Public
    UserDto onboardDoctor(RegisterRequest registerRequest);   // Admin Only
}
