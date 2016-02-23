'use strict';

// Application basic configuration

module.exports = function($routeProvider, $translateProvider, RestangularProvider, AppConstants, AppTranslations) {

	// Configuring Restangular default behaviour

	RestangularProvider.setBaseUrl(AppConstants.REST_API_APP_URL);
	RestangularProvider.setFullResponse(true);

	// Configuring application's translations

  	$translateProvider.translations('en', AppTranslations);
  	$translateProvider.preferredLanguage('en');
  	$translateProvider.useSanitizeValueStrategy('escape');

	// Configuring application's navigation

	$routeProvider
		.when("/", {
			templateUrl: "./pages/dashboard.html",
			controller: "dashboardController"
		})
		.when("/dashboard", {
			templateUrl: "./pages/dashboard.html",
			controller: "dashboardController"
		})
		.when("/search", {
			templateUrl: "./pages/search.html",
			controller: "searchController"
		})
		.when("/add", {
			templateUrl: "./pages/add.html",
			controller: "addController"
		})
		.otherwise({
			redirectTo: '/'
		});
};