/*
 * Created on July 31, 2012
 */
package com.treetop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.treetop.businessobjectapplications.BeanOperationsReporting;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.KeyValue;
import com.treetop.businessobjects.ManufacturingOrderDetail;
import com.treetop.businessobjects.Warehouse;
import com.treetop.controller.operations.InqOperations;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.UtilityDateTime;

/**
 * @author Tom Haile.
 * 
 *         Services class to obtain, return and maintain data for Plant
 *         Performance
 * 
 */
@RunWith(Suite.class)
public class ServiceOperationsReporting {
	
	public static final String FORECAST_LABEL = "Dec Fcst";
	
	public enum ReportingType {
		PKG, PROC, BLND, FZCHRY, RAWFRUIT, UTIL, QA, MAINT, WHSE, INVTADJ
	};

	private static class BuildSQL {
		/**
		 * requestType: PKG - Packaging
		 */
		private static String sqlGetMasterList(ReportingType type, InqOperations inqOperations) throws Exception {
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			try {

				// Determine Library
				String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";

				sqlString.append("SELECT ");
				// DCPALL - Descriptive Code File
				sqlString.append("DCPN01, DCPK04, DCPK05 ");

				sqlString.append("FROM " + ttLibrary + "DCPALL ");

				sqlString.append("WHERE DCPTYP = 'V' AND DCPK00 = 'OPSRPT' ");
				sqlString.append("  AND DCPK01 = '" + type.toString() + "' ");
				sqlString.append("  AND DCPK02 = '" + inqOperations.getWarehouse() + "' ");
				sqlString.append("  AND DCPK03 = 'MASTER' ");
				sqlString.append(" ");
				
				sqlString.append("ORDER BY DCPN01 ");

				// *********************************************************************************
			} catch (Exception e) {
				throwError.append(" Error building sqlGetMasterList statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReporting.");
				throwError.append("BuildSQL.sqlGetMasterList(ProcessType:" + type.toString() + ", InqOperations)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}
		
		private static String updateTotalAmount(InqOperations io, BigDecimal amount) {
			StringBuffer sql = new StringBuffer();
			
			String ttLibrary = GeneralUtility.getTTLibrary(io.getEnvironment());
			DateTime dt = UtilityDateTime.getSystemDate();
			
			sql.append(" UPDATE " + ttLibrary + ".OPPWTOTL SET ");
			
			sql.append(" OATOTL = " + new DecimalFormat("0.00").format(amount) + ", ");
			sql.append(" OALMDT = " + dt.getDateFormatyyyyMMdd() + ", ");				//last modified date
			sql.append(" OALMTM = " + dt.getTimeFormathhmmss() + ", ");					//last modified time
			sql.append(" OALMUS = '" + io.getUserProfile() + "' ");						//last modified user

			sql.append(" WHERE ");
			sql.append("     OAWHSE = '" + io.getWarehouse() + "' ");
			sql.append(" AND OADATE = " + io.getEndDate() + " ");

			
			
			return sql.toString();
		}
		
		private static String insertTotalAmount(InqOperations io, BigDecimal amount) {
			StringBuffer sql = new StringBuffer();
			
			String ttLibrary = GeneralUtility.getTTLibrary(io.getEnvironment());
			DateTime dt = UtilityDateTime.getSystemDate();
			
			sql.append(" INSERT INTO " + ttLibrary + ".OPPWTOTL VALUES ( ");
			
			sql.append(" '" + io.getWarehouse() + "', ");						//warehouse
			sql.append(" " + io.getEndDate() + ", ");							//week ending date
			sql.append(" " + new DecimalFormat("0.00").format(amount) + ", ");	//amount
			sql.append(" " + dt.getDateFormatyyyyMMdd() + ", ");				//last modified date
			sql.append(" " + dt.getTimeFormathhmmss() + ", ");					//last modified time
			sql.append(" '" + io.getUserProfile() + "' ");						//last modified user
			
			sql.append(" ) ");
			
			
			return sql.toString();
		}
		
		
		private static String listTotalAmount(InqOperations io) {
			StringBuffer sql = new StringBuffer();
			
			String ttLibrary = GeneralUtility.getTTLibrary(io.getEnvironment());
			String library = GeneralUtility.getLibrary(io.getEnvironment());
			
            sql.append(" SELECT \r");
            sql.append(" YEA4, PERI, WEEK, WHLO, MWWHNM, AMT \r");
            sql.append(" FROM ( \r");
            sql.append(" SELECT \r");
            sql.append(" M.CPYEA4 AS YEA4, \r");
            sql.append(" M.CPPERI AS PERI, \r");
            sql.append(" W.CPPERI AS WEEK, \r");
            sql.append(" OAWHSE AS WHLO, \r");
            sql.append(" OATOTL AMT \r");

            sql.append(" FROM \r");
            sql.append(" " + ttLibrary + ".OPPWTOTL \r");
            sql.append(" INNER JOIN " + library + ".CSYPER AS M ON \r");
            sql.append("      M.CPCONO=100 AND M.CPDIVI='100' \r");
            sql.append("      AND M.CPPETP=1 \r");
            sql.append("      AND M.CPFDAT<=OADATE \r");
            sql.append("      AND M.CPTDAT>=OADATE \r");
            sql.append(" LEFT OUTER JOIN " + library + ".CSYPER AS W ON \r");
            sql.append("      W.CPCONO=100 AND W.CPDIVI='100' \r");
            sql.append("      AND W.CPPETP=2 \r");
            sql.append("      AND W.CPFDAT<=OADATE \r");
            sql.append("      AND W.CPTDAT>=OADATE \r");

            sql.append(" WHERE OADATE >= " + io.getStartDate() + " AND OADATE <= " + io.getEndDate() + " \r");

            sql.append(" UNION ALL \r");

            sql.append(" SELECT \r");
            sql.append(" BVYEA4 AS YEA4, \r");
            sql.append(" BVPERI AS PERI, \r");
            sql.append(" 0 AS WEEK, \r");
            sql.append(" BVWHLO AS WHLO, \r");
            sql.append(" SUM(BVBLAM) AS AMT \r");

            sql.append(" FROM " + ttLibrary + ".BGVPLVAR \r");
            sql.append(" WHERE BVBVER='VRD' \r");
            sql.append(" AND BVYEA4=" + io.getFiscalYear() + " \r");
            sql.append(" AND BVPERI=" +io.getFiscalPeriodStart() + " \r");
            
            sql.append(" AND BVWHLO IN ( \r");
            sql.append(" SELECT DISTINCT DCPK02 \r");
            sql.append(" FROM " + ttLibrary + ".DCPALL \r");
            sql.append(" WHERE DCPK00='OPSRPT' \r");
            sql.append(" ) \r");
            
            sql.append(" GROUP BY BVYEA4, BVPERI, BVWHLO \r");

            sql.append(" ) AS A \r");
            sql.append(" LEFT OUTER JOIN " + library + ".MITWHL ON \r");
            sql.append("      MWCONO=100 AND MWWHLO=WHLO \r");

            sql.append(" ORDER BY YEA4, PERI, WEEK, WHLO \r");
			
			
			return sql.toString();
		}

		
		private static String getWeeksInMonth(InqOperations inqOperations) throws Exception {
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			try {

				// Determine Library
				String Library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";

				sqlString.append(" SELECT ");
				// DCPALL - Descriptive Code File
				sqlString.append(" W.CPPERI AS WEEK ");

				sqlString.append(" FROM M3DJDPRD.CSYPER AS W ");
				sqlString.append(" INNER JOIN M3DJDPRD.CSYPER AS M ON ");
				sqlString.append("       W.CPCONO=M.CPCONO AND W.CPDIVI=M.CPDIVI AND M.CPPETP=1 ");
				sqlString.append("       AND W.CPFDAT>=M.CPFDAT AND W.CPTDAT<=M.CPTDAT ");
				sqlString.append("       AND M.CPYEA4=" + inqOperations.getFiscalYear() + " ");
				sqlString.append("       AND M.CPPERI=" + inqOperations.getFiscalPeriodStart() + " ");
				
				sqlString.append(" WHERE W.CPCONO=100 AND W.CPDIVI='100' ");
				sqlString.append(" AND W.CPPETP=2 ");
				
				sqlString.append(" ORDER BY W.CPPERI ");

				
			} catch (Exception e) {
				throwError.append(" Error building getWeeksInMonth statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReporting.");
				throwError.append("getWeeksInMonth()");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}
		
	}

	/**
	 * Load Packaging Data
	 * 
	 * @param ResultSet
	 * @param BeanOperationsReporting
	 * @return BeanOperationsReporting
	 */
	private static class LoadFields {
		/**
		 * To be used to Build the initial Linked HashMap
		 */
		private static void loadMasterList(LinkedHashMap<String, ManufacturingOrderDetail> lhm, ResultSet rs)
				throws Exception {

			StringBuffer throwError = new StringBuffer();
			
			try {

				try {
					while (rs.next()) {
						
						String key = buildKey(rs);
						
						ManufacturingOrderDetail m = new ManufacturingOrderDetail();

						lhm.put(key, m);
					}

				} catch (Exception e) {
					throwError.append(" Error occurred loading Packaging data. " + e);
				}

			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReporting.");
				throwError.append("loadMasterList(ResultSet). " + e);
				throw new Exception(throwError.toString());
			}

			
		}
		
		private static void weeksInMonth(ResultSet rs, List<String> weeks)
				throws Exception {

			StringBuffer throwError = new StringBuffer();


			
			try {

				while (rs.next()) {

					String week = rs.getString("WEEK").trim();
					weeks.add(week);
					
				}

			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReporting.");
				throwError.append("weeksInMonth(ResultSet, List<String>). " + e);
				throw new Exception(throwError.toString());
			}


		}
		
		private static void loadTotalAmount(InqOperations io, ResultSet rs, LinkedHashMap<String, SortedMap<String, BigDecimal>> lhm)
		throws Exception {
			
			StringBuffer throwError = new StringBuffer();
			
			try {

				
				
				while (rs.next()) {
					
		
					String warehouse = rs.getString("WHLO").trim();
					String warehouseName = rs.getString("MWWHNM").trim();
					
					String warehouseKey = warehouse + " - " + warehouseName;
					
					SortedMap<String, BigDecimal> values = lhm.get(warehouseKey);
					if (values == null) {
						
						values = new TreeMap<String, BigDecimal>();
						lhm.put(warehouseKey, values);
						
					}
					
					String week = rs.getString("WEEK").trim();
					String period = rs.getString("PERI").trim();
					BigDecimal amount = rs.getBigDecimal("AMT");
					
					
					if (week.equals("0")) {
						// load forecast
						if (values.get("FCST") == null) {
							values.put("FCST", amount);
						} else {
							values.put("FCST", values.get("FCST").add(amount));
						}
						
					} else {
						// load actuals

						values.put(week, amount);
						
						if (values.get("MTD") == null) {
							values.put("MTD", amount);
						} else {
							values.put("MTD", values.get("MTD").add(amount));
						}
						
						
					}
					

					
					
				}

			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReporting.");
				throwError.append("loadTotalAmount(ResultSet). " + e);
				throw new Exception(throwError.toString());
			}
			
		}
		
	}

	/**
	 * Add Warehouse data to Bean Data
	 * 
	 * @param InqOperations
	 * @return will not return anything
	 * @throws Exception
	 * 
	 */
	public static void getOperationsWarehouse(InqOperations inValues) throws Exception {
		StringBuffer throwError = new StringBuffer();

		// validate incoming data
		if (inValues == null) {
			throwError.append("Inquiry bean cannot be null.  ");
		} else {
			if (inValues.getEnvironment().equals("")) {
				throwError.append("Environment cannot be empty.  ");
			}
			if (inValues.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be empty.  ");
			}
		}

		if (throwError.toString().trim().equals("")) {
			// initial validation passed

			try {
				Vector<String> vector = new Vector<String>();
				vector.addElement(inValues.getWarehouse());
				Warehouse whse = ServiceWarehouse.findWarehouse(inValues.getEnvironment(), vector);
				inValues.getBean().setWarehouse(whse);
			} catch (Exception e) {
				throwError.append(e);
			}

			finally {

			}
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReporting.");
			throwError.append("getOperationsWarehouse(InqOperations). ");
			throw new Exception(throwError.toString());
		}
	}

	/**
	 * Return after initializing the LinkedHashmap in the Bean, which is in the
	 * inValues object
	 * 
	 * @param InqOperations
	 * @return nothing, pointer should still work,
	 * @throws Exception
	 * 
	 */
	private static void getMasterData(InqOperations inValues, ReportingType type,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement getThem = null;
		BeanOperationsReporting thisBean = inValues.getBean();

		try // catch all
		{
			try {
				
				String sqlString = BuildSQL.sqlGetMasterList(type, inValues);
				getThem = conn.prepareStatement(sqlString);
				rs = getThem.executeQuery();
				
			} catch (Exception e) {
				throwError.append("Error occured Building or Executing a sql statement. " + e);
			}

			if (throwError.toString().equals("")) {

				LoadFields.loadMasterList(lhm, rs);
				// 1/8/13 -TW build the Linked Hashmap within the LoadMasterList
				// try {
				// while (rs.next()) {
				// lhm.put(LoadFields.loadMasterList(rs), new
				// ManufacturingOrderDetail());
				// }

				// } catch (Exception e) {
				// throwError.append(" Error occurred loading Packaging data. "
				// + e);
				// }

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
			if (getThem != null) {
				try {
					getThem.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
			// inValues.setBean(thisBean);
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReporting.");
			throwError.append("getPackagingMasterData(");
			throwError.append("InqOperations, ProcessType:");
			throwError.append(type.toString() + ", conn). ");
			throw new Exception(throwError.toString());
		}

	}

	public static LinkedHashMap<String, ManufacturingOrderDetail> getMos(InqOperations inValues, ReportingType type)
			throws Exception {
		StringBuffer throwError = new StringBuffer();

		if (inValues == null) {
			throwError.append("Inquiry Bean object cannot be null.  ");
		}

		LinkedHashMap<String, ManufacturingOrderDetail> lhm = null;
		if (throwError.toString().trim().equals("")) {

			try {

				if (type == ReportingType.PROC) {
					lhm = inValues.getBean().getProcessingMOs();
				} else if (type == ReportingType.PKG) {
					lhm = inValues.getBean().getPackagingMOs();
				} else if (type == ReportingType.BLND) {
					lhm = inValues.getBean().getBlendingMOs();
				} else if (type == ReportingType.FZCHRY) {
					lhm = inValues.getBean().getFrozenCherryMOs();
				} else if (type == ReportingType.RAWFRUIT) {
					lhm = inValues.getBean().getRawFruitMOs();
				} 
				
				if (lhm.isEmpty()) {

					Connection conn = null;

					if (throwError.toString().trim().equals("")) {
						// passed initial validation
						try {
							conn = ServiceConnection.getConnectionStack11();
						} catch (Exception e) {
							throwError.append(e);
						}
					}

					try {

						getMasterData(inValues, type, lhm, conn);

					} catch (Exception e) {
						throwError.append(e);

					} finally {
						if (conn != null) {
							try {
								ServiceConnection.returnConnectionStack11(conn);
							} catch (Exception el) {
								el.printStackTrace();
							}
						}
					}

				}

				// TODO - get master data for MO's if they are empty.

			} catch (Exception e) {
				throwError.append(e);
			}

		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReporting.");
			throwError.append("getMos(InqOperations, ProcessType:");
			throwError.append(type.toString() + "). ");
			throw new Exception(throwError.toString());
		}

		return lhm;

	}
	
	
	public static void storeTotalAmount(InqOperations io, BigDecimal amount) throws Exception {
		
		StringBuffer throwError = new StringBuffer();
		
		Connection conn = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack11();
			storeTotalAmount(conn, io, amount);
			
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (conn != null) {
				try {
					ServiceConnection.returnConnectionStack11(conn);
				} catch (Exception e) {}
			}
		}
		
		
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReporting.");
			throwError.append("storeTotalAmount() ");
			throw new Exception(throwError.toString());
		}
		
	}

	
	private static void storeTotalAmount(Connection conn, InqOperations io, BigDecimal amount)
	throws Exception {
		
		StringBuffer throwError = new StringBuffer();

		Statement stmt = null;
		
		try {
			
			stmt = conn.createStatement();
			
			String updateSql = BuildSQL.updateTotalAmount(io, amount);
			int updated = stmt.executeUpdate(updateSql);
			
			if (updated == 0) {
				//no records updated, insert the record
				
				String insertSql = BuildSQL.insertTotalAmount(io, amount);
				stmt.executeUpdate(insertSql);
				
			}
			
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {}
			}
		}
		
		
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReporting.");
			throwError.append("storeTotalAmount() ");
			throw new Exception(throwError.toString());
		}
		
	}
	
	public static void listTotalAmount(InqOperations io) throws Exception {
		
		StringBuffer throwError = new StringBuffer();
		
		Connection conn = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack11();
			listTotalAmount(conn, io);
			
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (conn != null) {
				try {
					ServiceConnection.returnConnectionStack11(conn);
				} catch (Exception e) {}
			}
		}
		
		
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReporting.");
			throwError.append("listTotalAmount() ");
			throw new Exception(throwError.toString());
		}
		
	}
	
