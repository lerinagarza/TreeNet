/*
 * Created on September 21, 2009
 * 
 */
package com.treetop.services;

import java.sql.*;
import java.util.Vector;

import com.treetop.app.transaction.*;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.BeanTransaction;
import com.treetop.utilities.*;
import com.treetop.utilities.html.DropDownSingle;


/**
 * @author twalto 
 *
 * Services class to obtain and return data 
 * to business objects.
 * 
 * Process / Report / Retrieve Anything to do with Transactions
 */
public class ServiceTransactionError extends BaseService {

	public static final String ttlib = "DB";
	public static final String library = "M3DJD";
	public static final String qgpl = "QGPL";
	public static final String treenet = "TREENET";

	/**
	 *  Constructor
	 */
	public ServiceTransactionError() {
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
	 * Load class fields from result set.
	 */
	
	public static DropDownSingle loadFieldsDropDownSingle(String requestType,
							          		        ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		DropDownSingle returnValue = new DropDownSingle();
	
		try{ 
			if (requestType.equals("ddShortCutUser"))
			{
			   //HHPTDATA, DPNUSER 
				 returnValue.setValue(rs.getString("MELUSR").trim());
				 if (rs.getString("DPNUSRNAME").trim() != null)
				   returnValue.setDescription(rs.getString("DPNUSRNAME").trim());
			}
			if (requestType.equals("ddShortCutWarehouse"))
			{
			   //HHPTDATA, MITWHL 
				 returnValue.setValue(rs.getString("MELWHS").trim());
				 if (rs.getString("MWWHNM").trim() != null)
				   returnValue.setDescription(rs.getString("MWWHNM").trim());
			}
			if (requestType.equals("ddShortCutItem"))
			{
			   //HHPTDATA, MITMAS
				 returnValue.setValue(rs.getString("MELITM").trim());
				 if (rs.getString("MMITDS").trim() != null)
				   returnValue.setDescription(rs.getString("MMITDS").trim());
			}
			
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Drop Down Single class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceTransactionError.");
			throwError.append("loadFieldsDropDownSingle(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
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
			//----------------------------------------------------------------------------------
			// DropDown Lists
			//----------------------------------------------------------------------------------
			if (inRequestType.trim().equals("ddShortCutUser"))
			{
				sqlString.append("SELECT MELUSR, DPNUSRNAME ");
				sqlString.append("FROM " + qgpl + ".HHPTDATA ");
				sqlString.append("LEFT OUTER JOIN " + treenet + ".DPPNUSER ON DPNUSER = MELUSR ");
				// Enviroment where
				sqlString.append("WHERE MELUSR <> ' ' AND MELETX <> ' ' AND MELETX <> 'OK'");
			//	sqlString.append("  AND MELENV = '" + library + environment + "' ");
				sqlString.append("GROUP BY MELUSR, DPNUSRNAME ");
				sqlString.append("ORDER BY DPNUSRNAME, MELUSR ");
			}
			if (inRequestType.trim().equals("ddShortCutWarehouse"))
			{
				sqlString.append("SELECT MELWHS, MWWHNM ");
				sqlString.append("FROM " + qgpl + ".HHPTDATA ");
				sqlString.append("INNER JOIN " + library + environment + ".MITWHL ON MWWHLO = MELWHS ");
				// Enviroment where
				sqlString.append("WHERE MELUSR <> ' ' AND MELETX <> ' ' AND MELETX <> 'OK'");
		//		sqlString.append("  AND MELENV = '" + library + environment + "' ");
				sqlString.append("GROUP BY MELWHS, MWWHNM ");
				sqlString.append("ORDER BY MELWHS, MWWHNM ");
			}
			if (inRequestType.trim().equals("ddShortCutItem"))
			{
				sqlString.append("SELECT MELITM, MMITDS ");
				sqlString.append("FROM " + qgpl + ".HHPTDATA ");
				sqlString.append("INNER JOIN " + library + environment + ".MITMAS ON MMITNO = MELITM ");
				// Enviroment where
				sqlString.append("WHERE MELUSR <> ' ' AND MELETX <> ' ' AND MELETX <> 'OK'");
		//		sqlString.append("  AND MELENV = '" + library + environment + "' ");
				sqlString.append("GROUP BY MELITM, MMITDS ");
				sqlString.append("ORDER BY MELITM, MMITDS ");
			}
			//----------------------------------------------------------------------------------
			// Lists
			//----------------------------------------------------------------------------------
			
			if (inRequestType.trim().equals("listTransactionError"))
			{
			   sqlString.append("SELECT MELCDE, MELADR, MELENV, MELDTE, MELTME, ");
			   sqlString.append("  MELUSR, MELTR#, MELECD, MELETX, MELWHS, ");
			   sqlString.append("  MELLOT, MELITM, ");
			   sqlString.append("  SUBSTRING(MELMAN,35,4) AS TYPE, ");
			   sqlString.append("  SUBSTRING(MELMAN,64,7) AS MO, ");
			   sqlString.append("  SUBSTRING(MELMAN,102,5) AS PRQTY, ");
			   sqlString.append("  DPNUSRNAME, ");
			   sqlString.append("  MWWHLO, MWWHNM, MWFACI, ");
			   sqlString.append("  MMITDS ");
			   sqlString.append("FROM " + qgpl + ".HHPTDATA ");
			   sqlString.append("LEFT OUTER JOIN " + treenet + ".DPPNUSER ");
			   sqlString.append("  ON MELUSR = DPNUSER ");
			   sqlString.append("LEFT OUTER JOIN " + library + environment + ".MITWHL ");
			   sqlString.append("  ON MWWHLO = MELWHS ");
			   sqlString.append("LEFT OUTER JOIN " + library + environment + ".MITMAS ");
			   sqlString.append("  ON MMITNO = MELITM ");
			   sqlString.append("WHERE MELETX <> 'OK' AND MELETX <> ' ' ");
			   try
			   {
				   InqTransaction it = (InqTransaction) requestClass.elementAt(0);
				   sqlString.append("AND MELENV = '" + library + it.getInqEnvironment().trim() + "' ");
				   if (!it.getInqUser().trim().equals(""))
					   sqlString.append("AND MELUSR = '" + it.getInqUser().trim() + "' ");
				   if (!it.getInqWhse().trim().equals(""))
					   sqlString.append("AND MELWHS = '" + it.getInqWhse().trim() + "' ");
				   if (!it.getInqItem().trim().equals(""))
					   sqlString.append("AND MELITM = '" + it.getInqItem().trim() + "' ");
				   if (!it.getInqLot().trim().equals(""))
					   sqlString.append("AND MELLOT LIKE '%" + it.getInqLot().trim() + "%' ");
				   if (!it.getInqFromDate().trim().equals(""))
				   {
					   DateTime fromDT = UtilityDateTime.getDateFromMMddyyWithSlash(it.getInqFromDate());
					   sqlString.append("AND MELDTE >= " + fromDT.getDateFormatyyyyMMdd() + " ");
				   }
				   if (!it.getInqToDate().trim().equals(""))
				   {
					   DateTime toDT = UtilityDateTime.getDateFromMMddyyWithSlash(it.getInqToDate());
					   sqlString.append("AND MELDTE <= " + toDT.getDateFormatyyyyMMdd() + " ");
				   }
				   if (!it.getInqTransactionType().equals(""))
					   sqlString.append("AND SUBSTRING(MELMAN,35,4) = '" + it.getInqTransactionType() + "' ");
				   if (it.getOrderBy().trim().equals("transactionDate") ||
					   it.getOrderBy().trim().equals(""))
					   sqlString.append(" ORDER BY MELDTE " + it.getOrderStyle().trim() + ", MELTME " + it.getOrderStyle().trim() + ", MELUSR, SUBSTRING(MELMAN,64,7), MELLOT ");
				   if (it.getOrderBy().trim().equals("user"))
					   sqlString.append(" ORDER BY MELUSR " + it.getOrderStyle().trim() + ", MELDTE, MELTME, SUBSTRING(MELMAN,64,7), MELLOT ");
				  if (it.getOrderBy().trim().equals("lot"))
					   sqlString.append(" ORDER BY MELLOT " + it.getOrderStyle().trim() + ", MELDTE, MELTME, SUBSTRING(MELMAN,64,7) ");
				   if (it.getOrderBy().trim().equals("error"))
					   sqlString.append(" ORDER BY MELETX " + it.getOrderStyle().trim() + ", MELDTE, MELTME, SUBSTRING(MELMAN,64,7), MELLOT ");
				   
					   
			   }
			   catch(Exception e)
			   {}
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement"
							 + " for request type " + inRequestType + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceTransactionError.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		
		return sqlString.toString();
	}

	/**
 *   Method Created 9/21/09 TWalton
 *   // Use to control the information retrieval
 *       Will only retieve Items that are included in the file
 * @param -- Send in a Vector 
 * 				(TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 *  Incoming Vector:
 *    Element 1 = String type of dropdown Values Include: 
 *    			"ddShortCut"
 */
public static Vector dropDownUser(String environment, 
								  Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();

	if (environment == null || environment.trim().equals(""))
		environment = "PRD";
	
	try { // get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		ddit = dropDownUser(environment, inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection for Stack 3-- ServiceTransactionError.dropDownUser: " + e);
			throwError.append("Error returning connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceTransactionError.");
			throwError.append("dropDownUser(String, Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	// return value
	return ddit;
}

/**
 *   Method Created 9/21/09 TWalton
 *   // Use to control the information retrieval
 *       Will only retieve Items that are included in the file
 * @param -- Send in a Vector 
 * 				(TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 *  Incoming Vector:
 *    Element 1 = String type of dropdown Values Include: 
 *    			"ddShortCut"
 */
private static Vector dropDownUser(String environment, 
								   Vector inValues, 
								   Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector ddit = new Vector();
	ResultSet rs = null;
	Statement listThem = null;
	
	try
	{
		try { // Get the list of Item Type Values - Along with Descriptions
			listThem = conn.createStatement();
			rs = listThem.executeQuery(buildSqlStatement(environment, ((String) inValues.elementAt(0) + "User"), inValues));
		} catch(Exception e) {
			throwError.append("Error occured Retrieving or Executing sql (User). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next() && throwError.toString().equals(""))
				{
					DropDownSingle dds = loadFieldsDropDownSingle(((String) inValues.elementAt(0) + "User"), rs);
					ddit.addElement(dds);
				}
			} catch(Exception e)
			{
				throwError.append(" Error occured while Building Vector from sql (User). " + e);
			}
		}
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		
		//close result sets / statements.
		try
		{
			if (rs != null)
				rs.close();
			
			if (listThem != null)
				listThem.close();
			
		} catch(Exception e){
			throwError.append("Error closing result set / statement." + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceTransactionError.");
			throwError.append("dropDownUser(");
			throwError.append("String, Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return ddit;
}

/**
 *   Method Created 9/21/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the 
 *              Vector which includes InqTransaction for Selection Criteria
 * @return BeanTransaction -- loaded with a Vector of TransactionError Objects
 * @throws Exception
 */
public static BeanTransaction listTransactionError(String environment,
												   Vector inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	BeanTransaction returnValue = new BeanTransaction();
	Connection conn = null;
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		returnValue = listTransactionError(environment, inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {

		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection for Stack 3 -- ServiceTransactionError.listTransactionError: " + e);
			throwError.append("Error closing connection. " + e);
		}
		
		// Log any errors..
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceTransaction.");
			throwError.append("listTransactionError(String,Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
 *  Method Created 9/21/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the 
 *              Vector which includes InqTransaction for Selection Criteria
 * @return BeanTransaction -- loaded with a Vector of TransactionError Objects
 */
private static BeanTransaction listTransactionError(String environment,
												    Vector inValues, 
												    Connection conn)
throws Exception
{
	// Load All information Relating List of Transaction Errors which should be returned
	StringBuffer throwError = new StringBuffer();
	BeanTransaction returnValue = new BeanTransaction();
	ResultSet rs = null; 
	Statement listThem = null; 
	
	try
	{
		try {
		   // Get a list of Transaction Errors based on inquiry criteria
		   listThem = conn.createStatement();
		   rs = listThem.executeQuery(buildSqlStatement(environment, "listTransactionError", inValues));
		 
		} catch(Exception e) {
		   	throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}
		 
		Vector listTransactionErrors = new Vector();
		 
		try
		{
		 	while (rs.next() && throwError.toString().equals(""))
		    {
		 		listTransactionErrors.addElement(loadFieldsTransactionError("listTransactionError", rs));
     		}// END of the WHILE Statement
		 	
		} catch(Exception e) {
			throwError.append(" Error occured while Building Vector from sql statement. " + e);
		}
		
		 returnValue.setListTransactionErrors(listTransactionErrors);
		 
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing result set (rs). " + e);
		}
		
		
		try
		{
			if (listThem != null)
				listThem.close();
		} catch(Exception e){
			throwError.append("Error closing statement (listThem). " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceTransactionError.");
			throwError.append("listTransactionError(String, Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}
	return returnValue;
}

/**
 ** Load class fields from result set.
 */
	
	public static TransactionError loadFieldsTransactionError(String requestType,
							          		        		  ResultSet rs)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		TransactionError returnValue = new TransactionError();
	
		try{ 
			if (requestType.equals("listTransactionError"))
			{
				try
				{
//					 HHPTDATA
					if (rs.getString("MELCDE").trim().equals("A"))
					   returnValue.setErrorBeforeAfter("After");
					if (rs.getString("MELCDE").trim().equals("B"))
					   returnValue.setErrorBeforeAfter("Before");
					returnValue.setSenderAddress(rs.getString("MELADR").trim());
					DateTime dt = UtilityDateTime.getDateFromyyyyMMdd(rs.getString("MELDTE"));
					DateTime tm = UtilityDateTime.getTimeFromhhmmss(rs.getString("MELTME"));
					dt.setTimeFormatAMPM(tm.getTimeFormatAMPM());
					dt.setTimeFormathhmmss(tm.getTimeFormathhmmss());
					dt.setTimeFormathhmmssColon(tm.getTimeFormathhmmssColon());
					returnValue.setTransactionDate(dt);
					returnValue.setUserProfile(rs.getString("MELUSR").trim());
					returnValue.setErrorCode(rs.getString("MELECD").trim());
					returnValue.setErrorText(rs.getString("MELETX").trim());
					returnValue.setWarehouseInfo(ServiceWarehouse.loadFieldsWarehouse("basicWarehouse", rs));
					returnValue.setLotNumber(rs.getString("MELLOT").trim());
					returnValue.setItemNumber(rs.getString("MELITM").trim());
					if (rs.getString("MMITDS") != null)
					   returnValue.setItemDescription(rs.getString("MMITDS").trim());
					returnValue.setTransactionType(rs.getString("TYPE")); 
					if (returnValue.getTransactionType().trim().equals("ME11"))
					{
						returnValue.setTransactionType("Production");
						returnValue.setOrderType("MO"); 
						returnValue.setOrderNumber(rs.getString("MO")); 
						returnValue.setTransactionQuantity(rs.getString("PRQTY")); 
						
					}
					if (returnValue.getTransactionType().trim().equals("ME12"))
						returnValue.setTransactionType("Transfer");
					
					try // from DPPNUSER
					{
						if (rs.getString("DPNUSRNAME") != null)
							returnValue.setUserLongName(rs.getString("DPNUSRNAME").trim());
					}
					catch(Exception e)
					{}
					if (returnValue.getUserLongName().trim().equals(""))
						returnValue.setUserProfile(rs.getString("MELUSR").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}
			}	
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Drop Down Single class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceTransactionError.");
			throwError.append("loadFieldsTransactionError(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

/**
 *   Method Created 9/22/09 TWalton
 *   // Use to control the information retrieval
 *       Will only retieve Items that are included in the file
 * @param -- Send in a Vector 
 * 				(TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 *  Incoming Vector:
 *    Element 1 = String type of dropdown Values Include: 
 *    			"ddShortCut"
 */
public static Vector dropDownWarehouse(String environment, 
								       Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();

	if (environment == null || environment.trim().equals(""))
		environment = "PRD";
	
	try { // get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		ddit = dropDownWarehouse(environment, inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection for Stack 3 -- ServiceTransactionError.dropDownWarehouse: " + e);
			throwError.append("Error returning connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceTransactionError.");
			throwError.append("dropDownWarehouse(String, Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	// return value
	return ddit;
}

/**
 *   Method Created 9/21/09 TWalton
 *   // Use to control the information retrieval
 *       Will only retieve Items that are included in the file
 * @param -- Send in a Vector 
 * 				(TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 *  Incoming Vector:
 *    Element 1 = String type of dropdown Values Include: 
 *    			"ddShortCut"
 */
private static Vector dropDownWarehouse(String environment, 
								        Vector inValues, 
								        Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector ddit = new Vector();
	ResultSet rs = null;
	Statement listThem = null;
	
	try
	{
		try { // Get the list of Item Type Values - Along with Descriptions
			listThem = conn.createStatement();
			rs = listThem.executeQuery(buildSqlStatement(environment, ((String) inValues.elementAt(0) + "Warehouse"), inValues));
		} catch(Exception e) {
			throwError.append("Error occured Retrieving or Executing sql (Warehouse). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next() && throwError.toString().equals(""))
				{
					DropDownSingle dds = loadFieldsDropDownSingle(((String) inValues.elementAt(0) + "Warehouse"), rs);
					ddit.addElement(dds);
				}
			} catch(Exception e)
			{
				throwError.append(" Error occured while Building Vector from sql (Warehouse). " + e);
			}
		}
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		
		//close result sets / statements.
		try
		{
			if (rs != null)
				rs.close();
			
			if (listThem != null)
				listThem.close();
			
		} catch(Exception e){
			throwError.append("Error closing result set / statement." + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceTransactionError.");
			throwError.append("dropDownWarehouse(");
			throwError.append("String, Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return ddit;
}

/**
 *   Method Created 9/22/09 TWalton
 *   // Use to control the information retrieval
 *       Will only retieve Items that are included in the file
 * @param -- Send in a Vector 
 * 				(TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 *  Incoming Vector:
 *    Element 1 = String type of dropdown Values Include: 
 *    			"ddShortCut"
 */
public static Vector dropDownItem(String environment, 
								  Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();

	if (environment == null || environment.trim().equals(""))
		environment = "PRD";
	
	try { // get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		ddit = dropDownItem(environment, inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection for Stack 3 -- ServiceTransactionError.dropDownItem: " + e);
			throwError.append("Error returning connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceTransactionError.");
			throwError.append("dropDownItem(String, Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	// return value
	return ddit;
}

/**
 *   Method Created 9/21/09 TWalton
 *   // Use to control the information retrieval
 *       Will only retieve Items that are included in the file
 * @param -- Send in a Vector 
 * 				(TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 *  Incoming Vector:
 *    Element 1 = String type of dropdown Values Include: 
 *    			"ddShortCut"
 */
private static Vector dropDownItem(String environment, 
								   Vector inValues, 
								   Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector ddit = new Vector();
	ResultSet rs = null;
	Statement listThem = null;
	
	try
	{
		try { // Get the list of Item Type Values - Along with Descriptions
			listThem = conn.createStatement();
			rs = listThem.executeQuery(buildSqlStatement(environment, ((String) inValues.elementAt(0) + "Item"), inValues));
		} catch(Exception e) {
			throwError.append("Error occured Retrieving or Executing sql (Warehouse). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next() && throwError.toString().equals(""))
				{
					DropDownSingle dds = loadFieldsDropDownSingle(((String) inValues.elementAt(0) + "Item"), rs);
					ddit.addElement(dds);
				}
			} catch(Exception e)
			{
				throwError.append(" Error occured while Building Vector from sql (Item). " + e);
			}
		}
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		
		//close result sets / statements.
		try
		{
			if (rs != null)
				rs.close();
			
			if (listThem != null)
				listThem.close();
			
		} catch(Exception e){
			throwError.append("Error closing result set / statement." + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceTransactionError.");
			throwError.append("dropDownItem(");
			throwError.append("String, Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return ddit;
}
}

