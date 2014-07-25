/*
 * Created on November 6, 2008
 * 
 */
package com.treetop.services;

import java.sql.*;
import java.text.*;
import java.util.*;

import com.ibm.as400.access.*;
import com.treetop.businessobjects.Warehouse;
import com.treetop.utilities.html.DropDownDual;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HtmlOption;
import com.treetop.utilities.*;
import com.treetop.viewbeans.CommonRequestBean;

/**
 * @author twalto .
 *
 * Services class to obtain and return data 
 * to business objects.
 */
public class ServiceWarehouse extends BaseService {

	//	 9/18/09 - TWalton Changed the Library to only be:
	//   M3DJD, the Environment is being sent into anywhere the 
	//  Library is used, the Environment will be Tacked onto the 
	//   End of the Library Values
	//	public static final String library = "M3DJD";
	//	public static final String ttlib = "DB";

	/**
	 * 
	 */
	public ServiceWarehouse() {
		super();
	}

	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		try
		{
			Vector d = new Vector();
			d = dropDownProductionFacility(new CommonRequestBean("PRD"));
			System.out.println(d);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Build an sql statement.
	 * @param String request type
	 * @param Vector request class
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

		String library = GeneralUtility.getLibrary(environment);
		String ttlibrary = GeneralUtility.getTTLibrary(environment);

		try { 
			// **********************************************************************************
			//	VERIFY WAREHOUSE INFORMATION		
			// Verify Warehouse
			if (inRequestType.equals("verifyWarehouse"))
			{
				// build the sql statement.
				sqlString.append("SELECT  MWWHLO  ");
				sqlString.append("FROM " + library + ".MITWHL ");
				sqlString.append(" WHERE MWWHLO = '" + (String) requestClass.elementAt(0) + "' ");
			}
			if (inRequestType.equals("verifyLocation"))
			{
				// build the sql statement.
				sqlString.append("SELECT  MSWHSL  ");
				sqlString.append("FROM " + library + ".MITPCE ");
				sqlString.append(" WHERE MSWHLO = '" + (String) requestClass.elementAt(0) + "' ");
				sqlString.append("   ANd MSWHSL = '" + (String) requestClass.elementAt(1) + "' ");
			}
			// **********************************************************************************
			//			 DROP DOWN LISTS FROM ITEM MASTER INFORMATION
			if (inRequestType.equals("ddRFWhseFacility"))
			{  // Only Raw Fruit Inventories -- 
				//   Items MUST be in the Item Warehouse and Item Facility Records
				//   WILL use to create Dual Drop Down List
				sqlString.append(" SELECT MBFACI, CFFACN, MBWHLO, MWWHNM ");
				sqlString.append(" FROM " + library + ".MITBAL ");
				sqlString.append(" INNER JOIN " + library + ".MITMAS ");
				sqlString.append("    ON MBITNO = MMITNO ");
				sqlString.append(" INNER JOIN " + library + ".MITWHL ");
				sqlString.append("    ON MBWHLO = MWWHLO ");
				sqlString.append(" INNER JOIN " + library + ".CFACIL ");
				sqlString.append("    ON MBFACI = CFFACI ");
				sqlString.append(" WHERE (MMITTY = '200' OR MMITTY = '205') ");
				sqlString.append(" GROUP BY MBFACI, CFFACN, MBWHLO, MWWHNM ");
				sqlString.append(" ORDER BY MBFACI, MBWHLO ");
			}
			
			if (inRequestType.equals("productionWarehouse"))
			{  	//return production warehouse that have MOs against them
				sqlString.append(" SELECT MWWHLO, MWWHNM ");
				sqlString.append(" FROM " + library + ".MITWHL ");
				sqlString.append(" INNER JOIN " + library + ".MWOHED ON ");
				sqlString.append(" MWCONO=VHCONO AND MWWHLO=VHWHLO ");
				sqlString.append(" WHERE MWCONO = 100 ");
				sqlString.append(" AND MWWHTY='10' ");
				sqlString.append(" GROUP BY MWWHLO, MWWHNM ");
				sqlString.append(" ORDER BY MWWHLO ");
				
			}
			
			if (inRequestType.equals("specItemProductionWarehouse"))
			{  	//return production warehouse that have items with specs against it
				sqlString.append(" SELECT DISTINCT MWWHLO, MWWHNM ");
				sqlString.append(" FROM " + ttlibrary + ".QAPGSPHD ");
				sqlString.append(" INNER JOIN " + library + ".MITBAL ON ");
				sqlString.append("       SHCONO=MBCONO AND MBITNO=SHITNO AND MBPUIT='1' ");
				sqlString.append(" INNER JOIN " + library + ".MITWHL ON ");
				sqlString.append("       SHCONO=MWCONO AND MBWHLO=MWWHLO ");
				sqlString.append("       AND MWWHTY='10' ");
				sqlString.append(" INNER JOIN " + library + ".MPDHED ON ");
				sqlString.append("       SHCONO = PHCONO AND MBFACI = PHFACI");
				sqlString.append("       AND MBITNO = PHPRNO AND PHSTAT <> '90' ");
				sqlString.append(" WHERE SHCONO=100 AND SHDIVI='100' ");
				sqlString.append(" ORDER BY MWWHLO ");
			}

			if (inRequestType.equals("ddRFInventory"))
			{  // Only Raw Fruit Inventories -- 
				//   Items MUST be in the Item Warehouse and Item Facility Records
				//   WILL use to create Dual Drop Down List
				sqlString.append(" SELECT MBFACI, CFFACN, MBWHLO, MWWHNM ");
				sqlString.append(" FROM " + library + ".MITBAL ");
				sqlString.append(" INNER JOIN " + library + ".MITMAS ");
				sqlString.append("    ON MBITNO = MMITNO ");
				sqlString.append(" INNER JOIN " + library + ".MITWHL ");
				sqlString.append("    ON MBWHLO = MWWHLO ");
				sqlString.append(" INNER JOIN " + library + ".CFACIL ");
				sqlString.append("    ON MBFACI = CFFACI ");
				sqlString.append(" INNER JOIN " + library + ".MITLOC ");
				sqlString.append("    ON MBFACI = MLFACI AND MBWHLO = MLWHLO ");
				sqlString.append("   AND MBITNO = MLITNO ");
				sqlString.append(" WHERE (MMITTY = '200' OR MMITTY = '205') ");
				sqlString.append(" GROUP BY MBFACI, CFFACN, MBWHLO, MWWHNM ");
				sqlString.append(" ORDER BY MBFACI, MBWHLO ");
			}
			if (inRequestType.equals("ddRFWarehouse"))
			{  // Only Raw Fruit Inventories -- 
				//   Items MUST be in the Item Warehouse
				//   WILL use to create Single Drop Down List
				sqlString.append(" SELECT MBWHLO, MWWHNM ");
				sqlString.append(" FROM " + library + ".MITBAL ");
				sqlString.append(" INNER JOIN " + library + ".MITMAS ");
				sqlString.append("    ON MBITNO = MMITNO ");
				sqlString.append(" INNER JOIN " + library + ".MITWHL ");
				sqlString.append("    ON MBWHLO = MWWHLO ");
				sqlString.append(" WHERE (MMITTY = '200' OR MMITTY = '205') ");
				sqlString.append(" GROUP BY MBWHLO, MWWHNM ");
				sqlString.append(" ORDER BY MBWHLO ");
			}
			if (inRequestType.equals("ddRFLoad"))
			{  // Only Raw Fruit Scale Tickets -- 
				//   Items MUST be in the Scale Ticket File
				//   WILL use to create Dual Drop Down List
				sqlString.append(" SELECT MWFACI, CFFACN, MWWHLO, MWWHNM ");
				sqlString.append(" FROM " + ttlibrary + ".GRPCSCALE ");
				sqlString.append(" INNER JOIN " + library + ".MITWHL ");
				sqlString.append("    ON G1WHSE = MWWHLO ");
				sqlString.append(" INNER JOIN " + library + ".CFACIL ");
				sqlString.append("    ON G1FACI = CFFACI ");
				sqlString.append(" GROUP BY MWFACI, CFFACN, MWWHLO, MWWHNM ");
				sqlString.append(" ORDER BY MWFACI, MWWHLO ");
			}
			if (inRequestType.equals("ddPFList"))
			{  // Only Plant Floor Facility Warehouse -- 
				//   WILL use to create Dual Drop Down List
				sqlString.append(" SELECT MWFACI, CFFACN, MWWHLO, MWWHNM ");
				sqlString.append(" FROM " + ttlibrary + ".PFPFDATA ");
				sqlString.append(" INNER JOIN " + library + ".MITWHL ");
				sqlString.append("    ON PFFWHLO = MWWHLO ");
				sqlString.append(" INNER JOIN " + library + ".CFACIL ");
				sqlString.append("    ON PFFFACI = CFFACI ");
				sqlString.append(" GROUP BY MWFACI, CFFACN, MWWHLO, MWWHNM ");
				sqlString.append(" ORDER BY MWFACI, MWWHLO ");
			}
			if (inRequestType.equals("dddInventory"))
			{  // All Inventories -- 
				//   Items MUST be in the Item Warehouse and Item Facility Records
				//   WILL use to create Dual Drop Down List
				sqlString.append(" SELECT MWFACI, CFFACN, MWWHLO, MWWHNM ");
				sqlString.append(" FROM " + library + ".MITLOC ");
				sqlString.append(" INNER JOIN " + library + ".MITWHL ");
				sqlString.append("    ON MLWHLO = MWWHLO ");
				sqlString.append(" INNER JOIN " + library + ".CFACIL ");
				sqlString.append("    ON MLFACI = CFFACI ");
				try{
					if (requestClass.size() == 2)
					{
						if (!((String) requestClass.elementAt(1)).equals(""))
						{
							sqlString.append(" WHERE MLFACI = '" + (String) requestClass.elementAt(1) + "' ");
						}
					}
				}catch(Exception e){}
				sqlString.append(" GROUP BY MWFACI, CFFACN, MWWHLO, MWWHNM ");
				sqlString.append(" ORDER BY MWFACI, MWWHLO ");
			}

			if (inRequestType.equals("ddProductionFacility")) {
				sqlString.append(" SELECT CFFACI, CFFACN ");
				sqlString.append(" FROM " + library + ".CFACIL ");
				sqlString.append(" INNER JOIN  " + library + ".MPDHED ");
				sqlString.append(" ON CFCONO=PHCONO AND CFFACI=PHFACI AND PHSTAT='20' ");
				sqlString.append(" WHERE CFCONO=100 ");
				sqlString.append(" GROUP BY CFFACI, CFFACN ");
				sqlString.append(" ORDER BY CFFACI ");

			}

			// **********************************************************************************
			//	FIND WAREHOUSE INFORMATION		
			// Retrieve 1 WAREHOUSE - Based on Warehouse Number Sent In
			//  Include Address Type 1 - Final Destination or Good Receiving Address
			if (inRequestType.equals("basicWarehouse"))
			{
				// cast the incoming parameter class.
				String warehouseNumber = (String) requestClass.elementAt(0);

				sqlString.append(" SELECT MWFACI, CFFACN, MWWHLO, MWWHNM, ");
				sqlString.append("    OACONM, OAADR1, OAADR2, OAADR3, OAADR4, ");
				sqlString.append("    OATOWN, OAECAR, OAPONO ");
				sqlString.append(" FROM " + library + ".MITWHL ");
				sqlString.append(" INNER JOIN " + library + ".CFACIL ");
				sqlString.append("    ON MWCONO = CFCONO AND MWFACI = CFFACI ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".CIADDR ");
				sqlString.append("    ON MWCONO = OACONO AND MWWHLO = OAADK2 AND OAADTH = 1 ");
				sqlString.append("   AND OAADK3 = '' ");
				sqlString.append(" WHERE MWCONO = '100' AND MWWHLO = '" + warehouseNumber.trim() + "' ");
			}		
			// **********************************************************************************
			//  UPDATE
			// **********************************************************************************
			//  INSERT
			// **********************************************************************************
			//  DELETE

		} catch (Exception e) {
			throwError.append(" Error building sql statement"
					+ " for request type " + inRequestType + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceWarehouse.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}

		return sqlString.toString();
	}

	/**
	 *   Method Created 11/06/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
	 *  Incoming Vector:
	 *    Element 1 = String - Values:
	 *      ddRFWhseFacility -- Dual Drop down of Warehouse Faciliti assigned to Raw Fruit 
	 * @return Vector - of DropDownDual 
	 */
	public static Vector dualDropDownWarehouseFacility(String environment, 
			Vector inValues)
	{
		Vector ddd = new Vector();
		Connection conn = null;
		if (environment == null || environment.trim().equals(""))
			environment = "PRD";
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack5();
			ddd = dualDropDownWarehouseFacility(environment, inValues, conn);
		} catch (Exception e)
		{
			// Don't really need to worry about any exceptions
		}
		finally {
			if (conn != null)
			{
				try
				{
					ServiceConnection.returnConnectionStack5(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// return value
		return ddd;
	}

	/**
	 * Method Created 11/06/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
	 *     Current Types: ddRFWhseFacility  
	 * @return Vector - of DropDownDual - Utility
	 */
	private static Vector dualDropDownWarehouseFacility(String environment, 
			Vector inValues, 
			Connection conn)
	{
		Vector ddd = new Vector();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try
		{
			try {
				// Get the list of Item Type Values - Along with Descriptions
				listThem = conn.prepareStatement(buildSqlStatement(environment, (String) inValues.elementAt(0), inValues));
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
					DropDownDual ddDual = loadFieldsDropDownDual((String) inValues.elementAt(0), rs);
					ddd.addElement(ddDual);
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
		//		throwError.append("ServiceWarehouse.");
		//		throwError.append("dualDropDownWarehouseFacility(");
		//		throwError.append("Vector, conn). ");
		//		throw new Exception(throwError.toString());
		//	}
		// return value
		return ddd;
	}
	/**
	 * Load class fields from result set.
	 */

	public static DropDownDual loadFieldsDropDownDual(String requestType,
			ResultSet rs)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		DropDownDual returnValue = new DropDownDual();
		try{ 
			if (requestType.equals("ddRFWhseFacility") ||
					requestType.equals("ddRFInventory"))
			{
				returnValue.setMasterValue(rs.getString("MBFACI").trim());
				returnValue.setMasterDescription(rs.getString("CFFACN").trim());
				returnValue.setSlaveValue(rs.getString("MBWHLO").trim());
				returnValue.setSlaveDescription(rs.getString("MWWHNM").trim());
			}
			if (requestType.equals("ddRFLoad") ||
					requestType.equals("dddInventory") ||
					requestType.equals("ddPFList"))
			{
				returnValue.setMasterValue(rs.getString("MWFACI").trim());
				returnValue.setMasterDescription(rs.getString("CFFACN").trim());
				returnValue.setSlaveValue(rs.getString("MWWHLO").trim());
				String slaveDesc = FindAndReplace.remove(rs.getString("MWWHNM").trim(), "'");
				//			slaveDesc = FindAndReplace.remove(slaveDesc, "#");
				//			slaveDesc = FindAndReplace.remove(slaveDesc, "&");
				returnValue.setSlaveDescription(slaveDesc.trim());
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the DualDropDown class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceWarehouse.");
			throwError.append("loadFieldsDropDownDual(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}
	
	
	public static Vector<HtmlOption> dropDownProductionWarehouse(String environment) 
	throws Exception {
		
		Vector<HtmlOption> options = null;
		
		Connection conn = null;
		if (environment == null || environment.trim().equals("")) {
			environment = "PRD";
		}
		
		try {
			
			conn = ServiceConnection.getConnectionStack5();
			options = dropDownProductionWarehouse(environment, conn);
			
		} catch (Exception e) {
			System.err.println("Exception @ ServiceWarehouse.dropDownProductionWarehouse(environment).  " + e);
		} finally {
			if (conn != null) {
				ServiceConnection.returnConnectionStack5(conn);
			}
		}
		
		return options;
	}
	
	private static Vector<HtmlOption> dropDownProductionWarehouse(String environment, Connection conn)
	throws Exception {
		Vector<HtmlOption> options = new Vector<HtmlOption>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			String requestType = "productionWarehouse";
			
			String sql = buildSqlStatement(environment, requestType, null);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				options.addElement(loadFieldsHtmlOption(requestType, rs));
			}
			
			
		} catch (Exception e) {
			
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return options;
	}
	
	public static Vector<HtmlOption> dropDownSpecItemProductionWarehouse(String environment) 
	throws Exception {
		
		Vector<HtmlOption> options = null;
		
		Connection conn = null;
		if (environment == null || environment.trim().equals("")) {
			environment = "PRD";
		}
		
		try {
			
			conn = ServiceConnection.getConnectionStack5();
			options = dropDownSpecItemProductionWarehouse(environment, conn);
			
		} catch (Exception e) {
			System.err.println("Exception @ ServiceWarehouse.dropDownSpecItemProductionWarehouse(environment).  " + e);
		} finally {
			if (conn != null) {
				ServiceConnection.returnConnectionStack5(conn);
			}
		}
		
		return options;
	}
	
	private static Vector<HtmlOption> dropDownSpecItemProductionWarehouse(String environment, Connection conn)
	throws Exception {
		Vector<HtmlOption> options = new Vector<HtmlOption>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {

			
			String sql = buildSqlStatement(environment, "specItemProductionWarehouse", null);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				options.addElement(loadFieldsHtmlOption("productionWarehouse", rs));
			}
			
			
		} catch (Exception e) {
			
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return options;
	}

	/**
	 *   Method Created 12/30/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
	 *  Incoming Vector:
	 *    Element 1 = String - Values: Current Types: ddRFWarehouse  
	 * @return Vector - of DropDownSingle 
	 */
	public static Vector dropDownWarehouse(String environment, 
			Vector inValues)
	{
		Vector ddd = new Vector();
		Connection conn = null;
		if (environment == null || environment.trim().equals(""))
			environment = "PRD";
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack5();
			ddd = dropDownWarehouse(environment, inValues, conn);
		} catch (Exception e)
		{
			// Don't really need to worry about any exceptions
		}
		finally {
			if (conn != null)
			{
				try
				{
					ServiceConnection.returnConnectionStack5(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// return value
		return ddd;
	}

	/**
	 * Method Created 12/30/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
	 *     Current Types: ddRFWarehouse  
	 * @return Vector - of DropDownSingle - Utility
	 */
	private static Vector dropDownWarehouse(String environment, 
			Vector inValues, 
			Connection conn)
	{
		Vector ddd = new Vector();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try
		{
			try {
				// Get the list of Item Type Values - Along with Descriptions
				listThem = conn.prepareStatement(buildSqlStatement(environment, (String) inValues.elementAt(0), inValues));
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
					DropDownSingle ddSingle = loadFieldsDropDownSingle((String) inValues.elementAt(0), rs);
					ddd.addElement(ddSingle);
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
		//		throwError.append("ServiceWarehouse.");
		//		throwError.append("dualDropDownWarehouseFacility(");
		//		throwError.append("Vector, conn). ");
		//		throw new Exception(throwError.toString());
		//	}
		// return value
		return ddd;
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
			if (requestType.equals("ddRFWarehouse"))
			{
				returnValue.setValue(rs.getString("MBWHLO").trim());
				returnValue.setDescription(rs.getString("MWWHNM").trim());
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the DropDownSingle class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceWarehouse.");
			throwError.append("loadFieldsDropDownSingle(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

	public static HtmlOption loadFieldsHtmlOption(String requestType, ResultSet rs) throws Exception {
		StringBuffer throwError = new StringBuffer();
		HtmlOption o = new HtmlOption();

		if (requestType.equals("ddProductionFacility")) {
			try {
				o.setValue(rs.getString("CFFACI").trim());
			} catch (Exception e) {
				throwError.append(e);
			}
			try {
				o.setDescription(rs.getString("CFFACN").trim());
			} catch (Exception e) {
				throwError.append(e);
			}
		}
		
		
		if (requestType.equals("productionWarehouse")) {
			try {
				o.setValue(rs.getString("MWWHLO").trim());
			} catch (Exception e) {
				throwError.append(e);
			}
			try {
				o.setDescription(rs.getString("MWWHNM").trim());
			} catch (Exception e) {
				throwError.append(e);
			}
		}
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceWarehouse.");
			throwError.append("loadFieldsHtmlOption(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return o;
	}

	/**
	 *   Method Created 12/30/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in the String for use in the SQL statement
	 *    
	 * @return Warehouse 
	 * @throws Exception
	 */
	public static Warehouse findWarehouse(String environment,
			Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Warehouse returnValue = new Warehouse();
		Connection conn = null;
		if (environment == null || environment.trim().equals(""))
			environment = "PRD";
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack5();
			returnValue = findWarehouse(environment, inValues, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
					ServiceConnection.returnConnectionStack5(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceWarehouse.");
			throwError.append("findWarehouse(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *    Method Created 10/15/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in the InqCodeDate Object for use in the SQL statement
	 * @return ItemWarehouse
	 * @throws Exception
	 */
	private static Warehouse findWarehouse(String environment,
			Vector inValues, 
			Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Warehouse returnValue = new Warehouse();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try
		{
			try {
				// Get the list of DataSet File Names - Along with Descriptions
				listThem = conn.prepareStatement(buildSqlStatement(environment, "basicWarehouse", inValues));
				rs = listThem.executeQuery();
			} catch(Exception e)
			{
				throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			}		
			try
			{
				if (rs.next())
				{
					returnValue = loadFieldsWarehouse("basicWarehouse", rs);
				}// END of the IF Statement
			} catch(Exception e)
			{
				throwError.append(" Error occured while Building Vector from sql statement. " + e);
			} 		
		} catch (Exception e)
		{
			throwError.append(e);
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
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceWarehouse.");
			throwError.append("findWarehouse(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 * Load class fields from result set.
	 */

	public static Warehouse loadFieldsWarehouse(String requestType,
			ResultSet rs)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Warehouse returnValue = new Warehouse();
		try{ 
			if (requestType.equals("basicWarehouse"))
			{
				if (rs.getString("MWFACI") != null)
				{
					returnValue.setFacility(rs.getString("MWFACI"));
					returnValue.setWarehouse(rs.getString("MWWHLO").trim());
					returnValue.setWarehouseDescription(rs.getString("MWWHNM").trim());
				}
				if (rs.getString("CFFACN") != null)	
					returnValue.setFacilityDescription(rs.getString("CFFACN"));
			}

			if (requestType.equals("rfLoad"))
			{
				returnValue.setFacility(rs.getString("G1FACI"));
				returnValue.setWarehouse(rs.getString("G1WHSE").trim());
				try
				{  // Facility
					if (rs.getString("ECFFACN") != null)
						returnValue.setFacilityDescription(rs.getString("ECFFACN"));
				}
				catch(Exception e)
				{}
				try
				{  // Warehouse
					if (rs.getString("CMWWHNM") != null)
						returnValue.setWarehouseDescription(rs.getString("CMWWHNM"));
				}
				catch(Exception e)
				{}
			}	
			if (requestType.equals("rfPO"))
			{
				returnValue.setFacility(rs.getString("G3FACI"));
				returnValue.setWarehouse(rs.getString("G3WHSE").trim());
				try
				{ // Facility
					if (rs.getString("FCFFACN") != null)
						returnValue.setFacilityDescription(rs.getString("FCFFACN"));
				}
				catch(Exception e)
				{}
				try
				{  // Warehouse
					if (rs.getString("DMWWHNM") != null)
						returnValue.setWarehouseDescription(rs.getString("DMWWHNM"));
				}
				catch(Exception e)
				{}
			}	
			if (requestType.equals("rfEntryLot"))
			{
				returnValue.setFacility(rs.getString("G4FACI"));
				returnValue.setWarehouse(rs.getString("G4WHSE").trim());
				try
				{
					returnValue.setFacilityDescription(rs.getString("CFFACN"));
				}
				catch(Exception e)
				{}
				try
				{
					returnValue.setWarehouseDescription(rs.getString("MWWHNM").trim());
				}
				catch(Exception e)
				{}
			}
			if (requestType.equals("loadMO"))
			{
				returnValue.setFacility(rs.getString("VHFACI").trim());
				returnValue.setWarehouse(rs.getString("VHWHLO").trim());
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Warehouse class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		//	if (!throwError.toString().equals("")) {
		//		throwError.append("Error @ com.treetop.services.");
		//		throwError.append("ServiceWarehouse.");
		//		throwError.append("loadFieldsWarehouse(requestType, rs: ");
		//		throwError.append(requestType + "). ");
		//		throw new Exception(throwError.toString());
		//	}
		return returnValue;
	}

	/**
	 * Added Method: 9/14/09 - Teri Walton
	 * Use to Validate:
	 * 	 Send in Warehouse and location
	 *   Return Message if Location is not found - else Message would be Blank
	 */
	public static String verifyLocation(String environment,
			String warehouse,
			String location)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyLocation";
			String sqlString = "";

			// verify base class initialization.
			ServiceWarehouse a = new ServiceWarehouse();

			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// 	verify incoming Warehouse
			if (warehouse == null || warehouse.trim().equals(""))
				rtnProblem.append(" Warehouse must not be null or empty.");
			//  verify incoming Location
			if (location == null || location.trim().equals(""))
				rtnProblem.append(" Location must not be null or empty.");

			// 	get Test Location Info.
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(warehouse);
					parmClass.addElement(location);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnProblem.append(" Problem when building Test for ");
					rtnProblem.append(" Warehouse: " + warehouse );
					rtnProblem.append(" Location: " + location );		
					rtnProblem.append(" PrintScreen this message and send to Information Services. ");
				}
			}
			//			 get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					conn = ServiceConnection.getConnectionStack5();

					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();

					if (rs.next())
					{
						//		 it exists and all is good.
					} else {
						rtnProblem.append(" Location '" + location + "' ");
						rtnProblem.append("does not exist in Warehouse: " + warehouse + ". ");
					}

				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding ");
					rtnProblem.append(" Warehouse: " + warehouse );
					rtnProblem.append(" Location: " + location );		
					rtnProblem.append(" PrintScreen this message and send to Information Services. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Testing the Location ");
			throwError.append(location + " for Warehouse " + warehouse + " ");
			throwError.append("from the result set. " + e) ;
			// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						ServiceConnection.returnConnectionStack5(conn);
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
			throwError.append("ServiceLot.");
			throwError.append("verifyLocation(String: Warehouse, String: Location)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

	/**
	 * Added Method: 9/17/09 - Teri Walton
	 * Use to Validate:
	 * 	 Send in Warehouse 
	 *   Return Message if Warehouse is not found - else Message would be Blank
	 */
	public static String verifyWarehouse(String environment,
			String warehouse)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyWarehouse";
			String sqlString = "";

			// verify base class initialization.
			ServiceWarehouse a = new ServiceWarehouse();

			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// 	verify incoming Warehouse
			if (warehouse == null || warehouse.trim().equals(""))
				rtnProblem.append(" Warehouse must not be null or empty.");

			// 	get Test Location Info.
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(warehouse);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnProblem.append(" Problem when building Test for ");
					rtnProblem.append(" Warehouse: " + warehouse );	
					rtnProblem.append(" PrintScreen this message and send to Information Services. ");
				}
			}
			//			 get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					conn = ServiceConnection.getConnectionStack5();

					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();

					if (rs.next())
					{
						//		 it exists and all is good.
					} else {
						rtnProblem.append(" Warehouse '" + warehouse + "' does not exist. ");
					}

				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding ");
					rtnProblem.append(" Warehouse: " + warehouse );
					rtnProblem.append(" PrintScreen this message and send to Information Services. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Testing the Warehouse " + warehouse + " ");
			throwError.append("from the result set. " + e) ;
			// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						ServiceConnection.returnConnectionStack5(conn);
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
			throwError.append("ServiceWarehouse.");
			throwError.append("verifyWarehouse(String: Warehouse)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

	/**
	 * Build vector of HtmlOptions for production facilities
	 * @param crb  Set environment
	 * @return
	 * @throws Exception
	 */
	public static Vector dropDownProductionFacility(CommonRequestBean crb) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector<HtmlOption> returnValue = new Vector<HtmlOption>();
		Connection conn = null;

		try {
			conn = ServiceConnection.getConnectionStack5();

			returnValue = dropDownProductionFacility(crb, conn);

		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (conn != null) {
				ServiceConnection.returnConnectionStack5(conn);
			}
			if (!throwError.toString().equals("")) {
				throwError.insert(0, "Error @ com.treetop.services." +
						"ServiceWarehouse." +
				"dropDownProductionFacility()");
				throw new Exception(throwError.toString());
			}
		}

		return returnValue;
	}

	private static Vector dropDownProductionFacility(CommonRequestBean crb, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector<HtmlOption> returnValue = new Vector<HtmlOption>();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String requestType = "ddProductionFacility";
			Vector requestClass = new Vector();
			String sql = buildSqlStatement(crb.getEnvironment(), requestType, requestClass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				returnValue.addElement(loadFieldsHtmlOption(requestType, rs));
			}

		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (!throwError.toString().equals("")) {
				throwError.insert(0, "Error @ com.treetop.services." +
						"ServiceWarehouse." +
				"dropDownProductionFacility()");
				throw new Exception(throwError.toString());
			}
		}

		return returnValue;
	}
}

