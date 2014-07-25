/*
 * Created on February 1, 2008
 */
package com.treetop.utilities.html;

import com.treetop.services.*;

/**
 * @author twalto
 * Use this class to access services and retrieve basic data
 *
 */
public class RetrieveData {
	/**
	 * Take the User ID and convert it into the LONG NAME
	 * 
	 * Receive In:
	 *     userID -- 
	 *     userType -- AS400, M3 (Currently only set up to deal with M3 -- Default to M3
	 * 
	 * Creation date: (2/1/2008 TWalton)
	 */
	public static String getLongName(String userID, String userType) {
		String returnUser = userID;
		try
		{
		    if (userType.equals("AS400"))
		    {}
			else
			   returnUser = ServiceUser.returnNameFromM3User("PRD", userID);
		}
		catch(Exception e)
		{}
		return returnUser;
	}
}
