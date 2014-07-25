package com.treetop.servlets;

import com.treetop.data.*;
import com.treetop.*;
import java.util.*;
import java.math.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.treetop.services.ServiceConnection;
import com.ibm.as400.resource.*;
import com.ibm.as400.access.*;
/**
 * This Servlet will control which jsp's get thrown,
 *
 *     example,  inqSampleUsers.jsp
 *				 listSampleUsers.jsp
 *               dtlSampleUsers.jsp
 *               updSampleUsers.jsp
 *    
 * Creation date: (6/26/2003 9:57:57 AM)
 * @author:  David Eisenheim
 *
 *  PARAMETERS used (sent into) this Servlet
 *
 *    "msg"         = Message sent for display onto the page
 *    "requestType" = Valid Request Type's
 *                	  inquiry,      (selection critera)
 *					  list,         (list of parameters from selection critera)
 *                    detail,       (a sample user parameter)
 *                    insert, 
 *					  insertfinish, (data parameters to add the record)
 *                    update,       (user type and profile to change/update))
 *					  updatefinish, (data parameters for changing the record)
 *                    delete,       (user type and profile to be deleted)
 *					  deletefinish  (data parameters to delete the record)
 *                    
 *                    any other value will default to (inquiry)
 *
 *    All other parameters will be defined within the method's
 */
