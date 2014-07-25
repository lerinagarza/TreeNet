/*
 * Created on May 12, 2008
 *
 */
package com.treetop.app.inventory;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.treetop.Email;
import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanInventory;
import com.treetop.businessobjects.*;
import com.treetop.servlets.BaseServlet;
import com.treetop.services.*;
import com.treetop.viewbeans.ParameterMessageBean;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.UtilityDateTime;

/**
 * @author twalto
 * 
 */
public class CtlInventoryNew extends BaseServlet {


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

		//System.out.println(GeneralUtility.printRequestParameters(request));
		
		String requestType = request.getParameter("requestType");
		if (requestType == null)
			requestType = "inqFIFO";
		//requestType = "updProduction";
		request.setAttribute("requestType", requestType);
		request.setAttribute("msg", "");
		//TESTING// 
		//requestType = "inquiryAvailable";
		//------------------------------------------------------------------
		// Call Security
		String urlAddress = "/web/CtlInventoryNew?requestType=inqAcid";
		//if (requestType.equals("updateAging"))
		//	urlAddress = "/web/CtlResource?requestType=updateAging";
		if (requestType.equals("updateIncubationStatus"))
			urlAddress = "/web/CtlInventoryNew?requestType=updateIncubationStatus";
		if (requestType.equals("insertSnapshot"))
			urlAddress = "/web/CtlInventoryNew?requestType=insertSnapshot";
		if (requestType.equals("inqLotReclass") 
				|| requestType.equals("listLotReclass") 
				|| requestType.equals("updLotReclass"))
			urlAddress = "/web/CtlInventoryNew?requestType=inqLotReclass";

		if (requestType.equals("inqLotAttribute") || 
			requestType.equals("listLotAttribute") ||
			requestType.equals("updLotAttribute")	) {
			urlAddress = "/web/CtlInventoryNew?requestType=inqLotAttribute";
		}

		if (requestType.equals("inqLotReclass")) {
			
			urlAddress = "/web/CtlInventoryNew?requestType=inqLotReclass";
			
		}

		if (requestType.equals("updProduction"))
			urlAddress = "/web/CtlInventoryNew?requestType=updProduction";
		
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
		
		String environment = request.getParameter("environment");
		if (environment == null || environment.equals("")) {
			environment = "PRD";
		}

		
		String viewPath = "/APP/Inventory/";
		if (requestType.equals("updProduction") 
				|| requestType.equals("inqLotReclass")
				|| requestType.equals("listLotReclass")
				|| requestType.equals("updLotReclass")
				
				|| requestType.equals("inqLotAttribute")
				|| requestType.equals("listLotAttribute")
				|| requestType.equals("updLotAttribute")) {
			viewPath = "/view/inventory/";
		}

		
		// Default Screen
		String sendTo = viewPath + "inqFIFO.jsp";
		//*********************************
		//-----------------------------------------------------------------
		//   DETAIL PAGE
		//		NOT Going to have a DETAIL Page Section for NEW ITEM
		//-----------------------------------------------------------------
		//		if (requestType.equals("detail")) {
		//		}
		//-----------------------------------------------------------------
		//   UPDATE PAGE
		//-----------------------------------------------------------------
		if (requestType.equals("updateIncubationStatus") ||
				requestType.equals("insertSnapshot")) 
		{
			UpdInventory ui = new UpdInventory();
			ui.populate(request);
			ui.validate();
			ui.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			request.setAttribute("updViewBean", ui);
			if (!ui.getGoButton().trim().equals("") &&
					ui.getDisplayErrors().trim().equals(""))
			{	
				ui.setUserAuthorization(com.treetop.Security.getUserAuthorization(request));
				pageUpd(request, response);
			}
			if (requestType.equals("updateIncubationStatus"))
				sendTo = viewPath + "updIncubationStatus.jsp";
			if (requestType.equals("insertSnapshot"))
				sendTo = viewPath + "insertSnapshot.jsp";
		}
		if (requestType.equals("updLotReclass"))
		{

			BeanInventory bi = new BeanInventory();
			try
			{
				bi = SessionVariables.getSessionttiBeanInventory(request, response);
				SessionVariables.destroySessionttiBeanInventory(request, response);
			}
			catch(Exception e)
			{}
			if (bi.getByItemVectorOfInventory() != null &&
					bi.getByItemVectorOfInventory().size() > 0)
			{
				UpdInventory ui = new UpdInventory();
				ui.populate(request);
				ui.validate();
				ui.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
				ui.setBeanInventory(bi);
				ui.updateListLots(request);
				request.setAttribute("updViewBean", ui);
				
	
				
				if (!ui.getGoButton().trim().equals("") &&
						ui.getDisplayErrors().trim().equals(""))
				{	
					pageUpd(request, response);
					sendTo = "/view/inventory/updLotReclass.jsp";
				}
				else
					requestType = "inqLotReclass";
			}
			else
				requestType = "inqLotReclass";
		}

