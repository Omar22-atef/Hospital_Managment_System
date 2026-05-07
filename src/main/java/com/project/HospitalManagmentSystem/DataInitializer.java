package com.project.HospitalManagmentSystem;

import com.project.HospitalManagmentSystem.entity.Admin;
import com.project.HospitalManagmentSystem.entity.Doctor;
import com.project.HospitalManagmentSystem.enums.DaysOfWeek;
import com.project.HospitalManagmentSystem.repository.AdminRepository;
import com.project.HospitalManagmentSystem.repository.DoctorRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        // ── Seed Admin ──────────────────────────────────────────────
        if (adminRepository.count() == 0) {
            Admin admin = Admin.builder()
                    .name("Super Admin")
                    .email("")
                    .password(passwordEncoder.encode("123456"))
                    .build();

            adminRepository.save(admin);
            System.out.println("✅ Admin created!");
        }

        // ── Seed Doctors manually ───────────────────────────────────
        if (doctorRepository.count() == 0) {

            List<Doctor> doctors = List.of(

                    Doctor.builder()
                            .name("Dr. Ahmed Ali")
                            .email("ahmed.ali@hospital.com")
                            .password(passwordEncoder.encode("doctor123"))
                            .specialization("Cardiology")
                            .phone("01001234567")
                            .dayOfWeek(DaysOfWeek.SATURDAY)
                            .startTime(LocalTime.of(9, 0))
                            .endTime(LocalTime.of(15, 0))
                            .build(),

                    Doctor.builder()
                            .name("Dr. Sara Mohamed")
                            .email("sara.mohamed@hospital.com")
                            .password(passwordEncoder.encode("doctor123"))
                            .specialization("Pediatrics")
                            .phone("01009876543")
                            .dayOfWeek(DaysOfWeek.MONDAY)
                            .startTime(LocalTime.of(10, 0))
                            .endTime(LocalTime.of(16, 0))
                            .build(),

                    Doctor.builder()
                            .name("Dr. Omar Hassan")
                            .email("omar.hassan@hospital.com")
                            .password(passwordEncoder.encode("doctor123"))
                            .specialization("Orthopedics")
                            .phone("01112345678")
                            .dayOfWeek(DaysOfWeek.WEDNESDAY)
                            .startTime(LocalTime.of(8, 0))
                            .endTime(LocalTime.of(14, 0))
                            .build()

            );

            doctorRepository.saveAll(doctors);
            System.out.println("✅ " + doctors.size() + " Doctors created!");
        }
    }
}
