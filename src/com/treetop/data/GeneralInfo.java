package com.treetop.data;

import java.text.*;
import java.sql.*;
import java.util.*;
import javax.sql.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import com.treetop.utilities.ConnectionStack;


/**
 * Access to RDB file DBLIB/GNPADESC
 *        New BOX  DBPRD/GNPADESC
 *
 **/
public class GeneralInfo {
	/**
	 * Drop down list for general descriptions.
	 *
	 * Creation date: (6/24/2003 1:01:38 PM)
	 */
	public static String buildDropDownFullForKey1(Vector inList, String inKey1,
		                                          String inName, String inSelect)  
	{	
		     
		String dropDown     = "";
		String selected     = "";
		String selectOption = "";
		int descCount       = inList.size();
		
	
		if (inSelect.equals("") || inSelect==null)
		    selectOption = "Select an Entry--->:";
		else {
			 if (inSelect.trim().equals("*all") ||
			     inSelect.trim().equals("*All") ||
			     inSelect.trim().equals("*ALL"))
			     selectOption = inSelect.trim();
		     else {
	         	selectOption = inSelect.trim();
		     }
		}
		    
		
		if (descCount > 0)
			{
	   		    for (int x = 0; x < descCount; x++)
	   		    {
		   		    GeneralInfo nextDesc = (GeneralInfo) inList.elementAt(x);
	
		   		    if (inKey1.trim().equals(nextDesc.getKey1Value().trim()))
		   		    selected = "' selected='selected'>";
		   		    else
		   		    selected = "'>";
			   		    
		   		    dropDown = dropDown + "<option value='" + 
		   		    nextDesc.getKey1Value().trim() + selected +
		   		    nextDesc.getDescFull().trim() + "&nbsp;";
		   		}
	   		    if (!dropDown.equals(""))
	   		    {	  
	   		    	if (inSelect != null &&
	   		    	    inSelect.toLowerCase().equals("none"))
	   		    	{
						dropDown = "<select name='" + inName.trim() + "'>" +
								   dropDown + "</select>";
	   		    	}
	   		    	else
	   		    	{
	   		    		dropDown = "<select name='" + inName.trim() + "'>" +
		   		    			   "<option value='None' selected>" + selectOption +
		   		    			   dropDown + "</select>";
	   		    	}  	 
		        }
	  	  		
	  	   	}
	
		return dropDown; 
	}
	/**
	 * Set result set for all descriptions by each key by type code.
	 *
	 * Creation date: (6/13/2003 8:24:29 AM)
	 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
	 */
	public static Vector findDescByKey(String inType) {
	
		Vector      descList = new Vector();
		GeneralInfo desc     = new GeneralInfo();
		ResultSet   rs       = null;
	
		try
		{
		  PreparedStatement findIt = (PreparedStatement) findDescByKey.pop();
		  try
		  {
			 findIt.setString(1, inType);
			 rs = findIt.executeQuery();
		  }
		  catch(Exception e)
		  {
			 System.out.println("Exception error creating a Result Set at " +
						 "com.treetop.data.GeneralInfo.findDescByKey(String) " + e);
		  }
		  findDescByKey.push(findIt);	
		}
		catch(Exception e)
		{
		  System.out.println("Exception error pop/push of a Prepared Statement at" +
					  "com.treetop.data.GeneralInfo.findDescByKey(String) " + e);
		}
	
		try {
			while (rs.next())
			{
				GeneralInfo buildVector = new GeneralInfo();
				buildVector.loadFields(rs);
				descList.addElement(buildVector);
			}
			rs.close();
		}
		catch (Exception e){
			System.out.println("Exception error processing a result set " +
				               "(com.treetop.data.GeneralInfo.findDescByKey(String)): " + e);
			}
			
		return descList;	 
	}
	/**
	 *  This method will return a Vector of Strings....
	 *    element:  0 = Drop Down Selection for the Master List.
	 *              1 = Drop Down Selection for the Slave List.
	 *
	 *  If each piece is put into the JSP correctly, You will have
	 *    to choose from the first list, to retrieve the second.
	 * 
	 *    // The master all field, is a Y or N field and should be
	 *    //  Y if you want to be able to select ALL 
	 * Creation date: (6/13/2005 3:16:44 PM  Twalton)
	 */
	public static Vector buildDropDownDualList(String masterName,
											   String masterDescType,
											   String masterValue,
		                                       String slaveName,
		                                       String slaveDescType,
		                                       String slaveValue,
		                                       String masterAll,
		                                       String slaveAll) 
	{
		GeneralInfo initialize = new GeneralInfo();
		Vector    dropDownInfo  = new Vector();
		ResultSet rs            = null;
		String listMasterDisplay = "";
		String listMasterValue   = "";
		
		String    slaveInfoLists   = "";
		String    infoLists     = "<script language=\"javascript\"> " +
								  "var lists = new Array();" ;
		String    masterDropDown = "<select name=\"" + masterName + "\" size=1 " +
										"onchange=\"change" + masterName + "(this)\">";
        if (masterAll.toUpperCase().equals("Y"))
        {
           masterDropDown = masterDropDown +
		                    "<option value=\"*all\">*all";
		   slaveInfoLists = slaveInfoLists +
				" lists['*all'] = new Array(); " +
				" lists['*all'][0]" +
				" = new Array( '*all'); " +
				" lists['*all'][1]" +
				" = new Array( '*all'); ";	
		   listMasterDisplay    = "'*all'";
		   listMasterValue      = "'*all'";		
					
        }		                    																		 
		
		masterDescType = "ACU";
		slaveDescType  = "ACV";  
	try
	{
	if (masterDescType != null &&
	    !masterDescType.trim().equals("") &&
	    slaveDescType != null &&
	    !slaveDescType.trim().equals(""))		
	{	
	  try
	  {
		PreparedStatement findIt = (PreparedStatement) findDescDualByKey.pop();
		try
		{
		   findIt.setString(1, masterDescType);
		   findIt.setString(2, slaveDescType);
		   rs = findIt.executeQuery();
		}
		catch(Exception e)
		{
		   System.out.println("Exception error creating a Result Set at " +
					   "com.treetop.data.GeneralInfo.buildDropDownDualList(String, String, String, String) " + e);
		}
		findDescDualByKey.push(findIt);	
	  }
	  catch(Exception e)
	  {
		System.out.println("Exception error pop/push of a Prepared Statement at" +
					"com.treetop.data.GeneralInfo.buildDropDownDualList(String, String, String, String) " + e);
	  }
		
		String selected = "selected=\"selected\" ";									    			                    				
								
	    String saveMasterValue = "";
	    String saveMasterDesc  = "";
		String slaveValues  = "'*all'";
		String slaveDisplay = "'*all'";
		if (slaveAll != null &&
		    slaveAll.equals("N"))
		{    
		    slaveValues = "";
		    slaveDisplay = "";
		}    
		    
		
		try {
			while (rs.next())
			{
				GeneralInfo buildVector = new GeneralInfo();
				buildVector.loadFields(rs);
				
				if (buildVector.getDescType().equals(masterDescType))
				{
				   // Build the list of Master Codes	 
				  
				    if (!saveMasterValue.equals(""))
				    {
				    	if (!slaveDisplay.equals(""))
				    	{
				    		slaveInfoLists = slaveInfoLists +
								   " lists['" + saveMasterValue +
								   "'] = new Array(); " +
								   " lists['" + saveMasterValue +
								   "'][0]" +
								   " = new Array( " +
								   slaveDisplay + "); " +
								   " lists['" + saveMasterValue +
								   "'][1]" +
								   " = new Array(" +
								   slaveValues + "); ";
								   
							masterDropDown = masterDropDown +
											 "<option value=\"" + saveMasterValue + "\" " +
															   selected + ">" +
											   saveMasterValue + " - " +
											   saveMasterDesc.trim();
				    	}
						slaveValues = "'*all'";
						slaveDisplay = "'*all'";
						if (slaveAll != null &&
							slaveAll.equals("N"))
					   {
					   	slaveValues = "";
					   	slaveDisplay = "";
					   }
				    }
					
				    selected = "";
					if (masterValue != null &&
						masterValue.equals(buildVector.getKey1Value()))	
					  selected = "selected=\"selected\" ";
				   
				    if (!listMasterDisplay.equals(""))
				      listMasterDisplay = listMasterDisplay + ",";
				      
					listMasterDisplay = listMasterDisplay + " '" +
										buildVector.getDescSmall().trim() + " - " +
										buildVector.getDescFull().trim() + "' ";
					if (!listMasterValue.equals(""))
					  listMasterValue = listMasterValue + ",";
					  
					listMasterValue   = listMasterValue + " '" +
									    buildVector.getKey1Value() + "' "; 	
					
					saveMasterValue = buildVector.getKey1Value();
					saveMasterDesc  = buildVector.getDescFull().trim();				    
				}
				if (buildVector.getDescType().equals(slaveDescType))
				{
				   // Build the list of Slave Codes
				     // Ties the Master to the Slave	 
					if (!slaveDisplay.equals(""))
					  slaveDisplay = slaveDisplay + ",";
					slaveDisplay = slaveDisplay + "'" +
								   buildVector.getDescSmall().trim() + " - " +
								   buildVector.getDescFull().trim() + "' ";
				    if (!slaveValues.equals(""))
				      slaveValues = slaveValues + ",";
					slaveValues  = slaveValues + "'" +
									buildVector.getKey2Value() + "' "; 	
				}			
			}
			rs.close();
			if (!listMasterDisplay.equals(""))
			{
				slaveInfoLists = slaveInfoLists +
					   " lists['" + saveMasterValue +
					   "'] = new Array(); " +
					   " lists['" + saveMasterValue +
					   "'][0]" +
					   " = new Array( " +
					   slaveDisplay + "); " +
					   " lists['" + saveMasterValue +
					   "'][1]" +
					   " = new Array(" +
					   slaveValues + "); ";
					   
				masterDropDown = masterDropDown +
								 "<option value=\"" + saveMasterValue + "\" " +
												   selected + ">" +
								   saveMasterValue + " - " +
								   saveMasterDesc.trim();
			   infoLists = infoLists +
					   " lists['*all'] = new Array(); " +
					   " lists['*all'][0]" +
					   " = new Array( " +
					   listMasterDisplay + "); " +
					   " lists['*all'][1]" +
					   " = new Array( " +
					   listMasterValue + "); " +
					   slaveInfoLists;
			}			
			
		}
		catch (Exception e){
			System.out.println("Exception error processing a result set " +
							   "(GeneralInfo.buildDropDownDualList): " + e);
		}
		
		infoLists = infoLists +
					   "</script>";
		
		masterDropDown = masterDropDown +
					   "</select>";
		               
		dropDownInfo.addElement(masterDropDown);
		dropDownInfo.addElement(infoLists);
		
	}	
	}
	catch(Exception e)
	{
		System.out.println("Exception Caught within :" +
							   "com.treetop.data.GeneralInfo." +
							   ":buildDropDownDualList() " + e);
	}
				
		return dropDownInfo; 
	}
	/**
	 * Drop down list for general descriptions.
	 *   Send in values NOT to Include.
	 *
	 * Creation date: (12/28/2003 1:01:38 PM)
	 */
	public static String buildDropDownFullForKey1(Vector inList,
										 	      Vector doNotInclude, 
										          String inKey1,
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
			 if (inSelect.trim().equals("*all") ||
			     inSelect.trim().equals("*All") ||
			     inSelect.trim().equals("*ALL"))
			     selectOption = inSelect.trim();
		     else {
	         	selectOption = inSelect.trim();
		     }
		}
		    
		
		if (descCount > 0)
			{
	   		    for (int x = 0; x < descCount; x++)
	   		    {
		   		    GeneralInfo nextDesc = (GeneralInfo) inList.elementAt(x);
					String dontInclude   = "N";
					if (doNotIncludeCount > 0)
					{
						for (int y = 0; y < doNotIncludeCount; y++)
						{
							String testValue = (String) doNotInclude.elementAt(y);
							if (testValue.trim().equals(nextDesc.getKey1Value().trim()))
							{
							   dontInclude = "Y";
							   y = doNotIncludeCount;
							}
						}
					}
					if (dontInclude.equals("N"))
					{					
		   		    	if (inKey1.trim().equals(nextDesc.getKey1Value().trim()))
		   		    		selected = "' selected='selected'>";
		   		    	else
		   		    		selected = "'>";
			   		    
		   		    	dropDown = dropDown + "<option value='" + 
		   		    		nextDesc.getKey1Value().trim() + selected +
		   		    		nextDesc.getDescFull().trim() + "&nbsp;";
					}
		   		}
	   		    if (!dropDown.equals(""))
	   		    {	  
	   		    	if (inSelect != null &&
	   		    	    inSelect.toLowerCase().equals("none"))
	   		    	{
						dropDown = "<select name='" + inName.trim() + "'>" +
								   dropDown + "</select>";
	   		    	}
	   		    	else
	   		    	{
	   		    		dropDown = "<select name='" + inName.trim() + "'>" +
		   		    			   "<option value='None' selected>" + selectOption +
		   		    			   dropDown + "</select>";
	   		    	}  	 
		        }
	  	  		
	  	   	}
	
