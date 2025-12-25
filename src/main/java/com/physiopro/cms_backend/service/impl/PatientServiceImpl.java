package com.physiopro.cms_backend.service.impl;

import com.physiopro.cms_backend.exceptions.ResourceNotFoundException;
import com.physiopro.cms_backend.model.Patient;
import com.physiopro.cms_backend.model.User;
import com.physiopro.cms_backend.repository.PatientRepository;
import com.physiopro.cms_backend.service.PatientService;
import com.physiopro.cms_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public Patient findByUserId(UUID uuid) {
        return patientRepository.findByUserId(uuid).orElseThrow(() -> new ResourceNotFoundException("Patient profile not found"));
    }
}
