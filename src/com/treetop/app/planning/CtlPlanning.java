/*
 * Created on March 19, 2008
 *    Author Teri Walton
 * 
 * Will be used to control any reports needed for planning information
 * 
 */
package com.treetop.app.planning;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ibm.as400.access.*;
import com.lawson.api.*;
import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanPlanning;
import com.treetop.servlets.BaseServlet;
import com.treetop.services.*;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.viewbeans.ParameterMessageBean;

/**
 * @author twalto
 * 
 */
public class CtlPlanning extends BaseServlet {

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
			requestType = "updPlannedMO";
		//	requestType = "detail";
		request.setAttribute("requestType", requestType);
		request.setAttribute("msg", "");
		//TESTING//
		//requestType = "inquiryAvailable";
		
		

		//------------------------------------------------------------------
		// Set URL for Security
		String urlAddress = "/web/CtlPlanning";
		if (requestType.equals("updPlannedMO") ||
			requestType.equals("updPlanningDate")) {
			urlAddress = "/web/CtlPlanning?requestType=updPlannedMO";
		}
		//------------------------------------------------------------------
		
		
		//------------------------------------------------------------------
		// Call Security
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
		String sendTo = "/APP/Planning/inqPlannedProduction.jsp";
		//*********************************
		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//-----------------------------------------------------------------
		// Main Inquiry and List Pages
		if (requestType.equals("inqPlanRawFruit") ||
				requestType.equals("listPlanRawFruit")) 
		{ // Not using, never used
//			InqPlanRawFruit ip = new InqPlanRawFruit();
//			ip.populate(request);
			// Validation will set the Defaults where needed
//			ip.validate(); 
//			if (ip.getRequestType().trim().equals(""))
//				ip.setRequestType(requestType);
//			else
//				requestType = ip.getRequestType();
//			request.setAttribute("inqViewBean", ip);
//			request.setAttribute("requestType", requestType);
//			if (requestType.equals("listPlanRawFruit"))
//			{	
//				pageList(request, response);
//				ip = (InqPlanRawFruit) request.getAttribute("inqViewBean");
//				requestType = ip.getRequestType().trim();
//				if (requestType.equals("listPlanRawFruit"))
//					sendTo = "/APP/Planning/listPlanRawFruit.jsp";
//			}
			// Use for Debugging to test information
			//System.out.println("STOP");
		}	
		if (requestType.equals("inqPlannedProduction") ||
				requestType.equals("listPlannedProduction")) 
		{
			InqPlanning ip = new InqPlanning();
			ip.populate(request);
			// Validation will set the Defaults where needed
			ip.validate(); 
			if (ip.getRequestType().trim().equals(""))
				ip.setRequestType(requestType);
			else
				requestType = ip.getRequestType();
			request.setAttribute("inqViewBean", ip);
			request.setAttribute("requestType", requestType);
			if (requestType.equals("listPlannedProduction"))
			{	
				pageList(request, response);
				ip = (InqPlanning) request.getAttribute("inqViewBean");
				requestType = ip.getRequestType().trim();
				if (requestType.equals("listPlannedProduction"))
					sendTo = "/APP/Planning/listPlannedProduction.jsp";
			}
//			if (sendTo.equals("/APP/Planning/inqPlanRawFruit.jsp"))
//				sendTo = "/APP/Planning/inqPlannedProduction.jsp";

			// Use for Debugging to test information
			System.out.println("STOP -- if calling inqPlannedProduction from CtlPlanning");
		}

