package com.treetop.data;

import java.text.*;
import java.sql.*;
import java.math.*;
import java.util.*;
import javax.sql.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import com.treetop.utilities.ConnectionStack;

/**
 * Access to RDB file DBLIB/SRPACUST
 *
 * Code used to generate the table.
 *
 * CREATE TABLE DBLIB/SRPACUST (
 *  SRANUMBER  INT        NOT NULL WITH DEFAULT, Customer Number
 *  SRANAME    CHAR ( 30) NOT NULL WITH DEFAULT, Customer Name
 *  SRAADDRES1 CHAR ( 60) NOT NULL WITH DEFAULT, Customer Address 1
 *  SRAADDRES2 CHAR ( 60) NOT NULL WITH DEFAULT, Customer Address 2
 *  SRACITY    CHAR ( 50) NOT NULL WITH DEFAULT, City
 *  SRASTATE   CHAR (  2) NOT NULL WITH DEFAULT, State
 *  SRAZIP     INT  (5,0) NOT NULL WITH DEFAULT, Zip Code
 *  SRAZIPEXT  INT  (4,0) NOT NULL WITH DEFAULT, Zip Code Extention
 *  SRAFGNPOST CHAR (  8) NOT NULL WITH DEFAULT, Foreign Postal Code
 *  SRAFGNCNTY CHAR ( 50) NOT NULL WITH DEFAULT, Foreign Country
 *  SRACONTACT CHAR ( 30) NOT NULL WITH DEFAULT, Customer Contact
 *  SRAPHONE   CHAR ( 30) NOT NULL WITH DEFAULT, Customer Contact Phone
 *  SRAEMAIL   CHAR (100) NOT NULL WITH DEFAULT, Customer Contact Email
 *  SRASHPCMP  CHAR ( 30) NOT NULL WITH DEFAULT, Shipping Company
 *  SRASHPACCT CHAR ( 36) NOT NULL WITH DEFAULT, Shipping Account
 *  SRAUPDDATE DATE       NOT NULL WITH DEFAULT, Update Date
 *  SRAUPDTIME TIME       NOT NULL WITH DEFAULT, Update Time
 *  SRAUPDUSER CHAR ( 10) NOT NULL WITH DEFAULT, Update User
 *  SRACO      CHAR (  5) NOT NULL WITH DEVAULT, Company Number
 *
 * Be sure to set the environment (library) for this file . It
 *  should be "DBLIB." for the live environment and "WKLIB." 
 *  for the test environment.
 *   METHODS TO BE MAINTAINED PRIOR TO EVERY EXPORT:
 *   :init()
 *   :buildDropDownOfAllCustomers(SampleRequestCustomer)
 *   :loadFields(ResultSet,String)
 **/
public class SampleRequestCustomer {

	private Integer       custNumber;
	private String        name;
	private String        address1;
	private String        address2;
	private String        city;
	private String        state;
	private Integer       zip;
	private Integer       zipExtention;
	private String        foreignPostalCode;
	private String        foreignCountry;
	private String        contact;
	private String        contactPhone;
	private String        contactEmail;
	private String        shippingCompany;
	private String        shippingAcct;
	private java.sql.Date updateDate;
	private java.sql.Time updateTime;
	private String        updateUser;
	private String        updateUserLong;
	private String        company;
	
	//live or test environment on the as400
	private String  library; 
	
	//**** For use in Main Method,
    // Constructor Methods and 
    // Update, Add, Delete Methods  ****//
	private static PreparedStatement sqlAdd;
	private static PreparedStatement sqlDelete;
	private static PreparedStatement sqlUpdate;
	private static PreparedStatement sqlFindByCustNumber;
	//private static PreparedStatement sqlFindInfiniumCustByCustNumber;
	private static PreparedStatement sqlFindM3CustByCustNumber;

	// Additional fields.
	private String         canIDeleteIt;
	
	private boolean persists = false;
	private static Connection connection;

