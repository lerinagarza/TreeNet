package com.treetop.data;

import java.sql.*;
import java.util.*;
import java.math.*;
import com.treetop.utilities.html.HTMLHelpers;
/**
 * Analytical Identification Code Value For Inventory
 *
 **/
public class AnalyticalCodeValue extends AnalyticalCode{
	/**
	 * Retrieve all values by Analytical Code and Type.
	 *
	 */
	public static Vector findValuesByCode(String codeIn) 
	{
		Vector list = new Vector();
		AnalyticalCodeValue justInitialize = new AnalyticalCodeValue();	
		
		try {		
			PreparedStatement findThem = (PreparedStatement) 
											AnalyticalCodeValue.findAllValuesByIdent.pop();
			ResultSet rs = null;
			
			try {
				findThem.setString(1, codeIn);								
				rs = findThem.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
									"at com.treetop.data.AnalyticalCodeValues." +
								   "findValuesByCode): " + e);
			}
			
			AnalyticalCodeValue.findAllValuesByIdent.push(findThem);
	
			try {
					
				while (rs.next())
				{
					AnalyticalCodeValue acv = new AnalyticalCodeValue(rs);					
					list.addElement(acv);		
				}			
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set " +
								   "at com.treetop.data.AnalyticalCodeValues." +
								   "findValuesByCodeAndType): " + e);
			}
					
			rs.close();	
					
		}	 
		catch (Exception e) {
			System.out.println("error at com.treetop.data.CustomerMCOT." +
								"at com.treetop.data.AnalyticalCodeValues." +
								"findValuesByCode): " + e);
		}	
				
		return list; 
	}
	/**
	 * Send In:
	 * 		String - Type Code ie: RF, DR, CN - Default of "RF" if nothing else is sent.
	 * 		String - Analytical Code - Default of 'TGRAD' if Nothing is Sent in.
	 * 		String - Master Name for Type Code
	 * 		String - Master Value for Type Code
	 * 		String - Slave Name for Inventory Code Chosen
	 * 		String - Slave Value
	 * 
	 *  This method will return a Vector of Strings....
	 *    element:  0 = Drop Down Selection for the Master List.
	 *              1 = Drop Down Selection for the Slave List.
	 *
	 *  If each piece is put into the JSP correctly, You will have
	 *    to choose from the first list, to retrieve the second.
	 * 
	 * Creation date: (7/7/2005 9:50:44 PM  Twalton)
	 */
	public static Vector buildDualDropDownByTypeAndCode(String typeCode,
														String analCode,
														String masterName,
											            String masterValue,
		                                                String slaveName,
		                                                String slaveValue) 
	{
		AnalyticalCodeValue initialize = new AnalyticalCodeValue();
		Vector    dropDownInfo  = new Vector();
		ResultSet rs            = null;
		String listMasterDisplay = "";
		String listMasterValue   = "";
		
		String    slaveInfoLists   = "";
		String    infoLists     = "<script language=\"javascript\"> " +
								  "var lists = new Array();" ;
		String    masterDropDown = "<select name=\"" + masterName + "\" size=1 " +
										"onchange=\"change" + masterName + "(this)\">" +
										"<option value=\"*all\">*all";
										
		   slaveInfoLists = slaveInfoLists +
				" lists['*all'] = new Array(); " +
				" lists['*all'][0]" +
				" = new Array( '*all'); " +
				" lists['*all'][1]" +
				" = new Array( '*all'); ";	
		   listMasterDisplay    = "'*all'";
		   listMasterValue      = "'*all'";		
	try
	{
		if (typeCode == null ||
		    typeCode.trim().equals(""))
		    typeCode = "RF";
		if (analCode == null ||
			analCode.trim().equals(""))
			analCode = "TGRAD";
			
	  	try
	  	{
			PreparedStatement findIt = (PreparedStatement) dualDropDownByTypeAndCode.pop();
			try
			{
		   		findIt.setString(1, analCode);
		   		findIt.setString(2, typeCode);
		   		rs = findIt.executeQuery();
			}
			catch(Exception e)
			{
		   		System.out.println("Exception error creating a Result Set at " +
					   "com.treetop.data.AnalyticalCodeValue." +
					   "buildDualDropDownByTypeAndCode(String, String, String, String, String, String) " + e);
			}
			dualDropDownByTypeAndCode.push(findIt);	
	  	}
	  	catch(Exception e)
	  	{
			System.out.println("Exception error pop/push of a Prepared Statement at" +
					"com.treetop.data.AnalyticalCodeValue." +
					"buildDualDropDownByTypeAndCode(String, String, String, String, String, String) " + e);
	  	}
		
		String selected = "selected=\"selected\" ";									    			                    				
								
	    String saveMasterValue = "";
	    String saveMasterDesc  = "";
		String slaveValues  = "";
		String slaveDisplay = "";
		try 
		{
			while (rs.next())
			{
				if (!rs.getString("INGGC1").trim().equals(saveMasterValue) &&
					!saveMasterValue.equals(""))
				{ // Tie Slave Lists to Master Codes
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
					selected = "";
					
					if (masterValue != null &&
						masterValue.equals(saveMasterValue))	
					  selected = "selected=\"selected\" ";
				   
					if (!listMasterDisplay.equals(""))
					  listMasterDisplay = listMasterDisplay + ",";
				      
					listMasterDisplay = listMasterDisplay + " '" +
										saveMasterValue + " - " +
										saveMasterDesc + "' ";
					if (!listMasterValue.equals(""))
					  listMasterValue = listMasterValue + ",";
					  
					listMasterValue   = listMasterValue + " '" +
										saveMasterValue + "' "; 
													   
					masterDropDown = masterDropDown +
									 "<option value=\"" + saveMasterValue + "\" " +
													   selected + ">" +
									   saveMasterDesc.trim();
					slaveValues     = "";
					slaveDisplay    = "";
		    
					
				}				
				saveMasterValue = rs.getString("GNAKY2");
				saveMasterDesc  = rs.getString("GNAD20");				
				
			   // Build the list of Slave Codes
				 // Ties the Master to the Slave	 
				if (!slaveDisplay.equals(""))
				  slaveDisplay = slaveDisplay + ",";
					slaveDisplay = slaveDisplay + "'" +
				    rs.getString("INGSDS").trim() + " - " +
				    HTMLHelpers.displayNumber((new BigDecimal(rs.getString("INGNV"))), rs.getInt("INCDTD"))+ "' ";
				if (!slaveValues.equals(""))
					  slaveValues = slaveValues + ",";
					slaveValues  = slaveValues + "'" +
									rs.getString("INGNV") + "' "; 	
				
			} // End of the While Statement
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
					   
				selected = "";
					
				if (masterValue != null &&
					masterValue.equals(saveMasterValue))	
				  selected = "selected=\"selected\" ";
				   
				if (!listMasterDisplay.equals(""))
				  listMasterDisplay = listMasterDisplay + ",";
				      
				listMasterDisplay = listMasterDisplay + " '" +
									saveMasterValue + " - " +
									saveMasterDesc + "' ";
				if (!listMasterValue.equals(""))
				  listMasterValue = listMasterValue + ",";
					  
				listMasterValue   = listMasterValue + " '" +
									saveMasterValue + "' "; 					
					   
				masterDropDown = masterDropDown +
								 "<option value=\"" + saveMasterValue + "\" " +
												   selected + ">" +
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
							   "(com.treetop.data.AnalyticalCodeValue." +
							   "buildDualDropDownByTypeAndCode(String, String, String, String, String, String)): " + e);
		}
		
		infoLists = infoLists +
					   "</script>";
		
		masterDropDown = masterDropDown +
					   "</select>";
		               
		dropDownInfo.addElement(masterDropDown);
		dropDownInfo.addElement(infoLists);
		
	}
	catch(Exception e)
	{
		System.out.println("Exception Caught within :" +
							   "com.treetop.data.AnalyticalCodeValue." +
					   		"buildDualDropDownByTypeAndCode(String, String, String, String, String, String) " + e);
	}
				
		return dropDownInfo; 
	}
	
	// Data base fields from files IDPSANAL, INPCANAL, and INPGANAL.
		    
	private String		valueRecordIdCode;		//INGREC
	private Integer		valueSequenceNumber;	//INGSEQ
	private BigDecimal  valueNumeric;			//INGNV
	private String		shortValueDescription;	//INGSDS
	private String   	valueReference;			//INGREF
	private String		valueDescription;		//INGRF	
	private String      specification;            
	

	// Static class values.
	private static boolean acvPersists = false;
		
	// database environment
	private static String library = "DBLIB.";
	//private static String wklib   = "WKLIB.";
		
	//sql prepared statements.
	private static Stack findAllByIdentAndCode = null;
	private static Stack findAllByInvGroupCode = null;
	private static Stack findValueBySeq = null;
	private static Stack findAllValuesByIdent = null;
	private static Stack updateByCodeTypeValue = null;
	private static Stack addIdentValue = null;
	private static Stack deleteIdentValue = null;

	private static Stack dualDropDownByTypeAndCode = null;
	
