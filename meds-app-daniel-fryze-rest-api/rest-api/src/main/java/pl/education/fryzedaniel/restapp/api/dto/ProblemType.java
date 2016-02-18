package pl.education.fryzedaniel.restapp.api.dto;

/**
 * Enumeration which defines and describes possible problem type.
 * It is used to map problem type with <tt>Error</tt> code and error details message
 * sent to the client in response message in case of exception scenario.
 * These error codes are specific to this REST API, and describe in details the problem cause.
 * 
 * @author daniel.fryze
 */
public enum ProblemType {

	/** Problem connected with validation errors of input REST messages. */
	VALIDATION_ERROR("1101", "problem.type.validation.error"),

	/** A problem of resource entity duplication. */
	DUPLICATE_ENTITY("1102", "problem.type.duplicate.entity"),

	/** A problem related to database access: can be JPA or SQL related. */
	DATA_RELATED_ERROR("1103", "problem.type.data.related.error"),

	/** Any other internal server problem caused by exception thrown while processing. */
	SERVER_INTERNAL_TECHNICAL_ERROR("1104", "problem.type.server.technical.error");

	/** The problem code - a number specifying the real problem cause. */
	private String code;

	/** The prolbem's default message - a message describing in detail the problem. */
	private String message;

	ProblemType(final String code, final String message) {
		this.code = code;
		this.message = message;
	}

	// GETTERS AND SETTERS

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}