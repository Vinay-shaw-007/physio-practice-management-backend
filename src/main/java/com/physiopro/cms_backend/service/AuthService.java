package com.physiopro.cms_backend.service;

import com.physiopro.cms_backend.dto.RegisterRequest;
import com.physiopro.cms_backend.dto.UserDto;

public interface AuthService {
    String[] login(String email, String password);
    UserDto register(RegisterRequest registerRequest);
    String refreshToken(String refreshToken);
    UserDto getMyProfile(String email);
}
