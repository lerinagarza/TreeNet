package com.treetop;

import java.sql.*;
import java.util.*;
import java.math.*;
import javax.sql.*;
import com.treetop.*;
import com.treetop.utilities.ConnectionStack;
/**
 * Insert the type's description here.
 * Creation date: (07/28/2004 1:23:45 PM)
 * @author: William T Haile
 * 	Change: 12/24/08 TWalto - point to new machine using ConnectionStack
 *
 * Access to EmailLog and Emailkey Information Tables. 
 *                                     (DBLIB/EMAILLOG)
 *                                     (DBLIB/EMAILKEY)
 */
public class EmailKey extends Email{


	// Data base fields.	
	protected String	emailKey1;			//EMLKEY1
	protected String    emailKey2;			//EMLKEY2
	

    // Class static fields.
	private static boolean keyPersists = false;
	private static Stack findByUniqueNumber = null;
	private static Stack findByKey1AndKey2 = null;
	private static Stack insertStack = null;
	private static Stack deleteByUniqueNumber = null;
	
	
	
/**
 * EmailKey constructor comment.
 */
public EmailKey() {
	super();
	super.init();
	init();
}
/**
 * EMailKey constructor comment.
 */
public EmailKey(Integer uniqueNumberIn,
				String emailKey1In, 
				String emailKey2In)
	throws InstantiationException 
{
	super();
	super.init();
	init();
	
	String errorMessage = "";

	try
	{
		PreparedStatement findIt = (PreparedStatement) 
									findByUniqueNumber.pop();
		ResultSet rs = null;

		try
		{
			findIt.setInt(1, uniqueNumberIn.intValue());
			findIt.setString(2, emailKey1In);
			findIt.setString(3, emailKey2In);
			rs = findIt.executeQuery();									
		}
		catch(Exception e)
		{			
			errorMessage = "Exception error creating a result set.  " +
				           "Instantiation error at - com.treetop." +
					       "EmailKey(unq#:(" + uniqueNumberIn  + 
					       ", key1:" + emailKey1In + 
						   ", key2:" + emailKey2In + ")";
		}
		
		findByUniqueNumber.push(findIt);

		try
		{
			if (rs.next() == false)
			{		 
				errorMessage = "Exception error: unable to obtain EmailKey record. " +
				               "Instantiation error at - com.treetop." +
					           "EmailKey(unq#:(" + uniqueNumberIn  +
							   ", key1:" + emailKey1In + 
							   ", key2:" + emailKey2In + ")";
			}
			else
			// use the resultset to load the class fields.
			this.loadFields(rs);
		} 
		catch (Exception e) 
		{
			errorMessage = "Exception error processing a result set. " +
						   "Instantiation error at - " +
						   "com.treetop.EmailKey(ket1:" + emailKey1In +
					  	   ", key1:" + emailKey1In + 
						   ", key2:" + emailKey2In + ")"; 
		}
		
		rs.close();				
	}
	catch(Exception e)
	{
		errorMessage = "Instantiation error found " +
					   "com.treetop.EmailKey(key1:" + emailKey1In +
					   ", key1:" + emailKey1In + 
					   ", key2:" + emailKey2In + ")";
					    				
	}
	if (!errorMessage.equals(""))
	   throw new InstantiationException(errorMessage);

	return;
}

/**
 * Write a record to EmailKey file.
 * Creation date: (12/01/2003 8:24:29 AM)
 */
public void addKey() throws Exception {

	PreparedStatement addIt = null;
	String throwError = "";

	// Add a EMAILKEY record.
	try {
		addIt = (PreparedStatement) insertStack.pop();
		addIt.setInt(1, auditUniqueKey.intValue());
		addIt.setString(2, emailKey1);
		addIt.setString(3, emailKey2);
		
		addIt.executeUpdate();
		

	} catch (Exception e) {
		throwError = "error at com.treetop.EmailKey." +
			         "addKey(unq#:(" + auditUniqueKey +
			         ") key1:(" + emailKey1 + 
			         ") key2:(" + emailKey2 + ")) - " + e;
	}
	insertStack.push(addIt);
	
	if (!throwError.equals(""))
		 throw new Exception(throwError);

	return;	
}
/**
 * This method will recieve in two Email Keys (Strings)
 * and return all instances of the EmailKey class in a vector.
 *  
 * Creation date: (07/29/2004 1:07:44 PM)
 */
public static Vector findEmailsByKeys(String key1In,
									  String key2In) 
{
	
	Vector emailList = new Vector();

	// Ensure this class has been compilied on the server prior
	// to using static fields within this class.
	if (findByKey1AndKey2 == null)
	{
		EmailKey justInitialize = new EmailKey();
	}

	try
	{
		PreparedStatement findThem = (PreparedStatement) 
									  findByKey1AndKey2.pop();
		ResultSet rs = null;
		
		try 
		{
			findThem.setString(1, key1In);
			findThem.setString(2, key2In);
			rs = findThem.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println("Exception error creating a result set " +
							   "(com.treetop.EmailKey.findEmailsByKeys(" +
							   key1In + "," + key2In + ") " + e);
		}
		
		findByKey1AndKey2.push(findThem);

		try 
		{
			while (rs.next())
			{
				EmailKey oneElement = new EmailKey();
				oneElement.loadFields(rs);
				emailList.addElement(oneElement);
			}
		}
		catch (Exception e){
			System.out.println("Error processing a result set to build a vector:" +
				               "com.treetop.EmailKey." +
				               ":findEmailsByKeys(" + key1In + "," + 
							   key2In + ") " + e);		                
		}
		rs.close();
	}
	catch (Exception e) 
	{
		System.out.println("Error: com.treetop.EmailKey." +
			               "findEmailsByKeys(" + key1In + "," +
						   key2In + ") " + e);	
	}
			
	return emailList; 
}

/**
 * Insert the method's description here.
 * Creation date: (07/29/2004 11:40:10 AM)
 */
public String getEmailKey1() 
{
	return emailKey1;	
}
/**
 * Insert the method's description here.
 * Creation date: (07/29/2004 11:40:10 AM)
 */
public String getEmailKey2() 
{
	return emailKey2;	
}
/**
 * Insert the method's description here.
 * Creation date: (07/28/2004 11:40:10 AM)
 */
public void init() {
	// Test for initial connection.
	
	if (keyPersists == false) 
	{
	    keyPersists = true;
		
	    Connection conn1  = null;
		Connection conn2  = null;
		Connection conn3  = null;
		Connection conn4  = null;
		Connection conn5  = null;
	    
		try {
			// Retrieve five connections from the connection pool.
			conn1 = ConnectionStack.getConnection();
			conn2 = ConnectionStack.getConnection();
			conn3 = ConnectionStack.getConnection();
			conn4 = ConnectionStack.getConnection();
			conn5 = ConnectionStack.getConnection();

			// Create and stack multiple prepared statements.
			
			// Find By Key1 and Key2.
			String fbKey1Key2 =
				"SELECT * " +
				" FROM " + library + "EMAILLOG " +
				" INNER JOIN  " + library + "EMAILKEY " +
				" ON EMLUNIQUE = EMLLOGKEY " +
				" WHERE EMLKEY1 = ? AND EMLKEY2 = ?";

			PreparedStatement fbKey1Key21 = conn1.prepareStatement(fbKey1Key2);
			PreparedStatement fbKey1Key22 = conn1.prepareStatement(fbKey1Key2);
			PreparedStatement fbKey1Key23 = conn1.prepareStatement(fbKey1Key2);
			PreparedStatement fbKey1Key24 = conn4.prepareStatement(fbKey1Key2);
			PreparedStatement fbKey1Key25 = conn5.prepareStatement(fbKey1Key2);

			findByKey1AndKey2 = new Stack();
			findByKey1AndKey2.push(fbKey1Key21);
			findByKey1AndKey2.push(fbKey1Key22);
			findByKey1AndKey2.push(fbKey1Key23);
			findByKey1AndKey2.push(fbKey1Key24);
			findByKey1AndKey2.push(fbKey1Key25);


			// Find By Unique Key.
			String fbUniqueKey =
				"SELECT * " +
				" FROM " + library + "EMAILLOG " +
				" INNER JOIN  " + library + "EMAILKEY " +
				" ON EMLUNIQUE = EMLLOGKEY " +
				" WHERE EMLUNIQUE = ? AND " +
				" EMLKEY1 = ? AND EMLKEY2 = ? ";

			PreparedStatement fbUniqueKey1 = conn1.prepareStatement(fbUniqueKey);
			PreparedStatement fbUniqueKey2 = conn1.prepareStatement(fbUniqueKey);
			PreparedStatement fbUniqueKey3 = conn1.prepareStatement(fbUniqueKey);
			PreparedStatement fbUniqueKey4 = conn4.prepareStatement(fbUniqueKey);
			PreparedStatement fbUniqueKey5 = conn5.prepareStatement(fbUniqueKey);

			findByUniqueNumber = new Stack();
			findByUniqueNumber.push(fbUniqueKey1);
			findByUniqueNumber.push(fbUniqueKey2);
			findByUniqueNumber.push(fbUniqueKey3);
			findByUniqueNumber.push(fbUniqueKey4);
			findByUniqueNumber.push(fbUniqueKey5);


			// Insert Email Key.
			String insertKey =
				"INSERT INTO " + library + "EMAILKEY " +
				" VALUES (?, ?, ? )";
			PreparedStatement insert1 = conn1.prepareStatement(insertKey);
			PreparedStatement insert2 = conn2.prepareStatement(insertKey);
			PreparedStatement insert3 = conn3.prepareStatement(insertKey);
			PreparedStatement insert4 = conn4.prepareStatement(insertKey);
			PreparedStatement insert5 = conn5.prepareStatement(insertKey);

			insertStack = new Stack();
			insertStack.push(insert1);
			insertStack.push(insert2);
			insertStack.push(insert3);
			insertStack.push(insert4);
			insertStack.push(insert5);


			// Delete By Unique Key.
			String deleteKey =		
				"DELETE FROM " + library + "EMAILKEY " +
				" WHERE EMLLOGKEY = ? ";

			PreparedStatement deleteKey1 = conn1.prepareStatement(deleteKey);
			PreparedStatement deleteKey2 = conn2.prepareStatement(deleteKey);
			PreparedStatement deleteKey3 = conn3.prepareStatement(deleteKey);
			PreparedStatement deleteKey4 = conn4.prepareStatement(deleteKey);
			PreparedStatement deleteKey5 = conn5.prepareStatement(deleteKey);
			
			deleteByUniqueNumber = new Stack();
			deleteByUniqueNumber.push(deleteKey1);
			deleteByUniqueNumber.push(deleteKey2);
			deleteByUniqueNumber.push(deleteKey3);
			deleteByUniqueNumber.push(deleteKey4);
			deleteByUniqueNumber.push(deleteKey5);

		} catch (Exception e) {
			System.out.println("error: An exception occured at com.treetop." +
							   "EmailKey.init()" +  e);	              
		}
		finally
		{
			ConnectionStack.returnConnection(conn1);
			ConnectionStack.returnConnection(conn2);
			ConnectionStack.returnConnection(conn3);
			ConnectionStack.returnConnection(conn4);
			ConnectionStack.returnConnection(conn5);
		}
	}
}
/**
 * Load the class fields from the incoming result set.
 * Creation date: (07/28/2004 11:40:10 AM)
 */

protected void loadFields(ResultSet rs) 
{
	super.loadFields(rs);

	try {
		emailKey1     = rs.getString("EMLKEY1");
		emailKey2     = rs.getString("EMLKEY2");
	}
	catch (Exception e)
	{
		System.out.println("error: Exception at com.treetop." +
			               "EmailKey.loadFields(ResultSet);" + e);
	}
}

/**
 * 	This method tests access to the files "EMAILLOG" and "EMAILKEY".
 *                                        
 * All class
 * fields should be tested and verified for access to and from the Enterprise 
 * database. A listing of records as they are accessed and updated should be 
 * generated. Also connections, prepared statements, and the loadFields method
 * are confirmed.
 * 
 * Creation date: (07/29/2004 11:40:10 AM)
 */

public static void main(String[] args) {

	// Create a audit log and key file entry.
	
	try {
	
		// Load the Email Key class.		
		String[] dates = com.treetop.SystemDate.getSystemDate();
		EmailKey email = new EmailKey();
		email.setAuditType("email");
		email.setAuditSystem("COA");
		email.setAuditFrom("abc@whocares.com");
		email.setAuditTo("someBodyWhoCares@icare.com");
		email.setAuditToType("cc");
		email.setAuditDate(java.sql.Date.valueOf(dates[7]));
		email.setAuditTime(java.sql.Time.valueOf(dates[8]));
		email.setAuditSubject("the subject of the email");
		email.emailKey1 = "keyOne1";
		email.emailKey2 = "keyTwo1";	

		// Add the class entry to database files.
		try {	
			email.add();
			email.addKey();	
			System.out.println("email: " + email);
		} catch (Exception e)
		{
			System.out.println("main failed");
		}
		
		
		// Test the EmailKey constructor.
		try {
			EmailKey newEmailKey = new EmailKey(email.getAuditUniqueKey(),
												email.getEmailKey1(), 
			                                    email.getEmailKey2());
			System.out.println("newEmailKey: " + newEmailKey);
			
			
			// add a couple more key entries
			email.setEmailKey1("keyOne2");
			email.setEmailKey1("keyTwo2");
			email.addKey();
			
			email.setEmailKey1("keyOne3");
			email.setEmailKey1("keyTwo3");
			email.addKey();

			// Find all Email Log entries forselected keys.
			Vector emailList = findEmailsByKeys("keyOne1",
												"keyTwo1");
			for (int x = 0; x < emailList.size(); x++)
			{
				EmailKey here = (EmailKey) emailList.elementAt(x);
				String justHangOn = here.getEmailKey1();
			}
						


			// Delete the entries created fo rthis test run.			
			newEmailKey.delete();
		} catch (Exception e)
		{ 
			System.out.println("main failed at add emailkey");
		}
		
			
	} catch (Exception e) {
		System.out.println("error: At com.treetop.EmailKey.main()");
	}


}
/**
 * Insert the method's description here.
 * Creation date: (12/4/2003 11:51:21 AM)
 */
public void newMethod() {}
/**
 * Insert the method's description here.
 * Creation date: (07/29/2004 11:40:10 AM)
 */
public void setEmailKey1(String emailKey1In) 
{
	this.emailKey1 = emailKey1In;	
}
/**
 * Insert the method's description here.
 * Creation date: (07/29/2004 11:40:10 AM)
 */
public void setEmailKey2(String emailKey2In) 
{
	this.emailKey2 = emailKey2In;	
}

/**
 * List class contents for test purposes.
 */
	public String toString() {

	return new String(
	"auditType: " + auditType + "\n" +
	"auditSystem: " + auditSystem + "\n" +
	"auditFrom: " + auditFrom + "\n" +
	"auditTo: " + auditTo + "\n" +
	"auditToType: " + auditToType +"\n" +
	"auditDate: " + auditDate +"\n" +
	"auditTime: " + auditTime +"\n" +
	"auditSubject: " + auditSubject +"\n" +
	"auditUnique: " + auditUniqueKey +"\n" + 
	"emailKey1: " + emailKey1 + "\n" +
	"emailKey2: " + emailKey2 +"\n"); 
	}

}
