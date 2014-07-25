/*
 * Created on Jul 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.app.coa;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.treetop.Email;
import com.treetop.GetDate;
import com.treetop.SessionVariables;
import com.treetop.SystemDate;
import com.treetop.services.*;
import com.treetop.app.CtlKeyValues;
import com.treetop.app.function.CtlFunction;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.data.UserFile;
import com.treetop.servlets.*;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.*;
import com.treetop.viewbeans.ParameterMessageBean;

/**
 * @author twalto
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CtlCOANew extends BaseServlet {

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
			requestType = "inquiry";
		request.setAttribute("requestType", requestType);
		request.setAttribute("msg", "");
		//TESTING//
		//requestType = "inquiryAvailable";
		//------------------------------------------------------------------
		// Call Security
		String urlAddress = "/web/CtlCOANew";

		if (!callSecurity(request, response, urlAddress).equals("")) {
			if (!callSecurity(request, response, "/web/CtlCOANew?requestType=preview").equals("")) {
				try {
					response.sendRedirect("/web/TreeNetInq?msg="
							+ SessionVariables.getSessionttiSecStatus(request,
									response));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			if (!requestType.equals("inquiry"))
			{	
			  requestType = "preview";
			  request.setAttribute("requestType", requestType);
			}
		}
		//------------------------------------------------------------------
		//Passed Security
		//*********************************
		// Default Screen
		String sendTo = "/APP/COA/inqCOA.jsp";
		//*********************************
		// TEST FOR ALL
		InqCOA ic = new InqCOA();
		ic.populate(request);
		ic.setUpdateUser(SessionVariables.getSessionttiProfile(request, response));
		ic.validate();
		if (!requestType.equals(ic.getRequestType()))
			ic.setRequestType(requestType);
		if (!ic.getDisplayErrors().equals(""))
			requestType = "inquiry";
		request.setAttribute("inqViewBean", ic);
		//-----------------------------------------------------------------
		//   UPDATE PAGE
		//-----------------------------------------------------------------
		if (requestType.equals("goToUpdate") ||
			requestType.equals("update")) {
			pageUpd(request, response);
			requestType = (String) request.getAttribute("requestType");
			ic.setRequestType(requestType);
			if (requestType.equals("goToUpdate"))
			   sendTo = "/APP/COA/updateCOA.jsp";
		}		
		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//-----------------------------------------------------------------
		// Main Inquiry and List Pages
		if (requestType.equals("inquiry") || 
			requestType.equals("build") ||
			requestType.equals("preview") ||
			requestType.equals("fax") ||
			requestType.equals("email")) {
			if (requestType.equals("build") ||
				requestType.equals("preview") ||
				requestType.equals("fax") ||
				requestType.equals("email"))
			{
				BuildCOA bc = new BuildCOA();
				bc.populate(request);
				bc.validate();
				if (!requestType.equals(bc.getRequestType()))
					bc.setRequestType(requestType);
				request.setAttribute("buildViewBean", bc);
				if (bc.getDisplayErrors().trim().equals("") &&
					!bc.getSaveChanges().equals("") &&
					((String) request.getAttribute("update")) == null)
				{
					request.setAttribute("requestType", requestType);
					pageUpd(request, response);
				}
				pageBuild(request, response);
				ic = (InqCOA) request.getAttribute("inqViewBean");
				if (ic.getRequestType().equals("build")||
					ic.getRequestType().equals("preview") ||
					ic.getRequestType().equals("fax") ||
					ic.getRequestType().equals("email"))
				{	
					
					if (ic.getRequestType().equals("preview"))
					{
					   pageDtl(request, response);	
					   sendTo = "/APP/COA/previewCOA.jsp";
					   requestType = "preview";	
					}
					else
					{
					   if (ic.getRequestType().equals("email") ||
						   ic.getRequestType().equals("fax"))
					   {
						   emailCOA(request);
					   }
					   sendTo = "/APP/COA/buildCOA.jsp";
					   requestType = "build";
					}
				}
				else
				{
//					 Decision Made within pageList Method as to which screen to display
					requestType = "inquiry";
				}
			}
			// INQUIRY PAGE
			if (requestType.equals("inquiry"))
			{	
				pageInq(request, response);
			}
			request.setAttribute("inqViewBean", ic);

		}
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
			System.out.println("CtlCOANew Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
			ParameterMessageBean.printParameters(request);
			System.out.println("---------------------");
			System.out.println(theException);
			theException.printStackTrace();
		}
		return;

	}

	/*
	 * Retrieve and Build everthing needed for display on the Inquiry Page
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
		// Currently we do NOT have a List page for COA's
	}

	/*
	 * Use for Building the HTML - for Preview and For Email
	 * 
	 * @see com.treetop.servlets.BaseServlet#pageDetail(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void pageDtl(HttpServletRequest request,
						   HttpServletResponse response) {
	    try
	    {
	    	String requestType = (String) request.getAttribute("requestType");
	    	BuildCOA bldCOA = (BuildCOA) request.getAttribute("buildViewBean");
	    	String pageHTML = "";
		//	--------------------------------------------------------------------------------
	    	if (bldCOA.getCoaType().equals("LOT"))
	    	  pageHTML = buildHTMLCOAForLOT(request);		
	    	if (bldCOA.getCoaType().equals("CO"))
	    	  pageHTML = buildHTMLCOAForCO(request);	
	    	if (bldCOA.getCoaType().equals("DO"))
	    	  pageHTML = buildHTMLCOAForDO(request);
	    	
	    	if (requestType.equals("preview"))
	    		request.setAttribute("pageHTML", pageHTML);
	    	
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Error Found in pageDtl: " + e);
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
	try
	{
		String requestType = (String) request.getAttribute("requestType");
		//--------------------------------------------------------------------------------
		Vector sendParms   = new Vector();
		// BUILD SCREEN - UPDATE HEADER
		if (requestType.equals("build"))
		{
		   BuildCOA bc = (BuildCOA) request.getAttribute("buildViewBean");
		   try
		   {
		   	
		   	  sendParms.addElement(bc);
		   	  ServiceCOA.updateCOA("updateCOA", sendParms);
		   }
		   catch(Exception e)
		   {
		   	  bc.setDisplayErrors("This COA Was NOT Updated: " + e);
		   }
		   request.setAttribute("buildViewBean", bc);
		}	
		if (requestType.equals("update"))
		{
			UpdCOA uc = new UpdCOA();
			uc.populate(request);
			uc.populateAttributeSequence(request);
			uc.validate();
			// Update the Comment Section of the COA
			try
			{
				if (!uc.getCoaType().equals("LOT"))
				{
				   sendParms.addElement(uc);
				   ServiceCOA.updateCOA("updateCOALine", sendParms);
				}
			}
			catch(Exception e)
			{
				System.out.println("UpdateCOALine Problem: " + e);
			}
			if (uc.getListAttributeSequence().size() > 0)
			{	
			   for (int x = 0; x < uc.getListAttributeSequence().size(); x++)
			   {	
			   	  try
				  {
			   	     sendParms = new Vector();
			         uc.setLineAttrSequence((String) uc.getListAttributeSequence().elementAt(x)); 
				     uc.setAttributeID((String) uc.getListAttributeID().elementAt(x));
				     sendParms.addElement(uc);
				     ServiceCOA.updateCOA("updateCOAAttr", sendParms);
				  }
			   	  catch(Exception e)
				  {}
			   }
			}
			request.setAttribute("requestType", "build");
			request.setAttribute("update", "update");
		}		
		if (requestType.equals("goToUpdate"))
		{
			InqCOA ic = (InqCOA) request.getAttribute("inqViewBean");
		    UpdCOA uc = new UpdCOA();
			BeanCOA forScreen = new BeanCOA();
			try
			{
				forScreen = ServiceCOA.buildCOA(ic);
				uc.setInqDistributionOrder(ic.getInqDistributionOrder());
				uc.setInqSalesOrder(ic.getInqSalesOrder());
				uc.setInqLot(ic.getInqLot());
				uc.setLineNumber(ic.getLineNumber());
				uc.setLineSuffix(ic.getLineSuffix());
				uc.setEnvironment(ic.getEnvironment());
				if (!uc.getInqSalesOrder().equals(""))
				   uc.setCoaType("CO");
				else
				{
				   if (!uc.getInqDistributionOrder().equals(""))
					   uc.setCoaType("DO");
				   else
				   {
					   if (!uc.getInqLot().equals(""))
						   uc.setCoaType("LOT");
				   }
				}
				uc.setReturnBean(forScreen);
				// Build Vectors to display information in the correct order
				// Flip the Table OVER
				request.setAttribute("updViewBean", uc);			
				pageUpdFlip(request, response);
			}
			catch(Exception e)
			{
				// Need to add Error Data
				System.out.println("Error Found Building the Screen: " + e);
			}
		}
	}
	catch(Exception e)
	{
	}
}

/**
 * This method will build the HTML code for
 *   the preview page (will also be used as
 *   the body of the email.
 *
 * Creation date: (11/12/2003 10:01:30 AM)
 * Update 8/18/06 - TWalton
 * Updated 2/23/08 - TWalton -- used with Movex
 */
