/*
 * Created on May 15, 2008
 *
 */
package com.treetop.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.Vector;

//import com.ibm.as400.access.AS400;
//import com.ibm.as400.access.AS400Text;
//import com.ibm.as400.access.ProgramParameter;
import com.treetop.businessobjects.DataSet;
import com.treetop.businessobjects.DateTime;
import com.treetop.app.dataset.*;
import com.treetop.utilities.*;

/**
 * @author twalto
 *
 * Services class to obtain and return data 
 * to business objects.
 * Lot Services Object.
 */
public class ServiceDataSet extends BaseService{

	public static final String dblib   = "DBPRD";
	public static final String library = "M3DJDPRD";
	//public static final String libraryx = "APDEV";
	
	/**
	 *  Construction of the Base inforamtion
	 */
	public ServiceDataSet() {
		super();
	}
	
	/**
	 * Read through the Chosen DataSet for specific year and get the 
	 * company cost for the item (ZTP9CST), and update the DataSet 
	 * Will update field UCUCOS - in the Chosen DataSet
	 * 
	 * @param UpdDataSet
	 * @return Nothing.
	 * @throws Exception
	 */
	public static void updateCompanyCostDmpDataSet(UpdDataSet inValues, String budgetVersion)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = com.treetop.services.ServiceConnection.getConnectionStack1();
			updateCompanyCostDmpDataSet(inValues, conn, budgetVersion);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
					com.treetop.services.ServiceConnection.returnConnectionStack1(conn);
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceDataSet.");
			throwError.append("updateCompanyCostDmpDataSet(");
			throwError.append("UpdDataSet). ");
			throw new Exception(throwError.toString());
		}
		return;
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
		
		//REMEMBER -- the File name is being sent in with the UpdDataSet
		
		try { 
			//******************************************************************
			//*** LIST SECTION
			//******************************************************************
			if (inRequestType.equals("listNames"))
			{
				fieldList.append("SELECT SCSBDS, SCTX40, SCTX15, SCFILE ");
				sqlString.append("FROM " + library + ".OSSSET ");
				sqlString.append("  WHERE SCFILE <> ' ' ");
				sqlString.append("GROUP BY SCSBDS, SCTX40, SCTX15, SCFILE ");
				sqlString.append("ORDER BY SCTX40 ");
			}
			
			
			
			if (inRequestType.equals("listYears"))
			{
				fieldList.append("SELECT OSYEA4 ");
				sqlString.append("FROM " + library + ".O100001 ");
				sqlString.append("GROUP BY OSYEA4 ");
				sqlString.append("ORDER BY OSYEA4 ");
			}
			
			
			
			if (inRequestType.equals("getForecastWithoutActuals"))
			{
				String year       = (String) requestClass.elementAt(0);
				String period     = (String) requestClass.elementAt(1);
				String recordtype = (String) requestClass.elementAt(2);
				String version    = (String) requestClass.elementAt(3);
				
				sqlString.append("SELECT * ");
				sqlString.append("FROM " + library + ".o100022 ");
				sqlString.append("WHERE OSYEA4 = " + year + " ");
				sqlString.append("AND OSPERI = " + period + " ");
				sqlString.append("AND OSSSTT =  " + recordtype + " ");
				sqlString.append("AND OSBVER = '" + version + "' ");
				sqlString.append("AND UCIVQT <> 0 ");
				sqlString.append("AND ROUND(UCIVQT,0) = ROUND(UCVOL3,0) ");
			}
			
			
			
			if (inRequestType.equals("checkForActual"))
			{
				ResultSet rs = (ResultSet) requestClass.elementAt(0);
				
				sqlString.append("SELECT * ");
				sqlString.append("FROM " + library + ".o100022 ");
				sqlString.append("WHERE OSYEA4 = " + rs.getString("OSYEA4") + " ");
				sqlString.append("AND OSPERI = " + rs.getString("OSPERI") + " ");
				sqlString.append("AND OSSSTT =  32 ");
				sqlString.append("AND UCCUNO = '" + rs.getString("UCCUNO") + "' ");
				sqlString.append("AND UCITNO = '" + rs.getString("UCITNO") + "' ");
				sqlString.append("AND UCIVQT <> 0 ");
			}
			
			
			
			if (inRequestType.equals("readItemsFromDataSet"))
			{
				UpdDataSet uds = (UpdDataSet) requestClass.elementAt(0);
				String budgetVersion = (String) requestClass.elementAt(1);
				
				fieldList.append("SELECT UCITNO, OSYEA4, T9CSU9, T9ALU9, OSSSTT, OSBVER, MMITTY ");
				sqlString.append("FROM " + library + "." + uds.getDataSetName().trim() + " ");
				sqlString.append("INNER JOIN " + library + ".MITMAS ON UCITNO = MMITNO ");
				sqlString.append("INNER JOIN " + dblib + ".ZTP9CST ");
				sqlString.append("ON T9YEA4 = " + uds.getCostYear() + " AND MMITNO = T9ITNO ");
				sqlString.append("WHERE OSYEA4 = " + uds.getDataSetYear() + " AND OSSSTT = 33 ");
				sqlString.append("AND OSBVER = '" + budgetVersion + "' ");
				sqlString.append("GROUP BY UCITNO, OSYEA4, T9CSU9, T9ALU9, OSSSTT, OSBVER, MMITTY ");
				sqlString.append("ORDER BY UCITNO ");
			}																						
			
			
			
			if (inRequestType.equals("readItemsFrom22File"))									
			{																					
				Vector parms = (Vector) requestClass.elementAt(0);
				String t9year	= (String) parms.elementAt(0);	//t9year
				String cono		= (String) parms.elementAt(1);	//oscono
				String fileName	= (String) parms.elementAt(2);	//file name
				String yea4		= (String) parms.elementAt(3);	//osyea4
				String sstt		= (String) parms.elementAt(4);	//ossstt
				String bver		= (String) parms.elementAt(5);	//osbver
				
				fieldList.append("SELECT OSCONO, OSYEA4, OSSSTT, OSBVER, UCITNO, OKCUCL, MMITCL, T9CSU9, T9ALU9 ");					
				sqlString.append("FROM " + library + "." + fileName.trim() + " ");
				sqlString.append("INNER JOIN " + library + ".OCUSMA ");
				sqlString.append("ON OSCONO = OKCONO AND UCCUNO = OKCUNO ");
				sqlString.append("INNER JOIN " + library + ".MITMAS ");
				sqlString.append("ON OSCONO = MMCONO AND UCITNO = MMITNO ");
				sqlString.append("INNER JOIN " + dblib + ".ZTP9CST ");
				sqlString.append("ON T9YEA4 = " + t9year + " AND UCITNO = T9ITNO ");
				sqlString.append("WHERE OSCONO = " + cono + " AND OSYEA4 = " + yea4 + " ");
				sqlString.append("AND OSSSTT = " + sstt + " AND OSBVER = '" + bver.trim() + "' ");  
				sqlString.append("GROUP BY OSCONO, OSYEA4, OSSSTT, OSBVER, UCITNO, OKCUCL, MMITCL, T9CSU9, T9ALU9 ");											
			}
			
			
			
			if (inRequestType.equals("ByCustItem"))
			{
				UpdDataSet fromVb = (UpdDataSet) requestClass.elementAt(0);
				sqlString.append("SELECT * FROM " + library + "." + fromVb.getDataSetName() + " ");
				sqlString.append(" WHERE OSSSTT = " + fromVb.getRecordType() + " ");
				sqlString.append(" AND OSYEA4 = " + fromVb.getSalesYear());
				sqlString.append(" ORDER BY UCCUNO, UCITNO ");
			}
			
			
			
			if (inRequestType.equals("revenueCustItem"))
			{	
				String fileName = (String) requestClass.elementAt(0);
				String version  = (String) requestClass.elementAt(1);
				String year		= (String) requestClass.elementAt(2);
				String sPeriod	= (String) requestClass.elementAt(3);
				String ePeriod	= (String) requestClass.elementAt(4);
				sqlString.append("SELECT oscono, osdivi, ossstt, osbver, oslevl, osyea4, uccuno, ucitno ");
				sqlString.append("FROM " + library + "." + fileName + " ");
				sqlString.append("WHERE OSSSTT = 33 ");
				sqlString.append("AND OSBVER = '" + version + "' ");
				sqlString.append("AND OSYEA4 = " + year + " ");
				sqlString.append("AND OSPERI >= " + sPeriod + " ");
				sqlString.append("AND OSPERI <= " + ePeriod + " ");
				sqlString.append("AND UCCUNO < '99999' ");
				sqlString.append("Group By oscono, osdivi, ossstt, osbver, oslevl, osyea4, uccuno, ucitno ");
				sqlString.append("ORDER BY UCCUNO, UCITNO ");
			}
			
			
			
			if (inRequestType.equals("getBrokerageDataSet"))
			{
				String fileName = (String) requestClass.elementAt(0);
				String year		= (String) requestClass.elementAt(1);
				String sPeriod	= (String) requestClass.elementAt(2);
				String ePeriod	= (String) requestClass.elementAt(3);
				String version  = (String) requestClass.elementAt(4);
				sqlString.append("SELECT * FROM " + library + "." + fileName + " ");
				sqlString.append("WHERE OSSSTT = 33 ");
				sqlString.append("AND OSBVER = '" + version + "' ");
				sqlString.append("AND OSYEA4 = " + year + " ");
				sqlString.append("AND OSPERI >= " + sPeriod + " ");
				sqlString.append("AND OSPERI <= " + ePeriod + " ");
				sqlString.append("AND UCCUNO < '99999' ");
				sqlString.append(" ORDER BY UCCUNO, UCITNO ");
			}
			
			
			
			if (inRequestType.equals("getBrokerageObotra"))
			{
				ResultSet rs    = (ResultSet) requestClass.elementAt(0);
				String cust     = rs.getString("UCCUNO");
				String item     = rs.getString("UCITNO");
				sqlString.append("SELECT OCIVNO, OCRATX FROM " + library + ".OBOTRA ");
				sqlString.append("WHERE OCCUNO = '" + cust + "' ");
				sqlString.append("AND OCITNO = '" + item + "' ");
				sqlString.append("AND SUBSTRING(OCCHID,1,3) != 'OER' ");
				sqlString.append("ORDER BY OCIVNO DESC ");
			}
			
			
			
			if (inRequestType.equals("listAudit"))
			{
				String fileName = (String) requestClass.elementAt(0);
				fieldList.append("SELECT DSAFILE, DSAYEARFR, DSAYEARTO, DSARECTYPE, ");
				fieldList.append("       DSACOMMENT, DSASTATUS, DSADATE, DSATIME, DSAUSER, ");
				sqlString.append("FROM " + dblib + ".DSPAUDIT ");
				fieldList.append("       SCSBDS, SCTX40, SCTX15, SCFILE ");
				sqlString.append("INNER JOIN " + library + ".OSSSET ");
				sqlString.append("   ON SCFILE = DSAFILE ");
				
				if (!fileName.trim().equals(""))
					sqlString.append("  WHERE DSAFILE = '" + fileName.trim() + "' ");
				
				sqlString.append("ORDER BY DSADATE DESC, DSATIME DESC ");
			}
			
			
			
			if (inRequestType.equals("getCustomerChain"))
			{
				ResultSet rsChain = (ResultSet) requestClass.elementAt(0);
				sqlString.append("SELECT * FROM " + library + ".OCHCUS ");
				sqlString.append("inner join dbprd.PLL1PRC4 on oschct = pl1nme ");
				sqlString.append(" WHERE OSCUNO = '" + rsChain.getString("UCCUNO").trim() + "' ");
			}
			
			
			
			if (inRequestType.equals("getDeliveryTypes"))
			{
				ResultSet rsCustItem = (ResultSet) requestClass.elementAt(0);
				String    date       = (String)    requestClass.elementAt(1);
				sqlString.append("SELECT OBCUNO, OBITNO, OBMODL FROM " + library + ".OOLINE ");
				sqlString.append(" WHERE OBCUNO = '" + rsCustItem.getString("UCCUNO").trim() + "' ");
				sqlString.append(" AND OBITNO = '" + rsCustItem.getString("UCITNO").trim() + "' ");
				sqlString.append(" AND OBDWDT >= " + date + " ");
			}
			
			
			
			if (inRequestType.equals("getPriceCustChain"))
			{
				ResultSet rsChain    = (ResultSet) requestClass.elementAt(0);
				ResultSet rsCustItem = (ResultSet) requestClass.elementAt(1);
				sqlString.append("SELECT * FROM " + dblib + ".PLL1PRC4 ");
				sqlString.append(" WHERE PL1NME = '" + rsChain.getString("OSCHCT").trim() + "' ");
				sqlString.append(" AND PL1ITM = '" + rsCustItem.getString("UCITNO").trim() + "' ");
				
				// 20120524 wth - For 2012 BUDG use only where PL1SED (Sort Effective date) equals 20120801 per Brooke Goodrich.
				//sqlString.append(" AND PL1SED <= 20111001 "); //temporary for 20111101 - issues with pricing per Becky Rish
				//sqlString.append("AND PL1SED = 20120801 "); //temporary for Brooke order by not necessary. 11/07/12
				sqlString.append(" ORDER BY PL1YR DESC, PL1MM DESC, PL1DD DESC "); //normal FOQx
				
			}
			
			
			
			if (inRequestType.equals("getPriceCustMarket"))
			{
				ResultSet rsCustMstr = (ResultSet) requestClass.elementAt(0);
				ResultSet rsCustItem = (ResultSet) requestClass.elementAt(1);
				sqlString.append("SELECT * FROM " + dblib + ".PLL1PRC4 ");
				sqlString.append(" WHERE PL1NME = 'MKT" + rsCustMstr.getString("OKCFC3").trim() + "' ");
				sqlString.append(" AND PL1ITM = '" + rsCustItem.getString("UCITNO").trim() + "' ");
				
				// 20120524 wth - For 2012 BUDG use only where PL1SED (Sort Effective date) equals 20120801 per Brooke Goodrich.
				//sqlString.append(" AND PL1SED <= 20120319 "); //temporary for 20111101 - issues with pricing per Becky Rish
				//sqlString.append("AND PL1SED = 20120801 "); //temporary for Brooke order by not necessary. 11/07/12
				sqlString.append(" ORDER BY PL1YR DESC, PL1MM DESC, PL1DD DESC "); //normal FOQx
				
			}
			
			
			
			if (inRequestType.equals("getCustMaster"))
			{
				ResultSet rsCustItem = (ResultSet) requestClass.elementAt(0);
				sqlString.append("SELECT OKCUNO, OKCFC3 FROM " + library + ".OCUSMA ");
				sqlString.append(" WHERE OKCUNO = '" + rsCustItem.getString("UCCUNO") + "' ");
			}

			
			
			if (inRequestType.equals("getDMPfile"))
			{
				String fileName   = (String) requestClass.elementAt(0);
				String fiscalYear = (String) requestClass.elementAt(1);
				String version    = (String) requestClass.elementAt(2);
				
				sqlString.append("SELECT * FROM " + library + "." + fileName + " ");
				sqlString.append("WHERE OSSSTT = 33 ");
				sqlString.append("AND OSYEA4 = " + fiscalYear + " ");
				sqlString.append("AND OSBVER = '" + version + "' ");
			}
			
			
			
			if (inRequestType.equals("getLBIentry"))
			{
				ResultSet rs = (ResultSet) requestClass.elementAt(0);
				
				sqlString.append("SELECT * FROM " + library + ".O100022 ");
				sqlString.append("WHERE OSCONO = " + rs.getString("OSCONO").trim() + " ");
				sqlString.append("AND OSDIVI = '" + rs.getString("OSDIVI").trim() + "' ");
				sqlString.append("AND OSSSTT = " + rs.getString("OSSSTT").trim() + " ");
				sqlString.append("AND OSBVER = '" + rs.getString("OSBVER").trim() + "' ");
				sqlString.append("AND OSYEA4 = " + rs.getString("OSYEA4").trim() + " ");
				sqlString.append("AND OSPERI = " + rs.getString("OSPERI").trim() + " ");
				sqlString.append("AND UCDIVI = '" + rs.getString("OSDIVI").trim() + "' ");
				sqlString.append("AND UCCUNO = '" + rs.getString("UCCUNO").trim() + "' ");
				sqlString.append("AND UCITNO = '" + rs.getString("UCITNO").trim() + "' ");
			}
			
			
			
			if (inRequestType.equals("getV13Records"))
			{
				String fromFile = (String) requestClass.elementAt(0);
				String recordType = (String) requestClass.elementAt(1);
				String toFile  = (String) requestClass.elementAt(2);
				
				sqlString.append("SELECT  ");
				sqlString.append("OSCONO, OSDIVI, OSSSTT, OSBVER, OSLEVL, OSYEA4, ");
				sqlString.append("OSPERI, UCCUNO, UCITNO, UCIVQT, UCSAAM, UCALBO, ");
				sqlString.append("UCUCOS, MFAVER, MFMADJ, UCDIA1, MFMFOR ");
				sqlString.append("FROM " + library + "." + fromFile + " ");
				
				sqlString.append("LEFT OUTER JOIN " + library + "." + "MITMAS ");
				sqlString.append("ON OSCONO = MMCONO and UCITNO = MMITNO ");
				
				sqlString.append("WHERE OSSSTT = " + recordType + " ");
				
				if (fromFile.toUpperCase().equals("O100028") && toFile.toUpperCase().equals("O100043"))
					sqlString.append("AND MMITCL < '300' ");
				
				if (fromFile.toUpperCase().equals("O100028") && toFile.toUpperCase().equals("O100054"))
					sqlString.append("AND MMITCL > '299' AND MMITCL < '400' ");
				
				if (fromFile.toUpperCase().equals("O100029") || 
					fromFile.toUpperCase().equals("O100030") ||
					fromFile.toUpperCase().equals("O100031") ||
					fromFile.toUpperCase().equals("O100032") ||
					fromFile.toUpperCase().equals("O100033") ||
					fromFile.toUpperCase().equals("O100034") )
					sqlString.append("AND MMITCL < '400' ");
						
				
				sqlString.append("ORDER BY OSCONO, OSDIVI, OSSSTT, OSBVER, OSLEVL, OSYEA4, OSPERI, UCCUNO, UCITNO ");
			}
			
			
			
			if (inRequestType.equals("getV13RecordsWithFiscalYear"))
			{
				String fromFile = (String) requestClass.elementAt(0);
				String fromRecordType = (String) requestClass.elementAt(1);
				String toFile  = (String) requestClass.elementAt(2);
				String fiscalYear = (String) requestClass.elementAt(3);
				
				sqlString.append("SELECT  ");
				sqlString.append("OSCONO, OSDIVI, OSSSTT, OSBVER, OSLEVL, OSYEA4, ");
				sqlString.append("OSPERI, UCCUNO, UCITNO, UCIVQT, UCSAAM, UCALBO, ");
				sqlString.append("UCUCOS, MFAVER, MFMADJ, UCDIA1, MFMFOR ");
				sqlString.append("FROM " + library + "." + fromFile + " ");
				
				sqlString.append("LEFT OUTER JOIN " + library + "." + "MITMAS ");
				sqlString.append("ON OSCONO = MMCONO and UCITNO = MMITNO ");
				
				sqlString.append("WHERE OSSSTT = " + fromRecordType + " ");
				
				sqlString.append("AND OSYEA4 = " + fiscalYear + " ");
				
				if (fromFile.toUpperCase().equals("O100028") && toFile.toUpperCase().equals("O100043"))
					sqlString.append("AND MMITCL < '300' ");
				
				if (fromFile.toUpperCase().equals("O100028") && toFile.toUpperCase().equals("O100054"))
					sqlString.append("AND MMITCL > '299' AND MMITCL < '400' ");
				
				if (fromFile.toUpperCase().equals("O100029") || 
					fromFile.toUpperCase().equals("O100030") ||
					fromFile.toUpperCase().equals("O100031") ||
					fromFile.toUpperCase().equals("O100032") ||
					fromFile.toUpperCase().equals("O100033") ||
					fromFile.toUpperCase().equals("O100034") )
					sqlString.append("AND MMITCL < '400' ");
						
				
				sqlString.append("ORDER BY OSCONO, OSDIVI, OSSSTT, OSBVER, OSLEVL, OSYEA4, OSPERI, UCCUNO, UCITNO ");
			}
			
			   
			   
			   
			if (inRequestType.equals("getSalesHistForDataSet"))
			{
				String fileName = (String) requestClass.elementAt(0);
			   	String sYear    = (String) requestClass.elementAt(1);
			   	String sPeriod  = (String) requestClass.elementAt(2);
			   	String eYear    = (String) requestClass.elementAt(3);
			   	String ePeriod  = (String) requestClass.elementAt(4);
			   	String where = "";
			   	
			   	 if (fileName.equals("tempfile"))//Retail
			   	 {
			   	 	where = "WHERE OKCUCL = '101' ";
			   	 	where = where + "AND MMITCL < '300' ";
			   	 }
			   	 
			   	if (fileName.equals("o100050"))//Ingredient
			   	{
			   		where = "WHERE OKCUCL = '109' ";
			   		where = where + "AND (";
			   		where = where + "MMITCL <> '110' ";
			   		where = where + "AND MMITCL <> '210' ";
//			   		where = where + "AND MMITCL <> '580' ";
			   		where = where + "AND MMITCL <> '739' ";
//			   		where = where + "AND MMITCL <> '800' ) ";
			   		where = where + " ) ";
			   	}

			   	if (fileName.equals("o100052"))//Food Service & Fresh Slice
			   	{
			   		where = "WHERE (MMITTY = '130' ";
			   		where = where + "or ";
			   		where = where + "(OKCUCL = '103' ";
			   		where = where + "And UCITNO <> '1005001001' "; 
			   		where = where + "And UCITNO <> '1000001055' ";
			   		where = where + "And (  (mmitcl >= '300' and mmitcl <= '399') or ";
			   		where = where + "(mmitcl >= '401' and mmitcl <= '564') or ";
			   		where = where + "(mmitcl >= '566' and mmitcl <= '663') or ";
			   		where = where + "(mmitcl >= '665' and mmitcl <= '745') or ";
			   		where = where + "(mmitcl >= '747' and mmitcl <= '799') or ";
			   		where = where + "(mmitcl >= '100' and mmitcl <= '170') or ";    	 	
			   		where = where + "(mmitcl = '210') ) ) )";
			   	 }
			   	 
			   	 if (fileName.equals("o100043"))//Retail
			   	 {
			   	 	where = "WHERE OKCUCL = '101' ";
			   	 	where = where + "AND MMITCL < '300' ";
			   	 }
			   	 
			   	 if (fileName.equals("o100044"))//Military
			   	 {
			   		 where = "WHERE OKCUCL = '104' ";
			   		 where = where + "AND MMITCL < '400' ";
			   	 }
			   	 
			   	 if (fileName.equals("o100045"))//Club
			   	 {
			   		 where = "WHERE OKCUCL = '102' ";
			   		 where = where + "AND MMITCL < '400' ";
			   	 }
			   	 
			   	 if (fileName.equals("o100046"))//Mass Merchandising
			   	 {
			   		 where = "WHERE OKCUCL = '106' ";
			   		 where = where + "AND MMITCL < '400' ";
			   	 }
			   	 
			   	 if (fileName.equals("o100047"))//Dollar Store
			   	 {
			   		 where = "WHERE OKCUCL = '119' ";
			   		 where = where + "AND MMITCL < '400' ";
			   	 }
			   	 
			   	 if (fileName.equals("o100048"))//Export
			   	 {
			   		 where = "WHERE OKCUCL = '110' ";
			   		 where = where + "AND MMITCL < '400' ";
			   	 }
			   	 
			   	 if (fileName.equals("o100049"))//  Natural Foods
			   	 {
			   		 where = "WHERE OKCUCL = '111' ";
			   		 where = where + "AND MMITCL < '400' ";
			   	 }
			   	 
			   	 if (fileName.equals("o100054"))//Private Label
			   	 {
			   		 where = "WHERE OKCUCL = '101' ";
			   		 where = where + "AND MMITCL > '299' and MMITCL < '400' ";
			   	 }
			   	
			   	 if (fileName.equals("o100053"))//Custom Pack
			   	 {
			   		 where = "WHERE OKFACI = '150' ";
			   		 where = where + "AND ucsaam <> 0 ";
			   		 where = where + "AND MMITCL <> '800' ";
			   		 where = where + "AND UCCUNO <> '32074' ";
			   	 }
			   	 
			   	 if (!where.equals(""))
			   	 {
			   		 sqlString.append("SELECT osyea4, osperi, uccuno, ucitno, ucivqt, ucsaam ");
			   		 sqlString.append("FROM " + library + ".o100022 ");
			   		 sqlString.append("left outer join " + library + ".OCUSMA ");
			   		 sqlString.append("on oscono = okcono and uccuno = okcuno ");
			   		 sqlString.append("left outer join " + library + ".MITMAS ");
			   		 sqlString.append("on oscono = mmcono and ucitno = mmitno ");
			   		 
			   		 sqlString.append(where);
			   		 sqlString.append("AND OSYEA4 >= " + sYear + " ");
			   		 sqlString.append("AND OSPERI >= " + sPeriod + " ");
			   		 sqlString.append("AND OSYEA4 <= " + eYear + " ");
			   		 sqlString.append("AND OSPERI <= " + ePeriod + " ");
			   		 sqlString.append("AND OSSSTT = 32 ");
			   	 }
			}
			   
			   
			   
			   if (inRequestType.equals("getDataSet32Record"))
			   {
			   	 String fileName = (String) requestClass.elementAt(0);
			   	 ResultSet rs    = (ResultSet) requestClass.elementAt(1);
			   	 	
			     sqlString.append("SELECT * ");
			     if (fileName.equals("tempfile"))
			    	 sqlString.append("FROM thaile.tempfile ");
			     else
			    	 sqlString.append("FROM " + library + "." + fileName + " ");
			     sqlString.append("WHERE OSCONO = 100 ");
			     sqlString.append("AND OSSSTT = 32 ");
			     sqlString.append("AND OSYEA4 = " + rs.getString("OSYEA4") + " ");
			     sqlString.append("AND OSPERI = " + rs.getString("OSPERI") + " ");
			     sqlString.append("AND UCITNO = '" + rs.getString("UCITNO") + "' ");
			     sqlString.append("AND UCCUNO = '" + rs.getString("UCCUNO") + "' ");
			   }
			
			   
			
			if (inRequestType.equals("findMissingVersion"))
			{
				String fileName = (String) requestClass.elementAt(0);
			   	String fromYear = (String) requestClass.elementAt(1);
			   	String fromType = (String) requestClass.elementAt(2);
			   	String fromVersion = (String) requestClass.elementAt(3);
			   	String addYear = (String) requestClass.elementAt(4);
			   	String addType = (String) requestClass.elementAt(5);
			   	String addVersion = (String) requestClass.elementAt(6);
			   	 	
			    sqlString.append("SELECT a.uccuno as cuno, a.ucitno as itno ");
			    sqlString.append("FROM " + library + "." + fileName + " a ");
			    sqlString.append("where not exists  ");
			    sqlString.append("(select b.uccuno, b.ucitno from " + library + "." + fileName + " b ");
			    sqlString.append("Where a.uccuno = b.uccuno and a.ucitno = b.ucitno ");
			    sqlString.append("and b.osyea4 = " + addYear + " ");
			    sqlString.append("and b.ossstt = " + addType + " ");
			    sqlString.append("and b.osbver = '" + addVersion + "' ");
			    sqlString.append("group by b.uccuno, b.ucitno) ");
			    sqlString.append("and a.osyea4 = " + fromYear + " ");
			    sqlString.append("and a.ossstt = " + fromType + " ");
			    sqlString.append("and a.osbver = '" + fromVersion + "' ");
			    sqlString.append("group by a.uccuno, a.ucitno ");
			}
			
			
			
			if (inRequestType.equals("findComparisionData"))
			{
				String fileName          = (String) requestClass.elementAt(0);
				String dataSetFiscalYear = (String) requestClass.elementAt(1);
				String recordType        = (String) requestClass.elementAt(2);
				String version           = (String) requestClass.elementAt(3);
				String cogsFiscalYear    = (String) requestClass.elementAt(4);
				
				sqlString.append("SELECT ");
				sqlString.append("oscono, osdivi, ossstt, osbver, osyea4, osperi, uccuno, ");
				sqlString.append("ucitno, ucivqt, ucaav1, ucucos, ");
				sqlString.append("t9csu9, t9alu9 ");
				sqlString.append("FROM " + library + "." + fileName + " ");
				sqlString.append("left outer join " + dblib + ".ZTP9CST ");
				sqlString.append("on t9yea4 = " + cogsFiscalYear + " and ucitno = t9itno ");
				sqlString.append("where osyea4 = " + dataSetFiscalYear + " ");
				sqlString.append("and osbver = '" + version + "' ");
				sqlString.append("and ossstt = " + recordType + " ");
			}
			
			
			
			if (inRequestType.equals("getActualsForOverlay"))
			{
				String fiscalYear = (String) requestClass.elementAt(0);
				String fromPeriod = (String) requestClass.elementAt(1);
				
				sqlString.append("select ");
				sqlString.append("oscono, osyea4, osperi, uccuno, ucitno, ");
				sqlString.append("ucivqt, ucsaam, ucucos, ");
				sqlString.append("OKCUCL, MMITCL, T9CSU9, T9ALU9 ");
				sqlString.append("from " + library + ".o100022 ");
				sqlString.append("left outer join " + library + ".ocusma ");
				sqlString.append("on oscono = okcono and uccuno = okcuno ");
				sqlString.append("left outer join " + library + ".mitmas ");
				sqlString.append("on oscono = mmcono and ucitno = mmitno ");
				sqlString.append("left outer join " + dblib + ".ztp9cst ");
				sqlString.append("on osyea4 = t9yea4 and ucitno = t9itno ");
				sqlString.append("where osyea4 = " + fiscalYear + " ");
				sqlString.append("and osperi = " + fromPeriod + " ");
				sqlString.append("and ossstt = 32 ");
				sqlString.append("and ucivqt <> 0 ");
			}
			
			
			
			if (inRequestType.equals("getMatchingForecastEntry"))
			{
				String fiscalYear    = (String) requestClass.elementAt(0);
				String fromPeriod    = (String) requestClass.elementAt(1);
				String recordType    = (String) requestClass.elementAt(2);
				String budgetVersion = (String) requestClass.elementAt(3);
				String custNo        = (String) requestClass.elementAt(4);
				String itemNo        = (String) requestClass.elementAt(5);
				
				sqlString.append("select ");
				sqlString.append("oscono, ossstt, osbver, osyea4, osperi, ");
				sqlString.append("uccuno, ucitno, ucivqt, ucofqs, ");
				sqlString.append("ucsaam, ucucos, ucvol3 ");
				sqlString.append("from " + library + ".o100022 ");
				sqlString.append("where osyea4 = " + fiscalYear + " ");
				sqlString.append("and osperi = " + fromPeriod + " ");
				sqlString.append("and ossstt = " + recordType + " ");
				sqlString.append("and osbver = '" + budgetVersion + "' ");
				sqlString.append("and uccuno = '" + custNo.trim() + "' ");
				sqlString.append("and ucitno = '" + itemNo.trim() + "' ");
			}
			
			
			
			//******************************************************************
			//*** INSERT SECTION
			//******************************************************************
			
			if (inRequestType.equals("insertAudit"))
			{
				UpdDataSet uds = (UpdDataSet) requestClass.elementAt(0); // requested Values
				StringBuffer sb = (StringBuffer) requestClass.elementAt(1); // Determine Complete or Incomplete
				sqlString.append("INSERT INTO " + dblib + ".DSPAUDIT ");
				sqlString.append("VALUES(");
				sqlString.append("'" + uds.getDataSetName().trim() + "', "); 
				sqlString.append("'" + uds.getCostYear().trim() + "', "); 
				sqlString.append("'" + uds.getDataSetYear().trim() + "', ");
				sqlString.append("'" + uds.getRecordType().trim() + "', ");
				sqlString.append("'" + uds.getComment().trim() + "', ");
				
				if (sb.toString().trim().equals(""))
					sqlString.append("'Complete', ");
				else
					sqlString.append("'Incomplete', ");
				
				sqlString.append("' ', "); // Misc Field 0
				sqlString.append("' ', "); // Misc Field 1
				sqlString.append("' ', "); // Misc Field 2
				sqlString.append("' ', "); // Misc Field 3
				sqlString.append("' ', "); // Misc Field 4
				sqlString.append("' ', "); // Misc Field 5
				sqlString.append("' ', "); // Misc Field 6
				sqlString.append("' ', "); // Misc Field 7
				sqlString.append("' ', "); // Misc Field 8
				sqlString.append("' ', "); // Misc Field 9
				// get actual Date and Time
				DateTime dt = UtilityDateTime.getSystemDate();
				sqlString.append(dt.getDateFormatyyyyMMdd().trim() + ", ");
				sqlString.append(dt.getTimeFormathhmmss().trim() + ", ");
				sqlString.append("'" + uds.getUpdateUser() + "' ");
				sqlString.append(")");
			}
			
			
			
			if (inRequestType.equals("addLBIentry"))
			{
				ResultSet rs		= (ResultSet) requestClass.elementAt(0);
				BigDecimal lineAmt	= (BigDecimal) requestClass.elementAt(1);
				BigDecimal bkrAmt	= (BigDecimal) requestClass.elementAt(2);
				String volumeOnly   = (String) requestClass.elementAt(3);
				
				sqlString.append("INSERT INTO " + library + ".O100022 ");
				sqlString.append("(OSCONO,OSDIVI,OSSSTT,OSBVER,OSLEVL,OSYEA4,");
				sqlString.append(" OSPERI,UCDIVI,UCWHLO,UCCUNO,UCITNO,");
				sqlString.append(" UCIVQT,UCOFQS,UCGRWE,UCNEWE,UCVOL3,UCSGAM,");
				sqlString.append(" UCSAAM,UCOFRA,UCDIA1,UCDIA2,UCDIA3,UCDIA4,");
				sqlString.append(" UCDIA5,UCDIA6,UCALBO,UCUCOS,UCDCOS,UCONK1,");
				sqlString.append(" UCONK2,UCONK3,UCONK4,UCONK5) ");
				sqlString.append("VALUES(");
				sqlString.append(rs.getString("OSCONO").trim() + ", ");//oscono
				sqlString.append("'" + rs.getString("OSDIVI").trim() + "', ");//osdivi
				sqlString.append(rs.getString("OSSSTT").trim() + ", ");//ossstt
				sqlString.append("'" + rs.getString("OSBVER").trim() + "', ");//osbver
				sqlString.append(rs.getString("OSLEVL").trim() + ", ");//oslevl
				sqlString.append(rs.getString("OSYEA4").trim() + ", ");//osyea4
				sqlString.append(rs.getString("OSPERI").trim() + ", ");//osperi
				sqlString.append("'" + rs.getString("OSDIVI").trim() + "', ");//ucdivi
				sqlString.append("'209', ");//ucwhlo
				sqlString.append("'" + rs.getString("UCCUNO").trim() + "', ");//uccuno
				sqlString.append("'" + rs.getString("UCITNO").trim() + "', ");//ucitno
				sqlString.append(rs.getString("UCIVQT").trim() + ", ");//ucivqt
				sqlString.append(rs.getString("UCIVQT").trim() + ", ");//ucofqs
				sqlString.append("0, ");//UCGRWE
				sqlString.append("0, ");//UCNEWE
				sqlString.append("0, ");//UCVOL3
				sqlString.append("0, ");//UCSGAM
				
				if(volumeOnly.equals("true"))
				{
					sqlString.append("0, ");//UCSAAM
					sqlString.append("0, ");//UCOFRA
				} else
				{
					sqlString.append(lineAmt.toString().trim() + ", ");//UCSAAM
					sqlString.append(bkrAmt.toString().trim() + ", ");//UCOFRA
				}
				
				sqlString.append("0, ");//ucdia1
				sqlString.append("0, ");//UCDIA2
				sqlString.append("0, ");//UCDIA3
				sqlString.append("0, ");//UCDIA4
				sqlString.append("0, ");//UCDIA5
				sqlString.append("0, ");//UCDIA6
				if(volumeOnly.equals("true"))
				{
					sqlString.append("0, ");//UCALBO
					sqlString.append("0, ");//ucucos
				} else
				{
					sqlString.append(rs.getString("UCALBO").trim() + ", ");//UCALBO
					sqlString.append(rs.getString("UCUCOS").trim() + ", ");//ucucos
				}
				
				sqlString.append("0, ");//UCDCOS
				sqlString.append("0, ");//UCONK1
				sqlString.append("0, ");//UCONK2
				sqlString.append("0, ");//UCONK3
				sqlString.append("0, ");//UCONK4
				sqlString.append("0 ");//UCONK5
				sqlString.append(") ");
			}
			
			
			
			if (inRequestType.equals("addV14Records"))
			{
				ResultSet rs	= (ResultSet) requestClass.elementAt(0);
				String toFile	= (String) requestClass.elementAt(1);
				
				
				sqlString.append("INSERT INTO " + library + "." + toFile + " ");
				sqlString.append("(OSCONO,OSDIVI,OSSSTT,OSBVER,OSLEVL,OSYEA4,");
				sqlString.append(" OSPERI,UCCUNO,UCITNO,UCIVQT,UCSAAM,UCALBO,");
				sqlString.append(" UCUCOS,UCAAV1,UCAAV2,UCAAV3,UCAAV4) ");
				
				sqlString.append("VALUES(");
				sqlString.append(rs.getString("OSCONO").trim() + ", ");//oscono
				sqlString.append("'" + rs.getString("OSDIVI").trim() + "', ");//osdivi
				sqlString.append(rs.getString("OSSSTT").trim() + ", ");//ossstt
				sqlString.append("'" + rs.getString("OSBVER").trim() + "', ");//osbver
				sqlString.append(rs.getString("OSLEVL").trim() + ", ");//oslevl
				sqlString.append(rs.getString("OSYEA4").trim() + ", ");//osyea4
				sqlString.append(rs.getString("OSPERI").trim() + ", ");//osperi
				sqlString.append("'" + rs.getString("UCCUNO").trim() + "', ");//uccuno
				sqlString.append("'" + rs.getString("UCITNO").trim() + "', ");//ucitno
				
				if (rs.getString("OSSSTT").trim().equals("32"))
					sqlString.append(rs.getString("UCIVQT").trim() + ", ");//ucivqt
				else
					sqlString.append(rs.getString("MFMFOR").trim() + ", ");//ucivqt
				
				sqlString.append(rs.getString("UCSAAM").trim() + ", ");//ucsaam
				sqlString.append(rs.getString("UCALBO").trim() + ", ");//ucalbo
				sqlString.append(rs.getString("UCUCOS").trim() + ", ");//ucucos
				sqlString.append(rs.getString("MFAVER").trim() + ", ");//ucaav1
				sqlString.append(rs.getString("MFMADJ").trim() + ", ");//ucaav2
				sqlString.append(rs.getString("UCDIA1").trim() + ", ");//ucaav3
				sqlString.append("0 ");//ucaav4
				sqlString.append(") ");
			}
			
			
			
			if (inRequestType.equals("addV14RecordsFrom34To33"))
			{
				ResultSet rs	    = (ResultSet) requestClass.elementAt(0);
				String toFile	    = (String) requestClass.elementAt(1);
				String toRecordType = (String) requestClass.elementAt(2);
				String version      = (String) requestClass.elementAt(3);	
				
				sqlString.append("INSERT INTO " + library + "." + toFile + " ");
				sqlString.append("(OSCONO,OSDIVI,OSSSTT,OSBVER,OSLEVL,OSYEA4,");
				sqlString.append(" OSPERI,UCCUNO,UCITNO,UCIVQT,UCSAAM,UCALBO,");
				sqlString.append(" UCUCOS,UCAAV1,UCAAV2,UCAAV3,UCAAV4) ");
				
				sqlString.append("VALUES(");
				sqlString.append(rs.getString("OSCONO").trim() + ", ");//oscono
				sqlString.append("'" + rs.getString("OSDIVI").trim() + "', ");//osdivi
				sqlString.append(toRecordType + ", ");//ossstt
				sqlString.append("'" + version + "', ");//osbver
				sqlString.append(rs.getString("OSLEVL").trim() + ", ");//oslevl
				sqlString.append(rs.getString("OSYEA4").trim() + ", ");//osyea4
				sqlString.append(rs.getString("OSPERI").trim() + ", ");//osperi
				sqlString.append("'" + rs.getString("UCCUNO").trim() + "', ");//uccuno
				sqlString.append("'" + rs.getString("UCITNO").trim() + "', ");//ucitno
				sqlString.append(rs.getString("MFMFOR").trim() + ", ");//ucivqt
				sqlString.append(rs.getString("UCSAAM").trim() + ", ");//ucsaam
				sqlString.append(rs.getString("UCALBO").trim() + ", ");//ucalbo
				sqlString.append(rs.getString("UCUCOS").trim() + ", ");//ucucos
				sqlString.append(rs.getString("MFAVER").trim() + ", ");//ucaav1
				sqlString.append(rs.getString("MFMADJ").trim() + ", ");//ucaav2
				sqlString.append(rs.getString("UCDIA1").trim() + ", ");//ucaav3
				sqlString.append("0 ");//ucaav4
				sqlString.append(") ");
			}
			
			
			
			if (inRequestType.equals("addDataSet32From22"))
			{
				String fileName      = (String) requestClass.elementAt(0);
			   	ResultSet rs         = (ResultSet) requestClass.elementAt(1);
			   	BigDecimal price     = (BigDecimal) requestClass.elementAt(2);
			   	BigDecimal ivqt	     = (BigDecimal) requestClass.elementAt(3);
			   	
			   	if (fileName.equals("tempfile"))
			   		sqlString.append("INSERT INTO thaile.tempfile ");
			   	else
			   		sqlString.append("INSERT INTO " + library + "." + fileName + " ");
			   	
			    sqlString.append("VALUES (");
			    sqlString.append("100, ");
				sqlString.append("'', " );
				sqlString.append("32, ");
				sqlString.append("'', ");
				sqlString.append("0, ");
				sqlString.append(rs.getString("OSYEA4") + ", ");
				sqlString.append(rs.getString("OSPERI") + ", ");
				sqlString.append("'" + rs.getString("UCCUNO") + "', ");
				sqlString.append("'" + rs.getString("UCITNO") + "', ");
				  
			  	sqlString.append(ivqt.toString() + ", ");//UCIVQT
			  	sqlString.append(rs.getString("UCSAAM") + ", ");//UCSAAM
				sqlString.append("0, ");//UCALBO
				sqlString.append("0, ");//UCUCOS
				sqlString.append("0, ");//UCAAV1
				sqlString.append(price.toString() + ", ");//UCAAV2
				sqlString.append("0, ");//UCAAV3
				sqlString.append("0 ");//UCAAV4
			    sqlString.append(" )");
			}
			
			
			
			if (inRequestType.equals("addMissingVersion"))
			{
				
				String fileName	    = (String) requestClass.elementAt(0);
				String addYear      = (String) requestClass.elementAt(1);
				String addType      = (String) requestClass.elementAt(2);
				String addVersion   = (String) requestClass.elementAt(3);
				ResultSet rs	    = (ResultSet) requestClass.elementAt(4);
				
				sqlString.append("INSERT INTO " + library + "." + fileName + " ");
				sqlString.append("(OSCONO,OSDIVI,OSSSTT,OSBVER,OSLEVL,OSYEA4,");
				sqlString.append(" OSPERI,UCCUNO,UCITNO,UCIVQT,UCSAAM,UCALBO,");
				sqlString.append(" UCUCOS,UCAAV1,UCAAV2,UCAAV3,UCAAV4) ");
				
				sqlString.append("VALUES(");
				sqlString.append("100,");//oscono
				sqlString.append("'', ");//osdivi
				sqlString.append(addType + ", ");//ossstt
				sqlString.append("'" + addVersion + "', ");//osbver
				sqlString.append("0, ");//oslevl
				sqlString.append(addYear + ", ");//osyea4
				sqlString.append("1, ");//osperi
				sqlString.append("'" + rs.getString("CUNO").trim() + "', ");//uccuno
				sqlString.append("'" + rs.getString("ITNO").trim() + "', ");//ucitno
				sqlString.append("0, ");//ucivqt
				sqlString.append("0, ");//ucsaam
				sqlString.append("0, ");//ucalbo
				sqlString.append("0, ");//ucucos
				sqlString.append("0, ");//ucaav1
				sqlString.append("0, ");//ucaav2
				sqlString.append("0, ");//ucaav3
				sqlString.append("0 ");//ucaav4
				sqlString.append(") ");
			}
			
			
			
			if (inRequestType.equals("addForecastVarianceRecord"))
			{
				String cono  	     = (String) requestClass.elementAt(0);
				String recordType    = (String) requestClass.elementAt(1);
				String budgetVersion = (String) requestClass.elementAt(2);
				String fiscalYear	 = (String) requestClass.elementAt(3);
				String fiscalPeriod  = (String) requestClass.elementAt(4);
				String custNo   	 = (String) requestClass.elementAt(5);
				String itemNo	     = (String) requestClass.elementAt(6);
				BigDecimal qty	     = (BigDecimal) requestClass.elementAt(7);
				String cogs	         = (String) requestClass.elementAt(8);
				
				sqlString.append("INSERT INTO " + library + ".O100022 ");
				sqlString.append("(OSCONO,OSDIVI,OSSSTT,OSBVER,OSLEVL,OSYEA4,");
				sqlString.append(" OSPERI,UCDIVI,UCWHLO,UCCUNO,UCITNO,");
				sqlString.append(" UCIVQT,UCOFQS,UCGRWE,UCNEWE,UCVOL3,UCSGAM,");
				sqlString.append(" UCSAAM,UCOFRA,UCDIA1,UCDIA2,UCDIA3,UCDIA4,");
				sqlString.append(" UCDIA5,UCDIA6,UCALBO,UCUCOS,UCDCOS,UCONK1,");
				sqlString.append(" UCONK2,UCONK3,UCONK4,UCONK5) ");
				sqlString.append("VALUES(");
				sqlString.append(cono + ", ");//oscono
				sqlString.append("'', ");//osdivi
				sqlString.append(recordType + ", ");//ossstt
				sqlString.append("'" + budgetVersion + "', ");//osbver
				sqlString.append("0, ");//oslevl
				sqlString.append(fiscalYear + ", ");//osyea4
				sqlString.append(fiscalPeriod + ", ");//osperi
				sqlString.append("'', ");//ucdivi
				sqlString.append("'209', ");//ucwhlo
				sqlString.append("'" + custNo + "', ");//uccuno
				sqlString.append("'" + itemNo + "', ");//ucitno
				sqlString.append("0, ");//ucivqt
				sqlString.append("0, ");//ucofqs
				sqlString.append("0, ");//UCGRWE
				sqlString.append("0, ");//UCNEWE
				sqlString.append(qty.toString() + ", ");//UCVOL3
				sqlString.append("0, ");//UCSGAM
				sqlString.append("0, ");//UCSAAM
				sqlString.append("0, ");//UCOFRA
				sqlString.append("0, ");//ucdia1
				sqlString.append("0, ");//UCDIA2
				sqlString.append("0, ");//UCDIA3
				sqlString.append("0, ");//UCDIA4
				sqlString.append("0, ");//UCDIA5
				sqlString.append("0, ");//UCDIA6
				sqlString.append("0, ");//UCALBO
				sqlString.append(cogs + ", ");//ucucos
				sqlString.append("0, ");//UCDCOS
				sqlString.append("0, ");//UCONK1
				sqlString.append("0, ");//UCONK2
				sqlString.append("0, ");//UCONK3
				sqlString.append("0, ");//UCONK4
				sqlString.append("0 ");//UCONK5
				sqlString.append(") ");
			}
			
				

			
			//******************************************************************
			//*** UPDATE SECTION
			//******************************************************************
			
//wth 07/27/2011			if (inRequestType.equals("updateCompanyCost"))
//wth 07/27/2011			{
//wth 07/27/2011				// Deals with 
//wth 07/27/2011				//	  	  0 = file Name
//wth 07/27/2011				//		  1 = Item Number
//wth 07/27/2011				//		  2 = Year
//wth 07/27/2011				//		  3 = Value
//wth 07/27/2011				//		  4 = Type
//wth 07/27/2011				sqlString.append("UPDATE " + library + "." + requestClass.elementAt(0) + " ");
//wth 07/27/2011				sqlString.append(" SET UCUCOS = " + requestClass.elementAt(3) + " ");
//wth 07/27/2011				sqlString.append("WHERE UCITNO = '" + requestClass.elementAt(1) + "' ");
//wth 07/27/2011				sqlString.append("  AND OSYEA4 = " + requestClass.elementAt(2) + " ");
				
//wth 07/27/2011				if (!requestClass.elementAt(4).equals("both"))
//wth 07/27/2011					sqlString.append(" AND OSSSTT = " + requestClass.elementAt(4));
//wth 07/27/2011			}
			
			
			
			if (inRequestType.equals("updateDsCompanyCost"))
			{
				// Deals with 
				//	  	  0 = file Name
				//		  1 = Item Number
				//		  2 = Year
				//		  3 = Type
				//		  4 = Value
				//        5 = Budget Version
				sqlString.append("UPDATE " + library + "." + requestClass.elementAt(0) + " ");
				sqlString.append(" SET UCUCOS = " + requestClass.elementAt(4) + " ");
				sqlString.append("WHERE OSCONO = 100 ");
				sqlString.append("AND UCITNO = '" + requestClass.elementAt(1) + "' ");
				sqlString.append("  AND OSYEA4 = " + requestClass.elementAt(2) + " ");
				sqlString.append(" AND OSSSTT = " + requestClass.elementAt(3) + " ");
				sqlString.append("AND OSBVER = '" + requestClass.elementAt(5) + "' ");
					
			}
			
			
			
			if (inRequestType.equals("update22FileCompanyCost"))
			{
				String fileName	= (String) requestClass.elementAt(0);
				String cono		= (String) requestClass.elementAt(1);
				String yea4		= (String) requestClass.elementAt(2);
				String sstt		= (String) requestClass.elementAt(3);
				String bver		= (String) requestClass.elementAt(4);
				String itno		= (String) requestClass.elementAt(5);
				String cucl		= (String) requestClass.elementAt(6);
				String itcl		= (String) requestClass.elementAt(7);
				String amt		= (String) requestClass.elementAt(8);

				sqlString.append("UPDATE " + library + "." + fileName + " ");
				sqlString.append(" SET UCUCOS = " + amt + " ");
				sqlString.append("WHERE OSCONO = " + cono + " ");
				sqlString.append("AND OSYEA4 = " + yea4 + " ");
				sqlString.append("AND OSSSTT = " + sstt + " ");
				sqlString.append("AND OSBVER = '" + bver + "' ");
				sqlString.append("AND UCITNO = '" + itno + "' ");
			}
			
			
			
			if (inRequestType.equals("updateAvgCost"))
			{
				UpdDataSet fromVb = (UpdDataSet) requestClass.elementAt(0);
				String fileName = fromVb.getDataSetName();
				String lastCustomer = (String) requestClass.elementAt(1);
				String lastItemNo = (String) requestClass.elementAt(2);
				BigDecimal avgCost = (BigDecimal) requestClass.elementAt(3);
				String budgetYear = fromVb.getBudgetYear();
				
				sqlString.append(" UPDATE " + library + "." + fileName + " SET MFTREQ = ");
				sqlString.append(avgCost.toString() + " ");
				sqlString.append(" WHERE UCCUNO = '" + lastCustomer + "' ");
				sqlString.append(" AND UCITNO = '" + lastItemNo + "' ");
				sqlString.append(" AND OSYEA4 = " + budgetYear + " ");
				sqlString.append(" AND OSSSTT = " + "34 "  );
			}
			
			
			
			if (inRequestType.equals("updateRevenuePrice"))
			{
				String fileName		 = (String) requestClass.elementAt(0);
				ResultSet rsCustItem = (ResultSet) requestClass.elementAt(1);
				BigDecimal revAmt    = (BigDecimal) requestClass.elementAt(2);
				String columnNo      = (String) requestClass.elementAt(3);
				BigDecimal fgtAmt    = (BigDecimal) requestClass.elementAt(4);
				String periodStart   = (String) requestClass.elementAt(5);
				String periodEnd     = (String) requestClass.elementAt(6);
				
				sqlString.append(" UPDATE " + library + "." + fileName );
				sqlString.append(" SET UCAAV2 = " + revAmt.toString() + " "); //normal price field
				//sqlString.append(" SET UCAAV4 = " + revAmt.toString() + " "); //temporary for BUDG
				
				sqlString.append("WHERE UCCUNO = '" + rsCustItem.getString("UCCUNO") + "' ");
				sqlString.append("AND UCITNO = '" + rsCustItem.getString("UCITNO") + "' ");
				sqlString.append("AND OSYEA4 = " + rsCustItem.getString("OSYEA4") + " ");
				sqlString.append("AND OSPERI >= " + periodStart + " ");
				sqlString.append("AND OSPERI <= " + periodEnd + " ");
				sqlString.append("AND OSSSTT = " + rsCustItem.getString("OSSSTT") + " ");
				sqlString.append("AND OSBVER = '" + rsCustItem.getString("OSBVER") + "' ");
			}
			
			
			
			if (inRequestType.equals("updateBrokerageRate"))
			{
				String fileName		= (String) requestClass.elementAt(0);
				String cust			= (String) requestClass.elementAt(1);
				String item			= (String) requestClass.elementAt(2);
				String year			= (String) requestClass.elementAt(3);
				String period		= (String) requestClass.elementAt(4);
				BigDecimal rate		= (BigDecimal) requestClass.elementAt(5);
				BigDecimal rateF    = (BigDecimal) requestClass.elementAt(6);
				String recordType   = (String) requestClass.elementAt(7);
				String version      = (String) requestClass.elementAt(8);
				
				sqlString.append("UPDATE " + library + "." + fileName + " ");
				sqlString.append("SET UCAAV1 = " + rateF.toString() + " ");
				//sqlString.append(" MFAVER = " + rateF.toString() + " ");
				sqlString.append("WHERE UCCUNO = '" + cust + "' ");
				sqlString.append("AND UCITNO = '" + item + "' ");
				sqlString.append("AND OSYEA4 = " + year + " ");
				sqlString.append("AND OSPERI = " + period + " ");
				sqlString.append("AND OSSSTT = " + recordType + " ");
				sqlString.append("AND OSBVER = '" + version + "' ");
			}
			
			
			
			if (inRequestType.equals("updateLBIentry"))
			{
				ResultSet rs	   = (ResultSet) requestClass.elementAt(0);
				BigDecimal qty     = (BigDecimal) requestClass.elementAt(1);
				BigDecimal bkrAmt  = (BigDecimal) requestClass.elementAt(2);
				BigDecimal bkrRate = (BigDecimal) requestClass.elementAt(3);
				BigDecimal lineAmt = (BigDecimal) requestClass.elementAt(4);
				
				sqlString.append("UPDATE " + library + ".o100022 ");
				sqlString.append("SET UCIVQT = " + qty.toString().trim() + ", ");
				sqlString.append("UCOFQS = " + qty.toString().trim() + ", ");
				sqlString.append("UCALBO = " + bkrAmt.toString().trim() + ", ");
				sqlString.append("UCDIA1 = " + bkrRate.toString().trim() + ", ");
				sqlString.append("UCSAAM = " + lineAmt.toString().trim() + ", ");
				sqlString.append("UCWHLO = '240' ");
				sqlString.append("WHERE OSCONO = " + rs.getString("OSCONO").trim() + " ");
				sqlString.append("AND UCDIVI = '" + rs.getString("UCDIVI").trim() + "' ");
				sqlString.append("AND OSSSTT = " + rs.getString("OSSSTT").trim() + " ");
				sqlString.append("AND OSBVER = '" + rs.getString("OSBVER").trim() + "' ");
				sqlString.append("AND OSYEA4 = " + rs.getString("OSYEA4").trim() + " ");
				sqlString.append("AND OSPERI = " + rs.getString("OSPERI").trim() + " ");
				sqlString.append("AND UCDIVI = '" + rs.getString("UCDIVI").trim() + "' ");
				sqlString.append("AND UCCUNO = '" + rs.getString("UCCUNO").trim() + "' ");
				sqlString.append("AND UCITNO = '" + rs.getString("UCITNO").trim() + "' ");
			}
			
			
			
			if (inRequestType.equals("updateDataSet32From22file"))
			{
			   	String fileName = (String) requestClass.elementAt(0);
			   	ResultSet rs    = (ResultSet) requestClass.elementAt(1);
			   	BigDecimal ivqt = (BigDecimal) requestClass.elementAt(2);
			   	BigDecimal saam = (BigDecimal) requestClass.elementAt(3);
			   	BigDecimal madj = (BigDecimal) requestClass.elementAt(4);
			   	
			   	if (fileName.equals("tempfile"))
			   		sqlString.append("UPDATE thaile.tempfile ");
			   	else
			   		sqlString.append("UPDATE " + library + "." + fileName + " ");
			   	
				sqlString.append("SET UCIVQT = " + ivqt.toString() + ", ");
				sqlString.append("UCSAAM = " + saam.toString() + ", ");  
				sqlString.append("UCAAV2 = " + madj.toString() + " "); 
				sqlString.append("WHERE OSCONO = 100 ");
				sqlString.append("AND OSSSTT = 32 ");
				sqlString.append("AND OSYEA4 = " + rs.getString("OSYEA4") + " ");
				sqlString.append("AND OSPERI = " + rs.getString("OSPERI") + " ");
				sqlString.append("AND UCITNO = '" + rs.getString("UCITNO") + "' ");
				sqlString.append("AND UCCUNO = '" + rs.getString("UCCUNO") + "' ");
			}
			
			
			
			if (inRequestType.equals("updateVersionTo32"))
			{
			   	String fileName    = (String) requestClass.elementAt(0);
			   	String version     = (String) requestClass.elementAt(1);
			   	String fromYear    = (String) requestClass.elementAt(2);
			   	String fromPeriod  = (String) requestClass.elementAt(3);
			   	String toYear      = (String) requestClass.elementAt(4);
			   	String toPeriod    = (String) requestClass.elementAt(5);
			   	
			   	sqlString.append("UPDATE " + library + "." + fileName + " ");
				sqlString.append("SET OSSSTT = 32, ");
				sqlString.append("UCIVQT = 0, ");
				sqlString.append("UCSAAM = 0, ");  
				sqlString.append("UCAAV2 = 0 "); 
				sqlString.append("WHERE OSCONO = 100 ");
				sqlString.append("AND OSSSTT = 33 ");
				sqlString.append("AND OSBVER = '" + version + "' ");
				sqlString.append("AND OSYEA4 >= " + fromYear + " ");
				sqlString.append("AND OSPERI >= " + fromPeriod + " ");
				sqlString.append("AND OSYEA4 <= " + toYear + " ");
				sqlString.append("AND OSPERI <= " + toPeriod + " ");
			}
			
			
			
			if (inRequestType.equals("updateVersionBackTo33"))
			{
			   	String fileName    = (String) requestClass.elementAt(0);
			   	String version     = (String) requestClass.elementAt(1);
			   	String fromYear    = (String) requestClass.elementAt(2);
			   	String fromPeriod  = (String) requestClass.elementAt(3);
			   	String toYear      = (String) requestClass.elementAt(4);
			   	String toPeriod    = (String) requestClass.elementAt(5);
			   	
			   	sqlString.append("UPDATE " + library + "." + fileName + " ");
				sqlString.append("SET OSSSTT = 33, ");
				sqlString.append("OSBVER = '" + version + "' ");
				//sqlString.append("UCSAAM = 0 ");   				//09262012 leave the sales dollars now.
				sqlString.append("WHERE OSCONO = 100 ");
				sqlString.append("AND OSSSTT = 32 ");
				sqlString.append("AND OSYEA4 >= " + fromYear + " ");
				sqlString.append("AND OSPERI >= " + fromPeriod + " ");
				sqlString.append("AND OSYEA4 <= " + toYear + " ");
				sqlString.append("AND OSPERI <= " + toPeriod + " ");
			}
			
			
			
			if (inRequestType.equals("updateForecastVariance"))
			{
				ResultSet rs = (ResultSet) requestClass.elementAt(0);
				BigDecimal qty = (BigDecimal) requestClass.elementAt(1);
				
				sqlString.append("UPDATE " + library + ".o100022 ");
				sqlString.append("set UCVOL3 = " + qty.toString() + " ");
				sqlString.append("where oscono = " + rs.getString("oscono") + " ");
				sqlString.append("and ossstt = " + rs.getString("ossstt") + " ");
				sqlString.append("and osbver = '" + rs.getString("osbver") + "' ");
				sqlString.append("and osyea4 = " + rs.getString("osyea4") + " ");
				sqlString.append("and osperi = " + rs.getString("osperi") + " ");
				sqlString.append("and uccuno = '" + rs.getString("uccuno") + "' ");
				sqlString.append("and ucitno = '" + rs.getString("ucitno") + "' ");
			}
			
			
			
			if (inRequestType.equals("setUcvol3ToUcivqtForVersion"))
			{
				String fiscalYear    = (String) requestClass.elementAt(0);
				String recordType    = (String) requestClass.elementAt(1);
				String budgetVersion = (String) requestClass.elementAt(2);
				
				sqlString.append("update " + library + ".o100022 ");
				sqlString.append("set ucvol3 = ucivqt ");
				sqlString.append("where osyea4 = " + fiscalYear + " ");
				sqlString.append("and ossstt = " + recordType + " ");
				sqlString.append("and osbver = '" + budgetVersion + "' ");
			}
			
			
			
			if (inRequestType.equals("delete33s"))
			{
				String fiscalYear = (String) requestClass.elementAt(0);
				String version    = (String) requestClass.elementAt(1);
				
				sqlString.append("DELETE FROM " + library + ".o100022 ");
				sqlString.append("WHERE OSSSTT = 33 ");
				sqlString.append("AND OSYEA4 = " + fiscalYear + " ");
				sqlString.append("AND OSBVER = '" + version + "' ");
			}
			
			
			
			if (inRequestType.equals("deleteSalesHistoryFromVersion"))
			{
				String fileName    = (String) requestClass.elementAt(0);
				String fromYear    = (String) requestClass.elementAt(1);
				String fromPeriod  = (String) requestClass.elementAt(2);
				String toYear      = (String) requestClass.elementAt(3);
				String toPeriod    = (String) requestClass.elementAt(4);
				
				sqlString.append("DELETE FROM " + library + "." + fileName + " ");
				sqlString.append("WHERE OSSSTT = 32 ");
				sqlString.append("AND OSYEA4 >= " + fromYear + " ");
				sqlString.append("AND OSPERI >= " + fromPeriod + " ");
				sqlString.append("AND OSYEA4 <= " + toYear + " ");
				sqlString.append("AND OSPERI <= " + toPeriod + " ");
			}
			
			
			
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for request type " + inRequestType + ". ");
		}
		
		
		
		if (inRequestType.equals("deleteComparisionData"))
		{
			String chanel             = (String) requestClass.elementAt(0);
			String dataSetFiscalYear = (String) requestClass.elementAt(1);
			String recordType         = (String) requestClass.elementAt(2);
			String version            = (String) requestClass.elementAt(3);
			
			sqlString.append("DELETE FROM " + dblib + ".BAPBDMP1 ");
			sqlString.append("WHERE BBCHNL = '" + chanel + "' " );
			sqlString.append("AND BBYEA4 = " + dataSetFiscalYear + " ");
			sqlString.append("AND BBSSTT = " + recordType + " ");
			sqlString.append("AND BBBVER = '" + version + "' ");
		}
		
		
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServicePlanning.");
			throwError.append("buildSqlStatement(String, Vector)");
			throw new Exception(throwError.toString());
		}
		
		String x = fieldList.toString() + sqlString.toString();
		return fieldList.toString() + sqlString.toString();
	}
	/**
	 * Load class fields from result set.
	 */
	private static DataSet loadFields(String loadType,
								      ResultSet rs)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		DataSet		 rtnValue   = new DataSet();
		// Not Currently using loadType
		try
		{
			// from file OSSSET
			if (loadType.trim().equals("datasetName") ||
				loadType.trim().equals("auditLog"))
			{ 
				rtnValue.setFileName(rs.getString("SCFILE").trim());
				rtnValue.setDataSetID(rs.getString("SCSBDS").trim());
				rtnValue.setDescription(rs.getString("SCTX40").trim());
				rtnValue.setName(rs.getString("SCTX15").trim());
			}
			if (loadType.trim().equals("datasetYear"))
			{ 
				rtnValue.setYearDataSet(rs.getString("OSYEA4").trim());
			}
			// from file DSPAUDIT
			if (loadType.trim().equals("auditLog"))
			{ 
				rtnValue.setYearFrom(rs.getString("DSAYEARFR").trim());
				rtnValue.setYearTo(rs.getString("DSAYEARTO").trim());
				rtnValue.setRecordType(rs.getString("DSARECTYPE").trim());
				rtnValue.setComment(rs.getString("DSACOMMENT").trim());
				rtnValue.setCompletionStatus(rs.getString("DSASTATUS").trim());
				rtnValue.setUpdateDate(rs.getString("DSADATE"));
				rtnValue.setUpdateTime(rs.getString("DSATIME"));
				rtnValue.setUpdateUser(rs.getString("DSAUSER").trim());
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the class  ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceDataSet.");
			throwError.append("loadFields(loadType:");
			throwError.append(loadType + ", rs). ");
			throw new Exception(throwError.toString());
		}
		return rtnValue;
	}
	
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		// Test all that you can.
		String stophere = "";  
		Vector vector = null;
		String temp = "temp";
		
		try {
		//*** TEST "buildListLotPayment(String)".
			
			
			//Update Brokerage field for Ingredient data set only
			// Modify UCAAV1 with latest brokerage percent paid on latest invoice.
			if ("x".equals("y"))
			{
						
				// add missing entries.
				if (1 == 1) {
					String fileName = "o100052";
					String fromYear = "2014";
					String sPeriod = "1";
					String ePeriod = "2";
					String version = "FOQ1";
					updateBrokerageRate(fileName,fromYear,sPeriod,ePeriod,version);
					stophere = "x";
				}
			}
			
			
			//Add additional customer item records to data sets
			// Add entries that exist in 32's but not in 33's
			if ("x".equals("y"))
			{
						
				// add missing entries.
				if (1 == 1) {
					String fileName = "o100052";
					String fromYear = "2012";
					String fromType = "32";
					String fromVersion = "";
					String addYear	= "2013";
					String addType	= "33";
					String addVersion = "FOQ1";
					String addNextYear = "2014";
					addMissingEntries(fileName,fromYear,fromType,fromVersion,addYear,addType,addVersion,addNextYear);
					stophere = "x";
				}
			}
			
			
			
			//Revenue Price.
			//  Determine the common delivery method (most used)
			//  from OOLINE (OBMODL) by customer item.
			//  use delivery method for price category with
			//  customer item in PLP1PRCL.
			if ("x".equals("y"))
			{
				UpdDataSet fromVb = new UpdDataSet();
						
				// update revenue price
				if (1 == 1) {
					String fileName = "o100054";
					String version  = "BUDG";
					String year		= "2014";
					String sPeriod	= "01";
					String ePeriod	= "12";
					updateRevenuePrice(fileName, version, year, sPeriod, ePeriod);
					stophere = "x";
				}
			}
			
			
			
			//Load LBI file with type 33 data from data sets.
			//Select a DMP Data Set. This process will 
			//clear/copy all Data Sets into O100022 for type 33's and version.
			//set a switch to load volume only. volumeOnly == true or false.
			if ("x".equals("y"))
			{
				if (1 == 1)
				{
					String volumeOnly = "true"; //"true" or "false"
					
					//delete all the 33 entries in file o100022.
					buildLBIDataSetType33("delete33s", "2014", "SMBD", volumeOnly ); 
	
					
					//load all the files into the LBI Data Set.
					buildLBIDataSetType33("o100043", "2014", "SMBD", volumeOnly );
					buildLBIDataSetType33("o100044", "2014", "SMBD", volumeOnly );
					buildLBIDataSetType33("o100045", "2014", "SMBD", volumeOnly );
					buildLBIDataSetType33("o100046", "2014", "SMBD", volumeOnly );
					buildLBIDataSetType33("o100047", "2014", "SMBD", volumeOnly );
					buildLBIDataSetType33("o100048", "2014", "SMBD", volumeOnly );
					buildLBIDataSetType33("o100049", "2014", "SMBD", volumeOnly );
					buildLBIDataSetType33("o100054", "2014", "SMBD", volumeOnly );
					buildLBIDataSetType33("o100050", "2014", "SMBD", volumeOnly );
					buildLBIDataSetType33("o100052", "2014", "SMBD", volumeOnly );
					buildLBIDataSetType33("o100053", "2014", "SMBD", volumeOnly );
			
				}
			}
			
			//Company Cost into DMP data sets
			if ("x".equals("y"))
			{
				UpdDataSet fromVb = new UpdDataSet();
				
				// update company cost.
				if (1 == 1) {
					fromVb.setDataSetName("O100050");
					fromVb.setCostYear("2014");
					fromVb.setDataSetYear("2014");
					fromVb.setRecordType("33");
					String budgetVersion = "FOQ1";
					updateCompanyCostDmpDataSet(fromVb, budgetVersion);
					stophere = "x";
				}
			}
			
			
			
			//Company Cost into LBI (22 file).
			if ("x".equals("y"))
			{	
				// update company cost.
				if (1 == 1) {
					Vector parms = new Vector();
					parms.addElement("2012");		//Company Cost fiscal year
					parms.addElement("100");		//22 file update keys oscono
					parms.addElement("o100022");	//22 file update keys 
					parms.addElement("2012");		//22 file update keys osyea4
					parms.addElement("33");			//22 file update keys ossstt
					parms.addElement("BUDG");		//22 file update keys osbver
					
					updateCompanyCost22File(parms);
					stophere = "x";
				}
			}
			
			
			
			//Copy from Version 13.1 data sets into Version 14.1 data sets
			if ("x".equals("y"))
			{
				//enter the from and to data sets, record type, and fiscal year.
				String fromFile   = "o100038";
				String toFile     = "o100053";
				String recordType = "33";
				
				loadV14DataSet(fromFile, toFile, recordType);
				
				stophere = "x";
			}
			
			
			//Move from Version 13.1 type 34 records into Version 14.1 type 33
			if ("x".equals("y"))
			{
				//enter the from and to data sets, record type, and fiscal year.
				String fromFile       = "o100037";
				String toFile         = "o100052";
				String fromRecordType = "34";
				String toRecordType   = "33";
				String version        = "BUDG";
				String fiscalYear     = "2014";
				
				loadV14DataSetFrom34To33(fromFile, toFile, fromRecordType, toRecordType, version, fiscalYear);
				
				stophere = "x";
			}
			
			
			
			//This process builds 32 records into a Data Set file
			//defined in M3. The 32's (actuals) are loaded from
			//the LBI "o100022" file into a select set
			//of data set files. Pass in the data set file name
			//and the periods to load the sales history from.
			if ("x".equals("y"))
			{
				if (1 ==1) //load the data set below with actuals.
				{
					// run for each different fiscal year.
					buildDataSetSalesHistory("o100054", "2013", "09", "2013", "12");
				}
			}
			
			
			//This process updates or adds 32 records into a Data Set file
			//defined in M3. The 32's (actuals) are loaded from
			//the LBI "o100022" file into a select set
			//of data set files. Pass in the data set file name
			//and the periods to update the sales history from.
			if ("x".equals("y"))
			{
				if (1 ==1) //load the data set below with actuals.
				{
					// run for each different fiscal year.
					updateDataSetSalesFrom22File("o100052", "2013", "10", "2013", "10");
				}
			}
			
			
			
			//This process overlays forecast records for 
			//selected periods with actuals sales quantities 
			//and price. History is obtained from the o100022 file.
			//All other field values are kept.
			if ("x".equals("y"))
			{
				if (1 ==1) //load the data set below with actuals.
				{
					// run for each different fiscal year.
					overlayDataSetSalesFrom22File("o100052", "FOQ1", "2014", "01", "2014", "02");
				}
			}
			
			
			
			//Build a Variance File to allow Price and COGS comaprisions.
			
			//Data Set Record Type
			//Data Set Version
			//Fiscal Year used to retrieve Company Cost
			if ("x".equals("y"))
			{
				if (1 ==1)
				{
					// run for each different fiscal year.
					String chanel = "Club";
					String dataSetFiscalYear = "2013";
					String recordType = "33";
					String version = "BUDG";
					String cogsFiscalYear = "2013";
					String priceDate = "20120801";
					buildComparisionWorkFileForPriceAndCogs(chanel,dataSetFiscalYear,recordType,version,cogsFiscalYear,priceDate);
				}
			}
			
			
			
			//Overlay period 4 or period 8 forecast with actuals for finance reporting.
			//period 4/5 updates version FOQ1 records.
			//period 8/9 updates version FOQ2 records.
			//can be rerun any time - updates UCVOL3 field.
			if ("x".equals("y"))
			{
				if (1 ==1)
				{
					// run for each different fiscal year and version.
					String dataSetFiscalYear = "2013";
					String recordType = "33";
					String version = "FOQ2";
					updateForecastOffsetActuals(dataSetFiscalYear,recordType,version);
				}
			}
			
			
			
			String stopHere = "yes";
			

		} catch (Exception e) {
			System.out.println(e);
		}
	}
