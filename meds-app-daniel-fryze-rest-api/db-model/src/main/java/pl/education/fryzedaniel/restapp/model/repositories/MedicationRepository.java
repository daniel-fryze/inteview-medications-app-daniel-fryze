package pl.education.fryzedaniel.restapp.model.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.education.fryzedaniel.restapp.model.entities.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

	Collection<Medication> findByName(String name);
	Collection<Medication> findByNameContaining(String name);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM Medication u WHERE LOWER(u.name) = LOWER(?1)")
    Boolean existsByName(String name);

	@Query("SELECT m.name FROM Medication AS m WHERE LOWER(m.name) = LOWER(?1)")
	Collection<String> findNamesOnlyByName(String name);

	@Query("SELECT m.id, m.name, m.producerName FROM Medication AS m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
	Collection<String[]> findNamesOnlyByNameContaining(String name);
}