/*
 * Created on October 20, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.app.specification;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanSpecification;
import com.treetop.businessobjects.*;
import com.treetop.servlets.BaseServlet;
import com.treetop.services.*;
import com.treetop.utilities.*;
import com.treetop.viewbeans.ParameterMessageBean;

/**
 * @author twalto
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CtlSpecificationNEW extends BaseServlet {

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
	 * @see com.treetop.servlets.BaseServlet#performTask(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void performTask(HttpServletRequest request,
			HttpServletResponse response) {

		String requestType = request.getParameter("requestType");
		if (requestType == null)
			requestType = "inqCPGSpec";

		request.setAttribute("requestType", requestType);
		request.setAttribute("msg", "");
		//TESTING//
		//requestType = "updCPGSpec";
		//------------------------------------------------------------------
		// Call Security
		String urlAddress = "/web/CtlSpecificationNEW";
		if (requestType.equals("updCPGSpec"))
			urlAddress = "/web/CtlSpecificationNEW?requestType=updCPGSpec";

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
		String sendTo = "/APP/Specification/inqCPGSpec.jsp";
		//*********************************
		//-----------------------------------------------------------------
		//   UPDATE PAGE To be dealt with BEFORE the List Pages
		//-----------------------------------------------------------------
		
		if (requestType.equals("updCPGSpec") ||
			requestType.equals("addCPGSpec")) {
			UpdSpecification us = new UpdSpecification();
			us.populate(request);
			us.validate();
			us.determineSecurity(request, response);
			us.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			request.setAttribute("updViewBean", us);
			request.setAttribute("requestType", requestType);
			sendTo = "/APP/Specification/updCPGSpec.jsp";
			pageUpd(request, response);
		}
//		-----------------------------------------------------------------
		//   DELETE RECORDS - Then show the LIST PAGE Again
		//-----------------------------------------------------------------
		
		if (requestType.equals("deleteCPGSpec")) {
			UpdSpecification us = new UpdSpecification();
			us.populate(request);
			us.validate();
			us.determineSecurity(request, response);
			request.setAttribute("updViewBean", us);
			request.setAttribute("requestType", requestType);
			pageUpd(request, response);
			requestType = "listCPGSpec";
		}
		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//-----------------------------------------------------------------
		// Main Inquiry and List Pages
		if (requestType.equals("inqCPGSpec") ||
			requestType.equals("listCPGSpec"))
		{
			InqSpecification is = new InqSpecification();
			is.populate(request);
			if (is.getRequestType().equals("deleteCPGSpec"))
				is.setRequestType(requestType);
			is.validate();
			is.determineSecurity(request, response);
			request.setAttribute("inqViewBean", is);
			request.setAttribute("requestType", requestType);
			sendTo = "/APP/Specification/inqCPGSpec.jsp";
			if (requestType.equals("listCPGSpec"))
			{
				pageList(request, response);
				sendTo = "/APP/Specification/listCPGSpec.jsp";
			}
		}
		//-----------------------------------------------------------------
		//   DETAIL PAGE
		//-----------------------------------------------------------------
		if (requestType.equals("dtlCPGSpec")) {
			DtlSpecification ds = new DtlSpecification();
			ds.populate(request);
			ds.validate();
			ds.determineSecurity(request, response);
			request.setAttribute("dtlViewBean", ds);
			request.setAttribute("requestType", requestType);
			sendTo = "/APP/Specification/dtlCPGSpec.jsp";
			pageDtl(request, response);
		}
		//-----------------------------------------------------------------
		//  Go to the JSP
		//-----------------------------------------------------------------
		try { // Send down to the JSP
			getServletConfig().getServletContext().getRequestDispatcher(sendTo)
					.forward(request, response);
		} catch (Throwable theException) {
			
			System.out.println("---------------------");
			System.out.println("CtlSpecification Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
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
	 */
	protected void pageList(HttpServletRequest request,
							HttpServletResponse response) {
		
		InqSpecification is = (InqSpecification) request.getAttribute("inqViewBean");
		try
		{
//			 Go out and get the Specification Information
			if (is.getRequestType().equals("listCPGSpec"))
			{	
				Vector sendParms = new Vector();
				sendParms.addElement(is);
				BeanSpecification bi = ServiceSpecification.listCPGSpecs(sendParms);
				Vector getList = new Vector();
				getList.addElement(bi);
				is.setListReport(getList);
			}
		} catch(Exception e)
		{}
		request.setAttribute("inqViewBean", is);
	}

	/*
	 * Retrieve and Build everthing needed for the Detail Page
	 */
	protected void pageDtl(HttpServletRequest request,
						   HttpServletResponse response) {
		
		DtlSpecification ds = (DtlSpecification) request.getAttribute("dtlViewBean");
		try
		{
//			 Go out and get the Specification Information
			if (ds.getRequestType().equals("dtlCPGSpec"))
			{	
				Vector sendParms = new Vector();
				sendParms.addElement(ds);
				ds.setBeanSpec(ServiceSpecification.findCPGSpec(sendParms));
			}
		} catch(Exception e)
		{}
		request.setAttribute("dtlViewBean", ds);
	}

	/*
	 * Retrieve and Build everthing needed for the Update Page
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
protected void pageUpd(HttpServletRequest request,
					   HttpServletResponse response) {
    UpdSpecification us = (UpdSpecification) request.getAttribute("updViewBean");
	String requestType = (String) request.getAttribute("requestType");
	
	try
	{
		if (requestType.equals("updCPGSpec") ||
			requestType.equals("addCPGSpec")) {
		   if (!us.getSaveButton().equals("") &&
			    us.getDisplayMessage().equals(""))
		   { // Update the Information
		      // Do the actual Update Process 	
			  try
			  {
				if (requestType.equals("updCPGSpec"))
				{
				   us.populateAnalyticalTests(request);
				   us.populateProcessParameters(request);
//				 NEED To add in a Validate Section for this.....
				}
				if (requestType.equals("addCPGSpec"))
				{
					 DateTime dtNow = UtilityDateTime.getSystemDate();
					 us.setRevisionDate(dtNow.getDateFormatyyyyMMdd());
//				 NEED To add in a Validate Section for this.....
				}
				Vector sendValues = new Vector();
				sendValues.addElement(us);
				ServiceSpecification.processCPGSpec(sendValues);
			  }
			  catch(Exception e)
			  {
			  	// Problem Adding / Updating a Load
			  }
		   }
//			 Get the Newest Version of the Specification 
				// AFTER the Updates have Been Completed
			  DtlSpecification ds = new DtlSpecification();
			 // Need to set the Item Number AND the Revision Date
			  ds.setItemNumber(us.getItemNumber());
		      ds.setRevisionDate(us.getRevisionDate());
			  Vector sendParms = new Vector();
			  sendParms.addElement(ds);	
			  us.loadFromBeanSpecification(ServiceSpecification.findCPGSpec(sendParms));	
			  us.buildDropDownVectors(); 
		}
		if (requestType.equals("deleteCPGSpec"))
		{
			// Go out and delete this record 
			// Only pending Status can be deleted
			// NEED the item and the revision date
			Vector sendValues = new Vector();
			sendValues.addElement(us);
			ServiceSpecification.processCPGSpec(sendValues);
		}
			
	} catch(Exception e)
	{}
	request.setAttribute("updViewBean", us);
	}
}
