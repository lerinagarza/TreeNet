/*
 * Created on May 29, 2009
 * Author Teri Walton
 *
 */
package com.treetop.services;

import java.sql.*;
import java.util.*;

import com.treetop.businessobjects.DateTime;
import com.treetop.utilities.*;

/**
 * @author twalto
 *
 * Services class modify
 * Lot Related Work Files. 
 */
public class ServiceLotWorkFiles extends BaseService{

	public static final String ttlib   = "DBPRD";
	public static final String library = "M3DJDPRD";
	
	/**
	 *  Construction of the Base inforamtion
	 */
	public ServiceLotWorkFiles() {
		super();
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
		
		try { 
		//******************************************************************
	    //*** LIST SECTION
		//******************************************************************
			if (inRequestType.equals("listRawFruitLots"))
			{
				sqlString.append("SELECT LMCONO, LMITNO, LMBANO, LMATNR, MMEVGR, ");
				sqlString.append("       AGATID, AGATVA, AGATVN ");
				sqlString.append("FROM " +  library + ".MILOMA ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MITMAS ");
				sqlString.append("       ON MMCONO = LMCONO AND MMITNO = LMITNO ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MIATTR ");
				sqlString.append("       ON AGCONO = LMCONO AND AGATNR = LMATNR ");
				sqlString.append("      AND AGITNO = LMITNO AND AGBANO = LMBANO ");
				sqlString.append("      AND (AGATID = 'TGRAD' OR AGATID = 'VAR' OR AGATID = '# CTNRS' ");
				sqlString.append("        OR AGATID = 'AVG WT CTNR' OR AGATID = 'RUN' OR AGATID = 'CTNR TYPE' ");
				sqlString.append("        OR AGATID = 'COO') ");
				sqlString.append("WHERE MMITTY = '200' ");
				//sqlString.append("and LMBANO not like '%''%' ");
				//-----------------------------------
				// For debug Purposes
			//	sqlString.append("  AND LMITNO = '75000002' ");
				//-----------------------------------
				sqlString.append("  AND NOT EXISTS (SELECT * FROM " + ttlib + ".GRPRATTR ");
				sqlString.append("      WHERE GRRCONO = LMCONO AND GRRATNR = LMATNR ");
				sqlString.append("        AND GRRITNO = LMITNO AND GRRBANO = LMBANO) ");
			}
			
			
			
			if (inRequestType.equals("listChangesLast5Days"))
			{  // Return if MILOMA (Lot Master) or MIATTR (Lot Attributes) have changed in the past 5 days
				DateTime dt1 = UtilityDateTime.getSystemDate();
				DateTime dt2 = UtilityDateTime.addDaysToDate(dt1, -5);
				sqlString.append("SELECT GRRBANO ");
				sqlString.append("FROM " +  ttlib + ".GRPRATTR ");
				sqlString.append("INNER JOIN " + library + ".MILOMA ");
				sqlString.append("     ON GRRCONO = LMCONO AND GRRATNR = LMATNR ");
				sqlString.append("    AND GRRITNO = LMITNO AND GRRBANO = LMBANO ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MIATTR ");
				sqlString.append("       ON AGCONO = LMCONO AND AGATNR = LMATNR ");
				sqlString.append("      AND AGITNO = LMITNO AND AGBANO = LMBANO ");
				sqlString.append("WHERE LMLMDT >= " + dt2.getDateFormatyyyyMMdd() + " ");
				sqlString.append("   OR AGLMDT >= " + dt2.getDateFormatyyyyMMdd() + " ");
				sqlString.append("GROUP BY GRRBANO ");
				sqlString.append("ORDER BY GRRBANO ");
			}
			//******************************************************************
			//*** INSERT SECTION
			//******************************************************************
			if (inRequestType.equals("addRawFruitLot"))
			{
				sqlString.append("INSERT INTO " + ttlib + ".GRPRATTR ");
				sqlString.append("(GRRCONO,  GRRATNR, GRRITNO,   GRRBANO, GRRORGANIC, ");
				sqlString.append(" GRRTGRAD, GRRVAR,  GRRCNTTYP, GRRCOO,  GRRCNTNRS,      ");
				sqlString.append(" GRRAVGCNT, GRRRUN, GRRDATE,   GRRTIME ) ");
				sqlString.append(" VALUES( ");
				sqlString.append("100,"); // Company (GRRCONO)
				sqlString.append(requestClass.elementAt(2) + ","); // Attribute Number - to be able to tie to the LOT Master (GRRATNR)
				sqlString.append("'" + requestClass.elementAt(3) +"',"); // Item Number (GRRITNO)
				sqlString.append("'" + requestClass.elementAt(4) +"',"); // Lot Number (GRRBANO)
				sqlString.append("'" + requestClass.elementAt(5) +"',"); // Organic / Conventional (GRRORGANIC)
				sqlString.append("'" + requestClass.elementAt(6) +"',"); // Tree Top Grade (GRRTGRAD)
				sqlString.append("'" + requestClass.elementAt(7) +"',"); // Variety (GRRVAR)
				sqlString.append("'" + requestClass.elementAt(8) +"',"); // Container Type (GRRCNTTYP)
				sqlString.append("'" + requestClass.elementAt(9) +"',"); // Country of Origin (GRRCOO)
				sqlString.append(requestClass.elementAt(10) + ","); // Number of Containers (GRRCNTNRS)
				sqlString.append(requestClass.elementAt(11) + ","); // Average Weight Per Container (GRRAVGCNT)
				sqlString.append("'" + requestClass.elementAt(12) +"',"); // Run (GRRRUN)
				sqlString.append(requestClass.elementAt(0) + ","); // Date
				sqlString.append(requestClass.elementAt(1) + " "); // Time
				sqlString.append(") ");
			}
			//******************************************************************
			//*** UPDATE SECTION
			//******************************************************************
			
			//******************************************************************
			//*** DELETE SECTION
			//******************************************************************
			if (inRequestType.equals("deleteRawFruit"))
			{
				sqlString.append("DELETE FROM " + ttlib + ".GRPRATTR ");
				sqlString.append("WHERE GRRBANO IN (");
				StringBuffer lots = new StringBuffer();
				for (int x = 0; x < requestClass.size(); x++)
				{
					if (!lots.toString().trim().equals(""))
					   lots.append(",");
					lots.append("'" + ((String) requestClass.elementAt(x)).trim() + "'");
				}
				sqlString.append(lots);
				sqlString.append(")");
			}
			
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for request type " + inRequestType + ". ");
		}
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceLotWorkFiles.");
			throwError.append("buildSqlStatement(String, Vector)");
			throw new Exception(throwError.toString());
		}
		   
