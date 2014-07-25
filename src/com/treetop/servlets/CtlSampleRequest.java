package com.treetop.servlets;
 
import com.treetop.data.*;
import com.treetop.utilities.html.HTMLHelpers;
import com.treetop.*;
import com.treetop.app.CtlKeyValues;
import com.treetop.businessobjectapplications.*;
import com.treetop.businessobjects.*;
import com.treetop.services.*;

import java.util.*;
import java.math.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.*; 
import com.ibm.as400.access.*;
/**
 * This Servlet will control which jsp's get thrown,
 *     example,  inqSampleRequest.jsp
 *				 listSampleRequest.jsp
 *               dtlSampleRequest.jsp
 *               updSampleRequest.jsp
 *    
 * Creation date: (6/17/2003 9:57:57 AM)
 * @author:  Teri Walton
 *
 *  PARAMETERS used (sent into) this Servlet
 *    "msg"         = Message sent for display onto the page
 *    "requestType" = Valid Request Type's
 *                	  inq, 
 *					  list, (List of Parameters to narrow the list displayed)
 *                    detail, (A Parameter(Sample Number))
 *                    add, 
 *					  addfinish, (List of parameters to add the record)
 *                    copy, (A Paramter(Sample Number to be copied from))
 *                    update, (A Parameter(Sample you wish to change/update))
 *					  updatefinish, (List of Parameters for changing the record)
 *                    delete, (A Parameter(Sample Number to be Deleted))
 *					  deletefinish (A Parameter(Sample Number to be Deleted))
 *        default will be inq.
 *    All other parameters will be defined within the method's
 */
public class CtlSampleRequest extends javax.servlet.http.HttpServlet {

