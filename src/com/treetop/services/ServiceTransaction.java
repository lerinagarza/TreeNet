/*
 * Created on September 25, 2008
 * 
 */
package com.treetop.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.ibm.as400.access.*;

import com.treetop.app.transaction.UpdTransaction;
import com.treetop.app.transaction.InqTransaction;
import com.treetop.businessobjects.*;
import com.treetop.businessobjects.User;
import com.treetop.services.ServiceUser;
import com.treetop.utilities.ConnectionStack;
import com.treetop.utilities.ConnectionStack3;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.DropDownSingle;

/**
 * @author twalto .
 * 
 *         Services class to obtain and return data to business objects.
 * 
 *         Process / Report / Retrieve Anything to do with Transactions
 */
public class ServiceTransaction extends BaseService {

	public static final String ttlib = "DBPRD";
	public static final String library = "M3DJDPRD";

	/**
	 * Constructor
	 */
	public ServiceTransaction() {
		super();
	}

	/**
	 * Main testing.
	 */
	public static void main(String[] args) {
		try {

			if ("x" == "x") {
				// test submitTrackAndTrace
				InqTransaction inq = new InqTransaction();
				inq.setRequestType("inqProductionDayForward");
				inq.setInqFacility("");
				inq.setInqDateYYYYMMDD("");
				inq.setInqItem("");
				inq.setOutQ("PRDP1");
				inq.setInqOrder("5102107");

				String stopHere = "yes";
				submitTrackAndTrace(inq);
				stopHere = "here";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Load class fields from result set.
	 */

	public static DateTime loadFieldsDateTime(String requestType, ResultSet rs) throws Exception {
		StringBuffer throwError = new StringBuffer();
		DateTime returnValue = new DateTime();

		try {
			if (requestType.equals("loadUsage")) {
				try {
					// BAPAHIST File
					returnValue.setM3FiscalYear(rs.getString("BAYEA4"));
					returnValue.setM3FiscalPeriod(rs.getString("BAWEEK"));
				} catch (Exception e) {
					// Problem Caught when loading the SalesOrderHeader/Detail
					// information
				}
			}
		} catch (Exception e) {
			throwError.append(" Problem loading the DateTime class ");
			throwError.append("from the result set. " + e);
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceTransaction.");
			throwError.append("loadFieldsDateTime(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

	/**
	 * Build an sql statement.
	 * 
	 * @param String
	 *            request type
	 * @param Vector
	 *            request class
	 * @return sql string
	 * @throws Exception
	 */

	private static String buildSqlStatement(String inRequestType, Vector requestClass) throws Exception {
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();

		try {
			if (inRequestType.equals("listAllYearPeriodUsage") || inRequestType.equals("list5DaysYearPeriodUsage")) {
				// DO NOT NEED the incoming Vector
				sqlString.append("SELECT BAYEA4, BAWEEK  ");
				sqlString.append("FROM " + ttlib + ".BAPAHIST ");
				sqlString.append(" WHERE SUBSTRING(BATRAN, 1,2) <> 'TO' ");
				if (inRequestType.equals("list5DaysYearPeriodUsage")) {
					try {
						DateTime dt1 = UtilityDateTime.getSystemDate();
						DateTime dt2 = UtilityDateTime.addDaysToDate(dt1, -5);
						sqlString.append("AND BARDTE >= " + dt2.getDateFormatyyyyMMdd() + " ");
					} catch (Exception e) {
						// System.out.println("Show only in Debug: " + e);
					}
				}
				sqlString.append("GROUP BY BAYEA4, BAWEEK ");
				sqlString.append("ORDER BY BAYEA4, BAWEEK ");
			}
			if (inRequestType.equals("findTransactionByItemLot")) {
				Transaction trans = (Transaction) requestClass.elementAt(0);
				sqlString.append("SELECT MTCONO, MTITNO, MTTTYP, MTBANO, MTRIDN, MTRIDL, MTTRTP  ");
				sqlString.append("FROM " + library + ".MITTRA ");
				sqlString.append(" WHERE MTCONO = '100' ");
				sqlString.append("   AND MTITNO = '" + trans.getItemNumber() + "' ");
				sqlString.append("   AND MTBANO = '" + trans.getLotNumber() + "' ");
				if (!trans.getTransactionType().trim().equals(""))
					sqlString.append("   AND MTTTYP = " + trans.getTransactionType().trim() + " ");
				sqlString.append("ORDER BY MTCONO, MTITNO, MTBANO, MTTTYP, MTRIDN DESC, MTRIDL DESC ");
			}
			if (inRequestType.equals("findHold")) {
				sqlString.append("SELECT MTCONO, MTWHLO, MTITNO, MTWHSL, MTBANO, ");
				sqlString.append("       MTSTAS, MTATNB, MTNHSN, MTRGDT, MTRGTM ");
				sqlString.append("FROM " + library + ".MITTRAZ10 ");
				sqlString.append("INNER JOIN " + ttlib + ".BAPVHIST ");
				sqlString.append("        ON MTNHSN = BVTRAN AND MTTTYP = BVTTYP ");
				sqlString.append("WHERE MTCONO = '100' ");
				sqlString.append("  AND MTTTYP = 96 ");
				sqlString.append("  AND BVHOLD = ' ' ");
				// Use for Testing
				// System.out.println("ServiceTransaction.buildSQLStatment remove Filter:");
				// sqlString.append("  AND MTWHLO = '209' ");
				// sqlString.append("  AND MTITNO = '100139' ");
				// sqlString.append("  AND MTBANO = 'F5102560' ");
				// order by
				sqlString.append("ORDER BY MTCONO, MTTTYP, MTITNO, MTWHLO, MTBANO, MTWHSL, MTNHSN ");
			}

			if (inRequestType.equals("buildDropDownFacility")) {
				sqlString.append("SELECT cffaci, cffacn ");
				sqlString.append("FROM " + library + ".cfacil ");
				sqlString.append("WHERE cfcono=100 and cffaci>'200' ");
				sqlString.append("ORDER BY cffaci");
			}

			// ----------------------------------------------------------------------------------
			// Delete Records
			// ----------------------------------------------------------------------------------
			if (inRequestType.equals("deleteYearPeriod")) {
				sqlString.append("DELETE FROM " + ttlib + ".WKPAUSAGE ");
				DateTime dt = (DateTime) requestClass.elementAt(0);
				sqlString.append("WHERE WKAYEAR = " + dt.getM3FiscalYear() + " ");
				sqlString.append("  AND WKAWEEK = " + dt.getM3FiscalPeriod() + " ");
			}
			// ---------------------------------------------------------------------------------
			// Insert Records
			// ---------------------------------------------------------------------------------
			if (inRequestType.equals("insertYearPeriod")) {
				DateTime dt = (DateTime) requestClass.elementAt(0);
				sqlString.append("INSERT INTO " + ttlib + ". WKPAUSAGE ");
				sqlString.append("(SELECT BAYEA4, BAWEEK, "); // YEAR // WEEK
				sqlString.append("'" + dt.getM3FiscalYear().trim());
				if (dt.getM3FiscalPeriod().trim().length() == 1)
					sqlString.append("0");
				sqlString.append(dt.getM3FiscalPeriod().trim() + "', "); // Year/Week
																			// Combo
				sqlString.append("MWFACI, MWWHLO, "); // Facility Warehouse
				sqlString.append("VHPRNO, B.MMITDS, "); // Produced Item and
														// Description
				sqlString.append("B.MMITTY, B.MMITCL, B.MMITGR, "); // Consumed
																	// Item
																	// Type/ProductLine/Item
																	// Group
				sqlString.append("MTITNO, A.MMITDS, "); // Consumed Item and
														// Description
				sqlString.append("A.MMITTY, A.MMITCL, A.MMITGR, "); // Consumed
																	// Item
																	// Type/ProductLine/Item
																	// Group
				sqlString.append("SUM(MTTRQT), A.MMUNMS, "); // Usage Qty -
																// Basic Unit of
																// Measure
				sqlString.append("SUM(BAALQT), BAALUN, "); // Alt Usage Qty -
															// Alt Unit of
															// Measure
				DateTime today = UtilityDateTime.getSystemDate();
				sqlString.append(today.getDateFormatyyyyMMdd() + ", "); // Today's
																		// Date
				sqlString.append(today.getTimeFormathhmmss() + ", "); // Today's
																		// Time
																		// (now)
				sqlString.append("'TreeNet' "); // Application used to Load Data
				sqlString.append("FROM " + library + ".MITTRAZ10 ");
				sqlString.append(" INNER JOIN " + ttlib + ".BALAHIST ");
				sqlString.append("    ON MTNHSN = BATRAN ");
				sqlString.append(" INNER JOIN " + library + ".MITWHL ");
				sqlString.append("    ON MTWHLO = MWWHLO ");
				sqlString.append(" INNER JOIN " + library + ".MITMAS A ");
				sqlString.append("    ON A.MMITNO = MTITNO ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".MWOHED ");
				sqlString.append("    ON MWFACI = VHFACI AND MTRIDN = VHMFNO ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".MITMAS B ");
				sqlString.append("    ON B.MMITNO = VHPRNO ");
				sqlString.append(" WHERE MTTTYP = 11 ");
				sqlString.append("   AND BAYEA4 = " + dt.getM3FiscalYear() + " ");
				sqlString.append("   AND BAWEEK = " + dt.getM3FiscalPeriod() + " ");
				sqlString.append(" GROUP BY BAYEA4, BAWEEK, MWFACI, MWWHLO, VHPRNO, ");
				sqlString
						.append("   B.MMITDS, B.MMITTY, B.MMITCL, B.MMITGR, BAALUN, MTITNO, A.MMITDS, A.MMITTY, A.MMITCL, A.MMITGR, A.MMUNMS ) ");
			}
			// ---------------------------------------------------------------------------------
			// Update Records
			// ---------------------------------------------------------------------------------
			if (inRequestType.equals("updateHold")) {
				sqlString.append("UPDATE " + ttlib + ".BAPVHIST ");
				sqlString.append("   SET BVHOLD = '" + requestClass.elementAt(1) + "' ");
				sqlString.append(" WHERE BVTRAN = '" + requestClass.elementAt(0) + "' ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement" + " for request type " + inRequestType + ". " + e);
		}
		// return data.
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceTransaction.");
			throwError.append("buildSqlStatement(String,Vector)");
			throw new Exception(throwError.toString());
		}

		return sqlString.toString();
	}

	/**
	 * Return a String for Updated Return Value
	 * 
	 * @param String
	 *            Envionment, UpdTransaction.
	 * @return String.
	 * @throws Exception
	 *             * @deprecated 1/26/12 - TWalton - No Longer Needed
	 */

	public static String DELETEupdateUsageWorkFile(UpdTransaction inClass) throws Exception {
		String rtnValue = "";
		StringBuffer throwError = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		try {

			// Determine Which Year/Period's will be Rebuilt -- Use DateTime
			// Class to load information
			Vector yearPeriod = new Vector();
			if (!inClass.getGoButton().trim().equals("")) { // Must have a Year
															// and a Week sent
															// in --ONE ONLY
				if (inClass.getFiscalWeek().equals("") || inClass.getFiscalYear().equals(""))
					rtnValue = "Please Choose a Year and Week.";
				else {
					DateTime dt = new DateTime();
					dt.setM3FiscalYear(inClass.getFiscalYear());
					dt.setM3FiscalPeriod(inClass.getFiscalWeek());
					yearPeriod.addElement(dt);
				}
			}
			if (!inClass.getGoDailyButton().trim().equals("")) {
				yearPeriod = listYearPeriodUsageReport("5days");
			}
			if (!inClass.getGoAllButton().trim().equals("")) {
				yearPeriod = listYearPeriodUsageReport("all");
			}

			if (yearPeriod.size() > 0) {
				conn = ConnectionStack.getConnection();
				System.out.println("Process " + yearPeriod.size() + " Year Period's");
				for (int x = 0; x < yearPeriod.size(); x++) {
					// Delete the Records for the ONE year - period
					// Send into the method using a Vector
					try {
						Vector sendToDelete = new Vector();
						sendToDelete.addElement((DateTime) yearPeriod.elementAt(x));
						// deleteUsageWorkFile(sendToDelete, conn);

					} catch (Exception e) {
						System.out.println("Only Display for Debug:" + e);
					}

					// INSERT THE NEW RECORDS
					try {
						Vector sendToInsert = new Vector();
						sendToInsert.addElement((DateTime) yearPeriod.elementAt(x));
						// insertUsageWorkFile(sendToInsert, conn);
					} catch (Exception e) {
						throwError.append("ERROR Found Inserting New Records: " + e);
					}
				}
				rtnValue = "Process is Complete! ";
				System.out.println("Process is Complete! ");
			}
		} catch (Exception e) {
			throwError.append(" Problem Processing Work File. " + e);
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
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceTransaction.");
			throwError.append("updateUsageWorkFile(String, UpdTransaction). ");
			throw new Exception(throwError.toString());
		}

		// return value
		return rtnValue;
	}

	/**
	 * Method Created 9/25/08 TWalton Use to get a Listing of Valid Year /
	 * Period(Week) from the BAPAHIST File
	 * 
	 * @param -- Not Needed
	 * @return Vector -- Filled with DateTime Objects
	 * @throws Exception
	 */
	public static Vector listYearPeriodUsageReport(String requestType) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector returnValue = new Vector();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ConnectionStack.getConnection();
			returnValue = listYearPeriodUsageReport(requestType, conn);
		} catch (Exception e) {
			throwError.append(e);
			System.out.println("ServiceTransaction.listYearPeriodUsageReport(" + requestType + ")::");
			System.out.println(" Error occured when getting Running Actual Process: " + e);
		} finally {
			if (conn != null) {
				try {
					ConnectionStack.returnConnection(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceTransaction.");
			throwError.append("listUsageReportYearPeriod(). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 * Method Created 9/25/08 TWalton Use to get a Listing of Valid Year /
	 * Period(Week) from the BAPAHIST File
	 * 
	 * @param -- ONLY the Connection
	 * @return Vector -- Filled with DateTime Objects
	 * @throws Exception
	 */
	private static Vector listYearPeriodUsageReport(String requestType, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector returnValue = new Vector();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try {
			// System.out.println("Got to this spot");
			try {
				// Get the list of Year and Period(Week) from the BAPAHIST FILE
				String sendType = requestType;
				if (sendType.equals("all"))
					sendType = "listAllYearPeriodUsage";
				if (sendType.equals("5days"))
					sendType = "list5DaysYearPeriodUsage";
				listThem = conn.prepareStatement(buildSqlStatement(sendType, returnValue));
				rs = listThem.executeQuery();
			} catch (Exception e) {
				throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
				System.out.println("ServiceTransaction.listYearPeriodUsageReport(" + requestType + ", conn)::");
				System.out.println(" Error occured when getting Sql Statement and running query: " + e);

			}
			if (throwError.toString().equals("")) {
				try {
					// int countRecords = 0;
					while (rs.next()) {
						// Build a Vector of Classes to Return
						try {
							// countRecords++;
							returnValue.addElement(loadFieldsDateTime("loadUsage", rs));
						} catch (Exception e) {
							throwError.append("Error occured While Processing sql statement. " + e);
						}
					}// END of the While Statement
						// System.out.println("Processed " + countRecords +
						// " records");
				} catch (Exception e) {
					throwError.append(" Error occured while Building Vector from sql statement. " + e);
				}
			}

		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
			if (listThem != null) {
				try {
					listThem.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceTransaction.");
			throwError.append("listYearPeriodUsageReport(conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 * Insert Records to the WKPAUSAGE File
	 * 
	 * @param Vector
	 *            (1st Element DateTime -- Use for Year and Period)
	 * @throws Exception
	 *             * @deprecated 1/26/12 - TWalton - No Longer needed
	 */
	private static void DELETEinsertUsageWorkFile(Vector inParms, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		PreparedStatement insertRecords = null;
		try {
			insertRecords = conn.prepareStatement(buildSqlStatement("insertYearPeriod", inParms));
			insertRecords.executeUpdate();
		} catch (Exception e) {
			throwError.append(" Error occured when Inserting Records. " + e);
		} finally {
			try {
				if (insertRecords != null)
					insertRecords.close();
			} catch (Exception el) {
				// el.printStackTrace();
			}
		}

		// return data.
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceTransaction.");
			throwError.append("insertUsageWorkFile(");
			throwError.append("Vector, Connection). ");
			throw new Exception(throwError.toString());
		}

		return;
	}

	/**
	 * Delete Records from the WKPAUSAGE File
	 * 
	 * @param Vector
	 *            (1st Element DateTime -- Use for Year and Period)
	 * @throws Exception
	 *             * @deprecated 1/26/12 - TWalton - No longer needed
	 */
	private static void DELETEdeleteUsageWorkFile(Vector inVector, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		PreparedStatement deleteRecords = null;
		try {
			deleteRecords = conn.prepareStatement(buildSqlStatement("deleteYearPeriod", inVector));
			deleteRecords.executeUpdate();
		} catch (Exception e) {
			System.out.println("Only care on Debug: " + e);
		} finally {
			try {
				if (deleteRecords != null)
					deleteRecords.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Method Created 2/12/08 TWalton // Use to control the information
	 * retrieval
	 * 
	 * @param -- Send in a Vector Transaction Business Object
	 * @return Transaction
	 * @throws Exception
	 */
	public static Transaction findTransactionByItemLot(Vector inValues) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Transaction returnValue = new Transaction();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack14();
			returnValue = findTransactionByItemLot(inValues, conn);
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (conn != null) {
				try {
					ServiceConnection.returnConnectionStack14(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceTransaction.");
			throwError.append("findTransactionByItemLot(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 * Method Created 2/12/09 TWalton // Use to control the information
	 * retrieval
	 * 
	 * @param -- Send in the Transaction BusinessObject for use in the SQL
	 *        statement
	 * @return Transaction
	 * @throws Exception
	 */
	private static Transaction findTransactionByItemLot(Vector inValues, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Transaction returnValue = new Transaction();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try {
			try {
				// got get the Transactions
				listThem = conn.prepareStatement(buildSqlStatement("findTransactionByItemLot", inValues));
				rs = listThem.executeQuery();
			} catch (Exception e) {
				throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			}
			try {
				if (rs.next()) {
					returnValue = loadFieldsTransaction("", rs);
				}// END of the IF Statement
			} catch (Exception e) {
				throwError.append(" Error occured while Building Vector from sql statement. " + e);
			}
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
			if (listThem != null) {
				try {
					listThem.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceTransaction.");
			throwError.append("findTransactionByItemLot(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 * Load class fields from result set.
	 */

	public static Transaction loadFieldsTransaction(String requestType, ResultSet rs) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Transaction returnValue = new Transaction();

		try {
			// MITTRA File
			returnValue.setCompany(rs.getString("MTCONO").trim());
			returnValue.setItemNumber(rs.getString("MTITNO").trim());
			returnValue.setTransactionType(rs.getString("MTTTYP").trim());
			returnValue.setLotNumber(rs.getString("MTBANO").trim());
			returnValue.setOrderNumber(rs.getString("MTRIDN").trim());
			returnValue.setOrderLineNumber(rs.getString("MTRIDL").trim());
			returnValue.setOrderType(rs.getString("MTTRTP").trim());

		} catch (Exception e) {
			throwError.append(" Problem loading the Transaction class ");
			throwError.append("from the result set. " + e);
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceTransaction.");
			throwError.append("loadFieldsTransaction(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

	/**
	 * Method Created 5/20/09 TWalton Use to Read the MITTRA file for Products
	 * put on Hold THEN, put a flag into the BAPAHIST File Flag = Blank (Needs
	 * Processing) Flag = 9 (Skip that Transaction) Flag = 1 (Chosen
	 * Transaction) Crystal report can then be used with the BAPAHIST File
	 * 
	 * @param -- requestType
	 * @return void -- program should just run, should not have to return
	 *         anything
	 * @throws Exception
	 */
	public static void determineHoldTransactions(String requestType) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector returnValue = new Vector();
		Connection conn = null;
		try {
			if (requestType.trim().equals(""))
				requestType = "findHold";
			// get a connection to be sent to find methods
			conn = ConnectionStack.getConnection();
			determineHoldTransactions(requestType, conn);
		} catch (Exception e) {
			throwError.append(e);
			System.out.println("ServiceTransaction.determineHoldTransactions(" + requestType + ")::");
			System.out.println(" Error occured when Running Actual Process: " + e);
		} finally {
			if (conn != null) {
				try {
					ConnectionStack.returnConnection(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceTransaction.");
			throwError.append("determineHoldTransactions(). ");
			throw new Exception(throwError.toString());
		}
		// return value -- nothing to return
		return;
	}

	/**
	 * Method Created 5/20/09 TWalton Use to Read the MITTRA file for Products
	 * put on Hold THEN, put a flag into the BAPAHIST File Flag = Blank (Needs
	 * Processing) Flag = 9 (Skip that Transaction) Flag = 1 (Chosen
	 * Transaction) Crystal report can then be used with the BAPAHIST File
	 * 
	 * @param -- requestType, Connection
	 * @return void -- program should just run, should not have to return
	 *         anything
	 * @throws Exception
	 */
	private static void determineHoldTransactions(String requestType, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		Statement listThem = null;
		Statement updIt = null;
		try {
			// System.out.println("Got to this spot");
			try {
				// Get the list of 96 Transactions to process
				// Within the Process will update the BAPAHIST File, Field:
				listThem = conn.createStatement();
				rs = listThem.executeQuery(buildSqlStatement(requestType, new Vector()));
			} catch (Exception e) {
				throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
				System.out.println("ServiceTransaction.determineHoldTransactions(" + requestType + ", conn)::");
				System.out.println(" Error occured when getting Sql Statement and running query: " + e);

			}
			if (throwError.toString().equals("")) {
				try {
					int countRecords = 0;
					int processCount = 0;
					String saveCONO = "";
					String saveWHLO = "";
					String saveITNO = "";
					String saveWHSL = "";
					String saveBANO = "";
					String saveRGDT = "";
					String saveRGTM = "";
					String saveSTAS = "";
					String saveATNB = ""; // Attribute Number for the Lot
					String saveNHSN = ""; // use to tie records to the BAPAHIST
											// File
					while (rs.next()) {
						// process TWO records at a time
						try {
							if (processCount == 0) {
								saveCONO = rs.getString("MTCONO");
								saveWHLO = rs.getString("MTWHLO");
								saveITNO = rs.getString("MTITNO");
								saveWHSL = rs.getString("MTWHSL");
								saveBANO = rs.getString("MTBANO");
								saveSTAS = rs.getString("MTSTAS");
								saveATNB = rs.getString("MTATNB");
								saveNHSN = rs.getString("MTNHSN");
								saveRGDT = rs.getString("MTRGDT");
								saveRGTM = rs.getString("MTRGTM");
							}
							if (processCount == 1) {
								if (saveCONO.equals(rs.getString("MTCONO")) && saveWHLO.equals(rs.getString("MTWHLO"))
										&& saveITNO.equals(rs.getString("MTITNO"))
										&& saveWHSL.equals(rs.getString("MTWHSL"))
										&& saveBANO.equals(rs.getString("MTBANO"))) {
									if (!saveSTAS.equals("3") && rs.getString("MTSTAS").equals("3")) {
										// System.out.println("Process Records, 1st Record with SKIP(9), 2nd with Chosen(1)");
										try {
											Vector sendForUpdate = new Vector();
											sendForUpdate.addElement(saveNHSN);
											// System.out.println("Update with 9: "
											// + saveNHSN);
											sendForUpdate.addElement("9");
											updIt = conn.createStatement();
											updIt.executeUpdate(buildSqlStatement("updateHold", sendForUpdate));
											updIt.close();
											sendForUpdate = new Vector();
											sendForUpdate.addElement(rs.getString("MTNHSN"));
											// System.out.println("Update with 1: "
											// + rs.getString("MTNHSN"));
											sendForUpdate.addElement("1");
											updIt = conn.createStatement();
											updIt.executeUpdate(buildSqlStatement("updateHold", sendForUpdate));
											updIt.close();

										} catch (Exception e) {
											System.out
													.println("Error Processing Records, 1st Record with SKIP(9), 2nd with Chosen(1)");
											System.out.println("Update with 9: " + saveNHSN);
											System.out.println("Update with 1: " + rs.getString("MTNHSN"));
											System.out.println("Update Hold Flag in BALAHIST. " + e);
										}
									} else {
										// System.out.println("Process Records, both with with SKIP(9)");
										try {
											Vector sendForUpdate = new Vector();
											sendForUpdate.addElement(saveNHSN);
											// System.out.println("Update with 9 (1st record): "
											// + saveNHSN);
											sendForUpdate.addElement("9");
											updIt = conn.createStatement();
											updIt.executeUpdate(buildSqlStatement("updateHold", sendForUpdate));
											updIt.close();
											sendForUpdate = new Vector();
											sendForUpdate.addElement(rs.getString("MTNHSN"));
											// System.out.println("Update with 9 (2nd record): "
											// + rs.getString("MTNHSN"));
											sendForUpdate.addElement("9");
											updIt = conn.createStatement();
											updIt.executeUpdate(buildSqlStatement("updateHold", sendForUpdate));
											updIt.close();

										} catch (Exception e) {
											System.out.println("Error Processing Records, both with with SKIP(9)");
											System.out.println("Update with 9 (1st record): " + saveNHSN);
											System.out.println("Update with 9 (2nd record): " + rs.getString("MTNHSN"));
											System.out.println("Update Hold Flag in BALAHIST. " + e);
										}
									}
									processCount = -1;
								} else {
									System.out.println("***********************************");
									System.out.println("Transaction: " + saveNHSN
											+ " should be reviewed, only ONE 96 Record");
									System.out.println(saveCONO);
									System.out.println(rs.getString("MTCONO"));
									System.out.println(saveWHLO);
									System.out.println(rs.getString("MTWHLO"));
									System.out.println(saveITNO);
									System.out.println(rs.getString("MTITNO"));
									System.out.println(saveWHSL);
									System.out.println(rs.getString("MTWHSL"));
									System.out.println(saveBANO);
									System.out.println(rs.getString("MTBANO"));
									System.out.println(saveSTAS);
									System.out.println(rs.getString("MTSTAS"));
									System.out.println(saveATNB);
									System.out.println(rs.getString("MTATNB"));
									System.out.println(saveNHSN);
									System.out.println(rs.getString("MTNHSN"));
									System.out.println(saveRGDT);
									System.out.println(rs.getString("MTRGDT"));
									System.out.println(saveRGTM);
									System.out.println(rs.getString("MTRGTM"));
									System.out.println("***********************************");
									try {
										// Mark with an 8 so it will NOT be
										// processed again
										Vector sendForUpdate = new Vector();
										sendForUpdate.addElement(saveNHSN);
										// System.out.println("Update with 8 (1st record): "
										// + saveNHSN);
										sendForUpdate.addElement("8");
										updIt = conn.createStatement();
										updIt.executeUpdate(buildSqlStatement("updateHold", sendForUpdate));
										updIt.close();
									} catch (Exception e) {
									}
									saveCONO = rs.getString("MTCONO");
									saveWHLO = rs.getString("MTWHLO");
									saveITNO = rs.getString("MTITNO");
									saveWHSL = rs.getString("MTWHSL");
									saveBANO = rs.getString("MTBANO");
									saveSTAS = rs.getString("MTSTAS");
									saveATNB = rs.getString("MTATNB");
									saveNHSN = rs.getString("MTNHSN");
									saveRGDT = rs.getString("MTRGDT");
									saveRGTM = rs.getString("MTRGTM");
									processCount = 0;
								}
							}
							processCount++;
							countRecords++;
						} catch (Exception e) {
							throwError.append("Error occured While Processing sql statement. " + e);
						}
					}// END of the While Statement
					System.out.println("Processed " + countRecords + " records - Hold Flag");
				} catch (Exception e) {
					throwError.append(" Error occured while Updating Records Vector from sql statement. " + e);
				}
			}

		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
			if (listThem != null) {
				try {
					listThem.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
			if (updIt != null) {
				try {
					updIt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceTransaction.");
			throwError.append("determineHoldTransactions(conn). ");
			throw new Exception(throwError.toString());
		}
		// return value -- will be returning a void
		return;
	}

	/**
	 * This returns HTML for spool file data for screen presentation
	 * 
	 * @param InqTransaction
	 * @return Vector<String>
	 * @throws Exception
	 */

	public static Vector<String> submitTrackAndTrace(InqTransaction it) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector<String> spooledData = new Vector<String>();

		if (it.getInqEnvironment() == null || it.getInqEnvironment().trim().equals("")) {
			it.setInqEnvironment("PRD");
		}

		// AS400 system = ConnectionStack.getAS400Object();
		// AS400 system = new AS400("lawson.treetop.com","DAUSER","WEB230502");
		AS400 system = new AS400("lawson.treetop.com", "KKEIFE", "COUGAR");

		try {
			spooledData = submitTrackAndTrace(it, system);

		} catch (Exception e) {
			throwError.append(e);
		} finally {
			try {
				// ConnectionStack.returnAS400Object(system);
				system.disconnectService(AS400.COMMAND);
			} catch (Exception e) {
				System.out.println("Error disconnecting AS400 system:  " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append(" @ com.treetop.Services.");
				throwError.append("ServiceTransaction.");
				throwError.append("submitTrackAndTrace(InqTransaction). ");
				throw new Exception(throwError.toString());
			}
		}

		return spooledData;
	}

	/**
	 * Builds spooled data from RPG program.
	 * 
	 * @param InqTransaction
	 * @param AS400
	 * @return Vector
	 * @throws Exception
	 */

	private static Vector submitTrackAndTrace(InqTransaction it, AS400 system) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector<String> returnValue = new Vector<String>();
		String fileName = null;

		User u = ServiceUser.loadUserOutQ(new User(it.getInqUser().trim()));
		it.setOutQ(u.getOutQ().trim());
		it.setOutQDescr(u.getOutQDescr());

		ProgramCall pgm = new ProgramCall(system);
		String pgmLib = "MOVEX";
		String pgmName = "";
		ProgramParameter[] parmList = null;

		try {
			if (it.requestType.equals("inqProductionDayForwardOS")) {
				//TODO get back to PRD
				pgmName = "INC172";
				parmList = new ProgramParameter[2];
				parmList[0] = new ProgramParameter(new AS400Text(30).toBytes(it.getInqManufactureLabel()));
				parmList[1] = new ProgramParameter(10);
			}

			if (it.requestType.equals("inqSingleIngredientForward")) {
				//TODO get back to PRD
				pgmName = "INC152T";
				parmList = new ProgramParameter[3];
				parmList[0] = new ProgramParameter(new AS400Text(15).toBytes(it.getInqItem()));
				parmList[1] = new ProgramParameter(new AS400Text(20).toBytes(it.getInqLot()));
				parmList[2] = new ProgramParameter(10);
			}

			if (it.getRequestType().equals("inqProductionDayBack")
					|| it.getRequestType().equals("inqProductionDayForward")
					|| it.getRequestType().equals("inqFruitToShipping")) {

				if (it.getRequestType().equals("inqProductionDayBack")) {
					//TODO get back to PRD
					pgmName = "INC163T";
				}
				if (it.getRequestType().equals("inqProductionDayForward")) {
					//TODO get back to PRD
					pgmName = "INC157T";
				}
				if (it.getRequestType().equals("inqFruitToShipping")) {
					//TODO get back to PRD
					pgmName = "INC166T";
				}

				if (!it.getInqOrder().equals("")) {
					it.setInqFacility("");
					it.setInqDateYYYYMMDD("");
					it.setInqItem("");
				}

				parmList = new ProgramParameter[5];
				parmList[0] = new ProgramParameter(new AS400Text(3).toBytes(it.getInqFacility()));
				parmList[1] = new ProgramParameter(new AS400Text(8).toBytes(it.getInqDateYYYYMMDD()));
				parmList[2] = new ProgramParameter(new AS400Text(15).toBytes(it.getInqItem()));
				parmList[3] = new ProgramParameter(new AS400Text(10).toBytes(it.getInqOrder()));
				parmList[4] = new ProgramParameter(10);
			}

			String pgmPath = "/QSYS.LIB/" + pgmLib + ".LIB/" + pgmName + ".PGM";
			pgm.setProgram(pgmPath, parmList);

			if (pgm.run() != true) {
				System.out.println("Program failed!");
				throwError.append("Request not Submitted:  ");
				AS400Message[] messagelist = pgm.getMessageList();
				
				returnValue.addElement("<h1>Error submitting Track and Trace Report.</h1>" + "\r");
				returnValue.addElement("<h3>Please contact the IS Help Desk at x1425 with the following information.</h3>" + "\r");
				
				returnValue.addElement("<p>Requesting:\t\t" + it.getRequestType() + "</p>\r");
				
				for (AS400Message message : pgm.getMessageList()) {
					System.out.println("Error:");
					System.out.println("\tDefault reply= " + message.getDefaultReply());
					System.out.println("\tFile Name= " + message.getFileName());
					System.out.println("\tHelp= " + message.getHelp());
					System.out.println("\tID= " + message.getID());
					System.out.println("\tLibrary Name= " + message.getLibraryName());
					System.out.println("\tPath= " + message.getPath());
					System.out.println("\tSeverity= " + message.getSeverity());
					System.out.println("\tText= " + message.getText());
					System.out.println("\tType= " + message.getType());
					System.out.println("\tDate= " + message.getDate());
					returnValue.addElement("<div>" + message.getText() + "<br>\r");
					returnValue.addElement(message.getHelp() + "</div>\r");
				}
				
				/*for (int i = 0; i < messagelist.length; ++i) {
					System.out.println(messagelist[i]);
					throwError.append(messagelist[i]);
					
				}*/
				
				
				
			} else {

				if (it.requestType.equals("inqProductionDayForwardOS")) {
					AS400Text rtntext = new AS400Text(10, system);
					fileName = (String) rtntext.toObject(parmList[1].getOutputData());

				} else if (it.requestType.equals("inqSingleIngredientForward")) {
					AS400Text rtntext = new AS400Text(10, system);
					fileName = (String) rtntext.toObject(parmList[2].getOutputData());

				} else {
					AS400Text rtntext = new AS400Text(10, system);
					fileName = (String) rtntext.toObject(parmList[4].getOutputData());
				}

				returnValue = GeneralUtility.getSpoolFileData(it.getInqEnvironment(), fileName, true);

			}

		} catch (Exception e) {
			throwError.append(e);
			System.out.println(throwError.toString());
		} finally {
			/*if (!throwError.toString().equals("")) {
				throw new Exception(throwError.toString());
			}*/
		}

		return returnValue;
	}

	/**
	 * Build DropDownSingle for Facility
	 * 
	 * @param environment
	 * @param inValues
	 * @return
	 */
	public static Vector dropDownFacility(String environment, Vector inValues) {
		Vector ddit = new Vector();
		Connection conn = null;
		StringBuffer throwError = new StringBuffer();

		if (environment == null || environment.trim().equals(""))
			environment = "PRD";

		try { // get a connection to be sent to find methods
			conn = ConnectionStack3.getConnection();
			ddit = dropDownItem(environment, inValues, conn);
		} catch (Exception e) {
			throwError.append("Error executing method. " + e);
		} finally {

			try {
				if (conn != null)
					ConnectionStack3.returnConnection(conn);
			} catch (Exception e) {
				System.out.println("Error closing a connection for Stack 3 -- ServiceTransactionError.dropDownItem: "
						+ e);
				throwError.append("Error returning connection. " + e);
			}

			// Log any errors.
			if (!throwError.toString().equals("")) {
				throwError.append("Error @ com.treetop.services.");
				throwError.append("ServiceTransactionError.");
				throwError.append("dropDownItem(String, Vector). ");
				System.out.println(throwError.toString());
				Exception e = new Exception();
				e.printStackTrace();
			}
		}
		return ddit;

	}

	private static Vector dropDownItem(String environment, Vector inValues, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector ddit = new Vector();
		ResultSet rs = null;
		Statement listThem = null;

		try {
			try { // Get the list of Item Type Values - Along with Descriptions
				listThem = conn.createStatement();
				rs = listThem.executeQuery(buildSqlStatement("buildDropDownFacility", inValues));
			} catch (Exception e) {
				throwError.append("Error occured Retrieving or Executing sql (Facility). " + e);
			}

			if (throwError.toString().equals("")) {
				try {
					while (rs.next() && throwError.toString().equals("")) {
						DropDownSingle dds = loadFieldsDropDownSingle("buildDropDownFacility", rs);
						ddit.addElement(dds);
					}
				} catch (Exception e) {
					throwError.append(" Error occured while Building Vector from sql (Item). " + e);
				}
			}
		} catch (Exception e) {
			throwError.append(e);
		}

		finally {

			// close result sets / statements.
			try {
				if (rs != null)
					rs.close();

				if (listThem != null)
					listThem.close();

			} catch (Exception e) {
				throwError.append("Error closing result set / statement." + e);
			}

			// Log any errors.
			if (!throwError.toString().trim().equals("")) {
				throwError.append(" @ com.treetop.Services.");
				throwError.append("ServiceTransactionError.");
				throwError.append("dropDownItem(");
				throwError.append("String, Vector, conn). ");
				System.out.println(throwError.toString());
				throw new Exception(throwError.toString());
			}
		}

		// return value
		return ddit;
	}

	/**
	 * Load class fields from result set.
	 */

	public static DropDownSingle loadFieldsDropDownSingle(String requestType, ResultSet rs) throws Exception {
		StringBuffer throwError = new StringBuffer();
		DropDownSingle returnValue = new DropDownSingle();

		try {
			if (requestType.equals("buildDropDownFacility")) {
				// HHPTDATA, DPNUSER
				returnValue.setValue(rs.getString("cffaci").trim());
				returnValue.setDescription(rs.getString("cffacn").trim());
			}

		} catch (Exception e) {
			throwError.append(" Problem loading the Drop Down Single class ");
			throwError.append("from the result set. " + e);
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceTransactionError.");
			throwError.append("loadFieldsDropDownSingle(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

}
