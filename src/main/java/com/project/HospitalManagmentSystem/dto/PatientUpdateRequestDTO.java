package com.project.HospitalManagmentSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientUpdateRequestDTO {
    @Size(min = 3, max = 50, message = "Name Must be between 3 to 50 characters")
    private String name;
    @Size(min = 11, max = 11, message = "Phone Number Must Be 11 Numbers")
    private String phone;
    private LocalDate dateOfBirth;
}