/**
 * Analytical Code Value empty Constructor.
 */
public AnalyticalCodeValue() {
	super();
	init();
}

/**
 * Instantiate the Analytical Code Value 
 * class from a result set.
**/
public AnalyticalCodeValue(ResultSet rs) {
	super();		
	init();		
	loadFields(rs);	
}	

/**
 * Analytical Code Value constructor.
 */
public AnalyticalCodeValue(String analyticalCodeIn,							
         					String analyticalCodeTypeIn,
                    		BigDecimal valueNumericIn,
                    		String invGroupCode1In,
                    		String invGroupCode2In,
                    		String invGroupCode3In)
	throws InstantiationException 
{
	super();
	init();

	// Use prepared statement to obtain result set.	
	ResultSet rs = null;
	String errorMessage = "";
	
	try 
	{
		PreparedStatement findIt = (PreparedStatement) findValueBySeq.pop(); 
									         
		try {
			findIt.setString(1, analyticalCodeIn);
			findIt.setString(2, analyticalCodeTypeIn);
			findIt.setBigDecimal(3, valueNumericIn);
			findIt.setString(4, invGroupCode1In);
			findIt.setString(5, invGroupCode2In);
			findIt.setString(6, invGroupCode3In);
			rs = findIt.executeQuery();
		}
		catch (Exception e){			
			errorMessage = "Exception error creating a result set " +
							"at com.treetop.data.AnalyticalCodeValue." +
						   "(code:" + analyticalCodeIn + "," +
						   " type:" + analyticalCodeTypeIn + "," +
						   " value:" + valueNumericIn +
						   ") " + e;
		}									            
		findValueBySeq.push(findIt);
	} 
	catch (Exception e) 
	{
		errorMessage = "Exception error with the pop/push of statement " +
					   "at com.treetop.data.AnalyticalCodeValue." +
					   "(code:" + analyticalCodeIn + "," +
					   " type:" + analyticalCodeTypeIn + "," +
					   " value:" + valueNumericIn +
					   ") " + e;
	}
	
	try
	{
		if (rs.next())// Test to see if it is valid at all..
		{
			loadFields(rs);
		} else
		{
			errorMessage = "Exception error The record was not found. " +
							"at com.treetop.data.AnalyticalCodeValue." +
							"(code:" + analyticalCodeIn + "," +
							" type:" + analyticalCodeTypeIn + "," +
							" value:" + valueNumericIn +
							") " ;
		}
	}
	catch(Exception e)
	{
		errorMessage = "Exception error with loadFields on " +
					   "at com.treetop.data.AnalyticalCodeValue." +
					   "(code:" + analyticalCodeIn + "," +
					   " type:" + analyticalCodeTypeIn + "," +
					   " value:" + valueNumericIn +
					   ") " + e;
	}
		
	try 
	{
		rs.close();
	} 
	catch (SQLException e) 
	{}
				
	if (!errorMessage.equals(""))
	   throw new InstantiationException(errorMessage);

	return;
}


