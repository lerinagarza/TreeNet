package com.treetop.data;

import java.sql.*;
import java.util.*;
import com.treetop.utilities.ConnectionStack;
/**
 * Access to Form System Headings file DBLIB/FMPEHDGS
 *    9/22/08 TWalton changed to NEW BOX -  Library DBPRD
 **/
public class FormHeadings {
	

	// Data base fields.
	
	private 	String        	recordHdgId;
	private 	Integer       	formNumber;
	private 	Integer       	formLineHdgNumber;
	private 	Integer       	columnNumberFrom;
	private 	Integer       	columnNumberTo;
	private 	String        	columnHeading;
	private 	String        	updateHdgUser;
	private 	String        	updateHdgUserName;
	private 	java.sql.Date 	updateHdgDate;
	private 	java.sql.Time 	updateHdgTime;
																

    // Define database environment (live or test) on the AS/400.
	
//  private static String library = "WKLIB."; // test environment
	// 9/22/08 TWalton - Change to point to New BOX
    //private static String library = "DBLIB."; // live environment
	private static String library = "DBPRD.";

    // For use in Main Method,
    // Constructor Methods and Lookup Methods.
    
	private static Stack sqlDelete = null;
	private static Stack sqlInsert = null;
	private static Stack sqlUpdate = null;

	private static Stack findHeadingsByForm;
	private static Stack findHeadingsByLine;
	
	
	// Additional fields.
	
	private static boolean persists = false;

	
/**
 * Instantiate the form headings.
 *
 * Creation date: (8/18/2003 10:36:39 AM)
 */
public FormHeadings() {

	init();
	
}
/**
 Instantiate the form headings.
 *
 * Creation date: (8/18/2003 10:36:39 AM)
 */
public FormHeadings(Integer inFormNumber, Integer inFormLineHdgNumber)
                    throws InstantiationException {
    super();
    
		
	if (persists == false) 	
		init();
		
   	
	ResultSet rs = null;
	
	try {
		
		PreparedStatement headingsByLine = (PreparedStatement) findHeadingsByLine.pop();
		
		headingsByLine.setInt(1, inFormNumber.intValue());
		headingsByLine.setInt(2, inFormLineHdgNumber.intValue());
				
		rs = headingsByLine.executeQuery();
		findHeadingsByLine.push(headingsByLine);
		
		if (rs.next() == false)
			throw new InstantiationException("The heading: " + inFormNumber + 
				                             " and the line: " + inFormLineHdgNumber + " not found");
		else
 	       loadFields(rs, "all");
 	       
 	    rs.close(); 	    
			
	}
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormHeadings.FormHeadings(Integer Integer) " + e);
		return;
	}
	
}
/**
 * Delete a record from the Form System Headings.
 *
 * Creation date: (8/18/2003 1:50:29 PM)
 */
private void delete() {

	try {
		
		PreparedStatement deleteData = (PreparedStatement) sqlDelete.pop(); 
			
		deleteData.setInt(1, formNumber.intValue());
		deleteData.setInt(2, formLineHdgNumber.intValue());
				
		deleteData.executeUpdate();
		
		sqlDelete.push(deleteData);
		
	} 
	catch (SQLException e) {	
		System.out.println("SQL error at com.treetop.data.FormHeadings.delete(): " + e);
	}
	
}
/**
 * Set result set for form headings by the form number.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
public static Vector findHeadingsByForm(Integer inFormNumber) {

	Vector       headingList = new Vector();
	FormHeadings headings    = new FormHeadings();
	
	try {
		
		PreparedStatement headingsByForm = (PreparedStatement) findHeadingsByForm.pop();
		ResultSet rs = null;
		
		try {
		
	 		headingsByForm.setInt(1, inFormNumber.intValue());		
			rs = headingsByForm.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormHeadings.findHeadingsByForm): " + e);
		}
		
		FormHeadings.findHeadingsByForm.push(headingsByForm);

		try {
			
			while (rs.next())
			{
				FormHeadings buildVector = new FormHeadings();
				buildVector.loadFields(rs, "standard");
				headingList.addElement(buildVector);
			}
			
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
				               "(FormHeadings.findHeadingsByForm): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormHeadings." +
			               "findHeadingsByForm(Integer): " + e);
	}	
			
	return headingList; 
}
/**
 * Set result set for form headings by the form number and heading line number.
 *
 * Creation date: (8/18/2003 8:24:29 AM)
 */
