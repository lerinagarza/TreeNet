package com.treetop.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.ManufacturingOrderDetail;
import com.treetop.businessobjects.Safety;
import com.treetop.controller.operations.InqOperations;
import com.treetop.services.ServiceOperationsReporting.ReportingType;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.UtilityDateTime;


/**
 * @author deisen
 *
 * Service class to obtain and return data from
 * H/R systems (Timekeeper, HRMS) for Operations Reporting.
 * 
 */
@RunWith(Suite.class)
public class ServiceOperationsReportingHR {

	/**
	 * @author deisen
	 * main, used for debuging and testing.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) {		
		
		String timeFrame = new String();
		String employee  = new String();
		
		int	 activeTest = 1;		
		
		try {
	
			// testing labor safety period dates.	
	
			if (activeTest == 0)  
			{
				Connection conn = null;
				conn = ServiceConnection.getConnectionStack11(); 
				
				Vector<DateTime> weeklyDates = new Vector<DateTime>();
				weeklyDates = UtilityDateTime.getDatesFromYearWeeklyPeriod("2013", "18", conn);
				String breakPoint = "stopHere";			
			
				try {
				
					if (conn != null)		
						ServiceConnection.returnConnectionStack11(conn);
					
				} catch(Exception e) {						
				}			
			}			

			// testing year-to-date labor safety statistics.	
			
			if (activeTest == 0)  
			{
				InqOperations operations = new InqOperations();
				operations.setEnvironment(" TST ");
				operations.setWarehouse(" 209 ");
				operations.setFiscalYear(" 2013 ");				
				operations.setFiscalWeekEnd(" 16 ");
				timeFrame = "Year";
				employee = "";
			
				getLaborSafetyYearToDate(operations);
				String breakPoint = "stopHere";
				logLaborSafetyResults(operations, timeFrame, employee);			
			}
			
			// testing month-to-date labor safety statistics.	
			
			if (activeTest == 0)  
			{
				InqOperations operations = new InqOperations();
				operations.setEnvironment(" TST ");
				operations.setWarehouse(" 209 ");
				operations.setFiscalYear(" 2013 ");
				operations.setFiscalPeriodEnd(" 04 ");
				operations.setFiscalWeekEnd(" 19 ");
				timeFrame = "Month";
				employee = "61";
				
				getLaborSafetyMonthToDate(operations);
				String breakPoint = "stopHere";
				logLaborSafetyResults(operations, timeFrame, employee);			
			}
			
			// testing week-to-date labor safety statistics.	
			
			if (activeTest == 0)  
			{
				InqOperations operations = new InqOperations();
				operations.setEnvironment(" TST ");
				operations.setWarehouse(" 209 ");
				operations.setFiscalYear(" 2013 ");				
				operations.setFiscalWeekEnd(" 19 ");
				timeFrame = "Week";	
				employee = "";

				getLaborSafetyWeekToDate(operations);
				String breakPoint = "stopHere";
				logLaborSafetyResults(operations, timeFrame, employee);	
			}
			
			// testing labor safety statistics for a single employee
			
			if (activeTest == 0)  
			{
				Vector<String> employeeList = new Vector<String>();
				employeeList.addElement("21084");
				employeeList.addElement("21086");
				employeeList.addElement("21087");
				employeeList.addElement("21089");
				employeeList.addElement("21090");
				employeeList.addElement("21091");
				employeeList.addElement("21092");
				employeeList.addElement("21093");
				employeeList.addElement("21097");
				employeeList.addElement("21109");
				
				for (int y = 0; employeeList.size() > y; y++)
				{
					String employeeNo = (String) employeeList.elementAt(y);	
				
					InqOperations operations = new InqOperations();
					operations.setEnvironment(" TST ");
					operations.setWarehouse(" 209 ");
					operations.setFiscalYear(" 2013 ");
					operations.setFiscalPeriodEnd(" 04 ");
					operations.setFiscalWeekEnd(" 19 ");
					timeFrame = "Month";
					employee = employeeNo;
				
					findFiscalDates(operations);
					buildLaborHoursWorkedEE(operations, "month-to-date", employee);
					String breakPoint = "stopHere";
					logLaborSafetyResults(operations, timeFrame, employee);	
				}
			}
			
			// testing week-to-date labor safety statistics.	
			
			
			
		} catch (Exception e) 
		{
				System.out.println(e);	
		}		

	} // main

	private static class BuildSQL {
		

		/**
		 * @author deisen
		 * SQL to retrieve payroll labor saftey statistics by warehouse.
		 */
			
		private static String getLaborSafety(InqOperations operations)
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();
			
			try {
				
				String startingDate = operations.getBean().getSafetyMetrics().getYearStartDate().elementAt(1);
				String endingDate = operations.getBean().getSafetyMetrics().getWeekEndDate().elementAt(1);
				String periodEndDate = operations.getBean().getSafetyMetrics().getWeekEndDate().elementAt(0);
				
				String libraryHRMS = GeneralUtility.getPayrollLibraryHRMS(operations.getEnvironment());		
				
				if ((!operations.getEnvironment().trim().equals("")) &&
					(!operations.getWarehouse().trim().equals("")) &&
					(!operations.getFiscalYear().trim().equals("")) &&
					(!operations.getFiscalWeekEnd().trim().equals("")) &&
					(!startingDate.trim().equals("")) &&
					(!endingDate.trim().equals("")))
				{
					sqlString.append(" Select substring(pc.PositionCodeUserDefined2,1,2) as MedicalPlantNo, es.EmpNo,");
					sqlString.append(" isnull(hi.DaysAway,0) as AwayDays, isnull(hi.DaysRestricted,0) as RestrictedDays,");	
					sqlString.append(" CONVERT(char(8), hi.IncidentDateTime, 112) as IncidentDate,");									
					sqlString.append(" CONVERT(char(8), DateAdd(Day, isnull(hi.DaysAway,0), hi.IncidentDateTime), 112) as DaysAwayDate,");
					sqlString.append(" DateDiff(Day, '" + endingDate + "', DateAdd(Day, isnull(hi.DaysAway,0), hi.IncidentDateTime)) as DaysAwayAdjust,");					
					sqlString.append(" '" + periodEndDate + "' as PeriodEndDate");
				
					sqlString.append(" From " + libraryHRMS + ".HEALTH_INCIDENTS hi");
					
					sqlString.append(" Left Outer Join " + libraryHRMS + ".vPerson_Positions pp on");
					sqlString.append(" hi.PersonIdNo = pp.PersonIdNo and pp.PositionPrimaryInd = '1' and");
					sqlString.append(" pp.PositionToEffectDate = '1/1/3000'");
					sqlString.append(" Left Outer Join " + libraryHRMS + ".vPosition_Codes pc on");
					sqlString.append(" hi.PersonIdNo = pp.PersonIdNo and pp.PositionPrimaryInd = '1' and");
					sqlString.append(" pp.PositionIdNo = pc.PositionIdNo and pc.PositionCodeToEffectDate = '1/1/3000'");
					sqlString.append(" Left Outer Join " + libraryHRMS + ".vEmployment_Status es on");
					sqlString.append(" hi.PersonIdNo = es.PersonIdNo and es.EmploymentStatusToEffectDate = '1/1/3000'");
					
					sqlString.append(" Where");
					sqlString.append(" hi.IncidentDateTime between '" + startingDate + "' and '" + endingDate + "'");
					
					sqlString.append(" Order By substring(pc.PositionCodeUserDefined2,1,2), es.empno, hi.IncidentDateTime");
				}
					
			} catch (Exception e) 
			{
				throwError.append(" Error building sql getLaborSafety statement. " + e);				
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingHR.");
				throwError.append("BuildSQL.getLaborSafety(InqOperations)");;
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
					
		} // getLaborSafety

