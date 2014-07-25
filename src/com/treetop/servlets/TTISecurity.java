package com.treetop.servlets;

import com.ibm.as400.access.AS400;
import com.ibm.as400.resource.RUser;
import com.treetop.ServerInfo;
import com.treetop.SessionVariables;
import com.treetop.businessobjects.UrlValidationData;
import com.treetop.utilities.ConnectionStack;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 * Determine Results Sets upon request. 
 * Creation date: (10/2/2002 9:33:27 AM)
 * William T Haile: 
 */
public class TTISecurity extends javax.servlet.http.HttpServlet {

	static AS400 as400;
	static String startSchedular;
	static Hashtable urlAuths;
	
	
/**
 * Process incoming HTTP GET requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doGet(javax.servlet.http.HttpServletRequest request, 
	              javax.servlet.http.HttpServletResponse response)
	              
	
	   throws javax.servlet.ServletException, java.io.IOException {

	performTask(request, response);

}

/**
 * Process incoming HTTP POST requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doPost(javax.servlet.http.HttpServletRequest request, 
	               javax.servlet.http.HttpServletResponse response)
	               

       throws javax.servlet.ServletException, java.io.IOException {

	performTask(request, response);

}

/**
 * Get the group profile assigned to the user.
 * Creation date: (11/11/2002 3:20:58 PM)
 */
public String getGroupProfile(String ttiUser)
	                            
{
	try {

		RUser user = new RUser(as400, ttiUser);
        String groupName = (String) user.getAttributeValue(RUser.GROUP_PROFILE_NAME);
		
		if (groupName == null)
		{
			System.err.println("Group profile retrieval error for user " + ttiUser +
							   ". - com.treetop.servlets.TTISecurity." +
				               "getGroupProfile(String) - ");
			return "not found";
		} else
		{
			return groupName;
		}
		
			
	
	} catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		String x = theException.toString();
		//theException.printStackTrace();
		System.err.println("An exception occured: - com.treetop.servlets.TTISecurity." +
						   "getGroupProfile(String) - " + x );
	}

	return "not found";
	
}
/**
 * Returns the servlet info string.
 */
public String getServletInfo() {

	return super.getServletInfo();

}
/**
 *  This method receives in as parameters the requested url. 
 *
 *  The current url is tested against current the user profile values. The return
 * array contains test results.
 *
 * return array "urlInfo[]"
 *  urlInfo[0] = "Authority Passed" or "Authority Failed"
 *  urlFile[1] = a breif message about authority failure.
 * 
 * Creation date: (11/11/2002 3:20:58 PM)
 * Modified:      (04/11/2003           )       
 */
