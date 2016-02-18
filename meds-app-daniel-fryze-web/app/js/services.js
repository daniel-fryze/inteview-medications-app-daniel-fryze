// definitions of service components used across the appliaction

angular.module('MedicationsDatabaseApp.Services', ['ngResource'])

	// a factory component for creating Firebase pre-configured synced
	.factory(
	    'FirebaseSynchedObjectFactory',
		['FIREBASE_STORE_BASE_URL', '$firebaseObject',
		function(FIREBASE_STORE_BASE_URL, $firebaseObject) {
			return function(firebaseRefPath) {
				var ref = new Firebase(FIREBASE_STORE_BASE_URL + firebaseRefPath);
				return $firebaseObject(ref);
			};
		}
	])
	
	// a factgory component for constructing Firebase ref objects
	.factory(
	    'FirebaseRefFactory',
		['FIREBASE_STORE_BASE_URL', '$firebaseObject',
		function(FIREBASE_STORE_BASE_URL, $firebaseObject) {
			return function(firebaseRefPath) {
				return new Firebase(FIREBASE_STORE_BASE_URL + firebaseRefPath);
			};
		}
	]);