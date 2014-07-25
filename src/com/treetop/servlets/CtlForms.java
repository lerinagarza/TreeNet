package com.treetop.servlets;

import com.treetop.data.*;
import com.treetop.utilities.html.*;
import com.treetop.*;
import java.math.*;
import java.util.*;

import javax.servlet.http.*;

/**
 * Controlling Servlet for creating/inputing to Forms.
 *    it will create the HTML for display.
 *    edit input information
 *    
 * Creation date: (8/11/2003 4:40:04 PM)
 * @author: Teri Walton
 */

public class CtlForms extends javax.servlet.http.HttpServlet 
{
	/**
	 * This Method will return:
	 * 		 a Vector of Vectors
	 * 
	 * 	  Vector outline
	 * 		Vector - 1 Vector element for each form
	 * 		  element Vector - 3 Vector elements for each form	
	 * 						   0 - form headings   (FormHeadings Class) 
	 * 						   1 - form definition (FormDefinition Class)
	 * 						   2 - form data	   (FormData Class)
	 * 
	 * Example of how Used:
	 * 		Will determine a list of Forms,
	 *      WHICH have this specific DataType
	 * 
	 * 		Will Call the method to Build a Vector by form from
	 *      the list of forms numbers.
	 * 
	 * 		Set each of these vectors into a vector to return.
	 * 
	 * Will set this vector into a request attribute
	 * 
	 * Creation date: (10/13/2004 4:45:48 PM)
	 */
	public static void templateBuildAllByDataType(javax.servlet.http.HttpServletRequest request, 
													javax.servlet.http.HttpServletResponse response,
													String template,
												    String dataType,
												    String dataValue) 
	{
		Vector returnVector = new Vector();
		try
		{	
			template = "";
			
			if (dataType != null &&
			    !dataType.equals(""))
			{
			 	FormDefinition setType = new FormDefinition();
			 	setType.setDataType(dataType);
			 	
				String[] userGroups  = SessionVariables.getSessionttiUserGroups(request,response);
				if (userGroups != null && 
					userGroups.length == 1 &&
					userGroups[0].trim().equals(""))
				   userGroups = null;
	
				Vector returnDefinitions = FormDefinition.
						   findDefinitionByType(setType,
												SessionVariables.getSessionttiUserRoles(request,response),
												userGroups,
												SessionVariables.getSessionttiProfile(request,response));				
				try
				{
					for (int x = 0; x < returnDefinitions.size(); x++)
					{
						FormHeader getFormNumber = (FormHeader) returnDefinitions.elementAt(x);
						templateBuildByFormByDataValue(request,
													   response,
													   template,
													   getFormNumber.getFormNumber(),
													   dataType,
													   dataValue);
						Vector returnValue = (Vector) request.getAttribute("vectorByForm");
						if (returnValue.size() > 0)
							returnVector.addElement(returnValue);
					}
						
				}
				catch(Exception e)
				{
				//	System.out.println("What is the Problem? " + e);
				}				
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception caught within the " +
							   "CtlForms.templateBuildAllByDataType" +
							   "(String, String, String): " + 
							   e);
		}	
		request.setAttribute("listAllForms", returnVector);
		return;
	}
	/**
	 * This Method will return:
	 * 		 a vector of 3 Vectors
	 * 			elements:
	 * 				0-FormHeadings
	 * 				1-FormDefinition
	 * 				3-FormDataClasses for one specific FormNumber 
	 *          for a VERY specific dataType and Value
	 * 
	 * Example of how Used:
	 * 		Send in FormNumber   23
	 * 		        DataType     broker
	 *              Value        27
	 *   Will Return Vector of FormData Classes of every 
	 *       transaction from formNumber 23 with Broker #27
	 *       in ANY of the fields.
	 * 
	 * Will SET this vector into a request attribute  
	 * 
	 * Creation date: (10/13/2004 4:45:48 PM)
	 */
	public static void templateBuildByFormByDataValue(javax.servlet.http.HttpServletRequest request, 
													  javax.servlet.http.HttpServletResponse response,
													  String template,
										              Integer formNumber,
												      String dataType,
												      String dataValue) 
	{
		String[] parameterValues = (String[]) request.getAttribute("parameterValues");
		// The attributeVector, will be set in a request.setAttribute Fashion.
		Vector vectorByForm = new Vector();
		//    This Vector will include 3 other Vectors:
		//		0 - formHeadings
		Vector headingVector    = new Vector();
		//		1 - formDefinition
		Vector definitionVector = new Vector();
		//      2 - formData
		Vector dataVector       = new Vector();
		try
		{
//			formNumber = new Integer("20");
			request.setAttribute("formNumber", formNumber);
//			dataType   = "broker";
//			dataValue  = "252";
			if (template == null ||
				template.equals(""))
				template   = "list";
			try
			{
				headingVector = FormHeadings.findHeadingsByForm(formNumber);	
			}
			catch(Exception e)
			{
				
			}
			try
			{	
				definitionVector = retrieveFormDefinition(request,
														  response,
														  template);
			}
			catch(Exception e)
			{
				
			}
			try
			{
				if (definitionVector.size() > 0)
				{
					FormData dataClass = new FormData();
					dataClass.setFormNumber(formNumber);
					dataClass.setDataType(dataType);
					if (dataType.equals("broker"))
					{
					   dataClass.setDataCode("NU");
					   dataClass.setDataNumeric(new BigDecimal(dataValue));
					}
					if (dataType.equals("customer"))
					{
						dataClass.setDataCode("TX");
						dataClass.setDataText(dataValue);
					}
					dataVector = FormData.findDataByValue(dataClass, 
														  definitionVector);
					try
					{
						if (dataType.equals("broker") ||
							dataType.equals("customer"))
						{
							for (int x = 0; x < definitionVector.size(); x++)
							{
								FormDefinition thisRecord = (FormDefinition) definitionVector.elementAt(x);
								String fromName = "&from" + thisRecord.getColumnNumber();
								String toName   = "&to" + thisRecord.getColumnNumber();

								if (dataType.equals(thisRecord.getDataType().trim()))
								{
									if (dataType.equals("customer"))
									{
										String coFieldValue = "";
										String custFieldValue = "";
										int    underscorePlace = dataValue.indexOf("_");
										if (underscorePlace > 0)
										{
											try
											{
												coFieldValue   = dataValue.substring(0, underscorePlace).trim();
												custFieldValue = dataValue.substring((underscorePlace + 1), dataValue.length()).trim();
											}
											catch(Exception e)
											{
											}
										}										
										parameterValues[1] = parameterValues[1] +
												fromName + "Co" +
												"=" + coFieldValue +
												fromName + "Cust" +
												"=" + custFieldValue + 
												"&to"    + thisRecord.getColumnNumber() +									
												"=" + dataValue +
												toName + "Co" +
												"=" + coFieldValue +
												toName + "Cust" +
												"=" + custFieldValue;
									}
									else
									{
										parameterValues[1] = parameterValues[1] +
												fromName +
												"=" + dataValue +
												toName +									
												"=" + dataValue;
									}
								}
							}
						
						
						}
					}
					catch(Exception e)
					{
						
					}
				}
			}
			catch(Exception e)
			{
			}
//			if (dataVector.size() > 0)
//			{
				vectorByForm.addElement(headingVector);
				vectorByForm.addElement(definitionVector);
				vectorByForm.addElement(dataVector);
//			}
		}
		catch(Exception e)
		{
			System.out.println("Exception caught within the " +
			                   "CtlForms.templateBuildVectorByFormByDataValue" +
			                   "(String, Integer, String, String): " + 
			                   e);
		}	
		request.setAttribute("parameterValues", parameterValues);
		request.setAttribute("vectorByForm", vectorByForm);
		return;
	}
	/**
	 *  Used to build an Input Box.
	 * This Method will receive in:
	 *   fieldValue - If the field already has a value, send that in. 
	 *   thisForm    - will give me details on how it should be displayed
	 *   extraName   - to be used when building the function on an input.
	 *
	 * Finally to return a string, which is has HTML coded
	 *    into it.
	 *
	 * Creation date: (9/15/2003 10:37:48 AM)
	 */
	public static String buildStandardTime(String fieldValue,
		                             FormDefinition thisForm, 
		                             String extraName) 
	{
		String htmlTime = "";
		
		if (fieldValue == null ||
			fieldValue.trim().equals("null") ||
			fieldValue.trim().equals("00:00:00") ||
			fieldValue.trim().length() == 0)
		   fieldValue = "00:00:00";
	       java.sql.Time sqlValue = java.sql.Time.valueOf("00:00:00");
		
		String fieldName = extraName + thisForm.getColumnNumber();
		 

	    try
	    {
		    sqlValue = java.sql.Time.valueOf(fieldValue);
	    }
	    catch(Exception e)
	    {
		    System.out.println("Problem occured when creating SQL value for Time: " +
			         e + " CtlForms.buildStandardTime()");
	    }
	    
	     
	    String newTime = HTMLHelpersInput.inputBoxTime3Sections(fieldName,
		                                                thisForm.getHeadingLong().trim(),
		                                                sqlValue.toString());
	
	    htmlTime = newTime;
		return htmlTime;
	}
	/**
	 * Will decide if the fields are OK.
	 *   Anything that would come from the Inquiry Page.
	 *   For a specific Form Number -- 
	 * 
	 *   While doing that it will create a vector of
	 *   the fields, using the High, Low fields in the column Class.
	 *
	 * Finally to return a string, which is the error message
	 *
	 * Creation date: (7/20/2004 10:42:48 AM)
	 */
	private String validateInquiryParameters(javax.servlet.http.HttpServletRequest request,
				 			                 javax.servlet.http.HttpServletResponse response) 
	{
		String anyProblem = "";
	try
	{
		//** Reset this Vector **//
		// Use this vector to send the inquiry input fields to the Class.
		//  It will use these fields to filter the SQL statement.
		
	   //--  Will Include each element of the Inquiry Definition Vector	
		Vector validInquiryVector = new Vector(); 
	   //--  Display / Resend Parameters received into this Servlet	
	    String[] parameterValues  = new String[2];		                                         
	    parameterValues[0] = ""; //Display at the top of the page
	    parameterValues[1] = ""; //Resend parameters
	    request.setAttribute("parameterValues", parameterValues);
	
		Vector inquiryDefinition = retrieveFormDefinition(request, response, "");

		request.setAttribute("inquiryDefinition", inquiryDefinition);
		FormDefinition headingInfo = new FormDefinition();
		int definitionSize = 0;
		try
		{
			headingInfo = (FormDefinition) inquiryDefinition.elementAt(0);	
			definitionSize = inquiryDefinition.size();
		}
		catch(Exception e)
		{
		}
		anyProblem = validateInquiryBasicParameters(request, 
		                                            response,
		                                            headingInfo);

		parameterValues = (String[]) request.getAttribute("parameterValues");
	//--------------------------------------------------------------	
    // Set up Element 0 of the Vector		
		FormDefinition inquiryClass           = new FormDefinition();
		
		inquiryClass.setFormNumber(headingInfo.getFormNumber());
		inquiryClass.setColumnNumber(new Integer(0));
		inquiryClass.setDataCode("");
		inquiryClass.setDataType("");
//		inquiryClass.setOrderByColumn(headingInfo.getOrderByColumn());
		inquiryClass.setOrderBySequence(headingInfo.getOrderBySequence());
		inquiryClass.setOrderByStyle(headingInfo.getOrderByStyle());
		inquiryClass.setShowOnInquiry(headingInfo.getShowOnInquiry());
		
		validInquiryVector.addElement(inquiryClass);
    //----------------------------------------------------------------


		//** ORDER THE DATA **//
		String orderBy = request.getParameter("orderBy");
		if (orderBy == null ||
			orderBy.equals("null"))
		   orderBy = "";
		String orderStyle = request.getParameter("orderStyle");
		if (orderStyle == null ||
			orderStyle.equals("null"))
		   orderStyle = "";
		    
		request.setAttribute("orderBy", orderBy);
		request.setAttribute("orderStyle", orderStyle);	
	  //***********************************************
	  //  Run through each element in the definition
	  //    Can inquiry on any or all
	  //***********************************************
	    int checkForOrderBy = 9999999;
	  if (definitionSize > 0)
	  {  
	    for (int x = 0; x < definitionSize; x++)
	    {
		    inquiryClass           = new FormDefinition();
			FormDefinition inquiryDefinitionClass = (FormDefinition) inquiryDefinition.elementAt(x);
			String fromName  = "from"  + inquiryDefinitionClass.getColumnNumber();
			String toName    = "to"    + inquiryDefinitionClass.getColumnNumber();
			inquiryClass.setShowOnInquiry(inquiryDefinitionClass.getShowOnInquiry());
	        inquiryClass.setFormNumber(inquiryDefinitionClass.getFormNumber());
			inquiryClass.setColumnNumber(inquiryDefinitionClass.getColumnNumber());
			inquiryClass.setDataCode(inquiryDefinitionClass.getDataCode());
			inquiryClass.setDataType(inquiryDefinitionClass.getDataType());
			inquiryClass.setDataNumber(inquiryDefinitionClass.getDataNumber());
			inquiryClass.setSortOrderSequence(inquiryDefinitionClass.getSortOrderSequence());
			inquiryClass.setSortOrderStyle(inquiryDefinitionClass.getSortOrderStyle());
		    inquiryClass.setFormulaNumber(inquiryDefinitionClass.getFormulaNumber());
		    inquiryClass.setSearchType(inquiryDefinitionClass.getSearchType());
		    
		    if (inquiryDefinitionClass.getColumnNumber().toString().trim().equals(orderBy))
		    {//Sort Order Information
			    inquiryClass.setSortOrderSequence(new Integer(1));
			    inquiryClass.setSortOrderStyle(orderStyle);
//			    if(orderStyle.equals(""))
//			       orderStyle = "Desc";
//			    else
//			       orderStyle = "";
		    }
		    else
		    {
			    if (!orderBy.trim().equals("") &&
			        !orderBy.trim().equals("0"))
			    {
				    inquiryClass.setSortOrderSequence(new Integer(0));
				    inquiryClass.setSortOrderStyle("");
			    }
			    else
			    {
				    if (inquiryDefinitionClass.getSortOrderSequence().intValue() > 0 &&
					    inquiryDefinitionClass.getSortOrderSequence().intValue() < checkForOrderBy)
				    {
					   checkForOrderBy = inquiryDefinitionClass.getSortOrderSequence().intValue();
					   request.setAttribute("orderBy", inquiryDefinitionClass.getColumnNumber().toString());						   
		//		       orderBy = inquiryDefinitionClass.getColumnNumber().toString();
				    }
			    }
		    }
			
			    
			if (inquiryDefinitionClass.getFormula() == null)
			   inquiryClass.setFormula("");
			else
			   inquiryClass.setFormula(inquiryDefinitionClass.getFormula());
	
			if (inquiryDefinitionClass.getOperand1() == null)
			   inquiryClass.setOperand1(new Integer(0));
			else
			   inquiryClass.setOperand1(inquiryDefinitionClass.getOperand1());
			   
			if (inquiryDefinitionClass.getOperand2() == null)
			   inquiryClass.setOperand2(new Integer(0));
			else
			   inquiryClass.setOperand2(inquiryDefinitionClass.getOperand2());
		
		if ((request.getParameter(fromName) != null &&
			request.getParameter(fromName).trim().length() != 0 &&
			!request.getParameter(fromName).trim().toLowerCase().equals("none")))
		{
			inquiryClass.setStatusCode("R");
			parameterValues[1] = parameterValues[1] +
						  "&" + fromName + "=" + request.getParameter(fromName);
					
			parameterValues[0] = parameterValues[0] +
							   inquiryDefinitionClass.getHeadingLong().trim() +
							   ": <i>" +
							   request.getParameter(fromName) +
							   "</i>&nbsp;<br>"; 
					
		}
		
		if ((request.getParameter(toName) != null &&
			request.getParameter(toName).trim().length() != 0) &&
			!request.getParameter(toName).trim().toLowerCase().equals("none"))
		{
			inquiryClass.setStatusCode("R");
			parameterValues[1] = parameterValues[1] +
						  "&" + toName   + "=" + request.getParameter(toName);
			parameterValues[0] = parameterValues[0] +
			                   inquiryDefinitionClass.getHeadingLong().trim() +
			                   ": <i>" +
			                   request.getParameter(toName) +
			                   "</i>&nbsp;<br>"; 
		}
		if ((inquiryClass.getStatusCode() != null &&
			 inquiryClass.getStatusCode().equals("R")) ||
			inquiryDefinitionClass.getDataType().trim().toLowerCase().equals("customer"))
		{
			//************//		
			//** Number **//
			if (inquiryDefinitionClass.getDataCode().trim().equals("NU"))
			{			
			   BigDecimal fromValue = new BigDecimal("0");
			   BigDecimal toValue   = new BigDecimal("0");
			   if (request.getParameter(fromName) != null &&
			       !request.getParameter(fromName).toLowerCase().equals("none"))
			   {
			   		String fromError = ValidateFields.validateBigDecimal(request.getParameter(fromName)); 
			   		if (request.getParameter(fromName) != null &&
			       		fromError.equals(""))
			   		{    
			       		fromValue = new BigDecimal(request.getParameter(fromName));
			       		inquiryClass.setNumericLow(fromValue);
			   		}
			   		anyProblem = anyProblem + fromError;
			   }
			   if (request.getParameter(toName) != null &&
				   !request.getParameter(toName).toLowerCase().equals("none"))
			   {
			   		String toError   = ValidateFields.validateBigDecimal(request.getParameter(toName));
			   		if (request.getParameter(toName) != null &&
				   		toError.equals(""))
			   		{
				   		toValue = new BigDecimal(request.getParameter(toName));
				   		inquiryClass.setNumericHigh(toValue);
			   		}
			   		anyProblem = anyProblem + toError;
			   }
				   
			  if (!fromValue.toString().equals("0") &&
				   !toValue.toString().equals("0"))
			   {
				   anyProblem = anyProblem +
							   ValidateFields.testRangeBigDecimal(fromValue,
															      toValue);           
			   }
			}
			//**********//
			//** Text **//
			if (inquiryDefinitionClass.getDataCode().trim().equals("TX"))
			{
				String fromValue = request.getParameter(fromName);
				if (fromValue == null)
				   fromValue = "";
				String toValue   = request.getParameter(toName);
				if (toValue == null)
				   toValue = "";
				if (inquiryDefinitionClass.getDataType().trim().toLowerCase().equals("customer"))
				{
					String companyValue = fromName + "Co";
					String custValue    = fromName + "Cust";
					String testFromCo = request.getParameter(companyValue);
					if (testFromCo == null)
					   testFromCo = "";
					String testFromCust = request.getParameter(custValue);
					if (testFromCust == null)
					   testFromCust = "";
					   
				    fromValue = testFromCo +
					            "_" +
					            testFromCust;
					            
					if (!fromValue.trim().equals("_"))
					{
						inquiryClass.setStatusCode("R");
						parameterValues[1] = parameterValues[1] +
									  "&" + companyValue   + "=" + testFromCo +
									  "&" + custValue      + "=" + testFromCust;
						parameterValues[0] = parameterValues[0] +
											 "From " +
											 inquiryDefinitionClass.getHeadingLong().trim() +
											 ": <i>" +
											 fromValue +
											 "</i>&nbsp;<br>";
					}
					                
					companyValue = toName + "Co";
					custValue    = toName + "Cust";
					String testToCo = request.getParameter(companyValue);
					if (testToCo == null)
					   testToCo = "";
					String testToCust = request.getParameter(custValue);
					if (testToCust == null)
					   testToCust = "";
					   
					toValue = testToCo +
							  "_" +
							  testToCust;	
							  
					if (!toValue.trim().equals("_"))
					{
						inquiryClass.setStatusCode("R");
						parameterValues[1] = parameterValues[1] +
									  "&" + companyValue   + "=" + testToCo +
									  "&" + custValue      + "=" + testToCust;
						parameterValues[0] = parameterValues[0] +
											 "To " +
											 inquiryDefinitionClass.getHeadingLong().trim() +
											 ": <i>" +
											 toValue +
											 "</i>&nbsp;<br>";
					} 				        							                					
				}
				if (!fromValue.toString().equals("") ||
					!toValue.toString().equals(""))
				{
					// May want to add in a check about the range of two text fields.          
				}			
				
			    inquiryClass.setTextLow(fromValue);
		        inquiryClass.setTextHigh(toValue);		
		    }
			//**********//
			//** Date **//
			if (inquiryDefinitionClass.getDataCode().trim().equals("DT"))
			{
			    String[] returnValuesFrom = validateDate(request.getParameter(fromName));
				if (returnValuesFrom[0] != null)
				   anyProblem = anyProblem + returnValuesFrom[0];
				String[] returnValuesTo   = validateDate(request.getParameter(toName));
				if (returnValuesTo[0] != null)
					   anyProblem = anyProblem + returnValuesTo[0];
		
				if (returnValuesFrom[0] == null &&
					returnValuesTo[0] == null &&
					returnValuesFrom[1] != null &&
					returnValuesTo[1] != null)
				{
					anyProblem = anyProblem +
								ValidateFields.testRangeDate(java.sql.Date.valueOf(returnValuesFrom[1]),
															 java.sql.Date.valueOf(returnValuesTo[1]));           
			    	   
				}
					// Get/Check the Date Fields
				if (anyProblem.equals(""))
				{
				   //IF no problems, set these date fields into the first
				   //   element of the vector
					inquiryClass.setDateLow(java.sql.Date.valueOf(returnValuesFrom[1]));
					inquiryClass.setDateHigh(java.sql.Date.valueOf(returnValuesTo[1]));
		       }
			}
			
		 } 
			//** Time **//
			if (inquiryDefinitionClass.getDataCode().trim().equals("TM") &&
			   ((request.getParameter("hour" + fromName) != null &&
				 request.getParameter("hour" + fromName).trim().length() != 0) ||
			    (request.getParameter("min" + fromName) != null &&
				 request.getParameter("min" + fromName).trim().length() != 0) ||
			    (request.getParameter("sec" + fromName) != null &&
				 request.getParameter("sec" + fromName).trim().length() != 0) ||
			    (request.getParameter("hour" + toName) != null &&
				 request.getParameter("hour" + toName).trim().length() != 0) ||
			    (request.getParameter("min" + toName) != null &&
				 request.getParameter("min" + toName).trim().length() != 0) ||
			    (request.getParameter("sec" + toName) != null &&
				 request.getParameter("sec" + toName).trim().length() != 0)))
			{
			   inquiryClass.setStatusCode("R");
			   String fromTime = HTMLHelpers.combineTime3Sections(
				                              request.getParameter("hour" + fromName),
				                              request.getParameter("min" + fromName),
				                              request.getParameter("sec" + fromName),
											  "Y");
			   
			   String toTime   = HTMLHelpers.combineTime3Sections(
				                              request.getParameter("hour" + toName),
				                              request.getParameter("min" + toName),
				                              request.getParameter("sec" + toName),
											  "Y");
			   
				String[] returnValuesFrom = validateTime(fromTime);
				if (returnValuesFrom[0] != null)
				   anyProblem = anyProblem + returnValuesFrom[0];
				String[] returnValuesTo   = validateTime(toTime);
				if (returnValuesTo[0] != null)
					   anyProblem = anyProblem + returnValuesTo[0];
		
				if (returnValuesFrom[0] == null &&
					returnValuesTo[0] == null)
				{
					if (!fromTime.equals("00:00:00") &&
						!toTime.equals("00:00:00"))
					{	
					  anyProblem = anyProblem +
								ValidateFields.testRangeTime(java.sql.Time.valueOf(returnValuesFrom[1]),
															 java.sql.Time.valueOf(returnValuesTo[1]));
					}
			    	   
				}
					// Get/Check the Date Fields
				if (anyProblem.equals(""))
				{
				   //IF no problems, set these date fields into the first
				   //   element of the vector
					if (!fromTime.equals("00:00:00"))
					  inquiryClass.setTimeLow(java.sql.Time.valueOf(returnValuesFrom[1]));
					if (!toTime.equals("00:00:00"))
					  inquiryClass.setTimeHigh(java.sql.Time.valueOf(returnValuesTo[1]));
			   }
			}
			   
	
		    validInquiryVector.addElement(inquiryClass);
	   }
	  }
	   request.setAttribute("validInquiryVector", validInquiryVector);
	   request.setAttribute("parameterValues", parameterValues);
	}
	catch(Exception e)
	{
		System.out.println("Problem within CtlForms.ValidateInquiryParameters" +
			               "(request, response).  " + e);  
	}
		return anyProblem;
	}
	/**
	 * Will decide if the fields are OK.
	 *   Will create a Vector of Elements (validBasicVector)
	 *   This Vector will include:
	 *      0 Element) Sort Information
	 *      1 Element) Transaction Number (Null if not chosen)
	 *      2 Element) Transaction (Effective) Date (Null if not chosen)
	 *      3 Element) Transaction User (Null if not chosen)
	 *      4 Element) Last Update Date (Null if not chosen)
	 *      5 Element) Last Update Time (Null if not chosen) 
	 *   
	 * Will to return a string, which will be an error message IF there
	 *    is a problem.
	 * 
	 *  Put INTO REQUEST ATTRIBUTES From this Method:
	 *       validBasicVector   = (For Use in finding data)
	 *       parameterValues    = String Array for resending Data.
	 *
	 * Creation date: (9/8/2004 9:30:48 AM)
	 */
	private String validateInquiryBasicParameters(javax.servlet.http.HttpServletRequest request,
				 			                      javax.servlet.http.HttpServletResponse response,
				 			                      FormDefinition headingInfo) 
	{
		String anyProblem = "";
	try
	{
		//** Reset Vector **//
		Vector validBasicVector   = new Vector();
		//  Retrieve ParameterValues String Array (To Add To It)
		String[] parameterValues = (String[]) request.getAttribute("parameterValues");	
		
		//----- BUILD VECTOR ------//
		try
		{
			String[] setOrderBySequence = headingInfo.getOrderBySequence();
			String[] setOrderByStyle    = headingInfo.getOrderByStyle();
			// Create an Empty Class to put information into.
			FormDefinition basicInquiryClass = new FormDefinition();

			parameterValues[1] = parameterValues[1] +
				  "&formNumber=" + headingInfo.getFormNumber();
			//--------------------------------------------	
			// ELEMENT 0 of VECTOR			
			basicInquiryClass.setFormNumber(headingInfo.getFormNumber());
			basicInquiryClass.setColumnNumber(new Integer(0));
			validBasicVector.addElement(basicInquiryClass);
		
        //--------------------------------------------	
		// ELEMENT 1 of VECTOR			
			basicInquiryClass = new FormDefinition();
			basicInquiryClass.setFormNumber(headingInfo.getFormNumber());
			basicInquiryClass.setColumnNumber(new Integer(0));
			basicInquiryClass.setDataCode("NU");
			basicInquiryClass.setDataType("number");
			basicInquiryClass.setShowOnInquiry(headingInfo.getShowOnInquiry());
			try
			{
				basicInquiryClass.setSortOrderSequence(new Integer(setOrderBySequence[0]));
			}
			catch(Exception e)
			{
			}
			basicInquiryClass.setSortOrderStyle(setOrderByStyle[0]);
			
			String testValue = "N";

			if (request.getParameter("fromTransactionNumber") != null &&
			    !request.getParameter("fromTransactionNumber").equals(""))
			{
			   try
			   {
			   	  anyProblem = ValidateFields.validateInteger(request.getParameter("fromTransactionNumber"));
			      basicInquiryClass.setNumericLow(new BigDecimal(request.getParameter("fromTransactionNumber")));
				  parameterValues[1] = parameterValues[1] +
					  "&fromTransactionNumber=" + 
					  	request.getParameter("fromTransactionNumber");
				  parameterValues[0] = parameterValues[0] +
					"From Transaction Number: " + 
					request.getParameter("fromTransactionNumber") +
					"<br>";
				  testValue = "Y";
			   }
			   catch(Exception e)
			   {
			   }
			}
			if (request.getParameter("toTransactionNumber") != null &&
				!request.getParameter("toTransactionNumber").equals(""))
			{
			   try
			   {
				  anyProblem = ValidateFields.validateInteger(request.getParameter("toTransactionNumber"));
				  basicInquiryClass.setNumericHigh(new BigDecimal(request.getParameter("toTransactionNumber")));
				  parameterValues[1] = parameterValues[1] +
					  "&toTransactionNumber=" + 
					  request.getParameter("toTransactionNumber");
				  parameterValues[0] = parameterValues[0] +
				  		"To Transaction Number: " + 
				  		request.getParameter("toTransactionNumber") + 
				  		"<br>";
				  testValue = "Y";
			   }
			   catch(Exception e)
			   {
			   }
			}	
			   
			if (((request.getParameter("fromTransactionNumber") != null &&
			     !request.getParameter("fromTransactionNumber").equals("")) &&
			     (request.getParameter("toTransactionNumber") != null &&
				 !request.getParameter("toTransactionNumber").equals(""))) &&			   
				  anyProblem.equals(""))
			{
				anyProblem = anyProblem +
							ValidateFields.testRangeBigDecimal((new BigDecimal(request.getParameter("fromTransactionNumber"))),
															   (new BigDecimal(request.getParameter("toTransactionNumber"))));           
			}			
			if (anyProblem.equals("") &&
			    testValue.equals("Y"))
			  basicInquiryClass.setStatusCode("R");
			  					
			validBasicVector.addElement(basicInquiryClass);
		
		//--------------------------------------------	
		// ELEMENT 2 of VECTOR	
			basicInquiryClass = new FormDefinition();
			basicInquiryClass.setFormNumber(headingInfo.getFormNumber());
			basicInquiryClass.setColumnNumber(new Integer(0));
			basicInquiryClass.setDataCode("DT");
			basicInquiryClass.setDataType("date");
			basicInquiryClass.setShowOnInquiry(headingInfo.getShowOnInquiry());
			try{
				basicInquiryClass.setSortOrderSequence(new Integer(setOrderBySequence[1]));
			}
			catch(Exception e)
			{
			}			basicInquiryClass.setSortOrderStyle(setOrderByStyle[1]);
			
			
			String[] returnValuesFromEff = validateDate(request.getParameter("fromEffectiveDate"));
			if (returnValuesFromEff[0] != null)
			   anyProblem = anyProblem + returnValuesFromEff[0];
			String[] returnValuesToEff   = validateDate(request.getParameter("toEffectiveDate"));
			if (returnValuesToEff[0] != null)
				   anyProblem = anyProblem + returnValuesToEff[0];
			   
			if (returnValuesFromEff[0] == null &&
				returnValuesToEff[0] == null &&
				returnValuesFromEff[1] != null &&
				returnValuesToEff[1] != null)
			{
				anyProblem = anyProblem +
							ValidateFields.testRangeDate(java.sql.Date.valueOf(returnValuesFromEff[1]),
														 java.sql.Date.valueOf(returnValuesToEff[1]));           
			}    	   
			// Get/Check the effective Date Fields
			if (anyProblem.equals(""))
			{
			   //IF no problems, set these date fields into the first
			   //   element of the vector
			   if (returnValuesFromEff[1] != null)
			   {
				 basicInquiryClass.setDateLow(java.sql.Date.valueOf(returnValuesFromEff[1]));
				 basicInquiryClass.setStatusCode("R");			
			   }
			   if (returnValuesToEff[1] != null)
			   {
				 basicInquiryClass.setDateHigh(java.sql.Date.valueOf(returnValuesToEff[1]));
				 basicInquiryClass.setStatusCode("R");
			   }			
	
				if (returnValuesFromEff[1] != null ||
					returnValuesToEff[1] != null)
				{
					if (returnValuesFromEff[1] != null &&
						returnValuesToEff[1] != null &&
						returnValuesFromEff[1].equals(returnValuesToEff[1]))
					{
						parameterValues[0] = parameterValues[0] +
								 "Effective Date: <i>" +
								 request.getParameter("fromEffectiveDate") +
								 "</i>&nbsp;<br>";
						parameterValues[1] = parameterValues[1] +
							  "&fromEffectiveDate=" + request.getParameter("fromEffectiveDate") +
							  "&toEffectiveDate=" + request.getParameter("toEffectiveDate");
					}
					else
					{
						if (returnValuesFromEff[1] != null &&
							returnValuesToEff[1] != null)
						{	
							parameterValues[0] = parameterValues[0] +
								 "Effective Dates: <i>" +
								 request.getParameter("fromEffectiveDate") +
								 " to " +
								 request.getParameter("toEffectiveDate") +
								 "</i>&nbsp;<br>";
							parameterValues[1] = parameterValues[1] +
										  "&fromEffectiveDate=" + request.getParameter("fromEffectiveDate") +
										  "&toEffectiveDate=" + request.getParameter("toEffectiveDate");
				             
						}
						else
						{
							if (returnValuesFromEff[1] != null &&
								returnValuesToEff[1] == null)
							{
								parameterValues[0] = parameterValues[0] +
								   "Effective Date: <i> After " +
								   request.getParameter("fromEffectiveDate") +
								   "</i>&nbsp;<br>";
								parameterValues[1] = parameterValues[1] +
											  "&fromEffectiveDate=" + request.getParameter("fromEffectiveDate");
							   
							}
							if (returnValuesFromEff[1] == null &&
								returnValuesToEff[1] != null)
							{
								parameterValues[0] = parameterValues[0] +
								   "Effective Date: <i> Before " +
								   request.getParameter("toEffectiveDate") +
								   "</i>&nbsp;<br>";
								parameterValues[1] = parameterValues[1] +
											  "&toEffectiveDate=" + request.getParameter("toEffectiveDate");
							}						 			
						}
					}
				}
			}
			validBasicVector.addElement(basicInquiryClass);
			//--------------------------------------------	
			// ELEMENT 3 of VECTOR		
				basicInquiryClass = new FormDefinition();
				basicInquiryClass.setFormNumber(headingInfo.getFormNumber());
				basicInquiryClass.setColumnNumber(new Integer(0));
				basicInquiryClass.setDataCode("TX");
				basicInquiryClass.setDataType("text");
				basicInquiryClass.setShowOnInquiry(headingInfo.getShowOnInquiry());
			try{
				basicInquiryClass.setSortOrderSequence(new Integer(setOrderBySequence[2]));
			}
			catch(Exception e)
			{
			}			basicInquiryClass.setSortOrderStyle(setOrderByStyle[2]);
				

				if (request.getParameter("fromTransactionUser") != null &&
					!request.getParameter("fromTransactionUser").equals(""))
				{
				   try
				   {
					  basicInquiryClass.setTextLow(request.getParameter("fromTransactionUser"));
					  parameterValues[1] = parameterValues[1] +
						  "&fromTransactionUser=" + request.getParameter("fromTransactionUser");
					  parameterValues[0] = parameterValues[0] +
						"From Transaction User: " + request.getParameter("fromTransactionUser");
					basicInquiryClass.setStatusCode("R");						
				   }
				   catch(Exception e)
				   {
				   }
				}
				if (request.getParameter("toTransactionUser") != null &&
					!request.getParameter("toTransactionUser").equals(""))
				{
				   try
				   {
					  basicInquiryClass.setTextHigh(request.getParameter("toTransactionUser"));
					  parameterValues[1] = parameterValues[1] +
						  "&toTransactionUser=" + request.getParameter("toTransactionUser");
					  parameterValues[0] = parameterValues[0] +
					  "To Transaction User: " + request.getParameter("toTransactionUser");
					basicInquiryClass.setStatusCode("R");					  
				   }
				   catch(Exception e)
				   {
				   }
				}	
			   
				validBasicVector.addElement(basicInquiryClass);
			//--------------------------------------------	
			// ELEMENT 4 of VECTOR	
				basicInquiryClass = new FormDefinition();
				basicInquiryClass.setFormNumber(headingInfo.getFormNumber());
				basicInquiryClass.setColumnNumber(new Integer(0));
				basicInquiryClass.setDataCode("DT");
				basicInquiryClass.setDataType("date");
				basicInquiryClass.setShowOnInquiry(headingInfo.getShowOnInquiry());
			try{
				basicInquiryClass.setSortOrderSequence(new Integer(setOrderBySequence[3]));
			}
			catch(Exception e)
			{
			}			basicInquiryClass.setSortOrderStyle(setOrderByStyle[3]);
				
			
				String[] returnValuesFrom = validateDate(request.getParameter("fromUpdateDate"));
				if (returnValuesFrom[0] != null)
				   anyProblem = anyProblem + returnValuesFrom[0];
				String[] returnValuesTo   = validateDate(request.getParameter("toUpdateDate"));
				if (returnValuesTo[0] != null)
					   anyProblem = anyProblem + returnValuesTo[0];
			   
				if (returnValuesFrom[0] == null &&
					returnValuesTo[0] == null &&
					returnValuesFrom[1] != null &&
					returnValuesTo[1] != null)
				{
					anyProblem = anyProblem +
								ValidateFields.testRangeDate(java.sql.Date.valueOf(returnValuesFrom[1]),
															 java.sql.Date.valueOf(returnValuesTo[1]));           
				}    	   
				// Get/Check the effective Date Fields
				if (anyProblem.equals(""))
				{
				   //IF no problems, set these date fields into the first
				   //   element of the vector
				   if (returnValuesFrom[1] != null)
				   {
					 basicInquiryClass.setDateLow(java.sql.Date.valueOf(returnValuesFrom[1]));
					 parameterValues[1] = parameterValues[1] +
						  "&fromUpdateDate=" + request.getParameter("fromUpdateDate");
					basicInquiryClass.setStatusCode("R");			
				   }
				   if (returnValuesTo[1] != null)
				   {
					 basicInquiryClass.setDateHigh(java.sql.Date.valueOf(returnValuesTo[1]));
					parameterValues[1] = parameterValues[1] +
						  "&toUpdateDate=" + request.getParameter("toUpdateDate");
					basicInquiryClass.setStatusCode("R");			
				   }
	
					if (returnValuesFrom[1] != null ||
						returnValuesTo[1] != null)
					{
						if (returnValuesFrom[1] != null &&
							returnValuesTo[1] != null &&
							returnValuesFrom[1].equals(returnValuesTo[1]))
						{
							parameterValues[0] = parameterValues[0] +
									 "Last Update Date: <i>" +
									 request.getParameter("fromUpdateDate") +
									 "</i>&nbsp;<br>";
						}
						else
						{
							if (returnValuesFrom[1] != null &&
								returnValuesTo[1] != null)
							{	
								parameterValues[0] = parameterValues[0] +
									 "Last Update Dates: <i>" +
									 request.getParameter("fromUpdateDate") +
									 " to " +
									 request.getParameter("toUpdateDate") +
									 "</i>&nbsp;<br>";
							}
							else
							{
								if (returnValuesFrom[1] != null &&
									returnValuesTo[1] == null)
								{
									parameterValues[0] = parameterValues[0] +
									   "Last Update Date: <i> After " +
									   request.getParameter("fromUpdateDate") +
									   "</i>&nbsp;<br>";
								}
								if (returnValuesFrom[1] == null &&
									returnValuesTo[1] != null)
								{
									parameterValues[0] = parameterValues[0] +
									   "Last Update Date: <i> Before " +
									   request.getParameter("toUpdateDate") +
									   "</i>&nbsp;<br>";
								}						 			
							}
						}
					}
				}
				validBasicVector.addElement(basicInquiryClass);
			//--------------------------------------------	
			// ELEMENT 5 of VECTOR	
			   returnValuesFrom = new String[2];
			   returnValuesTo   = new String[2];
				basicInquiryClass = new FormDefinition();
				basicInquiryClass.setFormNumber(headingInfo.getFormNumber());
				basicInquiryClass.setColumnNumber(new Integer(0));
				basicInquiryClass.setDataCode("TM");
				basicInquiryClass.setDataType("time");
				basicInquiryClass.setShowOnInquiry(headingInfo.getShowOnInquiry());
				try{
					basicInquiryClass.setSortOrderSequence(new Integer(setOrderBySequence[4]));
				}
				catch(Exception e)
				{
				}
				basicInquiryClass.setSortOrderStyle(setOrderByStyle[4]);
				
			
			if ((request.getParameter("hourfromUpdateTime") != null &&
			     request.getParameter("hourfromUpdateTime").trim().length() != 0) ||
		        (request.getParameter("minfromUpdateTime") != null &&
			     request.getParameter("minfromUpdateTime").trim().length() != 0) ||
		        (request.getParameter("secfromUpdateTime") != null &&
		     	 request.getParameter("secfromUpdateTime").trim().length() != 0))
		    {
				String fromTime = HTMLHelpers.combineTime3Sections(
											   request.getParameter("hourfromUpdateTime"),
											   request.getParameter("minfromUpdateTime"),
											   request.getParameter("secfromUpdateTime"), 
											   "Y");	
				if (!fromTime.equals("00:00:00"))
				{	
				returnValuesFrom = validateTime(fromTime);
				if (returnValuesFrom[0] != null)
				   anyProblem = anyProblem + returnValuesFrom[0];
				else
				{
				   basicInquiryClass.setTimeLow(java.sql.Time.valueOf(returnValuesFrom[1]));
				   parameterValues[1] = parameterValues[1] +
					   "&hourfromUpdateTime=" + request.getParameter("hourfromUpdateTime") +
					   "&minfromUpdateTime="  + request.getParameter("minfromUpdateTime") +
					   "&secfromUpdateTime="  + request.getParameter("secfromUpdateTime");											   
				}		
				basicInquiryClass.setStatusCode("R");
				}
		    }
			if ((request.getParameter("hourtoUpdateTime") != null &&
				 request.getParameter("hourtoUpdateTime").trim().length() != 0) ||
				(request.getParameter("mintoUpdateTime") != null &&
				 request.getParameter("mintoUpdateTime").trim().length() != 0) ||
				(request.getParameter("sectoUpdateTime") != null &&
				 request.getParameter("sectoUpdateTime").trim().length() != 0))
			{
				String toTime = HTMLHelpers.combineTime3Sections(
											   request.getParameter("hourtoUpdateTime"),
											   request.getParameter("mintoUpdateTime"),
											   request.getParameter("sectoUpdateTime"),
											   "Y");	
				if (!toTime.equals("00:00:00"))
				{	
				returnValuesTo = validateTime(toTime);
				if (returnValuesTo[0] != null)
				   anyProblem = anyProblem + returnValuesTo[0];
				else
				{
				   basicInquiryClass.setTimeHigh(java.sql.Time.valueOf(returnValuesTo[1]));
				   parameterValues[1] = parameterValues[1] +
					   "&hourtoUpdateTime=" + request.getParameter("hourtoUpdateTime") +
					   "&mintoUpdateTime="  + request.getParameter("mintoUpdateTime") +
					   "&sectoUpdateTime="  + request.getParameter("sectoUpdateTime");											   
				}							   
				basicInquiryClass.setStatusCode("R");	
				}
			}			
		
			if (returnValuesFrom[1] != null &&
				returnValuesTo[1] != null)
			{
				anyProblem = anyProblem +
							ValidateFields.testRangeTime(java.sql.Time.valueOf(returnValuesFrom[1]),
														 java.sql.Time.valueOf(returnValuesTo[1]));           
			    	   
			}
	
			if (returnValuesFrom[1] != null ||
				returnValuesTo[1] != null)
			{
				if (returnValuesFrom[1] != null &&
					returnValuesTo[1] != null &&
					returnValuesFrom[1].equals(returnValuesTo[1]))
				{
					parameterValues[0] = parameterValues[0] +
							 "Last Update Time: <i>" +
							 returnValuesFrom[1] +
							 "</i>&nbsp;<br>";
				}
				else
				{
					if (returnValuesFrom[1] != null &&
						returnValuesTo[1] != null)
					{	
						parameterValues[0] = parameterValues[0] +
							 "Last Update Times: <i>" +
							 returnValuesFrom[1] +
							 " to " +
							 returnValuesTo[1] +
							 "</i>&nbsp;<br>";
					}
					else
					{
						if (returnValuesFrom[1] != null &&
							returnValuesTo[1] == null)
						{
							parameterValues[0] = parameterValues[0] +
							   "Last Update Time: <i> After " +
							   returnValuesFrom[1] +
							   "</i>&nbsp;<br>";
						}
						if (returnValuesFrom[1] == null &&
							returnValuesTo[1] != null)
						{
							parameterValues[0] = parameterValues[0] +
							   "Last Update Time: <i> Before " +
							   returnValuesTo[1] +
							   "</i>&nbsp;<br>";
						}						 			
					}
				}
			}
			validBasicVector.addElement(basicInquiryClass);					
		}
		catch (Exception e)
		{
		}
		
	   request.setAttribute("validBasicVector", validBasicVector);
	   request.setAttribute("parameterValues", parameterValues);
	}
	catch(Exception e)
	{
		System.out.println("Problem within CtlForms.ValidateInquiryParameters" +
			               "(request, response).  " + e);  
	}
		return anyProblem;
	}
	/**
	 * Maintain Page Control
	 * 
	 *   Builds / Updates
	 *     Forms Header, Definition, Headings & Security
	 * 
	 *   Uses Methods to gather information to Build the
	 *     Forms Maintenance Pages.
	 * 
	 * Creation date: (8/10/2004 9:29:40 AM)
	 */
	private void pageMaintainForm(javax.servlet.http.HttpServletRequest request,
						          javax.servlet.http.HttpServletResponse response)  
	{
		try
		{
			
		
		
			
		}
		catch(Exception e)
		{
			System.out.println("Error in CtlForms.pageMaintainForm: " + e);
		}
		return;  	   
	}
	/**
	 * this method is used to decide what to do with the class.
	 *    whether to update or add it.
	 * Creation date: (8/21/2003 4:12:18 PM)
	 */
	private String createTransaction(Vector thisRecord, 
	                                 javax.servlet.http.HttpServletRequest request,
	                                 javax.servlet.http.HttpServletResponse response) 
	{
		Integer transactionNumber = null;
		try
		{
			transactionNumber = new Integer(request.getParameter("transactionNumber"));
			request.setAttribute("transactionNumber", transactionNumber);
		}
		catch(Exception e)
		{
		}
		
		String problem   = "";
		int    infoCount = 0; // number of cells which NOW have info in them.
		try
		{
			infoCount = thisRecord.size();
		}
		catch(Exception e)
		{
			
		}
		FormData changeThisRecord = new FormData();	
		if (infoCount > 0)
		{
			if (transactionNumber == null)
	        {
		        try
		        {
			       changeThisRecord.insert(thisRecord);  
		        }
		        catch(Exception e)
		        {
			        problem = "Problem occurred while Inserting a Record. \\n" +
			                "CtlForms.transaction(): " + e;
		        }
	        }
			else
			{
		        try
		        {
			       changeThisRecord.update(thisRecord);  
		        }
		        catch(Exception e)
		        {
			        problem = "Problem occurred while Updating a Record. \\n" +
			                "CtlForms.transaction(): " + e;
		        }
			}
		}
	
		return problem;	
	}
	/**
	 * Retrive in formNumber as a parameter.
	 * 
	 *   1.  Test to Make sure it is a valid number.
	 *   2.  Test to Make sure it is a valid Form.
	 *   3.  Create Request Parameter for Form Number.
	 *   4.  Create Request Parameter for FormHeader(Class) of Form Number.
	 *
	 * Creation date: (9/7/2004 10:50:48 AM)
	 */
	private void validateFormNumber(javax.servlet.http.HttpServletRequest request,
				 			          javax.servlet.http.HttpServletResponse response) 
	{
	try
	{
		String testInteger = ValidateFields.validateInteger(request.getParameter("FormNumber"));
		if (testInteger.equals("") &&
		    request.getParameter("formNumber") != null)
		{
	        request.setAttribute("formHeader", (new FormHeader(new Integer(request.getParameter("formNumber")))));
			request.setAttribute("formNumber", (new Integer(request.getParameter("formNumber"))));
		}
	}
	catch(Exception e)
	{
		System.out.println("Problem within CtlForms.validateFormNumber" +
			               "(request, response).  " + e);  
	}
		return;
	}
	/**
	 * Detail Page Control
	 *   Uses Methods to gather information to Build the Detail Page.
	 * 
	 * Creation date: (7/22/2004 2:13:40 PM)
	 */
	private void pageDtl(javax.servlet.http.HttpServletRequest request,
						 javax.servlet.http.HttpServletResponse response)  
	{
		try
		{
			Integer transactionNumber = new Integer(0);
			try
			{
			   transactionNumber = new Integer(request.getParameter("transactionNumber"));
			   Vector thisTransaction = FormData.findDataByTranByEntry(
												   ((Integer) request.getAttribute("formNumber")),
												   transactionNumber);
			   request.setAttribute("thisTransaction", thisTransaction);												
			   
			}
			catch(Exception e)
			{
			}
		}
		catch(Exception e)
		{
			System.out.println("Error in CtlForms.pageDtl: " + e);
		}
		return;  	   
	}
	/**
	 * This Method will receive in:
	 *
	 *  a String which should be a time,
	 *   and will verify that it is a time.
	 *
	 * Finally to return a string, which is the error message
	 *
	 * Creation date: (9/11/2003 01:19:48 PM)
	 */
	private String[] validateTime(String time) 
	{
		String[] timeProblem = new String[2];
	try
	{
		//** Set a Valid Time
//	    java.sql.Time timeInput = java.sql.Time.valueOf("00:00:00");
	
	    // Test if a valid Time
		if (time != null &&
			!time.trim().equals("null") &&
			time.length() != 0)
		{
			try
			{
//	           timeInput    = java.sql.Time.valueOf(time);
	           timeProblem[1] = time;	
			}
			catch(Exception e)
			{
				System.out.println("Problem with validating a Time: " +
					                 e );
				timeProblem[0] = "Unable to validate this time " +
					               time + ".  \n";			
			}
			
		}
	}
	catch(Exception e)
	{
		System.out.println("Problem within CtlForms.ValidateTime" +
			               "(String).  " + e);  
	}
		return timeProblem;
	}
	/**
	 * This Method will receive in:
	 *
	 *   A String to test to make sure it is a date.
	 *   
	 * 
	 * This Method will return a string array.
	 *     Value 0 = Problem message.
	 *     Value 1 = Date in the format YYYY-DD-MM (ONLY if no problem) 
	 * 
	 * Creation date: (7/20/2004 10:52:48 AM)
	 */
	private String[] validateDate(String inDate) 
	{
		String[] returnValues = new String[2];
		//** Set a Valid Date
//		java.sql.Date dateIn = java.sql.Date.valueOf("1950-01-01");
	try
	{
	    // Test if a valid Date
		if (inDate != null &&
			!inDate.trim().equals("null") &&
			inDate.length() != 0)
		{
			String dateArray[] = CheckDate.validateDate(inDate);
			if (!dateArray[6].equals(""))
			{			
				returnValues[0] = dateArray[6];
		    }
			else
			{
			   returnValues[1] = dateArray[7];
			}
		}
	}
	catch(Exception e)
	{
		System.out.println("Problem within CtlForms.ValidateDate" +
			               "(String).  " + e);  
	}
	 	return returnValues;
	}
	/**
	 * Detail Page Control
	 *   Uses Methods to gather information to Build the Detail Page.
	 * 
	 * Creation date: (7/22/2004 2:13:40 PM)
	 */
	private void pageUpd(javax.servlet.http.HttpServletRequest request,
						 javax.servlet.http.HttpServletResponse response)  
	{
		try
		{
			
			Integer transactionNumber = (Integer) request.getAttribute("transactionNumber");
			if (transactionNumber == null)
			{
				try
				{
			   		transactionNumber = new Integer(request.getParameter("transactionNumber"));
				   if (transactionNumber == null)
				      transactionNumber = new Integer(0);
				   Vector thisTransaction = FormData.findDataByTranByEntry(
			                                     ((Integer) request.getAttribute("formNumber")),
												   transactionNumber);
				   request.setAttribute("thisTransaction", thisTransaction);	
				}
				catch(Exception e)
				{
				}
			}
			Vector updateDefinition = retrieveFormDefinition(request, 
														   response, 
														   "update");
			request.setAttribute("updateDefinition", updateDefinition);                                                           
		}
		catch(Exception e)
		{
			System.out.println("Error in CtlForms.pageUpd: " + e);
		}
		return;  	   
	}
	/**
	 * List (Report,Form) Page Control
	 *   Uses Methods to gather information to Build the Report, Form Page.
	 *   Will also use Templates to decide WHAT the page should look like.
	 *   Each TEMPLATE will have seperate methods to build the table.
	 * 
	 * Creation date: (7/22/2004 2:15:40 PM)
	 */
	private void pageList(javax.servlet.http.HttpServletRequest request,
						  javax.servlet.http.HttpServletResponse response)  
	{
		try
		{
			Vector validBasicVector   = (Vector) request.getAttribute("validBasicVector");
			Vector validInquiryVector = (Vector) request.getAttribute("validInquiryVector");
			
			Vector formHeadings    = FormHeadings.findHeadingsByForm((Integer) request.getAttribute("formNumber"));
			request.setAttribute("formHeadings", formHeadings);

			Vector listDefinition = retrieveFormDefinition(request, 
														   response, 
														   "list");
			request.setAttribute("listDefinition", listDefinition);                                                           
			
			Vector dataList          = FormData.findDataByRange(validBasicVector,
																validInquiryVector,
																listDefinition);
			request.setAttribute("dataList", dataList);  
			 	
		}
		catch(Exception e)
		{
			System.out.println("Error in CtlForms.pageList: " + e);
		}  	   
		return;	
	}
	/**
	 * Inquiry Page Control
	 *   Uses Methods to gather information to Build the Inquiry Page.
	 * 
	 *   inquiry JSP needs:  inquiryDefinition
	 * 
	 * Creation date: (7/22/2004 2:14:40 PM)
	 */
	private void pageInq(javax.servlet.http.HttpServletRequest request,
						 javax.servlet.http.HttpServletResponse response)  
	{
		try
		{
			if (request.getAttribute("formNumber") != null)
			{
				Vector inquiryDefinition = retrieveFormDefinition(request, response, "inquiry");
				request.setAttribute("inquiryDefinition", inquiryDefinition);
			}			
		}
		catch(Exception e)
		{
			System.out.println("Error in CtlForms.pageInq: " + e);
		}  	   
		return;	
	}
/**
 * This Method will receive in:
 *
 *  Will take in a Class, return a string which
 *    will have both a number of days and a time field.
 *
 * Creation date: (10/23/2003 8:39:48 AM)
 */
public static String buildCustomDurationTime(FormData thisData) 
{
	String htmlTime = "";
	BigDecimal zero = new BigDecimal("0");
	BigDecimal days = thisData.getDataNumeric();

	if (days.compareTo(zero) != 0)
	{
		htmlTime = htmlTime + days + "&nbsp;Days&nbsp:";
	}
    htmlTime = htmlTime + buildStandardTime(thisData.getDataTime(),
    										thisData.getDataType());	
	
	return htmlTime;
}
/**
 *  Used to build an Input Box / from the JSP's;
 * This Method will receive in:
 *   dateValue - If the field already has a value, send that in. 
 *   thisForm - will give me details on how it should be displayed
 *   extraName - to be used when building the function on an input.
 *
 * Finally to return a string, which is has HTML coded
 *    into it.
 *
 * Creation date: (8/12/2003 12:52:48 AM)
 */
public static String[] buildStandardDate(String dateValue,
	                                     FormDefinition thisForm, 
	                                     String extraName,
						   		         String requestType) 
{
	
	String[] htmlDate = new String[2];
	htmlDate[0] = "";  //display Input date Box.
    htmlDate[1] = "";  // javascript for use in JSP (Bottom Part)
    
	String formName     = "" + requestType;
	String functionName = "Cal";
	String fieldName    = extraName;

	if (fieldName.equals("from") || 
		fieldName.equals("to") ||
		fieldName.equals("input"))
	{
		functionName = extraName + functionName + thisForm.getColumnNumber();  
	    fieldName    = extraName + thisForm.getColumnNumber();
	}
	else
	{
		if (!fieldName.equals(""))
		{
			functionName = extraName + functionName;
		}
	}
    htmlDate[1] = JavascriptInfo.getCalendarFoot(formName, 
                      							 functionName, 
                       							 fieldName);
 	
   if (dateValue == null ||
       dateValue.equals("null"))
   {
       dateValue = "";
   }
   if (extraName.equals("inputEffectiveDate"))
   {
	  if (dateValue.equals(""))
	  {
       String dateArray[]        = SystemDate.getSystemDate();
       java.sql.Date currentDate = java.sql.Date.valueOf(dateArray[7]);
       String[] newDateArray     = GetDate.getDates(currentDate);
   	   dateValue                 = newDateArray[5];
	  }
   }
         
       htmlDate[0] = HTMLHelpersInput.inputBoxDate(fieldName,
	                                    dateValue,
	                                    functionName, 
	                                    "N", "N");

	return htmlDate;
}
/**
 *  Used to build a display of a date.
 * This Method will receive in:
 *   dateValue - Date Value for display
 *
 * Finally to return a string, which is has HTML coded
 *    into it.
 *
 * Creation date: (9/15/2003 9:40:48 AM)
 */
public static String buildStandardDate(java.sql.Date dateValue, 
									   String dataType) 
{
	String htmlDate = "";

	String[] getDate = GetDate.getDates(dateValue);
	if (getDate[0].trim().length() == 0)
	{
		htmlDate = getDate[5];
		if (htmlDate.trim().length() == 0 ||
		    htmlDate.equals("01/01/1950"))
			htmlDate = "&nbsp;";
	}	

	return htmlDate;
}
/**
 * This Method will receive in:
 *   the Form Definition class which will be for the
 *     specific line you are working with.
 *
 *   If there is a valid Hi and Low in the definition
 *   it will return the HTML code for how the display of
 *   that is built
 *
 * Finally to return a string, which is has HTML coded
 *    into it.
 *
 * Creation date: (9/12/2003 10:52:48 AM)
 */
public static String buildStandardDateRange(FormDefinition thisForm) 
{
	String htmlDateRange = "";
try
{
	       String dateRange = "";
	       String thisLowDate = "" + thisForm.getDateLow();
	       String thisHighDate = "" + thisForm.getDateHigh();
	       if (!thisLowDate.equals("1950-01-01"))
	       {
		      String lowDate = buildStandardDate(thisForm.getDateLow(),
		      									 thisForm.getDataType());
		    
  		       dateRange = "" + lowDate;
		       if (!thisHighDate.equals("2039-12-31"))
		       {
		          String highDate = buildStandardDate(thisForm.getDateHigh(),
		          									  thisForm.getDataType());
			       dateRange = dateRange + " - " + highDate;
		       }
		       else
		           dateRange = "> " + dateRange;
	       }
	       else
	       {
               if (!thisHighDate.equals("1950-01-01"))
		       {  
			      String highDate = buildStandardDate(thisForm.getDateHigh(),
			      								      thisForm.getDataType());
		          dateRange = "< " + highDate;
	           }
           }
	       htmlDateRange = htmlDateRange +
   	                       dateRange;
}
catch(Exception e)
{
	System.out.println("Problem within CtlForms.buildStandardDateRange" +
		               "().  " + e);  
}
	return htmlDateRange;
}
/**
 *  Used to build an Input Box.
 * This Method will receive in:
 *   numberValue - If the field already has a value, send that in. 
 *   thisForm    - will give me details on how it should be displayed
 *   extraName   - to be used when building the function on an input.
 *
 * Finally to return a string, which is has HTML coded
 *    into it.
 *
 * Creation date: (8/12/2003 12:52:48 AM)
 */
public static String buildStandardNumber(String fieldValue,
	                               FormDefinition thisForm, 
	                               String extraName) 
{
	String htmlNumber = "";
	
	if (fieldValue == null ||
		fieldValue.trim().equals("null") ||
		fieldValue.trim().length() == 0)
	   fieldValue = "";

	String fieldName = extraName + thisForm.getColumnNumber();
	
	if (thisForm.getDataType().trim().toLowerCase().equals("transaction") &&
		!thisForm.getJoinFormNumber().toString().equals("0") &&
		!thisForm.getJoinFormColumn().toString().equals("0"))
	{
		
		htmlNumber = FormData.buildDataDropDownList(thisForm.getJoinFormNumber(),
											  	    thisForm.getColumnNumber(),
											  	    fieldName, 
											  	    fieldValue, 
												    "");
	}
	else
	{	 
    	htmlNumber = HTMLHelpersInput.inputBoxNumber(fieldName,
	    	                                 fieldValue,
	        	                             thisForm.getHeadingLong().trim(),
	            	                         thisForm.getInputSize().intValue(), 
	                                     	thisForm.getInputMaxLength().intValue(),
	                                     	thisForm.getRequiredEntry(), 
	                                     	"N");
	}

	return htmlNumber;
}
/**
 * This Method will receive in:
 *
 *
 * Then decide how it should be displayed in the JSP,
 *     this decision is based on the requestType.
 *
 * Finally to return a string, which is has HTML coded
 *    into it.
 *
 * Creation date: (9/15/2003 1:45:48 PM)
 */
public static String buildStandardNumber(BigDecimal numberValue, 
	                               		 int decimalPositions,
	                               		 String dataType,
	                               		 Integer inFormNumber) 
{
	String htmlNumber = "";
	htmlNumber = HTMLHelpers.displayNumber(numberValue, 
										decimalPositions);
	if (htmlNumber.trim().length() == 0 ||
		htmlNumber.equals("0"))
      htmlNumber = "&nbsp;";
	
	if (dataType.trim().toLowerCase().equals("transaction") &&
		!htmlNumber.equals("&nbsp;"))
	{
	//** Create Link for the Transaction Number to go to Transaction Detail
	   htmlNumber = "<a class=\"a04001\" href=\"/web/CtlForms?requestType=detail" +
						  "&transactionNumber=" + htmlNumber +
						  "&formNumber=" + inFormNumber +
						  "\" target=\"_blank\">" + htmlNumber +
						  "</a>";
	}

            
	return htmlNumber;
}
/**
 * This Method will receive in:
 *
 *
 * Then decide how it should be displayed in the JSP,
 *     this decision is based on the requestType.
 *
 * Finally to return a string, which is has HTML coded
 *    into it.
 *
 * Creation date: (9/15/2003 1:45:48 PM)
 */
public static String buildStandardNumber(FormData inValues) 
{
	String htmlNumber = "";
	htmlNumber = HTMLHelpers.displayNumber(inValues.getDataNumeric(), 
										inValues.getDecimalPositions().intValue());
	if (htmlNumber.trim().length() == 0 ||
		htmlNumber.equals("0"))
	  htmlNumber = "&nbsp;";
	
	if (inValues.getDataType().trim().toLowerCase().equals("transaction") &&
		!htmlNumber.equals("&nbsp;"))
	{
	   String transactionName = "";
	   Vector dataDescription = inValues.getDataDescription();
	   try
	   {
	   	   for (int x = 0; x < dataDescription.size(); x++)
	   	   {
	   	   	  String testValue = (String) dataDescription.elementAt(x);
	   	   	  if (testValue != null &&
	   	   	      !testValue.trim().equals(""))
	   	   	  {
	   	   	  	if (transactionName.equals(""))
	   	   	  	   transactionName = " - ";
	   	   	  	transactionName = transactionName + testValue + " ";
	   	   	  }
	   	   }
	   }
	   catch(Exception e)
	   {
	   }
	//** Create Link for the Transaction Number to go to Transaction Detail
	   htmlNumber = "<a class=\"a04001\" href=\"/web/CtlForms?requestType=detail" +
						  "&transactionNumber=" + htmlNumber +
						  "&formNumber=" + inValues.getJoinFormNumber() +
						  "\" target=\"_blank\">" + htmlNumber + 
						  transactionName +
						  "</a>";
	}
	if (inValues.getDataType().trim().toLowerCase().equals("broker") &&
		!htmlNumber.equals("&nbsp;"))
	{
		// Construct the Broker Class
		try
		{
			String         className   = (String) inValues.getDataClassName().elementAt(0);
			if (className != null &&
				className.equals("Broker"))
			{
				// Not needed, if needed can - re-add
//				Broker classValues = (Broker) inValues.getDataClass().elementAt(0);
//				htmlNumber = "<a class=\"a04001\" " +
 //                           "href=\"/web/JSP/Router/brokerRouter.jsp?" +
//							"requestType=detail" + 
//							"&brokerNumber=" + htmlNumber +
//							"\" target=\"_blank\">" +
//							htmlNumber + " - " +
//						    classValues.getName().trim() +
//						    "</a>";
			}

		}
		catch(Exception e)
		{
		}
	}

	
            
	return htmlNumber;
}
/**
 * This Method will receive in:
 *   the Form Definition class which will be for the
 *     specific line you are working with.
 *
 *   If there is a valid Hi and Low in the definition
 *   it will return the HTML code for how the display of
 *   that is built
 *
 * Finally to return a string, which is has HTML coded
 *    into it.
 *
 * Creation date: (9/15/2003 10:30:48 AM)
 */
public static String buildStandardNumberRange(FormDefinition thisForm) 
{
	String htmlNumberRange = "";
try
{
	String numberRange = "";

	BigDecimal zero           = new BigDecimal("0");
	BigDecimal thisLowNumber  = thisForm.getNumericLow();
	BigDecimal thisHighNumber = thisForm.getNumericHigh();
	

	if (thisLowNumber.compareTo(zero) != 0)
	{
		String lowNumber = HTMLHelpers.displayNumber(thisLowNumber, thisForm.getDecimalPositions().intValue());
		     numberRange = " " + lowNumber;
		if (thisHighNumber.compareTo(zero) != 0)
		{
		   String highNumber = HTMLHelpers.displayNumber(thisHighNumber, thisForm.getDecimalPositions().intValue());
		         numberRange = numberRange + " - " + highNumber;
		}
		else
		   numberRange = "> " + numberRange;
	 }
	 else
	 {
        if (thisHighNumber.compareTo(zero) != 0)
		{  
		   String highNumber = HTMLHelpers.displayNumber(thisHighNumber, 
			                        thisForm.getDecimalPositions().intValue());
		         numberRange = "0 - " + highNumber;
	    }
     }
     htmlNumberRange = htmlNumberRange +
                       numberRange;
}
catch(Exception e)
{
	System.out.println("Problem within CtlForms.buildStandardNumberRange" +
		               "(FormDefinintion).  " + e);  
}
	return htmlNumberRange;
}
/**
 * This Method will receive in:
 *
 *
 * Then decide how it should be displayed in the JSP,
 *     this decision is based on the requestType.
 *
 * Finally to return a string, which is has HTML coded
 *    into it.
 *
 * Creation date: (8/12/2003 12:52:48 AM)
 */
public static String buildStandardText(String textValue,
									   String dataType) 
{
	String htmlText = textValue.trim();

	if (htmlText.trim().length() == 0 ||
	    htmlText.trim().equals("_"))
	   htmlText = "&nbsp;";
	   
	if (dataType.toLowerCase().equals("logonpassword") &&
	    !htmlText.equals("&nbsp;"))
	{
		for (int x = 0; x < dataType.trim().length(); x++);
		{
			htmlText = htmlText + "*";	   
		}
	}
		
	return htmlText;
}
/**
 * This Method will receive in:
 *
 *
 * Then decide how it should be displayed in the JSP,
 *     this decision is based on the requestType.
 *
 * Finally to return a string, which is has HTML coded
 *    into it.
 *
 * Creation date: (8/12/2003 12:52:48 AM)
 */
public static String buildStandardText(FormData inValues) 
{
	String htmlText = inValues.getDataText().trim();

	if (htmlText.trim().length() == 0 ||
		htmlText.trim().equals("_"))
	   htmlText = "&nbsp;";
	   
	if (!htmlText.trim().equals("&nbsp;") &&
		 inValues.getDataType().toLowerCase().trim().equals("email"))
	{
		 htmlText = "<a class=\"a04001\" " +
					"href=\"mailto:" +
					inValues.getDataText().trim() +
					"\">" +
					inValues.getDataText().trim() +
					"</a>";	
	}
	                
	if (!htmlText.trim().equals("&nbsp;") &&
		inValues.getDataType().toLowerCase().trim().equals("customer"))
	{
		// Seperate the Co and the Customer
		try
		{
			String         className   = (String) inValues.getDataClassName().elementAt(0);
			if (className != null &&
			    className.equals("CustomerBillTo"))
			{
				// Need to redo if neeeded at a later time
//				CustomerBillTo classValues = (CustomerBillTo) inValues.getDataClass().elementAt(0);
//				int    findUnderscore = inValues.getDataText().trim().indexOf("_");
//				String companyNumber  = inValues.getDataText().trim().substring(0, findUnderscore);
//				String customerNumber = inValues.getDataText().trim().substring((findUnderscore + 1), inValues.getDataText().trim().length());
//				
//				htmlText = 	"<a class=\"a04001\" " +
//							"href=\"/web/JSP/Router/customerRouter.jsp?" +
	//						"requestType=billToDetail" + 
	//						"&companyNumber=" + companyNumber +
	//						"&customerNumber=" + customerNumber +
	//						"\" target=\"_blank\">" +
	//						inValues.getDataText().trim() + " - " +
	//		   				classValues.getCustomerName() +
	//						"</a>";				
			}
		}
		catch(Exception e)
		{
		}
		
	}	
	if (!htmlText.trim().equals("&nbsp;") &&
	    inValues.getDataType().toLowerCase().trim().equals("logonpassword"))
	{
		int passwordLength = htmlText.trim().length();
		htmlText = "";
		if (passwordLength > 0)
		{
			for (int z = 0; z < passwordLength; z++)
			{
				htmlText = htmlText + "*";	   
			}
		}
	}	
	if (!htmlText.trim().equals("&nbsp;") &&
		(inValues.getDataType().toLowerCase().trim().equals("39wrin") ||
		 inValues.getDataType().toLowerCase().trim().equals("39productdesc") ||
		 inValues.getDataType().toLowerCase().trim().equals("39distcenter") ||
		 inValues.getDataType().toLowerCase().trim().equals("39problem") ||
		 inValues.getDataType().toLowerCase().trim().equals("39vendor")))
		{
			String descCode = "SVN"; // Vendor
			if (inValues.getDataType().toLowerCase().trim().equals("39wrin"))
				descCode = "SWR";
			if (inValues.getDataType().toLowerCase().trim().equals("39productdesc"))
				descCode = "SPD";
			if (inValues.getDataType().toLowerCase().trim().equals("39distcenter"))
				descCode = "SDC";
			if (inValues.getDataType().toLowerCase().trim().equals("39problem"))
				descCode = "SPB";
			try
			{
			    GeneralInfo typeClass = new GeneralInfo(descCode,
                       01,
                       inValues.getDataText().trim(),
                        "  ",
                        "  ",
                        "  ");
			    htmlText = typeClass.getDescFull().trim();	       
			}
			catch(Exception e)
			{}
		}	
		
	return htmlText;
}
/**
 *  Used to build an Input Box.
 * This Method will receive in:
 *   numberValue - If the field already has a value, send that in. 
 *   thisForm    - will give me details on how it should be displayed
 *   extraName   - to be used when building the function on an input.
 *
 * Finally to return a string, which is has HTML coded
 *    into it.
 *
 * Creation date: (8/15/2003 10:27:48 AM)
 */
public static String buildStandardText(String fieldValue,
	                             FormDefinition thisForm, 
	                             String extraName,
								 javax.servlet.http.HttpServletRequest request,
								 javax.servlet.http.HttpServletResponse response) 
{
	String requestType   = (String) request.getAttribute("requestType");
	
	String htmlText = "";
	String needTextArea = "N";
	
	if (fieldValue == null ||
		fieldValue.trim().equals("null") ||
		fieldValue.trim().length() == 0)
	   fieldValue = "";

	String fieldName      = extraName + thisForm.getColumnNumber();
	int    fieldInputSize = thisForm.getInputSize().intValue();
	if (thisForm.getDataType().trim().equals("email"))
	   fieldInputSize = 60;
	  
	if (thisForm.getInputSize().intValue() > 30 ||
		fieldInputSize >= 30)
	{
	    if (requestType.trim().equals("inq"))
	       fieldInputSize = 30;
	    else
	    {
		    if ((thisForm.getInputSize().intValue() > 60 ||
		        fieldInputSize >= 60) &&
		        !thisForm.getDataType().trim().equals("email"))
		        needTextArea = "Y";
	    }
	}
	if (needTextArea.equals("Y"))
	   htmlText = HTMLHelpersInput.inputBoxTextarea(fieldName,
											fieldValue,
	   								        6,
	                                        60,
											thisForm.getInputMaxLength().intValue(),
	   										"N");	
	else
	{
		if (!thisForm.getDataType().trim().toLowerCase().equals("customer") &&
			!thisForm.getDataType().trim().toLowerCase().equals("state") &&
			!thisForm.getDataType().trim().toLowerCase().equals("39wrin") &&
			!thisForm.getDataType().trim().toLowerCase().equals("39productdesc") &&
			!thisForm.getDataType().trim().toLowerCase().equals("39distcenter") &&
			!thisForm.getDataType().trim().toLowerCase().equals("39problem") &&
			!thisForm.getDataType().trim().toLowerCase().equals("39vendor"))
		{
			htmlText = HTMLHelpersInput.inputBoxText(fieldName,
										 fieldValue,
										 thisForm.getHeadingLong().trim(),
										 fieldInputSize, 
										 thisForm.getInputMaxLength().intValue(),
										thisForm.getRequiredEntry(),
										"N");
		}
		if (thisForm.getDataType().trim().toLowerCase().equals("customer"))
		{
//		   if (requestType.equals("list"))
//		   {
//			 htmlText = CustomerBillTo.buildDataDropDownList(fieldName, 
//															 fieldValue,
//															 "");
//		   }
//		   else
//		   {
			 String coNumber = fieldName + "Co";
			 String custNumber = fieldName + "Cust";
			 String coFieldValue = "";
			 String custFieldValue = "";
			 int    underscorePlace = fieldValue.indexOf("_");
			 if (underscorePlace > 0)
			 {
				 try
				 {
					 coFieldValue   = fieldValue.substring(0, underscorePlace).trim();
					 custFieldValue = fieldValue.substring((underscorePlace + 1), fieldValue.length()).trim();
				 }
				 catch(Exception e)
				 {
				 }
			 }
	   	  
			 htmlText = HTMLHelpersInput.inputBoxText(coNumber,
										  coFieldValue,
										  thisForm.getHeadingLong().trim(),
										  3, 
										  5,
										  thisForm.getRequiredEntry(), "N") + 
					  "_" +
					  HTMLHelpersInput.inputBoxText(custNumber,
										  custFieldValue,
										  thisForm.getHeadingLong().trim(),
										  5, 
										  7,
										  thisForm.getRequiredEntry(), "N");
//		   }								 
		}
		if (thisForm.getDataType().trim().toLowerCase().equals("state"))
		{
			htmlText = HTMLHelpersInput.dropDownStates(fieldName, fieldValue, "Select a State / Province", "N");
		}	
		// Drop Down Lists specific to Form 39
		if (thisForm.getDataType().trim().toLowerCase().equals("39wrin") ||
			thisForm.getDataType().trim().toLowerCase().equals("39productdesc") ||
			thisForm.getDataType().trim().toLowerCase().equals("39distcenter") ||
			thisForm.getDataType().trim().toLowerCase().equals("39problem") ||
			thisForm.getDataType().trim().toLowerCase().equals("39vendor"))
		{
			String descCode = "SVN"; // Vendor
			if (thisForm.getDataType().trim().toLowerCase().equals("39wrin"))
				descCode = "SWR";
			if (thisForm.getDataType().trim().toLowerCase().equals("39productdesc"))
				descCode = "SPD";
			if (thisForm.getDataType().trim().toLowerCase().equals("39distcenter"))
				descCode = "SDC";
			if (thisForm.getDataType().trim().toLowerCase().equals("39problem"))
				descCode = "SPB";
			try
			{
				Vector typeList       = new Vector();
				typeList              = GeneralInfo.findDescByFull(descCode);
				htmlText              = GeneralInfo.buildDropDownFullForKey1(
											typeList, fieldValue, 
											fieldName, "Select One: ");
				
			    //   GeneralInfo typeClass = new GeneralInfo("SRC",
                  //      25,
                    //    sampleClass.getType().trim(),
                      //  "  ",
//                        "  ",
  //                      "  ");
//displayInfo       = typeClass.getDescFull().trim();	       
			}
			catch(Exception e)
			{}
		}
	}

	return htmlText;
}
/**
 * This Method will receive in:
 *   the Form Definition class which will be for the
 *     specific line you are working with.
 *
 *   If there is a valid Hi and Low in the definition
 *   it will return the HTML code for how the display of
 *   that is built
 *
 * Finally to return a string, which is has HTML coded
 *    into it.
 *
 * Creation date: (9/15/2003 10:30:48 AM)
 */
public static String buildStandardTextRange(FormDefinition thisForm) 
{
	String htmlTextRange = "";
try
{
	String textRange = "";

	String thisLowText  = thisForm.getTextLow().trim();
	String thisHighText = thisForm.getTextHigh().trim();

	if (thisLowText.trim().length() != 0)
	{
		textRange = " " + thisLowText;
		if (thisHighText.trim().length() != 0)
		{
		   textRange = textRange + " - " + thisHighText;
		}
		else
		   textRange = "> " + textRange;
	 }
	 else
	 {
        if (thisHighText.trim().length() != 0)
		{  
	         textRange = "< " + thisHighText;
	    }
     }
     htmlTextRange = htmlTextRange +
                      textRange;
}
catch(Exception e)
{
	System.out.println("Problem within CtlForms.buildStandardTextRange" +
		               "(FormDefinintion).  " + e);  
}
	return htmlTextRange;
}
/**
 * This Method will receive in:
 *
 *
 * Then decide how it should be displayed in the JSP,
 *     this decision is based on the requestType.
 *
 * Finally to return a string, which is has HTML coded
 *    into it.
 *
 * Creation date: (8/12/2003 12:52:48 AM)
 */
public static String buildStandardTime(java.sql.Time timeValue,
									   String dataType) 
{
	String htmlTime = "" + timeValue;
	if (htmlTime.trim().length() == 0)
	   htmlTime = "nbsp;";	
	
	return htmlTime;
}
/**
 * This Method will receive in:
 *   the Form Definition class which will be for the
 *     specific line you are working with.
 *
 *   If there is a valid Hi and Low in the definition
 *   it will return the HTML code for how the display of
 *   that is built
 *
 * Finally to return a string, which is has HTML coded
 *    into it.
 *
 * Creation date: (9/15/2003 10:39:48 AM)
 */
public static String buildStandardTimeRange(FormDefinition thisForm) 
{
	String htmlTimeRange = "";
try
{
	       String timeRange = "";
//    java.sql.Time thisTime = java.sql.Time.valueOf("00:00:00");

	String newLow = "" + thisForm.getTimeLow();
	String newHigh = "" +  thisForm.getTimeHigh();
    
//    int testLow = thisForm.getTimeLow().compareTo(thisTime);
//    int testHigh = thisForm.getTimeHigh().compareTo(thisTime);
   
	if (!newLow.trim().equals("00:00:00"))
	{
        timeRange = "" + thisForm.getTimeLow();
	    if (!newHigh.trim().equals("00:00:00"))
	   		timeRange = timeRange + " - " + thisForm.getTimeHigh();
	    else
		    timeRange = "> " + timeRange;
	 }
	 else
	 {
        if (!newHigh.trim().equals("00:00:00"))
	        timeRange = "< " + thisForm.getTimeHigh();
	}
	       htmlTimeRange = htmlTimeRange +
   	                       timeRange;
}
catch(Exception e)
{
	System.out.println("Problem within CtlForms.buildStandardTimeRange" +
		               "(FormDefinition).  " + e);  
}
	return htmlTimeRange;
}
/**
 *  Used to compare if a field is within
 *    the ranges for the definition of that
 *    form.
 *
 * Finally to return a string:
 *          YES   (outside the range)
 *    OR    NO    (if good)
 *
 * Creation date: (10/21/2003 3:00:48 PM)
 */
public static String compareToRanges(FormData thisData) 
{
   String returnValue   = "NO";
   try
   {
	  //**NUMBER 
 	  if(thisData.getDataCode().trim().equals("NU"))
 	  { 
	     BigDecimal zero   = new BigDecimal(0);
 	     BigDecimal low    = thisData.getNumericLow();
	     BigDecimal high   = thisData.getNumericHigh();
	
	     if (low.compareTo(zero) != 0 ||
		     high.compareTo(zero) != 0)
	     {
//	        BigDecimal data = thisData.getDataNumeric();
	        if (high.compareTo(zero) == 0)
	           high = new BigDecimal(999999999999.999999999999);

//	        int testLow = low.compareTo(data);
//	        int testHigh = high.compareTo(data); 
	        if (low.compareTo(thisData.getDataNumeric()) > 0 ||
		       high.compareTo(thisData.getDataNumeric()) < 0)
	           returnValue="YES";	
	     }
 	  }
 	  
 	  //**TIME
 	  if(thisData.getDataCode().trim().equals("TM"))
 	  {
	 	 java.sql.Time defaultTime       = java.sql.Time.valueOf("00:00:00");
	     String        defaultTimeString = defaultTime + "";
 	     java.sql.Time low               = thisData.getTimeLow();
 	     String        lowString         = low + "";
	     java.sql.Time high              = thisData.getTimeHigh();
	     String        highString        = high + "";
	     

	     if (!defaultTimeString.equals(lowString) ||
		     !defaultTimeString.equals(highString))
	     {
		    if (defaultTimeString.equals(highString))
		       high = java.sql.Time.valueOf("23:59:59");
		    
			int highSeconds = TimeUtilities.convertSQLTimeToSeconds(thisData.getTimeHigh());		       
			int lowSeconds  = TimeUtilities.convertSQLTimeToSeconds(thisData.getTimeLow());		       
			int dataSeconds = TimeUtilities.convertSQLTimeToSeconds(thisData.getDataTime());		       
		       
			if (dataSeconds < lowSeconds ||
				dataSeconds > highSeconds)
			   returnValue = "YES";		     	
	     }
 	  }

 	  //**DATE
 	  if(thisData.getDataCode().trim().equals("DT"))
 	  {
	 	  java.sql.Date defaultDate       = java.sql.Date.valueOf("1950-01-01");
	 	  String        defaultDateString = defaultDate + "";
	 	  java.sql.Date low               = thisData.getDateLow();
	 	  String        lowString         = low + "";
	 	  java.sql.Date high              = thisData.getDateHigh();
	 	  String        highString        = high + "";
	 	  
	     if (!defaultDateString.equals(lowString) ||
		     !defaultDateString.equals(highString))
	     {
		    if (defaultDateString.equals(highString))
		       high = java.sql.Date.valueOf("2029-12-31");
		       
	        java.sql.Date data = thisData.getDataDate();

	        if (data.compareTo(high) > 0 ||
		        low.compareTo(data) > 0)
	           returnValue = "YES";

	     }	 	  
 	  }
 	  
 	  if(thisData.getDataCode().trim().equals("TX"))
 	  {

		  String defaultString = "";
	      String lowString     = thisData.getTextLow().trim().toUpperCase();
	 	  String highString    = thisData.getTextHigh().trim().toUpperCase();
	 	  
	     if (!lowString.equals(defaultString) ||
		     !highString.equals(defaultString))
	     {
		    String dataString  = thisData.getDataText().trim().toUpperCase();

			int comparelowData = dataString.compareTo(lowString);
			int comparehiData  = dataString.compareTo(highString);
		
	        if (comparelowData < 0 ||
		        comparehiData < 0)
	           returnValue = "YES";
	     }	 	  
 	  } 	  
   }
   catch(Exception e)
   {
	  System.out.println("Problem found in CtlForms.compareToRanges():" + e);
   }

	return returnValue;
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
 *   Sets the variables which will remain in the servlet.
 */
public void init() 
{
try
{
}
catch(Exception e)
{
	System.out.println("Problem when retrieving Javascript code from the " +
		               "JavaScriptInfo Class.  " +
		               "Problem occured in CtlForms.init(): " +
	                   e); 	               
}	
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
	    //** Always test for a message, and send it on.
 	 	String message = request.getParameter("msg");
  	    if (message == null)
          message = "";
		request.setAttribute("message", message);
		
        // Request Types could be,  
        //     inq, list, insert, updateFinish, update, delete, detail
     	//     
	    String requestType = request.getParameter("requestType");
 	  	if (requestType == null)
     	  requestType = "inq";
		request.setAttribute("requestType", requestType);

        String urlAddress = "/web/CtlForms(USE)";
 	//********************************************************************
		// Execute security servlet. Not sure where security will come in.
		//********************************************************************
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
//	    String thisStatus = SessionVariables.getSessionttiSecStatus(request,response);
	    if (!SessionVariables.getSessionttiSecStatus(request,response).equals(""))
		{
			response.sendRedirect("/web/TreeNetInq?msg=" + 
				SessionVariables.getSessionttiSecStatus(request,response));
			return;
		}	    
	      // remove the Status and the Url
	    sess.removeAttribute("ttiTheURL");
	    sess.removeAttribute("ttiSecStatus");

//***************************************************************************
	String sendTo = "/JSP/FormSystem/forms.jsp";
	validateFormNumber(request,
	                   response);
	                   
	// NOT INQUIRY, Validate the Parameters                   
	if (!requestType.equals("inq"))
	{
	   String thisMessage = validateInquiryParameters(request,response);
	   if (!thisMessage.equals(""))
	   {
	   	  requestType = "inq";
	  	  request.setAttribute("requestType", requestType);
		  request.setAttribute("message", thisMessage);
	   }
	}
	                       
//***************************************************************
//   Build the Detail Page
//***************************************************************
	requestType = (String) request.getAttribute("requestType");
	
   if (requestType.equals("detail"))
	  pageDtl(request,response);
	  	                       
//***************************************************************
//    The update;  IF it comes in with a transaction Number
//      it will be updated, if not it will be added.
//***************************************************************
  requestType = (String) request.getAttribute("requestType");
	
	if (requestType.equals("finishUpdate"))
	{
		try
		{
			String problemMsg = validateInputParameters(request,response);
			request.setAttribute("message", problemMsg);
		}
		catch(Exception e)
		{
			System.out.println("Problem in CtlForms.performTask(request, response)" +
				          "problem occurred when validating input parameters: " + e);
		}
		request.setAttribute("requestType", "list");
	}
	
//***************************************************************
//   Build the List Page after deleting a transaction
//    Delete the transaction chosen.
//***************************************************************
  requestType = (String) request.getAttribute("requestType");
	
  if (requestType.equals("delete"))
  {
     String errorMessage = deleteTransaction(request,
                                             response);
     request.setAttribute("message", errorMessage);
     request.setAttribute("requestType", "list");
  }	
	
//***************************************************************
//   Build the List Page
//   The list page may have update capabilities.
//***************************************************************
  requestType = (String) request.getAttribute("requestType");
	
	if (requestType.equals("list"))
	{
	   pageList(request,response);
	   // Can add a if statement around this part when
	   //   not wanted on the list page.
	   pageUpd(request,response);
	}
 //*****************************************************************
 //  Build Inquiry Section 
 //    If a form has already been chosen, it will show
 //       the report inquiry.
 //    IF no form has been chosen it will show the drop down of
 //       all forms.
 //*****************************************************************
 requestType = (String) request.getAttribute("requestType");
	
	if (requestType.equals("inq"))
	   pageInq(request,response);

//*******************************************************************
//  Maintenance Section:
//*******************************************************************
 //   if (requestType.equals("maintain"))
   // {
     //  pageMaintainForm(request, response);	
	   //sendTo = "/JSP/FormSystem/Maintenance/maintainForms.jsp";
    //}
//*******************************************************************    		
  message = (String) request.getAttribute("message");
  		
		//***** Go to the JSP *****//
		getServletConfig().getServletContext().
			getRequestDispatcher(sendTo + 
				"?msg=" + message).
			forward(request, response);	

	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		theException.printStackTrace();
	}
}
/**
 * Delete the chosen Transaction
 * Creation date: (8/21/2003 4:12:18 PM)
 */
private String deleteTransaction(javax.servlet.http.HttpServletRequest request,
                                 javax.servlet.http.HttpServletResponse response) 
{
	String problem = "";
	Integer transactionNumber = null;
	try
	{
		transactionNumber = new Integer(request.getParameter("transactionNumber"));
	}
	catch(Exception e)
	{
	}
	
    try
    {
      if (transactionNumber != null)
      {
		 FormData thisOne = new FormData();
		 boolean error = thisOne.deleteFormData(((Integer) request.getAttribute("formNumber")),
											    transactionNumber);
		 if (error = false)
		 {
	        problem = "Problem when trying to delete transaction number " +
	                    transactionNumber + ".";
         }
	  }     	
    }
    catch(Exception e)
    {
       problem = "Problem occurred while attempting to delete a Record. \\n" +
		                "CtlForms.deleteTransaction(): " + e;
	}
	return problem;	
}
/**
 *   Check fields to Either update or Add a Record
 * 
 * Will decide if the fields are OK.
 *   Will generate a vector of the FormData class
 *   Built from what was input.
 *
 * Finally to return a string, which is the error message
 *
 * Creation date: (9/24/2003 09:10:48 AM)
 */
private String validateInputParameters(javax.servlet.http.HttpServletRequest request,
			 			                javax.servlet.http.HttpServletResponse response) 
{
   // Retrieve in the Update Definition (To Find Information)
   Vector updateDefinition = retrieveFormDefinition(request, response, "update");
   int    definitionSize = 0;
   try
   {
   	 definitionSize = updateDefinition.size();	
   }
   catch(Exception e)
   {
   }
	String testTransactionNumber = request.getParameter("transactionNumber");
	Integer transactionNumber = new Integer("0");
	try
	{
	   if (testTransactionNumber == null)
          transactionNumber = new Integer(FormData.nextTranNumber());
       else
          transactionNumber = new Integer(testTransactionNumber);
	}
    catch(Exception e)
    {
    }
       
	String anyProblem = "";
	//Test all fields, if they are all blank go = "no""
    String go = "no";
    String requiredFieldNotEntered = "";
    
   // For use to get things updated or inserted.
   Vector thisOne = new Vector();
   // Create the vector which will be used to update or insert.
   Vector formUpdateVector  = new Vector();

try
{
	//***************************************************************** 
	 // Retrieve the CURRENT Date/Time & User
	  String dateArray[]        = SystemDate.getSystemDate();
	  java.sql.Date currentDate = java.sql.Date.valueOf(dateArray[7]);
	  java.sql.Time currentTime = java.sql.Time.valueOf(dateArray[8]);
	
   // Use this string for each validation.
	String thisProblem     = "";
   // Get the definition for this input section.
   FormDefinition thisDefinition = (FormDefinition) updateDefinition.elementAt(0);
	//************************************

   // Each Transaction will have an effective Date.
   //   All records within a transaction will have the same effective date.
    java.sql.Date effectiveDate = java.sql.Date.valueOf("1950-01-01");
    java.sql.Date defaultDate   = java.sql.Date.valueOf("1950-01-01");
    java.sql.Time defaultTime   = java.sql.Time.valueOf("00:00:00");
    BigDecimal    defaultNumber = new BigDecimal("0");
    
   //***************************************************************** 
   // Test the effective Date which was input
   try
   {
    	String[] returnValues = validateDate(request.getParameter("inputEffectiveDate"));
    	if (returnValues[0] == null ||
        	returnValues[0].equals(""))
	   		effectiveDate = java.sql.Date.valueOf(returnValues[1]);
		else
	   	anyProblem = anyProblem + returnValues[0];
   }
   catch(Exception e)
   {   	
   		effectiveDate = currentDate;
   }

 
    //Uncomment when Security is in.
   String userProfile = SessionVariables.getSessionttiProfile(request,response);
	String time = "";
   //*****************************************************************
   //  Run through each element in the definition
   //    To check to see if there is anything
   //    entered for that cell(field)
   //*****************************************************************
    String defaultInput = "";
    
    for (int x = 0; x < definitionSize; x++)
    {
	   thisDefinition   = (FormDefinition) updateDefinition.elementAt(x);
	   FormData  inputData     = new FormData();
	   
	if (thisDefinition.getFormula() != null &&
	    thisDefinition.getFormula().trim().length() <= 0)
	   {	 
	   defaultInput = "Y"; 
	   String inputName = "input" + thisDefinition.getColumnNumber();
	   inputData.setDataCode(thisDefinition.getDataCode().trim());
	   inputData.setDataNumber(thisDefinition.getDataNumber());
	   inputData.setRecordDtaId("DA");
	   inputData.setRecordCmtId("");	 
       inputData.setFormNumber(thisDefinition.getFormNumber());
  	   inputData.setColumnNumber(thisDefinition.getColumnNumber());
  	   inputData.setDataType(thisDefinition.getDataType());
	   inputData.setTranNumber(transactionNumber);
	   inputData.setRequiredEntry(thisDefinition.getRequiredEntry());
       inputData.setTranEffDate(effectiveDate);
       inputData.setDataNumeric(defaultNumber);
       inputData.setDataDate(defaultDate);	
	   inputData.setDataTime(defaultTime);		 
       inputData.setUpdateUser(userProfile);
       inputData.setUpdateDate(currentDate);     
       inputData.setUpdateTime(currentTime);

		     
	  //** Time **//
    if (thisDefinition.getDataCode().trim().equals("TM"))
	{
	   if ((request.getParameter("hour" + inputName) != null &&
			   !request.getParameter("hour" + inputName).equals("")) ||
		      (request.getParameter("min" + inputName) != null &&
			   !request.getParameter("min" + inputName).equals("")) ||
              (request.getParameter("sec" + inputName) != null &&
	           !request.getParameter("sec" + inputName).equals("")))
	   {
		     go = "yes";
		     defaultInput = "N";
	   }

	    if ((request.getParameter("hour" + inputName) == null ||
			   request.getParameter("hour" + inputName).equals("")) &&
		      (request.getParameter("min" + inputName) == null ||
			   request.getParameter("min" + inputName).equals("")) &&
              (request.getParameter("sec" + inputName) == null ||
	           request.getParameter("sec" + inputName).equals("")) &&
              thisDefinition.getRequiredEntry().trim().equals("Y"))	 
	          requiredFieldNotEntered = requiredFieldNotEntered +
		            thisDefinition.getHeadingLong().trim() + " is required.\\n";   
	   	
		  time = HTMLHelpers.combineTime3Sections(
			                    request.getParameter("hour" + inputName),
			                    request.getParameter("min" + inputName),
			                    request.getParameter("sec" + inputName),
								"Y");
				
		  String[] returnValues1 = validateTime(time);
          if (returnValues1[0] == null ||
              returnValues1[0].equals(""))
             inputData.setDataTime(java.sql.Time.valueOf(returnValues1[1]));
	      else
	         anyProblem = anyProblem + returnValues1[0];		
    }
    else
	{
 
       if (request.getParameter(inputName) != null &&
	       !request.getParameter(inputName).trim().equals(""))
       {
	       go = "yes";
  	       defaultInput = "N";	   
       }
       else
       {
          if (thisDefinition.getRequiredEntry().trim().equals("Y"))	 
	          requiredFieldNotEntered = requiredFieldNotEntered +
		            thisDefinition.getHeadingLong().trim() + " is required.\\n";   
       }
      //** Number **//
   	   if (thisDefinition.getDataCode().trim().equals("NU"))
	   {
	   	  if (request.getParameter(inputName) != null &&
	   	      !request.getParameter(inputName).trim().equals("") &&
	   	      !request.getParameter(inputName).trim().toLowerCase().equals("none"))
	   	  {    
		     thisProblem = ValidateFields.validateBigDecimal(request.getParameter(inputName));
       	     if (thisProblem.equals(""))
       	     {
	            inputData.setDataNumeric(new BigDecimal(request.getParameter(inputName)));
			    if (thisDefinition.getDataType().trim().toLowerCase().equals("broker"))
			   	{
				  thisProblem = inputData.validateDataByType();
			   	}              
			 }
			 anyProblem = anyProblem + thisProblem;
	   	  }		
	   	       
       }
		  
	  //** Date **//
	   if (thisDefinition.getDataCode().trim().equals("DT"))
	   {
	   	String testDate = request.getParameter(inputName);
	   	if (testDate != null &&
	   	    !testDate.equals(""))
	   	{
			String[] returnValues1 = validateDate(request.getParameter(inputName));
			if (returnValues1[0] == null ||
			    returnValues1[0].equals(""))
		   		inputData.setDataDate(java.sql.Date.valueOf(returnValues1[1]));
			else
		   		anyProblem = anyProblem + returnValues1[0];
	   	}		
	   }
	       
      //** Text **//
	   // Text has to have a record in the data file AND
	   //  a record in the Text File .
	   if (thisDefinition.getDataCode().trim().equals("TX"))
	   {
	   	   thisProblem = "";
		   String thisText = request.getParameter(inputName);
		   if (thisDefinition.getDataType().trim().toLowerCase().equals("customer"))
		   {
		   	  thisText = "";
			  String companyValue = inputName + "Co";
			  String custValue    = inputName + "Cust";
			  String testCo = request.getParameter(companyValue);
			  if (testCo == null)
			     testCo = "";
			  String testCust = request.getParameter(custValue);
			  if (testCust == null)
			     testCust = "";
			     
			  thisText = testCo +
						"_" +
						testCust;
						
		  	  if ((!testCo.equals("") &&
		  	       testCust.equals("")) ||
		  	      (testCo.equals("") &&
		  	       !testCust.equals("")))
		  	  {
			  	  	anyProblem = anyProblem +
			  	  	     "When entering a customer number, you have to input " +
			  	  	     "both the company number AND the customer number.  \n";     
			  }
			  else
			    defaultInput = "N";
		   }
           if (thisText != null &&
               anyProblem.equals(""))
           {
              inputData.setDataText(thisText);
              if (!thisText.equals("") &&
              	  thisDefinition.getDataType().trim().toLowerCase().equals("as400user") ||
              	  thisDefinition.getDataType().trim().toLowerCase().equals("email") ||
              	  thisDefinition.getDataType().trim().toLowerCase().equals("customer"))
			  {
			  	 anyProblem = anyProblem + inputData.validateDataByType();
			  }              
	       }
           else
     	      anyProblem = anyProblem + thisProblem;
     	      
     	      		
	   }		   
	}
	     	  //** Set the class into the vector
	   inputData.setDataDefaulted(defaultInput);
       thisOne.addElement(inputData);          
	   }
	     	  //** Set the class into the vector
       formUpdateVector.addElement(inputData);      
    } // End of the For Loop

 
}
catch(Exception e)
{
	System.out.println("Problem within CtlForms.ValidateInputParameters" +
		               "(request, response).  " + e);  
}
try
{
   if (anyProblem.equals("") &&
	   go.equals("yes") &&
	   requiredFieldNotEntered.equals(""))
   {
	   //  Go actually do the transaction.
	   String thisProblem = createTransaction(thisOne, 
	                                          request, 
	                                          response);
	   if (!thisProblem.equals(""))
	      anyProblem = anyProblem + thisProblem;
   }
   else
   {
      if (go.equals("no"))
      {
          formUpdateVector  = new Vector();
          requiredFieldNotEntered = "";
      }
          
      if (!requiredFieldNotEntered.equals(""))
          anyProblem = anyProblem + requiredFieldNotEntered;
   }
 
   if (!anyProblem.equals(""))
      request.setAttribute("formUpdateVector", formUpdateVector);
   
}
catch(Exception e)
{
	System.out.println("Problem when trying to update/insert a transaction.  " +
	                   "Method called from validateInputParameters(request,response): " +
	                   e); 
}
   
	return anyProblem;
}
/**
 * This Method will take in a parameter for form number.
 *   Test to make sure the form number exists.
 * 
 *    Test to see if there is already a session variable, 
 *       for the definition,  of the specific form asked for.
 *    If not reset the session variable using the NEW form number.
 *    If a NEW form definition, reset appropriate session variables.
 * 
 *    Will ALSO test the Transaction Number if applicable
 * 
 * Finally to return a string, which is the error message
 *
 * Creation date: (7/27/2004 4:22:48 PM)
 */
private static Vector retrieveFormDefinition(javax.servlet.http.HttpServletRequest request,
			 			              javax.servlet.http.HttpServletResponse response,
			 			              String definitionType) 
{
  Vector returnDefinition = new Vector();
try
{
	String[] userGroups  = SessionVariables.getSessionttiUserGroups(request,response);
	if (userGroups != null && 
		userGroups.length == 1 &&
		userGroups[0].trim().equals(""))
	   userGroups = null;
	
	try
	{
		if (definitionType.equals("inquiry"))
		   returnDefinition = FormDefinition.
			   findDefinitionByFormByInquiry(((Integer) request.getAttribute("formNumber")),
									SessionVariables.getSessionttiUserRoles(request,response),
									userGroups,
									SessionVariables.getSessionttiProfile(request,response));
									
		if (definitionType.equals("list"))
		   returnDefinition = FormDefinition.
			   findDefinitionByFormByView(((Integer) request.getAttribute("formNumber")),
									SessionVariables.getSessionttiUserRoles(request,response),
									userGroups,
									SessionVariables.getSessionttiProfile(request,response));
									
		if (definitionType.equals("update"))
		   returnDefinition = FormDefinition.
			   findDefinitionByFormByEntry(((Integer) request.getAttribute("formNumber")),
			   						new Integer("0"),
									SessionVariables.getSessionttiUserRoles(request,response),
									userGroups,
									SessionVariables.getSessionttiProfile(request,response));
									
		if (definitionType.equals(""))
		   returnDefinition = FormDefinition.
			   findDefinitionByFormByForm((Integer) request.getAttribute("formNumber"));
									
	}
	catch(Exception e)
	{} 

}
catch(Exception e)
{
	System.out.println("Problem within CtlForms.retrieveFormDefinition" +
		               "(request, response).  " + e);  
}
	return returnDefinition;
}
}
