package com.treetop.utilities;

import java.sql.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import com.treetop.*;
import com.treetop.businessobjects.UrlValidationData;



public class NetSecurity {
	
	static Hashtable urlAuths = null;
	
	
	/**
	 * Creation date: (04/01/2009)	
	 * 
	 * Grant or deny access to custom Treetop sever applications.
	 * 
	 *   Pass in HttpServletRequest and HttpServletResponse objects
	 * to grant or deny access. 
	 * 
	 * Session variables are updated allowing servlets to perform
	 * checks against them.
	 *
	 * Incomming request parameters:
	 * 	javax.servlet.http.HttpServletRequest request
	 *  javax.servlet.http.HttpServletResponse response
	 *
	 * Session variables used:
	 *
	 *    ttiSecStatus:      Message to return to calling servlet or jsp.
	 *    
	 *    ttiProfile:        Current value of user signed on to the server.
	 *    
	 *    ttiUserID:         Current value of "ttiProfile" changed to uppercase.
	 *    
	 *    ttiUserRoles:      Roles assigned to user number (profile) from file
	 *                       DPPMUSERR.
	 *                       
	 *    ttiUserGroups:     Groups assigned to user number (profile) from file
	 *                       DPPMUSERG.
	 *                       
	 *    securitySystem1:   Used for additional menu security like Grower, Payroll, or Forms.
	 *    securitySystem2:   Used for additional menu security like Grower, Payroll, or Forms.
	 *    securitySystem3:   Used for additional menu security like Grower, Payroll, or Forms.
	 *    securitySystem4:   Used for additional menu security like Grower, Payroll, or Forms.
	 *    securitySystem5:   Used for additional menu security like Grower, Payroll, or Forms.
	 *                       
	 *  
	 * 
	 * @param request Object that encapsulates the request to the servlet 
	 * @param response Object that encapsulates the response from the servlet
	 * @return void.
	 */
	public synchronized static void checkSecurity(javax.servlet.http.HttpServletRequest request,
							  javax.servlet.http.HttpServletResponse response)
	{
		
		try
		{
			//************************************************************************
			//Initialize the class if needed.
			//************************************************************************
			
			if (urlAuths == null)
			{
			   try {
				   urlAuths = getUrlAuths();
			   } catch (Exception e) {
				   System.out.println("Error when loading security hashtable " + e +
			       " - com.treetop.utilities.NetSecurity.init()");
			   }
			}

			//************************************************************************ 
			// Get the current requested URL.
			//************************************************************************
			String theurl = SessionVariables.getSessionttiTheURL(request,response);		
		
			if (theurl == null)
				theurl = "/servlet/com.treetop.servlets.TreeNetInq";

			
				
			//************************************************************************
			// Retrieve / set session variable "ttiProfile".
			//************************************************************************
	        String ttiProfile = SessionVariables.getSessionttiProfile(request,response);
	        

	        if (ttiProfile == null)
	        {
		        String[] returnInfo = getSessionProfile(request, response);
		        ttiProfile = returnInfo[0];
	      
		        SessionVariables.setSessionttiProfile(request,response,returnInfo[0]);
	        }
	      

			
	        //************************************************************************
		    // Retrieve / set session variable ttiUserID.
		    //************************************************************************
		    String ttiUserID = SessionVariables.getSessionttiUserID(request,response);
		     
			//**
			// Allow for testing here with different user ID's.
			//
			//ttiUserID = null;
		 
		    if (ttiUserID == null)
		    {
		        ttiUserID = ttiProfile.toUpperCase();
		        SessionVariables.setSessionttiUserID(request,response,ttiUserID);
		    }

		    ttiUserID = ttiUserID.toUpperCase();

		    	

		    //*************************************************************************
		    // Retrieve / set session array variable ttiUserRoles.
		    //*************************************************************************
		    String[] roleInfo = SessionVariables.
		                        getSessionttiUserRoles(request,response);

		    //**
			// Allow for testing here with different profiles.
			//
			 //roleInfo = null;
		    if (roleInfo == null)
		    {
		    	roleInfo = getProfileRoles(ttiProfile, theurl);
		    
		    	if (!roleInfo[0].equals("Authority passed"))
				{
					SessionVariables.setSessionttiSecStatus(request,response,roleInfo[0]);
					return;
				}

				if (roleInfo[1].equals(""))
				{
					String emptyArray[] = new String[1];
					emptyArray[0] = "";
					SessionVariables.setSessionttiUserRoles(request,response,emptyArray);
				} else
				{
					String allRoles = roleInfo[1];
					int total = 1;
				
					for(int i = 0; i < allRoles.length(); i++)
					{
						if (allRoles.substring(i,i + 1).equals(","))
						total++;
					}
					
					int count = 0;
					int i     = 0;
					int x     = 0;
					String roles[] = new String[total]; 
				
					while (allRoles.length() > x)
					{
						if (allRoles.substring(x,x + 1).equals(","))
						{
							roles[count] = allRoles.substring(i, x);
							i = x + 1;
							count = count + 1;
						}
					
						x = x + 1;
					}
				
					roles[count] = allRoles.substring(i, allRoles.length());
					SessionVariables.setSessionttiUserRoles(request,response,roles);
				}
		    }	

			
			//***********************************************************************
	        // Retrieve / set session variable ttiUserGroups.
	        //***********************************************************************
	        String[] groupInfo = SessionVariables.getSessionttiUserGroups(request,response);
	                                 

	        //**
			// Allow for testing here with different profiles.
			//
			//groupInfo = null;

	        if (groupInfo == null)
		    {
			    groupInfo = getUserGroups(ttiProfile);

				if (groupInfo[1].equals(""))
				{
					String emptyArray[] = new String[1];
					emptyArray[0] = "";
					SessionVariables.setSessionttiUserGroups(request,response,emptyArray);
				} else
				{
					String allGroups = groupInfo[1];
					int total = 1;
				
					for(int i = 0; i < allGroups.length(); i++)
					{
						if (allGroups.substring(i,i + 1).equals(","))
							total++;
					}
					
					int count = 0;
					int i     = 0;
					int x     = 0;
					String groups[] = new String[total]; 
				
					while (allGroups.length() > x)
					{
						if (allGroups.substring(x,x + 1).equals(","))
						{
							groups[count] = allGroups.substring(i, x);
							i = x + 1;
							count = count + 1;
						}
					
						x = x + 1;
					}
				
					groups[count] = allGroups.substring(i, allGroups.length());
					SessionVariables.setSessionttiUserGroups(request,response,groups);
				}
		    }
	        
	   
		    //**************************************************************************
		    // Verify user authority to application.
		    //**************************************************************************	    
		    
		    String[] urlInfo = getURLAuthority(theurl, request, response);
		    
		    //if (urlInfo[0].equals("Authority Failed"))
			//{
				SessionVariables.setSessionttiSecStatus(
									request,response,urlInfo[1]);
				//return;
			//}
		
////			if (urlInfo[0].equals("Authority Passed") &&
////				urlInfo[2].equals("yes"))
////			{
////				SessionVariables.setSessionttiSecStatus(
////									request,response,"logOn");
////				return;
////			}
			
			


		}
		catch(Throwable theException)
		{
			// uncomment the following line when unexpected exceptions
			// are occuring to aid in debugging the problem.
			 theException.printStackTrace();
		
		}
		return;
	}
	
