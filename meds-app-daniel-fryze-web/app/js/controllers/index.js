'use strict';

var app = require('angular').module('application');

// registering all 'controller' components

app.controller('searchController', require('./controllerSearch'));
app.controller('addController', require('./controllerAdd'));
app.controller('dashboardController', require('./controllerDashboard'));

app.controller('modalController', require('./controllerModal'));
app.controller('containerController', require('./controllerContainer'));