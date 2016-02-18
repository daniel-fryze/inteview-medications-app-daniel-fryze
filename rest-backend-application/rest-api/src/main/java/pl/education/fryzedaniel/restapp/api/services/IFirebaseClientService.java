package pl.education.fryzedaniel.restapp.api.services;

public interface IFirebaseClientService {

	/**
	 * Updates the <tt>read counter</tt> and <tt>last update date</tt> properties for given key in Firebase store.
	 * 
	 * @param medicationName the medication name for which the data is updated
	 */
	void updateReadCount(String medicationName);

}