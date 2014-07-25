/*
 * Created on March 21, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.app.item;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.treetop.Email;
import com.treetop.SessionVariables;
import com.treetop.app.CtlKeyValues;
//import com.treetop.app.finance.UpdFinance;
import com.treetop.app.function.CtlFunction;
import com.treetop.app.function.UpdFunctionDetail;
import com.treetop.app.inventory.InqInventory;
import com.treetop.businessobjectapplications.BeanItem;
import com.treetop.businessobjects.*;
import com.treetop.servlets.BaseServlet;
import com.treetop.services.*;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.viewbeans.ParameterMessageBean;
import com.treetop.utilities.UtilityDateTime;
//import com.treetop.utilities.html.*;

/**
 * @author twalto
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CtlItem extends BaseServlet {

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
			requestType = "update";
			//requestType = "inqVariance";
		request.setAttribute("requestType", requestType);
		request.setAttribute("msg", "");
		//TESTING//
		//requestType = "inquiryAvailable";
		//------------------------------------------------------------------
		// Call Security
		String urlAddress = "/web/CtlItem";
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
		String sendTo = "/view/item/updItem.jsp";
		//*********************************
		//-----------------------------------------------------------------
		//   UPDATE PAGE To be dealt with BEFORE the List Pages
		//-----------------------------------------------------------------
		if (requestType.equals("updVariance") ||
			requestType.equals("addVariance") ||
			requestType.equals("deleteVariance") ||
			requestType.equals("pendVariance")) {
			UpdItemVariance uiv = new UpdItemVariance();
			uiv.populate(request);
			uiv.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			uiv.validate();
			request.setAttribute("updViewBean", uiv);
			
			if (uiv.getDisplayMessage().equals("") &&
			   !uiv.getItem().trim().equals(""))
			   pageUpdVariance(request, response);
			
			uiv = (UpdItemVariance) request.getAttribute("updViewBean");
			requestType = uiv.getRequestType(); // Used to go back to LIST page
			
			sendTo = "/APP/Item/updItemVariance.jsp";
		}		
		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//-----------------------------------------------------------------
		// Main Inquiry and List Pages
		if (requestType.equals("inquiry") || requestType.equals("list")) {
	//		InqReserveNumbers irn = new InqReserveNumbers();
	//		irn.populate(request);
	//		List errors = irn.validate();
	//		request.setAttribute("inqViewBean", irn);
	//		if (requestType.equals("list")) {
	//			if (errors == null || errors.size() == 0) {
	//				pageList(request, response);
	//				sendTo = "/APP/Resource/listNewItem.jsp";
	//			} else {
	//				requestType = "inquiry";
	//			}
	//		}
	//		if (requestType.equals("inquiry"))
	//			pageInq(request, response);
		}
		// Inquiry and List Pages for Available Resources
//		String resource = "";
//		String resourceType = "";
//		if (requestType.equals("addSendAvailable")) {
//			InqAvailableResources iar = new InqAvailableResources();
//			iar.populate(request);
//			List errors = iar.validate();
//			if (errors != null && errors.size() > 0) {
//				request.setAttribute("msg", errors.toString());
//				sendTo = "/APP/Resource/inqAvailableResources.jsp"; // List page
//																	// is
//				requestType = "listAvailable";
//			} else {
//				resource = iar.getResourceClass() + iar.getResourceEnd();
//				requestType = "add";
//			}
//		}
		// Additional Inquiry Pages
		if (requestType.equals("inqVariance") ||
			requestType.equals("listVariance"))
		{
			InqItem ii = new InqItem();
			ii.populate(request);
			ii.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			ii.setUpdateSecurity(request, response, "variance");
			ii.validate();
			if (!ii.getRequestType().equals("pendVariance") &&
				!ii.getRequestType().equals("deleteVariance") &&
				!ii.getRequestType().equals("updVariance"))
			   requestType = ii.getRequestType();
			else
			   ii.setRequestType(requestType);
			request.setAttribute("inqViewBean", ii);
			request.setAttribute("requestType", requestType);
			sendTo = "/APP/Item/inqItemVariance.jsp";
			if (requestType.equals("listVariance"))
			{
				pageList(request, response);
				sendTo = "/APP/Item/listItemVariance.jsp";
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
		if (requestType.equals("update")) {
			UpdItem ut = new UpdItem();
			ut.populate(request);
			ut.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			ut.validate();
			request.setAttribute("updViewBean", ut);
			if (!ut.getItem().trim().equals(""))
			  pageUpd(request, response);
			sendTo = "/view/item/updItem.jsp";
		}
		
		//-----------------------------------------------------------------
		//   PAUSE EMAILS
		//      Stop sending email reminders using the Tickler Function
		//-----------------------------------------------------------------
		if (requestType.equals("pause")) {
			//get the hidden fields and run the method to update the pause field
			UpdItem ut = new UpdItem();
			ut.populate(request);
			ut.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			ut.setPause("1");
			
			try {
				ServiceItem.updateNewItemPause(ut.getEnvironment(), ut);
			} catch (Exception e) {
				System.out.println("---------------------");
				System.out.println("CtlItem Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
				ParameterMessageBean.printParameters(request);
				System.out.println("---------------------");
				System.out.println(e);
				e.printStackTrace();
			}
			
			ut.validate();
			request.setAttribute("updViewBean", ut);
			if (!ut.getItem().trim().equals(""))
			  pageUpd(request, response);
			sendTo = "/view/item/updItem.jsp";
		}
		
		//-----------------------------------------------------------------
		//   UNPAUSE EMAILS
		//      Start sending email reminders using the Tickler Function
		//-----------------------------------------------------------------
		if (requestType.equals("unpause")) {
			//get the hidden fields and run the method to update the pause field
			UpdItem ut = new UpdItem();
			ut.populate(request);
			ut.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			ut.setPause("");
			String date = request.getParameter("newStartDate");
			
			try{
				ServiceTickler.resetTargetDate(ut, date);
			} catch (Exception e) {
				System.out.println("---------------------");
				System.out.println("CtlItem Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
				ParameterMessageBean.printParameters(request);
				System.out.println("---------------------");
				System.out.println(e);
				e.printStackTrace();
			}
			
			try {
				ServiceItem.updateNewItemPause(ut.getEnvironment(), ut);
			} catch (Exception e) {
				System.out.println("---------------------");
				System.out.println("CtlItem Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
				ParameterMessageBean.printParameters(request);
				System.out.println("---------------------");
				System.out.println(e);
				e.printStackTrace();
			}
			
			ut.validate();
			request.setAttribute("updViewBean", ut);
			if (!ut.getItem().trim().equals(""))
			  pageUpd(request, response);
			sendTo = "/view/item/updItem.jsp";
		}
		
		
		//-----------------------------------------------------------------
		//   COMPLETE EMAILS
		//      Set all remaining incomplete email reminders to complete for the Tickler Function
		//-----------------------------------------------------------------
		if (requestType.equals("complete")) {
			//get the hidden fields and run the method to update the pause field
			UpdItem ut = new UpdItem();
			ut.populate(request);
			ut.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
			
			//Execute service method to complete all email for this item.
			try {
				ServiceTickler.updateCompleteAll(ut.getGroup(), ut.getItem());
			} catch (Exception e) {
				System.out.println("---------------------");
				System.out.println("CtlItem Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
				ParameterMessageBean.printParameters(request);
				System.out.println("---------------------");
				System.out.println(e);
				e.printStackTrace();
			}
			
			ut.validate();
			request.setAttribute("updViewBean", ut);
			if (!ut.getItem().trim().equals(""))
			  pageUpd(request, response);
			sendTo = "/view/item/updItem.jsp";
		}
		
		//-----------------------------------------------------------------
		//  Go to the JSP
		//-----------------------------------------------------------------
		try { // Send down to the JSP
			getServletConfig().getServletContext().getRequestDispatcher(sendTo)
					.forward(request, response);
		} catch (Throwable theException) {
			
			System.out.println("---------------------");
			System.out.println("CtlItem Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
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
		
		InqItem ii = (InqItem) request.getAttribute("inqViewBean");
		try
		{
//			 Go out and get the Variance Information
			if (ii.getRequestType().equals("listVariance"))
			{	
				Vector sendParms = new Vector();
				sendParms.addElement(ii);
				BeanItem bi = ServiceItem.listVariancesByItem(sendParms);
				Vector getList = new Vector();
				getList.addElement(bi);
				ii.setListReport(getList);
			}
			
		} catch(Exception e)
		{}
		request.setAttribute("inqViewBean", ii);
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
	
	   UpdItem ui = (UpdItem) request.getAttribute("updViewBean");
	   BeanItem bi = new BeanItem();
	   String group = "";
		try
		{
			if (!ui.getSaveButton().equals("") &&
				ui.getDisplayMessage().trim().equals(""))
			{ // UPDATE the Record... 
				try
				{
					Vector sendInfo = new Vector();
					sendInfo.addElement(ui);
					ServiceItem.updateNewItem(ui.getEnvironment(), sendInfo);
				} catch(Exception e){
					System.out.println("Problem Updating Item" + ui.getItem().trim() + ": " + e);
				}
				try
				{// Add any alias's as needed -- on the update
			    if ((!ui.getAliasCheckBox().trim().equals("") &&
			    	 !ui.getCaseUPC().trim().equals(""))	||
				     !ui.getFlagFPK().trim().equals("") ||
				     !ui.getFlagOPN().trim().equals("") ||
				     !ui.getFlagJCE().trim().equals("") ||
				     !ui.getPlanner().trim().equals("") ||
				     !ui.getFlagCAR().trim().equals("") ||
				     !ui.getAliasRPT1().trim().equals("") ||
				     !ui.getFlagClub().trim().equals("") ||
				     !ui.getClubItemNumber().trim().equals("")|| 
				     !ui.getFlagSingleStrength().equals("") ||
				     !ui.getFlagAllergen().equals(""))
				      ServiceItem.addAlias(ui);
				}catch(Exception e)
				{
					System.out.println("Problem Adding Alias for Item " + ui.getItem().trim() + ": " + e);
				}
				
			}
			// Retrieve New Item Information
			bi = ServiceItem.buildNewItem(ui.getEnvironment(), ui.getItem());
			if (ui.getGroup().equals(""))
			{ // Basically first time Through
				if (!bi.getItemClass().getFunctionGroup().equals(""))
				  ui.setGroup(bi.getItemClass().getFunctionGroup());	
				else
				{	//   IF there is no information, it will ADD a record.
				  ui.setGroup(UpdItem.determineGroup(bi.getItemClass()));
				  String returnValue = ServiceItem.verifyNewItem(ui.getEnvironment(), ui.getItem());
				  if (!returnValue.equals("") &&
					   ui.getDisplayMessage().trim().equals(""))
				  {
				     try
					 { // Add the item to the file -- before displaying the screen
						Vector sendInfo = new Vector();
						sendInfo.addElement(ui);
						ServiceItem.addNewItem(ui.getEnvironment(), sendInfo);
					 } catch(Exception e){
						System.out.println("Problem Adding Item " + ui.getItem().trim() + ": " + e);
				 	 }
					 try { // add the Tickler Information
						 UpdFunctionDetail ufd = new UpdFunctionDetail();
						 ufd.setGroup(ui.getGroup());
						 ufd.setKeyValue(ui.getItem());
						 ufd.setUserProfile(ui.getUpdateUser());
						 ufd.setEstTargetDate(ui.getEstTargetDate());
						 ufd.setRequestType("addList");
						 request.setAttribute("addFunction", ufd);
						 CtlFunction thisOne = new CtlFunction();
						thisOne.performTask(request, response);
					  } catch (Exception e) {
						System.out.println("Problem Adding Functons to Tickler for Item " + ui.getItem().trim() + ": " + e);
					  }
				  }
				}
			}
			if (!ui.getSaveButton().equals("") &&
				ui.getDisplayMessage().trim().equals(""))
			{
			   //-----------------------------------------------
			   // Go to the Function's and Update information
			   // Which would relate to the Responsibility Piece.
				if (!ui.getGroup().equals(""))
				{	
			   	  try {
					//request.setAttribute("group", ui.getGroup());
					CtlFunction thisOne = new CtlFunction();
					thisOne.performTask(request, response);
				  } catch (Exception e) {
					System.out.println("Problem Updating Tickler: " + e);
				  }
				}
			}
			// Obtain Tickler Function Detail for this resource.
			try {
				if (!ui.getItem().equals(""))
				{
					TicklerFunctionDetail fd = new TicklerFunctionDetail();
					fd.setGroup(ui.getGroup());
					fd.setIdKeyValue(ui.getItem());
					fd.setEstTargetDate(ui.getEstTargetDate());
				//	ServiceTickler.updateCompleteAll(ui.getGroup(), ui.getItem());
					
					bi.setFunctions(ServiceTickler.buildFunctionList(fd));
				}
			} catch(Exception e){
				 System.out.println("Problem retrieving Tickler: " + e);
			}	
			if (ui.getDisplayMessage().trim().equals(""))
			   ui.loadUpdItemFromBeanItem(bi);
			
			//set are emails complete.
			ServiceTickler.areEmailsComplete(ui);
			
			ui.setAreYouOwner(UpdItem.getSecurity(request, response, bi.getItemClass().getResponsible()));
		//	ui.setAreYouOwner(UpdItem.getSecurity(request, response));
			request.setAttribute("updViewBean", ui);
		}
		catch(Exception e)
		{
		 System.out.println("CtlItem, pageUpd  : " + e);	
		}	   
		
		// IF GTIN is filled in -- go get the Family Tree
		if (!ui.getPalletGTIN().equals("")) {
			try { // GET THE FAMILY TREE
				request.setAttribute("familyTree", ServiceGTIN.buildViews(ui.getPalletGTIN()));
			} catch (Exception e) {
				// There is a problem when retrieving the Parent
				// Child - Family Tree
			}
		}
		//-----------------------------------------------
//		// URL's and Comments -- Call the KeyValues Servlet
//		//  ONLY on and UPDATE-ADD-DELETE (Saved)
		try {
			CtlKeyValues thisOne = new CtlKeyValues();
			thisOne.performTask(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}	   
	// Obtain URL's for the Item
	try {
		if (!ui.getItem().equals(""))
		{
			KeyValue kv = new KeyValue();
			kv.setEntryType("ItemUrl");
			kv.setKey1(ui.getItem().trim());
			bi.setItemUrls(ServiceKeyValue.buildKeyValueList(kv));
		}
	} catch(Exception e)
	{
		// skip it. allowed to be empty
		bi.setItemUrls(new Vector());
	}
	// Supplier Summary
	try {
		if (!ui.getItem().trim().equals(""))
		{
			KeyValue kv = new KeyValue();
			kv.setEntryType("ItemUrl2");
			kv.setKey1(ui.getItem().trim());
			Vector listSupplierSummary = ServiceKeyValue.buildKeyValueList(kv);
			if (!listSupplierSummary.isEmpty())
			{
				KeyValue newKV = (KeyValue) listSupplierSummary.elementAt(0);
				if (ui.getSupplierSummaryURLRemove().trim().equals(""))
				{
					ui.setSupplierSummaryURL(newKV.getValue());
				}else{
					try{
						KeyValue deleteKV = new KeyValue();
						deleteKV.setDeleteUser(ui.getUpdateUser());
						deleteKV.setUniqueKey(newKV.getUniqueKey());
						ServiceKeyValue.deleteKeyValue(deleteKV);
						ui.setSupplierSummaryURL("");
					}catch(Exception e){}
				}
			}
		}	
	} catch(Exception e)
	{}
	
	// Obtain Comments for Each Item
	try {
		if (!ui.getItem().equals(""))
		{
			KeyValue kv = new KeyValue();
			kv.setEntryType("ItemNewComment");
			kv.setKey1(ui.getItem().trim());
			bi.setComments(ServiceKeyValue.buildKeyValueList(kv));
		}
	} catch(Exception e)
	{
//		 skip it. allowed to be empty
		bi.setComments(new Vector());
	}	
	request.setAttribute("beanItem", bi);
    request.setAttribute("displayImagePath", ("X:\\ProductInfo\\Resource\\" + ui.getItem().trim() + "\\"));
	}

/*
 * Retrieve and Build everthing needed for the Update Variance Page
 *   Created 9/29/08 TWalton
 */
