package com.project.HospitalManagmentSystem.controller;

import com.project.HospitalManagmentSystem.entity.Admin;
import com.project.HospitalManagmentSystem.repository.AdminRepository;
import com.project.HospitalManagmentSystem.service.AdminFacadeService;
import com.project.HospitalManagmentSystem.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor

public class AdminController {

    private final AdminFacadeService adminFacade;
    private final AdminRepository adminRepository;

    // GET /api/admin/dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<Object>> getDashboard() {
        return ResponseEntity.ok(new ApiResponse<>("success", adminFacade.getDashboardData()));
    }

    // GET /api/admin/doctors
    @GetMapping("/doctors")
    public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> getAllDoctors() {
        return ResponseEntity.ok(new ApiResponse<>("success", adminFacade.getAllDoctors()));
    }

    @GetMapping("/appointments")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAllAppointments()
    {
        return ResponseEntity.ok(new ApiResponse<>("success", adminFacade.getAllAppointments()));
    }

    // POST /api/admin/doctors (Create)
    @PostMapping("/doctors")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> createDoctor(@RequestBody DoctorRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>("success", adminFacade.addDoctor(dto)));
    }

    // PUT /api/admin/doctors/{id} (Update)
    @PutMapping("/doctors/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> updateDoctor(@PathVariable Long id, @RequestBody DoctorRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>("success", adminFacade.updateDoctor(id, dto)));
    }

    // DELETE /api/admin/doctors/{id}
    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDoctor(@PathVariable Long id) {
        adminFacade.deleteDoctor(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Doctor deleted successfully"));
    }

    // GET /api/admin/patients
    @GetMapping("/patients")
    public ResponseEntity<ApiResponse<List<PatientResponseDTO>>> getAllPatients() {
        return ResponseEntity.ok(new ApiResponse<>("success", adminFacade.getAllPatients()));
    }

    @GetMapping("/me")
    public AdminResponseDTO getAdmin(Authentication authentication) {

        String email = authentication.getName();

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        return new AdminResponseDTO(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getCreatedAt(),
                admin.getUpdatedAt()
        );
    }
}