		return dropDown; 
	}

	private String  descType;
	private int     descCompany;
	private String  key1Value;
	private String  key2Value;
	private String  key3Value;
	private String  key4Value;
	private String  descFull;
	private String  descShort;
	private String  descSmall;
	

	
	// Changed from Prepared Statements to
	//    Stacked Prepared Statements 6/14/05 twalton
	
	// Misc. Fields
	private static boolean generalInfoPersists = false;	
	private static String library  = "DBPRD.";   
	
	// Stacked Prepared Statements
	private static Stack findDescByRec       = null;
	private static Stack findDescCompByRec   = null;	
	
	private static Stack findDescByKey       = null;
	private static Stack findDescByKey1      = null;
	private static Stack findDescCompByKey   = null;
		
	private static Stack findDescDualByKey   = null;	
	
	private static Stack findDescByFull      = null;
	private static Stack findDescCompByFull  = null;	
	
	private static Stack findDescByShort     = null;
	private static Stack findDescCompByShort = null;	
	
	private static Stack findDescByText      = null;
	private static Stack findDescCompByText  = null;	
	
	private static Stack findDescBySeq       = null;
	private static Stack findDescCompBySeq   = null;	
	
	private static Stack findDescShort       = null;
	private static Stack findDescShortComp   = null;	