protected void pageUpdVariance(HttpServletRequest request,
					   		   HttpServletResponse response) {
	
   UpdItemVariance uiv = (UpdItemVariance) request.getAttribute("updViewBean");
   String requestType = (String) request.getAttribute("requestType");
   uiv.setRequestType(requestType);
   ItemVariance iv = new ItemVariance();
   try
   {
   	 iv = ServiceItem.updateItemVariance("PRD", uiv);
   	 if (!requestType.equals("pendVariance") &&
   	 	 !requestType.equals("deleteVariance"))
   	    uiv.loadFromItemVariance(iv);
  }
   catch(Exception e)
   {}
   
   // ADD PENDING RECORD (NEW VARIANCE for this Item)
   if (requestType.trim().equals("addVariance"))
   {
   	 uiv.setRequestType("updVariance");  
   }
   // DELETE PENDING RECORD // CHANGE FROM ACTIVE TO PENDING
   if (requestType.trim().equals("deleteVariance") ||
   	   requestType.trim().equals("pendVariance"))
   {
   	  uiv.setRequestType("listVariance");
   }   
   // UPDATE PENDING RECORD
   if (requestType.trim().equals("updVariance") &&
  	   !uiv.getGoButton().equals(""))
   {
   	    uiv.setRequestType("listVariance");
   }  

   request.setAttribute("updViewBean", uiv);
}
}
