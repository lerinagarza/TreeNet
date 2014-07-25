package com.treetop.data;

import java.sql.*;
import java.util.*;
import java.math.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import com.treetop.utilities.ConnectionStack;

/**
 * Access to Form System Data Values file DBLIB/FMPCDATA, DBLIB/FMPDCMNT & DBLIB/FMPHDEFT
 *         9/19/08 TWalton changed to NEW BOX -  Library DBPRD
 **/
public class FormData extends FormDefinition {	

	// Data base fields.
	
	private   	String        	recordDtaId;	
	private   	Integer      	tranNumber;
	private   	java.sql.Date 	tranEffDate;
	private   	String        	updateUser;
	private   	String        	updateUserName;
	private   	java.sql.Date 	updateDate;
	private   	java.sql.Time 	updateTime;
	protected  	BigDecimal    	dataNumeric;
	private   	java.sql.Date 	dataDate;
	private   	java.sql.Time 	dataTime;
	private  	String        	dataText;
	private		Vector			dataClass;
	private		Vector			dataClassName;
	private		Vector			dataDescription;
	private   	String        	dataAttribute;	
	private   	String        	recordCmtId;
	private   	String        	dataComment;
	private   	String        	recordDftId;
	private     String        	dataDefaulted;						
						
	
	// Define database environment (live or test) on the AS/400.
	
//	private static String library  = "WKLIB."; 		// test environment
    //private static String library  = "DBLIB."; 9/19/08-Twalton	Change to point to new machine	// live environment
	 private static String library = "DBPRD.";
 //   private static String prism    = "PRISME01.";	// live environment comment Out 9/19/08 TWalton - Will need to rewrite if necessary
 //   private static String systemAR = "ARDBFA."; comment Out 9/19/08 TWalton - Will need to rewrite if necessary


	// For use in Main Method,
    // Constructor Methods and Lookup Methods.

    private static Stack sqlDeleteData = null;
	private static Stack sqlInsertData = null;
	private static Stack sqlUpdateData = null;
		
	private static Stack sqlDeleteComment = null;
	private static Stack sqlInsertComment = null;
	private static Stack sqlUpdateComment = null;
	
	private static Stack sqlDeleteDefaulted = null;
	private static Stack sqlInsertDefaulted = null;
	private static Stack sqlUpdateDefaulted = null;	
	
	private static Stack findDataByFormByEntry = null;
	private static Stack findDataByFormByView  = null;
	private static Stack findDataByTranByDesc  = null;
	private static Stack findDataByTranByEntry = null;
	private static Stack findDataByTranByView  = null;	
				
	
	// Additional fields.
	
	private static boolean persists   = false;
	
	private static int     binNumeric = 30;
	private static int     binDate    = 20;
	private static int     binTime    = 20;
	private static int     binText    = 30; 
	private static int     noRounding = 12;

	private static java.sql.Date  defaultDate = java.sql.Date.valueOf("1950-01-01");
	private static java.sql.Time  defaultTime = java.sql.Time.valueOf("00:00:00");
	private static String         defaultCustomer = "     _              ";	
		
			
/**
 * Instantiate the form column data (cell).
 *
 * Creation date: (8/15/2003 10:36:39 AM)
 */
public FormData() {

	super();
	
	init();
	
}
/**
 * Instantiate the form column data (cell).
 *
 * Creation date: (10/21/2003 1:14:39 PM)
 */
public FormData(FormDefinition definition, ResultSet rs, String loadMode, String joinClass)
throws InstantiationException { 

	super(definition);	
	
	loadFieldsData(rs, loadMode, joinClass);	
}
/**
 Instantiate the form column data (cell).
 *
 * Creation date: (9/10/2003 1:14:39 PM)
 */
public FormData(ResultSet rs, String loadMode, String joinClass)
throws InstantiationException { 

	super();	
	
	loadFields(rs, loadMode, joinClass);	
}
/**
 * Build the cell duration data.
 *
 * Creation date: (10/16/2003 8:24:29 AM)
 */
public static FormData buildDataCalcDuration(FormData calcData, ResultSet rs, String firstPass,
	                                         String dataField1, String dataField2,
	                                         java.sql.Date lastDate, java.sql.Time lastTime, 
	                                         BigDecimal lastNumber) {
		                                     
	long   oneDay    = (24*60*60);
	long   oneHour   = (60*60);
	long   oneMinute = 60;
	int    days      = 0;
	int    hours     = 0;
	int    minutes   = 0;
	int    seconds   = 0;
	String elapsed   = "";
		                                     
	try {
		                                     
			
	    if (firstPass.equals ("Y")) {
	        calcData.statusCode = "X";

	        int found  = calcData.formula.toLowerCase().indexOf("days");		        
		    if (found >= 0)
		    	calcData.formulaDataCode = "DT";
		        found  = calcData.formula.toLowerCase().indexOf("time");		        
		    if (found >= 0) 
				calcData.formulaDataCode = "TM";
			    found  = calcData.formula.toLowerCase().indexOf("number");		        
		    if (found >= 0)
		        calcData.formulaDataCode = "NU";
	    }	  
			
	    else {		    

		    // Calcuate the date duration in number of days.
					     
		    int found  = calcData.formula.toLowerCase().indexOf("days");		        
		    if (found >= 0) {
				
                java.sql.Date tranDate = rs.getDate(dataField1);
				long date = tranDate.getTime() - lastDate.getTime();  
				     days = (int) date/(24*60*60*1000);
				calcData.dataNumeric     = new BigDecimal(days);
				calcData.formulaDataCode = "DT";
				 
		    }
		     
		        
		     // Calcuate the time duration in both days and time (hh:mm:ss).
		     
		        found  = calcData.formula.toLowerCase().indexOf("time");		        
			if (found >= 0) {
				     
		        java.sql.Date tranDate = rs.getDate(dataField1);	// Date from transaction
	        	java.sql.Time tranTime = rs.getTime(dataField2);    // Time from transaction
																	
	        	long date = tranDate.getTime() - lastDate.getTime();
	        	long time = tranTime.getTime() - lastTime.getTime();

	        	date = date/1000;									// Date from milliseconds to seconds
	        	time = time/1000;          							// Time from milliseconds to seconds

	        	time = time + date;							 		// Combine date and time seconds

	        	while (time >= oneDay) {
		        	days = days + 1;						     	// Count number of days 
		        	time = time - oneDay;                      		// Subtract 1 days seconds from time
	        	}
				while (time >= oneHour) {
					hours = hours + 1;						 		// Count number of hours
					time = time - oneHour;                     		// Subtract 1 hours seconds from time
				}
				while (time >= oneMinute) {
					minutes = minutes + 1;					 		// Count number of minutes
					time = time - oneMinute;                   		// Subtract 1 minutes seconds from time
				}
				
				seconds = new BigDecimal(time).intValue();	 
				      
				if (hours < 10)
					elapsed = elapsed + "0" + hours + ":";
				else
				   	elapsed = elapsed + hours + ":";
				if (minutes < 10)
					elapsed = elapsed + "0" + minutes + ":";
				else
					elapsed = elapsed + minutes + ":";
				if (seconds < 10)
					elapsed = elapsed + "0" + seconds;
				else
					elapsed = elapsed + seconds;
				if (elapsed.length() == 8)
				  calcData.dataTime    = java.sql.Time.valueOf(elapsed);
				else
				  calcData.dataTime    = java.sql.Time.valueOf("00:00:00");
	        	calcData.dataNumeric = new BigDecimal(days);
	        	calcData.formulaDataCode = "TM";
			}


		    // Calcuate a numeric duration.
		     
		        found  = calcData.formula.toLowerCase().indexOf("number");		        
		    if (found >= 0) {

			    BigDecimal tranNumber    = rs.getBigDecimal(dataField1);	
			    calcData.dataNumeric     = tranNumber.subtract(lastNumber);
			    calcData.formulaDataCode = "NU";
		    }	                		
			               		
	    }
	
	}	 
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData." +
			               "buildDataCalcDuration(Class RS String String Date Time BigDecimal): " + e);
	}
	
			
	return calcData;	
}
/**
 * Build the cell data base on a range of cells.
 *
 * Creation date: (11/05/2003 8:24:29 AM)
 */
public static FormData buildDataCalcRange(FormData calcData, ResultSet rs) {                                   
				                                     
	try {		 
			
		calcData.dataNumeric = new BigDecimal(0);
		BigDecimal columns   = new BigDecimal(0);
		BigDecimal oneColumn = new BigDecimal(1);	  	     
	    	    	     
		Vector definitionList = FormDefinition.findDefinitionByFormByRange(calcData.formNumber,
																		   calcData.formulaNumber);
		
		if (definitionList.size() > 0) {   	// Vector must have at least one element.
	    	
	    		    	
			// Summarize each column between the first and last columns defined for the range.
			
			try {
				
				for (int x = 0; x < definitionList.size(); x++) {
							
					FormDefinition definition = (FormDefinition) definitionList.elementAt(x);	        
					String fieldName = buildDataFieldName(definition.columnNumber, 
														  definition.dataCode, definition.dataNumber);
					int orderByView  = calcData.listOrderView.intValue(); 
					if (((calcData.formulaInclusion.toUpperCase().equals ("A"))) ||
						((calcData.formulaInclusion.toUpperCase().equals ("V"))  && (orderByView > 0))) {
						 String defaultedField = buildDataFieldDefault(definition.columnNumber, 
												 definition.dataCode, definition.dataNumber);	  		             
						 if (!rs.getString(defaultedField).toUpperCase().equals("Y")) {
							 columns = columns.add(oneColumn);
							 calcData.dataNumeric = calcData.dataNumeric.add(rs.getBigDecimal(fieldName));
						 }
					}
				
				 }
						
			}
			catch (Exception e) {
				System.out.println("Exception error processing a vector (FormData.buildDataCalcRange): " + e);
			}
		         

			// Average the summary total and consider and rounding requirements.
		     
			try {
			
				int average  = calcData.formula.toLowerCase().indexOf("average");		        
				if (average >= 0) {
							  		   		     
					int divisor = columns.intValue();			         		
						         
					if (divisor == 0)   // Prevent divide by zero.
						calcData.dataNumeric = new BigDecimal(0);
					else {
				
						int decimal   = calcData.decimalPositions.intValue(); 
						int roundUp   = calcData.formula.toLowerCase().indexOf("/up");
						int roundDown = calcData.formula.toLowerCase().indexOf("/down");
				         
						if (roundUp >= 0) 
							calcData.dataNumeric = calcData.dataNumeric.divide(columns, decimal,
																			   BigDecimal.ROUND_HALF_UP);	               
						else {		                 
							if (roundDown >= 0) 
								calcData.dataNumeric = calcData.dataNumeric.divide(columns, decimal,
																				   BigDecimal.ROUND_DOWN);		                     
							else 
								calcData.dataNumeric = calcData.dataNumeric.divide(columns, noRounding,
																				   BigDecimal.ROUND_DOWN);	
						}
					}				
				}
		     	
			}     
	     	     
			catch (Exception e) {
				System.out.println("Exception error computing an average (FormData.buildDataCalcRange): " + e);
			}
			    	     	 
		 }
	     
	}	 
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData.buildDataCalcRange(Class RS): " + e);
	}	
			
	return calcData;	
}
/**
 * Build the data defaulted records from the transaction data (conversion).
 *
 * Creation date: (11/17/2003 8:24:29 AM)
 */
public static int buildDataDefaulted() {


	int records = 0;
	FormData info = new FormData();
	

	String[]        defaultedNU;
	String[]        defaultedDT;
	String[]        defaultedTM;
	String[]        defaultedTX;
	
	defaultedNU = new String[binNumeric];
	defaultedDT = new String[binDate];
	defaultedTM = new String[binTime];
	defaultedTX = new String[binText];

	String dataBin = "";		
	Connection conn = null;
	try {

		String sqlStatement = "SELECT * FROM " + library + "FMPCDATA";   
		// Change to Stack 9/19/08 - TWalton, point to New Box
		conn = ConnectionStack.getConnection();    

		Statement stmt = conn.createStatement();    
		ResultSet rs   = stmt.executeQuery(sqlStatement);                
         
		while (rs.next()) {	       		        
		
			Integer formNumber = new Integer(rs.getInt("FMCFRMNBR"));
			Integer tranNumber = new Integer(rs.getInt("FMCTRNNBR")); 
					records    = records + 1;
	      
			for (int i = 0; i < defaultedNU.length; i++) {
				defaultedNU[i] = "N";
			}

			for (int i = 0; i < defaultedDT.length; i++) {
				int x = i + 1;
		        		    		       	    
				if (x < 10)
					dataBin = "0" + x;
				else
					dataBin = String.valueOf(x);
		        
				String dataField   = "FMCDT" + dataBin;
				java.sql.Date date = rs.getDate(dataField);
				String defDate     = defaultDate.toString();
				String dtaDate     = date.toString();
		       
				if (dtaDate.equals (defDate))
					defaultedDT[i] = "Y";
				else
					defaultedDT[i] = "N";
			}

			for (int i = 0; i < defaultedTM.length; i++) {
				int x = i + 1;
		        		    		       	    
				if (x < 10)
					dataBin = "0" + x;
				else
					dataBin = String.valueOf(x);
		        
				String dataField   = "FMCTM" + dataBin;
				java.sql.Time time = rs.getTime(dataField);
				String defTime     = defaultTime.toString();
				String dtaTime     = time.toString();
		       
				if (dtaTime.equals (defTime))
					defaultedTM[i] = "Y";
				else
					defaultedTM[i] = "N";
			}

			for (int i = 0; i < defaultedTX.length; i++) {
				defaultedTX[i] = "N";
			}


			 PreparedStatement insertDefaulted = (PreparedStatement) sqlInsertDefaulted.pop();
		        
		      
				insertDefaulted.setString(1, "DD");		        
				insertDefaulted.setInt(2, formNumber.intValue());
				insertDefaulted.setInt(3, tranNumber.intValue());
		    
				int index = 3;
		    
				for(int i = 0; i < binNumeric; i++) {
					index = index + 1;
					insertDefaulted.setString(index, defaultedNU[i]);
				}

				for(int i = 0; i < binDate; i++) {
					index = index + 1;
					insertDefaulted.setString(index, defaultedDT[i]);
				}

				for(int i = 0; i < binTime; i++) {
					index = index + 1;
					insertDefaulted.setString(index, defaultedTM[i]);
				}

				for(int i = 0; i < binText; i++) {
					index = index + 1;
					insertDefaulted.setString(index, defaultedTX[i]);
				}	
		
		        		
				insertDefaulted.executeUpdate();

				sqlInsertDefaulted.push(insertDefaulted);	  	   
         
		}            	
		
	}	 
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData." +
						   "buildDataDefaulted(): " + e);
	}
	finally
	{ // Added Finally to Return Connection - 9/19/08 TWalton
		try
		{
		   ConnectionStack.returnConnection(conn);
		}
		catch(Exception e)
		{}
	}
	
			
	return records; 
}
/** 
 * Drop down list of transaction numbers with an associated description.
 *
 * Creation date: (9/21/2004 8:24:29 AM)
 */
public static String buildDataDropDownJoin(Integer inJoinForm, Integer inJoinColumn,
										   String inName, String inData, String inSelect) {

	String dropDownList = "";
	String selected     = "";
	String selectOption = "";
			
	FormDefinition definition   = new FormDefinition();	
	Connection conn = null;
		
	try {
		
		Vector definitionData = FormDefinition.findDefinitionByFormByColumn(inJoinForm, inJoinColumn);
		if (definitionData.size() == 1) {
			FormDefinition definitionInfo = (FormDefinition) definitionData.elementAt(0);
			String fieldName = buildDataFieldName(definitionInfo.columnNumber, definitionInfo.dataCode,
												  definitionInfo.dataNumber);			                                      
			                                      			
			// Initial SQL statement for selecting the security transaction.    
  
			String sqlStatement = "SELECT FMCRECORD, FMCTRNNBR, " + fieldName + " " +
							"FROM " + library + "FMPCDATA " +	   
							"WHERE FMCFRMNBR = " + definitionInfo.formNumber + " " +
							"ORDER BY " + fieldName; 
			

			// Execute the SQL statement using a connection pool.	
		// 9/19/08 TWalton - change to use NEW Box - Connection Stack
			conn = ConnectionStack.getConnection();

			Statement stmt = conn.createStatement();    
			ResultSet rs   = stmt.executeQuery(sqlStatement);                
         
			// 9/19/08 TWalton - Return in the Finally Statement
				
			// Process the result set to create a drop down list of transactions for a description.
				
			try {
				
				if (inName == null || inName.trim().equals(""))
					inName = "input" + definitionInfo.columnNumber;
				
				if (inSelect == null || inSelect.equals(""))
					selectOption = "Select an Entry--->:";
				else {
					 if (inSelect.trim().equals("*all"))
						 selectOption = "*all";
					 else {
						if (inSelect.trim().equals("*ALL"))
							selectOption = "*ALL";
						else
							selectOption = inSelect.trim();
					 }
				}
						        
				while (rs.next())
				{	
					String recordCode = rs.getString("FMCRECORD");					
					if (recordCode != null && recordCode.equals("DA")) {
					
						String code = new Integer(rs.getInt("FMCTRNNBR")).toString();	
						String desc = decodeDescription(fieldName, definitionInfo.dataCode, rs); 
					
						if (inData.trim().equals(code.trim()))
							selected = "' selected='selected'>";
						else
							selected = "'>";	 
	   		   
						dropDownList = dropDownList + "<option value='" + 
						code.trim() + selected +
						code.trim() + " - " + desc.trim() + "&nbsp;";
					}
				}
	   		
				if (!dropDownList.equals(""))   		    	   		    
					dropDownList = "<select name='" + inName + "'>" +
								   "<option value='None' selected>" + selectOption +
								   dropDownList + "</select>";					
									
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set (FormData.buildDataDropDownJoin): " + e);
			}        

			stmt.close();
			rs.close();		
		}		

	}	
	catch (Exception e) {
		System.out.println("Exception error processing security at com.treetop.data." + 
						   "FormData.buildDataDropDownJoin(Integer Integer String String String): " + e);
	}
	finally
	{
		// 9/19/08 - TWalton -- Return the Connection to the STACK
		try
		{
			ConnectionStack.returnConnection(conn);
		}
		catch(Exception e)
		{}
	}
	
	return dropDownList;
}
/** 
 * Drop down list of transactions with a list of grouped descriptions.
 *
 * Creation date: (9/21/2004 8:24:29 AM)
 */
public static String buildDataDropDownList(Integer inFormNumber, Integer inColumnNumber,
										   String inName, String inData, String inSelect) {

	String dropDownList = "";
	String selected     = "";
	String selectOption = "";
			
	FormDefinition definition   = new FormDefinition();	
	Connection conn = null;	
	try {
		
		Vector definitionData = FormDefinition.findDefinitionByFormBySearch(inFormNumber);
		if (definitionData.size() > 0) {
			FormDefinition definitionInfo = (FormDefinition) definitionData.elementAt(0);
			String fieldName = buildDataFieldName(definitionInfo.columnNumber, definitionInfo.dataCode,
												  definitionInfo.dataNumber);			                                      
			                                      			
			// Initial SQL statement for selecting the security transaction.    
  
			String sqlStatement = "SELECT * " +
							"FROM " + library + "FMPCDATA " +	   
							"WHERE FMCFRMNBR = " + inFormNumber + " " +
							"ORDER BY " + fieldName; 
			

			// Execute the SQL statement using a connection pool.	
		// 9/19/08 TWalton -- Change to the ConnectionStack
			conn = ConnectionStack.getConnection();

			Statement stmt = conn.createStatement();    
			ResultSet rs   = stmt.executeQuery(sqlStatement);                
         // 9/19/08 return the Connection in the Finally Statement
								
			// Process the result set to create a drop down list of transactions for a description.
				
			try {
				
				if (inName == null || inName.trim().equals(""))
					inName = "input" + inColumnNumber;
				
				if (inSelect == null || inSelect.equals(""))
					selectOption = "Select an Entry--->:";
				else {
					 if (inSelect.trim().equals("*all"))
						 selectOption = "*all";
					 else {
						if (inSelect.trim().equals("*ALL"))
							selectOption = "*ALL";
						else
							selectOption = inSelect.trim();
					 }
				}
						        
				while (rs.next())
				{	
					String recordCode = rs.getString("FMCRECORD");					
					if (recordCode != null && recordCode.equals("DA")) {
												
						String code = new Integer(rs.getInt("FMCTRNNBR")).toString();
						String desc = "";
						String separator = "";
						
						for (int x = 0; x < definitionData.size(); x++) {
							definitionInfo = (FormDefinition) definitionData.elementAt(x);
							fieldName = buildDataFieldName(definitionInfo.columnNumber, definitionInfo.dataCode,
														   definitionInfo.dataNumber);
							if (x == (definitionData.size() -1))			// Last element
								separator = "";
							else
						    	separator = buildDataFieldSeparator(definitionInfo.listSeparatorValue,
						                                            definitionInfo.listSeparatorLength);                                  							
							desc = desc + decodeDescription(fieldName, definitionInfo.dataCode, rs) + separator;
						} 
					
						if (inData.trim().equals(code.trim()))
							selected = "' selected='selected'>";
						else
							selected = "'>";	 
	   		   
						dropDownList = dropDownList + "<option value='" + 
						code.trim() + selected +
						code.trim() + " - " + desc.trim() + "&nbsp;";
					}
				}
	   		
				if (!dropDownList.equals(""))   		    	   		    
					dropDownList = "<select name='" + inName + "'>" +
								   "<option value='None' selected>" + selectOption +
								   dropDownList + "</select>";					
									
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set (FormData.buildDataDropDownList): " + e);
			}        

			stmt.close();
			rs.close();		
		}		

	}	
	catch (Exception e) {
		System.out.println("Exception error processing security at com.treetop.data." + 
						   "FormData.buildDataDropDownList(Integer Integer String String String): " + e);
	}
	finally
	{
		// 9/19/08 TWalton - Returned to the Connection Stack
		try
		{
			ConnectionStack.returnConnection(conn);
		}
		catch(Exception e)
		{}
	}
	
	return dropDownList;
}
/** 
 * Drop down list of all possible values for a given cell.
 *
 * Creation date: (9/30/2004 8:24:29 AM)
 */
