package com.treetop.data;

import java.text.*;
import java.sql.*;
import java.util.*;
import javax.sql.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import com.treetop.utilities.ConnectionStack;


/**
 * Access to RDB file DBLIB/SRPGUSER
 *
 * Code used to generate the table.
 *
 * CREATE TABLE DBLIB/SRPGUSER 
 *  SRGTYPE    CHAR (10)  (tech, salesrep, etc.)
 *  SRGINITIAL CHAR ( 3)  User name initials
 *  SRGNAME    CHAR (30)  Name
 *  SRGPROFILE CHAR (10)  User Profile
 *  SRGSHIP    CHAR ( 1)  eMail for Shipping
 *  SRGFOLLOW  CHAR ( 1)  eMail for Follow Up
 *  SRGFEEDBK  CHAR ( 1)  eMail for Feedback
 *  SRGUPDDATE DATE		  Last Date Updated
 *  SRGUPDTIME TIME		  Last Time Updated
 *  SRGUPDUSER CHAR (10)  Last User to Update
 * 
 **/
public class SampleRequestUsers {

	private String        userType;
	private String        userInitials;
	private String        userName;
	private String        userProfile;
	private String        notifyShipped;
	private String        notifyFollowUp;
	private String        notifyFeedback;
	private java.sql.Date updateDate;
	private java.sql.Time updateTime;
	private String        updateUser;
	private String        updateUserName;


	//**** For use in Main Method,
    // Constructor Methods and 
    // Insert, Update, Delete and Lookup Methods  ****//
    
	private static PreparedStatement sqlDelete;
	private static PreparedStatement sqlInsert;
	private static PreparedStatement sqlUpdate;

	private static PreparedStatement findUserType;

	private static PreparedStatement findAllByName;
	private static PreparedStatement findAllByProfile;
	
	private static PreparedStatement findTypeByName;
	private static PreparedStatement findTypeByProfile;
	private static PreparedStatement findSampleUserByProfile;

	private static PreparedStatement findUserByName;
	private static PreparedStatement findUserByProfile;


	// Additional fields.
	
	private boolean persists = false;
	private static Connection connection;
/**
 * SampleRequestUsers constructor comment.
 */
public SampleRequestUsers() {

	init();
}
/**
 * Instantiate the sample request users class.
 *
 * Creation date: (6/11/2003 10:36:39 AM)
 */
public SampleRequestUsers(String inUserType, String inUserProfile) 
	throws InstantiationException { 

	if (connection == null)
		init();
		
	ResultSet rs = null;
	
	try {
		findUserByProfile.setString(1, inUserType);
		findUserByProfile.setString(2, inUserProfile.toUpperCase());
		
		rs = findUserByProfile.executeQuery();
		
		if (rs.next() == false)
			throw new InstantiationException("The user: " + inUserProfile + " not found");
			
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleReqeustUsers." +
			               "SampleReqeustUsers(String,String) " + e);
		return;
	}
	
	loadFields(rs);	
}
/**
 * Delete sample request user record.
 *.
 * Creation date: (6/11/2003 8:24:29 AM)
 */
public void delete() {

	try {
		sqlDelete.setString(1, userType);
		sqlDelete.setString(2, userProfile);
		
		sqlDelete.executeUpdate();
		
		
	} catch (SQLException e) {
		System.out.println("SQL Exception at com.treetop.data.SampleRequestUsers.delete(): " + e);
	}	
}
/**
 * Delete a single user record by profile.
 *
 * Creation date: (6/11/2003 8:24:29 AM)
 */
public boolean deleteUserByProfile(String inUserType, String inUserProfile) {

	try {
		sqlDelete.setString(1, inUserType);
		sqlDelete.setString(2, inUserProfile);

		sqlDelete.executeUpdate();		
		return true;
		
	} catch (Exception e) {
		System.out.println("SQL Exception at com.treetop.data.SampleRequestUsers.delete(): " + e);
		return false;
	}	
}
/**
 * Set result set for all users by name description for all types.
 *
 * Creation date: (6/13/2003 8:24:29 AM)
 */
public static Vector findAllByName() {

	Vector    allUsers = new Vector();
	ResultSet rs       = null;

	
	try {
		
		rs = findAllByName.executeQuery();

		try {
			while (rs.next())
			{
				SampleRequestUsers buildVector = new SampleRequestUsers();
				buildVector.loadFields(rs);
				allUsers.addElement(buildVector);
			}
			rs.close();
		}
		catch (Exception e){
			System.out.println("Exception error processing a result set " +
				               "(SampleRequestUsers.findAllByName): " + e);
		}
		
	}
	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestUsers." +
			               "findAllByName(): " + e);
	}
	
			
	return allUsers;	 
}
/**
 * Set result set for all users by user profile for all types.
 *
 * Creation date: (6/13/2003 8:24:29 AM)
 */