/**
 * General descriptions constructor comment.
 */
public GeneralInfo() {
	
	super();	
	init();
}
/**
 * Instantiate the general description class.
 *
 * Creation date: (6/23/2003 10:36:39 AM)
 *     Redo with Stack 6/15/05 Twalton 
 */
public GeneralInfo(String inDescType,  int inDescCompany,
	               String inKey1Value, String inKey2Value,
	               String inKey3Value, String inKey4Value)
	throws InstantiationException { 

   String eMessage = "";
   try
   {
	  init();
	  ResultSet rs = null;
	  try
	  {   
	  	 PreparedStatement findIt = (PreparedStatement) findDescCompByRec.pop();
	  	 try
	  	 {
			findIt.setString(1, inDescType);
			findIt.setInt(2, inDescCompany);
			findIt.setString(3, inKey1Value);
			findIt.setString(4, inKey2Value);
			findIt.setString(5, inKey3Value);
			findIt.setString(6, inKey4Value);	
			
			rs = findIt.executeQuery();
	  	 }
	  	 catch(Exception e)
	  	 {
	  	 	eMessage = eMessage +
	  	 				"Exception error creating a Result Set at " +
	  	 				"com.treetop.data.GeneralInfo(String, int, String, String, String, String) " + e;
	  	 }
	  	 findDescCompByRec.push(findIt);	
	  }
	  catch(Exception e)
	  {
	  	eMessage = eMessage +
	  				"Exception error pop/push of a Prepared Statement at" +
	  				"com.treetop.data.GeneralInfo(String, int, String, String, String, String) " + e;
	  }
	  if (rs.next())
		loadFields(rs);	
	  else
	    eMessage = eMessage + "The type: " + inDescType + " not found";
	  rs.close();
   }
   catch(Exception e)
   {
   	   eMessage = eMessage + "Exception error: Unable to Build Instance, at " +
   	   			  "com.treetop.data.GeneralInfo(String, int, String, String, String, String) " +
   	   			  e;
   }
   if (!eMessage.equals(""))
   {
   	   throw new InstantiationException(eMessage);
   }
   	
}
/**
 * Instantiate the general description class.
 *
 * Creation date: (6/23/2003 10:36:39 AM)
 *     Redo with Stack 6/15/05 Twalton
 */
public GeneralInfo(String inDescType,  
	               String inKey1Value, String inKey2Value,
	               String inKey3Value, String inKey4Value)
	throws InstantiationException { 


		String eMessage = "";
		try
		{
		   init();
		   ResultSet rs = null;
		   try
		   {   
			  PreparedStatement findIt = (PreparedStatement) findDescByRec.pop();
			  try
			  {
				 findIt.setString(1, inDescType);
				 findIt.setString(2, inKey1Value);
				 findIt.setString(3, inKey2Value);
				 findIt.setString(4, inKey3Value);
				 findIt.setString(5, inKey4Value);	
			
				 rs = findIt.executeQuery();
			  }
			  catch(Exception e)
			  {
				 eMessage = eMessage +
							 "Exception error creating a Result Set at " +
							 "com.treetop.data.GeneralInfo(String, String, String, String, String) " + e;
			  }
			findDescByRec.push(findIt);	
		   }
		   catch(Exception e)
		   {
			 eMessage = eMessage +
						 "Exception error pop/push of a Prepared Statement at" +
						 "com.treetop.data.GeneralInfo(String, String, String, String, String) " + e;
		   }
		   if (rs.next())
			 loadFields(rs);	
		   else
			 eMessage = eMessage + "The type: " + inDescType + " not found";
			rs.close();
	  
		}
		catch(Exception e)
		{
			eMessage = eMessage + "Exception error: Unable to Build Instance, at " +
					   "com.treetop.data.GeneralInfo(String, String, String, String, String) " +
					   e;
		}
		if (!eMessage.equals(""))
		{
			throw new InstantiationException(eMessage);
		}

}
/**
 * Drop down list for general descriptions.
 *    Of the key2 Values.
 *
 * Creation date: (6/29/2005 11:15:38 AM)
 */
