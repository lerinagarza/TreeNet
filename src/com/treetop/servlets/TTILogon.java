package com.treetop.servlets;

import com.treetop.data.*;
import com.treetop.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;


/**
 * Controlling Servlet to decide WHICH 
 *    Logon screen to display, and verify the
 *    Parameters sent from the logon screen
 *    Then forward to the original destination
 * Creation date: (8/9/2004 1:37:27 PM)
 * Teri Walton: 
 */
public class TTILogon extends javax.servlet.http.HttpServlet {
	/**
	 * Build String Array, to decide what should be displayed on the 
	 *    JSP, One logon or a Drop Down List to Choose From.
	 *    
	 * Creation date: (8/30/2004 1:30:40 PM)
	 * Created by:    Teri Walton
	 */
	private void buildLogon(javax.servlet.http.HttpServletRequest request,
						    javax.servlet.http.HttpServletResponse response,
						    String message)  
	{
		String[] logonDisplay = new String[2];
		logonDisplay[0] = ""; //One Type To Display
		logonDisplay[1] = ""; // Drop Down List To Display (if needed)
		//----------------------------------------------------------------
		// The URL Class:
		//   This will be used to determine which sign on's should be
		//   displayed.
		//----------------------------------------------------------------
		String thisURL = SessionVariables.getSessionttiTheURL(request,response);
		UrlFile thisRecord = new UrlFile();       
		try
		{
		   thisRecord = new UrlFile(thisURL, "yes");
		}
		catch(Exception e)
		{
		}
		String testDisplay = "";
		if (thisRecord.getTitle() != null)
		{
		   Vector setTestDisplay = new Vector();
		   Vector setValue       = new Vector();
		   if (!thisRecord.getSecuritySystem1().trim().equals(""))
		   {
			  setTestDisplay.addElement(thisRecord.getSecuritySystem1());
			  setValue.addElement(thisRecord.getSecurityValue1());
		   }
		   if (!thisRecord.getSecuritySystem2().trim().equals(""))
		   {
			  setTestDisplay.addElement(thisRecord.getSecuritySystem2());
			  setValue.addElement(thisRecord.getSecurityValue2());	     
		   }
		   if (!thisRecord.getSecuritySystem3().trim().equals(""))
		   {
			  setTestDisplay.addElement(thisRecord.getSecuritySystem3());
			  setValue.addElement(thisRecord.getSecurityValue3());
		   }
		   if (!thisRecord.getSecuritySystem4().trim().equals(""))
		   {
			  setTestDisplay.addElement(thisRecord.getSecuritySystem4());
			  setValue.addElement(thisRecord.getSecurityValue4());
		   }
		   if (!thisRecord.getSecuritySystem5().trim().equals(""))
		   {
			  setTestDisplay.addElement(thisRecord.getSecuritySystem5());  
			  setValue.addElement(thisRecord.getSecurityValue1());    	           	           	            	     
		   }
		   if (setTestDisplay.size() > 0)
		   {
		   	   if(setTestDisplay.size() == 1)
			     logonDisplay[0] = (String) setTestDisplay.elementAt(0);
			   else
			   {
				   if (request.getParameter("displayType") == null)
					  logonDisplay[1] = buildDropDownList(setTestDisplay, 
			   	                                          setValue);
			   	    else
	                {
	                   logonDisplay[0] = request.getParameter("displayType");
	                }
			   }
		   }
		   
		}
	   	
	   request.setAttribute("displayInfo", logonDisplay); 	
	    	
	}
	/**
	 *  Knows what to do with ANY logon situation.
	 *    
	 *  Will Return a message, Error Message if there is a problem.
	 * Creation date: (9/14/2004 3:10:40 PM)
	 * Created by:    Teri Walton
	 */
	private String validateLogon(javax.servlet.http.HttpServletRequest request,
						         javax.servlet.http.HttpServletResponse response)  
	{
		String returnErrorMessage = "LogOn";
		
		  		
		//---------------------------------------------------
		//  Test Grower Logon (Specifically)
		//---------------------------------------------------
		  if (request.getParameter("grower") != null &&
			  request.getParameter("grower").equals("Log On"))
		  {    
			 returnErrorMessage = validateGrowerLogon(request,
										              response);
		  }
		//---------------------------------------------------
		//  Test Employee Logon (Specifically)
		//---------------------------------------------------
		if (request.getParameter("payroll") != null &&
			request.getParameter("payroll").equals("Log On"))
		{    
		   returnErrorMessage = validateEmployeeLogon(request,
										              response);
		}
		//---------------------------------------------------
		//  Test Form System Logon (Specifically)
		//     Specific Logon generated from information
		//     held in the forms system.
		//---------------------------------------------------
		if (request.getParameter("forms") != null &&
			request.getParameter("forms").equals("Log On"))
		{    
		   returnErrorMessage = validateFormsLogon(request,
										           response);
		}	
		//---------------------------------------------------
		//  Test all the Logon's 
		//   Set the first one which is valid.
		//---------------------------------------------------
		if (request.getParameter("testAll") != null)
		{  
			String thisURL = SessionVariables.getSessionttiTheURL(request,response);
			UrlFile thisRecord = new UrlFile();       
			try
			{
			   thisRecord = new UrlFile(thisURL, "yes");
			}
			catch(Exception e)
			{
			}
			String returnMessage = "";
			String loggedOn = "N";
			if (thisRecord.getSecuritySystem1().equals("FM") ||
			    thisRecord.getSecuritySystem2().equals("FM") ||
			    thisRecord.getSecuritySystem3().equals("FM") ||
			    thisRecord.getSecuritySystem4().equals("FM") ||
			    thisRecord.getSecuritySystem5().equals("FM"))
	        {
				returnMessage =  validateFormsLogon(request,
												   response);
				if (returnMessage.equals(""))
				   loggedOn = "Y";
	        }
			
			if ((thisRecord.getSecuritySystem1().equals("GR") ||
				 thisRecord.getSecuritySystem2().equals("GR") ||
				 thisRecord.getSecuritySystem3().equals("GR") ||
				 thisRecord.getSecuritySystem4().equals("GR") ||
				 thisRecord.getSecuritySystem5().equals("GR")) &&
				 loggedOn.equals("N"))
			{
				returnMessage =  validateGrowerLogon(request,
													response);
				if (returnMessage.equals(""))
				   loggedOn = "Y";
			}			

			if ((thisRecord.getSecuritySystem1().equals("PR") ||
				 thisRecord.getSecuritySystem2().equals("PR") ||
				 thisRecord.getSecuritySystem3().equals("PR") ||
				 thisRecord.getSecuritySystem4().equals("PR") ||
				 thisRecord.getSecuritySystem5().equals("PR")) &&
				 loggedOn.equals("N"))
			{
				returnMessage =  validateEmployeeLogon(request,
											   		  response);
				if (returnMessage.equals(""))
				   loggedOn = "Y";
			}
			if (loggedOn.equals("N"))
			   returnErrorMessage = "This username / password combination does not have authority to this page.  Please try again with the correct user and password.";
            else
               returnErrorMessage = "";
		}			
				
		return returnErrorMessage;
	}
		/**
		 * Build String Array, to decide what should be displayed on the 
		 *    JSP, One logon or a Drop Down List to Choose From.
		 *    
		 * Creation date: (8/30/2004 1:30:40 PM)
		 * Created by:    Teri Walton
		 */
		private String buildDropDownList(Vector setTestDisplay, 
	                                     Vector setValue)  
		{
	       String returnDropDownList = ""; 	
	       int dropDownSize = 0;
	       try
	       {
	       	 dropDownSize = setTestDisplay.size();
	       }
	       catch(Exception e)
	       {
	       }
	       if (dropDownSize > 1)
	       {
	       	   for (int x = 0; x < dropDownSize; x++)
	       	   {
	       	      returnDropDownList = returnDropDownList +
	       	                  "<option value=\"/web/TTILogon?displayType=" +
	       	                  (String) setTestDisplay.elementAt(x) +
	       	                  "\">";
	       	      if (((String) setTestDisplay.elementAt(x)).equals("GR"))
	       	          returnDropDownList = returnDropDownList +
	       	                   "Logon as a Grower";
				  if (((String) setTestDisplay.elementAt(x)).equals("PR"))
				  	  returnDropDownList = returnDropDownList +
							 "Logon as an Employee";	       	   	
				  if (((String) setTestDisplay.elementAt(x)).equals("FM"))
				  	  returnDropDownList = returnDropDownList +
						   "Logon as Other";		       	   	
	       	   }
	       	   if (!returnDropDownList.equals(""))
	       	   {
	       	   	   returnDropDownList = "<select name=\"thisOne\" onChange=\"JumpToIt2(this)\">" +
	       	   	         "<option value=\"\">Choose a Logon Option" +
	       	   	         returnDropDownList +
	       	   	         "</select>";
	       	   }
	       }
	        	
	       return returnDropDownList; 	
		}
	/**
	 *  Reset ALL logon Variables,
	 *     SessionVariables to null.
	 *    
	 * Creation date: (8/13/2004 11:08:40 AM)
	 * Created by:    Teri Walton
	 */
	private void logoff(javax.servlet.http.HttpServletRequest request,
						javax.servlet.http.HttpServletResponse response)  
	{
      try
      {		
		String thisURL = request.getParameter("logoffURL");
		UrlFile thisRecord = new UrlFile();       
		try
		{
		   thisRecord = new UrlFile(thisURL, "yes");
		}
		catch(Exception e)
		{
		}
		if (thisRecord.getTitle() != null)
		{
			//------------------------------------------------------
			//  Remove Session Variables related to Payroll
			//------------------------------------------------------
			if (thisRecord.getSecuritySystem1().equals("PR") ||
			    thisRecord.getSecuritySystem2().equals("PR") ||
			    thisRecord.getSecuritySystem3().equals("PR") ||
				thisRecord.getSecuritySystem4().equals("PR") ||
				thisRecord.getSecuritySystem5().equals("PR"))
			{
//				String chartAndWhere = "Q:/Image/Charts/" + 
//										SessionVariables.getPayrollNumber(request, response) +
//									   "SilentPaycheck.jpg";
//				SessionVariables.setPayrollNumber(request, response, null);
//				SessionVariables.destroyPayrollNumber(request, response);
//				
//				File thisOne = new File(chartAndWhere);
//				thisOne.delete();
				
			}
			//------------------------------------------------------
			//  Remove Session Variables related to Grower
			//------------------------------------------------------
			if (thisRecord.getSecuritySystem1().equals("GR") ||
				thisRecord.getSecuritySystem2().equals("GR") ||
				thisRecord.getSecuritySystem3().equals("GR") ||
				thisRecord.getSecuritySystem4().equals("GR") ||
				thisRecord.getSecuritySystem5().equals("GR"))
			{
				SessionVariables.setGrowerSeq(request, response, null);
				SessionVariables.destroyGrowerSeq(request,response);
				SessionVariables.setIsFieldRep(request, response, null);
				SessionVariables.destroyIsFieldRep(request,response);
			}
				
			//------------------------------------------------------
			//  Remove Session Variables related to the Forms System
			//------------------------------------------------------
			Vector testSecurity = new Vector();
			if (thisRecord.getSecuritySystem1().equals("FM"))
				testSecurity.addElement(thisRecord.getSecurityValue1());
			if (thisRecord.getSecuritySystem2().equals("FM"))
				testSecurity.addElement(thisRecord.getSecurityValue2());
			if (thisRecord.getSecuritySystem3().equals("FM"))
				testSecurity.addElement(thisRecord.getSecurityValue3());
			if (thisRecord.getSecuritySystem4().equals("FM"))
				testSecurity.addElement(thisRecord.getSecurityValue4());
			if (thisRecord.getSecuritySystem5().equals("FM"))
				testSecurity.addElement(thisRecord.getSecurityValue5());
				
			if (testSecurity.size() > 0)
			{	  
				for (int x = 0; x < testSecurity.size(); x++)
				{
					try
					{
						SessionVariables.destroySessionValue(request, response, ("FM" + ((String) testSecurity.elementAt(x)).trim()));
					}
					catch(Exception e)
					{
						System.out.println("Exception " + e);
					}
				}
			}  
		} 			
      }
      catch(Exception e)
      {
      	  System.out.println("Exception caught in TTILogon.logoff: " + e);
      }
		
	}
	/**
	 *  Retrieve two parameters from the logon.
	 *     sequence Number &
	 *     Pin Number.
	 *  Decide if these parameters are good.
	 *     They must match.
	 *    
	 *  Will Return a message, Error Message if there is a problem.
	 * Creation date: (8/10/2004 9:25:40 AM)
	 * Created by:     Teri Walton
	 */
	private String validateGrowerLogon(javax.servlet.http.HttpServletRequest request,
						               javax.servlet.http.HttpServletResponse response)  
	{
		// Allow Session Variable Access
		HttpSession sess = request.getSession(true);
		
		String returnErrorMessage = "";
		Integer growerSeq = new Integer("0");
		try
		{
			growerSeq = new Integer(request.getParameter("field1"));
		}
		catch(Exception e)
		{
			returnErrorMessage = returnErrorMessage +
	                             "The Sequence Number input (" +
	                             request.getParameter("seq") + 
	                             ") is not a number, Please try again.\n";			
		}
		String  growerPin = request.getParameter("field2");
		if (growerPin == null ||
		    growerPin.equals(""))
		   returnErrorMessage = returnErrorMessage +
							 " A Pin Number is required, Please make sure" +
							 " to input both a sequence number and a pin number";
	
		if (returnErrorMessage.equals(""))
		{		
		try
		{
//			Grower growerClass = new Grower(growerSeq);
			// IF field rep......
			if (growerPin.equals("999999"))
			{
				SessionVariables.setIsFieldRep(request, response, "Y");
				SessionVariables.setGrowerSeq(request,response, growerSeq.toString());					
			}
			else
			{
//			   if (growerClass.getPinNumber() != null &&
//			       growerClass.getPinNumber().equals(growerPin.toLowerCase()))
//			   	  SessionVariables.setGrowerSeq(request,response, growerSeq.toString());		
//			   else
//			      returnErrorMessage = "The pin number input for Grower Sequence (" + growerSeq +
//			                           ") is not correct.  Please try again.";   
			}
			    	
	   	  }
		  catch(Exception e)
		  {
		 	returnErrorMessage = "The Grower Sequence (" + growerSeq +
		 	                     ") cannot be found. " +
		 		                 "  Please Try again.";
		  }
		}
			
		return returnErrorMessage;
	}
	/**
	 *  Retrieve two parameters from the logon.
	 *     employeeNumber &
	 *     Last 4 of the SSN.
	 *  Decide if these parameters are good.
	 *     They must match.
	 *    
	 *  Will Return a message, Error Message if there is a problem.
	 * Creation date: (8/10/2004 9:25:40 AM)
	 * Created by:    Teri Walton
	 */
	private String validateEmployeeLogon(javax.servlet.http.HttpServletRequest request,
						               javax.servlet.http.HttpServletResponse response)  
	{
		String returnErrorMessage = "";
		
		String ssn3           = request.getParameter("field1");
		String employeeNumber = request.getParameter("field2");
		
		//Test the SSN3 and the Payroll number (make sure they are Integers)
	    try
	    {
		        Integer SSN3Test = new Integer(request.getParameter("field1"));
	    }
	    catch(Exception e)
	    {
	        returnErrorMessage = "The value for Last 4 digits of your SSN# is " +
	                  "not a number.  Please try another value. \n";
	    }		
	    try
	    {
		    Integer employeeNumberTest = new Integer(request.getParameter("field2"));
				
	    }
	    catch(Exception e)
	    {
		     returnErrorMessage = returnErrorMessage +
		     		  "The value for Payroll Number is " +
	   	              "not a number.  Please try another value. \n";
	    }
	    // if no messages validate the employee
		if (returnErrorMessage.equals(""))
		{
			// Payroll no longer needed
	 	    //if (!PayrollMaster.validateEmployee(employeeNumber, 
			 //    	                               ssn3))
		    //{
			 //      returnErrorMessage = "The last 4 digits of the SSN# do not " +
	   	      //                    "match the Payroll Number.  Please try again. \n";	            
//	 	    }
		}
		if (returnErrorMessage.equals(""))
		{
			SessionVariables.setPayrollNumber(request,response, employeeNumber.toString());		
		}			
		return returnErrorMessage;
	}
	/**
	 *  Retrieve two parameters from the logon.
	 *     field1 &
	 *     field2.
	 *  
	 *  Decide if these parameters are good.
	 *     They must match, in the Forms System to the 
	 *     form for that URL
	 *    
	 *  Will Return a message, Error Message if there is a problem.
	 * Creation date: (8/27/2004 4:08:40 PM)
	 * Created by:     Teri Walton
	 */
	private String validateFormsLogon(javax.servlet.http.HttpServletRequest request,
						              javax.servlet.http.HttpServletResponse response)  
	{
		// Allow Session Variable Access
		HttpSession sess = request.getSession(true);
		
		String returnErrorMessage = "";
		
		String field1 = request.getParameter("field1");
		if (field1 == null ||
		    field1.equals(""))
		   returnErrorMessage = " Please make sure each field has an entry.";    
		
		String field2 = request.getParameter("field2");
		if (field2 == null ||
			field2.equals(""))
		   returnErrorMessage = " Please make sure each field has an entry.";    		
	if (returnErrorMessage.equals(""))
	{
		String thisURL = SessionVariables.getSessionttiTheURL(request,response);
		UrlFile thisRecord = new UrlFile();       
		try
		{
		   thisRecord = new UrlFile(thisURL, "yes");
		}
		catch(Exception e)
		{
		}
		if (thisRecord.getTitle() != null)
		{
		   Vector testSecurity = new Vector();
	   	   String test = thisRecord.getSecuritySystem1();
		   if (test.equals("FM"))
		      testSecurity.addElement(thisRecord.getSecurityValue1());
		   test = thisRecord.getSecuritySystem2();
		   if (test.equals("FM"))
			  testSecurity.addElement(thisRecord.getSecurityValue2());
		   test = thisRecord.getSecuritySystem3();
		   if (test.equals("FM"))
			  testSecurity.addElement(thisRecord.getSecurityValue3());
		   test = thisRecord.getSecuritySystem4();
		   if (test.equals("FM"))
			  testSecurity.addElement(thisRecord.getSecurityValue4());
		   test = thisRecord.getSecuritySystem5();
		   if (test.equals("FM"))
			  testSecurity.addElement(thisRecord.getSecurityValue5());
		   if (testSecurity.size() > 0)
		   {	  
		      for (int x = 0; x < testSecurity.size(); x++)
		      {
		      	try
		      	{
		      		Integer formNumber   = new Integer(((String) testSecurity.elementAt(x)).trim());
		      		String[] errorMessage = FormData.findDataByLogon(formNumber, 
											field1, 
											field2); 
		      	    if (errorMessage[1].equals("yes") ||
		      	        errorMessage[1].equals("ok"))
		      	    {
		      	    	// Set Security Logon Session Variable
		      	    	String sessionValue = "FM" + formNumber;
		      	    	SessionVariables.setSessionValue(request,
		      	    	                                 response,
		      	    	                                 sessionValue,
		      	    	                                 field1);
		      	    	x = testSecurity.size();
		      	    }	
		      	    else
		      	       returnErrorMessage = errorMessage[0];  
		      	}
		      	catch(Exception e)
		      	{
		      		System.out.println("Exception " + e);
		      	}
		      }
		   }  
		} 
	}
			
		return returnErrorMessage;
	}


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
 * Returns the servlet info string.
 */
public String getServletInfo() {

	return super.getServletInfo();

}
/**
 * Initializes the servlet.
 */
public void init() 
	throws ServletException { 
	
 
}
/**
 * Grant or deny access to custom Treetop sever applications.
 *
 * Incomming request parameters:
 *    theurlclass    = request variable. 
 *    ttidestination = the destination which was put into the session variable.
 *
 * Incomming session variables:
 *    
 *
 *
 * Set session variables:
 *    
 *    ttiSpecific Security variables.
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
		String requestType = request.getParameter("requestType");
		if(requestType == null)
		   requestType = "";
		String message = "LogOn"; //if problem found
		String sendTo  = "/JSP/TreeNet/logon.jsp?";
       
		//---------------------------------------------------
		//  Test Log OFF
		//---------------------------------------------------
		  if (request.getParameter("logoff") != null)
		  {
		     if (requestType.equals("logoff"))
		     {  
		     	message = ""; 
			    logoff(request,
			           response);
		     }
		     else
		     {
		     	request.setAttribute("logoffURL", (String) request.getParameter("logoffURL"));
				if (request.getParameter("destination") != null &&
					(request.getParameter("destination").equals("annualReport2004") ||
					 request.getParameter("destination").equals("annualReport2004Financial")))
					 sendTo = "/JSP/Document/AnnualReport/2004/logoff.jsp?";
               else 
               {
					if (request.getParameter("destination") != null &&
					   (request.getParameter("destination").equals("annualReport2005") ||
						 request.getParameter("destination").equals("annualReport2005Financial")))
					    sendTo = "/JSP/Document/AnnualReport/2005/logoff.jsp?";
					else
					{	
						if (request.getParameter("destination") != null &&
							(request.getParameter("destination").equals("annualReport2006") ||
							 request.getParameter("destination").equals("annualReport2006Financial")))
							 sendTo = "/JSP/Document/AnnualReport/2006/logoff.jsp?";
						else
						{
							if (request.getParameter("destination") != null &&
								(request.getParameter("destination").equals("annualReport2007") ||
								 request.getParameter("destination").equals("annualReport2007Financial")))
								 sendTo = "/JSP/Document/AnnualReport/2007/logoff.jsp?";
							else
							{	
								if (request.getParameter("destination") != null &&
									(request.getParameter("destination").equals("annualReport2008") ||
									request.getParameter("destination").equals("annualReport2008Financial")))
								    sendTo = "/JSP/Document/AnnualReport/2008/logoff.jsp?";
								else
						            sendTo  = "/JSP/TreeNet/logoff.jsp?";
						   }
						}    
					}  

                }    
		     }
		  }
		  		
		if (requestType.equals("logoff"))
		{
			response.sendRedirect("/web/JSP/TreeNet/closeWindow.jsp");	
			return;
		}		     	 
		//---------------------------------------------------
		//  Validate Logon.....
		//---------------------------------------------------
		if (request.getParameter("logoff") == null)
		     message = validateLogon(request,
		                             response);
		//----------------------------------------------------------------
		// The Destination:
		//   This is the original destination of the page.
		//----------------------------------------------------------------
		// IF message is blank
		if (message.equals(""))
		{
				response.sendRedirect(SessionVariables.getDestinationUrl(request, response));
				return;
		}
        else
        {   
			if (request.getParameter("destination") != null &&
			    (request.getParameter("destination").equals("annualReport2004") ||
			     request.getParameter("destination").equals("annualReport2004Financial"))&&
			    sendTo.equals("/JSP/TreeNet/logon.jsp?"))
			    sendTo = "/JSP/Document/AnnualReport/2004/logon.jsp?";
			if (request.getParameter("destination") != null &&
				(request.getParameter("destination").equals("annualReport2005") ||
				 request.getParameter("destination").equals("annualReport2005Financial"))&&
				sendTo.equals("/JSP/TreeNet/logon.jsp?"))
				sendTo = "/JSP/Document/AnnualReport/2005/logon.jsp?";
			if (request.getParameter("destination") != null &&
				(request.getParameter("destination").equals("annualReport2006") ||
				 request.getParameter("destination").equals("annualReport2006Financial"))&&
				sendTo.equals("/JSP/TreeNet/logon.jsp?"))
				sendTo = "/JSP/Document/AnnualReport/2006/logon.jsp?";
			if (request.getParameter("destination") != null &&
				(request.getParameter("destination").equals("annualReport2007") ||
				 request.getParameter("destination").equals("annualReport2007Financial"))&&
				sendTo.equals("/JSP/TreeNet/logon.jsp?"))
					sendTo = "/JSP/Document/AnnualReport/2007/logon.jsp?";
			if (request.getParameter("destination") != null &&
				(request.getParameter("destination").equals("annualReport2008") ||
				 request.getParameter("destination").equals("annualReport2008Financial"))&&
				sendTo.equals("/JSP/TreeNet/logon.jsp?"))
					sendTo = "/JSP/Document/AnnualReport/2008/logon.jsp?";
			
			if (sendTo.equals("/JSP/TreeNet/logon.jsp?"))
			  buildLogon(request, response, message);
        	 
        	if (message.equals("LogOn"))
        	   message = "";
        	   
		  getServletConfig().getServletContext().
		  	  getRequestDispatcher(sendTo +
			  	"msg=" + message).
			  forward(request, response);
        }               
                
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		 theException.printStackTrace();
	
	}
}
}
