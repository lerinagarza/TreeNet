package com.treetop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.treetop.businessobjectapplications.BeanInventory;

/**
 * Insert the type's description here.
 * Creation date: (12/23/2002 4:39:29 PM)
 * @author: 
 */
public class SessionVariables {
/**
 * SessionVariables constructor comment.
 */
public SessionVariables() {
	super();
}
/**
 * Remove / destroy the Grower Sequence Number for the 
 * current session.
 * Creation date: (08/27/2004 4:49:44 PM)
 */
public static void destroyGrowerSeq(HttpServletRequest request,
									HttpServletResponse response) 
{
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);

	try
	{		
		sess.removeAttribute("growerSeq");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}		
	return; 
}
/**
 * Remove / destroy the Is Field Rep Value for the 
 * current session.
 * Creation date: (08/27/2004 4:49:44 PM)
 */
public static void destroyIsFieldRep(HttpServletRequest request,
									 HttpServletResponse response) 
{
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);

	try
	{		
		sess.removeAttribute("isFieldRep");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}		
	return; 
}
/**
 * Remove / destroy the Payroll Number Value for the 
 * current session.
 * Creation date: (08/27/2004 4:49:44 PM)
 */
public static void destroyPayrollNumber(HttpServletRequest request,
										HttpServletResponse response) 
{
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);

	try
	{		
		sess.removeAttribute("payrollNumber");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}		
	return; 
}
/**
 * Remove / destroy the Session Value for the 
 * current session.
 * Creation date: (08/27/2004 4:49:44 PM)
 */
public static void destroySessionValue(HttpServletRequest request,
									   HttpServletResponse response,
									   String sessionValue) 
{
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);

	try
	{		
		sess.removeAttribute(sessionValue);	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}		
	return; 
}
/**
 * Remove / destroy the Security Status Value for the 
 * current session.
 * Creation date: (08/27/2004 4:49:44 PM)
 */
public static void destroySessionttiSecStatus(HttpServletRequest request,
											  HttpServletResponse response) 
{
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);

	try
	{		
		sess.removeAttribute("ttiSecStatus");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}		
	return; 
}
/**
 * Remove / destroy the Vector of Lots 
 * current session.
 * Creation date: (09/10/2008 8:55:44 PM)
 */
public static void destroySessionttiBeanInventory(HttpServletRequest request,
											 HttpServletResponse response) 
{
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);

	try
	{		
		sess.removeAttribute("ttiBeanInventory");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}		
	return; 
}
/**
 * Remove / destroy the Url Value for the 
 * current session.
 * Creation date: (08/27/2004 4:49:44 PM)
 */
public static void destroySessionttiTheUrl(HttpServletRequest request,
										   HttpServletResponse response) 
{
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);

	try
	{		
		sess.removeAttribute("ttiTheURL");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}		
	return; 
}
/**
 * Return the Destination Url for the 
 * current session.
 * Creation date: (08/13/2004 4:49:44 PM)
 */