		if (requestType.equals("updLotAttribute")) {

			InqInventory ii = new InqInventory(request);
			UpdAttribute ua = new UpdAttribute(request);

			

			ua.validate();
			if (ua.getErrorMessage().trim().equals("")) {
				//No errors
				if (ua.getSubmit().trim().equals("")) {
					//submit button was not pressed 
					//do not process, go back to inquiry page
					requestType = "inqLotAttribute";
					request.setAttribute("requestType", requestType);
				} else {
					//process the update
					request.setAttribute("viewBean", ua);
					pageUpd(request, response);

					//done processing, go back to inquiry page
					requestType = "inqLotAttribute";
					request.setAttribute("requestType", requestType);
				}

			} else {
				//error found, do not process, to back to update/list page
				request.setAttribute("updViewBean", ua);
				request.setAttribute("viewbean", ii);
				requestType = "listLotAttribute";
				request.setAttribute("requestType", requestType);
				
			}
		}

		if (requestType.equals("updProduction")) 
		{
			UpdProduction up = new UpdProduction();
			up.populate(request);
			
			if (!up.getMOButton.trim().equals("")) {
				up.validate();
			}
			
			up.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			up.setAuthorization(request.getHeader("Authorization"));
			
			if (!up.getProcessLotsButton().trim().equals("")) {
				up.populateLots(request);
			}
			
			request.setAttribute("updViewBean", up);
			
			if (up.getMOError.trim().equals("")
				&& (
						!up.getMOButton.trim().equals("") 
						|| !up.getProcessLotsButton().trim().equals("")
					)
				){
				
				pageUpdProduction(request, response);
			}
			sendTo = viewPath + "updProduction.jsp";
		}
		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//-----------------------------------------------------------------
		if (requestType.equals("inqFIFO") || requestType.equals("listFIFO")) {
			InqInventory ii = new InqInventory();
			ii.populate(request);
			ii.validate();
			request.setAttribute("inqViewBean", ii);
			if (requestType.equals("listFIFO")) {
				if (ii.getDisplayErrors() == null || ii.getDisplayErrors().trim().equals("")) 
				{
					pageList(request, response);
					ii = (InqInventory) request.getAttribute("inqViewBean");
					if (!ii.getDisplayErrors().trim().equals(""))
						requestType = "inqFIFO";
					else
						sendTo = viewPath + "listFIFO.jsp";
				} else 
					requestType = "inqFIFO";
			}
			if (requestType.equals("inqFIFO"))
			{
				//	pageInq(request, response);
				sendTo = viewPath + "inqFIFO.jsp";
			}
		}
		if (requestType.equals("inqAcid") || 
				requestType.equals("listAcid")) {
			InqInventory ii = new InqInventory();
			ii.populate(request);
			ii.validate();
			ii.setRequestType("listAcid");
			request.setAttribute("inqViewBean", ii);
			if (requestType.equals("listAcid") || requestType.equals("inqAcid")) {
				if (ii.getDisplayErrors() == null || 
						ii.getDisplayErrors().trim().equals("")) 
				{
					pageList(request, response);
					ii = (InqInventory) request.getAttribute("inqViewBean");
					if (!ii.getDisplayErrors().trim().equals(""))
						requestType = "inqAcid";
					else
						sendTo = "/view/inventory/listAcid.jsp";
				} else 
					requestType = "inqAcid";
			}
		}
		if (requestType.equals("inqLotReclass") ||
				requestType.equals("listLotReclass")) {
						
			InqInventory ii = new InqInventory();
			ii.populate(request);
			
			
			try
			{ // determine if additional filters should be added
			    String[] rolesR = SessionVariables.getSessionttiUserRoles(request, response);
			    for (int xr = 0; xr < rolesR.length; xr++)
			    {
			       if (rolesR[xr].equals("13"))// reflective of Ocean Spray Henderson
			          ii.setFacilityFilter("384"); 
			    }
			 }
			 catch(Exception e)
			 {}
			
			ii.validate();
			ii.setRequestType(requestType);
			request.setAttribute("inqViewBean", ii);
			if (requestType.equals("listLotReclass") ||
					ii.getDisplayErrors().equals(""))
			{
				pageList(request, response);	
				ii = (InqInventory) request.getAttribute("inqViewBean");
				if (!ii.getDisplayErrors().trim().equals(""))
					requestType = "inqLotReclass";
			}
			if (requestType.equals("inqLotReclass"))
				sendTo = viewPath + "inqLotReclass.jsp";
			else
			{
				sendTo = viewPath + "listLotReclass.jsp";
				// IF this is going to the LIST page, 
				//    The InqInventory Class needs to be set as a Session Variable
				SessionVariables.setSessionttiBeanInventory(request, response, ii.getBeanInventory());
			}
		}

