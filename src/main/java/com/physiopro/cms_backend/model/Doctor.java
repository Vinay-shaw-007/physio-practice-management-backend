package com.physiopro.cms_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String specialization;

    private Integer yearsOfExperience;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "is_available")
    private boolean isAvailable = true;

    // --- Arrays ---
    @ElementCollection
    @CollectionTable(name = "doctor_qualifications", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "qualification")
    private List<String> qualifications;

    @ElementCollection
    @CollectionTable(name = "doctor_services", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "service")
    private List<String> services;
}