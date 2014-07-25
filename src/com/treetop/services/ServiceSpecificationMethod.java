/*
 * Created on January 7, 2009
 * 
 */
package com.treetop.services;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;

import com.ibm.as400.access.*;
import com.treetop.app.specification.DtlMethod;
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
 *    Currently only used for CPG Method -
 *    Drop Down Lists
 */
public class ServiceSpecificationMethod extends BaseService {

	public static final String library = "M3DJDPRD";
	public static final String ttlib = "DBPRD";

	/**
	 * 
	 */
	public ServiceSpecificationMethod() {
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
//    VERIFY METHOD
// *********************************************************************************			
			if (inRequestType.equals("verifyMethod"))
			{
				// cast the incoming parameter class.
				String methodNumber = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT  SPNMETH  ");
				sqlString.append("FROM " + ttlib + ".SPPNMETH ");
				sqlString.append(" WHERE SPNMETH = '" + methodNumber + "' ");
			}
// **********************************************************************************
//		FIND/LIST Method
// **********************************************************************************
		if (inRequestType.equals("dtlMethod"))
		{
			DtlMethod dMeth = (DtlMethod) requestClass.elementAt(0);
			sqlString.append(" SELECT * ");
			sqlString.append(" FROM " + ttlib + ".SPPNMETH ");
			sqlString.append(" WHERE SPNMETH = '" + dMeth.getMethodNumber() + "' ");
			if (!dMeth.getRevisionDate().equals(""))
				sqlString.append(" AND SPNMETH <= " + dMeth.getRevisionDate() + "' ");
			else
				sqlString.append(" AND SPNMETH <> 'PENDING'" );
			sqlString.append(" ORDER BY SPNRECST, SPNREVIS DESC ");
		}
//			 DROP DOWN LISTS FROM METHOD MASTER INFORMATION
		if (inRequestType.equals("ddMethod"))
		{
		//	String type = (String) requestClass.elementAt(0);
			
//			 Always show the Item Type and the Description
			sqlString.append(" SELECT SPNMETH, SPNTITLE ");
			sqlString.append(" FROM " + ttlib + ".SPPNMETH ");
			sqlString.append(" WHERE SPNRECST = 'ACTIVE' ");
			sqlString.append(" GROUP BY SPNMETH, SPNTITLE ");
			sqlString.append(" ORDER BY SPNMETH ");
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
			throwError.append("Error at com.treetop.services.ServiceMethod.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		
		return sqlString.toString();
	}

/**
 *   Method Created 1/7/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				inqCPGSpecs - Means it will tie by item to the CPG Spec files to return only valid entries 
 */
public static Vector dropDownMethod(Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		ddit = dropDownMethod(inValues, conn);
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
 * Method Created 1/7/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 */
private static Vector dropDownMethod(Vector inValues, 
									 Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Item Type Values - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", "ddMethod", inValues));
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
		 		DropDownSingle dds = loadFieldsDropDownSingle("ddMethod", rs);
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
	  if (requestType.equals("ddMethod"))
	  {
		returnValue.setValue(rs.getString("SPNMETH").trim());
		returnValue.setDescription(rs.getString("SPNTITLE").trim());
	  }
	} catch(Exception e)
	{
		throwError.append(" Problem loading Info from the result set. " + e) ;
	}
	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceSpecificationMethod.");
		throwError.append("loadFieldsDropDownSingle(requestType, rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}
	return returnValue;
}

/**
 * Use to Validate a Method, Return Message if Formula is not found
 *    Will test to see if there is a record in the File SPPLFORM
 */
   public static String verifyMethod(String environment, 
   									 String methodNumber)
		  throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyMethod";
			String sqlString = "";
			
			// verify base class initialization.
//		ServiceItem a = new ServiceItem();
			 System.out.println("ServiceSpecificationMethod.verifyMethod -- Don't know if the Class needs to be instatiated");				
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Formula
			if (methodNumber == null || methodNumber.trim().equals(""))
				rtnProblem.append(" Method Number must not be null or empty.");
			
			// get Formula Info.
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(methodNumber);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
				rtnProblem.append(" Problem when building Test for Formula: ");
				rtnProblem.append(methodNumber + " PrintScreen this message and send to Information Services. ");
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
						rtnProblem.append(" Method Number '" + methodNumber + "' ");
						rtnProblem.append("does not exist. ");
					}
					
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Formula Number: ");
					rtnProblem.append(methodNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Method Number class ");
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
			throwError.append("ServiceSpecificationMethod.");
			throwError.append("verifyMethod(String: methodNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

/**
 * Load class fields from result set.
 */

public static SpecificationMethod loadFieldsMethod(String requestType,
						          		  	       ResultSet rs)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	SpecificationMethod returnValue = new SpecificationMethod();
	try{ 
	  if (requestType.equals("dtlFormula"))
	  {
		returnValue.setMethodNumber(rs.getString("SPNMETH").trim());
		returnValue.setMethodName(rs.getString("SPNTITLE").trim());
		returnValue.setRevisionDate(rs.getString("SPNREVIS"));
		returnValue.setRecordStatus(rs.getString("SPNRECST"));
	  }
	} catch(Exception e)
	{
		throwError.append(" Problem loading Info from the result set. " + e) ;
	}
	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceSpecificationMethod.");
		throwError.append("loadFieldsMethod(requestType, rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}
	return returnValue;
}

/**
 *   Method Created 1/7/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the DtlMethod Object for use in the SQL statement
 * @return Formula
 * @throws Exception
 */
public static SpecificationMethod findMethod(Vector inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	SpecificationMethod returnValue = new SpecificationMethod();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		returnValue = findMethod(inValues, conn);
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
		throwError.append("ServiceSpecificationMethod.");
		throwError.append("findMethod(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 *    Method Created 1/7/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the DtlSpecification Object for use in the SQL statement
 * @return Formula
 * @throws Exception
 */
private static SpecificationMethod findMethod(Vector inValues, 
										  	  Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	SpecificationMethod returnValue = new SpecificationMethod();
 	try
	{
		try {
			   // Get the list of DataSet File Names - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", "dtlMethod", inValues));
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
	 				returnValue = loadFieldsMethod("dtlMethod", rs);
	 				x++;
	 				saveRevision = rs.getString("SPNREVIS");
	 			}
	 			else
	 			{
	 				if (!saveRevision.equals(rs.getString("SPNREVIS")))
	 				{
	 					returnValue.setSupercedesDate(rs.getString("SPNREVIS"));
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