private String buildHTMLCOAForCO(javax.servlet.http.HttpServletRequest request) 
{
   StringBuffer bldHTML = new StringBuffer();
   String pageBreak     = "";
try
{
	  BuildCOA bldCOA = (BuildCOA) request.getAttribute("buildViewBean");
	  if (bldCOA.getListReport().size() > 0)
	  {	
		  
	  BeanCOA  bc     = (BeanCOA)bldCOA.getListReport().elementAt(0);
	  int lines = bc.getListSOLineItems().size();
	     if (lines > 0)
	     { 	
	  //	String requestType = (String) request.getAttribute("requestType");
//	Vector soDetailVector = (Vector) request.getAttribute("soDetailVector");
//	SalesOrderDetail headerInfo = (SalesOrderDetail) soDetailVector.elementAt(0);
     // 1/14/13 TW -  added in the encoding so the 3/8 character would work
	bldHTML.append("<html>");
	bldHTML.append("<%@page language=\"java\" contentType=\"text/html; charset=utf-8\" pageEncoding=\"utf-8\"%>");
	bldHTML.append("<head>");
    bldHTML.append(HTMLHelpers.styleSheetHeadSectionV2());
    bldHTML.append("</head><body>");
    String skip = "N";
    // 8/7/12 -- TWalton -- took out the save by item,,, each LINE gets displayed
    //   String saveItem = "";
        for (int x = 0; x < lines; x++)	
        {
        	SalesOrderLineItem	thisItem = (SalesOrderLineItem) bc.getListSOLineItems().elementAt(x);
        	String skipByItem = "N";
        	if (thisItem.getItemClass().getItemType().equals("250") ||
   	        thisItem.getItemClass().getItemType().equals("260") ||
   	        thisItem.getItemClass().getItemType().equals("270") ||
   	        thisItem.getItemClass().getItemType().equals("280"))
        		//||
   	        //saveItem.trim().equals(thisItem.getItemClass().getItemNumber()))
        		   skipByItem = "Y";
          //saveItem = thisItem.getItemClass().getItemNumber();	
  	  if (skipByItem.equals("N"))
  	  {
      	if (bc.getListCOADetailAttributes().size() == 0 )
      	{	
      		skip = "Y";
      		x = lines;
      	}
       if (skip.equals("N") && skipByItem.equals("N"))
       {	
      	 COADetailAttributes cda      = (COADetailAttributes) bc.getListCOADetailAttributes().elementAt(x);
      	 int z = x;
      	 while (!thisItem.getLineNumber().equals(cda.getLineNumber()))
      	 {
      		z++;
      		cda  = (COADetailAttributes) bc.getListCOADetailAttributes().elementAt(z);
      	 }
      	 Vector contactInfo = getContactInfoByPerson(bldCOA.getCoaType(), thisItem.getOrderNumber(), cda.getSignatureQA());
// Heading Area of EACH PAGE      	
 	 bldHTML.append("<table" + pageBreak + "><tr>");
 	 bldHTML.append("<td style=\"width:20%\"> " +
 	 		"<img src=\"/web/Include/images/TT_logo2C-2013.png\" style='margin:50px 20px'>" +
 	 		"</td>");
     bldHTML.append("<td class=\"td0424\" style=\"text-align:center\"><b>Certificate of Analysis</b></td>");
     bldHTML.append("<td style=\"width:20%\"> &nbsp;");
//     if(cda.getSignatureQA().trim().equals("AOSORI") 
//    	|| cda.getSignatureQA().trim().equals("BFRESZ") 
//	    || cda.getSignatureQA().trim().equals("FCERPA"))
//	  {
//	   bldHTML.append("<img src=\"https://image.treetop.com/webapp/TreeNetImages/SabrosoLogoTiny.png\">");
//	  }
     bldHTML.append("</td></tr></table>");
	bldHTML.append(buildHTMLCOAForCOHeader(thisItem, cda));
    //***************************
    // Item Line
	 bldHTML.append("<table cellspacing=\"0\" style=\"width:100%\" >");
     bldHTML.append("<tr class = \"tr02\">");
     bldHTML.append("<td class= \"td0320\" ><b>");
//     if (cda.getSignatureQA().equals("AOSORI")
//    		 	|| cda.getSignatureQA().equals("BFRESZ")
//    		 	|| cda.getSignatureQA().equals("FCERPA"))
//    		 { // 7/8/10 if one of the Sabroso QA managers is on the COA the Sabroso image will display before the description
//    		 	System.out.println("stop here");
//    		 	bldHTML.append("<img style=\"height:25px;\"src=\"https://image.treetop.com/webapp/TreeNetImages/SabrosoLogoTiny.png\">&nbsp;&nbsp;");   	
//    		 }     
     bldHTML.append(thisItem.getItemClass().getItemNumber());
     bldHTML.append("&nbsp;&nbsp;");
   if (cda.getSignatureQA().equals("AOSORI")
 	|| cda.getSignatureQA().equals("BFRESZ")
 	|| cda.getSignatureQA().equals("SLEMKE")
 	|| cda.getSignatureQA().equals("FCERPA"))
 { // 7/8/10 if one of the Sabroso QA managers is on the COA the Sabroso image will display before the description
// 	System.out.println("stop here");
 	bldHTML.append("<img style=\"height:25px;\"src=\"https://image.treetop.com/webapp/TreeNetImages/SabrosoLogoTiny.png\">&nbsp;&nbsp;");   	
 }
     bldHTML.append(thisItem.getItemClass().getItemDescription());
     bldHTML.append("</b></td>");
              
         if (cda.getShowAttributeModel().equals("Y"))
         {
         	bldHTML.append("<td class= \"td0320\" ><b>");
         	bldHTML.append(thisItem.getItemClass().getAttributeModel());
         	bldHTML.append("</b></td>");
         }
         // Possibly have to Add Spec Into here also

    bldHTML.append("</tr></table>");

    bldHTML.append(buildHTMLCOAForCOTable(thisItem, 
    								 bc.getListCOADetailAttributes(),
									 bc.getListAttrValues()));
      bldHTML.append("<table cellspacing=\"0\" style=\"width:100%\">");
      bldHTML.append("<tr><td>&nbsp;</td></tr>");
      bldHTML.append("<tr><td class=\"td0416\" style=\"text-align:center\" colspan=\"5\"><b><i>");
      //bldHTML.append("Tree Top, Inc. Guarantees that this product is negative for E.coli, Salmonella and Staphylococcus.<br>"); // commented out 12/16/09 TWalton
      bldHTML.append("Tree Top, Inc. Certifies that the above information is true and correct to the best of our knowledge.");
      bldHTML.append("</i></b></td></tr> ");
    
      bldHTML.append("<tr><td>&nbsp;</td></tr>");    
      bldHTML.append("<tr><td class=\"td0414\" style=\"text-align:center\" colspan=\"5\">");
      bldHTML.append("Tree Top, Inc. 220 E. Second Ave., P.O. Box 248 &nbsp;&nbsp;&nbsp; Selah, WA  98942-0248<br>");
      bldHTML.append(contactInfo.elementAt(0) + "&nbsp;&nbsp;&nbsp;" + contactInfo.elementAt(1));
      bldHTML.append("</td></tr>");
    
      bldHTML.append("<tr><td style=\"width:5%\"></td>");
      bldHTML.append("<td class=\"td04140105\" style=\"width:20%; text-align:center\">" + contactInfo.elementAt(3) + "</td>");
      bldHTML.append("<td colspan=\"3\"></td></tr>");
    
      bldHTML.append("<tr><td>&nbsp;</td>");
      bldHTML.append("<td class=\"td04140105\" style=\"text-align=center\"> ");
      if (((String) contactInfo.elementAt(4)).trim().equals("Mike Frausto"))
      {
    	 bldHTML.append("QA Manager or Designee"); 
      } else {
         bldHTML.append("Quality Assurance Manager");
      }
      bldHTML.append(" </td>");
      bldHTML.append("<td class=\"td04140105\" style=\"text-align=center\">" + contactInfo.elementAt(2) + "</td>");
      bldHTML.append("<td class=\"td04140105\" style=\"text-align=center; width:20%\">");
      if (thisItem.getItemClass().getItemType().equals("120"))
         bldHTML.append("Kosher: OU");
      bldHTML.append("&nbsp;</td>");
      bldHTML.append("<td class=\"td04140105\" style=\"width:5%\">&nbsp;</td></tr>");
    
//**** Get system date (Today's Date) for bottom of page ***//
      //String[] todaysDate = SystemDate.getSystemDate();
      DateTime dt = UtilityDateTime.getSystemDate();
      bldHTML.append("<tr><td class=\"td04140105\" style=\"text-align=center\" colspan=\"5\">");
      bldHTML.append("APPROVED:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp");
      bldHTML.append(dt.getDayOfWeek() + ",&nbsp;&nbsp;&nbsp;");
      bldHTML.append(dt.getDateFormatMonthNameddyyyy() + "</td></tr></table>");
    
      pageBreak   = " style=\"page-break-before:always\" ";
     } // End of the If Statement
  	  } // End of the IF the item is valid
     }  //End of the For Statement
	bldHTML.append("</body></html>");
    if (skip.equals("Y"))
    	bldHTML = new StringBuffer();
	     }
	     else
	     {
		  	bldHTML.append(buildHTMLCOAError("CO", bldCOA.getInqSalesOrder()));
	   }	     	
   }
	  else
   {
	  	bldHTML.append(buildHTMLCOAError("CO", bldCOA.getInqSalesOrder()));
   }
}
catch(Exception e)
{
	System.out.println("Problem found in CtlCOA.buildHTMLCOA(): " + e);
}
      return bldHTML.toString();
}

/**
 * This method will build the HTML code for
 *   the preview page (will also be used as
 *   the body of the email.  --
 *  Build The HEADER Section of the page.
 *
 * Creation date: (11/13/2003 9:25:30 AM)
 * Updated 2/23/08 - TWalton -- used with Movex
 */
private String buildHTMLCOAForCOHeader(SalesOrderLineItem thisItem, COADetailAttributes cda) 
{
	StringBuffer bldHTML = new StringBuffer();
try
{
	bldHTML.append("<table style=\"width:100%\" cellspacing=\"0\">");
    bldHTML.append("<tr><td class=\"td04160105\" style=\"width:3%\" rowspan=\"6\">&nbsp;</td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:15%\" ><b>Customer Order Number:</b></td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:30%\" ><b>");
    bldHTML.append(thisItem.getOrderNumber() + "</b></td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:15%\" rowspan=\"3\" valign=\"top\">");
    bldHTML.append("<b>Comments:</b></td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:30%\"  rowspan=\"3\" valign=\"top\">");
    bldHTML.append(cda.getComment() + "&nbsp;</td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:3%\" rowspan=\"6\"> &nbsp;</td></tr>");
      
    bldHTML.append("<tr><td class=\"td04160105\"><b>Customer Number:</b></td>");
    bldHTML.append("<td class=\"td04160105\" >");
    bldHTML.append(thisItem.getCustomerNumber() + "</td></tr>");
    
    bldHTML.append("<tr><td class=\"td04160105\" ><b>Customer PO:</b></td>");
    bldHTML.append("<td class=\"td04160105\" >");
    bldHTML.append(thisItem.getCustomerPONumber() + "</td></tr>");
    
    bldHTML.append("<tr><td class=\"td04160105\" ><b>Customer Name:</b></td>");
    bldHTML.append("<td class=\"td04160105\" >");
    bldHTML.append(thisItem.getCustomerName() + "</td>");
    bldHTML.append("<td class=\"td04160105\" rowspan=\"3\" valign=\"top\">");
    bldHTML.append("<b>Item Comments:</b></td>");
    bldHTML.append("<td class=\"td04160105\" rowspan=\"3\" valign=\"top\">");
    bldHTML.append(cda.getItemComment().trim() + "&nbsp;</td></tr>");
    
    bldHTML.append("<tr><td class=\"td04160105\" valign=\"top\">");
    bldHTML.append("<b>Ship to Address:</b></td>");
    bldHTML.append("<td class=\"td04160105\" > ");
	if (!thisItem.getAddress1().trim().equals(""))
    {
       bldHTML.append(thisItem.getAddress1().trim());
       if (!thisItem.getAddress2().trim().equals("") ||
           !thisItem.getAddress3().trim().equals("") ||
           !thisItem.getAddress4().trim().equals(""))
          bldHTML.append("<br>");
    }
    if (!thisItem.getAddress2().trim().equals(""))
    {
       bldHTML.append(thisItem.getAddress2().trim());
       if (!thisItem.getAddress3().trim().equals("") ||
           !thisItem.getAddress4().trim().equals(""))
          bldHTML.append("<br>");
    }   
    if (!thisItem.getAddress3().trim().equals(""))
    {
       bldHTML.append(thisItem.getAddress3().trim());
       if (!thisItem.getAddress4().trim().equals(""))
          bldHTML.append("<br>");
    }        
    if (!thisItem.getAddress4().trim().equals(""))
       bldHTML.append(thisItem.getAddress4().trim());		 
    bldHTML.append("&nbsp;</td></tr>");
    
    bldHTML.append("<tr><td class=\"td04160105\" valign=\"top\">");
    bldHTML.append("<b>Ship Date:</b></td>");
    bldHTML.append("<td class=\"td04160105\" >");
    String df1 = cda.getDateFormat();
    if (df1 == null || df1.trim().equals(""))
    	df1 = "0";
    bldHTML.append(BuildCOA.convertDate(thisItem.getActualShipDate(), new Integer(df1)));
    bldHTML.append("</tr></table>");
     
    // Convert Ship date based on COA Information
          
}
catch(Exception e)
{
	System.out.println("Problem found in CtlCOANew.buildHTMLCOAHeader(): " + e);
}
      return bldHTML.toString();
}

/**
 * This method will build the HTML code for
 *   the preview page (will also be used as
 *   the body of the email.
 *  
 * Creation date: (11/12/2003 10:01:30 AM)
 * Update - 8/18/06 TWalton
 * Updated 2/23/08 - TWalton -- used with Movex
 */
