'use strict';

// Service responsible for generating messages which are later passed to modals

module.exports = function() {

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

	this.generateErrorMessage = function(response) {
		return new Message(false, response.generalMessage, response.detailedMessage);
	};

	// Creates new 'message' instance for success scenarios

	this.createSuccessMessage = function(message) {
		return new Message(true, message);
	};

};