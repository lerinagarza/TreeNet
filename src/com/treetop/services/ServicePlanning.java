/*
 * Created on March 21, 2008
 *
 */
package com.treetop.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import MvxAPI.MvxSockJ;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramParameter;

import com.treetop.businessobjectapplications.*;
import com.treetop.businessobjects.Inventory;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.PlannedOrder;
import com.treetop.app.planning.*;
import com.treetop.utilities.*;
import com.treetop.viewbeans.CommonRequestBean;

import com.lawson.api.APILog;
import com.lawson.api.BaseAPI;
import com.lawson.api.ServiceAPILog;

/**
 * @author twalto
 *
 * Services class to obtain and return data 
 * to business objects.
 * Lot Services Object.
 */
public class ServicePlanning extends BaseService{
	
	public static final String dblib   = "DBPRD";
	public static final String library = "M3DJDPRD";
	//public static final String testing = "M3DJDTST";
	//public static final String libraryx = "APDEV";
	
	/**
	 *  Construction of the Base information
	 */
	public ServicePlanning() {
		super();
	}

	private static class BuildSQL {
		/**
		 * Create statement to adjust the dates on Planned MO's
		 */
		private static String updPlannedOrdersMO(CommonRequestBean inBean)
		throws Exception {
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
								
				// Determine Library
				String sqlLibrary = GeneralUtility.getLibrary(inBean.getEnvironment()) + ".";
					
				 sqlString.append("UPDATE ");
			   	 sqlString.append(sqlLibrary + "MMOPLP ");
			   	 sqlString.append("SET ROSTDT = ROFIDT, RORELD = ROFIDT ");	
				
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building updPlannedOrdersMO. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServicePlanning.");
				throwError.append("BuildSQL.updPlannedOrdersMO(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * Create statement to Delete All Planned Orders
		 */
		private static String deletePlannedOrders(CommonRequestBean inBean)
		throws Exception {
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
								
				// Determine Library
				String sqlLibrary = GeneralUtility.getLibrary(inBean.getEnvironment()) + ".";
					
				 sqlString.append("DELETE ");
			   	 sqlString.append("FROM " + sqlLibrary + "MITPLO p ");
			   	 sqlString.append("WHERE EXISTS (");
			   	 sqlString.append("  SELECT CAST(ROPLPN AS CHAR(10)) ");		   	 
			   	 sqlString.append("  FROM " + sqlLibrary + "MMOPLP ");
			   	 sqlString.append("  WHERE p.MOCONO = ROCONO AND ");
			   	 sqlString.append("        p.MORIDN = CAST(ROPLPN AS CHAR(10))) ");		   	
			
			   	 //	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building deletePlannedOrders. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServicePlanning.");
				throwError.append("BuildSQL.deletePlannedOrders(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * Create statement to Delete All Planned MO Orders
		 */
		private static String deletePlannedOrdersMO(CommonRequestBean inBean)
		throws Exception {
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
								
				// Determine Library
				String sqlLibrary = GeneralUtility.getLibrary(inBean.getEnvironment()) + ".";
					
				 sqlString.append("DELETE ");
				 sqlString.append("FROM " + sqlLibrary + "MMOPLP ");		   	 
				
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building deletePlannedOrdersMO. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServicePlanning.");
				throwError.append("BuildSQL.updPlannedOrdersMO(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * Create statement to adjust the dates on Planned MO's
		 *    Changes the MITPLO - Planning file, to reflect the correct MO date on the Supply Items
		 */
		private static String updPlannedOrdersMOSupplies(CommonRequestBean inBean)
		throws Exception {
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
								
				// Determine Library
				String sqlLibrary = GeneralUtility.getLibrary(inBean.getEnvironment()) + ".";
				sqlString.append("UPDATE ");
			   	sqlString.append(sqlLibrary + "MITPLO ");
			   	sqlString.append("SET MOPLDT = (SELECT a.ROFIDT ");
			   	sqlString.append("  FROM " + sqlLibrary + "MMOPLP a ");
			   	sqlString.append("    WHERE MOCONO = a.ROCONO AND MORIDN = CAST(a.ROPLPN AS CHAR(10))) ");
			   	sqlString.append(" WHERE MOCONO = '100' AND MOORCA = '110' ");
			   	sqlString.append("   AND EXISTS (SELECT * FROM ");
			   	sqlString.append(sqlLibrary + "MMOPLP b ");
			   	sqlString.append("   WHERE MOCONO = b.ROCONO ");
			   	sqlString.append("    AND MORIDN = CAST(b.ROPLPN AS CHAR(10)) ");
			   	sqlString.append("    AND b.ROSTDT = MOPLDT AND B.ROSTDT <> b.ROFIDT) ");
			   	 
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building updPlannedOrdersMOSupplies. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServicePlanning.");
				throwError.append("BuildSQL.updPlannedOrdersMOSupplies(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * Fetch records from MMOPLP to load into
		 * Planned Orders Work File.
		 */
		private static String addPlannedWorkFileRecords(CommonRequestBean inBean, ResultSet rs, String yearWk)
		throws Exception {
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
								
				// Determine Library
				String sqlLibrary = GeneralUtility.getTTLibrary(inBean.getEnvironment()) + ".";
				
				sqlString.append("INSERT INTO " + sqlLibrary + "INPPLNWK ");
				sqlString.append("VALUES(");
				sqlString.append(yearWk + ", ");//year week key field
				sqlString.append(rs.getString("ROCONO") + ", ");
				sqlString.append("'" + rs.getString("ROFACI") + "', ");
				sqlString.append("'" + rs.getString("ROWHLO") + "', ");
				sqlString.append("'" + rs.getString("ROPRNO") + "', ");
				sqlString.append("'" + rs.getString("ROITNO") + "', ");
				sqlString.append(rs.getString("ROPLPN") + ", ");
				sqlString.append(rs.getString("ROPLPS") + ", ");
				sqlString.append(rs.getString("ROPLDT") + ", ");
				sqlString.append(rs.getString("ROORQA") + ", ");
				sqlString.append("'" + rs.getString("ROMAUN") + "', ");
				sqlString.append(rs.getString("ROPPQT") + " ");
				sqlString.append(") ");
				
			} catch (Exception e) {
					throwError.append(" Error building addPlannedWorkFileRecords. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServicePlanning.");
				throwError.append("BuildSQL.addPlannedWorkFileRecords(CommonRequestBean, ");
				throwError.append("ResultSet, " + yearWk + ") ");
				throw new Exception(throwError.toString());
			}
			
			return sqlString.toString();
		}

		/**
		 * Create statement to Delete a week from the
		 * Planned Orders Work File.
		 */
		private static String deletePlannedWorkFileForWeek(CommonRequestBean inBean, String yearWk)
		throws Exception {
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
								
				// Determine Library
				String sqlLibrary = GeneralUtility.getTTLibrary(inBean.getEnvironment()) + ".";
					
				 sqlString.append("DELETE ");
				 sqlString.append("FROM " + sqlLibrary + "INPPLNWK ");
				 sqlString.append("WHERE IPYRWK = " + yearWk + " ");
				
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building deletePlannedWorkFileForWeek. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServicePlanning.");
				throwError.append("BuildSQL.deletePlannedWorkFileForWeek(CommonRequestBean, ");
				throwError.append(yearWk + ") ");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * Create statement to Delete previous weeks from the
		 * Planned Orders Work File.
		 */
		private static String deletePlannedWorkFilePreviousWeeks(CommonRequestBean inBean, String yearWk)
		throws Exception {
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
								
				// Determine Library
				String sqlLibrary = GeneralUtility.getTTLibrary(inBean.getEnvironment()) + ".";
					
				 sqlString.append("DELETE ");
				 sqlString.append("FROM " + sqlLibrary + "INPPLNWK ");
				 sqlString.append("WHERE IPYRWK < " + yearWk + " ");
				
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building deletePlannedWorkFilePreviousWeeks. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServicePlanning.");
				throwError.append("BuildSQL.deletePlannedWorkFilePreviousWeeks(CommonRequestBean, ");
				throwError.append(yearWk + ") ");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * Fetch records from MMOPLP to load into
		 * Planned Orders Work File.
		 */
		private static String getPlannedOrders(CommonRequestBean inBean)
		throws Exception {
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
								
				// Determine Library
				String sqlLibrary = GeneralUtility.getLibrary(inBean.getEnvironment()) + ".";
					
				 sqlString.append("Select ");
				 sqlString.append("ROCONO, ROFACI, ROWHLO, ROPRNO, ");
				 sqlString.append("ROITNO, ROPLPN, ROPLPS, ROPLDT, ");
				 sqlString.append("ROORQA, ROMAUN, ROPPQT ");
				 sqlString.append("FROM " + sqlLibrary + "MMOPLP ");
				
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building getPlannedOrders. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServicePlanning.");
				throwError.append("BuildSQL.getPlannedOrders(CommonRequestBean) ");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}
		
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
		
		// qualify file fields for use.
		StringBuffer fieldList = new StringBuffer();
		
		try { 
		   if (inRequestType.equals("findUploadForecast"))
		   {
			    // Read through the list of Uploaded Forecast information
			 	fieldList.append("SELECT PFUCITNO, PFN3NDID, PFOSPERI, PFMFMFOR ");
			 	sqlString.append("FROM " + dblib + ".PFPUPLOAD " );
			 	sqlString.append("INNER JOIN " + library + ".MITMAS ON MMITNO = PFUCITNO ");
			 	sqlString.append("INNER JOIN " + library + ".MITWHL ON MWWHLO = PFN3NDID ");
			 	//sqlString.append("WHERE PFUCITNO = '200101' ");
			 	sqlString.append("ORDER BY PFOSPERI, PFN3NDID, PFUCITNO ");
		   }
		   if (inRequestType.equals("findRawFruitOrders"))
		   {
		   	    // Will only currently be returning the Order Number
		   		fieldList.append("SELECT MORIDN ");
		   		sqlString.append("FROM " + library + ".MITPLO " );
		   		sqlString.append("INNER JOIN " + library + ".MITMAS ON MMITNO = MOITNO ");
		   		sqlString.append("WHERE MMITTY = '200' "); // Only look at Raw Fruit
		   		sqlString.append("  AND MMGRP3 = 'RCA' "); // Only look at Raw Fruit
		   		sqlString.append("  AND (MOORCA = '110' OR MOORCA = '111') "); // Determines Consumed
		   		sqlString.append("  AND MOPLDT BETWEEN " + requestClass.elementAt(0));
		   		sqlString.append("      AND " + requestClass.elementAt(1) +  " ");
		   		sqlString.append("GROUP BY MORIDN ");
		   		sqlString.append("ORDER BY MORIDN ");
		   }
		   if (inRequestType.equals("detailProduced"))
		   {
		   	    fieldList.append("SELECT MOCONO, MOWHLO, MOITNO, MOPLDT, MOSTAT, ");
		   		fieldList.append("MOORCA, MORIDN, MORIDL, MOTRQT, ");
		   		sqlString.append("FROM " + library + ".MITPLO " );
		   		fieldList.append("MMITDS, MMUNMS, MMITTY, ");
		   		sqlString.append("INNER JOIN " + library + ".MITMAS ON MMITNO = MOITNO ");
		   		fieldList.append("MWWHNM ");
		   		sqlString.append("INNER JOIN " + library + ".MITWHL ON MWWHLO = MOWHLO ");
		   		sqlString.append("WHERE ((MOORCA = '100' "); // Determined Produced
		   		sqlString.append(" AND MOSTAT = '20') OR ( MOORCA = '101' AND MOSTAT >= '20' )) ");
		   		sqlString.append("  AND MORIDN IN (");
		   		if (requestClass.size() > 0)
		   		{
		   		   for (int y = 0; y < requestClass.size(); y++)
		   		   {
		   		   	  PlannedOrder po = (PlannedOrder) requestClass.elementAt(y);
		   		   	  if (y > 0)
		   		   	  	sqlString.append(", ");
		   		   	  sqlString.append("'" + po.getOrderNumber() + "'");
		   		   }
		   		}
		   		sqlString.append("  ) ");
		   		sqlString.append("ORDER BY MOPLDT, MOITNO, MORIDN ");
		   }
		   if (inRequestType.equals("detailRawFruitConsumed"))
		   {
		   		fieldList.append("SELECT MOCONO, MOWHLO, MOITNO, MOPLDT, MOSTAT, ");
		   		fieldList.append("MOORCA, MORIDN, MORIDL, MOTRQT, ");
		   		sqlString.append("FROM " + library + ".MITPLO " );
		   		fieldList.append("MMITDS, MMUNMS, MMITTY, ");
		   		sqlString.append("INNER JOIN " + library + ".MITMAS ON MMITNO = MOITNO ");
		   		fieldList.append("MWWHNM ");
		   		sqlString.append("INNER JOIN " + library + ".MITWHL ON MWWHLO = MOWHLO ");
		   		sqlString.append("WHERE MMITTY = '200' "); // Only look at Raw Fruit
		   		sqlString.append("  AND MMGRP3 = 'RCA' "); // Only look at Raw Fruit
		   		sqlString.append("  AND (MOORCA = '110' OR MOORCA = '130' OR MOORCA = '111' OR MOORCA = '131')"); // Determines Consumed
		   		sqlString.append("  AND MORIDN = '" + requestClass.elementAt(0) + "' ");
		   		sqlString.append("ORDER BY MOPLDT, MOORCA, MOITNO  ");
		   }
		   if (inRequestType.equals("findProducedFromOrders"))
		   {
		   	    // Will only currently be returning the Order Number
		   		fieldList.append("SELECT MOWHLO, MOITNO, MOSTAT, MORIDN, MMITTY ");
		   		sqlString.append("FROM " + library + ".MITPLO " );
		   		sqlString.append("INNER JOIN " + library + ".MITMAS ON MMITNO = MOITNO ");
		   		sqlString.append("WHERE ((MOORCA = '100' "); // Determined Produced
		   		sqlString.append(" AND MOSTAT = '20') OR ( MOORCA = '101' AND MOSTAT >= '20' )) ");
		   		sqlString.append("  AND MORIDN IN (");
		   		if (requestClass.size() > 0)
		   		{
		   		   for (int y = 0; y < requestClass.size(); y++)
		   		   {
		   		   	  if (y > 0)
		   		   	  	sqlString.append(", ");
		   		   	  sqlString.append("'" + requestClass.elementAt(y) + "'");
		   		   }
		   		}
		   		sqlString.append(") ");
		   		sqlString.append("  AND MOWHLO IN ('209', '220', '230', '240', '490', '469') ");
		   		sqlString.append("GROUP BY MOWHLO, MOITNO, MOSTAT, MORIDN, MMITTY ");
		   		sqlString.append("ORDER BY MOITNO, MORIDN, MOWHLO ");
		   }
		   if (inRequestType.equals("findProducedFromItems"))
		   {
		   		fieldList.append("SELECT MOCONO, MOWHLO, MOITNO, MOPLDT, MOSTAT, ");
		   		fieldList.append("MOORCA, MORIDN, MORIDL, MOTRQT, ");
		   		sqlString.append("FROM " + library + ".MITPLO " );
		   		fieldList.append("MMITDS, MMUNMS, MMITTY, ");
		   		sqlString.append("INNER JOIN " + library + ".MITMAS ON MMITNO = MOITNO ");
		   		fieldList.append("MWWHNM ");
		   		sqlString.append("INNER JOIN " + library + ".MITWHL ON MWWHLO = MOWHLO ");
		   		sqlString.append("WHERE ((MOORCA = '100' "); // Determined Produced
		   		sqlString.append(" AND MOSTAT = '20') OR ( MOORCA = '101' AND MOSTAT >= '20' )) ");
		   		sqlString.append("  AND MOPLDT BETWEEN " + requestClass.elementAt(0));
		   		sqlString.append("  AND " + requestClass.elementAt(1) +  " ");
		   		sqlString.append("  AND MOITNO IN (");
		   		if (requestClass.size() > 0)
		   		{
		   		   for (int y = 2; y < requestClass.size(); y++)
		   		   {
		   		   	  if (y > 2)
		   		   	  	sqlString.append(", ");
		   		   	  sqlString.append("'" + requestClass.elementAt(y) + "'");
		   		   }
		   		}
		   		sqlString.append(") ");
		   		sqlString.append("  AND MOWHLO IN ('209', '220', '230', '240', '490', '469') ");
		   		sqlString.append("ORDER BY MOWHLO, MOPLDT, MORIDN, MOORCA ");
		   }	
		   if (inRequestType.equals("findConsumedFromOrder"))
		   {
		   		fieldList.append("SELECT MOCONO, MOWHLO, MOITNO, MOPLDT, MOSTAT, ");
		   		fieldList.append("MOORCA, MORIDN, MORIDL, MOTRQT, ");
		   		sqlString.append("FROM " + library + ".MITPLO " );
		   		fieldList.append("MMITDS, MMUNMS, MMITTY, ");
		   		sqlString.append("INNER JOIN " + library + ".MITMAS ON MMITNO = MOITNO ");
		   		fieldList.append("MWWHNM ");
		   		sqlString.append("INNER JOIN " + library + ".MITWHL ON MWWHLO = MOWHLO ");
		   		sqlString.append("WHERE (MOORCA = '110' OR MOORCA = '130' OR MOORCA = '111' OR MOORCA = '131') "); // Determined Consumed
		   		sqlString.append("  AND MORIDN ='" + requestClass.elementAt(0) + "'");
		   		sqlString.append("  AND MMITTY IN ('150', '170', '180', '181', '182', '200') ");
		   		sqlString.append("ORDER BY MOWHLO, MOPLDT, MORIDN, MOORCA ");
		   }		   
		   if (inRequestType.equals("findConsumedFromOrders"))
		   {
		   		fieldList.append("SELECT MOCONO, MOWHLO, MOITNO, MOPLDT, MOSTAT, ");
		   		fieldList.append("MOORCA, MORIDN, MORIDL, MOTRQT, ");
		   		sqlString.append("FROM " + library + ".MITPLO " );
		   		fieldList.append("MMITDS, MMUNMS, MMITTY, ");
		   		sqlString.append("INNER JOIN " + library + ".MITMAS ON MMITNO = MOITNO ");
		   		fieldList.append("MWWHNM ");
		   		sqlString.append("INNER JOIN " + library + ".MITWHL ON MWWHLO = MOWHLO ");
		   		sqlString.append("WHERE (MOORCA = '110' OR MOORCA = '111') "); // Determined Consumed
		   		sqlString.append("  AND MORIDN IN (");
		   		if (requestClass.size() > 0)
		   		{
		   		   for (int y = 0; y < requestClass.size(); y++)
		   		   {
		   		   	  if (y > 0)
		   		   	  	sqlString.append(", ");
		   		   	  sqlString.append("'" + requestClass.elementAt(y) + "'");
		   		   }
		   		}
		   		sqlString.append(") ");
		   		sqlString.append("  AND MMITTY IN ('150', '170', '180', '181', '182', '200') ");
		   		sqlString.append("ORDER BY MOWHLO, MOPLDT, MORIDN, MOORCA ");
		   }		   
		   if (inRequestType.equals("findOrdersFromConsumed"))
		   {
		   	    // Will only currently be returning the Order Number
		   		fieldList.append("SELECT MOWHLO, MOITNO, MOSTAT, MORIDN, MMITTY ");
		   		sqlString.append("FROM " + library + ".MITPLO " );
		   		sqlString.append("INNER JOIN " + library + ".MITMAS ON MMITNO = MOITNO ");
		   		sqlString.append("WHERE (MOORCA = '110' OR MOORCA = '111') "); // Determined Consumed
		   		sqlString.append("  AND MOPLDT BETWEEN " + requestClass.elementAt(0));
		   		sqlString.append("      AND " + requestClass.elementAt(1) +  " ");
		   		sqlString.append("  AND MOITNO IN (");
		   		if (requestClass.size() > 2)
		   		{
		   		   for (int y = 2; y < requestClass.size(); y++)
		   		   {
		   		   	  if (y > 2)
		   		   	  	sqlString.append(", ");
		   		   	  sqlString.append("'" + ((String) requestClass.elementAt(y)).trim() + "'");
		   		   }
		   		}
		   		sqlString.append(") ");
		   		sqlString.append("GROUP BY MOWHLO, MOITNO, MOSTAT, MORIDN, MMITTY ");
		   		sqlString.append("ORDER BY MORIDN, MOITNO, MOWHLO ");
		   }		
		   if (inRequestType.equals("basicInventory"))
		   {
		   	  fieldList.append("SELECT MBITNO, MMITDS, SUM(MBSTQT) ");
		   	  sqlString.append("FROM " + library + ".MITBAL ");
		   	  sqlString.append("INNER JOIN " + library + ".MITMAS ");
		   	  sqlString.append("  ON MBITNO = MMITNO ");
		   	  sqlString.append("WHERE MMITTY = '200' ");
		   	  sqlString.append("GROUP BY MBITNO, MMITDS ");
			  sqlString.append("ORDER BY MBITNO ");
		   }
		   if (inRequestType.equals("poData"))
		   {
		   	  // Changed field while testing should be using IBCFQA instead of IBORQA
		   	  fieldList.append("SELECT MBITNO, MMITDS, MBWHLO, MWWHNM, IBDWDT, SUM(IBORQA), SUM(IBRVQA) ");
		   	  sqlString.append("FROM " + library + ".MITBAL ");
		   	  sqlString.append("INNER JOIN " + library + ".MITMAS ");
		   	  sqlString.append("  ON MBITNO = MMITNO ");
		   	  sqlString.append("INNER JOIN " + library + ".MITWHL ON MWWHLO = MBWHLO ");
		   	  sqlString.append("INNER JOIN " + library + ".MPLINE ON MBITNO = IBITNO ");
		   	  sqlString.append("       AND MBWHLO = IBWHLO ");
		   	  sqlString.append("WHERE MMITTY = '200' ");
		   	  sqlString.append("  AND MMGRP3 = 'RCA' ");
		   	  sqlString.append("  AND IBPUST = '20' and IBPUSL = '20' ");
		   	  sqlString.append("  AND (IBORTY = 'RF' OR IBORTY = 'P03' ");
		   	  try
			  {
		   	     DateTime dt = UtilityDateTime.getSystemDate();
		   	     //    If today's date is Less than start date for the chosen week, include the "RFF"
		   	     int dtint = (new Integer(dt.getDateFormatyyyyMMdd())).intValue();
		   	     int dttst = (new Integer((String) requestClass.elementAt(0))).intValue();
		   	     if (dtint < dttst)
		   	  	   sqlString.append(" OR IBORTY = 'RFF'");
			  }
		   	  catch(Exception e)
			  {}
		   	  sqlString.append(") ");
		   	  sqlString.append("  AND IBDWDT BETWEEN " + requestClass.elementAt(0));
	   		  sqlString.append("      AND " + requestClass.elementAt(1) +  " ");
		   	  sqlString.append("GROUP BY MBITNO, MMITDS, IBDWDT, MBWHLO, MWWHNM ");
			  sqlString.append("ORDER BY MBITNO, MBWHLO, IBDWDT ");
		   }
		   if (inRequestType.equals("planWhse"))
		   {
		   	 String type = (String) requestClass.elementAt(0);
		   	 fieldList.append("SELECT MOWHLO, MWWHNM ");
		   	 sqlString.append("FROM " + library + ".MITPLO ");
		   	 sqlString.append("INNER JOIN " + library + ".MITWHL ");
		   	 sqlString.append("   ON MOWHLO = MWWHLO ");
		   	 if (type.equals("production"))
		   	 {
		   	 	sqlString.append(" WHERE MOORCA = '100' OR MOORCA = '101' ");
		   	 }
		   	 sqlString.append("GROUP BY MOWHLO, MWWHNM ");
		   	 sqlString.append("ORDER BY MOWHLO ");
		   }
		   if (inRequestType.equals("planItemGroup"))
		   {
		   	 String type = (String) requestClass.elementAt(0);
		   	 fieldList.append("SELECT MMITGR, CTTX40 ");
		   	 sqlString.append("FROM " + library + ".MITPLO ");
		   	 sqlString.append("INNER JOIN " + library + ".MITMAS ");
		   	 sqlString.append("   ON MOITNO = MMITNO ");
		   	 sqlString.append("INNER JOIN " + library + ".CSYTAB ");
		   	 sqlString.append("   ON CTSTCO = 'ITGR' ");
		   	 sqlString.append("   AND CTSTKY = MMITGR AND CTCONO = 100 ");
		   	 if (type.equals("production"))
		   	 {
		   	 	sqlString.append(" WHERE MOORCA = '100' OR MOORCA = '101' ");
		   	 }
		   	 sqlString.append("  GROUP BY MMITGR, CTTX40 ");
		   	 sqlString.append("  ORDER BY MMITGR ");
		   }
		  
		   
		   if (inRequestType.equals("planWorkCenter"))
		   {
//		   	use work center page
//		   	 String type = (String) requestClass.elementAt(0);
//		   	 fieldList.append("SELECT POPLGR, POOPDS ");
//		   	 sqlString.append("FROM " + library + ".MITPLO ");
//		   	 sqlString.append("INNER JOIN " + library + ".MITMAS ");
//		   	 sqlString.append("   ON MOITNO = MMITNO ");
//		   	 sqlString.append("INNER JOIN " + library + ".CSYTAB ");
//		   	 sqlString.append("   ON CTSTCO = 'ITGR' ");
//		   	 sqlString.append("   AND CTSTKY = MMITGR AND CTCONO = 100 ");
//		   	 if (type.equals("production"))
//		   	 {
//		   	 	sqlString.append(" WHERE MOORCA = '100' OR MOORCA = '101' ");
//		   	 }
//		   	 sqlString.append("  GROUP BY MMITGR, CTTX40 ");
//		   	 sqlString.append("  ORDER BY MMITGR ");
		  }
		} catch (Exception e) {
				throwError.append(" Error building sql statement");
				throwError.append(" for request type " + inRequestType + ". ");
		}
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServicePlanning.");
			throwError.append("buildSqlStatement(String, Vector)");
			throw new Exception(throwError.toString());
		}
		return fieldList.toString() + sqlString.toString();
	}
	/**
	 * Load class fields from result set.
	 */
	
	private static PlannedOrder loadFields(String loadType,
											  ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		PlannedOrder buildRecord = new PlannedOrder();

		// Not Currently using loadType
		try
		{
			if (loadType.equals("basicTestProduced"))
			{  // Load Only Basic Data Needed for Testing
				// MITPLO
				buildRecord.setProducedItemNumber(rs.getString("MOITNO"));
				buildRecord.setWarehouse(rs.getString("MOWHLO"));
				buildRecord.setStatus(rs.getString("MOSTAT"));
				buildRecord.setOrderNumber(rs.getString("MORIDN"));
				// MITMAS
				buildRecord.setProducedItemType(rs.getString("MMITTY"));
			}
			if (loadType.equals("basicTestConsumed"))
			{  // Load Only Basic Data Needed for Testing
				// MITPLO
				buildRecord.setConsumedItemNumber(rs.getString("MOITNO"));
				buildRecord.setWarehouse(rs.getString("MOWHLO"));
				buildRecord.setStatus(rs.getString("MOSTAT"));
				buildRecord.setOrderNumber(rs.getString("MORIDN"));
				// MITMAS
				buildRecord.setConsumedItemType(rs.getString("MMITTY"));
			}
			if (loadType.equals("plannedOrderProduced"))
			{  
				// MITPLO
				buildRecord.setCompany(rs.getString("MOCONO").trim());
				buildRecord.setWarehouse(rs.getString("MOWHLO").trim());
				buildRecord.setProducedItemNumber(rs.getString("MOITNO").trim());
				buildRecord.setPlanningDate(rs.getString("MOPLDT").trim());
				buildRecord.setStatus(rs.getString("MOSTAT").trim());
				buildRecord.setOrderCategory(rs.getString("MOORCA").trim());
				buildRecord.setOrderNumber(rs.getString("MORIDN").trim());
				buildRecord.setOrderLine(rs.getString("MORIDL").trim());
				buildRecord.setProducedQuantity(rs.getString("MOTRQT").trim());
				try
				{
				// MITMAS -- Item Master
				   buildRecord.setProducedItemDescription(rs.getString("MMITDS").trim());
				   buildRecord.setProducedItemBasicUOM(rs.getString("MMUNMS").trim());
				   buildRecord.setProducedItemType(rs.getString("MMITTY").trim());
				}
				catch(Exception e)
				{}
				try
				{
				// MITWHL -- Warehouse Master
				   buildRecord.setWarehouseDescription(rs.getString("MWWHNM").trim());
				}
				catch(Exception e)
				{}
			}	
			if (loadType.equals("plannedOrderConsumed"))
			{  
				// MITPLO
				buildRecord.setCompany(rs.getString("MOCONO").trim());
				buildRecord.setWarehouse(rs.getString("MOWHLO").trim());
				buildRecord.setConsumedItemNumber(rs.getString("MOITNO").trim());
				buildRecord.setPlanningDate(rs.getString("MOPLDT").trim());
				buildRecord.setStatus(rs.getString("MOSTAT").trim());
				buildRecord.setOrderCategory(rs.getString("MOORCA").trim());
				buildRecord.setOrderNumber(rs.getString("MORIDN").trim());
				buildRecord.setOrderLine(rs.getString("MORIDL").trim());
				buildRecord.setConsumedQuantity(rs.getString("MOTRQT").trim());
				try
				{
				// MITMAS -- Item Master
				   buildRecord.setConsumedItemDescription(rs.getString("MMITDS").trim());
				   buildRecord.setConsumedItemBasicUOM(rs.getString("MMUNMS").trim());
				   buildRecord.setConsumedItemType(rs.getString("MMITTY").trim());
				}
				catch(Exception e)
				{}
				try
				{
				// MITWHL -- Warehouse Master
				   buildRecord.setWarehouseDescription(rs.getString("MWWHNM").trim());
				}
				catch(Exception e)
				{}
			}
			if (loadType.equals("poData"))
			{  
				try
				{
				// MITBAL -- Item Warehouse Master
				   buildRecord.setItemNumber(rs.getString("MBITNO").trim());
				   buildRecord.setWarehouse(rs.getString("MBWHLO").trim());
				}
				catch(Exception e)
				{}
				try
				{
				// MPLINE -- Lines for the Purchase Order
				   buildRecord.setPlanningDate(rs.getString("IBDWDT"));
				   BigDecimal tst1 = new BigDecimal(rs.getString(6));
				   BigDecimal tst2 = new BigDecimal(rs.getString(7));
				   buildRecord.setQuantity(tst1.subtract(tst2).toString());
				}
				catch(Exception e)
				{}
				try
				{
				// MITMAS -- Item Master
				   buildRecord.setItemDescription(rs.getString("MMITDS").trim());
				}
				catch(Exception e)
				{}
				try
				{
				// MITWHL -- Warehouse Master
				   buildRecord.setWarehouseDescription(rs.getString("MWWHNM").trim());
				}
				catch(Exception e)
				{}				
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the class PlannedOrder ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServicePlanning.");
			throwError.append("loadFields(loadType:");
			throwError.append(loadType + ", rs). ");
			throw new Exception(throwError.toString());
		}
		return buildRecord;
	}
	
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		// Test all that you can.
		
		// testing planned order deletion.
		String startHere = "x";
		
		if (0 == 1) 
		{		
			CommonRequestBean crb = new CommonRequestBean();
			crb.setEnvironment("TST");
			deletePlannedOrders(crb);			
			String breakPoint = "stopHere";
		}
		
		// testing manufacturing planned order update.
		
		if (0 == 1) 
		{		
			CommonRequestBean crb = new CommonRequestBean();
			crb.setEnvironment("TST");
			updatePlannedOrdersMO(crb);			
			String breakPoint = "stopHere";
		}
		
		//Update the Planned MO Work File used for comparison.
		if (1 == 0)
		{
			CommonRequestBean crb = new CommonRequestBean();
			crb.setEnvironment("PRD");
			updatePlannedOrdersWorkFile(crb);			
			String breakPoint = "stopHere";
		}
	
	}
	/**
	 * Return a Vector o String's which will be the Order Numbers 
	 * @param Vector InValues and Connection.
	 * @return Vector (Strings -- Order Numbers).
	 */
	private static Vector findListRawFruitOrders(Vector inValues, Connection conn)						
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnVector = new Vector();
		ResultSet rs = null;
		PreparedStatement findThem = null;
		try
		{
			try {
				findThem = conn.prepareStatement(buildSqlStatement("findRawFruitOrders", inValues));
				rs = findThem.executeQuery();
			} catch(Exception e)
			{
				throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			}

	//		}
			if (throwError.toString().equals(""))
			{
			   try
			   {
				   while (rs.next())
				   {
						rtnVector.addElement(rs.getString("MORIDN").trim());
				   }
			   } catch(Exception e)
			   {
			   	  throwError.append(" error occured while Building Vector from sql statement. " + e);
			   } 
			} // end of the IF statement
		} catch(Exception e)
		{
			throwError.append(" error occured. " + e);
		}
		finally
		{
			try
			{
				if (findThem != null)
					findThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
			try
			{
				if (rs != null)
					rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}			
		}	
		// return data.	
		if (!throwError.toString().trim().equals("")) 
		{
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServicePlanning.");
			throwError.append("findListRawFruitOrders(");
			throwError.append("Vector (dates), Connection). ");
			throw new Exception(throwError.toString());
		}
			return rtnVector;
	}

	/**
	 * Return a Vector of PlannedOrder Classes 
	 * @param Vector InValues (Vector of Strings, Orders)and Connection.
	 * @return Vector (PlannedOrder).
	 */
	private static Vector findListProducedFromOrders(Vector inValues, Connection conn)						
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnVector = new Vector();
	  if (inValues.size() > 0)
	  {
		ResultSet rs = null;
		PreparedStatement findThem = null;
		try
		{
			try { 
				findThem = conn.prepareStatement(buildSqlStatement("findProducedFromOrders", inValues));
				rs = findThem.executeQuery();
			} catch(Exception e)
			{
				throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			}
	
	//		}
			if (throwError.toString().equals(""))
			{
			   try
			   {
				   while (rs.next())
				   {
						rtnVector.addElement(loadFields("basicTestProduced", rs));
				   }
			   } catch(Exception e)
			   {
			   	  throwError.append(" error occured while Building Vector from sql statement. " + e);
			   } 
			} // end of the IF statement
		} catch(Exception e)
		{
			throwError.append(" error occured. " + e);
		}
		finally
		{
			try
			{
				if (findThem != null)
					findThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
			try
			{
				if (rs != null)
					rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}			
		}	
		// return data.	
		if (!throwError.toString().trim().equals("")) 
		{
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServicePlanning.");
			throwError.append("findListProducedItemsFromOrders(");
			throwError.append("Vector (orders), Connection). ");
			throw new Exception(throwError.toString());
		}
	  }
	  return rtnVector;
	}

	/**
	 * Return a Vector of PlannedOrder Classes 
	 * @param Vector InValues (Vector of Strings, Orders)and Connection.
	 * @return Vector (PlannedOrder).
	 */
	private static Vector findListConsumedFromItems(Vector inValues, Connection conn)						
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnVector = new Vector();
	  if (inValues.size() > 0)
	  {
		ResultSet rs = null;
		PreparedStatement findThem = null;
		try
		{
			try { 
				findThem = conn.prepareStatement(buildSqlStatement("findOrdersFromConsumed", inValues));
				rs = findThem.executeQuery();
			} catch(Exception e)
			{
				throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			}
	
	//		}
			if (throwError.toString().equals(""))
			{
			   try
			   {
				   while (rs.next())
				   {
						rtnVector.addElement(loadFields("basicTest", rs));
				   }
			   } catch(Exception e)
			   {
			   	  throwError.append(" error occured while Building Vector from sql statement. " + e);
			   } 
			} // end of the IF statement
		} catch(Exception e)
		{
			throwError.append(" error occured. " + e);
		}
		finally
		{
			try
			{
				if (findThem != null)
					findThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
			try
			{
				if (rs != null)
					rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}			
		}	
		// return data.	
		if (!throwError.toString().trim().equals("")) 
		{
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServicePlanning.");
			throwError.append("findListOrdersFromConsumedItems(");
			throwError.append("Vector (items), Connection). ");
			throw new Exception(throwError.toString());
		}
	  }
	  return rtnVector;
	}

	/**
	 * Return a Vector of PlannedOrder Classes 
	 * @param Vector InValues (Vector of TopLevel's So Far, Vector of PlannedOrders).
	 * @return Vector (PlannedOrder -- 2 Elements Vector Top Levels, and Vector of Still to Go).
	 */
	private static Vector compareListsForTopLevel(Vector topLevels, Vector inValues)						
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnVector = new Vector();
		Vector stillToGo = new Vector();
	  if (inValues.size() > 0)
	  {
		try
		{
			Vector list1 = (Vector) inValues.elementAt(0);
			Vector list2 = (Vector) inValues.elementAt(1);
			if (list1.size() > 0)
			{
				if (list2.size() > 0)
				{
				   String saveItem = "";
				   for (int one = 0; one < list1.size(); one++)
				   {
				   	   PlannedOrder test1 = (PlannedOrder) list1.elementAt(one);
				   	   if (!saveItem.trim().equals(test1.getProducedItemNumber().trim()))
				   	   {
				   	   	   String topLevel = "Y";
						   if (!test1.getProducedItemType().equals("100") &&
							   !test1.getProducedItemType().equals("110") &&
							   !test1.getProducedItemType().equals("120") &&
							   !test1.getProducedItemType().equals("130"))
						   {
				   	   	       for (int two = 0; two < list2.size(); two++)
				   	   	       {
				   	   		      PlannedOrder test2 = (PlannedOrder) list2.elementAt(two);	
				   	   	   	      if (test1.getProducedItemNumber().trim().equals(test2.getProducedItemNumber().trim()))
				   	   	   	      {
				   	   	   	  	     stillToGo.addElement(test2);
				   	   	   	  	     topLevel = "N";
				   	   	   	      }
				   	   	       }
							}
				   	   	    saveItem = test1.getProducedItemNumber().trim();
				   	   	    if (topLevel.equals("Y"))
				   	   	   	   topLevels.addElement(test1);
				   	   }
				   }
				}
				else
				{   // Run through List 1 and use them all as Top Level.
					   for (int one = 0; one < list1.size(); one++)
					   {
					   	  topLevels.addElement(list1.elementAt(one));
					   }
				}
			}
		} catch(Exception e)
		{
			throwError.append(" error occured. " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) 
		{
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServicePlanning.");
			throwError.append("compareListsForTopLevel(");
			throwError.append("Vector (Top Level), Vector (2 lists)). ");
			throw new Exception(throwError.toString());
		}
	  }
	  rtnVector.addElement(topLevels);
	  rtnVector.addElement(stillToGo);
	  return rtnVector;
	}

	/**
 * Return a Vector of PlannedOrder which will be the Order Numbers 
 * @param Vector sendParms, Vector listTopLevel and Connection.
 * @return Vector (PlannedOrder).
 */
	private static Vector findListProducedFromItems(Vector sendParms, Vector listItems, Connection conn)						
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnVector = new Vector();
		ResultSet rs = null;
		PreparedStatement findThem = null;
		try
		{
			for (int x = 0; x < listItems.size(); x++)
			{
				sendParms.addElement(((PlannedOrder) listItems.elementAt(x)).getProducedItemNumber().trim()); 
			}
			try {
				findThem = conn.prepareStatement(buildSqlStatement("findProducedFromItems", sendParms));
				rs = findThem.executeQuery();
			} catch(Exception e)
			{
				throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			}
	
	//		}
			if (throwError.toString().equals(""))
			{
			   try
			   {
				   while (rs.next())
				   {
					  rtnVector.addElement(loadFields("plannedOrderProduced", rs));
				   }
			   } catch(Exception e)
			   {
			   	  throwError.append(" error occured while Building Vector from sql statement. " + e);
			   } 
			} // end of the IF statement
		} catch(Exception e)
		{
			throwError.append(" error occured. " + e);
		}
		finally
		{
			try
			{
				if (findThem != null)
					findThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
			try
			{
				if (rs != null)
					rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}			
		}	
		// return data.	
		if (!throwError.toString().trim().equals("")) 
		{
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServicePlanning.");
			throwError.append("findListProducedFromItems(");
			throwError.append("Vector (dates), Vector ItemLists Connection). ");
			throw new Exception(throwError.toString());
		}
			return rtnVector;
	}

/**
 * Return a Vector of Consumed PlannedOrder Classes 
 * @param Vector InValues (Vector of PlannedOrder, Orders)and Connection.
 * @return Vector (PlannedOrder).
 */
private static Vector findListConsumedFromOrders(Vector inValues, Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	Vector rtnVector = new Vector();
  if (inValues.size() > 0)
  {
	ResultSet rs = null;
	PreparedStatement findThem = null;
	try
	{
		try { 
			findThem = conn.prepareStatement(buildSqlStatement("findConsumedFromOrders", inValues));
			rs = findThem.executeQuery();
		} catch(Exception e)
		{
			throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}

//		}
		if (throwError.toString().equals(""))
		{
		   try
		   {
			   while (rs.next())
			   {
					rtnVector.addElement(loadFields("plannedOrderConsumed", rs));
			   }
		   } catch(Exception e)
		   {
		   	  throwError.append(" error occured while Building Vector from sql statement. " + e);
		   } 
		} // end of the IF statement
	} catch(Exception e)
	{
		throwError.append(" error occured. " + e);
	}
	finally
	{
		try
		{
			if (findThem != null)
				findThem.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception el){
			el.printStackTrace();
		}			
	}	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServicePlanning.");
		throwError.append("findListConsumedFromOrders(");
		throwError.append("Vector, Connection). ");
		throw new Exception(throwError.toString());
	}
  }
  return rtnVector;
}

/**
 * Return a Vector of Consumed PlannedOrder Classes 
 * @param String -- order Number
 * @return Vector (PlannedOrder).
 */
private static Vector findListConsumedFromOrder(String order, Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	Vector rtnVector = new Vector();
	ResultSet rs = null;
	PreparedStatement findThem = null;
	try
	{
		try {
			Vector parmValues = new Vector();
			parmValues.addElement(order);
			
			findThem = conn.prepareStatement(buildSqlStatement("findConsumedFromOrder", parmValues));
			rs = findThem.executeQuery();
		} catch(Exception e)
		{
			throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}

//		}
		if (throwError.toString().equals(""))
		{
		   try
		   {
			   while (rs.next())
			   {
					rtnVector.addElement(loadFields("plannedOrderConsumed", rs));
			   }
		   } catch(Exception e)
		   {
		   	  throwError.append(" error occured while Building Vector from sql statement. " + e);
		   } 
		} // end of the IF statement
	} catch(Exception e)
	{
		throwError.append(" error occured. " + e);
	}
	finally
	{
		try
		{
			if (findThem != null)
				findThem.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception el){
			el.printStackTrace();
		}			
	}	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServicePlanning.");
		throwError.append("findListConsumedFromOrder(");
		throwError.append("String, Connection). ");
		throw new Exception(throwError.toString());
	}
 
  return rtnVector;
}

/**
 * Return a Vector or PlannedOrder's 
 * 		Will include both Production and Usage
 * @param Vector InValues and Connection.
 * @return Vector (PlannedOrder's).
 */
private static BeanPlanning findDetailsRawFruitOrders(BeanPlanning bp,
													  Vector rfOrders,
												      Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	ResultSet rs = null;
	ResultSet rs1 = null;
	PreparedStatement findThem = null;
	PreparedStatement findUsage = null;
	try
	{
		try {
			findThem = conn.prepareStatement(buildSqlStatement("detailProduced", rfOrders));
			rs = findThem.executeQuery();
		} catch(Exception e)
		{
			throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}

//		}
		if (throwError.toString().equals(""))
		{
		   Vector 	rawFruit209Main = new Vector(); // Selah 
		   Vector 	rawFruit220Main = new Vector();	// Cashmere 
		   Vector 	rawFruit230Main = new Vector(); // Wenatchee
		   Vector 	rawFruit240Main = new Vector(); // Ross	
		   Vector 	rawFruit490Main = new Vector();	// Fresh Slice 
		   Vector 	rawFruit469Main = new Vector(); // Prosser
		   try
		   {
			   while (rs.next())
			   {  // for each one, go get the usage details
	//				System.out.println(rs.getString("MORIDN"));
	//				System.out.println(rs.getString("MOITNO"));
	//				System.out.println(rs.getString("MOPLDT"));
					PlannedOrder po = loadFields("plannedOrderProduced", rs);
					Vector usageInfo = new Vector();
					Vector rf = new Vector();
					Vector elim = new Vector();
					try {
						Vector sendParm = new Vector();
						sendParm.addElement(po.getOrderNumber());
						findUsage = conn.prepareStatement(buildSqlStatement("detailRawFruitConsumed", sendParm));
						rs1 = findUsage.executeQuery();
						while (rs1.next())
						{
						  if (rs1.getString("MMITTY").trim().equals("200"))
						  {
							 if (rs1.getString("MOORCA").equals("130"))
								elim.addElement(loadFields("plannedOrderConsumed", rs1));
							 else
								rf.addElement(loadFields("plannedOrderConsumed", rs1));
						  }
						}
						
					} catch(Exception e)
					{
						 System.out.println("Debug this: " + e);
					}
					usageInfo.addElement(rf);
					usageInfo.addElement(elim);
					if (po.getWarehouse().equals("209"))
					{
						rawFruit209Main.addElement(po);
						rawFruit209Main.addElement(usageInfo);
					}
					if (po.getWarehouse().equals("220"))
					{
						rawFruit220Main.addElement(po);
						rawFruit220Main.addElement(usageInfo);
					}
					if (po.getWarehouse().equals("230"))
					{
						rawFruit230Main.addElement(po);
						rawFruit230Main.addElement(usageInfo);
					}
					if (po.getWarehouse().equals("240"))
					{
						rawFruit240Main.addElement(po);
						rawFruit240Main.addElement(usageInfo);
					}
					if (po.getWarehouse().equals("490"))
					{
						rawFruit490Main.addElement(po);
						rawFruit490Main.addElement(usageInfo);
					}
					if (po.getWarehouse().equals("469"))
					{
						rawFruit469Main.addElement(po);
						rawFruit469Main.addElement(usageInfo);
					}
			   }
		   } catch(Exception e)
		   {
		   	  throwError.append(" error occured while Building Vector from sql statement. " + e);
		   } 
		   bp.setRawFruit209Main(rawFruit209Main);
		   bp.setRawFruit220Main(rawFruit220Main); 
		   bp.setRawFruit230Main(rawFruit230Main);
		   bp.setRawFruit240Main(rawFruit240Main);
		   bp.setRawFruit490Main(rawFruit490Main);
		   bp.setRawFruit469Main(rawFruit469Main);
		   
		} // end of the IF statement
	} catch(Exception e)
	{
		throwError.append(" error occured. " + e);
	}
	finally
	{
		try
		{
			if (findThem != null)
				findThem.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
		try
		{
			if (findUsage != null)
				findUsage.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rs1 != null)
				rs1.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServicePlanning.");
		throwError.append("findDetailsRawFruitOrders(");
		throwError.append("Vector (dates), Connection). ");
		throw new Exception(throwError.toString());
	}
		return bp;
}

/**
 * Return a Vector of PlannedOrder Classes 
 * @param Vector InValues (Vector of TopLevel's So Far, Vector of PlannedOrders).
 * @return Vector (PlannedOrder -- 2 Elements Vector Top Levels, and Vector of Still to Go).
 */
private static Vector compareListsForOrders(Vector list1, Vector list2)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	Vector rtnVector = new Vector();  //Returned Values must be in BOTH lists
	try
	{
		if (list1.size() > 0)
		{
			if (list2.size() > 0)
			{
			   for (int one = 0; one < list1.size(); one++)
			   {
			   	   String onBoth = "";
			   	   PlannedOrder test1 = (PlannedOrder) list1.elementAt(one);
	   	   	       for (int two = 0; two < list2.size(); two++)
	   	   	       {
	   	   		      String orderNumber = (String) list2.elementAt(two);	
	   	   	   	      if (test1.getOrderNumber().trim().equals(orderNumber.trim()))
			   	   	  {
			   	   	   	 onBoth = "Y";
			   	   	   	 two = list2.size();
					   }
			   	   }
	   	   	       if (onBoth.equals("Y"))
	   	   	          rtnVector.addElement(test1);
			   }
			}
		}
	} catch(Exception e)
	{
		throwError.append(" error occured. " + e);
	}
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServicePlanning.");
		throwError.append("compareListsForOrders(");
		throwError.append("Vector, Vector). ");
		throw new Exception(throwError.toString());
	}

  return rtnVector;
}

/**
 * Return a Vector of Inventory Classes 
 * @param Vector InValues (Vector of Strings, Orders)and Connection.
 * @return Vector (Inventory).
 */
private static Vector findInventoryRawFruit(Vector inValues, Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	Vector rtnVector = new Vector();
    ResultSet rs = null;
	PreparedStatement findThem = null;
	try
	{
		try { 
			findThem = conn.prepareStatement(buildSqlStatement("basicInventory", inValues));
			rs = findThem.executeQuery();
		} catch(Exception e)
		{
			throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}

//		}
		if (throwError.toString().equals(""))
		{
		   try
		   {
			   while (rs.next())
			   {
					rtnVector.addElement(loadFieldsInventory("basicInventory", rs));
			   }
		   } catch(Exception e)
		   {
		   	  throwError.append(" error occured while Building Vector from sql statement. " + e);
		   } 
		} // end of the IF statement
	} catch(Exception e)
	{
		throwError.append(" error occured. " + e);
	}
	finally
	{
		try
		{
			if (findThem != null)
				findThem.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception el){
			el.printStackTrace();
		}			
	}	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServicePlanning.");
		throwError.append("findRawFruitInventory(");
		throwError.append("Vector, Connection). ");
		throw new Exception(throwError.toString());
	}

  return rtnVector;
}

/**
 * Load class fields from result set.
 */

private static Inventory loadFieldsInventory(String loadType,
										        ResultSet rs)
		throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Inventory   buildRecord = new Inventory();

	// Not Currently using loadType
	try
	{
		if (loadType.equals("basicInventory"))
		{  
			buildRecord.setItemNumber(rs.getString("MBITNO").trim());
			buildRecord.setItemDescription(rs.getString("MMITDS").trim());
			buildRecord.setQuantityOnHand(rs.getString(3));
		}
	} catch(Exception e)
	{
		throwError.append(" Problem loading the class Inventory ");
		throwError.append("from the result set. " + e) ;
	}
	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServicePlanning.");
		throwError.append("loadFieldsInventory(loadType:");
		throwError.append(loadType + ", rs). ");
		throw new Exception(throwError.toString());
	}
	return buildRecord;
}

/**
 * Return a Vector of Inventory Classes 
 * @param Vector InValues (Vector of Strings, Orders)and Connection.
 * @return Vector (Inventory).
 */
private static Vector findPurchaseOrders(Vector inValues, Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	Vector rtnVector = new Vector();
    ResultSet rs = null;
	PreparedStatement findThem = null;
	try
	{
		try { 
			
			findThem = conn.prepareStatement(buildSqlStatement("poData", inValues));
			rs = findThem.executeQuery();
		} catch(Exception e)
		{
			throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}

//		}
		if (throwError.toString().equals(""))
		{
		   try
		   {
			   while (rs.next())
			   {
					rtnVector.addElement(loadFields("poData", rs));
			   }
		   } catch(Exception e)
		   {
		   	  throwError.append(" error occured while Building Vector from sql statement. " + e);
		   } 
		} // end of the IF statement
	} catch(Exception e)
	{
		throwError.append(" error occured. " + e);
	}
	finally
	{
		try
		{
			if (findThem != null)
				findThem.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception el){
			el.printStackTrace();
		}			
	}	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServicePlanning.");
		throwError.append("findPurchaseOrders(");
		throwError.append("Vector, Connection). ");
		throw new Exception(throwError.toString());
	}

  return rtnVector;
}


/**
 *   // Return a vector of Vectors,  listing Warehouse and Description
 * @param -- currently not using. 
 * @return Vector Warehouse Information.
 * @throws Exception
 */
public static Vector listDropDownPlannedWarehouse(Vector inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector returnValue = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack.getConnection();
		returnValue = listDropDownPlannedWarehouse(inValues, conn);
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
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServicePlanning.");
		throwError.append("listDropDownPlannedWarehouse(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 *   Get a list of Vectors,  including Whse and Description
 * @param -- currently not using. 
 * @return Vector
 * @throws Exception
 */
private static Vector listDropDownPlannedWarehouse(Vector inValues, 
									  		       Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector returnValue = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
	  try {
		// Get the list of DataSet File Names - Along with Descriptions
	  	Vector sendParms = new Vector(); // use when needed
	  	sendParms.addElement("production");
		listThem = conn.prepareStatement(buildSqlStatement("planWhse", sendParms));
		rs = listThem.executeQuery();
	  } catch(Exception e)
	  {
	    throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
	  }
	  if (throwError.toString().equals(""))
	  {
	   try
	   {
	   	  while (rs.next())
		  {
		  	 // Build a Vector of Classes to Return
			  try { 
			  	  Vector rtnList = new Vector();
			  	  rtnList.addElement(rs.getString("MOWHLO").trim());
			  	  rtnList.addElement(rs.getString("MWWHNM").trim());
			  	  returnValue.addElement(rtnList);
			   } catch(Exception e)
			   {
				   throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			   }
	      }// END of the While Statement
       } catch(Exception e)
       {
   	      throwError.append(" Error occured while Building Vector from sql statement. " + e);
       } 
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
		throwError.append("ServicePlanning.");
		throwError.append("listDropDownPlannedWarehouse(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 *   // Return a vector of Vectors,  listing Item Group and Description
 * @param -- currently not using. 
 * @return Vector Item Group Information.
 * @throws Exception
 */
public static Vector listDropDownItemGroup(Vector inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector returnValue = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack.getConnection();
		returnValue = listDropDownItemGroup(inValues, conn);
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
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServicePlanning.");
		throwError.append("listDropDownItemGroup(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 *   Get a list of Vectors,  including Item Group and Description
 * @param -- currently not using. 
 * @return Vector
 * @throws Exception
 */
private static Vector listDropDownItemGroup(Vector inValues, 
									  		Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector returnValue = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
	  try {
		// Get the list of DataSet File Names - Along with Descriptions
	  	Vector sendParms = new Vector(); // use when needed
	  	sendParms.addElement("production");
		listThem = conn.prepareStatement(buildSqlStatement("planItemGroup", sendParms));
		rs = listThem.executeQuery();
	  } catch(Exception e)
	  {
	    throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
	  }
	  if (throwError.toString().equals(""))
	  {
	   try
	   {
	   	  while (rs.next())
		  {
		  	 // Build a Vector of Classes to Return
			  try { 
			  	  Vector rtnList = new Vector();
			  	  rtnList.addElement(rs.getString("MMITGR").trim());
			  	  rtnList.addElement(rs.getString("CTTX40").trim());
			  	  returnValue.addElement(rtnList);
			   } catch(Exception e)
			   {
				   throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			   }
	      }// END of the While Statement
       } catch(Exception e)
       {
   	      throwError.append(" Error occured while Building Vector from sql statement. " + e);
       } 
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
		throwError.append("ServicePlanning.");
		throwError.append("listDropDownItemGroup(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 * Update planned manufacturing orders (MMOPLP).
 * 	
 */
public static void updatePlannedOrdersMO(CommonRequestBean crb)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	BeanPlanning bean = new BeanPlanning();
	
	try {
		// update planned manufacturing orders
		//conn = ConnectionStack.getConnection();
		conn = ServiceConnection.getConnectionStack1();
		bean = updatePlannedOrdersMO(conn, crb);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				//ConnectionStack.returnConnection(conn);
				ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServicePlanning.");
		throwError.append("updatePlannedOrdersMO(");
		throwError.append("). ");		
	}
	// return value
	return;
}

/**
 * Delete all planned orders (MITPLO) linked by order number to planned 
 * manufacturing orders (MMOPLP). 
 */
private static BeanPlanning deletePlannedOrders(Connection conn, CommonRequestBean crb)
								          
throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	BeanPlanning bp = new BeanPlanning();
	Statement deleteThem = null;
	String sql = new String();
	
	try
	{
		// Get and Run the SQL Statement
		try { 
			deleteThem = conn.createStatement();			
			sql = BuildSQL.deletePlannedOrders(crb);
			deleteThem.executeUpdate(sql);			
		} catch(Exception e)
		{
			throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}

	
	} catch(Exception e)
	{
		throwError.append(" error occured. " + e);
	}
	finally
	{
		try
		{
			if (deleteThem != null)
				deleteThem.close();
		} catch(Exception el){
			el.printStackTrace();
		}		
	}	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServicePlanning.");
		throwError.append("deletePlannedOrders(");
		throwError.append("Connection). ");
		bp.setErrorMessage(throwError.toString());
	}
	// return value
	return bp;
}

/**
 * Return a Bean business object. 
 *  Will process the forecast uploaded into WKPLDFCST
 *  Will return Message in the BeanPlanning ** Good or Bad for Display on the Screen **
 * 
 *  Will clear the WKPLDFCST File when done with loading into Movex via API   
 * 
 * @param InqPlanning View Object.
 * @return BeanPlanning Business Object.
 * @throws Exception
 */
public static BeanPlanning updateForecast(UpdPlanning inValues)
{
	StringBuffer throwError = new StringBuffer();
	BeanPlanning bp = new BeanPlanning();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack.getConnection();
		bp = updateForecast(inValues, conn);
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
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServicePlanning.");
		throwError.append("buildPlanningRawFruitRequirements(");
		throwError.append("InqPlanRawFruit). ");
		bp.setErrorMessage(throwError.toString());
	}
	// return value
	return bp;
}

/**
  * Return a Bean business object. 
 *  Will process the forecast uploaded into WKPLDFCST
 *  Will return Message in the BeanPlanning ** Good or Bad for Display on the Screen **
 * 
 *  Will clear the WKPLDFCST File when done with loading into Movex via API   
 * 
 * @param InqPlanning View Object.
 * @return BeanPlanning Business Object.
 * @throws Exception
 */
private static BeanPlanning updateForecast(UpdPlanning inValues, 
								           Connection conn)
throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	BeanPlanning bp = new BeanPlanning();
	PreparedStatement findThem = null;
	ResultSet rs = null;
	try
	{
		// Get and Run the SQL Statement
		try { 
			Vector sendValues = new Vector();
			sendValues.addElement(inValues);
			findThem = conn.prepareStatement(buildSqlStatement("findUploadForecast", sendValues));
			rs = findThem.executeQuery();
		} catch(Exception e)
		{
			throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}

//		}
		if (throwError.toString().equals(""))
		{
		   try
		   {
		   	   // Read Through the PFPUPLOAD File
		   	   String saveYearPeriod = "";
		   	   String sundayDate = "";
		   	   String saturdayDate = "";
		   	   int recordCount = 0;
		       MvxSockJ socketObject = null;
			   while (rs.next())
			   {
			   	   // For Each ONE, you must read the Forecast
//			   	   System.out.println(rs.getString("PFUCITNO") + " " + rs.getString("PFOSPERI"));
			   	   // Go get the Dates from the year and Period from the Forecast
//			   	   System.out.println(rs.getString("PFOSPERI"));
			   	   recordCount++;
			   	   String forecastValue = "Y";
			   	   try
				   {
			   	   	 BigDecimal bdTest = rs.getBigDecimal("PFMFMFOR");
				   }
			   	   catch(Exception e)
				   {
			   	   	  forecastValue = "N";
				   }
			   	 if (forecastValue.equals("Y"))
			   	 {	
			   	   if (!saveYearPeriod.trim().equals(rs.getString("PFOSPERI").trim()))
			   	   {
			   	   	  sundayDate = "";
			   	   	  saturdayDate = "";
			   	   	  try
					  {
//			   	   	  	 System.out.println(rs.getString("WKOSPERI").substring(0,1));
//			   	   	  	 System.out.println(rs.getString("WKOSPERI").substring(1,3));
			   	         Vector returnedDates = UtilityDateTime.getDatesFromYearWeeklyPeriod(("200" + rs.getString("PFOSPERI").substring(0,1)), rs.getString("PFOSPERI").substring(1,3), conn);
			   	         if (returnedDates.size() == 2)
			   	         {
			   	         	sundayDate = ((DateTime) returnedDates.elementAt(0)).getDateFormatyyyyMMdd();
			   	            saturdayDate = ((DateTime) returnedDates.elementAt(1)).getDateFormatyyyyMMdd();
			             }
			   	      }
			   	      catch(Exception e)
					  {}
			   	      saveYearPeriod = rs.getString("PFOSPERI").trim();
			       }
			   	   //------------------------------------------------------
			   	   // Run the Movex API
//			   	   System.out.println("Run API");
			   	   
			   	   try
				   {
			   	   	 // get Socket to send in -- reset every 1000 records
					  if (recordCount == 500)
					  {
						 try
						 {
						 	// Close any open Socket Connections
						 	 socketObject.mvxClose();
						 	System.out.println("Close Socket:" + recordCount );
						 }
						 catch(Exception e)
						 {
							//el.printStackTrace();
						 }					  	
					  	socketObject = null;
					  	recordCount = 1;
					  }
					  if (recordCount == 1)
					  {
						try {
							 // build once EACH time this method is called
							socketObject= BaseAPI.getSockEnv("FCS350MI", inValues.getEnvironment());
							System.out.println("Open Socket:" + recordCount );
						 }
						 catch(Exception e)
						 {
						 	System.out.println("Error: Cannot Open Socket Connection: " + e);
						 	throwError.append("Error: At Record Count: " + recordCount + " Cannot Open Socket Connection: " + e);
						 }	
					  }
					 //----------------------------------------------------
			   	   	// Build the FCS350MIUpdForQty class to send in also
//					FCS350MIUpdForQty sendClass = new FCS350MIUpdForQty();
//					sendClass.setEnvironment(inValues.getEnvironment());
//					sendClass.setSentFromProgram("Update Forecast");
//					sendClass.setUserProfile(inValues.getUserProfile());
//					sendClass.setWarehouse(rs.getString("PFN3NDID"));
//					sendClass.setItemNumber(rs.getString("PFUCITNO"));
//					sendClass.setFromDate(sundayDate);
//					sendClass.setToDate(saturdayDate);
//					sendClass.setForecastQuantity(rs.getString("PFMFMFOR"));
			   	   	
//			   	   	FCS350MI.updForQty(sendClass, socketObject);
				   }
			   	   catch(Exception e)
				   {
                        // Catch Error and move on to the next record
				   }
//			   	  System.out.println("Record Count" + recordCount);
				}
			   }
			   try
			   {
				 	// Close any open Socket Connections
				 socketObject.mvxClose();
			   }
			   catch(Exception e)
			   {
				//el.printStackTrace();
			   }
			   
		   } catch(Exception e)
		   {
		   	  throwError.append(" error occured while Building Vector from sql statement. " + e);
		   } 
		} // end of the IF statement
	} catch(Exception e)
	{
		throwError.append(" error occured. " + e);
	}
	finally
	{
		try
		{
			if (findThem != null)
				findThem.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception el){
			el.printStackTrace();
		}			
	}	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServicePlanning.");
		throwError.append("updateForecast(");
		throwError.append("UpdPlanning, Connection). ");
		bp.setErrorMessage(throwError.toString());
	}
	// return value
	return bp;
}

/**
 * Delete all planned manufacturing orders (MMOPLP). 
 */
private static BeanPlanning deletePlannedOrdersMO(Connection conn, CommonRequestBean crb)
								          
throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	BeanPlanning bp = new BeanPlanning();
	Statement deleteThem = null;
	String sql = new String();
	
	try
	{
		// Get and Run the SQL Statement
		try { 
			deleteThem = conn.createStatement();			
			sql = BuildSQL.deletePlannedOrdersMO(crb);
			deleteThem.executeUpdate(sql);			
		} catch(Exception e)
		{
			throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}

		
	} catch(Exception e)
	{
		throwError.append(" error occured. " + e);
	}
	finally
	{
		try
		{
			if (deleteThem != null)
				deleteThem.close();
		} catch(Exception el){
			el.printStackTrace();
		}		
	}	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServicePlanning.");
		throwError.append("deletePlannedOrdersMO(");
		throwError.append("Connection). ");
		bp.setErrorMessage(throwError.toString());
	}
	// return value
	return bp;
}

/**
 * Delete all planned orders (MITPLO) linked by order number to planned 
 * manufacturing orders (MMOPLP), Then delete all planned manufacturing orders (MMOPLP).
 */
public static void deletePlannedOrders(CommonRequestBean crb)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	BeanPlanning bean = new BeanPlanning();
	
	try {
		// delete planned orders
		//conn = ConnectionStack.getConnection();
		conn = ServiceConnection.getConnectionStack1();
		bean = deletePlannedOrders(conn, crb);
		
		if (bean.getErrorMessage().trim().equals(""))
			// delete planned manufacturing orders		
			bean = deletePlannedOrdersMO(conn, crb);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
			   //ConnectionStack.returnConnection(conn);
				ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServicePlanning.");
		throwError.append("deletePlannedOrders(");
		throwError.append("). ");		
	}
	// return value
	return;
}

/**
 * Update planned manufacturing orders (MMOPLP).
 * 	Will adjust the Dates
 */
private static BeanPlanning updatePlannedOrdersMO(Connection conn, CommonRequestBean crb)
								          
throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	BeanPlanning bp = new BeanPlanning();
	Statement updateThem = null;
	Statement updateThemSupplies = null;
	String sql = new String();
	
	try
	{
		//  Statement will replace the planned date of Supplies needed to be used
		try { 
			updateThemSupplies = conn.createStatement();			
			sql = BuildSQL.updPlannedOrdersMOSupplies(crb);
			updateThemSupplies.executeUpdate(sql);			
		} catch(Exception e)
		{
			throwError.append("Error occured Executing the SQL statement to Update Supply Dates. " + e);
		}
		
		//  Statement will replace the "start" date of Planned MO's with the Finish Date - they should be the same
		try { 
			updateThem = conn.createStatement();			
			sql = BuildSQL.updPlannedOrdersMO(crb);
			updateThem.executeUpdate(sql);			
		} catch(Exception e)
		{
			throwError.append("Error occured Executing the SQL statement to Update Start Date of Planned MO. " + e);
		}

		if (throwError.toString().equals(""))
		{

		} 
	} catch(Exception e)
	{
		throwError.append(" error occured. " + e);
	}
	finally
	{
		try
		{
			if (updateThem != null)
				updateThem.close();
			if (updateThemSupplies != null)
				updateThemSupplies.close();
		} catch(Exception el){
			el.printStackTrace();
		}		
	}	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServicePlanning.");
		throwError.append("updatePlannedOrdersMO(");
		throwError.append("Connection). ");
		bp.setErrorMessage(throwError.toString());
	}
	// return value
	return bp;
}


/**
 * Maintain six weeks of the Planned Orders file(MMOPLP)
 * in work file.
 * @param CommonRequestBean
 * 	
 */
public static void updatePlannedOrdersWorkFile(CommonRequestBean crb)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	try {
		conn = ServiceConnection.getConnectionStack1();
		updatePlannedOrdersWorkFile(conn, crb);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		if (conn != null)
		{
			try
			{
				ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception e){
				throwError.append("Error returning connection. " + e);
				e.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServicePlanning.");
		throwError.append("updatePlannedOrdersWorkFile(");
		throwError.append("). ");		
	}
	// return value
	return;
}


/**
 * Maintain six weeks of the Planned Orders file(MMOPLP)
 * in work file.
 * @param Connection
 * @param CommonRequestBean
 */
private static void updatePlannedOrdersWorkFile(Connection conn, CommonRequestBean crb)
								          
throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	Statement deleteWk  = null;
	Statement deleteWks = null;
	Statement getThem   = null;
	Statement addIt     = null;
	ResultSet rs        = null;
	String sql = new String();
	com.treetop.businessobjects.DateTime date = null;
	String yearWk = "";
	String blastWk = ""; 
	
	try
	{
		
		// delete records for current Fiscal Year and Week if they exist.
		date   = com.treetop.utilities.UtilityDateTime.getSystemDate();
		
		if (date.getM3FiscalWeek().trim().length() == 1)
			yearWk = date.getM3FiscalYear() + "0" +  date.getM3FiscalWeek();
		else
			yearWk = date.getM3FiscalYear() + date.getM3FiscalWeek();
		
		// Get and Run the SQL Statement
		try { 
			deleteWk = conn.createStatement();			
			sql = BuildSQL.deletePlannedWorkFileForWeek(crb, yearWk);
			deleteWk.executeUpdate(sql);
			
			//clear out work file data over 60 days old.
			int no = -60;
			date = com.treetop.utilities.UtilityDateTime.addDaysToDate(date, no);
			
			if (date.getM3FiscalWeek().trim().length() == 1)
				blastWk = date.getM3FiscalYear() + "0" +  date.getM3FiscalWeek();
			else
				blastWk = date.getM3FiscalYear() + date.getM3FiscalWeek();
			
			deleteWks = conn.createStatement();			
			sql = BuildSQL.deletePlannedWorkFilePreviousWeeks(crb, blastWk);
			deleteWks.executeUpdate(sql);
		} catch(Exception e)
		{
			throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}
		
		
		//add records to work file from MMOPLP.
		if (throwError.toString().equals(""))
		{
			try {
				//retrieve records to add.
				getThem = conn.createStatement();
				sql = BuildSQL.getPlannedOrders(crb);
				rs = getThem.executeQuery(sql);
				addIt = conn.createStatement();
				
				while(rs.next())
				{
					sql = BuildSQL.addPlannedWorkFileRecords(crb, rs, yearWk);
					addIt.executeUpdate(sql);
				}
			} catch (Exception e) {
				throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			}

		}
		
		
	} catch(Exception e)
	{
		throwError.append(" error occured. " + e);
	}
	finally
	{
		try {
			if (deleteWk != null)
				deleteWk.close();
		} catch(Exception e){ }
		
		try {
			if (deleteWks != null)
				deleteWks.close();
		} catch(Exception e){ }
		
		try {
			if (getThem != null)
				getThem.close();
		} catch(Exception e){ }
		
		try {
			if (addIt != null)
				addIt.close();
		} catch(Exception e){ }
		
		try {
			if (rs != null)
				rs.close();
		} catch(Exception e){ }
			
	}	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServicePlanning.");
		throwError.append("updatePlannedOrdersWorkFile(");
		throwError.append("Connection, CommonRequestBean). ");
	}
	
}
}