private String buildHTMLCOAForCOTable(SalesOrderLineItem thisItem,
								      Vector listCoa,
		                              Vector listAttr) 
{
	 
  StringBuffer tableHTML     = new StringBuffer(); // Used to build the WHOLE Table
  try
  {
  	COADetailAttributes cdaHead = (COADetailAttributes) listCoa.elementAt(0);
  	tableHTML.append("<table cellspacing=\"0\" style=\"width:100%\" border=\"1\">");
//---------------------------------------------------------------------------------
// Row 1 -- HEADINGS
    tableHTML.append("<tr class=\"tr01\">");
    
    tableHTML.append("<td class=\"td04140105\" style=\"width:7%; text-align:center\">");
    tableHTML.append("<b>Lot</b></td>");
    
    tableHTML.append("<td class=\"td04140105\" style=\"width:7%; text-align:center\">");
    tableHTML.append("<b>Production Date</b></td>");
 
    int columnSpan = 3;
    if (cdaHead.getShowUnits().equals("1"))
    {
    	tableHTML.append("<td class=\"td04140105\" style=\"width:7%; text-align:center\">");
        tableHTML.append("<b>Pounds Shipped</b></td>");
        columnSpan++;
    }
    if (cdaHead.getShowUnits().equals("2"))
    {
    	tableHTML.append("<td class=\"td04140105\" style=\"width:7%; text-align:center\">");
        tableHTML.append("<b>Kilograms Shipped</b></td>");
        columnSpan++;
    }    

    tableHTML.append("<td class=\"td04140105\" style=\"width:7%; text-align:center\">");
    tableHTML.append("<b>Cases Shipped</b></td>");
    
    int countAttributes = 0;
    int startAttribute = 0;
    String saveLot = "";
    String firstLotFound = "";
    Vector attrMinMax = new Vector();
    Vector decimals = new Vector();
//  	int z = 0;
//  	while (!thisItem.getLineNumber().equals(thisItem.getLineNumber()))
//  	{
//  		z++;
//  		COADetailAttributes cda  = (COADetailAttributes) listCoa.elementAt(z);
//  	}
     for (int x = 0; x < listAttr.size(); x++)
    {
    	COADetailAttributes cda = (COADetailAttributes) listCoa.elementAt(x);
    	AttributeValue av = (AttributeValue) listAttr.elementAt(x);
    	  
       if (cda.getLineNumber().equals(thisItem.getLineNumber()))
       {
          if (firstLotFound.equals(""))
          {
              startAttribute = x;
        //      thisLineLot = (com.treetop.businessobjectapplications.COADetailAttributes) listCoa.elementAt(x);
              if (av.getLotObject().getLotNumber() != null)
              {	
                saveLot = av.getLotObject().getLotNumber().trim();
                firstLotFound = "Y";
              }
          }
          if (firstLotFound.equals("Y") && av.getLotObject().getLotNumber() != null && saveLot.equals(av.getLotObject().getLotNumber().trim()) )
          {
             countAttributes++;
             tableHTML.append("<td class=\"td04140105\" style=\"width:7%; text-align:center\">");
             tableHTML.append("<b>" + cda.getAttributeClass().getAttributeDescription() + "</b></td>");    
             decimals.addElement(cda.getAttributeClass().getDecimalPlaces());
             String displayMinMax = "";
             if (cda.getAttributeClass().getLowValue() != null)
                displayMinMax = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(cda.getAttributeClass().getLowValue(), (new Integer(cda.getAttributeClass().getDecimalPlaces()).intValue()));
             if (cda.getAttributeClass().getHighValue() != null)
             {
                if (displayMinMax.trim().equals(""))
                   displayMinMax = "0";
                displayMinMax = displayMinMax + " / ";
                displayMinMax = displayMinMax + com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(cda.getAttributeClass().getHighValue(), (new Integer(cda.getAttributeClass().getDecimalPlaces()).intValue()));
             }
                
             if (displayMinMax.trim().equals(""))
                displayMinMax = "&nbsp;";          
             attrMinMax.addElement(displayMinMax);
          }
       }
    } // End of the for loop
    tableHTML.append("</tr>");
//--------------------------------------------------------------------------------------------
// Row 2
    if (cdaHead.getShowMinMax().equals("1"))
    {
       tableHTML.append("<tr class=\"tr01\">");
       
       tableHTML.append("<td class=\"td05140105\" style=\"text-align:right\" colspan=\"" + columnSpan + "\">");
       tableHTML.append("<b>Specification Min / Max --->>></b></td>");
       if (attrMinMax.size() > 0)
       {	
          for (int x = 0; x < attrMinMax.size(); x++)
          {
       	     tableHTML.append("<td class=\"td05140105\" style=\"text-align:center\"><b>");
       	     tableHTML.append(attrMinMax.elementAt(x));
             tableHTML.append("</b></td>");
          }
       }
       tableHTML.append("</tr>");
    }   
    // Target Not currently included in this COA
    
    String[] aveValue = new String[countAttributes];
    int countLot = 0;
    if (countAttributes == 0)
      startAttribute = 0;
    for (int lot = startAttribute; lot < listAttr.size(); lot++)
    {
       COADetailAttributes cda = (COADetailAttributes) listCoa.elementAt(lot);
       if (cda.getLineNumber().equals(thisItem.getLineNumber()))
       {
          AttributeValue av = (AttributeValue) listAttr.elementAt(lot);
          countLot++;  
//----------------------------------------------------------------------------------------
//     NEXT ROW          
          tableHTML.append("<tr class=\"tr00\">");
           
          tableHTML.append("<td class=\"td04140105\" style=\"text-align:center\">");
          if (av.getLotObject().getLotNumber() != null)
             tableHTML.append(av.getLotObject().getLotNumber());
          tableHTML.append("&nbsp;</td>");
          
          tableHTML.append("<td class=\"td04140105\" style=\"text-align:center\">");
          String df2 = cda.getDateFormat();
          if (df2 == null || df2.trim().equals(""))
          	df2 = "0";
          if (av.getLotObject().getManufactureDate() != null &&
          	  !av.getLotObject().getManufactureDate().trim().equals(""))
            tableHTML.append(BuildCOA.convertDate(av.getLotObject().getManufactureDate(), new Integer(df2)));
          tableHTML.append("&nbsp;</td>");
          
          String displayQty = av.getLotObject().getQuantity();
          try
          {
          	java.math.BigDecimal bd = new java.math.BigDecimal(displayQty); 
          	if (bd.compareTo(new java.math.BigDecimal("0")) < 0)
          	   bd = bd.multiply(new java.math.BigDecimal("-1"));
          	displayQty = bd.toString();
          }
          catch(Exception e)
          {}       
        //** NOT YET coded for Pounds Shipped
          if (cdaHead.getShowUnits().equals("1"))
          {
          	tableHTML.append("<td class=\"td04140105\" style=\"text-align:right\">");
            tableHTML.append("<b>" + BuildCOA.convertUOM(av.getItemNumber(), "LB", displayQty));
            tableHTML.append("</b></td>");
          }
//        ** NOT YET coded for Kilograms Shipped  
          if (cdaHead.getShowUnits().equals("2"))
          {
          	tableHTML.append("<td class=\"td04140105\" style=\"text-align:center\">");
          	tableHTML.append("<b>" + BuildCOA.convertUOM(av.getItemNumber(), "KG", displayQty));
            tableHTML.append("</b></td>");
          }              
//        **Cases Shipped** 
          tableHTML.append("<td class=\"td04140105\" style=\"text-align:right\"><b>");
          tableHTML.append(HTMLHelpersMasking.maskNumber(displayQty, 0));
          tableHTML.append("</b></td>");
          
          if (lot != startAttribute)
             lot = lot - 1;
         if (countAttributes > 0)
         {	
          for (int x = 0; x < countAttributes; x++)
          {
         	 av = (com.treetop.businessobjects.AttributeValue) listAttr.elementAt(lot);
         	 String displayAttrValue = av.getValue();
         	 String alignValue = "style=\"text-align:center\" ";
         	 // Adding them together
         	 if (av.getFieldType().equals("numeric"))
         	 {
         	 	try
         	 	{
 		 	 	   if (aveValue[x] == null)
		 	 	      aveValue[x] = "0";
		 	 	   java.math.BigDecimal xxx = new java.math.BigDecimal(aveValue[x]).add(new java.math.BigDecimal(av.getValue()));
		 	 	   aveValue[x] = xxx.toString();
		 	 	   if (av.getAttribute().trim().equals("TPC"))
		 	 	   {
		 	 		 try{
		 	 			if (new BigDecimal(displayAttrValue).compareTo(new BigDecimal("1")) < 0)
		 	 			{
		 	 				displayAttrValue = "0";
		 	 			}
		 	 		 }catch(Exception e)
		 	 		 {}
		 	 		 displayAttrValue = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(displayAttrValue, 0);
		 	 	   }else{
		 	 	      displayAttrValue = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(displayAttrValue, new Integer((String) decimals.elementAt(x)).intValue());
		 	 	   }
         	 	 	alignValue = "style=\"text-align:right\" ";
         	 	}
         	 	catch(Exception e)
         	 	{ }
         	 }
         	 else
         	 {	
         	 	aveValue[x] = "&nbsp;";
         	 	if (displayAttrValue.trim().equals(""))
         	 	   displayAttrValue = "&nbsp;";
         	 }
 	         tableHTML.append("<td class=\"td04140105\" " + alignValue + ">");
 	         tableHTML.append(displayAttrValue);
 	         tableHTML.append("</td>");         	 	 	   

              lot++;
          } // End of the For Loop for Attributes
         }
          tableHTML.append("</tr>");  
                    
       } // As long as the LineNumber is correct
    } // End of the Lot For Loop
    // Once more for the Average of the Attributes
    if (cdaHead.getShowAverage().equals("1"))
    {
       tableHTML.append("<tr class=\"tr01\">");
       
       tableHTML.append("<td class=\"td05140105\" style=\"text-align:right\" colspan=\"" + columnSpan + "\">");
       tableHTML.append("<b>Average of Attributes --->>></b></td>");
       if (aveValue.length > 0)
       {	
          for (int x = 0; x < aveValue.length; x++)
          {
            String displayAve = "&nbsp;";
            if (!aveValue[x].equals("&nbsp;"))
            {
               try
               {
                  BigDecimal a = new BigDecimal(aveValue[x]);
                  if (a.compareTo(new BigDecimal("0")) > 0 &&
                      new BigDecimal(countLot).compareTo(new BigDecimal("0")) > 0)
                  {
                     BigDecimal ave = a.divide(new BigDecimal(countLot), 2);
                     displayAve = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(ave.toString(), new Integer((String) decimals.elementAt(x)).intValue());
                  }   
                  else 
                     displayAve = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber("0", new Integer((String) decimals.elementAt(x)).intValue());
               }
               catch(Exception e)
               {}
            }
       	     tableHTML.append("<td class=\"td05140105\" style=\"text-align:right\"><b>");
       	     tableHTML.append(displayAve);
             tableHTML.append("</b></td>");
          }
       }
       tableHTML.append("</tr>");
    } // Only if the averages are diplayed
    tableHTML.append("</table>");  
  }
  catch(Exception e)
  {
	System.out.println("Error in CtlCOANew.buildHTMLCOATable: " + e);
  }
  return tableHTML.toString();
      
}

/*
 * Retrieve and Build everthing needed for the Build Page
 *     This page will be used to Build the COA - Update Information related to how the
 *     COA should display.
 * 
 * @see com.treetop.servlets.BaseServlet#pageList(javax.servlet.http.HttpServletRequest,
 *      javax.servlet.http.HttpServletResponse)
 */
protected void pageBuild(HttpServletRequest request,
						HttpServletResponse response) {
	String requestType = (String) request.getAttribute("requestType");
	//--------------------------------------------------------------------------------
	InqCOA ic = (InqCOA) request.getAttribute("inqViewBean");
	BuildCOA bc = (BuildCOA) request.getAttribute("buildViewBean");
	
	BeanCOA forScreen = new BeanCOA();
	try
	{
		forScreen = ServiceCOA.buildCOA(ic);
		Vector bcv = new Vector();
		bcv.addElement(forScreen);
		bc.setListReport(bcv);
		if (bc.displayErrors.equals(""))
		   bc.loadUpdateFromBeanCOA(forScreen);
	}
	catch(Exception e)
	{
		// Need to add Error Data
		System.out.println("Error Found building the screen: " + e);
	}
	request.setAttribute("inqViewBean", ic);
	request.setAttribute("buildViewBean", bc);
}

/*
 * Retrieve and Build everthing needed for the Update Page
 *      Build a Bunch of Vectors to Flip the View of the Table Around for the JSP
 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest,
 *      javax.servlet.http.HttpServletResponse)
 */
protected void pageUpdFlip(HttpServletRequest request,
					       HttpServletResponse response) {
	try
	{
		UpdCOA uc = (UpdCOA) request.getAttribute("updViewBean");	
	    BeanCOA bc = uc.getReturnBean();
        // Element 0 is the Heading for the column
		Vector sequenceNumber = new Vector(); // Sequence Number
		 sequenceNumber.addElement("Sequence");
		Vector attributeCode = new Vector(); 
		 attributeCode.addElement("Attribute Classes");
		// Set up ALL the Vectors Needed --- only 100 Lots
		 Vector vectorOfLots = new Vector();
		 Vector buildVector = null;
		 String saveLot = "";
		
		int lotCount = 0;
		String vectorName = "";
		String notFirstLot = "";
		 for (int x = 0; x < bc.getListAttrValues().size(); x++)
		 {
		 	AttributeValue av = (AttributeValue) bc.getListAttrValues().elementAt(x);
		 	COADetailAttributes cda = (COADetailAttributes) bc.getListCOADetailAttributes().elementAt(x);
		 	if (!saveLot.equals(av.getLotObject().getLotNumber().trim()))
		 	{
		 	   	lotCount++;
		 	   	if (x != 0)
		 	   	{
		 	   	   vectorOfLots.addElement(buildVector);
		 	   	   notFirstLot = "Y";
		 	   	}
	 	        buildVector = new Vector();
		 	    buildVector.addElement(av.getLotObject().getLotNumber().trim());
		 	    saveLot = av.getLotObject().getLotNumber().trim();
		 	} 
	 		if (notFirstLot.equals(""))
	 		{	
		 		sequenceNumber.addElement(cda.getSequence());
		 		attributeCode.addElement(av);
	 		}
		 	// Build the Cells for the Lot Numbers
		 	String setAttributeValue = av.getValue();
		 	if (av.getFieldType().equals("numeric"))
		 	{
		 	   setAttributeValue = HTMLHelpersMasking.maskNumber(setAttributeValue, new Integer((String) av.getDecimalPlaces()).intValue());	
		 	}
		 	buildVector.addElement(setAttributeValue);
		 }
		 vectorOfLots.addElement(buildVector);
		
		 uc.setListAttributeID(attributeCode);
		 uc.setListAttributeSequence(sequenceNumber);
		 uc.setListLots(vectorOfLots);
		 
		 request.setAttribute("updViewBean", uc);	
	}
	catch(Exception e)
	{
	}
}

