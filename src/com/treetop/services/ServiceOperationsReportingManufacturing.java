package com.treetop.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.treetop.businessobjects.ManufacturingFinance;
import com.treetop.businessobjects.ManufacturingOrderDetail;
import com.treetop.businessobjects.ManufacturingOrderDetail.YieldType;
import com.treetop.controller.operations.InqOperations;
import com.treetop.services.ServiceOperationsReporting.ReportingType;
import com.treetop.utilities.GeneralUtility;

public class ServiceOperationsReportingManufacturing {

	private static class BuildSQL {

		/**
		 * Returned Fields {"FACI","WHLO","MFNO","ITNO"}
		 * @param inqOperations
		 * @return
		 * @throws Exception
		 */
		private static String listMOs(InqOperations inqOperations) throws Exception {
			StringBuffer sql = new StringBuffer();
			
			String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
			
			String warehouse = inqOperations.getWarehouse();
			String startDate = inqOperations.getStartDate();
			String endDate   = inqOperations.getEndDate();
			
			sql.append(" SELECT NGFACI AS FACI, NGWHLO AS WHLO, NGRIDN AS MFNO, NGPRNO AS ITNO \r");
			sql.append(" FROM " + library + "CPOHED \r");
			sql.append(" WHERE NGCONO=100 AND NGDIVI='100' \r");
			sql.append(" AND NGWHLO='" + warehouse + "' \r");
			sql.append(" AND NGFIDT>=" + startDate + " \r");
			sql.append(" AND NGFIDT<=" + endDate + " \r");
			
			return sql.toString();
		}
		
		
		/**
		 * Returned Fields {"FACI","WHLO","MFNO","ITNO"}
		 * @param inqOperations
		 * @return
		 * @throws Exception
		 */
		private static String traceMOs(InqOperations inqOperations) throws Exception {
			StringBuffer sql = new StringBuffer();
			
			String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
			
			String warehouse = inqOperations.getWarehouse();
			String startDate = inqOperations.getStartDate();
			String endDate   = inqOperations.getEndDate();
			
			
			// Link tank MOs derived from finished goods (tagged with FG item number)
			sql.append(" SELECT VHFACI AS FACI, VHWHLO AS WHLO, LMRORN AS MFNO, VHPRNO AS ITNO \r");
			
			sql.append(" FROM \r");
			sql.append(" " + library + "CPOHED \r");
			sql.append(" INNER JOIN " + library + "MILOMA ON \r");
			sql.append("       NGCONO=LMCONO AND NGPRNO=LMITNO AND NGRIDN=LMRORN \r");
			sql.append(" INNER JOIN " + library + "MITTRA ON \r");
			sql.append("       NGCONO=MTCONO AND NGWHLO=MTWHLO AND LMITNO=MTITNO \r");
			sql.append("       AND LMBANO=MTBANO AND MTTTYP='11' \r");
			sql.append(" INNER JOIN " + library + "MWOHED ON \r");
			sql.append("       NGCONO=VHCONO AND NGWHLO=VHWHLO \r");
			sql.append("       AND CAST(MTRIDN AS NUMERIC (10,0))=VHMFNO \r");
			sql.append(" INNER JOIN " + library + "MITMAS AS MT ON \r");
			sql.append("       NGCONO=MT.MMCONO AND NGPRNO=MT.MMITNO AND MT.MMITCL='775' \r");
			sql.append(" INNER JOIN " + library + "MITMAS AS PR ON \r");
			sql.append("       NGCONO=PR.MMCONO AND VHPRNO=PR.MMITNO AND PR.MMITCL<>'775' \r");
			sql.append(" WHERE NGCONO=100 \r");
			sql.append(" AND NGWHLO='" + warehouse + "' \r");
			sql.append(" AND NGFIDT>=" + startDate + " \r");
			sql.append(" AND NGFIDT<=" + endDate + " \r");

			sql.append(" GROUP BY VHFACI, VHWHLO, LMRORN, VHPRNO \r");
			
			sql.append(" UNION ALL \r");
			
			
			//Finished good MOs
			sql.append(" SELECT NGFACI AS FACI, NGWHLO AS WHLO, NGRIDN AS MFNO, NGPRNO AS ITNO \r");
			sql.append(" FROM \r");
			sql.append(" " + library + "CPOHED \r");
			sql.append(" INNER JOIN " + library + "MITMAS ON \r");
			sql.append("       NGCONO=MMCONO AND NGPRNO=MMITNO AND MMITCL<>'775' \r");
			sql.append(" WHERE NGCONO=100 \r");
			sql.append(" AND NGWHLO='" + warehouse + "' \r");
			sql.append(" AND NGFIDT>=" + startDate + " \r");
			sql.append(" AND NGFIDT<=" + endDate + " \r");			
			

			return sql.toString();
		}
		
		

		private static String groupingSql(InqOperations inqOperations, ReportingType type, boolean production) throws Exception {
			StringBuffer sql = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			
			try {
				
				// Determine Library
				String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";
				
				String warehouse = inqOperations.getWarehouse();
				

				
				String prodOrUsage = "1";
				if (production) {
					prodOrUsage = "2";
				}
				sql.append(" LEFT OUTER JOIN " + library + "MITMAS ON \r");
				sql.append("      MMCONO=100 AND MMITNO=ITNO \r");
				
				sql.append(" LEFT OUTER JOIN " + library + "MITPOP AS PRCD ON \r");
				sql.append("      PRCD.MPCONO=100 AND PRCD.MPALWT=3 AND PRCD.MPALWQ='PRCD' AND PRCD.MPITNO=MMITNO \r");
				sql.append(" 	  AND PRCD.MPE0PA='" + warehouse + "' \r");
				
				sql.append(" LEFT OUTER JOIN " + library + "MITPOP AS NFC ON \r");
				sql.append("      NFC.MPCONO=100 AND NFC.MPALWT=3 AND NFC.MPALWQ='NFC' AND NFC.MPITNO=MMITNO \r");
				
				sql.append(" INNER JOIN dbtst.dcpall AS GRP ON \r");
				sql.append("       GRP.DCPK00='OPSRPT' \r");
				sql.append("       AND GRP.DCPK01='" + type + "' \r");
				sql.append("       AND GRP.DCPK02='" + warehouse + "' \r");
				sql.append("       AND GRP.DCPK03='MFG' \r");
				sql.append("       AND (GRP.DCPN01=0 OR GRP.DCPN01=" + prodOrUsage + ") \r");
				sql.append("       AND (GRP.DCPT80='' \r");
				sql.append("            OR (GRP.DCPT80='ITTY:PLGR:EVGR:POPN(NFC)' \r");
				sql.append("                AND (GRP.DCPA01=MMITTY OR GRP.DCPA01='') \r");
				sql.append("                AND (GRP.DCPA02=VOPLG1 OR GRP.DCPA02='') \r");
				sql.append("                AND (GRP.DCPA03=MMEVGR OR GRP.DCPA03='') \r");
				sql.append("                AND (GRP.DCPA04=IFNULL(NFC.MPPOPN,'N') OR GRP.DCPA04='') \r");
				sql.append("            ) \r");
				sql.append("            OR (GRP.DCPT80='ITGR:ITNO:EVGR:ATMO:ACRF' \r");
				sql.append("                AND (GRP.DCPA01=MMITGR OR GRP.DCPA01='') \r");
				sql.append("                AND (GRP.DCPA02=MMITNO OR GRP.DCPA02='') \r");
				sql.append("                AND (GRP.DCPA03=MMEVGR OR GRP.DCPA03='') \r");
				sql.append("                AND (GRP.DCPA04=MMATMO OR GRP.DCPA04='') \r");
				sql.append("                AND (GRP.DCPA05=MMACRF OR GRP.DCPA05='') \r");
				sql.append("            ) \r");
				sql.append("            OR (GRP.DCPT80='ITCL:PLGR:EVGR:GRP4:GRTS|GRTI' \r");
				sql.append("                AND (GRP.DCPA01=MMITCL OR GRP.DCPA01='') \r");
				sql.append("                AND (GRP.DCPA02=VOPLG1 OR GRP.DCPA02='') \r");
				sql.append("                AND (GRP.DCPA03=MMEVGR OR GRP.DCPA03='') \r");
				sql.append("                AND (GRP.DCPA04=MMGRP4 OR GRP.DCPA04='') \r");
				sql.append("                AND (GRP.DCPA05=MMGRTS OR GRP.DCPA05=MMGRTI OR GRP.DCPA05='') \r");
				sql.append("            ) \r");
				sql.append("       ) \r");
				
				
			} catch (Exception e) {
				throwError.append(" Error building getEarnings statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingManufacturing.");
				throwError.append("BuildSQL.getEarnings(InqOperations) ");
				throw new Exception(throwError.toString());
			}
			return sql.toString();
		}
		
		
		private static String groupingMOs(InqOperations inqOperations, ReportingType type, boolean production) throws Exception {
			StringBuffer sql = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			
			try {
				
				// Determine Library
				String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";
				
				String warehouse = inqOperations.getWarehouse();

				sql.append(" SELECT \r");
				sql.append(" GRP.DCPK04, GRP.DCPK05, FACI, WHLO, \r");
				sql.append(" ITNO,  MMITDS AS ITDS, MFNO, \r");
				sql.append(" IFNULL(PRCD.MPPOPN,ITNO) AS PRCD \r");
				
				
				sql.append(" FROM ( \r");
				if (!production && (warehouse.equals("280"))) {
					//for usage at Medford and Ross, use traceMOs()
					sql.append(traceMOs(inqOperations));
				} else {
					sql.append(listMOs(inqOperations));
				}
				
				sql.append(" ) AS LIST \r");

				sql.append(" LEFT OUTER JOIN " + library + "MWOOPE ON \r");
				sql.append("      VOCONO=100 AND VOFACI=FACI AND VOMFNO=MFNO AND VOCHID<>'RPEXCL' \r");
				sql.append(" LEFT EXCEPTION JOIN " + ttLibrary + "dcpall AS EXCL ON \r");
				sql.append("      EXCL.DCPK00='OPSRPT' AND EXCL.DCPK01='EXCL' \r");
				sql.append("      AND EXCL.DCPK02=WHLO AND EXCL.DCPA01=VOPLG1 \r");
				
				
				sql.append(groupingSql(inqOperations, type, production));
				
				
				
				
				sql.append(" GROUP BY \r");
				sql.append(" GRP.DCPK04, GRP.DCPK05, FACI, WHLO, ITNO,  MMITDS, MFNO, PRCD.MPPOPN \r");
				
			} catch (Exception e) {
				throwError.append(" Error building getEarnings statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingManufacturing.");
				throwError.append("BuildSQL.getEarnings(InqOperations) ");
				throw new Exception(throwError.toString());
			}
			return sql.toString();
		}
		
		private static String earnings(InqOperations inqOperations, ReportingType reportingType) throws Exception {
			StringBuffer sql = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			try {

				// Determine Library
				String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";

				String warehouse = inqOperations.getWarehouse();
				String startDate = inqOperations.getStartDate();

				//Maintenance require YTD values, use the beginning of the fiscal year as the start date
				if (reportingType == ReportingType.MAINT) {
					startDate = inqOperations.getFiscalYearStartDate();
				}
				
				String endDate = inqOperations.getEndDate();

	
				
				sql.append(" SELECT \r");
				sql.append(" CASE WHEN DCPK05<>'' THEN DCPK05 ELSE DCPK04 END AS DCPK04, \r");
				sql.append(" C.CPYEA4 AS YEAR, \r");
				sql.append(" C.CPPERI AS MONTH, \r");
				sql.append(" D.CPPERI AS WEEK, \r");
				sql.append(" SUM(VHMAQT*KECAMT) AS EARNINGS \r");


				sql.append(" FROM \r");
				sql.append(" " + library + "CPOHED \r");
				sql.append(" LEFT OUTER JOIN " + library + "MWOHED ON \r");
				sql.append("      NGCONO=VHCONO AND NGFACI=VHFACI AND \r");
				sql.append("      NGPRNO=VHPRNO AND NGRIDN=VHMFNO \r");
				sql.append(" INNER JOIN " + ttLibrary + "MCPEARN ON \r");
				sql.append("      NGCONO=KECONO AND NGFACI=KEFACI AND NGPRNO=KEITNO \r");
				sql.append("      AND KEFDAT<=NGFIDT AND KETDAT>=NGFIDT \r");

				sql.append(" INNER JOIN " + ttLibrary + "dcpall ON \r");
				sql.append("       DCPK00='OPSRPT' \r");
				sql.append("       AND DCPK01='" + reportingType + "' \r");
				sql.append("       AND DCPK02=NGWHLO \r");
				sql.append("       AND DCPK03='EARN' \r");
				sql.append("       AND (DCPA02=VHFACI OR DCPA02='') \r");
				sql.append("       AND (DCPA03=KECOCE OR DCPA03='') \r");
				sql.append("       AND (DCPA04=KEPLGR OR DCPA04='') \r");
				sql.append("       AND (DCPA05=KECOMP OR DCPA05='') \r");

				sql.append(" LEFT OUTER JOIN " + library + "CSYPER AS C ON C.CPCONO=100 AND C.CPDIVI='100' \r");
				sql.append(" AND C.CPPETP=1 AND NGFIDT>=C.CPFDAT AND NGFIDT<=C.CPTDAT \r");
				sql.append(" LEFT OUTER JOIN " + library + "CSYPER AS D ON D.CPCONO=100 AND D.CPDIVI='100' \r");
				sql.append(" AND D.CPPETP=2 AND NGFIDT>=D.CPFDAT AND NGFIDT<=D.CPTDAT \r");
				
				sql.append(" WHERE \r");
				sql.append(" NGCONO=100 AND NGWHLO='" + warehouse + "' \r");
				sql.append(" AND NGFIDT>=" + startDate + " \r");
				sql.append(" AND NGFIDT<=" + endDate + " \r");

				sql.append(" GROUP BY DCPK04, DCPK05, C.CPYEA4, C.CPPERI, D.CPPERI \r");



			} catch (Exception e) {
				throwError.append(" Error building getEarnings statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingManufacturing.");
				throwError.append("BuildSQL.getEarnings(InqOperations) ");
				throw new Exception(throwError.toString());
			}
			return sql.toString();
		}

