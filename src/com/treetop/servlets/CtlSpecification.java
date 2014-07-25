package com.treetop.servlets;

import com.treetop.*;
import com.treetop.data.*;
import com.treetop.view.*;
import com.treetop.viewbeans.ParameterMessageBean;

import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.util.*;
import java.math.*;

/*This servlet will control the looks and views of 
 *   Attributes based information.
 *
 * Creation date: (12/8/2004 8:21:34 AM)
 * @author: Teri Walton
 */
public class CtlSpecification extends javax.servlet.http.HttpServlet 
{
	/**
	 * Send in a fieldValue and it will highlight that field
	 *   in the drop down box   
	 * This Method will be used from the JSP's to display the 
	 *     show revisions drop down list for the Specification application.
	 *     Will show on both the Inquiry page and the List Page.
	 * 
	 *   The Default will be to show the Current Revision Only
	 *
	 * Creation date: (4/26/2005 11:15:48 AM)
	 */
	public static String buildShowRevisionsDropDown(String fieldValue) 
	{
		
		if (fieldValue == null ||
		    fieldValue.trim().equals(""))
		    fieldValue = "Show Summary";
		
		
		String returnString = "<select name=\"showrevisions\">" +
							  "<option value=\"All\">All";
							  
			returnString = returnString + 
			   		 "<option value=\"Current Revision Only\"";
	
			if (fieldValue != null &&
			    fieldValue.equals("Current Revision Only"))
			    returnString = returnString +
			                   " selected";
					   		 
			returnString = returnString + 
					   		 ">Current Revision Only";
					   		 
			returnString = returnString + 
				 "<option value=\"Non-Current Revisions\"";
	
			if (fieldValue != null &&
				fieldValue.equals("Non-Current Revisions"))
				returnString = returnString +
						   	" selected";
					   		 
			returnString = returnString + 
							 ">Non-Current Revisions";	
							 	
		returnString = returnString + 
			 "<option value=\"Show Summary\"";
							 	
		if (fieldValue != null &&
			fieldValue.equals("Show Summary"))
			returnString = returnString +
						" selected";
					   		 
		returnString = returnString + 
						 ">Show Summary Of All Revisions";	
						 						 			   		 
		returnString = returnString + 
					  "</select>";
			
		return returnString;
	}
		/**
		 * Test, and Check all the Parameters
		 *    Which would go to the list page
		 * 
		 * Will Return a String of error messages, or blank if no problem. 
		 * 
		 * Creation date: (3/21/2005 4:24:21 PM)	
		 * Author:		   Teri Walton 
		 */
		private String validateParametersList(javax.servlet.http.HttpServletRequest request, 
							   	                 javax.servlet.http.HttpServletResponse response) 
		{
			String returnMessage = "";
			try
			{
			  //RESET ALL the Fields
				String[] parameterValues    = (String[]) request.getAttribute("parameterValues");		                                         
						 
				SpecificationViewInquiry  fromHeader = (SpecificationViewInquiry) request.getAttribute("fromHeader");
	//			SpecificationViewInquiry  toHeader   = (SpecificationViewInquiry) request.getAttribute("toHeader");
	
				//-------------------------------------------------------------------------	
				//  Test Order By Information
				//-------------------------------------------------------------------------
				  String orderBy = request.getParameter("orderBy");
				  if (orderBy == null ||
					  orderBy.equals(""))
					orderBy = "specificationcode"; // default
				  String orderStyle = request.getParameter("orderStyle");
				  if (orderStyle == null)
					orderStyle = ""; // default
					
				fromHeader.setOrderByField(orderBy);
				fromHeader.setOrderByStyle(orderStyle);
					
			 //-----------------------------------------------------------------------		  
			 //  Specification Number
			 //----------------------------------------------------------------------
			 String specification = request.getParameter("inqspecification");
			 if (specification != null && 
				 !specification.trim().equals(""))
			 {
			 	specification = URLDecoder.decode(specification);
			 	if (!parameterValues[0].equals(""))
				   parameterValues[0] = parameterValues[0] +
												   "<br>";
				for (int z = 0; z < specification.length(); z++)
				{
					int foundPercent = specification.indexOf("%");
					if (foundPercent == 0 &&
					    specification.substring(0, 1).equals("%"))
					  specification = specification.substring(1, specification.length());
					if (foundPercent > 0)
					  specification = specification.substring(0, foundPercent);  
				}
												   
				parameterValues[0] = parameterValues[0] +
									 "Specification Code: " +
									 specification;
				parameterValues[1] = parameterValues[1] +
									 "&inqspecification=" + URLEncoder.encode(specification.trim());	
				fromHeader.setSpecificationCode(specification.trim().toUpperCase());
			 }
			 //-----------------------------------------------------------------------		  
			 //  Specification Description
			 //----------------------------------------------------------------------
			 String specDesc = request.getParameter("inqspecdesc");
			 if (specDesc != null && 
				 !specDesc.trim().equals("") &&
				 !specDesc.trim().equals("None"))
			 {
				if (!parameterValues[0].equals(""))
				   parameterValues[0] = parameterValues[0] +
												   "<br>";
				parameterValues[0] = parameterValues[0] +
									 "Specification Description: " +
									 specDesc;
				parameterValues[1] = parameterValues[1] +
									 "&inqspecdesc=" + specDesc.trim();	
				fromHeader.setGeneralDescription(specDesc.trim());
			 }				 
			//-----------------------------------------------------------------------		  
			//  Show Inventory
			 //Exclude Inventory presentation - switch to new AS400 12/24/08 wth
			//----------------------------------------------------------------------
			//String showInventory = request.getParameter("showinventory");
			//if (showInventory != null &&
				//(showInventory.equals("on") ||
				//showInventory.equals("Y")))
			//{
				//if (!parameterValues[0].equals(""))
							//parameterValues[0] = parameterValues[0] +
												//"<br>";
			    //parameterValues[0] = parameterValues[0] +
									 //"Show Inventory: Yes";
			    //parameterValues[2] = parameterValues[2] +
									//"&showinventory=" + showInventory.trim();
			   //fromHeader.setShowInventory(showInventory.trim());	
			//}		
			//-----------------------------------------------------------------------		  
			//  Show Customer Information
			//----------------------------------------------------------------------
			String showCustomer = request.getParameter("showcustomer");
			if (showCustomer != null &&
				(showCustomer.equals("on") ||
				 showCustomer.equals("Y")))
			{
				if (!parameterValues[0].equals(""))
			  	parameterValues[0] = parameterValues[0] +
									"<br>";
				parameterValues[0] = parameterValues[0] +
									 "Show Customer Information: Yes";
				parameterValues[2] = parameterValues[2] +
									"&showcustomer=" + showCustomer.trim();
				fromHeader.setShowCustomers(showCustomer.trim());	
			}	
			//-----------------------------------------------------------------------		  
			//  Show Revisions
			//----------------------------------------------------------------------
			String showRevisions = request.getParameter("showrevisions");
			if (showRevisions != null)
			{
			   if (!parameterValues[0].equals(""))
				  parameterValues[0] = parameterValues[0] +
										"<br>";
			   parameterValues[0] = parameterValues[0] +
									 "Show Revisions: " + showRevisions;
			   parameterValues[2] = parameterValues[2] +
									"&showrevisions=" + showRevisions.trim();
			   fromHeader.setShowAllRevisions(showRevisions.trim());
			   if (showRevisions.trim().equals("Show Summary"))
			     fromHeader.setShowInventory("on");
			}	
			//-----------------------------------------------------------------------		  
			//  Show Variety
			//----------------------------------------------------------------------
			String showVarieties = request.getParameter("showvariety");
			if (showVarieties != null)
			{
			   if (!parameterValues[0].equals(""))
				  parameterValues[0] = parameterValues[0] +
										"<br>";
			   parameterValues[0] = parameterValues[0] +
									 "Show Varieties: " + showVarieties;
			   parameterValues[2] = parameterValues[2] +
									"&showvariety=" + showVarieties.trim();
			}										
			//-----------------------------------------------------------------------		  
			//  Specification Type
			//----------------------------------------------------------------------
			String specType = request.getParameter("inqspectype");
			if (specType != null && 
				!specType.trim().equals("") &&
				!specType.trim().equals("None"))
			{
			   if (!parameterValues[0].equals(""))
				  parameterValues[0] = parameterValues[0] +
												  "<br>";
			   parameterValues[0] = parameterValues[0] +
									"Specification Type: " +
									specType;
			   parameterValues[1] = parameterValues[1] +
									"&inqspectype=" + specType.trim();	
			   fromHeader.setSpecificationType(specType.trim());
			}	
			//-----------------------------------------------------------------------		  
			//  Specification Status
			//----------------------------------------------------------------------
			String specStatus = request.getParameter("inqspecstatus");
			if (specStatus != null && 
			    !specStatus.trim().equals(""))
			{
			   if (!specStatus.equals("All"))
			   {
					if (!parameterValues[0].equals(""))
				   	parameterValues[0] = parameterValues[0] +
												   "<br>";		  	
				 	parameterValues[0] = parameterValues[0] +
									  "Specification Status: " +
									  specStatus.trim();
				}
	             parameterValues[1] = parameterValues[1] +
									  "&inqspecstatus=" + specStatus.trim();	
				 fromHeader.setStatusCode(specStatus.trim());
			}		  
			  
			//-----------------------------------------------------------------------		  
			//  Resource
			//----------------------------------------------------------------------
			String resource = request.getParameter("inqresource");
			if (resource != null && 
			    !resource.trim().equals("") &&
				!resource.trim().equals("None"))
			{
				if (!parameterValues[0].equals(""))
				   parameterValues[0] = parameterValues[0] +
												   "<br>";		  	
				 parameterValues[0] = parameterValues[0] +
									  "Resource Number: " +
									  resource;
				 parameterValues[1] = parameterValues[1] +
									  "&inqresource=" + resource.trim();	
				 fromHeader.setResourceNumber(resource.trim());
			}	

			//-----------------------------------------------------------------------		  
			//  Company Number
			//----------------------------------------------------------------------
			  String company = request.getParameter("inqcompany");
			  if (company != null && 
			  !company.trim().equals("") &&
			  !company.trim().equals("None"))
			  {
				if (!parameterValues[0].equals(""))
				   parameterValues[0] = parameterValues[0] +
												   "<br>";		  	
				 parameterValues[0] = parameterValues[0] +
									  "Company: " +
									  company;
				 parameterValues[1] = parameterValues[1] +
									  "&inqcompany=" + company.trim();	
				 fromHeader.setCompanyNumber(company);
			  }		  
			  //-----------------------------------------------------------------------		  
			  //  Accounts Receivable Customer Number
			  //----------------------------------------------------------------------
			  String cust = request.getParameter("inqcustomer");
			  if (cust != null && 
			  !cust.trim().equals("") &&
			  !cust.trim().equals("None"))
			  {
				if (!parameterValues[0].equals(""))
				   parameterValues[0] = parameterValues[0] +
												   "<br>";		  	
				 parameterValues[0] = parameterValues[0] +
									  "A/R Customer: " +
									  cust;
				 parameterValues[1] = parameterValues[1] +
									  "&inqcustomer=" + cust.trim();	
				 fromHeader.setCustomerNumber(cust);
			  }
			  //-----------------------------------------------------------------------		  
			  //  Customer Status
			  //----------------------------------------------------------------------
			  String custStatus = request.getParameter("inqcuststatus");
			  if (custStatus != null && 
				  !custStatus.trim().equals(""))
			  {
			  	if (!custStatus.equals("All"))
			  	{
					if (!parameterValues[0].equals(""))
				   		parameterValues[0] = parameterValues[0] +
												   "<br>";		  	
				 	parameterValues[0] = parameterValues[0] +
									  "Customer Status: " + 
									  custStatus.trim();
			  	}		 					 
				 parameterValues[1] = parameterValues[1] +
									  "&inqcuststatus=" + custStatus.trim();	
				 fromHeader.setCustomerStatus(custStatus.trim());
			  }		  
			  //-----------------------------------------------------------------------		  
			  //  Fruit Variety
			  //----------------------------------------------------------------------
			  String fruitVariety = request.getParameter("inqvariety");
			  if (fruitVariety != null && 
			      !fruitVariety.trim().equals("") &&
			      !fruitVariety.trim().equals("None"))
			  {
				if (!parameterValues[0].equals(""))
				   parameterValues[0] = parameterValues[0] +
												   "<br>";		  	
				 parameterValues[0] = parameterValues[0]+
									  "Fruit Variety: " +
									  fruitVariety;
				 parameterValues[1] = parameterValues[1] +
									  "&inqvariety=" + fruitVariety.trim();	
				 fromHeader.setFruitVariety(fruitVariety.trim());
				//-----------------------------------------------------------------------		  
				//  Fruit Variety List
				//    Only to be used if a Fruit Variety is chosen  
				//----------------------------------------------------------------------
				 String fvList = request.getParameter("inqFVList");
				 if (fvList != null && 
					!fvList.trim().equals(""))
				 {
				    parameterValues[0] = parameterValues[0]+
										"And " +
										fvList;
				    parameterValues[1] = parameterValues[1] +
										"&inqFVList=" + fvList.trim();	
				    fromHeader.setFruitVarietyList(fvList);
				}			  
				 
			  }		
			  //-----------------------------------------------------------------------		  
			  request.setAttribute("fromHeader", fromHeader);
	//		  request.setAttribute("toHeader", toHeader);
			  request.setAttribute("parameterValues", parameterValues);
	
			}
			catch(Exception e)
			{
				System.out.println("Error in CtlSpecification.validateParametersList(request, response: " + e);
			}  	    	
			
		  return returnMessage;		
		}
		/**
		 * Test, and Check all the Parameters
		 *    Which would go to the list page	 * 
		 * Will Return a String of error messages, or blank if no problem. 
		 * 
		 * Creation date: (3/21/2005 4:24:21 PM)	
		 * Author:		   Teri Walton 
		 */
		private String validateParametersListInventory(javax.servlet.http.HttpServletRequest request, 
							   	                       javax.servlet.http.HttpServletResponse response) 
		{
			String returnMessage = "";
			try
			{
				String[] parameterValues    = (String[]) request.getAttribute("parameterValues");		                                         
						 
				SpecificationViewInquiry  fromHeader = (SpecificationViewInquiry) request.getAttribute("fromHeader");
	//			SpecificationViewInquiry  toHeader   = (SpecificationViewInquiry) request.getAttribute("toHeader");
				
			 //-----------------------------------------------------------------------		  
			 //  Specification Number
			 //----------------------------------------------------------------------
			 String specification = request.getParameter("inqspecification");
			 if (specification != null && 
				 !specification.trim().equals(""))
			 {
			 	specification = URLDecoder.decode(specification);
	//		 	if (!parameterValues[0].equals(""))
	//			   parameterValues[0] = parameterValues[0] +
	//											   "<br>";
	//			parameterValues[0] = parameterValues[0] +
	//								 "Specification ID: " +
	//								 specification;
	//			parameterValues[1] = parameterValues[1] +
	//								 "&inqspecification=" + specification.trim();	
				fromHeader.setSpecificationCode(specification.trim().toUpperCase());
			 }
			//-----------------------------------------------------------------------		  
			 //  Revision Date
			 //----------------------------------------------------------------------
			 String specificationDate = request.getParameter("specificationDate");
			 if (specificationDate != null && 
				 !specificationDate.trim().equals(""))
			 {
	//			if (!parameterValues[0].equals(""))
	//			   parameterValues[0] = parameterValues[0] +
	//											   "<br>";
	//			parameterValues[0] = parameterValues[0] +
	//								 "Revision Date: " +
	//								 specificationDate.trim();
	//			parameterValues[1] = parameterValues[1] +
	//								 "&specificationDate=" + specificationDate.trim();	
				fromHeader.setSpecificationDate(specificationDate.trim());
			 }	
			 //-----------------------------------------------------------------------		  
			 //  Show Revisions
			 //----------------------------------------------------------------------
			 String showRevisions = request.getParameter("showrevisions");
			 if (showRevisions != null)
			 {
//				if (!parameterValues[0].equals(""))
//				   parameterValues[0] = parameterValues[0] +
//										 "<br>";
//				parameterValues[0] = parameterValues[0] +
//									  "Show Revisions: " + showRevisions;
//				parameterValues[2] = parameterValues[2] +
//									 "&showrevisions=" + showRevisions.trim();
				fromHeader.setShowAllRevisions(showRevisions.trim());	
			 }				 	
			//-----------------------------------------------------------------------		  
			 //  Classification Code
			 //----------------------------------------------------------------------
			 String classification = request.getParameter("classification");
			 if (classification != null && 
				 !classification.trim().equals(""))
			 {
	//			if (!parameterValues[0].equals(""))
	//			   parameterValues[0] = parameterValues[0] +
	//											   "<br>";
	//			parameterValues[0] = parameterValues[0] +
	//								 "Classification: " +
	//								 classification.trim();
	//			parameterValues[1] = parameterValues[1] +
	//								 "&classification=" + classification.trim();	
				fromHeader.setClassification(classification.trim());
			 }				
					 		
			  //-----------------------------------------------------------------------		  
			  request.setAttribute("fromHeader", fromHeader);
	//		  request.setAttribute("toHeader", toHeader);
	//		  request.setAttribute("parameterValues", parameterValues);
		
			}
			catch(Exception e)
			{
				System.out.println("Error in CtlSpecification.validateParameters(request, response: " + e);
			}  	    	
			
		  return returnMessage;		
		}
	/**
	 * Test, and Check all the Parameters
	 *    Which would go to the Detail page	 * 
	 * Will Return a String of error messages, or blank if no problem. 
	 * 
	 * Creation date: (4/1/2005 2:54:21 PM)
	 * Author:		   Teri Walton 
	 */
	private String validateParametersDetail(javax.servlet.http.HttpServletRequest request, 
						   	                javax.servlet.http.HttpServletResponse response) 
	{
		String returnMessage = "";
		try
		{
		  //RESET ALL the Fields
			String[] parameterValues    = (String[]) request.getAttribute("parameterValues");		                                         
					 
			SpecificationViewInquiry  fromHeader = (SpecificationViewInquiry) request.getAttribute("fromHeader");
//			SpecificationViewInquiry  toHeader   = (SpecificationViewInquiry) request.getAttribute("toHeader");
			
		 //-----------------------------------------------------------------------		  
		 //  Specification Number
		 //----------------------------------------------------------------------
		 String specification = request.getParameter("specification");
		 if (specification != null && 
			 !specification.trim().equals(""))
		 {
		 	specification = URLDecoder.decode(specification);
//		 	if (!parameterValues[0].equals(""))
//			   parameterValues[0] = parameterValues[0] +
//											   "<br>";
//			parameterValues[0] = parameterValues[0] +
//								 "Specification ID: " +
//								 specification;
//			parameterValues[1] = parameterValues[1] +
//								 "&inqspecification=" + specification.trim();	
			fromHeader.setSpecificationCode(specification.trim().toUpperCase());
		 }
		 else
		 	returnMessage = "You Must have a Specification Code";
		//-----------------------------------------------------------------------		  
		 //  Revision Date
		 //----------------------------------------------------------------------
		 String specificationDate = request.getParameter("specificationDate");
		 if (specificationDate != null && 
			 !specificationDate.trim().equals(""))
		 {
//			if (!parameterValues[0].equals(""))
//			   parameterValues[0] = parameterValues[0] +
//											   "<br>";
//			parameterValues[0] = parameterValues[0] +
//								 "Revision Date: " +
//								 specificationDate.trim();
//			parameterValues[1] = parameterValues[1] +
//								 "&specificationDate=" + specificationDate.trim();	
			fromHeader.setSpecificationDate(specificationDate.trim());
		 }		
		 fromHeader.setOrderByField("specificationDate");
		 fromHeader.setShowAllRevisions("All");
		 fromHeader.setOrderByStyle("");
		 fromHeader.setShowInventory("off");
		 fromHeader.setStatusCode("All");
		 fromHeader.setCustomerStatus("All");		 
				 		
		  //-----------------------------------------------------------------------		  
		  request.setAttribute("fromHeader", fromHeader);
//		  request.setAttribute("toHeader", toHeader);
//		  request.setAttribute("parameterValues", parameterValues);
	
		}
		catch(Exception e)
		{
			System.out.println("Error in CtlSpecification.validateParameters(request, response: " + e);
		}  	    	
		
	  return returnMessage;		
	}
		/**
		 * Test, and Check all the Parameters
		 *   Depending on What the request Type is, 
		 *   Only Certain fields will be checked.
		 * 
		 *   Inquiry Parameters, which have to be 
		 *   Tested and Checked every time the servlet is called
		 * Build parameterValues: [0] for display -- at the top of the list page
		 *                        [1] for resend  -- used to resend the parameters
		 * 											 from a JSP back to the servlet.
		 * 
		 * Will Return a String of error messages, or blank if no problem. 
		 * 
		 * Creation date: (4/1/2005 2:27:21 PM)	
		 * Author:		   Teri Walton 
		 */
		private String validateParameters(javax.servlet.http.HttpServletRequest request, 
							   	          javax.servlet.http.HttpServletResponse response) 
		{
			String returnMessage = "";
			try
			{
			  //RESET ALL the Fields
				String[] parameterValues    = new String[3];		                                         
						 parameterValues[0] = ""; //Display at the top of the page
						 parameterValues[1] = ""; //Resend parameters
						 parameterValues[2] = ""; //Show Parameters
			
			  //SpecificationViewInquiry will be used for all list and detail pages.
			  			 
				SpecificationViewInquiry  fromHeader = new SpecificationViewInquiry();
				SpecificationViewInquiry  toHeader   = new SpecificationViewInquiry();
              
				request.setAttribute("fromHeader", fromHeader);
				request.setAttribute("toHeader", toHeader);
				request.setAttribute("parameterValues", parameterValues);
              
				//-----------------------------------------------------------------------		  
				//  Request Type
				//----------------------------------------------------------------------
				String   requestType        = (String) request.getAttribute("requestType");
				fromHeader.setRequestType(requestType.trim());
							
				if (requestType.equals("list"))
				{
		   			returnMessage = validateParametersList(request, response);
		   			returnMessage = returnMessage + validateParametersListAttributes(request, response);
		   			if (!returnMessage.equals(""))
			   		  requestType = "inquiry";
				}		
				if (requestType.equals("listInventory"))
				   returnMessage = validateParametersListInventory(request, response);
				if (requestType.equals("detail"))
					returnMessage = validateParametersDetail(request, response);
			
				parameterValues    = (String[]) request.getAttribute("parameterValues");		                                         
					 
				fromHeader = (SpecificationViewInquiry) request.getAttribute("fromHeader");
				toHeader   = (SpecificationViewInquiry) request.getAttribute("toHeader");
			
			  //-----------------------------------------------------------------------		  
			  request.setAttribute("fromHeader", fromHeader);
			  request.setAttribute("toHeader", toHeader);
			  request.setAttribute("parameterValues", parameterValues);
			  request.setAttribute("requestType", requestType);
	
			}
			catch(Exception e)
			{
				System.out.println("Error in CtlSpecification.validateParameters(request, response: " + e);
			}  	    	
			
		  return returnMessage;		
		}
	/**
	 * Test, and Check all the Parameters
	 *    Associated to Attributes
	 *    Which would go to the list page
	 * 
	 * Will Return a String of error messages, or blank if no problem. 
	 * 
	 * Creation date: (4/12/2005 8:34:21 AM)	
	 * Author:		   Teri Walton 
	 */
	private String validateParametersListAttributes(javax.servlet.http.HttpServletRequest request, 
						   	                        javax.servlet.http.HttpServletResponse response) 
	{
		String returnMessage = "";
		try
		{
		  //RESET ALL the Fields
			String[] parameterValues    = (String[]) request.getAttribute("parameterValues");		                                         
					 
			SpecificationViewInquiry  fromHeader = (SpecificationViewInquiry) request.getAttribute("fromHeader");
//			SpecificationViewInquiry  toHeader   = (SpecificationViewInquiry) request.getAttribute("toHeader");

			//-------------------------------------------------------------------------	
			//  Test Each Attribute
			//-------------------------------------------------------------------------
			Vector attCodes = new Vector();
			for (int x = 1; x < 6; x++)
			{
			   String codeName  = "inqAnalyticalCode" + x;
			   String parmValue = request.getParameter(codeName);
			   if (parmValue != null)
			   {
			   	 if (request.getParameter("listAgain") == null &&
			   	     request.getParameter("orderBy") == null)
			       parmValue = URLDecoder.decode(parmValue);
			   	 if (!parmValue.trim().equals("") &&
				   	!parmValue.trim().equals("None"))
			   	 {
			   	  
				    if (!parameterValues[0].equals(""))
					   parameterValues[0] = parameterValues[0] +
					  								 "<br>";
				    parameterValues[0] = parameterValues[0] +
									   "Chosen Attribute: " +
									   parmValue;
				    parameterValues[1] = parameterValues[1] +
									   "&" + codeName + "=" + 
				                       URLEncoder.encode(parmValue.trim());
				 	
				    attCodes.addElement(parmValue);
			   	 }   
			   }			   
			}  
			try
			{ 			
				if (attCodes.size() > 0)
				   fromHeader.setAttributeCodes(attCodes);
			}
			catch(Exception e)
			{}
			   
			
		  request.setAttribute("fromHeader", fromHeader);
//		  request.setAttribute("toHeader", toHeader);
		  request.setAttribute("parameterValues", parameterValues);

		}
		catch(Exception e)
		{
			System.out.println("Error in CtlSpecification.validateParametersListAttributes(request, response: " + e);
		}  	    	
		
	  return returnMessage;		
	}
	/**
	 * Generate information needed to display the
	 *   Detail Page of the Specification ID
	 *    This Page includes
	 * 			Basic Specification Data Data 
	 * 
	 * Creation date: (3/22/2005 10:43:21 AM)
	 * Author:		   Teri Walton
	 */
	private void pageDetail(javax.servlet.http.HttpServletRequest request, 
						   	javax.servlet.http.HttpServletResponse response) 
	{
		try
		{
			SpecificationViewInquiry fromHeader = (SpecificationViewInquiry) request.getAttribute("fromHeader");
			SpecificationViewInquiry toHeader   = (SpecificationViewInquiry) request.getAttribute("toHeader");

		    Vector detailSpec = new Vector();
			if (fromHeader.getSpecificationDate() == null)
			{
			  fromHeader.setShowAllRevisions("Current Revision Only");
			  detailSpec = SpecificationView.findViewBySpec(fromHeader, toHeader);
			} 
			else
			  detailSpec = SpecificationView.findViewBySpecByLot(fromHeader); 

		   	request.setAttribute("detailSpecification", detailSpec);
	   					
		}
		catch(Exception e)
		{
			System.out.println("Error in CtlSpecification.pageDetail(request, response): " + e);
		}  	    	
	  return;		
	}
	/**
	 * Process incoming requests for information
	 * 
	 * 1. Test Security
	 * 2. Decides based on Request Type where to go within the servlet.
	 * 3. Based on error(problem) messages and requestType, chooses which
	 *     JSP is thrown from the servlet.
	 * 
	 * @param request Object that encapsulates the request to the servlet 
	 * @param response Object that encapsulates the response from the servlet
	 */
	public void performTask(javax.servlet.http.HttpServletRequest request, 
							javax.servlet.http.HttpServletResponse response) 
	{
	try
	{
		//****** Main Parameters to always test for ********
		String message = request.getParameter("msg");
	  	if (message == null)
	      message = "";
	    request.setAttribute("message", message);
	    // use this variable to send and retrieve messages within 
	    //    this servlet.
	
		String requestType = request.getParameter("requestType");
	 	if (requestType == null)
	       requestType = "inquiry";
//	    requestType = "detail";
	    request.setAttribute("requestType", requestType);
	     	  
		//********************************************************************
		// Execute security servlet. Not sure where security will come in.
		//********************************************************************
	
		//  Define URL Address, to look in GNPVMENU File, and check security
		//  THIS URL HAS TO BE IN THE GNPVMENU FILE,
		//     you can test with one you already know works.
		String urlAddress = "/web/CtlSpecification";
		//if (requestType.equals("update"))
		//   urlAddress = "/web/CtlInventoryLot?requestType=update";
	
		// Allow Session Variable Access
		HttpSession sess = request.getSession(true);
			
		// Set the Status
		SessionVariables.setSessionttiSecStatus(request,response,"");
		
		// Decide which URL to use, based on Request Type.
		     
		SessionVariables.setSessionttiTheURL(request, response, urlAddress);
			
		// Call the security Servlet
	  	getServletConfig().getServletContext().
	    getRequestDispatcher("/TTISecurity" ).
		include(request, response);
	
		// Decision of whether or not to use the Inq, List or Detail
		String thisStatus = SessionVariables.getSessionttiSecStatus(request,response);
		if (!SessionVariables.getSessionttiSecStatus(request,response).equals(""))
		{
			response.sendRedirect("/web/TreeNetInq?msg=" +  
					SessionVariables.getSessionttiSecStatus(request,response));
			return;
		}	    
		// remove the Status and the Url
		sess.removeAttribute("ttiTheURL");
		sess.removeAttribute("ttiSecStatus");
	//********************************************************************
	//********************************************************************
	  //**********  Set a Default Location to go to.
	    String sendTo = "/JSP/Specification/inquirySpecifications.jsp?";
	    
	  //-----------------------------------------------------------------
	  // Based on Request Type Decide which methods to call 
	  //
	  if (!requestType.equals("inquiry"))
	  {
		//-------------------------------------------------------------
		//  Unless RequestType = inquiry ALWAYS call validate Parameters
		String returnMessage = validateParameters(request, response);
		request.setAttribute("message", returnMessage);
		//-------------------------------------------------------------
		requestType = (String) request.getAttribute("requestType");
					  	
		//------------------------------------------------------------
		//  Going to Detail Page
		if (requestType.equals("detail"))
		{
		   if (returnMessage.equals(""))
		   {	
		   // use this to get the revisions	
		   pageList(request, response);
		   // get the detail information
		   pageDetail(request, response);
		   }
		   sendTo = "/JSP/Specification/detailSpecification.jsp?";
		}
		if (requestType.equals("list") ||
		    requestType.equals("listInventory"))
		{
			pageList(request, response);
			if (requestType.equals("listInventory"))
			  sendTo = "/JSP/Specification/listInventory.jsp?";
			else
			  sendTo = "/JSP/Specification/listSpecifications.jsp?";
	  	}
	  }
	  // Retrieve the request Type, it could change within 
	  //  methods called above
	  requestType = (String) request.getAttribute("requestType");
	  if (requestType.equals("inquiry"))
	  { 
		 sendTo = "/JSP/Specification/inquirySpecifications.jsp?";
	  }
      //----  Retrieve Message  ----
      message = (String) request.getAttribute("message"); 
		 
			//***** Go to the JSP *****//
			getServletConfig().getServletContext().
				getRequestDispatcher(sendTo +
					"msg=" + message).
				forward(request, response);
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		System.out.println(theException);
		
		System.out.println("---------------------");
		System.out.println("CtlSpecification Called By: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
		ParameterMessageBean.printParameters(request);
		System.out.println("---------------------");	
		
		theException.printStackTrace();
	}
	}


	/**
	 * Generate information needed to display the
	 *   List Page of the Specification ID's
	 * 
	 * Creation date: (3/22/2005 10:45:21 AM)
	 * Author:		   Teri Walton
	 */
	private void pageList(javax.servlet.http.HttpServletRequest request, 
						  javax.servlet.http.HttpServletResponse response) 
	{
		try
		{
			Vector reportList = new Vector();
			SpecificationViewInquiry fromHeader = (SpecificationViewInquiry) request.getAttribute("fromHeader");
			SpecificationViewInquiry toHeader   = (SpecificationViewInquiry) request.getAttribute("toHeader");
			String requestType = (String) request.getAttribute("requestType");
//			
		  // Testing Information Will Need to use Another Actual Method when ready
		  if (requestType.equals("listInventory"))
		    reportList = SpecificationView.findViewBySpecByLot(fromHeader);
		  else
		  	reportList = SpecificationView.findViewBySpec(fromHeader, toHeader);
			
			request.setAttribute("reportList", reportList);

		}
		catch(Exception e)
		{
			System.out.println("Error in CtlSpecification.pageList(request, response): " + e);
		}  	    	
	  return;		
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
 * Initializes the servlet.
 */
public void init() {
	// insert code to initialize the servlet here

}
}