/**
 * Vector
 *    0 = Phone Number
 *    1 = Fax Number
 *    2 = Email Address
 *    3 = url of signature image
 *    4 = Long Name
 *    5 = Plant
 *
 * Creation date: (12/9/2003 11:03:51 AM)
 *   Updated 2/19/08 TWalton
 * @return java.lang.String[][][][]
 */
private Vector getContactInfoByPerson(String coaType, 
									  String emailSubject,
									  String theQaPerson) 
{
	Vector returnValues = new Vector();
  try
  {
	if (theQaPerson.trim().equals(""))
	{
		returnValues.add("PHONE: (509) 697-7251");
		returnValues.add("&nbsp;");
		returnValues.add("&nbsp;");
		returnValues.add("&nbsp;");
		returnValues.add("&nbsp;");
		returnValues.add("&nbsp;");
	}
	else
	{	
 		//*** Cashmere  - Plant has closed
//  		if (theQaPerson.equals("02"))
// 		{
//  	added Cashmere, Peggy Liley, when changed Prosser to Marina Tiede - TW - 04/15/07
//  			contactInfo[0] = "PHONE: (509) 782-2312";
// 			contactInfo[1] = "Fax: (509) 782-6800";
//  			contactInfo[2] = "Email: <a href=\"mailto:pliley@treetop.com?subject=COA for " +
// 		                 lineItemInfo.getOrderNumber() + ".\">" +
//		                 "pliley@treetop.com</a>";
//		    contactInfo[3] = "<img src=\"https://image.treetop.com/webapp/COA0909.gif\">";
// 		}		       	
	//*** Wenatchee
	   if (theQaPerson.equals("KBEAUS")) // Kim Beausoleil
	   {  
		  UserFile uf = new UserFile("KBEAUS");
		  returnValues.add("PHONE: (509) 697-7251");
		 // returnValues.add("Fax: (509) 662-0626"); -- 5/14/10 TW per Tom Hurson remove Fax Number
		  returnValues.add("");
		  returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
		  returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA0327.gif\">");
		  returnValues.add(uf.getUserNameLong().trim());
		  returnValues.add("Wenatchee");
		}
	   if (theQaPerson.equals("SLAURI")) // Shelly Laurie - Can update but does not have Signature Authority
	   {
		  UserFile uf = new UserFile("SLAURI"); 
		  returnValues.add("PHONE: (509) 697-7251");
		 // returnValues.add("Fax: (509) 662-0626"); -- 5/14/10 TW per Tom Hurson remove Fax Number
		  returnValues.add("");
		  returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
		  returnValues.add("");
		  returnValues.add(uf.getUserNameLong().trim());
		  returnValues.add("Wenatchee");
		}
		if (theQaPerson.equals("SLEMKE")) // Scott Lemke -- Moved to Wenatchee
		{
			UserFile uf = new UserFile("SLEMKE"); 
			returnValues.add("PHONE: (509) 697-7251");
			//returnValues.add("Fax: (509) 698-1523");-- 5/14/10 TW per Tom Hurson remove Fax Number
			returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
			returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA0762.gif\">");
			returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Wenatchee");
		}		
	    //*** Ross
	   // Took out Julie 9/11/12
	//	if (theQaPerson.equals("JLENSE")) // Julie Lenseigne
	//	{
	//		UserFile uf = new UserFile("JLENSE"); 
	//		returnValues.add("PHONE: (509) 697-7251");
	//		//returnValues.add("Fax: (509) 698-1417"); -- 5/14/10 TW per Tom Hurson remove Fax Number
	//		  returnValues.add("");
	//		returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	 //                emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
	//		returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA1417.gif\">");
	//		returnValues.add(uf.getUserNameLong().trim());
	//		returnValues.add("Ross");
	//	}
		if (theQaPerson.equals("MFRAUS")) // 6/13/12 tw  Mike Frausto (Plant Manager)
		{ //  Julie Lenseigne is leaving the Ross Plant
			UserFile uf = new UserFile("MFRAUS"); 
			returnValues.add("PHONE: (509) 697-7251");
			  returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
			returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA0575.gif\">");
			returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Ross");
		}
		if (theQaPerson.equals("LCROLL")) // Laurie Crollard -- Moved from Selah
		{
			UserFile uf = new UserFile("LCROLL"); 
			returnValues.add("PHONE: (509) 697-7251");
			//returnValues.add("Fax: (509) 698-1523");-- 5/14/10 TW per Tom Hurson remove Fax Number
			  returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
			returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA9001.gif\">");
			returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Ross");
		}
		//*** Ross - Fresh Slice
		if (theQaPerson.equals("BFEIGN"))
		{
			UserFile uf = new UserFile("BFEIGN"); 
			returnValues.add("PHONE: (509) 697-7251");
			//returnValues.add("Fax: (509) 698-1417"); -- 5/14/10 TW per Tom Hurson remove Fax Number
			  returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
		    returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA6449.gif\">");
		    returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Ross Fresh Slice");
		}	
		//*** Prosser 
		// Marina no longer at Prosser
		//if (theQaPerson.equals("ESTENG")) // 1/31/13 TW - Changed Last Name
		if (theQaPerson.equals("ELEMAS"))	
		{
			//UserFile uf = new UserFile("ESTENG");
			UserFile uf = new UserFile("ELEMAS");
			returnValues.add("PHONE: (509) 697-7251");
			//returnValues.add("Fax: (509) 786-4128");-- 5/14/10 TW per Tom Hurson remove Fax Number
			  returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
		   // returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA3645.gif\">");
		    returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA3645_V3.gif\">");
		    returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Prosser");
		}
		
		if (theQaPerson.equals("MOSORI"))	
		{
			UserFile uf = new UserFile("MOSORI");
			returnValues.add("PHONE: (509) 697-7251");
			returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
		    returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA3176.gif\">");
		    returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Prosser");
		}	
		
	    //*** Selah -- Moved Laurie Crollard from Selah to Ross
//		if (theQaPerson.equals("LCROLL")) // Laurie Crollard
//		{
//			UserFile uf = new UserFile("LCROLL"); 
//			returnValues.add("PHONE: (509) 697-7251");
//			//returnValues.add("Fax: (509) 698-1523");-- 5/14/10 TW per Tom Hurson remove Fax Number
//			  returnValues.add("");
//			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
//	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
//			returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA9001.gif\">");
//			returnValues.add(uf.getUserNameLong().trim());
//			returnValues.add("Selah");
//		}
	    //*** Rialto - Plant has Closed
//		if (theQaPerson.equals("BCHAND")) // Bela Chandra
//		{
//			returnValues.add("PHONE: (509) 697-7251");
//			returnValues.add("Fax: (909) 874-1799");
//			returnValues.add("Email: <a href=\"mailto:jlenseigne@treetop.com?subject=COA for " +
//		                 emailSubject + ".\">" + "bchandra@treetop.com</a>");
//			returnValues.add("<img src=\"https://image.treetop.com/webapp/COA5001.gif\">");
//		}
		//***  Medford Plant (Sabroso)
//		if (theQaPerson.equals("BFRESZ")) // Brigette Fresz
//		{
//			UserFile uf = new UserFile("BFRESZ"); 
//			returnValues.add("PHONE: (509) 697-7251");
//			//returnValues.add("Fax: (509) 698-1523");-- 5/14/10 TW per Tom Hurson remove Fax Number
//			returnValues.add("");
//			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
//	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
//			returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA3750.gif\">");
//			returnValues.add(uf.getUserNameLong().trim());
//			returnValues.add("Medford");
//		}		
	
		if (theQaPerson.equals("DMCELR")) // Dawn McElreath
		{
			UserFile uf = new UserFile("DMCELR"); 
			returnValues.add("PHONE: (509) 697-7251");
			//returnValues.add("Fax: (509) 698-1523");-- 5/14/10 TW per Tom Hurson remove Fax Number
			returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
			returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA0316.gif\">");
			returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Medford");
		}		
		if (theQaPerson.equals("VGUY")) // Vicki Guy
		{
			UserFile uf = new UserFile("VGUY"); 
			returnValues.add("PHONE: (509) 697-7251");
			returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
			returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA3152.gif\">");
			returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Medford");
		}		
		//***  Woodburn Plant (Sabroso)
		if (theQaPerson.equals("AOSORI")) // Alba Osorio
		{
			UserFile uf = new UserFile("AOSORI"); 
			returnValues.add("PHONE: (509) 697-7251");
			//returnValues.add("Fax: (509) 698-1523");-- 5/14/10 TW per Tom Hurson remove Fax Number
			  returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
			returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA3870.gif\">");
			returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Woodburn");
		}		
		//***  Oxnard Plant (Sabroso)
		// 11/9/11 TW - Comment out, no longer with the company
//		if (theQaPerson.equals("FCERPA")) // Fiorella Cerpa
//		{
//			UserFile uf = new UserFile("FCERPA"); 
//			returnValues.add("PHONE: (509) 697-7251");
//			//returnValues.add("Fax: (509) 698-1523");-- 5/14/10 TW per Tom Hurson remove Fax Number
//			  returnValues.add("");
//			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
//	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
//			returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA5123.gif\">");
//			returnValues.add(uf.getUserNameLong().trim());
//			returnValues.add("Oxnard");
//		}
		// 11/9/11 TW
		if (theQaPerson.equals("JCASAG")) // Julie Casagrande
		{
			UserFile uf = new UserFile("JCASAG"); 
			returnValues.add("PHONE: (509) 697-7251");
			//returnValues.add("Fax: (509) 698-1523");-- 5/14/10 TW per Tom Hurson remove Fax Number
			  returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
			returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA9425.gif\">");
			returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Oxnard");
		}		
		   // added 5/27/11 TW
		if (theQaPerson.equals("HSINGH")) // Harsimran Singh Randhawa
		{
			UserFile uf = new UserFile("HSINGH"); 
			returnValues.add("PHONE: (509) 697-7251");
		    returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
			returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA7350.gif\">");
			returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Oxnard");
		}		
		   // added 5/10/12 TW
		if (theQaPerson.equals("KERICK")) // Kristin Erickson
		{
			UserFile uf = new UserFile("KERICK"); 
			returnValues.add("PHONE: (509) 697-7251");
		    returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
			returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA1925.gif\">");
			returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Prosser");
		}		
		//*** Corporate QA - Greg Melroy
		if (theQaPerson.equals("GMELRO"))
		{
			UserFile uf = new UserFile("GMELRO"); 
			returnValues.add("PHONE: (509) 697-7251");
			returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
		    returnValues.add("<img src=\"https://image.treetop.com/webapp/TreeNetImages/COA4734.gif\">");
		    returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Corporate QA");
		}		
		//*** Corporate QA - Jodee Green
		if (theQaPerson.equals("JHURST"))
		{
			UserFile uf = new UserFile("JHURST"); 
			returnValues.add("PHONE: (509) 697-7251");
			returnValues.add("");
			returnValues.add("Email: <a href=\"mailto:" + uf.getUserEmail().trim() + "?subject=COA for " +
	                 emailSubject + ".\">" + uf.getUserEmail().trim() + "</a>");
		    returnValues.add("");
		    returnValues.add(uf.getUserNameLong().trim());
			returnValues.add("Corporate QA");
		}		
  	}
  }catch(Exception e)
  {}
  	return returnValues;
}

/**
 * Generate and send an E-mail of the COA
 * Creation date: (12/11/2003 9:06:11 AM)
 *   Updated 8/18/06 TWalton
 */