public class CtlSampleUsers extends javax.servlet.http.HttpServlet {
// 10/28/11 TWalton - Change to use ServiceConnection, not have it be a Servlet Variable
//		Connection conn;
// 10/28/11 TWalton = Not used anywhere, no longer needed
//		AS400 as400;

/**
 * Add/Update a record using the SampleRequestUsers class.
 *
 * Creation date: (6/26/2003 3:59:40 PM)
 */
private void addUpdateUser(javax.servlet.http.HttpServletRequest request,
  		  	               javax.servlet.http.HttpServletResponse response)  
{
	
	try
	{
	  //** Bring in Variables.
	  
	  	String[] generalInfo = (String[]) request.getAttribute("generalinfo");
		String   msg         = (String) request.getAttribute("msg");
		String   requestType = (String) request.getAttribute("requesttype");

		String   userType    = "";
		String   userProfile = "";

		if (requestType.equals("updatefinish")) {
			userType    = (String) request.getParameter("usertype");
			userProfile = (String) request.getParameter("userprofile");
			request.setAttribute("requesttype", "update");
		}
		if (requestType.equals("insertfinish")) {
			userType    = (String) request.getAttribute("nametype");
			userProfile = (String) request.getAttribute("nameprofile");
			request.setAttribute("requesttype", "insert");	
		}			 			
			
		String nameInitials        = request.getParameter("nameinitials");
		String nameDesc            = request.getParameter("namedesc");	
		String notifyShipped       = (String) request.getAttribute("emailshipped");
		String notifyFollowUp      = "N";
		String notifyFeedback      = "N";
				
        String dateArray[]         = SystemDate.getSystemDate();
        java.sql.Date currentDate  = java.sql.Date.valueOf(dateArray[7]);
 		java.sql.Time currentTime  = java.sql.Time.valueOf(dateArray[8]);
      //String currentUser         = com.treetop.SessionVariables.getSessionttiProfile(request, response);
        String currentUser         = "DEISEN";
     
	           userProfile         = userProfile.toUpperCase();  	
		       nameInitials        = nameInitials.toUpperCase();		       
		       notifyShipped       = notifyShipped.toUpperCase();
		       notifyFollowUp      = notifyFollowUp.toUpperCase();
		       notifyFeedback      = notifyFeedback.toUpperCase();
		       currentUser         = currentUser.toUpperCase();
		        	 					
		 
      //** Insert (add) user record.	

		try
		{
		   if (requestType.equals("insertfinish")) 
		   {
		   	   SampleRequestUsers sampleUser = new SampleRequestUsers();
			   sampleUser.insertSampleRequestUser(userType,
										  	      nameInitials,
											      nameDesc,
											      userProfile,
											      notifyShipped,
											      notifyFollowUp,
											      notifyFeedback,
											      currentDate,
											      currentTime,
											      currentUser);								
		   }
		}
		catch(Exception e)
		{
		   System.out.println("Error in CtlSampleUsers.insertSampleRequestUser: " + e);
	    }
		
		
	  //** Update user record.

	  	try
		{
		   if (requestType.equals("updatefinish"))  
		   {
		       SampleRequestUsers sampleUser = new SampleRequestUsers();
			   sampleUser.updateSampleRequestUser(userType,
										  	      nameInitials,
											      nameDesc,
											      userProfile,
											      notifyShipped,
											      notifyFollowUp,
											      notifyFeedback,
											      currentDate,
											      currentTime,
											      currentUser);		
		   }
		}
		catch(Exception e) 
		{
		   System.out.println("Error in CtlSampleUsers.updateSampleRequestUser: " + e);
	    }
		
		   
		return;

		
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleUsers.addupdateUser: " + e);
	}  	    	
}
/**
 * Drop down list for generic yes, no response for email options.
 *
 * Creation date: (7/21/2003 1:01:38 PM)
 */
public static String buildMailDropDown(String inOption, String inName, String inSelect)  
{
		    
	String dropDown     = "";
	String selected     = "";
	String selectOption = "";
	
	
	if (inSelect.equals("") || inSelect==null)
	    selectOption = "Select an Entry--->:";


	    
	if (inOption.trim().toUpperCase().equals("Y"))
	    selected = "' selected='selected'>";
	else
	    selected = "'>";
		   		    
	dropDown = dropDown + "<option value='" + 
	           "Y" + selected +
	   		   "Yes" + "&nbsp;";

	    
	if (inOption.trim().toUpperCase().equals("N"))
	    selected = "' selected='selected'>";
	else
	    selected = "'>";
		   		    
	dropDown = dropDown + "<option value='" +  
	           "N" + selected +
	   		   "No" + "&nbsp;";

	   		   
	if (selectOption.equals(""))
	dropDown = "<select name='" + inName.trim() + "'>" + 			  
	   		   dropDown + "</select>";  	 	
	else	 
	dropDown = "<select name='" + inName.trim() + "'>" +
			   "<option value='None' selected>" + selectOption +
	   		   dropDown + "</select>";  	 
 
	return dropDown; 
} 
	
/**
 * Get the sample user input data and build a parameter list
 * to re-load the fields for an update.
 * Creation date: (7/15/2003 2:56:40 PM)
 */
private String buildParmInput(javax.servlet.http.HttpServletRequest request,
					         javax.servlet.http.HttpServletResponse response)  
{
	String parmList = "";
	
	try
	{
	  //** Bring in variables.
	  	String[] generalInfo = (String[]) request.getAttribute("generalinfo");
		String msg           = (String) request.getAttribute("msg");
		String requestType   = (String) request.getAttribute("requesttype");

		String userType      = "";
		String userProfile   = "";
		String mailShippedDropDown = "";
		
		
 	  //** Bring in parameter data.	
 	    if (requestType.equals("update"))	{  	
		       userType       = (String) request.getParameter("usertype");
		       userProfile    = (String) request.getParameter("userprofile");
 	    }
 	    if (requestType.equals("insert"))	{  	
		       userType        = (String) request.getAttribute("nametype");
		       userProfile     = (String) request.getAttribute("nameprofile");
		       
		       Vector typeList = new Vector();
		       typeList        = GeneralInfo.findDescByFull("SRU");
		String typeDropDown    = GeneralInfo.buildDropDownFullForShort(typeList, userType,
			                                                       "nametype", "User Type");
		       request.setAttribute("typedropdown", typeDropDown);
 	    }
 	    
		String userInitials   = (String) request.getParameter("nameinitials");
		String userName       = (String) request.getParameter("namedesc");
		String notifyShipped  = (String) request.getAttribute("emailshipped");
		String notifyFollowUp = (String) request.getParameter("notifyfollowup");
		String notifyFeedback = (String) request.getParameter("notifyfeedback");
		String updateDate     = (String) request.getParameter("updatedate");
		String updateTime     = (String) request.getParameter("updatetime");
		String updateUser     = (String) request.getParameter("updateuser");

		
	  //** Build parameter list from existing input.			
		parmList = "&requesttype="    + requestType +
 				   "&usertype="       + userType +
 				   "&userprofile="    + userProfile +	
				   "&userinitials="   + userInitials +
				   "&username="       + userName +
				   "&notifyshipped="  + notifyShipped +
				   "&notifyfollowup=" + notifyFollowUp +
				   "&notifyfeedback=" + notifyFeedback +
				   "&updatedate="     + updateDate +
				   "&updatetime="     + updateTime +
				   "&updateuser="     + updateUser;	
				   
		mailShippedDropDown = buildMailDropDown(notifyShipped.trim(), "emailshipped", " ");	     
		request.setAttribute("mailshipped", mailShippedDropDown);
		
			
	}
	
	catch(Exception e) 
	{
		System.out.println("Error in CtlSampleUsers.buildParmInput: " + e);
	}
	
	return parmList; 
} 
/**
 * Get the sample user class and build a parameter list
 * to pre-load the fields for a copy or update.
 * Creation date: (6/27/2003 2:56:40 PM)
 */
private String buildParmList(javax.servlet.http.HttpServletRequest request,
					         javax.servlet.http.HttpServletResponse response)  
{
	String parmList            = "";
	String mailShippedDropDown = "";
	
	try
	{
	  //** Bring in variables.
	  	String[] generalInfo = (String[]) request.getAttribute("generalinfo");
		String msg           = (String) request.getAttribute("msg");
		String requestType   = (String) request.getAttribute("requesttype");
		
 	  //** Bring in parameter data.		
		String userType      = (String) request.getParameter("usertype");
		String userProfile   = (String) request.getParameter("userprofile");

		if (requestType.equals("insert")) {
		    userType    = "";
		    userProfile = "";
		}

		
	  //** Build class from existing record.			
		parmList = "&requesttype=" + requestType +
 				   "&usertype=" + userType +
 				   "&userprofile=" + userProfile;

		if (!requestType.equals("insert"))
		{
			
		    SampleRequestUsers userInfo = new SampleRequestUsers(userType, userProfile);
			parmList = parmList +
				   "&userinitials="    + userInfo.getUserInitials().trim() +
				   "&username="        + userInfo.getUserName().trim() +
				   "&notifyshipped="   + userInfo.getNotifyShipped().trim() +
				   "&notifyfollowup="  + userInfo.getNotifyFollowUp().trim() +
				   "&notifyfeedback="  + userInfo.getNotifyFeedback().trim() +
				   "&updatedate="      + userInfo.getUpdateDate() +
				   "&updatetime="      + userInfo.getUpdateTime() +
				   "&updateuser="      + userInfo.getUpdateUser() + 	
				   "&updateusername="  + userInfo.getUpdateUserName();
				   
		    mailShippedDropDown = buildMailDropDown(userInfo.getNotifyShipped().trim(), "emailshipped", " ");	     
		    request.setAttribute("mailshipped", mailShippedDropDown);
		}
		else
		{
			mailShippedDropDown = buildMailDropDown("N", "emailshipped", " ");	     
		    request.setAttribute("mailshipped", mailShippedDropDown);
		}
	
		
	}
	
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleUsers.pageUpd: " + e);
	}
	
