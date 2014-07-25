/*
 * Created on November 11, 2008
 * Author:  Teri Walton
 * Data files accessed.
 */
package com.treetop.services;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import com.treetop.viewbeans.*;
import com.treetop.businessobjects.*;
import com.treetop.utilities.ConnectionStack;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.html.*;

/**
 * @author twalton
 *
 * Service to access Movex(M3) Supplier Information.
 */
public class ServiceSupplier extends BaseService {
	
	public static final String library = "M3DJDPRD";
	public static final String ttlib = "DBPRD";
	
	/**
	 * 
	 */
	public ServiceSupplier() {
		super();
	}
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		try
		{
			String stopHere = "Frist";
			
			if (1 == 0) {
				Vector<DropDownSingle> v = new Vector<DropDownSingle>(); 
				v = getDropDownSinglePeachSuppliers("PRD");
				
				stopHere = "at the end";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
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
	
		if (inRequestType.equals("verifySupplier"))
		{
			// cast the incoming parameter class.
			String supplierNumber = (String) requestClass.elementAt(0);
			
			// build the sql statement.
			sqlString.append("SELECT  IDSUNO  ");
			sqlString.append("FROM " + library + ".CIDMAS ");
			sqlString.append(" WHERE IDSUNO = '" + supplierNumber + "' ");
		}
		
		if (inRequestType.equals("verifySupplierIdLevel"))
		{
			// cast the incoming parameter class.
			CommonRequestBean commonRequest = (CommonRequestBean) requestClass.elementAt(0);
			String libraryM3  = GeneralUtility.getLibrary(commonRequest.getEnvironment().trim());
			
			// build the sql statement.
			sqlString.append("SELECT  IDSUNO  ");
			sqlString.append("FROM " + libraryM3 + ".CIDMAS ");
			sqlString.append("WHERE IDCONO = " + commonRequest.getCompanyNumber().trim() + " ");
			sqlString.append("AND IDSUNO = '" + commonRequest.getIdLevel1().trim() + "' ");
		}
		
		if (inRequestType.equals("peachSupplierList"))
		{
			// cast the incoming parameter class.
			
			String libraryM3  = GeneralUtility.getLibrary(environment);
			
			// build the sql statement.
			sqlString.append("SELECT IDSUNO, IDSUNM ");
			sqlString.append("FROM " + libraryM3 + ".CIDMAS ");
			sqlString.append("WHERE IDSUNM LIKE 'CA CANNING PC ASSOC-CCPA%' " );
			sqlString.append("ORDER BY IDSUNM ");
		}
		
		
	
	} catch (Exception e) {
			throwError.append(" Error building sql statement for request type " + inRequestType + ". " + e);
	}
	// return data.	
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceSupplier.");
		throwError.append("buildSqlStatement(String,String,Vector)");
		throw new Exception(throwError.toString());
	}
	return sqlString.toString();
 }

	/**
	 * Load class fields from result set.
	 */
	public static Supplier loadFieldsSupplier(String requestType,
											  ResultSet rs)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Supplier returnValue = new Supplier();
		try{ 
			if (requestType.equals("basic"))
			{
				if (rs.getString("IDSUNO") != null)
				   returnValue.setSupplierNumber(rs.getString("IDSUNO").trim());
				if (rs.getString("IDSUNM") != null)
				   returnValue.setSupplierName(rs.getString("IDSUNM").trim());
			}	
			if (requestType.equals("rfLoad"))
			{
				returnValue.setSupplierNumber(rs.getString("G1SCSUPP").trim());
				if (rs.getString("AIDSUNM") != null)
				    returnValue.setSupplierName(rs.getString("AIDSUNM").trim());
			}	
			if (requestType.equals("rfPO"))
			{
				returnValue.setSupplierNumber(rs.getString("G3SUPP").trim());
				if (rs.getString("BIDSUNM") != null)
				   returnValue.setSupplierName(rs.getString("BIDSUNM").trim());
			}	
			if (requestType.equals("rfEntryLot"))
			{
				returnValue.setSupplierNumber(rs.getString("G4SUPP").trim());
				if (rs.getString("AIDSUNM") != null)
					   returnValue.setSupplierName(rs.getString("AIDSUNM").trim());
			}		
			if (requestType.equals("rfEntrySpecialCharges"))
			{
				returnValue.setSupplierNumber(rs.getString("G6SUPP").trim());
				if (rs.getString("BIDSUNM") != null)
					   returnValue.setSupplierName(rs.getString("BIDSUNM").trim());
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Supplier class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceSupplier.");
			throwError.append("loadFieldsSupplier(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

	/**
	 * Use to Validate a Sent in Movex (M3) Supplier Number
	 *    // Check in file CIDMAS
	 */
	public static String verifySupplier(CommonRequestBean requestBean)
		throws Exception
	{
		StringBuffer throwError  = new StringBuffer();
		StringBuffer rtnProblem  = new StringBuffer();
		Connection conn          = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		
		try { 
			String requestType = "verifySupplierIdLevel";
			String sqlString = "";
				
			if (requestBean.getEnvironment().trim() == null || requestBean.getEnvironment().trim().equals(""))
				requestBean.setEnvironment("PRD");
			
			// verify incoming Specification
			if (requestBean.getIdLevel1().trim() == null || requestBean.getIdLevel1().trim().equals(""))
				rtnProblem.append(" Movex (M3) supplier number must not be null or empty."); 
			
			if (throwError.toString().equals(""))
			{
				try {
					Vector<CommonRequestBean> parmClass = new Vector<CommonRequestBean>();
					parmClass.addElement(requestBean);
					sqlString = buildSqlStatement(requestBean.getEnvironment().trim(), requestType, parmClass);
										
				} catch (Exception e) {
					
					throwError.append(" Error trying to build sqlString. ");
					rtnProblem.append(" Problem when building test for supplier number: ");
					rtnProblem.append(requestBean.getIdLevel1().trim());
				    rtnProblem.append(" PrintScreen this message and send to Information Services. ");
				}
			}
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				
				try {
					conn = com.treetop.utilities.ConnectionStack7.getConnection();
						
					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();
						
					if (rs.next())
					{
	//					 it exists and all is good.
						
					} else {
						rtnProblem.append(" Movex (M3) supplier number '" + requestBean.getIdLevel1().trim() + "'");
						rtnProblem.append(" does not exist. ");
					}
						
				} catch (Exception e) {
					throwError.append(" Error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Movex (M3) supplier number: ");
					rtnProblem.append(requestBean.getIdLevel1().trim());
					rtnProblem.append(" PrintScreen this message and send to Information Services. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem verifying the Movex (M3) supplier number; " + requestBean.getIdLevel1().trim());
			
		// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						com.treetop.utilities.ConnectionStack7.returnConnection(conn);
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
			throwError.append("ServiceSupplier.");
			throwError.append("verifySupplier(CommonRequestBean)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

	/**
	 * Use to Validate a Sent in Movex (M3) Supplier Number
	 *    // Check in file CIDMAS
	 */
	public static String verifySupplier(String environment, String supplierNumber)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifySupplier";
			String sqlString = "";
				
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Specification
			if (supplierNumber == null || supplierNumber.trim().equals(""))
				rtnProblem.append(" Movex (M3) Supplier Number must not be null or empty.");
			
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(supplierNumber);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
				rtnProblem.append(" Problem when building Test for Supplier Number: ");
				rtnProblem.append(supplierNumber + " PrintScreen this message and send to Information Services. ");
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
	//					 it exists and all is good.
					} else {
						rtnProblem.append(" Movex (M3) Supplier Number '" + supplierNumber + "' ");
						rtnProblem.append("does not exist. ");
					}
						
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Movex (M3) Supplier Number: ");
					rtnProblem.append(supplierNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem verifying the Movex (M3) Supplier Number; " + supplierNumber);
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
			throwError.append("ServiceSupplier.");
			throwError.append("verifySupplier(String: supplierNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

	public static Vector<DropDownSingle> getDropDownSinglePeachSuppliers(String env) throws Exception {
		
		Vector<DropDownSingle> items = new Vector<DropDownSingle>();
		
		StringBuffer throwError = new StringBuffer();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack15();
			stmt = conn.createStatement();
			
			String environment = env;
			String requestType = "peachSupplierList";
			Vector parms = new Vector();
			String sql = buildSqlStatement(environment, requestType, parms);
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
	
				DropDownSingle dd = new DropDownSingle();
				String fullName = rs.getString("IDSUNM").trim();
				String shortName = fullName.substring(25);
				dd.setDescription(shortName);
				dd.setValue(rs.getString("IDSUNO").trim());
				
				items.add(dd);
	
			}
			
			
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {}
			}
			if (conn != null) {
				try {
					ServiceConnection.returnConnectionStack15(conn);
				} catch (Exception e) {}
			}
		}
		
		if (!throwError.toString().trim().equals("")) {
			throw new Exception(throwError.toString());
		}
		
		return items;
	}	
	
}
