package com.treetop.data;

import java.sql.*;
import java.util.*;
import java.math.*;
import com.ibm.as400.access.*;
import com.treetop.utilities.ConnectionStack;

/**
 * Access to Form System Column Definitions file DBLIB/FMPBFDEF
 *       9/22/08 TWalton changed to NEW BOX -  Library DBPRD
 **/
public class FormDefinition extends FormHeader {
	

	// Data base fields.
	
	private   	String        	recordDefId;			// FMBREC
	protected 	String        	statusCode;				// FMBACT
	protected 	Integer       	columnNumber;			// FMBCOL
	protected 	String        	dataType;				// FMBDTY
	protected	String			dataSearch; 			// FMBDSR
	protected 	String        	dataCode;				// FMBDCD
	protected 	Integer       	dataNumber;				// FMBBIN
	protected 	Integer       	decimalPositions;		// FMBDEC
	private		String			dropDownList;
	protected 	Integer       	operand1;				// FMBOP1
	protected 	Integer       	operand2;				// FMBOP2
	protected 	String        	formula;				// FMBFOR
	protected 	String        	formulaDataCode;		    
	protected 	Integer       	formulaNumber;			// FMBFNO
	protected 	String	    	formulaInclusion;		// FMBFIN
	protected	Integer			joinFormNumber;			// FMBJFN	
	private		Integer			joinFormColumn;			// FMBJFC	
	protected 	Integer       	sortOrderSequence;		// FMBSSQ	
	protected 	String        	sortOrderStyle;			// FMBSST
	private		Integer			listOrderDescription;	// FMBLOD
	private   	Integer       	listOrderEntry;			// FMBLOE
	private		Integer			listOrderInquiry;		// FMBLOI
	private 	Integer       	listOrderSearch;		// FMBLOS
	protected 	Integer       	listOrderView;			// FMBLOV
	protected	String			listSeparatorValue;		// FMBOSV
	protected	Integer			listSeparatorLength;	// FMBOSL	
	private   	String        	tableDataFormat;		// FMBFMT
	private   	BigDecimal    	numericLow;				// FMBNLO
	private  	BigDecimal    	numericHigh;			// FMBNHI
	private   	java.sql.Date 	dateLow;				// FMBDLO
	private   	java.sql.Date 	dateHigh;				// FMBDHI
	private   	java.sql.Time 	timeLow;				// FMBTLO
	private   	java.sql.Time 	timeHigh;				// FMBTHI
	private   	String        	textLow;				// FMBXLO
	private   	String        	textHigh;				// FMBXHI
	public    	String        	requiredEntry;			// FMBRQE
	private   	String        	requiredRange;			// FMBRQR
	private  	String        	unitOfMeasure;			// FMBUOM
	protected 	String        	searchType;				// FMBTOS
	protected	String			buildClass;				// FMBBCL
	protected	String			buildDropDown;			// FMBBDD
	private   	String        	overrideEdit;			// FMBOVE
	private   	Integer       	inputSize;				// FMBIBS
	private   	Integer       	inputMaxLength;			// FMBIBL
	private   	String        	showTotals;				// FMBTOT
	private   	String        	showAverage;			// FMBAVG
	private   	String        	headingLong;			// FMBRHL
	private   	String        	headingShort;			// FMBRHS
	private   	String        	updateDefUser;			// FMBUSR
	private   	String        	updateDefUserName;
	private   	java.sql.Date 	updateDefDate;			// FMBDTE
	private   	java.sql.Time 	updateDefTime;			// FMBTME
	private   	String        	referenceGuideDef;		// FMBREF
	private   	String        	helpTextDef;			// FMBHLP										


	// Define database environment (live or test) on the AS/400.
	
//	private static String library = "WKLIB."; // test environment
	// 9/22/08 TWalton change to Library DBPRD
	//private static String library = "DBLIB."; // live environment
	private static String library = "DBPRD."; // Live Environment

	// For use in Main Method,
    // Constructor Methods and Lookup Methods.

    private   static Stack sqlDelete = null;
	private   static Stack sqlInsert = null;
	private   static Stack sqlUpdate = null;

	private   static Stack findDefinitionByFormByColumn   = null;
	private	  static Stack findDefinitionByFormByDesc     = null;  
	private   static Stack findDefinitionByFormByEntry    = null;
	protected static Stack findDefinitionByFormByForm     = null;
	private   static Stack findDefinitionByFormByInquiry  = null;
	private   static Stack findDefinitionByFormByJoin     = null;
	private   static Stack findDefinitionByFormByRange    = null;
	private	  static Stack findDefinitionByFormBySearch   = null;
	private   static Stack findDefinitionByFormByTemplate = null;
	private   static Stack findDefinitionByFormByType     = null;
	private   static Stack findDefinitionByFormByView     = null;
	private	  static Stack findDefinitionByJoinByForm     = null;
	private   static Stack findDefinitionByNextIndex      = null;
	private   static Stack findDefinitionByType           = null;
	
	
	// Additional fields.
	
	private static boolean persists = false;

		
/**
 * Instantiate the form column definition.
 *
 * Creation date: (8/14/2003 10:36:39 AM)
 */
public FormDefinition() {

	super();
	
	init(); 
	
}
/**
 * Instantiate the form definition from another form definition class.
 *
 * Creation date: (10/21/2003 10:36:39 AM)
 */
public FormDefinition(FormDefinition definition) throws InstantiationException { 

	super(definition);

	try {

        recordDefId       	= definition.getRecordDefId();
		statusCode        	= definition.getStatusCode();
		formNumber        	= definition.getFormNumber();
	    columnNumber      	= definition.getColumnNumber();
		dataType          	= definition.getDataType();
		dataSearch			= definition.getDataSearch();
		dataCode          	= definition.getDataCode();
		dataNumber        	= definition.getDataNumber();
		decimalPositions  	= definition.getDecimalPositions();
		dropDownList		= definition.getDropDownList();		
		operand1          	= definition.getOperand1();
		operand2          	= definition.getOperand2();
	    formula           	= definition.getFormula();
	    formulaDataCode   	= definition.getFormulaDataCode();
	    formulaNumber     	= definition.getFormulaNumber();
	    formulaInclusion  	= definition.getFormulaInclusion();
	    joinFormNumber		= definition.getJoinFormNumber();
	    joinFormColumn		= definition.getJoinFormColumn();	   
		sortOrderSequence  	= definition.getSortOrderSequence();
	    sortOrderStyle    	= definition.getSortOrderStyle();		
		listOrderEntry    	= definition.getListOrderEntry();
		listOrderInquiry    = definition.getListOrderInquiry();
		listOrderSearch    	= definition.getListOrderSearch();
		listOrderView     	= definition.getListOrderView();
		tableDataFormat   	= definition.getTableDataFormat();
		numericLow        	= definition.getNumericLow();
		numericHigh       	= definition.getNumericHigh();	    
		dateLow           	= definition.getDateLow();
		dateHigh          	= definition.getDateHigh();
		timeLow           	= definition.getTimeLow();
		timeHigh          	= definition.getTimeHigh();
		textLow           	= definition.getTextLow();
		textHigh          	= definition.getTextHigh();
		requiredEntry     	= definition.getRequiredEntry();
		requiredRange     	= definition.getRequiredRange();
		unitOfMeasure     	= definition.getUnitOfMeasure();
		searchType			= definition.getSearchType();
		buildClass			= definition.getBuildClass();
		buildDropDown		= definition.getBuildDropDown();		
		overrideEdit	  	= definition.getOverrideEdit();
		inputSize         	= definition.getInputSize();
		inputMaxLength    	= definition.getInputMaxLength();
		showTotals        	= definition.getShowTotals();
		showAverage       	= definition.getShowAverage();
		headingLong       	= definition.getHeadingLong();
		headingShort      	= definition.getHeadingShort();
		updateDefUser     	= definition.getUpdateDefUser();
		updateDefUserName 	= definition.getUpdateDefUserName();
		updateDefDate     	= definition.getUpdateDefDate();
		updateDefTime     	= definition.getUpdateDefTime();
		referenceGuideDef 	= definition.getReferenceGuideDef();
		helpTextDef       	= definition.getHelpTextDef();

	}	
	catch (Exception e)
	{
		System.out.println("Exception error at com.treetop.data.FormDefinition." +
			               "FormDefinition(Class) " + e);
	}
	
}
/**
 * Instantiate the form definition.
 *
 * Creation date: (8/14/2003 10:36:39 AM)
 */
public FormDefinition(Integer inFormNumber, Integer inColumnNumber)
					  throws InstantiationException { 

	super();
	
	
	if (persists == false) 	    
		init();
		
   	
	ResultSet rs = null;
	
	try {
		
		PreparedStatement definitionByFormByColumn = (PreparedStatement) findDefinitionByFormByColumn.pop();
		
		definitionByFormByColumn.setInt(1, inFormNumber.intValue());
		definitionByFormByColumn.setInt(2, inColumnNumber.intValue());
			
		rs = definitionByFormByColumn.executeQuery();
		findDefinitionByFormByColumn.push(definitionByFormByColumn);
		
		
		if (rs.next() == false)
			throw new InstantiationException("The form number: " + inFormNumber + 
				                             " and the column: " + inColumnNumber + " not found");
		else 
		    loadFields(rs, "standard");		
		
		rs.close();
			
			
	}
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
			               "FormDefinition(Integer Integer) " + e);
		return;
	}	
		
}
/**
 * Instantiate the form definition.
 *
 * Creation date: (8/14/2003 10:36:39 AM)
 */
public FormDefinition(ResultSet rs, String loadMode)	throws InstantiationException { 

	super();

    loadFields(rs, loadMode);
	
}
/**
 * Delete a record from the Form System Column Definitions.
 *
 * Creation date: (8/14/2003 1:50:29 PM)
 */