private void emailCOA(javax.servlet.http.HttpServletRequest request) 
{

try
{
	InqCOA   inqCOA = (InqCOA) request.getAttribute("inqViewBean");
	BuildCOA bldCOA = (BuildCOA) request.getAttribute("buildViewBean");
	BeanCOA  bc     = (BeanCOA)bldCOA.getListReport().elementAt(0);
	COADetailAttributes cda = (COADetailAttributes) bc.getListCOADetailAttributes().elementAt(0);
	
//	Vector soDetailVector = (Vector) request.getAttribute("soDetailVector");
	
//   SalesOrderDetail headerInfo = (SalesOrderDetail) soDetailVector.elementAt(0);
	
//    COAInfo1 thisCOAInfo = new COAInfo1(headerInfo.getOrderNumber());    
	String[] to          = new String[8];
    if (inqCOA.getRequestType().equals("email"))
    {
    	to[0]                = cda.getEmail1(); 
    	to[1]                = cda.getEmail2(); 
    	to[2]                = cda.getEmail3(); 
    	to[3]                = cda.getEmail4();
    	to[4]                = cda.getEmail5(); 
    	to[5]                = cda.getEmail6(); 
    	to[6]                = cda.getEmail7(); 
    	to[7]                = cda.getEmail8();
    }
    if (inqCOA.getRequestType().equals("fax"))
    {
    	to	= new String[4];
    	to[0]                = cda.getFax1(); 
    	to[1]                = cda.getFax2(); 
    	to[2]                = cda.getFax3(); 
    	to[3]                = cda.getFax4();
    	for (int x = 0; x < 4; x++)
    	{
    		if (!to[x].trim().equals(""))
    		{
    			String onlyNumbers = "";
    			for (int z = 0; z < to[x].length(); z++)
    			{
    				String testValue = to[x].substring(z, (z + 1));
    				if (testValue.trim().equals("0") ||
    					testValue.trim().equals("1") ||
    					testValue.trim().equals("2") ||
    					testValue.trim().equals("3") ||
    					testValue.trim().equals("4") ||
    					testValue.trim().equals("5") ||
    					testValue.trim().equals("6") ||
    					testValue.trim().equals("7") ||
    					testValue.trim().equals("8") ||
    					testValue.trim().equals("9"))
    				{
    					onlyNumbers = onlyNumbers + testValue;
    				}
    			}
    			if (!onlyNumbers.trim().equals(""))
    			   to[x] = onlyNumbers + "@fax.treetop.com";
    		}
    	}
    }
	String[] cc          = new String[0];
	String[] bcc         = new String[0];	
	String subject       = "COA for Sales Order " + cda.getOrderNumber() + ".";
	String body          = "";
	String[] key1 = new String[1];
	String[] key2 = new String[1];
	
	if (!inqCOA.getInqDistributionOrder().trim().equals(""))
	{
		subject = "COA for Distribution Order " + cda.getOrderNumber() + ".";
		body = buildHTMLCOAForDO(request);
		key1[0] = "DO";
		key2[0] = cda.getOrderNumber().toString();
	}
	if (!inqCOA.getInqLot().trim().equals(""))
	{
		subject = "COA for Lot Number " + cda.getLotNumber() + ".";
		body = buildHTMLCOAForLOT(request);
		key1[0] = "LOT";
		key2[0] = cda.getLotNumber().toString();
	}
	if (inqCOA.getInqDistributionOrder().trim().equals("") &&
		inqCOA.getInqLot().trim().equals(""))
	{
		try{
			SalesOrderLineItem	thisItem = (SalesOrderLineItem) bc.getListSOLineItems().elementAt(0);
			subject = "COA for " + thisItem.getCustomerName() + " SO#:" + cda.getOrderNumber() + ".";	
		}catch(Exception e)
		{}
		body = buildHTMLCOAForCO(request);
		key1[0] = "CO";
		key2[0] = cda.getOrderNumber().toString();
	}
	
	
	// define parameters required for email aduit log.
	String system = "COA";
	
	Email sendingEmail   = new Email();
	String sentMessage         = sendingEmail.sendEmail(to,
		                                          cc,
		                                          bcc,
		                                          inqCOA.getUpdateUser(),
		                                          subject,
		                                          body,
		                                          system,
		                                          key1,
												  key2);
	if(sentMessage.equals(""))
	   sentMessage = "Your Email has been sent!";
	
	request.setAttribute("requestType", "sentEmail");

	request.setAttribute("msg", sentMessage);
   }
   catch(Exception e)
   {
      System.out.println("Exception found in CtlCOA.emailCOA() : " + e);	   
   } 	
}

/**
 * This method will build the HTML code for
 *   the preview page, when a COA is not yet built
 *  Build The HEADER Section of the page.
 *
 * Creation date: (2/27/2008 TWalton)
 */
private String buildHTMLCOAError(String coaType, String inValue) 
{
	StringBuffer bldHTML = new StringBuffer();
try
{
	bldHTML.append("<html><head>");
    bldHTML.append(HTMLHelpers.styleSheetHeadSectionV2());
    bldHTML.append("</head><body>");
	bldHTML.append("<table style=\"width:100%\" cellspacing=\"0\">");
    
	bldHTML.append("<tr class=\"tr01\">");
	bldHTML.append("<td class=\"td04160105\" style=\"width:3%\" rowspan=\"15\">&nbsp;</td>");
    bldHTML.append("<td class=\"td04160105\" colspan=\"3\">");
    bldHTML.append("<b>This Sales Order does not currently have a COA Built.  Please contact one of the following people: </b>");
    bldHTML.append("</td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:3%\" rowspan=\"15\"> &nbsp;</td></tr>");
  //Headings
    bldHTML.append("<tr class=\"tr02\">");
    bldHTML.append("<td class=\"td04140105\"><b>Location</b></td>");
    bldHTML.append("<td class=\"td04140105\"><b>Name</b></td>");
    bldHTML.append("<td class=\"td04140105\"><b>Email</b></td></tr>");
    
    // Wenatchee
    Vector listData = getContactInfoByPerson(coaType, inValue, "KBEAUS");
    bldHTML.append("<tr class=\"tr00\">");
    bldHTML.append("<td class=\"td04140105\">Wenatchee</td>");
    bldHTML.append("<td class=\"td04140105\">Kim Beausoleil</td>");
    bldHTML.append("<td class=\"td04140105\">" + listData.elementAt(2) + "</td></tr>");
    // Additional Wenatchee
    listData = getContactInfoByPerson(coaType, inValue, "SLAURI");
    bldHTML.append("<tr class=\"tr00\">");
    bldHTML.append("<td class=\"td04140105\">Wenatchee</td>");
    bldHTML.append("<td class=\"td04140105\">Shelly Laurie</td>");
    bldHTML.append("<td class=\"td04140105\">" + listData.elementAt(2) + "</td></tr>");
    // Ross 
 //   listData = getContactInfoByPerson(coaType, inValue, "JLENSE");
 //  bldHTML.append("<tr class=\"tr00\">");
 //   bldHTML.append("<td class=\"td04140105\">Ross</td>");
 //   bldHTML.append("<td class=\"td04140105\">Julie Lenseigne</td>");
 //   bldHTML.append("<td class=\"td04140105\">" + listData.elementAt(2) + "</td></tr>");
    // Ross 
    listData = getContactInfoByPerson(coaType, inValue, "LCROLL");
    bldHTML.append("<tr class=\"tr00\">");
    bldHTML.append("<td class=\"td04140105\">Ross</td>");
    bldHTML.append("<td class=\"td04140105\">Laurie Crollard</td>");
    bldHTML.append("<td class=\"td04140105\">" + listData.elementAt(2) + "</td></tr>");    
    // Ross - Fresh Slice
    listData = getContactInfoByPerson(coaType, inValue, "BFEIGN");
    bldHTML.append("<tr class=\"tr00\">");
    bldHTML.append("<td class=\"td04140105\">Ross Fresh Slice</td>");
    bldHTML.append("<td class=\"td04140105\">Brett Feigner</td>");
    bldHTML.append("<td class=\"td04140105\">" + listData.elementAt(2) + "</td></tr>");   
    // Prosser
    // 1/31/13 - Emily's Last name has Changed
    //listData = getContactInfoByPerson(coaType, inValue, "ESTENG");
    listData = getContactInfoByPerson(coaType, inValue, "ELEMAS");
    bldHTML.append("<tr class=\"tr00\">");
    bldHTML.append("<td class=\"td04140105\">Prosser</td>");
   // bldHTML.append("<td class=\"td04140105\">Emily Stengle</td>");
    //bldHTML.append("<td class=\"td04140105\">Emily Lemaster</td>"); wth2014/05/07
    bldHTML.append("<td class=\"td04140105\">Emily Faris</td>");     //wth2014/05/07
    bldHTML.append("<td class=\"td04140105\">" + listData.elementAt(2) + "</td></tr>");  
    // Medford  6/5/13
    listData = getContactInfoByPerson(coaType, inValue, "DMCELR");
    bldHTML.append("<tr class=\"tr00\">");
    bldHTML.append("<td class=\"td04140105\">Medford</td>");
    bldHTML.append("<td class=\"td04140105\">Dawn McElreath</td>");
    bldHTML.append("<td class=\"td04140105\">" + listData.elementAt(2) + "</td></tr>");  
    // Medford  6/5/13
    listData = getContactInfoByPerson(coaType, inValue, "VGUY");
    bldHTML.append("<tr class=\"tr00\">");
    bldHTML.append("<td class=\"td04140105\">Medford</td>");
    bldHTML.append("<td class=\"td04140105\">Vicki Guy</td>");
    bldHTML.append("<td class=\"td04140105\">" + listData.elementAt(2) + "</td></tr>");   
    // Woodburn  6/5/13
    listData = getContactInfoByPerson(coaType, inValue, "AOSORI");
    bldHTML.append("<tr class=\"tr00\">");
    bldHTML.append("<td class=\"td04140105\">Woodburn</td>");
    bldHTML.append("<td class=\"td04140105\">Alba Osorio</td>");
    bldHTML.append("<td class=\"td04140105\">" + listData.elementAt(2) + "</td></tr>");  
    // Oxnard  6/5/13
    listData = getContactInfoByPerson(coaType, inValue, "HSINGH");
    bldHTML.append("<tr class=\"tr00\">");
    bldHTML.append("<td class=\"td04140105\">Oxnard</td>");
    bldHTML.append("<td class=\"td04140105\">Harsimran Singh Randhawa</td>");
    bldHTML.append("<td class=\"td04140105\">" + listData.elementAt(2) + "</td></tr>");   
    // R & D
    listData = getContactInfoByPerson(coaType, inValue, "JHURST");
    bldHTML.append("<tr class=\"tr00\">");
    bldHTML.append("<td class=\"td04140105\">Corporate QA</td>");
    bldHTML.append("<td class=\"td04140105\">Jodee Green</td>");
    bldHTML.append("<td class=\"td04140105\">" + listData.elementAt(2) + "</td></tr>"); 
    
    listData = getContactInfoByPerson(coaType, inValue, "GMELRO");
    bldHTML.append("<tr class=\"tr00\">");
    bldHTML.append("<td class=\"td04140105\">R & D</td>");
    bldHTML.append("<td class=\"td04140105\">Greg Melroy</td>");
    bldHTML.append("<td class=\"td04140105\">" + listData.elementAt(2) + "</td></tr>");    
    bldHTML.append("</table>");
    
    bldHTML.append("</body></html>");
 
}
catch(Exception e)
{
	System.out.println("Problem found in CtlCOANew.buildHTMLCOAError(): " + e);
}
      return bldHTML.toString();
}

/**
 * This method will build the HTML code for
 *   the preview page (will also be used as
 *   the body of the email.
 *
 * Creation date: (12/29/2009 TWalton
 * 
 */
private String buildHTMLCOAForLOT(javax.servlet.http.HttpServletRequest request) 
{
   StringBuffer bldHTML = new StringBuffer();
try
{
	BuildCOA bldCOA = (BuildCOA) request.getAttribute("buildViewBean");
	if (bldCOA.getListReport().size() > 0)
	{	
	   try
	   {
	      BeanCOA  bc     = (BeanCOA)bldCOA.getListReport().elementAt(0);
	      if (bc.getListAttrValues().size() > 0)
	      {
		      Lot      lotObject    = ((AttributeValue) bc.getListAttrValues().elementAt(0)).getLotObject(); 
		      COADetailAttributes cda      = (COADetailAttributes) bc.getListCOADetailAttributes().elementAt(0);

		      // Attach the Header Information for the COA -- Will work the same for ALL COA's
 
		      bldHTML.append(buildHTMLCOAHeader("LOT", lotObject.getLotNumber(), cda.getSignatureQA()));
		  
		      
		      bldHTML.append(buildHTMLCOAForLOTHeader(lotObject, cda));
		      bldHTML.append(buildHTMLCOAForLOTTable(cda, bc.getListAttrValues()));
		      
		      Vector sendToFooter = new Vector();
		        sendToFooter.add(bldCOA.getCoaType());
		        sendToFooter.add("");
		        sendToFooter.add(lotObject.getItemNumber());
		        sendToFooter.add(lotObject.getItemType());
		        sendToFooter.add(lotObject.getLotNumber());
		        sendToFooter.add(cda.getSignatureQA());
		      bldHTML.append(buildHTMLCOAFooter(sendToFooter));
		      bldHTML.append("</body></html>");
	      }
	   }
	   catch(Exception e)
	   {
		   
	   }
     } // End of the If Statement

}
catch(Exception e)
{
	System.out.println("Problem found in CtlCOA.buildHTMLCOAForLOT(): " + e);
}
      return bldHTML.toString();
}

/**
 * This method will build the HTML code for
 *   the preview page, when a COA is not yet built
 *  Build The HEADER Section of the page, Logo ect....
 *
 * Creation date: (12/29/2009 TWalton)
 */
