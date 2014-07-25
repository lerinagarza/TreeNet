package com.treetop.data;

import java.sql.*;
import java.util.*;
import com.treetop.utilities.ConnectionStack;
/**
 * Access to Form System Header file DBLIB/FMPAFHDR
 *    9/22/08 TWalton changed to NEW BOX -  Library DBPRD
 **/
public class FormHeader {
	

	// Data base fields.
								
	private   	String        	recordHdrId;
	protected 	Integer       	formNumber;
	private   	String        	formTitle;
	private   	String        	ownerProfile;
	private   	String        	ownerName;
	private		String			formType;
	private   	String        	groupingCode;
	private   	String        	requireDateRange;
	protected 	String        	secureInquiry;
	protected 	String        	secureUpdate;
	private   	String        	updateHdrUser;
	private   	String        	updateHdrUserName;
	private   	java.sql.Date	updateHdrDate;
	private   	java.sql.Time	updateHdrTime;
	protected	String[]		orderBySequence;
	protected	String[]		orderByStyle;
	protected	String[]		showOnInquiry;
	private		String[]		showOnList;
	private		String[]		showOnUpdate;
	private     String         	reportTemplate;
	private		String			updatePageType;
	private   	String        	referenceGuideHdr;
	private   	String        	helpTextHdr;
																

	// Define database environment (live or test) on the AS/400.
	
//	private static String library = "WKLIB."; 	// test environment
	// 9/22/08 TWalton - Moved and point to new box 
	//private static String library = "DBLIB."; 	// live environment
	private static String library = "DBPRD.";

	// For use in Main Method,
    // Constructor Methods and Lookup Methods.

	private static Stack sqlDelete = null;
	private static Stack sqlInsert = null;
	private static Stack sqlUpdate = null;

	private static Stack findHeaderByForm = null;
	private static Stack findHeaderByType = null;
	
	
	// Additional fields.
	
	private static boolean persists = false;
	private	int	   basicIndex = 5;
	
	
/**
 * Instantiate the form header.
 *
 * Creation date: (8/14/2003 10:36:39 AM)
 */
public FormHeader() {

	init();
}
/**
 * Instantiate the form header using the form definition class.
 *
 * Creation date: (10/21/2003 10:36:39 AM)
 */
public FormHeader(FormDefinition definition) throws InstantiationException {
	
	try {

        recordHdrId 	  	= definition.getRecordHdrId();
		formNumber        	= definition.getFormNumber();
	    formTitle         	= definition.getFormTitle();
		ownerProfile      	= definition.getOwnerProfile();
		ownerName         	= definition.getOwnerName();
		formType            = definition.getFormType();
		groupingCode      	= definition.getGroupingCode();
		requireDateRange  	= definition.getRequireDateRange();
		secureInquiry     	= definition.getSecureInquiry();
	    secureUpdate      	= definition.getSecureUpdate();
		updateHdrUser     	= definition.getUpdateHdrUser();
		updateHdrUserName 	= definition.getUpdateHdrUserName();
		updateHdrDate     	= definition.getUpdateHdrDate();
		updateHdrTime     	= definition.getUpdateHdrTime();
		orderBySequence    	= definition.getOrderBySequence();
	    orderByStyle      	= definition.getOrderByStyle();
	    showOnInquiry       = definition.getShowOnInquiry();
		showOnList          = definition.getShowOnList();
		showOnUpdate        = definition.getShowOnUpdate();
		reportTemplate      = definition.getReportTemplate();
		updatePageType      = definition.getUpdatePageType();
		referenceGuideHdr 	= definition.getReferenceGuideHdr();
		helpTextHdr       	= definition.getHelpTextHdr();							

	}	
	catch (Exception e)
	{
		System.out.println("Exception error at com.treetop.data.FormHeader." +
			               "FormHeader(Class) " + e);
	}
}
/**
 * Instantiate the form header.
 *
 * Creation date: (8/14/2003 10:36:39 AM)
 */
public FormHeader(Integer inFormNumber)
                  throws InstantiationException {
                  	                  	
    super();
    

	if (persists == false)
		init();		
		
	ResultSet rs = null;
	
	try {
		
		PreparedStatement headerByForm = (PreparedStatement) findHeaderByForm.pop();
		
		headerByForm.setInt(1, inFormNumber.intValue());
			
		rs = headerByForm.executeQuery();
		findHeaderByForm.push(headerByForm);
		
	  	if (rs.next() == false)
 	       throw new InstantiationException("FormHeader.FormHeader(Integer): " + inFormNumber + " not found");
 	    else
 	       loadFields(rs, "all");
 	       
 	    rs.close();
			
	}
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormHeader.FormHeader(Integer) " + e);
		return;
	}
	
}
/**
 * Instantiate the form header.
 *
 * Creation date: (8/14/2003 10:36:39 AM)
 */
public FormHeader(ResultSet rs, String loadMode)	throws InstantiationException {
	
	loadFields(rs, loadMode);
		
}
/**
 * Drop down list for form header titles to return the form number.
 *
 * Creation date: (8/22/2003 1:01:38 PM)
 */
