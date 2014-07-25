package com.treetop.data;

import java.sql.*;
import java.util.*;
import com.treetop.utilities.ConnectionStack;
/**
 * Access to Form System Data Values file DBLIB/FMPESECU
 *       9/22/08 TWalton changed to NEW BOX -  Library DBPRD
 **/
public class FormSecurity {
	

	// Data base fields.

	private 	String        	recordSecId; 
	private 	Integer       	roleNumber;
	private 	Integer       	groupNumber;
	private 	String       	userProfile;
	private 	Integer      	formNumber;
	private 	Integer       	columnNumber;
	private 	String        	secureInquiry;
	private 	String        	secureUpdate;
	private 	String        	updateSecUser;
	private 	String        	updateSecUserName;
	private 	java.sql.Date 	updateSecDate;
	private 	java.sql.Time 	updateSecTime;
																				

	// Define database environment (live or test) on the AS/400.
	
//	private static String library = "WKLIB."; // test environment
	// 9/22/08 TWalton Change to point to the NEW Machine
    //private static String library = "DBLIB."; // live environment
    private static String library = "DBPRD."; // live Environment

	// For use in Main Method,
    // Constructor Methods and Lookup Methods.
 
	private static Stack sqlDelete = null;
	private static Stack sqlInsert = null;
	private static Stack sqlUpdate = null;

		
	// Additional fields.
	
