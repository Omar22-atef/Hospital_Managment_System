package com.project.HospitalManagmentSystem.dto;

import com.project.HospitalManagmentSystem.enums.DaysOfWeek;
import com.project.HospitalManagmentSystem.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RegisterRequestDTO {

    // Common fields for all roles
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private UserRole role;

    // Patient fields
    private String phone;
    private LocalDate dateOfBirth;

    // Doctor fields
    private String specialization;
    private DaysOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}