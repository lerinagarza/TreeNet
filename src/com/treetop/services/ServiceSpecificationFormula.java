/*
 * Created on October 21, 2008
 * 
 */
package com.treetop.services;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;

import com.ibm.as400.access.*;
import com.treetop.app.specification.DtlFormula;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.utilities.html.DropDownSingle;

/**
 * @author twalto .
 *
 * Services class to obtain and return data 
 * to business objects.
 * 
 * Formula -- 
 *    Currently only used for CPG Formula - 
 *     Drop Down Lists
 */
public class ServiceSpecificationFormula extends BaseService {

	public static final String library = "M3DJDPRD";
	public static final String ttlib = "DBPRD";

	/**
	 * 
	 */
	public ServiceSpecificationFormula() {
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
// **********************************************************************************
//    VERIFY FORMULA
// *********************************************************************************			
			if (inRequestType.equals("verifyFormula"))
			{
				// cast the incoming parameter class.
				String formulaNumber = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT  SPLFORM  ");
				sqlString.append("FROM " + ttlib + ".SPPLFORM ");
				sqlString.append(" WHERE SPLFORM = '" + formulaNumber + "' ");
			}
// **********************************************************************************
//		FIND/LIST FORMULA
// **********************************************************************************
		if (inRequestType.equals("dtlFormula"))
		{
			DtlFormula df = (DtlFormula) requestClass.elementAt(0);
			sqlString.append(" SELECT * ");
			sqlString.append(" FROM " + ttlib + ".SPPLFORM ");
			sqlString.append(" WHERE SPLFORM = '" + df.getFormulaNumber() + "' ");
			if (!df.getRevisionDate().equals(""))
				sqlString.append(" AND SPLREVIS <= " + df.getRevisionDate() + "' ");
			else
				sqlString.append(" AND SPLRECST <> 'PENDING'" );
			sqlString.append(" ORDER BY SPLRECST, SPLREVIS DESC ");
		}
//			 DROP DOWN LISTS FROM FORMULA MASTER INFORMATION
		if (inRequestType.equals("ddFormula"))
		{
			String type = (String) requestClass.elementAt(0);
			
//			 Always show the Item Type and the Description
			sqlString.append(" SELECT SPLFORM, SPLNAME ");
			sqlString.append(" FROM " + ttlib + ".SPPLFORM ");
			if (type.equals("inqCPGSpecs"))
			{
				sqlString.append(" INNER JOIN " + ttlib + ".SPPPHEAD ");
				sqlString.append("    ON SPPFORM = SPLFORM ");
			}
			sqlString.append(" WHERE SPLRECST = 'ACTIVE' ");
			sqlString.append(" GROUP BY SPLFORM, SPLNAME ");
			sqlString.append(" ORDER BY SPLFORM ");
		}
		
// **********************************************************************************
//  UPDATE
// **********************************************************************************
// **********************************************************************************		
//  INSERT
// **********************************************************************************
// **********************************************************************************		
//  DELETE
// **********************************************************************************
		} catch (Exception e) {
				throwError.append(" Error building sql statement"
							 + " for request type " + inRequestType + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceFormula.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		
		return sqlString.toString();
	}

/**
 *   Method Created 10/21/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				inqCPGSpecs - Means it will tie by item to the CPG Spec files to return only valid entries 
 */
public static Vector dropDownFormula(Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		ddit = dropDownFormula(inValues, conn);
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
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
	// return value
	return ddit;
}

/**
 * Method Created 10/21/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 */
private static Vector dropDownFormula(Vector inValues, 
									  Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Item Type Values - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", "ddFormula", inValues));
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
		 		DropDownSingle dds = loadFieldsDropDownSingle("ddFormula", rs);
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
	// test and throw error if needed.
//	if (!throwError.toString().trim().equals(""))
//	{
//		throwError.append(" @ com.treetop.Services.");
//		throwError.append("ServiceItem.");
//		throwError.append("findItemCodeDate(");
//		throwError.append("Vector, conn). ");
//		throw new Exception(throwError.toString());
//	}
	// return value
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
	  if (requestType.equals("ddFormula"))
	  {
		returnValue.setValue(rs.getString("SPLFORM").trim());
		returnValue.setDescription(rs.getString("SPLNAME").trim());
	  }
	} catch(Exception e)
	{
		throwError.append(" Problem loading Info from the result set. " + e) ;
	}
	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceFormula.");
		throwError.append("loadFieldsDropDownSingle(requestType, rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}
	return returnValue;
}

/**
 * Use to Validate a Formula, Return Message if Formula is not found
 *    Will test to see if there is a record in the File SPPLFORM
 */
   public static String verifyFormula(String environment, 
   									  String formulaNumber)
		  throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyFormula";
			String sqlString = "";
			
			// verify base class initialization.
			//ServiceItem a = new ServiceItem();
			 System.out.println("ServiceRawFruit.verifyFormula -- Don't know if the Class needs to be instatiated");			
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Formula
			if (formulaNumber == null || formulaNumber.trim().equals(""))
				rtnProblem.append(" Formula Number must not be null or empty.");
			
			// get Formula Info.
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(formulaNumber);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
				rtnProblem.append(" Problem when building Test for Formula: ");
				rtnProblem.append(formulaNumber + " PrintScreen this message and send to Information Services. ");
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
						rtnProblem.append(" Formula Number '" + formulaNumber + "' ");
						rtnProblem.append("does not exist. ");
					}
					
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Formula Number: ");
					rtnProblem.append(formulaNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Formula Number class ");
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
			throwError.append("ServiceFormula.");
			throwError.append("verifyFormula(String: formulaNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

/**
 * Load class fields from result set.
 */

public static SpecificationFormula loadFieldsFormula(String requestType,
						          		  	   ResultSet rs)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	SpecificationFormula returnValue = new SpecificationFormula();
	try{ 
	  if (requestType.equals("dtlFormula"))
	  {
		returnValue.setFormulaNumber(rs.getString("SPLFORM").trim());
		returnValue.setFormulaName(rs.getString("SPLNAME").trim());
		returnValue.setRevisionDate(rs.getString("SPLREVIS"));
		returnValue.setRecordStatus(rs.getString("SPLRECST"));
	  }
	} catch(Exception e)
	{
		throwError.append(" Problem loading Info from the result set. " + e) ;
	}
	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceSpecificationFormula.");
		throwError.append("loadFieldsFormula(requestType, rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}
	return returnValue;
}

/**
 *   Method Created 10/28/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the DtlFormula Object for use in the SQL statement
 * @return Formula
 * @throws Exception
 */
public static SpecificationFormula findFormula(Vector inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	SpecificationFormula returnValue = new SpecificationFormula();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		returnValue = findFormula(inValues, conn);
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
		throwError.append("ServiceFormula.");
		throwError.append("findFormula(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 *    Method Created 10/28/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the DtlSpecification Object for use in the SQL statement
 * @return Formula
 * @throws Exception
 */
private static SpecificationFormula findFormula(Vector inValues, 
										  	  Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	SpecificationFormula returnValue = new SpecificationFormula();
 	try
	{
		try {
			   // Get the list of DataSet File Names - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", "dtlFormula", inValues));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }		
		 try
		 {
		 	int x = 0;
		 	String saveRevision = "";
	//	 	String saveStatus = "";
		 	while (rs.next() && x < 2)
		 	{
	 			if (x == 0)
	 			{
	 				returnValue = loadFieldsFormula("dtlFormula", rs);
	 				x++;
	 				saveRevision = rs.getString("SPLREVIS");
	 			}
	 			else
	 			{
	 				if (!saveRevision.equals(rs.getString("SPLREVIS")))
	 				{
	 					returnValue.setSupercedesDate(rs.getString("SPLREVIS"));
	 					x++;
	 				}
	 			}
     		}// END of the IF Statement
		 } catch(Exception e)
		 {
			throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		throwError.append(e);
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
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceSpecificationFormula.");
		throwError.append("findFormula(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}
}