private void delete() {

	try { 
		
		PreparedStatement deleteData = (PreparedStatement) sqlDelete.pop();
			
		deleteData.setInt(1, formNumber.intValue());
		deleteData.setInt(2, columnNumber.intValue());
						
		deleteData.executeUpdate();

		sqlDelete.push(deleteData);
		
	} 
	catch (SQLException e) {	
		System.out.println("SQL error at com.treetop.data.FormDefinition.delete(): " + e);
	}
	
}
/**
 * Set result set for form definition by the form number and sorted by the data entry sequence.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
public static String buildDropDownList(FormDefinition definitionData, Integer inTranNumber) {
	
	String  dropDownList = "";
	String  dataValue    = "";
	String  listName     = "";
	int     join         = 0;	
	int	    custom		 = 0;
	
	try {
		
		// Only process the drop down list if requested. "N" = No list.
		
		if (!definitionData.buildDropDown.toLowerCase().equals ("n")) {		// Request for drop down
		
			// Retrieve the transaction data, in order to highlight the drop down list entry which
			// matchs the data.
				
			if (inTranNumber.intValue() > 0) {
				Vector transactionData = FormData.findDataByTranByEntry(definitionData.formNumber, inTranNumber);
				if (transactionData.size() > 0) {
					for (int x = 0; x < transactionData.size(); x++) {
						FormData transactionInfo = (FormData) transactionData.elementAt(x);
						if (definitionData.joinFormColumn.intValue() == transactionInfo.columnNumber.intValue()) {
							
							join = definitionData.dataType.toLowerCase().indexOf("transaction");
							if (join >= 0) {									
								dataValue = transactionInfo.dataNumeric.setScale(0).toString();
								break;
							}
							else {
								String fieldName = FormData.buildDataFieldName(definitionData.columnNumber, 
								                   definitionData.dataCode, definitionData.dataNumber);
								dataValue = FormData.decodeDescription(transactionInfo);
								break;			                                      
							}
								
						}
					}								
				}
			}	
		
			// Short drop down list for joined columns.
			
			if (definitionData.buildDropDown.toLowerCase().equals ("s")) 
				dropDownList = FormData.buildDataDropDownJoin(definitionData.joinFormNumber,
														      definitionData.joinFormColumn, "", dataValue, "");
			
			// Long drop down list for the joined form column or current form column.
				
			if (definitionData.buildDropDown.toLowerCase().equals ("l")) {
				join = definitionData.dataType.toLowerCase().indexOf("transaction");
				if (join >= 0) 
					dropDownList = FormData.buildDataDropDownList(definitionData.joinFormNumber,
				                                                  definitionData.columnNumber, "", dataValue, "");
				else
					dropDownList = FormData.buildDataDropDownList(definitionData.formNumber,
				                                                  definitionData.columnNumber, "", dataValue, "");					
			}
			
			// Drop down list based on the exising data values entered for the column.
		
			if (definitionData.buildDropDown.toLowerCase().equals ("v")) {
				listName = "input" + definitionData.columnNumber;
				dropDownList = FormData.buildDataDropDownValue(definitionData.formNumber,
						                definitionData.columnNumber, listName, dataValue, "");					
			}
			
			// Custom drop down list (another class outside of the form system).
		
			if (definitionData.buildDropDown.toLowerCase().equals ("c")) {
				listName = "input" + definitionData.columnNumber;
			// Broker and Customer will have to be reviewed if needed at a later time
				custom = definitionData.dataType.toLowerCase().indexOf("broker");
			//	if (custom >= 0)				
			//		dropDownList = Broker.buildDataDropDownList(listName, dataValue, "");
				custom = definitionData.dataType.toLowerCase().indexOf("customer");
			//	if (custom >= 0)				
			//		dropDownList = CustomerBillTo.buildDataDropDownList(listName, dataValue, "");				
			}
		
		}																	// End drop down request							
					
	}	 
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormDefinition.buildDropDownList(Class Integer): " + e);
	}	
			
	return dropDownList; 
}
/**
 * Set result set for form definition by the form and column numbers.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
public static Vector findDefinitionByFormByColumn(Integer inFormNumber, Integer inColumnNumber) {

	Vector         definitionList = new Vector();
	FormDefinition definition     = new FormDefinition();	
	
	try {

		PreparedStatement definitionByFormByColumn = (PreparedStatement) FormDefinition.findDefinitionByFormByColumn.pop();
		ResultSet rs = null;
		
		try {
					
			definitionByFormByColumn.setInt(1, inFormNumber.intValue());
			definitionByFormByColumn.setInt(2, inColumnNumber.intValue());		
			rs = definitionByFormByColumn.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormDefinition.findDefinitionByFormByColumn): " + e);
		}
		
		FormDefinition.findDefinitionByFormByColumn.push(definitionByFormByColumn);		

		try {
			
			while (rs.next())
			{
				FormDefinition buildCell = new FormDefinition(rs, "standard");
				definitionList.addElement(buildCell);
			}
			
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
				               "(FormDefinition.findDefinitionByFormByColumn): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
			               "findDefinitionByFormByColumn(Interger Interger): " + e);
	}	
			
	return definitionList; 
}
/**
 * Set result set for form definition by the form number and sorted by the data entry sequence.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
public static Vector findDefinitionByFormByDesc(Integer inFormNumber) {
	
	Vector         definitionList = new Vector();
	FormDefinition definition     = new FormDefinition();
		
	try {

		PreparedStatement definitionByFormByDesc = (PreparedStatement) FormDefinition.findDefinitionByFormByDesc.pop();
		ResultSet rs = null;
		
		try {
			
			definitionByFormByDesc.setInt(1, inFormNumber.intValue());		
			rs = definitionByFormByDesc.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormDefinition.findDefinitionByFormByDesc): " + e);
		}
		
		FormDefinition.findDefinitionByFormByDesc.push(definitionByFormByDesc);		

		try {			

			while (rs.next())
			{
				FormDefinition buildCell = new FormDefinition(rs, "standard");
				definitionList.addElement(buildCell);
			}
						
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
				               "(FormDefinition.findDefinitionByFormByDesc): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
			               "findDefinitionByFormByDesc(Integer): " + e);
	}	
			
	return definitionList; 
}
/**
 * Set result set for form definition by the form number and sorted by the data entry sequence.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
public static Vector findDefinitionByFormByEntry(Integer inFormNumber, Integer inTranNumber,
												 String[] inRole, String[] inGroup, String inProfile) {
	
	Vector         definitionList = new Vector();
	FormDefinition definition     = new FormDefinition();
	String         inquiry        = "";
	String		   update         = "";	
	
	try {

		PreparedStatement definitionByFormByEntry = (PreparedStatement) FormDefinition.findDefinitionByFormByEntry.pop();
		ResultSet rs = null;
		
		try {
			
			definitionByFormByEntry.setInt(1, inFormNumber.intValue());		
			rs = definitionByFormByEntry.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormDefinition.findDefinitionByFormByEntry): " + e);
		}
		
		FormDefinition.findDefinitionByFormByEntry.push(definitionByFormByEntry);		

		try {
			
			Vector headerList = FormHeader.findHeaderBySecurityByForm(inFormNumber, inRole, inGroup, inProfile); 	 
			if (headerList.size() == 1) {
				int x = 0;
				FormHeader header = (FormHeader) headerList.elementAt(x);
				inquiry = header.secureInquiry;
				update  = header.secureUpdate;
			}
			else {
				inquiry = "N";
				update  = "N";
			} 	             	            
			
			while (rs.next())
			{
				FormDefinition buildCell = new FormDefinition(rs, "standard");
				buildCell.secureInquiry  = inquiry;
				buildCell.secureUpdate   = update;
				buildCell.dropDownList   = FormDefinition.buildDropDownList(buildCell, inTranNumber);				   								
				definitionList.addElement(buildCell);
			}
						
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "(FormDefinition.findDefinitionByFormByEntry): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
						   "findDefinitionByFormByEntry(Integer Integer String[] String[] String): " + e);
	}	
			
	return definitionList; 
}
/**
 * Set result set for form definition by the form number.
 *
 * Creation date: (8/25/2004 8:24:29 AM)
 */
public static Vector findDefinitionByFormByForm(Integer inFormNumber) {
	
	Vector         definitionList = new Vector();
	FormDefinition definition     = new FormDefinition();
		
	try {

		PreparedStatement definitionByFormByForm = (PreparedStatement) FormDefinition.findDefinitionByFormByForm.pop();
		ResultSet rs = null;
		
		try {
			
			definitionByFormByForm.setInt(1, inFormNumber.intValue());		
			rs = definitionByFormByForm.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormDefinition.findDefinitionByFormByForm): " + e);
		}
		
		FormDefinition.findDefinitionByFormByForm.push(definitionByFormByForm);		

		try {			
		
			while (rs.next())
			{
				FormDefinition buildCell = new FormDefinition(rs, "standard");							
				definitionList.addElement(buildCell);
			}
						
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "(FormDefinition.findDefinitionByFormByForm): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
						   "findDefinitionByFormByForm(Integer): " + e);
	}	
			
	return definitionList; 
}
/**
 * Set result set for form definition by the form number and sorted by the inquiry page sequence.
 *
 * Creation date: (9/07/2004 8:24:29 AM)
 */
public static Vector findDefinitionByFormByInquiry(Integer inFormNumber, 
												   String[] inRole, String[] inGroup, String inProfile) {
	
	Vector         definitionList = new Vector();
	FormDefinition definition     = new FormDefinition();
	String         inquiry        = "";
	String		   update         = "";	
	
	try {

		PreparedStatement definitionByFormByInquiry = (PreparedStatement) FormDefinition.findDefinitionByFormByInquiry.pop();
		ResultSet rs = null;
		
		try {
			
			definitionByFormByInquiry.setInt(1, inFormNumber.intValue());		
			rs = definitionByFormByInquiry.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormDefinition.findDefinitionByFormByInquiry): " + e);
		}
		
		FormDefinition.findDefinitionByFormByInquiry.push(definitionByFormByInquiry);		

		try {
			
			Vector headerList = FormHeader.findHeaderBySecurityByForm(inFormNumber, inRole, inGroup, inProfile); 	 
			if (headerList.size() == 1) {
				int x = 0;
				FormHeader header = (FormHeader) headerList.elementAt(x);
				inquiry = header.secureInquiry;
				update  = header.secureUpdate;
			}
			else {
				inquiry = "N";
				update  = "N";
			} 	             	            
			
			while (rs.next())
			{
				FormDefinition buildCell = new FormDefinition(rs, "standard");
				buildCell.secureInquiry  = inquiry;
				buildCell.secureUpdate   = update;				
				definitionList.addElement(buildCell);
			}
						
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "(FormDefinition.findDefinitionByFormByInquiry): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
						   "findDefinitionByFormByInquiry(Integer String[] String[] String): " + e);
	}	
			
	return definitionList; 
}
/**
 * Set result set for form definition by the join form number of another form.
 *
 * Creation date: (9/09/2004 8:24:29 AM)
 */
public static Vector findDefinitionByFormByJoin(Integer inJoinFormNumber) {
	
	Vector         definitionList = new Vector();
	FormDefinition definition     = new FormDefinition();
		
	try {

		PreparedStatement definitionByFormByJoin = (PreparedStatement) FormDefinition.findDefinitionByFormByJoin.pop();
		ResultSet rs = null;
		
		try {
			
			definitionByFormByJoin.setInt(1, inJoinFormNumber.intValue());		
			rs = definitionByFormByJoin.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormDefinition.findDefinitionByFormByJoin): " + e);
		}
		
		FormDefinition.findDefinitionByFormByJoin.push(definitionByFormByJoin);		

		try {			
		
			while (rs.next())
			{
				FormDefinition buildCell = new FormDefinition(rs, "standard");							
				definitionList.addElement(buildCell);
			}
						
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "(FormDefinition.findDefinitionByFormByJoin): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
						   "findDefinitionByFormByJoin(Integer): " + e);
	}	
			
	return definitionList; 
}
/**
 * Set result set for form definition by the form number and a range by view sequence.
 *
 * Creation date: (11/05/2003 8:24:29 AM)
 */
public static Vector findDefinitionByFormByRange(Integer inFormNumber, Integer inFormulaNumber) {
	
	                                            	
	Vector         definitionList = new Vector();
	FormDefinition definition     = new FormDefinition();
		
	try {

		PreparedStatement definitionByFormByRange = (PreparedStatement) FormDefinition.findDefinitionByFormByRange.pop();
		ResultSet rs = null;
		
		try {
			
			definitionByFormByRange.setInt(1, inFormNumber.intValue());
			definitionByFormByRange.setInt(2, inFormulaNumber.intValue());		
			rs = definitionByFormByRange.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormDefinition.findDefinitionByFormByRange): " + e);
		}
		
		FormDefinition.findDefinitionByFormByRange.push(definitionByFormByRange);		

		try {
				
		   	while (rs.next())
			{
				FormDefinition buildCell = new FormDefinition(rs, "standard");
				definitionList.addElement(buildCell);
			}		
			
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
				               "(FormDefinition.findDefinitionByFormByRange): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
			               "findDefinitionByFormByRange(Integer Integer Integer): " + e);
	}	
			
	return definitionList; 
}
/**
 * Set result set for form definition by the form number and sorted by the view sequence.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
public static Vector findDefinitionByFormBySearch(Integer inFormNumber) {
	
	Vector         definitionList = new Vector();
	FormDefinition definition     = new FormDefinition();
		
	try {

		PreparedStatement definitionByFormBySearch = (PreparedStatement) FormDefinition.findDefinitionByFormBySearch.pop();
		ResultSet rs = null;
		
		try {
			
			definitionByFormBySearch.setInt(1, inFormNumber.intValue());		
			rs = definitionByFormBySearch.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
								"(FormDefinition.findDefinitionByFormBySearch): " + e);
		}
				
		FormDefinition.findDefinitionByFormBySearch.push(definitionByFormBySearch);		

		try {			
			
			while (rs.next())
			{
				FormDefinition buildCell = new FormDefinition(rs, "standard");				
				definitionList.addElement(buildCell);
			}
						
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "(FormDefinition.findDefinitionByFormBySearch): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
						   "findDefinitionByFormBySearch(Integer): " + e);
	}	
			
	return definitionList; 
}
/**
 * Set result set for form definition by the form number and sorted by the view sequence.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
public static Vector findDefinitionByFormByTemplate(Integer inFormNumber, String inTemplate,
												    String[] inRole, String[] inGroup, String inProfile) {
	
	Vector         definitionList = new Vector();
	FormDefinition definition     = new FormDefinition();
	String         inquiry        = "";
	String		   update         = "";		
	
	try {

		PreparedStatement definitionByFormByTemplate = (PreparedStatement) FormDefinition.findDefinitionByFormByTemplate.pop();
		ResultSet rs = null;
		
		try {
			
			definitionByFormByTemplate.setInt(1, inFormNumber.intValue());
			definitionByFormByTemplate.setString(2, inTemplate.toLowerCase());		
			rs = definitionByFormByTemplate.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormDefinition.findDefinitionByFormByTemplate): " + e);
		}
				
		FormDefinition.findDefinitionByFormByTemplate.push(definitionByFormByTemplate);		

		try {
				
			Vector headerList = FormHeader.findHeaderBySecurityByForm(inFormNumber, inRole, inGroup, inProfile); 	 
			if (headerList.size() == 1) {
				int x = 0;
				FormHeader header = (FormHeader) headerList.elementAt(x);
				inquiry = header.secureInquiry;
				update  = header.secureUpdate;
			}
			else {
				inquiry = "N";
				update  = "N";
			} 	             	            
			
			while (rs.next())
			{
				FormDefinition buildCell = new FormDefinition(rs, "standard");
				buildCell.secureInquiry  = inquiry;
				buildCell.secureUpdate   = update;
				definitionList.addElement(buildCell);
			}
						
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "(FormDefinition.findDefinitionByFormByTemplate): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
						   "findDefinitionByFormByTemplate(Integer String String[] String[] String): " + e);
	}	
			
	return definitionList; 
}
/**
 * Set vector of definition cells for a specific form and data type.
 *
 * Creation date: (10/05/2004 8:24:29 AM)
 */
