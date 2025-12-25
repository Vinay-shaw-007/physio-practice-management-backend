package com.physiopro.cms_backend.dto;

import com.physiopro.cms_backend.model.Role;
import lombok.Data;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String name;
    private String email;
    private Role role;

    private String phone;
    private String avatar;
    private boolean isActive;
}