	return parmList;
} 
/**
 * This method takes in information to make decisions on the number
 * of checkboxes for each user type to display on the JSP.
 *
 * Creation date: (6/16/2003 1:01:38 PM)
 * Updated to use NEW Stylesheet 6/17/08 TWalton
 */
public String buildUserBoxes(javax.servlet.http.HttpServletRequest request,
							 javax.servlet.http.HttpServletResponse response)  
{
	StringBuffer userBoxes = new StringBuffer();
	SampleRequestUsers listTypes = new SampleRequestUsers();
	ResultSet userTypes          = listTypes.findUserTypes();
	int boxCount = 0;
		
	userBoxes.append("<table class='table00' cellspacing = '0'> ");
	userBoxes.append("<tr class='tr00'> ");
	userBoxes.append("<td style='width:5%'>&nbsp;</td> ");

	try {
			while (userTypes.next())
			{
				String checkBox = userTypes.getString("GNAD20");		
				boxCount = boxCount + 1;
				String boxNumber = "box" + boxCount;
				userBoxes.append("<td class='td04140102'> ");
				userBoxes.append("<input type='checkbox' name='" + boxNumber + "'>");
				userBoxes.append("<b>" + checkBox + "</b></td>");
			}
			userTypes.close();
		}
		catch (Exception e){
			System.out.println("Exception error processing a result set " +
				               "(buildUserBoxes): " + e);
		} 
	
	userBoxes.append("<td>&nbsp;</td><td style='width:5%'>&nbsp;</td> ");
	userBoxes.append("<input type='hidden' value='" + boxCount +"'>");

	return userBoxes.toString(); 
}

	
/**
 * Delete a sample user.
 * Creation date: (7/24/2003 8:39:40 AM)
 */
