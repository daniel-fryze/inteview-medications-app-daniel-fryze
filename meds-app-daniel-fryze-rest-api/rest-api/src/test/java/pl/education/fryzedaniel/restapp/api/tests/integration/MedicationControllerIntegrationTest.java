package pl.education.fryzedaniel.restapp.api.tests.integration;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import pl.education.fryzedaniel.restapp.ConfigurationTestsIntegration;
import pl.education.fryzedaniel.restapp.api.tests.IntegrationTestMarker;
import pl.education.fryzedaniel.restapp.model.entities.Medication;
import pl.education.fryzedaniel.restapp.model.repositories.MedicationRepository;

@SpringApplicationConfiguration(classes = ConfigurationTestsIntegration.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("integration-tests")
@Category(IntegrationTestMarker.class)
public class MedicationControllerIntegrationTest {

	private MediaType contentType = new MediaType(
		MediaType.APPLICATION_JSON.getType(),
		MediaType.APPLICATION_JSON.getSubtype(),
		Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private List<Medication> medicationList = new ArrayList<>();

	@Mock
	private MedicationRepository medicationRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

//	@Autowired
//    void setConverters(HttpMessageConverter<?>[] converters) {
//
//        this.mappingJackson2HttpMessageConverter = 
//        	Arrays.asList(converters)
//        		.stream()
//        		.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
//        		.findAny()
//        		.get();
//
//        Assert.assertNotNull("the JSON message converter must not be null",
//            this.mappingJackson2HttpMessageConverter);
//    }

    @Before
    public void setup() throws Exception {

//        this.mockMvc = webAppContextSetup(webApplicationContext).build();
//        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void readAllMedications() throws Exception {

//    	Mockito
//    		.when(medicationRepository.findAll())
//    		.thenReturn(new ArrayList<Medication>());

//        mockMvc.perform(get("/medication"))
//        	.andExpect(status().isOk())
//        	.andExpect(content().contentType(contentType))
//        	.andExpect(jsonPath("$", hasSize(0)));
    }

}