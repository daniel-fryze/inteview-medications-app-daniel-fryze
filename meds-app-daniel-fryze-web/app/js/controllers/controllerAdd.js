'use strict';

// Controller responsible for all interactions on the page 'add'

module.exports = function($scope, $rootScope, $translate, $log, Restangular, MessagesFactory, AppConstants) {

	$rootScope.selectedMainTab = "add";

	$scope.producerOptions = 
		['Libertas Pharma', 'Oasis Medical', 'BioMarin Pharmaceutical', 'Barr Laboratories'];

	$scope.medication = null;

	// Method called when 'Add ne medication' button is clicked
	
	$scope.addNew = function () {

		var newMedication = {
			name: $scope.medication.name,
			genericName: $scope.medication.genericName,
			dosage: $scope.medication.dosage,
			producerName: $scope.medication.producerName,
			description: $scope.medication.description,
			interactions: $scope.medication.interactions
		};

		Restangular
			.all("medication")
			.post(newMedication)
			.then(
				function(response) {
					// in case of success generate event to make the container controller show modal window with confirmation
  					$rootScope.$emit(
  						AppConstants.EVENTS_SHOW_MODAL, 
  						MessagesFactory.createSuccessMessage($translate.instant('global.messages.medication.added')));
  				}
  				// error situation handling is handled globally (configured in angular 'config' function) 
			)
	};

};