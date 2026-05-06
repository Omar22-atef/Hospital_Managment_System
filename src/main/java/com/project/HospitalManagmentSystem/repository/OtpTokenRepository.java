package com.project.HospitalManagmentSystem.repository;

import com.project.HospitalManagmentSystem.entity.OtpToken;
import com.project.HospitalManagmentSystem.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {

    Optional<OtpToken> findByEmailAndUserRoleAndIsUsedFalse(String email, UserRole userRole);

    void deleteAllByEmailAndUserRole(String email, UserRole userRole);
}