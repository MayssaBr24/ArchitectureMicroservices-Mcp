package org.ms.patientservice.mcp;


import org.ms.patientservice.model.MedicalHistory;
import org.ms.patientservice.model.Patient;
import org.ms.patientservice.service.PatientService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class PatientMCPService {

    @Autowired
    private PatientService patientService;

    @Tool(name = "get_all_patients",
            description = "Obtenir la liste complète de tous les patients")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @Tool(name = "search_patients_by_name",
            description = "Rechercher des patients par nom ou prénom")
    public List<Patient> searchPatientsByName(String nom) {
        return patientService.searchPatientsByName(nom);
    }

    @Tool(name = "get_patient_by_id",
            description = "Obtenir les détails d'un patient par son ID")
    public Patient getPatientById(String patientId) {
        return patientService.getPatientById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));
    }

    @Tool(name = "create_new_patient",
            description = "Créer un nouveau patient")
    public Patient createPatient(
            String nom,
            String prenom,
            String dateNaissance,
            String email,
            String groupeSanguin) {

        Patient patient = new Patient();
        patient.setNom(nom);
        patient.setPrenom(prenom);
        patient.setDateNaissance(LocalDate.parse(dateNaissance));
        patient.setEmail(email);
        patient.setGroupeSanguin(groupeSanguin);

        return patientService.createPatient(patient);
    }

    @Tool(name = "add_patient_allergy",
            description = "Ajouter une allergie à un patient")
    public Patient addAllergy(String patientId, String allergy) {
        return patientService.addAllergy(patientId, allergy);
    }

    @Tool(name = "get_patients_by_blood_type",
            description = "Obtenir les patients par groupe sanguin")
    public List<Patient> getPatientsByBloodType(String groupeSanguin) {
        return patientService.getPatientsByBloodType(groupeSanguin);
    }

    @Tool(name = "calculate_patient_age",
            description = "Calculer l'âge d'un patient en années")
    public int calculatePatientAge(String patientId) {
        Patient patient = getPatientById(patientId);
        return Period.between(patient.getDateNaissance(), LocalDate.now()).getYears();
    }

    @Tool(name = "add_medical_history",
            description = "Ajouter un historique médical pour un patient")
    public MedicalHistory addMedicalHistory(
            String patientId,
            String diagnostic,
            String traitement,
            String medecin) {

        MedicalHistory history = new MedicalHistory();
        history.setDate(LocalDate.now());
        history.setDiagnostic(diagnostic);
        history.setTraitement(traitement);
        history.setMedecin(medecin);

        return patientService.addMedicalHistory(patientId, history);
    }

    @Tool(name = "get_patient_medical_history",
            description = "Obtenir l'historique médical d'un patient")
    public List<MedicalHistory> getMedicalHistory(String patientId) {
        return patientService.getPatientMedicalHistory(patientId);
    }

    @Tool(name = "get_patient_count",
            description = "Obtenir le nombre total de patients")
    public long getPatientCount() {
        return patientService.getAllPatients().size();
    }
}