public static String[] getURLAuthority(String theurl,
								javax.servlet.http.HttpServletRequest request,
	                            javax.servlet.http.HttpServletResponse response) 
{

	// Define return array.
	String   profile  = SessionVariables.getSessionttiProfile(request,response);
	String[] roles    = SessionVariables.getSessionttiUserRoles(request,response);
	String[] groups   = SessionVariables.getSessionttiUserGroups(request,response);
	String[] paths    = SessionVariables.getSessionttiUserPaths(request,response);
	String[] pubPaths = SessionVariables.getSessionttiUserPubPaths(request,response);
	String[] urlInfo = new String[3];
	String goToLogOn = "no";
	urlInfo[2] = goToLogOn;
	
	
	// ------------------------------------
    UrlValidationData uvd = (UrlValidationData)urlAuths.get(theurl);
    // 
    boolean hasAccess = false;
    if (uvd!=null) { // check to see if user's role is included in the validation data for that URL.
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
		//com.treetop.data.UrlFile urlFile = new com.treetop.data.UrlFile(theurl,"no"); 01/16/09

		//if (urlFile != null)01/16/09
		//{01/16/09
			//if (urlFile.getType().trim().equals("major") ||01/16/09
				//urlFile.getType().trim().equals("minor"))01/16/09
			//{01/16/09
				//urlInfo[0] = "Authority Passed";
				//urlInfo[1] = "";
	    		//return urlInfo;
			//}01/16/09
			
        	//urlFile = com.treetop.data.UrlFile.updateAuthorityFields(urlFile,01/16/09
	        									    //roles,01/16/09
	        									    //groups,01/16/09
	        									    //paths,01/16/09
	        									    //pubPaths,01/16/09
	        									    //profile,01/16/09
	        									    //profile);01/16/09
		    //String urlFile = "N";

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
 * Initializes the servlet.
 */
public void init() 
	throws ServletException { 
	
   //***** *******************************************
   // Establish a as400 connection
   //************************************************
   try {
	   Class.forName("com.ibm.as400.access.AS400JDBCDriver");
   }

   catch (ClassNotFoundException e) {
	   System.out.println(e);
   }
   
//   Connection conn = null;
 //  try {
	//   conn = DriverManager.getConnection("jdbc:as400:10.6.100.3",
		//   			                      "DAUSER",
		  // 			                      "WEB230502");
   	   //conn = ConnectionStack.getConnection();
//   } 

 //  catch (Exception e) {
//	   System.out.println("Error retrieving connection: " + e + " - com.treetop." +
//		                  "servlets.TTISecurity.init()");
   //}

   try {
	   // Create an AS/400 object for the AS/400 system that holds the files.
	   // 10/28/11 TWalton - change to use lawson.treetop.com
	   //as400 = new AS400("10.6.100.3","DAUSER","WEB230502");
	   as400 = new AS400("lawson.treetop.com","DAUSER","WEB230502");
	   System.out.print("AS400 new TTISecurity");
   }
   catch (Exception e) {
	   System.out.println("Error defining AS/400 system object : " + e + 
		   				  " - com.treetop.servlets.TTISecurity.init()");
   }

   try {
	   // Create the connection pool class.
	   //com.treetop.ConnectionPool connectionPool = new com.treetop.ConnectionPool();
   } catch (Exception e) {
	   System.out.println("Error when creating connection pool class: " + e +
		                  " - com.treetop.servlets.TTISecurity.init()");
   }

   try {
	   // Create Foundation classes.
	   com.treetop.data.UrlFile z = new com.treetop.data.UrlFile();
	   com.treetop.data.UserFile zz = new com.treetop.data.UserFile();
   } catch (Exception e) {
	   System.out.println("Error when creating Foundation classes: " + e +
		                  " - com.treetop.servlets.TTISecurity.init()");
   }
   
   // Set initial servlet variables.
   startSchedular = "yes";
   
   //reloadUserFile = "yes";
   
   try {
	   TTISecurity.urlAuths = com.treetop.services.ServiceSecurity.getUrlAuths();
   } catch (Exception e) {
	   System.out.println("Error when loading security hashtable " + e +
       " - com.treetop.servlets.TTISecurity.init()");
   }
   

}
/**
 * Grant or deny access to custom Treetop sever applications.
 *
 * Incomming request parameters:
 *    theurl     = getSessionttiTheURL(request,response)
 *    ttiProfile = getSessionttiProfile(request,response)
 *
 * Incomming session variables:
 *
 *
 * Set session variables:
 *    ttiSecStatus:      Message to return to calling servlet or jsp.
 
 *    ttiProfile:        Current value of user signed on to the server.
 
 *    ttiUserID:         Current value of "ttiProfile" changed to uppercase.
 
 *    ttiGroupProfile:   AS400 group profile assigned to AS400 user.
 
 *    ttiUserRoles:      Roles assigned to user number (profile) from file
 *                       DPPMUSERR.
 
 *    ttiUserGroups:     Groups assigned to user number (profile) from file
 *                       DPPMUSERG.
 
 *    ttiUserPaths:      Paths assigned to user number (profile) from file
 *                       DPPMUSERP.
 
 *    ttiUserPubPaths:   Authority to allow the current user to add documents to
 *                       a specific path the user is allowed to see.
 
 *    ttiUserPubFolders: Authority to allow the current user to add folders.
 
 *    ttiURLAppType:     Type of application requested from call servlet or jsp
 *                       for example "Trade Spending".
 *  
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performTask(javax.servlet.http.HttpServletRequest request,
	                    javax.servlet.http.HttpServletResponse response)
{
	
	try
	{
		//************************************************************************
		// Get the current server related information.
		//************************************************************************
	 	String[] serverInfo = ServerInfo.getNameAndPath(request, response);
	 	
	 	String library = serverInfo[2];
	//library = "DBLIB.";
	 	
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
	        	
	        String[] returnInfo = getSessionProfile(request, response, library);
	        ttiProfile = returnInfo[0];
      
	        SessionVariables.setSessionttiProfile(request,response,returnInfo[0]);
        
	        SessionVariables.setSessionttiUserPubFolders(request,response,
		                                                 returnInfo[1]);
        }
        
		if (ttiProfile.equals("default"))
		{
			SessionVariables.setSessionttiGroupProfile(request, response, "default");
			String[] guest = new String[1];
			guest[0] = "11";
			SessionVariables.setSessionttiUserRoles(request, response, guest);
		}
		
		


        
		//************************************************************************
		// Rebuild the User Profile file.
		//************************************************************************
        
        // Production Server.
        // commented out 2/02/2009 - wth ( No longer used).
        //String theHost = request.getHeader("Host");
        
		//if (theHost.equals("treenet.treetop.com") && reloadUserFile.equals("yes"))
		//{
			//reloadUserFile = "no";
			
		   // call program to rebuild user profile file.
		   //try {// Call a RPG program.
		   		//AS400 system = new AS400("10.6.100.3","DAUSER","WEB230502");
				//ProgramCall pgm = new ProgramCall(system);
				//ProgramParameter[] parmList = new ProgramParameter[0];
							
				//Call the program.
				//pgm = new ProgramCall(system,"/QSYS.LIB/GNLIB.LIB/DPC190.PGM", parmList );
							
				//if (pgm.run() != true) {
					//System.out.println(" Error occured while rebuilding user profile" +
									   //" file USERPROFIL. Program call failed.");
				//}
					
		   //} catch (Exception e) {
			   //System.out.println("Error occured while rebuilding user profile. " + e +
				                  //" - com.treetop.servlets.TTISecurity.init()");
		   //}
		//}
		
		
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
	    // Retrieve / set session variable ttiGroupProfile. Reroute if unavailable.
	    //*************************************************************************
	    String ttiGroupProfile = SessionVariables.
	    						 getSessionttiGroupProfile(request,response);
		

		//**
		// Allow for testing here with different profiles.
		//
		//ttiGroupProfile = null;

	    
	    if (ttiGroupProfile == null || ttiGroupProfile.equals("not found"))
	    {
	    	ttiGroupProfile = "not found";
	        //ttiGroupProfile = getGroupProfile(ttiUserID); 01/07/09 wth

	        if (ttiGroupProfile == null || ttiGroupProfile.equals("not found"))
	        {
		        if (1 == 2)
		        {
		        String msg = "** LEVEL ONE SECURITY FAILURE ** " +
		        			 "The current user (" + ttiUserID + ") does not have " +
		                     "a Group Profile assignment - " +
			                 "com.treetop.servlets.TTISecurity.performTask(req,rsp)";
				SessionVariables.setSessionttiSecStatus(request,response,msg);
				return;
		        }
	    	}
	    }

	    SessionVariables.setSessionttiGroupProfile(request,response,ttiGroupProfile); 

    
	    //*************************************************************************
	    // Retrieve / set session array variable ttiUserRoles.
	    //*************************************************************************
	    String[] roleInfo = SessionVariables.
	                        getSessionttiUserRoles(request,response);

	    //**
		// Allow for testing here with different profiles.
		//
		// roleInfo = null;
	    if (roleInfo == null)
	    {
	    	roleInfo = getProfileRoles(ttiProfile, theurl, library);
	    
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
        String[] groupInfo = SessionVariables.
                                 getSessionttiUserGroups(request,response);

        //**
		// Allow for testing here with different profiles.
		//
		//groupInfo = null;

        if (groupInfo == null)
	    {
		    groupInfo = getUserGroups(ttiProfile, library);

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
	

  
        //***********************************************************************
        // Retrieve / set session variable ttiUserPaths.
        //***********************************************************************
        String[] pathInfo = SessionVariables.
                                 getSessionttiUserPaths(request,response);

        if (pathInfo != null)
        {
        	if (pathInfo[0].equals("rebuild"))
        		pathInfo = null;
        }
        
        if (pathInfo == null)	
        {
		    pathInfo = getUserPaths(ttiProfile, library);

			if (pathInfo[1].equals(""))
			{
				String emptyArray[] = new String[1];
				emptyArray[0] = "";
				SessionVariables.setSessionttiUserPaths(request,response,emptyArray);
				SessionVariables.setSessionttiUserPubPaths(request,response,emptyArray);
				SessionVariables.setSessionttiUserPathTitles(request,
					 										 response, emptyArray);
			} else
			{
				//load ttiUserPaths session variable.
				String allPaths = pathInfo[1];
				int total = 1;
			
				for(int i = 0; i < allPaths.length(); i++)
				{
					if (allPaths.substring(i,i + 1).equals(","))
					total++;
				}
				
				int count = 0;
				int i     = 0;
				int x     = 0;
				String paths[] = new String[total];
			
				while (allPaths.length() > x)
				{
					if (allPaths.substring(x,x + 1).equals(","))
					{
						paths[count] = allPaths.substring(i, x);
						i = x + 1;
						count = count + 1;
					}
				
					x = x + 1;
				}
			
				paths[count] = allPaths.substring(i, allPaths.length());
				SessionVariables.setSessionttiUserPaths(request,response,paths);

				// load ttiUserPubPaths session variable
				String allPublish = pathInfo[2];
				total = 1;
			
				for(i = 0; i < allPublish.length(); i++)
				{
					if (allPublish.substring(i,i + 1).equals(","))
					total++;
				}
				
				count = 0;
				i     = 0;
				x     = 0;
				String publish[] = new String[total];
			
				while (allPublish.length() > x)
				{
					if (allPublish.substring(x,x + 1).equals(","))
					{
						publish[count] = allPublish.substring(i, x);
						i = x + 1;
						count = count + 1;
					}
				
					x = x + 1;
				}
			
				publish[count] = allPublish.substring(i, allPublish.length());
				SessionVariables.setSessionttiUserPubPaths(request,response,publish);

				// load ttiUserPathTitles session variable.
				String[] titles = new String[paths.length];
				
				for(i = 0; i < paths.length; i++)
				{
					titles[i] = com.treetop.data.UrlFile.buildTitlePath(paths[i]);
				}
				SessionVariables.setSessionttiUserPathTitles(request,response,titles);
				
			}
	    }
        
   
	    //**************************************************************************
	    // Verify user authority to application.
	    //**************************************************************************	    
	    
	    String[] urlInfo = getURLAuthority(theurl, request, response);
	    
	    if (urlInfo[0].equals("Authority Failed"))
		{
			SessionVariables.setSessionttiSecStatus(
								request,response,urlInfo[1]);
			return;
		}
	
		if (urlInfo[0].equals("Authority Passed") &&
			urlInfo[2].equals("yes"))
		{
			SessionVariables.setSessionttiSecStatus(
								request,response,"logOn");
			return;
		}
		
		
		//*************************************************************************
	    // Test (teir two) selective application security.
	    //*************************************************************************
	    //TradeSpending
	    boolean tradeSpending = false;

	    try {

		    for (int i = 0; (i + 13) < theurl.length() || tradeSpending == false; i++)
	    	{
		    	String x = theurl.substring(i, i + 13);
		    
		    	if (x.equals("TradeSpending"))
			    	tradeSpending = true;
	    	}
	    	
		
	    	if (tradeSpending == true)
	    	{
		    	SessionVariables.setSessionttiURLAppType(request,response,
			                                         "Trade Spending");
	     		tsSecurity(request, response, ttiUserID);
	    	} else 
	    	{
		    	//Add additional application types.
	    	}
	    } catch (Exception e) {
	    }
	    
	    String ending = "X"; 

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
 * Trade Spending - Load all session variables if not already done.
 * Creation date: (10/7/2002 2:02:43 PM)
 */
 
public static void tsSecurity(javax.servlet.http.HttpServletRequest request,
	                    javax.servlet.http.HttpServletResponse response,
	                    String ttiUser)
{
	// Session variable "ttiTSStatus" is retrieved. If the value is not "yes"
	// then load all required Trade Spending session variables.
	try {		 		
		String ttiTSStatus = "";
		
		try 
		{
			ttiTSStatus = SessionVariables.getSessionttiTSStatus(request,response);

			if (!ttiTSStatus.equals("yes"))
				ttiTSStatus = "no";
		} catch(NullPointerException npe) 
		{
			ttiTSStatus = "no";
		}

//**
// Force rebuild for testing purposes
//
//ttiTSStatus = "no";

		// Load if necessary, session variables form Trade Spending Authority file.
		// If current session user is not set up then stop access and send message. 
 		if (!ttiTSStatus.equals("yes"))
	 	{ 			   
			boolean reDirectUser = false;
		    Connection conn = null;
			try {
				conn = ConnectionStack.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
					    "SELECT * FROM DBLIB.TSLEAUTH WHERE TSEUID = '" + 
			 		    ttiUser.toUpperCase() + "'");
						
			
				if (rs.next()) 
				{
					SessionVariables.setSessionttiTSHierLvl
						(request,response,rs.getBigDecimal("TSELVL"));
						
					SessionVariables.setSessionttiTSCo
						(request,response,rs.getBigDecimal("TSECMP")); 
				 	
				 
				 	if (rs.getString("TSETRD").equals("  "))
				  		SessionVariables.setSessionttiTSTrade
				  			(request, response, "*all");
				 	else
				 		SessionVariables.setSessionttiTSTrade
				  			(request, response, rs.getString("TSETRD"));
				 
				 	if (rs.getString("TSENSM").equals("  "))
				 		SessionVariables.setSessionttiTSNatlSlsMgr
				  			(request, response, "*all");
				 	else
				 		SessionVariables.setSessionttiTSNatlSlsMgr
				  			(request, response, rs.getString("TSENSM"));
				 
				 	if (rs.getString("TSERSM").equals("  "))
				  		SessionVariables.setSessionttiTSRegSlsMgr
				  			(request, response, "*all");
				 	else 
						SessionVariables.setSessionttiTSRegSlsMgr
				  			(request, response, rs.getString("TSERSM"));

				 	
					int brokerInt = Integer.parseInt(rs.getString("TSEBRK"));
					if (brokerInt == 0)
						SessionVariables.setSessionttiTSBroker
							(request, response, "*all");
					else
						SessionVariables.setSessionttiTSBroker
							(request, response, rs.getString("TSEBRK"));
						
							
					int marketInt = Integer.parseInt(rs.getString("TSEMKT"));					
					if (marketInt == 0)
						SessionVariables.setSessionttiTSMarket
							(request, response, "*all");
					else
						SessionVariables.setSessionttiTSMarket
							(request, response, rs.getString("TSEMKT"));

					
				 	if (rs.getString("TSECSR").equals("     "))
				    	SessionVariables.setSessionttiTSCustServRep
				  			(request, response, "*all");
				 	else
				 		SessionVariables.setSessionttiTSCustServRep
				 			(request, response, rs.getString("TSECSR")); 
					 	
					
				 	SessionVariables.setSessionttiTSStatus(request, response, "yes"); 
			     	
					  
		     	} else {
			     	reDirectUser = true;
			 	}
		
			} catch(SQLException sqle) {
				System.err.println("com.treetop.servlets.TTISecurity.tsSecurity" +
								   "(HttpServletRequest,HttpServletResponse,String) - " +
					               "SQL error: " + sqle);
			}
			finally
			{
				if (conn != null)
				{
					try
					{
						ConnectionStack.returnConnection(conn);
					}
					catch(Exception e)
					{
						System.out.println("*** Tried to Return Connection to the STACK ** " + e);
					}
				}
			}

		    
			if (reDirectUser == true)
			{
				String msg = " - The server UserID of " + ttiUser + " is not " + 
            				 "authorized to Trade Spending (TSPEAUTH). " +
            				 "- com.treetop.TTISecurity.tsSecurity(rq,rsp,String)";
				SessionVariables.setSessionttiSecStatus(request,response,msg);
				return;
			} 
		}	
			

		} catch(Throwable theException)
		{
			// uncomment the following line when unexpected exceptions
			// are occuring to aid in debugging the problem.
			theException.printStackTrace();
			System.err.println("com.treetop.servlets.TTISecurity.tsSecurity" +
							   "(HttpServletRequest,HttpServletResponse,String) - " +
							   "An exception occured." + theException);
		}
	return;
}

/**
 * Insert the method's description here.
 * Creation date: (1/16/2003 10:28:48 AM)
 */
public static String[] getProfileRoles(String ttiProfile, String theurl, String library) 
{
	

	String[] theReturn = new String[2];
	theReturn[0] = "";
	theReturn[1] = "";
	String whereRole = "";
	Connection conn = null;
    try
    {
    	conn = ConnectionStack.getConnection();
	    Statement stmt = conn.createStatement(); 
        ResultSet rs = stmt.executeQuery("SELECT DPNUSER, DPNUSERNBR, DPMROLENBR " +
                                       "FROM " + library + "DPPNUSER " +
                                       "INNER JOIN " + library + "DPPMUSERR " +
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
      
   		} catch (SQLException sqle)
     	{ 
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
			{
				try
				{
					ConnectionStack.returnConnection(conn);
				}
				catch(Exception e)
				{
					System.out.println("*** Tried to Return Connection to the STACK ** " + e);
				}
			}
		}
     	
		return theReturn;	
	}



/**
 * Insert the method's description here.
 * Creation date: (11/11/2002 3:20:58 PM)
 */
public static String[] getSessionProfile(javax.servlet.http.HttpServletRequest request,
	                              javax.servlet.http.HttpServletResponse response,
	                              String library) 
	                            
{
	// Determine current server.
    // Verify and default authorization value if server is local.

    String[] profileInfo = new String[2]; 
	String theurl = request.getHeader("Host");
	String auth = "";
	String allowCreateFolders = "N";
	String ttiProfile = "";



	if (theurl.contains("localhost"))
	{
	    auth = "Basic dGhhaWxlOm05dngzMg=="; // user = thaile
	    allowCreateFolders = "Y";
	}
	else
	    auth = request.getHeader("Authorization");

//System.out.println("authority value before change is = " + auth);
//auth = "Basic dGhhaWxlOm05dngzMg==";
//System.out.println("authority value after change is = " + auth);

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
	//ttiProfile = "APPLEH";        // default user
	//ttiProfile = "DBRIES";	// Debbie Briesmeister
	

	// Set the user profile to "default" if not already obtained.
	if (ttiProfile == null || ttiProfile.equals("DAUSER"))
	{
		ttiProfile = "default";
	} else {
		
		// Retrieve the user authority to add folders.
		Connection conn = null;
		try {
			conn = ConnectionStack.getConnection();
			Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery("SELECT * " +
                                         	"FROM " + library + "DPPNUSER " +
                                         	"WHERE DPNUSER = '" + 
                                         	ttiProfile.toUpperCase() + "'");
                                         
        	if (rs.next())
	       	allowCreateFolders = rs.getString("DPNFOLDER");
    
      		rs.close();
      		stmt.close();
    	} catch (Exception e)
    	{ System.err.println("Error TTISecurity.testRoleAuthority: " + e);
	   	ttiProfile = "Failed";
		}
    	finally
		{
			if (conn != null)
			{
				try
				{
					ConnectionStack.returnConnection(conn);
				}
				catch(Exception e)
				{
					System.out.println("*** Tried to Return Connection to the STACK ** " + e);
				}
			}
		}
	}

    profileInfo[0] = ttiProfile;
    profileInfo[1] = allowCreateFolders;
	return profileInfo;
		
}

/**
 * Insert the method's description here.
 * Creation date: (4/10/2003 1:45:25 PM)
 */
public static String[] getUserGroups(String ttiProfile,
	 						  String library) 
{
	String[] theReturn = new String[2];
	theReturn[0] = "";
	theReturn[1] = "";
	String groups = "";
    Connection conn = null;
	try
	{
		conn = ConnectionStack.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery
			("SELECT * " +
			 "FROM " + library + "DPPMUSERG " +
			 "INNER JOIN " + library + "DPPNUSER " +
			 "ON DPMUSERNBR = DPNUSERNBR " +
			 "WHERE DPNUSER = \'" + ttiProfile.toUpperCase() + "\'" +
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
	} catch (SQLException sqle)
	{
		System.err.println("SQL error TTISecurity.getUserGroups: " + sqle);

		String msg = "You are currently not set up (Group) to view this page. " +
		             "Please contact Information Services. " +
		             "com.treetop.servlets.TTISecurity.perfromTask(req,rsp)." +
		             "getUserGroups(" + ttiProfile + ")";
		theReturn[0] = msg;
	}
	finally
	{
		if (conn != null)
		{
			try
			{
				ConnectionStack.returnConnection(conn);
			}
			catch(Exception e)
			{
				System.out.println("*** Tried to Return Connection to the STACK ** " + e);
			}
		}
	}
	
	return theReturn;
}

/**
 * Insert the method's description here.
 * Creation date: (4/10/2003 1:45:25 PM)
 */
public static String[] getUserPaths(String ttiProfile,
							 String library) 
{

	String[] theReturn = new String[3];
	theReturn[0] = "";
	theReturn[1] = "";
	theReturn[2] = "";
	String paths = "";
	String titles = "";
	String publish = "";
    Connection conn = null;
	try
	{
		conn = ConnectionStack.getConnection();
		Statement stmt = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		
		ResultSet rs = stmt.executeQuery
			("SELECT * " +
			 "FROM " + library + "DPPMUSERP " +
			 "INNER JOIN " + library + "DPPNUSER " +
			 "ON DPMUSERNBR = DPNUSERNBR " +
			 "INNER JOIN " + library + "DPPNPATH " +
			 "ON DPMPATHNBR = DPNPATHNBR " +
			 "WHERE DPNUSER = \'" + ttiProfile.toUpperCase() + "\'" +
			 "ORDER BY DPNPATH");

		while (rs.next())
		{
			String pathValue = rs.getString("DPNPATH").trim();
			String pathPublish = rs.getString("DPMPUBLISH");
			theReturn[0] = "Authority passed";
			
			if (paths.equals(""))
			{
				paths = pathValue;
				publish = pathPublish;
			} else
			{
				paths = paths + "," + pathValue;
				publish = publish + "," + pathPublish;
			}

			theReturn[1] = paths;
			theReturn[2] = publish;
		}

		rs.close();
		
	} catch (SQLException sqle)
	{
		System.err.println("SQL error TTISecurity.getUserPaths: " + sqle);

		String msg = "You are currently not set up (Path) to view this page. " +
		             "Please contact Information Services. " +
		             "com.treetop.servlets.TTISecurity.perfromTask(req,rsp)." +
		             "getUserPaths(" + ttiProfile + ")";
		theReturn[0] = msg;
	}
	finally
	{
		if (conn != null)
		{
			try
			{
				ConnectionStack.returnConnection(conn);
			}
			catch(Exception e)
			{
				System.out.println("*** Tried to Return Connection to the STACK ** " + e);
			}
		}
	}
	
	return theReturn;
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
 * Creation date: (8/13/2004 1:45:25 PM)
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
 * Grant or deny access to custom Treetop sever applications.
 *
 * Incomming request parameters:
 *    theurl     = getSessionttiTheURL(request,response)
 *    ttiProfile = getSessionttiProfile(request,response)
 *
 * Incomming session variables:
 *
 *
 * Set session variables:
 *    ttiSecStatus:      Message to return to calling servlet or jsp.
 
 *    ttiProfile:        Current value of user signed on to the server.
 
 *    ttiUserID:         Current value of "ttiProfile" changed to uppercase.
 
 *    ttiGroupProfile:   AS400 group profile assigned to AS400 user.
 
 *    ttiUserRoles:      Roles assigned to user number (profile) from file
 *                       DPPMUSERR.
 
 *    ttiUserGroups:     Groups assigned to user number (profile) from file
 *                       DPPMUSERG.
 
 *    ttiUserPaths:      Paths assigned to user number (profile) from file
 *                       DPPMUSERP.
 
 *    ttiUserPubPaths:   Authority to allow the current user to add documents to
 *                       a specific path the user is allowed to see.
 
 *    ttiUserPubFolders: Authority to allow the current user to add folders.
 
 *    ttiURLAppType:     Type of application requested from call servlet or jsp
 *                       for example "Trade Spending".
 *  
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public static void checkSecurity(javax.servlet.http.HttpServletRequest request,
	                             javax.servlet.http.HttpServletResponse response)
{
	
	try
	{
		//************************************************************************
		// Get the current server related information.
		//************************************************************************
	 	String[] serverInfo = ServerInfo.getNameAndPath(request, response);
	 	
	 	String library = serverInfo[2];
	//library = "DBLIB.";
	 	
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
	        	
	        String[] returnInfo = getSessionProfile(request, response, library);
	        ttiProfile = returnInfo[0];
      
	        SessionVariables.setSessionttiProfile(request,response,returnInfo[0]);
        
	        SessionVariables.setSessionttiUserPubFolders(request,response,
		                                                 returnInfo[1]);
        }
        
		if (ttiProfile.equals("default"))
		{
			SessionVariables.setSessionttiGroupProfile(request, response, "default");
			String[] guest = new String[1];
			guest[0] = "11";
			SessionVariables.setSessionttiUserRoles(request, response, guest);
		}
		


        
		//************************************************************************
		// Rebuild the User Profile file.
		//************************************************************************
        
        // Production Server.
        //commented out 2/02/2009 - wth (No longer used).
        //String theHost = request.getHeader("Host");
        
		//if (theHost.equals("treenet.treetop.com") && reloadUserFile.equals("yes"))
		//{
			//reloadUserFile = "no";
			
		   // call program to rebuild user profile file.
		   //try {// Call a RPG program.
		   		//AS400 system = new AS400("10.6.100.3","DAUSER","WEB230502");
				//ProgramCall pgm = new ProgramCall(system);
				//ProgramParameter[] parmList = new ProgramParameter[0];
							
				//Call the program.
				//pgm = new ProgramCall(system,"/QSYS.LIB/GNLIB.LIB/DPC190.PGM", parmList );
							
				//if (pgm.run() != true) {
					//System.out.println(" Error occured while rebuilding user profile" +
									  // " file USERPROFIL. Program call failed.");
				//}
					
		   //} catch (Exception e) {
			   //System.out.println("Error occured while rebuilding user profile. " + e +
				                  //" - com.treetop.servlets.TTISecurity.init()");
		   //}
		//}
		
		
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
	    // Retrieve / set session variable ttiGroupProfile. Reroute if unavailable.
	    //*************************************************************************
	    String ttiGroupProfile = SessionVariables.
	    						 getSessionttiGroupProfile(request,response);
		

		//**
		// Allow for testing here with different profiles.
		//
		//ttiGroupProfile = null;

	    
	    if (ttiGroupProfile == null || ttiGroupProfile.equals("not found"))
	    {
	    	ttiGroupProfile = "not found";
	        //ttiGroupProfile = getGroupProfile(ttiUserID); 01/07/09 wth

	        if (ttiGroupProfile == null || ttiGroupProfile.equals("not found"))
	        {
		        if (1 == 2)
		        {
		        String msg = "** LEVEL ONE SECURITY FAILURE ** " +
		        			 "The current user (" + ttiUserID + ") does not have " +
		                     "a Group Profile assignment - " +
			                 "com.treetop.servlets.TTISecurity.performTask(req,rsp)";
				SessionVariables.setSessionttiSecStatus(request,response,msg);
				return;
		        }
	    	}
	    }

	    SessionVariables.setSessionttiGroupProfile(request,response,ttiGroupProfile); 

    
	    //*************************************************************************
	    // Retrieve / set session array variable ttiUserRoles.
	    //*************************************************************************
	    String[] roleInfo = SessionVariables.
	                        getSessionttiUserRoles(request,response);

	    //**
		// Allow for testing here with different profiles.
		//
		// roleInfo = null;
	    if (roleInfo == null)
	    {
	    	roleInfo = getProfileRoles(ttiProfile, theurl, library);
	    
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
        String[] groupInfo = SessionVariables.
                                 getSessionttiUserGroups(request,response);

        //**
		// Allow for testing here with different profiles.
		//
		//groupInfo = null;

        if (groupInfo == null)
	    {
		    groupInfo = getUserGroups(ttiProfile, library);

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
	

  
        //***********************************************************************
        // Retrieve / set session variable ttiUserPaths.
        //***********************************************************************
        String[] pathInfo = SessionVariables.
                                 getSessionttiUserPaths(request,response);

        if (pathInfo != null)
        {
        	if (pathInfo[0].equals("rebuild"))
        		pathInfo = null;
        }
        
        if (pathInfo == null)	
        {
		    pathInfo = getUserPaths(ttiProfile, library);

			if (pathInfo[1].equals(""))
			{
				String emptyArray[] = new String[1];
				emptyArray[0] = "";
				SessionVariables.setSessionttiUserPaths(request,response,emptyArray);
				SessionVariables.setSessionttiUserPubPaths(request,response,emptyArray);
				SessionVariables.setSessionttiUserPathTitles(request,
					 										 response, emptyArray);
			} else
			{
				//load ttiUserPaths session variable.
				String allPaths = pathInfo[1];
				int total = 1;
			
				for(int i = 0; i < allPaths.length(); i++)
				{
					if (allPaths.substring(i,i + 1).equals(","))
					total++;
				}
				
				int count = 0;
				int i     = 0;
				int x     = 0;
				String paths[] = new String[total];
			
				while (allPaths.length() > x)
				{
					if (allPaths.substring(x,x + 1).equals(","))
					{
						paths[count] = allPaths.substring(i, x);
						i = x + 1;
						count = count + 1;
					}
				
					x = x + 1;
				}
			
				paths[count] = allPaths.substring(i, allPaths.length());
				SessionVariables.setSessionttiUserPaths(request,response,paths);

				// load ttiUserPubPaths session variable
				String allPublish = pathInfo[2];
				total = 1;
			
				for(i = 0; i < allPublish.length(); i++)
				{
					if (allPublish.substring(i,i + 1).equals(","))
					total++;
				}
				
				count = 0;
				i     = 0;
				x     = 0;
				String publish[] = new String[total];
			
				while (allPublish.length() > x)
				{
					if (allPublish.substring(x,x + 1).equals(","))
					{
						publish[count] = allPublish.substring(i, x);
						i = x + 1;
						count = count + 1;
					}
				
					x = x + 1;
				}
			
				publish[count] = allPublish.substring(i, allPublish.length());
				SessionVariables.setSessionttiUserPubPaths(request,response,publish);

				// load ttiUserPathTitles session variable.
				String[] titles = new String[paths.length];
				
				for(i = 0; i < paths.length; i++)
				{
					titles[i] = com.treetop.data.UrlFile.buildTitlePath(paths[i]);
				}
				SessionVariables.setSessionttiUserPathTitles(request,response,titles);
				
			}
	    }
        
   
	    //**************************************************************************
	    // Verify user authority to application.
	    //**************************************************************************	    
	    
	    String[] urlInfo = getURLAuthority(theurl, request, response);
	    
	    if (urlInfo[0].equals("Authority Failed"))
		{
			SessionVariables.setSessionttiSecStatus(
								request,response,urlInfo[1]);
			return;
		}
	
		if (urlInfo[0].equals("Authority Passed") &&
			urlInfo[2].equals("yes"))
		{
			SessionVariables.setSessionttiSecStatus(
								request,response,"logOn");
			return;
		}
		
		
		//*************************************************************************
	    // Test (teir two) selective application security.
	    //*************************************************************************
	    //TradeSpending
	    boolean tradeSpending = false;

	    try {

		    for (int i = 0; (i + 13) < theurl.length() || tradeSpending == false; i++)
	    	{
		    	String x = theurl.substring(i, i + 13);
		    
		    	if (x.equals("TradeSpending"))
			    	tradeSpending = true;
	    	}
	    	
		
	    	if (tradeSpending == true)
	    	{
		    	SessionVariables.setSessionttiURLAppType(request,response,
			                                         "Trade Spending");
	     		tsSecurity(request, response, ttiUserID);
	    	} else 
	    	{
		    	//Add additional application types.
	    	}
	    } catch (Exception e) {
	    }
	    
	    String ending = "X"; 

	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		 theException.printStackTrace();
	
	}
	return;
}
	
	
}