public static Vector findDefinitionByFormByType(FormData inData) {
	
	Vector         definitionList = new Vector();
	FormDefinition definition     = new FormDefinition();
	String         loadMode       = "standard";
		
	try {

		PreparedStatement definitionByFormByType = (PreparedStatement) FormDefinition.findDefinitionByFormByType.pop();
		ResultSet rs = null;
		
		try {			
			
			definitionByFormByType.setInt(1, inData.formNumber.intValue());
			definitionByFormByType.setString(2, inData.dataType.trim().toUpperCase());		
			rs = definitionByFormByType.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormDefinition.findDefinitionByFormByType): " + e);
		}
		
		FormDefinition.findDefinitionByFormByType.push(definitionByFormByType);		

		try {			
	
			while (rs.next())
			{						
			    	FormDefinition buildCell = new FormDefinition(rs, loadMode);
					definitionList.addElement(buildCell);				
			}
									
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "(FormDefinition.findDefinitionByFormByType): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
						   "findDefinitionByFormByType(Class): " + e);
	}

	return definitionList; 
}																								
/**
 * Set result set for form definition by the form number and sorted by the view sequence.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
public static Vector findDefinitionByFormByView(Integer inFormNumber, 
	                                            String[] inRole, String[] inGroup, String inProfile) {
	
	Vector         definitionList = new Vector();
	FormDefinition definition     = new FormDefinition();
	String         inquiry        = "";
	String		   update         = "";		
	
	try {

		PreparedStatement definitionByFormByView = (PreparedStatement) FormDefinition.findDefinitionByFormByView.pop();
		ResultSet rs = null;
		
		try {
			
			definitionByFormByView.setInt(1, inFormNumber.intValue());		
			rs = definitionByFormByView.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormDefinition.findDefinitionByFormByView): " + e);
		}
				
		FormDefinition.findDefinitionByFormByView.push(definitionByFormByView);		

		try {
				
		    Vector headerList = FormHeader.findHeaderBySecurityByForm(inFormNumber, inRole, inGroup, inProfile); 	 
		    if (headerList.size() == 1) {
	 	        int x = 0;
 	            FormHeader header = (FormHeader) headerList.elementAt(x);
 	            inquiry = header.secureInquiry;
 	            update  = header.secureUpdate;
 	        }
		    else {
			    inquiry = "N";
			    update  = "N";
		    } 	             	            
			
			while (rs.next())
			{
				FormDefinition buildCell = new FormDefinition(rs, "standard");
				buildCell.secureInquiry  = inquiry;
				buildCell.secureUpdate   = update;
				definitionList.addElement(buildCell);
			}
						
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
				               "(FormDefinition.findDefinitionByFormByView): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
			               "findDefinitionByFormByView(Integer String[] String[] String): " + e);
	}	
			
	return definitionList; 
}
/**
 * Set result set for form definition by the form number.
 *
 * Creation date: (8/25/2004 8:24:29 AM)
 */
public static Vector findDefinitionByJoinByForm(Integer inFormNumber) {
	
	Vector         definitionList = new Vector();
	FormDefinition definition     = new FormDefinition();
		
	try {

		PreparedStatement definitionByJoinByForm = (PreparedStatement) FormDefinition.findDefinitionByJoinByForm.pop();
		ResultSet rs = null;
		
		try {
			
			definitionByJoinByForm.setInt(1, inFormNumber.intValue());		
			rs = definitionByJoinByForm.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormDefinition.findDefinitionByJoinByForm): " + e);
		}
		
		FormDefinition.findDefinitionByJoinByForm.push(definitionByJoinByForm);		

		try {			
		
			while (rs.next())
			{
				FormDefinition buildCell = new FormDefinition(rs, "standard");							
				definitionList.addElement(buildCell);
			}
						
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "(FormDefinition.findDefinitionByJoinByForm): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
						   "findDefinitionByJoinByForm(Integer): " + e);
	}	
			
	return definitionList; 
}
/**
 * Use result set to determine the next available index per data code.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
public static int findDefinitionByNextIndex(Integer inFormNumber, String inDataCode) {
	                                    
    int            index      = 0;
	FormDefinition definition = new FormDefinition();	
		
	try {
		
		PreparedStatement definitionByNextIndex = (PreparedStatement) FormDefinition.findDefinitionByNextIndex.pop();
		ResultSet rs = null;
		
		try {
			
			definitionByNextIndex.setInt(1, inFormNumber.intValue());
			definitionByNextIndex.setString(2, inDataCode);
			rs = definitionByNextIndex.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormDefinition.findDefinitionByNextIndex): " + e);
		}
		
		FormDefinition.findDefinitionByNextIndex.push(definitionByNextIndex);
		

		try {
			if (rs.next())
			{				
				Integer dataNumber = new Integer(rs.getInt("FMBBIN"));
				        index      = dataNumber.intValue() + 1;					       	
			}
			
			else
			    index = 1;			    
			
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
				               "(FormDefinition.findDefinitionByNextIndex): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
			               "findDefinitionByNextIndex(Interger String): " + e);
	}	
			
	return index; 
}
/**
 * Set vector of form headers with definitions containg a specific data type.
 *
 * Creation date: (10/05/2004 8:24:29 AM)
 */
public static Vector findDefinitionByType(FormDefinition inDefinition, 
										  String[] inRole, String[] inGroup, String inProfile) {
	
	Vector         headerList = new Vector();
	FormDefinition definition = new FormDefinition();
	String         inquiry    = "";
	String		   update     = "";
	String         loadMode   = "standard";
		
	try {

		PreparedStatement definitionByType = (PreparedStatement) FormDefinition.findDefinitionByType.pop();
		ResultSet rs = null;
		
		try {
			
			definitionByType.setString(1, inDefinition.dataType.trim().toUpperCase());		
			rs = definitionByType.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormDefinition.findDefinitionByType): " + e);
		}
		
		FormDefinition.findDefinitionByType.push(definitionByType);		

		try {
			
			Integer lastForm = new Integer("0");
			
			while (rs.next())
			{
				
				Integer thisForm = new Integer(rs.getInt("FMANBR"));
				if (thisForm != lastForm) {
			    
					FormHeader buildHeader = new FormHeader(rs, loadMode);
					Vector header = FormHeader.findHeaderBySecurityByForm(thisForm, inRole, inGroup, inProfile); 	 
					if (header.size() == 1) {
						FormHeader headerData = (FormHeader) header.elementAt(0);
						inquiry = headerData.secureInquiry;
						update  = headerData.secureUpdate;
					}
					else {
						inquiry = "N";
						update  = "N";
					}			 	                        
								
					buildHeader.secureInquiry = inquiry;
					buildHeader.secureUpdate  = update;				
					headerList.addElement(buildHeader);
					lastForm = thisForm;
				}
			}
									
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "(FormDefinition.findDefinitionByType): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
						   "findDefinitionByType(Class String[] String[] String): " + e);
	}

	return headerList; 
}
/**
 * Retrieve the form definition build class control.
 *
 * Creation date: (8/24/2004 4:45:28 PM)
 */
public String getBuildClass() {

	return buildClass;	
}
/**
 * Retrieve the form definition build dropdown list control.
 *
 * Creation date: (9/21/2004 4:45:28 PM)
 */
public String getBuildDropDown() {

	return buildDropDown;	
}
/**
 * Retrieve the column number.
 *
 * Creation date: (10/02/2003 10:53:28 AM)
 */
public Integer getColumnNumber() 
{
	return columnNumber;	
}
/**
 * Retrieve the form definition data type code.
 *
 * Creation date: (8/21/2003 4:45:28 PM)
 */
public String getDataCode() {

	return dataCode;	
}
/**
 * Retrieve the column data number (position of data in transaction record arrays).
 *
 * Creation date: (10/09/2003 10:53:28 AM)
 */
public Integer getDataNumber() 
{
	return dataNumber;	
}
/**
 * Retrieve the form definition data search description.
 *
 * Creation date: (9/21/2004 4:45:28 PM)
 */
public String getDataSearch() {

	return dataSearch;	
}
/**
 * Retrieve the form definition data type description.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getDataType() {

	return dataType;	
}
/**
 * Retrieve the form definition date high value.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Date getDateHigh() {

	return dateHigh;	
}
/**
 * Retrieve the form definition date low value.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Date getDateLow() {

	return dateLow;	
}
/**
 * Retrieve the form definition numeric value decimal positions.
 *
 * Creation date: (8/14/2003 10:53:28 AM)
 */
public Integer getDecimalPositions() 
{
	return decimalPositions;	
}
/**
 * Retrieve the form definition drop down list.
 *
 * Creation date: (9/21/2004 4:45:28 PM)
 */
public String getDropDownList() {

	return dropDownList;	
}
/**
 * Retrieve the form number.
 *
 * Creation date: (8/12/2003 10:53:28 AM)
 */
public Integer getFormNumber() 
{
	return formNumber;	
}
/**
 * Retrieve the form definition calculation formula description.
 *
 * Creation date: (10/15/2003 4:45:28 PM)
 */
public String getFormula() {

	return formula;	
}
/**
 * Retrieve the form definition input data type code for calculated cells.
 *
 * Creation date: (10/22/2003 4:45:28 PM)
 */
public String getFormulaDataCode() {

	return formulaDataCode;	
}
/**
 * Retrieve the form definition operand inclusion code for cells used in calculations.
 *
 * Creation date: (11/10/2003 4:45:28 PM)
 */
public String getFormulaInclusion() {

	return formulaInclusion;	
}
/**
 * Retrieve the calculation formula number.
 *
 * Creation date: (11/06/2003 10:53:28 AM)
 */
public Integer getFormulaNumber() 
{
	return formulaNumber;	
}
/**
 * Retrieve the form definition long heading description.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getHeadingLong() {

	return headingLong;	
}
/**
 * Retrieve the form definition short heading description.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getHeadingShort() {

	return headingShort;	
}
/**
 * Retrieve the form definition help text.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getHelpTextDef() {

	return helpTextDef;	
}
/**
 * Retrieve the form definition input box maximum length.
 *
 * Creation date: (10/02/2003 10:53:28 AM)
 */
public Integer getInputMaxLength() 
{
	return inputMaxLength;	
}
/**
 * Retrieve the form definition input box size (length).
 *
 * Creation date: (8/14/2003 10:53:28 AM)
 */
public Integer getInputSize() 
{
	return inputSize;	
}
/**
 * Retrieve the form definition joining form number.
 *
 * Creation date: (8/23/2004 10:53:28 AM)
 */
public Integer getJoinFormNumber() 
{
	return joinFormNumber;	
}
/**
 * Retrieve the form definition joining form column number.
 *
 * Creation date: (8/23/2004 10:53:28 AM)
 */
public Integer getJoinFormColumn() 
{
	return joinFormColumn;	
}
/**
 * Retrieve the form definition list order for each description.
 *
 * Creation date: (10/01/2004 10:53:28 AM)
 */
public Integer getListOrderDescription() 
{
	return listOrderDescription;	
}
/**
 * Retrieve the form definition list order for data entry.
 *
 * Creation date: (10/02/2003 10:53:28 AM)
 */
public Integer getListOrderEntry() 
{
	return listOrderEntry;	
}
/**
 * Retrieve the form definition list order for inquiry.
 *
 * Creation date: (9/03/2004 10:53:28 AM)
 */
public Integer getListOrderInquiry() 
{
	return listOrderInquiry;	
}
/**
 * Retrieve the form definition list order for data searching (drop down list).
 *
 * Creation date: (9/24/2004 10:53:28 AM)
 */
public Integer getListOrderSearch() 
{
	return listOrderSearch;	
}
/**
 * Retrieve the form definition list order for viewing (report).
 *
 * Creation date: (10/02/2003 10:53:28 AM)
 */
public Integer getListOrderView() 
{
	return listOrderView;	
}
/**
 * Retrieve the form definition list separator value length.
 *
 * Creation date: (10/27/2004 10:53:28 AM)
 */
public Integer getListSeparatorLength() 
{
	return listSeparatorLength;	
}
/**
 * Retrieve the form definition list separator value, used between cells.
 *
 * Creation date: (10/27/2004 4:45:28 PM)
 */
public String getListSeparatorValue() {

	return listSeparatorValue;	
}
/**
 * Retrieve the form definition numeric high value.
 *
 * Creation date: (8/12/2003 10:53:28 AM)
 */
public BigDecimal getNumericHigh() 
{
	return numericHigh;	
}
/**
 * Retrieve the form definition numeric low value.
 *
 * Creation date: (8/12/2003 10:53:28 AM)
 */
public BigDecimal getNumericLow() 
{
	return numericLow;	
}
/**
 * Retrieve the calculation operand column number.
 *
 * Creation date: (10/15/2003 10:53:28 AM)
 */
