package com.project.HospitalManagmentSystem.dto;

import com.project.HospitalManagmentSystem.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequestDTO {

    @Email
    @NotBlank
    private String email;

    private UserRole role;
}