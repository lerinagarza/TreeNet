/*
 * Created on March 12, 2008
 *   @author twalto .
 */
package com.treetop.services;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;

import com.ibm.as400.access.*;
import com.treetop.app.finance.*;
import com.treetop.businessobjects.DateTime;
import com.treetop.utilities.*;
import com.treetop.utilities.html.DropDownSingle;

/**
 * Services class to obtain/update information
 *   related to Movex's Cost Types
 * 		Cost Type information is by Item/Facility
 */
public class ServiceCostTypes extends BaseService {

	public static final String library = "M3DJDPRD.";
	public static final String ttlib = "DBPRD.";
	// Use for Testing
	//public static final String library = "APDEV.";

	/**
	 * 
	 */
	public ServiceCostTypes() {
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
	 * Run through the decisions on how to calculate Company Cost
	 *     will also determine which Records need to be build
	 */
	public static void buildCompanyCost(String environment, Vector incomingParms)
			throws Exception
	{
//		 Changes to Method
		// 2/2/09 TWalton - Change from Connection Stack to dbConnection
		StringBuffer throwError = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		try { 
			// verify base class initialization.
			ServiceCostTypes a = new ServiceCostTypes();
						
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			conn = getDBConnection();
			
			buildCompanyCost(environment, conn, incomingParms);
			
		} catch(Exception e)
		{
			throwError.append(" Problem Building the Cost Type Information " + e);
		// return connection.
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}

		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceCostTypes.");
			throwError.append("buildcompanyCost(String: Environment, Vector)");
			throw new Exception(throwError.toString());
		}
		return;
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
									 	    Vector parms)
		throws Exception 
	{
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();
		
		try { 
			if (inRequestType.trim().equals("verifyDateCostType"))
			{
				String date = (String) parms.elementAt(0);
				String costType = (String) parms.elementAt(1);
				String itemNumber = (String) parms.elementAt(2);
				sqlString.append("SELECT KOCONO ");
				sqlString.append("FROM " + library + "MCHEAD ");
				sqlString.append("WHERE KOPCTP = '" + costType.trim() + "' ");
				sqlString.append("  AND KOPCDT = " + date);
				if (!itemNumber.trim().equals(""))
					sqlString.append(" AND KOITNO = '" + itemNumber.trim() + "' ");
				
				sqlString.append(" GROUP BY KOCONO ");
			}
			if (inRequestType.trim().equals("datesByItemType"))
			{
				String costType = (String) parms.elementAt(0);
				sqlString.append("SELECT KOPCDT ");
				sqlString.append("FROM " + library + "MCHEAD ");
				sqlString.append("WHERE KOPCTP = '" + costType.trim() + "' ");
				sqlString.append("GROUP BY KOPCDT ");
				sqlString.append("ORDER BY KOPCDT DESC ");
			}
			//----------------------------
			// Drop Down Lists
			//----------------------------
			if (inRequestType.trim().equals("ddFiscalYearForecast"))
			{ // Will only be applicable for the forecasted Quantities
				sqlString.append("SELECT CSAYEAR ");
				sqlString.append("FROM " + ttlib + "CSPAFCST ");
				sqlString.append("WHERE CSABVER = 'BUD' ");
				sqlString.append("GROUP BY CSAYEAR ");
				sqlString.append("ORDER BY CSAYEAR DESC ");
			}
			//------------------------------
			// Find
			//------------------------------
			if (inRequestType.trim().equals("findSummarizeByTypeDate"))
			{ // Summarize By Item Facility -- Based on Cost Type and Date
				String date = (String) parms.elementAt(0);
				String costType = (String) parms.elementAt(1);
				String year = (String) parms.elementAt(2);
				String itemType = (String) parms.elementAt(3);
				String itemNumber = (String) parms.elementAt(4);
				sqlString.append("SELECT ");
				sqlString.append("M9ITNO, M9FACI, ");
				sqlString.append("KOCSU9, ");
				//sqlString.append("SUM(MFMFOR) totalForecast ");
				sqlString.append("CSAFQTY ");
				sqlString.append("FROM " + library + "MITFAC ");
				sqlString.append(" INNER JOIN " + library + "MITMAS ");
				sqlString.append("   ON  M9ITNO = MMITNO ");
				if (itemNumber.trim().equals("") &&
				    !itemType.trim().equals(""))
				   sqlString.append(" AND MMITTY = '" + itemType + "' ");
				//sqlString.append("   AND MMITTY IN ('100', '110', '125', '120', '130', '140', '150','210') ");
				sqlString.append(" LEFT OUTER JOIN " + library + "MCHEAD ");
				sqlString.append("   ON  M9CONO = KOCONO ");
				sqlString.append("   AND M9FACI = KOFACI ");
				sqlString.append("   AND M9ITNO = KOITNO ");
				sqlString.append("   AND KOPCTP = '" + costType.trim() + "' ");
				sqlString.append("   AND KOPCDT = " + date.trim() );
		//		sqlString.append(" LEFT OUTER JOIN " + library + "MITWHL ");
		//		sqlString.append("   ON MWFACI = M9FACI ");
		//		sqlString.append(" LEFT OUTER JOIN " + library + "MITAFO ");
		//		sqlString.append("   ON  MFCONO = M9CONO ");
		//		sqlString.append("   AND MFITNO = M9ITNO ");
		//		sqlString.append("   AND MFWHLO = MWWHLO ");
				sqlString.append(" LEFT OUTER JOIN " + ttlib + "CSPAFCST ");
				sqlString.append("   ON M9FACI = CSAFACI ");
				sqlString.append("  AND M9ITNO = CSAITNO ");
				sqlString.append("  AND CSAYEAR = '" + year + "' AND CSABVER = 'BUD' ");
				if (!itemNumber.trim().equals(""))
					sqlString.append(" WHERE M9ITNO = '" + itemNumber + "' ");
		//		sqlString.append(" WHERE M9ITNO IN( '4100035001') ");
				sqlString.append("  AND KOSTRT = 'STD' ");
				sqlString.append("GROUP BY M9ITNO, M9FACI, KOCSU9, CSAFQTY ");
				sqlString.append("ORDER BY M9ITNO, M9FACI ");
			}
			if (inRequestType.trim().equals("insertCompanyCost"))
			{
				//Element 0 -- UpdFinance (view Class)
				//Element 1 - Facility
				//Element 2 - Item Number
				//Element 3 - Cost Value - For Company Cost
				UpdFinance uf = (UpdFinance) parms.elementAt(0);
				
				sqlString.append("INSERT INTO " + library + "MCHEAD ");
			//	sqlString.append("INSERT INTO M3DJDTST.MCHEAD ");
				sqlString.append("VALUES(");
				sqlString.append("100, "); // Company
				sqlString.append("'" + ((String) parms.elementAt(1)).toString() + "', "); // Facility
				sqlString.append("'" + ((String) parms.elementAt(2)).toString() + "', "); // Item Number
				sqlString.append("'STD', "); // Product Structure Type
				sqlString.append("'', "); // Configuration Identity
				sqlString.append("0, "); // Costing Category
				sqlString.append("'', "); // Reference Order Number
				sqlString.append("0, "); // Reference Order Line
				sqlString.append("0, "); // Simulation Round
				sqlString.append("0, "); // Simulated Manufacturing Order Number
				sqlString.append("'" + uf.getToCostType() + "', "); // Costing Type
				sqlString.append(uf.getToCostDateFileFormat() + ", "); // Costing Date
				sqlString.append(((String) parms.elementAt(3)).toString() + ", "); // Costing Sum 1
				sqlString.append(((String) parms.elementAt(3)).toString() + ", "); // Costing Sum 2
				sqlString.append(((String) parms.elementAt(3)).toString() + ", "); // Costing Sum 3
				sqlString.append(((String) parms.elementAt(3)).toString() + ", "); // Costing Sum 4
				sqlString.append(((String) parms.elementAt(3)).toString() + ", "); // Costing Sum 5
				sqlString.append(((String) parms.elementAt(3)).toString() + ", "); // Costing Sum 6
				sqlString.append(((String) parms.elementAt(3)).toString() + ", "); // Costing Sum 7
				sqlString.append(((String) parms.elementAt(3)).toString() + ", "); // Costing Sum 8
				sqlString.append(((String) parms.elementAt(3)).toString() + ", "); // Costing Sum 9
				sqlString.append("0, "); // Costing Sum 1
				sqlString.append("0, "); // Costing Sum 2
				sqlString.append("0, "); // Costing Sum 3
				sqlString.append("0, "); // Costing Sum 4
				sqlString.append("0, "); // Costing Sum 5
				sqlString.append("0, "); // Costing Sum 6
				sqlString.append("0, "); // Costing Sum 7
				sqlString.append("0, "); // Costing Sum 8
				sqlString.append("0, "); // Costing Sum 9
				sqlString.append("0, "); // Scrap Total 1
				sqlString.append("0, "); // Scrap Total 2
				sqlString.append("0, "); // Scrap Total 3
				sqlString.append("'', "); // Costing Name
				sqlString.append("0, "); // Order Quantity
				sqlString.append("'', "); // Costing Model - Product Costing
				sqlString.append("0, "); // Structure Date
				sqlString.append("0, "); // Manually Updated Mark
				sqlString.append("1, "); // Manually Updated
				sqlString.append("0, "); // Data Saved
				sqlString.append("0, "); // Number of Components
				sqlString.append("0, "); // Number of Operations
				sqlString.append("0, "); // Number of Subcontract Operations
				sqlString.append("0, "); // Average On-Hand Balance
				sqlString.append("0, "); // Annual Demand
				sqlString.append("0, "); // Lead Time This Level
				sqlString.append("0, "); // Structure Complexity
				sqlString.append("0, "); // User-Defined Field 2 - Item
				sqlString.append("0, "); // Costing Percentage
				sqlString.append("0, "); // Text Identity
				DateTime dt = UtilityDateTime.getSystemDate();
				sqlString.append(dt.getDateFormatyyyyMMdd() + ", "); // Entry Date
				sqlString.append(dt.getTimeFormathhmmss() + ", "); // Entry Time
				sqlString.append(dt.getDateFormatyyyyMMdd() + ", "); // Change Date
				sqlString.append("1, "); // Change Number
				sqlString.append("'" + uf.getFromCostType() + uf.getFromCostDate() + "', "); // Changed By
				sqlString.append("0, "); // Line Suffix
				sqlString.append("0 "); // Timestamp
				sqlString.append(")");
			}
			if (inRequestType.trim().equals("deleteByTypeDate"))
			{
				String date = (String) parms.elementAt(0);
				String costType = (String) parms.elementAt(1);
				String itemType = (String) parms.elementAt(2);
				String itemNumber = (String) parms.elementAt(3);
				sqlString.append("DELETE ");
				sqlString.append("FROM " + library + "MCHEAD ");
		//		sqlString.append("FROM M3DJDDEV.MCHEAD ");
				sqlString.append("WHERE KOPCTP = '" + costType.trim() + "' ");
				sqlString.append("  AND KOPCDT = " + date);
				if (itemNumber.trim().equals(""))
				{
				   sqlString.append(" AND EXISTS(SELECT * FROM " + library + "MITMAS ");
				   sqlString.append(" WHERE MMITNO = KOITNO AND MMITTY = '" + itemType + "') ");
				}
				else
				   sqlString.append(" AND KOITNO = '" + itemNumber.trim() + "' ");
			}
			if (inRequestType.trim().equals("getByCostType"))
			{
				String costType = (String) parms.elementAt(0);
				String item     = (String) parms.elementAt(1);
				String year     = (String) parms.elementAt(2);
				
				sqlString.append("SELECT KOITNO, KOCSU9, KOPCDT ");
				sqlString.append("FROM " + library + "MCHEAD ");
				sqlString.append("WHERE KOPCTP = '" + costType.trim() + "' ");
				sqlString.append("  AND KOCSU9 <> 0 ");
				sqlString.append("  AND KOITNO = '" + item.trim() + "' ");
				if (!year.trim().equals(""))
				{
					sqlString.append(" AND KOPCDT < " + year.trim() + "0801 ");
				}
				sqlString.append(" GROUP BY KOITNO, KOCSU9, KOPCDT ");
				sqlString.append(" ORDER BY KOPCDT DESC ");
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement"
							 + " for request type " + inRequestType + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceCostTypes.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		
		return sqlString.toString();
	}

	/**
	 * Will Generate and Build Company Cost Records
	 * @throws Exception
	 */
	private static void buildCompanyCost(String environment, 
										 Connection conn, 
										 Vector inValues)
		throws Exception 
	{
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement findIt = null;
		PreparedStatement deleteIt = null;
		PreparedStatement insertIt = null;
		try { 
			
			UpdFinance uf = (UpdFinance) inValues.elementAt(0);
			try
			{
				if (!uf.getShouldOverride().trim().equals(""))
				{
				// Delete the Records that are there for that Cost Type and Date
				   	  try {
				   	  	Vector sqlParms = new Vector();
				   	  	sqlParms.add(uf.getToCostDateFileFormat());
				   	  	sqlParms.add(uf.getToCostType());
				   	  	sqlParms.add(uf.getItemType());
				   	  	sqlParms.add(uf.getItemNumber());
						deleteIt = conn.prepareStatement(buildSqlStatement(environment, "deleteByTypeDate", sqlParms));
						deleteIt.executeUpdate();
					  } catch(Exception e)
					  {
						throwError.append(" error occured executing a delete ");
						throwError.append(" sql statement. " + e);
					  } 					
				}
			// Insert New Records for all item/Facility Records
				try {
					Vector sendValues = new Vector();
					sendValues.add(uf.getFromCostDate());
					sendValues.add(uf.getFromCostType());
			   	  	sendValues.add(uf.getFromFiscalYear());
			   	  	sendValues.add(uf.getItemType());
			   	  	sendValues.add(uf.getItemNumber());
					findIt = conn.prepareStatement(buildSqlStatement(environment, "findSummarizeByTypeDate", sendValues));
					rs = findIt.executeQuery();
				}
				catch(Exception e)
				{
					System.out.println(" Find Information to Calculate Company Cost SQL: " + e);
					throwError.append(" error occured executing the sql statement: " + e);
				}
				try
				{
					// Process the Result Set
					String saveItem = "";
					String saveFacility = "";
					Vector faci = new Vector(); // String
					Vector cost = new Vector(); // BigDecimal
					Vector forecast = new Vector(); // BigDecimal
					int countRecords = 0;
					while (rs.next())
					{ 
						countRecords++;
//					  if (!saveFacility.trim().equals(rs.getString("M9FACI").trim()))
//					  {	
						if(!saveItem.trim().equals(rs.getString("M9ITNO").trim()))	
						{
							if (!saveItem.trim().equals(""))
							{
							// Process the Built Vectors - to Generate Company Cost
								Vector sendParms = new Vector();
						//		sendParms = faci;
								sendParms.add(cost);
								sendParms.add(forecast);
								BigDecimal companyCost = calculateCompanyCost(sendParms);
							
								// Insert all the Appropriate Item Facility Cost Records
								//   for Company Cost
								if (faci.size() > 0)
								{	
								   for (int x = 0; x < faci.size(); x++)
								   {
								   	  try {
								   	  	Vector sqlParms = new Vector();
								   	  	sqlParms.add(uf);
								   	  	sqlParms.add(faci.elementAt(x));
								   	  	sqlParms.add(saveItem);
								   	  	sqlParms.add(companyCost.toString());
										insertIt = conn.prepareStatement(buildSqlStatement(environment, "insertCompanyCost", sqlParms));
										insertIt.executeUpdate();
									  } catch(Exception e)
									  {
									  	if (e.toString().contains("DuplicateKeyException") != true)
									  	{	
										   throwError.append(" error occured executing an ");
										   throwError.append("add sql statement. " + e);
									  	}
									  } 
								   } 
								}
							// Clear the Vectors to Begin Again
							   faci = new Vector();
							   cost = new Vector();
							   forecast = new Vector();
							}
							saveItem = rs.getString("M9ITNO");
							
						}
						faci.add(rs.getString("M9FACI"));
						if (rs.getString("KOCSU9") == null)
							cost.add((new BigDecimal("0")));
						else
						    cost.add(rs.getBigDecimal("KOCSU9"));
				//		System.out.println(rs.getString(4));
						if (rs.getString(4) == null)
							forecast.add((new BigDecimal("0")));
						else
						    forecast.add(rs.getBigDecimal(4));
						saveFacility = rs.getString("M9FACI");
					  } // Same Facility
					
					// Process the LAST one
					try
					{
						// Process the Built Vectors - to Generate Company Cost
						Vector sendParms = new Vector();
						sendParms.add(cost);
						sendParms.add(forecast);
						BigDecimal companyCost = calculateCompanyCost(sendParms);
						
						// Insert all the Appropriate Item Facility Cost Records
						//   for Company Cost
						if (faci.size() > 0)
						{	
						   for (int x = 0; x < faci.size(); x++)
						   {
						   	  try {
						   	  	Vector sqlParms = new Vector();
						   	  	sqlParms.add(uf);
						   	  	sqlParms.add(faci.elementAt(x));
						   	  	sqlParms.add(saveItem);
						   	  	sqlParms.add(companyCost.toString());
								insertIt = conn.prepareStatement(buildSqlStatement(environment, "insertCompanyCost", sqlParms));
								insertIt.executeUpdate();
							  } catch(Exception e)
							  {
								if (e.toString().contains("DuplicateKeyException") != true)
							  	{	
								   throwError.append(" error occured executing an ");
								   throwError.append("add sql statement. " + e);
							  	}
							  } 
						   } 
						}
				  } // END of One Record
				  catch(Exception e){  }

					
					// Process Last batch of Vectors
					// Process the Built Vectors - to Generate Company Cost
					Vector sendParms = new Vector();
			//		sendParms = faci;
					sendParms.add(cost);
					sendParms.add(forecast);
					BigDecimal companyCost = calculateCompanyCost(sendParms);
				
					// Insert all the Appropriate Item Facility Cost Records
					//   for Company Cost
					if (faci.size() > 0)
					{	
					   for (int x = 0; x < faci.size(); x++)
					   {
					   	  try {
					   	  	Vector sqlParms = new Vector();
					   	  	sqlParms.add(uf);
					   	  	sqlParms.add(faci.elementAt(x));
					   	  	sqlParms.add(saveItem);
					   	  	sqlParms.add(companyCost.toString());
							insertIt = conn.prepareStatement(buildSqlStatement(environment, "insertCompanyCost", sqlParms));
							insertIt.executeUpdate();
						  } catch(Exception e)
						  {
							if (e.toString().contains("DuplicateKeyException") != true)
						  	{	
							   throwError.append(" error occured executing an ");
							   throwError.append("add sql statement. " + e);
						  	}
						  } 
					   } 
					}
				}
				catch(Exception e)
				{
					throwError.append(" error occured reading of result set. " + e);
				} 
			} catch (Exception e) {
			//	throwError.append(" Error building sql statement"
			//				 + " for update of Cost Type 9 + ". " + e);
			}
			finally {
				if (rs != null) {
					try {
						rs.close();
						findIt.close();
						insertIt.close();
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}		
			
			
			
			
			
			
			
		} catch (Exception e) {
			throwError.append(" Error Found: " + e);
		}
		
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceCostTypes.");
			throwError.append("buildCompanyCost(String,Connection,Vector)");
			throw new Exception(throwError.toString());
		}
	}

	/**
	 * Use to return a Vector of Costing Dates by Cost Type 
	 */
	
	public static Vector returnVectorDatesByCostType(String environment, String costType)
			throws Exception
	{
//		 Changes to Method
		// 2/2/09 TWalton - Change from Connection Stack to dbConnection
		StringBuffer throwError = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		Vector returnValues = new Vector();
		try { 
			String requestType = "";
			String sqlString = "";
			
			// verify base class initialization.
			ServiceBrokerage a = new ServiceBrokerage();
						
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			conn = getDBConnection();
			
			if (!costType.trim().equals(""))
			   returnValues = findDatesByCostType(environment, costType, conn);
			
		} catch(Exception e)
		{
			throwError.append(" Problem Retreiving Dates " + e);
		// return connection.
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
	
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinance.");
			throwError.append("returnVectorDatesByCostType(String: Environment, String)");
			throw new Exception(throwError.toString());
		}
		return returnValues;
	}

	/**
	 * Return a Vector of Costing Dates
	 *    Information based on the incoming CostType
	 * 
	 * @param String costType
	 * @return Vector dates.
	 * @throws Exception
	 */
	private static Vector findDatesByCostType(String environment,
											  String costType,
											  Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		PreparedStatement findIt = null;
		ResultSet rs = null;
		Vector returnDates = new Vector();
		
		try {
			Vector sendValues = new Vector();
			sendValues.add(costType);
			
		//------------------------------	
		// Get All Costing Dates Descending Order
			try {
				findIt = conn.prepareStatement(buildSqlStatement(environment, "datesByItemType", sendValues));
				rs = findIt.executeQuery();
			}
			catch(Exception e)
			{
				System.out.println(" TEST Valid SQL: " + e);
				throwError.append(" error occured executing the sql statement, to Retrieve Dates by Cost Type. " + e);
			}
			try
			{
				while (rs.next())
				{
					returnDates.addElement(rs.getString("KOPCDT"));
				}
			
			}
			catch(Exception e)
			{
				throwError.append(" error occured reading of result set. " + e);
			} finally {
				if (rs != null) {
					try {
						rs.close();
						findIt.close();
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			throwError.append(" error occured processing information. " + e);
		} 
		// return error data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceCostTypes.");
			throwError.append("findDatesByCostType(Environment, CostType, Connection). ");
			throw new Exception(throwError.toString());
		}
		return returnDates;
	}

	/**
	 * Added Method: 3/14/08 - Teri Walton
	 * Use to Validate:
	 * 	 Send in Date and Cost Type 
	 *   Return Message if Record is not found - else Message would be Blank
	 */
	public static String verifyDateCostType(String environment, 
										    String date,
											String costType,
											String itemNumber)
			throws Exception
	{
		// Changes to Method
		// 2/2/09 TWalton - Changed from Connection Pool to DBConnection
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyRecords";
			String sqlString = "";
				
			// verify base class initialization.
			ServiceCostTypes a = new ServiceCostTypes();
							
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming date
			if (date == null || date.trim().equals(""))
				rtnProblem.append(" Date must not be null or empty. ");
			if (costType == null || costType.trim().equals(""))
				rtnProblem.append(" Cost Type must not be null or empty. ");
			
			// get Test Lot Number Info.
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(date);
					parmClass.add(costType);
					parmClass.add(itemNumber);
					sqlString = buildSqlStatement(environment, "verifyDateCostType", parmClass);
				} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnProblem.append(" Problem when building Test for Date: " + date);
					rtnProblem.append(" And Cost Type: " + costType + " PrintScreen this message and send to Information Services. ");
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
	//					 it exists and all is good.
					} else {
						rtnProblem.append(" Date '" + date + "' ");
						rtnProblem.append(" And CostType '" + costType + "' ");
						rtnProblem.append("does not exist. ");
					}
						
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Test for Date: " + date);
					rtnProblem.append(" And Cost Type: " + costType + " PrintScreen this message and send to Information Services. ");
				}
			}
			} catch(Exception e)
			{
				throwError.append(" Problem Testing the Date and Cost Type ");
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
				throwError.append("ServiceLot.");
				throwError.append("verifyDateCostType(String: Environment, String: Date, String: CostType, String: ItemNumber)");
				throw new Exception(throwError.toString());
			}
			return rtnProblem.toString();
		}