public static String buildDropDownFullForKey2(Vector inList, String inKey2,
	                                          String inName, String inSelect)  
{	
	     
	String dropDown     = "";
	String selected     = "";
	String selectOption = "";
	int descCount       = inList.size();
	

	if (inSelect.equals("") || inSelect==null)
	    selectOption = "Select an Entry--->:";
	else {
		 if (inSelect.trim().equals("*all") ||
		     inSelect.trim().equals("*All") ||
		     inSelect.trim().equals("*ALL"))
		     selectOption = inSelect.trim();
	     else {
         	selectOption = inSelect.trim();
	     }
	}
	    
	
	if (descCount > 0)
		{
   		    for (int x = 0; x < descCount; x++)
   		    {
	   		    GeneralInfo nextDesc = (GeneralInfo) inList.elementAt(x);

	   		    if (inKey2.trim().equals(nextDesc.getKey2Value().trim()))
	   		    selected = "' selected='selected'>";
	   		    else
	   		    selected = "'>";
		   		    
	   		    dropDown = dropDown + "<option value='" + 
	   		    nextDesc.getKey2Value().trim() + selected +
	   		    nextDesc.getDescFull().trim() + "&nbsp;";
	   		}
   		    if (!dropDown.equals(""))
   		    {	  
   		    	if (inSelect != null &&
   		    	    inSelect.toLowerCase().equals("none"))
   		    	{
					dropDown = "<select name='" + inName.trim() + "'>" +
							   dropDown + "</select>";
   		    	}
   		    	else
   		    	{
   		    		dropDown = "<select name='" + inName.trim() + "'>" +
	   		    			   "<option value='None' selected>" + selectOption +
	   		    			   dropDown + "</select>";
   		    	}  	 
	        }
  	  		
  	   	}

	return dropDown; 
}
/**
 * Drop down list for general descriptions.
 * Use Descriptive Key1 and Descriptive Ket2.
 * The return value will be GNAD20.
 * 
 *
 * Creation date: (6/24/2003 1:01:38 PM)
 */
public static String buildDropDownFullForKey1Key2R20(
										Vector inList, 
										String inKey,
										String inName, 
										String inSelect)  
{	
	     
	String dropDown     = "";
	String selected     = "";
	String selectOption = "";
	int descCount       = inList.size();
	

	if (inSelect.equals("") || inSelect==null)
		selectOption = "Select an Entry--->:";
	else {
		 if (inSelect.trim().equals("*all") ||
			 inSelect.trim().equals("*All") ||
			 inSelect.trim().equals("*ALL"))
			 selectOption = inSelect.trim();
		 else {
			selectOption = inSelect.trim();
		 }
	}
	    
	
	if (descCount > 0)
		{
			for (int x = 0; x < descCount; x++)
			{
				GeneralInfo nextDesc = (GeneralInfo) inList.elementAt(x);
				String test = nextDesc.getKey1Value();
				test = test + nextDesc.getKey2Value();

				if (inKey.trim().equals(test))
				selected = "' selected='selected'>";
				else
				selected = "'>";
		   		    
				dropDown = dropDown + "<option value='" + 
				nextDesc.getDescFull().trim() + " " + 
				selected +
				nextDesc.getKey1Value().trim() + " " +
				nextDesc.getKey2Value().trim() + " - " +
				nextDesc.getDescFull().trim() + "&nbsp;";
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
/**
 * Drop down list for general descriptions.
 *
 * Creation date: (6/24/2003 1:01:38 PM)
 */
public static String buildDropDownFullForKey1Key2(
										Vector inList, 
										String inKey,
										String inName, 
										String inSelect)  
{	
	     
	String dropDown     = "";
	String selected     = "";
	String selectOption = "";
	int descCount       = inList.size();
	

	if (inSelect.equals("") || inSelect==null)
		selectOption = "Select an Entry--->:";
	else {
		 if (inSelect.trim().equals("*all") ||
			 inSelect.trim().equals("*All") ||
			 inSelect.trim().equals("*ALL"))
			 selectOption = inSelect.trim();
		 else {
			selectOption = inSelect.trim();
		 }
	}
	    
	
	if (descCount > 0)
		{
			for (int x = 0; x < descCount; x++)
			{
				GeneralInfo nextDesc = (GeneralInfo) inList.elementAt(x);
				String test = nextDesc.getKey1Value();
				test = test + nextDesc.getKey2Value();

				if (inKey.trim().equals(test))
				selected = "' selected='selected'>";
				else
				selected = "'>";
		   		    
				dropDown = dropDown + "<option value='" + 
				nextDesc.getKey1Value().trim() + " " +
				nextDesc.getKey2Value().trim() + 
				selected +
				nextDesc.getKey1Value().trim() + " " +
				nextDesc.getKey2Value().trim() + " - " +
				nextDesc.getDescFull().trim() + "&nbsp;";
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
/**
 * Drop down list for general descriptions.
 *
 * Creation date: (6/24/2003 1:01:38 PM)
 */
public static String buildDropDownFullForShort(Vector inList, String inShort,
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
		    GeneralInfo nextDesc = (GeneralInfo) inList.elementAt(x);
				
	   		if (inShort.trim().equals(nextDesc.getDescShort().trim()))
	   		selected = "' selected='selected'>";
	   		else
	   		selected = "'>";
		   		    
	   		dropDown = dropDown + "<option value='" + 
	   		nextDesc.getDescShort().trim() + selected +
	   		nextDesc.getDescFull().trim() + "&nbsp;";
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
/**
 * Set result set for all descriptions by the full description for the type code.
 *
 * Creation date: (6/24/2003 8:24:29 AM)
 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
 */
public static Vector findDescByFull(String inType) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;

	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescByFull.pop();
	  try
	  {
		 findIt.setString(1, inType);
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescByFull(String) " + e);
	  }
	  findDescByFull.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescByFull(String) " + e);
	}
		
	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescByFull(String)): " + e);
	}
			
	return descList;	 
}
/**
 * Set result set for all descriptions by the full description for the type code and company.
 *
 * Creation date: (6/24/2003 8:24:29 AM)
 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
 */
public static Vector findDescByFull(String inType, int inCompany) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;

	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescCompByFull.pop();
	  try
	  {
		 findIt.setString(1, inType);
		 findIt.setInt(2, inCompany);	 
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescByFull(String, int) " + e);
	  }
	  findDescCompByFull.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescByFull(String, int) " + e);
	}
	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescByFull(String, int)): " + e);
	}
			
	return descList;	 
}
/**
 * Set result set for all descriptions by each key by type code and Key 1.
 *
 * Creation date: (6/29/2005 11:14:29 AM)
 */
