package com.sante.appointment.controller;

import com.sante.appointment.model.Appointment;
import com.sante.appointment.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@Slf4j
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // 1. GET tous les rendez-vous
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        log.info("GET /api/appointments - Récupération de tous les rendez-vous");
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    // 2. GET rendez-vous par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable String id) {
        log.info("GET /api/appointments/{}", id);
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            return ResponseEntity.ok(appointment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 3. POST créer un nouveau rendez-vous
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Map<String, String> request) {
        log.info("POST /api/appointments - Création d'un rendez-vous");

        try {
            // Validation des champs requis
            String patientId = request.get("patientId");
            String patientName = request.get("patientName");
            String dateTimeStr = request.get("dateTime");
            String type = request.get("type");
            String doctorName = request.get("doctorName");
            String department = request.get("department");

            if (patientId == null || patientName == null || dateTimeStr == null ||
                    type == null || doctorName == null || department == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Tous les champs sont requis: patientId, patientName, dateTime, type, doctorName, department"));
            }

            // Conversion de la date
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

            // Création du rendez-vous
            Appointment appointment = appointmentService.createAppointment(
                    patientId, patientName, dateTime, type, doctorName, department
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(appointment);

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Format de date invalide. Utilisez: yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur lors de la création: " + e.getMessage()));
        }
    }

    // 4. PUT mettre à jour le statut
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateAppointmentStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> request) {

        log.info("PUT /api/appointments/{}/status", id);

        String status = request.get("status");
        if (status == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Le champ 'status' est requis"));
        }

        try {
            String result = appointmentService.updateAppointmentStatus(id, status);
            return ResponseEntity.ok(Map.of("message", result));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 5. GET rendez-vous d'un patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable String patientId) {
        log.info("GET /api/appointments/patient/{}", patientId);
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(patientId));
    }

    // 6. GET rendez-vous du jour
    @GetMapping("/today")
    public ResponseEntity<List<Appointment>> getTodayAppointments() {
        log.info("GET /api/appointments/today");
        return ResponseEntity.ok(appointmentService.getTodayAppointments());
    }

    // 7. GET rendez-vous à venir
    @GetMapping("/upcoming")
    public ResponseEntity<List<Appointment>> getUpcomingAppointments() {
        log.info("GET /api/appointments/upcoming");
        return ResponseEntity.ok(appointmentService.getUpcomingAppointments());
    }

    // 8. GET nombre de rendez-vous
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countAppointments() {
        log.info("GET /api/appointments/count");
        long count = appointmentService.countAppointments();
        return ResponseEntity.ok(Map.of("count", count));
    }

    // 9. GET résumé des rendez-vous
    @GetMapping("/summary")
    public ResponseEntity<String> getAppointmentSummary() {
        log.info("GET /api/appointments/summary");
        return ResponseEntity.ok(appointmentService.getAppointmentSummary());
    }

    // 10. GET informations du service
    @GetMapping("/info")
    public ResponseEntity<String> getServiceInfo() {
        log.info("GET /api/appointments/info");
        String info = """
            📍 SERVICE DE RENDEZ-VOUS MÉDICAUX
            ==================================
            
            Endpoints REST disponibles:
            
            1. GET  /api/appointments           - Tous les rendez-vous
            2. GET  /api/appointments/{id}      - Rendez-vous par ID
            3. POST /api/appointments           - Créer un rendez-vous
            4. PUT  /api/appointments/{id}/status - Mettre à jour le statut
            
            5. GET  /api/appointments/patient/{id} - Rendez-vous d'un patient
            6. GET  /api/appointments/today     - Rendez-vous du jour
            7. GET  /api/appointments/upcoming  - Rendez-vous à venir
            8. GET  /api/appointments/count     - Nombre de rendez-vous
            9. GET  /api/appointments/summary   - Résumé
            10.GET  /api/appointments/info      - Cette info
            
            Format date pour création: yyyy-MM-dd HH:mm
            """;
        return ResponseEntity.ok(info);
    }

    // 11. DELETE supprimer un rendez-vous
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable String id) {
        log.info("DELETE /api/appointments/{}", id);
        // Note: Pour simplifier, on ne gère pas la suppression dans le service statique
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(Map.of("error", "Suppression non implémentée dans la version simple"));
    }

    // 12. Endpoint de santé
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "appointment-service",
                "timestamp", LocalDateTime.now().toString(),
                "appointments", String.valueOf(appointmentService.countAppointments())
        ));
    }
}