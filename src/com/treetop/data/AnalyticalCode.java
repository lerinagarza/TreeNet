/*
 * Created on November 30, 2004 x
**/
package com.treetop.data;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import javax.sql.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import com.treetop.view.*;
/**
 * Analytical Identification Code For Inventory.
**/
public class AnalyticalCode {
	/**
	 * Test the Code Type to set the SQL "WHERE" clause.
	 *
	 */
	public static String sqlWhereCodeType(AnalyticalCodeValue ac,
										  String whereClause) 
	{
	
		String  sqlWhere = "";
			
		try {			
			
			if ((ac.codeType != null) && (!ac.codeType.equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
				 
				if ((foundWhere >= 0) || (foundLike >= 0) || (foundBetween >= 0))
					sqlWhere = sqlWhere + "AND ";		
				
				sqlWhere = sqlWhere + "(INCTYP = '" + ac.codeType + "') ";          
			}		 
		}
	
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data." +
								"AnalyticalCode.sqlWhereCodeType" +
								"(Class String): " + e);
		}	
		
		return sqlWhere; 	 
		
	}

	/**
	 * Test the Code Type to set the SQL "WHERE" clause.
	 *
	 */
	public static String sqlWhereInvGroupCode1(AnalyticalCodeValue ac,
											   String whereClause) 
	{
	
		String  sqlWhere = "";
			
		try {			
			
			if ((ac.getInvGroupCode1() != null) && (!ac.getInvGroupCode1().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
				 
				if ((foundWhere >= 0) || (foundLike >= 0) || (foundBetween >= 0))
					sqlWhere = sqlWhere + "AND ";	
				
				sqlWhere = sqlWhere + "(INCGC1 = '" + ac.getInvGroupCode1() + "') ";          
			}		 
		}
	
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data." +
								"AnalyticalCode.sqlWhereInvGroupCode1" +
								"(Class String): " + e);
		}	
		
		return sqlWhere;	 
		
	}

	/**
	 * Test the Code Type to set the SQL "WHERE" clause.
	 *
	 */
	public static String sqlWhereInvGroupCode2(AnalyticalCodeValue ac,
											   String whereClause) 
	{
	
		String  sqlWhere = "";
			
		try {			
			
			if ((ac.getInvGroupCode2() != null) && (!ac.getInvGroupCode2().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
				 
				if ((foundWhere >= 0) || (foundLike >= 0) || (foundBetween >= 0))
					sqlWhere = sqlWhere + "AND ";	
				
				sqlWhere = sqlWhere + "(INCGC2 = '" + ac.getInvGroupCode2() + "') ";          
			}		 
		}
	
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data." +
								"AnalyticalCode.sqlWhereInvGroupCode2" +
								"(Class String): " + e);
		}	
		
		return sqlWhere;	 
		
	}

	/**
	 * Test the Code Type to set the SQL "WHERE" clause.
	 *
	 */
	public static String sqlWhereInvGroupCode3(AnalyticalCodeValue ac,
											   String whereClause) 
	{
	
		String  sqlWhere = "";
			
		try {			
			
			if ((ac.getInvGroupCode3() != null) && (!ac.getInvGroupCode3().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
				 
				if ((foundWhere >= 0) || (foundLike >= 0) || (foundBetween >= 0))
					sqlWhere = sqlWhere + "AND ";	
				
				sqlWhere = sqlWhere + "(INCGC3 = '" + ac.getInvGroupCode3() + "') ";          
			}		 
		}
	
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data." +
								"AnalyticalCode.sqlWhereInvGroupCode3" +
								"(Class String): " + e);
		}	
		
		return sqlWhere;	 
		
	}
	
	// Data base fields from files IDPSANAL and INPCANAL.
	
	protected	String		recordCode;				// IDSREC
	protected   String		recordDeleteCode;		// IDSDLT
	protected	String      analyticalCode;			// IDSCDE
	protected	String      codeType;				//  INCTYP
	protected   String		typeOfSpecification;	// IDSTYP
	protected	Integer		decimalPositions;		// IDSDEC
	protected	Integer		decimalsToDisplay;		//  INCDTD
	protected	String		unitOfMeasure;			// IDSUNT
	protected	String		methodOfMeasureNumber;	// IDSMTH
	protected	String		shortDescription;		// IDSDSC
	protected	BigDecimal	targetValue;			// IDSDTA
	protected	BigDecimal	upperStandardLimit;		// IDSSHI
	protected	BigDecimal	lowerStandardLimit;		// IDSSLO
	protected	String		unitDescription;		// IDSUDS
	protected	Integer		oldSortSequence;		// IDSSEQ
	protected	Integer		newSortSequence;		//  INCSEQ
	protected	String		productGroupingA;		// IDSPGA
	protected	String		productGroupingB;		// IDSPGB
	protected	String		productGroupingC;		// IDSPGC
	protected	String		productGroupingD;		// IDSPGD
	protected	String		productGroupingE;		// IDSPGE
	protected	String		productGroupingF;		// IDSPGF
	protected	String		productGroupingG;		// IDSPGG
	protected	String		productGroupingH;		// IDSPGH
	protected	String		productGroupingI;		// IDSPGI
	protected	String		productGroupingJ;		// IDSPGJ
	protected	String		productGroupingK;		// IDSPGK
	protected	String		productGroupingL;		// IDSPGL
	protected	String		productGroupingM;		// IDSPGM
	protected	String		productGroupingN;		// IDSPGN
	protected	String		productGroupingO;		// IDSPGO
	protected	String		productGroupingP;		// IDSPGP
	protected	String		productGroupingQ;		// IDSPGQ
	protected	String		productGroupingR;		// IDSPGR
	protected	String		productGroupingS;		// IDSPGS
	protected	String		productGroupingT;		// IDSPGT
	protected	String		coaHdr1;				// IDSHD1
	protected	String		coaHdr2;				// IDSHD2
	protected	String		coaHdr3;				// ISDHD3
	protected	String		identGroupingCode;		// IDSGRP
	protected	String		division;				// IDSDIV
	protected	BigDecimal	testedBrixLevel;		// IDSTBX	
	protected	String      valueList;				//  INCVL
	protected	String      longDescription;		//  INCDSC
	protected	String		analyticalReference;	//  INCREF
	protected   String		positiveNegitive;		// IDSVAL
	protected	String		requiredValue;			// 
	protected	String		invGroupCode1;			//	INCGC1
	protected	String		invGroupCode2;			//	INCGC2
	protected	String		invGroupCode3;			//	INCGC3
		
	protected   String      orderByField;
	protected   String      orderByStyle;					
	
	// Static class fields.
	private static boolean analPersists = false;
			
	// Define database environment (live or test) on the AS/400.	
	private static String library  = "DBLIB.";      // live environment
	private static String library2 = "HSLIB.";
	//private static String wklib  = "WKLIB.";       // test environment
		
	// sql prepared statements..
	private static Stack findIdentWithoutType = null;
	private static Stack findAllIdentsByType = null;
	private static Stack findAllIdentsByTypeDropDown = null;
	private static Stack findIdentByCode = null;
	private static Stack findIdentByTypeCodeGroup = null;
	private static Stack addIdent = null;
	private static Stack deleteIdent = null;
	private static Stack updateByCodeAndType = null;	    

					
	
	/**
	 * Instantiate the Analytical Code class.
	**/
	public AnalyticalCode() {
		super();
		
		init();
	}
	
	/**
	 * Instantiate the Analytical Code class
	 * from a result set.
	**/
	public AnalyticalCode(ResultSet rs) {
		super();
		
		init();
		
		loadFields(rs);	
	}

	/**
	 * Instantiate the Analytical Code class 
	 * using a Analytical Inventory Code.
	 */
	public AnalyticalCode(String inAnalyticalCode, 
						  String inCodeType,
						  String inInvGroupCode1,
						  String inInvGroupCode2,
						  String inInvGroupCode3) 
						  throws InstantiationException {

		String errorMessage = "";
		
		try{
			init();
			ResultSet rs = null;
		
			// Analytical Code for Raw Fruit (RF).
			if (inCodeType.equals("RF") )
			{
				try {
					PreparedStatement findIt = (PreparedStatement) findIdentByTypeCodeGroup.pop();
				
					try {
						findIt.setString(1, inCodeType);
						findIt.setString(2, inAnalyticalCode);
						findIt.setString(3, inInvGroupCode1);
						findIt.setString(4, inInvGroupCode2);
						findIt.setString(5, inInvGroupCode3);						
						rs = findIt.executeQuery();
		
					}
					catch (Exception e) {			
						errorMessage = "Exception error creating a result set " +
									   "error: " + e;								 
					}
		
					findIdentByTypeCodeGroup.push(findIt);
				}
				catch (Exception e) {			
					errorMessage = "Exception error processing a result set " +
								   "error: " + e;								 
				}
			}
			
			// Analytical Code for Dried (DR).
			if (inCodeType.equals("DR") )
			{
				try {
					PreparedStatement findIt = (PreparedStatement) findIdentByCode.pop();
			
					try {
						findIt.setString(1, inCodeType);
						findIt.setString(2, inAnalyticalCode);
						rs = findIt.executeQuery();
	
					}
					catch (Exception e) {			
						errorMessage = "Exception error creating a result set " +
									   "error: " + e;								 
					}
	
					findIdentByCode.push(findIt);
				}
				catch (Exception e) {			
					errorMessage = "Exception error processing a result set " +
								   "error: " + e;								 
				}
			}
			
			try {

				if (rs.next()) 
					loadFields(rs);	
			else
				errorMessage = "Exception error, The requested analytical code " +
								" could not be found. ";		
			}
			catch (Exception e) {			
				errorMessage = "Exception error processing a result set " +
								"error: " + e;									 
			}
			
			rs.close();

			
			if (!inCodeType.equals("RF") &&
				!inCodeType.equals("DR"))
				errorMessage = "Exception error, Analytical Code Type of (" + inCodeType +
								" ) could not be identified. ";
		} 
		catch (Exception e) {	
			errorMessage = "Exception error unable to build instance. " +
							"error:" + e;		
		}
		
		if (!errorMessage.equals(""))
		{
			errorMessage = errorMessage +
						   " @ com.treetop.data.AnalyticalCode(" +
						   "code:" + inAnalyticalCode +
						   " type:" + inCodeType +
						   " grpCode1:" + inInvGroupCode1 +
						   " grpCode2:" + inInvGroupCode2 +
						   " grpCode3:" + inInvGroupCode3 + ") ";
		   throw new InstantiationException(errorMessage);
		}	
	}

	/**
	 * Instantiate the Analytical Code class 
	 * using a Analytical Code Without Inventory Type.
	 */
	public AnalyticalCode(String inAnalyticalCode) 
						  throws InstantiationException {
	
		String errorMessage = "";
		
		try{
			init();
			ResultSet rs = null;

			try {
				PreparedStatement findIt = (PreparedStatement) findIdentWithoutType.pop();
				
				try {
					findIt.setString(1, inAnalyticalCode);					
					rs = findIt.executeQuery();
		
				}
				catch (Exception e) {			
					errorMessage = "Exception error creating a result set " +
									"at com.treetop.data." +
									"AnalyticalCode(code:" + inAnalyticalCode +
									") " + e;								 
				}
		
				findIdentWithoutType.push(findIt);
			
				try {
	
					if (rs.next()) 
						loadFields(rs);	
					else
						errorMessage = "Exception error, The requested analytical code " +
										" could not be found. (error @ com.treetop.data." +
										"AnalyticalCode(code:" + inAnalyticalCode + ")";	
				}
				catch (Exception e) {			
					errorMessage = "Exception error processing a result set " +
										"at com.treetop.data." +
							    		"AnalyticalCode(code:" + inAnalyticalCode + ")" + e;									 
				}
			
				rs.close();
			}
			catch (Exception e) {
				errorMessage = "Exception error unable to build instance. " +
								"at com.treetop.data.AnalyticalCode(code:" + 
								inAnalyticalCode + ") " + e;
			}
	
		}
		catch (Exception e) {	
			errorMessage = "Exception error unable to build instance. " +
							"at com.treetop.data.AnalyticalCode(code:" + 
				 			inAnalyticalCode + ") " + e;		
		}
		
		if (!errorMessage.equals(""))
		   throw new InstantiationException(errorMessage);		
	}

