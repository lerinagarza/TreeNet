package com.treetop;

import java.util.*;
import com.ibm.as400.access.*;
import java.math.*;
/**
 * Returns an array of current system dates.
 *
 * [0] hhmmss
 * [1] mmddyy
 * [2] mmddyyyy
 * [3] yyyymmdd
 * [4] mm/dd/yy
 * [5] mm/dd/yyyy
 * [6] mon, tue,
 * [7] yyyy-mm-dd
 * [8] hh:mm:ss
 * [9] mmmmmmmmm dd, yyyy
 * Creation date: (8/29/2002 1:07:29 PM)
 * @author: 
 * Modified 01/06/2009 - Change IP address to access new AS400 (10.6.100.3)
 * @deprecated
 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
 */
public class SystemDate {
	

/**
 * Insert the method's description here.
 * Creation date: (8/30/2002 10:07:37 AM)
 * @deprecated
 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
 */
public SystemDate() { super(); }
/**
 * Insert the method's description here.
 * Creation date: (8/29/2002 1:12:33 PM)
 * @return java.util.Vector
 * 
 * @deprecated
 * 1/26/12 - TWalton - use UtilityDateTime -- add to that object if necessary
 */
public static String[] getSystemDate() 
{

	// Establish AS400 session
	
	//AS400 sys = new AS400("10.6.100.3", "dauser", "web230502");
	// 10/28/11 TWalton - Get rid of the IP address point to Lawson
	AS400 sys = new AS400("lawson.treetop.com", "dauser", "web230502");
	
	// a variable return value
	String returnValue = null;

	
	// Return array
	String dates[] = new String[10];
	
	// Load the time array element.
	returnValue = getSystemTime(sys);
	dates[0] = returnValue;
	//System.out.println("theTime is " + dates[0]);

	
	// Load the date(MMDDYY) array element.
	returnValue = getSystemDate1(sys);
	dates[1] = returnValue;
	//System.out.println("date[1] is " + dates[1]);

	// Load the date(MMDDYYYY) array element.
	returnValue = getSystemDate2(sys);
	dates[2] = returnValue;
	//System.out.println("date[2] is " + dates[2]);

	// Load the date(YYYYMMDD) array element.
	returnValue = getSystemDate3(sys);
	dates[3] = returnValue;
	//System.out.println("date[3] is " + dates[3]);

	// Load the date(MM/DD/YY) array element.
	returnValue = getSystemDate4(sys);
	dates[4] = returnValue;
	//System.out.println("date[4] is " + dates[4]);

	// Load the date(MM/DD/YYYY) array element.
	returnValue = getSystemDate5(sys);
	dates[5] = returnValue;
	//System.out.println("date[5] is " + dates[5]);

	// Load the day of the week array element.
	returnValue = getTheDay(sys);
	dates[6] = returnValue;
	//System.out.println("date[6] is " + dates[6]);

	// Load the date(YYYY-MM-DD) array element.
	returnValue = getSystemDate7(sys);
	dates[7] = returnValue;
	//System.out.println("date[7] is " + dates[7]);

	// Load the time array element (hh:mm:ss).
	returnValue = getSystemTime(sys);
	dates[8] = returnValue.substring(0,2) + ":" +
			   returnValue.substring(2,4) + ":" +
			   returnValue.substring(4,6);
	//System.out.println("theTime is " + dates[8]);

	// Load the date(mmmmmmmm dd, yyyy) array element.
	returnValue = getSystemDate9(sys);
	dates[9] = returnValue;
	//System.out.println("date[9] is " + dates[9]);
	 
	sys.disconnectService(AS400.COMMAND);
	return dates;
}
/**
 * Insert the method's description here.
 * Creation date: (8/30/2002 11:03:31 AM)
 * @return java.lang.String
 * @param param com.ibm.as400.access.AS400
 */
private static String getSystemDate1(AS400 sys) {
	String theDate = null;
	
	// Variable used to retrieve the system value requested 
	String systemValue;
	
	
	// Load the date variable.

	try
	{
	   SystemValue sv = new SystemValue(sys, "QMONTH");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	      
	   theDate = systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QDAY");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	         
	   theDate = theDate + systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QYEAR");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	         
	   theDate = theDate + systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}
	
	return theDate;
}
/**
 * Insert the method's description here.
 * Creation date: (8/30/2002 11:03:31 AM)
 * @return java.lang.String
 * @param param com.ibm.as400.access.AS400
 */
private static String getSystemDate2(AS400 sys) {
	String theDate = null;
	int i;
	
	// Variable used to retrieve the system value requested 
	String systemValue;
	
	
	// Load the date variable.

	try
	{
	   SystemValue sv = new SystemValue(sys, "QMONTH");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	      
	   theDate = systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QDAY");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	      
	   theDate = theDate + systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QYEAR");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;


	   try
	   {
		  i = Integer.parseInt(systemValue);

		  if (i < 50)
		     systemValue = "20" + systemValue;
		  else
		     systemValue = "19" + systemValue;
		      
	   } catch (NumberFormatException e) {
		   	System.out.println("com.treetop.SystemDate error - " + e);
	   }
	   
	   theDate = theDate + systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}
	
	return theDate;
}
/**
 * Insert the method's description here.
 * Creation date: (8/30/2002 11:03:31 AM)
 * @return java.lang.String
 * @param param com.ibm.as400.access.AS400
 */
private static String getSystemDate3(AS400 sys) {
	String theDate = null;
	int i;
	
	// Variable used to retrieve the system value requested 
	String systemValue;
	
	
	// Load the date variable.

	try
	{
	   SystemValue sv = new SystemValue(sys, "QYEAR");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;


	   try
	   {
		  i = Integer.parseInt(systemValue);

		  if (i < 50)
		     systemValue = "20" + systemValue;
		  else
		     systemValue = "19" + systemValue;
		      
	   } catch (NumberFormatException e) {
		   	System.out.println("com.treetop.SystemDate error - " + e);
	   }
	   
	   theDate = systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}
	   

	try
	{
	   SystemValue sv = new SystemValue(sys, "QMONTH");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	      
	   theDate = theDate + systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QDAY");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	      
	   theDate = theDate + systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	
	   
	    
	   
	      
	   
	
	return theDate;
}
/**
 * Insert the method's description here.
 * Creation date: (8/30/2002 11:03:31 AM)
 * @return java.lang.String
 * @param param com.ibm.as400.access.AS400
 */
private static String getSystemDate4(AS400 sys) {
	String theDate = null;
	
	// Variable used to retrieve the system value requested 
	String systemValue;
	
	
	// Load the date variable.

	try
	{
	   SystemValue sv = new SystemValue(sys, "QMONTH");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	      
	   theDate = systemValue + "/";

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QDAY");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	      
	   theDate = theDate + systemValue + "/";

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QYEAR");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	     
	   theDate = theDate + systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}
	
	return theDate;
}
/**
 * Insert the method's description here.
 * Creation date: (8/30/2002 11:03:31 AM)
 * @return java.lang.String
 * @param param com.ibm.as400.access.AS400
 */
private static String getSystemDate5(AS400 sys) {
	String theDate = null;
	int i;
	
	// Variable used to retrieve the system value requested 
	String systemValue;
	
	
	// Load the date variable.

	try
	{
	   SystemValue sv = new SystemValue(sys, "QMONTH");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	      
	   theDate = systemValue + "/";

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QDAY");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;  
	      
	   theDate = theDate + systemValue + "/";

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QYEAR");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;


	   try
	   {
		  i = Integer.parseInt(systemValue);

		  if (i < 50)
		     systemValue = "20" + systemValue;
		  else
		     systemValue = "19" + systemValue;
		      
	   } catch (NumberFormatException e) {
		   	System.out.println("com.treetop.SystemDate error - " + e);
	   }
	   
	   theDate = theDate + systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}
	
	return theDate;
}
/**
 * Insert the method's description here.
 * Creation date: (8/30/2002 11:03:31 AM)
 * @return java.lang.String
 * @param param com.ibm.as400.access.AS400
 */
private static String getSystemDate7(AS400 sys) {
	String theDate = null;
	int i;
	
	// Variable used to retrieve the system value requested 
	String systemValue;
	
	
	// Load the date variable.

    try
	{
	   SystemValue sv = new SystemValue(sys, "QYEAR");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;


	   try
	   {
		  i = Integer.parseInt(systemValue);

		  if (i < 50)
		     systemValue = "20" + systemValue;
		  else
		     systemValue = "19" + systemValue;
		      
	   } catch (NumberFormatException e) {
		   	System.out.println("com.treetop.SystemDate error - " + e);
	   }
	   
	   theDate = systemValue + "-";

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}
	
	try
	{
	   SystemValue sv = new SystemValue(sys, "QMONTH");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	      
	   theDate = theDate + systemValue + "-";

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QDAY");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;  
	      
	   theDate = theDate + systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	
	
	return theDate;
}
/**
 * Insert the method's description here.
 * Creation date: (8/30/2002 11:03:31 AM)
 * @return java.lang.String
 * @param param com.ibm.as400.access.AS400
 */
private static String getSystemDate9(AS400 sys) {
	String theDate = null;
	int i;
	
	// Variable used to retrieve the system value requested 
	String systemValue;
	
	
	// Load the date variable.

	try
	{
	   SystemValue sv = new SystemValue(sys, "QMONTH");
	   systemValue = (String)sv.getValue();

	   theDate = systemValue;
	   if (theDate.equals("01"))
	      theDate = "January ";
	   if (theDate.equals("02"))
	      theDate = "February ";
   	   if (theDate.equals("03"))
	      theDate = "March ";
   	   if (theDate.equals("04"))
	      theDate = "April ";
   	   if (theDate.equals("05"))
	      theDate = "May ";
   	   if (theDate.equals("06"))
	      theDate = "June ";
   	   if (theDate.equals("07"))
	      theDate = "July ";
   	   if (theDate.equals("08"))
	      theDate = "August ";
   	   if (theDate.equals("09"))
	      theDate = "September ";
   	   if (theDate.equals("10"))
	      theDate = "October ";
   	   if (theDate.equals("11"))
	      theDate = "November ";
   	   if (theDate.equals("12"))
	      theDate = "December ";

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QDAY");
	   systemValue = (String)sv.getValue();

	   theDate = theDate + systemValue + ", ";

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QYEAR");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;


	   try
	   {
		  i = Integer.parseInt(systemValue);

		  if (i < 50)
		     systemValue = "20" + systemValue;
		  else
		     systemValue = "19" + systemValue;
		      
	   } catch (NumberFormatException e) {
		   	System.out.println("com.treetop.SystemDate error - " + e);
	   }
	   
	   theDate = theDate + systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}
	
	return theDate;
}
/**
 * Insert the method's description here.
 * Creation date: (8/30/2002 11:03:31 AM)
 * @return java.lang.String
 * @param param com.ibm.as400.access.AS400
 */
private static String getSystemTime(AS400 sys) {
	String theTime = null;
	
	// Variable used to retrieve the system value requested 
	String systemValue;
	
	
	// Load the time array element.

	try
	{
	   SystemValue sv = new SystemValue(sys, "QHOUR");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	      
	   theTime = systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QMINUTE");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	      
	   theTime = theTime + systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	try
	{
	   SystemValue sv = new SystemValue(sys, "QSECOND");
	   systemValue = (String)sv.getValue();

	   if (systemValue.length() == 1)
	      systemValue = "0" + systemValue;
	          
	   theTime = theTime + systemValue;

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}
	
	return theTime;
}
/**
 * Insert the method's description here.
 * Creation date: (8/30/2002 11:03:31 AM)
 * @return java.lang.String
 * @param param com.ibm.as400.access.AS400
 */
private static String getTheDay(AS400 sys) {
	String theDay = null;
	
	// Variable used to retrieve the system value requested 
	String systemValue;
	
	
	// Load the date variable.

	try
	{
	   SystemValue sv = new SystemValue(sys, "QDAYOFWEEK");
	   systemValue = (String)sv.getValue();
	      
	   theDay = systemValue;
	   if (theDay.equals("*SUN"))
	      theDay = "Sunday";
	   if (theDay.equals("*MON"))
	      theDay = "Monday";
	   if (theDay.equals("*TUE"))
   	      theDay = "Tuesday";
	   if (theDay.equals("*WED"))
   	      theDay = "Wednesday";
	   if (theDay.equals("*THU"))
   	      theDay = "Thursday";
	   if (theDay.equals("*FRI"))
   	      theDay = "Friday";
	   if (theDay.equals("*SAT"))
   	      theDay = "Saturday";

	} catch (Exception e) {
		System.out.println("com.treetop.SystemDate error - " + e);
	}

	
	return theDay;
}
/**
 * Insert the method's description here.
 * Creation date: (8/30/2002 9:48:47 AM)
 */
public static void main() 
{
	// 10/28/11 TWalton - comment out code not needed
//	AS400 sys = new AS400("10.6.100.3", "dauser", "web230502");

	//String theDate[] = getSystemDate();

}
}
