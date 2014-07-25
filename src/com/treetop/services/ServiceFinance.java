/*
 * Created on March 20, 2008
 *
 */
package com.treetop.services;

import java.sql.*;
import java.util.Vector;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;
import com.treetop.app.finance.*;
import com.treetop.businessobjects.DateTime;
import com.treetop.utilities.*;
import com.treetop.utilities.html.*;
/**
 * @author twalto
 *
 * Services class to obtain and return data 
 * to business objects.
 * Lot Services Object.
 */
public class ServiceFinance extends BaseService{
	// Not needed right now
	public static final String dblib   = "DBPRD";
	public static final String library = "M3DJDPRD";
	
	/**
	 *  Construction of the Base inforamtion
	 */
	public ServiceFinance() {
		super();
	}
	
	/**
	 * Build an sql statement.
	 * @param request type
	 * @param Vector selection criteria
	 * @return sql string
	 * @throws Exception
	 */

	private static String buildSqlStatement(String environment,
											String inRequestType,
											Vector requestClass)
		throws Exception 
	{
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();
		if (inRequestType.equals("stateFeeDates"))
		{
			sqlString.append("SELECT OEDRDT, OEDRTM ");
			sqlString.append("FROM " + dblib + ".OEPDSFHS ");
			sqlString.append("GROUP BY OEDRDT, OEDRTM ");
			sqlString.append("ORDER BY OEDRDT DESC, OEDRTM DESC ");
		}
		try
		{
			if (inRequestType.equals("ddRFChargeCodes"))
			{
				sqlString.append("SELECT FUPLOP, FUTX40 ");
				sqlString.append("FROM " + library + ".FPLOPT ");
				sqlString.append("GROUP BY FUPLOP, FUTX40 ");
				sqlString.append("ORDER BY FUPLOP, FUTX40 ");
			}
		}
		catch(Exception e)
		{}
		
		return sqlString.toString();
	}
	
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		// Test all that you can.
	}

	/**
	 * Add Records to file DBPRD/OEPDSFHS
	 *    Calling an CL program ARC760 
	 *      Which will call RPG program ARR760 to do this
	 */
	
	private static BldFinance callRPGProcessStateFees(BldFinance bf, 
													  AS400 system)
	{
		try
		{
		// Create an AS400 OBJECT - on the m3 machine :
	      ProgramCall pgm = new ProgramCall(system);
		// Display information for Testing	
		//	System.out.println(inValues.elementAt(0));
		//	System.out.println(inValues.elementAt(1));
		//	System.out.println(inValues.elementAt(2));
	
			ProgramParameter[] parmList = new ProgramParameter[1];
			
			// Initialize parameters.
			// create a AS400text object -- Define Length
			AS400Text dates = new AS400Text(16, system);
				
			// create a byte array to be used as a parameter and
			// load it with the AS400Text object with the incoming
			// string value.
			   // Hard Code Environment for now
			byte[] parmDates = dates.toBytes(bf.getBldFromDateParmFormat() + bf.getBldToDateParmFormat());
							
			// Set Parameters into the Parm List
			parmList[0]  = new ProgramParameter(parmDates, 16);
    //		 Define the Returned Parms with just length
			
			// Call the program.
			pgm = new ProgramCall(system,"/QSYS.LIB/MOVEX.LIB/ARC760.PGM", parmList);			

			if (pgm.run() != true) {
				bf.setDisplayMessage(" Error occured while running RPG Program ARC760.  Program call failed.");
			}
			else
				bf.setDisplayMessage(" Program to Build State Fees Processing (Compute Monthly Payments) has been run.  ");
		}
		catch(Exception e)
		{
			bf.setDisplayMessage("Problem when running ARC760 Program: " + e);
		}	
		return bf;
	}	
	/*
 	 * Use this to Control Access of RPG/CL Calls
	 */
	public static BldFinance callRPG(BldFinance bf) {
		AS400 as400 = null;
		try
		{
			as400 = ConnectionStack.getAS400Object();
			if (bf.getRequestType().trim().equals("addStateFees"))
				bf = callRPGProcessStateFees(bf, as400);
			if (bf.getRequestType().trim().equals("bldReportStateFees"))
				bf = callRPGRunReportStateFees(bf, as400);
		}
		catch(Exception e)
		{
			bf.setDisplayMessage("PrintScreen this and send to the Helpdesk, " +
								 "ServiceFinance.callRPG(BldFinance) " + 
					             "Problem Building AS400 Object when calling Program: " + e);
			// Uncomment for Testing
//			System.out.println("ServiceFinance.callRPG(BldFinance) ");
//			System.out.println("Problem Building AS400 Object when calling Program: " + e);
		}
		finally
		{
			if (as400 != null)
			{
				try
				{
					ConnectionStack.returnAS400Object(as400);
				}
				catch(Exception e)
				{}
			}
		}
		return bf;
	}

	/**
	 * Return a Vector of DateTime classes
	 *    Information based on the File OEPDSFHS - State Fees
	 * 
	 * @param 
	 * @return Vector dates.
	 * @throws Exception
	 */
	private static Vector findDateTimeForStateFees(String environment,
											       Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		PreparedStatement findIt = null;
		ResultSet rs = null;
		Vector returnDates = new Vector();
		
		try {
			Vector sendValues = new Vector();
			
		//------------------------------	
		// Get All Costing Dates Descending Order
			try {
				findIt = conn.prepareStatement(buildSqlStatement(environment, "stateFeeDates", sendValues));
				rs = findIt.executeQuery();
			}
			catch(Exception e)
			{
				System.out.println(" TEST Valid SQL: " + e);
				throwError.append(" error occured executing the sql statement, to Retrieve Dates for State Fees. " + e);
			}
			try
			{
				while (rs.next())
				{
					try
					{
//					   if (!rs.getString("OEDRDT").trim().equals("0"))
//					   {	
					      DateTime dt = UtilityDateTime.getDateFromyyyyMMdd(rs.getString("OEDRDT"));
					      DateTime dt2 = UtilityDateTime.getTimeFromhhmmss(rs.getString("OEDRTM"));
					      dt.setTimeFormathhmmss(rs.getString("OEDRTM"));
					      dt.setTimeFormatAMPM(dt2.getTimeFormatAMPM());
					      dt.setTimeFormathhmmssColon(dt2.getTimeFormathhmmssColon());
					      returnDates.addElement(dt);
//					   }
					}
					catch(Exception e)
					{}
				}
			
			}
			catch(Exception e)
			{
				throwError.append(" error occured reading of result set. " + e);
			} finally {
				if (rs != null) {
					try {
						rs.close();
						findIt.close();
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			throwError.append(" error occured processing information. " + e);
		} 
		// return error data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceFinance.");
			throwError.append("findDateTimeForStateFees(Environment, Connection). ");
			throw new Exception(throwError.toString());
		}
		return returnDates;
	}

	/**
	 * Use to return a Vector of DateTime classes - that are built from 
	 *      the dates in the OEPDSFHS file, for State Fees
	 */
	
	public static Vector returnVectorDateTimeStateFees(String environment)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		Vector returnValues = new Vector();
		try { 
			String requestType = "";
			String sqlString = "";
			
			// verify base class initialization.
			ServiceFinance a = new ServiceFinance();
						
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			conn = ConnectionStack.getConnection();
			
			returnValues = findDateTimeForStateFees(environment, conn);
			
		} catch(Exception e)
		{
			throwError.append(" Problem Retreiving Dates " + e);
		// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						ConnectionStack.returnConnection(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
	
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinance.");
			throwError.append("returnVectorDateTimeStateFees(String: Environment)");
			throw new Exception(throwError.toString());
		}
		return returnValues;
	}

	/**
	 * Run Report from Java
	 *    Calling an CL program ARC761 
	 *      Which will call RPG program ARR761 or ARR762 to do this
	 */
	
	private static BldFinance callRPGRunReportStateFees(BldFinance bf, 
													    AS400 system)
	{
		try
		{
		// Create an AS400 OBJECT - on the m3 machine :
	      ProgramCall pgm = new ProgramCall(system);
		// Display information for Testing	
		//	System.out.println(inValues.elementAt(0));
		//	System.out.println(inValues.elementAt(1));
		//	System.out.println(inValues.elementAt(2));
			ProgramParameter[] parmList = new ProgramParameter[1];
			
			// Initialize parameters.
			// create a AS400text object -- Define Length
			AS400Text parms = new AS400Text(45, system);
				
			// create a byte array to be used as a parameter and
			// load it with the AS400Text object with the incoming
			// string value.
			StringBuffer rec = new StringBuffer(45);
		    rec.setLength(45);
			for (int x = 0; x < 45; x++) {
			 	  rec.insert(0, " ");
			  }
			// Start with a BLANK String Buffer
			if (bf.getRptChosenReport().trim().equals("DR-3"))
				rec.insert(0, "1");
			if (bf.getRptChosenReport().trim().equals("DR-4"))
				rec.insert(1, "1");
			rec.insert(2, bf.getRptType().trim());
			rec.insert(3, bf.getRptRunType().trim());
			rec.insert(4, bf.getRptMonth().trim());
			rec.insert(19, bf.getRptDate().trim());
			String reportTime = bf.getRptTime().trim();
			if (reportTime.length() == 5)
				reportTime = "0" + reportTime;
			rec.insert(27, reportTime);
			rec.insert(39, bf.getUpdateUser().trim());
			rec.setLength(45);
			
			byte[] parmInfo = parms.toBytes(rec.toString());
							
			// Set Parameters into the Parm List
			parmList[0]  = new ProgramParameter(parmInfo, 45);
	//		 Define the Returned Parms with just length
			
			// Call the program.
			pgm = new ProgramCall(system,"/QSYS.LIB/MOVEX.LIB/ARC761.PGM", parmList);			
	
			if (pgm.run() != true) {
				bf.setDisplayMessage(" Error occured while running RPG Program ARC761.  Program call failed.");
			}
			else
				bf.setDisplayMessage(" Program to Run Report for State Fees has been run.  ");
		}
		catch(Exception e)
		{
			bf.setDisplayMessage("Problem when running ARC761 Program: " + e);
		}	
		return bf;
	}

	/**
	 *   Method Created 11/10/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
	 * @return Vector - of DropDownSingle 
	 * 
	 *  Incoming Vector:
	 *    Element 1 = String - ONLY needed IF narrowing List
	 */
	public static Vector dropDownRFChargeCode(Vector inValues)
	{
		Vector ddit = new Vector();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ConnectionStack.getConnection();
			ddit = dropDownRFChargeCode(inValues, conn);
		} catch (Exception e)
		{
			// Don't really need to worry about any exceptions
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
		// return value
		return ddit;
	}

	/**
	 * Method Created 11/7/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
	 * @return Vector - of DropDownSingle
	 */
	private static Vector dropDownRFChargeCode(Vector inValues, 
										   	   Connection conn)
	{
		Vector ddit = new Vector();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try
		{
			try {
				   // Get the list of Item Type Values - Along with Descriptions
			   listThem = conn.prepareStatement(buildSqlStatement("PRD", "ddRFChargeCodes", inValues));
			   rs = listThem.executeQuery();
			 } catch(Exception e)
			 {
			 	System.out.println("error" + e);
			   	//throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			 }		
			 try
			 {
			 	while (rs.next())
			    {
			 		DropDownSingle dds = loadFieldsDropDownSingle("ddRFChargeCodes", rs);
			 		ddit.addElement(dds);
	     		}// END of the IF Statement
			 } catch(Exception e)
			 {
				//throwError.append(" Error occured while Building Vector from sql statement. " + e);
			 } 		
		} catch (Exception e)
		{
			//throwError.append(e);
		}
		finally {
		   if (rs != null)
		   {
			   try
			   {
				  rs.close();
				} catch(Exception el){
					el.printStackTrace();
				}
		    }
		   if (listThem != null)
		   {
			   try
			   {
				  listThem.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
	//	if (!throwError.toString().trim().equals(""))
	//	{
	//		throwError.append(" @ com.treetop.Services.");
	//		throwError.append("ServiceFinance");
	//		throwError.append("dropDownRFChargeCode(");
	//		throwError.append("Vector, conn). ");
	//		throw new Exception(throwError.toString());
	//	}
		// return value
		return ddit;
	}

	/**
	 * Load class fields from result set.
	 */
	
	public static DropDownSingle loadFieldsDropDownSingle(String requestType,
							          		  		     ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		DropDownSingle returnValue = new DropDownSingle();
		try{ 
		  if (requestType.equals("ddRFChargeCodes"))
		  {
			returnValue.setValue(rs.getString("FUPLOP").trim());
			returnValue.setDescription(rs.getString("FUTX40").trim());
		  }
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Drop Down Single class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanace.");
			throwError.append("loadFieldsDropDownSingle(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}
}