	/**
	 * Get the session user profile.
	 * Creation date: (03/13/2009)
	 */
	private static String[] getSessionProfile(javax.servlet.http.HttpServletRequest request,
		                              		  javax.servlet.http.HttpServletResponse response) 
		                            
	{
		// Determine current server.
	    // Verify and default authorization value if server is local.

	    String[] profileInfo = new String[2]; 
		String theurl = request.getHeader("Host");
		String auth = "";
		String ttiProfile = "";



		if (theurl.equals("localhost:9081") ||
			theurl.equals("localhost:9082") ||
		 //   theurl.equals("qtreetop:25009")) )
			theurl.equals("lawson:27000"))
		{
		    auth = "Basic dGhhaWxlOm05dngzMg=="; // user = thaile
		} else
		    auth = request.getHeader("Authorization");


		// Retrieve the user.
		ttiProfile = com.treetop.Security.getProfile(auth);
		
		
		
		//**
		// Allow for testing here with different profiles.
		//
		//System.out.println("profile override in effect:" + ttiProfile); 
	    //ttiProfile = "GNUTA";     // system operator
		//ttiProfile = "BNELSO";    // ing sales	
		//ttiProfile = "HOMER";     // main menu testing (no one) (myohmy)
		//ttiProfile = "DWEBST";    // technician.
		//ttiProfile = "KWANGL";    // Kim Wangler.
		//ttiProfile = "BBOUTI";    // Bonnie Boutiler
		//ttiProfile = "CHANSH";    // Cindy Hanshew
		//ttiProfile = null;        // default user
		//ttiProfile = "DBRIES";	// Debbie Briesmeister
		



	    profileInfo[0] = ttiProfile;
		return profileInfo;
			
	}
	
	
	/**
	 * For each user (session) obtain their available roles.
	 * Creation date: (3/19/2009)
	 */
	private static String[] getProfileRoles(String ttiProfile, String theurl) 
	{
		

		String[] theReturn = new String[2];
		theReturn[0] = "";
		theReturn[1] = "";
		String whereRole = "";
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
	    try
	    {
	    	conn = ConnectionStackSecurity.getConnection();
		    stmt = conn.createStatement(); 
	        rs   = stmt.executeQuery("SELECT DPNUSER, DPNUSERNBR, DPMROLENBR " +
	                                       "FROM TREENET.DPPNUSER " +
	                                       "INNER JOIN TREENET.DPPMUSERR " +
	                                       "ON DPMUSERNBR = DPNUSERNBR " +
	                                       "WHERE DPNUSER = '" + ttiProfile.toUpperCase() + "' " +
	                                       "GROUP BY DPNUSER, DPNUSERNBR, DPMROLENBR " +
	                                       "ORDER BY DPMROLENBR");
	      	while (rs.next())
	      	{
		     	theReturn[0] = "Authority passed";
	         	if (whereRole.equals(""))
	            	whereRole = rs.getString("DPMROLENBR");
	         	else
	         		whereRole = whereRole + "," + rs.getString("DPMROLENBR");
	      	}
	      	
	      	rs.close();
	      	stmt.close();
	      	
	      	if (whereRole.equals(""))
	      	{	
		      	String msg = "You are currently not set up (Role) to view this page" +
		      	             " (" + theurl + "). " +
						     "Please contact Information Services. " +
						     "com.treetop.servlets.TTISecurity.performTask(req,rsp)." +
						     "getProfileRoles(" + ttiProfile + ")";
		   		theReturn[0] = msg;
		   		return theReturn;
	      	}   
	         
	        theReturn[1] = whereRole;
	      
	   	} catch (SQLException sqle) { 
		     System.err.println("SQL error TTISecurity.getProfileRoles: " + sqle);
		  
		   	String msg = "You are currently not set up (Role) to view this page." +
						 "Please contact Information Services. " +
						 "com.treetop.servlets.TTISecurity.performTask(req,rsp)." +
						 "getProfileRoles(" + ttiProfile + ")";
		   	theReturn[0] = msg;
		   	return theReturn;
	   	}
	   	finally
		{
			if (conn != null)
				try {
					ConnectionStackSecurity.returnConnection(conn);
				} catch(Exception e) {
					System.out.println("Error returning connection in NetSecurity.getProfileRoles(). " + e);
				}
				
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
					System.out.println("Error closing stmt in NetSecurity.getProfileRoles(). " + e);
				}
					
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					System.out.println("Error closing resultset in NetSecurity.getProfileRoles(). " + e);
				}
		}
	     	
		return theReturn;	
	}
	
	
	/**
	 * Load user groups associated to the user profile
	 * Creation date: (3/20/2009)
	 */
	private static String[] getUserGroups(String ttiProfile) 
	{
		String[] theReturn = new String[2];
		theReturn[0] = "";
		theReturn[1] = "";
		String groups = "";
	    Connection conn = null;
	    Statement stmt  = null;
	    ResultSet rs    = null;
	    
		try
		{
			conn = ConnectionStackSecurity.getConnection();
			stmt = conn.createStatement();
			rs   = stmt.executeQuery
				("SELECT * " +
				 "FROM TREENET.DPPMUSERG " +
				 "INNER JOIN TREENET.DPPNUSER " +
				 "ON DPMUSERNBR = DPNUSERNBR " +
				 "WHERE DPNUSER = '" + ttiProfile.toUpperCase() + "' " +
				 "ORDER BY DPMGRPNBR");

			while (rs.next())
			{
				theReturn[0] = "Authority passed";
				
				if (groups.equals(""))
					groups = rs.getString("DPMGRPNBR");
				else
					groups = groups + "," + rs.getString("DPMGRPNBR");

				theReturn[1] = groups;
			}

			rs.close();
			stmt.close();
		} catch (Exception e)
		{
			System.err.println("SQL error TTISecurity.getUserGroups: " + e);

			String msg = " Error occured loading user groups for this profile. " + e +
			             " @ com.treetop.servlets.TTISecurity.perfromTask(req,rsp)." +
			             "getUserGroups(" + ttiProfile + ")";
			theReturn[0] = msg;
		}
		finally
		{
			if (conn != null)
				try {
					ConnectionStackSecurity.returnConnection(conn);
				} catch(Exception e) {
					System.out.println(" Error returning connection. NetSecurity.getUserGroup() " + e);
				}
			
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
					System.out.println(" Error closing statement. NetSecurity.getUserGroup()" + e);
				}
				
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					System.out.print(" Error closing result set. NetSecurity.getUserGroup()" + e);
				}
		}
		
		return theReturn;
	}
	
	
	/**
	 *  This method receives in as parameters the requested url. 
	 *
	 *  The current url is tested against current user profile values. The return
	 * array contains test results.
	 *
	 * return array "urlInfo[]"
	 *  urlInfo[0] = "Authority Passed" or "Authority Failed"
	 *  urlFile[1] = a breif message about authority failure.
	 * 
	 * Creation date: (03/20/2009)
	 *       
	 */
	private static String[] getURLAuthority(String theurl,
						   				   javax.servlet.http.HttpServletRequest request,
						   				   javax.servlet.http.HttpServletResponse response) 
	{

		// Define return array.
		String   profile  = SessionVariables.getSessionttiProfile(request,response);
		String[] roles    = SessionVariables.getSessionttiUserRoles(request,response);
		String[] groups   = SessionVariables.getSessionttiUserGroups(request,response);
		String[] urlInfo = new String[3];
		String goToLogOn = "no";
		urlInfo[2] = goToLogOn;
		
		
		// obtain the authority hash table.
	    UrlValidationData uvd = (UrlValidationData) urlAuths.get(theurl);
	    
	    //condiction access variable
	    boolean hasAccess = false;
	    
	    if (uvd != null) { // check to see if user's role is included in the validation data for that URL.
	    	// Check to see if users roles are in the URL's valid roles
	    	for (int i=0; i<roles.length; i++) {
	    		String role = roles[i];
	    		Iterator iter = uvd.getRoles().iterator();
	    		
	    		while (iter.hasNext()) {
	    			if (role.equals((String)iter.next())) {
	    				hasAccess = true;
	    				break;
	    			}
	    		}
	    		if (hasAccess) break; // break out for loop
	    	}
	    	
	    	// check to see if user's group has access to this URL
	    	if (!hasAccess) {
	        	for (int i=0; i<groups.length; i++) {
	        		String group = groups[i];
	        		Iterator iter = uvd.getGroups().iterator();
	        		while (iter.hasNext()) {
	        			if (group.equals((String)iter.next())) {
	        				hasAccess = true;
	        				break;
	        			}
	        		}
	        		if (hasAccess) break; // break out for loop
	        	}
	    	}
	    }
		
		
		// hasAccess now has been set.
		// ------------------------------------
		
		try
		{
			if (!hasAccess)
	        {
		        String msg = "Authority not granted for this page.";
		        urlInfo[0] = "Authority Failed";
		        urlInfo[1] = msg;
	        } else
	        {
		    	urlInfo[0] = "Authority Passed";
		    	urlInfo[1] = "";
			} 

			// If authority has passed verify additional log-on
			// requirements for this url.		
			if (urlInfo[0].equals("Authority Passed"))
			{
				 goToLogOn =  validateUrlSecurity(request, response, uvd);
				 urlInfo[2] = goToLogOn; 
			}
			return urlInfo;
		 		
		} catch(Exception e)
		{
			String msg = "Authority not granted for this page. " + e +
			             " - com.treetop.servlets.TTISecurity.getURLAuthority()";
			urlInfo[0] = "Authority Failed";
			urlInfo[1] = msg;
			return urlInfo;
		}
	}
	
	
	/**
	 *  This method checks the "Additional Security" fields 
	 * defined in the url class.
	 *  If a value is entered in any of the "Additional Security"
	 * fields, checks are made to verify proper login information
	 * has been provided by the current user.
	 *  If this method determines additional login information
	 * is required the current user session is routed to a
	 * Log On screen. The Log On servlet takes control of the 
	 * session at that point and current attemps to pass 
	 * security form within this servlet are finished.
	 * 
	 * Rules to grant authority.
	 * 	A: If a system is found.
	 * 		> Test all systems to see if session information is
	 * 			there. if session information exists for any of 
	 * 			them set "anyPassed" = "yes".
	 *  B: Test each system with coresponding required code.
	 * 		> If required = "Y", verify coresponding log on
	 * 			information exists for the session.
	 *  		If it does not - exit to the log on servlet.
	 * 		> If required <> "Y" and anyPassed = "no" then
	 * 			exit to log on servlet.
	 * 
	 * 	
	 * Rules for session security settings.
	 *  	> Session variables for user security are set 
	 * 			to empty.
	 * 		> If grower is required set session security to
	 * 			grower.
	 * 		> Else if payroll employee is required set session
	 * 			security to payroll employee.
	 * 		> Else if From signon is required set session 
	 * 			security to Form Info.
	 * 		> If session security is empty and grower security
	 * 			is checked set session secuirty to grower.
	 * 		> If session security is empty and payroll security
	 * 			is checked set session security to payroll.
	 * 		> If session security is empty and Form security
	 * 			is checked set session security to Form.
	 * 
	 *  
	 * Creation date: (4/01/2009)
	 */
	private static String validateUrlSecurity(javax.servlet.http.HttpServletRequest request,
									 javax.servlet.http.HttpServletResponse response,
									 com.treetop.businessobjects.UrlValidationData uvd)
	{
		// Set a value of NO ADDITIONAL SECURITY PASSED YET.
		String anyPassed = "no";
		String goToLogOn = "no";
		String ttiProfile = SessionVariables.getSessionttiProfile(request,response);
		String growerNumber = "";
		String payrollNumber = "";
		String formNumber = "";
		String securitySystem = "";
		String securityValue  = "";
		String securityUser   = "";
		SessionVariables.setSecuritySystem(request, response, securitySystem);
		SessionVariables.setSecurityValue(request, response, securityValue);
		SessionVariables.setSecurityUser(request, response, securityUser);
		
		try {
			// Any additional Security required for this url?
			// If not then exit this method - quit testing. 
			if (uvd.getSecuritySystem1().trim().equals("") &&
				uvd.getSecuritySystem2().trim().equals("") &&
				uvd.getSecuritySystem3().trim().equals("") &&
				uvd.getSecuritySystem4().trim().equals("") &&
				uvd.getSecuritySystem5().trim().equals("") )
				return goToLogOn;
			
			// Load vectors of all Security and Required codes.
			Vector systems = new Vector();
			Vector codes = new Vector();
			Vector values = new Vector();
			
			if (!uvd.getSecuritySystem1().trim().equals(""))
			{
				systems.addElement(uvd.getSecuritySystem1().trim());
				codes.addElement(uvd.getSecurityRequired1().trim());
				values.addElement(uvd.getSecurityValue1().trim());
			}
			if (!uvd.getSecuritySystem2().trim().equals(""))
			{
				systems.addElement(uvd.getSecuritySystem2().trim());
				codes.addElement(uvd.getSecurityRequired2().trim());
				values.addElement(uvd.getSecurityValue2().trim());
			}
			if (!uvd.getSecuritySystem3().trim().equals(""))
			{
				systems.addElement(uvd.getSecuritySystem3().trim());
				codes.addElement(uvd.getSecurityRequired3().trim());
				values.addElement(uvd.getSecurityValue3().trim());
			}
			if (!uvd.getSecuritySystem4().trim().equals(""))
			{
				systems.addElement(uvd.getSecuritySystem4().trim());
				codes.addElement(uvd.getSecurityRequired4().trim());
				values.addElement(uvd.getSecurityValue4().trim());
			}
			if (!uvd.getSecuritySystem5().trim().equals(""))
			{
				systems.addElement(uvd.getSecuritySystem5().trim());
				codes.addElement(uvd.getSecurityRequired5().trim());
				values.addElement(uvd.getSecurityValue5().trim());
			}								
			
			// Test to see if any Additional Security Systems
			// have already been entered into.
			for (int x = 0; x < systems.size(); x++)
			{
				String system = (String) systems.elementAt(x);
				String code = (String) codes.elementAt(x);
				String value = (String) values.elementAt(x);
				
				if (system.equals("GR"))
				{
					growerNumber = com.treetop.SessionVariables.
										 getGrowerSeq(request, response);
					if (growerNumber != null &&
						!growerNumber.equals(""))	
					    anyPassed = "yes";			
				}
				
				if (system.equals("PR"))
				{
					payrollNumber = com.treetop.SessionVariables	    
									.getPayrollNumber(request, response, "x");
					if (payrollNumber != null &&
						!payrollNumber.equals(""))	
						anyPassed = "yes";	
				}
				
				if (system.equals("FM"))
				{
					String formSecurity = "FM" + value;
					String formSecurityValue = com.treetop.SessionVariables.getSessionValue(
												request, response, formSecurity);
					
					if (formSecurityValue != null &&
						!formSecurityValue.equals(""))
					{
						anyPassed = "yes";
						formNumber = formSecurity;
					}
				}
			}
			
			// If NO additional log-on information has been 
			// entered for this session and additional log-on
			// information is required for this url then set
			// the return parameter to "yes" and exit.
			if (anyPassed.equals("no") && ttiProfile.toLowerCase().equals("default"))
			{
				goToLogOn = "yes";
				
			// Verify that each additional log-on system is 
			// loged on to if required for is set to "Y".
			} else
			{
			
				// Test to see if any additional log on requirements
				// are needed for this current session and url combo.
				for (int x = 0; x < systems.size(); x++)
				{
					String system = (String) systems.elementAt(x);
					String code = (String) codes.elementAt(x);
					String value = (String) values.elementAt(x);
				
					if (system.equals("GR") && 
						code.equals("Y"))
					{
						if (growerNumber == null ||
							growerNumber.trim().equals(""))
							goToLogOn = "yes";
					}
					
					if (system.equals("PR") && 
						code.equals("Y"))
					{
						if (payrollNumber == null ||
							payrollNumber.trim().equals(""))
							goToLogOn = "yes";
					}
					
					if (system.equals("FM") &&
						code.equals("Y"))
					{
						String theValue = com.treetop.SessionVariables.getSessionValue(
											request, response, "FM" + value);
						if (theValue == null ||
							theValue.equals(""))
							goToLogOn = "yes";
					}
				}
				
				if(goToLogOn.equals("no"))
				{
					// If security has been passed by using the
					//additional security fields assigned to a
					//url, session variables must be set for
					//the purpose of identifying the user 
					//authority that granted access to the url.
					String sessionSecuritySet = "no";
					String required = "no";
					
					// Test to see if system entry is required.
					for (int x = 0; x < codes.size(); x++)
					{
						String code = (String) codes.elementAt(x);
						if (code.equals("Y"))
							required = "yes";
					}
					
					if (required.equals("no"))
					{
						// Is there a Grower Number?
						if (growerNumber!= null &&
							!growerNumber.equals(""))
						{
							SessionVariables.setSecuritySystem(request, response, "GR");
							SessionVariables.setSecurityValue(request, response, growerNumber);
							return goToLogOn;
						}
						
						// Is there a Payroll Number?
						if (payrollNumber != null &&
							!payrollNumber.equals(""))
						{
							SessionVariables.setSecuritySystem(request, response, "PR");
							SessionVariables.setSecurityValue(request, response, payrollNumber);
							return goToLogOn;
						}
						
						if (formNumber != null &&
							!formNumber.equals(""))
						{
							SessionVariables.setSecuritySystem(request, response, "FM");
							SessionVariables.setSecurityValue(request, response, formNumber);
							String value = SessionVariables.getSessionValue(request, response, formNumber);
							if (value == null)
								value = "";
							SessionVariables.setSecurityUser(request, response, value);
							return goToLogOn;
						}
						
					}
					
					// Is grower required?
					for (int x = 0; x < systems.size(); x++)
					{
						String system = (String) systems.elementAt(x);
						String code   = (String) codes.elementAt(x);
						if(system.equals("GR") && code.equals("Y"))
						{
							sessionSecuritySet = "yes";
							x = systems.size();
							SessionVariables.setSecuritySystem(request, response, "GR");
							SessionVariables.setSecurityValue(request, response, growerNumber);
						}
					}
					
					// Is payroll required.
					if (sessionSecuritySet.equals("no"))
					{
						for (int x = 0; x < systems.size(); x++)
						{
							String system = (String) systems.elementAt(x);
							String code   = (String) codes.elementAt(x);
							if(system.equals("PR") && code.equals("Y"))
							{
								sessionSecuritySet = "yes";
								x = systems.size();
								SessionVariables.setSecuritySystem(request, response, "PR");
								SessionVariables.setSecurityValue(request, response, payrollNumber);
							}
						}
					}
					
					// Is form number required.
					if (sessionSecuritySet.equals("no"))
					{
						for (int x = 0; x < systems.size(); x++)
						{
							String system = (String) systems.elementAt(x);
							String code   = (String) codes.elementAt(x);
							if(system.equals("FM") && code.equals("Y"))
							{
								sessionSecuritySet = "yes";
								x = systems.size();
								SessionVariables.setSecuritySystem(request, response, "FM");
								SessionVariables.setSecurityValue(request, response, formNumber);
								String value = SessionVariables.getSessionValue(request, response, formNumber);
								if (value == null)
									value = "";
								SessionVariables.setSecurityUser(request, response, value);
							}
						}
					}
				}
			}
		}
		catch (Exception e){
		}
		
		return goToLogOn;
	}
	
	
	/**
	 * NetSecurity constructor.
	 */
	public NetSecurity() 
	{
		init();
	}
	
	
	/**
	 * Initializes the class.
	 * Creation date: (04/01/2009)	
	 */
	public void init() {
		if (urlAuths == null)
		{
		   try {
			   urlAuths = getUrlAuths();
		   } catch (Exception e) {
			   System.out.println("Error when loading security hashtable " + e +
		       " - com.treetop.utilities.NetSecurity.init()");
		   }
		}
	}
	
	
	/**
	 * Build an sql statement.
	 * 
	 * @param String
	 *            request type
	 * @param Vector
	 *            request class
	 * @return sql string
	 * @throws Exception
	 */
	
	private static String buildSqlStatement(String environment,
			String inRequestType, Vector requestClass) throws Exception {
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();
	
		try { // Get the URL and User Files
	
			if (inRequestType.equals("getUrlInfo")) {
				// cast the incoming parameter class.
	
				// build the sql statement.
				sqlString.append("SELECT  gnvurl, dpmrectype, dpmsecnbr,  ");
				sqlString.append("gnvsecsys1, gnvsecsys2, gnvsecsys3, gnvsecsys4, gnvsecsys5, ");
				sqlString.append("gnvsecreq1, gnvsecreq2, gnvsecreq3, gnvsecreq4, gnvsecreq5, ");
				sqlString.append("gnvsecval1, gnvsecval2, gnvsecval3, gnvsecval4, gnvsecval5 ");
				sqlString.append("FROM " + "TREENET" + ".GNPVMENU ");
				sqlString.append("INNER JOIN " + "TREENET" + ".DPPMURLUSE ");
				sqlString.append("ON GNVURLNBR = DPMURLNBR ");
				sqlString.append("ORDER BY gnvurl, dpmrectype, dpmsecnbr  ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement"
					+ " for request type " + inRequestType + ". " + e);
		}
		// return data.
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceSecurity.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
	
		return sqlString.toString();
	}

	private static Hashtable getUrlAuths() throws Exception {
		
		//only run this if urlAuths is null
		if (urlAuths == null)
		{
			StringBuffer throwError = new StringBuffer();
			Connection conn = null;
			
			try {
				// get a connection to be sent to find methods
				conn = ConnectionStackSecurity.getConnection();
				urlAuths = new Hashtable();
				urlAuths = getUrlAuths(urlAuths, conn);
			} catch (Exception e) {
				throwError.append(e);
			} finally {
				if (conn != null) {
					try {
						ConnectionStackSecurity.returnConnection(conn);
					} catch (Exception e) {
						throwError.append("Error returning connection. " + e);
					}
				}
				
				// log any errors.
				if (!throwError.toString().trim().equals("")) {
					throwError.append(" @ com.treetop.Services.");
					throwError.append("ServiceSecurity.");
					throwError.append("getUrlAuths(");
					throwError.append("). ");
					System.out.println(throwError.toString());
					Exception e = new Exception();
					e.printStackTrace();
				}
			}
		}
	
		return urlAuths;
	}

	/**
	 * Method Created 01/16/09 THAILE // Use to control the information
	 * retrieval
	 * 
	 * @param Hashtable
	 * @parm connection
	 * @return Hashtable
	 * @throws Exception
	 */
	private static Hashtable getUrlAuths(Hashtable urlAuths, Connection conn)
			throws Exception {
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			// Get the list of Url and Users
			Vector parmList = new Vector();
			ps = conn.prepareStatement(buildSqlStatement("PRD", "getUrlInfo",
					parmList));
			rs = ps.executeQuery();
			String priorUrl = null;
			UrlValidationData uvd = null;
			
			while (rs.next()) {
				String url = rs.getString("GNVURL").trim();
				if (!url.equals(priorUrl)) {
					uvd = new UrlValidationData();
					urlAuths.put(url, uvd);
				}
				String type = rs.getString("DPMRECTYPE").trim();
				String value = rs.getString("DPMSECNBR").trim();
				uvd.setSecurityRequired1(rs.getString("gnvsecreq1").trim());
				uvd.setSecurityRequired2(rs.getString("gnvsecreq2").trim());
				uvd.setSecurityRequired3(rs.getString("gnvsecreq3").trim());
				uvd.setSecurityRequired4(rs.getString("gnvsecreq4").trim());
				uvd.setSecurityRequired5(rs.getString("gnvsecreq5").trim());
				uvd.setSecuritySystem1(rs.getString("gnvsecsys1").trim());
				uvd.setSecuritySystem2(rs.getString("gnvsecsys2").trim());
				uvd.setSecuritySystem3(rs.getString("gnvsecsys3").trim());
				uvd.setSecuritySystem4(rs.getString("gnvsecsys4").trim());
				uvd.setSecuritySystem5(rs.getString("gnvsecsys5").trim());
				uvd.setSecurityValue1(rs.getString("gnvsecval1").trim());
				uvd.setSecurityValue2(rs.getString("gnvsecval2").trim());
				uvd.setSecurityValue3(rs.getString("gnvsecval3").trim());
				uvd.setSecurityValue4(rs.getString("gnvsecval4").trim());
				uvd.setSecurityValue5(rs.getString("gnvsecval5").trim());
				
				if (type.equals("G")) {
					uvd.getGroups().add(value);
				} else if (type.equals("R")) {
					uvd.getRoles().add(value);
				} else if (type.equals("U")) {
					uvd.getUsers().add(value);
				}
				priorUrl = url;
			}
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceSecurity.");
			throwError.append("getUrlAuths(");
			throwError.append("Hashtable, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return urlAuths;
	}

	/**
	 * Test the methods.
	 *
	 * Creation date: (04/01/2009)	
	 */
	public static void main(String[] args) {
	
		try {	
	

		}
		
		catch(Exception e) {
			System.out.println(" error: " + e); 
		}				
		
	}

}


