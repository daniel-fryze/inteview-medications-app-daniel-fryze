package pl.education.fryzedaniel.restapp.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import pl.education.fryzedaniel.restapp.api.dto.ErrorMessageDTO;
import pl.education.fryzedaniel.restapp.api.dto.ProblemType;

/**
 * The factory component responsible for creating appropriate message DTO objects
 * for different error scenarios.
 * 
 * @author daniel.fryze
 */
@Service
public class MessageDetailsFactory {

	// TODO 2: to refactor

	// AUTOWIRED DEPENDENCIES

	private MessageSource messagesRepository;

	@Autowired
	public MessageDetailsFactory(final MessageSource messagesRepository) {
		this.messagesRepository = messagesRepository;
	}

	// FACTORY METHODS

	/**
	 * Generates the message specific to <tt>Data relate errors</tt>.
	 * 
	 * @param messageDetails error details
	 * @return specific <code>MessageDTO</code> object
	 */
	public ErrorMessageDTO generateMessageForDataRelatedErrors(final String messageDetails) {

		ErrorMessageDTO message = new ErrorMessageDTO();
		message.setCode(ProblemType.DATA_RELATED_ERROR.getCode());
		message.setGeneralMessage(messagesRepository.getMessage(ProblemType.DATA_RELATED_ERROR.getMessage(),
			null, LocaleContextHolder.getLocale()));
		message.getDetails().put("detailsMessage", messageDetails);
		return message;
	}

	/**
	 * Generates the message specific to <tt>Duplicate Medication entity</tt> error.
	 * 
	 * @param messageDetails error details
	 * @return specific <code>MessageDTO</code> object
	 */
	public ErrorMessageDTO generateMessageForDuplicateEntity(final String fieldName, final String fieldValue) {

		ErrorMessageDTO message = new ErrorMessageDTO();
		message.setCode(ProblemType.DUPLICATE_ENTITY.getCode());
		message.setGeneralMessage(messagesRepository.getMessage(ProblemType.DUPLICATE_ENTITY.getMessage(),
			new String[] { fieldValue, fieldName }, LocaleContextHolder.getLocale()));
		message.getDetails().put("attributeName", fieldName);
		message.getDetails().put("attributeValue", fieldValue);
		return message;
	}

	/**
	 * Generates the message specific to <tt>Validation</tt> error.
	 * 
	 * @param messageDetails error details
	 * @return specific <code>MessageDTO</code> object
	 */
	public ErrorMessageDTO generateMessageForValidationError(final String detailedMessageCode, final String fieldName,
			final Object fieldValue) {

		ErrorMessageDTO message = new ErrorMessageDTO();
		message.setCode(ProblemType.VALIDATION_ERROR.getCode());

		String rejectedValue = (String) fieldValue;

		message.setGeneralMessage(messagesRepository.getMessage(
			ProblemType.VALIDATION_ERROR.getMessage(), null, LocaleContextHolder.getLocale()));
		message.setDetailedMessage(messagesRepository.getMessage(
			detailedMessageCode, new String[] { fieldName, rejectedValue }, LocaleContextHolder.getLocale()));
		message.getDetails().put("attributeName", fieldName);
		message.getDetails().put("attributeValue", rejectedValue);
		return message;
	}

	/**
	 * Generates the message for generic error (any other not served by above handlers).
	 * 
	 * @param messageDetails error details
	 * @return specific <code>MessageDTO</code> object
	 */
	public ErrorMessageDTO generateMessageForOtherServerError(final String messageDetails) {

		ErrorMessageDTO message = new ErrorMessageDTO();
		message.setCode(ProblemType.SERVER_INTERNAL_TECHNICAL_ERROR.getCode());
		message.setGeneralMessage(messagesRepository.getMessage(ProblemType.SERVER_INTERNAL_TECHNICAL_ERROR.getMessage(),
			null, LocaleContextHolder.getLocale()));
		message.getDetails().put("detailsMessage", messageDetails);
		return message;
	}

}