private String deleteSampleUser(String inType, String inProfile)  
{
	String returnMessage = "";

	// Build sample orders by user type and profile.
	Vector sampleRequests          = new Vector();
	SampleRequestOrder sampleOrder = new SampleRequestOrder();
	
	try
	{
		sampleRequests = sampleOrder.findSamplesbyUser(inType.trim(), inProfile.trim().toUpperCase());		
	}
	catch(Exception e)
	{
		 
	}
	
	// Test for existing sample requests for this user and type.
	int vectorSize = sampleRequests.size();

	if (vectorSize == 0)
	{
		try
		{
		SampleRequestUsers sampleUser = new SampleRequestUsers(inType, inProfile); 
	    sampleUser.deleteUserByProfile(inType, inProfile);
	    }
	    catch(Exception e)
	    {		 
	    }
	}
	else
 	{
	 	returnMessage = "This user cannot be deleted. " +
		                "It is attached to the following sample request order(s): ";
		int count = 0;                

		for (int x=0; x < vectorSize; x++)
		{
	        SampleRequestOrder thisSample = (SampleRequestOrder) sampleRequests.elementAt(x);
	        
	        if (count==0)
	        returnMessage = returnMessage + thisSample.getSampleNumber();
	        else
	        returnMessage = returnMessage + ", " + thisSample.getSampleNumber();

	        count = 1;			
		} 
	}

	
return returnMessage;	 
}
/**
 * Process incoming HTTP GET requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

	performTask(request, response);

}
/**
 * Process incoming HTTP POST requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

	performTask(request, response);

}
/**
 * Returns the servlet info string.
 */
public String getServletInfo() {

	return super.getServletInfo();

}
/**
 * This method takes in information to assemble an SQL statement to
 * retrieve a vector filled with user data to display on the list JSP.

 * Creation date: (6/18/2003 1:01:38 PM)
 */
