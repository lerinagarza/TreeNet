package com.treetop.data;

import java.text.*;
import java.sql.*;
import java.math.*;
import java.util.*;
import javax.sql.*;
import com.ibm.as400.access.*;
import com.treetop.*;


/**
 * Access to RDB file TREENET/DPPNPATH.
 *
 * Code used to generate the table.
 *
 * CREATE TABLE TREENET/DPPNROLE (
 *  DPNPATH    CHAR (340) NOT NULL WITH DEFAULT, //path value
 *  DPNPATHNBR INT        NOT NULL WITH DEFAULT, //path number 
 *
 * Be sure to set the environment (library) for this file . It
 *  should be "TREENET." for the live environment and "WKLIB." 
 *  for the test environment.
 *   METHODS TO BE MAINTAINED PRIOR TO EVERY EXPORT:
 *   :findAllPaths()
 *   :findPathsbyUserNumber(String)
 *   :init()
 *   :loadFields(ResultSet,String)
 **/
public class PathFile {

	private String  pathValue;
	private Integer pathNumber;

	private String  pubPath; //(Y or N) based on User

	//live or test environment on the as400
	private String  library; 
	
	//**** For use in Main Method,
    // Constructor Methods and 
    // Update, Add, Delete Methods  ****//
	private static PreparedStatement dltSql;
	private static PreparedStatement addSql;
	private static PreparedStatement updSql;
	private static PreparedStatement findByPathNumberSql;
	private static PreparedStatement findByPathNameSql;

	private static PreparedStatement addDPPMUSERPSql;