public static Vector findAllByProfile() {

	Vector    allUsers = new Vector();
	ResultSet rs       = null;

	
	try {
	
		rs = findAllByProfile.executeQuery();

		try {
			while (rs.next())
			{
				SampleRequestUsers buildVector = new SampleRequestUsers();
				buildVector.loadFields(rs);
				allUsers.addElement(buildVector);
			}
			rs.close();
		}
		catch (Exception e){
			System.out.println("Exception error processing a result set " +
				               "(SampleRequestUsers.findAllByProfile): " + e);
		}
		
	}
	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestUsers." +
			               "findAllByProfile(): " + e);
	}
	
			
	return allUsers;	
}
/**
 * Set result set for all users by name description for a type.
 *
 * Creation date: (6/13/2003 8:24:29 AM)
 */
public static Vector findTypeByName(String inUserType) {

	Vector    allUsers = new Vector();
	ResultSet rs       = null;

	
	try {
		findTypeByName.setString(1, inUserType);
		
		rs = findTypeByName.executeQuery();

		try {
			while (rs.next())
			{
				SampleRequestUsers buildVector = new SampleRequestUsers();
				buildVector.loadFields(rs);
				allUsers.addElement(buildVector);
			}
			
		}
		catch (Exception e){
		//	System.out.println("Exception error processing a result set " +
		//		               "(SampleRequestUsers.findTypeByName): " + e);
		}
		
	}
	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestUsers." +
			               "findTypeByName(): " + e);
	}
	finally
	{
		try
		{
		   if (rs != null)
			  rs.close();
		}
		catch(Exception e)
		{
		}
	}
	
	
			
	return allUsers;	
}
/**
 * Set result set for all users by user profile for a type.
 *
 * Creation date: (6/13/2003 8:24:29 AM)
 */
public static Vector findTypeByProfile(String inUserType) {

	Vector    allUsers = new Vector();
	ResultSet rs       = null;

	
	try {
		findTypeByProfile.setString(1, inUserType);
		
		rs = findTypeByProfile.executeQuery();

		try {
			while (rs.next())
			{
				SampleRequestUsers buildVector = new SampleRequestUsers();
				buildVector.loadFields(rs);
				allUsers.addElement(buildVector);
			}
			rs.close();
		}
		catch (Exception e){
			System.out.println("Exception error processing a result set " +
				               "(SampleRequestUsers.findTypeByProfile): " + e);
		}
		
	}
	 
	catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestUsers." +
			               "findTypeByProfile(): " + e);
	}
	
			
	return allUsers;	
}
/**
 * Set result set for a single user by name description.
 *
 * Creation date: (6/11/2003 8:24:29 AM)
 */
public static SampleRequestUsers findUserByName(String inUserType, String inUserName) {

	ResultSet rs = null;
	
	try {
		findUserByName.setString(1, inUserType);
		findUserByName.setString(2, inUserName);
		
		rs = findUserByName.executeQuery();
		
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestUsers." +
			               "findUserByName(): " + e);
		return null;
	}
	
	
	try {
		if (rs.next())
		{
			SampleRequestUsers aSampleRequestUser = new SampleRequestUsers();
			aSampleRequestUser.loadFields(rs);
			
			return aSampleRequestUser;
		}
		
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleReqeustUsers." +
			               "findUserByName(): " + e);
		return null;
	}
	
	return null;	
}
/**
 * Set result set for a single user by profile name.
 *
 * Creation date: (6/11/2003 8:24:29 AM)
 */
public static SampleRequestUsers findUserByProfile(String inUserType, String inUserProfile) {

	
	ResultSet rs = null;
	
	try {
		findUserByProfile.setString(1, inUserType);
		findUserByProfile.setString(2, inUserProfile.toUpperCase());
		
		rs = findUserByProfile.executeQuery();
		
				
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestUsers." +
			               "findUserByProfile(): " + e);
		return null;
	}

	
	try {
		if (rs.next())
		{
			SampleRequestUsers aSampleRequestUser = new SampleRequestUsers();
			aSampleRequestUser.loadFields(rs);
			
			return aSampleRequestUser;	    
		}
				
		
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleReqeustUsers." +
			               "findUserByProfile(): " + e);
		}
	
	return null;	
}
/**
 * Set result set for all users by name description for all types.
 *
 * Creation date: (6/13/2003 8:24:29 AM)
 */