	private static String dropDownListAll;
/**
 * Used to Instantiate the SampleRequestCustomer Class.
 *       All the fields will be null.
 *
 * Creation date: (6/11/2003 1:12:39 PM)
 */
public SampleRequestCustomer() 
{
	if (connection == null)
		init();

	loadFieldsEmpty();
}
/**
 * Used to Instantiate the SampleRequestCustomer Class.
 *    By sending in a Customer Number.
 *    The fields will be loaded with information from that Customer.
 *
 * Creation date: (6/11/2003 1:14:39 PM)
 */
public SampleRequestCustomer(Integer custNumberIn)

throws InstantiationException 
{ 
	if (connection == null)
		init();
	
	ResultSet rs = null;
	
	try 
	{
		sqlFindByCustNumber.setInt(1, custNumberIn.intValue());
		rs = sqlFindByCustNumber.executeQuery();
		
		if (rs.next() == false)
			throw new InstantiationException("The Customer: " + custNumberIn + 
											 " was not found");
	} 
	catch (SQLException e) 
	{
		System.out.println("SQL error at " +
						   "com.treetop.data.SampleRequestCustomer.SampleRequestCustomer(Integer) " + 
						   e);
		return;
	}
	
	loadFields(rs);

	try {
		rs.close();
	} 
	catch (SQLException eAny) 
	{
		System.out.println("SQL error at " +
				   "com.treetop.data.SampleRequestCustomer." +
				   "SampleRequestCustomer(Integer) --Close rs Section-- : " + eAny);
	}
	
}
/**
 * Used to Instantiate the SampleRequestCustomer Class.
 *    By sending in a Customer Number.
 *    The fields will be loaded with information from that Customer.
 *
 * Creation date: (6/11/2003 1:14:39 PM)
 */
public SampleRequestCustomer(String companyNumberIn, 
							 String custNumberIn, 
							 String whichOne)

throws InstantiationException 
{
	if (connection == null)
		init();
	
	ResultSet rs = null;

	if (whichOne.equals("infinium"))
	{
		
		if (companyNumberIn.length() == 4)
		   companyNumberIn = " " + companyNumberIn;	
		if (companyNumberIn.length() == 3)
		   companyNumberIn = "  " + companyNumberIn;
		if (companyNumberIn.length() == 2)
		   companyNumberIn = "  0" + companyNumberIn;
		if (companyNumberIn.length() == 1)
		   companyNumberIn = "  00" + companyNumberIn;	
		if (companyNumberIn.length() == 0)
		   companyNumberIn = "  025"; // Default in Ingredient		
	   
//		if (custNumberIn.length() == 9)
//		   custNumberIn = "     " + custNumberIn;
//		if (custNumberIn.length() == 8)
//		   custNumberIn = "      " + custNumberIn;
//		if (custNumberIn.length() == 7)
//		   custNumberIn = "       " + custNumberIn;
//		if (custNumberIn.length() == 6)
//		   custNumberIn = "        " + custNumberIn;
//		if (custNumberIn.length() == 5)	
//		   custNumberIn = "         " + custNumberIn;
//		if (custNumberIn.length() == 4)
//		   custNumberIn = "          " + custNumberIn;
//		if (custNumberIn.length() == 3)
//		   custNumberIn = "           " + custNumberIn;
//		if (custNumberIn.length() == 2)
//		   custNumberIn = "            " + custNumberIn;
//		if (custNumberIn.length() == 1)
//		   custNumberIn = "             " + custNumberIn; 
	
		try 
		{
			sqlFindM3CustByCustNumber.setString(1, custNumberIn.trim());
			//sqlFindM3CustByCustNumber.setString(1, custNumberIn);
			rs = sqlFindM3CustByCustNumber.executeQuery();
			//sqlFindInfiniumCustByCustNumber.setString(1, companyNumberIn);
			//sqlFindInfiniumCustByCustNumber.setString(2, custNumberIn);
			//rs = sqlFindInfiniumCustByCustNumber.executeQuery();
		
			if (rs.next() == false)
				throw new InstantiationException("The Customer: " + custNumberIn + 
													 " was not found");
		} 
		catch (SQLException e) 
		{
			System.out.println("SQL error at " +
							   "com.treetop.data.SampleRequestCustomer.SampleRequestCustomer(String, String, String) " + 
							   e);
			return;
		}
		loadM3Fields(rs);
		//loadInfiniumFields(rs); -- OLD WAY

		try 
		{
			rs.close();
		} 
		catch (SQLException eAny) 
		{
			System.out.println("SQL error at " +
				   "com.treetop.data.SampleRequestCustomer." +
				   "SampleRequestCustomer(Infinium Customer Information) --Close rs Section-- : " + eAny);
		}
	}
}
/**
 * Used to Instantiate the SampleRequestCustomer Class.
 *    By sending in a Result Set Record,
 *    The fields will be loaded with information from that Customer.
 *
 * Creation date: (6/30/2003 1:14:39 PM)
 */
private SampleRequestCustomer(ResultSet rs)

throws InstantiationException 
{ 
	loadFields(rs);	
}
/**
 * Add a record into the Sample Request Customer File.
 *
 * Creation date: (6/11/2003 1:50:29 PM)
 */
private void add() 
{
	try 
	{
		sqlAdd.setInt(1, custNumber.intValue());
		sqlAdd.setString(2, name);
		sqlAdd.setString(3, address1);
		sqlAdd.setString(4, address2);
		sqlAdd.setString(5, city);
		sqlAdd.setString(6, state);
		sqlAdd.setInt(7, zip.intValue());
		sqlAdd.setInt(8, zipExtention.intValue());
		sqlAdd.setString(9, foreignPostalCode);
		sqlAdd.setString(10, foreignCountry);
		sqlAdd.setString(11, contact);
		sqlAdd.setString(12, contactPhone);
		sqlAdd.setString(13, contactEmail);
		sqlAdd.setString(14, shippingCompany);
		sqlAdd.setString(15, shippingAcct);
		sqlAdd.setDate(16, updateDate);
		sqlAdd.setTime(17, updateTime);
		sqlAdd.setString(18, updateUser);
		sqlAdd.setString(19, company);
		
		sqlAdd.executeUpdate();
		
	} 
	catch (SQLException e) 
	{
		System.out.println("SQL error at " +
					"com.treetop.data.UserFile.add(): " + 
					e);
	}	
}
/**
 * Send in all the fields for the record to be added to the file.
 *
 * Creation date: (6/11/2003 1:48:29 PM)
 */
public static void addToSampleRequestCustomer(							
										int custNumber,
										String name,
										String address1,
										String address2,
						 			    String city,
										String state,
										int zip,
										int zipExtention,
										String foreignPostalCode,
										String foreignCountry,
										String contact,
										String contactPhone,
										String contactEmail,
										String shippingCompany,
										String shippingAcct,
										java.sql.Date updateDate,
										java.sql.Time updateTime,
										String updateUser,
										String company)
throws InvalidLengthException, Exception
{
	SampleRequestCustomer newRecord = new SampleRequestCustomer();
	
	newRecord.setCustNumber(custNumber);
	newRecord.setName(name);
	newRecord.setAddress1(address1);
	newRecord.setAddress2(address2);
	newRecord.setCity(city);
	newRecord.setState(state);
	newRecord.setZip(zip);
	newRecord.setZipExtention(zipExtention);
	newRecord.setForeignPostalCode(foreignPostalCode);
	newRecord.setForeignCountry(foreignCountry);
	newRecord.setContact(contact);
	newRecord.setContactPhone(contactPhone);
	newRecord.setContactEmail(contactEmail);
	newRecord.setShippingCompany(shippingCompany);
	newRecord.setShippingAcct(shippingAcct);
	newRecord.setUpdateDate(updateDate);
	newRecord.setUpdateTime(updateTime);
	newRecord.setUpdateUser(updateUser);
	newRecord.setCompany(company);
	
	newRecord.add();

}
/**
 * Send in the Class to be added to the file.
 *
 * Creation date: (7/17/2003 10:28:29 AM)
 */
public static void addToSampleRequestCustomer(
					SampleRequestCustomer newRecord)
throws InvalidLengthException, Exception
{
	newRecord.add();
}
/**
 * Drop down list for All Customers.
 *
 * Creation date: (6/24/2003 1:01:38 PM)
 */
public static String buildDropDownOfAllCustomers(String chosenCustNumber,
	                                            String chooseName, 
	                                            String selectStatement)  
{
	// this string was created to skip the method.
	// because of the length of the drop down list.
	String commentOut = "YES";
	if (commentOut.equals("NO"))
	{
	if (dropDownListAll == null)
	{	

	// Define Variables 
	String dropDown     = "";
	String selected     = "";
	String selectOption = "";
	// Define Selection Information
	if (selectStatement.equals("") || 
		selectStatement.equals("null"))
	    selectOption = "Select a Customer--->:";
	else {
		 if (selectStatement.trim().equals("*all"))
		     selectOption = "*all";
	     else
	         selectOption = "Select a " + selectStatement.trim() + "--->";
	     }
	
	// Get list of all customers, build Drop Down List.

	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "DBLIB."; // live environment
	String saveCust = "";
	try 
	{
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(
			"SELECT * FROM " + library + "SRPACUST " +
			"ORDER BY SRANAME");
		 
		while(rs.next())
		{
			if (!saveCust.equals(rs.getString("SRANUMBER")))
			{
				saveCust = rs.getString("SRANUMBER");
		   		if (rs.getString("SRANUMBER").trim().equals(chosenCustNumber.trim()))
		   		    selected = "' selected='selected'>";
	 	  		    else
	  	 		    selected = "'>";
		   		    
	   			    dropDown = dropDown + "<option value='" + 
	   			    rs.getString("SRANUMBER").trim() + selected +
	   			    rs.getString("SRANAME").trim() + "&nbsp;-&nbsp;" +
	   		 	   rs.getString("SRANUMBER").trim() + "&nbsp;";
			}
		}
		rs.close();		
	} 
	catch (SQLException e) 
	{
		System.out.println("SQL error at " +
						   "com.treetop.data.SampleRequestCustomer.buildDropDownOfAllCustomers() " + 
						   e);
	}

	if (!dropDown.equals(""))
	{	   		    
 	    dropDown = "<select name='" + chooseName.trim() + "' >" +
 	    		   "<option value='None' selected>" + selectOption +
 	    		   dropDown + "</select>";  	 
    }

	dropDownListAll = dropDown;
	}
 } 	  		
 return dropDownListAll;
	
}
/**
 * Drop down list for All Customers.
 *
 * Creation date: (6/24/2003 1:01:38 PM)
 */
public static String buildDropDownOfAllCustomers(String chosenCustNumber,
	                                            String chooseName, 
	                                            String selectStatement,
	                                            String only25)  
{
	// this string was created to skip the method.
	// because of the length of the drop down list.
	String commentOut = "YES";
	if (commentOut.equals("NO"))
	{
	if (dropDownListAll == null)
	{	

	// Define Variables 
	String dropDown     = "";
	String selected     = "";
	String selectOption = "";
	// Define Selection Information
	if (selectStatement.equals("") || 
		selectStatement.equals("null"))
	    selectOption = "Select a Customer--->:";
	else {
		 if (selectStatement.trim().equals("*all"))
		     selectOption = "*all";
	     else
	         selectOption = "Select a " + selectStatement.trim() + "--->";
	     }
	
	// Get list of all customers, build Drop Down List.

	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "DBLIB."; // live environment
	String saveCust = "";
	try 
	{
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(
			"SELECT * FROM " + library + "SRPACUST " +
			"ORDER BY SRANAME");

		int x = 0;
		while(rs.next() && x < 25)
		{
			x = x + 1;
			if (!saveCust.equals(rs.getString("SRANUMBER")))
			{
				saveCust = rs.getString("SRANUMBER");
		   		if (rs.getString("SRANUMBER").trim().equals(chosenCustNumber.trim()))
		   		    selected = "' selected='selected'>";
	 	  		    else
	  	 		    selected = "'>";
		   		    
	   			    dropDown = dropDown + "<option value='" + 
	   			    rs.getString("SRANUMBER").trim() + selected +
	   			    rs.getString("SRANAME").trim() + "&nbsp;-&nbsp;" +
	   		 	   rs.getString("SRANUMBER").trim() + "&nbsp;";
			}
		}
		rs.close();		
	} 
	catch (SQLException e) 
	{
		System.out.println("SQL error at " +
						   "com.treetop.data.SampleRequestCustomer.buildDropDownOfAllCustomers() " + 
						   e);
	}

	if (!dropDown.equals(""))
	{	   		    
 	    dropDown = "<select name='" + chooseName.trim() + "' >" +
 	    		   "<option value='None' selected>" + selectOption +
 	    		   dropDown + "</select>";  	 
    }

	dropDownListAll = dropDown;
	}
	}
  	  		
 return dropDownListAll; 
}
/**
 * Delete a record from the Sample Request Customer File.
 *
 * Creation date: (6/11/2003 1:50:29 PM)
 */
private void delete() 
{
	try 
	{
		sqlDelete.setInt(1, custNumber.intValue());
		
		sqlDelete.executeUpdate();
		
	} 
	catch (SQLException e) 
	{
		System.out.println("SQL Exception at " + 
					"com.treetop.data.SampleRequestCustomer.delete(): " + 
					e);
	}	
}
/**
 * Delete a record from the Sample Request Customer File.
 *    will delete based on the Customer Number sent into this method.
 *
 * Creation date: (6/11/2003 2:04:29 PM)
 */
public boolean deleteByCustNumber(int custNumberIn) 
{
	
	try 
	{
		sqlDelete.setInt(1, custNumberIn);
		
		sqlDelete.executeUpdate();
	
		return true;	
	} 
	catch (Exception e) 
	{
		System.out.println("SQL Exception at " +
						"com.treetop.data.SampleRequestCustomer.deleteByCustNumber(): " + 
						e);
		return false;
	}
}
/**
 * Retrieve from the class the First Line of the Address,
 *
 * Creation date: (6/11/2003 10:56:28 AM)
 */
public String getAddress1() 
{
	return address1;	
}
/**
 * Retrieve from the class the Second Line of the Address,
 *
 * Creation date: (6/11/2003 10:56:28 AM)
 */
public String getAddress2() 
{
	return address2;	
}
/**
 * Retrieve from the class the City,
 *
 * Creation date: (6/11/2003 10:57:28 AM)
 */
public String getCity() 
{
	return city;	
}
/**
 * Retrieve from the class the Company who updated the Record,
 *
 * Creation date: (7/2/2003 1:53:28 PM)
 */
public String getCompany() 
{
	return company;	
}
/**
 * Retrieve from the class the Customer Contact,
 *
 * Creation date: (6/11/2003 11:00:28 AM)
 */
public String getContact() 
{
	return contact;	
}
/**
 * Retrieve from the class the Customer Contact Email,
 *
 * Creation date: (6/11/2003 11:01:28 AM)
 */
public String getContactEmail() 
{
	return contactEmail;	
}
/**
 * Retrieve from the class the Customer Contact Phone,
 *
 * Creation date: (6/11/2003 11:01:28 AM)
 */
public String getContactPhone() 
{
	return contactPhone;	
}
/**
 * Retrieve from the class the Customer Number,
 *
 * Creation date: (6/11/2003 10:53:28 AM)
 */
public int getCustNumber() 
{
	return custNumber.intValue();	
}
/**
 * Retrieve from the class the Foreign Country,
 *
 * Creation date: (6/11/2003 11:00:28 AM)
 */
public String getForeignCountry() 
{
	return foreignCountry;	
}
/**
 * Retrieve from the class the Foreign Postal Code,
 *
 * Creation date: (6/11/2003 10:59:28 AM)
 */
public String getForeignPostalCode() 
{
	return foreignPostalCode;	
}
/**
 * Retrieve from the class the Customer Name,
 *
 * Creation date: (6/11/2003 10:55:28 AM)
 */
public String getName() 
{
	return name;	
}
/**
 * Retrieve from the class the Shipping Account,
 *
 * Creation date: (6/11/2003 11:02:28 AM)
 */
public String getShippingAcct() 
{
	return shippingAcct;	
}
/**
 * Retrieve from the class the Shipping Company,
 *
 * Creation date: (6/11/2003 11:02:28 AM)
 */
public String getShippingCompany() 
{
	return shippingCompany;	
}
/**
 * Retrieve from the class the State,
 *
 * Creation date: (6/11/2003 10:57:28 AM)
 */
public String getState() 
{
	return state;	
}
/**
 * Retrieve from the class the Date the Record was updated,
 *
 * Creation date: (6/11/2003 11:03:28 AM)
 */
public java.sql.Date getUpdateDate() 
{
	return updateDate;	
}
/**
 * Retrieve from the class the Time the Record was updated,
 *
 * Creation date: (6/11/2003 11:04:28 AM)
 */
public java.sql.Time getUpdateTime() 
{
	return updateTime;	
}
/**
 * Retrieve from the class the User of the Record was updated,
 *
 * Creation date: (6/11/2003 11:04:28 AM)
 */
public String getUpdateUser() 
{
	return updateUser;	
}
/**
 * Retrieve from the class the User of the Record was updated,
 *   Additional field in the class which has the full name of the user.
 *
 * Creation date: (7/17/2003 4:09:28 PM)
 */
public String getUpdateUserLong() 
{
	return updateUserLong;	
}
/**
 * Retrieve from the class the Zip Code,
 *
 * Creation date: (6/11/2003 10:58:28 AM)
 */
public int getZip() 
{
	return zip.intValue();	
}
/**
 * Retrieve from the class the Zip Code Extention,
 *
 * Creation date: (6/11/2003 10:59:28 AM)
 */
public int getZipExtention() 
{
	return zipExtention.intValue();	
}
/**
 * Used to build prepare Statements, define variables.
 * 
 * Creation date: (6/11/2003 11:52:29 AM)
 */
public void init() {
	
	// Test for initial connection.

//	System.out.println("persists = " + persists);	
	if (this.persists == false) 
	{
		connection = ConnectionStack.getConnection();
	    //connection = SQLConnect.connection; // OLD BOX
	    this.persists = true;
	}
	

	// Define database environment prior to every export (live or test).
	//library = "WKLIB."; // test environment
	//library = "DBLIB."; // live environment
	library = "DBPRD."; // Live M3 Environment

	try 
	{
		sqlAdd = connection.prepareStatement(
			"INSERT INTO " + library + "SRPACUST " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		sqlDelete = connection.prepareStatement(
			"DELETE FROM " + library + "SRPACUST " +
			" WHERE SRANUMBER = ?");

		sqlUpdate = connection.prepareStatement(
			"UPDATE " + library + "SRPACUST " +
			" SET SRANUMBER  = ?, SRANAME    = ?, SRAADDRES1 = ?, " +
			    " SRAADDRES2 = ?, SRACITY    = ?, SRASTATE   = ?, " +
			    " SRAZIP     = ?, SRAZIPEXT  = ?, SRAFGNPOST = ?, " +
			    " SRAFGNCNTY = ?, SRACONTACT = ?, SRAPHONE   = ?, " +
			    " SRAEMAIL   = ?, SRASHPCMP  = ?, SRASHPACCT = ?, " +
			    " SRAUPDDATE = ?, SRAUPDTIME = ?, SRAUPDUSER = ?, " +
			    " SRACO = ? " + 
			" WHERE SRANUMBER = ?");

		sqlFindByCustNumber = connection.prepareStatement(
			"SELECT * FROM " + library + "SRPACUST " +
			" WHERE SRANUMBER = ?");

	//	sqlFindInfiniumCustByCustNumber = connection.prepareStatement(
	//		"SELECT * FROM ARDBFA.ARLCU " +
	//		" WHERE CUCO = ? AND CUCUNO = ? ");

		sqlFindM3CustByCustNumber = connection.prepareStatement(
				"SELECT OKCUNO, OKCUNM, OKCUA1, OKCUA2, OKPHNO " +
				" FROM M3DJDPRD.OCUSMA " +
				" WHERE OKCUNO = ? ");	

	} 
	catch (SQLException e) 
	{
		System.out.println("SQL exception occured at " + 
			 			"com.treetop.data.SampleRequestCustomer.init() " +
			               e);
	}
}
/**
 * Take a result set, (from an SQL Query) and
 *    set the information into the fields within
 *    this class.
 *
 * Creation date: (6/11/2003 11:07:29 AM)
 */
protected void loadFields(ResultSet rs) 
{
	try 
	{
		custNumber		  = new Integer(rs.getInt("SRANUMBER"));
	    name      	      =             rs.getString("SRANAME").trim();
	    address1 	      =             rs.getString("SRAADDRES1").trim();
	    address2	      =             rs.getString("SRAADDRES2").trim();
	    city     	      =             rs.getString("SRACITY").trim();
	    state			  =             rs.getString("SRASTATE").trim();
		zip 	     	  = new Integer(rs.getInt("SRAZIP"));
		zipExtention	  = new Integer(rs.getInt("SRAZIPEXT")); 
	    foreignPostalCode =             rs.getString("SRAFGNPOST").trim();
	    foreignCountry    =             rs.getString("SRAFGNCNTY").trim();
	    contact 		  =             rs.getString("SRACONTACT").trim();
	    contactPhone	  =             rs.getString("SRAPHONE").trim();
	    contactEmail 	  =             rs.getString("SRAEMAIL").trim();
	    shippingCompany   =             rs.getString("SRASHPCMP").trim();
	    shippingAcct 	  =             rs.getString("SRASHPACCT").trim();
	    updateDate        = 			rs.getDate("SRAUPDDATE");
	    updateTime        = 			rs.getTime("SRAUPDTIME");
	    updateUser 		  =             rs.getString("SRAUPDUSER").trim();
		company           =             rs.getString("SRACO").trim();
		updateUserLong    = updateUser;
	
		// Define database environment prior to every export (live or test).
		//library = "WKLIB."; // test environment
		library = "DBLIB."; // live environment
		try
		{
			//UserFile newUser = new UserFile(updateUser);
			//Integer  newUserNum = new Integer(newUser.getUserNumber());
			//newUser = new UserFile(newUserNum);
			//updateUserLong = newUser.getUserNameLong().trim();
			
		}
		catch (Exception e)
		{
	//		Do not need this out.println, will have Profile if not user Name.
	//		System.out.println("Person not found in User File.  Problem occured " +
	//                           " in SampleRequestCustomer.loadFields " + e);   			
		}
		
	
	}
	catch (Exception e)
	{
		System.out.println("SQL Exception at com.treetop.data.SampleRequestCustomer.loadFields();" + e);
	}

	persists = true;
				
}
/**
 * Used to Instantiate the class with blank, or 0 fields.
 *  EMPTY
 *
 * Creation date: (7/17/2003 3:25:29 PM)
 */
protected void loadFieldsEmpty() 
{
	try 
	{
		custNumber		  = new Integer(0);
	    name      	      = "";
	    address1 	      = "";
	    address2	      = "";
	    city     	      = "";
	    state			  = "";
		zip 	     	  = new Integer(0);
		zipExtention	  = new Integer(0); 
	    foreignPostalCode = "";
	    foreignCountry    = "";
	    contact 		  = "";
	    contactPhone	  = "";
	    contactEmail 	  = "";
	    shippingCompany   = "";
	    shippingAcct 	  = "";
	    updateDate        = java.sql.Date.valueOf("1900-01-01");
	    updateTime        = java.sql.Time.valueOf("00:00:00");
	    updateUser 		  = "";
		company           = "";
		updateUserLong    = "";
	
		// Define database environment prior to every export (live or test).
		//library = "WKLIB."; // test environment
		library = "DBLIB."; // live environment
	
	}
	catch (Exception e)
	{
		System.out.println("SQL Exception at com.treetop.data.SampleRequestCustomer.loadFieldsEmpty();" + e);
	}

	persists = true;
				
}
/**
 * Take a result set, (from an SQL Query) and
 *    set the information into the fields within
 *    this class.
 *    Loading this field with classes from the ARLCU File.
 *
 * Creation date: (7/3/2003 4:01:29 PM)
 */
protected void loadInfiniumFieldsDELETE(ResultSet rs) 
{
	try 
	{
		custNumber		  = new Integer(rs.getInt("CUCUNO"));
	    name      	      =             rs.getString("CUNAME").trim();
	    address1 	      =             rs.getString("CUADR1").trim();
	    address2	      =             rs.getString("CUADR2").trim();
	    city     	      =             rs.getString("CUCITY").trim();
	    state			  =             rs.getString("CUSTAT").trim();
		zip               = new Integer ("0");
		zipExtention      = new Integer ("0");
	    
	    foreignCountry    =             rs.getString("CUCTRY").trim();
	    if (foreignCountry.length() == 0 || foreignCountry.equals("USA"))
	    {
		    if (rs.getString("CUPOST").length() != 0)
		    {
			    int zipLength = rs.getString("CUPOST").trim().length();
			    String whatZip = rs.getString("CUPOST");
			    if (zipLength == 5)
			    	zip           = new Integer (rs.getString("CUPOST").trim());
			    if (zipLength == 10)
			    {
			    	zip           = new Integer (rs.getString("CUPOST").substring(0,5));
			    	zipExtention  = new Integer (rs.getString("CUPOST").substring(6,zipLength));
			    }
			}
		    foreignPostalCode     = "";
			
	    }
		else
	    {
			foreignPostalCode     = rs.getString("CUPOST").trim();

	    }
		company           =         rs.getString("CUCO").trim();
	    contactPhone	  =         rs.getString("CUTEL").trim();

	    contact 		  =         "";
	    contactEmail 	  =         "";
	    shippingCompany   =         "";
	    shippingAcct 	  =         "";
  		updateDate        = java.sql.Date.valueOf("1900-01-01");
	    updateTime        = java.sql.Time.valueOf("00:00:00");
	    updateUser 		  = "";
		updateUserLong    = "";
	}
	catch (Exception e)
	{
		System.out.println("SQL Exception at com.treetop.data.SampleRequestCustomer.loadInfiniumFields();" + e);
	}

	persists = true;
				
}
/**
 * Use to test the prepared statements,
 *                 Add, Delete, Update,
 *     and whatever else needs testing.
 *
 * Creation date: (6/11/2003 2:34:29 PM)
 */
public static void main(String[] args) 
{
	java.sql.Date theDate = java.sql.Date.valueOf("2003-01-17");
	java.sql.Time theTime = java.sql.Time.valueOf("12:13:14"); 
	
	if ("x" == "y")
	{
	
	// add a few customers.
	try 
	{
		SampleRequestCustomer one = mainAddSampleRequestCustomer(
								1, "Customer Name",
								"1st Line of Address", "2nd Line of Address",
								"City", "ST", 54321, 1234, "PostCode",
								"Foreign Country", "Contact", "Contact Phone",
								"Contact Email", "Shipping Company", 
								"Shipping Account", theDate, theTime, "User",
								"025"); 

	//	System.out.println("one: " + one);
		
	} 
	catch (Exception e) 
	{
		System.out.println("error at ONE " +
			         "com.treetop.data.SampleRequestCustomer.main()" + 
			         e);
	}
	
	try 
	{
		SampleRequestCustomer two = mainAddSampleRequestCustomer(
								2, "ABC Company",
								"123 West 6th Street", "",
								"Yakima", "WA", 99999, 1111, "",
								"", "Mr. ZZZZZ", "509-555-5555",
								"ZZZZ@ZZZZ.com", "Moving Freight", 
								"", theDate, theTime, "TWALTO", "025"); 
	//	System.out.println("two: " + two);
		
	} 
	catch (Exception e) 
	{
		System.out.println("error at TWO " +
			         "com.treetop.data.SampleRequestCustomer.main()" + 
			         e);
	}
	
	try 
	{
		SampleRequestCustomer three = mainAddSampleRequestCustomer(
								3, "XYZ Inc.",
								"987 North Freight Ave", "Suite 123A",
								"Vancouver", "", 0, 0, "ZZZXY",
								"Canada", "Ms. Money", "487-999-6541",
								"canadaMoney@XYZ.com", "", 
								"", theDate, theTime, "THAILE", "001"); 
	//	System.out.println("three: " + three);
		
	} 
	catch (Exception e) 
	{
		System.out.println("error at THREE " +
			         "com.treetop.data.SampleRequestCustomer.main()" + 
			         e);
	}

	
	// find (instatiate) Class by customer Number.
	Integer cust2 = new Integer(2);
	try 
	{
		SampleRequestCustomer two = new SampleRequestCustomer(cust2);
		System.out.println("find two: " + two);

		// update the Name
		try
		{
			two.setName("ABC Company/Changed");
		}
		catch (Exception e) 
		{
			System.out.println("com.treetop.Data.SampleRequestCustomer.Main()" +
				               ". Problem with change test: " + e);
		}

		two.update();
		
		//delete 2nd Record
		two.delete();
	} 
	catch (Exception e) 
	{
		System.out.println("com.treetop.data.SampleRequestCustomer.Main(). " +
			               "Error with find/delete in main" + e);
	}

	// delete one and three.
	try 
	{
		Integer cust1 = new Integer(1);
		SampleRequestCustomer one = new SampleRequestCustomer(cust1);
		one.delete();
	} 
	catch (Exception e) 
	{
		System.out.println("com.treetop,data.SampleRequestCustomer.Main(). " + 
			               "delete problem with one: " + e);
	}

	try 
	{
		Integer cust3 = new Integer(3);
		SampleRequestCustomer three = new SampleRequestCustomer(cust3);
		three.delete();
	} 
	catch (Exception e) 
	{
		System.out.println("com.treetop,data.SampleRequestCustomer.Main(). " + 
			               "delete problem with three: " + e);
	}
		
	// find a Customer that dosent exist.
	try
	{
		Integer cust = new Integer(9999);
		SampleRequestCustomer notThere = new SampleRequestCustomer(cust);
		System.out.println("notThere: " + notThere);
	} 
	catch (InstantiationException ie) 
	{
		System.out.println("record not there: " + ie);
	}
	//  Access the ARLCU file.  with a customer Number.  (Testing)
	try 
	{
		SampleRequestCustomer inf = new SampleRequestCustomer("","21102","infinium");
		System.out.println("find inf: " + inf);

	} 
	catch (Exception e) 
	{
		System.out.println("com.treetop.data.SampleRequestCustomer.Main(). " +
			               "Error with infinium stuff " + e);
	}
	}
	
	if ("x" == "x")
	{
		int x = 0;
		x = nextSampleRequestCustomerNumber();
		String stophere = "x";
	}


}
/**
 * Used to Test within the Class 
 *    Specifically used with the Main Method.
 *
 * Creation date: (6/11/2003 1:12:39 PM)
 */
protected static SampleRequestCustomer mainAddSampleRequestCustomer(
										int custNumber,
										String name,
										String address1,
										String address2,
						 			    String city,
										String state,
										int zip,
										int zipExtention,
										String foreignPostalCode,
										String foreignCountry,
										String contact,
										String contactPhone,
										String contactEmail,
										String shippingCompany,
										String shippingAcct,
										java.sql.Date updateDate,
										java.sql.Time updateTime,
										String updateUser,
										String company)
throws InvalidLengthException, Exception
{
	SampleRequestCustomer newRecord = new SampleRequestCustomer();
	
	newRecord.setCustNumber(custNumber);
	newRecord.setName(name);
	newRecord.setAddress1(address1);
	newRecord.setAddress2(address2);
	newRecord.setCity(city);
	newRecord.setState(state);
	newRecord.setZip(zip);
	newRecord.setZipExtention(zipExtention);
	newRecord.setForeignPostalCode(foreignPostalCode);
	newRecord.setForeignCountry(foreignCountry);
	newRecord.setContact(contact);
	newRecord.setContactPhone(contactPhone);
	newRecord.setContactEmail(contactEmail);
	newRecord.setShippingCompany(shippingCompany);
	newRecord.setShippingAcct(shippingAcct);
	newRecord.setUpdateDate(updateDate);
	newRecord.setUpdateTime(updateTime);
	newRecord.setUpdateUser(updateUser);
	newRecord.setCompany(company);
	
	newRecord.add();
	
	return newRecord;
}
/**
 * Convert the Old Files into the New Ones
 *   May need to change the customer Number.
 *
 * Creation date: (7/9/2003 11:34:29 AM)
 */
public static void mainConversion() 
{    
	//SQLRun = SQLRun + SQLDetail + SQLWhere + SQLOrder;
	
//	try
//	{		 
//		Statement stmt = connection.createStatement();
//		ResultSet rs = stmt.executeQuery(SQLRun);
//		 
//		try
//		{
// 		   	while (rs.next())
//  		    {
//	   		    SampleRequestCustomer buildVector = new SampleRequestCustomer(rs);
//  	    		returnCustomers.addElement(buildVector);
//		    }
// 	       rs.close();
//	 	}
//		catch (Exception e)
	//	{
	//		System.out.println("Exception Error while Reading a result set (SampleRequestCustomer.findCustomer)" + e);
	//	}
//	}
//	catch (Exception e)
//	{
//		System.out.println("Exception on Running SQL (SampleRequestCustomer.findCustomer) " + e);
//	}




	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void newMethod() {}
/**
 * Use this Method to get a new Sample Request Customer Number for Add/Copy.
 * Creation date: (7/8/2003 10:03:39 AM)
 */
public static int nextSampleRequestCustomerNumber() 
{
	AS400 as400 = null;

	try {
		// create a AS400 object
		as400 = ConnectionStack.getAS400Object();
		//AS400 as400 = new AS400("10.6.100.1", "DAUSER", "web230502");
		ProgramCall pgm = new ProgramCall(as400);

		ProgramParameter[] parmList = new ProgramParameter[1];
		parmList[0] = new ProgramParameter(100);
		pgm = new ProgramCall(as400, "/QSYS.LIB/MOVEX.LIB/CLSCUSTNBR.PGM", parmList);

		if (pgm.run() != true)
		{
			return 0;
		} 
		else 
		{
			AS400PackedDecimal pd = new AS400PackedDecimal(9, 0);
			byte[] data = parmList[0].getOutputData();
			double dd = pd.toDouble(data, 0);
			int i = (int) dd;
			as400.disconnectService(AS400.COMMAND);
			return i;
		}

	} catch (Exception e) {
		return 0;
	}
			
	finally {
		if (as400 != null)
			ConnectionStack.returnAS400Object(as400);
	}

}
/**
 * Set field into the class for First Line of the Address,
 *
 * Creation date: (6/11/2003 10:27:28 AM)
 */
public void setAddress1(String address1In) 

throws InvalidLengthException 
{
	if (address1In.length() > 60)
		throw new InvalidLengthException(
				"address1In", address1In.length(), 60);

	this.address1 = address1In;
}
/**
 * Set field into the class for Second Line of the Address,
 *
 * Creation date: (6/11/2003 10:27:28 AM)
 */
public void setAddress2(String address2In) 

throws InvalidLengthException 
{
	if (address2In.length() > 60)
		throw new InvalidLengthException(
				"address2In", address2In.length(), 60);

	this.address2 = address2In;
}
/**
 * Set field into the class for City,
 *
 * Creation date: (6/11/2003 10:31:28 AM)
 */
public void setCity(String cityIn) 

throws InvalidLengthException 
{
	if (cityIn.length() > 50)
		throw new InvalidLengthException(
				"cityIn", cityIn.length(), 50);

	this.city = cityIn;
}
/**
 * Set field into the class for Company,
 *
 * Creation date: (7/2/2003 1:51:28 PM)
 */
public void setCompany(String companyIn) 

throws InvalidLengthException 
{
	if (companyIn.length() > 5)
		throw new InvalidLengthException(
				"companyIn", companyIn.length(), 5);

	this.company = companyIn;
}
/**
 * Set field into the class for Customer Contact,
 *
 * Creation date: (6/11/2003 10:40:28 AM)
 */
public void setContact(String contactIn) 

throws InvalidLengthException 
{
	if (contactIn.length() > 30)
		throw new InvalidLengthException(
				"contactIn", contactIn.length(), 30);

	this.contact = contactIn;
}
/**
 * Set field into the class for Customer Contact Email Address,
 *
 * Creation date: (6/11/2003 10:43:28 AM)
 */
public void setContactEmail(String contactEmailIn) 

throws InvalidLengthException 
{
	if (contactEmailIn.length() > 100)
		throw new InvalidLengthException(
				"contactEmailIn", contactEmailIn.length(), 100);

	this.contactEmail = contactEmailIn;
}
/**
 * Set field into the class for Customer Contact Phone Number,
 *
 * Creation date: (6/11/2003 10:42:28 AM)
 */
public void setContactPhone(String contactPhoneIn) 

throws InvalidLengthException 
{
	if (contactPhoneIn.length() > 30)
		throw new InvalidLengthException(
				"contactPhoneIn", contactPhoneIn.length(), 30);

	this.contactPhone = contactPhoneIn;
}
/**
 * Set field into the class for Customer Number,
 *
 * Creation date: (6/11/2003 10:24:28 AM)
 */
public void setCustNumber(int custNumberIn) 
{
	this.custNumber =  new Integer(custNumberIn);
}
/**
 * Set field into the class for Foreign Country,
 *
 * Creation date: (6/11/2003 10:38:28 AM)
 */
public void setForeignCountry(String foreignCountryIn) 

throws InvalidLengthException 
{
	if (foreignCountryIn.length() > 50)
		throw new InvalidLengthException(
				"foreignCountryIn", foreignCountryIn.length(), 50);

	this.foreignCountry = foreignCountryIn;
}
/**
 * Set field into the class for Foreign Postal Code,
 *
 * Creation date: (6/11/2003 10:36:28 AM)
 */
public void setForeignPostalCode(String foreignPostalCodeIn) 

throws InvalidLengthException 
{
	if (foreignPostalCodeIn.length() > 8)
		throw new InvalidLengthException(
				"foreignPostalCodeIn", foreignPostalCodeIn.length(), 8);

	this.foreignPostalCode = foreignPostalCodeIn;
}
/**
 * Set field into the class for Customer Name,
 *
 * Creation date: (6/11/2003 10:24:28 AM)
 */
public void setName(String nameIn) 

throws InvalidLengthException 
{
	if (nameIn.length() > 30)
		throw new InvalidLengthException(
				"nameIn", nameIn.length(), 30);

	this.name = nameIn;
}
/**
 * Set field into the class for Shipping Account,
 *
 * Creation date: (6/11/2003 10:46:28 AM)
 */
public void setShippingAcct(String shippingAcctIn) 

throws InvalidLengthException 
{
	if (shippingAcctIn.length() > 36)
		throw new InvalidLengthException(
				"shippingAcctIn", shippingAcctIn.length(), 36);

	this.shippingAcct = shippingAcctIn;
}
/**
 * Set field into the class for Customer Prefered Shipping Company,
 *
 * Creation date: (6/11/2003 10:44:28 AM)
 */
public void setShippingCompany(String shippingCompanyIn) 

throws InvalidLengthException 
{
	if (shippingCompanyIn.length() > 30)
		throw new InvalidLengthException(
				"shippingCompanyIn", shippingCompanyIn.length(), 30);

	this.shippingCompany = shippingCompanyIn;
}
/**
 * Set field into the class for State,
 *
 * Creation date: (6/11/2003 10:32:28 AM)
 */
public void setState(String stateIn) 

throws InvalidLengthException 
{
	if (stateIn.length() > 2)
		throw new InvalidLengthException(
				"stateIn", stateIn.length(), 2);

	this.state = stateIn;
}
/**
 * Set field into the class for Date record was updated,
 *
 * Creation date: (6/11/2003 10:48:28 AM)
 */
public void setUpdateDate(java.sql.Date updateDateIn)  
{
	this.updateDate =  updateDateIn;
}
/**
 * Set field into the class for Time record was updated,
 *
 * Creation date: (6/11/2003 10:50:28 AM)
 */
public void setUpdateTime(java.sql.Time updateTimeIn)  
{
	this.updateTime =  updateTimeIn;
}
/**
 * Set field into the class for User who updated the record,
 *
 * Creation date: (6/11/2003 10:51:28 AM)
 */
public void setUpdateUser(String updateUserIn) 

throws InvalidLengthException 
{
	if (updateUserIn.length() > 10)
		throw new InvalidLengthException(
				"updateUserIn", updateUserIn.length(), 10);

	this.updateUser = updateUserIn;
}
/**
 * Set field into the class for Full user name of who updated the record,
 *
 * Creation date: (7/17/2003 4:10:28 PM)
 */
public void setUpdateUserLong(String updateUserLongIn) 
{

	this.updateUserLong = updateUserLongIn;
}
/**
 * Set field into the class for Zip Code,
 *
 * Creation date: (6/11/2003 10:33:28 AM)
 */
public void setZip(int zipIn) 
{
	this.zip =  new Integer(zipIn);
}
/**
 * Set field into the class for Zip Code Extention,
 *
 * Creation date: (6/11/2003 10:34:28 AM)
 */
public void setZipExtention(int zipExtentionIn) 
{
	this.zipExtention =  new Integer(zipExtentionIn);
}
/**
 * String values for each variable.
 *
 * Creation date: (6/11/2003 2:10:29 PM)
 */
public String toString() 
{
	return new String(
        "custNumber: "        + custNumber        + "\n" +
	    "name: "              + name              + "\n" +
	    "address1: "          + address1          + "\n" +
	    "address2: "          + address2	      + "\n" +
	    "city: "              + city              + "\n" +
	    "state: "             + state             + "\n" +
		"zip: "               + zip     	      + "\n" +
		"zipExtention: "      + zipExtention      + "\n" +
	    "foreignPostalCode: " + foreignPostalCode + "\n" +
	    "foreignCountry: "    + foreignCountry    + "\n" +
	    "contact: "           + contact           + "\n" +
	    "contactPhone: "	  + contactPhone      + "\n" +
	    "contactEmail: " 	  + contactEmail      + "\n" +
	    "shippingCompany: "   + shippingCompany   + "\n" +
	    "shippingAcct: " 	  + shippingAcct      + "\n" +
	    "updateDate: "        + updateDate        + "\n" +
	    "updateTime: "        + updateTime  	  + "\n" +
	    "updateUser: " 		  + updateUser        + "\n" +
	    "company: "           + company           + "\n" +
		"library: "           + library           + "\n");
}
/**
 * Update a record from the Sample Request Customer File.
 *
 * Creation date: (6/11/2003 2:09:29 PM)
 */
private void update() 
{
	try 
	{
	// Filling the fields
		sqlUpdate.setInt(1, custNumber.intValue());
		sqlUpdate.setString(2, name);
		sqlUpdate.setString(3, address1);
		sqlUpdate.setString(4, address2);
		sqlUpdate.setString(5, city);
		sqlUpdate.setString(6, state);
		sqlUpdate.setInt(7, zip.intValue());
		sqlUpdate.setInt(8, zipExtention.intValue());
		sqlUpdate.setString(9, foreignPostalCode);
		sqlUpdate.setString(10, foreignCountry);
		sqlUpdate.setString(11, contact);
		sqlUpdate.setString(12, contactPhone);
		sqlUpdate.setString(13, contactEmail);
		sqlUpdate.setString(14, shippingCompany);
		sqlUpdate.setString(15, shippingAcct);
		sqlUpdate.setDate(16, updateDate);
		sqlUpdate.setTime(17, updateTime);
		sqlUpdate.setString(18, updateUser);
		sqlUpdate.setString(19, company);
// Where section of SQL statement
		sqlUpdate.setInt(20, custNumber.intValue());
		
		sqlUpdate.executeUpdate();
		
	} 
	catch (SQLException e) 
	{
		System.out.println("Sql error at " +
					"com.treetop.data.PathFile.update(): " + 
					e);
	}		
}
/**
 * Send in all the fields for the record to be added to the file.
 *
 * Creation date: (6/11/2003 1:48:29 PM)
 */
public static void updateSampleRequestCustomer(							
										int custNumber,
										String name,
										String address1,
										String address2,
						 			    String city,
										String state,
										int zip,
										int zipExtention,
										String foreignPostalCode,
										String foreignCountry,
										String contact,
										String contactPhone,
										String contactEmail,
										String shippingCompany,
										String shippingAcct,
										java.sql.Date updateDate,
										java.sql.Time updateTime,
										String updateUser,
										String company)
throws InvalidLengthException, Exception
{
	SampleRequestCustomer newRecord = new SampleRequestCustomer();
	
	newRecord.setCustNumber(custNumber);
	newRecord.setName(name);
	newRecord.setAddress1(address1);
	newRecord.setAddress2(address2);
	newRecord.setCity(city);
	newRecord.setState(state);
	newRecord.setZip(zip);
	newRecord.setZipExtention(zipExtention);
	newRecord.setForeignPostalCode(foreignPostalCode);
	newRecord.setForeignCountry(foreignCountry);
	newRecord.setContact(contact);
	newRecord.setContactPhone(contactPhone);
	newRecord.setContactEmail(contactEmail);
	newRecord.setShippingCompany(shippingCompany);
	newRecord.setShippingAcct(shippingAcct);
	newRecord.setUpdateDate(updateDate);
	newRecord.setUpdateTime(updateTime);
	newRecord.setUpdateUser(updateUser);
	newRecord.setCompany(company);
	
	newRecord.update();

}
/**
 * Send in the Class for the record to be Updated in the file.
 *
 * Creation date: (7/17/2003 10:28:29 AM)
 */
public static void updateSampleRequestCustomer(	
										SampleRequestCustomer newRecord)
throws InvalidLengthException, Exception
{
	newRecord.update();
}

/**
 * Call this method will return a vector which will include Sample Request Customers.
 *   Send in a bunch of parameters to define the SQL statement.
 *
 * Creation date: (6/30/2003 11:08:29 AM)
 */
public Vector findCustomer(Integer fromCustNumber,
						   Integer toCustNumber,
						   String name,
						   String city,
						   String state,
						   String country,
						   String contactName,
						   String orderBy) 
{	
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	//String library = "DBLIB."; // live environment
	String library = "DBPRD."; // Live M3 Environment

    Vector returnCustomers = new Vector();	
	
	String SQLRun = "SELECT * " +
                    "FROM " + library + "SRPACUST ";
    String SQLDetail = "";
    
    String SQLWhere = "";
    
	int fromC = fromCustNumber.intValue();
	int toC   = toCustNumber.intValue();
	if (fromC != 0 || toC != 999999999)
	{
		if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    SQLWhere = SQLWhere + "SRANUMBER BETWEEN " +
	                fromCustNumber + " and " + toCustNumber +
	                " ";
	}
    
    if (name != null && name.trim().length() != 0)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

    	name = name.toUpperCase();
    	name = "%" + name + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRANAME) LIKE \'" + name + "\' ";
    }

    if (city != null && city.trim().length() != 0)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

    	city = city.toUpperCase();
    	city = "%" + city + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRACITY) LIKE \'" + city + "\' ";
    }

    if (state != null && state.trim().length() != 0 && !state.equals("None"))
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

        SQLWhere = SQLWhere +
                  "SRASTATE = \'" + state + "\' ";
    }

    if (country != null && country.trim().length() != 0)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

    	country = country.toUpperCase();
    	country = "%" + country + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRAFGNCNTY) LIKE \'" + country + "\' ";
    }

    if (contactName != null && contactName.trim().length() != 0)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

    	contactName = contactName.toUpperCase();
    	contactName = "%" + contactName + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRACONTACT) LIKE \'" + contactName + "\' ";
    }
	//***********************************************//
	//***  Information for Order of Results   **********//
	//     Order by can be
	//        NumberA      = Customer Number Ascending
	//        NumberD      = Customer Number Descending
	//        NameA        = Customer Name Ascending (Default if Blank)
	//        NameD        = Customer Name Descending
	//        CityA        = City Ascending
	//        CityD        = City Descending
	//        StateA       = State Ascending
	//        StateD       = State Descending
	//        ContactA     = Customer Contact Name Ascending
	//        ContactD     = Customer Contact Name Descending
	//***
	String SQLOrder = "ORDER BY UPPER(SRANAME)"; // Default NameA
	if (orderBy == null)
	   orderBy = "";
	if (orderBy.equals("NumberA"))
	   SQLOrder = "ORDER BY SRANUMBER";
	if (orderBy.equals("NumberD"))
	   SQLOrder = "ORDER BY SRANUMBER DESC";
	if (orderBy.equals("NameD"))
	   SQLOrder = "ORDER BY UPPER(SRANAME) DESC";
	if (orderBy.equals("CityA"))
	   SQLOrder = "ORDER BY SRACITY, SRASTATE, UPPER(SRANAME)";
	if (orderBy.equals("CityD"))
	   SQLOrder = "ORDER BY SRACITY DESC, SRASTATE DESC, UPPER(SRANAME) DESC";
	if (orderBy.equals("StateA"))
	   SQLOrder = "ORDER BY SRASTATE, UPPER(SRANAME)";
	if (orderBy.equals("StateD"))
	   SQLOrder = "ORDER BY SRASTATE DESC, UPPER(SRANAME) DESC";
	if (orderBy.equals("ContactA"))
	   SQLOrder = "ORDER BY SRACONTACT, UPPER(SRANAME)";
	if (orderBy.equals("ContactD"))
	   SQLOrder = "ORDER BY SRACONTACT DESC, UPPER(SRANAME) DESC";
	if (orderBy.equals("CountryA"))
	   SQLOrder = "ORDER BY SRAFGNCNTY, UPPER(SRANAME)";
	if (orderBy.equals("CountryD"))
	   SQLOrder = "ORDER BY SRAFGNCNTY DESC, UPPER(SRANAME) DESC";

	//***********************************************//
	//***  Put SQL Statement Together      **********//
    SQLRun = SQLRun + SQLDetail + SQLWhere + SQLOrder;
	try
	{		 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun);
		 
		try
		{
 		   	while (rs.next())
   		    {
	   		    SampleRequestCustomer buildVector = new SampleRequestCustomer(rs);
  	    		returnCustomers.addElement(buildVector);
		    }
 	       rs.close();
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set (SampleRequestCustomer.findCustomer)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL (SampleRequestCustomer.findCustomer) " + e);
	}         
	
	return returnCustomers;
	
}
/**
 * Take a result set, (from an SQL Query) and
 *    set the information into the fields within
 *    this class.
 *    Loading this field with classes from the OCUSMA File.
 *
 * Creation date: (8/4/2008 TWALTO)
 *    Replace the loadInfinium Fields
 */
