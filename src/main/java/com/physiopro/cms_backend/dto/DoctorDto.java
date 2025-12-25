package com.physiopro.cms_backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class DoctorDto extends UserDto {

    private UUID doctorId; // The ID from the 'doctors' table

    private String specialization;
    private Integer yearsOfExperience;
    private String bio;
    private boolean isAvailable;

    // Arrays
    private List<String> qualifications; // List version if you store multiple
    private List<String> services; // Service Names (or IDs)
}