	/**
	 * Will Calculate the Company Cost based on Incoming Values
	 *    Will Return a BigDecimal Value of the 
	 * 		Calculated Company Cost
	 * @throws Exception
	 */
	private static BigDecimal calculateCompanyCost(Vector inValues)
	{
		BigDecimal companyCost = new BigDecimal("0");
		try
		{
			String allSameCost = "Y";
			// Test to see if the Costs are all the Same (Do not need to Calculate if they are)
			Vector cost = (Vector) inValues.elementAt(0);
			BigDecimal testCost = new BigDecimal("0");
			if (cost.size() > 0)
			{
			  if (cost.size() == 1)
			  {
			  	testCost = (BigDecimal) cost.elementAt(0);
			  	if (testCost == null)
			  		testCost = new BigDecimal("0");
			  }
			  else
			  {
			     for (int cst = 0; cst < cost.size(); cst++)
			     {	
			   	   if (((BigDecimal) cost.elementAt(cst)).compareTo(new BigDecimal("0")) != 0)
			   	   {
			   	  	  if (testCost.compareTo(new BigDecimal("0")) != 0)
			   	  	  {
			   	  	  	if (testCost.compareTo((BigDecimal) cost.elementAt(cst)) != 0);
			   	  	  	  allSameCost = "N";
			   	  	  }
			   	  	  else
			   	  	  	testCost = (BigDecimal) cost.elementAt(cst);
			   	    }
			     }
			  }
			}
			if (allSameCost.equals("N"))
			{ // Not all the Same Cost
			   Vector forecast = (Vector) inValues.elementAt(1);
			   String allZero = "Y";
			   int countNotZero = 0;
			   int whereNotZero = 0;
			   for (int fcst = 0; fcst < forecast.size(); fcst++)
			   {	
			   	  if (((BigDecimal) forecast.elementAt(fcst)).compareTo(new BigDecimal("0")) != 0)
			   	  {
			   	  	 countNotZero++;
			   	  	 whereNotZero = fcst;
			   	  }
			   } 
			   if (countNotZero == 1) // Only ONE Forecasted Facility
			   	  companyCost = (BigDecimal) cost.elementAt(whereNotZero);
			   else
			   {
			   	   if (countNotZero == 0) // Not Forecasted Anywhere
			   	   {
			   	   	  // Need to do a Basic Average for All that are not Zero Cost
			   	   	  int countRecords = 0;
			   	   	  BigDecimal addedCosts = new BigDecimal("0");
			   	   	  for (int cst = 0; cst < cost.size(); cst++ )
			   	   	  {
			   	   	  	 if (((BigDecimal) cost.elementAt(cst)).compareTo(new BigDecimal("0")) != 0)
						 {
			   	   	  	 	countRecords++;
			   	   	  	 	addedCosts = addedCosts.add((BigDecimal) cost.elementAt(cst));
						 }
			   	   	  }
			   	   	  if (countRecords != 0 && addedCosts.compareTo(new BigDecimal("0")) != 0)
			   	   	  {
			   	   	  	 companyCost = addedCosts.divide(new BigDecimal(countRecords), 2);
			   	   	  }
			   	   }
			   	   else
			   	   {
			   	   	  // Big Calculation -- Build Weighted Average Using Costs AND Forecast
			   	   	  BigDecimal addedForecast = new BigDecimal("0");
			   	   	  BigDecimal totalCalculatedOut = new BigDecimal("0");
			   	   	  for (int fcst = 0; fcst < forecast.size(); fcst++ )
			   	   	  {
			   	   	     if (((BigDecimal) forecast.elementAt(fcst)).compareTo(new BigDecimal("0")) != 0)
			   	   	     {
			   	   	     	addedForecast = addedForecast.add((BigDecimal) forecast.elementAt(fcst));
			   	   	     	BigDecimal multOut = ((BigDecimal) forecast.elementAt(fcst)).multiply((BigDecimal) cost.elementAt(fcst));
			   	   	     	totalCalculatedOut = totalCalculatedOut.add(multOut);
			   	   	     }
			   	   	  }
			   	   	  companyCost = totalCalculatedOut.divide(addedForecast, 2);
			   	   	}
			   }
			}
			else
				companyCost = testCost;
		}
		catch(Exception e)
		{
			// Catch the Exception 
			// Only Display if Testing
			System.out.println("Problem: " + e);
		}
		return companyCost;
	}