/**
* Add a Analytical Code Value Record (INPGANAL).
*  Code type must not be blank.
*  A matching code value must exist in file IDPCANAL.
*  Code Type and Value must not already exist 
*  in file INPGANAL.
*
*/

public void add() 
	throws Exception 
{
	init();
	String popped = "no";
	PreparedStatement addIt = null;
	String throwError = "";
	
	if (analyticalCode.trim().equals("") ||
		codeType.trim().equals(""))
		throwError = "Unable to add this requested " +
					 "Analytical Code Value. " +
					 "The code (" + analyticalCode + ") " +
					 "and type (" + codeType + ") " +
					 "Analytical Code or Code Type are blank. ";
					 
	if (throwError.equals(""))
	{
		// test to see if a Ident entry exists in the 
		// Analytical Code File "IDPGANAL".
		try {
			addIt = (PreparedStatement) addIdentValue.pop();
			popped = "yes";
			addIt.setString(1,  valueRecordIdCode);
			addIt.setString(2,  analyticalCode);
			addIt.setString(3,  codeType);
			addIt.setInt(4, valueSequenceNumber.intValue());
			addIt.setBigDecimal(5,  valueNumeric);
			addIt.setString(6,  shortValueDescription);
			addIt.setString(7,  valueReference);
			addIt.setString(8,  valueDescription);
			addIt.setString(9,  specification);
			addIt.setString(10, invGroupCode1);
			addIt.setString(11, invGroupCode2);
			addIt.setString(12, invGroupCode3);
					              	
			// Test to determine if Code/Type/Value already exists.
			try {
				AnalyticalCodeValue alreadyExists = new AnalyticalCodeValue(analyticalCode, 
																  			codeType,
																  			valueNumeric,
																  			invGroupCode1,
																  			invGroupCode2,
																  			invGroupCode3);
				throwError = "Unable to add this requested " +
							 "Analytical Code Type Value. " +
							 "The combonation (code:" + analyticalCode +
							 ", type:" + codeType +
							 "value:" + valueNumeric +
 							 ") " +
							 "already exist. ";
			} catch (Exception e) {
				 //should throw a instantiation error.			
			}
				
//			if (throwError.equals(""));			
//				throwError = verifyClassValues();				
				
			if (throwError.equals(""))
				addIt.executeUpdate();
	
		} catch (Exception e) {
			throwError = "Error while trying to add " +
						 "Analytical Code Value " +
						 "(code:" + analyticalCode + 
						 ", type:" + codeType +
						 ", value:" + valueNumeric + "). " +
						 "Please contact IS Help Desk for " +
						 "assistance. error @ " + e;
		}
			
		if (popped.equals("yes"))
			addIdentValue.push(addIt);
	}
		
	if (!throwError.equals(""))
	{
		throwError = throwError + " com.treetop.data." +
					 "AnalyticalCodeValue.add()";	
		throw new Exception(throwError);
	}
	
	return;	
}

/**
 * Delete the record from file INPGANAL.
 * 
 */
public void delete() {

	// Delete a INPGANAL record.
	try {
		PreparedStatement deleteIt = (PreparedStatement) deleteIdentValue.pop(); 
		deleteIt.setString(1, codeType);
		deleteIt.setString(2, analyticalCode);
		deleteIt.setBigDecimal(3, valueNumeric);
		deleteIt.setString(4, invGroupCode1);
		deleteIt.setString(5, invGroupCode2);
		deleteIt.setString(6, invGroupCode3);
				
		deleteIt.executeUpdate();
		deleteIdentValue.push(deleteIt);	

	} catch (Exception e) {
		System.out.println("error at com.treetop.data." +
						   "AnalyticalCodeValue.delete():" + e);
	}
}	

/**
 * Create Prepared Statements
 */
