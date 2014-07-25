/*
 * Created on December 22, 2008
 *
  * 
 */
package com.treetop.services;


import java.sql.*;
import java.util.*;
import java.math.*;

import javax.sql.*;
import com.ibm.as400.access.*;

import com.treetop.*;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.view.*;
import com.treetop.viewbeans.*;
import com.treetop.utilities.*;
import com.treetop.utilities.html.DropDownSingle;


/**
 * @author thaile .
 *
 * Services class to obtain and return data 
 * to business objects.
 * 
 */
public class ServiceCustomer extends BaseService {

	public static final String library = "M3DJDPRD";
	public static final String ttlib = "DBPRD";
	//public static final String ttlibTEST = "APDEV";

	/**
	 * 
	 */
	public ServiceCustomer() {
		super();
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
			
			//Build Customer Business Object.
			if ("x".equals("x"))
			{
				//by customer number
				if (1 == 1)
				{
					Customer cc = new Customer();
					cc.setCompany("100");
					cc.setNumber("30000");
					getCustomerByNumber(cc);
				}
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
		
		try { //catch errors

// **********************************************************************************
//           SELECT
		
		if (inRequestType.equals("getCustomerByNumber"))
		{
			Customer cc = (Customer) requestClass.elementAt(0);
			
			sqlString.append("SELECT OKCONO, OKDIVI, OKSTAT, OKCUNO, ");
			sqlString.append("OKCUCL, OKCUTP, OKALCU, OKCUNM, OKSDST, ");
			sqlString.append("OKCFC3 ");
			sqlString.append("FROM " + library + ".OCUSMA ");
			sqlString.append("WHERE OKCONO = " + cc.getCompany() + " ");
			sqlString.append("AND OKCUNO = '" + cc.getNumber() + "' ");
		}
		
		if (inRequestType.equals("verifyCustomerByNumberIdLevelM3"))
		{
			CommonRequestBean commonRequest = (CommonRequestBean) requestClass.elementAt(0);
			String libraryM3 = GeneralUtility.getLibrary(commonRequest.getEnvironment().trim());			
			
			sqlString.append("SELECT OKCONO, OKDIVI, OKSTAT, OKCUNO, ");
			sqlString.append("OKCUCL, OKCUTP, OKALCU, OKCUNM, OKSDST, ");
			sqlString.append("OKCFC3 ");
			sqlString.append("FROM " + libraryM3 + ".OCUSMA ");
			sqlString.append("WHERE OKCONO = " + commonRequest.getCompanyNumber().trim() + " ");
			sqlString.append("AND OKDIVI = '" + commonRequest.getDivisionNumber().trim() + "' ");
			sqlString.append("AND OKCUNO = '" + commonRequest.getIdLevel1().trim() + "' ");
		}
			
		if (inRequestType.equals("verifyCustomerByNumberIdLevelSample"))
		{			
			CommonRequestBean commonRequest = (CommonRequestBean) requestClass.elementAt(0);
			String libraryTT = GeneralUtility.getTTLibrary(commonRequest.getEnvironment().trim());
			
			sqlString.append("SELECT SRANUMBER, SRANAME ");			
			sqlString.append("FROM " + libraryTT + ".SRPACUST ");
			sqlString.append("WHERE SRANUMBER = " + commonRequest.getIdLevel1().trim());
		}		
					
			
// **********************************************************************************
//			 DROP DOWN LISTS 
		
		if (inRequestType.equals("ddCustomerInqIngSpec"))
		{
			String type = (String) requestClass.elementAt(0);
			
			//Show Customer No and Name
			sqlString.append("SELECT IDTCNO, OKCUNM ");
			sqlString.append("FROM DBPRD.IDPTSPHD ");
			if (type.equals("inqINGSpecs"))
			{
				sqlString.append("INNER JOIN " + library + ".OCUSMA	 ");
				sqlString.append("ON SUBSTRING(IDTCNO,10,5) = OKCUNO ");
			}
			sqlString.append("GROUP BY IDTCNO, OKCUNM ");
			sqlString.append("ORDER BY OKCUNM ");
		}
		

// **********************************************************************************
//  UPDATE

// **********************************************************************************
//  INSERT

// **********************************************************************************
//  DELETE

		} catch (Exception e) {
				throwError.append(" Error building sql statement"
							 + " for request type " + inRequestType + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceCustomer.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		
		return sqlString.toString();
	}

/**
 *   Method Created 12/23/08 THaile
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 * 
 *  Incoming Vector:
 *    Element 0 = String - Values:
 * 				  inqIngSpecs - Means it will tie by item to the ING Spec files to return only valid entries 
 */
public static Vector dropDownCustomerInqIngSpec(Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack.getConnection();
		ddit = dropDownCustomerInqIngSpec(inValues, conn);
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
 *   Method Created 01/20/09 THaile
 *   // Use Company & Customer Number
 * @param -- Customer Business object
 * @return   Customer Business Object
 * 
 */
public static Customer getCustomerByNumber(Customer inCustomer)
{
	StringBuffer error = new StringBuffer();
	Customer outCustomer = new Customer();
	Connection conn = null;
	try {
		if (inCustomer.getNumber() == null)
		{
			error.append("The input cus# (" + inCustomer.getNumber() + ") is not a valid number. ");
		}
		
		if (inCustomer.getCompany() == null ||
			inCustomer.getCompany().trim().equals(""))
		{
			inCustomer.setCompany("100");
		}
		
		if (error.toString().trim().equals("") && !inCustomer.getNumber().trim().equals("")
			&& !inCustomer.getCompany().trim().equals(""))
		{	
			try {
				//get a connection.
				conn = getDBConnection();
				
				//build the customer business object.
				outCustomer = getCustomerByNumber(inCustomer, conn);
			} catch (Exception e) {
				error.append("Error executing customer bussiness object build. " + e);
			}
		}
	
	} catch (Exception e) {
		error.append("Method level error. " + e);
	}
	
	finally {
		if (conn != null)
		{
			try
			{
			   conn.close();
			} catch(Exception e){
				error.append("Error closing the connection. " + e);
			}
		}
	}
	
	//list the error with a stack trace into the server log.	
	if (!error.toString().trim().equals(""))
	{
		error.append("Error at com.treetop.services.ServiceCustomer.getCustomerByNumber(");
		error.append(inCustomer.getNumber() + "). ");
		System.out.println(error.toString());
		Exception e = new Exception();
		e.printStackTrace();
	}

	
	return outCustomer;
}


/**
 * Get customer information using customer numnber. 
 * @param String - customer number.
 * @param Connection.
 * @return Customer business object.
 */
private static Customer getCustomerByNumber(Customer incc, Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rs = null;
	PreparedStatement ps = null;
	String requestType = "";
	String sqlString = "";
	Customer outcc = new Customer();
	

	
	try { //enable finally.
		
		try //build sql.
		{
			String environment = "PRD";
			requestType = "getCustomerByNumber";
			Vector parmClass = new Vector();
			parmClass.addElement(incc);
			sqlString = buildSqlStatement(environment, requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error building sql for getCustomerByNumber. " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				ps = conn.prepareStatement(sqlString);
				rs = ps.executeQuery();
				
				// for each record update the Sales File.
				if (rs.next() && throwError.toString().equals(""))
				{
					//extract keys used for update.
					outcc = loadFields(requestType, rs);
					ps.close();
					rs.close();
				}
			} catch (Exception e) {
				throwError.append("Error at execute sql (getCustomerByNumber). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try {
			if (!throwError.toString().trim().equals(""))
			{
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			}
		} catch(Exception e) {
			//skip this
		}
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceCustomer.");
		throwError.append("getCustomerByNumber(");
		throwError.append("Number, Connection");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}

  return outcc;
}


/**
 * Load class fields from result set.
 */
private static Customer loadFields(String loadType,
							       ResultSet rs)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Customer	 cc         = new Customer();
	
	if ((loadType.equals("getCustomerByNumber")) ||
		(loadType.equals("getCustomerByNumberIdLevelM3")))
	{	
		cc.setCompany(rs.getString("OKCONO").trim());
		cc.setDivision(rs.getString("OKDIVI").trim());
		cc.setStatus(rs.getString("OKSTAT").trim());
		cc.setNumber(rs.getString("OKCUNO").trim());
		cc.setGroup(rs.getString("OKCUCL").trim());
		cc.setType(rs.getString("OKCUTP").trim());
		cc.setSearchKey(rs.getString("OKALCU").trim());
		cc.setName(rs.getString("OKCUNM").trim());
		cc.setDistrict(rs.getString("OKSDST").trim());
		cc.setMarket(rs.getString("OKCFC3").trim());
	}
	
	if (loadType.equals("getCustomerByNumberIdLevelSample"))
	{
		cc.setNumber(rs.getString("SRANUMBER").trim());
		cc.setName(rs.getString("SRANAME").trim());
	}

	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("loadFields(loadType:");
		throwError.append(loadType + ", rs). ");
		throw new Exception(throwError.toString());
	}
	return cc;
}

/**
 * Method Created 12/19/08 THaile
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 */
private static Vector dropDownCustomerInqIngSpec(Vector inValues, 
									             Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Customers numbers in the Specification Header file - Along with their names
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", "ddCustomerInqIngSpec", inValues));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		 	System.out.println("error" + e);
		 }		
		 try
		 {
		 	while (rs.next())
		    {
		 		DropDownSingle dds = loadFieldsDropDownSingle("ddCustomerInqIngSpec", rs);
		 		ddit.addElement(dds);
     		}
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
//		throwError.append("ServiceCustomer.");
//		throwError.append("dropDownCustomerUnqIngSpec(");
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
	  
	  if (requestType.equals("ddCustomerInqIngSpec"))
	  {
		returnValue.setValue(rs.getString("IDTCNO").trim());
		returnValue.setDescription(rs.getString("OKCUNM").trim());
	  }
	  
	} catch(Exception e)
	{
		throwError.append(" Error loading dropdown fields ");
		throwError.append("from the result set. " + e) ;
	}
	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceCustomer.");
		throwError.append("loadFieldsDropDownSingle(requestType(");
		throwError.append(requestType + "), rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}
	return returnValue;
}

/**
 *   Method Copied 08/17/10 DEisen
 *   // Use Company, Division & Customer Number
 */
public static String verifyCustomerByNumber(CommonRequestBean requestBean)
{
	StringBuffer error = new StringBuffer();
	String returnValue = new String();
	Connection conn = null;
	
	try {
		if (requestBean.getIdLevel1().trim() == "")
		{
			error.append("The input customer id (" + requestBean.getIdLevel1().trim() + ") is not a valid number. ");
		}
		
		if (requestBean.getCompanyNumber().trim() == null ||
				requestBean.getCompanyNumber().trim().equals(""))
		{
			requestBean.setCompanyNumber("100");
		}
		if (requestBean.getDivisionNumber().trim() == null ||
				requestBean.getDivisionNumber().trim().equals(""))
		{
			requestBean.setDivisionNumber("100");
		}
		
		if (error.toString().trim().equals("")
			&& !requestBean.getIdLevel1().trim().equals("")
			&& !requestBean.getIdLevel2().trim().equals("")
			&& !requestBean.getCompanyNumber().trim().equals("")
			&& !requestBean.getDivisionNumber().trim().equals(""))
		{	
			try {
				//get a connection.
				conn = com.treetop.utilities.ConnectionStack7.getConnection();				
				returnValue = verifyCustomerByNumber(requestBean, conn);
				
			} catch (Exception e) {
				error.append("Error executing verify customer. " + e);
			}
		}
	
	} catch (Exception e) {
		error.append("Method level error. " + e);
	}
	
	finally {
		if (conn != null)
		{
			try
			{
				com.treetop.utilities.ConnectionStack7.returnConnection(conn);
			} catch(Exception e){
				error.append("Error closing the connection. " + e);
			}
		}
	}
	
	//list the error with a stack trace into the server log.	
	if (!error.toString().trim().equals(""))
	{
		error.append("Error at com.treetop.services.ServiceCustomer.verifyCustomerByNumber(");
		error.append(requestBean.getIdLevel1().trim() + "). ");
		System.out.println(error.toString());
		Exception e = new Exception();
		e.printStackTrace();
	}

	
	return returnValue;
}

/**
 * Verify customer number. 
 */
private static String verifyCustomerByNumber(CommonRequestBean requestBean, Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rs = null;
	PreparedStatement ps = null;
	String requestType = "";
	String sqlString = "";
	String returnValue = new String();
	
	try { //enable finally.
		
		try //build sql.
		{
			if (requestBean.getIdLevel2().trim().toUpperCase().equals("M3"))
			{			
				requestType = "verifyCustomerByNumberIdLevelM3";
			}
			if (requestBean.getIdLevel2().trim().toUpperCase().equals("SAM"))
			{			
				requestType = "verifyCustomerByNumberIdLevelSample";
			}
			
			Vector<CommonRequestBean> parmClass = new Vector<CommonRequestBean>();
			parmClass.addElement(requestBean);
			sqlString = buildSqlStatement(requestBean.getEnvironment().trim(), requestType, parmClass);
			
		} catch(Exception e) {
			throwError.append("Error building sql for verifyCustomerByNumber. " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				ps = conn.prepareStatement(sqlString);
				rs = ps.executeQuery();					
				
				if (rs.next())
				{
					 returnValue = "";
				}
				else {
					
					 StringBuffer message = new StringBuffer();
					 message.append(" Customer Number '" + requestBean.getIdLevel1().trim() + "'");
					 message.append(" does not exist. ");
					 returnValue = message.toString();
				}
				
				ps.close();
				rs.close();
				
			} catch (Exception e) {
				throwError.append("Error at execute sql (verifyCustomerByNumber). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try {
			if (!throwError.toString().trim().equals(""))
			{
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			}
		} catch(Exception e) {
			//skip this
		}
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceCustomer.");
		throwError.append("verifyCustomerByNumber(");
		throwError.append("CommonRequestBean, Connection");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}

  return returnValue;
}
}

