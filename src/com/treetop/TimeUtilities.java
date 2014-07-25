package com.treetop;

import java.math.*;
/**
 * Public Static methods for Time,
 *   To be used for any easily external
 *   calculation or manipulation of time.
 * 
 * Creation date: (10/28/2003 2:21:21 PM)
 * @author: Teri Walton
 * @deprecated
 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
 */
public class TimeUtilities {
/**
 * Use this method to take an int Number
 * and return a String which will be
 *    x Days xx:xx:xx (Only having days when needed)
 *
 * Creation date: (10/28/2003 2:43:53 PM)
 * @return String
 * @deprecated
 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
 */
public static String convertSecondsToTime(int inSeconds) 
{
	String seconds = "";
try
{
	int    secondsPerDay = 24 * 60 * 60;
	int    dayCount      = 0;
    while (inSeconds > secondsPerDay)
	{
	   dayCount  = dayCount + 1;
	   inSeconds = inSeconds - secondsPerDay;
	}
	if (dayCount > 0)
	   seconds = seconds + dayCount + " days :";

	int    secondsPerHour = 60 * 60;
	int    hourCount      = 0;
    while (inSeconds > secondsPerHour)
	{
	   hourCount  = hourCount + 1;
	   inSeconds = inSeconds - secondsPerHour;
	}
	if (hourCount < 10)
	   seconds = seconds + "0" + hourCount + ":";
	else
	   seconds = seconds + hourCount + ":";
		
	int    secondsPerMinute = 60;
	int    minuteCount      = 0;
    while (inSeconds > secondsPerMinute)
	{
	   minuteCount  = minuteCount + 1;
	   inSeconds = inSeconds - secondsPerMinute;
	}
	if (minuteCount < 10)
	   seconds = seconds + "0" + minuteCount + ":";
	else
	   seconds = seconds + minuteCount + ":";

	if (inSeconds < 10)
	   seconds = seconds + "0" + inSeconds;
	else
	   seconds = seconds + inSeconds;
	
}
catch(Exception e)
{
	System.out.println("Exception found in TimeUtilities.convertSQLTimeToSeconds(): " + e);
}	
	return seconds;
	
}
/**
 * Use this method to take an SQL Time fields
 * and return and int, which equates to seconds.
 *
 * Creation date: (10/28/2003 2:43:53 PM)
 * @return int
 * @deprecated
 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
 */
public static int convertSQLTimeToSeconds(java.sql.Time inTime) 
{
	int seconds = 0;
try
{
	String inTimeString = inTime + "";
	
	String hour          = inTimeString.substring(0, 2);
	int    hourInt       = Integer.parseInt(hour);
	int    hourSeconds   = hourInt * 60 * 60;
	
	String minute        = inTimeString.substring(3, 5);
	int    minuteInt     = Integer.parseInt(minute);
	int    minuteSeconds = minuteInt * 60;
	
	String second        = inTimeString.substring(6, 8);
	int    secondInt     = Integer.parseInt(second);

	seconds              = hourSeconds +
	                       minuteSeconds +
						   secondInt;

}
catch(Exception e)
{
	System.out.println("Exception found in TimeUtilities.convertSQLTimeToSeconds(): " + e);
}	
	return seconds;
	
}
/**
 * Format any Integer holding time into jsp display format.
 *
 * Creation date: (3/23/2005 11:51:27 AM)
 * 
 * @deprecated
 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
 */
public static String formatGetTime(Integer inTime) {
		
	if ((inTime == null) || (inTime.intValue() == 0))
		return null;
			
	String        timeAsIs   = "";
	String        timeToSQL  = "";
	java.sql.Time timeSQL    = null;
	String        timeEdited = null;
				
	try {
			
		timeAsIs = inTime.toString();
		for (int i = 0; timeAsIs.length() < 6; i++) {
			timeAsIs = "0" + timeAsIs;
		}
		
		if (timeAsIs.length() == 6)
			timeToSQL = timeAsIs.substring(0, 2) + ":" +	// Hours
						timeAsIs.substring(2, 4) + ":" +	// Minutes
						timeAsIs.substring(4, 6); 			// Seconds
		else {
			System.out.println("Invalid time error at com.treetop." +
							   "TimeUtilities.formatGetTime(Integer): " + timeAsIs);
			return null;
		}				           
	}
	catch (Exception e) {	
		System.out.println("Time conversion error at com.treetop." +
						   "TimeUtilities.formatGetTime(Integer): " + timeAsIs + " " + e);
		return null;
	}
		
	try {
			
		timeSQL    = java.sql.Time.valueOf(timeToSQL);
		timeEdited = timeSQL.toString();
			
		if (!timeEdited.trim().equals (timeToSQL.trim())) {		
			System.out.println("Invalid time error at com.treetop." +
							   "TimeUtilities.formatGetTime(Integer): " + timeAsIs);
			return null;				
		}
			
	}
	catch (Exception e) {	
		System.out.println("java.sql.Time conversion error at com.treetop." +
						   "TimeUtilities.formatGetTime(Integer): " + timeAsIs + " " + e);
		return null;
	}
		
	return timeEdited;		       

}
/**
 * Format any String holding time into an Integer.
 *
 * Creation date: (3/23/2005 9:56:28 AM)
 * @deprecated
 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
 */
public static Integer formatSetTime(String inTime) {
	
	Integer noValue = new Integer("0"); 
		
	if ((inTime == null) || (inTime.equals ("0")) || (inTime.trim().equals ("")))
		return noValue;

	String        timeEdited = "";
	String        timeAsIs   = "";
	java.sql.Time timeSQL    = null;
	Integer       timeValue  = new Integer("0");
				
	try {

		if (inTime.length() == 8)
			timeSQL = java.sql.Time.valueOf(inTime);
		else {
			System.out.println("Invalid time error at com.treetop." +
							   "TimeUtilities.formatSetTime(String): " + inTime);
			return noValue;
		}				           
	}
	catch (Exception e) {	
		System.out.println("Time conversion error at com.treetop." +
						   "TimeUtilities.formatSetTime(String): " + inTime + " " + e);
		return noValue;
	}
		
	try {
				
		timeEdited = timeSQL.toString();			
		timeAsIs   = timeEdited.substring(0, 2) + 		// Hours
		             timeEdited.substring(3, 5) +		// Minutes
		             timeEdited.substring(6, 8);		// Seconds	
		timeValue  = new Integer(timeAsIs);	
	}
	catch (Exception e) {	
		System.out.println("java.sql.Time conversion error at com.treetop." +
						   "TimeUtilities.formatSetTime(String): " + inTime + " " + e);
		return noValue;
	}
		
	return timeValue;		       

}
/**
 * Use the Main method to check other methods.
 * Creation date: (10/28/2003 2:41:36 PM)
 * @param args java.lang.String[]
 * @deprecated
 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
 */
public static void main(String[] args) 
{
try
{
	// use this time for manipulation
	java.sql.Time useTime    = java.sql.Time.valueOf("10:20:30");
	int           useSeconds = 222222;

	//TEST METHODS
	int seconds      = convertSQLTimeToSeconds(useTime);

	String timeValue = convertSecondsToTime(useSeconds);
		
}
catch(Exception e)
{
	System.out.println("Exception Caught in TimeUtilities.main: " + e);
}
}
}
