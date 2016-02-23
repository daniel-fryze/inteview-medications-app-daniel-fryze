'use strict';

// Service responsible for generating messages which are later passed to modals

module.exports = function($log) {

	// Constructor creating the new 'message' instance

	var Message = function(isSuccess, message, details) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.details = details;
	}

	// Creates new 'message' instance based on explicit properties

	this.createErrorMessage = function(message, details) {
		return new Message(false, message, details);
	};

	// Creates new 'message' instance based on input Rest response object

	this.generateErrorMessage = function(responseData) {
		return new Message(false, responseData.generalMessage, responseData.detailedMessage);
	};

	// Creates new 'message' instance for success scenarios

	this.createSuccessMessage = function(message) {
		return new Message(true, message);
	};

};