		if (requestType.equals("inqLotAttribute") || requestType.equals("listLotAttribute")) {
			
			InqInventory ii = (InqInventory) request.getAttribute("viewbean");
			if (ii == null) {
				ii = new InqInventory();
				ii.populate(request);
			}
			
			
			if (requestType.equals("listLotAttribute")) {
				ii.validate();
			}
			if (requestType.equals("inqLotAttribute")) {
				BeanInventory bean = SessionVariables.getSessionttiBeanInventory(request, response);

				if (bean != null && !bean.getReturnMessage().trim().equals("")) {
					String displayMessage = bean.getReturnMessage();
					ii.setDisplayMessage(displayMessage);
				}

			}

			ii.setRequestType(requestType);
			request.setAttribute("inqViewBean", ii);

			//get list of lots
			if (requestType.equals("listLotAttribute") && ii.getDisplayErrors().equals("")) {
				pageList(request, response);	
				ii = (InqInventory) request.getAttribute("inqViewBean");

				if (!ii.getDisplayErrors().trim().equals("")) {
					requestType = "inqLotReclass";
				}

			}

			//choose which page to go to
			if (requestType.equals("inqLotAttribute")) {
				sendTo = viewPath + "inqLotAttribute.jsp";
			} else {
				sendTo = viewPath + "listLotAttribute.jsp";
				// IF this is going to the LIST page, 
				//    The InqInventory Class needs to be set as a Session Variable
				SessionVariables.setSessionttiBeanInventory(request, response, ii.getBeanInventory());
			}

		}


