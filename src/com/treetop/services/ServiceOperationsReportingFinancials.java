package com.treetop.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.treetop.businessobjectapplications.BeanOperationsReporting;
import com.treetop.businessobjects.AccountData;
import com.treetop.businessobjects.ManufacturingFinance;
import com.treetop.controller.operations.InqOperations;
import com.treetop.services.ServiceOperationsReporting.ReportingType;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.UtilityDateTime;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ServiceOperationsReportingFinancials.UnitTests.class })
public class ServiceOperationsReportingFinancials {

	private static Hashtable<String, BigDecimal> benefitsRates = null;
	private static Hashtable<String, BigDecimal> utilitiesRates = null;
	
	static {
		
		utilitiesRates = new Hashtable<String, BigDecimal>();
		
		int fiscalYear = Integer.valueOf(UtilityDateTime.getSystemDate().getM3FiscalYear());
		fiscalYear--;
		//load utilities rates for last and current fiscal year
		for (int i = 0; i<2; i++) {
			loadUtilitesRates(fiscalYear++);
		}
		
	}


	private static class BuildSQL {
		
		private static String getUtilitiesRates(int fiscalYear) throws Exception {
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			
			try {
			
				sqlString.append(" SELECT DCPK01, DCPT40, BETFA4, BEDIPE ");
				sqlString.append(" FROM DBPRD.DCPALL ");
				sqlString.append(" INNER JOIN M3DJDPRD.FGDITD ON BECONO=100 ");
				sqlString.append(" AND BETTAB='" + fiscalYear + "UTIL' ");
				sqlString.append(" AND BETFA1=DCPA01 AND BETFA2=DCPA02 AND BETFA3=DCPA03 ");
				sqlString.append(" WHERE DCPK00='UTIL' ");
				
			} catch (Exception e) {
				throwError.append(" Error building sql getSpending statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReporting.");
				throwError.append("BuildSQL.getUtilitiesRates(int fiscalYear)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
			
		}
		
		private static String getPlannedSpending(InqOperations inqOperations, ReportingType reportingType) throws Exception {
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			
			String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
			String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";

			String warehouse = inqOperations.getWarehouse();
			String fiscalYear = inqOperations.getFiscalYear();
			String periodEnd = inqOperations.getFiscalPeriodEnd();
			String weekEnd = inqOperations.getFiscalWeekEnd();
			
			
			try {
				
				sqlString.append(" SELECT \r");
				sqlString.append(" DCPK04, \r");
				sqlString.append(" SUM(BCBLAM) AS YTD_PLAN, \r");
				sqlString.append(" SUM(CASE WHEN MONTH=" + periodEnd + " THEN BCBLAM ELSE 0 END) AS MTD_PLAN, \r");
				sqlString.append(" SUM(CASE WHEN WEEK=" + weekEnd + " THEN BCBLAM ELSE 0 END) AS WTD_PLAN \r");
				sqlString.append(" \r");
				sqlString.append(" \r");
				sqlString.append(" FROM ( \r");
				sqlString.append(" SELECT \r");
				sqlString.append(" \r");
				sqlString.append(" DCPK04, \r");
				sqlString.append(" M.CPYEA4 AS YEAR, \r");
				sqlString.append(" M.CPPERI AS MONTH, \r");
				sqlString.append(" W.CPPERI AS WEEK, \r");
				sqlString.append(" \r");
				sqlString.append(" SUM(CASE WHEN MOD(M.CPPERI,3)=0 THEN BCBLAM/5 ELSE BCBLAM/4 END) AS BCBLAM \r");
				sqlString.append(" \r");
				sqlString.append(" FROM \r");
				sqlString.append(" " + ttLibrary + "DCPALL \r");
				sqlString.append(" LEFT OUTER JOIN " + library + "FBUDET ON \r");
				sqlString.append("      BCCONO=100 AND BCDIVI='100' AND BCBVER='VRD' \r");
				sqlString.append(" \r");
				sqlString.append(" \r");
				sqlString.append(" INNER JOIN " + library + "CSYPER AS M ON M.CPCONO=100 AND M.CPDIVI='100' \r");
				sqlString.append(" AND M.CPPETP=1 AND M.CPYEA4=LEFT(BCBUPE,4) AND M.CPPERI=SUBSTR(BCBUPE,5,2) \r");
				sqlString.append(" AND M.CPYEA4=" + fiscalYear + "  \r");
				sqlString.append(" \r");
				sqlString.append(" INNER JOIN " + library + "CSYPER AS W ON W.CPCONO=100 AND W.CPDIVI='100' \r");
				sqlString.append(" AND W.CPPETP=2 AND M.CPYEA4=W.CPYEA4 \r");
				sqlString.append(" AND W.CPFDAT>=M.CPFDAT AND W.CPTDAT<=M.CPTDAT \r");
				sqlString.append(" AND W.CPPERI<=" + weekEnd + " \r");
				sqlString.append(" \r");
				sqlString.append(" \r");
				sqlString.append(" WHERE \r");
				sqlString.append(" DCPK00='OPSRPT' \r");
				sqlString.append(" AND DCPK01='" + reportingType + "' \r");
				sqlString.append(" AND DCPK02='" + warehouse + "' \r");
				sqlString.append(" AND DCPK03='PLAN' \r");
				sqlString.append(" \r");
				sqlString.append(" AND BCAIT1<>'99998' \r");
				sqlString.append(" AND (BCAIT1=DCPA01 OR DCPA01='') \r");
				sqlString.append(" AND (BCAIT2=DCPA02 OR DCPA02='') \r");
				sqlString.append(" AND (BCAIT3=DCPA03 OR DCPA03='') \r");
				sqlString.append(" AND (BCAIT4=DCPA04 OR DCPA04='') \r");
				sqlString.append(" AND (BCAIT5=DCPA05 OR DCPA05='') \r");
				sqlString.append(" \r");
				sqlString.append(" GROUP BY DCPK04, M.CPYEA4, M.CPPERI, W.CPPERI \r");
				sqlString.append(" \r");
				sqlString.append(" ) AS A \r");
				sqlString.append(" GROUP BY DCPK04 \r");
				
			} catch (Exception e) {
				throwError.append(" Error building sql getSpending statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReporting.");
				throwError.append("BuildSQL.getPlannedSpending(InqOperations)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
			
		}
		

		private static String getSpending(InqOperations inqOperations, ReportingType reportingType) throws Exception {
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			
			String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
			String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";

			String warehouse = inqOperations.getWarehouse();
			String fiscalYear = inqOperations.getFiscalYear();
			String periodEnd = inqOperations.getFiscalPeriodEnd();
			String weekEnd = inqOperations.getFiscalWeekEnd();
			
			String startDate = inqOperations.getStartDate();
			String endDate = inqOperations.getEndDate();
			
			

			// Match this value type to how the spending is categorized
			// Maintenance --> Parts
			// Quality and Lab --> Supplies
			// Warehousing --> Supplies

			String valueType = "";
			if (reportingType == ReportingType.MAINT) {
				valueType = "Parts";
				startDate = inqOperations.getFiscalYearStartDate();
			} else {
				valueType = "Supplies";
			}

			try {

				sqlString.append(" SELECT \r");
				sqlString.append(" DCPK04, \r");
				sqlString.append(" SUM(AMOUNT) AS YTD_SPEND, \r");
				sqlString.append(" SUM(CASE WHEN MONTH=" + periodEnd + " THEN AMOUNT ELSE 0 END) AS MTD_SPEND, \r");
				sqlString.append(" SUM(CASE WHEN WEEK=" + weekEnd + " THEN AMOUNT ELSE 0 END) AS WTD_SPEND \r");
				sqlString.append(" \r");
				sqlString.append(" FROM ( \r");
				sqlString.append(" SELECT \r");
				sqlString.append(" C.CPYEA4 AS YEAR, \r");
				sqlString.append(" C.CPPERI AS MONTH, \r");
				sqlString.append(" D.CPPERI AS WEEK, \r");
				sqlString.append(" CASE WHEN DCPK05='' THEN DCPK04 ELSE DCPK05 END AS DCPK04, \r");
				sqlString.append(" SUM(EGACAM) AS AMOUNT \r");
				sqlString.append(" \r");
				sqlString.append(" FROM \r");
				sqlString.append(" " + ttLibrary + "DCPALL \r");
				sqlString.append(" INNER JOIN " + library + "FGLEDG ON \r");
				sqlString.append("       EGCONO=100 AND EGDIVI='100' \r");
				sqlString.append("       AND EGVSER<>'CA1'       \r"); // EXLUDE
																		// COST
																		// ACCOUNTING
																		// TRANSACTIONS
																		// (WILL
																		// BE
																		// REPORTED
																		// FROM
																		// CINACC)
				sqlString.append("       AND EGAIT1<>'53310'     \r");// INVENTORY
																		// ADJUSTMENTS
																		// REPORTED
																		// SEPARATELY
				sqlString.append(" \r");
				sqlString.append(" INNER JOIN " + library + "FCHACC ON \r");
				sqlString.append("       EGCONO=EACONO AND EGDIVI=EADIVI AND EAAITP=1 AND EAAITM=EGAIT1 AND EAAICL='510' \r");
				// EXCLUDE LABOR ACCOUNTS
				sqlString.append("       AND EARDRI<>'B01' \r");
				sqlString.append("       AND EARDRI<>'B03' \r");
				sqlString.append("       AND EARDRI<>'B05' \r");
				sqlString.append("       AND EARDRI<>'B07' \r");
				sqlString.append("       AND EARDRI<>'B09' \r");
				sqlString.append("       AND EARDRI<>'B11' \r");
				sqlString.append(" \r");
				// EXCLUDE UTILITIES SPEND ACCOUNTS  (Spend manually estimated seperately)
				sqlString.append("       AND EARDRI<>'B02' \r");
				

				sqlString.append(" \r");
				
				sqlString.append(" LEFT OUTER JOIN " + library + "CSYPER AS C ON C.CPCONO=100 AND C.CPDIVI='100' \r");
				sqlString.append(" AND C.CPPETP=1 AND EGACDT>=C.CPFDAT AND EGACDT<=C.CPTDAT \r");
				sqlString.append(" LEFT OUTER JOIN " + library + "CSYPER AS D ON D.CPCONO=100 AND D.CPDIVI='100' \r");
				sqlString.append(" AND D.CPPETP=2 AND EGACDT>=D.CPFDAT AND EGACDT<=D.CPTDAT \r");
				
				sqlString.append(" \r");
				sqlString.append(" WHERE \r");
				sqlString.append(" DCPK00='OPSRPT' \r");
				sqlString.append(" AND DCPK01='" + reportingType + "' \r");
				sqlString.append(" AND DCPK02='" + warehouse + "' \r");
				sqlString.append(" AND DCPK03='SPEND' \r");
				sqlString.append(" AND DCPK04='" + valueType + "' \r");
				sqlString.append(" \r");

				sqlString.append(" AND EGACDT>=" + startDate + " \r");
				sqlString.append(" AND EGACDT<=" + endDate + " \r");

				sqlString.append(" AND EGAIT1>='64000' \r");		//get accounts in 64000 range only
				sqlString.append(" AND EGAIT1<='64999' \r");	
				
				sqlString.append(" AND (EGAIT1=DCPA01 OR DCPA01='') \r");
				sqlString.append(" AND (EGAIT2=DCPA02 OR DCPA02='') \r");
				sqlString.append(" AND (EGAIT3=DCPA03 OR DCPA03='') \r");
				sqlString.append(" AND (EGAIT4=DCPA04 OR DCPA04='') \r");
				sqlString.append(" AND (EGAIT5=DCPA05 OR DCPA05='') \r");
				sqlString.append(" \r");
				sqlString.append(" \r");
				sqlString.append("  \r");
				sqlString.append(" GROUP BY C.CPYEA4, C.CPPERI, D.CPPERI, DCPK04, DCPK05 \r");
				sqlString.append("  \r");
				sqlString.append("  \r");
				sqlString.append(" UNION ALL \r");
				sqlString.append("  \r");
				sqlString.append(" SELECT \r");
				sqlString.append(" C.CPYEA4 AS YEAR, \r");
				sqlString.append(" C.CPPERI AS MONTH, \r");
				sqlString.append(" D.CPPERI AS WEEK, \r");
				sqlString.append(" CASE WHEN DCPK05='' THEN DCPK04 ELSE DCPK05 END AS DCPK04, \r");
				sqlString.append(" SUM(EZACAM) AS AMOUNT \r");
				sqlString.append("  \r");
				sqlString.append(" FROM \r");
				sqlString.append(" " + ttLibrary + "DCPALL \r");
				sqlString.append(" INNER JOIN " + library + "CINACC ON \r");
				sqlString.append("       EZCONO=100 AND EZDIVI='100' \r");
				sqlString.append("  \r");
				sqlString.append(" INNER JOIN " + library + "FCHACC ON \r");
				sqlString
						.append("       EZCONO=EACONO AND EZDIVI=EADIVI AND EAAITP=1 AND EAAITM=EZAIT1 AND EAAICL='510' \r");
				// --EXCLUDE LABOR ACCOUNTS
				sqlString.append("       AND EARDRI<>'B01' \r");
				sqlString.append("       AND EARDRI<>'B03' \r");
				sqlString.append("       AND EARDRI<>'B05' \r");
				sqlString.append("       AND EARDRI<>'B07' \r");
				sqlString.append("       AND EARDRI<>'B09' \r");
				sqlString.append("       AND EARDRI<>'B11' \r");
				sqlString.append("  \r");
				
				sqlString.append(" \r");
				
				sqlString.append(" LEFT OUTER JOIN " + library + "CSYPER AS C ON C.CPCONO=100 AND C.CPDIVI='100' \r");
				sqlString.append(" AND C.CPPETP=1 AND EZTRDT>=C.CPFDAT AND EZTRDT<=C.CPTDAT \r");
				sqlString.append(" LEFT OUTER JOIN " + library + "CSYPER AS D ON D.CPCONO=100 AND D.CPDIVI='100' \r");
				sqlString.append(" AND D.CPPETP=2 AND EZTRDT>=D.CPFDAT AND EZTRDT<=D.CPTDAT \r");
				
				sqlString.append("  \r");
				sqlString.append(" WHERE \r");
				sqlString.append(" DCPK00='OPSRPT' \r");
				sqlString.append(" AND DCPK01='" + reportingType + "' \r");
				sqlString.append(" AND DCPK02='" + warehouse + "' \r");
				sqlString.append(" AND DCPK03='SPEND' \r");
				sqlString.append(" AND DCPK04='" + valueType + "' \r");
				sqlString.append("  \r");

				sqlString.append(" AND EZTRDT>=" + startDate + " \r");
				sqlString.append(" AND EZTRDT<=" + endDate + " \r");
								
				sqlString.append(" AND EZAIT1>='64000' \r");		//get accounts in 64000 range only
				sqlString.append(" AND EZAIT1<='64999' \r");		
				
				sqlString.append("  \r");
				sqlString.append(" AND (EZAIT1=DCPA01 OR DCPA01='') \r");
				sqlString.append(" AND (EZAIT2=DCPA02 OR DCPA02='') \r");
				sqlString.append(" AND (EZAIT3=DCPA03 OR DCPA03='') \r");
				sqlString.append(" AND (EZAIT4=DCPA04 OR DCPA04='') \r");
				sqlString.append(" AND (EZAIT5=DCPA05 OR DCPA05='') \r");
				sqlString.append("  \r");
				sqlString.append("  \r");
				sqlString.append("  \r");
				sqlString.append(" GROUP BY C.CPYEA4, C.CPPERI, D.CPPERI, DCPK04, DCPK05 \r");
				sqlString.append("  \r");
				sqlString.append(" ) AS A \r");
				sqlString.append(" GROUP BY DCPK04 \r");

			} catch (Exception e) {
				throwError.append(" Error building sql getSpending statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReporting.");
				throwError.append("BuildSQL.getSpending(InqOperations)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();

		}

		/**
		 * 
		 */
		private static String getUtilitiesSpending(InqOperations inqOperations) throws Exception {
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";
			
			String warehouse = inqOperations.getWarehouse();
			String startDate = inqOperations.getStartDate();
			String endDate = inqOperations.getEndDate();
			
			try {

				sqlString.append("SELECT ");
				sqlString.append("DCPT40, ");
				sqlString.append("SUM(IFNULL(GNGVL1,0)) AS UNITS, ");
				sqlString.append("SUM(IFNULL(GNGVL1*GNGVL2,0)) AS ACTUAL ");
				sqlString.append(" \r ");

				sqlString.append("FROM " + ttLibrary + "DCPALL ");
				sqlString.append(" \r ");

				sqlString.append("LEFT OUTER JOIN " + ttLibrary + "GNPGUTIL ");
				sqlString.append("ON  GNGK00=DCPK00 AND GNGK01=DCPK01 AND GNGK02=DCPT40 ");
				sqlString.append(" \r ");

				sqlString.append("WHERE DCPK00='UTIL' ");
				sqlString.append("AND DCPK01 = '" + warehouse + "' ");
				sqlString.append("AND GNGDTE >=  " + startDate + " AND GNGDTE <= " + endDate + " ");
				sqlString.append("GROUP BY DCPT40 ");

			} catch (Exception e) {
				throwError.append(" Error building sql getUtilitiesSpending statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReporting.");
				throwError.append("getUtilitiesSpending(InqOperations)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * 
		 */
		private static String getBenefitsRate(InqOperations inqOperations, String fiscalYear) throws Exception {
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			try {

				// Determine Library
				String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";

				sqlString.append("SELECT ");
				// --total E02 and E07 rate
				sqlString.append("mwwhlo, sum(b.khpope)/100 as bkhpope ");

				sqlString.append("from ( ");

				sqlString.append("select ");
				sqlString.append("khcono, khelco, khfaci, khobv1, khpctp, mwwhlo, max(khfrdt) as khfrdt ");

				// --M3 Screen: PCS120
				sqlString.append("from " + library + "mcprco ");
				sqlString.append("left outer join " + library + "mitwhl ");
				sqlString.append("on khcono=mwcono and mwwhty='10' ");

				sqlString.append("where khcono=100 ");

				// -- E02-> Incentive (by faci) | E07->Burden (by faci/whse)
				sqlString.append("and (khelco='E02' or khelco='E07') ");
				sqlString.append("and khfaci=mwfaci ");
				sqlString.append("and khobv1 like case when khelco='E07' then mwwhlo||'%' else '%' end ");
				sqlString.append("and khpctp='3' ");

				// --get the ending date for the fiscal year to use as a max
				// date to select the rates
				sqlString.append("and khfrdt<=(select cpfdat from  " + library + "csyper ");
				sqlString.append("where cpcono=100 and cpdivi='100' and cppetp=1 ");
				sqlString.append("and cpyea4 = " + fiscalYear + " ");
				sqlString.append("and cpperi=1) ");

				sqlString.append("group by khcono, khelco, khfaci, khobv1, khpctp, mwwhlo ");
				sqlString.append(") as a ");
				sqlString.append("left outer join " + library + "mcprco as b on ");
				sqlString.append("a.khcono=b.khcono ");
				sqlString.append("and a.khelco=b.khelco ");
				sqlString.append("and a.khfaci=b.khfaci ");
				sqlString.append("and a.khobv1=b.khobv1 ");
				sqlString.append("and a.khpctp=b.khpctp ");
				sqlString.append("and a.khfrdt=b.khfrdt ");
				sqlString.append("group by mwwhlo ");
				sqlString.append("order by mwwhlo ");

				// *********************************************************************************
			} catch (Exception e) {
				throwError.append(" Error building sqlRates statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingFinancials.");
				throwError.append("BuildSQL.sqlGetRates(InqOperations, fiscalYear)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * 
		 */
		private static String getJournalEntries(InqOperations inqOperations) throws Exception {
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			try {

				// Determine Library
				String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";

				sqlString.append(" SELECT ");
				sqlString.append(" EGAIT1, ");
				sqlString.append(" DIM1.EATX40 AS DIM1TX, ");
				sqlString.append(" EGAIT3, ");
				sqlString.append(" DIM3.EATX40 AS DIM3TX, ");
				
				//Sign is reversed to show (positive as favorable, negative as unfavorable)
				sqlString.append(" SUM(-EGACAM) AS EGACAM ");

				sqlString.append(" \r ");

				sqlString.append(" FROM " + library + "FGLEDG ");

				sqlString.append(" \r ");

				sqlString.append(" INNER JOIN " + library + "FCHACC AS DIM1 ");
				sqlString.append("ON EGCONO = DIM1.EACONO ");
				sqlString.append("AND EGDIVI = DIM1.EADIVI ");
				sqlString.append("AND DIM1.EAAITP = 1 ");
				sqlString.append("AND DIM1.EAAITM = EGAIT1 ");
				sqlString.append("AND DIM1.EAAICL = '510' ");

				sqlString.append(" \r ");

				sqlString.append(" LEFT OUTER JOIN " + library + "FCHACC AS DIM3 ");
				sqlString.append("ON EGCONO = DIM3.EACONO ");
				sqlString.append("AND EGDIVI = DIM3.EADIVI ");
				sqlString.append("AND DIM3.EAAITP = 3 ");
				sqlString.append("AND DIM3.EAAITM = EGAIT3 ");

				sqlString.append(" \r ");

				sqlString.append("INNER JOIN " + library + "CSYPER A ");
				sqlString.append("ON A.CPCONO = 100 ");
				sqlString.append("AND A.CPDIVI = '100' ");
				sqlString.append("AND A.CPPETP = 2 ");
				sqlString.append("AND A.CPYEA4 = " + inqOperations.getFiscalYear() + " ");
				sqlString.append("AND A.CPPERI = " + inqOperations.getFiscalWeekStart() + " ");

				sqlString.append(" \r ");

				sqlString.append("INNER JOIN " + library + "CSYPER B ");
				sqlString.append("ON B.CPCONO = 100 ");
				sqlString.append("AND B.CPDIVI = '100' ");
				sqlString.append("AND B.CPPETP = 2 ");
				sqlString.append("AND B.CPYEA4 = " + inqOperations.getFiscalYear() + " ");
				sqlString.append("AND B.CPPERI = " + inqOperations.getFiscalWeekEnd() + " ");

				sqlString.append(" \r ");

				sqlString.append("INNER JOIN " + library + "MITWHL ");
				sqlString.append("ON EGCONO = MWCONO ");
				sqlString.append("AND MWWHLO = '" + inqOperations.getWarehouse() + "' ");
				sqlString.append("AND EGAIT2 = MWFACI ");

				sqlString.append(" \r ");

				sqlString.append("WHERE ");
				sqlString.append("EGCONO = 100 ");
				sqlString.append("AND EGDIVI = '100' ");
				sqlString.append("AND EGYEA4 = " + inqOperations.getFiscalYear() + " ");
				sqlString.append("AND EGVSER = 'GL1' ");
				sqlString.append("AND EGACDT >= A.CPFDAT ");
				sqlString.append("AND EGACDT <= B.CPTDAT ");

				sqlString.append("AND  ( ");
				sqlString.append("EGAIT1 = '53220' OR "); // Usage Variance
															// Materials
				sqlString.append("EGAIT1 = '53221' OR "); // Usage Variance
															// Materials
															// (Co-pack)
				sqlString.append("EGAIT1 = '64276' OR "); // Labor Bill-out
				
				// Journal Entries against labor accounts
				sqlString.append("(EGAIT1 >= '64200' AND EGAIT1<='64299' AND EGFEID='GL01' AND EGFNCN='10' )  ");
				sqlString.append(" ) ");

				if (inqOperations.getWarehouse().trim().equals("209"))
					sqlString.append("AND EGAIT3 >= '2000' AND EGAIT3 <= '2999' ");

				if (inqOperations.getWarehouse().trim().equals("240"))
					sqlString.append("AND EGAIT3 >= '3000' AND EGAIT3 <= '3999' ");

				if (inqOperations.getWarehouse().trim().equals("490"))
					sqlString.append("AND EGAIT3 >= '4000' AND EGAIT3 <= '4999' ");

				sqlString.append(" \r ");

				sqlString.append("GROUP BY EGAIT1, DIM1.EATX40, EGAIT3, DIM3.EATX40 ");
				sqlString.append("ORDER BY EGAIT3, EGAIT1 ");

			} catch (Exception e) {
				throwError.append(" Error building sql statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingFinancials.");
				throwError.append("BuildSQL.getPlantJournals(InqOperations)");
				throw new Exception(throwError.toString());
			}

			return sqlString.toString();
		}

		/**
		 * 
		 */
		private static String getInventoryAdjustments(InqOperations inqOperations) throws Exception {
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			try {

				// Determine Library
				String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";

				sqlString.append(" SELECT ");
				sqlString.append(" ifNull(COCE,'BLANK') AS CostCenter, ");
				sqlString.append(" ifNull(EATX40,'BLANK') as CostCenterDescr, ");
				sqlString.append(" ifNull(RSCD,'') as reasonCode, ");
				sqlString.append(" ifNull(RSCDTX,'') as reasonDescription, ");
				sqlString.append(" ifNull(sum(QTY),0) as quantity, ");
				sqlString.append(" ifNull(SUM(AMOUNT),0) AS dollars ");

				sqlString.append(" \r ");
				sqlString.append(" FROM ( ");
				sqlString.append(" \r ");

				sqlString.append(" SELECT EGAIT3 AS COCE, ");
				sqlString.append(" EGVSER AS RSCD, ");
				sqlString.append(" CASE WHEN EGVSER='GL1' THEN 'Journal Entry' ");
				sqlString.append("      WHEN EGVSER='AP1' THEN 'Accounts Payable' ");
				sqlString.append("      ELSE EGVSER END AS RSCDTX, ");
				sqlString.append(" 0 as qty, ");
				sqlString.append(" SUM(-EGACAM) AS AMOUNT ");

				sqlString.append(" \r ");

				sqlString.append(" FROM " + library + "FGLEDG ");

				sqlString.append(" INNER JOIN " + library + "CSYPER AS A ON ");
				sqlString.append("       A.CPCONO=100 AND A.CPDIVI='100' ");
				sqlString.append("       AND A.CPPETP=2 AND A.CPYEA4=" + inqOperations.getFiscalYear()
						+ " AND A.CPPERI=" + inqOperations.getFiscalWeekStart() + " ");
				sqlString.append(" INNER JOIN " + library + "CSYPER AS B ON ");
				sqlString.append("       B.CPCONO=100 AND B.CPDIVI='100' ");
				sqlString.append("       AND B.CPPETP=2 AND B.CPYEA4=" + inqOperations.getFiscalYear()
						+ " AND B.CPPERI=" + inqOperations.getFiscalWeekEnd() + " ");

				sqlString.append(" \r ");

				sqlString.append(" WHERE EGCONO=100 AND EGDIVI='100' ");
				sqlString.append(" AND EGAIT1='53310' ");

				sqlString.append(" AND EGAIT2 = '" + inqOperations.getBean().getWarehouse().getFacility() + "' ");

				if (inqOperations.getWarehouse().equals("209")) {
					sqlString.append(" AND EGAIT3 >= '2000' ");
					sqlString.append(" AND EGAIT3 <= '2999' ");
				} else if (inqOperations.getWarehouse().equals("240")) {
					sqlString.append(" AND EGAIT3 >= '3000' ");
					sqlString.append(" AND EGAIT3 <= '3999' ");
				} else if (inqOperations.getWarehouse().equals("490")) {
					sqlString.append(" AND EGAIT3 >= '4000' ");
					sqlString.append(" AND EGAIT3 <= '4999' ");
				}

				sqlString.append(" AND EGVSER<>'CA1' ");

				sqlString.append(" AND EGACDT >= A.CPFDAT ");
				sqlString.append(" AND EGACDT <= B.CPTDAT ");

				sqlString.append(" \r ");

				sqlString.append(" GROUP BY EGAIT3, EGVSER ");

				sqlString.append(" \r ");

				sqlString.append(" UNION ALL ");

				sqlString.append(" \r ");

				sqlString.append(" SELECT EZAIT3 AS COCE, ");

				sqlString.append(" IFNULL(MRRSCD,'') AS RSCD, ");

				sqlString.append(" CASE WHEN MRRSCD IS NOT NULL AND MRRSCD='' THEN 'REASON CODE BLANK' ");
				sqlString.append("      WHEN MRRSCD IS NOT NULL THEN RSCD.CTTX40 ");
				sqlString.append("      ELSE TTYP.CTTX40 END AS RSCDTX, ");
				sqlString.append(" sum(mttrqt) as qty, ");
				sqlString.append(" SUM(-EZACAM) AS AMOUNT ");

				sqlString.append(" \r ");

				sqlString.append(" FROM " + library + "CINACC ");

				sqlString.append(" INNER JOIN " + library + "CSYPER AS A ON ");
				sqlString.append("       A.CPCONO=100 AND A.CPDIVI='100' ");
				sqlString.append("       AND A.CPPETP=2 AND A.CPYEA4=" + inqOperations.getFiscalYear()
						+ " AND A.CPPERI=" + inqOperations.getFiscalWeekStart() + " ");
				sqlString.append(" INNER JOIN " + library + "CSYPER AS B ON ");
				sqlString.append("       B.CPCONO=100 AND B.CPDIVI='100' ");
				sqlString.append("       AND B.CPPETP=2 AND B.CPYEA4=" + inqOperations.getFiscalYear()
						+ " AND B.CPPERI=" + inqOperations.getFiscalWeekEnd() + " ");

				sqlString.append(" LEFT OUTER JOIN " + library + "MITTRA ON ");
				sqlString.append("       EZCONO=MTCONO AND EZANBR=MTANBR ");
				sqlString.append(" LEFT OUTER JOIN " + library + "MGLINE ON ");
				sqlString.append("      EZCONO=MRCONO AND MTRIDN=MRTRNR AND MTRIDL=MRPONR ");
				sqlString.append(" LEFT OUTER JOIN " + library + "CSYTAB AS RSCD ON ");
				sqlString.append("      EZCONO=RSCD.CTCONO AND RSCD.CTSTCO='RSCD' AND RSCD.CTSTKY=MRRSCD ");
				sqlString.append(" LEFT OUTER JOIN " + library + "CSYTAB AS TTYP ON ");
				sqlString.append("      EZCONO=TTYP.CTCONO AND TTYP.CTSTCO='TTYP' AND TTYP.CTSTKY=MTTTYP ");

				sqlString.append(" \r ");

				sqlString.append(" WHERE EZCONO=100 AND EZDIVI='100' ");
				sqlString.append(" AND EZAIT1='53310' ");

				sqlString.append(" AND EZAIT2 = '" + inqOperations.getBean().getWarehouse().getFacility() + "' ");

				if (inqOperations.getWarehouse().equals("209")) {
					sqlString.append(" AND EZAIT3 >= '2000' ");
					sqlString.append(" AND EZAIT3 <= '2999' ");
				} else if (inqOperations.getWarehouse().equals("240")) {
					sqlString.append(" AND EZAIT3 >= '3000' ");
					sqlString.append(" AND EZAIT3 <= '3999' ");
				} else if (inqOperations.getWarehouse().equals("490")) {
					sqlString.append(" AND EZAIT3 >= '4000' ");
					sqlString.append(" AND EZAIT3 <= '4999' ");
				}

				sqlString.append(" AND EZTRDT >= A.CPFDAT ");
				sqlString.append(" AND EZTRDT <= B.CPTDAT ");

				sqlString.append(" \r ");

				sqlString.append(" GROUP BY EZAIT3, MRRSCD, RSCD.CTTX40, TTYP.CTTX40 ");

				sqlString.append(" \r ");

				sqlString.append(" ) AS A ");

				sqlString.append(" \r ");

				sqlString.append(" LEFT OUTER JOIN " + library + "FCHACC ON ");
				sqlString.append("      EACONO=100 AND EADIVI='100' AND EAAITP=3 AND EAAITM=COCE ");

				sqlString.append(" \r ");

				sqlString.append(" GROUP BY COCE, EATX40, RSCD, RSCDTX ");

				sqlString.append(" \r ");

				sqlString.append(" HAVING SUM(AMOUNT)<>0 ");

				sqlString.append(" \r ");

				sqlString.append(" ORDER BY COCE, RSCD, RSCDTX ");

			} catch (Exception e) {
				throwError.append(" Error building sql statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingFinancials.");
				throwError.append("BuildSQL.getInvAdjForPlant(InqOperations)");
				throw new Exception(throwError.toString());
			}

			return sqlString.toString();
		}
	}

	private static class LoadFields {

		private static void utilitiesRates(ResultSet rs, int fiscalYear) throws Exception {
			
			StringBuffer throwError = new StringBuffer();

			try {
				
				String warehouse = rs.getString("DCPK01").trim();
				String account = rs.getString("DCPT40").trim();
				String workCenter = rs.getString("BETFA4").trim();
				BigDecimal rate = rs.getBigDecimal("BEDIPE").setScale(3, BigDecimal.ROUND_HALF_UP);
				
				String key = fiscalYear + ":" + warehouse + ":" + account + ":" + workCenter;
				utilitiesRates.put(key, rate);
				
			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingFinance.");
				throwError.append("spending(ResultSet, ManufacturingFinance). " + e);
				throw new Exception(throwError.toString());
			}
			
		}
		
		private static void spending(ResultSet rs, LinkedHashMap<String, ManufacturingFinance> lhm) throws Exception {

			StringBuffer throwError = new StringBuffer();

			try {

				String key = rs.getString("DCPK04").trim();
				ManufacturingFinance mf = lhm.get(key);
				if (mf == null) {
					mf = new ManufacturingFinance();
					lhm.put(key, mf);
				}
				
				BigDecimal weekToDateSpending = rs.getBigDecimal("WTD_SPEND").setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal monthToDateSpending = rs.getBigDecimal("MTD_SPEND").setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal yearToDateSpending = rs.getBigDecimal("YTD_SPEND").setScale(2, BigDecimal.ROUND_HALF_UP);
				
				mf.setWtdActual(weekToDateSpending);
				mf.setMtdActual(monthToDateSpending);
				mf.setYtdActual(yearToDateSpending);
				
				
			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingFinance.");
				throwError.append("spending(ResultSet, ManufacturingFinance). " + e);
				throw new Exception(throwError.toString());
			}

		}
		
		private static void plannedSpending(ResultSet rs, LinkedHashMap<String, ManufacturingFinance> lhm) throws Exception {

			StringBuffer throwError = new StringBuffer();

			try {

				String key = rs.getString("DCPK04").trim();
				ManufacturingFinance mf = lhm.get(key);
				if (mf == null) {
					mf = new ManufacturingFinance();
					lhm.put(key, mf);
				}
				
				BigDecimal weekToDatePlannedSpending = rs.getBigDecimal("WTD_PLAN").setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal monthToDatePlannedSpending = rs.getBigDecimal("MTD_PLAN").setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal yearToDatePlannedSpending = rs.getBigDecimal("YTD_PLAN").setScale(2, BigDecimal.ROUND_HALF_UP);
				
				mf.setWtdPlan(weekToDatePlannedSpending);
				mf.setMtdPlan(monthToDatePlannedSpending);
				mf.setYtdPlan(yearToDatePlannedSpending);
				
				
			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingFinance.");
				throwError.append("spending(ResultSet, ManufacturingFinance). " + e);
				throw new Exception(throwError.toString());
			}

		}

		/**
		 * 
		 */
		private static void utilitiesSpending(ResultSet rs, InqOperations inqOperations) throws Exception {

			StringBuffer throwError = new StringBuffer();
			BigDecimal variance = null;
			LinkedHashMap<String, ManufacturingFinance> lhm = inqOperations.getBean().getUtilities();

			try {

				while (rs.next()) {

					String group = rs.getString("DCPT40");
					group = (group == null) ? "Undefined" : group.trim();

					ManufacturingFinance mf = lhm.get(group);
					if (mf == null) {
						mf = new ManufacturingFinance();
						lhm.put(group, mf);
					}

					BigDecimal wtdUnits = rs.getBigDecimal("UNITS").setScale(0, BigDecimal.ROUND_HALF_UP);
					mf.setWtdUnits(wtdUnits);

					BigDecimal wtdActual = rs.getBigDecimal("ACTUAL").setScale(2, BigDecimal.ROUND_HALF_UP);
					mf.setWtdActual(wtdActual);

				}

			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingFinance.");
				throwError.append("utilitiesDollars(ResultSet). " + e);
				throw new Exception(throwError.toString());
			}

		}

		/**
		 * 
		 */
		private static void benefitsRate(ResultSet rs, String fiscalYear) throws Exception {

			StringBuffer throwError = new StringBuffer();

			try {
				while (rs.next()) {

					// define the hash table key
					String key = fiscalYear + "-" + rs.getString("mwwhlo").trim();
					BigDecimal rate = rs.getBigDecimal("bkhpope");
					rate = rate.setScale(2, BigDecimal.ROUND_HALF_UP);
					
					benefitsRates.put(key, rate);

				}

			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReporting.");
				throwError.append("LoadFields.plantRates(ResultSet, Hashtable, String). " + e);
				throw new Exception(throwError.toString());
			}

		}

		/**
		 * 
		 */
		private static void journalEntries(ResultSet rs, InqOperations inqOperations) throws Exception {

			StringBuffer throwError = new StringBuffer();
			LinkedHashMap<String, AccountData> journals = inqOperations.getBean().getPlantJournals();

			try {
				while (rs.next()) {

					AccountData ad = new AccountData();
					ad.setDim1(rs.getString("EGAIT1").trim());
					ad.setDim1Description(rs.getString("DIM1TX").trim());

					ad.setDim3(rs.getString("EGAIT3").trim());
					ad.setDim3Description(rs.getString("DIM3TX").trim());

					ad.setAmount1(rs.getBigDecimal("EGACAM").setScale(2, BigDecimal.ROUND_HALF_UP));

					// define the hash table key
					String key = ad.getDim1() + "-" + ad.getDim3();

					journals.put(key, ad);
				}

			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingFinancials. ");
				throwError.append("LoadFields.plantJournals(ResultSet, InqOperations). " + e);
				throw new Exception(throwError.toString());
			}

		}

		/**
		 * 
		 */
		private static void inventoryAdjustments(ResultSet rs, InqOperations inqOperations) throws Exception {

			StringBuffer throwError = new StringBuffer();
			LinkedHashMap<String, AccountData> adjustments = inqOperations.getBean().getInventoryAdjustments();

			try {
				while (rs.next()) {
					// object returned
					AccountData ad = new AccountData();
					ad.setDim3(rs.getString("CostCenter").trim());
					ad.setDim3Description(rs.getString("CostCenterDescr").trim());
					ad.setDim4(rs.getString("reasonCode").trim());
					ad.setDim4Description(rs.getString("reasonDescription").trim());

					BigDecimal quantity = rs.getBigDecimal("quantity").setScale(0, BigDecimal.ROUND_HALF_UP);
					ad.setQuantity1(quantity);

					BigDecimal dollars = rs.getBigDecimal("dollars").setScale(2, BigDecimal.ROUND_HALF_UP);
					ad.setAmount1(dollars);

					// define the hash table key
					String key = rs.getString("CostCenter").trim() + "-" + rs.getString("reasonCode").trim();
					adjustments.put(key, ad);
				}

			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingFinancials. ");
				throwError.append("LoadFields.invAdjByPlant(ResultSet, InqOperations). " + e);
				throw new Exception(throwError.toString());
			}

		}
	}

	private static void getSpending(InqOperations inqOperations, ReportingType reportingType,
			LinkedHashMap<String, ManufacturingFinance> lhm, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();

		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			String sql = BuildSQL.getSpending(inqOperations, reportingType);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				LoadFields.spending(rs, lhm);
				
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
			throwError.append("ServiceOperationsReportingFinancials.");
			throwError.append("getSpending(");
			throwError.append("InqOperations, ReportingType, conn). ");
			throw new Exception(throwError.toString());
		}
	}
	
	private static void getPlannedSpending(InqOperations inqOperations, ReportingType reportingType,
			LinkedHashMap<String, ManufacturingFinance> lhm, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();

		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			String sql = BuildSQL.getPlannedSpending(inqOperations, reportingType);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				LoadFields.plannedSpending(rs, lhm);
				
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
			throwError.append("ServiceOperationsReportingFinancials.");
			throwError.append("getPlannedSpending(");
			throwError.append("InqOperations, ReportingType, conn). ");
			throw new Exception(throwError.toString());
		}
	}

	/**
	 * Return Maintenance Report Data for labor and parts.
	 * 
	 * @param InqOperations
	 * @return void -- pointer of what was sent in should work as the return
	 * @throws Exception
	 */
	public static void getMaintenanceDollars(InqOperations inqOperations) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;

		if (inqOperations == null) {
			throwError.append("Inquiry Bean cannot be null.  ");
		} else {
			if (inqOperations.getEnvironment().equals("")) {
				throwError.append("Environment cannot be empty.  ");
			}
			if (inqOperations.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be empty.  ");
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

			try {
				// get a connection
				conn = ServiceConnection.getConnectionStack11();

				LinkedHashMap<String, ManufacturingFinance> lhm = inqOperations.getBean().getMaintenance();
				
				getSpending(inqOperations, ReportingType.MAINT, lhm, conn);
				
				getPlannedSpending(inqOperations, ReportingType.MAINT, lhm, conn);
				
				ServiceOperationsReportingPayroll.getMaintenanceLabor(inqOperations, lhm);
				
				ServiceOperationsReportingManufacturing.getEarningsFinancial(inqOperations, lhm, ReportingType.MAINT);
				
				
				loadBenefits(inqOperations, lhm);
				
				//calculate the variances
				for (String key : lhm.keySet()) {
					ManufacturingFinance mf = lhm.get(key);
					calculateVariances(mf);
				}
				
				
				
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
			throwError.append("ServiceOperationsReporting.");
			throwError.append("getMaintenanceDollars(InqOperations). ");
			throw new Exception(throwError.toString());
		}

	}
	
	private static void loadBenefits(InqOperations inqOperations, LinkedHashMap<String, ManufacturingFinance> lhm) throws Exception {
		StringBuffer throwError = new StringBuffer();
		try {
			
			BigDecimal rate = getBenefitsRate(inqOperations);
			
			ManufacturingFinance benefits = new ManufacturingFinance();
			ManufacturingFinance labor = lhm.get("Labor");
			if (labor != null) {
				benefits.setWtdActual(labor.getWtdActual().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP));
				benefits.setWtdEarnings(labor.getWtdEarnings().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP));
				benefits.setWtdPlan(labor.getWtdPlan().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP));
				
				benefits.setMtdActual(labor.getMtdActual().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP));
				benefits.setMtdEarnings(labor.getMtdEarnings().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP));
				benefits.setMtdPlan(labor.getMtdPlan().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP));
				
				benefits.setYtdActual(labor.getYtdActual().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP));
				benefits.setYtdEarnings(labor.getYtdEarnings().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP));
				benefits.setYtdPlan(labor.getYtdPlan().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			
			lhm.put("Benefits", benefits);
			
			
		} catch (Exception e) {
			throwError.append("Error loading benefits object.  " + e);
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingFinancials.");
			throwError.append("loadBenefits(InqOperations). ");
			throw new Exception(throwError.toString());
		}
	}

	private static void calculateVariances(ManufacturingFinance mf) {
		BigDecimal variance = BigDecimal.ZERO;

		// Earnings versus Actual
		// Week to date
		variance = mf.getWtdEarnings().subtract(mf.getWtdActual());
		mf.setWtdEarningsVar(variance);

		// Month to Date
		variance = mf.getMtdEarnings().subtract(mf.getMtdActual());
		mf.setMtdEarningsVar(variance);

		// Year to Date
		variance = mf.getYtdEarnings().subtract(mf.getYtdActual());
		mf.setYtdEarningsVar(variance);

		// Plan versus Actual
		// Week to date
		variance = mf.getWtdPlan().subtract(mf.getWtdActual());
		mf.setWtdPlanVar(variance);
		// Month to Date
		variance = mf.getMtdPlan().subtract(mf.getMtdActual());
		mf.setMtdPlanVar(variance);
		// Year to Date
		variance = mf.getYtdPlan().subtract(mf.getYtdActual());
		mf.setYtdPlanVar(variance);

	}
	
	@RunWith(Suite.class)
	@Suite.SuiteClasses({
		ServiceOperationsReportingFinancials.UnitTests.Selah.class})
	public static class UnitTests {

		private static InqOperations inqOperations = null;
		private static Connection conn = null;
		
		@BeforeClass
		public static void setUpBeforeClass() throws Exception {
			conn = ServiceConnection.getConnectionStack11();
		}
		
		@Before
		public void setUp() {
			inqOperations = new InqOperations();
			inqOperations.setEnvironment("PRD");
			inqOperations.setFiscalYear("2013");
			inqOperations.setFiscalPeriodStart("1");
			inqOperations.setFiscalPeriodEnd("4");
			inqOperations.setFiscalPeriodStart("1");
			inqOperations.setFiscalPeriodEnd("21");
		}
		
		@After
		public void tearDown() {
			inqOperations = null;
		}
		
		@AfterClass
		public static void tearDownAfterClass() throws Exception {
			ServiceConnection.returnConnectionStack11(conn);
		}
		
		
		public static class Selah {
			
			private static InqOperations inqOperations = null;
			private static Connection conn = null;
			
			@BeforeClass
			public static void setUpBeforeClass() throws Exception {
				conn = ServiceConnection.getConnectionStack11();
			}
			
			@Before
			public void setUp() throws Exception {
				inqOperations = new InqOperations();
				inqOperations.setEnvironment("PRD");
				inqOperations.setWarehouse("209");
				inqOperations.setFiscalYear("2013");
				inqOperations.setFiscalPeriodStart("1");
				inqOperations.setFiscalPeriodEnd("5");
				inqOperations.setFiscalWeekStart("1");
				inqOperations.setFiscalWeekEnd("21");
				ServiceOperationsReporting.getOperationsWarehouse(inqOperations);
				
				inqOperations.buildDateRangeFromWeek();
				
			}
			
			@After
			public void tearDown() {
				inqOperations = null;
			}
			
			@AfterClass
			public static void tearDownAfterClass() throws Exception {
				ServiceConnection.returnConnectionStack11(conn);
			}
			
			@Test
			public void testUtilities() throws Exception {
				
				getUtilitiesDollars(inqOperations);

				LinkedHashMap<String, ManufacturingFinance> lhm = inqOperations.getBean().getUtilities();

				for (String key : lhm.keySet()) {
					ManufacturingFinance mf = lhm.get(key);
					if (key.equals("Fuel")) {

						assertEquals(key + "  WTD Units", new BigDecimal("745494"), mf.getWtdUnits());
						assertEquals(key + "  WTD Actual",  new BigDecimal("413881.35"), mf.getWtdActual());
						assertEquals(key + "  WTD Earnings", new BigDecimal("420172.05"), mf.getWtdEarnings());

					} else if (key.equals("Water")) {

						assertEquals(key + "  WTD Units", new BigDecimal("74628658"), mf.getWtdUnits());
						assertEquals(key + "  WTD Actual", new BigDecimal("144382.68"), mf.getWtdActual());
						assertEquals(key + "  WTD Earnings", new BigDecimal("245469.68"), mf.getWtdEarnings());

					} else if (key.equals("Power")) {

						assertEquals(key + "  WTD Units", new BigDecimal("5071811"), mf.getWtdUnits() );
						assertEquals(key + "  WTD Actual", new BigDecimal("404536.87"), mf.getWtdActual());
						assertEquals(key + "  WTD Earnings", new BigDecimal("493871.13"), mf.getWtdEarnings());

					}

				}

			}
			
			@Test
			public void testJournalEntries() throws Exception {

				inqOperations.setWarehouse("280");
				ServiceOperationsReporting.getOperationsWarehouse(inqOperations);
				getJournalEntries(inqOperations);
				
			}
			
			
			@Test
			public void testInventoryAdjustments() throws Exception {

				inqOperations.setWarehouse("280");
				ServiceOperationsReporting.getOperationsWarehouse(inqOperations);
				getInventoryAdjustments(inqOperations);
				
			}
			
			@Test
			public void testUtilitiesRates() throws Exception {
				loadUtilitesRates(2013);
				for (String key : utilitiesRates.keySet()) {
					
					System.out.println(key + "\t=\t" + utilitiesRates.get(key));
					
				}
			}
			
			@Test
			public void testMaintenance() throws Exception {

				getMaintenanceDollars(inqOperations);
				
				LinkedHashMap<String, ManufacturingFinance> lhm = inqOperations.getBean().getMaintenance();
				
				for (String key : lhm.keySet()) {
					ManufacturingFinance mf = lhm.get(key);
					
					if (key.equals("Labor")) {
						assertEquals(key + " Week to date actual", new BigDecimal("31546.16"), mf.getWtdActual());
						assertEquals(key + " Week to date earn", new BigDecimal("30856.05"), mf.getWtdEarnings());
						assertEquals(key + " Week to date plan", new BigDecimal("31000.00"), mf.getWtdPlan());
						assertEquals(key + " Week to date earn variance", new BigDecimal("-690.11"), mf.getWtdEarningsVar());
						assertEquals(key + " Week to date plan variance", new BigDecimal("-546.16"), mf.getWtdPlanVar());
						
						assertEquals(key + " Month to date actual", new BigDecimal("123216.16"), mf.getMtdActual());
						assertEquals(key + " Month to date earn", new BigDecimal("159230.12"), mf.getMtdEarnings());
						assertEquals(key + " Month to date plan", new BigDecimal("124000.00"), mf.getMtdPlan());
						assertEquals(key + " Month to date earn variance", new BigDecimal("36013.96"), mf.getMtdEarningsVar());
						assertEquals(key + " Month to date plan variance", new BigDecimal("783.84"), mf.getMtdPlanVar());
						
						assertEquals(key + " Year to date actual", new BigDecimal("607405.44"), mf.getYtdActual());
						assertEquals(key + " Year to date earn", new BigDecimal("629851.80"), mf.getYtdEarnings());
						assertEquals(key + " Year to date plan", new BigDecimal("621382.86"), mf.getYtdPlan());
						assertEquals(key + " Year to date earn variance", new BigDecimal("22446.36"), mf.getYtdEarningsVar());
						assertEquals(key + " Year to date plan variance", new BigDecimal("13977.42"), mf.getYtdPlanVar());
						
					}
					
					if (key.equals("Parts")) {
						assertEquals(key + " Week to date actual", new BigDecimal("13531.44"), mf.getWtdActual());
						assertEquals(key + " Week to date earn", new BigDecimal("36416.98"), mf.getWtdEarnings());
						assertEquals(key + " Week to date plan", new BigDecimal("26982.75"), mf.getWtdPlan());
						assertEquals(key + " Week to date earn variance", new BigDecimal("22885.54"), mf.getWtdEarningsVar());
						assertEquals(key + " Week to date plan variance", new BigDecimal("13451.31"), mf.getWtdPlanVar());
						
						assertEquals(key + " Month to date actual", new BigDecimal("117144.34"), mf.getMtdActual());
						assertEquals(key + " Month to date earn", new BigDecimal("203928.51"), mf.getMtdEarnings());
						assertEquals(key + " Month to date plan", new BigDecimal("107931.00"), mf.getMtdPlan());
						assertEquals(key + " Month to date earn variance", new BigDecimal("86784.17"), mf.getMtdEarningsVar());
						assertEquals(key + " Month to date plan variance", new BigDecimal("-9213.34"), mf.getMtdPlanVar());
						
						assertEquals(key + " Year to date actual", new BigDecimal("1021595.43"), mf.getYtdActual());
						assertEquals(key + " Year to date earn", new BigDecimal("801911.02"), mf.getYtdEarnings());
						assertEquals(key + " Year to date plan", new BigDecimal("1012535.09"), mf.getYtdPlan());
						assertEquals(key + " Year to date earn variance", new BigDecimal("-219684.41"), mf.getYtdEarningsVar());
						assertEquals(key + " Year to date plan variance", new BigDecimal("-9060.34"), mf.getYtdPlanVar());
					}
					
					
				}
				
				
			}
			
			@Test
			public void testQuality() throws Exception {

				getQualityDollars(inqOperations);
				
				LinkedHashMap<String, ManufacturingFinance> lhm = inqOperations.getBean().getQuality();
				
				for (String key : lhm.keySet()) {
					ManufacturingFinance mf = lhm.get(key);
					
					if (key.equals("Labor")) {
						assertEquals(key + " Week to date actual", new BigDecimal("31546.16"), mf.getWtdActual());
						assertEquals(key + " Week to date earn", new BigDecimal("30855.19"), mf.getWtdEarnings());
						assertEquals(key + " Week to date plan", new BigDecimal("31000.00"), mf.getWtdPlan());
						assertEquals(key + " Week to date earn variance", new BigDecimal("-690.97"), mf.getWtdEarningsVar());
						assertEquals(key + " Week to date plan variance", new BigDecimal("-546.16"), mf.getWtdPlanVar());
						
						assertEquals(key + " Month to date actual", new BigDecimal("123216.16"), mf.getMtdActual());
						assertEquals(key + " Month to date earn", new BigDecimal("159230.20"), mf.getMtdEarnings());
						assertEquals(key + " Month to date plan", new BigDecimal("124000.00"), mf.getMtdPlan());
						assertEquals(key + " Month to date earn variance", new BigDecimal("36014.04"), mf.getMtdEarningsVar());
						assertEquals(key + " Month to date plan variance", new BigDecimal("783.84"), mf.getMtdPlanVar());
						
						assertEquals(key + " Year to date actual", new BigDecimal("607405.44"), mf.getYtdActual());
						assertEquals(key + " Year to date earn", new BigDecimal("629871.42"), mf.getYtdEarnings());
						assertEquals(key + " Year to date plan", new BigDecimal("621382.86"), mf.getYtdPlan());
						assertEquals(key + " Year to date earn variance", new BigDecimal("22465.98"), mf.getYtdEarningsVar());
						assertEquals(key + " Year to date plan variance", new BigDecimal("13977.42"), mf.getYtdPlanVar());
						
					}
					
					if (key.equals("Parts")) {
						assertEquals(key + " Week to date actual", new BigDecimal("13531.44"), mf.getWtdActual());
						assertEquals(key + " Week to date earn", new BigDecimal("36414.98"), mf.getWtdEarnings());
						assertEquals(key + " Week to date plan", new BigDecimal("26982.75"), mf.getWtdPlan());
						assertEquals(key + " Week to date earn variance", new BigDecimal("22883.54"), mf.getWtdEarningsVar());
						assertEquals(key + " Week to date plan variance", new BigDecimal("13451.31"), mf.getWtdPlanVar());
						
						assertEquals(key + " Month to date actual", new BigDecimal("117144.34"), mf.getMtdActual());
						assertEquals(key + " Month to date earn", new BigDecimal("203914.92"), mf.getMtdEarnings());
						assertEquals(key + " Month to date plan", new BigDecimal("107931.00"), mf.getMtdPlan());
						assertEquals(key + " Month to date earn variance", new BigDecimal("86770.58"), mf.getMtdEarningsVar());
						assertEquals(key + " Month to date plan variance", new BigDecimal("-9213.34"), mf.getMtdPlanVar());
						
						assertEquals(key + " Year to date actual", new BigDecimal("1021595.43"), mf.getYtdActual());
						assertEquals(key + " Year to date earn", new BigDecimal("801870.26"), mf.getYtdEarnings());
						assertEquals(key + " Year to date plan", new BigDecimal("1012535.09"), mf.getYtdPlan());
						assertEquals(key + " Year to date earn variance", new BigDecimal("-219725.17"), mf.getYtdEarningsVar());
						assertEquals(key + " Year to date plan variance", new BigDecimal("-9060.34"), mf.getYtdPlanVar());
					}
					
					
				}
				
				
			}
			
		}
		
/*
		public class Prosser {
			
		}
		
		public class Medford {
			
		}
		
		public class Woodburn {
			
		}*/
		
		@Test
		public void testGetUtilitiesDollars209() throws Exception {
			inqOperations.setEnvironment("PRD");
			inqOperations.setWarehouse("209");
			inqOperations.setFiscalYear("2013");
			inqOperations.setFiscalPeriodStart("5");
			inqOperations.setFiscalPeriodEnd("5");
			inqOperations.setFiscalWeekStart("19");
			inqOperations.setFiscalWeekEnd("19");

			getUtilitiesDollars(inqOperations);

			LinkedHashMap<String, ManufacturingFinance> lhm = inqOperations.getBean().getUtilities();

			for (String key : lhm.keySet()) {
				ManufacturingFinance mf = lhm.get(key);
				if (key.equals("Fuel")) {

					assertEquals(key + "  WTD Units", mf.getWtdUnits(), new BigDecimal("59596"));
					assertEquals(key + "  WTD Actual", mf.getWtdActual(), new BigDecimal("36949.52"));
					// assertEquals(key + "  WTD Earnings", mf.getWtdEarnings(),
					// new BigDecimal("13506.58"));

				} else if (key.equals("Water")) {

					assertEquals(key + "  WTD Units", mf.getWtdUnits(), new BigDecimal("4888463"));
					assertEquals(key + "  WTD Actual", mf.getWtdActual(), new BigDecimal("9468.95"));
					// assertEquals(key + "  WTD Earnings", mf.getWtdEarnings(),
					// new BigDecimal("9415.53"));

				} else if (key.equals("Power")) {

					assertEquals(key + "  WTD Units", mf.getWtdUnits(), new BigDecimal("731170"));
					assertEquals(key + "  WTD Actual", mf.getWtdActual(), new BigDecimal("59955.94"));
					// assertEquals(key + "  WTD Earnings", mf.getWtdEarnings(),
					// new BigDecimal("32891.39"));

				}

			}

		}


		@Test
		public void testGetBenefitsRate() throws Exception {

			inqOperations.setEnvironment("PRD");
			inqOperations.setFiscalYear("2013");

			inqOperations.setWarehouse("209");
			assertEquals("fiscal 2013 whse 209 ", new BigDecimal(".64"), getBenefitsRate(inqOperations));

			inqOperations.setWarehouse("280");
			assertEquals("fiscal 2013 whse 280 ", new BigDecimal(".50"), getBenefitsRate(inqOperations));

		}

		@Test
		public void testGetPlantJournals() throws Exception {
			inqOperations.setEnvironment("PRD");
			inqOperations.setWarehouse("209");
			inqOperations.setFiscalYear("2013");
			inqOperations.setFiscalWeekStart("17");
			inqOperations.setFiscalWeekEnd("17");

			getJournalEntries(inqOperations);

			LinkedHashMap<String, AccountData> lhm = inqOperations.getBean().getPlantJournals();

			for (String key : lhm.keySet()) {
				AccountData ad = lhm.get(key);

				if (key.equals("53220-2000")) {
					assertEquals(key, new BigDecimal("-7912.53"), ad.getAmount1());
				}

				if (key.equals("64277-2000")) {
					assertEquals(key, new BigDecimal("8584.56"), ad.getAmount1());
				}
				if (key.equals("53220-2030")) {
					assertEquals(key, new BigDecimal("-294.41"), ad.getAmount1());
				}
				if (key.equals("53221-2080")) {
					assertEquals(key, new BigDecimal("1610.99"), ad.getAmount1());
				}
				if (key.equals("64277-2080")) {
					assertEquals(key, new BigDecimal("7764.56"), ad.getAmount1());
				}
				if (key.equals("64277-2081")) {
					assertEquals(key, new BigDecimal("3810.49"), ad.getAmount1());
				}
				if (key.equals("64276-2110")) {
					assertEquals(key, new BigDecimal("-14410.15"), ad.getAmount1());
				}
				if (key.equals("64277-2110")) {
					assertEquals(key, new BigDecimal("9140.09"), ad.getAmount1());
				}
				if (key.equals("64277-2420")) {
					assertEquals(key, new BigDecimal("1562.88"), ad.getAmount1());
				}
				if (key.equals("64276-2440")) {
					assertEquals(key, new BigDecimal("17568.88"), ad.getAmount1());
				}

			}
		}

		@Test
		public void testGetInvAdjByPlant() throws Exception {
			inqOperations.setEnvironment("PRD");
			inqOperations.setWarehouse("469");
			inqOperations.setFiscalYear("2013");
			inqOperations.setFiscalWeekStart("15");
			inqOperations.setFiscalWeekEnd("15");

			getInventoryAdjustments(inqOperations);

			LinkedHashMap<String, AccountData> lhm = inqOperations.getBean().getPlantJournals();

			for (String key : lhm.keySet()) {
				AccountData ad = lhm.get(key);
				if (key.equals("2000-M01")) {
					assertEquals(key + " " + ad.getEntryText() + " = ", ad.getAmount1(), new BigDecimal("-5273.40"));
					assertEquals(key + " " + ad.getEntryText() + " = ", ad.getQuantity1(), new BigDecimal("-2650"));
				}

				if (key.equals("2025-W01")) {
					assertEquals(key + " " + ad.getEntryText() + " = ", ad.getAmount1(), new BigDecimal("-231.43"));
					assertEquals(key + " " + ad.getEntryText() + " = ", ad.getQuantity1(), new BigDecimal("-5"));
				}

				if (key.equals("2080-M05")) {
					assertEquals(key + " " + ad.getEntryText() + " = ", ad.getAmount1(), new BigDecimal("1047.04"));
					assertEquals(key + " " + ad.getEntryText() + " = ", ad.getQuantity1(), new BigDecimal("451"));
				}

				if (key.equals("2080-M07")) {
					assertEquals(key + " " + ad.getEntryText() + " = ", ad.getAmount1(), new BigDecimal("2863.93"));
					assertEquals(key + " " + ad.getEntryText() + " = ", ad.getQuantity1(), new BigDecimal("19117"));
				}

			}
		}

	}

	/**
	 * Return Maintenance Report Data for labor and parts.
	 * 
	 * @param InqOperations
	 * @return void -- pointer of what was sent in should work as the return
	 * @throws Exception
	 */
	public static void getUtilitiesDollars(InqOperations inValues) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;

		if (inValues == null) {
			throwError.append("Inquiry Bean cannot be null.  ");
		} else {
			if (inValues.getEnvironment().equals("")) {
				throwError.append("Environment cannot be empty.  ");
			}
			if (inValues.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be empty.  ");
			}
			if (inValues.getFiscalYear().equals("")) {
				throwError.append("Fiscal Year cannot be empty.  ");
			}
			if (inValues.getFiscalPeriodStart().equals("")) {
				throwError.append("FiscalPeriodStart cannot be empty.  ");
			}
			if (inValues.getFiscalPeriodEnd().equals("")) {
				throwError.append("FiscalPeriodEnd cannot be empty.  ");
			}
			if (inValues.getFiscalWeekStart().equals("")) {
				throwError.append("FiscalWeekStart cannot be empty.  ");
			}
			if (inValues.getFiscalWeekEnd().equals("")) {
				throwError.append("FiscalWeekEnd cannot be empty.  ");
			}
		}

		if (throwError.toString().trim().equals("")) {

			try {
				// get a connection
				conn = ServiceConnection.getConnectionStack11();

				getUtilitiesDollars(inValues, conn);

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
			throwError.append("ServiceOperationsReporting.");
			throwError.append("getUtilitiesDollars(InqOperations). ");
			throw new Exception(throwError.toString());
		}

	}

	/**
	 * Return Maintenance Report Data for labor and parts.
	 * 
	 * @param InqOperations
	 * @return void -- pointer of what was sent in should work as the return
	 * @throws Exception
	 */
	private static void getUtilitiesDollars(InqOperations inqOperations, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try // catch all
		{

			String warehouse = inqOperations.getWarehouse();
			String year = inqOperations.getFiscalYear();
			LinkedHashMap<String, ManufacturingFinance> utilities = inqOperations.getBean().getUtilities();
			
			
			
			String sqlString = BuildSQL.getUtilitiesSpending(inqOperations);
			pstmt = conn.prepareStatement(sqlString);
			rs = pstmt.executeQuery();

			LoadFields.utilitiesSpending(rs, inqOperations);
			
			//If nothing was loaded from the spending, run, default in values
			if (utilities.isEmpty()) {
				utilities.put("Fuel", new ManufacturingFinance());
				utilities.put("Power", new ManufacturingFinance());
				utilities.put("Water", new ManufacturingFinance());
			}
			
			LinkedHashMap<String, ManufacturingFinance> earnings = new LinkedHashMap<String, ManufacturingFinance>();
			ServiceOperationsReportingManufacturing.getEarningsFinancial(inqOperations, earnings, ReportingType.UTIL);
			
			
			for (String key : utilities.keySet()) {
				ManufacturingFinance m = utilities.get(key);
				for (String earnKey : earnings.keySet()) {
					String rateKey = year + ":" + warehouse + ":" + key + ":" + earnKey;
					Hashtable<String, BigDecimal> _ht = utilitiesRates;
					BigDecimal rate = utilitiesRates.get(rateKey);
					
					if (rate == null) {
						rate = BigDecimal.ZERO;
					}
					                   
					
					ManufacturingFinance earn = earnings.get(earnKey);
					BigDecimal ytdEarn = earn.getYtdEarnings();
					ytdEarn = ytdEarn.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
					
					m.setWtdEarnings(m.getWtdEarnings().add(ytdEarn));
				}
				calculateVariances(m);
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

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReporting.");
			throwError.append("getUtilitiesDollars(");
			throwError.append("InqOperations, conn). ");
			throw new Exception(throwError.toString());
		}
	}

	/**
	 * Return rate for specific fiscal year and warehouse number.
	 * 
	 * @param String
	 *            fiscal year
	 * @param String
	 *            warehouse number
	 * @return BigDecimal rate
	 * @throws Exception
	 */
	public static BigDecimal getBenefitsRate(InqOperations inqOperations) throws Exception {
		StringBuffer throwError = new StringBuffer();
		BigDecimal rate = null;
		String previousFiscalYear = null;

		try {// catch all

			// incoming validation
			try {
				int x = Integer.parseInt(inqOperations.getFiscalYear());

				if (x < 2000 || x > 3000)
					throwError.append("Incoming fiscal year is out of range. ");

				previousFiscalYear = "" + (x - 1);
			} catch (Exception e) {
				throwError.append("Problem with incoming fiscal year (" + inqOperations.getFiscalYear() + "). " + e);
			}

			try {
				int x = Integer.parseInt(inqOperations.getWarehouse());

				if (x < 001 || x > 999) {
					throwError.append("Incoming warehouse number is out of range. ");
				}
			} catch (Exception e) {
				throwError
						.append("Problem with incoming warehouse number (" + inqOperations.getWarehouse() + "). " + e);
			}

			if (benefitsRates == null && throwError.toString().equals("")) {
				benefitsRates = new Hashtable<String, BigDecimal>();
				getBenefitsRate(inqOperations, previousFiscalYear);
			}

			String key = inqOperations.getFiscalYear().trim() + "-" + inqOperations.getWarehouse().trim();
			rate = (BigDecimal) benefitsRates.get(key);

			if (rate == null) {
				//attempt to load with the current fiscal year for request
				getBenefitsRate(inqOperations, inqOperations.getFiscalYear());
				rate = (BigDecimal) benefitsRates.get(key);
			}
			
			// if still zero, just return zero
			if (rate == null) {
				rate = BigDecimal.ZERO;
			}
			

		} catch (Exception e) {
			throwError.append("Error on catch all. " + e);
		}

		finally {

		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingFinancials.");
			throwError.append("getMaintenanceDollars(InqOperations). ");
			throw new Exception(throwError.toString());
		}

		return rate;

	}

	/**
	 * Return Hashtable of Plant rates.
	 * 
	 * @param InqOperations
	 * @param String
	 *            InqOperations FiscalYear -1
	 * @return Hashtable
	 * @throws Exception
	 */
	private static void getBenefitsRate(InqOperations inqOperations, String previousFiscalYear) throws Exception {


		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement getThem = null;

		try { // catch all

			conn = ServiceConnection.getConnectionStack11();
			
			// get a connection
			try {

				// load hash table with current fiscal year rates

				String sqlString = BuildSQL.getBenefitsRate(inqOperations, inqOperations.getFiscalYear());
				getThem = conn.prepareStatement(sqlString);
				rs = getThem.executeQuery();

				LoadFields.benefitsRate(rs, inqOperations.getFiscalYear());
				
			} catch (Exception e) {
				throwError.append("Error occured Building or Executing a sql statement. " + e);
			}


			// load hash table with previous fiscal year rates
			if (throwError.toString().equals("")) {

				try {

				String sqlString = BuildSQL.getBenefitsRate(inqOperations, previousFiscalYear);
				getThem = conn.prepareStatement(sqlString);
				rs = getThem.executeQuery();
		
					LoadFields.benefitsRate(rs,  previousFiscalYear);
				} catch (Exception e) {
					throwError.append(" Error occurred loading rates hash table. " + e);
				}


			}

		} catch (Exception e) {
			throwError.append("Error on catch all. " + e);
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

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingFinancials.");
			throwError.append("getRates(InqOperations, " + previousFiscalYear + ".) .");
			throw new Exception(throwError.toString());
		}


	}

	/**
	 * Return journal data for plants.
	 * 
	 * @param InqOperations
	 * @return void -- pointer of what was sent in should work as the return
	 * @throws Exception
	 */
	public static void getJournalEntries(InqOperations inValues) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;

		if (inValues == null) {
			throwError.append("Inquiry Bean cannot be null.  ");
		} else {
			if (inValues.getEnvironment().equals("")) {
				throwError.append("Environment cannot be empty.  ");
			}
			if (inValues.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be empty.  ");
			}
			if (inValues.getFiscalYear().equals("")) {
				throwError.append("Fiscal Year cannot be empty.  ");
			}
			if (inValues.getFiscalWeekStart().equals("")) {
				throwError.append("FiscalWeekStart cannot be empty.  ");
			}
		}

		if (throwError.toString().trim().equals("")) {

			try {
				// get a connection
				conn = ServiceConnection.getConnectionStack11();

				getJournalEntries(inValues, conn);

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
			throwError.append("ServiceOperationsReportingFinancials.");
			throwError.append("getPlantJournals(InqOperations). ");
			throw new Exception(throwError.toString());
		}

	}

	/**
	 * Return journal data for plants.
	 * 
	 * @param InqOperations
	 * @return void -- pointer of what was sent in should work as the return
	 * @throws Exception
	 */
	private static void getJournalEntries(InqOperations inValues, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement getThem = null;
		BeanOperationsReporting bean = inValues.getBean();

		try // catch all
		{
			try {
				String sqlString = BuildSQL.getJournalEntries(inValues);
				getThem = conn.prepareStatement(sqlString);
				rs = getThem.executeQuery();
			} catch (Exception e) {
				throwError.append("Error occured Building or Executing a sql statement. " + e);
			}

			if (throwError.toString().equals("")) {
				try {
					LoadFields.journalEntries(rs, inValues);

				} catch (Exception e) {
					throwError.append(" Error occurred loading data. " + e);
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
			if (getThem != null) {
				try {
					getThem.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingFinancials.");
			throwError.append("getPlantJournals(");
			throwError.append("InqOperations, conn). ");
			throw new Exception(throwError.toString());
		}
	}

	/**
	 * Return inventory adjustments for a plant.
	 * 
	 * @param InqOperations
	 * @return void -- pointer of what was sent in should work as the return
	 * @throws Exception
	 */
	public static void getInventoryAdjustments(InqOperations inValues) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;

		if (inValues == null) {
			throwError.append("Inquiry Bean cannot be null.  ");
		} else {
			if (inValues.getEnvironment().equals("")) {
				throwError.append("Environment cannot be empty.  ");
			}
			if (inValues.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be empty.  ");
			}
			if (inValues.getFiscalYear().equals("")) {
				throwError.append("Fiscal Year cannot be empty.  ");
			}
			if (inValues.getFiscalWeekStart().equals("")) {
				throwError.append("FiscalWeekStart cannot be empty.  ");
			}
		}

		if (throwError.toString().trim().equals("")) {

			try {
				// get a connection
				conn = ServiceConnection.getConnectionStack11();

				getInventoryAdjustments(inValues, conn);
				getInventoryAdjustmentsEarnings(inValues);
				

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
			throwError.append("ServiceOperationsReportingFinancials.");
			throwError.append("getInvAdjByPlants(InqOperations). ");
			throw new Exception(throwError.toString());
		}

	}

	/**
	 * Return inventory adjustments for a plant.
	 * 
	 * @param InqOperations
	 * @return void -- pointer of what was sent in should work as the return
	 * @throws Exception
	 */
	private static void getInventoryAdjustments(InqOperations inValues, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement getThem = null;
		BeanOperationsReporting bean = inValues.getBean();

		try // catch all
		{
			try {
				String sqlString = BuildSQL.getInventoryAdjustments(inValues);
				getThem = conn.prepareStatement(sqlString);
				rs = getThem.executeQuery();
			} catch (Exception e) {
				throwError.append("Error occured Building or Executing a sql statement. " + e);
			}

			if (throwError.toString().equals("")) {
				try {
					LoadFields.inventoryAdjustments(rs, inValues);

				} catch (Exception e) {
					throwError.append(" Error occurred loading data. " + e);
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
			if (getThem != null) {
				try {
					getThem.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingFinancials.");
			throwError.append("getInvAdjByPlant(");
			throwError.append("InqOperations, conn). ");
			throw new Exception(throwError.toString());
		}
	}
	
	
	/**
	 * Return inventory adjustments earnings (B12) for a plant.
	 * 
	 * @param InqOperations
	 * @return void -- pointer of what was sent in should work as the return
	 * @throws Exception
	 */
	private static void getInventoryAdjustmentsEarnings(InqOperations inValues) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement getThem = null;
		BeanOperationsReporting bean = inValues.getBean();

		try // catch all
		{
			
	
			ServiceOperationsReportingManufacturing.getEarningsFinancial(
					inValues, inValues.getBean().getInventoryAdjustmentsEarnings(), ReportingType.INVTADJ);

			

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
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingFinancials.");
			throwError.append("getInvAdjByPlant(");
			throwError.append("InqOperations, conn). ");
			throw new Exception(throwError.toString());
		}
	}
	

	/**
	 * Return Lab and Quality Financial Report Data for labor and parts.
	 * 
	 * @param InqOperations
	 * @return void -- pointer of what was sent in should work as the return
	 * @throws Exception
	 */
	public static void getQualityDollars(InqOperations inqOperations) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;

		if (inqOperations == null) {
			throwError.append("Inquiry Bean cannot be null.  ");
		} else {
			if (inqOperations.getEnvironment().equals("")) {
				throwError.append("Environment cannot be empty.  ");
			}
			if (inqOperations.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be empty.  ");
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

			try {
				// get a connection
				conn = ServiceConnection.getConnectionStack11();

				LinkedHashMap<String, ManufacturingFinance> lhm = inqOperations.getBean().getQuality();
				
				getSpending(inqOperations, ReportingType.QA, lhm, conn);
				ServiceOperationsReportingPayroll.getQualityLabor(inqOperations, lhm);
				ServiceOperationsReportingManufacturing.getEarningsFinancial(inqOperations, lhm, ReportingType.QA);
				
				loadBenefits(inqOperations, lhm);
				
				//calculate the variances
				for (String key : lhm.keySet()) {
					ManufacturingFinance mf = lhm.get(key);
					calculateVariances(mf);
				}
				
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
			throwError.append("ServiceOperationsReporting.");
			throwError.append("getQualityDollars(InqOperations). ");
			throw new Exception(throwError.toString());
		}

	}

	/**
	 * Return Warehousing Financial Report Data for labor and parts.
	 * 
	 * @param InqOperations
	 * @return void -- pointer of what was sent in should work as the return
	 * @throws Exception
	 */
	public static void getWarehousingDollars(InqOperations inqOperations) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;

		if (inqOperations == null) {
			throwError.append("Inquiry Bean cannot be null.  ");
		} else {
			if (inqOperations.getEnvironment().equals("")) {
				throwError.append("Environment cannot be empty.  ");
			}
			if (inqOperations.getWarehouse().equals("")) {
				throwError.append("Warehouse cannot be empty.  ");
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

			try {

				conn = ServiceConnection.getConnectionStack11();
				SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
				LinkedHashMap<String, ManufacturingFinance> lhm = inqOperations.getBean().getWarehousing();


				getSpending(inqOperations, ReportingType.WHSE, lhm, conn);
				ServiceOperationsReportingPayroll.getWarehousingLabor(inqOperations, lhm);
				ServiceOperationsReportingManufacturing.getEarningsFinancial(inqOperations, lhm, ReportingType.WHSE);

				loadBenefits(inqOperations, lhm);
				
				//calculate the variances
				for (String key : lhm.keySet()) {
					ManufacturingFinance mf = lhm.get(key);
					calculateVariances(mf);
				}

				
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
			throwError.append("ServiceOperationsReporting.");
			throwError.append("getWarehousingDollars(InqOperations). ");
			throw new Exception(throwError.toString());
		}

	}
	
	private static void loadUtilitesRates(int fiscalYear) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			String sql = BuildSQL.getUtilitiesRates(fiscalYear);
			conn = ServiceConnection.getConnectionStack11();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				LoadFields.utilitiesRates(rs, fiscalYear);
			}
			
		} catch (Exception e) { 
			
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
			if (conn != null) {
				try {
					ServiceConnection.returnConnectionStack11(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		
		
	}

}
