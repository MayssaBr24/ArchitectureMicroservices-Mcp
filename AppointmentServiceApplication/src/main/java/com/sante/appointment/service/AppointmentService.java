package com.sante.appointment.service;

import com.sante.appointment.model.Appointment;
import com.sante.appointment.model.AppointmentRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {

    private final List<Appointment> appointments = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Données de test STATIQUES
        appointments.add(createAppointmentStatic(
                "P001",
                "Jean Dupont",
                LocalDateTime.now().plusDays(1).withHour(10).withMinute(0),
                "Consultation",
                "Dr. Martin",
                "Cardiologie",
                "Contrôle annuel"
        ));

        appointments.add(createAppointmentStatic(
                "P002",
                "Sophie Martin",
                LocalDateTime.now().plusDays(2).withHour(14).withMinute(30),
                "Urgence",
                "Dr. Bernard",
                "Pédiatrie",
                "Fièvre persistante"
        ));

        appointments.add(createAppointmentStatic(
                "P003",
                "Pierre Bernard",
                LocalDateTime.now().plusHours(3),
                "Suivi",
                "Dr. Dubois",
                "Orthopédie",
                "Suivi fracture"
        ));
    }

    // Méthode privée pour créer un rendez-vous statique
    private Appointment createAppointmentStatic(
            String patientId,
            String patientName,
            LocalDateTime dateTime,
            String type,
            String doctorName,
            String department,
            String notes) {

        return new Appointment(
                UUID.randomUUID().toString(),  // ID unique
                patientId,
                patientName,
                dateTime,
                type,
                doctorName,
                department,
                "SCHEDULED",  // Statut par défaut
                notes
        );
    }

    // Méthode publique pour créer un nouveau rendez-vous (dynamique)
    public Appointment createAppointment(
            String patientId,
            String patientName,
            LocalDateTime dateTime,
            String type,
            String doctorName,
            String department) {

        Appointment appointment = new Appointment(
                UUID.randomUUID().toString(),
                patientId,
                patientName,
                dateTime,
                type,
                doctorName,
                department,
                "SCHEDULED",
                ""
        );

        appointments.add(appointment);
        return appointment;
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments); // Retourne une copie
    }

    public Appointment getAppointmentById(String id) {
        return appointments.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé: " + id));
    }

    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointments.stream()
                .filter(a -> a.getPatientId().equals(patientId))
                .toList();
    }

    public List<Appointment> getTodayAppointments() {
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime todayEnd = todayStart.plusDays(1);

        return appointments.stream()
                .filter(a -> !a.getDateTime().isBefore(todayStart) && a.getDateTime().isBefore(todayEnd))
                .toList();
    }

    public List<Appointment> getUpcomingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        return appointments.stream()
                .filter(a -> a.getDateTime().isAfter(now))
                .toList();
    }

    public long countAppointments() {
        return appointments.size();
    }

    public String updateAppointmentStatus(String id, String status) {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus(status);
        return "Statut mis à jour pour le rendez-vous " + id + ": " + status;
    }

    public String getAppointmentSummary() {
        long total = countAppointments();
        long today = getTodayAppointments().size();
        long upcoming = getUpcomingAppointments().size();

        return String.format("""
            📊 RÉSUMÉ DES RENDEZ-VOUS:
            ---------------------------
            Total: %d rendez-vous
            Aujourd'hui: %d rendez-vous
            À venir: %d rendez-vous
            """, total, today, upcoming);
    }

}