public Vector getUserList(javax.servlet.http.HttpServletRequest request, 
	                      javax.servlet.http.HttpServletResponse response)  
{
	ResultSet userTypes = SampleRequestUsers.findUserType();
	
	String sqlList      = "SELECT * FROM DBPRD.SRPGUSER ";
	String parmsQueried = "";
	Vector userList     = new Vector();
	
    int boxCount   = 0;
    int checkCount = 0;
    int sqlWhere   = 0;
    int andParm    = 0;
    
    try {
			while (userTypes.next())
			{
				boxCount = boxCount + 1;
				String boxNumber = "box" + boxCount;
				String checkBox = request.getParameter(boxNumber); 

				if (checkBox == null || checkBox.equals("null"))
				checkBox = "off";
				else
				checkBox = "on";
	
				if (checkBox.equals("on"))
				{	
				String typeCode = userTypes.getString("GNAD08");
				sqlWhere = 1;
				checkCount = checkCount + 1;
				
				   if (checkCount == 1)	{			    
			          sqlList = sqlList + " WHERE (SRGTYPE = '" + typeCode + "' ";
			          parmsQueried = parmsQueried + userTypes.getString("GNAD20").trim();
				   }
			       else {
			          sqlList = sqlList + " OR SRGTYPE = '" + typeCode + "' ";
			          parmsQueried = parmsQueried + ",  " + userTypes.getString("GNAD20").trim();
			          
			       }
    			}
			          
			} 
			userTypes.close();
			
	  	}
		
		catch (Exception e){
			System.out.println("Exception error processing a result set " +
				               "(getUserList): " + e);
		}

	if (checkCount == 0)
	    parmsQueried = parmsQueried + "All user types included.";
	else
	    parmsQueried = parmsQueried + " types included.";

	    

	String userName = request.getParameter("username"); 
	       userName = userName.toUpperCase();
	       	
	if (sqlWhere == 0)				    
	   sqlList = sqlList + " WHERE UPPER(SRGNAME) LIKE '%" + userName + "%' ";
	else
	   sqlList = sqlList + ") AND UPPER(SRGNAME) LIKE '%" + userName + "%' ";
	   
	if (!userName.equals ("")) {
	   parmsQueried = parmsQueried + "<br>" + "If name contains: " + userName;
	   andParm = 1;
	}		   


	
	String userInitials = request.getParameter("userinitials");
	       userInitials = userInitials.toUpperCase();		       
	       	
	
	sqlList = sqlList + " AND UPPER(SRGINITIAL) LIKE '%" + userInitials + "%' ";
	if (!userInitials.equals ("")) {
	   if (andParm == 1) {
	       parmsQueried = parmsQueried + "&nbsp;&nbsp;" + "and";
	       andParm = 0;
	   }
	    
	   parmsQueried = parmsQueried + "<br>" + "If initials contains: " + userInitials;
	   andParm = 1;
	}
		
		

	String userProfile = request.getParameter("userprofile");
	       userProfile = userProfile.toUpperCase();	      
	       	
	
	sqlList = sqlList + " AND UPPER(SRGPROFILE) LIKE '%" + userProfile + "%' ";
	if (!userProfile.equals ("")) {
	   if (andParm == 1) {
	       parmsQueried = parmsQueried + "&nbsp;&nbsp;" + "and";
	       andParm = 0;
	   }
	    
	   parmsQueried = parmsQueried + "<br>" + "If profile contains: " + userProfile;
	   andParm = 1;
	}
	

	request.setAttribute("parmsqueried", parmsQueried);
	

	String orderBy   = request.getParameter("orderby");
	if (orderBy == null)
        orderBy = "";
        
	String sortOrder = "";

	if (orderBy.equals("A"))
	sortOrder = " ORDER BY UPPER(SRGINITIAL), UPPER(SRGTYPE)";
	if (orderBy.equals("B"))
	sortOrder = " ORDER BY UPPER(SRGTYPE), UPPER(SRGPROFILE)";	
	if (orderBy.equals("C")) 
	sortOrder = " ORDER BY UPPER(SRGPROFILE), UPPER(SRGTYPE)";
	if (orderBy.equals("D"))
	sortOrder = " ORDER BY UPPER(SRGNAME), UPPER(SRGTYPE)";

	if (sortOrder.equals(""))
	sortOrder = " ORDER BY UPPER(SRGPROFILE), UPPER(SRGTYPE)";

	sqlList = sqlList + sortOrder;
	
// 10/28/11 TWalton - Change to use ServiceConnection 8 - instead of basic Connection information
	Connection conn = null;
	try {
		try{
		conn = ServiceConnection.getConnectionStack8();
		}catch(Exception e)
		{}
		
	    Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlList);

		try {
			while (rs.next())
			{
				
				SampleRequestUsers buildVector = new SampleRequestUsers(rs.getString("SRGTYPE"), 
																        rs.getString("SRGPROFILE"));
				userList.addElement(buildVector); 
			}
			rs.close();
			}
		
			catch (Exception e){
				System.out.println("Exception error processing a result set " +
					               "(getUserList): " + e);
			}
				
			
		}
		catch (SQLException SQLe){
			System.out.println("com.treetop.servlets.getUserList() " +
			                   "with SQL error:" + SQLe);			
		}
		finally{
			try{
				ServiceConnection.returnConnectionStack8(conn);
			}catch(Exception e)
			{}
		}
		

	return userList;  
}

	
/**
 * Initializes the servlet. 
 */
public void init() 
	throws ServletException {
	
   //************************************************
   // Establish a as400 connection
   //************************************************
   try {
	   Class.forName("com.ibm.as400.access.AS400JDBCDriver");
   }

   catch (ClassNotFoundException e) {
	   System.out.println(e);
   }
   
// 10/28/11 Twalton = Change to use ServiceConnection
//   try {
//	   conn = DriverManager.getConnection("jdbc:as400:10.6.100.3",
//		   			                      "DAUSER",
//		   			                      "WEB230502");
 //  }

//   catch (Exception e) {
//	   System.out.println("Error retrieving connection: " + e + " - com.treetop." +
//		                  "servlets.ListSampleUsers.init()");
 //  }
// 10/28/11 TWalton - no longer needed -- not used anywere -- 10.6.100.1 has not been valid for 3+ years
//   try {
	   // Create an AS/400 object for the AS/400 system that holds the files.
//	   as400 = new AS400("10.6.100.1","DAUSER","WEB230502");
//System.out.print("AS400 new CtlSampleUsers");
//   }
	
//   catch (Exception e) {
//	   System.out.println("Error defining AS/400 system object : " + e + 
//		   				  " - com.treetop.servlets.ListSampleUsers.init()");
 //  }


}
/**
 * Insert the method's description here.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
public void newMethod() {}
/**
 * Used to send information to the detail Page.
 * Creation date: (6/27/2003 9:33:40 AM)
 */
