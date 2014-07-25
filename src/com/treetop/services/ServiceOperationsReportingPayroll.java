package com.treetop.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.treetop.businessobjects.ManufacturingFinance;
import com.treetop.businessobjects.ManufacturingOrderDetail;
import com.treetop.controller.operations.InqOperations;
import com.treetop.services.ServiceOperationsReporting.ReportingType;
import com.treetop.utilities.GeneralUtility;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ServiceOperationsReportingPayroll.UnitTests.class })
public class ServiceOperationsReportingPayroll {



	private enum ValueType {
		WTD, MTD, YTD
	}

	public static void getProcessingLabor(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm) throws Exception {

		StringBuffer throwError = new StringBuffer();

		// validate incoming data
		if (inqOperations == null) {
			throwError.append("Inquiry bean cannot be null.  ");
		} else {
			if (inqOperations.getEnvironment().equals("")) {
				throwError.append("Environment cannot be blank.  ");
			}
			if (inqOperations.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be blank.  ");
			}
			if (inqOperations.getFiscalYear().equals("")) {
				throwError.append("Fiscal Year cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodStart().equals("")) {
				throwError.append("FiscalPeriodStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodEnd().equals("")) {
				throwError.append("FiscalPeriodEnd cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekStart().equals("")) {
				throwError.append("FiscalWeekStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekEnd().equals("")) {
				throwError.append("FiscalWeekEnd cannot be empty.  ");
			}
		}

		if (throwError.toString().trim().equals("")) {
			// passed initial validation

			Connection conn = null;
			try {
				conn = ServiceConnection.getConnectionStack11();

				getManufacturingHoursAndDollars(inqOperations, lhm, ReportingType.PROC, ValueType.WTD, conn);

			} catch (Exception e) {
				throwError.append(e);
			}

			finally {
				if (conn != null) {
					try {
						ServiceConnection.returnConnectionStack11(conn);
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("getLaborHoursAndDollars(InqOperations, ReportingType). ");
			throw new Exception(throwError.toString());
		}
	}
	
	public static void getBlendingLabor(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm) throws Exception {

		StringBuffer throwError = new StringBuffer();

		// validate incoming data
		if (inqOperations == null) {
			throwError.append("Inquiry bean cannot be null.  ");
		} else {
			if (inqOperations.getEnvironment().equals("")) {
				throwError.append("Environment cannot be blank.  ");
			}
			if (inqOperations.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be blank.  ");
			}
			if (inqOperations.getFiscalYear().equals("")) {
				throwError.append("Fiscal Year cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodStart().equals("")) {
				throwError.append("FiscalPeriodStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodEnd().equals("")) {
				throwError.append("FiscalPeriodEnd cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekStart().equals("")) {
				throwError.append("FiscalWeekStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekEnd().equals("")) {
				throwError.append("FiscalWeekEnd cannot be empty.  ");
			}
		}

		if (throwError.toString().trim().equals("")) {
			// passed initial validation

			Connection conn = null;
			try {
				conn = ServiceConnection.getConnectionStack11();

				getManufacturingHoursAndDollars(inqOperations, lhm, ReportingType.BLND, ValueType.WTD, conn);

			} catch (Exception e) {
				throwError.append(e);
			}

			finally {
				if (conn != null) {
					try {
						ServiceConnection.returnConnectionStack11(conn);
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("getLaborHoursAndDollars(InqOperations, ReportingType). ");
			throw new Exception(throwError.toString());
		}
	}
	
	public static void getFrozenCherryLabor(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm) throws Exception {

		StringBuffer throwError = new StringBuffer();

		// validate incoming data
		if (inqOperations == null) {
			throwError.append("Inquiry bean cannot be null.  ");
		} else {
			if (inqOperations.getEnvironment().equals("")) {
				throwError.append("Environment cannot be blank.  ");
			}
			if (inqOperations.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be blank.  ");
			}
			if (inqOperations.getFiscalYear().equals("")) {
				throwError.append("Fiscal Year cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodStart().equals("")) {
				throwError.append("FiscalPeriodStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodEnd().equals("")) {
				throwError.append("FiscalPeriodEnd cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekStart().equals("")) {
				throwError.append("FiscalWeekStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekEnd().equals("")) {
				throwError.append("FiscalWeekEnd cannot be empty.  ");
			}
		}

		if (throwError.toString().trim().equals("")) {
			// passed initial validation

			Connection conn = null;
			try {
				conn = ServiceConnection.getConnectionStack11();

				getManufacturingHoursAndDollars(inqOperations, lhm, ReportingType.FZCHRY, ValueType.WTD, conn);

			} catch (Exception e) {
				throwError.append(e);
			}

			finally {
				if (conn != null) {
					try {
						ServiceConnection.returnConnectionStack11(conn);
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("getLaborHoursAndDollars(InqOperations, ReportingType). ");
			throw new Exception(throwError.toString());
		}
	}

	public static void getPackagingLabor(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm) throws Exception {

		StringBuffer throwError = new StringBuffer();

		// validate incoming data
		if (inqOperations == null) {
			throwError.append("Inquiry bean cannot be null.  ");
		} else {
			if (inqOperations.getEnvironment().equals("")) {
				throwError.append("Environment cannot be blank.  ");
			}
			if (inqOperations.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be blank.  ");
			}
			if (inqOperations.getFiscalYear().equals("")) {
				throwError.append("Fiscal Year cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodStart().equals("")) {
				throwError.append("FiscalPeriodStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodEnd().equals("")) {
				throwError.append("FiscalPeriodEnd cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekStart().equals("")) {
				throwError.append("FiscalWeekStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekEnd().equals("")) {
				throwError.append("FiscalWeekEnd cannot be empty.  ");
			}
		}

		if (throwError.toString().trim().equals("")) {
			// passed initial validation

			Connection conn = null;
			try {
				conn = ServiceConnection.getConnectionStack11();

				getManufacturingHoursAndDollars(inqOperations, lhm, ReportingType.PKG, ValueType.WTD, conn);

			} catch (Exception e) {
				throwError.append(e);
			}

			finally {
				if (conn != null) {
					try {
						ServiceConnection.returnConnectionStack11(conn);
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("getLaborHoursAndDollars(InqOperations, ReportingType). ");
			throw new Exception(throwError.toString());
		}
	}
	
	public static void getMaintenanceLabor(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingFinance> lhm) throws Exception {

		StringBuffer throwError = new StringBuffer();

		// validate incoming data
		if (inqOperations == null) {
			throwError.append("Inquiry bean cannot be null.  ");
		} else {
			if (inqOperations.getEnvironment().equals("")) {
				throwError.append("Environment cannot be blank.  ");
			}
			if (inqOperations.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be blank.  ");
			}
			if (inqOperations.getFiscalYear().equals("")) {
				throwError.append("Fiscal Year cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodStart().equals("")) {
				throwError.append("FiscalPeriodStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodEnd().equals("")) {
				throwError.append("FiscalPeriodEnd cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekStart().equals("")) {
				throwError.append("FiscalWeekStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekEnd().equals("")) {
				throwError.append("FiscalWeekEnd cannot be empty.  ");
			}
		}

		if (throwError.toString().trim().equals("")) {
			// passed initial validation

			Connection conn = null;
			try {
				conn = ServiceConnection.getConnectionStack11();

				getFinancialDollars(inqOperations, lhm, ReportingType.MAINT, ValueType.YTD, conn);

			} catch (Exception e) {
				throwError.append(e);
			}

			finally {
				if (conn != null) {
					try {
						ServiceConnection.returnConnectionStack11(conn);
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("getLaborHoursAndDollars(InqOperations, ReportingType). ");
			throw new Exception(throwError.toString());
		}
	}
	
	public static void getQualityLabor(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingFinance> lhm) throws Exception {

		StringBuffer throwError = new StringBuffer();

		// validate incoming data
		if (inqOperations == null) {
			throwError.append("Inquiry bean cannot be null.  ");
		} else {
			if (inqOperations.getEnvironment().equals("")) {
				throwError.append("Environment cannot be blank.  ");
			}
			if (inqOperations.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be blank.  ");
			}
			if (inqOperations.getFiscalYear().equals("")) {
				throwError.append("Fiscal Year cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodStart().equals("")) {
				throwError.append("FiscalPeriodStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodEnd().equals("")) {
				throwError.append("FiscalPeriodEnd cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekStart().equals("")) {
				throwError.append("FiscalWeekStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekEnd().equals("")) {
				throwError.append("FiscalWeekEnd cannot be empty.  ");
			}
		}

		if (throwError.toString().trim().equals("")) {
			// passed initial validation

			Connection conn = null;
			try {
				conn = ServiceConnection.getConnectionStack11();

				getFinancialDollars(inqOperations, lhm, ReportingType.QA, ValueType.WTD, conn);

			} catch (Exception e) {
				throwError.append(e);
			}

			finally {
				if (conn != null) {
					try {
						ServiceConnection.returnConnectionStack11(conn);
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("getLaborHoursAndDollars(InqOperations, ReportingType). ");
			throw new Exception(throwError.toString());
		}
	}
	
	public static void getWarehousingLabor(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingFinance> lhm) throws Exception {

		StringBuffer throwError = new StringBuffer();

		// validate incoming data
		if (inqOperations == null) {
			throwError.append("Inquiry bean cannot be null.  ");
		} else {
			if (inqOperations.getEnvironment().equals("")) {
				throwError.append("Environment cannot be blank.  ");
			}
			if (inqOperations.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be blank.  ");
			}
			if (inqOperations.getFiscalYear().equals("")) {
				throwError.append("Fiscal Year cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodStart().equals("")) {
				throwError.append("FiscalPeriodStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalPeriodEnd().equals("")) {
				throwError.append("FiscalPeriodEnd cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekStart().equals("")) {
				throwError.append("FiscalWeekStart cannot be empty.  ");
			}
			if (inqOperations.getFiscalWeekEnd().equals("")) {
				throwError.append("FiscalWeekEnd cannot be empty.  ");
			}
		}

		if (throwError.toString().trim().equals("")) {
			// passed initial validation

			Connection conn = null;
			try {
				conn = ServiceConnection.getConnectionStack11();

				getFinancialDollars(inqOperations, lhm, ReportingType.WHSE, ValueType.WTD, conn);

			} catch (Exception e) {
				throwError.append(e);
			}

			finally {
				if (conn != null) {
					try {
						ServiceConnection.returnConnectionStack11(conn);
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("getLaborHoursAndDollars(InqOperations, ReportingType). ");
			throw new Exception(throwError.toString());
		}
	}

	private static void getManufacturingHoursAndDollars(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm, ReportingType reportingType, ValueType valueType,
			Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			String loadValues = "WTD";
			String requestType = inqOperations.getRequestType();
			if (requestType.equals("monthly")) {
				loadValues = "MTD";
			}
			
			String sql = BuildSQL.getPayroll(inqOperations, reportingType, valueType);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				
				LoadFields.manufacturingPayroll(rs, lhm, loadValues);
				

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
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("getLaborHoursAndDollars(InqOperations, reportingType, Connection). ");
			throw new Exception(throwError.toString());
		}
		
	}
	
	private static void getFinancialDollars(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingFinance> lhm, ReportingType reportingType, ValueType valueType,
			Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			String sql = BuildSQL.getPayroll(inqOperations, reportingType, valueType);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {


				LoadFields.financialPayroll(rs, lhm);
				

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
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("getLaborHoursAndDollars(InqOperations, reportingType, Connection). ");
			throw new Exception(throwError.toString());
		}
		
	}

	private static class BuildSQL {
		private static String getPayroll(InqOperations inqOperations, ReportingType pg, ValueType vt) throws Exception {
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();

			try {
				
				String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";
				String tkLibrary = GeneralUtility.getPayrollLibraryTK(inqOperations.getEnvironment()) + ".";
				String custLibrary = GeneralUtility.getPayrollLibraryCustom(inqOperations.getEnvironment()) + ".";

				String fiscalYear = inqOperations.getFiscalYear();

				String startDate = inqOperations.getStartDate();
				String endDate = inqOperations.getEndDate();
				if (vt == ValueType.YTD) {
					startDate = inqOperations.getFiscalYearStartDate();
				}

				String reportingType = pg.toString();
				String warehouse = inqOperations.getWarehouse();

				sqlString.append(" SELECT ");
				sqlString.append(" DCPK04, ");
				sqlString.append(" SUM(TEMP_DOL) AS YTD_TEMP_DOL, \r");
				sqlString.append(" SUM(CASE WHEN MONTH=" + inqOperations.getFiscalPeriodEnd()
						+ " THEN TEMP_DOL ELSE 0 END) AS MTD_TEMP_DOL, \r");
				sqlString.append(" SUM(CASE WHEN WEEK=" + inqOperations.getFiscalWeekEnd()
						+ " THEN TEMP_DOL ELSE 0 END) AS WTD_TEMP_DOL, \r");
				sqlString.append(" SUM(RFT_DOL) AS YTD_RFT_DOL, \r");
				sqlString.append(" SUM(CASE WHEN MONTH=" + inqOperations.getFiscalPeriodEnd()
						+ " THEN RFT_DOL ELSE 0 END) AS MTD_RFT_DOL, \r");
				sqlString.append(" SUM(CASE WHEN WEEK=" + inqOperations.getFiscalWeekEnd()
						+ " THEN RFT_DOL ELSE 0 END) AS WTD_RFT_DOL, \r");

				sqlString.append(" SUM(TEMP_HRS) AS YTD_TEMP_HRS, \r");
				sqlString.append(" SUM(CASE WHEN MONTH=" + inqOperations.getFiscalPeriodEnd()
						+ " THEN TEMP_HRS ELSE 0 END) AS MTD_TEMP_HRS, \r");
				sqlString.append(" SUM(CASE WHEN WEEK=" + inqOperations.getFiscalWeekEnd()
						+ " THEN TEMP_HRS ELSE 0 END) AS WTD_TEMP_HRS, \r");
				sqlString.append(" SUM(RFT_HRS) AS YTD_RFT_HRS, \r");
				sqlString.append(" SUM(CASE WHEN MONTH=" + inqOperations.getFiscalPeriodEnd()
						+ " THEN RFT_HRS ELSE 0 END) AS MTD_RFT_HRS, \r");
				sqlString.append(" SUM(CASE WHEN WEEK=" + inqOperations.getFiscalWeekEnd()
						+ " THEN RFT_HRS ELSE 0 END) AS WTD_RFT_HRS \r");

				sqlString.append(" \r ");

				sqlString.append(" FROM ( ");

				sqlString.append(" \r ");

				sqlString.append(" SELECT ");

				sqlString.append(" C.CPYEA4 AS YEAR, C.CPPERI AS MONTH, D.CPPERI AS WEEK, ");
				sqlString.append(" CASE WHEN DCPK05<>'' THEN DCPK05 ELSE DCPK04 END AS DCPK04, ");
				sqlString.append(" \r ");
				sqlString
						.append(" SUM(CASE WHEN TPGRUP>='T1' AND TPGRUP<='T9' THEN ILDOLL ELSE 0 END) as TEMP_DOL, \r");
				sqlString.append(" SUM(CASE WHEN TPGRUP<'T1' OR TPGRUP>'T9' THEN ILDOLL ELSE 0 END) AS RFT_DOL, \r");
				sqlString
						.append(" SUM(CASE WHEN TPGRUP>='T1' AND TPGRUP<='T9' THEN TPRETM ELSE 0 END) as TEMP_HRS, \r");
				sqlString.append(" SUM(CASE WHEN TPGRUP<'T1' OR TPGRUP>'T9' THEN TPRETM ELSE 0 END) AS RFT_HRS \r");
				sqlString.append(" \r ");
				sqlString.append(" FROM ");
				sqlString.append(" " + ttLibrary + "DCPALL ");
				sqlString.append(" \r ");
				sqlString.append(" INNER JOIN " + custLibrary + "WFPLBRDTL ON ");
				sqlString.append("      TPCONO = 01 ");
				sqlString.append("      AND TPRDTI >= " + startDate + " ");
				sqlString.append("      AND TPRDTI <= " + endDate + " ");
				// Must be in the "Variables" account range
				sqlString.append("      AND ILDIM1>='64000' AND ILDIM1<='64999' ");
				sqlString.append("      AND (ILDIM1=DCPA01 OR DCPA01='') "); // Account
				sqlString.append("      AND (ILDIM2=DCPA02 OR DCPA02='') "); // Facility
				sqlString.append("      AND (ILDIM3=DCPA03 OR DCPA03='') "); // CostCenter
				sqlString.append("      AND (TPSEC5=DCPA05 OR DCPA05='') "); // JobCode
				sqlString.append(" \r ");
				sqlString.append(" INNER  JOIN " + tkLibrary + "CKDESCMS ON ");
				sqlString.append("      DSCODE = 'C' ");
				sqlString.append("      AND TPSEC5 = DSKEYM ");
				sqlString.append("      AND (DSEX03=DCPA04 OR DCPA04='') "); // ManufacturingLine
				sqlString.append(" \r ");
				// only get records for worked time (excludes PTO, HOL, etc.)
				sqlString.append(" INNER JOIN " + tkLibrary + "CKWORKCD ON ");
				sqlString.append("       TPCODE = WCCODE and WCNONW = 'N' ");
				sqlString.append(" \r ");
				sqlString
						.append(" LEFT OUTER JOIN " + library + "CSYPER C ON C.CPCONO = 100 AND C.CPDIVI = '100' AND C.CPPETP = 1 AND TPRDTI>=C.CPFDAT AND TPRDTI<=C.CPTDAT ");
				sqlString.append(" \r ");
				sqlString
						.append(" LEFT OUTER JOIN " + library + "CSYPER D ON D.CPCONO = 100 AND D.CPDIVI = '100' AND D.CPPETP = 2 AND TPRDTI>=D.CPFDAT AND TPRDTI<=D.CPTDAT ");
				sqlString.append(" \r ");
				sqlString.append(" WHERE ");
				sqlString.append(" DCPK00='OPSRPT' ");
				sqlString.append(" \r ");
				sqlString.append(" AND DCPK01='" + reportingType + "' ");
				sqlString.append(" AND DCPK02='" + warehouse + "' ");
				sqlString.append(" AND DCPK03='SPEND' ");
				sqlString.append(" AND DCPK04='Labor' ");

				sqlString.append(" \r ");

				sqlString
						.append(" GROUP BY C.CPYEA4, C.CPPERI, D.CPPERI, DCPK04, DCPK05, ILDIM1, ILDIM2, ILDIM3, DSEX03, TPSEC5, TPGRUP ");
				sqlString.append(" ) AS A ");
				sqlString.append(" GROUP BY DCPK04 ");

			} catch (Exception e) {
				throwError.append(" Error building getPayrollLabor statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingPayroll.");
				throwError.append("BuildSQL.getPayrollLabor(InqOperations, reportingType, ValueType)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();

		}
	}

	private static class LoadFields {

		
		private static void manufacturingPayroll(ResultSet rs,
				LinkedHashMap<String, ManufacturingOrderDetail> lhm,
				String loadValues) throws Exception {

			StringBuffer throwError = new StringBuffer();

			try {

				String group = rs.getString("DCPK04").trim();
				
				// The group may or may not be already in the LinkedHashMap
				// If it is not, add it
				ManufacturingOrderDetail mo = lhm.get(group);
				if (mo == null) {
					mo = new ManufacturingOrderDetail();
					lhm.put(group, mo);
				}

				BigDecimal rftDollars = rs.getBigDecimal(loadValues + "_RFT_DOL").setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal tempDollars = rs.getBigDecimal(loadValues + "_TEMP_DOL").setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal rftHours = rs.getBigDecimal(loadValues + "_RFT_HRS").setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal tempHours = rs.getBigDecimal(loadValues + "_TEMP_HRS").setScale(2, BigDecimal.ROUND_HALF_UP);

				// Combine RFT and Temp labor dollars and hours
				mo.setLaborActual(rftDollars.add(tempDollars));
				mo.setLaborHoursActual(rftHours.add(tempHours));

			} catch (Exception e) {
				throwError.append(e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingPayroll.");
				throwError.append("LoadFields.payroll(ResultSet). ");
				throw new Exception(throwError.toString());
			}

		}
		
		private static void financialPayroll(ResultSet rs,
				LinkedHashMap<String, ManufacturingFinance> lhm) throws Exception {

			StringBuffer throwError = new StringBuffer();

			try {

				String group = rs.getString("DCPK04").trim();
				
				// The group may or may not be already in the LinkedHashMap
				// If it is not, add it
				ManufacturingFinance m = lhm.get(group);
				if (m == null) {
					m = new ManufacturingFinance();
					lhm.put(group, m);
				}

				BigDecimal weekToDateRftDollars = rs.getBigDecimal("WTD_RFT_DOL");
				BigDecimal weekToDateTempDollars = rs.getBigDecimal("WTD_TEMP_DOL");

				BigDecimal monthToDateRftDollars = rs.getBigDecimal("MTD_RFT_DOL");
				BigDecimal monthToDateTempDollars = rs.getBigDecimal("MTD_TEMP_DOL");
				
				BigDecimal yearToDateRftDollars = rs.getBigDecimal("YTD_RFT_DOL");
				BigDecimal yearToDateTempDollars = rs.getBigDecimal("YTD_TEMP_DOL");
				
				// Combine RFT and Temp labor dollars
				m.setWtdActual(weekToDateRftDollars.add(weekToDateTempDollars));
				m.setMtdActual(monthToDateRftDollars.add(monthToDateTempDollars));
				m.setYtdActual(yearToDateRftDollars.add(yearToDateTempDollars));

			} catch (Exception e) {
				throwError.append(e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingPayroll.");
				throwError.append("LoadFields.payroll(ResultSet). ");
				throw new Exception(throwError.toString());
			}

		}

	}

	@RunWith(Suite.class)
	@Suite.SuiteClasses({ ServiceOperationsReportingPayroll.UnitTests.Medford.class,
			ServiceOperationsReportingPayroll.UnitTests.Prosser.class,
			ServiceOperationsReportingPayroll.UnitTests.Selah.class,
			ServiceOperationsReportingPayroll.UnitTests.Woodburn.class })
	public static class UnitTests {

		public static class Medford {
		}

		public static class Prosser {
		
			private static InqOperations inqOperations = null;

			@Before
			public void setUp() {
				inqOperations = new InqOperations();
				inqOperations.setEnvironment("PRD");
				inqOperations.setWarehouse("469");
				inqOperations.setFiscalYear("2013");
				inqOperations.setFiscalPeriodStart("1");
				inqOperations.setFiscalPeriodEnd("3");
				inqOperations.setFiscalWeekStart("1");
				inqOperations.setFiscalWeekEnd("13");
			}
		
		
			@Test
			public void testProcessingLabor() throws Exception {
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = ServiceOperationsReporting.getMos(inqOperations,
						ReportingType.PROC);
				getProcessingLabor(inqOperations, lhm);

				ManufacturingOrderDetail mo = lhm.get("Labor");

				assertEquals("Actual Labor dollars.", new BigDecimal("35369.23"), mo.getLaborActual());
				assertEquals("Actual Labor hours.", new BigDecimal("1818.07"), mo.getLaborHoursActual());

			}
			
			@Test
			public void testBlendingLabor() throws Exception {
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = ServiceOperationsReporting.getMos(inqOperations,
						ReportingType.BLND);
				getBlendingLabor(inqOperations, lhm);

				ManufacturingOrderDetail mo = lhm.get("Labor");

				assertEquals("Actual Labor dollars.", new BigDecimal("8498.58"), mo.getLaborActual());
				assertEquals("Actual Labor hours.", new BigDecimal("500.67"), mo.getLaborHoursActual());

			}
					
			@Test
			public void testFrozenCherryLabor() throws Exception {
			
				// override default inquiry object for frozen cherry
				// data only available for week 3
				inqOperations = new InqOperations();
				inqOperations.setEnvironment("PRD");
				inqOperations.setWarehouse("469");
				inqOperations.setFiscalYear("2013");
				inqOperations.setFiscalPeriodStart("1");
				inqOperations.setFiscalPeriodEnd("1");
				inqOperations.setFiscalWeekStart("3");
				inqOperations.setFiscalWeekEnd("3");
			
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = ServiceOperationsReporting.getMos(inqOperations,
						ReportingType.FZCHRY);
				getFrozenCherryLabor(inqOperations, lhm);

				ManufacturingOrderDetail mo = lhm.get("Labor");

				assertEquals("Actual Labor dollars.", new BigDecimal("7054.12"), mo.getLaborActual());
				assertEquals("Actual Labor hours.", new BigDecimal("383.30"), mo.getLaborHoursActual());

			}
			
			@Test
			public void testPackagingLabor() throws Exception {
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = ServiceOperationsReporting.getMos(inqOperations,
						ReportingType.PKG);
				getPackagingLabor(inqOperations, lhm);

				ManufacturingOrderDetail mo = lhm.get("Labor");

				assertEquals("Actual Labor dollars.", new BigDecimal("6642.36"), mo.getLaborActual());
				assertEquals("Actual Labor hours.", new BigDecimal("383.20"), mo.getLaborHoursActual());

			}
		
		}

		public static class Selah {

			private static InqOperations inqOperations = null;

			@Before
			public void setUp() {
				inqOperations = new InqOperations();
				inqOperations.setEnvironment("PRD");
				inqOperations.setWarehouse("209");
				inqOperations.setFiscalYear("2013");
				inqOperations.setFiscalPeriodStart("1");
				inqOperations.setFiscalPeriodEnd("3");
				inqOperations.setFiscalWeekStart("1");
				inqOperations.setFiscalWeekEnd("13");
			}

			@Test
			public void testProcessingLabor() throws Exception {
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = ServiceOperationsReporting.getMos(inqOperations,
						ReportingType.PROC);
				getProcessingLabor(inqOperations, lhm);

				ManufacturingOrderDetail mo = lhm.get("Labor");

				assertEquals("Actual Labor dollars.", new BigDecimal("40751.04"), mo.getLaborActual());
				assertEquals("Actual Labor hours.", new BigDecimal("2122.23"), mo.getLaborHoursActual());

			}
			
			@Test
			public void testBlendingLabor() throws Exception {
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = ServiceOperationsReporting.getMos(inqOperations,
						ReportingType.BLND);
				getBlendingLabor(inqOperations, lhm);

				ManufacturingOrderDetail mo = lhm.get("Labor");

				assertEquals("Actual Labor dollars.", new BigDecimal("6549.71"), mo.getLaborActual());
				assertEquals("Actual Labor hours.", new BigDecimal("329.00"), mo.getLaborHoursActual());

			}
			
			@Test
			public void testPackagingLabor() throws Exception {
				LinkedHashMap<String, ManufacturingOrderDetail> lhm = ServiceOperationsReporting.getMos(inqOperations,
						ReportingType.PKG);
				getPackagingLabor(inqOperations, lhm);

				for (String key : lhm.keySet()) {
					ManufacturingOrderDetail mo = lhm.get(key);
					
					if (key.equals("Line 2")) {
						assertEquals(key + " Actual Labor dollars.", new BigDecimal("28435.29"), mo.getLaborActual());
						assertEquals(key + "Actual Labor hours.", new BigDecimal("1515.26"), mo.getLaborHoursActual());
					}
					
					if (key.equals("Line 3")) {
						assertEquals(key + " Actual Labor dollars.", new BigDecimal("21552.81"), mo.getLaborActual());
						assertEquals(key + "Actual Labor hours.", new BigDecimal("1101.46"), mo.getLaborHoursActual());
					}
					
					if (key.equals("Line 8")) {
						assertEquals(key + " Actual Labor dollars.", new BigDecimal("18446.91"), mo.getLaborActual());
						assertEquals(key + "Actual Labor hours.", new BigDecimal("991.25"), mo.getLaborHoursActual());
					}
					
					if (key.equals("Variety Pack")) {
						assertEquals(key + " Actual Labor dollars.", new BigDecimal("2568.53"), mo.getLaborActual());
						assertEquals(key + "Actual Labor hours.", new BigDecimal("144.57"), mo.getLaborHoursActual());
					}
					
					if (key.equals("Sanitation")) {
						assertEquals(key + " Actual Labor dollars.", new BigDecimal("3272.91"), mo.getLaborActual());
						assertEquals(key + "Actual Labor hours.", new BigDecimal("187.00"), mo.getLaborHoursActual());
					}
					
					if (key.equals("Rework")) {
						assertEquals(key + " Actual Labor dollars.", new BigDecimal("1363.44"), mo.getLaborActual());
						assertEquals(key + "Actual Labor hours.", new BigDecimal("80.00"), mo.getLaborHoursActual());
					}
					
					if (key.equals("Training")) {
						assertEquals(key + " Actual Labor dollars.", new BigDecimal("561.30"), mo.getLaborActual());
						assertEquals(key + "Actual Labor hours.", new BigDecimal("31.25"), mo.getLaborHoursActual());
					}
					
					if (key.equals("Other")) {
						assertEquals(key + " Actual Labor dollars.", new BigDecimal("4081.78"), mo.getLaborActual());
						assertEquals(key + "Actual Labor hours.", new BigDecimal("228.50"), mo.getLaborHoursActual());
					}
					
				}

			}
			
			@Test
			public void testMaintenanceLabor() throws Exception {
				LinkedHashMap<String, ManufacturingFinance> lhm = new LinkedHashMap<String, ManufacturingFinance>(); 
				getMaintenanceLabor(inqOperations, lhm);

				ManufacturingFinance m = lhm.get("Labor");

				assertEquals("Week to Date Dollars.", new BigDecimal("32516.98"), m.getWtdActual());
				assertEquals("Month to Date Dollars.", new BigDecimal("155095.59"), m.getMtdActual());
				assertEquals("Year to Date Dollars.", new BigDecimal("373005.98"), m.getYtdActual());

			}
			
			@Test
			public void testQualityLabor() throws Exception {
				LinkedHashMap<String, ManufacturingFinance> lhm = new LinkedHashMap<String, ManufacturingFinance>(); 
				getQualityLabor(inqOperations, lhm);

				ManufacturingFinance m = lhm.get("Labor");

				assertEquals("Week to Date Dollars.", new BigDecimal("10665.23"), m.getWtdActual());
				

			}
			
			@Test
			public void testWarehousingLabor() throws Exception {
				LinkedHashMap<String, ManufacturingFinance> lhm = new LinkedHashMap<String, ManufacturingFinance>(); 
				getWarehousingLabor(inqOperations, lhm);

				ManufacturingFinance m = lhm.get("Labor");

				assertEquals("Week to Date Dollars.", new BigDecimal("16243.20"), m.getWtdActual());
				

			}

		}

		public static class Woodburn {
		}

	}

}