/**
* Add a Analytical Code Record (INPCANAL).
*  Code type must not be blank.
*  A matching code value must first exist in file IDPSANAL.
*  Code must not already exist in file INPCANAL.
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
					 "Analytical Code and Type. " +
					 "The code (" + analyticalCode + ") " +
					 "and type (" + codeType + ") " +
					 "Analytical Code or Code Type are blank. ";
					 
	if (throwError.equals(""))
	{
		// test to see if a Ident entry exists in the 
		// Analytical Code File "IDPSANAL".
		try {
			addIt = (PreparedStatement) addIdent.pop();
			popped = "yes";
			addIt.setString(1,  analyticalCode);
			addIt.setString(2,  codeType);
			addIt.setString(3,  valueList);
			addIt.setString(4,  analyticalReference);
			addIt.setString(5,  longDescription);
			addIt.setInt(6,  decimalsToDisplay.intValue());
			addIt.setInt(7,  newSortSequence.intValue());
			addIt.setString(8, requiredValue);
			addIt.setString(9, invGroupCode1);
			addIt.setString(10, invGroupCode2);
			addIt.setString(11, invGroupCode3);
					              	
			// Test to determine if Code/Type already exists.
			try {
				AnalyticalCode alreadyExists = new AnalyticalCode(analyticalCode, 
																  codeType,
																  invGroupCode1,
																  invGroupCode2,
																  invGroupCode3);
				throwError = "Unable to add this requested " +
							 "Analytical Code and Type. " +
							 "The code (" + analyticalCode + ") " +
							 "and type (" + codeType + ") " +
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
						 "Analytical Code (" +
						 analyticalCode + ") " +
						 "Code Type (" +
						 codeType + "). " +
						 "Please contact IS Help Desk for " +
						 "assistance. error @ " + e;
		}
			
		if (popped.equals("yes"))
			addIdent.push(addIt);
	}
		
	if (!throwError.equals(""))
	{
		throwError = throwError + " com.treetop.data." +
					 "AnalyticalCode.add()";	
		throw new Exception(throwError);
	}
	
	return;	
}

/**
 * Delete the record from file INPCANAL.
 * 
 */
public void delete() {

	// Delete a INPCANAL record.
	try {
		PreparedStatement deleteIt = (PreparedStatement) deleteIdent.pop(); 
		deleteIt.setString(1, codeType);
		deleteIt.setString(2, analyticalCode);
		deleteIt.setString(3, invGroupCode1);
				
		deleteIt.executeUpdate();
		deleteIdent.push(deleteIt);	

	} catch (Exception e) {
		System.out.println("error at com.treetop.data." +
						   "AnalyticalCode.delete():" + e);
	}
}	
	
