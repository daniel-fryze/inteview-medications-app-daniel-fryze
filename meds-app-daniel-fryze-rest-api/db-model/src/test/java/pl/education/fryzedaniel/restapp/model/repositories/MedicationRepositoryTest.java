//package pl.education.fryzedaniel.restapp.model.repositories;
//
//import java.util.Collection;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import pl.education.fryzedaniel.restapp.model.ApplicationDbModel;
//import pl.education.fryzedaniel.restapp.model.entities.Medication;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(ApplicationDbModel.class)
//public class MedicationRepositoryTest {
//
//	@Autowired
//	MedicationRepository repository;
//
//	@Test
//	public void findsFirstPageOfCities() {
//		Collection<Medication> medications = repository.findAll();
//		System.out.println(medications.size());
//	}
//}