		/**
		 * @author deisen
		 * SQL to insert payroll labor saftey statistics.
		 */
			
		private static String insertLaborSafety(InqOperations operations, ResultSet rs, int seqNumber)
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();
			
			try {	
				
				String libraryCustom = GeneralUtility.getPayrollLibraryCustom(operations.getEnvironment());				
				
				if (!operations.getEnvironment().trim().equals(""))	
				{
					String facilityNo = "3" + rs.getString("MedicalPlantNo").trim();
					String sequenceNo = String.format("%09d", seqNumber);					
				
					sqlString.append("INSERT INTO " + libraryCustom + ".WFPEMPSAF ");
					sqlString.append("VALUES(");
					sqlString.append(sequenceNo + ",");
					sqlString.append("'" + "01" + "',");								// Company
					sqlString.append(rs.getString("EmpNo").trim() + ",");
					sqlString.append("'" + facilityNo + "',");
					sqlString.append("'" + "000" + "',");								// Warehouse
					sqlString.append(rs.getString("AwayDays").trim() + ",");	
					sqlString.append(rs.getString("RestrictedDays").trim() + ",");	
					sqlString.append(rs.getString("DaysAwayAdjust").trim() + ",");		
					sqlString.append(rs.getString("IncidentDate").trim() + ",");	
					sqlString.append(rs.getString("DaysAwayDate").trim() + ",");
					sqlString.append(rs.getString("PeriodEndDate").trim());	
					sqlString.append(")");					
				}
					
			} catch (Exception e) 
			{
				throwError.append(" Error building sql insertLaborSafety statement. " + e);				
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingHR.");
				throwError.append("BuildSQL.insertLaborSafetyLaborSafety(InqOperations, ResultSet, int)");;
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
					
		} // insertLaborSafety

		/**
		 * @author deisen
		 * SQL to delete payroll labor saftey statistics.
		 */
			
		private static String deleteLaborSafety(InqOperations operations)
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();
			
			try {	
				
				String libraryCustom = GeneralUtility.getPayrollLibraryCustom(operations.getEnvironment());				
				
				if (!operations.getEnvironment().trim().equals(""))					
				{
					sqlString.append("DELETE FROM " + libraryCustom + ".WFPEMPSAF");					
				}
					
			} catch (Exception e) 
			{
				throwError.append(" Error building sql deleteLaborSafety statement. " + e);				
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingHR.");
				throwError.append("BuildSQL.deleteLaborSafety(InqOperations)");;
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
					
		} // deleteLaborSafety

		/**
		 * @author deisen
		 * SQL to compute payroll labor hours worked.
		 */
			
		private static String getLaborHoursWorked(InqOperations operations, String timeFrame)
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();
			