	private static void listTotalAmount(Connection conn, InqOperations io)
	throws Exception {
		
		StringBuffer throwError = new StringBuffer();

		Statement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt = conn.createStatement();
			
			String sql = BuildSQL.listTotalAmount(io);
			rs = stmt.executeQuery(sql);
			
			LinkedHashMap<String, SortedMap<String, BigDecimal>> lhm = io.getBean().getPlantSummaries();
			
			LoadFields.loadTotalAmount(io, rs, lhm);
			
			
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {}
			}
		}
		
		
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReporting.");
			throwError.append("listTotalAmount() ");
			throw new Exception(throwError.toString());
		}
		
	}
	
	public static void getWeeksInMonth(InqOperations inqOperations) throws Exception {

		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack11();
			getWeeksInMonth(conn, inqOperations);
			
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (conn != null) {
				try {
					ServiceConnection.returnConnectionStack11(conn);
				} catch (Exception e) {}
			}
		}
		
		
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReporting.");
			throwError.append("getWeeksInMonth() ");
			throw new Exception(throwError.toString());
		}

	}
	
	private static void getWeeksInMonth(Connection conn, InqOperations inqOperations) throws Exception {

		
		StringBuffer throwError = new StringBuffer();

		Statement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt = conn.createStatement();
			
			String sql = BuildSQL.getWeeksInMonth(inqOperations);
			rs = stmt.executeQuery(sql);
			List<String> weeks = inqOperations.getWeeksInMonth();
			LoadFields.weeksInMonth(rs, weeks);
			
			
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {}
			}
		}
		
		
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReporting.");
			throwError.append("getWeeksInMonth() ");
			throw new Exception(throwError.toString());
		}
		

	}
	
	
	
	public static void getWeeklyComments(InqOperations inqOperations, String[] commentTypes) throws Exception {

		StringBuffer throwError = new StringBuffer();

		
		try {
			
			KeyValue keys = new KeyValue();
			keys.setEnvironment(inqOperations.getEnvironment());
			keys.setEntryType("OperationsReporting");
			
			keys.setKey2(inqOperations.getWarehouse());
			keys.setKey3(inqOperations.getFiscalYear());

			if (inqOperations.getWeeksInMonth().isEmpty()) {
				getWeeksInMonth(inqOperations);
			}
			
			keys.setVisibleOnLoad(true);
			keys.setViewOnly(false);
			keys.setHeaderText(null);
			
			
			for (String commentType : commentTypes) {
				
				keys.setKey1(commentType); 
				
				Vector<KeyValue> comments = new Vector<KeyValue>();
				
				for (String week : inqOperations.getWeeksInMonth()) {
					keys.setKey5(week);
					@SuppressWarnings("unchecked")
					Vector<KeyValue> c = ServiceKeyValue.buildKeyValueList(keys);
					for (KeyValue j : c) {
						j.setDescription("Week " + week);
						comments.add(j);
					}
					
				}
				
				inqOperations.getBean().getWeeklyComments().put(commentType, comments);
				
			}
			
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			
		}
		
		
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReporting.");
			throwError.append("getWeeklyComments() ");
			throw new Exception(throwError.toString());
		}

	}
	
	
	

	@RunWith(Suite.class)
	@Suite.SuiteClasses({ ServiceOperationsReporting.UnitTests.Medford.class,
			ServiceOperationsReporting.UnitTests.Prosser.class, 
			ServiceOperationsReporting.UnitTests.Selah.class,
			ServiceOperationsReporting.UnitTests.Ross.class,
			ServiceOperationsReporting.UnitTests.Wenatchee.class,
			ServiceOperationsReporting.UnitTests.Woodburn.class,
			ServiceOperationsReporting.UnitTests.ExecSummary.class,
			ServiceOperationsReporting.UnitTests.Shared.class,})
	public static class UnitTests {

		private static InqOperations inqOperations = null;


		
		@Before
		public void setUp() {
			inqOperations = new InqOperations();
		}
		
		public static class ExecSummary {
			@Test
			public void testListTotalAmount() throws Exception {
				
				inqOperations = new InqOperations();
				inqOperations.setEnvironment("PRD");
				inqOperations.setWarehouse("209");
				inqOperations.setFiscalYear("2014");
				inqOperations.setFiscalPeriodStart("1");
				inqOperations.setFiscalPeriodEnd("1");
				inqOperations.setFiscalWeekStart("1");
				inqOperations.setFiscalWeekEnd("1");
				inqOperations.buildDateRangeFromWeek();
				
				Integer[] weeks = inqOperations.getWeekInPeriod(4);
				for (int week : weeks) {
					System.out.print(week + "\t");
				}
				
				//listTotalAmount(inqOperations);
				
			}
		}
		
		public static class Shared {
			
			@Test
			public void testGetWeeksInMonth() {
				try {

					inqOperations.setFiscalYear("2014");
					inqOperations.setFiscalPeriodStart("1");
					
					getWeeksInMonth(inqOperations);
					List<String> weeks = inqOperations.getWeeksInMonth();
					
					assertEquals("Count of weeks", weeks.size(), 4);
					
					for (int i=0; i<weeks.size(); i++) {
						String week = weeks.get(i);
						if (i == 0) {
							assertEquals("Week 1", week, "1");
						}
						if (i == 1) {
							assertEquals("Week 2", week, "2");
						}
						if (i == 2) {
							assertEquals("Week 3", week, "3");
						}
						if (i == 3) {
							assertEquals("Week 4", week, "4");
						}
					}
				} catch (Exception e) {
					fail(e.toString());
				}
			}

			@Test
			public void testGetWeeklyComments() {
				InqOperations inqOperations = new InqOperations();
				inqOperations.setEnvironment("PRD");
				inqOperations.setWarehouse("209");
				inqOperations.setFiscalYear("2014");
				inqOperations.setFiscalPeriodStart("1");
				
				try {
				
					
					
					//exec service to load safety comments
					getWeeklyComments(inqOperations, new String[] {"safety","packaging"});
					
					Hashtable<String, Vector<KeyValue>> commentTable = inqOperations.getBean().getWeeklyComments();
						
					Vector<KeyValue> safetyComments = commentTable.get("safety");
					for (int i=0; i<safetyComments.size(); i++) {
						if (i==0) {
							KeyValue comment = safetyComments.get(i);
							assertEquals("Week 1 Safety",comment.getValue(),"Days w/o LTA 469\r\nDays w/o MA 81");
						}
					}
					
					
					Vector<KeyValue> packagingComments = commentTable.get("packaging");
					for (int i=0; i<packagingComments.size(); i++) {
						if (i==0) {
							KeyValue comment = packagingComments.get(i);
							assertEquals("Week 1 Packaging",comment.getValue(),"Line 2 - Small run sizes in both 8-pk and 9-pk configurations\r\n" +
									"Pouch - Leaker sortation; heater element failure and replacement\r\n" +
									"VP - Cascade Ice film (eye mark sensor failure)\r\n" +
									"Training - new employee training\r\n" +
									"Rework - L8 leaker sortation\r\n" +
									"Other - Rework sortation of L8 leakers and rewrap");
						}
						if (i==1) {
							KeyValue comment = packagingComments.get(i);
							assertEquals("Week 2 Packaging",comment.getValue(),"Pouch line unfavorable - Daily Inspection/sortation & CIP.");
						}
					}

				} catch (Exception e) {
					fail(e.toString());
				}
				
			}
			
		}
		
		public static class Medford {

			private static InqOperations io = null;

			@Before
			public void setUp() throws Exception {
				io = new InqOperations();
				io.setEnvironment("PRD");
				io.setWarehouse("280");
				io.setFiscalYear("2013");
				io.setFiscalPeriodStart("9");
				io.setFiscalPeriodEnd("9");
				io.setFiscalWeekStart("37");
				io.setFiscalWeekEnd("37");
				io.buildDateRangeFromWeek();

				getOperationsWarehouse(io);
			}
			
			@Test
			public void testProcessing() throws Exception {
				
				ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = getMos(io, ReportingType.PROC);
				
				System.out.println("Row\t" + GeneralUtility.getObjectFieldNames(new ManufacturingOrderDetail()));
				int i = 0;
				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail m = lhm.get(key);
					System.out.println(key + "\t" + GeneralUtility.getObjectFieldData(m));
				}
				
				
			}

		}
		
		public static class Oxnard {

			private static InqOperations io = null;

			@Before
			public void setUp() throws Exception {
				io = new InqOperations();
				io.setEnvironment("PRD");
				io.setWarehouse("251");
				io.setFiscalYear("2012");
				io.setFiscalPeriodStart("11");
				io.setFiscalPeriodEnd("11");
				io.setFiscalWeekStart("45");
				io.setFiscalWeekEnd("45");
				io.buildDateRangeFromWeek();

				getOperationsWarehouse(io);
			}
			
			@Test
			public void testProcessing() throws Exception {
				
				ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = getMos(io, ReportingType.PROC);
				
				System.out.println("Row\t" + GeneralUtility.getObjectFieldNames(new ManufacturingOrderDetail()));
				int i = 0;
				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail m = lhm.get(key);
					System.out.println(key + "\t" + GeneralUtility.getObjectFieldData(m));
				}
				
				
			}

		}
		
		public static class FreshSlice {

			private static InqOperations io = null;

			@Before
			public void setUp() throws Exception {
				io = new InqOperations();
				io.setEnvironment("PRD");
				io.setWarehouse("490");
				io.setFiscalYear("2013");
				io.setFiscalPeriodStart("9");
				io.setFiscalPeriodEnd("9");
				io.setFiscalWeekStart("35");
				io.setFiscalWeekEnd("35");
				io.buildDateRangeFromWeek();

				getOperationsWarehouse(io);
			}
			
			@Test
			public void testProcessing() throws Exception {
				
				ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = getMos(io, ReportingType.PROC);
				
				System.out.println("Row\t" + GeneralUtility.getObjectFieldNames(new ManufacturingOrderDetail()));
				int i = 0;
				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail m = lhm.get(key);
					System.out.println(key + "\t" + GeneralUtility.getObjectFieldData(m));
				}
				
				
			}

		}

		public static class Ross {

			private static InqOperations io = null;

			@Before
			public void setUp() throws Exception {
				io = new InqOperations();
				io.setEnvironment("PRD");
				io.setWarehouse("240");
				io.setFiscalYear("2013");
				io.setFiscalPeriodStart("5");
				io.setFiscalPeriodEnd("5");
				io.setFiscalWeekStart("18");
				io.setFiscalWeekEnd("21");
				io.buildDateRangeFromWeek();

				getOperationsWarehouse(io);
			}
			
			@Test
			public void testProcessing() throws Exception {
				
				ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
				
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = getMos(io, ReportingType.PROC);
				
				System.out.println("Row\t" + GeneralUtility.getObjectFieldNames(new ManufacturingOrderDetail()));
				int i = 0;
				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail m = lhm.get(key);
					System.out.println(key + "\t" + GeneralUtility.getObjectFieldData(m));
					
				}
				
			}
			
			@Test
			public void testRawFruit() throws Exception {
				
				ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
				
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = getMos(io, ReportingType.RAWFRUIT);
				
				System.out.println("Row\t" + GeneralUtility.getObjectFieldNames(new ManufacturingOrderDetail()));
				int i = 0;
				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail m = lhm.get(key);
					System.out.println(key + "\t" + GeneralUtility.getObjectFieldData(m));
					
				}
				
			}

		}
		
		public static class Wenatchee {

			private static InqOperations io = null;

			@Before
			public void setUp() throws Exception {
				io = new InqOperations();
				io.setEnvironment("PRD");
				io.setWarehouse("230");
				io.setFiscalYear("2013");
				io.setFiscalPeriodStart("9");
				io.setFiscalPeriodEnd("9");
				io.setFiscalWeekStart("39");
				io.setFiscalWeekEnd("39");
				io.buildDateRangeFromWeek();

				getOperationsWarehouse(io);
			}
			
			@Test
			public void testProcessing() throws Exception {
				
				ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = getMos(io, ReportingType.PROC);
				
				System.out.println("Row\t" + GeneralUtility.getObjectFieldNames(new ManufacturingOrderDetail()));
				int i = 0;
				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail m = lhm.get(key);
					System.out.println(key + "\t" + GeneralUtility.getObjectFieldData(m));
				}
				
				
			}
			
			@Test
			public void testRawFruit() throws Exception {
				
				ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
				
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = getMos(io, ReportingType.RAWFRUIT);
				
				System.out.println("Row\t" + GeneralUtility.getObjectFieldNames(new ManufacturingOrderDetail()));
				int i = 0;
				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail m = lhm.get(key);
					System.out.println(key + "\t" + GeneralUtility.getObjectFieldData(m));
					
				}
				
			}

		}
		
		public static class Prosser {

			private static InqOperations io = null;

			@Before
			public void setUp() throws Exception {
				io = new InqOperations();
				io.setEnvironment("PRD");
				io.setWarehouse("469");
				io.setFiscalYear("2013");
				io.setFiscalPeriodStart("8");
				io.setFiscalPeriodEnd("8");
				io.setFiscalWeekStart("35");
				io.setFiscalWeekEnd("35");
				io.buildDateRangeFromWeek();

				getOperationsWarehouse(io);
			}
			
			@Test
			public void testRawFruit() throws Exception {
				ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = getMos(io, ReportingType.RAWFRUIT);
				int i = 0;
				System.out.println("Row\t" + GeneralUtility.getObjectFieldNames(new ManufacturingOrderDetail()));
				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail m = lhm.get(key);
					System.out.println(key + "\t" + GeneralUtility.getObjectFieldData(m));
				}
				
			}
			
			@Test
			public void testPackaging() throws Exception {
				ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PKG);
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = getMos(io, ReportingType.PKG);
				int i = 0;
				System.out.println("Row\t" + GeneralUtility.getObjectFieldNames(new ManufacturingOrderDetail()));
				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail m = lhm.get(key);
					System.out.println(key + "\t" + GeneralUtility.getObjectFieldData(m));
				}
				
			}

		}

		public static class Selah {

			private static InqOperations io = null;

			@Before
			public void setUp() throws Exception {
				io = new InqOperations();
				io.setEnvironment("PRD");
				io.setWarehouse("209");
				io.setFiscalYear("2013");
				io.setFiscalPeriodStart("9");
				io.setFiscalPeriodEnd("9");
				io.setFiscalWeekStart("37");
				io.setFiscalWeekEnd("37");
				io.buildDateRangeFromWeek();

				getOperationsWarehouse(io);
			}

			@Test
			public void testRawFruit() throws Exception {
				ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = getMos(io, ReportingType.RAWFRUIT);
				int i = 0;
				System.out.println("Row\t" + GeneralUtility.getObjectFieldNames(new ManufacturingOrderDetail()));
				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail m = lhm.get(key);
					System.out.println(key + "\t" + GeneralUtility.getObjectFieldData(m));
/*					
					if (i == 0) {
						assertEquals("Raw Fruit element " + i, "Pear Slurry", key);
						assertEquals(key + " Usage Raw Fruit Forecast", new BigDecimal("0"), m.getUsageRawFruitForecast());
						assertEquals(key + " Usage Raw Fruit Planned", new BigDecimal("0"), m.getUsageRawFruitPlanned());
						assertEquals(key + " Usage Raw Fruit Standard", new BigDecimal("0"), m.getUsageRawFruitStandard());
						assertEquals(key + " Usage Raw Fruit Actual", new BigDecimal("0"), m.getUsageRawFruitActual());
					}
					
					if (i == 1) {
						assertEquals("Raw Fruit element " + i, "Pear Conv", key);
						assertEquals(key + " Usage Raw Fruit Forecast", new BigDecimal("0"), m.getUsageRawFruitForecast());
						assertEquals(key + " Usage Raw Fruit Planned", new BigDecimal("0"), m.getUsageRawFruitPlanned());
						assertEquals(key + " Usage Raw Fruit Standard", new BigDecimal("0"), m.getUsageRawFruitStandard());
						assertEquals(key + " Usage Raw Fruit Actual", new BigDecimal("0"), m.getUsageRawFruitActual());
					}
					
					if (i == 2) {
						assertEquals("Raw Fruit element " + i, "Pear Org", key);
						assertEquals(key + " Usage Raw Fruit Forecast", new BigDecimal("0"), m.getUsageRawFruitForecast());
						assertEquals(key + " Usage Raw Fruit Planned", new BigDecimal("0"), m.getUsageRawFruitPlanned());
						assertEquals(key + " Usage Raw Fruit Standard", new BigDecimal("0"), m.getUsageRawFruitStandard());
						assertEquals(key + " Usage Raw Fruit Actual", new BigDecimal("0"), m.getUsageRawFruitActual());
					}
					
					if (i == 3) {
						assertEquals("Raw Fruit element " + i, "NFC Apple Conv", key);
						assertEquals(key + " Usage Raw Fruit Forecast", new BigDecimal("1248.75"), m.getUsageRawFruitForecast());
						assertEquals(key + " Usage Raw Fruit Planned", new BigDecimal("352.00"), m.getUsageRawFruitPlanned());
						assertEquals(key + " Usage Raw Fruit Standard", new BigDecimal("571.46"), m.getUsageRawFruitStandard());
						assertEquals(key + " Usage Raw Fruit Actual", new BigDecimal("578.89"), m.getUsageRawFruitActual());
					}
					
					if (i == 4) {
						assertEquals("Raw Fruit element " + i, "NFC Apple Org", key);
						assertEquals(key + " Usage Raw Fruit Forecast", new BigDecimal("0"), m.getUsageRawFruitForecast());
						assertEquals(key + " Usage Raw Fruit Planned", new BigDecimal("259.00"), m.getUsageRawFruitPlanned());
						assertEquals(key + " Usage Raw Fruit Standard", new BigDecimal("289.17"), m.getUsageRawFruitStandard());
						assertEquals(key + " Usage Raw Fruit Actual", new BigDecimal("261.97"), m.getUsageRawFruitActual());
					}
					
					if (i == 5) {
						assertEquals("Raw Fruit element " + i, "Hot Apple Conv", key);
						assertEquals(key + " Usage Raw Fruit Forecast", new BigDecimal("0"), m.getUsageRawFruitForecast());
						assertEquals(key + " Usage Raw Fruit Planned", new BigDecimal("664.00"), m.getUsageRawFruitPlanned());
						assertEquals(key + " Usage Raw Fruit Standard", new BigDecimal("521.28"), m.getUsageRawFruitStandard());
						assertEquals(key + " Usage Raw Fruit Actual", new BigDecimal("614.39"), m.getUsageRawFruitActual());
					}
					
					if (i == 6) {
						assertEquals("Raw Fruit element " + i, "Hot Apple Org", key);
						assertEquals(key + " Usage Raw Fruit Forecast", new BigDecimal("0"), m.getUsageRawFruitForecast());
						assertEquals(key + " Usage Raw Fruit Planned", new BigDecimal("137.00"), m.getUsageRawFruitPlanned());
						assertEquals(key + " Usage Raw Fruit Standard", new BigDecimal("177.36"), m.getUsageRawFruitStandard());
						assertEquals(key + " Usage Raw Fruit Actual", new BigDecimal("174.04"), m.getUsageRawFruitActual());
					}
					
					if (i == 7) {
						assertEquals("Raw Fruit element " + i, "Apple Slurry", key);
						assertEquals(key + " Usage Raw Fruit Forecast", new BigDecimal("0"), m.getUsageRawFruitForecast());
						assertEquals(key + " Usage Raw Fruit Planned", new BigDecimal("0"), m.getUsageRawFruitPlanned());
						assertEquals(key + " Usage Raw Fruit Standard", new BigDecimal("29.64"), m.getUsageRawFruitStandard());
						assertEquals(key + " Usage Raw Fruit Actual", new BigDecimal("0.00"), m.getUsageRawFruitActual());
					}
					
					if (i == 8) {
						assertEquals("Raw Fruit element " + i, "Sauce Apple Conv", key);
						assertEquals(key + " Usage Raw Fruit Forecast", new BigDecimal("772.25"), m.getUsageRawFruitForecast());
						assertEquals(key + " Usage Raw Fruit Planned", new BigDecimal("730.00"), m.getUsageRawFruitPlanned());
						assertEquals(key + " Usage Raw Fruit Standard", new BigDecimal("664.18"), m.getUsageRawFruitStandard());
						assertEquals(key + " Usage Raw Fruit Actual", new BigDecimal("620.53"), m.getUsageRawFruitActual());
					}
					
					if (i == 9) {
						assertEquals("Raw Fruit element " + i, "Sauce Apple Org", key);
						assertEquals(key + " Usage Raw Fruit Forecast", new BigDecimal("0"), m.getUsageRawFruitForecast());
						assertEquals(key + " Usage Raw Fruit Planned", new BigDecimal("0"), m.getUsageRawFruitPlanned());
						assertEquals(key + " Usage Raw Fruit Standard", new BigDecimal("0"), m.getUsageRawFruitStandard());
						assertEquals(key + " Usage Raw Fruit Actual", new BigDecimal("0"), m.getUsageRawFruitActual());
					}
*/
					i++;
				}

			}

			@Test
			public void testProcessing() throws Exception {
				ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = io.getBean().getProcessingMOs();
				
				System.out.println("Row\t" + GeneralUtility.getObjectFieldNames(new ManufacturingOrderDetail()));
				int i = 0;
				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail m = lhm.get(key);
					System.out.println(key + "\t" + GeneralUtility.getObjectFieldData(m));
					/*
					
					if (i == 0) {
						assertEquals("Processing element " + i, "Pear Slurry - Conv", key);

					}
					
					if (i == 1) {
						assertEquals("Processing element " + i, "Pear Slurry - Org", key);

					}
					
					if (i == 2) {
						assertEquals("Processing element " + i, "Hard Pear - Conv", key);

					}
					
					if (i == 3) {
						assertEquals("Processing element " + i, "Hard Pear - Org", key);

					}
					
					if (i == 4) {
						assertEquals("Processing element " + i, "NFC - Conv", key);
						assertEquals(key + " Usage Yield ",
								new BigDecimal("578.8905"), m.getUsageRawFruitActual());
						assertEquals(key + " Production FS at Std ",
								new BigDecimal("95435.0970"), m.getProductionFsAtStd());
						
						assertEquals(key + " Yield ",
								new BigDecimal("164.8587"), m.getYieldActual());
						
						assertEquals(key + " Material Variance Usage ",
								new BigDecimal("-1558.85"), m.getCostVarianceUsage());
						assertEquals(key + " Material Variance Substitution ",
								new BigDecimal("0.29"), m.getCostVarianceSubstitution());
						assertEquals(key + " Material Variance Total ",
								new BigDecimal("-1558.56"), m.getCostVarianceTotal());
						assertEquals(key + " Material Variance Supplies ",
								new BigDecimal("445.96"), m.getCostVarianceSupplies());
						
						
						
					}
					
					if (i == 5) {
						assertEquals("Processing element " + i, "NFC - Org", key);
						assertEquals(key + " Usage Yield ",
								new BigDecimal("261.9670"), m.getUsageRawFruitActual());
						assertEquals(key + " Production FS at Std ",
								new BigDecimal("43116.5070"), m.getProductionFsAtStd());
						
						assertEquals(key + " Yield ",
								new BigDecimal("164.5876"), m.getYieldActual());
						
						assertEquals(key + " Material Variance Usage ",
								new BigDecimal("8723.78"), m.getCostVarianceUsage());
						assertEquals(key + " Material Variance Substitution ",
								new BigDecimal("-15026.14"), m.getCostVarianceSubstitution());
						assertEquals(key + " Material Variance Total ",
								new BigDecimal("-6302.36"), m.getCostVarianceTotal());
						assertEquals(key + " Material Variance Supplies ",
								new BigDecimal("0.15"), m.getCostVarianceSupplies());
					}
					
					if (i == 6) {
						assertEquals("Processing element " + i, "Type S - Conv", key);
						assertEquals(key + " Usage Yield ",
								new BigDecimal("571.4680"), m.getUsageRawFruitActual());
						assertEquals(key + " Production FS at Std ",
								new BigDecimal("26338.3447"), m.getProductionFsAtStd());
						
						assertEquals(key + " Yield ",
								new BigDecimal("46.0890"), m.getYieldActual());
						
						assertEquals(key + " Material Variance Usage ",
								new BigDecimal("-4813.29"), m.getCostVarianceUsage());
						assertEquals(key + " Material Variance Substitution ",
								new BigDecimal("0.00"), m.getCostVarianceSubstitution());
						assertEquals(key + " Material Variance Total ",
								new BigDecimal("-4813.29"), m.getCostVarianceTotal());
						assertEquals(key + " Material Variance Supplies ",
								new BigDecimal("0"), m.getCostVarianceSupplies());
					}
					
					if (i == 7) {
						assertEquals("Processing element " + i, "Type S - Org", key);

					}
					
					if (i == 8) {
						assertEquals("Processing element " + i, "Hard Apple - Conv", key);
						assertEquals(key + " Usage Yield ",
								new BigDecimal("614.4000"), m.getUsageRawFruitActual());
						assertEquals(key + " Production FS at Std ",
								new BigDecimal("128119.1520"), m.getProductionFsAtStd());
						
						assertEquals(key + " Yield ",
								new BigDecimal("208.5273"), m.getYieldActual());
						
						assertEquals(key + " Material Variance Usage ",
								new BigDecimal("-12865.81"), m.getCostVarianceUsage());
						assertEquals(key + " Material Variance Substitution ",
								new BigDecimal("-4899.31"), m.getCostVarianceSubstitution());
						assertEquals(key + " Material Variance Total ",
								new BigDecimal("-17765.12"), m.getCostVarianceTotal());
						assertEquals(key + " Material Variance Supplies ",
								new BigDecimal("743.84"), m.getCostVarianceSupplies());
					}
					
					if (i == 9) {
						assertEquals("Processing element " + i, "Hard Apple - Org", key);
						assertEquals(key + " Usage Yield ",
								new BigDecimal("174.0430"), m.getUsageRawFruitActual());
						assertEquals(key + " Production FS at Std ",
								new BigDecimal("39019.6080"), m.getProductionFsAtStd());
						
						assertEquals(key + " Yield ",
								new BigDecimal("224.1953"), m.getYieldActual());
						
						assertEquals(key + " Material Variance Usage ",
								new BigDecimal("1191.07"), m.getCostVarianceUsage());
						assertEquals(key + " Material Variance Substitution ",
								new BigDecimal("-3141.86"), m.getCostVarianceSubstitution());
						assertEquals(key + " Material Variance Total ",
								new BigDecimal("-1950.79"), m.getCostVarianceTotal());
						assertEquals(key + " Material Variance Supplies ",
								new BigDecimal("-46.44"), m.getCostVarianceSupplies());
					}
					
					if (i == 10) {
						assertEquals("Processing element " + i, "Sauce - Conv", key);
						assertEquals(key + " Usage Yield ",
								new BigDecimal("620.5240"), m.getUsageRawFruitActual());
						assertEquals(key + " Production LB at Std ",
								new BigDecimal("1005571.0000"), m.getProductionLbAtStd());
						
						assertEquals(key + " Recovery ",
								new BigDecimal("0.8103"), m.getRecoveryActual());
						
						assertEquals(key + " Material Variance Usage ",
								new BigDecimal("10352.07"), m.getCostVarianceUsage());
						assertEquals(key + " Material Variance Substitution ",
								new BigDecimal("-18812.51"), m.getCostVarianceSubstitution());
						assertEquals(key + " Material Variance Total ",
								new BigDecimal("-8460.44"), m.getCostVarianceTotal());
						assertEquals(key + " Material Variance Supplies ",
								new BigDecimal("-659.99"), m.getCostVarianceSupplies());
					}
					
					if (i == 11) {
						assertEquals("Processing element " + i, "Sauce - Org", key);

					}
					
					if (i == 12) {
						assertEquals("Processing element " + i, "Labor", key);

						assertEquals(key + " Actual ", 
								new BigDecimal("24329.42"), m.getLaborActual());
						assertEquals(key + " Earned ", 
								new BigDecimal("26807.72"), m.getLaborEarned());
						assertEquals(key + " Variance ", 
								new BigDecimal("2478.30"), m.getLaborVariance());
						assertEquals(key + " Variance with Benefits ", 
								new BigDecimal("4064.41"), m.getLaborVarianceWithBenefits());
						
						assertEquals(key + " Hours Actual ", 
								new BigDecimal("1384.81"), m.getLaborHoursActual());
					}

					i++;
					*/
				}
				

			}
			
			@Test
			public void testPackaging() throws Exception {
				ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PKG);
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = getMos(io, ReportingType.PKG);
				
				System.out.println("Row\t" + GeneralUtility.getObjectFieldNames(new ManufacturingOrderDetail()));
				int i = 0;
				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail m = lhm.get(key);
					System.out.println(key + "\t" + GeneralUtility.getObjectFieldData(m));
					/*
					if (i == 0) {
						assertEquals("Packaging element " + i, "Line 2", key);
						assertEquals(key + " Hold for non-conformance",
								new BigDecimal("8292"), m.getHoldForNonConformance());
						assertEquals(key + " Production Forecast",
								new BigDecimal("106670"), m.getProductionForecast());
						assertEquals(key + " Production Planned",
								new BigDecimal("81090"), m.getProductionPlanned());
						assertEquals(key + " Production Actual",
								new BigDecimal("81338.0000"), m.getProduction());
						assertEquals(key + " Production % of Planned",
								new BigDecimal("1.00"), m.getProductionPercentagePlanned());
						
						assertEquals(key + " Labor Earned",
								new BigDecimal("17974.33"), m.getLaborEarned());
						assertEquals(key + " Labor Actual",
								new BigDecimal("18386.99"), m.getLaborActual());
						assertEquals(key + " Labor Variance",
								new BigDecimal("-412.66"), m.getLaborVariance());
						assertEquals(key + " Labor Variance with Benefits",
								new BigDecimal("-676.76"), m.getLaborVarianceWithBenefits());

						assertEquals(key + " Labor Hours Standard",
								new BigDecimal("955.31"), m.getLaborHoursStandard());
						assertEquals(key + " Labor Hours Actual",
								new BigDecimal("1093.23"), m.getLaborHoursActual());

					}
					
					
					if (i == 1) {
						assertEquals("Packaging element " + i, "Line 3", key);
						assertEquals(key + " Hold for non-conformance",
								new BigDecimal("0"), m.getHoldForNonConformance());
						assertEquals(key + " Production Forecast",
								new BigDecimal("43950"), m.getProductionForecast());
						assertEquals(key + " Production Planned",
								new BigDecimal("31100"), m.getProductionPlanned());
						assertEquals(key + " Production Actual",
								new BigDecimal("22960.0000"), m.getProduction());
						assertEquals(key + " Production % of Planned",
								new BigDecimal("0.74"), m.getProductionPercentagePlanned());
						
						assertEquals(key + " Labor Earned",
								new BigDecimal("4952.47"), m.getLaborEarned());
						assertEquals(key + " Labor Actual",
								new BigDecimal("4537.19"), m.getLaborActual());
						assertEquals(key + " Labor Variance",
								new BigDecimal("415.28"), m.getLaborVariance());
						assertEquals(key + " Labor Variance with Benefits",
								new BigDecimal("681.06"), m.getLaborVarianceWithBenefits());

						assertEquals(key + " Labor Hours Standard",
								new BigDecimal("295.14"), m.getLaborHoursStandard());
						assertEquals(key + " Labor Hours Actual",
								new BigDecimal("237.73"), m.getLaborHoursActual());
					}
					
					if (i == 2) {
						assertEquals("Packaging element " + i, "Line 4", key);
						assertEquals(key + " Hold for non-conformance",
								new BigDecimal("471"), m.getHoldForNonConformance());
						assertEquals(key + " Production Forecast",
								new BigDecimal("12325"), m.getProductionForecast());
						assertEquals(key + " Production Planned",
								new BigDecimal("30000"), m.getProductionPlanned());
						assertEquals(key + " Production Actual",
								new BigDecimal("14788.0000"), m.getProduction());
						assertEquals(key + " Production % of Planned",
								new BigDecimal("0.49"), m.getProductionPercentagePlanned());
						
						assertEquals(key + " Labor Earned",
								new BigDecimal("2760.92"), m.getLaborEarned());
						assertEquals(key + " Labor Actual",
								new BigDecimal("5712.87"), m.getLaborActual());
						assertEquals(key + " Labor Variance",
								new BigDecimal("-2951.95"), m.getLaborVariance());
						assertEquals(key + " Labor Variance with Benefits",
								new BigDecimal("-4841.20"), m.getLaborVarianceWithBenefits());

						assertEquals(key + " Labor Hours Standard",
								new BigDecimal("150.81"), m.getLaborHoursStandard());
						assertEquals(key + " Labor Hours Actual",
								new BigDecimal("305.53"), m.getLaborHoursActual());
					}
					
					if (i == 3) {
						assertEquals("Packaging element " + i, "Combi", key);

					}
					
					if (i == 4) {
						assertEquals("Packaging element " + i, "Line 7", key);

					}
					
					if (i == 5) {
						assertEquals("Packaging element " + i, "Line 8", key);
						assertEquals(key + " Hold for non-conformance",
								new BigDecimal("558"), m.getHoldForNonConformance());
						assertEquals(key + " Production Forecast",
								new BigDecimal("39175"), m.getProductionForecast());
						assertEquals(key + " Production Planned",
								new BigDecimal("45100"), m.getProductionPlanned());
						assertEquals(key + " Production Actual",
								new BigDecimal("44252.0000"), m.getProduction());
						assertEquals(key + " Production % of Planned",
								new BigDecimal("0.98"), m.getProductionPercentagePlanned());
						
						assertEquals(key + " Labor Earned",
								new BigDecimal("15364.29"), m.getLaborEarned());
						assertEquals(key + " Labor Actual",
								new BigDecimal("11128.76"), m.getLaborActual());
						assertEquals(key + " Labor Variance",
								new BigDecimal("4235.53"), m.getLaborVariance());
						assertEquals(key + " Labor Variance with Benefits",
								new BigDecimal("6946.27"), m.getLaborVarianceWithBenefits());

						assertEquals(key + " Labor Hours Standard",
								new BigDecimal("714.99"), m.getLaborHoursStandard());
						assertEquals(key + " Labor Hours Actual",
								new BigDecimal("611.79"), m.getLaborHoursActual());
					}
					
					if (i == 6) {
						assertEquals("Packaging element " + i, "Variety Pack", key);
						assertEquals(key + " Hold for non-conformance",
								new BigDecimal("560"), m.getHoldForNonConformance());
						assertEquals(key + " Production Forecast",
								new BigDecimal("11875"), m.getProductionForecast());
						assertEquals(key + " Production Planned",
								new BigDecimal("32200"), m.getProductionPlanned());
						assertEquals(key + " Production Actual",
								new BigDecimal("26142.0000"), m.getProduction());
						assertEquals(key + " Production % of Planned",
								new BigDecimal("0.81"), m.getProductionPercentagePlanned());
						
						assertEquals(key + " Labor Earned",
								new BigDecimal("9073.89"), m.getLaborEarned());
						assertEquals(key + " Labor Actual",
								new BigDecimal("5629.43"), m.getLaborActual());
						assertEquals(key + " Labor Variance",
								new BigDecimal("3444.46"), m.getLaborVariance());
						assertEquals(key + " Labor Variance with Benefits",
								new BigDecimal("5648.91"), m.getLaborVarianceWithBenefits());

						assertEquals(key + " Labor Hours Standard",
								new BigDecimal("530.80"), m.getLaborHoursStandard());
						assertEquals(key + " Labor Hours Actual",
								new BigDecimal("336.00"), m.getLaborHoursActual());
					}
					
					if (i == 7) {
						assertEquals("Packaging element " + i, "Rework", key);
						assertEquals(key + " Labor Earned",
								new BigDecimal("0"), m.getLaborEarned());
						assertEquals(key + " Labor Actual",
								new BigDecimal("2303.11"), m.getLaborActual());
						assertEquals(key + " Labor Variance",
								new BigDecimal("-2303.11"), m.getLaborVariance());
						assertEquals(key + " Labor Variance with Benefits",
								new BigDecimal("-3777.10"), m.getLaborVarianceWithBenefits());

						assertEquals(key + " Labor Hours Actual",
								new BigDecimal("137.72"), m.getLaborHoursActual());
					}
					
					if (i == 8) {
						assertEquals("Packaging element " + i, "Sanitation", key);
						assertEquals(key + " Labor Earned",
								new BigDecimal("3515.11"), m.getLaborEarned());
						assertEquals(key + " Labor Actual",
								new BigDecimal("3725.07"), m.getLaborActual());
						assertEquals(key + " Labor Variance",
								new BigDecimal("-209.96"), m.getLaborVariance());
						assertEquals(key + " Labor Variance with Benefits",
								new BigDecimal("-344.33"), m.getLaborVarianceWithBenefits());

						assertEquals(key + " Labor Hours Actual",
								new BigDecimal("214.50"), m.getLaborHoursActual());
					}
					
					if (i == 9) {
						assertEquals("Packaging element " + i, "Training", key);
						assertEquals(key + " Labor Earned",
								new BigDecimal("0"), m.getLaborEarned());
						assertEquals(key + " Labor Actual",
								new BigDecimal("881.17"), m.getLaborActual());
						assertEquals(key + " Labor Variance",
								new BigDecimal("-881.17"), m.getLaborVariance());
						assertEquals(key + " Labor Variance with Benefits",
								new BigDecimal("-1445.12"), m.getLaborVarianceWithBenefits());

						assertEquals(key + " Labor Hours Actual",
								new BigDecimal("60.50"), m.getLaborHoursActual());
					}
					
					if (i == 10) {
						assertEquals("Packaging element " + i, "Other", key);
						assertEquals(key + " Labor Earned",
								new BigDecimal("1645.33"), m.getLaborEarned());
						assertEquals(key + " Labor Actual",
								new BigDecimal("4041.16"), m.getLaborActual());
						assertEquals(key + " Labor Variance",
								new BigDecimal("-2395.83"), m.getLaborVariance());
						assertEquals(key + " Labor Variance with Benefits",
								new BigDecimal("-3929.16"), m.getLaborVarianceWithBenefits());

						assertEquals(key + " Labor Hours Actual",
								new BigDecimal("228.50"), m.getLaborHoursActual());
					}
					*/
					i++;
				}
			}

		}

		public static class Woodburn {

			private static InqOperations io = null;

			@Before
			public void setUp() throws Exception {
				io = new InqOperations();
				io.setEnvironment("PRD");
				io.setWarehouse("290");
				io.setFiscalYear("2013");
				io.setFiscalPeriodStart("6");
				io.setFiscalPeriodEnd("6");
				io.setFiscalWeekStart("25");
				io.setFiscalWeekEnd("25");
				io.buildDateRangeFromWeek();

				getOperationsWarehouse(io);
			}
			
			@Test
			public void testProcessing() throws Exception {
				
				ServiceOperationsReportingManufacturing.getManufacturingByPlantWhse(io, ReportingType.PROC);
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = getMos(io, ReportingType.PROC);
				
				System.out.println("Row\t" + GeneralUtility.getObjectFieldNames(new ManufacturingOrderDetail()));
				int i = 0;
				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail m = lhm.get(key);
					System.out.println(key + "\t" + GeneralUtility.getObjectFieldData(m));
					
					if (i == 0) {
						assertEquals("Processing element " + i, "Drum Dryer", key);
					}
					
					if (i == 1) {
						assertEquals("Processing element " + i, "In Line Finisher", key);
					}
					
					if (i == 2) {
						assertEquals("Processing element " + i, "Fresh Fruit", key);
					}
					
					if (i == 3) {
						assertEquals("Processing element " + i, "Concentrate", key);
					}
					
					if (i == 4) {
						assertEquals("Processing element " + i, "Single Strength", key);
					}
					
					if (i == 5) {
						assertEquals("Processing element " + i, "Formulated", key);
					}
					
					if (i == 6) {
						assertEquals("Processing element " + i, "Sample Cup", key);
					}
					
					if (i == 7) {
						assertEquals("Processing element " + i, "Pouch", key);
					}
					
					if (i == 8) {
						assertEquals("Processing element " + i, "Labor", key);
					}

					i++;
				}
				
				
			}

		}

	}
	
	/**
	 * Build a standard colon separated key string from "DCPK04" and "DCPK05"
	 * @param rs
	 * @param fields
	 * @return
	 */
	public static String buildKey(ResultSet rs) {
		return buildKey(rs, new String[] {"DCPK04","DCPK05"});
	}
	
	/**
	 * Build a standard colon separated key string from the passed in fields
	 * @param rs
	 * @return
	 */
	public static String buildKey(ResultSet rs, String[] fields) {
		String result = "";
		try {
			
			//get the set values for DCPK04 and DCPK05
			
			ArrayList<String> keys = new ArrayList<String>();
			
			for (String field : fields) {
				
				String key = "";
				key = rs.getString(field);
				key = (key == null) ? "Undefined" : key.trim();
				
				//some values may be references to other fields in the result set
				try {
					key = rs.getString(key).trim();
				} catch (Exception e) {
					//Counldn't find it, just use the set value
				}
				if (!key.equals("")) {
					keys.add(key);
				}
				
			}
			
			StringBuffer buildKey = new StringBuffer();
			int i=0;
			for (String key : keys) {
				if (i > 0) {
					buildKey.append(":");
				}
				buildKey.append(key);
				i++;
			}
			
			result = buildKey.toString().trim();

			
		} catch (Exception e) {
			//if anything fails, return "Undefined"
			result = "Undefined";
		}
		
		return result;
	}

}
