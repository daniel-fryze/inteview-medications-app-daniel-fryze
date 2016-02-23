'use strict';

var app = require('angular').module('application');

// registering all 'service' components

app.service('FirebaseService', require('./serviceFirebase'));