	private static boolean persists = false;
	
	
/**
 * Instantiate the form security.
 *
 * Creation date: (10/10/2003 10:36:39 AM)
 */
public FormSecurity() {

	init();
	
}
/**
 * Delete a record from the Form System Security.
 *
 * Creation date: (10/14/2003 1:50:29 PM)
 */
private void delete() {

	try {
		
		PreparedStatement deleteData = (PreparedStatement) sqlDelete.pop(); 
			
		deleteData.setInt(1, roleNumber.intValue());
		deleteData.setInt(2, groupNumber.intValue());
		deleteData.setString(3, userProfile);
		deleteData.setInt(4, formNumber.intValue());
		deleteData.setInt(5, columnNumber.intValue());
				
		deleteData.executeUpdate();
		
		sqlDelete.push(deleteData);
		
	} 
	catch (SQLException e) {	
		System.out.println("SQL error at com.treetop.data.FormSecurity.delete(): " + e);
	}
	
}
/**
 * Retrieve the security column number.
 *
 * Creation date: (10/14/2003 10:53:28 AM)
 */
public Integer getColumnNumber() 
{
	return columnNumber;	
}
/**
 * Retrieve the security form number.
 *
 * Creation date: (10/14/2003 10:53:28 AM)
 */
public Integer getFormNumber() 
{
	return formNumber;	
}
/**
 * Retrieve the security group number.
 *
 * Creation date: (10/14/2003 10:53:28 AM)
 */
public Integer getGroupNumber() 
{
	return groupNumber;	
}
/**
 * Retrieve the security record identification code.
 *
 * Creation date: (10/14/2003 4:45:28 PM)
 */
public String getRecordSecId() {

	return recordSecId;	
}
/**
 * Retrieve the security role number.
 *
 * Creation date: (10/14/2003 10:53:28 AM)
 */
public Integer getRoleNumber() 
{
	return roleNumber;	
}
/**
 * Retrieve the security code to allow inquiry.
 *
 * Creation date: (10/14/2003 4:45:28 PM)
 */
public String getSecureInquiry() {

	return secureInquiry;	
}
/**
 * Retrieve the security code to allow update.
 *
 * Creation date: (10/14/2003 4:45:28 PM)
 */
public String getSecureUpdate() {

	return secureUpdate;	
}
/**
 * Retrieve the security last update date. 
 *
 * Creation date: (10/14/2003 4:45:28 PM)
 */
public java.sql.Date getUpdateSecDate() {

	return updateSecDate;	
}
/**
 * Retrieve the security last update time. 
 *
 * Creation date: (10/14/2003 4:45:28 PM)
 */
public java.sql.Time getUpdateSecTime() {

	return updateSecTime;	
}
/**
 * Retrieve the security last update user profile.
 *
 * Creation date: (10/14/2003 4:45:28 PM)
 */
public String getUpdateSecUser() {

	return updateSecUser;	
}
/**
 * Retrieve the security last update user profile name.
 *
 * Creation date: (10/14/2003 4:45:28 PM)
 */
public String getUpdateSecUserName() {

	return updateSecUserName;	
}
/**
 * Retrieve the security user profile.
 *
 * Creation date: (10/14/2003 4:45:28 PM)
 */
public String getUserProfile() {

	return userProfile;	
}
/**
 * SQL definitions.
 *
 * Creation date: (10/10/2003 8:24:29 AM)
 */
public void init() {
	
	// Test for prior initialization.
	
	if (persists == false) {	
		persists = true;
	    

	// Perform initialization.	 
 
	try {
		// 9/22/08 TWalton change from ConnectionPool to ConnectionStack
		Connection conn1 = ConnectionStack.getConnection();
		Connection conn2 = ConnectionStack.getConnection();
		Connection conn3 = ConnectionStack.getConnection();
		Connection conn4 = ConnectionStack.getConnection();
		Connection conn5 = ConnectionStack.getConnection();
		Connection conn6 = ConnectionStack.getConnection();


		String deleteSecurity =  
			"DELETE FROM " + library + "FMPESECU " +
			"WHERE FMEROL = ? AND FMEGRP = ? AND FMEUSR = ? AND FMENBR = ? AND FMECOL = ?";
			
		PreparedStatement deleteSecurity1 = conn1.prepareStatement(deleteSecurity);
		PreparedStatement deleteSecurity2 = conn2.prepareStatement(deleteSecurity);
		PreparedStatement deleteSecurity3 = conn3.prepareStatement(deleteSecurity);
		PreparedStatement deleteSecurity4 = conn4.prepareStatement(deleteSecurity);
		PreparedStatement deleteSecurity5 = conn5.prepareStatement(deleteSecurity);
		PreparedStatement deleteSecurity6 = conn6.prepareStatement(deleteSecurity);
		

		sqlDelete = new Stack();
		sqlDelete.push(deleteSecurity1);
		sqlDelete.push(deleteSecurity2);
		sqlDelete.push(deleteSecurity3);
		sqlDelete.push(deleteSecurity4);
		sqlDelete.push(deleteSecurity5);
		sqlDelete.push(deleteSecurity6);		
		

		String insertSecurity = 
			"INSERT INTO " + library + "FMPESECU " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
		PreparedStatement insertSecurity1 = conn1.prepareStatement(insertSecurity);
		PreparedStatement insertSecurity2 = conn2.prepareStatement(insertSecurity);
		PreparedStatement insertSecurity3 = conn3.prepareStatement(insertSecurity);
		PreparedStatement insertSecurity4 = conn4.prepareStatement(insertSecurity);
		PreparedStatement insertSecurity5 = conn5.prepareStatement(insertSecurity);
		PreparedStatement insertSecurity6 = conn6.prepareStatement(insertSecurity);
		

		sqlInsert = new Stack();
		sqlInsert.push(insertSecurity1);
		sqlInsert.push(insertSecurity2);
		sqlInsert.push(insertSecurity3);
		sqlInsert.push(insertSecurity4);
		sqlInsert.push(insertSecurity5);
		sqlInsert.push(insertSecurity6);		
			         

		String updateSecurity = 
			"UPDATE " + library + "FMPESECU " +
			"SET FMEREC = ?, FMEROL = ?, FMEGRP = ?, FMEUSR = ?, FMENBR = ?, FMECOL = ?, " + 
			    "FMEINQ = ?, FMEUPD = ?, FMEUUS = ?, FMEUDT = ?, FMEUTM = ? " +
			"WHERE FMEROL = ? AND FMEGRP = ? AND FMEUSR = ? AND FMENBR = ? AND FMECOL = ?";
			
		PreparedStatement updateSecurity1 = conn1.prepareStatement(updateSecurity);
		PreparedStatement updateSecurity2 = conn2.prepareStatement(updateSecurity);
		PreparedStatement updateSecurity3 = conn3.prepareStatement(updateSecurity);
		PreparedStatement updateSecurity4 = conn4.prepareStatement(updateSecurity);
		PreparedStatement updateSecurity5 = conn5.prepareStatement(updateSecurity);
		PreparedStatement updateSecurity6 = conn6.prepareStatement(updateSecurity);
		

		sqlUpdate = new Stack();
		sqlUpdate.push(updateSecurity1);
		sqlUpdate.push(updateSecurity2);
		sqlUpdate.push(updateSecurity3);
		sqlUpdate.push(updateSecurity4);
		sqlUpdate.push(updateSecurity5);
		sqlUpdate.push(updateSecurity6);
		
		
		// Return the connections back to the pool.
		
		ConnectionStack.returnConnection(conn1);
		ConnectionStack.returnConnection(conn2);
		ConnectionStack.returnConnection(conn3);
		ConnectionStack.returnConnection(conn4);
		ConnectionStack.returnConnection(conn5);
		ConnectionStack.returnConnection(conn6);		
	
	}
    catch (SQLException e) {
		System.out.println("SQL exception occured at com.treetop.data.FormSecurity.init()" + e);
	}

	}
	
}
/**
 * Insert a record into the Form System Security.
 *
 * Creation date: (10/14/2003 1:50:29 PM)
 */
private void insert() {

	try {
			
		PreparedStatement insertData = (PreparedStatement) sqlInsert.pop();

		insertData.setString(1, recordSecId);
		insertData.setInt(2, roleNumber.intValue());
		insertData.setInt(3, groupNumber.intValue());
		insertData.setString(4, userProfile);
		insertData.setInt(5, formNumber.intValue());
		insertData.setInt(6, columnNumber.intValue());
	    insertData.setString(7, secureInquiry);
	    insertData.setString(8, secureUpdate);
		insertData.setString(9, updateSecUser);
		insertData.setDate(10, updateSecDate);
		insertData.setTime(11, updateSecTime);
			
		insertData.executeUpdate();
		
		sqlInsert.push(insertData);
		
	}	
	catch (SQLException e) {	
		System.out.println("SQL error at com.treetop.data.FormSecurity.insert(): " + e);
	}
	
}
/**
 * Set security column number.
 *
 * Creation date: (10/14/2003 10:27:28 AM)
 */
public void setColumnNumber(Integer inColumnNumber) {
		
	this.columnNumber = inColumnNumber;
}
/**
 * Set security form number.
 *
 * Creation date: (10/14/2003 10:27:28 AM)
 */
public void setFormNumber(Integer inFormNumber) {
		
	this.formNumber = inFormNumber;
}
/**
 * Set security group number.
 *
 * Creation date: (10/14/2003 10:27:28 AM)
 */
public void setGroupNumber(Integer inGroupNumber) {
		
	this.groupNumber = inGroupNumber;
}
/**
 * Set the security record identification code.
 *
 * Creation date: (10/14/2003 10:27:28 AM)
 */
public void setRecordSecId(String inRecordId) throws InvalidLengthException {
	
	if (inRecordId.length() > 2)
		throw new InvalidLengthException(
				"inRecordId", inRecordId.length(), 2);

	this.recordSecId = inRecordId;
}
/**
 * Set security Role number.
 *
 * Creation date: (10/14/2003 10:27:28 AM)
 */
public void setRoleNumber(Integer inRoleNumber) {
		
	this.roleNumber = inRoleNumber;
}
/**
 * Set the security code to allow inquiry.
 *
 * Creation date: (10/14/2003 10:27:28 AM)
 */
public void setSecureInquiry(String inSecureInquiry) throws InvalidLengthException {
	
	if (inSecureInquiry.length() > 1)
		throw new InvalidLengthException(
				"inSecureInquiry", inSecureInquiry.length(), 1);

	this.secureInquiry = inSecureInquiry;
}
/**
 * Set the security code to allow update.
 *
 * Creation date: (10/14/2003 10:27:28 AM)
 */
public void setSecureUpdate(String inSecureUpdate) throws InvalidLengthException {
	
	if (inSecureUpdate.length() > 1)
		throw new InvalidLengthException(
				"inSecureUpdate", inSecureUpdate.length(), 1);

	this.secureUpdate = inSecureUpdate;
}
/**
 * Set the security date of the last record updated.
 *
 * Creation date: (10/14/2003 10:27:28 AM)
 */
public void setUpdateSecDate(java.sql.Date inUpdateDate) {
		
	this.updateSecDate = inUpdateDate;
}
/**
 * Set the security time of the last record updated.
 *
 * Creation date: (10/14/2003 10:27:28 AM)
 */
public void setUpdateSecTime(java.sql.Time inUpdateTime) {
		
	this.updateSecTime = inUpdateTime;
}
/**
 * Set security update user profile.
 *
 * Creation date: (10/14/2003 10:27:28 AM)
 */
public void setUpdateSecUser(String inUpdateUser) throws InvalidLengthException {
	
	if (inUpdateUser.length() > 10)
		throw new InvalidLengthException(
				"inUpdateUser", inUpdateUser.length(), 10);

	this.updateSecUser = inUpdateUser;
}
/**
 * Set security update user profile name.
 *
 * Creation date: (8/18/2003 10:27:28 AM)
 */
public void setUpdateSecUserName(String inUpdateUserName) throws InvalidLengthException {
	
	if (inUpdateUserName.length() > 30)
		throw new InvalidLengthException(
				"inUpdateUserName", inUpdateUserName.length(), 30);

	this.updateSecUserName = inUpdateUserName;
}
/**
 * Set the security user profile.
 *
 * Creation date: (10/14/2003 10:27:28 AM)
 */
public void setUserProfile(String inUserProfile) throws InvalidLengthException {
	
	if (inUserProfile.length() > 10)
		throw new InvalidLengthException(
				"inUserProfile", inUserProfile.length(), 10);

	this.userProfile = inUserProfile;
}
/**
 * Update a record into the Form System Security.
 *
 * Creation date: (10/14/2003 1:50:29 PM)
 */
private void update() {

	try {
		
		PreparedStatement updateData = (PreparedStatement) sqlUpdate.pop();

		updateData.setString(1, recordSecId);
		updateData.setInt(2, roleNumber.intValue());
		updateData.setInt(3, groupNumber.intValue());
		updateData.setString(4, userProfile);
		updateData.setInt(5, formNumber.intValue());
		updateData.setInt(6, columnNumber.intValue());
	    updateData.setString(7, secureInquiry);
	    updateData.setString(8, secureUpdate);
		updateData.setString(9, updateSecUser);
		updateData.setDate(10, updateSecDate);
		updateData.setTime(11, updateSecTime);

		updateData.setInt(12, roleNumber.intValue()); 
		updateData.setInt(13, groupNumber.intValue());
		updateData.setString(14, userProfile);
		updateData.setInt(15, formNumber.intValue());
		updateData.setInt(16, columnNumber.intValue());
				
		updateData.executeUpdate();
		
		sqlUpdate.push(updateData);
		
	}	
	catch (SQLException e) { 
		System.out.println("SQL error at com.treetop.data.FormSecurity.update(): " + e);
	}
	
}
}