/**
 * 
**/
	public void init() {
		
		// Test for prior initialization.
	
		if (analPersists == false) {	
			analPersists = true;						
		
			// Perform initialization create connection pool. 
 
			try {

				//com.treetop.ConnectionPool connectionPool = new com.treetop.ConnectionPool();
				Connection conn1 = null;
				Connection conn2 = null;
				Connection conn3 = null;
				Connection conn4 = null;
				Connection conn5 = null;
				
				
				// Find Analytical Code Without InventoryType.
				String findIt = 
					"SELECT * " +
					"FROM " + library + "IDPSANAL " +
					"WHERE IDSCDE = ? " +
					" AND IDSDLT <> 'D' ";
					
				PreparedStatement ps1 = conn1.prepareStatement(findIt);	 				         
				PreparedStatement ps2 = conn2.prepareStatement(findIt);	
				PreparedStatement ps3 = conn3.prepareStatement(findIt);	
				PreparedStatement ps4 = conn4.prepareStatement(findIt);	
				PreparedStatement ps5 = conn5.prepareStatement(findIt);	

				findIdentWithoutType = new Stack();
				findIdentWithoutType.push(ps1);
				findIdentWithoutType.push(ps2);
				findIdentWithoutType.push(ps3);
				findIdentWithoutType.push(ps4);
				findIdentWithoutType.push(ps5);
				
				// Find all Analytical Codes By Type.			
				findIt =
				  "SELECT * " + 
				  "FROM " + library + "INPCANAL " +
				  "INNER JOIN " + library + "IDPSANAL ON INCCDE = IDSCDE " +
				  "WHERE INCTYP = ? " +
				  "  AND IDSDLT <> 'D' " +			
				  "ORDER BY INCSEQ, INCCDE ";
				  
				ps1 = conn1.prepareStatement(findIt);	 				         
				ps2 = conn2.prepareStatement(findIt);	
				ps3 = conn3.prepareStatement(findIt);	
				ps4 = conn4.prepareStatement(findIt);	
				ps5 = conn5.prepareStatement(findIt);	

				findAllIdentsByType = new Stack();
				findAllIdentsByType.push(ps1);
				findAllIdentsByType.push(ps2);
				findAllIdentsByType.push(ps3);
				findAllIdentsByType.push(ps4);
				findAllIdentsByType.push(ps5);	
						
				// Find all Analytical Codes By Type For Drop Down List.			
				findIt =
				  "SELECT IDSCDE, IDSDSC " + 
				  "FROM " + library + "INPCANAL " +
				  "INNER JOIN " + library + "IDPSANAL ON INCCDE = IDSCDE " +
				  "WHERE INCTYP = ? " +
				  "  AND IDSDLT <> 'D' " +			
				  "GROUP BY IDSCDE, IDSDSC " +
				  "ORDER BY IDSCDE, IDSDSC ";
				  
				ps1 = conn1.prepareStatement(findIt);	 				         
				ps2 = conn2.prepareStatement(findIt);	
				ps3 = conn3.prepareStatement(findIt);	
				ps4 = conn4.prepareStatement(findIt);	
				ps5 = conn5.prepareStatement(findIt);	

				findAllIdentsByTypeDropDown = new Stack();
				findAllIdentsByTypeDropDown.push(ps1);
				findAllIdentsByTypeDropDown.push(ps2);
				findAllIdentsByTypeDropDown.push(ps3);
				findAllIdentsByTypeDropDown.push(ps4);
				findAllIdentsByTypeDropDown.push(ps5);	
		    				
				// Find Analytical Code By Code and Type.			
				findIt =
				  "SELECT * " + 
				  "FROM " + library + "IDPSANAL " +
				  "LEFT OUTER JOIN " + library + "INPCANAL ON INCCDE = IDSCDE " +
				  "WHERE INCTYP = ? " +
				  "  AND IDSCDE = ? " +
				  "  AND IDSDLT <> 'D' " ;			
				  
				ps1 = conn1.prepareStatement(findIt);	 				         
				ps2 = conn2.prepareStatement(findIt);	
				ps3 = conn3.prepareStatement(findIt);	
				ps4 = conn4.prepareStatement(findIt);	
				ps5 = conn5.prepareStatement(findIt);	

				findIdentByCode = new Stack();
				findIdentByCode.push(ps1);
				findIdentByCode.push(ps2);
				findIdentByCode.push(ps3);
				findIdentByCode.push(ps4);
				findIdentByCode.push(ps5);
				
				
				// Find Analytical Code By Code, Type, 
				// and InvGroupCodes.			
				findIt =
				  "SELECT * " + 
				  "FROM " + library + "IDPSANAL " +
				  "LEFT OUTER JOIN " + library + "INPCANAL ON INCCDE = IDSCDE " +
				  "WHERE INCTYP = ? " +
				  "  AND IDSCDE = ? " +
				  "  AND INCGC1 = ? " +
				  "  AND INCGC2 = ? " +
				  "  AND INCGC3 = ? " +
				  "  AND IDSDLT <> 'D' " ;			
				  
				ps1 = conn1.prepareStatement(findIt);	 				         
				ps2 = conn2.prepareStatement(findIt);	
				ps3 = conn3.prepareStatement(findIt);	
				ps4 = conn4.prepareStatement(findIt);	
				ps5 = conn5.prepareStatement(findIt);	

				findIdentByTypeCodeGroup = new Stack();
				findIdentByTypeCodeGroup.push(ps1);
				findIdentByTypeCodeGroup.push(ps2);
				findIdentByTypeCodeGroup.push(ps3);
				findIdentByTypeCodeGroup.push(ps4);
				findIdentByTypeCodeGroup.push(ps5);
			

				// Update By Analytical Code and Code Type.
				String updateIt =
					"UPDATE " + library + "INPCANAL " +
					" SET INCVL  = ?, INCREF = ?, INCDSC = ?, " +
					"     INCDTD = ?, INCSEQ = ?, INCREQ = ?, " +
					"     INCGC1 = ?, INCGC2 = ?, INCGC3 = ? " +
					" WHERE INCCDE = ? AND INCTYP = ? " +
					"   AND INCGC1 = ? ";

				ps1 = conn1.prepareStatement(updateIt);
				ps2 = conn2.prepareStatement(updateIt);
				ps3 = conn3.prepareStatement(updateIt);
				ps4 = conn4.prepareStatement(updateIt);
				ps5 = conn5.prepareStatement(updateIt);

				updateByCodeAndType = new Stack();
				updateByCodeAndType.push(ps1);
				updateByCodeAndType.push(ps2);
				updateByCodeAndType.push(ps3);
				updateByCodeAndType.push(ps4);
				updateByCodeAndType.push(ps5);



				// Add a record to INPCANAL.
				String insertIt =
					"INSERT INTO " + library + "INPCANAL " +
					" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
					
				ps1 = conn1.prepareStatement(insertIt);
				ps2 = conn2.prepareStatement(insertIt);
				ps3 = conn3.prepareStatement(insertIt);
				ps4 = conn4.prepareStatement(insertIt);
				ps5 = conn5.prepareStatement(insertIt);

				addIdent = new Stack();
				addIdent.push(ps1);
				addIdent.push(ps2);
				addIdent.push(ps3);
				addIdent.push(ps4);
				addIdent.push(ps5);


				// Delete Analytical Code.
				String deleteIt =		
					"DELETE FROM " + library + "INPCANAL " +
					" WHERE INCTYP = ? AND INCCDE = ? " +
					"   AND INCGC1 = ? ";

				ps1 = conn1.prepareStatement(deleteIt);
				ps2 = conn2.prepareStatement(deleteIt);
				ps3 = conn3.prepareStatement(deleteIt);
				ps4 = conn4.prepareStatement(deleteIt);
				ps5 = conn5.prepareStatement(deleteIt);
			
				deleteIdent = new Stack();
				deleteIdent.push(ps1);
				deleteIdent.push(ps2);
				deleteIdent.push(ps3);
				deleteIdent.push(ps4);
				deleteIdent.push(ps5);
							

				// Return the connections back to the pool.
		
			//	connectionPool.returnConnection(conn1);
			//	connectionPool.returnConnection(conn2);
			//	connectionPool.returnConnection(conn3);
			//	connectionPool.returnConnection(conn4);
			//	connectionPool.returnConnection(conn5);				
				
			}				

			catch (SQLException e) {
					System.out.println("SQL exception occured at " +
					"com.treetop.data.AnalyticalCode.init()" + e);
			}			
		}					 			         					
	}
	
	/** 
	 * Build a drop down list of analytical codes by type.
	 *  parameters: String fieldName: used for the returning dropdown list name.
	 *              int widthPercent: size it or default of 0.
	 *              String inData: used for specific selection.
	 * 				String inData2: used to obtain the Analytical code Type.
	 * 				String inSelect: used to place other info on dropdown list (*all, etc). 
	 *
	 */
	public static String buildDropDownListByCodeType(String fieldName,
													 int widthPercent, 
													 String inData,
													 String inData2, 
													 String useThis) {

		String dropDownList = "";
		String selected     = "";
		String selectOption = "*All";
		String listName     = fieldName;
		
		if (!useThis.equals(""))
			selectOption = useThis;
			
		if (listName.trim().equals(""))
			listName = "code";
			
		String widthSize = "";
		
		if (widthPercent != 0)
			widthSize = " style='width:" +widthPercent + "%'";
				
		AnalyticalCode justInitialize = new AnalyticalCode();	
		
		try {
		
			PreparedStatement findThem = (PreparedStatement) 
											AnalyticalCode.findAllIdentsByTypeDropDown.pop();
			findThem.setString(1, inData2);
			ResultSet rs = null;
		
				try {						
					rs = findThem.executeQuery();
				}
				catch (Exception e) {
					System.out.println("Exception error creating a result set " +
										"at com.treetop.data.AnalyticalCode." +
										"buildDataDropDownListbyCodetype): " + e);
				}
		
				AnalyticalCode.findAllIdentsByTypeDropDown.push(findThem);
				
				
				// Process the result set to create a drop down list.
				
				try {
						        
					while (rs.next())
					{				
						String code = rs.getString("IDSCDE");									
						String desc = rs.getString("IDSDSC");										
					
						if (inData.trim().equals(code.trim()))
							selected = "' selected='selected'>";
						else
							selected = "'>";	 
	   		   
						dropDownList = dropDownList + "<option value='" + 
						code.trim() + selected +
						code.trim() + " - " + desc.trim() + "&nbsp;";					
					}
	   		
					if (!dropDownList.equals(""))   		    	   		    
						dropDownList = "<select name='" + fieldName + "'" + widthSize + ">" +
									   "<option value='None' selected>" + selectOption +
									   dropDownList + "</select>";					
									
				}
				catch (Exception e) {
					System.out.println("Exception error processing a result set " + 
										"at com.treetop.data.AnalyticalCode." +
                                       ".buildDropDownListbyCodeType): " + e);
				}        

				rs.close();					

		}	
		catch (Exception e) {
			System.out.println("Exception error processing com.treetop.data." + 
							   "AnalyticalCode.buildDropDownListbyCodeType" +
							   "(" + fieldName + ", " +  widthPercent + ", " +
							   inData + ", " + inData2 + ", " + useThis +
							   "): " + e);
		}
	
		return dropDownList;
	}	
	
	/**
	 * Retrieve Analytical Codes for a specific Analytical 
	 *  Type in sort order by New Sequence Number.
	 *
	 */
	public static Vector findAnalyticalCodesByNewSeqForType(String inCodeType,
															String deleteMe) {

		Vector list = new Vector();
		AnalyticalCode justInitialize = new AnalyticalCode();	

	
		try {
		
			PreparedStatement findThem = (PreparedStatement) AnalyticalCode.findAllIdentsByType.pop();
			findThem.setString(1, inCodeType);
			ResultSet rs = null;
		
			try {										
				rs = findThem.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
									"at com.treetop.data." +
								   "AnalyticalCode." +
								   "findAnalyticalCodesByNewSeqForType(): " + e);
			}
		
			AnalyticalCode.findAllIdentsByType.push(findThem);

			try {
				
				while (rs.next())
				{
					AnalyticalCode ac = new AnalyticalCode(rs);					
					list.addElement(ac);		
				}
			
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set " +
									"at com.treetop.data." +
									"AnalyticalCode." + 
									"findAnalyticalCodesByNewSeqForType(): " + e);
			}
				
			rs.close();	
				
		}	 
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data." +
							   "AnalyticalCode." +
							   "findAnalyticalCodesByNewSeqForType(): " + e);
		}	
			
		return list; 
	}