private String buildHTMLCOAHeader(String coaType, String inValue, String signatureValue) 
{
   StringBuffer bldHTML = new StringBuffer();
   try
   {
	   // 1/14/13 TW -  added in the encoding so the 3/8 character would work
   	  bldHTML.append("<html>");
   	  bldHTML.append("<%@page language=\"java\" contentType=\"text/html; charset=utf-8\" pageEncoding=\"utf-8\"%>");
   	  bldHTML.append("<head>");
      bldHTML.append(HTMLHelpers.styleSheetHeadSectionV2());
      bldHTML.append("</head><body>");
 //*** Heading Area of EACH PAGE      	
	  bldHTML.append("<table><tr>");
	  bldHTML.append("<td style=\"width:20%\"> <img src=\"/web/Include/images/");
	  
	  // 6/9/10 TWalton -- Per Jeannie Swedberg (also Bonna Cannon) will only use Tree Top Logo on the COA's 
	  //if(signatureValue.equals("AOSORI") 
	  //|| signatureValue.equals("BFRESZ") 
	  //|| signatureValue.equals("FCERPA"))
	  //{
	  // bldHTML.append("TreeNetImages/SabrosoLogoMedium");
	  //}
	  //else
	  //{
	     bldHTML.append("TT_logo2C-2013");
	  //}
	  bldHTML.append(".png\" style='margin:50px 20px'></td>");
      bldHTML.append("<td class=\"td0424\" style=\"text-align:center\"><b>Certificate of Analysis</b></td>");
      bldHTML.append("<td style=\"width:20%\"> &nbsp;");
//      if(signatureValue.equals("AOSORI") 
//	    || signatureValue.equals("BFRESZ") 
//	    || signatureValue.equals("FCERPA"))
//	  {
//	   bldHTML.append("<img src=\"https://image.treetop.com/webapp/TreeNetImages/SabrosoLogoTiny.png\">");
//	  }
	  
      
      bldHTML.append("</td><td style=\"width:5%\"> &nbsp;</td></tr></table>");
 
   }
   catch(Exception e)
   {
	  System.out.println("Problem found in CtlCOANew.buildHTMLCOAHeader(): " + e);
   }
      return bldHTML.toString();
}

/**
 * This method will build the HTML code for
 *   the preview page, when a COA is not yet built
 *  Build The FOOTER Section of the page, Signature Information ect....
 *  Send in a Vector:
 *  	0 = COA Type
 *      1 = Order Number
 *      2 = Item Number
 *      3 = Item Type
 *      4 = Lot Number
 *      5 = Signature Value
 *
 * Creation date: (12/29/2009 TWalton)
 */
private String buildHTMLCOAFooter(Vector inValues) 
{
   StringBuffer bldHTML = new StringBuffer();
   try
   {
//		cda  = (COADetailAttributes) bc.getListCOADetailAttributes().elementAt(z);
	     Vector contactInfo = getContactInfoByPerson((String) inValues.elementAt(0), (String) inValues.elementAt(1), (String) inValues.elementAt(5));
	   
	      bldHTML.append("<table cellspacing=\"0\" style=\"width:100%\">");
	      bldHTML.append("<tr><td>&nbsp;</td></tr>");
	      bldHTML.append("<tr><td class=\"td0416\" style=\"text-align:center\" colspan=\"5\"><b><i>");
	      //bldHTML.append("Tree Top, Inc. Guarantees that this product is negative for E.coli, Salmonella and Staphylococcus.<br>"); // commented out 12/16/09 TWalton
	      bldHTML.append("Tree Top, Inc. Certifies that the above information is true and correct to the best of our knowledge.");
	      bldHTML.append("</i></b></td></tr> ");
	    
	      bldHTML.append("<tr><td>&nbsp;</td></tr>");    
	      bldHTML.append("<tr><td class=\"td0414\" style=\"text-align:center\" colspan=\"5\">");
	      bldHTML.append("Tree Top, Inc. 220 E. Second Ave., P.O. Box 248 &nbsp;&nbsp;&nbsp; Selah, WA  98942-0248<br>");
	      bldHTML.append(contactInfo.elementAt(0) + "&nbsp;&nbsp;&nbsp;" + contactInfo.elementAt(1));
	      bldHTML.append("</td></tr>");
	    
	      bldHTML.append("<tr><td style=\"width:5%\"></td>");
	      bldHTML.append("<td class=\"td04140105\" style=\"width:20%; text-align:center\">" + contactInfo.elementAt(3) + "</td>");
	      bldHTML.append("<td colspan=\"3\"></td></tr>");
	    
	      bldHTML.append("<tr><td>&nbsp;</td>");
	      bldHTML.append("<td class=\"td04140105\" style=\"text-align=center\"> ");
	      if (((String) contactInfo.elementAt(4)).trim().equals("Mike Frausto"))
	      {
	    	 bldHTML.append("QA Manager or Designee"); 
	      } else {
	         bldHTML.append("Quality Assurance Manager");
	      }
	      bldHTML.append("</td>");
	      bldHTML.append("<td class=\"td04140105\" style=\"text-align=center\">" + contactInfo.elementAt(2) + "</td>");
	      bldHTML.append("<td class=\"td04140105\" style=\"text-align=center; width:20%\">");
	      if (((String) inValues.elementAt(3)).trim().equals("120"))
	         bldHTML.append("Kosher: OU");
	      bldHTML.append("&nbsp;</td>");
	      bldHTML.append("<td class=\"td04140105\" style=\"width:5%\">&nbsp;</td></tr>");
	    
//	**** Get system date (Today's Date) for bottom of page ***//
	      //String[] todaysDate = SystemDate.getSystemDate();
	      DateTime dt = UtilityDateTime.getSystemDate();
	      bldHTML.append("<tr><td class=\"td04140105\" style=\"text-align=center\" colspan=\"5\">");
	      bldHTML.append("APPROVED:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp");
	      bldHTML.append(dt.getDayOfWeek() + ",&nbsp;&nbsp;&nbsp;");
	      bldHTML.append(dt.getDateFormatMonthNameddyyyy() + "</td></tr></table>");
 
   }
   catch(Exception e)
   {
	  System.out.println("Problem found in CtlCOANew.buildHTMLCOAFooter(): " + e);
   }
      return bldHTML.toString();
}

/**
 * This method will build the HTML code for
 *   the preview page (will also be used as
 *   the body of the email).  --
 *  Build The HEADER Section of the page 
 *      specifically for LOT driven COA's.
 *
 * Creation date: 1/04/2010 TWalton
 */
private String buildHTMLCOAForLOTHeader(Lot thisLot, COADetailAttributes cda) 
{
	StringBuffer bldHTML = new StringBuffer();
try
{
	bldHTML.append("<table style=\"width:100%\" cellspacing=\"0\">");
	
    bldHTML.append("<tr><td class=\"td04160105\" style=\"width:3%\" rowspan=\"5\">&nbsp;</td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:15%\" ><b>Lot Number:</b></td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:30%\" ><b>");
    bldHTML.append(thisLot.getLotNumber() + "</b></td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:15%\" rowspan=\"5\" valign=\"top\">");
    bldHTML.append("<b>Comments:</b></td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:30%\"  rowspan=\"5\" valign=\"top\">");
    bldHTML.append(cda.getComment() + "&nbsp;</td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:3%\" rowspan=\"5\"> &nbsp;</td></tr>");
      
    bldHTML.append("<tr><td class=\"td04160105\" ><b>Manufacture Date:</b></td>");
    bldHTML.append("<td class=\"td04160105\" >");
    // Convert Manufacture date based on COA Information
    String df1 = cda.getDateFormat();
    if (df1 == null || df1.trim().equals(""))
    	df1 = "0";
    bldHTML.append(BuildCOA.convertDate(thisLot.getManufactureDate(), new Integer(df1)));
    bldHTML.append("</td></tr>");
    
    bldHTML.append("<tr><td class=\"td04160105\" ><b>Expiration Date:</b></td>");
    bldHTML.append("<td class=\"td04160105\" >");
    // Convert Expiration date based on COA Information
    bldHTML.append(BuildCOA.convertDate(thisLot.getExpirationDate(), new Integer(df1)));
    bldHTML.append("</td></tr>");
    
    bldHTML.append("<tr><td class=\"td04160105\" ><b>Item Number:</b></td>");
    bldHTML.append("<td class=\"td04160105\" >&nbsp;");
//    if (cda.getSignatureQA().equals("AOSORI")
//        	|| cda.getSignatureQA().equals("BFRESZ")
//        	|| cda.getSignatureQA().equals("FCERPA"))
//        { // 7/8/10 if one of the Sabroso QA managers is on the COA the Sabroso image will display before the description
//        	System.out.println("stop here");
//        	bldHTML.append("<img style=\"height:15px;\"src=\"https://image.treetop.com/webapp/TreeNetImages/SabrosoLogoTiny.png\">&nbsp;&nbsp;");   	
//        }
    bldHTML.append(thisLot.getItemNumber() + "</td></tr>");
    
    bldHTML.append("<tr><td class=\"td04160105\" valign=\"top\">");
    bldHTML.append("<b>Item Description:</b></td>");
    bldHTML.append("<td class=\"td04160105\" > ");
    if (cda.getSignatureQA().equals("AOSORI")
    	|| cda.getSignatureQA().equals("BFRESZ")
    	|| cda.getSignatureQA().equals("SLEMKE")
    	|| cda.getSignatureQA().equals("FCERPA"))
    { // 7/8/10 if one of the Sabroso QA managers is on the COA the Sabroso image will display before the description
//    	System.out.println("stop here");
    	bldHTML.append("<img style=\"height:15px;\"src=\"https://image.treetop.com/webapp/TreeNetImages/SabrosoLogoTiny.png\">&nbsp;&nbsp;");   	
    }
       bldHTML.append(thisLot.getItemDescription().trim());
    bldHTML.append("&nbsp;</td></tr>");
    
    bldHTML.append("</table>");
}
catch(Exception e)
{
	System.out.println("Problem found in CtlCOANew.buildHTMLLotHeader(): " + e);
}
      return bldHTML.toString();
}

/**
 * This method will build the HTML code for
 *   the preview page (will also be used as
 *   the body of the email.
 *  
 * Creation date: 1/4/2010 TWalton
 */
private String buildHTMLCOAForLOTTable(COADetailAttributes cdaHead,
								       Vector listAttr) 
{
	 
  StringBuffer tableHTML     = new StringBuffer(); // Used to build the WHOLE Table
  try
  {
  	tableHTML.append("<table align=\"center\" cellspacing=\"0\" style=\"width:70%\" border=\"1\">");
//---------------------------------------------------------------------------------
// Row 1 -- HEADINGS
    tableHTML.append("<tr class=\"tr02\">");
    
    tableHTML.append("<td class=\"td04140105\" style=\"text-align:center\"><b>Attribute</b></td>");
    tableHTML.append("<td class=\"td04140105\" style=\"text-align:center\"><b>Value</b></td>");
    if (cdaHead.getShowMinMax().equals("1"))
    {
    	tableHTML.append("<td class=\"td04140105\" style=\"text-align:center\"><b>Minimum</b></td>");
    	tableHTML.append("<td class=\"td04140105\" style=\"text-align:center\"><b>Maximum</b></td>");
    }
    tableHTML.append("<td class=\"td04140105\" style=\"text-align:center\"><b>Unit</b></td>");
    tableHTML.append("</tr>");
//--------------------------------------------------------------------------------------------
// Dynamic Section -- 1 row for each Attribute
    if (listAttr.size() > 0)
    {
    	for (int x = 0; x < listAttr.size(); x++)
    	{
    		AttributeValue av = (AttributeValue) listAttr.elementAt(x);
    		if (!av.getFieldType().trim().equals(""))
//    				&& 11/7/11 TWalton - should not include the attribute sequence, that is for the Model
//   		    !av.getAttributeSequence().trim().equals("0"))
    		{   	
    			tableHTML.append("<tr class=\"tr00\">");
    			
    			tableHTML.append("<td class=\"td04140105\">&nbsp;&nbsp;&nbsp;");
    			tableHTML.append(av.getAttributeDescription() + "&nbsp;&nbsp;&nbsp;</td>"); 
    			
    			String displayAttrValue = av.getValue();
            	String alignValue = "style=\"text-align:center\" ";
            	if (av.getFieldType().equals("numeric"))
            	{
            	if (av.getAttribute().trim().equals("TPC"))
  		 	 	   {
  		 	 		 try{
  		 	 			System.out.println(new BigDecimal(displayAttrValue).compareTo(new BigDecimal("1")));
  		 	 			if (new BigDecimal(displayAttrValue).compareTo(new BigDecimal("1")) < 0)
  		 	 			{
  		 	 				displayAttrValue = "0";
  		 	 			}
  		 	 		 }catch(Exception e)
  		 	 		 {}
  		 	 		 displayAttrValue = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(displayAttrValue, 0);
  		 	 	   }else{
  		 	 		displayAttrValue = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(displayAttrValue, new Integer((String) av.getDecimalPlaces()).intValue());
  		 	 	   }
            	 	alignValue = "style=\"text-align:right\" ";
           	 	}
           	 	if (displayAttrValue.trim().equals(""))
           	 	   displayAttrValue = "&nbsp;";
    	         tableHTML.append("<td class=\"td04140105\" " + alignValue + ">");
    	         tableHTML.append(displayAttrValue);
    	         tableHTML.append("</td>");
    	         if (cdaHead.getShowMinMax().equals("1"))
     		     {
    	            String min = "&nbsp;";
    	            String max = "&nbsp;";
    	            if (av.getFieldType().equals("numeric"))
                	{
    	            	min = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(av.getLowValue(), new Integer((String) av.getDecimalPlaces()).intValue());
    	            	if (min.trim().equals(""))
    	            	  min = "&nbsp;";
    	            	max = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(av.getHighValue(), new Integer((String) av.getDecimalPlaces()).intValue());
    	            	if (max.trim().equals(""))
      	            	  max = "&nbsp;";
                	}
    	            tableHTML.append("<td class=\"td04140105\" style=\"text-align:right\">");
       	            tableHTML.append(min + "</td>");
       	            tableHTML.append("<td class=\"td04140105\" style=\"text-align:right\">");
    	            tableHTML.append(max + "</td>");
     		     } 
    	         tableHTML.append("<td class=\"td04140105\">&nbsp;&nbsp;&nbsp;");
     			 tableHTML.append(av.getAttributeUOMDescription() + "&nbsp;&nbsp;&nbsp;</td>"); 
    	         tableHTML.append("</tr>");
    		}
    	}
    }
    tableHTML.append("</table>");
  }
  catch(Exception e)
  {
	System.out.println("Error in CtlCOANew.buildHTMLCOAForLOTTable: " + e);
  }
  return tableHTML.toString();
      
}