		if (requestType.equals("inqRawFruit") ||
				requestType.equals("listRawFruit")) {
			InqInventory ii = new InqInventory();
			ii.populate(request);
			ii.validate();
			ii.setRequestType(requestType);
			request.setAttribute("inqViewBean", ii);
			if (requestType.equals("listRawFruit") &&
					ii.getDisplayErrors().equals(""))
			{
				pageList(request, response);
				sendTo = "/view/inventory/listRawFruit.jsp";
			}
			else
			{
				sendTo = "/view/inventory/inqRawFruit.jsp";
			}
		}
		//-----------------------------------------------------------------
		//  Go to the JSP
		//-----------------------------------------------------------------
		try { // Send down to the JSP
			getServletConfig().getServletContext().getRequestDispatcher(sendTo)
			.forward(request, response);
		} catch (Throwable theException) {

			System.out.println("---------------------");
			System.out.println("CtlInventoryNew Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
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
		// List of Inventory --
		InqInventory inqInv = (InqInventory) request.getAttribute("inqViewBean");
		try
		{
			if (inqInv.getRequestType().equals("listFIFO"))
			{	
				inqInv.setBeanInventory(ServiceInventory.buildFIFOList("", inqInv));
				if (inqInv.getBeanInventory().getListSOLineItems().size() == 0)
					inqInv.setDisplayErrors("There are no Line Items for Customer Orders: " + inqInv.getInqCustomerOrder());
				else
				{
					if (inqInv.getBeanInventory().getByItemVectorOfInventory().size() == 0)
						inqInv.setDisplayErrors("There is no Valid Inventory (based on the criteria) for this Customer Order: " + inqInv.getInqCustomerOrder());
				}
			}
			if (inqInv.getRequestType().equals("listAcid"))
			{	
				Vector parms = new Vector();
				parms.addElement(inqInv);
				inqInv.setBeanInventory(ServiceInventory.listAverageAcid(parms));
			}
			if (inqInv.getRequestType().equals("listLotReclass"))
			{	
				Vector parms = new Vector();
				//TESTING
				parms.addElement(inqInv);
				inqInv.setBeanInventory(ServiceInventory.listLotsToReclassify(parms));
			}

			if (inqInv.getRequestType().equals("listLotAttribute"))
			{	
				Vector parms = new Vector();
				parms.addElement(inqInv);

				//get the list of inventory
				BeanInventory bean = new BeanInventory();
				try {
					bean = ServiceInventory.listLotsToUpdateAttributes(parms);
				} catch (Exception e) {
					// TODO: handle exception
					inqInv.setInqStatus(e.toString());
				}

				//get the first lot and get the attributes for it
				Vector<Inventory> lots = bean.getByItemVectorOfInventory();
				if (lots.size() > 0) {
					Inventory lot = lots.elementAt(0);

					Vector<AttributeValue> attributes = new Vector<AttributeValue>();
					if (lot != null) {
						attributes = ServiceAttribute.findAttributeValues(inqInv.getEnvironment(), lot);
					}
					bean.setListAttributes(attributes);
				}

				inqInv.setBeanInventory(bean);



			}

			if (inqInv.getRequestType().equals("listRawFruit"))
			{	
				Vector parms = new Vector();
				parms.addElement(inqInv);
				inqInv.setBeanInventory(ServiceInventory.listRawFruit(parms));
			}
		}
		catch(Exception e)
		{
			inqInv.setDisplayErrors("Problem building information: " + e);
		}
		request.setAttribute("inqViewBean", inqInv);	
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
		try
		{
			String requestType = (String) request.getAttribute("requestType");

			UpdInventory updInv = (UpdInventory) request.getAttribute("updViewBean");

			if (updInv == null) {
				updInv = new UpdInventory();
			}

			if (updInv.getRequestType().equals("updateIncubationStatus"))
			{
				BeanInventory bi = ServiceInventory.updateIncubationStatus(updInv);
				updInv.setDisplayMessage(bi.getReturnMessage());
			}

			if (updInv.getRequestType().equals("insertSnapshot"))
			{
				try
				{
					ServiceInventory.inventorySnapshot(updInv);
					updInv.setDisplayMessage("A snapshot has been taken of this inventory. ");
				}
				catch(Exception e)
				{
					// Catch Thrown errors
					updInv.setDisplayMessage("Problem Occurred taking a snapshot of the inventory: " + e);
				}
			}
			if (updInv.getRequestType().equals("updLotReclass"))
			{
				
				try
				{
					// Process ONE element at a time.
					BeanInventory bi = updInv.getBeanInventory();
					Vector newListOfLots = new Vector();
					for (int x = 0; x < bi.getByItemVectorOfInventory().size(); x++)
					{
						Inventory i = (Inventory) bi.getByItemVectorOfInventory().elementAt(x);
						try
						{
							// First Figure out if this element will be processed....
							// Only process elements that the line has been checkmarked.
							if (!i.getCheckedValue().equals(""))
							{
								
								Vector sendingParms = new Vector();
								// User
								sendingParms.add(updInv.getUpdateUser());
								// From Status
								sendingParms.add(updInv.getInqStatusFrom());
								// To Status
								sendingParms.add(updInv.getInqStatusTo());
								// Inventory Business Object
								sendingParms.add(i);
								// return back the Inventory Class - 
								//	comment filled with message from API Transaction
								sendingParms.add(updInv);
								
								//send in the request header authorization to execute the API with 
								//the current user's profile and password
								
								
								sendingParms.add(com.treetop.Security.getUserAuthorization(request));
								// for "TST"
								//sendingParms.add("Basic amhhZ2xlOnZ5MXVndw==");
								
								i = ServiceInventory.updateInventoryStatus(sendingParms);
								
								
								if (!updInv.getRemark().trim().equals("")) {
									Inventory i2 = ServiceInventory.updateInventoryLotComment(sendingParms);
									//if the two responses are the same, just go with the original one
									//otherwise, add the second one to the first
									if (!i.getComment().trim().equals(i2.getComment().trim())) {
										i.setComment("Reclass:  " + i.getComment().trim() + "<br>" + 
												"Hold Comments: " + i2.getComment().trim());
									}
									
								}
							}
						}
						catch(Exception e)
						{}
						newListOfLots.add(i);	
					}
					bi.setByItemVectorOfInventory(newListOfLots);
					updInv.setBeanInventory(bi);
					
					String environment = request.getParameter("environment");
					if (environment == null || environment.equals("")) {
						environment = "PRD";
					}
					updInv.setEnvironment(environment);
					Vector sendLots = new Vector();
					sendLots.add(updInv);
					//Also need to update the Tagging Type and The Disposition
					ServiceLot.updateFields(sendLots);
				}
				catch(Exception e)
				{
					// Only used to catch problems.
				}
			}

			if (requestType.equals("updLotAttribute")) {
				UpdAttribute ua = (UpdAttribute) request.getAttribute("viewBean");
				BeanInventory bean = new BeanInventory();
				try {
					
					String authorization = com.treetop.Security.getUserAuthorization(request);
					String userProfile = com.treetop.Security.getProfile(authorization);
					
					ua.setAuthorization(authorization);
					ua.setUserProfile(userProfile);
					
					bean = ServiceAttribute.updateAttributes(ua);
				} catch (Exception e) {
					System.out.println(e);
				}
				
				if (ua.getErrorMessage().trim().equals("")) {
					bean.setReturnMessage("Attributes Updated");
				} else {
					bean.setReturnMessage(ua.getErrorMessage());
				}
				
				
				SessionVariables.setSessionttiBeanInventory(request, response, bean);
			}

			request.setAttribute("updViewBean", updInv);
		}
		catch(Exception e)
		{
		}

	}

	/*
	 * Retrieve and Build everything needed for the Update Page as it relates to Production
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void pageUpdProduction(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
			
			UpdProduction updProd = (UpdProduction) request.getAttribute("updViewBean");
			String requestType = (String) request.getAttribute("requestType");
			if (requestType.equals("updProduction")) {
				
				
				if (!updProd.getProcessLotsButton().equals("") &&
						updProd.getProcessLotsError().equals("")) {
					
					
					
					try {

						ServiceManufacturingOrder.processProduction(updProd);
						
					} catch(Exception e) {
						updProd.setDisplayMessage("Error Found Processing Lots:" + e);
					}
					
					
					
				}
				
				Vector sendVB = new Vector();
				sendVB.addElement(updProd);
				BeanInventory bi = new BeanInventory();
				bi.setMoHeader(ServiceManufacturingOrder.findMO(updProd.getEnvironment(), sendVB));
				updProd.loadFromBeanInventory(bi);
				updProd.setUpdBean(bi);	
				
			}	//end if updProduction
			
			request.setAttribute("updViewBean", updProd);
			
			
		} catch(Exception e) {
			System.out.println("Stop and see what the issue is:" + e);
		}
	}

}
