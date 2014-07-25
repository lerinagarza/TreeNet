package com.treetop.data;

import java.text.*;
import java.sql.*;
import java.util.*;
import javax.sql.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import com.treetop.utilities.*;


/**
 * Access to RDB file DBLIB/IDP2DESC
 *     New BOX  DBPRD/IDP2DESC
 **/
public class GeneralInfoDried {
	/**
	 * Drop down list for dried general descriptions.
	 *   Send in values NOT to Include.
	 *
	 * Creation date: (2/24/2005 2:58:38 PM)
	 */
	public static String buildDropDownFullForCode(Vector inList, 
	 											  Vector doNotInclude,
												  String inCode,
		                                          String inName, 
		                                          String inSelect) 
	{
		String dropDown     = "";
		String selected     = "";
		String selectOption = "";
		int descCount       = inList.size();
		int doNotIncludeCount = 0;
		try
		{
			doNotIncludeCount = doNotInclude.size();
		}
		catch(Exception e)
		{
		}		
	
		if (inSelect.equals("") || inSelect==null)
		    selectOption = "Select an Entry--->:";
		else {
			 if (inSelect.trim().equals("*all"))
			     selectOption = "*all";
		     else {
		     	if (inSelect.trim().equals("*ALL"))
			     	selectOption = "*All";
			    else
		         	selectOption = "Select a " + inSelect.trim() + "--->";
		     }
		}
		
		
		if (descCount > 0)
			{
	   		    for (int x = 0; x < descCount; x++)
	   		    {
		   		    GeneralInfoDried nextDesc = (GeneralInfoDried) inList.elementAt(x);
		   		    
					String dontInclude   = "N";
					if (doNotIncludeCount > 0)
					{
						for (int y = 0; y < doNotIncludeCount; y++)
						{
							String testValue = (String) doNotInclude.elementAt(y);
							if (testValue.trim().equals(nextDesc.getDescCode().trim()))
							{
							   dontInclude = "Y";
							   y = doNotIncludeCount;
							}
						}
					}
					if (dontInclude.equals("N"))
					{					
						if (inCode.trim().equals(nextDesc.getDescCode().trim()))
							selected = "' selected='selected'>";
						else
							selected = "'>";
			   		    
						dropDown = dropDown + "<option value='" + 
							nextDesc.getDescCode().trim() + selected +
							nextDesc.getDescFull().trim() + "&nbsp;";
					}
		   		}
	   		    if (!dropDown.equals(""))
	   		    {
		   		    dropDown = "<select name='" + inName.trim() + "'>" +
		   		    		   "<option value='None' selected>" + selectOption +
		   		    		   dropDown + "</select>";  	 
	   		    }
	  	  		
	  	   	}
	
		return dropDown; 
	}

	private String  descType;
	private String  descCode;
	private int   	plantNumber;
	private int     productLine;
	private String  descFull;
	private String  descShort;
	

	//**** For use in Main Method,
    // Constructor Methods and Lookup Methods     ****//
	   
    private static PreparedStatement findDescByRec;
	private static PreparedStatement findDescByRecAll;
    private static PreparedStatement findDescByCode;	
	private static PreparedStatement findDescByFull;	
	private static PreparedStatement findDescByShort;
	
	
	// Additional fields.
	
	private boolean persists = false;
	private static Connection connection;
/**
 * General descriptions constructor comment.
 */
public GeneralInfoDried() {
	
	if (connection == null)
		init();
}
/**
 * Instantiate the general description (dried) class.
 *
 * Creation date: (6/26/2003 10:36:39 AM)
 */
public GeneralInfoDried(String inDescType, String inDescCode)	      
	throws InstantiationException { 

	if (connection == null)
		init();
		
	ResultSet rs = null;
	
	try {
		findDescByRecAll.setString(1, inDescType);
		findDescByRecAll.setString(2, inDescCode);		
		
		rs = findDescByRecAll.executeQuery();
		
		if (rs.next() == false)
			throw new InstantiationException("The type: " + inDescType +
				                             " and code: " + inDescCode + " not found");	
			
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.GeneralInfoDried." +
			               "GeneralInfoDried(String,String) " + e);
		return;
	}
	
	loadFields(rs);	
}
/**
 * Drop down list for dried general descriptions.
 *
 * Creation date: (6/26/2003 1:01:38 PM)
 */
