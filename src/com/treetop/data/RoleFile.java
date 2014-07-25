package com.treetop.data;

import java.text.*;
import java.sql.*;
import java.math.*;
import java.util.*;
import javax.sql.*;
import com.ibm.as400.access.*;
import com.treetop.*;


/**
 * Acces to RDB file TREENET/DPPNROLE.
 *
 * Code used to generate the table.
 *
 * CREATE TABLE TREENET/DPPNROLE (
 *  DPNROLENBR INT        NOT NULL WITH DEFAULT, //role number     
 *  DPNROLEDSC CHAR ( 30) NOT NULL WITH DEFAULT, //role description
 *
 *
 * Be sure to set the environment (library) for this file . It
 *  should be "TREENET." for the live environment and "WKLIB." 
 *  for the test environment.
 *   METHODS TO BE MAINTAINED PRIOR TO EVERY EXPORT:
 *   :findAllRoles()
 *   :findRolesbyUrlNumber(String)
 *   :findRolesbyUserNumber(String)
 *   :init()"
 *   :loadFields(ResultSet,String)
 **/
public class RoleFile {

	private Integer roleNumber;
	private String  roleDescription;
	private String  roleDefinition;

	//live or test environment on the as400
	private String  library; 

	//**** For use in Main Method,
    // Constructor Methods and 
    // Update, Add, Delete Methods  ****//
	private static PreparedStatement dltSql;
	private static PreparedStatement addSql;
	private static PreparedStatement updSql;
	private static PreparedStatement findByRoleNumberSql;

