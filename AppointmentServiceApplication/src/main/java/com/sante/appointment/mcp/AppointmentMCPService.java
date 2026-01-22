package com.sante.appointment.mcp;

import com.sante.appointment.model.Appointment;
import com.sante.appointment.service.AppointmentService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AppointmentMCPService {

    @Autowired
    private AppointmentService appointmentService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Tool(name = "get_all_appointments",
            description = "Obtenir tous les rendez-vous")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @Tool(name = "get_appointment_by_id",
            description = "Obtenir un rendez-vous par son ID")
    public Appointment getAppointmentById(String appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) {
            throw new RuntimeException("Rendez-vous non trouvé avec ID: " + appointmentId);
        }
        return appointment;
    }

    @Tool(name = "create_appointment",
            description = "Créer un nouveau rendez-vous")
    public Appointment createAppointment(
            String patientId,
            String patientName,
            String dateTime,
            String type,
            String doctorName,
            String department) {

        LocalDateTime dt = LocalDateTime.parse(dateTime, formatter);
        return appointmentService.createAppointment(
                patientId, patientName, dt, type, doctorName, department
        );
    }

    @Tool(name = "get_patient_appointments",
            description = "Obtenir les rendez-vous d'un patient")
    public List<Appointment> getPatientAppointments(String patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    @Tool(name = "get_today_appointments",
            description = "Obtenir les rendez-vous du jour")
    public List<Appointment> getTodayAppointments() {
        return appointmentService.getTodayAppointments();
    }

    @Tool(name = "count_appointments",
            description = "Compter le nombre total de rendez-vous")
    public long countAppointments() {
        return appointmentService.countAppointments();
    }

    @Tool(name = "appointment_service_info",
            description = "Informations sur le service de rendez-vous")
    public String getServiceInfo() {
        long total = appointmentService.countAppointments();
        long today = appointmentService.getTodayAppointments().size();

        return String.format("""
            Service de Rendez-vous Médicaux
            ===============================
            Rendez-vous totaux: %d
            Rendez-vous aujourd'hui: %d
            
            Commandes disponibles:
            - get_all_appointments
            - create_appointment
            - get_today_appointments
            - get_patient_appointments
            """, total, today);
    }
}