public static String getDestinationUrl(HttpServletRequest request,
									   HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String destinationUrl = null;

	try
	{		
		destinationUrl = (String) sess.getAttribute("destinationUrl");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	return destinationUrl;	
}
/**
 * Return the Grower Sequence Number for the 
 * current session.
 * Creation date: (08/13/2004 4:49:44 PM)
 */
public static String getGrowerSeq(HttpServletRequest request,
								  HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String growerSeq = null;

	try
	{		
		growerSeq = (String) sess.getAttribute("growerSeq");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	return growerSeq;	
}
/**
 * Return the Is Field Rep for the 
 * current session.
 * Creation date: (08/13/2004 4:49:44 PM)
 */
public static String getIsFieldRep(HttpServletRequest request,
								   HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String isFieldRep = null;

	try
	{		
		isFieldRep = (String) sess.getAttribute("isFieldRep");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	return isFieldRep;	
}
/**
 * Return the Payroll Number for the 
 * current session.
 * Creation date: (08/13/2004 4:49:44 PM)
 */
public static String getPayrollNumber(HttpServletRequest request,
									  HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String payrollNumber = null;

	try
	{		
		payrollNumber = (String) sess.getAttribute("payrollNumber");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	return payrollNumber;	
}
/**
 * Return the Payroll Number for the 
 * current session.
 * Creation date: (10/01/2004 4:49:44 PM)
 */
public static String getPayrollNumber(HttpServletRequest request,
									  HttpServletResponse response,
									  String checkTheTimer) 
{
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);

	// get the time this session value was set.
	java.util.Date timeSet = null;
	java.util.Date currTime = new java.util.Date();
	String payrollNumber = null;
	
	try {
		//String timeString = (String) sess.getAttribute("payrollNumberTimer");
		timeSet = (java.util.Date) sess.getAttribute("payrollNumberTimer");
		if (timeSet == null)
		{
			timeSet = new java.util.Date();
			long minuteInMillis = 1000 * 60;
			timeSet.setTime(timeSet.getTime() + (minuteInMillis * -7));
		}
		//timeSet = new java.util.Date(timeString);
	} catch(Exception e)
	{
		timeSet = new java.util.Date();
		long minuteInMillis = 1000 * 60;
		timeSet.setTime(timeSet.getTime() + (minuteInMillis * -7));
	}

	// compare current time to the set as a session variable.
	// Return a payrollNumber value only if current time is
	// less than the session variable value.	
	int x1 = timeSet.compareTo(currTime);
	
	if (x1 > 0)
	{
		try
		{		
			payrollNumber = (String) sess.getAttribute("payrollNumber");	
		}
		catch(Throwable theException)
		{
			// uncomment the following line when unexpected exceptions
			// are occuring to aid in debugging the problem.
			//theException.printStackTrace();
		}
	}	
	
	return payrollNumber;	
}
/**
 * Return the Payroll Number time set for the 
 * current session.
 * Creation date: (10/01/2004 4:49:44 PM)
 */
public static String getPayrollNumberTimer(HttpServletRequest request,
									  	   HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String dateValue = null;
	
	try
	{		
		dateValue = (String) sess.getAttribute("payrollNumberTimer");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	return dateValue;
}	
/**
 * Return the broker value for the current application.
 * Creation date: (12/26/2002 4:49:44 PM)
 */
public static String getSessionBroker(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String broker = null;

	try
	{
		//*******************************************************
		// Determine application to retrieve required variables.
		//*******************************************************
		String applicationType = getSessionttiURLAppType(request,response);

		//*****************
		// No application.
		//*****************
		if (applicationType == null)
			broker = ""; 

		//*****************
		// Trade Spending.
		//*****************
		if (applicationType.equals("Trade Spending"))
			broker = (String) sess.getAttribute("ttiTSBroker");	

	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}
	
	
	
return broker;	
}
/**
 * Return the market value for the current application.
 * Creation date: (12/23/2002 4:49:44 PM)
 */
public static String getSessionMarket(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String market = null;

	try
	{
		//*******************************************************
		// Determine application to retrieve required variables.
		//*******************************************************
		String applicationType = getSessionttiURLAppType(request,response);

		//*****************
		// No application.
		//*****************
		if (applicationType == null)
			market = ""; 

		//*****************
		// Trade Spending.
		//*****************
		if (applicationType.equals("Trade Spending"))
			market = (String) sess.getAttribute("ttiTSMarket");	

	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}
	
	
	
return market;	
}
/**
 * Return the system security system for the current application.
 * Creation date: (09/21/2004 4:49:44 PM)
 */
public static String getSessionSecuritySystem(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String system = null;

	try
	{		
		system = (String) sess.getAttribute("securitySystem");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	

	return system;
}
/**
 * Return the system security value for the current application.
 * Creation date: (09/21/2004 4:49:44 PM)
 */
public static String getSessionSecurityValue(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String value = null;

	try
	{		
		value = (String) sess.getAttribute("securityValue");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	

	return value;
}
/**
 * Return the system security value for the current application.
 * Creation date: (09/21/2004 4:49:44 PM)
 */
public static String getSessionSecurityUser(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String user = null;

	try
	{		
		user = (String) sess.getAttribute("securityUser");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	

	return user;
}
/**
 * Return the trade value for the current application.
 * Creation date: (12/26/2002 4:49:44 PM)
 */
public static String getSessionTrade(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String trade = null;

	try
	{
		//*******************************************************
		// Determine application to retrieve required variables.
		//*******************************************************
		String applicationType = getSessionttiURLAppType(request,response);

		//*****************
		// No application.
		//*****************
		if (applicationType == null)
			trade = ""; 

		//*****************
		// Trade Spending.
		//*****************
		if (applicationType.equals("Trade Spending"))
			trade = (String) sess.getAttribute("ttiTSTrade");



	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}
	
	
	
return trade;	
}
/**
 * Return the session value of the value for the incomming
 * parameter.
 * Creation date: (08/26/2004 4:49:44 PM)
 */
public static String getSessionValue(HttpServletRequest request,
									 HttpServletResponse response,
									 String value) 
{
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);

	String theValue = null;
	
	try
	{		
		// Get the session value for the session variable 
		// passed in.		
		theValue = (String) sess.getAttribute(value);
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
return theValue;	
}
/**
 * Return the User ID for the current session.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static String getSessionttiGroupProfile(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String ttiGroupProfile = null;

	try
	{		
		ttiGroupProfile = (String) sess.getAttribute("ttiGroupProfile");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return ttiGroupProfile;	
}
/**
 * Return the User ID for the current session.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static String getSessionttiLastURL(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String ttiLastURL = null;

	try
	{		
		ttiLastURL = (String) sess.getAttribute("ttiLastURL");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return ttiLastURL;	
}
/**
 * Return the current value for session value ttiProfile.
 * Creation date: (12/26/2002 4:49:44 PM)
 */
public static String getSessionttiProfile(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String ttiProfile = null;

	try
	{		
		ttiProfile = (String) sess.getAttribute("ttiProfile");	
		if (ttiProfile != null)
		ttiProfile = ttiProfile.toUpperCase();
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return ttiProfile;	
}
/**
 * Return the User ID for the current session.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static String getSessionttiSecStatus(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String ttiSecStatus = null;

	try
	{		
		ttiSecStatus = (String) sess.getAttribute("ttiSecStatus");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return ttiSecStatus;	
}
/**
 * Return the User ID for the current session.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static String getSessionttiTheURL(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String theurl = null;

	try
	{		
		theurl = (String) sess.getAttribute("ttiTheURL");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return theurl;	
}
/**
 * Return the User ID for the current session.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static String getSessionttiTSStatus(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String ttiTSStatus = null;

	try
	{		
		ttiTSStatus = (String) sess.getAttribute("ttiTSStatus");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return ttiTSStatus;	
}
/**
 * Return the market value for the current application.
 * Creation date: (12/26/2002 4:49:44 PM)
 */
public static String getSessionttiURLAppType(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);
	String ttiURLAppType = null;		

	try
	{
		ttiURLAppType = (String) sess.getAttribute("ttiURLAppType");
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}
		
	
return ttiURLAppType;	
}
/**
 * Return the User ID for the current session.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static String getSessionttiUserID(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String ttiUserID = null;

	try
	{		
		ttiUserID = (String) sess.getAttribute("ttiUserID");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return ttiUserID;	
}
/**
 * Insert the method's description here.
 * Creation date: (12/27/2002 4:48:22 PM)
 */
public void newMethod() {}
/**
 * Set the incoming value to session value ttiBeanInventory.
 * Creation date: (9/2/2008 11:15:44 AM)
 */
public static void setSessionttiBeanInventory(HttpServletRequest request,
								 		 HttpServletResponse response,
										 BeanInventory inValue) 
{
	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);
	try
	{
		sess.setAttribute("ttiBeanInventory", inValue);	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
}
/**
 * Return the Vector saved to the current session.
 * Creation date: (9/2/2008 11:25:44 AM)
 */
public static BeanInventory getSessionttiBeanInventory(HttpServletRequest request,
										   			   HttpServletResponse response) 
{
	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	BeanInventory ttiBeanInventory = null;
	try
	{		ttiBeanInventory = (BeanInventory) sess.getAttribute("ttiBeanInventory");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	return ttiBeanInventory;	
}
/**
 * Return the current value for session value ttiProfile.
 * Creation date: (05/09/2013)
 */
public static String getSessionttiProfile(HttpServletRequest request) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String ttiProfile = null;

	try
	{		
		ttiProfile = (String) sess.getAttribute("ttiProfile");	
		if (ttiProfile != null)
		ttiProfile = ttiProfile.toUpperCase();
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return ttiProfile;	
}
/**
 * Set the incoming value to session value.
 * Creation date: (08/13/2004 4:49:44 PM)
 */
public static void setDestinationUrl(HttpServletRequest request,
									 HttpServletResponse response,
									 String destinationUrl) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("destinationUrl", destinationUrl);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
}
/**
 * Set the incoming value to session value.
 * Creation date: (08/13/2004 4:49:44 PM)
 */
public static void setGrowerSeq(HttpServletRequest request,
								HttpServletResponse response,
								String growerSeq) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("growerSeq", growerSeq);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
}
/**
 * Set the incoming value to session value.
 * Creation date: (08/13/2004 4:49:44 PM)
 */
public static void setIsFieldRep(HttpServletRequest request,
							   HttpServletResponse response,
							   String isFieldRep) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("isFieldRep", isFieldRep);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
}
/**
 * Set the incoming value to session value.
 * Creation date: (08/13/2004 4:49:44 PM)
 */
public static void setPayrollNumber(HttpServletRequest request,
									HttpServletResponse response,
									String payrollNumber) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("payrollNumber", payrollNumber);
		java.util.Date theDate = new java.util.Date();
		long minuteInMillis = 1000 * 60;
		theDate.setTime(theDate .getTime() + (minuteInMillis * 1));
		//String theDateString = theDate.toString();
		SessionVariables.setPayrollNumberTimer(request,
											   response,
											   theDate);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
}
/**
 * Set the incoming value to session value.
 * Creation date: (10/01/2004 4:49:44 PM)
 */
public static void setPayrollNumberTimer(HttpServletRequest request,
										 HttpServletResponse response,
										 java.util.Date payrollNumberTimer) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("payrollNumberTimer", payrollNumberTimer);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
}
/**
 * Set the incoming value to session value.
 * Creation date: (09/21/2004 4:49:44 PM)
 */
public static void setSecuritySystem(HttpServletRequest request,
									HttpServletResponse response,
									String securitySystem) 
{
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);

	try
	{			
		sess.setAttribute("securitySystem", securitySystem);				
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
}
/**
 * Set the incoming value to session value.
 * Creation date: (09/21/2004 4:49:44 PM)
 */
public static void setSecurityUser(HttpServletRequest request,
									HttpServletResponse response,
									String securityUser) 
{
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);

	try
	{			
		sess.setAttribute("securityUser", securityUser);				
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
}
/**
 * Set the incoming value to session value.
 * Creation date: (09/21/2004 4:49:44 PM)
 */
public static void setSecurityValue(HttpServletRequest request,
									HttpServletResponse response,
									String securityValue) 
{
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);

	try
	{			
		sess.setAttribute("securityValue", securityValue);				
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiGroupProfile(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiGroupProfile) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiGroupProfile", ttiGroupProfile);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiProfile.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiProfile(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiProfile) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiProfile", ttiProfile);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiSecStatus(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiSecStatus) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiSecStatus", ttiSecStatus);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiTheURL(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiTheURL) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiTheURL", ttiTheURL);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiTSBroker(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiTSBroker) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiTSBroker", ttiTSBroker);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiTSCo(HttpServletRequest request,
								 HttpServletResponse response,
								 java.math.BigDecimal ttiTSCo) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiTSCo", ttiTSCo);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiTSCustServRep(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiTSCustServRep) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiTSCustServRep", ttiTSCustServRep);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiTSHierLvl(HttpServletRequest request,
								 HttpServletResponse response,
								 java.math.BigDecimal ttiTSHierLvl) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiTSHierLvl", ttiTSHierLvl);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiTSMarket(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiTSMarket) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiTSMarket", ttiTSMarket);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiTSNatlSlsMgr(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiTSNatlSlsMgr) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiTSNatlSlsMgr", ttiTSNatlSlsMgr);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiTSRegSlsMgr(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiTSRegSlsMgr) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiTSRegSlsMgr", ttiTSRegSlsMgr);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiTSStatus(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiTSStatus) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiTSStatus", ttiTSStatus);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiTSTrade(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiTSTrade) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiTSTrade", ttiTSTrade);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiURLAppType(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiURLAppType) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiURLAppType", ttiURLAppType);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiURLAuthLvl(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiURLAuthLvl) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiURLAuthLvl", ttiURLAuthLvl);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserID.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiUserID(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiUserID) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiUserID", ttiUserID);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}

