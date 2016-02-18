package pl.education.fryzedaniel.restapp.api.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.apache.log4j.Logger;
import pl.education.fryzedaniel.restapp.api.dto.MessageDTO;
import pl.education.fryzedaniel.restapp.api.services.MessageDetailsFactory;

/**
 * Component which services all possible error scenarios. It is responsible for handling exceptions
 * possibly thrown during REST requests processing and creating the appropriate HTTP response objects
 * for them: containing both appropriate HTTP Status Code and response body with error details.
 * 
 * @author daniel.fryze
 */
@ControllerAdvice
public class ErrorHandler {

	@Autowired
	private MessageDetailsFactory messageDetailsFactory;

	/**
	 * Method for handling <code>MethodArgumentNotValidException</code> exception.
	 * This exception is thrown in case of <tt>Validation</tt> errors for REST input methods.
	 * 
	 * @param ex the exception which caused the problem with validation details
	 * @return the response message object with HTTP Status code 400 and validation details in body
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MessageDTO processValidationError(final MethodArgumentNotValidException ex) {

		Logger.getLogger(this.getClass()).error(ex.getMessage(), ex);

		FieldError fieldError = ex.getBindingResult().getFieldError();

		MessageDTO message = null;
		if (fieldError != null) {
			message = messageDetailsFactory.generateMessageForValidationError(
				fieldError.getDefaultMessage(), fieldError.getField(), fieldError.getRejectedValue());
		}
		return message;
	}

	/**
	 * Method for handling <code>DataAccessException</code> exception.
	 * This exception is thrown in case of any problem associated with <tt>Database access</tt>.
	 * 
	 * @param ex the exception which caused the problem with database access problem details
	 * @return the response message object with HTTP Status code 500 and exception details in body
	 */
	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public MessageDTO processException(final DataAccessException ex) {

		Logger.getLogger(this.getClass()).error(ex.getMessage(), ex);

		MessageDTO message = null;
		if (ex != null) {
			message = messageDetailsFactory.generateMessageForDataRelatedErrors(ex.getMessage());
		}

		return message;
	}

	/**
	 * Method for handling any other exception. Used for all other error scenarios.
	 * 
	 * @param ex the exception which caused the problem with problem details
	 * @return the response message object with HTTP Status code 500 and exception details in body
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public MessageDTO processExceptionE(final Exception ex) {

		Logger.getLogger(this.getClass()).error(ex.getMessage(), ex);

		MessageDTO message = null;
		if (ex != null) {
			message = messageDetailsFactory.generateMessageForOtherServerError(ex.getMessage());
		}

		return message;
	}

}