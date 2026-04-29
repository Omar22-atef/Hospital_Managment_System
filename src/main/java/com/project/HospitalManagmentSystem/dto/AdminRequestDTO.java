package com.project.HospitalManagmentSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDTO {
    @NotBlank(message = "Admin Name is required")
    @Size(min = 3, max = 50, message = "Admin Name length must be between 3 and 50")
    private String name;

    @NotBlank(message = "Admin Email is required")
    @Email(message = "Invalid Email format")
    private String email;

    @NotBlank(message = "Admin Password is required")
    private String password;
}