public static Vector findDescByKey1(String inType, String inKey1) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;

	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescByKey1.pop();
	  try
	  {
		 findIt.setString(1, inType);
		 findIt.setString(2, inKey1);
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescByKey1(String, String) " + e);
	  }
	  findDescByKey1.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescByKey(String, String) " + e);
	}

	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescByKey1(String)): " + e);
		}
		
	return descList;	 
}
/**
 * Set result set for all descriptions by each key by type code and company.
 *
 * Creation date: (6/13/2003 8:24:29 AM)
 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
 */
public static Vector findDescByKey(String inType, int inCompany) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;
	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescCompByKey.pop();
	  try
	  {
		 findIt.setString(1, inType);
		 findIt.setInt(2, inCompany);	 
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescByKey(String, int) " + e);
	  }
	  findDescCompByKey.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescByKey(String, int) " + e);
	}
	
	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescByKey(String, int)): " + e);
	}
			
	return descList;	 
}
/**
 * Set result set for a single description by all full record key and company.
 *
 * Creation date: (6/24/2003 8:24:29 AM)
 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
 */
public static Vector findDescByRec(String inDescType,  int inDescCompany,
	                               String inKey1Value, String inKey2Value,
	                               String inKey3Value, String inKey4Value) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;

	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescCompByRec.pop();
	  try
	  {
		 findIt.setString(1, inDescType);
		 findIt.setInt(2, inDescCompany);
		 findIt.setString(3, inKey1Value);
		 findIt.setString(4, inKey2Value);
		 findIt.setString(5, inKey3Value);
		 findIt.setString(6, inKey4Value);	 
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescByRec(String, int, String, String, String, String) " + e);
	  }
	  findDescCompByRec.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescByRec(String, int, String, String, String, String) " + e);
	}

	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescByRec(String, int, String, String, String, String)): " + e);
	}
			
	return descList;	  
}
/**
 * Set result set for a single description by all full record key.
 *
 * Creation date: (6/24/2003 8:24:29 AM)
 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
 */
public static Vector findDescByRec(String inDescType,
	               				   String inKey1Value, String inKey2Value,
	                               String inKey3Value, String inKey4Value) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;

	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescByRec.pop();
	  try
	  {
		 findIt.setString(1, inDescType);
		 findIt.setString(2, inKey1Value);
		 findIt.setString(3, inKey2Value);
		 findIt.setString(4, inKey3Value);
		 findIt.setString(5, inKey4Value);	
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescByRec(String, String, String, String, String) " + e);
	  }
	  findDescByRec.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescByRec(String, String, String, String, String) " + e);
	}

	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescByRec(String, String, String, String, String)): " + e);
	}
		
	return descList;	  
}
/**
 * Set result set for all descriptions by the sequence number for the type code.
 *
 * Creation date: (6/24/2003 8:24:29 AM)
 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
 */
public static Vector findDescBySeq(String inType) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;

	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescBySeq.pop();
	  try
	  {
		 findIt.setString(1, inType);
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescBySeq(String) " + e);
	  }
	  findDescBySeq.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescBySeq(String) " + e);
	}

	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescBySeq(String)): " + e);
	}
			
	return descList;	 
}
/**
 * Set result set for all descriptions by the sequence number for the type code and company.
 *
 * Creation date: (6/24/2003 8:24:29 AM)
 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
 */
public static Vector findDescBySeq(String inType, int inCompany) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;

	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescCompBySeq.pop();
	  try
	  {
		 findIt.setString(1, inType);
		 findIt.setInt(2, inCompany);
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescBySeq(String, int) " + e);
	  }
	  findDescCompBySeq.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescBySeq(String, int) " + e);
	}

	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescBySeq(String, int)): " + e);
	}
			
	return descList;	 
}
/**
 * Set result set for all descriptions by the short description for the type code.
 *
 * Creation date: (6/24/2003 8:24:29 AM)
 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
 */
public static Vector findDescByShort(String inType) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;

	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescByShort.pop();
	  try
	  {
		 findIt.setString(1, inType);
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescByShort(String) " + e);
	  }
	  findDescByShort.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescByShort(String) " + e);
	}

	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescByShort(String)): " + e);
	}
			
	return descList;	 
}
/**
 * Set result set for all descriptions by the short description for the type code and company.
 *
 * Creation date: (6/24/2003 8:24:29 AM)
 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
 */
public static Vector findDescByShort(String inType, int inCompany) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;
	
	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescCompByShort.pop();
	  try
	  {
		 findIt.setString(1, inType);
		 findIt.setInt(2, inCompany);
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescByShort(String, int) " + e);
	  }
	  findDescCompByShort.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescByShort(String, int) " + e);
	}	

	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescByShort(String, int)): " + e);
	}
			
	return descList;	 
}
/**
 * Set result set to a text description for the type code and company.
 *
 * Creation date: (6/24/2003 8:24:29 AM)
 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
 */
public static Vector findDescByText(String inType, int inCompany, String inText) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;

	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescCompByText.pop();
	  try
	  {
		 findIt.setString(1, inType);
		 findIt.setInt(2, inCompany);
		 findIt.setString(3, inText.toUpperCase());
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescByText(String, int, String) " + e);
	  }
	  findDescCompByText.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescByText(String, int, String) " + e);
	}	

	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescByText(String, int, String)): " + e);
	}
			
	return descList;	 
}
/**
 * Set result set to a text description for the type code.
 *
 * Creation date: (6/24/2003 8:24:29 AM)
 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
 */
public static Vector findDescByText(String inType, String inText) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;

	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescByText.pop();
	  try
	  {
		 findIt.setString(1, inType);
		 findIt.setString(2, inText.toUpperCase());
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescByText(String, String) " + e);
	  }
	  findDescByText.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescByText(String, String) " + e);
	}	

	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescByText(String, String)): " + e);
	}
			
	return descList;	 
}
/**
 * Set result set for a full description using the type code and short description.
 *
 * Creation date: (6/25/2003 8:24:29 AM)
 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
 */
public static Vector findDescShort(String inType, String inShort) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;

	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescShort.pop();
	  try
	  {
		 findIt.setString(1, inType);
		 findIt.setString(2, inShort);
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescShort(String, String) " + e);
	  }
	  findDescShort.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescShort(String, String) " + e);
	}	

	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescShort(String, String)): " + e);
	}
			
	return descList;	 
}
/**
 * Set result set for a full description using the type code and short description and company.
 *
 * Creation date: (6/25/2003 8:24:29 AM)
 *     Redo with Stacked Prepared Statements 6/15/05 Twalton 
 */
