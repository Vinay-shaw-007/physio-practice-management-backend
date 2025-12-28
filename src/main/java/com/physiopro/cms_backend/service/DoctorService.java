package com.physiopro.cms_backend.service;

import com.physiopro.cms_backend.model.Doctor;
import com.physiopro.cms_backend.model.User;

import java.util.Optional;
import java.util.UUID;

public interface DoctorService {
    Doctor findByUserId(UUID uuid);
    void createDoctorProfile(User user);
}