public Integer getOperand1() 
{
	return operand1;	
}
/**
 * Retrieve the calculation operand column number.
 *
 * Creation date: (10/15/2003 10:53:28 AM)
 */
public Integer getOperand2() 
{
	return operand2;	
}
/**
 * Retrieve the form definition override edit control.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getOverrideEdit() {

	return overrideEdit;	
}
/**
 * Retrieve the record identification code.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getRecordDefId() {

	return recordDefId;	
}
/**
 * Retrieve the form definition reference guide.
 *
 * Creation date: (10/02/2003 4:45:28 PM)
 */
public String getReferenceGuideDef() {

	return referenceGuideDef;	
}
/**
 * Retrieve the form definition required entry control.
 *
 * Creation date: (10/02/2003 4:45:28 PM)
 */
public String getRequiredEntry() {

	return requiredEntry;	
}
/**
 * Retrieve the form definition required range control.
 *
 * Creation date: (10/02/2003 4:45:28 PM)
 */
public String getRequiredRange() {

	return requiredRange;	
}
/**
 * Retrieve the form definition type of search.
 *
 * Creation date: (8/20/2004 4:45:28 PM)
 */
public String getSearchType() {

	return searchType;	
}
/**
 * Retrieve the form definition show average control.
 *
 * Creation date: (8/14/2003 4:45:28 PM)
 */
public String getShowAverage() {

	return showAverage;	
}
/**
 * Retrieve the form definition show totals control.
 *
 * Creation date: (8/14/2003 4:45:28 PM)
 */
public String getShowTotals() {

	return showTotals;	
}
/**
 * Retrieve the form definition sort order sequence number.
 *
 * Creation date: (10/02/2003 10:53:28 AM)
 */
public Integer getSortOrderSequence() 
{
	return sortOrderSequence;	
}
/**
 * Retrieve the form definition sort order style (assending, descending).
 *
 * Creation date: (10/02/2003 10:53:28 AM)
 */
public String getSortOrderStyle() 
{
	return sortOrderStyle;	
}
/**
 * Retrieve the form definition status code.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getStatusCode() {

	return statusCode;	
}
/**
 * Retrieve the form definition HTML table data format.
 *
 * Creation date: (11/10/2003 10:53:28 AM)
 */
