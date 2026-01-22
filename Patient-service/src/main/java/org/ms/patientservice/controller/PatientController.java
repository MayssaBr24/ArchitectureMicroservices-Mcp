package org.ms.patientservice.controller;


import org.ms.patientservice.model.MedicalHistory;
import org.ms.patientservice.model.Patient;
import org.ms.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // GET all patients
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    // GET patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create patient
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.createPatient(patient));
    }

    // PUT update patient
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable String id,
            @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.updatePatient(id, patient));
    }

    // DELETE patient
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    // GET search patients by name
    @GetMapping("/search")
    public ResponseEntity<List<Patient>> searchPatients(
            @RequestParam String nom) {
        return ResponseEntity.ok(patientService.searchPatientsByName(nom));
    }

    // GET patients by blood type
    @GetMapping("/blood-type/{type}")
    public ResponseEntity<List<Patient>> getPatientsByBloodType(
            @PathVariable String type) {
        return ResponseEntity.ok(patientService.getPatientsByBloodType(type));
    }

    // POST add allergy to patient
    @PostMapping("/{id}/allergies")
    public ResponseEntity<Patient> addAllergy(
            @PathVariable String id,
            @RequestParam String allergy) {
        return ResponseEntity.ok(patientService.addAllergy(id, allergy));
    }

    // POST add medical history
    @PostMapping("/{id}/medical-history")
    public ResponseEntity<MedicalHistory> addMedicalHistory(
            @PathVariable String id,
            @RequestBody MedicalHistory history) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.addMedicalHistory(id, history));
    }

    // GET patient medical history
    @GetMapping("/{id}/medical-history")
    public ResponseEntity<List<MedicalHistory>> getMedicalHistory(
            @PathVariable String id) {
        return ResponseEntity.ok(patientService.getPatientMedicalHistory(id));
    }
}
