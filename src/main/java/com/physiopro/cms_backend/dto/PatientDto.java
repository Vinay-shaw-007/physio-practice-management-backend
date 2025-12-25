package com.physiopro.cms_backend.dto;

import com.physiopro.cms_backend.model.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true) // Includes UserDto fields in hash/equals
public class PatientDto extends UserDto {

    private UUID patientId; // The ID from the 'patients' table

    private LocalDate dateOfBirth;
    private Gender gender;
    private String bloodGroup;
    private String address;
    private String medicalHistory;

    // Physical Stats
    private Double height;
    private Double weight;

    // Collections
    private List<String> allergies;
    private List<String> medications;

    // Nested Object
    private EmergencyContactDto emergencyContact;
}