/**
 * Set vector to return Analytical Codes using selections 
 * from incomming class parameters.
 *
 */
public static Vector findAnalyticalCodesInquiry(AnalyticalCodeValue fromClass,
												AnalyticalCodeValue toClass) 
{
	Vector    list = new Vector();
	String    whereClause  = "";
	String    whereLike    = "";
           
        
	// Initial SQL statement for selection.    
	String sqlStatement = "SELECT *" + 
		" FROM " + library + "IDPSANAL " +                      
		"LEFT OUTER JOIN " + library + "INPCANAL " +
		"  ON IDSCDE = INCCDE ";
		
		
	// Add a INNER JOIN to the sql statement if the 
	// selection request asked for a specific analytical
	// value. Add a join to the INPGANAL file.
	BigDecimal value = new BigDecimal(0);
	if (fromClass.getValueNumeric() == null)
		value = new BigDecimal(0);
	else
		value = fromClass.getValueNumeric();
		
	BigDecimal zero = new BigDecimal(0); 
	int x = value.compareTo(zero);
	
	if (x != 0)
		sqlStatement = sqlStatement +
		" INNER JOIN " + library + "INPGANAL " +
		"  ON INCCDE = INGCDE ";
	    

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

		String orderBy = sqlOrderBy(fromClass);
			sqlStatement   = sqlStatement + orderBy;   
         
		// Execute the SQL statement using a connection pool.
//		com.treetop.ConnectionPool connectionPool = new com.treetop.ConnectionPool();        
		Connection conn = null;    

		Statement  stmt = conn.createStatement();    
		ResultSet  rs   = stmt.executeQuery(sqlStatement);                
          
		//connectionPool.returnConnection(conn);                          

		// Process the SQL result into the return data class vector elements. 
		try {
	        
			while (rs.next())
			{
				AnalyticalCode buildVector = new AnalyticalCode();
				buildVector.loadFields(rs);
				list.addElement(buildVector); 
			}		
						
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "at com.tretop.data.AnalyticalCode" +
							   ".findAnalyticalCodeInquiry(Class, Class): " + e);
		}

		rs.close();       
 
	}

	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data." +
						   "AnalyticalCode.findAnalyticalCode" +
						   "Inquiry(Class, Class): " + e);
	}

	return list;
     
}


/**
 * Return a result set of Analytical Codes that are either 
 * assigned to on of the incomming lots or assigned to 
 * the incoming specification
 * 
 * @param lotNumber
 * @param lotType
 * @return result set
 */
public static ResultSet findRfCodesForLots(Vector lots,
										   String lotTypeIn,
										   String specIn,
										   Integer specRevisionDate)
{
	ResultSet  rs   = null;
	AnalyticalCode ji = new AnalyticalCode();
	
	// Initial sql statement.
	String  sqlStatement = "SELECT * FROM " + library +
						   "INPCANAL " +
						   "INNER JOIN " + library + 
						   "INPWSPIN ON INCCDE = INWCDE " +
						   "        AND INCGC1 = INWGC1 " +
						   "LEFT OUTER JOIN " + library +
						   "INPGANAL ON INWCDE = INGCDE " +
						   "AND INWGC1 = INGGC1 " +
						   "AND INWNV = INGNV " +
						   "WHERE INWLOT IN(";
	for (int x=0; x < lots.size(); x++)
	{
		String lot = (String) lots.elementAt(x);
		
		if (x > 0 && !lot.trim().equals(""))
			sqlStatement = sqlStatement + ", ";
		
		if (!lot.trim().equals(""))
			sqlStatement = sqlStatement + "'" + lot.trim() +
						   "'";
	}
						   
	sqlStatement = sqlStatement + ") " +
				   "ORDER BY INCSEQ, INCCDE, INWLOT ";
				   
	try {	           	                           
		// Execute the SQL statement using a connection pool.
	//	com.treetop.ConnectionPool connectionPool = new com.treetop.ConnectionPool();        
	//	Connection conn = connectionPool.getConnection();    

	//	Statement  stmt = conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
//												ResultSet.CONCUR_UPDATABLE);
		
	//	rs = stmt.executeQuery(sqlStatement);
		
		//return to connection pool.
	//	connectionPool.returnConnection(conn);
	}
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data." +
						   "AnalyticalCode.findRfCodesForLota" +
						   "(Vector, String, String, Integer): " +
						    e);
	}
		
	return rs;
}

/**
 * Return a result set of Analytical Codes that are either 
 * assigned to on of the incomming lots or assigned to 
 * the incoming specification
 * 
 * @param lotNumber
 * @param lotType
 * @return result set
 */
public static ResultSet findRfSampleDateLots(VectorView5 dates,
											 String lotNumberIn,
											 String lotTypeIn,
											 String invGroupCodeIn)
{
	ResultSet  rs   = null;
	AnalyticalCode ji = new AnalyticalCode();
	
	// Initial sql statement.
	String  sqlStatement = "SELECT * FROM " + library +
						   "INPCANAL " +
						   "INNER JOIN " + library + 
						   "INPYSPIN ON INCCDE = INYCDE " +
						   "AND INCGC1 = INYGC1 " +
						   "LEFT OUTER JOIN " + library +
						   "INPWSPIN ON INYLOT = INWLOT " +
						   "AND INYTYP = INWTYP " +
						   "AND INYSDT = INWSDT " +
						   "AND INYSTM = INWSTM " +
						   "AND INYCDE = INWCDE " +
						   "LEFT OUTER JOIN " + library +
						   "INPGANAL one ON INYCDE = INGCDE " +
						   "AND INYGC1 = INGGC1 " +
						   "AND INYNV = INGNV " +
						   "WHERE INYLOT = '" + lotNumberIn +
						   "' AND INYTYP = '" + lotTypeIn +
						   "' AND (INYSDT IN("; 
						   
	for (int x=0; x < dates.vector3.size(); x++)
	{
		String rDate = (String) dates.vector3.elementAt(x);
		
		if (x > 0 && !rDate.trim().equals(""))
			sqlStatement = sqlStatement + ", ";
		
		if (!rDate.trim().equals(""))
			sqlStatement = sqlStatement + rDate.trim();		   
	}
	
	sqlStatement = sqlStatement + ") AND INYSTM IN(";
	
	for (int x=0; x < dates.vector4.size(); x++)
	{
		String rTime = (String) dates.vector4.elementAt(x);
		
		if (x > 0 && !rTime.trim().equals(""))
			sqlStatement = sqlStatement + ", ";
		
		if (!rTime.trim().equals(""))
			sqlStatement = sqlStatement  + rTime.trim();
	}
						   
	sqlStatement = sqlStatement + ") )" +
				   "ORDER BY INCSEQ, INCCDE, INYSDT DESC, " +
				   "INYSTM DESC ";
				   
	
				   
	try {	           	                           
		// Execute the SQL statement using a connection pool.
//		com.treetop.ConnectionPool connectionPool = new com.treetop.ConnectionPool();        
//		Connection conn = connectionPool.getConnection();    

//		Statement  stmt = conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
//												ResultSet.CONCUR_UPDATABLE);
		
//		rs = stmt.executeQuery(sqlStatement);
		
		//return to connection pool.
//		connectionPool.returnConnection(conn);
	}
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data." +
						   "AnalyticalCode.findRfSampleDateLots" +
						   "(VectorView5, String, String): " +
						    e);
	}
		
	return rs;
}

