package pl.education.fryzedaniel.restapp.api.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.Transaction.Result;

import pl.education.fryzedaniel.restapp.api.services.interfaces.IFirebaseClient;
import pl.education.fryzedaniel.restapp.api.utilities.Constants;
import pl.education.fryzedaniel.restapp.api.utilities.Utils;

/**
 * The service responsible for communication with <tt>Firebase</tt> service.
 * 
 * @author daniel.fryze
 */
@Service
@Profile({"default", "dev-integration", "dev-integration-h2-db"})
public class FirebaseClientServiceImpl implements IFirebaseClient {

	@Value("${firebase.storage.application.name}")
	private String firebaseAppName;

	/** {@inheritDoc} */
	@Override
	public void updateReadCount(final String medicationName) {

		Firebase firebaseRef = createFirebaseReference();

		// we use Firebase transaction-capabilities here because this data can be changed frequently
		// by the service and there could be i.e. a 'last commit wins' problems if we don't
		firebaseRef.child(Constants.FB_PATH_MEDICATIONS_READ_COUNTER_PREFIX + medicationName)
			.runTransaction(new Transaction.Handler() {

				/** {@inheritDoc} */
				@Override
				public Result doTransaction(MutableData dataUnderChange) {
					// we increment 'counter' property for the node representing requested medication resource
					if (dataUnderChange.child(Constants.FB_COUNTER_PROP).getValue() != null) {
						dataUnderChange.child(Constants.FB_COUNTER_PROP).setValue((Long) dataUnderChange.child(Constants.FB_COUNTER_PROP).getValue() + 1);
					} else {
						dataUnderChange.child(Constants.FB_COUNTER_PROP).setValue(1L);
					}
					// we update the 'update-date'property for the node representing requested medication resource
					long currentTime = Calendar.getInstance().getTime().getTime();
					// workarounds for not working descending order with Firebase Arrays
					dataUnderChange.child(Constants.FB_UPDATE_TIMESTAMP_PROP_REVERSE).setValue((0 - currentTime) + Long.MAX_VALUE);
					dataUnderChange.child(Constants.FB_COUNTER_PROP_REVERSE).setValue(
						(0 - (Long) dataUnderChange.child(Constants.FB_COUNTER_PROP).getValue()) + 100000);
					dataUnderChange.child(Constants.FB_UPDATE_DATE_PROP).setValue(
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
		firebaseRef.child(Constants.FB_PATH_MEDICATIONS_TOTAL_READ_COUNT)
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

		firebaseRef.child(Constants.FB_PATH_MEDICATIONS_YEAR_READ_COUNT)
		.runTransaction(new Transaction.Handler() {

			/** {@inheritDoc} */
			@Override
			public Result doTransaction(MutableData dataUnderChange) {

				String currentYearString = Utils.generateCurrentInYearString();

			    if (dataUnderChange.child("year").getValue() == null || 
			    		!dataUnderChange.child("year").getValue().equals(currentYearString)) {
			    	dataUnderChange.child("year").setValue(currentYearString);
			    	dataUnderChange.child("counter").setValue(1L);
			    } else {
			    	dataUnderChange.child("counter").setValue((Long) dataUnderChange.child("counter").getValue() + 1);
			    }
				return Transaction.success(dataUnderChange);
			}

			/** {@inheritDoc} */
			@Override
			public void onComplete(FirebaseError error, boolean isCommited, DataSnapshot dataValue) {
				logFirebaseOperationStatus(error, isCommited, dataValue);
			}
		});

		firebaseRef.child(Constants.FB_PATH_MEDICATIONS_TODAY_READ_COUNT)
		.runTransaction(new Transaction.Handler() {

			String currentDayInDate = Utils.generateCurrentDayString();

			/** {@inheritDoc} */
			@Override
			public Result doTransaction(MutableData dataUnderChange) {
			    if (dataUnderChange.child("day").getValue() == null || 
			    		!dataUnderChange.child("day").getValue().equals(currentDayInDate)) {
			    	dataUnderChange.child("day").setValue(currentDayInDate);
			    	dataUnderChange.child("counter").setValue(1L);
			    } else {
			    	dataUnderChange.child("counter").setValue((Long) dataUnderChange.child("counter").getValue() + 1);
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

	/** {@inheritDoc} */
	@Override
	public String generateCurrentStorageState() {
//		System.out.println("Current state of Firebase storage (local firebase storage mock)");
//		Firebase firebaseRef = createFirebaseReference();
//		firebaseRef.addValueEventListener(new ValueEventListener() {
//			@Override
//			public void onDataChange(DataSnapshot snapshot) {
//				System.out.println(snapshot.getValue());
//			}
//			@Override public void onCancelled(FirebaseError error) { }
//		});
		// TODO 3: napisac generowanie
		return "";
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
		// TODO 4: ogarnac logowanie w ogolnosci
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
			Constants.FB_HTTPS_PREFIX + firebaseAppName + Constants.FB_URL_POSTFIX);
		return firebaseRef;
	}

}