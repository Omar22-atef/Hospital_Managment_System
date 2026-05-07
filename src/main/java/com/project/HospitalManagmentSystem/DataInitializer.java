package com.project.HospitalManagmentSystem;

import com.project.HospitalManagmentSystem.entity.Admin;
import com.project.HospitalManagmentSystem.repository.AdminRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        // ── Seed Admin ──────────────────────────────────────────────
        if (adminRepository.count() == 0) {
            Admin admin = Admin.builder()
                    .name("Super Admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("123456"))
                    .build();

            adminRepository.save(admin);
            System.out.println("Admin created!");
        }

        
    }
}
