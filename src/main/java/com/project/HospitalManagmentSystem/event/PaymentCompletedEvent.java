package com.project.HospitalManagmentSystem.event;

import com.project.HospitalManagmentSystem.entity.Payment;

public class PaymentCompletedEvent {

    private final Payment payment;

    public PaymentCompletedEvent(Payment payment) {
        this.payment = payment;
    }

    public Payment getPayment() {
        return payment;
    }
}
