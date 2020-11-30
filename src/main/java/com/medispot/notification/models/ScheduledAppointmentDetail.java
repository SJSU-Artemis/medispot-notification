package com.medispot.notification.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduledAppointmentDetail {
    String name;
    String phoneNumber;
    String email;
    String testCenterName;
    String testCenterAddress;
    LocalDate appointmentDate;
    String appointmentTime;
}
