package pl.education.fryzedaniel.restapp.api.services.interfaces;

/**
 * The interface for a service to communicate with <tt>Firebase</tt> storage.
 * 
 * @author daniel.fryze
 */
public interface IFirebaseClient {

	/**
	 * Updates the read count for a specified medication (recognized by name).
	 * @param medicationName the medication name we want to update the read counter for.
	 */
	void updateReadCount(String medicationName);

	/**
	 * Returns the JSON representation of the current storage state in Firebase.
	 * @return the JSON representation of the storage db
	 */
	String generateCurrentStorageState();
}