public String getTableDataFormat() 
{
	return tableDataFormat;	
}
/**
 * Retrieve the form definition text high value.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getTextHigh() {

	return textHigh;	
}
/**
 * Retrieve the form definition text low value.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getTextLow() {

	return textLow;	
}
/**
 * Retrieve the form definition time high value.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Time getTimeHigh() {

	return timeHigh;	
}
/**
 * Retrieve the form column time low value.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Time getTimeLow() {

	return timeLow;	
}
/**
 * Retrieve the form definition unit of measure description.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getUnitOfMeasure() {

	return unitOfMeasure;	
}
/**
 * Retrieve the last update date. 
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Date getUpdateDefDate() {

	return updateDefDate;	
}
/**
 * Retrieve the last update time. 
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Time getUpdateDefTime() {

	return updateDefTime;	
}
/**
 * Retrieve the last update user profile.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getUpdateDefUser() {

	return updateDefUser;	
}
/**
 * Retrieve the last update user profile name.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getUpdateDefUserName() {

	return updateDefUserName;	
}
/**
 * SQL definitions.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
public void init() {
	
    // Test for prior initialization.
	
	if (persists == false) {	
	    persists = true;
	    

	// Perform initialization.	 
 
	try {
		// 9/22/08 TWalton change from ConnectionPool to Connection Stack
		Connection conn1 = ConnectionStack.getConnection();
		Connection conn2 = ConnectionStack.getConnection();
		Connection conn3 = ConnectionStack.getConnection();
		Connection conn4 = ConnectionStack.getConnection();
		Connection conn5 = ConnectionStack.getConnection();
		Connection conn6 = ConnectionStack.getConnection();
		

		String deleteDefinition =  
			  "DELETE FROM " + library + "FMPBFDEF " +
			  "WHERE FMBNBR = ? AND FMBCOL = ?";

		PreparedStatement deleteDefinition1 = conn1.prepareStatement(deleteDefinition);
		PreparedStatement deleteDefinition2 = conn2.prepareStatement(deleteDefinition);
		PreparedStatement deleteDefinition3 = conn3.prepareStatement(deleteDefinition);
		PreparedStatement deleteDefinition4 = conn4.prepareStatement(deleteDefinition);
		PreparedStatement deleteDefinition5 = conn5.prepareStatement(deleteDefinition);
		PreparedStatement deleteDefinition6 = conn6.prepareStatement(deleteDefinition);

		sqlDelete = new Stack();
		sqlDelete.push(deleteDefinition1);
		sqlDelete.push(deleteDefinition2);
		sqlDelete.push(deleteDefinition3);
		sqlDelete.push(deleteDefinition4);
		sqlDelete.push(deleteDefinition5);
		sqlDelete.push(deleteDefinition6);
		
		

		String insertDefinition = 
			  "INSERT INTO " + library + "FMPBFDEF " +
			  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
			         " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
		             " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," + 
		             " ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement insertDefinition1 = conn1.prepareStatement(insertDefinition);
		PreparedStatement insertDefinition2 = conn2.prepareStatement(insertDefinition);
		PreparedStatement insertDefinition3 = conn3.prepareStatement(insertDefinition);
		PreparedStatement insertDefinition4 = conn4.prepareStatement(insertDefinition);
		PreparedStatement insertDefinition5 = conn5.prepareStatement(insertDefinition);
		PreparedStatement insertDefinition6 = conn6.prepareStatement(insertDefinition);

		sqlInsert = new Stack();
		sqlInsert.push(insertDefinition1);
		sqlInsert.push(insertDefinition2);
		sqlInsert.push(insertDefinition3);
		sqlInsert.push(insertDefinition4);
		sqlInsert.push(insertDefinition5);
		sqlInsert.push(insertDefinition6);
			         

		String updateDefinition = 
			  "UPDATE " + library + "FMPBFDEF " +
			  "SET FMBREC = ?, FMBACT = ?, FMBNBR = ?, FMBCOL = ?, FMBDTY = ?," +
			     " FMBDSR = ?, FMBDCD = ?, FMBBIN = ?, FMBDEC = ?, FMBOP1 = ?," +
			     " FMBOP2 = ?, FMBFOR = ?, FMBFNO = ?, FMBFIN = ?, FMBJFN = ?," +
			     " FMBJFC = ?, FMBSSQ = ?, FMBSST = ?, FMBLOD = ?, FMBLOE = ?," +
			     " FMBLOI = ?, FMBLOS = ?, FMBLOV = ?, FMBLSV = ?, FMBLSL = ?," +
			     " FMBFMT = ?, FMBNLO = ?, FMBNHI = ?, FMBDLO = ?, FMBDHI = ?," +
			     " FMBTLO = ?, FMBTHI = ?, FMBXLO = ?, FMBXHI = ?, FMBRQE = ?," +
			     " FMBRQR = ?, FMBUOM = ?, FMBTOS = ?, FMBBCL = ?, FMBBDD = ?," +
			     " FMBOVE = ?, FMBIBS = ?, FMBIBL = ?, FMBTOT = ?, FMBAVG = ?," +
			     " FMBRHL = ?, FMBRHS = ?, FMBUSR = ?, FMBDTE = ?, FMBTME = ?," +
			     " FMBREF = ?, FMBHLP = ? " +			    
			  "WHERE FMBNBR = ? AND FMBCOL = ?";

		PreparedStatement updateDefinition1 = conn1.prepareStatement(updateDefinition);
		PreparedStatement updateDefinition2 = conn2.prepareStatement(updateDefinition);
		PreparedStatement updateDefinition3 = conn3.prepareStatement(updateDefinition);
		PreparedStatement updateDefinition4 = conn4.prepareStatement(updateDefinition);
		PreparedStatement updateDefinition5 = conn5.prepareStatement(updateDefinition);
		PreparedStatement updateDefinition6 = conn6.prepareStatement(updateDefinition);

		sqlUpdate = new Stack();
		sqlUpdate.push(updateDefinition1);
		sqlUpdate.push(updateDefinition2);
		sqlUpdate.push(updateDefinition3);
		sqlUpdate.push(updateDefinition4);
		sqlUpdate.push(updateDefinition5);
		sqlUpdate.push(updateDefinition6);
		
		
		String definitionByFormByColumn = 
			  "SELECT * " +
			  "FROM " + library + "FMPBFDEF " +
			  "JOIN " + library + "FMPAFHDR ON FMBNBR = FMANBR " +
			  "WHERE FMBNBR = ? AND FMBCOL = ? " +
			  "ORDER BY FMBCOL";

		PreparedStatement definitionByFormByColumn1 = conn1.prepareStatement(definitionByFormByColumn);
		PreparedStatement definitionByFormByColumn2 = conn2.prepareStatement(definitionByFormByColumn);
		PreparedStatement definitionByFormByColumn3 = conn3.prepareStatement(definitionByFormByColumn);
		PreparedStatement definitionByFormByColumn4 = conn4.prepareStatement(definitionByFormByColumn);
		PreparedStatement definitionByFormByColumn5 = conn5.prepareStatement(definitionByFormByColumn);
		PreparedStatement definitionByFormByColumn6 = conn6.prepareStatement(definitionByFormByColumn);

		findDefinitionByFormByColumn = new Stack();
		findDefinitionByFormByColumn.push(definitionByFormByColumn1);
		findDefinitionByFormByColumn.push(definitionByFormByColumn2);
		findDefinitionByFormByColumn.push(definitionByFormByColumn3);
		findDefinitionByFormByColumn.push(definitionByFormByColumn4);
		findDefinitionByFormByColumn.push(definitionByFormByColumn5);
		findDefinitionByFormByColumn.push(definitionByFormByColumn6);


		String definitionByFormByDesc = 
			  "SELECT * " +
			  "FROM " + library + "FMPBFDEF " +
			  "JOIN " + library + "FMPAFHDR ON FMBNBR = FMANBR " +
			  "WHERE FMBNBR = ? AND FMBLOD > 0 " +
		      "ORDER BY FMBLOD";

		PreparedStatement definitionByFormByDesc1 = conn1.prepareStatement(definitionByFormByDesc);
		PreparedStatement definitionByFormByDesc2 = conn2.prepareStatement(definitionByFormByDesc);
		PreparedStatement definitionByFormByDesc3 = conn3.prepareStatement(definitionByFormByDesc);
		PreparedStatement definitionByFormByDesc4 = conn4.prepareStatement(definitionByFormByDesc);
		PreparedStatement definitionByFormByDesc5 = conn5.prepareStatement(definitionByFormByDesc);
		PreparedStatement definitionByFormByDesc6 = conn6.prepareStatement(definitionByFormByDesc);

		findDefinitionByFormByDesc = new Stack();
		findDefinitionByFormByDesc.push(definitionByFormByDesc1);
		findDefinitionByFormByDesc.push(definitionByFormByDesc2);
		findDefinitionByFormByDesc.push(definitionByFormByDesc3);
		findDefinitionByFormByDesc.push(definitionByFormByDesc4);
		findDefinitionByFormByDesc.push(definitionByFormByDesc5);
		findDefinitionByFormByDesc.push(definitionByFormByDesc6);
		
		
		String definitionByFormByEntry = 
			  "SELECT * " +
			  "FROM " + library + "FMPBFDEF " +
			  "JOIN " + library + "FMPAFHDR ON FMBNBR = FMANBR " +
			  "WHERE FMBNBR = ? AND FMBLOE > 0 " +
			  "ORDER BY FMBLOE";

		PreparedStatement definitionByFormByEntry1 = conn1.prepareStatement(definitionByFormByEntry);
		PreparedStatement definitionByFormByEntry2 = conn2.prepareStatement(definitionByFormByEntry);
		PreparedStatement definitionByFormByEntry3 = conn3.prepareStatement(definitionByFormByEntry);
		PreparedStatement definitionByFormByEntry4 = conn4.prepareStatement(definitionByFormByEntry);
		PreparedStatement definitionByFormByEntry5 = conn5.prepareStatement(definitionByFormByEntry);
		PreparedStatement definitionByFormByEntry6 = conn6.prepareStatement(definitionByFormByEntry);

		findDefinitionByFormByEntry = new Stack();
		findDefinitionByFormByEntry.push(definitionByFormByEntry1);
		findDefinitionByFormByEntry.push(definitionByFormByEntry2);
		findDefinitionByFormByEntry.push(definitionByFormByEntry3);
		findDefinitionByFormByEntry.push(definitionByFormByEntry4);
		findDefinitionByFormByEntry.push(definitionByFormByEntry5);
		findDefinitionByFormByEntry.push(definitionByFormByEntry6);
		
		
		String definitionByFormByForm = 
			  "SELECT * " +
			  "FROM " + library + "FMPBFDEF " +
			  "JOIN " + library + "FMPAFHDR ON FMBNBR = FMANBR " +
			  "WHERE FMBNBR = ? ";			 

		PreparedStatement definitionByFormByForm1 = conn1.prepareStatement(definitionByFormByForm);
		PreparedStatement definitionByFormByForm2 = conn2.prepareStatement(definitionByFormByForm);
		PreparedStatement definitionByFormByForm3 = conn3.prepareStatement(definitionByFormByForm);
		PreparedStatement definitionByFormByForm4 = conn4.prepareStatement(definitionByFormByForm);
		PreparedStatement definitionByFormByForm5 = conn5.prepareStatement(definitionByFormByForm);
		PreparedStatement definitionByFormByForm6 = conn6.prepareStatement(definitionByFormByForm);

		findDefinitionByFormByForm = new Stack();
		findDefinitionByFormByForm.push(definitionByFormByForm1);
		findDefinitionByFormByForm.push(definitionByFormByForm2);
		findDefinitionByFormByForm.push(definitionByFormByForm3);
		findDefinitionByFormByForm.push(definitionByFormByForm4);
		findDefinitionByFormByForm.push(definitionByFormByForm5); 
		findDefinitionByFormByForm.push(definitionByFormByForm6);
		
		
		String definitionByFormByInquiry = 
					  "SELECT * " +
					  "FROM " + library + "FMPBFDEF " +
					  "JOIN " + library + "FMPAFHDR ON FMBNBR = FMANBR " +
					  "WHERE FMBNBR = ? AND FMBLOI > 0 " +
					  "ORDER BY FMBLOI";

		PreparedStatement definitionByFormByInquiry1 = conn1.prepareStatement(definitionByFormByInquiry);
		PreparedStatement definitionByFormByInquiry2 = conn2.prepareStatement(definitionByFormByInquiry);
		PreparedStatement definitionByFormByInquiry3 = conn3.prepareStatement(definitionByFormByInquiry);
		PreparedStatement definitionByFormByInquiry4 = conn4.prepareStatement(definitionByFormByInquiry);
		PreparedStatement definitionByFormByInquiry5 = conn5.prepareStatement(definitionByFormByInquiry);
		PreparedStatement definitionByFormByInquiry6 = conn6.prepareStatement(definitionByFormByInquiry);

		findDefinitionByFormByInquiry = new Stack();
		findDefinitionByFormByInquiry.push(definitionByFormByInquiry1);
		findDefinitionByFormByInquiry.push(definitionByFormByInquiry2);
		findDefinitionByFormByInquiry.push(definitionByFormByInquiry3);
		findDefinitionByFormByInquiry.push(definitionByFormByInquiry4);
		findDefinitionByFormByInquiry.push(definitionByFormByInquiry5);
		findDefinitionByFormByInquiry.push(definitionByFormByInquiry6);
		
		
		String definitionByFormByJoin = 
					  "SELECT * " +
					  "FROM " + library + "FMPBFDEF " +	
					  "JOIN " + library + "FMPAFHDR ON FMBNBR = FMANBR " +				 
					  "WHERE FMBNBR = ? AND FMBJFN > 0 AND UPPER(FMBDTY) LIKE '%TRANSACTION%' " +
					  "ORDER BY FMBNBR, FMBCOL";

		PreparedStatement definitionByFormByJoin1 = conn1.prepareStatement(definitionByFormByJoin);
		PreparedStatement definitionByFormByJoin2 = conn2.prepareStatement(definitionByFormByJoin);
		PreparedStatement definitionByFormByJoin3 = conn3.prepareStatement(definitionByFormByJoin);
		PreparedStatement definitionByFormByJoin4 = conn4.prepareStatement(definitionByFormByJoin);
		PreparedStatement definitionByFormByJoin5 = conn5.prepareStatement(definitionByFormByJoin);
		PreparedStatement definitionByFormByJoin6 = conn6.prepareStatement(definitionByFormByJoin);

		findDefinitionByFormByJoin = new Stack();
		findDefinitionByFormByJoin.push(definitionByFormByJoin1);
		findDefinitionByFormByJoin.push(definitionByFormByJoin2);
		findDefinitionByFormByJoin.push(definitionByFormByJoin3);
		findDefinitionByFormByJoin.push(definitionByFormByJoin4);
		findDefinitionByFormByJoin.push(definitionByFormByJoin5);
		findDefinitionByFormByJoin.push(definitionByFormByJoin6);
		
		
		String definitionByFormByRange = 
			  "SELECT * " +
			  "FROM " + library + "FMPBFDEF " +
			  "JOIN " + library + "FMPAFHDR ON FMBNBR = FMANBR " +
			  "WHERE FMBNBR = ? AND FMBFNO = ? AND UPPER(FMBDCD) = 'NU' AND FMBBIN > 0 " +
			  "ORDER BY FMBCOL";

		PreparedStatement definitionByFormByRange1 = conn1.prepareStatement(definitionByFormByRange);
		PreparedStatement definitionByFormByRange2 = conn2.prepareStatement(definitionByFormByRange);
		PreparedStatement definitionByFormByRange3 = conn3.prepareStatement(definitionByFormByRange);
		PreparedStatement definitionByFormByRange4 = conn4.prepareStatement(definitionByFormByRange);
		PreparedStatement definitionByFormByRange5 = conn5.prepareStatement(definitionByFormByRange);
		PreparedStatement definitionByFormByRange6 = conn6.prepareStatement(definitionByFormByRange);

		findDefinitionByFormByRange = new Stack();
		findDefinitionByFormByRange.push(definitionByFormByRange1);
		findDefinitionByFormByRange.push(definitionByFormByRange2);
		findDefinitionByFormByRange.push(definitionByFormByRange3);
		findDefinitionByFormByRange.push(definitionByFormByRange4);
		findDefinitionByFormByRange.push(definitionByFormByRange5);
		findDefinitionByFormByRange.push(definitionByFormByRange6);			
		
		
		String definitionByFormBySearch = 
					  "SELECT * " +
					  "FROM " + library + "FMPBFDEF " +
					  "JOIN " + library + "FMPAFHDR ON FMBNBR = FMANBR " +
					  "WHERE FMBNBR = ? AND FMBLOS > 0 " +
					  "ORDER BY FMBLOS";

		PreparedStatement definitionByFormBySearch1 = conn1.prepareStatement(definitionByFormBySearch);
		PreparedStatement definitionByFormBySearch2 = conn2.prepareStatement(definitionByFormBySearch);
		PreparedStatement definitionByFormBySearch3 = conn3.prepareStatement(definitionByFormBySearch);
		PreparedStatement definitionByFormBySearch4 = conn4.prepareStatement(definitionByFormBySearch);
		PreparedStatement definitionByFormBySearch5 = conn5.prepareStatement(definitionByFormBySearch);
		PreparedStatement definitionByFormBySearch6 = conn6.prepareStatement(definitionByFormBySearch);

		findDefinitionByFormBySearch = new Stack();
		findDefinitionByFormBySearch.push(definitionByFormBySearch1);
		findDefinitionByFormBySearch.push(definitionByFormBySearch2);
		findDefinitionByFormBySearch.push(definitionByFormBySearch3);
		findDefinitionByFormBySearch.push(definitionByFormBySearch4);
		findDefinitionByFormBySearch.push(definitionByFormBySearch5);
		findDefinitionByFormBySearch.push(definitionByFormBySearch6);
		
		
		String definitionByFormByTemplate = 
					  "SELECT * " +
					  "FROM " + library + "FMPJREPT " +
					  "JOIN " + library + "FMPKTEMP ON FMJRPT = FMKRPT " +					
					  "JOIN " + library + "FMPBFDEF ON FMJNBR = FMBNBR AND FMJCOL = FMBCOL " +					  
					  "JOIN " + library + "FMPAFHDR ON FMJNBR = FMANBR " +				
					  "WHERE FMJNBR = ? AND LOWER(FMKTMP) = ? AND FMJSEQ > 0 " +
					  "ORDER BY FMJNBR, FMJSEQ, FMJCOL";

		PreparedStatement definitionByFormByTemplate1 = conn1.prepareStatement(definitionByFormByTemplate);
		PreparedStatement definitionByFormByTemplate2 = conn2.prepareStatement(definitionByFormByTemplate);
		PreparedStatement definitionByFormByTemplate3 = conn3.prepareStatement(definitionByFormByTemplate);
		PreparedStatement definitionByFormByTemplate4 = conn4.prepareStatement(definitionByFormByTemplate);
		PreparedStatement definitionByFormByTemplate5 = conn5.prepareStatement(definitionByFormByTemplate);
		PreparedStatement definitionByFormByTemplate6 = conn6.prepareStatement(definitionByFormByTemplate);

		findDefinitionByFormByTemplate = new Stack();
		findDefinitionByFormByTemplate.push(definitionByFormByTemplate1);
		findDefinitionByFormByTemplate.push(definitionByFormByTemplate2);
		findDefinitionByFormByTemplate.push(definitionByFormByTemplate3);
		findDefinitionByFormByTemplate.push(definitionByFormByTemplate4);
		findDefinitionByFormByTemplate.push(definitionByFormByTemplate5);
		findDefinitionByFormByTemplate.push(definitionByFormByTemplate6);
		
		
		String definitionByFormByType = 
			  "SELECT * " +
			  "FROM " + library + "FMPBFDEF " +
			  "JOIN " + library + "FMPAFHDR ON FMBNBR = FMANBR " +
			  "WHERE FMBNBR = ? AND UPPER(FMBDTY) = ? " +
			  "ORDER BY FMBLOV, FMBCOL";

		PreparedStatement definitionByFormByType1 = conn1.prepareStatement(definitionByFormByType);
		PreparedStatement definitionByFormByType2 = conn2.prepareStatement(definitionByFormByType);
		PreparedStatement definitionByFormByType3 = conn3.prepareStatement(definitionByFormByType);
		PreparedStatement definitionByFormByType4 = conn4.prepareStatement(definitionByFormByType);
		PreparedStatement definitionByFormByType5 = conn5.prepareStatement(definitionByFormByType);
		PreparedStatement definitionByFormByType6 = conn6.prepareStatement(definitionByFormByType);

		findDefinitionByFormByType = new Stack();
		findDefinitionByFormByType.push(definitionByFormByType1);
		findDefinitionByFormByType.push(definitionByFormByType2);
		findDefinitionByFormByType.push(definitionByFormByType3);
		findDefinitionByFormByType.push(definitionByFormByType4);
		findDefinitionByFormByType.push(definitionByFormByType5);
		findDefinitionByFormByType.push(definitionByFormByType6);				
				
		
		String definitionByFormByView = 
			  "SELECT * " +
			  "FROM " + library + "FMPBFDEF " +
			  "JOIN " + library + "FMPAFHDR ON FMBNBR = FMANBR " +
			  "WHERE FMBNBR = ? AND FMBLOV > 0 " +
			  "ORDER BY FMBLOV";

		PreparedStatement definitionByFormByView1 = conn1.prepareStatement(definitionByFormByView);
		PreparedStatement definitionByFormByView2 = conn2.prepareStatement(definitionByFormByView);
		PreparedStatement definitionByFormByView3 = conn3.prepareStatement(definitionByFormByView);
		PreparedStatement definitionByFormByView4 = conn4.prepareStatement(definitionByFormByView);
		PreparedStatement definitionByFormByView5 = conn5.prepareStatement(definitionByFormByView);
		PreparedStatement definitionByFormByView6 = conn6.prepareStatement(definitionByFormByView);

		findDefinitionByFormByView = new Stack();
		findDefinitionByFormByView.push(definitionByFormByView1);
		findDefinitionByFormByView.push(definitionByFormByView2);
		findDefinitionByFormByView.push(definitionByFormByView3);
		findDefinitionByFormByView.push(definitionByFormByView4);
		findDefinitionByFormByView.push(definitionByFormByView5);
		findDefinitionByFormByView.push(definitionByFormByView6);
				
		
		String definitionByJoinByForm = 
					  "SELECT * " +
					  "FROM " + library + "FMPBFDEF " +					 
					  "WHERE FMBJFN = ? AND UPPER(FMBDTY) LIKE '%TRANSACTION%' " +
					  "ORDER BY FMBNBR, FMBCOL";

		PreparedStatement definitionByJoinByForm1 = conn1.prepareStatement(definitionByJoinByForm);
		PreparedStatement definitionByJoinByForm2 = conn2.prepareStatement(definitionByJoinByForm);
		PreparedStatement definitionByJoinByForm3 = conn3.prepareStatement(definitionByJoinByForm);
		PreparedStatement definitionByJoinByForm4 = conn4.prepareStatement(definitionByJoinByForm);
		PreparedStatement definitionByJoinByForm5 = conn5.prepareStatement(definitionByJoinByForm);
		PreparedStatement definitionByJoinByForm6 = conn6.prepareStatement(definitionByJoinByForm);

		findDefinitionByJoinByForm = new Stack();
		findDefinitionByJoinByForm.push(definitionByJoinByForm1);
		findDefinitionByJoinByForm.push(definitionByJoinByForm2);
		findDefinitionByJoinByForm.push(definitionByJoinByForm3);
		findDefinitionByJoinByForm.push(definitionByJoinByForm4);
		findDefinitionByJoinByForm.push(definitionByJoinByForm5);
		findDefinitionByJoinByForm.push(definitionByJoinByForm6);
		

		String definitionByNextIndex = 
			  "SELECT * " +
			  "FROM " + library + "FMPBFDEF " +
			  "JOIN " + library + "FMPAFHDR ON FMBNBR = FMANBR " +
			  "WHERE FMBNBR = ? AND FMBDCD = ? AND FMBBIN > 0 " +
		      "ORDER BY FMBBIN DESC";
		
		PreparedStatement definitionByNextIndex1 = conn1.prepareStatement(definitionByNextIndex);
		PreparedStatement definitionByNextIndex2 = conn2.prepareStatement(definitionByNextIndex);
		PreparedStatement definitionByNextIndex3 = conn3.prepareStatement(definitionByNextIndex);
		PreparedStatement definitionByNextIndex4 = conn4.prepareStatement(definitionByNextIndex);
		PreparedStatement definitionByNextIndex5 = conn5.prepareStatement(definitionByNextIndex);
		PreparedStatement definitionByNextIndex6 = conn6.prepareStatement(definitionByNextIndex);

		findDefinitionByNextIndex = new Stack();
		findDefinitionByNextIndex.push(definitionByNextIndex1);
		findDefinitionByNextIndex.push(definitionByNextIndex2);
		findDefinitionByNextIndex.push(definitionByNextIndex3);
		findDefinitionByNextIndex.push(definitionByNextIndex4);
		findDefinitionByNextIndex.push(definitionByNextIndex5);
		findDefinitionByNextIndex.push(definitionByNextIndex6);
		
		
		String definitionByType = 
			  "SELECT * " +
			  "FROM " + library + "FMPBFDEF " +
			  "JOIN " + library + "FMPAFHDR ON FMBNBR = FMANBR " +
			  "WHERE UPPER(FMBDTY) = ? " +
			  "ORDER BY FMBLOV, FMBCOL";

		PreparedStatement definitionByType1 = conn1.prepareStatement(definitionByType);
		PreparedStatement definitionByType2 = conn2.prepareStatement(definitionByType);
		PreparedStatement definitionByType3 = conn3.prepareStatement(definitionByType);
		PreparedStatement definitionByType4 = conn4.prepareStatement(definitionByType);
		PreparedStatement definitionByType5 = conn5.prepareStatement(definitionByType);
		PreparedStatement definitionByType6 = conn6.prepareStatement(definitionByType);

		findDefinitionByType = new Stack();
		findDefinitionByType.push(definitionByType1);
		findDefinitionByType.push(definitionByType2);
		findDefinitionByType.push(definitionByType3);
		findDefinitionByType.push(definitionByType4);
		findDefinitionByType.push(definitionByType5);
		findDefinitionByType.push(definitionByType6);				
		
// 9/22/08 TWalton add Close of Connections
		ConnectionStack.returnConnection(conn1);
		ConnectionStack.returnConnection(conn2);
		ConnectionStack.returnConnection(conn3);
		ConnectionStack.returnConnection(conn4);
		ConnectionStack.returnConnection(conn5);
		ConnectionStack.returnConnection(conn6);
  
	}				

    catch (SQLException e) {
		System.out.println("SQL exception occured at com.treetop.data.FormDefinition.init()" + e);
	}
    
	}
	
}
/**
 * Insert a record into the Form System Column Definitions.
 *
 * Creation date: (8/13/2003 1:50:29 PM)
 */
