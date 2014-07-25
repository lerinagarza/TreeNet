package com.treetop;

import java.text.*;
import java.sql.*;
import java.math.*;
import java.util.*;
import javax.sql.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import com.treetop.businessobjects.DateTime;
import com.treetop.data.*;
import com.treetop.utilities.ConnectionStack;
import com.treetop.utilities.UtilityDateTime;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
/**
 * Insert the type's description here.
 * Creation date: (7/24/2003 8:45:43 AM)
 * @author: 
 */
public class Email 
{
	private String to;
	private String cc;
	private String bcc;
	private String from;
	private String subject;
	private String body;
	
	// Audit file entries DBLIB/(EMAILLOG EMAILLOG2).
	protected String auditType;				//EMLTYPE
	protected String auditSystem;			//EMLSYSTEM
	protected String auditFrom;				//EMLFROM
	protected String auditTo;				//EMLTO
	protected String auditToType;           //EMLTOTYPE
	protected java.sql.Date auditDate;		//EMLDATE
	protected java.sql.Time auditTime;		//EMLTIME
	protected String auditSubject;			//EMLSUBJECT
	protected Integer auditUniqueKey;		//EMLUNIQUE
		
		

	private static Stack findEmailByProfile = null;
	private static Stack findEmailByUniqueNumber = null;
	private static Stack addEmailEntry = null;
	private static Stack deleteEmailEntry = null;
	private static Stack deleteEmailKeyEntries = null;
	private static boolean persists = false;
	protected static String library = "DBPRD.";//01/02/09
	
/**
 * Email constructor comment.
 */
	public Email() {
		super();
		init();
	}
	
	
/**
 * Use this constructor to build a class by Unique Number.
 * 				
 */
	public Email(Integer auditUniqueKeyIn) 
	throws InstantiationException 
	{
		init();
		
		String error = "";
		PreparedStatement classByUnq = null;
		ResultSet rs = null;
		String popped = "no";

		// obtain a result set for incoming Unique Number.		
		try 
		{	
			classByUnq = (PreparedStatement) findEmailByUniqueNumber.pop();
			popped = "yes";							
			classByUnq.setInt(1, auditUniqueKeyIn.intValue());				
			rs = classByUnq.executeQuery();
			
		} catch (Exception e) {
						
			error = "Error creating a result set at " +
					 "com.treetop.Email.Email(unq#(" +
					 auditUniqueKeyIn + "): " + e;							   
		}
			
		if (popped.equals("yes"))	
			findEmailByUniqueNumber.push(classByUnq);

		// load class via result set.	
		try
		{
			if (rs.next())			
				loadFields(rs);
			else
				error = "Error reading the result set at " +
						"com.treetop.Email.Email(unq#(" +
						auditUniqueKeyIn + "): " ; 			
		}
		catch(Exception e)
		{
			error = "Error loading Email class at " +
					"com.treetop.Email.Email(unq#(" +
					 auditUniqueKeyIn + "): " + e;
		}
			
		try 
		{
			rs.close();
		} 
		catch (SQLException e) 
		{
			// close it.
		}
		
		if (!error.equals(""))
			throw new InstantiationException(error);
		return;
	}
	

/**
 * Add the current class values as a record to file 
 * EMAILLOG.
 * Creation date: (07/26/2004 8:24:29 AM)
 */
	public void add() throws Exception  {

		// Add a Audit Log record.
		String throwError = "";
		String popped = "no";
		PreparedStatement addIt = null;
		
		try {
			addIt = (PreparedStatement) addEmailEntry.pop();
			popped = "yes"; 
			//auditFrom = "twalton@treetop.com";
			addIt.setString(1,  auditType);
			addIt.setString(2,  auditSystem);
			addIt.setString(3,  auditFrom.trim());
			addIt.setString(4,  auditTo.trim());
			addIt.setString(5,  auditToType);
			addIt.setDate(6,  auditDate);
			addIt.setTime(7,  auditTime);
			addIt.setString(8,  auditSubject);
			int nextNumber = 0;

			// Fetch a unique number for entries unless 
			// a unique number already exists.			
			//if (auditUniqueKey == null)
			//{
				nextNumber = nextUniqueNumber();
				auditUniqueKey = new Integer(nextNumber);
			//} else
				//nextNumber = auditUniqueKey.intValue(); 
				
			addIt.setInt(9, auditUniqueKey.intValue());
			
			if (nextNumber == 0)
			{
				if (popped.equals("yes"))
				{
					addEmailEntry.push(addIt);
					popped = "no";
				}
				throwError = "Unable to add the email " +
							 "to the Audit Log file. " +
							 "The Unique Key Number could " +
							 "not be obtained. com.treetop." +
							 "Email.add()";
			} else
			{ 		
				addIt.executeUpdate();				
			}	

		} catch (Exception e) {
			if (popped.equals("yes"))
			{
				addEmailEntry.push(addIt);
				popped = "no";
			}
			throwError = "Unable to add the email " +
						 "to the Audit Log file. " +
						 "The Unique Key Number could " +
						 "not be obtained. com.treetop." +
						 "Email.add() " + e;
		}
		if (popped.equals("yes"))
		{
			addEmailEntry.push(addIt);
			popped = "no";
		}
		if (!throwError.equals(""))
			throw new Exception(throwError);	
	}
	
/**
 * Add email Audit Log Entries. 
 * EMAILLOG.
 * Creation date: (07/30/2004 8:24:29 AM)
 */
	public void addEmailEntries(MimeMessage message, 
								String system,
								String[] key1,
								String[] key2,
								String fromAddr) {
		
		try {
			// Set up a Email Key class for audit additions.
			EmailKey emailLog = new EmailKey();
			
			// audit type.
			emailLog.setAuditType("email");
			
			// audit system.
			emailLog.setAuditSystem(system);
			
			// audit Date and Time.
			//String[] dates = com.treetop.SystemDate.getSystemDate();
			DateTime dt = UtilityDateTime.getSystemDate();
			emailLog.setAuditDate(java.sql.Date.valueOf(dt.getDateFormatyyyyMMddDash()));
			emailLog.setAuditTime(java.sql.Time.valueOf(dt.getTimeFormathhmmssColon()));
			
			// audit Subject.
			emailLog.setAuditSubject(message.getSubject());
			
			// from email address.
			emailLog.setAuditFrom(fromAddr);

				 
			Address[] toTO = message.getRecipients(Message.RecipientType.TO);			
			for(int y = 0; y < toTO.length; y++)
			{
				emailLog.setAuditToType("");
				auditTo = toTO[y].toString();
				emailLog.setAuditTo(auditTo);
				emailLog.add();
					
				// Load key entries for each recipient.
				for (int x = 0; x < key1.length; x++)
				{
					emailLog.setEmailKey1(key1[x]);
					emailLog.setEmailKey2(key2[x]);
					emailLog.addKey();
				}
			}
			
			Address[] toCC = message.getRecipients(Message.RecipientType.CC);
			if (toCC != null)
			{				
				for(int y = 0; y < toCC.length; y++)
				{
					emailLog.setAuditToType("cc");
					auditTo = toCC[y].toString();
					emailLog.setAuditTo(auditTo);
					emailLog.add();
					
					// Load key entries for each recipient.
					for (int x = 0; x < key1.length; x++)
					{
						emailLog.setEmailKey1(key1[x]);
						emailLog.setEmailKey2(key2[x]);
						emailLog.addKey();															
					}								
				}
			}
							
			Address[] toBCC = message.getRecipients(Message.RecipientType.BCC);
			if (toBCC != null)
			{			
				for(int y = 0; y < toBCC.length; y++)
				{
					emailLog.setAuditToType("bcc");
					auditTo = toBCC[y].toString();
					emailLog.setAuditTo(auditTo);
					emailLog.add();
					
					// Load key entries for each recipient.
					for (int x = 0; x < key1.length; x++)
					{
						emailLog.setEmailKey1(key1[x]);
						emailLog.setEmailKey2(key2[x]);
						emailLog.addKey();
					}								
				}
			}			
			
		} catch (Exception e) {
			System.out.println("error @ com.treetop.Email." +
			                   "addEmailEntries(MimeMessage, String) - "
			                   + e);
		}
	}
	
	
/**
 * Verify the from email parameter is valid.
 * Creation date: (07/30/2004 8:24:29 AM)
 * 
 * return String array [0] = error message
 *                     [1] = prorper from value to be used.
 */
	public String[] checkFrom(String testFrom) {
		
		String returnMessage = "";
    	
		//Get the From   	
		if (testFrom != null && testFrom.trim().length() != 0)			
		{
			int findAt 		= testFrom.lastIndexOf("@");
	
			if (findAt < 0)
			{
				testFrom = findAddress(testFrom);
					
				if (testFrom.length() == 0)
				{
					returnMessage = "The From Email Address for this profile " +
						"(" + from + ") Could Not Be Found.  An Email has been sent " +
						"to the helpdesk.  Please contact the helpdesk to set up this " +
						"profile to use email.";
				}	
			}
		}
		else
		{
			returnMessage = "This Email does not have a from address. " +
				"To send an Email you need to have a from Address.";
			
		}
	String[] returnArray = new String[2];
	returnArray[0] = returnMessage;
	returnArray[1] = testFrom;
	return returnArray;   
	}
       
/**
 * Verify the from email parameter is valid.
 * Creation date: (07/30/2004 8:24:29 AM)
 * 
 * return String array [0] = error message
 *                     [1] = prorper from value to be used.
 */
	public String checkKeyFields(String from,
								 String subject,
								 String[] keyField1, 
								 String[] keyField2) {
		
		String returnMessage = "";
			
	   	// keys must not be null.	
		if (keyField1 == null )
		{
			returnMessage = "A incomming keyfield parameter " +
							" is null for this email request. " +
							"from:" + from + "/" +
							"subject:" + subject + "/" +
							"key1:" + keyField1 + "/" +
							"key2:" + keyField2 + ". ";
		}
	   	
	   	// keys must not be null.	
		if (keyField2 == null && returnMessage.equals(""))
		{
			returnMessage = "A incomming keyfield parameter " +
							" is null for this email request. " +
							"from:" + from + "/" +
							"subject:" + subject + "/" +
							"key1:" + keyField1 + "/" +
							"key2:" + keyField2 + ". ";
		}		
    
    	// two key entries must exist at a single time.
		if (keyField1.length != keyField2.length
		    &&  returnMessage.equals(""))
		{
			returnMessage = "keyField size counts are not " +
							"equal for this email request " +
							"from:" + from + "/" +
							"subject:" + subject + "/" +
							"key1:" + keyField1 + "/" +
							"key2:" + keyField2 + ". ";	
		}
		
		// keyField values must not be null of empty.
		
		
		
		return returnMessage;
	}
	
/**
 * Insert the method's description here.
 * Creation date: (07/26/2004 8:24:29 AM)
 */
	public void delete() throws Exception {

		// Delete a Audit Log record.
		PreparedStatement deleteIt = null;
		PreparedStatement deleteIt2 = null;
		String popped = "no";
		String throwError = ""; 
		try {
			deleteIt = (PreparedStatement) deleteEmailEntry.pop();
			deleteIt2 = (PreparedStatement) deleteEmailKeyEntries.pop();
			popped = "yes"; 
			deleteIt.setInt(1, auditUniqueKey.intValue());
			deleteIt2.setInt(1, auditUniqueKey.intValue());				
			deleteIt.executeUpdate();
			deleteIt2.executeUpdate();
		} catch (Exception e) {
			if (popped.equals("yes"))
			{
				popped = "no";
				deleteEmailEntry.push(deleteIt);
				deleteEmailKeyEntries.push(deleteIt2);
			}
			throwError = "error at com.treetop.Email." +
					   "delete():" + e;
		}
		if (popped.equals("yes"))
		{
			popped = "no";
			deleteEmailEntry.push(deleteIt);
			deleteEmailKeyEntries.push(deleteIt2);
		}
		if (!throwError.equals(""))
			throw new Exception(throwError);	
	}


/**
 * This method will be used to get the email address,
 *  from the DPPNUSER File based on Profile.
 *      Changed to point to the TreeNet User File 12/24/08 TWalton
 * Creation date: (7/24/2003 9:05:32 AM)
 */
	private String findAddress(String userID) 
	{
	String emailAddress = "";
	PreparedStatement findIt = null; 
	ResultSet rs = null;
	
	try 
	{
		userID = userID.toUpperCase();
		findIt = (PreparedStatement) findEmailByProfile.pop();
		findIt.setString(1, userID);
		rs = findIt.executeQuery();
		
		if (rs.next())
		{
			//emailAddress = rs.getString("EMHEMAIL").trim().toLowerCase();
			emailAddress = rs.getString("DPNEMAIL");
		}
		rs.close();
	} 
	catch (SQLException e) 
	{
		System.out.println("SQL error at " +
						   "com.treetop.data.SampleRequestCustomer.SampleRequestCustomer(Integer) " + 
						   e);
	}
	findEmailByProfile.push(findIt);
	return emailAddress;	
}

/**
 *
 * Creation date: (3/1/2004 2:40:51 PM)
 */
public String getBCC()
{
	return bcc.trim();		
}
/**
 *
 * Creation date: (3/1/2004 2:40:51 PM)
 */
public String getBody()
{
	return body.trim();		
}
/**
 *
 * Creation date: (3/1/2004 2:40:51 PM)
 */
public String getCC()
{
	return cc.trim();		
}
/**
 *
 * Creation date: (3/1/2004 2:40:51 PM)
 */
public String getFrom()
{
	return from.trim();		
}
/**
 *
 * Creation date: (3/1/2004 2:40:51 PM)
 */
public String getSubject()
{
	return subject.trim();		
}
/**
 *
 * Creation date: (3/1/2004 2:40:51 PM)
 */
public String getTo()
{
	return to.trim();		
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void init() 
{
	
	// Test for initial connection.
	
	if (persists == false) 
	{
	    persists = true;
	    
	    Connection conn1 = null;   	
		Connection conn2 = null; 	   	
		Connection conn3 = null; 	   	
		Connection conn4 = null; 	   	
		Connection conn5 = null; 	   	
	    
	    
		try 
		{
				
			// Retrieve five connections from the connection pool.
			conn1 = ConnectionStack.getConnection();
			conn2 = ConnectionStack.getConnection();
			conn3 = ConnectionStack.getConnection();
			conn4 = ConnectionStack.getConnection();
			conn5 = ConnectionStack.getConnection();

			// Find the email address of the user.
			String findEmail = 									
			//	"SELECT * FROM DBLIB.EMPHIDTL " +
			//	" WHERE UPPER(EMHUSERID) = ?";
			// Change to point to mail TreeNet File
				"SELECT * FROM TREENET.DPPNUSER " +
				" WHERE UPPER(DPNUSER) = ?";

			PreparedStatement findEmailByProfile1 = conn1.prepareStatement(findEmail);			   
			PreparedStatement findEmailByProfile2 = conn2.prepareStatement(findEmail);			   		   
			PreparedStatement findEmailByProfile3 = conn3.prepareStatement(findEmail);			   		   
			PreparedStatement findEmailByProfile4 = conn4.prepareStatement(findEmail);			   
			PreparedStatement findEmailByProfile5 = conn5.prepareStatement(findEmail);	
			   	   		   		
			findEmailByProfile = new Stack();
			findEmailByProfile.push(findEmailByProfile1);		   		
			findEmailByProfile.push(findEmailByProfile2);
			findEmailByProfile.push(findEmailByProfile3);
			findEmailByProfile.push(findEmailByProfile4);
			findEmailByProfile.push(findEmailByProfile5);
											   		


			// Find the Email Log entry by unique number.
			String findLog = 									
				"SELECT * FROM " + library + "EMAILLOG " +
				" WHERE EMLUNIQUE = ?";

			PreparedStatement findLog1 = conn1.prepareStatement(findLog);			   
			PreparedStatement findLog2 = conn2.prepareStatement(findLog);			   		   
			PreparedStatement findLog3 = conn3.prepareStatement(findLog);			   		   
			PreparedStatement findLog4 = conn4.prepareStatement(findLog);			   
			PreparedStatement findLog5 = conn5.prepareStatement(findLog);	
			   	   		   		
			findEmailByUniqueNumber = new Stack();
			findEmailByUniqueNumber.push(findLog1);		   		
			findEmailByUniqueNumber.push(findLog2);
			findEmailByUniqueNumber.push(findLog3);
			findEmailByUniqueNumber.push(findLog4);
			findEmailByUniqueNumber.push(findLog5);
											   		
				
				
			// Insert Audit Log record.
			String insertIt =
				"INSERT INTO " + library + "EMAILLOG " +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? )";
					
			PreparedStatement insertIt1 = conn1.prepareStatement(insertIt);
			PreparedStatement insertIt2 = conn1.prepareStatement(insertIt);
			PreparedStatement insertIt3 = conn1.prepareStatement(insertIt);
			PreparedStatement insertIt4 = conn1.prepareStatement(insertIt);
			PreparedStatement insertIt5 = conn1.prepareStatement(insertIt);
				
			addEmailEntry = new Stack();
			addEmailEntry.push(insertIt1);
			addEmailEntry.push(insertIt2);
			addEmailEntry.push(insertIt3);
			addEmailEntry.push(insertIt4);
			addEmailEntry.push(insertIt5);
	
			
			// Delete Email Audit entry by Key Field.			
			String deleteIt =		
			"DELETE FROM " + library + "EMAILLOG " +
			" WHERE EMLUNIQUE = ? ";
	
			PreparedStatement deleteIt1 = conn1.prepareStatement(deleteIt);
			PreparedStatement deleteIt2 = conn1.prepareStatement(deleteIt);
			PreparedStatement deleteIt3 = conn1.prepareStatement(deleteIt);
			PreparedStatement deleteIt4 = conn1.prepareStatement(deleteIt);
			PreparedStatement deleteIt5 = conn1.prepareStatement(deleteIt);
				
			deleteEmailEntry = new Stack();
			deleteEmailEntry.push(deleteIt1);
			deleteEmailEntry.push(deleteIt2);
			deleteEmailEntry.push(deleteIt3);
			deleteEmailEntry.push(deleteIt4);
			deleteEmailEntry.push(deleteIt5);
				
			
			// Delete Email Key entries by Key Field.			
			deleteIt =		
			"DELETE FROM " + library + "EMAILKEY " +
			" WHERE EMLLOGKEY = ? ";
	
			deleteIt1 = conn1.prepareStatement(deleteIt);
			deleteIt2 = conn1.prepareStatement(deleteIt);
			deleteIt3 = conn1.prepareStatement(deleteIt);
			deleteIt4 = conn1.prepareStatement(deleteIt);
			deleteIt5 = conn1.prepareStatement(deleteIt);
				
			deleteEmailKeyEntries = new Stack();
			deleteEmailKeyEntries.push(deleteIt1);
			deleteEmailKeyEntries.push(deleteIt2);
			deleteEmailKeyEntries.push(deleteIt3);
			deleteEmailKeyEntries.push(deleteIt4);
			deleteEmailKeyEntries.push(deleteIt5);
				
		}
		catch (Exception e)
		{
			System.out.println("Error at com.treetop.Email.init() " +
							   "Problem when setting up Prepared Statements " + e);
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
 * Take a result set, (from an SQL Query) and
 *    set the information into the fields within
 *    this class.
 * Creation date: (5/11/2004 1:36:19 PM)
 */
protected void loadFields(ResultSet rs) 
{
	try 
	{ 
		auditType			= rs.getString("EMLTYPE");
		auditSystem        	= rs.getString("EMLSYSTEM");
		auditFrom		  	= rs.getString("EMLFROM");
		auditTo    		  	= rs.getString("EMLTO");
		auditToType		  	= rs.getString("EMLTOTYPE");
		auditDate			= rs.getDate("EMLDATE");
		auditTime	  		= rs.getTime("EMLTIME");
		auditSubject		= rs.getString("EMLSUBJECT");
		auditUniqueKey		= new Integer(rs.getInt("EMLUNIQUE"));		    		
	}
	catch (Exception e)
	{
		System.out.println("SQL Exception at com.treetop.data.UCCNet.loadFields();" + e);
	}
					
}



/**
 * Obtain a unique number to for this email.
 * Creation date: (7/28/2004 10:36:39 AM)
 */
private int nextUniqueNumber() 
{

	AS400 as400 = null;
	
	try {
		// create a AS400 object
		as400 = ConnectionStack.getAS400Object();
		//AS400 as400 = new AS400("10.6.100.1", "DAUSER", "web230502");
		ProgramCall pgm = new ProgramCall(as400);

		ProgramParameter[] parmList = new ProgramParameter[1];
		parmList[0] = new ProgramParameter(100);
		pgm = new ProgramCall(as400, "/QSYS.LIB/MOVEX.LIB/CLEMAILNBR.PGM", parmList);

		if (pgm.run() != true)
		{
			return 0;
		} else {
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
			try {
				as400.disconnectService(AS400.COMMAND);
			} catch (Exception e) {
				
			}
			ConnectionStack.returnAS400Object(as400);
	}
	

}

/**
 * Insert the method's description here.
 * Creation date: (7/28/2004 8:24:29 AM)
 */
public static void main(String[] args) {

	// Test retrieval of user email address.
	Email email = new Email();
	String userEmail = email.findAddress("THAILE");
	System.out.println("userEmail:" + userEmail);
	
	// Load the Email Audit Log class entry.		
	String[] dates = com.treetop.SystemDate.getSystemDate();
	email.setAuditType("email");
	email.setAuditSystem("COA");
	email.setAuditFrom("abc@whocares.com");
	email.setAuditTo("someBodyWhoCares@icare.com");
	email.setAuditToType("cc");
	email.setAuditDate(java.sql.Date.valueOf(dates[7]));
	email.setAuditTime(java.sql.Time.valueOf(dates[8]));
	email.setAuditSubject("the subject of the email");	

	try {	
		email.add();	
		System.out.println("email: " + email);
	} catch (Exception e)
	{
		System.out.println("main failed");
	}
	
	// Test the class constructor.
	try {
		Email emailNew = new Email(email.getAuditUniqueKey());
		email.delete();
	} catch(Exception e)
	{
		System.out.println("constructor FAILED" + e);
	}


	// Use the jsp in WebContent/JSP/Email.emailMain.jsp
	//
	// Use a jsp to test this class because server access is 
	// required.
	
}

/**
 * This will take in a number of to, cc, and bcc's.
 *   Send an email from the same person to each one.
 * Creation date: (7/24/2003 8:46:32 AM)
 */
	public String sendEmail(String[] to,
						String[] cc,
						String[] bcc,
						String from,
						String subject,
						String body) 
	{
		String defaultAddress = "helpdesk@treetop.com";
		//String defaultAddress = "thaile@treetop.com";
		
		String returnMessage = "";
		try
		{
			// Setting up the basics for sending an email message.
			Properties props = new Properties();
    		props.put("mail.smtp.host", "is-cas.private.treetop.com");
    		Session mailSession = Session.getInstance(props,null);
    		MimeMessage message = new MimeMessage(mailSession);
    		InternetAddress addressFrom = new InternetAddress(defaultAddress);
			InternetAddress addressTo = new InternetAddress(defaultAddress);
		
    	
    		//Get the From   	
			if (from != null && from.trim().length() != 0)			
			{
				String testFrom = from;
				int findAt 		= testFrom.lastIndexOf("@");
	
				if (findAt < 0)
				{
					testFrom = findAddress(testFrom);
					if (testFrom.length() == 0)
					{
						returnMessage = "The From Email Address for this profile " +
					    	"(" + from + ") Could Not Be Found.  An Email has been sent " +
					    	"to the helpdesk.  Please contact the helpdesk to set up this " +
					    	"profile to use email.";
   			   			message.setFrom(addressFrom);
 						addressTo = new InternetAddress(defaultAddress);
				    	message.addRecipient(Message.RecipientType.TO, addressTo);

			        	String newSubject  = "Error trying to Find Email Address: From: " + from;
			        	message.setSubject(newSubject);

			        	String msgText  = "Problem when sending this email : <br>" +
			     						  returnMessage + "<br>" +
			       						"Subject : " + subject + "<br>" +
			       						body;	
	 			    	message.setContent(msgText,"text/html");

				    	Transport.send(message);
				    	return returnMessage; // Leave Method
					}	
				}

				if (returnMessage.equals(""))
				{
					try
					{
						addressFrom = new InternetAddress(testFrom);
						message.setFrom(addressFrom);
					}
					catch(Exception e)
					{
						returnMessage = "The From Email Address " +
						    "(" + testFrom + ") is not a Valid Internet Email " +
						    "Address.";
					}
				}
			}
			else
			{
				returnMessage = "This Email does not have a from address. " +
		   						"To send an Email you need to have a from Address.";
				return returnMessage; // Leave Method 
			}
	   
       
			// Test the To's      
        	String[] errorProfileTo = new String[to.length];
        	int countErrors = 0;
        	String[] errorAddress = new String[to.length];
        	int countErrorAdd = 0;
        
        	int countGood = 0;
        
			for (int x = 0; x < to.length; x++)
			{
				if (to[x] != null && 
		   			to[x].trim().length() != 0)
				{
			    	String testTo = to[x];
					int findAt 		= testTo.lastIndexOf("@");

					if (findAt < 0)
					{
						testTo = findAddress(testTo);
						if (testTo.length() == 0)
						{
							errorProfileTo[countErrors] = to[x];
							countErrors = countErrors + 1;
						}
					}

					if (!testTo.equals(""))
					{
						try
						{
							addressTo = new InternetAddress(testTo);
							message.addRecipient(Message.RecipientType.TO, addressTo);
							countGood = countGood + 1;
						}
						catch(Exception e)
						{
							errorAddress[countErrorAdd] = to[x];
							countErrorAdd = countErrorAdd + 1;
						}
					}
		   		}
			  
			}
		
		
			//*** You have to have at least one good TO email.
			if (countGood == 0)
			{
				returnMessage = "The Email you tried to send does not have " +
			 	  				"any To Valid Addresses to send it to, please add/correct the " +
			 	  				"to addresses and retry.";
				return returnMessage; // Leave Method 
		    }


			// Get the CC's     
			for (int x = 0; x < cc.length; x++)
			{
		   		if (cc[x] != null && 
			   		cc[x].trim().length() != 0)
			   	{
				    String testTo = cc[x];
					int findAt 		= testTo.lastIndexOf("@");
	
					if (findAt < 0)
					{
						testTo = findAddress(testTo);
						
						if (testTo.length() == 0)
						{
							errorProfileTo[countErrors] = cc[x];
							countErrors = countErrors + 1;
						}
					}

					if (!testTo.equals(""))
					{
						try
						{
							addressTo = new InternetAddress(testTo);
							message.addRecipient(Message.RecipientType.CC, addressTo);
						}
						catch(Exception e)
						{
							errorAddress[countErrorAdd] = cc[x];
							countErrorAdd = countErrorAdd + 1;
						}
					}
		   		}
			}

			// Get the BCC's
			for (int x = 0; x < bcc.length; x++)
			{
		   		if (bcc[x] != null && 
			   		bcc[x].trim().length() != 0)
				{
				    String testTo = bcc[x];
					int findAt 		= testTo.lastIndexOf("@");
	
					if (findAt < 0)
					{
						testTo = findAddress(testTo);
						
						if (testTo.length() == 0)
						{
							errorProfileTo[countErrors] = bcc[x];
							countErrors = countErrors + 1;
						}
					}

					if (!testTo.equals(""))
					{
						try
						{
							addressTo = new InternetAddress(testTo);
							message.addRecipient(Message.RecipientType.BCC, addressTo);
						}
						catch(Exception e)
						{
							errorAddress[countErrorAdd] = bcc[x];
							countErrorAdd = countErrorAdd + 1;
						}
					}
		   		}
			}

			message.setSubject(subject);
			message.setContent(body,"text/html");
		   
			//System.out.println("message: " + message);
			Transport.send(message);
	
			   
			//*** Send out an email of all addresses which needed to be retrieved using ***//
			//    a profile, but could not be found.  
			//  Send this email to the helpdesk and to the person who tried to send the email. 
			if (countErrors != 0)
			{
				MimeMessage problemMessage = new MimeMessage(mailSession);
				problemMessage.setFrom(addressFrom);
		        
				addressTo = new InternetAddress(defaultAddress);
				problemMessage.addRecipient(Message.RecipientType.TO, addressTo);
				problemMessage.addRecipient(Message.RecipientType.CC, addressFrom);
		        	        
				String newSubject  = "Error trying to Find Email Addresses To:" + errorAddress[0];
				problemMessage.setSubject(newSubject);
				
				String msgText  = "Problem when trying to find email addresses for these Profiles : <br>";
				for (int x = 0; x < countErrors; x++)
				{
					msgText = msgText + errorProfileTo[x] + "<br>";
				}
				msgText = msgText +
					"These are not valid profiles OR e-mail addresses. <br>";
		        
				msgText = msgText + "For This Email: <br>" +
									"Subject : " + subject + "<br>" + body;
							   	
				problemMessage.setContent(msgText,"text/html");
	
				Transport.send(problemMessage);
			}
			
		
			//*** Send out an email of all addresses which were not valid internet email  ***//
			//    addresses.  
			//  Send this email to the person who tried to send the email. 
			if (countErrorAdd != 0)
			{
				MimeMessage problemMessage1 = new MimeMessage(mailSession);
				problemMessage1.setFrom(addressFrom);
		        
				problemMessage1.addRecipient(Message.RecipientType.TO, addressFrom);
		        	        
				String newSubject  = "Error not Valid Email Addresses";
				problemMessage1.setSubject(newSubject);
				
				String msgText  = "Problem with these Addresses, they are not Valid Internet Email Addresses: <br>";
				for (int x = 0; x < countErrors; x++)
				{
					msgText = msgText + errorProfileTo[x] + "<br>";
				}
		        
				msgText = msgText + "For This Email: <br>" +
						  "Subject : " + subject + "<br>" + body;
							   	
				problemMessage1.setContent(msgText,"text/html");
	
				Transport.send(problemMessage1);
			}
		}
		catch(Throwable theException)
		{
			// uncomment the following line when unexpected exceptions
			// are occuring to aid in debugging the problem.
			//theException.printStackTrace();
			System.out.println("com.treetop.Email.sendEmail()" + theException);
		}
		return returnMessage;
	}
	
	
	   

/**
 * This will take in a number of to, cc, and bcc's.
 *   Send an email from the same person to each one.
 * Creation date: (7/24/2003 8:46:32 AM)
 */

/**
 *    Current system access:
 * 			System - "COA" (Certificate of Analysis)
 * 			Key1   - "SO"  (Sales Order)
 *          Key2   - Sales Order Number
 */
	public String sendEmail(String[] to,
								String[] cc,
								String[] bcc,
								String from,
								String subject,
								String body,
								String system,
								String[] keyField1,
								String[] keyField2) 
	{
		String defaultAddress = "helpdesk@treetop.com";
		//String defaultAddress = "thaile@treetop.com";
		
		String returnMessage = "";
		try
		{
			// Setting up the basics for sending an email message.
			Properties props = new Properties();
			props.put("mail.smtp.host", "is-cas.private.treetop.com");
			Session mailSession = Session.getInstance(props,null);
			MimeMessage message = new MimeMessage(mailSession);
			InternetAddress addressFrom = new InternetAddress(defaultAddress);
			InternetAddress addressTo = new InternetAddress(defaultAddress);
		
    	
			//Get the From 
			String[] checkFrom = checkFrom(from);
			returnMessage = checkFrom[0];
			from = checkFrom[1];
			
			if (returnMessage.equals(""))
			{
				try{
					addressFrom = new InternetAddress(from);
					message.setFrom(addressFrom);
				} catch (Exception e)
				{
					returnMessage = "Unable to set email from " +
					                "location. ";
				}
			}
			
	   		if (system == null || system.trim().equals(""))
	   		{
	   			returnMessage = "A system is required for " +
	   							"this email request. " +
	   							"from:" + from + "/" +
	   							"subject:" + subject + "/" +
								"key1:" + keyField1 + "/" +
								"key2:" + keyField2 + ". ";	   							
	   		}


   
			// Test the To's - at least one good one.
			String[] errorProfileTo = new String[to.length];
			int countErrors = 0;
			String[] errorAddress = new String[to.length];      			
			int countErrorAdd = 0;
			
			int countGood = 0;
        
			for (int x = 0; x < to.length; x++)
			{
				if (to[x] != null && 
					to[x].trim().length() != 0)
				{
					String testTo = to[x];
					int findAt 		= testTo.lastIndexOf("@");

					if (findAt < 0)
					{
						testTo = findAddress(testTo);
						if (testTo.length() == 0)
						{
							errorProfileTo[countErrors] = to[x];
							countErrors = countErrors + 1;
						}
					}

					if (!testTo.equals(""))
					{
						try
						{
							//System.out.println("testTo: " + testTo);
							//System.out.println("from: " + from);
							//System.out.println("subject: " + subject);
							addressTo = new InternetAddress(testTo);
							message.addRecipient(Message.RecipientType.TO, addressTo);
							countGood = countGood + 1;
						}
						catch(Exception e)
						{
							errorAddress[countErrorAdd] = to[x];
							countErrorAdd = countErrorAdd + 1;
						}
					}
				}
		  	}
		
		
			//*** You have to have at least one good TO email.
			if (countGood == 0)
			{
				returnMessage = "The Email you tried to send does not have " +
								"any To Valid Addresses to send it to, please add/correct the " +
								"to addresses and retry.";
				return returnMessage; // Leave Method 
			}


			// Get the CC's     
			for (int x = 0; x < cc.length; x++)
			{
				if (cc[x] != null && 
					cc[x].trim().length() != 0)
				{
					String testTo = cc[x];
					int findAt 		= testTo.lastIndexOf("@");

					if (findAt < 0)
					{
						testTo = findAddress(testTo);
					
						if (testTo.length() == 0)
						{
							errorProfileTo[countErrors] = cc[x];
							countErrors = countErrors + 1;
						}
					}

					if (!testTo.equals(""))
					{
						try
						{
							addressTo = new InternetAddress(testTo);
							message.addRecipient(Message.RecipientType.CC, addressTo);
						}
						catch(Exception e)
						{
							errorAddress[countErrorAdd] = cc[x];
							countErrorAdd = countErrorAdd + 1;
						}
					}
				}
			}

			// Get the BCC's
			for (int x = 0; x < bcc.length; x++)
			{
				if (bcc[x] != null && 
					bcc[x].trim().length() != 0)
				{
					String testTo = bcc[x];
					int findAt 		= testTo.lastIndexOf("@");

					if (findAt < 0)
					{
						testTo = findAddress(testTo);
					
						if (testTo.length() == 0)
						{
							errorProfileTo[countErrors] = bcc[x];
							countErrors = countErrors + 1;
						}
					}

					if (!testTo.equals(""))
					{
						try
						{
							addressTo = new InternetAddress(testTo);
							message.addRecipient(Message.RecipientType.BCC, addressTo);
						}
						catch(Exception e)
						{
							errorAddress[countErrorAdd] = bcc[x];
							countErrorAdd = countErrorAdd + 1;
						}
					}
				}
			}

			message.setSubject(subject);
			message.setContent(body,"text/html");
	   
			Transport.send(message);
			addEmailEntries(message,
							system,
							keyField1,
							keyField2,
							from);
							
			
			//*** Send out an email of all addresses which needed to be retrieved using ***//
			//    a profile, but could not be found.  
			//  Send this email to the helpdesk and to the person who tried to send the email. 
			if (countErrors != 0)
			{
				MimeMessage problemMessage = new MimeMessage(mailSession);
	        	problemMessage.setFrom(addressFrom);
	        
            	addressTo = new InternetAddress(defaultAddress);
	        	problemMessage.addRecipient(Message.RecipientType.TO, addressTo);
	        	problemMessage.addRecipient(Message.RecipientType.CC, addressFrom);
	        	        
	        	String newSubject  = "Error trying to Find Email Addresses TO:" + errorAddress[0];
				problemMessage.setSubject(newSubject);
			
	        	String msgText  = "Problem when trying to find email addresses for these Profiles : <br>";
	        	for (int x = 0; x < countErrors; x++)
	        	{
		        	msgText = msgText + errorProfileTo[x] + "<br>";
	        	}
	        	msgText = msgText +
	                    "These are not valid profiles OR e-mail addresses. <br>";
	        
	       		msgText = msgText + "For This Email: <br>" +
       					"Subject : " + subject + "<br>" + body;
       						
		    	problemMessage.setContent(msgText,"text/html");

		    	Transport.send(problemMessage);
			}
			
			
			//*** Send out an email of all addresses which were not valid internet email  ***//
			//    addresses.  
			//  Send this email to the person who tried to send the email. 
			if (countErrorAdd != 0)
			{
				MimeMessage problemMessage1 = new MimeMessage(mailSession);
	        	problemMessage1.setFrom(addressFrom);
	        
	        	problemMessage1.addRecipient(Message.RecipientType.TO, addressFrom);
	        	        
	        	String newSubject  = "Error not Valid Email Addresses";
				problemMessage1.setSubject(newSubject);
			
	        	String msgText  = "Problem with these Addresses, they are not Valid Internet Email Addresses: <br>";
	        	for (int x = 0; x < countErrors; x++)
	        	{
		        	msgText = msgText + errorProfileTo[x] + "<br>";
	        	}
	        
	       		msgText = msgText + "For This Email: <br>" +
       					"Subject : " + subject + "<br>" +
       					body;	
		    	problemMessage1.setContent(msgText,"text/html");

		    	Transport.send(problemMessage1);
			}
		}
		catch(Throwable theException)
		{
			// uncomment the following line when unexpected exceptions
			// are occuring to aid in debugging the problem.
			//theException.printStackTrace();
			System.out.println("com.treetop.Email.sendEmail()" + theException);
		}
		return returnMessage;
	}

/**
 * This will take in a number of to, cc, and bcc's.
 *   Send an email from the same person to each one.
 * Creation date: (7/24/2003 8:46:32 AM)
 */
public String sendEmail(Vector emailInfo) 
{
//	String defaultAddress = "helpdesk@treetop.com";
	String defaultAddress = "twalton@treetop.com";
	
    Email thisEmail  = (Email) emailInfo.elementAt(0);
    
	String returnMessage = "";
	try
	{
	  // Setting up the basics for sending an email message.
	   Properties props = new Properties();
       props.put("mail.smtp.host", "is-cas.private.treetop.com");
       Session mailSession = Session.getInstance(props,null);
       MimeMessage message = new MimeMessage(mailSession);
       InternetAddress addressFrom = new InternetAddress(defaultAddress);
	   InternetAddress addressTo = new InternetAddress(defaultAddress);
       //****************************************
       //  Testing, getting the From
       //****************************************
	   if (from != null && 
		   from.trim().length() != 0)
	   {
		    String testFrom = from;
			int findAt 		= testFrom.lastIndexOf("@");
	
			if (findAt < 0)
			{
				testFrom = findAddress(testFrom);
				if (testFrom.length() == 0)
				{
					returnMessage = "The From Email Address for this profile " +
					    "(" + from + ") Could Not Be Found.  An Email has been sent " +
					    "to the helpdesk.  Please contact the helpdesk to set up this " +
					    "profile to email.";
   			   		message.setFrom(addressFrom);
 					addressTo = new InternetAddress(defaultAddress);
				    message.addRecipient(Message.RecipientType.TO, addressTo);

			        String newSubject  = "Error trying to Find Email Address FROM: " + from;
			        message.setSubject(newSubject);

			        String msgText  = "Problem when sending this email : <br>" +
			     					  returnMessage + "<br>" +
			       					"Subject : " + thisEmail.getSubject().trim() + "<br>" +
			       					thisEmail.getBody().trim();	
	 			    message.setContent(msgText,"text/html");

				    Transport.send(message);
				    return returnMessage; // Leave Method
				}
			}

			if (returnMessage.equals(""))
			{
				try
				{
					addressFrom = new InternetAddress(testFrom);
					message.setFrom(addressFrom);
				}
				catch(Exception e)
				{
						returnMessage = "The From Email Address " +
						    "(" + testFrom + ") is not a Valid Internet Email " +
						    "Address.";
				}
			}
	   }
	   else
	   {
		   returnMessage = "This Email does not have a from address. " +
		   	"To send an Email you need to have a from Address.";
		   return returnMessage; // Leave Method 
	   }
       
      //****************************************
      //  Testing, getting the To's
      //****************************************
        String[] errorProfileTo = new String[emailInfo.size()];
        int countErrors = 0;
        String[] errorAddress = new String[emailInfo.size()];
        int countErrorAdd = 0;
        
        int countGood = 0;
        
		for (int x = 0; x < emailInfo.size(); x++)
		{
			thisEmail  = (Email) emailInfo.elementAt(x);
    
		   if (thisEmail.getTo() != null && 
		   	   thisEmail.getTo().trim().length() != 0)
		   {
			    String testTo = thisEmail.getTo();
				int findAt 		= testTo.lastIndexOf("@");

				if (findAt < 0)
				{
					testTo = findAddress(testTo);
					if (testTo.length() == 0)
					{
						errorProfileTo[countErrors] = thisEmail.getTo();
						countErrors = countErrors + 1;
					}
				}

				if (!testTo.equals(""))
				{
					try
					{
						addressTo = new InternetAddress(testTo);
						message.addRecipient(Message.RecipientType.TO, addressTo);
						countGood = countGood + 1;
					}
					catch(Exception e)
					{
						errorAddress[countErrorAdd] = thisEmail.getTo();
						countErrorAdd = countErrorAdd + 1;
					}
				}
	   		}
      //****************************************
      //  Testing, getting the CC's
      //****************************************
		   if (thisEmail.getCC() != null && 
		   	   thisEmail.getCC().trim().length() != 0)
		   {
			    String testTo = thisEmail.getCC();
				int findAt 		= testTo.lastIndexOf("@");

				if (findAt < 0)
				{
					testTo = findAddress(testTo);
					if (testTo.length() == 0)
					{
						errorProfileTo[countErrors] = thisEmail.getCC();
						countErrors = countErrors + 1;
					}
				}

				if (!testTo.equals(""))
				{
					try
					{
						addressTo = new InternetAddress(testTo);
						message.addRecipient(Message.RecipientType.CC, addressTo);
					}
					catch(Exception e)
					{
						errorAddress[countErrorAdd] = thisEmail.getCC();
						countErrorAdd = countErrorAdd + 1;
					}
				}
	   		}
     //****************************************
      //  Testing, getting the BCC's
      //****************************************
	   	   if (thisEmail.getBCC() != null && 
		   	   thisEmail.getBCC().trim().length() != 0)
		   {
			    String testTo = thisEmail.getBCC();
				int findAt 		= testTo.lastIndexOf("@");

				if (findAt < 0)
				{
					testTo = findAddress(testTo);
					if (testTo.length() == 0)
					{
						errorProfileTo[countErrors] = thisEmail.getBCC();
						countErrors = countErrors + 1;
					}
				}

				if (!testTo.equals(""))
				{
					try
					{
						addressTo = new InternetAddress(testTo);
						message.addRecipient(Message.RecipientType.BCC, addressTo);
					}
					catch(Exception e)
					{
						errorAddress[countErrorAdd] = thisEmail.getBCC();
						countErrorAdd = countErrorAdd + 1;
					}
				}
	   		}
	
		}
		//*** You have to have at least one good TO email.
		if (countGood == 0)
		{
			   returnMessage = "The Email you tried to send does not have " +
		 	  	"any To Valid Addresses to send it to, please add/correct the " +
		 	  	"to addresses and retry.";
			   return returnMessage; // Leave Method 
	    }

       	message.setSubject(thisEmail.getSubject().trim());
		message.setContent(thisEmail.getBody().trim(),"text/html");

		Transport.send(message);

	   
	   
	 //*** Send out an email of all addresses which needed to be retrieved using ***//
	 //    a profile, but could not be found.  
	 //  Send this email to the helpdesk and to the person who tried to send the email. 
		if (countErrors != 0)
		{
			MimeMessage problemMessage = new MimeMessage(mailSession);
	        problemMessage.setFrom(addressFrom);
	        
            addressTo = new InternetAddress(defaultAddress);
	        problemMessage.addRecipient(Message.RecipientType.TO, addressTo);
	        problemMessage.addRecipient(Message.RecipientType.CC, addressFrom);
	        	        
	        String newSubject  = "Error trying to Find Email Addresses TO: " + errorAddress[0];
			problemMessage.setSubject(newSubject);
			
	        String msgText  = "Problem when trying to find email addresses for these Profiles : <br>";
	        for (int x = 0; x < countErrors; x++)
	        {
		        msgText = msgText + errorProfileTo[x] + "<br>";
	        }
	        msgText = msgText +
	                    "These are not valid profiles OR e-mail addresses. <br>";
	        
	       	msgText = msgText + "For This Email: <br>" +
       					"Subject : " + thisEmail.getSubject().trim() + "<br>" +
       					thisEmail.getBody().trim();	
		    problemMessage.setContent(msgText,"text/html");

		    Transport.send(problemMessage);
		}
	 //*** Send out an email of all addresses which were not valid internet email  ***//
	 //    addresses.  
	 //  Send this email to the person who tried to send the email. 
		if (countErrorAdd != 0)
		{
			MimeMessage problemMessage1 = new MimeMessage(mailSession);
	        problemMessage1.setFrom(addressFrom);
	        
	        problemMessage1.addRecipient(Message.RecipientType.TO, addressFrom);
	        	        
	        String newSubject  = "Error not Valid Email Addresses";
			problemMessage1.setSubject(newSubject);
			
	        String msgText  = "Problem with these Addresses, they are not Valid Internet Email Addresses: <br>";
	        for (int x = 0; x < countErrors; x++)
	        {
		        msgText = msgText + errorProfileTo[x] + "<br>";
	        }
	        
	       	msgText = msgText + "For This Email: <br>" +
       					"Subject : " + thisEmail.getSubject().trim() + "<br>" +
       					thisEmail.getBody().trim();	
		    problemMessage1.setContent(msgText,"text/html");

		    Transport.send(problemMessage1);
		}



	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}
	return returnMessage;
}
/**
 *
 * Creation date: (3/1/2004 2:42:28 PM)
 */
public void setBCC(String bccIn) 
{
	this.bcc = bccIn;
}
/**
 *
 * Creation date: (3/1/2004 2:42:28 PM)
 */
public void setBody(String bodyIn) 
{
	this.body = bodyIn;
}
/**
 *
 * Creation date: (3/1/2004 2:42:28 PM)
 */
public void setCC(String ccIn) 
{
	this.cc = ccIn;
}
/**
 *
 * Creation date: (3/1/2004 2:42:28 PM)
 */
public void setFrom(String fromIn) 
{
	this.from = fromIn;
}
/**
 *
 * Creation date: (3/1/2004 2:42:28 PM)
 */
public void setSubject(String subjectIn) 
{
	this.subject = subjectIn;
}
/**
 *
 * Creation date: (3/1/2004 2:42:28 PM)
 */
public void setTo(String toIn) 
{
	this.to = toIn;
}
	/**
	 * @return
	 */
	public java.sql.Date getAuditDate() {
		return auditDate;
	}

	/**
	 * @return
	 */
	public String getAuditFrom() {
		return auditFrom;
	}

	/**
	 * @return
	 */
	public String getAuditSubject() {
		return auditSubject;
	}

	/**
	 * @return
	 */
	public String getAuditSystem() {
		return auditSystem;
	}

	/**
	 * @return
	 */
	public java.sql.Time getAuditTime() {
		return auditTime;
	}

	/**
	 * @return
	 */
	public String getAuditTo() {
		return auditTo;
	}

	/**
	 * @return
	 */
	public String getAuditToType() {
		return auditToType;
	}

	/**
	 * @return
	 */
	public String getAuditType() {
		return auditType;
	}

	/**
	 * @return
	 */
	public Integer getAuditUniqueKey() {
		return auditUniqueKey;
	}

	/**
	 * @return
	 */
	public String getBcc() {
		return bcc;
	}

	/**
	 * @return
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * @param date
	 */
	public void setAuditDate(java.sql.Date date) {
		auditDate = date;
	}

	/**
	 * @param string
	 */
	public void setAuditFrom(String string) {
		auditFrom = string;
	}

	/**
	 * @param string
	 */
	public void setAuditSubject(String string) {
		auditSubject = string;
	}

	/**
	 * @param string
	 */
	public void setAuditSystem(String string) {
		auditSystem = string;
	}

	/**
	 * @param time
	 */
	public void setAuditTime(java.sql.Time time) {
		auditTime = time;
	}

	/**
	 * @param string
	 */
	public void setAuditTo(String string) {
		auditTo = string;
	}

	/**
	 * @param string
	 */
	public void setAuditToType(String string) {
		auditToType = string;
	}

	/**
	 * @param string
	 */
	public void setAuditType(String string) {
		auditType = string;
	}

	/**
	 * @param integer
	 */
	public void setAuditUniqueKey(Integer integer) {
		auditUniqueKey = integer;
	}

	/**
	 * @param string
	 */
	public void setBcc(String string) {
		bcc = string;
	}

	/**
	 * @param string
	 */
	public void setCc(String string) {
		cc = string;
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
		"auditUnique: " + auditUniqueKey +"\n"); 
	}
}