public static String buildDataDropDownValue(Integer inFormNumber, Integer inColumnNumber,
											String inName, String inData, String inSelect) {

	String dropDownList = "";
	String selected     = "";
	String selectOption = "";
			
	FormDefinition definition   = new FormDefinition();	
	Connection conn = null;	
	try {
		
		Vector definitionData = FormDefinition.findDefinitionByFormByColumn(inFormNumber, 
																			inColumnNumber);
		if (definitionData.size() > 0) {
			FormDefinition definitionInfo = (FormDefinition) definitionData.elementAt(0);
			String fieldName = buildDataFieldName(definitionInfo.columnNumber, definitionInfo.dataCode,
												  definitionInfo.dataNumber);
			String fieldDefault = buildDataFieldDefault(definitionInfo.columnNumber, definitionInfo.dataCode,
														definitionInfo.dataNumber);			                                      
			                                      			
			// Initial SQL statement for selecting the security transaction.    
  
			String sqlStatement = "SELECT FMCRECORD, " + fieldDefault + ", " + fieldName + " " +
							"FROM " + library + "FMPCDATA " +
							"JOIN "	+ library + "FMPHDEFT " +
							"ON FMCTRNNBR = FMHTRNNBR AND " + fieldDefault + " = 'N' " +
							"WHERE FMCFRMNBR = " + inFormNumber + " " +
							"GROUP BY FMCRECORD, " + fieldDefault + ", " + fieldName + " " +
							"ORDER BY " + fieldName; 
			

			// Execute the SQL statement using a connection pool.	
		
//			 9/22/08 TWalton -- Change to the ConnectionStack
			conn = ConnectionStack.getConnection();

			Statement stmt = conn.createStatement();    
			ResultSet rs   = stmt.executeQuery(sqlStatement);                
         // 9/22/08 return the Connection in the Finally Statement
			
			// Process the result set to create a drop down list of transactions for a description.
				
			try {
				
				if (inName == null || inName.trim().equals(""))
					inName = "input" + inColumnNumber;
				
				if (inSelect == null || inSelect.equals(""))
					selectOption = "Select an Entry--->:";
				else {
					 if (inSelect.trim().equals("*all"))
						 selectOption = "*all";
					 else {
						if (inSelect.trim().equals("*ALL"))
							selectOption = "*ALL";
						else
							selectOption = inSelect.trim();
					 }
				}
						        
				while (rs.next())
				{	
					String recordCode = rs.getString("FMCRECORD");					
					if (recordCode != null && recordCode.equals("DA")) {
						String desc = decodeDescription(fieldName, definitionInfo.dataCode, rs);				                              							
												 					
						if (inData.trim().equals(desc.trim()))
							selected = "' selected='selected'>";
						else
							selected = "'>";	 
	   		   
						dropDownList = dropDownList + "<option value='" + 
						desc.trim() + selected +
						desc.trim() + "&nbsp;";
					}
				}
	   		
				if (!dropDownList.equals(""))   		    	   		    
					dropDownList = "<select name='" + inName + "'>" +
								   "<option value='None' selected>" + selectOption +
								   dropDownList + "</select>";					
									
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set (FormData.buildDataDropDownValue): " + e);
			}        

			stmt.close();
			rs.close();		
		}		

	}	
	catch (Exception e) {
		System.out.println("Exception error processing security at com.treetop.data." + 
						   "FormData.buildDataDropDownValue(Integer Integer String String String): " + e);
	}
	finally
	{
		// 9/22/08 TWalton - Returned to the Connection Stack
		try
		{
			ConnectionStack.returnConnection(conn);
		}
		catch(Exception e)
		{}
	}
	
	return dropDownList;
}
/**
 * Build the field name, from the column definition, that contains the data value.
 *
 * Creation date: (10/17/2003 8:24:29 AM)
 */
public static String buildDataFieldDefault(Integer inColumnNumber, String inDataCode, Integer inDataNumber) {


	String fieldDefault = "";
	
	try {

		int operand = inColumnNumber.intValue();
		if (operand > 0) {

			String dataBin = "";
		
			int binNumber = inDataNumber.intValue();
		
		
			if (binNumber < 10)
				dataBin = "0" + binNumber;
			else
				dataBin = String.valueOf(binNumber);		    

		
			fieldDefault = "FMH" + inDataCode + dataBin;
		}

		else
			fieldDefault = "FMHTRNDTE";
				
	}	 
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData." +
						   "buildDataFieldDefault(Integer String Integer): " + e);
	}	
			
	return fieldDefault; 
}
/**
 * Build the field name, from the column definition, that contains the data value.
 *
 * Creation date: (10/17/2003 8:24:29 AM)
 */
public static String buildDataFieldName(Integer inColumnNumber, String inDataCode, Integer inDataNumber) {


	String fieldName = "FMCTRNDTE";										// Default field
	
	try {

		int operand = inColumnNumber.intValue();
		if (operand > 0) {												// Normal data column

		    String dataBin = "";		
		    int binNumber = inDataNumber.intValue();		
		
		    if (binNumber < 10)
		        dataBin = "0" + binNumber;
		    else
		        dataBin = String.valueOf(binNumber);
		
		    fieldName = "FMC" + inDataCode + dataBin;
		}
		
		else {															// Basic column
			if((inDataNumber != null) && (inDataNumber.intValue() > 0)) 
				fieldName = buildDataFieldNameBasic(inDataNumber);					
		}
				
	}	 
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData." +
			               "buildDataFieldName(Integer String Integer): " + e);
	}	
			
	return fieldName; 
}
/**
 * Build the field name, from the basic column definition index value.
 *
 * Creation date: (10/07/2004 8:24:29 AM)
 */
public static String buildDataFieldNameBasic(Integer inDataIndex) {


	String fieldName = "";
	
	try {

		if((inDataIndex != null) && (inDataIndex.intValue() > 0)) {
			
			if (inDataIndex.intValue() == 1)
				fieldName = "FMCTRNNBR";
			if (inDataIndex.intValue() == 2)
				fieldName = "FMCTRNDTE";
			if (inDataIndex.intValue() == 3)
				fieldName = "FMCUSER";
			if (inDataIndex.intValue() == 4)
				fieldName = "FMCDATE";
			if (inDataIndex.intValue() == 5)
				fieldName = "FMCTIME";
		}
		else
			fieldName = "FMCTRNDTE";				
						
	}	 
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData." +
						   "buildDataFieldNameBasic(Integer): " + e);
	}	
			
	return fieldName; 
}
/**
 * Build the spacing value that separates cells.
 *
 * Creation date: (10/27/2004 8:24:29 AM)
 */
public static String buildDataFieldSeparator(String inSeparator, Integer inSeparatorLength) {


	String separator = "";
	
	try {

		if (inSeparatorLength.intValue() > 0) {	
			
			if (inSeparator.length() < inSeparatorLength.intValue()) {
				for (int x = inSeparator.length(); x < inSeparatorLength.intValue(); x++) {
					inSeparator = inSeparator + " ";
				}
			}			
			
			for (int x = 0; x < inSeparatorLength.intValue(); x++) {				
				if (inSeparator.substring(x,x+1).equals (" "))
					separator = separator + "&nbsp";
				else
					separator = separator + inSeparator.substring(x,x+1);
			}
			
		}
		else
			separator = " ";

	}	 
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData." +
						   "buildDataFieldSeparator(String Integer): " + e);
	}	
			
	return separator; 
}
/**
 * Process the forumla defined for the data cell.
 *
 * Creation date: 10/4/2004 1:48:29 PM)
 */
public static FormData buildDataFormula(FormData dataCell, String formula, ResultSet rs) {
	
	try {
	    	
		// Calculate a value based on a range of columns.
		
		int range  = formula.toLowerCase().indexOf("range");		        
		if (range >= 0)			                     
			dataCell = buildDataCalcRange(dataCell, rs);
			dataCell.dataDefaulted = "N";      	
	}			
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData." +
						   "buildDataFormula(Class String ResultSet): " + e);
	}	
			
	return dataCell;	
}
/**
 * Built a vector of joining columns for the main (view) form.
 *
 * Creation date: (9/09/2004 8:24:29 AM)
 */
public static Vector buildDataJoinColumn(Vector mainColumn) {
	
	Vector joinColumn = new Vector();
	Vector linkColumn = new Vector();
	Vector joinReview = new Vector();
	String newJoin    = "";	                          
				                                     
	try {
		
		for (int x = 0; x < mainColumn.size(); x++) {
			FormDefinition columnCell = (FormDefinition) mainColumn.elementAt(x);
						
			if (columnCell.joinFormNumber.intValue() > 0) {				
				if (columnCell.buildClass.toUpperCase().equals ("Y")) {					
					joinColumn.addElement(columnCell);
					newJoin = columnCell.formNumber.toString() + columnCell.joinFormNumber.toString();
					joinReview.addElement(newJoin);
					
					linkColumn = findDefinitionByFormByJoin(columnCell.joinFormNumber);
					if (linkColumn.size() > 0) {
						for (int j = 0; j < linkColumn.size(); j++) {
							FormDefinition joinedCell = (FormDefinition) linkColumn.elementAt(j);
							
							boolean addJoin = true;
							        newJoin = joinedCell.formNumber.toString() + joinedCell.joinFormNumber.toString();
							        							
							for (int r = 0; r < joinReview.size(); r++) {
								String oldJoin = (String) joinReview.elementAt(r);
								if (newJoin.equals (oldJoin)) {
									addJoin = false;
									break;
								}
							}
							if (addJoin)
								mainColumn.addElement(joinedCell);							
						}
					}
				}
			}		
		}			

	}	 
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData.buildDataJoinColumn(Vector): " + e);
	}	
			
	return joinColumn;	
}
/**
 * Build a all information relating to joined cells.
 *
 * Creation date: (10/06/2004 8:24:29 AM)
 */
public static FormData buildDataJoinInfo(FormData dataCell, ResultSet rs) {

	String useFormula = "y";
		
	try {                  
				                                     
		int join = dataCell.dataType.toLowerCase().indexOf("transaction");
		if (join >= 0) {
			
			String fieldName = buildDataFieldName(dataCell.columnNumber, dataCell.dataCode,
										          dataCell.dataNumber);
			Integer transactionNumber = new Integer(rs.getBigDecimal(fieldName).intValue());					
			dataCell.dataDescription = findDataByTranByDesc(dataCell.joinFormNumber, 
			                                                transactionNumber);
														 			
			if (dataCell.buildClass.toLowerCase().equals ("y")) {									   			                     
				dataCell.dataClass = findDataByTranByView(dataCell.joinFormNumber,
													       transactionNumber, useFormula);
				dataCell.dataClassName.addElement("FormData");
			}
		}								                                          
	
	}
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData." + 
                           "buildDataJoinInfo(Class ResultSet): " + e);
	}	
			
	return dataCell;	
}
/**
 * Decode the stored customer number into two separate components (company & number).
 *
 * Creation date: (8/24/2004 1:48:29 PM)
 */
public static String[] decodeCustomer(String inDataText) {

	String[] customerData;
	         customerData   = new String[2];  
	String   companyNumber  = "";
	String   customerNumber = "";
					
	try {
	    	
		int n = inDataText.indexOf("_");	
			        
		companyNumber   = inDataText.substring(0,n);		
		customerNumber  = inDataText.substring(n+1,inDataText.length());
	// Customer will need to be different for MOVEX	
//		customerData[0] = CustomerBillTo.buildCompanyNumber(companyNumber);
//		customerData[1] = CustomerBillTo.buildCustomerNumber(customerNumber);		
		
	}			
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData." +
						   "decodeCustomerNumber(String): " + e);
	}	
			
	return customerData;	
}
/** 
 * Decode the various data values into a description.
 *
 * Creation date: (9/27/2004 8:24:29 AM)
 */
public static String decodeDescription(FormData inTransactionData) {

	String description = "";
	String dataCode    = inTransactionData.dataCode;

	try {		
		
		if (dataCode.toUpperCase().equals ("DT")) {
			String[] dates = GetDate.getDates(inTransactionData.dataDate);
			description    = dates[9].trim(); 	 
		}
		if (dataCode.toUpperCase().equals ("NU"))
			description = inTransactionData.dataNumeric.toString().trim();
		if (dataCode.toUpperCase().equals ("TM")) 
			description = inTransactionData.dataTime.toString().trim();
		if (dataCode.toUpperCase().equals ("TX"))
			description = inTransactionData.dataText.trim();

	}	
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData." +
						   "decodeDescription(Class): " + e);
	}
	
	return description;
}
/** 
 * Decode the various data values into a description.
 *
 * Creation date: (9/27/2004 8:24:29 AM)
 */
public static String decodeDescription(String inFieldName, String inDataCode, ResultSet rs) {

	String description = "";

	try {		
		
		if (inDataCode.toUpperCase().equals ("DT")) {
			String[] dates = GetDate.getDates(rs.getDate(inFieldName));
			description    = dates[9].trim(); 	 
		}
		if (inDataCode.toUpperCase().equals ("NU"))
			description = rs.getBigDecimal(inFieldName).toString().trim();
		if (inDataCode.toUpperCase().equals ("TM")) 
			description = rs.getTime(inFieldName).toString().trim();
		if (inDataCode.toUpperCase().equals ("TX"))
			description = rs.getString(inFieldName).trim();

	}	
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData." +
						   "decodeDescription(String String ResultSet): " + e);
	}
	
	return description;
}
/**
 * Set vector of form data by the form number and order by data entry sequence.
 *
 * Creation date: (10/07/2003 8:24:29 AM)
 */
public static Vector findDataByFormByEntry(Integer inFormNumber) {

	Vector   dataList  = new Vector();
	FormData data      = new FormData();
	String	 loadMode  = "standard";
	String   joinClass = "n";
	
	try {
		
		PreparedStatement dataByFormByEntry = (PreparedStatement) FormData.findDataByFormByEntry.pop();
		ResultSet rs = null;
		
		try {
			dataByFormByEntry.setInt(1, inFormNumber.intValue());				
			rs = dataByFormByEntry.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormData.findDataByFormByEntry): " + e);
		}
		
		FormData.findDataByFormByEntry.push(dataByFormByEntry);

		try {
			
			while (rs.next())
			{
				String recordCode = rs.getString("FMCRECORD");					
				if (recordCode != null && recordCode.equals("DA")) {					
		      	    FormData buildCell = new FormData(rs, loadMode, joinClass);
				    dataList.addElement(buildCell);
				}
			}			
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
				               "(FormData.findDataByFormByEntry): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormData." +
			               "findDataByFormByEntry(Integer): " + e);
	}	
			
	return dataList; 
}
/**
 * Set vector of form data by the form number and order by report view.
 *
 * Creation date: (10/07/2003 8:24:29 AM)
 */
public static Vector findDataByFormByView(Integer inFormNumber) {

	Vector   dataList  = new Vector();
	FormData data      = new FormData();
	String	 loadMode  = "standard";
	String   joinClass = "n";
	
	try {
		
		PreparedStatement dataByFormByView = (PreparedStatement) FormData.findDataByFormByView.pop();
		ResultSet rs = null;
		
		try {
			
			dataByFormByView.setInt(1, inFormNumber.intValue());				
			rs = dataByFormByView.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormData.findDataByFormByView): " + e);
		}
		
		FormData.findDataByFormByView.push(dataByFormByView);

		try {
			
			while (rs.next())
			{
				String recordCode = rs.getString("FMCRECORD");					
				if (recordCode != null && recordCode.equals("DA")) {					
					FormData buildCell = new FormData(rs, loadMode, joinClass);
					dataList.addElement(buildCell);
				}
			}
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "(FormData.findDataByFormByView): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormData." +
						   "findDataByFormByView(Integer): " + e);
	}	
			
	return dataList; 
}
/** 
 * Security processing by form (application).
 * Find the logon user and password in the form data to validate a signon attempt.
 *
 * Creation date: (8/25/2004 8:24:29 AM)
 */
public static String[] findDataByLogon(Integer inFormNumber, String inLogonUser, String inLogonPassword) {

	FormDefinition definition    = new FormDefinition();
	String         fieldUser     = "";
	String         fieldPassword = "";
	String[]       returnMessage;
				   returnMessage = new String[2];
	Connection conn = null;
	
	returnMessage[0] = "";
	returnMessage[1] = "no";
	
	try {
		
		Vector definitionData = FormDefinition.findDefinitionByFormByForm(inFormNumber);
		if (definitionData.size() > 1) {
			returnMessage[0] = " The logon information can not be processed. \n";
			
		        	
			// Initial SQL statement for selecting the security transaction.    
  
			String sqlStatement = "SELECT * " + 
							"FROM " + library + "FMPCDATA " +	   
							"WHERE ";   
					        
	
			// Add the form number to the "WHERE" clause.

			sqlStatement = sqlStatement + "FMCFRMNBR = " + inFormNumber;
			 
		
			// Add the logon field variables to the "WHERE" clause.		
			
			String user     = "n";
			String password = "n";
			
			for (int x = 0; x < definitionData.size(); x++) {
				FormDefinition definitionInfo = (FormDefinition) definitionData.elementAt(x);
				
				if ((definitionInfo.dataType.trim().toLowerCase().equals ("logonuser")) && (user.equals ("n"))) {
					fieldUser = buildDataFieldName(definitionInfo.columnNumber, definitionInfo.dataCode,
												   definitionInfo.dataNumber);			
					sqlStatement = sqlStatement + " AND (UPPER(" + fieldUser + ") = '" + 
												  inLogonUser.toUpperCase() + "')";				
					user = "y";
				}
				if ((definitionInfo.dataType.trim().toLowerCase().equals ("logonpassword")) && (password.equals ("n"))) {
					fieldPassword = buildDataFieldName(definitionInfo.columnNumber, definitionInfo.dataCode,
													   definitionInfo.dataNumber);			
					sqlStatement = sqlStatement + " AND (UPPER(" + fieldPassword + ") = '" + 
												  inLogonPassword.toUpperCase() + "')";				
					password = "y";
				}
				
			}
			

			// Execute the SQL statement using a connection pool.
			
			if ((user.equals ("y")) && (password.equals ("y"))) { 
		
//				 9/22/08 TWalton -- Change to the ConnectionStack
				conn = ConnectionStack.getConnection();

				Statement stmt = conn.createStatement();    
				ResultSet rs   = stmt.executeQuery(sqlStatement);                
	         // 9/22/08 return the Connection in the Finally Statement
				
				// Process the result set to validate the logon variables.
				
				try {
						        
					if (rs.next()) {
						
						String dataUser     = rs.getString(fieldUser).trim();
						String dataPassword = rs.getString(fieldPassword).trim();
						
						if ((dataUser != null) && (!dataUser.equals("")) &&
							(dataPassword != null) && (!dataPassword.equals(""))) {						    
						   	
							if ((dataUser.equals (inLogonUser)) && (dataPassword.equals (inLogonPassword))) {
								returnMessage[0] = "";
								returnMessage[1] = "yes";
							}
							else {						   		
								returnMessage[0] = " The logon information is incomplete. \n";
								returnMessage[1] = "ok";			// Case sensitivity problem
							}	
							}
						   
						else
							returnMessage[0] = " The logon transaction information has not been setup. \n";
					}
					else
						returnMessage[0] = " The logon information is invalid. \n";
					
				}
				catch (Exception e) {
					System.out.println("Exception error processing a result set (FormData.findDataByLogon): " + e);
				}        

				stmt.close();
				rs.close();
			}
			
			else
				returnMessage[0] = " No logon information setup for processing. \n";
			
		}
			
		else
			returnMessage[0] = " No logon information available to process. \n";
		
	}	
	catch (Exception e) {
		System.out.println("Exception error processing security at com.treetop.data." + 
						   "FormData.findDataByLogon(Integer String String): " + e);
	}
	finally
	{
		// 9/22/08 TWalton - Returned to the Connection Stack
		try
		{
			ConnectionStack.returnConnection(conn);
		}
		catch(Exception e)
		{}
	}
	
	return returnMessage;
}
/** 
 * Find the logon user name description.
 *
 * Creation date: (9/27/2004 8:24:29 AM)
 */