/**
 * Return the User Roles for the current session.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static String[] getSessionttiUserRoles(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String ttiUserRoles[] = null;

	try
	{		
		ttiUserRoles = (String[]) sess.getAttribute("ttiUserRoles");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return ttiUserRoles;	
}

/**
 * Set the incoming value to session value ttiUserRoles.
 * Creation date: (12/27/2002 4:49:44 PM)
 */
public static void setSessionttiUserRoles(HttpServletRequest request,
								 HttpServletResponse response,
								 String[] ttiUserRoles) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiUserRoles", ttiUserRoles);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
/**
 * Set the incoming value to session value ttiUserRoles.
 * Creation date: (08/26/2002 4:49:44 PM)
 */
public static void setSessionValue(HttpServletRequest request,
								   HttpServletResponse response,
								   String name,
								   String value) 
{	
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);

	try
	{			
		sess.setAttribute(name, value);				
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}		
}
/**
 * Return the User Groups for the current session.
 * Creation date: (4/9/2003 4:49:44 PM)
 */
public static String[] getSessionttiUserGroups(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String ttiUserGroups[] = null;

	try
	{		
		ttiUserGroups = (String[]) sess.getAttribute("ttiUserGroups");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return ttiUserGroups;	
}

/**
 * Return the User Paths for the current session.
 * Creation date: (4/9/2003 4:49:44 PM)
 */
public static String[] getSessionttiUserPaths(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String ttiUserPaths[] = null;

	try
	{		
		ttiUserPaths = (String[]) sess.getAttribute("ttiUserPaths");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return ttiUserPaths;	
}

/**
 * Set the incoming value to session value ttiUserGroups.
 * Creation date: (4/9/2003 4:49:44 PM)
 */
public static void setSessionttiUserGroups(HttpServletRequest request,
								 HttpServletResponse response,
								 String[] ttiUserGroups) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiUserGroups", ttiUserGroups);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}

/**
 * Set the incoming value to session value ttiUserPaths.
 * Creation date: (4/9/2003 4:49:44 PM)
 */
public static void setSessionttiUserPaths(HttpServletRequest request,
								 HttpServletResponse response,
								 String[] ttiUserPaths) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiUserPaths", ttiUserPaths);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}

/**
 * Return the User Paths for the current session.
 * Creation date: (4/9/2003 4:49:44 PM)
 */
public static String[] getSessionttiUserPathTitles(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String ttiUserPathTitles[] = null;

	try
	{		
		ttiUserPathTitles = (String[]) sess.getAttribute("ttiUserPathTitles");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return ttiUserPathTitles;	
}

/**
 * Return the User Paths for the current session.
 * Creation date: (4/9/2003 4:49:44 PM)
 */
public static String getSessionttiUserPubFolders(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String ttiUserPubFolders = null;

	try
	{		
		ttiUserPubFolders = (String) sess.getAttribute("ttiUserPubFolders");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return ttiUserPubFolders;	
}

/**
 * Return the User Paths for the current session.
 * Creation date: (4/9/2003 4:49:44 PM)
 */
public static String[] getSessionttiUserPubPaths(HttpServletRequest request,
						HttpServletResponse response) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	String ttiUserPubPaths[] = null;

	try
	{		
		ttiUserPubPaths = (String[]) sess.getAttribute("ttiUserPubPaths");	
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
return ttiUserPubPaths;	
}

/**
 * Set the incoming value to session value ttiUserPaths.
 * Creation date: (4/9/2003 4:49:44 PM)
 */
public static void setSessionttiUserPathTitles(HttpServletRequest request,
								 HttpServletResponse response,
								 String[] ttiUserPathTitles) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiUserPathTitles", ttiUserPathTitles);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}

/**
 * Set the incoming value to session value ttiUserPaths.
 * Creation date: (4/9/2003 4:49:44 PM)
 */
public static void setSessionttiUserPubFolders(HttpServletRequest request,
								               HttpServletResponse response,
								               String ttiUserPubFolders) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiUserPubFolders", ttiUserPubFolders);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}

/**
 * Set the incoming value to session value ttiUserPaths.
 * Creation date: (4/9/2003 4:49:44 PM)
 */
public static void setSessionttiUserPubPaths(HttpServletRequest request,
								 HttpServletResponse response,
								 String[] ttiUserPubPaths) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiUserPubPaths", ttiUserPubPaths);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}

/**
 * Set the incoming value to session value ttiUserPaths.
 * Creation date: (4/9/2003 4:49:44 PM)
 */
public static void setSessionttiUserPubPaths(HttpServletRequest request,
								 HttpServletResponse response,
								 String ttiUserPubFolders) 
{

	//**********************************
	// Allow Session Variable Access
	//**********************************
	HttpSession sess = request.getSession(true);

	try
	{
			
		sess.setAttribute("ttiUserPubFolders", ttiUserPubFolders);	
			
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}	
	
	
}
}
