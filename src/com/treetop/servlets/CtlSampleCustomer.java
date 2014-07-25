package com.treetop.servlets;

import com.treetop.data.*;
import com.treetop.*;
import java.util.*;
import java.math.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
/**
 * This Servlet will control which jsp's get thrown,
 *     example,  inqCustomer.jsp
 *				 listCustomer.jsp
 *               dtlCustomer.jsp
 *               updCustomer.jsp
 *    
 * Creation date: (6/27/2003 3:16:57 PM)
 * @author:  Teri Walton
 *
 *  PARAMETERS used (sent into) this Servlet
 *    "msg"         = Message sent for display onto the page
 *    "requestType" = Valid Request Type's
 *                	  inq, 
 *					  list, (List of Parameters to narrow the list displayed)
 *                    detail, (A Parameter(Formula Number))
 *                    add, 
 *					  addfinish, (List of parameters to add the record)
 *                    copy, (A Paramter(Customer Number to be copied from))
 *                    update, (A Parameter(Customer Info you wish to change/update))
 *					  updatefinish, (List of Parameters for changing the record)
 *                    delete, (A Parameter(Customer Number to be Deleted))
 *					  deletefinish (A Parameter(Customer Number to be Deleted))
 *        default will be inq.
 *    All other parameters will be defined within the method's
 */