/**
 * This method will build the HTML code for
 *   the preview page (will also be used as
 *   the body of the email for a Distribution Order
 *
 * Creation date: 11/1/2011 - TWalton
 */
private String buildHTMLCOAForDO(javax.servlet.http.HttpServletRequest request) 
{
   StringBuffer bldHTML = new StringBuffer();
   String pageBreak     = "";
try
{
	  BuildCOA bldCOA = (BuildCOA) request.getAttribute("buildViewBean");
	  if (bldCOA.getListReport().size() > 0)
	  {	
		  
	  BeanCOA  bc     = (BeanCOA)bldCOA.getListReport().elementAt(0);
	     if (bc.getListDOLineItems().size() > 0)
	     { 	
	    	 // 1/14/13 TW -  added in the encoding so the 3/8 character would work
	    	bldHTML.append("<html>");
	    	bldHTML.append("<%@page language=\"java\" contentType=\"text/html; charset=utf-8\" pageEncoding=\"utf-8\"%>");
	    	bldHTML.append("<head>");
    bldHTML.append(HTMLHelpers.styleSheetHeadSectionV2());
    bldHTML.append("</head><body>");
    String skip = "N";
    String saveItem = "";
     for (int x = 0; x < bc.getListDOLineItems().size(); x++)	
     {
     	DistributionOrderLineItem	thisItem = (DistributionOrderLineItem) bc.getListDOLineItems().elementAt(x);
     	String skipByItem = "N";
     	if (thisItem.getItemClass().getItemType().equals("250") ||
	        thisItem.getItemClass().getItemType().equals("260") ||
	        thisItem.getItemClass().getItemType().equals("270") ||
	        thisItem.getItemClass().getItemType().equals("280") ||
	        saveItem.trim().equals(thisItem.getItemClass().getItemNumber()))
     		   skipByItem = "Y";
       saveItem = thisItem.getItemClass().getItemNumber();	
  	  if (skipByItem.equals("N"))
  	  {
      	if (bc.getListCOADetailAttributes().size() == 0 )
      	{	
      		skip = "Y";
      		x = bc.getListDOLineItems().size();
      	}
       if (skip.equals("N") && skipByItem.equals("N"))
       {	
      	 COADetailAttributes cda      = (COADetailAttributes) bc.getListCOADetailAttributes().elementAt(x);
      	 int z = x;
      	 while (!thisItem.getLineNumber().equals(cda.getLineNumber()))
      	 {
      		z++;
      		cda  = (COADetailAttributes) bc.getListCOADetailAttributes().elementAt(z);
      	 }
      	 Vector contactInfo = getContactInfoByPerson(bldCOA.getCoaType(), thisItem.getOrderNumber(), cda.getSignatureQA());
// Heading Area of EACH PAGE      	
 	 bldHTML.append("<table" + pageBreak + "><tr>");
 	 bldHTML.append("<td style=\"width:20%\"> " +
 			"<img src=\"/web/Include/images/TT_logo2C-2013.png\" style='margin:50px 20px'>" +
 	 		"</td>");
     bldHTML.append("<td class=\"td0424\" style=\"text-align:center\"><b>Certificate of Analysis</b></td>");
     bldHTML.append("<td style=\"width:20%\"> &nbsp;");
     bldHTML.append("</td></tr></table>");
	bldHTML.append(buildHTMLCOAForDOHeader(thisItem, cda));
    //***************************
    // Item Line
	 bldHTML.append("<table cellspacing=\"0\" style=\"width:100%\" >");
     bldHTML.append("<tr class = \"tr02\">");
     bldHTML.append("<td class= \"td0320\" ><b>");
     bldHTML.append(thisItem.getItemClass().getItemNumber());
     bldHTML.append("&nbsp;&nbsp;");
     bldHTML.append(thisItem.getItemClass().getItemDescription());
     bldHTML.append("</b></td>");
              
         if (cda.getShowAttributeModel().equals("Y"))
         {
         	bldHTML.append("<td class= \"td0320\" ><b>");
         	bldHTML.append(thisItem.getItemClass().getAttributeModel());
         	bldHTML.append("</b></td>");
         }
    bldHTML.append("</tr></table>");

    bldHTML.append(buildHTMLCOAForDOTable(thisItem, 
    								 bc.getListCOADetailAttributes(),
									 bc.getListAttrValues()));
      bldHTML.append("<table cellspacing=\"0\" style=\"width:100%\">");
      bldHTML.append("<tr><td>&nbsp;</td></tr>");
      bldHTML.append("<tr><td class=\"td0416\" style=\"text-align:center\" colspan=\"5\"><b><i>");
      //bldHTML.append("Tree Top, Inc. Guarantees that this product is negative for E.coli, Salmonella and Staphylococcus.<br>"); // commented out 12/16/09 TWalton
      bldHTML.append("Tree Top, Inc. Certifies that the above information is true and correct to the best of our knowledge.");
      bldHTML.append("</i></b></td></tr> ");
    
      bldHTML.append("<tr><td>&nbsp;</td></tr>");    
      bldHTML.append("<tr><td class=\"td0414\" style=\"text-align:center\" colspan=\"5\">");
      bldHTML.append("Tree Top, Inc. 220 E. Second Ave., P.O. Box 248 &nbsp;&nbsp;&nbsp; Selah, WA  98942-0248<br>");
      bldHTML.append(contactInfo.elementAt(0) + "&nbsp;&nbsp;&nbsp;" + contactInfo.elementAt(1));
      bldHTML.append("</td></tr>");
    
      bldHTML.append("<tr><td style=\"width:5%\"></td>");
      bldHTML.append("<td class=\"td04140105\" style=\"width:20%; text-align:center\">" + contactInfo.elementAt(3) + "</td>");
      bldHTML.append("<td colspan=\"3\"></td></tr>");
    
      bldHTML.append("<tr><td>&nbsp;</td>");
      bldHTML.append("<td class=\"td04140105\" style=\"text-align=center\"> ");
      if (((String) contactInfo.elementAt(4)).trim().equals("Mike Frausto"))
      {
    	 bldHTML.append("QA Manager or Designee"); 
      } else {
         bldHTML.append("Quality Assurance Manager");
      }
      bldHTML.append("</td>");
      bldHTML.append("<td class=\"td04140105\" style=\"text-align=center\">" + contactInfo.elementAt(2) + "</td>");
      bldHTML.append("<td class=\"td04140105\" style=\"text-align=center; width:20%\">");
      if (thisItem.getItemClass().getItemType().equals("120"))
         bldHTML.append("Kosher: OU");
      bldHTML.append("&nbsp;</td>");
      bldHTML.append("<td class=\"td04140105\" style=\"width:5%\">&nbsp;</td></tr>");
    
//**** Get system date (Today's Date) for bottom of page ***//
      //String[] todaysDate = SystemDate.getSystemDate();
      DateTime dt = UtilityDateTime.getSystemDate();
      bldHTML.append("<tr><td class=\"td04140105\" style=\"text-align=center\" colspan=\"5\">");
      bldHTML.append("APPROVED:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp");
      bldHTML.append(dt.getDayOfWeek() + ",&nbsp;&nbsp;&nbsp;");
      bldHTML.append(dt.getDateFormatMonthNameddyyyy() + "</td></tr></table>");
    
      pageBreak   = " style=\"page-break-before:always\" ";
     } // End of the If Statement
  	  } // End of the IF the item is valid
     }  //End of the For Statement
	bldHTML.append("</body></html>");
    if (skip.equals("Y"))
    	bldHTML = new StringBuffer();
	     }
	     else
	     {
		  	bldHTML.append(buildHTMLCOAError("DO", bldCOA.getInqDistributionOrder()));
	   }	     	
   }
	  else
   {
	  	bldHTML.append(buildHTMLCOAError("DO", bldCOA.getInqDistributionOrder()));
   }
}
catch(Exception e)
{
	System.out.println("Problem found in CtlCOA.buildHTMLCOAforDO(): " + e);
}
      return bldHTML.toString();
}

/**
 * This method will build the HTML code for
 *   the preview page (will also be used as
 *   the body of the email.  --
 *  Build The HEADER Section of the page.
 *
 * Creation date: 11/1/2011 TWalton
 */
private String buildHTMLCOAForDOHeader(DistributionOrderLineItem thisItem, COADetailAttributes cda) 
{
	StringBuffer bldHTML = new StringBuffer();
try
{
	bldHTML.append("<table style=\"width:100%\" cellspacing=\"0\">");
    bldHTML.append("<tr><td class=\"td04160105\" style=\"width:3%\" rowspan=\"4\">&nbsp;</td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:15%\" ><b>Distribution Order Number:</b></td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:30%\" ><b>");
    bldHTML.append(thisItem.getOrderNumber() + "</b></td>");
    bldHTML.append("<td class=\"td04160105\" valign=\"top\" style=\"width:15%\" >");
    bldHTML.append("<b>Ship Date:</b></td>");
    bldHTML.append("<td class=\"td04160105\" >");
    String df1 = cda.getDateFormat();
    if (df1 == null || df1.trim().equals(""))
    	df1 = "0";
    bldHTML.append(BuildCOA.convertDate(thisItem.getActualShipDate(), new Integer(df1)));
    bldHTML.append("&nbsp;</td>");
    bldHTML.append("<td class=\"td04160105\" style=\"width:3%\" rowspan=\"4\">&nbsp;</td></tr>");
    
    bldHTML.append("<tr><td class=\"td04160105\"><b>From Facility:</b></td>");
    bldHTML.append("<td class=\"td04160105\" >");
    bldHTML.append(thisItem.getFromWarehouse().getFacility().trim() + "&nbsp;&nbsp;");
    bldHTML.append(thisItem.getFromWarehouse().getFacilityDescription().trim());
    bldHTML.append("<td class=\"td04160105\"><b>To Facility:</b></td>");
    bldHTML.append("<td class=\"td04160105\" >");
    bldHTML.append(thisItem.getToWarehouse().getFacility() + "&nbsp;&nbsp;");
    bldHTML.append(thisItem.getToWarehouse().getFacilityDescription() + "&nbsp;&nbsp;");
    bldHTML.append("</td></tr>");
    
    bldHTML.append("<tr><td class=\"td04160105\"><b>From Warehouse:</b></td>");
    bldHTML.append("<td class=\"td04160105\" >");
    bldHTML.append(thisItem.getFromWarehouse().getWarehouse().trim() + "&nbsp;&nbsp;");
    bldHTML.append(thisItem.getFromWarehouse().getWarehouseDescription().trim());
    bldHTML.append("<td class=\"td04160105\"><b>To Warehouse:</b></td>");
    bldHTML.append("<td class=\"td04160105\" >");
    bldHTML.append(thisItem.getToWarehouse().getWarehouse() + "&nbsp;&nbsp;");
    bldHTML.append(thisItem.getToWarehouse().getWarehouseDescription() + "&nbsp;&nbsp;");
    bldHTML.append("</td></tr>");
    
    bldHTML.append("<tr><td class=\"td04160105\" valign=\"top\"><b>");
    if (!cda.getComment().trim().equals(""))
       bldHTML.append("COA Comments:");
    bldHTML.append("&nbsp;</b></td>");
    bldHTML.append("<td class=\"td04160105\">");
    if (!cda.getComment().trim().equals(""))
       bldHTML.append(cda.getComment().trim()); 
    bldHTML.append("&nbsp;</td>");
    bldHTML.append("<td class=\"td04160105\" valign=\"top\"><b>");
    if (!cda.getItemComment().trim().equals(""))
       bldHTML.append("Item Comments:");
    bldHTML.append("&nbsp;</b></td>");
    bldHTML.append("<td class=\"td04160105\">");
    if (!cda.getItemComment().trim().equals(""))
       bldHTML.append(cda.getItemComment().trim()); 
    bldHTML.append("&nbsp;</td></tr>"); 
     
    bldHTML.append("</table>");
          
}
catch(Exception e)
{
	System.out.println("Problem found in CtlCOANew.buildHTMLCOAHeaderforDO(): " + e);
}
      return bldHTML.toString();
}

