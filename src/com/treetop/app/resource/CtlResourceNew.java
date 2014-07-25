/*
 * Created on Jul 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.app.resource;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.treetop.Email;
import com.treetop.SessionVariables;
import com.treetop.services.*;
import com.treetop.app.CtlKeyValues;
import com.treetop.app.function.CtlFunction;
import com.treetop.businessobjects.*;

import com.treetop.servlets.*;
import com.treetop.utilities.html.*;
import com.treetop.viewbeans.ParameterMessageBean;

/**
 * @author twalto
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CtlResourceNew extends BaseServlet {

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
			requestType = "inquiry";
		request.setAttribute("requestType", requestType);
		request.setAttribute("msg", "");
		//TESTING//
		//requestType = "inquiryAvailable";
		//------------------------------------------------------------------
		// Call Security
		String urlAddress = "/web/CtlResourceNew";
		//if (requestType.equals("updateAging"))
		//	urlAddress = "/web/CtlResource?requestType=updateAging";

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
		String sendTo = "/APP/Resource/inqNewItem.jsp";
		//*********************************
		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//-----------------------------------------------------------------
		// Main Inquiry and List Pages
		if (requestType.equals("inquiry") || requestType.equals("list")) {
			InqReserveNumbers irn = new InqReserveNumbers();
			irn.populate(request);
			List errors = irn.validate();
			request.setAttribute("inqViewBean", irn);
			if (requestType.equals("list")) {
				if (errors == null || errors.size() == 0) {
					pageList(request, response);
					sendTo = "/APP/Resource/listNewItem.jsp";
				} else {
					requestType = "inquiry";
				}
			}
			if (requestType.equals("inquiry"))
				pageInq(request, response);
		}
		// Inquiry and List Pages for Available Resources
		String resource = "";
		String resourceType = "";
		if (requestType.equals("addSendAvailable")) {
			InqAvailableResources iar = new InqAvailableResources();
			iar.populate(request);
			List errors = iar.validate();
			if (errors != null && errors.size() > 0) {
				request.setAttribute("msg", errors.toString());
				sendTo = "/APP/Resource/inqAvailableResources.jsp"; // List page
																	// is
				requestType = "listAvailable";
			} else {
				resource = iar.getResourceClass() + iar.getResourceEnd();
				requestType = "add";
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
		if (requestType.equals("update") || requestType.equals("add")) {
			UpdNewItem urn = new UpdNewItem();
			urn.populate(request);
			urn.validate();
			if (urn.getResource().equals("") && !resource.equals(""))
			{	
				urn.setResource(resource);
				urn.setResourceType(urn.getGroup());
			}	
			request.setAttribute("updViewBean", urn);
			if (urn.getErrorOnScreen().equals(""))
			  pageUpd(request, response);
			urn = (UpdNewItem) request.getAttribute("updViewBean");
			requestType = urn.getRequestType();
			request.setAttribute("msg", urn.getErrorOnScreen());
			if (urn.getResource().equals(""))
				sendTo = "/APP/Resource/selResource.jsp?requestType=update&location=/web/CtlResourceNew&showHeading=Y";
			else
				sendTo = "/APP/Resource/updNewItem.jsp";
		}
		if (requestType.equals("inquiryAvailable")
				|| requestType.equals("listAvailable")) {
				InqAvailableResources iar = new InqAvailableResources();
				iar.populate(request);
				iar.setRequestType(requestType);
				List errors = iar.validate();
				iar.setErrors(errors);
				request.setAttribute("inqViewBean", iar);
				request.setAttribute("requestType", requestType);
				sendTo = "/APP/Resource/inqAvailableResources.jsp"; // List page is
				// included in
				// the inq page
				if (requestType.equals("listAvailable")) {
					
					if (errors == null || errors.size() == 0) {
						pageList(request, response);
					} else {
						requestType = "inquiryAvailable";
						request.setAttribute("msg", errors.toString());
					}
				}
				if (requestType.equals("inquiryAvailable"))
					pageInq(request, response);
			}
		
		//-----------------------------------------------------------------
		//  Go to the JSP
		//-----------------------------------------------------------------
		try { // Send down to the JSP
			getServletConfig().getServletContext().getRequestDispatcher(sendTo)
					.forward(request, response);
		} catch (Throwable theException) {
			
			System.out.println("---------------------");
			System.out.println("CtlResourceNew Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
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
		String requestType = (String) request.getAttribute("requestType");
		//--------------------------------------------------------------------------------
		if (requestType.equals("inquiry")) {
			InqReserveNumbers irn = (InqReserveNumbers) request
					.getAttribute("inqViewBean");
			irn.setRequestType("list");
			request.setAttribute("inqViewBean", irn);
		}
		//---------------------------------------------------------------------------------
		if (requestType.equals("inquiryAvailable")) {
			InqAvailableResources iar = (InqAvailableResources) request
					.getAttribute("inqViewBean");
			iar.setRequestType("listAvailable");
			request.setAttribute("inqViewBean", iar);
		}
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
		String requestType = (String) request.getAttribute("requestType");
		//--------------------------------------------------------------------------------
		if (requestType.equals("inquiry") || requestType.equals("list")) {

			InqReserveNumbers irn = (InqReserveNumbers) request
					.getAttribute("inqViewBean");
			irn.setRequestType("list");

			Vector listReport = new Vector();
			// report
//			try {
//				listReport = ServiceResource.buildListResourceReservation(irn);
//			} catch (Exception e) {
//				System.out
//						.println("Error caught in CtlResourceNew().pageList(): "
//								+ e);
//			}
//			irn.setListReport(listReport);
			// add any other thing needed, example drop downs.

			request.setAttribute("inqViewBean", irn);
		}
		//--------------------------------------------------------------------------------
		if (requestType.equals("listAvailable")) {

			InqAvailableResources iar = (InqAvailableResources) request
					.getAttribute("inqViewBean");
			iar.setRequestType("listAvailable");

			ResourcesAvailable listOfResources = new ResourcesAvailable();
			// report
			try {
			//	listOfResources = ServiceResource.buildResourcesAvailable(iar
			//			.getInqStartNumber());
			} catch (Exception e) {
				System.out
						.println("Error caught in CtlResourceNew().pageList(): "
								+ e);
			}
			iar.setListOfResources(listOfResources);
			//-------------------------------------------
			//HARD CODE -- WHEN LIST CHANGES...REDO
			DropDownSingle thisRecord = new DropDownSingle();
			thisRecord.setValue("01");
			thisRecord.setDescription("FINISHED GOODS");
			Vector ddList = new Vector();
			ddList.add(thisRecord);
			iar.setDropDownResourceClass(DropDownSingle.buildDropDown(ddList, "resourceClass", "", "None", "B", ""));
			//iar.setDropDownResourceClass(Resource.buildDropDownClass(
			//		"resourceClass", 0, "01", ""));
			// add any other thing needed, example drop downs.

			request.setAttribute("inqViewBean", iar);
		}
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
	
//**		Vector problems = new Vector();
		String goodMessage = "";
		UpdNewItem ur = (UpdNewItem) request.getAttribute("updViewBean");
		if (ur.getResource() != null && 
			!ur.getResource().equals("")) 
		{
			//ONLY if you Click Save
			if (ur.getSaveButton() != null && !ur.getSaveButton().equals("")) 
			{
				ur.setUserProfile(com.treetop.SessionVariables.getSessionttiProfile(request, response));
				try {
//					if (ur.getRequestType().equals("add"))
//						ServiceResource.addResourceReservation(ur);
//					else
//						ServiceResource.updateResourceReservation(ur);
					goodMessage = "Information for Resource: "
							+ ur.getResource() + " has been saved.";
				} catch (Exception e) {
					ur.setErrorOnScreen("** NOT SAVED ** " + e.toString() + "");
				}
				//-----------------------------------------------
				// Go to the Function's and Add/Update information
				// Which would relate to the Responsibility Piece.
				try {
					CtlFunction thisOne = new CtlFunction();
					thisOne.performTask(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//-----------------------------------------------
				//-----------------------------------------------
				// URL's and Comments -- Call the KeyValues Servlet
				//  ONLY on and UPDATE-ADD-DELETE (Saved)
				try {
					CtlKeyValues thisOne = new CtlKeyValues();
					thisOne.performTask(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//-----------------------------------------------
			}
		}
		if (ur.getErrorOnScreen().equals("")) 
		{
			try {
				BeanResource br = new BeanResource();
//				BeanResource br = ServiceResource.buildResourceReservation(ur.getResource(), ur.getResourceType());
				if (ur.getAddNew().equals("") &&
					br.getResourceNewClass().getResourceNumber() == null)
				{
					ur.setRequestType("listAvailable");
				}
				else
				{	
				// So the Responsibilities Section JSP will work - Seperate information
				request.setAttribute("listResponsibilities", br.getResourceNewClass().getFunctions());
				if (br.getResourceNewClass().getResourceDescription() != null && 
					!br.getResourceNewClass().getResourceDescription().equals("")) 
				{
					try {// TEST To See if there are ANY valid Aging Fields.
						 //  BECAUSE if all 0's then Preload the Defaults.
//						if (br.getResourceNewClass().getBestByNonSaleableHiLimit().equals("0") && 
//							br.getResourceNewClass().getBestByNonSaleableLowLimit().equals("0") && 
//							br.getResourceNewClass().getBestBySalvageHiLimit().equals("0") && 
//							br.getResourceNewClass().getBestBySalvageLowLimit().equals("0")	&& 
//							br.getResourceNewClass().getBestByCriticalHiLimit().equals("0")	&& 
//							br.getResourceNewClass().getBestByCriticalLowLimit().equals("0") && 
//							br.getResourceNewClass().getBestByWatchHiLimit().equals("0") && 
//							br.getResourceNewClass().getBestByWatchLowLimit().equals("0") && 
//							br.getResourceNewClass().getBestByOtherHiLimit().equals("0") && 
//							br.getResourceNewClass().getBestByOtherLowLimit().equals("0") && 
//							br.getResourceNewClass().getNonBestByExtraHiLimit().equals("0") && 
//							br.getResourceNewClass().getNonBestByExtraLowLimit().equals("0") && 
//							br.getResourceNewClass().getNonBestBySalvageHiLimit().equals("0") && 
//							br.getResourceNewClass().getNonBestBySalvageLowLimit().equals("0") && 
//							br.getResourceNewClass().getNonBestByCriticalHiLimit().equals("0") && 
//							br.getResourceNewClass().getNonBestByCriticalLowLimit().equals("0") && 
//							br.getResourceNewClass().getNonBestByWatchHiLimit().equals("0") && 
//							br.getResourceNewClass().getNonBestByWatchLowLimit().equals("0") && 
//							br.getResourceNewClass().getNonBestByOtherHiLimit().equals("0") && 
//							br.getResourceNewClass().getNonBestByOtherLowLimit().equals("0") && 
//							br.getResourceNewClass().getBestByDaysToQahd().equals("0") && 
//							br.getResourceNewClass().getNonBestByDaysToQahd().equals("0"))
							  //br.setResourceClass(ServiceResource.retrieveAgingDefaults(br.getResourceNewClass()));
					} catch (Exception e) {
						// Just have to Catch Error..
						// Means there is a problem with retrieving Aging Fields.
					}
					ur.loadUpdNewItemFromBeanResource(br);
					if (ur.getProjectOwner().equals(""))
					   ur.setProjectOwner(SessionVariables.getSessionttiProfile(request, response));
					if (!ur.getRequestType().equals("listAvailable"))
					  ur.setRequestType("update");
					if (!ur.getPalletGTIN().equals("")) {
						try { // GET THE FAMILY TREE
							Vector returnedList = ServiceGTIN.buildViews(ur.getPalletGTIN());
							request.setAttribute("familyTree", returnedList);
						} catch (Exception e) {
							// There is a problem when retrieving the Parent
							// Child - Family Tree
						}
					}
				}
				}
			} catch (Exception e) {
				// Caught error, with building of the Resource Reservation
				// Information
			}
	
			if (ur.getResourceDescription() == null ||
				ur.getResourceDescription().equals("")) {
				String testSecurity = UpdNewItem.getSecurity(request, response);
				if (testSecurity.equals("N") && 
					!ur.getResource().equals("")) 
				{
					ur.setResource("");
					ur.setErrorOnScreen("You are not allowed to ADD a NEW Resource, Please try an existing resource.");
				} else
					if (!ur.getRequestType().equals("listAvailable"))
					  ur.setRequestType("add");
			}
			//-------------------------------------------------
			// add any other thing needed, drop downs.
			try {
				ur.setDropDownGS1CompanyPrefix(ServiceGTIN.buildDropDownGS1CompanyPrefix());
			} catch (Exception e) {
				System.out.println("Exception caught: " + e);
			}
//** CHANGES			
//			String imageList[] = HTMLHelpersLinks.getDirectoryFromPath("\\\\10.6.100.6\\x\\ProductInfo\\Resource\\"
//															+ ur.getResource().trim() + "");
//		 12/20/06 tw - Could not seem to make this work, will work on it at a later time
//       Changed the view to go to the Folder as a link instead of displaying what is in the folder.		
//			String imageList[] = HTMLHelpersLinks.getDirectoryFromPathAS400("/Network/ProductInfo/Resource/"
//															+ ur.getResource().trim() + "/");
//			request.setAttribute("imageList", imageList);
			request.setAttribute("displayImagePath", ("X:\\ProductInfo\\Resource\\" + ur.getResource().trim() + "\\"));
		
			if (ur.getErrorOnScreen().equals("") &&
				!goodMessage.equals(""))
				ur.setErrorOnScreen(goodMessage);
		}
		request.setAttribute("updViewBean", ur);

	}}
