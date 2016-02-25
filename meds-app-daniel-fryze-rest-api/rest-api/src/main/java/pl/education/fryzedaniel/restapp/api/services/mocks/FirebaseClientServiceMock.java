package pl.education.fryzedaniel.restapp.api.services.mocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import pl.education.fryzedaniel.restapp.api.services.interfaces.IFirebaseClient;
import pl.education.fryzedaniel.restapp.api.utilities.Constants;

@Service
@Profile("dev-mock")
public class FirebaseClientServiceMock implements IFirebaseClient {

	@Autowired
	private FirebaseStorageMock firebaseStorage;

	/** {@inheritDoc} */
	@Override
	public void updateReadCount(final String medicationName) {

		String counterSinglePath = 
			Constants.FB_PATH_MEDICATIONS_READ_COUNTER_PREFIX + medicationName + "/" + Constants.FB_COUNTER_PROP;
		if (firebaseStorage.getValue(counterSinglePath) == null) {
			firebaseStorage.setValue(counterSinglePath, 0);
		} else {
			firebaseStorage.setValue(counterSinglePath, ((Integer) firebaseStorage.getValue(counterSinglePath)) + 1);
		}
		String counterTotalPath = Constants.FB_PATH_MEDICATIONS_TOTAL_READ_COUNT;
		if (firebaseStorage.getValue(counterTotalPath) == null) {
			firebaseStorage.setValue(counterTotalPath, 0);
		} else {
			firebaseStorage.setValue(counterTotalPath, ((Integer) firebaseStorage.getValue(counterTotalPath)) + 1);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String generateCurrentStorageState() {
		return this.firebaseStorage.getJsonStorageState();
	}
}