public void init() 
{
	// Test for initialization.	
	if (acvPersists == false) 
	{
		super.init();
	    acvPersists = true;
		
		try {
			// Retrieve five connections from the connection pool.
//			com.treetop.ConnectionPool connectionPool =
//				new com.treetop.ConnectionPool();
			Connection conn1  = null;
			Connection conn2  = null;
			Connection conn3  = null;
			Connection conn4  = null;
			Connection conn5  = null;
			

			// Create and stack multiple prepared statements.
			
			// Find By Analytical Code, Analytical Code Type,
			// Inventory group Codes, and Numeric Value. 
			
			String fbKeys =
				"SELECT * " + 
				" FROM " + library + "INPGANAL " +
				"INNER JOIN " + library + "INPCANAL " +
				" ON INGCDE = INCCDE " +
				" AND INGTYP = INCTYP " +
				" AND INGGC1 = INCGC1 " +
				" AND INGGC2 = INCGC2 " +
				" AND INGGC3 = INCGC3 " +
				"INNER JOIN " + library + "IDPSANAL " +
				" ON INCCDE = IDSCDE " +
				" WHERE INGCDE = ? " + 
				"   AND INGTYP = ? " +
				"   AND INGNV  = ? " +
				"   AND INGGC1 = ? " +
				"   AND INGGC2 = ? " +
				"   AND INGGC3 = ? ";
				
			PreparedStatement ps1 = conn1.prepareStatement(fbKeys);
			PreparedStatement ps2 = conn2.prepareStatement(fbKeys);	
			PreparedStatement ps3 = conn3.prepareStatement(fbKeys);	
			PreparedStatement ps4 = conn4.prepareStatement(fbKeys);	
			PreparedStatement ps5 = conn5.prepareStatement(fbKeys);	

			findValueBySeq = new Stack();
			findValueBySeq.push(ps1);
			findValueBySeq.push(ps2);
			findValueBySeq.push(ps3);
			findValueBySeq.push(ps4);
			findValueBySeq.push(ps5);
			
			
			// Find All By Analytical Code, 
			//  and Analytical Code Value. 
			fbKeys =
				"SELECT * " + 
				" FROM " + library + "INPGANAL " +
				"INNER JOIN " + library + "INPCANAL " +
				" ON INGCDE = INCCDE " +
				" AND INGTYP = INCTYP " +
				" AND INGGC1 = INCGC1 " +
				"INNER JOIN " + library + "IDPSANAL " +
				" ON INCCDE = IDSCDE " +
				" WHERE INGCDE = ? " + 
				"   AND INGTYP = ? ";
				
			ps1 = conn1.prepareStatement(fbKeys);
			ps2 = conn2.prepareStatement(fbKeys);	
			ps3 = conn3.prepareStatement(fbKeys);	
			ps4 = conn4.prepareStatement(fbKeys);	
			ps5 = conn5.prepareStatement(fbKeys);	

			findAllByIdentAndCode = new Stack();
			findAllByIdentAndCode.push(ps1);
			findAllByIdentAndCode.push(ps2);
			findAllByIdentAndCode.push(ps3);
			findAllByIdentAndCode.push(ps4);
			findAllByIdentAndCode.push(ps5);
			
			
			// Find All Values By Analytical Code. 
			fbKeys =
				"SELECT * " + 
				" FROM " + library + "IDPSANAL " +
				"LEFT OUTER JOIN " + library + "INPCANAL " +
				" ON IDSCDE = INCCDE " +
				"LEFT OUTER JOIN " + library + "INPGANAL " +
				" ON INCCDE = INGCDE " +
				" AND INCTYP = INGTYP " +
				" AND INCGC1 = INGGC1 " +
				" AND INCGC2 = INGGC2 " +
				" AND INCGC3 = INGGC3 " +
				" WHERE IDSCDE = ? " + 
				" ORDER BY IDSCDE, INCTYP, INCGC1, INCGC2, INCGC3, INGSEQ ";
				
			ps1 = conn1.prepareStatement(fbKeys);
			ps2 = conn2.prepareStatement(fbKeys);	
			ps3 = conn3.prepareStatement(fbKeys);	
			ps4 = conn4.prepareStatement(fbKeys);	
			ps5 = conn5.prepareStatement(fbKeys);	

			findAllValuesByIdent = new Stack();
			findAllValuesByIdent.push(ps1);
			findAllValuesByIdent.push(ps2);
			findAllValuesByIdent.push(ps3);
			findAllValuesByIdent.push(ps4);
			findAllValuesByIdent.push(ps5);	
			
			
			// Find All By Analytical Code, 
			//  Analytical Code Value,
			//  and InvGroupCodes.
			fbKeys =
				"SELECT * " + 
				" FROM " + library + "INPCANAL " +
				"LEFT OUTER JOIN " + library + "INPGANAL " +
				" ON INGCDE = INCCDE " +
				" AND INGTYP = INCTYP " +
				" AND INGGC1 = INCGC1 " +
				" AND INGGC2 = INCGC2 " +
				" AND INGGC3 = INCGC3 " +
				"INNER JOIN " + library + "IDPSANAL " +
				" ON INCCDE = IDSCDE " +
				" WHERE INCCDE = ? " + 
				"   AND INCTYP = ? " +
				"   AND INCGC1 = ? " +
				"   AND INCGC2 = ? " +
				"   AND INCGC3 = ? ";
				
			ps1 = conn1.prepareStatement(fbKeys);
			ps2 = conn2.prepareStatement(fbKeys);	
			ps3 = conn3.prepareStatement(fbKeys);	
			ps4 = conn4.prepareStatement(fbKeys);	
			ps5 = conn5.prepareStatement(fbKeys);	

			findAllByInvGroupCode = new Stack();
			findAllByInvGroupCode.push(ps1);
			findAllByInvGroupCode.push(ps2);
			findAllByInvGroupCode.push(ps3);
			findAllByInvGroupCode.push(ps4);
			findAllByInvGroupCode.push(ps5);
			

			// Update By Analytical Code, Code Type, and Value.
			String updateIt =
				"UPDATE " + library + "INPGANAL " +
				" SET INGREC = ?,  " +
				"     INGSEQ = ?, INGSDS = ?, " +
				"     INGRF  = ?, INGDSC = ?, " +
				"     INGSPC = ?, INGGC1 = ?, " +
				"     INGGC2 = ?, INGGC3 = ?  " +
				" WHERE INGCDE = ? AND INGTYP = ? " +
				"   AND INGNV  = ? AND INGGC1 = ? " +
				"   AND INGGC2 = ? AND INGGC3 = ? ";

			ps1 = conn1.prepareStatement(updateIt);
			ps2 = conn2.prepareStatement(updateIt);
			ps3 = conn3.prepareStatement(updateIt);
			ps4 = conn4.prepareStatement(updateIt);
			ps5 = conn5.prepareStatement(updateIt);

			updateByCodeTypeValue = new Stack();
			updateByCodeTypeValue.push(ps1);
			updateByCodeTypeValue.push(ps2);
			updateByCodeTypeValue.push(ps3);
			updateByCodeTypeValue.push(ps4);
			updateByCodeTypeValue.push(ps5);
			

			// Add a record to INPGANAL.
			String insertIt =
				"INSERT INTO " + library + "INPGANAL " +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
						 "?, ? )";
					
			ps1 = conn1.prepareStatement(insertIt);
			ps2 = conn2.prepareStatement(insertIt);
			ps3 = conn3.prepareStatement(insertIt);
			ps4 = conn4.prepareStatement(insertIt);
			ps5 = conn5.prepareStatement(insertIt);

			addIdentValue = new Stack();
			addIdentValue.push(ps1);
			addIdentValue.push(ps2);
			addIdentValue.push(ps3);
			addIdentValue.push(ps4);
			addIdentValue.push(ps5);


			// Delete Analytical Code Value.
			String deleteIt =		
				"DELETE FROM " + library + "INPGANAL " +
				" WHERE INGTYP = ? AND INGCDE = ? " +
				"   AND INGNV =  ? AND INGGC1 = ? " +
				"   AND INGGC2 = ? AND INGGC3 = ? ";

			ps1 = conn1.prepareStatement(deleteIt);
			ps2 = conn2.prepareStatement(deleteIt);
			ps3 = conn3.prepareStatement(deleteIt);
			ps4 = conn4.prepareStatement(deleteIt);
			ps5 = conn5.prepareStatement(deleteIt);
			
			deleteIdentValue = new Stack();
			deleteIdentValue.push(ps1);
			deleteIdentValue.push(ps2);
			deleteIdentValue.push(ps3);
			deleteIdentValue.push(ps4);
			deleteIdentValue.push(ps5);					

			// Build Dual Drop Down List for Type and Analytical Code
			String findIt =		
				"SELECT * " + 
				"FROM " + library + "GNPADESC " +
				"INNER JOIN " + library + "INPGANAL " +
					"ON GNAKY1 = INGTYP " +
					"AND GNAKY2 = INGGC1 " +
					"AND GNAKY3 = INGGC2 " +
					"AND INGCDE = ? " +
			    "INNER JOIN " + library + "INPCANAL " +
			        "ON INGCDE = INCCDE " +
			        "AND INGTYP = INCTYP " +
			        "AND INGGC1 = INCGC1 " +
			        "AND INGGC2 = INCGC2 " +
			        "AND INGGC3 = INGGC3 " +
				" WHERE GNATYP = 'ACV' AND GNAKY1 = ? " + 
				" ORDER BY INGCDE, GNAKY1, GNAKY2, INGNV ";

			ps1 = conn1.prepareStatement(findIt);
			ps2 = conn2.prepareStatement(findIt);
			ps3 = conn3.prepareStatement(findIt);
			ps4 = conn4.prepareStatement(findIt);
			ps5 = conn5.prepareStatement(findIt);
			
			dualDropDownByTypeAndCode = new Stack();
			dualDropDownByTypeAndCode.push(ps1);
			dualDropDownByTypeAndCode.push(ps2);
			dualDropDownByTypeAndCode.push(ps3);
			dualDropDownByTypeAndCode.push(ps4);
			dualDropDownByTypeAndCode.push(ps5);	
			

			// Return the connections back to the pool.
//			connectionPool.returnConnection(conn1);
//			connectionPool.returnConnection(conn2);
//			connectionPool.returnConnection(conn3);
//			connectionPool.returnConnection(conn4);
//			connectionPool.returnConnection(conn5);
			
		} catch (Exception e) {
			System.out.println("error: An exception occured at " +
								"com.treetop.data." +
							   "AnalyticalCodeValue.init()" +  e);	              
		}
	}
}