	/**
	 * Use to return a Vector which includes the Cost Value
	 * Send in a Vector:  Item Number, Cost Type, and the Year
	 * 				Will Return the most current Cost for that Fiscal Year 
	 */
	
	public static Vector returnMostCurrentCost(String environment, 
											   Vector inValues)
	throws Exception
	{
//		 Changes to Method
		// 2/2/09 TWalton - Change from Connection Stack to dbConnection
		StringBuffer throwError = new StringBuffer();
		Vector rtnValue = new Vector();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = getDBConnection();
			rtnValue = returnMostCurrentCost(environment, inValues, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
				   conn.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceCostTypes.");
			throwError.append("returnMostCurrent(");
			throwError.append("String, Vector). ");
			throw new Exception(throwError.toString());
		}
		return rtnValue;
	}

	/**
	 * Use to return a Vector which includes the Cost Value
	 * Send in a Vector:  Item Number, Cost Type, and the Year
	 * 				Will Return the most current Cost for that Fiscal Year 
	 */
	
	private static Vector returnMostCurrentCost(String environment, 
											    Vector inValues, 
												Connection conn)
	throws Exception
	{
		Vector returnValues = new Vector();
		StringBuffer throwError = new StringBuffer();
	    ResultSet rs = null;
		PreparedStatement findThem = null;
		try
		{
			try { 
				// on the READ only get the item number (group By) 
				//  Will then use the Item Number to get the Valid Company Cost
				findThem = conn.prepareStatement(buildSqlStatement(environment, "getByCostType", inValues));
				rs = findThem.executeQuery();
			} catch(Exception e)
			{
				throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			}		
			if (throwError.toString().equals(""))
			{
			   try
			   {
				  if (rs.next())
				  {
				  	 String newCost = rs.getString("KOCSU9").trim();
				  	 returnValues.addElement(newCost);
				  }
			   } catch(Exception e)
			   {
			   	  throwError.append(" error occured while Building Vector from sql statement. " + e);
			   } 
			} // end of the IF statement		
		} catch(Exception e)
		{
			throwError.append(" error occured. " + e);
		}
		finally
		{
			try
			{
				if (findThem != null)
					findThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
			try
			{
				if (rs != null)
					rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}	
		}	
		if (returnValues.size() == 0)
			returnValues.addElement("0");
		// return data.	
		if (!throwError.toString().trim().equals("")) 
		{
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceCostTypes.");
			throwError.append("returnMostCurrentCost(");
			throwError.append("String, Vector, Connection). ");
			throw new Exception(throwError.toString());
		}
		return returnValues;
	}

	/**
	 *   Method Created 8/19/10 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
	 * @return Vector - of DropDownSingle 
	 * 
	 *  Incoming Vector:
	 *    Element 1 = String - ONLY needed IF narrowing List
	 *    			Not currently used
	 */
	public static Vector dropDownFiscalYear(Vector inValues)
	{
		Vector ddit = new Vector();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ConnectionStack.getConnection();
			ddit = dropDownFiscalYear(inValues, conn);
		} catch (Exception e)
		{
			// Don't really need to worry about any exceptions
		}
		finally {
			if (conn != null)
			{
				try
				{
				   ConnectionStack.returnConnection(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// return value
		return ddit;
	}

	/**
	 * Method Created 8/19/10 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
	 * 				May also determine the type of drop down list
	 * @return Vector - of DropDownSingle
	 */
	private static Vector dropDownFiscalYear(Vector inValues, 
										   	 Connection conn)
	{
		Vector ddit = new Vector();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try
		{
			try {
				   // Get the list of Item Type Values - Along with Descriptions
			   listThem = conn.prepareStatement(buildSqlStatement("PRD", "ddFiscalYearForecast", inValues));
			   rs = listThem.executeQuery();
			 } catch(Exception e)
			 {
			 	System.out.println("error" + e);
			   	//throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			 }		
			 try
			 {
			 	while (rs.next())
			    {
			 		DropDownSingle dds = loadFieldsDropDownSingle("ddFiscalYearForecast", rs);
			 		ddit.addElement(dds);
	     		}// END of the IF Statement
			 } catch(Exception e)
			 {
				//throwError.append(" Error occured while Building Vector from sql statement. " + e);
			 } 		
		} catch (Exception e)
		{
			//throwError.append(e);
		}
		finally {
		   if (rs != null)
		   {
			   try
			   {
				  rs.close();
				} catch(Exception el){
					el.printStackTrace();
				}
		    }
		   if (listThem != null)
		   {
			   try
			   {
				  listThem.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		return ddit;
	}

	/**
	 * Load class fields from result set.
	 */
	
	public static DropDownSingle loadFieldsDropDownSingle(String requestType,
							          		  		     ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		DropDownSingle returnValue = new DropDownSingle();
		try{ 
		  if (requestType.equals("ddFiscalYearForecast"))
		  {
			returnValue.setValue(rs.getString("CSAYEAR").trim());
			returnValue.setDescription(rs.getString("CSAYEAR").trim());
		  }
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Drop Down Single class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceCostTypes.");
			throwError.append("loadFieldsDropDownSingle(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}
}