/**
 * Return a result set of Analytical Codes that are  
 * assigned to on of the incomming lot and Sample
 * Dates.
 * 
 * @param Sample Date and Time
 * @param Lot Number
 * @param lotType
 * @return result set
 */
public static ResultSet findRfCodesForDates(Vector dates,
										    String lotTypeIn,
										    String specIn,
										    Integer specRevisionDate)
{
	ResultSet  rs   = null;
	AnalyticalCode ji = new AnalyticalCode();
	
	// Initial sql statement.
	String  sqlStatement = "SELECT * FROM " + library +
						   "INPCANAL " +
						   "INNER JOIN " + library + 
						   "INPWSPIN ON INCCDE = INWCDE " +
						   "LEFT OUTER JOIN " + library +
						   "INPYSPIN ON INCCDE = INYCDE " +
						   "LEFT OUTER JOIN " + library +
						   "INPGANAL ON INWCDE = INGCDE " +
						   "AND INWNV = INGNV " +
						   "WHERE INYLOT IN(";
	for (int x=0; x < dates.size(); x++)
	{
		VectorView5 vv5 = (VectorView5) dates.elementAt(x);
		String rDate = (String) vv5.vector3.elementAt(0);
		String rTime = (String) vv5.vector4.elementAt(0);
		
		if (x > 0 )
			sqlStatement = sqlStatement + ", ";
		
		sqlStatement = sqlStatement + "('" + rDate.trim() +
						   "'";
	}
						   
	sqlStatement = sqlStatement + ") " +
				   "ORDER BY INCSEQ, INCCDE, INWLOT ";
				   
	try {	           	                           
		// Execute the SQL statement using a connection pool.
//		com.treetop.ConnectionPool connectionPool = new com.treetop.ConnectionPool();        
//		Connection conn = connectionPool.getConnection();    

//		Statement  stmt = conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
//												ResultSet.CONCUR_UPDATABLE);
		
//		rs = stmt.executeQuery(sqlStatement);
		
		//return to connection pool.
//		connectionPool.returnConnection(conn);
	}
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data." +
						   "AnalyticalCode.findRfCodesForLota" +
						   "(Vector, String, String, Integer): " +
						    e);
	}
		
	return rs;
}

/**
 * Return a result set of Analytical Codes that are either 
 * assigned to on of the incomming lots or assigned to 
 * the incoming specification
 * 
 * @param lotNumber
 * @param lotType
 * @return result set
 */
public static ResultSet findDrCodesForLots(Vector lots,
										   String lotTypeIn,
										   String specIn,
										   Integer specRevisionDate)
{
	ResultSet  rs   = null;
	AnalyticalCode ji = new AnalyticalCode();
	
	// Initial sql statement.
	String  sqlStatement = "SELECT * FROM " + library +
						   "INPCANAL " + 
						   "INNER JOIN " + library +
						   "IDPSANAL ON INCCDE = IDSCDE " +
						   "INNER JOIN " + library2 + 
						   "IDLWSPNE ON IDSCDE = IDWCDE " +
						   "LEFT OUTER JOIN " + library2 +
						   "IDLEINV6 ON IDWLOT = IDELOT " +
						   "LEFT OUTER JOIN " + library +
						   "INPILOT ON IDELOT = INILOT " +
						   "LEFT OUTER JOIN " + library +
						   "INPGANAL ON INCTYP = INGTYP " +
						   "AND INCCDE = INGCDE " +
						   "AND IDWDTA = INGNV " +
						   "WHERE INCTYP = 'DR' " +
						   "AND   IDWLOT IN(";
	for (int x=0; x < lots.size(); x++)
	{
		String lot = (String) lots.elementAt(x);
		
		if (x > 0 && !lot.trim().equals(""))
			sqlStatement = sqlStatement + ", ";
		
		if (!lot.trim().equals(""))
			sqlStatement = sqlStatement + "'" + lot.trim() +
						   "'";
	}
						   
	sqlStatement = sqlStatement + ") " +
				   "ORDER BY INCSEQ, IDWCDE, IDWLOT ";
				   
	try {	           	                           
		// Execute the SQL statement using a connection pool.
//		com.treetop.ConnectionPool connectionPool = new com.treetop.ConnectionPool();        
//		Connection conn = connectionPool.getConnection();    

//		Statement  stmt = conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
//												ResultSet.CONCUR_UPDATABLE);
		
//		rs = stmt.executeQuery(sqlStatement);
		
		//return to connection pool.
//		connectionPool.returnConnection(conn);
	}
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data." +
						   "AnalyticalCode.findDrCodesForLot" +
						   "(Vector, String, String, Integer): " +
						    e);
	}
		
	return rs;
}


/**
 * Test Analytical Code to set the SQL "WHERE" clause.
 *
 */
public static String sqlWhereAnalyticalCode(AnalyticalCodeValue ac, 
											String whereClause)
{

	String  sqlWhere = "";
	
	try {			
		
		if ((ac.analyticalCode != null) && 
			(!ac.analyticalCode.equals(""))) 
		{
			int foundWhere   = whereClause.indexOf("=");
			int foundLike    = whereClause.indexOf("LIKE");
			int foundBetween = whereClause.indexOf("BETWEEN");
			 
			if ((foundWhere >= 0) || (foundLike >= 0) || (foundBetween >= 0))
				sqlWhere = sqlWhere + "AND ";	
	   		   				    			   
			sqlWhere = sqlWhere +
						"(IDSCDE LIKE '" + ac.analyticalCode.trim().toUpperCase() + "%') ";
		}
	}

	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data." +
							"AnalyticalCode.sqlWhereAnalyticalCode" +
							"(Class String): " + e);
	}	
	
	return sqlWhere; 	
	
}


/**
 * Test Analytical Code to set the SQL "WHERE" clause.
 *
 */
public static String sqlWhereAnalyticalValue(AnalyticalCodeValue ac, 
											 String whereClause)
{

	String  sqlWhere = "";
	
	try {			
		if ((ac.getValueNumeric() != null) && 
			(ac.getValueNumeric().intValue() != 0)) 
		{
			int foundWhere   = whereClause.indexOf("=");
			int foundLike    = whereClause.indexOf("LIKE");
			int foundBetween = whereClause.indexOf("BETWEEN");
			 
			if ((foundWhere >= 0) || (foundLike >= 0) || (foundBetween >= 0))
				sqlWhere = sqlWhere + "AND ";	
	   		   				    			   
			sqlWhere = sqlWhere +
						"(INGNV = " + ac.getValueNumeric() + ") ";
		}
	}

	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data." +
							"AnalyticalCode.sqlWhereAnalyticalValue" +
							"(Class String): " + e);
	}	
	
	return sqlWhere; 	
	
}

/**
 * Test the Code Type to set the SQL "WHERE" clause.
 *
 *  Search to include Specification 6/2/05 TW
 */
public static String sqlWhereSpecification(AnalyticalCodeValue ac,
									       String whereClause) 
{

	String  sqlWhere = "";
		
	try {			
		
		if ((ac.getSpecification() != null) && 
		    (!ac.getSpecification().equals("")))
		{					   		
			int foundWhere   = whereClause.indexOf("=");
			int foundLike    = whereClause.indexOf("LIKE");
			int foundBetween = whereClause.indexOf("BETWEEN");
			 
			if ((foundWhere >= 0) || (foundLike >= 0) || (foundBetween >= 0))
				sqlWhere = sqlWhere + "AND ";		
			
			sqlWhere = sqlWhere + "(EXISTS(SELECT * FROM " + library +
			                        "IDPVSPDA " +
			                        "WHERE IDVSPC = '" + ac.getSpecification().toUpperCase() +
			                        "' AND IDSCDE = IDVCDE)) ";          
		}		 
	}

	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data." +
							"AnalyticalCode.sqlSpecification" +
							"(Class String): " + e);
	}	
	
	return sqlWhere; 	 
	
}

