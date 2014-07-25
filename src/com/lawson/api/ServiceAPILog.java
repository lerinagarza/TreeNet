/*
 * Created on November 22, 2006
 *  // Moved to be with the Lawson Informaton 
 *  // Also Changed the file to the new machine  TWALTO 6/11/08
 */
package com.lawson.api;

  import com.treetop.businessobjects.DateTime;
  import com.treetop.services.BaseService;
  import com.treetop.utilities.ConnectionStack;
  import com.treetop.utilities.UtilityDateTime;
  import java.sql.Connection;
  import java.sql.PreparedStatement;
  import java.util.Vector;

  import com.treetop.utilities.FindAndReplace;


/**
 * @author thaile
 *
 * Services class to obtain and return data 
 * to business objects.
 * API Log Services Object.
 * 
 *   Log (file) specifically used for Tracking API information
 * 			File Name: GNPAPILOG
 */
public class ServiceAPILog extends BaseService{

   /**
	* Constructor
	*/
	public ServiceAPILog() {
		super();
	}

	/**
	 * Add a API Log entry. 
	 * 
	 * @param Business Object, apiLog
	 * @return String containing exception criteria.
	 * @throws Exception
	 */
	
	public static String logAPITransaction(APILog inData)
									
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String returnInfo = "";
		
//		 verify base class initialization.
		ServiceAPILog sv = new ServiceAPILog();
		
		// edit incoming transaction data.
		if (inData.getInputData().trim().equals(""))
		{
			throwError.append("Incoming InputData is Blank. ");
			throwError.append("No valid log entry can be entered. ");
		}
		if (throwError.toString().equals(""))
		{
			Connection conn = null;
			try {
				// get a connection to be sent to find methods
				conn = ConnectionStack.getConnection();
				
				returnInfo = logAPITransaction(inData,
										       conn);		
			} catch (Exception e)
			{
				throwError.append(e);
			}
			finally {
				if (conn != null)
				{
					try
					{
					   ConnectionStack.returnConnection(conn);
					} catch(Exception el){
						el.printStackTrace();
					}
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.lawson.api.");
			throwError.append("ServiceAPILog.");
			throwError.append("logAPITransaction(");
			throwError.append("APILog) ");
			throw new Exception(throwError.toString());
		}

		// return value
		return returnInfo;
	}
	
	
	/**
	 * Build an sql statement.
	 * @param request type
	 * @param Vector selection criteria
	 * @return sql string
	 * @throws Exception
	 */

	private static String buildSqlStatement(String inRequestType,
											Vector requestClass)
		throws Exception 
	{
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();
		
		try { // add a new log entry.
			if (inRequestType.equals("addLogNew"))
			{
				// cast the incoming parameter class.
				APILog info = (APILog) requestClass.elementAt(0);
				// get current system date and time.
				DateTime dt = UtilityDateTime.getSystemDate();
				
				// build the sql statement.
				sqlString.append("INSERT  INTO DB" + info.getEnvironment().trim() + ".GNPAPILOG ");
				sqlString.append("VALUES(");
				sqlString.append("'" + info.getApiName().trim() + "', ");
				sqlString.append("'" + info.getApiMethod().trim() + "', ");
				sqlString.append("'" + info.getEnvironment().trim() + "', ");
				sqlString.append("'" + info.getSentFromProgram().trim() + "', ");
				sqlString.append("'" + FindAndReplace.replaceApostrophe(info.getInputData().trim()) + "', ");
				sqlString.append("'" + FindAndReplace.replaceApostrophe(info.getOutputData().trim()) + "', ");
				sqlString.append("'" + info.getUserProfile().trim() + "', ");
				sqlString.append( dt.getDateFormatyyyyMMdd() + ", ");
				sqlString.append( dt.getTimeFormathhmmss() + ") ");
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement");
				throwError.append(" for request type " + inRequestType + ". ");
		}
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.lawson.api.");
			throwError.append("ServiceAPILog.");
			throwError.append("buildSqlStatement(String, Vector)");
			throw new Exception(throwError.toString());
		}

		return sqlString.toString();
	}
	
	
	/**
	 * Enter a record for an API tranaction.
	 * @param APILog -- incoming information to be used to add a record
	 * @param Connection send in the Connection 
	 * @return String of any relevant information.
	 */
	private static String logAPITransaction(APILog apiData,
									        Connection conn)
	throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String sqlString = "";
		PreparedStatement addIt = null;
		String returnInfo = "";
		// get the sql statement.
		try {
			// use vector to hold selection criteria.
			Vector parmClass = new Vector();
			parmClass.addElement(apiData);
			sqlString = buildSqlStatement("addLogNew", parmClass);
		} catch(Exception e) {
			throwError.append(" Error at sql request. " + e);
		}
		
		// get a connection. execute sql.
		if (throwError.toString().trim().equals(""))
		{
			try {
				addIt = conn.prepareStatement(sqlString);
				addIt.executeUpdate();
			} catch(Exception e)
			{
				throwError.append("error occured executing a sql statement. " + e);
				
			// return connection.
			} finally {
				
				try {
					if (addIt != null)
						addIt.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		if (!throwError.toString().trim().equals("")) 
		{
			throwError.append("Error at com.lawson.api.");
			throwError.append("ServiceAPILog.");
			throwError.append("logAPITransaction(APILog, Connection)");
			throw new Exception(throwError.toString());
		}
		return returnInfo;
	}
	
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		// Test all that you can.
		String stophere = "";		
		Vector vector = null;
		
		try {
		//*** TEST "logAPITransaction(String,String,String,String)".
			if ("x".equals("x"))
			{
				String apiName = "MMS200MI";
				String inputData = "AddItemBasic   abcdefg";
				String returnData = "NOK ' huh?";
				String apiUser = "THAILE";
				String returnInfo = "";
				
				ServiceAPILog logClass = new ServiceAPILog();
				
				try {
//					returnInfo = logAPITransaction(apiName,
//												   inputData,
//												   returnData,
//												   apiUser);
				} catch (Exception e) {
					stophere = "x";
				}
				
				stophere = "x";
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