private void insert() {

	try {
	
		PreparedStatement insertData = (PreparedStatement) sqlInsert.pop();
		
		insertData.setString(1, recordDefId);
		insertData.setString(2, statusCode);
		insertData.setInt(3, formNumber.intValue());
		insertData.setInt(4, columnNumber.intValue());
		insertData.setString(5, dataType);
		insertData.setString(6, dataSearch);
		insertData.setString(7, dataCode);
		insertData.setInt(8, dataNumber.intValue());
		insertData.setInt(9, decimalPositions.intValue());
		insertData.setInt(10, operand1.intValue());
		insertData.setInt(11, operand2.intValue());
		insertData.setString(12, formula);
		insertData.setInt(13, formulaNumber.intValue());
		insertData.setString(14, formulaInclusion);
		insertData.setInt(15, joinFormNumber.intValue());
		insertData.setInt(16, joinFormColumn.intValue()); 		
		insertData.setInt(17, sortOrderSequence.intValue());
		insertData.setString(18, sortOrderStyle);
		insertData.setInt(19, listOrderDescription.intValue());
		insertData.setInt(20, listOrderEntry.intValue());
		insertData.setInt(21, listOrderInquiry.intValue());
		insertData.setInt(22, listOrderSearch.intValue());
		insertData.setInt(23, listOrderView.intValue());
		insertData.setString(24, listSeparatorValue);
		insertData.setInt(25, listSeparatorLength.intValue());
		insertData.setString(26, tableDataFormat);
		insertData.setBigDecimal(27, numericLow);
		insertData.setBigDecimal(28, numericHigh);
		insertData.setDate(29, dateLow);
		insertData.setDate(30, dateHigh);
		insertData.setTime(31, timeLow);
		insertData.setTime(32, timeHigh);
		insertData.setString(33, textLow);
		insertData.setString(34, textHigh);
		insertData.setString(35, requiredEntry);
		insertData.setString(36, requiredRange);
		insertData.setString(37, unitOfMeasure);
		insertData.setString(38, searchType);
		insertData.setString(39, buildClass);
		insertData.setString(40, buildDropDown);
		insertData.setString(41, overrideEdit);
		insertData.setInt(42, inputSize.intValue());
		insertData.setInt(43, inputMaxLength.intValue());
		insertData.setString(44, showTotals);
		insertData.setString(45, showAverage);	
		insertData.setString(46, headingLong);
		insertData.setString(47, headingShort);
	    insertData.setString(48, updateDefUser);
		insertData.setDate(49, updateDefDate);
		insertData.setTime(50, updateDefTime);		
		insertData.setString(51, referenceGuideDef);
		insertData.setString(52, helpTextDef);
				
		insertData.executeUpdate();

		sqlInsert.push(insertData);
		
	}	
	catch (SQLException e) {	
		System.out.println("SQL error at com.treetop.data.FormDefinition.insert(): " + e);
	}
	
}
/**
 * Load fields from data base record.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
protected void loadFields(ResultSet rs, String loadMode) {

	try {

		super.loadFields(rs, loadMode);		
												
		recordDefId       		= rs.getString("FMBREC");
		statusCode        		= rs.getString("FMBACT");
		formNumber        		= new Integer(rs.getInt("FMBNBR"));
	    columnNumber      		= new Integer(rs.getInt("FMBCOL"));
		dataType          		= rs.getString("FMBDTY");
		dataSearch				= rs.getString("FMBDSR");
		dataCode          		= rs.getString("FMBDCD");
		dataNumber        		= new Integer(rs.getInt("FMBBIN"));
		decimalPositions  		= new Integer(rs.getInt("FMBDEC"));
		dropDownList			= "";
		operand1          		= new Integer(rs.getInt("FMBOP1"));
		operand2          		= new Integer(rs.getInt("FMBOP2"));
		formula           		= rs.getString("FMBFOR");
		formulaDataCode   		= "";
		formulaNumber     		= new Integer(rs.getInt("FMBFNO"));
		formulaInclusion  		= rs.getString("FMBFIN");
		joinFormNumber    		= new Integer(rs.getInt("FMBJFN"));
		joinFormColumn  		= new Integer(rs.getInt("FMBJFC"));
		sortOrderSequence  		= new Integer(rs.getInt("FMBSSQ"));
		sortOrderStyle    		= rs.getString("FMBSST");
		listOrderDescription	= new Integer(rs.getInt("FMBLOD"));
		listOrderEntry    		= new Integer(rs.getInt("FMBLOE"));
		listOrderInquiry		= new Integer(rs.getInt("FMBLOI"));
		listOrderSearch    		= new Integer(rs.getInt("FMBLOS"));
		listOrderView    		= new Integer(rs.getInt("FMBLOV"));
		listSeparatorValue 		= rs.getString("FMBLSV");
		listSeparatorLength		= new Integer(rs.getInt("FMBLSL"));
		tableDataFormat   		= rs.getString("FMBFMT");
		numericLow        		= rs.getBigDecimal("FMBNLO");
		numericHigh       		= rs.getBigDecimal("FMBNHI");	   
		dateLow           		= rs.getDate("FMBDLO");
		dateHigh          		= rs.getDate("FMBDHI");
		timeLow           		= rs.getTime("FMBTLO");
		timeHigh          		= rs.getTime("FMBTHI");
		textLow           		= rs.getString("FMBXLO");
		textHigh          		= rs.getString("FMBXHI");
		requiredEntry     		= rs.getString("FMBRQE");
		requiredRange     		= rs.getString("FMBRQR");
		unitOfMeasure     		= rs.getString("FMBUOM");
		searchType        		= rs.getString("FMBTOS");
		buildClass				= rs.getString("FMBBCL");
		buildDropDown			= rs.getString("FMBBDD");
		overrideEdit		  	= rs.getString("FMBOVE");
		inputSize         		= new Integer(rs.getInt("FMBIBS"));
		inputMaxLength    		= new Integer(rs.getInt("FMBIBL"));
		showTotals        		= rs.getString("FMBTOT");
		showAverage       		= rs.getString("FMBAVG");
		headingLong       		= rs.getString("FMBRHL");
		headingShort      		= rs.getString("FMBRHS");
		updateDefUser     		= rs.getString("FMBUSR");
		updateDefUserName 		= updateDefUser;
		updateDefDate     		= rs.getDate("FMBDTE");
		updateDefTime     		= rs.getTime("FMBTME");
		referenceGuideDef 		= rs.getString("FMBREF");
		helpTextDef       		= rs.getString("FMBHLP");
														
		
		try {
		
			if (loadMode.equals("all")) {
			UserFile newUser = new UserFile(updateDefUser);
			Integer  newUserNum = new Integer(newUser.getUserNumber());
			newUser = new UserFile(newUserNum);
			updateDefUserName = newUser.getUserNameLong();
			}
			
		}
		catch (Exception e) {		
		}
				
	
	}
	catch (Exception e) {	
		System.out.println("SQL Exception at com.treetop.data.FormDefinition" +
			               ".loadFields(RS String): " + e);
	}
				
}
/**
 * Form Column definition file testing.
 *
 * Creation date: (8/14/2003 8:24:29 AM)
 */
