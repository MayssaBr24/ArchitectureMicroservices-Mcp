package org.ms.patientservice.repository;



import org.ms.patientservice.model.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, String> {

    List<MedicalHistory> findByPatientId(String patientId);

    List<MedicalHistory> findByPatientIdOrderByDateDesc(String patientId);
}