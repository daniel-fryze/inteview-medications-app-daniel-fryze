// definitions of controller components used across the whole appliaction

angular.module('MedicationsDatabaseApp.Controllers', [])

	// a controller component for the web page 'searching existing medications'

	.controller(
		'MedicationsSearchController', 
		['FIREBASE_STORE_BASE_URL', 'FIREBASE_STORE_COUNTERS_PATH', '$http', '$rootScope', 
		'$scope', '$timeout', '$firebaseObject', 'Restangular', 'FirebaseSynchedObjectFactory',
		function (FIREBASE_STORE_BASE_URL, FIREBASE_STORE_COUNTERS_PATH,
		          $http, $rootScope, $scope, $timeout, $firebaseObject, Restangular, FirebaseSynchedObjectFactory) {

			$rootScope.selectedMainTab = "search";

			$scope.searchKey = "";
			$scope.recentSearchKey = "";

			$scope.searchMethodInvoked = false;
			$scope.searchResultsExist = false;

			$scope.findMethodInvoked = false;
			$scope.findResultExists = false;

			$scope.searchResults = null;
			$scope.findResult = null;

			$scope.chosenMedId = null;

			// method invoked when the 'search' button is clicked
			$scope.search = function () {

				if ($scope.searchKey) {

					console.log('Invoking \'search\' function for input search key:' + $scope.searchKey);

					Restangular.all('medication')
						.getList({namePattern: $scope.searchKey, partialSearch: true, namesOnly: true})
						.then(function(response) {
							$scope.searchResults = response.data;
							if ($scope.searchResults.length > 0) {
								$scope.searchResultsExist = true;
							} else {
								$scope.searchResultsExist = false;
							}
							$scope.searchMethodInvoked = true;
						});
					$scope.recentSearchKey = $scope.searchKey;
				}
			};

			// method called when the select item on a search results list is clicked
			$scope.findDetails = function (medicationId, forced) {

				if (medicationId && ((medicationId != $scope.chosenMedId) || forced)) {

					console.log('Invoking \'find details\' function for medication id:' + medicationId);

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
							FirebaseSynchedObjectFactory(FIREBASE_STORE_COUNTERS_PATH  + $scope.findResult.name)
							.$bindTo($scope, "requestsCountForFoundItem");
					});	
				}
			};

			// method called when the 'clear all' button is clicked
			$scope.clearAll = function () {

				console.log('Invoking \'clear all\' function');

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
			
			$scope.selectTab = function (selectedTab) {
				$scope.selectedDetailsTab = selectedTab;
			};

			$scope.refreshFind = function () {
				if ($scope.chosenMedId) {
					$scope.findDetails($scope.chosenMedId, true);
				}
			};
    }])

	// a controller component for the web page 'adding new medications'

	.controller(
		'AddNewMedicationController', 
		['$rootScope', '$timeout', '$scope', '$http', 'Restangular',
		function ($rootScope, $timeout, $scope, $http, Restangular) {

			$rootScope.selectedMainTab = "add";
			$scope.producerOptions = ['Libertas Pharma', 'Oasis Medical', 'BioMarin Pharmaceutical', 'Barr Laboratories'];
			$scope.medication = null;

			$scope.addNew = function () {

				var newMedication = {
					name: $scope.medication.name,
					genericName: $scope.medication.genericName,
					dosage: $scope.medication.dosage,
					producerName: $scope.medication.producerName,
					description: $scope.medication.description,
					interactions: $scope.medication.interactions
				};

				Restangular.all("medication").post(newMedication).then(function(response) {
					$('#modalConfirmationSuccess').modal('show');
				}, function(response) {
					// the error handling for Restangular requests is serviced globally in 'main.js' file
				});
			};
	}])

	// a controller component for the web page 'dashboard'

	.controller(
		'DashboardController', 
		['FIREBASE_STORE_BASE_URL', 'FIREBASE_STORE_COUNTERS_PATH', 'FIREBASE_STORE_COUNTERS_SUMS_PATH', 
		'$firebaseObject', '$firebaseArray', '$rootScope', '$scope', '$http', '$timeout', 'FirebaseRefFactory', 'FirebaseSynchedObjectFactory',
		function (FIREBASE_STORE_BASE_URL, FIREBASE_STORE_COUNTERS_PATH, FIREBASE_STORE_COUNTERS_SUMS_PATH, 
		          $firebaseObject, $firebaseArray, $rootScope, $scope, $http, $timeout, FirebaseRefFactory, FirebaseSynchedObjectFactory) {
		
			$rootScope.selectedMainTab = "dashboard";

			var firebaseRefSingle = FirebaseRefFactory(FIREBASE_STORE_COUNTERS_PATH);
			var firebaseRefTotals = FirebaseRefFactory(FIREBASE_STORE_COUNTERS_SUMS_PATH);

			var queryRecentDataReads = firebaseRefSingle.orderByChild("updateDateTimestampReverse").limitToFirst(5);
			var queryAllReads = firebaseRefSingle;		
			var queryMostOftenDataReads = firebaseRefSingle.orderByChild("counterReverse").limitToFirst(5);

			$scope.recentDataReads = $firebaseArray(queryRecentDataReads);
			$scope.mostFrequentDataReads = $firebaseArray(queryMostOftenDataReads);

			FirebaseSynchedObjectFactory(FIREBASE_STORE_COUNTERS_SUMS_PATH)
			.$bindTo($scope, "totalReads");
	}]);