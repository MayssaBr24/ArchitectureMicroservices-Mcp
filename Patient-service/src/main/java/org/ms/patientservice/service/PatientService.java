package org.ms.patientservice.service;

import org.ms.patientservice.model.MedicalHistory;
import org.ms.patientservice.model.Patient;
import org.ms.patientservice.repository.MedicalHistoryRepository;
import org.ms.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    // CRUD Patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(String id) {
        return patientRepository.findById(id);
    }

    public Patient createPatient(Patient patient) {
        patient.setDateInscription(LocalDate.now());
        return patientRepository.save(patient);
    }

    public Patient updatePatient(String id, Patient patientDetails) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

        patient.setNom(patientDetails.getNom());
        patient.setPrenom(patientDetails.getPrenom());
        patient.setDateNaissance(patientDetails.getDateNaissance());
        patient.setEmail(patientDetails.getEmail());
        patient.setTelephone(patientDetails.getTelephone());
        patient.setAdresse(patientDetails.getAdresse());
        patient.setGroupeSanguin(patientDetails.getGroupeSanguin());
        patient.setAllergies(patientDetails.getAllergies());

        return patientRepository.save(patient);
    }

    public void deletePatient(String id) {
        patientRepository.deleteById(id);
    }

    // Recherche
    public List<Patient> searchPatientsByName(String nom) {
        return patientRepository.findByNomContainingIgnoreCase(nom);
    }

    public List<Patient> getPatientsByBloodType(String groupeSanguin) {
        return patientRepository.findByGroupeSanguin(groupeSanguin);
    }

    // Gestion des allergies
    @Transactional
    public Patient addAllergy(String patientId, String allergy) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

        List<String> allergies = patient.getAllergies();
        if (!allergies.contains(allergy)) {
            allergies.add(allergy);
            patient.setAllergies(allergies);
            return patientRepository.save(patient);
        }
        return patient;
    }

    // Historique médical
    public MedicalHistory addMedicalHistory(String patientId, MedicalHistory history) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

        history.setPatient(patient);
        return medicalHistoryRepository.save(history);
    }

    public List<MedicalHistory> getPatientMedicalHistory(String patientId) {
        return medicalHistoryRepository.findByPatientIdOrderByDateDesc(patientId);
    }
}