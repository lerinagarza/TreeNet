/*
 * Created on September 25, 2008 - TWalton
 *
 */
package com.treetop.app.transaction;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.app.transaction.UpdTransaction;
import com.treetop.businessobjects.DateTime;
import com.treetop.servlets.BaseServlet;
import com.treetop.viewbeans.ParameterMessageBean;
import com.treetop.services.ServiceTransaction;
import com.treetop.services.ServiceTransactionError;
import com.treetop.utilities.UtilityDateTime;

/**
 * @author twalto
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CtlTransaction extends BaseServlet {

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
			requestType = "updUsageWorkFile";
		
		//**********************************************************************		
		//COMMENT THIS LINE BEFORE DEPLOYING!!!!!!!!!!!!
		//requestType = "inqSingleIngredientForward";
		//**********************************************************************
		
		
		//System.out.println("CtlTransaction, set Request Type to updHoldData");
		//requestType = "updHoldFlag";
		request.setAttribute("requestType", requestType);
		request.setAttribute("msg", "");
		//------------------------------------------------------------------
		// Call Security
		String urlAddress = "/web/CtlTransaction";
		if (requestType.equals("updHoldFlag"))
			urlAddress = "/web/CtlTransaction?requestType=updHoldFlag";
		if (requestType.equals("inqTransactionError") ||
				requestType.equals("listTransactionError"))
				urlAddress = "/web/CtlTransaction?requestType=inqTransactionError";
		
		if (requestType.equals("inqSingleIngredientForward"))
				urlAddress = "/web/CtlTransaction?requestType=inqSingleIngredientForward";
		if (requestType.equals("inqProductionDayBack"))
				urlAddress = "/web/CtlTransaction?requestType=inqProductionDayBack";
		if (requestType.equals("inqProductionDayForward"))
				urlAddress = "/web/CtlTransaction?requestType=inqProductionDayForward";
		if (requestType.equals("inqFruitToShipping"))
			urlAddress = "/web/CtlTransaction?requestType=inqFruitToShipping";
		
		
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
		
		String sendTo = "/APP/Transaction/updUsageWorkFile.jsp";
		//String sendTo = "/APP/Transaction/inqTrackAndTrace.jsp";
		//*********************************
		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//-----------------------------------------------------------------
		// Main Inquiry and List Pages
		if (requestType.equals("inquiry") || requestType.equals("list")) {
		}
		if (requestType.trim().equals("inqTransactionError") ||
			requestType.trim().equals("listTransactionError"))
		{
			sendTo = "/view/transaction/inqTransactionError.jsp";
			InqTransaction it = new InqTransaction();
			it.populate(request);
			//it.setInqUser(SessionVariables.getSessionttiProfile(request, response));
			it.validate();
			request.setAttribute("inqViewBean", it);
			if (requestType.equals("listTransactionError") &&
				it.getDisplayMessage().trim().equals(""))
			{
				pageList(request, response);
				sendTo = "view/transaction/listTransactionError.jsp";
			}
			else
			{ // Set some default Values -- Dates
				pageInq(request, response);
			}
		}
		
		if (requestType.trim().equals("inqSingleIngredientForward"))
			{
				sendTo = "/APP/Transaction/inqTrackAndTrace.jsp";
				InqTransaction it = new InqTransaction();
				it.setInqUser(SessionVariables.getSessionttiProfile(request, response));
				it.populate(request);
				request.setAttribute("inqViewBean", it);
				if (!it.getGoButton().equals("")) {
					//NOT the first time on this page  Do stuff here
					it.validate();
					request.setAttribute("inqViewBean", it);
					if(it.getDisplayMessage().equals("")) {
						pageInq(request, response);
					}
					
				}
				
			
			}
		if (requestType.trim().equals("inqProductionDayBack"))
		{
			sendTo = "/APP/Transaction/inqTrackAndTrace.jsp";
			InqTransaction it = new InqTransaction();
			it.setInqUser(SessionVariables.getSessionttiProfile(request, response));
			it.populate(request);
			it.validate();
			request.setAttribute("inqViewBean", it);
			if (requestType.equals("inqProductionDayBack") &&
				it.getDisplayMessage().trim().equals(""))
			{
				pageInq(request, response);
				sendTo = "APP/Transaction/inqTrackAndTrace.jsp";
			}
			else
			{ // Set some default Values -- Dates
				pageInq(request, response);
			}
		}
		if (requestType.trim().equals("inqProductionDayForward"))
		{
			sendTo = "/APP/Transaction/inqTrackAndTrace.jsp";
			InqTransaction it = new InqTransaction();
			it.setInqUser(SessionVariables.getSessionttiProfile(request, response));
			it.populate(request);
			it.validate();
			request.setAttribute("inqViewBean", it);
			if (requestType.equals("inqProductionDayForward") &&
				it.getDisplayMessage().trim().equals(""))
			{
				pageInq(request, response);
				sendTo = "APP/Transaction/inqTrackAndTrace.jsp";
			}
			else
			{ // Set some default Values -- Dates
				pageInq(request, response);
			}
		}
		
		if (requestType.trim().equals("inqFruitToShipping"))
		{
			sendTo = "/APP/Transaction/inqTrackAndTrace.jsp";
			InqTransaction it = new InqTransaction();
			it.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			it.setInqUser(SessionVariables.getSessionttiProfile(request, response));
			it.populate(request);
			it.validate();
			request.setAttribute("inqViewBean", it);
			if (requestType.equals("inqFruitToShipping") &&
				it.getDisplayMessage().trim().equals(""))
			{
				pageInq(request, response);
				sendTo = "APP/Transaction/inqTrackAndTrace.jsp";
			}
			else
			{ // Set some default Values -- Dates
				pageInq(request, response);
			}
		}
		
		
		//-----------------------------------------------------------------
		//   DETAIL PAGE
		//		NOT Going to have a DETAIL Page Section for NEW ITEM
		//-----------------------------------------------------------------
		//		if (requestType.equals("detail")) {
		//		}
		//-----------------------------------------------------------------
		//   UPDATE PAGE
		//-----------------------------------------------------------------
		// No longer building this work file
		//if (requestType.equals("updUsageWorkFile")) {
		//	UpdTransaction ut = new UpdTransaction();
		//	ut.populate(request);
		//	ut.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
		//	ut.validate();
		//	ut.getSecurity(request, response);
		//	request.setAttribute("updViewBean", ut);
		//	if (!ut.getGoAllButton().trim().equals("") ||
		//		!ut.getGoButton().trim().equals("") ||
		//		!ut.getGoDailyButton().trim().equals(""))
		//	   pageUpd(request, response);
		//	sendTo = "/APP/Transaction/updUsageWorkFile.jsp";
		//}
		if (requestType.equals("updHoldFlag"))
		{
			UpdTransaction ut = new UpdTransaction();
			ut.populate(request);
			request.setAttribute("updViewBean", ut);
			if (!ut.getGoButton().trim().equals(""))
			{
				try
				{
					ServiceTransaction.determineHoldTransactions("");
					//System.out.println("DONE, Processing");
				}
				catch(Exception e)
				{
					System.out.println("Found Error" + e);
				}
			}
			sendTo = "/APP/Transaction/updHoldFlag.jsp";
		}
		//-----------------------------------------------------------------
		//  Go to the JSP
		//-----------------------------------------------------------------
		try { // Send down to the JSP
			getServletConfig().getServletContext().getRequestDispatcher(sendTo)
					.forward(request, response);
		} catch (Throwable theException) {
			
			System.out.println("---------------------");
			System.out.println("CtlTransaction Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
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
	 * Use method to set defaults into the DATE fields
	 */
	protected void pageInq(HttpServletRequest request,
						   HttpServletResponse response) {
		InqTransaction it = (InqTransaction) request.getAttribute("inqViewBean");
		
		DateTime today = UtilityDateTime.getSystemDate();
		if (it.getInqFromDate().equals(""))
			it.setInqFromDate(today.getDateFormatMMddyySlash());
		if (it.getInqToDate().equals(""))
			it.setInqToDate(today.getDateFormatMMddyySlash());
		
		if (it.getRequestType().equals("inqSingleIngredientForward") 
				|| it.getRequestType().equals("inqProductionDayBack") 
				|| it.getRequestType().equals("inqProductionDayForward")
				|| it.getRequestType().equals("inqFruitToShipping")) {
			
			
			//call service from here if there is no display message  (no errors in validation)
			if (it.getDisplayMessage().equals("")) {
				try {
					ServiceTransaction.submitTrackAndTrace(it);
					if (it.getDisplayErrors().equals("")) {					
						if (it.getRequestType().equals("inqSingleIngredientForward")) {
							it.setDisplayMessage("Submitted Track and Trace Request for Item: " + it.getInqItem().trim() +
									"  Lot: " + it.getInqLot() + "<br />Sent to printer: " +
									it.getOutQ() + " - " + it.getOutQDescr());
						}
						if (it.getRequestType().equals("inqProductionDayBack")
								|| it.getRequestType().equals("inqProductionDayForward")
								|| it.getRequestType().equals("inqFruitToShipping")) {
							if (it.getInqOrder().equals("")) {
								it.setDisplayMessage("Submitted Track and Trace Request for " +
									"Facility: " + it.getInqFacility().trim() + 
									"  Start Date: " + it.getInqDate() + 
									"  Item: " + it.getInqItem().trim() + 
									"<br />Sent to printer: " +
									it.getOutQ() + " - " + it.getOutQDescr());
							} else {
								it.setDisplayMessage("Submitted Track and Trace Request for " +
									"Manufacturing Order Number: " + it.getInqOrder() +
									"<br />Sent to printer: " + it.getOutQ() + " - " + it.getOutQDescr());
							}
						}
					} else {it.setDisplayMessage(it.getDisplayErrors());}
				} catch (Exception e) {it.setDisplayMessage("Error submitting Track and Trace Request:  " + e.toString());}
			}
		}
		                                         
		
		request.setAttribute("inqViewBean", it);
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
		InqTransaction it = (InqTransaction) request.getAttribute("inqViewBean");
		String requestType = (String) request.getAttribute("requestType");
		if (requestType.trim().equals("listTransactionError"))
		{
			try
			{
				Vector sendValues = new Vector();
				sendValues.addElement(it);
				it.setBean(ServiceTransactionError.listTransactionError(it.getInqEnvironment(), sendValues));
			}
			catch(Exception e)
			{
				it.setDisplayMessage(e.toString());
			}
		}
		request.setAttribute("inqViewBean", it);

		return;
	}

	/*
	 * Retrieve and Build everthing needed for the Detail Page
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageDetail(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void pageDtl(HttpServletRequest request,
			HttpServletResponse response) {
		// NOT Going to Include a page DTL Section
	}

	/*
	 * Retrieve and Build everthing needed for the Update Page
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
protected void pageUpd(HttpServletRequest request,
			HttpServletResponse response) {
	
//	   UpdTransaction ut = (UpdTransaction) request.getAttribute("updViewBean");
//		try
//		{
//			ut.setDisplayMessage(ServiceTransaction.updateUsageWorkFile(ut));
//		}
//		catch(Exception e)
//		{
//			ut.setDisplayMessage(e.toString());
//		}
//		request.setAttribute("updViewBean", ut);
	}
}
