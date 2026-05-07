package com.project.HospitalManagmentSystem.event;

import com.project.HospitalManagmentSystem.entity.Appointment;

public class AppointmentCancelledEvent {

    private final Appointment appointment;

    public AppointmentCancelledEvent(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}