private void pageDtl(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{
	try
	{
	  //** Bring in variables.
	  	String[] generalInfo = (String[]) request.getAttribute("generalinfo");
		String msg           = (String) request.getAttribute("msg");
		String requestType   = (String) request.getAttribute("requesttype");
 
		String userType      = request.getParameter("usertype");
        String userProfile   = request.getParameter("userprofile");
		

	  //** Process delete request.
		if (requestType.equals("deletefinish"))
		{
			userType    = request.getParameter("nametype");
            userProfile = request.getParameter("nameprofile");            
			msg = deleteSampleUser(userType, userProfile);

			if (msg.equals(""))
			{
			   requestType = "deletefinal";			   		  
			   msg = "Completed Normally.";
			}
			else
			   requestType = "delete";
			   
		}

		
	  //** Set parameters and load sample user class information.   
	    if (!requestType.equals("deletefinal"))
	    {		
	        SampleRequestUsers userDetail = SampleRequestUsers.findUserByProfile(userType, userProfile);

      	    request.setAttribute("userdetail", userDetail);  	      		
		    request.setAttribute("generalinfo", generalInfo);
	    }
		
		
      //** Execute detail JSP.
        request.setAttribute("requesttype", requestType);	
		getServletConfig().getServletContext().
		getRequestDispatcher("/JSP/SampleRequest/dtlSampleUsers.jsp?" +
				             "msg=" + msg).
			                forward(request, response);			
	}
	
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleUsers.pageDtl: " + e);
	}  	   
		
}
/**
 * Used to send information to the inquiry Page.
 * Creation date: (6/26/2003 10:00:40 AM)
 */
private void pageInq(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{
	try
	{
      //** Bring in variables.
		String msg         = (String) request.getAttribute("msg");
		String requestType = (String) request.getAttribute("requesttype");

		
	  //** Set parameters.
		String userBoxes   = buildUserBoxes(request, response);
	    request.setAttribute("userboxes", userBoxes);

				
      //** Execute inquiry JSP.
		getServletConfig().getServletContext().
			getRequestDispatcher("/JSP/SampleRequest/inqSampleUsers.jsp?" +
				                 "msg=" + msg).
			                    forward(request, response);			
	}
	
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleUsers.pageInq: " + e);
	}  
		
}
/**
 * Used to send information to the list page.
 * Creation date: (6/26/2003 10:00:40 AM)
 */
private void pageLst(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{	
	try
	{
	  //** Bring in variables.
	    String[] generalInfo = (String[]) request.getAttribute("generalinfo");
	  	String msg           = (String) request.getAttribute("msg");
		String requestType   = (String) request.getAttribute("requesttype");

		
	  //** Use the information about what was queried.
		String queryInfo = "";

		String orderBy = request.getParameter("orderby");  	
        if (orderBy == null)
            orderBy = "";
        if (orderBy.equals(""))
	        orderBy = "C";  

   	    Vector userList = new Vector(); 
	    userList = getUserList(request, response);
  	
  	    request.setAttribute("listofusers", userList);   
     	request.setAttribute("generalinfo", generalInfo);
     	
				 	 
	  //** Execute list JSP.
	    getServletConfig().getServletContext().
	    getRequestDispatcher("/JSP/SampleRequest/listSampleUsers.jsp?" + 
		                     "msg=" + msg +
	    		             "&orderby=" + orderBy).
		 	                 forward(request, response);
				  
	}
	
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleUsers.pageLst: " + e);
	}
	   	    	
}
/**
 * Used to send information to the insert/update Page.
 * Creation date: (6/26/2003 2:56:40 PM)
 */
private void pageUpd(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{
	try
	{
	  //** Bring in variables.
	  	String[] generalInfo = (String[]) request.getAttribute("generalinfo");
		String msg           = (String) request.getAttribute("msg");
		String requestType   = (String) request.getAttribute("requesttype");

	  
	  //** Prepare new parameters.		
		String parmList      = "";
		String typeDropDown  = "";
				

	  //** Set parameters and check function requested.
		if (requestType.equals("insertfinish") || 
			requestType.equals("updatefinish"))
		{
		   addUpdateUser(request, response);
		   msg = "Completed Normally";
		   requestType   = (String) request.getAttribute("requesttype");
		}
		
		   parmList = buildParmList(request,response);	   
				   

	  //** Build drop down box for user types.   	    
	    if (requestType.equals("insert"))
	    {
      	   Vector typeList     = new Vector();
		          typeList     = GeneralInfo.findDescByFull("SRU");
		          typeDropDown = GeneralInfo.buildDropDownFullForShort(typeList, " ",
			                                                        "nametype", "User Type");
	    }
	  
	    request.setAttribute("typedropdown", typeDropDown);
	    	    

	  //** Execute update JSP. 
		getServletConfig().getServletContext().
		getRequestDispatcher("/JSP/SampleRequest/updSampleUsers.jsp" + 
			                 "?msg=" + msg + parmList).
		                	 forward(request, response);	
		return;
		
	
	}
	
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleUsers.pageUpd: " + e);
	}  	
		
}
/**
 * Control for Sample users servlets, (JSP's)
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 * 
 */
