/*
 * Created on March 5, 2008
 *
  * 
 */
package com.treetop.services;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;

import com.ibm.as400.access.*;
//import com.treetop.app.brokerage.*;
import com.treetop.businessobjectapplications.BrokerageHeader;
import com.treetop.businessobjects.DateTime;
import com.treetop.utilities.*;

/**
 * @author twalto .
 *
 * Services class to obtain and return data 
 * to business objects.
 * 
 * Brokerage Information
 */
public class ServiceBrokerage extends BaseService {

	public static final String library = "M3DJDPRD.";
	// Use for Testing
	//public static final String library = "APDEV.";

	/**
	 * 
	 */
	public ServiceBrokerage() {
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
	 * Use to Validate a Send in Item, Return Message if Item is not found
	 */
	
	public static void unpaidChangeYearPeriod(String environment, 
											  Vector incomingParms)
			throws Exception
	{
// 10/9/08 -- Currently NOT using this Method -- Not going to do this process   TWalton
		// Jim Betts talked to Kim Wangler and Heather Sauve, determined it was not needed
		StringBuffer throwError = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement updIt = null;
		try { 
			String requestType = "";
			String sqlString = "";
			
			// verify base class initialization.
			ServiceBrokerage a = new ServiceBrokerage();
						
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			
//			UpdBrokerage ub = (UpdBrokerage) incomingParms.elementAt(0);
//			if (!ub.getDriedUpdate().trim().equals(""))
//				requestType = "Dried";
//			if (!ub.getCpgUpdate().trim().equals(""))
//				requestType = "CPG";
	  // ONLY if you sent in an Update Value
			if (!requestType.equals(""))
			{	
				conn = ConnectionStack.getConnection();
				// Must update the Detail Records FIRST
				try {
					Vector parmClass = new Vector();
					if (requestType.equals("CPG"))
						parmClass.addElement("CPG");
					else
						parmClass.addElement("ING");
//					if (!ub.getDateTime().getM3FiscalYear().trim().equals(""))
//					{
//						parmClass.addElement(ub.getDateTime().getM3FiscalYear().trim());
//						parmClass.addElement(ub.getDateTime().getM3FiscalPeriod().trim());
//					}
//					else
//					{
//						parmClass.addElement("0");
//						parmClass.addElement("0");
//					}
					updateChangeYearPeriodDetail(environment, ("updateDetail" + requestType), parmClass, conn);
				} catch (Exception e) {
					throwError.append(" error trying to update the Detail Records. " + e);
				}
				ResultSet rs = null;
				PreparedStatement findIt = null;
				// Review and Update the Header Information
				if (throwError.toString().equals("")) {
					try {
						Vector parmClass = new Vector();
						sqlString = buildSqlStatement(environment, ("readPending" + requestType), parmClass);
					} catch (Exception e) {
						throwError.append(" error trying to build sqlString. " + e);
					}
				}
				//------------------------------	
				// Get Pending Records
				try
				{
					if (throwError.toString().equals(""))
					{	
						try {
							findIt = conn.prepareStatement(sqlString);
							rs = findIt.executeQuery();
						}
						catch(Exception e)
						{
							System.out.println(" TEST Valid SQL: " + e);
							throwError.append(" error occured executing the sql statement, to Retrieve Pending Invoice Information. " + e);
						}
					}
					if (throwError.toString().equals(""))
					{
						Vector byKey = new Vector();
						String saveCompany = "";
						String saveDivision = "";
						String saveAgreeID = "";
						String saveRecipient = "";
						while (rs.next())
						{
							try
							{
								// Logic will be done in the updateChangeDate Method
								if (!saveCompany.equals(rs.getString("OFCONO").trim()) ||
									!saveDivision.equals(rs.getString("OFDIVI").trim()) ||
									!saveAgreeID.equals(rs.getString("OFAGID").trim()) ||
									!saveRecipient.equals(rs.getString("OFBREC").trim()))
								{
									if (byKey.size() > 0)
									{// Process Records
										updateChangeYearPeriodProcessing(environment, requestType, incomingParms, byKey, conn);
										byKey = new Vector();
									}
									saveCompany   = rs.getString("OFCONO").trim();
									saveDivision  = rs.getString("OFDIVI").trim();
									saveAgreeID   = rs.getString("OFAGID").trim();
									saveRecipient = rs.getString("OFBREC").trim();
								}
								// Build the Vector of Header Pending Records 
								byKey.addElement(loadFieldsBrokerageHeader("loadHeader", rs));
							}
							catch(Exception e)
							{}
						}
						if (byKey.size() > 0)
						{// Process Records
							updateChangeYearPeriodProcessing(environment, requestType, incomingParms, byKey, conn);
						}
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
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the Invoice Date ");
			throwError.append("from the result set. " + e) ;
		// return connection.
		} finally {
			try {
				if (conn != null)
					ConnectionStack.returnConnection(conn);
			} catch (Exception el) {
				el.printStackTrace();
			}
		}

		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceBrokerage.");
			throwError.append("unpaidChangeDate(String: Environment, Vector)");
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
									 	    Vector requestClass)
		throws Exception 
	{
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();
		
		try { 
			if (inRequestType.equals("readPendingDried") ||
				inRequestType.equals("readPendingCPG"))
			{	
			  // build the sql statement.
				sqlString.append("SELECT * FROM " + library + "OBOPER ");
				sqlString.append(" WHERE OFSTAT = 10 ");
				if (inRequestType.equals("readPendingDried"))
				{	
					sqlString.append("AND (SUBSTRING(OFAGID, 1,3) = 'ING' ");
					sqlString.append(" OR SUBSTRING(OFAGID, 1,2) = 'FS') ");
				}
				if (inRequestType.equals("readPendingCPG"))
					sqlString.append("AND SUBSTRING(OFAGID, 1,3) = 'CPG' ");
				sqlString.append("ORDER BY OFCONO, OFDIVI, OFAGID, OFSTDE, ");
				sqlString.append("OFYEA4, OFBREC, OFPERI ");
			}
			// Update the Detail Records in OBOTRA
			if (inRequestType.equals("updateDetailCPG") ||
				inRequestType.equals("updateDetailDried"))
			{	
				Vector sentValues = (Vector) requestClass.elementAt(0);
			   // Vector Elements expected
			   // 0 = Type
			   // 1 = Year
			   // 2 = Period	
			   // build the sql statement.
				sqlString.append("UPDATE " + library + "OBOTRA ");
				sqlString.append("   SET OCYEA4 = " + sentValues.elementAt(1) + ", ");
				sqlString.append("  OCPERI = " + sentValues.elementAt(2));
				sqlString.append(" WHERE EXISTS (SELECT * FROM " + library + "OBOPER ");
				sqlString.append("     WHERE OFSTAT = 10 ");
				sqlString.append("    AND OCCONO = OFCONO AND OCDIVI = OFDIVI ");
				sqlString.append("    AND OCAGID = OFAGID AND OCSTDE = OFSTDE ");
				sqlString.append("    AND OCBREC = OFBREC ) ");
				if (((String) sentValues.elementAt(0)).trim().length() == 2)
					sqlString.append("AND SUBSTRING(OCAGID, 1,2) = '" + sentValues.elementAt(0));
				else
					sqlString.append("AND SUBSTRING(OCAGID, 1,3) = '" + sentValues.elementAt(0));
				sqlString.append("' ");
			}
			// Insert Header into OBOPER
			if (inRequestType.equals("insertHeader"))
			{	
			   // Vector Elements expected
			   // 0 = BrokerageHeader Class
				BrokerageHeader bh = (BrokerageHeader) requestClass.elementAt(0);
			   // build the sql statement.
				sqlString.append("INSERT INTO " + library + "OBOPER ");
				sqlString.append("VALUES(");
				sqlString.append(bh.getCompany() + ", ");
				sqlString.append("'" + bh.getDivision().trim() + "', ");
				sqlString.append("'" + bh.getAgreementID().trim() + "', "); 
				sqlString.append(bh.getValidFromDate() + ", ");
				sqlString.append("'" + bh.getRecipientAgreementBonus().trim() + "', ");
				sqlString.append(bh.getYear() + ", ");
				sqlString.append(bh.getPeriod() + ", ");
				sqlString.append(bh.getGeneratingValue() + ", ");
				sqlString.append(bh.getPayingAmount() + ", ");
				sqlString.append("'" + bh.getCurrency().trim() + "', ");
				sqlString.append(bh.getCreditedBonusAmount() + ", ");
				sqlString.append(bh.getPaymentDate() + ", ");
				sqlString.append(bh.getReservedBonusAmount() + ", ");
				sqlString.append(bh.getReservedAmountLocalCurr() + ", ");
				sqlString.append("'" + bh.getStatus().trim() + "', ");
				sqlString.append(bh.getEntryDate() + ", ");
				sqlString.append(bh.getEntryTime() + ", ");
				sqlString.append(bh.getChangeDate() + ", ");
				sqlString.append(bh.getChangeNumber() + ", ");
				sqlString.append("'" + bh.getChangedBy() + "' ");
				sqlString.append(") ");
			}		
			if (inRequestType.equals("updateOLDPendingHeader"))
			{	
				// Vector Elements expected
				// 0 = BrokerageHeader Class
				BrokerageHeader bh = (BrokerageHeader) requestClass.elementAt(0);
				// build the sql statement.
				sqlString.append("UPDATE " + library + "OBOPER ");
				sqlString.append(" SET OFSTAT = 40, ");
				sqlString.append("   OFABOV = 0, OFABOA = 0, OFPBOA = 0, ");
				sqlString.append("   OFALBO = 0, OFALBL = 0, ");
				sqlString.append("   OFLMDT = " + bh.getChangeDate() + ", ");
				sqlString.append("   OFCHID = '" + bh.getChangedBy() + "' ");
				sqlString.append(" WHERE OFSTAT = 10 ");
				sqlString.append("   AND OFCONO = " + bh.getCompany() + " ");
				sqlString.append("   AND OFDIVI = '" + bh.getDivision() + "' ");
				sqlString.append("   AND OFAGID = '" + bh.getAgreementID() + "' ");
				sqlString.append("   AND OFBREC = '" + bh.getRecipientAgreementBonus() + "' ");
				sqlString.append("   AND (OFYEA4 <> " + bh.getYear() + " ");
				sqlString.append("   OR OFPERI <> " + bh.getPeriod() + ") ");
			}
			if (inRequestType.equals("updateHeader"))
			{	
				// Vector Elements expected
				// 0 = BrokerageHeader Class
				BrokerageHeader bh = (BrokerageHeader) requestClass.elementAt(0);
				// build the sql statement.
				sqlString.append("UPDATE " + library + "OBOPER ");
				sqlString.append(" SET OFSTAT = " + bh.getStatus() + ", ");
				sqlString.append("   OFABOV = " + bh.getGeneratingValue() + ", ");
				sqlString.append("   OFABOA = " + bh.getPayingAmount() + ", ");
				sqlString.append("   OFPBOA = " + bh.getCreditedBonusAmount() + ", ");
				sqlString.append("   OFALBO = " + bh.getReservedBonusAmount() + ", ");
				sqlString.append("   OFALBL = " + bh.getReservedAmountLocalCurr() + ", ");
				sqlString.append("   OFLMDT = " + bh.getChangeDate() + ", ");
				sqlString.append("   OFCHID = '" + bh.getChangedBy() + "' ");
				sqlString.append(" WHERE OFSTAT = 10 ");
				sqlString.append("   AND OFCONO = " + bh.getCompany() + " ");
				sqlString.append("   AND OFDIVI = '" + bh.getDivision() + "' ");
				sqlString.append("   AND OFAGID = '" + bh.getAgreementID() + "' ");
				sqlString.append("   AND OFBREC = '" + bh.getRecipientAgreementBonus() + "' ");
				sqlString.append("   AND OFYEA4 = " + bh.getYear() + " ");
				sqlString.append("   AND OFPERI = " + bh.getPeriod() + " ");
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement"
							 + " for request type " + inRequestType + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceBrokerage.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		
		return sqlString.toString();
	}

	/**
	 * Determine which records should be updated and or inserted
	 *    based on a Vector of all the information.
	 */
	private static void updateChangeYearPeriodProcessing(String environment,
														 String requestType,
														 Vector incomingParms,
														 Vector processRecords,
														 Connection conn)
		throws Exception
	{
//		 10/9/08 -- Currently NOT using this Method -- Not going to do this process   TWalton
		// Jim Betts talked to Kim Wangler and Heather Sauve, determined it was not needed
		StringBuffer throwError = new StringBuffer();
		if (processRecords.size() > 0)
		{
//		   UpdBrokerage ub = (UpdBrokerage) incomingParms.elementAt(0);
//		   String currentYear = ub.getDateTime().getM3FiscalYear().trim();
//		   String currentPeriod = ub.getDateTime().getM3FiscalPeriod().trim();
		   if (processRecords.size() == 1)
		   {
		   	  // ONLY ONE RECORD
		   	  BrokerageHeader bh = (BrokerageHeader) processRecords.elementAt(0);
//		   	  if (currentYear.trim().equals(bh.getYear().trim()) &&
//		   	  	  currentPeriod.trim().equals(bh.getPeriod()))
//		   	  {
		   	  	// Do not need to do anything,  is already set
//		   	  }
//		   	  else
//		   	  {
		   	  	// INSERT a New Period Record
//		   	  	  try {
//		   	  	  	bh.setEntryDate(ub.getDateTime().getDateFormatyyyyMMdd());
//		   	  	  	bh.setChangeDate(ub.getDateTime().getDateFormatyyyyMMdd());
//		   	  	  	bh.setEntryTime(ub.getDateTime().getTimeFormathhmmss());
//		   	  	  	bh.setChangedBy(ub.getUpdateUser());
//		   	  	  	bh.setYear(currentYear);
//		   	  	  	bh.setPeriod(currentPeriod);
//		   	  	  	Vector sendParms = new Vector();
//		   	  	  	sendParms.add(bh);
//		   	  	  	insertYearPeriod(environment, requestType, sendParms, conn);
//		   	  	  }catch(Exception e)
//				  {
//		   	  	  	throwError.append("Error in Inserting a Record: " + e);
//				  }
		   	   // Update Old Pending Record
		   	  	  try {
		   	  	  	Vector sendParms = new Vector();
		   	  	  	sendParms.add(bh);
		   	  	  	updateYearPeriod(environment, "updateOLDPendingHeader", sendParms, conn);
		   	  	  }catch(Exception e)
				  {
		   	  	  	throwError.append("Error in Updating a Record: " + e);
				  }		   	  
//		   	  }
		   }
		   else
		   {
		   	  BrokerageHeader bh = (BrokerageHeader) processRecords.elementAt(0);
		   	  BigDecimal genValue = new BigDecimal("0");
		   	  BigDecimal payAmount = new BigDecimal("0");
		   	  BigDecimal credAmount = new BigDecimal("0");
		   	  BigDecimal resBonus = new BigDecimal("0");
		   	  BigDecimal resLocal = new BigDecimal("0");
		   	  try
			  {
		   	  		genValue = new BigDecimal(bh.getGeneratingValue());
		   	  		payAmount = new BigDecimal(bh.getPayingAmount());
		   	  		credAmount = new BigDecimal(bh.getCreditedBonusAmount());
		   	  		resBonus = new BigDecimal(bh.getReservedBonusAmount());
		   	  		resLocal = new BigDecimal(bh.getReservedAmountLocalCurr());
			  }
		   	  catch(Exception e)
			  {
		   	  	
			  }
		   	  String insertNewPeriod = "Y";
		   	  // PROCESS the RECORDS within the VECTOR (Add Quantities)
		   	  for (int x = 1; x < processRecords.size(); x++)
		   	  {
		   	  	try
				{
		   	  		BrokerageHeader addValues = (BrokerageHeader) processRecords.elementAt(x);
//		   	  		if (currentYear.equals(addValues.getYear()) &&
//		   	  			currentPeriod.equals(addValues.getPeriod()))
//		   	  		   insertNewPeriod = "N";
		   	  		genValue = genValue.add(new BigDecimal(addValues.getGeneratingValue()));
		   	  		payAmount = payAmount.add(new BigDecimal(addValues.getPayingAmount()));
		   	  		credAmount = credAmount.add(new BigDecimal(addValues.getCreditedBonusAmount()));
		   	  		resBonus = resBonus.add(new BigDecimal(addValues.getReservedBonusAmount()));
		   	  		resLocal = resLocal.add(new BigDecimal(addValues.getReservedAmountLocalCurr()));
				}
		   	  	catch(Exception e)
				{
		   	  		System.out.println("Error Found in Processing of Vector Record : " + e);
				}
		   	  }
	   	  	// INSERT a New Period Record
			  try {
//	   	  	  	bh.setEntryDate(ub.getDateTime().getDateFormatyyyyMMdd());
//	   	  	  	bh.setChangeDate(ub.getDateTime().getDateFormatyyyyMMdd());
//	   	  	  	bh.setEntryTime(ub.getDateTime().getTimeFormathhmmss());
//	   	  	  	bh.setChangedBy(ub.getUpdateUser());
//	   	  	  	bh.setYear(currentYear);
//	   	  	  	bh.setPeriod(currentPeriod);
	   	  	  	bh.setGeneratingValue(genValue.toString());
	   	  	  	bh.setPayingAmount(payAmount.toString());
	   	  	  	bh.setCreditedBonusAmount(credAmount.toString());
	   	  	  	bh.setReservedBonusAmount(resBonus.toString());
	   	  	  	bh.setReservedAmountLocalCurr(resLocal.toString());
	   	  	  	Vector sendParms = new Vector();
	   	  	  	sendParms.add(bh);
	   	  	  	if (insertNewPeriod.trim().equals("Y"))
	   	  	  	   insertYearPeriod(environment, "insertHeader", sendParms, conn);
	   	  	  	else
	   	  	  		updateYearPeriod(environment, "updateHeader", sendParms, conn);
	   	  	  }catch(Exception e)
			  {
	   	  	  	throwError.append("Error in Inserting a Record: " + e);
			  }
	   	   // Update Old Pending Record
	   	  	  try {
	   	  	  	Vector sendParms = new Vector();
	   	  	  	sendParms.add(bh);
	   	  	  	updateYearPeriod(environment, "updateOLDPendingHeader", sendParms, conn);
	   	  	  }catch(Exception e)
			  {
	   	  	  	throwError.append("Error in Updating a Record: " + e);
			  }		   	  
		   	
		   	
		   }
		}	
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceBrokerage.");
			throwError.append("updateChangeDateYearPeriodProcessing(String: Environment, Vector)");
			throw new Exception(throwError.toString());
		}
		return;
	}

	/**
	 * Load class fields from result set.
	 */
		
	public static BrokerageHeader loadFieldsBrokerageHeader(String requestType,
								                        	ResultSet rs)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BrokerageHeader returnBH = new BrokerageHeader();
		try{ 
			if (requestType.equals("loadHeader"))
			{
				try
				{
//					 Brokerage File -- OBOPER
					 returnBH.setCompany(rs.getString("OFCONO").trim());
					 returnBH.setDivision(rs.getString("OFDIVI").trim());
					 returnBH.setAgreementID(rs.getString("OFAGID").trim());
					 returnBH.setValidFromDate(rs.getString("OFSTDE").trim());
					 returnBH.setRecipientAgreementBonus(rs.getString("OFBREC").trim());
					 returnBH.setYear(rs.getString("OFYEA4").trim());
					 returnBH.setPeriod(rs.getString("OFPERI").trim());
					 returnBH.setGeneratingValue(rs.getString("OFABOV").trim());
					 returnBH.setPayingAmount(rs.getString("OFABOA").trim());
					 returnBH.setCurrency(rs.getString("OFCUCD").trim());
					 returnBH.setCreditedBonusAmount(rs.getString("OFPBOA").trim());
					 returnBH.setPaymentDate(rs.getString("OFPYDT").trim());
					 returnBH.setReservedBonusAmount(rs.getString("OFALBO").trim());
					 returnBH.setReservedAmountLocalCurr(rs.getString("OFALBL").trim());
					 returnBH.setStatus(rs.getString("OFSTAT").trim());
					 returnBH.setEntryDate(rs.getString("OFRGDT").trim());
					 returnBH.setEntryTime(rs.getString("OFRGTM").trim());
					 returnBH.setChangeDate(rs.getString("OFLMDT").trim());
					 returnBH.setChangeNumber(rs.getString("OFCHNO").trim());
					 returnBH.setChangedBy(rs.getString("OFCHID").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the BrokerageHeader class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceBrokerage.");
			throwError.append("loadFieldsBrokerageHeader(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnBH;
	}

	/**
	 * Use to Update the Detail section of the Brokerage:
	 *       OBOTRA -- Detail File
	 */
		
	private static void updateChangeYearPeriodDetail(String environment,
													 String requestType, 
													 Vector incomingParms, 
													 Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		PreparedStatement updIt = null;
		try { 
			String sqlString = "";
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(incomingParms);
				sqlString = buildSqlStatement(environment, requestType, parmClass);
			} catch (Exception e) {
				throwError.append(" error trying to build sqlString to update Year Period Detail. " + e);
			}
			// 	get a connection. execute sql
			if (throwError.toString().equals("")) {
				try {
					updIt = conn.prepareStatement(sqlString);
					updIt.executeUpdate();
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement to update Year Period Detail. " + e);
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the Invoice Date for the Detail Records. " + e) ;
			// return Prepared Statement
		} finally {
			try {
				if (updIt != null)
					updIt.close();
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceBrokerage.");
			throwError.append("updateChangeYearPeriodDetail(");
			throwError.append("String:Environment,String requestType, Vector, Connection)");
			throw new Exception(throwError.toString());
		}
		return;
	}

	/**
	 * Use to Update the Detail section of the Brokerage:
	 *       OBOPER -- Detail File
	 */
		
	private static void insertYearPeriod(String environment,
										 String requestType, 
										 Vector incomingParms,  // Brokerage Header
										 Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		PreparedStatement addIt = null;
		try { 
			String sqlString = "";
			try {
				sqlString = buildSqlStatement(environment, "insertHeader", incomingParms);
			} catch (Exception e) {
				throwError.append(" error trying to build sqlString to update Year Period Detail. " + e);
			}
			// 	get a connection. execute sql
			if (throwError.toString().equals("")) {
				try {
					addIt = conn.prepareStatement(sqlString);
					addIt.executeUpdate();
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement to update Year Period Detail. " + e);
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the Invoice Date for the Detail Records. " + e) ;
			// return Prepared Statement
		} finally {
			try {
				if (addIt != null)
					addIt.close();
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceBrokerage.");
			throwError.append("insertYearPeriod(");
			throwError.append("String:Environment,String requestType, Vector, Connection)");
			throw new Exception(throwError.toString());
		}
		return;
	}

	/**
	 * Use to Update the Detail section of the Brokerage:
	 *       OBOPER -- Detail File
	 */
		
	private static void updateYearPeriod(String environment,
										 String requestType, 
										 Vector incomingParms,  // Brokerage Header
										 Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		PreparedStatement updIt = null;
		try { 
			String sqlString = "";
			try {
				sqlString = buildSqlStatement(environment, requestType, incomingParms);
			} catch (Exception e) {
				throwError.append(" error trying to build sqlString to update Year Period Detail. " + e);
			}
			// 	get a connection. execute sql
			if (throwError.toString().equals("")) {
				try {
					updIt = conn.prepareStatement(sqlString);
					updIt.executeUpdate();
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement to update Year Period Detail. " + e);
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the Invoice Date for the Detail Records. " + e) ;
			// return Prepared Statement
		} finally {
			try {
				if (updIt != null)
					updIt.close();
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceBrokerage.");
			throwError.append("updateYearPeriod(");
			throwError.append("String:Environment,String requestType, Vector, Connection)");
			throw new Exception(throwError.toString());
		}
		return;
	}
}