public static String buildDropDownFullForCode(Vector inList, String inCode,
	                                          String inName, String inSelect) 
{
	String dropDown     = "";
	String selected     = "";
	String selectOption = "";
	int descCount       = inList.size();
	

	if (inSelect.equals("") || inSelect==null)
	    selectOption = "Select an Entry--->:";
	else {
		 if (inSelect.trim().equals("*all"))
		     selectOption = "*all";
	     else {
	     	if (inSelect.trim().equals("*ALL"))
		     	selectOption = "*All";
		    else
	         	selectOption = "Select a " + inSelect.trim() + "--->";
	     }
	}
	
	
	if (descCount > 0)
		{
   		    for (int x = 0; x < descCount; x++)
   		    {
	   		    GeneralInfoDried nextDesc = (GeneralInfoDried) inList.elementAt(x);

	   		    if (inCode.trim().equals(nextDesc.getDescCode().trim()))
	   		    selected = "' selected='selected'>";
	   		    else
	   		    selected = "'>";
		   		    
	   		    dropDown = dropDown + "<option value='" + 
	   		    nextDesc.getDescCode().trim() + selected +
	   		    nextDesc.getDescFull() + "&nbsp;";
	   		}
   		    if (!dropDown.equals(""))
   		    {
	   		    dropDown = "<select name='" + inName.trim() + "'>" +
	   		    		   "<option value='None' selected>" + selectOption +
//	   		    		   dropDown + "</select>";  							wth 06/08/2011
	   		    		   dropDown +											//wth 06/08/2011
	   		    		   //"<select name='" + inName.trim() + "'>" +			//wth 06/08/2011
	   		    		   "<option value='None' >" + selectOption +	//wth 06/08/2011
	   		    		   "</select>";											//wth 06/08/2011
   		    }
  	  		
  	   	}

	return dropDown; 
}
/**
 * Set result set for all dried descriptions by the description code for the type code.
 *
 * Creation date: (6/26/2003 8:24:29 AM)
 */
public static Vector findDescByCode(String inType) {

	Vector           descList = new Vector();
	GeneralInfoDried desc     = new GeneralInfoDried();
	ResultSet        rs       = null;

	
	try {
		GeneralInfoDried.findDescByCode.setString(1, inType);
		
		rs = GeneralInfoDried.findDescByCode.executeQuery();

		try {
			while (rs.next())
			{
				GeneralInfoDried buildVector = new GeneralInfoDried();
				buildVector.loadFields(rs);
				descList.addElement(buildVector);
			}
			rs.close();
		}
		catch (Exception e){
			System.out.println("Exception error processing a result set " +
				               "(GeneralInfoDried.findDescByCode): " + e);
		}
		
	}
	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.GeneralInfoDried." +
			               "findDescByCode(): " + e);
	}
	
			
	return descList;	 
}
/**
 * Set result set for all dried descriptions by the full description for the type code.
 *
 * Creation date: (6/24/2003 8:24:29 AM)
 */
public static Vector findDescByFull(String inType) {

	Vector           descList = new Vector();
	GeneralInfoDried desc     = new GeneralInfoDried();
	ResultSet        rs       = null;

	
	try {
		GeneralInfoDried.findDescByFull.setString(1, inType);
		
		rs = GeneralInfoDried.findDescByFull.executeQuery();

		try {
			while (rs.next())
			{
				GeneralInfoDried buildVector = new GeneralInfoDried();
				buildVector.loadFields(rs);
				descList.addElement(buildVector);
			}
			rs.close();
		}
		catch (Exception e){
			System.out.println("Exception error processing a result set " +
				               "(GeneralInfoDried.findDescByFull): " + e);
		}
		
	}
	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.GeneralInfoDried." +
			               "findDescByFull(): " + e);
	}
	
			
	return descList;	 
}
/**
 * Set result set for a single dried description by the full record key.
 *
 * Creation date: (6/26/2003 8:24:29 AM)
 */
public static Vector findDescByRec(String inDescType,  String inDescCode) {	              
	            
	Vector           descList = new Vector();
	GeneralInfoDried desc     = new GeneralInfoDried();
	ResultSet        rs       = null;

	
	try {
		GeneralInfoDried.findDescByRec.setString(1, inDescType);
		GeneralInfoDried.findDescByRec.setString(2, inDescCode);
		
		
		rs = GeneralInfoDried.findDescByRec.executeQuery();

		try {
			while (rs.next())
			{
				GeneralInfoDried buildVector = new GeneralInfoDried();
				buildVector.loadFields(rs);
				descList.addElement(buildVector);
			}
			rs.close();
		}
		catch (Exception e){
			System.out.println("Exception error processing a result set " +
				               "(GeneralInfoDried.findDescByRec): " + e);
		}
		
	}
	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.GeneralInfoDried." +
			               "findDescByRec(): " + e);
	}
	
			
	return descList;	 
}
/**
 * Set result set for all dried descriptions by the short description for the type code.
 *
 * Creation date: (6/26/2003 8:24:29 AM)
 */