/**
 * Test the Long Description "WHERE" clause.
 *
 */
public static String sqlWhereLongDescription(AnalyticalCodeValue ac,
											 String whereClause)
{

	String  sqlWhere = "";
		
	try {			
		
		if ((ac.longDescription != null) && 
			(!ac.longDescription.equals("")))
		{					   		
			int foundWhere   = whereClause.indexOf("=");
			int foundLike    = whereClause.indexOf("LIKE");
			int foundBetween = whereClause.indexOf("BETWEEN");
			 
			if ((foundWhere >= 0) || (foundLike >= 0) || (foundBetween >= 0))
				sqlWhere = sqlWhere + "AND ";		
			
			sqlWhere = sqlWhere +
						"(INCDSC LIKE '%" + ac.longDescription + "%') ";
			//sqlWhere = sqlWhere + "AND " +
			//"(INCDSC LIKE '%" + ac.longDescription + "%') ";
		}		 
	}

	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data." +
							"AnalyticalCode.sqlWhereLongDescription" +
							"(Class String): " + e);
	}	
	
	return sqlWhere; 	 
	
}

/**
 * Test the Long Description "WHERE" clause.
 *
 */
public static String sqlWhereShortDescription(AnalyticalCodeValue ac,
											  String whereClause)
{

	String  sqlWhere = "";
		
	try {			
		
		if ((ac.shortDescription != null) && 
			(!ac.shortDescription.equals("")))
		{					   		
			int foundWhere   = whereClause.indexOf("=");
			int foundLike    = whereClause.indexOf("LIKE");
			int foundBetween = whereClause.indexOf("BETWEEN");
			 
			if ((foundWhere >= 0) || (foundLike >= 0) || (foundBetween >= 0))
				sqlWhere = sqlWhere + "AND ";		
			
			sqlWhere = sqlWhere +
						"(IDSDSC LIKE '%" + ac.shortDescription.toUpperCase() + "%') ";
		}		 
	}

	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data." +
							"AnalyticalCode.sqlWhereShortDescription" +
							"(Class String): " + e);
	}	
	
	return sqlWhere; 	 
	
}

/**
 * Build the SQL order by (sort).
 *
 */
public static String sqlOrderBy(AnalyticalCodeValue inClass){


	String orderBySql = "ORDER BY ";
	String orderBy    = "";
	String orderStyle = "";
		
	
	try {

		if (inClass.orderByField != null) 
		{
		
			if ((inClass.getOrderByStyle() == null) || 
				(inClass.getOrderByStyle().equals("")))
					orderStyle = orderStyle + ", ";
			else
				orderStyle = orderStyle + " " + inClass.getOrderByStyle().toUpperCase() + ", ";		       
		

			if (inClass.getOrderByField().trim().toLowerCase().equals("analyticalcode"))
				orderBy = orderBy + orderBySql + "IDSCDE" + orderStyle + "INCTYP" +
						  orderStyle + "INCGC1" + orderStyle + "INCGC2" +
						  orderStyle + "INCGC3";
			if (inClass.getOrderByField().toLowerCase().equals("codetype"))
				orderBy = orderBy + orderBySql + "INCTYP" + orderStyle + "IDSCDE" +
						  orderStyle + "INCGC1" + orderStyle + "INCGC2" +
						  orderStyle + "INCGC3";
			if (inClass.getOrderByField().toLowerCase().equals("longdescription"))
				orderBy = orderBy + orderBySql + "INCDSC" + orderStyle + "IDSCDE, INCTYP";
			if (inClass.getOrderByField().toLowerCase().equals("shortdescription"))
				orderBy = orderBy + orderBySql + "IDSDSC" + orderStyle + "IDSCDE, INCTYP";
			if (inClass.getOrderByField().toLowerCase().equals("newsortsequence"))
				orderBy = orderBy + orderBySql + "INCSEQ" + orderStyle + "IDSCDE, INCTYP" +
						  orderStyle + "INCGC1" + orderStyle + "INCGC2" +
						  orderStyle + "INCGC3";
		} 
				       
		if (orderBy.equals(""))
			orderBy = orderBySql + "INCSEQ, IDSCDE, INCTYP";  
				
	}
	 
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data." +
						   "AnalyticalCode.sqlRangeOrderBy(Class): " + e);
	}
	
			
	return orderBy; 
} 
													