/**
 * This method will build the HTML code for
 *   the preview page will also be used as
 *   the body of the email. specific to Distribution Orders
 *  
 * Creation date: 11/1/2011 TWalton
 */
private String buildHTMLCOAForDOTable(DistributionOrderLineItem thisItem,
								      Vector listCoa,
		                              Vector listAttr) 
{
	 
  StringBuffer tableHTML     = new StringBuffer(); // Used to build the WHOLE Table
  try
  {
  	COADetailAttributes cdaHead = (COADetailAttributes) listCoa.elementAt(0);
  	tableHTML.append("<table cellspacing=\"0\" style=\"width:100%\" border=\"1\">");
//---------------------------------------------------------------------------------
// Row 1 -- HEADINGS
    tableHTML.append("<tr class=\"tr01\">");
    
    tableHTML.append("<td class=\"td04140105\" style=\"width:7%; text-align:center\">");
    tableHTML.append("<b>Lot</b></td>");
    
    tableHTML.append("<td class=\"td04140105\" style=\"width:7%; text-align:center\">");
    tableHTML.append("<b>Production Date</b></td>");
 
    int columnSpan = 3;
    if (cdaHead.getShowUnits().equals("1"))
    {
    	tableHTML.append("<td class=\"td04140105\" style=\"width:7%; text-align:center\">");
        tableHTML.append("<b>Pounds Shipped</b></td>");
        columnSpan++;
    }
    if (cdaHead.getShowUnits().equals("2"))
    {
    	tableHTML.append("<td class=\"td04140105\" style=\"width:7%; text-align:center\">");
        tableHTML.append("<b>Kilograms Shipped</b></td>");
        columnSpan++;
    }    

    tableHTML.append("<td class=\"td04140105\" style=\"width:7%; text-align:center\">");
    tableHTML.append("<b>Cases Shipped</b></td>");
    
    int countAttributes = 0;
    int startAttribute = 0;
    String saveLot = "";
    String firstLotFound = "";
    Vector attrMinMax = new Vector();
    Vector decimals = new Vector();
     for (int x = 0; x < listAttr.size(); x++)
    {
    	COADetailAttributes cda = (COADetailAttributes) listCoa.elementAt(x);
    	AttributeValue av = (AttributeValue) listAttr.elementAt(x);
    	  
       if (cda.getLineNumber().equals(thisItem.getLineNumber()))
       {
          if (firstLotFound.equals(""))
          {
              startAttribute = x;
        //      thisLineLot = (com.treetop.businessobjectapplications.COADetailAttributes) listCoa.elementAt(x);
              if (av.getLotObject().getLotNumber() != null)
              {	
                saveLot = av.getLotObject().getLotNumber().trim();
                firstLotFound = "Y";
              }
          }
          if (firstLotFound.equals("Y") && av.getLotObject().getLotNumber() != null && saveLot.equals(av.getLotObject().getLotNumber().trim()) )
          {
             countAttributes++;
             tableHTML.append("<td class=\"td04140105\" style=\"width:7%; text-align:center\">");
             tableHTML.append("<b>" + cda.getAttributeClass().getAttributeDescription() + "</b></td>");    
             decimals.addElement(cda.getAttributeClass().getDecimalPlaces());
             String displayMinMax = "";
             if (cda.getAttributeClass().getLowValue() != null)
                displayMinMax = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(cda.getAttributeClass().getLowValue(), (new Integer(cda.getAttributeClass().getDecimalPlaces()).intValue()));
             if (cda.getAttributeClass().getHighValue() != null)
             {
                if (displayMinMax.trim().equals(""))
                   displayMinMax = "0";
                displayMinMax = displayMinMax + " / ";
                displayMinMax = displayMinMax + com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(cda.getAttributeClass().getHighValue(), (new Integer(cda.getAttributeClass().getDecimalPlaces()).intValue()));
             }
                
             if (displayMinMax.trim().equals(""))
                displayMinMax = "&nbsp;";          
             attrMinMax.addElement(displayMinMax);
          }
       }
    } // End of the for loop
    tableHTML.append("</tr>");
//--------------------------------------------------------------------------------------------
// Row 2
    if (cdaHead.getShowMinMax().equals("1"))
    {
       tableHTML.append("<tr class=\"tr01\">");
       
       tableHTML.append("<td class=\"td05140105\" style=\"text-align:right\" colspan=\"" + columnSpan + "\">");
       tableHTML.append("<b>Specification Min / Max --->>></b></td>");
       if (attrMinMax.size() > 0)
       {	
          for (int x = 0; x < attrMinMax.size(); x++)
          {
       	     tableHTML.append("<td class=\"td05140105\" style=\"text-align:center\"><b>");
       	     tableHTML.append(attrMinMax.elementAt(x));
             tableHTML.append("</b></td>");
          }
       }
       tableHTML.append("</tr>");
    }   
    // Target Not currently included in this COA
    
    String[] aveValue = new String[countAttributes];
    int countLot = 0;
    if (countAttributes == 0)
      startAttribute = 0;
    for (int lot = startAttribute; lot < listAttr.size(); lot++)
    {
       COADetailAttributes cda = (COADetailAttributes) listCoa.elementAt(lot);
       if (cda.getLineNumber().equals(thisItem.getLineNumber()))
       {
          AttributeValue av = (AttributeValue) listAttr.elementAt(lot);
          countLot++;  
//----------------------------------------------------------------------------------------
//     NEXT ROW          
          tableHTML.append("<tr class=\"tr00\">");
           
          tableHTML.append("<td class=\"td04140105\" style=\"text-align:center\">");
          if (av.getLotObject().getLotNumber() != null)
             tableHTML.append(av.getLotObject().getLotNumber());
          tableHTML.append("&nbsp;</td>");
          
          tableHTML.append("<td class=\"td04140105\" style=\"text-align:center\">");
          String df2 = cda.getDateFormat();
          if (df2 == null || df2.trim().equals(""))
          	df2 = "0";
          if (av.getLotObject().getManufactureDate() != null &&
          	  !av.getLotObject().getManufactureDate().trim().equals(""))
            tableHTML.append(BuildCOA.convertDate(av.getLotObject().getManufactureDate(), new Integer(df2)));
          tableHTML.append("&nbsp;</td>");
          
          String displayQty = av.getLotObject().getQuantity();
          try
          {
          	java.math.BigDecimal bd = new java.math.BigDecimal(displayQty); 
          	if (bd.compareTo(new java.math.BigDecimal("0")) < 0)
          	   bd = bd.multiply(new java.math.BigDecimal("-1"));
          	displayQty = bd.toString();
          }
          catch(Exception e)
          {}       
        //** NOT YET coded for Pounds Shipped
          if (cdaHead.getShowUnits().equals("1"))
          {
          	tableHTML.append("<td class=\"td04140105\" style=\"text-align:right\">");
            tableHTML.append("<b>" + BuildCOA.convertUOM(av.getItemNumber(), "LB", displayQty));
            tableHTML.append("</b></td>");
          }
//        ** NOT YET coded for Kilograms Shipped  
          if (cdaHead.getShowUnits().equals("2"))
          {
          	tableHTML.append("<td class=\"td04140105\" style=\"text-align:center\">");
          	tableHTML.append("<b>" + BuildCOA.convertUOM(av.getItemNumber(), "KG", displayQty));
            tableHTML.append("</b></td>");
          }              
//        **Cases Shipped** 
          tableHTML.append("<td class=\"td04140105\" style=\"text-align:right\"><b>");
          tableHTML.append(HTMLHelpersMasking.maskNumber(displayQty, 0));
          tableHTML.append("</b></td>");
          
          if (lot != startAttribute)
             lot = lot - 1;
         if (countAttributes > 0)
         {	
          for (int x = 0; x < countAttributes; x++)
          {
         	 av = (com.treetop.businessobjects.AttributeValue) listAttr.elementAt(lot);
         	 String displayAttrValue = av.getValue();
         	 String alignValue = "style=\"text-align:center\" ";
         	 // Adding them together
         	 if (av.getFieldType().equals("numeric"))
         	 {
         	 	try
         	 	{
 		 	 	   if (aveValue[x] == null)
		 	 	      aveValue[x] = "0";
		 	 	   java.math.BigDecimal xxx = new java.math.BigDecimal(aveValue[x]).add(new java.math.BigDecimal(av.getValue()));
		 	 	   aveValue[x] = xxx.toString();
		 	 	 if (av.getAttribute().trim().equals("TPC"))
		 	 	   {
		 	 		 try{
		 	 			 if (new BigDecimal(displayAttrValue).compareTo(new BigDecimal("1")) < 0)
		 	 			{
		 	 				displayAttrValue = "0";
		 	 			}
		 	 		 }catch(Exception e)
		 	 		 {}
		 	 		 displayAttrValue = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(displayAttrValue, 0);
		 	 	   }else{
		 	 	      displayAttrValue = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(displayAttrValue, new Integer((String) decimals.elementAt(x)).intValue());
		 	 	   }   
         	 	 	  	 	 	alignValue = "style=\"text-align:right\" ";
         	 	}
         	 	catch(Exception e)
         	 	{ }
         	 }
         	 else
         	 {	
         	 	aveValue[x] = "&nbsp;";
         	 	if (displayAttrValue.trim().equals(""))
         	 	   displayAttrValue = "&nbsp;";
         	 }
 	         tableHTML.append("<td class=\"td04140105\" " + alignValue + ">");
 	         tableHTML.append(displayAttrValue);
 	         tableHTML.append("</td>");         	 	 	   

              lot++;
          } // End of the For Loop for Attributes
         }
          tableHTML.append("</tr>");  
                    
       } // As long as the LineNumber is correct
    } // End of the Lot For Loop
    // Once more for the Average of the Attributes
    if (cdaHead.getShowAverage().equals("1"))
    {
       tableHTML.append("<tr class=\"tr01\">");
       
       tableHTML.append("<td class=\"td05140105\" style=\"text-align:right\" colspan=\"" + columnSpan + "\">");
       tableHTML.append("<b>Average of Attributes --->>></b></td>");
       if (aveValue.length > 0)
       {	
          for (int x = 0; x < aveValue.length; x++)
          {
            String displayAve = "&nbsp;";
            if (!aveValue[x].equals("&nbsp;"))
            {
               try
               {
                  BigDecimal a = new BigDecimal(aveValue[x]);
                  if (a.compareTo(new BigDecimal("0")) > 0 &&
                      new BigDecimal(countLot).compareTo(new BigDecimal("0")) > 0)
                  {
                     BigDecimal ave = a.divide(new BigDecimal(countLot), 2);
                     displayAve = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber(ave.toString(), new Integer((String) decimals.elementAt(x)).intValue());
                  }   
                  else 
                     displayAve = com.treetop.utilities.html.HTMLHelpersMasking.maskNumber("0", new Integer((String) decimals.elementAt(x)).intValue());
               }
               catch(Exception e)
               {}
            }
       	     tableHTML.append("<td class=\"td05140105\" style=\"text-align:right\"><b>");
       	     tableHTML.append(displayAve);
             tableHTML.append("</b></td>");
          }
       }
       tableHTML.append("</tr>");
    } // Only if the averages are diplayed
    tableHTML.append("</table>");  
  }
  catch(Exception e)
  {
	System.out.println("Error in CtlCOANew.buildHTMLCOATable: " + e);
  }
  return tableHTML.toString();
      
}
}