/**
 * Load class fields from result set.
 */
protected void loadFields(ResultSet rs) 
{

	try {
		super.loadFields(rs);
		 
		if (rs.getString("INGREC") != null)
		{
			valueRecordIdCode		= rs.getString("INGREC").trim();
			valueSequenceNumber		= new Integer(rs.getInt("INGSEQ"));
			valueNumeric			= rs.getBigDecimal("INGNV");
			shortValueDescription	= rs.getString("INGSDS").trim();
			valueReference			= rs.getString("INGRF").trim();
			valueDescription		= rs.getString("INGDSC").trim();
			specification           = rs.getString("INGSPC").trim();
		}					
	}
	catch (Exception e)
	{
		System.out.println("error: SQL Exception at " +
							"com.treetop.data." +
			               "AnalyticalCodeValue.loadFields(RS);" + e);
		if (analyticalCode != null)
		   System.out.println(" ANALYTICAL CODE: " + analyticalCode);
		if (codeType != null)
		   System.out.println(" CODE TYPE: " + codeType);
		if (longDescription != null)
		   System.out.println(" LONG DESCRIPTION: " + longDescription);
		if (valueNumeric != null)
		   System.out.println(" VALUE NUMERIC: " + valueNumeric);
			               
	}
}

/**
 * 	This method tests access to the Analytical Code Value 
 * file "INPGANAL". All class fields should be tested and
 * verified for access to and from the Enterprise database.
 * A listing of records as they are accessed and updated 
 * should be generated. Also connections, prepared statements, 
 * and the loadFields method are confirmed.
 * 
 */

