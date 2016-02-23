'use strict';

require('firebase');

// Controller responsible for all interactions on the page 'search'

module.exports = function($scope, $rootScope, Restangular, FirebaseService, AppConstants, $log) {

	$rootScope.selectedMainTab = "search";

	// Method called when the 'Clear all' button is clicked

	$scope.clearAll = function () {

		$scope.searchKey = "";
		$scope.recentSearchKey = "";

		$scope.searchMethodInvoked = false;
		$scope.searchResultsExist = false;

		$scope.findMethodInvoked = false;
		$scope.findResultExists = false;

		$scope.searchResults = null;
		$scope.findResult = null;

		$scope.chosenMedId = null;

		$scope.selectedDetailsTab = null;
	};

	// Method called when the 'Search' button is clicked

	$scope.search = function () {

		if ($scope.searchKey) {

			Restangular.all('medication')
				.getList({namePattern: $scope.searchKey, partialSearch: true, namesOnly: true})
				.then(function(response) {
					$scope.searchResults = response.data;
					$scope.searchResultsExist = ($scope.searchResults.length > 0);
					$scope.searchMethodInvoked = true;
				});
			$scope.recentSearchKey = $scope.searchKey;
		}
	};

	// Method called when the select item on a search results list is clicked

	$scope.findDetails = function (medicationId, forced) {

		if (medicationId && ((medicationId != $scope.chosenMedId) || forced)) {

			$scope.chosenMedId = medicationId;

			Restangular.one('medication', medicationId).get()
				.then(function(response) {

					$scope.findResult = response.data;
					if ($scope.findResult) {
						$scope.findResultExists = true;
						if (!$scope.selectedDetailsTab) {
							$scope.selectedDetailsTab = 'info';
						}	
					} else {
						$scope.findResultExists = false;
					}
					$scope.findMethodInvoked = true;

					var selectedMedicationCounterPath = 
						AppConstants.FIREBASE_BASE_URL + AppConstants.FIREBASE_COUNTERS_PATH + $scope.findResult.name;
					FirebaseService.getFirebaseSyncedObject(selectedMedicationCounterPath).$bindTo($scope, "requestsCount");
				});
		}
	};

	// Method called when the tab is selected in 'medication details' panel

	$scope.selectTab = function (selectedTab) {
		$scope.selectedDetailsTab = selectedTab;
	};

	// Method called when the 'Refresh data' button is clicked

	$scope.refreshFind = function () {
		if ($scope.chosenMedId) {
			$scope.findDetails($scope.chosenMedId, true);
		}
	};

	// Clearing all on the view when opening the page

	$scope.clearAll();
};