public static String findDataByLogonUser(Integer inFormNumber, String inLogonUser) {

	FormDefinition definition = new FormDefinition();
	String		   logonText  = "";	
	String		   logonUser  = "n";
	String		   logonName  = "n";
	String		   fieldUser  = "";
	String		   fieldName  = "";
	Connection conn = null;
	try {
		
		Vector definitionData = FormDefinition.findDefinitionByFormByForm(inFormNumber);
		if (definitionData.size() > 1) {

		        	
			// Initial SQL statement for selecting the security transaction.    
  
			String sqlStatement = "SELECT * " + 
							"FROM " + library + "FMPCDATA " +	   
							"WHERE ";   
					        
	
			// Add the form number to the "WHERE" clause.

			sqlStatement = sqlStatement + "FMCFRMNBR = " + inFormNumber;
			 
		
			// Add the logon user variable to the "WHERE" clause.			

			for (int x = 0; x < definitionData.size(); x++) {
				FormDefinition definitionInfo = (FormDefinition) definitionData.elementAt(x);
				
				if ((definitionInfo.dataType.trim().toLowerCase().equals ("logonuser")) && (logonUser.equals ("n"))) {
					fieldUser = buildDataFieldName(definitionInfo.columnNumber, definitionInfo.dataCode,
												   definitionInfo.dataNumber);			
					sqlStatement = sqlStatement + " AND (UPPER(" + fieldUser + ") = '" + 
												  inLogonUser.toUpperCase() + "')";
					logonUser = "y";			
				}
				if ((definitionInfo.dataType.trim().toLowerCase().equals ("logonname")) && (logonName.equals ("n"))) {
					if (definitionInfo.dataCode.toUpperCase().equals ("TX")) {
						fieldName = buildDataFieldName(definitionInfo.columnNumber, definitionInfo.dataCode,
													   definitionInfo.dataNumber);								
						logonName = "y";
					}			
				}
				
			}
			

			// Execute the SQL statement using a connection pool.
			
			if ((logonUser.equals ("y")) && (logonName.equals ("y"))) { 
		
//				 9/22/08 TWalton -- Change to the ConnectionStack
				conn = ConnectionStack.getConnection();

				Statement stmt = conn.createStatement();    
				ResultSet rs   = stmt.executeQuery(sqlStatement);                
	         // 9/22/08 return the Connection in the Finally Statement
				
				// Process the result set to retrieve the logon name.
				
				try {
						        
					if (rs.next()) {						
						logonText = rs.getString(fieldName).trim();
					}					
					
				}
				catch (Exception e) {
					System.out.println("Exception error processing a result set (FormData.findDataByLogonUser): " + e);
				}        

				stmt.close();
				rs.close();
			}			
			
		}
			

	}	
	catch (Exception e) {
		System.out.println("Exception error processing com.treetop.data." + 
						   "FormData.findDataByLogonUser(Integer String): " + e);
	}
	finally
	{
		// 9/22/08 TWalton - Returned to the Connection Stack
		try
		{
			ConnectionStack.returnConnection(conn);
		}
		catch(Exception e)
		{}
	}
	
	return logonText;
}
/**
 * Set vector to return form data by the requested search values.
 *
 * Each element in the input search vectors are equal to one form column or
 * represent standard (basic) fields in the transaction data record.
 * An SQL process will be performed on the search criteria for each element.
 *
 * The view vector is sequenced in viewing order, which controls the placement of data columns.
 *
 * Creation date: (10/08/2003 8:24:29 AM)
 */
public static Vector findDataByRange(Vector searchBasic, Vector searchColumn, Vector viewColumn) {

	Vector    dataList   = new Vector();  
	Integer   formNumber = new Integer(0);
	String    orderBy    = "";
	String    loadMode   = "standard";
	String	  joinClass	 = "r"; 
	String    useFormula = "y";   
	int       col        = 0;
	int       count      = 0;
       
	Integer[]       infoCalcColumn;
	Integer[]       infoOperand1;
	Integer[]       infoOperand2;
	String[]        infoFormula;
	String[]        infoDataField1;
	String[]        infoDataField2;
	String[]        infoFirstPass;
	java.sql.Date[] infoLastDate;
	java.sql.Time[] infoLastTime;
	BigDecimal[]    infoLastNumber;
        
	String[]        orderByField;  
	String[]        orderByStyle; 
 	
	int size = searchColumn.size() - 1;
	infoCalcColumn    = new Integer[size];
	infoOperand1      = new Integer[size];
	infoOperand2      = new Integer[size];
	infoFormula       = new String[size];
	infoDataField1    = new String[size];
	infoDataField2    = new String[size];
	infoFirstPass     = new String[size];
	infoLastDate      = new java.sql.Date[size];
	infoLastTime      = new java.sql.Time[size];
	infoLastNumber    = new BigDecimal[size];

	int entry = searchBasic.size() + searchColumn.size();
	orderByField      = new String[entry];
	orderByStyle      = new String[entry];
	
	Connection conn = null;

	if (size > 0) {  // at least one column exists.

	try {
		        	
		// Initial SQL statement for selecting each column transaction).    
  
		String sqlStatement = "SELECT * " + 
					   "FROM " + library + "FMPCDATA" +                      
		   " LEFT OUTER JOIN " + library + "FMPDCMNT ON FMCFRMNBR = FMDFRMNBR AND FMCTRNNBR = FMDTRNNBR" +
		   " LEFT OUTER JOIN " + library + "FMPHDEFT ON FMCFRMNBR = FMHFRMNBR AND FMCTRNNBR = FMHTRNNBR";
           
		sqlStatement = sqlClauseJoin(searchColumn, sqlStatement);        
       
		sqlStatement = sqlStatement + " WHERE ";
       	
   
		// Process the basic transaction data vector. The first element is for general
		// information, such as the form number.

		for (int x = 0; x < searchBasic.size(); x++) {					// Start basic columns					

			FormDefinition searchCell = (FormDefinition) searchBasic.elementAt(x);
			
			if (x == 0)  												// General information	           
				formNumber = searchCell.getFormNumber();			
			
			// Subsequent elements are used to create SQL statements for selecting data based
			// on general transaction fields. (basic)
			
			else {														// Basic column information		
									
				if (searchCell.showOnInquiry != null) 					// Where clause selections			
					sqlStatement = sqlStatement + sqlClauseWhereBasic(searchCell, x-1);
						
				if (searchCell.sortOrderSequence !=null) {				// Sort order    			
					int seq = searchCell.sortOrderSequence.intValue();
					if ((seq > 0) && (seq <= entry)) {
						orderByField[seq] = buildDataFieldName(searchCell.columnNumber,
															   searchCell.dataCode, searchCell.dataNumber);
						orderByStyle[seq] = searchCell.sortOrderStyle;
					}
				}				
			}
		}																// End basic columns
		
		// Create SQL statements for each data form column. (non-basic) 

		for (int x = 0; x < searchColumn.size(); x++) {					// Start data columns

			FormDefinition searchCell = (FormDefinition) searchColumn.elementAt(x);								               
	            	           
			// Build the "WHERE" SQL function for each data value for all column elements.

			if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R")))
				 sqlStatement = sqlStatement + sqlClauseWhere(searchCell);
               
               
			// Build arrays to hold sorting order for SQL statement.
				
			if (searchCell.sortOrderSequence !=null) {					// Sort order
	    			
				int seq = searchCell.sortOrderSequence.intValue();
				if ((seq > 0) && (seq <= entry)) {
					orderByField[seq] = buildDataFieldName(searchCell.columnNumber,
														   searchCell.dataCode, searchCell.dataNumber);
					orderByStyle[seq] = searchCell.sortOrderStyle;
				}
			}
			                     

			// Build arrays to hold column information for calculated results.

			if ((searchCell.formula != null) && (!searchCell.formula.trim().equals(""))) {
				infoCalcColumn[col] = searchCell.columnNumber;
				infoOperand1[col]   = searchCell.operand1;
				infoOperand2[col]   = searchCell.operand2;
				infoFormula[col]    = searchCell.formula;    			   
    			    
				int operandNumber = searchCell.operand1.intValue();  	// First operand column
				if (operandNumber > 0) {
					FormDefinition operand = new FormDefinition(formNumber, searchCell.operand1);
					infoDataField1[col]    = buildDataFieldName(operand.columnNumber, 
																operand.dataCode, operand.dataNumber);
				}
				else  {
					String  operandCode = "";
					Integer operandBin  = new Integer(0);
					infoDataField1[col] = buildDataFieldName(searchCell.operand1, operandCode, operandBin);
				}
	                

				operandNumber = searchCell.operand2.intValue();		 	// Second operand column
				if (operandNumber > 0) {
					   FormDefinition operand = new FormDefinition(formNumber, searchCell.operand2);
					   infoDataField2[col]    = buildDataFieldName(operand.columnNumber, 
																   operand.dataCode, operand.dataNumber);
				}
				else  {
					String  operandCode = "";
					Integer operandBin  = new Integer(0);
					infoDataField2[col] = buildDataFieldName(searchCell.operand2, operandCode, operandBin);
				} 			                                                

				infoFirstPass[col]  = "Y";
				infoLastDate[col]   = defaultDate;
				infoLastTime[col]   = defaultTime;
				infoLastNumber[col] = new BigDecimal(0);    
				col = col + 1;
			}            

		}																// End data columns

		// Add the form number to the "WHERE" clause.

		sqlStatement = sqlStatement + "FMCFRMNBR = " + formNumber;
       

		// Add the sort sequence to the "ORDER BY" clause.

		orderBy      = sqlOrderBy(orderByField, orderByStyle);
		sqlStatement = sqlStatement + orderBy;                   
      

		// Execute the SQL statement using a connection pool.
		
//		 9/22/08 TWalton -- Change to the ConnectionStack
		conn = ConnectionStack.getConnection();

		Statement stmt = conn.createStatement();    
		ResultSet rs   = stmt.executeQuery(sqlStatement);                
     // 9/22/08 return the Connection in the Finally Statement

		// Process the SQL result into the return data class vector elements. 
		// Use the view order to sequence the vector elements. 
              
		try {
	        
			while (rs.next()) {

			for (int v = 0; v < viewColumn.size(); v++) {
		        
				FormDefinition viewCell = (FormDefinition) viewColumn.elementAt(v);
				FormData buildCell = new FormData(viewCell, rs, loadMode, joinClass);
                
                // Start of all column calculations.
				if (col > 0) {  
					for(int i = 0; i < col; i++) {
						if (buildCell.columnNumber.intValue() == infoCalcColumn[i].intValue()) {

							// Calculate a duration value.
							int duration  = infoFormula[i].toLowerCase().indexOf("duration");		        
							if (duration >= 0) {	
			                     
								buildCell = buildDataCalcDuration(buildCell, rs, infoFirstPass[i],
																  infoDataField1[i], infoDataField2[i],
																  infoLastDate[i], infoLastTime[i],
																  infoLastNumber[i]);	
	                		     
								if (buildCell.formulaDataCode.equals("DT"))
									infoLastDate[i] = rs.getDate(infoDataField1[i]);
								if (buildCell.formulaDataCode.equals("TM")) {
									infoLastDate[i] = rs.getDate(infoDataField1[i]);
									infoLastTime[i] = rs.getTime(infoDataField2[i]);
								}
								if (buildCell.formulaDataCode.equals("NU")) 
									infoLastNumber[i] = rs.getBigDecimal(infoDataField1[i]);
			                		 
								infoFirstPass[i] = "N";
							}

							// Calculate a value based on a formula.
							buildCell = buildDataFormula(buildCell, infoFormula[i], rs);
		                 	  
						}
					}
				}			// End of column calculations
				
                
				// Build data for joined forms.
				buildCell = buildDataJoinInfo(buildCell, rs);				
               			
				dataList.addElement(buildCell);               
			}
			}
        
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set (FormData.findDataByRange): " + e);
		}        
        
		stmt.close();
		rs.close();

	}
	catch (Exception e) {
		System.out.println("Exception error processing SQL at com.treetop.data." + 
						   "FormData.findDataByRange(Vector Vector Vector): " + e);
	}
	finally
	{
		// 9/22/08 TWalton - Returned to the Connection Stack
		try
		{
			ConnectionStack.returnConnection(conn);
		}
		catch(Exception e)
		{}
	}

	}

	return dataList;     
}
/**
 * Set vector of form data by the transaction number and order by description sequence.
 *
 * Creation date: (10/09/2003 8:24:29 AM)
 */
public static Vector findDataByTranByDesc(Integer inFormNumber, Integer inTranNumber) {

	Vector   dataList  = new Vector();
	FormData data      = new FormData();
	String	 loadMode  = "standard";
	String   joinClass = "y";
	
	try {
		
		PreparedStatement dataByTranByDesc = (PreparedStatement) FormData.findDataByTranByDesc.pop();
		ResultSet rs = null;
		
		try {
			
			dataByTranByDesc.setInt(1, inFormNumber.intValue());
			dataByTranByDesc.setInt(2, inTranNumber.intValue());				
			rs = dataByTranByDesc.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormData.findDataByTranByDesc): " + e);
		}
		
		FormData.findDataByTranByDesc.push(dataByTranByDesc);

		try {
			
			while (rs.next())
			{
				String recordCode = rs.getString("FMCRECORD");					
				if (recordCode != null && recordCode.equals("DA")) {
					FormData buildCell = new FormData(rs, loadMode, joinClass);
					String description = decodeDescription(buildCell);					
					dataList.addElement(description.trim());
				}
			}
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "(FormData.findDataByTranByDesc): " + e);
		}
		
		rs.close();
				
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormData." +
						   "findDataByTranByDesc(Integer Integer): " + e);
	}	
			
	return dataList; 
}
/**
 * Set vector of form data by the transaction number and order by data entry sequence. 
 *
 * Creation date: (10/09/2003 8:24:29 AM)
 */
public static Vector findDataByTranByEntry(Integer inFormNumber, Integer inTranNumber) {

	Vector   dataList  = new Vector();
	FormData data      = new FormData();
	String	 loadMode  = "standard";
	String   joinClass = "y";
	
	try {
		
		PreparedStatement dataByTranByEntry = (PreparedStatement) FormData.findDataByTranByEntry.pop();
		ResultSet rs = null;
		
		try {
			
			dataByTranByEntry.setInt(1, inFormNumber.intValue());
			dataByTranByEntry.setInt(2, inTranNumber.intValue());				
			rs = dataByTranByEntry.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
			    			   "(FormData.findDataByTranByEntry): " + e);
		}
		
		FormData.findDataByTranByEntry.push(dataByTranByEntry);

		try {
			
			while (rs.next())
			{
				String recordCode = rs.getString("FMCRECORD");					
				if (recordCode != null && recordCode.equals("DA")) {					
		      	    FormData buildCell = new FormData(rs, loadMode, joinClass);
				    dataList.addElement(buildCell);
				}
			}
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
				               "(FormData.findDataByTranByEntry): " + e);
		}
		
		rs.close();
				
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormData." +
			               "findDataByTranByEntry(Integer Integer): " + e);
	}	
			
	return dataList; 
}
/**
 * Set vector of form data by the transaction number and order by report view.
 *
 * Creation date: (10/09/2003 8:24:29 AM)
 */
public static Vector findDataByTranByView(Integer inFormNumber, Integer inTranNumber,
                                          String useFormula) {

	Vector   dataList  = new Vector();
	FormData data      = new FormData();
	String	 loadMode  = "standard";
	String   joinClass = "y";
	
	try {
		
		PreparedStatement dataByTranByView = (PreparedStatement) FormData.findDataByTranByView.pop();
		ResultSet rs = null;
		
		try {
			
			dataByTranByView.setInt(1, inFormNumber.intValue());
			dataByTranByView.setInt(2, inTranNumber.intValue());				
			rs = dataByTranByView.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormData.findDataByTranByView): " + e);
		}
		
		FormData.findDataByTranByView.push(dataByTranByView);

		try {
			
			while (rs.next())
			{
				String recordCode = rs.getString("FMCRECORD");					
				if (recordCode != null && recordCode.equals("DA")) {					
					FormData buildCell = new FormData(rs, loadMode, joinClass);
					
					if (useFormula.toLowerCase().equals ("y"))
						buildCell = buildDataFormula(buildCell, buildCell.formula, rs);
						
					dataList.addElement(buildCell);
				}
			}
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
							   "(FormData.findDataByTranByView): " + e);
		}
		
		rs.close();
				
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormData." +
						   "findDataByTranByView(Integer Integer): " + e);
	}	
			
	return dataList; 
}
/**
 * Set vector to return form data by the requested data type value.
 *
 * Creation date: (10/08/2003 8:24:29 AM)
 */
public static Vector findDataByValue(FormData inData, Vector viewColumn) {

	Vector dataList  = new Vector();
	String orderBy   = "";
	String loadMode  = "standard";
	String joinClass = "y";  
	Connection conn = null;

	try {
		
		// Retrieve a vector of cell definitions for the form and data type requested.
		
		Vector definitionList = findDefinitionByFormByType(inData);
		
		if (definitionList.size() > 0) {		
			
		        	
			// Initial SQL statement for selecting each column transaction).    
  
			String sqlStatement = "SELECT * " + 
					       "FROM " + library + "FMPCDATA" +                      
		       " LEFT OUTER JOIN " + library + "FMPDCMNT ON FMCFRMNBR = FMDFRMNBR AND FMCTRNNBR = FMDTRNNBR" +
		       " LEFT OUTER JOIN " + library + "FMPHDEFT ON FMCFRMNBR = FMHFRMNBR AND FMCTRNNBR = FMHTRNNBR";
        		
		 	sqlStatement = sqlStatement + " WHERE ";
		 	
			// Add the form number to the "WHERE" clause.

			sqlStatement = sqlStatement + "FMCFRMNBR = " + inData.formNumber;
			
			// Select the transactions by the column value.
			
			for (int x = 0; x < definitionList.size(); x++) {				
				FormDefinition columnCell = (FormDefinition) definitionList.elementAt(x);				
				String sqlselect = sqlClauseWhereValue(columnCell, inData);
				sqlStatement = sqlStatement + sqlselect + " ";
				break;						
			}
			
			// Add the sort sequence to the "ORDER BY" clause.

			orderBy      = sqlOrderBy(inData);
			sqlStatement = sqlStatement + orderBy;                   
			
			// Execute the SQL statement using a connection pool.
		
//			 9/22/08 TWalton -- Change to the ConnectionStack
			conn = ConnectionStack.getConnection();

			Statement stmt = conn.createStatement();    
			ResultSet rs   = stmt.executeQuery(sqlStatement);                
         // 9/22/08 return the Connection in the Finally Statement

			// Process the SQL result into the return data class vector elements. 
			// Use the view order to sequence the vector elements. 
              
			try {
	        
				while (rs.next()) {

					for (int v = 0; v < viewColumn.size(); v++) {		        
						FormDefinition viewCell = (FormDefinition) viewColumn.elementAt(v);
						FormData buildCell = new FormData(viewCell, rs, loadMode, joinClass);
						
						// Calculate a value based on a formula.
						buildCell = buildDataFormula(buildCell, buildCell.formula, rs);
						
						// Build data for joined forms.
						buildCell = buildDataJoinInfo(buildCell, rs);		
						
						dataList.addElement(buildCell); 
					}
				}
				
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set (FormData.findDataByValue): " + e);
			}        
        
			stmt.close();
			rs.close();          		
		
		}
		
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.findDataByValue(Class): " + e);
	}	
	finally
	{
		// 9/22/08 TWalton - Returned to the Connection Stack
		try
		{
			ConnectionStack.returnConnection(conn);
		}
		catch(Exception e)
		{}
	}
	
	return dataList; 	
}   
/**
 * Retrieve the next available transaction number.
 *
 * Creation date: (9/24/2003 4:36:39 PM)
 */
public static int nextTranNumber() {
	
	AS400 as400 = null;
	
	try {
		//create a AS400 object	
		as400 = ConnectionStack.getAS400Object();
		//AS400       as400 = new AS400("10.6.100.1", "DAUSER", "web230502");
		ProgramCall pgm   = new ProgramCall(as400);
	
		ProgramParameter[] parmList = new ProgramParameter[1];
		parmList[0] = new ProgramParameter(100);
				pgm = new ProgramCall(as400, "/QSYS.LIB/MOVEX.LIB/GNCFORMTRN.PGM", parmList);
	
		if (pgm.run() != true)
			return 0;
			
		else {
			AS400PackedDecimal number = new AS400PackedDecimal(11, 0);
			byte[] data = parmList[0].getOutputData();
			double dd   = number.toDouble(data, 0);
			int    tran = (int) dd;
			as400.disconnectService(AS400.COMMAND);
			return tran;
		}
	
	}
	catch (Exception e) {
		return 0;
	}
	
	finally {
		if (as400 != null)
			ConnectionStack.returnAS400Object(as400);
	}
		
}
/**
 * Test the data type to set the proper SQL file join statements.
 *
 * Creation date: (8/23/2004 8:24:29 AM)
 */
public static String sqlClauseJoin(Vector inSearchInfo, String sqlStatement) {
	
	try {
		
		for (int x = 0; x < inSearchInfo.size(); x++) {
			FormDefinition searchCell = (FormDefinition) inSearchInfo.elementAt(x);
			if (searchCell.dataType.trim().toLowerCase().equals ("broker")) 
				sqlStatement = sqlStatement + sqlJoinBroker(searchCell, sqlStatement);
		}
		for (int x = 0; x < inSearchInfo.size(); x++) {
			FormDefinition searchCell = (FormDefinition) inSearchInfo.elementAt(x);
			if (searchCell.dataType.trim().toLowerCase().equals ("customer")) 
				sqlStatement = sqlStatement + sqlJoinCustomer(searchCell, sqlStatement);			
		}
		for (int x = 0; x < inSearchInfo.size(); x++) {
			FormDefinition searchCell = (FormDefinition) inSearchInfo.elementAt(x);
			if (searchCell.dataType.trim().toLowerCase().equals ("order number")) 
				sqlStatement = sqlStatement + sqlJoinOrderNumber(searchCell, sqlStatement);
		}
		for (int x = 0; x < inSearchInfo.size(); x++) {
			FormDefinition searchCell = (FormDefinition) inSearchInfo.elementAt(x);
			if (searchCell.dataType.trim().toLowerCase().equals ("resource")) 
				sqlStatement = sqlStatement + sqlJoinResource(searchCell, sqlStatement);			
		}
		for (int x = 0; x < inSearchInfo.size(); x++) {
			FormDefinition searchCell = (FormDefinition) inSearchInfo.elementAt(x);
			if (searchCell.dataType.trim().toLowerCase().equals ("sample number")) 
				sqlStatement = sqlStatement + sqlJoinSampleNumber(searchCell, sqlStatement);
		}
					
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlClauseJoin(Vector String): " + e);
	}	
	
	return sqlStatement; 	
}
/**
 * Test the search type to set the proper SQL select clause.
 *
 * Creation date: (8/20/2004 8:24:29 AM)
 */
