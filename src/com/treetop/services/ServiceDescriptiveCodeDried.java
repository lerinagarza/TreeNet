/*
 * Created on Mar 27, 2006
 *
 */
package com.treetop.services;

import java.sql.*;
import com.treetop.utilities.*;
import java.sql.ResultSet;
import java.text.*;
import java.util.Vector;


import com.treetop.businessobjects.*;

/**
 * @author thaile
 *
 * Services class to obtain and return data 
 * to business objects.
 * Descriptive Code Dried Services Object.
 */
public class ServiceDescriptiveCodeDried {
	
	
	/**
	 * Return a DescriptiveCodeDried object. Use the 
	 * incoming result set for the load of the object.
	 * use the incoming string to determine how
	 * to load the object from the incoming result set.
	 * @param result set.
	 * @param string (logic value).
	 * @return DescriptiveCodeDried business object.
	 * @throws Exception
	 */
	
	public static DescriptiveCodeDried buildDescriptiveCodeDried(ResultSet rs,
																 String fmRequest)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		DescriptiveCodeDried code = new DescriptiveCodeDried();
		
		// edit fmRequest
		try{
			if (fmRequest == null ||
				fmRequest.trim().equals("") )
				throwError.append("The incoming string must not be null, blank, or empty. ");
		} catch(Exception e) {
			throwError.append("Error during edit of string parameter. " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try {
				// execute find method.
				code = loadFields(rs, fmRequest);
			} catch (Exception e)
			{
				throwError.append(e);
			}
		}
		
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceDescriptiveCodeDried.");
			throwError.append("buildDescriptiveCodeDried(rs, string:");
			throwError.append(fmRequest + ") ");
			throw new Exception(throwError.toString());
		}

		// return value
		return 	code;
	}
	
	
	/**
	 * Load class fields from result set.
	 * 
	 */
	
	private static DescriptiveCodeDried loadFields(ResultSet rs, 
												   String type)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		DescriptiveCodeDried code = new DescriptiveCodeDried();

		try{ // sqlStatement("lotPaymentInquiry")
			
			if (type.equals("lotPaymentInquiry paymentVariety"))
			{
				code.setDescType(rs.getString("pvID2IND").trim());
				code.setDescCode(rs.getString("pvID2DCD").trim());		
				code.setDescFull(rs.getString("pvID2DSC").trim());
				code.setDescShort(rs.getString("pvID2D08").trim());
				code.setPlantNumber(rs.getString("pvID2PLT").trim());
				code.setProductLine(rs.getString("pvID2PLN").trim());
			}
			
		} catch(Exception e)
		{
			throwError.append(" Problem loading the class ");
			throwError.append("from the result set. " + e) ;
		}
		
		try{ // sqlStatement("lotPaymentInquiry")
			if (type.equals("lotPaymentInquiry inventoryVariety"))
			{
				code.setDescType(rs.getString("ivID2IND").trim());
				code.setDescCode(rs.getString("ivID2DCD").trim());		
				code.setDescFull(rs.getString("ivID2DSC").trim());
				code.setDescShort(rs.getString("ivID2D08").trim());
				code.setPlantNumber(rs.getString("ivID2PLT").trim());
				code.setProductLine(rs.getString("ivID2PLN").trim());
			}
			
		} catch(Exception e)
		{
			throwError.append(" Problem loading the class ");
			throwError.append("from the result set. " + e) ;
		}
		
		// return data.
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceDescriptiveCodeDried.");
			throwError.append("loadFields(rs, type:");
			throwError.append(type + "). ");
			throw new Exception(throwError.toString());
		}
		return code;
	}
	
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		// Test all that you can.
		String stophere = "";		
		
		try {
		//*** TEST "buildxx(String)".
			if ("x".equals("x"))
			{
				Vector x = new Vector();
				String k = "PT";
				x = codeValuesByName(k);
				stophere = "yes";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	/**
	 * Vector of Code Values.
	 * Order by Value Name.
	 */
	public static Vector codeValuesByName(String key1)
	{
		StringBuffer    throwError  = new StringBuffer();
		Connection 		conn        = null;
		Vector			list		= null;
		
		try {
			conn = ConnectionStack4.getConnection();
			list = codeValuesByName(key1, conn);
			
		} catch (Exception e)
		{
			throwError.append(e);
		}
		
		finally {
			
			if (conn != null)
			{
				try {
				   ConnectionStack4.returnConnection(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		
		if (!throwError.toString().trim().equals(""))
		{
			System.out.println("Error at com.treetop.services.ServiceDescriptiveCodeDried." +
		               "codeVasluesByName(): " + throwError.toString());
		}
		
		return list;
	}


	/**
	 * Return scheduled load by schedule Number.
	 */
	private static Vector codeValuesByName(String key1, Connection conn)
	throws Exception
	{
		StringBuffer      throwError    = new StringBuffer();
		ResultSet         rs            = null;
		Statement         findIt        = null;
		Vector            list          = new Vector();
		String			  requestType   = "";
		String			  sqlString		= "";
				
		try { //catch all
			
			//Get values by key code.
			try {
				requestType = "codeValuesByName";
				Vector parmClass = new Vector();
				parmClass.addElement(key1);
				sqlString = buildSqlStatement(requestType, parmClass);
				findIt = conn.createStatement();
				rs = findIt.executeQuery(sqlString);
			 } catch(Exception e)
			 {
			   	throwError.append("Error occured retrieving or executing a sql statement. " + e);
			 }
			 
			 if (throwError.toString().trim().equals(""))
			 {
				 try {
					 
					 
					 //vector to hold all detail.
					 Vector dtl = new Vector();
	
					 while(rs.next())
					 {
						 list.addElement(rs.getString("ID2DSC").trim());
					 }
					
		     	 } catch(Exception e)
				 {
					throwError.append(" Error occured while building class in method. " + e);
				 } 		
			 }
	
		} catch (Exception e)
		{
			throwError.append(e);
		}
		
		finally {
			
		   if (rs != null)
		   {
			   try {
				  rs.close();
				} catch(Exception el){
					el.printStackTrace();
				}
		    }
		   if (findIt != null)
		   {
			   try {
				  findIt.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceDescriptiveCodeDried.");
			throwError.append("CodeValuesByName, conn). ");
			throw new Exception(throwError.toString());
		}
	
		return list;
	}


	/**
	 * Build an SQL statement for specifications.
	 */
		
	private static String buildSqlStatement(String requestType,
										    Vector requestClass)
	throws Exception 
	{
		StringBuffer sqlString       = new StringBuffer();	
		StringBuffer throwError      = new StringBuffer();
			
		try { //catch all
			
			if (requestType.equals("codeValuesByName"))
			{
				String key1 = (String) requestClass.elementAt(0);
				
				sqlString.append("select ID2DSC ");
				sqlString.append("from dbprd.idp2desc ");
				sqlString.append("where ID2IND = '" + key1.trim() + "'  ");
				sqlString.append("and ID2DLT = '' ");
				sqlString.append("order by ID2DSC "); 
			}

			
		
		//catch all
		} catch (Exception e) {
				throwError.append(" Error building sql statement" +
							      " for request type " + requestType + ". " + e);
		}
	
		
			
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceDescriptiveCodeDried.");
			throwError.append("buildSqlStatement(String, Vector)");
			throw new Exception(throwError.toString());
		}
			
		return sqlString.toString();
	}

}
