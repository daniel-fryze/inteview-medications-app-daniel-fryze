'use strict';

// Service responsible for interactions with Firebase

module.exports = function($firebaseObject, $firebaseArray) {

	// Creates the Firebase reference for a given path

	this.createFirebaseReference = function(firebasePath) {
		return new Firebase(firebasePath);
	};

	// Creates a Firebase synched object - used to bind remote data to angular scope

	this.getFirebaseSyncedObject = function(firebasePath) {
		var firebaseReference = this.createFirebaseReference(firebasePath);
		return $firebaseObject(firebaseReference);
	};

	// Creates a Firebase synched array - used to bind remote data list to angular scope

	this.getFirebaseSyncedArray = function(firebaseRef) {
		return $firebaseArray(firebaseRef);
	};

};