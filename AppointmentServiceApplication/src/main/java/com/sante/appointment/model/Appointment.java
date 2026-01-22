package com.sante.appointment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private String id;
    private String patientId;
    private String patientName;
    private LocalDateTime dateTime;
    private String type;
    private String doctorName;
    private String department;
    private String status;
    private String notes;

    // Getter personnalisé pour la date formatée
    public String getFormattedDateTime() {
        return dateTime.toString(); // Ou utiliser un DateTimeFormatter
    }
}