	// Additional fields.
	private boolean persists = false;
	private static Connection connection;
/**
 * UserRole constructor comment.
 */
public RoleFile() {
	
	if (connection == null)
		init();
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public RoleFile(Integer roleNumberIn) 
	throws InstantiationException { 

	if (connection == null)
		init();
		
	ResultSet rs = null;
	
	try {
		findByRoleNumberSql.setInt(1, roleNumberIn.intValue());
		rs = findByRoleNumberSql.executeQuery();
		
		if (rs.next() == false)
			throw new InstantiationException("The role: " + roleNumberIn + " not found");
			
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.RoleFile.RoleFile(Integer) " + e);
		return;
	}
	
	loadFields(rs, "");	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void add() {

	try {
		addSql.setInt(1, roleNumber.intValue());
		addSql.setString(2, roleDescription);
		addSql.setString(3, roleDefinition);
		
		addSql.executeUpdate();
		
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.RoleFile.add(): " + e);
	}	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
protected static RoleFile addRoleFile(int roleNumber, 
									  String roleDescription,
									  String roleDefinition)
									 
	throws InvalidLengthException, Exception
{
	RoleFile newRoleRecord = new RoleFile();
	newRoleRecord.setRoleNumber(roleNumber);
	newRoleRecord.setRoleDescription(roleDescription);
	newRoleRecord.setRoleDefinition(roleDefinition);
	newRoleRecord.add();
	return newRoleRecord;
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void addToRoleFile(int roleNumber, 
								 String roleDescription,
								 String roleDefinition) 
	throws InvalidLengthException, Exception
{
	RoleFile newRoleRecord = new RoleFile();
	newRoleRecord.setRoleNumber(roleNumber);
	newRoleRecord.setRoleDescription(roleDescription);
	newRoleRecord.setRoleDefinition(roleDefinition);
	newRoleRecord.add();

}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void delete() {

	try {
		dltSql.setInt(1, roleNumber.intValue());
		dltSql.executeUpdate();
	} catch (SQLException e) {
		System.out.println("SQL Exception at com.treetop.data.RoleFile.delete(): " + e);
	}	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public boolean deleteByRoleNumber(int roleNumberIn) {

	try {
		dltSql.setInt(1, roleNumberIn);
		dltSql.executeUpdate();		
		return true;
		
	} catch (Exception e) {
		System.out.println("SQL Exception at com.treetop.data.RoleFile.delete(): " + e);
		return false;
	}	
}
/**
 * Call this method will return a vector which will include all roles.
 *   The returned vector will be alphabetical by name.
 * Creation date: (5/1/2003 11:41:29 AM)
 */
public Vector findAllRoles() 
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment


	
    //****TESTING****
	//	System.out.println("RoleFile.findAllRoles");

   Vector allRoles = new Vector();	
	
	String SQLRun = "SELECT * " +
                    "FROM " + library + "DPPNROLE " +
                    "ORDER BY DPNROLEDSC";

	try
	{		 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun);
		 
		try
		{
 		   	while (rs.next())
   		    {
	      		RoleFile buildVector = new RoleFile();
  	    		buildVector.loadFields(rs, "");
  	    		allRoles.addElement(buildVector);
		    }
 	       rs.close();
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set (RoleFile.findAllRoles)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL (RoleFile.findAllRoles) " + e);
	}         
	
	return allRoles;
	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static Vector findByRoleNumber(Integer roleNumberIn) {

	ResultSet rs = null;
	
	try {
		findByRoleNumberSql.setInt(1, roleNumberIn.intValue());
		rs = findByRoleNumberSql.executeQuery();
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.RoleFile." +
			               "findByRoleNumber(): " + e);
		return null;
	}
	
	Vector someRoles = new Vector();
	
	try {
		while (rs.next())
		{
			RoleFile aRoleFile = new RoleFile();
			aRoleFile.loadFields(rs, "");
			someRoles.addElement(aRoleFile);
		}
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.RoleFile.findByRoleNumber(): " + e);
		return null;
	}

	return someRoles;		
			
}
/**
 * Call this method will return an array of Role numbers.
 * Creation date: (5/1/2003 11:41:29 AM)
 */
public Vector findRolesbyUrlNumber(String urlNumber) 
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment


	
    //****TESTING****
	//	System.out.println("RoleFile.findRolesbyUrlNumber");

	Vector Roles = new Vector();
	
   int x = 0;
	
	String SQLRun = "SELECT * " +
                    "FROM " + library + "DPPMURLUSE " +
                    "WHERE DPMURLNBR = " + urlNumber + " AND DPMRECTYPE = 'R' " +
                    "ORDER BY DPMSECNBR";

	try
	{		 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun);
		 
		try
		{
 		   	while (rs.next())
   		    {
				RoleFile buildVector = new RoleFile();
  	    		buildVector.loadFields(rs, "numberOnly");
  	    		Roles.addElement(buildVector);
		    }
 	       rs.close();
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set (RoleFile.findRolesbyUrlNumber)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL (RoleFile.findRolesbyUrlNumber) " + e);
	}         
	
	return Roles;
	
}
/**
 * Call this method will return an array of Role numbers.
 * Creation date: (5/1/2003 11:41:29 AM)
 */
public Vector findRolesbyUserNumber(String userNumber) 
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment


	
    //****TESTING****
	//	System.out.println("RoleFile.findRolesbyUserNumber");

	Vector Roles = new Vector();
	
   int x = 0;
	
	String SQLRun = "SELECT * " +
                    "FROM " + library + "DPPMUSERR " +
                    "WHERE DPMUSERNBR = " + userNumber + " " +
                    "ORDER BY DPMROLENBR";
   	try
	{		 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun);
		 
		try
		{
 		   	while (rs.next())
   		    {
				RoleFile buildVector = new RoleFile();
  	    		buildVector.loadFields(rs, "roleNumberOnly");
  	    		Roles.addElement(buildVector);
		    }
 	       rs.close();
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set (RoleFile.findRolesbyUserNumber)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL (RoleFile.findRolesbyUserNumber) " + e);
	}         
	
	return Roles;
	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getRoleDescription() {

	return roleDescription;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public int getRoleNumber() {

	return roleNumber.intValue();	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void init() {
	
	// Test for initial connection.

//	System.out.println("persists = " + persists);
	
	if (this.persists == false) {
	    //connection = SQLConnect.connection;
	    this.persists = true;
	}


	// Define database environment prior to every export (live or test).
	//library = "WKLIB."; // test environment
	library = "TREENET."; // live environment

	//try {
		//dltSql = connection.prepareStatement(
			//"DELETE FROM " + library + "DPPNROLE " +
			//" WHERE DPNROLENBR = ?");

		//addSql = connection.prepareStatement(
			//"INSERT INTO " + library + "DPPNROLE " +
			//" VALUES (?, ?, ? )");
			         

		//updSql = connection.prepareStatement(
			//"UPDATE " + library + "DPPNROLE " +
			//" SET DPNROLENBR  = ?, DPNROLEDSC = ?, DPNROLEDEF = ? " + 
			//" WHERE DPNROLENBR = ?");

		//findByRoleNumberSql = connection.prepareStatement(
			//"SELECT * FROM " + library + "DPPNROLE " +
			//" WHERE DPNROLENBR = ?");

	//} catch (SQLException e) {
		//System.out.println("SQL exception occured at com.treetop.data.RoleFile.init()" +
			               //e);
	//}
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
protected void loadFields(ResultSet rs, String numberOnly) {

	try {

		// Define database environment prior to every export (live or test).
		//library = "WKLIB."; // test environment
		library = "TREENET."; // live environment

		
		if (numberOnly.trim().equals("numberOnly"))
		{
			roleNumber        = new Integer(rs.getInt("DPMSECNBR"));
		}
		else
		{
			if (numberOnly.trim().equals("roleNumberOnly"))
			roleNumber        = new Integer(rs.getInt("DPMROLENBR"));
			else
			{
			roleNumber        = new Integer(rs.getInt("DPNROLENBR"));
			roleDescription   = rs.getString("DPNROLEDSC");
			roleDefinition    = rs.getString("DPNROLEDEF");
			}
		}
		
	}
	catch (Exception e)
	{

		System.out.println("SQL Exception at com.treetop.data.RoleFile.loadFields();" + e);
	}

	persists = true;
				
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void main(String[] args) {

	// add a few roles.
	
	try {
		
		RoleFile one = addRoleFile(999991, "Test Add Role One", "Def1"); 					 
		System.out.println("one: " + one);
		
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.RoleFile.main()");
	}
	
	try {
		RoleFile two = addRoleFile(999992, "Test Add Role Two", "Def2"); 					 
		System.out.println("two: " + two);
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.RoleFile.main()");
	}
	
	try {
		RoleFile three = addRoleFile(999993, "Test Add Role Three", "Def3"); 					 
		System.out.println("three: " + three);
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.RoleFile.main()");
	}
	
	// find by role number.
	Integer roleNbr = new Integer(999992);
	try {
		RoleFile two = new RoleFile(roleNbr);
		System.out.println("find two: " + two);

		// update a role.
		try{
			two.setRoleDescription("changed Test Add Role Two");
		} catch (Exception e) {
			System.out.println("com.treetop.Data.RoleFile.Main()" +
				               ". Problem with change test: " + e);
		}

		two.update();

		
		//delete
		two.delete();
	} catch (Exception e) {
		System.out.println("com.treetop.data.RoleFile.Main(). " +
			               "Error with find/delete in main" + e);
	}


	// delete one and three.
	try {
		Integer roleNbr1 = new Integer(999991);
		RoleFile one = new RoleFile(roleNbr1);
		one.delete();
	} catch (Exception e) {
		System.out.println("com.treetop,data.RoleFile.Main(). " + 
			               "delete problem with one: " + e);
	}

	try {
		Integer roleNbr3 = new Integer(999993);
		RoleFile three = new RoleFile(roleNbr3);
		three.delete();
	} catch (Exception e) {
		System.out.println("com.treetop,data.RoleFile.Main(). " + 
			               "delete problem with three: " + e);
	}
		

	// find a role that dosent exist.
	Integer roleNbr2 = new Integer(999990);
	try {
		RoleFile notThereRoleFile = new RoleFile(roleNbr2);
		System.out.println("notThereRoleFile: " + notThereRoleFile);
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
public static int nextRoleNumber() 
{
//	10/28/11 TWalton -- Not used, 10.6.100.1 points to a machine that has not been valid for 3+ years
//	try {
//		// create a AS400 object
//		AS400 as400 = new AS400("10.6.100.1", "DAUSER", "web230502");
//		ProgramCall pgm = new ProgramCall(as400);

//		ProgramParameter[] parmList = new ProgramParameter[1];
//		parmList[0] = new ProgramParameter(100);
//		pgm = new ProgramCall(as400, "/QSYS.LIB/GNLIB.LIB/CLROLENBR.PGM", parmList);

//		if (pgm.run() != true)
//		{
	//		return 0;
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
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setRoleDefinition(String roleDefinitionIn) throws InvalidLengthException {

	if (roleDefinitionIn.length() > 100)
		throw new InvalidLengthException(
				"roleDefinitionIn", roleDefinitionIn.length(), 100);

	this.roleDefinition =  roleDefinitionIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setRoleDescription(String roleDescriptionIn) throws InvalidLengthException {

	if (roleDescriptionIn.length() > 30)
		throw new InvalidLengthException(
				"roleDescriptionIn", roleDescriptionIn.length(), 30);

	this.roleDescription =  roleDescriptionIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setRoleNumber(int roleNumberIn) {

	this.roleNumber =  new Integer(roleNumberIn);
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public String toString() {

	return new String(
		"roleNumber: " + roleNumber + "\n" +
		"roleDescription: " + roleDescription + "\n" +
	    "roleDefinition: " + roleDefinition + "\n" +
	    "library: " + library + "\n");
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void update() {

	try {
		updSql.setInt(1, roleNumber.intValue());
		updSql.setString(2, roleDescription);
		updSql.setString(3, roleDefinition);
		updSql.setInt(4, roleNumber.intValue());
		updSql.executeUpdate();
		
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.RoleFile.update(): " + e);
	}		
}
}
