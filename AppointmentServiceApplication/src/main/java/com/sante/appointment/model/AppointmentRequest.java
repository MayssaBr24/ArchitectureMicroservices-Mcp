package com.sante.appointment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {
    private String patientId;
    private String patientName;
    private LocalDateTime dateTime;
    private String type;
    private String doctorName;
    private String department;
    private String notes;
}