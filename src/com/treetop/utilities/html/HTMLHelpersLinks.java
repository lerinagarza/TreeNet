/*
 * Created on Jul 29, 2005
 */
package com.treetop.utilities.html;

import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.net.URLEncoder;
import com.ibm.as400.access.*;
import com.treetop.utilities.FindAndReplace;


/**
 * @author twalto
 * 
 * Use this class to put Standard HTML Code for Links
 * 		into, for use in JSP's
 * 10/28/11 Twalton - removed methods getDirectoryFromPathAS400 and getFoldersFromPathAS400
 *    TreeNet61 Project has the data if needed
 */
public class HTMLHelpersLinks {
	/**
	 * Receive In:
	 *     String to      -- Who the email is supposed to be Sent to
	 * 						    Can be blank and filled in Later
	 *     String from    -- Who the email is supposed to be Sent from
	 * 						    Can be blank and filled in Later
	 *     String subject -- Subject of the Email
	 *     String body    -- Text for the body of the email
	 *
	 * Return:
	 *     String - Link to the Email Servlet, in the form of a button
	 *
	 * Creation date: (12/5/2005 1:00:37 PM  -- TWalton)
	 */
	public static String buttonEmail(String to,
		                             String from,
		                             String subject,
		                             String body) 
	{
		StringBuffer returnString = new StringBuffer();
		if (to == null)
		  to = "";
		if (from == null)
		  from = "";
		if (subject == null)
		  subject = "";
		if (body == null)
		  body = "";
		returnString.append("<button onClick=\"window.open('");
		returnString.append("CtlEmail?");
        returnString.append("emailTo=");
        returnString.append(to.trim());
        returnString.append("&emailFrom=");
        returnString.append(from.trim());
        returnString.append("&emailSubject=");
        returnString.append(subject.trim());
        returnString.append("&additionalBody=");
        returnString.append(FindAndReplace.remove(body.trim(), "'"));
        returnString.append("')\">");
        returnString.append("Email");
        returnString.append("</button> ");
	
		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String salesOrderValue     -- What the Sales Order Number IS... 
	 *     String invoiceValue        -- What the Invoice Number is...
	 *  USE EITHER Sales Order Number OR Invoice Number
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a04001";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionaParameters -- IF there are other parameters that should get added 
	 *                                   to the link
	 *
	 * Return:
	 *     String - Link to the sonumRouter, built based on input parameters
	 *
	 * Creation date: (1/7/2005 11:32:37 AM)
	 * Update date:  11/4/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	 */
	public static String routerSalesOrder(
		String salesOrderValue,
		String invoiceValue,
		String linkClass,
		String additionalStyle,
		String additionalParameters) {

		StringBuffer returnString = new StringBuffer();
		returnString.append(salesOrderValue);
//		if ((salesOrderValue != null && !salesOrderValue.trim().equals(""))
//			|| invoiceValue != null
//			&& !invoiceValue.trim().equals("")) {
//			if (linkClass == null || linkClass.trim().equals(""))
//				linkClass = "a04001";
//			if (additionalStyle == null || additionalStyle.trim().equals(""))
//				additionalStyle = "";
//			else
//				additionalStyle = "style=\"" + additionalStyle + "\" ";
//			if (additionalParameters == null
//				|| additionalParameters.trim().equals(""))
//				additionalParameters = "";
//			else {
//				if (!additionalParameters.substring(0, 1).equals("&"))
//					additionalParameters = "&" + additionalParameters;
//			}//

//			returnString.append("<a class=\"");
//			returnString.append(linkClass);
//			returnString.append("\" ");
//			returnString.append(additionalStyle);
//			returnString.append("href=\"/web/JSP/Router/sonumRouter.jsp?");
//			if (invoiceValue != null && !invoiceValue.trim().equals("")) {
//				returnString.append("invoice=");
//				returnString.append(invoiceValue.trim());
//			} else {
//				returnString.append("sonum=");
//				returnString.append(salesOrderValue.trim());
//			}
//			returnString.append(additionalParameters);
//			returnString.append("\" ");
//			returnString.append("target=\"_blank\"");
//			returnString.append("title=\"");
//			returnString.append(
//				"Click here to look for more information about this");
//			if (invoiceValue != null && !invoiceValue.trim().equals("")) {
//				returnString.append(" Invoice: ");
//				returnString.append(invoiceValue.trim());
//			} else {
//				returnString.append(" Sales Order: ");
//				returnString.append(salesOrderValue.trim());
//			}
//			returnString.append("\">");
//			if (invoiceValue != null && !invoiceValue.trim().equals(""))
//				returnString.append(invoiceValue.trim());
//			else
//				returnString.append(salesOrderValue.trim());
//			returnString.append("</a>");
//		}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String specificationValue  -- What the Specification Code IS... 
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a04001";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionaParameters -- IF there are other parameters that should get added 
	 *                                   to the link
	 *
	 * Return:
	 *     String - Link to the SpecificationRouter, built based on input parameters
	 *
	 * Creation date: (3/31/2005 11:51:37 AM)
	 * Update date:  11/4/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	 */
	public static String routerSpecification(String specificationValue,
					                         String linkClass,
		    			                     String additionalStyle,
		                			         String additionalParameters) 
	{
		StringBuffer returnString = new StringBuffer();

		if (specificationValue != null && !specificationValue.trim().equals("")) {
			if (linkClass == null || linkClass.trim().equals(""))
				linkClass = "a04001";
			if (additionalStyle == null || additionalStyle.trim().equals(""))
				additionalStyle = "";
			else
				additionalStyle = "style=\"" + additionalStyle + "\" ";
			if (additionalParameters == null
				|| additionalParameters.trim().equals(""))
				additionalParameters = "";
			else {
				if (!additionalParameters.substring(0, 1).equals("&"))
					additionalParameters = "&" + additionalParameters;
			}
			String mouseoverValue =
				"Click here to look for more information about Specification: "
					+ specificationValue.trim();

			returnString.append("<a class=\"");
			returnString.append(linkClass);
			returnString.append("\" ");
			returnString.append(additionalStyle);
			returnString.append("href=\"/web/JSP/Router/specificationRouter.jsp?");
			returnString.append("specification=");
			returnString.append(URLEncoder.encode(specificationValue.trim()));
			returnString.append(additionalParameters);
			returnString.append("\" ");
			returnString.append("target=\"_blank\"");
			returnString.append("title=\"");
			returnString.append(mouseoverValue);
			returnString.append("\">");
			returnString.append(specificationValue.trim());
			returnString.append("</a>");
		}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");

		return returnString.toString();		

	}
	/**
	 * Receive In:
	 *     String codeValue            -- What the lot Number IS... 
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a04001";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionaParameters -- IF there are other parameters that should get added 
	 *                                   to the link
	 *
	 * Return:
	 *     String - Link to the LotRouter, built based on input parameters
	 *
	 * Creation date: (12/3/2004 8:30:37 AM)
	 * Update date:  11/4/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	 * @deprecated
	 *    Remove code after April 30,2013 -- Not being used
	 */
	public static String routerAnalyticalCode(String codeValue,
					                           String linkClass,
		    			                       String additionalStyle,
		                			           String additionalParameters) 
	{
		
		StringBuffer returnString = new StringBuffer();

		if (codeValue != null && !codeValue.trim().equals("")) {
			if (linkClass == null || linkClass.trim().equals(""))
				linkClass = "a04001";
			if (additionalStyle == null || additionalStyle.trim().equals(""))
				additionalStyle = "";
			else
				additionalStyle = "style=\"" + additionalStyle + "\" ";
			if (additionalParameters == null
				|| additionalParameters.trim().equals(""))
				additionalParameters = "";
			else {
				if (!additionalParameters.substring(0, 1).equals("&"))
					additionalParameters = "&" + additionalParameters;
			}
			String mouseoverValue =
				"Click here to look for more information about Analytical Code: "
					+ codeValue.trim();

			returnString.append("<a class=\"");
			returnString.append(linkClass);
			returnString.append("\" ");
			returnString.append(additionalStyle);
			returnString.append("href=\"/web/JSP/Router/analyticalCodeRouter.jsp?");
			returnString.append("code=");
			returnString.append(URLEncoder.encode(codeValue.trim()));
			returnString.append(additionalParameters);
			returnString.append("\" ");
			returnString.append("target=\"_blank\"");
			returnString.append("title=\"");
			returnString.append(mouseoverValue);
			returnString.append("\">");
			returnString.append(codeValue.trim());
			returnString.append("</a>");
		}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");

		return returnString.toString();		
	}
	/**
	 * Receive In:
	 *     String brokerValue         -- What the broker Number IS... 
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a04001";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionaParameters -- IF there are other parameters that should get added 
	 *                                   to the link
	 *
	 * Return:
	 *     String - Link to the BrokerRouter, built based on input parameters
	 *
	 * Creation date: (1/7/2005 11:16:37 AM)
	 * Update date:  11/4/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	 */
	public static String routerBroker(String brokerValue,
		                              String linkClass,
		                              String additionalStyle,
		                              String additionalParameters) 
	{
		
		StringBuffer returnString = new StringBuffer();
		returnString.append(brokerValue);

		//if (brokerValue != null && !brokerValue.trim().equals("")) {
		//	if (linkClass == null || linkClass.trim().equals(""))
		//		linkClass = "a04001";
		//	if (additionalStyle == null || additionalStyle.trim().equals(""))
		//		additionalStyle = "";
		//	else
		//		additionalStyle = "style=\"" + additionalStyle + "\" ";
		//	if (additionalParameters == null
		//		|| additionalParameters.trim().equals(""))
		//		additionalParameters = "";
		//	else {
		//		if (!additionalParameters.substring(0, 1).equals("&"))
		//			additionalParameters = "&" + additionalParameters;
		//	}
		//	String mouseoverValue =
		//		"Click here to look for more information about Broker: "
		//			+ brokerValue.trim();

			//returnString.append("<a class=\"");
//			returnString.append(linkClass);
//			returnString.append("\" ");
//			returnString.append(additionalStyle);
//			returnString.append("href=\"/web/JSP/Router/brokerRouter.jsp?");
	//		returnString.append("brokerNumber=");
	//		returnString.append(brokerValue.trim());
	//		returnString.append(additionalParameters);
	//		returnString.append("\" ");
	//		returnString.append("target=\"_blank\"");
	//		returnString.append("title=\"");
	//		returnString.append(mouseoverValue);
	//		returnString.append("\">");
	//		returnString.append(brokerValue.trim());
	//		returnString.append("</a>");
	//	}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");

		return returnString.toString();		

	}
	/**
	 * Receive In:
	 *     String customerValue       -- What the customer Number IS... 
	 *     String companyNumberValue  -- What is the Company Number
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a04001";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionaParameters -- IF there are other parameters that should get added 
	 *                                   to the link
	 *
	 * Return:
	 *     String - Link to the CustomerRouter, built based on input parameters
	 *
	 * Creation date: (1/7/2005 11:17:37 AM)
	 * Update date:  11/4/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	 */
	public static String routerCustomer(
		String customerValue,
		String companyNumberValue,
		String linkClass,
		String additionalStyle,
		String additionalParameters) {

		StringBuffer returnString = new StringBuffer();
		returnString.append(customerValue);
//		if (customerValue != null && !customerValue.trim().equals("")) {
//			if (linkClass == null || linkClass.trim().equals(""))
//				linkClass = "a04001";
//			if (additionalStyle == null || additionalStyle.trim().equals(""))
//				additionalStyle = "";
//			else
//				additionalStyle = "style=\"" + additionalStyle + "\" ";
//			if (additionalParameters == null
//				|| additionalParameters.trim().equals(""))
//				additionalParameters = "";
//			else {
//				if (!additionalParameters.substring(0, 1).equals("&"))
//					additionalParameters = "&" + additionalParameters;
//			}
//			String mouseoverValue =
//				"Click here to look for more information about Customer: "
//					+ customerValue.trim();

//			returnString.append("<a class=\"");
//			returnString.append(linkClass);
//			returnString.append("\" ");
//			returnString.append(additionalStyle);
//			returnString.append("href=\"/web/JSP/Router/customerRouter.jsp?");
//			returnString.append("customerNumber=");
//			returnString.append(customerValue.trim());
//			returnString.append("&companyNumber=");
//			returnString.append(companyNumberValue.trim());
//			returnString.append(additionalParameters);
//			returnString.append("\" ");
//			returnString.append("target=\"_blank\"");
//			returnString.append("title=\"");
//			returnString.append(mouseoverValue);
//			returnString.append("\">");
//			returnString.append(customerValue.trim());
//			returnString.append("</a>");
//		}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String formulaValue        -- What the formula Number IS... 
	 *     String revisedDateValue    -- What is the Revised Date
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a04001";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionaParameters -- IF there are other parameters that should get added 
	 *                                   to the link
	 *
	 * Return:
	 *     String - Link to the FormulaRouter, built based on input parameters
	 *
	 * Creation date: (1/7/2005 11:15:37 AM)
	 * Update date:  11/4/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	 */
	public static String routerFormula(String formulaValue,
								       String revisedDateValue,
		                               String linkClass,
		                               String additionalStyle,
		                               String additionalParameters) 
	{
		StringBuffer returnString = new StringBuffer();

		if (formulaValue != null && !formulaValue.trim().equals("")) {
			if (linkClass == null || linkClass.trim().equals(""))
				linkClass = "a0414";
			if (additionalStyle == null || additionalStyle.trim().equals(""))
				additionalStyle = "";
			else
				additionalStyle = "style=\"" + additionalStyle + "\" ";
			if (additionalParameters == null
				|| additionalParameters.trim().equals(""))
				additionalParameters = "";
			else {
				if (!additionalParameters.substring(0, 1).equals("&"))
					additionalParameters = "&" + additionalParameters;
			}
			String mouseoverValue =
				"Click here to look for more information about Formula: "
					+ formulaValue.trim();

			returnString.append("<a class=\"");
			returnString.append(linkClass);
			returnString.append("\" ");
			returnString.append(additionalStyle);
			returnString.append("href=\"/web/JSP/Router/formulaRouter.jsp?");
			returnString.append("formula=");
			returnString.append(formulaValue.trim());
			returnString.append("&revised=");
			returnString.append(revisedDateValue.trim());
			returnString.append(additionalParameters);
			returnString.append("\" ");
			returnString.append("target=\"_blank\"");
			returnString.append("title=\"");
			returnString.append(mouseoverValue);
			returnString.append("\">");
			returnString.append(formulaValue.trim());
			returnString.append("</a>");
		}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String growerWarehouseValue         -- What the Grower Warehouse Number IS... 
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a04001";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionaParameters -- IF there are other parameters that should get added 
	 *                                   to the link
	 *
	 * Return:
	 *    *** Currently going to the grower warehouse DETAIL page.
	 *     String - Link to the GrowerWarehouseRouter, built based on input parameters
	 *
	 * Creation date: (3/15/2005 11:02:37 AM)
	 * Update date:  11/4/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	 */
	public static String routerGrowerWarehouse(String growerWarehouseValue,
		                              String linkClass,
		                              String additionalStyle,
		                              String additionalParameters) 
	{
		StringBuffer returnString = new StringBuffer();

		if (growerWarehouseValue != null && !growerWarehouseValue.trim().equals("")) {
			if (linkClass == null || linkClass.trim().equals(""))
				linkClass = "a04001";
			if (additionalStyle == null || additionalStyle.trim().equals(""))
				additionalStyle = "";
			else
				additionalStyle = "style=\"" + additionalStyle + "\" ";
			if (additionalParameters == null
				|| additionalParameters.trim().equals(""))
				additionalParameters = "";
			else {
				if (!additionalParameters.substring(0, 1).equals("&"))
					additionalParameters = "&" + additionalParameters;
			}
			String mouseoverValue =
				"Click here to look for more information about Grower Warehouse: "
					+ growerWarehouseValue.trim();

			returnString.append("<a class=\"");
			returnString.append(linkClass);
			returnString.append("\" ");
			returnString.append(additionalStyle);
			//returnString.append("href=\"/web/JSP/Router/growerWarehouseRouter.jsp?");
			returnString.append("href=\"/web/CtlGrowers?requestType=detailWarehouse&");
			returnString.append("growerWarehouse=");
			returnString.append(growerWarehouseValue.trim());
			returnString.append(additionalParameters);
			returnString.append("\" ");
			returnString.append("target=\"_blank\"");
			returnString.append("title=\"");
			returnString.append(mouseoverValue);
			returnString.append("\">");
			returnString.append(growerWarehouseValue.trim());
			returnString.append("</a>");
		}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String lotValue            -- What the lot Number IS... 
	 *     String lotTypeValue        -- What is the Lot Type, IE. RF, FG, CN
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a04001";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionaParameters -- IF there are other parameters that should get added 
	 *                                   to the link
	 *
	 * Return:
	 *     String - Link to the LotRouter, built based on input parameters
	 *
	 * Creation date: (12/3/2004 8:30:37 AM)
	 * Update date:  11/4/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	 */
	public static String routerLot(String lotValue,
								   String lotTypeValue,
		                           String linkClass,
		                           String additionalStyle,
		                           String additionalParameters) 
	{
		StringBuffer returnString = new StringBuffer();

		if (lotValue != null && !lotValue.trim().equals("")) {
			if (linkClass == null || linkClass.trim().equals(""))
				linkClass = "a04001";
			if (additionalStyle == null || additionalStyle.trim().equals(""))
				additionalStyle = "";
			else
				additionalStyle = "style=\"" + additionalStyle + "\" ";
			if (additionalParameters == null
				|| additionalParameters.trim().equals(""))
				additionalParameters = "";
			else {
				if (!additionalParameters.substring(0, 1).equals("&"))
					additionalParameters = "&" + additionalParameters;
			}
			String mouseoverValue =
				"Click here to look for more information about Lot: "
					+ lotValue.trim();

			returnString.append("<a class=\"");
			returnString.append(linkClass);
			returnString.append("\" ");
			returnString.append(additionalStyle);
			returnString.append("href=\"/web/JSP/Router/lotRouter.jsp?");
			returnString.append("lot=");
			returnString.append(lotValue.trim());
			returnString.append("&lotType=");
			returnString.append(lotTypeValue.trim());
			returnString.append(additionalParameters);
			returnString.append("\" ");
			returnString.append("target=\"_blank\"");
			returnString.append("title=\"");
			returnString.append(mouseoverValue);
			returnString.append("\">");
			returnString.append(lotValue.trim());
			returnString.append("</a>");
		}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");

		return returnString.toString();
		
	}
	/**
	 * Receive In:
	 *     String methodValue         -- What the method Number IS... 
	 *     String revisedDateValue    -- What is the Revised Date
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a04001";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionaParameters -- IF there are other parameters that should get added 
	 *                                   to the link
	 *
	 * Return:
	 *     String - Link to the MethodRouter, built based on input parameters
	 *
	 * Creation date: (1/7/2005 11:15:37 AM)
	 * Update date:  11/4/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	 */
	public static String routerMethod(String methodValue,
								      String revisedDateValue,
		                              String linkClass,
		                              String additionalStyle,
		                              String additionalParameters) 
	{
		StringBuffer returnString = new StringBuffer();

		if (methodValue != null && !methodValue.trim().equals("")) {
			if (linkClass == null || linkClass.trim().equals(""))
				linkClass = "a04001";
			if (additionalStyle == null || additionalStyle.trim().equals(""))
				additionalStyle = "";
			else
				additionalStyle = "style=\"" + additionalStyle + "\" ";
			if (additionalParameters == null
				|| additionalParameters.trim().equals(""))
				additionalParameters = "";
			else {
				if (!additionalParameters.substring(0, 1).equals("&"))
					additionalParameters = "&" + additionalParameters;
			}
			String mouseoverValue =
				"Click here to look for more information about Method: "
					+ methodValue.trim();

			returnString.append("<a class=\"");
			returnString.append(linkClass);
			returnString.append("\" ");
			returnString.append(additionalStyle);
			returnString.append("href=\"/web/JSP/Router/methodRouter.jsp?");
			returnString.append("method=");
			returnString.append(methodValue.trim());
			returnString.append("&revised=");
			returnString.append(revisedDateValue.trim());
			returnString.append(additionalParameters);
			returnString.append("\" ");
			returnString.append("target=\"_blank\"");
			returnString.append("title=\"");
			returnString.append(mouseoverValue);
			returnString.append("\">");
			returnString.append(methodValue.trim());
			returnString.append("</a>");
		}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String offerValue          -- What the Offer 
	 *     String offerSequenceValue  -- What is the Offer Sequence
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a04001";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionaParameters -- IF there are other parameters that should get added 
	 *                                   to the link
	 *
	 * Return:
	 *     String - Link to the OfferRouter, built based on input parameters
	 *
	 * Creation date: (1/7/2005 11:15:37 AM)
	 * Update date:  11/4/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	 */
	public static String routerOffer(String offerValue,
								     String offerSequenceValue,
		                             String linkClass,
		                             String additionalStyle,
		                             String additionalParameters) 
	{
		StringBuffer returnString = new StringBuffer();
		returnString.append(offerValue);
//		if (offerValue != null && !offerValue.trim().equals("")) {
//			if (linkClass == null || linkClass.trim().equals(""))
//				linkClass = "a04001";
//			if (additionalStyle == null || additionalStyle.trim().equals(""))
//				additionalStyle = "";
//			else
//				additionalStyle = "style=\"" + additionalStyle + "\" ";
//			if (additionalParameters == null
//				|| additionalParameters.trim().equals(""))
//				additionalParameters = "";
//			else {
//				if (!additionalParameters.substring(0, 1).equals("&"))
//					additionalParameters = "&" + additionalParameters;
//			}
//			String mouseoverValue =
//				"Click here to look for more information about Offer: "
//					+ offerValue.trim();

//			returnString.append("<a class=\"");
//			returnString.append(linkClass);
//			returnString.append("\" ");
//			returnString.append(additionalStyle);
//			returnString.append("href=\"/web/JSP/Router/offerRouter.jsp?");
//			returnString.append("offernum=");
//			returnString.append(offerValue.trim());
//			returnString.append("&offerseq=");
//			returnString.append(offerSequenceValue.trim());
//			returnString.append(additionalParameters);/
//			returnString.append("\" ");
//			returnString.append("target=\"_blank\"");
//			returnString.append("title=\"");
//			returnString.append(mouseoverValue);
//			returnString.append("\">");
//			returnString.append(offerValue.trim());
//			returnString.append("</a>");
//		}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");

		return returnString.toString();
		
	}
	/**
	 * Receive In:
	 *     String vendorValue       -- What the vendor Number IS... 
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a04001";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionalParameters -- IF there are other parameters that should get added 
	 *                                   to the link - to be sent to the Resource Router
	 *
	 * Return:
	 *     String - Link to the VendorRouter, built based on input parameters
	 *
	 * Creation date: (8/29/2005 TWalton)
	 * Update date: 
	 */
	public static String routerVendor(
		String vendorValue,
		String linkClass,
		String additionalStyle,
		String additionalParameters) {
		StringBuffer returnString = new StringBuffer();
		returnString.append(vendorValue);
//		if (vendorValue != null && !vendorValue.trim().equals("")) {
//			if (linkClass == null || linkClass.trim().equals(""))
//				linkClass = "a04001";
//			if (additionalStyle == null || additionalStyle.trim().equals(""))
//				additionalStyle = "";
//			else
//				additionalStyle = "style=\"" + additionalStyle + "\" ";
//			if (additionalParameters == null
//				|| additionalParameters.trim().equals(""))
//				additionalParameters = "";
//			else {
//				if (!additionalParameters.substring(0, 1).equals("&"))
//					additionalParameters = "&" + additionalParameters;
//			}
//			String mouseoverValue =
//				"Click here to look for more information about Vendor "
//					+ vendorValue.trim();
	
//			returnString.append("<a class=\"");
//			returnString.append(linkClass);
//			returnString.append("\" ");
//			returnString.append(additionalStyle);
//			returnString.append("href=\"/web/JSP/Router/vendorRouter.jsp?");
//			returnString.append("vendorNumber=");
//			returnString.append(vendorValue.trim());
//			returnString.append(additionalParameters);
//			returnString.append("\" ");
//			returnString.append("target=\"_blank\"");
//			returnString.append("title=\"");
//			returnString.append(mouseoverValue);
//			returnString.append("\">");
//			returnString.append(vendorValue.trim());
//			returnString.append("</a>");
//	
//		}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");
	
		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String gtinValue       -- What the Global Trade Item Number IS... 
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a04001";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionalParameters -- IF there are other parameters that should get added 
	 *                                   to the link - to be sent to the Resource Router
	 *
	 * Return:
	 *     String - Link to the GTINRouter, built based on input parameters
	 *
	 * Creation date: (8/29/2005 TWalton)
	 * Update date: 
	 */
	public static String routerGTIN(
		String gtinValue,
		String linkClass,
		String additionalStyle,
		String additionalParameters) {
		StringBuffer returnString = new StringBuffer();
	
		if (gtinValue != null && !gtinValue.trim().equals("")) {
			if (linkClass == null || linkClass.trim().equals(""))
				linkClass = "a04001";
			if (additionalStyle == null || additionalStyle.trim().equals(""))
				additionalStyle = "";
			else
				additionalStyle = "style=\"" + additionalStyle + "\" ";
			if (additionalParameters == null
				|| additionalParameters.trim().equals(""))
				additionalParameters = "";
			else {
				if (!additionalParameters.substring(0, 1).equals("&"))
					additionalParameters = "&" + additionalParameters;
			}
			String mouseoverValue =
				"Click here to look for more information about GTIN "
					+ gtinValue.trim();
	
			returnString.append("<a class=\"");
			returnString.append(linkClass);
			returnString.append("\" ");
			returnString.append(additionalStyle);
			returnString.append("href=\"/web/JSP/Router/gtinRouter.jsp?");
			//returnString.append("href=\"/web/CtlGTIN?");
			//returnString.append("requestType=detail");
			//returnString.append("&gtinNumber=");
			returnString.append("gtin=");
			returnString.append(gtinValue.trim());
			returnString.append(additionalParameters);
			returnString.append("\" ");
			returnString.append("target=\"_blank\"");
			returnString.append("title=\"");
			returnString.append(mouseoverValue);
			returnString.append("\">");
			returnString.append(HTMLHelpersMasking.maskGTINNumber(gtinValue.trim()));
			returnString.append("</a>");
	
		}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");
	
		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String linkUrl    -- Where the image you want to find is located. 
	 *
	 * Return:
	 *     String - Link to the url sent int, with an image of a camera you click on
	 *
	 * Creation date: (1/17/2005 TWalton)
	 * Update date:  8/2/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	
	 */
	public static String imageCamera(String linkUrl) {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		if (linkUrl != null && !linkUrl.trim().equals("")) {
			linkUrl = linkUrl.trim();
			if ((linkUrl.length() > 4 && linkUrl.substring((linkUrl.length() - 4), (linkUrl.length() - 3)).equals(".")) ||
				(linkUrl.length() > 5 && linkUrl.substring((linkUrl.length() - 5),(linkUrl.length() - 4)).equals(".")) ) {
				returnString.append("<a href=\"");
				returnString.append(linkUrl);
				returnString.append("\" target=\"_blank\">");

				String findPicture = imageNotPicture(linkUrl);
				if (findPicture.equals("")) {
					returnString.append("<img border=\"0\" src=\"https://image.treetop.com/webapp/camera.gif\"");
					returnString.append(" title=\"Click on Camera to View Picture\">");
				} else
					returnString.append(findPicture);

				returnString.append("</a>");
			}
		}
		if (returnString.toString().equals(""))
		{	
			returnString.append(
			"<img border=\"0\" src=\"https://image.treetop.com/webapp/null.gif\">");
		}   

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String linkUrl    -- Where the image you want to find is located. 
	 *
	 * Return:
	 *     String - Link to the url sent int, with an image of a camera you click on
	 *
	 * Creation date: (1/17/2005 TWalton)
	 * Update date:  8/2/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	
	 */
	public static String imageCamera(String linkUrl, String showLink) {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		if (linkUrl != null && !linkUrl.trim().equals("")) {
			linkUrl = linkUrl.trim();
			
			int periodSpot = linkUrl.lastIndexOf(".");
			if (periodSpot > 0)
			{
				returnString.append("<a href=\"");
				returnString.append(linkUrl);
				returnString.append("\" target=\"_blank\">");
				String kindOfDoc = linkUrl.substring(periodSpot, linkUrl.length());
				if (kindOfDoc.trim().equals(".gif") ||
					kindOfDoc.trim().equals(".jpg"))
				{
					returnString.append("<img border=\"0\" src=\"https://image.treetop.com/webapp/camera.gif\"");
					returnString.append(" title=\"Click on Camera to View Picture\">");
				}else{
					returnString.append(imageNotPicture(linkUrl));	
				}
				if (showLink.trim().equals("Y"))
					returnString.append("&nbsp;&nbsp;" + linkUrl);
				returnString.append("</a>");
				
			}
		}
		if (returnString.toString().equals(""))
		{	
			returnString.append(
			"<img border=\"0\" src=\"https://image.treetop.com/webapp/null.gif\">");
		}   

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String linkUrl    -- Where the image you want to find is located. 
	 *     int    width      -- width of displayed image default if 0 of 20px
	 *     int    height     -- height of displayed image default if 0 of 20px
	 *
	 * Return:
	 *     String - Link to the url sent int, with an image of a camera you click on
	 *
	 * Creation date: (1/17/2005 TWalton)
	 * Update date:  8/2/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	
	 */
	public static String imageThumbnail(
		String linkUrl,
		int width,
		int height) {

		if (width == 0)
			width = 20;
		if (height == 0)
			height = 20;

		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		if (linkUrl != null && !linkUrl.trim().equals("")) {
			linkUrl = linkUrl.trim();
			if (linkUrl.length() > 4
				&& linkUrl.substring(
					(linkUrl.length() - 4),
					(linkUrl.length() - 3)).equals(
					".")) {
				returnString.append("<a href=\"");
				returnString.append(linkUrl);
				returnString.append("\" target=\"_blank\">");

				String findPicture = imageNotPicture(linkUrl);
				if (findPicture.equals("")) {
					returnString.append("<img border=\"0\" src=\"");
					returnString.append(linkUrl);
					returnString.append("\"");
					returnString.append(" width=\"");
					returnString.append(width);
					returnString.append("\"");
					returnString.append(" height=\"");
					returnString.append(height);
					returnString.append("\"");
					returnString.append(" title=\"Click on Image to Enlarge\"");
					returnString.append(">");
				} else
					returnString.append(findPicture);

				returnString.append("</a>");
			}
		}
		if (returnString.toString().equals(""))
			returnString.append("&nbsp;");

		return returnString.toString();
	}
	/**
	 *   Decide if an image is not a picture, called from the 
	 * 		imageCamera method and the imageThumbnail method
	 * Receive In:
	 *     String linkUrl    -- Where the image you want to find is located. 
	 *
	 * Return:
	 *     String - Link to the image and title section
	 *
	 * Creation date: (8/2/05 TWalton)
	 */
	private static String imageNotPicture(String linkUrl) {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		if (linkUrl != null && !linkUrl.trim().equals("")) 
		{
			linkUrl = linkUrl.trim();
			// Find the last period in the linkUrl String
			int periodSpot = linkUrl.lastIndexOf(".");
			String kindOfDoc = linkUrl.substring(periodSpot, linkUrl.length());
			if (!kindOfDoc.trim().equals(""))
			{
			   if (kindOfDoc.trim().equals(".doc") ||
				   kindOfDoc.trim().equals(".docx"))
			   {
				  returnString.append("<img border=\"0\" src=\"https://image.treetop.com/webapp/TreeNetImages/iconWord.jpg\"");
				  returnString.append(" title=\"Click Here to View Word Document\">");
			   }
			   if (kindOfDoc.trim().equals(".ppt") ||
				   kindOfDoc.trim().equals(".pptx"))
			   {
				  returnString.append("<img border=\"0\" src=\"https://image.treetop.com/webapp/TreeNetImages/iconPowerpoint.jpg\"");
				  returnString.append(" title=\"Click Here to View Powerpoint Presentation\">");
			   }
			   if (kindOfDoc.trim().equals(".xls") ||
				   kindOfDoc.trim().equals(".xlsx"))
			   {
				   returnString.append("<img border=\"0\" src=\"https://image.treetop.com/webapp/TreeNetImages/iconExcel.jpg\"");
				   returnString.append(" title=\"Click Here to View Excel Spreadsheet\">");
			   }
			   if (kindOfDoc.trim().equals(".pdf"))
			   {
				   returnString.append("<img border=\"0\" src=\"https://image.treetop.com/webapp/TreeNetImages/iconPdf.jpg\"");
				   returnString.append(" title=\"Click Here to View PDF\">");
			   }
			   if (kindOfDoc.trim().equals(".onetoc2"))
			   {
				   returnString.append("<img border=\"0\" src=\"https://image.treetop.com/webapp/TreeNetImages/iconOneNote.jpg\"");
				   returnString.append(" title=\"Click Here to View The One Note\">");
			   }
			   if (returnString.toString().trim().equals(""))
			   {
				   returnString.append("<img border=\"0\" src=\"https://image.treetop.com/webapp/TreeNetImages/iconDefault.gif\"");
				   returnString.append(" title=\"Click Here to View\">");  
			   }
			}
		}

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String displayValue       -- What should be displayed...
	 *     String urlLink            -- The URL 
	 *     String mouseoverValue     -- What the Mouseover Should Say
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a0414";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *
	 * Return:
	 *     String - Return a link
	 *
	 * Creation date: (12/20/2005 TWalton)
	 * Update - TWalton 10/22/08 -- use new Stylesheet
	 */
	public static String basicLink(
		String displayValue,
		String urlLink,
		String mouseoverValue,
		String linkClass,
		String additionalStyle) {
		StringBuffer returnString = new StringBuffer();

		if (urlLink != null && !urlLink.trim().equals("")) {
			
			if (displayValue == null || displayValue.trim().equals(""))
			    displayValue = urlLink;
			if (linkClass == null || linkClass.trim().equals(""))
				linkClass = "a0414";
			if (additionalStyle == null || additionalStyle.trim().equals(""))
				additionalStyle = "";
			else
				additionalStyle = "style=\"" + additionalStyle + "\" ";

			returnString.append("<a class=\"");
			returnString.append(linkClass);
			returnString.append("\" ");
			returnString.append(additionalStyle);
			returnString.append("href=\"");
			returnString.append(urlLink);
			returnString.append("\" ");
			returnString.append("target=\"_blank\"");
			if (mouseoverValue != null && !mouseoverValue.trim().equals(""))
			{
				returnString.append("title=\"");
				returnString.append(mouseoverValue);
				returnString.append("\"");
			}
			returnString.append(">");
			returnString.append(displayValue);
			returnString.append("</a>");

		}
		else
			returnString.append("&nbsp;");

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String urlPath  -- Path from which you want to know which Files are Contained. 
	 *
	 * Return:
	 *     String - Link to the url sent int, with an image of a camera you click on
	 *
	 * Creation date: (9/25/06 TWalton)
    *  To directly point to the NON AS400 ifs Files
	 */
	public static String[] getDirectoryFromPath(String urlPath) {
	
		
		
		
		File dir = new File(urlPath);
		
		String[] directoryNames = null;
		
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return !name.startsWith("~")
					&& !name.endsWith(".js")
					&& name.lastIndexOf(".") > 0;
			}
		};
		try
		{
		   directoryNames = dir.list(filter);
		   Arrays.sort(directoryNames);
		}
		catch(Exception e)
		{
		}
		
		return directoryNames;
	}
	/**
	 * Receive In:
	 *     String urlPath  -- Path from which you want to know which Files are Contained. 
	 *
	 * Return:
	 *     String - Link to the url sent int, with an image of a camera you click on
	 *
	 * Creation date: (9/25/06 TWalton)
    *  To directly point to the NON AS400 ifs Files and Folders
	 */
	public static String[] getFoldersFromPath(String urlPath) {
		
		File dir = new File(urlPath);
		
		String[] directoryNames = null;
		
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.lastIndexOf(".") <= 0;
			}
		};
		try
		{
		   directoryNames = dir.list(filter);
		   Arrays.sort(directoryNames);
		}
		catch(Exception e)
		{
		}

		return directoryNames;
	}	
	/**
	 * Receive In:
	 *     String resourceValue       -- What the resource Number IS... 
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a04001";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionalParameters -- IF there are other parameters that should get added 
	 *                                   to the link - to be sent to the Resource Router
	 *
	 * Return:
	 *     String - Link to the ResourceRouter, built based on input parameters
	 *
	 * Creation date: (12/3/2004 TWalton)
	 * Update date:  7/28/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLLinks
	 */
	public static String routerResource(
		String resourceValue,
		String linkClass,
		String additionalStyle,
		String additionalParameters) {
		StringBuffer returnString = new StringBuffer();
		returnString.append(resourceValue);
//		if (resourceValue != null && !resourceValue.trim().equals("")) {
//			if (linkClass == null || linkClass.trim().equals(""))
//				linkClass = "a04001";
//			if (additionalStyle == null || additionalStyle.trim().equals(""))
//				additionalStyle = "";
//			else
//				additionalStyle = "style=\"" + additionalStyle + "\" ";
//			if (additionalParameters == null
//				|| additionalParameters.trim().equals(""))
//				additionalParameters = "";
//			else {
//				if (!additionalParameters.substring(0, 1).equals("&"))
//					additionalParameters = "&" + additionalParameters;
//			}
//			String mouseoverValue =
//				"Click here to look for more information about resource "
//					+ resourceValue.trim();
	
//			returnString.append("<a ");
//			if (!linkClass.toLowerCase().equals("none"))
//			{
//				returnString.append("class=\"");
//				returnString.append(linkClass);
//				returnString.append("\" ");
//			}
//			returnString.append(additionalStyle);
//			returnString.append("href=\"");
//			if (linkClass.toLowerCase().equals("none"))
//			   returnString.append("https://treenet.treetop.com");
//			returnString.append("/web/JSP/Router/resourceRouter.jsp?");
//			returnString.append("resource=");
//			returnString.append(resourceValue.trim());
//			returnString.append(additionalParameters);
//			returnString.append("\" ");
//			returnString.append("target=\"_blank\"");
//			if (!linkClass.toLowerCase().equals("none"))
//			{	
//			  returnString.append("title=\"");
//			  returnString.append(mouseoverValue);
//			  returnString.append("\"");
//			}
//			returnString.append(">");
//			returnString.append(resourceValue.trim());
//			returnString.append("</a>");
//	
//		}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");
	
		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String linkUrl    -- Where the folder you want to find is located. 
	 * Return:
	 *     String - Link to a display within the IFS for this field, with an image of a folder you click on
	 *
	 * Creation date: (5/9/2006 TWalton)
	 */
	public static String imageFolder(String linkUrl, String findIFSValue) {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		if (linkUrl != null && !linkUrl.trim().equals("")) {
			linkUrl = linkUrl.trim();
			returnString.append("<a href=\"");
			returnString.append("/web/APP/Utilities/displayIFSOnly.jsp?linkURL=");
			returnString.append(linkUrl.trim());
			returnString.append("&findIFS=");
			returnString.append(findIFSValue.trim());
			returnString.append("\" target=\"_blank\">");

			returnString.append("<img border=\"0\" src=\"https://image.treetop.com/webapp/FolderClosedSmall.gif\"");
			returnString.append(" title=\"Click on Folder to Go the What is in that Folder\">");
			returnString.append("</a>");
		}
		if (returnString.toString().equals(""))
			returnString.append("&nbsp;");
	
		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String displayValue       -- What should be displayed...
	 *     String urlLink            -- The URL 
	 *     String mouseoverValue     -- What the Mouseover Should Say
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a0414";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *
	 * Return:
	 *     String - Return a link
	 *
	 * Creation date: (12/20/2005 TWalton)
	 * Update - TWalton 10/22/08 -- use new Stylesheet
	 */
	public static String basicLinkSamePage(
		String displayValue,
		String urlLink,
		String mouseoverValue,
		String linkClass,
		String additionalStyle) {
		StringBuffer returnString = new StringBuffer();
	
		if (urlLink != null && !urlLink.trim().equals("")) {
			
			if (displayValue == null || displayValue.trim().equals(""))
			    displayValue = urlLink;
			if (linkClass == null || linkClass.trim().equals(""))
				linkClass = "a0414";
			if (additionalStyle == null || additionalStyle.trim().equals(""))
				additionalStyle = "";
			else
				additionalStyle = "style=\"" + additionalStyle + "\" ";
	
			returnString.append("<a class=\"");
			returnString.append(linkClass);
			returnString.append("\" ");
			returnString.append(additionalStyle);
			returnString.append("href=\"");
			returnString.append(urlLink);
			returnString.append("\" ");
			if (mouseoverValue != null && !mouseoverValue.trim().equals(""))
			{
				returnString.append("title=\"");
				returnString.append(mouseoverValue);
				returnString.append("\"");
			}
			returnString.append(">");
			returnString.append(displayValue);
			returnString.append("</a>");
	
		}
		else
			returnString.append("&nbsp;");
	
		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     String itemValue           -- What the item Number IS... 
	 *     String linkClass           -- the class from the Stylesheet for the link
	 *            default = "a0412";
	 *     String additionalStyle     -- Any Style change, for example, font size....
	 *     String additionalParameters -- IF there are other parameters that should get added 
	 *                                   to the link - to be sent to the Resource Router
	 *
	 * Return:
	 *     String - Link to the ResourceRouter, built based on input parameters
	 *
	 * Creation date: (6/5/2008 TWalton)
	 *      Will replace the routerResource
	 */
	public static String routerItem(String itemValue,
									String linkClass,
									String additionalStyle,
									String additionalParameters) 
	{
		StringBuffer returnString = new StringBuffer();
	
		if (itemValue != null && !itemValue.trim().equals("")) {
			if (linkClass == null || linkClass.trim().equals(""))
				linkClass = "a0412";
			if (additionalStyle == null || additionalStyle.trim().equals(""))
				additionalStyle = "";
			else
				additionalStyle = "style=\"" + additionalStyle + "\" ";
			if (additionalParameters == null
				|| additionalParameters.trim().equals(""))
				additionalParameters = "";
			else {
				if (!additionalParameters.substring(0, 1).equals("&"))
					additionalParameters = "&" + additionalParameters;
			}
			String mouseoverValue =
				"Click here to look for more information about item "
					+ itemValue.trim();
	
			returnString.append("<a ");
			if (!linkClass.toLowerCase().equals("none"))
			{
				returnString.append("class=\"");
				returnString.append(linkClass);
				returnString.append("\" ");
			}
			returnString.append(additionalStyle);
			returnString.append("href=\"");
			if (linkClass.toLowerCase().equals("none"))
			   returnString.append("https://treenet.treetop.com");
			returnString.append("/web/JSP/Router/itemRouter.jsp?");
			returnString.append("item=");
			returnString.append(itemValue.trim());
			returnString.append(additionalParameters);
			returnString.append("\" ");
			returnString.append("target=\"_blank\"");
			if (!linkClass.toLowerCase().equals("none"))
			{	
			  returnString.append("title=\"");
			  returnString.append(mouseoverValue);
			  returnString.append("\"");
			}
			returnString.append(">");
			returnString.append(itemValue.trim());
			returnString.append("</a>");
	
		}
		if (returnString == null || returnString.toString().trim().equals(""))
			returnString.append("&nbsp;");
	
		return returnString.toString();
	}
}