public static Vector findDescShortComp(String inType, String inShort, int inCompany) {

	Vector      descList = new Vector();
	GeneralInfo desc     = new GeneralInfo();
	ResultSet   rs       = null;

	try
	{
	  PreparedStatement findIt = (PreparedStatement) findDescShortComp.pop();
	  try
	  {
		 findIt.setString(1, inType);
		 findIt.setString(2, inShort);
		 findIt.setInt(3, inCompany);
		 rs = findIt.executeQuery();
	  }
	  catch(Exception e)
	  {
		 System.out.println("Exception error creating a Result Set at " +
					 "com.treetop.data.GeneralInfo.findDescShort(String, String, int) " + e);
	  }
	  findDescShortComp.push(findIt);	
	}
	catch(Exception e)
	{
	  System.out.println("Exception error pop/push of a Prepared Statement at" +
				  "com.treetop.data.GeneralInfo.findDescShort(String, String, int) " + e);
	}	

	try {
		while (rs.next())
		{
			GeneralInfo buildVector = new GeneralInfo();
			buildVector.loadFields(rs);
			descList.addElement(buildVector);
		}
		rs.close();
	}
	catch (Exception e){
		System.out.println("Exception error processing a result set " +
			               "(com.treetop.data.GeneralInfo.findDescShort(String, String, int)): " + e);
	}
		
			
	return descList;	 
}
/**
 * Retrieve the descriptive type company number. 
 *
 * Creation date: (6/23/2003 4:45:28 PM)
 */
public int getDescCompany() {

	return descCompany;	
}
/**
 * Retrieve the full length descriptive text. 
 *
 * Creation date: (6/23/2003 4:45:28 PM)
 */
public String getDescFull() {

	return descFull;	
}
/**
 * Retrieve the short (abbreviated) length descriptive text. 
 *
 * Creation date: (6/23/2003 4:45:28 PM)
 */
public String getDescShort() {

	return descShort;	
}
/**
 * Retrieve the short (abbreviated) length descriptive text. 
 *
 * Creation date: (6/23/2003 4:45:28 PM)
 */
public String getDescSmall() {

	return descSmall;	
}
/**
 * Retrieve the descriptive type code. 
 *
 * Creation date: (6/23/2003 4:45:28 PM)
 */
public String getDescType() {

	return descType;	
}
/**
 * Retrieve the first descriptive key value. 
 *
 * Creation date: (6/23/2003 4:45:28 PM)
 */
public String getKey1Value() {

	return key1Value;	
}
/**
 * Retrieve the second descriptive key value. 
 *
 * Creation date: (6/23/2003 4:45:28 PM)
 */
public String getKey2Value() {

	return key2Value;	
}
/**
 * Retrieve the third descriptive key value. 
 *
 * Creation date: (6/23/2003 4:45:28 PM)
 */
public String getKey3Value() {

	return key3Value;	
}
/**
 * Retrieve the fourth descriptive key value. 
 *
 * Creation date: (6/23/2003 4:45:28 PM)
 */
public String getKey4Value() {

	return key4Value;	
}
/**
 * SQL definitions.
 *
 * Creation date: (6/23/2003 8:24:29 AM)
 */
