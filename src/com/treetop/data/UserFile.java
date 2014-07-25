package com.treetop.data;


import java.text.*;
import java.sql.*;
import java.math.*;
import java.util.*;
import javax.sql.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import java.util.Stack;
import com.treetop.utilities.*;

/**
 * Access to RDB file TREENET/DPPNROLE.
 *
 * Code used to  generate the table.
 *
 * CREATE TABLE TREENET/DPPNROLE (
 *  DPNUSERNBR CHAR ( 10) NOT NULL WITH DEFAULT, //user name     
 *  DPNUSERNBR INT  (  9) NOT NULL WITH DEFAULT, //user number
 *  DPNGROUP   CHAR ( 10) NOT NULL WITH DEFAULT, //ignore
 *  DPNLVL     INT  (  9) NOT NULL WITH DEFAULT, //ignore
 *  DPNFOLDER  CHAR (  1) NOT NULL WITH DEFAULT) //allow user to create a folder.
 *
 *
 * Be sure to set the environment (library) for this file . It
 *  should be "TREENET." for the live environment and "WKLIB." 
 *  for the test environment.
 *   METHODS TO BE MAINTAINED PRIOR TO EVERY EXPORT:
 *   :findAllUsers()
 *   :findUsersByPath(int)
 *   :findUsersByRoles(String[])
 *   :init()"
 *   :loadFields(ResultSet,String)
 **/
public class UserFile {


	private String  userName;
	private Integer userNumber;
	private String  userGroup;
	private Integer userLevel;
	private String  userCreateFolder;
	private String  userEmail;

	//live or test environment on the as400
	private String  library; 

	//** from userprofil file **//
	private String  userNameLong;
	//** for find users by path from TREENET.DPPMUSERP **//
	private String  publishPath;  // Y or N

	//**** For use in Main Method,
    // Constructor Methods and 
    // Update, Add, Delete Methods  ****//
	private static PreparedStatement dltSql;
	private static PreparedStatement addSql;
	private static PreparedStatement updSql;
	private static PreparedStatement addDppmuserpSql;
	private static PreparedStatement deleteAllDppmuserpSql;
	private static PreparedStatement findDppmuserpSql;
	private static PreparedStatement updateDppmuserpSql;
	private static PreparedStatement deleteDppmuserpSql;
	private static PreparedStatement deleteAllDppmuserrSql;
	private static PreparedStatement addDppmuserrSql;

	private static Stack fbuNameStack = null;
	private static Stack fbuNumberStack = null;
	