		/**
		 * 
		 */
		private static String actualProduction(InqOperations inqOperations, ReportingType type) throws Exception {
			StringBuffer sql = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			try {

				String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";

				String warehouse = inqOperations.getWarehouse();
				String startDate = inqOperations.getStartDate();
				String endDate = inqOperations.getEndDate();

				sql.append(" SELECT \r");
				sql.append(" GRP.*, \r");
				sql.append(" VHMAQT, LB.MUCOFA AS LBCONV, FS.MUCOFA AS FSCONV, \r");
				sql.append(" IFNULL(VHMAQT/CAST((VOCTCD/VOPITI) AS NUMERIC(15,6))*VOPRNP,0) AS STDLBRHRS \r");
				
				
				sql.append(" FROM ( \r");
				sql.append(groupingMOs(inqOperations, type, true));
				sql.append(" ) AS GRP \r");
				
				
				sql.append(" LEFT OUTER JOIN " + library + "MWOHED ON \r");
				sql.append("      VHCONO=100 AND VHFACI=FACI AND VHMFNO=MFNO \r");
				sql.append(" LEFT OUTER JOIN " + library + "MWOOPE ON \r");
				sql.append("      VOCONO=100 AND VOFACI=FACI AND VOMFNO=MFNO AND VOCHID<>'RPEXCL' \r");
				sql.append(" LEFT EXCEPTION JOIN " + ttLibrary + "DCPALL AS EXCL ON \r");
				sql.append("      EXCL.DCPK00='OPSRPT' AND EXCL.DCPK01='EXCL' \r");
				sql.append("      AND EXCL.DCPK02=WHLO AND EXCL.DCPA01=VOPLG1 \r");
				sql.append(" LEFT OUTER JOIN " + library + "MITAUN AS LB ON \r");
				sql.append("      LB.MUCONO=100 AND LB.MUITNO=ITNO \r");
				sql.append("      AND LB.MUAUTP=1 AND LB.MUALUN='LB' \r");
				sql.append(" LEFT OUTER JOIN " + library + "MITAUN AS FS ON \r");
				sql.append("      FS.MUCONO=100 AND FS.MUITNO=ITNO \r");
				sql.append("      AND FS.MUAUTP=1 AND FS.MUALUN='FS' \r");
				
				sql.append(" ORDER BY  GRP.DCPK04, GRP.DCPK05, GRP.PRCD, GRP.ITNO \r");

				
				
				// *********************************************************************************

			} catch (Exception e) {
				throwError.append(e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingManufacturing.");
				throwError.append("BuildSQL.actualProduction(InqOperations, ProcessType:");
				throwError.append(type.toString() + ") ");
				throw new Exception(throwError.toString());
			}
			return sql.toString();
		}

		/**
		 * Returns summarized list of materials used
		 */
		private static String actualMaterials(InqOperations inqOperations, ReportingType type) throws Exception {
			StringBuffer sql = new StringBuffer();
			StringBuffer throwError = new StringBuffer();

			try {

				// Determine Library
				String library 		= GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
				String ttLibrary 	= GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";

				String warehouse 	= inqOperations.getWarehouse();
				String startDate 	= inqOperations.getStartDate();
				String endDate 		= inqOperations.getEndDate();
				
				//Ross and Medford drive the material usage from Finished Goods
				boolean useFG = warehouse.equals("240") || warehouse.equals("280");
				
				sql.append(" SELECT \r");

				sql.append("GRP.*, \r");
				sql.append(" PR.MMITTY AS PR_MMITTY, \r");
				sql.append(" PR.MMITCL AS PR_MMITCL, \r");
				sql.append(" RF.DCPK04 AS RFK04, \r");
				sql.append(" RF.DCPK05 AS RFK05, \r");
				
				sql.append(" NBTTYP, \r");
				sql.append(" MT.MMITTY, \r");
				sql.append(" MT.MMITCL, \r");
				sql.append(" MT.MMITGR, \r");
				sql.append(" MT.MMBYPR, \r");
				sql.append(" MT.MMITNO, \r");
				sql.append(" MT.MMITDS, \r");
				
				sql.append(" SUM(CASE WHEN VHORTY='CPK' THEN 0 ELSE NBTOSF END) AS NBTOSF, \r");
				sql.append(" SUM(CASE WHEN VHORTY='CPK' THEN 0 ELSE NBTOAF END) AS NBTOAF, \r");
				sql.append(" IFNULL(LB.MUCOFA,1) AS LBCONV, \r");
				sql.append(" IFNULL(FS.MUCOFA,1) AS FSCONV, \r");
				sql.append(" CASE WHEN SUM(NBSIQT)=0 AND (MFAC.M9VAMT=0 OR VHORTY='CPK' OR PFAC.M9VAMT=0) THEN SUM(VMREQT) ELSE SUM(NBSIQT) END AS NBSIQT, \r");
				sql.append(" CASE WHEN SUM(NBAIQT)=0 AND (MFAC.M9VAMT=0 OR VHORTY='CPK' OR PFAC.M9VAMT=0) THEN SUM(VMRPQT) ELSE SUM(NBAIQT) END AS NBAIQT, \r");
				sql.append(" MAX(VMFMT2) AS VMFMT2 \r");
				
				sql.append(" \r");
				
				
				sql.append(" FROM ( \r");
				
				sql.append(" SELECT NGFACI, NGWHLO, NGRIDN, NGPRNO, \r");
				sql.append(" NBMTNO AS MTNO, \r");
				sql.append(" CASE WHEN NBTTYP = 0 AND NBSIQT<0 THEN 13 ELSE NBTTYP END AS NBTTYP, \r");
				sql.append(" NBSIQT, NBAIQT, NBTOSF, NBTOAF, \r");
				sql.append(" 0 AS VMREQT, 0 AS VMRPQT, '' AS VMFMT2 \r");
				sql.append(" FROM " + library + "CPOHED \r");
				sql.append(" INNER JOIN " + library + "CPOMAT ON \r");
				sql.append(" NGCONO = NBCONO AND NGDIVI = NBDIVI \r");
				sql.append(" AND NBOCAT = 1 AND NGRIDN = NBRIDN \r");
				sql.append(" INNER JOIN " + library + "MITMAS ON \r");
				sql.append(" NGCONO=MMCONO AND NBMTNO=MMITNO AND MMITTY<>'975' \r");
				sql.append(" WHERE NGCONO=100 AND NGWHLO='" + warehouse + "' \r");
				sql.append(" AND NGFIDT>=" + startDate + " AND NGFIDT<=" + endDate + " \r");
				sql.append(" \r");
				sql.append(" UNION ALL \r");
				sql.append(" \r");
				
				sql.append(" SELECT NGFACI, NGWHLO, NGRIDN, NGPRNO, VMMTNO AS MTNO, \r");
				sql.append(" CASE WHEN VMBYPR=1 THEN 13 \r");
				sql.append("      WHEN NBTTYP IS NOT NULL THEN NBTTYP \r");
				sql.append("      ELSE 11 END AS NBTTYP, \r");
				
				sql.append(" 0 AS NBSIQT, 0 AS NBAIQT, 0 AS NBTOSF, 0 AS NBTOAF, \r");
				
				//plants enter 0.00001 or similar as consumed quantity for non-standard materials
				//system evaluates this to 1.0 for the reserved quantity
				//force back to zero if consumed qty rounds to zero at two decimals
				sql.append(" case when round(vmcnqt,2)=0 then 0 else VMREQT end as VMREQT, \r");
				
				sql.append(" VMRPQT, VMFMT2 \r");
				sql.append(" FROM " + library + "CPOHED \r");
				sql.append(" INNER JOIN " + library + "MWOMAT ON \r");
				sql.append(" NGCONO=VMCONO AND NGFACI=VMFACI \r");
				sql.append(" AND NGPRNO=VMPRNO AND NGRIDN=VMMFNO \r");
				
				sql.append(" LEFT OUTER JOIN " + library + "CPOMAT ON \r");
				sql.append(" NGCONO = NBCONO AND NGDIVI = NBDIVI \r");
				sql.append(" AND NBOCAT = 1 AND NGRIDN = NBRIDN AND NBMTNO=VMMTNO \r");
				
				sql.append(" INNER JOIN " + library + "MITMAS ON \r");
				sql.append(" NGCONO=MMCONO AND VMMTNO=MMITNO AND MMITTY<>'975' \r");
				
				sql.append(" WHERE NGCONO=100 AND NGWHLO='" + warehouse + "' \r");
				sql.append(" AND NGFIDT>=" + startDate + " AND NGFIDT<=" + endDate + " \r");
				sql.append(" \r");
				
				sql.append(" ) AS A \r");
				
				
				sql.append(" INNER JOIN ( \r");
				sql.append(groupingMOs(inqOperations, type, false));
				sql.append(" ) AS GRP ON NGRIDN=MFNO \r");
				
				sql.append(" LEFT OUTER JOIN " + library + "MWOHED ON \r");
				sql.append("      VHCONO=100 AND VHFACI=FACI AND VHMFNO=MFNO \r");
				sql.append(" LEFT OUTER JOIN " + library + "MITMAS AS MT ON \r");
				sql.append("      MT.MMCONO=100 AND MT.MMITNO=MTNO \r");
				sql.append(" LEFT OUTER JOIN " + library + "MITMAS AS PR ON \r");
				sql.append("      PR.MMCONO=100 AND PR.MMITNO=ITNO \r");
				sql.append(" LEFT OUTER JOIN " + library + "MITFAC AS PFAC ON \r");
				sql.append("      PFAC.M9CONO=100 AND PFAC.M9ITNO=ITNO AND PFAC.M9FACI=FACI \r");
				sql.append(" LEFT OUTER JOIN " + library + "MITFAC AS MFAC ON \r");
				sql.append("      MFAC.M9CONO=100 AND MFAC.M9ITNO=MTNO AND MFAC.M9FACI=FACI \r");
				sql.append(" \r");
				sql.append(" LEFT OUTER JOIN " + library + "MITAUN AS LB ON \r");
				sql.append("      LB.MUCONO=100 AND MTNO=LB.MUITNO AND LB.MUAUTP = 1 AND LB.MUALUN = 'LB' \r");
				sql.append(" \r");
				sql.append(" LEFT OUTER JOIN " + library + "MITAUN AS FS ON \r");
				sql.append("      FS.MUCONO=100 AND MTNO=FS.MUITNO AND FS.MUAUTP = 1 AND FS.MUALUN = 'FS' \r");
				sql.append(" \r");
				
				
				sql.append(" LEFT OUTER JOIN dbtst.dcpall AS RF ON \r");
				sql.append("       RF.DCPK00='OPSRPT' \r");
				sql.append("       AND RF.DCPK01='RAWFRUIT' \r");
				sql.append("       AND RF.DCPK02=NGWHLO \r");
				sql.append("       AND RF.DCPK03='MFG' \r");
				sql.append("       AND (RF.DCPT80='' \r");
				sql.append("            OR (RF.DCPT80='ITGR:ATMO:EVGR:DCPK04(GRP)' \r");
				sql.append("                AND (RF.DCPA01=MT.MMITGR OR RF.DCPA01='') \r");
				sql.append("                AND (RF.DCPA02=MT.MMATMO OR RF.DCPA02='') \r");
				sql.append("                AND (RF.DCPA03=MT.MMEVGR OR RF.DCPA03='') \r");
				sql.append("                AND (RF.DCPA04=GRP.DCPK04 OR RF.DCPA04='') \r");
				sql.append("            ) \r");
				sql.append("       ) \r");


				sql.append(" GROUP BY  \r");
				sql.append(" GRP.DCPK04, GRP.DCPK05, GRP.MFNO, GRP.ITNO, GRP.ITDS, GRP.PRCD, GRP.FACI, GRP.WHLO, \r");
				sql.append(" RF.DCPK04, RF.DCPK05, VHORTY, PR.MMITCL, PR.MMITTY, \r");
				sql.append(" NBTTYP, LB.MUCOFA, FS.MUCOFA, MT.MMITTY, MT.MMITCL, MT.MMITGR, \r");
				sql.append(" MT.MMBYPR, MT.MMITNO, PFAC.M9VAMT, MFAC.M9VAMT, MT.MMITDS \r");
				
				sql.append(" ORDER BY GRP.DCPK04, GRP.DCPK05, GRP.PRCD, GRP.ITNO \r");

				// *********************************************************************************
			} catch (Exception e) {
				throwError.append(" Error building sqlGetActualRawFruit statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingManufacturing.");
				throwError.append("BuildSQL.actualMaterials(InqOperations, ProcessType:");
				throwError.append(type + ") ");
				throw new Exception(throwError.toString());
			}
			return sql.toString();
		}

		/**
		 * 
		 */
		private static String getPackagingDataForecast(ReportingType type, InqOperations inqOperations)
				throws Exception {
			StringBuffer sql = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			try {

				// Determine Library
				String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";

				String warehouse = inqOperations.getWarehouse();
				String startPeriod = inqOperations.getFiscalPeriodStart();
				String endPeriod = inqOperations.getFiscalPeriodEnd();
				String endDate = inqOperations.getEndDate();
				
				sql.append(" SELECT \r");
				sql.append(" DCPK04, DCPK05, PERI, ITNO, MMITDS AS ITDS, IFNULL(PRCD.MPPOPN,ITNO) AS PRCD, QTY \r");
				
				
				sql.append(" FROM ( \r");
				
				sql.append(" SELECT \r");
				sql.append(" VUPERI AS PERI, VUFACI AS FACI, MBWHLO AS WHLO, \r");
				sql.append(" VUITNO AS ITNO, POPLGR AS VOPLG1, VUUQTY AS QTY \r");
				sql.append(" FROM " + ttLibrary + "ZJPVOL \r");
				sql.append(" INNER JOIN " + library + "MITBAL ON \r");
				sql.append("      MBCONO=100 AND MBFACI=VUFACI AND MBITNO=VUITNO AND MBPUIT=1 \r");
				sql.append(" LEFT OUTER JOIN " + library + "MPDOPE ON \r");
				sql.append("      POCONO=100 AND POFACI=VUFACI AND POPRNO=VUITNO \r");
				sql.append("      AND POFDAT<= " + endDate + " \r");
				sql.append("      AND POTDAT>= " + endDate + "     \r");
				sql.append(" LEFT EXCEPTION JOIN " + ttLibrary + "DCPALL AS EXCL ON \r");
				sql.append("      EXCL.DCPK00='OPSRPT' AND EXCL.DCPK01='EXCL' \r");
				sql.append("      AND EXCL.DCPK02=MBWHLO AND EXCL.DCPA01=POPLGR \r");
				sql.append(" WHERE \r");
				sql.append(" VUBVER='FND' AND VURTYP='P' \r");
				sql.append(" AND MBWHLO='" + warehouse + "' \r");
				sql.append(" AND VUYEA4=" + inqOperations.getFiscalYear() + " \r");
				sql.append(" AND VUPERI>=" + startPeriod + " \r");
				sql.append(" AND VUPERI<=" + endPeriod + " \r");
				sql.append(" GROUP BY VUPERI, VUFACI, VUITNO, MBWHLO, POPLGR, VUUQTY \r");
				
				sql.append(" ) AS A \r");
				
				sql.append(groupingSql(inqOperations, type, true));

				
				

				// *********************************************************************************
			} catch (Exception e) {
				throwError.append(" Error building sqlGetPackagingDataForecast statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingManufacturing.");
				throwError.append("BuildSQL.sqlGetPackagingDataForecast(ProcessType:");
				throwError.append(type.toString() + ", InqOperations)");
				throw new Exception(throwError.toString());
			}
			return sql.toString();
		}

		/**
		 * 
		 */
		private static String getPackagingDataPlanned(ReportingType type, InqOperations inqOperations) throws Exception {
			StringBuffer sql = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			try {

				// Determine Library
				String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";

				String warehouse = inqOperations.getWarehouse();
				String startDate = inqOperations.getStartDate();
				String endDate = inqOperations.getEndDate();
				
				sql.append(" SELECT \r");
				sql.append(" DCPK04, DCPK05, WHLO, ITNO, MMITDS AS ITDS, IFNULL(PRCD.MPPOPN,ITNO) AS PRCD, QTY \r");
				
				
				sql.append(" FROM ( \r");
				sql.append(" SELECT \r");
				sql.append(" MFFACI AS FACI, MFWHLO AS WHLO, \r");
				sql.append(" MFPRNO AS ITNO, POPLGR AS VOPLG1, \r");
				sql.append(" CAST(MFPPQT AS NUMERIC(11,0)) AS QTY \r");
				
				
				sql.append(" FROM " + ttLibrary + "INPFPLMO \r");
				sql.append(" LEFT OUTER JOIN " + library + "MPDOPE ON \r");
				sql.append("      POCONO=100 AND POFACI=MFFACI AND POPRNO=MFPRNO \r");
				sql.append("      AND POFDAT<= " + endDate + " \r");
				sql.append("      AND POTDAT>= " + endDate + " \r");
				sql.append(" LEFT EXCEPTION JOIN " + ttLibrary + "DCPALL AS EXCL ON    \r");
				sql.append("      EXCL.DCPK00='OPSRPT' AND EXCL.DCPK01='EXCL' \r");
				sql.append("      AND EXCL.DCPK02=MFWHLO AND EXCL.DCPA01=POPLGR \r");
				
				sql.append(" WHERE \r");
				sql.append(" MFCONO=100 \r");
				sql.append(" AND MFSTAT='FIN' \r");
				sql.append(" AND MFWHLO='" + warehouse + "' \r");
				sql.append(" AND MFPLDT>=" + startDate + " \r");
				sql.append(" AND MFPLDT<=" + endDate + " \r");
				
				sql.append(" GROUP BY MFFACI, MFWHLO, MFPRNO, POPLGR, MFPPQT \r");
				
				sql.append(" ) AS A \r");
				
				sql.append(groupingSql(inqOperations, type, true));
			
				// *********************************************************************************
			} catch (Exception e) {
				throwError.append(" Error building sqlGetPackagingDataPlanned statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingManufacturing.");
				throwError.append("BuildSQL.sqlGetPackagingDataPlanned(ProcessType:");
				throwError.append(type.toString() + ", InqOperations)");
				throw new Exception(throwError.toString());
			}
			return sql.toString();
		}

		/**
		 * @deprecated
		 */
		private static String getPackagingHfnc(InqOperations inqOperations) throws Exception {
			StringBuffer sql = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			try {

				// Determine Library
				String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";

				sql.append("SELECT ");
				// DCPALL - Descriptive Code as Filter
				// CPOHED - Order Costing Header
				// CPOCOA -
				// CPOOPE -Costing Operation Lines

				sql.append("DCPK04, ");
				sql.append("ifNull(sum(bvtrqt),0) as HFNC ");

				sql.append("FROM " + ttLibrary + "BAPVHIST ");

				sql.append("INNER JOIN " + library + "MITMAS ");
				sql.append("ON MMCONO = 100 AND BVITNO = MMITNO ");

				sql.append("INNER JOIN " + library + "MILOMA ");
				sql.append("ON MMCONO = LMCONO and MMITNO = LMITNO AND BVBANO = LMBANO ");

				sql.append("INNER JOIN " + library + "CPOOPE ");
				sql.append("ON LMCONO = NACONO AND LMRORN = NARIDN ");

				sql.append("INNER JOIN " + library + "CPOHED ");
				sql.append("ON NACONO = NGCONO AND NADIVI = NGDIVI ");
				sql.append("AND NAFACI = NGFACI AND NAOCAT = NGOCAT ");
				sql.append("AND NARIDN = NGRIDN ");

				sql.append("INNER JOIN " + ttLibrary + "dcpall ");
				sql.append("ON DCPK00 = 'OPSRPT' ");
				sql.append("AND DCPK01 = 'PKG' ");
				sql.append("AND DCPK02 = NGWHLO ");
				sql.append("AND DCPK03 = 'WKCTR' ");
				sql.append("and DCPA01 = NAPLGR ");

				sql.append("INNER JOIN " + library + "MITWHL ");
				sql.append("ON MMCONO = MWCONO AND BVWHLO = MWWHLO ");

				sql.append("WHERE ");
				sql.append("BVTTYP = 96 ");
				sql.append("AND BVYEA4 = " + inqOperations.getFiscalYear() + " ");
				sql.append("AND BVWEEK >= " + inqOperations.getFiscalWeekStart() + " ");
				sql.append("AND BVWEEK <= " + inqOperations.getFiscalWeekEnd() + " ");
				sql.append("AND BVHOLD = '1' ");
				sql.append("AND BVSTAS = '3' ");
				sql.append("AND NGWHLO = '" + inqOperations.getWarehouse() + "' ");

				sql.append("GROUP BY dcpk04 ");
				sql.append("ORDER BY dcpk04 ");

				// *********************************************************************************
			} catch (Exception e) {
				throwError.append(" Error building sqlGetPackagingHfnc statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingManufacturing.");
				throwError.append("BuildSQL.sqlGetPackagingHfnc(InqOperations)");
				throw new Exception(throwError.toString());
			}
			return sql.toString();
		}

		/**
		 * 
		 */
		private static String forecastRawFruit(InqOperations inqOperations) throws Exception {
			StringBuffer sql = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			try {

				// Determine Library
				String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";

				sql.append("SELECT ");
				// DCPALL - Descriptive Code as Filter
				// ZJPVOL - Work Order Header
				// CSYPER - M3 Calendar Information

				sql.append("DCPK05, DCPK02, VUYEA4, VUPERI, DCPK04, VUUQTY as FcstQty ");

				sql.append("FROM " + ttLibrary + "dcpall ");

				// Tie to the Start Week Value
				sql.append("INNER JOIN " + library + "CSYPER a on ");
				sql.append("a.CPCONO = '100' and a.CPDIVI = '100' and a.CPPETP = 2 ");
				sql.append("and a.CPYEA4 = " + inqOperations.getFiscalYear() + " ");
				sql.append("and a.CPPERI = " + inqOperations.getFiscalWeekStart() + " ");

				// Tie to the Ending Week Value
				sql.append("INNER JOIN " + library + "CSYPER b on ");
				sql.append("b.CPCONO = a.CPCONO and b.CPDIVI = a.CPDIVI and b.CPPETP = 2 ");
				sql.append("and b.CPYEA4 = a.CPYEA4 ");
				sql.append("and b.CPPERI = " + inqOperations.getFiscalWeekEnd() + " ");

				// Tie to Figure out the Month Value based on the Starting Week
				// Value
				sql.append("INNER JOIN " + library + "CSYPER c on ");
				sql.append("c.CPCONO = a.CPCONO and c.CPDIVI = a.CPDIVI and c.CPPETP = 1 ");
				sql.append("and c.CPYEA4 = a.CPYEA4 ");
				sql.append("and c.CPFDAT <= a.CPFDAT ");
				sql.append("and c.CPTDAT >= a.CPFDAT ");

				// Tie to Figure out the Month Value based on the Ending Week
				// Value
				sql.append("INNER JOIN " + library + "CSYPER d on ");
				sql.append("d.CPCONO = a.CPCONO and d.CPDIVI = a.CPDIVI and d.CPPETP = 1 ");
				sql.append("and d.CPYEA4 = a.CPYEA4 ");
				sql.append("and d.CPFDAT <= b.CPFDAT ");
				sql.append("and d.CPTDAT >= b.CPFDAT ");
				
				// 10/10/13 TWalton
				// Tie to the Warehouse, to be able to tie the volumes by Facility 
				sql.append("INNER JOIN " + library + "MITWHL ON ");
				sql.append("     MWCONO = a.CPCONO ");
				sql.append(" AND DCPK02 = MWWHLO ");
				
				// Tie to the Volumes based on Month
				sql.append("INNER JOIN " + ttLibrary + "ZJPVOL ON ");
				sql.append("     VUBVER  = 'FQ1-RF' ");	//forecast version
				sql.append(" AND VURTYP  = 'C' ");
				sql.append(" AND VUITNO  = DCPK05 ");	//Item code
				sql.append(" AND MWFACI = VUFACI "); // Facility
				sql.append(" AND VUYEA4  = a.CPYEA4 ");
				sql.append(" and VUPERI >= c.CPPERI ");
				sql.append(" AND VUPERI <= d.CPPERI ");

				sql.append("WHERE ");
				sql.append(" DCPK00 = 'OPSRPT' and dcpk01 = 'RAWFRUIT' and DCPK03 = 'MASTER' ");
				sql.append(" AND DCPK02 = '" + inqOperations.getWarehouse() + "' ");

				sql.append("GROUP BY DCPK05, DCPK02, VUYEA4, VUPERI, DCPK04, VUUQTY ");
				sql.append("ORDER BY DCPK02, DCPK04, VUYEA4 ");

				// *********************************************************************************
			} catch (Exception e) {
				throwError.append(" Error building forecastRawFruit statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReporting.");
				throwError.append("BuildSQL.forecastRawFruit(InqOperations)");
				throw new Exception(throwError.toString());
			}
			return sql.toString();
		}

		/**
		 * 
		 */
		private static String plannedRawFruit(InqOperations inqOperations) throws Exception {
			StringBuffer sql = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			try {

				// Determine Library
				String library = GeneralUtility.getLibrary(inqOperations.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(inqOperations.getEnvironment()) + ".";
				
				String warehouse = inqOperations.getWarehouse();
				String startDate = inqOperations.getStartDate();
				String endDate = inqOperations.getEndDate();

				sql.append(" SELECT ");
				// DCPALL - Descriptive Code as Filter
				// INPFPLMO - Planned Manufacturing Orders

				sql.append(" DCPK04, DCPK05, sum(cast(MFPPQT as numeric(11,0))) as plannedQty ");

				sql.append(" FROM " + ttLibrary + "dcpall ");


				sql.append(" INNER JOIN " + ttLibrary + "INPFPLMO ON ");
				sql.append(" MFCONO = 100 AND MFSTAT = 'FNR' ");
				sql.append(" AND MFWHLO = DCPK02 ");	//Warehouse
				sql.append(" AND MFPRNO = DCPK05  ");	//RF item (text description)
				sql.append(" AND MFDATE >= " + startDate + " AND MFDATE <= " + endDate + " ");


				sql.append("WHERE ");
				sql.append(" DCPK00 = 'OPSRPT' AND dcpk03 = 'MASTER' AND DCPK01 = 'RAWFRUIT' ");
				sql.append(" AND DCPK02 = '" + warehouse + "' ");

				sql.append("GROUP BY DCPK04, DCPK05 ");

				// *********************************************************************************
			} catch (Exception e) {
				throwError.append(" Error building BuildSQL.PlannedRawFruit statement. " + e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsReporting.");
				throwError.append("BuildSQL.plannedRawFruit(InqOperations)");
				throw new Exception(throwError.toString());
			}
			return sql.toString();
		}
	}

	private static class LoadFields {

		
		private static void earningsManufacturing(ResultSet rs, InqOperations inqOperations,
				LinkedHashMap<String, ManufacturingOrderDetail> lhm) throws Exception {

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

				int week = rs.getInt("WEEK");

				int weekStart = Integer.parseInt(inqOperations.getFiscalWeekStart());
				int weekEnd = Integer.parseInt(inqOperations.getFiscalWeekEnd());

				BigDecimal amount = rs.getBigDecimal("EARNINGS").setScale(2, BigDecimal.ROUND_HALF_UP);

				if (week <= weekEnd && week >= weekStart) {
					mo.setLaborEarned(mo.getLaborEarned().add(amount));
				}

			} catch (Exception e) {
				throwError.append(e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingManufacturing.");
				throwError.append("LoadFields.payroll(ResultSet). ");
				throw new Exception(throwError.toString());
			}

		}

		private static void earningsFinancial(ResultSet rs, InqOperations inqOperations,
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

				int month = rs.getInt("MONTH");
				int week = rs.getInt("WEEK");

				int periodStart = Integer.parseInt(inqOperations.getFiscalPeriodStart());
				int periodEnd = Integer.parseInt(inqOperations.getFiscalPeriodEnd());

				int weekStart = Integer.parseInt(inqOperations.getFiscalWeekStart());
				int weekEnd = Integer.parseInt(inqOperations.getFiscalWeekEnd());

				BigDecimal amount = rs.getBigDecimal("EARNINGS").setScale(2, BigDecimal.ROUND_HALF_UP);

				if (week == weekEnd) {
					m.setWtdEarnings(m.getWtdEarnings().add(amount));
				}

				if (month == periodEnd) {
					m.setMtdEarnings(m.getMtdEarnings().add(amount));
				}

				m.setYtdEarnings(m.getYtdEarnings().add(amount));

			} catch (Exception e) {
				throwError.append(e);
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingPayroll.");
				throwError.append("LoadFields.payroll(ResultSet). ");
				throw new Exception(throwError.toString());
			}

		}

		/**
		 * Loads material usage quantity and cost variance fields
		 */
		private static void actualMaterials(ResultSet rs, LinkedHashMap<String, ManufacturingOrderDetail> lhm,
				ReportingType type, InqOperations inqOperations) throws Exception {

			StringBuffer throwError = new StringBuffer();
			String warehouse = inqOperations.getWarehouse();
			ManufacturingOrderDetail m = null;
			String group = null;

			try {

				group = ServiceOperationsReporting.buildKey(rs);

				m = lhm.get(group);
				if (m == null) {
					m = new ManufacturingOrderDetail();
					lhm.put(group, m);
				}
				
				String order = rs.getString("MFNO").trim();
				if (!m.getOrderNumbers().contains(order)) {
					m.getOrderNumbers().add(order);
				}


				String itemType = rs.getString("MMITTY").trim();
				String itemGroup = rs.getString("MMITGR").trim();
				String productGroup = rs.getString("MMITCL").trim();
				String transactionType = rs.getString("NBTTYP").trim();
				
				boolean rawFruit = itemType.equals("200") || itemType.equals("205");
				boolean procFruit = itemType.equals("210");
				boolean byProduct = transactionType.equals("13") 
					|| ( transactionType.equals("0")
						 && rs.getBigDecimal("NBSIQT").compareTo(BigDecimal.ZERO) < 0);
				boolean slurry = itemGroup.equals("RF-AP SL") || itemGroup.equals("RF-PR SL");
				boolean lineTank = productGroup.equals("775");
				
				boolean supply = (itemType.compareTo("250") >= 0 && itemType.compareTo("290") <= 0 ) || itemType.equals("950");
				boolean packaging = (itemType.compareTo("250") >= 0 && itemType.compareTo("260") <= 0 );

				boolean finishedGood = (itemType.compareTo("100") >= 0 && itemType.compareTo("130") <= 0 );
				boolean concentrate = itemGroup.startsWith("CN");
				boolean puree = itemType.equals("140");
				boolean sugar = itemGroup.equals("S0904") || itemGroup.equals("S1014") || itemGroup.equals("S1904");
				
				//boolean blendIn = rs.getString("VMFMT2").trim().toUpperCase().equals("B");
				boolean blendIn = rs.getBigDecimal("NBSIQT").compareTo(BigDecimal.ZERO) == 0
										&& !supply;
				
				boolean washDownBlendIn = rs.getString("MMBYPR").equals("1") 
												&& itemType.equals("210")
												&& !byProduct;

				
				// *********************************************************************************
				// Load Cost Dollars
				// *********************************************************************************
				BigDecimal costActual = rs.getBigDecimal("NBTOAF").setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal costStandard = rs.getBigDecimal("NBTOSF").setScale(2, BigDecimal.ROUND_HALF_UP);

				// *********************************************************************************
				// Load Quantities
				// *********************************************************************************
				BigDecimal lbConversion = rs.getBigDecimal("LBCONV").setScale(4, BigDecimal.ROUND_HALF_UP);
				BigDecimal fsConversion = rs.getBigDecimal("FSCONV").setScale(4, BigDecimal.ROUND_HALF_UP);
				
				BigDecimal usageActual = rs.getBigDecimal("NBAIQT").setScale(4, BigDecimal.ROUND_HALF_UP);
				BigDecimal usageActualLbAtStd = usageActual.multiply(lbConversion).setScale(4, BigDecimal.ROUND_HALF_UP);
				BigDecimal usageActualFsAtStd = usageActual.multiply(fsConversion).setScale(4, BigDecimal.ROUND_HALF_UP);

				BigDecimal usageStandard = rs.getBigDecimal("NBSIQT").setScale(4, BigDecimal.ROUND_HALF_UP);
				BigDecimal usageStandardLbAtStd = usageStandard.multiply(lbConversion).setScale(4, BigDecimal.ROUND_HALF_UP);
				BigDecimal usageStandardFsAtStd = usageStandard.multiply(fsConversion).setScale(4, BigDecimal.ROUND_HALF_UP);

				BigDecimal tons = new BigDecimal("2000");
				
				
				if (type == ReportingType.PKG) {

					// for packaging cost variances, load everything in total cost variance
					if (warehouse.equals("209")
							//|| warehouse.equals("230")	//reported below
							|| warehouse.equals("251")
							|| warehouse.equals("280")
							|| warehouse.equals("290")
							|| warehouse.equals("469")
							|| warehouse.equals("490")) {
						m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
						m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));

					}
					
					//Wenatchee
					if (warehouse.equals("230")) {
						
						if (lineTank) {
							//Line tank
							m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
							m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));
							
							m.setCostVarianceActualQty(m.getCostVarianceActualQty().add(usageActual));
							m.setCostVarianceStandardQty(m.getCostVarianceStandardQty().add(usageStandard));
							
						} else if (finishedGood) {
							//rework
							m.setCostVarianceRework(m.getCostVarianceRework().add(costStandard.subtract(costActual)));
						} else {
							//packaging supplies
							m.setCostVarianceSupplies(m.getCostVarianceSupplies().add(costStandard.subtract(costActual)));
						}
					}
					
					//Ross
					if (warehouse.equals("240")) {
						
						if (lineTank) {
							//Line tank
							m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
							m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));
							
							m.setCostVarianceActualQty(m.getCostVarianceActualQty().add(usageActual));
							m.setCostVarianceStandardQty(m.getCostVarianceStandardQty().add(usageStandard));
							
						} else if (finishedGood) {
							//rework
							m.setCostVarianceRework(m.getCostVarianceRework().add(costStandard.subtract(costActual)));
						} else {
							//packaging supplies
							m.setCostVarianceSupplies(m.getCostVarianceSupplies().add(costStandard.subtract(costActual)));
						}
					}
					
					
				}
				
				// Selah yield usage
				if (warehouse.equals("209")) {
					if (type == ReportingType.PROC) {

						if (supply) {
							m.setCostVarianceSupplies(m.getCostVarianceSupplies().add(
									costStandard.subtract(costActual)));
						} else if (byProduct) {
							//push by product cost variances to usage variance
							BigDecimal costVariance = costStandard.subtract(costActual);
							m.setCostVarianceUsage(m.getCostVarianceUsage().add(costVariance));
							m.setCostVarianceTotal(m.getCostVarianceTotal().add(costVariance));
							
						} else {
							m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
							m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));

							m.setCostVarianceActualQty(m.getCostVarianceActualQty().add(usageActualLbAtStd));
							m.setCostVarianceStandardQty(m.getCostVarianceStandardQty().add(usageStandardLbAtStd));

						}

						if (group.startsWith("Pear Slurry")) {
							if (rawFruit && !byProduct && slurry) {
								
								BigDecimal usageActualYield = usageActualLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageActualYield(m.getUsageActualYield().add(usageActualYield));
								
								BigDecimal usageStandardYield = usageStandardLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));

							}
						}

						if (group.startsWith("Hard Pear")) {
							if (rawFruit && !byProduct && !slurry) {
								
								BigDecimal usageActualYield = usageActualLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageActualYield(m.getUsageActualYield().add(usageActualYield));
								
								BigDecimal usageStandardYield = usageStandardLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));

							}
						}

						if (group.startsWith("NFC")) {
							if (rawFruit && !byProduct && !slurry) {
								
								BigDecimal usageActualYield = usageActualLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageActualYield(m.getUsageActualYield().add(usageActualYield));
								
								BigDecimal usageStandardYield = usageStandardLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));

							}
						}

						if (group.startsWith("Type S")) {
							if (rawFruit && !byProduct && slurry) {
								
								BigDecimal usageActualYield = usageActualLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageActualYield(m.getUsageActualYield().add(usageActualYield));
								
								BigDecimal usageStandardYield = usageStandardLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));

							}
						}

						if (group.startsWith("Hot Apple")) {
							if (rawFruit && !byProduct) {
								
								BigDecimal usageActualYield = usageActualLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageActualYield(m.getUsageActualYield().add(usageActualYield));
								
								BigDecimal usageStandardYield = usageStandardLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));

							}
						}

						if (group.startsWith("Sauce")) {
							if (rawFruit && !byProduct) {

								BigDecimal usageYield = usageActualLbAtStd;
								m.setUsageActualYield(m.getUsageActualYield().add(usageYield));
								
								BigDecimal usageStandardYield = usageStandardLbAtStd;
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));

							}
						}
					}
					if (type == ReportingType.BLND) {

						m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
						m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));

						m.setCostVarianceActualQty(m.getCostVarianceActualQty().add(usageActualLbAtStd));
						m.setCostVarianceStandardQty(m.getCostVarianceStandardQty().add(usageStandardLbAtStd));

						if (group.equals("Juice")) {

							if (concentrate) {

								m.setUsageActual(m.getUsageActual().add(usageActual));
								m.setUsageStandard(m.getUsageStandard().add(usageStandard));

							}
						}
						if (group.equals("Sauce")) {
							m.setUsageActualYield(m.getUsageActualYield().add(usageActualLbAtStd));
						}
					}
				} // end Selah
				
				
				//Oxnard
				if (warehouse.equals("251")) {
					if (type == ReportingType.PROC) {
						if (group.startsWith("Fresh Fruit")) {
							
							if (supply && !sugar) {
								// packaging supplies
								m.setCostVarianceSupplies(m.getCostVarianceSupplies().add(costStandard.subtract(costActual)));
							} else {
								// everything else
								m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
								m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));
		
								m.setCostVarianceActualQty(m.getCostVarianceActualQty().add(usageActualLbAtStd));
								m.setCostVarianceStandardQty(m.getCostVarianceStandardQty().add(usageStandardLbAtStd));
	
								
								m.setUsageActualYield(m.getUsageActualYield().add(usageActualLbAtStd));
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardLbAtStd));
		
							}
						}
						
						if (group.startsWith("Line Tank")) {
							
							if (packaging) {
								// packaging supplies
								m.setCostVarianceSupplies(m.getCostVarianceSupplies().add(costStandard.subtract(costActual)));
							} else {
								// everything else
								m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
								m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));
		
								m.setCostVarianceActualQty(m.getCostVarianceActualQty().add(usageActualLbAtStd));
								m.setCostVarianceStandardQty(m.getCostVarianceStandardQty().add(usageStandardLbAtStd));
	
								
								m.setUsageActualYield(m.getUsageActualYield().add(usageActualLbAtStd));
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardLbAtStd));
		
							}
							
						}
						
						
					}
				}	//end Oxnard
				

				// Woodburn yield usage
				if (warehouse.equals("290")) {
					if (type == ReportingType.PROC) {

						//report all non-line tank finished good cost variances as "Supplies"
						if (!rs.getString("PR_MMITCL").trim().equals("775")  
								&& !group.startsWith("In Line Finisher")
								&& !group.startsWith("Fresh Fruit")) {
							
							m.setCostVarianceSupplies(m.getCostVarianceSupplies().add(costStandard.subtract(costActual)));
							
						} else {
							
							if (supply) {
								m.setCostVarianceSupplies(m.getCostVarianceSupplies().add(costStandard.subtract(costActual)));
							} else {
								m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
								m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));
		
								m.setCostVarianceActualQty(m.getCostVarianceActualQty().add(usageActualLbAtStd));
								m.setCostVarianceStandardQty(m.getCostVarianceStandardQty().add(usageStandardLbAtStd));
							}
							
							if (!packaging) {
								BigDecimal usageYield = usageActualLbAtStd;
								m.setUsageActualYield(m.getUsageActualYield().add(usageYield));
								BigDecimal usageYieldStandard = usageStandardLbAtStd;
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageYieldStandard));
							}
							
						}
						
					}
				} // end Woodburn
				
				// Medford yield usage
				if (warehouse.equals("280")) {
					if (type == ReportingType.PROC) {
						// Cost variances
						if (rawFruit || procFruit || puree) {
							// Fruit
							m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
							m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));
	
							m.setCostVarianceActualQty(m.getCostVarianceActualQty().add(usageActualLbAtStd));
							m.setCostVarianceStandardQty(m.getCostVarianceStandardQty().add(usageStandardLbAtStd));
						} else {
							// Everything else
							m.setCostVarianceSupplies(m.getCostVarianceSupplies().add(costStandard.subtract(costActual)));
						}
						
						//Usage for Yields
						
						if (rs.getString("PR_MMITCL").trim().equals("580")) {
							// Formulated products
							
							if (!packaging && !lineTank) {
								m.setUsageActualYield(m.getUsageActualYield().add(usageActualLbAtStd));
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardLbAtStd));
							}
							
							//BlendIn - for Formulated products
							if (washDownBlendIn) {
								m.setUsageActualBlendIn(m.getUsageActualBlendIn().add(usageActualLbAtStd));
								m.setUsageStandardBlendIn(m.getUsageStandardBlendIn().add(usageStandardLbAtStd));
							}
							

						} else {
							// Purees
							
							//Usage for yields
							if (rawFruit || procFruit || puree) {
								m.setUsageActualYield(m.getUsageActualYield().add(usageActualLbAtStd));
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardLbAtStd));
							}
							
							
							//BlendIn - for Formulated products
							//need to define additional product blend in for puree product
							if (washDownBlendIn) {
								m.setUsageActualBlendIn(m.getUsageActualBlendIn().add(usageActualLbAtStd));
								m.setUsageStandardBlendIn(m.getUsageStandardBlendIn().add(usageStandardLbAtStd));
							}
	
						}
						
						
						//Washdown
						if (byProduct) {
							m.setUsageActualWashdown(m.getUsageActualWashdown().add(usageActualLbAtStd));
							m.setUsageStandardWashdown(m.getUsageStandardWashdown().add(usageStandardLbAtStd));
						}
						
												
					}
					
				} // End Medford

				// Prosser
				if (warehouse.equals("469")) {
					if (type == ReportingType.PROC) {
						
						if (rawFruit && byProduct) {
							//push by product cost variances to usage variance
							BigDecimal costVariance = costStandard.subtract(costActual);
							m.setCostVarianceUsage(m.getCostVarianceUsage().add(costVariance));
							m.setCostVarianceTotal(m.getCostVarianceTotal().add(costVariance));
						
						} else if (!rawFruit) {
							m.setCostVarianceSupplies(m.getCostVarianceSupplies().add(costStandard.subtract(costActual)));
						} else {
							m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
							m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));
	
							m.setCostVarianceActualQty(m.getCostVarianceActualQty().add(usageActualLbAtStd));
							m.setCostVarianceStandardQty(m.getCostVarianceStandardQty().add(usageStandardLbAtStd));
						}
	
						if (group.startsWith("Hot Apple") || group.startsWith("Pear")) {
							if (rawFruit && !byProduct) {
								
								BigDecimal usageYield = usageActualLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageActualYield(m.getUsageActualYield().add(usageYield));
								
								BigDecimal usageStandardYield = usageStandardLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));
								
							}
						} else if (group.startsWith("NFC") || group.startsWith("Fresh Matters Tude")) {
							if (rawFruit && !byProduct && !slurry) {
								
								BigDecimal usageYield = usageActualLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageActualYield(m.getUsageActualYield().add(usageYield));
								
								BigDecimal usageStandardYield = usageStandardLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));
								
							}
						} else if (group.startsWith("Grape")) {
							if (rawFruit && !byProduct) {
								
								BigDecimal usageYield = usageActualLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageActualYield(m.getUsageActualYield().add(usageYield));
								
								BigDecimal usageStandardYield = usageStandardLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));
	
							}
							
						} else if (rs.getString("PR_MMITCL").trim().equals("750")) {
							//soft fruits (reported by item number)
							if (rawFruit || procFruit || concentrate) {
								m.setYieldType(YieldType.YIELD);
								BigDecimal usageActualYield = usageActualLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageActualYield(m.getUsageActualYield().add(usageActualYield));
								
								BigDecimal usageStandardYield = usageStandardLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));
	
							}
						} else if (rs.getString("PR_MMITTY").trim().equals("110")) {
							//soft fruits for NWN (reported by item number)
							if (rawFruit || procFruit || concentrate) {
								m.setYieldType(YieldType.RECOVERY);
								BigDecimal usageActualYield = usageActualFsAtStd;
								m.setUsageActualYield(m.getUsageActualYield().add(usageActualYield));
								
								BigDecimal usageStandardYield = usageStandardFsAtStd;
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));
	
							}
						}
					}

				} // end Prosser
				
				
				// Ross
				if (warehouse.equals("240")) {
					if (type == ReportingType.PROC) {
						
						if (group.startsWith("Custom Dryer")) {
							
							if (packaging) {
								m.setCostVarianceSupplies(m.getCostVarianceSupplies().add(costStandard.subtract(costActual)));
							} else {
								m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
								m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));
		
								m.setCostVarianceActualQty(m.getCostVarianceActualQty().add(usageActualLbAtStd));
								m.setCostVarianceStandardQty(m.getCostVarianceStandardQty().add(usageStandardLbAtStd));

								m.setUsageActualYield(m.getUsageActualYield().add(usageActualLbAtStd));
								m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardLbAtStd));
							}

							
						} else {
							//load cost variances
							if (supply) {
								m.setCostVarianceSupplies(m.getCostVarianceSupplies().add(costStandard.subtract(costActual)));
							} else if (slurry && byProduct) {
								//slurry by proudct
								m.setCostVarianceSlurry(m.getCostVarianceSlurry().add(costStandard.subtract(costActual)));
							} else {
								//only report usg/subs var for gross tons
								//or for rescreen, report finished goods
								m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
								m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));
		
								m.setCostVarianceActualQty(m.getCostVarianceActualQty().add(usageActualLbAtStd));
								m.setCostVarianceStandardQty(m.getCostVarianceStandardQty().add(usageStandardLbAtStd));
							}
							
							
							if (group.startsWith("Drum Dry")) {
								//load usage for yields
								if (!supply) {
									//Must be in item group RF-Apple
									//nets together gross pounds dumped and eliminators
									//include slurry
									BigDecimal usageYieldActual = usageActualLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
									m.setUsageActualYield(m.getUsageActualYield().add(usageYieldActual));
									
									BigDecimal usageStandardYield = usageStandardLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
									m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));
								}
							} else {
								//load usage for yields
								if (!supply && !slurry) {
									//Must be in item group RF-Apple
									//nets together gross pounds dumped and eliminators
			
									BigDecimal usageYieldActual = usageActualLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
									m.setUsageActualYield(m.getUsageActualYield().add(usageYieldActual));
									
									BigDecimal usageStandardYield = usageStandardLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
									m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));
								}
							}

							
							
							if (byProduct && slurry) {
								m.setUsageActualSlurry(m.getUsageActualSlurry().add(usageActualLbAtStd));
								m.setUsageStandardSlurry(m.getUsageStandardSlurry().add(usageStandardLbAtStd));
							}
							//elims
							if (byProduct && rawFruit && !slurry) {
								m.setUsageActualElims(m.getUsageActualElims().add(usageActualLbAtStd));
								m.setUsageStandardElims(m.getUsageStandardElims().add(usageStandardLbAtStd));
							}
							//gross
							if (!byProduct && rawFruit && !slurry) {
								m.setUsageActualGross(m.getUsageActualGross().add(usageActualLbAtStd));
								m.setUsageStandardGross(m.getUsageStandardGross().add(usageStandardLbAtStd));
							}
							
						}
					}
				}
				
				// Wenatchee
				if (warehouse.equals("230")) {
					if (type == ReportingType.PROC) {
						
						//load cost variances
						if (supply) {
							m.setCostVarianceSupplies(m.getCostVarianceSupplies().add(costStandard.subtract(costActual)));
						} else if (slurry && byProduct) {
							//slurry by proudct
							m.setCostVarianceSlurry(m.getCostVarianceSlurry().add(costStandard.subtract(costActual)));
						} else {
							//only report usg/subs var for gross tons
							//or for rescreen, report finished goods
							m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
							m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));
	
							m.setCostVarianceActualQty(m.getCostVarianceActualQty().add(usageActualLbAtStd));
							m.setCostVarianceStandardQty(m.getCostVarianceStandardQty().add(usageStandardLbAtStd));
						}
						
						
						
						//load usage for yields
						if (!supply && !slurry) {
							//Must be in item group RF-Apple
							//nets together gross pounds dumped and eliminators
	
							BigDecimal usageYieldActual = usageActualLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
							m.setUsageActualYield(m.getUsageActualYield().add(usageYieldActual));
							
							BigDecimal usageStandardYield = usageStandardLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP);
							m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardYield));
						}
						
						if (!group.startsWith("Rescreen") && !group.startsWith("Grind")) {
							if (byProduct && slurry) {
								m.setUsageActualSlurry(m.getUsageActualSlurry().add(usageActualLbAtStd));
								m.setUsageStandardSlurry(m.getUsageStandardSlurry().add(usageStandardLbAtStd));
							}
							//elims
							if (byProduct && rawFruit && !slurry) {
								m.setUsageActualElims(m.getUsageActualElims().add(usageActualLbAtStd));
								m.setUsageStandardElims(m.getUsageStandardElims().add(usageStandardLbAtStd));
							}
							//gross
							if (!byProduct && rawFruit && !slurry) {
								m.setUsageActualGross(m.getUsageActualGross().add(usageActualLbAtStd));
								m.setUsageStandardGross(m.getUsageStandardGross().add(usageStandardLbAtStd));
							}
						}
						
					}
				} // end Wenatchee
				
				//Fresh Slice
				if (warehouse.equals("490")) {
					if (type == ReportingType.PROC) {
						
						
						if (supply) {
							// packaging supplies
							m.setCostVarianceSupplies(m.getCostVarianceSupplies().add(costStandard.subtract(costActual)));
						} else {
							// everything else
							m.setCostVarianceActualCost(m.getCostVarianceActualCost().add(costActual));
							m.setCostVarianceStandardCost(m.getCostVarianceStandardCost().add(costStandard));
	
							m.setCostVarianceActualQty(m.getCostVarianceActualQty().add(usageActualLbAtStd));
							m.setCostVarianceStandardQty(m.getCostVarianceStandardQty().add(usageStandardLbAtStd));
							
							m.setUsageActualYield(m.getUsageActualYield().add(usageActualLbAtStd));
							m.setUsageStandardYield(m.getUsageStandardYield().add(usageStandardLbAtStd));
	
						}
						
						
						//elims
						if (byProduct && rawFruit) {
							m.setUsageActualElims(m.getUsageActualElims().add(usageActualLbAtStd));
							m.setUsageStandardElims(m.getUsageStandardElims().add(usageStandardLbAtStd));
						}
						//gross
						if (!byProduct && rawFruit) {
							m.setUsageActualGross(m.getUsageActualGross().add(usageActualLbAtStd));
							m.setUsageStandardGross(m.getUsageStandardGross().add(usageStandardLbAtStd));
						}
						
						
					}
				}	//end Fresh Slice
				
				
				
				

			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingManufacturing.");
				throwError.append("LoadFields.actualMaterials(ResultSet, LinkedHashMap, ");
				throwError.append("ProcessType:" + type.toString() + "InqOperations	). " + e);
				throw new Exception(throwError.toString());
			}

		}

		/**
		 * 
		 */
		private static void actualRawFruit(ResultSet rs, LinkedHashMap<String, ManufacturingOrderDetail> lhm,
				ReportingType type, InqOperations inqOperations) throws Exception {

			StringBuffer throwError = new StringBuffer();
			String warehouse = inqOperations.getWarehouse();

			String key = ServiceOperationsReporting.buildKey(rs, new String[] {"RFK04","RFK05"});

			//only use valid keys
			if (key.equals("Undefined:Undefined")) {
				return;
			}
			
			String mmitno = rs.getString("MMITNO").trim();
			String mmitgr = rs.getString("MMITGR").trim();
			String ttyp = rs.getString("NBTTYP").trim();
			
			if (mmitno.equals("75000099")	//sec slry apple
					|| mmitno.equals("75000145")) { //sec slry pear
			
				return;
			}

			try {

				BigDecimal tons = new BigDecimal(2000);

				ManufacturingOrderDetail m = lhm.get(key);
				if (m == null) {
					m = new ManufacturingOrderDetail();
					lhm.put(key, m);
				}

				if (m != null) {

					BigDecimal lbConversion = rs.getBigDecimal("LBCONV").setScale(4, BigDecimal.ROUND_HALF_UP);
					
					BigDecimal usageActual = rs.getBigDecimal("NBAIQT").setScale(4, BigDecimal.ROUND_HALF_UP);
					BigDecimal usageActualLbAtStd = usageActual.multiply(lbConversion).setScale(4, BigDecimal.ROUND_HALF_UP);

					BigDecimal usageStandard = rs.getBigDecimal("NBSIQT").setScale(4, BigDecimal.ROUND_HALF_UP);
					BigDecimal usageStandardLbAtStd = usageStandard.multiply(lbConversion).setScale(4, BigDecimal.ROUND_HALF_UP);

					// report in tons

					
					m.setUsageRawFruitActual(m.getUsageRawFruitActual().add(
							usageActualLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP)));
					m.setUsageRawFruitStandard(m.getUsageRawFruitStandard().add(
							usageStandardLbAtStd.divide(tons, 4, BigDecimal.ROUND_HALF_UP)));
				}

			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingManufacturing.");
				throwError.append("LoadFields.actualRawFruit(ResultSet, LinkedHashMap, ");
				throwError.append("ProcessType:" + type.toString() + ", InqOperations). " + e);
				throw new Exception(throwError.toString());
			}

		}

		/**
		 */
		private static void actualProduction(ResultSet rs, LinkedHashMap<String, ManufacturingOrderDetail> lhm,
				InqOperations inqOperations, ReportingType type) throws Exception {

			StringBuffer throwError = new StringBuffer();
			ManufacturingOrderDetail m = null;
			String key = "";

			try {

				
				key = ServiceOperationsReporting.buildKey(rs);

				
				m = lhm.get(key);
				if (m == null) {
					m = new ManufacturingOrderDetail();
					lhm.put(key, m);
				}
								

				BigDecimal production = rs.getBigDecimal("VHMAQT").setScale(4, BigDecimal.ROUND_HALF_UP);
				m.setProduction(m.getProduction().add(production));
								
				

				BigDecimal productionLbAtStd = BigDecimal.ZERO;
				BigDecimal lbConv = rs.getBigDecimal("LBCONV");
				if (lbConv != null) {
					productionLbAtStd = production.multiply(lbConv).setScale(4, BigDecimal.ROUND_HALF_UP);
					m.setProductionLbAtStd(m.getProductionLbAtStd().add(productionLbAtStd));
				} else {
					m.setProductionLbAtStd(m.getProductionLbAtStd().add(production));
				}
				
				
				
				
				BigDecimal productionFsAtStd = BigDecimal.ZERO;
				BigDecimal fsConv = rs.getBigDecimal("FSCONV");
				if (fsConv != null) {
					productionFsAtStd = production.multiply(fsConv).setScale(4, BigDecimal.ROUND_HALF_UP);
				}
				m.setProductionFsAtStd(m.getProductionFsAtStd().add(productionFsAtStd));

				
				BigDecimal laborHoursStandard = rs.getBigDecimal("STDLBRHRS");
				if (laborHoursStandard != null) {
					m.setLaborHoursStandard(m.getLaborHoursStandard().add(laborHoursStandard.setScale(2, BigDecimal.ROUND_HALF_UP)));
				}
				
				
				String order = rs.getString("MFNO").trim();
				if (!m.getOrderNumbers().contains(order)) {
					m.getOrderNumbers().add(order);
				}

				
			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingManufacturing.");
				throwError.append("LoadFields.actualProduction(ResultSet, LinkedHashMap, InqOperations, ");
				throwError.append("ProcessType:" + type.toString() + "). " + e);
				throw new Exception(throwError.toString());
			}
		}

		/**
		 */
		private static void packagingDataForecast(
				ResultSet rs, LinkedHashMap<String, 
				ManufacturingOrderDetail> lhm,
				boolean monthToDate)
				throws Exception {

			StringBuffer throwError = new StringBuffer();
			ManufacturingOrderDetail m = null;
			
			try {
				// Get the MO information from the LinkedHashMap
				String key = ServiceOperationsReporting.buildKey(rs);
				
				m = lhm.get(key);
				if (m == null) {
					m = new ManufacturingOrderDetail();
					lhm.put(key, m);
				}


				BigDecimal fcstQty = rs.getBigDecimal("QTY").setScale(0, BigDecimal.ROUND_HALF_UP);
				int period = 0;

				if (monthToDate) {
					
					// data is return from SQL as monthly, nothing to do
					
				} else {
					
					if (fcstQty.compareTo(BigDecimal.ZERO) != 0) {
						try {
	
							// distribute monthly forecast to weeks (4-4-5)
							// every third month, divide by 5, else divide by 4
							if (rs.getInt("PERI") % 3 == 0) {
								fcstQty = fcstQty.divide(new BigDecimal("5"), 0, BigDecimal.ROUND_HALF_UP);
							} else {
								fcstQty = fcstQty.divide(new BigDecimal("4"), 0, BigDecimal.ROUND_HALF_UP);
							}
	
						} catch (Exception e) {
							// Skip over if this piece has an error
						}
					}
					
				}

				m.setProductionForecast(m.getProductionForecast().add(fcstQty));

			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingManufacturing.");
				throwError.append("packagingDataForecast(ResultSet, LinkedHashMap). " + e);
				throw new Exception(throwError.toString());
			}
		}

		/**
		 */
		private static void packagingDataPlanned(ResultSet rs, LinkedHashMap<String, ManufacturingOrderDetail> lhm)
				throws Exception {

			StringBuffer throwError = new StringBuffer();
			ManufacturingOrderDetail m = null;
			
			try {
			
				// Get the MO information from the LinkedHashMap
				String key = ServiceOperationsReporting.buildKey(rs);
				
				m = lhm.get(key);
				if (m == null) {
					m = new ManufacturingOrderDetail();
					lhm.put(key, m);
				}


				BigDecimal plannedQuantity = rs.getBigDecimal("QTY").setScale(0, BigDecimal.ROUND_HALF_UP);
				m.setProductionPlanned(m.getProductionPlanned().add(plannedQuantity));


			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingManufacturing.");
				throwError.append("loadPackagingReportData(ResultSet, LinkedHashMap). " + e);
				throw new Exception(throwError.toString());
			}

		}

		/**
		 * @deprecated
		 */
		private static void packagingHfnc(ResultSet rs, LinkedHashMap<String, ManufacturingOrderDetail> lhm)
				throws Exception {

			StringBuffer throwError = new StringBuffer();

			try {
				// Get the MO information from the LinkedHashMap
				String code = rs.getString("DCPK04").trim();
				ManufacturingOrderDetail mo = (ManufacturingOrderDetail) lhm.get(code);

				mo.setHoldForNonConformance(rs.getBigDecimal("HFNC").setScale(0, BigDecimal.ROUND_HALF_UP));

			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingManufacturing.");
				throwError.append("loadPackagingHfncs(ResultSet, LinkedHashMap). " + e);
				throw new Exception(throwError.toString());
			}

		}

		/**
		 */
		private static void forecastRawFruit(ResultSet rs, 
				LinkedHashMap<String, ManufacturingOrderDetail> lhm, 
				boolean monthToDate)
				throws Exception {

			StringBuffer throwError = new StringBuffer();

			try {

				String key = ServiceOperationsReporting.buildKey(rs);

				ManufacturingOrderDetail m = lhm.get(key);
				if (m == null) {
					m = new ManufacturingOrderDetail();
					lhm.put(key, m);
				}

				BigDecimal fcstQty = new BigDecimal(rs.getString("FcstQty").trim());
				int period = 0;

				if (fcstQty.compareTo(new BigDecimal("0")) != 0) {
					
					if (monthToDate) {
						
						// data is return from SQL as monthly, nothing to do
						
					} else {
						// WEEKLY
						// use 4-4-5 method to take monthly volumes down to weekly volumes
						try {
							period = rs.getInt("VUPERI");
							int divideBy = 4;
							if (period % 3 == 0) {	//if the period is 3, 5, 9, or 12
								divideBy = 5;
							}
							
							fcstQty = fcstQty.divide((new BigDecimal(divideBy)), 2, BigDecimal.ROUND_HALF_UP);
						} catch (Exception e) {
							fcstQty = new BigDecimal("0");
						}
					
					}
					
				}

				m.setUsageRawFruitForecast(fcstQty);


			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReporting.");
				throwError.append("LoadFields.forecastRawFruit(ResultSet, LinkedHashMap). " + e);
				throw new Exception(throwError.toString());
			}
		}
		
		/**
		 */
		private static void plannedRawFruit(ResultSet rs, LinkedHashMap<String, ManufacturingOrderDetail> lhm)
				throws Exception {

			StringBuffer throwError = new StringBuffer();
			ManufacturingOrderDetail mo = null;
			String group = null;
			try {

				group = ServiceOperationsReporting.buildKey(rs);

				mo = lhm.get(group);
				if (mo == null) {
					mo = new ManufacturingOrderDetail();
					lhm.put(group, mo);
				}

				BigDecimal plannedQty = rs.getBigDecimal("PlannedQty").setScale(4, BigDecimal.ROUND_HALF_UP);

				mo.setUsageRawFruitPlanned(plannedQty);


			} catch (Exception e) {
				throwError.append("Error at com.treetop.services.ServiceOperationsReporting.");
				throwError.append("LoadFields.plannedRawFruit(ResultSet, LinkedHashMap). " + e);
				throw new Exception(throwError.toString());
			}
		}

	}

	private static void calculateYield(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm, ReportingType type) throws Exception {
		StringBuffer throwError = new StringBuffer();
		try {

			int i = 0;
			for (String group : lhm.keySet()) {
				ManufacturingOrderDetail m = lhm.get(group);

				// Selah
				if (inqOperations.getWarehouse().equals("209")) {

					if (type == ReportingType.PROC) {
						if (group.startsWith("Pear Slurry") 
								|| group.startsWith("Hard Pear") 
								|| group.startsWith("NFC")
								|| group.startsWith("Type S") 
								|| group.startsWith("Hot Apple")) {
							m.setYieldType(YieldType.YIELD);
							BigDecimal production = m.getProductionFsAtStd();
							BigDecimal usageActual = m.getUsageActualYield();
							BigDecimal usageStandard = m.getUsageStandardYield();

							if (usageActual.compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal yield = production.divide(usageActual, 4, BigDecimal.ROUND_HALF_UP);
								m.setYieldActual(yield);
							}
							
							if (usageStandard.compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal yield = production.divide(usageStandard, 4, BigDecimal.ROUND_HALF_UP);
								m.setYieldStandard(yield);
							}

						}

						if (group.startsWith("Sauce")) {
							m.setYieldType(YieldType.RECOVERY);
							BigDecimal production = m.getProduction();
							BigDecimal usageActual = m.getUsageActualYield();
							BigDecimal usageStandard = m.getUsageStandardYield();

							if (usageActual.compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal yield = production.divide(usageActual, 4, BigDecimal.ROUND_HALF_UP);
								m.setRecoveryActual(yield);
							}
							
							if (usageStandard.compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal yield = production.divide(usageStandard, 4, BigDecimal.ROUND_HALF_UP);
								m.setRecoveryStandard(yield);
							}
							
						}
					}

					if (type == ReportingType.BLND) {
						if (group.equals("Juice")) {
							m.setYieldType(YieldType.RECOVERY);
							BigDecimal standard = m.getUsageStandard();
							BigDecimal actual = m.getUsageActual();

							if (actual.compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal yield = standard.divide(actual, 4, BigDecimal.ROUND_HALF_UP);
								m.setRecoveryActual(yield);
							}
						}
						if (group.equals("Sauce")) {
							m.setYieldType(YieldType.RECOVERY);
							BigDecimal production = m.getProductionLbAtStd();
							BigDecimal usage = m.getUsageActualYield();

							if (usage.compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal yield = production.divide(usage, 4, BigDecimal.ROUND_HALF_UP);
								m.setRecoveryActual(yield);
							}
						}
					}
				} // end Selah
				
				
				
				// Oxnard
				if (inqOperations.getWarehouse().equals("251")) {

					if (type == ReportingType.PROC) {
						
						if (group.startsWith("Fresh Fruit")) {
						
							m.setYieldType(YieldType.RECOVERY);
							BigDecimal production = m.getProductionLbAtStd();
							BigDecimal usage = m.getUsageActualYield();
							BigDecimal usageStandard = m.getUsageStandardYield();
	
							if (usage.compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal yield = production.divide(usage, 4, BigDecimal.ROUND_HALF_UP);
								m.setRecoveryActual(yield);
							}
							
							if (usageStandard.compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal yield = production.divide(usageStandard, 4, BigDecimal.ROUND_HALF_UP);
								m.setRecoveryStandard(yield);
							}
						
						}
						
						if (group.startsWith("Line Tanks")) {
							
							m.setYieldType(YieldType.RECOVERY);
							BigDecimal production = m.getProductionLbAtStd();
							BigDecimal usage = m.getUsageActualYield();
							BigDecimal usageStandard = m.getUsageStandardYield();
	
							if (usage.compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal yield = production.divide(usage, 4, BigDecimal.ROUND_HALF_UP);
								m.setRecoveryActual(yield);
							}
							
							if (usageStandard.compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal yield = production.divide(usageStandard, 4, BigDecimal.ROUND_HALF_UP);
								m.setRecoveryStandard(yield);
							}
							
						}
					}
					
				}	// end Oxnard
				
				

				// Woodburn
				if (inqOperations.getWarehouse().equals("290")) {
					m.setYieldType(YieldType.RECOVERY);
					BigDecimal production = m.getProductionLbAtStd();
					BigDecimal usage = m.getUsageActualYield();
					BigDecimal usageStandard = m.getUsageStandardYield();

					if (usage.compareTo(BigDecimal.ZERO) != 0) {
						BigDecimal yield = production.divide(usage, 4, BigDecimal.ROUND_HALF_UP);
						m.setRecoveryActual(yield);
					}
					
					if (usageStandard.compareTo(BigDecimal.ZERO) != 0) {
						BigDecimal yield = production.divide(usageStandard, 4, BigDecimal.ROUND_HALF_UP);
						m.setRecoveryStandard(yield);
					}
					
				} // end Woodburn

				
				//Medford 
				if (inqOperations.getWarehouse().equals("280")) {
					m.setYieldType(YieldType.RECOVERY);
					BigDecimal production = m.getProductionLbAtStd();
					BigDecimal usageActual = m.getUsageActualYield();
					BigDecimal usageStandard = m.getUsageStandardYield();

					if (usageActual.compareTo(BigDecimal.ZERO) != 0) {
						BigDecimal yield = production.divide(usageActual, 4, BigDecimal.ROUND_HALF_UP);
						m.setRecoveryActual(yield);
					}
					
					if (usageStandard.compareTo(BigDecimal.ZERO) != 0) {
						BigDecimal yield = production.divide(usageStandard, 4, BigDecimal.ROUND_HALF_UP);
						m.setRecoveryStandard(yield);
					}
					
				}
				
				// Wenatchee
				if (inqOperations.getWarehouse().equals("230")) {

					if (type == ReportingType.PROC) {
						m.setYieldType(YieldType.LBPERTON);
						BigDecimal production = m.getProductionLbAtStd();
						BigDecimal usageActual = m.getUsageActualYield();
						BigDecimal usageStandard = m.getUsageStandardYield();

						if (usageActual.compareTo(BigDecimal.ZERO) != 0) {
							BigDecimal yield = production.divide(usageActual, 4, BigDecimal.ROUND_HALF_UP);
							m.setRecoveryActual(yield);
						}
						
						if (usageStandard.compareTo(BigDecimal.ZERO) != 0) {
							BigDecimal yield = production.divide(usageStandard, 4, BigDecimal.ROUND_HALF_UP);
							m.setRecoveryStandard(yield);
						}
						
					}
				}
				
				// Ross
				if (inqOperations.getWarehouse().equals("240")) {

					if (type == ReportingType.PROC) {
						BigDecimal production = m.getProductionLbAtStd();
						BigDecimal usageActual = m.getUsageActualYield();
						BigDecimal usageStandard = m.getUsageStandardYield();
						
						if (group.startsWith("Evap") || group.startsWith("Low Moisture") || group.startsWith("Drum Dry")) {
							m.setYieldType(YieldType.LBPERTON);
							if (usageActual.compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal yield = production.divide(usageActual, 4, BigDecimal.ROUND_HALF_UP);
								m.setRecoveryActual(yield);
							}
							
							if (usageStandard.compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal yield = production.divide(usageStandard, 4, BigDecimal.ROUND_HALF_UP);
								m.setRecoveryStandard(yield);
							}
							
							
						} else {
							
							m.setYieldType(YieldType.RECOVERY);
							if (group.startsWith("Custom Dryer")) {
								// lb for lb recovery
								if (usageActual.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal yield = production
										.divide(usageActual, 4, BigDecimal.ROUND_HALF_UP);
									m.setRecoveryActual(yield);
								}
								
								if (usageStandard.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal yield = production
										.divide(usageStandard, 4, BigDecimal.ROUND_HALF_UP);
									m.setRecoveryStandard(yield);
								}
								
							} else {
								// convert usage tons back to LB
								if (usageActual.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal yield = production
										.divide(usageActual.multiply(new BigDecimal(2000)), 4, BigDecimal.ROUND_HALF_UP);
									m.setRecoveryActual(yield);
								}
								
								if (usageStandard.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal yield = production
										.divide(usageStandard.multiply(new BigDecimal(2000)), 4, BigDecimal.ROUND_HALF_UP);
									m.setRecoveryStandard(yield);
								}
								
							}
							
							
						}

					}
				}
				
				
				
				// Prosser
				if (inqOperations.getWarehouse().equals("469")) {
					
					BigDecimal production = m.getProductionFsAtStd();
					BigDecimal usageActual = m.getUsageActualYield();
					BigDecimal usageStandard = m.getUsageStandardYield();
					
					if (group.startsWith("Hot Apple") 
							|| group.startsWith("NFC")
							|| group.startsWith("Fresh Matters Tude")
							|| group.startsWith("Pear")
							|| group.startsWith("Grape")
							|| m.getYieldType() == YieldType.YIELD) {
						m.setYieldType(YieldType.YIELD);
						
						if (usageActual.compareTo(BigDecimal.ZERO) != 0) {
							BigDecimal yield = production.divide(usageActual, 4, BigDecimal.ROUND_HALF_UP);
							m.setYieldActual(yield);
						}
						if (usageStandard.compareTo(BigDecimal.ZERO) != 0) {
							BigDecimal yield = production.divide(usageStandard, 4, BigDecimal.ROUND_HALF_UP);
							m.setYieldStandard(yield);
						}
						
					} else {
						
						if (usageActual.compareTo(BigDecimal.ZERO) != 0) {
							BigDecimal recovery = production.divide(usageActual, 4, BigDecimal.ROUND_HALF_UP);
							m.setRecoveryActual(recovery);
						}
						if (usageStandard.compareTo(BigDecimal.ZERO) != 0) {
							BigDecimal recovery = production.divide(usageStandard, 4, BigDecimal.ROUND_HALF_UP);
							m.setRecoveryStandard(recovery);
						}
						
					}
					

					
					
				}	//end Prosser
				
				// Fresh Slice
				if (inqOperations.getWarehouse().equals("490")) {
					if (type == ReportingType.PROC) {
						BigDecimal production = m.getProductionLbAtStd();
						BigDecimal usageActual = m.getUsageActualYield();
						BigDecimal usageStandard = m.getUsageStandardYield();
						
						m.setYieldType(YieldType.RECOVERY);
						if (usageActual.compareTo(BigDecimal.ZERO) != 0) {
							BigDecimal yield = production.divide(usageActual, 4, BigDecimal.ROUND_HALF_UP);
							m.setRecoveryActual(yield);
						}
						
						if (usageStandard.compareTo(BigDecimal.ZERO) != 0) {
							BigDecimal yield = production.divide(usageStandard, 4, BigDecimal.ROUND_HALF_UP);
							m.setRecoveryStandard(yield);
						}

					}
				}	//end Fresh Slice
				
				

			} // end loop

		} catch (Exception e) {
			throwError.append(e);
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("calculateYield(");
			throwError.append("InqOperations, ProcessType:" + type.toString() + "). ");
			throw new Exception(throwError.toString());
		}

	}
	
	
	private static void calculateSlurryAndElims(InqOperations inqOperations) throws Exception {
		StringBuffer throwError  = new StringBuffer();
		
		LinkedHashMap<String, ManufacturingOrderDetail> lhm = null;
		ManufacturingOrderDetail pct = new ManufacturingOrderDetail();
		
		try {
		
			lhm = ServiceOperationsReporting.getMos(inqOperations, ReportingType.PROC);
			
			for (String key : lhm.keySet()) {
				
				if (key.startsWith("Evap") 
						|| key.startsWith("Low Moisture")
						|| key.startsWith("Single Pass")
						|| key.startsWith("Frozen")
						|| inqOperations.getWarehouse().equals("490")) {
					
					ManufacturingOrderDetail m = lhm.get(key);
					
					pct.setUsageStandardGross(pct.getUsageStandardGross()
							.add(m.getUsageStandardGross()));
					pct.setUsageActualGross(pct.getUsageActualGross()
							.add(m.getUsageStandardGross()));
					
					
					pct.setUsageStandardSlurry(pct.getUsageStandardSlurry().add(
							m.getUsageStandardSlurry()));
					pct.setUsageActualSlurry(pct.getUsageActualSlurry().add(
							m.getUsageActualSlurry()));
					
					pct.setUsageStandardElims(pct.getUsageStandardElims().add(
							m.getUsageStandardElims()));
					pct.setUsageActualElims(pct.getUsageActualElims().add(
							m.getUsageActualElims()));
					
				
				}
				
			}

			if (pct.getUsageStandardGross().compareTo(BigDecimal.ZERO) != 0) {
				pct.setYieldSlurryStandard(pct.getUsageStandardSlurry()
						.divide(pct.getUsageStandardGross(), 4 , BigDecimal.ROUND_HALF_UP));
			}
			if (pct.getUsageActualGross().compareTo(BigDecimal.ZERO) != 0) {
				pct.setYieldSlurryActual(pct.getUsageActualSlurry()
						.divide(pct.getUsageActualGross(), 4 , BigDecimal.ROUND_HALF_UP));
			}
			
			if (pct.getUsageStandardGross().compareTo(BigDecimal.ZERO) != 0) {
				pct.setYieldElimsStandard(pct.getUsageStandardElims()
						.divide(pct.getUsageStandardGross(), 4 , BigDecimal.ROUND_HALF_UP));
			}
			if (pct.getUsageActualGross().compareTo(BigDecimal.ZERO) != 0) {
				pct.setYieldElimsActual(pct.getUsageActualElims()
						.divide(pct.getUsageActualGross(), 4 , BigDecimal.ROUND_HALF_UP));
			}
			
			lhm.put("ProcSlurryElims", pct);
			
		} catch (Exception e) {
			throwError.append(e);
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("calculateSlurryAndElims(");
			throwError.append("InqOperations)");
			throw new Exception(throwError.toString());
		}

		
	}

	/**
	 * Set labor variance field (Earned - actual)
	 * 
	 * @param lhm
	 */
	private static void calculateLaborVarianceAndBenefits(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm) throws Exception {

		StringBuffer throwError = new StringBuffer();
		try {

			BigDecimal benefitRate = ServiceOperationsReportingFinancials.getBenefitsRate(inqOperations);

			for (String group : lhm.keySet()) {
				ManufacturingOrderDetail mo = lhm.get(group);
				mo.setLaborVariance(mo.getLaborEarned().subtract(mo.getLaborActual())
						.setScale(2, BigDecimal.ROUND_HALF_UP));

				mo.setLaborVarianceWithBenefits(mo.getLaborVariance().multiply(benefitRate.add(BigDecimal.ONE))
						.setScale(2, BigDecimal.ROUND_HALF_UP));

			}
		} catch (Exception e) {
			throwError.append(e);
		}
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("calculateLaborVarianceAndBenefits(");
			throwError.append("InqOperations, LinkedHashMap).  ");
			throw new Exception(throwError.toString());
		}

	}

	private static void getEarningsManufacturing(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm, ReportingType reportingType, Connection conn)
			throws Exception {
		StringBuffer throwError = new StringBuffer();

		ResultSet rs = null;
		Statement stmt = null;

		try {

			String sql = BuildSQL.earnings(inqOperations, reportingType);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				LoadFields.earningsManufacturing(rs, inqOperations, lhm);

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
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("getEarnings(");
			throwError.append("InqOperations, LinkedHashMap).  ");
			throw new Exception(throwError.toString());
		}
	}

	public static void getEarningsFinancial(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingFinance> lhm, ReportingType reportingType) throws Exception {
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

				getEarningsFinancial(inqOperations, lhm, reportingType, conn);

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
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("getEarningsFinancial(InqOperations, LHM, ReportingType. ");
			throw new Exception(throwError.toString());
		}

	}

	private static void getEarningsFinancial(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingFinance> lhm, ReportingType reportingType, Connection conn)
			throws Exception {
		StringBuffer throwError = new StringBuffer();

		ResultSet rs = null;
		Statement stmt = null;

		try {

			String sql = BuildSQL.earnings(inqOperations, reportingType);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				LoadFields.earningsFinancial(rs, inqOperations, lhm);

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
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("getEarnings(");
			throwError.append("InqOperations, LinkedHashMap).  ");
			throw new Exception(throwError.toString());
		}
	}

	private static void summarizeLabor(InqOperations inqOperations) throws Exception {
		
		StringBuffer throwError = new StringBuffer();
		try {
			
			String warehouse = inqOperations.getWarehouse();
			LinkedHashMap<String, ManufacturingOrderDetail> labor = inqOperations.getBean().getProcessingLabor();
			
			if (warehouse.equals("230") || warehouse.equals("240") || warehouse.equals("490")) {
				
				//get the standard labor hours from the processing MOs
				LinkedHashMap<String, ManufacturingOrderDetail> proc = inqOperations.getBean().getProcessingMOs();
				for (String key : proc.keySet()) {
					ManufacturingOrderDetail m = proc.get(key);
					if (!key.contains("Labor")) {
						
						String headerKey = "";
						String[] keyComposite = key.split(":");
						if (keyComposite.length >= 1) {
							headerKey = keyComposite[0];
						}
						// use "Direct" for all of Fresh's processing labor
						if (warehouse.equals("490")) {
							headerKey = "Direct";
						}
						
						
						ManufacturingOrderDetail l = labor.get(headerKey);
						if (l == null) {
							l = new ManufacturingOrderDetail();
							labor.put(headerKey, l);
						}
						l.setLaborHoursStandard(l.getLaborHoursStandard().add(m.getLaborHoursStandard()));
						
						//  LB/MnHr
						if (warehouse.equals("230") || warehouse.equals("240")) {
							l.setProduction(l.getProduction().add(m.getProductionLbAtStd()));
						}
						
						//  CS/MnHr
						if (warehouse.equals("490")) {
							l.setProduction(l.getProduction().add(m.getProduction()));
						}
						
					} else {
						
						String headerKey = key.substring(8,key.length());
						ManufacturingOrderDetail l = labor.get(headerKey);
						
						if (l == null) {
							l = new ManufacturingOrderDetail();
							labor.put(headerKey, l);
						}
						
						l.setLaborHoursActual(l.getLaborHoursActual().add(m.getLaborHoursActual()));
						l.setLaborActual(l.getLaborActual().add(m.getLaborActual()));
						l.setLaborEarned(l.getLaborEarned().add(m.getLaborEarned()));
						l.setLaborVariance(l.getLaborVariance().add(m.getLaborVariance()));
						l.setLaborVarianceWithBenefits(l.getLaborVarianceWithBenefits().add(m.getLaborVarianceWithBenefits()));
						
					}
					
				}
				
				BigDecimal benefitRate = ServiceOperationsReportingFinancials.getBenefitsRate(inqOperations);
				
				for (String key : labor.keySet()) {
					ManufacturingOrderDetail l = labor.get(key);
					if (l.getLaborHoursStandard().compareTo(BigDecimal.ZERO) != 0) {
						l.setUnitsPerManHourStandard(
								l.getProduction().divide(l.getLaborHoursStandard(), 4, BigDecimal.ROUND_HALF_UP));
					}
					if (l.getLaborHoursActual().compareTo(BigDecimal.ZERO) != 0) {
						l.setUnitsPerManHourActual(
								l.getProduction().divide(l.getLaborHoursActual(), 4, BigDecimal.ROUND_HALF_UP));
					}
					
					l.setLaborVariance(l.getLaborEarned().subtract(l.getLaborActual())
							.setScale(2, BigDecimal.ROUND_HALF_UP));

					l.setLaborVarianceWithBenefits(l.getLaborVariance().multiply(benefitRate.add(BigDecimal.ONE))
							.setScale(2, BigDecimal.ROUND_HALF_UP));
					

				}
				

			}		//end Ross
			
		} catch (Exception e) {
			throwError.append(e);
		}
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("summarizeLabor(");
			throwError.append("InqOperations).  ");
			throw new Exception(throwError.toString());
		}
		
	}
	
	private static void calculateCostVariance(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm) throws Exception {
		StringBuffer throwError = new StringBuffer();
		try {

			for (String group : lhm.keySet()) {
				ManufacturingOrderDetail mo = lhm.get(group);

				BigDecimal quantityActual = mo.getCostVarianceActualQty();
				BigDecimal quantityStandard = mo.getCostVarianceStandardQty();
				BigDecimal quantityVariance = quantityStandard.subtract(quantityActual);

				BigDecimal costActual = mo.getCostVarianceActualCost();
				BigDecimal costStandard = mo.getCostVarianceStandardCost();
				BigDecimal costVariance = costStandard.subtract(costActual).setScale(2, BigDecimal.ROUND_HALF_UP);

				BigDecimal priceActual = BigDecimal.ZERO;
				if (quantityActual.compareTo(BigDecimal.ZERO) != 0) {
					priceActual = costActual.divide(quantityActual, 12, BigDecimal.ROUND_HALF_UP);
				}

				BigDecimal priceStandard = BigDecimal.ZERO;
				if (quantityStandard.compareTo(BigDecimal.ZERO) != 0) {
					priceStandard = costStandard.divide(quantityStandard, 12, BigDecimal.ROUND_HALF_UP);
				}

				BigDecimal priceVariance = priceStandard.subtract(priceActual);

				BigDecimal usageVariance = quantityVariance.multiply(priceStandard).setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal substitutionVariance = priceVariance.multiply(quantityActual).setScale(2, BigDecimal.ROUND_HALF_UP);

				mo.setCostVarianceUsage(mo.getCostVarianceUsage().add(usageVariance));
				mo.setCostVarianceSubstitution(mo.getCostVarianceSubstitution().add(substitutionVariance));
				mo.setCostVarianceTotal(mo.getCostVarianceTotal().add(costVariance));

			}
		} catch (Exception e) {
			throwError.append(e);
		}
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("calculateCostVariance(");
			throwError.append("InqOperations, LinkedHashMap).  ");
			throw new Exception(throwError.toString());
		}
	}

	private static void getActualProduction(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm, ReportingType type, Connection conn) throws Exception {

		StringBuffer throwError = new StringBuffer();

		ResultSet rs = null;
		Statement getThem = null;

		// Go out and get the Processing Production Actual.
		try {
			try {
				String sql = BuildSQL.actualProduction(inqOperations, type);
				getThem = conn.createStatement();
				rs = getThem.executeQuery(sql);
			} catch (Exception e) {
				throwError.append("Error occured Building or Executing a sql statement. " + e);
			}

			if (throwError.toString().equals("")) {
				try {
					while (rs.next()) {
						LoadFields.actualProduction(rs, lhm, inqOperations, type);
					}

				} catch (Exception e) {
					throwError.append(" Error occurred loading Actual Processing data. " + e);
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
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("getActualProduction(");
			throwError.append("InqOperations, LinkedHashMap, ");
			throwError.append("ProcessType:" + type.toString() + ",	conn). ");
			throw new Exception(throwError.toString());
		}

	}

	private static void getForecastedProduction(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm, ReportingType type, Connection conn) throws Exception {

		StringBuffer throwError = new StringBuffer();

		ResultSet rs = null;
		Statement getThem = null;

		boolean monthToDate = inqOperations.getRequestType().equals("monthly");
		
		// Go out and get the Processing Production Actual.
		try {
			try {
				String sql = BuildSQL.getPackagingDataForecast(type, inqOperations);
				getThem = conn.createStatement();
				rs = getThem.executeQuery(sql);
			} catch (Exception e) {
				throwError.append("Error occured Building or Executing a sql statement. " + e);
			}

			if (throwError.toString().equals("")) {
				try {
					while (rs.next()) {
						LoadFields.packagingDataForecast(rs, lhm, monthToDate);
					}

				} catch (Exception e) {
					throwError.append(" Error occurred loading forecast Processing data. " + e);
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
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("getForecastedProduction(");
			throwError.append("InqOperations, LinkedHashMap, ");
			throwError.append("ProcessType:" + type.toString() + ",	conn). ");
			throw new Exception(throwError.toString());
		}

	}

	private static void getPlannedProduction(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm, ReportingType type, Connection conn) throws Exception {

		StringBuffer throwError = new StringBuffer();

		ResultSet rs = null;
		Statement getThem = null;

		// Go out and get the Processing Production Actual.
		try {
			try {
				String sql = BuildSQL.getPackagingDataPlanned(type, inqOperations);
				getThem = conn.createStatement();
				rs = getThem.executeQuery(sql);
			} catch (Exception e) {
				throwError.append("Error occured Building or Executing a sql statement. " + e);
			}

			if (throwError.toString().equals("")) {
				try {
					while (rs.next()) {
						LoadFields.packagingDataPlanned(rs, lhm);
					}

				} catch (Exception e) {
					throwError.append(" Error occurred loading planned Processing data. " + e);
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
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("getPlannedProduction(");
			throwError.append("InqOperations, LinkedHashMap, ProcessType:" + type.toString() + ", conn). ");
			throw new Exception(throwError.toString());
		}

	}

	/**
	 * Adds actual raw fruit used information to the processing MOs
	 * LinkedHashMap<br>
	 * All numbers are reported in LB<br>
	 * Fruit used is broken down into individual buckets (Itty 200, Itty 210,
	 * etc).
	 * 
	 * @author jhagle
	 * @param inqOperations
	 * @param conn
	 * @throws Exception
	 */
	private static void getActualMaterials(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm, ReportingType type, Connection conn) throws Exception {

		StringBuffer throwError = new StringBuffer();
		Statement stmt = null;
		ResultSet rs = null;

		try {

			stmt = conn.createStatement();
			String sql = BuildSQL.actualMaterials(inqOperations, type);
			
			rs = stmt.executeQuery(sql);


			while (rs.next()) {

				LoadFields.actualMaterials(rs, lhm, type, inqOperations);

				// if this is a process load, fill out the raw fruit usage LHM
				// using this result set
				if (type == ReportingType.PROC) {
					LinkedHashMap<String, ManufacturingOrderDetail> rfMos = ServiceOperationsReporting.getMos(
							inqOperations, ReportingType.RAWFRUIT);
					LoadFields.actualRawFruit(rs, inqOperations.getBean().getRawFruitMOs(), type, inqOperations);
				}

			}

			calculateCostVariance(inqOperations, lhm);

		} catch (Exception e) {
			System.err.println(e);
			throwError.append(e);
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("getActualMaterials(InqOperations, LinkedHasMap,");
			throwError.append("ProcessType:" + type.toString() + ", conn). ");
			throw new Exception(throwError.toString());
		}

	}
	
	/**
	 * Return Processing Report Data for Plant by Groups Will include Forecast,
	 * Planned and Actual
	 * 
	 * @param InqOperations
	 * @return Will not be sending anything back
	 * @throws Exception
	 */
	private static void getManufacturingByPlantWhse(InqOperations inqOperations, ReportingType type, Connection conn)
			throws Exception {
		StringBuffer throwError = new StringBuffer();

		PreparedStatement getThem = null;
		ResultSet rs = null;
		try // catch all
		{

			LinkedHashMap<String, ManufacturingOrderDetail> lhm = ServiceOperationsReporting
					.getMos(inqOperations, type);

			if (type == ReportingType.PROC) {

				getActualProduction(inqOperations, lhm, type, conn);
				getActualMaterials(inqOperations, lhm, type, conn);
				calculateYield(inqOperations, lhm, type);

				
				summarizeManfacturingOrderDetails(inqOperations);
				
				
				//if wenatchee or ross, calculate slurry and elims
				if (inqOperations.getWarehouse().equals("230") 
						|| inqOperations.getWarehouse().equals("240")
						|| inqOperations.getWarehouse().equals("490")) {
					calculateSlurryAndElims(inqOperations);
				}
				
				
				
				getForecastedRawFruit(inqOperations, lhm, type, conn);
				getPlannedRawFruit(inqOperations, lhm, type, conn);

				ServiceOperationsReportingPayroll.getProcessingLabor(inqOperations, lhm);
				getEarningsManufacturing(inqOperations, lhm, type, conn);

				calculateLaborVarianceAndBenefits(inqOperations, lhm);
				
				summarizeLabor(inqOperations);
				
				
								
			}

			if (type == ReportingType.PKG) {

				
				getActualProduction(inqOperations, lhm, type, conn);
				getActualMaterials(inqOperations, lhm, type, conn);

				getForecastedProduction(inqOperations, lhm, type, conn);
				getPlannedProduction(inqOperations, lhm, type, conn);
				
				ServiceOperationsReportingPayroll.getPackagingLabor(inqOperations, lhm);
				getEarningsManufacturing(inqOperations, lhm, type, conn);
				calculateLaborVarianceAndBenefits(inqOperations, lhm);

				getPackagingHfnc(inqOperations, conn);
			
				
			}

			if (type == ReportingType.FZCHRY) {

				getActualProduction(inqOperations, lhm, type, conn);
				getPlannedProduction(inqOperations, lhm, type, conn);

				ServiceOperationsReportingPayroll.getFrozenCherryLabor(inqOperations, lhm);
				getEarningsManufacturing(inqOperations, lhm, type, conn);
				calculateLaborVarianceAndBenefits(inqOperations, lhm);

			}

			if (type == ReportingType.BLND) {

				getActualProduction(inqOperations, lhm, type, conn);
				getPlannedProduction(inqOperations, lhm, type, conn);
				getActualMaterials(inqOperations, lhm, type, conn);
				calculateYield(inqOperations, lhm, type);

				ServiceOperationsReportingPayroll.getBlendingLabor(inqOperations, lhm);
				getEarningsManufacturing(inqOperations, lhm, type, conn);
				calculateLaborVarianceAndBenefits(inqOperations, lhm);

			}
			

		} catch (Exception e) {
			throwError.append(e);
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("getManufacturingByPlantWhse(");
			throwError.append("InqOperations, ProcessType:" + type.toString() + ", conn). ");
			throw new Exception(throwError.toString());
		}
	}


	/**
	 * Return Processing Report Data for Plant by Groupings.
	 * 
	 * @param InqOperations
	 * @return void  pointer of what was sent in should work as the return
	 * @throws Exception
	 */
	public static void getManufacturingByPlantWhse(InqOperations inqOperations, ReportingType type) throws Exception {
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

				getManufacturingByPlantWhse(inqOperations, type, conn);

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
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("getManufacturingByPlantWhse(InqOperations, ProcessType:" + type.toString() + "). ");
			throw new Exception(throwError.toString());
		}

	}

	/**
	 * Return Packaging HFNC for Plant by Lines.
	 * 
	 * @param InqOperations
	 * @return Will not be sending anything back
	 * @throws Exception
	 * @deprecated
	 */
	private static void getPackagingHfnc(InqOperations inqOperations, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement getThem = null;
		LinkedHashMap<String, ManufacturingOrderDetail> lhm = null;

		try // catch all
		{
			try {
				String sql = BuildSQL.getPackagingHfnc(inqOperations);
				getThem = conn.prepareStatement(sql);
				rs = getThem.executeQuery();
			} catch (Exception e) {
				throwError.append("Error occured Building or Executing a sql statement. " + e);
			}

			if (throwError.toString().equals("")) {
				try {
					lhm = inqOperations.getBean().getPackagingMOs();

					while (rs.next()) {
						LoadFields.packagingHfnc(rs, lhm);
					}

				} catch (Exception e) {
					throwError.append(" Error occurred loading Packaging data. " + e);
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
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("getPackagingHfnc(");
			throwError.append("InqOperations, conn). ");
			throw new Exception(throwError.toString());
		}
	}

	/**
	 * Adds forecasted raw fruit information to the processing MOs LinkedHashMap<br>
	 * All numbers are reported in TONS<br>
	 * Fruit is broken down into individual planning buckets
	 * 
	 * @author twalto 12/6/12
	 * @param inqOperations
	 * @param conn
	 * @throws Exception
	 */
	private static void getForecastedRawFruit(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm, ReportingType type, Connection conn) throws Exception {

		StringBuffer throwError = new StringBuffer();
		Statement stmt = null;
		ResultSet rs = null;

		try {

			stmt = conn.createStatement();
			String sql = BuildSQL.forecastRawFruit(inqOperations);
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				LinkedHashMap<String, ManufacturingOrderDetail> rfMos = ServiceOperationsReporting.getMos(
						inqOperations, ReportingType.RAWFRUIT);

				boolean monthToDate = inqOperations.getRequestType().equals("monthly");
					
				LoadFields.forecastRawFruit(rs, inqOperations.getBean().getRawFruitMOs(), monthToDate);
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
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("getForecastedRawFruit(InqOperations, LinkedHasMap,");
			throwError.append("ProcessType:" + type.toString() + ", conn). ");
			throw new Exception(throwError.toString());
		}

	}

	/**
	 * Adds planned raw fruit information to the processing MOs LinkedHashMap<br>
	 * All numbers are reported in TONS<br>
	 * Fruit is broken down into individual planning buckets
	 * 
	 * @author twalto 12/6/12
	 * @param inqOperations
	 * @param conn
	 * @throws Exception
	 */
	private static void getPlannedRawFruit(InqOperations inqOperations,
			LinkedHashMap<String, ManufacturingOrderDetail> lhm, ReportingType type, Connection conn) throws Exception {

		StringBuffer throwError = new StringBuffer();
		Statement stmt = null;
		ResultSet rs = null;

		try {

			stmt = conn.createStatement();
			String sql = BuildSQL.plannedRawFruit(inqOperations);
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				LoadFields.plannedRawFruit(rs, inqOperations.getBean().getRawFruitMOs());
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
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingManufacturing.");
			throwError.append("getPlannedRawFruit(InqOperations, LinkedHasMap,");
			throwError.append("ProcessType:" + type.toString() + ", conn). ");
			throw new Exception(throwError.toString());
		}

	}
	
	private static void summarizeManfacturingOrderDetails(InqOperations inqOperations) {
		LinkedHashMap<String, ManufacturingOrderDetail> lhm = inqOperations.getBean().getProcessingMOsSummary();
		ManufacturingOrderDetail sum = new ManufacturingOrderDetail();
		String lastGroup = "";
		YieldType yt = null;
		boolean first = true;
		for (String key : inqOperations.getBean().getProcessingMOs().keySet()) {
			ManufacturingOrderDetail m = inqOperations.getBean().getProcessingMOs().get(key);
			String[] keyParts = key.split(":");
			String thisGroup = "";
			if (keyParts.length > 0) {
				thisGroup = keyParts[0];
			}
			
			
			if (!first && !thisGroup.equals(lastGroup)) {
				yt = m.getYieldType();
				sum.setYieldType(yt);
				lhm.put(lastGroup, sum);
				sum = new ManufacturingOrderDetail();
			}
			
			try {
				GeneralUtility.addObjects(m, sum);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			lastGroup = thisGroup;
			first = false;
		}
		
		//add last group
		sum.setYieldType(yt);
		lhm.put(lastGroup, sum);
		
		try {
			calculateYield(inqOperations, lhm, ReportingType.PROC);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

}