public static void main(String[] args) {	


	try {

		String[] role;
	    String[] group; 
	    String   profile = "DEISEN";
	    role  = new String[1];
	    role[0]  = "77";
	    group = new String[3];
	    group[0] = "0";
	    group[1] = "1";
	    group[2] = "2";
	    Integer form = new Integer("935");
	    String  temp = "jsp10";
		Vector  view = findDefinitionByFormByTemplate(form, temp, role, group, profile);	

	    System.out.println("Find by Template: " + temp + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormDefinition.main() on find by template: " + e);		
	}
	
	
	try {

		Integer  form   = new Integer("13");
		String   code   = "NU";
		
		int index = findDefinitionByNextIndex(form, code);		

		System.out.println("FormDefinition Next Index: " + index);
	}
	catch (Exception e) {
		System.out.println("Error: FormDefinition.main() Next Index: " + e);		 
	}

	
	try {
		
	    Integer  value1 = new Integer("13");
	    Integer  value2 = new Integer("15");
	    Integer  line   = new Integer("0");
	    
	    Vector data     = new Vector();
	    Vector result   = new Vector();
	    FormData info   = new FormData();
	    
	    FormDefinition vector0 = new FormDefinition(value1, value2);
	    vector0.setColumnNumber(line);
	    vector0.dateHigh = java.sql.Date.valueOf("2003-08-21");
	    vector0.dateLow  = java.sql.Date.valueOf("2003-08-20"); 
		data.addElement(vector0);

		FormDefinition vector1 = new FormDefinition(value1, value2);
		BigDecimal value3 = new BigDecimal(0);
		BigDecimal value4 = new BigDecimal(999);
	    vector1.setNumericLow(value3);
	    vector1.setNumericHigh(value4);
	   	data.addElement(vector1);

	   	result = FormData.findDataByRange(data, data, data);

		System.out.println("FormDefinition Select by Range: " + value1 + " " + value2);
	}
	catch (Exception e) {
		System.out.println("Error: FormDefinition.main() on Select by Range: " + e);		 
	}

	
	// find definitions. 

	try {

		Integer        value1 = new Integer("13");
		Integer        value2 = new Integer("5");
		FormDefinition form   = new FormDefinition(value1, value2);		
	           
	    System.out.println("FormDefinition instantiation: " + value1 + " " + value2 + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormDefinition.main() on Instantiation: " + e);		
	}
	
	
	try {

		String[] role;
	    String[] group; 
	    String   profile = "DEISEN";
	    role  = new String[1];
	    role[0]  = "77";
	    group = new String[3];
	    group[0] = "0";
	    group[1] = "1";
	    group[2] = "2";
	    Integer form = new Integer("13");
	    Integer tran = new Integer("0");
		Vector  column  = new Vector();
		        column  = findDefinitionByFormByEntry(form, tran, role, group, profile);	

	    System.out.println("Find by Form: " + form + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormDefinition.main() on find1: " + e);		
	}

		
	// insert definitions. 
	
	try {

		java.sql.Date loDate = java.sql.Date.valueOf("1111-11-11");
		java.sql.Date hiDate = java.sql.Date.valueOf("2222-22-22");
		java.sql.Time loTime = java.sql.Time.valueOf("00:00:00");
		java.sql.Time hiTime = java.sql.Time.valueOf("00:00:00");
		java.sql.Date theDate = java.sql.Date.valueOf("2003-05-19");
	    java.sql.Time theTime = java.sql.Time.valueOf("03:45:10");
	    Integer value1 = new Integer("5544");
	    Integer value2 = new Integer("633");
	    FormDefinition form1 = new FormDefinition();
	    form1.setFormNumber(value1);
	    form1.setColumnNumber(value2);
	    form1.setRecordDefId("FD");
	    form1.setStatusCode("A");	    
	    form1.setHeadingLong("12345678901234567890");
	    form1.setHeadingShort("12341234");
	    Integer value3 = new Integer(1);	    
	    form1.setDataType("Number");
	    form1.setDataCode("NU");
	    BigDecimal value5 = new BigDecimal(555.88);
	    form1.setNumericLow(value5);
	    BigDecimal value6 = new BigDecimal(7777.44);
	    form1.setNumericHigh(value6);
	    Integer value7 = new Integer(2);
	    form1.setDecimalPositions(value7);
	    form1.setDateLow(loDate);
	    form1.setDateHigh(hiDate);
	    form1.setTimeLow(loTime);
	    form1.setTimeHigh(hiTime);
	    form1.setTextLow("");
	    form1.setTextHigh("");
	    form1.setUnitOfMeasure("unit of measure");
	    form1.setOverrideEdit("N");
	    form1.setHelpTextDef("Help text, which can be up to 500 bytes long.");
	    Integer value8 = new Integer(4);
	    form1.setInputSize(value8);
	    form1.setShowTotals("Y");
	    form1.setShowAverage("Y");
	    form1.setUpdateDefUser("DEISEN");
	    form1.setUpdateDefDate(theDate);
	    form1.setUpdateDefTime(theTime);
	    form1.insert();
	   	    
		System.out.println("First insert: " + form1 + " sucessfull");
	}				
	catch (Exception e) {
		System.out.println("Error: FormDefinition.main() on insert1: " + e);
	}

	
	// new class. 

	try {
	    Integer value1 = new Integer("5544");
	    Integer value2 = new Integer("633");
	    FormDefinition form1 = new FormDefinition(value1, value2);

	    System.out.println("New class: " + value1 + " " + value2 + " sucessfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormDefinition.main() on new class1: " + e);
	}   
	
}
/**
 * Retrieve the next available column number.
 *
 * Creation date: (10/02/2003 4:36:39 PM)
 *
 */
public static int nextColumnNumber() {
//	 9/22/08 TWalton not currently being used // Comment OUT 
	try {
		
	//	AS400       as400 = new AS400("10.6.100.1", "DAUSER", "web230502");
	//	ProgramCall pgm   = new ProgramCall(as400);

	//	ProgramParameter[] parmList = new ProgramParameter[1];
	//	parmList[0] = new ProgramParameter(100);
	//	        pgm = new ProgramCall(as400, "/QSYS.LIB/GNLIB.LIB/GNCFORMCOL.PGM", parmList);

//		if (pgm.run() != true)
	//		return 0;
		
		//else {
			//AS400PackedDecimal number = new AS400PackedDecimal(11, 0);
//			byte[] data = parmList[0].getOutputData();
	//		double dd   = number.toDouble(data, 0);
		//	int    tran = (int) dd;
			//as400.disconnectService(AS400.COMMAND);
//			return tran;
	//	}

	}
	catch (Exception e) {
		return 0;
	}
	return 0;
}
/**
 * Retrieve the next available forumla number.
 *
 * Creation date: (11/06/2003 4:36:39 PM)
 */
public static int nextFormulaNumber() {
// 9/22/08 TWalton - Not currently using this method // Comment OUT
	try {
		
		//AS400       as400 = new AS400("10.6.100.1", "DAUSER", "web230502");
//		ProgramCall pgm   = new ProgramCall(as400);

	//	ProgramParameter[] parmList = new ProgramParameter[1];
		//parmList[0] = new ProgramParameter(100);
		  //      pgm = new ProgramCall(as400, "/QSYS.LIB/GNLIB.LIB/GNCFORMFOR.PGM", parmList);

//		if (pgm.run() != true)
	//		return 0;
		
		//else {
			//AS400PackedDecimal number = new AS400PackedDecimal(11, 0);
//			byte[] data    = parmList[0].getOutputData();
	//		double dd      = number.toDouble(data, 0);
		//	int    formula = (int) dd;
			//as400.disconnectService(AS400.COMMAND);
//			return formula;
	//	}

	}
	catch (Exception e) {
		return 0;
	}
	return 0;
}
/**
 * Send in the class for the record to be updated into the file.
 *
 * Creation date: (10/07/2003 1:48:29 PM)
 */
public static void updateFormDefinition(FormDefinition inUpdateData) {
	
	inUpdateData.update();	       
	
}
/**
 * Send in the vector for the records to be updated into the file.
 *
 * Creation date: (10/07/2003 1:48:29 PM)
 */
public static void updateFormDefinition(Vector inUpdateData) {

	for (int x = 0; x < inUpdateData.size(); x ++)
		{	
			 
		FormDefinition updateData = (FormDefinition) inUpdateData.elementAt(x);
				 updateData.update();
		         
		}	

}
/**
 * Update the definition cell with the index value used to store its data.
 *
 * Creation date: (10/06/2003 8:24:29 AM)
 */
public static int updateNextIndex(Integer inFormNumber, Integer inColumnNumber) {
	                                    
	ResultSet      rs         = null;
	FormDefinition definition = new FormDefinition();
	int            index      = 0;	
		
	try {

		PreparedStatement definitionByFormByColumn = (PreparedStatement) FormDefinition.findDefinitionByFormByColumn.pop();
		
		definitionByFormByColumn.setInt(1, inFormNumber.intValue());
		definitionByFormByColumn.setInt(2, inColumnNumber.intValue());
				
		rs = definitionByFormByColumn.executeQuery();
		FormDefinition.findDefinitionByFormByColumn.push(definitionByFormByColumn);
		

		try {
			if (rs.next())
			{				
				 FormDefinition column = new FormDefinition(rs, "standard");	
								index  = findDefinitionByNextIndex(column.formNumber, column.dataCode);	
					column.dataNumber  = new Integer(index);
				    
				 updateFormDefinition(column); 
			}
			else
			index = 0;
					    
			rs.close();
		}
		catch (Exception e){
			System.out.println("Exception error processing a result set " +
							   "(FormDefinition.updateNextIndex): " + e);
		}
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormDefinition." +
						   "updateNextIndex(Interger Interger): " + e);
	}

	return index;			
}
/**
 * Set the build class control.
 *
 * Creation date: (8/24/2004 10:27:28 AM)
 */
public void setBuildClass(String inBuildClass) throws InvalidLengthException {
	
	if (inBuildClass.length() > 1)
		throw new InvalidLengthException(
				"inBuildClass", inBuildClass.length(), 1);

	this.buildClass = inBuildClass;
}
/**
 * Set the build drop drown list control.
 *
 * Creation date: (9/21/2004 10:27:28 AM)
 */
public void setBuildDropDown(String inBuildDropDown) throws InvalidLengthException {
	
	if (inBuildDropDown.length() > 1)
		throw new InvalidLengthException(
				"inBuildDropDown", inBuildDropDown.length(), 1);

	this.buildClass = inBuildDropDown;
}
/**
 * Set column number.
 *
 * Creation date: (10/02/2003 10:27:28 AM)
 */
public void setColumnNumber(Integer inColumnNumber) {
		
	this.columnNumber = inColumnNumber;
}
/**
 * Set type of data code.
 *
 * Creation date: (8/21/2003 10:27:28 AM)
 */
public void setDataCode(String inDataCode) throws InvalidLengthException {
	
	if (inDataCode.length() > 2)
		throw new InvalidLengthException(
				"inDataCode", inDataCode.length(), 2);

	this.dataCode = inDataCode;
}
/**
 * Set the column data number (position of data in transaction record arrays).
 *
 * Creation date: (10/09/2003 10:27:28 AM)
 */
public void setDataNumber(Integer inDataNumber) {
		
	this.dataNumber = inDataNumber;
}
/**
 * Set type of data search description.
 *
 * Creation date: (9/21/2004 10:27:28 AM)
 */
public void setDataSearch(String inDataSearch) throws InvalidLengthException {
	
	if (inDataSearch.length() > 20)
		throw new InvalidLengthException(
				"inDataSearch", inDataSearch.length(), 20);

	this.dataSearch = inDataSearch;
}
/**
 * Set type of data description.
 *
 * Creation date: (8/21/2003 10:27:28 AM)
 */
public void setDataType(String inDataType) throws InvalidLengthException {
	
	if (inDataType.length() > 20)
		throw new InvalidLengthException(
				"inDataType", inDataType.length(), 20);

	this.dataType = inDataType;
}
/**
 * Set the date high value.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setDateHigh(java.sql.Date inDateHigh) {
		
	this.dateHigh = inDateHigh;
}
/**
 * Set the date low value.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setDateLow(java.sql.Date inDateLow) {
		
	this.dateLow = inDateLow;
}
/**
 * Set number of decimal positions for numeric data.
 *
 * Creation date: (8/14/2003 10:27:28 AM)
 */
public void setDecimalPositions(Integer inDecimalPositions) {
		
	this.decimalPositions = inDecimalPositions;
}
/**
 * Set the drop down list.
 *
 * Creation date: (9/21/2004 10:27:28 AM)
 */
public void setDropDownList(String inDropDownList) throws InvalidLengthException {
	
	if (inDropDownList.length() > 8000)
		throw new InvalidLengthException(
				"inDropDownList", inDropDownList.length(), 8000);

	this.dropDownList = inDropDownList;
}
/**
 * Set form number.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setFormNumber(Integer inFormNumber) {
		
	this.formNumber = inFormNumber;
}
/**
 * Set calculation formula description.
 *
 * Creation date: (10/15/2003 10:27:28 AM)
 */
public void setFormula(String inFormula) throws InvalidLengthException {
	
	if (inFormula.length() > 20)
		throw new InvalidLengthException(
				"inFormula", inFormula.length(), 20);

	this.formula = inFormula;
}
/**
 * Set the form definition input data type code for calculated cells.
 *
 * Creation date: (10/22/2003 10:27:28 AM)
 */
public void setFormulaDataCode(String inFormulaDataCode) throws InvalidLengthException {
	
	if (inFormulaDataCode.length() > 2)
		throw new InvalidLengthException(
				"inFormulaDataCode", inFormulaDataCode.length(), 2);

	this.formulaDataCode = inFormulaDataCode;
}
/**
 * Retrieve the form definition operand inclusion code for cells used in calculations.
 *
 * Creation date: (11/10/2003 10:27:28 AM)
 */
public void setFormulaInclusion(String inFormulaInclusion) throws InvalidLengthException {
	
	if (inFormulaInclusion.length() > 1)
		throw new InvalidLengthException(
				"inFormulaInclusion", inFormulaInclusion.length(), 1);

	this.formulaInclusion = inFormulaInclusion;
}
/**
 * Set the calculation formula number.
 *
 * Creation date: (11/06/2003 10:27:28 AM)
 */
public void setFormulaNumber(Integer inFormulaNumber) {
		
	this.formulaNumber = inFormulaNumber;
}
/**
 * Set long report heading.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setHeadingLong(String inHeadingLong) throws InvalidLengthException {
	
	if (inHeadingLong.length() > 50)
		throw new InvalidLengthException(
				"inHeadingLong", inHeadingLong.length(), 50);

	this.headingLong = inHeadingLong;
}
/**
 * Set short report heading.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setHeadingShort(String inHeadingShort) throws InvalidLengthException {
	
	if (inHeadingShort.length() > 20)
		throw new InvalidLengthException(
				"inHeadingShort", inHeadingShort.length(), 20);

	this.headingShort = inHeadingShort;
}
/**
 * Set help text.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setHelpTextDef(String inHelpText) throws InvalidLengthException {
	
	if (inHelpText.length() > 500)
		throw new InvalidLengthException(
				"inHelpText", inHelpText.length(), 500);

	this.helpTextDef = inHelpText;
}
/**
 * Set column input box size maximum length.
 *
 * Creation date: (10/02/2003 10:27:28 AM)
 */
public void setInputMaxLength(Integer inInputMaxLength) {
		
	this.inputMaxLength = inInputMaxLength;
}
/**
 * Set column input box size (length).
 *
 * Creation date: (8/14/2003 10:27:28 AM)
 */
public void setInputSize(Integer inInputSize) {
		
	this.inputSize = inInputSize;
}
/**
 * Set joining form number.
 *
 * Creation date: (8/23/2004 10:27:28 AM)
 */
public void setJoinFormNumber(Integer inFormNumber) {
		
	this.joinFormNumber = inFormNumber;
}
/**
 * Set joining form column number.
 *
 * Creation date: (8/23/2004 10:27:28 AM)
 */
public void setJoinFormColumn(Integer inColumnNumber) {
		
	this.joinFormColumn = inColumnNumber;
}
/**
 * Set the form definition list order for each description.
 *
 * Creation date: (10/01/2004 10:53:28 AM)
 */
public void setListOrderDescription(Integer inListOrder) {
		
	this.listOrderDescription = inListOrder;
}
/**
 * Set column list order for data entry.
 *
 * Creation date: (10/02/2003 10:27:28 AM)
 */
public void setListOrderEntry(Integer inListOrder) {
		
	this.listOrderEntry = inListOrder;
}
/**
 * Set column list order for inquiry.
 *
 * Creation date: (9/03/2004 10:27:28 AM)
 */
public void setListOrderInquiry(Integer inListOrder) {
		
	this.listOrderInquiry = inListOrder;
}
/**
 * Retrieve the form definition list order for data searching (drop down list).
 *
 * Creation date: (9/24/2004 10:53:28 AM)
 */
public void setListOrderSearch(Integer inListOrder) {
		
	this.listOrderSearch = inListOrder;
}
/**
 * Set column list order for viewing report.
 *
 * Creation date: (10/02/2003 10:27:28 AM)
 */
public void setListOrderView(Integer inListOrder) {
		
	this.listOrderView = inListOrder;
}
/**
 * Set the form definition list separator value length.
 *
 * Creation date: (10/27/2004 10:53:28 AM)
 */
public void setListSeparatorLength(Integer inListSeparatorLength) {
		
	this.listSeparatorLength = inListSeparatorLength;
}
/**
 * Set the form definition list separator value, used between cells.
 *
 * Creation date: (10/27/2004 4:45:28 PM)
 */
public void setListSeparatorValue(String inListSeparatorValue) throws InvalidLengthException {
	
	if (inListSeparatorValue.length() > 15)
		throw new InvalidLengthException(
				"inListSeparatorValue", inListSeparatorValue.length(), 15);

	this.listSeparatorValue = inListSeparatorValue;
}
/**
 * Set numeric high value.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setNumericHigh(BigDecimal inNumericHigh) {
		
	this.numericHigh = inNumericHigh;
}
/**
 * Set numeric low value.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setNumericLow(BigDecimal inNumericLow) {
		
	this.numericLow = inNumericLow;
}
/**
 * Set calculation operand column number.
 *
 * Creation date: (10/15/2003 10:27:28 AM)
 */
public void setOperand1(Integer inOperand1) {
		
	this.operand1 = inOperand1;
}
/**
 * Set calculation operand column number.
 *
 * Creation date: (10/15/2003 10:27:28 AM)
 */
public void setOperand2(Integer inOperand2) {
		
	this.operand2 = inOperand2;
}
/**
 * Set override edit.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setOverrideEdit(String inOverrideEdit) throws InvalidLengthException {
	
	if (inOverrideEdit.length() > 1)
		throw new InvalidLengthException(
				"inOverrideEdit", inOverrideEdit.length(), 1);

	this.overrideEdit = inOverrideEdit;
}
/**
 * Set the record identification code.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setRecordDefId(String inRecordId) throws InvalidLengthException {
	
	if (inRecordId.length() > 2)
		throw new InvalidLengthException(
				"inRecordId", inRecordId.length(), 2);

	this.recordDefId = inRecordId;
}
/**
 * Set reference guide.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setReferenceGuideDef(String inReferenceGuide) throws InvalidLengthException {
	
	if (inReferenceGuide.length() > 500)
		throw new InvalidLengthException(
				"inReferenceGuide", inReferenceGuide.length(), 500);

	this.referenceGuideDef = inReferenceGuide;
}
/**
 * Set required entry switch.
 *
 * Creation date: (10/02/2003 10:27:28 AM)
 */
public void setRequiredEntry(String inRequiredEntry) throws InvalidLengthException {
	
	if (inRequiredEntry.length() > 1)
		throw new InvalidLengthException(
				"inRequiredEntry", inRequiredEntry.length(), 1);

	this.requiredEntry = inRequiredEntry;
}
/**
 * Set required range for inquiry page switch.
 *
 * Creation date: (10/02/2003 10:27:28 AM)
 */
public void setRequiredRange(String inRequiredRange) throws InvalidLengthException {
	
	if (inRequiredRange.length() > 1)
		throw new InvalidLengthException(
				"inRequiredRange", inRequiredRange.length(), 1);

	this.requiredRange = inRequiredRange;
}
/**
 * Set search type for inquiry page.
 *
 * Creation date: (8/20/2004 10:27:28 AM)
 */
public void setSearchType(String inSearchType) throws InvalidLengthException {
	
	if (inSearchType.length() > 15)
		throw new InvalidLengthException(
				"inSearchType", inSearchType.length(), 15);

	this.searchType = inSearchType;
}
/**
 * Set control for showing column average.
 *
 * Creation date: (8/14/2003 10:27:28 AM)
 */
public void setShowAverage(String inShowAverage) throws InvalidLengthException {
	
	if (inShowAverage.length() > 1)
		throw new InvalidLengthException(
				"inShowAverage", inShowAverage.length(), 1);

	this.showAverage = inShowAverage;
}
/**
 * Set control for showing column totals.
 *
 * Creation date: (8/14/2003 10:27:28 AM)
 */
public void setShowTotals(String inShowTotals) throws InvalidLengthException {
	
	if (inShowTotals.length() > 1)
		throw new InvalidLengthException(
				"inShowTotals", inShowTotals.length(), 1);

	this.showTotals = inShowTotals;
}
/**
 * Set column sort order sequence number.
 *
 * Creation date: (10/02/2003 10:27:28 AM)
 */
public void setSortOrderSequence(Integer inSortOrderSequence) {
		
	this.sortOrderSequence = inSortOrderSequence;
}
/**
 * Set column sort order style (ascending, descending).
 *
 * Creation date: (10/02/2003 10:27:28 AM)
 */
public void setSortOrderStyle(String inSortOrderStyle) {
		
	this.sortOrderStyle = inSortOrderStyle;
}
/**
 * Set record activity status code.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setStatusCode(String inStatusCode) throws InvalidLengthException {
	
	if (inStatusCode.length() > 1)
		throw new InvalidLengthException(
				"inStatusCode", inStatusCode.length(), 1);

	this.statusCode = inStatusCode;
}
/**
 * Set the form definition HTML table data format.
 *
 * Creation date: (11/10/2003 10:27:28 AM)
 */
public void setTableDataFormat(String inTableDataFormat) throws InvalidLengthException {
	
	if (inTableDataFormat.length() > 15)
		throw new InvalidLengthException(
				"inTableDataFormat", inTableDataFormat.length(), 15);

	this.tableDataFormat = inTableDataFormat;
}
/**
 * Set text high value.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setTextHigh(String inTextHigh) throws InvalidLengthException {
	
	if (inTextHigh.length() > 20)
		throw new InvalidLengthException(
				"inTextHigh", inTextHigh.length(), 20);

	this.textHigh = inTextHigh;
}
/**
 * Set text low value.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setTextLow(String inTextLow) throws InvalidLengthException {
	
	if (inTextLow.length() > 20)
		throw new InvalidLengthException(
				"inTextLow", inTextLow.length(), 20);

	this.textLow = inTextLow;
}
/**
 * Set the time high value.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setTimeHigh(java.sql.Time inTimeHigh) {
		
	this.timeHigh = inTimeHigh;
}
/**
 * Set the time low value.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setTimeLow(java.sql.Time inTimeLow) {
		
	this.timeLow = inTimeLow;
}
/**
 * Set unit of measure description.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setUnitOfMeasure(String inUnitOfMeasure) throws InvalidLengthException {
	
	if (inUnitOfMeasure.length() > 20)
		throw new InvalidLengthException(
				"inUnitOfMeasure", inUnitOfMeasure.length(), 20);

	this.unitOfMeasure = inUnitOfMeasure;
}
/**
 * Set the date of the last record updated.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setUpdateDefDate(java.sql.Date inUpdateDate) {
		
	this.updateDefDate = inUpdateDate;
}
/**
 * Set the time of the last record updated.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setUpdateDefTime(java.sql.Time inUpdateTime) {
		
	this.updateDefTime = inUpdateTime;
}
/**
 * Set update user profile.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setUpdateDefUser(String inUpdateUser) throws InvalidLengthException {
	
	if (inUpdateUser.length() > 10)
		throw new InvalidLengthException(
				"inUpdateUser", inUpdateUser.length(), 10);

	this.updateDefUser = inUpdateUser;
}
/**
 * Set update user profile name.
 *
 * Creation date: (8/13/2003 10:27:28 AM)
 */
public void setUpdateDefUserName(String inUpdateUserName) throws InvalidLengthException {
	
	if (inUpdateUserName.length() > 30)
		throw new InvalidLengthException(
				"inUpdateUserName", inUpdateUserName.length(), 30);

	this.updateDefUserName = inUpdateUserName;
}
/**
 * Update a record in the Form System Column Definitions.
 *
 * Creation date: (8/14/2003 1:50:29 PM)
 */
private void update() {

	try {
	
		PreparedStatement updateData = (PreparedStatement) sqlUpdate.pop();
		
		updateData.setString(1, recordDefId);
		updateData.setString(2, statusCode);
		updateData.setInt(3, formNumber.intValue());
		updateData.setInt(4, columnNumber.intValue());
		updateData.setString(5, dataType);
		updateData.setString(6, dataSearch);
		updateData.setString(7, dataCode);
		updateData.setInt(8, dataNumber.intValue());
		updateData.setInt(9, decimalPositions.intValue());
		updateData.setInt(10, operand1.intValue());
		updateData.setInt(11, operand2.intValue());
		updateData.setString(12, formula);
		updateData.setInt(13, formulaNumber.intValue());
		updateData.setString(14, formulaInclusion);
		updateData.setInt(15, joinFormNumber.intValue());
		updateData.setInt(16, joinFormColumn.intValue()); 		
		updateData.setInt(17, sortOrderSequence.intValue());
		updateData.setString(18, sortOrderStyle);
		updateData.setInt(19, listOrderDescription.intValue());
		updateData.setInt(20, listOrderEntry.intValue());
		updateData.setInt(21, listOrderInquiry.intValue());
		updateData.setInt(22, listOrderSearch.intValue());
		updateData.setInt(23, listOrderView.intValue());
		updateData.setString(24, listSeparatorValue);
		updateData.setInt(25, listSeparatorLength.intValue());
		updateData.setString(26, tableDataFormat);
		updateData.setBigDecimal(27, numericLow);
		updateData.setBigDecimal(28, numericHigh);
		updateData.setDate(29, dateLow);
		updateData.setDate(30, dateHigh);
		updateData.setTime(31, timeLow);
		updateData.setTime(32, timeHigh);
		updateData.setString(33, textLow);
		updateData.setString(34, textHigh);
		updateData.setString(35, requiredEntry);
		updateData.setString(36, requiredRange);
		updateData.setString(37, unitOfMeasure);
		updateData.setString(38, searchType);
		updateData.setString(39, buildClass);
		updateData.setString(40, buildDropDown);
		updateData.setString(41, overrideEdit);
		updateData.setInt(42, inputSize.intValue());
		updateData.setInt(43, inputMaxLength.intValue());
		updateData.setString(44, showTotals);
		updateData.setString(45, showAverage);	
		updateData.setString(46, headingLong);
		updateData.setString(47, headingShort);
		updateData.setString(48, updateDefUser);
		updateData.setDate(49, updateDefDate);
		updateData.setTime(50, updateDefTime);		
		updateData.setString(51, referenceGuideDef);
		updateData.setString(52, helpTextDef);
		
		updateData.setInt(53, formNumber.intValue());
		updateData.setInt(54, columnNumber.intValue());
				
		updateData.executeUpdate();

		sqlUpdate.push(updateData);
		
	} 
	catch (SQLException e) {	
		System.out.println("SQL error at com.treetop.data.FormDefinition.update(): " + e);
	}
		
}

}
