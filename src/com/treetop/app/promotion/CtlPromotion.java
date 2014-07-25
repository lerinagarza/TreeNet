/*
 * Created on December 3, 2007
 *    Author Teri Walton
 * 
 * Will be used to retrieve / display information associated to Tree Top Promotions
 * 
 */
package com.treetop.app.promotion;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ibm.as400.access.*;
import com.treetop.SessionVariables;
import com.treetop.servlets.BaseServlet;
import com.treetop.viewbeans.ParameterMessageBean;
import com.treetop.services.ServicePromotion;

/**
 * @author twalto
 * 
 */
public class CtlPromotion extends BaseServlet {

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
			requestType = "getPromo";
	//	requestType = "detail";
		request.setAttribute("requestType", requestType);
		request.setAttribute("msg", "");
		//TESTING//
		//requestType = "inquiryAvailable";
		//------------------------------------------------------------------
		// Call Security
		String urlAddress = "/web/CtlPromotion";

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
		String sendTo = "/APP/Promotion/displayPromo.jsp";
		//*********************************
		//-----------------------------------------------------------------
		//   Retrieve a NEW Promotion Number
		//-----------------------------------------------------------------
		// Retrieve and Display a New Promotion Number
		if (requestType.equals("getPromo")) 
		{
			InqPromotion ip = new InqPromotion();
			ip.populate(request);
			// Validation will set the Defaults where needed
			ip.validate(); 
			request.setAttribute("inqViewBean", ip);
			pageDisplayPromo(request);
			ip = (InqPromotion) request.getAttribute("inqViewBean");
//			System.out.println("STOP");
		}
		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//-----------------------------------------------------------------
		// Main Inquiry and List Pages
		//if (requestType.equals("inquiry") || requestType.equals("list")) {
      //		}
		//-----------------------------------------------------------------
		//   DETAIL PAGE
		//		NOT Going to have a DETAIL Page Section for NEW ITEM
		//-----------------------------------------------------------------
		if (requestType.equals("detail")) {
			InqPromotion ip = new InqPromotion();
			ip.populate(request);
		// 	Validation will set the Defaults where needed
			ip.validate(); 
	
			request.setAttribute("inqViewBean", ip);
			pageDtl(request, response);
	//		ip = (InqPromotion) request.getAttribute("inqViewBean");
//				System.out.println("STOP");
			sendTo = "/APP/Promotion/detailPromo.jsp";	
		}
		//-----------------------------------------------------------------
		//   UPDATE PAGE
		//-----------------------------------------------------------------
		//if (requestType.equals("update")) {
		//}
		//-----------------------------------------------------------------
		//  Go to the JSP
		//-----------------------------------------------------------------
		try { // Send down to the JSP
			getServletConfig().getServletContext().getRequestDispatcher(sendTo)
					.forward(request, response);
		} catch (Throwable theException) {
			
			System.out.println("---------------------");
			System.out.println("CtlPromotion Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
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
// Left empty for now, not using yet
	}

	/*
	 **** Should be moved to a SERVICE if more Promotion information is gathered ***
	 *
	 * Retrieve and Display a New Promo
	 *    Calling an RPG program PAR343 to do this
	 */
	protected void pageDisplayPromo(HttpServletRequest request) {
		try
		{
			
			InqPromotion ip = (InqPromotion) request.getAttribute("inqViewBean");
			ServicePromotion thisTime = new ServicePromotion();
			// Call RPG Program
			ip = thisTime.callRPGPromotionNumber(ip);

			request.setAttribute("inqViewBean", ip);
		}
		catch(Exception e)
		{
			System.out.println("Problem when retreiving Promotion Number: " + e);
		}
		return;
	}

	/*
	 * Retrieve and Build everthing needed for the Update Page
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
protected void pageUpd(HttpServletRequest request,
			HttpServletResponse response) {
// left empty for now -- Not using yet
	}

/*
 * Retrieve and Build everthing needed for the Detail Page
 * 
 */
protected void pageDtl(HttpServletRequest request,
		HttpServletResponse response) {
	try
	{
	  InqPromotion ip = (InqPromotion) request.getAttribute("inqViewBean");
	  String requestType = ip.getRequestType();
	  if (requestType.equals("detail"))
	  {	
	  	Vector toSend = new Vector();
	  	toSend.addElement(ServicePromotion.buildPromotionDetail(ip));
		ip.setListReport(toSend);
	  }
	  request.setAttribute("inqViewBean", ip);
	}
	catch(Exception e)
	{
		System.out.println("Problem found in CtlPromotion.pageDtl(request): " + e);
		ParameterMessageBean.printParameters(request);
	}
}
}