	String server;
	private int itemCount;
/**
 * 	Temporary session variables will be set up here based on their
 * Sample Request User Type or role authority. 
 * Access authority will be tested for against the session variable values
 * defined in this method. 
 * 
 * Creation date: (8/22/2003 08:00:40 AM)
 */
private void accessSecurity(javax.servlet.http.HttpServletRequest request,
					       javax.servlet.http.HttpServletResponse response)  
{
	HttpSession sess = request.getSession(true);
			
	String techAuth  = (String) sess.getAttribute("techAuth");
	String salesAuth = (String) sess.getAttribute("salesAuth");
	String recvAuth  = (String) sess.getAttribute("recvAuth");
	String profile   = SessionVariables.getSessionttiProfile(request, response);

	
	// See if the current user has role value of "8" (programmer).
	if (techAuth == null || salesAuth == null || recvAuth == null)
	{
    	String[] userRoles = SessionVariables.getSessionttiUserRoles(request,
	    	                                                         response);
	 	                                                         
    	for (int x = 0; x < userRoles.length; x++)
    	{
	    	if(userRoles[x].trim().equals("8"))
	    	{
	    		techAuth  = "yes";
	    		salesAuth = "yes";
	    		recvAuth  = "yes";
	    		sess.setAttribute("techAuth", techAuth);
	    		sess.setAttribute("salesAuth", salesAuth);
	    		sess.setAttribute("recvAuth", recvAuth);
	    	}
    	}
	}
	
	if (techAuth == null)
	{
		try {
			SampleRequestUsers sru = new SampleRequestUsers("tech", profile);
			techAuth = "yes";
		} catch (Exception e) {
			techAuth = "no";
		}

		sess.setAttribute("techAuth", techAuth);
	}

	if (salesAuth == null)
	{
		try {
			SampleRequestUsers sru = new SampleRequestUsers("sales", profile);
			salesAuth = "yes";
		} catch (Exception e) {
			salesAuth = "no";
		}

		sess.setAttribute("salesAuth", salesAuth);
	}

	if (recvAuth == null)
	{
		try {
			SampleRequestUsers sru = new SampleRequestUsers("received", profile);
			recvAuth = "yes";
		} catch (Exception e) {
			recvAuth = "no";
		}

		sess.setAttribute("recvAuth", recvAuth);
	}	
    
}
/**
 /**
 * Build the Document Checkboxes to be used for the Update/Add Page
 * Creation date: (6/24/2003 2:56:40 PM)
 */
private String buildDocumentCheckBoxes(SampleRequestOrder sampleClass)  
{
	String boxes = "";
//	** Hide - requested Removal 9/29/05 TW Project 7142
	
//	try
//	{  
//		// Get list of All Documents.  To build the checkboxes around.
//		GeneralInfo descInfo  = new GeneralInfo();
//		Vector typeList       = new Vector();
//		typeList              = GeneralInfo.findDescByFull("SRF");
		
		// Determine How many total Documents.
//		int howMany = typeList.size();
//		String[] keyCode = new String[howMany];
//		int count = 0;
//		try
//		{
//			keyCode = sampleClass.getKeyCode();
//			int howManyChecked = 0;
//			if (keyCode != null)
//			   howManyChecked = keyCode.length;
			   
//			for (int x = 0; x < howMany; x++)
//			{
//				// Use this field to decide whether the box is checked.
//				String checked = "";
//				descInfo = (GeneralInfo) typeList.elementAt(x);
//				String code = descInfo.getKey1Value();
//				String codeName = descInfo.getDescFull();
//				for (int y = 0; y < howManyChecked; y++)
//				{
//					if (code.equals(keyCode[y]))
//					{
//						 checked = "checked";
//						 y = howManyChecked;
//					}
//				}
 //				if (sampleClass.getStatus().trim().equals("OP") ||
	// 				sampleClass.getStatus().trim().equals("PD"))
	//		    {
	//			   boxes = boxes + 
//		 	      "<input type='checkbox' name='" + code + "'" + 
//				               " " + checked + ">" +
//				               codeName + "&nbsp;&nbsp;&nbsp;";
//			    }
//			    else
//			    {
//				   boxes = boxes + 
//		 	      "<input type='checkbox' name='" + x + "' disabled" + 
//				               " " + checked + ">" +
//				               codeName + "&nbsp;&nbsp;&nbsp;";
//				  if (checked.equals("checked"))
//				  {
//					   boxes = boxes + 
//					 	      "<input type='hidden' name='" + code + "' value= '" + 
//				               descInfo.getKey1Value() + "'>";
//				  }
//				  else
//				  {
//					   boxes = boxes + 
//					 	      "<input type='hidden' name='" + code + "' value= ''>";
//				  }
//			    }
//			  
//			   count = count + 1;
//			   if (count == 4)
//			   {
//				   count = 0;
//				   boxes = boxes + "<br>";
//			   }
//			  
//			}
//		}
//		catch (Exception e)
//		{
//		}
//	}
//	catch(Exception e)
//	{
//		System.out.println("Error in CtlSampleRequest.buildDocumentCheckBoxes(): " + e);
//	}
	return boxes;
}
/**
 * Insert the method's description here.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
private String[] buildDropDownLists(String generalInfo[],
									SampleRequestOrder sampleClass,
									String requestType) 
{
	GeneralInfo setClass = new GeneralInfo();
	GeneralInfoDried setDried = new GeneralInfoDried();
	SampleRequestUsers setUsers = new SampleRequestUsers();
    
    	// Build the necessary drop down lists for jsp presentation.
   if (sampleClass.getStatus().trim().equals("OP") 
	   || sampleClass.getStatus().trim().equals("PD"))
   {
 		// Get drop down list of sales type.	
		Vector typeList       = new Vector();
		typeList              = GeneralInfo.findDescByFull("SRC");
		String ddSampleTypes  = GeneralInfo.buildDropDownFullForKey1(
									typeList, sampleClass.getType(), 
									"sampleType", "Sample Type");
		generalInfo[0]        = ddSampleTypes;
   }
   else
   {
	   // Get Input Hidden field for Display
	   String displayInfo    = sampleClass.getType().trim();
	   try
	   {
	       GeneralInfo typeClass = new GeneralInfo("SRC",
		                                        25,
		                                        sampleClass.getType().trim(),
		                                        "  ",
		                                        "  ",
		                                        "  ");
			displayInfo       = typeClass.getDescFull().trim();	       
	   }
	   catch(Exception e)
	   {
		   //System.out.println("Error when trying to get long description of " +
			   				  //"Sample Type from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	   }
	   generalInfo[0]       = displayInfo +
	    	                  "<input type=\"hidden\" name=\"sampleType\" value=\"" +
	    	                  sampleClass.getType().trim() + "\">";
   }

//generalInfo[1] - sales reps.
   if (sampleClass.getStatus().trim().equals("OP"))
   {
  	 // Get drop down list of sales reps.	
		Vector userListBox          = new Vector();
		userListBox                 = SampleRequestUsers.findTypeByName("sales");
		String ddSalesRep           = SampleRequestUsers.buildDropDownNameForProfile(
										             userListBox, 
										             sampleClass.getSalesRep(),
										             "Sales Rep");
		generalInfo[1]              = ddSalesRep;
   }
    else
   {
	   // Get Input Hidden field for Display
	   String displayInfo    = sampleClass.getSalesRep().trim();
	   try
	   {
	       SampleRequestUsers userClass = new SampleRequestUsers("sales",
		                                      sampleClass.getSalesRep().trim());
		   displayInfo                  = userClass.getUserName().trim();	       
	   }
	   catch(Exception e)
	   {
		   //System.out.println("Error when trying to get description of " +
			   				  //"Sales Rep from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	   }
	   generalInfo[1]       = displayInfo +
	    	                  "<input type=\"hidden\" name=\"sales\" value=\"" +
	    	                  sampleClass.getSalesRep().trim() + "\">";
	   
   }
// ** Hide - requested Removal 9/29/05 TW Project 7142
//generalInfo[2] - application types. 
//   if (sampleClass.getStatus().trim().equals("OP"))
//   {		
		// Get drop down list of sample applications.
//		Vector appList        = new Vector();
//		appList               = GeneralInfo.findDescByFull("SRA");
//		String ddAppTypes     = GeneralInfo.buildDropDownFullForKey1(
//									appList, sampleClass.getApplication(), 
//									"appType", "Application Type");
//		generalInfo[2]        = ddAppTypes;
   //}
//   else
   //{
	   // Get Input Hidden field for Display
//	   String displayInfo    = sampleClass.getApplication().trim();
//	   try
//	   {
//	       GeneralInfo appClass = new GeneralInfo("SRA",
//		                                        25,
//		                                        sampleClass.getApplication().trim(),
//		                                        "  ",
//		                                        "  ",
//		                                        "  ");
//			displayInfo       = appClass.getDescFull().trim();	       
//	   }
//	   catch(Exception e)
//	   {
//		   //System.out.println("Error when trying to get long description of " +
			   				  //"Application Type from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
//	   }
//	   generalInfo[2]       = displayInfo +
//	    	                  "<input type=\"hidden\" name=\"appType\" value=\"" +
//	    	                  sampleClass.getApplication().trim() + "\">";
	   
//   }

//	** Hide - requested Removal 9/29/05 TW Project 7142
//generalInfo[3] - technicians.
//   if (sampleClass.getStatus().trim().equals("PD") ||
//	   sampleClass.getStatus().trim().equals("OP"))
//  {		
		// Get drop down list of received by persons.
//		Vector recvListBox          = new Vector();
//		recvListBox                 = SampleRequestUsers.findTypeByName("tech");
//		String ddReceivedBy         = SampleRequestUsers.buildDropDownNameForProfile(
//										recvListBox,
//										sampleClass.getTechnician(),
//										"Technician");
//		generalInfo[3]              = ddReceivedBy;
//   }
//   else
//   {
 	  // Get Input Hidden field for Display
//  	  String displayInfo    = sampleClass.getTechnician().trim();
//   	  try
	  //{
  //	     SampleRequestUsers userClass = new SampleRequestUsers("tech",
//	  	                                    sampleClass.getTechnician().trim());
//	   	 displayInfo                  = userClass.getUserName().trim();	       
//	   }
//	   catch(Exception e)
// 	   {
	 	  //System.out.println("Error when trying to get description of " +
		   				  //"who Received Request from Class, " +
		                  //"CtlSampleRequest.buildDropDownLists()" + e);
//   	   }
//   		generalInfo[3]    = displayInfo +
//    	                  "<input type=\"hidden\" name=\"tech\" value=\"" +
//    	                  sampleClass.getTechnician().trim() + "\">";	   
//   }
   
   	
//generalInfo[4] - received by persons.
   if (sampleClass.getStatus().trim().equals("OP"))
   {		
		// Get drop down list of received by persons.
		Vector recvListBox          = new Vector();
		recvListBox                 = SampleRequestUsers.findTypeByName("received");
		String ddReceivedBy         = SampleRequestUsers.buildDropDownNameForProfile(
										recvListBox,
										sampleClass.getWhoReceivedRequest(),
										"Receiving Person");
		generalInfo[4]              = ddReceivedBy;
   }
    else
   {
	   // Get Input Hidden field for Display
	   String displayInfo    = sampleClass.getWhoReceivedRequest().trim();
	   try
	   {
	       SampleRequestUsers userClass = new SampleRequestUsers("received",
		                                      sampleClass.getWhoReceivedRequest().trim());
		   displayInfo                  = userClass.getUserName().trim();	       
	   }
	   catch(Exception e)
	   {
		   //System.out.println("Error when trying to get description of " +
			   				  //"who Received Request from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	   }
	   generalInfo[4]       = displayInfo +
	    	                  "<input type=\"hidden\" name=\"received\" value=\"" +
	    	                  sampleClass.getWhoReceivedRequest().trim() + "\">";	   
   }

//generalInfo[5] - ship via.
   if (sampleClass.getStatus().trim().equals("OP")||	//wth 06/08/2011
	   sampleClass.getStatus().trim().equals("PD"))  	//wth 06/08/2011
   {
		// Get drop down list of ship via.
		Vector shipViaList    = new Vector();
		shipViaList           = GeneralInfo.findDescByFull("SRP");
		String ddShipVia      = GeneralInfo.buildDropDownFullForKey1(
								shipViaList, sampleClass.getShipVia(),
								"shipVia", "Choose Ship Via");
		generalInfo[5]        = ddShipVia;
   }
   else
   {
	   if (sampleClass.getStatus().trim().equals("SH") ||
		   sampleClass.getStatus().trim().equals("CO"))
	   {
	   	   // Get Input Hidden field for Display
		   String displayInfo    = sampleClass.getShipVia().trim();
	 	   try
		   {
		       GeneralInfo appClass = new GeneralInfo("SRP",
		                                        25,
		                                        sampleClass.getShipVia().trim(),
		                                        "  ",
		                                        "  ",
		                                        "  ");
				displayInfo       = appClass.getDescFull().trim();	       
	  	    }
	  	    catch(Exception e)
	 	    {
		         //System.out.println("Error when trying to get long description of " +
			   				  //"Ship Via Type from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	  	    }
		    generalInfo[5]       = displayInfo +
	    	                  "<input type=\"hidden\" name=\"shipVia\" value=\"" +
	    	                  sampleClass.getShipVia().trim() + "\">";
	   }
   }

//generalInfo[6] - ship how.
   if (sampleClass.getStatus().trim().equals("PD"))
   {
		// Get drop down list of ship how.
		Vector shipHowList    = new Vector();
		shipHowList           = GeneralInfo.findDescBySeq("SRH");
		String ddShipHow      = GeneralInfo.buildDropDownFullForKey1(
								shipHowList, sampleClass.getShipHow(),
								"shipHow", "Choose How Shipped");
		generalInfo[6]        = ddShipHow;
   }
   else
   {
	   if (sampleClass.getStatus().trim().equals("SH") ||
		   sampleClass.getStatus().trim().equals("CO"))
	   {
	   	   // Get Input Hidden field for Display
		   String displayInfo    = sampleClass.getShipHow().trim();
	 	   try
		   {
		       GeneralInfo appClass = new GeneralInfo("SRH",
		                                        25,
		                                        sampleClass.getShipHow().trim(),
		                                        "  ",
		                                        "  ",
		                                        "  ");
				displayInfo       = appClass.getDescFull().trim();	       
	  	    }
	  	    catch(Exception e)
	 	    {
		         //System.out.println("Error when trying to get long description of " +
			   				  //"Ship How Type from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	  	    }
		    generalInfo[6]       = displayInfo +
	    	                  "<input type=\"hidden\" name=\"shipHow\" value=\"" +
	    	                  sampleClass.getShipHow().trim() + "\">";
	   }
   }

//generalInfo[7] - document checkboxes.
	// Call method to get checkboxes for Documents,
//	if (sampleClass.getStatus().trim().equals("OP") ||
//		sampleClass.getStatus().trim().equals("PD"))
//	{
//		generalInfo[7]  = buildDocumentCheckBoxes(sampleClass);
//	}
//	else
//	{
//		String documents = "";
//		int docNumber = sampleClass.getCheckBoxValue20().length;
//		for (int x = 0; x < docNumber; x++)
//		{
//			if (x != 0)
//			   documents = documents + ", ";
//			documents = documents + sampleClass.getCheckBoxValue20()[x].trim();
//		}
//		generalInfo[7] = documents + "&nbsp;";
//	}

//****************************
//  This customer drop down list was commented out because of the time it
//   takes to build the drop down list.
//   We went with an input field instead.
//generalInfo[8] - customers.
//   if (sampleClass.getStatus().trim().equals("OP"))
//   {

		// Get dropdown list of Customers
//		SampleRequestCustomer custClass = new SampleRequestCustomer();
//		Integer cust                    = sampleClass.getCustNumber();
//		String customer                 = cust + "";
//		String ddCustomer               = "";
//		if (server.equals("IDE"))
//			ddCustomer           = custClass.buildDropDownOfAllCustomers(
//										  		customer,
//										        "custNumber",
//										        "", "");
//		else
//			ddCustomer           = custClass.buildDropDownOfAllCustomers(
//										  		customer,
//										        "custNumber",
//										        "");
//		generalInfo[8]                 = ddCustomer;
//   }

//generalInfo[9] - order status.
	// Get drop down list of order status types.
	
	generalInfo[9]        = buildStatusDropDownList(sampleClass.
								getStatus().trim());

	//generalInfo[10] - locations.
   if (sampleClass.getStatus().trim().equals("OP"))
  {		
		// Get drop down list of locations.
		// Get drop down list of ship via.
		Vector locationList   = new Vector();
		locationList          = GeneralInfo.findDescByFull("LOC");
		String ddLocation     = GeneralInfo.buildDropDownFullForKey1(
								locationList, sampleClass.getLocation(),
								"location", "Location");
		generalInfo[10]       = ddLocation;
   }
   else
   {
		// Get Input Hidden field for Display
		String displayInfo    = sampleClass.getLocation().trim();
		
	 	try
		{
			GeneralInfo appClass = new GeneralInfo("LOC",
		                                            25,
		                                            sampleClass.getLocation().trim(),
		                                        	"  ",
		                                        	"  ",
		                                        	"  ");
			displayInfo       = appClass.getDescFull().trim();	       
	  	}
	  	catch(Exception e)
	 	{
			//System.out.println("Error when trying to get long description of " +
			   				   //"Ship Via Type from Class, " +
			                   //"CtlSampleRequest.buildDropDownLists()" + e);
	  	}
		generalInfo[10]       = displayInfo +
	    	                  "<input type=\"hidden\" name=\"location\" value=\"" +
	    	                  sampleClass.getLocation().trim() + "\">";
	  	   
   }

	
    // DETAIL SECTION:
	//  A variable number of detail lines display on every order (0-many).
	// In order to track the detail dropdown lists within the "generalInfo"
	// array, the first available list will start at array element 20.
	//
	//  Add an array element dropdown list for every detail line entry within
	// the sample order class using the "dtlSeqNumber" total elements.

//generalInfo[range of x y] unit of measure.
// x = 20, y = (20 + total number of detail lines.)

	// Get drop down list of unit of measure.
	int place        = 20;
    int count        = sampleClass.getDtlSeqNumber().length;
    int until        = place + count;
	Vector list      = new Vector();
	int nameAt       = 1;
	int classElement = 0;

    if (sampleClass.getStatus().trim().equals("SH") ||
		sampleClass.getStatus().trim().equals("CO"))
	{
  	   for (int x = place; x < until; x++)
	   {
	   	   // Get Input Hidden field for Display
		   String displayInfo    = sampleClass.getUnitOfMeasure()[classElement].trim();
	 	   try
		   {
		       GeneralInfo appClass = new GeneralInfo("UOM",
		                                        25,
		                                        sampleClass.getUnitOfMeasure()[classElement].trim(),
		                                        "  ",
		                                        "  ",
		                                        "  ");
				displayInfo       = appClass.getDescFull().trim();	       
	  	    }
	  	    catch(Exception e)
	 	    {
		         //System.out.println("Error when trying to get long description of " +
			   				  //"Unit Of Measure from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	  	    }
	 	    String varName = nameAt + "uom";
		    generalInfo[x]    = displayInfo +
	    	                  "<input type=\"hidden\" name=\"" + varName + "\" value=\"" +
	    	                  sampleClass.getUnitOfMeasure()[classElement].trim() + "\">";
	    	nameAt           = nameAt + 1;
		    classElement     = classElement + 1;                  
	   }
	}
	else
	{	
 	   if (count > 0)  
			list = GeneralInfo.findDescByFull("UOM", 25);
		
  	   for (int x = place; x < until; x++)
	   {
	       String dd        = GeneralInfo.buildDropDownFullForKey1(
				   		      list, sampleClass.getUnitOfMeasure()[classElement],
						      nameAt + "uom", "Unit Of Measure");
		   generalInfo[x]   = dd;
		   nameAt           = nameAt + 1;
		   classElement     = classElement + 1;
       }
	}

    // Get drop down list of product group.
    place        = until;
    until        = until + count;
	nameAt       = 1;
	classElement = 0;
	
    if (sampleClass.getStatus().trim().equals("SH") ||
		sampleClass.getStatus().trim().equals("CO"))
	{
  	   for (int x = place; x < until; x++)
	   {
	   	   // Get Input Hidden field for Display
		   String displayInfo    = sampleClass.getProductGroup()[classElement].trim();
	 	   try
		   {
		       GeneralInfoDried appClass = new GeneralInfoDried("GP",
		                                        sampleClass.getProductGroup()[classElement].trim());
				displayInfo       = appClass.getDescFull().trim();	       
	  	    }
	  	    catch(Exception e)
	 	    {
		         //System.out.println("Error when trying to get long description of " +
			   				  //"Product Group from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	  	    }
	 	    String varName = nameAt + "productGroup";
		    generalInfo[x]    = displayInfo +
	    	                  "<input type=\"hidden\" name=\"" + varName + "\" value=\"" +
	    	                  sampleClass.getProductGroup()[classElement].trim() + "\">";
	    	nameAt           = nameAt + 1;
		    classElement     = classElement + 1;                  
	   }
	}
	else
	{    
		if (count > 0)  
			//list = GeneralInfoDried.findDescByCode("GP");  wth 06/08/2011
			list = GeneralInfoDried.findDescByFull("GP");  //wth 06/08/2011
	
		for (int x = place; x < until; x++)
  	    {
	    	String dd        = GeneralInfoDried.buildDropDownFullForCode(
							   list, sampleClass.getProductGroup()[classElement],
						 	   nameAt + "productGroup", "Product Group");
			generalInfo[x]   = dd;
			nameAt           = nameAt + 1;
			classElement     = classElement + 1;
   		 }
	}
	

	// Get drop down list of product type.
    place        = until;
    until        = until + count;
	nameAt       = 1;
	classElement = 0;
 
    if (sampleClass.getStatus().trim().equals("SH") ||
		sampleClass.getStatus().trim().equals("CO"))
	{
  	   for (int x = place; x < until; x++)
	   {
	   	   // Get Input Hidden field for Display
		   String displayInfo    = sampleClass.getProductType()[classElement].trim();
	 	   try
		   {
		       GeneralInfoDried appClass = new GeneralInfoDried("PT",
		                                        sampleClass.getProductType()[classElement].trim());
				displayInfo       = appClass.getDescFull().trim();	       
	  	    }
	  	    catch(Exception e)
	 	    {
		         //System.out.println("Error when trying to get long description of " +
			   				  //"Product Type from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	  	    }
	 	    String varName = nameAt + "productType";
		    generalInfo[x]    = displayInfo +
	    	                  "<input type=\"hidden\" name=\"" + varName + "\" value=\"" +
	    	                  sampleClass.getProductType()[classElement].trim() + "\">";
	    	nameAt           = nameAt + 1;
		    classElement     = classElement + 1;                  
	   }
	}
	else
	{     
		if (count > 0)  
			//list = GeneralInfoDried.findDescByCode("PT");    wth 06/08/2011
			list = GeneralInfoDried.findDescByFull("PT");    //wth 06/08/2011
	
		for (int x = place; x < until; x++)
  	    {
	    	String dd        = GeneralInfoDried.buildDropDownFullForCode(
							   list, sampleClass.getProductType()[classElement],
						 	   String.valueOf(nameAt) + "productType", "Product Type");
			generalInfo[x]   = dd;
			nameAt           = nameAt + 1;
			classElement     = classElement + 1;
   		}
	}
		

	// Get drop down list of cut size.
    place        = until;
    until        = until + count;
	nameAt       = 1;
	classElement = 0;

    if (sampleClass.getStatus().trim().equals("SH") ||
		sampleClass.getStatus().trim().equals("CO"))
	{
  	   for (int x = place; x < until; x++)
	   {
	   	   // Get Input Hidden field for Display
		   String displayInfo    = sampleClass.getCutSize()[classElement].trim();
	 	   try
		   {
		       GeneralInfoDried appClass = new GeneralInfoDried("CS",
		                                        sampleClass.getCutSize()[classElement].trim());
				displayInfo       = appClass.getDescFull().trim();	       
	  	    }
	  	    catch(Exception e)
	 	    {
		         //System.out.println("Error when trying to get long description of " +
			   				  //"Cut Size from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	  	    }
	 	    String varName = nameAt + "cutSize";
		    generalInfo[x]    = displayInfo +
	    	                  "<input type=\"hidden\" name=\"" + varName + "\" value=\"" +
	    	                  sampleClass.getCutSize()[classElement].trim() + "\">";
	    	nameAt           = nameAt + 1;
		    classElement     = classElement + 1;                  
	   }
	}
	else
	{    
		if (count > 0)  
			//list = GeneralInfoDried.findDescByCode("CS");  wth 06/08/2011
			list = GeneralInfoDried.findDescByFull("CS");  //wth 06/08/2011
	
		for (int x = place; x < until; x++)
  	    {
	    	String dd        = GeneralInfoDried.buildDropDownFullForCode(
							   list, sampleClass.getCutSize()[classElement],
						 	   nameAt + "cutSize", "Cut Size");
			generalInfo[x]   = dd;
			nameAt           = nameAt + 1;
			classElement     = classElement + 1;
   		 }
	}


	// Get drop down list of color.
    place        = until;
    until        = until + count;
	nameAt       = 1;
	classElement = 0;

    if (sampleClass.getStatus().trim().equals("SH") ||
		sampleClass.getStatus().trim().equals("CO"))
	{
  	   for (int x = place; x < until; x++)
	   {
	   	   // Get Input Hidden field for Display
		   String displayInfo    = sampleClass.getColor()[classElement]
		   						   .trim();
	 	   try
		   {
		       GeneralInfoDried appClass = new GeneralInfoDried("CL",
		                                   sampleClass.getColor()[classElement]
		                                   .trim());
				displayInfo       = appClass.getDescFull().trim();	       
	  	    }
	  	    catch(Exception e)
	 	    {
		         //System.out.println("Error when trying to get long description of " +
			   				  //"Color from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	  	    }
	 	    String varName = nameAt + "color";
		    generalInfo[x]    = displayInfo +
	    	                  "<input type=\"hidden\" name=\"" + varName + "\" value=\"" +
	    	                  sampleClass.getColor()[classElement].trim() + "\">";
	    	nameAt           = nameAt + 1;
		    classElement     = classElement + 1;                  
	   }
	}
	else
	{    
		if (count > 0)  
			//list = GeneralInfoDried.findDescByCode("CL");  wth  06/08/2011
			list = GeneralInfoDried.findDescByFull("CL");  //wth  06/08/2011
	
		for (int x = place; x < until; x++)
  	    {
	    	String dd        = GeneralInfoDried.buildDropDownFullForCode(
							   list, sampleClass.getColor()[classElement],
						 	   nameAt + "color", "Color");
			generalInfo[x]   = dd;
			nameAt           = nameAt + 1;
			classElement     = classElement + 1;
   		 }
	}

	// Get drop down list of flavor.
    place        = until;
    until        = until + count;
	nameAt       = 1;
	classElement = 0;

    if (sampleClass.getStatus().trim().equals("SH") ||
		sampleClass.getStatus().trim().equals("CO"))
	{
  	   for (int x = place; x < until; x++)
	   {
	   	   // Get Input Hidden field for Display
		   String displayInfo    = sampleClass.getFlavor()[classElement]
		   						   .trim();
	 	   try
		   {
		       GeneralInfoDried appClass = new GeneralInfoDried("FL",
		                                   sampleClass.getFlavor()[classElement]
		                                   .trim());
				displayInfo       = appClass.getDescFull().trim();	       
	  	    }
	  	    catch(Exception e)
	 	    {
		         //System.out.println("Error when trying to get long description of " +
			   				  //"Flavor from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	  	    }
	 	    String varName = nameAt + "flavor";
		    generalInfo[x]    = displayInfo +
	    	                  "<input type=\"hidden\" name=\"" + varName + "\" value=\"" +
	    	                  sampleClass.getFlavor()[classElement].trim() + "\">";
	    	nameAt           = nameAt + 1;
		    classElement     = classElement + 1;                  
	   }
	}
	else
	{    
		if (count > 0)  
			//list = GeneralInfoDried.findDescByCode("FL");  wth 06/08/2011
			list = GeneralInfoDried.findDescByFull("FL");  //wth 06/08/2011
	
		for (int x = place; x < until; x++)
  	    {
	    	String dd        = GeneralInfoDried.buildDropDownFullForCode(
							   list, sampleClass.getFlavor()[classElement],
						 	   nameAt + "flavor", "Flavor");
			generalInfo[x]   = dd;
			nameAt           = nameAt + 1;
			classElement     = classElement + 1;
   		 }
	}
	
	

    // Get drop down list of chemical additive.
    place        = until;
    until        = until + count;
	nameAt       = 1;
	classElement = 0;
 
    if (sampleClass.getStatus().trim().equals("SH") ||
		sampleClass.getStatus().trim().equals("CO"))
	{
  	   for (int x = place; x < until; x++)
	   {
	   	   // Get Input Hidden field for Display
		   String displayInfo    = sampleClass.getChemicalAdditive()[classElement].trim();
	 	   try
		   {
		       GeneralInfoDried appClass = new GeneralInfoDried("CA",
		                                        sampleClass.getChemicalAdditive()[classElement].trim());
				displayInfo       = appClass.getDescFull().trim();	       
	  	    }
	  	    catch(Exception e)
	 	    {
		         //System.out.println("Error when trying to get long description of " +
			   				  //"Chemical Additive from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	  	    }
	 	    String varName = nameAt + "chemicalAdditive";
		    generalInfo[x]    = displayInfo +
	    	                  "<input type=\"hidden\" name=\"" + varName + "\" value=\"" +
	    	                  sampleClass.getChemicalAdditive()[classElement].trim() + "\">";
	    	nameAt           = nameAt + 1;
		    classElement     = classElement + 1;                  
	   }
	}
	else
	{     
		if (count > 0)  
			list = GeneralInfoDried.findDescByCode("CA");
	
		for (int x = place; x < until; x++)
  	    {
	    	String dd        = GeneralInfoDried.buildDropDownFullForCode(
							   list, sampleClass.getChemicalAdditive()[classElement],
						 	   nameAt + "chemicalAdditive", "Chemical Additive");
			generalInfo[x]   = dd;
			nameAt           = nameAt + 1;
			classElement     = classElement + 1;
   		}
	}


	// Get drop down list of fruit type.
    place        = until;
    until        = until + count;
	nameAt       = 1;
	classElement = 0;
 
    if (sampleClass.getStatus().trim().equals("SH") ||
		sampleClass.getStatus().trim().equals("CO"))
	{
  	   for (int x = place; x < until; x++)
	   {
	   	   // Get Input Hidden field for Display
		   String displayInfo    = sampleClass.getFruitType()[classElement].trim();
	 	   try
		   {
		       GeneralInfoDried appClass = new GeneralInfoDried("FT",
		                                        sampleClass.getFruitType()
		                                        [classElement].trim());
				displayInfo       = appClass.getDescFull().trim();	       
	  	    }
	  	    catch(Exception e)
	 	    {
		         //System.out.println("Error when trying to get long description of " +
			   				  //"Fruit Type from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	  	    }
	 	    String varName = nameAt + "fruitType";
		    generalInfo[x]    = displayInfo +
	    	                  "<input type=\"hidden\" name=\"" + varName + "\" value=\"" +
	    	                  sampleClass.getFruitType()[classElement].trim() + "\">";
	    	nameAt           = nameAt + 1;
		    classElement     = classElement + 1;                  
	   }
	}
	else
	{     
		if (count > 0)  
			//list = GeneralInfoDried.findDescByCode("FT");  wth 06/08/2011
			list = GeneralInfoDried.findDescByFull("FT");  //wth 06/08/2011
	
		for (int x = place; x < until; x++)
  	    {
	  	    String fruitType = sampleClass.getFruitType()[classElement];
	  	    if (fruitType.length() == 0 &&
		  	    requestType.equals("add"))
	  	       fruitType = "01"; // Default in Apple
	    	String dd        = GeneralInfoDried.buildDropDownFullForCode(
							   list, fruitType,
						 	   nameAt + "fruitType", "Fruit Type");
			generalInfo[x]   = dd;
			nameAt           = nameAt + 1;
			classElement     = classElement + 1;
   		}
	}



    // Get drop down list of fruit variety.
    place        = until;
    until        = until + count;
	nameAt       = 1;
	classElement = 0;

    if (sampleClass.getStatus().trim().equals("SH") ||
		//sampleClass.getStatus().trim().equals("CO") ||  wth 06/08/2011
		//sampleClass.getStatus().trim().equals("PD"))    wth 06/08/2011
    	sampleClass.getStatus().trim().equals("CO"))    //wth 06/08/2011
	{
  	   for (int x = place; x < until; x++)
	   {
	   	   // Get Input Hidden field for Display
		   String displayInfo    = sampleClass.getFruitVariety()[classElement].trim();
		   if (displayInfo.trim().equals(""))
		   {
		   	 if (sampleClass.getStatus().trim().equals("PD"))
		   	  displayInfo = "No Specific Variety Chosen";
		   }
		   else
		   {	
	 	      try
		      {
		         GeneralInfoDried appClass = new GeneralInfoDried("FV",
		                                        sampleClass.getFruitVariety()[classElement].trim());
				 displayInfo       = appClass.getDescFull().trim();	       
	  	      }
	  	      catch(Exception e)
	 	      {
		         //System.out.println("Error when trying to get long description of " +
			   	//			  "Fruit Variety from Class, " +
			     //             "CtlSampleRequest.buildDropDownLists()" + e);
	  	      }
		   }
	 	    String varName = nameAt + "fruitVariety";
		    generalInfo[x]    = displayInfo +
	    	                  "<input type=\"hidden\" name=\"" + varName + "\" value=\"" +
	    	                  sampleClass.getFruitVariety()[classElement].trim() + "\">";
	    	nameAt           = nameAt + 1;
		    classElement     = classElement + 1;                  
	   }
	}
	else
	{    
		if (count > 0)  
			list = GeneralInfoDried.findDescByCode("FV");
	
		for (int x = place; x < until; x++)
  	    {
	    	String dd        = GeneralInfoDried.buildDropDownFullForCode(
							   list, sampleClass.getFruitVariety()[classElement],
						 	   nameAt + "fruitVariety", "Fruit Variety");
			generalInfo[x]   = dd;
			nameAt           = nameAt + 1;
			classElement     = classElement + 1;
   		}
	}


	// Get drop down list of shipped fruit variety.
    place        = until;
    until        = until + count;
	nameAt       = 1;
	classElement = 0;

    if (!sampleClass.getStatus().trim().equals("PD"))
	{
  	   for (int x = place; x < until; x++)
	   {
	   	   // Get Input Hidden field for Display
		   String displayInfo    = sampleClass.getShippedFruitVariety()[classElement]
		   						   .trim();
	 	   try
		   {
		       GeneralInfoDried appClass = new GeneralInfoDried("FV",
		                                        sampleClass.getShippedFruitVariety()
		                                        [classElement].trim());
				displayInfo       = appClass.getDescFull().trim();	       
	  	    }
	  	    catch(Exception e)
	 	    {
		         //System.out.println("Error when trying to get long description of " +
			   				  //"Shipped Fruit Variety from Class, " +
			                  //"CtlSampleRequest.buildDropDownLists()" + e);
	  	    }
	 	    String varName = nameAt + "shippedFruitVariety";
		    generalInfo[x]    = displayInfo +
	    	                  "<input type=\"hidden\" name=\"" + varName + "\" value=\"" +
	    	                  sampleClass.getShippedFruitVariety()[classElement].trim() + "\">";
	    	nameAt           = nameAt + 1;
		    classElement     = classElement + 1;                  
	   }
	}
	else
	{    
		if (count > 0)  
			list = GeneralInfoDried.findDescByCode("FV");
	
		for (int x = place; x < until; x++)
  	    {
	    	String dd        = GeneralInfoDried.buildDropDownFullForCode(
							   list, sampleClass.getShippedFruitVariety()[classElement],
						 	   nameAt + "shippedFruitVariety", "Shipped Fruit Variety");
			generalInfo[x]   = dd;
			nameAt           = nameAt + 1;
			classElement     = classElement + 1;
   		}
	}



	// Get drop down list of Formula Numbers's, only in (Pending/Ship) Status.
	place        = until;
   	until        = until + count;
 	nameAt       = 1;
  	classElement = 0;
    
 	if (sampleClass.getStatus().trim().equals("PD") ||
	 	sampleClass.getStatus().trim().equals("SH"))
 	{
  		RandDFormula formInfo = new RandDFormula();
  		for (int x = place; x < until; x++)
     	{
   			generalInfo[x]   = formInfo.buildDropDownOfAllFormulas(
          					   sampleClass.getFormulaNumber()[classElement],
          					   "RD", nameAt + "formula", "");
   			nameAt       = nameAt + 1;
  			classElement = classElement + 1;
     	}
 	}
 	else
 	{
    	if (sampleClass.getStatus().trim().equals("OP") ||
			sampleClass.getStatus().trim().equals("CO"))
		{
 	 	  for (int x = place; x < until; x++)
	 	  {
	  	 	   // Get Input Hidden field for Display
		  	 String displayInfo    = "" + sampleClass.getFormulaNumber()[classElement];
	 	  	 try
		   	 {
			     RandDFormula formClass = new RandDFormula(sampleClass.getFormulaNumber()[classElement]);
					  displayInfo       = formClass.getName().trim();	       
		  	 }
		  	 catch(Exception e)
	 		 {
		 	     //System.out.println("Error when trying to get long description of " +
			  	 		  		    //"Formula from Class, " +
			 	                    //"CtlSampleRequest.buildDropDownLists()" + e);
  	   		 }
	 	   	 String varName = nameAt + "formula";
		     generalInfo[x]    = displayInfo +
	 	   	                  "<input type=\"hidden\" name=\"" + varName + "\" value=\"" +
	  	  	                  sampleClass.getFormulaNumber()[classElement] + "\">";
	   	 	 nameAt           = nameAt + 1;
		     classElement     = classElement + 1;                  
	  	  }
		}
 	}

	return generalInfo;
}
/**
 * Insert the method's description here.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
public GeneralInfo buildGeneralInfoViewData(String key3Value, 
											String key1Value) 
{	
	
	GeneralInfo type = null;

	try {
		type = new GeneralInfo(key1Value, 25, key3Value,
				                   "", "", "");
	} catch(Exception etype) {
		type = new GeneralInfo();
		type.setDescFull("");
	}

	if (type.getDescFull() == null)
	{
		type.setDescFull("");
	}
	
	return type;
}
/**
 * Insert the method's description here.
 * Creation date: (7/29/2003 10:00:40 AM)
 */
private String[] buildInqDropDownLists(javax.servlet.http.HttpServletRequest request,
									   javax.servlet.http.HttpServletResponse response) 
{
	String[] generalInfo = (String[]) request.getAttribute("generalInfo");
	
	// Build the necessary drop down lists for jsp presentation.
    GeneralInfo setClass = new GeneralInfo();
    GeneralInfoDried setDried = new GeneralInfoDried();
    SampleRequestUsers setUsers = new SampleRequestUsers();
    
	String sampleType     = request.getParameter("sampleType");
	if (sampleType == null || 
		sampleType.equals("None"))
	   sampleType = "";
	Vector typeList       = new Vector();
	typeList              = GeneralInfo.findDescByFull("SRC");
	String ddSampleTypes  = GeneralInfo.buildDropDownFullForKey1(
								typeList, sampleType, 
								"sampleType", "*all");
	generalInfo[0]        = ddSampleTypes;

		
	// Get drop down list of sales reps.

	String salesRep       = request.getParameter("sales");
	if (salesRep == null ||
		salesRep.equals("None"))
	   salesRep = "";	
	Vector userListBox    = new Vector();
	userListBox           = SampleRequestUsers.findTypeByName("sales");
	String ddSalesRep     = SampleRequestUsers.buildDropDownNameForProfile(
									userListBox, 
									salesRep,
									"*all");
	generalInfo[1]        = ddSalesRep;

//	** Hide - requested Removal 9/29/05 TW Project 7142
	// Get drop down list of sample applications.
//	String application    = request.getParameter("appType");
//	if (application == null ||
//		application.equals("None"))
//	   application = "";	
//	Vector appList        = new Vector();
//	appList               = GeneralInfo.findDescByFull("SRA");
//	String ddAppTypes     = GeneralInfo.buildDropDownFullForKey1(
//								appList, application, 
//								"appType", "*all");
//	generalInfo[2]        = ddAppTypes;

//	** Hide - requested Removal 9/29/05 TW Project 7142
	// Get drop down list of technicans.
//	String technician     = request.getParameter("tech");
//	if (technician == null ||
//		technician.equals("None"))
//	   technician = "";		
//	Vector techListBox          = new Vector();
//	techListBox                 = SampleRequestUsers.findTypeByName("tech");
//	String ddTechnicians        = SampleRequestUsers.buildDropDownNameForProfile(
//									techListBox, 
//									technician,
//									"*all");
//	generalInfo[3]              = ddTechnicians;


	// Get drop down list of product group.
	String prodGroup      = request.getParameter("productGroup");
	if (prodGroup == null ||
		prodGroup.equals("None"))
	   prodGroup = "";			
	Vector productGroup   = new Vector();
	productGroup          = GeneralInfoDried.findDescByFull("GP");
	String ddpg           = GeneralInfoDried.buildDropDownFullForCode(
		                    productGroup, prodGroup,
		                    "productGroup", "*all");
	generalInfo[4]       = ddpg;
	

	// Get drop down list of product type.
	String prodType       = request.getParameter("productType");
	if (prodType == null ||
		prodType.equals("None"))
	   prodType = "";				
	Vector productType    = new Vector();
	productType           = GeneralInfoDried.findDescByFull("PT");
	String ddpt           = GeneralInfoDried.buildDropDownFullForCode(
		                    productType, prodType,
		                    "productType", "*all");
	generalInfo[5]       = ddpt;


	// Get drop down list of cut Size.
	String cutSize        = request.getParameter("cutSize");
	if (cutSize == null ||
		cutSize.equals("None"))
	   cutSize = "";		
	Vector cutSizev       = new Vector();
	cutSizev              = GeneralInfoDried.findDescByFull("CS");
	String ddcs           = GeneralInfoDried.buildDropDownFullForCode(
		                    cutSizev, cutSize,
		                    "cutSize", "*all");
	generalInfo[6]       = ddcs;


	// Get drop down list of chemical additive.
	String chemAdd           = request.getParameter("chemicalAdditive");
	if (chemAdd == null ||
		chemAdd.equals("None"))
	   chemAdd = "";	
	Vector chemicalAdditivev = new Vector();
	chemicalAdditivev        = GeneralInfoDried.findDescByFull("CA");

	String ddca              = GeneralInfoDried.buildDropDownFullForCode(
		                       chemicalAdditivev, chemAdd,
         		               "chemicalAdditive" , "*all");
	generalInfo[7]  = ddca;


	// Get drop down list of fruit variety.
    String fruitVariety = request.getParameter("fruitVariety");
	if (fruitVariety == null ||
		fruitVariety.equals("None"))
	   fruitVariety = "";		
	Vector variety   = new Vector();
	variety          = GeneralInfoDried.findDescByFull("FV");

	String ddfv      = GeneralInfoDried.buildDropDownFullForCode(
		               variety, fruitVariety,
		               "fruitVariety", "*all");
	generalInfo[8]  = ddfv;

	// Get drop down list of shipped fruit variety. 
    String shippedFruitVariety = request.getParameter("shippedFruitVariety");
	if (shippedFruitVariety == null ||
		shippedFruitVariety.equals("None"))
	   shippedFruitVariety = "";		

	String ddsfv    = GeneralInfoDried.buildDropDownFullForCode(
		               variety, fruitVariety,
		               "shippedFruitVariety", "*all");
	generalInfo[9]  = ddsfv;

	// Get drop down list of Fruit Types.
    String fruitType = request.getParameter("fruitType");
	if (fruitType == null ||
		fruitType.equals("None"))
	   fruitType = "";		
	Vector fType     = new Vector();
	fType            = GeneralInfoDried.findDescByFull("FT");
	
	String ddft      = GeneralInfoDried.buildDropDownFullForCode(
		               fType, fruitType,
		               "fruitType", "*all");
	generalInfo[10]  = ddft;
	
	// Get drop down list of Color.
    String color = request.getParameter("color");
	if (color == null ||
		color.equals("None"))
	   color = "";		
	Vector colorV    = new Vector();
	colorV           = GeneralInfoDried.findDescByFull("CL");
	
	String ddcl      = GeneralInfoDried.buildDropDownFullForCode(
		               colorV, color,
		               "color", "*all");
	generalInfo[11]  = ddcl;


	// Get drop down list of Flavor.
    String flavor = request.getParameter("flavor");
	if (flavor == null ||
		flavor.equals("None"))
	   flavor = "";		
	Vector flavorV   = new Vector();
	flavorV          = GeneralInfoDried.findDescByFull("FL");
	
	String ddfl      = GeneralInfoDried.buildDropDownFullForCode(
		               flavorV, flavor,
		               "flavor", "*all");
	generalInfo[12]  = ddfl;


	// Get drop down list of shipping locations.
	String location  = request.getParameter("location");
	if (location == null ||
		location.equals("None"))
	    location = "";	
	Vector locList        = new Vector();
	locList               = GeneralInfo.findDescByFull("LOC");
	String ddLocTypes     = GeneralInfo.buildDropDownFullForKey1(
								locList, location, 
								"location", "*all");
	generalInfo[13]        = ddLocTypes;
	
	
	
	return generalInfo;
}
/**
 * Build the Detail Section for the print page, and the Email
 * Creation date: (8/18/2003 2:41:40 PM)
 */
private String buildReportDetailHTML(javax.servlet.http.HttpServletRequest request,
					    	         javax.servlet.http.HttpServletResponse response) 
{
	StringBuffer sampleDetailHTML = new StringBuffer();
//******************************************************************************
//   Receiving in Session Variables
//******************************************************************************
//*** General Information Array
   String[] generalInfo = (String[]) request.getAttribute("generalInfo");
//*** Sample Requested
   Integer sampleNumber = new Integer("0");
try
{
	  sampleNumber = (Integer) request.getAttribute("sampleNumber");   
}
catch (Exception e)
{

}  
   SampleRequestOrder chosenSample = new SampleRequestOrder(); 
try
{
   chosenSample = new SampleRequestOrder(sampleNumber);
if(chosenSample.getDtlSeqNumber().length > 0)
{
    //* Beginning of Table   
	sampleDetailHTML.append("<table border=\"1\" cellspacing=\"1\" style=\"width:98%\" align=\"right\">");

	//** row 1 - HEADINGS
	sampleDetailHTML.append(" <tr class=\"tr05\">");
	sampleDetailHTML.append("  <td class=\"td0014\" style=\"width:10%; text-align:center\"><b>Quantity</b></td>");
    sampleDetailHTML.append("  <td class=\"td0014\" style=\"width:10%; text-align:center\"><b>Sample Size</b></td>");
    sampleDetailHTML.append("  <td class=\"td0014\" style=\"text-align:center\"><b>Product Group</b></td>");
    sampleDetailHTML.append("  <td class=\"td0014\" style=\"text-align:center\"><b>Fruit Type</b></td>");
    sampleDetailHTML.append("  <td class=\"td0014\" style=\"text-align:center\"><b>Product Type</b></td>");
    sampleDetailHTML.append("  <td class=\"td0014\" style=\"text-align:center\"><b>Product Size</b></td>");
    sampleDetailHTML.append("  <td class=\"td0014\" style=\"text-align:center\"><b>Additive</b></td>");
    sampleDetailHTML.append("  <td class=\"td0014\" style=\"text-align:center\"><b>Color</b></td>");
    sampleDetailHTML.append("  <td class=\"td0014\" style=\"text-align:center\"><b>Flavor</b></td>");
    sampleDetailHTML.append("  <td class=\"td0014\" style=\"text-align:center\"><b>Resource</b></td>");
    sampleDetailHTML.append("  <td class=\"td0014\" style=\"text-align:center\"><b>Spec</b></td>");
    sampleDetailHTML.append(" </tr>");

   int lineCount = 0;
 
   for (int x = 0; x < chosenSample.getDtlSeqNumber().length; x++)
   {
	 //** New Row
	  this.itemCount = this.itemCount + chosenSample.getQuantity()[x].intValue();
      sampleDetailHTML.append(" <tr>");
      sampleDetailHTML.append("  <td class=\"td0412\" style=\"text-align:right\">&nbsp;&nbsp;" + chosenSample.getQuantity()[x] + " &nbsp;</td>");
      sampleDetailHTML.append("  <td class=\"td0412\" style=\"text-align:right\">");       
     //Quantity            
      String testSize = "" + chosenSample.getContainerSize()[x];
      if(!testSize.trim().equals("0"))
          sampleDetailHTML.append(testSize + "&nbsp;");
      sampleDetailHTML.append(chosenSample.getUnitOfMeasure()[x] + "</td>");
     // Product Description
      //------------------------------
	  // Get Product Group Description 
	  GeneralInfoDried generalClass = new GeneralInfoDried();
	  String productGroup = "&nbsp;";
	  if (!chosenSample.getProductGroup()[x].equals("00"))
	  {
		 try
		 {
			generalClass = new GeneralInfoDried("GP", chosenSample.getProductGroup()[x]);
			if (generalClass.getDescFull() != null &&
		   	    !generalClass.getDescFull().equals("null"))
		       productGroup = generalClass.getDescFull();
		 }
		 catch (Exception e)
		 {
		 	//System.out.println("Problem when instatiate of Product Group Error:" + e);
		 }
	  }
      sampleDetailHTML.append("  <td class=\"td0412\">" + productGroup + "</td>");                 
      //--------------------------
      // Get Fruit Type
	  generalClass = new GeneralInfoDried();	     
	  String productFruitType = "&nbsp;";
	  if (!chosenSample.getFruitType()[x].equals("00"))
	  {			
		 try
		 {
			generalClass = new GeneralInfoDried("FT", chosenSample.getFruitType()[x]);
			if (generalClass.getDescFull() != null &&
	 	       !generalClass.getDescFull().equals("null"))
	  		  productFruitType = generalClass.getDescFull();
		 }
		 catch (Exception e)
		 {
		 	//System.out.println("Problem when instatiate of Fruit Type Error:" + e);
		 }
	  }
 	  sampleDetailHTML.append("  <td class=\"td0412\">" + productFruitType + "</td>"); 
 	  //------------------------------
 	  // Get Product Type Description
	  generalClass = new GeneralInfoDried();
	  String productType = "&nbsp;";
	  if (!chosenSample.getProductType()[x].equals("00"))
	  {
		 try
		 {
			generalClass = new GeneralInfoDried("PT", chosenSample.getProductType()[x]);
			if (generalClass.getDescFull() != null &&
		   	   !generalClass.getDescFull().equals("null"))
		      productType = generalClass.getDescFull();
		 }
		 catch (Exception e)
		 {
		 	//System.out.println("Problem when instatiate of Product Type Error:" + e);
		 }
	  }
      sampleDetailHTML.append("  <td class=\"td0412\">" + productType + "</td>");                 
      //--------------------------------  
	  // Get Cut Size Description
	  generalClass = new GeneralInfoDried();
	  String productCutSize = "&nbsp;";
	  if (!chosenSample.getCutSize()[x].equals("00"))
	  {			
		 try
		 {
			generalClass = new GeneralInfoDried("CS", chosenSample.getCutSize()[x]);
			if (generalClass.getDescFull() != null &&
	 	       !generalClass.getDescFull().equals("null"))
	  		  productCutSize = generalClass.getDescFull();
		 }
		 catch (Exception e)
		 {
			//System.out.println("Problem when instatiate of Cut Size Error:" + e);
		 }
	  }
      sampleDetailHTML.append("  <td class=\"td0412\">" + productCutSize + "</td>");  
      //----------------------------------------
      // Get Preservative / Chemical Additive
      String preserv = chosenSample.getPreservative()[x].trim();
      if (preserv.length() == 0)
      {
	     //Chemical Additive
		 generalClass = new GeneralInfoDried();
		 if (!chosenSample.getProductGroup()[x].equals("00"))
	  	 {	
			try
			{
				generalClass = new GeneralInfoDried("CA", chosenSample.getChemicalAdditive()[x]);
				if (generalClass.getDescFull() != null &&
	  	    	   !generalClass.getDescFull().equals("null"))
			   	  preserv = generalClass.getDescFull();
			}
			catch (Exception e)
			{
				//System.out.println("Problem when instatiate of Chemical Additive Error:" + e);
			}
	  	 }
	  	 else
	  	    preserv = "&nbsp;";	          
      }
  	  sampleDetailHTML.append("  <td class=\"td0412\">" + preserv + "&nbsp;</td>");       
  	  //------------------------
      // Get Color.
	  generalClass = new GeneralInfoDried();	
	  String productColor = "&nbsp;";
	  try
	  {
		 generalClass = new GeneralInfoDried("CL", chosenSample.getColor()[x]);
		 if (generalClass.getDescFull() != null &&
       		!generalClass.getDescFull().equals("null"))
           productColor = generalClass.getDescFull();
	  }
	  catch (Exception e)
	  {
		 //System.out.println("Problem when instatiate of Color Error:" + e);
	  }
	  sampleDetailHTML.append("  <td class=\"td0412\">" + productColor + "</td>"); 
	  //-------------------------
      // Get Flavor.
	  generalClass = new GeneralInfoDried();	
	  String productFlavor = "&nbsp;";
	  try
	  {
		 generalClass = new GeneralInfoDried("FL", chosenSample.getFlavor()[x]);
		 if (generalClass.getDescFull() != null &&
       		!generalClass.getDescFull().equals("null"))
           productFlavor = generalClass.getDescFull();
	  }
	  catch (Exception e)
  	  {
		 //System.out.println("Problem when instatiate of Flavor Error:" + e);
	  }
 	  sampleDetailHTML.append("  <td class=\"td0412\">" + productFlavor + "</td>"); 
 	  //------------------ 
      // Resource
      String productResource = chosenSample.getResource()[x] + "";
      sampleDetailHTML.append("  <td class=\"td0412\">" + productResource + "&nbsp;</td>");
      //-------------------
      // Spec Number
      String productSpec = chosenSample.getSpecNumber()[x] + "";
      sampleDetailHTML.append("  <td class=\"td0412\">" + productSpec + "&nbsp;</td>"); 
      sampleDetailHTML.append(" </tr>");	  
      //------------------------------------     			  
      // Get Shipped Fruit Variety
	  generalClass = new GeneralInfoDried();	
	  String shippedVariety = "";
	  try
	  {
		 generalClass = new GeneralInfoDried("FV", chosenSample.getShippedFruitVariety()[x]);
		 if (generalClass.getDescFull() != null &&
       		!generalClass.getDescFull().equals("null"))
           shippedVariety = generalClass.getDescFull();
	  }
	  catch (Exception e)
	  {
		  //System.out.println("Problem when instatiate of Shipped Fruit Variety Error:" + e);
	  }
	  //-------------------------------------
  	  //** New row if needed
      if (chosenSample.getItemDescription()[x].trim().length() != 0 ||
          chosenSample.getAdditionalDescription()[x].trim().length() != 0 ||
         !shippedVariety.equals(""))
      {
		 sampleDetailHTML.append(" <tr>");
		 sampleDetailHTML.append("	<td colspan=\"9\" class=\"td0412\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		 sampleDetailHTML.append("      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");			   
		 sampleDetailHTML.append(       chosenSample.getItemDescription()[x].trim() + "&nbsp;" + chosenSample.getAdditionalDescription()[x].trim() + "</td>");
		 sampleDetailHTML.append("  <td colspan=\"2\" class=\"td0412\" style=\"text-align:right\">" + shippedVariety + "&nbsp;</td>");			  
		 sampleDetailHTML.append(" </tr>");
       }    
   } // END Of the FOR LOOP
   //* End of Table
   sampleDetailHTML.append("</table>");
}
}
catch (Exception e)
{
	System.out.println("Exception Problem in the JSP (When receiving in the Sample Request Vector) = " + e);
}
	return sampleDetailHTML.toString();
	
}

/**
 * Printer Friendly HTML Code, for Display
 *
 * Creation date: (11/21/2003 3:52:15 PM)
 * @return java.lang.String
 */
private String buildReportHTML(javax.servlet.http.HttpServletRequest request,
   						       javax.servlet.http.HttpServletResponse response) 
{
   Vector listSamples  = new Vector();
   Vector listSalesRep = new Vector();
   Vector listCustomer = new Vector();
   String[] generalInfo = (String[]) request.getAttribute("generalInfo");
   
try
{
   listSamples  = (Vector) request.getAttribute("listSamples");
   listSalesRep = (Vector) request.getAttribute("listSalesRep");
   listCustomer = (Vector) request.getAttribute("listCustomer");
}
catch (Exception e)
{
   System.out.println("Exception occurred in CtlSampleRequest.buildReportHTML(): " + e);
}	
   SampleRequestOrder thisOrder = new SampleRequestOrder();
   StringBuffer reportHTML = new StringBuffer();
   this.itemCount = 0;
try
{
    //* Beginning of Table   
	reportHTML.append("<table style=\"width:96%\" align=\"center\">");
   	reportHTML.append(" <tr>");
   	//reportHTML.append("  <td rowspan=\"2\"><img src=\"https://image.treetop.com/webapp/TTNewLogoMediumTransparentWithBanner.gif\"></td>");
   	//reportHTML.append("  <td rowspan=\"2\"><img src=\"https://image.treetop.com/webapp/TTNewLogoSmallTransparentWithBanner.gif\"></td>");		//wth 06/08/2011
   	//reportHTML.append("  <td rowspan=\"2\"><img src=\"https://image.treetop.com/webapp/TreeNetImages/TTLogoWordmark_w_Sabroso.gif\"></td>");		//wth 06/08/2011
   	reportHTML.append("  <td rowspan=\"2\">" +
   			"<img src=\"/web/Include/images/TT_Logo_2C_300dpi.png\" style='width:119px'>" + // jh 01/18/2014
   			"</td>");		
   	
    reportHTML.append("  <td class=\"td0424\" style=\"text-align:center\"><b>Sample Requests with Line Item Information</b></td>");
    reportHTML.append(" </tr>");
    reportHTML.append(" <tr>");
    reportHTML.append("  <td class=\"td0414\" style=\"text-align:center\">" + generalInfo[0] + "</td>");
    reportHTML.append(" </tr>");        
    reportHTML.append("</table>");
    reportHTML.append("<table cellspacing=\"0\" style=\"width:96%\" align=\"center\">");
    reportHTML.append(" <tr class=\"tr02\">");
    reportHTML.append("  <td class=\"td0416\" style=\"text-align:center\"><b>SR #</b></td>");
    reportHTML.append("  <td class=\"td0416\" style=\"text-align:center\"><b>Date Entered</b></td>");
    reportHTML.append("  <td class=\"td0416\" style=\"text-align:center\"><b>Sales Rep</b></td>");
    reportHTML.append("  <td class=\"td0416\" style=\"text-align:center\"><b>Customer</b></td>");                     
    reportHTML.append("  <td class=\"td0416\" style=\"text-align:center\"><b>Date Shipped</b></td>");
    reportHTML.append("  <td class=\"td0416\" style=\"text-align:center\"><b>Delivery Date</b></td>");	//wth 06/08/2011
	reportHTML.append(" </tr>");        
   for (int x = 0; x < listSamples.size(); x++)
   {
      thisOrder                          =  (SampleRequestOrder) listSamples.elementAt(x);
	  SampleRequestOrder detailThisOrder = new SampleRequestOrder(thisOrder.getSampleNumber());
      SampleRequestUsers thisSalesRep    = (SampleRequestUsers) listSalesRep.elementAt(x);
      SampleRequestCustomer thisCustomer = (SampleRequestCustomer) listCustomer.elementAt(x);
      request.setAttribute("sampleNumber", detailThisOrder.getSampleNumber());  
      
      reportHTML.append(" <tr>");
      reportHTML.append("  <td class=\"td04161405\" style=\"text-align:center\"><b>" + detailThisOrder.getSampleNumber() + "&nbsp;</b></td>");
      reportHTML.append("  <td class=\"td04161405\" style=\"text-align:center\"><b>" + detailThisOrder.getReceivedDate() + "&nbsp;</b></td>");
      reportHTML.append("  <td class=\"td04161405\" style=\"text-align:center\"><b>" + thisSalesRep.getUserName()        + "&nbsp;</b></td>");
      reportHTML.append("  <td class=\"td04161405\" style=\"text-align:center\"><b>" + thisCustomer.getName()            + "&nbsp;</b></td>"); 
      
      //if (detailThisOrder.getShippingDate().equals("1900-01-01"))	//wth 06/08/2011
    	  //reportHTML.append("  <td class=\"td04161405\" style=\"text-align:center\"><b>" + "" + "&nbsp;</b></td>");	//wth 06/08/2011 xyz
      //else	//wth 06/08/2011
    	  reportHTML.append("  <td class=\"td04161405\" style=\"text-align:center\"><b>" + detailThisOrder.getShippingDate() + "&nbsp;</b></td>");	//wth 06/08/2011
      //reportHTML.append("  <td class=\"td04161405\" style=\"text-align:center\"><b>" + detailThisOrder.getShippingDate() + "&nbsp;</b></td>"); 	//wth 06/08/2011
      reportHTML.append("  <td class=\"td04161405\" style=\"text-align:center\"><b>" + detailThisOrder.getDeliveryDate() + "&nbsp;</b></td>");	//wth 06/08/2011
      reportHTML.append(" </tr>");    
      reportHTML.append(" <tr>");
      reportHTML.append("  <td class=\"td0416\" colspan=\"6\">" + buildReportDetailHTML(request,response) + "</td>"); //wth 06/08/2011          
      reportHTML.append(" </tr>");   
   }
    reportHTML.append("</table>");
    
    reportHTML.append("<table style=\"width:98%\" border=\"2\">");
    reportHTML.append(" <tr class=\"tr02\">");
    reportHTML.append("  <td class=\"td0416\"><b>Total Number of Samples:</b></td>");
    reportHTML.append("  <td class=\"td0416\"><b>" + listSamples.size() + "</b></td>");          
    reportHTML.append(" </tr>");
    reportHTML.append(" <tr class=\"tr02\">");
    reportHTML.append("  <td class=\"td0416\"><b>Total Number of Samples Items:</b></td>");
    reportHTML.append("  <td class=\"td0416\"><b>" + this.itemCount + "</b></td>");          
    reportHTML.append(" </tr>");         
    reportHTML.append("</table>");
}
catch (Exception e)
{
	System.out.println("Exception occurred in CtlSampleRequest.buildReportHTML(): " + e);
}
	return reportHTML.toString();
}
/**
 * Insert the method's description here.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
public SampleRequestCustomer buildSampleCustomerViewData(Integer customerNumber) 
{
	
	SampleRequestCustomer customer = null;

	try {
		customer = new SampleRequestCustomer(customerNumber);
	} catch(Exception eUser) {
		customer = new SampleRequestCustomer();
	}

	if (customer.getName() == null)
	{
		try {
			customer.setName("");
		} catch(Exception e) {}
	}
	
	return customer;
}
/**
 * Build the Detail Section for the print page, and the Email
 * Creation date: (8/18/2003 2:41:40 PM)
 */
private String buildSampleDetailHTML(javax.servlet.http.HttpServletRequest request,
					    	         javax.servlet.http.HttpServletResponse response) 
{
	String sampleDetailHTML = "";
//******************************************************************************
//   Receiving in Session Variables
//******************************************************************************
//*** General Information Array
   String[] generalInfo = (String[]) request.getAttribute("generalInfo");
//*** Sample Requested
   Integer sampleNumber = new Integer(request.getParameter("sampleNumber"));

   SampleRequestOrder chosenSample = new SampleRequestOrder(); 
try
{
   chosenSample = new SampleRequestOrder(sampleNumber);

    //* Beginning of Table   
	sampleDetailHTML = "<table border=\"0\" cellspacing=\"0\"  cellpadding=\"2\" style=\"width:95%\" align=\"center\">";

	//** row 1
	sampleDetailHTML = sampleDetailHTML +
      "<tr style=\"background-color:lightgrey\">" +
         "<td style=\"border-top:3px solid black; " +
                     "border-left:3px solid black; " +
                     "border-bottom:2px solid black;" +
                     "width:10%\">" +
            "<b>Quantity</b>" +
         "</td>" +
         "<td style=\"border-top:3px solid black; " +
                     "border-left:1px solid black; " +
                     "border-bottom:2px solid black;" +
                     "width:10%\">" +
            "<b>Sample Size</b>" +
         "</td>" +
         "<td style=\"border-top:3px solid black; " +
                     "border-left:1px solid black; " +
                     "border-bottom:2px solid black\">" +
            "<b>Product Group</b>" +
         "</td>" +
         "<td style=\"border-top:3px solid black; " +
                     "border-bottom:2px solid black;" + 
                     "border-left:1px solid black\">" +
            "<b>Fruit Type</b>" +
         "</td>" +
         
         "<td style=\"border-top:3px solid black; " +
                     "border-bottom:2px solid black;" + 
                     "border-left:1px solid black\">" +
            "<b>Product Type</b>" +
         "</td>" +
         "<td style=\"border-top:3px solid black; " +
                     "border-bottom:2px solid black;" + 
                     "border-left:1px solid black\">" +
            "<b>Product Size</b>" +
         "</td>" +
         "<td style=\"border-top:3px solid black; " +
                     "border-bottom:2px solid black;" + 
                     "border-left:1px solid black\">" +
            "<b>Additive</b>" +
         "</td>" +         
         "<td style=\"border-top:3px solid black; " +
                     "border-bottom:2px solid black;" + 
                     "border-left:1px solid black\">" +
            "<b>Color</b>" +
         "</td>" +
         "<td style=\"border-top:3px solid black; " +
                     "border-bottom:2px solid black;" + 
                     "border-left:1px solid black\">" +
            "<b>Flavor</b>" +
         "</td>" +
         "<td style=\"border-top:3px solid black; " +
                     "border-bottom:2px solid black;" + 
                     "border-left:1px solid black\">" +
            "<b>Resource</b>" +
         "</td>";

       if (chosenSample.getViewLot().trim().equals("Y"))
      {
	     sampleDetailHTML = sampleDetailHTML +	      
           "<td style=\"border-top:3px solid black; " +
                     "border-bottom:2px solid black;" + 
                     "border-left:1px solid black\">" +
            "<b>Lot Number</b>" +
         "</td>";	      
      }
      if (chosenSample.getViewVariety().trim().equals("Y"))
      {
	     sampleDetailHTML = sampleDetailHTML +	   
           "<td style=\"border-top:3px solid black; " +
                     "border-bottom:2px solid black;" + 
                     "border-left:1px solid black\">" +
            "<b>Fruit Variety</b>" +
         "</td>";	  
      }	   									
     sampleDetailHTML = sampleDetailHTML +
         "<td style=\"border-top:3px solid black; " +
                     "border-bottom:2px solid black;" +
                     "border-left:1px solid black;" +                   
                     "border-right:3px solid black\">" +
            "<b>Spec</b>" +
         "</td>" +      
      "</tr>";

   int lineCount = 0;
   for (int x = 0; x < chosenSample.getDtlSeqNumber().length; x++)
   {
	//** row
    sampleDetailHTML = sampleDetailHTML +
      "<tr>" +
         "<td style=\"border-top:1px solid black; " +
                     "border-left:3px solid black;\">" +
            "&nbsp;&nbsp;" + chosenSample.getQuantity()[x] + " &nbsp;" +
         "</td>" +
         "<td style=\"border-top:1px solid black; " +
                     "border-left:1px solid black;\">";         
      //Quantity            
      String testSize = "" + chosenSample.getContainerSize()[x];
      if(!testSize.trim().equals("0"))
          sampleDetailHTML = sampleDetailHTML + testSize + "&nbsp;";

		  sampleDetailHTML = sampleDetailHTML + 
             chosenSample.getUnitOfMeasure()[x] +
         "</td>";
       // Product Description
	     // Get Product Group Description  
			 GeneralInfoDried generalClass = new GeneralInfoDried();
			 String productGroup = "&nbsp;";
	     if (!chosenSample.getProductGroup()[x].equals("00"))
	     {			 
		  	 try
		     {
				generalClass = new GeneralInfoDried("GP", chosenSample.getProductGroup()[x]);
			 }
			 catch (Exception e)
			 {
			 	//System.out.println("Problem when instatiate of Product Group Error:" + e);
			 }
			 if (generalClass.getDescFull() != null &&
   	            !generalClass.getDescFull().equals("null"))
                 productGroup = generalClass.getDescFull();
	     }
             sampleDetailHTML = sampleDetailHTML +
                         "<td style=\"border-top:1px solid black; " +
                                     "border-left:1px solid black\">" +
                             productGroup + "</td>";                 

                             
	     // Get Fruit Type
			generalClass = new GeneralInfoDried();	     
			String productFruitType = "&nbsp;";
	    if (!chosenSample.getFruitType()[x].equals("00"))
	    {		
			try
			{
				generalClass = new GeneralInfoDried("FT", chosenSample.getFruitType()[x]);
			}
			catch (Exception e)
			{
				//System.out.println("Problem when instatiate of Fruit Type Error:" + e);
			}
	        if (generalClass.getDescFull() != null &&
 	            !generalClass.getDescFull().equals("null"))
  		           productFruitType = generalClass.getDescFull();
	    }
  		    sampleDetailHTML = sampleDetailHTML + 
  		                            "<td style=\"border-top:1px solid black; " +
                                     "border-left:1px solid black\">" +
                             productFruitType + "</td>"; 
                              
		 // Get Product Type Description
			generalClass = new GeneralInfoDried();
			String productType = "&nbsp;";
	   if (!chosenSample.getProductType()[x].equals("00"))
	   {			
			try
			{
				generalClass = new GeneralInfoDried("PT", chosenSample.getProductType()[x]);
			}
			catch (Exception e)
			{
				//System.out.println("Problem when instatiate of Product Type Error:" + e);
			}
			
  	       if (generalClass.getDescFull() != null &&
   	          !generalClass.getDescFull().equals("null"))
    	         productType = generalClass.getDescFull();
	   }
            sampleDetailHTML = sampleDetailHTML + 
                                    "<td style=\"border-top:1px solid black; " +
                                     "border-left:1px solid black\">" +
                             productType + "</td>";                 
        
	     // Get Cut Size Description
			generalClass = new GeneralInfoDried();
			String productCutSize = "&nbsp;";
	   if (!chosenSample.getCutSize()[x].equals("00"))
	   {	
			try
			{
				generalClass = new GeneralInfoDried("CS", chosenSample.getCutSize()[x]);
			}
			catch (Exception e)
			{
				//System.out.println("Problem when instatiate of Cut Size Error:" + e);
			}
	         if (generalClass.getDescFull() != null &&
 	            !generalClass.getDescFull().equals("null"))
  		           productCutSize = generalClass.getDescFull();
	   }
            sampleDetailHTML = sampleDetailHTML + 
                                    "<td style=\"border-top:1px solid black; " +
                                     "border-left:1px solid black\">" +
                             productCutSize + "</td>";  
                              
 		// Get Preservative / Chemical Additive
         String preserv = chosenSample.getPreservative()[x].trim();
          if (preserv.length() == 0)
          {
	          //Chemical Additive
				generalClass = new GeneralInfoDried();
			if (!chosenSample.getChemicalAdditive()[x].equals("00"))
			{		
				try
				{
					generalClass = new GeneralInfoDried("CA", chosenSample.getChemicalAdditive()[x]);
				}
				catch (Exception e)
				{
					//System.out.println("Problem when instatiate of Chemical Additive Error:" + e);
				}
 	   		      if (generalClass.getDescFull() != null &&
  	    	       	 !generalClass.getDescFull().equals("null"))
		   	           preserv = generalClass.getDescFull();
			}
			else
			  preserv = "&nbsp;";	          
          }
     		    sampleDetailHTML = sampleDetailHTML + 
  		                            "<td style=\"border-top:1px solid black; " +
                                     "border-left:1px solid black\">" +
                             preserv + "&nbsp;</td>";       
                             

	     // Get Color.
			generalClass = new GeneralInfoDried();	
			String productColor = "&nbsp;";
			try
			{
				generalClass = new GeneralInfoDried("CL", chosenSample.getColor()[x]);
			}
			catch (Exception e)
			{
				//System.out.println("Problem when instatiate of Color Error:" + e);
			}
       		   if (generalClass.getDescFull() != null &&
         		  !generalClass.getDescFull().equals("null"))
           			  productColor = generalClass.getDescFull();
  		    sampleDetailHTML = sampleDetailHTML + 
  		                             "<td style=\"border-top:1px solid black; " +
                                     "border-left:1px solid black\">" +
                             productColor + "</td>"; 

        // Get Flavor.
			generalClass = new GeneralInfoDried();	
			String productFlavor = "&nbsp;";
			try
			{
				generalClass = new GeneralInfoDried("FL", chosenSample.getFlavor()[x]);
			}
			catch (Exception e)
			{
				//System.out.println("Problem when instatiate of Flavor Error:" + e);
			}
       		   if (generalClass.getDescFull() != null &&
         		  !generalClass.getDescFull().equals("null"))
           			  productFlavor = generalClass.getDescFull();
  		    sampleDetailHTML = sampleDetailHTML + 
  		                             "<td style=\"border-top:1px solid black; " +
                                     "border-left:1px solid black\">" +
                             productFlavor + "</td>"; 
           			  

  		           
        // Resource
        String productResource = chosenSample.getResource()[x] + "";
                          
             sampleDetailHTML = sampleDetailHTML + 
                   "<td style=\"border-top:1px solid black; " +
                   "border-left:1px solid black\">" +
                             productResource + "&nbsp;</td>";

      // Lot Number                        
      if (chosenSample.getViewLot().trim().equals("Y"))
      {
	      String lotNumber = chosenSample.getLotNumber()[x] + "";
	      
             sampleDetailHTML = sampleDetailHTML + 
                   "<td style=\"border-top:1px solid black; " +
                   "border-left:1px solid black\">" +
                   lotNumber + "&nbsp;</td>";

      }

      // Fruit Variety
      if (chosenSample.getViewVariety().trim().equals("Y"))
      {
	     //String variety = chosenSample.getShippedFruitVariety()[x];   wth 06/08/2011
    	 String variety = chosenSample.getFruitVariety()[x];            //wth 06/08/2011

		 GeneralInfoDried thisGeneralClass = new GeneralInfoDried();
 		 try
		 {
			thisGeneralClass = new GeneralInfoDried("FV", variety);
			variety = thisGeneralClass.getDescFull().trim();          //wth 06/08/2011
//			variety = "next";
		 }
		 catch (Exception e)
		 {
					//System.out.println("Problem when instatiate of Fruit Variety Error:" + e);
		 }
	      
         sampleDetailHTML = sampleDetailHTML + 
                 "<td style=\"border-top:1px solid black; " +
                   "border-left:1px solid black\">" +
                   variety + "&nbsp;</td>";
          
       }
                             
       // Spec Number
        String productSpec = chosenSample.getSpecNumber()[x] + "";
                          
             sampleDetailHTML = sampleDetailHTML + 
                   "<td style=\"border-top:1px solid black; " +
                   "border-left:1px solid black; " +
                   "border-right:3px solid black\">" +
                             productSpec + "&nbsp;</td></tr>";    
                              
   //** New row if needed
         if (chosenSample.getItemDescription()[x].trim().length() != 0 ||
             chosenSample.getAdditionalDescription()[x].trim().length() != 0)
         {
	         int columnSpan = 10;
             if (chosenSample.getViewLot().trim().equals("Y"))
                columnSpan++;
             if (chosenSample.getViewVariety().trim().equals("Y"))
      			columnSpan++;
	         
			sampleDetailHTML = sampleDetailHTML +
			  "<tr><td style=\"border-left:3px solid black\">&nbsp;" +
			  "</td><td colspan=\"" + columnSpan + "\" style=\"border-right:3px solid black\">" +
			  chosenSample.getItemDescription()[x].trim() + "&nbsp;" +
			  chosenSample.getAdditionalDescription()[x].trim() + "</td></tr>";
          }    
     lineCount = lineCount + 1;
     
   }
   for (int y = lineCount; y < 10; y++)
   {
	  //* row
	  if (y == lineCount)
	  	  sampleDetailHTML = sampleDetailHTML +
      "<tr>" +
         "<td colspan=\"13\" " +
               "style=\"border-top:3px solid black\">" +
            "&nbsp;" +
         "</td>" +
      "</tr>";
	  else
	  {
	  sampleDetailHTML = sampleDetailHTML +
      "<tr>" +
         "<td>" +
            "&nbsp;" +
         "</td>" +
      "</tr>";
	  }
   }

	//* End of Table
     sampleDetailHTML = sampleDetailHTML + 
   "</table>";

}
catch (Exception e)
{
	System.out.println("Exception Problem in the JSP (When receiving in the Sample Request Vector) = " + e);
}

	return sampleDetailHTML;
	
	}
/**
 * Build the HTML for both the Print Page and the Email.
 * Creation date: (8/18/2003 11:08:40 AM)
 */
private String buildSampleHTML(javax.servlet.http.HttpServletRequest request,
					    	   javax.servlet.http.HttpServletResponse response) 
{
	String sampleHTML = "";
//******************************************************************************
//   Receiving in Session Variables
//******************************************************************************
   String requestType = (String) request.getAttribute("requestType");

//*** Sample Requested
   Integer sampleNumber = new Integer(request.getParameter("sampleNumber"));	                                        
   SampleRequestOrder chosenSample = new SampleRequestOrder(); 
try
{
   chosenSample = new SampleRequestOrder(sampleNumber);

//******************* Class Customer Info  ********************************
// IF there is not customer Make all the fields Blank
// If there is a customer, put the correct information in each field.
//
   SampleRequestCustomer sampleCust = new SampleRequestCustomer();
   String custNumber  = "";
   String custName    = "";
   String custAdd1    = "";
   String custAdd2    = "";
   String custCity    = "";
   String custState   = "";
   String custZip     = "";
   String custZipExt  = "";
   String custPostCd  = "";
   String custCountry = "";

try
{
   sampleCust = (SampleRequestCustomer) request.getAttribute("custInfo");
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Customer Information Vector) = " + e);
}
   if (sampleCust != null)
   {
   if (sampleCust.getName() != null &&
       sampleCust.getCustNumber() != 0)
   {
      custNumber    = "" + sampleCust.getCustNumber();
      custName      = sampleCust.getName();
      custAdd1      = sampleCust.getAddress1();
      custAdd2      = sampleCust.getAddress2();
      custCity      = sampleCust.getCity();
      custState     = sampleCust.getState();
      if (sampleCust.getZip() != 0)
         custZip    = "" + sampleCust.getZip();
      if (sampleCust.getZipExtention() != 0)
         custZipExt = "-" + sampleCust.getZipExtention();
      custPostCd    = sampleCust.getForeignPostalCode();
      custCountry   = sampleCust.getForeignCountry();

   }
   }
//*************************************************************************************************************
//*** Class - Person Receiving Sample Request
	String recPerson = chosenSample.getWhoReceivedRequest().trim();
	if (recPerson == null || recPerson.equals("None"))
	   recPerson = "";
	SampleRequestUsers recInfo = new SampleRequestUsers();
	if (!recPerson.equals(""))
	{
		try
		{
			recInfo = new SampleRequestUsers("received", recPerson);
		}
		catch(Exception e)
		{
//				System.out.println("Problem when instatiate of Who Received Request Error:" + e);
		}
	}
	if(recInfo.getUserName() != null)
      recPerson = recInfo.getUserName();

//*************************************************************************************************************
//*** Class - Sample Request Ship Via
   String shipVia = "";
	GeneralInfo shipViaInfo = new GeneralInfo();
	try
	{
		shipViaInfo = new GeneralInfo("SRP", 
									25,
									chosenSample.getShipVia().trim(),
									"  ",
									"  ",
									"  ");
	}
	catch(Exception e)
	{
//		System.out.println("Problem when instatiate of Ship Via Error:" + e);
	}

   if(shipViaInfo.getDescFull() != null)
      shipVia = shipViaInfo.getDescFull();

//*************************************************************************************************************
//*** Class - Sample Request Ship How
   String shipHow = "";
	GeneralInfo shipHowInfo = new GeneralInfo();
	try
	{
		shipHowInfo = new GeneralInfo("SRH", 
									25,
									chosenSample.getShipHow().trim(),
									"  ",
									"  ",
									"  ");
	}
	catch(Exception e)
	{
//		System.out.println("Problem when instatiate of Ship How Error:" + e);
	}
 
   if(shipHowInfo.getDescFull() != null)
      shipHow = shipHowInfo.getDescFull();

//*************************************************************************************************************
//*** Class - Broker Information
   String brokerName = "";
  // 2/26/09 -- Use the ServiceCustomer Class to get the information I need from MOVEX
           //   put BRK on the front of the Broker Number, to get information.
  // 1/30/09 -- BROKER NO LONGER VALID -- IF needed must find a NEW way to retrieve.
       // BROKER data class no longer valid
	//Broker brokerInfo = new Broker();
	//try
	//{   //TW 9/30/05 Printing to error log every time it was 0
	//	if (chosenSample.getTerritory().intValue() != 0)
	//	  brokerInfo = new Broker(chosenSample.getTerritory());
	//}
	//catch (Exception e)
	//{
		//System.out.println("Problem when instatiate of Chemical Additive Error:" + e);
	//}
	if (chosenSample.getTerritory().intValue() != 0)
	{	
       try
       {
    	
    	  Customer inCust = new Customer();
    	  inCust.setNumber("BRK" + chosenSample.getTerritory());
    	  Customer outCust = ServiceCustomer.getCustomerByNumber(inCust);
    	  brokerName = outCust.getName();
       }
	   catch(Exception e)
	   {
	   }
	}
//   if(brokerInfo.getName() != null)
  //    brokerName = brokerInfo.getName();

//*************************************************************************************************************
//** Hide - requested Removal 9/29/05 TW Project 7142
//*** Class - Document Information.
//   String documents = "";
// try
//   {
//	   Integer[] seqNumDocument = chosenSample.getCheckBoxSeqNumber();
// 	   int       documentNumber = chosenSample.getCheckBoxSeqNumber().length;
//     String    first          = "Yes";
//      for (int x = 0; x < documentNumber; x++)
//     {
//	      if (x > 0)
//	         documents = documents + ", ";
//	         
//	      documents = documents + chosenSample.getCheckBoxValue20()[x].trim();
  //    }
//	}
//	catch (Exception e)
//	{
		//System.out.println("Problem when retrieving Documents Error:" + e);
//	}
	
//*************************************************************************************************************      
	//***** HEADER SECTION OF THE PAGE
      
    //* Beginning of Table   
	sampleHTML = "<table style=\"width:96%\" align=\"center\">";
if (requestType.equals("print") || 
	requestType.equals("printremarks"))
{
	//** row 1
	sampleHTML = sampleHTML +
      "<tr>" +
         "<td rowspan=\"2\" colspan=\"4\">" +
            //"<img src=\"https://image.treetop.com/webapp/TTMedLogo.gif\">" +
            //"<img src=\"https://image.treetop.com/webapp/TreeNetImages/TTLogoWordmark_w_Sabroso.gif\">" + // wth 06/08/2011 
         "<img src=\"/web/Include/images/TT_Logo_2C_300dpi.png\" style='width:199px'>" +
         "</td>" +
     
         " <td style=\"border-style:solid; " +
                    "border-color:black; " +
                    "background-color:lightgrey; " +
                    "text-align:center\" colspan=\"4\">" +
            "<b>SAMPLE REQUEST</b>" +
         " </td>" +
      "</tr>";

      //** row 2
      sampleHTML = sampleHTML +
       "<tr>" +
         "<td style=\"width:15%\">&nbsp;</td>" +       
         "<td style=\"text-align:right; font-size:12pt; width:9%\">" +
            "<b>No.</b>&nbsp;&nbsp;" +
         "</td>" +
         "<td style=\"text-align:left; font-size:12pt; width:9%\">" +
            "&nbsp;&nbsp;<b>" + chosenSample.getSampleNumber()  + "</b>" +
         "</td>" +
         "<td style=\"width:15%\">&nbsp;</td>" +
      "</tr>     ";
    
    //** row 3
  String newDate[] = GetDate.getDates(chosenSample.getReceivedDate());
    
    sampleHTML = sampleHTML + 
      "<tr>" +
         "<td colspan=\"4\" style=\"font-size:8pt\">" +
            "<b>&nbsp;P.O.BOX 248, SELAH, WASHINGTON 98942 U.S.A</b>" +
         "</td>" +
         "<td>" +
            "<b>Date:</b>" +
         "</td>" +
         "<td>" +
         newDate[5] +
         "</td>" +
      "</tr>";

    //** row 4
    sampleHTML = sampleHTML +
      "<tr>" +
         "<td colspan=\"4\" style=\"font-size:7pt\">" +
            "Phone: 509/697-7251&nbsp;&nbsp;" +
            "Phone: 800/367-6571&nbsp;&nbsp;" +
            "Fax: 509/697-0409&nbsp;" +
         "</td>" +
      "</tr>";

          //** row 5
    sampleHTML = sampleHTML +
      "<tr>" +
         "<td style=\"width:12%\">&nbsp;</td>" +
      "</tr>";

    //** row 6
    sampleHTML = sampleHTML +
      "<tr>" + 
         "<td>" +
            "<b>Ship To:</b>" +
         "</td>" +
         "<td colspan=\"3\">" +
            custName +
         "</td>" +
         "<td>" +
            "<b>Broker:</b>" +
         "</td>" +
         "<td colspan=\"3\">" +
            brokerName +
         "</td>" +
      "</tr>";

    //** row  
    sampleHTML = sampleHTML +
      "<tr>" +
         "<td>" +
         "</td>" +
         "<td colspan=\"3\">" +
            custAdd1 +
         "</td>" +
      "</tr>";

    //** row  
    String displayInfo    = chosenSample.getSalesRep().trim();						//wth 06/08/2011
	   try																			//wth 06/08/2011
	   {																			//wth 06/08/2011
	       SampleRequestUsers userClass = new SampleRequestUsers("sales",			//wth 06/08/2011
		                                      chosenSample.getSalesRep().trim());	//wth 06/08/2011
		   displayInfo                  = userClass.getUserName().trim();	  		//wth 06/08/2011     
	   }																			//wth 06/08/2011
	   catch(Exception e)															//wth 06/08/2011
	   {}																			//wth 06/08/2011
	//if (!custAdd2.equals(""))														//wth 06/08/2011
	//{																				//wth 06/08/2011
      sampleHTML = sampleHTML +
      "<tr>" +
         "<td>" +
         "</td>" +
         "<td colspan=\"3\">" +
            custAdd2 +
         "</td>" +
         "<td>" +																	//wth 06/08/2011
         "<b>Sales Rep:</b>" + 														//wth 06/08/2011
      "</td>" +																		//wth 06/08/2011
      "<td colspan=\"3\">" +														//wth 06/08/2011
         displayInfo +																//wth 06/08/2011
      "</td>" +																		//wth 06/08/2011
      "</tr>";
	//}																				//wth 06/08/2011

	//** row
	if (!custZip.equals(""))
	{
	   if (custZip.length() == 4)
	      custZip = "0" + custZip;
	   if (custZip.length() == 3)
	      custZip = "00" + custZip;

	  sampleHTML = sampleHTML +
      "<tr>" +
         "<td>" +
         "</td>" +
         "<td colspan=\"3\">" +
            custCity + "&nbsp;" + 
		    custState + "&nbsp;" +
            custZip + "&nbsp;" + 
            custZipExt +
         "</td>" +
      "</tr>"; 
		custCity = "";
	}

	//** row
	if (!custPostCd.equals("") ||
		(!custCountry.trim().equals("") &&
		 !custCountry.trim().equals("USA")))
	{
	  sampleHTML = sampleHTML +
      "<tr>" +
         "<td>" +
         "</td>" +
         "<td colspan=\"3\">" +
            custCity + "&nbsp;&nbsp;&nbsp;" + 
			custCountry + "&nbsp;&nbsp;" + 
			custPostCd +
         "</td>" +
      "</tr>"; 		
	}

    //** row (empty)
	   sampleHTML = sampleHTML +
      "<tr>" +
         "<td>&nbsp;" +
         "</td>" +
      "</tr>";

    //** row 
	   sampleHTML = sampleHTML +
      "<tr>" +
         "<td>" +
            "<b>Attention:</b>" +
         "</td>" +
         "<td colspan=\"3\">" +
            chosenSample.getCustContact() +
         "</td>" +
         "<td>" +
            "<b>Mark For:</b>" +
         "</td>" +
         "<td colspan=\"3\">" +
             chosenSample.getAttnContact() +
         "</td>" +
      "</tr>";
      
    //** row
    if (chosenSample.getCustContactPhone().length() != 0 ||
	    chosenSample.getAttnContactPhone().length() != 0)
    {
	   sampleHTML = sampleHTML +   
      "<tr>" +
         "<td>" +
         "</td>" +
         "<td colspan=\"3\">" +
             chosenSample.getCustContactPhone() +
         "</td>" +
         "<td>" +
         "</td>" +
         "<td colspan=\"3\">" +
             chosenSample.getAttnContactPhone() +
         "</td>" +
      "</tr>";
    }

    //** row
    if (chosenSample.getCustContactEmail().length() != 0 ||
	    chosenSample.getAttnContactEmail().length() != 0)
    {
	  sampleHTML = sampleHTML +   
      "<tr>" +
         "<td>" +
         "</td>" +
         "<td colspan=\"3\">" +
             chosenSample.getCustContactEmail() +
         "</td>" +
         "<td>" +
         "</td>" +
         "<td colspan=\"3\">" +
             chosenSample.getAttnContactEmail() +
         "</td>" +
      "</tr>";
    }

    //** row (empty)
//    sampleHTML = sampleHTML +
//      "<tr>" +
//         "<td>&nbsp;" +
//         "</td>" +
//      "</tr>";

    //** row
    sampleHTML = sampleHTML +
          "<tr>" +
         "<td>" +
            "<b>Charges:</b>" +
         "</td>" +
         "<td colspan=\"3\">" +
            chosenSample.getShippingCharge() +
         "</td>" +
         "<td>" +
            "<b>Account:</b>" +
         "</td>" +
         "<td colspan=\"3\">" +
             chosenSample.getGlAccountNumber() +
         "</td>" +
      "</tr>";

    //** row
    sampleHTML = sampleHTML +
          "<tr>" +
         "<td>" +
         "</td>" +
         "<td colspan=\"3\">" +
            shipVia +
         "</td>" +
         "<td>" +
         "</td>" +
         "<td colspan=\"3\">" +
             chosenSample.getGlAccountMisc() +
         "</td>" +
      "</tr>";
  
    //** row
    sampleHTML = sampleHTML +
          "<tr>" +
         "<td><b>Tracking #:</b>" +
         "</td>" +
         "<td colspan=\"3\">" +
            chosenSample.getTrackingNumber() +
         "</td>" +
         "<td>" +
         "</td>" +
         "<td colspan=\"3\">" +
            "</td>" +
      "</tr>";

//	** Hide - requested Removal 9/29/05 TW Project 7142
  // Row for which documents get sent.
//    sampleHTML = sampleHTML +
//    "<tr>" +  
//         "<td colspan=\"2\"><b>Documents to be Sent:</b>" +
//         "</td>" +
//         "<td colspan=\"5\">" +
//            documents +
//         "</td>" +
//      "</tr>";  
}
else
{
	//** row 1
	//sampleHTML = sampleHTML +
      //"<tr>" +
       //" <td style=\"border-style:solid; " +
                   // "border-color:black; " +
                    //"background-color:lightgrey; " +
                    //"text-align:center\" colspan=\"2\">" +
            //"<b>SAMPLE REQUEST</b>" +
         //" </td>" +
      //"</tr>";
	sampleHTML = sampleHTML +
	  "<tr>" +
       " <td style=\"border-style:solid; " +
         "border-color:black; " +
         "background-color:lightgrey; " +
         "text-align:center\" colspan=\"2\">" +
         "<b>SAMPLE REQUEST</b>" +
       " </td>" +
      "</tr>";

      //** row 2
      sampleHTML = sampleHTML +
       "<tr>" +
         "<td style=\"font-size:12pt; width=25%\">" +
            "<b>NUMBER:</b>&nbsp;&nbsp;" +
         "</td>" +
         "<td style=\"text-align:left; font-size:12pt\">" +
            "&nbsp;&nbsp;<b>" + chosenSample.getSampleNumber()  + "</b>" +
         "</td>" +
      "</tr>     ";
    
    //** row 3
  String newDate[] = GetDate.getDates(chosenSample.getReceivedDate());
    
    sampleHTML = sampleHTML + 
      "<tr>" +
         "<td>" +
            "<b>REQUEST DATE:</b>" +
         "</td>" +
         "<td>" +
         newDate[5] +
         "</td>" +
      "</tr>";

    //** row 4
    sampleHTML = sampleHTML +
      "<tr>" + 
         "<td>" +
            "<b>SHIP TO:</b>" +
         "</td>" +
         "<td>" +
            custName +
         "</td>" +
      "</tr>";

    //** row 5
    sampleHTML = sampleHTML + 
      "<tr>" +
         "<td>" +
            "<b>ATTN:</b>" +
         "</td>" +
         "<td>" +
            chosenSample.getCustContact() +
         "</td>" +
      "</tr>";

    //** row 6
    sampleHTML = sampleHTML + 
      "<tr>" +
         "<td>" +
           "</td>" +
         "<td>" +
            chosenSample.getCustContactPhone() +
         "</td>" +
      "</tr>";

    //** row 7
    sampleHTML = sampleHTML + 
      "<tr>" +
         "<td>" +
           "</td>" +
         "<td>" +
            chosenSample.getCustContactEmail() +
         "</td>" +
      "</tr>";
      
    //** row 8
    sampleHTML = sampleHTML + 
      "<tr>" +
         "<td>" +
            "<b>MARK FOR:</b>" +
         "</td>" +
         "<td>" +
            chosenSample.getAttnContact() +
         "</td>" +
      "</tr>";

    //** row 9
    sampleHTML = sampleHTML + 
      "<tr>" +
         "<td>" +
           "</td>" +
         "<td>" +
            chosenSample.getAttnContactPhone() +
         "</td>" +
      "</tr>";

    //** row 10
    sampleHTML = sampleHTML + 
      "<tr>" +
         "<td>" +
           "</td>" +
         "<td>" +
            chosenSample.getAttnContactEmail() +
         "</td>" +
      "</tr>";
      
  //** row 11
   String shipDate[] = GetDate.getDates(chosenSample.getShippingDate());

    sampleHTML = sampleHTML +
      "<tr>" + 
         "<td>" +
            "<b>SHIP DATE:</b>" +
         "</td>" +
         "<td colspan=\"2\">" +
            shipDate[5] +
         "</td>" +
      "</tr>";
  //** row 12
    sampleHTML = sampleHTML +
      "<tr>" +  
         "<td><b>Ship Via:</b>" +
         "</td>" +
         "<td colspan=\"3\">" +
            shipVia +
         "</td>" +
      "</tr>";    
  //** row 13
    sampleHTML = sampleHTML +
      "<tr>" +  
         "<td><b>Tracking #:</b>" +
         "</td>" +
         "<td colspan=\"3\">" +
            chosenSample.getTrackingNumber() +
         "</td>" +
      "</tr>";   
//	** Hide - requested Removal 9/29/05 TW Project 7142
  // Row for which documents get sent.
//    sampleHTML = sampleHTML +
//      "<tr>" +  
//         "<td><b>Documents to be Sent:</b>" +
//         "</td>" +
//         "<td colspan=\"3\">" +
//            documents +
//         "</td>" +
//      "</tr>";  
}

  	//* End of First Table
     sampleHTML = sampleHTML +
   "</table><br>";

   //***** GET THE DETAIL SECTION
   String detailHTML = buildSampleDetailHTML(request,
	                                         response);

      sampleHTML = sampleHTML + detailHTML;

if (requestType.equals("print") ||
	requestType.equals("printremarks"))
{
   //***** BOTTOM PART OF THE PAGE
   sampleHTML = sampleHTML +
   "<table style=\"width:95%\" align=\"center\">" +
      "<tr>" +
         "<td style=\"width:5%\">&nbsp;</td>" +
         "<td>" +
            "<b>SAMPLE REQUEST PER</b>&nbsp;&nbsp;" +
         "</td>" +
         "<td style=\"border-bottom:1px solid black;" +
                    "width:30%\">" +
            chosenSample.getWhoRequested().trim() + "&nbsp;" +
         "</td>" +
         "<td>" +
            "&nbsp;&nbsp<b>TO</b>&nbsp;&nbsp;" +
         "</td>" +
         "<td style=\"border-bottom:1px solid black;" +
                    "width:30%\">" +
            recPerson + "&nbsp;" +
         "</td>" +
         "<td style=\"width:5%\">&nbsp;</td>" +
      "</tr>" +
   "</table>" +
"<br>";

//** NEW TABLE
   // determine if shipped date should be displayed.						  wth 07/28/2011
   String shipDate = chosenSample.getShippingDate().toString();				//wth 07/28/2011
   
   if (chosenSample.getStatus().trim().equals("OP") ||						//wth 07/28/2011
	   chosenSample.getStatus().trim().equals("PD") ||						//wth 07/28/2011
	   chosenSample.getShippingDate().toString().equals("1900-01-01"))		//wth 07/28/2011
	   shipDate = "";														//wth 07/28/2011
   
   sampleHTML = sampleHTML +
   "<table border=\"0\" cellspacing=\"0\" cellpadding=\"10\" style=\"width:95%\" align=\"center\">" +
      "<tr>" +
         "<td style=\"width:5%;" +
                    "border-top:3px solid black;" +
                    "border-bottom:3px solid black\">&nbsp;</td>" +
         "<td style=\"width:10%;" +
                    "border-top:3px solid black;" +
                    "border-bottom:3px solid black\">" +
            "<b>SHIPPED:</b>" +
         "</td>" +
         "<td style=\"width:14%;" +
                    "text-align:right;" +
                    "border-top:3px solid black;" +
                    "border-bottom:3px solid black\">" +
 //wth 07/28/2011                   "<b>Date:</b>&nbsp;" + chosenSample.getShippingDate() +	  
            "<b>Date:</b>&nbsp;" + shipDate +								//wth 07/28/2011
         "</td>" +  
         "<td style=\"width:20%;" +
                    "border-top:3px solid black;" +
                    "border-bottom:3px solid black\">" +
            "&nbsp;" + 
         "</td>" +
         "<td style=\"width:7%;" +
                    "text-align:right;" +
                    "border-top:3px solid black;" +
                    "border-bottom:3px solid black\">" +
            "<b>How:</b>&nbsp;" +
         "</td>" +
         "<td colspan=\"2\" style=\"" +
                    "border-top:3px solid black;" +
                    "border-bottom:3px solid black\">" +
            "&nbsp;" + shipHow +
         "</td>" +
         "<td style=\"border-top:3px solid black;" +
                   "border-bottom:3px solid black\">&nbsp;</td>" +
      "</tr>" +
   "</table>" +
"<br>";
if (requestType.equals("printremarks"))
{
//** LAST TABLE
   sampleHTML = sampleHTML +
   "<table border=\"0\" cellspacing=\"0\" style=\"width:95%\" align=\"center\">" +
      "<tr>" +
         "<td style=\"width:5%\">&nbsp;</td>" +
         "<td style=\"vertical-align:text-top; width:10%\">" +
            "<b>REMARKS:</b>" +
         "</td>" +
         "<td>";
         
   String delDate[] = GetDate.getDates(chosenSample.getDeliveryDate());
   sampleHTML = sampleHTML +
		 "Delivery Date: " + delDate[5];
   
   for (int x = 0; x < chosenSample.getRemark().length; x++)
   {
	   sampleHTML = sampleHTML + "<br>" +
	        chosenSample.getRemark()[x];
   }

   sampleHTML = sampleHTML +
        "&nbsp; " +
         "</td>" +
      "</tr>" +
      "<tr>" +
         "<td>&nbsp;</td>" +
      "</tr>" +
      "<tr>" +
         "<td style=\"text-align:center\" colspan=\"3\">" +
            "<b>BROKER COPY</b>" +
         "</td>" +
      "</tr>" +
   "</table> ";
}
else
{
	//** LAST TABLE
   sampleHTML = sampleHTML +
   "<table border=\"0\" cellspacing=\"0\" style=\"width:95%\" align=\"center\">" +
      "<tr>" +
         "<td style=\"text-align:center\" colspan=\"3\">" +
            "<b>PACKING SLIP</b>" +
         "</td>" +
      "</tr>" +
   "</table> ";
}  

}
   
}
catch (Exception e)
{
   System.out.println("Exception Problem in the JSP (When receiving in the Sample Request Vector) = " + e);
}
	return sampleHTML;
	
	}
/**
 * Insert the method's description here.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
public SampleRequestUsers buildSampleUserViewData(String userType, 
											      String userProfile) 
{	
	
	SampleRequestUsers user = null;

	try {
		user = new SampleRequestUsers(userType, userProfile);
	} catch(Exception eUser) {
		user = new SampleRequestUsers();
	}

	if (user.getUserName() == null)
	{
		try {
			user.setUserName("");
		} catch(Exception e) {}
	}
	
	return user;
}
/**
 * Build the Email which will be sent out, when an order has been shipped
 * Creation date: (7/25/2003 3:26:40 PM)
 */
private String buildShippedEmail(SampleRequestOrder sampleClass,
								 javax.servlet.http.HttpServletRequest request,
					    	     javax.servlet.http.HttpServletResponse response)  
{
	String returnMessage = "";

	try
	{
		String custName = "";
		// Retrieve Customer Information
		try
		{
			SampleRequestCustomer custClass = new SampleRequestCustomer(sampleClass.getCustNumber());
			custName = custClass.getName();
			request.setAttribute("custInfo", custClass);
	
		}catch(Exception e)
		{
			// If there is a problem when instantiating the class, the name is left blank
		}
		
		// Load up the to array, for people who get this email
		String[] to      = new String[8];
		to[0] = sampleClass.getEmailWhenShipped1();
		to[1] = sampleClass.getEmailWhenShipped2();
		to[2] = sampleClass.getEmailWhenShipped3();
		to[3] = sampleClass.getEmailWhenShipped4();
		to[4] = sampleClass.getEmailWhenShipped5();

//		** Hide - requested Removal 9/29/05 TW Project 7142
		//** Test if technician wants to receive email when shipped.
		int x = 5;
//		try
//		{
//		    SampleRequestUsers techInfo = new SampleRequestUsers("tech", 
//											                     sampleClass.getTechnician());
//		    if (techInfo.getNotifyShipped().trim().equals("Y"))
//		    {
//			    to[x] = sampleClass.getTechnician();
//			    x++;
//		    }
//		}
//		catch(Exception e)
//		{
//			System.out.println("Exception caught in CtlSampleRequest.buildShippedEmail(): " + e +
//				"\n  Occured when trying to get Technician Information.");
//		}
		//** Test if sales rep wants to receive email when shipped.
		try
		{
		    SampleRequestUsers salesInfo = new SampleRequestUsers("sales", 
											                     sampleClass.getSalesRep());
		    if (salesInfo.getNotifyShipped().trim().equals("Y"))
		    {
			    to[x] = sampleClass.getSalesRep();
			    x++;
		    }
		}
		catch(Exception e)
		{
			System.out.println("Exception caught in CtlSampleRequest.buildShippedEmail(): " + e +
				"\n  Occured when trying to get Sales Rep Information.");
		}
		//** Test if received by person wants to receive email when shipped.
		try
		{
		    SampleRequestUsers receivedInfo = new SampleRequestUsers("received", 
											                     sampleClass.getWhoReceivedRequest());
		    if (receivedInfo.getNotifyShipped().trim().equals("Y"))
		    {
			    to[x] = sampleClass.getWhoReceivedRequest();
		    }
		}
		catch(Exception e)
		{
			System.out.println("Exception caught in CtlSampleRequest.buildShippedEmail(): " + e +
				"\n  Occured when trying to get Sales Rep Information.");
		}

		String[] cc      = new String[0];
		String[] bcc     = new String[0];
		// put current user profile as the from
		String   from    = com.treetop.SessionVariables.getSessionttiProfile(request, response);
		// subject
		String   subject = "A Sample (" + sampleClass.getSampleNumber() + 
		                   ") Has Been Shipped to " + custName;
		// body
		String   body    = "<html>" +
                           "<head>" +
                           "<style type=\"text/css\">" +
                           "td {font-family:arial;" +
                           "    font-size:10pt}" +
                           "</style>" +
                           "</head>" +
                           "<body>" + 
                           "<table> <tr> <td> " + // wth 06/08/2011 
                           //"<img src=\"https://image.treetop.com/webapp/TreeNetImages/TTLogoWordmark_w_Sabroso.gif\" />" + // wth 06/08/2011
                           "<img src=\"/web/Include/images/TT_Logo_2C_300dpi.png\" style='width:199px' />" +
                          "</td> </tr> </table> " + // wth 06/08/2011 
                           buildSampleHTML(request, response) +
                           "</body>" +
                           "</html>";

		// If a Message was returned there was a problem.
		
		// override the shipping emails for test purposes.		wth 06/08/2010
		//to      = new String[8]; //temporary 					wth 06/08/2011
		//to[0]   = "THAILE";      //temporary					wth 06/08/2011
		//to[1]	= "TWALTO";		   //temporary					wth 06/08/2011
		//to[2]   = "JHAGLE";	   //temporary					wth 06/08/2011
		//to[1]	= "DGILL";		   //temporary					wth 06/08/2011
		Email sendMessage = new Email();
		returnMessage = sendMessage.sendEmail(to,
			    			     	    	  cc,
   							     	    	  bcc,
		 					     	    	  from,
		     				     	    	  subject,
					      	     	    	  body);

	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleRequest.buildShippedEmail: " + e);
		returnMessage = "Problem within CtlSampleRequest.buildShippedEmail " + e;
		            
	}
	return returnMessage;
}
/**
 * Take in a String (Status), and return a String
 *   Drop Down List.
 *
 * Creation date: (8/5/2003 2:18:40 PM)
 */
private String buildStatusDropDownList(String status) 
{
	String statusDropDown = "";
	try
	{
		GeneralInfo descInfo = new GeneralInfo();
		// Get drop down list of order status types.
		Vector statusList     = new Vector();
		statusList            = GeneralInfo.findDescBySeq("SRS");
		int firstOne = 0; // this is the first record needed.	
		int lastOne  = 0; // this is the last record needed.
		
		if (status.trim().equals("OP"))
		{
		   firstOne = 0;
		   lastOne  = 1;
		}
		if (status.trim().equals("PD"))
		{
		   firstOne = 0;
		   lastOne  = 2;
		}
		if (status.trim().equals("SH"))
		{
		   firstOne = 1;
		   lastOne  = 3;
		}
		if (status.trim().equals("CO"))
		{
		   firstOne = 2;
		   lastOne  = 3;
		}
		
  		for (int x = firstOne; x <= lastOne; x++)
 		{
			String selected     = "";
   		    GeneralInfo nextDesc = (GeneralInfo) statusList.elementAt(x);

   		    if (status.trim().equals(nextDesc.getKey1Value().trim()))
   		    selected = "' selected='selected'>";
   		    else
   		    selected = "'>";
		   		    
   		    statusDropDown = statusDropDown + "<option value='" + 
						    nextDesc.getKey1Value().trim() + selected +
				   		    nextDesc.getDescFull().trim() + "&nbsp;";
  		}
	    if (!statusDropDown.equals(""))
	    {	   		    
		    statusDropDown = "<select name='status'>" +
	  		    		      statusDropDown + "</select>";  	 
        }
  	  		
	}
	catch (Exception e)
	{
		System.out.println("Exception Caught from CtlSampleRequest.buildStatusDropDownList() = " + e);
	}
	return statusDropDown;
}
/**
 * Delete a Sample Request using parameters retrieved from a jsp via 
 * the Request Class.
 *
 *  Returns a message if there is an error, or Completed Normally if there
 *    is no problems
 * 
 * Creation date: (10/8/2003 3:19:40 PM)
 */
private String deleteSample(javax.servlet.http.HttpServletRequest request,
		  				    javax.servlet.http.HttpServletResponse response)  
{
	String message = "";
	try
	{
 		String sampleNumber = request.getParameter("sampleNumber");
 		request.setAttribute("sampleNumber", null);
		if (sampleNumber != null && sampleNumber.length() != 0)
		{
			try
			{
				SampleRequestOrder deleteThis = new SampleRequestOrder();
				Integer testedNumber = new Integer(sampleNumber);
				try
				{
				  deleteThis = new SampleRequestOrder(testedNumber);
				}
				catch(Exception e)
				{
					message = message +
					     "This sample (" + testedNumber + ") was not found.";
				}
				if (deleteThis.getStatus().trim().equals("OP") ||
					deleteThis.getStatus().trim().equals("PD"))
				{
					int thisRequest = testedNumber.intValue();
  				    boolean deleteTest = deleteThis.deleteBySampleNumber(thisRequest);
					if (deleteTest = false)
					{
					   message = message +
						  "There is a problem when trying to delete sample (" +
						  testedNumber + ")\\n" + 
						  "This sample was not deleted. " +
						  " \\n problem occured in CtlSampleRequest.deleteSample()"; 
					}
			//		System.out.println("Delete");
				
				}

		//		System.out.println("Stop");
				
			}
			catch(NumberFormatException nfe)
			{
				message = message +
					  "There is a problem with the Sample Number, " + 
					  "Please contact the helpDesk with this problem.\\n " +
					  "com.treetop.servlets.CtlSampleRequest." +
					  "deleteSample()";
			}		
		}
	}
	catch(Exception e)
	{
		System.out.println("Exception occurred in CtlSampleRequest.deleteSample(): " + e);
	}
	if (message.equals(""))
	   message = "Completed Normally";	
	return message;
}
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
 * Insert the method's description here.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
public void newMethod() {}
/**
 * Use to send information to the detail Page.
 * Creation date: (7/14/2003 4:06:40 PM)
 */
private void pageDtl(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{
	try
	{
	  //** Bring in Variables.
	  	String[] generalInfo = (String[]) request.getAttribute("generalInfo");
		String msg = (String) request.getAttribute("msg");
		String requestType = (String) request.getAttribute("requestType");
		
		Vector sampleDetail = new Vector(); // To be put in a Session Variable to be
	                                       //  accessed in the JSP.

        Integer sampleNumber = new Integer(request.getParameter("sampleNumber"));
        SampleRequestOrder chosenSample = new SampleRequestOrder();
		SampleRequestCustomer custInfo = new SampleRequestCustomer();
        try
        {	
			chosenSample = new SampleRequestOrder(sampleNumber);       
			Integer custNumber = chosenSample.getCustNumber();
			String newCust = custNumber + "";
			int custNumberInt  = Integer.parseInt(newCust);
			if (custNumberInt != 0)
			{
				try
				{
					custInfo = new SampleRequestCustomer(custNumber);
				}
				catch(Exception e)
				{
//					System.out.println("Problem when instatiate of customer Error:" + e);
				}
			}			                                  
        }
        catch(Exception e)
        {}
        // 3/26/12 TWalton 
        // Added Ability to "get" comments
    	if (chosenSample.getSampleNumber().intValue() > 0)
    	{
    		KeyValue kv = new KeyValue();
    		kv.setEnvironment("PRD");
    		kv.setKey1(chosenSample.getSampleNumber().toString().trim());
   			try {
   				kv.setEntryType("SampleComment");
   				chosenSample.setListComments(ServiceKeyValue.buildKeyValueList(kv));
   			}catch(Exception e){
   				chosenSample.setListComments(new Vector());
   			}	  		
    		try {
    			kv.setEntryType("SampleUrl");
    			chosenSample.setListURLs(ServiceKeyValue.buildKeyValueList(kv));
    		}catch(Exception e){
    			chosenSample.setListURLs(new Vector());
    		}	  	
    	}
    	sampleDetail.addElement(chosenSample);
		request.setAttribute("sampleDetail", sampleDetail);
		request.setAttribute("custInfo", custInfo);

	if (requestType.equals("print") ||
		requestType.equals("printremarks"))
    {
	       
		String builtPage = buildSampleHTML(request, response);
		
		HttpSession sess = request.getSession(true);
		sess.setMaxInactiveInterval(600);
        sess.setAttribute("pageHTML", builtPage);
             
 		response.sendRedirect("/web/JSP/SampleRequest/prntSample.jsp");
				
     }
     else
     {	
//		** Hide - requested Removal 9/29/05 TW Project 7142
		// Get Technician Name / Information
//		String tech = chosenSample.getTechnician();
//		SampleRequestUsers techInfo = new SampleRequestUsers();
//		try
//		{
//			techInfo = new SampleRequestUsers("tech", tech);
//		}
//		catch(Exception e)
//		{
//			System.out.println("Problem when instatiate of Technician Error:" + e);
//		}
		SampleRequestUsers repInfo = new SampleRequestUsers();
		SampleRequestUsers recInfo = new SampleRequestUsers();
		GeneralInfo statusInfo = new GeneralInfo();
		GeneralInfo typeInfo = new GeneralInfo();
		GeneralInfo shipViaInfo = new GeneralInfo();
		GeneralInfo shipHowInfo = new GeneralInfo();

	try
	{	
		// Get Sales Rep Name / Information
		String salesRep = chosenSample.getSalesRep();
		try
		{
			repInfo = new SampleRequestUsers("sales", salesRep);
		}
		catch(Exception e)
		{
//			System.out.println("Problem when instatiate of Sales Rep Error:" + e);
		}

		// Get Person Receiving Request / Information
		String salesRec = chosenSample.getWhoReceivedRequest().trim();
		if (!salesRec.equals("None"))
		{
			try
			{
				recInfo = SampleRequestUsers.findUserByProfile(salesRec);
			}
			catch(Exception e)
			{
//				System.out.println("Problem when instatiate of Who Received Request Error:" + e);
			}
		}
		
		// Sample Status
		try
		{
			statusInfo = new GeneralInfo("SRS", 
										25,
										chosenSample.getStatus().trim(),
										"  ",
										"  ",
										"  ");
		}
		catch(Exception e)
		{
//			System.out.println("Problem when instatiate of Status Error:" + e);
		}

		// Sample Type
		try
		{
			typeInfo = new GeneralInfo("SRC", 
										25,
										chosenSample.getType().trim(),
										"  ",
										"  ",
										"  ");
		}
		catch(Exception e)
		{
//			System.out.println("Problem when instatiate of RequestType Error:" + e);
		}
//		** Hide - requested Removal 9/29/05 TW Project 7142
		// Sample Application
//		GeneralInfo appInfo = new GeneralInfo();
//		try
//		{
//			appInfo = new GeneralInfo("SRA", 
//										25,
//										chosenSample.getApplication().trim(),
//										"  ",
//										"  ",
//										"  ");
//		}
//		catch(Exception e)
//		{
//			System.out.println("Problem when instatiate of Sample Application Error:" + e);
//		}
		
		// Ship Via
		try
		{
			shipViaInfo = new GeneralInfo("SRP", 
										25,
										chosenSample.getShipVia().trim(),
										"  ",
										"  ",
										"  ");
		}
		catch(Exception e)
		{
//			System.out.println("Problem when instatiate of Ship Via Error:" + e);
		}

		// Ship How
		try
		{
			shipHowInfo = new GeneralInfo("SRH", 
										25,
										chosenSample.getShipHow().trim(),
										"  ",
										"  ",
										"  ");
		}
		catch(Exception e)
		{
//			System.out.println("Problem when instatiate of Ship Via Error:" + e);
		}

		// How Many Detail Records are there.
		int lineItemCount = chosenSample.getDtlSeqNumber().length;

		// Detail Line Vectors
		if (lineItemCount > 0)
		{
			
			Vector prodGroup       = new Vector();
			Vector prodType        = new Vector();
			Vector cutSize         = new Vector();
			Vector color           = new Vector();
			Vector flavor          = new Vector();
			Vector chemAdd         = new Vector();
			Vector fruitVar        = new Vector();
			Vector shippedFruitVar = new Vector();
			Vector fruitType       = new Vector();

			for (int x = 0; x < lineItemCount; x++)
			{
				//* Product Group
				GeneralInfoDried generalClass = new GeneralInfoDried();
				if (!chosenSample.getProductGroup()[x].equals("00"))
				{
				try
				{
					generalClass = new GeneralInfoDried("GP", chosenSample.getProductGroup()[x]);
				}
				catch (Exception e)
				{
					//System.out.println("Problem when instatiate of Product Group Error:" + e);
				}
				}
				prodGroup.addElement(generalClass);
				
				//* Product Type
				generalClass = new GeneralInfoDried();
				if (!chosenSample.getProductType()[x].equals("00"))
				{
				try
				{
					generalClass = new GeneralInfoDried("PT", chosenSample.getProductType()[x]);
				}
				catch (Exception e)
				{
					//System.out.println("Problem when instatiate of Product Type Error:" + e);
				}
				}
				prodType.addElement(generalClass);
				
				//* Cut Size
				generalClass = new GeneralInfoDried();
				if (!chosenSample.getCutSize()[x].equals("00"))
				{
				try
				{
					generalClass = new GeneralInfoDried("CS", chosenSample.getCutSize()[x]);
				}
				catch (Exception e)
				{
					//System.out.println("Problem when instatiate of Cut Size Error:" + e);
				}
				}
				cutSize.addElement(generalClass);

				//* Color
				generalClass = new GeneralInfoDried();
				try
				{
					generalClass = new GeneralInfoDried("CL", chosenSample.getColor()[x]);
				}
				catch (Exception e)
				{
					//System.out.println("Problem when instatiate of Color Error:" + e);
				}
				color.addElement(generalClass);

				//* Flavor
				generalClass = new GeneralInfoDried();
				try
				{
					generalClass = new GeneralInfoDried("FL", chosenSample.getFlavor()[x]);
				}
				catch (Exception e)
				{
					//System.out.println("Problem when instatiate of Flavor Error:" + e);
				}
				flavor.addElement(generalClass);
				
				//* Chemical Additive
				generalClass = new GeneralInfoDried();
				if (!chosenSample.getChemicalAdditive()[x].equals("00"))
				{
				try
				{
					generalClass = new GeneralInfoDried("CA", chosenSample.getChemicalAdditive()[x]);
				}
				catch (Exception e)
				{
					//System.out.println("Problem when instatiate of Chemical Additive Error:" + e);
				}
				}
				chemAdd.addElement(generalClass);
				
				//* Fruit Variety
				generalClass = new GeneralInfoDried();
				try
				{
					generalClass = new GeneralInfoDried("FV", chosenSample.getFruitVariety()[x]);
				}
				catch (Exception e)
				{
					//System.out.println("Problem when instatiate of Fruit Variety Error:" + e);
				}
				fruitVar.addElement(generalClass);

				//* Shipped Fruit Variety
				generalClass = new GeneralInfoDried();
				try
				{
					generalClass = new GeneralInfoDried("FV", chosenSample.getShippedFruitVariety()[x]);
				}
				catch (Exception e)
				{
					//System.out.println("Problem when instatiate of Shipped Fruit Variety Error:" + e);
				}
				shippedFruitVar.addElement(generalClass);

				//* Fruit Type
				generalClass = new GeneralInfoDried();
				if (!chosenSample.getFruitType()[x].equals("00"))
				{
				try
				{
					generalClass = new GeneralInfoDried("FT", chosenSample.getFruitType()[x]);
				}
				catch (Exception e)
				{
					//System.out.println("Problem when instatiate of Fruit Type Error:" + e);
				}
				}
				fruitType.addElement(generalClass);
					

			}
  
			request.setAttribute("prodGroup", prodGroup);	
			request.setAttribute("prodType", prodType);	
			request.setAttribute("cutSize", cutSize);
			request.setAttribute("color", color);
			request.setAttribute("flavor", flavor);	
			request.setAttribute("chemAdd", chemAdd);	
			request.setAttribute("fruitVar", fruitVar);
			request.setAttribute("shippedFruitVar", shippedFruitVar);
			request.setAttribute("fruitType", fruitType);	
		}
	}
	catch(Exception e)
	{}
  // retrieve session variables for Sample Request security.
    Vector listSecurity = new Vector();
    HttpSession sess = request.getSession(true);			
	String techAuth  = (String) sess.getAttribute("techAuth");
	String salesAuth = (String) sess.getAttribute("salesAuth");
	String recvAuth  = (String) sess.getAttribute("recvAuth");
		// security.
		String access = "no";
	try
	{
		if (chosenSample.getStatus().trim().equals("OP") )
		{
			if (salesAuth.equals("yes") || recvAuth.equals("yes") )
				access = "yes";
		} else
		{
			if (techAuth.equals("yes") )
				access = "yes";
		}
	}
	catch(Exception e)
	{}
		
		listSecurity.addElement(access);    
		request.setAttribute("listSecurity", listSecurity);
			
		request.setAttribute("shipViaInfo", shipViaInfo);
		request.setAttribute("shipHowInfo", shipHowInfo);
//		** Hide - requested Removal 9/29/05 TW Project 7142
//		request.setAttribute("appInfo", appInfo);
		request.setAttribute("typeInfo", typeInfo);
		request.setAttribute("statusInfo", statusInfo);
		request.setAttribute("recInfo", recInfo);
		request.setAttribute("repInfo", repInfo);
//		** Hide - requested Removal 9/29/05 TW Project 7142
//		request.setAttribute("techInfo", techInfo);
		
		request.setAttribute("generalInfo", generalInfo);
		

			//***** Go to the Dtl JSP *****//
			getServletConfig().getServletContext().
				getRequestDispatcher("/JSP/SampleRequest/dtlSample.jsp?" +
					"msg=" + msg).
				forward(request, response);
     }
	

	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleRequest.pageDtl: " + e);
	}  	    	
}
/**
 * Use to send information to the inquiry Page.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
private void pageInq(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{
	try
	{
	  
	  //** Bring in Variables.
	  	String[] generalInfo = (String[]) request.getAttribute("generalInfo");
		String msg = (String) request.getAttribute("msg");
		String requestType = (String) request.getAttribute("requestType");

		generalInfo = buildInqDropDownLists(request, response);
		request.setAttribute("generalInfo", generalInfo);
	
		//***** Go to the Inq JSP *****//
		getServletConfig().getServletContext().
			getRequestDispatcher("/JSP/SampleRequest/inqSample.jsp?" +
				"msg=" + msg).
			forward(request, response);
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleRequest.pageInq: " + e);
	}  	    	
}
/**
 * Use to send information to the List Jsp.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
private void pageList(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{	
	try
	{
	  //** Bring in Variables.
	    String[] generalInfo = (String[]) request.getAttribute("generalInfo");
	  	String msg = (String) request.getAttribute("msg");
		String requestType = (String) request.getAttribute("requestType");

		//*** Use for information about what was queried.
		String queryInfo = "";
		String parmResend = "";
 
        Vector listSample = new Vector(); // To be put in as a Request Variable
        								   //  accessed by the JSP.
       

        // Use the single sample number or all other selection inquiry fields
        // from the inquiry jsp. 
		Integer sampleNumber = (Integer) request.getAttribute("sampleNumber");

		if (sampleNumber != null)
		{
			try
			{
				SampleRequestOrder chosenSample = new SampleRequestOrder(sampleNumber); 
				listSample.addElement(chosenSample);
				queryInfo = "Sample Number: " + sampleNumber;
				parmResend = "&sampleNumber=" + sampleNumber;
			}
			catch(Exception e)
			{
			    System.out.println("Error in CtlSampleRequest.pageList When trying to retrive Sample: " + e);	
			}
		}
		else
		{			
			String[] status = new String[4];
		    int countStatus = 0;
			if (request.getParameter("OP") != null && 
				!request.getParameter("OP").equals("null"))
			{
				status[countStatus] = "OP";
				countStatus++;
				if (countStatus == 1)
				   queryInfo = "Status: ";
				queryInfo = queryInfo + "<i>Open</i> ";	
				parmResend = parmResend +
				           "&OP=" + 
				            request.getParameter("OP");	
			}
			if (request.getParameter("PD") != null && 
				!request.getParameter("PD").equals("null"))
			{
				status[countStatus] = "PD";
				countStatus++;
				if (countStatus == 1)
				   queryInfo = "Status: ";
				else
				   queryInfo = queryInfo + ",";
				queryInfo = queryInfo + "<i>Pending</i> ";	
				parmResend = parmResend +
				           "&PD=" + 
				            request.getParameter("PD");						
			}
			if (request.getParameter("SH") != null && 
				!request.getParameter("SH").equals("null"))
			{
				status[countStatus] = "SH";
				countStatus++;
				if (countStatus == 1)
				   queryInfo = "Status: ";
				else
				   queryInfo = queryInfo + ",";				
                queryInfo = queryInfo + "<i>Shipped</i> ";
				parmResend = parmResend +
				           "&SH=" + 
				            request.getParameter("SH");	                
			}
			if (request.getParameter("CO") != null && 
				!request.getParameter("CO").equals("null"))
			{
				status[countStatus] = "CO";
				countStatus++;
				if (countStatus == 1)
				   queryInfo = "Status: ";
				else
				   queryInfo = queryInfo + ",";				
                queryInfo = queryInfo + "<i>Complete</i> ";
				parmResend = parmResend +
				           "&CO=" + 
				            request.getParameter("CO");	                
			}

			//from and to sample numbers.
			Integer fromSampleNumber = (Integer) request.getAttribute("fromSample");
				                                        
			if (fromSampleNumber != null)
			{				
				int fromNbr = fromSampleNumber.intValue();

				if (fromNbr != 0)
				{
				    queryInfo = queryInfo + "From SampleNumber: <i>" + 
				                request.getParameter("fromSample") + "</i> ";
				    parmResend = parmResend +
				                "&fromSample=" + 
				                 request.getParameter("fromSample");
				}
			}
			    
			Integer toSampleNumber = (Integer) request.getAttribute(
				                                      "toSample");
			if (toSampleNumber != null)
			{
				int toNbr = toSampleNumber.intValue();
			
				if (toNbr != 999999999)
				{
			 		queryInfo = queryInfo + "To SampleNumber: <i>" + 
			    	            request.getParameter("toSample") + "</i> ";
					parmResend = parmResend +
				                "&toSample=" + 
				                request.getParameter("toSample");		    	            
				}
			}

			//from and to received dates.
			java.sql.Date fromReceivedDate = null;			     
			String frd = (String) request.getAttribute("fromReceivedDate");
				                                        
			if (frd != null)
			{
  			    fromReceivedDate = java.sql.Date.valueOf(frd);
			    queryInfo = queryInfo + "From Received Date: <i>" + 
			                request.getParameter("fromReceivedDate") + "</i> ";
				parmResend = parmResend +
				           "&fromReceivedDate=" + 
				            request.getParameter("fromReceivedDate");			                
			}

			java.sql.Date toReceivedDate = null;
			String trd = (String) request.getAttribute("toReceivedDate");
			
			if (trd != null)
			{
 			    toReceivedDate = java.sql.Date.valueOf(trd);
		 	    queryInfo = queryInfo + "To Received Date: <i>" + 
		 	                request.getParameter("toReceivedDate") + "</i> ";
				parmResend = parmResend +
				           "&toReceivedDate=" + 
				            request.getParameter("toReceivedDate");			 	    
			}

			//from and to ship dates.
			java.sql.Date fromShipDate = null;
			String fsd = (String) request.getAttribute("fromShipDate");
			
			if (fsd != null)
			{
 			    fromShipDate = java.sql.Date.valueOf(fsd);
			    queryInfo = queryInfo + "From Ship Date: <i>" + 
			                request.getParameter("fromShipDate") + "</i> ";
				parmResend = parmResend +
				           "&fromShipDate=" + 
				            request.getParameter("fromShipDate");				                
			}

			java.sql.Date toShipDate = null;
			String tsd = (String) request.getAttribute("toShipDate");
			
			if (tsd != null)
			{
				toShipDate = java.sql.Date.valueOf(tsd);
			    queryInfo = queryInfo + "To Ship Date: <i>" + 
			                request.getParameter("toShipDate") + "</i> ";
				parmResend = parmResend +
				           "&toShipDate=" + 
				            request.getParameter("toShipDate");				                
			}

			//from and to delivery dates.
			java.sql.Date fromDeliveryDate = null;
			String fdd = (String) request.getAttribute("fromDeliveryDate");
			
			if (fdd != null)
			{
 			    fromDeliveryDate = java.sql.Date.valueOf(fdd);
			    queryInfo = queryInfo + "From Delivery Date: <i>" + 
			                request.getParameter("fromDeliveryDate") + "</i> ";
				parmResend = parmResend +
				           "&fromDeliveryDate=" + 
				            request.getParameter("fromDeliveryDate");	
			}

			java.sql.Date toDeliveryDate = null;
			String tdd = (String) request.getAttribute("toDeliveryDate");
			
			if (tdd != null)
			{
				toDeliveryDate = java.sql.Date.valueOf(tdd);
			    queryInfo = queryInfo + "To Delivery Date: <i>" + 
			                request.getParameter("toDeliveryDate") + "</i> ";
				parmResend = parmResend +
				           "&toDeliveryDate=" + 
				            request.getParameter("toDeliveryDate");				                
			}

			//customer name. 
			String customerName = request.getParameter("customerName");
			
			if (customerName != null && customerName.length() != 0)
			{
				queryInfo = queryInfo + "Customer Name: <i>" 
				                      + customerName + "</i> ";
				parmResend = parmResend +
				           "&customerName=" + 
				            request.getParameter("customerName");	
			}
			
  		    //customer number.
			Integer customerNumber = (Integer) request.getAttribute("customerNumber");
				                                        
			if (customerNumber != null)
			{				
				queryInfo = queryInfo + "Customer Number: <i>" + 
          				    request.getParameter("customerNumber") + "</i> ";
				parmResend = parmResend +
				           "&customerNumber=" + 
				            request.getParameter("customerNumber");	            	  
		    }			
			//formula name. 
			String formulaName = request.getParameter("formulaName");
			
			if (formulaName != null && formulaName.length() != 0)
			{
				queryInfo = queryInfo + "Formula Name: <i>" 
				                      + formulaName + "</i> ";
				parmResend = parmResend +
				           "&formulaName=" + 
				            request.getParameter("formulaName");	  				                      
			}

  		    //formula number.
			Integer formulaNumber = (Integer) request.getAttribute("formulaNumber");
				                                        
			if (formulaNumber != null)
			{				
				queryInfo = queryInfo + "Formula Number: <i>" + 
          				    request.getParameter("formulaNumber") + "</i> ";
				parmResend = parmResend +
				           "&formulaNumber=" + 
				            request.getParameter("formulaNumber");	            	  
		    }						                      
			//resource number. 
			String resource = request.getParameter("resource");
			
			if (resource != null && resource.length() != 0)
			{
				queryInfo = queryInfo + "Resource: <i>" 
				                      + resource + "</i> ";
				parmResend = parmResend +
				           "&resource=" + 
				            request.getParameter("resource");	  				                      
			}
				              
			//product description. 
			String  productDescription = request.getParameter("productDescription");
			
			if (productDescription != null && productDescription.length() != 0)
			{
				queryInfo = queryInfo + "Product Description: <i>" 
				                      + productDescription + "</i> ";
				parmResend = parmResend +
				           "&productDescription=" + 
				            request.getParameter("productDescription");				                      
			}

			//broker.
			String broker = request.getParameter("broker");
			
			if (broker != null && broker.length() != 0)
			{
				queryInfo = queryInfo + "Broker: <i>" 
				                   + broker + "</i> ";
				parmResend = parmResend +
				           "&broker=" + 
				            request.getParameter("broker");				                   
			}

			//sample type
			String sampleType = request.getParameter("sampleType");
			
			if (sampleType != null && sampleType.length() != 0 
				                   && !sampleType.equals("None"))
			{
				queryInfo = queryInfo + "Sample Type: <i>" 
				                      + sampleType + "</i> ";
				parmResend = parmResend +
				           "&sampleType=" + 
				            request.getParameter("sampleType");
			}

			//technician. ** Hide - requested Removal 9/29/05 TW Project 7142
//			String technician = request.getParameter("tech");
//			
//			if (technician != null && technician.length() != 0 
//				                   && !technician.equals("None"))
//			{
//				queryInfo = queryInfo + "Technician: <i>" 
//				                      + technician + "</i> ";
//				parmResend = parmResend +
//				           "&tech=" + 
//				            request.getParameter("tech");
//			}

			//application. ** Hide - requested Removal 9/29/05 TW Project 7142
//			String application = request.getParameter("appType");
//			
//			if (application != null && application.length() != 0 
//				                    && !application.equals("None"))
//			{
//				queryInfo = queryInfo + "Application: <i>" 
//				                      + application + "</i> ";
//				parmResend = parmResend +
//				           "&appType=" + 
//				            request.getParameter("appType");
//			}

			//sales representitive.
			String salesRep = request.getParameter("sales");
			
			if (salesRep != null && salesRep.length() != 0 
				                 && !salesRep.equals("None"))
			{
				queryInfo = queryInfo + "Sales Representitive: <i>" 
				                      + salesRep + "</i> ";
				parmResend = parmResend +
				           "&sales=" + 
				            request.getParameter("sales");				                      
			}
			//Lot Number
			String lotNumber = request.getParameter("lotNumber");
			
			if (lotNumber != null && lotNumber.length() != 0)
			{
				queryInfo = queryInfo + "Lot Number: <i>" 
				                      + lotNumber + "</i> ";
				parmResend = parmResend +
				           "&lotNumber=" + 
				            request.getParameter("lotNumber");				                      
			}

			//fruit variety.
			String fruitVariety = request.getParameter("fruitVariety");
			
			if (fruitVariety != null && fruitVariety.length() != 0 
				                     && !fruitVariety.equals("None"))
			{
				queryInfo = queryInfo + "Fruit Variety: <i>" 
				                      + fruitVariety + "</i> ";
				parmResend = parmResend +
				           "&fruitVariety=" + 
				            request.getParameter("fruitVariety");				                      
			}

			//shipped fruit variety.
			String shippedFruitVariety = request.getParameter("shippedFruitVariety");
			
			if (shippedFruitVariety != null && shippedFruitVariety.length() != 0 
				                     && !shippedFruitVariety.equals("None"))
			{
				queryInfo = queryInfo + "Shipped Fruit Variety: <i>" 
				                      + shippedFruitVariety + "</i> ";
				parmResend = parmResend +
				           "&shippedFruitVariety=" + 
				            request.getParameter("shippedFruitVariety");
			}
				                      
			//product group.
			String productGroup = request.getParameter("productGroup");
			
			if (productGroup != null && productGroup.length() != 0 
				                     && !productGroup.equals("None"))
			{
				queryInfo = queryInfo + "Product Group: <i>"
				                      + productGroup + "</i> ";
				parmResend = parmResend +
				           "&productGroup=" + 
				            request.getParameter("productGroup");
			}

			//product type.
			String productType = request.getParameter("productType");
			
			if (productType != null && productType.length() != 0 
				                    && !productType.equals("None"))
			{
				queryInfo = queryInfo + "Product Type: <i>" 
				                      + productType + "</i> ";
				parmResend = parmResend +
				           "&productType=" + 
				            request.getParameter("productType");
			}

			//cut size.
			String cutSize = request.getParameter("cutSize");
			
			if (cutSize != null && cutSize.length() != 0 
				                && !cutSize.equals("None"))
			{
				queryInfo = queryInfo + "Cut Size: <i>" 
				                      + cutSize + "</i> ";
				parmResend = parmResend +
				           "&cutSize=" + 
				            request.getParameter("cutSize");
			}

			//chemical additive.
			String chemicalAdditive = request.getParameter("chemicalAdditive");
			
			if (chemicalAdditive != null && chemicalAdditive.length() != 0 
				                         && !chemicalAdditive.equals("None"))
			{
				queryInfo = queryInfo + "Additive: <i>" 
				                      + chemicalAdditive + "</i> ";
				parmResend = parmResend +
				           "&chemicalAdditive=" + 
				            request.getParameter("chemicalAdditive");
			}
				                      
			//fruit type.
			String fruitType = request.getParameter("fruitType");
			
			if (fruitType != null && fruitType.length() != 0 
				                  && !fruitType.equals("None"))
			{
				queryInfo = queryInfo + "Fruit Type: <i>" 
				                      + fruitType + "</i> ";
				parmResend = parmResend +
				           "&fruitType=" + 
				            request.getParameter("fruitType");
			}

			//color.
			String color = request.getParameter("color");
			
			if (color != null && color.length() != 0 
				                    && !color.equals("None"))
			{
				queryInfo = queryInfo + "Color: <i>" 
				                      + color + "</i> ";
				parmResend = parmResend +
				           "&color=" + 
				            request.getParameter("color");
			}

			//flavor.
			String flavor = request.getParameter("flavor");
			
			if (flavor != null && flavor.length() != 0 
				                    && !flavor.equals("None"))
			{
				queryInfo = queryInfo + "Flavor: <i>" 
				                      + flavor + "</i> ";
				parmResend = parmResend +
				           "&flavor=" + 
				            request.getParameter("flavor");
			}

			//location.
			String location = request.getParameter("location");
			
			if (location != null && location.length() != 0 
				                    && !location.equals("None"))
			{
				queryInfo = queryInfo + "Location: <i>" 
				                      + location + "</i> ";
				parmResend = parmResend +
				           "&location=" + 
				            request.getParameter("location");
			}

			if (queryInfo.length() != 0)
			   queryInfo = "Information Requested: " + queryInfo;
	
			String orderby = request.getParameter("orderby");
			if (orderby == null)
				orderby = "";

			SampleRequestOrder sampleData = new SampleRequestOrder();
			
			listSample = sampleData.findSamples(status,
												fromSampleNumber,
												toSampleNumber,
												fromReceivedDate,
												toReceivedDate,
												fromShipDate,
												toShipDate,
												fromDeliveryDate,
												toDeliveryDate,
												customerName,
												customerNumber,
												formulaName,
												formulaNumber,
												resource,
												productDescription,
												broker,
												sampleType,
//** Hide - requested Removal 9/29/05 TW Project 7142		
												"", //technician,
												"", //application,
												salesRep,
												lotNumber,
												fruitVariety,
												shippedFruitVariety,
												productGroup,
												productType,
												fruitType,
												cutSize,
												chemicalAdditive,
												color,
												flavor,
												location,
												orderby);												   
	}
		
	generalInfo[0] = queryInfo;
	generalInfo[1] = parmResend;
			   
	request.setAttribute("listSamples", listSample);
	request.setAttribute("generalInfo", generalInfo);
	
	// Load view vectors associated from the SampleRequestOrder vector.
	int sampleCount = listSample.size();
	Vector listStatus   = new Vector();
    Vector listType     = new Vector();
    Vector listSalesRep = new Vector();
    Vector listCustomer = new Vector();
    Vector listSecurity = new Vector();

    // retrieve session variables for Sample Request security.
    HttpSession sess = request.getSession(true);			
	String techAuth  = (String) sess.getAttribute("techAuth");
	String salesAuth = (String) sess.getAttribute("salesAuth");
	String recvAuth  = (String) sess.getAttribute("recvAuth");
    
	
	for (int x = 0; x < sampleCount; x++)
	{
		SampleRequestOrder sro = (SampleRequestOrder) listSample.elementAt(x);
		
		// sample type
		String key1Value = "SRC";	
		GeneralInfo type = buildGeneralInfoViewData(sro.getType().trim(),
			                                        key1Value);
		listType.addElement(type);

		// sample status
		key1Value = "SRS";
		GeneralInfo status = buildGeneralInfoViewData(sro.getStatus().trim(),
			                                          key1Value);
		listStatus.addElement(status);

		// sample sales rep.
		SampleRequestUsers salesRep = buildSampleUserViewData("sales", 
			                                                   sro.getSalesRep());
		listSalesRep.addElement(salesRep);

		// sample customer name.
		SampleRequestCustomer customer = buildSampleCustomerViewData(
															sro.getCustNumber());
		listCustomer.addElement(customer);

		// security.
		String access = "no";
		if (sro.getStatus().trim().equals("OP") )
		{
			if (salesAuth.equals("yes") || recvAuth.equals("yes") )
				access = "yes";
		} else
		{
			if (techAuth.equals("yes") )
				access = "yes";
		}
		
		listSecurity.addElement(access);
	}

	if (sampleCount > 249)		msg = msg + "The maximum of 250 list entries has been reached. " +
		            "Only the first 250 selections are presented for display." +
		            "Please use the search values from the preceding page " +
		            "to narrow the list of  entries to be presented.";
		            
    request.setAttribute("listType", listType);
	request.setAttribute("listStatus", listStatus);
	request.setAttribute("listSalesRep", listSalesRep);
	request.setAttribute("listCustomer", listCustomer);
	request.setAttribute("listSecurity", listSecurity);
	
	String template = request.getParameter("template");
	String version  = request.getParameter("version");
	if (version.equals("02"))
	{
	    String headInfo = HTMLHelpers.styleSheetHeadSectionV2() +
          "<title>Report Sample Request</title>";
		
		String builtPage = buildReportHTML(request,
			                               response);
		sess.setAttribute("pageHTML", builtPage);
		sess.setAttribute("headInfo", headInfo);
 		response.sendRedirect("/web/JSP/SampleRequest/reportSample.jsp");
	}
	else
	{ 
	  //***** Go to the List JSP *****//
	  getServletConfig().getServletContext().
			getRequestDispatcher("/JSP/SampleRequest/listSample.jsp?" +
				"msg=" + msg).
			forward(request, response);
	}
	  
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleRequest.pageList: " + e);
	}
	   	    	
}
/**
 * Use to send information to the Add/Update Page.
 * Creation date: (6/20/2003 2:56:40 PM)
 */
