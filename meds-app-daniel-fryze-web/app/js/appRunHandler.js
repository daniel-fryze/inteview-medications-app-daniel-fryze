'use strict';

// Application code to kickstart the application

module.exports = function($rootScope, $log, Restangular, MessagesFactory, AppConstants) {

	// Configuring default error responses handling by Restangular in order to display the information 
	// related to these error scenarios int he same way across the whole application - using modal windows

	Restangular.setErrorInterceptor(
		function(response) {
			if (response.status == 409) {
				// processing 'conflict error' - when we try to add entity with the same name
				$log.warn("An response with '409' status received when calling Rest service - entity creation conflict (duplicate name).");
				$rootScope.$emit(AppConstants.EVENTS_SHOW_MODAL, MessagesFactory.generateErrorMessage(response.data));
			} else if (response.status == 400) {
				// processing 'validation error' - when we try to send a form which causes validation errors  
				// (on server side) - this generally means, that our client side validation is not good enough
				$log.warn("An response with '400' status received when calling Rest service - something wrong withi the validation on server.");
 				$rootScope.$emit(AppConstants.EVENTS_SHOW_MODAL, MessagesFactory.generateErrorMessage(response.data));
			} else if (response.status == 500) {
				// processing 'internal server error' - when something else goes wrong on the server
				$log.warn("An response with '500' status received when calling Rest service - Internal Server Error.");
				$rootScope.$emit(AppConstants.EVENTS_SHOW_MODAL, MessagesFactory.generateErrorMessage(response.data));
			} else {
				// processing any other problem with different HTTP Status code (not anticipated in above cases)
				$log.warn("An response with " + response.status + " status received when calling Rest service - some other problem.");
				$rootScope.$emit(AppConstants.EVENTS_SHOW_MODAL, MessagesFactory.generateErrorMessage(response.data));
			}
			return false;
		}
	);
}
