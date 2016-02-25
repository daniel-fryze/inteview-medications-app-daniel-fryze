package pl.education.fryzedaniel.restapp.api.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * General utilities class used across the whole application.
 * 
 * @author daniel.fryze
 */
public class Utils {

	/**
	 * Method generates the current year in String type.
	 * 
	 * @return current year in String format (i.e. "2016")
	 */
	public static String generateCurrentInYearString() {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(new Date());
	    int currentYear = cal.get(Calendar.YEAR);
	    return new Integer(currentYear).toString();
	}

	/**
	 * Method generates the current date (to represent current day).
	 * 
	 * @return current date in String format (i.e. "01-02-2016")
	 */
	public static String generateCurrentDayString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date());
	}

}
