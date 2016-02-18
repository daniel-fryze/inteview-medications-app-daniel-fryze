package pl.education.fryzedaniel.restapp.api.controllers;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import pl.education.fryzedaniel.restapp.api.dto.MessageDTO;
import pl.education.fryzedaniel.restapp.api.services.IFirebaseClientService;
import pl.education.fryzedaniel.restapp.api.services.MessageDetailsFactory;
import pl.education.fryzedaniel.restapp.model.entities.Medication;
import pl.education.fryzedaniel.restapp.model.repositories.MedicationRepository;

/**
 * The REST controller for servicing HTTP requests of Medication database service.
 * 
 * @author daniel.fryze
 */
@CrossOrigin
@RestController
@RequestMapping("/medication")
public class MedicationController {

	@Autowired
	public IFirebaseClientService firebaseService;

	@Autowired
	private MessageDetailsFactory messageDetailsFactory;

	private final MedicationRepository medicationRepository;

	@Autowired
	public MedicationController(final MedicationRepository medicationRepository) {
		this.medicationRepository = medicationRepository;
	}

	// REST BUSINESS METHODS

	/**
	 * The REST method for adding new <tt>Medication</tt> entity to the system. The entity'
	 * attributes are validated based on business rules. Additionally the uniqueness of its name
	 * attribute is checked. In case of success, <tt>201</tt> HTTP Status Code is returned, and the
	 * location of the resource for newly created entity is returned in <tt>Location</tt> response
	 * header. Otherwise, in case of failure appropriate HTTP Status Code is returned based on the
	 * cause of failure.
	 * 
	 * @param newEntity
	 *            the resource entity being added
	 * @param ucBuilder
	 *            injected <tt>UriComponentsBuilder</tt> service
	 * @return <code>ResponseEntity</code> object representing the response sent to the client
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNew(final @RequestBody @Validated Medication newEntity,
			final UriComponentsBuilder ucBuilder) {

		// checking if there is already an existing resource with the same name in the system
		if (medicationRepository.existsByName(newEntity.getName())) {
			return new ResponseEntity<MessageDTO>(
					messageDetailsFactory.generateMessageForDuplicateEntity("name", newEntity.getName()),
					HttpStatus.CONFLICT);
		}
		newEntity.setCreationDate(new Date());
		Medication savedMedication = this.medicationRepository.save(newEntity);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/medication/{id}").buildAndExpand(savedMedication.getId()).toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	/**
	 * The REST method for getting particular entity with given ID. As 'ID' is a unique property of this resource
	 * the method returns either the entity resource with given ID (if it exists) or null otherwise.
	 * 
	 * @param id the identifier value for Medication resource
	 * @return the Medication resource entity with given ID or null if it doesn't exist
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Medication> getById(final @PathVariable Long id) {
		Medication medication = this.medicationRepository.findOne(id);
		firebaseService.updateReadCount(medication.getName());
		return new ResponseEntity<Medication>(medication, HttpStatus.OK);
	}

	/**
	 * The REST method for searching <tt>medication</tt> entities by <tt>name</tt> attribute. The
	 * search can be modified using <tt>partial search</tt> method. <tt>Partial search</tt> means,
	 * that we want to all entities with names containing given pattern to be returned.
	 * 
	 * @param medicationName
	 *            the pattern of name we are searching for
	 * @param partialSearch
	 *            parameter defining if the <tt>partial search</tt> is enabled for this request
	 * @return the collection of <code>Medication</code> entities being a result of the search
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Collection<?> findByName(
			final @RequestParam(value = "namePattern", required = true) String namePattern,
			final @RequestParam(value = "namesOnly", required = false) Optional<Boolean> namesProjection,
			final @RequestParam(value = "partialSearch", required = false) Optional<Boolean> partialSearch) {

		if (!paremeterEnabled(namesProjection)) {
			if (paremeterEnabled(partialSearch)) {
				return this.medicationRepository.findByNameContaining(namePattern);
			} else {
				return this.medicationRepository.findByName(namePattern);
			}	
		} else {
			if (paremeterEnabled(partialSearch)) {
				return this.medicationRepository.findNamesOnlyByNameContaining(namePattern);
			} else {
				return this.medicationRepository.findNamesOnlyByName(namePattern);
			}	
		}
	}

	/**
	 * Helper method. Determines if the given request parameter, represented as
	 * <code>Optional</code> type is 'enabled' for the request ('enabled' means: two conditions are
	 * met: it is present and is set to <code>true</code>.
	 * 
	 * @param parameter
	 *            request parameter being input of the rest service method
	 * @return boolean: <tt>true</tt> - if it's enabled, <tt>false</tt> - otherwise
	 */
	private boolean paremeterEnabled(final Optional<Boolean> parameter) {
		return parameter.isPresent() && parameter.get().booleanValue() == true;
	}

}