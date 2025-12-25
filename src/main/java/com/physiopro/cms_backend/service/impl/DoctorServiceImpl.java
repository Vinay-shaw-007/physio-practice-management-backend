package com.physiopro.cms_backend.service.impl;

import com.physiopro.cms_backend.exceptions.ResourceNotFoundException;
import com.physiopro.cms_backend.model.Doctor;
import com.physiopro.cms_backend.model.User;
import com.physiopro.cms_backend.repository.DoctorRepository;
import com.physiopro.cms_backend.service.DoctorService;
import com.physiopro.cms_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public Doctor findByUserId(UUID uuid) {
        return doctorRepository.findByUserId(uuid).orElseThrow(() -> new ResourceNotFoundException("Doctor profile not found"));;
    }
}
