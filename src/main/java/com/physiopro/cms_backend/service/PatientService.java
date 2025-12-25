package com.physiopro.cms_backend.service;

import com.physiopro.cms_backend.model.Patient;
import com.physiopro.cms_backend.model.User;

import java.util.Optional;
import java.util.UUID;

public interface PatientService {
    Patient findByUserId(UUID uuid);
}