public static String sqlClauseWhere(FormDefinition searchCell) {

	String sqlSelect   = "";
	String specialType = "n";	
	
	try {		
		
		// Create SQL "WHERE" clause statement (BETWEEN) to select data by low to high range.
		
		if (searchCell.searchType.trim().toLowerCase().equals ("range")) {
			
			if (searchCell.dataType.trim().toLowerCase().equals ("customer")) {			
				sqlSelect = sqlSelect + sqlRangeCustomer(searchCell);
				specialType = "y";
			}
				
			if (specialType.equals ("n")) {										
				if (searchCell.getDataCode().equals("DT"))
					sqlSelect = sqlSelect + sqlRangeDate(searchCell);
				if (searchCell.getDataCode().equals("NU"))
					sqlSelect = sqlSelect + sqlRangeNumber(searchCell);			
				if (searchCell.getDataCode().equals("TM"))
					sqlSelect = sqlSelect + sqlRangeTime(searchCell);
				if (searchCell.getDataCode().equals("TX"))
					sqlSelect = sqlSelect + sqlRangeText(searchCell);
			}
		}
		
		// Create SQL "WHERE" clause statement (EQUALS) to select data by a single value.
		
		if (searchCell.searchType.trim().toLowerCase().equals ("single")) {
						
			if (searchCell.dataType.trim().toLowerCase().equals ("customer")) {			
				sqlSelect = sqlSelect + sqlSingleCustomer(searchCell);
				specialType = "y";
			}
				
			if (specialType.equals ("n")) {							
				if (searchCell.getDataCode().equals("DT"))
					sqlSelect = sqlSelect + sqlSingleDate(searchCell);
				if (searchCell.getDataCode().equals("NU"))
					sqlSelect = sqlSelect + sqlSingleNumber(searchCell);				
				if (searchCell.getDataCode().equals("TM"))
					sqlSelect = sqlSelect + sqlSingleTime(searchCell);
				if (searchCell.getDataCode().equals("TX"))
					sqlSelect = sqlSelect + sqlSingleText(searchCell);
			}
		}
		
		// Create SQL "WHERE" clause statement (LIKE) to select data that contains a search value.
		// (some "contains" are not always applicable, therefore default to the "single" process.)
		
		if (searchCell.searchType.trim().toLowerCase().equals ("contains")) {
						
			if (searchCell.dataType.trim().toLowerCase().equals ("customer")) {			
				sqlSelect = sqlSelect + sqlContainsCustomer(searchCell);
				specialType = "y";
			}
				
			if (specialType.equals ("n")) {	
				if (searchCell.getDataCode().equals("DT"))
					sqlSelect = sqlSelect + sqlSingleDate(searchCell);
				if (searchCell.getDataCode().equals("NU"))
					sqlSelect = sqlSelect + sqlSingleNumber(searchCell);				
				if (searchCell.getDataCode().equals("TM"))
					sqlSelect = sqlSelect + sqlSingleTime(searchCell);
				if (searchCell.getDataCode().equals("TX"))
					sqlSelect = sqlSelect + sqlContainsText(searchCell);
			}
		}
		
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlClauseWhere(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the basic transaction data (non-column) search type to set the proper SQL select clause.
 *  
 * (some "contains" are not always applicable, therefore default to the "single" process.)
 *
 * Creation date: (9/09/2004 8:24:29 AM)
 */
public static String sqlClauseWhereBasic(FormDefinition searchCell, int dataIndex) {

	String sqlSelect = "";
	int    x         = dataIndex;
	
	try {
		
		if ((!searchCell.showOnInquiry[x].toLowerCase().equals ("n")) &&
			(!searchCell.showOnInquiry[x].toLowerCase().equals ("y"))) {
		    		
		
		// Create SQL "WHERE" clause statement to select transaction number.
		
		if (dataIndex == 0) {
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("r")) 		// Range: low to high		
				sqlSelect = sqlSelect + sqlRangeTranNumber(searchCell);
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("s")) 		// Single value selection
				sqlSelect = sqlSelect + sqlSingleTranNumber(searchCell);
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("c")) 		// Contains value selected
				sqlSelect = sqlSelect + sqlSingleTranNumber(searchCell);
		}
		
		
		// Create SQL "WHERE" clause statement to select transaction date.
		
		if (dataIndex == 1) {
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("r")) 		
				sqlSelect = sqlSelect + sqlRangeTranDate(searchCell);
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("s")) 		
				sqlSelect = sqlSelect + sqlSingleTranDate(searchCell);
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("c")) 		
				sqlSelect = sqlSelect + sqlSingleTranDate(searchCell);
		}


		// Create SQL "WHERE" clause statement to select transaction user.
		
		if (dataIndex == 2) {
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("r")) 		
				sqlSelect = sqlSelect + sqlRangeTranUser(searchCell);
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("s")) 		
				sqlSelect = sqlSelect + sqlSingleTranUser(searchCell);
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("c")) 		
				sqlSelect = sqlSelect + sqlContainsTranUser(searchCell);
		}


		// Create SQL "WHERE" clause statement to select last update date.
		
		if (dataIndex == 3) {
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("r")) 		
				sqlSelect = sqlSelect + sqlRangeTranUpdateDate(searchCell);
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("s")) 		
				sqlSelect = sqlSelect + sqlSingleTranUpdateDate(searchCell);
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("c")) 		
				sqlSelect = sqlSelect + sqlSingleTranUpdateDate(searchCell);
		}


		// Create SQL "WHERE" clause statement to select last update time.
		
		if (dataIndex == 4) {
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("r")) 		
				sqlSelect = sqlSelect + sqlRangeTranUpdateTime(searchCell);
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("s")) 		
				sqlSelect = sqlSelect + sqlSingleTranUpdateTime(searchCell);
			if (searchCell.showOnInquiry[x].toLowerCase().equals ("c")) 		
				sqlSelect = sqlSelect + sqlSingleTranUpdateTime(searchCell);
		}
	}	
		
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlClauseWhereBasic(Class int): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the data value to set the proper SQL select clause.
 *
 * Creation date: (10/06/2004 8:24:29 AM)
 */
public static String sqlClauseWhereValue(FormDefinition columnCell, FormData dataCell) {

	String sqlSelect   = "";
	
	try {			

		String dataField = buildDataFieldName(columnCell.columnNumber, columnCell.dataCode,
											  columnCell.dataNumber);				
						
		if (columnCell.getDataCode().equals("DT"))
			sqlSelect = " AND (" + dataField + " = '" + dataCell.getDataDate() + "')";
		if (columnCell.getDataCode().equals("NU"))
			sqlSelect = " AND (" + dataField + " = " + dataCell.getDataNumeric() + ")";		
		if (columnCell.getDataCode().equals("TM"))
			sqlSelect = " AND (" + dataField + " = '" + dataCell.getDataTime() + "')";
		if (columnCell.getDataCode().equals("TX"))
			sqlSelect = " AND (" + dataField + " = '" + dataCell.getDataText() + "')";
			
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlClauseWhereValue(Class Class): " + e);
	}	
	
	return sqlSelect; 	
}
/**
 * Test the text value to contain a selected customer number.
 *
 * Creation date: (8/26/2004 8:24:29 AM)
 */
public static String sqlContainsCustomer(FormDefinition searchCell) {

	String sqlSelect = "";	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
		if (!searchCell.getTextLow().equals("")) {
			
//			String[] messageInfo = ValidateFields.validateCustomer(searchCell.getTextLow(), "limited", defaultCustomer);
//			String customerData  = messageInfo[1];
			
//			if (!customerData.equals (defaultCustomer)) {
				
//				int d = defaultCustomer.indexOf("_");
//				String defaultCompany = defaultCustomer.substring(0,d);
//				String defaultNumber  = defaultCustomer.substring(d+1,defaultCustomer.length());		    
				
//				int x = customerData.indexOf("_");
//				String companyNumber  = customerData.substring(0,x);
//				String customerNumber = customerData.substring(x+1,customerData.length());
				
//				String dataField = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode,
//													  searchCell.dataNumber);	
													  		
//				if (!companyNumber.equals (defaultCompany))	
//					sqlSelect = sqlSelect + "(SUBSTR(" + dataField + ",1,5) LIKE '%" + companyNumber + "%') AND ";
//				if (!customerNumber.equals (defaultNumber))	
//					sqlSelect = sqlSelect + "(SUBSTR(" + dataField + ",7,20) LIKE '%" + customerNumber + "%') AND ";
															
//			}
			  
		}
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlContainsCustomer(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the text value to contain a selected value.
 *
 * Creation date: (8/20/2004 8:24:29 AM)
 */
public static String sqlContainsText(FormDefinition searchCell) {

	String sqlSelect = "";	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
		if (!searchCell.getTextLow().equals("")) {

			String dataField = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode,
												  searchCell.dataNumber);	
			    
			sqlSelect = sqlSelect + "(UPPER(" + dataField + ") LIKE '%" + 
									searchCell.getTextLow().toUpperCase() + "%') AND ";
			                        			
		}
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlContainsText(Class): " + e);
	}	
	
	return sqlSelect; 	
}
/**
 * Test the transaction user value to contain a selected value.
 *
 * Creation date: 9/09/2004 8:24:29 AM)
 */
public static String sqlContainsTranUser(FormDefinition searchCell) {

	String sqlSelect = "";	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
		if (!searchCell.getTextLow().equals("")) {

			sqlSelect = sqlSelect + "(UPPER(FMCUSER) LIKE '%" + 
									searchCell.getTextLow().toUpperCase() + "%') AND ";			                        			
		}
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlContainsTranUser(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Create SQL statement to join the broker master to the form data.
 *
 * Creation date: (9/29/2004 8:24:29 AM)
 */
public static String sqlJoinBroker(FormDefinition searchCell, String sqlStatement) {
	
	try {		
  // 9/19/08 -TWALTON - Moving Data to NEW Box
   //        IF This information is needed, we will need to point to the Movex Files at a later date.
			
//		int fileFound = sqlStatement.indexOf("ARPABRKR");		        
//		if (fileFound < 0)			
//			sqlStatement = " LEFT OUTER JOIN " + library + "ARPABRKR ON ";
//		int select = sqlStatement.indexOf("ARABKR");		        
//		if (select >= 0)	
//			sqlStatement = sqlStatement + "OR ";
										
//		String fieldName = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode, 
//											  searchCell.dataNumber);
//		sqlStatement = sqlStatement + "(" + fieldName + " = ARABKR) ";									 					               			
					
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlJoinBroker(Class String): " + e);
	}	
	
	return sqlStatement;	
}
/**
 * Create SQL statement to join the customer master to the form data.
 *
 * Creation date: (8/24/2004 8:24:29 AM)
 */
public static String sqlJoinCustomer(FormDefinition searchCell, String sqlStatement) {
	
	try {		
   // 9/19/08 -TWALTON - Moving Data to NEW Box
   //        IF This information is needed, we will need to point to the Movex Files at a later date.

//		int fileFound = sqlStatement.indexOf("ARLCU");		        
//		if (fileFound < 0)			
//			sqlStatement = " LEFT OUTER JOIN " + systemAR + "ARLCU ON ";
//		int substr = sqlStatement.indexOf("SUBSTR");		        
//		if (substr >= 0)	
//			sqlStatement = sqlStatement + "OR ";
//										
//		String fieldName = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode, 
//											  searchCell.dataNumber);
//		sqlStatement = sqlStatement + "(SUBSTR(" + fieldName + ",1,5) = CUCO AND " +
//									  "SUBSTR(" + fieldName + ",7,20) = CUCUNO) ";					               			
					
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlJoinCustomer(Class String): " + e);
	}	
	
	return sqlStatement;	
}
/**
 * Create SQL statement to join the sales order header to the form data.
 *
 * Creation date: (11/15/2004 8:24:29 AM)
 */
public static String sqlJoinOrderNumber(FormDefinition searchCell, String sqlStatement) {
	
	try {		
  // 9/19/08 -TWALTON - Moving Data to NEW Box
   //        IF This information is needed, we will need to point to the Movex Files at a later date.
			
//		int fileFound = sqlStatement.indexOf("ARPPSOHD");		        
//		if (fileFound < 0)			
//			sqlStatement = " LEFT OUTER JOIN " + library + "ARPPSOHD ON ";
//		int select = sqlStatement.indexOf("ARORD#");		        
//		if (select >= 0)	
//			sqlStatement = sqlStatement + "OR ";
										
//		String fieldName = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode, 
//											  searchCell.dataNumber);
//		sqlStatement = sqlStatement + "(" + fieldName + " = ARORD#) ";									 					               			
					
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlJoinOrderNumber(Class String): " + e);
	}	
	
	return sqlStatement;	
}
/**
 * Create SQL statement to join the resource master to the form data.
 *
 * Creation date: (11/15/2004 8:24:29 AM)
 */
public static String sqlJoinResource(FormDefinition searchCell, String sqlStatement) {
	
	try {		
  // 9/19/08 -TWALTON - Moving Data to NEW Box
  //        IF This information is needed, we will need to point to the Movex Files at a later date.
			
//		int fileFound = sqlStatement.indexOf("RESMST");		        
//		if (fileFound < 0)			
//			sqlStatement = " LEFT OUTER JOIN " + prism + "RESMST ON ";
//		int select = sqlStatement.indexOf("RMRESC");		        
//		if (select >= 0)	
//			sqlStatement = sqlStatement + "OR ";
										
//		String fieldName = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode, 
//											  searchCell.dataNumber);
//		sqlStatement = sqlStatement + "(" + fieldName + " = RMRESC) ";									 					               			
					
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlJoinResource(Class String): " + e);
	}	
	
	return sqlStatement;	
}
/**
 * Create SQL statement to join the sample request order to the form data.
 *
 * Creation date: (11/16/2004 8:24:29 AM)
 */
public static String sqlJoinSampleNumber(FormDefinition searchCell, String sqlStatement) {
	
	try {		
  // 9/19/08 -TWALTON - Moving Data to NEW Box
   //        IF This information is needed, we will need to point to the Movex Files at a later date.
			
//		int fileFound = sqlStatement.indexOf("SRLAHDR1");		        
//		if (fileFound < 0)			
//			sqlStatement = " LEFT OUTER JOIN " + library + "SRLAHDR1 ON ";
//		int select = sqlStatement.indexOf("SRASR#");		        
//		if (select >= 0)	
//			sqlStatement = sqlStatement + "OR ";
										
//		String fieldName = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode, 
//											  searchCell.dataNumber);
//		sqlStatement = sqlStatement + "(" + fieldName + " = SRASR#) ";									 					               			
					
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlJoinSampleNumber(Class String): " + e);
	}	
	
	return sqlStatement;	
}
/**
 * Build the SQL order by sort.
 *
 * Creation date: (10/15/2003 8:24:29 AM)
 */
public static String sqlOrderBy(FormData inData) {

	String  orderBy      = "";
	boolean defaultOrder = false;
		
	try {
		
		if ((inData.sortOrderSequence != null) && (inData.sortOrderSequence.intValue() > 0)) {
			String fieldName = buildDataFieldName(inData.columnNumber, inData.dataCode, inData.dataNumber);		
			orderBy = "ORDER BY " + fieldName;
			if ((inData.sortOrderStyle != null) && (!inData.sortOrderStyle.trim().equals ("")))
				orderBy = orderBy + " " + inData.sortOrderStyle.trim().toUpperCase();		
		}
		else {
			orderBy = "ORDER BY FMCTRNDTE";
			if ((inData.orderByStyle != null) && (!inData.sortOrderStyle.trim().equals (""))) 
				orderBy = orderBy + " " + inData.sortOrderStyle.trim().toUpperCase();
				
			orderBy = orderBy + ", FMCTRNNBR";						
		}	

	}	 
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData." +
						   "sqlOrderBy(Class): " + e);
	}
				
	return orderBy; 
}
/**
 * Build the SQL order by sort.
 *
 * Creation date: (10/15/2003 8:24:29 AM)
 */
public static String sqlOrderBy(String[] inSortField, String[] inSortStyle) {

	String orderBy = "";
		
	try {

		 for(int i = 0; i < inSortField.length; i++) {

			 if ((inSortField[i] != null) && (!inSortField[i].trim().equals(""))) {		
			     
				 int found  = orderBy.toUpperCase().indexOf("ORDER BY");		        
				 if (found >= 0) 
					 orderBy = orderBy + ", " + inSortField[i];
				 else		          
				 orderBy = " ORDER BY " + inSortField[i];

		         
				 if ((inSortStyle[i] != null) && (!inSortStyle[i].trim().equals("")))
					  orderBy = orderBy + " " + inSortStyle[i].toUpperCase();		          
			 }

		 }

		 if (orderBy.equals(""))
			 orderBy = " ORDER BY FMCTRNDTE, FMCTRNNBR";            	
		
	}	 
	catch (Exception e) {
		System.out.println("Exception error at com.treetop.data.FormData." +
						   "sqlOrderBy(String[] String[] int): " + e);
	}	
			
	return orderBy; 
}
/**
 * Test the text value to the from/to customer range.
 *
 * Creation date: (8/26/2004 8:24:29 AM)
 */
public static String sqlRangeCustomer(FormDefinition searchCell) {

	String  sqlSelect    = "";
	boolean lowCompany   = false;
	boolean	highCompany  = false;
	boolean lowCustomer  = false;
	boolean	highCustomer = false;		
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
		if (!searchCell.getTextLow().equals("")) {
			
			// Convert the customer number to its special format.
			
//			String[] messageInfo    = ValidateFields.validateCustomer(searchCell.getTextLow(), "limited", defaultCustomer);
//			String customerDataLow  = messageInfo[1];
//					 messageInfo    = ValidateFields.validateCustomer(searchCell.getTextHigh(), "limited", defaultCustomer);
//			String customerDataHigh = messageInfo[1];
			
			// Divide the company and customer numbers into separate fields.
				
//			int d = defaultCustomer.indexOf("_");
//			String defaultCompany = defaultCustomer.substring(0,d);
//			String defaultNumber  = defaultCustomer.substring(d+1,defaultCustomer.length());		    
				
//			int l = customerDataLow.indexOf("_");
//			String companyNumberLow   = customerDataLow.substring(0,l);
//			String customerNumberLow  = customerDataLow.substring(l+1,customerDataLow.length());
//				
//			int h = customerDataHigh.indexOf("_");
//			String companyNumberHigh  = customerDataHigh.substring(0,h);
//			String customerNumberHigh = customerDataHigh.substring(h+1,customerDataLow.length());
			
			// Retrieve the field name referencing the stored data.
				
			String dataField = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode,
												  searchCell.dataNumber);
			
			// Review user input data and determine with portions of the search criteria exist.
													 
//			if (!companyNumberLow.equals (defaultCompany))
//				lowCompany = true;
//			if (!companyNumberHigh.equals (defaultCompany))
//				highCompany = true;
//			if (!customerNumberLow.equals (defaultNumber))
//				lowCustomer = true;
//			if (!customerNumberHigh.equals (defaultNumber))
//				highCustomer = true;				
								
			// Process the company number portion into SQL.
												  		
//			if ((lowCompany) && (highCompany)) {			
//				sqlSelect = sqlSelect + "(SUBSTR(" + dataField + ",1,5) BETWEEN '" + companyNumberLow + "' ";
//				sqlSelect = sqlSelect + "AND '" + companyNumberHigh + "') AND ";
//			}				
//			if ((lowCompany) && (!highCompany))
//				sqlSelect = sqlSelect + "(SUBSTR(" + dataField + ",1,5) >= '" + companyNumberLow + "') AND ";
//			if ((!lowCompany) && (highCompany))
//				sqlSelect = sqlSelect + "(SUBSTR(" + dataField + ",1,5) <= '" + companyNumberHigh + "') AND ";					
//								
//			// Process the customer number portion into SQL.
//								
//			if ((lowCustomer) && (highCustomer)) {
//				sqlSelect = sqlSelect + "(SUBSTR(" + dataField + ",7,20) BETWEEN '" + customerNumberLow + "' ";
//				sqlSelect = sqlSelect + "AND '" + customerNumberHigh + "') AND ";			
//			}
//			if ((lowCustomer) && (!highCustomer))
//				sqlSelect = sqlSelect + "(SUBSTR(" + dataField + ",7,20) >= '" + customerNumberLow + "') AND ";
//			if ((!lowCustomer) && (highCustomer))
//				sqlSelect = sqlSelect + "(SUBSTR(" + dataField + ",7,20) <= '" + customerNumberHigh + "') AND ";									
						  
		}
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlRangeCustomer(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the date value to the from/to date range.
 *
 * Creation date: (8/25/2003 8:24:29 AM)
 */
public static String sqlRangeDate(FormDefinition searchCell) {

	String  sqlSelect = "";
	boolean lowRange  = false;
	boolean	highRange = false;	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
			
			if (searchCell.getDateLow() != null)
				lowRange = true;
			if (searchCell.getDateHigh() != null)
				highRange = true; 	
	    		   		   
			String dataField = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode,
												  searchCell.dataNumber);			    
			
			if ((lowRange) && (highRange)) {			// Both ranges used
				sqlSelect = sqlSelect + "(" + dataField + " BETWEEN '" + searchCell.getDateLow() + "' ";
				sqlSelect = sqlSelect + "AND '" + searchCell.getDateHigh() + "') AND ";
			}
			if ((lowRange) && (!highRange)) 			// Only low range
				sqlSelect = sqlSelect + "(" + dataField + " >= '" + searchCell.getDateLow() + "') AND ";
				
			if ((!lowRange) && (highRange)) 			// Only high range
				sqlSelect = sqlSelect + "(" + dataField + " <= '" + searchCell.getDateHigh() + "') AND ";				     	    
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlRangeDate(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Set the SQL range statement to select the from/to effective dates.
 *
 * Creation date: (8/25/2003 8:24:29 AM)
 */
public static String sqlRangeTranDate(FormDefinition searchCell) {

	String  sqlSelect = "";
	boolean lowRange  = false;
	boolean highRange = false;	

	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
			  
			if (searchCell.getDateLow() != null)
				lowRange = true;
			if (searchCell.getDateHigh() != null)
				highRange = true;
			
			if ((lowRange) && (highRange)) {			// Both ranges used 
				sqlSelect = sqlSelect + "(FMCTRNDTE BETWEEN '" + searchCell.getDateLow() + "' ";
				sqlSelect = sqlSelect + "AND '" + searchCell.getDateHigh() + "') AND ";	       
			}
			
			if ((lowRange) && (!highRange)) 			// Only low range 
				sqlSelect = sqlSelect + "(FMCTRNDTE >= '" + searchCell.getDateLow() + "') AND ";
				
			if ((!lowRange) && (highRange)) 			// Only high range  
				sqlSelect = sqlSelect + "(FMCTRNDTE <= '" + searchCell.getDateLow() + "') AND ";
		}
		
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlRangeTranDate(Class): " + e);
	}
					
	return sqlSelect; 
}
/**
 * Test the transaction number to the from/to numeric range.
 *
 * Creation date: (9/09/2003 8:24:29 AM)
 */
