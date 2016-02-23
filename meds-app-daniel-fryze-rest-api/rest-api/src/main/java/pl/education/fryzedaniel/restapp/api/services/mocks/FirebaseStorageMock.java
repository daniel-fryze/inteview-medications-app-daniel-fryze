package pl.education.fryzedaniel.restapp.api.services.mocks;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * The local (mock) representation of Firebase storage (similar data structure as in real FB service).
 * It is used primarily for local development (i.e. when internet connection is down) and local tests.
 * 
 * @author daniel.fryze
 */
@Service
public class FirebaseStorageMock {

	/** The data structure for the mock representation of Firebase storage. */
	private Map<String, Object> firebaseInternalDatabase = new HashMap<String, Object>();

	/**
	 * The method which updates the data, setting the value for a given key.
	 * 
	 * @param key the key for which the value is changed
	 * @param value the new value set for the given key
	 */
	public void setValue(final String key, final Object value) {
		firebaseInternalDatabase.put(key, value);
	}

	/**
	 * The method which returns the value for a given key.
	 * 
	 * @param key the key for which we read the data
	 * @return the data for the given key
	 */
	public Object getValue(final Object key) {
		return firebaseInternalDatabase.get(key);
	}

	/**
	 * The method which returns the JSON representation of the current storage state.
	 * 
	 * @return the String containing the JSON representation of the current data state
	 */
	public String getJsonStorageState() {
		// TODO 1 : napisac generowanie
		return "";
	}
}