package com.project.HospitalManagmentSystem.config;

import com.project.HospitalManagmentSystem.repository.AdminRepository;
import com.project.HospitalManagmentSystem.repository.DoctorRepository;
import com.project.HospitalManagmentSystem.repository.PatientRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.isTokenValid(token)) {
                String email = jwtUtil.extractEmail(token);
                String role = jwtUtil.extractRole(token);

                // Check token exists in database (not logged out)
                boolean tokenExistsInDb = switch (role) {
                    case "ADMIN" -> adminRepository.findByToken(token).isPresent();
                    case "DOCTOR" -> doctorRepository.findByToken(token).isPresent();
                    case "PATIENT" -> patientRepository.findByToken(token).isPresent();
                    default -> false;
                };

                if (tokenExistsInDb) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email, null,
                                    List.of(() -> "ROLE_" + role)
                            );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}