public void init() {
	
 
		try {
			// Test for initial connection.
			
			if (generalInfoPersists == false) {	
				generalInfoPersists = true;						
		
			// Get Connections from the Connection Pool
			//com.treetop.ConnectionPool connectionPool = new com.treetop.ConnectionPool();
			Connection conn1  = ConnectionStack.getConnection();
			Connection conn2  = ConnectionStack.getConnection();
			Connection conn3  = ConnectionStack.getConnection();
			Connection conn4  = ConnectionStack.getConnection();
			Connection conn5  = ConnectionStack.getConnection();
			Connection conn6  = ConnectionStack.getConnection();
			Connection conn7  = ConnectionStack.getConnection();
			Connection conn8  = ConnectionStack.getConnection();
			Connection conn9  = ConnectionStack.getConnection();
			Connection conn10  = ConnectionStack.getConnection();
		// Find Specific Record with ALL the Keys // Not including Company
			String findIt = 
						"SELECT * FROM DBPRD.GNPADESC " +
						" WHERE " +
						"        GNATYP = ?  AND GNAKY1 = ?" +
						" AND    GNAKY2 = ?  AND GNAKY3 = ?" +
						" AND    GNAKY4 = ?";
						
			PreparedStatement ps1  = conn1.prepareStatement(findIt);	 				         
			PreparedStatement ps2  = conn2.prepareStatement(findIt);	
			PreparedStatement ps3  = conn3.prepareStatement(findIt);	
			PreparedStatement ps4  = conn4.prepareStatement(findIt);	
			PreparedStatement ps5  = conn5.prepareStatement(findIt);	
			PreparedStatement ps6  = conn6.prepareStatement(findIt);	 				         
			PreparedStatement ps7  = conn7.prepareStatement(findIt);	
			PreparedStatement ps8  = conn8.prepareStatement(findIt);	
			PreparedStatement ps9  = conn9.prepareStatement(findIt);	
			PreparedStatement ps10 = conn10.prepareStatement(findIt);	

			findDescByRec = new Stack();
			findDescByRec.push(ps1);
			findDescByRec.push(ps2);
			findDescByRec.push(ps3);
			findDescByRec.push(ps4);
			findDescByRec.push(ps5);							
			findDescByRec.push(ps6);
			findDescByRec.push(ps7);
			findDescByRec.push(ps8);
			findDescByRec.push(ps9);
			findDescByRec.push(ps10);							
				
//			Find Specific Record with ALL the Keys // Including Company			
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE " +
				"        GNATYP = ?  AND GNACMP = ?" +
				" AND    GNAKY1 = ?  AND GNAKY2 = ?" +
				" AND    GNAKY3 = ?  AND GNAKY4 = ?";
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescCompByRec = new Stack();
			findDescCompByRec.push(ps1);
			findDescCompByRec.push(ps2);
			findDescCompByRec.push(ps3);
			findDescCompByRec.push(ps4);
			findDescCompByRec.push(ps5);							
			findDescCompByRec.push(ps6);
			findDescCompByRec.push(ps7);
			findDescCompByRec.push(ps8);
			findDescCompByRec.push(ps9);
			findDescCompByRec.push(ps10);							
				
//			Find List of Records by Type 		
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND    GNATYP = ?" +
				" ORDER BY GNAKY1, GNAKY2, GNAKY3, GNAKY4";				
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescByKey = new Stack();
			findDescByKey.push(ps1);
			findDescByKey.push(ps2);
			findDescByKey.push(ps3);
			findDescByKey.push(ps4);
			findDescByKey.push(ps5);							
			findDescByKey.push(ps6);
			findDescByKey.push(ps7);
			findDescByKey.push(ps8);
			findDescByKey.push(ps9);
			findDescByKey.push(ps10);							
				
//			Find List of Records by Type & Company		
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND    GNATYP = ?  AND GNACMP = ?" +
				" ORDER BY GNAKY1, GNAKY2, GNAKY3, GNAKY4";			
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescCompByKey = new Stack();
			findDescCompByKey.push(ps1);
			findDescCompByKey.push(ps2);
			findDescCompByKey.push(ps3);
			findDescCompByKey.push(ps4);
			findDescCompByKey.push(ps5);							
			findDescCompByKey.push(ps6);
			findDescCompByKey.push(ps7);
			findDescCompByKey.push(ps8);
			findDescCompByKey.push(ps9);
			findDescCompByKey.push(ps10);							
				
//			Find List of Records by Type 		
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND    GNATYP = ? AND GNAKY1 = ? " +
				" ORDER BY GNAKY2, GNAKY3, GNAKY4";				
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescByKey1 = new Stack();
			findDescByKey1.push(ps1);
			findDescByKey1.push(ps2);
			findDescByKey1.push(ps3);
			findDescByKey1.push(ps4);
			findDescByKey1.push(ps5);							
			findDescByKey1.push(ps6);
			findDescByKey1.push(ps7);
			findDescByKey1.push(ps8);
			findDescByKey1.push(ps9);
			findDescByKey1.push(ps10);
							
//			Find List of Records by Type Ordered By Long Description		
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND    GNATYP = ?" +
				" ORDER BY GNAD20";	
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescByFull = new Stack();
			findDescByFull.push(ps1);
			findDescByFull.push(ps2);
			findDescByFull.push(ps3);
			findDescByFull.push(ps4);
			findDescByFull.push(ps5);							
			findDescByFull.push(ps6);
			findDescByFull.push(ps7);
			findDescByFull.push(ps8);
			findDescByFull.push(ps9);
			findDescByFull.push(ps10);							
				
//			Find List of Records by Type & Company Ordered By Long Description		
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND    GNATYP = ?  AND GNACMP = ?" +
				" ORDER BY GNAD20";
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescCompByFull = new Stack();
			findDescCompByFull.push(ps1);
			findDescCompByFull.push(ps2);
			findDescCompByFull.push(ps3);
			findDescCompByFull.push(ps4);
			findDescCompByFull.push(ps5);							
			findDescCompByFull.push(ps6);
			findDescCompByFull.push(ps7);
			findDescCompByFull.push(ps8);
			findDescCompByFull.push(ps9);
			findDescCompByFull.push(ps10);						
					
//			Find List of Records by Type Ordered By Short (8 long) Description		
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND    GNATYP = ?" +
				" ORDER BY GNAD08";
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescByShort = new Stack();
			findDescByShort.push(ps1);
			findDescByShort.push(ps2);
			findDescByShort.push(ps3);
			findDescByShort.push(ps4);
			findDescByShort.push(ps5);							
			findDescByShort.push(ps6);
			findDescByShort.push(ps7);
			findDescByShort.push(ps8);
			findDescByShort.push(ps9);
			findDescByShort.push(ps10);	

//			Find List of Records by Type & Company Ordered By Short (8 long) Description		
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND    GNATYP = ?  AND GNACMP = ?" +
				" ORDER BY GNAD08";
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescCompByShort = new Stack();
			findDescCompByShort.push(ps1);
			findDescCompByShort.push(ps2);
			findDescCompByShort.push(ps3);
			findDescCompByShort.push(ps4);
			findDescCompByShort.push(ps5);							
			findDescCompByShort.push(ps6);
			findDescCompByShort.push(ps7);
			findDescCompByShort.push(ps8);
			findDescCompByShort.push(ps9);
			findDescCompByShort.push(ps10);					

//			Find List of Records by Type AND Long Description		
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND    GNATYP = ? AND UPPER(GNAD20) = ?" +
				" ORDER BY GNAD20";
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescByText = new Stack();
			findDescByText.push(ps1);
			findDescByText.push(ps2);
			findDescByText.push(ps3);
			findDescByText.push(ps4);
			findDescByText.push(ps5);							
			findDescByText.push(ps6);
			findDescByText.push(ps7);
			findDescByText.push(ps8);
			findDescByText.push(ps9);
			findDescByText.push(ps10);	

//			Find List of Records by Type, Company AND Long Description 
//					Order by Short Description		
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND    GNATYP = ?  AND GNACMP = ?" +
				" AND    UPPER(GNAD20) = ?" +
				" ORDER BY GNAD08";
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescCompByText = new Stack();
			findDescCompByText.push(ps1);
			findDescCompByText.push(ps2);
			findDescCompByText.push(ps3);
			findDescCompByText.push(ps4);
			findDescCompByText.push(ps5);							
			findDescCompByText.push(ps6);
			findDescCompByText.push(ps7);
			findDescCompByText.push(ps8);
			findDescCompByText.push(ps9);
			findDescCompByText.push(ps10);	

//			Find List of Records by Type
//					Order by Sequence Number		
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND    GNATYP = ?" +
				" ORDER BY GNASEQ";
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescBySeq = new Stack();
			findDescBySeq.push(ps1);
			findDescBySeq.push(ps2);
			findDescBySeq.push(ps3);
			findDescBySeq.push(ps4);
			findDescBySeq.push(ps5);							
			findDescBySeq.push(ps6);
			findDescBySeq.push(ps7);
			findDescBySeq.push(ps8);
			findDescBySeq.push(ps9);
			findDescBySeq.push(ps10);	

//			Find List of Records by Type and Company
//					Order by Sequence Number		
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND    GNATYP = ?  AND GNACMP = ?" +
				" ORDER BY GNASEQ";
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescCompBySeq = new Stack();
			findDescCompBySeq.push(ps1);
			findDescCompBySeq.push(ps2);
			findDescCompBySeq.push(ps3);
			findDescCompBySeq.push(ps4);
			findDescCompBySeq.push(ps5);							
			findDescCompBySeq.push(ps6);
			findDescCompBySeq.push(ps7);
			findDescCompBySeq.push(ps8);
			findDescCompBySeq.push(ps9);
			findDescCompBySeq.push(ps10);	

//			Find List of Records by Type and Short Description
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND    GNATYP = ?  AND GNAD08 = ?";
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescShort = new Stack();
			findDescShort.push(ps1);
			findDescShort.push(ps2);
			findDescShort.push(ps3);
			findDescShort.push(ps4);
			findDescShort.push(ps5);							
			findDescShort.push(ps6);
			findDescShort.push(ps7);
			findDescShort.push(ps8);
			findDescShort.push(ps9);
			findDescShort.push(ps10);	

//			Find List of Records by Type and Short Description and Company
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND    GNATYP = ?  AND GNAD08 = ? AND GNACMP = ?";
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescShortComp = new Stack();
			findDescShortComp.push(ps1);
			findDescShortComp.push(ps2);
			findDescShortComp.push(ps3);
			findDescShortComp.push(ps4);
			findDescShortComp.push(ps5);							
			findDescShortComp.push(ps6);
			findDescShortComp.push(ps7);
			findDescShortComp.push(ps8);
			findDescShortComp.push(ps9);
			findDescShortComp.push(ps10);	

//			Find List of Records by TWO Types, for a Dual Drop Down List
//                 where ONE relies on the other.
			findIt = 
				"SELECT * FROM DBPRD.GNPADESC " +
				" WHERE (GNASTS = ' ' OR GNASTS = 'A')" +
				" AND   (GNATYP = ? OR GNATYP = ?) " + 
				" ORDER BY GNAKY1, GNAKY2 ";
				
			ps1  = conn1.prepareStatement(findIt);	 				         
			ps2  = conn2.prepareStatement(findIt);	
			ps3  = conn3.prepareStatement(findIt);	
			ps4  = conn4.prepareStatement(findIt);	
			ps5  = conn5.prepareStatement(findIt);	
			ps6  = conn6.prepareStatement(findIt);	 				         
			ps7  = conn7.prepareStatement(findIt);	
			ps8  = conn8.prepareStatement(findIt);	
			ps9  = conn9.prepareStatement(findIt);	
			ps10 = conn10.prepareStatement(findIt);	

			findDescDualByKey = new Stack();
			findDescDualByKey.push(ps1);
			findDescDualByKey.push(ps2);
			findDescDualByKey.push(ps3);
			findDescDualByKey.push(ps4);
			findDescDualByKey.push(ps5);							
			findDescDualByKey.push(ps6);
			findDescDualByKey.push(ps7);
			findDescDualByKey.push(ps8);
			findDescDualByKey.push(ps9);
			findDescDualByKey.push(ps10);	

			// Return the connections back to the pool.
		
			ConnectionStack.returnConnection(conn1);
			ConnectionStack.returnConnection(conn2);
			ConnectionStack.returnConnection(conn3);
			ConnectionStack.returnConnection(conn4);
			ConnectionStack.returnConnection(conn5);
			ConnectionStack.returnConnection(conn6);
			ConnectionStack.returnConnection(conn7);
			ConnectionStack.returnConnection(conn8);
			ConnectionStack.returnConnection(conn9);
			ConnectionStack.returnConnection(conn10);

		} // end of the IF Statement
	} catch (SQLException e) {
		System.out.println("SQL exception occured at com.treetop.data.GeneralInfo.init()" +
			               e);
	}
}
/**
 * Load fields from data base record.
 *
 * Creation date: (6/23/2003 8:24:29 AM)
 */
