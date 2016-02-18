(function () {

	'use strict';

	// configuring dependencies for angularjs app
	angular.module('MedicationsDatabaseApp',
	    [
		    'ngRoute', 
			'ngAnimate', 
			'jcs-autoValidate', 
			'firebase', 
			'restangular',
			'MedicationsDatabaseApp.Controllers', 
			'MedicationsDatabaseApp.Services',
		])

	// defining constants used across the application
	.constant('FIREBASE_STORE_BASE_URL', 'https://meds-app-daniel-fryze-firebase-database.firebaseio.com/')
	.constant('FIREBASE_STORE_COUNTERS_PATH', 'medication_reads/counters/')
	.constant('FIREBASE_STORE_COUNTERS_SUMS_PATH', 'medication_reads/counters_sums/')
	.constant('REST_API_APP_URL', 'https://meds-app-daniel-fryze-rest-api.herokuapp.com')

	// configuring navigation (routes provider) for the whole application
	.config([

		'$locationProvider', '$routeProvider', 'RestangularProvider', 'REST_API_APP_URL',
		function($locationProvider, $routeProvider, RestangularProvider, REST_API_APP_URL) {

			RestangularProvider.setBaseUrl(REST_API_APP_URL);
			RestangularProvider.setFullResponse(true);

			$routeProvider
				.when("/", {
					templateUrl: "./templates/dashboard.html",
					controller: "DashboardController"
				})
				.when("/dashboard", {
					templateUrl: "./templates/dashboard.html",
					controller: "DashboardController"
				})
				.when("/search", {
					templateUrl: "./templates/search.html",
					controller: "MedicationsSearchController"
				})
				.when("/add", {
					templateUrl: "./templates/add.html",
					controller: "AddNewMedicationController"
				})
				.otherwise({
					redirectTo: '/'
				});
		}
	])

	// disabling styling valid elements for auto-validate plugin
	.run([
		'validator',
        function (validator) {
			validator.setValidElementStyling(false);
			validator.setInvalidElementStyling(true);
        }])
	
	// configuring the default failure messages for Restangular REST calls
	.run(['Restangular', '$window', function(Restangular, $window){
		Restangular.setErrorInterceptor(
			function(response) {
				if (response.status == 409) {
					// processing 'conflict error' - when we try to add entity with the same name
					console.log("An response with '409' status received when calling Rest service - entity creation conflict (duplicate name).");
					if (response && response.data) {
						console.log("Error message details: " + response.data.generalMessage);
						$('#failureReason').html(response.data.generalMessage);
					}
					$('#modalConfirmationFailure').modal('show');
				} else if (response.status == 400) {
					// processing 'validation error' - when we try to send a form which causes validation errors  
					// (on server side) - this generally means, that our client side validation is not good enough
					console.log("An response with '400' status received when calling Rest service - something wrong withi the validation on server.");
					if (response && response.data) {
						console.log("Error message details: " + response.data.generalMessage);
						$('#failureReason').html(response.data.generalMessage);
					}
					$('#modalConfirmationFailure').modal('show');
				} else if (response.status == 500) {
					// processing 'internal server error' - when something else goes wrong on the server
					console.log("An response with '500' status received when calling Rest service - Internal Server Error.");
					if (response && response.data) {
						console.log("Error message details: " + response.data.generalMessage);
						$('#failureReason').html(response.data.generalMessage);
					}
					$('#modalConfirmationFailure').modal('show');
				} else {
					// processing any other problem with different HTTP Status code (not anticipated in above cases)
					console.log("An response with " + response.status + " status received when calling Rest service - some other problem.");
					if (response && response.data) {
						console.log("Error message details: " + response.data.generalMessage);
						$('#failureReason').html(response.data.generalMessage);
					}
					$('#modalConfirmationFailure').modal('show');
				}
				return false;
			}
		);

	}]);

}());