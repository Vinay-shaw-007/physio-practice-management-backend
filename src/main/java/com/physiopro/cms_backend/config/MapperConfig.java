package com.physiopro.cms_backend.config;

import com.physiopro.cms_backend.dto.DoctorDto;
import com.physiopro.cms_backend.dto.PatientDto;
import com.physiopro.cms_backend.model.Doctor;
import com.physiopro.cms_backend.model.Patient;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // 1. Set Strategy to Strict (Helps avoid ambiguity)
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // 2. Configure Patient -> PatientDto Mapping
        mapper.typeMap(Patient.class, PatientDto.class).addMappings(mapping -> {
            // Map User fields to DTO base fields
            mapping.map(src -> src.getUser().getId(), PatientDto::setId); // User ID
            mapping.map(Patient::getId, PatientDto::setPatientId);    // Patient Profile ID
            mapping.map(src -> src.getUser().getName(), PatientDto::setName);
            mapping.map(src -> src.getUser().getEmail(), PatientDto::setEmail);
            mapping.map(src -> src.getUser().getPhone(), PatientDto::setPhone);
            mapping.map(src -> src.getUser().getAvatar(), PatientDto::setAvatar);
            mapping.map(src -> src.getUser().getRole(), PatientDto::setRole);
            mapping.map(src -> src.getUser().isActive(), PatientDto::setActive);
        });

        // 3. Configure Doctor -> DoctorDto Mapping
        mapper.typeMap(Doctor.class, DoctorDto.class).addMappings(mapping -> {
            // Map User fields to DTO base fields
            mapping.map(src -> src.getUser().getId(), DoctorDto::setId); // User ID
            mapping.map(Doctor::getId, DoctorDto::setDoctorId);     // Doctor Profile ID
            mapping.map(src -> src.getUser().getName(), DoctorDto::setName);
            mapping.map(src -> src.getUser().getEmail(), DoctorDto::setEmail);
            mapping.map(src -> src.getUser().getPhone(), DoctorDto::setPhone);
            mapping.map(src -> src.getUser().getAvatar(), DoctorDto::setAvatar);
            mapping.map(src -> src.getUser().getRole(), DoctorDto::setRole);
            mapping.map(src -> src.getUser().isActive(), DoctorDto::setActive);
        });

        return mapper;
    }

}