public static Vector findHeadingsByLine(Integer inNumber, Integer inLine) {

	Vector       headingList = new Vector();
	FormHeadings headings    = new FormHeadings();
	
	try {
		
		PreparedStatement headingsByLine = (PreparedStatement) FormHeadings.findHeadingsByLine.pop();
		ResultSet rs = null;
		
		try {
			
			headingsByLine.setInt(1, inNumber.intValue());
			headingsByLine.setInt(2, inLine.intValue());
			rs = headingsByLine.executeQuery();
		}
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "(FormHeadings.findHeadingsByLine): " + e);
		}
		
		FormHeadings.findHeadingsByLine.push(headingsByLine);

		try {
			
			while (rs.next())
			{
				FormHeadings buildVector = new FormHeadings();
				buildVector.loadFields(rs, "all");
				headingList.addElement(buildVector);
			}
						
		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set " +
				               "(FormHeadings.findHeadingsByLine): " + e);
		}
		
		rs.close();
		
	}	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.FormHeadings." +
			               "findHeadingsByLine(Integer Integer): " + e);
	}	
			
	return headingList; 
}
/**
 * Retrieve the form column heading text.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getColumnHeading() {

	return columnHeading;	
}
/**
 * Retrieve the form column number 'from' column.
 *
 * Creation date: (8/12/2003 10:53:28 AM)
 */
public Integer getColumnNumberFrom() 
{
	return columnNumberFrom;	
}
/**
 * Retrieve the form column number 'to' column.
 *
 * Creation date: (8/12/2003 10:53:28 AM)
 */
public Integer getColumnNumberTo() 
{
	return columnNumberTo;	
}
/**
 * Retrieve the form line headings number.
 *
 * Creation date: (8/12/2003 10:53:28 AM)
 */
