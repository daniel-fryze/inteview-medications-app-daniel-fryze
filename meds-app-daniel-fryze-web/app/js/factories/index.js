'use strict';

var app = require('angular').module('application');

// registering all 'factory' components

app.service('MessagesFactory', require('./factoryMessages'));