	// Additional fields.
	private boolean persists = false;
	private static Connection connection;
	
/**
 * UserRole constructor comment.
 */
public UserFile() {	
		init();
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public UserFile(Integer userNumberIn) 
	throws InstantiationException { 

	if (connection == null)
		init();


		
	ResultSet rs1 = null;
	
	try {
		PreparedStatement fbuNumber = (PreparedStatement) fbuNumberStack.pop();
		fbuNumber.setInt(1, userNumberIn.intValue());
		ResultSet rs = fbuNumber.executeQuery();
		fbuNumberStack.push(fbuNumber);
		//findByUserNumberSql.setInt(1, userNumberIn.intValue());
		//rs = findByUserNumberSql.executeQuery();
		
		if (rs.next() == false)
			throw new InstantiationException("The user: " + userNumberIn + " not found");
		rs1 = rs;
			
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.UserFile.UserFile(Integer) " + e);
		return;
	}
	
	loadFields(rs1, "allUsers");	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public UserFile(String userNameIn) 
	throws InstantiationException { 

	if (connection == null)
		init();
		
	ResultSet rs2 = null;
	
	try {
		PreparedStatement fbuName = (PreparedStatement) fbuNameStack.pop();
		fbuName.setString(1, userNameIn);
		ResultSet rs = fbuName.executeQuery();
		fbuNameStack.push(fbuName);
		
		//findByUserNameSql.setString(1, userNameIn);
		//rs = findByUserNameSql.executeQuery();
		
		if (rs.next() == false)
			throw new InstantiationException("The user: " + userNameIn + " not found");
		rs2 = rs;
		
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.UserFile.UserFile(String) " + e);
		return;
	}
	
	loadFields(rs2, "allUsers");	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void add() {

//	try {
//		addSql.setString(1, userName);
//		addSql.setInt(2, userNumber.intValue());
//		addSql.setString(3, userGroup);
//		addSql.setInt(4, userLevel.intValue());
//		addSql.setString(5, userCreateFolder);
//		
//		addSql.executeUpdate();
		
//	} catch (SQLException e) {
//		System.out.println("Sql error at com.treetop.data.UserFile.add(): " + e);
//	}	
}
/**
 * Call this method will return a vector which will include all users, based on the roles sent in.
 *   The returned vector will be alphabetical by first name.
 * Creation date: (5/8/2003 11:41:29 AM)
 */
public Vector findUsersByRoles(String[] ChosenRoles) 
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment


	
    //****TESTING****
	//	System.out.println("UserFile.findUsersByRoles");

   Vector users = new Vector();

    String roleList = "= 0 ";
	int x = ChosenRoles.length;
	
	if (x > 0)
	{
		roleList = "IN (" + ChosenRoles[0];
	   for (int z = 1; z < x; z++)
 	   {
		   roleList = roleList + "," + ChosenRoles[z];
 	   }
 	   roleList = roleList + ") ";
	 }		

  	String SQLRun = "SELECT * " +
                    "FROM " + library + "DPJUSERR " +
                    "WHERE DPMROLENBR " + roleList + 
                    "ORDER BY UPTEXT, DPNUSER";

	try
	{		 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun);
		 
		int saveUser = 0;
		try
		{
 		   	while (rs.next())
   		    {
	        
	        	if (saveUser != rs.getInt("DPNUSERNBR"))
	        	{
		        	saveUser = rs.getInt("DPNUSERNBR");
		        
  		      		UserFile buildVector = new UserFile();
	   	    		buildVector.loadFields(rs, "allUsers");
 	  	    		users.addElement(buildVector);
	 	     	}
		    }
 	       rs.close();
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set (UserFile.findUsersByRoles)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL (UserFile.findUsersByRoles) " + e);
	}         
	
	return users;
	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void addPathToUser(int userNumber,
	                             int pathNumber)  
	throws InvalidLengthException, Exception
{
	addDppmuserpSql.setInt(1, userNumber);
	addDppmuserpSql.setInt(2, pathNumber);
	addDppmuserpSql.setString(3, "Y");
	addDppmuserpSql.executeUpdate();

}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void addRoleToUser(String userNumber,
	                             int roleNumber)  
	throws InvalidLengthException, Exception
{
	addDppmuserrSql.setString(1, userNumber);
	addDppmuserrSql.setInt(2, roleNumber);
	addDppmuserrSql.executeUpdate();

}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void addToUserFile(String userName,
	                             int userNumber, 
								 String userGroup,
								 int userLevel,
								 String userCreateFolder) 
	throws InvalidLengthException, Exception
{
	UserFile newUserRecord = new UserFile();
	newUserRecord.setUserName(userName);
	newUserRecord.setUserNumber(userNumber);
	newUserRecord.setUserGroup(userGroup);
	newUserRecord.setUserLevel(userLevel);
	newUserRecord.setUserCreateFolder(userCreateFolder);
	newUserRecord.add();

}
/**
 * Insert the method's description here.
 * Creation date: (5/09/2003 8:24:29 AM)
 */
public static void addUpdatePathToUser(String userNumber,
	                                   int pathNumber,
	                                   String pathSecurity)  
	throws InvalidLengthException, Exception
{
	ResultSet rs = null;

	//Using the incoming parameters get the matching record from file DPPMUSERP. 
	try
	{
		findDppmuserpSql.setString(1, userNumber);
		findDppmuserpSql.setInt(2, pathNumber);
		rs = findDppmuserpSql.executeQuery();

		// if it exists verify the publish value
		if (rs.next())
		{
			String securityValue = rs.getString("DPMPUBLISH");
			if (!securityValue.equals(pathSecurity))
			{
				updateDppmuserpSql.setString(1, pathSecurity);
				updateDppmuserpSql.setString(2, userNumber);
				updateDppmuserpSql.setInt(3, pathNumber);
				updateDppmuserpSql.executeUpdate();
			}
		// if it does not exist add the record.
		} else
		{
			addDppmuserpSql.setString(1, userNumber);
			addDppmuserpSql.setInt(2, pathNumber);
			addDppmuserpSql.setString(3, pathSecurity);
			addDppmuserpSql.executeUpdate();
			
		}
	}
	catch (SQLException e)
	{
		System.out.println("Sql error at com.treetop.data.addPathToUser(): " + e);
	}
	rs.close();	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 * ReWrite 10/05/05 TWalton
 */
protected static UserFile addUserFile(UserFile inClass)
									 
	throws InvalidLengthException, Exception
{
	UserFile newUserRecord = new UserFile();
	newUserRecord.setUserName(inClass.getUserName());
	newUserRecord.setUserNumber(inClass.getUserNumber());
	newUserRecord.setUserGroup(inClass.getUserGroup());
	newUserRecord.setUserLevel(inClass.getUserLevel());
	newUserRecord.setUserCreateFolder(inClass.getUserCreateFolder());
	newUserRecord.setUserEmail(inClass.getUserEmail());

	newUserRecord.add();
	return newUserRecord;
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void delete() {

//	try {
//		dltSql.setString(1, userName);
//		dltSql.executeUpdate();
//	} catch (SQLException e) {
//		System.out.println("SQL Exception at com.treetop.data.UserFile.delete(): " + e);
//	}	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void deletePathAllUsers(int pathNumber)
	throws InvalidLengthException, Exception
{
	deleteAllDppmuserpSql.setInt(1, pathNumber);
	deleteAllDppmuserpSql.executeUpdate();

}
/**
 * Insert the method's description here.
 * Creation date: (5/09/2003 8:24:29 AM)
 */
public static void deleteUserAndPath(String userNumberIn, 
	                                 int pathNumberIn)
	
{
	try {
		deleteDppmuserpSql.setString(1, userNumberIn);
		deleteDppmuserpSql.setInt(2, pathNumberIn);
		deleteDppmuserpSql.executeUpdate();
	} catch (Exception e) {
		System.out.println("error: com.treetop.data.UserFile.deleteUserAndPath()" +
							e);
	}

}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void deleteUserRoles(String userNumber)
	throws InvalidLengthException, Exception
{
	deleteAllDppmuserrSql.setString(1, userNumber);
	deleteAllDppmuserrSql.executeUpdate();

}
/**
 * Call this method will return a vector which will include all users.
 *   The returned vector will be alphabetical by first name.
 * Creation date: (5/1/2003 11:41:29 AM)
 */
public Vector findAllUsers() 
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment


	
    //****TESTING****
	//	System.out.println("UserFile.findAllUsers");

   Vector allUsers = new Vector();	
	
	String SQLRun = "SELECT * " +
                    "FROM " + library + "DPPNUSER " +
                    "ORDER BY DPNUSRNAME, DPNUSER";

	try
	{		 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun);
		 
		int saveUser = 0;
		try
		{
 		   	while (rs.next())
   		    {
	        
	        	if (saveUser != rs.getInt("DPNUSERNBR"))
	        	{
		        	saveUser = rs.getInt("DPNUSERNBR");
		        
  		      		UserFile buildVector = new UserFile();
	   	    		buildVector.loadFields(rs, "allUsers");
 	  	    		allUsers.addElement(buildVector);
	 	     	}
		    }
 	       rs.close();
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set (UserFile.findAllUsers)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL (UserFile.findAllUsers) " + e);
	}         
	
	return allUsers;
	
}
/**
 * Call this method will return a vector which will include all users, based on the path sent in.
 *   The returned vector will be alphabetical by first name.
 * Creation date: (6/27/2003 11:41:29 AM)
 */
public Vector findCanIPublishToItUsersByPath(int path) 
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment
	
    //****TESTING****
	//	System.out.println("UserFile.findCanISeeItUsersByPath");

   Vector users = new Vector();

  	String SQLRun = "SELECT * " +
                    "FROM " + library + "DPJUSERP " +
//                    "INNER JOIN QGPL.USERPROFIL ON UPUPRF = DPNUSER " +
                    "WHERE DPMPATHNBR = " + path + " " +
                    "AND DPMPUBLISH = 'Y' " +
                    "ORDER BY DPNUSRNAME, DPNUSER";

	try
	{		 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun);
		 
		int saveUser = 0;
		try
		{
 		   	while (rs.next())
   		    {
	        
	        	if (saveUser != rs.getInt("DPNUSERNBR"))
	        	{
		        	saveUser = rs.getInt("DPNUSERNBR");
		        
  		      		UserFile buildVector = new UserFile();
	   	    		buildVector.loadFields(rs, "both");
 	  	    		users.addElement(buildVector);
	 	     	}
		    }
 	       rs.close();
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set (UserFile.findUsersByPath)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL (UserFile.findUsersByPath) " + e);
	}         
	
	return users;
	
}
/**
 * Call this method will return a vector which will include all users, based on the path sent in.
 *   The returned vector will be alphabetical by first name.
 * Creation date: (6/27/2003 11:41:29 AM)
 */
public Vector findCanISeeItUsersByPath(int path) 
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment
	
    //****TESTING****
	//	System.out.println("UserFile.findCanISeeItUsersByPath");

   Vector users = new Vector();

  	String SQLRun = "SELECT * " +
                    "FROM " + library + "DPJUSERP " +
//                    "INNER JOIN QGPL.USERPROFIL ON UPUPRF = DPNUSER " +
                    "WHERE DPMPATHNBR = " + path + " " +
                    "ORDER BY DPNUSRNAME, DPNUSER";

	try
	{		 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun);
		 
		int saveUser = 0;
		try
		{
 		   	while (rs.next())
   		    {
	        
	        	if (saveUser != rs.getInt("DPNUSERNBR"))
	        	{
		        	saveUser = rs.getInt("DPNUSERNBR");
		        
  		      		UserFile buildVector = new UserFile();
	   	    		buildVector.loadFields(rs, "both");
 	  	    		users.addElement(buildVector);
	 	     	}
		    }
 	       rs.close();
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set (UserFile.findUsersByPath)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL (UserFile.findUsersByPath) " + e);
	}         
	
	return users;
	
}
/**
 * Call this method will return a vector which will include all users, based on the path sent in.
 *   The returned vector will be alphabetical by first name.
 * Creation date: (5/12/2003 11:41:29 AM)
 */
public Vector findUsersByPath(int path) 
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment


	
    //****TESTING****
	//	System.out.println("UserFile.findUsersByPath");

   Vector users = new Vector();

  	String SQLRun = "SELECT * " +
                    "FROM " + library + "DPJUSERP " +
                    "WHERE DPMPATHNBR = " + path + " " +
                    "ORDER BY DPNUSER";

	try
	{		 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun);
		 
		int saveUser = 0;
		try
		{
 		   	while (rs.next())
   		    {
	        
	        	if (saveUser != rs.getInt("DPNUSERNBR"))
	        	{
		        	saveUser = rs.getInt("DPNUSERNBR");
		        
  		      		UserFile buildVector = new UserFile();
	   	    		buildVector.loadFields(rs, "byPath");
 	  	    		users.addElement(buildVector);
	 	     	}
		    }
 	       rs.close();
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set (UserFile.findUsersByPath)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL (UserFile.findUsersByPath) " + e);
	}         
	
	return users;
	
}
/**
 * Call this method will return a vector which will include all users, based on the group sent in.
 *   The returned vector will be alphabetical by first name.
 * Creation date: (12/5/2005 4:20:29 PM  TWalton)
 */
public Vector findUsersByGroup(String chosenGroup) 
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment
	
    //****TESTING****
	//	System.out.println("UserFile.findUsersByGroups");

   Vector users = new Vector();


  	String SQLRun = "SELECT * " +
                    "FROM " + library + "DPPNUSER " +
//                    "LEFT OUTER JOIN QGPL.USERPROFIL " + 
//                    "  ON DPNUSER = UPUPRF " +
                    "INNER JOIN " + library + "DPPMUSERG " +
                    "  ON DPNUSERNBR = DPMUSERNBR " +
                    "WHERE DPMGRPNBR = " + chosenGroup + 
                    " ORDER BY DPNUSRNAME, DPNUSER";

	try
	{		 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun);
		 
		try
		{
 		   	while (rs.next())
   		    {
  		      		UserFile buildVector = new UserFile();
	   	    		buildVector.loadFields(rs, "allUsers");
 	  	    		users.addElement(buildVector);
		    }
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set (UserFile.findUsersByGroup)" + e);
		}
		try
		{
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL (UserFile.findUsersByGroup) " + e);
	}         
	
	return users;
	
}
/**
 * Insert the method's description here.
 * Creation date: (5/12/2003 4:45:28 PM)
 */
public String getPublishPath() {

	return publishPath;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getUserCreateFolder() {

	return userCreateFolder;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getUserGroup() {

	return userGroup;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public int getUserLevel() {

	return userLevel.intValue();	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getUserName() {

	return userName;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getUserNameLong() {

	return userNameLong;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public int getUserNumber() {

	return userNumber.intValue();	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 * Change to new AS400 01/06/2009 wth.
 */
public void init() {

	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment

	
	// Test for initial connection.

	//System.out.println("UserFile persists = " + this.persists);
	
	if (connection == null) {
	    //connection = SQLConnect.connection;
		connection = ConnectionStack.getConnection();
	    this.persists = true;

		Connection conn1 = null;
		Connection conn2 = null;
		Connection conn3 = null;
		Connection conn4 = null;
		Connection conn5 = null;
		Connection conn6 = null;	    
	    
	try {
//		dltSql = connection.prepareStatement(
//			"DELETE FROM " + library + "DPPNUSER " +
//			" WHERE DPNUSER = ?");

//		addSql = connection.prepareStatement(
//			"INSERT INTO " + library + "DPPNUSER " +
//			" VALUES (?, ?, ?, ?, ?, ?, ?)");
			         
//		updSql = connection.prepareStatement(
//			"UPDATE " + library + "DPPNUSER " +
//			" SET DPNUSER = ?, DPNUSERNBR = ?, DPNGROUP = ?, DPNLVL = ?," +
//			" DPNFOLDER = ?, DPNEMAIL = ? " + 
//			" WHERE DPNUSER = ?");
		

		conn1 = ConnectionStack.getConnection();
		conn2 = ConnectionStack.getConnection();
		conn3 = ConnectionStack.getConnection();
		conn4 = ConnectionStack.getConnection();
		conn5 = ConnectionStack.getConnection();
		conn6 = ConnectionStack.getConnection();
		
		//Find By User Name.
		fbuNameStack = new Stack();
		
		PreparedStatement fbuName1 = conn1.prepareStatement(
			"SELECT * FROM " + library + "DPPNUSER " +
			" WHERE DPNUSER = ?");
		fbuNameStack.push(fbuName1);

		PreparedStatement fbuName2 = conn2.prepareStatement(
			"SELECT * FROM " + library + "DPPNUSER " +
			" WHERE DPNUSER = ?");
		fbuNameStack.push(fbuName2);

		PreparedStatement fbuName3 = conn3.prepareStatement(
			"SELECT * FROM " + library + "DPPNUSER " +
			" WHERE DPNUSER = ?");
		fbuNameStack.push(fbuName3);
			
		

		// Find by User Number.
		fbuNumberStack = new Stack();
		
		PreparedStatement fbuNumber1 = conn4.prepareStatement(
			"SELECT * FROM " + library + "DPPNUSER " +
            //"INNER JOIN QGPL.USERPROFIL ON UPUPRF = DPNUSER " + 01/06/09 wth
            " WHERE DPNUSERNBR = ?");
		fbuNumberStack.push(fbuNumber1);

		PreparedStatement fbuNumber2 = conn5.prepareStatement(
			"SELECT * FROM " + library + "DPPNUSER " +
           	//"INNER JOIN QGPL.USERPROFIL ON UPUPRF = DPNUSER " + 01/06/09 wth
           	" WHERE DPNUSERNBR = ?");
		fbuNumberStack.push(fbuNumber2);

		PreparedStatement fbuNumber3 = conn6.prepareStatement(
			"SELECT * FROM " + library + "DPPNUSER " +
            //"INNER JOIN QGPL.USERPROFIL ON UPUPRF = DPNUSER " + 01/06/09
            " WHERE DPNUSERNBR = ?");
		fbuNumberStack.push(fbuNumber3);
				
		
		

		addDppmuserpSql = connection.prepareStatement(
			"INSERT INTO " + library + "DPPMUSERP " +
			" VALUES (?, ?, ?)");

		deleteAllDppmuserpSql = connection.prepareStatement(
			"DELETE FROM " + library + "DPPMUSERP " +
			" WHERE DPMPATHNBR = ?");

		findDppmuserpSql = connection.prepareStatement(
			"SELECT * FROM " + library + "DPPMUSERP " +
			" WHERE DPMUSERNBR = ? AND DPMPATHNBR = ?");

		updateDppmuserpSql = connection.prepareStatement(
			"UPDATE " + library + "DPPMUSERP " +
			" SET DPMPUBLISH = ? " +
			" WHERE DPMUSERNBR = ? AND DPMPATHNBR = ?");

		deleteDppmuserpSql = connection.prepareStatement(
			"DELETE FROM " + library + "DPPMUSERP " +
			" WHERE DPMUSERNBR = ? AND DPMPATHNBR = ?");

		deleteAllDppmuserrSql = connection.prepareStatement(
			"DELETE FROM " + library + "DPPMUSERR " +
			" WHERE DPMUSERNBR = ?");

		addDppmuserrSql = connection.prepareStatement(
			"INSERT INTO " + library + "DPPMUSERR " +
			" VALUES (?, ?)");
		
		
	} catch (SQLException e) {
		System.out.println("SQL exception occured at com.treetop.data.UserFile.init()" +
			               e);
	}
	finally
	{
		ConnectionStack.returnConnection(conn1);
		ConnectionStack.returnConnection(conn2);
		ConnectionStack.returnConnection(conn3);
		ConnectionStack.returnConnection(conn4);
		ConnectionStack.returnConnection(conn5);
		ConnectionStack.returnConnection(conn6);
	}
	
	}

	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
protected void loadFields(ResultSet rs1, String requestType) {

	try {
		// Define database environment prior to every export (live or test).
		//library = "WKLIB."; // test environment
		library = "TREENET."; // live environment
		ResultSet rs = rs1;

		userName          = rs.getString("DPNUSER");
		userNumber        = new Integer(rs.getInt("DPNUSERNBR"));
		userGroup         = rs.getString("DPNGROUP");
		userLevel         = new Integer(rs.getInt("DPNLVL"));
		userCreateFolder  = rs.getString("DPNFOLDER");
		userEmail         = rs.getString("DPNEMAIL");
		userNameLong      = rs.getString("DPNUSRNAME");
		if (userNameLong.trim().equals(""))
			userNameLong  = rs.getString("DPNUSER");
		//if (requestType.equals("allUsers") || requestType.equals("both"))
		//{
			//try
			//{
			//	userNameLong  = rs.getString("UPTEXT");
			//}
			//catch(Exception e)
			//{}				
		//	if (userNameLong.trim().equals(""))
		//		userNameLong  = rs.getString("DPNUSER");
		//}			
			
		if (requestType.equals("byPath") || requestType.equals("both"))
		{
			publishPath  = rs.getString("DPMPUBLISH");
		}			
		else
			publishPath  = "N";
		
	
	}
	catch (Exception e)
	{

	//	System.out.println("SQL Exception at com.treetop.data.UserFile.loadFields();" + e);
	}

	persists = true;
				
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void main(String[] args) {

	// add a few users.
	
	try {
		
//		UserFile one = addUserFile("TEST01", UserFile.nextUserNumber(), 
//			                       "TestGP1", 999991, "N"); 					 
//		System.out.println("one: " + one);
		
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.UserFile.main()");
	}
	
	try {
		
//		UserFile two = addUserFile("TEST02", UserFile.nextUserNumber(), 
//			                       "TestGP2", 999992, "N"); 					 
//		System.out.println("two: " + two);
		
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.UserFile.main()");
	}
	
	try {
		
//		UserFile three = addUserFile("TEST03", UserFile.nextUserNumber(), 
//			                         "TestGP3", 999993, "N"); 					 
//		System.out.println("three: " + three);
		
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.UserFile.main()");
	}
	
	// find by role number.
	String user2 = "TEST02";
	try {
		UserFile two = new UserFile(user2);
		System.out.println("find two: " + two);

		// update a role.
		try{
			two.setUserGroup("TestGP2-CH");
		} catch (Exception e) {
			System.out.println("com.treetop.Data.UserFile.Main()" +
				               ". Problem with change test: " + e);
		}

		two.update();

		
		//delete
		two.delete();
	} catch (Exception e) {
		System.out.println("com.treetop.data.UserFile.Main(). " +
			               "Error with find/delete in main" + e);
	}


	// delete one and three.
	try {
		String user1 = "TEST01";
		UserFile one = new UserFile(user1);
		one.delete();
	} catch (Exception e) {
		System.out.println("com.treetop,data.UserFile.Main(). " + 
			               "delete problem with one: " + e);
	}

	try {
		String user3 = "TEST03";
		UserFile three = new UserFile(user3);
		three.delete();
	} catch (Exception e) {
		System.out.println("com.treetop,data.UserFile.Main(). " + 
			               "delete problem with three: " + e);
	}
		

	// find a user that dosent exist.
	String userX = "XXXXXX";
	try {
		UserFile notThereUserFile = new UserFile(userX);
		System.out.println("notThereUserFile: " + notThereUserFile);
	} catch (InstantiationException ie) {
		System.out.println("file not there: " + ie);
	}

}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void newMethod() {}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static int nextUserNumber() 
{
//	10/28/11 TWalton -- Not used, 10.6.100.1 points to a machine that has not been valid for 3+ years
//	try {
		// create a AS400 object
//		AS400 as400 = new AS400("10.6.100.1", "DAUSER", "web230502");
//		ProgramCall pgm = new ProgramCall(as400);

//		ProgramParameter[] parmList = new ProgramParameter[1];
//		parmList[0] = new ProgramParameter(100);
//		pgm = new ProgramCall(as400, "/QSYS.LIB/GNLIB.LIB/CLUSERNBR.PGM", parmList);

//		if (pgm.run() != true)
//		{
//			return 0;
//		} else {
//			AS400PackedDecimal pd = new AS400PackedDecimal(9, 0);
//			byte[] data = parmList[0].getOutputData();
//			double dd = pd.toDouble(data, 0);
//			int i = (int) dd;
//			as400.disconnectService(AS400.COMMAND);
//			return i;
//		}

//	} catch (Exception e) {
		return 0;
//	}
				
}
/**
 * Insert the method's description here.
 * Creation date: (5/12/2003 4:45:28 PM)
 */
public void setPublishPath(String publishPathIn) throws InvalidLengthException {

	if (publishPathIn.length() > 1)
		throw new InvalidLengthException(
				"publishPathIn", publishPathIn.length(), 1);

	this.publishPath =  publishPathIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setUserCreateFolder(String userCreateFolderIn) 
		throws InvalidLengthException {

	if (userCreateFolderIn.length() > 1)
		throw new InvalidLengthException(
				"userCreateFolderIn", userCreateFolderIn.length(), 1);

	this.userCreateFolder =  userCreateFolderIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setUserGroup(String userGroupIn) throws InvalidLengthException {

	if (userGroupIn.length() > 10)
		throw new InvalidLengthException(
				"userGroupIn", userGroupIn.length(), 10);

	this.userGroup =  userGroupIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setUserLevel(int userLevelIn) {

	this.userLevel =  new Integer(userLevelIn);
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setUserName(String userNameIn) throws InvalidLengthException {

	if (userNameIn.length() > 10)
		throw new InvalidLengthException(
				"userNameIn", userNameIn.length(), 10);

	this.userName =  userNameIn;
}
/**
 * Insert the method's description here.
 * Creation date: (5/1/2003 4:45:28 PM)
 */
public void setUserNameLong(String userNameLongIn) throws InvalidLengthException {

	this.userNameLong =  userNameLongIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setUserNumber(int userNumberIn) {

	this.userNumber =  new Integer(userNumberIn);
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public String toString() {

	return new String(
		"userName: " + userName + "\n" +
		"userNumber: " + userNumber + "\n" +
		"userGroup: " + userGroup + "\n" +
	    "userLvl: " + userLevel + "\n" +
	    "userCreateFolder: " + userCreateFolder + "\n" +
	    "library: " + library + "\n");
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void update() {

//	try {
//		updSql.setString(1, userName);
//		updSql.setInt(2, userNumber.intValue());
//		updSql.setString(3, userGroup);
//		updSql.setInt(4, userLevel.intValue());
//		updSql.setString(5, userCreateFolder);
//		// Added Email 10/10/05 TWalton
//		updSql.setString(6, userEmail);
//		updSql.setString(7, userName);
//		updSql.executeUpdate();
		
//	} catch (SQLException e) {
//		System.out.println("Sql error at com.treetop.data.UserFile.update(): " + e);
//	}		
}
	/**
	 * @return
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param string
	 */
	public void setUserEmail(String string) {
		userEmail = string;
	}

	/**
	 * @param integer
	 */
	public void setUserLevel(Integer integer) {
		userLevel = integer;
	}

	/**
	 * @param integer
	 */
	public void setUserNumber(Integer integer) {
		userNumber = integer;
	}

}