public static ResultSet findUserType() {

	ResultSet rs = null;
	
		
	try {		

		rs = findUserType.executeQuery();
		
	}
	 
	catch (Exception e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestUsers." +
			               "findUserType(): " + e);
	}
	
			
	return rs;	 
}
/**
 * Set result set for all users by name description for all types.
 *
 * Creation date: (6/13/2003 8:24:29 AM)
 */
public ResultSet findUserTypes() {

	ResultSet rs = null;
	
		
	try {		

		rs = findUserType.executeQuery();
		
	}
	 
	catch (Exception e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestUsers." +
			               "findUserType(): " + e);
	}
	
			
	return rs;	
}
/**
 * Retrieve the email notification flag for customer feedback. 
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public String getNotifyFeedback() {

	return notifyFeedback;	
}
/**
 * Retrieve the email notification flag used for follow up contact by sales representive.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public String getNotifyFollowUp() {

	return notifyFollowUp;	
}
/**
 * Retrieve the email notification flag for shipped sample.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public String getNotifyShipped() {

	return notifyShipped;	
}
/**
 * Retrieve the last update date. 
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public java.sql.Date getUpdateDate() {

	return updateDate;	
}
/**
 * Retrieve the last update time. 
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public java.sql.Time getUpdateTime() {

	return updateTime;	
}
/**
 * Retrieve the last update user profile.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public String getUpdateUser() {

	return updateUser;	
}
/**
 * Retrieve the last update user profile.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public String getUpdateUserName() {

	return updateUserName;	
}
/**
 * Retrieve the user name initials.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public String getUserInitials() {

	return userInitials;	
}
/**
 * Retrieve the user name description.
 *
 * Creation date: (6/11/2003 4:45:28 PM)
 */
public String getUserName() {

	return userName;	
}
/**
 * Retrieve the user profile name.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public String getUserProfile() {

	return userProfile;	
}
/**
 * Retrieve the user record type.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public String getUserType() {

	return userType;	
}
/**
 * SQL definitions.
 *
 * Creation date: (6/11/2003 8:24:29 AM)
 */