private void pageUpd(javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response)  
{
	try
	{
	  //** Bring in Variables.
	  	String[] generalInfo = (String[]) request.getAttribute("generalInfo");
		String msg = (String) request.getAttribute("msg");
		String requestType = (String) request.getAttribute("requestType");


	// attempt to finish the update request
		if (requestType.equals("updateFinish"))
		{
		    updateSample(request, response);
		    msg = (String) request.getAttribute("msg");
		    
		    // 3/26/12 TWalton Added Comments and URL's
		    //   This processes the information relating to those values
		    try {
				CtlKeyValues thisOne = new CtlKeyValues();
				thisOne.performTask(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


	// present the "update" jsp with current sample information. 

		SampleRequestOrder sampleClass = null;
	
		if (requestType.equals("update") ||
			requestType.equals("copy")) // first request prior to any edit.
		{
			// get the sample modififed on the update jsp.
			String requestSample = request.getParameter("sampleNumber");
			Integer sampleNumber = new Integer(requestSample);
			sampleClass = new SampleRequestOrder(sampleNumber);

						// verify authority of current user against order status.
			String authorityPassed = "yes";
			HttpSession sess = request.getSession(true);
			
			String techAuth  = (String) sess.getAttribute("techAuth");
			String salesAuth = (String) sess.getAttribute("salesAuth");
			String recvAuth  = (String) sess.getAttribute("recvAuth");

			if (sampleClass.getStatus().trim().equals("OP") )
			{
				if (salesAuth.equals("no") && recvAuth.equals("no") )
					authorityPassed = "no";
			} else {
				if (techAuth.equals("no") )
					authorityPassed = "no";
			}
			if ((!techAuth.equals("no") ||
				 !salesAuth.equals("no") ||
				 !recvAuth.equals("no")) &&
				  requestType.equals("copy") &&
				  authorityPassed.equals("no"))
			   authorityPassed = "yes";

			if (authorityPassed.equals("no") )
			{
				msg = "Your current authority does not allow access to this " +
				      "order (" + sampleClass.getSampleNumber() + "). " +
				      "The  order status is currently (" + 
				      sampleClass.getStatus() + ")." +
				      " - com.treetop.servlets.CtlSampleRequest.pageUpd()";
				response.sendRedirect("/web/CtlSampleRequest?msg=" + 
				msg);
			    return;
			}
			

            if (requestType.equals("copy"))
            {	
			   int next = SampleRequestOrder.nextSampleNumber();
			   Integer nextNumber = new Integer(next);
			   sampleClass.setSampleNumber(nextNumber);
			   sampleClass.setStatus("OP");
		       //get current date and time for received date and time.
		       String[] dates = SystemDate.getSystemDate();
		       java.sql.Date currentDate = java.sql.Date.valueOf(dates[7]);
		       sampleClass.setReceivedDate(currentDate);
		       java.sql.Time currentTime = java.sql.Time.valueOf(dates[8]);
		       sampleClass.setReceivedTime(currentTime);

		      //get server date and add 10 days for default delivery date.
		       DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //date format
		       Calendar cal = Calendar.getInstance(); //create Calendar class object.
		       cal.setTime(new java.util.Date()); //set Calendar class value.
		       cal.add(Calendar.DATE, 10); //add 10 days to Calendar object value
		       String def = format.format(cal.getTime()); //place Calendar object into
												   //into string using
												   //SimpleDateFormat "format".
 		       java.sql.Date defaultDatePlus = java.sql.Date.valueOf(def);

		       sampleClass.setDeliveryDate(defaultDatePlus);		
		       sampleClass.setShippingDate(java.sql.Date.valueOf("1900-01-01"));	   
            }
          } else {
			// get a new empty sample available for modification.
			if (requestType.equals("add")) // brand new 
			{
				sampleClass = new SampleRequestOrder();
				sampleClass = setUpNewSample(sampleClass, request, response);
			} else {
				sampleClass = (SampleRequestOrder) request.getAttribute("sro");
			}
		}

		//  Check comment field arrays for jsp presentation. Add default array
		// values if necessary for jsp presentation. Currently set at four (4)
		// lines.	
		
		String[] curComment            = sampleClass.getComment();
		java.sql.Date[] curCommentDate = sampleClass.getCommentDate();
		java.sql.Time[] curCommentTime = sampleClass.getCommentTime();
		Integer[] curCommentSeqNumber  = sampleClass.getCommentSeqNumber();
		

		// determine number of comment entries by entries in comment array.
		int count = 0;
		try {
			count = curComment.length;
		} catch(Exception e) {}			
		
		if ((4 - count) != 0)
		{
			String[] outComment            = new String[4];
			java.sql.Date[] outCommentDate = new java.sql.Date[4];
			java.sql.Time[] outCommentTime = new java.sql.Time[4];
			Integer[] outCommentSeqNumber  = new Integer[4];
			
			for (int x = 0; x < 4; x++)
			{
				if (x < count)
				{
					outComment[x]          = curComment[x];
					outCommentDate[x]      = curCommentDate[x];
					outCommentTime[x]      = curCommentTime[x];
					outCommentSeqNumber[x] = curCommentSeqNumber[x];
					
				} else {
					outComment[x]          = new String("");
					outCommentDate[x]      = java.sql.Date.valueOf("1900-01-01");
					outCommentTime[x]      = java.sql.Time.valueOf("00:00:01");
					outCommentSeqNumber[x] = new Integer("0");
				}
			}				
			sampleClass.setComment(outComment);
			sampleClass.setCommentDate(outCommentDate);
			sampleClass.setCommentTime(outCommentTime);
			sampleClass.setCommentSeqNumber(outCommentSeqNumber);
		}


		//  Check remark field arrays for jsp presentation. Add default array
		// values if necessary for jsp presentation. Currently set at four (6)
		// lines.	
		
		String[] curRemark           = sampleClass.getRemark();
		Integer[] curRemarkSeqNumber = sampleClass.getRemarkSeqNumber();

		// determine number of remark entries by entries in remark array.
		count = 0;
		try {
			count = curRemark.length;
		} catch(Exception e) {}			
		
		if ((6 - count) != 0)
		{
			String[] outRemark           = new String[6];
			Integer[] outRemarkSeqNumber = new Integer[6];
			
			for (int x = 0; x < 6; x++)
			{
				if (x < count)
				{
					outRemark[x]          = curRemark[x];
					outRemarkSeqNumber[x] = curRemarkSeqNumber[x];
				} else {
					outRemark[x]          = new String("");
					outRemarkSeqNumber[x] = new Integer("0");
				}
			}				
			sampleClass.setRemark(outRemark);
			sampleClass.setRemarkSeqNumber(outRemarkSeqNumber);
		}
	
		

	// Get all drop down lists.
		generalInfo = buildDropDownLists(generalInfo, 
			                             sampleClass, 
			                             requestType);

	//Get Customer Information.
		SampleRequestCustomer custClass = new SampleRequestCustomer();
		if (!sampleClass.getCustNumber().equals("0"))
		{
			try
			{
				custClass = new SampleRequestCustomer(sampleClass.getCustNumber());
			}
			catch (Exception e)
			{
			//	  System.out.println("problem Loading Customer: " + e);
			}
		}
		request.setAttribute("custClass", custClass);
		
		   // 3/26/12 TWalton 
        // Added Ability to "get" comments
    	if (sampleClass.getSampleNumber().intValue() > 0)
    	{
    		KeyValue kv = new KeyValue();
    		kv.setEnvironment("PRD");
    		kv.setKey1(sampleClass.getSampleNumber().toString().trim());
   			try {
   				kv.setEntryType("SampleComment");
   				sampleClass.setListComments(ServiceKeyValue.buildKeyValueList(kv));
   			}catch(Exception e){
   				sampleClass.setListComments(new Vector());
   			}	  		
    		try {
    			kv.setEntryType("SampleUrl");
    			sampleClass.setListURLs(ServiceKeyValue.buildKeyValueList(kv));
    		}catch(Exception e){
    			sampleClass.setListURLs(new Vector());
    		}	  	
    	}

	
	// Go to the Upd JSP //
		request.setAttribute("requestType", requestType);
		request.setAttribute("generalInfo", generalInfo);
		request.setAttribute("sro", sampleClass);
		getServletConfig().getServletContext().
			getRequestDispatcher("/JSP/SampleRequest/updSample.jsp?" +
				"msg=" + msg 
				).forward(request, response);
		
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleRequest.pageUpd(): " + e);
	} 
	return;	
}
/**
 * Control servlet for Sample Requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 * 
 */
public void performTask(javax.servlet.http.HttpServletRequest request, 
					    javax.servlet.http.HttpServletResponse response) 
{
	try
	{
	    String requestType = request.getParameter("requestType");
 	  		if (requestType == null)
     			requestType = "inq";

    	String urlAddress = "/web/CtlSampleRequest";
       
    	if (requestType.equals("list"))
       		urlAddress = "/web/CtlSampleRequest?requestType=list";
       					 
    	if (requestType.equals("detail") ||
	    	requestType.equals("print") ||
	    	requestType.equals("printremarks"))
       		urlAddress = "/web/CtlSampleRequest?requestType=detail";
       		             
    	if (requestType.equals("add") ||
	    	requestType.equals("update") ||
	    	requestType.equals("copy") ||
	    	requestType.equals("addFinish") ||
	    	requestType.equals("updateFinish") ||
	    	requestType.equals("delete"))
    	{
        	urlAddress = "/web/CtlSampleRequest?requestType=update";
       		//SampleRequestOrder sro = (SampleRequestOrder) request.getAttribute("sro");
       		int x = 1;
    	}
       	 
    	request.setAttribute("requestType", requestType); 
    	
    	 
		//********************************************************************
		// Execute security servlet. 
		//********************************************************************
		// Allow Session Variable Access
		HttpSession sess = request.getSession(true);
		
		// Set the Status
		SessionVariables.setSessionttiSecStatus(request,response,"");
		  
	    // Set the URL address used by the security servlet. 
		SessionVariables.setSessionttiTheURL(request, response, urlAddress);
		
		// Call the security Servlet
  	    getServletConfig().getServletContext().
        getRequestDispatcher("/TTISecurity" ).
	    include(request, response);

	    // Decision of whether or not to use the Inq, List or Detail
	    if (!SessionVariables.getSessionttiSecStatus(request,response).equals(""))
		{
			response.sendRedirect("/web/TreeNetInq?msg=" + 
				SessionVariables.getSessionttiSecStatus(request,response));
			return;
		}
		 
	    // remove the Status and the Url
	    sess.removeAttribute("ttiTheURL");
	    sess.removeAttribute("ttiSecStatus");

	    //*********************************************************************

	    // Set up session variables for servlet authority.
	    accessSecurity(request, response);


	    // Determine the server running this servlet.
	    if (server == null)
	    {
		    String[] serverInfo = ServerInfo.getNameAndPath(request, response);

		    if (serverInfo[2].equals("WKLIB."))
		    	server = "IDE";
		    else
		    	server = "Live";
	    }
	    
		//  Use this Array to send to the Jsp's for anything other than a Vector
		//  Each Jsp may use a different number of array values.
		//  Look in the page methods for details on what the values mean for 
		//  that page.
		String[] generalInfo = new String[400];
		request.setAttribute("generalInfo", generalInfo); 
	     
    	//** Always test for a message, and send it on.
  		String msg = request.getParameter("msg");
    	if (msg == null)
       		msg = "";
    	request.setAttribute("msg", msg); 

    	if (requestType.equals("delete"))
    	{
    	   msg = deleteSample(request, response);
    	   request.setAttribute("msg", msg);
    	}  

    	
    	// execute the servlet method for the incoming request type.	
    	if (requestType.equals("list") ||
	    	requestType.equals("delete"))
 	  		validateParameters(request, response);
		requestType = (String) request.getAttribute("requestType");

		if (requestType.equals("inq"))
			pageInq(request,response);
                
    	if (requestType.equals("detail") ||
	    	requestType.equals("print") ||
	    	requestType.equals("printremarks"))
    		pageDtl(request, response);
                
    	if (requestType.equals("add") || 
	        requestType.equals("update") ||
	        requestType.equals("copy") ||
    		requestType.equals("addFinish") ||              
			requestType.equals("updateFinish"))
    		pageUpd(request, response);

    	if (requestType.equals("defineLabel") ||
	    	requestType.equals("displayLabel"))
    		pageLabel(request, response);   

    	return;
                
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		theException.printStackTrace();
	}
}
/**
 * Use this method prints the sample request document.
 * Creation date: (8/1/2003 10:03:39 AM)
 */
public static String printSampleRequestx(String inNumber) 
{
	  // Not going to use at this time 9/19/08 TWalton
    String msg = "";

    //while (inNumber.length() < 9) {
	  //     inNumber = "0" + inNumber;					
    //}

	//try {
		//// create a AS400 object
		//AS400 as400 = new AS400("10.6.100.1", "DAUSER", "web230502");
		//ProgramCall pgm = new ProgramCall(as400);

		//ProgramParameter[] parmList = new ProgramParameter[1];
		//AS400Text sampleNumber = new AS400Text(9, as400);
		//parmList[0] = new ProgramParameter(sampleNumber.toBytes(inNumber));
		
		//pgm = new ProgramCall(as400, "/QSYS.LIB/IDLIB.LIB/IDC501.PGM", parmList);
		
		//if (pgm.run() == true) 
		//{
			//msg = "Completed Normally";
			//return msg;
		//} 
		//else 
	//	{
		//	msg = "Print Request Not Processed";
			//return msg;
		//}

	//} catch (Exception e) {
		//msg = "Print Request Process Failed";
		return msg; 
	//}		
	
}
/**
 * Insert the method's description here.
 * Creation date: (6/17/2003 10:00:40 AM)
 */
private SampleRequestOrder setUpNewSample(SampleRequestOrder sro,
					 javax.servlet.http.HttpServletRequest request,
					 javax.servlet.http.HttpServletResponse response) 
{
	// Allow Session Variable Access
	HttpSession sess = request.getSession(true);
	String ttiProfile = SessionVariables.getSessionttiProfile(request,response);

	
	try{
		Integer zero = new Integer("0");
		Integer sampleNumber = null;
		
		try {
			sampleNumber = new Integer(request.getParameter("sampleNumber"));
		} catch(Exception e) {
			sampleNumber = new Integer("0");
		}
		
		boolean testValue = zero.equals(sampleNumber);
		
		if (testValue == true)
		{	
			int next = SampleRequestOrder.nextSampleNumber();
			Integer nextNumber = new Integer(next);
			sro.setSampleNumber(nextNumber);
		} else
			sro.setSampleNumber(sampleNumber);

		sro.setStatus("OP");
		sro.setType("");
		sro.setApplication("");
		sro.setSalesRep("");
		sro.setTechnician("");
		sro.setTerritory(new Integer("0"));

		//get current date and time for received date and time.
		String[] dates = SystemDate.getSystemDate();
		java.sql.Date currentDate = java.sql.Date.valueOf(dates[7]);
		sro.setReceivedDate(currentDate);
		java.sql.Time currentTime = java.sql.Time.valueOf(dates[8]);
		sro.setReceivedTime(currentTime);

		
		//get server date and add 10 days for default delivery date.
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //date format
		Calendar cal = Calendar.getInstance(); //create Calendar class object.
		cal.setTime(new java.util.Date()); //set Calendar class value.
		cal.add(Calendar.DATE, 10); //add 10 days to Calendar object value
		String def = format.format(cal.getTime()); //place Calendar object into
												   //into string using
												   //SimpleDateFormat "format".
 		java.sql.Date defaultDatePlus = java.sql.Date.valueOf(def);
 		
 		java.sql.Date defaultDate     = java.sql.Date.valueOf("1900-01-01");

		sro.setDeliveryDate(defaultDatePlus);
		sro.setCustNumber(new Integer("0"));
		sro.setCustContact("");
		sro.setCustContactPhone("");
		sro.setCustContactEmail("");
		sro.setAttnContact("");
		sro.setAttnContactPhone("");
		sro.setAttnContactEmail("");
		sro.setWhoRequested("");
		sro.setWhoReceivedRequest("");
		sro.setShippingCharge("");
		sro.setShipVia("05");
		sro.setShipHow("");
		sro.setTrackingNumber("");
		//sro.setGlAccountNumber("025-120-109-3108");
		sro.setGlAccountNumber("61140-125-109");
		sro.setGlAccountMisc("");
		sro.setShippingDate(defaultDate);
		sro.setEmailWhenShipped1("");
		sro.setEmailWhenShipped2("");
		sro.setEmailWhenShipped3("");
		sro.setEmailWhenShipped4("");
		sro.setEmailWhenShipped5("");
		sro.setCreateDate(currentDate);
		sro.setCreateTime(currentTime);
		sro.setCreateUser(ttiProfile);
		sro.setUpdateDate(defaultDate);
		sro.setUpdateTime(java.sql.Time.valueOf("00:00:01"));
		sro.setUpdateUser("");
		sro.setLocation("");


		// default 3 detail lines.
		BigDecimal[] outQuantity          = new BigDecimal[3];
		BigDecimal[] outContainerSize     = new BigDecimal[3];
		String[] outUnitOfMeasure         = new String[3];
		String[] outProductGroup          = new String[3];
		String[] outProductType           = new String[3];
		String[] outCutSize               = new String[3];
		String[] outColor                 = new String[3];
		String[] outFlavor                = new String[3];
		String[] outChemicalAdditive      = new String[3];
		String[] outFruitVariety          = new String[3];
		String[] outShippedFruitVariety   = new String[3];
		String[] outFruitType             = new String[3];
		String[] outItemDescription       = new String[3];
		String[] outAdditionalDescription = new String[3];
		String[] outPreservative          = new String[3];
		String[] outResource              = new String[3];
		String[] outLotNumber             = new String[3];
		Integer[] outFormulaNumber        = new Integer[3];
		String[] outSpecNumber            = new String[3];
		Integer[] outDtlSeqNumber         = new Integer[3];
		BigDecimal[] outBrixLevel         = new BigDecimal[3];

		// default 3 detail lines.
		for (int x = 0; x < 3; x++)
		{
				
			outQuantity[x]              = new BigDecimal("0");
			outContainerSize[x]         = new BigDecimal("0");
			outUnitOfMeasure[x]         = "";
			outProductGroup[x]          = "";
			outProductType[x]           = "";
			outCutSize[x]               = "";
			outColor[x]                 = "";
			outFlavor[x]                = "";
			outChemicalAdditive[x]      = "";
			outFruitVariety[x]          = "";
			outShippedFruitVariety[x]   = "";
			outFruitType[x]             = "";           
			outItemDescription[x]       = "";
			outAdditionalDescription[x] = "";
			outPreservative[x]          = "";
			outResource[x]              = "";
			outLotNumber[x]             = "";
			outFormulaNumber[x]         = new Integer("0");
			outSpecNumber[x]            = "";
			outDtlSeqNumber[x]          = new Integer("0");
			outBrixLevel[x]             = new BigDecimal("0");
		}
			
		sro.setQuantity(outQuantity);
		sro.setContainerSize(outContainerSize);
		sro.setUnitOfMeasure(outUnitOfMeasure);
		sro.setProductGroup(outProductGroup);
		sro.setProductType(outProductType);
		sro.setCutSize(outCutSize);
		sro.setColor(outColor);
		sro.setFlavor(outFlavor);
		sro.setChemicalAdditive(outChemicalAdditive);
		sro.setFruitVariety(outFruitVariety);
		sro.setShippedFruitVariety(outShippedFruitVariety);
		sro.setFruitType(outFruitType);
		sro.setItemDescription(outItemDescription);
		sro.setAdditionalDescription(outAdditionalDescription);
		sro.setPreservative(outPreservative);
		sro.setResource(outResource);
		sro.setLotNumber(outLotNumber);
		sro.setFormulaNumber(outFormulaNumber);
		sro.setSpecNumber(outSpecNumber);
		sro.setDtlSeqNumber(outDtlSeqNumber);
		sro.setBrixLevel(outBrixLevel);
			

		// default 4 comment lines.
		String[] outComment            = new String[4];
		java.sql.Date[] outCommentDate = new java.sql.Date[4];
		java.sql.Time[] outCommentTime = new java.sql.Time[4];
		Integer[] outCommentSeqNumber  = new Integer[4];
			
		for (int x = 0; x < 4; x++)
		{	
				outComment[x]          = new String("");
				outCommentDate[x]      = java.sql.Date.valueOf("1900-01-01");
				outCommentTime[x]      = java.sql.Time.valueOf("00:00:01");
				outCommentSeqNumber[x] = new Integer("0");
		}				
		sro.setComment(outComment);
		sro.setCommentDate(outCommentDate);
		sro.setCommentTime(outCommentTime);
		sro.setCommentSeqNumber(outCommentSeqNumber);

		// default 6 remark lines.		
		String[] outRemark           = new String[6];
		Integer[] outRemarkSeqNumber = new Integer[6];
			
		for (int x = 0; x < 6; x++)
		{
			outRemark[x]          = new String("");
			outRemarkSeqNumber[x] = new Integer("0");
		}
			
		sro.setRemark(outRemark);
		sro.setRemarkSeqNumber(outRemarkSeqNumber);
	
		return sro;
	} catch(Exception e) {
		
		try{
			String msg = "Unable to add a Sample Request. Please notify " +
		      			 "Information Services. Error(" + e + ")." +
		      			 " - com.treetop.servlets." +
		      			 "CtlSampleRequest.setUpNewSample()";
			getServletConfig().getServletContext().
				getRequestDispatcher("/JSP/SampleRequest/inqSample.jsp?" +
					"msg=" + msg).
				forward(request, response);
		} catch(Exception e2) {
			System.out.println("Error:" + e2 + " - com.treetop.servlets." +
				               "CtlSampleRequest.setUpNewSample()");
		}
	}

	return sro;
}
/**
 * Update/Change a Sample Request using parameters retrieved from a jsp via 
 * the Request Class.
 *
 *  The method variable "msg" will be initialized to "". When a edit from
 * input parameters fails, the "msg" variable will contain the information
 * regarding the edit failure. If no edit failures occur the "msg" variable will
 * contain a simple status message for the user.
 * 
 * Creation date: (6/20/2003 3:59:40 PM)
 */
private void updateSample(javax.servlet.http.HttpServletRequest request,
		  				   javax.servlet.http.HttpServletResponse response)  
{
	try
	{
	  //** Bring in Variables.
		String msg = "";
		String requestType = (String) request.getAttribute("requestType");
		SampleRequestOrder sro = null;
		Integer sampleNumber = null;
		String emailIt = "no";   // set later to yes if all goes well.

		// This variable will contain the status of the order prior to update.
		// If the order status has changed without all edits being passed then
		// order status will be set to its value prior to update. A additional
		// message will be sent to the user, to notify the user of the reset.
		String savedStatus = ""; 

        //verify the sample number and build the sample class to be updated.
        try
        {
	        sampleNumber = new Integer(request.getParameter("sampleNumber"));
        } catch(Exception e)
        {
	        msg = "Error occured on update. \\n" +
	              " The sample reuqest number used (" + 
	              request.getParameter("sampleNumber") + ")" +
	              " is a invalid value.\\n" +
	              " Please contact Information Servicies for assistance.\\n" +
	              " Error:" + e + 
	              " - com.treetop.servlets.CtlSampleRequest.updateSample()";
        }
        
        //if the sample number is valid attempt to create a class.
        if (msg.equals(""))
        {
        	try
        	{
	        	sro = new SampleRequestOrder(sampleNumber);
        	} catch(Exception e)
        	{
	        	try {
		        	sro = new SampleRequestOrder();
		        	sro.setSampleNumber(sampleNumber);
		        	sro = setUpNewSample(sro, request,response);
	        	} catch(Exception e2)
	        	{
		        	msg = "Error occured on update.\\n" +
	        	      " Unable to obtain the sample information for update.\\n" +
	        	      " Please contact Information Servicies for assistance.\\n" +
	              	  " Error:" + e2 + 
	                  " - com.treetop.servlets.CtlSampleRequest.updateSample()";
	        	}
        	}
        }
			
        //if the class is available continue to edit all fields to be updated.
        if (msg.equals(""))
        {
	        // verify authority of current user against order status.
			String authorityPassed = "yes";
			HttpSession sess = request.getSession(true);
			
			String techAuth  = (String) sess.getAttribute("techAuth");
			String salesAuth = (String) sess.getAttribute("salesAuth");
			String recvAuth  = (String) sess.getAttribute("recvAuth");
			if (sro.getStatus().trim().equals("OP") )
			{
				if (salesAuth.equals("no") && recvAuth.equals("no") )
					authorityPassed = "no";
			} else {
				if (techAuth.equals("no") )
					authorityPassed = "no";
			}

			if (authorityPassed.equals("no") )
			{
				msg = "Your current authority does not allow access to this " +
				      "order (" + sro.getSampleNumber() + "). " +
				      "The  order status is currently (" + 
				      sro.getStatus().trim() + ").\\n";
			}
	         
	        //status
	        String status = request.getParameter("status");
	        if (status == null || status.equals("None") || status.equals(""))
		        msg = " A specific STATUS must be selected. \\n";	        	      
        	else
        	{
        		savedStatus = sro.getStatus();
        		sro.setStatus(status);
        	}

        	
	        //sample type.
        	String sampleType = request.getParameter("sampleType");
        	if (sampleType.equals("None") || sampleType == null)
        	{
	           sampleType = "";
               if (sro.getStatus().trim().equals("SH"))
               {
	         	  msg = msg + " A specific SAMPLE TYPE must be selected from the" +
	        	      " available list. Please select one.\\n";
        	   }
                  
        	} 
        	
        	sro.setType(sampleType);
        	

        	//sales rep.
        	String salesRep = request.getParameter("sales");
        	if (salesRep.equals("None") || salesRep == null)
        	{
	        	msg = msg + " A specific SALES REP must be selected from the " +
	        	      "available list. Please select one.\\n";
        	} else {
	        	sro.setSalesRep(salesRep);
        	}

//			** Hide - requested Removal 9/29/05 TW Project 7142
        	//application type.
//        	String appType = request.getParameter("appType");
        	
//        	if (appType.equals("None") || appType == null)
//        		appType = "";
	        	
//	        sro.setApplication(appType);
			sro.setApplication("");
        
//			** Hide - requested Removal 9/29/05 TW Project 7142
        	//tecnnician.
//        	String technician = request.getParameter("tech");
        	
//        	if (technician == null || technician.equals(""))
//        		technician = "";

//       	if (sro.getStatus().trim().equals("SH"))
//        	{
//	        	if (technician.equals("") || technician.equals("None"))
//	        		msg = msg + " A specific TECHNICIAN must be selected from " +
//	        		            "the available list. Please select one.\\n";
//      	}

//	        sro.setTechnician(technician);
			sro.setTechnician("");


	        //location.
	        String location = request.getParameter("location");

	        if (location == null || location.equals("None"))
	        	location = "";

	        if (sro.getStatus().trim().equals("PD"))
	        {
	        	if (location.equals("") || location.equals("None"))
	        		msg = msg + " A specific LOCATION must be selected from " +
	        		            "the available list. Please select one.\\n";
        	}

	        sro.setLocation(location);


	        
        	//territory.
        	try {
        		Integer territory = new Integer(request.getParameter("territory"));
        		sro.setTerritory(territory); 
        	} catch(Exception e)
        	{
	        	msg = msg + "The value entered for TERRITORY (" +
	              	" The territory number entered (" + 
	              	request.getParameter("territory") + ")" +
	              	" is a invalid value." +
	              	" Please enter a 3 digit numeric value.\\n";
        	}

        	
        	//received date.  
        	String receivedDate = request.getParameter("receivedDate");
        	String dateTest = "";
        	try {        		
        		java.sql.Date theDate = java.sql.Date.valueOf(receivedDate); 
        		String x[] = GetDate.getDates(theDate);

        		if (x[0].equals(""))
    				dateTest = "AOK";
    				sro.setReceivedDate(theDate);	
        	} catch(Exception e) {}

        	if (!dateTest.equals("AOK"))
        	{
	        	try {
	        		String x[] = CheckDate.validateDate(receivedDate);

		        	if (x[6].equals(""))
		        	{
			        	sro.setReceivedDate(java.sql.Date.valueOf(x[7]));
	  	      		} else {
		  		   		msg = msg + "The value keyed for Received Date (" +
		    	      		receivedDate + ") is not a valid date value. " +
		       	     		 "Please enter a different value and try again. \\n";
	        		}
	        	} catch(Exception e) {
		        	msg = msg + "The value keyed for Received Date (" +
		    	      	receivedDate + ") is not a valid date value. " +
		       	     	"Please enter a different value and try again. \\n";
	        	}
        	}


			//received time.
			String receivedTime = request.getParameter("receivedTime");
			try {
				java.sql.Time theTime = java.sql.Time.valueOf(receivedTime);
				sro.setReceivedTime(theTime);
			} catch(Exception e) {
				msg = msg + "The value keyed for Received Time (" +
				      receivedTime + ") is not a valid time value. Time " +
				      "is military as example - 12:00:00 is noon (hh:mm:ss). \\n";
			}


			//delivery date.
			String deliveryDate = request.getParameter("deliveryDate");
        	dateTest = "";
        	try {        		
        		java.sql.Date theDate = java.sql.Date.valueOf(deliveryDate); 
        		String x[] = GetDate.getDates(theDate);

        		if (x[0].equals(""))
    				dateTest = "AOK";
    				sro.setDeliveryDate(theDate);	
        	} catch(Exception e) {}

        	if (!dateTest.equals("AOK"))
        	{
	        	try {
	        		String x[] = CheckDate.validateDate(deliveryDate);

		        	if (x[6].equals(""))
		        	{
			        	sro.setDeliveryDate(java.sql.Date.valueOf(x[7]));
	  	      		} else {
		  		   		msg = msg +"The value keyed for Delivery Date (" +
		    	      		deliveryDate + ") is not a valid date value. " +
		       	     		 "Please enter a different value and try again. \\n";
	        		}
	        	} catch(Exception e) {
		        	msg = msg + "The value keyed for Delivery Date (" +
		    	      	deliveryDate + ") is not a valid date value. " +
		       	     	"Please enter a different value and try again. \\n";
	        	}
        	} else {
	        	if (deliveryDate.equals("1900-01-01"))
	        	{
		        	msg = msg + "The current Delivery Date value is of (" +
		        	            deliveryDate + ") is not a proper date. " +
		        	            "please enter a valid value.\\n";
	        	}
        	}


        	//customer number.
        	String customer = request.getParameter("custNumber");
        	if (customer == null || customer.equals("") || customer.equals("None"))
        		customer = "0";
        	try 
        	{
        		Integer custNumber = new Integer(customer);
        		sro.setCustNumber(custNumber);
        	// ONLY tested when going into Pending Status
        	//  Must have a valid customer number.
        		if (sro.getStatus().trim().equals("PD"))
	        	{
		         	try
	   		     	{
		   		     	SampleRequestCustomer testCust = new SampleRequestCustomer(custNumber);
	     		   	}
	       		 	catch(Exception e)
	        		{
           		        msg = msg + "The value entered for Customer Number (" +
	            	  	   customer + ")" +
	             	 	   " is an invalid value." +
	              		   " Please enter a valid Customer number.\\n";		        	
	        		}
	        	}
 	 	  
        	} catch(Exception e)
        	{
	        	msg = msg + "The value entered for Customer Number (" +
	              	customer + ")" +
	              	" is not a invalid number." +
	              	" Please enter a numeric value.\\n";
        	}
        	
        	//customer contact name.
        	String customerContact = request.getParameter("customerContact");
        	
        	if (customerContact == null)
        		customerContact = "";        		
        	sro.setCustContact(customerContact);


        	//customer contact phone.
        	String contactPhone = request.getParameter("contactPhone");
        	
        	if (contactPhone == null)
        		contactPhone = "";        		
        	sro.setCustContactPhone(contactPhone);


        	//customer contact email.
        	String contactEmail = request.getParameter("contactEmail");
        	
        	if (contactEmail == null)
        		contactEmail = "";       		
        	sro.setCustContactEmail(contactEmail);
        	

        	//attention contact name.
        	String attnContact = request.getParameter("attnContact");
        	
        	if (attnContact == null)
        		attnContact = "";        		
        	sro.setAttnContact(attnContact);


        	//attention contact phone.
        	String attnPhone = request.getParameter("attnPhone");
        	
        	if (attnPhone == null)
        		attnPhone = "";        		
        	sro.setAttnContactPhone(attnPhone);


        	//attention contact email.
        	String attnEmail = request.getParameter("attnEmail");
        	
        	if (attnEmail == null)
        		attnEmail = "";       		
        	sro.setAttnContactEmail(attnEmail);


        	//who requested.
        	String requestedBy = request.getParameter("requestedBy");

        	if (requestedBy == null)
        		requestedBy = "";
        	sro.setWhoRequested(requestedBy);


        	//who received request.
        	String receivedBy = request.getParameter("received");
        	if (receivedBy.equals("") || receivedBy == null || receivedBy.equals("None"))
        	{
	        	msg = msg + " A specific SAMPLE REQUEST RECEIVED BY must" +
	        	            "be selected from the " +
	        			    "available list. Please select one.\\n";
        	} else {
	        	sro.setWhoReceivedRequest(receivedBy);
        	}

        	
        	//shipping charge.
        	String shipCharge = request.getParameter("shipCharge");

        	if (shipCharge == null)
        		shipCharge = "";
        	sro.setShippingCharge(shipCharge);


        	//ship via. ONLY tested when going into Ship Status
        	String shipVia = request.getParameter("shipVia");

        	if (shipVia == null || shipVia.equals("None") )
        		shipVia = "";
        		 
        	if (sro.getStatus().trim().equals("SH"))
        	{ 	
 	 	      	if (shipVia.equals("") )
    	    	{
	    	    	msg = msg + " A specific SHIP VIA must be selected from " +
	     		   	            "the available list. Please select one.\\n";
        		} else {
	        		sro.setShipVia(shipVia);
	        	}
        	} else
        		sro.setShipVia(shipVia);
        	
        	//tracking number. ONLY tested when going into Ship Status
	        String trackingNumber = request.getParameter("trackingNumber");
        	
 	  	     if (trackingNumber == null)
  		    	trackingNumber = "";       		
     		 sro.setTrackingNumber(trackingNumber);
        	 
        		
        	//ship how. ONLY tested when going into Ship Status
        	String shipHow = request.getParameter("shipHow");

        	if (shipHow == null || shipHow.equals("None") )
        		shipHow = "";
        		
        	if (sro.getStatus().trim().equals("SH"))
        	{       	
	       		if (shipHow.equals("") )
        		{
	        		msg = msg + " A specific SHIP HOW must be selected from " +
	        		            "the available list. Please select one.\\n";
        		} else {        		
		        	sro.setShipHow(shipHow);
        		}
        	} else
        		sro.setShipHow(shipHow);
        	
        	//gl account number.
        	String acctNumber = request.getParameter("acctNumber");
        	
        	if (acctNumber == null)
        		acctNumber = "";       		
        	sro.setGlAccountNumber(acctNumber);


        	//gl account misc component.
        	String acctMisc = request.getParameter("acctMisc");
        	
        	if (acctMisc == null)
        		acctMisc = "";
        	sro.setGlAccountMisc(acctMisc);
        		
        	//  If the sample order status is not empty and not equal to "OP"
        	// then verify the misc component is 5 long and numeric.	
        	if (status.trim().equals("PD") && 
	        	savedStatus.trim().equals("OP") )
        	{
	        	try
	        	{
		        	Integer y = new Integer(acctMisc.trim());
					int x = acctMisc.trim().length();
		     //   	if (x != 5)
			//		{
			//			msg = msg + "The value keyed for G/L Account " +
			//			            "Miscellaneous component (" +
		   	//	 	      			acctMisc + ") must be 5 long. " +
		    // 	  	     		 	"Please enter a valid value and try again. \\n ";
			//		}
	        	} catch(Exception e)
	        	{
		        //	msg = msg + "The value keyed for G/L Account Miscellaneous (" +
		        //				acctMisc + ") is NOT A VALID NUMERIC value. " +
		        //				"Please enter a valid value and try again. \\n";
	        	}
	        	
				
        	}


        	//ship date.
        	//  Set ship date to todays date if current value is default and 
        	// order status is changing from OPEN to PENDING.
        	String shipDate = request.getParameter("shipDate");
        	 
        	if (status.trim().equals("PD") && 
	        	savedStatus.trim().equals("OP"))
        	{
	        	String[] td = SystemDate.getSystemDate();
	        	shipDate = td[7];
	        	
				//get server date and add 10 days for default delivery date.
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //date format
				Calendar cal = Calendar.getInstance(); //create Calendar class object.
				cal.setTime(new java.util.Date()); //set Calendar class value.
				cal.add(Calendar.DATE, 10); //add 10 days to Calendar object value
				shipDate = format.format(cal.getTime()); //place Calendar object into
														   //into string using
														   //SimpleDateFormat "format".
	        	java.sql.Date thisDate = java.sql.Date.valueOf(shipDate);
	        	sro.setShippingDate(thisDate);
	        }
			//ONLY tested when going into Ship Status
        	//if (sro.getStatus().trim().equals("SH"))  wth 06/08/2011
        	//{											wth 06/08/2011
	        	
 		       	dateTest = "";
   		     	try {        		
     		   		java.sql.Date theDate = java.sql.Date.valueOf(shipDate); 
       		 		String x[] = GetDate.getDates(theDate);

		       		if (x[0].equals(""))
  		  				dateTest = "AOK";
    					sro.setShippingDate(theDate);	
     			} catch(Exception e) {}

	        	if (!dateTest.equals("AOK"))
 		       	{
	  		      	try {
	    		   		String x[] = CheckDate.validateDate(shipDate);

			        	if (x[6].equals(""))
			        	{
				        	sro.setShippingDate(java.sql.Date.valueOf(x[7]));
	  		      		} else {
		  			   		msg = msg + "The value keyed for SHIP DATE (" +
		   		 	      		shipDate + ") is not a valid date value. " +
		     	  	     		 "Please enter a different value and try again. \\n";
	       		 		}
	        		} catch(Exception e) {
		        		msg = msg + "The value keyed for SHIP DATE (" +
		    	     	 	shipDate + ") is not a valid date value. " +
		       	   		  	"Please enter a different value and try again. \\n";
		        	}
 		       	} else {
	  		      	if ( (shipDate.equals("1900-01-01") && 
		   		     	  sro.getStatus().trim().equals("SH")) ||
	      		  		 (shipDate.equals("1900-01-01") &&
		       		 	  sro.getStatus().trim().equals("CO")) )
		        	{
			        	msg = msg + "The current Ship Date value is of (" +
			       	            shipDate + ") is not a proper date. " +
		        	            "please enter a valid value.\\n";
	  		      	}
     		   	}
        	//}	wth 06/08/2011

        	//email when shipped (1-5).
        	String addEmail1 = request.getParameter("addEmail1");
        	String addEmail2 = request.getParameter("addEmail2");
        	String addEmail3 = request.getParameter("addEmail3");
        	String addEmail4 = request.getParameter("addEmail4");
        	String addEmail5 = request.getParameter("addEmail5");
        	
        	if (addEmail1 == null)
        		addEmail1 = "";
        	if (addEmail2 == null)
        		addEmail2 ="";
        	if (addEmail3 == null)
        		addEmail3 = "";
        	if (addEmail4 == null)
        		addEmail4 = "";
        	if (addEmail5 == null)
        		addEmail5 = "";
        			
        	sro.setEmailWhenShipped1(addEmail1);
        	sro.setEmailWhenShipped2(addEmail2);
        	sro.setEmailWhenShipped3(addEmail3);
        	sro.setEmailWhenShipped4(addEmail4);
        	sro.setEmailWhenShipped5(addEmail5); 
        	
        	//create date, time, and user.
        	//only set them if not already set in prior areas.
        	if (sro.getCreateUser() == null)
        	{
	        	String createUser = request.getParameter("createUser");
	        	String createDate = request.getParameter("createDate");
	        	String createTime = request.getParameter("createTime");
	        	
	        	if (createUser == null)
	        		createUser = "";
	        		createDate = "1900-01-01";
	        		createTime = "00:00:01";
	        	
	        	sro.setCreateUser(createUser);
	        	java.sql.Date theDate = java.sql.Date.valueOf(createDate);
				sro.setCreateDate(theDate);
				java.sql.Time theTime = java.sql.Time.valueOf(createTime);
				sro.setUpdateTime(theTime);
        	}	
        	
			//update date and time														  response);
			//get current date and time for received date and time.
			String[] dates = SystemDate.getSystemDate();
			java.sql.Date currentDate = java.sql.Date.valueOf(dates[7]);
			sro.setUpdateDate(currentDate);
			java.sql.Time currentTime = java.sql.Time.valueOf(dates[8]);
			sro.setUpdateTime(currentTime);

			//update user
			String ttiProfile = SessionVariables.getSessionttiProfile(request,
																	  response);
			sro.setUpdateUser(ttiProfile);

			//View Lot Numbers
	        String viewLot = request.getParameter("viewLot");

	        if (viewLot == null)
	        {
		        if (sro.getStatus().equals("PD") ||
			        sro.getStatus().equals("OP"))
		           viewLot = "N";
		        else
		           viewLot = sro.getViewLot().trim();
	        }
            else
	          viewLot = "Y";
	            
	        sro.setViewLot(viewLot);

			//View Fruit Variety
	        String viewVariety = request.getParameter("viewVariety");

	        if (viewVariety == null)
	        {
		        if (sro.getStatus().equals("PD") ||
			        sro.getStatus().equals("OP"))
		           viewVariety = "N";
		        else
		           viewVariety = sro.getViewVariety().trim();
	        }
            else
	          viewVariety = "Y";
	            
	        sro.setViewVariety(viewVariety);	        
	           
   			//CHECK BOX LINES

   			//Get list of all possible check boxes.
			GeneralInfo descInfo  = new GeneralInfo();
			Vector typeList       = new Vector();
			typeList              = GeneralInfo.findDescByFull("SRF");
			int howMany = typeList.size();

			//Set up Arrays for Information
			String[]  descKey1           = new String[howMany];
			String[]  keyCode1           = new String[howMany];
			String[]  keyValue1          = new String[howMany];
			String[]  checkBoxValue81    = new String[howMany];
			String[]  checkBoxValue201   = new String[howMany];
			Integer[] checkBoxSeqNumber1 = new Integer[howMany];
			int newDocCount = 0;
			
			for (int x = 0; x < howMany; x++)
			{
				descInfo = (GeneralInfo) typeList.elementAt(x);
				String key1 = descInfo.getKey1Value();

				if (request.getParameter(key1) != null && 
					!request.getParameter(key1).trim().equals(""))
				{
					descKey1[newDocCount]           = descInfo.getDescType();
					keyCode1[newDocCount]           = descInfo.getKey1Value();
					keyValue1[newDocCount]          = descInfo.getDescSmall();
					checkBoxValue81[newDocCount]    = descInfo.getDescShort();
					checkBoxValue201[newDocCount]   = descInfo.getDescFull();
					checkBoxSeqNumber1[newDocCount] = new Integer(newDocCount + 1);
					newDocCount = newDocCount + 1;
				}
			}
			//Set up Arrays To Transfer Information to Class
			String[]  descKey           = new String[newDocCount];
			String[]  keyCode           = new String[newDocCount];
			String[]  keyValue          = new String[newDocCount];
			String[]  checkBoxValue8    = new String[newDocCount];
			String[]  checkBoxValue20   = new String[newDocCount];
			Integer[] checkBoxSeqNumber = new Integer[newDocCount];

			for (int x = 0; x < newDocCount; x++)
			{
				descKey[x]	         = descKey1[x];
				keyCode[x]           = keyCode1[x];
				keyValue[x]          = keyValue1[x];
				checkBoxValue8[x]    = checkBoxValue81[x];
				checkBoxValue20[x]   = checkBoxValue201[x];
				checkBoxSeqNumber[x] = checkBoxSeqNumber1[x];
			}
			// set into the Class
			sro.setDescKey(descKey);
			sro.setKeyCode(keyCode);
			sro.setKeyValue(keyValue);
			sro.setCheckBoxValue8(checkBoxValue8);
			sro.setCheckBoxValue20(checkBoxValue20);
			sro.setCheckBoxSeqNumber(checkBoxSeqNumber);
				
        	//DETAIL LINES.

        	// comparision fields.
        	String testString = "";
        	BigDecimal zero = new BigDecimal("0");
        	Integer zeroInteger = new Integer("0");

        	
			// current detail lines.
			testString = request.getParameter("currentLines");

			if (testString == null || testString.equals(""))
				testString = "0";
				
 			BigDecimal holdBDx = new BigDecimal("0");		
 						
 			try {
	 			holdBDx = new BigDecimal(testString);
 			} catch(Exception e) {
	 			testString = "0";
 			}
 						
 			int currentLines = Integer.parseInt(testString);

 			//determine total number of detail lines required for jsp display.
			testString = request.getParameter("additionalLines");
			
			if (testString == null || testString.equals(""))
				testString = "0";
				
 			holdBDx = new BigDecimal("0");		
 						
 			try {
	 			holdBDx = new BigDecimal(testString);
 			} catch(Exception e) {
	 			testString = "0";
 			}
 						
 			int additionalLines = Integer.parseInt(testString);

			int totalLines = currentLines + additionalLines;

			
        	//  lineCount: total number of detail lines entered against. 
        	int lineCount = 0;

        	//  useThis: determines if the detail entry is entered against.
        	//  useThis = "yes" if a error occurs on input 
			//  useThis = "yes" if a valid non blank/zero entry occurs.
			String[] useThis = new String[totalLines];
			
        	for (int x = 0; x < totalLines; x++) 
        	{
	        	useThis[x] = "no";
        	}
        	
			BigDecimal[] quantity          = new BigDecimal[totalLines];
			BigDecimal[] containerSize     = new BigDecimal[totalLines];
			String[] unitOfMeasure         = new String[totalLines];
			String[] productGroup          = new String[totalLines];
			String[] productType           = new String[totalLines];
			String[] cutSize               = new String[totalLines];
			String[] color                 = new String[totalLines];
			String[] flavor                = new String[totalLines];
			String[] chemicalAdditive      = new String[totalLines];
			String[] fruitType             = new String[totalLines];
			String[] fruitVariety          = new String[totalLines];
			String[] shippedFruitVariety   = new String[totalLines];
			String[] itemDescription       = new String[totalLines];
			String[] additionalDescription = new String[totalLines];
			String[] preservative          = new String[totalLines];
			String[] resource              = new String[totalLines];
			String[] lotNumber             = new String[totalLines];
			Integer[] formulaNumber        = new Integer[totalLines];
			String[] specNumber            = new String[totalLines];
			Integer[] dtlSeqNumber         = new Integer[totalLines];
			BigDecimal[] brixLevel         = new BigDecimal[totalLines];	

			//all detail lines.
			for (int x=1; x < (totalLines + 1); x++)
			{
				//see if the user wants this detail line deleted.
				String testDeleteDetail = request.getParameter(x + "deleteline");
				if (testDeleteDetail == null)
					testDeleteDetail = "false";
				else
					testDeleteDetail = "true";

				if (testDeleteDetail.equals("false"))
				{
					// detail quantity.
					testString = request.getParameter(x + "quantity");
				
 					if (testString != null &&
	 			   		!testString.equals(""))
 					{
 						BigDecimal holdBD = new BigDecimal("0");
 						try {
	 						holdBD = new BigDecimal(testString);
 						} catch(Exception e) {
	 						holdBD = new BigDecimal("0");
	 						msg = msg + " The Quantity value of (" + 
	 						            testString + ") for sequence " + x +
	 						            " is invalid. Please enter a valid " +
	 						            "numeric value. \\n";
	 						useThis[x-1] = "yes";
 						}
 						
 						quantity[x-1] = holdBD;
 						boolean testValue = holdBD.equals(zero);

 						// If not zero then use this detail line.
 						if (testValue == false)
 							useThis[x-1] = "yes";
 						
 					} else {
	 					quantity[x-1] = new BigDecimal("0");
 					}

 					// detail container size.
 					testString = request.getParameter(x + "containerSize");
				
 					if (testString != null &&
	 			   		!testString.equals(""))
 					{
 						BigDecimal holdBD = new BigDecimal("0");
 						try {
	 						holdBD = new BigDecimal(testString);
 						} catch(Exception e) {
	 						holdBD = new BigDecimal("0");
	 						msg = msg + " The Container Size value of (" + 
	 						            testString + ") for sequence " + x +
	 						            " is invalid. Please enter a valid " +
	 						            "numeric value. \\n";
	 						useThis[x-1] = "yes";
 						}
 						
 						containerSize[x-1] = holdBD;
 						boolean testValue = holdBD.equals(zero);

 						// If not zero then use this detail line.
 						if (testValue == false)
 							useThis[x-1] = "yes";
 						
 					} else {
	 					containerSize[x-1] = new BigDecimal("0");
 					}

 					// detail unit of measure.
 					testString = request.getParameter(x + "uom");
				
 					if (testString == null ||
	 			   		testString.equals("") || testString.equals("None"))
 					{
	 					unitOfMeasure[x-1] = "";
 						boolean testValue = quantity[x-1].equals(zero);

 						if (testValue == false && testString.equals("None"))
 						{
	 						msg = msg + " A unit of measure must be selected" +
	 						            " if a quantity is entered" + 
	 						            " for sequence " + x + ".\\n";
	 						useThis[x-1] = "yes";
 						}
 						
 					} else {
	 					useThis[x-1] = "yes";
	 					unitOfMeasure[x-1] = testString;
 					}


 					// detail product group.
 					testString = request.getParameter(x + "productGroup");

 					if (testString == null ||
	 					testString.equals("") || testString.equals("None"))
 					{
	 					productGroup[x-1] = "";
						boolean testValue = quantity[x-1].equals(zero);

						if (testValue == false && testString.equals("None"))
						{
							msg = msg + " A Product Group must be selected" +
										" if a quantity is entered" + 
										" for sequence " + x + ".\\n";
							useThis[x-1] = "yes";
						}
 					} else {
	 					useThis[x-1] = "yes";
	 					productGroup[x-1] = testString;
 					}

 					// detail product type.
 					testString = request.getParameter(x + "productType");

 					if (testString == null ||
	 					testString.equals("") || testString.equals("None"))
 					{
	 					productType[x-1] = "";
	 					
						boolean testValue = quantity[x-1].equals(zero);

						if (testValue == false && testString.equals("None"))
						{
							msg = msg + " A Product Type must be selected" +
										" if a quantity is entered" + 
										" for sequence " + x + ".\\n";
							useThis[x-1] = "yes";
						}	 					
 					} else {
	 					useThis[x-1] = "yes";
	 					productType[x-1] = testString;
 					}

 					// detail cut size.
 					testString = request.getParameter(x + "cutSize");

 					if (testString == null ||
	 					testString.equals("") || testString.equals("None"))
 					{
	 					cutSize[x-1] = "";
						boolean testValue = quantity[x-1].equals(zero);

						if (testValue == false && testString.equals("None"))
						{
							msg = msg + " A Cut Size must be selected" +
										" if a quantity is entered" + 
										" for sequence " + x + ".\\n";
							useThis[x-1] = "yes";
						}	 					
 					} else {
	 					useThis[x-1] = "yes";
	 					cutSize[x-1] = testString;
 					}


 					// detail color.
 					testString = request.getParameter(x + "color");

 					if (testString == null ||
	 					testString.equals("") || testString.equals("None"))
 					{
	 					color[x-1] = "";
 					} else {
	 					useThis[x-1] = "yes";
	 					color[x-1] = testString;
 					}

 					// detail flavor.
 					testString = request.getParameter(x + "flavor");

 					if (testString == null ||
	 					testString.equals("") || testString.equals("None"))
 					{
	 					flavor[x-1] = "";
 					} else {
	 					useThis[x-1] = "yes";
	 					flavor[x-1] = testString;
 					}

 					// chemical additive.
 					testString = request.getParameter(x + "chemicalAdditive");

 					if (testString == null ||
	 					testString.equals("") || testString.equals("None"))
 					{
	 					chemicalAdditive[x-1] = "";
						boolean testValue = quantity[x-1].equals(zero);

						if (testValue == false && testString.equals("None"))
						{
							msg = msg + " An Additive must be selected" +
										" if a quantity is entered" + 
										" for sequence " + x + ".\\n";
							useThis[x-1] = "yes";
						}	 					
 					} else {
	 					useThis[x-1] = "yes";
	 					chemicalAdditive[x-1] = testString;
 					}

 					// fruit type.
 					testString = request.getParameter(x + "fruitType");

 					if (testString == null ||
	 					testString.equals("") || testString.equals("None"))
 					{
	 					fruitType[x-1] = "";
						boolean testValue = quantity[x-1].equals(zero);

						if (testValue == false && testString.equals("None"))
						{
							msg = msg + " A Fruit Type must be selected" +
										" if a quantity is entered" + 
										" for sequence " + x + ".\\n";
							useThis[x-1] = "yes";
						}	 					
 					} else {
	 				//	useThis[x-1] = "yes"; // Defaulted in, do not use to decide if it is
	 				//      a valid record.
	 					fruitType[x-1] = testString;
 					}

 					// detail fruit variety.
 					testString = request.getParameter(x + "fruitVariety");

 					if (testString == null ||
	 					testString.equals("") || testString.equals("None"))
 					{
	 					fruitVariety[x-1] = "";
 					} else {
	 					useThis[x-1] = "yes";
	 					fruitVariety[x-1] = testString;
 					}

 					// detail shipped fruit variety.
 					testString = request.getParameter(x + "shippedFruitVariety");

 					if (testString == null ||
	 					testString.equals("") || testString.equals("None"))
 					{
	 					shippedFruitVariety[x-1] = "";
 					} else {
	 					useThis[x-1] = "yes";
	 					shippedFruitVariety[x-1] = testString;
 					}

 					
 					// detail item description.
 					testString = request.getParameter(x + "itemDescription");

 					if (testString == null || testString.trim().equals(""))
	 					testString = "";
 					else
	 					useThis[x-1] = "yes";

 					itemDescription[x-1] = testString.trim();

 					
 					// detail additional description.
 					testString = request.getParameter(x + "addDescription");

 					if (testString == null || testString.trim().equals(""))
	 					testString = "";
 					else
	 					useThis[x-1] = "yes";
	 					
	 				additionalDescription[x-1] = testString.trim();

	 				
 					// detail preservative.
 					testString = request.getParameter(x + "preservative");

 					if (testString == null || testString.trim().equals(""))
	 					testString = "";
 					else
	 					useThis[x-1] = "yes";
	 					
	 				preservative[x-1] = testString.trim();


 					// detail resource.
	 				// new Box (m3 Movex) version is item
 					testString = request.getParameter(x + "resource");

 					if (testString == null || testString.trim().equals(""))
	 					testString = "";
 					else
 					{
	 					try 
	 					{
	 						if (itemDescription[x-1].trim().length() == 0)
	 						{
//	 							Resource testResource = new Resource(testString);
	 							BeanItem testItem = ServiceItem.buildNewItem("PRD", testString);
	 							itemDescription[x-1] = testItem.getItemClass().getItemDescription();
	 						   //itemDescription[x-1] = testResource.getResourceDescription();
	 						}
	 						if (itemDescription[x-1].trim().length() == 0)
	 						{
	 						   BeanItem bi = ServiceItem.buildNewItem("PRD", testString);
	 						   if (bi.getItemClass() != null &&
	 						   	   bi.getItemClass().getItemDescription() != null)
	 						     itemDescription[x-1] = bi.getItemClass().getItemDescription().trim();
	 						}
 						} 
	 					catch(Exception e) 
 						{
	 						msg = msg + " The Item Number value of (" + 
	 						            testString + ") for sequence " + x +
	 						            " is invalid. Please enter a valid " +
	 						            "Movex Item Number. \\n";
 						}
	 					useThis[x-1] = "yes";
 					}
	 					
	 				resource[x-1] = testString.trim();
 					

 					// detail lot number.
 					testString = request.getParameter(x + "lotNumber");

 					if (testString == null || testString.trim().equals(""))
	 					testString = "";
                    else
	                    useThis[x-1] = "yes";	
	                    	
	 				lotNumber[x-1] = testString.trim();
 					

 					// detail formula Number.
 					testString = request.getParameter(x + "formula");
				
 					if (testString != null && !testString.equals("")
	 					&& !testString.equals("0") 
	 					&& !testString.equals("None"))	 			   		
 					{
 						Integer holdInteger = new Integer("0");
 						try {
	 						holdInteger = new Integer(testString);
 						} catch(Exception e) {
	 						holdInteger = new Integer("0");
	 						msg = msg + " The Formula Number value of (" + 
	 						            testString + ") for sequence " + x +
	 						            " is invalid. Please enter a valid " +
	 						            "numeric Formula Number. \\n";
	 						useThis[x-1] = "yes";
 						}
 						
 						formulaNumber[x-1] = holdInteger;
 						boolean testValue = holdInteger.equals(zeroInteger);

 						// If not zero then use this detail line.
 						if (testValue == false)
 							useThis[x-1] = "yes";
 						
 					} else {
	 					formulaNumber[x-1] = new Integer("0");
 					}


 					// detail brix level.
 					testString = request.getParameter(x + "brixLevel");
				
 					if (testString != null && !testString.equals("")
	 					&& !testString.equals("0") 
	 					&& !testString.equals("None"))	 			   		
 					{
 						BigDecimal holdBD = new BigDecimal("0");
 						try {
	 						holdBD = new BigDecimal(testString);
 						} catch(Exception e) {
	 						holdBD = new BigDecimal("0");
	 						msg = msg + " The Brix Level value of (" + 
	 						            testString + ") for sequence " + x +
	 						            " is invalid. Please enter a valid " +
	 						            "numeric Brix Level. \\n";
	 						useThis[x-1] = "yes";
 						}
 						
 						brixLevel[x-1] = holdBD;
 						boolean testValue = holdBD.equals(zero);

 						// If not zero then use this detail line.
 						if (testValue == false)
 							useThis[x-1] = "yes";
 						
 					} else {
	 					brixLevel[x-1] = new BigDecimal("0");
 					}

 					// detail spec number.
 					testString = request.getParameter(x + "spec");

 					if (testString == null || testString.trim().equals(""))
	 					testString = "";
 					else 
	 					useThis[x-1] = "yes";
	 					
	 				specNumber[x-1] = testString.trim();
 	

	 				// test for additional requested lines.
	 				if (x > currentLines)
	 					useThis[x-1] = "yes";

	 				
	 				// increment lineCount and load detail sequence number.
					if (useThis[x-1].equals("yes"))
					{
						lineCount = lineCount + 1;
						Integer dtlSeq = new Integer(lineCount);
						dtlSeqNumber[x-1] = dtlSeq;
					}						

				}
			}


			//set SampleRequestOrder Detail fields.
					
			BigDecimal[] outQuantity          = new BigDecimal[lineCount];
			BigDecimal[] outContainerSize     = new BigDecimal[lineCount];
			String[] outUnitOfMeasure         = new String[lineCount];
			String[] outProductGroup          = new String[lineCount];
			String[] outProductType           = new String[lineCount];
			String[] outCutSize               = new String[lineCount];
			String[] outColor                 = new String[lineCount];
			String[] outFlavor                = new String[lineCount];
			String[] outChemicalAdditive      = new String[lineCount];
			String[] outFruitType             = new String[lineCount];
			String[] outFruitVariety          = new String[lineCount];
			String[] outShippedFruitVariety   = new String[lineCount];
			String[] outItemDescription       = new String[lineCount];
			String[] outAdditionalDescription = new String[lineCount];
			String[] outPreservative          = new String[lineCount];
			String[] outResource              = new String[lineCount];
			String[] outLotNumber             = new String[lineCount];
			Integer[] outFormulaNumber        = new Integer[lineCount];
			BigDecimal[] outBrixLevel         = new BigDecimal[lineCount];
			String[] outSpecNumber            = new String[lineCount];
			Integer[] outDtlSeqNumber         = new Integer[lineCount];
			
			int y = 0;
			 		
			for (int x = 0; x < totalLines; x++)
			{
				if (useThis[x].equals("yes"))
				{
					outQuantity[y]              = quantity[x];
					outContainerSize[y]         = containerSize[x];
					outUnitOfMeasure[y]         = unitOfMeasure[x];
					outProductGroup[y]          = productGroup[x];
					outProductType[y]           = productType[x];
					outCutSize[y]               = cutSize[x];
					outColor[y]                 = color[x];
					outFlavor[y]                = flavor[x];
					outChemicalAdditive[y]      = chemicalAdditive[x];
					outFruitType[y]             = fruitType[x];
					outFruitVariety[y]          = fruitVariety[x];
					outShippedFruitVariety[y]   = shippedFruitVariety[x];
					outItemDescription[y]       = itemDescription[x];
					outAdditionalDescription[y] = additionalDescription[x];
					outPreservative[y]          = preservative[x];
					outResource[y]              = resource[x];
					outLotNumber[y]             = lotNumber[x];
					outFormulaNumber[y]         = formulaNumber[x];
					outBrixLevel[y]             = brixLevel[x];
					outSpecNumber[y]            = specNumber[x];
					outDtlSeqNumber[y]          = dtlSeqNumber[x];     
					y++;
				}
			}
					
			sro.setQuantity(outQuantity); 
			sro.setContainerSize(outContainerSize);
			sro.setUnitOfMeasure(outUnitOfMeasure);
			sro.setProductGroup(outProductGroup);
			sro.setProductType(outProductType);
			sro.setCutSize(outCutSize);
			sro.setColor(outColor);
			sro.setFlavor(outFlavor);
			sro.setChemicalAdditive(outChemicalAdditive);
			sro.setFruitType(outFruitType);
			sro.setFruitVariety(outFruitVariety);
			sro.setShippedFruitVariety(outShippedFruitVariety);
			sro.setItemDescription(outItemDescription);
			sro.setAdditionalDescription(outAdditionalDescription);
			sro.setPreservative(outPreservative);
			sro.setResource(outResource);
			sro.setLotNumber(outLotNumber);
			sro.setFormulaNumber(outFormulaNumber);
			sro.setBrixLevel(outBrixLevel);
			sro.setSpecNumber(outSpecNumber);
			sro.setDtlSeqNumber(outDtlSeqNumber);  						
			 
			
			//COMMENT LINES

			// compairision fields.
			testString = "";

			// linecount: total number of comment lines entered against.
			lineCount = 0;

			// useThisComment: determines if the detail entry is entered against.
			// useThisComment = "yes" if a error occurs on input.
			// useThisComment = "yes" if a valid non blank/zero entry occurs.
			String[] useThisComment = new String[4]; 
			for (int x = 0; x < 4; x++)
			{
				useThisComment[x] = "no";
			}

			String[] comment = new String[4];
			java.sql.Date[] commentDate = new java.sql.Date[4];
			java.sql.Time[] commentTime = new java.sql.Time[4];
			Integer[] commentSeqNumber = new Integer[4];
			
			//four comment lines.
			for (int x=1; x < 5; x++)
			{
				// comment.
				testString = request.getParameter(x + "comment");
				
				if (testString == null || testString.trim().equals(""))
					testString = "";
				else
					useThisComment[x-1] = "yes";

				comment[x-1] = testString.trim();

				// comment date.
				testString = request.getParameter(x + "commentDate");
				
				if (testString == null || testString.trim().equals("") ||
					testString.equals("1900-01-01"))
					testString = dates[7];

				commentDate[x-1] = java.sql.Date.valueOf(testString);

				// comment time.
				testString = request.getParameter(x + "commentTime");
				
				if (testString == null || testString.trim().equals("") ||
					testString.equals("00:00:01"))
					testString = dates[8];

				commentTime[x-1] = java.sql.Time.valueOf(testString);

				// detail sequence Number.
 				Integer cmtSeq = new Integer(lineCount + 1);
 				commentSeqNumber[x-1] = cmtSeq;

 					
				// increment lineCount.
				if (useThisComment[x-1].equals("yes"))
					lineCount = lineCount + 1;
			}
					 
			//set SampleRequestOrder Comment fields.
			String[] outComment            = new String[lineCount];
			java.sql.Date[] outCommentDate = new java.sql.Date[lineCount];
			java.sql.Time[] outCommentTime = new java.sql.Time[lineCount];
			Integer[] outCommentSeqNumber  = new Integer[lineCount];
			
			y = 0;
			
			for (int x = 0; x < 4; x++)
			{
				if (useThisComment[x].equals("yes"))
				{
 					outComment[y] = comment[x];
 					outCommentDate[y] = commentDate[x];
 					outCommentTime[y] = commentTime[x];
 					outCommentSeqNumber[y] = commentSeqNumber[x];
 					y++;
				}
			}

			sro.setComment(outComment);
			sro.setCommentDate(outCommentDate);
			sro.setCommentTime(outCommentTime);
			sro.setCommentSeqNumber(outCommentSeqNumber);


			//REMARK LINES

			// compairision fields.
			testString = "";

			// linecount: total number of comment lines entered against.
			lineCount = 0;

			// useThisRemark: determines if the detail entry is entered against.
			// useThisRemark = "yes" if a error occurs on input.
			// useThisRemark = "yes" if a valid non blank/zero entry occurs.
			String[] useThisRemark = new String[6]; 
			for (int x = 0; x < 6; x++)
			{
				useThisRemark[x] = "no";
			}

			String[] remark = new String[6];
			Integer[] remarkSeqNumber = new Integer[6];
			
			//six remark lines.
			for (int x=1; x < 7; x++)
			{
				// remark.
				testString = request.getParameter(x + "remark");
				
				if (testString == null || testString.trim().equals(""))
					testString = "";
				else
					useThisRemark[x-1] = "yes";

				remark[x-1] = testString.trim();

				// remark sequence Number.
 				Integer rmkSeq = new Integer(lineCount + 1);
 				remarkSeqNumber[x-1] = rmkSeq;

 					
				// increment lineCount.
				if (useThisRemark[x-1].equals("yes"))
					lineCount = lineCount + 1;
			}
					 
			//set SampleRequestOrder Remark fields.
			String[] outRemark           = new String[lineCount];
			Integer[] outRemarkSeqNumber = new Integer[lineCount];
			
			y = 0;
			
			for (int x = 0; x < 6; x++)
			{
				if (useThisRemark[x].equals("yes"))
				{
 					outRemark[y] = remark[x];
 					outRemarkSeqNumber[y] = remarkSeqNumber[x];
 					y++;
				}
			}

			sro.setRemark(outRemark);
			sro.setRemarkSeqNumber(outRemarkSeqNumber);
			
			
				
        	//if there is a error message then wrap it with method specific info.
        	// also change the order status back to it's last valid status if it
        	// had a valid value.
        	
        	if (!msg.equals(""))
        	{
	        	if (savedStatus == null)
	        		savedStatus = "";
	        	if (!savedStatus.equals("") && 
		        	!savedStatus.trim().equals(sro.getStatus()))
	        	{
	        		sro.setStatus(savedStatus);
	        		msg = msg + " The order status has been set back to its " +
	        		            "original value prior to change due to edit " +
	        		            "failures. \\n";
	        	}
	        	
        		msg = "Error occured on update. \\n" + msg +        			  
        			  " - com.treetop.servlets.CtlSampleRequest.updateSample()";
        	} else
        	{
	        	if (sro.getStatus().equals("SH") &&
		        	//!sro.getStatus().equals(savedStatus.trim() )) // wth 06/08/2011
	        		savedStatus.trim().equals("PD") )						// wth 06/08/2011
	        	{
		        	// Set the method variable to allow this to email after update.
		        	emailIt = "yes";
		        	//buildShippedEmail(sro, request, response);

		        	String[] fv = sro.getShippedFruitVariety();

		        	for (int x = 0; x < fv.length; x++)
		        	{	
		        		if (fv[x].trim().equals(""))
		        			fv[x] = sro.getFruitVariety()[x];
		        	}
		        	
		        	sro.setShippedFruitVariety(fv);
	        	}
        	}
	        		        
        }


        //  Test for a error message. If one exists then route user back to
        // update jsp with necessary parameters and drop down lists.
		request.setAttribute("sro", sro);
        request.setAttribute("msg", msg);
        
        if (!msg.equals(""))
        {			
			request.setAttribute("requestType", "updateRetry");
				
        } else {
	        sro.delete();
	        sro.add();
			request.setAttribute("requestType", "updateFinished");
			msg = "Changes were Completed Normally";
			request.setAttribute("msg", msg);

			// email here if needed.
			if (emailIt.equals("yes"))
				buildShippedEmail(sro, request, response);
        }
	                  	                              	 
		return;
		
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleRequest.updateSample(): " + e);
	}  	    	
}
/**
 * Use to Check Parameters, 
 *		If they should be there,
 *      If numbers are valid,
 *      If dates are valid,
 *      if from's are less than to's.
 *
 * Creation date: (6/17/2003 10:29:40 AM)
 */
private void validateParameters(javax.servlet.http.HttpServletRequest request,
								javax.servlet.http.HttpServletResponse response)  
{	
	try
	{
		//** Bring in Variables.
	  	String msg = (String) request.getAttribute("msg");

	  	if (msg == null)
	  		msg = "";
	  		
		String requestType = (String) request.getAttribute("requestType");
		
		if (requestType.equals("list") || requestType.equals("delete"))
		{
			if (!requestType.equals("delete"))
			{
			//*** Test Sample Numbers, make sure they are numbers.
 			String sampleNumber = request.getParameter("sampleNumber");
			if (sampleNumber != null && sampleNumber.length() != 0)
			{
				try
				{
					Integer testedNumber = new Integer(sampleNumber);
					request.setAttribute("sampleNumber", testedNumber);
					  	pageList(request, response); 
					return; 
				}
				catch(NumberFormatException nfe)
				{
					msg = msg +
						  "There is a problem with the Sample Number, " + 
						  "the value entered is not a valid numeric entry.\\n " +
						  "com.treetop.servlets.CtlSampleRequest." +
						  "ValidateParameters()";
				}		
			}
			else
			{
				request.setAttribute("sampleNumber", null);
			}
			}

		
			String fromSample = request.getParameter("fromSample");
			if (fromSample == null || fromSample.length() == 0)
			   fromSample = "0";
			String toSample = request.getParameter("toSample");
			if (toSample == null || toSample.length() == 0)
			   toSample = "999999999";
					
 			try
			{
				Integer testedFromSample    = new Integer(fromSample);
				Integer testedToSample      = new Integer(toSample);
				int x = testedFromSample.compareTo(testedToSample);
				
				if (x > 0)
					msg = msg +
					      "For the Sample Number Fields, the From Sample number " +
			        	  "is greater than the to Sample.\\n" +
			              "com.treetop.servlets.CtlSampleRequest." +
						  "ValidateParameters()"; 
			    else
			    {
  					request.setAttribute("fromSample", testedFromSample);
  					request.setAttribute("toSample", testedToSample);
			    } 
			}
			catch(NumberFormatException nfe)
			{
				msg = msg +
				      "There is a problem with the Sample Number, " + 
					  "the numbers chosen are not valid.\\n " +
					  "com.treetop.servlets.CtlSampleRequest." +
					  "ValidateParameters()";
			}

			
			//*** Test Broker Number, make sure it is numeric.
 			String broker = request.getParameter("broker");
			if (broker != null && broker.length() != 0)
			{
				try
				{
					// Does not matter if Broker is Valid - on NEW BOX 
					Integer testedNumber = new Integer(broker);
				//	ValidateFields vf = new ValidateFields();
				//	String newmsg = vf.validateBroker(broker);

				//	if (!msg.equals(""))
				//		msg = msg +
				//			  "The requested broker value is invalid. " + 
				//		      newmsg + 
				//		      "\\ncom.treetop.servlets.CtlSampleRequest." +
				//		  	  "ValidateParameters()";
				//	else
				//	{	
						request.setAttribute("broker", testedNumber);
				//	}
				}
				catch(NumberFormatException nfe)
				{
					msg = msg +
					      "There is a problem with the Sample Number, " + 
						  "the value entered is not a valid numeric entry. \\n" +
						  "com.treetop.servlets.CtlSampleRequest." +
						  "ValidateParameters()";
				}	
			}
			
			//*** Test Customer Number, make sure it is numeric.
 			String customerNumber = request.getParameter("customerNumber");
			if (customerNumber != null && customerNumber.length() != 0)
			{
				try
				{
					Integer testedNumber = new Integer(customerNumber);
					try
					{
					   SampleRequestCustomer cust = new SampleRequestCustomer(testedNumber);
					   request.setAttribute("customerNumber", testedNumber);
					}
					catch(Exception e)
					{
						msg = msg +
							  "The requested customer does not exist( " +
							  request.getParameter("customerNumber") + ")\\n" + 
						      "com.treetop.servlets.CtlSampleRequest." +
						  	  "ValidateParameters()";
					}

				}
				catch(NumberFormatException nfe)
				{
					msg = msg +
					      "There is a problem with the Customer Number, " + 
						  "the value entered is not a valid numeric entry. \\n" +
						  "com.treetop.servlets.CtlSampleRequest." +
						  "ValidateParameters()";
				}	
			}

			//*** Test Formula Number, make sure it is numeric.
 			String formulaNumber = request.getParameter("formulaNumber");
			if (formulaNumber != null && formulaNumber.length() != 0)
			{
				try
				{
					Integer testedNumber = new Integer(formulaNumber);
					try
					{
					   RandDFormula formula = new RandDFormula(testedNumber);
					   request.setAttribute("formulaNumber", testedNumber);
					}
					catch(Exception e)
					{
						msg = msg +
							  "The requested formula does not exist( " +
							  request.getParameter("formulaNumber") + ")\\n" + 
						      "com.treetop.servlets.CtlSampleRequest." +
						  	  "ValidateParameters()";
					}

				}
				catch(NumberFormatException nfe)
				{
					msg = msg +
					      "There is a problem with the Formula Number, " + 
						  "the value entered is not a valid numeric entry. \\n" +
						  "com.treetop.servlets.CtlSampleRequest." +
						  "ValidateParameters()";
				}	
			}
				
			//Test Received, Ship, and Delivery dates.  
			//make sure they are valid dates. and the "from" is before the "to".
			//  Received Date
			int intFromDate = 0;
			int intToDate = 99999999;
			String fromDate = request.getParameter("fromReceivedDate");
			String toDate = request.getParameter("toReceivedDate");
			
			if (fromDate != null && fromDate.length() != 0
				                 && msg.equals(""))
			{
				String fromdate[] = CheckDate.validateDate(fromDate);
				if (!fromdate[6].equals(""))
				{			
					msg = msg +
					      "Unable to validate date from Received Date. " +
					       fromdate[6] + " ( " + fromDate +  " ) \\n" +
						  "(CtlSampleRequest.validateParameters. " +
						  "--From Received Date--)";
				}
				else
				{
					intFromDate = Integer.parseInt(fromdate[5]);
					request.setAttribute("fromReceivedDate", fromdate[7]); 
				}
			}

			
			if (toDate != null && toDate.length() != 0)
			{
				String todate[] = CheckDate.validateDate(toDate);
				if (!todate[6].equals(""))
				{			
					msg = msg +
					      "Unable to validate date from Received Date. " +
					       todate[6] + " ( " + toDate +  " ) \\n" +
						  "(CtlSampleRequest.validateParameters. " +
						  "--To Received Date--)";
				}
				else
				{
					intToDate = Integer.parseInt(todate[5]);
					request.setAttribute("toReceivedDate", todate[7]); 
				}
			}

			if (intToDate < intFromDate) //** Error if True	
    		{
				msg = msg +
				        "Ending Received Date must be greater than " +
				        "or equal to Starting Received Date. \\n" +
						" Start Date of " + fromDate +  " " +
						" Ending Date of " + toDate +  " \\n" +
		   				"(CtlSampleRequest.validateParameters. " +
						"--Compare Received Dates--)";	
 	 		}
    		
			// Ship Date
			intFromDate = 0;
			intToDate = 99999999;
			fromDate = request.getParameter("fromShipDate");
			toDate = request.getParameter("toShipDate");
			
			if (fromDate != null && fromDate.length() != 0 && msg.equals(""))
			{
				String fromdate[] = CheckDate.validateDate(fromDate);
				
				if (!fromdate[6].equals(""))
				{			
					msg = msg +
					      "Unable to validate date From Ship Date. " +
					       fromdate[6] + " ( " + fromDate +  " ) \\n" +
						  "(CtlSampleRequest.validateParameters. " +
						  "--From Ship Date--)";
				}
				else
				{
					intFromDate = Integer.parseInt(fromdate[5]);
					request.setAttribute("fromShipDate", fromdate[7]); 
				}
			}

			
			if (toDate != null && toDate.length() != 0)
			{
				String todate[] = CheckDate.validateDate(toDate);
				if (!todate[6].equals(""))
				{			
					msg = msg +
					      "Unable to validate date From Ship Date. \\n" +
					       todate[6] + " ( " + toDate +  " ) \\n" +
						  "(CtlSampleRequest.validateParameters. " +
						  "--To Ship Date--)";
				}
				else
				{
					intToDate = Integer.parseInt(todate[5]);
					request.setAttribute("toShipDate", todate[7]); 
				}
			}

			if (intToDate < intFromDate) //** Error if True	
    		{
				msg = msg +
				        "Ending Ship Date must be greater than " +
				        "or equal to Starting Ship Date. \\n" +
						" Start Date of " + fromDate +  " " +
						" Ending Date of " + toDate +  " \\n" +
		   				"(CtlSampleRequest.validateParameters. " +
						"--Compare Ship Dates--)";	
 	 		}

			// Delivery Date
    		intFromDate = 0;
			intToDate = 99999999;
			fromDate = request.getParameter("fromDeliveryDate");
			toDate = request.getParameter("toDeliveryDate");
			
			if (fromDate != null && fromDate.length() != 0
				                 && msg.equals(""))
			{
				String fromdate[] = CheckDate.validateDate(fromDate);
				
				if (!fromdate[6].equals(""))
				{			
					msg = msg +
					      "Unable to validate date From Delivery Date. " +
					       fromdate[6] + " ( " + fromDate +  " ) \\n" +
						  "(CtlSampleRequest.validateParameters. " +
						  "--From Delivery Date--)";
				}
				else
				{
					intFromDate = Integer.parseInt(fromdate[5]);
					request.setAttribute("fromDeliveryDate", fromdate[7]); 
				}
			}

			
			if (toDate != null && toDate.length() != 0)
			{
				String todate[] = CheckDate.validateDate(toDate);
				if (!todate[6].equals(""))
				{			
					msg = msg +
					      "Unable to validate date To Delivery Date. " +
					       todate[6] + " ( " + toDate +  " ) \\n" +
						  "(CtlSampleRequest.validateParameters. " +
						  "--To Delivery Date--)";
				}
				else
				{
					intToDate = Integer.parseInt(todate[5]);
					request.setAttribute("toDeliveryDate", todate[7]); 
				}
			}

			if (intToDate < intFromDate) //** Error if True	
    		{
				msg = msg +
				      "Ending Delivery Date must be greater than " +
				      "or equal to Starting Delivery Date. \\n" +
						" Start Date of " + fromDate +  " " +
						" Ending Date of " + toDate +  " \\n" +
		   				"(CtlSampleRequest.validateParameters. " +
						"--Compare Delivery Dates--)";	
 	 		}
    		
			if (msg.equals("") || requestType.equals("delete"))
			{
				request.setAttribute("requestType", "list");
			  	pageList(request, response);
			}
			else
			{
				request.setAttribute("msg", msg);
				pageInq(request,response);

			}
			
		}

		//Add, Copy, Update
		// Add an Else in here if you want to test things from here.
	}
	catch(Exception e)
	{
		System.out.println("Error in CtlSampleRequest.validateParameters: " + e);
	}
	   	    	
}

/**
 * Will return a Vector of Strings.
 * Creation date: (2/2/2004 10:58:32 AM)
 * @return java.util.Vector[]
 */
private Vector buildLabelInfo(SampleRequestOrder chosenSample,
							  javax.servlet.http.HttpServletRequest request,
					          javax.servlet.http.HttpServletResponse response)
{
	Vector labels = new Vector();
	for (int x = 0; x < chosenSample.getQuantity().length; x++)
	{
		String thisLabel = "";
		for (int row = 1; row < 6; row++)
		{
			if (!thisLabel.equals(""))
			   thisLabel = thisLabel + "<br>";
			for (int seq = 1; seq < 3; seq++)
			{
				String fieldName = "row" + row + "seq" + seq;
				String parmValue = request.getParameter(fieldName);
                GeneralInfoDried generalClass = new GeneralInfoDried();				
				if (parmValue != null &&
					!parmValue.trim().equals(""))
				{
					//**Additional Description
					if (parmValue.trim().equals("ad"))
					{
						thisLabel = thisLabel +
						   chosenSample.getAdditionalDescription()[x] +
						   "&nbsp;&nbsp;";
					}
					//**Brix
					if (parmValue.trim().equals("bx"))
					{
						thisLabel = thisLabel + "Brix: " +
						   chosenSample.getBrixLevel()[x] +
						   "&nbsp;&nbsp;";
					}
					//**Chemical Additive
					if (parmValue.trim().equals("ca"))
					{
			           String chemAdd = "&nbsp;";
					   if (!chosenSample.getChemicalAdditive()[x].equals("00"))
					   {			           
		  	           try
		               {
				          generalClass = new GeneralInfoDried("CA", chosenSample.getChemicalAdditive()[x]);
			              if (generalClass.getDescFull() != null &&
   	                         !generalClass.getDescFull().equals("null"))
                             //chemAdd = generalClass.getDescFull();  wth 06/08/2011	
			            	   chemAdd = "Additive: " + generalClass.getDescFull();  //wth 06/08/2011
			           }
			           catch (Exception e)
			           {
			 	       //   System.out.println("Problem when instatiate of Chemical Additive Error:" + e);
			           }
					   }						
						thisLabel = thisLabel + 
						   chemAdd +
						   "&nbsp;&nbsp;";		
					}					
					//**Color
					if (parmValue.trim().equals("cl"))
					{
			           String color = "&nbsp;";
		  	           try
		               {
				          generalClass = new GeneralInfoDried("CL", chosenSample.getColor()[x]);
			              if (generalClass.getDescFull() != null &&
   	                         !generalClass.getDescFull().equals("null"))
                             color = "Color: " + generalClass.getDescFull();				          
			           }
			           catch (Exception e)
			           {
			 	         // System.out.println("Problem when instatiate of Color Error:" + e);
			           }						
						thisLabel = thisLabel +
						   color +
						   "&nbsp;&nbsp;";								
					}
					//**Art Color
					// wth 06/08/2011  Added Art Color
					if (parmValue.trim().equals("c2"))
					{
			           String color = "&nbsp;";
		  	           try
		               {
				          generalClass = new GeneralInfoDried("CL", chosenSample.getColor()[x]);
			              if (generalClass.getDescFull() != null &&
   	                         !generalClass.getDescFull().equals("null"))
                             color = "Art Color: " + generalClass.getDescFull();				          
			           }
			           catch (Exception e)
			           {
			 	         // System.out.println("Problem when instatiate of Color Error:" + e);
			           }						
						thisLabel = thisLabel +
						   color +
						   "&nbsp;&nbsp;";								
					}
					//**Nat Color
					// wth 06/08/2011  Added Art Color
					if (parmValue.trim().equals("c3"))
					{
			           String color = "&nbsp;";
		  	           try
		               {
				          generalClass = new GeneralInfoDried("CL", chosenSample.getColor()[x]);
			              if (generalClass.getDescFull() != null &&
   	                         !generalClass.getDescFull().equals("null"))
                             color = "Nat Color: " + generalClass.getDescFull();				          
			           }
			           catch (Exception e)
			           {
			 	         // System.out.println("Problem when instatiate of Color Error:" + e);
			           }						
						thisLabel = thisLabel +
						   color +
						   "&nbsp;&nbsp;";								
					}
					//**Description
					if (parmValue.trim().equals("ds"))
					{
						thisLabel = thisLabel +
						   chosenSample.getItemDescription()[x] +
						   "&nbsp;&nbsp;";
					}	
					//**Flavor
					if (parmValue.trim().equals("fl"))
					{
			           String flavor = "&nbsp;";
		  	           try
		               {
				          generalClass = new GeneralInfoDried("FL", chosenSample.getFlavor()[x]);
			              if (generalClass.getDescFull() != null &&
   	                         !generalClass.getDescFull().equals("null"))
                             flavor = "Flavor: " + generalClass.getDescFull();				          
			           }
			           catch (Exception e)
			           {
			 	       //   System.out.println("Problem when instatiate of Flavor Error:" + e);
			           }						
						thisLabel = thisLabel + 
						   flavor +
						   "&nbsp;&nbsp;";								
					}	
					//**Atr Flavor
					// wth 06/08/2011  Added Atr. Flavor
					if (parmValue.trim().equals("f2"))
					{
			           String flavor = "&nbsp;";
		  	           try
		               {
				          generalClass = new GeneralInfoDried("FL", chosenSample.getFlavor()[x]);
			              if (generalClass.getDescFull() != null &&
   	                         !generalClass.getDescFull().equals("null"))
                             flavor = "Art Flavor: " + generalClass.getDescFull();				          
			           }
			           catch (Exception e)
			           {
			 	       //   System.out.println("Problem when instatiate of Flavor Error:" + e);
			           }						
						thisLabel = thisLabel + 
						   flavor +
						   "&nbsp;&nbsp;";								
					}	
					//**Nat Flavor
					// wth 06/08/2011  Added Atr. Flavor
					if (parmValue.trim().equals("f3"))
					{
			           String flavor = "&nbsp;";
		  	           try
		               {
				          generalClass = new GeneralInfoDried("FL", chosenSample.getFlavor()[x]);
			              if (generalClass.getDescFull() != null &&
   	                         !generalClass.getDescFull().equals("null"))
                             flavor = "Nat Flavor: " + generalClass.getDescFull();				          
			           }
			           catch (Exception e)
			           {
			 	       //   System.out.println("Problem when instatiate of Flavor Error:" + e);
			           }						
						thisLabel = thisLabel + 
						   flavor +
						   "&nbsp;&nbsp;";								
					}								
					//**Fruit Type
					if (parmValue.trim().equals("ft"))
					{
			           String fruitType = "&nbsp;";
					   if (!chosenSample.getFruitType()[x].equals("00"))
					   {
		  	           try
		               {
				          generalClass = new GeneralInfoDried("FT", chosenSample.getFruitType()[x]);
			              if (generalClass.getDescFull() != null &&
   	                         !generalClass.getDescFull().equals("null"))
                             fruitType = generalClass.getDescFull();				          
			           }
			           catch (Exception e)
			           {
			 	        //  System.out.println("Problem when instatiate of Fruit Type Error:" + e);
			           }	
					   }					
						thisLabel = thisLabel + 
						   fruitType +
						   "&nbsp;&nbsp;";						
					}	
					//**Fruit Variety
					if (parmValue.trim().equals("fv"))
					{
			           String fruitVariety = "&nbsp;";
		  	           try
		               {
				          //generalClass = new GeneralInfoDried("FV", chosenSample.getShippedFruitVariety()[x]);  wth 06/08/2011
		  	        	 generalClass = new GeneralInfoDried("FV", chosenSample.getFruitVariety()[x]);          //wth 06/08/2011
			              if (generalClass.getDescFull() != null &&
   	                         !generalClass.getDescFull().equals("null"))
                             fruitVariety = "Fruit Variety: " + generalClass.getDescFull();				          
			           }
			           catch (Exception e)
			           {
			 	        //  System.out.println("Problem when instatiate of Fruit Variety Error:" + e);
			           }						
						thisLabel = thisLabel + 
						   fruitVariety +
						   "&nbsp;&nbsp;";	
					}									
					//**Lot Number
					if (parmValue.trim().equals("ln"))
					{
						thisLabel = thisLabel + 
						   chosenSample.getLotNumber()[x] +
						   "&nbsp;&nbsp;";
					}		
					//**Product Group
					if (parmValue.trim().equals("pg"))
					{
			           String productGroup = "&nbsp;";
					   if (!chosenSample.getProductGroup()[x].equals("00"))
					   {
		  	           try
		               {
				          generalClass = new GeneralInfoDried("GP", chosenSample.getProductGroup()[x]);
			              if (generalClass.getDescFull() != null &&
   	                         !generalClass.getDescFull().equals("null"))
                             productGroup = generalClass.getDescFull();				          
			           }
			           catch (Exception e)
			           {
			 	        //  System.out.println("Problem when instatiate of Product Group Error:" + e);
			           }	
					   }					
						thisLabel = thisLabel + 
						   productGroup +
						   "&nbsp;&nbsp;";
					}	
					//**Product Size
					if (parmValue.trim().equals("cs"))
					{
			           String cutSize = "&nbsp;";
					   if (!chosenSample.getCutSize()[x].equals("00"))
					   {
		  	           try
		               {
				          generalClass = new GeneralInfoDried("CS", chosenSample.getCutSize()[x]);
			              if (generalClass.getDescFull() != null &&
   	                         !generalClass.getDescFull().equals("null"))
                             cutSize = generalClass.getDescFull();				          
			           }
			           catch (Exception e)
			           {
			 	         // System.out.println("Problem when instatiate of Cut Size Error:" + e);
			           }			
					   }			
						thisLabel = thisLabel + 
						   cutSize +
						   "&nbsp;&nbsp;";								
					}	
					//**Product Type
					if (parmValue.trim().equals("pt"))
					{
			           String productType = "&nbsp;";
					   if (!chosenSample.getProductType()[x].equals("00"))
					   {
		  	           try
		               {
				          generalClass = new GeneralInfoDried("PT", chosenSample.getProductType()[x]);
			              if (generalClass.getDescFull() != null &&
   	                         !generalClass.getDescFull().equals("null"))
                             productType = generalClass.getDescFull();				          
			           }
			           catch (Exception e)
			           {
			 	         // System.out.println("Problem when instatiate of Product Type Error:" + e);
			           }					
					   }	
						thisLabel = thisLabel + 
						   productType +
						   "&nbsp;&nbsp;";						
					}	
					//**Resource
					if (parmValue.trim().equals("re"))
					{
						thisLabel = thisLabel + "Resource: " +
						   chosenSample.getResource()[x] +
						   "&nbsp;&nbsp;";
					}	
					//**Sample Size
					if (parmValue.trim().equals("sz"))
					{
                       String testSize = "" + chosenSample.getContainerSize()[x];
                       if(!testSize.trim().equals("0"))
                         thisLabel = thisLabel + testSize + "&nbsp;";

        		       thisLabel = thisLabel + 
                         chosenSample.getUnitOfMeasure()[x] +
						   "&nbsp;&nbsp;";
					}	
					//**Spec Number
					if (parmValue.trim().equals("sp"))
					{
						thisLabel = thisLabel + 
						   chosenSample.getSpecNumber()[x] +
						   "&nbsp;&nbsp;";
					}									
				}
			}
		}
            thisLabel = "<table style=\"width:98%\">" +
                          "<tr>" +                           
							 "<td style=\"text-align:center\">" + 
                      //"<img src=\"https://image.treetop.com/webapp/verySmallLogo.bmp\">" +					 
                      //"<img src=\"https://image.treetop.com/webapp/TTNewLogoSmallTransparent.gif\">" +       wth 06/08/2011
					  //"<img src=\"https://image.treetop.com/webapp/TreeNetImages/TTLogoWordmark_w_Sabroso_Small.gif\">" + // wth 06/08/2011
                      "<img src=\"/web/Include/images/TT_Logo_2C_300dpi.png\" style='width:119px'>" + // jh 01/18/2014
                                "</td>" +
                              "</tr>" +    
							  "<tr>" +                           
							    "<td style=\"text-align:center\">" + 
			                       thisLabel.trim() +
			                 "</td>" +   
			                 "</tr>" +   
			                 "</table>";		
	
	    BigDecimal displayNumber = chosenSample.getQuantity()[x].setScale(0, BigDecimal.ROUND_CEILING);
		String     setNumber     = displayNumber + "" ;
		Integer    thisQty       = new Integer(0);
		try
		{
			thisQty = new Integer(setNumber);
		}
		catch(Exception e)
		{
			
		}
		// Add 1 label for each Qty.
		for (int y = 0; y < thisQty.intValue(); y++)
		{
 	       labels.addElement(thisLabel);
		}	

	}
	return labels;
}

/**
 * Use to send label information.
 * Creation date: (12/17/2003 4:01:40 PM)
 */
private void pageLabel(javax.servlet.http.HttpServletRequest request,
			 		   javax.servlet.http.HttpServletResponse response)  
{
	String requestType = (String) request.getAttribute("requestType");

	if (requestType.equals("defineLabel"))
    {
		try
		{	       
			//***** Go to the Dtl JSP *****//
			getServletConfig().getServletContext().
				getRequestDispatcher("/JSP/SampleRequest/updSampleLabels.jsp?" +
					"msg=").
				forward(request, response);
    
		}
		catch(Exception e)
		{
			System.out.println("Error in CtlSampleRequest.pageLabel: " + e);
		}  	 
		
     }
     else
     {
		SampleRequestOrder chosenSample = new SampleRequestOrder();
 		String sampleNumber = request.getParameter("sampleNumber");
		if (sampleNumber != null && sampleNumber.length() != 0)
		{
			try
			{
				Integer testedNumber = new Integer(sampleNumber);
         		chosenSample = new SampleRequestOrder(testedNumber); 
			}
			catch(NumberFormatException nfe)
			{
				System.out.println(
					  "There is a problem with the Sample Number, " + 
					  "the value entered is not a valid numeric entry.\\n " +
					  "com.treetop.servlets.CtlSampleRequest." +
					  "ValidateParameters()");
			}
			catch(Exception e)
			{
				System.out.println(
					  "There is a problem with the Sample Number, " + 
					  "the value entered is not a valid numeric entry.\\n " +
					  "com.treetop.servlets.CtlSampleRequest." +
					  "ValidateParameters()");
			}				
		}	
	     
		try
		{
			Vector sentLabelInfo = new Vector();
			Labels thisInformation = new Labels();
			Integer template = new Integer(1);
			Integer labelNum = new Integer(1);
			try
			{
			   labelNum = new Integer(request.getParameter("labelNumber"));
			}
			catch(Exception e)
			{
			
			}
			
			String  thisDetail = "";
			if (request.getParameter("shippingLabel") != null)
			{
				SampleRequestCustomer chosenCustomer = new SampleRequestCustomer(chosenSample.getCustNumber());
                String custAdd2      = chosenCustomer.getAddress2();
                String custZip       = "";
                String custZipExt    = "";
                if (chosenCustomer.getZip() != 0)
                  custZip    = "" + chosenCustomer.getZip();
                if (chosenCustomer.getZipExtention() != 0)
                  custZipExt = "-" + chosenCustomer.getZipExtention();
                 thisDetail = "<table style=\"width:98%\" cellpadding\"1\">" +
                               "<tr>" +                           
   							     "<td style=\"font-size:9pt;color=#006400\">" + 
                                  //"<img src=\"https://image.treetop.com/webapp/TTNewLogoSmallTransparent.gif\" align=\"top\">" +       wth 06/08/2011
                                  //"<img src=\"https://image.treetop.com/webapp/TreeNetImages/TTLogoWordmark_w_Sabroso_Small.jpg\">" + // wth 06/08/2011
   							  "<img src=\"/web/Include/images/TT_Logo_2C_300dpi.png\" style='width:117px' />" + //jh 01/18/2014
                                  "&nbsp;&nbsp;&nbsp;" +
                             "</td>" +
        							  "<td style=\"font-size:9pt;color:#006400; text-align:center \">" + 
                                "&nbsp;&nbsp;&nbsp;&nbsp;" + "*&nbsp;&nbsp;Ingredient Division&nbsp;&nbsp;*&nbsp;&nbsp;" +
                                "<br> 1-800-367-6571 <br>" +
                                "&nbsp;&nbsp;&nbsp;&nbsp;<font style=\"font-size:11pt; color=#000000\"><b>" + 
										      chosenCustomer.getName() + "</b></font>" + 
                              "</td>" +
                       "</tr>" +                    
							  "<tr>" +                           
							    "<td style=\"font-size:11pt; text-align:center\" colspan=\"2\">" + 
			                       chosenCustomer.getAddress1() + "<br>";
			    if (!custAdd2.trim().equals(""))
			       thisDetail = thisDetail +
			                       custAdd2 + "<br>";
			    thisDetail = thisDetail +
			                 chosenCustomer.getCity() + "&nbsp;&nbsp;&nbsp;" +
			                 chosenCustomer.getState() + "&nbsp;&nbsp;&nbsp;" +
			                 chosenCustomer.getForeignCountry() + "<br>" +
							 "&nbsp;&nbsp;&nbsp;" + custZip + custZipExt + "&nbsp;&nbsp;&nbsp;" +
							 chosenCustomer.getForeignPostalCode() +
			                 "</td>" +   
			                 "</tr>" +  
                             "<tr>" +                           
							    "<td style=\"font-size:11pt; text-align:center\" colspan=\"2\">" +
							    "ATTN: " + chosenSample.getCustContact() +
							    "&nbsp;&nbsp;&nbsp;" + chosenSample.getCustContactPhone() +
							 "</td>" +   
			                 "</tr>" +     
			                 "</table>";
			thisInformation.setTemplateNumber(template);
			thisInformation.setLabelNumber(labelNum);
			thisInformation.setLabelInformation(thisDetail);
			sentLabelInfo.addElement(thisInformation);			                 
			}
			Vector labels = buildLabelInfo(chosenSample,
				                           request,
				                           response);
			
			for (int x = 0; x < labels.size(); x++)
			{
      			thisDetail = (String) labels.elementAt(x);
                thisInformation = new Labels();      				
				thisInformation.setTemplateNumber(template);
				thisInformation.setLabelNumber(labelNum);
				thisInformation.setLabelInformation(thisDetail);
				sentLabelInfo.addElement(thisInformation);			
			}	

			String message = CtlLabelTemplates.displayTemplate(request,
				                                               response,
				                                               sentLabelInfo);
			
		}
		catch(Exception e)
		{
			System.out.println("Error in CtlSampleRequest.pageDtl: " + e);
		}  	 		
  	 }
		
 return;  	
}
}