		if (requestType.equals("updPlannedMO") ||
			requestType.equals("updPlanningDate")){
			//Planned MO page
			UpdPlanning up = new UpdPlanning(request);
			
			request.setAttribute("viewBean",up);
			
			if (up.getErrorMessage().equals("")) {
			
				if(!up.getSubmit().equals("")){
					pageUpd(request, response);
				}
				
			}
			if (requestType.equals("updPlanningDate"))
				sendTo = "/view/planning/updPlanningDate.jsp";
			else
				sendTo = "/view/planning/updPlannedMO.jsp";

		}
		//-----------------------------------------------------------------
		//   DETAIL PAGE
		//		NOT Going to have a DETAIL Page Section for NEW ITEM
		//-----------------------------------------------------------------
		//if (requestType.equals("detail")) {	
		//}
		//-----------------------------------------------------------------
		//   UPDATE PAGE
		//-----------------------------------------------------------------
		// Moved to the CtlDataSet Servlet -- Can Delete
		if (requestType.equals("updForecast")) {
			UpdPlanning up = new UpdPlanning();
			up.populate(request);
			up.setUserProfile(SessionVariables.getSessionttiProfile(request, response));
			//			// Validation will set the Defaults where needed
			//ip.validate(); 
			request.setAttribute("updViewBean", up);
			request.setAttribute("requestType", requestType);
			if (!up.getUpdateButton().equals(""))
				pageUpd(request, response);			
			sendTo = "/APP/Planning/updForecast.jsp";
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
	 * Retrieve and Build everything needed for the List Page
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageList(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void pageList(HttpServletRequest request,
			HttpServletResponse response) {
		try
		{
//			5/23/13 - Not using, have never used
//			InqPlanRawFruit ip = (InqPlanRawFruit) request.getAttribute("inqViewBean");
//			String requestType = (String) request.getAttribute("requestType");
//			if (ip != null)
//			{
//				if (requestType.equals("listPlanRawFruit"))
//				{
//					Vector loadReport = new Vector();
//					loadReport.addElement(ServicePlanning.buildPlanningRawFruitRequirements(ip));
//					ip.setListReport(loadReport);
//				}
//			}
//			request.setAttribute("inqViewBean", ip);
		}
		catch(Exception e)
		{
		}
	}

	/*
	 * Retrieve and Build everthing needed for the Update Page
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void pageUpd(HttpServletRequest request,
			HttpServletResponse response) {
		String requestType = request.getParameter("requestType");
		// Moved into the CtlDataSet Servlet -- can delete
		if (requestType.equals("updForecast")) {
			UpdPlanning up = (UpdPlanning) request.getAttribute("updViewBean");
			try
			{
				BeanPlanning bp = ServicePlanning.updateForecast(up);
				//	    	
				//	    	uds.setDisplayMessage("Information has been updated. ");	
			}
			catch(Exception e)
			{
				//	    	up.setDisplayMessage("Problem occurred when trying to update: " + e );
			}
			request.setAttribute("updViewBean", up);
		}

		if(requestType.equals("updPlannedMO")){
			UpdPlanning up = (UpdPlanning) request.getAttribute("viewBean");
			CommonRequestBean crb = new CommonRequestBean();
			crb.setEnvironment(up.getEnvironment());
			try {
				// 11/1/12 - Change the code to call the ServicePlanning Method
				//System.out.println("Delete Planned Orders");
				ServicePlanning.deletePlannedOrders(crb);
				
				//PMS170MIMassDelete api = new PMS170MIMassDelete(up);
				//String authorization = request.getHeader("Authorization");
				//PMS170MI.massDelete(api, authorization);
				
			} catch (Exception e) {
				//set status message in UpdPlanning
				up.setErrorMessage(
						"Error executing Delete of Planned MO's<br /><br />" + 
						e.getMessage() + 
						"<br /><br />Please contact the HelpDesk at x1425");
			}
			if (up.getErrorMessage().equals("") && up.getDisplayMessage().trim().equals("")) {
				up.setDisplayMessage("The process to delete planned orders has been completed.");

			}
			request.setAttribute("viewbean", up);
		}

		if(requestType.equals("updPlanningDate")){
			UpdPlanning up = (UpdPlanning) request.getAttribute("viewBean");
			CommonRequestBean crb = new CommonRequestBean();
			crb.setEnvironment(up.getEnvironment());
				try {
					// 11/19/12 - Allow for Update of Planned Dates, 
				//	System.out.println("Update Dates on the MO");
					ServicePlanning.updatePlannedOrdersMO(crb);
					
				} catch (Exception e) {
					//set status message in UpdPlanning
					up.setErrorMessage(
							"Error executing Update of Dates on MO's<br /><br />" + 
							e.getMessage() + 
							"<br /><br />Please contact the HelpDesk at x1425");
				}
				try {
					// 4/9/13 - TWalton - Allow to Upload the Planned workfile
					ServicePlanning.updatePlannedOrdersWorkFile(crb);
					
				} catch (Exception e) {
					//set status message in UpdPlanning
					up.setErrorMessage(
							"Error executing Update Planned Orders Work File<br />" + 
							e.getMessage() + 
							"<br /><br />Please contact the HelpDesk at x1425");
				}
			if (up.getErrorMessage().equals("") && up.getDisplayMessage().trim().equals("")) {
				up.setDisplayMessage("The process to update Dates on MO's has been completed.");
			}
			request.setAttribute("viewbean", up);
		}

	}

	/*
	 * Retrieve and Build everything needed for the Detail Page
	 * 
	 */
	protected void pageDtl(HttpServletRequest request,
			HttpServletResponse response) {

	}
}
