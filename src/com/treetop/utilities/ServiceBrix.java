/*
 * Created on October 21, 2008
 *
 */
package com.treetop.utilities;

import java.sql.*;
import java.util.*;
import java.math.*;

import com.treetop.businessobjects.*;
import com.treetop.services.BaseService;
import com.treetop.utilities.ConnectionStack3;

/**
 * @author twalto .
 *
 * Brix Table - dbprd.brixTable
 */
public class ServiceBrix extends BaseService {

	public static final String library = "M3DJDPRD";
	public static final String ttlib = "DBPRD";
	
	/**
	 * 
	 */
	public ServiceBrix() {
		super();
	}
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		try
		{
			if (1 ==1 )
			{
	//			Vector v = new Vector();
	//			v = dropDownBinType(v);
	//			String x = "stop here";
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Use to Validate a Sent in Brix Table
	 *    // Check in file BRIXTABLE
	 */
	public static Brix findBrix(String brixValue)
	{
		Connection conn = null; // New Lawson Box - Lawson Database
		Statement findIt = null;
		ResultSet rs = null;
		Brix returnBrix = new Brix();
		try { 
			if (brixValue == null || brixValue.trim().equals(""))
			   returnBrix.setErrorMessage("Must send in a Brix Value");
			if (returnBrix.getErrorMessage().toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(brixValue);
					
					conn = ConnectionStack3.getConnection();	
					findIt = conn.createStatement();
					rs = findIt.executeQuery(buildSqlStatement("findBrix", parmClass));
					
					if (rs.next())
					{
						// Load up the Brix Business Object for Return
						returnBrix = loadFieldsBrix("findBrix", rs);
						
					} else {
						returnBrix.setErrorMessage("Brix " + brixValue + " cannot be found, please enter another value.");
					}
				} catch (Exception e) {
					System.out.println("Error @ com.treetop.services.");
					System.out.println("ServiceBrix.getBrix(String brixValue)");
					System.out.println(" Problem with SQL and Finding the Brix: " + brixValue);
					System.out.println("error occured executing a sql statement. " + e);
				}
			}	
		} catch(Exception e)
		{
			System.out.println("Error @ com.treetop.services.");
			System.out.println("ServiceBrix.getBrix(String brixValue)");
			System.out.println(" Problem Finding the Brix: " + brixValue);
		// return connection.
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("Error closing result set - ServiceBrix.findBrix. " + e);
			}
			try {
				if (findIt != null)
					findIt.close();
			} catch (Exception e) {
				System.out.println("Error closing Statement - ServiceBrix.findBrix. " + e);
			}
			try {
				if (conn != null)
					ConnectionStack3.returnConnection(conn);
			} catch (Exception e) {
				System.out.println("Error closing a connection for Stack 3-- ServiceBrix.findBrix: " + e);
				System.out.println("Error closing connection - ServiceBrix.findBrix. " + e);
			}
		}		
		return returnBrix;
	}	
	
	

	/**
 * Build an sql statement.
 * @param String request type
 * @param Vector request class
 * @return sql string
 * @throws Exception
 */
	
private static String buildSqlStatement(String inRequestType,
								 	    Vector requestClass)
{
	StringBuffer sqlString = new StringBuffer();
		
	try { 
	//-------------------------------------------------------	
	// Find 1 Record
	//--------------------------------------------------------
		if (inRequestType.equals("findBrix"))
		{
			sqlString.append("SELECT GNBBRX, GNBFSG  ");
			sqlString.append("FROM " + ttlib + ".BRIXFILE ");
			sqlString.append(" WHERE GNBBRX = " + (String) requestClass.elementAt(0)  + " ");
		}
	} catch (Exception e) {
		System.out.println(" Error building sql statementfor request type " + inRequestType + ". " + e);
	}
	return sqlString.toString();
}

	/**
	 * Load class fields from result set.
	 */
	public static Brix loadFieldsBrix(String requestType,
						          	  ResultSet rs)
	{
		Brix returnValue = new Brix();
		try{ 
		  if (requestType.equals("findBrix"))
		  {
			 returnValue.setBrix(rs.getString("GNBBRX"));
			 returnValue.setPoundsFSperGallon(rs.getString("GNBFSG"));
		  }
		} catch(Exception e)
		{
			System.out.println(" Problem loading the Brix class ");
			returnValue.setErrorMessage("Problem when loading the brix fields " + e);
		}
		return returnValue;
	}
}