			try {	
				
				Vector<String> dateList = findTimeFrameDates(operations, timeFrame);
				
				String startingDate = dateList.elementAt(0);								
				String endingDate = dateList.elementAt(1);
				
				Safety safetyDates = operations.getBean().getSafetyMetrics();
				safetyDates.setStartingDate(startingDate);
				safetyDates.setEndingDate(endingDate);
				
				String libraryTK = GeneralUtility.getPayrollLibraryTK(operations.getEnvironment());
				String libraryM3 = GeneralUtility.getLibrary(operations.getEnvironment());
				
				if ((!operations.getEnvironment().trim().equals("")) &&
					(!operations.getWarehouse().trim().equals("")) &&
					(!startingDate.trim().equals("")) &&
					(!endingDate.trim().equals("")))
				{
					sqlString.append(" SELECT IFNULL(SUM(TIHR01),0) AS HoursWorked");
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC01 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC01 <> '     ' AND");					
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR02),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC02 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC02 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR03),0) AS HoursWorked");
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC03 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC03 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR04),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC04 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC04 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR05),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC05 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC05 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR06),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC06 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC06 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR07),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC07 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC07 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR08),0) AS HoursWorked");		
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC08 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC08 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR09),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC09 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC09 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR10),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC10 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC10 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
				}
					
			} catch (Exception e) 
			{
				throwError.append(" Error building sql getLaborHoursWorked statement. " + e);				
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingHR.");
				throwError.append("BuildSQL.getLaborHoursWorked(InqOperations, String)");;
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
					
		} // getLaborHoursWorked

		/**
		 * @author deisen
		 * SQL to insert payroll labor incident detail information.
		 */
			
		private static String updateLaborDetail(InqOperations operations, ResultSet rs)
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();
			
			try {	
				
				String libraryCustom = GeneralUtility.getPayrollLibraryCustom(operations.getEnvironment());				
				
				if ((!operations.getEnvironment().trim().equals("")) &&
					(!operations.getWarehouse().trim().equals("")))
				{
					sqlString.append(" UPDATE " + libraryCustom + ".WFPEMPSAF");			
					sqlString.append(" SET");
					sqlString.append(" OSWHLO = '" + rs.getString("DSWHLO").trim() + "'");					
					
					sqlString.append(" WHERE");
					sqlString.append(" OSREC# = '" + rs.getString("OSREC#").trim() + "'");				
				}
					
			} catch (Exception e) 
			{
				throwError.append(" Error building sql insertLaborSafety statement. " + e);				
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingHR.");
				throwError.append("BuildSQL.updateLaborDetail(InqOperations, ResultSet)");;
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
					
		} // updateLaborDetail

		/**
		 * @author deisen
		 * SQL to retrieve payroll labor incident detail information.
		 */
			
		private static String getLaborDetail(InqOperations operations)
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();
			
			try {
				
				String libraryCustom = GeneralUtility.getPayrollLibraryCustom(operations.getEnvironment());
				String libraryTK = GeneralUtility.getPayrollLibraryTK(operations.getEnvironment());
				
				if ((!operations.getEnvironment().trim().equals("")) &&
					(!operations.getWarehouse().trim().equals("")))										
				{
					sqlString.append(" SELECT OSREC#, IFNULL(DSXREF,'000') AS DSWHLO");					
					
					sqlString.append(" FROM " + libraryCustom + ".WFPEMPSAF");
					
					sqlString.append(" LEFT OUTER JOIN " + libraryTK + ".CKDESCMS ON");
					sqlString.append(" DSCODE = 'S' AND DSKEYM = OSFACI");
					
					sqlString.append(" ORDER BY OSREC#");
				}
					
			} catch (Exception e) 
			{
				throwError.append(" Error building sql getLaborDetail statement. " + e);				
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingHR.");
				throwError.append("BuildSQL.getLaborDetail(InqOperations)");;
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
					
		} // getLaborDetail

		/**
		 * @author deisen
		 * SQL to compute payroll labor incident case statistics.
		 */
			
		private static String getLaborIncidents(InqOperations operations, String timeFrame)
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();
			
			try {
				
				Vector<String> dateList = findTimeFrameDates(operations, timeFrame);
				
				String startingDate = dateList.elementAt(0);								
				String endingDate = dateList.elementAt(1);
				
				Safety safetyDates = operations.getBean().getSafetyMetrics();
				safetyDates.setStartingDate(startingDate);
				safetyDates.setEndingDate(endingDate);
				
				String libraryCustom = GeneralUtility.getPayrollLibraryCustom(operations.getEnvironment());
				
				if ((!operations.getEnvironment().trim().equals("")) &&
					(!operations.getWarehouse().trim().equals("")))										
				{
					sqlString.append(" SELECT OSWHLO, SUM(OSDAWY) AS DaysLost, SUM(OSDRES) AS DaysRestricted,");				
					
					sqlString.append(" (SELECT COUNT(*)");
					sqlString.append("  FROM " + libraryCustom + ".WFPEMPSAF");				
					sqlString.append("  WHERE OSWHLO = '" + operations.getWarehouse().trim() + "' AND");
					sqlString.append("        OSIDTE BETWEEN '" + startingDate + "' AND '" + endingDate + "' AND");
					sqlString.append("        OSDAWY = 0 AND OSDRES = 0) AS MedicalCases,");
					
					sqlString.append(" (SELECT COUNT(*)");
					sqlString.append("  FROM " + libraryCustom + ".WFPEMPSAF");				
					sqlString.append("  WHERE OSWHLO = '" + operations.getWarehouse().trim() + "' AND");
					sqlString.append("        OSIDTE BETWEEN '" + startingDate + "' AND '" + endingDate + "' AND");
					sqlString.append("        OSDAWY > 0) AS LostTime,");
					
					sqlString.append(" (SELECT COUNT(*)");
					sqlString.append("  FROM " + libraryCustom + ".WFPEMPSAF");				
					sqlString.append("  WHERE OSWHLO = '" + operations.getWarehouse().trim() + "' AND");
					sqlString.append("        OSIDTE BETWEEN '" + startingDate + "' AND '" + endingDate + "' AND");
					sqlString.append("        OSDRES > 0) AS RestrictedDuty,");
					
					sqlString.append(" (SELECT IFNULL(SUM(OSDADJ),0) AS DaysLostAdjust");
					sqlString.append("  FROM " + libraryCustom + ".WFPEMPSAF");				
					sqlString.append("  WHERE OSWHLO = '" + operations.getWarehouse().trim() + "' AND");
					sqlString.append("        OSIDTE BETWEEN '" + startingDate + "' AND '" + endingDate + "' AND");
					sqlString.append("        OSDADT > OSPEDT) AS DaysAwayAdjust");
					
					sqlString.append(" FROM " + libraryCustom + ".WFPEMPSAF");
					
					sqlString.append(" WHERE");
					sqlString.append(" OSWHLO = '" + operations.getWarehouse().trim() + "' AND");
					sqlString.append(" OSIDTE BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" GROUP BY OSWHLO");
					sqlString.append(" ORDER BY OSWHLO");
				}
					
			} catch (Exception e) 
			{
				throwError.append(" Error building sql getLaborIncidents statement. " + e);				
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingHR.");
				throwError.append("BuildSQL.getLaborIncidents(InqOperations, String)");;
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
					
		} // getLaborIncidents

		/**
		 * @author deisen
		 * SQL to compute payroll labor hours worked for a single employee.
		 */
			
		private static String getLaborHoursWorkedEE(InqOperations operations, String timeFrame, String employee)
		throws Exception
		{	
			StringBuffer sqlString  = new StringBuffer();	
			StringBuffer throwError = new StringBuffer();
			
			try {	
				
				Vector<String> dateList = findTimeFrameDates(operations, timeFrame);
				
				String startingDate = dateList.elementAt(0);								
				String endingDate = dateList.elementAt(1);
				
				Safety safetyDates = operations.getBean().getSafetyMetrics();
				safetyDates.setStartingDate(startingDate);
				safetyDates.setEndingDate(endingDate);
				
				String libraryTK = GeneralUtility.getPayrollLibraryTK(operations.getEnvironment());
				String libraryM3 = GeneralUtility.getLibrary(operations.getEnvironment());
				
				if ((!operations.getEnvironment().trim().equals("")) &&
					(!operations.getWarehouse().trim().equals("")) &&
					(!startingDate.trim().equals("")) &&
					(!endingDate.trim().equals("")))
				{
					sqlString.append(" SELECT IFNULL(SUM(TIHR01),0) AS HoursWorked");
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC01 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TIEMPN = " + employee + " AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC01 <> '     ' AND");					
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR02),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC02 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TIEMPN = " + employee + " AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC02 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR03),0) AS HoursWorked");
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC03 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TIEMPN = " + employee + " AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC03 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR04),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC04 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TIEMPN = " + employee + " AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC04 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR05),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC05 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TIEMPN = " + employee + " AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC05 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR06),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC06 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TIEMPN = " + employee + " AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC06 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR07),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC07 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TIEMPN = " + employee + " AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC07 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR08),0) AS HoursWorked");		
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC08 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TIEMPN = " + employee + " AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC08 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR09),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC09 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TIEMPN = " + employee + " AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC09 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
					
					sqlString.append(" UNION ALL");
					
					sqlString.append(" SELECT IFNULL(SUM(TIHR10),0) AS HoursWorked");				
					
					sqlString.append(" FROM " + libraryTK + ".CKTIMEFL");									
					sqlString.append(" JOIN " + libraryTK + ".CKWORKCD ON");
					sqlString.append(" TIPC10 = WCCODE and WCNONW = 'N'");
					sqlString.append(" JOIN " + libraryM3 + ".MITWHL ON");
					sqlString.append(" MWCONO = 100 AND MWWHLO = '" + operations.getWarehouse().trim() + "'");
					
					sqlString.append(" WHERE");
					sqlString.append(" TICONO = '1' AND TIEMPN = " + employee + " AND TRIM(TISEC3) = MWFACI AND");
					sqlString.append(" TIPHST = 'P' AND TIPC10 <> '     ' AND");	
					sqlString.append(" SUBSTRING(TIBSFT,1,8) BETWEEN '" + startingDate + "' AND '" + endingDate + "'");
				}
					
			} catch (Exception e) 
			{
				throwError.append(" Error building sql getLaborHoursWorked statement. " + e);				
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceOperatonsReportingHR.");
				throwError.append("BuildSQL.getLaborHoursWorked(InqOperations, String)");;
				
				throw new Exception(throwError.toString());
			}
				
			return sqlString.toString();
					
		} // getLaborHoursWorkedEE

	} // BuildSQL 


	private static class LoadFields {

	
		/**
		 * @author deisen
		 * Load fields for payroll labor safety statistics by warehouse.
		 */
				
		private static void laborIncidents(InqOperations operations, ResultSet rs) 
		throws Exception
		{		
			StringBuffer throwError = new StringBuffer();
			
			try { 
				
				Safety safetyIncidents = operations.getBean().getSafetyMetrics();
				
				BigDecimal medicalCases = new BigDecimal(rs.getString("MedicalCases").trim());
				safetyIncidents.setMedicalCases(medicalCases);
				
				BigDecimal lostTime = new BigDecimal(rs.getString("LostTime").trim());
				safetyIncidents.setLostTimeCases(lostTime);
				
				BigDecimal restrictedDuty = new BigDecimal(rs.getString("RestrictedDuty").trim());
				safetyIncidents.setRestrictedDutyCases(restrictedDuty);
				
				BigDecimal frequency = BigDecimal.ZERO;
				frequency = lostTime.add(restrictedDuty);
				safetyIncidents.setFrequency(frequency);
				
				if (safetyIncidents.getHoursWorked().compareTo(BigDecimal.ZERO) > 0)
				{				
					BigDecimal frequenceFactor = new BigDecimal("200000");					
					BigDecimal frequencyRate = frequency.multiply(frequenceFactor);
					frequencyRate = frequencyRate.divide(safetyIncidents.getHoursWorked(), 2, BigDecimal.ROUND_HALF_UP);
					safetyIncidents.setFrequencyRate(frequencyRate);
				}
				else
					safetyIncidents.setFrequencyRate(BigDecimal.ZERO);
				
				BigDecimal recordable = BigDecimal.ZERO;
				recordable = recordable.add(medicalCases);
				recordable = recordable.add(lostTime);
				recordable = recordable.add(restrictedDuty);
				safetyIncidents.setTotalRecordable(recordable);
				
				if (safetyIncidents.getHoursWorked().compareTo(BigDecimal.ZERO) > 0)
				{				
					BigDecimal frequenceFactor = new BigDecimal("200000");					
					BigDecimal frequencyRate = recordable.multiply(frequenceFactor);
					frequencyRate = frequencyRate.divide(safetyIncidents.getHoursWorked(), 2, BigDecimal.ROUND_HALF_UP);
					safetyIncidents.setTotalRecordableRate(frequencyRate);
				}
				else
					safetyIncidents.setTotalRecordableRate(BigDecimal.ZERO);
				
				BigDecimal daysLost = new BigDecimal(rs.getString("DaysLost").trim());							
				BigDecimal daysRestricted = new BigDecimal(rs.getString("DaysRestricted").trim());
				BigDecimal daysAdjustment = new BigDecimal(rs.getString("DaysAwayAdjust").trim());	
				BigDecimal daysAway = daysLost.add(daysRestricted);				
				daysAway = daysAway.subtract(daysAdjustment);				
				safetyIncidents.setDaysAwayFromWork(daysAway);
				
				if (safetyIncidents.getHoursWorked().compareTo(BigDecimal.ZERO) > 0)
				{				
					BigDecimal frequenceFactor = new BigDecimal("200000");					
					BigDecimal frequencyRate = daysAway.multiply(frequenceFactor);
					frequencyRate = frequencyRate.divide(safetyIncidents.getHoursWorked(), 2, BigDecimal.ROUND_HALF_UP);
					safetyIncidents.setSeverityRate(frequencyRate);
				}
				else
					safetyIncidents.setSeverityRate(BigDecimal.ZERO);
				
				BigDecimal factorRecorded = new BigDecimal("7.5");
				BigDecimal safetyIndex = safetyIncidents.getFrequencyRate();
				BigDecimal frequencyRate = safetyIncidents.getTotalRecordableRate().divide(factorRecorded, 2, BigDecimal.ROUND_HALF_UP);
				safetyIndex = safetyIndex.add(frequencyRate);
				BigDecimal factorSeverity = new BigDecimal("5");
				frequencyRate = safetyIncidents.getSeverityRate().divide(factorSeverity, 2, BigDecimal.ROUND_HALF_UP);
				safetyIndex = safetyIndex.add(frequencyRate);
				safetyIncidents.setSafetyIndex(safetyIndex);				

			} catch (Exception e) 
			{		
				throwError.append("Load fields failed. ");
				throwError.append("laborIncidents. " + e);
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingHR.");
				throwError.append("LoadFields.laborIncidents(InqOperations, ResultSet)");
				
				throw new Exception(throwError.toString());
			}
	
		} // laborIncidents

		/**
		 * @author deisen
		 * Load fields to compute payroll labor hours worked.
		 */
				
		private static void laborHoursWorked(InqOperations operations, ResultSet rs) 
		throws Exception
		{		
			StringBuffer throwError = new StringBuffer();
			
			try { 
				
				Safety safetyHours = operations.getBean().getSafetyMetrics();
				BigDecimal hours = safetyHours.getHoursWorked();
				
				BigDecimal hoursWorked = new BigDecimal(rs.getString("HoursWorked").trim());			
				hours = hours.add(hoursWorked);
				safetyHours.setHoursWorked(hours);
			
			} catch (Exception e) 
			{		
				throwError.append("Load fields failed. ");
				throwError.append("laborHoursWorked. " + e);
			}
				
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append("Error at com.treetop.services.ServiceOperationsReportingHR.");
				throwError.append("LoadFields.laborHoursWorked(InqOperations, ResultSet)");
				
				throw new Exception(throwError.toString());
			}		
			
		} // laborHoursWorked
		
	} // LoadFields

	

	/**
	 * @author deisen
	 * Unit test payroll labor safety metrics.
	 */	
	
	public static class UnitTesting {

		
		
		/**
		 * @author deisen
		 * Unit test payroll labor safety metrics.
		 */	
		
		@Test
		
		public void getLaborSafetyWeekToDate()
		throws Exception
		{
			String timeFrame = new String();
			InqOperations operations = new InqOperations();
		
			operations.setEnvironment(" TST ");
			operations.setWarehouse(" 209 ");
			operations.setFiscalYear(" 2013 ");				
			operations.setFiscalWeekEnd(" 19 ");
			timeFrame = "Week";	
		
			getLaborSafetyWeekToDate();
			
			System.out.println("Environment.....: " + operations.getEnvironment());					
			System.out.println("Whse/Time Frame.: " + operations.getWarehouse() + " " + timeFrame);
			System.out.println("Year/Period/Week: " + operations.getFiscalYear() + "/" + operations.getFiscalPeriodEnd()
					                                + "/" + operations.getFiscalWeekEnd());
			
			assertEquals("StartingDate", "20121202", operations.getBean().getSafetyMetrics().getStartingDate());
			assertEquals("EndingDate", "20121208", operations.getBean().getSafetyMetrics().getEndingDate());
			assertEquals("HoursWorked", "10347.91", operations.getBean().getSafetyMetrics().getHoursWorked());
			assertEquals("MedicalCases", "1", operations.getBean().getSafetyMetrics().getMedicalCases());	
			assertEquals("RestrictedDuty", "1", operations.getBean().getSafetyMetrics().getRestrictedDutyCases());					
			
			
			System.out.println("Lost Time.......: " + operations.getBean().getSafetyMetrics().getLostTimeCases());
			System.out.println("Frequency Total.: " + operations.getBean().getSafetyMetrics().getFrequency());
			System.out.println("Frequency Rate..: " + operations.getBean().getSafetyMetrics().getFrequencyRate());
			System.out.println("Total Recordable: " + operations.getBean().getSafetyMetrics().getTotalRecordable());
			System.out.println("Recordable Rate.: " + operations.getBean().getSafetyMetrics().getTotalRecordableRate());
			System.out.println("Days Away.......: " + operations.getBean().getSafetyMetrics().getDaysAwayFromWork());
			System.out.println("Severity Rate...: " + operations.getBean().getSafetyMetrics().getSeverityRate());
			System.out.println("Safety Index....: " + operations.getBean().getSafetyMetrics().getSafetyIndex());
			System.out.println(" ");
			
		} // getLaborSafetyWeekToDate		
		
	} // UnitTesting

		/**
	 * @author deisen
	 * Build payroll labor incident detail information.
	 */
	
	public static void buildLaborDetail(InqOperations operations)
	throws Exception
	{			
		StringBuffer throwError    = new StringBuffer();		
		Connection   connDB211     = null;
		Connection   connSqlServer = null;
		
		try {
			
			connDB211 = ServiceConnection.getConnectionStack11();			
			buildLaborDetail(operations, connDB211);
			
		} catch (Exception e)
		{
			throwError.append("build labor detail process failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (connDB211 != null)		
					ServiceConnection.returnConnectionStack11(connDB211);
				
			} catch(Exception e)
			{
				throwError.append("Connection DB2 failed return. " + e);
			}
			
			try {
				
				if (connSqlServer != null)		
					ServiceConnectionSqlServer.returnConnection(connSqlServer);
				
			} catch(Exception e)
			{
				throwError.append("Connection sql server failed return. " + e);
			}			
		}
		
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("buildLaborDetail");
			throwError.append("(InqOperations). ");
			
			throw new Exception(throwError.toString());
		}
		
	} // buildLaborDetail

	/**
	 * @author deisen
	 * Retrieve the fiscal begining and ending dates.
	 */
	
	@SuppressWarnings("unchecked")
	private static void findFiscalDates(InqOperations operations, Connection connDB211)
	throws Exception
	{
		StringBuffer     throwError    = new StringBuffer();
		Vector<DateTime> weeklyDates   = new Vector<DateTime>();
		Vector<DateTime> monthlyDates  = new Vector<DateTime>(); 
		DateTime         yearStartDate = new DateTime();
		DateTime         yearEndDate   = new DateTime();
		DateTime         dateTime      = new DateTime();		
		
		try {
			
			Safety safetyDates = operations.getBean().getSafetyMetrics();
			
			int thisYear = (Integer.valueOf(operations.getFiscalYear().trim())) -1;		
			String fiscalYear = new String(Integer.toString(thisYear));
			
			String yearNumber = new String(operations.getFiscalYear().trim());
 			String fiscalWeek = new String(operations.getFiscalWeekEnd().trim());
 			String fiscalMonth = new String(operations.getFiscalPeriodEnd().trim());
			
	 		try {
	 		
	 			yearStartDate = UtilityDateTime.getDateFromyyyyMMdd(fiscalYear + "08" + "01");
	 			yearEndDate = UtilityDateTime.getDateFromyyyyMMdd(yearNumber + "07" + "31");
	 			
	 			if (!fiscalWeek.equals(""))
	 				weeklyDates = UtilityDateTime.getDatesFromYearWeeklyPeriod(yearNumber, fiscalWeek, connDB211);
	 			else {
	 				dateTime.setDateFormatyyyyMMdd("");
	 				dateTime.setDateFormatMMddyyyySlash("");
	 				weeklyDates.add(dateTime);
	 				weeklyDates.add(dateTime);
	 			}
	 			
	 			if (!fiscalMonth.equals(""))
	 				monthlyDates = UtilityDateTime.getDatesFromYearMonthlyPeriod(yearNumber, fiscalMonth, connDB211);
	 			else {
	 				dateTime.setDateFormatyyyyMMdd("");
	 				dateTime.setDateFormatMMddyyyySlash("");
	 				monthlyDates.add(dateTime);
	 				monthlyDates.add(dateTime);
	 			}
			
			 } catch(Exception e)
			 {
			   	throwError.append("Error occured retrieving fiscal begin and end dates. " + e);
			 }
			 
			 if (throwError.toString().trim().equals(""))
			 {
				 try {					 
					 
					 Vector<String> dates = new Vector<String>(safetyDates.getYearStartDate());
					 dates.addElement(yearStartDate.getDateFormatyyyyMMdd().toString().trim());
					 dates.addElement(yearStartDate.getDateFormatMMddyyyySlash().toString().trim());
					 safetyDates.setYearStartDate(dates);
					 
					 dates = new Vector<String>(safetyDates.getYearEndDate());
					 dates.addElement(yearEndDate.getDateFormatyyyyMMdd().toString().trim());
					 dates.addElement(yearEndDate.getDateFormatMMddyyyySlash().toString().trim());
					 safetyDates.setYearEndDate(dates);
					 
					 dates = new Vector<String>(safetyDates.getWeekStartDate());
					 dateTime = (DateTime) weeklyDates.elementAt(0);
					 dates.addElement(dateTime.getDateFormatyyyyMMdd().toString().trim());					 
					 dates.addElement(dateTime.getDateFormatMMddyyyySlash().toString().trim());
					 safetyDates.setWeekStartDate(dates);
					 
					 dates = new Vector<String>(safetyDates.getWeekEndDate());
					 dateTime = (DateTime) weeklyDates.elementAt(1);
					 dates.addElement(dateTime.getDateFormatyyyyMMdd().toString().trim());					 
					 dates.addElement(dateTime.getDateFormatMMddyyyySlash().toString().trim());
					 safetyDates.setWeekEndDate(dates);
					 
					 dates = new Vector<String>(safetyDates.getMonthStartDate());
					 dateTime = (DateTime) monthlyDates.elementAt(0);
					 dates.addElement(dateTime.getDateFormatyyyyMMdd().toString().trim());					 
					 dates.addElement(dateTime.getDateFormatMMddyyyySlash().toString().trim());
					 safetyDates.setMonthStartDate(dates);
					 
					 dates = new Vector<String>(safetyDates.getMonthEndDate());
					 dateTime = (DateTime) monthlyDates.elementAt(1);
					 dates.addElement(dateTime.getDateFormatyyyyMMdd().toString().trim());					 
					 dates.addElement(dateTime.getDateFormatMMddyyyySlash().toString().trim());
					 safetyDates.setMonthEndDate(dates);
					 
		     	 } catch(Exception e)
				 {
		     		throwError.append("Error occured while processing DateTime vector. " + e);
				 }
			 }		 
	
		} catch (Exception e)
		{
			throwError.append("Find fiscal dates processing failed. " + e);		
		}
		
		finally {		
		
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("findFiscalDates");
			throwError.append("(InqOperations, Conn). ");	
			
			throw new Exception(throwError.toString());
		}

	} // findFiscalDates

	/**
	 * @author deisen
	 * Retrieve the fiscal begining and ending dates.
	 */
	
	public static void findFiscalDates(InqOperations operations)
	throws Exception
	{			
		StringBuffer throwError = new StringBuffer();		
		Connection   connDB211  = null;
		
		try {
			
			connDB211 = ServiceConnection.getConnectionStack11(); 
			findFiscalDates(operations, connDB211);
			
		} catch (Exception e)
		{
			throwError.append("Find fiscal dates process failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (connDB211 != null)		
					ServiceConnection.returnConnectionStack11(connDB211);
				
			} catch(Exception e)
			{
				throwError.append("Connection failed return. " + e);
			}			
		}
		
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("findFiscalDates");
			throwError.append("(InqOperations). ");
			
			throw new Exception(throwError.toString());
		}

	} // findFiscalDates

	/**
	 * @author deisen
	 * Build payroll labor safety statistics.
	 */
	
	private static void buildLaborSafety(InqOperations operations, Connection connDB211, Connection connSqlServer)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String       sql        = new String();
		Statement    deleteIt   = null;
		Statement    listThem   = null;
		Statement    insertIt   = null;	
		ResultSet    rs         = null;
		int          seqNumber  = 1000;
		
		try {

			try { 	
	 			
				sql = BuildSQL.deleteLaborSafety(operations); 				
				deleteIt = connDB211.createStatement();
				deleteIt.executeUpdate(sql);  			
	 		
	 		 } catch(Exception e)
	 		 {
	 			throwError.append("Error occured retrieving or executing a sql statement (deleteLaborSafety). " + e);
	 		 }	 		 
	 		
	 		try { 			
	 			
				sql = BuildSQL.getLaborSafety(operations);
				listThem = connSqlServer.createStatement();
				rs = listThem.executeQuery(sql);
				
			 } catch(Exception e)
			 {
				 throwError.append("Error occured retrieving or executing a sql statement (getLaborSafety). " + e);
			 }	
			 
			 if (throwError.toString().trim().equals(""))
			 {
				 try {
					 
					 while (rs.next())
					 {
						 seqNumber = seqNumber + 1;
						 sql = BuildSQL.insertLaborSafety(operations, rs, seqNumber);
						 insertIt = connDB211.createStatement();
				 		 insertIt.executeUpdate(sql);  		
					 }				
					 
		     	 } catch(Exception e)
				 {
		     		throwError.append("Error occured while processing a result set. " + e);
				 }
			 }		 
	
		} catch (Exception e)
		{
			throwError.append("Build labor safety processing failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (rs != null)		  
			  	    rs.close();
				
			} catch(Exception e)
			{
				throwError.append("Result set close failed. " + e);
			}
			
			try {
		   
				if (listThem != null)		  
				    listThem.close();
				
			} catch(Exception e)
			{
				throwError.append("SQL statement close failed. " + e);
			}
			
			try {
				  
				if (insertIt != null)		  
					insertIt.close();
				
			} catch(Exception e)
			{
				throwError.append("Insert connnection close failed. " + e);
			}			
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("buildLaborSafety");
			throwError.append("(InqOperations, Conn, Conn). ");		
			
			throw new Exception(throwError.toString());
		}

	} // buildLaborSafety

	/**
	 * @author deisen
	 * Process to retrieve labor saftey statistics by warehouse.
	 */
		
	private static void getLaborSafety(InqOperations operations, String timeFrame)
	throws Exception
	{			
		StringBuffer throwError    = new StringBuffer();
		Connection   connDB211     = null;
		Connection   connSqlServer = null;
		
		try {
			
			connDB211 = ServiceConnection.getConnectionStack11(); 
			connSqlServer = ServiceConnectionSqlServer.getConnection(); 
				
		} catch (Exception e)
		{
			throwError.append("get labor safety connection process failed. " + e);		
		}
		
		try {
		
			findFiscalDates(operations, connDB211);
			buildLaborSafety(operations, connDB211, connSqlServer);
			buildLaborDetail(operations, connDB211);
			
			buildLaborHoursWorked(operations, timeFrame, connDB211);
			buildLaborIncidents(operations, timeFrame, connDB211);

		} catch (Exception e) 
		{
			throwError.append(" Error processing getLaborSafety. " + e);				
		}
		
		finally {
			
			try {
			
				if (connDB211 != null)		
					ServiceConnection.returnConnectionStack11(connDB211);
				
			} catch(Exception e)
			{
				throwError.append("Connection DB2 failed return. " + e);
			}
			
			try {
				
				if (connSqlServer != null)		
					ServiceConnectionSqlServer.returnConnection(connSqlServer);
				
			} catch(Exception e)
			{
				throwError.append("Connection sql server failed return. " + e);
			}			
		}
			
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("getLaborSafety");
			throwError.append("(InqOperations, String). ");
		
			throw new Exception(throwError.toString());
		}

	} // getLaborSafety

	/**
	 * @author deisen
	 * Build payroll labor hours worked for a single employee.
	 */
	
	public static void buildLaborHoursWorkedEE(InqOperations operations, String timeFrame, String employee)	
	throws Exception
	{			
		StringBuffer throwError = new StringBuffer();		
		Connection   connDB211  = null;
		
		try {
			
			connDB211 = ServiceConnection.getConnectionStack11();			
			buildLaborHoursWorkedEE(operations, timeFrame, employee, connDB211);
			
		} catch (Exception e)
		{
			throwError.append("Build labor hours worked EE process failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (connDB211 != null)		
					ServiceConnection.returnConnectionStack11(connDB211);
				
			} catch(Exception e)
			{
				throwError.append("Connection DB2 failed return. " + e);
			}			
		}
		
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("buildLaborHoursWorkedEE");
			throwError.append("(InqOperations, String, String). ");
			
			throw new Exception(throwError.toString());
		}

	} // buildLaborHoursWorkedEE

	/**
	 * @author deisen
	 * Build payroll labor incident detail information.
	 */
	
	private static void buildLaborDetail(InqOperations operations, Connection connDB211)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String       sql        = new String();		
		Statement    listThem   = null;
		Statement    updateIt   = null;	
		ResultSet    rs         = null;		
		
		try {
	
			try { 	
	 			
				sql = BuildSQL.getLaborDetail(operations); 				
				listThem = connDB211.createStatement();
				rs = listThem.executeQuery(sql);
	 		
	 		 } catch(Exception e)
	 		 {
	 			throwError.append("Error occured retrieving or executing a sql statement. " + e);
	 		 }	 		 

			 if (throwError.toString().trim().equals(""))
			 {
				 try {
					 
					 while (rs.next())
					 {						 
						 sql = BuildSQL.updateLaborDetail(operations, rs);
						 updateIt = connDB211.createStatement();
				 		 updateIt.executeUpdate(sql);  		
					 }				
					 
		     	 } catch(Exception e)
				 {
		     		throwError.append("Error occured while processing a result set. " + e);
				 }
			 }		 
	
		} catch (Exception e)
		{
			throwError.append("Build labor detail processing failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (rs != null)		  
			  	    rs.close();
				
			} catch(Exception e)
			{
				throwError.append("Result set close failed. " + e);
			}
			
			try {
		   
				if (listThem != null)		  
				    listThem.close();
				
			} catch(Exception e)
			{
				throwError.append("SQL statement close failed. " + e);
			}
			
			try {
				  
				if (updateIt != null)		  
					updateIt.close();
				
			} catch(Exception e)
			{
				throwError.append("Update connnection close failed. " + e);
			}			
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("buildLaborDetail");
			throwError.append("(InqOperations, Conn). ");		
			
			throw new Exception(throwError.toString());
		}
	
	} // buildLaborDetail

	/**
	 * @author deisen
	 * Build payroll labor safety statistics.
	 */
	
	public static void buildLaborSafety(InqOperations operations)
	throws Exception
	{			
		StringBuffer throwError    = new StringBuffer();		
		Connection   connDB211     = null;
		Connection   connSqlServer = null;
		
		try {
			
			connDB211 = ServiceConnection.getConnectionStack11(); 
			connSqlServer = ServiceConnectionSqlServer.getConnection(); 
			
			buildLaborSafety(operations, connDB211, connSqlServer);
			
		} catch (Exception e)
		{
			throwError.append("build labor safety process failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (connDB211 != null)		
					ServiceConnection.returnConnectionStack11(connDB211);
				
			} catch(Exception e)
			{
				throwError.append("Connection DB2 failed return. " + e);
			}
			
			try {
				
				if (connSqlServer != null)		
					ServiceConnectionSqlServer.returnConnection(connSqlServer);
				
			} catch(Exception e)
			{
				throwError.append("Connection sql server failed return. " + e);
			}			
		}
		
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("buildLaborSafety");
			throwError.append("(InqOperations). ");
			
			throw new Exception(throwError.toString());
		}

	} // buildLaborSafety

	/**
	 * @author deisen
	 * Build payroll labor incident case statistics.
	 */
	
	private static void buildLaborIncidents(InqOperations operations, String timeFrame, Connection connDB211)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String       sql        = new String();		
		Statement    listThem   = null;		
		ResultSet    rs         = null;		
		
		try {
	
			try { 	
	 			
				sql = BuildSQL.getLaborIncidents(operations, timeFrame); 				
				listThem = connDB211.createStatement();
				rs = listThem.executeQuery(sql);
	 		
	 		 } catch(Exception e)
	 		 {
	 			throwError.append("Error occured retrieving or executing a sql statement. " + e);
	 		 }	 		 
	
			 if (throwError.toString().trim().equals(""))
			 {
				 try {
					 
					 while (rs.next())
					 {						 
						 LoadFields.laborIncidents(operations, rs);						
					 }				
					 
		     	 } catch(Exception e)
				 {
		     		throwError.append("Error occured while processing a result set. " + e);
				 }
			 }		 
	
		} catch (Exception e)
		{
			throwError.append("Build labor incidents processing failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (rs != null)		  
			  	    rs.close();
				
			} catch(Exception e)
			{
				throwError.append("Result set close failed. " + e);
			}
			
			try {
		   
				if (listThem != null)		  
				    listThem.close();
				
			} catch(Exception e)
			{
				throwError.append("SQL statement close failed. " + e);
			}			
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("buildLaborIncidents");
			throwError.append("(InqOperations, String, Conn). ");		
			
			throw new Exception(throwError.toString());
		}

	} // buildLaborIncidents

	/**
	 * @author deisen
	 * Build payroll labor incident case statistics.
	 */
	
	public static void buildLaborIncidents(InqOperations operations, String timeFrame)
	throws Exception
	{			
		StringBuffer throwError = new StringBuffer();		
		Connection   connDB211  = null;
		
		try {
			
			connDB211 = ServiceConnection.getConnectionStack11();			
			buildLaborIncidents(operations, timeFrame, connDB211);
			
		} catch (Exception e)
		{
			throwError.append("build labor incidents process failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (connDB211 != null)		
					ServiceConnection.returnConnectionStack11(connDB211);
				
			} catch(Exception e)
			{
				throwError.append("Connection DB2 failed return. " + e);
			}			
		}
		
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("buildLaborIncidents");
			throwError.append("(InqOperations, String). ");
			
			throw new Exception(throwError.toString());
		}

	} // buildLaborIncidents

	/**
	 * @author deisen
	 * Build payroll labor hours worked.
	 */
	
	private static void buildLaborHoursWorked(InqOperations operations, String timeFrame, Connection connDB211)	
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String       sql        = new String();		
		Statement    listThem   = null;		
		ResultSet    rs         = null;		
		
		try {
			
			Safety safetyHours = operations.getBean().getSafetyMetrics();
			safetyHours.setHoursWorked(BigDecimal.ZERO);
	
			try { 	
	 			
				sql = BuildSQL.getLaborHoursWorked(operations, timeFrame); 				
				listThem = connDB211.createStatement();
				rs = listThem.executeQuery(sql);
	 		
	 		 } catch(Exception e)
	 		 {
	 			throwError.append("Error occured retrieving or executing a sql statement. " + e);
	 		 }	 		 
	
			 if (throwError.toString().trim().equals(""))
			 {
				 try {
					 
					 while (rs.next())
					 {						 
						 LoadFields.laborHoursWorked(operations, rs);						
					 }				
					 
		     	 } catch(Exception e)
				 {
		     		throwError.append("Error occured while processing a result set. " + e);
				 }
			 }		 
	
		} catch (Exception e)
		{
			throwError.append("Build labor hours worked processing failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (rs != null)		  
			  	    rs.close();
				
			} catch(Exception e)
			{
				throwError.append("Result set close failed. " + e);
			}
			
			try {
		   
				if (listThem != null)		  
				    listThem.close();
				
			} catch(Exception e)
			{
				throwError.append("SQL statement close failed. " + e);
			}			
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("buildLaborHoursWorked");
			throwError.append("(InqOperations, String, Conn). ");		
			
			throw new Exception(throwError.toString());
		}
	
	} // buildLaborHoursWorked

	/**
	 * @author deisen
	 * Determine payroll labor processing time frame dates.
	 */
		
	private static Vector<String> findTimeFrameDates(InqOperations operations, String timeFrame)	
	throws Exception
	{		
		StringBuffer throwError = new StringBuffer();
		Vector<String> dateList = new Vector<String>(); 
		
		try {	
			
			Safety safetyDates = operations.getBean().getSafetyMetrics();

			if (timeFrame.trim().toUpperCase().equals("YEAR-TO-DATE"))
			{
				dateList.addElement(safetyDates.getYearStartDate().elementAt(0));
				dateList.addElement(safetyDates.getWeekEndDate().elementAt(0));								
			}
			
			if (timeFrame.trim().toUpperCase().equals("WEEK-TO-DATE"))
			{				
				dateList.addElement(safetyDates.getWeekStartDate().elementAt(0));
				dateList.addElement(safetyDates.getWeekEndDate().elementAt(0));				
			}
			
			if (timeFrame.trim().toUpperCase().equals("MONTH-TO-DATE"))
			{
				dateList.addElement(safetyDates.getMonthStartDate().elementAt(0));
				dateList.addElement(safetyDates.getMonthEndDate().elementAt(0));				
			}			
				
		} catch (Exception e) 
		{
			throwError.append(" Error retrieving time frame dates. " + e);				
		}
			
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append("Error at com.treetop.services.ServiceOperatonsReportingHR.");
			throwError.append("BuildSQL.findTimeFrameDates(InqOperations, String)");;
			
			throw new Exception(throwError.toString());
		}
			
		return dateList;
				
	} // findTimeFrameDates

	/**
	 * @author deisen
	 * Process to retrieve month-to-date labor safety statistics by warehouse.
	 */
		
	public static void getLaborSafetyMonthToDate(InqOperations operations)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String       timeFrame  = new String("month-to-date");		
		
		try {
		
			getLaborSafety(operations, timeFrame);
			
		} catch (Exception e) 
		{
			throwError.append(" Error processing getLaborSafetyMonthToDate. " + e);				
		}

		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("getLaborSafetyMonthToDate");
			throwError.append("(InqOperations). ");
		
			throw new Exception(throwError.toString());
		}

	} // getLaborSafetyMonthToDate

	/**
	 * @author deisen
	 * Process to retrieve year-to-date labor safety statistics by warehouse.
	 */
		
	public static void getLaborSafetyYearToDate(InqOperations operations)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String       timeFrame  = new String("year-to-date");		
		
		try {
		
			getLaborSafety(operations, timeFrame);
			
		} catch (Exception e) 
		{
			throwError.append(" Error processing getLaborSafetyYearToDate. " + e);				
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("getLaborSafetyYearToDate");
			throwError.append("(InqOperations). ");
		
			throw new Exception(throwError.toString());
		}
	
	} // getLaborSafetyYearToDate

	/**
	 * @author deisen
	 * Process to retrieve week-to-date labor safety statistics by warehouse.
	 */
		
	public static void getLaborSafetyWeekToDate(InqOperations operations)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String       timeFrame  = new String("week-to-date");		
		
		try {
		
			getLaborSafety(operations, timeFrame);
			
		} catch (Exception e) 
		{
			throwError.append(" Error processing getLaborSafetyWeekToDate. " + e);				
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("getLaborSafetyWeekToDate");
			throwError.append("(InqOperations). ");
		
			throw new Exception(throwError.toString());
		}
	
	} // getLaborSafetyWeekToDate

	/**
	 * @author deisen
	 * Log safety statistics to the console.
	 */
		
	public static void logLaborSafetyResults(InqOperations operations, String timeFrame, String employee)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		
		try {
		
			System.out.println("Environment.....: " + operations.getEnvironment());					
			System.out.println("Whse/Time Frame.: " + operations.getWarehouse() + " " + timeFrame);
			System.out.println("Year/Period/Week: " + operations.getFiscalYear() + "/" + operations.getFiscalPeriodEnd()
					                                + "/" + operations.getFiscalWeekEnd());
			System.out.println("Employee Number.: " + employee);
			System.out.println("Starting Date...: " + operations.getBean().getSafetyMetrics().getStartingDate());
			System.out.println("Ending Date.....: " + operations.getBean().getSafetyMetrics().getEndingDate());	
			System.out.println("Hours Worked....: " + operations.getBean().getSafetyMetrics().getHoursWorked());
			
			if (employee.trim().equals(""))
			{
				System.out.println("Medical Cases...: " + operations.getBean().getSafetyMetrics().getMedicalCases());
				System.out.println("Restricted Duty.: " + operations.getBean().getSafetyMetrics().getRestrictedDutyCases());
				System.out.println("Lost Time.......: " + operations.getBean().getSafetyMetrics().getLostTimeCases());
				System.out.println("Frequency Total.: " + operations.getBean().getSafetyMetrics().getFrequency());
				System.out.println("Frequency Rate..: " + operations.getBean().getSafetyMetrics().getFrequencyRate());
				System.out.println("Total Recordable: " + operations.getBean().getSafetyMetrics().getTotalRecordable());
				System.out.println("Recordable Rate.: " + operations.getBean().getSafetyMetrics().getTotalRecordableRate());
				System.out.println("Days Away.......: " + operations.getBean().getSafetyMetrics().getDaysAwayFromWork());
				System.out.println("Severity Rate...: " + operations.getBean().getSafetyMetrics().getSeverityRate());
				System.out.println("Safety Index....: " + operations.getBean().getSafetyMetrics().getSafetyIndex());
			}
			
			System.out.println(" ");
			
		} catch (Exception e) 
		{
			throwError.append(" Error processing logLaborSafetyResults. " + e);				
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("logLaborSafetyResults");
			throwError.append("(InqOperations, String). ");
		
			throw new Exception(throwError.toString());
		}
	
	} // logLaborSafetyResults 

	/**
	 * @author deisen
	 * Build payroll labor hours worked.
	 */
	
	public static void buildLaborHoursWorked(InqOperations operations, String timeFrame)	
	throws Exception
	{			
		StringBuffer throwError = new StringBuffer();		
		Connection   connDB211  = null;
		
		try {
			
			connDB211 = ServiceConnection.getConnectionStack11();			
			buildLaborHoursWorked(operations, timeFrame, connDB211);
			
		} catch (Exception e)
		{
			throwError.append("build labor hours worked process failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (connDB211 != null)		
					ServiceConnection.returnConnectionStack11(connDB211);
				
			} catch(Exception e)
			{
				throwError.append("Connection DB2 failed return. " + e);
			}			
		}
		
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("buildLaborHoursWorked");
			throwError.append("(InqOperations, String). ");
			
			throw new Exception(throwError.toString());
		}
	
	} // buildLaborHoursWorked

	/**
	 * @author deisen
	 * Build payroll labor hours worked for a single employee.
	 */
	
	private static void buildLaborHoursWorkedEE(InqOperations operations, String timeFrame, String employee, 
			                                    Connection connDB211)	
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String       sql        = new String();		
		Statement    listThem   = null;		
		ResultSet    rs         = null;		
		
		try {
			
			Safety safetyHours = operations.getBean().getSafetyMetrics();
			safetyHours.setHoursWorked(BigDecimal.ZERO);
	
			try { 	
	 			
				sql = BuildSQL.getLaborHoursWorkedEE(operations, timeFrame, employee); 				
				listThem = connDB211.createStatement();
				rs = listThem.executeQuery(sql);
	 		
	 		 } catch(Exception e)
	 		 {
	 			throwError.append("Error occured retrieving or executing a sql statement. " + e);
	 		 }	 		 
	
			 if (throwError.toString().trim().equals(""))
			 {
				 try {
					 
					 while (rs.next())
					 {						 
						 LoadFields.laborHoursWorked(operations, rs);						
					 }				
					 
		     	 } catch(Exception e)
				 {
		     		throwError.append("Error occured while processing a result set. " + e);
				 }
			 }		 
	
		} catch (Exception e)
		{
			throwError.append("Build labor hours worked EE processing failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (rs != null)		  
			  	    rs.close();
				
			} catch(Exception e)
			{
				throwError.append("Result set close failed. " + e);
			}
			
			try {
		   
				if (listThem != null)		  
				    listThem.close();
				
			} catch(Exception e)
			{
				throwError.append("SQL statement close failed. " + e);
			}			
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsReportingHR.");
			throwError.append("buildLaborHoursWorkedEE");
			throwError.append("(InqOperations, String, String, Conn). ");		
			
			throw new Exception(throwError.toString());
		}
	
	} // buildLaborHoursWorkedEE

} // ServiceOperationsReportingHR
