'use strict';

// Default (en) application's translations

module.exports = {

	// Main page template

	'template.main.title'                    : 'Medications Book - REST App - application from: Daniel Fryze',
	'template.main.menu.dashboard'           : 'Dashboard',
	'template.main.menu.add'                 : 'Search engine',
	'template.main.menu.search'              : 'Add new medication',

	// Modal windows

	'modal.confirmation.success.title'       : 'Operation successful',
	'modal.confirmation.error.title'         : 'Operation failed',

	// Page 'add'

	'pages.add.header'                       : 'Medications creator',
	'pages.add.panel.new.heading'            : 'Add new medication',
	'pages.add.label.name'                   : 'Name',
	'pages.add.label.generic'                : 'Generic name',
	'pages.add.label.dosage'                 : 'Dosage',
	'pages.add.label.producer'               : 'Producer',
	'pages.add.label.description'            : 'Description',
	'pages.add.label.interactions'           : 'Interactions',
	'pages.add.button.save'                  : 'Save new medication',
	'pages.add.placeholder.max100'           : 'Max. 100 chars',
	'pages.add.placeholder.max1000'          : 'Max. 1000 chars',
	'pages.add.placeholder.req.max100'       : 'Required, max. 100 chars',

	// Page 'search'

	'pages.search.header'                    : 'Medications search engine',
	'pages.search.panel.query.heading'       : 'Search query',
	'pages.search.panel.results.heading'     : 'Search results for query pattern',
	'pages.search.panel.details.heading'     : 'Medication details for medication name',
	'pages.search.tabs.title.info'           : 'Info',
	'pages.search.tabs.title.description'    : 'Description',
	'pages.search.tabs.title.dosage'         : 'Dosage',
	'pages.search.tabs.title.interactions'   : 'Interactions',
    'pages.search.details.label.reads'       : 'Database requests count',
	'pages.search.details.label.producer'    : 'Producer',
	'pages.search.details.label.date'        : 'Date added',
	'pages.search.details.label.generic'     : 'Generic name',
	'pages.search.details.label.name'        : 'Name',
	'pages.search.details.title.details'     : 'Medication details',
	'pages.search.tabs.interactions.heading' : 'Possible dangerous interactions',
	'pages.search.tabs.dosage.heading'       : 'Suggested optimal dosage',
	'pages.search.tabs.description.heading'  : 'Medication detailed description',
	'pages.search.info.unavailable'          : 'Data unavailable',
    'pages.search.button.search'             : 'Search',
    'pages.search.button.clear'              : 'Clear all',
    'pages.search.button.refresh'            : 'Refresh data',
    'pages.search.messages.results.1'        : 'To see the search results click the search button above',
    'pages.search.messages.results.2'        : 'We are sorry, but no results were found for your query',
    'pages.search.messages.results.3'        : 'Please change your search criteria and try again',
	'pages.search.messages.details.1'        : 'To see the search results click the search button above',
	'pages.search.messages.details.2'        : 'We are sorry, but no results were found for your query',
	'pages.search.messages.details.3'        : 'Please change your search criteria and try again',
	'pages.search.messages.producer'         : 'Producer',
	'pages.search.messages.unknown'          : 'unknown',
	'pages.search.placeholders.search'       : 'Enter medication name',

	// Page 'dashboard'

	'pages.dashboard.header'                 : 'Medications dashboard',
	'pages.dashboard.label.reads.total'      : 'Total reads',
	'pages.dashboard.label.reads.year'       : 'This year reads',
	'pages.dashboard.label.reads.today'      : 'Today reads',
	'pages.dashboard.title.recent'           : 'recently requested items',
	'pages.dashboard.title.frequent'         : 'most frequently requested items',

	// Global messages
	
	'global.messages.medication.added'       : 'The new medication has been successfully added to a database'

};