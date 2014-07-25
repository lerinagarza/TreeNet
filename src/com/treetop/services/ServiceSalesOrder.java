/*
 * Created on October 19, 2007
 *
 */
package com.treetop.services;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;

import javax.sql.*;
import com.ibm.as400.access.*;

//import com.lawson.m3.conversion.ConversionNotesFileData;
import com.treetop.*;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.app.coa.*;
import com.treetop.utilities.ConnectionStack;
import com.treetop.utilities.FindAndReplace;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.html.DropDownSingle;

/**
 * @author twalto .
 *
 * Services class to obtain and return data 
 * to business objects.
 * 
 * Certificate of Analysis 
 */
public class ServiceSalesOrder extends BaseService {

	public static final String library = "M3DJDPRD.";
   /**
	* 
	*/
	public ServiceSalesOrder() {
		super();
	}
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		try
		{
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Load class fields from result set.
	 */
	public static SalesOrder loadFieldsSalesOrderHeader(String requestType,
								                        ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		SalesOrder returnSO = new SalesOrder();
	
		try{ 
			if (requestType.equals("loadHeader"))
			{
				try
				{
//					 Sales Order  File -- OOHEAD
					 returnSO.setOrderNumber(rs.getString("OAORNO").trim());
					 returnSO.setCustomerPONumber(rs.getString("OACUOR").trim());
					 returnSO.setCustomerNumber(rs.getString("OACUNO").trim());
					 returnSO.setOrderType(rs.getString("OAORTP").trim());
					 returnSO.setShipDate(rs.getString("OAORDT").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}
				try
				{
//					 Customer Detail File -- OCUSMA
					 returnSO.setCustomerName(rs.getString("OKCUNM").trim());
					 returnSO.setMarket(rs.getString("OKCFC3").trim());
					 returnSO.setCustomerPlanTo(rs.getString("OKFRE1").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}				
			}
		  
		} catch(Exception e)
		{
			throwError.append(" Problem loading the SalesOrderLineItem class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceSalesOrder.");
			throwError.append("loadFieldsLineItem(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnSO;
	}

	/**
	 * Use to Validate a Send in Sales Order,
	 *   Send in requestType "salesordershipped" if you  want to throw an error if NOT shipped   
	 * Return Return Message if Sales Order is not found
	 */
	
	public static String verifySalesOrder(String environment, 
										  String salesOrder,
										  String requestType)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String sqlString = "";
			
			// verify base class initialization.
			ServiceSalesOrder a = new ServiceSalesOrder();
						
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Sales Order.
			if (salesOrder == null || salesOrder.trim().equals(""))
				rtnProblem.append(" Sales Order must not be null or empty.");
			
			// get Sales Order Info.
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(salesOrder);
					sqlString = buildSqlStatement(environment, "verifySalesOrder", parmClass);
				} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
				rtnProblem.append(" Problem when building Test for Sales Order: ");
				rtnProblem.append(salesOrder + " PrintScreen this message and send to Information Services. ");
				}
			}
			
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					
					conn = ConnectionStack.getConnection();
					
					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();
					
					if (rs.next())
					{
						if (requestType.equals("salesordershipped") &&
							rs.getString("MTRIDN") == null)
						{
							rtnProblem.append(" Sales Order '" + salesOrder + "' exists.  ");
							rtnProblem.append("But has not Shipped. ");
						}
//						 it exists and all is good.
					} else {
						rtnProblem.append(" Sales Order '" + salesOrder + "' ");
						rtnProblem.append("does not exist. ");
					}
					
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Sales Order: ");
					rtnProblem.append(salesOrder + " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Sales Order class ");
			throwError.append("from the result set. " + e) ;
		// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						ConnectionStack.returnConnection(conn);
					if (rs != null)
						rs.close();
					if (findIt != null)
						findIt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}

		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceSalesOrder.");
			throwError.append("verifySalesOrder(String: SalesOrder)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

	/**
	 * Build an sql statement.
	 * @param String request type
	 * @param Vector request class
	 * @return sql string
	 * @throws Exception
	 */
	
	private static String buildSqlStatement(String environment,
											String inRequestType,
									 	    Vector requestClass)
		throws Exception 
	{
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();
		
		try { // Verify Sales Order
			// Use the GeneralUtility class to obtain library.
			String library = GeneralUtility.getLibrary(environment);
	//*****************************************************************************
	//  VERIFY
			if (inRequestType.equals("verifySalesOrder"))
			{
				// cast the incoming parameter class.
				String salesOrder = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT  OAORNO, MTRIDN  ");
				sqlString.append("FROM " + library + ".OOHEAD ");
				sqlString.append("  LEFT OUTER JOIN " + library + ".MITTRA ");
				sqlString.append("    ON OACONO = MTCONO AND OAORNO = MTRIDN ");
				sqlString.append(" WHERE OAORNO = '" + editLengthSalesOrder(salesOrder) + "' ");
				sqlString.append("GROUP BY OAORNO, MTRIDN ");
			}
   //******************************************************************************
   // FIND INFORMATION
			if (inRequestType.equals("findBasicCustomerOrder"))
			{
				// cast the incoming parameter class.
				String salesOrder = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				// from OOHEAD - Customer Order Header
				sqlString.append("SELECT OAORNO, OACUOR, OACUNO, OAORTP, OARESP, ");
				sqlString.append("       OASMCD, OACHID, OAWHLO, OAORDT, ");
				// OOLINE
				sqlString.append("OBITNO, OBITDS, OBPONR, OBPOSX, OBORQT, ");
				// MITMAS
				sqlString.append("MMITNO, MMITDS, MMITTY, MMITGR, MMITCL, ");
				sqlString.append("MMATMO, MMUNMS, MMSTAT, ");
				//OCUSMA
				sqlString.append("OKCUNM ");
				sqlString.append("FROM " + library + ".OOHEAD ");
				sqlString.append("  INNER JOIN " + library + ".OOLINE ");
				sqlString.append("          ON OBORNO = OAORNO ");
				sqlString.append("  INNER JOIN " + library + ".MITMAS ");
				sqlString.append("          ON MMITNO = OBITNO ");
				sqlString.append("  INNER JOIN " + library + ".OCUSMA ");
				sqlString.append("          ON OKCUNO = OACUNO ");
				sqlString.append(" WHERE OAORNO = '" + editLengthSalesOrder(salesOrder) + "' ");
				sqlString.append("ORDER BY OBPONR, OBPOSX, OBITNO ");
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement"
							 + " for request type " + inRequestType + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceSalesOrder.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		
		
		return sqlString.toString();
	}

	/**
	 * Add "0"'s onto the Front of the Sales Order -- The length should be 10
	 */
	
	public static String editLengthSalesOrder(String salesOrder)
	{
		if (salesOrder.trim().length() < 10)
		{
			for (int z = salesOrder.trim().length(); z < 10; z++)
			{
				salesOrder = "0" + salesOrder;
			}
			//System.out.println("salesOrder: " + salesOrder);
		}
		return salesOrder;
	}

	/**
		 * Load class fields from result set.
		 */
		
		public static SalesOrderLineItem loadFieldsSalesOrderLineItem(String requestType,
												                      ResultSet rs)
				throws Exception
		{
			StringBuffer throwError = new StringBuffer();
			SalesOrderLineItem returnSO = new SalesOrderLineItem();
		
			try{ 
				if (requestType.equals("loadLine"))
				{
					try
					{
	//					 Sales Order  File -- OOHEAD
						 returnSO.setOrderNumber(rs.getString("OAORNO").trim());
						 returnSO.setCustomerPONumber(rs.getString("OACUOR").trim());
						 returnSO.setCustomerNumber(rs.getString("OACUNO").trim());
						 returnSO.setOrderType(rs.getString("OAORTP").trim());
						 returnSO.setOrderResponsible(rs.getString("OARESP").trim());
						 returnSO.setOrderSoldBy(rs.getString("OASMCD").trim());
						 returnSO.setLastModifiedBy(rs.getString("OACHID").trim());
						 returnSO.setShippingWarehouse(rs.getString("OAWHLO").trim());
						 returnSO.setPlannedShipDate(rs.getString("OAORDT").trim());
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
//						System.out.println("Caught Problem in ServiceSalesOrder.load" + e);
					}
					try
					{
	//					 Customer Detail File -- OCUSMA
						 returnSO.setCustomerName(rs.getString("OKCUNM").trim());
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
					}				
					try
					{
	//					 Customer Address File -- OCUSAD
						 returnSO.setShipToCity(rs.getString("OPTOWN").trim());
						 returnSO.setShipToState(rs.getString("OPECAR").trim());
						 returnSO.setShipToZip(rs.getString("OPPONO").trim());
						 returnSO.setAddress1(rs.getString("OPCUA1").trim());
						 returnSO.setAddress2(rs.getString("OPCUA2").trim());
						 returnSO.setAddress3(rs.getString("OPCUA3").trim());
						 returnSO.setAddress4(rs.getString("OPCUA4").trim());
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
//						System.out.println("caught problem with address:" + e);
					}	
					try
					{
	//					 Item Related Informaion -- MITMAS and Line Information
						 returnSO.setItemClass(ServiceItem.loadFieldsItem("loadForCOA", rs));
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
					}			
					try
					{
	//					 Line Item Information -- OOLINE Line Information
						 returnSO.setLineNumber(rs.getString("OBPONR"));
						 returnSO.setSuffix(rs.getString("OBPOSX"));
						 returnSO.setOrderQuantity(rs.getString("OBORQT"));
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
					}		
					try
					{
	//					 transaction Related information
						 returnSO.setActualShipDate(rs.getString("MTTRDT"));
					}
					catch(Exception e)
					{
						// Problem Caught when loading the Actual Ship Date
					}		
				}
			  
			} catch(Exception e)
			{
				throwError.append(" Problem loading the SalesOrderLineItem class ");
				throwError.append("from the result set. " + e) ;
			}
			// return data.
			if (!throwError.toString().equals("")) {
				throwError.append("Error @ com.treetop.services.");
				throwError.append("ServiceSalesOrder.");
				throwError.append("loadFieldsLineItem(requestType, rs: ");
				throwError.append(requestType + "). ");
				throw new Exception(throwError.toString());
			}
			return returnSO;
		}
	/**
	 * Send in Environment, Customer Order Number, 
	 *    Return a Vector, of SalesOrderLineItem Class 
	 */
		
	public static Vector buildSalesOrderBasic(String environment, 
								              String customerOrderNumber)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnValue = new Vector();
		Connection conn = null; // New Lawson Box - Lawson Database
		try { 
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			if (customerOrderNumber == null || customerOrderNumber.trim().equals(""))
			   throwError.append(" Customer Order Number must not be null or empty.");
			conn = ConnectionStack.getConnection();
			
			// get Sales Order Information
			if (throwError.toString().equals(""))
			{
				try {
					rtnValue = findSalesOrderBasic(environment, customerOrderNumber, conn);
				} catch (Exception e) {
				throwError.append(" error trying to retrieve Item Data. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Retrieving Item Number Information. " + e);
		// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						ConnectionStack.returnConnection(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
			// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceSalesOrder.");
			throwError.append("buildSalesOrderBasic(String environment: ");
			throwError.append("String customerOrderNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnValue;
	}
	/**
	 * Send in Environment, CustomerOrderNumber, 
	 *    Return a Vector of SalesOrderLineItem Class 
	 */
	private static Vector findSalesOrderBasic(String environment, 
								            String customerOrderNumber,
								            Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnValue = new Vector();
		PreparedStatement findIt = null;
		ResultSet rs = null;
		String sqlString = new String();
		try { 
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(customerOrderNumber);
				sqlString = buildSqlStatement(environment, "findBasicCustomerOrder", parmClass);
			} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
			}
			// get a execute sql, build return object.
			if (throwError.toString().equals("")) {
				try {
					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();
				} catch (Exception e) {
					throwError.append(" error trying to execute sqlString. ");
				}
			}
			if (throwError.toString().equals("")) {
				try {					
					while (rs.next())
					{
						// need to Load the information into the Item Class
						try
						{
							rtnValue.addElement(loadFieldsSalesOrderLineItem("loadLine", rs));
						} catch (Exception e) {
							throwError.append(" error occured loading Fields from an sql statement. " + e);
						}
					}
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Retrieving Data for Customer Order Information. " + e) ;
		// return connection.
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (findIt != null)
					findIt.close();
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceSalesOrder.");
			throwError.append("findSalesOrderBasic(String environment: ");
			throwError.append("String customerOrderNumber, Connection conn)");
			throw new Exception(throwError.toString());
		}
		return rtnValue;
	}
}

