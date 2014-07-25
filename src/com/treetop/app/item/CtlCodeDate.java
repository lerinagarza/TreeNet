package com.treetop.app.item;

import com.treetop.data.*;
import com.treetop.services.ServiceItem;
import com.treetop.services.ServiceGTIN;
import com.treetop.servlets.BaseServlet;
import com.treetop.viewbeans.ParameterMessageBean;
import com.treetop.businessobjects.*;
import com.treetop.*;

import java.util.*;
import java.io.IOException;
import java.math.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * Insert the type's description here.
 * Creation date: (12/30/2003 11:07:36 AM)
 * @author: 
 */
public class CtlCodeDate extends BaseServlet  
{
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
 * Process incoming requests for information
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
	if (requestType == null)
		requestType = "inq";
	request.setAttribute("requestType", requestType);
	request.setAttribute("msg", "");
 	  	
	//------------------------------------------------------------------
	// Call Security
 	   String urlAddress = "/web/CtlItem";

	   if (!callSecurity(request, response, urlAddress).equals("")) {
	   	try {
			response.sendRedirect("/web/TreeNetInq?msg="
					+ SessionVariables.getSessionttiSecStatus(request,
								response));
		 } catch (IOException e) {
			e.printStackTrace();
	 	 }
		return;
	   }
		//------------------------------------------------------------------
		//Passed Security
		//*********************************
		// Default Screen
		String sendTo = "/APP/Item/inqCodeDate.jsp";
		
		InqCodeDate icd = new InqCodeDate();
		icd.populate(request);
		icd.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
		icd.validate();
		request.setAttribute("inqViewBean", icd);
		if (requestType.equals("detail") &&
			icd.getDisplayMessage().equals(""))
		{
			pageDtl(request, response);
			sendTo = "/APP/Item/dtlCodeDate.jsp";
		}
	//-----------------------------------------------------------------
	//  Go to the JSP
	//-----------------------------------------------------------------
	try { // Send down to the JSP
		getServletConfig().getServletContext().getRequestDispatcher(sendTo)
				.forward(request, response);
	} catch (Throwable theException) {
		
		System.out.println("---------------------");
		System.out.println("CtlCodeDate Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
		ParameterMessageBean.printParameters(request);
		System.out.println("---------------------");
		System.out.println(theException);
		theException.printStackTrace();
	}
	return;	
}
catch(Throwable theException)
{
	// uncomment the following line when unexpected exceptions
	// are occuring to aid in debugging the problem.
	theException.printStackTrace();
}
}
/* (non-Javadoc)
 * @see com.treetop.servlets.BaseServlet#pageInq(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
protected void pageInq(HttpServletRequest request, HttpServletResponse response) {
	
	
}
/* (non-Javadoc)
 * @see com.treetop.servlets.BaseServlet#pageList(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
protected void pageList(HttpServletRequest request, HttpServletResponse response) {
	
	
}
/* (non-Javadoc)
 * @see com.treetop.servlets.BaseServlet#pageUpd(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
protected void pageUpd(HttpServletRequest request, HttpServletResponse response) {
	
	
}
/* (non-Javadoc)
 * @see com.treetop.servlets.BaseServlet#pageDtl(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
protected void pageDtl(HttpServletRequest request, HttpServletResponse response) {
	try
	{
		InqCodeDate icd = (InqCodeDate) request.getAttribute("inqViewBean");
	    try
	    {
	       Vector sendValues = new Vector();
	       sendValues.addElement(icd);
		   icd.setItemWhse(ServiceItem.findItemCodeDate(sendValues));
		   icd.buildBestByValue();		                                         
        }
	    catch(Exception e)
	    {
	    	 System.out.println("Error On Retrieve Of Information: " + e);
	    }
	    request.setAttribute("inqViewBean", icd);
	    
		// IF GTIN is filled in -- go get the Family Tree
		if (!icd.getInqItem().equals("")) {
			try { // GET THE FAMILY TREE
				request.setAttribute("familyTree", ServiceGTIN.buildViews(icd.getItemWhse().getNewItemPalletGTIN()));
			} catch (Exception e) {
				// There is a problem when retrieving the Parent
				// Child - Family Tree
			}
		}	    
	}
	catch(Exception e)  
	{
	  	// No problem Just Catch it.
	}
	  return;		
}
}