/**
     *   // Use to control the information retrieval
	 * @param -- currently not using. 
	 * @return Vector of Business Object DataSet.
	 * @throws Exception
	 */
	public static Vector listDataSetNames(Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector returnValue = new Vector();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = getDBConnection();
			returnValue = listDataSetNames(inValues, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
				   conn.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceDataSet.");
			throwError.append("listDataSetNames(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   Get a list of DataSet File Names - Along with Descriptions
	 * @param -- currently not using. 
	 * @return Vector of Business Object DataSet.
	 * @throws Exception
	 */
	private static Vector listDataSetNames(Vector inValues, 
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
			listThem = conn.prepareStatement(buildSqlStatement("listNames", inValues));
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
				  	  returnValue.addElement(loadFields("datasetName", rs));
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
			throwError.append("ServiceDataSet.");
			throwError.append("listDataSetNames(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   // Use to control the information retrieval
	 *   // Will use the Data Set O100001 to determine the year's.
	 * @param -- currently not using. 
	 * @return Vector of Business Object DataSet.
	 * @throws Exception
	 */
	public static Vector listDataSetYears(Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector returnValue = new Vector();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = getDBConnection();
			returnValue = listDataSetYears(inValues, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
				   conn.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceDataSet.");
			throwError.append("listDataSetYears(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   Get a list of DataSet File Names - Along with Descriptions
	 * @param -- currently not using. 
	 * @return Vector of Business Object DataSet.
	 * @throws Exception
	 */
	private static Vector listDataSetYears(Vector inValues, 
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
			listThem = conn.prepareStatement(buildSqlStatement("listYears", inValues));
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
				  	  returnValue.addElement(loadFields("datasetYear", rs));
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
			throwError.append("ServiceDataSet.");
			throwError.append("listDataSetYears(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 * Insert a record into the Audit Log 
	 * @return .
	 */
	private static void insertAuditLog(Vector inParms,
									   Connection conn)						
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		PreparedStatement updIt = null;
		try
		{
	  	 	updIt = conn.prepareStatement(buildSqlStatement("insertAudit", inParms));
	  	 	updIt.executeUpdate();
		} catch(Exception e)
		{
			throwError.append(" error occured. " + e);
		}
		finally
		{
			try
			{
				if (updIt != null)
					updIt.close();
			} catch(Exception el){
				el.printStackTrace();
			}		
		}	
		
		// return data.	
		if (!throwError.toString().trim().equals("")) 
		{
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceDataSet.");
			throwError.append("insertAuditLog(");
			throwError.append("Vector, Connection). ");
			throw new Exception(throwError.toString());
		}
	
	  return;
	}

	/**
	 *   Send in the DataSet File Name  
	 *      in the first element of the Vector
	 *    Return a Vector of ONLY 10 elements of DataSets
	 *    IF a dataset name is NOT sent in the last 10 updated records will be returned
	 * @param -- currently not using. 
	 * @return Vector of Business Object DataSet.
	 * @throws Exception
	 */
	public static Vector listAuditLog(Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector returnValue = new Vector();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = getDBConnection();
			returnValue = listAuditLog(inValues, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
				   conn.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceDataSet.");
			throwError.append("listAuditLog(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   Get a list of DataSet Class - from the File Name
	 * @param -- currently not using. 
	 * @return Vector of Business Object DataSet.
	 * @throws Exception
	 */
	private static Vector listAuditLog(Vector inValues, 
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
			listThem = conn.prepareStatement(buildSqlStatement("listAudit", inValues));
			rs = listThem.executeQuery();
		  } catch(Exception e)
		  {
		    throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		  }
		  if (throwError.toString().equals(""))
		  {
		   try
		   {
		   	  int x = 0;
			  while (rs.next())
			  {
			  	 // Build a Vector of Classes to Return
				  try { 
				  	if (x < 10)
				  	{	
				  	  returnValue.addElement(loadFields("auditLog", rs));
				  	  x++;
				  	}
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
			throwError.append("ServiceDataSet.");
			throwError.append("listAuditLog(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}
/**
 * Update revenue price for a data set
 * by fiscal year and period range
 * @param String file name
 * @param String version
 * @param String fiscal year
 * @param String from period
 * @param String to period
 * @return void
 * @throws Exception
 */
public static void updateRevenuePrice(String fileName, String version, String year, String sPeriod, String ePeriod)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = com.treetop.services.ServiceConnection.getConnectionStack1();
		
		// execute the update method
		conn = updateRevenuePrice(fileName, version, year, sPeriod, ePeriod, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				com.treetop.services.ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	try
	{
		Vector sendParms = new Vector();
		sendParms.addElement(fileName);
		sendParms.addElement(throwError);
	   insertAuditLog(sendParms, conn);
	}
	catch(Exception e)
	{}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceDataSet.");
		throwError.append("updateRevenuePrice(");
		throwError.append(fileName + ", ");
		throwError.append(version + ", ");
		throwError.append(year + ", ");
		throwError.append(sPeriod + ", ");
		throwError.append(ePeriod + " ");
		throwError.append("). ");
	}
	
	return;
}


/**
 * Update revenue price for a data set
 * by fiscal year and period range
 * @param String file name
 * @param String version
 * @param String fiscal year
 * @param String from period
 * @param String to period
 * @return Connection
 * @throws Exception
 */
private static Connection updateRevenuePrice(String fileName, String version, String year, 
											 String sPeriod,  String ePeriod, Connection conn)
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsCustItem = null;
    ResultSet rsChain    = null;
    ResultSet rsSales    = null;
    ResultSet rsPrice    = null;
    ResultSet rsCustMstr = null;
	PreparedStatement findThem  = null;
	PreparedStatement findSales = null;
	PreparedStatement findChain = null;
	PreparedStatement findPrice = null;
	PreparedStatement findMstr  = null;	
	java.sql.Statement updateIt = null;	
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally 
	
		//GET THE BUDGET RECORDS TO BE UPDATED.
		try //build sql of budget records to update.
		{
			Vector parmClass = new Vector();
			parmClass.addElement(fileName);
			parmClass.addElement(version);
			parmClass.addElement(year);
			parmClass.addElement(sPeriod);
			parmClass.addElement(ePeriod);
			requestType = "revenueCustItem";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (revenueCustItem). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findThem = conn.prepareStatement(sqlString);
				rsCustItem = findThem.executeQuery();
				
				//determine date 6 months ago.
			    com.treetop.businessobjects.DateTime dt = new DateTime();
			    dt = com.treetop.utilities.UtilityDateTime.getSystemDate();
			    
			    //Becky wants 6 months sales history used for April Forecast.
			    dt = com.treetop.utilities.UtilityDateTime.addDaysToDate(dt, -182);
			    //dt = com.treetop.utilities.UtilityDateTime.addDaysToDate(dt, -56);
			    
			    String date = dt.getDateFormatyyyyMMdd();
				
				// for each record get the Customer Chain if it exists.
				while (rsCustItem.next() && throwError.toString().equals(""))
				{
					//set all delivery method counters to zero.
					int modl1 = 0;
					int modl2 = 0;
					int modl3 = 0;
					int modl4 = 0;
					int modl5 = 0;
					int modl6 = 0;
					String columnNo = "";
					BigDecimal revAmt = new BigDecimal("0");
					revAmt.setScale(2);
					BigDecimal fgtAmt = new BigDecimal("0");
					fgtAmt.setScale(2);
//debug					
					if (rsCustItem.getString("UCCUNO").trim().equals("32088") &&
						rsCustItem.getString("UCITNO").trim().equals("101417"))
					{
						String debugStop = "here";
					}
//end debug					
					try //build sql for Sales Common Delivery.
					{
						Vector parmClass = new Vector();
						parmClass.addElement(rsCustItem);
						parmClass.addElement(date);
						requestType = "getDeliveryTypes";
						sqlString = buildSqlStatement(requestType, parmClass);
					} catch (Exception e) {
						throwError.append("Error at build sql (getDeliveryTypes). " + e);
					}
					
					try //read all delivery method counts and accumulate freuqency.
					{

						findSales = conn.prepareStatement(sqlString);
						rsSales   = findSales.executeQuery();
						
						while(rsSales.next() && throwError.toString().equals(""))
						{
							//allow exception for non numeric data.
							try {
								if (rsSales.getInt("OBMODL") == 1)
									modl1 = modl1 + 1;
								if (rsSales.getInt("OBMODL") == 2)
									modl2 = modl2 + 1;
								if (rsSales.getInt("OBMODL") == 3)
									modl3 = modl3 + 1;
								if (rsSales.getInt("OBMODL") == 4)
									modl4 = modl4 + 1;
								if (rsSales.getInt("OBMODL") == 5)
									modl5 = modl5 + 1;
								if (rsSales.getInt("OBMODL") == 6)
									modl6 = modl6 + 1;
							} catch (Exception e) {
								String skipIt = "skip this entry if it fails to become an int.";
							}
						}
					} catch (Exception e) {
						throwError.append("Error at execute sql (getDeliveryTypes). " + e);
					}
					
					findSales.close();
					
					if (modl6 != 0 || modl5 != 0 || modl4 != 0 || modl3 != 0 || modl2 != 0 || modl1 != 0)
					{
						if (modl6 >= modl5)
						{
							if (modl6 >= modl4)
							{
								if(modl6 >= modl3)
								{
									if(modl6 >= modl2) 
									{
										if (modl6 >= modl1)
										{
											columnNo = "6";
										}
									}
								}
							}
						}

						if(columnNo.equals("")) 
						{
							if (modl5 >= modl6)
							{
								if (modl5 >= modl4)
								{
									if (modl5 >= modl3)
									{
										if (modl5 >= modl2)
										{
											if (modl5 >= modl1)
											{
												columnNo = "5";
											}
										}
									}
								}
							}
						}
							
						if(columnNo.equals("")) 
						{
							if (modl4 >= modl6)
							{
								if (modl4 >= modl5)
								{
									if (modl4 >= modl3)
									{
										if (modl4 >= modl2)
										{
											if (modl4 >= modl1)
											{
												columnNo = "4";
											}
										}
									}
								}
							}
						}
							
						if (columnNo.equals(""))
						{
							if (modl3 >= modl6)
							{
								if (modl3 >= modl5)
								{
									if (modl3 >= modl4)
									{
										if (modl3 >= modl2)
										{
											if (modl3 >= modl1)
											{
												columnNo = "3";
											}
										}
									}
								}
							}
						}
							
						if(columnNo.equals(""))
						{
							if (modl2 >= modl6)
							{
								if (modl2 >= modl5)
								{
									if (modl2 >= modl4)
									{
										if (modl2 >= modl3)
										{
											if (modl2 >= modl1)
											{
												columnNo = "2";
											}
										}
									}
								}
							}
						}
						
						if(columnNo.equals(""))
						{
							if (modl1 >= modl6)
							{
								if (modl1 >= modl5)
								{
									if (modl1 >= modl4)
									{
										if (modl1 >= modl3)
										{
											if (modl1 >= modl2)
											{
												columnNo = "1";
											}
										}
									}
								}
							}
						}
						
					}
					
					//Condition for Brooke on two customer delivery types.	removed 4/25/2011
					//if (rsCustItem.getString("UCCUNO").trim().equals("32142") ||
						//rsCustItem.getString("UCCUNO").trim().equals("32144"))
					//{
						//columnNo = "2";
						//String stopHere = "x";	
					//}
					
					// Condition column based on Channel if no history found.  
					if(columnNo.equals(""))
					{
						if (fileName.toLowerCase().equals("o100043")) //Retail
							columnNo = "5";
						
						if (fileName.toLowerCase().equals("o100044")) //Military
							columnNo = "5";
						
						if (fileName.toLowerCase().equals("o100045")) //Club
							columnNo = "0";
						
						if (fileName.toLowerCase().equals("o100046")) //Mass Merchandise
							columnNo = "5";
						
						if (fileName.toLowerCase().equals("o100047")) //Dollar
							columnNo = "1";
						
						if (fileName.toLowerCase().equals("o100048")) //Export
							columnNo = "1";
						
						if (fileName.toLowerCase().equals("o100049")) //Natural
							columnNo = "3";
						
						if (fileName.toLowerCase().equals("o100052")) //Fresh Slice & Food Service
							columnNo = "6";
						
						if (fileName.toLowerCase().equals("o100054")) //Private Label
							columnNo = "5";
					}
						
					// Condition test values to exist loop (Load a non zero price).
					String continueLoop = "yes";	//changes if a revenue price is loaded or conditioned to exit.
					
					// Perform until a revenue price is loaded or time to leave.
					while (continueLoop.equals("yes"))
					{
						
						//09/13/2012 skip Club altogrther if no history found.
						if (fileName.toLowerCase().equals("o100045") && columnNo.equals("0")) //Club
							continueLoop = "no";
						
						//09/13/2012 Only use 3 for Natural regaurdless of history.
						if (fileName.toLowerCase().equals("o100049") && columnNo.equals("0")) //Natural
							columnNo = "3";
							
						if (continueLoop.equals("yes"))
						{	
						
							//if (fileName.toLowerCase().equals("o100030") && columnNo.equals("0"))
							//{
								//revAmt = new BigDecimal("0");
								//revAmt.setScale(2);
								//fgtAmt = new BigDecimal("0");
								//fgtAmt.setScale(2);
							//} else
							//{
							
								try //build sql for customer chain.
								{
									Vector parmClass = new Vector();
									parmClass.addElement(rsCustItem);
									requestType = "getCustomerChain";
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch (Exception e) {
									throwError.append("Error at build sql (getCustomerChain). " + e);
								}
								
								try //see if customer chain exists.
								{
									findChain = conn.prepareStatement(sqlString);
									rsChain   = findChain.executeQuery();
								} catch (Exception e) {
									throwError.append("Error at execute sql (getCustomerChain). " + e);
								}
								
								if (rsChain.next() && throwError.toString().equals("")) // use the chain in the price lookup if it exists.
								{
									try //build sql for price retrival using customer chain.
									{
										Vector parmClass = new Vector();
										parmClass.addElement(rsChain);
										parmClass.addElement(rsCustItem);
										requestType = "getPriceCustChain";
										sqlString = buildSqlStatement(requestType, parmClass);
									} catch (Exception e) {
										throwError.append("Error at build sql (getPriceCustChain). " + e);
									}
									
									try //get the price record via Customer Chain and Item.
									{
										findPrice = conn.prepareStatement(sqlString);
										rsPrice   = findPrice.executeQuery();
										//findPrice.close();
									} catch (Exception e) {
										throwError.append("Error at execute sql (getPriceCustChain). " + e);
									}
								} 
								else //use Customer Market for price lookup if no Chain exists.
								{
									try //build sql for price retrival using Customer Market
									{
										try // build sql for Customer Master.
										{
											Vector parmClass = new Vector();
											parmClass.addElement(rsCustItem);
											requestType = "getCustMaster";
											sqlString = buildSqlStatement(requestType, parmClass);
										} catch (Exception e) {
											throwError.append("Error at build sql (getCustMaster). " + e);
										}
										
										try //get the Market from Customer Master.
										{
											findMstr   = conn.prepareStatement(sqlString);
											rsCustMstr = findMstr.executeQuery();
										} catch (Exception e) {
											throwError.append("Error at execute sql (getCustMaster). " + e);
											}
										
										if (rsCustMstr.next() && throwError.toString().equals(""))
										{
											try //build sql for price retrieval using Customer Market.
											{
												Vector parmClass = new Vector();
												parmClass.addElement(rsCustMstr);
												parmClass.addElement(rsCustItem);
												requestType = "getPriceCustMarket";
												sqlString = buildSqlStatement(requestType, parmClass);
											} catch (Exception e) {
												throwError.append("Error at build sql (getPriceCustMarket). " + e);
											}
											
											try //get the price record via Customer Market and Item.
											{
												findPrice = conn.prepareStatement(sqlString);
												rsPrice   = findPrice.executeQuery();
												//findPrice.close();
											} catch (Exception e) {
												throwError.append("Error at execute sql (getPriceCustMarket). " + e);
											}
										}
									} catch (Exception e) {
										throwError.append("Error getting price. " + e);
									}
								}
								
								
								// Calculate price if price record available.
								if ( rsPrice != null && rsPrice.next() && throwError.toString().equals("")) 
								{
									//access to the price file is by effective date (decending). Only look at this record.
									
									// get list price.
									BigDecimal list = new BigDecimal("0");
									list.setScale(2);
									list = rsPrice.getBigDecimal("PL1CP" + columnNo);
									
									// get estimated freight.
									BigDecimal estFrt = new BigDecimal("0");
									estFrt.setScale(2);
									estFrt = rsPrice.getBigDecimal("PLC" + columnNo + "D2");
										
									// get transaction freight.
									BigDecimal trnsFrt = new BigDecimal("0");
									trnsFrt.setScale(2);
									trnsFrt = rsPrice.getBigDecimal("PLC" + columnNo + "D3");
									
									// get market freight.
									BigDecimal mktFrt = new BigDecimal("0");
									mktFrt.setScale(2);
									mktFrt = rsPrice.getBigDecimal("PL1OF" + columnNo);
									
									// get transfer freight.
									BigDecimal trfFrt = new BigDecimal("0");
									trfFrt.setScale(2);
									trfFrt = rsPrice.getBigDecimal("PL1OT" + columnNo);
									
									//calculate revenue amount.
									revAmt = revAmt.add(list);
									revAmt = revAmt.add(estFrt);
									revAmt = revAmt.add(trnsFrt);
									revAmt = revAmt.subtract(mktFrt);
									revAmt = revAmt.subtract(trfFrt);
									
									fgtAmt = fgtAmt.add(estFrt);
									fgtAmt = fgtAmt.add(trnsFrt);
								}
							//}
						
							//Perform The Update
							BigDecimal zero = new BigDecimal("0");
							int testZero = revAmt.compareTo(zero); //sets testZero to 0 if list = zero.
							
							if (testZero != 0  && throwError.toString().equals(""))
							{
								try //build/update sql for update.
								{
									Vector parmClass = new Vector();
									parmClass.addElement(fileName);
									parmClass.addElement(rsCustItem);
									parmClass.addElement(revAmt);
									parmClass.addElement(columnNo);
									parmClass.addElement(fgtAmt);
									parmClass.addElement(sPeriod);
									parmClass.addElement(ePeriod);
									requestType = "updateRevenuePrice";
									sqlString = buildSqlStatement(requestType, parmClass);
									updateIt  = conn.createStatement();
									updateIt.executeUpdate(sqlString);
									updateIt.close();
									continueLoop = "no";
								} catch (Exception e) {
									throwError.append("Error at build/update sql (updateRevenuePrice). " + e);
								}
							}
							
							try {
								findChain.close();
								findPrice.close();
								findMstr.close();
							} catch (Exception e) {}
						}
						
						if (fileName.toLowerCase().equals("o100043") ) //Retail
						{
							if (columnNo.equals("5"))
								continueLoop = "no";
							else
								columnNo = "5";
						}
						
						if (fileName.toLowerCase().equals("o100044") ) //Military
							continueLoop = "no";
						
						if (fileName.toLowerCase().equals("o100045") ) //Club
							continueLoop = "no";
						
						if (fileName.toLowerCase().equals("o100046") ) //Mass Merchandise 5
						{
							if (columnNo.equals("5"))
								continueLoop = "no";
							else
								columnNo = "5";
						}
						
						if (fileName.toLowerCase().equals("o100047") ) //Dollar 
							if (columnNo.equals("1"))
								continueLoop = "no";
							else
								columnNo = "1";
						
						if (fileName.toLowerCase().equals("o100048") ) //Export  
							if (columnNo.equals("1"))
								continueLoop = "no";
							else
								columnNo = "1";
						
						if (fileName.toLowerCase().equals("o100049") ) //Natural 
							continueLoop = "no";
						
						if (fileName.toLowerCase().equals("o100052") ) //Fresh Slice & Food Service
						{
							if(columnNo.equals("5"))
								continueLoop = "no";
							else
								columnNo = "5";
						}
						
						if (fileName.toLowerCase().equals("o100053") ) //Co-pack
						{
							if (columnNo.equals("1"))
								continueLoop = "no";
							else
								columnNo = "1";
						}
						
						if (fileName.toLowerCase().equals("o100054") ) //Private Label
						{
							if (columnNo.equals("5"))
								continueLoop = "no";
							else
								columnNo = "5";
						}
						
						if (fileName.toLowerCase().equals("xtest") ) //test file
						{
							if (columnNo.equals("5"))
								continueLoop = "no";
							else
								columnNo = "5";
						}
					}
					
					try {
						if (findSales != null)
							findSales.close();
						if (rsSales != null)
							rsSales.close();
					} catch (Exception e) {
						String x = "x";
					}
					try {
						if (findChain != null)
						findChain.close();
						if (rsChain != null)
							rsChain.close();
					} catch (Exception e) {
						String x = "x";
					}
					try {
						if (findPrice != null)
						findPrice.close();
						if (rsPrice != null)
							rsPrice.close();
					} catch (Exception e) {
						String x = "x";
					}
					try {
						if (findMstr != null)
							findMstr.close();
						if (rsCustMstr != null)
							rsCustMstr.close();
					} catch (Exception e) {
						String x = "x";
					}
					try {
						if (updateIt != null)
							updateIt.close();
					} catch (Exception e) {
						String x = "x";
					}
				//}
				}
			} catch (Exception e) {
				throwError.append(" Error at execute the sql (revenueCustItem). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findThem != null)
				findThem.close();
			if (findSales != null)
				findSales.close();
			if (findChain != null)
				findChain.close();
			if (findPrice != null)
				findPrice.close();
			if (findMstr != null)
				findMstr.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsCustItem != null)
				rsCustItem.close();
			if (rsSales != null)
				rsSales.close();
			if (rsChain != null)
				rsChain.close();
			if (rsPrice != null)
				rsPrice.close();
			if (rsCustMstr != null)
				rsCustMstr.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("updateRevenuePrice(");
		throwError.append("UpdDataSet, Connection). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * Update brokerage rate (UCAAV1) for a data set.
 * Get the rate from file OBOTRA.
 * 
 * @param String fileName.
 * @param String year.
 * @param String starting period.
 * @param String ending period.
 * @return 
 * @throws Exception
 */
public static void updateBrokerageRate(String fileName, String year, String sPeriod, String ePeriod, String version)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = com.treetop.services.ServiceConnection.getConnectionStack1();
		
		// execute the update method
		conn = updateBrokerageRate(fileName, year, sPeriod, ePeriod, version, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				com.treetop.services.ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	try
	{
		Vector sendParms = new Vector();
		sendParms.addElement(fileName);
		sendParms.addElement(throwError);
	   insertAuditLog(sendParms, conn);
	}
	catch(Exception e)
	{}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceDataSet.");
		throwError.append("updateBrokerageRate(");
		throwError.append(fileName + ", ");
		throwError.append(year + ", ");
		throwError.append(sPeriod + ", ");
		throwError.append(ePeriod + " ");
		throwError.append("). ");
	}
	
	return;
}

/**
 * Update the brokerage rate of a Data Set.
 * Get rate form file OBOTRA.
 * @param year.
 * @param starting period.
 * @param ending period. 
 * @param connection.
 * @return connection.
 */
private static Connection updateBrokerageRate(String fileName, String year, 
											  String sPeriod,  String ePeriod, 
											  String version,  Connection conn)
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsDataSet = null;
    ResultSet rsObotra  = null;
	PreparedStatement findDataSet = null;
	PreparedStatement findObotra  = null;
	PreparedStatement updateIt = null;	
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally 
	
		//Get the budget data set file to be updated.
		try //build sql of budget records to update.
		{
			Vector parmClass = new Vector();
			parmClass.addElement(fileName);
			parmClass.addElement(year);
			parmClass.addElement(sPeriod);
			parmClass.addElement(ePeriod);
			parmClass.addElement(version);
			requestType = "getBrokerageDataSet";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (getBrokerageDataSet). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findDataSet = conn.prepareStatement(sqlString);
				rsDataSet   = findDataSet.executeQuery();
				
				// for each record get the Customer Chain if it exists.
				while (rsDataSet.next() && throwError.toString().equals(""))
				{

					
					try //build sql for Sales brokerage (OBOTRA).
					{
						Vector parmClass = new Vector();
						parmClass.addElement(rsDataSet);
						requestType = "getBrokerageObotra";
						sqlString = buildSqlStatement(requestType, parmClass);
					} catch (Exception e) {
						throwError.append("Error at build sql (getBrokerageObotra). " + e);
					}
					
					if (throwError.toString().equals(""))
					{
					
						try //execute sql.
						{
							//get the latest brokerage for customer and item.	
							findObotra = conn.prepareStatement(sqlString);
							rsObotra   = findObotra.executeQuery();
							String invNbr = "";
							String endIt  = "";
							BigDecimal rate = new BigDecimal("0");
							BigDecimal rateFactored = new BigDecimal("0");
						
							while (rsObotra.next() && throwError.toString().equals("") &&
								   endIt.equals(""))
							{
								if (invNbr.equals(""))
									invNbr = rsObotra.getString("OCIVNO").trim();
								
								if (invNbr.trim().equals(rsObotra.getString("OCIVNO").trim()))
								{
									BigDecimal one = new BigDecimal("1");
									rate = rate.add(rsObotra.getBigDecimal("OCRATX"));
									rateFactored = rate.multiply(one);
	
									//Perform the update.
									try //build/update sql for update.
									{
										Vector parmClass = new Vector();
										parmClass.addElement(fileName);
										parmClass.addElement(rsDataSet.getString("UCCUNO"));
										parmClass.addElement(rsDataSet.getString("UCITNO"));
										parmClass.addElement(rsDataSet.getString("OSYEA4"));
										parmClass.addElement(rsDataSet.getString("OSPERI"));
										BigDecimal factor = new BigDecimal(".01");
										rateFactored = rateFactored.multiply(factor);
										rate.setScale(2,0);
										rateFactored.setScale(6,0);
										parmClass.addElement(rate);
										parmClass.addElement(rateFactored);
										parmClass.addElement(rsDataSet.getString("OSSSTT"));
										parmClass.addElement(rsDataSet.getString("OSBVER"));
										requestType = "updateBrokerageRate";
										sqlString = buildSqlStatement(requestType, parmClass);
										updateIt  = conn.prepareStatement(sqlString);
										updateIt.executeUpdate();
										updateIt.close();
									} catch (Exception e) {
										throwError.append("Error at build/update sql (updateBrokerageRate). " + e);
									}
								} else
									endIt = "now";
							}
						} catch (Exception e) {
							throwError.append("Error at execute sql (getBrokerageObotra). " + e);
						}
					}
					
					findObotra.close();
					rsObotra.close();
				}
			} catch (Exception e) {
				throwError.append(" Error at execute the sql (getBrokerageDataSet). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try {
			if (findDataSet != null)
				findDataSet.close();
			if (findObotra != null)
				findObotra.close();

			if (rsDataSet != null)
				rsDataSet.close();
			if (rsObotra != null)
				rsObotra.close();
		} catch(Exception el){
			el.printStackTrace();
		}

	}	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("updateRevenuePrice(");
		throwError.append("UpdDataSet, Connection). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * Read the DataSet for a specific year and cost type, get the Company Cost, 
 * and load it back into the Dataset
 * 
 * Return nothing, unless there is a problem, then it will THROW it back
 * @param UpdDataSet -- parameter criteria to allow update of datast
 *        Connection -- 
 * @return Nothing.
 */
private static void updateCompanyCostDmpDataSet(UpdDataSet inValues, Connection conn, String budgetVersion)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rs = null;
	PreparedStatement findThem = null;
	PreparedStatement updIt = null;
	try
	{
		try { 
			// On the READ get the item number from the data set (group By) 
			// also get the company cost for the item/year form the Company Cost file.
			// Then just update the same data set at the item/year/type/version level with 
			// the company cost value.
			Vector sendParms = new Vector();
			sendParms.addElement(inValues);
			sendParms.addElement(budgetVersion);
			findThem = conn.prepareStatement(buildSqlStatement("readItemsFromDataSet", sendParms));	
			rs = findThem.executeQuery();
		} catch(Exception e)
		{
			throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}
		
		while (throwError.toString().equals("") && rs.next() )
		{
		   try
		   {
	  
				   	//For every (goruped by item/year/type/version in the result set update multiple entires
				   	//in the data set file in the UCUCOS field.
				   try {																						
					   Vector sendParms = new Vector();														
					   // 0 = file Name	
					   // 1 = Item Number
					   // 2 = Year
					   // 3 = Record type
					   // 4 = Company cost
					   // 5 = Budget Version
					   
					   sendParms.addElement(inValues.getDataSetName().trim());
					   sendParms.addElement(rs.getString("UCITNO").trim());
					   sendParms.addElement(inValues.getDataSetYear().trim());
					   sendParms.addElement(inValues.getRecordType().trim());
					   
					   // use item uom cost for Ingredient Food Service.
					   if (inValues.getDataSetName().trim().toUpperCase().equals("O100052")
						   && !rs.getString("MMITTY").equals("130"))
						   sendParms.addElement(rs.getString("T9CSU9").trim());								
					   else		
						   sendParms.addElement(rs.getString("T9ALU9").trim());		
					   
					   sendParms.addElement(budgetVersion);
					   
					   updIt = conn.prepareStatement(buildSqlStatement("updateDsCompanyCost", sendParms));	
					   updIt.executeUpdate();																
				   } catch (Exception e) {
					   throwError.append(" error trying to build sqlString to update . " + e);
				   }
		   } catch(Exception e)
		   {
		   	  throwError.append(" error occured while Building Vector from sql statement. " + e);
		   } 
		} 		
	}catch(Exception e)
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
			if (updIt != null)
				updIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}		
	}	
	try
	{
		Vector sendParms = new Vector();
		sendParms.addElement(inValues);
		sendParms.addElement(throwError);
	    insertAuditLog(sendParms, conn);
	}
	catch(Exception e)
	{}
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("updateCompanyCostDmpDataSet(");
		throwError.append("UpdDataSet, Connection). ");
		throw new Exception(throwError.toString());
	}

  return;
}

/**
 * Build the LBI Reporting file (o100022).
 * 
 * @param
 * @return 
 * @throws Exception
 */
public static void buildLBIDataSetType33(String fileName, String fiscalYear, String version, String volumeOnly)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = com.treetop.services.ServiceConnection.getConnectionStack1();
		
		// execute the update method
		conn = buildLBIDataSetType33(fileName, fiscalYear, version, volumeOnly, conn );
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				com.treetop.services.ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceDataSet.");
		throwError.append("buildLBIDataSetType33(). ");
	}
	
	return;
}

/**
 * Build the LBI Reporting file o100022. 
 * @param  Connection conn.
 * @return Connection.
 */
private static Connection buildLBIDataSetType33(String fileName, String fiscalYear, String version, 
		                                        String volumeOnly, Connection conn )						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsDmp			   = null;
    ResultSet rsLbi			   = null;
	PreparedStatement findDmp  = null;
	PreparedStatement findLbi  = null;
	PreparedStatement addIt    = null;
	PreparedStatement updateIt = null;
	PreparedStatement deleteIt = null;
	String sqlString = "";
	String requestType = "";
	
	try { //enable finally 
		
		if (fileName.equals("delete33s"))
		{
			try {
				requestType = fileName;
				Vector parmClass = new Vector();
				parmClass.addElement(fiscalYear);
				parmClass.addElement(version);
				sqlString = buildSqlStatement(requestType, parmClass);
				deleteIt = conn.prepareStatement(sqlString);
				deleteIt.executeUpdate();
				deleteIt.close();
			} catch (Exception e) {
				throwError.append("Error deleting type 33 records from LBI DataSet (delete33s). " + e);
			}
		}
		
		//get the DMP DataSet file records.
		if (throwError.toString().equals("") && 
			!requestType.equals("delete33s"))
		{
			try //build the sql statement.
			{
				requestType = "getDMPfile";
				Vector parmClass = new Vector();
				parmClass.addElement(fileName);
				parmClass.addElement(fiscalYear);
				parmClass.addElement(version);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch (Exception e) {
				throwError.append("Error at build sql (getDMPfile). " + e);
			}
		
			// execute the sql.
			if (throwError.toString().equals(""))
			{

				try //get the data set file information.
				{
					findDmp = conn.prepareStatement(sqlString);
					rsDmp	= findDmp.executeQuery();
			
					while (rsDmp.next() && throwError.toString().equals(""))
					{
						//debug statements
						if (rsDmp.getString("OSYEA4").equals("2009") &&
							rsDmp.getString("OSPERI").equals('8') &&
							rsDmp.getString("UCCUNO").equals("30879") &&
							rsDmp.getString("UCITNO").equals("200571"))
						{
							String stopHere = "yes";
						}
						
						//build sql to find LBI entry.
						
						BigDecimal qty = rsDmp.getBigDecimal("UCIVQT");
						qty.setScale(6);
						BigDecimal price = rsDmp.getBigDecimal("UCAAV2");
						price.setScale(6);
						BigDecimal lineAmt = qty.multiply(price);
						lineAmt.setScale(2,0);
								
						BigDecimal brokerageBC = new BigDecimal("0");
						brokerageBC.setScale(2);
								

						BigDecimal brokerageRate = rsDmp.getBigDecimal("UCAAV1");
						brokerageBC = lineAmt.multiply(brokerageRate);
						brokerageBC.setScale(2,0);

								
						//build update sql
						try {
							requestType = "addLBIentry";
							Vector parmClass = new Vector();
							parmClass.addElement(rsDmp);
							parmClass.addElement(lineAmt);
							parmClass.addElement(brokerageBC);
							parmClass.addElement(volumeOnly);
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch (Exception e) {
							throwError.append("Error at build sql (addLBIentry). " + e);
						}
								
						//execute sql
						if (throwError.toString().equals(""))
						{
							try {
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
							} catch (Exception e){
								throwError.append("Error at execute sql (addLBIentry). " + e);
							}
						}

					}
					
					rsDmp.close();
					findDmp.close();
					
				} catch(Exception e) {
					throwError.append("Error at execute sql (getDMPfile). " + e);
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
			if (findDmp != null)
				findDmp.close();
			if (findLbi != null)
				findLbi.close();
			if (updateIt != null)
				updateIt.close();
			if (addIt != null)
				addIt.close();
			if (deleteIt != null)
				deleteIt.close();
		} catch(Exception e){
			throwError.append("Error closing prepared statements. " + e);
		}
		try
		{
			if (rsDmp != null)
				rsDmp.close();
			if (rsLbi != null)
				rsLbi.close();
		} catch(Exception e){
			throwError.append("Error closing result sets. " + e);
		}	

	}	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("buildLbiDataSetType33");
		throwError.append("(filename " + fileName + ", ");
		throwError.append("fiscalYear " + fiscalYear + ", ");
		throwError.append("version " + version + ", ");
		throwError.append("Connection conn ) ");
		throw new Exception(throwError.toString());
	}

  return conn;
}



/**
 * For the LIB o100022 file ONLY 
 * for cono/yea4/sstt/bver update UCUCOS  
 * company cost from by item (ZTP9CST), 
 * Will update field UCUCOS - in o100022
 * 
 * @param Vector parms
 * @return Nothing.
 * @throws Exception
 */
public static void updateCompanyCost22File(Vector parms)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		updateCompanyCost22File(parms, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
			   conn.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceDataSet.");
		throwError.append("updateCompanyCost22File(");
		throwError.append("UpdDataSet). ");
		throw new Exception(throwError.toString());
	}
	return;
}



/**
 * For the LIB o100022 file ONLY 
 * for cono/yea4/sstt/bver update UCUCOS  
 * company cost from by item (ZTP9CST), 
 * Will update field UCUCOS - in o100022
 * 
 * @param Vector parms
 * @return Nothing.
 * @throws Exception
 */
private static void updateCompanyCost22File(Vector parms, 
									  		Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rs = null;
	PreparedStatement findThem = null;
	PreparedStatement updIt = null;
	try
	{
		try { 
			// Read the 22 file and group by co#/year/rec type/version. 
			// also get the company cost for the item/year form the Company Cost file.
			// Then just update the same data set at the co#/year/rec type/version level 
			// with the company cost value.
			Vector sendParms = new Vector();
			sendParms.addElement(parms);

			findThem = conn.prepareStatement(buildSqlStatement("readItemsFrom22File", sendParms));
			rs = findThem.executeQuery();
		} catch(Exception e)
		{
			throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}
		
		while (throwError.toString().equals("") && rs.next() )
		{
			//For every (goruped by co#/year/sstt/bver/item in the result set update multiple entires
			//in the o100022 file in the UCUCOS field.
			  try {	
			   Vector sendParms = new Vector();
			   String fileName = (String) parms.elementAt(2);
			   sendParms.addElement(fileName);
			   sendParms.addElement(rs.getString("OSCONO").trim());
			   sendParms.addElement(rs.getString("OSYEA4").trim());
			   sendParms.addElement(rs.getString("OSSSTT").trim());
			   sendParms.addElement(rs.getString("OSBVER").trim());
			   sendParms.addElement(rs.getString("UCITNO").trim());
			   sendParms.addElement(rs.getString("OKCUCL").trim());
			   sendParms.addElement(rs.getString("MMITCL").trim());
					   
			   // use item uom cost for Ingredient Food Service.
			   int itcl = 0;
			   try {
				   itcl = rs.getInt("MMITCL");
			   }  catch(Exception e){} 
			   
			   if (rs.getString("OKCUCL").trim().equals("103") &&
				   !rs.getString("UCITNO").trim().equals("1005001001") &&
				   !rs.getString("UCITNO").trim().equals("1000001055") &&
				   ( (itcl >= 300 && itcl <= 800) || (itcl >= 100 && itcl <= 170) || (itcl == 210)) )
					   sendParms.addElement(rs.getString("T9CSU9").trim());								
			   else
				   sendParms.addElement(rs.getString("T9ALU9").trim());
					   
				   updIt = conn.prepareStatement(buildSqlStatement("update22FileCompanyCost", sendParms));
				   updIt.executeUpdate();
			  } catch (Exception e) {
					   throwError.append(" error trying to build sqlString to update . " + e);
			  }
		   
		}
	}catch(Exception e)
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
			if (updIt != null)
				updIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}		
	}	

	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("updateCompanyCost22File(");
		throwError.append("UpdDataSet, Connection). ");
		throw new Exception(throwError.toString());
	}

  return;
}

/**
 * Load V14.1 Data Sets from V13 data sets.
 * 
 * @param from file
 * @param to file
 * @param record type
 * @return 
 * @throws Exception
 */
public static void loadV14DataSet(String fromFile, String toFile, String recordType)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = com.treetop.services.ServiceConnection.getConnectionStack1();
		
		// execute the update method
		conn = loadV14DataSet(fromFile, toFile, recordType, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				com.treetop.services.ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceDataSet.");
		throwError.append("loadV14DataSet(");
		throwError.append(fromFile + ", ");
		throwError.append(toFile + ", ");
		throwError.append(recordType + ", ");
		throwError.append("). ");
	}
	
	return;
}

/**
 * Load V14.1 Data Sets from V13 data sets.
 * 
 * @param from file
 * @param to file
 * @param record type
 * @return Connection.
 * @throws Exception
 */
private static Connection loadV14DataSet(String fromFile, String toFile, 
										 String recordType, Connection conn)
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rs = null;
	PreparedStatement findThem  = null;
	java.sql.Statement addIt = null;	
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally 
	
		//GET THE V13 data set records.
		try 
		{
			Vector parmClass = new Vector();
			parmClass.addElement(fromFile);
			parmClass.addElement(recordType);
			parmClass.addElement(toFile);
			requestType = "getV13Records";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (getV13Records). " + e);
		}
		
		// build and execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findThem = conn.prepareStatement(sqlString);
				rs       = findThem.executeQuery();
				
				//create the update statement here outside of the loop.
				addIt  = conn.createStatement();
				
				// for each record write a V14 entry.
				while (rs.next() && throwError.toString().equals(""))
				{
					
					try //build sql for V14 record add.
					{
						Vector parmClass = new Vector();
						parmClass.addElement(rs);
						parmClass.addElement(toFile);
						requestType = "addV14Records";
						sqlString = buildSqlStatement(requestType, parmClass);
					} catch (Exception e) {
						throwError.append("Error at build sql (addV14Records). " + e);
					}
	
					//execute add
					try
					{
						addIt.executeUpdate(sqlString);
									
									
					} catch (Exception e) {
						throwError.append("Error at add of V14 Records (addV14Records). " + e);
					}
				}
				
				addIt.close();
				
			
			} catch (Exception e) {
				throwError.append(" Error at build and execute the sql. " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findThem != null)
				findThem.close();
			if (addIt != null)
				addIt.close();
		} catch(Exception e){
		}
		
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("loadV14DataSet(String, String, String, Connection). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * Build actual sales into an existing 
 * Data Set file from the LBI (22) Sales File.
 * April 25 2011.
 * 
 * @param String Start fiscal year
 * @param String Start fiscal period
 * @param String End fiscal year
 * @param String End fiscal period
 * @return 
 * @throws Exception
 */
public static void buildDataSetSalesHistory(String fileName, 
											String sYear, String sPeriod,
											String eYear, String ePeriod)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = com.treetop.services.ServiceConnection.getConnectionStack1();
		
		// execute the update method
		Vector vector = new Vector();
		vector.addElement(conn);
		vector.addElement(fileName);
		vector.addElement(sYear);
		vector.addElement(sPeriod);
		vector.addElement(eYear);
		vector.addElement(ePeriod);
		vector = buildDataSetSalesHistory(vector);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				com.treetop.services.ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception e){
				throwError.append("Error on return of conn. " + e );
			}
		}

	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceDataSet.");
		throwError.append("buildDataSetSalesHistory(");
		throwError.append(fileName + ", ");
		throwError.append(sYear + ", " + sPeriod + ", ");
		throwError.append(eYear + "; " + ePeriod + " ");
		throwError.append("). ");
		System.out.println(throwError.toString());
		Exception e = new Exception();
		e.printStackTrace();
	}
	
	return;
}

/**
 * Build the Sales Detail File. 
 * @param conn, fiscal year, fiscal period.
 * @return Connection.
 */
private static Vector buildDataSetSalesHistory(Vector vector)						
	throws Exception 
{
	Connection connA = (Connection) vector.elementAt(0);
	String fileName  = (String) vector.elementAt(1);
	String sYear     = (String) vector.elementAt(2);
	String sPeriod   = (String) vector.elementAt(3);
	String eYear     = (String) vector.elementAt(4);
	String ePeriod   = (String) vector.elementAt(5);
	
	StringBuffer throwError = new StringBuffer();
    ResultSet rs22file  = null;
    
	Statement find22file  = null;
	Statement addIt      = null;
	String requestType = "";
	String sqlString = "";
		
	try { //enable finally.
		
		try //build sql.
			{
			requestType = "getSalesHistForDataSet";
			Vector parmClass = new Vector();
			parmClass.addElement(fileName);
			parmClass.addElement(sYear);
			parmClass.addElement(sPeriod);
			parmClass.addElement(eYear);
			parmClass.addElement(ePeriod);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (getSalesHistForDataSet). " + e);
		}
			
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {
				find22file = connA.createStatement();
				rs22file   = find22file.executeQuery(sqlString);
					
				// for each record add or update a data set detail file entry.
				while (rs22file.next() && throwError.toString().equals(""))
				{			      
					//addIt
					try { //build sql
						BigDecimal zero = new BigDecimal("0");
						BigDecimal ivqt = new BigDecimal(rs22file.getString("UCIVQT")).setScale(0, 4);
									
						BigDecimal saam = new BigDecimal(rs22file.getString("UCSAAM"));
						BigDecimal price = new BigDecimal("0");
						price.setScale(6);
						int x = ivqt.compareTo(zero);
								      
						if (x != 0)
						{
						   	price = saam.divide(ivqt, 6, 4);
						   	price = price.setScale(6,4);
						}		    
								    
						requestType = "addDataSet32From22";
						Vector parmClass = new Vector();
						parmClass.addElement(fileName);
						parmClass.addElement(rs22file);
						parmClass.addElement(price);
						parmClass.addElement(ivqt);
						sqlString = buildSqlStatement(requestType, parmClass);
					} catch (Exception e) {
						throwError.append("Error ar build sql (addDataSet32From22)");
					}
								
					if (throwError.toString().equals(""))
					{
						try {
							addIt = connA.createStatement();
							addIt.executeUpdate(sqlString);
							addIt.close();
						} catch (Exception e) {
							throwError.append("Error at execute sql (addDataSet32From22");
						}
					}
				}
			} catch (Exception e) {
				throwError.append(" Error at execute the sql (getSalesHistForDataSet). " + e);
			}
						
		}
						
		find22file.close();
		rs22file.close();
					
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
		
				
	finally
	{
		try
		{
			if (find22file != null)
				find22file.close();
			if (addIt != null)
				addIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rs22file != null)
				rs22file.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("buildDataSetSalesHistory(");
		throwError.append("Vector");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}
	
	return vector;

}

/**
 * Update or add actual sales into an existing 
 * Data Set file from the LBI 22 Sales File.
 * October 31 2008.
 * 
 * @param String Start fiscal year
 * @param String Start fiscal period
 * @param String End fiscal year
 * @param String End fiscal period
 * @return 
 * @throws Exception
 */
public static void updateDataSetSalesFrom22File(String fileName, 
											  String sYear, String sPeriod,
											  String eYear, String ePeriod)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get connections to be sent to find methods
		conn = com.treetop.services.ServiceConnection.getConnectionStack1();
		
		
		// execute the update method
		Vector vector = new Vector();
		vector.addElement(conn);
		vector.addElement(fileName);
		vector.addElement(sYear);
		vector.addElement(sPeriod);
		vector.addElement(eYear);
		vector.addElement(ePeriod);
		vector = updateDataSetSalesFrom22File(vector);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				com.treetop.services.ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception e){
				throwError.append("Error returning conn." + e );
			}
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceDataSet.");
		throwError.append("updateDataSetSalesFrom22File(");
		throwError.append(fileName + ", ");
		throwError.append(sYear + ", " + sPeriod + ", ");
		throwError.append(eYear + "; " + ePeriod + " ");
		throwError.append("). ");
		System.out.println(throwError.toString());
		Exception e = new Exception();
		e.printStackTrace();
	}
	
	return;
}

/**
 * Update / Add the Sales Detail File. 
 * @param conn, fiscal year, fiscal period.
 * @return Connection.
 */
private static Vector updateDataSetSalesFrom22File(Vector vector)						
	throws Exception 
{
	Connection connA = (Connection) vector.elementAt(0);
	String fileName  = (String) vector.elementAt(1);
	String sYear     = (String) vector.elementAt(2);
	String sPeriod   = (String) vector.elementAt(3);
	String eYear     = (String) vector.elementAt(4);
	String ePeriod   = (String) vector.elementAt(5);
	
	StringBuffer throwError = new StringBuffer();
    ResultSet rs22file  = null;
    ResultSet rsDS     = null;
    
	Statement find22file  = null;
	Statement findDS     = null;
	Statement addIt      = null;
	Statement updateIt   = null;
	
	String requestType = "";
	String sqlString = "";
		
	try { //enable finally.
		
		try //build sql.
		{
			requestType = "getSalesHistForDataSet";
			Vector parmClass = new Vector();
			parmClass.addElement(fileName);
			parmClass.addElement(sYear);
			parmClass.addElement(sPeriod);
			parmClass.addElement(eYear);
			parmClass.addElement(ePeriod);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (getSalesHistForDataSet). " + e);
		}
			
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {
				find22file = connA.createStatement();
				rs22file   = find22file.executeQuery(sqlString);
					
				// for each record add or update a data set detail file entry.
				while (rs22file.next() && throwError.toString().equals(""))
				{
					//get the record in the data set if it exists
					//build the sql.
					
					try {
						requestType = "getDataSet32Record";
						Vector parmClass = new Vector();
						parmClass.addElement(fileName);
						parmClass.addElement(rs22file);
						sqlString = buildSqlStatement(requestType, parmClass);
					} catch (Exception e) {
						throwError.append("Error at build sql (getDataSet32Record). " + e);
					}
					
					//execute sql.
					if (throwError.toString().equals(""))
					{
						try {
							findDS   = connA.createStatement();
							rsDS     = findDS.executeQuery(sqlString);
							
							//update existing record.
							if (rsDS.next() && throwError.toString().equals(""))
							{
								//the amt field will be used here for accumulation into ivqt and saam.
								
								BigDecimal zero = new BigDecimal("0");
								
								BigDecimal ivqt = new BigDecimal(rsDS.getString("UCIVQT"));
								BigDecimal amt = new BigDecimal(rs22file.getString("UCIVQT"));
								ivqt = ivqt.add(amt);
								
								
								BigDecimal saam = new BigDecimal(rsDS.getString("UCSAAM"));
								amt             = new BigDecimal(rs22file.getString("UCSAAM"));
								saam = saam.add(amt);
								
								BigDecimal price = new BigDecimal("0");
								price.setScale(6);
							    int x = ivqt.compareTo(zero);
							      
							    if (x != 0)
							    {
							    	price = saam.divide(ivqt, 6, 4);
							    	price = price.setScale(6,4);
							    }
							    
							    //build the sql
							    try {
							    	requestType = "updateDataSet32From22file";
							    	Vector parmClass = new Vector();
							    	parmClass.addElement(fileName);
							    	parmClass.addElement(rsDS);
							    	parmClass.addElement(ivqt);
							    	parmClass.addElement(saam);
							    	parmClass.addElement(price);
							    	sqlString = buildSqlStatement(requestType, parmClass);
							    } catch (Exception e) {
							    	throwError.append("Error at build sql (updateDataSet32From22file). " + e);
							    }
							    
							    if (throwError.toString().equals(""))
							    {
							    	try {
							    		updateIt = connA.createStatement();
										updateIt.executeUpdate(sqlString);
										updateIt.close();
							    	} catch (Exception e) {
							    		throwError.append("Error at execute sql (updateDataSet32From22file). " + e);
							    	}
							    }
							      
							} else //addIt
							{
								try { //build sql
									BigDecimal zero = new BigDecimal("0");
									
									BigDecimal ivqt = new BigDecimal(rs22file.getString("UCIVQT"));								
									BigDecimal saam = new BigDecimal(rs22file.getString("UCSAAM"));
									
									BigDecimal madj = new BigDecimal("0");
									madj.setScale(6);
								    int x = ivqt.compareTo(zero);
								      
								    if (x != 0)
								    {
								    	madj = saam.divide(ivqt, 6, 4);
								    	madj = madj.setScale(6,4);
								    }
								    
									requestType = "addDataSet32From22";
									Vector parmClass = new Vector();
									parmClass.addElement(fileName);
									parmClass.addElement(rs22file);
									parmClass.addElement(madj);
									parmClass.addElement(ivqt);
									
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch (Exception e) {
									throwError.append("Error ar build sql (addDataSet32From22)");
								}
								
								if (throwError.toString().equals(""))
								{
									try {
										addIt = connA.createStatement();
										addIt.executeUpdate(sqlString);
										addIt.close();
									} catch (Exception e) {
										throwError.append("Error at execute sql (addDataSet32From22");
									}
								}
							}
						} catch (Exception e) {
							throwError.append(" Error at execute the sql (getDataSet32Record). " + e);
						}
						
						rsDS.close();
						findDS.close();
					}
				}
						
				find22file.close();
				rs22file.close();
			} catch(Exception e) {
				throwError.append("Error at execute sql (get22fileSalesForDataSet). " + e);
			}
		}
					
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
		
				
	finally
	{
		try
		{
			if (find22file != null)
				find22file.close();
		} catch(Exception e) {}//ignore
		try {
			if (findDS != null)
				findDS.close();
		} catch(Exception e) {}//ignore
		try {
			if (addIt != null)
				addIt.close();
		} catch(Exception e) {}//ignore
		try {
			if (updateIt != null)
				updateIt.close();
		} catch(Exception e) {}//ignore
		
		try
		{
			if (rs22file != null)
				rs22file.close();
			if (rsDS != null)
				rsDS.close();
		} catch(Exception e) {}//ignore
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("updateDataSetSalesFrom22File(");
		throwError.append("Vector");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}
	
	return vector;

}

/**
 * Load V14.1 Data Sets from V13 
 * with Record Types, Versions, and Years
 * 
 * @param from file
 * @param to file
 * @param from record type
 * @param to record type
 * @param budget version
 * @param fiscal year
 * @return 
 * @throws Exception
 */
public static void loadV14DataSetFrom34To33(String fromFile, String toFile, 
		                                    String fromRecordType, String toRecordType,
		                                    String version, String fiscalYear)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = com.treetop.services.ServiceConnection.getConnectionStack1();
		
		// execute the update method
		conn = loadV14DataSetFrom34To33(fromFile, toFile, fromRecordType, toRecordType, version, fiscalYear, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				com.treetop.services.ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceDataSet.");
		throwError.append("loadV14DataSetFrom33To34(");
		throwError.append(fromFile + ", ");
		throwError.append(toFile + ", ");
		throwError.append(fromRecordType + ", ");
		throwError.append(toRecordType + ", ");
		throwError.append(version + ", ");
		throwError.append(fiscalYear + ", ");
		throwError.append("). ");
	}
	
	return;
}

/**
 * Load V14.1 Data Sets from V13 data sets.
 * 
 * @param from file
 * @param to file
 * @param record type
 * @return Connection.
 * @throws Exception
 */
private static Connection loadV14DataSetFrom34To33(String fromFile, String toFile, 
										           String fromRecordType, String toRecordType,
										           String version, String fiscalYear, Connection conn)
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rs = null;
	PreparedStatement findThem  = null;
	java.sql.Statement addIt = null;	
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally 
	
		//GET THE V13 data set records.
		try 
		{
			Vector parmClass = new Vector();
			parmClass.addElement(fromFile);
			parmClass.addElement(fromRecordType);
			parmClass.addElement(toFile);
			parmClass.addElement(fiscalYear);
			requestType = "getV13RecordsWithFiscalYear";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (getV13RecordsWithFiscalYear). " + e);
		}
		
		// build and execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findThem = conn.prepareStatement(sqlString);
				rs       = findThem.executeQuery();
				
				//create the update statement here outside of the loop.
				addIt  = conn.createStatement();
				
				// for each record write a V14 entry.
				while (rs.next() && throwError.toString().equals(""))
				{
					
					try //build sql for V14 record add.
					{
						Vector parmClass = new Vector();
						parmClass.addElement(rs);
						parmClass.addElement(toFile);
						parmClass.addElement(toRecordType);
						parmClass.addElement(version);
						requestType = "addV14RecordsFrom34To33";
						sqlString = buildSqlStatement(requestType, parmClass);
					} catch (Exception e) {
						throwError.append("Error at build sql (addV14RecordsFrom34To33). " + e);
					}
	
					//execute add
					try
					{
						addIt.executeUpdate(sqlString);
									
									
					} catch (Exception e) {
						throwError.append("Error at add of V14 Records (addV14RecordsFrom34To33). " + e);
					}
				}
				
				addIt.close();
				
			
			} catch (Exception e) {
				throwError.append(" Error at build and execute the sql. " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findThem != null)
				findThem.close();
			if (addIt != null)
				addIt.close();
		} catch(Exception e){
		}
		
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("loadV14DataSetFrom34To33(String, String, String, ");
		throwError.append("String, String, String, Connection). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * Overlay Sales History into Forecast Records.
 * 
 * @param file name
 * @param version 
 * @param from year
 * @param from period
 * @param to year
 * @param to period 
 * @throws Exception
 */
public static void overlayDataSetSalesFrom22File(String fileName,   String version, String fromYear, 
		                                         String fromPeriod, String toYear,  String toPeriod)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = com.treetop.services.ServiceConnection.getConnectionStack1();
		
		// execute the update method
		conn = overlayDataSetSalesFrom22File(fileName, version, fromYear, fromPeriod, toYear, toPeriod, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				com.treetop.services.ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceDataSet.");
		throwError.append("overlayDataSetSalesFrom22File(");
		throwError.append(fileName + ", ");
		throwError.append(version + ", ");
		throwError.append(fromYear + ", ");
		throwError.append(fromPeriod + ", ");
		throwError.append(toYear + ", ");
		throwError.append(toPeriod + ", ");
		throwError.append("). ");
	}
	
	return;
}

/**
 * Overlay Sales History into Forecast Records.
 * 
 * @param file name
 * @param version 
 * @param from year
 * @param from period
 * @param to year
 * @param to period 
 * @param connection
 * @return connection
 * @throws Exception
 */
private static Connection overlayDataSetSalesFrom22File(String fileName, String version, 
										                String fromYear, String fromPeriod,
										                String toYear,   String toPeriod,
										                Connection conn)
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	Statement deleteIt = null;
	Statement updateIt = null;	
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally 
		
		//delete existing sales history for periods to be overlayed
		try {
			Vector parmClass = new Vector();
			parmClass.addElement(fileName);
			parmClass.addElement(fromYear);
			parmClass.addElement(fromPeriod);
			parmClass.addElement(toYear);
			parmClass.addElement(toPeriod);
			requestType = "deleteSalesHistoryFromVersion";
			sqlString = buildSqlStatement(requestType, parmClass);
			deleteIt  = conn.createStatement();
			deleteIt.executeUpdate(sqlString);
		} catch(Exception e) {
			throwError.append("Error at(deleteSalesHistoryFromVersion). " + e);
		}
		
		
		//modify record type and zero quantity, price, and sales dollars
		//for records to be overlayed.
		try {
			if (throwError.toString().equals(""))
			{
				Vector parmClass = new Vector();
				parmClass.addElement(fileName);
				parmClass.addElement(version);
				parmClass.addElement(fromYear);
				parmClass.addElement(fromPeriod);
				parmClass.addElement(toYear);
				parmClass.addElement(toPeriod);
				requestType = "updateVersionTo32";
				sqlString = buildSqlStatement(requestType, parmClass);
				updateIt  = conn.createStatement();
				updateIt.executeUpdate(sqlString);
			}
		} catch(Exception e) {
			throwError.append("Error at(updateVersionTo32). " + e);
		}
	
		//Run the Update Sales History for the requested years and periods
		try 
		{
			if (throwError.toString().equals(""))
			{
				updateDataSetSalesFrom22File(fileName, fromYear, fromPeriod, toYear, toPeriod);
			}
		} catch(Exception e) {
			throwError.append("Error at update of sales history. " + e);
		}
		
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
	
	
	//modify record type and version
	//for records to be overlayed.
	try {
		if (throwError.toString().equals(""))
		{
			Vector parmClass = new Vector();
			parmClass.addElement(fileName);
			parmClass.addElement(version);
			parmClass.addElement(fromYear);
			parmClass.addElement(fromPeriod);
			parmClass.addElement(toYear);
			parmClass.addElement(toPeriod);
			requestType = "updateVersionBackTo33";
			sqlString = buildSqlStatement(requestType, parmClass);
			updateIt  = conn.createStatement();
			updateIt.executeUpdate(sqlString);
		}
	} catch(Exception e) {
		throwError.append("Error at(updateVersionBackTo33). " + e);
	}
				
			
	finally
	{
		try {
			deleteIt.close();
		} catch(Exception e){
		}
		
		try
		{
			updateIt.close();
		} catch(Exception e){
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("overlayDataSetSalesFrom22File(String, String, String, ");
		throwError.append("String, String, String, Connection). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * Add missing entries by year/type.
 * 
 * @param file name
 * @param from year
 * @param from record type
 * @param from record version
 * @param add year
 * @param add record type
 * @param add record version 
 * @throws Exception
 */
public static void addMissingEntries(String fileName, String fromYear, String fromType, String fromVersion, 
		                                              String addYear,  String addType,  String addVersion,
		                                              String addNextYear)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = com.treetop.services.ServiceConnection.getConnectionStack1();
		
		// execute the update method
		conn = addMissingEntries(fileName, fromYear, fromType, fromVersion, addYear, addType, addVersion, addNextYear, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				com.treetop.services.ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceDataSet.");
		throwError.append("addMissingEntries(");
		throwError.append(fileName + ", ");
		throwError.append(fromYear + ", ");
		throwError.append(fromType + ", ");
		throwError.append(fromVersion + ", ");
		throwError.append(addYear + ", ");
		throwError.append(addType + ", ");
		throwError.append(addVersion + " ");
		throwError.append("). ");
	}
	
	return;
}

/**
 * Add missing entries by year/type.
 * 
 * @param file name
 * @param from year
 * @param from record type
 * @param from record version
 * @param add year
 * @param add record type
 * @param add record version 
 * @throws Exception
 */
private static Connection addMissingEntries(String fileName, String fromYear, 
										    String fromType, String fromVersion,
										    String addYear,  String addType,
										    String addVersion, String addNextYear,
										    Connection conn)
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	PreparedStatement findThem = null;
	ResultSet rs  = null;
	Statement addIt = null;	
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally 
		
		//find existing type/version entries not existing in second type/version 
		try {
			Vector parmClass = new Vector();
			parmClass.addElement(fileName);
			parmClass.addElement(fromYear);
			parmClass.addElement(fromType);
			parmClass.addElement(fromVersion);
			parmClass.addElement(addYear);
			parmClass.addElement(addType);
			parmClass.addElement(addVersion);
			
			requestType = "findMissingVersion";
			sqlString = buildSqlStatement(requestType, parmClass);
			findThem = conn.prepareStatement(sqlString);
			rs       = findThem.executeQuery();
		} catch(Exception e) {
			throwError.append("Error at(findMissingVersion). " + e);
		}
		
		
		//add additional entries
		try {
			while (rs.next() && throwError.toString().equals(""))
			{
				Vector parmClass = new Vector();
				parmClass.addElement(fileName);
				parmClass.addElement(addYear);
				parmClass.addElement(addType);
				parmClass.addElement(addVersion);
				parmClass.addElement(rs);
				
				requestType = "addMissingVersion";
				sqlString = buildSqlStatement(requestType, parmClass);
				addIt  = conn.createStatement();
				addIt.executeUpdate(sqlString);
				
				if (!addNextYear.equals(""))
				{
					parmClass = new Vector();
					parmClass.addElement(fileName);
					parmClass.addElement(addNextYear);
					parmClass.addElement(addType);
					parmClass.addElement(addVersion);
					parmClass.addElement(rs);
					
					requestType = "addMissingVersion";
					sqlString = buildSqlStatement(requestType, parmClass);
					addIt  = conn.createStatement();
					addIt.executeUpdate(sqlString);
				}
			}
		} catch(Exception e) {
			throwError.append("Error at(addMissingVersion). " + e);
		}
		
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try {
			findThem.close();
			rs.close();
		} catch(Exception e){
		}
		
		try
		{
			addIt.close();
		} catch(Exception e){
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("addMissingEntries(String, String, String, ");
		throwError.append("String, String, String, String, Connection). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}


/**
 * Build Comparision Work File For Price and Cost of Goods Sold.
 * 
 * @param Data set file fiscal year
 * @param Data set file record type
 * @param Data set file version
 * @param Company cost fiscal year
 * @throws Exception
 */
public static void buildComparisionWorkFileForPriceAndCogs(
		String chanel, String dataSetFiscalYear, String recordType, String version, String cogsFiscalYear, String priceDate)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = com.treetop.services.ServiceConnection.getConnectionStack1();
		
		// execute the update method
		conn = buildComparisionWorkFileForPriceAndCogs(chanel, dataSetFiscalYear, recordType, version, cogsFiscalYear, priceDate, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				com.treetop.services.ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceDataSet.");
		throwError.append("buildComparisionWorkFileForPriceAndCogs(");
		throwError.append(dataSetFiscalYear + ", ");
		throwError.append(recordType + ", ");
		throwError.append(version + ", ");
		throwError.append(cogsFiscalYear + ", ");
		throwError.append(priceDate + " ");
		throwError.append("). ");
	}
	
	return;
}


/**
 * Build Comparision Work File For Price and Cost of Goods Sold.
 * 
 * @param Data set file fiscal year
 * @param Data set file record type
 * @param Data set file version
 * @param Company cost fiscal year
 * @throws Exception
 */
private static Connection buildComparisionWorkFileForPriceAndCogs(
		String chanel, String dataSetFiscalYear, String recordType, String version, String cogsFiscalYear, 
		String priceDate, Connection conn)
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
	PreparedStatement findThem = null;
	ResultSet rs  = null;
	Statement addIt = null;
	Statement deleteIt = null;
	String requestType = "";
	String sqlString = "";
	
	//return file name while editing the chanel value.
	String fileName = getFileNameFromChanelName(chanel);
	
	try { //enable finally 
		
		//delete existing entries.
		try {
			Vector parmClass = new Vector();
			parmClass.addElement(chanel);
			parmClass.addElement(dataSetFiscalYear);
			parmClass.addElement(recordType);
			parmClass.addElement(version);
			
			requestType = "deleteComparisionData";
			sqlString = buildSqlStatement(requestType, parmClass);
			deleteIt  = conn.createStatement();
			deleteIt.executeUpdate(sqlString);
			
		} catch(Exception e) {
			throwError.append("Error at(deleteComparisionData). " + e);
		}
		
		
		//Load file with dataset data except new price (price2). 
		try {
			if (throwError.toString().equals(""))
			{
				Vector parmClass = new Vector();
				parmClass.addElement(fileName);
				parmClass.addElement(dataSetFiscalYear);
				parmClass.addElement(recordType);
				parmClass.addElement(version);
				parmClass.addElement(cogsFiscalYear);
				
				requestType = "findComparisionData";
				sqlString = buildSqlStatement(requestType, parmClass);
				findThem = conn.prepareStatement(sqlString);
				rs       = findThem.executeQuery();
			}
		} catch(Exception e) {
			throwError.append("Error at(findComparisionData). " + e);
		}
		
		
		//add additional entries
		try {
			while (rs.next() && throwError.toString().equals(""))
			{
				Vector parmClass = new Vector();
				parmClass.addElement(fileName);
				parmClass.addElement(chanel);
				parmClass.addElement(rs);
				
				requestType = "addComparisionData";
				sqlString = buildSqlStatement(requestType, parmClass);
				addIt  = conn.createStatement();
				addIt.executeUpdate(sqlString);
				
				if (throwError.toString().equals(""))
				{
					addIt  = conn.createStatement();
					addIt.executeUpdate(sqlString);
				}
			}
		} catch(Exception e) {
			throwError.append("Error at(addComparisionData). " + e);
		}
		
		//update comparision data price2 field.
		//BigDecimal amount = getPriceWithOsFields();
		
		
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try {
			findThem.close();
			rs.close();
		} catch(Exception e){
		}
		
		try
		{
			addIt.close();
		} catch(Exception e){
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("addMissingEntries(String, String, String, ");
		throwError.append("String, String, String, String, Connection). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}


private static String getFileNameFromChanelName(String chanel)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	String fileName = "";
	
	if (chanel.trim().equals("Retail"))
		fileName = "o100043";
		
	if (chanel.trim().equals("Military"))
		fileName = "o100044";
	
	if (chanel.trim().equals("Club"))
		fileName = "o100045";
	
	if (chanel.trim().equals("Mass Merchandising"))
		fileName = "o100046";
	
	if (chanel.trim().equals("Dollar Store"))
		fileName = "o100047";
	
	if (chanel.trim().equals("Export"))
		fileName = "o100048";
	
	if (chanel.trim().equals("Natural Foods"))
		fileName = "o100049";
	
	if (chanel.trim().equals("Private Label"))
		fileName = "o100054";
	
	if (chanel.trim().equals("Ingredient"))
		fileName = "o100050";
	
	if (chanel.trim().equals("Food Service Fresh"))
		fileName = "o100052";
	
	
	if (fileName.equals(""))
		throwError.append("Error: File name for chanel not found. ");
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("getFileNameFromChanelName(String).  ");
		
		throw new Exception(throwError.toString());
	}
	
	return fileName;
}

/**
 * Add Actuals and adjust forecast.
 * Modify UCVOL3 with actual variance.  
 * 
 * @param String fiscal year
 * @param String record type
 * @param String version
 * @return Nothing.
 * @throws Exception
 */
public static void updateForecastOffsetActuals(String fiscalYear, String recordType, String version)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	try {
		// get a connection to be sent to find methods
		conn = com.treetop.services.ServiceConnection.getConnectionStack1();
		updateForecastOffsetActuals(fiscalYear, recordType, version, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		if (conn != null)
		{
			try
			{
				com.treetop.services.ServiceConnection.returnConnectionStack1(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceDataSet.");
		throwError.append("updateForecastOffsetActuals(");
		throwError.append("fiscalYear, recordType, version). ");
		throw new Exception(throwError.toString());
	}
	return;
}

/**
 * Add Actuals and adjust forecast.
 * Modify UCVOL3 with actual variance.  
 * 
 * @param String fiscal year
 * @param String record type
 * @param String version
 * @return Nothing.
 * @throws Exception
 */
private static void updateForecastOffsetActuals(String fiscalYear, String recordType, 
		                                        String budgetVersion, Connection conn )			
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsActuals = null;
    ResultSet rsForecast = null;
    ResultSet rsOffset = null;
	PreparedStatement findThem = null;
	PreparedStatement findIt   = null;
	PreparedStatement updIt = null;
	PreparedStatement addIt = null;
	String requestType = "";
	String sqlString = "";
	String fromPeriod = "";
	String toPeriod = "";
	BigDecimal actualQty = null;
	BigDecimal forecastQty = null;
	BigDecimal variance = null;
	
	try
	{

		try { 
			// Set th UCVOL3 field to UCIVQT for the entire fiscal year.
			Vector sendParms = new Vector();
			sendParms.addElement(fiscalYear);
			sendParms.addElement(recordType);
			sendParms.addElement(budgetVersion);
			requestType = "setUcvol3ToUcivqtForVersion";
			sqlString = buildSqlStatement(requestType, sendParms);
			findThem = conn.prepareStatement(buildSqlStatement("setUcvol3ToUcivqtForVersion", sendParms));	
			updIt = conn.prepareStatement(sqlString);	
			updIt.executeUpdate();			
		} catch(Exception e)
		{
			throwError.append("Error occured setting UCVOL3 to UCIVQT. " + e);
		}

		
		//determine periods to update.
		if (throwError.toString().equals("") )
		{
			if (budgetVersion.equals("FOQ1"))
			{
				fromPeriod = "4";
				toPeriod   = "5";
			}
			
			if (budgetVersion.equals("FOQ2"))
			{
				fromPeriod = "8";
				toPeriod   = "9";
			}
		}

		
		
		//get sales history for from period from the 22 file exclude 0 quantity.
		if (throwError.toString().equals("") )
		{
			try {
				Vector sendParms = new Vector();
				sendParms.addElement(fiscalYear);
				sendParms.addElement(fromPeriod);
				requestType = "getActualsForOverlay";
				sqlString = buildSqlStatement(requestType, sendParms);
				
				findThem = conn.prepareStatement(sqlString);
				rsActuals = findThem.executeQuery();
			} catch (Exception e) {
				throwError.append("Error at getActualsForOverlay. " + e);
			}
		}
		
		
		//overlay actuals process.
		try {
			//update or add version records according to retrieved sales history.
			// get matching forecast record of actual obtained above if it exists.
			// if match exists determine variance of actual vs. budget.
			//  update ucvol3 in forecast with actual.
			//  update ucvol3 with variance in to period entry.
			// if match does not exist create forecast entry from actual
			//  and offseting forecast entry infollowing (to) period.
	
			while (throwError.toString().equals("") && rsActuals.next() )
			{
				//debug
				if (rsActuals.getString("UCCUNO").trim().equals("32391") && rsActuals.getString("UCITNO").trim().equals("103778") )
				{
					String stopHere = "yes";
				}
				
				//get matching forecast record for the actual.
				Vector sendParms = new Vector();
				sendParms.addElement(fiscalYear);
				sendParms.addElement(fromPeriod);
				sendParms.addElement(recordType);
				sendParms.addElement(budgetVersion);
				sendParms.addElement(rsActuals.getString("uccuno"));
				sendParms.addElement(rsActuals.getString("ucitno"));
				requestType = "getMatchingForecastEntry";
				sqlString = buildSqlStatement(requestType, sendParms);
				
				findIt = conn.prepareStatement(sqlString);
				rsForecast = findIt.executeQuery();
				
				if (rsForecast.next() && throwError.toString().equals(""))
				{
					//determine variance.
					actualQty = new BigDecimal(rsActuals.getString("UCIVQT"));
					forecastQty   = new BigDecimal(rsForecast.getString("UCIVQT"));
					variance = actualQty.subtract(forecastQty);
					
					//update the from period forecast record (UCVOL3) with actual qty.
					try {
						sendParms = new Vector();
						sendParms.addElement(rsForecast);
						sendParms.addElement(actualQty);
						requestType = "updateForecastVariance";
						sqlString = buildSqlStatement(requestType, sendParms);
						updIt = conn.prepareStatement(sqlString);	
						updIt.executeUpdate();
					} catch (Exception e) {
						throwError.append("Error at updateForecastVariance. " + e);
					}
					
					//update the to period forecast record (UCVOL3) with variance if it exists
					if (throwError.toString().equals(""))
					{
						//get matching forecast record for the actual.
						sendParms = new Vector();
						sendParms.addElement(fiscalYear);
						sendParms.addElement(toPeriod);
						sendParms.addElement(recordType);
						sendParms.addElement(budgetVersion);
						sendParms.addElement(rsActuals.getString("uccuno"));
						sendParms.addElement(rsActuals.getString("ucitno"));
						requestType = "getMatchingForecastEntry";
						sqlString = buildSqlStatement(requestType, sendParms);
						
						findIt = conn.prepareStatement(sqlString);
						rsForecast = findIt.executeQuery();
						
						if (rsForecast.next() && throwError.toString().equals(""))
						{
							//determine variance.
							forecastQty = new BigDecimal(rsForecast.getString("UCIVQT"));
							actualQty   = forecastQty.subtract(variance);
							
							//update the to period forecast record (UCVOL3) with variance agaianst qty.
							try {
								sendParms = new Vector();
								sendParms.addElement(rsForecast);
								sendParms.addElement(actualQty);
								requestType = "updateForecastVariance";
								sqlString = buildSqlStatement(requestType, sendParms);
								updIt = conn.prepareStatement(sqlString);	
								updIt.executeUpdate();
							} catch (Exception e) {
								throwError.append("Error at updateForecastVariance. " + e);
							}
						
						// no matching forecast record in to period to apply variance (add one). 	
						} else {
							try {
								sendParms = new Vector();
								sendParms.addElement(rsActuals.getString("OSCONO"));
								sendParms.addElement(recordType);
								sendParms.addElement(budgetVersion);
								sendParms.addElement(fiscalYear);
								sendParms.addElement(toPeriod);
								sendParms.addElement(rsActuals.getString("UCCUNO"));
								sendParms.addElement(rsActuals.getString("UCITNO"));
								sendParms.addElement(actualQty);
								sendParms.addElement("0");
								requestType = "addForecastVarianceRecord";
								sqlString = buildSqlStatement(requestType, sendParms);
								addIt = conn.prepareStatement(sqlString);	
								addIt.executeUpdate();
							} catch (Exception e) {
								throwError.append("Error at addForecastVarianceRecord (one). " + e);
							}
						}
					}
					
					
				//else no match found - add offsetting forecast entries.	
				} else {
					
					try {
						//add the from period forecast entry hold the actual found.
						actualQty = new BigDecimal(rsActuals.getString("UCIVQT"));
						
						//get item class if available.
						int itcl = 0;
						try {
							itcl = Integer.parseInt(rsActuals.getString("MMITCL").trim());
						} catch (Exception e) {
							String stopHere = "yes";
						}
						
					} catch (Exception e) {
						//ignore if an error occurs here
					}
					
					//from period entry.
					try {
						sendParms = new Vector();
						sendParms.addElement(rsActuals.getString("OSCONO"));
						sendParms.addElement(recordType);
						sendParms.addElement(budgetVersion);
						sendParms.addElement(fiscalYear);
						sendParms.addElement(fromPeriod);
						sendParms.addElement(rsActuals.getString("UCCUNO"));
						sendParms.addElement(rsActuals.getString("UCITNO"));
						sendParms.addElement(actualQty);
						sendParms.addElement("0");
						requestType = "addForecastVarianceRecord";
						sqlString = buildSqlStatement(requestType, sendParms);
						addIt = conn.prepareStatement(sqlString);	
						addIt.executeUpdate();
					} catch (Exception e) {
						throwError.append("Error at addForecastVarianceRecord (two). " + e);
					}
					
					//to period entry
					
					try {
	                    //find out if the record exists first.
						//get matching forecast record for the actual.
						sendParms = new Vector();
						sendParms.addElement(fiscalYear);
						sendParms.addElement(toPeriod);
						sendParms.addElement(recordType);
						sendParms.addElement(budgetVersion);
						sendParms.addElement(rsActuals.getString("uccuno"));
						sendParms.addElement(rsActuals.getString("ucitno"));
						requestType = "getMatchingForecastEntry";
						sqlString = buildSqlStatement(requestType, sendParms);
						
						findIt = conn.prepareStatement(sqlString);
						rsForecast = findIt.executeQuery();
						
						if (rsForecast.next() && throwError.toString().equals(""))
						{
							//determine variance.
							forecastQty   = new BigDecimal(rsForecast.getString("UCIVQT"));
							actualQty     = forecastQty.subtract(actualQty);
							
							//update the from period forecast record (UCVOL3) with actual qty.
							try {
								sendParms = new Vector();
								sendParms.addElement(rsForecast);
								sendParms.addElement(actualQty);
								requestType = "updateForecastVariance";
								sqlString = buildSqlStatement(requestType, sendParms);
								updIt = conn.prepareStatement(sqlString);	
								updIt.executeUpdate();
							} catch (Exception e) {
								throwError.append("Error at updateForecastVariance. " + e);
							}
						} else
						{
							actualQty = actualQty.multiply(new BigDecimal("-1"));
							sendParms = new Vector();
							sendParms.addElement(rsActuals.getString("OSCONO"));
							sendParms.addElement(recordType);
							sendParms.addElement(budgetVersion);
							sendParms.addElement(fiscalYear);
							sendParms.addElement(toPeriod);
							sendParms.addElement(rsActuals.getString("UCCUNO"));
							sendParms.addElement(rsActuals.getString("UCITNO"));
							sendParms.addElement(actualQty);
							sendParms.addElement("0");
							requestType = "addForecastVarianceRecord";
							sqlString = buildSqlStatement(requestType, sendParms);
							addIt = conn.prepareStatement(sqlString);	
							addIt.executeUpdate();
						}
					} catch (Exception e) {
						throwError.append("Error at addForecastVarianceRecord (three). " + e);
					}
					
				}	
			}
		} catch (Exception e) {
			throwError.append("Error in the overlay actuals process. " + e);
		}

		
		//Set all initial budget/forecast records to zero if no sales history exists. 
		//Add the values zeroed to the following period. This eliminates any values from
		// the initial period that are not sales history without losing budget/forcast values
		// for the entire year. 
		
		//Find existing forecast records for the starting period that had no sales history.
		//These are records where UCIVQT and UCVOL3 are equal. 
		
		try {
			
			if (throwError.toString().equals("") )
			{
				try {
					Vector sendParms = new Vector();
					sendParms.addElement(fiscalYear);
					sendParms.addElement(fromPeriod);
					sendParms.addElement(recordType);
					sendParms.addElement(budgetVersion);
					
					requestType = "getForecastWithoutActuals";
					sqlString = buildSqlStatement(requestType, sendParms);
					
					findThem = conn.prepareStatement(sqlString);
					rsForecast = findThem.executeQuery();
					
					while(rsForecast.next() && throwError.toString().equals("") )
					{
						//see if an actual exists
						sendParms = new Vector();
						sendParms.addElement(rsForecast);
						requestType = "checkForActual";
						sqlString = buildSqlStatement(requestType, sendParms);
						findIt = conn.prepareStatement(sqlString);
						rsActuals = findIt.executeQuery();
						
						if (!rsActuals.next())
						{
							variance  = rsForecast.getBigDecimal("UCVOL3");
							actualQty = BigDecimal.ZERO;
							
							sendParms = new Vector();
							sendParms.addElement(rsForecast);
							sendParms.addElement(actualQty);
							requestType = "updateForecastVariance";
							sqlString = buildSqlStatement(requestType, sendParms);
							updIt = conn.prepareStatement(sqlString);	
							updIt.executeUpdate();
							
							
		                    //find out if the record exists first.
							//get matching forecast record for the actual.
							sendParms = new Vector();
							sendParms.addElement(fiscalYear);
							sendParms.addElement(toPeriod);
							sendParms.addElement(recordType);
							sendParms.addElement(budgetVersion);
							sendParms.addElement(rsForecast.getString("uccuno"));
							sendParms.addElement(rsForecast.getString("ucitno"));
							requestType = "getMatchingForecastEntry";
							sqlString = buildSqlStatement(requestType, sendParms);
							
							findIt = conn.prepareStatement(sqlString);
							rsOffset = findIt.executeQuery();
							
							if (rsOffset.next() && throwError.toString().equals(""))
							{
								//determine variance.
								forecastQty   = new BigDecimal(rsOffset.getString("UCVOL3"));
								actualQty     = forecastQty.add(variance);
								
								//update the to period forecast record (UCVOL3) with additional variance.
								try {
									sendParms = new Vector();
									sendParms.addElement(rsOffset);
									sendParms.addElement(actualQty);
									requestType = "updateForecastVariance";
									sqlString = buildSqlStatement(requestType, sendParms);
									updIt = conn.prepareStatement(sqlString);	
									updIt.executeUpdate();
								} catch (Exception e) {
									throwError.append("Error at updateForecastVariance. " + e);
								}
							} else
							{
								sendParms = new Vector();
								sendParms.addElement(rsForecast.getString("OSCONO"));
								sendParms.addElement(recordType);
								sendParms.addElement(budgetVersion);
								sendParms.addElement(fiscalYear);
								sendParms.addElement(toPeriod);
								sendParms.addElement(rsForecast.getString("UCCUNO"));
								sendParms.addElement(rsForecast.getString("UCITNO"));
								sendParms.addElement(variance);
								sendParms.addElement("0");
								requestType = "addForecastVarianceRecord";
								sqlString = buildSqlStatement(requestType, sendParms);
								addIt = conn.prepareStatement(sqlString);	
								addIt.executeUpdate();
							}
						}
					}
					
				} catch (Exception e) {
					throwError.append("Error at getForecastWithoutActuals. " + e);
				}
			}
		
		} catch (Exception e) {
			throwError.append("Error moving forecast without sales to following period. ");
		}
				
	}catch(Exception e)
	{
		throwError.append(" error occured. " + e);
	}
	finally
	{
		try
		{
			if (findThem != null)
				findThem.close();
			if (findIt != null)
				findIt.close();
		} catch(Exception e){
		}
		
		try
		{
			if (rsActuals != null)
				rsActuals.close();
			if (rsForecast != null)
				rsForecast.close();
		} catch(Exception e){
		}
		
		try
		{
			if (updIt != null)
				updIt.close();
			if (addIt != null)
				addIt.close();
		} catch(Exception e){
		}		
	}	
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceDataSet.");
		throwError.append("updateForeCastOffsetActuals(");
		throwError.append(fiscalYear + ", " + recordType + ", ");
		throwError.append(budgetVersion + ", conn) ");
		throw new Exception(throwError.toString());
	}

  return;
}

}