protected void loadFields(ResultSet rs) {

	try {
		
		descType     = rs.getString("GNATYP");
		descCompany  = rs.getInt("GNACMP");
		key1Value    = rs.getString("GNAKY1");
		key2Value    = rs.getString("GNAKY2");
		key3Value    = rs.getString("GNAKY3");
		key4Value    = rs.getString("GNAKY4");
		descFull     = rs.getString("GNAD20");
		descShort    = rs.getString("GNAD08");
		descSmall    = rs.getString("GNAD02");  
	
	}
	catch (Exception e)
	{
		System.out.println("SQL Exception at com.treetop.data.GeneralInfo" +
			               ".loadFields();" + e);
	}
}
/**
 * General descriptive code file testing.
 *
 * Creation date: (6/24/2003 8:24:29 AM)
 */
public static void main(String[] args) {
	
	
	GeneralInfo descInfo = new GeneralInfo();
	

	// search descriptions. ----------------------

	try {

		Vector vector1 = GeneralInfo.findDescBySeq("SRH"); 
		System.out.println("First search successfull");	
		
		String dropDown = buildDropDownFullForShort(vector1, "", "dropdown", "Entry");

		vector1 = GeneralInfo.findDescByFull("DPT"); 
		System.out.println("Again First search successfull");	
		
		dropDown = buildDropDownFullForKey1(vector1, "", "dropdown", "Entry");
		String x = "stop here";
		
		vector1 = GeneralInfo.findDescByFull("REG");
		dropDown = buildDropDownFullForKey1Key2(vector1, "", "dropDown", "Entry");
		x = "stop here";

	} catch (Exception e) {
		System.out.println("search1 failed: " + e);

	}


	try {

		Vector vector2 = GeneralInfo.findDescByFull("SRU"); 
		System.out.println("Second search successfull");		 

	} catch (Exception e) {
		System.out.println("search2 failed: " + e);

	}


	
	try {

		Vector vector3 = GeneralInfo.findDescByShort("SRC"); 
		System.out.println("Third search successfull");		 

	} catch (Exception e) {
		System.out.println("search3 failed: " + e);

	}


	try {

		Vector vector4 = GeneralInfo.findDescShort("SRU", "tech"); 
		System.out.println("forth search successfull");		 

	} catch (Exception e) {
		System.out.println("search4 failed: " + e);

	}
	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setDescFull(String descFullIn) 
{
	this.descFull =  descFullIn;
}
}
