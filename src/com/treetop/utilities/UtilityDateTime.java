/*
 * Created on March 5, 2008
 */
package com.treetop.utilities;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ibm.as400.access.*;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.Lot;
import com.treetop.services.BaseService;
import com.treetop.services.ServiceConnection;
import com.treetop.utilities.ConnectionStack;
import com.treetop.utilities.html.DropDownDual;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.DropDownTriple;
import com.treetop.viewbeans.CommonRequestBean;

/**
 * @author twalto .
 *
 * This Utility will deal with anything related to the Date and Time
 * Many times it will return the BusinessObject DateTime
 */
public class UtilityDateTime extends BaseService {
	
	public static final String library    = "M3DJDPRD";
	public static final String ttlibrary  = "DBPRD";

	/**
	 *  Constructor
	 */
	public UtilityDateTime() {
		super();
	}
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		try
		{
			// 10/23/13 TWalton Test new Method
			CommonRequestBean crb = new CommonRequestBean();
			crb.setDate("20130204");
			crb.setTime("133040");
			//crb.setTime("84421");
			
			DateTime dt = getDateFromyyyyMMddTimeFromhhmmss(crb);
			System.out.println("Stop Here for Test");
			
			
//			String x = "start here";
//			DateTime dt = getSystemDate();
//			x = "stop here";
//			DateTime dt2 = UtilityDateTime.addDaysToDate(dt, -10);
//			x = "stop here";
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (8/29/2002 1:12:33 PM)
	 *     // Rewritten 3/7/08 TWalton
	 *     moved from SystemDate Utility
	 * @return java.businessobjects.DateTime
	 */
	public static DateTime getSystemDate()  
	{
		DateTime dt = new DateTime();
		try
		{
			AS400 as400 = null;
			try {
            //  Get AS400 Object
				as400 = ServiceConnection.getAS400();
				Vector parms = new Vector();
				parms.add(as400);
				dt = loadFields("systemDate", parms);
				DateTime addYearPer = findM3FiscalYearPeriod(dt);
				dt.setM3FiscalYear(addYearPer.getM3FiscalYear());
				dt.setM3FiscalPeriod(addYearPer.getM3FiscalPeriod());
			} catch (Exception e) {
				dt.setDateErrorMessage("Error occured retreiving System Date. " + e);
			// return as400 Object
			} finally {
				if (as400 != null) {
					try {
						ServiceConnection.returnAS400(as400);
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}
		catch(Exception e)
		{
			dt.setDateErrorMessage("Error occured building System Date. " + e);
		}
		
		//used to run rebuild of Costing Work Files for fiscal 2012 after year ending
//		dt.setM3FiscalYear("2012");
//		dt.setDateFormatyyyyMMdd("20120731");
		
		return dt;	
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
	 * Build a DateTime Class which Includes:
	 * 		Fiscal Year from M3
	 * 		Fiscal Period from M3
	 *   Send in date Formatted yyyyMMdd
	 * 		OR Blank will Assume get System Date
	 * 
	 * Creation date: (3/8/2008 TWalton)
	 */
	public static DateTime getDateFormatM3Fiscal(String inDate) 
	{
		DateTime dt = new DateTime();	
		if (inDate.trim().equals("")) {
			dt = getSystemDate();
			
		} else {
			// Validate the Date Coming in 
			dt = getDateFromyyyyMMdd(inDate);
			
		}
		
		if (dt.getDateErrorMessage().trim().equals("")) {
			dt = findM3FiscalYearPeriod(dt);
		}
		
		return dt;
	}

	/**
	 * Send in Month Day and Year, this will be tested to see
	 *    if it is a valid date
	 * Creation date: (3/13/2008 TWalton)
	 * @return String - Error Message
	 */
	private static String validateDate(String month, String day, String century, String year) 
	{
		String returnProblem = "";
		boolean validDate = false;
		try {
			
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		    String datetest = century + year + "/" + month + "/" + day;
		    // parse the text into a Date object and back.
		    String returndate = sdf.format(sdf.parse(datetest)).trim();
		    //return true only if the string passed through a parse and
		    //format method without error, and returned the same value.
		    if (datetest.equalsIgnoreCase(returndate)) 
		        validDate = true; 
		}
		catch (Exception e) {
			returnProblem = "The date is invalid - not a valid calendar date.";
		}
		// Check validDate value after parse test.
		
		if (!validDate && returnProblem.trim().equals("")) 
			returnProblem = "The date is invalid - not a valid calendar date.";
		
		return returnProblem;
	}
	/**
	 * Send in Any Date this method will check to see if it is a Valid Date
	 * 	   Send in date in the yyyyMMdd format
	 * Creation date: (3/13/2008 TWalton)
	 * @return DateTime
	 */
	public static DateTime getDateFromyyyyMMdd(String inDate) 
	{
		DateTime dt = new DateTime();
		if (inDate.length() == 8)
	    {
			String century = inDate.substring(0,2); 
			String year = inDate.substring(2,4);
			String month = inDate.substring(4,6);
	    	String day = inDate.substring(6,8);
	    	dt.setDateErrorMessage(validateDate(month, day, century, year));
	    	if (dt.getDateErrorMessage().trim().equals(""))
	    	{	
	    		Vector parms = new Vector();
	    		// 	Send to the Load Fields
	    		parms.addElement(month); // Month
	    		parms.addElement(day); // Day
	    		parms.addElement(century); // Century
	    		parms.addElement(year); // Year
	    		try
				{
	    			dt = loadFields("getDate", parms);
				}
	    		catch(Exception e)
				{
	    			dt.setDateErrorMessage("The date is invalid - not a valid calendar date.");
				}
	    	}
	    }
	    else
	    	dt.setDateErrorMessage("The date is invalid - the length is incorrect.");
		
		return dt;
	}
/**
 * Load class fields based on loadType
 */
	
private static DateTime loadFields(String loadType,
								   Vector parms)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	
	DateTime dt = new DateTime();
	try
	{	
		// Begin with a Sent in dt that is already loaded with formated Dates
		if (loadType.equals("findYearPeriod"))
		{
			dt = (DateTime) parms.elementAt(0);
			ResultSet rs = (ResultSet) parms.elementAt(1);
			if (rs.getString("CPYEA4") != null)
			{
			   dt.setM3FiscalYear(rs.getString("CPYEA4"));
			   dt.setM3FiscalPeriod(rs.getString("CPPERI"));
			   dt.setM3FiscalWeek(rs.getString("CPWEEK"));
			}
		}
		else
		{
			String month = "";
			String day = "";
			String year = "";
			String century = "20";
			String hour = "";
			String minute = "";
			String second = "";
			if (loadType.equals("systemDate"))
			{
				AS400 sys = (AS400) parms.elementAt(0);
				// MONTH ***********************
				try
				{
				   SystemValue sv = new SystemValue(sys, "QMONTH");
				   month = (String) sv.getValue();
				   if (month.length() == 1)
				   	 month = "0" + month;
				} catch (Exception e) {
					System.out.println("com.treetop.UtilityDateTime.loadFields ");
					System.out.println(" Error on Retrieving System Month - " + e);
				}
				// DAY ***************************
				try
				{
				   SystemValue sv = new SystemValue(sys, "QDAY");
				   day = (String) sv.getValue();
				   if (day.length() == 1)
				   	 day = "0" + day;
				} catch (Exception e) {
					System.out.println("com.treetop.UtilityDateTime.loadFields ");
					System.out.println(" Error on Retrieving System Day - " + e);
				}
				// YEAR ***************************
				try
				{
				   SystemValue sv = new SystemValue(sys, "QYEAR");
				   year = (String) sv.getValue();
				   if (year.length() == 1)
				   	 year = "0" + year;
				} catch (Exception e) {
					System.out.println("com.treetop.UtilityDateTime.loadFields ");
					System.out.println(" Error on Retrieving System Year - " + e);
				}					
				// HOUR ***************************
				try
				{
				   SystemValue sv = new SystemValue(sys, "QHOUR");
				   hour = (String) sv.getValue();
				   if (hour.length() == 1)
				   	 hour = "0" + hour;
				} catch (Exception e) {
					System.out.println("com.treetop.UtilityDateTime.loadFields ");
					System.out.println(" Error on Retrieving System Hour - " + e);
				}
				// MINUTE *************************
				try
				{
				   SystemValue sv = new SystemValue(sys, "QMINUTE");
				   minute = (String) sv.getValue();
				   if (minute.length() == 1)
				   	 minute = "0" + minute;
				} catch (Exception e) {
					System.out.println("com.treetop.UtilityDateTime.loadFields ");
					System.out.println(" Error on Retrieving System Minute - " + e);
				}
				// SECOND *************************
				try
				{
				   SystemValue sv = new SystemValue(sys, "QSECOND");
				   second = (String) sv.getValue();
				   if (second.length() == 1)
				   	 second = "0" + second;
				} catch (Exception e) {
					System.out.println("com.treetop.UtilityDateTime.loadFields ");
					System.out.println(" Error on Retrieving System Second - " + e);
				}
			} // end of the SystemDate 
			if (loadType.equals("getDate"))
			{
				// Send in Vector with Month(0) Day(1) Century(2) Year(3)
				month   = (String) parms.elementAt(0);
				day     = (String) parms.elementAt(1);
				century = (String) parms.elementAt(2);
				year    = (String) parms.elementAt(3);
			}
		// Set Basic Data *****************************
			dt.setCentury(century);
			dt.setYear(year);
			dt.setMonth(month);
			dt.setDay(day);
		// Set ALL Formats ****************************
			dt.setDateFormatMMddyy(month + day + year);
			dt.setDateFormatMMddyySlash(month + "/" + day + "/" + year);
			dt.setDateFormatMMddyyyy(month + day + century + year);
			dt.setDateFormatMMddyyyySlash(month + "/" + day + "/" + century + year);
			dt.setDateFormatyyyyMMdd(century + year + month + day);
			dt.setDateFormatyyyyMMddDash(century + year + "-" +  month + "-" + day);
			dt.setDateFormatMonthNameddyyyy(getMonthLongName(month) + day + ", " + century + year);
			dt.setDateFormatddMMMyySlash(day + "/" + getMonthShortName(month) + "/" + year);
			String year100 = "1";
			if (century.trim().equals("19"))
				year100 = "0";
			dt.setDateFormat100Year(year100 + year + month + day);
			
			if (!loadType.equals("formatTime"))
			{	
			// Example DELETE
			int cy = Integer.valueOf(century + year).intValue();
			int mm = Integer.valueOf(month).intValue()-1;
			int dd = Integer.valueOf(day).intValue();
			GregorianCalendar calendar = new GregorianCalendar(cy, mm, dd);
			String julian = calendar.get(Calendar.DAY_OF_YEAR) + "";
			if (julian.trim().length() == 2)
				julian = "0" + julian;
			if (julian.trim().length() == 1)
				julian = "00" + julian;
			dt.setDateFormatJulian(julian);
			dt.setDateFormatyyddd(year +  julian);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			dt.setDayOfWeekNumber(dayOfWeek + "");
			if (dayOfWeek == 1)
			{
				dt.setDayOfWeekShort("SUN");
				dt.setDayOfWeek("Sunday");
			}
			if (dayOfWeek == 2)
			{
				dt.setDayOfWeekShort("MON");
				dt.setDayOfWeek("Monday");
			}
			if (dayOfWeek == 3)
			{
				dt.setDayOfWeekShort("TUE");
				dt.setDayOfWeek("Tuesday");
			}
			if (dayOfWeek == 4)
			{
				dt.setDayOfWeekShort("WED");
				dt.setDayOfWeek("Wednesday");
			}
			if (dayOfWeek == 5)
			{
				dt.setDayOfWeekShort("THU");
				dt.setDayOfWeek("Thursday");
			}
			if (dayOfWeek == 6)
			{
				dt.setDayOfWeekShort("FRI");
				dt.setDayOfWeek("Friday");
			}
			if (dayOfWeek == 7)
			{
				dt.setDayOfWeekShort("SAT");
				dt.setDayOfWeek("Saturday");
			}
			}
	//************************************************************************
	// Time	
			if (loadType.equals("formatTime"))
			{
				// Send in Vector with Hour(0) Minute(1) Second(2)
				hour    = (String) parms.elementAt(0);
				minute  = (String) parms.elementAt(1);
				second  = (String) parms.elementAt(2);
			}
			dt.setTimeFormathhmmss(hour + minute + second);
			dt.setTimeFormathhmmssColon(hour + ":" + minute + ":" + second);
			dt.setTimeFormathhmm(hour + minute);
			dt.setTimeFormathhmmColon(hour + ":" + minute);
			String ampm = "AM";
			try
			{
				Integer hr = new Integer(hour);
				if (hr.intValue() > 12)
				{
					hour = (hr.intValue() - 12) + "";
					if (hour.trim().length() == 0)
						hour = "00";
					if (hour.trim().length() == 1)
						hour = "0" + hour.trim();
					ampm = "PM";
				}
				if (hr.intValue() == 12)
					ampm = "PM";
			}
			catch(Exception e)
			{}
			dt.setTimeFormatAMPM(hour + ":" + minute + " " + ampm);
				
		} //END of the Load and Format Everything
			
	//--------------------------------------------------------------------------------------		
	
		} catch(Exception e)
		{
			throwError.append(" Problem loading the class DateTime Class: " + e);
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.utilities.UtilityDateTime.");
			throwError.append("loadFields(loadType:");
			throwError.append(loadType + ", Vector). ");
			throw new Exception(throwError.toString());
		}
		return dt;
	}

	/**
	 * Take in the Month Number and Return the Short Name for the Month
	 * Creation date: (3/7/2008 TWalton)
	 */
	private static String getMonthShortName(String monthNumber) {
		String longName = "JAN";
		if (monthNumber.equals("02"))
			longName = "FEB";
		if (monthNumber.equals("03"))
			longName = "MAR";
		if (monthNumber.equals("04"))
			longName = "APR";
		if (monthNumber.equals("05"))
			longName = "MAY";
		if (monthNumber.equals("06"))
			longName = "JUN";
		if (monthNumber.equals("07"))
			longName = "JUL";
		if (monthNumber.equals("08"))
			longName = "AUG";
		if (monthNumber.equals("09"))
			longName = "SEP";
		if (monthNumber.equals("10"))
			longName = "OCT";
		if (monthNumber.equals("11"))
			longName = "NOV";
		if (monthNumber.equals("12"))
			longName = "DEC";
		return longName;
	}
	/**
	 * Take in the Month Number and Return the Long Name for the Month
	 * Creation date: (3/7/2008 TWalton)
	 */
	private static String getMonthLongName(String monthNumber) {
		String longName = "January ";
		if (monthNumber.equals("02"))
			longName = "February ";
		if (monthNumber.equals("03"))
			longName = "March ";
		if (monthNumber.equals("04"))
			longName = "April ";
		if (monthNumber.equals("05"))
			longName = "May ";
		if (monthNumber.equals("06"))
			longName = "June ";
		if (monthNumber.equals("07"))
			longName = "July ";
		if (monthNumber.equals("08"))
			longName = "August ";
		if (monthNumber.equals("09"))
			longName = "September ";
		if (monthNumber.equals("10"))
			longName = "October ";
		if (monthNumber.equals("11"))
			longName = "November ";
		if (monthNumber.equals("12"))
			longName = "December ";
		
		return longName;
	}
/**
 * Find the M3 Fiscal Information 
 * 	  Send in the DateTime Class, it will add the 
 * 		Year and Period to that class
 * 
 *	Creation date: (3/8/2008 TWalton)
 *	Modified 7/31/2012 jhagle - added fiscal week
 */
 private static DateTime findM3FiscalYearPeriod(DateTime dt)
 {
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	PreparedStatement findIt = null;
	ResultSet rs = null;
	try
	{
		conn = ServiceConnection.getConnectionStack10();
		
		try {
			Vector sendValues = new Vector();
			sendValues.addElement(dt);
			findIt = conn.prepareStatement(buildSqlStatement("findYearPeriod", sendValues));
			rs = findIt.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println("UtilityDateTime.findM3FiscalYearPeriod : ");
			System.out.println(" Build and Run the SQL Statement: " + e);
		}	
		try
		{
			if (rs.next())
			{
				Vector sendValues = new Vector();
				sendValues.addElement(dt);
				sendValues.addElement(rs);
				dt = loadFields("findYearPeriod", sendValues);
			}
		}
		catch(Exception e)
		{
			System.out.println("UtilityDateTime.findM3FiscalYearPeriod : ");
			System.out.println(" Run through the SQL Statement: " + e);
		}			
	}
	catch(Exception e)
	{
		System.out.println("UtilityDateTime.findM3FiscalYearPeriod : ");
		System.out.println("Problem Occurred when looking up the Fiscal Year and Period : " + e);
	} finally {
	  try
	  {
		if (conn != null) {
			try {
				ServiceConnection.returnConnectionStack10(conn);
			} catch (Exception el) {
				el.printStackTrace();
			}
		  if (findIt != null)
			 findIt.close();
		  if (rs != null)
		  	 rs.close();
		}
	  }
	  catch(Exception e)
	  {}
	}
	return dt;
}
 
/**
 * Build an sql statement.
 * @param request type
 * @param Vector selection criteria
 * @return sql string
 * @throws Exception
 */
	
 private static String buildSqlStatement(String inRequestType,
										Vector requestValues)
 {
	StringBuffer sqlString = new StringBuffer();
		
	try { 
		// ------------------------------------------------------------------
		// Find Fiscal Year and Period Based on sent in Date
		if (inRequestType.equals("findYearPeriod"))	
		{
			DateTime dt = (DateTime) requestValues.elementAt(0);
			sqlString.append("SELECT A.CPYEA4, A.CPPERI, B.CPPERI AS CPWEEK ");
			sqlString.append("FROM " + library + ".CSYPER AS A ");
			sqlString.append("LEFT OUTER JOIN " + library + ".CSYPER AS B ON ");
			sqlString.append("A.CPCONO=B.CPCONO AND A.CPDIVI=B.CPDIVI ");
			sqlString.append("AND B.CPPETP=2 ");
			sqlString.append("AND " + dt.getDateFormatyyyyMMdd() + " >= B.CPFDAT ");
			sqlString.append("AND " + dt.getDateFormatyyyyMMdd() + " <= B.CPTDAT ");

			sqlString.append("WHERE A.CPCONO = 100 ");
			sqlString.append("  AND A.CPDIVI = '100' ");
			sqlString.append("  AND A.CPPETP = 1 ");
			sqlString.append("  AND " + dt.getDateFormatyyyyMMdd() + " >= A.CPFDAT ");
			sqlString.append("  AND " + dt.getDateFormatyyyyMMdd() + " <= A.CPTDAT ");
		
		}
		if (inRequestType.equals("findByYearPeriod"))	
		{
			DateTime dt = (DateTime) requestValues.elementAt(0);
			sqlString.append("SELECT * ");
			sqlString.append("FROM " + library + ".CSYPER ");
			sqlString.append("WHERE CPCONO = 100 ");
			sqlString.append("  AND CPDIVI = '100' ");
			sqlString.append("  AND CPPETP = 1 ");
			sqlString.append("  AND CPYEA4 = " + dt.getM3FiscalYear() + " ");
			sqlString.append("  AND CPPERI = " + dt.getM3FiscalPeriod() + " ");
		}
		
		if (inRequestType.equals("findDates"))	
		{
			String type = (String) requestValues.elementAt(0);
			String year = (String) requestValues.elementAt(1);
			String period = (String) requestValues.elementAt(2);
			
			sqlString.append("SELECT CPYEA4, CPPERI, CPFDAT, CPTDAT ");
			sqlString.append("FROM " + library + ".CSYPER ");
			sqlString.append("WHERE CPCONO = 100 ");
			sqlString.append("  AND CPDIVI = '100' ");
			if (type.trim().equals("month"))
			   sqlString.append("  AND CPPETP = 1 ");
			if (type.trim().equals("week"))
			   sqlString.append("  AND CPPETP = 2 ");
			sqlString.append("  AND CPYEA4 = " + year + " ");
			sqlString.append("  AND CPPERI = " + period + " ");
		}
//----------------------------------------		
// Drop Down Lists
		if (inRequestType.equals("listAllYears"))	
		{
			sqlString.append("SELECT CPCONO, CPYEA4 ");
			sqlString.append("FROM " + library + ".CSYPER ");
			sqlString.append("  WHERE CPCONO = 100 ");
			sqlString.append("  GROUP BY CPCONO, CPYEA4 ");
			sqlString.append("  ORDER BY CPCONO, CPYEA4 ");
		}	
		
		if (inRequestType.equals("listYearPeriodWeek"))	
		{
			Integer currentFiscalYear = (Integer) requestValues.elementAt(0);
			Integer numYears = (Integer) requestValues.elementAt(1);
			
			sqlString.append(" SELECT ");
			sqlString.append(" W.CPYEA4 AS YEAR, ");
			sqlString.append(" M.CPPERI AS PERIOD, ");
			sqlString.append(" M.CPTX15 AS PERIOD_TX, ");
			sqlString.append(" W.CPPERI AS WEEK, ");
			sqlString.append(" W.CPFDAT AS FROMDATE, ");
			sqlString.append(" W.CPTDAT AS TODATE ");

			sqlString.append(" FROM " + library + ".CSYPER AS W");
			sqlString.append(" LEFT OUTER JOIN  " + library + ".CSYPER AS M ON");
			sqlString.append("     W.CPCONO=M.CPCONO ");
			sqlString.append(" AND W.CPDIVI=M.CPDIVI ");
			sqlString.append(" AND M.CPPETP=1 ");
			sqlString.append(" AND W.CPFDAT>=M.CPFDAT ");
			sqlString.append(" AND W.CPTDAT<=M.CPTDAT ");

			sqlString.append(" WHERE W.CPCONO=100 ");
			sqlString.append(" AND W.CPDIVI='100' ");
			sqlString.append(" AND W.CPPETP=2 ");
			sqlString.append(" AND W.CPYEA4 >= " + currentFiscalYear + " ");
			sqlString.append(" AND W.CPYEA4 <= " + currentFiscalYear + " + " + numYears + " ");
			sqlString.append(" AND W.CPPERI <> 0 ");
			
			sqlString.append(" ORDER BY W.CPYEA4, M.CPPERI, W.CPPERI ");
			
		}
		
		
	}
	catch(Exception e)
	{
		System.out.println("UtilityDateTime.buildSQLStatement : ");
		System.out.println(" Build and Run the SQL Statement: " + e);
	}	
	
	return sqlString.toString();
 }

/**
 * Send in Any Date this method will check to see if it is a Valid Date
 * 	   Send in date in the yyyyMMdd format
 * Creation date: (3/13/2008 TWalton)
 * @return DateTime
 */
public static DateTime getDateFromMMddyyyyWithSlash(String inDate) 
{
	DateTime dt = new DateTime();
	if (inDate.length() >= 8)
    {
		String month = inDate.substring(0,inDate.indexOf("/"));
		if (month.length() == 1)
			month = "0" + month;
		String workWithDate = inDate.substring((inDate.indexOf("/") + 1));
		String day = workWithDate.substring(0, inDate.indexOf("/"));
		String century = workWithDate.substring((inDate.indexOf("/") + 1), (inDate.indexOf("/") + 3));
		String year = workWithDate.substring((workWithDate.length() - 2), workWithDate.length());
		
		dt.setDateErrorMessage(validateDate(month, day, century, year));
    	if (dt.getDateErrorMessage().trim().equals(""))
    	{	
    		Vector parms = new Vector();
    		// 	Send to the Load Fields
    		parms.addElement(month); // Month
    		parms.addElement(day); // Day
    		parms.addElement(century); // Century
    		parms.addElement(year); // Year
    		try
			{
    			dt = loadFields("getDate", parms);
			}
    		catch(Exception e)
			{
    			dt.setDateErrorMessage("The date is invalid - not a valid calendar date.");
			}
    	}
    }
    else
    	dt.setDateErrorMessage("The date is invalid - the length is incorrect.");
	
	return dt;
}

/**
 * Send in Any Date this method will check to see if it is a Valid Date
 * 	   Send in date in the MMddyy format With Slashes Between
 * Creation date: (3/13/2008 TWalton)
 * @return DateTime
 */
public static DateTime getDateFromMMddyyWithSlash(String inDate) 
{
	DateTime dt = new DateTime();
	if (inDate.length() >= 8)
    {
		String month = inDate.substring(0,inDate.indexOf("/"));
		if (month.length() == 1)
			month = "0" + month;
		String workWithDate = inDate.substring((inDate.indexOf("/") + 1));
		String day = workWithDate.substring(0, inDate.indexOf("/"));
		String year = workWithDate.substring((workWithDate.length() - 2), workWithDate.length());
		String century = "20";
		if (new Integer(year).intValue() >= 70)
			century = "19";
		
		dt.setDateErrorMessage(validateDate(month, day, century, year));
    	if (dt.getDateErrorMessage().trim().equals(""))
    	{	
    		Vector parms = new Vector();
    		// 	Send to the Load Fields
    		parms.addElement(month); // Month
    		parms.addElement(day); // Day
    		parms.addElement(century); // Century
    		parms.addElement(year); // Year
    		try
			{
    			dt = loadFields("getDate", parms);
			}
    		catch(Exception e)
			{
    			dt.setDateErrorMessage("The date is invalid - not a valid calendar date.");
			}
    	}
    }
    else
    	dt.setDateErrorMessage("The date is invalid - the length is incorrect.");
	
	return dt;
}
/**
 * Build a DateTime Class which Includes:
 * 		Send in a DateTime Class, 
 *    add a number of days to the date 
 *    OR subtract if a negative number
 *  Send Back a new DateTime Class
 * Creation date: (3/21/2008 TWalton)
 */
public static DateTime addDaysToDate(DateTime inDT,
									 int addDays) 
{
	DateTime dt = new DateTime();	
	try
	{
	// Create Date Format -- Calendar only works with one format
		String date_format = "yyyy-MM-dd";
	// Build a Java / Simple Date Format
		SimpleDateFormat sdf = new SimpleDateFormat(date_format);
		// Create int's of the month, day and year.
		int year  = 0;
		int month = 0;
		int day   = 0;
		try
		{
			year  = (new Integer(inDT.getCentury() + inDT.getYear())).intValue();
			month = ((new Integer(inDT.getMonth())).intValue() - 1);
			day   = (new Integer(inDT.getDay())).intValue();
		}
		catch(Exception e)
		{
			dt.setDateErrorMessage("Problem when formatting the year, month and day.");	
		}
		if (dt.getDateErrorMessage().trim().equals(""))
		{
			// Create an instance of the Calendar
			Calendar cl = Calendar.getInstance();
			// set the incoming date into the Calendar Instance
			cl.set(year, month, day);
	//		System.out.println("Date is: " + sdf.format(cl.getTime()));
			// Add the Days to the incoming Date
			cl.add(Calendar.DATE, addDays);
	//		System.out.println("Date + 7 Days is: " + sdf.format(cl.getTime()));
			// Date is in the yyyy-MM-dd format
			String newDate = sdf.format(cl.getTime());
			dt = getDateFromyyyyMMddWithDash(newDate);
			DateTime addYearPer = findM3FiscalYearPeriod(dt);
			dt.setM3FiscalYear(addYearPer.getM3FiscalYear());
			dt.setM3FiscalPeriod(addYearPer.getM3FiscalPeriod());
		}
	}
	catch(Exception e)
	{
		dt.setDateErrorMessage("Problem when adding days to the date.&nbsp;&nbsp;" + 
							   "Date sent in: " + inDT.getDateFormatMMddyySlash() + 
							   " tried Adding " + addDays + " days to the date.");
	}
	return dt;
}

/**
 * Send in Any Time this method will format the other applicable
 *     Time Formats
 * Creation date: (3/22/2008 TWalton)
 * @return DateTime
 */
public static DateTime getTimeFromhhmmss(String inTime) 
{
	DateTime dt = new DateTime();
	if (inTime.length() < 6)
	{
		while (inTime.length() != 6)
		{
			inTime = "0" + inTime;
		}
	}
	if (inTime.length() == 6)
    {
		Vector parms = new Vector();
		// 	Send to the Load Fields
		parms.addElement(inTime.substring(0, 2)); // Hour
		parms.addElement(inTime.substring(2, 4)); // Minute
		parms.addElement(inTime.substring(4, 6)); // Second
		try
		{
    		dt = loadFields("formatTime", parms);
		}
    	catch(Exception e)
		{
    		dt.setDateErrorMessage("The Time is invalid.");
		}
    }
    else
    	dt.setDateErrorMessage("The time is invalid - the length is incorrect.");
	
	return dt;
}

/**
 * Send in Any Date this method will check to see if it is a Valid Date
 * 	   Send in date in the yyyy-MM-dd format
 * Creation date: (3/22/2008 TWalton)
 * @return DateTime
 */
public static DateTime getDateFromyyyyMMddWithDash(String inDate) 
{
	DateTime dt = new DateTime();
	if (inDate.length() == 10)
    {
		String century = inDate.substring(0, 2);
		String year = inDate.substring(2, 4);
		String month = inDate.substring(5, 7);
		String day   = inDate.substring(8, 10);
		
		dt.setDateErrorMessage(validateDate(month, day, century, year));
    	if (dt.getDateErrorMessage().trim().equals(""))
    	{	
    		Vector parms = new Vector();
    		// 	Send to the Load Fields
    		parms.addElement(month); // Month
    		parms.addElement(day); // Day
    		parms.addElement(century); // Century
    		parms.addElement(year); // Year
    		try
			{
    			dt = loadFields("getDate", parms);
			}
    		catch(Exception e)
			{
    			dt.setDateErrorMessage("The date is invalid - not a valid calendar date.");
			}
    	}
    }
    else
    	dt.setDateErrorMessage("The date is invalid - the length is incorrect.");
	
	return dt;
}

/**
 * Send in Year, and Monthly Period
 *    Return Vector with TWO Elements, beginning Date and Ending Date	   
 *     Only send Connection if you have it  
 * 
 * Creation date: (7/7/2008 TWalton)
 * @return DateTime
 */
public static Vector<DateTime> getDatesFromYearMonthlyPeriod(String year, String period, Connection conn) 
{
	return findM3DatesFromYearPeriod("month", year, period, conn);
}
/**
 * Send in Year, and Weekly Period
 *    Return Vector with TWO Elements, beginning Date and Ending Date	 
 *    Only send Connection if you have it  
 * 
 * Creation date: (7/7/2008 TWalton)
 * @return DateTime
 */
public static Vector<DateTime> getDatesFromYearWeeklyPeriod(String year, String period, Connection conn) 
{
	return findM3DatesFromYearPeriod("week", year, period, conn);
}
/**
 * Find the Beginning and Ending Dates for the Period
 *    Use requestType to Determine if Monthly or Weekly
 * 
 *Creation date: (7/7/2008 TWalton)
 */
 private static Vector findM3DatesFromYearPeriod(String requestType, 
 	                                             String year,
												 String period,
												 Connection inCONN)
 {
 	Connection conn = null;
	PreparedStatement findIt = null;
	ResultSet rs = null;
	Vector returnValues = new Vector();
	try
	{
		if (inCONN == null)
		   conn = ConnectionStack.getConnection();
		try {
			Vector sendValues = new Vector();
			sendValues.addElement(requestType);
			sendValues.addElement(year);
			sendValues.addElement(period);
			if (inCONN == null)
			   findIt = conn.prepareStatement(buildSqlStatement("findDates", sendValues));
			else
			   findIt = inCONN.prepareStatement(buildSqlStatement("findDates", sendValues));
			rs = findIt.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println("UtilityDateTime.findM3DatesFromYearPeriod : ");
			System.out.println(" Build and Run the SQL Statement: " + e);
		}	
		try
		{
			if (rs.next())
			{
				returnValues.addElement(getDateFromyyyyMMdd(rs.getString("CPFDAT")));
				returnValues.addElement(getDateFromyyyyMMdd(rs.getString("CPTDAT")));
			}
		}
		catch(Exception e)
		{
			System.out.println("UtilityDateTime.FindM3DatesFromYearPeriod : ");
			System.out.println(" Run through the SQL Statement: " + e);
		}			
	}
	catch(Exception e)
	{
		System.out.println("UtilityDateTime.findM3DatesFromYearPeriod : ");
		System.out.println("Problem Occurred when looking up the Fiscal Year and Period : " + e);
	} finally {
	  try
	  {
		if (conn != null) {
			try {
			   ConnectionStack.returnConnection(conn);
			} catch (Exception el) {
				el.printStackTrace();
			}
		  if (findIt != null)
			 findIt.close();
		  if (rs != null)
		  	 rs.close();
		}
	  }
	  catch(Exception e)
	  {}
	}
	return returnValues;
}

/**
 *   Method Created 11/6/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				listAllYears - Means Return a list of ALL years
 */
public static Vector dropDownYear(Vector inValues)
{
	Vector ddyear = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack.getConnection();
		ddyear = dropDownYear(inValues, conn);
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ConnectionStack.returnConnection(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// return value
	return ddyear;
}

/**
 * Method Created 11/6/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 */
private static Vector dropDownYear(Vector inValues, 
								   Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Item Type Values - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement((String) inValues.elementAt(0), inValues));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		 	System.out.println("error" + e);
		   	//throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }		
		 try
		 {
		 	while (rs.next())
		    {
		 		DropDownSingle dds = loadFieldsDropDownSingle((String) inValues.elementAt(0), rs);
		 		ddit.addElement(dds);
     		}// END of the IF Statement
		 } catch(Exception e)
		 {
			//throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
	   if (rs != null)
	   {
		   try
		   {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try
		   {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
//	if (!throwError.toString().trim().equals(""))
//	{
//		throwError.append(" @ com.treetop.Services.");
//		throwError.append("UtilityDateTime.");
//		throwError.append("dropDownYear(");
//		throwError.append("Vector, conn). ");
//		throw new Exception(throwError.toString());
//	}
	// return value
	return ddit;
}

/**
 * Load class fields from result set.
 */

public static DropDownSingle loadFieldsDropDownSingle(String requestType,
						          		  		     ResultSet rs)
		throws Exception
{
	StringBuffer throwError = new StringBuffer();
	DropDownSingle returnValue = new DropDownSingle();
	try{ 
	  if (requestType.equals("listAllYears"))
	  {
		returnValue.setValue(rs.getString("CPYEA4").trim());
		returnValue.setDescription(rs.getString("CPYEA4").trim());
	  }
	} catch(Exception e)
	{
		throwError.append(" Problem loading the Item Warehouse class ");
		throwError.append("from the result set. " + e) ;
	}
	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("UtilityDateTime.");
		throwError.append("loadFieldsDropDownSingle(requestType, rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}
	return returnValue;
} 

	public static Vector<DropDownDual> buildMonthWeekDualDropDown() {
		Vector<DropDownDual> result = new Vector<DropDownDual>();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat m = new SimpleDateFormat("MMMM");
		int weekInYear = 0;
		int weeksPerMonth = 0;
		for (int month=1; month<=12; month++) {
			
			//convert fiscal period to calendar month
			if (month >= 8) {
				c.set(Calendar.MONTH, (month - 7) + 1 );
			} else {
				c.set(Calendar.MONTH, (month + 5) + 1 );
			}
			String monthName = m.format(c.getTime());
			

			if (month % 3 == 0) {
				weeksPerMonth = 5;
			} else {
				weeksPerMonth = 4;
			}
			for (int weekInMonth=1; weekInMonth<=weeksPerMonth; weekInMonth++) {
				weekInYear++;
				DropDownDual ddd = new DropDownDual();
				ddd.setMasterDescription(monthName);
				ddd.setMasterValue(String.valueOf(month));
				ddd.setSlaveDescription("Week " + weekInYear);
				ddd.setSlaveValue(String.valueOf(weekInYear));
				result.addElement(ddd);
			}
		}
		return result;
	}
	
	
	
	/**
	 * @author deisen.
	 * Update the production lot with the code date.
	 */
		
	public static Vector<String> findLotCodeDate(CommonRequestBean crb)							
	throws Exception 
	{
		AS400	       as400       = null;
		StringBuffer   throwError  = new StringBuffer();
		Vector<String> returnValue = new Vector<String>();
			
		try {
			String crbItem = crb.getIdLevel1();
			String crbWarehouse = crb.getIdLevel2();
			String crbFacility = crb.getIdLevel3();
			String crbLine = crb.getIdLevel4();
			String crbShift = crb.getIdLevel5();
			
			DateTime dateTime = crb.getDateTime();
			
			String julian = dateTime.getDateFormatyyddd();
			
			
			returnValue.add(0, " ");	// Message
			returnValue.add(1, " ");	// Lot reference1 (code date)
			returnValue.add(2, " ");	// Lot reference2
			
			as400 = ServiceConnection.getAS400();	
			ProgramCall pgm = new ProgramCall(as400);
			ProgramParameter[] parmList = new ProgramParameter[13];
			AS400Text environment = new AS400Text(3, as400);
			AS400Text company     = new AS400Text(3, as400);
			AS400Text itemNumber  = new AS400Text(15, as400);
			AS400Text dateStd     = new AS400Text(8, as400);
			AS400Text dateJulian  = new AS400Text(5, as400);
			AS400Text facility    = new AS400Text(3, as400);
			AS400Text warehouse   = new AS400Text(3, as400);
			AS400Text packLine    = new AS400Text(2, as400);
			AS400Text laborShift  = new AS400Text(2, as400);
			AS400Text timeHours   = new AS400Text(2, as400);
			AS400Text timeMinutes = new AS400Text(2, as400);
			
			byte[] parmEnvironment = environment.toBytes(crb.getEnvironment().trim());
			byte[] parmCompany     = company.toBytes(crb.getCompanyNumber().trim());
			byte[] parmItemNumber  = itemNumber.toBytes(crbItem.trim());
			byte[] parmDateStd     = dateStd.toBytes(dateTime.getDateFormatyyyyMMdd().trim());
			byte[] parmDateJulian  = dateJulian.toBytes(dateTime.getDateFormatyyddd().trim());
			byte[] parmFacility    = facility.toBytes(crbFacility.trim());
			byte[] parmWarehouse   = warehouse.toBytes(crbWarehouse.trim());
			byte[] parmPackLine    = packLine.toBytes(crbLine.trim().substring(1,3));
			byte[] parmLaborShift  = laborShift.toBytes(crbShift.trim().substring(1,3));
			byte[] parmTimeHours   = timeHours.toBytes(dateTime.getTimeFormathhmm().trim().substring(0,2));
			byte[] parmTimeMinutes = timeMinutes.toBytes(dateTime.getTimeFormathhmm().trim().substring(2,4));
			
			parmList[0] = new ProgramParameter(parmEnvironment);
			parmList[1] = new ProgramParameter(parmCompany);
			parmList[2] = new ProgramParameter(parmItemNumber);
			parmList[3] = new ProgramParameter(parmDateStd);
			parmList[4] = new ProgramParameter(parmDateJulian);		
			parmList[5] = new ProgramParameter(parmFacility);
			parmList[6] = new ProgramParameter(parmWarehouse);
			parmList[7] = new ProgramParameter(parmPackLine);	
			parmList[8] = new ProgramParameter(parmLaborShift);
			parmList[9] = new ProgramParameter(parmTimeHours);
			parmList[10] = new ProgramParameter(parmTimeMinutes);
			parmList[11] = new ProgramParameter(12);
			parmList[12] = new ProgramParameter(12);
			
			pgm = new ProgramCall(as400, "/QSYS.LIB/MOVEX.LIB/HHCTCODE.PGM", parmList);
		
			if (pgm.run() != true)
				return returnValue;	
			
			else {
				
				AS400Text returnReference1 = new AS400Text(12, as400);
				AS400Text returnReference2 = new AS400Text(12, as400);		
				byte[] outReference1  = parmList[11].getOutputData();
				byte[] outReference2  = parmList[12].getOutputData();
				String infoReference1 = (String) returnReference1.toObject(outReference1, 0);
				String infoReference2 = (String) returnReference2.toObject(outReference2, 0);	
				returnValue.set(1, infoReference1.trim());
				returnValue.set(2, infoReference2.trim());
				
				as400.disconnectService(AS400.COMMAND);
				return returnValue;			
			} 
			
		} catch (Exception e) 
		{
			throwError.append("Lot code date processing failed. " + e);
		}
		
		finally {
			
			try {
				
				if (as400 !=null)
					ServiceConnection.returnAS400(as400);
				
			} catch(Exception e)
			{			
				throwError.append("AS400 object failed return when requesting CodeDate");			
			}
		}
			
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceMobility.");
			throwError.append("updateLotCodeDate(UpdProduction) ");			
				
			throw new Exception(throwError.toString());
		}
			
		return returnValue;
	}
	
	public static Vector<DropDownTriple> dropDownYearPeriodWeek(Integer year, Integer numYears) {
		Vector<DropDownTriple> values = new Vector<DropDownTriple>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat mdyySlash = new SimpleDateFormat("M/d/yy");
		
		try {
			
			conn = ServiceConnection.getConnectionStack1();
			stmt = conn.createStatement();
			
			Vector requestValues = new Vector();
			requestValues.addElement(year);
			requestValues.addElement(numYears - 1);  //numYears includes initially selected year
			String sql = buildSqlStatement("listYearPeriodWeek", requestValues);
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				DropDownTriple ddt = new DropDownTriple();
				ddt.setList1Value(rs.getString("YEAR").trim());
				ddt.setList1Description(rs.getString("YEAR").trim());
				
				ddt.setList2Value(rs.getString("PERIOD").trim());
				
				String monthName = rs.getString("PERIOD_TX").trim();
				if (monthName.length() >= 2) {
					monthName = monthName.substring(0,1).toUpperCase() + monthName.substring(1,monthName.length()).toLowerCase();
				}
				ddt.setList2Description(rs.getString("PERIOD").trim() + "-" + monthName);
				
				
				ddt.setList3Value(rs.getString("PERIOD").trim() + "-" + rs.getString("WEEK").trim());
				
				Calendar fromDate = Calendar.getInstance();
				fromDate.setTime(yyyyMMdd.parse(rs.getString("FROMDATE").trim()));
				
				Calendar toDate = Calendar.getInstance();
				toDate.setTime(yyyyMMdd.parse(rs.getString("TODATE").trim()));
				
				String from = mdyySlash.format(fromDate.getTime());
				String to = mdyySlash.format(toDate.getTime());
				
				ddt.setList3Description("Wk " + rs.getString("WEEK").trim() + " - " + from);
				
				values.addElement(ddt);
			}
			
		} catch (Exception e) {
			System.err.println("Exception @ UtilityDateTime.dropDownYearPeriodWeek(e).  " + e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (Exception e) {}
			}
			if (stmt != null) {
				try { stmt.close(); } catch (Exception e) {}
			}
			if (conn != null) {
				try { ServiceConnection.returnConnectionStack1(conn); } catch (Exception e) {}
			}
		}
		
		return values;
	}

	/**
	 * Send in the Common Request Bean
	 *    with Date and Time in the correct spots from the Common Request Bean
	 * Date in the yyyyMMdd format
	 * Time in the hhmmss format
	 * 
	 * Creation date: (10/23/2013 TWalton)
	 * @return DateTime filled object
	 */
	public static DateTime getDateFromyyyyMMddTimeFromhhmmss(CommonRequestBean crb) 
	{
		DateTime dt = new DateTime();
		//*** DATE SECTION ***//
		try{
			if (crb.getDate().length() == 8)
			{
				String century = crb.getDate().substring(0,2); 
				String year = crb.getDate().substring(2,4);
				String month = crb.getDate().substring(4,6);
				String day = crb.getDate().substring(6,8);
				dt.setDateErrorMessage(validateDate(month, day, century, year));
				if (dt.getDateErrorMessage().trim().equals(""))
				{	
					Vector parms = new Vector();
					// 	Send to the Load Fields
					parms.addElement(month); // Month
					parms.addElement(day); // Day
					parms.addElement(century); // Century
					parms.addElement(year); // Year
					try
					{
						dt = loadFields("getDate", parms);
					}catch(Exception e)	{
						dt.setDateErrorMessage("The date is invalid - not a valid calendar date.");
					}
				}
			}
			else
				dt.setDateErrorMessage("The date is invalid - the length is incorrect.");
		}catch(Exception e)
		{
			dt.setDateErrorMessage("Error Found when trying to create Date object");
		}
		
		//*** TIME SECTION ***//
		DateTime dtTime = new DateTime();
		try{
			if (crb.getTime().length() < 6)
			{
				while (crb.getTime().length() != 6)
				{
					crb.setTime("0" + crb.getTime().trim());
				}
			}
			if (crb.getTime().length() == 6)
			{
				Vector parms = new Vector();
				// 	Send to the Load Fields
				parms.addElement(crb.getTime().substring(0, 2)); // Hour
				parms.addElement(crb.getTime().substring(2, 4)); // Minute
				parms.addElement(crb.getTime().substring(4, 6)); // Second
				try
				{
					dtTime = loadFields("formatTime", parms);
				}catch(Exception e)	{
					dtTime.setDateErrorMessage("The Time is invalid.");
				}
			}
			else
				dtTime.setDateErrorMessage("The time is invalid - the length is incorrect.");
		}catch(Exception e)
		{
			dtTime.setDateErrorMessage("Error Found when trying to add the Time object");
		}
		dt.setDateErrorMessage(dt.getDateErrorMessage().trim() + dtTime.getDateErrorMessage().trim());
		dt.setTimeFormatAMPM(dtTime.getTimeFormatAMPM());
		dt.setTimeFormathhmm(dtTime.getTimeFormathhmm());
		dt.setTimeFormathhmmColon(dtTime.getTimeFormathhmmColon());
		dt.setTimeFormathhmmss(dtTime.getTimeFormathhmmss());
		dt.setTimeFormathhmmssColon(dtTime.getTimeFormathhmmssColon());
		
		return dt;
	}
	
} // End of the Entire Utility