public static String sqlRangeTranNumber(FormDefinition searchCell) {

	String     sqlSelect = "";
	BigDecimal zero      = new BigDecimal("0");
	boolean    lowRange  = false;
	boolean	   highRange = false;	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
			
			if (searchCell.getNumericLow() != null) {
				int numeric  = searchCell.getNumericLow().compareTo(zero);
				if (numeric != 0) 							
					lowRange = true;
			}
			
			if (searchCell.getNumericHigh() != null) {
				int numeric  = searchCell.getNumericHigh().compareTo(zero);
				if (numeric != 0) 							
					highRange = true;
			}	    		   		   
			
			if ((lowRange) && (highRange)) {			// Both ranges used
				sqlSelect = sqlSelect + "(FMCTRNNBR BETWEEN " + searchCell.getNumericLow() + " ";
				sqlSelect = sqlSelect + "AND " + searchCell.getNumericHigh() + ") AND ";
			}
			if ((lowRange) && (!highRange)) 			// Only low range
				sqlSelect = sqlSelect + "(FMCTRNNBR >= " + searchCell.getNumericLow() + ") AND ";
				
			if ((!lowRange) && (highRange)) 			// Only high range
				sqlSelect = sqlSelect + "(FMCTRNNBR <= " + searchCell.getNumericHigh() + ") AND ";				     	    
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlRangeTranNumber(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the transaction user value to the from/to text range.
 *
 * Creation date: (9/09/2004 8:24:29 AM)
 */
public static String sqlRangeTranUser(FormDefinition searchCell) {

	String  sqlSelect = "";
	boolean lowRange  = false;
	boolean	highRange = false;	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
			
			if ((searchCell.getTextLow() != null) && (!searchCell.getTextLow().trim().equals("")))
				lowRange = true;
			if ((searchCell.getTextHigh() != null) && (!searchCell.getTextHigh().trim().equals("")))
				highRange = true; 	
	    		   		   
			String dataField = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode,
												  searchCell.dataNumber);			    
			
			if ((lowRange) && (highRange)) {			// Both ranges used
				sqlSelect = sqlSelect + "(FMCUSER BETWEEN '" + searchCell.getTextLow() + "' ";
				sqlSelect = sqlSelect + "AND '" + searchCell.getTextHigh() + "') AND ";
			}
			if ((lowRange) && (!highRange)) 			// Only low range
				sqlSelect = sqlSelect + "(FMCUSER >= '" + searchCell.getTextLow() + "') AND ";
				
			if ((!lowRange) && (highRange)) 			// Only high range
				sqlSelect = sqlSelect + "(FMCUSER <= '" + searchCell.getTextHigh() + "') AND ";				     	    
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlRangeTranUser(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Set the SQL range statement to select the from/to last transaction update date.
 *
 * Creation date: (9/09/2004 8:24:29 AM)
 */
public static String sqlRangeTranUpdateDate(FormDefinition searchCell) {

	String  sqlSelect = "";
	boolean lowRange  = false;
	boolean highRange = false;	

	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
			  
			if (searchCell.getDateLow() != null)
				lowRange = true;
			if (searchCell.getDateHigh() != null)
				highRange = true;
			
			if ((lowRange) && (highRange)) {			// Both ranges used 
				sqlSelect = sqlSelect + "(FMCDATE BETWEEN '" + searchCell.getDateLow() + "' ";
				sqlSelect = sqlSelect + "AND '" + searchCell.getDateHigh() + "') AND ";	       
			}
			
			if ((lowRange) && (!highRange)) 			// Only low range 
				sqlSelect = sqlSelect + "(FMCDATE >= '" + searchCell.getDateLow() + "') AND ";
				
			if ((!lowRange) && (highRange)) 			// Only high range  
				sqlSelect = sqlSelect + "(FMCDATE <= '" + searchCell.getDateLow() + "') AND ";
		}
		
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlRangeTranUpdateDate(Class): " + e);
	}
					
	return sqlSelect; 
}
/**
 * Set the SQL range statement to select the from/to last transaction update time.
 *
 * Creation date: (9/09/2004 8:24:29 AM)
 */
public static String sqlRangeTranUpdateTime(FormDefinition searchCell) {

	String  sqlSelect = "";
	boolean lowRange  = false;
	boolean	highRange = false;	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
			
			if (searchCell.getTimeLow() != null)
				lowRange = true;
			if (searchCell.getTimeHigh() != null)
				highRange = true;   		   		   
						
			if ((lowRange) && (highRange)) {			// Both ranges used
				sqlSelect = sqlSelect + "(FMCTIME BETWEEN '" + searchCell.getTimeLow() + "' ";
				sqlSelect = sqlSelect + "AND '" + searchCell.getTimeHigh() + "') AND ";
			}
			if ((lowRange) && (!highRange)) 			// Only low range
				sqlSelect = sqlSelect + "(FMCTIME >= '" + searchCell.getTimeLow() + "') AND ";
				
			if ((!lowRange) && (highRange)) 			// Only high range
				sqlSelect = sqlSelect + "(FMCTIME <= '" + searchCell.getTimeHigh() + "') AND ";				     	    
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlRangeTranUpdateTime(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the numeric value to the from/to numeric range.
 *
 * Creation date: (8/25/2003 8:24:29 AM)
 */
public static String sqlRangeNumber(FormDefinition searchCell) {

	String     sqlSelect = "";
	BigDecimal zero      = new BigDecimal("0");
	boolean    lowRange  = false;
	boolean	   highRange = false;	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
			
			if (searchCell.getNumericLow() != null) {
				int numeric  = searchCell.getNumericLow().compareTo(zero);
				if (numeric != 0) 							
					lowRange = true;
			}
			
			if (searchCell.getNumericHigh() != null) {
				int numeric  = searchCell.getNumericHigh().compareTo(zero);
				if (numeric != 0) 							
					highRange = true;
			}
	    		   		   
			String dataField = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode,
												  searchCell.dataNumber);			    
			
			if ((lowRange) && (highRange)) {			// Both ranges used
				sqlSelect = sqlSelect + "(" + dataField + " BETWEEN " + searchCell.getNumericLow() + " ";
				sqlSelect = sqlSelect + "AND " + searchCell.getNumericHigh() + ") AND ";
			}
			if ((lowRange) && (!highRange)) 			// Only low range
				sqlSelect = sqlSelect + "(" + dataField + " >= " + searchCell.getNumericLow() + ") AND ";
				
			if ((!lowRange) && (highRange)) 			// Only high range
				sqlSelect = sqlSelect + "(" + dataField + " <= " + searchCell.getNumericHigh() + ") AND ";				     	    
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlRangeNumber(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the text value to the from/to text range.
 *
 * Creation date: (8/25/2003 8:24:29 AM)
 */
public static String sqlRangeText(FormDefinition searchCell) {

	String  sqlSelect = "";
	boolean lowRange  = false;
	boolean	highRange = false;	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
			
			if ((searchCell.getTextLow() != null) && (!searchCell.getTextLow().trim().equals("")))
				lowRange = true;
			if ((searchCell.getTextHigh() != null) && (!searchCell.getTextHigh().trim().equals("")))
				highRange = true; 	
	    		   		   
			String dataField = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode,
												  searchCell.dataNumber);			    
			
			if ((lowRange) && (highRange)) {			// Both ranges used
				sqlSelect = sqlSelect + "(" + dataField + " BETWEEN '" + searchCell.getTextLow() + "' ";
				sqlSelect = sqlSelect + "AND '" + searchCell.getTextHigh() + "') AND ";
			}
			if ((lowRange) && (!highRange)) 			// Only low range
				sqlSelect = sqlSelect + "(" + dataField + " >= '" + searchCell.getTextLow() + "') AND ";
				
			if ((!lowRange) && (highRange)) 			// Only high range
				sqlSelect = sqlSelect + "(" + dataField + " <= '" + searchCell.getTextHigh() + "') AND ";				     	    
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlRangeText(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the time value to the from/to time range.
 *
 * Creation date: (8/25/2003 8:24:29 AM)
 */
public static String sqlRangeTime(FormDefinition searchCell) {

	String  sqlSelect = "";
	boolean lowRange  = false;
	boolean	highRange = false;	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
			
			if (searchCell.getTimeLow() != null)
				lowRange = true;
			if (searchCell.getTimeHigh() != null)
				highRange = true; 	
	    		   		   
			String dataField = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode,
												  searchCell.dataNumber);			    
			
			if ((lowRange) && (highRange)) {			// Both ranges used
				sqlSelect = sqlSelect + "(" + dataField + " BETWEEN '" + searchCell.getTimeLow() + "' ";
				sqlSelect = sqlSelect + "AND '" + searchCell.getTimeHigh() + "') AND ";
			}
			if ((lowRange) && (!highRange)) 			// Only low range
				sqlSelect = sqlSelect + "(" + dataField + " >= '" + searchCell.getTimeLow() + "') AND ";
				
			if ((!lowRange) && (highRange)) 			// Only high range
				sqlSelect = sqlSelect + "(" + dataField + " <= '" + searchCell.getTimeHigh() + "') AND ";				     	    
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlRangeTime(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the text value to a single selected customer value.
 *
 * Creation date: (8/20/2004 8:24:29 AM)
 */
public static String sqlSingleCustomer(FormDefinition searchCell) {

	String sqlSelect = "";	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
		if (!searchCell.getTextLow().equals("")) {
			
//			String[] messageInfo = ValidateFields.validateCustomer(searchCell.getTextLow(), "limited", defaultCustomer);
//			String customerData  = messageInfo[1];
//			
//			if (!customerData.equals (defaultCustomer)) {
//				
//				int d = defaultCustomer.indexOf("_");
//				String defaultCompany = defaultCustomer.substring(0,d);
//				String defaultNumber  = defaultCustomer.substring(d+1,defaultCustomer.length());		    
//				
//				int x = customerData.indexOf("_");
//				String companyNumber  = customerData.substring(0,x);
//				String customerNumber = customerData.substring(x+1,customerData.length());
//				
	//			String dataField = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode,
	//												  searchCell.dataNumber);	
													  		
	///			if (!companyNumber.equals (defaultCompany))	
	//				sqlSelect = sqlSelect + "(SUBSTR(" + dataField + ",1,5) = '" + companyNumber + "') AND ";
	//			if (!customerNumber.equals (defaultNumber))	
	//				sqlSelect = sqlSelect + "(SUBSTR(" + dataField + ",7,20) = '" + customerNumber + "') AND ";	
											
	//		}
			  
		}
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlSingleCustomer(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the date value to a single selected value.
 *
 * Creation date: (8/20/2004 8:24:29 AM)
 */
public static String sqlSingleDate(FormDefinition searchCell) {

	String sqlSelect = "";	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
		if  (searchCell.getDateLow() != null) {
		   		   
			String dataField = buildDataFieldName(searchCell. columnNumber, searchCell.dataCode,
												  searchCell.dataNumber);			    
			   
			sqlSelect = sqlSelect + "(" + dataField + " = '" + searchCell.getDateLow() + "') AND ";
  
		}
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlSingleDate(Class): " + e);
	}	
	
	return sqlSelect;	
}
/** 
 * Test the numeric value to a single selected value.
 *
 * Creation date: (8/20/2004 8:24:29 AM)
 */
public static String sqlSingleNumber(FormDefinition searchCell) {

	String sqlSelect = "";	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
	   
			String dataField = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode,
												  searchCell.dataNumber);	
			    			   
			sqlSelect = sqlSelect + "(" + dataField + " = " + searchCell.getNumericLow() + ") AND ";
			
		}
	  
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlSingleNumber(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the text value to a single selected value.
 *
 * Creation date: (8/20/2004 8:24:29 AM)
 */
public static String sqlSingleText(FormDefinition searchCell) {

	String sqlSelect = "";	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
		if (!searchCell.getTextLow().equals("")) {

			String dataField = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode,
												  searchCell.dataNumber);	
			
			if (!searchCell.getDataType().trim().toLowerCase().equals ("customer"))    
				sqlSelect = sqlSelect + "(" + dataField + " = '" + searchCell.getTextLow() + "') AND ";
			else {
				
			}
			  
		}
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlSingleText(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the time value to a single selected value.
 *
 * Creation date: (8/20/2004 8:24:29 AM)
 */
public static String sqlSingleTime(FormDefinition searchCell) {

	String sqlSelect = "";	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
		if  (searchCell.getTimeLow() != null) {
		   
			String dataField = buildDataFieldName(searchCell.columnNumber, searchCell.dataCode,
												  searchCell.dataNumber);			    
			   
			sqlSelect = sqlSelect + "(" + dataField + " = '" + searchCell.getTimeLow() + "') AND ";
			  
		}
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlSingleTime(Class): " + e);
	}	
	
	return sqlSelect; 	
}
/**
 * Test the transaction date value to a single selected value.
 *
 * Creation date: (9/09/2004 8:24:29 AM)
 */
public static String sqlSingleTranDate(FormDefinition searchCell) {

	String sqlSelect = "";	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
		if  (searchCell.getDateLow() != null) {		   	
			
			sqlSelect = sqlSelect + "(FMCTRNDTE = '" + searchCell.getDateLow() + "') AND ";  
		}
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlSingleTranDate(Class): " + e);
	}	
	
	return sqlSelect;	
}
/** 
 * Test the transaction number value to a single selected value.
 *
 * Creation date: (9/09/2004 8:24:29 AM)
 */
public static String sqlSingleTranNumber(FormDefinition searchCell) {

	String sqlSelect = "";	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
	   			
			sqlSelect = sqlSelect + "(FMCTRNNBR = " + searchCell.getNumericLow() + ") AND ";
		}
	  
	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlSingleTranNumber(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the transaction user to a single selected value.
 *
 * Creation date: (9/09/2004 8:24:29 AM)
 */
public static String sqlSingleTranUser(FormDefinition searchCell) {

	String sqlSelect = "";	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
		if (!searchCell.getTextLow().equals("")) {
			
			sqlSelect = sqlSelect + "(FMCUSER = '" + searchCell.getTextLow() + "') AND ";			  
		}
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlSingleTranUser(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the last update transaction date value to a single selected value.
 *
 * Creation date: (9/09/2004 8:24:29 AM)
 */
public static String sqlSingleTranUpdateDate(FormDefinition searchCell) {

	String sqlSelect = "";	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
		if  (searchCell.getDateLow() != null) {		   	
			
			sqlSelect = sqlSelect + "(FMCDATE = '" + searchCell.getDateLow() + "') AND ";  
		}
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlSingleTranUpdateDate(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Test the last updated transaction time to a single selected value.
 *
 * Creation date: (9/09/2004 8:24:29 AM)
 */
public static String sqlSingleTranUpdateTime(FormDefinition searchCell) {

	String sqlSelect = "";	
	
	try {		

		if ((searchCell.getStatusCode() != null) && (searchCell.getStatusCode().toUpperCase().equals("R"))) {
		if  (searchCell.getTimeLow() != null) {		   
			
			sqlSelect = sqlSelect + "(FMCTIME = '" + searchCell.getTimeLow() + "') AND ";			  
		}
		}

	}
	catch (Exception e) {
		System.out.println("Exception error FormData.sqlSingleTranUpdateTime(Class): " + e);
	}	
	
	return sqlSelect;	
}
/**
 * Retrieve the form data presentation attribute.
 *
 * Creation date: (11/11/2003 4:45:28 PM)
 */
public String getDataAttribute() {

	return dataAttribute;
}
/**
 * Retrieve the form data class value.
 *
 * Creation date: (8/23/2004 4:45:28 PM)
 */
public Vector getDataClass() {

	return dataClass;
}
/**
 * Retrieve the form data class name attribute.
 *
 * Creation date: (8/23/2004 4:45:28 PM)
 */
public Vector getDataClassName() {

	return dataClassName;
}
/**
 * Retrieve the form data text value.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getDataComment() {

	return dataComment;
}
/**
 * Retrieve the form data date value. 
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Date getDataDate() {

	return dataDate;	
}
/**
 * Retrieve the form data defaulted value code.
 *
 * Creation date: (11/17/2003 4:45:28 PM)
 */
public String getDataDefaulted() {

	return dataDefaulted;
}
/**
 * Retrieve the form data description value.
 *
 * Creation date: (10/1/2004 4:45:28 PM)
 */
public Vector getDataDescription() {

	return dataDescription;
}
/**
 * Retrieve the form data numeric value.
 *
 * Creation date: (8/12/2003 10:53:28 AM)
 */
public BigDecimal getDataNumeric() 
{
	return dataNumeric;
}
/**
 * Retrieve the form data text value.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getDataText() {

	return dataText;
}
/**
 * Retrieve the form data time value. 
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Time getDataTime() {

	return dataTime;	
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
 * Retrieve the record identification code (data comment).
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getRecordCmtId() {

	return recordCmtId;	
}
/**
 * Retrieve the record identification code (data defaulted).
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getRecordDftId() {

	return recordDftId;	
}
/**
 * Retrieve the record identification code (data value).
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getRecordDtaId() {

	return recordDtaId;	
}
/**
 * Retrieve the from data transaction date. 
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Date getTranEffDate() {

	return tranEffDate;	
}
/**
 * Retrieve the form data transaction number.
 *
 * Creation date: (8/12/2003 10:53:28 AM)
 */
public Integer getTranNumber() 
{
	return tranNumber;	
}
/**
 * Retrieve the last update date. 
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Date getUpdateDate() {

	return updateDate;	
}
/**
 * Retrieve the last update time. 
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Time getUpdateTime() {

	return updateTime;	
}
/**
 * Retrieve the last update user profile.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getUpdateUser() {

	return updateUser;	
}
/**
 * Retrieve the last update user profile name.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getUpdateUserName() {

	return updateUserName;	
}
/**
 * SQL definitions.
 *
 * Creation date: (8/15/2003 8:24:29 AM)
 */
public void init() {	
	 
	
	// Test for prior initialization.
	
	if (persists == false) {	
	    persists = true;	   
	    

	// Perform initialization.
	 
	try {
   // 9/22/08 TWalton - Change from Connection Pool to Connection Stack.
		Connection conn1 = ConnectionStack.getConnection();
		Connection conn2 = ConnectionStack.getConnection();
		Connection conn3 = ConnectionStack.getConnection();
		Connection conn4 = ConnectionStack.getConnection();
		Connection conn5 = ConnectionStack.getConnection();	
		Connection conn6 = ConnectionStack.getConnection();			
		

		// data -----------------
		
		String deleteData = 
			  "DELETE FROM " + library + "FMPCDATA " +
			  "WHERE FMCFRMNBR = ? AND FMCTRNNBR = ?";			 

		PreparedStatement deleteData1 = conn1.prepareStatement(deleteData);		
		PreparedStatement deleteData2 = conn2.prepareStatement(deleteData);		 
		PreparedStatement deleteData3 = conn3.prepareStatement(deleteData);		 
		PreparedStatement deleteData4 = conn4.prepareStatement(deleteData);		 
		PreparedStatement deleteData5 = conn5.prepareStatement(deleteData);	
		PreparedStatement deleteData6 = conn6.prepareStatement(deleteData);	 

		sqlDeleteData = new Stack();		
		sqlDeleteData.push(deleteData1);		
		sqlDeleteData.push(deleteData2);
		sqlDeleteData.push(deleteData3);
		sqlDeleteData.push(deleteData4);
		sqlDeleteData.push(deleteData5);
		sqlDeleteData.push(deleteData6);

		
		String insertData =     			
		      "INSERT INTO " + library + "FMPCDATA " +
			  "VALUES (?, ?, ?, ?, ?, ?, ?, " +
			          "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			          "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			          "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			          "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			          "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement insertData1 = conn1.prepareStatement(insertData);
		PreparedStatement insertData2 = conn2.prepareStatement(insertData);
		PreparedStatement insertData3 = conn3.prepareStatement(insertData);
		PreparedStatement insertData4 = conn4.prepareStatement(insertData);
		PreparedStatement insertData5 = conn5.prepareStatement(insertData);
		PreparedStatement insertData6 = conn6.prepareStatement(insertData);

		sqlInsertData = new Stack();
		sqlInsertData.push(insertData1);
		sqlInsertData.push(insertData2);
		sqlInsertData.push(insertData3);
		sqlInsertData.push(insertData4);
		sqlInsertData.push(insertData5);
		sqlInsertData.push(insertData6);			
 

		String updateData = 
			  "UPDATE " + library + "FMPCDATA " +
			  "SET FMCRECORD = ?, FMCFRMNBR = ?, FMCTRNNBR = ?, FMCTRNDTE = ?," +
			     " FMCUSER   = ?, FMCDATE   = ?, FMCTIME   = ?," +
			     " FMCNU01 = ?, FMCNU02 = ?, FMCNU03 = ?, FMCNU04 = ?, FMCNU05 = ?," +
			     " FMCNU06 = ?, FMCNU07 = ?, FMCNU08 = ?, FMCNU09 = ?, FMCNU10 = ?," +
			     " FMCNU11 = ?, FMCNU12 = ?, FMCNU13 = ?, FMCNU14 = ?, FMCNU15 = ?," +
			     " FMCNU16 = ?, FMCNU17 = ?, FMCNU18 = ?, FMCNU19 = ?, FMCNU20 = ?," +
			     " FMCNU21 = ?, FMCNU22 = ?, FMCNU23 = ?, FMCNU24 = ?, FMCNU25 = ?," +
			     " FMCNU26 = ?, FMCNU27 = ?, FMCNU28 = ?, FMCNU29 = ?, FMCNU30 = ?," +
			     " FMCDT01 = ?, FMCDT02 = ?, FMCDT03 = ?, FMCDT04 = ?, FMCDT05 = ?," +
			     " FMCDT06 = ?, FMCDT07 = ?, FMCDT08 = ?, FMCDT09 = ?, FMCDT10 = ?," +
			     " FMCDT11 = ?, FMCDT12 = ?, FMCDT13 = ?, FMCDT14 = ?, FMCDT15 = ?," +
			     " FMCDT16 = ?, FMCDT17 = ?, FMCDT18 = ?, FMCDT19 = ?, FMCDT20 = ?," +
			     " FMCTM01 = ?, FMCTM02 = ?, FMCTM03 = ?, FMCTM04 = ?, FMCTM05 = ?," +
			     " FMCTM06 = ?, FMCTM07 = ?, FMCTM08 = ?, FMCTM09 = ?, FMCTM10 = ?," +
			     " FMCTM11 = ?, FMCTM12 = ?, FMCTM13 = ?, FMCTM14 = ?, FMCTM15 = ?," +
			     " FMCTM16 = ?, FMCTM17 = ?, FMCTM18 = ?, FMCTM19 = ?, FMCTM20 = ?," +
			     " FMCTX01 = ?, FMCTX02 = ?, FMCTX03 = ?, FMCTX04 = ?, FMCTX05 = ?," +
			     " FMCTX06 = ?, FMCTX07 = ?, FMCTX08 = ?, FMCTX09 = ?, FMCTX10 = ?," +
			     " FMCTX11 = ?, FMCTX12 = ?, FMCTX13 = ?, FMCTX14 = ?, FMCTX15 = ?," +
			     " FMCTX16 = ?, FMCTX17 = ?, FMCTX18 = ?, FMCTX19 = ?, FMCTX20 = ?," +
			     " FMCTX21 = ?, FMCTX22 = ?, FMCTX23 = ?, FMCTX24 = ?, FMCTX25 = ?," +
			     " FMCTX26 = ?, FMCTX27 = ?, FMCTX28 = ?, FMCTX29 = ?, FMCTX30 = ? " +
			  "WHERE FMCFRMNBR = ? AND FMCTRNNBR = ?";

		PreparedStatement updateData1 = conn1.prepareStatement(updateData);
		PreparedStatement updateData2 = conn2.prepareStatement(updateData);
		PreparedStatement updateData3 = conn3.prepareStatement(updateData);
		PreparedStatement updateData4 = conn4.prepareStatement(updateData);
		PreparedStatement updateData5 = conn5.prepareStatement(updateData);
		PreparedStatement updateData6 = conn6.prepareStatement(updateData);

		sqlUpdateData = new Stack();
		sqlUpdateData.push(updateData1);
		sqlUpdateData.push(updateData2);
		sqlUpdateData.push(updateData3);
		sqlUpdateData.push(updateData4);
		sqlUpdateData.push(updateData5);
		sqlUpdateData.push(updateData6);	

	
		// comment --------------
		
		String deleteComment = 
			  "DELETE FROM " + library + "FMPDCMNT " +
			  "WHERE FMDFRMNBR = ? AND FMDTRNNBR = ?";

		PreparedStatement deleteComment1 = conn1.prepareStatement(deleteComment);
		PreparedStatement deleteComment2 = conn2.prepareStatement(deleteComment);
		PreparedStatement deleteComment3 = conn3.prepareStatement(deleteComment);
		PreparedStatement deleteComment4 = conn4.prepareStatement(deleteComment);
		PreparedStatement deleteComment5 = conn5.prepareStatement(deleteComment);
		PreparedStatement deleteComment6 = conn6.prepareStatement(deleteComment);

		sqlDeleteComment = new Stack();
		sqlDeleteComment.push(deleteComment1);
		sqlDeleteComment.push(deleteComment2);
		sqlDeleteComment.push(deleteComment3);
		sqlDeleteComment.push(deleteComment4);
		sqlDeleteComment.push(deleteComment5);
		sqlDeleteComment.push(deleteComment6);
		

		String insertComment =
			  "INSERT INTO " + library + "FMPDCMNT " +
			  "VALUES (?, ?, ?, ?)";

		PreparedStatement insertComment1 = conn1.prepareStatement(insertComment);
		PreparedStatement insertComment2 = conn2.prepareStatement(insertComment);
		PreparedStatement insertComment3 = conn3.prepareStatement(insertComment);
		PreparedStatement insertComment4 = conn4.prepareStatement(insertComment);
		PreparedStatement insertComment5 = conn5.prepareStatement(insertComment);
		PreparedStatement insertComment6 = conn6.prepareStatement(insertComment);

		sqlInsertComment = new Stack();
		sqlInsertComment.push(insertComment1);
		sqlInsertComment.push(insertComment2);
		sqlInsertComment.push(insertComment3);
		sqlInsertComment.push(insertComment4);
		sqlInsertComment.push(insertComment5);
		sqlInsertComment.push(insertComment6);
		   

		String updateComment = 
			  "UPDATE " + library + "FMPDCMNT " +
			  "SET FMDRECORD = ?, FMDFRMNBR = ?, FMDTRNNBR = ?, FMDCOMENT = ? " +
			  "WHERE FMDFRMNBR = ? AND FMDTRNNBR = ?";

		PreparedStatement updateComment1 = conn1.prepareStatement(updateComment);
		PreparedStatement updateComment2 = conn2.prepareStatement(updateComment);
		PreparedStatement updateComment3 = conn3.prepareStatement(updateComment);
		PreparedStatement updateComment4 = conn4.prepareStatement(updateComment);
		PreparedStatement updateComment5 = conn5.prepareStatement(updateComment);
		PreparedStatement updateComment6 = conn6.prepareStatement(updateComment);

		sqlUpdateComment = new Stack();
		sqlUpdateComment.push(updateComment1);
		sqlUpdateComment.push(updateComment2);
		sqlUpdateComment.push(updateComment3);
		sqlUpdateComment.push(updateComment4);
		sqlUpdateComment.push(updateComment5);
		sqlUpdateComment.push(updateComment6);
		

		// defaulted ------------
		
		String deleteDefaulted = 
			  "DELETE FROM " + library + "FMPHDEFT " +
			  "WHERE FMHFRMNBR = ? AND FMHTRNNBR = ?";			 

		PreparedStatement deleteDefaulted1 = conn1.prepareStatement(deleteDefaulted);		
		PreparedStatement deleteDefaulted2 = conn2.prepareStatement(deleteDefaulted);		 
		PreparedStatement deleteDefaulted3 = conn3.prepareStatement(deleteDefaulted);		 
		PreparedStatement deleteDefaulted4 = conn4.prepareStatement(deleteDefaulted);		 
		PreparedStatement deleteDefaulted5 = conn5.prepareStatement(deleteDefaulted);	
		PreparedStatement deleteDefaulted6 = conn6.prepareStatement(deleteDefaulted);	 

		sqlDeleteDefaulted = new Stack();		
		sqlDeleteDefaulted.push(deleteDefaulted1);		
		sqlDeleteDefaulted.push(deleteDefaulted2);
		sqlDeleteDefaulted.push(deleteDefaulted3);
		sqlDeleteDefaulted.push(deleteDefaulted4);
		sqlDeleteDefaulted.push(deleteDefaulted5);
		sqlDeleteDefaulted.push(deleteDefaulted6);

		
		String insertDefaulted =     			
		      "INSERT INTO " + library + "FMPHDEFT " +
			  "VALUES (?, ?, ?, " +
			          "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			          "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			          "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			          "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			          "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement insertDefaulted1 = conn1.prepareStatement(insertDefaulted);
		PreparedStatement insertDefaulted2 = conn2.prepareStatement(insertDefaulted);
		PreparedStatement insertDefaulted3 = conn3.prepareStatement(insertDefaulted);
		PreparedStatement insertDefaulted4 = conn4.prepareStatement(insertDefaulted);
		PreparedStatement insertDefaulted5 = conn5.prepareStatement(insertDefaulted);
		PreparedStatement insertDefaulted6 = conn6.prepareStatement(insertDefaulted);

		sqlInsertDefaulted = new Stack();
		sqlInsertDefaulted.push(insertDefaulted1);
		sqlInsertDefaulted.push(insertDefaulted2);
		sqlInsertDefaulted.push(insertDefaulted3);
		sqlInsertDefaulted.push(insertDefaulted4);
		sqlInsertDefaulted.push(insertDefaulted5);
		sqlInsertDefaulted.push(insertDefaulted6);			
 

		String updateDefaulted = 
			  "UPDATE " + library + "FMPHDEFT " +
			  "SET FMHRECORD = ?, FMHFRMNBR = ?, FMHTRNNBR = ?," +			   
			     " FMHNU01 = ?, FMHNU02 = ?, FMHNU03 = ?, FMHNU04 = ?, FMHNU05 = ?," +
			     " FMHNU06 = ?, FMHNU07 = ?, FMHNU08 = ?, FMHNU09 = ?, FMHNU10 = ?," +
			     " FMHNU11 = ?, FMHNU12 = ?, FMHNU13 = ?, FMHNU14 = ?, FMHNU15 = ?," +
			     " FMHNU16 = ?, FMHNU17 = ?, FMHNU18 = ?, FMHNU19 = ?, FMHNU20 = ?," +
			     " FMHNU21 = ?, FMHNU22 = ?, FMHNU23 = ?, FMHNU24 = ?, FMHNU25 = ?," +
			     " FMHNU26 = ?, FMHNU27 = ?, FMHNU28 = ?, FMHNU29 = ?, FMHNU30 = ?," +
			     " FMHDT01 = ?, FMHDT02 = ?, FMHDT03 = ?, FMHDT04 = ?, FMHDT05 = ?," +
			     " FMHDT06 = ?, FMHDT07 = ?, FMHDT08 = ?, FMHDT09 = ?, FMHDT10 = ?," +
			     " FMHDT11 = ?, FMHDT12 = ?, FMHDT13 = ?, FMHDT14 = ?, FMHDT15 = ?," +
			     " FMHDT16 = ?, FMHDT17 = ?, FMHDT18 = ?, FMHDT19 = ?, FMHDT20 = ?," +
			     " FMHTM01 = ?, FMHTM02 = ?, FMHTM03 = ?, FMHTM04 = ?, FMHTM05 = ?," +
			     " FMHTM06 = ?, FMHTM07 = ?, FMHTM08 = ?, FMHTM09 = ?, FMHTM10 = ?," +
			     " FMHTM11 = ?, FMHTM12 = ?, FMHTM13 = ?, FMHTM14 = ?, FMHTM15 = ?," +
			     " FMHTM16 = ?, FMHTM17 = ?, FMHTM18 = ?, FMHTM19 = ?, FMHTM20 = ?," +
			     " FMHTX01 = ?, FMHTX02 = ?, FMHTX03 = ?, FMHTX04 = ?, FMHTX05 = ?," +
			     " FMHTX06 = ?, FMHTX07 = ?, FMHTX08 = ?, FMHTX09 = ?, FMHTX10 = ?," +
			     " FMHTX11 = ?, FMHTX12 = ?, FMHTX13 = ?, FMHTX14 = ?, FMHTX15 = ?," +
			     " FMHTX16 = ?, FMHTX17 = ?, FMHTX18 = ?, FMHTX19 = ?, FMHTX20 = ?," +
			     " FMHTX21 = ?, FMHTX22 = ?, FMHTX23 = ?, FMHTX24 = ?, FMHTX25 = ?," +
			     " FMHTX26 = ?, FMHTX27 = ?, FMHTX28 = ?, FMHTX29 = ?, FMHTX30 = ? " +
			  "WHERE FMHFRMNBR = ? AND FMHTRNNBR = ?";

		PreparedStatement updateDefaulted1 = conn1.prepareStatement(updateDefaulted);
		PreparedStatement updateDefaulted2 = conn2.prepareStatement(updateDefaulted);
		PreparedStatement updateDefaulted3 = conn3.prepareStatement(updateDefaulted);
		PreparedStatement updateDefaulted4 = conn4.prepareStatement(updateDefaulted);
		PreparedStatement updateDefaulted5 = conn5.prepareStatement(updateDefaulted);
		PreparedStatement updateDefaulted6 = conn6.prepareStatement(updateDefaulted);

		sqlUpdateDefaulted = new Stack();
		sqlUpdateDefaulted.push(updateDefaulted1);
		sqlUpdateDefaulted.push(updateDefaulted2);
		sqlUpdateDefaulted.push(updateDefaulted3);
		sqlUpdateDefaulted.push(updateDefaulted4);
		sqlUpdateDefaulted.push(updateDefaulted5);
		sqlUpdateDefaulted.push(updateDefaulted6);		
		
		
		// SQL selection --------			

		String dataByFormByEntry = 						// Cells ordered by entry sequence
		  	  "SELECT *  " +
			             "FROM (" + library + "FMPBFDEF JOIN " + library + "FMPAFHDR" +
			                  "  ON FMBNBR = FMANBR)" +
			             " JOIN " + library + "FMPCDATA" +
			                  "  ON FMBNBR = FMCFRMNBR" +
			  " LEFT OUTER JOIN " + library + "FMPDCMNT" +
			                  "  ON FMCFRMNBR = FMDFRMNBR AND FMCTRNNBR = FMDTRNNBR" +
			  " LEFT OUTER JOIN " + library + "FMPHDEFT" +
			                    "  ON FMCFRMNBR = FMHFRMNBR AND FMCTRNNBR = FMHTRNNBR" +
			  " WHERE FMBNBR = ?" +
		      " ORDER BY FMCTRNDTE, FMCTRNNBR, FMBLOE, FMBCOL";

		PreparedStatement dataByFormByEntry1 = conn1.prepareStatement(dataByFormByEntry);
		PreparedStatement dataByFormByEntry2 = conn2.prepareStatement(dataByFormByEntry);
		PreparedStatement dataByFormByEntry3 = conn3.prepareStatement(dataByFormByEntry);
		PreparedStatement dataByFormByEntry4 = conn4.prepareStatement(dataByFormByEntry);
		PreparedStatement dataByFormByEntry5 = conn5.prepareStatement(dataByFormByEntry);
		PreparedStatement dataByFormByEntry6 = conn6.prepareStatement(dataByFormByEntry);

		findDataByFormByEntry = new Stack();
		findDataByFormByEntry.push(dataByFormByEntry1);
		findDataByFormByEntry.push(dataByFormByEntry2);
		findDataByFormByEntry.push(dataByFormByEntry3);
		findDataByFormByEntry.push(dataByFormByEntry4);
		findDataByFormByEntry.push(dataByFormByEntry5);
		findDataByFormByEntry.push(dataByFormByEntry6);
		
		
		String dataByFormByView =						// Cells ordered by viewing sequence
			  "SELECT *  " +
						 "FROM (" + library + "FMPBFDEF JOIN " + library + "FMPAFHDR" +
								"  ON FMBNBR = FMANBR)" +
						 " JOIN " + library + "FMPCDATA" +
								"  ON FMBNBR = FMCFRMNBR" +
			  " LEFT OUTER JOIN " + library + "FMPDCMNT" +
								"  ON FMCFRMNBR = FMDFRMNBR AND FMCTRNNBR = FMDTRNNBR" +
			  " LEFT OUTER JOIN " + library + "FMPHDEFT" +
								"  ON FMCFRMNBR = FMHFRMNBR AND FMCTRNNBR = FMHTRNNBR" +                  
			  " WHERE FMBNBR = ?" +
			  " ORDER BY FMCTRNDTE, FMCTRNNBR, FMBLOV, FMBCOL";

		PreparedStatement dataByFormByView1 = conn1.prepareStatement(dataByFormByView);
		PreparedStatement dataByFormByView2 = conn2.prepareStatement(dataByFormByView);
		PreparedStatement dataByFormByView3 = conn3.prepareStatement(dataByFormByView);
		PreparedStatement dataByFormByView4 = conn4.prepareStatement(dataByFormByView);
		PreparedStatement dataByFormByView5 = conn5.prepareStatement(dataByFormByView);
		PreparedStatement dataByFormByView6 = conn6.prepareStatement(dataByFormByView);

		findDataByFormByView = new Stack();
		findDataByFormByView.push(dataByFormByView1);
		findDataByFormByView.push(dataByFormByView2);
		findDataByFormByView.push(dataByFormByView3);
		findDataByFormByView.push(dataByFormByView4);
		findDataByFormByView.push(dataByFormByView5);
		findDataByFormByView.push(dataByFormByView6);
		
		
		String dataByTranByDesc = 						// Cells ordered by description sequence
			  "SELECT *  " +
						 "FROM (" + library + "FMPBFDEF JOIN " + library + "FMPAFHDR" +
								"  ON FMBNBR = FMANBR)" +
						 " JOIN " + library + "FMPCDATA" +
								"  ON FMBNBR = FMCFRMNBR" +
			  " LEFT OUTER JOIN " + library + "FMPDCMNT" +
								"  ON FMCFRMNBR = FMDFRMNBR AND FMCTRNNBR = FMDTRNNBR" +
			  " LEFT OUTER JOIN " + library + "FMPHDEFT" +
								"  ON FMCFRMNBR = FMHFRMNBR AND FMCTRNNBR = FMHTRNNBR" +
			  " WHERE FMBNBR = ? AND FMCTRNNBR = ? AND FMBLOD > 0" +
			  " ORDER BY FMBLOD, FMBCOL";

		PreparedStatement dataByTranByDesc1 = conn1.prepareStatement(dataByTranByDesc);
		PreparedStatement dataByTranByDesc2 = conn2.prepareStatement(dataByTranByDesc);
		PreparedStatement dataByTranByDesc3 = conn3.prepareStatement(dataByTranByDesc);
		PreparedStatement dataByTranByDesc4 = conn4.prepareStatement(dataByTranByDesc);
		PreparedStatement dataByTranByDesc5 = conn5.prepareStatement(dataByTranByDesc);
		PreparedStatement dataByTranByDesc6 = conn6.prepareStatement(dataByTranByDesc);

		findDataByTranByDesc = new Stack();
		findDataByTranByDesc.push(dataByTranByDesc1);
		findDataByTranByDesc.push(dataByTranByDesc2);
		findDataByTranByDesc.push(dataByTranByDesc3);
		findDataByTranByDesc.push(dataByTranByDesc4);
		findDataByTranByDesc.push(dataByTranByDesc5);
		findDataByTranByDesc.push(dataByTranByDesc6);
		
		
	
		String dataByTranByEntry = 						// Cells ordered by entry sequence
			  "SELECT *  " +
			             "FROM (" + library + "FMPBFDEF JOIN " + library + "FMPAFHDR" +
			                    "  ON FMBNBR = FMANBR)" +
			             " JOIN " + library + "FMPCDATA" +
			                    "  ON FMBNBR = FMCFRMNBR" +
			  " LEFT OUTER JOIN " + library + "FMPDCMNT" +
			                    "  ON FMCFRMNBR = FMDFRMNBR AND FMCTRNNBR = FMDTRNNBR" +
			  " LEFT OUTER JOIN " + library + "FMPHDEFT" +
			                    "  ON FMCFRMNBR = FMHFRMNBR AND FMCTRNNBR = FMHTRNNBR" +
			  " WHERE FMBNBR = ? AND FMCTRNNBR = ? AND FMBLOE > 0" +
		      " ORDER BY FMBLOE, FMBCOL";

		PreparedStatement dataByTranByEntry1 = conn1.prepareStatement(dataByTranByEntry);
		PreparedStatement dataByTranByEntry2 = conn2.prepareStatement(dataByTranByEntry);
		PreparedStatement dataByTranByEntry3 = conn3.prepareStatement(dataByTranByEntry);
		PreparedStatement dataByTranByEntry4 = conn4.prepareStatement(dataByTranByEntry);
		PreparedStatement dataByTranByEntry5 = conn5.prepareStatement(dataByTranByEntry);
		PreparedStatement dataByTranByEntry6 = conn6.prepareStatement(dataByTranByEntry);

		findDataByTranByEntry = new Stack();
		findDataByTranByEntry.push(dataByTranByEntry1);
		findDataByTranByEntry.push(dataByTranByEntry2);
		findDataByTranByEntry.push(dataByTranByEntry3);
		findDataByTranByEntry.push(dataByTranByEntry4);
		findDataByTranByEntry.push(dataByTranByEntry5);
		findDataByTranByEntry.push(dataByTranByEntry6);
		
		
		String dataByTranByView = 						// Cells ordered by view sequence
			  "SELECT *  " +
						 "FROM (" + library + "FMPBFDEF JOIN " + library + "FMPAFHDR" +
								"  ON FMBNBR = FMANBR)" +
						 " JOIN " + library + "FMPCDATA" +
								"  ON FMBNBR = FMCFRMNBR" +
			  " LEFT OUTER JOIN " + library + "FMPDCMNT" +
								"  ON FMCFRMNBR = FMDFRMNBR AND FMCTRNNBR = FMDTRNNBR" +
			  " LEFT OUTER JOIN " + library + "FMPHDEFT" +
								"  ON FMCFRMNBR = FMHFRMNBR AND FMCTRNNBR = FMHTRNNBR" +
			  " WHERE FMBNBR = ? AND FMCTRNNBR = ? AND FMBLOV > 0" +
			  " ORDER BY FMBLOV, FMBCOL";

		PreparedStatement dataByTranByView1 = conn1.prepareStatement(dataByTranByView);
		PreparedStatement dataByTranByView2 = conn2.prepareStatement(dataByTranByView);
		PreparedStatement dataByTranByView3 = conn3.prepareStatement(dataByTranByView);
		PreparedStatement dataByTranByView4 = conn4.prepareStatement(dataByTranByView);
		PreparedStatement dataByTranByView5 = conn5.prepareStatement(dataByTranByView);
		PreparedStatement dataByTranByView6 = conn6.prepareStatement(dataByTranByView);

		findDataByTranByView = new Stack();
		findDataByTranByView.push(dataByTranByView1);
		findDataByTranByView.push(dataByTranByView2);
		findDataByTranByView.push(dataByTranByView3);
		findDataByTranByView.push(dataByTranByView4);
		findDataByTranByView.push(dataByTranByView5);
		findDataByTranByView.push(dataByTranByView6);
		
		// Return the connections back to the pool.
		
		ConnectionStack.returnConnection(conn1);
		ConnectionStack.returnConnection(conn2);
		ConnectionStack.returnConnection(conn3);
		ConnectionStack.returnConnection(conn4);
		ConnectionStack.returnConnection(conn5);
		ConnectionStack.returnConnection(conn6);
		
	}				

    catch (SQLException e) {
		System.out.println("SQL exception occured at com.treetop.data.FormData.init()" + e);
    }    
    	
	}
	
}
/**
 * Insert data records in the Form System for Values, Defaulted and Comments.
 *
 * Creation date: (8/15/2003 1:50:29 PM)
 */
public Exception insert(Vector inInsertInfo) {

	int element = 0;
		
	try {			

		// Create arrays for each type of data. Set all elements to there default values.
		
		BigDecimal[]    dataNU;
		java.sql.Date[] dataDT;
		java.sql.Time[] dataTM;
		String[]        dataTX;
		
		String[]        defaultedNU;
		String[]        defaultedDT;
		String[]        defaultedTM;
		String[]        defaultedTX; 
			
		dataNU = new BigDecimal[binNumeric];
		dataDT = new java.sql.Date[binDate];
		dataTM = new java.sql.Time[binTime];
		dataTX = new String[binText];
		
		defaultedNU = new String[binNumeric];
		defaultedDT = new String[binDate];
		defaultedTM = new String[binTime];
		defaultedTX = new String[binText];
		
     
        for (int i = 0; i < dataNU.length; i++) {
            dataNU[i]      = new BigDecimal(0);
            defaultedNU[i] = "Y";
        }
        for (int i = 0; i < dataDT.length; i++) {
            dataDT[i]      = defaultDate;
            defaultedDT[i] = "Y";
        }
        for (int i = 0; i < dataTM.length; i++) {
            dataTM[i]      = defaultTime;
            defaultedTM[i] = "Y";
        }
        for (int i = 0; i < dataTX.length; i++) {
            dataTX[i]      = null;
            defaultedTX[i] = "Y";
        }   
         
        
        // Build data class from the vector element.
        
        for (int x = 0; x < inInsertInfo.size(); x ++)
		{	
			 
		    FormData insertInfo = (FormData) inInsertInfo.elementAt(x);
		    if (!insertInfo.recordDtaId.equals("")) {
			    element = x;
		         
       
		    // Load data into array using the position of the data in the record less 1 for the array.
		
		    int index     = 0;
		    int binNumber = insertInfo.dataNumber.intValue();
		
		    if (binNumber == 0) 
		        index = updateNextIndex(insertInfo.formNumber, insertInfo.columnNumber);
	        else
	            index = binNumber;
			
		    index = index - 1;	
		
		    if (insertInfo.dataCode.equals("NU")) { 		 	 		       
		            dataNU[index]      = insertInfo.dataNumeric;
		            defaultedNU[index] = insertInfo.dataDefaulted;
		        }
		    
		        if (insertInfo.dataCode.equals("DT")) {
		            dataDT[index]      = insertInfo.dataDate;
		            defaultedDT[index] = insertInfo.dataDefaulted;
		        }
		    
		        if (insertInfo.dataCode.equals("TM")) {
		            dataTM[index]      = insertInfo.dataTime;
		            defaultedTM[index] = insertInfo.dataDefaulted;		        
		        }
		    
		        if (insertInfo.dataCode.equals("TX")) {
		            dataTX[index]      = insertInfo.dataText;
		            defaultedTX[index] = insertInfo.dataDefaulted;
		        }		    
		 
		    }
		    
		}
		
		

		// Insert data base record with class data (values).

		try {

		    if (element != 0) {
		        FormData insertInfo = (FormData) inInsertInfo.elementAt(element);

		        PreparedStatement insertData = (PreparedStatement) sqlInsertData.pop();
		
		        insertData.setString(1, insertInfo.recordDtaId);
		        insertData.setInt(2, insertInfo.formNumber.intValue());
		        insertData.setInt(3, insertInfo.tranNumber.intValue());
		        insertData.setDate(4, insertInfo.tranEffDate);
		        insertData.setString(5, insertInfo.updateUser);
		        insertData.setDate(6, insertInfo.updateDate);
		        insertData.setTime(7, insertInfo.updateTime);

		        int index = 7;
		    
			    for(int i = 0; i < binNumeric; i++) {
				    index = index + 1;
				    insertData.setBigDecimal(index, dataNU[i]);
			    }

			    for(int i = 0; i < binDate; i++) {
				    index = index + 1;
				    insertData.setDate(index, dataDT[i]);
			    }

			    for(int i = 0; i < binTime; i++) {
				    index = index + 1;
				    insertData.setTime(index, dataTM[i]);
			    }

			    for(int i = 0; i < binText; i++) {
				    index = index + 1;
				    insertData.setString(index, dataTX[i]);
			    }	
			    
		
		        insertData.executeUpdate();

		        sqlInsertData.push(insertData);
		    }
				
	    }		
	    catch (Exception e) {	
		    System.out.println("SQL error at com.treetop.data.FormData.insert.data(Vector): " + e);
		    return e;
	    }	

	
	 
	    // Insert data base record with class data (defaulted).
	    
	    try {	
		
		    if (element != 0) {
		        FormData insertInfo = (FormData) inInsertInfo.elementAt(element);

		        PreparedStatement insertDefaulted = (PreparedStatement) sqlInsertDefaulted.pop();
		        
		        if ((recordDftId != null) && (!recordDftId.equals("")) && (!recordDftId.equals("DD")))
		            insertDefaulted.setString(1, insertInfo.recordDftId);
		        else        
			        insertDefaulted.setString(1, "DD");
		        
		        insertDefaulted.setInt(2, insertInfo.formNumber.intValue());
		        insertDefaulted.setInt(3, insertInfo.tranNumber.intValue());
		    
		        int index = 3;
		    
			    for(int i = 0; i < binNumeric; i++) {
				    index = index + 1;
				    insertDefaulted.setString(index, defaultedNU[i]);
			    }

			    for(int i = 0; i < binDate; i++) {
				    index = index + 1;
				    insertDefaulted.setString(index, defaultedDT[i]);
			    }

			    for(int i = 0; i < binTime; i++) {
				    index = index + 1;
				    insertDefaulted.setString(index, defaultedTM[i]);
			    }

			    for(int i = 0; i < binText; i++) {
				    index = index + 1;
				    insertDefaulted.setString(index, defaultedTX[i]);
			    }	
		
		        		
		        insertDefaulted.executeUpdate();

		        sqlInsertDefaulted.push(insertDefaulted);
		    }
		
		}		
	    catch (Exception e) {	
		    System.out.println("SQL error at com.treetop.data.FormData.insert.defaulted(Vector): " + e);		
		    return e;
	    }



	    // Insert data base record with class data (comment).
	    
	    try {
		    
		    if (element != 0) {
		        FormData insertInfo = (FormData) inInsertInfo.elementAt(element);
		        if (!insertInfo.recordCmtId.equals("")) {
			        PreparedStatement insertComment = (PreparedStatement) sqlInsertComment.pop();
		            insertComment.setString(1, insertInfo.recordCmtId);
		            insertComment.setInt(2, insertInfo.formNumber.intValue());
		            insertComment.setInt(4, insertInfo.tranNumber.intValue());
		            insertComment.setString(5, insertInfo.dataComment);		
		            insertComment.executeUpdate();
		            sqlInsertComment.push(insertComment);
		        }
		    }
		    	
	    }	
	    catch (Exception e) {	
		    System.out.println("SQL error at com.treetop.data.FormData.insert.comment(Vector): " + e);
		    return e;
	    }   

	    
	    

    }	
	catch (Exception e) {	
		System.out.println("SQL error at com.treetop.data.FormData.insert(Vector): " + e);
		return e;
	}	

	return null;
}
/**
 * Load fields from data base record.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
protected void loadFields(ResultSet rs, String loadMode, String joinClass) {
	
	super.loadFields(rs, loadMode);

	loadFieldsData(rs, loadMode, joinClass);		
					
}
/**
 * Load fields from data base record.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
protected void loadFieldsData(ResultSet rs, String loadMode, String joinClass) {

	String defaultedField = "Y";
	
	try {	
							
	  	recordDtaId       	= rs.getString("FMCRECORD");
	  	recordCmtId       	= rs.getString("FMDRECORD");
	  	recordDftId       	= rs.getString("FMHRECORD");
	  	formNumber        	= new Integer(rs.getInt("FMCFRMNBR"));
	    tranNumber        	= new Integer(rs.getInt("FMCTRNNBR"));
	  	tranEffDate       	= rs.getDate("FMCTRNDTE");		
	  	updateUser        	= rs.getString("FMCUSER");
	  	updateUserName    	= updateUser;
	  	updateDate        	= rs.getDate("FMCDATE");
	  	updateTime        	= rs.getTime("FMCTIME");	
	  	dataAttribute     	= "";	
														

	  	try {

	  		dataNumeric     = new BigDecimal(0);
	  		dataDate        = defaultDate;
	  		dataTime        = defaultTime;
	  		dataText        = null;
	  		dataClass       = new Vector();
	  		dataClassName   = new Vector();
	  		dataDescription = new Vector();
	  		dataDefaulted   = defaultedField;
							
			
	  		if ((formula == null) || (formula.trim().equals(""))) {
	  			
				String dataField = buildDataFieldName(columnNumber, dataCode, dataNumber);	       
	    
	  	        if (dataCode.equals ("NU")) 			       
	  		        dataNumeric = rs.getBigDecimal(dataField);		        
		       		    
	  	        if (dataCode.equals ("DT"))			        
	  		        dataDate = rs.getDate(dataField);		        
		       
	  	        if (dataCode.equals ("TM")) 			      
	  		        dataTime = rs.getTime(dataField);		        
		       		
	  	        if (dataCode.equals ("TX")) 			      
	  		        dataText = rs.getString(dataField);	
	  		        
	  		    defaultedField = buildDataFieldDefault(columnNumber, dataCode, dataNumber);
	  		    dataDefaulted  = rs.getString(defaultedField); 
		        
	  		}
	  		
			if ((buildClass.toLowerCase().equals ("y")) && (dataDefaulted.toLowerCase().equals ("n")))
				loadFieldsJoin(rs, joinClass);		
			
			
	  	}
	  	catch (Exception e) {
	  		System.out.println("Exception at com.treetop.data.FormData" +
	  		                   ".loadFieldsData(RS String String) data: " + e);
	  	} 
		
		
	  	try {

	  		if (recordCmtId != null && recordCmtId.equals("DC"))
	  		    dataComment   = rs.getString("FMDCOMENT");
	  		else {
	  		    dataComment   = "";
	  		    recordCmtId   = "";
	  		}
			
	  	}
	  	catch (Exception e) {	  		
	  		System.out.println("Exception at com.treetop.data.FormData" +
	  		                   ".loadFieldsData(RS String String) comment: " + e);	
	  	} 

		
	  	try {
		
	  		if (loadMode.equals ("all")) {
	  		UserFile newUser    = new UserFile(updateUser);
	  		Integer  newUserNum = new Integer(newUser.getUserNumber());
	  		newUser = new UserFile(newUserNum);
	  		updateUserName = newUser.getUserNameLong();
	  		}
			
	  	}
	  	catch (Exception e) {	  		
	  		System.out.println("Exception at com.treetop.data.FormData" +
	  		                   ".loadFieldsData(RS String String) user name: " + e);	
	  	}
				
	
	}
	catch (Exception e) {	
		System.out.println("SQL Exception at com.treetop.data.FormData" +
			               ".loadFieldsData(RS String String): " + e);
	}
					
}
/**
 * Load fields from joined data base records.
 *
 * Creation date: (11/15/2004 8:24:29 AM)
 */
protected void loadFieldsJoin(ResultSet rs, String joinClass) {
	
	try {						
	  		
		if ((buildClass.toLowerCase().equals ("y")) && (dataDefaulted.toLowerCase().equals ("n"))) {
				
			// Broker data type.
			
			if (dataType.trim().toLowerCase().equals ("broker")) {		
		// Broker no longer Valid			
									
			}
			
			// Customer data type.			
	  		
			if (dataType.trim().toLowerCase().equals ("customer")) {	
		// Customer will have to look at NEW Movex			
//				if (joinClass.toLowerCase().equals( ("r"))) {			// From result set
//					CustomerBillTo customer = new CustomerBillTo();
//					try {
//						customer = new CustomerBillTo(rs);						
//					}
//					catch (Exception e) {								
//					}
//					dataClass.addElement(customer); 
//					dataClassName.addElement("CustomerBillTo");
//				}
//				if (joinClass.toLowerCase().equals( ("y"))) {			// Use constructor
//					CustomerBillTo customer = new CustomerBillTo();
//					try {
//						if (dataCode.equals ("TX")) { 
//							String[] customerData = decodeCustomer(dataText);
//							customer = new CustomerBillTo(customerData[0], customerData[1]);
//						}											
//					}
//					catch (Exception e) {								
//					}
//					dataClass.addElement(customer); 
//					dataClassName.addElement("CustomerBillTo");
//					}					
			}
			
			// Sales order number data type.
				
			if (dataType.trim().toLowerCase().equals ("order number")) {			
				// Will need to point to Movex if needed	
//				if (joinClass.toLowerCase().equals( ("r"))) {			// From result set
//					SalesOrderHeader header = new SalesOrderHeader();									
//					try {
//						Integer number = new Integer(rs.getInt("ARORD#"));
//						header = new SalesOrderHeader(number);						
//					}
//					catch (Exception e) {								
//					}
//					dataClass.addElement(header); 
//					dataClassName.addElement("SalesOrderHeader");
//				}
//				if (joinClass.toLowerCase().equals( ("y"))) {			// Use constructor
//					SalesOrderHeader header = new SalesOrderHeader();	
//					try {
//						if (dataCode.equals ("NU"))  
//							header = new SalesOrderHeader(dataNumeric);											
//					}
//					catch (Exception e) {								
//					}
//					dataClass.addElement(header); 
//					dataClassName.addElement("SalesOrderHeader");
//				}					
			}
			
			// Resource number data type.
			
			if (dataType.trim().toLowerCase().equals ("resource")) {			
			// will need to change to Item if needed		
//				if (joinClass.toLowerCase().equals( ("r"))) {			// From result set
//					Resource resource = new Resource();									
//					try {
//						String number = rs.getString("RMRESC");
//						resource = new Resource(number);						
//					}
//					catch (Exception e) {								
//					}
//					dataClass.addElement(resource); 
//					dataClassName.addElement("Resource");
//				}
//				if (joinClass.toLowerCase().equals( ("y"))) {			// Use constructor
//					Resource resource = new Resource();		
//					try {
//						if (dataCode.equals ("TX"))  
//							resource = new Resource(dataText);											
//					}
//					catch (Exception e) {								
//					}
//					dataClass.addElement(resource); 
//					dataClassName.addElement("Resource");
//				}					
			}
			
			// Sample request order number data type.
				
			if (dataType.trim().toLowerCase().equals ("sample number")) {			
					
				if (joinClass.toLowerCase().equals( ("r"))) {			// From result set
					SampleRequestOrder sampleRequest = new SampleRequestOrder();									
					try {
						Integer number = new Integer(rs.getInt("SRASR#"));
						sampleRequest  = new SampleRequestOrder(number);						
					}
					catch (Exception e) {								
					}
					dataClass.addElement(sampleRequest); 
					dataClassName.addElement("SampleRequestOrder");
				}
				if (joinClass.toLowerCase().equals( ("y"))) {			// Use constructor
					SampleRequestOrder sampleRequest = new SampleRequestOrder();
					try {
						if (dataCode.equals ("NU"))  
							sampleRequest = new SampleRequestOrder(dataNumeric);										
					}
					catch (Exception e) {								
					}
					dataClass.addElement(sampleRequest); 
					dataClassName.addElement("SampleRequestOrder");
				}					
			}
						
		}
			
	}
	catch (Exception e) {
		System.out.println("Exception at com.treetop.data.FormData" +
						   ".loadFieldsJoin(RS String) data: " + e);
	} 
							
}
/**
 * Form data file testing.
 *
 * Creation date: (8/19/2003 8:24:29 AM)
 */
public static void main(String[] args) {

	if ("x" == "y")
	{
	try {

		Integer length = new Integer("2"); 
		String  value  = "";
		String  result = buildDataFieldSeparator(value, length);
			 
		System.out.println("Separator: " + result + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormData.main() buildDataFieldSeparator: " + e);
	}		
			

	try {
	
		FormData data       = new FormData();
		data.formNumber     = new Integer("20");		
		data.dataCode       = "NU";
		data.dataNumber     = new Integer("55");
		data.columnNumber   = new Integer("777");
		data.dataType       = "broker";
		data.dataNumeric    = new BigDecimal("252");
		data.orderByStyle   = new String[] {"D", "D", "D", "D", "D"};
		data.sortOrderStyle = "desc";
//		data.sortOrderSeq   = new Integer("1");
				
		String   temp = "jsp11";
		String[] role;
		String[] group; 
		String   profile = "DEISEN";
		role  = new String[1];
		role[0]  = "77";
		group = new String[3];
		group[0] = "0";
		group[1] = "1";
		group[2] = "2";
		
		Vector	view  = findDefinitionByFormByTemplate(data.formNumber, temp, role, group, profile);		
		Vector  list  = findDataByValue(data, view);
			 
		System.out.println("findDataByValue successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormData.main() findDataByValue: " + e);
	}
	
	
	try {

		Integer form     = new Integer("26"); 
		String  user     = "thaile";
		String  password = "remy";
		String  name     = findDataByLogonUser(form, user);
			 
		System.out.println("Logon name: " + name + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormData.main() Logon name: " + e);
	}
		
			
	try {

		Integer form = new Integer("21");
		Integer col  = new Integer("322");
		String  list = buildDataDropDownValue(form, col, "", "", "");		
	 	            
		System.out.println("build Drop Down List: " + list + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormData.main() on buildDataDropDown: " + e);		
	}
	

	try {

		FormDefinition cell = new FormDefinition();
		java.sql.Date low   = java.sql.Date.valueOf("2003-08-10");
		java.sql.Date high   = java.sql.Date.valueOf("2003-08-23");		
		Integer column = new Integer("2");
		Integer number = new Integer("11");
		cell.setStatusCode("R");		
		cell.setDateLow(low);
//		cell.setDateHigh(high);
		cell.setColumnNumber(column);
		cell.setDataCode("NU");
		cell.setDataNumber(number);
		String sql = sqlRangeDate(cell);
			 
		System.out.println("Range successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormData.main() Range test: " + e);
	}

	
	try {

		Integer number = new Integer("935");		
		Vector  main   = findDefinitionByFormByForm(number);	
		Vector  join   = buildDataJoinColumn(main);
			 
		System.out.println("Join testing successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormData.main() Join test: " + e);
	}
	

	try {

		Integer value1       = new Integer("16");
		Integer value2       = new Integer("214");
		FormDefinition form1 = new FormDefinition(value1, value2);
		form1.statusCode     = "R";
//		form1.dateLow        = java.sql.Date.valueOf("2003-08-23");
		String  sql          = sqlSingleDate(form1);
			 
		System.out.println("SQL test: " + value1 + " " + value2 + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormData.main() on SQL test: " + e);
	}	
	

	try {
				
		FormData data1 = new FormData();				
				 data1.dataType = "eMail";
				 data1.dataText = "deisenheimtreetop.com";
				 data1.dataCode = "tx";
				 data1.requiredEntry = "R";
		String msg1 = data1.validateDataByType();
		System.out.println("validateDataByType message: " + msg1);
		
		FormData data2 = new FormData();
				 data2.dataType = "custOMER ";
				 data2.dataText = "01_20001";
				 data2.dataCode = "tx";
				 data2.requiredEntry = "R";
		String msg2 = data2.validateDataByType();
		System.out.println("validateDataByType message: " + msg2);
		
		FormData data3 = new FormData();
				 data3.dataType = "AS400User ";
				 data3.dataText = "a deisen";
			     data3.dataCode = "tx";
				 data3.requiredEntry = "R";
		String msg3 = data3.validateDataByType();		
		System.out.println("validateDataByType message: " + msg3);
	}
	catch (Exception e) {
		System.out.println("validateDataByType error: " + e);		
	}
	
	
	try {
		
        BigDecimal[]    dataNU;
        dataNU      = new BigDecimal[binNumeric];
        Vector data = new Vector();
        int index   = 7;
		    
        for(int i = 0; i < binNumeric; i++) {
	    index = index + 1;
	    String output = "Index: " + index + "  Data: " + dataNU[i] + "  I: " + i;
	    data.addElement(output);  
	    }
        System.out.println("Loop1 test successfull");
	}
	catch (Exception e) {
		System.out.println("Error: Loop1 Testint: " + e);		
	}


	try {
		
        java.sql.Date[] dataDT;
        dataDT = new java.sql.Date[binDate];
        Vector data = new Vector();
        int index   = 37;
		    
        for(int i = 0; i < binDate; i++) {
	    index = index + 1;
	    String output = "Index: " + index + "  Data: " + dataDT[i] + "  I: " + i;
	    data.addElement(output);  
	    }
	    System.out.println("Loop2 test successfull");
	}
	catch (Exception e) {
		System.out.println("Error: Loop2 Testint: " + e);		
	}    


	try {
 
		Integer value1 = new Integer("13");
		Integer value2 = new Integer("1063");
		Vector  data   = new Vector();		
	            data   = findDataByTranByEntry(value1, value2);	        
 	            
	    System.out.println("Find by Data/Tran: " + value1 + " " + value2 + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormData.main() on find1: " + e);		
	}
	

	try {
 
		Integer value1 = new Integer("13");
		Vector  data   = new Vector();		
	            data   = findDataByFormByView(value1);	          
 	            
	    System.out.println("Find by Data/View: " + value1 + " successfull");
	}	    
	catch (Exception e) {
		System.out.println("Error: FormData.main() on find2: " + e);		
	}
	}
	
	if ("x" == "x")
	{
		int x = 0;
		x = nextTranNumber();
		String stophere = "x";
	}
}
/**
 * Set the form data presentation attribute.
 *
 * Creation date: (11/11/2003 10:27:28 AM)
 */
public void setDataAttribute(String inAttribute) throws InvalidLengthException {
	
	if (inAttribute.length() > 15)
		throw new InvalidLengthException(
				"inAttribute", inAttribute.length(), 15);

	this.dataAttribute = inAttribute;
}
/**
 * Set the data class value.
 *
 * Creation date: (8/23/2004 10:27:28 AM)
 */
public void setDataClass(Vector inDataClass) {
		
	this.dataClass = inDataClass;
}
/**
 * Set the form data class name attribute.
 *
 * Creation date: (8/23/2004 10:27:28 AM)
 */
public void setDataClassName(Vector inDataClassName) {
		
	this.dataClassName = inDataClassName;
}
/**
 * Set comment text.
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setDataComment(String inComment) throws InvalidLengthException {
	
	if (inComment.length() > 1000)
		throw new InvalidLengthException(
				"inComment", inComment.length(), 1000);

	this.dataComment = inComment;
}
/**
 * Set the data date value.
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setDataDate(java.sql.Date inDataDate) {
		
	this.dataDate = inDataDate;
}
/**
 * Set the form data defaulted value code.
 *
 * Creation date: (11/17/2003 10:27:28 AM)
 */
public void setDataDefaulted(String inDefaulted) throws InvalidLengthException {
	
	if (inDefaulted.length() > 1)
		throw new InvalidLengthException(
				"inDefaulted", inDefaulted.length(), 1);

	this.dataDefaulted = inDefaulted;
}
/**
 * Set the form data description value.
 *
 * Creation date: (10/1/2004 4:45:28 PM)
 */
public void setDataDescription(Vector inDataDescription) {
		
	this.dataDescription = inDataDescription;
}
/**
 * Set numeric data value.
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setDataNumeric(BigDecimal inDataNumeric) {
		
	this.dataNumeric = inDataNumeric;
}
/**
 * Set data text.
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setDataText(String inText) throws InvalidLengthException {
	
	if (inText.length() > 5000)
		throw new InvalidLengthException(
				"inText", inText.length(), 5000);

	this.dataText = inText;
}
/**
 * Set the data time value.
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setDataTime(java.sql.Time inDataTime) {
		
	this.dataTime = inDataTime;
}
/**
 * Set form number.
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setFormNumber(Integer inFormNumber) {
		
	this.formNumber = inFormNumber;
}
/**
 * Set the record identification code (data comment).
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setRecordCmtId(String inRecordId) throws InvalidLengthException {
	
	if (inRecordId.length() > 2)
		throw new InvalidLengthException(
				"inRecordId", inRecordId.length(), 2);

	this.recordCmtId = inRecordId;
}
/**
 * Set the record identification code (data defaulted).
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setRecordDftId(String inRecordId) throws InvalidLengthException {
	
	if (inRecordId.length() > 2)
		throw new InvalidLengthException(
				"inRecordId", inRecordId.length(), 2);

	this.recordDftId = inRecordId;
}
/**
 * Set the record identification code (data value).
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setRecordDtaId(String inRecordId) throws InvalidLengthException {
	
	if (inRecordId.length() > 2)
		throw new InvalidLengthException(
				"inRecordId", inRecordId.length(), 2);

	this.recordDtaId = inRecordId;
}
/**
 * Set the transaction date.
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setTranEffDate(java.sql.Date inTranDate) {
		
	this.tranEffDate = inTranDate;
}
/**
 * Set form line transaction number.
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setTranNumber(Integer inTranNumber) {
		
	this.tranNumber = inTranNumber;
}
/**
 * Set the date of the last record updated.
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setUpdateDate(java.sql.Date inUpdateDate) {
		
	this.updateDate = inUpdateDate;
}
/**
 * Set the time of the last record updated.
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setUpdateTime(java.sql.Time inUpdateTime) {
		
	this.updateTime = inUpdateTime;
}
/**
 * Set update user profile.
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setUpdateUser(String inUpdateUser) throws InvalidLengthException {
	
	if (inUpdateUser.length() > 10)
		throw new InvalidLengthException(
				"inUpdateUser", inUpdateUser.length(), 10);

	this.updateUser = inUpdateUser;
}
/**
 * Set update user profile name.
 *
 * Creation date: (8/15/2003 10:27:28 AM)
 */
public void setUpdateUserName(String inUpdateUserName) throws InvalidLengthException {
	
	if (inUpdateUserName.length() > 30)
		throw new InvalidLengthException(
				"inUpdateUserName", inUpdateUserName.length(), 30);

	this.updateUserName = inUpdateUserName;
}
/**
 * Process each piece of user entered data to validate the values entered.
 *
 * Creation date: (8/24/2004 8:24:29 AM)
 */
public String validateDataByType() {
    
	String   message     = "";
	String   editData    = "";
	String[] messageInfo;
			 messageInfo = new String[2];    
  
	try {
      		
		if (this.dataCode.trim().toLowerCase().equals( ("tx"))) {					// Edit text data
				
			if (this.requiredEntry.trim().toLowerCase().equals ("y"))
				editData = "y";														// Entry is required
			else {																	// Optional entry
				if ((this.dataText == null) || (this.dataText.trim().equals ("")))
					editData = "n";													// No entry provided
				else {
					if (this.dataType.trim().toLowerCase().equals ("customer")) {	// Customer Type					
						if (this.dataText.equals ("_"))								// No entry provided
							editData = "n";		
						else
							editData = "y";											// Customer entered
					}				
					else
						editData = "y";												// Optional value entered
				}
			}
				
			if (editData.equals( ("y"))) {
				if (this.dataType.trim().toLowerCase().equals ("as400user")) 
					message = message + Security.validateUserProfile(this.dataText);														
										
//				if (this.dataType.trim().toLowerCase().equals ("customer")) {				  	
//					messageInfo = ValidateFields.validateCustomer(this.dataText, "full", defaultCustomer);
//					message     = messageInfo[0];
//					if (message.equals (""))
//						setDataText(messageInfo[1]);				
//				}
				
				if (this.dataType.trim().toLowerCase().equals ("email")) 
					message = message + ValidateFields.validateEMail(this.dataText);
					
//				if (this.dataType.trim().toLowerCase().equals ("resource")) 
//					message = message + ValidateFields.validateResource(this.dataText);							
				
			}
			else {
				if (this.dataType.trim().toLowerCase().equals ("customer"))				
					setDataText(defaultCustomer);			 						// No entry, default value							
			}
				
		}
		
		if (this.dataCode.trim().toLowerCase().equals( ("nu"))) {					// Edit numeric data
			
			if (this.requiredEntry.trim().toLowerCase().equals ("y"))
				editData = "y";														// Entry is required
			else {																	// Optional entry
				if (this.dataNumeric == null) 
					editData = "n";													// No entry provided
				else
					editData = "y";													// Optional value entered
			}
			
			if (editData.equals( ("y"))) {	
			
//				if (this.dataType.trim().toLowerCase().equals ("broker")) {
//					Integer dataNumber = new Integer(this.dataNumeric.setScale(0).toString());
//					message = message + ValidateFields.validateBroker(dataNumber);
//				}
//				if (this.dataType.trim().toLowerCase().equals ("order number")) {
//					Integer dataNumber = new Integer(this.dataNumeric.setScale(0).toString());
//					message = message + ValidateFields.validateOrderNumber(dataNumber);
//				}
//				if (this.dataType.trim().toLowerCase().equals ("sample number")) {
//					Integer dataNumber = new Integer(this.dataNumeric.setScale(0).toString());
//					message = message + ValidateFields.validateSampleNumber(dataNumber);
//				}
			}
			
		}			
		        
	}
	catch (Exception e) {
		System.out.println("Exception error validating input data at " + 
						   "com.treetop.data.FormData.validateDataByType(): " + e);
	}
	
	return message;     
}
/**
 * Delete a record of Form System Data value, comment or defaulted.
 *
 * Creation date: (8/15/2003 1:50:29 PM)
 */
private boolean delete(Integer inFormNumber, Integer inTranNumber) {

	try {
	
		PreparedStatement deleteComment = (PreparedStatement) sqlDeleteComment.pop();
		deleteComment.setInt(1, inFormNumber.intValue());
		deleteComment.setInt(2, inTranNumber.intValue());		
		deleteComment.executeUpdate();
		sqlDeleteComment.push(deleteComment);
					
	} 
	catch (Exception e) {
	}

			
	try 
	{
		PreparedStatement deleteData = (PreparedStatement) sqlDeleteData.pop();
		deleteData.setInt(1, inFormNumber.intValue());
		deleteData.setInt(2, inTranNumber.intValue());		
		deleteData.executeUpdate();
		sqlDeleteData.push(deleteData);

		PreparedStatement deleteDefaulted = (PreparedStatement) sqlDeleteDefaulted.pop();
		deleteDefaulted.setInt(1, inFormNumber.intValue());
		deleteDefaulted.setInt(2, inTranNumber.intValue());		
		deleteDefaulted.executeUpdate();
		sqlDeleteDefaulted.push(deleteDefaulted);
		
		return true;
					
	} 
	catch (Exception e) {	
		System.out.println("SQL error at com.treetop.data.FormData.delete(Integer Integer): " + e);
		return false;
	}	
	
}
/**
 * Send in the key fields for the data transactions to be deleted from all files.
 *
 * Creation date: (9/24/2003 1:48:29 PM)
 */
public boolean deleteFormData(Integer inFormNumber, Integer inTranNumber) { 		

	
	try {
	
		PreparedStatement deleteComment = (PreparedStatement) sqlDeleteComment.pop();
		deleteComment.setInt(1, inFormNumber.intValue());
		deleteComment.setInt(2, inTranNumber.intValue());		
		deleteComment.executeUpdate();
		sqlDeleteComment.push(deleteComment);
	
	}		
	catch (Exception e) {		
	}
	
	
	try 
	{
		PreparedStatement deleteData = (PreparedStatement) sqlDeleteData.pop();
		deleteData.setInt(1, inFormNumber.intValue());
		deleteData.setInt(2, inTranNumber.intValue());		
		deleteData.executeUpdate();
		sqlDeleteData.push(deleteData);

		PreparedStatement deleteDefaulted = (PreparedStatement) sqlDeleteDefaulted.pop();
		deleteDefaulted.setInt(1, inFormNumber.intValue());
		deleteDefaulted.setInt(2, inTranNumber.intValue());		
		deleteDefaulted.executeUpdate();
		sqlDeleteDefaulted.push(deleteDefaulted);
		
		return true;
	
	}		
	catch (Exception e) 
	{
		System.out.println("SQL Exception at com.treetop.data.FormData." +
						   "deleteFormData(Integer Integer): " + e);					
		return false;
	}
	
}
/**
 * Update data records in the Form System for Values, Defaulted and Comments.
 *
 * Creation date: (10/08/2003 1:50:29 PM)
 */
public Exception update(Vector inUpdateInfo) {

	int element = 0;
			
	try {			

		// Create arrays for each type of data. Set all elements to there default values.
		
		BigDecimal[]    dataNU;
		java.sql.Date[] dataDT;
		java.sql.Time[] dataTM;
		String[]        dataTX;

		String[]        defaultedNU;
		String[]        defaultedDT;
		String[]        defaultedTM;
		String[]        defaultedTX;
				
		dataNU = new BigDecimal[binNumeric];
		dataDT = new java.sql.Date[binDate];
		dataTM = new java.sql.Time[binTime];
		dataTX = new String[binText];

		defaultedNU = new String[binNumeric];
		defaultedDT = new String[binDate];
		defaultedTM = new String[binTime];
		defaultedTX = new String[binText];
		
     
        for (int i = 0; i < dataNU.length; i++) {
            dataNU[i]      = new BigDecimal(0);
            defaultedNU[i] = "Y";
        }
        for (int i = 0; i < dataDT.length; i++) {
            dataDT[i]      = defaultDate;
            defaultedDT[i] = "Y";
        }
        for (int i = 0; i < dataTM.length; i++) {
            dataTM[i]      = defaultTime;
            defaultedTM[i] = "Y";
        }
        for (int i = 0; i < dataTX.length; i++) {
            dataTX[i]      = null;
            defaultedTX[i] = "Y";
        }   
         
        
        // Build data class from the vector element.
        
        for (int x = 0; x < inUpdateInfo.size(); x ++)
		{	
			 
		    FormData updateInfo = (FormData) inUpdateInfo.elementAt(x);
		    if (!updateInfo.recordDtaId.equals("")) {
			    element = x; 
		         
       
		        // Load data into array using the position of the data in the record less 1 for the array.
		
		        int index     = 0;
		        int binNumber = updateInfo.dataNumber.intValue();
		
		        if (binNumber == 0) 
		            index = updateNextIndex(updateInfo.formNumber, updateInfo.columnNumber);
	            else
	                index = binNumber;
			
		        index = index - 1;	
		
		        if (updateInfo.dataCode.equals("NU")) { 		 	 		       
		            dataNU[index]      = updateInfo.dataNumeric;
		            defaultedNU[index] = updateInfo.dataDefaulted;
		        }
		    
		        if (updateInfo.dataCode.equals("DT")) {
		            dataDT[index]      = updateInfo.dataDate;
		            defaultedDT[index] = updateInfo.dataDefaulted;
		        }
		    
		        if (updateInfo.dataCode.equals("TM")) {
		            dataTM[index]      = updateInfo.dataTime;
		            defaultedTM[index] = updateInfo.dataDefaulted;		        
		        }
		    
		        if (updateInfo.dataCode.equals("TX")) {
		            dataTX[index]      = updateInfo.dataText;
		            defaultedTX[index] = updateInfo.dataDefaulted;
		        }		    
		 
		    }
		    
		}

		
		
		// Update data base record with class data (values).

	    try {
	  		
		    if (element != 0) {
		        FormData updateInfo = (FormData) inUpdateInfo.elementAt(element);

		        PreparedStatement updateData = (PreparedStatement) sqlUpdateData.pop();
		
		        updateData.setString(1, updateInfo.recordDtaId);
		        updateData.setInt(2, updateInfo.formNumber.intValue());
		        updateData.setInt(3, updateInfo.tranNumber.intValue());
		        updateData.setDate(4, updateInfo.tranEffDate);
		        updateData.setString(5, updateInfo.updateUser);
		        updateData.setDate(6, updateInfo.updateDate);
		        updateData.setTime(7, updateInfo.updateTime);

		        int index = 7;
		    
			    for(int i = 0; i < binNumeric; i++) {
				    index = index + 1;
				    updateData.setBigDecimal(index, dataNU[i]);
			    }

			    for(int i = 0; i < binDate; i++) {
				    index = index + 1;
				    updateData.setDate(index, dataDT[i]);
			    }

			    for(int i = 0; i < binTime; i++) {
				    index = index + 1;
				    updateData.setTime(index, dataTM[i]);
			    }

			    for(int i = 0; i < binText; i++) {
				    index = index + 1;
				    updateData.setString(index, dataTX[i]);
			    }	
		
		        index = index + 1; 
		        updateData.setInt(index, updateInfo.formNumber.intValue());
		        index = index + 1;
		        updateData.setInt(index, updateInfo.tranNumber.intValue());
		
		        updateData.executeUpdate();

		        sqlUpdateData.push(updateData);
		    }		
		
	    }		
	    catch (Exception e) {
	    
		    System.out.println("SQL error at com.treetop.data.FormData.update.data(Vector): " + e);
		    return e;
	    }
	

	
	    // Update data base record with class data (defaulted).
	    
	    try {	
		
		    if (element != 0) {
		        FormData updateInfo = (FormData) inUpdateInfo.elementAt(element);

		        PreparedStatement updateDefaulted = (PreparedStatement) sqlUpdateDefaulted.pop();

		        if ((updateInfo.recordDftId != null) && (!updateInfo.recordDftId.equals("")) && 
			       (!updateInfo.recordDftId.equals("DD")))
		            updateDefaulted.setString(1, updateInfo.recordDftId);
		        else        
			        updateDefaulted.setString(1, "DD");
		
		        updateDefaulted.setInt(2, updateInfo.formNumber.intValue());
		        updateDefaulted.setInt(3, updateInfo.tranNumber.intValue());
		    
		        int index = 3;
		    
			    for(int i = 0; i < binNumeric; i++) {
				    index = index + 1;
				    updateDefaulted.setString(index, defaultedNU[i]);
			    }

			    for(int i = 0; i < binDate; i++) {
				    index = index + 1;
				    updateDefaulted.setString(index, defaultedDT[i]);
			    }

			    for(int i = 0; i < binTime; i++) {
				    index = index + 1;
				    updateDefaulted.setString(index, defaultedTM[i]);
			    }

			    for(int i = 0; i < binText; i++) {
				    index = index + 1;
				    updateDefaulted.setString(index, defaultedTX[i]);
			    }	
		
		        index = index + 1; 
		        updateDefaulted.setInt(index, updateInfo.formNumber.intValue());
		        index = index + 1;
		        updateDefaulted.setInt(index, updateInfo.tranNumber.intValue());
		
		        updateDefaulted.executeUpdate();

		        sqlUpdateDefaulted.push(updateDefaulted);
		    }
		
		}		
	    catch (Exception e) {	
		    System.out.println("SQL error at com.treetop.data.FormData.update.defaulted(Vector): " + e);		
		    return e;
	    }



	    // Update data base record with class data (comment).
	    
	    try {	
		
		    if (element != 0) {
		        FormData updateInfo = (FormData) inUpdateInfo.elementAt(element);
		        if (!updateInfo.recordCmtId.equals("")) {
			        PreparedStatement updateComment = (PreparedStatement) sqlUpdateComment.pop();
		            updateComment.setString(1, updateInfo.recordCmtId);
		            updateComment.setInt(2, updateInfo.formNumber.intValue());
		            updateComment.setInt(4, updateInfo.tranNumber.intValue());
		            updateComment.setString(5, updateInfo.dataComment);		
		            updateComment.executeUpdate();
		            sqlUpdateComment.push(updateComment);
		        }
		    }
		    
	    }	
	    catch (Exception e) {	
		    System.out.println("SQL error at com.treetop.data.FormData.update.comment(Vector): " + e);
		    return e;
	    }

		    
	    

	}	
	catch (Exception e) {	
		System.out.println("SQL error at com.treetop.data.FormData.update(Vector): " + e);
		return e;
	}	

	return null;
}

}
