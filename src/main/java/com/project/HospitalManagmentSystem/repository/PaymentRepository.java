package com.project.HospitalManagmentSystem.repository;

import com.project.HospitalManagmentSystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByAppointmentPatientId(Long patientId);
    List<Payment> findByAppointmentPatientEmail(String email);
}