public Integer getFormLineHdgNumber() 
{
	return formLineHdgNumber;	
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
 * Retrieve the record identification code.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getRecordHdgId() {

	return recordHdgId;	
}
/**
 * Retrieve the last update date. 
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Date getUpdateHdgDate() {

	return updateHdgDate;	
}
/**
 * Retrieve the last update time. 
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public java.sql.Time getUpdateHdgTime() {

	return updateHdgTime;	
}
/**
 * Retrieve the last update user profile.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getUpdateHdgUser() {

	return updateHdgUser;	
}
/**
 * Retrieve the last update user profile name.
 *
 * Creation date: (8/12/2003 4:45:28 PM)
 */
public String getUpdateHdgUserName() {

	return updateHdgUserName;	
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
		// 9/22/08 TWalton Change to use Connectio STACK instead of Connection Pool
		Connection conn1 = ConnectionStack.getConnection();
		Connection conn2 = ConnectionStack.getConnection();
		Connection conn3 = ConnectionStack.getConnection();
		Connection conn4 = ConnectionStack.getConnection();
		Connection conn5 = ConnectionStack.getConnection();
		Connection conn6 = ConnectionStack.getConnection();

		String deleteHeadings = 
			"DELETE FROM " + library + "FMPGHDGS " +
			"WHERE FMGNBR = ? AND FMGHLN = ?";
			
		PreparedStatement deleteHeadings1 = conn1.prepareStatement(deleteHeadings);
		PreparedStatement deleteHeadings2 = conn2.prepareStatement(deleteHeadings);
		PreparedStatement deleteHeadings3 = conn3.prepareStatement(deleteHeadings);
		PreparedStatement deleteHeadings4 = conn4.prepareStatement(deleteHeadings);
		PreparedStatement deleteHeadings5 = conn5.prepareStatement(deleteHeadings);
		PreparedStatement deleteHeadings6 = conn6.prepareStatement(deleteHeadings);
		

		sqlDelete = new Stack();
		sqlDelete.push(deleteHeadings1);
		sqlDelete.push(deleteHeadings2);
		sqlDelete.push(deleteHeadings3);
		sqlDelete.push(deleteHeadings4);
		sqlDelete.push(deleteHeadings5);
		sqlDelete.push(deleteHeadings6);		
		

		String insertHeadings = 
			"INSERT INTO " + library + "FMPGHDGS " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
		PreparedStatement insertHeadings1 = conn1.prepareStatement(insertHeadings);
		PreparedStatement insertHeadings2 = conn2.prepareStatement(insertHeadings);
		PreparedStatement insertHeadings3 = conn3.prepareStatement(insertHeadings);
		PreparedStatement insertHeadings4 = conn4.prepareStatement(insertHeadings);
		PreparedStatement insertHeadings5 = conn5.prepareStatement(insertHeadings);
		PreparedStatement insertHeadings6 = conn6.prepareStatement(insertHeadings);
		

		sqlDelete = new Stack();
		sqlDelete.push(insertHeadings1);
		sqlDelete.push(insertHeadings2);
		sqlDelete.push(insertHeadings3);
		sqlDelete.push(insertHeadings4);
		sqlDelete.push(insertHeadings5);
		sqlDelete.push(insertHeadings6);		
			         

		String updateHeadings = 
			"UPDATE " + library + "FMPGHDGS " +
			"SET FMGREC = ?, FMGNBR = ?, FMGHLN = ?, FMGFCL = ?, FMGTCL = ?," +
			   " FMGHDG = ?, FMGUSR = ?, FMGDTE = ?, FMGTME = ? " +
			"WHERE FMGNBR = ? AND FMGHLN = ?";
			
		PreparedStatement updateHeadings1 = conn1.prepareStatement(updateHeadings);
		PreparedStatement updateHeadings2 = conn2.prepareStatement(updateHeadings);
		PreparedStatement updateHeadings3 = conn3.prepareStatement(updateHeadings);
		PreparedStatement updateHeadings4 = conn4.prepareStatement(updateHeadings);
		PreparedStatement updateHeadings5 = conn5.prepareStatement(updateHeadings);
		PreparedStatement updateHeadings6 = conn6.prepareStatement(updateHeadings);
		

		sqlDelete = new Stack();
		sqlDelete.push(updateHeadings1);
		sqlDelete.push(updateHeadings2);
		sqlDelete.push(updateHeadings3);
		sqlDelete.push(updateHeadings4);
		sqlDelete.push(updateHeadings5);
		sqlDelete.push(updateHeadings6);		
		

		String headingsByForm = 
			"SELECT * FROM " + library + "FMPGHDGS " +
			"WHERE FMGNBR = ? " +
		    "ORDER BY FMGNBR, FMGHLN";
		    
		PreparedStatement headingsByForm1 = conn1.prepareStatement(headingsByForm);
		PreparedStatement headingsByForm2 = conn2.prepareStatement(headingsByForm);
		PreparedStatement headingsByForm3 = conn3.prepareStatement(headingsByForm);
		PreparedStatement headingsByForm4 = conn4.prepareStatement(headingsByForm);
		PreparedStatement headingsByForm5 = conn5.prepareStatement(headingsByForm);
		PreparedStatement headingsByForm6 = conn6.prepareStatement(headingsByForm);
		

		findHeadingsByForm = new Stack();
		findHeadingsByForm.push(headingsByForm1);
		findHeadingsByForm.push(headingsByForm2);
		findHeadingsByForm.push(headingsByForm3);
		findHeadingsByForm.push(headingsByForm4);
		findHeadingsByForm.push(headingsByForm5);
		findHeadingsByForm.push(headingsByForm6);		
		

		String headingsByLine = 
			"SELECT * FROM " + library + "FMPGHDGS " +
			"WHERE FMGNBR = ? AND FMGHLN = ? " +
		    "ORDER BY FMGNBR, FMGHLN";
		    
		PreparedStatement headingsByLine1 = conn1.prepareStatement(headingsByLine);
		PreparedStatement headingsByLine2 = conn2.prepareStatement(headingsByLine);
		PreparedStatement headingsByLine3 = conn3.prepareStatement(headingsByLine);
		PreparedStatement headingsByLine4 = conn4.prepareStatement(headingsByLine);
		PreparedStatement headingsByLine5 = conn5.prepareStatement(headingsByLine);
		PreparedStatement headingsByLine6 = conn6.prepareStatement(headingsByLine);
		

		findHeadingsByLine = new Stack();
		findHeadingsByLine.push(headingsByLine1);
		findHeadingsByLine.push(headingsByLine2);
		findHeadingsByLine.push(headingsByLine3);
		findHeadingsByLine.push(headingsByLine4);
		findHeadingsByLine.push(headingsByLine5);
		findHeadingsByLine.push(headingsByLine6);
		
		
		// Return the connections back to the pool.
		// 9/22/08 TWalton change to use Connection Stack
		ConnectionStack.returnConnection(conn1);
		ConnectionStack.returnConnection(conn2);
		ConnectionStack.returnConnection(conn3);
		ConnectionStack.returnConnection(conn4);
		ConnectionStack.returnConnection(conn5);
		ConnectionStack.returnConnection(conn6);			

	}
    catch (SQLException e) {
		System.out.println("SQL exception occured at com.treetop.data.FormHeadings.init()" + e);
	}

	}
	
}
/**
 * Insert a record into the Form System Headings.
 *
 * Creation date: (8/18/2003 1:50:29 PM)
 */
