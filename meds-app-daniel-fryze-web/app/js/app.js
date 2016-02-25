'use strict';

// Loading jQuery module and binding to window object - to be visible globally
window.$ = window.jQuery = require('jquery');

// Loading Angular module for the application
var angular = require('angular');

// Defining dependencies for Angular application
const dependencies = [
  'ngRoute',				// routing (navigation)
  'firebase',				// communication with Firebase
  'restangular',            // handles REST communication
  'ui.bootstrap',           // bootstrap for Angular (no jQuery needed)
  'angularModalService',    // service for modal windows in Angular
  'pascalprecht.translate'  // translation capabilities
];

// Creating the Angular application with given dependencies
var app = angular.module('application', dependencies);

// Defining all requirements for dependent JS third-party modules
require('angular-animate');
require('angular-bootstrap-npm');
require('angular-modal-service');
require('angular-resource');
require('angular-route');
require('angular-translate');
require('angularfire');
require('bootstrap');
require('lodash');
require('restangular');
require('underscore');

// Loading all own application's modules
require('./services');
require('./factories');
require('./controllers');

// Defining and loading application constants and translations
app.constant("AppConstants", require('./appConstants'));
app.constant("AppTranslations", require('./appTranslations'));

// Loading application configuration module
app.config(require('./appConfig'));

// Loading application runtime handler module
app.run(require('./appRunHandler'));
