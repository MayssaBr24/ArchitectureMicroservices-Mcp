package org.ms.patientservice.repository;




import org.ms.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {

    List<Patient> findByNomContainingIgnoreCase(String nom);

    List<Patient> findByPrenomContainingIgnoreCase(String prenom);

    Optional<Patient> findByEmail(String email);

    List<Patient> findByGroupeSanguin(String groupeSanguin);
}
