package com.project.HospitalManagmentSystem.Repository;

import com.project.HospitalManagmentSystem.Entity.Appointment;
import com.project.HospitalManagmentSystem.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
List<Appointment> findByDoctor(Doctor doctor);
}
