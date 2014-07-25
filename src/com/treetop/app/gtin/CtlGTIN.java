/*
 * Created on Jul 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
 
package com.treetop.app.gtin;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.treetop.SessionVariables;
import com.treetop.utilities.html.HTMLHelpersLinks;
import com.treetop.app.resource.*;
import com.treetop.app.*;
import com.treetop.services.*;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.servlets.*;

/**
 * @author twalto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CtlGTIN extends BaseServlet {
	
	/* Retrieve and Build everthing needed for the Update Page
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageUpd(
		HttpServletRequest request,
		HttpServletResponse response) {

		UpdGTIN ug = (UpdGTIN) request.getAttribute("updViewBean");
		StringBuffer problemMessage = new StringBuffer();
		if (ug.saveButton != null) {
			try {
				ug.setUserProfile(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				// ADD OR COPY 
				
				if (ug.getRequestType().equals("add")
					|| ug.getRequestType().equals("copy")) {
					try {
						BeanGTIN testClass = ServiceGTIN.buildGtinDetail(ug.getGtinNumber());
						if (testClass.getGtin() != null &&
							testClass.getGtin().getGtinNumber() != null)
						{	
							problemMessage.append("This GTIN Number ");
						    problemMessage.append(ug.getGtinNumber());
						    problemMessage.append(
							" has already been used.  Please choose another number.");
						}    
						else
						{
							try
							{
								ServiceGTIN.addGTIN(ug);
							}catch(Exception e)
							{
							}
						}
					} catch (Exception e) {
					}
				}
				// UPDATE
				if (ug.getRequestType().equals("update")) {
				   ServiceGTIN.updateGtinDetail(ug);
				}
			} catch (Exception e) {
				problemMessage.append("GTIN Number: ");
				problemMessage.append(ug.getGtinNumber());
				problemMessage.append(" has NOT been saved.  ");
				problemMessage.append("Problem occurred when trying to ");
				problemMessage.append(ug.getRequestType());
				problemMessage.append(": ");
				problemMessage.append(e);
			}
			//-----------------------------------------------
			// URL's and Comments  -- Call the KeyValues Servlet
			//  ONLY on and UPDATE-ADD-DELETE  (Saved)
			try
			{
				CtlKeyValues thisOne = new CtlKeyValues();
				thisOne.performTask(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//-----------------------------------------------		
		} 
        if (problemMessage.toString().equals("") &&
            !ug.getGtinNumber().equals(""))
        {
			String requestType = ug.getRequestType();
			String gtinNumber  = ug.getGtinNumber();
			ug = new UpdGTIN();
          try
          {
			BeanGTIN mainClass = ServiceGTIN.buildGtinDetail(gtinNumber);
			ug.loadUpdGTINFromBeanGTIN(mainClass);
			if (ug.getRequestType().equals("copy"))
				ug.setGtinNumber("");
			else
				ug.setRequestType("update");
			List errors = ug.validate();
			ug.setErrors(errors);
          }
          catch(Exception e)
          {
          	ug.setRequestType("add");
          }    		
        }
		if (!ug.getGtinNumber().equals("")) {
			try {
				Vector returnedList =
					ServiceGTIN.buildViews(ug.getGtinNumber());
							    
				request.setAttribute("familyTree", returnedList);
			} catch (Exception e) {
				// There is a problem when retrieving the Parent Child - Family Tree
			}
		}
		//------Get Images in the folder for this GTIN
//** CHANGES		
//		String imageList[] = HTMLHelpersLinks.getDirectoryFromPath("\\\\10.6.100.6\\x\\ProductInfo\\GTIN\\" + ug.getGtinNumber().trim() + "");
// 12/20/06 tw - Could not seem to make this work, will work on it at a later time
//               Changed the view to go to the Folder as a link instead of displaying what is in the folder.		
//		String imageList[] = HTMLHelpersLinks.getDirectoryFromPathAS400("/Network/ProductInfo/GTIN/" + ug.getGtinNumber().trim() + "/");
//		request.setAttribute("imageList", imageList);		
		request.setAttribute("displayImagePath", ("X:\\ProductInfo\\GTIN\\" + ug.getGtinNumber().trim() + "\\"));
		
		request.setAttribute("msg", problemMessage.toString());
		request.setAttribute("updViewBean", ug);
		return;
	}

	/* Process incoming HTTP GET requests 
	 * 		Goes directly to the performTask
	 * @see com.treetop.servlets.BaseServlet#doGet()
	 */
	public void doGet(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response)
		throws javax.servlet.ServletException, java.io.IOException {
		performTask(request, response);
	}

	/* Process incoming HTTP POST requests 
	 * 		Goes directly to the performTask
	 * @see com.treetop.servlets.BaseServlet#doPost()
	 */
	public void doPost(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response)
		throws javax.servlet.ServletException, java.io.IOException {
		performTask(request, response);
	}

	/* Tests Parameters, Calls Security, and Directs Traffic between the pages
	 * @see com.treetop.servlets.BaseServlet#performTask(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void performTask(
		HttpServletRequest request,
		HttpServletResponse response) {

		String requestType = request.getParameter("requestType");
		if (requestType == null)
			requestType = "inquiry";
		//		requestType = "updateTies";	
		//------------------------------------------------------------------  
		// Call Security		
		String urlAddress = "/web/CtlGTIN";
		if (requestType.equals("update") ||
		    requestType.equals("add") ||
		    requestType.equals("copy") ||
		    requestType.equals("delete") ||
		    requestType.equals("updateTies"))
		   urlAddress = "/web/CtlGTIN?requestType=update";

		if (!callSecurity(request, response, urlAddress).equals("")) {
			try {
				response.sendRedirect(
					"/servlet/com.treetop.servlets.InqTreeNet?msg="
						+ SessionVariables.getSessionttiSecStatus(
							request,
							response));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		//------------------------------------------------------------------
		//Passed Security
		//*****************************************************************
		// Default Screen
		String sendTo = "/APP/GTIN/inqGTIN.jsp";
		//*****************************************************************
		//-----------------------------------------------------------------
		//   DELETE RECORD
		//-----------------------------------------------------------------
		if (requestType.equals("delete")) {
			UpdGTIN ug = new UpdGTIN();
			ug.populate(request);
			if (!ug.getGtinNumber().equals("")) {
				StringBuffer message = new StringBuffer();
				try {
					BeanGTIN tryDelete = ServiceGTIN.buildGtinDetail(ug.getGtinNumber());
//					tryDelete.delete();
					message.append("GTIN ");
					message.append(ug.getGtinNumber());
					message.append(" has been Deleted.");
				} catch (Exception e) {
					message.append("GTIN ");
					message.append(ug.getGtinNumber());
					message.append(" had a problem when trying to delete.  ");
					message.append(e);
				}
				request.setAttribute("msg", message.toString());
			}
			requestType = "list";
		}

		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//-----------------------------------------------------------------
		if (requestType.equals("inquiry") || requestType.equals("list")) {
			InqGTIN ig = new InqGTIN();
			ig.populate(request);
			ig.setRequestType(requestType);
			List errors = ig.validate();
			request.setAttribute("inqViewBean", ig);
			if (requestType.equals("list")) {
				if (errors == null || errors.size() == 0) {
					pageList(request, response);
					sendTo = "/APP/GTIN/listGTIN.jsp";
				} else {
					requestType = "inquiry";
					//ig.setErrors(errors);
				}
			}
			if (requestType.equals("inquiry"))
				pageInq(request, response);
		}
		//-----------------------------------------------------------------
		//   DETAIL PAGE
		//-----------------------------------------------------------------
		if (requestType.equals("detail")) {
			DtlGTIN dg = new DtlGTIN();
			dg.populate(request);
			List errors = dg.validate();
			request.setAttribute("dtlViewBean", dg);
			pageDtl(request, response);
			sendTo = "/APP/GTIN/dtlGTIN.jsp";
		}
		//-----------------------------------------------------------------
		//   UPDATE PAGES
		//-----------------------------------------------------------------
		if (requestType.equals("update")
			|| requestType.equals("add")
			|| requestType.equals("copy")) {
			UpdGTIN ug = new UpdGTIN();
			ug.populate(request);
			List errors = ug.validate();
			ug.setErrors(errors);
			request.setAttribute("updViewBean", ug);
			if (errors.size() == 0)
				pageUpd(request, response);
			sendTo = "/APP/GTIN/updGTIN.jsp";
		}
		if (requestType.equals("updateTies")) {
			UpdTieToChildren uttc = new UpdTieToChildren();
			uttc.populate(request);
			List errors = uttc.validate();
			uttc.setErrors(errors);
			request.setAttribute("updViewBean", uttc);
			pageUpdTies(request, response);
			sendTo = "/APP/GTIN/updTieToChildren.jsp";
		}
		//-----------------------------------------------------------------
		//  Go to the JSP
		//-----------------------------------------------------------------
		try { // Send down to the JSP
			getServletConfig().getServletContext().getRequestDispatcher(
				sendTo).forward(
				request,
				response);
		} catch (Throwable theException) {
			theException.printStackTrace();
		}
		return;

	}

	/* Retrieve and Build everthing needed for the Inquiry Page
	 * @see com.treetop.servlets.BaseServlet#pageInquiry(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageInq(
		HttpServletRequest request,
		HttpServletResponse response) {

		InqGTIN ig = (InqGTIN) request.getAttribute("inqViewBean");
		ig.setRequestType("list");
		// add any other thing needed, example drop downs.

		request.setAttribute("inqViewBean", ig);
	}

	/* Retrieve and Build everthing needed for the List Page
	 * @see com.treetop.servlets.BaseServlet#pageList(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageList(
		HttpServletRequest request,
		HttpServletResponse response) {

		InqGTIN ig = (InqGTIN) request.getAttribute("inqViewBean");
		ig.setRequestType("list");

		Vector listReport = new Vector();
		try {
			listReport =
				ServiceGTIN.buildGtinList(ig);
		} catch (Exception e) {
			System.out.println("Error caught in CtlGTIN().pageList(): " + e);
		}
		ig.setListReport(listReport);
		request.setAttribute("inqViewBean", ig);

	}

	/* 
	 * Retrieve and Build everthing needed for the Update Page
	 *   Specifically the Update Tie to Children Page
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageUpdTies(
		HttpServletRequest request,
		HttpServletResponse response) {

		UpdTieToChildren uttc =
			(UpdTieToChildren) request.getAttribute("updViewBean");
		StringBuffer problemMessage = new StringBuffer();
		if (uttc.saveButton != null && !uttc.getParentGTIN().equals("") &&
		    uttc.getErrors().size() == 0) {
			uttc.buildGTINFromSeperated();
			//DELETE Information
			try { // Test to see if currently has information
				ServiceGTIN.deleteChildren(uttc.getParentGTIN());
			} catch (Exception e) {
			}
			// ADD Information // ONE Child at a time.
			for (int x = 0; x < 7; x++) {
				try {
					if (x == 0)
					{
						uttc.setChildGTIN(uttc.getChildGTIN1());
						uttc.setChildSequence(uttc.getChildGTIN1Sequence());
						uttc.setChildQuantity(uttc.getChildGTIN1QtyInParent());
					}
					if (x == 1)
					{
						uttc.setChildGTIN(uttc.getChildGTIN2());
						uttc.setChildSequence(uttc.getChildGTIN2Sequence());
						uttc.setChildQuantity(uttc.getChildGTIN2QtyInParent());
					}					
					if (x == 2)
					{
						uttc.setChildGTIN(uttc.getChildGTIN3());
						uttc.setChildSequence(uttc.getChildGTIN3Sequence());
						uttc.setChildQuantity(uttc.getChildGTIN3QtyInParent());
					}					
					if (x == 3)
					{
						uttc.setChildGTIN(uttc.getChildGTIN4());
						uttc.setChildSequence(uttc.getChildGTIN4Sequence());
						uttc.setChildQuantity(uttc.getChildGTIN4QtyInParent());
					}								
					if (x == 4)
					{
						uttc.setChildGTIN(uttc.getChildGTIN5());
						uttc.setChildSequence(uttc.getChildGTIN5Sequence());
						uttc.setChildQuantity(uttc.getChildGTIN5QtyInParent());
					}		
					if (x == 5)
					{
						uttc.setChildGTIN(uttc.getChildGTIN6());
						uttc.setChildSequence(uttc.getChildGTIN6Sequence());
						uttc.setChildQuantity(uttc.getChildGTIN6QtyInParent());
					}
					if (x == 6)
					{
						uttc.setChildGTIN(uttc.getChildGTIN7());
						uttc.setChildSequence(uttc.getChildGTIN7Sequence());
						uttc.setChildQuantity(uttc.getChildGTIN7QtyInParent());
					}	
					if (!uttc.childGTIN.trim().equals(""))
					{	
					   ServiceGTIN.addChild(uttc);
					}   
				} catch (Exception e) {
				}
			}

		}
		if (problemMessage.toString().equals("")
			&& !uttc.getParentGTIN().equals("")) {
			try {
				InqGTIN setInfo = new InqGTIN();
				setInfo.setInqGTIN(uttc.getParentGTIN());
				BeanGTIN gtinInfo = ServiceGTIN.buildParentChildren(setInfo);
				if (gtinInfo.getGtin() != null)
				   uttc.buildUpdViewBeanFromBusinessBean(gtinInfo);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (!uttc.getParentGTIN().equals("")) {
			try {
				Vector returnedList =
					ServiceGTIN.rebuildViews(uttc.getParentGTIN());
							    
				request.setAttribute("familyTree", returnedList);
			} catch (Exception e) {
				// There is a problem when retrieving the Parent Child - Family Tree
			}
		}		

		request.setAttribute("msg", problemMessage.toString());
		request.setAttribute("updViewBean", uttc);
	}

/* Retrieve and Build everthing needed for the Detail Page
 * @see com.treetop.servlets.BaseServlet#pageDetail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
protected void pageDtl(
	HttpServletRequest request,
	HttpServletResponse response) {

	DtlGTIN dg = (DtlGTIN) request.getAttribute("dtlViewBean");
	dg.setRequestType("detail");

	try {
		dg.setDetailClass(ServiceGTIN.buildGtinDetail(dg.getGtinNumber()));
		
	} catch (Exception e) {
		//e.printStackTrace();
	}
	
	// add any other thing needed, example drop downs.
	if (!dg.getGtinNumber().equals("")) {
		try {
			Vector returnedList =
				ServiceGTIN.buildViews(dg.getGtinNumber());
							    
			request.setAttribute("familyTree", returnedList);
		} catch (Exception e) {
			// There is a problem when retrieving the Parent Child - Family Tree
		}
	}	
	//------Get Images in the folder for this GTIN
//** CHANGES	
//	String imageList[] = HTMLHelpersLinks.getDirectoryFromPath("\\\\10.6.100.6\\x\\ProductInfo\\GTIN\\" + dg.getGtinNumber().trim() + "/");
// 12/20/06 tw - Could not seem to make this work, will work on it at a later time
// Changed the view to go to the Folder as a link instead of displaying what is in the folder.		
//	String imageList[] = HTMLHelpersLinks.getDirectoryFromPathAS400("/Network/ProductInfo/GTIN/" + dg.getGtinNumber().trim() + "/");
//	request.setAttribute("imageList", imageList);		
	request.setAttribute("displayImagePath", ("X:\\ProductInfo\\GTIN\\" + dg.getGtinNumber().trim() + "\\"));

	request.setAttribute("dtlViewBean", dg);

}
}
