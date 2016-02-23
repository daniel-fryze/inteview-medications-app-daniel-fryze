'use strict';

// Controller responsible for handling modal windows

module.exports = function($scope, $rootScope, $log, ModalService, AppConstants) {

	// When receiving the global event, display appropriate modal window

	$rootScope.$on(AppConstants.EVENTS_SHOW_MODAL, function(event, details) {
		$scope.show('modal.template', details);
	});

    $scope.show = function(templateUrl, details) {

        var modal = ModalService.showModal({
            templateUrl: templateUrl,
            controller: "modalController",
  			inputs: {
    			messageDetails: details
      		}
        }).then(function(modal) {
            modal.element.modal();
            $rootScope.modalOpened = true;
        });
    };

}