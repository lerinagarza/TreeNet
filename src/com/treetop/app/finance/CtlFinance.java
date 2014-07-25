/*
 * Created on March 12, 2008  - TWalton
 *
 *   Will be used for programs related to Finance.
 *       Build Cost Type 9 for Example
 */ 
 
package com.treetop.app.finance;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.servlets.BaseServlet;
import com.treetop.services.*;
import com.treetop.viewbeans.ParameterMessageBean;

/**
 * @author twalto - 3/12/08
 *
 *  Build the Company Cost Information (Cost Type 9)
 * 
 */
public class CtlFinance  extends BaseServlet {
	
	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageDetail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageDtl(HttpServletRequest request, HttpServletResponse response) {
		
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		performTask(request, response);
		
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {
		performTask(request, response);
		
	}

	/* 
	 *  Request Types Used:
	 * 	    updateCostType
	 */
	public void performTask(HttpServletRequest request, HttpServletResponse response) {
	
		String requestType = request.getParameter("requestType");
		if (requestType == null)
			requestType = "changeCostType";
		// Set request Type for TESTING
//		requestType = "bldCostingReportFiles";
		request.setAttribute("requestType", requestType);
		request.setAttribute("msg", "");
		//------------------------------------------------------------------
		// Call Security
		String urlAddress = "/web/CtlFinance";

		if (requestType.equals("bldStateFees") ||   // Choose the Dates to Build From
			requestType.equals("addStateFees"))   // Call RPG to Build the Records for the File
			urlAddress = "/web/CtlFinance?requestType=bldStateFees";
		if (requestType.equals("rptStateFees") ||
			requestType.equals("bldReportStateFees"))     
			urlAddress = "/web/CtlFinance?requestType=rptStateFees";
		if (requestType.equals("bldCostingReportFiles"))     
			urlAddress = "/web/CtlFinance?requestType=bldCostingReportFiles";
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
		String sendTo = "/APP/Finance/updCostType.jsp";
		//*********************************
		//-----------------------------------------------------------------
		//   UPDATE PAGE
		//-----------------------------------------------------------------
		if (requestType.equals("updateCostType") ||
			requestType.equals("changeCostType"))
		{	
			UpdFinance uf = new UpdFinance();
			uf.populate(request);
			uf.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			if (uf.getRequestType().trim().equals("") ||
				!uf.getRequestType().equals(requestType))
				uf.setRequestType(requestType);
			uf.validate();
			request.setAttribute("updViewBean", uf);
			if (requestType.equals("updateCostType") &&
				((!uf.getShouldOverride().trim().equals("") &&
			     !uf.getShouldOverride().trim().equals("TEST") ) ||
				 (uf.getShouldOverride().trim().equals("") &&
				 		uf.getPage1().trim().equals("Y")))&&
				uf.getRetryCompanyCost().trim().equals(""))
			{
			   pageUpd(request, response);
			}
			sendTo = "/APP/Finance/updCostType.jsp";
		}		
		//-----------------------------------------------------------------
		//   BUILD INFORMATION - AND REPORTS
		//-----------------------------------------------------------------
		if (requestType.equals("bldStateFees") ||   // Choose the Dates to Build From
			requestType.equals("addStateFees") ||   // Call RPG to Build the Records for the File
			requestType.equals("rptStateFees") ||
			requestType.equals("bldReportStateFees"))     
		{	
			BldFinance bf = new BldFinance();
			bf.populate(request);
			bf.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			bf.validate();
			if (!bf.getDisplayMessage().equals(""))
			{
				if (requestType.equals("addStateFees"))
				{
				    requestType = "bldStateFees";
				    bf.setRequestType("bldStateFees");
				}
				if (requestType.equals("bldReportStateFees"))
				{
					requestType = "rptStateFees";
					bf.setRequestType("rptStateFees");
				}
			}
			request.setAttribute("requestType", requestType);
			request.setAttribute("bldViewBean", bf);
			if (requestType.equals("addStateFees") ||
				requestType.equals("bldReportStateFees"))
			{
			   pageBld(request, response);
			}
			if (requestType.equals("rptStateFees") ||
				requestType.equals("bldReportStateFees"))
			{
				sendTo = "/view/finance/rptStateFees.jsp";
			}
			else
			    sendTo = "/view/finance/bldStateFees.jsp";
		}	
		if (requestType.equals("bldCostingReportFiles"))     
		{	
			BldFinance bf = new BldFinance();
			bf.populate(request);
			bf.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			bf.validate();
			request.setAttribute("requestType", requestType);
			request.setAttribute("bldViewBean", bf);
			if (!bf.getBldBothButton().trim().equals("") ||
				!bf.getBldNextYearButton().trim().equals("") ||
				!bf.getBldCurrentYearButton().trim().equals(""))
			{
				pageBld(request, response);
			}
		    sendTo = "/view/finance/bldCostingReportFiles.jsp";
		}		
		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//-----------------------------------------------------------------
		// Main Inquiry and List Pages
//		if (requestType.equals("inquiry")) {
		//		}
		
		//-----------------------------------------------------------------
		//   DETAIL PAGE
		//		NOT Going to have a DETAIL Page Section for NEW ITEM
		//-----------------------------------------------------------------
		//		if (requestType.equals("detail")) {
		//		}

		//-----------------------------------------------------------------
		//  Go to the JSP
		//-----------------------------------------------------------------
		try { // Send down to the JSP
			getServletConfig().getServletContext().getRequestDispatcher(sendTo)
					.forward(request, response);
		} catch (Throwable theException) {
			
			System.out.println("---------------------");
			System.out.println("CtlFinance Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
			ParameterMessageBean.printParameters(request);
			System.out.println("---------------------");
			System.out.println(theException);
			theException.printStackTrace();
		}
		return;
		
		
		
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageInquiry(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageInq(HttpServletRequest request, HttpServletResponse response) {
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageList(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageList(HttpServletRequest request, HttpServletResponse response) 
	{
		
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageUpd(HttpServletRequest request, HttpServletResponse response) {
		UpdFinance uf = new UpdFinance();
		try
		{
			uf = (UpdFinance) request.getAttribute("updViewBean");
			Vector parms = new Vector();
			parms.addElement(uf);
			ServiceCostTypes.buildCompanyCost("", parms);
		}
		catch(Exception e)
		{
			uf.setDisplayMessage(e.toString());
		}
		if (uf.getDisplayMessage().trim().equals(""))
			uf.setDisplayMessage("Cost Type " + uf.getToCostType() + "For Date: " + uf.getToCostDate() + " Has Been Recalculated and Updated");
		request.setAttribute("updViewBean", uf);
	}

	/* 
	 * Will use to Build Reports, calling to RPG
	 * (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageBld(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageBld(HttpServletRequest request, HttpServletResponse response) {
		String requestType = (String) request.getAttribute("requestType");
		if (requestType.equals("addStateFees") ||
			requestType.equals("bldReportStateFees"))
		{	
			BldFinance bf = new BldFinance();
			try
			{
				bf = (BldFinance) request.getAttribute("bldViewBean");
				bf = ServiceFinance.callRPG(bf);
			}
			catch(Exception e)
			{
				bf.setDisplayMessage("Problem when calling Program: " + e.toString());
			}
			request.setAttribute("bldViewBean", bf);
		}
		if (requestType.equals("bldCostingReportFiles"))
		{	
			BldFinance bf = new BldFinance();
			try
			{
				bf = (BldFinance) request.getAttribute("bldViewBean");
				if (!bf.getBldBothButton().trim().equals(""))
				{
					try
					{
						//System.out.println("Called Both");
						ServiceFinanceWorkFiles.rebuildCostingWorkFilesForCurrentAndNextYear("100");
					}
					catch(Exception e)
					{
						System.out.println("Problem when calling Programs to Rebuild Both This Year and Last Year of the Costing Report Files: " + e.toString());
						bf.setDisplayMessage("Problem when calling Programs to Rebuild Both This Year and Last Year of the Costing Report Files: " + e.toString());
					}
				}
				if (!bf.getBldCurrentYearButton().trim().equals(""))
				{
					try
					{
						//System.out.println("Called Current");
						ServiceFinanceWorkFiles.rebuildCostingWorkFilesForCurrentYear("100");
					}
					catch(Exception e)
					{
						System.out.println("Problem when calling Programs to Rebuild Current Year of the Costing Report Files: " + e.toString());
						bf.setDisplayMessage("Problem when calling Programs to Rebuild Current Year of the Costing Report Files: " + e.toString());
					}
				}
				if (!bf.getBldNextYearButton().trim().equals(""))
				{
					try
					{
						//System.out.println("Called Next Year");
						ServiceFinanceWorkFiles.rebuildCostingWorkFilesForNextYear("100");
					}
					catch(Exception e)
					{
						System.out.println("Problem when calling Programs to Rebuild Next Year of the Costing Report Files: " + e.toString());
						bf.setDisplayMessage("Problem when calling Programs to Rebuild Next Year of the Costing Report Files: " + e.toString());
					}
				}
			}
			catch(Exception e)
			{
				System.out.println("Problem when calling Programs to Rebuild the Costing Report Files: " + e.toString());
				bf.setDisplayMessage("Problem when calling Programs to Rebuild the Costing Report Files: " + e.toString());
			}
			request.setAttribute("bldViewBean", bf);
		}
	}
}
