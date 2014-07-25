package com.treetop;

import java.text.*;
import java.util.*;
import java.math.*;
import sun.misc.*;
import com.ibm.as400.access.*;
/**
 * Returns an array of formated dates.
 *
 * [0] date validation message
 * [1] mmddyy
 * [2] mmddyyyy
 * [3] yyyymmdd
 * [4] mm/dd/yy
 * [5] mm/dd/yyyy
 * [6] mon, tue,
 * [7] yyyy-mm-dd
 * [8] hh:mm:ss
 * [9] mmmmmmmmm dd, yyyy
 * [10] dd/mmm/yy
 *
 * Creation date: (5/20/2003 2:07:29 PM)
 *
 * @author: David Eisenheim
 * 10/28/11 - TWalton - deleted methods getDate100, getDatePrism and getDateStandard
 *     these were accessing an IP address that was not valid for more than three years
 * @deprecated
 * 1/26/12 - TWalton - Should use UtilityDateTime going forward    
 */
public class GetDate {
/**
 * Determine the standard date from the Prism date format.
 *
 * Creation date: (10/11/2005 4:36:39 PM)
 *  * @deprecated
 * 1/26/12 - TWalton - Should use UtilityDateTime going forward    
 */
public static Integer getDatePrismFormat(Integer inDate) 
{
	Integer standardDate  = new Integer(0);

	try {
		
		String  dateFormat  = inDate.toString();	
		String  dateCentury = dateFormat.substring(0,1);
		String  dateValue   = dateFormat.substring(1,7);
		
		String century = "19";				// 1900 - 1999 (Default)
		
		if (dateCentury.equals("1")) 		// 2000 - 2099
			century = "20";
		if (dateCentury.equals("2")) 		// 2100 - 2199
			century = "21";
			
		String newDate = century + dateValue;
		standardDate = new Integer(newDate);
		return standardDate;				
		
	}
	catch (Exception e) { 
		return standardDate;
	}
	
} 
/**
 * GetDate constructor comment.
 *  * @deprecated
 * 1/26/12 - TWalton - Should use UtilityDateTime going forward    
 */
public GetDate() {
	
	super();
}
/**
 * Get a drop down list of all the different date formats available.
 * [1] mmddyy
 * [2] mmddyyyy
 * [3] yyyymmdd
 * [4] mm/dd/yy
 * [5] mm/dd/yyyy  (Default)
 * [7] yyyy-mm-dd
 * [9] mmmmmmmmm dd, yyyy
 * [10] dd/mmm/yy 
 *   Send in what you want the field to be named.
 *      and the value if available.
 * Creation date: (1/12/2004 10:36:26 AM)
 * @return java.lang.String
 *  * @deprecated
 * 1/26/12 - TWalton - Should use UtilityDateTime going forward    
 */
public static String dateFormatDropDown(String fieldName,
	                                    String fieldValue) 
{

	String dropDown = "" +
		   "<select name='" + fieldName.trim() + "'>";

    //**  1 mmddyy  **
    String selected = "";    
	if (fieldValue.trim().equals("1"))
	   selected = "selected='selected'";
	dropDown = dropDown + 
       	       "<option value='1' " + selected + ">" +
               "mmddyy";
    //**  2 mmddyyyy  **
    selected = "";
	if (fieldValue.trim().equals("2"))
	   selected = "selected='selected'";
	dropDown = dropDown + 
       	       "<option value='2' " + selected + ">" +
               "mmddyyyy";
    //**  3 yyyymmdd  **
    selected = "";
	if (fieldValue.trim().equals("3"))
	   selected = "selected='selected'";
	dropDown = dropDown + 
       	       "<option value='3' " + selected + ">" +
               "yyyymmdd";                
    //**  4 mm/dd/yy  **
    selected = "";
	if (fieldValue.trim().equals("4"))
	   selected = "selected='selected'";
	dropDown = dropDown + 
       	       "<option value='4' " + selected + ">" +
               "mm/dd/yy"; 
    //**  5 mm/dd/yyyy  **(defaulted)
    selected = "";
	if (fieldValue.trim().equals("5") ||
		fieldValue.trim().equals(""))
	   selected = "selected='selected'";
	dropDown = dropDown + 
       	       "<option value='5' " + selected + ">" +
               "mm/dd/yyyy";                           
    //**  7 yyyy-mm-dd  **
    selected = "";
	if (fieldValue.trim().equals("7"))
	   selected = "selected='selected'";
	dropDown = dropDown + 
       	       "<option value='7' " + selected + ">" +
               "yyyy-mm-dd";
    //**  9 mmmmmmmmm dd, yyyy  **
    selected = "";
	if (fieldValue.trim().equals("9"))
	   selected = "selected='selected'";
	dropDown = dropDown + 
       	       "<option value='9' " + selected + ">" +
               "mmmmmmmmm dd, yyyy";  
    //**  10 dd/mmm/yy   **
    selected = "";
	if (fieldValue.trim().equals("10"))
	   selected = "selected='selected'";
	dropDown = dropDown + 
       	       "<option value='10' " + selected + ">" +
               "dd/mmm/yy";                  

    dropDown = dropDown + "</select>";  	   		   
    
    return dropDown;
}
/**
 * Validate a date and convert to all supported formats.
 *
 * Creation date: (5/20/2003 2:45:20 PM)
 *
 * String[0]  = Message ("Invalid Date" or left blank)
 * String[1]  = MMDDYY					011703
 * String[2]  = MMDDCCYY				01172003
 * String[3]  = CCYYMMDD				20030117
 * String[4]  = MM/DD/YY				01/17/03
 * String[5]  = MM/DD/CCYY				01/17/2003
 * String[6]  = Name of Day				Friday
 * String[7]  = CCYY-MM-DD				2003-01-17
 * String[8]  = YYDDD					03017
 * String[9]  = Name of Month DD, CCYY	January 17, 2003
 * String[10] = DD/Month Name (abv)/YY	17/JAN/03
 * String[11] = Day of Week (number)    6
 *  * @deprecated
 * 1/26/12 - TWalton - Should use UtilityDateTime going forward    
 */
public static String[] getDates(java.sql.Date inDate) 
{ 

	String[] dateArray = new String[12];
	
	String stringDate  = inDate.toString();
	       stringDate  = stringDate.replace('-','/');
	       
	boolean validDate  = false;
	     dateArray[0]  = "Invalid Date";

	String ccyy        = "";
	String cent        = "";
	String year        = "";
	String month       = "";
	String day         = "";
	String[] codeMonth = new String[] {"   ", "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
		                                      "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
	
	try {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	    // parse the string date into a date object and back.
	    
	    String returnDate = dateFormat.format(dateFormat.parse(stringDate)).trim();
	    
	    // return true only if the string passed through a parse and
	    // format method is without error, and returned the same value.
	    
	    if (stringDate.equalsIgnoreCase(returnDate)) {
	        validDate    = true;
	        dateArray[0] = "";

	        // break the date into its compontent parts.
	        
			cent  = stringDate.substring(0,2);
			year  = stringDate.substring(2,4);
			month = stringDate.substring(5,7);
			day   = stringDate.substring(8,10);
			ccyy  = cent + year;
			
			// build Gregorian calendar with date parameter.
			
			int cy = Integer.valueOf(ccyy).intValue();
			int mm = Integer.valueOf(month).intValue()-1;
			int dd = Integer.valueOf(day).intValue();
			GregorianCalendar calendar = new GregorianCalendar(cy, mm, dd);
					
			// build each date format.
			
			dateArray[1] = month + day + year;
			dateArray[2] = month + day + cent + year;
			dateArray[3] = cent + year + month + day;
			dateArray[4] = month + "/" + day + "/" + year;
			dateArray[5] = month + "/" + day + "/" + cent + year;

			DateFormat format = DateFormat.getDateInstance(DateFormat.FULL);
            String fullDate = format.format(inDate);
            int x = fullDate.indexOf(",");
            dateArray[6] = fullDate.substring(0,x);            
		
			dateArray[7] = cent + year + "-" + month + "-" + day;
			
			int julian = calendar.get(Calendar.DAY_OF_YEAR);
			if (julian >= 100)
				dateArray[8] = year + julian;
			else {
				if (julian >= 10)
					dateArray[8] = year + "0" + julian;
				else	
					dateArray[8] = year + "00" + julian;
			}		   
					
	        format = DateFormat.getDateInstance(DateFormat.LONG);
            dateArray[9] = format.format(inDate);

            Integer monthNumber  = new Integer(month);
	        String  monthName    = codeMonth[monthNumber.intValue()];
	        dateArray[10] = day + "/" + monthName + "/" + year;
	        
			int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
			dateArray[11] = Integer.toString(weekDay);

	    }
	    	        
	}	
	
	catch (Exception e)	{
		  System.out.println("com.treetop.getDates(java.sql.Date): " + e);
	}
		
	return dateArray; 
}
/**
 * Format any Integer holding date into jsp display format.
 *
 * Creation date: (3/23/2005 9:28:06 AM)
 *  * @deprecated
 * 1/26/12 - TWalton - Should use UtilityDateTime going forward    
 */
public static String formatGetDate(Integer inDate) 
{
		
	if ((inDate == null) || (inDate.intValue() == 0))
		return null;
			
	String        dateAsIs   = "";
	String        dateToSQL  = "";
	java.sql.Date dateSQL    = null;
	String        dateEdited = "";
				
	try {
			
		dateAsIs = inDate.toString();
		if (dateAsIs.length() == 8)
			dateToSQL = dateAsIs.substring(0, 4) + "-" +	// Century,Year
						dateAsIs.substring(4, 6) + "-" +	// Month
						dateAsIs.substring(6, 8); 			// Day
		else {
			System.out.println("Invalid date error at com.treetop." +
							   "GetDate.formatGetDate(Integer): " + dateAsIs);
			return null;
		}				           
	}
	catch (Exception e) {	
		System.out.println("Date conversion error at com.treetop." +
						   "GetDate.formatGetDate(Integer): " + dateAsIs + " " + e);
		return null;
	}
		
	try {
			
		dateSQL = java.sql.Date.valueOf(dateToSQL);
		String dateCheck = dateSQL.toString();
			
		if (dateCheck.trim().equals (dateToSQL.trim())) {
			String[] dateFormats = GetDate.getDates(dateSQL);
			dateEdited = dateFormats[5];
		}
		else {
			System.out.println("Invalid date error at com.treetop." +
							   "GetDate.formatGetDate(Integer): " + dateAsIs);
			return null;				
		}
			
	}
	catch (Exception e) {	
		System.out.println("java.sql.Date conversion error at com.treetop." +
						   "GetDate.formatGetDate(Integer): " + dateAsIs + " " + e);
		return null;
	}
		
	return dateEdited;		       

}
/**
 * Format any String holding date into an Integer.
 *
 * Creation date: (3/23/2005 9:56:28 AM)
 *  * @deprecated
 * 1/26/12 - TWalton - Should use UtilityDateTime going forward    
 */
public static Integer formatSetDate(String inDate) 
{
	
	Integer noValue = new Integer("0"); 
		
	if ((inDate == null) || (inDate.equals ("0")) || (inDate.trim().equals ("")))
		return noValue;

	String        dateToSQL = "";
	java.sql.Date dateSQL   = null;
	Integer       dateValue = new Integer("0");
				
	try {

		if (inDate.length() == 10)
			dateToSQL = inDate.substring(6, 10) + '-' +		// Century,Year
						inDate.substring(0,  2) + '-' +		// Month
						inDate.substring(3,  5); 			// Day
		else {
			System.out.println("Invalid date error at com.treetop." +
							   "GetDate.formatSetDate(String): " + inDate);
			return noValue;
		}				           
	}
	catch (Exception e) {	
		System.out.println("Date conversion error at com.treetop." +
						   "GetDate.formatSetDate(String): " + inDate + " " + e);
		return noValue;
	}
		
	try {
		
		dateSQL = java.sql.Date.valueOf(dateToSQL);
		String dateCheck = dateSQL.toString();
			
		if (dateCheck.trim().equals (dateToSQL.trim())) {
			String[] dateFormats = GetDate.getDates(dateSQL);		
				     dateValue   = new Integer(dateFormats[3]);	
		}
		else {
			System.out.println("Invalid date error at com.treetop." +
							   "GetDate.formatSetDate(String): " + inDate);
			return noValue;				
		}			

	}
	catch (Exception e) {	
		System.out.println("java.sql.Date conversion error at com.treetop." +
						   "GetDate.formatSetDate(String): " + inDate + " " + e);
		return noValue;
	}
		
	return dateValue;		       

}
/**
 * Retrieve today's date from the system.
 *
 * Creation date: (04/27/2005 3:17:22 PM)
 *  * @deprecated
 * 1/26/12 - TWalton - Should use UtilityDateTime going forward    
 */

public static Integer getDateToday() 
{

	Integer todaysDate = new Integer(0);	
	
	try {
		
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy");		
		 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new java.util.Date()); 
		
		String today = format.format(calendar.getTime());	
		todaysDate   = GetDate.formatSetDate(today); 

	}
	catch (Exception e) { 
		System.out.println("com.treetop.getDateToday() exception: " + e);
	}
	
	return todaysDate;
} 
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 *  * @deprecated
 * 1/26/12 - TWalton - Should use UtilityDateTime going forward    
 */
public static void main(String[] args) 
{
	
	try {
		Integer prismDate = new Integer("1051231");	
		Integer standard  = getDatePrismFormat(prismDate);
		String stop = "stop";
	}

	catch (Exception e)	{
		  System.out.println("com.treetop.GetDatePrismFormat: " + e);
	}  

	try {
		java.sql.Date theDate = java.sql.Date.valueOf("2003-01-17");		
		String[] returnDate = getDates(theDate);		
		
	}

	catch (Exception e)	{
		  System.out.println("com.treetop.GetDate.main() error - " + e);
	}  
		
}
}
