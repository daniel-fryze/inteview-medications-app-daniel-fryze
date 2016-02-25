'use strict';

// Constants used across the whole application

module.exports = {

	// Rest API related data

	// Heroku deployed backend
	// REST_API_APP_URL                   : "https://meds-app-daniel-fryze-rest-api.herokuapp.com/",
	// Locally deployed backend
	REST_API_APP_URL                   : "http://localhost:9200",

	// Firebase related data

	FIREBASE_BASE_URL                  : "https://meds-app-daniel-fryze-firebase-database.firebaseio.com/",
	FIREBASE_COUNTERS_PATH             : "medication_reads/counters/",
	FIREBASE_COUNTERS_SUMS_TOTAL_PATH  : "medication_reads/counters_sums_total/",
	FIREBASE_COUNTERS_SUMS_YEAR_PATH   : "medication_reads/counters_sums_year/",
	FIREBASE_COUNTERS_SUMS_TODAY_PATH  : "medication_reads/counters_sums_today/",

	// Views related configuration

	DASHBOARD_RECENT_READS_NUMBER      : 5,
	DASHBOARD_FREQUENT_READS_NUMBER    : 5,

	// Names of events used globally across the whole app

	EVENTS_SHOW_MODAL                  : "eventShowModal"

};