public static void main(String[] args) {

	// Test add/instantiation/update of class.
	
	try {
		//add it.
		AnalyticalCodeValue ac = new AnalyticalCodeValue();
		ac.setValueRecordIdCode("");
		ac.setCodeType("RF");
		ac.setAnalyticalCode("NUMMY");
		ac.setValueSequenceNumber(new Integer("12"));
		ac.setValueNumeric(new BigDecimal("1"));
		ac.setShortValueDescription("short value description");
		ac.setValueReference("www.yippie.com");
		ac.setValueDescription("value description 500 long");
		ac.setSpecification("SPECX");
		ac.setInvGroupCode1("C1");
		ac.setInvGroupCode2("C2");
		ac.setInvGroupCode3("C3");
		ac.add();
		
		//update it.
		ac.setValueDescription("changed it");
		ac.update();
		
		//delete it.
		ac.delete();
		
		//instantiate it.
		String  testCode = "MCP";
		String  testType = "RF";
		BigDecimal testNv	 = new BigDecimal("10");
		AnalyticalCodeValue one = new AnalyticalCodeValue(testCode,
		                                    			testType,
		                                    			testNv,
		                                    			"", "", "");	                                     
		System.out.println("one: " + one);
		
		// test update of class.
		String sd = one.getShortValueDescription();
		one.setShortValueDescription("was changed");
		one.update();
		System.out.println("acvClass: " + one);
		one.setShortValueDescription(sd);
		one.update();
		System.out.println("acClass: " + one);		
		
		// test method  findValuesByInGroupCode(5-Strings)
		String code = "TGRAD";
		String type = "RF";
		String gc1  = "";
		String gc2  = null;
		String gc3  = "  ";
		Vector v = AnalyticalCodeValue.
						findValuesByInvGroupCode(code,
												 type,
												 gc1,
												 gc2,
												 gc3);
		sd = "stop";
		
		// Build a list of Analytical Code values
		//  for a specific Analytical Code and Type.
		Vector list = AnalyticalCodeValue.findValuesByCodeAndType("WCORE", "RF");
		one = (AnalyticalCodeValue) list.elementAt(1);
		System.out.println("one: " + one);
		
		
		// Build a list of Analytical Code values
		//  for a specific Analytical Code.
			//with values.
		list = AnalyticalCodeValue.findValuesByCode("IGRAD");
		one = (AnalyticalCodeValue) list.elementAt(0);
			//with out values.
		list = AnalyticalCodeValue.findValuesByCode("IDPC");
		one = (AnalyticalCodeValue) list.elementAt(0); 
		System.out.println("one: " + one);				


		//try one that does'nt exist.
		testCode  = "XYZ01";
		AnalyticalCodeValue two = new AnalyticalCodeValue(testCode,
														  testType,
														  testNv,
														  "", "", "");
		System.out.println("two: " + two);
			
	} catch (Exception e) {
		System.out.println("catch At AnalyticalCodeValue.main()");
	}
	
	
	// Test the result of different inquiry requests.
	try{
		// no selection criteria.
		AnalyticalCodeValue acFrom = new AnalyticalCodeValue();
		AnalyticalCodeValue acTo = new AnalyticalCodeValue();
		Vector z = AnalyticalCodeValue.findAnalyticalCodeValueInquiry(acFrom, acTo);
		String stopHere = "x";
		
		// select by value.
		acFrom.setValueNumeric(new BigDecimal(1));
		z = AnalyticalCodeValue.findAnalyticalCodeValueInquiry(acFrom, acTo);
		stopHere = "x";
		
		// select by code.
		acFrom = new AnalyticalCodeValue();
		acTo   = new AnalyticalCodeValue();
		acFrom.setAnalyticalCode("B");
		z = AnalyticalCodeValue.findAnalyticalCodeValueInquiry(acFrom, acTo);
		stopHere = "x";
		
		// select by code order by code descending.
		acFrom.setOrderByField("analyticalcode");
		acFrom.setOrderByStyle("DESC");
		z = AnalyticalCodeValue.findAnalyticalCodeValueInquiry(acFrom, acTo);
		stopHere = "x";
		
		// selct by long description.
		acFrom.setAnalyticalCode("");
		acFrom.setLongDescription("b");
		acFrom.setOrderByField("longdescription");
		z = AnalyticalCodeValue.findAnalyticalCodeValueInquiry(acFrom, acTo);
		stopHere = "x";
		
		// select by type order by long description
		acFrom.setLongDescription("i");
		acFrom.setCodeType("RF");
		acFrom.setOrderByField("longdescription");
		z = AnalyticalCodeValue.findAnalyticalCodeValueInquiry(acFrom, acTo);
		stopHere = "x";
	}
	catch (Exception e) {
		System.out.println("error occured in main at inquiry tests.");
	}
		
}

/**
 * Set vector to return Analytical Codes using selections 
 * from incomming class parameters.
 *
 */