protected void loadM3Fields(ResultSet rs) 
{
	try 
	{
		custNumber		  = new Integer(rs.getInt("OKCUNO"));
	    name      	      =             rs.getString("OKCUNM").trim();
	    address1 	      =             rs.getString("OKCUA1").trim();
	    address2	      =             rs.getString("OKCUA2").trim();
	    city     	      = "";
	    state			  = "";
		zip               = new Integer ("0");
		zipExtention      = new Integer ("0");
	    
//	    foreignCountry    =             rs.getString("CUCTRY").trim();
//	    if (foreignCountry.length() == 0 || foreignCountry.equals("USA"))
//	    {
//		    if (rs.getString("CUPOST").length() != 0)
//		    {
//			    int zipLength = rs.getString("CUPOST").trim().length();
//			    String whatZip = rs.getString("CUPOST");
//			    if (zipLength == 5)
//			    	zip           = new Integer (rs.getString("CUPOST").trim());
//			    if (zipLength == 10)
//			    {
//			    	zip           = new Integer (rs.getString("CUPOST").substring(0,5));
//			    	zipExtention  = new Integer (rs.getString("CUPOST").substring(6,zipLength));
//			    }
//			}
//		    foreignPostalCode     = "";
			
//	    }
//		else
//	    {
//			foreignPostalCode     = rs.getString("CUPOST").trim();

//	    }
//		company           =         rs.getString("CUCO").trim();
		company           =         "";
		foreignCountry    =         "";
		foreignPostalCode =         "";
	    contactPhone	  =         rs.getString("OKPHNO").trim();

	    contact 		  =         "";
	    contactEmail 	  =         "";
	    shippingCompany   =         "";
	    shippingAcct 	  =         "";
  		updateDate        = java.sql.Date.valueOf("1900-01-01");
	    updateTime        = java.sql.Time.valueOf("00:00:00");
	    updateUser 		  = "";
		updateUserLong    = "";
	}
	catch (Exception e)
	{
		System.out.println("SQL Exception at com.treetop.data.SampleRequestCustomer.loadM3Fields();" + e);
	}

	persists = true;
				
}
}