public class CtlSampleCustomer extends javax.servlet.http.HttpServlet {

/**
 * Add a Formula using the SampleRequestCustomer Class.
 * Creation date: (7/2/2003 10:39:40 AM)
 */
private String addUpdateCustomer(javax.servlet.http.HttpServletRequest request,
					 			 javax.servlet.http.HttpServletResponse response,
					 			 SampleRequestCustomer whichCust,
								 String requestType)  
{
	String returnMessage = "";
	try
	{
		// Get the current System Date/Time/User, put it into the Class
        String dateArray[]         = SystemDate.getSystemDate();
        java.sql.Date currentDate  = java.sql.Date.valueOf(dateArray[7]);
 		java.sql.Time currentTime  = java.sql.Time.valueOf(dateArray[8]);
     //Uncomment section after putting security in.
 	 	String currentUser         = com.treetop.SessionVariables.getSessionttiProfile(request, response);
// 	 	String currentUser         = "TWALTO";

 	 	whichCust.setUpdateDate(currentDate);
 	 	whichCust.setUpdateTime(currentTime);
 	 	whichCust.setUpdateUser(currentUser);

		if (requestType.equals("addFinish"))
		{
			try
			{
			     SampleRequestCustomer.addToSampleRequestCustomer(
									  whichCust);
			}
			catch (Exception e)
			{
				System.out.println("CtlSampleCustomer.addUpdateCustomer(). " +
					"There was an error when adding this customer. " +
					e);
				returnMessage = "There was an error when adding this customer. ";
			}	
		}

		if (requestType.equals("updateFinish"))
		{
			try
			{
				SampleRequestCustomer.updateSampleRequestCustomer(
									  whichCust);	
			}
			catch (Exception e)
			{
				System.out.println("CtlSampleCustomer.addUpdateCustomer(). " +
					"There was an error when updating this customer. " +
					e);
				returnMessage = "There was an error when updating this customer. ";
			}		
		}
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleCustomer.addUpdateCustomer: " + e);
		returnMessage = "Error on " + requestType + " // " + e;
	}  	 
	return returnMessage;	
}
/**
 * Delete a Customer by using the SampleRequestCustomer Class.
 * Creation date: (7/18/2003 8:39:40 AM)
 */
private String deleteCustomer(String whichCust)  
{
	String returnMessage = "";
	Integer testedCust = new Integer(0);
	int     testedCustNumber = 0;
	
	try
	{
		testedCust       = new Integer(whichCust);
		testedCustNumber = Integer.parseInt(whichCust);
	}
	catch(NumberFormatException nfe)
	{
		//System.out.println("Error in CtlSampleCustomer.validateParameters. " +
		//				   "Customer Number is not a number." +
		//				   nfe);
		returnMessage = "There is a problem with the Customer Number, " + 
		  "the number chosen is not valid.";
		return returnMessage;
	}
	// Test to see if there are Samples Against this Customer
	Vector samples = new Vector();
	SampleRequestOrder orderClass = new SampleRequestOrder();
	try
	{
		samples = orderClass.findSamplesbyCustomer(testedCust);		
	}
	catch(Exception e)
	{
	}

	int vectorSize = samples.size();

	if (vectorSize == 0)
	{
		SampleRequestCustomer deleteClass = new SampleRequestCustomer();
	    deleteClass.deleteByCustNumber(testedCustNumber);
	}
	else
 	{
	 	returnMessage = "This Customer cannot be deleted. " +
		                "It is attached to Sample Request Numbers: ";

		for (int x=0; x < vectorSize; x++)
		{
	        SampleRequestOrder thisone = (SampleRequestOrder) samples.elementAt(x);
	        returnMessage = returnMessage + thisone.getSampleNumber() + ", ";
			
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
 * Initializes the servlet.
 */
public void init() {
	// insert code to initialize the servlet here

}
/**
 * Pre-Load the class  // Copy // Update // addCust(Preload with Customer Info)
 *    Put in a new Customer Number and Blanks or 0's in the fields.
 *    Used to pre-load the fields for a Copy or Update.
 *    Used to pre-load fields when if a customer is chosen.
 *    Called from the Upd Method
 * Creation date: (7/17/2003 11:18:40 PM)
 */
private void loadClass(javax.servlet.http.HttpServletRequest request,
					   javax.servlet.http.HttpServletResponse response)  
{
	try
	{
	  //** Bring in Variables.
		String msg = (String) request.getAttribute("msg");
		String requestType = (String) request.getAttribute("requestType");
 		SampleRequestCustomer whichCust = (SampleRequestCustomer) request.getAttribute("whichCust");
		String custNumber = request.getParameter("custNumber");

		if (requestType.equals("copy"))
		{
		//Will Need to call a program to get a Customer Number.
			Integer customerNumber = new Integer(custNumber);
	 		try
	 		{
		 		// Load the class if the Customer Number is Already in the File
		 		 whichCust = new SampleRequestCustomer(customerNumber);
   			}
	 		catch(InstantiationException Inste)
	 		{
		 		System.out.println("Problem in CltSampleCustomer.loadClass with the " +
			 			"Instantiation of the class with the old customer number." + Inste);
 			}		
			int nextCustomer = SampleRequestCustomer.nextSampleRequestCustomerNumber();
			whichCust.setCustNumber(nextCustomer);
		}
		else
		// Adding Customer (If customer Number already chosen, change to Update Status
		//  Updating a Customer
		{
			int nextCustomer = 0;
			if (custNumber == null ||
				custNumber.equals("0") ||
				custNumber.length() == 0)
			{
				//Will Need to call a program to get a Customer Number.
				nextCustomer = SampleRequestCustomer.nextSampleRequestCustomerNumber();
			}
			else
 			{
	 			Integer customerNumber = new Integer(custNumber);
	 			try
	 			{
		 			// Load the class if the Customer Number is Already in the File
		 			 whichCust = new SampleRequestCustomer(customerNumber);
                    // If it IS there change the request type to Update if not already update.
		 			 if (requestType.equals("addcust"))
		 			    requestType = "update";
		 		}
	 			catch(InstantiationException Inste)
	 			{
		 			// If it is NOT there.
		 			if (requestType.equals("addcust"))
					{
						try
	 					{
		 					// load the class with Customer Information from ARCLU file, if there.
							whichCust = new SampleRequestCustomer(request.getParameter("company"), custNumber, "infinium");
						}
	 					catch(InstantiationException e)
	 					{
		 					// if that customer is not in the ARCLU File Load Field with Blanks
		 					nextCustomer = SampleRequestCustomer.nextSampleRequestCustomerNumber();
	 						msg = "The number " + custNumber + 
	 						" is not a current customer Number.";	
	 					}
					}
	 			}
			}
 			if (nextCustomer != 0 && !requestType.equals("add"))
 			{
				whichCust.setCustNumber(nextCustomer);
		 	}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("requestType", requestType);
 		request.setAttribute("whichCust", whichCust);
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleCustomer.loadClass: " + e);
	}
	 
}
/**
 * Get the class back (based on ONE Customer) to build a parameter list
 *    Used to pre-load the fields for a Copy or Update.
 *    Used to pre-load fields when if a customer is chosen.
 * Creation date: (7/2/2003 2:21:40 PM)
 */
private void loadGeneralInfoArray(javax.servlet.http.HttpServletRequest request,
					 			  javax.servlet.http.HttpServletResponse response)  
{
	try
	{
		String[] generalInfo            = (String[]) request.getAttribute("generalInfo");
		String requestType              = (String) request.getAttribute("requestType");
 		SampleRequestCustomer whichCust = (SampleRequestCustomer) request.getAttribute("whichCust");

	//********************************
	//  Get Drop Down List of States
		Vector stateList      = new Vector();
		stateList             = GeneralInfo.findDescByFull("STP");
		String dropDownStates = "";
		if (requestType.equals("add") ||
			requestType.equals("addcust") || 
			requestType.equals("copy") ||
			requestType.equals("update"))
           dropDownStates = GeneralInfo.buildDropDownFullForKey1(stateList, whichCust.getState(), "state", "State");

		if (requestType.equals("inq"))
		{
			String useState = request.getParameter("state");
			if (useState.equals("None"))
			   useState = "";
           dropDownStates = GeneralInfo.buildDropDownFullForKey1(stateList, useState, "state", "State");
		}
                  
		generalInfo[0]        = dropDownStates;
		//****************************
		
			// Will be able to change this to dynamically built later.
		generalInfo[1] = "<select name='company'>" +
	 		    		 "<option value='025' selected>Ingredient Division" +
						 "</select>";  	 
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleCustomer.loadGeneralInfoArray: " + e);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
public void newMethod() {}
/**
 * Use to send information to the detail Page.
 * Creation date: (6/20/2003 9:33:40 AM)
 */
private void pageDtl(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{
	try
	{
	  //** Bring in Variables.
	  	String[] generalInfo = (String[]) request.getAttribute("generalInfo");
		String msg = (String) request.getAttribute("msg");
		String requestType = (String) request.getAttribute("requestType");

		if (requestType.equals("deleteFinish"))
		{
			msg = deleteCustomer(request.getParameter("custNumber"));

			if (msg.equals(""))
			{
			   requestType = null;
			   msg = "Completed Normally.";
			}
		}
		SampleRequestCustomer chosenCustomer = new SampleRequestCustomer();
        Integer customerNumber = new Integer(request.getParameter("custNumber"));
        try{
		chosenCustomer = new SampleRequestCustomer(customerNumber);
		UserFile userFile = new UserFile(chosenCustomer.getUpdateUser());
		Integer x = new Integer(userFile.getUserNumber());
		UserFile userFil2 = new UserFile(x);
		chosenCustomer.setUpdateUserLong(userFil2.getUserNameLong()); 
        }
        catch(Exception e)
        {}
		
	    request.setAttribute("requestType", requestType);  
		request.setAttribute("customerDetail", chosenCustomer);
		request.setAttribute("generalInfo", generalInfo);		
		//***** Go to the Dtl JSP *****//
		getServletConfig().getServletContext().
			getRequestDispatcher("/JSP/SampleRequest/dtlCustomer.jsp?" +
				"msg=" + msg).
			forward(request, response);
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleCustomer.pageDtl: " + e);
	}  	    	
}
/**
 * Use to send information to the inquiry Page.
 * Creation date: (6/27/2003 3:33:40 PM)
 */
private void pageInq(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{
	try
	{
		// use GeneralInfo[0] for Drop down list of States.
	    String[] generalInfo = (String[]) request.getAttribute("generalInfo");
		String msg = (String) request.getAttribute("msg");
		String requestType = (String) request.getAttribute("requestType");

		// Get drop down list of states.
		Vector stateList      = new Vector();
		stateList             = GeneralInfo.findDescByFull("STP");
		String dropDownStates = GeneralInfo.buildDropDownFullForKey1(stateList, "", "state", "*all");
		generalInfo[0]        = dropDownStates;

		request.setAttribute("generalInfo", generalInfo);
		
		//***** Go to the Inq JSP *****//
		getServletConfig().getServletContext().
			getRequestDispatcher("/JSP/SampleRequest/inqCustomer.jsp?" +
				"msg=" + msg).
			forward(request, response);
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleCustomer.pageInq: " + e);
	}  	    	
}
/**
 * Use to send information to the List Jsp.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
private void pageList(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{	
	try
	{
	  //** Bring in Variables.
	    String[] generalInfo = (String[]) request.getAttribute("generalInfo");
	  	String msg = (String) request.getAttribute("msg");
		String requestType = (String) request.getAttribute("requestType");

		//*** Use for information about what was queried.
		String queryInfo = "";
 
        Vector ListCustomer = new Vector(); // To be put in a Session Variable to be
	                                       //  accessed in the JSP. 

		Integer custNumber  = (Integer) request.getAttribute("custNumber");
		SampleRequestCustomer chosenCustomer = new SampleRequestCustomer();
		
		if (custNumber != null)
		{
			try
			{
				chosenCustomer = new SampleRequestCustomer(custNumber);
				ListCustomer.addElement(chosenCustomer);
			}
			catch(Exception e)
			{
			    System.out.println("Error in CtlSampleCustomer.pageList when trying to retrieve Customer: " + e);	
			}
			
		}
		else
		{
			Integer fromCustNumber = (Integer) request.getAttribute("fromCustNumber");
			 int fromC = fromCustNumber.intValue();
			 if (fromC != 0)
			    queryInfo = queryInfo + "From Customer Number: <i>" + request.getParameter("fromCustNumber") + "</i> ";
			    
			Integer toCustNumber   = (Integer) request.getAttribute("toCustNumber");
			 int toC = toCustNumber.intValue();
			 if (toC != 999999999)
			    queryInfo = queryInfo + "To Customer Number: <i>" + request.getParameter("toCustNumber") + "</i> ";
			   
			String  name           = request.getParameter("name");
			 if (name != null && name.length() != 0)
			   queryInfo = queryInfo + "Customer Name: <i>" + name + "</i> ";
			   
			String  city           = request.getParameter("city");
			 if (city != null && city.length() != 0)
			   queryInfo = queryInfo + "City: <i>" + city + "</i> ";
			   
			String  state          = request.getParameter("state");
			 if (state != null && state.length() != 0 && !state.equals("None"))
			   queryInfo = queryInfo + "State: <i>" + state + "</i> ";
			   
			String  country        = request.getParameter("country");
			 if (country != null && country.length() != 0)
			   queryInfo = queryInfo + "Country: <i>" + country + "</i> ";
			   
			String  contactName    = request.getParameter("contactName");
			 if (contactName != null && contactName.length() != 0)
			   queryInfo = queryInfo + "Contact Name: <i>" + contactName + "</i> ";
			   
		    generalInfo[0] = queryInfo;
			   
			String  orderby               = request.getParameter("orderby");
			
			SampleRequestCustomer newClass = new SampleRequestCustomer();
			
			ListCustomer = newClass.findCustomer(fromCustNumber,
											   toCustNumber,
											   name,
											   city,
											   state,
											   country,
											   contactName,
											   orderby);
	}

		request.setAttribute("listCustomers", ListCustomer);
		request.setAttribute("generalInfo", generalInfo);
				 	 
	  //***** Go to the List JSP *****//
	  getServletConfig().getServletContext().
			getRequestDispatcher("/JSP/SampleRequest/listCustomer.jsp?" +
				"msg=" + msg).
			forward(request, response);
	  
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleCustomer.pageList: " + e);
	}
	   	    	
}
/**
 * Use to send information to the Add/Update Page.
 * Creation date: (6/20/2003 2:56:40 PM)
 */
private void pageUpd(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{
	try
	{
	  //** Bring in Variables.
	  	String[] generalInfo            = (String[]) request.getAttribute("generalInfo");
		String msg                      = (String) request.getAttribute("msg");
		String requestType              = (String) request.getAttribute("requestType");
 		SampleRequestCustomer whichCust = (SampleRequestCustomer) request.getAttribute("whichCust");

  		if (requestType.equals("addcust") || 
			requestType.equals("copy") ||
			requestType.equals("update") ||
			requestType.equals("add"))
		{
			//*****
			// Load the Class with Blanks, a Customer Number and/or Information
			//***** 
	  		loadClass(request, response);
			msg = (String)request.getAttribute("msg");
		}

 		
		if((requestType.equals("updateFinish") ||
			requestType.equals("addFinish")) &&
			msg.equals(""))
		{
		   msg = addUpdateCustomer(request, response,
			   					   whichCust, 
			   					   requestType);
		}
		if ((requestType.equals("updateFinish") ||
			requestType.equals("addFinish")) &&
			msg.equals(""))
	    {
		   msg = "Completed Normally";
		   request.setAttribute("requestType", "update");
		}
		else
		{
			   if (requestType.equals("addFinish"))
			      request.setAttribute("requestType", "addcust");
			   if (requestType.equals("updateFinish"))
			      request.setAttribute("requestType", "update");
		}

		requestType = (String) request.getAttribute("requestType");
        if (requestType.equals("add") ||
			requestType.equals("addcust") || 
			requestType.equals("copy") ||
			requestType.equals("update"))
		{
			//*****
			// Load this array with drop down boxes
			//***** 
			loadGeneralInfoArray(request, 
								 response);
		}
 		whichCust = (SampleRequestCustomer) request.getAttribute("whichCust");
 		if (!whichCust.getUpdateUser().trim().equals(""))
 		{
	 
 		}
 		
 		   

				//***** Go to the Upd JSP *****//
		   getServletConfig().getServletContext().
			   getRequestDispatcher("/JSP/SampleRequest/updCustomer.jsp?" +
				   "msg=" + msg).
			   forward(request, response);
		return;
	
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleCustomer.pageUpd: " + e);
	}  	    	
}
/**
 * Control for Sample Request Customer servlets, (JSP's)
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 * 
 */
public void performTask(javax.servlet.http.HttpServletRequest request, 
					    javax.servlet.http.HttpServletResponse response) 
{
	// Use specific request.set(get)Attributes for:
	//		requestType = Which type of request it is.
	//		msg         = Message to send back to the JSP
	//      generalInfo = for any strings to be sent to the JSP
	//		custClass   = Instantiated Customer Class
	try
	{
  	    //********************************************************************
		// Use this Array to send to the Jsp's for anything other than a Vector
		//  Each Jsp may use a different number of array values.
		//  Look in the page methods for details on what the values mean for that page.
		//  0 is loaded with -- (Drop Down List for State)
		//  1 is loaded with -- (Drop Down List for Company)
		String[] generalInfo = new String[2];
		request.setAttribute("generalInfo", generalInfo);
		
	    //** Always test for a message, and send it on.
	  	String msg = request.getParameter("msg");
 	    if (msg == null)
           msg = "";
	    request.setAttribute("msg", msg);

	   	//Instatiate Class To manipulate and send around.
		SampleRequestCustomer whichCust = new SampleRequestCustomer();
		request.setAttribute("whichCust", whichCust);
	     
	  // if there is no Request Type from the JSP, the default is INQ page.
	   String requestType = request.getParameter("requestType");
 	  	 if (requestType == null)
     	  requestType = "inq";
       request.setAttribute("requestType", requestType); 

       // Determining by Page, which page it uses for Security
       String urlAddress = "/web/CtlSampleCustomer?requestType=inq";
       if (requestType.equals("list"))
       		urlAddress = "/web/CtlSampleCustomer?requestType=list";
       if (requestType.equals("detail"))
       		urlAddress = "/web/CtlSampleCustomer?requestType=detail";
       if (requestType.equals("add") ||
	       requestType.equals("update") ||
	       requestType.equals("copy") ||
	       requestType.equals("addcust") ||
	       requestType.equals("addFinish") ||
	       requestType.equals("updateFinish") ||
	       requestType.equals("delete") ||
	       requestType.equals("deleteFinish"))
       		urlAddress = "/web/CtlSampleCustomer?requestType=update";
		//********************************************************************
		// Execute security servlet.
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

	    // Decision of whether or not to use the Inq, List or Detail
	    if (!SessionVariables.getSessionttiSecStatus(request,response).equals(""))
		{
			response.sendRedirect("/web/TreeNetInq?msg=" + 
				SessionVariables.getSessionttiSecStatus(request,response));
			return;
		}	    
	      // remove the Status and the Url
	    sess.removeAttribute("ttiTheURL");
	    sess.removeAttribute("ttiSecStatus");


	//**************************************************************
	//  Determine Which Method to Use, for which JSP Type to call,
	//       and if parameters need to be validated.
	//**************************************************************
	
    if (requestType.equals("inq"))
    	pageInq(request,
                response);

    if (requestType.equals("list"))
 	  	validateParameters(request,
		                   response);

    if (requestType.equals("detail") ||
	    requestType.equals("delete") ||
	    requestType.equals("deleteFinish"))
    	pageDtl(request,
                response);

    if (requestType.equals("add") ||
	    requestType.equals("update") ||
	    requestType.equals("copy"))
    	pageUpd(request,
                response);

    if (requestType.equals("addFinish") || 
	    requestType.equals("updateFinish") ||
		requestType.equals("addcust"))
    	validateParameters(request,
                response);
 
 }
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		theException.printStackTrace();
	}
}
/**
 * Use to Check Parameters, 
 *		If they should be there,
 *      If numbers are valid,
 *      If dates are valid,
 *      if from's are less than to's.
 *
 * Creation date: (6/27/2003 3:24:40 AM)
 */
private void validateParameters(javax.servlet.http.HttpServletRequest request,
								javax.servlet.http.HttpServletResponse response)  
{	
	try
	{
		//** Bring in Variables.
	  	String msg = (String) request.getAttribute("msg");
		String requestType = (String) request.getAttribute("requestType");
		SampleRequestCustomer whichCust = (SampleRequestCustomer) request.getAttribute("whichCust");
	
		if (requestType.equals("list"))
		{
		//*** Test Customer Numbers, make sure they are numbers.
	 		String custNumber = request.getParameter("custNumber");
			if (custNumber != null && custNumber.length() != 0)
			{
				try
				{
					Integer testedCustNumber = new Integer(custNumber);
					request.setAttribute("custNumber", testedCustNumber);
					  	pageList(request,
  				                 response);
				}
				catch(NumberFormatException nfe)
				{
					//System.out.println("Error in CtlSampleCustomer.validateParameters. " +
					//				   "Customer Number is not a number." +
					//				   nfe);
					msg = "There is a problem with the Customer Number, " + 
					  "the number chosen is not valid.";
				}		
			}
		
			String fromCustNumber = request.getParameter("fromCustNumber");
			if (fromCustNumber == null || fromCustNumber.length() == 0)
			   fromCustNumber = "0";
			String toCustNumber = request.getParameter("toCustNumber");
			if (toCustNumber == null || toCustNumber.length() == 0)
			   toCustNumber = "999999999";
					
 			try
			{
				Integer testedFromCustNumber = new Integer(fromCustNumber);
				Integer testedToCustNumber   = new Integer(toCustNumber);
				int x = testedFromCustNumber.compareTo(testedToCustNumber);
				
				if (x > 0)
				   msg = "For the Customer Number Fields, the From Customer is " +
			             "greater than the to Customer."; 
			    else
			    {
  					request.setAttribute("fromCustNumber", testedFromCustNumber);
  					request.setAttribute("toCustNumber", testedToCustNumber);
			    } 
			}
			catch(NumberFormatException nfe)
			{
				//System.out.println("Error in CtlSampleCustomer.validateParameters. " +
				//				   "Customer Number is not a number." +
				//				   nfe);
				msg = "There is a problem with a Customer Number, " + 
					  "the number chosen is not valid.";
			}


			if (msg.equals(""))
			  	pageList(request,
  		                 response);
			else
			{

				getServletConfig().getServletContext().
				getRequestDispatcher("/JSP/SampleRequest/inqCustomer.jsp?" +
					"msg=" + msg).
				forward(request, response);
			}
			
		}
		else
		{ 
		//AddFinish, UpdateFinish, addCust
		
		//************************************************  
   		// Test Customer Number (Make sure it is a number)
   		
		  String custNumber = request.getParameter("custNumber");
		  if (custNumber != null && custNumber.length() != 0)
		  {
			try
			{
				Integer testedCustNumber   = new Integer(custNumber);
				int testCustNumber         = Integer.parseInt(custNumber);

					whichCust.setCustNumber(testCustNumber);	
			}
			catch(NumberFormatException nfe)
			{
				//System.out.println("Error in CtlSampleCustomer.validateParameters. " +
				//				   "Customer Number is not a number." +
				//				   nfe);
				msg = msg        + "There is a problem with the Customer Number(" + 
					  custNumber + "), the number chosen is not valid. ";
			}		
		  }

	if (requestType.equals("addFinish") || 
	    requestType.equals("updateFinish"))
	{
		  
		//************************************************  
   		// Test Zip Code (Make sure it is a number)
   		
		  int testedZip = 0;
		  String zip = request.getParameter("zip");
		  if (zip != null && zip.length() != 0)
		  {
			try
			{
				testedZip = Integer.parseInt(zip);
			}
			catch(NumberFormatException nfe)
			{
				//System.out.println("Error in CtlSampleCustomer.validateParameters. " +
				//				   "Zip Code Number is not a number." +
				//				   nfe);
				msg = msg + "There is a problem with the Zip Code(" +
				      zip + "), the number chosen is not valid. ";
			}		
		  }
		  // Load into the Instantiated Class
		  whichCust.setZip(testedZip);
		
		//************************************************  
   		// Test Zip Code Extention (Make sure it is a number)

     	  int testedZipExtention = 0;
 		  String zipExtention = request.getParameter("zipExtention");
		  if (zipExtention != null && zipExtention.length() != 0)
		  { 
			try
			{
				testedZipExtention = Integer.parseInt(zipExtention);
			}
			catch(NumberFormatException nfe)
			{
			    //System.out.println("Error in CtlSampleCustomer.validateParameters. " +
				//				   "Zip Code Extention Number is not a number." +
				//				   nfe);
				msg = msg          + "There is a problem with the Zip Code Extention(" +
				      zipExtention + "), the number chosen is not valid.";
			}		
		  }
		  // Load into the Instantiated Class
		  whichCust.setZipExtention(testedZipExtention);

		  // Load all the other fields, No Tests Needed into the Instantiated Class
		  whichCust.setName(request.getParameter("name").trim());
		  whichCust.setAddress1(request.getParameter("address1").trim());
		  whichCust.setAddress2(request.getParameter("address2").trim());
		  whichCust.setCity(request.getParameter("city").trim());
    	  String  state              = request.getParameter("state");
    	  if (state == null || state.equals("None"))
		     state = "";
		  whichCust.setState(state);
		  whichCust.setForeignPostalCode(request.getParameter("foreignPostalCode").trim());
		  whichCust.setForeignCountry(request.getParameter("foreignCountry").trim());
		  whichCust.setContact(request.getParameter("contactName").trim());
		  whichCust.setContactPhone(request.getParameter("contactPhone").trim());
		  whichCust.setContactEmail(request.getParameter("contactEmail").trim());
		  whichCust.setShippingCompany(request.getParameter("shippingCompany").trim());
		  whichCust.setShippingAcct(request.getParameter("shippingAcct").trim());
		  whichCust.setCompany(request.getParameter("company").trim());
		  
	}
		  request.setAttribute("msg", msg);
		  request.setAttribute("whichCust", whichCust);
		  pageUpd(request,
  		          response);
		}

	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleCustomer.validateParameters: " + e);
	}
	   	    	
}
}