/**
 * 	This method tests access to the Analytical Code Value 
 * file "INPCANAL". All class fields should be tested and
 * verified for access to and from the Enterprise database.
 * A listing of records as they are accessed and updated 
 * should be generated. Also connections, prepared statements, 
 * and the loadFields method are confirmed.
**/
public static void main(String[] args) 
{
	
	// Test add/instantiation/update of class.
	try {
		// Test to see if it constructs without a type code.
		AnalyticalCode xyz = new AnalyticalCode("TGRAD");
		String ss = "stop";
		
		//add it.
		AnalyticalCode ac = new AnalyticalCode();
		ac.setCodeType("RF");
		ac.setAnalyticalCode("NUMMY");
		ac.setValueList("Y");
		ac.setAnalyticalReference("www.gotojail.com");
		ac.setLongDescription("long long long description");
		ac.setDecimalsToDisplay(new Integer("3"));
		ac.setNewSortSequence(new Integer("22"));
		ac.setRequiredValue("N");
		ac.setInvGroupCode1("C1");
		ac.setInvGroupCode2("C2");
		ac.setInvGroupCode3("C3");
		ac.add();
		
		//update it.
		ac.setLongDescription("changed it");
		ac.update();
		
		//delete it.
		ac.delete();
		
		//instantiate it.
		String cType  = "RF";
		String aCode = "PRHI";
		AnalyticalCode acClass = new AnalyticalCode(aCode, cType,
													"", "", "");
		System.out.println("acClass: " + acClass); 		
		System.out.println("AnalyticalCode(String String): " + aCode + " successfull");
			
	}	
	catch(Exception e) {		
		System.out.println("AnalyticalCode(String String) error: " + e); 
	}
	

	// Create a vector of "RF" codes and list one.
	try { 		
		Vector list = AnalyticalCode.findAnalyticalCodesByNewSeqForType("RF"
							, "delettMe");
		String x = "stop here";
		AnalyticalCode acClass = (AnalyticalCode) list.elementAt(1);
		System.out.println("acClass: " + acClass); 		
		System.out.println("findAnalyticalCodesByNameForType " +
		" successfull");		
	}	
	catch(Exception e) {		
		System.out.println("buildCustomerNumber error: " + e); 
	}
	
	// Test the result of different inquiry requests.
	try{
		// no selection criteria.
		AnalyticalCodeValue acFrom = new AnalyticalCodeValue();
		AnalyticalCodeValue acTo = new AnalyticalCodeValue();
		Vector z = AnalyticalCode.findAnalyticalCodesInquiry(acFrom, acTo);
		String stopHere = "x";
		
		// select by Inventory Group Code 1.
		acFrom.setInvGroupCode1("PR");
		z = AnalyticalCode.findAnalyticalCodesInquiry(acFrom, acTo);
		stopHere = "x";
		
		// select by specification.
		acFrom = new AnalyticalCodeValue();
		acTo   = new AnalyticalCodeValue();
		acFrom.setSpecification("RFS01");
		z = AnalyticalCode.findAnalyticalCodesInquiry(acFrom, acTo);
		stopHere = "x";
		acFrom.setSpecification("");
		
		// select by value.
		acFrom.setValueNumeric(new BigDecimal(1));
		z = AnalyticalCode.findAnalyticalCodesInquiry(acFrom, acTo);
		stopHere = "x";
		acFrom.setValueNumeric(new BigDecimal(0));
		
		// select by code.
		acFrom = new AnalyticalCodeValue();
		acTo   = new AnalyticalCodeValue();
		acFrom.setAnalyticalCode("B");
		z = AnalyticalCode.findAnalyticalCodesInquiry(acFrom, acTo);
		stopHere = "x";
		
		// select by code order by code descending.
		acFrom.setOrderByField("analyticalcode");
		acFrom.setOrderByStyle("DESC");
		z = AnalyticalCode.findAnalyticalCodesInquiry(acFrom, acTo);
		stopHere = "x";
		
		// selct by long description.
		acFrom = new AnalyticalCodeValue();
		acTo   = new AnalyticalCodeValue();
		acFrom.setLongDescription("b");
		acFrom.setOrderByField("longdescription");
		z = AnalyticalCode.findAnalyticalCodesInquiry(acFrom, acTo);
		stopHere = "x";
		
		// select and order by short description.
		acFrom = new AnalyticalCodeValue();
		acTo   = new AnalyticalCodeValue();
		acFrom.setShortDescription("acid");
		acFrom.setOrderByField("shortdescription");
		z = AnalyticalCode.findAnalyticalCodesInquiry(acFrom, acTo);
		stopHere = "x";
		
		// select by type order by long description
		acFrom = new AnalyticalCodeValue();
		acTo   = new AnalyticalCodeValue();
		acFrom.setLongDescription("i");
		acFrom.setCodeType("RF");
		acFrom.setOrderByField("longdescription");
		z = AnalyticalCode.findAnalyticalCodesInquiry(acFrom, acTo);
		stopHere = "x";
	}
	catch (Exception e) {
		System.out.println("error occured in main at inquiry tests.");
	}
	
}
	
	/**
	 * Load Analytical Code fields from data base record.
	**/
	protected void loadFields(ResultSet rs) 
	{		
	
		try {
			// 
			requiredValue               = "";
			
			try {
				if (rs.getString("INCTYP") != null )
				{
					codeType				= rs.getString("INCTYP");
					decimalsToDisplay		= new Integer(rs.getString("INCDTD"));
					newSortSequence			= new Integer(rs.getString("INCSEQ"));
					valueList				= rs.getString("INCVL");
					longDescription			= rs.getString("INCDSC");
					analyticalReference		= rs.getString("INCREF");
					requiredValue			= rs.getString("INCREQ");
					invGroupCode1			= rs.getString("INCGC1");
					invGroupCode2			= rs.getString("INCGC2");
					invGroupCode3			= rs.getString("INCGC3");
				}
			} catch (SQLException e) {} // May not have fields.
			
			recordCode				= rs.getString("IDSREC").trim();
			recordDeleteCode		= rs.getString("IDSDLT").trim();
			analyticalCode			= rs.getString("IDSCDE").trim();
			typeOfSpecification		= rs.getString("IDSTYP").trim();
			decimalPositions		= new Integer(rs.getString("IDSDEC"));
			unitOfMeasure			= rs.getString("IDSUNT").trim();
			methodOfMeasureNumber	= rs.getString("IDSMTH").trim();
			shortDescription		= rs.getString("IDSDSC").trim();
			targetValue				= rs.getBigDecimal("IDSDTA");
			upperStandardLimit		= rs.getBigDecimal("IDSSHI");
			lowerStandardLimit		= rs.getBigDecimal("IDSSLO");
			unitDescription			= rs.getString("IDSUDS").trim();
			oldSortSequence			= new Integer(rs.getString("IDSSEQ"));
			productGroupingA		= rs.getString("IDSPGA").trim();
			productGroupingB		= rs.getString("IDSPGB").trim();
			productGroupingC		= rs.getString("IDSPGC").trim();
			productGroupingD		= rs.getString("IDSPGD").trim();
			productGroupingE		= rs.getString("IDSPGE").trim();
			productGroupingF		= rs.getString("IDSPGF").trim();
			productGroupingG		= rs.getString("IDSPGG").trim();
			productGroupingH		= rs.getString("IDSPGH").trim();
			productGroupingI		= rs.getString("IDSPGI").trim();
			productGroupingJ		= rs.getString("IDSPGJ").trim();
			productGroupingK		= rs.getString("IDSPGK").trim();
			productGroupingL		= rs.getString("IDSPGL").trim();
			productGroupingM		= rs.getString("IDSPGM").trim();
			productGroupingN		= rs.getString("IDSPGN").trim();
			productGroupingO		= rs.getString("IDSPGO").trim();
			productGroupingP		= rs.getString("IDSPGP").trim();				
			productGroupingQ		= rs.getString("IDSPGQ").trim();
			productGroupingR		= rs.getString("IDSPGR").trim();
			productGroupingS		= rs.getString("IDSPGS").trim();
			productGroupingT		= rs.getString("IDSPGT").trim();
			coaHdr1					= rs.getString("IDSHD1").trim();
			coaHdr2					= rs.getString("IDSHD2").trim();
			coaHdr3					= rs.getString("IDSHD3").trim();
			identGroupingCode		= rs.getString("IDSGRP").trim();
			division				= rs.getString("IDSDIV").trim();
			testedBrixLevel			= rs.getBigDecimal("IDSTBX");
		}
		catch (Exception e) {			
			System.out.println("Exception at com.treetop.data.AnalyticalCode" +
							   ".loadFields(RS): " + e);
			e.printStackTrace();
		if (analyticalCode != null)
		   System.out.println(" ANALYTICAL CODE: " + analyticalCode);
		if (codeType != null)
		   System.out.println("CODE TYPE: " + codeType);
		if (longDescription != null)
		   System.out.println("LONG DESCRIPTION: " + longDescription);
		}
				
	}
	
/**
 * Update the Analytical Code data base record for
 * this current class object.
 * The data file is INPCANAL.
 * 
 * Throws String error. 
 */
