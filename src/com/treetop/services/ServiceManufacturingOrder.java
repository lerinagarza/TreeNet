/*
 * Created on September 14, 2009
 * Author:  Teri Walton
 * Data files accessed.
 */
package com.treetop.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;
import com.lawson.api.MHS850MI;
import com.lawson.api.MHS850MIAddMOReceipt;
import com.lawson.api.MMS850MI;
import com.lawson.api.MMS850MIAddMove;
import com.treetop.app.inventory.UpdProduction;
import com.treetop.app.inventory.UpdProductionLot;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.Lot;
import com.treetop.businessobjects.ManufacturingOrder;
import com.treetop.businessobjects.Warehouse;
import com.treetop.utilities.ConnectionStack;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.HTMLHelpersMasking;
import com.treetop.viewbeans.CommonRequestBean;

/**
 * @author twalton
 * 
 *         Service to access Movex(M3) Manufacturing Order Information.
 */
public class ServiceManufacturingOrder extends BaseService {

	public static final String library = "M3DJD";
	public static final String ttlib = "DB";

	/**
	 * 
	 */
	public ServiceManufacturingOrder() {
		super();
	}

	/**
	 * Main testing.
	 */
	public static void main(String[] args) {
		try {
		} catch (Exception e) {
			System.out.println(e);
		}
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
	private static String buildSqlStatement(String environment, String inRequestType, Vector requestClass)
			throws Exception {
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();

		try {
			// *********************************************************************************
			// Verify Supplier
			// *********************************************************************************
			if (inRequestType.equals("verifyManufacturingOrder")) {
				// build the sql statement.
				sqlString.append("SELECT  VHMFNO  ");
				sqlString.append("FROM " + library + environment + ".MWOHED ");
				sqlString.append(" WHERE VHMFNO = " + (String) requestClass.elementAt(0) + " ");
			}
			if (inRequestType.equals("verifyActiveMO")) {
				// build the sql statement.
				sqlString.append("SELECT  VHMFNO  ");
				sqlString.append("FROM " + library + environment + ".MWOHED ");
				sqlString.append(" WHERE VHMFNO = " + (String) requestClass.elementAt(0) + " ");
				sqlString.append(" AND VHWHST > 10 AND VHWHST < 90 ");
			}
			if (inRequestType.equals("verifyActiveMOWithItem")) {
				// build the sql statement.
				sqlString.append("SELECT  VHMFNO  ");
				sqlString.append("FROM " + library + environment + ".MWOHED ");
				sqlString.append(" WHERE VHMFNO = " + (String) requestClass.elementAt(0) + " ");
				sqlString.append(" AND VHPRNO = '" + (String) requestClass.elementAt(1) + "' ");
				sqlString.append(" AND VHWHST > 10 AND VHWHST < 90 ");
			}
			if (inRequestType.equals("verifyLocationOnOrder")) {
				// build the sql statement.
				sqlString.append("SELECT  VHWHSL, MSWHSL  ");
				sqlString.append("FROM " + library + environment + ".MWOHED ");
				sqlString.append("LEFT OUTER JOIN " + library + environment + ".MITPCE ");
				sqlString.append("  ON VHCONO = MSCONO AND VHWHLO = MSWHLO ");
				sqlString.append("  AND VHWHSL = MSWHSL ");
				sqlString.append(" WHERE VHMFNO = " + (String) requestClass.elementAt(0) + " ");
				sqlString.append(" AND VHPRNO = '" + (String) requestClass.elementAt(1) + "' ");
				sqlString.append(" AND VHWHST > 10 AND VHWHST < 90 ");
			}
			if (inRequestType.equals("verifyManufacturingOrderAndPartner")) {
				// build the sql statement.
				sqlString.append("SELECT  VHMFNO  ");
				sqlString.append("FROM " + library + environment + ".MWOHED ");
				sqlString.append("INNER JOIN " + library + environment + ".MMIPPT ");
				sqlString.append("   ON VHCONO = I7CONO AND I7WHLO = VHWHLO ");
				sqlString.append("  AND I7E0IO = 'I' AND I7E0PA = 'WHI' ");
				sqlString.append(" WHERE VHMFNO = " + (String) requestClass.elementAt(0) + " ");
				sqlString.append(" AND VHWHST > 10 AND VHWHST < 90 ");
			}
			// **********************************************************************************
			// Find one record
			// **********************************************************************************
			if (inRequestType.equals("findMO")) {
				UpdProduction up = (UpdProduction) requestClass.elementAt(0);
				sqlString.append("SELECT VHCONO, VHMFNO, VHITNO, VHFACI, VHWHLO, ");
				sqlString.append("       VHWHSL, VHWHST, VHRSDT, VHMAQA, ");
				sqlString.append("       MBWHLO, MBITNO, MBWHSL, MBFACI, ");
				sqlString.append("       MMITNO, MMITDS, MMUNMS, MMACRF, CTTX40, ");
				sqlString.append("       MUITNO, MUAUTP, MUALUN, MUCOFA, ");
				sqlString.append("       VOTXT2, VOSCHS ");
				sqlString.append(" FROM " + library + environment + ".MWOHED ");
				sqlString.append(" INNER JOIN " + library + environment + ".MITBAL ");
				sqlString.append("     ON MBCONO = VHCONO AND MBWHLO = VHWHLO ");
				sqlString.append("    AND MBITNO = VHITNO ");
				sqlString.append(" INNER JOIN " + library + environment + ".MITMAS ");
				sqlString.append("     ON MMCONO = VHCONO AND MMITNO = VHITNO ");
				sqlString.append(" LEFT OUTER JOIN " + library + environment + ".MITAUN ");
				sqlString.append("     ON MUCONO = VHCONO AND MUITNO = VHITNO ");
				sqlString.append("    AND MUAUTP = 1 AND MUALUN = 'PL' ");
				sqlString.append(" LEFT OUTER JOIN " + library + environment + ".CSYTAB ");
				sqlString.append("     ON CTCONO = VHCONO AND CTSTCO = 'ACRF' AND CTSTKY = MMACRF ");
				sqlString.append(" LEFT OUTER JOIN " + library + environment + ".MWOOPE ");
				sqlString.append("     ON VOCONO = VHCONO AND VOFACI = VHFACI ");
				sqlString.append("    AND VOMFNO = VHMFNO AND VOOPNO = 10 ");
				sqlString.append(" WHERE VHCONO = 100 AND VHMFNO = " + up.getManufacturingOrder() + " ");
				sqlString.append(" ORDER BY VHMFNO, VHITNO ");
			}

		} catch (Exception e) {
			throwError.append(" Error building sql statement for request type " + inRequestType + ". " + e);
		}
		// return data.
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceManufacturingOrder.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		return sqlString.toString();
	}

	/**
	 * Use to Validate a Sent in Movex (M3) Manufacturing Order Number // Check
	 * in file MWOHED
	 */
	public static String verifyOrderNumber(String environment, String manufacturingOrderNumber) throws Exception {
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try {
			String requestType = "verifyManufacturingOrder";
			String sqlString = "";

			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Manufacturing Order Number
			if (manufacturingOrderNumber == null || manufacturingOrderNumber.trim().equals(""))
				rtnProblem.append(" Movex Manufacturing Order Number must not be null or empty.");

			if (throwError.toString().equals("")) {
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(manufacturingOrderNumber);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnProblem.append(" Problem when building Test for Manufacturing Order Number: ");
					rtnProblem.append(manufacturingOrderNumber
							+ " PrintScreen this message and send to Information Services. ");
				}
			}
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					conn = ConnectionStack.getConnection();

					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();

					if (rs.next()) {
						// it exists and all is good.
						// Add in a test to see if the Partner is valid

					} else {
						rtnProblem.append(" Movex Manufacturing Order Number '" + manufacturingOrderNumber + "' ");
						rtnProblem.append("does not exist. ");
					}

				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Movex Manufacturing Order Number: ");
					rtnProblem.append(manufacturingOrderNumber
							+ " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch (Exception e) {
			throwError.append(" Problem verifying the Movex Manufacturing Order Number; " + manufacturingOrderNumber);
			// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						ConnectionStack.returnConnection(conn);
					if (rs != null)
						rs.close();
					if (findIt != null)
						findIt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceManufacturingOrder.");
			throwError.append("verifyOrderNumber(String: Envrionment, String: manufacturingOrderNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

	/**
	 * Method Created 9/16/09 TWalton // Use to control the information
	 * retrieval
	 * 
	 * @param -- Send in the UpdProduction Object for use in the SQL statement
	 * @return ManufacturingOrder
	 * @throws Exception
	 */
	public static ManufacturingOrder findMO(String environment, Vector inValues) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ManufacturingOrder returnValue = new ManufacturingOrder();
		Connection conn = null;
		if (environment == null || environment.trim().equals(""))
			environment = "PRD";
		try {
			// get a connection to be sent to find methods
			conn = getDBConnection();
			returnValue = findMO(environment, inValues, conn);
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceManufacturingOrder.");
			throwError.append("findMO(String, Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 * Method Created 9/16/09 TWalton // Use to control the information
	 * retrieval
	 * 
	 * @param -- Send in the UpdProduction Object for use in the SQL statement
	 * @return ManufacturingOrder
	 * @throws Exception
	 */
	private static ManufacturingOrder findMO(String environment, Vector inValues, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ManufacturingOrder returnValue = new ManufacturingOrder();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try {
			try {
				// Get the list of DataSet File Names - Along with Descriptions
				listThem = conn.prepareStatement(buildSqlStatement(environment, "findMO", inValues));
				rs = listThem.executeQuery();
			} catch (Exception e) {
				throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			}
			try {
				if (rs.next()) // ONLY get 1 record
				{
					returnValue = loadFieldsManufacturingOrder("loadMO", rs);
					try {
						UpdProduction up = (UpdProduction) inValues.elementAt(0);
						returnValue.setLine("1");
						returnValue.setShift("1");
					} catch (Exception e) {
					}
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
			throwError.append("ServiceManufacturingOrder.");
			throwError.append("findMO(");
			throwError.append("String, Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 * Load class fields from result set. for the COA Business Object.
	 */

	public static ManufacturingOrder loadFieldsManufacturingOrder(String requestType, ResultSet rs) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ManufacturingOrder returnMO = new ManufacturingOrder();

		try {
			if (requestType.equals("loadMO")) {
				returnMO.setCompanyNo(rs.getString("VHCONO").trim());
				returnMO.setOrderNumber(rs.getString("VHMFNO").trim());
				if (rs.getString("VHWHSL").trim().equals(""))
					returnMO.setDefaultLocation(rs.getString("MBWHSL").trim());
				else
					returnMO.setDefaultLocation(rs.getString("VHWHSL").trim());
				try {
					returnMO.setWarehouse(ServiceWarehouse.loadFieldsWarehouse(requestType, rs));
				} catch (Exception e) {
				}
				try {
					returnMO.setItem(ServiceItem.loadFieldsItem(requestType, rs));
				} catch (Exception e) {
				}
				try {
					returnMO.setLine(rs.getString("VOTXT2").trim());
					returnMO.setShift(rs.getString("VOSCHS"));
				} catch (Exception e) {
				}
				if (returnMO.getLine() == null || returnMO.getLine().trim().equals(""))
					returnMO.setLine("1");
				if (returnMO.getShift() == null || returnMO.getShift().trim().equals(""))
					returnMO.setShift("1");
				// returnMO.setActualStartDate(UtilityDateTime.getDateFromyyyyMMdd(rs.getString("VHRSDT")));
				returnMO.setProduction(rs.getBigDecimal("VHMAQA"));
			}
		} catch (Exception e) {
			throwError.append(" Problem loading the ManufacturingOrder class from the result set. " + e);
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceManufacturingOrder.");
			throwError.append("loadFieldsManufacturingOrder(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnMO;
	}

	/**
	 * Use to Validate a Sent in Movex (M3) Manufacturing Order Number // Check
	 * in file MWOHED for Status > 10 and < 90
	 */
	public static String verifyActiveOrderNumber(String environment, String manufacturingOrderNumber) throws Exception {
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try {
			String requestType = "verifyActiveMO";
			String sqlString = "";

			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Manufacturing Order Number
			if (manufacturingOrderNumber == null || manufacturingOrderNumber.trim().equals(""))
				rtnProblem.append(" Movex Manufacturing Order Number must not be null or empty.");

			if (throwError.toString().equals("")) {
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(manufacturingOrderNumber);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnProblem.append(" Problem when building Test for Manufacturing Order Number: ");
					rtnProblem.append(manufacturingOrderNumber
							+ " PrintScreen this message and send to Information Services. ");
				}
			}
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					conn = ConnectionStack.getConnection();

					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();

					if (rs.next()) {
						// it exists and all is good.
					} else {
						rtnProblem.append(verifyOrderNumber(environment, manufacturingOrderNumber));
						if (rtnProblem.equals("")) {
							rtnProblem.append(" Movex Manufacturing Order Number '" + manufacturingOrderNumber + "' ");
							rtnProblem
									.append("is not in a valid Status to work with.  Status must be >10 and Less than 90. ");
						}
					}

				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Movex Manufacturing Order Number: ");
					rtnProblem.append(manufacturingOrderNumber
							+ " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch (Exception e) {
			throwError.append(" Problem verifying the Movex Manufacturing Order Number; " + manufacturingOrderNumber);
			// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						ConnectionStack.returnConnection(conn);
					if (rs != null)
						rs.close();
					if (findIt != null)
						findIt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceManufacturingOrder.");
			throwError.append("verifyActiveOrderNumber(String: environment, String: manufacturingOrderNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

	public static void processProduction(UpdProduction updProduction) throws Exception {

		StringBuffer throwError = new StringBuffer();
		String environment = updProduction.getEnvironment();
		if (environment == null || environment.trim().equals("")) {
			environment = "PRD";
		}

		try {

			
			Vector<MHS850MIAddMOReceipt> adds = new Vector<MHS850MIAddMOReceipt>();
			Vector<MMS850MIAddMove> moves = new Vector<MMS850MIAddMove>();
			String facility = "";
			String lotRef1 = "";
			String lotRef2 = "";

			for (UpdProductionLot lot : (Vector<UpdProductionLot>) updProduction.getListLots()) {

				// Create Production Record for Lot

				if (facility.equals("")) {
				
					Vector parms = new Vector();
					parms.addElement(lot.getWarehouse());
					Warehouse w = ServiceWarehouse.findWarehouse(updProduction.getEnvironment(), parms);
					facility = w.getFacility();
					
				}
				
				DateTime mfgDate = UtilityDateTime.getDateFromMMddyyyyWithSlash(lot.getManufactureDate());
				mfgDate.setTimeFormathhmm("0000");
				
				//Calculate code date
				CommonRequestBean crb = new CommonRequestBean();
				crb.setEnvironment(updProduction.getEnvironment());
				crb.setCompanyNumber("100");
				crb.setIdLevel1(lot.getItemNumber());
				crb.setIdLevel2(lot.getWarehouse());
				crb.setIdLevel3(facility);
				crb.setIdLevel4("001");
				crb.setIdLevel5("001");
				crb.setDateTime(mfgDate);

				Vector<String> returnValues = UtilityDateTime.findLotCodeDate(crb);
	
				if (returnValues.size() == 3) {
					lotRef1 = returnValues.elementAt(1);
					lotRef2 = returnValues.elementAt(2);
				}

				
				MHS850MIAddMOReceipt add = buildMHS850MIAddMOReceipt(updProduction, lot, facility, lotRef1, lotRef2, mfgDate);

				adds.addElement(add);

				// Create Movement to Location for Lot
				if (!lot.getDefaultWarehouse().equals(lot.getWarehouse())
						|| !lot.getDefaultLocation().equals(lot.getLocation())) { // Create a movement Transaction

					MMS850MIAddMove move = buildMMS850MIAddMove(updProduction, lot, facility, lotRef1, lotRef2, mfgDate);
					
					moves.addElement(move);

					// System.out.println("Will Move the Inventory");
				}

			}

			Vector<String> productionErrors = MHS850MI.addMOReceipt(adds, updProduction.getAuthorization());
			Vector<String> transferErrors = MMS850MI.addMove(moves, updProduction.getAuthorization());
			
			
			Vector<String> palletErrors = new Vector<String>();
			if (updProduction.getDefaultWarehouse().equals("360")) {
				palletErrors = transferPallets(updProduction);
			}
			
			StringBuffer errors = new StringBuffer();
			for (String error : productionErrors) {
				errors.append(error + "<br>");
			}
			for (String error : transferErrors) {
				errors.append(error + "<br>");
			}
			for (String error : palletErrors) {
				errors.append(error + "<br>");
			}
			
			updProduction.setProcessLotsError(errors.toString());
			

		} catch (Exception e) {
			throwError.append(e);
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceManufacturingOrder.");
			throwError.append("processProduction(UpdProduction). ");
			throw new Exception(throwError.toString());
		}
		

	}
	
	private static MHS850MIAddMOReceipt buildMHS850MIAddMOReceipt(
			UpdProduction updProduction, 
			UpdProductionLot lot, 
			String facility,
			String lotRef1,
			String lotRef2,
			DateTime mfgDate) throws Exception {
		
		
		
		
		MHS850MIAddMOReceipt add = new MHS850MIAddMOReceipt();
				
		add.setSentFromProgram("ShortCutProduction");
		add.setEnvironment(lot.getEnvironment().trim());
		add.setUserProfile(lot.getUpdateUser());

		add.setCompany("100");
		add.setWarehouse(lot.getWarehouse());
		add.setItemNumber(lot.getItemNumber());
		add.setLocation(lot.getLocation().trim());
		add.setLotNumber(lot.getLot());
		add.setQuantityReceived(lot.getQuantity());
		add.setOrderNumber(updProduction.getManufacturingOrder());

		add.setLotReference1(lotRef1);
		add.setLotReference2(lotRef2);
		
		add.setReportingDate(mfgDate.getDateFormatyyyyMMdd().trim());
		add.setReportingTime(mfgDate.getTimeFormathhmm().trim());
		
		return add;
		
	}
	
	private static MMS850MIAddMove buildMMS850MIAddMove(
			UpdProduction updProduction, 
			UpdProductionLot lot, 
			String facility,
			String lotRef1,
			String lotRef2,
			DateTime mfgDate) throws Exception {
	
		
		
		MMS850MIAddMove move = new MMS850MIAddMove();
		move.setSentFromProgram("ShortCutProduction");
		move.setUserProfile(lot.getUpdateUser());
		move.setEnvironment(lot.getEnvironment());

		move.setProcess("");
		move.setCompany("100");

		move.setTransactionDate(mfgDate.getDateFormatyyyyMMdd());
		move.setTransactionTime(mfgDate.getTimeFormathhmmss());

		move.setWarehouse(lot.getWarehouse());
		move.setWarehouseLocation(lot.getDefaultLocation());

		move.setItemNumber(lot.getItemNumber());
		move.setLotNumber(lot.getLot());

		move.setReceivingNumber("");

		move.setQuantity(lot.getQuantity());

		move.setToLocation(lot.getLocation());

		move.setRemark("");
		
		
		move.setLotReference1(lotRef1);
		move.setLotReference2(lotRef2);
		
		return move;
		
	}

	private static Vector<String> transferPallets(UpdProduction updProduction) throws Exception {

		DateTime now = UtilityDateTime.getSystemDate();

		Vector<MMS850MIAddMove> moves = new Vector<MMS850MIAddMove>();

		for (int pal = 0; pal < 2; pal++) { // Process Through both Chep and
											// Peco Pallets to be Transfered

			MMS850MIAddMove move = new MMS850MIAddMove();
			move.setSentFromProgram("ShortCutProduction");
			move.setUserProfile(updProduction.getUpdateUser());
			move.setEnvironment(updProduction.getEnvironment());

			move.setProcess("");
			move.setCompany("100");

			move.setTransactionDate(now.getDateFormatyyyyMMdd());
			move.setTransactionTime(now.getTimeFormathhmmss());

			move.setWarehouse(updProduction.getDefaultWarehouse());

			move.setReceivingNumber("");
			move.setRemark("");
			
			if (!updProduction.getBillOfLading().equals("0")
					&& !updProduction.getBillOfLading().equals("")) {
				move.setLotReference1(updProduction.getBillOfLading());
			}
			move.setLotReference2("");
			
			
			
			if (pal == 0 && !updProduction.getChepPallets().equals("0") && !updProduction.getChepPallets().equals("")) { // 0 = Chep
				move.setItemNumber("1005001001"); // CHEP PALLET ITEM NUMBER
				move.setWarehouseLocation("01005");		//from
				move.setToLocation("00005");			// to
				move.setQuantity(updProduction.getChepPallets());
				moves.addElement(move);	
			}

			if (pal == 1 && !updProduction.getPecoPallets().equals("0") && !updProduction.getPecoPallets().equals("")) { // 1 =  Peco
				move.setItemNumber("1000001055"); // PECO PALLET ITEM NUMBER
				move.setWarehouseLocation("01006");		//from
				move.setToLocation("00006");			// to
				move.setQuantity(updProduction.getPecoPallets());
				moves.addElement(move);	
			}

						
		}	//end for loop
		
		return MMS850MI.addMove(moves, updProduction.getAuthorization());

	}

	
	

	/**
	 * Use to Validate a Sent in Movex (M3) Manufacturing Order Number // Check
	 * in file MWOHED -- Must have a valid produced item as well
	 */
	public static String verifyActiveOrderNumberWithItem(String environment, String manufacturingOrderNumber,
			String itemNumber) throws Exception {
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try {
			String requestType = "verifyActiveMOWithItem";
			String sqlString = "";

			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Manufacturing Order Number
			if (manufacturingOrderNumber == null || manufacturingOrderNumber.trim().equals(""))
				rtnProblem.append(" Movex Manufacturing Order Number must not be null or empty.");
			if (manufacturingOrderNumber == null || manufacturingOrderNumber.trim().equals(""))
				rtnProblem.append(" Movex Item Number must not be null or empty.");

			if (throwError.toString().equals("") && rtnProblem.toString().equals("")) {
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(manufacturingOrderNumber);
					parmClass.addElement(itemNumber);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnProblem.append(" Problem when building Test for Manufacturing Order Number: ");
					rtnProblem.append(manufacturingOrderNumber + " along with Item Number: ");
					rtnProblem.append(itemNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					conn = ConnectionStack.getConnection();

					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();

					if (rs.next()) {
						// it exists and all is good.
					} else {
						rtnProblem.append(" The Combination Manufacturing Order Number '" + manufacturingOrderNumber
								+ "' ");
						rtnProblem.append(" Item Number '" + itemNumber + "' ");
						rtnProblem.append("does not exist. ");
					}

				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Movex Manufacturing Order Number: ");
					rtnProblem.append(manufacturingOrderNumber + " along with Item Number: ");
					rtnProblem.append(itemNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch (Exception e) {
			throwError.append(" Problem verifying the Movex Manufacturing Order Number; " + manufacturingOrderNumber);
			throwError.append("  Item Number: " + itemNumber);
			// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						ConnectionStack.returnConnection(conn);
					if (rs != null)
						rs.close();
					if (findIt != null)
						findIt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceManufacturingOrder.");
			throwError.append("verifyOrderNumber(String: Envrionment, String: manufacturingOrderNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

	/**
	 * Use to Validate a Sent in Movex (M3) Manufacturing Order Number // Check
	 * in file MWOHED -- Must have a valid produced item as well
	 */
	public static String verifyLocationOnOrder(String environment, String manufacturingOrderNumber, String itemNumber)
			throws Exception {
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try {
			String requestType = "verifyLocationOnOrder";
			String sqlString = "";

			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Manufacturing Order Number
			if (manufacturingOrderNumber == null || manufacturingOrderNumber.trim().equals(""))
				rtnProblem.append(" Movex Manufacturing Order Number must not be null or empty.");
			if (manufacturingOrderNumber == null || manufacturingOrderNumber.trim().equals(""))
				rtnProblem.append(" Movex Item Number must not be null or empty.");

			if (throwError.toString().equals("") && rtnProblem.toString().equals("")) {
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(manufacturingOrderNumber);
					parmClass.addElement(itemNumber);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnProblem.append(" Problem when building Test for Location within a Manufacturing Order Number: ");
					rtnProblem.append(manufacturingOrderNumber
							+ " PrintScreen this message and send to Information Services. ");
				}
			}
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					conn = ConnectionStack.getConnection();

					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();

					if (rs.next()) {
						if (rs.getString("VHWHSL").trim().equals("")) {
							rtnProblem.append("A Location has not been entered in Manufacturing Order Number: "
									+ manufacturingOrderNumber);
							rtnProblem
									.append(" Please enter a Location into the Movex(M3) Manufacturing Order and try again. ");
						} else {
							if (rs.getString("MSWHSL") == null) {
								rtnProblem
										.append("An invalid Location has not been entered in Manufacturing Order Number: "
												+ manufacturingOrderNumber);
								rtnProblem.append(" Location: " + rs.getString("VHWHSL").trim());
								rtnProblem
										.append(" Please enter a valid Location into the Movex(M3) Manufacturing Order and try again. ");
							}
						}
					}

				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding the Location for Movex Manufacturing Order Number: ");
					rtnProblem.append(manufacturingOrderNumber + " along with Item Number: ");
					rtnProblem.append(itemNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch (Exception e) {
			throwError.append(" Problem verifying the Location attached to the Movex Manufacturing Order Number; "
					+ manufacturingOrderNumber);
			throwError.append("  Item Number: " + itemNumber);
			// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						ConnectionStack.returnConnection(conn);
					if (rs != null)
						rs.close();
					if (findIt != null)
						findIt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceManufacturingOrder.");
			throwError
					.append("verifyLocationOnOrder(String: Envrionment, String: manufacturingOrderNumber, String: itemNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

	/**
	 * Use to Validate a Sent in Movex (M3) Manufacturing Order Number // Check
	 * in file MWOHED
	 */
	public static String verifyPartner(String environment, String manufacturingOrderNumber) throws Exception {
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try {
			String requestType = "verifyManufacturingOrderAndPartner";
			String sqlString = "";

			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify Warehouse assigned to the incoming Manufacturing Order
			// Number has a partner setup so that the information can be input
			// through the web
			if (manufacturingOrderNumber == null || manufacturingOrderNumber.trim().equals(""))
				rtnProblem.append(" Movex Manufacturing Order Number must not be null or empty.");

			if (throwError.toString().equals("")) {
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(manufacturingOrderNumber);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnProblem.append(" Problem when building Test for Manufacturing Order Number tied to a Partner: ");
					rtnProblem.append(manufacturingOrderNumber
							+ " PrintScreen this message and send to Information Services. ");
				}
			}
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					conn = ConnectionStack.getConnection();

					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();

					if (rs.next()) {
						// it exists and all is good.
						// Add in a test to see if the Partner is valid

					} else {
						rtnProblem.append(" Partner does not exist for Movex Manufacturing Order Number '"
								+ manufacturingOrderNumber + "' ");
						rtnProblem
								.append("does not exist. Review the Warehouse Number for the MO, and contact Information Services.  Program to add a Partner is MMS865.");
					}

				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Movex Manufacturing Order Number: ");
					rtnProblem.append(manufacturingOrderNumber
							+ " tied to Partner. PrintScreen this message and send to Information Services. ");
				}
			}
		} catch (Exception e) {
			throwError.append(" Problem verifying the Movex Manufacturing Order Number; " + manufacturingOrderNumber
					+ " tied to Partner");
			// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						ConnectionStack.returnConnection(conn);
					if (rs != null)
						rs.close();
					if (findIt != null)
						findIt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceManufacturingOrder.");
			throwError.append("verifyPartner(String: Envrionment, String: manufacturingOrderNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

}
