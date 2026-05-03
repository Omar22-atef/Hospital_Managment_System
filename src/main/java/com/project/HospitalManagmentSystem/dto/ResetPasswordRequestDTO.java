package com.project.HospitalManagmentSystem.dto;

import com.project.HospitalManagmentSystem.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequestDTO {

    @NotBlank
    private String otpCode;

    @NotBlank
    private String newPassword;

    private UserRole role;

    private String email;
}