public void update() throws Exception
{
	String throwError = "";
	
	// Update a INPCANAL record.
	try {
		PreparedStatement updateIt = (PreparedStatement) updateByCodeAndType.pop();
		updateIt.setString(1,  valueList);
		updateIt.setString(2,  analyticalReference);
		updateIt.setString(3,  longDescription);
		updateIt.setInt(4,  decimalsToDisplay.intValue());
		updateIt.setInt(5,  newSortSequence.intValue());
		updateIt.setString(6,  requiredValue);	
		updateIt.setString(7,  invGroupCode1);
		updateIt.setString(8,  invGroupCode2);
		updateIt.setString(9,  invGroupCode3);		
		updateIt.setString(10, analyticalCode);
		updateIt.setString(11, codeType);
		updateIt.setString(12, invGroupCode1);
		updateIt.executeUpdate();
		updateByCodeAndType.push(updateIt);	
	} catch (Exception e) {
		System.out.println("Error at com.treetop.data." +
					 "AnalyticalCode.update(): unable to update record." + e);
	}
	
	if (!throwError.equals(""))
	{
		throwError = throwError + "Error at com.treetop.data." +
					 "AnalyticalCode.update() ";	
		throw new Exception(throwError);
	}
	
	return;	
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
		"decimalsToDisplay: " + decimalsToDisplay + "\n" +
		"unitOfMeasure: " + unitOfMeasure + "\n" +
		"methodOfMeasureNumber: " + methodOfMeasureNumber + "\n" +
		"shortDescription: " + shortDescription + "\n" +
		"targetValue: " + targetValue + "\n" +
		"upperStandardLimit: " + upperStandardLimit + "\n" +
		"lowerStandardLimit: " + lowerStandardLimit + "\n" +
		"unitDescription: " + unitDescription + "\n" +
		"oldSortSequence: " + oldSortSequence + "\n" +
		"newSortSequence: " + newSortSequence + "\n" +
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
		"positiveNegitive: " + positiveNegitive + "\n" +
		"requiredValue: " + requiredValue + "\n" +
		"invGroupCode1: " + invGroupCode1 + "\n" +
		"invGroupCode2: " + invGroupCode2 + "\n" +
		"invGroupCode3: " + invGroupCode3 + "\n");
	}	
	/**
	 * @return
	 */
	public String getAnalyticalCode() {
		return analyticalCode;
	}

	/**
	 * @return
	 */
	public String getCoaHdr1() {
		return coaHdr1;
	}

	/**
	 * @return
	 */
	public String getCoaHdr2() {
		return coaHdr2;
	}

	/**
	 * @return
	 */
	public String getCoaHdr3() {
		return coaHdr3;
	}

	/**
	 * @return
	 */
	public String getCodeType() {
		return codeType;
	}

	/**
	 * @return
	 */
	public Integer getDecimalPositions() {
		return decimalPositions;
	}

	/**
	 * @return
	 */
	public String getDivision() {
		return division;
	}

	/**
	 * @return
	 */
	public String getIdentGroupingCode() {
		return identGroupingCode;
	}

	/**
	 * @return
	 */
	public String getLongDescription() {
		return longDescription;
	}

	/**
	 * @return
	 */
	public BigDecimal getLowerStandardLimit() {
		return lowerStandardLimit;
	}

	/**
	 * @return
	 */
	public String getMethodOfMeasureNumber() {
		return methodOfMeasureNumber;
	}

	/**
	 * @return
	 */
	public String getProductGroupingA() {
		return productGroupingA;
	}

	/**
	 * @return
	 */
	public String getProductGroupingB() {
		return productGroupingB;
	}

	/**
	 * @return
	 */
	public String getProductGroupingC() {
		return productGroupingC;
	}

	/**
	 * @return
	 */
	public String getProductGroupingD() {
		return productGroupingD;
	}

	/**
	 * @return
	 */
	public String getProductGroupingE() {
		return productGroupingE;
	}

	/**
	 * @return
	 */
	public String getProductGroupingF() {
		return productGroupingF;
	}

	/**
	 * @return
	 */
	public String getProductGroupingG() {
		return productGroupingG;
	}

	/**
	 * @return
	 */
	public String getProductGroupingH() {
		return productGroupingH;
	}

	/**
	 * @return
	 */
	public String getProductGroupingI() {
		return productGroupingI;
	}

	/**
	 * @return
	 */
	public String getProductGroupingJ() {
		return productGroupingJ;
	}

	/**
	 * @return
	 */
	public String getProductGroupingK() {
		return productGroupingK;
	}

	/**
	 * @return
	 */
	public String getProductGroupingL() {
		return productGroupingL;
	}

	/**
	 * @return
	 */
	public String getProductGroupingM() {
		return productGroupingM;
	}

	/**
	 * @return
	 */
	public String getProductGroupingN() {
		return productGroupingN;
	}

	/**
	 * @return
	 */
	public String getProductGroupingO() {
		return productGroupingO;
	}

	/**
	 * @return
	 */
	public String getProductGroupingP() {
		return productGroupingP;
	}

	/**
	 * @return
	 */
	public String getProductGroupingQ() {
		return productGroupingQ;
	}

	/**
	 * @return
	 */
	public String getProductGroupingR() {
		return productGroupingR;
	}

	/**
	 * @return
	 */
	public String getProductGroupingS() {
		return productGroupingS;
	}

	/**
	 * @return
	 */
	public String getProductGroupingT() {
		return productGroupingT;
	}

	/**
	 * @return
	 */
	public String getRecordCode() {
		return recordCode;
	}

	/**
	 * @return
	 */
	public String getRecordDeleteCode() {
		return recordDeleteCode;
	}

	/**
	 * @return
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * @return
	 */
	public Integer getOldSortSequence() {
		return oldSortSequence;
	}

	/**
	 * @return
	 */
	public BigDecimal getTargetValue() {
		return targetValue;
	}

	/**
	 * @return
	 */
	public BigDecimal getTestedBrixLevel() {
		return testedBrixLevel;
	}

	/**
	 * @return
	 */
	public String getTypeOfSpecification() {
		return typeOfSpecification;
	}

	/**
	 * @return
	 */
	public String getUnitDescription() {
		return unitDescription;
	}

	/**
	 * @return
	 */
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	/**
	 * @return
	 */
	public BigDecimal getUpperStandardLimit() {
		return upperStandardLimit;
	}

	/**
	 * @return
	 */
	public String getValueList() {
		return valueList;
	}

	/**
	 * @return
	 */
	public String getAnalyticalReference() {
		return analyticalReference;
	}

	/**
	 * @return
	 */
	public String getPositiveNegitive() {
		return positiveNegitive;
	}

	/**
	 * @param string
	 */
	public void setAnalyticalCode(String string) {
		analyticalCode = string;
	}

	/**
	 * @param string
	 */
	public void setAnalyticalReference(String string) {
		analyticalReference = string;
	}

	/**
	 * @param string
	 */
	public void setCoaHdr1(String string) {
		coaHdr1 = string;
	}

	/**
	 * @param string
	 */
	public void setCoaHdr2(String string) {
		coaHdr2 = string;
	}

	/**
	 * @param string
	 */
	public void setCoaHdr3(String string) {
		coaHdr3 = string;
	}

	/**
	 * @param string
	 */
	public void setCodeType(String string) {
		codeType = string;
	}

	/**
	 * @param integer
	 */
	public void setDecimalPositions(Integer integer) {
		decimalPositions = integer;
	}

	/**
	 * @param string
	 */
	public void setDivision(String string) {
		division = string;
	}

	/**
	 * @param string
	 */
	public void setIdentGroupingCode(String string) {
		identGroupingCode = string;
	}

	/**
	 * @param string
	 */
	public void setLongDescription(String string) {
		longDescription = string;
	}

	/**
	 * @param decimal
	 */
	public void setLowerStandardLimit(BigDecimal decimal) {
		lowerStandardLimit = decimal;
	}

	/**
	 * @param string
	 */
	public void setMethodOfMeasureNumber(String string) {
		methodOfMeasureNumber = string;
	}

	/**
	 * @param string
	 */
	public void setPositiveNegitive(String string) {
		positiveNegitive = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingA(String string) {
		productGroupingA = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingB(String string) {
		productGroupingB = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingC(String string) {
		productGroupingC = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingD(String string) {
		productGroupingD = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingE(String string) {
		productGroupingE = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingF(String string) {
		productGroupingF = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingG(String string) {
		productGroupingG = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingH(String string) {
		productGroupingH = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingI(String string) {
		productGroupingI = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingJ(String string) {
		productGroupingJ = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingK(String string) {
		productGroupingK = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingL(String string) {
		productGroupingL = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingM(String string) {
		productGroupingM = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingN(String string) {
		productGroupingN = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingO(String string) {
		productGroupingO = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingP(String string) {
		productGroupingP = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingQ(String string) {
		productGroupingQ = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingR(String string) {
		productGroupingR = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingS(String string) {
		productGroupingS = string;
	}

	/**
	 * @param string
	 */
	public void setProductGroupingT(String string) {
		productGroupingT = string;
	}

	/**
	 * @param string
	 */
	public void setRecordCode(String string) {
		recordCode = string;
	}

	/**
	 * @param string
	 */
	public void setRecordDeleteCode(String string) {
		recordDeleteCode = string;
	}

	/**
	 * @param string
	 */
	public void setShortDescription(String string) {
		shortDescription = string;
	}

	/**
	 * @param integer
	 */
	public void setOldSortSequence(Integer integer) {
		oldSortSequence = integer;
	}

	/**
	 * @param decimal
	 */
	public void setTargetValue(BigDecimal decimal) {
		targetValue = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setTestedBrixLevel(BigDecimal decimal) {
		testedBrixLevel = decimal;
	}

	/**
	 * @param string
	 */
	public void setTypeOfSpecification(String string) {
		typeOfSpecification = string;
	}

	/**
	 * @param string
	 */
	public void setUnitDescription(String string) {
		unitDescription = string;
	}

	/**
	 * @param string
	 */
	public void setUnitOfMeasure(String string) {
		unitOfMeasure = string;
	}

	/**
	 * @param decimal
	 */
	public void setUpperStandardLimit(BigDecimal decimal) {
		upperStandardLimit = decimal;
	}

	/**
	 * @param string
	 */
	public void setValueList(String string) {
		valueList = string;
	}

	/**
	 * @return
	 */
	public String getOrderByField() {
		return orderByField;
	}

	/**
	 * @return
	 */
	public String getOrderByStyle() {
		return orderByStyle;
	}

	/**
	 * @param string
	 */
	public void setOrderByField(String string) {
		orderByField = string;
	}

	/**
	 * @param string
	 */
	public void setOrderByStyle(String string) {
		orderByStyle = string;
	}

	/**
	 * @return
	 */
	public Integer getDecimalsToDisplay() {
		return decimalsToDisplay;
	}

	/**
	 * @return
	 */
	public Integer getNewSortSequence() {
		return newSortSequence;
	}

	/**
	 * @param integer
	 */
	public void setDecimalsToDisplay(Integer integer) {
		decimalsToDisplay = integer;
	}

	/**
	 * @param integer
	 */
	public void setNewSortSequence(Integer integer) {
		newSortSequence = integer;
	}

	/**
	 * @return
	 */
	public String getRequiredValue() {
		return requiredValue;
	}

	/**
	 * @param string
	 */
	public void setRequiredValue(String string) {
		requiredValue = string;
	}

	/**
	 * @return
	 */
	public String getInvGroupCode1() {
		return invGroupCode1;
	}

	/**
	 * @return
	 */
	public String getInvGroupCode2() {
		return invGroupCode2;
	}

	/**
	 * @return
	 */
	public String getInvGroupCode3() {
		return invGroupCode3;
	}

	/**
	 * @param string
	 */
	public void setInvGroupCode1(String string) {
		invGroupCode1 = string;
	}

	/**
	 * @param string
	 */
	public void setInvGroupCode2(String string) {
		invGroupCode2 = string;
	}

	/**
	 * @param string
	 */
	public void setInvGroupCode3(String string) {
		invGroupCode3 = string;
	}

}