private void insert() {

	try {
		
		PreparedStatement insertData = (PreparedStatement) sqlInsert.pop();
	
		insertData.setString(1, recordHdgId);
		insertData.setInt(2, formNumber.intValue());
		insertData.setInt(3, formLineHdgNumber.intValue());
		insertData.setInt(4, columnNumberFrom.intValue());
		insertData.setInt(5, columnNumberTo.intValue());
		insertData.setString(6, columnHeading);
		insertData.setString(7, updateHdgUser);
		insertData.setDate(8, updateHdgDate);
		insertData.setTime(9, updateHdgTime);
				
		insertData.executeUpdate();
		
		sqlInsert.push(insertData);
		
	}	
	catch (SQLException e) { 
		System.out.println("SQL error at com.treetop.data.FormHeadings.insert(): " + e);
	}
	
}
/**
 * Load fields from data base record.
 *
 * Creation date: (8/12/2003 8:24:29 AM)
 */
protected void loadFields(ResultSet rs, String loadMode) {

	try {
		
		recordHdgId       	= rs.getString("FMGREC");
		formNumber        	= new Integer(rs.getInt("FMGNBR"));
	    formLineHdgNumber 	= new Integer(rs.getInt("FMGHLN"));
		columnNumberFrom  	= new Integer(rs.getInt("FMGFCL"));
		columnNumberTo    	= new Integer(rs.getInt("FMGTCL"));
		columnHeading     	= rs.getString("FMGHDG");
		updateHdgUser     	= rs.getString("FMGUSR");
		updateHdgUserName 	= updateHdgUser;
		updateHdgDate     	= rs.getDate("FMGDTE");
		updateHdgTime     	= rs.getTime("FMGTME");
														
		try {
		
			if (loadMode.equals ("all")) {
			UserFile newUser    = new UserFile(updateHdgUser);
			Integer  newUserNum = new Integer(newUser.getUserNumber());
			newUser = new UserFile(newUserNum);
			updateHdgUserName = newUser.getUserNameLong();
			}
			
		}
		catch (Exception e) {		
		}
				
	
	}
	catch (Exception e) {	
		System.out.println("SQL Exception at com.treetop.data.FormHeadings" +
			               ".loadFields(RS String): " + e);
	}
			
}
/**
 * Form headings file testing.
 *
 * Creation date: (8/18/2003 8:24:29 AM)
 */
