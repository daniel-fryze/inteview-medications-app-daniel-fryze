package pl.education.fryzedaniel.restapp.api.utilities;

/**
 * The interface used as a utility interface to store constants used across application.
 * 
 * @author daniel.fryze
 */
public interface Constants {

	/** GENERAL PURPOSE CONSTANTS */

	String DEFAULT_ENCODING = "UTF-8";

	/** CONSTANTS WITH PATHS TO APP-INTERNAL FILES */

	String MESSAGES_PATH = "messages/messages";

	/** FIREBASE RELATED CONFIGURATION CONSTANTS */

	String FB_HTTPS_PREFIX = "https://";
	String FB_URL_POSTFIX = ".firebaseio.com";

	/** FIREBASE DB RELATED CONFIGURATION CONSTANTS */

	String FB_PATH_MEDICATIONS_READ_COUNTER_PREFIX = "medication_reads/counters/";
	String FB_PATH_MEDICATIONS_READ_DATE_PREFIX = "medication_reads/dates/";
	String FB_PATH_MEDICATIONS_TOTAL_READ_COUNT = "medication_reads/counters_sums_total/counter";
	String FB_PATH_MEDICATIONS_YEAR_READ_COUNT = "medication_reads/counters_sums_year/counter";
	String FB_PATH_MEDICATIONS_TODAY_READ_COUNT = "medication_reads/counters_sums_today/counter";
	String FB_UPDATE_TIMESTAMP_PROP_REVERSE = "updateDateTimestampReverse";
	String FB_COUNTER_PROP_REVERSE = "counterReverse";
	String FB_UPDATE_DATE_PROP = "updateDate";
	String FB_COUNTER_PROP = "counter";

	/** HTTP PROTOCOL SPECIFIC CONSTANTS */

	String HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	String HEADER_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	String HEADER_ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
}