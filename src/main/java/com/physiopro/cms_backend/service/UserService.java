package com.physiopro.cms_backend.service;


import com.physiopro.cms_backend.model.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
    User findByEmail(String email);
    User save(User user);
    Boolean existsByEmail(String email);
}