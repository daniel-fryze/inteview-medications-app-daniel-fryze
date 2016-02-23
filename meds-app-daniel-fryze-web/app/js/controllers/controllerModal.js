'use strict';

// Controller responsible for handling internal modal window interactions

module.exports = function($scope, $rootScope, $log, close, messageDetails) {

	$scope.message = messageDetails.message;
	$scope.details = messageDetails.details;
	$scope.isSuccess = messageDetails.isSuccess;

	// Method called when modal window is closed

 	$scope.dismissModal = function(result) {
 		$rootScope.modalOpened = false;
    	close(result, 200);
 	};

}