		return sqlString.toString();
	}
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		// Test all that you can.
		String startHere = "yes";
		String checkHere = "yes";
		
		if ("x" == "y") {
			
			checkHere = "yes";
			
			try {
				buildRawFruit("bldLotAttribute");
			} catch (Exception e) {
				checkHere = "yes";
			}
			
		}
		

		
		if ("x" == "y")
		{
			String lib = "thaile";
			String table = "tempf";
			String sql = "";
			
			try {
				Connection conn = ServiceConnection.getConnectionStack1();
				sql = GeneralUtility.getPreparedStatementSql(conn, lib, table);
				ServiceConnection.returnConnectionStack1(conn);
				checkHere = "yes";
			} catch (Exception e) {
				checkHere = "yes";
			}
		}
	
	}
/**
 * Build Cost Component Entries 
 * For Current Fiscal Year By Type
 * @param conn.
 * @param cost type.
 * @return Connection.
 */
private static Connection buildRawFruit(String type,
										Connection conn)
	throws Exception 
{
	StringBuffer throwError     = new StringBuffer();
    ResultSet rs                = null;
	java.sql.Statement findIt   = null;
	java.sql.PreparedStatement addIt    = null;
	String sqlString            = "";
	
	try {
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();

		//delete records that the attributes or the lot has been changed
		//    within the past 5 days
		try
		{
			deleteRawFruit(type, conn);
		} catch (Exception e) {
			throwError.append("Error at build sql (deleteLotAttributeValues). " + e);
		}
		if (throwError.toString().equals(""))
		{
			//Get List of Lot's and Values to ADD Back into the File
			try //build sql.
			{
				Vector parmClass = new Vector();
				sqlString = buildSqlStatement("listRawFruitLots", parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (listRawFruitLots). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
					
					String lastBano = "";
					String lastItno = "";
					String lastAttr = "";
				    String organic = "";
				    String tgrad = "";
				    String var = "";
				    String containerType = "";
				    String countryOfOrigin = "";
				    String numberOfContainers = "0";
				    String aveWeightPerContainer = "0";
				    String run = "";
					
					// for each record with new Item/Lot add to the work File.
					String sql = GeneralUtility.getPreparedStatementSql(conn, ttlib, "GRPRATTR");
					addIt = conn.prepareStatement(sql);
					
					while (rs.next() && throwError.toString().equals(""))
					{
						try {//build the sql.
							if (!rs.getString("LMITNO").trim().equals(lastItno.trim()) ||
								!rs.getString("LMBANO").trim().equals(lastBano.trim()))
							{
								if (!lastBano.trim().equals(""))
								{
									addIt.setString(1, "100");
									addIt.setString(2, lastAttr);
									addIt.setString(3, lastItno);
									addIt.setString(4, lastBano);
									addIt.setString(5, organic);
									addIt.setString(6, tgrad);
									addIt.setString(7, FindAndReplace.replaceApostrophe(var));
									addIt.setString(8, containerType);
									addIt.setString(9, countryOfOrigin);
									addIt.setString(10, numberOfContainers);
									addIt.setString(11, aveWeightPerContainer);
									addIt.setString(12, run);
									addIt.setString(13, dt.getDateFormatyyyyMMdd());
									addIt.setString(14, dt.getTimeFormathhmmss());
									
									//sqlString = buildSqlStatement("addRawFruitLot", parmClass);

									if (throwError.toString().equals(""))
									{
										try {
											addIt.executeUpdate();
										} catch (Exception e) {
											throwError.append("Error executing sql (addRawFruitLot)" + e);
										}
									}
								}
								lastItno = rs.getString("LMITNO").trim();
								lastBano = rs.getString("LMBANO").trim();
								lastAttr = rs.getString("LMATNR").trim();
								organic = "";
							    tgrad = "";
							    var = "";
							    containerType = "";
							    countryOfOrigin = "";
							    numberOfContainers = "0";
							    aveWeightPerContainer = "0";
							    run = "";
							}
							if (organic.equals(""))
							{
								if (rs.getString("MMEVGR") != null)
								{	
									if (rs.getString("MMEVGR").trim().equals("CN"))
									   organic = "Conventional";
									if (rs.getString("MMEVGR").trim().equals("OR"))
										   organic = "Organic";
								}
							}
							if (rs.getString("AGATID") != null)
							{
								if (rs.getString("AGATID").trim().equals("TGRAD"))
								   tgrad = rs.getString("AGATVA").trim();
								if (rs.getString("AGATID").trim().equals("VAR"))
								   var = rs.getString("AGATVA").trim();
								if (rs.getString("AGATID").trim().equals("CTNR TYPE"))
								   containerType = rs.getString("AGATVA");
								if (rs.getString("AGATID").trim().equals("COO"))
								   countryOfOrigin = rs.getString("AGATVA").trim();
								if (rs.getString("AGATID").trim().equals("# CTNRS"))
								   numberOfContainers = rs.getString("AGATVN");
								if (rs.getString("AGATID").trim().equals("AVG WT CTNR"))
								   aveWeightPerContainer = rs.getString("AGATVN");
								if (rs.getString("AGATID").trim().equals("RUN"))
								   run = rs.getString("AGATVA").trim();
								
							}
						} catch (Exception e) {
							throwError.append("Error build sql (addRawFruitlot)" + e);
						}
					} // end of the WHILE
					// Insert the LAST Record
					addIt.setString(1, "100");
					addIt.setString(2, lastAttr);
					addIt.setString(3, lastItno);
					addIt.setString(4, lastBano);
					addIt.setString(5, organic);
					addIt.setString(6, tgrad);
					addIt.setString(7, FindAndReplace.replaceApostrophe(var));
					addIt.setString(8, containerType);
					addIt.setString(9, countryOfOrigin);
					addIt.setString(10, numberOfContainers);
					addIt.setString(11, aveWeightPerContainer);
					addIt.setString(12, run);
					addIt.setString(13, dt.getDateFormatyyyyMMdd());
					addIt.setString(14, dt.getTimeFormathhmmss());
					//sqlString = buildSqlStatement("addRawFruitLot", parmClass);
					
					try {
						addIt.executeUpdate();
					} catch (Exception e) {
						throwError.append("Error executing sql (addRawFruitLot)" + e);
					}
					
					addIt.close();
					rs.close();
					findIt.close();
					
				} catch(Exception e) {
					throwError.append("Error at execute sql (listRawFruitLots). " + e);
					System.out.println("Error at execute sql (listRawFruitLots). " + e);
				}
				
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
					
	finally
	{
		try
		{
			if (findIt != null)
				findIt.close();
			if (addIt != null)
				addIt.close();
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceLotWorkFiles.");
			throwError.append("buildRawFruit). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}

  return conn;
}
/**
 *   Method Created 6/1/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the Type of Build 
 *          Blank is valid
 * @return void
 * @throws Exception
 */
public static void buildRawFruit(String type)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack8.getConnection();
		buildRawFruit(type, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		//close connection.
		try
		{
			if (conn != null)
				ConnectionStack8.returnConnection(conn);
		} catch(Exception e){
			throwError.append("Error closing connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceLotWorkFiles.");
			throwError.append("buildRawFruit(String). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}
	// return value
	return;
}

/**
 * Delete any records that the Lot or Lot Attributes had been updated
 *    within the past 5 days
 * @param typc, conn.
 * @return Connection.
 */
private static Connection deleteRawFruit(String type,
										 Connection conn)
	throws Exception 
{
	StringBuffer throwError     = new StringBuffer();
    ResultSet rs                = null;
	java.sql.Statement findIt   = null;
	java.sql.Statement deleteIt = null;
	String sqlString            = "";
//	 Put all the lots found into a Vector
	Vector lotsFound = new Vector();
	try {
		try //build sql.
		{
			Vector parmClass = new Vector();
			sqlString = buildSqlStatement("listChangesLast5Days", parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (listChangesLast5Days). " + e);
		}
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findIt = conn.createStatement();
				rs = findIt.executeQuery(sqlString);
				
				while (rs.next())
				{ 
					lotsFound.addElement(rs.getString("GRRBANO"));
					
				} // end of the WHILE
				
				rs.close();
				findIt.close();
			} catch(Exception e) {
				throwError.append("Error at execute sql (listRawFruitLots). " + e);
				System.out.println("Error at execute sql (listRawFruitLots). " + e);
			}
			
		}		
		if (throwError.toString().equals("") &&
			lotsFound.size() > 0)
		{
			try //build sql.
			{
				sqlString = buildSqlStatement("deleteRawFruit", lotsFound);
			} catch(Exception e) {
				throwError.append("Error at build sql (deleteRawFruit). " + e);
			}
			try {
				deleteIt = conn.createStatement();
				deleteIt.executeUpdate(sqlString);
				deleteIt.close();
			} catch (Exception e) {
				throwError.append("Error executing sql (deleteRawFruit)" + e);
				System.out.println("Error executing sql (deleteRawFruit)" + e);
			}
		}	
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
					
	finally
	{
		try
		{
			if (findIt != null)
				findIt.close();
			if (deleteIt != null)
				deleteIt.close();
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceLotWorkFiles.");
			throwError.append("deleteRawFruit). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}

  return conn;
}

}



