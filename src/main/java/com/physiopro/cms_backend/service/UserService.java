package com.physiopro.cms_backend.service;


import com.physiopro.cms_backend.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
    User getUserByEmail(String email);
    User save(User user);
    Boolean existsByEmail(String email);
}