public static void main(String[] args) {
	

	// find headings. 

	try {

		Integer value1  = new Integer(13);
		Vector headings = new Vector();		
	           headings = findHeadingsByForm(value1);

	    System.out.println("Find by Form: " + value1 + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormHeadings.main() on find1: " + e);		
	}


	try {

		Integer value1  = new Integer(13);
		Integer value2  = new Integer(1);
		Vector headings = new Vector();		
	           headings = findHeadingsByLine(value1, value2);

	    System.out.println("Find by Line: " + value1 + " " + value2 + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormHeadings.main() on find2: " + e);
	}		

		
	// insert headings. 
	
	try {
		
		java.sql.Date theDate = java.sql.Date.valueOf("2003-05-19");
	    java.sql.Time theTime = java.sql.Time.valueOf("03:45:10");
	    Integer value1 = new Integer(5544);
	    Integer value2 = new Integer(1);
	    Integer value3 = new Integer(2);
	    Integer value4 = new Integer(4);
	    FormHeadings heading1 = new FormHeadings();
	    heading1.setRecordHdgId("HG");
	    heading1.setFormNumber(value1);
	    heading1.setFormLineHdgNumber(value2);
	    heading1.setColumnNumberFrom(value3);
	    heading1.setColumnNumberTo(value4);
	    heading1.setColumnHeading("This is the special heading");
	    heading1.setUpdateHdgUser("DEISEN");
	    heading1.setUpdateHdgDate(theDate);
	    heading1.setUpdateHdgTime(theTime);
	    heading1.insert();
	   	    
		System.out.println("First insert: " + heading1 + " successfull");
	}				
    catch (Exception e) {
		System.out.println("Error: FormHeadings.main() on insert1: " + e);
	}
		

	// update headings.

	try { 

		Integer value1 = new Integer(5544);
		Integer value2 = new Integer(1);
		FormHeadings heading1 = new FormHeadings(value1, value2);
		heading1.setColumnHeading("Column Heading Description");
		heading1.update();
		
		System.out.println("First update: " + heading1 + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormHeadings.main() on update1:" + e);
	}
	
	
	try { 

		Integer value1 = new Integer(5544);
		Integer value2 = new Integer(1);
		Integer value3 = new Integer(1);
		Integer value4 = new Integer(4);
		FormHeadings heading1 = new FormHeadings(value1, value2);
		heading1.setColumnNumberFrom(value3);
		heading1.setColumnNumberTo(value4);
		heading1.update();
				
		System.out.println("Second update: " + heading1 + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormHeadings.main() on update2:" + e);
	}
	

	// delete headers. 
			
	try {

		Integer value1 = new Integer(5544);
		Integer value2 = new Integer(1);
		FormHeadings heading1 = new FormHeadings(value1, value2);
		heading1.delete();
		
		System.out.println("First delete: " + heading1 + " successfull");
	}
	catch (Exception e) {
		System.out.println("Error: FormHeadings.main() on delete1:" + e);
	}

}
/**
 * Set column heading Text.
 *
 * Creation date: (8/18/2003 10:27:28 AM)
 */
public void setColumnHeading(String inHeading) throws InvalidLengthException {
	
	if (inHeading.length() > 50)
		throw new InvalidLengthException(
				"inHeading", inHeading.length(), 50);

	this.columnHeading = inHeading;
}
/**
 * Set form 'from' column number
 *
 * Creation date: (8/18/2003 10:27:28 AM)
 */
public void setColumnNumberFrom(Integer inColumnNumberFrom) {
		
	this.columnNumberFrom = inColumnNumberFrom;
}
/**
 * Set form 'to' column number
 *
 * Creation date: (8/18/2003 10:27:28 AM)
 */
public void setColumnNumberTo(Integer inColumnNumberTo) {
		
	this.columnNumberTo = inColumnNumberTo;
}
/**
 * Set form heading line number.
 *
 * Creation date: (8/18/2003 10:27:28 AM)
 */
public void setFormLineHdgNumber(Integer inFormLineHdgNumber) {
		
	this.formLineHdgNumber = inFormLineHdgNumber;
}
/**
 * Set form number.
 *
 * Creation date: (8/18/2003 10:27:28 AM)
 */
public void setFormNumber(Integer inFormNumber) {
		
	this.formNumber = inFormNumber;
}
/**
 * Set the record identification code.
 *
 * Creation date: (8/18/2003 10:27:28 AM)
 */
public void setRecordHdgId(String inRecordId) throws InvalidLengthException {
	
	if (inRecordId.length() > 2)
		throw new InvalidLengthException(
				"inRecordId", inRecordId.length(), 2);

	this.recordHdgId = inRecordId;
}
/**
 * Set the date of the last record updated.
 *
 * Creation date: (8/18/2003 10:27:28 AM)
 */
public void setUpdateHdgDate(java.sql.Date inUpdateDate) {
		
	this.updateHdgDate = inUpdateDate;
}
/**
 * Set the time of the last record updated.
 *
 * Creation date: (8/18/2003 10:27:28 AM)
 */
public void setUpdateHdgTime(java.sql.Time inUpdateTime) {
		
	this.updateHdgTime = inUpdateTime;
}
/**
 * Set update user profile.
 *
 * Creation date: (8/18/2003 10:27:28 AM)
 */
public void setUpdateHdgUser(String inUpdateUser) throws InvalidLengthException {
	
	if (inUpdateUser.length() > 10)
		throw new InvalidLengthException(
				"inUpdateUser", inUpdateUser.length(), 10);

	this.updateHdgUser = inUpdateUser;
}
/**
 * Set update user profile name.
 *
 * Creation date: (8/18/2003 10:27:28 AM)
 */
public void setUpdateHdgUserName(String inUpdateUserName) throws InvalidLengthException {
	
	if (inUpdateUserName.length() > 30)
		throw new InvalidLengthException(
				"inUpdateUserName", inUpdateUserName.length(), 30);

	this.updateHdgUserName = inUpdateUserName;
}
/**
 * Update a record in the Form System Headings.
 *
 * Creation date: (8/18/2003 1:50:29 PM)
 */
private void update(){

	try {
		
		PreparedStatement updateData = (PreparedStatement) sqlUpdate.pop();
	
		updateData.setString(1, recordHdgId);
		updateData.setInt(2, formNumber.intValue());
		updateData.setInt(3, formLineHdgNumber.intValue());
		updateData.setInt(4, columnNumberFrom.intValue());
		updateData.setInt(5, columnNumberTo.intValue());	
		updateData.setString(6, columnHeading);
		updateData.setString(7, updateHdgUser);
		updateData.setDate(8, updateHdgDate);
		updateData.setTime(9, updateHdgTime);

		updateData.setInt(10, formNumber.intValue());
		updateData.setInt(11, formLineHdgNumber.intValue());
					
		updateData.executeUpdate();
		
		sqlUpdate.push(updateData);
		
	} 
	catch (SQLException e) {	
		System.out.println("SQL error at com.treetop.data.FormHeadings.update(): " + e);
	}
	
}
}
