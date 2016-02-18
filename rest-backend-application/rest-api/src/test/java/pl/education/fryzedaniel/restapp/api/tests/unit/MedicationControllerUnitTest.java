package pl.education.fryzedaniel.restapp.api.tests.unit;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.education.fryzedaniel.restapp.ApplicationIntegrationTests;
import pl.education.fryzedaniel.restapp.ApplicationUnitTests;
import pl.education.fryzedaniel.restapp.api.controllers.MedicationController;
import pl.education.fryzedaniel.restapp.api.tests.UnitTestMarker;
import pl.education.fryzedaniel.restapp.model.entities.Medication;
import pl.education.fryzedaniel.restapp.model.repositories.MedicationRepository;

@SpringApplicationConfiguration(classes = ApplicationUnitTests.class)
@ActiveProfiles("unit-tests")
@RunWith(SpringJUnit4ClassRunner.class)
@Category(UnitTestMarker.class)
public class MedicationControllerUnitTest {

	private MedicationController controller;

	@Mock
	private MedicationRepository medicationRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.controller = new MedicationController(this.medicationRepository);
    }

    @Test
    public void readAllMedications() throws Exception {

    	Mockito
    		.when(medicationRepository.findAll())
    		.thenReturn(new ArrayList<Medication>());

//        Assert.assertEquals(
//        	medicationRepository.findAll().size(),
//        	controller.findAll().size());
    }

}