public static Vector findAnalyticalCodeValueInquiry(AnalyticalCodeValue fromClass,
													AnalyticalCodeValue toClass) 
{
	Vector    list = new Vector();
	String    whereClause  = "";
	String    whereLike    = "";
           
        
	// Initial SQL statement for selection.    
	String sqlStatement = "SELECT *" + 
		" FROM " + library + "IDPSANAL " +                      
		"LEFT OUTER JOIN " + library + "INPCANAL " +
		"  ON IDSCDE = INCCDE " +
		"LEFT OUTER JOIN " + library + "INPGANAL " +
		"  ON INCCDE = INGCDE AND INCGC1 = INGGC1 ";
	

	// Add WHERE and ORDERBY function to the sql statement.
	try {	           	           
		whereClause = whereClause + sqlWhereAnalyticalCode(fromClass, whereClause);
		whereClause = whereClause + sqlWhereCodeType(fromClass, whereClause);
		whereClause = whereClause + sqlWhereInvGroupCode1(fromClass, whereClause);
		whereClause = whereClause + sqlWhereInvGroupCode2(fromClass, whereClause);
		whereClause = whereClause + sqlWhereInvGroupCode3(fromClass, whereClause);
		whereClause = whereClause + sqlWhereLongDescription(fromClass, whereClause);
		whereClause = whereClause + sqlWhereShortDescription(fromClass, whereClause);		
		whereClause = whereClause + sqlWhereAnalyticalValue(fromClass, whereClause);
        whereClause = whereClause + sqlWhereSpecification(fromClass, whereClause);

		// Assemble the "WHERE" clause using the "WHERE" and "LIKE" SQL functions.

		if (!whereClause.equals("")) 
		{

			if (!whereLike.equals(""))
				sqlStatement = sqlStatement + "WHERE (" + whereClause + ") AND (" + whereLike + ")";
			else
				sqlStatement = sqlStatement + "WHERE " + whereClause;
			}

		else {
			if (!whereLike.equals(""))
				sqlStatement = sqlStatement + "WHERE " + whereLike;
		}      
                
      
		// Add the sort sequence to the "ORDER BY" clause.

		String orderBy = sqlOrderBy(fromClass) + ", INGNV ";
			sqlStatement   = sqlStatement + orderBy;   
         
		// Execute the SQL statement using a connection pool.
//		com.treetop.ConnectionPool connectionPool = new com.treetop.ConnectionPool();        
//		Connection conn = connectionPool.getConnection();    

//		Statement  stmt = conn.createStatement();    
//		ResultSet  rs   = stmt.executeQuery(sqlStatement);                
          
//		connectionPool.returnConnection(conn);                          

		// Process the SQL result into the return data class vector elements. 
		try {
	        
//			while (rs.next())
//			{
//				AnalyticalCodeValue buildVector = new AnalyticalCodeValue();
//				buildVector.loadFields(rs);
//				list.addElement(buildVector); 
//			}		
						
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "at com.tretop.data.AnalyticalCodeValue" +
							   ".findAnalyticalCodeValueInquiry(Class, Class): " + e);
		}

	//	rs.close();       
 
	}

	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data." +
						   "AnalyticalCodeValue.findAnalyticalCode" +
						   "ValueInquiry(Class, Class): " + e);
	}

	return list;
     
}


/**
 * Retrieve all values by Analytical Code and Type.
 *
 */
public static Vector findValuesByCodeAndType(String codeIn,
											 String typeIn) {

	Vector list = new Vector();
	AnalyticalCodeValue justInitialize = new AnalyticalCodeValue();	
	
	try {		
		PreparedStatement findThem = (PreparedStatement) 
										AnalyticalCodeValue.findAllByIdentAndCode.pop();
		ResultSet rs = null;
		
		try {
			findThem.setString(1, codeIn);
			findThem.setString(2, typeIn);								
			rs = findThem.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
								"at com.treetop.data.AnalyticalCodeValues." +
							   "findValuesByCodeAndType): " + e);
		}
		
		AnalyticalCodeValue.findAllByIdentAndCode.push(findThem);

		try {
				
			while (rs.next())
			{
				AnalyticalCodeValue mcot = new AnalyticalCodeValue(rs);					
				list.addElement(mcot);		
			}			
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "at com.treetop.data.AnalyticalCodeValues." +
							   "findValuesByCodeAndType): " + e);
		}
				
		rs.close();	
				
	}	 
	catch (Exception e) {
		System.out.println("error at com.treetop.data.CustomerMCOT." +
							"at com.treetop.data.AnalyticalCodeValues." +
							"findValuesByCodeAndType): " + e);
	}	
			
	return list; 
}

/**
 * Retrieve all values by Analytical Code and Type.
 *
 */
