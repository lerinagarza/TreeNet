package com.treetop;

import java.sql.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import sun.misc.*;
import com.ibm.as400.resource.*;
import com.ibm.as400.access.*;

/**
 * Security methods.
 * Creation date: (6/28/2002 4:47:20 PM)
 * William T Haile: 
 */
public class Security {
	/**
	 * Test the methods.
	 *
	 * Creation date: (07/28/2004 1:20:32 PM)	
	 */
	
	private static String defaultAuthorization = "Basic dGhhaWxlOm05dngzMg==";  //THAILE
	
	public static void main(String[] args) {
	
		try {	
	
			String profile = "TWALTO";				
			String msg   = validateUserProfile(profile);
				
			System.out.println("User profile: " + profile + " validated");	    
		}
		
		catch(Exception e) {
			System.out.println("validateUserProfile error: " + e); 
		}				
		
	}
	/**
	 *  Validate the user profile. 
	 *  Returns an error message in the form of a String,
	 *  Otherwise the error message is empty for a valid AS/400 user profile.
	 *
	 *  Creation date: (7/27/2004 8:29:37 AM)
	 */
	public static String validateUserProfile(String inProfile) {
		// 10/28/11 TWalton - no longer need this method, pointing to as400 that has not been valid for 3+ years                            
		String returnMessage  = "";
//	   	String errorMessage   = " The user profile of (" + inProfile +
//							    "), is invalid.  " +
//							    " Please try again. \n";	
//	
//		AS400 system = new AS400("10.6.100.1", "dauser", "web230502");		// Establish an AS/400 session
//		
//		try {
//			 
//			if ((inProfile == null) || (inProfile.equals ("")))
//				returnMessage = " The user profile is empty.  " +										
//							    " Please try again. \n";	    
//		   	else {	
//				RUser  user     = new RUser(system, inProfile);
//	        	String userName = (String) user.getAttributeValue(RUser.FULL_NAME);
//			
//				if (userName != null)		
//					returnMessage = "";
//				else
//					returnMessage = errorMessage;	
//		   	}
//		}	
//		catch(Throwable theException) {			
			//theException.printStackTrace();
			//System.err.println("An exception occured: - com.treetop.Security." +
							   //"validateUserProfile(String), Profile: " + inProfile);
//			returnMessage = errorMessage;
//		}
	
		return returnMessage;		
	}
/**
 * Insert the method's description here.
 * Creation date: (8/28/2002 11:22:48 AM)
 * @return java.lang.String
 */
public static String getPassword(String auth) {
	
	String password = "invalid";
	
	if (auth == null || (!auth.toUpperCase().startsWith("BASIC ") &&
		auth.length() > 6))
	{
		password = "web230502";
	     return password;
	}
		
	String userPassEncoded = auth.substring(6);

	sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();

	String userPassDecoded = null;

	try
	{

		userPassDecoded = new String(dec.decodeBuffer(userPassEncoded));

	}

	catch (java.io.IOException e)

	{
		password = "IOError";
		return password;
	}

	int c = userPassDecoded.indexOf(":");

	password = userPassDecoded.substring(c+1);

	//return new String [] {userPassDecoded.substring(0,c),
	//	                  userPassDecoded.substring(c+1)};

		
	return password;
}
/**
 * Return the Basic Profile
 * Creation date: (6/28/2002 10:43:01 AM)
 * @return java.lang.String
 * @param param char
 */
public static String getProfile(String auth) {

	String profile = "invalid";
	
	if (auth == null || (!auth.toUpperCase().startsWith("BASIC ") &&
		auth.length() > 6))
	{
		profile = "DAUSER";
	     return profile;
	}
		
	String userPassEncoded = auth.substring(6);

	sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();

	String userPassDecoded = null;

	try
	{

		userPassDecoded = new String(dec.decodeBuffer(userPassEncoded));

	}

	catch (java.io.IOException e)

	{
		profile = "IOError";
		return profile;
	}

	int c = userPassDecoded.indexOf(":");

	profile = userPassDecoded.substring(0,c);

	//return new String [] {userPassDecoded.substring(0,c),
	//	                  userPassDecoded.substring(c+1)};

		
	return profile;
}

	public static String getUserAuthorization(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		if (authorization == null) {
			authorization = defaultAuthorization;
		}
		return authorization;
	}
}
