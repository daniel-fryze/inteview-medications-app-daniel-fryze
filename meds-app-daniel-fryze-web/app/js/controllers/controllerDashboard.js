'use strict';

// Controller responsible for all interactions on the page 'dashboard'

module.exports = function($scope, $rootScope, $log, FirebaseService, AppConstants) {

	$rootScope.selectedMainTab = "dashboard";

	$scope.recentReadsNumber = AppConstants.DASHBOARD_RECENT_READS_NUMBER;
	$scope.frequentReadsNumber = AppConstants.DASHBOARD_FREQUENT_READS_NUMBER;

	var counterTotalPath = 
		AppConstants.FIREBASE_BASE_URL + AppConstants.FIREBASE_COUNTERS_SUMS_TOTAL_PATH;

	var counterThisYearPath = 
		AppConstants.FIREBASE_BASE_URL + AppConstants.FIREBASE_COUNTERS_SUMS_YEAR_PATH;

	var counterThisDayPath = 
		AppConstants.FIREBASE_BASE_URL + AppConstants.FIREBASE_COUNTERS_SUMS_TODAY_PATH;

	// Binding firebase objects to angular scoped values - to be updated asynchronously on the view

	FirebaseService.getFirebaseSyncedObject(counterTotalPath).$bindTo($scope, "totalReads");
	FirebaseService.getFirebaseSyncedObject(counterThisYearPath).$bindTo($scope, "thisYearReads");
	FirebaseService.getFirebaseSyncedObject(counterThisDayPath).$bindTo($scope, "todayReads");

	var firebaseReferenceCounters = FirebaseService.createFirebaseReference(
		AppConstants.FIREBASE_BASE_URL + AppConstants.FIREBASE_COUNTERS_PATH);

	var queryRecentDataReads = firebaseReferenceCounters
		.orderByChild("updateDateTimestampReverse")
		.limitToFirst(AppConstants.DASHBOARD_RECENT_READS_NUMBER);

	var queryMostOftenDataReads = firebaseReferenceCounters
		.orderByChild("counterReverse")
		.limitToFirst(AppConstants.DASHBOARD_FREQUENT_READS_NUMBER);

	// Binding firebase lists of objects to angular scoped values (arrays) - to be updated in real time

	$scope.recentDataReads = FirebaseService.getFirebaseSyncedArray(queryRecentDataReads);
	$scope.mostFrequentDataReads = FirebaseService.getFirebaseSyncedArray(queryMostOftenDataReads);

};