public void performTask(javax.servlet.http.HttpServletRequest request, 
					    javax.servlet.http.HttpServletResponse response) 
{
	try
	{
	   String requestType = request.getParameter("requesttype");
 	   if (requestType == null)
     	   requestType = "inquiry";
     	  
       String urlAddress = "/web/CtlSampleUsers?requesttype=inquiry";
       
       if (requestType.equals("list"))
       	   urlAddress = "/web/CtlSampleUsers?requesttype=list";
       		
       if (requestType.equals("detail"))
       	   urlAddress = "/web/CtlSampleUsers?requesttype=detail";
       		
       if (requestType.equals("insert") ||
	       requestType.equals("update") ||
	       requestType.equals("delete") ||
	       requestType.equals("insertfinish") ||
	       requestType.equals("updatefinish") ||
	       requestType.equals("deletefinish"))
       	   urlAddress = "/web/CtlSampleUsers?requesttype=update";
       	   
		//********************************************************************
		// Execute security servlet
		//********************************************************************
		// Allow Session Variable Access
		HttpSession sess = request.getSession(true);
		
		// Set the Status
		SessionVariables.setSessionttiSecStatus(request,response,"");
		
		// Decide which URL to use, based on Request Type.	     
		SessionVariables.setSessionttiTheURL(request, response, urlAddress);
		
		// Call the security Servlet
  	    getServletConfig().getServletContext().
        getRequestDispatcher("/TTISecurity" ).
	    include(request, response);

	    // Decision of whether or not to use the Inqiry, List or Detail
	    if (!SessionVariables.getSessionttiSecStatus(request,response).equals(""))
		{
			response.sendRedirect("TreeNetInq?msg=" + 
				SessionVariables.getSessionttiSecStatus(request,response));
			return;
		}	    
	    // Remove the Status and the Url
	    sess.removeAttribute("ttiTheURL");
	    sess.removeAttribute("ttiSecStatus");
	    //********************************************************************
	    
	    // Use this array to send to the Jsp's for anything other than a Vector.
	    // Each Jsp may use a different number of array values.
	    // Look in the page methods for details on what the values mean for that page.
	    String[] generalInfo = new String[2];
	    request.setAttribute("generalinfo", generalInfo); 
	     
        //** Always test for a message, and send it on.
  	    String msg = request.getParameter("msg");
        if (msg == null)
            msg = "";
        request.setAttribute("msg", msg);   

        
        if (requestType.equals("deletefinal"))
        requestType = "inquiry";
        request.setAttribute("requesttype", requestType);
        
	
        if (requestType.equals("inquiry"))
        	pageInq(request, response);
 
        if (requestType.equals("list")) 
            pageLst(request, response);

        if (requestType.equals("detail") ||
	        requestType.equals("delete") || 
	        requestType.equals("deletefinish"))
    	    pageDtl(request, response); 
          
        if (requestType.equals("insert") || 
	        requestType.equals("update") ||
	        requestType.equals("copy"))
        	pageUpd(request, response);              

        if (requestType.equals("insertfinish") ||  
	        requestType.equals("updatefinish"))
        	validateParameters(request, response);         	

	}
	
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		theException.printStackTrace();
	}
}
/**
 * Use to validate input (parameters). 
 *
 * Creation date: (6/30/2003 10:29:40 AM)
 */
