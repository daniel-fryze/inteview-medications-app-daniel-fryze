package pl.education.fryzedaniel.restapp.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@JsonInclude(Include.NON_NULL)
public class Medication {

    @Id
    @JsonIgnore
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

    @Column(length = 100, nullable = false, unique = true)
    @NotNull(message = "rest.method.input.message.validation.not.null")
    @Size(max = 100, message = "rest.method.input.message.validation.size.exceeded")
	private String name;

    @Column(length = 100)
    @Size(max = 100, message = "rest.method.input.message.validation.size.exceeded")
	private String genericName;

    @Column(length = 1000)
    @Size(max = 1000, message = "rest.method.input.message.validation.size.exceeded")
	private String description;

    @Column(length = 1000)
    @Size(max = 1000, message = "rest.method.input.message.validation.size.exceeded")
    private String dosage;

    @Column(length = 1000)
    @Size(max = 1000, message = "rest.method.input.message.validation.size.exceeded")
	private String interactions;

    @Column(length = 100)
    @Size(max = 100, message = "rest.method.input.message.validation.size.exceeded")
	private String producerName;

	// CONSTRUCTORS

    Medication() { }

    public Medication(final String name, final Date creationDate) {
        this.setName(name);
        this.setCreationDate(creationDate);
    }


	public Medication(Date creationDate, String name, String genericName, 
		String description, String dosage, String interactions, String producerName) {

		this.creationDate = creationDate;
		this.name = name;
		this.genericName = genericName;
		this.description = description;
		this.dosage = dosage;
		this.interactions = interactions;
		this.producerName = producerName;
	}

    // GETTERS AND SETTERS

	public String getGenericName() {
		return genericName;
	}

	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getInteractions() {
		return interactions;
	}

	public void setInteractions(String interactions) {
		this.interactions = interactions;
	}

	@JsonSerialize(using = JsonDateSerializer.class) 
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}