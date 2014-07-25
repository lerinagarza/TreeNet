/*
 * Created on July 14, 2010
 *
 */
package com.treetop.services;


import java.sql.*;
import java.util.*;
import com.treetop.businessobjects.*;
import com.treetop.utilities.GeneralUtility;

/**
 * @author thaile.
 *
 * Services class to obtain and return data 
 * to business objects.
 * 
 * Certificate of Analysis 
 */
public class ServiceDistributionOrder extends BaseService {

	/**
	 * 
	 */
	public ServiceDistributionOrder() {
		super();
	}
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		String startHere = "x";
		try
		{
			//verifyDistributionOrder
			if ("X" == "X")
			{
				verifyDistributionOrder("TST", "0001416756");
			}
			
			String endHere = "x";
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Load class fields from result set.
	 *    for the COA Business Object.
	 */
	
	public static DistributionOrderLineItem loadFieldsLineItems(String requestType,
																ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		DistributionOrderLineItem returnDO = new DistributionOrderLineItem();
	
		try{
			if (requestType.equals("loadFromCOA"))
			{
				returnDO.setCompanyNo(rs.getString("MGCONO"));
				returnDO.setFacility(rs.getString("MGFACI"));
				//returnDO.setProductGroup(rs.getString("MMITCL"));
				returnDO.setLastModifiedBy(rs.getString("MGCHID"));
				returnDO.setLineNumber(rs.getString("MRPONR"));
				returnDO.setOrderNumber(rs.getString("MGTRNR"));
				returnDO.setOrderQuantity(rs.getString("MTTRQT"));
				returnDO.setOrderResponsible(rs.getString("MGRESP"));
				returnDO.setOrderType("");
				returnDO.setSuffix(rs.getString("MRPOSX"));
				
				Vector vector = new Vector();
				
				vector.addElement(rs.getString("MGTWLO"));
				Warehouse whse = ServiceWarehouse.findWarehouse("PRD", vector);
				returnDO.setToWarehouse(whse);
				
				vector.setElementAt(rs.getString("MGWHLO"), 0);
				whse = ServiceWarehouse.findWarehouse("PRD", vector);
				returnDO.setFromWarehouse(whse);
				
				returnDO.setShipDate(rs.getString("MGTRDT"));
				returnDO.setReceiveDate(rs.getString("MGRIDT"));
				try
				{
//					 transaction Related information
					 returnDO.setActualShipDate(rs.getString("MTTRDT"));
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Actual Ship Date
				}		
				try
				{
//					 Item Related Informaion -- MITMAS and Line Information
					 returnDO.setItemClass(ServiceItem.loadFieldsItem("loadForCOA", rs));
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}			
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the COA class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceDistributionOrder.");
			throwError.append("loadFieldsLineItems(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnDO;
	}

	/**
	 * Use to Validate a Send in Sales Order, Return Return Message if Sales Order is not found
	 */
	
	public static String verifyDistributionOrder(String environment, String distributionOrder)
			throws Exception
	{
		// Changes to Method
		// 2/2/09 TWalton Changed from Connection Pool to DBConnection
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyDistributionOrder";
			String sqlString = "";
			
			// verify base class initialization.
			ServiceSalesOrder a = new ServiceSalesOrder();
						
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Sales Order.
			if (distributionOrder == null || distributionOrder.trim().equals(""))
				rtnProblem.append(" Sales Order must not be null or empty.");
			
			// get Sales Order Info.
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(distributionOrder);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
				rtnProblem.append(" Problem when building Test for Distribution Order: ");
				rtnProblem.append(distributionOrder + " PrintScreen this message and send to Information Services. ");
				}
			}
			
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					conn = getDBConnection();
					
					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();
					
					if (rs.next())
					{
//						 it exists and all is good.
					} else {
						rtnProblem.append(" Distribution Order '" + distributionOrder + "' ");
						rtnProblem.append("does not exist. ");
					}
					
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Distribution Order: ");
					rtnProblem.append(distributionOrder + " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem finding this record in the Distribution Order class ");
			throwError.append("from the result set. " + e) ;
		// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						conn.close();
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
			throwError.append("ServiceDistributionOrder.");
			throwError.append("verifyDistributionOrder(String: DistributionOrder)");
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
			
			if (inRequestType.equals("verifyDistributionOrder"))
			{
				// cast the incoming parameter class.
				String distributionOrder = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT MGTRNR ");
				sqlString.append("FROM " + library + ".MGHEAD ");
				sqlString.append(" WHERE MGTRNR = '" + editLengthDistributionOrder(distributionOrder) + "' ");
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement"
							 + " for request type " + inRequestType + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceDistributionOrder.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		
		
		return sqlString.toString();
	}

	/**
	 * Add "0"'s onto the Front of the Sales Order -- The length should be 10
	 */
	private static String editLengthDistributionOrder(String distributionOrder)
	{
		if (distributionOrder.trim().length() < 10)
		{
			for (int z = distributionOrder.trim().length(); z < 10; z++)
			{
				distributionOrder = "0" + distributionOrder;
			}
		}
		return distributionOrder;
	}
}