public static String buildDropDownTitleForNumber(Integer inNumber, String inName, String inSelect,
	                                             String[] inRole, String[] inGroup, String inProfile) { 
	     
	String  dropDown     = "";
	String  selected     = "";
	String  selectOption = "";
	Integer selectForm   = new Integer(0);

	Vector formList  = FormHeader.findHeaderBySecurityByForm(selectForm, inRole, inGroup, inProfile);
 	int    formCount = formList.size();
	

	if (inSelect.equals("") || inSelect==null)
	    selectOption = "Select an Entry--->:";
	else {
		 if (inSelect.trim().equals("*all"))
		     selectOption = "*all";
	     else
	         selectOption = "Select a " + inSelect.trim() + "--->";
	     }
	    
	
	if (formCount > 0)
		{
   		    for (int x = 0; x < formCount; x++)
   		    {
	   		    FormHeader nextForm = (FormHeader) formList.elementAt(x);

	   		    if ((nextForm.secureInquiry.equals ("Y")) || (nextForm.secureUpdate.equals ("Y"))) {

	   		        if (inNumber.equals(nextForm.getFormNumber()))
	   		        selected = "' selected='selected'>";
	   		        else
	   		        selected = "'>";
		   		    
	   		        dropDown = dropDown + "<option value='" + 
	   		        nextForm.getFormNumber() + selected + nextForm.getFormNumber() +
	   		        " - " + nextForm.getFormTitle().trim() + "&nbsp;";
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
/**
 * Delete a record from the Form System Header Definition.
 *
 * Creation date: (8/14/2003 1:50:29 PM)
 */
private void delete() {

	try { 
		
		PreparedStatement deleteData = (PreparedStatement) sqlDelete.pop();
			
		deleteData.setInt(1, formNumber.intValue());
		
		deleteData.executeUpdate();
		
		sqlDelete.push(deleteData);
		
	} 
	catch (SQLException e) {	
		System.out.println("SQL error at com.treetop.data.FormHeader.delete(): " + e);
	}
	
}
/**
 * Create a vector from a result set for the form header for users clearing the security check.
 *
 * Creation date: (10/13/2003 8:24:29 AM)
 */
public static Vector findHeaderBySecurityByForm(Integer inFormNumber, String[] inRole, String[] inGroup, String inProfile) {

	Vector     headerList = new Vector();
	FormHeader header     = new FormHeader();	
	
	Connection conn = null;
    // Initial SQL statement for each form column transaction).

    String sqlStatement = "SELECT * FROM" +          
		   "(" + library + "FMPESECU JOIN " + library + "FMPAFHDR ON FMENBR = FMANBR)" +
		   " WHERE (";

    try {

	    // Create SQL 'WHERE' statement.

	    int selectedForm = inFormNumber.intValue();
	    if (selectedForm > 0)
	        sqlStatement = sqlStatement + "FMENBR = " + selectedForm + " AND ";

	    sqlStatement = sqlStatement + "FMECOL = 0 AND (FMEINQ = 'Y' OR FMEUPD = 'Y'))";
		   

        // Create SQL 'WHERE' statement for role security.

        if (inRole != null) {
	        
            int x = inRole.length;
            if (x > 0) {
	    
   	            sqlStatement = sqlStatement + " AND (FMEROL IN (" + inRole[0];
		
	            for (int z = 1; z < x; z ++) {
		             sqlStatement = sqlStatement + "," + inRole[z];
	            }

		        sqlStatement = sqlStatement + ")";
            }
        }


        // Create SQL 'WHERE' statement for group security. 

        if (inGroup != null) {
	        
            int x = inGroup.length;
            if (x > 0) {

	            int found  = sqlStatement.indexOf("FMEROL IN");		        
		        if (found >= 0)
	                sqlStatement = sqlStatement + " OR FMEGRP IN (" + inGroup[0];
	            else
	                sqlStatement = sqlStatement + " AND (FMEGRP IN (" + inGroup[0];

		        for (int z = 1; z < x; z ++) {
		            sqlStatement = sqlStatement + "," + inGroup[z];
	            }

		        sqlStatement = sqlStatement + ")";
            }
        }


        // Create SQL 'WHERE' statement for user profile security.

        if (inProfile != null) {

	        int found1  = sqlStatement.indexOf("FMEROL IN");
	        int found2  = sqlStatement.indexOf("FMEGRP IN");		
		        
	        if ((found1 >= 0) || (found2 >=0))
	            sqlStatement = sqlStatement + " OR UPPER(FMEPRF) = '" + inProfile.toUpperCase() + "'";
	        else
	            sqlStatement = sqlStatement + " AND (UPPER(FMEPRF) = '" + inProfile.toUpperCase() + "'";
        }

        // Close out 'WHERE' clause.

        int found  = sqlStatement.indexOf(") AND (");	
         
	    if (found >= 0)
	        sqlStatement = sqlStatement + ")";


        // Add the sort sequence to the 'ORDER BY' clause.

        sqlStatement = sqlStatement + "	ORDER BY FMATTL";
    
        // 9/22/08 Change to use the ConnectionStack instead of Connection Pool.
        // Execute the SQL statement.
        
		//com.treetop.ConnectionPool connectionPool = new com.treetop.ConnectionPool();        
        conn = ConnectionStack.getConnection();

		Statement stmt  = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
		ResultSet rs    = stmt.executeQuery(sqlStatement);                
         
		// Return Connection in the Finally Statement
           		              
	    // Process the SQL result into the return data class vector elements.  
	
	    try {

		    int       lastFormNumber = 0;
		    String    lastInquiry    = "";
		    String    lastUpdate     = "";
		    String    inquiry        = "";
		    String    update         = "";
		    int		  nextRow        = 1;
		    int       previousRow    = 1;
		   		   				
	        while (rs.next())
		    {			 			    
		        int formNumber = rs.getInt("FMANBR");
		            nextRow    = rs.getRow();

		        // Change in form number, add class to vector element.

		        if (formNumber != lastFormNumber) {	  			        
			      
			        if (!rs.isFirst()) {                          // NOT first record in result set.
				        rs.absolute(previousRow);                 // Retrieve the previous (last in group).
				        FormHeader buildHeader = new FormHeader();    
			            buildHeader.loadFields(rs, "standard");  
			            buildHeader.secureInquiry = lastInquiry;
			            buildHeader.secureUpdate  = lastUpdate;
			            headerList.addElement(buildHeader);
			            rs.absolute(nextRow);                     // Return to the current record (first in group).
				    }
			        			        
			        lastFormNumber = formNumber;	      
		            lastInquiry    = "";
		            lastUpdate     = "";
		        }
		          
		        // Same form number, process the available security.
		        
		        inquiry     = rs.getString("FMEINQ");
		        update      = rs.getString("FMEUPD");
				previousRow = rs.getRow(); 
		     
			    if (!inquiry.equals (lastInquiry)) {
				    if (lastInquiry.equals(""))
				        lastInquiry = inquiry;
				    else {
					    if (lastInquiry.equals("N"))
					        lastInquiry = inquiry;
				    }
			    }
			        
			    if (!update.equals (lastUpdate)) {
				    if (lastUpdate.equals(""))
				        lastUpdate = update;
				    else {
					    if (lastUpdate.equals("N"))
					        lastUpdate = update;
				    }
			    }
			    
		    } 
			    
			// Process the last record.
		    
		    if (lastFormNumber > 0) {  	
			    rs.last(); 
			    FormHeader buildHeader = new FormHeader();    
			    buildHeader.loadFields(rs, "standard"); 
			    buildHeader.secureInquiry = lastInquiry; 
			    buildHeader.secureUpdate  = lastUpdate;
			    headerList.addElement(buildHeader);
		    }		   		   
		  	   
	    }
	    catch (Exception e) {
		    System.out.println("Exception error processing a result set " +
				               "(FormHeader.findHeaderBySecurityByForm): " + e);
	    }
	    
	    stmt.close();
	    rs.close();
	    
	}
    catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormHeader." +
			               "findHeaderBySecurityByForm(Integer String[] String[] String): " + e);
	}		
    finally
	{
    	try
		{
    		ConnectionStack.returnConnection(conn);
		}
    	catch(Exception e)
		{}
	}
			
	return headerList; 
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
 * Retrieve the form title.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getFormTitle() {

	return formTitle;	
}
/**
 * Retrieve the form type.
 *
 * Creation date: (9/03/2004 4:45:28 PM)
 */
public String getFormType() {

	return formType;	
}
/**
 * Retrieve the form grouping code.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getGroupingCode() {

	return groupingCode;	
}
/**
 * Retrieve the form help text.
 *
 * Creation date: (10/02/2003 4:45:28 PM)
 */
public String getHelpTextHdr() {

	return helpTextHdr;	
}
/**
 * Retrieve the form sort order sequencing.
 *
 * Creation date: (10/22/2003 10:53:28 AM)
 */
public String[] getOrderBySequence() {

	return orderBySequence;	
}
/**
 * Retrieve the form sort order style (assending, descending).
 *
 * Creation date: (10/22/2003 10:53:28 AM)
 */
public String[] getOrderByStyle() 
{
	return orderByStyle;	
}
/**
 * Retrieve the form owner profile name.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getOwnerName() {

	return ownerName;	
}
/**
 * Retrieve the form owner profile.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getOwnerProfile() {

	return ownerProfile;	
}
/**
 * Retrieve the record identification code.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getRecordHdrId() {

	return recordHdrId;	
}
/**
 * Retrieve the form reference guide.
 *
 * Creation date: (10/02/2003 4:45:28 PM)
 */
public String getReferenceGuideHdr() {

	return referenceGuideHdr;	
}
/**
 * Retrieve the report template name.
 *
 * Creation date: (8/23/2004 4:45:28 PM)
 */
public String getReportTemplate() {

	return reportTemplate;	
}
/**
 * Retrieve the required date range code.
 *
 * Creation date: (10/02/2003 4:45:28 PM)
 */
public String getRequireDateRange() {

	return requireDateRange;	
}
/**
 * Retrieve the form security for inquiry code.
 *
 * Creation date: (10/31/2003 4:45:28 PM)
 */
public String getSecureInquiry() {

	return secureInquiry;	
}
/**
 * Retrieve the form security for update code.
 *
 * Creation date: (10/31/2003 4:45:28 PM)
 */
public String getSecureUpdate() {

	return secureUpdate;	
}
/**
 * Retrieve the form display controls for inquiry pages.
 *
 * Creation date: (7/29/2004 4:45:28 PM)
 */
public String[] getShowOnInquiry() {

	return showOnInquiry;	
}
/**
 * Retrieve the form display controls for list pages.
 *
 * Creation date: (7/29/2004 4:45:28 PM)
 */
public String[] getShowOnList() {

	return showOnList;	
}
/**
 * Retrieve the form display controls for update pages.
 *
 * Creation date: (7/29/2004 4:45:28 PM)
 */
public String[] getShowOnUpdate() {

	return showOnUpdate;	
}
/**
 * Retrieve the last update date. 
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Date getUpdateHdrDate() {

	return updateHdrDate;	
}
/**
 * Retrieve the last update time. 
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Time getUpdateHdrTime() {

	return updateHdrTime;	
}
/**
 * Retrieve the last update user profile.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getUpdateHdrUser() {

	return updateHdrUser;	
}
/**
 * Retrieve the last update user profile name.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getUpdateHdrUserName() {

	return updateHdrUserName;	
}
/**
 * Retrieve the update page type.
 *
 * Creation date: (8/23/2004 4:45:28 PM)
 */
public String getUpdatePageType() {

	return updatePageType;	
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
		// 9/22/08 TWalton - Change to use the ConnectionStack instead of ConnectionPool
		Connection conn1 = ConnectionStack.getConnection();
		Connection conn2 = ConnectionStack.getConnection();
		Connection conn3 = ConnectionStack.getConnection();
		Connection conn4 = ConnectionStack.getConnection();
		Connection conn5 = ConnectionStack.getConnection();
		Connection conn6 = ConnectionStack.getConnection();
		

		String deleteHeader =  		
			"DELETE FROM " + library + "FMPAFHDR " +
			"WHERE FMANBR = ?";			
			
		PreparedStatement deleteHeader1 = conn1.prepareStatement(deleteHeader);
		PreparedStatement deleteHeader2 = conn2.prepareStatement(deleteHeader);
		PreparedStatement deleteHeader3 = conn3.prepareStatement(deleteHeader);
		PreparedStatement deleteHeader4 = conn4.prepareStatement(deleteHeader);
		PreparedStatement deleteHeader5 = conn5.prepareStatement(deleteHeader);
		PreparedStatement deleteHeader6 = conn6.prepareStatement(deleteHeader);
		

		sqlDelete = new Stack();
		sqlDelete.push(deleteHeader1);
		sqlDelete.push(deleteHeader2);
		sqlDelete.push(deleteHeader3);
		sqlDelete.push(deleteHeader4);
		sqlDelete.push(deleteHeader5);
		sqlDelete.push(deleteHeader6);
		

		String insertHeader = 
			"INSERT INTO " + library + "FMPAFHDR " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
		PreparedStatement insertHeader1 = conn1.prepareStatement(insertHeader);
		PreparedStatement insertHeader2 = conn2.prepareStatement(insertHeader);
		PreparedStatement insertHeader3 = conn3.prepareStatement(insertHeader);
		PreparedStatement insertHeader4 = conn4.prepareStatement(insertHeader);
		PreparedStatement insertHeader5 = conn5.prepareStatement(insertHeader);
		PreparedStatement insertHeader6 = conn6.prepareStatement(insertHeader);
		

		sqlInsert = new Stack();
		sqlInsert.push(insertHeader1);
		sqlInsert.push(insertHeader2);
		sqlInsert.push(insertHeader3);
		sqlInsert.push(insertHeader4);
		sqlInsert.push(insertHeader5);
		sqlInsert.push(insertHeader6);
			         

		String updateHeader = 
			"UPDATE " + library + "FMPAFHDR " +
			"SET FMAREC = ?, FMANBR = ?, FMATTL = ?, FMAOWN = ?, FMAGRP = ?, FMATYP = ?," +
			   " FMARDR = ?, FMAUSR = ?, FMADTE = ?, FMATME = ?, FMAOB# = ?, FMAOBS = ?," +
			   " FMASOI = ?, FMASOL = ?, FMASOU = ?, FMARPT = ?, FMAUPP = ?, FMAREF = ?," + 
			   " FMAHLP = ? " +	
			"WHERE FMANBR = ?";
			
		PreparedStatement updateHeader1 = conn1.prepareStatement(updateHeader);
		PreparedStatement updateHeader2 = conn2.prepareStatement(updateHeader);
		PreparedStatement updateHeader3 = conn3.prepareStatement(updateHeader);
		PreparedStatement updateHeader4 = conn4.prepareStatement(updateHeader);
		PreparedStatement updateHeader5 = conn5.prepareStatement(updateHeader);
		PreparedStatement updateHeader6 = conn6.prepareStatement(updateHeader);
		

		sqlUpdate = new Stack();
		sqlUpdate.push(updateHeader1);
		sqlUpdate.push(updateHeader2);
		sqlUpdate.push(updateHeader3);
		sqlUpdate.push(updateHeader4);
		sqlUpdate.push(updateHeader5);
		sqlUpdate.push(updateHeader6);

			
		String headerByForm = 
			"SELECT * FROM " + library + "FMPAFHDR " +
			"WHERE FMANBR = ?";
			
		PreparedStatement headerByForm1 = conn1.prepareStatement(headerByForm);
		PreparedStatement headerByForm2 = conn2.prepareStatement(headerByForm);
		PreparedStatement headerByForm3 = conn3.prepareStatement(headerByForm);
		PreparedStatement headerByForm4 = conn4.prepareStatement(headerByForm);
		PreparedStatement headerByForm5 = conn5.prepareStatement(headerByForm);
		PreparedStatement headerByForm6 = conn6.prepareStatement(headerByForm);
		

		findHeaderByForm = new Stack();
		findHeaderByForm.push(headerByForm1);
		findHeaderByForm.push(headerByForm2);
		findHeaderByForm.push(headerByForm3);
		findHeaderByForm.push(headerByForm4);
		findHeaderByForm.push(headerByForm5);
		findHeaderByForm.push(headerByForm6);
			
			
		String headerByType = 
			"SELECT * FROM " + library + "FMPAFHDR " +
			"ORDER BY FMATYP";
			
		PreparedStatement headerByType1 = conn1.prepareStatement(headerByType);
		PreparedStatement headerByType2 = conn2.prepareStatement(headerByType);
		PreparedStatement headerByType3 = conn3.prepareStatement(headerByType);
		PreparedStatement headerByType4 = conn4.prepareStatement(headerByType);
		PreparedStatement headerByType5 = conn5.prepareStatement(headerByType);
		PreparedStatement headerByType6 = conn6.prepareStatement(headerByType);
		

		findHeaderByType = new Stack();
		findHeaderByType.push(headerByType1);
		findHeaderByType.push(headerByType2);
		findHeaderByType.push(headerByType3);
		findHeaderByType.push(headerByType4);
		findHeaderByType.push(headerByType5);
		findHeaderByType.push(headerByType6);
		
		
		// Return the connections back to the pool.
		
		ConnectionStack.returnConnection(conn1);
		ConnectionStack.returnConnection(conn2);
		ConnectionStack.returnConnection(conn3);
		ConnectionStack.returnConnection(conn4);
		ConnectionStack.returnConnection(conn5);
		ConnectionStack.returnConnection(conn6);						

	}
    catch (SQLException e) {
		System.out.println("SQL exception occured at com.treetop.data.FormHeader.init()" + e);
	}

	}
		
}
/**
 * Insert a record into the Form System Header Definition.
 *
 * Creation date: (8/14/2003 1:50:29 PM)
 */
private void insert() {

	try {
		
		PreparedStatement insertData = (PreparedStatement) sqlInsert.pop();
	
		insertData.setString(1, recordHdrId);
		insertData.setInt(2, formNumber.intValue());
		insertData.setString(3, formTitle);
		insertData.setString(4, ownerProfile);		
		insertData.setString(5, groupingCode);
		insertData.setString(6, formType);
		insertData.setString(7, requireDateRange);
	    insertData.setString(8, updateHdrUser);
		insertData.setDate(9, updateHdrDate);
		insertData.setTime(10, updateHdrTime);
		
		String sortSequence = "";
		String sortStyle    = "";
		String showInquiry  = "";
		String showList	    = "";
		String showUpdate   = "";
		
		for (int x = 0; x < basicIndex; x++) {
			sortSequence = sortSequence + orderBySequence[x];
			sortStyle    = sortStyle + orderByStyle[x];
			showInquiry  = showInquiry + showOnInquiry[x];
			showList     = showList + showOnList[x];
			showUpdate   = showUpdate + showOnUpdate[x];
		}
		
		insertData.setString(11, sortSequence);
		insertData.setString(12, sortStyle);
		insertData.setString(13, showInquiry);
		insertData.setString(14, showList);
		insertData.setString(15, showUpdate);
		insertData.setString(16, reportTemplate);
		insertData.setString(17, updatePageType);		
		insertData.setString(18, referenceGuideHdr);
		insertData.setString(19, helpTextHdr);
				
		insertData.executeUpdate();
		
		sqlInsert.push(insertData);
		
	}	
	catch (SQLException e) {	
		System.out.println("SQL error at com.treetop.data.FormHeader.insert(): " + e);
	}
	
}
/**
 * Load fields from data base record.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
protected void loadFields(ResultSet rs, String loadMode) {

	try {
							
		recordHdrId       	= rs.getString("FMAREC");
		formNumber        	= new Integer(rs.getInt("FMANBR"));
	    formTitle         	= rs.getString("FMATTL");
		ownerProfile      	= rs.getString("FMAOWN");
		ownerName         	= ownerProfile;
		formType			= rs.getString("FMATYP");
		groupingCode      	= rs.getString("FMAGRP");
		requireDateRange  	= rs.getString("FMARDR");
		secureInquiry     	= "N";
	    secureUpdate      	= "N";
		updateHdrUser     	= rs.getString("FMAUSR");
		updateHdrUserName 	= updateHdrUser;
		updateHdrDate     	= rs.getDate("FMADTE");
		updateHdrTime     	= rs.getTime("FMATME");
	    reportTemplate		= rs.getString("FMARPT");
	    updatePageType    	= rs.getString("FMAUPP");
		referenceGuideHdr 	= rs.getString("FMAREF");
		helpTextHdr       	= rs.getString("FMAHLP");
																												
		
		// Breakdown of element positions for basic columns.
		// This scheme is reflected in method: FormData.buildDataFieldNameBasic(Integer)
		//
		// 1) Transaction Number
		// 2) Transaction Date
		// 3) Transaction User
		// 4) Last Update Date
		// 5) Last Update Time
		
		String sortSequence = rs.getString("FMAOB#");
		String sortStyle    = rs.getString("FMAOBS");
		String showInquiry	= rs.getString("FMASOI");
		String showList		= rs.getString("FMASOL");
		String showUpdate	= rs.getString("FMASOU");
		
		orderBySequence     = new String[basicIndex];
		orderByStyle        = new String[basicIndex];
		showOnInquiry       = new String[basicIndex];
		showOnList          = new String[basicIndex];
		showOnUpdate        = new String[basicIndex];
		
		for (int x = 0; x < basicIndex; x++) {
			orderBySequence[x] = sortSequence.substring(x,x+1);
			orderByStyle[x]    = sortStyle.substring(x,x+1);
			showOnInquiry[x]   = showInquiry.substring(x,x+1);
			showOnList[x]      = showList.substring(x,x+1);
			showOnUpdate[x]    = showUpdate.substring(x,x+1);
		}
			
		// If requested, load user name that owns the form.
			
		try {
		
			if (loadMode.equals ("all")) {
			UserFile newUser    = new UserFile(ownerProfile);
			Integer  newUserNum = new Integer(newUser.getUserNumber());
			newUser   = new UserFile(newUserNum);
			ownerName = newUser.getUserNameLong();
			}
			
		}
		catch (Exception e) {
		}
		
		// If requested, load the last user to update name.
		
		try {
		
			if (loadMode.equals ("all")) {
			UserFile newUser    = new UserFile(updateHdrUser);
			Integer  newUserNum = new Integer(newUser.getUserNumber());
			newUser = new UserFile(newUserNum);
			updateHdrUserName = newUser.getUserNameLong();
			}
			
		}
		catch (Exception e) {
		}
				
	
	}
	catch (Exception e) {
		System.out.println("SQL Exception at com.treetop.data.FormHeader" +
			               ".loadFields(RS String): " + e);
	}
				
}
/**
 * Form header file testing.
 *
 * Creation date: (8/14/2003 8:24:29 AM)
 */
public static void main(String[] args) {
	
	try {
		
		Integer    number = new Integer("21");
		FormHeader header = new FormHeader(number);
		
		System.out.println("FormHeader: " + number + " successfull");
	}	
	catch (Exception e) {
		System.out.println("Error: FormHeader.main() on Security: " + e);		
	}
		
		
	// find headers.

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
	    Integer form = new Integer(13);
		Vector   header  = new Vector();
		         header  = findHeaderBySecurityByForm(form, role, group, profile);   
	    	           
	    System.out.println("FormHeader security: successfull");
	}    
	catch (Exception e) {
		System.out.println("Error: FormHeader.main() on Security: " + e);		
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
	    Integer form = new Integer(0);
		Vector   header  = new Vector();
		         header  = findHeaderBySecurityByForm(form, role, group, profile);	    
	    	           
	    System.out.println("FormHeader security: successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormHeader.main() on Security: " + e);		
	}
	

	try {

		Integer    value1 = new Integer(13);
		FormHeader header = new FormHeader(value1);		
	           
	    System.out.println("FormHeader instantiation: " + value1 + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormHeader.main() on Instantiation: " + e);		
	}
		

	// insert headers. 
	
	try {

		java.sql.Date theDate = java.sql.Date.valueOf("2003-05-19");
	    java.sql.Time theTime = java.sql.Time.valueOf("03:45:10");
	    FormHeader header1 = new FormHeader();
	    header1.setRecordHdrId("FH");
	    Integer value1 = new Integer(5544);
	    header1.setFormNumber(value1);
	    header1.setFormTitle("This is the title for the form, stored in the HDR.");
	    header1.setOwnerProfile("THAILE");
	    header1.setGroupingCode("X7");
	    header1.setFormType("type");
	    header1.setRequireDateRange("Y");
	    header1.setUpdateHdrUser("DEISEN");
	    header1.setUpdateHdrDate(theDate);
	    header1.setUpdateHdrTime(theTime);
	    Integer number = new Integer("1");
		String[] orderSeq = new String[] {"0", "1", "0", " ", "0"};
		header1.setOrderBySequence(orderSeq);
		String[] orderStyle = new String[] {" ", "D", " ", " ", " "};
		header1.setOrderBySequence(orderSeq);
	    String[] inquiry = new String[] {"Y", "N", "N", "N", "N"};
	    header1.setShowOnInquiry(inquiry);
		String[] list    = new String[] {"Y", "N", "N", "N", "N"};
		header1.setShowOnList(list);
		String[] update  = new String[] {"Y", "N", "N", "N", "N"};
		header1.setShowOnUpdate(update);
		header1.setReportTemplate("");
		header1.setUpdatePageType("");
		header1.setReferenceGuideHdr("");
		header1.setHelpTextHdr("");
	    
	    header1.insert();
	 
		System.out.println("First insert: " + header1 + " successfull");
	}		
	catch (Exception e) {
		System.out.println("Error: FormHeader.main() on insert1: " + e);
	}


	// update headers.

	try { 

		Integer    value1  = new Integer(5544);
		FormHeader header1 = new FormHeader(value1);
		header1.setOwnerProfile("owner");
		header1.update();
		
		System.out.println("First update: " + header1 + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormHeader.main() on update1: " + e);
	}
	

	// delete headers.
	
	try {

		Integer    value1  = new Integer(5544);
		FormHeader header1 = new FormHeader(value1);
		header1.delete();
		
		System.out.println("First delete: " + header1 + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormHeader.main() on delete1: " + e);
	}
	
	
}
/**
 * Set form number.
 *
 * Creation date: (8/14/2003 10:27:28 AM)
 */
public void setFormNumber(Integer inFormNumber) {
		
	this.formNumber = inFormNumber;
}
/**
 * Set form title.
 *
 * Creation date: (8/14/2003 10:27:28 AM)
 */
public void setFormTitle(String inFormTitle) throws InvalidLengthException {
	
	if (inFormTitle.length() > 50)
		throw new InvalidLengthException(
				"inFormTitle", inFormTitle.length(), 50);

	this.formTitle = inFormTitle;
}
/**
 * Set form type.
 *
 * Creation date: (9/03/2004 10:27:28 AM)
 */
public void setFormType(String inFormType) throws InvalidLengthException {
	
	if (inFormType.length() > 20)
		throw new InvalidLengthException(
				"inFormType", inFormType.length(), 20);

	this.formType = inFormType;
}
/**
 * Set form grouping code.
 *
 * Creation date: (8/14/2003 10:27:28 AM)
 */
public void setGroupingCode(String inGroupingCode) throws InvalidLengthException {
	
	if (inGroupingCode.length() > 2)
		throw new InvalidLengthException(
				"inGroupingCode", inGroupingCode.length(), 2);

	this.groupingCode = inGroupingCode;
}
/**
 * Set form help text.
 *
 * Creation date: (10/02/2003 10:27:28 AM)
 */
public void setHelpTextHdr(String inHelpText) throws InvalidLengthException {
	
	if (inHelpText.length() > 500)
		throw new InvalidLengthException(
				"inHelpText", inHelpText.length(), 500);

	this.helpTextHdr = inHelpText;
}
/**
 * Set the form sort order sequencing.
 *
 * Creation date: (10/22/2003 10:27:28 AM)
 */
public void setOrderBySequence(String[] inOrderBySequence) {
		
	this.orderBySequence = inOrderBySequence;
}
/**
 * Set the form sort order style (assending, descending).
 *
 * Creation date: (10/22/2003 10:27:28 AM)
 */
public void setOrderByStyle(String[] inOrderByStyle) {
		
	this.orderByStyle = inOrderByStyle;
}
/**
 * Set update owner profile name.
 *
 * Creation date: (8/26/2003 4:45:28 PM)
 */
public void setOwnerName(String inOwnerName) throws InvalidLengthException {

	if (inOwnerName.length() > 30)
		throw new InvalidLengthException(
			      "inOwnerName: ", inOwnerName.length(), 30);

	this.ownerName = inOwnerName;
}
/**
 * Set form owner profile.
 *
 * Creation date: (8/14/2003 10:27:28 AM)
 */
public void setOwnerProfile(String inOwnerProfile) throws InvalidLengthException {
	
	if (inOwnerProfile.length() > 10)
		throw new InvalidLengthException(
				"inOwnerProfile", inOwnerProfile.length(), 10);

	this.ownerProfile = inOwnerProfile;
}
/**
 * Set the record identification code.
 *
 * Creation date: (8/14/2003 10:27:28 AM)
 */
public void setRecordHdrId(String inRecordId) throws InvalidLengthException {
	
	if (inRecordId.length() > 2)
		throw new InvalidLengthException(
				"inRecordId", inRecordId.length(), 2);

	this.recordHdrId = inRecordId;
}
/**
 * Set form reference guide.
 *
 * Creation date: (10/02/2003 10:27:28 AM)
 */
public void setReferenceGuideHdr(String inReferenceGuide) throws InvalidLengthException {
	
	if (inReferenceGuide.length() > 500)
		throw new InvalidLengthException(
				"inReferenceGuide", inReferenceGuide.length(), 500);

	this.referenceGuideHdr = inReferenceGuide;
}
/**
 * Set form report template name.
 *
 * Creation date: (8/23/2004 10:27:28 AM)
 */
public void setReportTemplate(String inReportTemplate) throws InvalidLengthException {
	
	if (inReportTemplate.length() > 25)
		throw new InvalidLengthException(
				"inReportTemplate", inReportTemplate.length(), 25);

	this.reportTemplate = inReportTemplate;
}
/**
 * Set required date range code.
 *
 * Creation date: (10/02/2003 10:27:28 AM)
 */
public void setRequireDateRange(String inRequireDateRange) throws InvalidLengthException {
	
	if (inRequireDateRange.length() > 1)
		throw new InvalidLengthException(
				"inRequireDateRange", inRequireDateRange.length(), 1);

	this.requireDateRange = inRequireDateRange;
}
/**
 * Set form security for inquiry code.
 *
 * Creation date: (10/31/2003 10:27:28 AM)
 */
public void setSecureInquiry(String inSecureInquiry) throws InvalidLengthException {
	
	if (inSecureInquiry.length() > 1)
		throw new InvalidLengthException(
				"inSecureInquiry", inSecureInquiry.length(), 1);

	this.secureInquiry = inSecureInquiry;
}
/**
 * Set form security for update code.
 *
 * Creation date: (10/31/2003 10:27:28 AM)
 */
public void setSecureUpdate(String inSecureUpdate) throws InvalidLengthException {
	
	if (inSecureUpdate.length() > 1)
		throw new InvalidLengthException(
				"inSecureUpdate", inSecureUpdate.length(), 1);

	this.secureUpdate = inSecureUpdate;
}
/**
 * Set the form display controls for inquiry pages.
 *
 * Creation date: (7/29/2003 10:27:28 AM)
 */
public void setShowOnInquiry(String[] inShowOnInquiry) {
		
	this.showOnInquiry = inShowOnInquiry;
}
/**
 * Set the form display controls for list pages.
 *
 * Creation date: (7/29/2003 10:27:28 AM)
 */
public void setShowOnList(String[] inShowOnList) {
		
	this.showOnList = inShowOnList;
}
/**
 * Set the form display controls for update pages.
 *
 * Creation date: (7/29/2003 10:27:28 AM)
 */
public void setShowOnUpdate(String[] inShowOnUpdate) {
		
	this.showOnUpdate = inShowOnUpdate;
}
/**
 * Set the date of the last record updated.
 *
 * Creation date: (8/14/2003 10:27:28 AM)
 */
public void setUpdateHdrDate(java.sql.Date inUpdateDate) {
		
	this.updateHdrDate = inUpdateDate;
}
/**
 * Set the time of the last record updated.
 *
 * Creation date: (8/14/2003 10:27:28 AM)
 */
public void setUpdateHdrTime(java.sql.Time inUpdateTime) {
		
	this.updateHdrTime = inUpdateTime;
}
/**
 * Set update user profile.
 *
 * Creation date: (8/14/2003 10:27:28 AM)
 */
public void setUpdateHdrUser(String inUpdateUser) throws InvalidLengthException {
	
	if (inUpdateUser.length() > 10)
		throw new InvalidLengthException(
				"inUpdateUser", inUpdateUser.length(), 10);

	this.updateHdrUser = inUpdateUser;
}
/**
 * Set update user profile name.
 *
 * Creation date: (8/15/2003 4:45:28 PM)
 */
public void setUpdateHdrUserName(String inUserName) throws InvalidLengthException {

	if (inUserName.length() > 30)
		throw new InvalidLengthException(
			      "inUserName: ", inUserName.length(), 30);

	this.updateHdrUserName = inUserName;
}
/**
 * Set update page type.
 *
 * Creation date: (8/23/2004 4:45:28 PM)
 */
public void setUpdatePageType(String inUpdatePage) throws InvalidLengthException {

	if (inUpdatePage.length() > 25)
		throw new InvalidLengthException(
				  "inUpdatePage: ", inUpdatePage.length(), 25);

	this.updatePageType = inUpdatePage;
}
/**
 * Update a record into the Form System Header Definition.
 *
 * Creation date: (8/14/2003 1:50:29 PM)
 */
private void update() {

	try {
		
		PreparedStatement updateData = (PreparedStatement) sqlUpdate.pop();
	
		updateData.setString(1, recordHdrId);
		updateData.setInt(2, formNumber.intValue());
		updateData.setString(3, formTitle);
		updateData.setString(4, ownerProfile);		
		updateData.setString(5, groupingCode);
		updateData.setString(6, formType);
		updateData.setString(7, requireDateRange);	
	    updateData.setString(8, updateHdrUser);
		updateData.setDate(9, updateHdrDate);
		updateData.setTime(10, updateHdrTime);
		
		String sortSequence = "";
		String sortStyle    = "";
		String showInquiry  = "";
		String showList	    = "";
		String showUpdate   = "";
		
		for (int x = 0; x < basicIndex; x++) {
			sortSequence = sortSequence + orderBySequence[x];
			sortStyle    = sortStyle + orderByStyle[x];
			showInquiry  = showInquiry + showOnInquiry[x];
			showList     = showList + showOnList[x];
			showUpdate   = showUpdate + showOnUpdate[x];
		}
		
		updateData.setString(11, sortSequence);
		updateData.setString(12, sortStyle);
		updateData.setString(13, showInquiry);
		updateData.setString(14, showList);
		updateData.setString(15, showUpdate);		
		updateData.setString(16, reportTemplate);
		updateData.setString(17, updatePageType);		
		updateData.setString(18, referenceGuideHdr);
		updateData.setString(19, helpTextHdr);

		updateData.setInt(20, formNumber.intValue());
				
		updateData.executeUpdate();
		
		sqlUpdate.push(updateData);
		
	}	
	catch (SQLException e) {	
		System.out.println("SQL error at com.treetop.data.FormHeader.update(): " + e);
	}
	
}
}
