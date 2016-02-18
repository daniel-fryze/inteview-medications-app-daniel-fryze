angular.module('MedicationsDatabaseApp.Services', ['ngResource'])

    .factory('Employees', function ($resource) {
        return $resource('http://localhost:5000/employees/:employeeId/:data');
    });