public static Vector findDescByShort(String inType) {

	Vector           descList = new Vector();
	GeneralInfoDried desc     = new GeneralInfoDried();
	ResultSet        rs       = null;

	
	try {
		GeneralInfoDried.findDescByShort.setString(1, inType);
		
		rs = GeneralInfoDried.findDescByShort.executeQuery();

		try {
			while (rs.next())
			{
				GeneralInfoDried buildVector = new GeneralInfoDried();
				buildVector.loadFields(rs);
				descList.addElement(buildVector);
			}
			rs.close();
		}
		catch (Exception e){
			System.out.println("Exception error processing a result set " +
				               "(GeneralInfoDried.findDescByShort): " + e);
		}
		
	}
	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.GeneralInfoDried." +
			               "findDescByShort(): " + e);
	}
	
			
	return descList;	 
}
/**
 * Retrieve the descriptive type code. 
 *
 * Creation date: (6/26/2003 4:45:28 PM)
 */
public String getDescCode() {

	return descCode;	
}
/**
 * Retrieve the full length descriptive text. 
 *
 * Creation date: (6/26/2003 4:45:28 PM)
 */
public String getDescFull() {

	return descFull;	
}
/**
 * Retrieve the short (abbreviated) length descriptive text. 
 *
 * Creation date: (6/26/2003 4:45:28 PM)
 */
public String getDescShort() {

	return descShort;	
}
/**
 * Retrieve the descriptive type. 
 *
 * Creation date: (6/26/2003 4:45:28 PM)
 */
public String getDescType() {

	return descType;	
}
/**
 * Retrieve the descriptive record plant number. 
 *
 * Creation date: (6/26/2003 4:45:28 PM)
 */
public int getPlantNumber() {

	return plantNumber;	
}
/**
 * Retrieve the descriptive record product line. 
 *
 * Creation date: (6/26/2003 4:45:28 PM)
 */
public int getProduceLine() {

	return productLine;	
}
/**
 * SQL definitions.
 *
 * Creation date: (6/26/2003 8:24:29 AM)
 */
public void init() {
	
	// Test for initial connection.

	if  (connection == null)
		connection = ConnectionStack.getConnection();
	     //connection = SQLConnect.connection;
	
 
	try {

		findDescByRec = connection.prepareStatement( 
			"SELECT * FROM DBPRD.IDP2DESC WHERE ID2DLT = ' ' AND ID2IND = ? AND ID2DCD = ?");
	// Get Information not based on Delete Code being ' '		
		findDescByRecAll = connection.prepareStatement( 
			"SELECT * FROM DBPRD.IDP2DESC " +
			" WHERE ID2IND = ? AND ID2DCD = ?");
					
		findDescByCode = connection.prepareStatement( 
			"SELECT * FROM DBPRD.IDP2DESC " +
			" WHERE ID2DLT = ' ' AND ID2IND = ?" +
			" ORDER BY ID2DCD");
		
		findDescByFull = connection.prepareStatement(
			"SELECT * FROM DBPRD.IDP2DESC " +
			" WHERE ID2DLT = ' ' AND ID2IND = ?" +
			" ORDER BY ID2DSC");

		findDescByShort = connection.prepareStatement(
			"SELECT * FROM DBPRD.IDP2DESC " +
			" WHERE ID2DLT = ' ' AND ID2IND = ?" +
			" ORDER BY ID2D08");

		ConnectionStack.returnConnection(connection);
		
	} catch (SQLException e) {
		System.out.println("SQL exception occured at com.treetop.data.GeneralInfo.init()" +
			               e);
	}
}
/**
 * Load fields from data base record.
 *
 * Creation date: (6/26/2003 8:24:29 AM)
 */
protected void loadFields(ResultSet rs) {

	try {
		
		descType     = rs.getString("ID2IND");
		descCode     = rs.getString("ID2DCD");		
		descFull     = rs.getString("ID2DSC");
		descShort    = rs.getString("ID2D08");
		plantNumber  = rs.getInt("ID2PLT");
		productLine  = rs.getInt("ID2PLN");
	
	}
	catch (Exception e)
	{
		System.out.println("SQL Exception at com.treetop.data.GeneralInfoDried" +
			               ".loadFields();" + e);
	}

	persists = true;
				
}
/**
 * General dried descriptive code file testing.
 *
 * Creation date: (6/26/2003 8:24:29 AM)
 */
public static void main(String[] args) {
	
	
	GeneralInfoDried descInfo = new GeneralInfoDried();
	

	// search descriptions. ----------------------

	try {

		Vector vector1 = GeneralInfoDried.findDescByCode("FV"); 
		System.out.println("First search successfull");	
		
		String dropDown = buildDropDownFullForCode(vector1, "", "dropdown", "Entry");


	} catch (Exception e) {
		System.out.println("search1 failed: " + e);

	}
	
	
}
}
