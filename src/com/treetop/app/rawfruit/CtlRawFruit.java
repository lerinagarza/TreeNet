package com.treetop.app.rawfruit;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.Email;
import com.treetop.SessionVariables;
import com.treetop.app.CtlKeyValues;
import com.treetop.businessobjectapplications.BeanAvailFruit;
import com.treetop.businessobjectapplications.BeanRawFruit;
import com.treetop.businessobjects.AvailFruitByWhse;
import com.treetop.businessobjects.KeyValue;
import com.treetop.businessobjects.RawFruitLoad;
import com.treetop.businessobjects.RawFruitPO;
import com.treetop.services.ServiceAvailableFruit;
import com.treetop.services.ServiceKeyValue;
import com.treetop.services.ServiceLotWorkFiles;
import com.treetop.services.ServiceRawFruit;
import com.treetop.servlets.BaseServlet;
import com.treetop.utilities.html.DropDownDual;
import com.treetop.utilities.html.DropDownTriple;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.viewbeans.ParameterMessageBean;

/**
 * Insert the type's description here.
 * Creation date: (11/5/2008)
 * @author: Twalton
 */
public class CtlRawFruit extends BaseServlet  
{
/**
 * Process incoming HTTP GET requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

	performTask(request, response);

}
/**
 * Process incoming HTTP POST requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

	performTask(request, response);

}
/**
 * Returns the servlet info string.
 */
public String getServletInfo() {

	return super.getServletInfo();

}
/**
 * Initializes the servlet.
 */
public void init() {
	// insert code to initialize the servlet here

}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performTask(javax.servlet.http.HttpServletRequest request, 
	                    javax.servlet.http.HttpServletResponse response) 
{
try
{
	String requestType = request.getParameter("requestType");
	if (requestType == null)
		requestType = "inqScaleTicket";
	//requestType = "";

	String environment = request.getParameter("environment");
	if (environment == null) {
		environment = "PRD";
	}
	
	String viewPath = "/view/rawFruit";
	
	request.setAttribute("requestType", requestType);
	request.setAttribute("msg", "");
	String displayMessage = "";
	//------------------------------------------------------------------
	// Call Security
	String urlAddress = "/web/CtlRawFruit?requestType=inqScaleTicket";
	if (requestType.equals("bldLotAttribute"))
	{
		urlAddress = "/web/CtlRawFruit?requestType=bldLotAttribute";
	}
	if (requestType.equals("update") ||
		requestType.equals("updateLot") ||
		requestType.equals("generatePO") ||
		requestType.equals("deleteLot") ||
		requestType.equals("deletePO") ||
		requestType.equals("deleteScaleTicket") ||
		requestType.equals("copyPositive") ||
		requestType.equals("copyNegative"))
	{
		urlAddress = "/web/CtlRawFruit?requestType=update";
	}
	if (requestType.equals("updAvailFruit") ||
		requestType.equals("updAvailFruitToPurchase") ||
		requestType.equals("addSchedFruit") ||
		requestType.equals("schedAvailFruit") ||
		requestType.equals("updSchedFruit") ||
		requestType.equals("deleteSchedLoad") ||
		requestType.equals("cancelSchedLoad") ||
		requestType.equals("unCancelSchedLoad") ||
		requestType.equals("receiveSchedLoad") ||
		requestType.equals("unreceiveSchedLoad"))
		urlAddress = "/web/CtlRawFruit?requestType=updAvailFruit&environment=PRD";
	
	//problem.. need to code security around , SCHEDULE of Loads... allow EVERYONE to see..
	// right now only people with Role 2 security can see any of this
	//if (requestType.equals("inqAvailFruit") ||
	//	requestType.equals("listAvailFruitAll"))
	//	urlAddress = "/web/CtlRawFruit?requestType=updAvailFruit&environment=PRD";
	
     if (!callSecurity(request, response, urlAddress).equals("")) {	   
	   	try {
		response.sendRedirect("/web/TreeNetInq?msg="
					+ SessionVariables.getSessionttiSecStatus(request,
								response));
		 } catch (Exception e) {
			e.printStackTrace();
	 	 }
		return;
	   }
		//------------------------------------------------------------------
		//Passed Security
		//*********************************
		// Default Screen
		String sendTo = "/APP/RawFruit/updRawFruit.jsp";
		//String sendTo = "/JSP/Test/testTripleDropDown.jsp";
	// ---------------------------------------------------------------
	//  Detail Pages - Reports for 1 Scale Ticket Number
	// ---------------------------------------------------------------
//	  requestType = "reportInvoice";
	  request.setAttribute("requestType", requestType);
	  if (requestType.equals("reportPO") ||
	  	  requestType.equals("reportFreight") ||
		  requestType.equals("reportInvoice") ||
		  requestType.equals("generatePO"))
	  {
	  	 DtlRawFruitLoad drfl = new DtlRawFruitLoad();
		 drfl.populate(request);
	//	 drfl.setScaleTicket("3022875");
		 drfl.validate();
		 request.setAttribute("dtlViewBean", drfl);
		 pageDtl(request, response);
		 if (requestType.equals("reportPO") ||
			 requestType.equals("generatePO"))
	  	    sendTo = "APP/RawFruit/dtlRawFruitReportPO.jsp";
		 if (requestType.equals("reportFreight"))
		 	sendTo = "APP/RawFruit/dtlRawFruitReportFreight.jsp";
		 if (requestType.equals("reportInvoice"))
		 	sendTo = "APP/RawFruit/dtlRawFruitReportInvoice.jsp";
	  }
	 
	// ---------------------------------------------------------------
	//  DELETE
	// ---------------------------------------------------------------
	   if (requestType.equals("deleteLot") ||
	   	   requestType.equals("deletePO"))
	   {
	   	  UpdRawFruitLoad urf = new UpdRawFruitLoad();
	   	  urf.populate(request);
	   	  request.setAttribute("updViewBean", urf);
	   	  
	   	  if (!urf.getScaleTicket().equals(""))
	   	  {
	   	  	deleteRecords(request, response);
	   	  }
	   	  requestType = "update";
	   	  request.setAttribute("requestType", requestType);
	   }
	 
	   if (requestType.equals("deleteScaleTicket"))
	   {
		   sendTo = "APP/RawFruit/deleteRawFruitLoad.jsp";
		   InqRawFruit irf = new InqRawFruit();
		   irf.populate(request);
		   irf.validate();
		   request.setAttribute("inqViewBean", irf);
		   if (!irf.getGetList().equals(""))
			  requestType = "listScaleTicket";
		   if (!irf.getDeleteLoad().equals(""))
		   {
			   deleteRecords(request, response);
			   requestType = "listScaleTicket"; 
		   }
		}
	   if (requestType.equals("deleteSchedLoad") ||
		   requestType.equals("cancelSchedLoad") ||
		   requestType.equals("receiveSchedLoad") ||
		   requestType.equals("goToReceiveSchedLoad") ||
		   requestType.equals("unreceiveSchedLoad") ||
		   requestType.equals("unCancelSchedLoad"))
	   {
		  DtlScheduledFruit dsf = new DtlScheduledFruit();
		  dsf.populate(request);
		  dsf.validate();
		  dsf.setUserProfile(SessionVariables.getSessionttiProfile(request, response));
		  InqScheduledFruit isf = new InqScheduledFruit();
		  isf.populate(request);
		  isf.validate();
		  dsf.setInqSched(isf);
		  if (requestType.equals("goToReceiveSchedLoad"))
			  dsf.setReceiveLoad("Y");
		  request.setAttribute("dtlViewBean", dsf);
		  if (dsf.getCancelLoad().trim().equals("") &&
			  dsf.getDeleteLoad().trim().equals("") &&
			  dsf.getReceiveLoad().trim().equals("") && 
			  dsf.getUnreceiveLoad().trim().equals("") &&
			  !requestType.equals("unCancelSchedLoad"))
		  {
		    pageDtl(request, response);
		    sendTo = "APP/RawFruit/dtlSchedFruit.jsp";
		  }else{
			pageUpd(request, response);
			 if (dsf.getOriginalRequestType().trim().equals("dtlSchedFruit") ||
			     dsf.getOriginalRequestType().trim().equals("listSchedFruit") ||
			     dsf.getOriginalRequestType().trim().equals("inqSchedFruit"))
				requestType = dsf.getOriginalRequestType().trim();
			 else
			 {
				 if (requestType.equals("goToReceiveSchedLoad"))
					 requestType = "update";
			 }
			 request.setAttribute("requestType", requestType);
		  }
	   }
	// ---------------------------------------------------------------
	//  COPY
	// ---------------------------------------------------------------
	   if (requestType.equals("copyPositive") ||
	   	   requestType.equals("copyNegative"))
	   {
		   InqRawFruit irf = new InqRawFruit();
		   irf.populate(request);
		   irf.validate();
		   irf.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
	   	   request.setAttribute("inqViewBean", irf);
	   	  
	   	  if (!irf.getScaleTicket().equals(""))
	   	  {
	   	  	copyRecords(request, response);
	   	  }
	   	  requestType = "listScaleTicket";
	   	  request.setAttribute("requestType", requestType);
	   }
	// ---------------------------------------------------------------
	//  Update Raw Fruit
	// ---------------------------------------------------------------
//	  Update Lot Information
	   if (requestType.equals("updateLot"))
	   {
		  UpdRawFruitLot urfl = new UpdRawFruitLot();
		  urfl.populate(request);
		  urfl.validate();
		  urfl.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
		  if (urfl.getUpdateUser() == null)
			  urfl.setUpdateUser("TreeNet");
		  urfl.populatePayments(request);
		  urfl.buildDropDownVectors();
		  request.setAttribute("updViewBean", urfl);
		  if (urfl.getDisplayMessage().trim().equals(""))
		   	 pageUpd(request, response);
		  sendTo = "APP/RawFruit/updRawFruitLot.jsp";
		  if (!urfl.getBackToLoad().equals(""))
		     requestType = "update";
	   }
	   
	   request.setAttribute("requestType", requestType);
	   
	   
	  // Update Load Information
	   if (requestType.equals("update"))
	   {
		   UpdRawFruitLoad urf = new UpdRawFruitLoad();
		   urf.populate(request);
		   urf.validate();
		
		   if (!urf.getQuickEntry().equals("")) {
			   //additional validation for quick entry screen
			   urf.validateQuickEntry();
		   }
		
		   urf.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
	      
		   if (urf.getUpdateUser() == null)
			   urf.setUpdateUser("TreeNet");
	      
		   if (urf.getInspectedBy().trim().equals(""))
			   urf.setInspectedBy(urf.getUpdateUser().trim());
	      
		   urf.populateQuickEntries(request);
		   request.setAttribute("updViewBean", urf);
		   urf.populateBins(request);
	       urf.populatePOs(request);
	       urf.buildDropDownVectors();
	   	  
	       if (!urf.getScaleTicket().equals("")) {
			  
	    	   if (urf.getDisplayMessage().trim().equals("")) {
	    		   //TODO remove
	    		   pageUpd(request, response);
	    	   }
		    
	    	   sendTo = "/APP/RawFruit/updRawFruitLoad.jsp";
	       }
	   	  
	       if (!urf.getQuickEntry().equals("")) {  
	    	   System.out.println("Use Quick Entry Screen");
	    	   sendTo = "/APP/RawFruit/updRawFruitLoadQuickEntry.jsp";
	       }
	   	  
	       urf = (UpdRawFruitLoad) request.getAttribute("updViewBean");

	       int POSeq = urf.getUpdBean().getListRFPO().size();
	       String addLot = "N";
	       String saveSeq = "";
	       
	       if (POSeq > 0)
	       {
	    	   for (int x = 0; x < POSeq; x++)
	    	   {
	    		   RawFruitPO testInfo = (RawFruitPO) urf.getUpdBean().getListRFPO().elementAt(x);
	    		   String testAdd = request.getParameter("addLot" + testInfo.getScaleSequence());
	    		   
	    		   if (testAdd != null && !testAdd.equals(""))
	    		   {
	    			   saveSeq = testInfo.getScaleSequence();
	    			   addLot = "Y";
	    		   }
	    	   }
	       }
	       
	       if (addLot.equals("Y"))
	       {
	    	   UpdRawFruitLot urfl = new UpdRawFruitLot();
	    	   urfl.populate(request);
	    	   urfl.validate();
	    	   urfl.buildDropDownVectors();
	    	   urfl.setScaleSequence(saveSeq);

	    	   try
	    	   {
	    		   // Figure out the PO number 	
	    		   UpdRawFruitLoad ur = (UpdRawFruitLoad) request.getAttribute("updViewBean");
	    		   RawFruitLoad rfl = ur.getUpdBean().getRfLoad();
	    		   
	    		   if (rfl.getListPOs().size() > 0)
	    		   {
	    			   for (int x = 0; x < rfl.getListPOs().size(); x++)
	    			   {
	    				   RawFruitPO po = (RawFruitPO) rfl.getListPOs().elementAt(x);
	    				   
	    				   if (po.getScaleSequence().equals(saveSeq))
	    				   {
	    					   urfl.setPoNumber(po.getPoNumber());
	    					   urfl.setRfPO(po);
	    				   }
	    			   }
	    		   }
	    	   }
	    	   catch(Exception e)
	    	   {}
	    	   
	    	   urfl.setLotSequenceNumber(urfl.getNextLotSequence());
	    	   request.setAttribute("updViewBean", urfl);
	    	   UpdRawFruitLot ul = (UpdRawFruitLot) request.getAttribute("updViewBean");
	    	   sendTo = "APP/RawFruit/updRawFruitLot.jsp";
	       }
	   }
	   
	   
	   if (requestType.equals("updAvailFruit") ||
		   requestType.equals("updAvailFruitToPurchase"))
	   {
		  UpdAvailableFruit uaf = new UpdAvailableFruit();
	   	  uaf.populate(request);
	   	  if (requestType.equals("updAvailFruitToPurchase"))
	   	  	 uaf.setInventoryType("TOPURCHASE");
	   	  uaf.validate();
	   	  uaf.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
	   	  // TEST, if no Warehouse and location are entered then go to a screen that says choose warehouse and location
	   	  if (uaf.getWhseNo().trim().equals("") ||
	   		  uaf.getLocAddNo().trim().equals(""))
	   	  {
	   		  uaf.setWhseNoError("");
	   		  uaf.setLocAddNoError("");
	   		  uaf.setDisplayMessage("");
	   		  InqAvailableFruit inqAvail = new InqAvailableFruit();
	   		  inqAvail.populate(request);
	   		  inqAvail.setEnvironment(uaf.getEnvironment());
	   		  inqAvail.setUserProfile(SessionVariables.getSessionttiProfile(request, response));
	   		  
	   		  //  TEST Warehouse Specific
	   		  //inqAvail.setUserProfile("ZIRKLE");
	   		  //String[] thisone = new String[1];
	   		  //thisone[0] = "2";
	   		  //inqAvail.setRoles(thisone);
	   		  //SessionVariables.setSessionttiUserRoles(request, response, thisone);
	   		  // end of TEST
	   		  
	   		  inqAvail.setRoles(SessionVariables.getSessionttiUserRoles(request, response));
	   		  
	   		  // call the service to ask for the Dual Drop Down List of
	   		  //    Warehouses and Locations
	   		  //*** Call this service within the updAvailaFruit information
	   		  inqAvail.setListReport(ServiceAvailableFruit.dropDownWarehouse(inqAvail));
	   		 // Review the Returned Value
	   		  if (inqAvail.getListReport().size() != 0)
	   		  { // if Zero, then no warehouses and locations exist. may want to note that
	   			  if (inqAvail.getListReport().size() == 1)
	   			  {
	   				// IF only one option, put the Warehouse and Locations into uaf.
	   				 DropDownTriple ddd = (DropDownTriple) inqAvail.getListReport().elementAt(0);
	   				 uaf.setWhseNo(ddd.getList2Value());
	   				 uaf.setLocAddNo(ddd.getList3Value());
	   			  }
	   			  else
	   			  {
	   				  // IF more than one option record
	   				  inqAvail.buildDropDownInformation();
	   			  }
	   		  }
	   		  request.setAttribute("inqViewBean", inqAvail);
	   		  sendTo = viewPath + "/updAvailFruit.jsp";
	   	  }
	   	  if (!uaf.getWhseNo().trim().equals("") &&
	   	      !uaf.getLocAddNo().trim().equals(""))
	   			  //&&
		   	  //!uaf.getWhseAddressNumber().trim().equals(""))
		  {
	   	     uaf.populateDetails(request);
	   	     uaf.setRequestType(requestType);
	   	     uaf.buildDropDownVectors();
	   	     InqAvailableFruit inqAvail = new InqAvailableFruit();
   		     inqAvail.setEnvironment(uaf.getEnvironment());
   		     inqAvail.setUserProfile(SessionVariables.getSessionttiProfile(request, response));
   		     inqAvail.setRoles(SessionVariables.getSessionttiUserRoles(request, response));
   		     inqAvail.setWhseNo(uaf.getWhseNo());
//   		*** Call this service within the updAvailFruit information
	   	     uaf.setListLocation(ServiceAvailableFruit.dropDownWarehouse(inqAvail));
		     request.setAttribute("updViewBean", uaf);
		     if (uaf.getDisplayMessage().trim().equals(""))
		     {
			    pageUpd(request, response); 
		     }
		     if (requestType.equals("updAvailFruitToPurchase"))
		    	sendTo = viewPath + "/updAvailFruitToPurchaseByWhse.jsp";
		     else	 
 		        sendTo = viewPath + "/updAvailFruitByWhse.jsp";
		  }
	   }
	  //  Will get to this screen if you want to Update a Scheduled Load of Fruit 
	   if (requestType.equals("updSchedFruit") ||
		   requestType.equals("addSchedFruitLoad"))
	   {
		   sendTo = "APP/RawFruit/updSchedFruit.jsp";
		  UpdScheduledFruit updSched = new UpdScheduledFruit();
		  updSched.populate(request);
		  updSched.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
		  updSched.validate();
		  updSched.populateDetails(request);
		  InqAvailableFruit iaf = new InqAvailableFruit();
		  iaf.setEnvironment(updSched.getEnvironment());
		  iaf.setUserProfile(SessionVariables.getSessionttiProfile(request, response));
		  iaf.setRoles(SessionVariables.getSessionttiUserRoles(request, response));
		  iaf.setRequestType(requestType);
		  InqScheduledFruit isf = new InqScheduledFruit();
		  isf.populate(request);
		  updSched.setInqSched(isf);
		  //*** Need to populate the Drop Down lists correctly //*****
		  //  from here because of the InqAvailableFruit information
		    Vector whseList = ServiceAvailableFruit.dropDownWarehouse(iaf);
		    DropDownDual addRecord = new DropDownDual();
		    addRecord.setMasterValue("ORCHARDRUN");
		    addRecord.setMasterDescription("ORCHARD RUN");
		    whseList.add(0, addRecord);
		  updSched.setDddWhseLocation(whseList);
		  //updSched.buildDropDownInformation();
		  //****
		  request.setAttribute("updViewBean", updSched);
		  pageUpd(request, response);
		  updSched = (UpdScheduledFruit) request.getAttribute("updViewBean");
		  if (!updSched.getSaveButton().trim().equals("") &&
			  updSched.getDisplayMessage().trim().equals(""))
		  {
			 if (isf.getOriginalRequestType().trim().equals("dtlSchedFruit") ||
				 isf.getOriginalRequestType().trim().equals("listSchedFruit") ||
				 isf.getOriginalRequestType().trim().equals("inqSchedFruit"))
			 {
				requestType = isf.getOriginalRequestType().trim();
				if (updSched.getAddNewLoad().trim().equals("Y"))
				{
				    requestType = "listSchedFruit";
				    request.setAttribute("inqLoadNumber", updSched.getLoadNumber());
				}
			 }
			 request.setAttribute("requestType", requestType);
		  }
		  updSched.buildDropDownInformation();
		  request.setAttribute("updViewBean", updSched);
	   }
	   //	 Will get to this screen from the screen for request type listAvailFruit
	   // the schedAvailFruit -- gets you to a place where you can Checkbox the available fruit you may want to schedule a load for
	   // the addSchedFruit -- gets you to the place you can schedule a load from the available fruit
	   if (requestType.equals("schedAvailFruit") ||
		   requestType.equals("addSchedFruit"))
	   { 
		   sendTo = "APP/RawFruit/updSchedFruit.jsp";
		   UpdScheduledFruit updSched = new UpdScheduledFruit();
		   updSched.populate(request);
		   updSched.validate();
		   updSched.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
		   if (requestType.equals("addSchedFruit"))
		      updSched.populateLoads(request); // will include populating the Details...
		   else
		   {
		      updSched.populateDetails(request);
		      updSched.buildDropDownInformation();
		   }
		   // get the Inquiry Information, must keep until the 
		   //    loads have been scheduled, plan to go back to the
		   //    available fruit list page after ADD
		   InqAvailableFruit iaf = new InqAvailableFruit();
		   iaf.populate(request);
		   updSched.setInqAvail(iaf);
	
		     // if there are no boxes checked, go back to the list page
		   updSched.setRequestType("addSchedFruit");
		   if (requestType.equals("addSchedFruit") && !updSched.getDisplayMessage().trim().equals(""))
		   {	   
			   updSched.setDisplayMessage("Errors were found: Please review and correct all Input Fields with Red Notes associated to them.");
			   updSched.buildDropDownInformation();
		   }
		   request.setAttribute("updViewBean", updSched);
		   if (requestType.equals("addSchedFruit") &&
			   updSched.getDisplayMessage().trim().equals(""))
		   {  // go and Process the ADD Records
			   pageUpd(request, response);
			   requestType = "listAvailFruit";
			   request.setAttribute("requestType", requestType);
		   } 
		   if (!updSched.getDisplayMessage().trim().equals("") &&
			   requestType.equals("schedAvailFruit"))
		   { // There were no Available Fruit chosen, go back to the list page
			   requestType = "listAvailFruit";
			   request.setAttribute("requestType", requestType);
			   displayMessage = updSched.getDisplayMessage();
		   }
		//   System.out.println("Stop to check");
	   }
	   //********************************************************************************************************
	   // -- TRANSFER LOADS
	   //	 Will get to this screen from the screen for request type listSchedFruit 
	   //     May come from listAvailFruit too.. -- will add later if needed
	   //  -- will allow schedule of Multiple Transfer Loads
	   if (requestType.equals("schedTransfer"))
	   { 
		   sendTo = "APP/RawFruit/updSchedTransfer.jsp";
		   UpdScheduledFruit updSched = new UpdScheduledFruit();
		   updSched.populate(request);
		   updSched.setRequestType(requestType);
		   updSched.validate();
		   updSched.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
		   updSched.populateLoads(request); // will include populating the Details...
//*********************'
		   //********************
		   //*******************
		   // get the Inquiry Information, must keep until the 
		   //    loads have been scheduled, plan to go back to the
		   //    available fruit list page after ADD
		// should be from the Scheduled Fruit screen   
		   InqScheduledFruit isf = new InqScheduledFruit();
		   isf.populate(request);
		   updSched.setInqSched(isf);
//*********************
		   //***************************
		   //***************************
		   request.setAttribute("updViewBean", updSched);
		   if (!updSched.getSaveButton().trim().equals("") &&
			    updSched.getDisplayMessage().trim().equals(""))
		   { // process and ADD/Update the records 
				 pageUpd(request, response);   
				 requestType = isf.getOriginalRequestType().trim();
				 request.setAttribute("requestType", requestType);
		   }
		   else
		   {
			   // IF loads are created -- go back to the list inquiry page (or the selection page)
			   // would not need to build drop down information
			   updSched.buildDropDownInformation();
			   request.setAttribute("updViewBean", updSched);
		   }
	   }
	   if (requestType.equals("updSchedTransfer"))
	   { 
		   sendTo = "APP/RawFruit/updSchedTransfer.jsp";
		   UpdScheduledFruit updSched = new UpdScheduledFruit();
		   updSched.populate(request);
		   updSched.setRequestType(requestType);
		   updSched.validate();
		   updSched.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
		   updSched.populateLoads(request); // will include populating details of the ONE load
		   request.setAttribute("updViewBean", updSched);
	      // go to the pageUpd section -- to be able retrieve the LOAD information before actually updating
	       pageUpd(request, response);
	       updSched = (UpdScheduledFruit) request.getAttribute("updViewBean");
		   updSched.buildDropDownInformation();
		   request.setAttribute("updViewBean", updSched);
	   }
	// ---------------------------------------------------------------
	//  Build Work File
	// ---------------------------------------------------------------
	   if (requestType.equals("bldLotAttribute"))
	   {
		   BldRawFruit brf = new BldRawFruit();
		   brf.populate(request);
		   brf.validate();
		   request.setAttribute("bldViewBean", brf);
		   if (!brf.getGoButton().equals(""))
		   {
		   	  pageBld(request, response);
		   }
		   sendTo = "APP/RawFruit/bldLotAttributeFile.jsp";
		}   
	// ---------------------------------------------------------------
	//  Inquiry / List Scale Ticket Search
	//  Inquiry Available Fruit 
	// Inquiry Scheduled Fruit
	// ---------------------------------------------------------------
	   if (requestType.equals("inqScaleTicket") ||
		   requestType.equals("listScaleTicket"))
	   {
		   sendTo = "APP/RawFruit/inqScaleTicket.jsp";
		   InqRawFruit irf = new InqRawFruit();
		   irf.populate(request);
		   irf.setRequestType(requestType.trim());
		   irf.validate();
		   irf.setAllowUpdate(InqRawFruit.getSecurity(request, response));
		   if (irf.getRequestType().equals("deleteScaleTicket"))
			  irf.setRequestType("listScaleTicket");
		   request.setAttribute("inqViewBean", irf);
		   if (requestType.equals("listScaleTicket") &&
			   irf.getDisplayMessage().trim().equals(""))
		   {
		   	  pageList(request, response);
			  sendTo = "APP/RawFruit/listScaleTicket.jsp";
		   }
		}

	   if (requestType.equals("inqAvailFruit") ||
		   requestType.equals("inqAvailFruitAll") ||
		   requestType.equals("listAvailFruit") ||
		   requestType.equals("listAvailFruitAll"))
	   {
		   sendTo = "APP/RawFruit/inqAvailFruit.jsp";
		   InqAvailableFruit iaf = new InqAvailableFruit();
		   iaf.populate(request);
		   iaf.setRequestType(requestType);
		   iaf.setUserProfile(SessionVariables.getSessionttiProfile(request, response));
	   	   iaf.setRoles(SessionVariables.getSessionttiUserRoles(request, response));
		   iaf.validate();
		   iaf.setDisplayMessage(displayMessage);
		   if (requestType.equals("inqAvailFruit") ||
			   requestType.equals("inqAvailFruitAll"))
		   {
//			 *** Call this service within the updAvailFruit information
			   iaf.setListReport(ServiceAvailableFruit.dropDownWarehouse(iaf));
			   iaf.buildDropDownInformation();
		   }
		   request.setAttribute("inqViewBean", iaf);
		   if (requestType.equals("listAvailFruit") ||
			   requestType.equals("listAvailFruitAll"))
		   {
			   pageList(request, response);
			   sendTo = "APP/RawFruit/listAvailFruit.jsp";
		   }
	   }
	   if (requestType.equals("inqSchedFruit") ||
		   requestType.equals("listSchedFruit"))
		   {
			   sendTo = "APP/RawFruit/inqSchedFruit.jsp";
			   InqScheduledFruit isf = new InqScheduledFruit();
			   isf.populate(request);
			   isf.setRequestType(requestType);
			   isf.setUserProfile(SessionVariables.getSessionttiProfile(request, response));
		   	   isf.setRoles(SessionVariables.getSessionttiUserRoles(request, response));
		   	   isf.validate();
			   isf.setAllowUpdate(InqRawFruit.getSecurity(request, response));
			   if (requestType.equals("inqSchedFruit"))
			   {
				   InqAvailableFruit iaf = new InqAvailableFruit();
				   iaf.setEnvironment(isf.getEnvironment());
				   iaf.setUserProfile(isf.getUserProfile());
				   iaf.setRoles(isf.getRoles());
//				 *** Call this service within the updAvailFruit information
				   Vector listWhses = ServiceAvailableFruit.dropDownWarehouse(iaf);
				   DropDownTriple ddt = new DropDownTriple();
				   ddt.setList1Value("ORCHARD RUN");
				   ddt.setList1Description("ORCHARD RUN");
				   ddt.setList2Value("ORCHARDRUN");
				   ddt.setList2Description("ORCHARD RUN");
				   listWhses.add(0, ddt);
				   isf.setListReport(listWhses);
				   isf.buildDropDownInformation();
			   }
			   request.setAttribute("inqViewBean", isf);
			   if (requestType.equals("listSchedFruit"))
			   {
				   pageList(request, response);
				   sendTo = "APP/RawFruit/listSchedFruit.jsp";
			   }
		   }
	      if (requestType.equals("dtlSchedFruit"))
		  {
			 DtlScheduledFruit dsf = new DtlScheduledFruit();
			 dsf.populate(request);
			 dsf.validate();
			 request.setAttribute("dtlViewBean", dsf); 
			 pageDtl(request, response);
			 sendTo = "APP/RawFruit/dtlSchedFruit.jsp";
		  }
	//-----------------------------------------------------------------
	//  Go to the JSP
	//-----------------------------------------------------------------
	try { // Send down to the JSP
		getServletConfig().getServletContext().getRequestDispatcher(sendTo)
				.forward(request, response);
	} catch (Throwable theException) {
		
		System.out.println("---------------------");
	//	System.out.println("CtlRawFruit Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
		ParameterMessageBean.printParameters(request);
		System.out.println("---------------------");
		System.out.println(theException);
		theException.printStackTrace();
	}
	return;	
}
catch(Throwable theException)
{
	// uncomment the following line when unexpected exceptions
	// are occuring to aid in debugging the problem.
	theException.printStackTrace();
}
}
/* (non-Javadoc)
 * @see com.treetop.servlets.BaseServlet#pageInq(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
protected void pageInq(HttpServletRequest request, HttpServletResponse response) {
	
	
}
/* (non-Javadoc)
 * @see com.treetop.servlets.BaseServlet#pageList(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
protected void pageList(HttpServletRequest request, HttpServletResponse response) {
	  String requestType = (String) request.getAttribute("requestType");
	  //requestType = "listScaleTicket";
	  try
	  {
	  	if (requestType.equals("listScaleTicket"))
	  	{
			InqRawFruit irf = (InqRawFruit) request.getAttribute("inqViewBean");
			Vector sendIn = new Vector();
			sendIn.addElement(irf);
			BeanRawFruit brf = ServiceRawFruit.listLoads(sendIn);
			if (brf.getListLoads().size() > 50)
			{
				irf.setDisplayMessage("Only 50 Loads have been displayed, please narrow your selection to view additional loads");
			}
			sendIn = new Vector();
			sendIn.addElement(brf);
			irf.setListReport(sendIn);
			request.setAttribute("inqViewBean", irf);
	  	}
	  	if (requestType.equals("listAvailFruit") ||
	  		requestType.equals("listAvailFruitAll"))
	  	{
			InqAvailableFruit iaf = (InqAvailableFruit) request.getAttribute("inqViewBean");
			iaf.setBeanAvailFruit(ServiceAvailableFruit.listAvailableFruit(iaf));
			iaf.setListReport(iaf.getBeanAvailFruit().getAvailFruitByWhseDetail());
			request.setAttribute("inqViewBean", iaf);
	  	}
		if (requestType.equals("listSchedFruit"))
	  	{
			InqScheduledFruit isf = (InqScheduledFruit) request.getAttribute("inqViewBean");
			
			try{
				String inqLoadNumber = (String) request.getAttribute("inqLoadNumber");
				if (inqLoadNumber != null && !inqLoadNumber.trim().equals(""))
					isf.setInqScheduledLoadNumber(inqLoadNumber.trim());
			}catch(Exception e){}

			isf.setBeanAvailFruit(ServiceAvailableFruit.listScheduledLoads(isf));
			isf.setListReport(isf.getBeanAvailFruit().getScheduledLoadDetail());
			request.setAttribute("inqViewBean", isf);
	  	}
	  }
	  catch(Exception e)
	  {
		  System.out.println("Error Caught in CtlRawFruit.pageList(): " + e);
	  }
}
/* 
 * Created: 11/13/08 - TWalton
 * Use to deal with anything related to Update
 * 
 */
protected void pageUpd(HttpServletRequest request, HttpServletResponse response) {
	
  String requestType = (String) request.getAttribute("requestType");
  try
  {
	  
  	if (requestType.equals("update"))
  	{
		UpdRawFruitLoad urf = (UpdRawFruitLoad) request.getAttribute("updViewBean");
		Vector sendValues = new Vector();
		
		try
		{

			//run this if Quick Entry.
			if (!urf.getQuickEntry().trim().equals("") )
			{
				if (!urf.getSaveButton().equals(""))
				{
					//check scale ticket.
			   		  try {
			   			  String scaleTicketNo = urf.getScaleTicket();
			   			  String problem = ServiceRawFruit.verifyQuickEntry(scaleTicketNo);
			   			  
			   			  if (!problem.equals("")) {
			   				  urf.setDisplayMessage(urf.getDisplayMessage() + problem);
			   			  }
			   		  } catch(Exception e) {
			   			  urf.setDisplayMessage("Error occured running this Quick Entry.");
			   		  }
					
			   		  if(urf.getDisplayMessage().trim().equals(""))
			   		  {
			   			sendValues = new Vector();
						sendValues.addElement(urf);
						ServiceRawFruit.processLoadQuickEntry(sendValues);
			   		  }
				}
				
				
			}
			
			//run this if not Quick Entry.
			if (urf.getQuickEntry().trim().equals(""))
			{
				sendValues.addElement(urf);
				ServiceRawFruit.processLoad(sendValues);
			}
			sendValues = new Vector();
			sendValues.addElement(urf.getScaleTicket());
			sendValues.addElement(urf.getScaleTicketCorrectionSequence());
			BeanRawFruit brf = ServiceRawFruit.findLoad(sendValues);
			urf.setUpdBean(brf);
			
			
			if (urf.getQuickEntry().trim().equals(""))
			{
				// NON quick entry
				
				urf.loadFromBeanRawFruit(brf);
				
				//-----------------------------------------------
				//		// URL's and Comments -- Call the KeyValues Servlet
				//		//  ONLY on and UPDATE-ADD-DELETE (Saved)
				try {
					CtlKeyValues thisOne = new CtlKeyValues();
					thisOne.performTask(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}	
				
				// Obtain Comments for Each Load
				try {
					if (!urf.getScaleTicket().equals(""))
					{
						KeyValue kv = new KeyValue();
						kv.setEntryType("ScaleTicketComment");
						kv.setKey1(urf.getScaleTicket().trim());
						kv.setKey2(urf.getScaleTicketCorrectionSequence());
						brf.setComments(ServiceKeyValue.buildKeyValueList(kv));
					}
				} catch(Exception e)
				{
		//		 skip it. allowed to be empty
					brf.setComments(new Vector());
				}
			} else {
				//Quick entry
				if (!brf.getRfLoad().getScaleTicketNumber().equals("") &&
					 urf.getDisplayMessage().equals(""))
				{
					urf.loadFromBeanRawFruit(brf);
				}
				
				urf.loadQuickEntries();
				
			}
			
			
			
			 
			
			request.setAttribute("updViewBean", urf);
		}
		catch(Exception e)
		{
			// Problem Adding / Updating a Load
		}
		
  	}
		
		

  	
  	
  	if (requestType.equals("updateLot"))
  	{
  		UpdRawFruitLot urf = (UpdRawFruitLot) request.getAttribute("updViewBean");
		Vector sendValues = new Vector();
		try
		{
			sendValues.addElement(urf);
			ServiceRawFruit.processLot(sendValues);
		}
		catch(Exception e)
		{
			// Problem Adding / Updating a Load
			System.out.println("Problem Found " + e);
		}
        // Get the Lot Information From the Files		
		sendValues = new Vector();
		sendValues.addElement(urf.getScaleTicket());
		sendValues.addElement(urf.getScaleTicketCorrectionSequence());
		sendValues.addElement(urf.getScaleSequence());
		sendValues.addElement(urf.getPoNumber());
		sendValues.addElement(urf.getLotSequenceNumber());
		sendValues.addElement(urf.getLotNumber());
		BeanRawFruit brf = ServiceRawFruit.findLot(sendValues);
		//-----------------------------------------------
//		// URL's and Comments -- Call the KeyValues Servlet
//		//  ONLY on and UPDATE-ADD-DELETE (Saved)
		try {
			CtlKeyValues thisOne = new CtlKeyValues();
			thisOne.performTask(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}	   
		// Obtain Comments for Each Lot
		try {
			if (!urf.getLotNumber().equals(""))
			{
				KeyValue kv = new KeyValue();
				kv.setEntryType("LotComment");
				kv.setKey1(urf.getLotNumber().trim());
				brf.setComments(ServiceKeyValue.buildKeyValueList(kv));
			}
		} catch(Exception e)
		{
//		 skip it. allowed to be empty
			brf.setComments(new Vector());
		}	
	//	System.out.println("VIEW LOT Information");
		urf.setUpdBean(brf);
		urf.loadFromBeanRawFruit(brf);
		request.setAttribute("updViewBean", urf);
  	}
  	//**********************************************************
  	// Update Available Fruit By Warehouse
  	if (requestType.trim().equals("updAvailFruit") ||
  		requestType.trim().equals("updAvailFruitToPurchase"))
  	{
  		UpdAvailableFruit updAvailFruit = (UpdAvailableFruit) request.getAttribute("updViewBean");
  		BeanAvailFruit baf = new BeanAvailFruit();
  		if (!updAvailFruit.getSaveButton().trim().equals(""))
  		{
  		   BeanAvailFruit updValues = ServiceAvailableFruit.updateAvailableFruit(updAvailFruit);
  		   //email(request);
  		}
  		try
  		{
  		   updAvailFruit.setBeanAvailFruit(ServiceAvailableFruit.findWhseFruit(updAvailFruit));
  		   if (updAvailFruit.getDisplayMessage().trim().equals(""))
  		   {
  			   updAvailFruit.loadFromBeanAvailFruit(updAvailFruit.getBeanAvailFruit());
  			   updAvailFruit.build10DropDownDualVectors();
  		   }
  		   
  		} catch(Exception e)
  		{
  			System.out.println("CtlRawFruit.pageUpdate, found error when requesting information about Available Fruit By Whse. " + e);
  		}
  		if (requestType.trim().equals("updAvailFruit"))
  		{
  		  try
  		  {
  			BeanAvailFruit addSchedLoads = updAvailFruit.getBeanAvailFruit();
  			// get the List of Scheduled Loads
  			InqScheduledFruit isf = new InqScheduledFruit();
  			isf.setEnvironment(updAvailFruit.getEnvironment());
  			isf.setRequestType(requestType);
  			isf.setInqWhseNo(updAvailFruit.getWhseNo());
  			isf.setInqLocAddNo(updAvailFruit.getLocAddNo());
  			BeanAvailFruit findSchedLoads = ServiceAvailableFruit.listScheduledLoads(isf);
  			//BeanAvailFruit findSchedLoads = ServiceAvailableFruit.findScheduledLoad(updAvailFruit);
  			//----------------------------------------------------
  			AvailFruitByWhse updAvail = addSchedLoads.getAvailFruitByWhse();
  			updAvail.setAllScheduledQtyTotal(findSchedLoads.getAvailFruitByWhse().getAllScheduledQtyTotal());
  			addSchedLoads.setAvailFruitByWhse(updAvail);
  			addSchedLoads.setScheduledLoadDetail(findSchedLoads.getScheduledLoadDetail());
  			updAvailFruit.setBeanAvailFruit(addSchedLoads);
  			request.setAttribute("updViewBean", updAvailFruit);
  		  } catch(Exception e)
  		  {
  		  	System.out.println("CtlRawFruit.pageUpdate, found error when requesting information about Scheduled Loads of Fruit By Whse. " + e);
  		  }
  		}
  		//	-----------------------------------------------
		// Comments -- Call the KeyValues Servlet
  		// Only need to call this when the SAVE button is hit
  		try {
  			if (!updAvailFruit.getSaveButton().trim().equals(""))
  	  		{
			   CtlKeyValues thisOne = new CtlKeyValues();
			   thisOne.performTask(request, response);
  	  		}
		} catch (Exception e) {
			e.printStackTrace();
		}	   
		// Obtain Comments for Each Warehouse and Warehouse Location Combination
		try {
			if (!updAvailFruit.getWhseNo().trim().equals("") &&
				!updAvailFruit.getLocAddNo().trim().equals(""))
			{
				String entryType = "AvailableFruitComment";
				if (requestType.trim().equals("updAvailFruitToPurchase"))
					entryType = "ToPurchaseFruitComment";
					
				KeyValue kv = new KeyValue();
				kv.setEnvironment(updAvailFruit.getEnvironment());
				kv.setEntryType(entryType);
				kv.setKey1(updAvailFruit.getWhseNo().trim());
				kv.setKey2(updAvailFruit.getLocAddNo().trim());
				updAvailFruit.setListComments(ServiceKeyValue.buildKeyValueList(kv));
			}
		} catch(Exception e)
		{
//		 skip it. allowed to be empty
			updAvailFruit.setListComments(new Vector());
		}	
  	}
//  **********************************************************
  	 if(requestType.trim().equals("unCancelSchedLoad"))
  	 {
  		try
		{
  			 DtlScheduledFruit dsf = (DtlScheduledFruit) request.getAttribute("dtlViewBean");
  			
// 				put in code for Un-Cancelling a load -- call to a service to do the work
				  CommonRequestBean crb = new CommonRequestBean();
			      crb.setEnvironment(dsf.getEnvironment().trim());
			      crb.setIdLevel1(dsf.getLoadNumber());
			      crb.setIdLevel2("");
			      ServiceAvailableFruit.updateScheduleLoadStatus(crb);
				
		} catch(Exception e){
			System.out.println("CtlRawFruit.pageUpdate, found error when Un-Cancelling a Scheduled Load. " + e);
		} 
  	 }
// **************************************************************  	
  	// Including a "TRANSFER" load
  	if (requestType.trim().equals("updSchedFruit") ||
  		requestType.trim().equals("addSchedFruitLoad") ||
  		requestType.trim().equals("updSchedTransfer"))
  	{
  		UpdScheduledFruit updSched = (UpdScheduledFruit) request.getAttribute("updViewBean");
  		try{
  			if (!updSched.getSaveButton().trim().equals(""))
  			{ // button was pushed, need to update the information
  				UpdScheduledFruit updSchedFruit = (UpdScheduledFruit) request.getAttribute("updViewBean");
  				if (requestType.trim().equals("updSchedTransfer"))
  				{
  					if (updSchedFruit.getListLoads().size() > 0)
  					  updSchedFruit = (UpdScheduledFruit) updSchedFruit.getListLoads().elementAt(0);
  				}
  				// May need to process update records... to include only ones with bin quantity....
  				updSchedFruit.processAdd();
  				if (updSched.getDisplayMessage().trim().equals(""))
  				{
  				   if (updSchedFruit.listScheduledFruitDetail.size() == 0)
  				   { // update the Status to be Cancelled -- if all details are 0
  				      CommonRequestBean crb = new CommonRequestBean();
  	  			      crb.setEnvironment(updSched.getEnvironment().trim());
  	  			      crb.setIdLevel1(updSched.getLoadNumber());
  	  			      crb.setIdLevel2("C");
  	  			      ServiceAvailableFruit.updateScheduleLoadStatus(crb);
  				   }
  				   else
  				      ServiceAvailableFruit.updateScheduledLoad(updSchedFruit);
  				}
  	//			System.out.println("what to do When the information should be updated?");
  			}
  			
  			try
  	  		{
  			   if (updSched.getDisplayMessage().trim().equals("") &&
  				   (!requestType.trim().equals("addSchedFruitLoad") ||
  				    !updSched.getSaveButton().trim().equals("")))
  			   {
  				   CommonRequestBean crb = new CommonRequestBean();
  				   crb.setEnvironment(updSched.getEnvironment().trim());
  				   crb.setIdLevel1(updSched.getLoadNumber());
  				   updSched.setBeanAvailFruit(ServiceAvailableFruit.findScheduledLoad(crb));	
  			   // load retrieved data into the upd screen
  				   updSched.loadFromBeanAvailFruit();
  				   if (requestType.trim().equals("updSchedTransfer"))
  				   {
  					   Vector newLoad = new Vector();
  					   newLoad.addElement(updSched);
  					   updSched.setListLoads(newLoad);
  				   }
  			   }
  			    request.setAttribute("updViewBean", updSched);
  	  		} catch(Exception e)
  	  		{
  	  			System.out.println("CtlRawFruit.pageUpdate, found error when requesting information about a scheduled Load. Load Number: " + updSched.getLoadNumber() + ":  " + e);
  	  		}
  	  		if (requestType.equals("updSchedFruit") ||
  	  			requestType.equals("updSchedTransfer") ||
  	  			requestType.equals("unCancelSchedLoad"))
  	  		{
  	  			//-----------------------------------------------
  	  			// Comments -- Call the KeyValues Servlet
  	  			// Only need to call this when the SAVE button is hit
  	  			try {
  	  				if (!updSched.getSaveButton().trim().equals(""))
  	  				{
  	  					CtlKeyValues thisOne = new CtlKeyValues();
  	  					// Added Code for Entry type and Key 1 -- 6/27/12 TWalton
  	  					request.setAttribute("EntryType", "ScheduledLoadComment");
  	  					request.setAttribute("Key1", updSched.getLoadNumber().trim());
  	  					thisOne.performTask(request, response);
  	  				}
  	  			} catch (Exception e) {
  	  				e.printStackTrace();
  	  			}	   
  	  			// Obtain Comments for Each Scheduled Load
  	  			try {
  	  				if (!updSched.getLoadNumber().trim().equals(""))
  	  				{
  	  					KeyValue kv = new KeyValue();
  	  					kv.setEnvironment(updSched.getEnvironment());
  	  					kv.setEntryType("ScheduledLoadComment");
  	  					kv.setKey1(updSched.getLoadNumber().trim());
  	  					updSched.setListComments(ServiceKeyValue.buildKeyValueList(kv));
  	  				}
  	  			} catch(Exception e)
  	  			{
//  				 skip it. allowed to be empty
  	  				updSched.setListComments(new Vector());
  	  			}
  	  		}
//  	  	System.out.println("UPDATE scheduled Fruit -- from the Servlet CtlRawFruit - method UpdPage");
//  	  	System.out.println("send in the Update Scheduled Fruit to the Service to actually be added");
  		}catch(Exception e){
  			System.out.println("Stop" + e);
  		}
  	}
  	//*********************************************************************
  	//  ADD New Scheduled Loads -- Loads Scheduled from Available Fruit
  	if (requestType.trim().equals("addSchedFruit") ||
  		requestType.trim().equals("schedTransfer"))
  	{
  		UpdScheduledFruit updSchedFruit = (UpdScheduledFruit) request.getAttribute("updViewBean");
  		// run through and determine if the load has to be added one load at a time
  		if (updSchedFruit.getListLoads().size() > 0)
  		{ 
  			for (int x = 0; x < updSchedFruit.getListLoads().size(); x++)
  			{
  				try{
  				 UpdScheduledFruit addRecord = (UpdScheduledFruit) updSchedFruit.getListLoads().elementAt(x);
  				 addRecord.processAdd();
  				 if(addRecord.getListScheduledFruitDetail() != null && addRecord.getListScheduledFruitDetail().size() > 0)
  				 {
  					ServiceAvailableFruit.addScheduledLoads(addRecord);
  				 }
  				}catch(Exception e){}
  			}
  		}
  		//System.out.println("ADD scheduled Fruit -- from the Servlet CtlRawFruit - method UpdPage");
  		//System.out.println("send in the Add Scheduled Fruit to the Service to actually be added");
  	}
  	//************************************************************************
  	// Delete or Cancel a Scheduled Load
  	if (requestType.trim().equals("deleteSchedLoad") ||
  		requestType.trim().equals("cancelSchedLoad") ||
  		requestType.trim().equals("receiveSchedLoad") ||
  		requestType.equals("goToReceiveSchedLoad") ||
  		requestType.trim().equals("unreceiveSchedLoad"))
  	{
  		DtlScheduledFruit dsf = (DtlScheduledFruit) request.getAttribute("dtlViewBean");
  		CommonRequestBean crb = new CommonRequestBean();
  		crb.setEnvironment(dsf.getEnvironment());
  		crb.setIdLevel1(dsf.getLoadNumber());
  		if (requestType.trim().equals("deleteSchedLoad"))
  		  crb.setIdLevel2("D");
  		if (requestType.trim().equals("cancelSchedLoad"))
  		  crb.setIdLevel2("C");
  		if (requestType.trim().equals("receiveSchedLoad") ||
  			requestType.trim().equals("goToReceiveSchedLoad"))
  		{
  		  crb.setIdLevel2("R");
  		  crb.setIdLevel3(dsf.getEmptyBins());
  		}
  		if (requestType.trim().equals("unreceiveSchedLoad"))
    	  crb.setIdLevel2("");
  		crb.setUser(dsf.getUserProfile());
 		ServiceAvailableFruit.updateScheduleLoadStatus(crb);
 		
 	    if (!requestType.trim().equals("deleteSchedLoad") &&
 	         !dsf.getComment().trim().equals(""))
 	    { // Add the comment to the list of comments
 	       KeyValue kvAdd = new KeyValue();
	       kvAdd.setEnvironment(dsf.getEnvironment().trim());
	       kvAdd.setEntryType("ScheduledLoadComment");
	       kvAdd.setKey1(dsf.getLoadNumber().trim());
	       kvAdd.setKey2("");
	       kvAdd.setKey3("");
	       kvAdd.setKey4("");
	       kvAdd.setKey5("");
	       kvAdd.setLastUpdateUser(dsf.getUserProfile());
	       kvAdd.setValue(dsf.getComment().trim());
		   kvAdd.setDescription("");
	       kvAdd.setSequence("0");
	       kvAdd.setStatus("");
		   kvAdd.setDeleteUser("");
		   try{
	         ServiceKeyValue.addKeyValue(kvAdd);
		   }catch(Exception e){
	 	     System.out.println("error found in the addKeyValues for a Method:CtlRawFruit.pageUpd: " + e);
		   }
 	    }
  	}
  }
  catch(Exception e)
  {
		// Use for Debug
		System.out.println("Error Found in CtlRawFruit.pageUpd(): " + e);
  }
}
/* (non-Javadoc)
 * @see com.treetop.servlets.BaseServlet#pageDtl(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
protected void pageDtl(HttpServletRequest request, HttpServletResponse response) {
  // Use the Dtl Page Section to retrieve Date for Display for the Reports
  try {
	  String requestType = (String) request.getAttribute("requestType");
  if (requestType.equals("dtlSchedFruit") ||
	  requestType.equals("deleteSchedLoad") ||
	  requestType.equals("cancelSchedLoad") ||
	  requestType.equals("receiveSchedLoad") ||
	  requestType.equals("unreceiveSchedLoad"))
  {
	  DtlScheduledFruit dsf = (DtlScheduledFruit) request.getAttribute("dtlViewBean");
	  
	   CommonRequestBean crb = new CommonRequestBean();
	   crb.setEnvironment(dsf.getEnvironment().trim());
	   crb.setIdLevel1(dsf.getLoadNumber());
	   dsf.setBeanAvailFruit(ServiceAvailableFruit.findScheduledLoad(crb));	
	   //	 Obtain Comments for Each Load
		try {
			if (!dsf.getLoadNumber().equals(""))
			{
				KeyValue kv = new KeyValue();
				kv.setEntryType("ScheduledLoadComment");
				kv.setEnvironment(dsf.getEnvironment());
				kv.setKey1(dsf.getLoadNumber().trim());
				dsf.setListComments(ServiceKeyValue.buildKeyValueList(kv));
			}
		} catch(Exception e)
		{
			// skip it. allowed to be empty
			dsf.setListComments(new Vector());
		}	
	  request.setAttribute("dtlViewBean", dsf);
  }
  else
  {
	  BeanRawFruit rfl = new BeanRawFruit();
	  DtlRawFruitLoad drf = (DtlRawFruitLoad) request.getAttribute("dtlViewBean");
	  Vector sendValues = new Vector();
	  sendValues.addElement(drf.getScaleTicket());
	  sendValues.addElement(drf.getScaleTicketCorrectionSequence());
	  if (requestType.equals("generatePO"))
	  {
		  sendValues.addElement("PRD");
		  sendValues.addElement(SessionVariables.getSessionttiProfile(request, response));
		  sendValues.addElement(com.treetop.Security.getUserAuthorization(request));
		  sendValues.addElement(drf.getReceivingDate());
		  rfl = ServiceRawFruit.generatePO(sendValues);
	  }else{
	    rfl = ServiceRawFruit.findLoad(sendValues);
	  }

		// Obtain Comments for Each Load
	  try {
			if (!drf.getScaleTicket().equals(""))
			{
				KeyValue kv = new KeyValue();
				kv.setEntryType("ScaleTicketComment");
				kv.setKey1(drf.getScaleTicket().trim());
				kv.setKey2(drf.getScaleTicketCorrectionSequence());
				rfl.setComments(ServiceKeyValue.buildKeyValueList(kv));
			}
		} catch(Exception e)
		{
//		 skip it. allowed to be empty
			rfl.setComments(new Vector());
		}	
		//	System.out.println("VIEW Information");
		 drf.setDtlBean(rfl);
		 drf.buildInvoiceCalculatedFields();
	 // System.out.println("VIEW Returned DTL Information ");
	  request.setAttribute("dtlViewBean", drf);
  }	  
   } catch(Exception e)
   {
   	   System.out.println("Problem occurred in pageDtl of the CtlRawFruit Servlet:: " + e);
   }
   return;		
}
/* 
 * Created: 1/5/09 - TWalton
 * Use to deal with anything related to Deleteing Records
 */
private void deleteRecords(HttpServletRequest request, HttpServletResponse response) {
	
	String requestType = (String) request.getAttribute("requestType");
  try
  {
  	if (requestType.equals("deletePO"))
  	{
  		UpdRawFruitLoad urf = (UpdRawFruitLoad) request.getAttribute("updViewBean");
  		if (!urf.getScaleTicket().trim().equals("") &&
  			!urf.getScaleSequence().trim().equals(""))
  		{
			try
			{
				Vector sendValues = new Vector();
				sendValues.addElement(urf);
			    ServiceRawFruit.delete("deletePO", sendValues);
 		    }
	  	    catch(Exception e)
		    {
			  // Problem Adding / Updating a Load
		    }
  		}
  	}
  	if (requestType.equals("deleteLot"))
  	{
  		UpdRawFruitLoad urf = (UpdRawFruitLoad) request.getAttribute("updViewBean");
  		if (!urf.getScaleTicket().trim().equals("") &&
  			!urf.getScaleSequence().trim().equals(""))
  		{
			try
			{
				Vector sendValues = new Vector();
				sendValues.addElement(urf);
			    ServiceRawFruit.delete("deleteLot", sendValues);
 		    }
	  	    catch(Exception e)
		    {
			  // Problem Adding / Updating a Load
		    }
  		}
  	}
  	if (requestType.equals("deleteScaleTicket"))
  	{
  		InqRawFruit irf = (InqRawFruit) request.getAttribute("inqViewBean");
  		if (!irf.getScaleTicket().trim().equals("") &&
  			!irf.getScaleTicketCorrectionSequence().trim().equals(""))
  		{
			try
			{
				Vector sendValues = new Vector();
				sendValues.addElement(irf);
			    ServiceRawFruit.delete("deleteLoad", sendValues);
 		    }
	  	    catch(Exception e)
		    {
			  // Problem Adding / Updating a Load
		    }
  		}
  	}
  }
  catch(Exception e)
  {
		// Use for Debug
		System.out.println("Error Found in CtlRawFruit.deleteRecords() - requestType: " + requestType + " :: " + e);
  }
    return;
}
/* 
 * Created: 3/20/09 - TWalton
 * Use to deal with anything related to Deleteing Records
 */
private void copyRecords(HttpServletRequest request, HttpServletResponse response) {
	
	String requestType = (String) request.getAttribute("requestType");
  try
  {
//	  fix this
 	  InqRawFruit irf = (InqRawFruit) request.getAttribute("inqViewBean");
  		if (!irf.getScaleTicket().trim().equals("") &&
  			!irf.getScaleTicketCorrectionSequence().trim().equals(""))
  		{
			try
			{
				Vector sendValues = new Vector();
				sendValues.addElement(irf);
			    ServiceRawFruit.copy(requestType, sendValues);
 		    }
	  	    catch(Exception e)
		    {
			   System.out.println("Error Found in CtlRawFruit.copyRecords() ");
			   System.out.println("  found error when attempting the ServiceRawFruit.copy::" + e);
		    }
  		}
  }
  catch(Exception e)
  {
		// Use for Debug
		System.out.println("Error Found in CtlRawFruit.copyRecords() - requestType: " + requestType + " :: " + e);
  }
    return;
}
/* (non-Javadoc)
 * @see com.treetop.servlets.BaseServlet#pageDtl(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
protected void pageBld(HttpServletRequest request, HttpServletResponse response) {
  // Use the Bld Page Section to build work file information for Reporting purposes
  try {
//	  BldRawFruit brf = (BldRawFruit) request.getAttribute("bldViewBean");
	  String requestType = (String) request.getAttribute("requestType");
	  if (requestType.equals("bldLotAttribute"))
	  {
		  ServiceLotWorkFiles.buildRawFruit(requestType);
	//	  System.out.println("Done with build");
	  }
   } catch(Exception e)
   {
   	   System.out.println("Problem occurred in pageBld of the CtlRawFruit Servlet:: " + e);
   }
   return;		
}
/**
 * Generate and send an E-mail every time the Available Fruit gets updated
 * Creation date: (11/17/2010 )
 *   Updated 8/18/06 TWalton
 */
private void email(javax.servlet.http.HttpServletRequest request) 
{
   try
   {
	  String requestType = (String) request.getAttribute("requestType");
	  if (requestType.trim().equals("updAvailFruit"))
	  {
	  	 UpdAvailableFruit updAvailFruit = (UpdAvailableFruit) request.getAttribute("updViewBean");
	  	 if (!updAvailFruit.getSaveButton().trim().equals(""))
	  	 {
	  		String[] to          = new String[1];
	  	    to[0]                = "roger.lucas@treetop.com"; 
	  		//to[0]                = "teri.walton@treetop.com"; 
	  	    String[] cc          = new String[1];
	    	cc[0]                = "teri.walton@treetop.com"; 
		    String[] bcc         = new String[0];	
		    String subject       = updAvailFruit.getUpdateUser() + " has updated Available Fruit.";
		    String body          = updAvailFruit.getUpdateUser() + " has updated Available Fruit.  "
		                          + "For Warehouse " + updAvailFruit.getWhseNo() + ".";
		    if (updAvailFruit.getEnvironment().trim().equals("TST"))
		    {
		    	subject = subject + "  In Environment TST.";
		    	body = body + "  In Environment TST.";
		    }
		    
		    String[] key1 = new String[0];
		    String[] key2 = new String[0];
		
		// define parameters required for email aduit log.
		  String system = "AvailFruit";
		  Email sendingEmail   = new Email();
		  String sentMessage         = sendingEmail.sendEmail(to,
			                                          cc,
			                                          bcc,
			                                          "teri.walton@treetop.com",
			                                          subject,
			                                          body,
			                                          system,
			                                          key1,
													  key2);
	  	 }
	  }	 
	  }
   catch(Exception e)
   {
      System.out.println("Exception found in CtlRawFruit.email() : " + e);	   
   } 	
}


	private void transformQuickEntry(UpdRawFruitLoad updRFLoad) {

		// loop through list of details coming in from the screen
		for (int i=0; i<updRFLoad.getListQuickEntries().size(); i++) {
			
			UpdRawFruitQuickEntry updRFQE = (UpdRawFruitQuickEntry)  updRFLoad.getListQuickEntries().elementAt(i);
			
			String stopHere = "check out the incoming viewbean";
			
		}
		
	}




}