public void init() {
	// Test for initial connection.
	if  (connection == null)
		connection = ConnectionStack.getConnection();
		//connection = SQLConnect.connection; // OLD BOX
	
	String ttlib = "DBPRD.";
	try {
		if (sqlDelete == null)
		{
	//	sqlDelete = connection.prepareStatement( 
	//		"DELETE FROM " + ttlib + "SRPGUSER " +
	//		" WHERE SRGTYPE = ? AND SRGPROFILE = ?");
		sqlDelete = connection.prepareStatement( 
				"UPDATE " + ttlib + "SRPGUSER " +
				" SET SRGTYPE = ' ' " +
				" WHERE SRGTYPE = ? AND SRGPROFILE = ?");

		sqlInsert = connection.prepareStatement(
			"INSERT INTO " + ttlib + "SRPGUSER " +
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			         

		sqlUpdate = connection.prepareStatement(
			"UPDATE " + ttlib + "SRPGUSER " +
			" SET SRGTYPE    = ?, SRGINITIAL = ?, SRGNAME    = ?, " +
			"     SRGPROFILE = ?, SRGSHIP    = ?, SRGFOLLOW  = ?, " +
			"     SRGFEEDBK  = ?, SRGUPDDATE = ?, SRGUPDTIME = ?, " +
			"     SRGUPDUSER = ?" +
			" WHERE SRGTYPE = ? AND SRGPROFILE = ?");

		
		findUserType = connection.prepareStatement(
			"SELECT * FROM " + ttlib + "GNPADESC " +
			" WHERE GNATYP = 'SRU'" +
			" ORDER BY GNASEQ");
		

		findAllByName = connection.prepareStatement(
			"SELECT * FROM " + ttlib + "SRPGUSER " +
			" ORDER BY SRGNAME");

		
		findAllByProfile = connection.prepareStatement(
			"SELECT * FROM " + ttlib + "SRPGUSER " +
			" ORDER BY SRGPROFILE");		
		
		
		findTypeByName = connection.prepareStatement(
			"SELECT * FROM " + ttlib + "SRPGUSER " + 
			" WHERE SRGTYPE = ?" +
			" ORDER BY SRGNAME");

		
		findTypeByProfile = connection.prepareStatement(
			"SELECT * FROM " + ttlib + "SRPGUSER " +
			" WHERE SRGTYPE = ?" +
		    " ORDER BY SRGPROFILE");		
		
		
		findUserByName = connection.prepareStatement(
			"SELECT * FROM " + ttlib + "SRPGUSER " +
		    " WHERE SRGTYPE = ? AND SRGNAME = ?");
		

		findUserByProfile = connection.prepareStatement(
			"SELECT * FROM " + ttlib + "SRPGUSER " +
		    " WHERE SRGTYPE = ? AND SRGPROFILE = ?");
		
		findSampleUserByProfile = connection.prepareStatement(
				"SELECT * FROM " + ttlib + "SRPGUSER " +
			    " WHERE SRGPROFILE = ?");
		}
	

	} catch (SQLException e) {
		System.out.println("SQL exception occured at com.treetop.data.SampleRequestUsers.init()" +
			               e);
	}
	ConnectionStack.returnConnection(connection);	
}
/**
 * Add sample request user record on insert.
 *
 * Creation date: (6/11/2003 8:24:29 AM)
 */
public void insert() {

	try {
		sqlInsert.setString(1, userType);
		sqlInsert.setString(2, userInitials);
		sqlInsert.setString(3, userName);
		sqlInsert.setString(4, userProfile);
		sqlInsert.setString(5, notifyShipped);
		sqlInsert.setString(6, notifyFollowUp);
		sqlInsert.setString(7, notifyFeedback);
		sqlInsert.setDate(8, updateDate);
		sqlInsert.setTime(9, updateTime);
		sqlInsert.setString(10, updateUser);		
	
		sqlInsert.executeUpdate();
		
		
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestUsers.insert(): " + e);
	}	
}
/**
 * Insert a sample request data record.
 *
 * Creation date: (5/12/2003 8:24:29 AM)
 */
public void insertSampleRequestUser(String userType,
							        String userInitials,
	                                String userName, 
			        	            String userProfile,
									String notifyShipped,
									String notifyFollowUp,
									String notifyFeedback,
						     java.sql.Date updateDate,
							 java.sql.Time updateTime,
									String updateUser)
									 
  	throws InvalidLengthException, Exception
{
	SampleRequestUsers newSampleRequestUser = new SampleRequestUsers();

	newSampleRequestUser.setUserType(userType);
	newSampleRequestUser.setUserInitials(userInitials);
	newSampleRequestUser.setUserName(userName);
	newSampleRequestUser.setUserProfile(userProfile);
	newSampleRequestUser.setNotifyShipped(notifyShipped);
	newSampleRequestUser.setNotifyFollowUp(notifyFollowUp);
	newSampleRequestUser.setNotifyFeedback(notifyFeedback);
	newSampleRequestUser.setUpdateDate(updateDate);
	newSampleRequestUser.setUpdateTime(updateTime);
	newSampleRequestUser.setUpdateUser(updateUser);
	
	newSampleRequestUser.insert();

	
}
/**
 * Load fields from data base record.
 *
 * Creation date: (6/11/2003 8:24:29 AM)
 */
protected void loadFields(ResultSet rs) {

	try {
		
		userType       = rs.getString("SRGTYPE");
		userInitials   = rs.getString("SRGINITIAL");
		userName       = rs.getString("SRGNAME");
		userProfile    = rs.getString("SRGPROFILE");
		notifyShipped  = rs.getString("SRGSHIP");
		notifyFollowUp = rs.getString("SRGFOLLOW");
		notifyFeedback = rs.getString("SRGFEEDBK");
		updateDate     = rs.getDate("SRGUPDDATE");
		updateTime     = rs.getTime("SRGUPDTIME");
		updateUser     = rs.getString("SRGUPDUSER");
		updateUserName = updateUser;

		try
		{
			UserFile newUser = new UserFile(updateUser);
			Integer  newUserNum = new Integer(newUser.getUserNumber());
			newUser = new UserFile(newUserNum);
			updateUserName = newUser.getUserNameLong();
			
		}
		catch (Exception e)
		{
			// If the person is not found, it will put the profile into the
		//	updateUserName field.
		//	System.out.println("Person not found in User File.  Problem occured " +
	    //                       " in SampleRequestUser.loadFields " + e);   			
		}
	
	}
	catch (Exception e)
	{
		System.out.println("SQL Exception at com.treetop.data.SampleRequestUser" +
			               ".loadFields();" + e);
	}

	persists = true;
				
}
/**
 * Sample request user file testing.
 *
 * Creation date: (6/12/2003 8:24:29 AM)
 */
public static void main(String[] args) {
	

	// insert users. ----------------------
	
	try {

		java.sql.Date theDate = java.sql.Date.valueOf("2003-05-19");
	    java.sql.Time theTime = java.sql.Time.valueOf("03:45:10");
	    SampleRequestUsers user1 = new SampleRequestUsers();
	    user1.setUserType("sales");
	    user1.setUserInitials("BDN");
	    user1.setUserName("Brad D. Nelson");
	    user1.setUserProfile("bnelso");
	    user1.setNotifyShipped("Y");
	    user1.setNotifyFollowUp("N");
	    user1.setNotifyFeedback("Y");
	    user1.setUpdateDate(theDate);
	    user1.setUpdateTime(theTime);
	    user1.setUpdateUser("deisen");
	    user1.insert();
	 
		System.out.println("User1: " + user1);
		
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.SampleRequestUsers1.main()");
	}

	try {

		java.sql.Date theDate = java.sql.Date.valueOf("2003-06-21");
	    java.sql.Time theTime = java.sql.Time.valueOf("10:15:01");
	    SampleRequestUsers user2 = new SampleRequestUsers();
	    user2.setUserType("sales");
	    user2.setUserInitials("XYZ");
	    user2.setUserName("Fred X. Flintstone");
	    user2.setUserProfile("fflint");
	    user2.setNotifyShipped("N");
	    user2.setNotifyFollowUp("N");
	    user2.setNotifyFeedback("N");
	    user2.setUpdateDate(theDate);
	    user2.setUpdateTime(theTime);
	    user2.setUpdateUser("uprofi");
	    user2.insert();
		
		System.out.println("User2: " + user2);
		
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.SampleRequestUsers2.main()");
	}

	
	try {

		java.sql.Date theDate = java.sql.Date.valueOf("2003-06-25");
	    java.sql.Time theTime = java.sql.Time.valueOf("10:31:45");
	    SampleRequestUsers user3 = new SampleRequestUsers();
	    user3.setUserType("tech");
	    user3.setUserInitials("T S");
	    user3.setUserName("Teresa Sweetwood");
	    user3.setUserProfile("tsweet");
	    user3.setNotifyShipped("N");
	    user3.setNotifyFollowUp("N");
	    user3.setNotifyFeedback("N");
	    user3.setUpdateDate(theDate);
	    user3.setUpdateTime(theTime);
	    user3.setUpdateUser("twalto");
	    user3.insert();	
			
		System.out.println("User3: " + user3);
		
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.SampleRequestUsers3.main()");
	}
	

	try {

		java.sql.Date theDate = java.sql.Date.valueOf("2003-06-25");
	    java.sql.Time theTime = java.sql.Time.valueOf("10:32:02");
	    SampleRequestUsers user4 = new SampleRequestUsers();
	    user4.setUserType("tech");
	    user4.setUserInitials("D F");
	    user4.setUserName("Debi Flores");
	    user4.setUserProfile("dflore");
	    user4.setNotifyShipped("N");
	    user4.setNotifyFollowUp("N");
	    user4.setNotifyFeedback("N");
	    user4.setUpdateDate(theDate);
	    user4.setUpdateTime(theTime);
	    user4.setUpdateUser("twalto");
	    user4.insert();	
		
		System.out.println("User4: " + user4);
		
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.SampleRequestUsers4.main()");
	}
	
	
	try {

		java.sql.Date theDate = java.sql.Date.valueOf("1969-08-23");
	    java.sql.Time theTime = java.sql.Time.valueOf("05:01:59");
	    SampleRequestUsers user5 = new SampleRequestUsers();
	    user5.setUserType("received");
	    user5.setUserInitials("JOY");
	    user5.setUserName("Joy Campbell");
	    user5.setUserProfile("jcambe");
	    user5.setNotifyShipped("N");
	    user5.setNotifyFollowUp("N");
	    user5.setNotifyFeedback("N");
	    user5.setUpdateDate(theDate);
	    user5.setUpdateTime(theTime);
	    user5.setUpdateUser("kwange");
	    user5.insert();	
		
		System.out.println("User5: " + user5);
		
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.SampleRequestUsers5.main()");
	}
	
 
	try {

		java.sql.Date theDate = java.sql.Date.valueOf("2003-06-25");
	    java.sql.Time theTime = java.sql.Time.valueOf("10:34:15");
	    SampleRequestUsers user6 = new SampleRequestUsers();
	    user6.setUserType("tech");
	    user6.setUserInitials("D W");
	    user6.setUserName("Doug Webster");
	    user6.setUserProfile("dwebst");
	    user6.setNotifyShipped("N");
	    user6.setNotifyFollowUp("N");
	    user6.setNotifyFeedback("N");
	    user6.setUpdateDate(theDate);
	    user6.setUpdateTime(theTime);
	    user6.setUpdateUser("twalto");
	    user6.insert();	
	
		System.out.println("User6: " + user6);
		
		} catch (Exception e) {
		System.out.println("error at com.treetop.data.SampleRequestUsers6.main()");
	}


		try {

		java.sql.Date theDate = java.sql.Date.valueOf("2003-06-25");
	    java.sql.Time theTime = java.sql.Time.valueOf("10:37:44");
	    SampleRequestUsers user7 = new SampleRequestUsers();
	    user7.setUserType("tech");
	    user7.setUserInitials("S S");
	    user7.setUserName("Scott Summers");
	    user7.setUserProfile("ssumme");
	    user7.setNotifyShipped("N");
	    user7.setNotifyFollowUp("N");
	    user7.setNotifyFeedback("N");
	    user7.setUpdateDate(theDate);
	    user7.setUpdateTime(theTime);
	    user7.setUpdateUser("twalto");
	    user7.insert();	
				
		System.out.println("User7: " + user7);
		
		} catch (Exception e) {
		System.out.println("error at com.treetop.data.SampleRequestUsers6.main()");
	}
	
		
	
	// update users. ----------------------

	try { 

		SampleRequestUsers user1 = new SampleRequestUsers("sales", "bnelso");
		user1.setUserInitials("BIG");
		user1.update();
		System.out.println("First update successfull");		

	} catch (Exception e) {
		System.out.println("update1 failed: " + e);
	}


	try {

		SampleRequestUsers user2 = new SampleRequestUsers("tech", "tsweet");
		user2.setNotifyFeedback("1");
		user2.update();
		System.out.println("Second update successfull");		

	} catch (Exception e) {
		System.out.println("update2 failed: " + e);
	}


	try {

		SampleRequestUsers user3 = new SampleRequestUsers("received", "jcambe");
		user3.setUpdateDate(java.sql.Date.valueOf("2000-07-04"));
		user3.update();
		System.out.println("Third update successfull");		

	} catch (Exception e) {
		System.out.println("update3 failed: " + e);
	}


	try {

		SampleRequestUsers user4 = new SampleRequestUsers("tech", "fflint");
		user4.setUpdateUser("deisen");
		user4.setUserName("Flintstone, Fred, Rock");
		user4.setUpdateTime(java.sql.Time.valueOf("04:34:44"));
		user4.update();
		System.out.println("Forth update successfull");		

	} catch (Exception e) {
		System.out.println("update4 failed: " + e);
	}
	
	

		
	// search users. ----------------------

	try {

//		Vector vector1 = findTypeByName("sales"); 
		System.out.println("First search successfull");		 

	} catch (Exception e) {
		System.out.println("search1 failed: " + e);

	}


	try {

//		Vector vector2 = findTypeByName("tech"); 
		System.out.println("Second search successfull");		 

	} catch (Exception e) {
		System.out.println("search2 failed: " + e);

	}


	
	try {

		Vector vector3 = findTypeByProfile("tech"); 
		System.out.println("Third search successfull");		 

	} catch (Exception e) {
		System.out.println("search3 failed: " + e);

	}

	
	try {

		Vector vector4 = findAllByProfile(); 
		System.out.println("Fourth search successfull");		 

	} catch (Exception e) {
		System.out.println("search4 failed: " + e);

	}


	
	try {

		ResultSet set5 = findUserType(); 
		System.out.println("Fifth search successfull");		
		
		try {
			while (set5.next())
			{
				String checkBox = set5.getString("GNAD20");	
				System.out.println("User type: " + checkBox);	
			}
			set5.close();
		}
		catch (Exception e){
			System.out.println("Exception error processing a result set " +
				               "(checkBox): " + e);
		} 

	} catch (Exception e) {
		System.out.println("search5 failed: " + e);

	}

	

		
	// delete users. ----------------------
	
	try {

		SampleRequestUsers user1 = new SampleRequestUsers("sales", "bnelso");
		user1.delete();
		System.out.println("First delete successfull");		

	} catch (Exception e) {
		System.out.println("delete1 failed: " + e);
	}

	
	try {

		SampleRequestUsers user2 = new SampleRequestUsers("sales", "fflint");
		user2.delete();
		System.out.println("Second delete successfull");		

	} catch (Exception e) {
		System.out.println("delete2 failed: " + e);
	}

	
	try {

		SampleRequestUsers user3 = new SampleRequestUsers("tech", "tsweet");
		user3.delete();
		System.out.println("Third delete successfull");		

	} catch (Exception e) {
		System.out.println("delete3 failed: " + e);
	}


	try {

		SampleRequestUsers user4 = new SampleRequestUsers("tech", "creill");
		user4.delete();
		System.out.println("Forth delete successfull");		

	} catch (Exception e) {
		System.out.println("delete4 failed: " + e);
	}


	try {

		SampleRequestUsers user5 = new SampleRequestUsers("received", "jcambe");
		user5.delete();
		System.out.println("Fifth delete successfull");		

	} catch (Exception e) {
		System.out.println("delete5 failed: " + e);
	}


	try {

		SampleRequestUsers user6 = new SampleRequestUsers("tech", "fflint");
		user6.delete();
		System.out.println("Sixth delete successfull");		

	} catch (Exception e) {
		System.out.println("delete6 failed: " + e);
	}

}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void newMethod() {}
/**
 * Update the email notification flag for feedback from customer.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public void setNotifyFeedback(String inNotifyFeedback) throws InvalidLengthException {

	if (inNotifyFeedback.length() > 1)
		throw new InvalidLengthException(
			      "inNotifyFeedback: ", inNotifyFeedback.length(), 1);

	this.notifyFeedback = inNotifyFeedback;
}
/**
 * Update the email notification flag for follow up contact by sales representive.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public void setNotifyFollowUp(String inNotifyFollowUp) throws InvalidLengthException {

	if (inNotifyFollowUp.length() > 1)
		throw new InvalidLengthException(
			      "inNotifyFollowUp: ", inNotifyFollowUp.length(), 1);

	this.notifyFollowUp = inNotifyFollowUp;
}
/**
 * Update the email notification flag for shipped sample.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public void setNotifyShipped(String inNotifyShipped) throws InvalidLengthException {

	if (inNotifyShipped.length() > 1)
		throw new InvalidLengthException(
			      "inNotifyShipped: ", inNotifyShipped.length(), 1);

	this.notifyShipped = inNotifyShipped;
}
/**
 * Update the date of the last record update.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public void setUpdateDate(java.sql.Date inUpdateDate) {

	this.updateDate = inUpdateDate;
}
/**
 * Update the time of the last record update.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public void setUpdateTime(java.sql.Time inUpdateTime) {

	this.updateTime = inUpdateTime;
}
/**
 * Update the user profile that performed the last record update.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public void setUpdateUser(String inUpdateUser) throws InvalidLengthException {

	if (inUpdateUser.length() > 10)
		throw new InvalidLengthException(
			      "inUpdateUser: ", inUpdateUser.length(), 10);

	this.updateUser = inUpdateUser;
}
/**
 * Update the user profile that performed the last record update.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public void setUpdateUserName(String inUpdateUserName) throws InvalidLengthException {

	
	this.updateUserName = inUpdateUserName;
}
/**
 * Update the user name initials.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public void setUserInitials(String inUserInitials) throws InvalidLengthException {

	if (inUserInitials.length() > 3)
		throw new InvalidLengthException(
			      "inUserInitials: ", inUserInitials.length(), 3);

	this.userInitials = inUserInitials;
}
/**
 * Update the user name description.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public void setUserName(String inUserName) throws InvalidLengthException {

	if (inUserName.length() > 30)
		throw new InvalidLengthException(
			      "inUserName: ", inUserName.length(), 30);

	this.userName = inUserName;
}
/**
 * Update the user profile.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public void setUserProfile(String inUserProfile) throws InvalidLengthException {

	if (inUserProfile.length() > 10)
		throw new InvalidLengthException(
			      "inUserProfile: ", inUserProfile.length(), 10);

	this.userProfile = inUserProfile;
}
/**
 * Update the type of user.
 *
 * Creation date: (6/12/2003 4:45:28 PM)
 */
public void setUserType(String inUserType) throws InvalidLengthException {

	if (inUserType.length() > 10)
		throw new InvalidLengthException(
			      "inUserType: ", inUserType.length(), 10);

	this.userType = inUserType;
}
/**
 * Change sample request user record on update.
 *.
 * Creation date: (6/11/2003 8:24:29 AM)
 */
public void update() {

	try {
		
		sqlUpdate.setString(1, userType);
		sqlUpdate.setString(2, userInitials);
		sqlUpdate.setString(3, userName);
		sqlUpdate.setString(4, userProfile);
		sqlUpdate.setString(5, notifyShipped);
		sqlUpdate.setString(6, notifyFollowUp);
		sqlUpdate.setString(7, notifyFeedback);
		sqlUpdate.setDate(8, updateDate);
		sqlUpdate.setTime(9, updateTime);
		sqlUpdate.setString(10, updateUser);	
		sqlUpdate.setString(11, userType);
		sqlUpdate.setString(12, userProfile);	
		
		sqlUpdate.executeUpdate();
		
		
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestUsers.update(): " + e);
	}		
}
/**
 * Updae a sample request data record.
 *
 * Creation date: (6/26/2003 8:24:29 AM)
 */
public void updateSampleRequestUser(String userType,
							        String userInitials,
	                                String userName, 
			        	            String userProfile,
									String notifyShipped,
									String notifyFollowUp,
									String notifyFeedback,
						     java.sql.Date updateDate,
							 java.sql.Time updateTime,
									String updateUser)
									 
  	throws InvalidLengthException, Exception
{
	SampleRequestUsers newSampleRequestUser = new SampleRequestUsers();

	newSampleRequestUser.setUserType(userType);
	newSampleRequestUser.setUserInitials(userInitials);
	newSampleRequestUser.setUserName(userName);
	newSampleRequestUser.setUserProfile(userProfile);
	newSampleRequestUser.setNotifyShipped(notifyShipped);
	newSampleRequestUser.setNotifyFollowUp(notifyFollowUp);
	newSampleRequestUser.setNotifyFeedback(notifyFeedback);
	newSampleRequestUser.setUpdateDate(updateDate);
	newSampleRequestUser.setUpdateTime(updateTime);
	newSampleRequestUser.setUpdateUser(updateUser);
	
	newSampleRequestUser.update();


}

/**
 * Drop down list for sample users.
 *
 * Creation date: (6/24/2003 1:01:38 PM)
 */
public static String buildDropDownNameForProfile(Vector inList, 
	                                             String inProfile,
	                                             String inSelect)
				
{
	String dropDown = "";
	String selected = "";
	String selectOption = "";	
	int userCount   = inList.size();
	
	if (inSelect.equals("") || inSelect==null)
	    selectOption = "Select a " + inSelect.trim() + "--->";
	else {
		 if (inSelect.trim().equals("*all"))
		     selectOption = "*all";
	     else
	         selectOption = "Select a " + inSelect.trim() + "--->";
	     }
		
	if (userCount > 0)
		{
   		    for (int x = 0; x < userCount; x++)
   		    {
	   		    SampleRequestUsers nextUser = (SampleRequestUsers) inList.elementAt(x);

	   		    if (inProfile.trim().toUpperCase().equals(nextUser.getUserProfile().trim()))
	   		    selected = "' selected='selected'>";
	   		    else
	   		    selected = "'>";
		   		    
	   		    dropDown = dropDown + "<option value='" + 
	   		    nextUser.getUserProfile().trim() + selected +
	   		    nextUser.getUserName() + "&nbsp;";
	   		}
   		    if (!dropDown.equals(""))
   		    {	
	   		    SampleRequestUsers nextUser = (SampleRequestUsers) inList.elementAt(0);
	   		    GeneralInfo descInfo = new GeneralInfo();    
				Vector descList      = GeneralInfo.findDescShort("SRU", nextUser.getUserType().trim());
				GeneralInfo nextDesc = (GeneralInfo) descList.elementAt(0);
				
 	   		    dropDown = "<select name='" + nextUser.getUserType().trim() + "'>" +
	   		    		   "<option value='None' selected>" +
	   		    		   selectOption + 
	   		    		   dropDown +
	   		    		   "</select>";
   		    }
  	  		
  	   	}

	return dropDown; 
}
/**
 * Set result set for a single user by profile name.
 *  The First User to be come accross..
 *
 * Creation date: (4/18/2006 TWalton)
 */
public static SampleRequestUsers findUserByProfile(String inUserProfile) {

	
	ResultSet rs = null;
	
	try {
		findSampleUserByProfile.setString(1, inUserProfile.trim().toUpperCase());
		
		rs = findSampleUserByProfile.executeQuery();
		
				
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestUsers." +
			               "findUserByProfile(): " + e);
		return null;
	}

	SampleRequestUsers aSampleRequestUser = new SampleRequestUsers();
	
	try {
		if (rs.next())
		{
			aSampleRequestUser.loadFields(rs);
		}
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleReqeustUsers." +
			               "findUserByProfile(): " + e);
		}
	
	return aSampleRequestUser;
}
}
