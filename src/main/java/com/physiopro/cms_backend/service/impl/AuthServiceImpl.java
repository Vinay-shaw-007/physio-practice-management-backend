package com.physiopro.cms_backend.service.impl;

import com.physiopro.cms_backend.dto.DoctorDto;
import com.physiopro.cms_backend.dto.PatientDto;
import com.physiopro.cms_backend.dto.RegisterRequest;
import com.physiopro.cms_backend.dto.UserDto;
import com.physiopro.cms_backend.model.Doctor;
import com.physiopro.cms_backend.model.Patient;
import com.physiopro.cms_backend.model.Role;
import com.physiopro.cms_backend.model.User;
import com.physiopro.cms_backend.security.JwtService;
import com.physiopro.cms_backend.service.AuthService;
import com.physiopro.cms_backend.service.DoctorService;
import com.physiopro.cms_backend.service.PatientService;
import com.physiopro.cms_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @Override
    public String[] login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken, refreshToken};
    }

    @Override
    @Transactional
    public UserDto registerPatient(RegisterRequest request) {
        // 1. Validation
        if (userService.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        // 2. Create User (FORCE ROLE = PATIENT)
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.PATIENT); // <--- Strict Enforcement
        user.setActive(true);

        User savedUser = userService.save(user);

        // 3. Create Patient Profile
        patientService.createPatientProfile(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto onboardDoctor(RegisterRequest request) {
        // 1. Validation
        if (userService.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        // 2. Create User (FORCE ROLE = DOCTOR)
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.DOCTOR); // <--- Strict Enforcement
        user.setActive(true);

        User savedUser = userService.save(user);

        // 3. Create Doctor Profile
        doctorService.createDoctorProfile(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        // 1. Extract User ID from the token (This also validates the signature)
        UUID userId = jwtService.getUserIdFromToken(refreshToken);

        // 2. Check if user still exists in DB
        User user = userService.getUserById(userId);

        // 3. Generate a NEW Access Token
        return jwtService.generateAccessToken(user);
    }

    @Override
    public UserDto getMyProfile(User user) {

        // 1. Return the Specific Profile based on Role using user.getId()
        if (user.getRole() == Role.DOCTOR) {
            Doctor doctor = doctorService.findByUserId(user.getId());
            // Maps Doctor Entity -> DoctorDto (includes User fields via MapperConfig)
            return modelMapper.map(doctor, DoctorDto.class);
        }

        else if (user.getRole() == Role.PATIENT) {
            Patient patient = patientService.findByUserId(user.getId());
            // Maps Patient Entity -> PatientDto
            return modelMapper.map(patient, PatientDto.class);
        }

        // 3. Fallback for Admin or basic Users
        return modelMapper.map(user, UserDto.class);
    }
}