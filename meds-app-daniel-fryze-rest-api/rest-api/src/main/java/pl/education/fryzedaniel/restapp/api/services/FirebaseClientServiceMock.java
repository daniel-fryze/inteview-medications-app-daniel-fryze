package pl.education.fryzedaniel.restapp.api.services;

import static pl.education.fryzedaniel.restapp.api.utilities.AppConstants.FB_HTTPS_PREFIX;
import static pl.education.fryzedaniel.restapp.api.utilities.AppConstants.FB_URL_POSTFIX;

import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * The service responsible for communication with <tt>Firebase</tt> service.
 * 
 * @author daniel.fryze
 */
@Service
@Profile("{dev-mock, unit-tests}")
public class FirebaseClientServiceMock implements IFirebaseClientService {

	@Resource(name = "appProperties")
	private Properties appProperties;

	/** {@inheritDoc} */
	@Override
	public void updateReadCount(final String medicationName) {
	}

	/**
	 * Logs the status of Firebase related operation. Because 'storing read count' for our medications
	 * is only an additional, secondary, not critical feature, we don't want to let FIREBASE-related 
	 * errors stop our application or make client's requests failed, but on the other side we CAN'T 
	 * loose the information about the problem cause, we just log them here
	 * 
	 * @param error object with error details for the operation (if successful it is <tt>null</tt>)
	 * @param isCommited <tt>true</tt> if the operation was committed, <tt>false</tt> otherwise
	 * @param dataValue object with details about value being updated by the transaction
	 */
	private void logFirebaseOperationStatus(FirebaseError error, boolean isCommited, DataSnapshot dataValue) {
		if (error != null) {
			Logger.getLogger(this.getClass()).debug(
				"Firebase operation for value at key [" + dataValue.getKey() + "] failed.");
			Logger.getLogger(this.getClass()).debug(
				"Error code [" + error.getCode() + "], error details [" + error.getDetails() + "].");
			Logger.getLogger(this.getClass()).debug(
				"Data at key [" + dataValue.getKey() + "] remains value [" + dataValue.getValue() + "].");
		}
	}

	/**
	 * Creates the Firebase reference based on configuration values from application configuration.
	 * 
	 * @return fully initialized Firebase reference object
	 */
	private Firebase createFirebaseReference() {
		Firebase firebaseRef = new Firebase(
			FB_HTTPS_PREFIX + appProperties.getProperty("firebase.application.name") + FB_URL_POSTFIX);
		return firebaseRef;
	}

}