/*
 * Created on Dec 1, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.services;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import javax.sql.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import com.treetop.app.function.UpdFunctionDetail;
import com.treetop.app.item.UpdItem;
import com.treetop.businessobjects.*;
import com.treetop.data.*;
import com.treetop.utilities.*;
import com.treetop.utilities.html.*;

/**
 * @author thaile
 *
  * Tickler: Assign A date and user to A Business Process.
  * 
  * Data files
  * 	GMPBFDEF - Function Definition.
  * 	GMPCPDEF - Phase Definition.
  * 	GMPDFGRP - Function Group.
  * 	GMPEFDPD - Function Dependencies (to other functions).
  * 	GMPFMSTR - Function Master at unique value.
 */
public class ServiceTickler extends BaseService {
	
	public static final String library = "DBPRD.";
	public static final String m3Lib = "M3DJDPRD.";
	//public static final String library = "DBTST.";
	
	/**
	 * 
	 */
	public ServiceTickler() {
		super();
	}
	
	
	/**
	 * Build an sql statement.
	 * @param InqReserveNumbers
	 * @return sql string
	 * @throws Exception
	 */

	private static String buildSqlStatement(String inRequestType,
											Vector requestClass)
		throws Exception 
	{
		StringBuffer sqlString = new StringBuffer();
		String whereClause = "";
		StringBuffer throwError = new StringBuffer();
		String thisDropDown = "";


		try { // Single entry selection criteria.
			if(inRequestType.equals("singleEntry"))
			{
				// cast the incoming parameter class.
				TicklerFunctionDetail fromVb = (TicklerFunctionDetail) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT * FROM ");
				sqlString.append(library + "GMPDFGRP ");
				
				sqlString.append("INNER JOIN " + library + "GMPBFDEF ");
				sqlString.append("ON GMDGRP = GMBGRP ");
				
				sqlString.append("LEFT OUTER JOIN " + library + "GMPFMSTR ");
				sqlString.append("ON GMDGRP = GMFGRP AND GMBNBR = GMFNBR ");
				
				sqlString.append("LEFT OUTER JOIN " + m3Lib + "MITMAS ");
				sqlString.append("ON MMCONO = '100' and GMFUNQ = MMITNO ");
				
				sqlString.append("LEFT OUTER JOIN " + library + "GMPCPDEF ");
				sqlString.append("ON GMDGRP = GMCGRP AND GMBPNM = GMCPNM ");
				
				String sqlWhere = (" WHERE GMDGRP = '" + fromVb.getGroup().trim()) + "' ";
				sqlWhere = sqlWhere + "AND GMBNBR = " + fromVb.getNumber() + " ";
				sqlWhere = sqlWhere + "AND GMFUNQ = '" + fromVb.getIdKeyValue().trim() + "' ";
				sqlString.append(sqlWhere);
			}
			
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		try { // List of Functions by Group and Id Value.
			if(inRequestType.equals("functionList"))
			{
				// cast the incoming parameter class.
				TicklerFunctionDetail fromVb = (TicklerFunctionDetail) requestClass.elementAt(0);
				
				// build the sql statement. --Change to be specific fields
				sqlString.append("SELECT  GMDGRP, GMBNBR, GMBDSC, GMBSEQ, GMBPNM, ");
				sqlString.append("GMCSEQ, GMCDYS, GMBRMD, GMBDLK, GMBULK, GMBPER, ");
				sqlString.append("GMBAPV, MMITDS, MMRESP, GMFUNQ, GMFPER, GMFSTS, ");
				sqlString.append("GMFTDT, GMFCDT, GMFCTM, GMFCUR, GMFINO, GMEDON ");
				sqlString.append("FROM " + library + "GMPDFGRP ");
				
				sqlString.append("INNER JOIN " + library + "GMPBFDEF ");
				sqlString.append("ON GMDGRP = GMBGRP ");
				
				sqlString.append("LEFT OUTER JOIN " + library + "GMPFMSTR ");
				sqlString.append("ON GMDGRP = GMFGRP ");
				sqlString.append("AND GMBNBR = GMFNBR ");
				sqlString.append("AND GMFUNQ = '" + fromVb.getIdKeyValue().trim() + "' ");
				
				sqlString.append("LEFT OUTER JOIN " + library + "GMPCPDEF ");
				sqlString.append("ON GMDGRP = GMCGRP AND GMBPNM = GMCPNM ");
				
				sqlString.append("LEFT OUTER JOIN " + library + "GMPEFDPD ");
				sqlString.append("ON GMDGRP = GMEGRP ");
				sqlString.append("AND GMBNBR = GMENBR ");
				
				sqlString.append("LEFT OUTER JOIN " + m3Lib + "MITMAS");
				sqlString.append(" ON MMCONO = '100' AND MMITNO = '" + fromVb.getIdKeyValue().trim() + "' ");
				
				sqlString.append(" WHERE GMDGRP = '" + fromVb.getGroup().trim() + "' ");
				sqlString.append("ORDER BY GMBSEQ ");
			}
			
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		try { // List of Functions by Group and Id Value.
			if(inRequestType.equals("findNewItemEmails"))
			{
				// cast the incoming parameter class.
				TicklerFunctionDetail fromVb = (TicklerFunctionDetail) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT * FROM ");
				sqlString.append(library + "GMPFMSTR ");
				
				sqlString.append("INNER JOIN " + library + "GMPDFGRP ");
				sqlString.append("ON GMFGRP = GMDGRP ");
				
				sqlString.append("INNER JOIN " + library + "GMPBFDEF ");
				sqlString.append("ON GMFGRP = GMBGRP AND GMFNBR = GMBNBR ");
				
				sqlString.append("INNER JOIN " + library + "GMPCPDEF ");
				sqlString.append("ON GMFGRP = GMCGRP AND GMBPNM = GMCPNM ");
				
				sqlString.append("INNER JOIN " + library + "MSPWITRS ");
				sqlString.append("ON GMFUNQ = MSWRSC ");
				
				sqlString.append("INNER JOIN " + m3Lib + "MITMAS ");
				sqlString.append("ON MMCONO = '100' AND GMFUNQ = MMITNO ");
				sqlString.append(" AND (MMSTAT = '10' OR MMSTAT = '20') ");
				
				sqlString.append("WHERE GMFCDT = 0  AND GMFPER <> ' ' ");
				sqlString.append("AND GMFSTS <> 'complete' ");
				if (fromVb.getGroup() != null && !fromVb.getGroup().equals(""))
				   sqlString.append("AND GMFGRP = '" + fromVb.getGroup().trim() + "' ");
				
//			sqlString.append("AND GMFPER = 'DARCHE' ");
				//sqlString.append("AND GMFUNQ <> ' ' ");
		//		sqlString.append("AND GMFUNQ = '100001' ");
				
			//remove this line after testing.
			//sqlString.append("AND GMFPER = 'THAILE' OR GMFPER = 'TWALTO'");
				
				sqlString.append("ORDER BY GMFPER, GMFUNQ, GMFNBR ");
			}
			
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // List of an Item Tickler's in incomplete status.
			if(inRequestType.equals("findIncompleteTicklers"))
			{
				// cast the incoming parameter class.
				UpdItem fromVb = (UpdItem) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT * ");
				sqlString.append("FROM " + library + "GMPFMSTR ");

				sqlString.append("INNER JOIN " + library + "GMPBFDEF ");
				sqlString.append("ON GMFGRP = GMBGRP AND GMFNBR = GMBNBR ");

				sqlString.append("WHERE GMFUNQ = '" + fromVb.getItem().trim() + "' ");
				sqlString.append("AND GMFSTS <> 'complete' ");
				
				sqlString.append("ORDER BY GMBSEQ ");
			}
			
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // Add a Tickler Function Detail Entry.
			if (inRequestType.equals("addDetail"))
			{
				// cast the incoming parameter class.
				TicklerFunctionDetail fromVb = (TicklerFunctionDetail) requestClass.elementAt(0);
							
				// build the sql statement.
				sqlString.append("INSERT  INTO  " + library + "GMPFMSTR ");
				sqlString.append("VALUES(");
				sqlString.append("'" + fromVb.getGroup().trim() + "', ");
				sqlString.append(fromVb.getNumber() + ", "); 
				sqlString.append("'" + fromVb.getIdKeyValue().trim() + "', ");
				sqlString.append("'" + fromVb.getRespPerson().trim() + "', ");
				if (fromVb.getStatus().trim().equals(""))
				   sqlString.append("'incomplete', ");
				else
				   sqlString.append("'" + fromVb.getStatus().trim() + "', ");
				
				// edit Setup Due Date.
				String targetDate = "0";
				if (!fromVb.getTargetDate().equals("0") &&
					!fromVb.getTargetDate().equals(""))
				{
					String[] ckDates = CheckDate.validateDate(fromVb.getTargetDate());
					
					if (ckDates[6].equals(""))
						targetDate = ckDates[4];
				}
				
				sqlString.append(new Integer(targetDate) + ", ");
				sqlString.append(fromVb.getCompletionDate().trim() + ", ");
				sqlString.append(fromVb.getCompletionTime() + ", ");
				sqlString.append("'" + fromVb.getCompletionUser().trim() + "', ");
				sqlString.append("' ') "); // always blank on add for initial notification.
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		try { // Update Tickler Function Detail Entry.  
			if (inRequestType.equals("updateSingle"))
			{
				// cast the incoming parameter class.
				TicklerFunctionDetail fromVb = (TicklerFunctionDetail) requestClass.elementAt(0);
				
				// edit Target Date.
				String targetDate = "0";
				if (!fromVb.getTargetDate().equals("0") &&
					!fromVb.getTargetDate().equals(""))
				{
					String[] ckDates = CheckDate.validateDate(fromVb.getTargetDate());
					
					if (ckDates[6].equals(""))
						targetDate = ckDates[4];
				}
				
				// edit Completion Date.
				String compDate = "0";
				if (!fromVb.getCompletionDate().equals("0") &&
					!fromVb.getCompletionDate().equals(""))
				{
					String[] ckDates = CheckDate.validateDate(fromVb.getCompletionDate());
					
					if (ckDates[6].equals(""))
						compDate = ckDates[4];
				}

				// build the sql statement.
				sqlString.append("UPDATE " + library + "GMPFMSTR ");
				sqlString.append(" SET GMFPER = '" + fromVb.getRespPerson().trim() + "',");
				sqlString.append(" GMFSTS = '" + fromVb.getStatus().trim() + "',");
				sqlString.append(" GMFTDT = " + new Integer(targetDate).intValue() + ",");
				sqlString.append(" GMFCDT = " + new Integer(compDate).intValue() + ",");
				sqlString.append(" GMFCTM = " + fromVb.getCompletionTime().intValue() + ",");
				sqlString.append(" GMFCUR = '" + fromVb.getCompletionUser().trim() + "', ");
				sqlString.append(" GMFINO = '" + fromVb.getInitialNotification().trim() + "' ");
				
				sqlString.append(" WHERE GMFGRP = '" + fromVb.getGroup().trim() + "' ");
				sqlString.append(" AND GMFNBR = " + fromVb.getNumber() + " ");
				sqlString.append(" AND GMFUNQ = '" + fromVb.getIdKeyValue().trim() + "' ");
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement");
				throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		try { // Complete All.  
			if (inRequestType.equals("updateAll"))
			{
				String group = (String) requestClass.elementAt(0);
				String uniqueID = (String) requestClass.elementAt(1);
			  	DateTime dt = UtilityDateTime.getSystemDate();
				
				// build the sql statement.
				sqlString.append("UPDATE " + library + "GMPFMSTR ");
				sqlString.append(" SET GMFSTS = 'complete',");
				sqlString.append(" GMFCDT = " + dt.getDateFormatyyyyMMdd() + ",");
				sqlString.append(" GMFCTM = " + dt.getTimeFormathhmmss() + "");
				sqlString.append(" WHERE GMFGRP = '" + group + "' ");
				sqlString.append(" AND GMFUNQ = '" + uniqueID + "' ");
				sqlString.append(" AND GMFSTS <> 'complete' ");
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement");
				throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // Complete All with unique Item.  
			if (inRequestType.equals("completeItemEmails"))
			{
				String uniqueID = (String) requestClass.elementAt(0);
			  	DateTime dt = UtilityDateTime.getSystemDate();
				
				// build the sql statement.
				sqlString.append("UPDATE " + library + "GMPFMSTR ");
				sqlString.append(" SET GMFSTS = 'complete',");
				sqlString.append(" GMFCDT = " + dt.getDateFormatyyyyMMdd() + ",");
				sqlString.append(" GMFCTM = " + dt.getTimeFormathhmmss() + "");
				sqlString.append(" WHERE GMFUNQ = '" + uniqueID + "' ");
				sqlString.append(" AND GMFSTS <> 'complete' ");
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement");
				throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try {  
			if (inRequestType.equals("UpdateTicklerDate"))
			{
				ResultSet rs = (ResultSet) requestClass.elementAt(0);
				String date = (String) requestClass.elementAt(1);
				
				// build the sql statement.
				sqlString.append("UPDATE " + library + "GMPFMSTR ");
				sqlString.append("SET GMFTDT = " + date + " ");
				sqlString.append("WHERE GMFGRP = '" + rs.getString("GMFGRP").trim() + "' ");
				sqlString.append("AND GMFNBR = " + rs.getString("GMFNBR") + " ");
				sqlString.append("AND GMFUNQ = '" + rs.getString("GMFUNQ").trim() + "' ");
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement");
				throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // Find out if any tasks have NOT been completedList of Functions by Group and Id Value.
			if(inRequestType.equals("allComplete"))
			{
				// cast the incoming parameter class.
				TicklerFunctionDetail fromVb = (TicklerFunctionDetail) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT * FROM ");
				sqlString.append(library + "GMPFMSTR ");
				sqlString.append(" WHERE GMFUNQ = '" + fromVb.getIdKeyValue().trim() + "' ");
				sqlString.append(" AND GMFSTS <> 'complete' ");
				sqlString.append("ORDER BY GMFGRP, GMFNBR ");
			}
			
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		

		try { // Get all phase records for group (helps determine target date).
			if (inRequestType.equals("groupPhases"))
			{
		
				// cast the incoming parameter class.
				TicklerFunctionDetail fromVb = (TicklerFunctionDetail) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT * FROM ");
				sqlString.append(library + "GMPCPDEF ");
				sqlString.append("WHERE GMCGRP = '" + fromVb.getGroup().trim() + "' ");
				sqlString.append("AND GMCSEQ <= '" + fromVb.getPhaseSequence().trim() + "' ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // Get dependant entries for a single function.
			if (inRequestType.equals("dependantOn") ||
				inRequestType.equals("dependantOnFilter"))
			{
				
				// cast the incoming parameter class.
				TicklerFunctionDetail fromVb = (TicklerFunctionDetail) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT * FROM ");
				sqlString.append(library + "GMPEFDPD ");
				if (inRequestType.equals("dependantOnFilter"))
				{
					sqlString.append("INNER JOIN " + library + "GMPFMSTR ");
					sqlString.append(" ON GMFGRP = GMEGRP AND GMFNBR = GMENBR ");
					sqlString.append(" AND GMFSTS <> 'complete' ");
					sqlString.append(" AND GMFUNQ = '" + fromVb.getIdKeyValue() + "' ");
					
				}
				sqlString.append("WHERE GMEGRP = '" + fromVb.getGroup().trim() + "' ");
				sqlString.append("AND GMENBR = " + fromVb.getNumber() + " ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType " + inRequestType + ". " + e); 
		}
		
		
		// 6/23/11 TW - no longer using the find, and then delete one at a time method
		   //  adjusted the SQL to find and delete in one statement.
		try { // Delete Function Master Entries.
			if (inRequestType.equals("deleteRogue"))
			{
//				 cast the incoming parameter class.
				String group = "";
				if (requestClass.size() > 0)
				   group = (String) requestClass.elementAt(0);
				
				sqlString.append("DELETE FROM " + library + "GMPFMSTR ");
				sqlString.append("WHERE NOT EXISTS (SELECT * ");
				sqlString.append("  FROM " + library + "MSPWITRS ");
				sqlString.append("     WHERE GMFUNQ = MSWRSC) ");
				if (!group.trim().equals(""))
					sqlString.append("AND GMFGRP = '" + group + "' ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		try { // Delete all the functions for a unique Number
			if (inRequestType.equals("deleteDetail"))
			{
				// cast the incoming parameter class.
				TicklerFunctionDetail fromVb = (TicklerFunctionDetail) requestClass.elementAt(0);
							
				// build the sql statement.
				sqlString.append("DELETE FROM " + library + "GMPFMSTR ");
				sqlString.append("WHERE GMFGRP = '" + fromVb.getGroup() + "' ");
				sqlString.append("  AND GMFUNQ = '" + fromVb.getIdKeyValue() + "' ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("buildSqlStatement(String, Vector)");
			throw new Exception(throwError.toString());
		}

		return sqlString.toString();
	}
	
	
	/**
	 * Return a vector of TicklerFunctionDetail business
	 * objects using the incoming class for selection 
	 * criteria.
	 *  4/11/12 - TWalton, change to send connection from into Private Methods Building here  
	 * @param TicklerFunctionDetail.
	 * @return Vector of TicklerFunctionDetail objects 
	 * @throws Exception
	 */
	
	public static Vector buildFunctionList(TicklerFunctionDetail fromVb)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnVector = new Vector();
		
		Connection conn = null; 
		try {
			conn = ServiceConnection.getConnectionStack15();
			
			rtnVector = findFunctionList(fromVb, conn);
			
		} catch(Exception e)
		{
			throwError.append("Exception found when building the Function List: " + e) ;
		// return connection.
		} finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack15(conn);
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("Tickler.");
			throwError.append("buildFunctionList(");
			throwError.append("TicklerFunctionDetail)." );
			throw new Exception(throwError.toString());
		}
	
		// return value
		return rtnVector;
	}
	
	
	/**
	 * Return a TicklerFunctionDetail object. Use the incoming
	 * TicklerFunctionDetail object for selection criteria. 
	 * 4/11/12 - TWalton, change to send connection from into Private Methods Building here  
	 * @param TicklerFunctionDetail.
	 * @return Resource business object.
	 * @throws Exception
	 */
	
	public static TicklerFunctionDetail buildTicklerDetail(TicklerFunctionDetail fromVb)
									
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		TicklerFunctionDetail dtl = new TicklerFunctionDetail();
		
		Connection conn = null; 
		try {
			conn = ServiceConnection.getConnectionStack15();
			
			dtl = findTicklerDetail(fromVb, conn);
			
			// Get Dependent Functions.
			Vector vector = findTicklerDependOn("", fromVb, conn);
			dtl.setDependantFunctions(vector);
			
		} catch(Exception e)
		{
			throwError.append("Exception found when retrieving data: " + e) ;
		// return connection.
		} finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack15(conn);
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceTickler.");
			throwError.append("buildTicklerDetail(");
			throwError.append("TicklerFunctionDetail) ");
			throw new Exception(throwError.toString());
		}

		// return value
		return dtl;
	}

	
	/**
	 * Delete any rouge entries in the Tickler Master file
	 * by Group Type.
	 *    6/23/11 - TW --- Adjusted to use only 1 SQL statement and if
	 *       group is blank it will delete ALL the groups rogue information
	 * @param String (Group Type)
	 * @throws Exception
	 */
	public static void deleteRogueEntries(String group)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String requestType = ""; 
		Connection conn = null;
		String sqlDelete = "";
		Statement deleteIt = null;
		
		// validate
		if (group == null)
			group = "";
		
		// verify base class initialization.
		ServiceTickler st = new ServiceTickler();

		try {
			conn = ServiceConnection.getConnectionStack15();
				
			// Pass along the incoming parameter. 
			Vector parmClass = new Vector();
			parmClass.addElement(group);
			// build the delete sql statement.
			// Pass along the incoming parameter. 
			requestType = "deleteRogue";
			parmClass = new Vector();
			sqlDelete = buildSqlStatement(requestType, parmClass);
				
			//execute the delete statement.
			deleteIt = conn.createStatement();
			deleteIt.executeUpdate(sqlDelete);
				
		} catch(Exception e) {
			throwError.append(" Error while atempting to delete ");
			throwError.append("entries from the Tickler Master file. ");
			throwError.append(e);
		} finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack15(conn);
				if (deleteIt != null)
					deleteIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("deleteRogueEntries(group:" + group + "). ");
			throw new Exception(throwError.toString());
		}
	}
	
	
	/**
	 * Receive a TicklerFunctionDetail object and load the
	 * dependantFunctions vector. Use the incoming
	 * TicklerFunctionDetail object for the selection criteria.
	 * * 4/11/12 - TWalton, change to send connection from Public Method
	 * @param TicklerFunctionDetail business object.
	 * @return TicklerFunctionDetail business object.
	 */
	private static Vector findTicklerDependOn(String typeIn, TicklerFunctionDetail fromVb, Connection conn)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnVector = new Vector();
		String sqlString = "";
	//	Connection conn = null;
		Statement findIt = null;
		ResultSet rs = null;
		String requestType = "dependantOn";
		if (typeIn.trim().equals("filter"))
			requestType = "dependantOnFilter";
		
		// verify base class initialization.
		ServiceTickler st = new ServiceTickler();

		// get the sql statement.
		try {
			
			// set selection criteria.
			Vector parmClass = new Vector();
			parmClass.addElement(fromVb);
			
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error trying to build sql statement" + e);
		}
		
		// get a connection. execute sql, build return object.
		if (throwError.toString().equals(""))
		{
			try {
//				conn = ServiceConnection.getConnectionStack15();
				findIt = conn.createStatement();
				rs = findIt.executeQuery(sqlString);			
				
				while (rs.next())
				{
					// load bus object from result set.
					TicklerFunctionDetail dtl = loadFields(rs, requestType, fromVb);
					dtl.setGroup(fromVb.getGroup());
					dtl.setIdKeyValue(fromVb.getIdKeyValue());
					dtl = findTicklerDetail(dtl, conn);
					rtnVector.addElement(dtl);
				}
				
			} catch(Exception e)
			{
				throwError.append(" Error occured executing a sql statement. " + e);
				
			// return connection.
			} finally {
				try {
//					if (conn != null)
//						ServiceConnection.returnConnectionStack15(conn);
					if (rs != null)
						rs.close();
					if (findIt != null)
						findIt.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("findTicklerDependOn(TicklerDetailFunction");
			throwError.append(" grp:" + fromVb.getGroup().trim());
			throwError.append(" fNbr:" + fromVb.getNumber());
			throwError.append(" id:" + fromVb.getIdKeyValue().trim());
			throwError.append("). ");
			throw new Exception(throwError.toString());
		}
		
		return rtnVector;
	}
	
	
	/**
	 * Return a TicklerFunctionDetail object. Use the incoming
	 * TicklerFunctionDetail object for the selection criteria.
	 * * 4/11/12 - TWalton, change to send connection from Public Method
	 * @param TicklerFunctionDetail business object.
	 * @return TicklerFunctionDetail business object.
	 */
	private static TicklerFunctionDetail findTicklerDetail(TicklerFunctionDetail fromVb, Connection conn)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		TicklerFunctionDetail dtl = new TicklerFunctionDetail();
		String sqlString = "";
//		Connection conn = null;
		Statement findIt = null;
		ResultSet rs = null;
		String requestType = "singleEntry";
		
		// verify base class initialization.
		ServiceTickler st = new ServiceTickler();

		// get the sql statement.
		try {
			// set selection criteria.
			Vector parmClass = new Vector();
			parmClass.addElement(fromVb);
			
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error trying to build sql statement" + e);
		}
		
		// get a connection. execute sql, build return object.
		if (throwError.toString().equals(""))
		{
			try {
//				conn = ServiceConnection.getConnectionStack15();
				findIt = conn.createStatement();
				rs = findIt.executeQuery(sqlString);			
				
				if (rs.next())
				{
					// load bus object from result set.
					dtl = loadFields(rs, requestType, fromVb);
				} //else
			//	{
				//	throwError.append(" Error - request not found.");
			//	}
				
			} catch(Exception e)
			{
				throwError.append(" Error occured executing a sql statement. " + e);
				
			// return connection.
			} finally {
				try {
//					if (conn != null)
//						ServiceConnection.returnConnectionStack15(conn);
					if (rs != null)
						rs.close();
					if (findIt != null)
						findIt.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("findTicklerDetail(TicklerDetailFunction");
			throwError.append(" grp:" + fromVb.getGroup().trim());
			throwError.append(" fNbr:" + fromVb.getNumber());
			throwError.append(" id:" + fromVb.getIdKeyValue().trim());
			throwError.append("). ");
			throw new Exception(throwError.toString());
		}
		
		return dtl;
	}
	
	
	
	/**
	 * Return a String date (mm/dd/yyyy). Use the incoming
	 * TicklerFunctionDetail object for the selection criteria.
	 * * 4/11/12 - TWalton, change to send connection from Public Method
	 * @param TicklerFunctionDetail inqVb.
	 * @return String Target Date.
	 */
	private static String getTargetDate(TicklerFunctionDetail fromVb, Connection conn)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		TicklerFunctionDetail dtl = new TicklerFunctionDetail();
		String sqlString = "";
	//	Connection conn = null;
		Statement findIt = null;
		ResultSet rs = null;
		String requestType = "groupPhases";
		int daysToAdd = 0;
		String targetDate = "";
		
		// verify base class initialization.
		ServiceTickler st = new ServiceTickler();

		// get the sql statement.
		try {
			
			// set selection criteria.
			Vector parmClass = new Vector();
			parmClass.addElement(fromVb);
			
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error trying to build sql statement" + e);
		}
		
		// get a connection. execute sql, build return object.
		if (throwError.toString().equals(""))
		{
			try {
//				conn = ServiceConnection.getConnectionStack15();
				findIt = conn.createStatement();
				rs = findIt.executeQuery(sqlString);			
				
				while (rs.next())
				{
					// load bus object from result set.
					daysToAdd = daysToAdd + rs.getInt("GMCDYS");	
				}
				
			} catch(Exception e)
			{
				throwError.append(" Error occured executing a sql statement. " + e);
				
			// return connection.
			} finally {
				try {
//					if (conn != null)
	//					ServiceConnection.returnConnectionStack15(conn);
					if (rs != null)
						rs.close();
					if (findIt != null)
						findIt.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		
		// get current system date.
		String[] dates = new String[10];
		if (fromVb.getEstTargetDate() == null || fromVb.getEstTargetDate().trim().equals(""))
		  dates = SystemDate.getSystemDate();
		else
		{
			DateTime dt = UtilityDateTime.getDateFromMMddyyyyWithSlash(fromVb.getEstTargetDate());
			dates[0] = dt.getTimeFormathhmmss();
			dates[1] = dt.getDateFormatMMddyy();
			dates[2] = dt.getDateFormatMMddyyyy();
			dates[3] = dt.getDateFormatyyyyMMdd();
			dates[4] = dt.getDateFormatMMddyySlash();
			dates[5] = dt.getDateFormatMMddyyyySlash();
			dates[6] = dt.getDayOfWeek();
			dates[7] = dt.getDateFormatyyyyMMddDash();
			dates[8] = dt.getTimeFormathhmmssColon();
			dates[9] = dt.getDateFormatMonthNameddyyyy();
		}
		int days = daysToAdd;
		int weDays = 0;
		
		// allow for weekends.
		if (daysToAdd > 0);
		{
			// test for day of week then subtract till weekend.
			if (dates[6].equals("Sunday"))
			{
				days = days + 1;
			}
			if (dates[6].equals("Monday")&& days > 4)
			{
				weDays = 2;
				days = days - 5;
			}
			if (dates[6].equals("Tuesday")&& days > 3)
			{
				weDays = 2;
				days = days - 4;
			}
			if (dates[6].equals("Wednesday")&& days > 2)
			{
				weDays = 2;
				days = days - 3;
			}
			if (dates[6].equals("Thursday")&& days > 1)
			{
				weDays = 2;
				days = days - 2;
			}
			if (dates[6].equals("Friday")&& days > 0)
			{
				weDays = 2;
				days = days - 1;
			}
			if (dates[6].equals("Saturday"))
			{
				days = days + 2;
			}
		}
		
		// determine additional weekends.
		while(days > 4)
		{
			days = days - 5;
			weDays = weDays + 2;
		}
		
		// add additionjal weekend days to additional days.
		daysToAdd = daysToAdd + weDays;
		
		// Add additional phase days to current date.		
		if (daysToAdd > 0)
		{
			String[] targetDates = CheckDate.changeDate(dates[5], daysToAdd);
			targetDate = targetDates[1];
			
		} else
			targetDate = dates[5];

		
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("getTargetDate(TicklerDetailFunction");
			throwError.append(" grp:" + fromVb.getGroup().trim());
			throwError.append(" fNbr:" + fromVb.getNumber());
			throwError.append(" id:" + fromVb.getIdKeyValue().trim());
			throwError.append("). ");
			throw new Exception(throwError.toString());
		}
		
		return targetDate;
	}
	
	
	
	/**
	 * Reset Target Dates not completed for a specific Item. 
	 * * 01/16/14 - THAILE, change to send connection from Public Method
	 * @param UpdItem inqVb.
	 * @param String New Start Target Date.
	 * @param Connection
	 */
	private static void resetTargetDate(UpdItem fromVb, String startDate, Connection conn)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String sqlString = "";
		String requestType = "";
		Statement findThem = null;
		ResultSet rsFindThem = null;
		Statement updateIt = null;
		try {
			//Verify incoming view bean contains an item number.
			if (fromVb.getItem().trim().equals(""))
				throwError.append("Item Number can not be blank. ");
			
			if(throwError.toString().equals(""))
			{
			
				//Find Tickler entries not completed in sequenced order.
				try {
					requestType = "findIncompleteTicklers";
					Vector parmClass = new Vector();
					parmClass.addElement(fromVb);
					sqlString = buildSqlStatement(requestType, parmClass);
					
					findThem = conn.createStatement();
					rsFindThem = findThem.executeQuery(sqlString);
				} catch(Exception e) {
					throwError.append("Error Finding Ticklers. " + e);
				}
			}
		
			//create an update statement plus to be used in the next loop.
			updateIt = conn.createStatement();
			DateTime latestDate = UtilityDateTime.getDateFromMMddyyyyWithSlash(startDate);
			
			//update each uncompleted Tickler Entry.
			while (rsFindThem.next() && throwError.toString().equals("")) 
			{
				//set add To Days = 0 each pass.
				int leadDays = rsFindThem.getInt("GMBDYS");
				int daysToAdd = leadDays;
				
				
				//add weekend days while over 5
				while(leadDays > 4)
				{
					leadDays = leadDays - 5;
					daysToAdd = daysToAdd + 2;
				}
				
				//modify Days To Add if remaining leadDays fall on or over the weekend.
				if (leadDays > 0)
				{
					DateTime testDt = UtilityDateTime.addDaysToDate(latestDate, leadDays);
					
					if (testDt.getDayOfWeek().equals("Saturday") ||
						testDt.getDayOfWeek().equals("Sunday"))
						daysToAdd = daysToAdd + 2;
					
					if (testDt.getDayOfWeek().equals("Monday") &&
					    leadDays >= 3 )
						daysToAdd = daysToAdd + 2;
				
					if (testDt.getDayOfWeek().equals("Tuesday") &&
						leadDays == 4)
						daysToAdd = daysToAdd + 2;
							
				}
				
				//modify latestDate (used to update new target date)
				if (daysToAdd > 0)
					latestDate = UtilityDateTime.addDaysToDate(latestDate, daysToAdd);
				
				//update the file
				try {
					requestType = "UpdateTicklerDate";
					Vector parmClass = new Vector();
					parmClass.addElement(rsFindThem);
					parmClass.addElement(latestDate.getDateFormatyyyyMMdd());
					sqlString = buildSqlStatement(requestType, parmClass);
					updateIt.executeUpdate(sqlString);
				} catch(Exception e) {
					throwError.append("Error updating Target Date. " + e);
				}
			}
		} catch(Exception e) {
			throwError.append("Error in method. " + e);
		} finally 
		{
			try {
				if (findThem != null)
					findThem.close();
			} catch(Exception e){
			}
			try {
				if (rsFindThem != null)
					rsFindThem.close();
			} catch(Exception e){
			}
			try {
				if (updateIt != null)
					updateIt.close();
			} catch(Exception e){
			}
		}
		
		
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("resetTargetDate(UpdItem, String, conn). ");
			throw new Exception(throwError.toString());
		}
		
	}
	
	
	
	/**
	 * Reset Target Dates not completed for a specific Item 
	 * using incoming date as start date. 
	 * * 01/16/14 - THAILE, change to send connection from Public Method
	 * @param UpdItem inqVb.
	 * @param String New Start Target Date.
	 */
	public static void resetTargetDate(UpdItem fromVb, String startDate)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		
		try {
	
			conn = ServiceConnection.getConnectionStack15();
			
			resetTargetDate(fromVb, startDate, conn);
			
		} catch(Exception e) {
			throwError.append(e);
			
		} finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack15(conn);
				
			} catch(Exception e){
				throwError.append("Error returning connection. " + e);
			}
	  }
		
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("resetTargetDate(UpdItem, String). ");
			throw new Exception(throwError.toString());
		}
		
	}
	
	
	
	/**
	 *  Return a vector of TicklerFunctionDetail business
	 * objects.
	 *  Use the incoming class for selection criteria.
	 *  * 4/11/12 - TWalton, change to send connection from Public Method
	 * @param TicklerFunctionDetail
	 * @return Vector of TicklerFunctionDetail objects.
	 * @throws Exception
	 */
	private static Vector findFunctionList(TicklerFunctionDetail fromVb, Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnVector = new Vector();
		String sqlString = "";
	//	Connection conn = null;
		Statement findThem = null;
		ResultSet rs = null;
		String requestType = "functionList";
		
		// verify base class initialization.
		ServiceTickler st = new ServiceTickler();
		
		
		// get sql statement for KeyValue List.
		if (throwError.toString().equals(""))
		{
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(fromVb);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch (Exception e) {
			throwError.append(" error trying to get sqlString. " + e);
			}
		}
		
		// get a connection. execute sql, build return object.
		if (throwError.toString().equals("")) {
			try {
//				conn = ServiceConnection.getConnectionStack15();
				findThem = conn.createStatement();
				rs = findThem.executeQuery(sqlString);
				
				// use "lastNbr" to verify loadFields execution
				// do to many to one Dependant On entries.
				int lastNbr = 99999999;
				TicklerFunctionDetail fd = new TicklerFunctionDetail();
				
				while (rs.next())
				{
					int currNbr = rs.getInt("GMBNBR");
					if (lastNbr != currNbr)
					{
						if (lastNbr != 99999999)
						{
							rtnVector.addElement(fd);
						}
						lastNbr = currNbr;
						fd = new TicklerFunctionDetail();
						fd = loadFields(rs, requestType, fromVb);
						
						//TODO  testing - need new item in M3
						if (fd.getTargetDate() == null || fd.getTargetDate().equals("0")) {
							fd.setTargetDate(getTargetDate(fd, conn));
						}
					}
					
					TicklerFunctionDetail depdOn = loadFields(rs, "dependantOn", fromVb);
					
					if (depdOn.getNumber() != null)
					{
						Vector vector = fd.getDependantFunctions();
						vector.addElement(depdOn);
						fd.setDependantFunctions(vector);
					}
				}
				
				if (lastNbr != 99999999)
				{
					rtnVector.addElement(fd);
				}
				
				// Load dependant on class values.
				//   review all objects returned.
				for (int x = 0; x < rtnVector.size(); x++)
				{
					 // test to see if returned objects have
					 // required dependant objects.
					 fd = (TicklerFunctionDetail) rtnVector.elementAt(x);
					 Vector vector = fd.getDependantFunctions();
					 
					 for (int y = 0; y < vector.size(); y++)
					 {
					 	TicklerFunctionDetail tfd = (TicklerFunctionDetail) vector.elementAt(y);
					 	tfd = loadDependantOnObject(tfd, rtnVector);
					 	vector.setElementAt(tfd, y); 
					 }
					 fd.setDependantFunctions(vector);
				}
				
			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. " + e);
	
				// return connection.
			} finally {
				try {
//					if (conn != null) 
//						ServiceConnection.returnConnectionStack15(conn);
					if (findThem != null)
						findThem.close();
					if (rs != null)
						rs.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		
		// return data.
	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("findFunctionList(TicklerFunctionDetail). ");
			throw new Exception(throwError.toString());
		}
		return rtnVector;
	}
	
	

	/**
 	* Load Dependant On Detail from incomming vector.
 	* @param Vector
 	* @return
 	* @throws Exception
 	*/
	private static TicklerFunctionDetail loadDependantOnObject(
									TicklerFunctionDetail fd, 
									Vector vector)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		
		for (int z = 0; z < vector.size(); z++)
		{
			TicklerFunctionDetail temp = (TicklerFunctionDetail) vector.elementAt(z);
			if (fd.getNumber().intValue() == temp.getNumber().intValue())
			{
				fd.setCompletionDate(temp.getCompletionDate());
				fd.setCompletionTime(temp.getCompletionTime());
				fd.setCompletionUser(temp.getCompletionUser());
				fd.setDefaultRespPerson(temp.getDefaultRespPerson());
				fd.setDescription(temp.getDescription());
				fd.setEstTargetDate(temp.getTargetDate());
				fd.setFunctionSequence(temp.getFunctionSequence());
				fd.setGroup(temp.getGroup());
				fd.setIdKeyValue(temp.getIdKeyValue());
				fd.setLeadTimeDays(temp.getLeadTimeDays());
				fd.setNumber(temp.getNumber());
				fd.setPhaseName(temp.getPhaseName());
				fd.setPhaseNumberOfDays(temp.getPhaseNumberOfDays());
				fd.setPhaseSequence(temp.getPhaseSequence());
				fd.setProcessDocument(temp.getProcessDocument());
				fd.setReminderText(temp.getReminderText());
				fd.setRespPerson(temp.getRespPerson());
				fd.setStatus(temp.getStatus());
				fd.setTargetDate(temp.getTargetDate());
				fd.setVerifyLink(temp.getVerifyLink());
				z = 999991;
			}
		}
		
		// return data.
	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("loadDependantOnObject(Vector). ");
			throw new Exception(throwError.toString());
		}
		return fd;
	}
	
	
	
	/**
	* Build email body.
	* @param TicklerFunctionDetail, String, StringBuffer
	* @return StringBuffer
	* @throws Exception
	* @deprecated
	*/
	private static StringBuffer addToEmailBody(TicklerFunctionDetail fromVb,
										String requestType,
										StringBuffer emailBody,
										String resourceChanged)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		
		// process via request type.
		if (requestType.equals("findNewItemEmails"))
		{
			try {
				if (emailBody.toString().equals("") ||
					resourceChanged.equals("yes"))
				{
					emailBody.append("<tr><td>&nbsp;</td></tr>");
					emailBody.append("<tr style=\"background-color:#cccc99\">");
					emailBody.append("<td style=\"width:2%\">&nbsp;</td>");
					emailBody.append("<td style=\"font-family:arial; font-size:11pt; color:#006400; font-weight:bold;\" colspan=\"2\">");
					emailBody.append("New Item Number:&nbsp;");
					emailBody.append("</td>");
					emailBody.append("<td style=\"border-bottom: 2px solid #006400; border-left: 2px solid #006400; border-right: 2px solid #006400;\">");
					emailBody.append(HTMLHelpersLinks.routerItem(fromVb.getIdKeyValue(), "none", "font-family:arial; font-size:12pt; color:#006400; font-weight:bold;", ""));
					emailBody.append("</td>");
					emailBody.append("<td style=\"width:2%\">&nbsp;</td>");
					emailBody.append("<td style=\"font-family:arial; font-size:11pt; color:#006400; font-weight:bold; border-bottom: 2px solid #006400; border-left: 2px solid #006400; border-right: 2px solid #006400;\">");
					emailBody.append("&nbsp;");
					try
					{
//					   BeanResource thisResource = ServiceResource.buildResourceReservation(fromVb.getIdKeyValue(), fromVb.getGroup());
//					   if (thisResource.getResourceNewClass().getResourceDescription() != null)
					   	 emailBody.append(fromVb.getIdKeyDescription().trim());
					}
					catch(Exception e)
					{}
					emailBody.append("</td>");
					emailBody.append("<td style=\"width:2%\">&nbsp;</td>");
					emailBody.append("</tr>");
				}
				// Description and Target Date
				emailBody.append("<tr>");
				emailBody.append("<td style=\"width:2%\">&nbsp;</td>");
				emailBody.append("<td>&nbsp;</td>");
				emailBody.append("<td style=\"text-align:right\">");
				emailBody.append("Function:");
				emailBody.append("</td>");
				emailBody.append("<td style=\"border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black;\">");
				emailBody.append(fromVb.getDescription());
				emailBody.append("</td>");
				emailBody.append("<td style=\"width:2%\">&nbsp;</td>");
				emailBody.append("<td style=\"border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black;\">");
				emailBody.append("TargetDate: ");
				emailBody.append(fromVb.getTargetDate());
				emailBody.append("</td>");
				emailBody.append("<td style=\"width:2%\">&nbsp;</td>");
				emailBody.append("</tr>");
				// Reminder Text
				emailBody.append("<tr>");
				emailBody.append("<td style=\"width:2%\">&nbsp;</td>");
				emailBody.append("<td>&nbsp;</td>");
				emailBody.append("<td>&nbsp;</td>");
				emailBody.append("<td>&nbsp;</td>");
				emailBody.append("<td style=\"width:2%\">&nbsp;</td>");
				emailBody.append("<td style=\"border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; width:50%\">");
				emailBody.append(fromVb.getReminderText());
				emailBody.append("</td>");
				emailBody.append("<td style=\"width:2%\">&nbsp;</td>");
				emailBody.append("</tr>");
				
			} catch(Exception e){
				throwError.append("e");
			}
			
		}
		
		// return data.
	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("addToEmailBody(TicklerFunctionDetail,");
			throwError.append("String, StringBuffer). ");
			throw new Exception(throwError.toString());
		}
		
		return emailBody;
	}
	
	
	
	/**
	 * Load class fields from result set.
	 * 
	 */
	
	public static TicklerFunctionDetail loadFields(ResultSet rs, 
											  	   String type,
											  	   TicklerFunctionDetail fromVb)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		TicklerFunctionDetail fd = new TicklerFunctionDetail();

		try{ // sqlStatement("keyValueList")
			
			if (type.equals("singleEntry") ||
				type.equals("functionList")||
				type.equals("findNewItemEmails"))
			{
				fd.setGroup(rs.getString("GMDGRP").trim());
				fd.setNumber(new Integer(rs.getString("GMBNBR").trim() ));
				fd.setDescription(rs.getString("GMBDSC").trim());
				fd.setFunctionSequence(rs.getString("GMBSEQ").trim());
				fd.setPhaseName(rs.getString("GMBPNM").trim());
				fd.setPhaseSequence(rs.getString("GMCSEQ").trim());
				fd.setPhaseNumberOfDays( new Integer(rs.getString("GMCDYS").trim() ));
				fd.setLeadTimeDays(new Integer(rs.getString("GMCDYS").trim() ));
				fd.setReminderText(rs.getString("GMBRMD").trim());
				fd.setProcessDocument(rs.getString("GMBDLK").trim());
				fd.setVerifyLink(rs.getString("GMBULK").trim());
				fd.setDefaultRespPerson(rs.getString("GMBPER").trim());
				fd.setManagerApproval(rs.getString("GMBAPV"));
				if (rs.getString("MMITDS") != null)
				{
				  fd.setIdKeyDescription(rs.getString("MMITDS").trim());
				  fd.setTicklerResponsible(rs.getString("MMRESP").trim());
				} else {
				   fd.setTicklerResponsible("helpdesk@treetop.com");
				}
				
				DateTime dt = UtilityDateTime.getSystemDate();
				// convert date for display.
				if (fromVb.getEstTargetDate() == null || fromVb.getEstTargetDate().trim().equals(""))
				{
				//   String[] dates = SystemDate.getSystemDate();
				//   fd.setEstTargetDate(dates[5]);
				//   [5] mm/dd/yyyy
				   fd.setEstTargetDate(dt.getDateFormatMMddyyyySlash());
				}
				else
				   fd.setEstTargetDate(fromVb.getEstTargetDate());
				
				try
				{
					// Test for existence of Detail record.
					if (rs.getString("GMFUNQ") == null)
					{
						fd.setIdKeyValue(fromVb.getIdKeyValue());
						fd.setRespPerson(rs.getString("GMBPER").trim());
						if (fd.getRespPerson().trim().equals(""))
						{
							try{
								if (rs.getString("MMRESP") != null)
									fd.setRespPerson(rs.getString("MMRESP"));
							}catch(Exception e)
							{}
						}
						fd.setStatus("");
						// Going to load it in from the main page.
						//fd.setTargetDate(getTargetDate(fd));
						fd.setCompletionDate("0");
						fd.setCompletionTime(new Integer("0"));
						fd.setCompletionUser("");
						// if it is foodservice Nick always does the GTIN stuff
						// Changed 12/30/13 - TW
						if (fd.getNumber().toString().equals("300") &&
							(rs.getString("MMRESP") != null &&
							 (rs.getString("MMRESP").equals("LBALDO") ||
							  rs.getString("MMRESP").equals("NMOTE"))))
						{
							fd.setRespPerson("NMOTE");
						}
						
						if (fd.getNumber().toString().equals("10") ||
							fd.getNumber().toString().equals("50"))
						{
							fd.setStatus("complete");
							fd.setCompletionDate(dt.getDateFormatyyyyMMdd());
							fd.setCompletionTime(new Integer(dt.getTimeFormathhmmss()));
							fd.setCompletionUser(fd.getRespPerson());
						}
						fd.setInitialNotification(" ");
		//				addDetail(fd);
					} else{
						fd.setIdKeyValue(rs.getString("GMFUNQ").trim());
						fd.setRespPerson(rs.getString("GMFPER").trim());
						fd.setStatus(rs.getString("GMFSTS").trim());
				
						// convert date for display.
						dt = new DateTime();
						String date = "0";
						//String field = "";
				
						if (rs.getInt("GMFTDT") != 0)
						{
							
							dt = UtilityDateTime.getDateFromyyyyMMdd(rs.getString("GMFTDT"));
							date = dt.getDateFormatMMddyyyySlash();
							//date = field.substring(4,6) + "/"; //mm
							//date = date + field.substring(6,8) + "/"; //dd
							//date = date + field.substring(0,4);
						}
						fd.setTargetDate(date);
				
						// convert date for display.
						date = "0";
						//field = "";
				
						if (rs.getInt("GMFCDT") != 0)
						{	
							dt = UtilityDateTime.getDateFromyyyyMMdd(rs.getString("GMFCDT"));
							date = dt.getDateFormatMMddyyyySlash();
//							date = field.substring(4,6) + "/"; //mm
//							date = date + field.substring(6,8) + "/"; //dd
//							date = date + field.substring(0,4);
						}
						fd.setCompletionDate(date);
						fd.setCompletionTime(new Integer(rs.getString("GMFCTM")));
						fd.setCompletionUser(rs.getString("GMFCUR").trim());
						fd.setInitialNotification(rs.getString("GMFINO"));
					}
				}catch(Exception e)
				{
					System.out.println("Exception: " + e);
				}
			}
			
			if (type.equals("dependantOn") ||
				type.equals("dependantOnFilter"))
			{
				if (rs.getString("GMEDON") != null)
					fd.setNumber(new Integer(rs.getString("GMEDON").trim() ));
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the class from the result set. ");
		}
		
		// return data.
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("loadFields(rs, type:");
			throwError.append(type + "). ");
			throw new Exception(throwError.toString());
		}
		return fd;
	}
	
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		// Test all that you can.
		String stophere = "";		
		Vector vector = null;
		
		try {
			
		//*** test buildTicklerFunctionDetail(TicklerFunctionDetail)
			if ("x".equals("y"))
			{
				TicklerFunctionDetail td = new TicklerFunctionDetail();	
				// test list access.
				td.setGroup("100-CPGItem");
				//td.setNumber(new Integer("10"));
				//td.setIdKeyValue("100001");
				//td = buildTicklerDetail(td);
				//stophere = "x";
				
				//test reminder email (Nag)
	//			if (1 == 1) {
	//				buildEmailReminders(td);
	//				stophere = "x";
	//			}
			}
			
		//*** test the retrieval of Functions in a list.
		//    remove some function detail records to ensure
		//    they are built.
			if (1 == 2)
			{
				TicklerFunctionDetail td = new TicklerFunctionDetail();
				td.setGroup("NewCPGItem");
				td.setIdKeyValue("0100001");
				vector = buildFunctionList(td);
				stophere = "x";	
			}
			
		//*** test the retrieval of Functions in a list
		// 	  update some of the records.
			if (1 == 2)
			{
				TicklerFunctionDetail td = new TicklerFunctionDetail();
				td.setGroup("NewCPGItem");
				td.setIdKeyValue("0100002");
				vector = buildFunctionList(td);
				stophere = "x";
				
				for (int x = 0; x < vector.size(); x++)
				{
					td = (TicklerFunctionDetail) vector.elementAt(x);
					if (x == 1)
						td.setCompletionUser("mememe");
					if (x == 2)
						td.setTargetDate("12/06/2005");
				}
				ServiceTickler.updateDetail(vector);
				stophere = "x";
			}
			
			
		//*** test reminder email (Nag)
//			if (1 == 1)
//			{
//				TicklerFunctionDetail td = new TicklerFunctionDetail();
				//td.setGroup("100-CPGItem");
//				buildEmailReminders(td);
//				stophere = "x";
//			}
			
			
		//*** test the delete of rogue entries in the
		// Tickler Master file.
			if (1 == 2)
			{
				deleteRogueEntries("");
				stophere = "x";
			}

				
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	

	/**
	 * Add a Tickler Detail file entry on the Enterprise data base.
	 *  4/11/12 - TWalton -- Change to Private
	 *            TWalton, change to send connection from Public Method 
	 * @param TicklerFunctionDetail
	 * @return 
	 * @throws Exception
	 */
	private static void addDetail(TicklerFunctionDetail addVb, Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String requestType = "";
		String sqlAddEntry = "";
//		Connection conn = null;
		Statement addIt = null;
		
		// verify base class initialization.
		ServiceTickler st = new ServiceTickler();
		
		// edit incoming data prior to add.	
		//throwError.append(editData(addVb));

		// if primary edits pass continue update process.
		if (throwError.toString().equals(""))
		{	
			try {
				// Pass along the incoming view bean information. 
				Vector parmClass = new Vector();
				parmClass.addElement(addVb);
				
				// get the sql statement.
				sqlAddEntry = buildSqlStatement("addDetail", parmClass);
			} catch(Exception e) {
				throwError.append(e);
			}
		}
		
		// get a connection, execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
				// 1/25/12 TWalton - Change to use ServiceConnection
				//conn = ConnectionStack5.getConnection();	
//				conn = ServiceConnection.getConnectionStack15();
				addIt = conn.createStatement();
				addIt.executeUpdate(sqlAddEntry);
				//addIt.close();
			} catch(Exception e)
			{
				throwError.append("error occured executing a ");
				throwError.append("add sql statement. " + e);
				
			// return connection.
			} finally {
				if (addIt != null)
				{
					try {
						addIt.close();
					} catch(Exception el){
						el.printStackTrace();
					}
				}
//				if (conn != null)
//				{
//					try {
//						ServiceConnection.returnConnectionStack15(conn);
//					} catch(Exception el){
//						el.printStackTrace();
//					}
//				}
			}
		}
		
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("addDetail(TicklerFunctionDetail)");
			throw new Exception(throwError.toString());
		}
	}



	/**
	* Send Tickler Function Email.
	* @param Vector
	* @deprecated -- moved to the Timer Jobs
	* @return
	* @throws Exception
	*/
	private static void sendEmail(String toProfile,
								  String fromProfile,
								  String requestType,
								  StringBuffer emailBody)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		
		// process via request type.
		if (requestType.equals("findNewItemEmails"))
		{
			try {
				String[] to = new String[1];
							
				// get To email address.
				try {
					UserFile userName = new UserFile(toProfile);
//					System.out.println(userName.getUserName());
//					userName = new UserFile("TWALTO");
					
					to[0] = userName.getUserEmail();
				} catch (Exception e){
					throwError.append("Error obtaining email from user profile. " + e);
				}
				
				String[] cc = new String[0];
				String[] bcc = new String[0];
				// 8/18/11 tw - changed from Helpdesk to Ronnie Butler 
//				 get From email address.
				String from = "helpdesk@treetop.com";
				try {
					UserFile userName = new UserFile(fromProfile);
					from = userName.getUserEmail();
				} catch (Exception e){
					throwError.append("Error obtaining email from user profile. " + e);
				}
				//from = "Teri.Walton@treetop.com";
				String subject = " New Item Outstanding Function Reminder";
				// BODY
				StringBuffer wrapEmailBody = new StringBuffer();
				wrapEmailBody.append("<table cellspacing=\"0\" style=\"width:100%\">");
				wrapEmailBody.append("<tr>");
				wrapEmailBody.append("<td></td>");
				wrapEmailBody.append("<td colspan=\"5\">");
				wrapEmailBody.append("Hi, <br>");
				wrapEmailBody.append("This email is a reminder of the new item functions awaiting your attention, ");
				wrapEmailBody.append("all prerequisite tasks are finished:");
				wrapEmailBody.append("</td>");
				wrapEmailBody.append("</tr>");
				wrapEmailBody.append(emailBody.toString());
				wrapEmailBody.append("<tr>");
				wrapEmailBody.append("<td></td>");
				wrapEmailBody.append("<td colspan=\"5\">");
				wrapEmailBody.append("<br>");
				wrapEmailBody.append("Once a function has been finished, indicate completion on the New Item Display. <br>");
				wrapEmailBody.append("All uncompleted tasks will continue to be emailed to you daily until they have been finished.<br>");
				wrapEmailBody.append("<b>Thanks,<br>");
				wrapEmailBody.append("New Item Team</b>");
				wrapEmailBody.append("</td>");
				wrapEmailBody.append("</tr>");
				wrapEmailBody.append("</table>");
				
				String body = wrapEmailBody.toString();
				Email email = new Email();
				email.sendEmail(to, cc, bcc, from, subject, body);
			} catch(Exception e){
				throwError.append("e");
			}
			
		}
		
		// return data.
	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("sendEmail(to" + toProfile + " ");
			throwError.append("from:" + fromProfile + " ");
			throwError.append("type:" + requestType + " ");
			throwError.append("body:" + emailBody + "). ");
			throw new Exception(throwError.toString());
		}
	}
	
	
	
	/**
	 * Execute a request to send reminder emails.
	 * List any problems in the system log.
	 * @param TicklerFunctionDetail.
	 * @return void 
	 * @deprecated
	 */
	
	public static void buildEmailReminders(TicklerFunctionDetail fromVb)
		 
	{
		StringBuffer errorLog = new StringBuffer();
		
		try {
			// process by Group Type.
	//		if (!fromVb.getGroup().trim().equals(""))
	//		{
				findNewItemReminders(fromVb);
	//		}
		} catch (Exception e){
			errorLog.append("error @ com.treetop.services.");
			errorLog.append("buildEmailReminders(");
			errorLog.append("TicklerFunctionDetail) - ");
			errorLog.append("group:" + fromVb.getGroup() + ". " + e);
			System.out.println(errorLog.toString());
		}
	}
	
	/**
	 * Create and send reminder emails by user for any 
	 * pending functions.
	 * * 4/11/12 - TWalton, change to send connection from Public Method
	 * @deprecated
	 * @param TicklerFunctionDetail
	 * @throws Exception
	 */
	private static void findNewItemReminders(TicklerFunctionDetail fromVb)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String requestType = "findNewItemEmails";
		String sqlEmails = ""; 
		Connection conn = null;
		Statement findThem = null;
		ResultSet rs = null;
		
		// verify base class initialization.
		ServiceTickler st = new ServiceTickler();

		
		// edit incoming data prior to add.	
		//throwError.append(editData(addVb));

		// if primary edits pass contiue update process.
		if (throwError.toString().equals(""))
		{
			TicklerFunctionDetail newVb = new TicklerFunctionDetail();
				
			try {
				// 1/25/12 TWalton - Change to use ServiceConnection
				//conn = ConnectionStack5.getConnection();	
				conn = ServiceConnection.getConnectionStack15();
					
				// Pass along the incoming view bean information. 
				Vector parmClass = new Vector();
				parmClass.addElement(fromVb);
				
				// get the sql statement.
				sqlEmails = buildSqlStatement(requestType, parmClass);
					
				findThem = conn.createStatement();
				rs = findThem.executeQuery(sqlEmails);
				
				// send emails from result set after validation.
				// method variables used to compare data and track logic.
				
				String respUser = ""; // email to
				String fromUser = ""; // email from
				String lastIdKeyValue = "";  // unique key
				int lastFunctionNumber = 0;  // mult per key
				StringBuffer emailBody = new StringBuffer();  // built/added to if condictions pass.
				String firstPass = "yes";
				String addToEmail = "yes";
				String idKeyValueChanged = "no";
				DateTime todayDT = UtilityDateTime.getSystemDate();
				
				while (rs.next())
				{
					// load business object from result set.
					newVb = loadFields(rs, requestType, newVb);
					// set save fields on first read only.
					if (firstPass.equals("yes"))
					{
						respUser = newVb.getRespPerson();
						fromUser = newVb.getTicklerResponsible();
						lastIdKeyValue = newVb.getIdKeyValue();
						firstPass = "no";
					}
					
					//System.out.println(respUser);
					//System.out.println(newVb.getRespPerson());
					//if (newVb.getRespPerson().trim().equals("CHARGR"))
						//System.out.println("StOP");
					// if not first attempt and user has changed 
					// see if any email for this person should be sent.
					if (!respUser.equals(newVb.getRespPerson()))
					{
						// send a email for this person
					  if (!emailBody.toString().equals("") )
					  {
						sendEmail(respUser, fromUser, requestType, emailBody);
					  }
						// set save fields.
					   fromUser = newVb.getTicklerResponsible();
					   respUser = newVb.getRespPerson();
					   emailBody = new StringBuffer();
					}
						// assume that everything will be emailed, to Start
					  addToEmail = "yes";
//					  Test current date against target date.
						// do not send if user already notified and not overdue.
					// if OVERDUE, send out the email no matter what
					// which means if Today is LessThan or Equal to the Target.. make the other decisions
					  DateTime targetDT = UtilityDateTime.getDateFromMMddyyyyWithSlash(newVb.getTargetDate());
					  if (new Integer(todayDT.getDateFormatyyyyMMdd()).intValue() <= 
					    new Integer(targetDT.getDateFormatyyyyMMdd()).intValue())
					  {
						// Get Dependant Functions, thate have NOT been done
//						newVb.setDependantFunctions(findTicklerDependOn(newVb, "filter"));

//						 Check dependant Functions -- Add to email if All are complete
						if (newVb.getDependantFunctions().size() > 0)
						  	addToEmail = "no";
						else
						{ // Update for the initial notification if necessary
							if (newVb.getInitialNotification().trim().equals(""))
							{
								newVb.setInitialNotification("Y");
								Vector updateVector = new Vector();
								updateVector.addElement(newVb);
								updateDetail(updateVector);
							}
						 }
					  }
						
					// track change of IdKeyValue.
					if (!lastIdKeyValue.equals(newVb.getIdKeyValue()))
					{
						idKeyValueChanged = "yes";
					}
						
					// add to email body if dependent function are complete.
					if (idKeyValueChanged.equals("yes") || 
						(addToEmail.equals("yes") &&
						 lastFunctionNumber != newVb.getNumber().intValue()))
					{
						addToEmailBody(newVb, requestType, 
									   emailBody, idKeyValueChanged);
						lastIdKeyValue = newVb.getIdKeyValue();
						idKeyValueChanged = "no";
					}
					lastFunctionNumber = newVb.getNumber().intValue();
				}// end of the WHILE	
				
				// test last email to send.
				if (!emailBody.toString().equals("") )
 				{
					sendEmail(respUser, fromUser, requestType, emailBody);
				}
				
			} catch(Exception e) {
				throwError.append("Group:" + fromVb.getGroup() + ". ");
				throwError.append(e);
			} finally {
				try {
					if (conn != null)
						ServiceConnection.returnConnectionStack15(conn);
					if (findThem != null)
						findThem.close();
					if (rs != null)
						rs.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		
		
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("sendNewItemReminders(TicklerFunctionDetail)" + throwError.toString());
			throw new Exception(throwError.toString());
		}
	}	

	
		
	/**
	 * Update Tickler Detail file entries on the Enterprise data base.
	 * @param Vector of TicklerFunctionDetail
	 * @return 
	 * @throws Exception
	 */
	public static void updateDetail(Vector list)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String requestType = "";
		String sqlExists = "";
		String sqlUpdate = "";
		Connection conn = null;
		Statement findIt = null;
		Statement updateIt = null;
		ResultSet rs = null;
		String updatedOne  = "";
		
		// verify base class initialization.
		ServiceTickler st = new ServiceTickler();
		
		// edit incoming data prior to add.	
		//throwError.append(editData(addVb));

		// if primary edits pass contiue update process.
		if (throwError.toString().equals("") && list != null && list.size() > 0)
		{
			TicklerFunctionDetail newVb = new TicklerFunctionDetail();
			try {
				// 1/25/12 TWalton - Change to use ServiceConnection
				//conn = ConnectionStack5.getConnection();	
				conn = ServiceConnection.getConnectionStack15();
				
				// cycle through incoming vector
				String hasFunction10 = "N";
				
				findIt = conn.createStatement();
				updateIt = conn.createStatement();
				
				for (int x = 0; x < list.size(); x++)
				{
					newVb = (TicklerFunctionDetail) list.elementAt(x);
					
					// Pass along the incoming view bean information. 
					Vector parmClass = new Vector();
					parmClass.addElement(newVb);
				
					// get the sql statement.
					requestType = "singleEntry";
					sqlExists = buildSqlStatement(requestType, parmClass);
					
					
					rs = findIt.executeQuery(sqlExists);			
					if (rs.next())
					{
						// load business object from result set.
						TicklerFunctionDetail currVb = loadFields(rs, requestType, newVb);
						newVb.setTicklerResponsible(currVb.getTicklerResponsible());
						if ((newVb.getIdKeyDescription() == null || newVb.getIdKeyDescription().trim().equals(""))
								&& !currVb.getIdKeyDescription().trim().equals(""))
							newVb.setIdKeyDescription(currVb.getIdKeyDescription());
						
						// If the newVb business object (incoming parm) initialnotification 
						// is null, set incoming business object value for initialNotification
						// to the current file value.
						if (newVb.getInitialNotification() == null )
						{
							newVb.setInitialNotification(currVb.getInitialNotification());
						}
						
						if (!newVb.getRespPerson().trim().equals(currVb.getRespPerson().trim()) ||
							!newVb.getStatus().trim().equals(currVb.getStatus().trim()) ||
							!newVb.getTargetDate().trim().equals(currVb.getTargetDate().trim()) ||
							!newVb.getCompletionDate().trim().equals(currVb.getCompletionDate().trim()) ||
							newVb.getCompletionTime().intValue() != currVb.getCompletionTime().intValue() ||
							!newVb.getCompletionUser().trim().equals(currVb.getCompletionUser().trim()) ||
							!newVb.getInitialNotification().equals(currVb.getInitialNotification()) )
						{
							// test here for InitialNotifcation change. If 
							// so only change that field leave all other fields
							// alone. Skip the next few changes?
							if (!newVb.getInitialNotification().equals(currVb.getInitialNotification()) )
							{
								// For now if Initial Notification changes then do
								// not update system date or time.
							} else
							{
								// test for completion request.
								if (!newVb.getCompletionUser().trim().equals("") &&
									newVb.getCompletionDate().trim().equals("") ||
									!newVb.getCompletionUser().trim().equals("") &&
									newVb.getCompletionDate().trim().equals("0"))
								{
								// get current system date and time.
									String[] sysDates = SystemDate.getSystemDate();
									newVb.setCompletionDate(sysDates[5]);
									newVb.setCompletionTime(new Integer(sysDates[0]));
								}
							}
							
							parmClass.setElementAt(newVb, 0);
							sqlUpdate = buildSqlStatement("updateSingle", parmClass);
							
							updateIt.executeUpdate(sqlUpdate);
							updatedOne = "Y";
						}
					}
				}
				
				if (!updatedOne.trim().equals(""))
				{
				   //----------------------------------------------------------
				   // Go check to see if all tasks are completed
				   // if YES, then email the Responsible person that it is completed.
				   //----------------------------------------------------------
				   emailComplete(newVb, conn);
				}
				
			} catch(Exception e) {
				throwError.append("(Group:" + newVb.getGroup() + ", ");
				throwError.append("Funct#:" + newVb.getNumber() + ", ");
				throwError.append("IDValue:" + newVb.getIdKeyValue() + ").");
				throwError.append(e);
			} finally {
				try {
					if (conn != null)
						ServiceConnection.returnConnectionStack15(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
				
				try { findIt.close(); } catch (Exception e) {}
				try { updateIt.close(); } catch (Exception e) {}
				try { rs.close(); } catch (Exception e) {}
			}
		
		}
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("updateDetail(Vector)");
			throw new Exception(throwError.toString());
		}
	}
	/**
	 * Update Tickler Detail Complete All Entries for this Group and ID
	 * @param Vector of TicklerFunctionDetail
	 * @return 
	 * @throws Exception
	 */
	public static void updateCompleteAll(String group, String uniqueID)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		Statement updateIt = null;
		
		// verify base class initialization.
		ServiceTickler st = new ServiceTickler();
	
		// if primary edits pass continue update process.
		if (throwError.toString().equals(""))
		{
			try {
				// 1/25/12 TWalton - Change to use ServiceConnection
				//conn = ConnectionStack5.getConnection();	
				conn = ServiceConnection.getConnectionStack15();
				
				if (group.equals("") && !uniqueID.equals(""))
				{
					String sqlString = "";
					String requestType = "completeItemEmails";
					Vector parmClass = new Vector();
					parmClass.addElement(uniqueID);
					sqlString = buildSqlStatement(requestType, parmClass);
					updateIt = conn.createStatement();
					updateIt.executeUpdate(sqlString);
				} else
				{
					Vector parmClass = new Vector();
					parmClass.addElement(group);
					parmClass.addElement(uniqueID);
					updateIt = conn.createStatement();
					updateIt.executeUpdate(buildSqlStatement("updateAll", parmClass));
				}
			} catch(Exception e) {
				throwError.append("(Group:" + group + ", ");
				throwError.append("IDValue:" + uniqueID + ").");
				throwError.append(e);
			} finally {
				try {
					if (conn != null)
						ServiceConnection.returnConnectionStack15(conn);
					if (updateIt != null)
						updateIt.close();
				} catch(Exception el){
					el.printStackTrace();
				}
		  }
		}
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("updateCompleteAll(String, String)");
			throw new Exception(throwError.toString());
		}
	}


	/**
	 * Add All of the Tickler Detail information for a new item GMPFMSTR file.
	 *    Will get the needed information and use the addDetail to populate it.
	 *   4/11/12 - TWalton, change to send connection from into Private Methods Building here  
	 *    
	 * @param UpdFunctionDetail
	 * @return 
	 * @throws Exception
	 */
	public static void addDetailAll(UpdFunctionDetail ufd)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		
		Connection conn = null; 
		try {
			conn = ServiceConnection.getConnectionStack15();
			
			// Retrieve Current Tickler List -- no GMPFMSTR information
			TicklerFunctionDetail fd = new TicklerFunctionDetail();
			fd.setGroup(ufd.getGroup());
			fd.setIdKeyValue(ufd.getKeyValue());
			fd.setEstTargetDate(ufd.getEstTargetDate().trim());
			// First Delete the current tickler file details that are out there.
			try{
			   deleteDetail(fd, conn);	
			}catch(Exception e)
			{
				System.out.println("Exception when Deleting Functions: " + e);
				throwError.append("Exception when Deleting Functions: " + e);
			}
			//Vector listOfFunctions = ServiceTickler.buildFunctionList(fd); -- Change to send in the Conn
			Vector listOfFunctions = ServiceTickler.findFunctionList(fd, conn);
		
			if (listOfFunctions.size() > 0)
			{
				for (int x = 0; x < listOfFunctions.size(); x++)
				{
					try
					{
						TicklerFunctionDetail tfd = (TicklerFunctionDetail) listOfFunctions.elementAt(x);
						addDetail(tfd, conn);
					}catch(Exception e)
					{
						System.out.println("Exception when Adding Function: " + e);
						throwError.append("Exception when Adding Function: " + e);
					}
				}
			}
			
		} catch(Exception e)
		{
			throwError.append("Exception found when finding list of Functions: " + e) ;
		// return connection.
		} finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack15(conn);
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("Tickler.");
			throwError.append("addDetailAll(");
			throwError.append("TicklerFunctionDetail)." );
			throw new Exception(throwError.toString());
		}
	}

	/**
	 * Delete Records from the Tickler Detail file entry on the Enterprise data base.
	 * 4/11/12 - TWalton, change to send connection from Public Method
	 * @param TicklerFunctionDetail
	 * @return 
	 * @throws Exception
	 */
	private static void deleteDetail(TicklerFunctionDetail addVb, Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		if (!addVb.getGroup().trim().equals("") &&
			!addVb.getIdKeyValue().trim().equals(""))
		{
		//	Connection conn = null;
			Statement deleteIt = null;
			String sql = "";
			
			try {
					// Pass along the incoming view bean information. 
				// 1/25/12 TWalton - Change to use ServiceConnection
				//conn = ConnectionStack5.getConnection();	
			//	conn = ServiceConnection.getConnectionStack15();
				
				Vector parmClass = new Vector();
				parmClass.addElement(addVb);
					
				// get the sql statement.
				sql = buildSqlStatement("deleteDetail", parmClass);
				deleteIt = conn.createStatement();
 	 			deleteIt.executeUpdate(sql);
 	 			//deleteIt.close();
			} catch(Exception e) {
				throwError.append("ServiceTickler:deleteDetail: Error when creating/running SQL statement: " + e);
			}finally {
			   if (deleteIt != null)
			   {
				   try {
					  deleteIt.close();
					} catch(Exception el){
						el.printStackTrace();
					}
				}
//			   if (conn != null)
//			   {
//				   try {
//					  ServiceConnection.returnConnectionStack15(conn);
//					} catch(Exception el){
//						el.printStackTrace();
//					}
//				}
			}
			
		}
		
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTickler.");
			throwError.append("addDetail(TicklerFunctionDetail)");
			throw new Exception(throwError.toString());
		}
	}


	/**
	 * Check to see if any other tasks need to be completed for this item
	 * if NOT then email the responsible person that this item's setup is complete 
	 * * 4/11/12 - TWalton, change to send connection from Public Method
	 * @param TicklerFunctionDetail
	 * @throws Exception
	 */
	 private static void emailComplete(TicklerFunctionDetail fromVb, Connection conn)
	 {
		 String requestType = "allComplete";	
		 String sqlStatement = "";
//		 Connection conn = null;
		 Statement findThem = null;
		 ResultSet rs = null;
		 
//		 verify base class initialization.
		 ServiceTickler st = new ServiceTickler();	
			
		 try {
			// 1/25/12 TWalton - Change to use ServiceConnection
				//conn = ConnectionStack5.getConnection();	
//				conn = ServiceConnection.getConnectionStack15();
		 	
//		  Pass along the incoming view bean information. 
			Vector parmClass = new Vector();
			parmClass.addElement(fromVb);
			
			// get the sql statement.
			sqlStatement = buildSqlStatement(requestType, parmClass);
				
			findThem = conn.createStatement();
			rs = findThem.executeQuery(sqlStatement);	 	
			if (rs.next())
			{
				// then there is at least one more task
			}else{
				//--------Email the Responsible Person that all tasks are complete
			
				String[] to = new String[1];
				String from = "helpdesk@treetop.com";
				// get email address.
				try {
					UserFile userName = new UserFile(fromVb.getTicklerResponsible().trim());
//					System.out.println(fromVb.getRespPerson());
//					System.out.println("<br> ticklerResp:" + fromVb.getTicklerResponsible());
//					UserFile userName = new UserFile("TWALTO");
					to[0] = userName.getUserEmail();
					from = userName.getUserEmail();
				} catch (Exception e){
					
				}
				
				String[] cc = new String[0];
				String[] bcc = new String[0];

				String subject = " Item " + fromVb.getIdKeyValue().trim() + " Has Been Completed";
				// BODY
				StringBuffer wrapEmailBody = new StringBuffer();
				wrapEmailBody.append("<table cellspacing=\"0\" style=\"width:100%\">");
				wrapEmailBody.append("<tr>");
				wrapEmailBody.append("<td></td>");
				wrapEmailBody.append("<td colspan=\"5\">");
				wrapEmailBody.append("Hi, <br>");
				wrapEmailBody.append("For Item " + fromVb.getIdKeyValue().trim() + " " + fromVb.getIdKeyDescription().trim());
				wrapEmailBody.append(" all setup Tasks have been completed.");
				wrapEmailBody.append("</td>");
				wrapEmailBody.append("</tr>");
				wrapEmailBody.append("<tr>");
				wrapEmailBody.append("<td></td>");
				wrapEmailBody.append("<td colspan=\"5\">");
				wrapEmailBody.append("<b>Thanks,<br>");
				wrapEmailBody.append("New Item Team</b>");
				wrapEmailBody.append("</td>");
				wrapEmailBody.append("</tr>");
				wrapEmailBody.append("</table>");
				
				String body = wrapEmailBody.toString();
				Email email = new Email();
				email.sendEmail(to, cc, bcc, from, subject, body);
				
				
			} // end of the ELSE -- No records returned in the SQL statement
			
	 } catch(Exception e) {
				//throwError.append("Group:" + fromVb.getGroup() + ". ");
				//throwError.append(e);
		} finally {
			try {
//				if (conn != null)
//					ServiceConnection.returnConnectionStack15(conn);
				if (findThem != null)
					findThem.close();
				if (rs != null)
					rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}			
	}
		
		
		
		/**
		 * Are All Tickler Emails complete for a specific Item. 
		 * * 01/20/14 - THAILE
		 * @param UpdItem inqVb.
		 * @param String New Start Target Date.
		 * @param Connection
		 */
		private static void areEmailsComplete(UpdItem fromVb, Connection conn)
			throws Exception 
		{
			StringBuffer throwError = new StringBuffer();
			fromVb.setAreEmailsComplete("Y");
			String sqlString = "";
			String requestType = "";
			Statement findThem = null;
			ResultSet rsFindThem = null;
			
			try {
				//Verify incoming view bean contains an item number.
				if (!fromVb.getItem().trim().equals(""))
				{
					//Find Tickler entries not completed in sequenced order.
					try {
						requestType = "findIncompleteTicklers";
						Vector parmClass = new Vector();
						parmClass.addElement(fromVb);
						sqlString = buildSqlStatement(requestType, parmClass);
						
						findThem = conn.createStatement();
						rsFindThem = findThem.executeQuery(sqlString);
					} catch(Exception e) {
						throwError.append("Error Finding Ticklers. " + e);
					}			
				
					//See if any incomplete emails are found.
					if (rsFindThem.next() && throwError.toString().equals("")) 
					{
						//set class value to "N".
						fromVb.setAreEmailsComplete("N");
					}
				}
				
			} catch(Exception e) {
				throwError.append("Error in method. " + e);
			} finally 
			{
				try {
					if (findThem != null)
						findThem.close();
				} catch(Exception e){
				}
				try {
					if (rsFindThem != null)
						rsFindThem.close();
				} catch(Exception e){
				}
				
			}
			
			
			// return data.	
			if (!throwError.toString().equals("")) {
				throwError.append("Error at com.treetop.services.");
				throwError.append("ServiceTickler.");
				throwError.append("areEmailsComplete(UpdItem, conn). ");
				throw new Exception(throwError.toString());
			}
			
		}
		
		
		
		/**
		 * Are All Tickler Emails complete for a specific Item. 
		 * * 01/20/14 - THAILE
		 * @param UpdItem inqVb.
		 */
		public static void areEmailsComplete(UpdItem fromVb)
			throws Exception 
		{
			StringBuffer throwError = new StringBuffer();
			Connection conn = null;
			
			try {
		
				conn = ServiceConnection.getConnectionStack15();
				
				areEmailsComplete(fromVb, conn);
				
			} catch(Exception e) {
				throwError.append(e);
				
			} finally {
				try {
					if (conn != null)
						ServiceConnection.returnConnectionStack15(conn);
					
				} catch(Exception e){
					throwError.append("Error returning connection. " + e);
				}
		  }
			
			// return data.	
			if (!throwError.toString().equals("")) {
				throwError.append("Error at com.treetop.services.");
				throwError.append("ServiceTickler.");
				throwError.append("areEmailsComplete(UpdItem). ");
				throw new Exception(throwError.toString());
			}
			
		}
	 
	 
}
