(function () {

	'use strict';

	angular.module('MedicationsDatabaseApp',
	    [
		    'ngRoute', 'ngAnimate', 
			'MedicationsDatabaseApp.Controllers', 'MedicationsDatabaseApp.Services',
			'jcs-autoValidate', 'firebase', 'restangular',
		])

	.config([

		'$locationProvider', '$routeProvider', 'RestangularProvider', function($locationProvider, $routeProvider, RestangularProvider) {

			RestangularProvider.setBaseUrl('https://meds-app-daniel-fryze-rest-api.herokuapp.com');
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

	.run([
		'validator',
        function (validator) {
			validator.setValidElementStyling(false);
			validator.setInvalidElementStyling(true);
        }])

	.run(['Restangular', '$window', function(Restangular, $window){
		Restangular.setErrorInterceptor(
			function(response) {
				if (response.status == 409) {
					// processing 'conflict error' - when we try to add entity with the same name
					$('#failureReason').html(generateFailurReasonDescription(response.data.generalMessage));
					$('#modalConfirmationFailure').modal('show');
				} else if (response.status == 400) {
					// processing 'validation error' - when we try to send a form which causes validation errors  
					// (on server side) - this generally means, that our client side validation is not good enough
					console.log("Resource not available...");
				} else if (response.status == 50) {
					// processing 'internal server error' - when something else goes wrong on the server
					console.log("Response received with HTTP error code: " + response.status );
				} else {
					// processing any other problem with different HTTP Status code (not anticipated in above cases)
					console.log("Response received with HTTP error code: " + response.status );
				}
				return false;
			}
		);

		var generateFailurReasonDescription = function(message) {
			return message;
		};

}]);

}());