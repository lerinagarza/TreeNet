package com.treetop;

import java.text.*;
import java.math.*;
import java.util.*;
import sun.misc.*;
/**
 * Test incoming date against current date minus 30 days.
 * Creation date: (6/21/2002 9:36:15 AM)
 * @author: William T Haile
 * @deprecated
 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
 * 
 */
public class CheckDate {
	
	/**
	 * @deprecated
	 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
	 */	
	public static String[] changeDate(String param, int daysToAdd)
	 throws Exception 
	{
		String[] theDate = validateDate(param);
		
		if (!theDate[6].equals(""))
			throw new Exception(theDate[6] + "(" + param + ")"  );
		
		Integer integerYear = new Integer(theDate[4].substring(0,4) );
		int intYear = integerYear.intValue();
		
		Integer integerMonth = new Integer(theDate[4].substring(4,6) );
				int intMonth = integerMonth.intValue();
				
		Integer integerDay = new Integer(theDate[4].substring(6,8) );
				int intDay = integerDay.intValue();
				
		// Gregorian calendar months start at 0 end at 11.
		//  for example 0 = janurary, 11 = December
		//  subtract 1 from current requested month.
		intMonth = intMonth - 1;
		
		GregorianCalendar cal = new GregorianCalendar(intYear, intMonth, intDay);
		cal.add(Calendar.DATE,daysToAdd);
	 
		intYear = cal.get(Calendar.YEAR);
		intMonth = cal.get(Calendar.MONTH);
		intDay = cal.get(Calendar.DATE);
		
		// after date change add 1 to month.
		// if necessary add one to year.
		intMonth = intMonth + 1;
		
		param = intMonth + "/" + intDay + "/" + intYear;
		theDate = validateDate(param);
		
		if (!theDate[6].equals(""))
			throw new Exception(theDate[6] + "(" + param + ")"  );
		
		return theDate;
	}
/**
 * Insert the method's description here.
 * Creation date: (6/21/2002 10:46:18 AM)
 * @param args java.lang.String[]
 * @deprecated
 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
 */
public static boolean checkIt(int inday, int inyear) {


	Calendar now = Calendar.getInstance();
	int testday = now.get(Calendar.DAY_OF_YEAR);
	int curryear  = now.get(Calendar.YEAR);
	
	if (curryear > inyear) {
		return false;
	}

    if ((curryear == inyear) && (testday > inday)) {
	    return false;
    }	
	

	return true;
	
	
	}
/**
 * Return authorization information (userid password)
 * Creation date: (6/27/2002 2:28:40 PM)
 * @return java.lang.String[]
 * @param auth java.lang.String
 * @deprecated
 * 1/26/12 - TWalton - Put within the security Service area
 */
public static String[] decodeBasicAuth(String auth) {
	
	
	if (auth == null || (!auth.toUpperCase().startsWith("BASIC ") &&
		auth.length() > 6))
	return new String [] {"null1","null2"}; // we only do BASIC

	String userPassEncoded = auth.substring(6);

	sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();

	String userPassDecoded = null;

	try
	{

		userPassDecoded = new String(dec.decodeBuffer(userPassEncoded));

	}

	catch (java.io.IOException e)

	{
		return new String [] {"null1","null2"};
	}

	int c = userPassDecoded.indexOf(":");

	return new String [] {userPassDecoded.substring(0,c),
		                  userPassDecoded.substring(c+1)};
	
	

}

/**
 * Insert the method's description here.
 * Creation date: (9/6/2002 3:52:16 PM)
 * @return java.lang.String
 * @param param java.lang.String
 *
 * The returning String array.
 * [0] mm/dd/yy
 * [1] mm/dd/yyyy
 * [2] mmddyy
 * [3] mmddyyyy
 * [4] yyyymmdd
 * [5] cyymmdd 
 * [6] error message
 * [7] yyyy-mm-dd
 * 
 * @deprecated
 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
 *
 */
public static String[] validateDate(String param) {
	
	// Initialize return parameter array to blanks.
	
	String date[] = new String[8];
	date[0] = "";
	date[1] = "";
	date[2] = "";
	date[3] = "";
	date[4] = "";
	date[5] = "";
	date[6] = "";
	date[7] = "";
	
    
    
	// Test incoming date parameter must be > 3 and < 11.

	int paramLength = param.length();
	
    if (paramLength < 3 || paramLength > 11) {
	    date[6] = "The date is invalid - the length is incorrect.";
        return date;
    }
	

	// Test for slash("/") or dash("-"). Place parameter vaule minus (/ or -) into
	// test area string.
	// determine number of string positions between start and first "/" or "-" = month.
	// determine number of string positions between first and second "/" or "-" = day.
	// determine number of string positions between second and "/" or "-" = year.

	int first = 0;
	int second = 0;
	String testarea = "";
	String month = "";
	String day = "";
	String year = "";
	String cent = "";
	
	for (int i = 0, j = 1; i < paramLength; i++, j++)
	{
	 	String skip = "no";  
			
	    if (param.substring(i, j).equals("/"))
	      skip = "yes";
	      
	    if (param.substring(i, j).equals("-"))
	      skip = "yes";  

	    if (skip.equals("no"))
	       testarea = testarea + param.substring(i, j);
	    else {
		    if (first == 0 ) {
		      first = i;
		      month = param.substring(0, (j - 1));
		    }
		    else { 
		      second = i - first - 1;
		      day = param.substring((first + 1), (j - 1));
		      year = param.substring((first + second + 2), (paramLength));
		    }
	    }
	}


	// Zero or two slashes or dashes (/-) must have been used.
	
	if (first != 0 && second == 0) {
		date[6] = "The date is invalid - invalid use of date seperators..";
	    return date;
	}

	
    // If no slashes or dashes were used in the incomimg date parameter.
    // then determine the month day and year values from total length.
     
	if (first == 0) {

		if (param.length() == 8) {
			month = param.substring(0, 2);
			day = param.substring(2, 4);
			year = param.substring(4, 8);
		}

		if (param.length() == 7) {
			month = param.substring(0, 1);
			day = param.substring(1, 3);
			year = param.substring(3, 7);
		}

		if (param.length() == 6) {
			month = param.substring(0, 2);
			day = param.substring(2, 4);
			year = param.substring(4, 6);
		}

		if (param.length() == 5) {
			month = param.substring(0, 1);
			day = param.substring(1, 3);
			year = param.substring(3, 5);
		}

		if (param.length() == 4) {
			month = param.substring(0, 1);
			day = param.substring(1, 2);
			year = param.substring(2, 4);
		}

		if (param.length() == 3) {
			month = param.substring(0, 1);
			day   = param.substring(1, 2);
			year  = param.substring(2, 3);
		}
	} 
		    
	
	// Ensure month and day lengths are 2 long, not one.
	// Ensure year length is 4 long.

    if (month.length() == 1)
	    month = "0" + month;

  	if (day.length() == 1)
	    day = "0" + day;

	if (year.length() == 1)
		year = "0" + year;
		
  	if (year.length() == 2) {

  		int z = year.compareTo("70");
    
	    if (z <= 0) {
	        year = "20" + year;
	    } 
	    else {
	        year = "19" + year;
	    }      
	}
  	
	// Load cent field.

	if (year.substring(0,2).equals("19"))
	{
		cent = "0";
	} else {
		cent = "1";
	}
	 
	// Validate incoming date.

	boolean validDate = false;

	try {
		
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	    String datetest = year + "/" + month + "/" + day;
	    // parse the text into a Date object and back.
	    String returndate = sdf.format(sdf.parse(datetest)).trim();
	    //return true only if the string passed through a parse and
	    //format method without error, and returned the same value.

	    if (datetest.equalsIgnoreCase(returndate)) 
	        validDate = true; 
	}
	catch (Exception e) {
		date[6] = "The date is invalid - not a valid calendar date.";
		return date;
	}
		
	// Check validDate value after parse test.
	
	if (!validDate) {
		date[6] = "The date is invalid - not a valid calendar date.";
		return date;
	}
		 
	// Load the date array with the validated date values.
	date[0] = month + "/" + day + "/" + year.substring(2, 4);
	date[1] = month + "/" + day + "/" + year;
	date[2] = month + day + year.substring(2, 4);
	date[3] = month + day + year;
	date[4] = year + month + day;
	date[5] = cent + year.substring(2, 4) + month + day;
	date[7] = year + "-" + month + "-" + day;
	return date;
}

	/**
	 * 
	 * @author thaile
	 *
	 * Convert a string from yyyymmdd to mmddyyyy.
	 * 
	 * @deprecated
	 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
	 * 
	 */
	public static String convertYMDtoMDY(String inDate){
		
		String outDate = inDate;	
		
		if (inDate != null && inDate.length() > 7)
		{
			String mmddyyyy = inDate.substring(0, 4);
			mmddyyyy = "/" + mmddyyyy; 
			mmddyyyy = inDate.substring(6, 8) + mmddyyyy;
			mmddyyyy = "/" + mmddyyyy;
			mmddyyyy = inDate.substring(4, 6) + mmddyyyy;
			outDate = mmddyyyy;
		}
		
		return outDate;
	}
}