	// Additional fields.
	private boolean persists = false;
	private static Connection connection;
/**
 * UserRole constructor comment.
 */
public PathFile() {
	
	if (connection == null)
		init();
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public PathFile(Integer pathNumberIn) 
	throws InstantiationException { 

	if (connection == null)
		init();
		
	ResultSet rs = null;
	
	try {
		findByPathNumberSql.setInt(1, pathNumberIn.intValue());
		rs = findByPathNumberSql.executeQuery();
		
		if (rs.next() == false)
			throw new InstantiationException("The path: " + pathNumberIn + " not found");
			
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.PathFile.PathFile(Integer) " + e);
		return;
	}
	
	loadFields(rs, "");	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public PathFile(String pathNameIn) 
	throws InstantiationException { 

	if (connection == null)
		init();
		
	ResultSet rs = null;
	
	try {
		findByPathNameSql.setString(1, pathNameIn);
		rs = findByPathNameSql.executeQuery();
		
		if (rs.next() == false)
			throw new InstantiationException("The path: " + pathNameIn + " not found");
			
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.PathFile.PathFile(String) " + e);
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
		addSql.setString(1, pathValue);
		addSql.setInt(2, pathNumber.intValue());
		
		addSql.executeUpdate();
		
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.UserFile.add(): " + e);
	}	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
protected static PathFile addPathFile(String pathValue, 
									  int pathNumber)
									 
	throws InvalidLengthException, Exception
{
	PathFile newPathRecord = new PathFile();
	newPathRecord.setPathValue(pathValue);
	newPathRecord.setPathNumber(pathNumber);
	newPathRecord.add();
	return newPathRecord;
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void addToPathFile(String pathValue, 
								 int pathNumber) 
	throws InvalidLengthException, Exception
{
	PathFile newPathRecord = new PathFile();
	newPathRecord.setPathValue(pathValue);
	newPathRecord.setPathNumber(pathNumber);
	newPathRecord.add();
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void addToPathFile(String pathValue, 
								 int pathNumber,
								 String userProfile) 
	throws InvalidLengthException, Exception
{
	PathFile newPathRecord = new PathFile();
	newPathRecord.setPathValue(pathValue);
	newPathRecord.setPathNumber(pathNumber);
	newPathRecord.add();

	UserFile userFile = new UserFile(userProfile);
	UserFile.addPathToUser(userFile.getUserNumber(), pathNumber);
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void delete() {

	try {
		dltSql.setInt(1, pathNumber.intValue());
		dltSql.executeUpdate();
	} catch (SQLException e) {
		System.out.println("SQL Exception at com.treetop.data.PathFile.delete(): " + e);
	}	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void deleteAllFromPathFile(String pathValue) 
	throws InvalidLengthException, Exception
{
	PathFile aPathRecord = new PathFile(pathValue);
	int pathNumber = aPathRecord.getPathNumber();
	aPathRecord.delete();

	UserFile.deletePathAllUsers(pathNumber);
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public boolean deleteByPathNumber(int pathNumberIn) {

	try {
		dltSql.setInt(1, pathNumberIn);
		dltSql.executeUpdate();		
		return true;
		
	} catch (Exception e) {
		System.out.println("SQL Exception at com.treetop.data.PathFile.delete(): " + e);
		return false;
	}	
}
/**
 * Call this method will return a vector which will include all paths.
 *   The returned vector will be alphabetical by name.
 * Creation date: (5/6/2003 11:41:29 AM)
 */
public Vector findAllPaths() 
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment


	
    //****TESTING****
	//	System.out.println("PathFile.findAllPaths");

   Vector allPaths = new Vector();	
	
	String SQLRun = "SELECT * " +
                    "FROM " + library + "DPPNPATH " +
                    "ORDER BY DPNPATH";

	try
	{		 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun);
		 
		try
		{
 		   	while (rs.next())
   		    {
	      		PathFile buildVector = new PathFile();
  	    		buildVector.loadFields(rs, "");
  	    		allPaths.addElement(buildVector);
		    }
 	       rs.close();
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set (PathFile.findAllPaths)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL (PathFile.findAllPaths) " + e);
	}         
	
	return allPaths;
	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static PathFile findByPathName(String pathNameIn) {

	ResultSet rs = null;
	
	try {
		findByPathNameSql.setString(1, pathNameIn);
		rs = findByPathNameSql.executeQuery();
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.PathFile." +
			               "findByPathName(): " + e);
		return null;
	}
	
	
	try {
		if (rs.next())
		{
			PathFile aPathFile = new PathFile();
			aPathFile.loadFields(rs, "");
			return aPathFile;
		}
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.PathFile.findByPathName(): " + e);
		return null;
	}
	
	return null;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static PathFile findByPathNumber(Integer pathNumberIn) {

	ResultSet rs = null;
	
	try {
		findByPathNumberSql.setInt(1, pathNumberIn.intValue());
		rs = findByPathNumberSql.executeQuery();
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.PathFile." +
			               "findByPathNumber(): " + e);
		return null;
	}
	
	
	try {
		if (rs.next())
		{
			PathFile aPathFile = new PathFile();
			aPathFile.loadFields(rs, "");
			return aPathFile;
		}
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.PathFile.findByPathNumber(): " + e);
		return null;
	}
	
	return null;	
}
/**
 * Call this method will return an array of Path numbers.
 * Creation date: (5/6/2003 11:41:29 AM)
 */
public Vector findPathsbyUserNumber(String userNumber) 
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment


	
    //****TESTING****
	//	System.out.println("PathFile.findPathsbyUserNumber");

   Vector Paths = new Vector();	
	
	String SQLRun = "SELECT * " +
                    "FROM " + library + "DPJPATHU " +
                    "WHERE DPMUSERNBR = " + userNumber + " " +
                    "ORDER BY DPNPATH";

	try
	{		 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun);
		 
		try
		{
 		   	while (rs.next())
   		    {
	      		PathFile buildVector = new PathFile();
  	    		buildVector.loadFields(rs, "byUser");
  	    		Paths.addElement(buildVector);
		    }
 	       rs.close();
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set (PathFile.findPathsbyUserNumber)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL (PathFile.findPathsbyUserNumber) " + e);
	}         
	
	return Paths;
	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public int getPathNumber() {

	return pathNumber.intValue();	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getPathValue() {

	return pathValue;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getPubPath() {

	return pubPath;	
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
			//"DELETE FROM " + library + "DPPNPATH " +
			//" WHERE DPNPATHNBR = ?");

		//addSql = connection.prepareStatement(
			//"INSERT INTO " + library + "DPPNPATH " +
			//" VALUES (?, ? )");
			         

		//updSql = connection.prepareStatement(
			//"UPDATE " + library + "DPPNPATH " +
			//" SET DPNPATH  = ?, DPNPATHNBR = ? " + 
			//" WHERE DPNPATHNBR = ?");

		//findByPathNumberSql = connection.prepareStatement(
			//"SELECT * FROM " + library + "DPPNPATH " +
			//" WHERE DPNPATHNBR = ?");

		//findByPathNameSql = connection.prepareStatement(
			//"SELECT * FROM " + library + "DPPNPATH " +
			//" WHERE DPNPATH = ?");


	//} catch (SQLException e) {
		//System.out.println("SQL exception occured at com.treetop.data.PathFile.init()" +
			               //e);
	//}
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
protected void loadFields(ResultSet rs, String byUser) {

	try {
		
		pathValue         = rs.getString("DPNPATH");
		pathNumber        = new Integer(rs.getInt("DPNPATHNBR"));
		pubPath           = "";
		
		// Define database environment prior to every export (live or test).
		//library = "WKLIB."; // test environment
		library = "TREENET."; // live environment
		
		if (byUser.equals("byUser"))
			pubPath       = rs.getString("DPMPUBLISH");
			
	
	}
	catch (Exception e)
	{
		System.out.println("SQL Exception at com.treetop.data.PathFile.loadFields();" + e);
	}

	persists = true;
				
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void main(String[] args) {

	// add a few paths.
	
	try {
		
		PathFile one = addPathFile("AAA/BBB/CCC", PathFile.nextPathNumber()); 					 
		System.out.println("one: " + one);
		
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.PathFile.main()");
	}
	
	try {
		
		PathFile two = addPathFile("DDD/EEE/FFF", PathFile.nextPathNumber()); 					 
		System.out.println("two: " + two);
		
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.PathFile.main()");
	}
	
	try {
		
		PathFile three = addPathFile("GGG/HHH/III", PathFile.nextPathNumber()); 					 
		System.out.println("three: " + three);
		
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.PathFile.main()");
	}
	
	// find by path name.
	String path2 = "DDD/EEE/FFF";
	try {
		PathFile two = new PathFile(path2);
		System.out.println("find two: " + two);

		// update a role.
		try{
			two.setPathValue("DDD/EEE/FFF/Changed");
		} catch (Exception e) {
			System.out.println("com.treetop.Data.PathFile.Main()" +
				               ". Problem with change test: " + e);
		}

		two.update();

		
		//delete
		two.delete();
	} catch (Exception e) {
		System.out.println("com.treetop.data.PathFile.Main(). " +
			               "Error with find/delete in main" + e);
	}


	// delete one and three.
	try {
		String path1 = "AAA/BBB/CCC";
		PathFile one = new PathFile(path1);
		one.delete();
	} catch (Exception e) {
		System.out.println("com.treetop,data.PathFile.Main(). " + 
			               "delete problem with one: " + e);
	}

	try {
		String path3 = "GGG/HHH/III";
		PathFile three = new PathFile(path3);
		three.delete();
	} catch (Exception e) {
		System.out.println("com.treetop,data.PathFile.Main(). " + 
			               "delete problem with three: " + e);
	}
		

	// find a path that dosent exist.
	String pathx = "XXXXXX";
	try {
		PathFile notTherePathFile = new PathFile(pathx);
		System.out.println("notTherePathFile: " + notTherePathFile);
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
public static int nextPathNumber() 
{
//10/28/11 TWalton -- Not used, 10.6.100.1 points to a machine that has not been valid for 3+ years
//	try {
//		// create a AS400 object
//		AS400 as400 = new AS400("10.6.100.1", "DAUSER", "web230502");
//		ProgramCall pgm = new ProgramCall(as400);

//		ProgramParameter[] parmList = new ProgramParameter[1];
//		parmList[0] = new ProgramParameter(100);
//		pgm = new ProgramCall(as400, "/QSYS.LIB/GNLIB.LIB/CLPATHNBR.PGM", parmList);

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
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setPathNumber(int pathNumberIn) {

	this.pathNumber =  new Integer(pathNumberIn);
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setPathValue(String pathValueIn) throws InvalidLengthException {

	if (pathValueIn.length() > 340)
		throw new InvalidLengthException(
				"pathValueIn", pathValueIn.length(), 340);

	this.pathValue =  pathValueIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setPubPath(String pubPathIn) {

	this.pubPath =  new String(pubPathIn);
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public String toString() {

	return new String(
		"pathValue: " + pathValue + "\n" +
		"pathNumber: " + pathNumber + "\n" +
		"library:  " + library + "\n");
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void update() {

	try {
		updSql.setString(1, pathValue);
		updSql.setInt(2, pathNumber.intValue());
		updSql.setInt(3, pathNumber.intValue());
		updSql.executeUpdate();
		
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.PathFile.update(): " + e);
	}		
}
}
