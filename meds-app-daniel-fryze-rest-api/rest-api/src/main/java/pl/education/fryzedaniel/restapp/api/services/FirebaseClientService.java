package pl.education.fryzedaniel.restapp.api.services;

import static pl.education.fryzedaniel.restapp.api.utilities.AppConstants.FB_COUNTER_PROP;
import static pl.education.fryzedaniel.restapp.api.utilities.AppConstants.FB_COUNTER_PROP_REVERSE;
import static pl.education.fryzedaniel.restapp.api.utilities.AppConstants.FB_HTTPS_PREFIX;
import static pl.education.fryzedaniel.restapp.api.utilities.AppConstants.FB_PATH_MEDICATIONS_READ_COUNTER_PREFIX;
import static pl.education.fryzedaniel.restapp.api.utilities.AppConstants.FB_PATH_MEDICATIONS_TOTAL_READ_COUNT;
import static pl.education.fryzedaniel.restapp.api.utilities.AppConstants.FB_UPDATE_DATE_PROP;
import static pl.education.fryzedaniel.restapp.api.utilities.AppConstants.FB_UPDATE_TIMESTAMP_PROP_REVERSE;
import static pl.education.fryzedaniel.restapp.api.utilities.AppConstants.FB_URL_POSTFIX;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.Transaction.Result;

/**
 * The service responsible for communication with <tt>Firebase</tt> service.
 * 
 * @author daniel.fryze
 */
@Service
//@Profile("{dev-integration, integration-tests}")
public class FirebaseClientService implements IFirebaseClientService {

	@Resource(name = "appProperties")
	private Properties appProperties;

	/** {@inheritDoc} */
	@Override
	public void updateReadCount(final String medicationName) {

		Firebase firebaseRef = createFirebaseReference();

		// we use Firebase transaction-capabilities here because this data can be changed frequently
		// by the service and there could be i.e. a 'last commit wins' problems if we don't
		firebaseRef.child(FB_PATH_MEDICATIONS_READ_COUNTER_PREFIX + medicationName)
			.runTransaction(new Transaction.Handler() {

				/** {@inheritDoc} */
				@Override
				public Result doTransaction(MutableData dataUnderChange) {
					// we increment 'counter' property for the node representing requested medication resource
					if (dataUnderChange.child(FB_COUNTER_PROP).getValue() != null) {
						dataUnderChange.child(FB_COUNTER_PROP).setValue((Long) dataUnderChange.child(FB_COUNTER_PROP).getValue() + 1);
					} else {
						dataUnderChange.child(FB_COUNTER_PROP).setValue(1L);
					}
					// we update the 'update-date'property for the node representing requested medication resource
					long currentTime = Calendar.getInstance().getTime().getTime();
					// workarounds for not working descending order with Firebase Arrays
					dataUnderChange.child(FB_UPDATE_TIMESTAMP_PROP_REVERSE).setValue((0 - currentTime) + Long.MAX_VALUE);
					dataUnderChange.child(FB_COUNTER_PROP_REVERSE).setValue(
						(0 - (Long) dataUnderChange.child(FB_COUNTER_PROP).getValue()) + 100000);
					dataUnderChange.child(FB_UPDATE_DATE_PROP).setValue(
						new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(currentTime));
					return Transaction.success(dataUnderChange);
				}
	
				/** {@inheritDoc} */
				@Override
				public void onComplete(FirebaseError error, boolean isCommited, DataSnapshot dataValue) {
					logFirebaseOperationStatus(error, isCommited, dataValue);
				}
			});

		// we use Firebase transaction-capabilities here because this data can be changed frequently
		// by the service and there could be i.e. a 'last commit wins' problems if we don't
		firebaseRef.child(FB_PATH_MEDICATIONS_TOTAL_READ_COUNT)
			.runTransaction(new Transaction.Handler() {

				/** {@inheritDoc} */
				@Override
				public Result doTransaction(MutableData dataUnderChange) {
					if (dataUnderChange.getValue() != null) {
						dataUnderChange.setValue((Long) dataUnderChange.getValue() + 1);
					} else {
						dataUnderChange.setValue(1);
					}
					return Transaction.success(dataUnderChange);
				}
	
				/** {@inheritDoc} */
				@Override
				public void onComplete(FirebaseError error, boolean isCommited, DataSnapshot dataValue) {
					logFirebaseOperationStatus(error, isCommited, dataValue);
				}
			});
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