private void validateParameters(javax.servlet.http.HttpServletRequest request,
								javax.servlet.http.HttpServletResponse response)  
{	
	try
	{
		//** Bring in variables.
	 	String msg           = (String) request.getAttribute("msg");
		String requestType   = (String) request.getAttribute("requesttype");

		String nameType      = request.getParameter("nametype");
		String nameProfile   = request.getParameter("nameprofile");
		String nameInitials  = request.getParameter("nameinitials");
		String nameDesc      = request.getParameter("namedesc");	
		String notifyShipped = request.getParameter("emailshipped");
			                   request.setAttribute("emailshipped", notifyShipped);
			                   request.setAttribute("nametype", nameType);
			                   request.setAttribute("nameprofile", nameProfile);

		msg = "";


		//** Edit input keyed by user. (Type)
		if (requestType.equals("insertfinish"))	   
		{			
		   if (nameType == null)
		       msg = "The user type is required, may not be blank.";
		   if (nameType.equals (""))
		       msg = "The user type is required, may not be blank.";
		   if (nameType.equals ("None"))
		       msg = "The user type is required, select an entry from the list.";   	    
		}

		
		//** Edit input keyed by user. (Profile)
		if (requestType.equals("insertfinish"))	   
		{
	    if (msg.equals("")) {	
			
		   if (nameProfile == null)
		       msg = "The profile is required, may not be blank.";
		   if (nameProfile.equals (""))
		       msg = "The profile is required, may not be blank.";
		    
		   if (msg.equals("") && !nameProfile.equals(nameInitials)) {
		   	  msg = "This user is not Valid in TreeNet.";
			  try
			  {
				UserFile thisUser = new UserFile(nameProfile);
				if (!thisUser.getUserName().equals(""))
				   msg = "";
			  }
			  catch(Exception e)
			  {
			  }
		   }
		}
		}
	
		 
		//** Edit insert user request. (Validate New)
		if (requestType.equals("insertfinish"))	   
		{
	    if (msg.equals("")) {
		    try {
		    SampleRequestUsers newUser = new SampleRequestUsers(nameType, nameProfile);
		    msg = "The user type and profile are already setup, use the change user info option.";
		    }
		    catch(Exception e) {
		    }
	    }
		}		
			
		
		//** Edit input keyed by user. (Initials)
		if (msg.equals("")) {
		
		   if (nameInitials == null)
		       msg = "The initials are required, may not be blank.";
		   if (nameInitials.equals (""))
		       msg = "The initials are required, may not be blank.";
		    
		   if (msg.equals("")) {
		       int length = nameInitials.length();
			   if (length < 1 || length > 3)
			       msg = "The initials are unused or too long, the maximum is 3.";
		   }
		}


		//** Edit input keyed by user. (Name)
		if (msg.equals("")) {
			
		   if (nameDesc == null)
		       msg = "The name is required, may not be blank.";
		   if (nameDesc.equals (""))
		       msg = "The name is required, may not be blank.";
		    
		   if (msg.equals("")) {
	           int length = nameDesc.length();
		  	   if (length < 1 || length > 30)
			       msg = "The name is unused or too long, the maximum is 30.";
		   }
		}
		

		//** Edit input keyed by user. (eMail Shipped)
		if (msg.equals("")) {
			
	        int length = notifyShipped.length();
			if (length < 1 || length > 1)
			    msg = "The eMail at shipping is unused or too long, the maximum is 1.";
		}
		
	    if (msg.equals("")) {	        
			if (notifyShipped == null)
			    notifyShipped = "N";
			if (notifyShipped.equals(""))
			    notifyShipped = "N";
			if (notifyShipped.equals(" "))
			    notifyShipped = "N";
			    
			request.setAttribute("emailshipped", notifyShipped);
								
			if (!notifyShipped.equals("n") && !notifyShipped.equals("N") && 
				!notifyShipped.equals("y") && !notifyShipped.equals("Y"))
			    msg = "The eMail at shipping is required to be a Y for yes, or N for no.";
		}	
			 				 

		//** Perform update or re-send screen with error.			
		if (msg.equals(""))
		  	pageUpd(request, response);
		else
		{
			if (requestType.equals("updatefinish"))	  		
			    request.setAttribute("requesttype", "update");
			if (requestType.equals("insertfinish"))	   		
			    request.setAttribute("requesttype", "insert");
			    
			String parmList = buildParmInput(request,response);				
						
			getServletConfig().getServletContext().
			getRequestDispatcher("/JSP/SampleRequest/updSampleUsers.jsp" + 
		                         "?msg=" + msg + parmList).				
			                     forward(request, response);    
		}	 				

	}
	
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleUsers.validateParameters: " + e);
	}
	   	    	
}
}