public static Vector findValuesByInvGroupCode(String codeIn,
											 String typeIn,
											 String groupCode1In,
											 String groupCode2In,
											 String groupCode3In) {

	Vector list = new Vector();
	AnalyticalCodeValue justInitialize = new AnalyticalCodeValue();
	
	if (groupCode1In == null || groupCode1In.equals(""))
		groupCode1In = "  ";
	
	if (groupCode2In == null || groupCode2In.equals(""))
			groupCode2In = "  ";
			
	if (groupCode3In == null || groupCode3In.equals(""))
			groupCode3In = "  ";	
	
	try {		
		PreparedStatement findThem = (PreparedStatement) 
										AnalyticalCodeValue.findAllByInvGroupCode.pop();
		ResultSet rs = null;
		
		try {
			findThem.setString(1, codeIn);
			findThem.setString(2, typeIn);
			findThem.setString(3, groupCode1In);
			findThem.setString(4, groupCode2In);
			findThem.setString(5, groupCode3In);							
			rs = findThem.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
								"at com.treetop.data.AnalyticalCodeValues." +
							   "findValuesByInvGroupCode): " + e);
		}
		
		AnalyticalCodeValue.findAllByInvGroupCode.push(findThem);

		try {
				
			while (rs.next())
			{
				AnalyticalCodeValue mcot = new AnalyticalCodeValue(rs);					
				list.addElement(mcot);		
			}			
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "at com.treetop.data.AnalyticalCodeValues." +
							   "findValuesByInvGroupCode): " + e);
		}
				
		rs.close();	
				
	}	 
	catch (Exception e) {
		System.out.println("error at com.treetop.data.CustomerMCOT." +
							"at com.treetop.data.AnalyticalCodeValues." +
							"findValuesByInvGroupCode): " + e);
	}	
			
	return list; 
}


	/**
	 * Used to test creation of class.
	**/	
	public String toString() {
	
		return new String(
		"recordCode: " + recordCode + "\n" +
		"recordDeleteCode" + recordDeleteCode + "\n" +
		"analyticalCode: " + analyticalCode + "\n" +
		"codeType: " + codeType + "\n" +
		"typeOfSpecification: " + typeOfSpecification + "\n" +
		"decimalPositions: " + decimalPositions + "\n" +
		"unitOfMeasure: " + unitOfMeasure + "\n" +
		"methodOfMeasureNumber: " + methodOfMeasureNumber + "\n" +
		"shortDescription: " + shortDescription + "\n" +
		"targetValue: " + targetValue + "\n" +
		"upperStandardLimit: " + upperStandardLimit + "\n" +
		"lowerStandardLimit: " + lowerStandardLimit + "\n" +
		"unitDescription: " + unitDescription + "\n" +
		"oldSortSequence: " + oldSortSequence + "\n" +
		"productGroupingA: " + productGroupingA + "\n" +
		"productGroupingB: " + productGroupingB + "\n" +
		"productGroupingC: " + productGroupingC + "\n" +
		"productGroupingD: " + productGroupingD + "\n" +
		"productGroupingE: " + productGroupingE + "\n" +
		"productGroupingF: " + productGroupingF + "\n" +
		"productGroupingG: " + productGroupingG + "\n" +
		"productGroupingH: " + productGroupingH + "\n" +
		"productGroupingI: " + productGroupingI + "\n" +
		"productGroupingJ: " + productGroupingJ + "\n" +
		"productGroupingK: " + productGroupingK + "\n" +
		"productGroupingL: " + productGroupingL + "\n" +
		"productGroupingM: " + productGroupingM + "\n" +
		"productGroupingN: " + productGroupingN + "\n" +
		"productGroupingO: " + productGroupingO + "\n" +
		"productGroupingP: " + productGroupingP + "\n" +			
		"productGroupingQ: " + productGroupingQ + "\n" +
		"productGroupingR: " + productGroupingR + "\n" +
		"productGroupingS: " + productGroupingS + "\n" +
		"productGroupingT: " + productGroupingT + "\n" +
		"coaHdr1: " + coaHdr1 + "\n" +
		"coaHdr2: " + coaHdr2 + "\n" +
		"coaHdr3: " + coaHdr3 + "\n" +
		"identGroupingCode: " + identGroupingCode + "\n" +
		"division: " + division + "\n" +
		"testedBrixLevel: " + testedBrixLevel + "\n" +
		"valueList: " + valueList + "\n" +
		"longDescription: " + longDescription + "\n" +
		"analyticalReference: " + analyticalReference + "\n" +
		"valueSequenceNumber: " + valueSequenceNumber + "\n" +
		"valueNumeric: " + valueNumeric + "\n" +
		"shortValueDescription: " + shortValueDescription + "\n" +
		"valueReference: " + valueReference + "\n" +
		"valueDescription: " + valueDescription + "\n" +
		"positiveNegitive: " + positiveNegitive + "\n" +
		"requiredValue: " + requiredValue + "\n" +
		"invGroupCode1: " + invGroupCode1 + "\n" +
		"invGroupCode2: " + invGroupCode2 + "\n" +
		"invGroupCode3: " + invGroupCode3 + "\n");																					
	}	

	
	/**
	 * @return
	 */
	public String getValueDescription() {
		return valueDescription;
	}

	/**
	 * @return
	 */
	public BigDecimal getValueNumeric() {
		return valueNumeric;
	}

	/**
	 * @return
	 */
	public String getValueRecordIdCode() {
		return valueRecordIdCode;
	}

	/**
	 * @return
	 */
	public String getValueReference() {
		return valueReference;
	}

	/**
	 * @return
	 */
	public Integer getValueSequenceNumber() {
		return valueSequenceNumber;
	}

	/**
	 * @return
	 */
	public String getShortValueDescription() {
		return shortValueDescription;
	}

	/**
	 * @param string
	 */
	public void setShortValueDescription(String string) {
		shortValueDescription = string;
	}

	/**
	 * @param string
	 */
	public void setValueDescription(String string) {
		valueDescription = string;
	}

	/**
	 * @param decimal
	 */
	public void setValueNumeric(BigDecimal decimal) {
		valueNumeric = decimal;
	}

	/**
	 * @param string
	 */
	public void setValueRecordIdCode(String string) {
		valueRecordIdCode = string;
	}

	/**
	 * @param string
	 */
	public void setValueReference(String string) {
		valueReference = string;
	}

	/**
	 * @param integer
	 */
	public void setValueSequenceNumber(Integer integer) {
		valueSequenceNumber = integer;
	}
	
/**
 * Update the Analytical Code Value data base record for
 * this current class object.
 * The data file is INPGANAL.
 * 
 * Throws String error. 
 */
public void update() throws Exception
{
	String throwError = "";

	// Update a INPGANAL record.
	try {
		PreparedStatement updateIt = (PreparedStatement) updateByCodeTypeValue.pop();
		updateIt.setString(1,  valueRecordIdCode);
		updateIt.setInt(2,  valueSequenceNumber.intValue());
		updateIt.setString(3,  shortValueDescription);
		updateIt.setString(4,  valueReference);
		updateIt.setString(5,  valueDescription);
		updateIt.setString(6, specification);
		updateIt.setString(7, invGroupCode1);
		updateIt.setString(8, invGroupCode2);
		updateIt.setString(9, invGroupCode3);		
		updateIt.setString(10,  analyticalCode);
		updateIt.setString(11,  codeType);
		updateIt.setInt(12,  valueNumeric.intValue());
		updateIt.setString(13, invGroupCode1);
		updateIt.setString(14, invGroupCode2);
		updateIt.setString(15, invGroupCode3);
		updateIt.executeUpdate();
		updateByCodeTypeValue.push(updateIt);	
	} catch (Exception e) {
		System.out.println("Error at com.treetop.data." +
					 "AnalyticalCodeValue.update(): unable to update record." + e);
	}

	if (!throwError.equals(""))
	{
		throwError = throwError + "Error at com.treetop.data." +
					 "AnalyticalCodeValue.update() ";	
		throw new Exception(throwError);
	}

	return;	
}		

	/**
	 * @return
	 */
	public String getSpecification() {
		return specification;
	}

	/**
	 * @param string
	 */
	public void setSpecification(String string) {
		specification = string;
	}

}
