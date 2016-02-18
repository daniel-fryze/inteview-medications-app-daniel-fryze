package pl.education.fryzedaniel.restapp.api.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A DTO type used for transporting details of error sent in response body (in <tt>JSON</tt> format)
 * in case of any error or problem scenario taking place on the server while processing.
 * 
 * @author daniel.fryze
 */
@JsonInclude(Include.NON_NULL)
public class MessageDTO {

	/** Code specifying the cause of the problem for the client. */
	private String code;

	/** Message describing the general problem's cause. */
	private String generalMessage;

	/** Message describing the problem's cause in detail. */
	private String detailedMessage;

	/** Map which stores arbitrary data in <code>Map</code> which contains details of problem. */
	private Map<String, String> details = new HashMap<String, String>();

	// GETTERS AND SETTERS

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, String> getDetails() {
		return details;
	}

	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

	public String getGeneralMessage() {
		return generalMessage;
	}

	public void setGeneralMessage(String generalMessage) {
		this.generalMessage = generalMessage;
	}

	public String getDetailedMessage() {
		return detailedMessage;
	}

	public void setDetailedMessage(String detailedMessage) {
		this.detailedMessage = detailedMessage;
	}

}