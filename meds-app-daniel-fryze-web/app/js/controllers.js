angular.module('MedicationsDatabaseApp.Controllers', [])

	.controller('MedicationsSearchController', function ($rootScope, $timeout, $scope, $http, $firebaseObject, Restangular) {

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
						var firebaseRef = new Firebase("https://temporarymateapp.firebaseio.com/medication_reads/counters/" + $scope.findResult.name);

						firebaseRef.on("value", function(snapshot) {
							$timeout(function() {
								$scope.$apply(function(data) {
									$scope.requestsCount = snapshot.val();
								});
							});
						});
				});	
			}
        };

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

    })

	.controller('AddNewMedicationController', function ($rootScope, $timeout, $scope, $http, Restangular) {

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
			});

        };
	})

	.controller('DashboardController', function ($firebaseObject, $firebaseArray, $rootScope, $scope, $http, $timeout) {

		$rootScope.selectedMainTab = "dashboard";

		var firebaseRefSingle = new Firebase("https://temporarymateapp.firebaseio.com/medication_reads/counters");
		var firebaseRefTotals = new Firebase("https://temporarymateapp.firebaseio.com/medication_reads/counters_sums");
		var queryRecentDataReads = firebaseRefSingle.orderByChild("updateDateTimestampReverse").limitToFirst(5);
		var queryAllReads = firebaseRefSingle;		
		var queryMostOftenDataReads = firebaseRefSingle.orderByChild("counterReverse").limitToFirst(5);
		$scope.recentDataReads = $firebaseArray(queryRecentDataReads);
		$scope.mostFrequentDataReads = $firebaseArray(queryMostOftenDataReads);

		firebaseRefTotals.on("value", function(snapshot) {
			$timeout(function() {
				$scope.$apply(function(data) {
					$scope.totalReads = snapshot.val();
				});
			});
		});

	});