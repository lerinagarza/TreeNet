/*
 * Created on March 19, 2008
 *    Author Teri Walton
 * 
 * Will be used to control any reports needed for planning information
 * 
 */
package com.treetop.app.dataset;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ibm.as400.access.*;
import com.treetop.SessionVariables;
import com.treetop.servlets.BaseServlet;
import com.treetop.services.*;
import com.treetop.viewbeans.ParameterMessageBean;

/**
 * @author twalto
 * 
 */
public class CtlDataSet extends BaseServlet {

	/*
	 * Process incoming HTTP GET requests Goes directly to the performTask
	 * 
	 * @see com.treetop.servlets.BaseServlet#doGet()
	 */
	public void doGet(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {
		performTask(request, response);
	}

	/*
	 * Process incoming HTTP POST requests Goes directly to the performTask
	 * 
	 * @see com.treetop.servlets.BaseServlet#doPost()
	 */
	public void doPost(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {
		performTask(request, response);
	}

	/*
	 * Tests Parameters, Calls Security, and Directs Traffic between the pages
	 * 
	 */
	public void performTask(HttpServletRequest request,
			HttpServletResponse response) {

		String requestType = request.getParameter("requestType");
		if (requestType == null)
			requestType = "updCompanyCost";
	//	requestType = "detail";
		request.setAttribute("requestType", requestType);
		request.setAttribute("msg", "");
		//TESTING//
		//requestType = "inquiryAvailable";
		//------------------------------------------------------------------
		// Call Security
		String urlAddress = "/web/CtlDataSet?requestType=" + requestType;

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
		String sendTo = "/APP/DataSet/updDataSet.jsp";
		//*********************************
		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//-----------------------------------------------------------------
		// Main Inquiry and List Pages
	//	if (requestType.equals("inqPlanRawFruit") ||
	//		requestType.equals("listPlanRawFruit")) 
	//	{
	//	}		
		//-----------------------------------------------------------------
		//   DETAIL PAGE
		//		NOT Going to have a DETAIL Page Section for NEW ITEM
		//-----------------------------------------------------------------
		//if (requestType.equals("detail")) {	
		//}
		//-----------------------------------------------------------------
		//   UPDATE PAGE
		//-----------------------------------------------------------------
		if (requestType.equals("updCompanyCost") ||
			requestType.equals("updAverageCost")) {
			UpdDataSet uds = new UpdDataSet();
			uds.populate(request);
			uds.setRequestType(requestType);
			uds.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			// Validation will set the Defaults where needed
			uds.validate(); 
			request.setAttribute("updViewBean", uds);
			request.setAttribute("requestType", requestType);
			if (!uds.getUpdDataSet().equals("") &&
				uds.getDisplayMessage().equals(""))
			   pageUpd(request, response);			
			
			sendTo = "/APP/DataSet/updDataSet.jsp";
		}
		//-----------------------------------------------------------------
		//  Go to the JSP
		//-----------------------------------------------------------------
		try { // Send down to the JSP
			getServletConfig().getServletContext().getRequestDispatcher(sendTo)
					.forward(request, response);
		} catch (Throwable theException) {
			
			System.out.println("---------------------");
			System.out.println("CtlPlanning Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
			ParameterMessageBean.printParameters(request);
			System.out.println("---------------------");
			System.out.println(theException);
			theException.printStackTrace();
		}
		return;
	}

	/*
	 * Retrieve and Build everthing needed for the Inquiry Page
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageInquiry(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void pageInq(HttpServletRequest request,
			HttpServletResponse response) {

		return;
	}

	/*
	 * Retrieve and Build everthing needed for the List Page
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageList(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void pageList(HttpServletRequest request,
							HttpServletResponse response) {
	}

	/*
	 * Retrieve and Build everthing needed for the Update Page
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
protected void pageUpd(HttpServletRequest request,
					   HttpServletResponse response) {
		UpdDataSet uds = (UpdDataSet) request.getAttribute("updViewBean");
	    try
		{
	    	if (uds.getRequestType().equals("updCompanyCost"))
	    	{
	    	   //ServiceDataSet.updateCompanyCostDmpDataSet(uds);
	    	   uds.setDisplayMessage("Company Cost Information has been updated. ");
	    	}
	    	//if (uds.getRequestType().equals("updAverageCost"))
	    	//{
	    	 //  ServiceDataSet.updateAverageCost(uds);
	    	  // uds.setDisplayMessage("Average Cost Information has been updated. ");
	    	//}
		}
	    catch(Exception e)
		{
	    	uds.setDisplayMessage("Problem occured when trying to update: " + e );
	    }
	    request.setAttribute("updViewBean", uds);
	}

/*
 * Retrieve and Build everthing needed for the Detail Page
 * 
 */
protected void pageDtl(HttpServletRequest request,
		HttpServletResponse response) {

}
}
