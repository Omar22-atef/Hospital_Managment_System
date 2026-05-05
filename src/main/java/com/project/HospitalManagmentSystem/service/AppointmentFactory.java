package com.project.HospitalManagmentSystem.service;

import com.project.HospitalManagmentSystem.entity.Appointment;
import com.project.HospitalManagmentSystem.entity.Doctor;
import com.project.HospitalManagmentSystem.entity.Patient;
import com.project.HospitalManagmentSystem.enums.AppointmentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class AppointmentFactory {

    public Appointment createAppointment(Patient patient, Doctor doctor, LocalDate date, LocalTime time) {
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(date);
        appointment.setAppointmentTime(time);


        appointment.setStatus(AppointmentStatus.PENDING);

        return appointment;
    }
}