/*
 * Created on June 17, 2008
 * Tom Haile
 *
 */
package com.treetop.services;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Vector;
import com.treetop.businessobjects.DataSet;
import com.treetop.businessobjects.DateTime;
import com.treetop.utilities.*;

/**
 * @author thaile
 *
 * Services class to obtain and return data 
 * to business objects.
 * Special Services Object.
 */
public class ServiceSalesWorkFiles extends BaseService{

	public static final String dblib   = "DBPRD";
	public static final String library = "M3DJDPRD";
	//public static final String libraryx = "APDEV";
	
	/**
	 *  Construction of the Base inforamtion
	 */
	public ServiceSalesWorkFiles() {
		super();
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
		
		try { 
		//******************************************************************
	    //*** LIST SECTION
		//******************************************************************
		   
		   if (inRequestType.equals("getCustomerShipTo"))
		   {
		   	  String cuno = (String) requestClass.elementAt(0);
		   	  String adid = (String) requestClass.elementAt(1);
		   	  sqlString.append("SELECT * ");
		   	  sqlString.append("FROM " + library + ".OCUSAD ");
		   	  sqlString.append(" WHERE OPCUNO = '" + cuno + "' ");
		   	  sqlString.append(" AND OPADID = '" + adid + "' ");
		   }
		   
		   
		   if (inRequestType.equals("getFromToDates"))
		   {
		      String fYear = (String) requestClass.elementAt(0);
		      String fPeriod = (String) requestClass.elementAt(1);
		      sqlString.append("SELECT * ");
		      sqlString.append("FROM " + library + ".CSYPER ");
		      sqlString.append("WHERE CPCONO = 100 ");
		      sqlString.append("AND CPDIVI = '   ' ");
		      sqlString.append("AND CPPETP = 1 ");
		      sqlString.append("AND CPYEA4 = " + fYear + " ");
		      sqlString.append("AND CPPERI = " + fPeriod + " ");
		   }
		   
		   
		   if (inRequestType.equals("getFiscalData"))
		   {
		   	  String actualDate = (String) requestClass.elementAt(0);
		   	  String type        = (String) requestClass.elementAt(1);
		   	  sqlString.append("Select * ");
		   	  sqlString.append("From " + library + ".CSYPER ");
		   	  sqlString.append("WHERE CPCONO = 100 ");
		   	  sqlString.append("AND CPDIVI = '100' ");//changed from div - '' 20090414.
		      sqlString.append("AND CPPETP = " + type + " ");
		      sqlString.append("AND CPFDAT <= " + actualDate + " ");
		      sqlString.append("AND CPTDAT >= " + actualDate + " ");
		   }
		   
		   
		   
		   if (inRequestType.equals("salesOSBSTDInvoiceDateRange"))
		   {
		   	  String fromDate = (String) requestClass.elementAt(0);
		   	  String toDate   = (String) requestClass.elementAt(1);
		   	  sqlString.append("SELECT  UCFACI, UCORNO, UCPONR, UCIVNO, UCIVDT, UCDLIX, ");
		   	  sqlString.append("UCAGNT, UCAGNO, UCCUNO, UCADID, UCORTP, UCWHLO, UCITNO, ");
		   	  sqlString.append("UCPYNO, UCIVQT, UCIVQA, UCGRWE, UCSAAM, UCSTUN, UCALUN, ");
		   	  sqlString.append("UCSPUN, UCCMP1, UCDIA1, UCDIA2, UCDIA3, UCDIA4, UCDIA5, ");
		   	  sqlString.append("UCDIA6, UCINRC, UCCUCL, UCITCL, UCDLIX ");
		   	  sqlString.append("FROM " + library + ".OSBSTDZ8 ");
		   	  sqlString.append("WHERE UCIVDT >= " + fromDate + " ");
		   	  sqlString.append("AND UCIVDT <= " + toDate + " ");
		   	  //sqlString.append("AND UCIVNO <> 0 ");
		   }
		   
		   
		   if (inRequestType.equals("salesOEPVXInvoiceDateRangeAll"))
		   {
		   	  String fiscalYear    = (String) requestClass.elementAt(0);
		   	  String fiscalPeriod  = (String) requestClass.elementAt(1);
		   	  sqlString.append("SELECT  * ");
		   	  sqlString.append("FROM  HSLIB.OEPVX ");
		   	  sqlString.append("LEFT OUTER JOIN LAWSON.CNVCOA ");
		   	  sqlString.append("ON SUBSTR(VXACCT,1,3) = X1CO ");
		   	  sqlString.append("AND SUBSTR(VXACCT,5,3) = X1ORG ");
		   	  sqlString.append("AND SUBSTR(VXACCT,9,3) = X1CCTR ");
		   	  sqlString.append("AND SUBSTR(VXACCT,13,10) = X1ACCT ");
		   	  sqlString.append("WHERE VXACYR = " + fiscalYear + " ");
		   	  sqlString.append("AND VXACMN = " + fiscalPeriod + " ");
		   }
		   
		   
		   
		   if (inRequestType.equals("getOsbstdByCustomer"))
		   {
		   	  sqlString.append("SELECT UCORNO, UCCUNO, UCCUCL, UCSMCD ");
		   	  sqlString.append("FROM " + library + ".OSBSTD ");
		   	  sqlString.append("ORDER BY UCCUNO, UCORNO ");
		   }
		   
		   
		   
		   if (inRequestType.equals("getZ100003ByCustomer"))
		   {
		   	  sqlString.append("SELECT UCCUNO ");
		   	  sqlString.append("FROM " + dblib + ".Z100003 ");
		   	  sqlString.append("ORDER BY UCCUNO ");
		   }
		   
		   
		   
		   if (inRequestType.equals("getOsbstdByItem"))
		   {
		   	  sqlString.append("SELECT UCORNO, UCITNO, UCITCL ");
		   	  sqlString.append("FROM " + library + ".OSBSTD ");
		   	  sqlString.append("WHERE UCITNO <> '          ' ");
		   	  sqlString.append("ORDER BY UCITNO, UCORNO ");
		   }
		   
		   
		   
		   if (inRequestType.equals("getZ100003ByItem"))
		   {
		   	  sqlString.append("SELECT UCITNO ");
		   	  sqlString.append("FROM " + dblib + ".Z100003 ");
		   	  sqlString.append("WHERE UCITNO <> '          ' ");
		   	  sqlString.append("ORDER BY UCITNO ");
		   }
		   
		   
		   
		   if (inRequestType.equals("getInvoiceLineRecord"))
		   {
		      String invoiceNo = (String) requestClass.elementAt(0);
		      String lineNo    = (String) requestClass.elementAt(1);
		      String delvNo    = (String) requestClass.elementAt(2);
		      sqlString.append("SELECT * ");
		      sqlString.append("FROM " + dblib + ".Z100003 ");
		      sqlString.append("WHERE UCIVNO = " + invoiceNo + " ");
		      sqlString.append("AND   UCPONR = " + lineNo + " ");
		      sqlString.append("AND   UCDLIX = " + delvNo + " ");
		   }

		   
		   
		   if (inRequestType.equals("getAgreementQuantity"))
		   {
		   	  String cuno = (String) requestClass.elementAt(0);
		   	  String agno = (String) requestClass.elementAt(1);
		   	  String itno = (String) requestClass.elementAt(2);
		   	  sqlString.append("SELECT UWCUNO, UWAGNO, UWOBV1, UWAGQT ");
		   	  sqlString.append("FROM " + library + ".OAGRLN ");
		   	  sqlString.append("WHERE UWCUNO = '" + cuno + "' ");
		   	  sqlString.append("AND   UWAGNO = '" + agno + "' ");
		   	  sqlString.append("AND   UWOBV1 = '" + itno + "' ");
		   }
		   
		   
		   if (inRequestType.equals("getCustomerMasterByCustomer") )
		   {
		   	  String cuno = (String) requestClass.elementAt(0);
		   	  
		   	  sqlString.append("SELECT OKCUNO, OKCUNM, OKFACI, OKCUCL, " );
		   	  sqlString.append("OKSMCD, OKSDST, OKCFC3, OKFRE1, OKFRE2, ");
		   	  sqlString.append("OKCFC5, OKCFC0, OKAGNT, OKPYNO, OKINRC, OKCUST ");
		   	  sqlString.append("FROM " + library + ".OCUSMA ");
		   	  sqlString.append("WHERE OKCUNO = '" + cuno + "' ");
		   }
		   
		   
		   if (inRequestType.equals("getItemMaster"))
		   {
		   	  String itno = (String) requestClass.elementAt(0);
		   	  sqlString.append("SELECT  MMITNO, MMFUDS, MMUNMS, MMACRF, ");
		   	  sqlString.append("MMITTY, MMITCL, MMITGR, MMCFI1, MMITDS, ");
		   	  sqlString.append("MMBUAR ");
		   	  sqlString.append("FROM " + library + ".MITMAS ");
		   	  sqlString.append("WHERE MMCONO = 100 ");
		   	  sqlString.append("AND   MMITNO = '" + itno.trim() + "' ");
		   }
		   
		   
		   if (inRequestType.equals("getWarehouseMaster"))
		   {
		   	  String whlo = (String) requestClass.elementAt(0);
		   	  sqlString.append("SELECT MWCONO, MWWHLO, MWWHNM " );
		   	  sqlString.append("FROM " + library + ".MITWHL ");
		   	  sqlString.append("WHERE MWCONO = 100 ");
		   	  sqlString.append("AND   MWWHLO = '" + whlo + "' ");
		   }
		   
		   
		   if (inRequestType.equals("getFacilityMaster"))
		   {
		   	  String facility = (String) requestClass.elementAt(0);
		   	  sqlString.append("SELECT CFCONO, CFFACI, CFFACN " );
		   	  sqlString.append("FROM " + library + ".CFACIL ");
		   	  sqlString.append("WHERE CFCONO = 100 ");
		   	  sqlString.append("AND   CFFACI = '" + facility + "' ");
		   }
		   
		   if (inRequestType.equals("getCsytab"))
		   {
		   	  String key1 = (String) requestClass.elementAt(0);
		   	  String key2 = (String) requestClass.elementAt(1);
		   	  sqlString.append("SELECT CTCONO, CTDIVI, CTSTCO, CTTX40, CTTX15 " );
		   	  sqlString.append("FROM " + library + ".CSYTAB ");
		   	  sqlString.append("WHERE CTCONO = 100 AND CTDIVI = '   ' ");
		   	  sqlString.append("AND   CTSTCO = '" + key1 + "' ");
		   	  sqlString.append("AND   CTSTKY = '" + key2 + "' ");
		   }
		   
		   
		   if (inRequestType.equals("getZ100003ZeroFiscalYear"))
		   {
		   	  sqlString.append("SELECT UCORNO, UCPONR, UCIVNO, UCDLIX, " );
		   	  sqlString.append("CPYEA4, CPPERI, CPTX15, FWEEK, UTACDT ");
		   	  sqlString.append("FROM " + dblib + ".Z100003 ");
		   	  sqlString.append("WHERE CPYEA4 = 0 "); 
		   	  //sqlString.append("WHERE FWEEK = 0 ");
		   	  sqlString.append("AND   UCIVNO <> 0 ");
		   	  sqlString.append("AND   UTACDT <> 0 ");
		   }
		   
		   
		   if (inRequestType.equals("getZ100003AtZero"))
		   {
		   	  sqlString.append("SELECT UCORNO, UCPONR, UCIVNO " );
		   	  sqlString.append("FROM " + dblib + ".Z100003 ");
		   	  sqlString.append("WHERE UCPONR = 0 ");
		   	  sqlString.append("AND   UCCUNO = '          ' ");
		   }
		   
		   
		   if (inRequestType.equals("getZ100003NotZero"))
		   {
		   	  String invoiceNo = (String) requestClass.elementAt(0);
		   	  sqlString.append("SELECT * " );
		   	  sqlString.append("FROM " + dblib + ".Z100003 ");
		   	  sqlString.append("WHERE UCIVNO = " + invoiceNo + " ");
		   	  sqlString.append("AND   UCPONR <> 0 ");
		   }
		   
		   
		   if (inRequestType.equals("getZeroSequenceEntriesInOINACC"))
		   {
		   	  sqlString.append("SELECT * " );
		   	  sqlString.append("FROM " + library + ".OINACCZ10 AS A ");
		   	  sqlString.append("INNER JOIN " + library + ".FCHACC ON ");
		   	  sqlString.append("A.UTAIT1 = EAAITM ");
		   	  sqlString.append("WHERE NOT EXISTS ");
		   	  sqlString.append("(SELECT * FROM " + dblib + ".Z100003U3 AS B ");
		   	  sqlString.append("WHERE B.UTIVNO = A.UTIVNO ");
		   	  sqlString.append("AND B.UCPONR = 0 ) ");
		   	  sqlString.append("AND A.UTIVDT > 20100801 ");
		   	  //sqlString.append("AND A.UTIVDT < 20110122 "); //temporary
		   	  sqlString.append("AND EAAITP = 1 AND EADIVI = '100' ");
		   	  sqlString.append("ORDER BY A.UTIVNO ");
		   }
		   
		   
		   if (inRequestType.equals("getNonZeroSequenceEntriesInOINACC"))
		   {
		   	  sqlString.append("SELECT * " );
		   	  sqlString.append("FROM " + library + ".OINACCZ11 AS A ");
		   	  sqlString.append("INNER JOIN " + library + ".FCHACC ON ");
		   	  sqlString.append("A.UTAIT1 = EAAITM ");
		   	  sqlString.append("WHERE NOT EXISTS ");
		   	  sqlString.append("(SELECT * FROM " + dblib + ".Z100003U2 AS B ");
		   	  sqlString.append("WHERE B.UTIVNO = A.UTIVNO) ");
		   	  sqlString.append("AND A.UTIVDT > 20100801 ");
		   	  //sqlString.append("AND A.UTIVDT < 20110122 "); //temporary
		   	  sqlString.append("AND EAAITP = 1 AND EADIVI = '100' ");
		   	  sqlString.append("ORDER BY A.UTIVNO, SUBSTR(A.UTIVRF,1,5), A.UTDLIX ");
		   }
		   
		   
		   if (inRequestType.equals("getZ100003WithNoCustomer"))
		   {
		   	  sqlString.append("SELECT A.UTIVNO AS aUTIVNO, " );
		   	  sqlString.append("A.UCPONR AS aUCPONR, ");
		   	  sqlString.append("A.UCORNO AS aUCORNO, ");
		   	  sqlString.append("A.UTACDT AS aUTACDT, ");
		   	  sqlString.append("B.UCCUNO AS bUCCUNO, ");
		   	  sqlString.append("B.UCADID AS bUCADID, ");
		   	  sqlString.append("B.UCORTP AS bUCORTP, ");
		   	  sqlString.append("B.UCWHLO AS bUCWHLO, ");
		   	  sqlString.append("B.UCINRC AS bUCINRC, ");
		   	  sqlString.append("B.UCAGNT AS bUCAGNT, ");
		   	  sqlString.append("B.UCORNO AS bUCORNO ");
		   	  sqlString.append("FROM " + dblib + ".Z100003 A ");
		   	  sqlString.append("INNER JOIN " + library + ".OSBSTD B ON ");
		   	  sqlString.append("A.UCORNO = B.UCORNO ");
		   	  sqlString.append("AND A.UCPONR = B.UCPONR ");
		   	  sqlString.append("WHERE A.UCCUNO = '  ' ");;
		   	  sqlString.append("ORDER BY bUCCUNO, aUTIVNO, aUCPONR DESC ");
		   }
		   
		   
		   if (inRequestType.equals("getZ100003WithNoItem"))
		   {
		   	  sqlString.append("SELECT A.UTIVNO AS aUTIVNO, " );
		   	  sqlString.append("A.UCPONR AS aUCPONR, ");
		   	  sqlString.append("A.UCDLIX AS aUCDLIX, ");
		   	  sqlString.append("B.UCIVDT AS bUCIVDT, ");
		   	  sqlString.append("B.UCDLIX AS bUCDLIX, ");
		   	  sqlString.append("B.UCAGNO AS bUCAGNO, ");
		   	  sqlString.append("B.UCITNO AS bUCITNO, ");
		   	  sqlString.append("B.UCIVQT AS bUCIVQT, ");
		   	  sqlString.append("B.UCIVQA AS bUCIVQA, ");
		   	  sqlString.append("B.UCGRWE AS bUCGRWE, ");
		   	  sqlString.append("B.UCSAAM AS bUCSAAM, ");
		   	  sqlString.append("B.UCSTUN AS bUCSTUN, ");
		   	  sqlString.append("B.UCALUN AS bUCALUN, ");
		   	  sqlString.append("B.UCSPUN AS bUCSPUN, ");
		   	  sqlString.append("B.UCCMP1 AS bUCCMP1, ");
		   	  sqlString.append("B.UCDIA1 AS bUCDIA1, ");
		   	  sqlString.append("B.UCDIA2 AS bUCDIA2, ");
		   	  sqlString.append("B.UCDIA3 AS bUCDIA3, ");
		   	  sqlString.append("B.UCDIA4 AS bUCDIA4, ");
		   	  sqlString.append("B.UCDIA5 AS bUCDIA5, ");
		   	  sqlString.append("B.UCCUNO AS bUCCUNO, ");
		   	  sqlString.append("B.UCDIA6 AS bUCDIA6 ");
		   	  sqlString.append("FROM " + dblib + ".Z100003 A ");
		   	  sqlString.append("INNER JOIN " + library + ".OSBSTD B ON "); 
		   	  sqlString.append("A.UCORNO = B.UCORNO ");
		   	  sqlString.append("AND A.UCPONR = B.UCPONR ");
		   	  sqlString.append("AND A.UCDLIX = B.UCDLIX ");
		   	  sqlString.append("WHERE A.UCITNO = '  ' ");;
		   	  sqlString.append("ORDER BY bUCITNO, aUTIVNO, aUCPONR DESC ");
		   }
		   
		   
		   if (inRequestType.equals("getZ100003WithNoSalesDollars"))
		   {
		   	  sqlString.append("SELECT * " );
		   	  sqlString.append("FROM " + dblib + ".Z100003 ");
		   	  sqlString.append("WHERE UCPONR <> 0 ");
		   	  sqlString.append("AND   SLAMT  = 0 ");
		   }

		   
		   if (inRequestType.equals("getOldDataSetFile"))
		   {
		     String fileName    = (String) requestClass.elementAt(0);
		     String year        = (String) requestClass.elementAt(1);
		     String period      = (String) requestClass.elementAt(2);
		     String fromType    = (String) requestClass.elementAt(3);
		     String fromVersion = (String) requestClass.elementAt(4);
		     
		   	 sqlString.append("SELECT a.OSCONO as aOSCONO, ");
		   	 sqlString.append("a.OSDIVI as aOSDIVI, a.OSSSTT as aOSSSTT, ");
		   	 sqlString.append("a.OSBVER as aOSBVER, a.OSLEVL as aOSLEVL, ");
		   	 sqlString.append("a.OSYEA4 as aOSYEA4, a.OSPERI as aOSPERI, ");
		   	 
		   	 if (fileName.equals("o100012") ||
		   	 	 fileName.equals("o100013") ||
				 fileName.equals("o100014") ||
		   	 	 fileName.equals("o100015") ||
				 fileName.equals("o100017") ||
				 fileName.equals("o100018") ||
				 fileName.equals("o100019") ||
				 fileName.equals("o100021") ||
				 fileName.equals("o100025") )
				 
		   	 {
		   	 	sqlString.append("a.MMCFI1 as aMMCFI1,");
		   	 } else
		   	 {
		   	 	sqlString.append("a.UCCUCL as aUCCUCL, ");
		   	 }
		   	 sqlString.append("a.UCSMCD as aUCSMCD, a.UCITCL as aUCITCL, ");
		   	 sqlString.append("a.OKCFC3 as aOKCFC3, a.UCCUNO as aUCCUNO, ");
		   	 sqlString.append("a.UCITNO as aUCITNO, ");
		   	 
		   	 sqlString.append("a.UCDEMA as aUCDEMA, a.UCIVQT as aUCIVQT, ");
		   	 sqlString.append("a.UCSAAM as aUCSAAM, a.UCDIA1 as aUCDIA1, ");
		   	 sqlString.append("a.UCDIA2 as aUCDIA2, a.UCDIA1 as aUCDIA3, ");
		   	 sqlString.append("a.UCDIA4 as aUCDIA4, a.UCALBO as aUCALBO, ");
		   	 sqlString.append("a.UCUCOS as aUCUCOS, a.MFCFOR as aMFCFOR, ");
		   	 sqlString.append("a.MFMFOR as aMFMFOR, a.MFNCFO as aMFNCFO, ");
		   	 sqlString.append("a.MFMADJ as aMFMADJ, a.MFALFF as aMFALFF, ");
		   	 sqlString.append("a.MFMADV as aMFMADV, a.MFAVER as aMFAVER, ");
		   	 sqlString.append("a.MFALA1 as aMFALA1, a.MFALA2 as aMFALA2, ");
		   	 sqlString.append("a.MFALA3 as aMFALA3, a.MFALAO as aMFALAO, ");
		   	 sqlString.append("a.MFTREF as aMFTREF, a.MFTREQ as aMFTREQ, ");
		   	 sqlString.append("a.MFSEAF as aMFSEAF, a.MFPSTA as aMFPSTA, ");
		   	 
		   	 sqlString.append("b.MMITNO as bMMITNO, b.MMITCL as bMMITCL, ");
		   	 sqlString.append("b.MMCFI1 as bMMCFI1, c.OKCUNO as cOKCUNO, ");
		   	 sqlString.append("c.OKCUCL as cOKCUCL, c.OKSMCD as cOKSMCD, ");
		   	 sqlString.append("c.OKCFC3 as cOKCFC3 ");
		   	 
		     sqlString.append("FROM " + "BUDGTEMP" + "." + fileName + " AS a ");
		     sqlString.append("LEFT OUTER JOIN " + library + ".MITMAS AS b ");
		     sqlString.append("ON a.OSCONO = b.MMCONO ");
		     sqlString.append("AND a.UCITNO = b.MMITNO ");
		     sqlString.append("LEFT OUTER JOIN " + library + ".OCUSMA AS c ");
		     sqlString.append("ON a.OSCONO = c.OKCONO ");
		     sqlString.append("AND a.UCCUNO = c.OKCUNO ");
		     sqlString.append("WHERE a.OSSSTT = " + fromType + " ");
		     sqlString.append("AND a.OSYEA4 = " + year + " ");
		     sqlString.append("AND a.OSPERI = " + period + " ");
		     sqlString.append("AND a.OSBVER = '" + fromVersion + "' ");
		     sqlString.append("ORDER BY a.OSCONO, a.OSDIVI, a.OSSSTT, a.OSBVER, ");
		     sqlString.append("a.OSLEVL, a.OSYEA4, a.OSPERI, a.UCCUNO, a.UCITNO ");
		   }
		   
		   
		   
		   if (inRequestType.equals("getNewDataSetRecord"))
		   {
		   	 String fileName  = (String) requestClass.elementAt(0);
		   	 ResultSet rs     = (ResultSet) requestClass.elementAt(1);
		   	 String cucl      = (String) requestClass.elementAt(2);
		   	 String smcd      = (String) requestClass.elementAt(3);
		   	 String cfc3      = (String) requestClass.elementAt(4);
		   	 String itcl      = (String) requestClass.elementAt(5);
		   	 String cfi1      = (String) requestClass.elementAt(6);
		   	 String toType    = (String) requestClass.elementAt(7);
		   	 String toVersion = (String) requestClass.elementAt(8);
		   	 
		   	 sqlString.append("SELECT * ");
		   	 sqlString.append("FROM " + library + "." + fileName + " ");
		   	 //sqlString.append("FROM " + "THAILE" + "." + fileName + " ");
		   	 sqlString.append("WHERE OSCONO = " + rs.getString("aOSCONO") + " ");
		   	 sqlString.append("AND OSDIVI = '" + rs.getString("aOSDIVI") + "' ");
		   	 sqlString.append("AND OSSSTT = " + toType + " ");
		   	 sqlString.append("AND OSBVER = '" + toVersion + "' ");
		   	 sqlString.append("AND OSLEVL = " + rs.getString("aOSLEVL") + " ");
		   	 sqlString.append("AND OSYEA4 = " + rs.getString("aOSYEA4") + " ");
		   	 sqlString.append("AND OSPERI = " + rs.getString("aOSPERI") + " ");
		   	 sqlString.append("AND UCITNO = '" + rs.getString("aUCITNO") + "' ");
		   	 sqlString.append("AND UCITCL = '" + itcl + "' ");
		   	 sqlString.append("AND UCSMCD = '" + smcd + "' ");
		   	 sqlString.append("AND UCCUNO = '" + rs.getString("aUCCUNO") + "' ");
		   	 
			 if (fileName.equals("o100012") ||
				 fileName.equals("o100013") ||
				 fileName.equals("o100014") ||
				 fileName.equals("o100015") ||
				 fileName.equals("o100017") ||
				 fileName.equals("o100018") ||
				 fileName.equals("o100019") ||
				 fileName.equals("o100021") ||
				 fileName.equals("o100025") )
			 {
			 	sqlString.append("AND MMCFI1 = '" + cfi1 + "' ");
			 } else
			 {
			 	sqlString.append("AND UCCUCL = '" + cucl + "' ");
			 }
			 
		   	 sqlString.append("AND OKCFC3 = '" + cfc3 + "' ");
		   }
		   
		   
		   
		   if (inRequestType.equals("getZfileSalesForDataSet"))
		   {
		   	 String fileName = (String) requestClass.elementAt(0);
		   	 String sYear    = (String) requestClass.elementAt(1);
		   	 String sPeriod  = (String) requestClass.elementAt(2);
		   	 String eYear    = (String) requestClass.elementAt(3);
		   	 String ePeriod  = (String) requestClass.elementAt(4);
		   	 String where = "";
		   	 
		   	 //if (fileName.equals("o100001"))//BDGT2 Concentrate
		   	 //{
		   	 	//where = "WHERE MMITTY = '150' AND OKCUCL = '109' ";
		   	 //}
		   	 
		   	 if (fileName.equals("o100002"))//BDGT1 Dried Frozen
		   	 {
		   	 	//where = "WHERE MMITTY <> '150' AND OKCUCL = '109' ";
		   		where = "WHERE OKCUCL = '109' ";
		   	 }

		   	 if (fileName.equals("o100005"))//BDGT3 Fresh Slice
		   	 {
		   	 	where = "WHERE MMITTY = '130' ";
		   	 }
		   	 
		   	 if (fileName.equals("o100006"))//BDGT4 Food Service
		   	 {
		   	 	where = "WHERE OKCUCL = '103' ";
		   	 	where = where + "AND UCITNO <> '1005001001' ";
		   	 	where = where + "AND UCITNO <> '1000001055' ";
		   	 	where = where + "AND ( (mmitcl >= '300' and mmitcl <= '800') or ";
		   	 	where = where + "(mmitcl >= '100' and mmitcl <= '170') or ";
		   	 	where = where + "(mmitcl = '210')) ";
		   	 }
		   	 
		   	 if (fileName.equals("o100012"))//CLUBS  Clubs
		   	 {
		   	 	where = "WHERE OKCUCL = '102' ";
		   	 }
		   	 
		   	 if (fileName.equals("o100013"))//DOLLAR  Dollar Store
		   	 {
		   	 	where = "WHERE OKCUCL = '119' ";
		   	 }
		   	 
		   	 if (fileName.equals("o100014"))//DRUG  Drug Discount
		   	 {
		   	 	where = "WHERE OKCUCL = '107' ";
		   	 }
		   	 
		   	 if (fileName.equals("o100015"))//EXPORT Export
		   	 {
		   	 	where = "WHERE OKCUCL = '110' ";
		   	 }
		   	 
		   	 if (fileName.equals("o100017"))//MASS M Mass Merchadnising
		   	 {
		   	 	where = "WHERE OKCUCL = '106' ";
		   	 }
		   	 
		   	 if (fileName.equals("o100018"))//MLTRY Military
		   	 {
		   	 	where = "WHERE OKCUCL = '104' ";
		   	 }
		   	 
		   	 if (fileName.equals("o100019"))//RETAIL Retail
		   	 {
		   	 	where = "WHERE OKCUCL = '101' ";
		   	 }
		   	 
		   	 if (fileName.equals("o100021"))//NW Naturals
		   	 {
		   	 	where = "WHERE OKCUCL = '105' ";
		   	 	where = where + "AND UCCUNO = '32074' ";
		   	 	where = where + "AND UCORTP >= '010' ";
		   	 	where = where + "AND UCORTP <= '400' ";
		   	 	where = where + "AND UCITNO <> ' ' ";
		   	 }
		   	
		   	 if (fileName.equals("o100025"))//CUSTOM Custom Pack
		   	 {
		   		where = "WHERE OKFACI = '150' ";
		   		where = where + "AND SLAMT <> 0 ";
		   		where = where + "AND UCORTP <> '401' ";
		   		where = where + "AND MMITCL <> '800' ";
		   		where = where + "AND UCCUNO <> '32074' ";
		   	 }
		   	 
		   	 if (fileName.equals("o100027"))//  Drug Discount
		   	 {
		   	 	where = "WHERE OKCUCL = '111' ";
		   	 }
		   	 
		   	 if (!where.equals(""))
		   	 {
		   	 	sqlString.append("SELECT * ");
		   	 	sqlString.append("FROM " + dblib + ".Z100003 "  );
		   	 	sqlString.append(where);
		   	 	sqlString.append("AND CPYEA4 >= " + sYear + " ");
		   	 	sqlString.append("AND CPPERI >= " + sPeriod + " ");
		   	 	sqlString.append("AND CPYEA4 <= " + eYear + " ");
		   	 	sqlString.append("AND CPPERI <= " + ePeriod + " ");
		   	 	sqlString.append("AND SLAMT <> 0 ");
		   	 }
		   }
		   	 
		   	 
		   
		   if (inRequestType.equals("getDataSet32Record"))
		   {
		   	 String fileName = (String) requestClass.elementAt(0);
		   	 ResultSet rs    = (ResultSet) requestClass.elementAt(1);
		   	 	
		     sqlString.append("SELECT * ");
		     sqlString.append("FROM " + library + "." + fileName + " ");
		     sqlString.append("WHERE OSCONO = 100 ");
		     sqlString.append("AND OSSSTT = 32 ");
		     sqlString.append("AND OSYEA4 = " + rs.getString("CPYEA4") + " ");
		     sqlString.append("AND OSPERI = " + rs.getString("CPPERI") + " ");
		     sqlString.append("AND UCITNO = '" + rs.getString("UCITNO") + "' ");
		     sqlString.append("AND UCCUNO = '" + rs.getString("UCCUNO") + "' ");
		   }
		   
		   
		   
		   if (inRequestType.equals("getLBIRecord"))
		   {
		   	 String cuNbr  = (String) requestClass.elementAt(0);
		   	 String itNbr  = (String) requestClass.elementAt(1);
		   	 String year   = (String) requestClass.elementAt(2);
		   	 String period = (String) requestClass.elementAt(3);
		   	 
		   	 	
		     sqlString.append("SELECT * ");
		     sqlString.append("FROM " + library + ".o100022 ");
		     sqlString.append("WHERE OSCONO = 100 ");
		     sqlString.append("AND OSSSTT = 32 ");
		     sqlString.append("AND OSYEA4 = " + year + " ");
		     sqlString.append("AND OSPERI = " + period + " ");
		     sqlString.append("AND UCITNO = '" + itNbr + "' ");
		     sqlString.append("AND UCCUNO = '" + cuNbr + "' ");
		   }
		   	 


		   if (inRequestType.equals("getLBI26Record"))
		   {
		   	 String cuNbr  = (String) requestClass.elementAt(0);
		   	 String itNbr  = (String) requestClass.elementAt(1);
		   	 String year   = (String) requestClass.elementAt(2);
		   	 String period = (String) requestClass.elementAt(3);
		   	 
		   	 	
		     sqlString.append("SELECT * ");
		     sqlString.append("FROM " + library + ".o100026 ");
		     sqlString.append("WHERE OSCONO = 100 ");
		     sqlString.append("AND OSSSTT = 32 ");
		     sqlString.append("AND OSYEA4 = " + year + " ");
		     sqlString.append("AND OSPERI = " + period + " ");
		     sqlString.append("AND UCITNO = '" + itNbr + "' ");
		     sqlString.append("AND UCCUNO = '" + cuNbr + "' ");
		     //sqlString.append("AND UCWHLO = '" + whlo + "' ");
		   }
		   
		   
		   
		   if (inRequestType.equals("getSalesForSummary"))
		   {
			   String fiscalYear = (String) requestClass.elementAt(0);
			   String fiscalPeriod = (String) requestClass.elementAt(1);
			   String nextFiscalYear = (String) requestClass.elementAt(2);
			   
			   sqlString.append("SELECT UCCUNO, UCITNO, UCIVQA, SLAMT, ");
			   sqlString.append("CPYEA4, CPPERI, OKCUCL, MMITCL, UCIVQT ");
			   sqlString.append("FROM " + dblib + ".Z100003 ");
			   sqlString.append("WHERE ((CPYEA4 = " + fiscalYear + " ");
			   sqlString.append("AND CPPERI >= " + fiscalPeriod + ") ");
			   sqlString.append("OR CPYEA4 = " + nextFiscalYear + ") ");
			   sqlString.append("AND (SLAMT <> 0 AND UCIVQA <> 0) ");
			   sqlString.append("ORDER BY UCCUNO, UCITNO, CPYEA4, CPPERI ");
		   }
		   
		   
		   
		   if (inRequestType.equals("getSalesForSummary26"))
		   {
			   String fiscalYear = (String) requestClass.elementAt(0);
			   String fiscalPeriod = (String) requestClass.elementAt(1);
			   String nextFiscalYear = (String) requestClass.elementAt(2);
			   
			   sqlString.append("SELECT ");
			   sqlString.append("z.UCCUNO as zUCCUNO, z.UCITNO as zUCITNO, z.UCIVQA as zUCIVQA, ");
			   sqlString.append("z.SLAMT as zSLAMT, z.CPYEA4 as zCPYEA4, z.CPPERI as zCPPERI, ");
			   sqlString.append("z.UCIVQT as zUCIVQT ");
			   sqlString.append("FROM " + dblib + ".Z100003 z ");
			   sqlString.append("inner join " + library + ".mitmas m on m.mmcono = 100 ");
			   sqlString.append("and z.ucitno = m.mmitno and m.mmitty <= '150' ");
			   sqlString.append("and m.MMITCL <> '210' and m.MMITCL <> '400' ");
			   sqlString.append("inner join " + library + ".MPDHED on PHCONO = 100 ");
			   sqlString.append("and (PHFACI = '316' or PHFACI = '317') and z.ucitno = phprno ");
			   sqlString.append("WHERE ((z.CPYEA4 = " + fiscalYear + " ");
			   sqlString.append("AND z.CPPERI >= " + fiscalPeriod + ") ");
			   sqlString.append("OR z.CPYEA4 = " + nextFiscalYear + ") ");
			   sqlString.append("AND (z.SLAMT <> 0 AND z.UCIVQA <> 0) ");
			   sqlString.append("ORDER BY z.UCCUNO, z.UCITNO, z.CPYEA4, z.CPPERI ");
		   }

		   
		   
		   
		   if (inRequestType.equals("getOsbstdAltUomErrors"))
		   {
			   sqlString.append("SELECT UCCUNO, UCDIVI, UCORNO, UCDLIX, ");
			   sqlString.append("UCWHLO, UCPONR, UCPOSX, UCORIG, UCIVQA, UCIVQS ");
			   sqlString.append("FROM " + library + ".OSBSTD ");
			   sqlString.append("WHERE UCRSCD >= 'R01' ");
			   sqlString.append("AND UCRSCD <= 'RET' AND UCYEA4 > 2008 ");
			   sqlString.append("AND UCCUCL = '109' AND UCIVQA <> UCIVQS ");
		   }
		   
		   
		   
		   
		//******************************************************************
		//*** INSERT SECTION
		//******************************************************************
		  
		   
		   
		   if (inRequestType.equals("addZ100003Record"))
		   {
		      Vector dtlFile = (Vector) requestClass.elementAt(0);
		      sqlString.append("INSERT INTO " + dblib + ".Z100003 ");
		      sqlString.append("VALUES (");
		      sqlString.append("'" + dtlFile.elementAt(1).toString() + "', ");
		      sqlString.append(dtlFile.elementAt(2).toString().trim() + ", ");
		      sqlString.append(dtlFile.elementAt(3).toString().trim() + ", ");
		      sqlString.append(dtlFile.elementAt(4).toString().trim() + ", ");
		      sqlString.append(dtlFile.elementAt(5).toString().trim() + ", ");
		      sqlString.append(dtlFile.elementAt(6).toString().trim() + ", ");
		      sqlString.append(dtlFile.elementAt(7).toString().trim() + ", ");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(8).toString().trim()) + "', ");
		      sqlString.append(dtlFile.elementAt(9).toString().trim() + ", ");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(10).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(11).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(12).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(13).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(14).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(15).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(16).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(17).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(18).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(19).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(20).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(21).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(22).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(23).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(24).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(25).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(26).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(27).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(28).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(29).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(30).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(31).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(32).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(33).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(34).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(35).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(36).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(37).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(38).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(39).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(40).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(41).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(42).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(43).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(44).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(45).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(46).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(47).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(48).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(49).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(50).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(51).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(52).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(53).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(54).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(55).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(56).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(57).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(58).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(59).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(60).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(61).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(62).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(63).toString().trim()) + "',");
		      sqlString.append(dtlFile.elementAt(64).toString() + ",");
		      sqlString.append(dtlFile.elementAt(65).toString() + ",");
		      sqlString.append(dtlFile.elementAt(66).toString() + ",");
		      sqlString.append(dtlFile.elementAt(67).toString() + ",");
		      sqlString.append(dtlFile.elementAt(68).toString() + ",");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(69).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(70).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(71).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(72).toString().trim()) + "',");
		      sqlString.append(dtlFile.elementAt(73).toString() + ",");
		      sqlString.append(dtlFile.elementAt(74).toString() + ",");
		      sqlString.append(dtlFile.elementAt(75).toString() + ",");
		      sqlString.append(dtlFile.elementAt(76).toString() + ",");
		      sqlString.append(dtlFile.elementAt(77).toString() + ",");
		      sqlString.append(dtlFile.elementAt(78).toString() + ",");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(79).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(80).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(81).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(82).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(83).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(84).toString().trim()) + "',");
		      sqlString.append(dtlFile.elementAt(85).toString() + ",");
		      sqlString.append(dtlFile.elementAt(86).toString() + ",");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(87).toString().trim()) + "',");
		      sqlString.append(dtlFile.elementAt(88).toString() + ",");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(89).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(90).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(91).toString().trim()) + "',");
		      sqlString.append(dtlFile.elementAt(92).toString() + ",");
		      sqlString.append(dtlFile.elementAt(93).toString() + ",");
		      sqlString.append(dtlFile.elementAt(94).toString() + ",");
		      sqlString.append(dtlFile.elementAt(95).toString() + ",");
		      sqlString.append(dtlFile.elementAt(96).toString() + ",");
		      sqlString.append(dtlFile.elementAt(97).toString() + ",");
		      sqlString.append(dtlFile.elementAt(98).toString() + ",");
		      sqlString.append(dtlFile.elementAt(99).toString() + ",");
		      sqlString.append(dtlFile.elementAt(100).toString() + ",");
		      sqlString.append(dtlFile.elementAt(101).toString() + ",");
		      sqlString.append(dtlFile.elementAt(102).toString() + ",");
		      sqlString.append(dtlFile.elementAt(103).toString() + ",");
		      sqlString.append(dtlFile.elementAt(104).toString() + ",");
		      sqlString.append(dtlFile.elementAt(105).toString() + ",");
		      sqlString.append(dtlFile.elementAt(106).toString() + ",");
		      sqlString.append(dtlFile.elementAt(107).toString() + ",");
		      sqlString.append(dtlFile.elementAt(108).toString() + ",");
		      sqlString.append(dtlFile.elementAt(109).toString() + ",");
		      sqlString.append(dtlFile.elementAt(110).toString() + ",");
		      sqlString.append(dtlFile.elementAt(111).toString() + ",");
		      sqlString.append(dtlFile.elementAt(112).toString() + ",");
		      sqlString.append(dtlFile.elementAt(113).toString() + ",");
		      sqlString.append(dtlFile.elementAt(114).toString() + ",");
		      sqlString.append(dtlFile.elementAt(115).toString() + ",");
		      sqlString.append(dtlFile.elementAt(116).toString() + ",");
		      sqlString.append(dtlFile.elementAt(117).toString() + ",");
		      sqlString.append(dtlFile.elementAt(118).toString() + ",");
		      sqlString.append(dtlFile.elementAt(119).toString() + ",");
		      sqlString.append(dtlFile.elementAt(120).toString() + ",");
		      sqlString.append(dtlFile.elementAt(121).toString() + ",");
		      sqlString.append(dtlFile.elementAt(122).toString() + ",");
		      sqlString.append(dtlFile.elementAt(123).toString() + ",");
		      sqlString.append(dtlFile.elementAt(124).toString() + ",");
		      sqlString.append(dtlFile.elementAt(125).toString() + ",");
		      sqlString.append(dtlFile.elementAt(126).toString() + ",");
		      sqlString.append(dtlFile.elementAt(127).toString() + ",");
		      sqlString.append(dtlFile.elementAt(128).toString() + ",");
		      sqlString.append(dtlFile.elementAt(129).toString() + ",");
		      sqlString.append(dtlFile.elementAt(130).toString() + ",");
		      sqlString.append(dtlFile.elementAt(131).toString() + ",");
		      sqlString.append(dtlFile.elementAt(132).toString() + ",");
		      sqlString.append(dtlFile.elementAt(133).toString() + ",");
		      sqlString.append(dtlFile.elementAt(134).toString() + ",");
		      sqlString.append(dtlFile.elementAt(135).toString() + ",");
		      sqlString.append(dtlFile.elementAt(136).toString() + ",");
		      sqlString.append(dtlFile.elementAt(137).toString() + ",");
		      sqlString.append(dtlFile.elementAt(138).toString() + ",");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(139).toString().trim()) + "',");
		      sqlString.append("'" + FindAndReplace.replaceApostrophe(dtlFile.elementAt(140).toString().trim()) + "')"); //20091209 add field OKCUST
		   } 
		   
		   if (inRequestType.equals("addSalesDetailOinacc"))
		   {
		   	  String invoiceNo     = (String) requestClass.elementAt(0);
		   	  String lineNo        = (String) requestClass.elementAt(1);
		   	  String orderNo       = (String) requestClass.elementAt(2);
		   	  String invoiceDate   = (String) requestClass.elementAt(3);
		   	  String actualDate    = (String) requestClass.elementAt(4);
		   	  String delvNo        = (String) requestClass.elementAt(5);
		      sqlString.append("INSERT INTO " + dblib + ".Z100003 ");
		      sqlString.append("VALUES (");
		      sqlString.append("'" + orderNo + "', " + lineNo + ", ");
			  sqlString.append(invoiceNo + ", 0, ");
		      sqlString.append(invoiceDate + ", " + actualDate + ", ");
		      sqlString.append(delvNo + ", ");
		      sqlString.append("'' , 0,'' ,'' ,'' ,'' ,'' ,'' , ");
		      sqlString.append("'', '', '', '', '', '', '', '', '', ");
		      sqlString.append("'', '', '', '', '', '', '', '', '', ");
		      sqlString.append("'', '', '', '', '', '', '', '', '', ");
		      sqlString.append("'', '', '', '', '', '', '', '', '', ");
		      sqlString.append("'', '', '', '', '', '', '', '', '', ");
		      sqlString.append("'', '', '', 0, 0, 0, 0, 0, '', '', ");
		      sqlString.append("'', '', 0, 0, 0, 0, 0, 0, '', '', '', ");
		      sqlString.append("'', '', '', ");
		      sqlString.append(" 0, ");
		      sqlString.append(" 0, ");
		      sqlString.append("'', 0, '', '', '', 0, 0, 0, 0, 0, 0, 0, ");
		      sqlString.append("0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ");
		      sqlString.append("0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ");
		      sqlString.append("0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ");
		      sqlString.append(invoiceNo + ", '', '' )"); //20091209 add field OKCUST
		   }
		   
		   
		   if (inRequestType.equals("addDataSetBudgetRecord"))
		   {
		   	  String fileName      = (String) requestClass.elementAt(0);
		   	  ResultSet rs         = (ResultSet) requestClass.elementAt(1);
		   	  BigDecimal dia1      = (BigDecimal) requestClass.elementAt(2);
		   	  BigDecimal madj      = (BigDecimal) requestClass.elementAt(3);
		   	  BigDecimal mfor      = (BigDecimal) requestClass.elementAt(4);
		   	  BigDecimal ucos      = (BigDecimal) requestClass.elementAt(5);
			  String     cucl      = (String) requestClass.elementAt(6);
			  String     smcd      = (String) requestClass.elementAt(7);
			  String     cfc3      = (String) requestClass.elementAt(8);
			  String     itcl      = (String) requestClass.elementAt(9);
			  String     cfi1      = (String) requestClass.elementAt(10);
			  String     toType    = (String) requestClass.elementAt(11);
			  String     toVersion = (String) requestClass.elementAt(12);
		   	  
		      sqlString.append("INSERT INTO " + library + "." + fileName + " ");
			  //sqlString.append("INSERT INTO " + "THAILE" + "." + fileName + " ");
		      sqlString.append("VALUES (");
		      sqlString.append(rs.getString("aOSCONO") + ", ");
			  sqlString.append("'" + rs.getString("aOSDIVI")+ "', ");
			  sqlString.append(toType + ", ");
			  sqlString.append("'" + toVersion + "', ");
			  sqlString.append(rs.getString("aOSLEVL") + ", ");
			  sqlString.append(rs.getString("aOSYEA4") + ", ");
			  sqlString.append(rs.getString("aOSPERI") + ", ");
			   	 
			  if (fileName.equals("o100012") ||
				  fileName.equals("o100013") ||
				  fileName.equals("o100014") ||
				  fileName.equals("o100015") ||
				  fileName.equals("o100017") ||
				  fileName.equals("o100018") ||
				  fileName.equals("o100019") ||
				  fileName.equals("o100021") ||
				  fileName.equals("o100025") )
			  {
			  	  sqlString.append("'" + cfi1 + "', ");
			  	  sqlString.append("'" + rs.getString("aUCITNO")+ "', ");
			  	  sqlString.append("'" + cfc3 + "', ");
			  	  sqlString.append("'" + rs.getString("aUCCUNO")+ "', ");
			  	  sqlString.append("'" + smcd + "', ");
			  	  sqlString.append("'" + itcl + "', ");
			  } else
			  {	 
			  	  sqlString.append("'" + cucl + "', ");
			  	  sqlString.append("'" + rs.getString("aUCITNO")+ "', ");
			  	  sqlString.append("'" + itcl + "', ");
			  	  sqlString.append("'" + smcd + "', ");
			  	  sqlString.append("'" + rs.getString("aUCCUNO")+ "', ");
			  	  sqlString.append("'" + cfc3 + "', ");//okcfc3
		      }
			  
		  	  sqlString.append(rs.getString("aUCDEMA") + ",");//ucdema
		  	  sqlString.append(rs.getString("aUCIVQT") + ",");//ucivqt
		  	  sqlString.append(rs.getString("aUCSAAM") + ",");//ucsaam
		  	  sqlString.append(rs.getString("aUCDIA1") + ",");//ucdia1
		  	  sqlString.append(rs.getString("aUCDIA2") + ",");//ucdia2
		  	  sqlString.append(rs.getString("aUCDIA3") + ",");//ucdia3
		  	  sqlString.append(rs.getString("aUCDIA4") + ",");//ucdia4
		  	  sqlString.append(rs.getString("aUCALBO") + ",");//ucalbo
		  	  sqlString.append(ucos.toString() + ", ");//ucucos
			  sqlString.append(rs.getString("aMFCFOR") + ",");//mfcfor
			  sqlString.append(mfor.toString() + ", ");//mfmfor
			  sqlString.append(rs.getString("aMFNCFO") + ",");//mfncfo
			  sqlString.append(madj.toString() + ", ");//mfmadj
			  sqlString.append(rs.getString("aMFALFF") + ",");//mfalff
			  sqlString.append(rs.getString("aMFMADV") + ",");//mfmadv
			  sqlString.append(rs.getString("aMFAVER") + ",");//mfaver
			  sqlString.append(rs.getString("aMFALA1") + ",");//mfala1
			  sqlString.append(rs.getString("aMFALA2") + ",");//mfala2
			  sqlString.append(rs.getString("aMFALA3") + ",");//mfala3
			  sqlString.append(rs.getString("aMFALAO") + ",");//mfalao
			  sqlString.append(rs.getString("aMFTREF") + ",");//mftref
			  sqlString.append(rs.getString("aMFTREQ") + ",");//mftreq
			  sqlString.append(rs.getString("aMFSEAF") + ", ");//mfseaf
		      sqlString.append(rs.getString("aMFPSTA") + " )");//mfpsta
		   }
		   
		   
		   if (inRequestType.equals("addDataSet32FromZfile"))
		   {
		   	  String fileName      = (String) requestClass.elementAt(0);
		   	  ResultSet rs         = (ResultSet) requestClass.elementAt(1);
		   	  BigDecimal madj      = (BigDecimal) requestClass.elementAt(2);
		   	  BigDecimal ivqt	   = (BigDecimal) requestClass.elementAt(3);
		   	  String cfi1          = (String) requestClass.elementAt(4);
		   	  String itcl          = (String) requestClass.elementAt(5);
		   	  String cfc3          = (String) requestClass.elementAt(6);
		   	  String smcd          = (String) requestClass.elementAt(7);
		   	  String cucl		   = (String) requestClass.elementAt(8);
		   	  
		      sqlString.append("INSERT INTO " + library + "." + fileName + " ");
		      sqlString.append("VALUES (");
		      sqlString.append("100, ");
			  sqlString.append("'', " );
			  sqlString.append("32, ");
			  sqlString.append("'', ");
			  sqlString.append("0, ");
			  sqlString.append(rs.getString("CPYEA4") + ", ");
			  sqlString.append(rs.getString("CPPERI") + ", ");
			   	 
			  if (fileName.equals("o100012") ||
				  fileName.equals("o100013") ||
				  fileName.equals("o100014") ||
				  fileName.equals("o100015") ||
				  fileName.equals("o100017") ||
				  fileName.equals("o100018") ||
				  fileName.equals("o100019") ||
				  fileName.equals("o100020") ||
				  fileName.equals("o100021") ||
				  fileName.equals("o100027") ||
				  fileName.equals("o100025") )
			  {
			  	  sqlString.append("'" + cfi1 + "', ");
			  	  sqlString.append("'" + rs.getString("UCITNO") + "', ");
			  	  sqlString.append("'" + cfc3 + "', ");
			  	  sqlString.append("'" + rs.getString("UCCUNO") + "', ");
			  	  sqlString.append("'" + smcd + "', ");
			  	  sqlString.append("'" + itcl + "', ");
			  } else
			  {
			  	  sqlString.append("'" + cucl + "', ");
			  	  sqlString.append("'" + rs.getString("UCITNO") + "', ");
			  	  sqlString.append("'" + itcl + "', ");
			  	  sqlString.append("'" + smcd + "', ");
			  	  sqlString.append("'" + rs.getString("UCCUNO") + "', ");
			  	  sqlString.append("'" + cfc3 + "', ");
			  }
			  
		  	  sqlString.append("0, ");//UCDEMA
		  	  sqlString.append(ivqt.toString() + ", ");//UCIVQT
		  	  sqlString.append(rs.getString("SLAMT") + ", ");//UCSAAM
		  	  sqlString.append("0, ");//UCDIA1
			  sqlString.append("0, ");//UCDIA2
			  sqlString.append("0, ");//UCDIA3
			  sqlString.append("0,");//UCDIA4
			  sqlString.append("0, ");//UCALBO
			  sqlString.append("0, ");//UCUCOS
			  sqlString.append("0, ");//MFCFOR
			  sqlString.append("0, ");//MFMFOR
			  sqlString.append("0, ");//MFNCFO
			  sqlString.append(madj.toString() + ", ");//MFMADJ
			  sqlString.append("0,");//MFALFF
			  sqlString.append("0,");//MFMADV
			  sqlString.append("0,");//MFAVER
			  sqlString.append("0,");//MFALA1
			  sqlString.append("0,");//MFALA2
			  sqlString.append("0,");//MFALA3
			  sqlString.append("0,");//MFALAo
			  sqlString.append("0,");//MFTREF
			  sqlString.append("0,");//MFTREQ
			  sqlString.append("0,");//MFSEAF
			  sqlString.append("0 ");//MFPSTA
		      sqlString.append(" )");
		   }
		   
		   
		   
		   if (inRequestType.equals("addSummaryFile"))
		   {
			   String cuNbr   = (String) requestClass.elementAt(0);
			   String itNbr   = (String) requestClass.elementAt(1);
			   String year    = (String) requestClass.elementAt(2);
			   String period  = (String) requestClass.elementAt(3);
			   BigDecimal qty = (BigDecimal) requestClass.elementAt(4);
			   BigDecimal dol = (BigDecimal) requestClass.elementAt(5);
			   
			   sqlString.append("INSERT INTO " + dblib + ".Z100005 ");
			   sqlString.append("(UCCUNO,UCITNO,CPYEA4,CPPERI,UCIVQA,SLAMT) ");
			   sqlString.append("VALUES(");
			   sqlString.append("'" + cuNbr.trim() + "', ");
			   sqlString.append("'" + itNbr.trim() + "', ");
			   sqlString.append(year.trim() + ", ");
			   sqlString.append(period.trim() + ", ");
			   sqlString.append(qty.toString() + ", ");
			   sqlString.append(dol.toString() + " ");
			   sqlString.append(")");
		   }
		   
		   
		   
		   
		   if (inRequestType.equals("addSummaryDataSet"))
		   {
			   String cuNbr   = (String) requestClass.elementAt(0);
			   String itNbr   = (String) requestClass.elementAt(1);
			   String year    = (String) requestClass.elementAt(2);
			   String period  = (String) requestClass.elementAt(3);
			   BigDecimal qty = (BigDecimal) requestClass.elementAt(4);
			   BigDecimal dol = (BigDecimal) requestClass.elementAt(5);
			   
				sqlString.append("INSERT INTO " + library + ".O100022 ");
				sqlString.append("(OSCONO,OSDIVI,OSSSTT,OSBVER,OSLEVL,OSYEA4,");
				sqlString.append(" OSPERI,UCDIVI,UCWHLO,UCCUNO,UCITNO,");
				sqlString.append(" UCIVQT,UCOFQS,UCGRWE,UCNEWE,UCVOL3,UCSGAM,");
				sqlString.append(" UCSAAM,UCOFRA,UCDIA1,UCDIA2,UCDIA3,UCDIA4,");
				sqlString.append(" UCDIA5,UCDIA6,UCALBO,UCUCOS,UCDCOS,UCONK1,");
				sqlString.append(" UCONK2,UCONK3,UCONK4,UCONK5) ");
				sqlString.append("VALUES(");
				sqlString.append("100, ");//OSCONO
				sqlString.append("'', ");//OSDIVI
				sqlString.append("32, ");//OSSSTT
				sqlString.append("'', ");//OSBVER
				sqlString.append("0, ");//OSLEVL
				sqlString.append(year + ", ");//OSYEA4
				sqlString.append(period + ", ");//OSPERI
				sqlString.append("'', ");//UCDIVI
				sqlString.append("'', ");//UCWHLO  3/2/11 changed from constant 209 wth.
				sqlString.append("'" + cuNbr.trim() + "', ");//UCCUNO
				sqlString.append("'" + itNbr.trim() + "', ");//UCITNO
				sqlString.append(qty.toString().trim() + ", ");//UCIVQT
				sqlString.append(qty.toString().trim() + ", ");//UCOFQS
				sqlString.append("0, ");//UCGRWE
				sqlString.append("0, ");//UCNEWE
				sqlString.append("0, ");//UCVOL3
				sqlString.append("0, ");//UCSGAM
				sqlString.append(dol.toString().trim() + ", ");//UCSAAM
				sqlString.append("0, ");//UCOFRA
				sqlString.append("0, ");//UCDIA1
				sqlString.append("0, ");//UCDIA2
				sqlString.append("0, ");//UCDIA3
				sqlString.append("0, ");//UCDIA4
				sqlString.append("0, ");//UCDIA5
				sqlString.append("0, ");//UCDIA6
				sqlString.append("0, ");//UCALBO
				sqlString.append("0, ");//UCUCOS
				sqlString.append("0, ");//UCDCOS
				sqlString.append("0, ");//UCONK1
				sqlString.append("0, ");//UCONK2
				sqlString.append("0, ");//UCONK3
				sqlString.append("0, ");//UCONK4
				sqlString.append("0 ");//UCONK5
				sqlString.append(") ");
			}
		   
		   
		   
		   
		   if (inRequestType.equals("addSummaryDataSet26"))
		   {
			   String cuNbr   = (String) requestClass.elementAt(0);
			   String itNbr   = (String) requestClass.elementAt(1);
			   String year    = (String) requestClass.elementAt(2);
			   String period  = (String) requestClass.elementAt(3);
			   BigDecimal qty = (BigDecimal) requestClass.elementAt(4);
			   
				sqlString.append("INSERT INTO " + library + ".O100026 ");
				sqlString.append("(OSCONO,OSDIVI,OSSSTT,OSBVER,OSLEVL,OSYEA4,");
				sqlString.append(" OSPERI,UCITNO,UCCUNO, UCIVQT, UCDEMA )");
				sqlString.append("VALUES(");
				sqlString.append("100, ");//OSCONO
				sqlString.append("'', ");//OSDIVI
				sqlString.append("32, ");//OSSSTT
				sqlString.append("'', ");//OSBVER
				sqlString.append("0, ");//OSLEVL
				sqlString.append(year + ", ");//OSYEA4
				sqlString.append(period + ", ");//OSPERI
				//sqlString.append("'', ");//UCWHLO
				sqlString.append("'" + itNbr.trim() + "', ");//UCITNO
				sqlString.append("'" + cuNbr.trim() + "', ");//UCCUNO
				sqlString.append(qty.toString().trim() + ", ");//UCIVQT
				sqlString.append("0 ");//UCDEMA
				sqlString.append(") ");
			}
		   

		   
		//******************************************************************
	    //*** UPDATE SECTION
		//******************************************************************


		   
		   if (inRequestType.equals("updateSalesDetailOinacc"))
		   {
		   	  String invoiceNo     = (String) requestClass.elementAt(0);
		   	  String lineNo        = (String) requestClass.elementAt(1);
		   	  String delvNo        = (String) requestClass.elementAt(2);
		   	  String fieldName1    = (String) requestClass.elementAt(3);
		   	  BigDecimal curr1     = (BigDecimal) requestClass.elementAt(4);
		   	  String fieldName2    = (String) requestClass.elementAt(5);
		   	  BigDecimal curr2     = (BigDecimal) requestClass.elementAt(6);
		   	  String glDate        = (String) requestClass.elementAt(7);
		   	  
			  sqlString.append("UPDATE " + dblib + ".Z100003 ");
			  sqlString.append("SET " + fieldName1 + " = " + curr1.toString() + ",  ");
			  sqlString.append(fieldName2 + " = " + curr2.toString() + ", ");
			  sqlString.append("UTACDT = " + glDate + " ");
			  sqlString.append("WHERE UCIVNO = " + invoiceNo.trim() + " "); 
			  sqlString.append("AND   UCPONR = " + lineNo + " ");
			  sqlString.append("AND   UCDLIX = " + delvNo + " ");
		   }
		   
		   
		   
		   if (inRequestType.equals("updateSalesDetailOepvx"))
		   {
		   	  String invoiceNo     = (String) requestClass.elementAt(0);
		   	  String lineNo        = (String) requestClass.elementAt(1);
		   	  String delvNo        = (String) requestClass.elementAt(2);
		   	  String fieldName1    = (String) requestClass.elementAt(3);
		   	  BigDecimal curr1     = (BigDecimal) requestClass.elementAt(4);
		   	  String fieldName2    = (String) requestClass.elementAt(5);
		   	  BigDecimal curr2     = (BigDecimal) requestClass.elementAt(6);
		   	  
			  sqlString.append("UPDATE " + dblib + ".Z100003 ");
			  sqlString.append("SET " + fieldName1 + " = " + curr1.toString() + ",  ");
			  sqlString.append(fieldName2 + " = " + curr1.toString() + "  ");
			  sqlString.append("WHERE UCIVNO = " + invoiceNo.trim() + " "); 
			  sqlString.append("AND   UCPONR = " + lineNo + " ");
			  sqlString.append("AND   UCDLIX = " + delvNo + " ");
		   }
		   
		   
		   
		   if (inRequestType.equals("updateZ100003FiscalData"))
		   {
		   	String orderNo         = (String) requestClass.elementAt(0);
		   	String invoiceNo       = (String) requestClass.elementAt(1);
		   	String delvNo          = (String) requestClass.elementAt(2);
		   	String fiscalYear      = (String) requestClass.elementAt(3);
		   	String fiscalPeriod    = (String) requestClass.elementAt(4);
		   	String fiscalName      = (String) requestClass.elementAt(5);
		   	String fiscalWeek      = (String) requestClass.elementAt(6);
		   	String lineNo          = (String) requestClass.elementAt(7);
		   	
		   	sqlString.append("UPDATE " + dblib + ".Z100003 ");
		   	sqlString.append("SET CPYEA4 = " + fiscalYear + ", ");
		   	sqlString.append("CPPERI = " + fiscalPeriod + ", ");
		   	sqlString.append("CPTX15 = '" + fiscalName + "', ");
		   	sqlString.append("FWEEK = " + fiscalWeek + " ");
		   	sqlString.append("WHERE UCORNO = '" + orderNo + "' ");
		   	sqlString.append("AND   UCIVNO = " + invoiceNo + " ");
		   	sqlString.append("AND   UCPONR = " + lineNo + " ");
		   	sqlString.append("AND   UCDLIX = " + delvNo + " ");
		   }
		   
		   
		   if (inRequestType.equals("updateZeroLineDetail"))
		   {
		   	  ResultSet rsZ100003NotZero = (ResultSet) requestClass.elementAt(0);
		   	  
			  sqlString.append("UPDATE " + dblib + ".Z100003 ");
			  sqlString.append("SET UCORNO = '" + rsZ100003NotZero.getString("UCORNO").trim() + "', ");
			  sqlString.append("UCCUNO = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("UCCUNO").trim()) + "', ");
			  sqlString.append("OKCUNM = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKCUNM").trim()) + "', ");
			  sqlString.append("UCADID = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("UCADID").trim()) + "', ");
			  sqlString.append("OPCUNM = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OPCUNM").trim()) + "', ");
			  sqlString.append("UCORTP = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("UCORTP").trim()) + "', ");
			  sqlString.append("UCWHLO = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("UCWHLO").trim()) + "', ");
			  sqlString.append("MWWHLO = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("MWWHLO").trim()) + "', ");
			  sqlString.append("OKFACI = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKFACI").trim()) + "', ");
			  sqlString.append("CFFACN = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("CFFACN").trim()) + "', ");
			  sqlString.append("OKCUCL = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKCUCL").trim()) + "', ");
			  sqlString.append("OKCUCL40 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKCUCL40").trim()) + "', ");
			  sqlString.append("OKCUCL15 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKCUCL15").trim()) + "', ");
			  sqlString.append("OKSMCD = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKSMCD").trim()) + "', ");
			  sqlString.append("OKSMCD40 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKSMCD40").trim()) + "', ");
			  sqlString.append("OKSMCD15 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKSMCD15").trim()) + "', ");
			  sqlString.append("OKSDST = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKSDST").trim()) + "', ");
			  sqlString.append("OKSDST40 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKSDST40").trim()) + "', ");
			  sqlString.append("OKSDST15 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKSDST15").trim()) + "', ");
			  sqlString.append("OKCFC3 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKCFC3").trim()) + "', ");
			  sqlString.append("OKCFC340 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKCFC340").trim()) + "', ");
			  sqlString.append("OKCFC315 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKCFC315").trim()) + "', ");
			  sqlString.append("OKFRE1 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKFRE1").trim()) + "', ");
			  sqlString.append("OKFRE140 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKFRE140").trim()) + "', ");
			  sqlString.append("OKFRE115 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKFRE115").trim()) + "', ");
			  sqlString.append("OKFRE2 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKFRE2").trim()) + "', ");
			  sqlString.append("OKFRE240 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKFRE215").trim()) + "', ");
			  sqlString.append("OKCFC5 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKCFC5").trim()) + "', ");
			  sqlString.append("OKCFC0 = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKCFC0").trim()) + "', ");
			  sqlString.append("OKAGNT = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKAGNT").trim()) + "', ");
			  sqlString.append("OKAGNTNM = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKAGNTNM").trim()) + "', ");
			  sqlString.append("OKPYNO = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKPYNO").trim()) + "', ");
			  sqlString.append("OKPYNONM = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("OKPYNONM").trim()) + "', ");
			  sqlString.append("UCINRC = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("UCINRC").trim()) + "', ");
			  sqlString.append("UCINRCNM = '" + FindAndReplace.replaceApostrophe(rsZ100003NotZero.getString("UCINRCNM").trim()) + "' ");
			  sqlString.append("WHERE UCIVNO = '" + rsZ100003NotZero.getString("UCIVNO").trim() + "' ");
			  sqlString.append("AND UCPONR = 0 ");
		   }
		   
		   
		   
		   if (inRequestType.equals("updateZ100003OrderDetail"))
		   {
		   	  String invoiceNo = (String) requestClass.elementAt(0);
		   	  Vector dtlFile   = (Vector) requestClass.elementAt(1);
		   	  
			  sqlString.append("UPDATE " + dblib + ".Z100003 ");
			  sqlString.append("SET UCCUNO = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(1))+ "', ");
			  sqlString.append("OKCUNM = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(2)) + "', ");
			  sqlString.append("UCADID = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(3)) + "', ");
			  sqlString.append("OPCUNM = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(4)) + "', ");
			  sqlString.append("UCORTP = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(5)) + "', ");
			  sqlString.append("UCWHLO = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(6)) + "', ");
			  sqlString.append("MWWHLO = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(7)) + "', ");
			  sqlString.append("OKFACI = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(8)) + "', ");
			  sqlString.append("CFFACN = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(9)) + "', ");
			  sqlString.append("OKCUCL = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(10)) + "', ");
			  sqlString.append("OKCUCL40 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(11)) + "', ");
			  sqlString.append("OKCUCL15 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(12)) + "', ");
			  sqlString.append("OKSMCD = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(13)) + "', ");
			  sqlString.append("OKSMCD40 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(14)) + "', ");
			  sqlString.append("OKSMCD15 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(15)) + "', ");
			  sqlString.append("OKSDST = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(16)) + "', ");
			  sqlString.append("OKSDST40 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(17)) + "', ");
			  sqlString.append("OKSDST15 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(18)) + "', ");
			  sqlString.append("OKCFC3 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(19)) + "', ");
			  sqlString.append("OKCFC340 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(20)) + "', ");
			  sqlString.append("OKCFC315 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(21)) + "', ");
			  sqlString.append("OKFRE1 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(22)) + "', ");
			  sqlString.append("OKFRE140 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(23)) + "', ");
			  sqlString.append("OKFRE115 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(24)) + "', ");
			  sqlString.append("OKFRE2 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(25)) + "', ");
			  sqlString.append("OKFRE240 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(26)) + "', ");
			  sqlString.append("OKFRE215 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(27)) + "', ");
			  sqlString.append("OKCFC5 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(28)) + "', ");
			  sqlString.append("OKCFC0 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(29)) + "', ");
			  //sqlString.append("OKAGNT = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(30)) + "', ");
			  //sqlString.append("OKAGNTNM = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(31)) + "', ");
			  sqlString.append("OKPYNO = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(32)) + "', ");
			  sqlString.append("OKPYNONM = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(33)) + "', ");
			  sqlString.append("UCINRC = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(34)) + "', ");
			  sqlString.append("UCINRCNM = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(35)) + "', ");
			  sqlString.append("OPECAR = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(36)) + "', ");
			  sqlString.append("OPECAR40 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(37)) + "', ");
			  sqlString.append("OPECAR15 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(38)) + "', ");
			  sqlString.append("OPCSCD = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(39)) + "', ");
			  sqlString.append("OPCSCD40 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(40)) + "', ");
			  sqlString.append("OPCSCD15 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(41)) + "', ");
			  sqlString.append("CPYEA4 = " + dtlFile.elementAt(42) + ", ");
			  sqlString.append("CPPERI = " + dtlFile.elementAt(43) + ", ");
			  sqlString.append("CPTX15 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(44)) + "', ");
			  sqlString.append("FWEEK = " + dtlFile.elementAt(45) + ", ");
			  sqlString.append("OKCUST = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(46)) + "' ");
			  sqlString.append("WHERE UTIVNO = " + invoiceNo + " ");
		   }

		   
		   
		   if (inRequestType.equals("updateZ100003SequenceDetail"))
		   {
		   	  String invoiceNo  = (String) requestClass.elementAt(0);
		   	  String sequenceNo = (String) requestClass.elementAt(1);
		   	  String delvNo     = (String) requestClass.elementAt(2);
		   	  Vector dtlFile    = (Vector) requestClass.elementAt(3);
		   	  
			  sqlString.append("UPDATE " + dblib + ".Z100003 ");
			  sqlString.append("SET UCIVDT = " + (String) dtlFile.elementAt(1) + ", ");
			  //sqlString.append("UCDLIX = " + (String) dtlFile.elementAt(2) + ", "); //do not update this field here.
			  sqlString.append("UCAGNO = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(3)) + "', ");
			  sqlString.append("UWAGQT = " + (String) dtlFile.elementAt(4) + ", ");
			  sqlString.append("UCITNO = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(5)) + "', ");
			  sqlString.append("MMITDS = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(6)) + "', ");
			  sqlString.append("MMFUDS = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(7)) + "', ");
			  sqlString.append("MMUNMS = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(8)) + "', ");
			  sqlString.append("MMACRF = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(9)) + "', ");
			  sqlString.append("MMACRF40 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(10)) + "', ");
			  sqlString.append("MMACRF15 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(11)) + "', ");
			  sqlString.append("MMITTY = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(12)) + "', ");
			  sqlString.append("MMITTY40 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(13)) + "', ");
			  sqlString.append("MMITTY15 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(14)) + "', ");
			  sqlString.append("MMITCL = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(15)) + "', ");
			  sqlString.append("MMITCL40 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(16)) + "', ");
			  sqlString.append("MMITCL15 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(17)) + "', ");
			  sqlString.append("MMITGR = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(18)) + "', ");
			  sqlString.append("MMITGR40 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(19)) + "', ");
			  sqlString.append("MMITGR15 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(20)) + "', ");
			  sqlString.append("MMCFI1 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(21)) + "', ");
			  sqlString.append("MMCFI140 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(22)) + "', ");
			  sqlString.append("MMCFI115 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(23)) + "', ");
			  sqlString.append("UCIVQT = " + (String) dtlFile.elementAt(24) + ", ");
			  sqlString.append("UCIVQA = " + (String) dtlFile.elementAt(25) + ", ");
			  sqlString.append("UCGRWE = " + (String) dtlFile.elementAt(26) + ", ");
			  sqlString.append("UCSAAM = " + (String) dtlFile.elementAt(27) + ", ");
			  sqlString.append("UCSTUN = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(28)) + "', ");
			  sqlString.append("UCALUN = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(29)) + "', ");
			  sqlString.append("UCSPUN = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(30)) + "', ");
			  sqlString.append("UCCMP1 = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(31)) + "', ");
			  sqlString.append("UCDIA1 = " + (String) dtlFile.elementAt(32) + ", ");
			  sqlString.append("UCDIA2 = " + (String) dtlFile.elementAt(33) + ", ");
			  sqlString.append("UCDIA3 = " + (String) dtlFile.elementAt(34) + ", ");
			  sqlString.append("UCDIA4 = " + (String) dtlFile.elementAt(35) + ", ");
			  sqlString.append("UCDIA5 = " + (String) dtlFile.elementAt(36) + ", ");
			  sqlString.append("UCDIA6 = " + (String) dtlFile.elementAt(37) + ", ");
			  sqlString.append("MMBUAR = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(38)) + "', ");
			  sqlString.append("OKAGNTNM = '" + FindAndReplace.replaceApostrophe( (String) dtlFile.elementAt(39)) + "' ");
			  sqlString.append("WHERE UTIVNO = " + invoiceNo + " ");
			  sqlString.append("AND   UCPONR = " + sequenceNo + " ");
			  sqlString.append("AND   UCDLIX = " + delvNo + " ");
		   }
		   
		   
		   
		   if (inRequestType.equals("updateOsbstdCustomerData"))
		   {
		   	  String cuno     = (String) requestClass.elementAt(0);
		   	  String cucl     = (String) requestClass.elementAt(1);
		   	  String smcd     = (String) requestClass.elementAt(2);
		   	  
			  sqlString.append("UPDATE " + library + ".OSBSTD ");
			  sqlString.append("SET UCCUCL = '" + cucl + "', ");
			  sqlString.append("UCSMCD = '" + FindAndReplace.replaceApostrophe(smcd) + "' ");
			  sqlString.append("WHERE UCCUNO = " + cuno + " ");
		   }
		   
		   
		   if (inRequestType.equals("updateZ100003CustomerData"))
		   {
		   	  String cuno     = (String) requestClass.elementAt(0);
		   	  String cucl     = (String) requestClass.elementAt(1);
		   	  String smcd     = (String) requestClass.elementAt(2);
		   	  String cfc3     = (String) requestClass.elementAt(3);
		   	  
			  sqlString.append("UPDATE " + dblib + ".Z100003 ");
			  sqlString.append("SET OKCUCL = '" + cucl + "', ");
			  sqlString.append("OKSMCD = '" + FindAndReplace.replaceApostrophe(smcd) + "', ");
			  sqlString.append("OKCFC3 = '" + FindAndReplace.replaceApostrophe(cfc3) + "' ");
			  sqlString.append("WHERE UCCUNO = '" + cuno + "' ");
		   }
		   
		   
		   
		   if (inRequestType.equals("updateOsbstdItemData"))
		   {
		   	  String itno     = (String) requestClass.elementAt(0);
		   	  String itcl     = (String) requestClass.elementAt(1);
		   	  
			  sqlString.append("UPDATE " + library + ".OSBSTD ");
			  sqlString.append("SET UCITCL = '" + itcl + "' ");
			  sqlString.append("WHERE UCITNO = '" + itno + "' ");
		   }
		   
		   
		   if (inRequestType.equals("updateZ100003ItemData"))
		   {
		   	  String itno     = (String) requestClass.elementAt(0);
		   	  String itcl     = (String) requestClass.elementAt(1);
		   	  String cfi1     = (String) requestClass.elementAt(2);
		   	  
			  sqlString.append("UPDATE " + dblib + ".Z100003 ");
			  sqlString.append("SET UCITCL = '" + itcl + "', ");
			  sqlString.append("MMCFI1 = '" + cfi1 + "' ");
			  sqlString.append("WHERE UCITNO = '" + itno + "' ");
		   }
		   
		   
		   if (inRequestType.equals("updateZ100003SalesDollars"))
		   {
		   	  ResultSet rs = (ResultSet) requestClass.elementAt(0);
		   	  BigDecimal amt = (BigDecimal) requestClass.elementAt(1);
		   	  
			  sqlString.append("UPDATE " + dblib + ".Z100003 ");
			  sqlString.append("SET SLAMT = " + amt.toString() + " ");
			  sqlString.append("WHERE UCIVNO = " + rs.getString("UCIVNO") + " ");
			  sqlString.append("AND UCPONR = " + rs.getString("UCPONR") + " ");
			  sqlString.append("AND UCDLIX = " + rs.getString("UCDLIX") + " ");
		   }
		   
		   
		   
		   if (inRequestType.equals("updateDataSetBudgetRecord"))
		   {
		   	  String fileName = (String) requestClass.elementAt(0);
		   	  ResultSet rs = (ResultSet) requestClass.elementAt(1);
		   	  BigDecimal dia1 = (BigDecimal) requestClass.elementAt(2);
		   	  BigDecimal madj = (BigDecimal) requestClass.elementAt(3);
		   	  BigDecimal mfor = (BigDecimal) requestClass.elementAt(4);
		   	  BigDecimal ivqt = (BigDecimal) requestClass.elementAt(5);
		   	  BigDecimal saam = (BigDecimal) requestClass.elementAt(6);
		   	  
		   	  sqlString.append("UPDATE " + library + "." + fileName + " ");
		   	  //sqlString.append("UPDATE " + "THAILE" + "." + fileName + " ");
		   	  
			   	 

			  sqlString.append("SET UCDIA1 = " + dia1.toString() + ", ");
		   	  sqlString.append("MFMADJ = " + madj.toString() + ", ");
		   	  sqlString.append("MFMFOR = " + mfor.toString() + ", ");
		   	  sqlString.append("UCIVQT = " + ivqt.toString() + ", ");
		   	  sqlString.append("UCSAAM = " + saam.toString() + " "); 
			  sqlString.append("WHERE OSCONO = " + rs.getString("OSCONO") + " ");
			  sqlString.append("AND OSDIVI = '" + rs.getString("OSDIVI") + "' ");
			  sqlString.append("AND OSSSTT = " + rs.getString("OSSSTT") + " ");
			  sqlString.append("AND OSBVER = '" + rs.getString("OSBVER") + "' ");
			  sqlString.append("AND OSLEVL = " + rs.getString("OSLEVL") + " ");
			  sqlString.append("AND OSYEA4 = " + rs.getString("OSYEA4") + " ");
			  sqlString.append("AND OSPERI = " + rs.getString("OSPERI") + " ");
			  sqlString.append("AND UCITNO = '" + rs.getString("UCITNO") + "' ");
			  sqlString.append("AND UCITCL = '" + rs.getString("UCITCL") + "' ");
			  sqlString.append("AND UCSMCD = '" + rs.getString("UCSMCD") + "' ");
			  sqlString.append("AND UCCUNO = '" + rs.getString("UCCUNO") + "' ");
			  
			  if (fileName.equals("o100012") ||
				  fileName.equals("o100013") ||
				  fileName.equals("o100014") ||
				  fileName.equals("o100015") ||
				  fileName.equals("o100017") ||
				  fileName.equals("o100018") ||
				  fileName.equals("o100019") ||
				  fileName.equals("o100021") ||
				  fileName.equals("o100025") )
			  {
			  	  sqlString.append("AND MMCFI1 = '" + rs.getString("MMCFI1") + "' ");
			  } else
			  {
			  	  sqlString.append("AND UCCUCL = '" + rs.getString("UCCUCL") + "' ");
			  }
			  
			  sqlString.append("AND OKCFC3 = '" + rs.getString("OKCFC3") + "' ");
		   }
		   
		   
		   
		   
		   if (inRequestType.equals("updateOsbstdQuantity"))
		   {
		   	  ResultSet rs = (ResultSet) requestClass.elementAt(0);
		   	  
			  sqlString.append("UPDATE " + library + ".OSBSTD ");
			  sqlString.append("SET UCIVQA = UCIVQS ");
			  sqlString.append("WHERE UCCUNO = " + rs.getString("UCCUNO") + " ");
			  sqlString.append("AND UCDIVI = '" + rs.getString("UCDIVI") + "' ");
			  sqlString.append("AND UCORNO = '" + rs.getString("UCORNO") + "' ");
			  sqlString.append("AND UCDLIX = " + rs.getString("UCDLIX") + " ");
			  sqlString.append("AND UCWHLO = '" + rs.getString("UCWHLO") + "' ");
			  sqlString.append("AND UCPONR = " + rs.getString("UCPONR") + " ");
			  sqlString.append("AND UCPOSX = " + rs.getString("UCPOSX") + " ");
		   }
		   
		   
		   
		   
		   if (inRequestType.equals("updateZfileKgToLb"))
		   {
			  sqlString.append("UPDATE " + dblib + ".Z100003 ");
			  sqlString.append("SET UCALUN = 'LB', ");
			  sqlString.append("UCIVQA = ROUND((UCIVQA * 2.2046),0), ");
			  sqlString.append("OKFRE2 = 'KG', ");
			  sqlString.append("OKFRE240 = 'CHANGED KG to LB (* 2.2046)' ");
			  sqlString.append("WHERE UCALUN = 'KG' ");
		   }
		   
		   
		   
		   
		   if (inRequestType.equals("updateSummaryDataSet"))
		   {
		   	  ResultSet rs    = (ResultSet) requestClass.elementAt(0);
			  BigDecimal qty = (BigDecimal) requestClass.elementAt(1);
			  BigDecimal dol = (BigDecimal) requestClass.elementAt(2);
		   	  
			  sqlString.append("UPDATE " + library + ".o100022 ");
			  sqlString.append("SET UCIVQT = " + qty.toString() + ", ");
			  sqlString.append("UCSAAM = " + dol.toString() + " ");
			  sqlString.append("WHERE OSSSTT = 32 ");
			  sqlString.append("AND OSYEA4 = " + rs.getString("OSYEA4").trim() + " ");
			  sqlString.append("AND OSPERI = " + rs.getString("OSPERI").trim() + " ");
			  sqlString.append("AND UCCUNO = '" + rs.getString("UCCUNO").trim() + "' ");
			  sqlString.append("AND UCITNO = '" + rs.getString("UCITNO").trim() + "' ");
		   }
		   
		   
		   
		   
		   if (inRequestType.equals("updateSummaryDataSet26"))
		   {
		   	  ResultSet rs    = (ResultSet) requestClass.elementAt(0);
			  BigDecimal qty = (BigDecimal) requestClass.elementAt(1);
		   	  
			  sqlString.append("UPDATE " + library + ".o100026 ");
			  sqlString.append("SET UCIVQT = " + qty.toString() + " ");
			  sqlString.append("WHERE OSSSTT = 32 ");
			  sqlString.append("AND OSYEA4 = " + rs.getString("OSYEA4").trim() + " ");
			  sqlString.append("AND OSPERI = " + rs.getString("OSPERI").trim() + " ");
			  sqlString.append("AND UCCUNO = '" + rs.getString("UCCUNO").trim() + "' ");
			  sqlString.append("AND UCITNO = '" + rs.getString("UCITNO").trim() + "' ");
			  //sqlString.append("AND UCWHLO = '" + rs.getString("UCWHLO").trim() + "' ");
		   }
		      
		   
		   
		   if (inRequestType.equals("clearO100022SalesHistory"))
		   {
			  String fiscalYear = (String) requestClass.elementAt(0);
			  String fiscalPeriod = (String) requestClass.elementAt(1);
			  
		   	  sqlString.append("UPDATE " + library + ".o100022 ");
		   	  sqlString.append("SET UCIVQT = 0, UCSAAM = 0 ");
		   	  sqlString.append("WHERE OSSSTT = 32 ");
		   	  sqlString.append("AND ((OSYEA4 = " + fiscalYear + " ");
		   	  sqlString.append("AND OSPERI >= " + fiscalPeriod + ") ");
		   	  sqlString.append("OR OSYEA4 > " + fiscalYear + ") ");
		   }
		   
		   
		   
		   
		   
		   if (inRequestType.equals("clearO100026SalesHistory"))
		   {
			  String fiscalYear = (String) requestClass.elementAt(0);
			  String fiscalPeriod = (String) requestClass.elementAt(1);
			  
		   	  sqlString.append("UPDATE " + library + ".o100026 ");
		   	  sqlString.append("SET UCIVQT = 0 ");
		   	  sqlString.append("WHERE OSSSTT = 32 ");
		   	  sqlString.append("AND ((OSYEA4 = " + fiscalYear + " ");
		   	  sqlString.append("AND OSPERI >= " + fiscalPeriod + ") ");
		   	  sqlString.append("OR OSYEA4 > " + fiscalYear + ") ");
		   }
		   
		   
		   
		   
		   //******************************************************************
		   //*** DELETE SECTION
		   //******************************************************************
		   
		   if (inRequestType.equals("deleteFromZ100005"))
		   {  
		   	  sqlString.append("DELETE FROM " + dblib + ".Z100005 ");
		   	  sqlString.append(" WHERE CPYEA4 > 2008 ");
		   }

		   
		   
		   
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for request type " + inRequestType + ". ");
		}
		
		
		
		if (inRequestType.equals("updateDataSet32FromZfile"))
		{
		   	String fileName = (String) requestClass.elementAt(0);
		   	ResultSet rs    = (ResultSet) requestClass.elementAt(1);
		   	BigDecimal ivqt = (BigDecimal) requestClass.elementAt(2);
		   	BigDecimal saam = (BigDecimal) requestClass.elementAt(3);
		   	BigDecimal madj = (BigDecimal) requestClass.elementAt(4);
		   	//BigDecimal ivqa = (BigDecimal) requestClass.elementAt(5);
		   	  
			sqlString.append("UPDATE " + library + "." + fileName + " ");
			sqlString.append("SET UCIVQT = " + ivqt.toString() + ", ");
			//sqlString.append("MFCFOR = " + ivqa.toString() + ", ");
			sqlString.append("UCSAAM = " + saam.toString() + ", ");  
			sqlString.append("MFMADJ = " + madj.toString() + " "); 
			sqlString.append("WHERE OSCONO = 100 ");
			sqlString.append("AND OSSSTT = 32 ");
			sqlString.append("AND OSYEA4 = " + rs.getString("OSYEA4") + " ");
			sqlString.append("AND OSPERI = " + rs.getString("OSPERI") + " ");
			sqlString.append("AND UCITNO = '" + rs.getString("UCITNO") + "' ");
			sqlString.append("AND UCCUNO = '" + rs.getString("UCCUNO") + "' ");
		}
		
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceSalesWorkFiles.");
			throwError.append("buildSqlStatement(String, Vector)");
			throw new Exception(throwError.toString());
		}
		   
		return sqlString.toString();
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

		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceSalesWorkFiles.");
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
		stophere = "no";
		
		try {
			
			//Build OLD Sales Detail For Reporting
			//Loads from old AS400 (OEPVX)
			if ("x".equals("y"))
			{
				
				//old sales history from old OE system
				if (1 == 2)
					buildOldSalesDetail("2007", "01");
				
				if (1 == 2)
					buildSalesDetailOepvx("2007", "01");
				
				if (1 == 2)
					updateZeroInvoiceLineDetail();
					
				if (1 == 2) 
					fixSalesDetailFiscalPeriod();
			}
			
			
			//Build Sales Detail For Reporting
			if ("x".equals("y"))
			{
				if (1 == 1)
				{
					try {
						updateAlternateUomOsbstd();
					} catch (Exception e) {
						String errorHere = "X";
					}
				}
				
				if (1 == 1)
					updateZfileKgToLb();
				
				if (1 == 1)
					buildSalesDetailSequenceZero();
				
				if (1 == 1)
					buildSalesDetailSequenceNonZero();
				
				if (1 == 1)
					updateInvoiceLevelDetail();
				
				if (1 == 1)
					updateSequenceLevelDetail();
				
				if (1 == 1) 
					fixSalesDetailFiscalPeriod();
				
				if (1 == 1)
					updateSalesDollars();
			}
			
			
			//Compare
			// Clear file DBPRD/Z100004 before execution for a begining date.
			if ("x".equals("y"))
			{
				if (1 == 1)
				{
					buildCompare("20110119", "20110122"); // date to rebuild must be between.
					//buildCompare("20101122", "20101125");
					//buildCompare("20101126", "20101128");
					//buildCompare("20101130", "20101204");
					//buildCompare("20101004", "20101006");
					//buildCompare("20100929", "20101003");
					//buildCompare("20100827", "20100829");
					//buildCompare("20100820", "20100822");
				}
			}
			
			
			//Update OSBSTD fields that need to be realigned.
			if ("x".equals("y"))
			{
				if (1 == 2) //modify Customer Master values.
				{
					updateOsbstdAssociatedCustomerMasterValues();
				}
				
				if (1==2) //modify Item Master values.
				{
					updateOsbstdAssociatedItemMasterValues();
				}
			}
			
			
			//Update Z100003 fields that need to be realigned.
			if ("x".equals("y"))
			{
				if (1 == 2) //modify Customer Master values.
				{
					updateZ100003AssociatedCustomerMasterValues();
				}
				
				if (1==2) //modify Item Master values.
				{
					updateZ100003AssociatedItemMasterValues();
				}
			}
			
			
			
			//Update Data Set Files for Budget or Forecast.
			//This process takes records from a file
			//saved off in library BUDGTEMP and loads the same entries
			//into a file with the same name in library M3DJDPRD. The
			//record keys get re-aligned (Item/Customer) criteria.
			if ("x".equals("y"))
			{
				if (1 == 1) //modify Item & Customer Master values.
				{
					updateBudgetDataSet("o100027", "2011", "1", "33", "34", "FOQ1", "");
					updateBudgetDataSet("o100027", "2011", "2", "33", "34", "FOQ1", "");
					updateBudgetDataSet("o100027", "2011", "3", "33", "34", "FOQ1", "");
					updateBudgetDataSet("o100027", "2011", "4", "33", "34", "FOQ1", "");
					updateBudgetDataSet("o100027", "2011", "5", "33", "34", "FOQ1", "");
					updateBudgetDataSet("o100027", "2011", "6", "33", "34", "FOQ1", "");
					updateBudgetDataSet("o100027", "2011", "7", "33", "34", "FOQ1", "");
					updateBudgetDataSet("o100027", "2011", "8", "33", "34", "FOQ1", "");
					updateBudgetDataSet("o100027", "2011", "9", "33", "34", "FOQ1", "");
					updateBudgetDataSet("o100027", "2011", "10", "33", "34", "FOQ1", "");
					updateBudgetDataSet("o100027", "2011", "11", "33", "34", "FOQ1", "");
					updateBudgetDataSet("o100027", "2011", "12", "33", "34", "FOQ1", "");
				}
			}
			
			
			//This process builds 32 records into a Data Set file
			//defined in M3. The 32's (actuals) are loaded from
			//the Sales History file "Z100003" into a select set
			//of data set files. Pass in the data set file name
			//and the periods to load the sales history from.
			if ("x".equals("y"))
			{
				if (1 ==1) //load the data set below with actuls.
				{
					// run for each diferent fiscal year.
					buildDataSetSalesFromZFile("o100012", "2011", "07", "2011", "07");
					//buildDataSetSalesFromZFile("o100013", "2010", "08", "2010", "08");
					//buildDataSetSalesFromZFile("o100014", "2010", "08", "2010", "08");
					//buildDataSetSalesFromZFile("o100015", "2010", "08", "2010", "08");
					//buildDataSetSalesFromZFile("o100017", "2010", "08", "2010", "08");
					//buildDataSetSalesFromZFile("o100018", "2010", "08", "2010", "08");
					//buildDataSetSalesFromZFile("o100021", "2010", "08", "2010", "08");
					//buildDataSetSalesFromZFile("o100025", "2010", "08", "2010", "08");
				}
			}
			
			
			
			//Build the Sales Summary files (Z100005) (o100022).
			if ("x".equals("y"))
			{
				if (1 == 2)
				{
					buildSalesSummary();//Z100005
				}
				
				if (1 == 1)
				{
					buildSalesSummaryDataSet();//o100022 and o100026
				}
			}
			
			String stopHere = "yes";
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
/**
 * Load detail file with invoiced sequence zero
 * sales data from file OINACC and OSBSTD.
 * 
 * @param 
 * @return 
 * @throws Exception
 */
public static void buildSalesDetailSequenceZero()
{
	StringBuffer throwError = new StringBuffer();
	Connection connA = null;
	Connection connB = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		connA = ConnectionStack4.getConnection();
		connB = ConnectionStack4.getConnection();
		
		// execute the update method
		Vector conns = new Vector();
		conns.addElement(connA);
		conns.addElement(connB);
		conns = buildSalesDetailSequenceZero(conns);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try
		{
			if (connA != null)
				ConnectionStack4.returnConnection(connA);
		} catch(Exception e){
			throwError.append("Error closing connection. " + e);
		}
		try
		{
			if (connB != null)
				ConnectionStack4.returnConnection(connB);
		} catch(Exception e){
			throwError.append("Error closing connection. " + e);
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildSalesDetailSequenceZero(). ");
	}
	
	return;
}



/**
 * Clear and load Sales History Summary File
 * Z100005
 * @param 
 * @return 
 * @throws Exception
 */
public static void buildSalesSummary()
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		
		conn = getDBConnection();
		
		// execute the update method
		conn = buildSalesSummary(conn);
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
				throwError.append("Error closing connection - " + el.toString() + ". ");
			}
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildSalesSummary(). ");
		Error e = new Error();
		System.out.println(throwError.toString());
		throw new Exception(throwError.toString());
	}
	
	return;
}



/**
 * Load detail file with invoice 
 * sales data from file OSBSTD and Others.
 * Pervious to May 2008.
 * 
 * @param 
 * @return 
 * @throws Exception
 */
public static void buildOldSalesDetail(String fYear, String fPeriod)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildOldSalesDetail(conn, fYear, fPeriod);
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
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildOldSalesDetail(");
		throwError.append("). ");
	}
	
	return;
}



/**
 * update order/invoice level detail to Z100003 file records
 * where invoice detail line is zero. 
 * 
 * @throws Exception
 */
public static void updateInvoiceLevelDetail()
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack4.getConnection();
		
		// execute the update method
		conn = updateInvoiceLevelDetail(conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try
		{
			if (conn != null)
				ConnectionStack4.returnConnection(conn);
		} catch(Exception e){
			throwError.append("Error closing connection. " + e);
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateInvoiceLevelDetail(");
		throwError.append("). ");
	}
	
	return;
}



/**
 * update invoice sequence level detail to Z100003 file records
 * where invoice detail line is non zero. 
 * 
 * @throws Exception
 */
public static void updateSequenceLevelDetail()
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack4.getConnection();
		
		// execute the update method
		conn = updateSequenceLevelDetail(conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try
		{
			if (conn != null)
				ConnectionStack4.returnConnection(conn);
		} catch(Exception e){
			throwError.append("Error closing connection. " + e);
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateSequenceLevelDetail(");
		throwError.append("). ");
	}
	
	return;
}



/**
 * add customer detail to file records
 * where invoice detail line is zero. 
 * 
 * @param String fiscal year
 * @return String fiscal period
 * @throws Exception
 */
public static void updateZeroInvoiceLineDetail()
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = updateZeroInvoiceLineDetail(conn);
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
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateZeroInvoiceLineDetail(");
		throwError.append("). ");
	}
	
	return;
}



/**
 * fix detail file after build. 
 * 
 * @param String fiscal year
 * @return String fiscal period
 * @throws Exception
 */
public static void fixSalesDetailFiscalPeriod()
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack4.getConnection();
		
		// execute the update method
		conn = fixSalesDetailFiscalPeriod(conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try
		{
			if (conn != null)
				ConnectionStack4.returnConnection(conn);
		} catch(Exception e){
			throwError.append("Error closing connection. " + e);
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("fixSalesDetailFiscalPeriod(");
		throwError.append("). ");
	}
	
	return;
}



/**
 * Build a comparision file.
 * FGLEDG, OINACC, Z100003.
 * 
 * @param 
 * @return 
 * @throws Exception
 */
public static void buildCompare(String fromDate, String toDate)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		String error = "";
		
		//remove records that will be rebuilt.
		if (1 == 1)
		{
			String sqlString = "DELETE FROM DBPRD.Z100004 ";
			sqlString = sqlString + "WHERE ACDT > " + fromDate + " ";
			//sqlString = sqlString + "AND ACDT < " + toDate + " "; //temporary
			PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
			ppZ4.executeUpdate();
			ppZ4.close();
		}
		
		//get the FGLEDG file records.
		if (1 ==1)
		{
			String sqlString = "SELECT EGACDT, EGAIT1, EGACAM, EGVTXT ";
			sqlString = sqlString + "FROM M3DJDPRD.FGLEDG ";
			sqlString = sqlString + "WHERE EGFEID = 'OI20' AND EGACDT > " + fromDate + " ";
			//sqlString = sqlString + " AND EGACDT < " + toDate + " "; //temporary
			sqlString = sqlString + "AND EGACAM <> 0 ";
			PreparedStatement ppFg = conn.prepareStatement(sqlString);
			ResultSet rsFg = ppFg.executeQuery();

			while (rsFg.next())
			{
				//build Z100004 access. Determine add or update.
				sqlString = "SELECT * FROM DBPRD.Z100004 ";
				sqlString = sqlString + "WHERE ACDT = " + rsFg.getString("EGACDT").trim() + " ";
				sqlString = sqlString + "AND IVNO = " + rsFg.getString("EGVTXT").substring(17,26) + " ";
				sqlString = sqlString + "AND AIT1 = '" + rsFg.getString("EGAIT1") + "' ";
				PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
				ResultSet rsZ4 = ppZ4.executeQuery();
				
				if (rsZ4.next())
				{
					BigDecimal egacam = new BigDecimal(rsFg.getString("EGACAM"));
					BigDecimal egamt  = new BigDecimal(rsZ4.getString("EGAMT"));
					egamt = egamt.add(egacam);
					egamt.setScale(2);
					sqlString = "UPDATE DBPRD.Z100004 SET EGAMT = " + egamt + " ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
					PreparedStatement updateIt = conn.prepareStatement(sqlString);
					updateIt.executeUpdate();
					updateIt.close();
				} else
				{
					sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
					sqlString = sqlString + rsFg.getString("EGACDT") + ", ";
					sqlString = sqlString + rsFg.getString("EGVTXT").substring(17,26)+ ", ";
					sqlString = sqlString + "'" + rsFg.getString("EGAIT1") + "', ";
					sqlString = sqlString + rsFg.getString("EGACAM") + ", ";
					sqlString = sqlString + "0,0)";
					PreparedStatement addIt = conn.prepareStatement(sqlString);
					addIt.executeUpdate();
					addIt.close();
				}
				
				ppZ4.close();
				rsZ4.close();
		}
		
		ppFg.close();
		rsFg.close();
		}
		
		
		//get the OINACC file records.
		if (1 ==1)
		{
			String sqlString = "SELECT UTACDT, UTAIT1, UTACAM, UTIVNO ";
			sqlString = sqlString + "FROM M3DJDPRD.OINACC ";
			sqlString = sqlString + "WHERE UTACDT > " + fromDate + " ";
			//sqlString = sqlString + " AND UTACDT < " + toDate + " "; //temporary
			sqlString = sqlString + "AND UTACAM <> 0 ";
			PreparedStatement ppOi = conn.prepareStatement(sqlString);
			ResultSet rsOi = ppOi.executeQuery();

			while (rsOi.next())
			{
				//build Z100004 access. Determine add or update.
				sqlString = "SELECT * FROM DBPRD.Z100004 ";
				sqlString = sqlString + "WHERE ACDT = " + rsOi.getString("UTACDT").trim() + " ";
				sqlString = sqlString + "AND IVNO = " + rsOi.getString("UTIVNO") + " ";
				sqlString = sqlString + "AND AIT1 = '" + rsOi.getString("UTAIT1") + "' ";
				PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
				ResultSet rsZ4 = ppZ4.executeQuery();
				
				if (rsZ4.next())
				{
					BigDecimal utacam = new BigDecimal(rsOi.getString("UTACAM"));
					BigDecimal oiamt  = new BigDecimal(rsZ4.getString("OIAMT"));
					oiamt = oiamt.add(utacam);
					oiamt.setScale(2);
					sqlString = "UPDATE DBPRD.Z100004 SET OIAMT = " + oiamt + " ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
					PreparedStatement updateIt = conn.prepareStatement(sqlString);
					updateIt.executeUpdate();
					updateIt.close();
				} else
				{
					sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
					sqlString = sqlString + rsOi.getString("UTACDT") + ", ";
					sqlString = sqlString + rsOi.getString("UTIVNO")+ ", ";
					sqlString = sqlString + "'" + rsOi.getString("UTAIT1") + "', ";
					sqlString = sqlString + "0, ";
					sqlString = sqlString + rsOi.getString("UTACAM") + ", ";
					sqlString = sqlString + "0 )";
					PreparedStatement addIt = conn.prepareStatement(sqlString);
					addIt.executeUpdate();
					addIt.close();
				}
				
				ppZ4.close();
				rsZ4.close();
			}
				
		ppOi.close();
		rsOi.close();
		}
		
		
		//get the Z100003 file records.
		if (1 == 1)
		{
			String sqlString = "SELECT * ";
			sqlString = sqlString + "FROM DBPRD.Z100003 ";
			sqlString = sqlString + "WHERE UTACDT > " + fromDate + " ";
			//sqlString = sqlString + "AND UTACDT < " + toDate + " "; //temporary
			PreparedStatement ppZ3 = conn.prepareStatement(sqlString);	
			ResultSet rsZ3 = ppZ3.executeQuery();
			BigDecimal zero = new BigDecimal("0");
			int test = 0;

			while (rsZ3.next())
			{
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC11100"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '11100' ";
					
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC11100"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'11100', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC11100") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				try
				{
				BigDecimal amt  = new BigDecimal(rsZ3.getString("UTAC24100"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '24100' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC24100"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'24100', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC24100") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC24200"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '24200' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC24200"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'24200', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC24200") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC40100"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '40100' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC40100"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'40100', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC40100") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC40300"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '40300' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC40300"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'40300', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC40300") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC40400"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '40400' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC40400"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'40400', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC40400") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC40500"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '40500' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC40500"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'40500', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC40500") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC40600"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '40600' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC40600"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'40600', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC40600") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC40700"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '40700' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC40700"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'40700', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC40700") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC40725"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '40725' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC40725"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'40725', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC40725") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC40735"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '40735' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC40735"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'40735', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC40735") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC40810"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '40810' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC40810"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'40810', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC40810") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC40830"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '40830' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC40830"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'40830', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC40830") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC58120"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '58120' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC58120"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'58120', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC58120") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC58130"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '58130' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC58130"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'58130', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC58130") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC58150"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '58150' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC58150"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'58150', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC58150") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC58330"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '58330' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC58330"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'58330', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC58330") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC58410"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '58410' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC58410"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'58410', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC58410") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC58420"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '58420' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC58420"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'58420', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC58420") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC58430"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '58430' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC58430"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'58430', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC58430") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC60120"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '60120' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC60120"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'60120', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC60120") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC60125"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '60125' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC60125"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'60125', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC60125") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC60135"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '60135' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC60135"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'60135', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC60135") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC60610"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '60610' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC60610"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'60610', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC60610") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC60660"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '60660' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC60660"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'60660', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC60660") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC61610"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '61610' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC61610"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'61610', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC61610") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC61630"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '61630' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC61630"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'61630', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC61630") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC64276"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '64276' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC64276"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'64276', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC64276") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC64530"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '64530' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC64530"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'64530', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC64530") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC64610"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '64610' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC64610"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'64610', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC64610") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC65540"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '65540' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC65540"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'65540', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC65540") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC65670"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '65670' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC65670"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'65670', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC65670") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC65750"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '65750' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC65750"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'65750', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC65750") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC66950"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '66950' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC66950"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'66950', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC66950") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC67100"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '67100' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC67100"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'67100', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC67100") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTAC99999"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = '99999' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTAC99999"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'99999', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTAC99999") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
				
				
				try
				{
				BigDecimal amt = new BigDecimal(rsZ3.getString("UTACOTHER"));
				test = amt.compareTo(zero);
				
				if (test != 0)
				{
					//build Z100004 access. Determine add or update.
					sqlString = "SELECT * FROM DBPRD.Z100004 ";
					sqlString = sqlString + "WHERE ACDT = " + rsZ3.getString("UTACDT").trim() + " ";
					sqlString = sqlString + "AND IVNO = " + rsZ3.getString("UTIVNO") + " ";
					sqlString = sqlString + "AND AIT1 = 'OTHER' ";
					PreparedStatement ppZ4 = conn.prepareStatement(sqlString);
					ResultSet rsZ4 = ppZ4.executeQuery();
					
					if (rsZ4.next())
					{
						BigDecimal acamt = new BigDecimal(rsZ3.getString("UTACOTHER"));
						BigDecimal z3amt  = new BigDecimal(rsZ4.getString("Z3AMT"));
						z3amt = z3amt.add(acamt);
						z3amt.setScale(2);
						sqlString = "UPDATE DBPRD.Z100004 SET Z3AMT = " + z3amt + " ";
						sqlString = sqlString + "WHERE ACDT = " + rsZ4.getString("ACDT") + " ";
						sqlString = sqlString + "AND IVNO = " + rsZ4.getString("IVNO") + " ";
						sqlString = sqlString + "AND AIT1 = '" + rsZ4.getString("AIT1") + "' ";
						PreparedStatement updateIt = conn.prepareStatement(sqlString);
						updateIt.executeUpdate();
						updateIt.close();
					} else
					{
						sqlString = "INSERT INTO DBPRD.Z100004 VALUES( ";
						sqlString = sqlString + rsZ3.getString("UTACDT") + ", ";
						sqlString = sqlString + rsZ3.getString("UTIVNO")+ ", ";
						sqlString = sqlString + "'OTHER', ";
						sqlString = sqlString + "0, 0,";
						sqlString = sqlString + rsZ3.getString("UTACOTHER") + ") ";
						PreparedStatement addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					}
					
					ppZ4.close();
					rsZ4.close();
				}
				} catch (Exception e) {
					error = e.toString();
					String stophere = "x";
				}
			}
		}

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
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildCompare(");
		throwError.append("). ");
	}
	
	return;
}



/**
 * Build the Sales Detail File. 
 * @param conn, fiscal year, fiscal period.
 * @return Connection.
 */
private static Vector buildSalesDetailSequenceNonZero(Vector conns)						
	throws Exception 
{
	Connection connA = (Connection) conns.elementAt(0);
	Connection connB = (Connection) conns.elementAt(1);
	StringBuffer throwError = new StringBuffer();
	ResultSet rsOinacc  = null;
    ResultSet rsZ100003 = null;
    
    PreparedStatement findOinacc  = null;
	PreparedStatement findZ100003 = null;

	PreparedStatement addIt       = null;
	PreparedStatement updateIt    = null;
	String requestType = "";
	String sqlString = "";
	
	String periodDesc = "";
	
	
	try { //enable finally.
		
		//get all entries not loaded from OINACC to Z100003 (seq NON zero).
		try
		{
			Vector parmClass = new Vector();
			requestType = "getNonZeroSequenceEntriesInOINACC";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (getNonZeroSequenceEntriesInOINACC). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findOinacc = connA.prepareStatement(sqlString);
				rsOinacc = findOinacc.executeQuery();
				
				//keep track of the invoice No, Sequence No)
				String invoiceNo  = "xx";
				String sequenceNo = "xx";
				String delvNo     = "xx";
				
				Vector dtlFile   = new Vector(); 
				
				
				// for each record add/update a sales detail file entry.
				while (rsOinacc.next() && throwError.toString().equals(""))
				{
					
					if (!rsOinacc.getString("UTIVNO").trim().equals(invoiceNo.trim()) ||
						!rsOinacc.getString("UTIVRF").substring(0,5).equals(sequenceNo) ||
				!rsOinacc.getString("UTDLIX").trim().equals(delvNo))
					{	
						invoiceNo  = rsOinacc.getString("UTIVNO").trim();
						sequenceNo = rsOinacc.getString("UTIVRF").substring(0,5);
						delvNo     = rsOinacc.getString("UTDLIX").trim();
						
						//build a vector of strings to use in the sql insert statement.
						dtlFile = new Vector();
						dtlFile.addElement("Z100003");	//file name
						dtlFile.addElement(rsOinacc.getString("UTORNO").trim());
						dtlFile.addElement(sequenceNo);
						dtlFile.addElement(rsOinacc.getString("UTIVNO").trim());
						dtlFile.addElement("0"); //UCIVDT
						dtlFile.addElement(rsOinacc.getString("UTIVDT").trim());
						dtlFile.addElement(rsOinacc.getString("UTACDT").trim());
						dtlFile.addElement(rsOinacc.getString("UTDLIX").trim());
						dtlFile.addElement("");  //UCAGNO
						dtlFile.addElement("0"); //UWAGQT
						dtlFile.addElement("");  //UCCUNO
						dtlFile.addElement("");  //OKCUNM
						dtlFile.addElement("");  //UDADID
						dtlFile.addElement("");  //OPCUNM
						dtlFile.addElement("");  //UCORTP
						dtlFile.addElement("");  //UCWHLO
						dtlFile.addElement("");  //MWWHLO
						dtlFile.addElement("");  //OKFICI
						dtlFile.addElement("");  //CFFACN
						dtlFile.addElement("");  //OKCUCL
						dtlFile.addElement("");  //OKCUCL40
						dtlFile.addElement("");  //OKCUCL15
						dtlFile.addElement("");  //OKSMCD
						dtlFile.addElement("");  //OKSMCD40
						dtlFile.addElement("");  //OKSMCD15
						dtlFile.addElement("");  //OKSDST
						dtlFile.addElement("");  //OKSDST40
						dtlFile.addElement("");  //OKSDST15
						dtlFile.addElement("");  //OKCFC3
						dtlFile.addElement("");  //OKCFC340
						dtlFile.addElement("");  //OKCFC315
						dtlFile.addElement("");  //OKFRE1
						dtlFile.addElement("");  //OKFRE40
						dtlFile.addElement("");  //OKFRE15
						dtlFile.addElement("");  //OKFRE2
						dtlFile.addElement("");  //OKFRE240
						dtlFile.addElement("");  //OKFRE215
						dtlFile.addElement("");  //OKCFC5
						dtlFile.addElement("");  //OKCFC0
						dtlFile.addElement("");  //OKAGNT
						dtlFile.addElement("");  //OKAGNTNM
						dtlFile.addElement("");  //OKPYNO
						dtlFile.addElement("");  //OKPYNONM
						dtlFile.addElement("");  //OKINRC
						dtlFile.addElement("");  //OKINRCNM
						dtlFile.addElement("");  //UCITNO
						dtlFile.addElement("");  //MMITDS
						dtlFile.addElement("");  //MMFUDS
						dtlFile.addElement("");  //MMUNMS
						dtlFile.addElement("");  //MMACRF
						dtlFile.addElement("");  //MMACRF40
						dtlFile.addElement("");  //MMACRF15
						dtlFile.addElement("");  //MMITTY
						dtlFile.addElement("");  //MMITTY40
						dtlFile.addElement("");  //MMITTY15
						dtlFile.addElement("");  //MMITCL
						dtlFile.addElement("");  //MMITCL40
						dtlFile.addElement("");  //MMITCL15
						dtlFile.addElement("");  //MMITGR
						dtlFile.addElement("");  //MMITGR40
						dtlFile.addElement("");  //MMITGR15
						dtlFile.addElement("");  //MMCFI1
						dtlFile.addElement("");  //MMCFI140
						dtlFile.addElement("");  //MMCFI115
						dtlFile.addElement("0"); //UCIVQT
						dtlFile.addElement("0"); //UCIVQA
						dtlFile.addElement("0"); //UCGRWE
						dtlFile.addElement("0"); //UCSAAM
						dtlFile.addElement("0"); //SLAMT
						dtlFile.addElement("");  //UCSTUN
						dtlFile.addElement("");  //UCALUN
						dtlFile.addElement("");  //UCSPUN
						dtlFile.addElement("");  //UCCMP1
						dtlFile.addElement("0"); //UCDIA1
						dtlFile.addElement("0"); //UCDIA2
						dtlFile.addElement("0"); //UCDIA3
						dtlFile.addElement("0"); //UCDIA4
						dtlFile.addElement("0"); //UCDIA5
						dtlFile.addElement("0"); //UCDIA6
						dtlFile.addElement("");  //OPECAR
						dtlFile.addElement("");  //OPECAR40
						dtlFile.addElement("");  //OPECAR15
						dtlFile.addElement("");  //OPCSCD
						dtlFile.addElement("");  //OPCSCD40
						dtlFile.addElement("");  //OPCSCD15
						dtlFile.addElement("0"); //CPYEA4
						dtlFile.addElement("0"); //CPPERI
						dtlFile.addElement("");  //CPTX15
						dtlFile.addElement("0"); //FWEEK
						dtlFile.addElement("");  //UCFACI
						dtlFile.addElement("");  //UCCUCL
						dtlFile.addElement("");  //UTITCL
						
						if (rsOinacc.getString("UTAIT1").trim().equals("11100"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC11100
						
						if (rsOinacc.getString("UTAIT1").trim().equals("24100"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim()); 
						else
							dtlFile.addElement("0"); //UTAC24100
						
						if (rsOinacc.getString("UTAIT1").trim().equals("24200"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC24200
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40100"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40100
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40300"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40300
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40400"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40400
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40500"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40500
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40600"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40600
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40700"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40700
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40725"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40725
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40735"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40735
						
						if (rsOinacc.getString("UTACAM").trim().equals("40810"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40810
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40830"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else	
							dtlFile.addElement("0"); //UTAC40830
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58120"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58120
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58130"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58130
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58150"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58150
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58330"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58330
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58410"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58410
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58420"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58420
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58430"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58430
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60120"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC60120
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60125"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC60125
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60135"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC60135
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60610"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC60610
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60660"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC60660
						
						if (rsOinacc.getString("UTAIT1").trim().equals("61610"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC61610
						
						if (rsOinacc.getString("UTAIT1").trim().equals("61630"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC61630
						
						if (rsOinacc.getString("UTAIT1").trim().equals("64276"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC64276
						
						if (rsOinacc.getString("UTAIT1").trim().equals("64530"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC64530
						
						if (rsOinacc.getString("UTAIT1").trim().equals("64610"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC64610
						
						if (rsOinacc.getString("UTAIT1").trim().equals("65540"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC65540
						
						if (rsOinacc.getString("UTAIT1").trim().equals("65670"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC65670
						
						if (rsOinacc.getString("UTAIT1").trim().equals("65750"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC65750
						
						if (rsOinacc.getString("UTAIT1").trim().equals("66950"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC66950
						
						if (rsOinacc.getString("UTAIT1").trim().equals("67100"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC67100
						
						if (rsOinacc.getString("UTAIT1").trim().equals("99999"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC99999
						
						if (!rsOinacc.getString("UTAIT1").trim().equals("11100") &&
							!rsOinacc.getString("UTAIT1").trim().equals("24100") &&
							!rsOinacc.getString("UTAIT1").trim().equals("24200") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40100") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40300") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40400") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40500") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40600") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40700") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40725") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40735") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40810") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40830") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58120") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58130") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58150") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58330") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58410") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58420") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58430") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60120") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60125") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60135") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60610") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60660") &&
							!rsOinacc.getString("UTAIT1").trim().equals("61610") &&
							!rsOinacc.getString("UTAIT1").trim().equals("61630") &&
							!rsOinacc.getString("UTAIT1").trim().equals("64276") &&
							!rsOinacc.getString("UTAIT1").trim().equals("64530") &&
							!rsOinacc.getString("UTAIT1").trim().equals("64610") &&
							!rsOinacc.getString("UTAIT1").trim().equals("65540") &&
							!rsOinacc.getString("UTAIT1").trim().equals("65670") &&
							!rsOinacc.getString("UTAIT1").trim().equals("65750") &&
							!rsOinacc.getString("UTAIT1").trim().equals("66950") &&
							!rsOinacc.getString("UTAIT1").trim().equals("67100")  )
								dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTACOTHER
						
						if (rsOinacc.getString("UTAIT1").trim().equals("11100"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL110
						
						if (rsOinacc.getString("UTAIT1").trim().equals("24100") ||
							rsOinacc.getString("UTAIT1").trim().equals("24200"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL230
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40100") ||
							rsOinacc.getString("UTAIT1").trim().equals("40300") ||
							rsOinacc.getString("UTAIT1").trim().equals("40400") ||
							rsOinacc.getString("UTAIT1").trim().equals("40500") ||
							rsOinacc.getString("UTAIT1").trim().equals("40600") ||
							rsOinacc.getString("UTAIT1").trim().equals("40700") ||
							rsOinacc.getString("UTAIT1").trim().equals("40725") ||
							rsOinacc.getString("UTAIT1").trim().equals("40735") ||
							rsOinacc.getString("UTAIT1").trim().equals("40810") ||
							rsOinacc.getString("UTAIT1").trim().equals("40830"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL400
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60120") ||
							rsOinacc.getString("UTAIT1").trim().equals("60125"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL410
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60135") ||
							rsOinacc.getString("UTAIT1").trim().equals("60610") ||
							rsOinacc.getString("UTAIT1").trim().equals("60660"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL420
						
						if (rsOinacc.getString("UTAIT1").trim().equals("61610") ||
							rsOinacc.getString("UTAIT1").trim().equals("61630"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL430
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58120") ||
							rsOinacc.getString("UTAIT1").trim().equals("58130") ||
							rsOinacc.getString("UTAIT1").trim().equals("58150") ||
							rsOinacc.getString("UTAIT1").trim().equals("58330") ||
							rsOinacc.getString("UTAIT1").trim().equals("58410") ||
							rsOinacc.getString("UTAIT1").trim().equals("58420") ||
							rsOinacc.getString("UTAIT1").trim().equals("58430"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL530
						
						if (rsOinacc.getString("UTAIT1").trim().equals("65540") ||
							rsOinacc.getString("UTAIT1").trim().equals("65670") ||
							rsOinacc.getString("UTAIT1").trim().equals("65750"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL600
						
						if (!rsOinacc.getString("UTAIT1").trim().equals("11100") &&
							!rsOinacc.getString("UTAIT1").trim().equals("24100") &&
							!rsOinacc.getString("UTAIT1").trim().equals("24200") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40100") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40300") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40400") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40500") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40600") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40700") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40725") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40735") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40810") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40830") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58120") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58130") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58150") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58330") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58410") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58420") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58430") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60120") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60125") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60135") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60610") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60660") &&
							!rsOinacc.getString("UTAIT1").trim().equals("61610") &&
							!rsOinacc.getString("UTAIT1").trim().equals("61630") &&
							!rsOinacc.getString("UTAIT1").trim().equals("64276") &&
							!rsOinacc.getString("UTAIT1").trim().equals("64530") &&
							!rsOinacc.getString("UTAIT1").trim().equals("64610") &&
							!rsOinacc.getString("UTAIT1").trim().equals("65540") &&
							!rsOinacc.getString("UTAIT1").trim().equals("65670") &&
							!rsOinacc.getString("UTAIT1").trim().equals("65750") &&
							!rsOinacc.getString("UTAIT1").trim().equals("66950") &&
							!rsOinacc.getString("UTAIT1").trim().equals("67100")  )
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCLOTHER
						
						
						dtlFile.addElement(rsOinacc.getString("UTIVNO").trim());
						dtlFile.addElement("");  //MMBUAR
						dtlFile.addElement("");  //20091209 add field OKCUST
						
						
						
						//Add a record to the file.
						try
						{
							requestType = "addZ100003Record";
							Vector parmClass = new Vector();
							parmClass.addElement(dtlFile);
							sqlString = buildSqlStatement(requestType, parmClass);
							addIt = connB.prepareStatement(sqlString);
							addIt.executeUpdate();
							addIt.close();
						} catch(Exception e) {
							throwError.append(" Error at execute the sql update Z100003. " + e);
						}
					
					//*******
					// UPDATE A RECORD THAT ALREADY EXISTS.
					} else
					{
						try //build sql to get the record.
						{
							Vector parmClass = new Vector();
							parmClass.addElement(rsOinacc.getString("UTIVNO"));
							parmClass.addElement(sequenceNo);
							parmClass.addElement(delvNo);
							requestType = "getInvoiceLineRecord";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch (Exception e) {
							throwError.append("Error at build sql (getInvoiceLineRecord). " + e);
						}
						
						try //get the Z100003 file record if it exists.
						{
							findZ100003 = connA.prepareStatement(sqlString);
							rsZ100003   = findZ100003.executeQuery();
							
							if (rsZ100003.next() && throwError.toString().equals(""))
							{
						
								//determine the fields to be modified.
								String ait1 = rsOinacc.getString("UTAIT1").trim();
								String aicl = rsOinacc.getString("EAAICL").trim();
								String fieldName1 = "";
								String fieldName2 = "";
								BigDecimal curr1 = new BigDecimal("0");
								BigDecimal curr2 = new BigDecimal("0");
						
						
								if (ait1.equals("11100") || ait1.equals("24100") || ait1.equals("24200") ||
									ait1.equals("40100") || ait1.equals("40300") || ait1.equals("40400") ||
									ait1.equals("40500") || ait1.equals("40600") || ait1.equals("40700") ||
									ait1.equals("40810") || ait1.equals("40830") || ait1.equals("58120") ||
									ait1.equals("58130") || ait1.equals("58150") || ait1.equals("58330") ||
									ait1.equals("58410") || ait1.equals("58420") || ait1.equals("58430") ||
									ait1.equals("60120") || ait1.equals("60125") || ait1.equals("60135") ||
									ait1.equals("60610") || ait1.equals("60660") || ait1.equals("61610") ||
									ait1.equals("61630") || ait1.equals("64276") || ait1.equals("64530") ||
									ait1.equals("64610") || ait1.equals("65540") || ait1.equals("65670") ||
									ait1.equals("65750") || ait1.equals("66950") || ait1.equals("67100") ||
									ait1.equals("99999") || ait1.equals("40725") || ait1.equals("40735") )									
								{
									fieldName1 = "UTAC" + ait1;
									curr1 = rsZ100003.getBigDecimal(fieldName1);
								}
									
								if (aicl.equals("110") || aicl.equals("230") || aicl.equals("400") || 
									aicl.equals("410") || aicl.equals("420") || aicl.equals("430") ||
									aicl.equals("530") || aicl.equals("600"))
								{
									fieldName2 = "UTCL" + aicl;
									curr2 = rsZ100003.getBigDecimal(fieldName2);
								} 
								
								if (fieldName1.trim().equals(""))
								{
									fieldName1 = "UTACOTHER";
									curr1 = rsZ100003.getBigDecimal("UTACOTHER");
								}
								
								if (fieldName2.trim().equals(""))
								{
									fieldName2 = "UTCLOTHER";
									curr2 = rsZ100003.getBigDecimal("UTCLOTHER");
								}						
						
								BigDecimal toAdd = rsOinacc.getBigDecimal("UTACAM");
								curr1 = curr1.add(toAdd);
								curr2 = curr2.add(toAdd);
								
								curr1.setScale(2);
								curr2.setScale(2);
								
								requestType = "updateSalesDetailOinacc";
								Vector parmClass = new Vector();
								parmClass.addElement(invoiceNo);
								parmClass.addElement(sequenceNo);
								parmClass.addElement(delvNo);
								parmClass.addElement(fieldName1);
								parmClass.addElement(curr1);
								parmClass.addElement(fieldName2);
								parmClass.addElement(curr2);
								parmClass.addElement(rsOinacc.getString("UTACDT"));
								sqlString = buildSqlStatement(requestType, parmClass);
								updateIt = connB.prepareStatement(sqlString);
								updateIt.executeUpdate();
								
								updateIt.close();
								findZ100003.close();
								rsZ100003.close();
							}
						} catch(Exception e) {
							throwError.append(" Error at execute the sql (updateSalesDetailOinacc). " + e);
						}
					}
				}
			} catch (Exception e) {
				throwError.append(" Error at execute the sql (getNonZeroSequenceEntriesInOINACC). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
	finally
	{
		try
		{
			if (findOinacc != null)
				findOinacc.close();
			if (findZ100003 != null)
				findZ100003.close();
			if (addIt != null)
				addIt.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsOinacc != null)
				rsOinacc.close();
			if (rsZ100003 != null)
				rsZ100003.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildSalesDetailZeroSequence(");
		throwError.append("Connection, ");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}

  return conns;
}



/**
 * Build the Sales Detail File. 
 * @param conn, fiscal year, fiscal period.
 * @return Connection.
 */
private static Vector buildSalesDetailSequenceZero(Vector conns)						
	throws Exception 
{
	Connection connA = (Connection) conns.elementAt(0);
	Connection connB = (Connection) conns.elementAt(1);
	StringBuffer throwError = new StringBuffer();
	ResultSet rsOinacc  = null;
    ResultSet rsZ100003 = null;
    
    PreparedStatement findOinacc  = null;
	PreparedStatement findZ100003 = null;

	PreparedStatement addIt       = null;
	PreparedStatement updateIt    = null;
	String requestType = "";
	String sqlString = "";
	
	String periodDesc = "";
	
	
	try { //enable finally.
		
		//get all entries not loaded from OINACC to Z100003 (seq zero).
		try
		{
			Vector parmClass = new Vector();
			requestType = "getZeroSequenceEntriesInOINACC";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (getZeroSequenceEntriesInOINACC). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findOinacc = connA.prepareStatement(sqlString);
				rsOinacc = findOinacc.executeQuery();
				
				//keep track of the invoice number)
				String invoiceNo = "xx";
				Vector dtlFile   = new Vector(); 
				
				
				// for each record add/update a sales detail file entry.
				while (rsOinacc.next() && throwError.toString().equals(""))
				{
					
					if (!rsOinacc.getString("UTIVNO").trim().equals(invoiceNo.trim()))
					{	
						invoiceNo = rsOinacc.getString("UTIVNO").trim();
						
						//build a vector of strings to use in the sql insert statement.
						dtlFile = new Vector();
						dtlFile.addElement("Z100003");	//file name
						dtlFile.addElement(rsOinacc.getString("UTORNO").trim());
						dtlFile.addElement("0");
						dtlFile.addElement(rsOinacc.getString("UTIVNO").trim());
						dtlFile.addElement("0"); //UCIVDT
						dtlFile.addElement(rsOinacc.getString("UTIVDT").trim());
						dtlFile.addElement(rsOinacc.getString("UTACDT").trim());
						dtlFile.addElement("0"); //UCDLIX
						dtlFile.addElement("");  //UCAGNO
						dtlFile.addElement("0"); //UWAGQT
						dtlFile.addElement("");  //UCCUNO
						dtlFile.addElement("");  //OKCUNM
						dtlFile.addElement("");  //UDADID
						dtlFile.addElement("");  //OPCUNM
						dtlFile.addElement("");  //UCORTP
						dtlFile.addElement("");  //UCWHLO
						dtlFile.addElement("");  //MWWHLO
						dtlFile.addElement("");  //OKFICI
						dtlFile.addElement("");  //CFFACN
						dtlFile.addElement("");  //OKCUCL
						dtlFile.addElement("");  //OKCUCL40
						dtlFile.addElement("");  //OKCUCL15
						dtlFile.addElement("");  //OKSMCD
						dtlFile.addElement("");  //OKSMCD40
						dtlFile.addElement("");  //OKSMCD15
						dtlFile.addElement("");  //OKSDST
						dtlFile.addElement("");  //OKSDST40
						dtlFile.addElement("");  //OKSDST15
						dtlFile.addElement("");  //OKCFC3
						dtlFile.addElement("");  //OKCFC340
						dtlFile.addElement("");  //OKCFC315
						dtlFile.addElement("");  //OKFRE1
						dtlFile.addElement("");  //OKFRE40
						dtlFile.addElement("");  //OKFRE15
						dtlFile.addElement("");  //OKFRE2
						dtlFile.addElement("");  //OKFRE240
						dtlFile.addElement("");  //OKFRE215
						dtlFile.addElement("");  //OKCFC5
						dtlFile.addElement("");  //OKCFC0
						dtlFile.addElement("");  //OKAGNT
						dtlFile.addElement("");  //OKAGNTNM
						dtlFile.addElement("");  //OKPYNO
						dtlFile.addElement("");  //OKPYNONM
						dtlFile.addElement("");  //OKINRC
						dtlFile.addElement("");  //OKINRCNM
						dtlFile.addElement("");  //UCITNO
						dtlFile.addElement("");  //MMITDS
						dtlFile.addElement("");  //MMFUDS
						dtlFile.addElement("");  //MMUNMS
						dtlFile.addElement("");  //MMACRF
						dtlFile.addElement("");  //MMACRF40
						dtlFile.addElement("");  //MMACRF15
						dtlFile.addElement("");  //MMITTY
						dtlFile.addElement("");  //MMITTY40
						dtlFile.addElement("");  //MMITTY15
						dtlFile.addElement("");  //MMITCL
						dtlFile.addElement("");  //MMITCL40
						dtlFile.addElement("");  //MMITCL15
						dtlFile.addElement("");  //MMITGR
						dtlFile.addElement("");  //MMITGR40
						dtlFile.addElement("");  //MMITGR15
						dtlFile.addElement("");  //MMCFI1
						dtlFile.addElement("");  //MMCFI140
						dtlFile.addElement("");  //MMCFI115
						dtlFile.addElement("0"); //UCIVQT
						dtlFile.addElement("0"); //UCIVQA
						dtlFile.addElement("0"); //UCGRWE
						dtlFile.addElement("0"); //UCSAAM
						dtlFile.addElement("0"); //SLAMT
						dtlFile.addElement("");  //UCSTUN
						dtlFile.addElement("");  //UCALUN
						dtlFile.addElement("");  //UCSPUN
						dtlFile.addElement("");  //UCCMP1
						dtlFile.addElement("0"); //UCDIA1
						dtlFile.addElement("0"); //UCDIA2
						dtlFile.addElement("0"); //UCDIA3
						dtlFile.addElement("0"); //UCDIA4
						dtlFile.addElement("0"); //UCDIA5
						dtlFile.addElement("0"); //UCDIA6
						dtlFile.addElement("");  //OPECAR
						dtlFile.addElement("");  //OPECAR40
						dtlFile.addElement("");  //OPECAR15
						dtlFile.addElement("");  //OPCSCD
						dtlFile.addElement("");  //OPCSCD40
						dtlFile.addElement("");  //OPCSCD15
						dtlFile.addElement("0"); //CPYEA4
						dtlFile.addElement("0"); //CPPERI
						dtlFile.addElement("");  //CPTX15
						dtlFile.addElement("0"); //FWEEK
						dtlFile.addElement("");  //UCFACI
						dtlFile.addElement("");  //UCCUCL
						dtlFile.addElement("");  //UTITCL
						
						if (rsOinacc.getString("UTAIT1").trim().equals("11100"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC11100
						
						if (rsOinacc.getString("UTAIT1").trim().equals("24100"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim()); 
						else
							dtlFile.addElement("0"); //UTAC24100
						
						if (rsOinacc.getString("UTAIT1").trim().equals("24200"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC24200
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40100"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40100
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40300"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40300
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40400"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40400
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40500"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40500
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40600"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40600
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40700"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40700
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40725"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40725
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40735"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40735
						
						if (rsOinacc.getString("UTACAM").trim().equals("40810"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC40810
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40830"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else	
							dtlFile.addElement("0"); //UTAC40830
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58120"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58120
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58130"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58130
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58150"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58150
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58330"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58330
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58410"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58410
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58420"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58420
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58430"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC58430
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60120"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC60120
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60125"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC60125
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60135"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC60135
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60610"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC60610
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60660"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC60660
						
						if (rsOinacc.getString("UTAIT1").trim().equals("61610"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC61610
						
						if (rsOinacc.getString("UTAIT1").trim().equals("61630"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC61630
						
						if (rsOinacc.getString("UTAIT1").trim().equals("64276"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC64276
						
						if (rsOinacc.getString("UTAIT1").trim().equals("64530"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC64530
						
						if (rsOinacc.getString("UTAIT1").trim().equals("64610"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC64610
						
						if (rsOinacc.getString("UTAIT1").trim().equals("65540"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC65540
						
						if (rsOinacc.getString("UTAIT1").trim().equals("65670"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC65670
						
						if (rsOinacc.getString("UTAIT1").trim().equals("65750"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC65750
						
						if (rsOinacc.getString("UTAIT1").trim().equals("66950"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC66950
						
						if (rsOinacc.getString("UTAIT1").trim().equals("67100"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC67100
						
						if (rsOinacc.getString("UTAIT1").trim().equals("99999"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTAC99999
						
						if (!rsOinacc.getString("UTAIT1").trim().equals("11100") &&
							!rsOinacc.getString("UTAIT1").trim().equals("24100") &&
							!rsOinacc.getString("UTAIT1").trim().equals("24200") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40100") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40300") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40400") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40500") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40600") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40700") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40725") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40735") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40810") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40830") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58120") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58130") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58150") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58330") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58410") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58420") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58430") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60120") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60125") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60135") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60610") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60660") &&
							!rsOinacc.getString("UTAIT1").trim().equals("61610") &&
							!rsOinacc.getString("UTAIT1").trim().equals("61630") &&
							!rsOinacc.getString("UTAIT1").trim().equals("64276") &&
							!rsOinacc.getString("UTAIT1").trim().equals("64530") &&
							!rsOinacc.getString("UTAIT1").trim().equals("64610") &&
							!rsOinacc.getString("UTAIT1").trim().equals("65540") &&
							!rsOinacc.getString("UTAIT1").trim().equals("65670") &&
							!rsOinacc.getString("UTAIT1").trim().equals("65750") &&
							!rsOinacc.getString("UTAIT1").trim().equals("66950") &&
							!rsOinacc.getString("UTAIT1").trim().equals("67100")  )
								dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTACOTHER
						
						if (rsOinacc.getString("UTAIT1").trim().equals("11100"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL110
						
						if (rsOinacc.getString("UTAIT1").trim().equals("24100") ||
							rsOinacc.getString("UTAIT1").trim().equals("24200"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL230
						
						if (rsOinacc.getString("UTAIT1").trim().equals("40100") ||
							rsOinacc.getString("UTAIT1").trim().equals("40300") ||
							rsOinacc.getString("UTAIT1").trim().equals("40400") ||
							rsOinacc.getString("UTAIT1").trim().equals("40500") ||
							rsOinacc.getString("UTAIT1").trim().equals("40600") ||
							rsOinacc.getString("UTAIT1").trim().equals("40700") ||
							rsOinacc.getString("UTAIT1").trim().equals("40725") ||
							rsOinacc.getString("UTAIT1").trim().equals("40735") ||
							rsOinacc.getString("UTAIT1").trim().equals("40810") ||
							rsOinacc.getString("UTAIT1").trim().equals("40830"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL400
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60120") ||
							rsOinacc.getString("UTAIT1").trim().equals("60125"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL410
						
						if (rsOinacc.getString("UTAIT1").trim().equals("60135") ||
							rsOinacc.getString("UTAIT1").trim().equals("60610") ||
							rsOinacc.getString("UTAIT1").trim().equals("60660"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL420
						
						if (rsOinacc.getString("UTAIT1").trim().equals("61610") ||
							rsOinacc.getString("UTAIT1").trim().equals("61630"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL430
						
						if (rsOinacc.getString("UTAIT1").trim().equals("58120") ||
							rsOinacc.getString("UTAIT1").trim().equals("58130") ||
							rsOinacc.getString("UTAIT1").trim().equals("58150") ||
							rsOinacc.getString("UTAIT1").trim().equals("58330") ||
							rsOinacc.getString("UTAIT1").trim().equals("58410") ||
							rsOinacc.getString("UTAIT1").trim().equals("58420") ||
							rsOinacc.getString("UTAIT1").trim().equals("58430"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL530
						
						if (rsOinacc.getString("UTAIT1").trim().equals("65540") ||
							rsOinacc.getString("UTAIT1").trim().equals("65670") ||
							rsOinacc.getString("UTAIT1").trim().equals("65750"))
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCL600
						
						if (!rsOinacc.getString("UTAIT1").trim().equals("11100") &&
							!rsOinacc.getString("UTAIT1").trim().equals("24100") &&
							!rsOinacc.getString("UTAIT1").trim().equals("24200") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40100") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40300") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40400") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40500") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40600") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40700") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40725") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40735") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40810") &&
							!rsOinacc.getString("UTAIT1").trim().equals("40830") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58120") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58130") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58150") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58330") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58410") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58420") &&
							!rsOinacc.getString("UTAIT1").trim().equals("58430") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60120") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60125") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60135") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60610") &&
							!rsOinacc.getString("UTAIT1").trim().equals("60660") &&
							!rsOinacc.getString("UTAIT1").trim().equals("61610") &&
							!rsOinacc.getString("UTAIT1").trim().equals("61630") &&
							!rsOinacc.getString("UTAIT1").trim().equals("64276") &&
							!rsOinacc.getString("UTAIT1").trim().equals("64530") &&
							!rsOinacc.getString("UTAIT1").trim().equals("64610") &&
							!rsOinacc.getString("UTAIT1").trim().equals("65540") &&
							!rsOinacc.getString("UTAIT1").trim().equals("65670") &&
							!rsOinacc.getString("UTAIT1").trim().equals("65750") &&
							!rsOinacc.getString("UTAIT1").trim().equals("66950") &&
							!rsOinacc.getString("UTAIT1").trim().equals("67100")  )
							dtlFile.addElement(rsOinacc.getString("UTACAM").trim());
						else
							dtlFile.addElement("0"); //UTCLOTHER
						
						
						dtlFile.addElement(rsOinacc.getString("UTIVNO").trim());
						dtlFile.addElement("");  //MMBUAR
						dtlFile.addElement("");  //OKCUST //20091209 add field OKCUST
						
						//Add a record to the file.
						try
						{
							requestType = "addZ100003Record";
							Vector parmClass = new Vector();
							parmClass.addElement(dtlFile);
							sqlString = buildSqlStatement(requestType, parmClass);
							addIt = connB.prepareStatement(sqlString);
							addIt.executeUpdate();
							addIt.close();
						} catch(Exception e) {
							throwError.append(" Error at execute the sql update Z100003. " + e);
						}
					
					//*******
					// UPDATE A RECORD THAT ALREADY EXISTS.
					} else
					{
						try //build sql to get the record.
						{
							Vector parmClass = new Vector();
							parmClass.addElement(rsOinacc.getString("UTIVNO"));
							parmClass.addElement("0"); //UCPONR
							parmClass.addElement("0"); //UCDLIX
							requestType = "getInvoiceLineRecord";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch (Exception e) {
							throwError.append("Error at build sql (getInvoiceLineRecord). " + e);
						}
						
						try //get the Z100003 file record if it exists.
						{
							findZ100003 = connA.prepareStatement(sqlString);
							rsZ100003   = findZ100003.executeQuery();
							
							if (rsZ100003.next() && throwError.toString().equals(""))
							{
						
								//determine the fields to be modified.
								String ait1 = rsOinacc.getString("UTAIT1").trim();
								String aicl = rsOinacc.getString("EAAICL").trim();
								String fieldName1 = "";
								String fieldName2 = "";
								BigDecimal curr1 = new BigDecimal("0");
								BigDecimal curr2 = new BigDecimal("0");
						
						
								if (ait1.equals("11100") || ait1.equals("24100") || ait1.equals("24200") ||
									ait1.equals("40100") || ait1.equals("40300") || ait1.equals("40400") ||
									ait1.equals("40500") || ait1.equals("40600") || ait1.equals("40700") ||
									ait1.equals("40810") || ait1.equals("40830") || ait1.equals("58120") ||
									ait1.equals("58130") || ait1.equals("58150") || ait1.equals("58330") ||
									ait1.equals("58410") || ait1.equals("58420") || ait1.equals("58430") ||
									ait1.equals("60120") || ait1.equals("60125") || ait1.equals("60135") ||
									ait1.equals("60610") || ait1.equals("60660") || ait1.equals("61610") ||
									ait1.equals("61630") || ait1.equals("64276") || ait1.equals("64530") ||
									ait1.equals("64610") || ait1.equals("65540") || ait1.equals("65670") ||
									ait1.equals("65750") || ait1.equals("66950") || ait1.equals("67100") ||
									ait1.equals("99999") || ait1.equals("47125") || ait1.equals("47135") )									
								{
									fieldName1 = "UTAC" + ait1;
									curr1 = rsZ100003.getBigDecimal(fieldName1);
								}
									
								if (aicl.equals("110") || aicl.equals("230") || aicl.equals("400") || 
									aicl.equals("410") || aicl.equals("420") || aicl.equals("430") ||
									aicl.equals("530") || aicl.equals("600"))
								{
									fieldName2 = "UTCL" + aicl;
									curr2 = rsZ100003.getBigDecimal(fieldName2);
								} 
								
								if (fieldName1.trim().equals(""))
								{
									fieldName1 = "UTACOTHER";
									curr1 = rsZ100003.getBigDecimal("UTACOTHER");
								}
								
								if (fieldName2.trim().equals(""))
								{
									fieldName2 = "UTCLOTHER";
									curr2 = rsZ100003.getBigDecimal("UTCLOTHER");
								}						
						
								BigDecimal toAdd = rsOinacc.getBigDecimal("UTACAM");
								curr1 = curr1.add(toAdd);
								curr2 = curr2.add(toAdd);
								
								curr1.setScale(2);
								curr2.setScale(2);
								
								requestType = "updateSalesDetailOinacc";
								Vector parmClass = new Vector();
								parmClass.addElement(invoiceNo);
								parmClass.addElement("0"); //UCPONR
								parmClass.addElement("0"); //UCDLIX
								parmClass.addElement(fieldName1);
								parmClass.addElement(curr1);
								parmClass.addElement(fieldName2);
								parmClass.addElement(curr2);
								parmClass.addElement(rsOinacc.getString("UTACDT"));
								sqlString = buildSqlStatement(requestType, parmClass);
								updateIt = connB.prepareStatement(sqlString);
								updateIt.executeUpdate();
								
								updateIt.close();
								findZ100003.close();
								rsZ100003.close();
							}
						} catch(Exception e) {
							throwError.append(" Error at execute the sql update Z100003. " + e);
						}
					}
				}
			} catch (Exception e) {
				throwError.append(" Error at execute the sql (getZeroSequenceEntriesInOINACC). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
	finally
	{
		try
		{
			if (findOinacc != null)
				findOinacc.close();
			if (findZ100003 != null)
				findZ100003.close();
			if (addIt != null)
				addIt.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsOinacc != null)
				rsOinacc.close();
			if (rsZ100003 != null)
				rsZ100003.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildSalesDetailZeroSequence(");
		throwError.append("Connection, ");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}

  return conns;
}



/**
 * Fix Sub Ledger Invoice Balances File. 
 * @param conn, fiscal year, fiscal period.
 * @return Connection.
 */
private static Connection fixSalesDetailFiscalPeriod(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsZ100003 = null;
    ResultSet rsCsyper  = null;
	PreparedStatement findZ100003 = null;
	PreparedStatement findCsyper  = null;
	PreparedStatement updateIt    = null;
	String requestType = "";
	String sqlString = "";
	

	
	try { //enable finally.
		
		
		//get the Detail File Z100003 (only if fiscal year = 0).
		// and with invoiceNo <> 0 and orderNo <> empty.
		// and actual date UTACDT <> 0.
		try //get the record
		{
			Vector parmClass = new Vector();
			requestType = "getZ100003ZeroFiscalYear";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("getZ100003ZeroFiscalYear). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findZ100003 = conn.prepareStatement(sqlString);
				rsZ100003 = findZ100003.executeQuery();
				
				// for each record update the Sales File.
				while (rsZ100003.next() && throwError.toString().equals(""))
				{
					//extract keys used for update.
					String orderNo      = rsZ100003.getString("UCORNO");
					String invoiceNo    = rsZ100003.getString("UCIVNO");
					String delvNo       = rsZ100003.getString("UCDLIX");
					String lineNo       = rsZ100003.getString("UCPONR");
					String fiscalYear   = "";
					String fiscalPeriod = "";
					String fiscalName   = "";
					String fiscalWeek   = "";
					
					try //build the sql
					{
						Vector parmClass = new Vector();
						parmClass.addElement(rsZ100003.getString("UTACDT"));
						parmClass.addElement("1");
						requestType = "getFiscalData";
						sqlString = buildSqlStatement(requestType, parmClass);
					} catch (Exception e) {
						throwError.append("Error at build sql (getFiscalData type 1). " + e);
					}
					
					try //execute the sql
					{
						findCsyper = conn.prepareStatement(sqlString);
						rsCsyper   = findCsyper.executeQuery();
						
						if (rsCsyper.next() && throwError.toString().equals(""))
						{
							fiscalYear   = rsCsyper.getString("CPYEA4");
							fiscalPeriod = rsCsyper.getString("CPPERI");
							fiscalName   = rsCsyper.getString("CPTX15");
						}
						
						findCsyper.close();
						rsCsyper.close();
					} catch (Exception e) {
						throwError.append("Error at execute sql (getFiscalData type 1). " + e);
					}
					
					try //build the sql
					{
						Vector parmClass = new Vector();
						parmClass.addElement(rsZ100003.getString("UTACDT"));
						parmClass.addElement("2");
						requestType = "getFiscalData";
						sqlString = buildSqlStatement(requestType, parmClass);
					} catch (Exception e) {
						throwError.append("Error at build sql (getFiscalData type 2). " + e);
					}
					
					try //execute the sql
					{
						findCsyper = conn.prepareStatement(sqlString);
						rsCsyper   = findCsyper.executeQuery();
						
						if (rsCsyper.next() && throwError.toString().equals(""))
						{
							fiscalWeek   = rsCsyper.getString("CPPERI");
						}
						
						findCsyper.close();
						rsCsyper.close();
					} catch (Exception e) {
						throwError.append("Error at execute sql (getFiscalData type 2). " + e);
					}
						
							
					requestType = "updateZ100003FiscalData";
					Vector parmClass = new Vector();
					parmClass.addElement(orderNo);
					parmClass.addElement(invoiceNo);
					parmClass.addElement(delvNo);
					parmClass.addElement(fiscalYear);
					parmClass.addElement(fiscalPeriod);
					parmClass.addElement(fiscalName);
					parmClass.addElement(fiscalWeek);
					parmClass.addElement(lineNo);
					sqlString = buildSqlStatement(requestType, parmClass);
					updateIt = conn.prepareStatement(sqlString);
					updateIt.executeUpdate();
					updateIt.close();
				}

			} catch (Exception e) {
				throwError.append("Error at execute sql (getZ100003ZeroFiscalYear). " + e);
			}

		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findCsyper != null)
				findCsyper.close();
			if (findZ100003 != null)
				findZ100003.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsCsyper != null)
				rsCsyper.close();
			if (rsZ100003 != null)
				rsZ100003.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("fixSalesDetail(");
		throwError.append("Connection ");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}



/**
 * Update Order/Invoice Level Detail in file Z100003
 * 
 * @param  Connection
 * @return Connection.
 * 
 * @throws Exception
 */
private static Connection updateInvoiceLevelDetail(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsZ100003            = null;
    ResultSet rsOcusma             = null;
    ResultSet rsCfacil             = null;
    ResultSet rsMitwhl             = null;
    ResultSet rsCsytab             = null;
    ResultSet rsOcusad             = null;
    ResultSet rsCsyper             = null;
	PreparedStatement findZ100003  = null;
	PreparedStatement findOcusma   = null;
	PreparedStatement findCfacil   = null;
	PreparedStatement findMitwhl   = null;
	PreparedStatement findCsytab   = null;
	PreparedStatement findOcusad   = null;
	PreparedStatement findCsyper   = null;
	PreparedStatement updateIt     = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
	
		//get the Detail File Z100003 (A) with joined OSBSTD (B) data.
		try //get the records
		{
			Vector parmClass = new Vector();
			requestType = "getZ100003WithNoCustomer";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at sql build of (getZ100003WithNoCustomer). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findZ100003 = conn.prepareStatement(sqlString);
				rsZ100003   = findZ100003.executeQuery();
				
				
				// keep track of invoice number.
				String invoiceNo = "xx";
				String cuno      = "xx";
				
				//define the output vector elements.
				Vector dtlFile = new Vector();
				dtlFile.addElement("");// - 
				dtlFile.addElement("");// - 1
				dtlFile.addElement("");// - 2
				dtlFile.addElement("");// - 3
				dtlFile.addElement("");// - 4
				dtlFile.addElement("");// - 5
				dtlFile.addElement("");// - 6
				dtlFile.addElement("");// - 7
				dtlFile.addElement("");// - 8
				dtlFile.addElement("");// - 9
				dtlFile.addElement("");// - 10
				dtlFile.addElement("");// - 11
				dtlFile.addElement("");// - 12
				dtlFile.addElement("");// - 13
				dtlFile.addElement("");// - 14
				dtlFile.addElement("");// - 15
				dtlFile.addElement("");// - 16
				dtlFile.addElement("");// - 17
				dtlFile.addElement("");// - 18
				dtlFile.addElement("");// - 19
				dtlFile.addElement("");// - 20
				dtlFile.addElement("");// - 21
				dtlFile.addElement("");// - 22
				dtlFile.addElement("");// - 23
				dtlFile.addElement("");// - 24
				dtlFile.addElement("");// - 25
				dtlFile.addElement("");// - 26
				dtlFile.addElement("");// - 27
				dtlFile.addElement("");// - 28
				dtlFile.addElement("");// - 29
				dtlFile.addElement("");// - 30
				dtlFile.addElement("");// - 31
				dtlFile.addElement("");// - 32
				dtlFile.addElement("");// - 33
				dtlFile.addElement("");// - 34
				dtlFile.addElement("");// - 35
				dtlFile.addElement("");// - 36
				dtlFile.addElement("");// - 37
				dtlFile.addElement("");// - 38
				dtlFile.addElement("");// - 39
				dtlFile.addElement("");// - 40
				dtlFile.addElement("");// - 41
				dtlFile.addElement("0");// - 42
				dtlFile.addElement("0");// - 43
				dtlFile.addElement("");// - 44
				dtlFile.addElement("0");// - 45
				dtlFile.addElement("");// - 46 //20091209 add field OKCUST
				
				
				// for each record update the Sales File.
				while (rsZ100003.next() && throwError.toString().equals(""))
				{
					//extract/update when invoice number changes.
					if (!rsZ100003.getString("aUTIVNO").trim().equals(invoiceNo))
					{
						invoiceNo = rsZ100003.getString("aUTIVNO").trim();
						
						//Load the vector with all other order level data.
						//vector to hold fields to update.
						
						dtlFile.setElementAt("Z100003", 0); 							//file name to update.
						dtlFile.setElementAt(rsZ100003.getString("bUCCUNO").trim(), 1);	//UCCUNO   - 1
						dtlFile.setElementAt(rsZ100003.getString("bUCADID").trim(), 3);	//UCADID   - 3
						dtlFile.setElementAt(rsZ100003.getString("bUCORTP").trim(), 5);	//UCORTP   - 5
						dtlFile.setElementAt(rsZ100003.getString("bUCWHLO").trim(), 6);	//UCWHLO   - 6
						dtlFile.setElementAt(rsZ100003.getString("bUCINRC").trim(), 34);//UCINRC   - 34
						

						//get Customer Master By Customer
						if (!rsZ100003.getString("bUCCUNO").trim().equals("") &&
							!rsZ100003.getString("bUCCUNO").trim().equals(cuno))
						{
							cuno = rsZ100003.getString("bUCCUNO").trim();
							
							try {
								Vector parmClass = new Vector();
								parmClass.addElement(rsZ100003.getString("bUCCUNO").trim());
								requestType = "getCustomerMasterByCustomer";
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch(Exception e) {
									throwError.append("Error at build sql (getCustomerMasterByCustomer). " + e);
							}
							
							// execute sql.
							if (throwError.toString().equals(""))
							{
								try {
									findOcusma = conn.prepareStatement(sqlString);
									rsOcusma = findOcusma.executeQuery();
										
									// for each record add a sales detail file entry.
									if (rsOcusma.next() && throwError.toString().equals(""))
									{			
										dtlFile.setElementAt(rsOcusma.getString("OKCUNM"), 2);
										dtlFile.setElementAt(rsOcusma.getString("OKFACI"), 8);
										dtlFile.setElementAt(rsOcusma.getString("OKCUCL"), 10);
										dtlFile.setElementAt(rsOcusma.getString("OKSMCD"), 13);
										dtlFile.setElementAt(rsOcusma.getString("OKSDST"), 16);
										dtlFile.setElementAt(rsOcusma.getString("OKCFC3"), 19);
										dtlFile.setElementAt(rsOcusma.getString("OKFRE1"), 22);
										dtlFile.setElementAt(rsOcusma.getString("OKFRE2"), 25);
										dtlFile.setElementAt(rsOcusma.getString("OKCFC5"), 28);
										dtlFile.setElementAt(rsOcusma.getString("OKCFC0"), 29);
										//dtlFile.setElementAt(rsOcusma.getString("OKAGNT"), 30);
										dtlFile.setElementAt(rsOcusma.getString("OKPYNO"), 32);
										dtlFile.setElementAt(rsOcusma.getString("OKCUST"), 46); //20091209 add field OKCUST
									} else
									{
										dtlFile.setElementAt("", 2);
										dtlFile.setElementAt("", 8);
										dtlFile.setElementAt("", 10);
										dtlFile.setElementAt("", 13);
										dtlFile.setElementAt("", 16);
										dtlFile.setElementAt("", 19);
										dtlFile.setElementAt("", 22);
										dtlFile.setElementAt("", 25);
										dtlFile.setElementAt("", 28);
										dtlFile.setElementAt("", 29);
										//dtlFile.setElementAt(", 30);
										dtlFile.setElementAt("", 32);
										dtlFile.setElementAt("", 46); //20091209 add field OKCUST
									}
								} catch (Exception e) {
									throwError.append(" Error at execute the sql (getCustomerMasterByCustomer). " + e);
								}
									
								findOcusma.close();
								rsOcusma.close();
							}
							
							//get Customer Master By Payer
							String pyno = (String) dtlFile.elementAt(32);
							dtlFile.setElementAt("",  33);
							if (!pyno.trim().equals(""))
							{
								try {
									Vector parmClass = new Vector();
									parmClass.addElement(pyno.trim());
									requestType = "getCustomerMasterByCustomer";
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch(Exception e) {
										throwError.append("Error at build sql Payer(getCustomerMasterByCustomer). " + e);
								}
								
								// execute sql.
								if (throwError.toString().equals(""))
								{
									try {
										findOcusma = conn.prepareStatement(sqlString);
										rsOcusma = findOcusma.executeQuery();
											
										// for each record add a sales detail file entry.
										if (rsOcusma.next() && throwError.toString().equals(""))
										{			
											dtlFile.setElementAt(rsOcusma.getString("OKCUNM"),  33);
										}
									} catch (Exception e) {
										throwError.append(" Error at execute the sql Payer(getCustomerMasterByCustomer). " + e);
									}
										
									findOcusma.close();
									rsOcusma.close();
								}
							}
							
							//get Facility master
							String facility = (String) dtlFile.elementAt(8);
							dtlFile.setElementAt("", 9);
							
							if (!facility.trim().equals(""))
							{
								try {
									Vector parmClass = new Vector();
									parmClass.addElement(facility);
									requestType = "getFacilityMaster";
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch(Exception e) {
										throwError.append("Error at build sql (getFacilityMaster). " + e);
								}
								
								// execute sql.
								if (throwError.toString().equals(""))
								{
									try {
										findCfacil = conn.prepareStatement(sqlString);
										rsCfacil = findCfacil.executeQuery();
											
										// for each record add a sales detail file entry.
										if (rsCfacil.next() && throwError.toString().equals(""))
										{			
											dtlFile.setElementAt(rsCfacil.getString("CFFACN"), 9);
										}
									} catch (Exception e) {
										throwError.append(" Error at execute the sql (getFacilityMaster). " + e);
									}
										
									findCfacil.close();
									rsCfacil.close();
								}
							}
							
							
							//get Csytab data
							
							//Customer Group
							String ctstky = "";
							String ctstco = "";
							
							ctstky = (String) dtlFile.elementAt(10);
							dtlFile.setElementAt("", 11);
							dtlFile.setElementAt("", 12);
							ctstco = "CUCL";
							if (!ctstky.trim().equals(""))
							{
								try 
								{
									Vector parmClass = new Vector();
									parmClass.addElement(ctstco);
									parmClass.addElement(ctstky);
									requestType = "getCsytab";
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch(Exception e) {
									throwError.append("Error at build sql (getCsytab-#10). " + e);
								}
							
								// execute sql.
								if (throwError.toString().equals(""))
								{
									try 
									{
										findCsytab = conn.prepareStatement(sqlString);
										rsCsytab = findCsytab.executeQuery();
										
										// for each record add a sales detail file entry.
										if (rsCsytab.next() && throwError.toString().equals(""))
										{			
											dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 11);
											dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 12);
										}
									} catch (Exception e) {
										throwError.append(" Error at execute the sql (getCsytab-#10). " + e);
									}
									
									findCsytab.close();
									rsCsytab.close();
								}
							}
							
							//Sales Person
							ctstky = (String) dtlFile.elementAt(13);
							dtlFile.setElementAt("", 14);
							dtlFile.setElementAt("", 15);
							ctstco = "SMCD";
							if (!ctstky.trim().equals(""))
							{
								try 
								{
									Vector parmClass = new Vector();
									parmClass.addElement(ctstco);
									parmClass.addElement(ctstky);
									requestType = "getCsytab";
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch(Exception e) {
									throwError.append("Error at build sql (getCsytab-#13). " + e);
								}
							
								// execute sql.
								if (throwError.toString().equals(""))
								{
									try 
									{
										findCsytab = conn.prepareStatement(sqlString);
										rsCsytab = findCsytab.executeQuery();
										
										// for each record add a sales detail file entry.
										if (rsCsytab.next() && throwError.toString().equals(""))
										{			
											dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 14);
											dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 15);
										}
									} catch (Exception e) {
										throwError.append(" Error at execute the sql (getCsytab-#13). " + e);
									}
									
									findCsytab.close();
									rsCsytab.close();
								}
							}
							
							//Sales District
							ctstky = (String) dtlFile.elementAt(16);
							dtlFile.setElementAt("", 17);
							dtlFile.setElementAt("", 18);
							ctstco = "SDST";
							if (!ctstky.trim().equals(""))
							{
								try 
								{
									Vector parmClass = new Vector();
									parmClass.addElement(ctstco);
									parmClass.addElement(ctstky);
									requestType = "getCsytab";
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch(Exception e) {
									throwError.append("Error at build sql (getCsytab-#16). " + e);
								}
							
								// execute sql.
								if (throwError.toString().equals(""))
								{
									try 
									{
										findCsytab = conn.prepareStatement(sqlString);
										rsCsytab = findCsytab.executeQuery();
										
										// for each record add a sales detail file entry.
										if (rsCsytab.next() && throwError.toString().equals(""))
										{			
											dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 17);
											dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 18);
										}
									} catch (Exception e) {
										throwError.append(" Error at execute the sql (getCsytab-#16). " + e);
									}
									
									findCsytab.close();
									rsCsytab.close();
								}
							}
							
							//Market
							ctstky = (String) dtlFile.elementAt(19);
							dtlFile.setElementAt("", 20);
							dtlFile.setElementAt("", 21);
							ctstco = "CFC3";
							if (!ctstky.trim().equals(""))
							{
								try 
								{
									Vector parmClass = new Vector();
									parmClass.addElement(ctstco);
									parmClass.addElement(ctstky);
									requestType = "getCsytab";
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch(Exception e) {
									throwError.append("Error at build sql (getCsytab-#19). " + e);
								}
							
								// execute sql.
								if (throwError.toString().equals(""))
								{
									try 
									{
										findCsytab = conn.prepareStatement(sqlString);
										rsCsytab = findCsytab.executeQuery();
										
										// for each record add a sales detail file entry.
										if (rsCsytab.next() && throwError.toString().equals(""))
										{			
											dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 20);
											dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 21);
										}
									} catch (Exception e) {
										throwError.append(" Error at execute the sql (getCsytab-#19). " + e);
									}
									
									findCsytab.close();
									rsCsytab.close();
								}
							}
							
							//Plan To
							ctstky = (String) dtlFile.elementAt(22);
							dtlFile.setElementAt("", 23);
							dtlFile.setElementAt("", 24);
							ctstco = "FRE1";
							if (!ctstky.trim().equals(""))
							{
								try 
								{
									Vector parmClass = new Vector();
									parmClass.addElement(ctstco);
									parmClass.addElement(ctstky);
									requestType = "getCsytab";
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch(Exception e) {
									throwError.append("Error at build sql (getCsytab-#22). " + e);
								}
							
								// execute sql.
								if (throwError.toString().equals(""))
								{
									try 
									{
										findCsytab = conn.prepareStatement(sqlString);
										rsCsytab = findCsytab.executeQuery();
										
										// for each record add a sales detail file entry.
										if (rsCsytab.next() && throwError.toString().equals(""))
										{			
											dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 23);
											dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 24);
										}
									} catch (Exception e) {
										throwError.append(" Error at execute the sql (getCsytab-#22). " + e);
									}
									
									findCsytab.close();
									rsCsytab.close();
								}
							}
							
							//Freight District
							ctstky = (String) dtlFile.elementAt(25);
							dtlFile.setElementAt("", 26);
							dtlFile.setElementAt("", 27);
							ctstco = "FRE2";
							if (!ctstky.trim().equals(""))
							{
								try 
								{
									Vector parmClass = new Vector();
									parmClass.addElement(ctstco);
									parmClass.addElement(ctstky);
									requestType = "getCsytab";
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch(Exception e) {
									throwError.append("Error at build sql (getCsytab-#25). " + e);
								}
							
								// execute sql.
								if (throwError.toString().equals(""))
								{
									try 
									{
										findCsytab = conn.prepareStatement(sqlString);
										rsCsytab = findCsytab.executeQuery();
										
										// for each record add a sales detail file entry.
										if (rsCsytab.next() && throwError.toString().equals(""))
										{			
											dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 26);
											dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 27);
										}
									} catch (Exception e) {
										throwError.append(" Error at execute the sql (getCsytab-#25). " + e);
									}
									
									findCsytab.close();
									rsCsytab.close();
								}
							}
						}
							
						
					
						//get Customer Master By Invoice Recipient
						dtlFile.setElementAt("",  35);
						if (!rsZ100003.getString("bUCINRC").trim().equals(""))
						{
							try {
								Vector parmClass = new Vector();
								parmClass.addElement(rsZ100003.getString("bUCINRC").trim());
								requestType = "getCustomerMasterByCustomer";
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch(Exception e) {
									throwError.append("Error at build sql Inv Recp(getCustomerMasterByCustomer). " + e);
							}
							
							// execute sql.
							if (throwError.toString().equals(""))
							{
								try {
									findOcusma = conn.prepareStatement(sqlString);
									rsOcusma = findOcusma.executeQuery();
										
									// for each record add a sales detail file entry.
									if (rsOcusma.next() && throwError.toString().equals(""))
									{			
										dtlFile.setElementAt(rsOcusma.getString("OKCUNM"),  35);
									}
								} catch (Exception e) {
									throwError.append(" Error at execute the sql Inv Recp(getCustomerMasterByCustomer). " + e);
								}
									
								findOcusma.close();
								rsOcusma.close();
							}
						}
						
						
						//get Warehouse master
						dtlFile.setElementAt("", 7);
						String whlo = rsZ100003.getString("bUCWHLO").trim();
						if (!whlo.equals(""))
						{
							try {
								Vector parmClass = new Vector();
								parmClass.addElement(whlo);
								requestType = "getWarehouseMaster";
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch(Exception e) {
									throwError.append("Error at build sql (getWarehouseMaster). " + e);
							}
							
							// execute sql.
							if (throwError.toString().equals(""))
							{
								try {
									findMitwhl = conn.prepareStatement(sqlString);
									rsMitwhl = findMitwhl.executeQuery();
										
									// for each record add a sales detail file entry.
									if (rsMitwhl.next() && throwError.toString().equals(""))
									{			
										dtlFile.setElementAt(rsMitwhl.getString("MWWHNM"), 7);
									}
								} catch (Exception e) {
									throwError.append(" Error at execute the sql (getWarehouseMaster). " + e);
								}
									
								findMitwhl.close();
								rsMitwhl.close();
							}
						}
						
						

						
						//Invoice Customer ShipTo.
						dtlFile.setElementAt("", 4);
						dtlFile.setElementAt("", 36);
						dtlFile.setElementAt("", 39);
						cuno = (String) dtlFile.elementAt(1);
						String adid = (String) dtlFile.elementAt(3);
						if (!adid.equals(""))
						{
							try 
							{
								Vector parmClass = new Vector();
								parmClass.addElement(cuno);
								parmClass.addElement(adid);
								requestType = "getCustomerShipTo";
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch (Exception e) {
								throwError.append("Error at build sql (getCustomerShipTo). " + e);
							}
							
							try //read the ShipTo record.
							{
								findOcusad = conn.prepareStatement(sqlString);
								rsOcusad   = findOcusad.executeQuery();
								
								if (rsOcusad.next() && throwError.toString().equals(""))
								{
									dtlFile.setElementAt(rsOcusad.getString("OPCUNM"), 4);
									dtlFile.setElementAt(rsOcusad.getString("OPECAR"), 36);
									dtlFile.setElementAt(rsOcusad.getString("OPCSCD"), 39);
								}
							} catch (Exception e) {
								throwError.append("Error at execute sql (getCustomerShipTo). " + e);
								String stopHere = "x";
							}
							
							findOcusad.close();
							rsOcusad.close();
						}
						
						
						//Ship To State
						dtlFile.setElementAt("", 37);
						dtlFile.setElementAt("", 38);
						String ctstky = (String) dtlFile.elementAt(36);
						String ctstco = "ECAR";
						if (!ctstky.trim().equals(""))
						{
							try 
							{
								Vector parmClass = new Vector();
								parmClass.addElement(ctstco);
								parmClass.addElement(ctstky);
								requestType = "getCsytab";
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch(Exception e) {
								throwError.append("Error at build sql (getCsytab-#36). " + e);
							}
						
							// execute sql.
							if (throwError.toString().equals(""))
							{
								try 
								{
									findCsytab = conn.prepareStatement(sqlString);
									rsCsytab = findCsytab.executeQuery();
									
									// for each record add a sales detail file entry.
									if (rsCsytab.next() && throwError.toString().equals(""))
									{			
										dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 37);
										dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 38);
									}
								} catch (Exception e) {
									throwError.append(" Error at execute the sql (getCsytab-#36). " + e);
								}
								
								findCsytab.close();
								rsCsytab.close();
							}
						}
						
						
						//Country
						dtlFile.setElementAt("", 40);
						dtlFile.setElementAt("", 41);
						ctstky = (String) dtlFile.elementAt(39);
						ctstco = "EDES";
						if (!ctstky.trim().equals(""))
						{
							try 
							{
								Vector parmClass = new Vector();
								parmClass.addElement(ctstco);
								parmClass.addElement(ctstky);
								requestType = "getCsytab";
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch(Exception e) {
								throwError.append("Error at build sql (getCsytab-#39). " + e);
							}
						
							// execute sql.
							if (throwError.toString().equals(""))
							{
								try 
								{
									findCsytab = conn.prepareStatement(sqlString);
									rsCsytab = findCsytab.executeQuery();
									
									// for each record add a sales detail file entry.
									if (rsCsytab.next() && throwError.toString().equals(""))
									{			
										dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 40);
										dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 41);
									}
								} catch (Exception e) {
									throwError.append(" Error at execute the sql (getCsytab-#39). " + e);
								}
								
								findCsytab.close();
								rsCsytab.close();
							}
						}
						
						
						// Date information
						dtlFile.setElementAt("0", 42);
						dtlFile.setElementAt("0", 43);
						dtlFile.setElementAt("", 44);
						dtlFile.setElementAt("0", 45);
						try //build the sql
						{
							Vector parmClass = new Vector();
							parmClass.addElement(rsZ100003.getString("aUTACDT"));
							parmClass.addElement("1");
							requestType = "getFiscalData";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch (Exception e) {
							throwError.append("Error at build sql (getFiscalData type 1). " + e);
						}
						
						try //execute the sql
						{
							findCsyper = conn.prepareStatement(sqlString);
							rsCsyper   = findCsyper.executeQuery();
							
							if (rsCsyper.next() && throwError.toString().equals(""))
							{
								dtlFile.setElementAt(rsCsyper.getString("CPYEA4"), 42);
								dtlFile.setElementAt(rsCsyper.getString("CPPERI"), 43);
								dtlFile.setElementAt(rsCsyper.getString("CPTX15"), 44);
							}
							
							findCsyper.close();
							rsCsyper.close();
						} catch (Exception e) {
							throwError.append("Error at execute sql (getFiscalData type 1). " + e);
						}
						
						try //build the sql
						{
							Vector parmClass = new Vector();
							parmClass.addElement(rsZ100003.getString("aUTACDT"));
							parmClass.addElement("2");
							requestType = "getFiscalData";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch (Exception e) {
							throwError.append("Error at build sql (getFiscalData type 2). " + e);
						}
						
						try //execute the sql
						{
							findCsyper = conn.prepareStatement(sqlString);
							rsCsyper   = findCsyper.executeQuery();
							
							if (rsCsyper.next() && throwError.toString().equals(""))
							{
								dtlFile.setElementAt(rsCsyper.getString("CPPERI"), 45);
							}
							
							findCsyper.close();
							rsCsyper.close();
						} catch (Exception e) {
							throwError.append("Error at execute sql (getFiscalData type 2). " + e);
						}
						
						
						//Add a record to the file.
						try
						{
							if (throwError.toString().trim().equals(""))
							{
								requestType = "updateZ100003OrderDetail";
								Vector parmClass = new Vector();
								parmClass.addElement(invoiceNo);
								parmClass.addElement(dtlFile);
								sqlString = buildSqlStatement(requestType, parmClass);
								updateIt = conn.prepareStatement(sqlString);
								updateIt.executeUpdate();
								updateIt.close();
							}
						} catch(Exception e) {
							throwError.append(" Error at execute the sql update Z100003. " + e);
							String stopHere = "x";
						}
					}
				}

				
				rsZ100003.close();
				findZ100003.close();
				
			} catch (Exception e) {
				throwError.append("Error at execute sql (getZ100003WithNoCustomer). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findZ100003 != null)
				findZ100003.close();
			if (findOcusma != null)
				findOcusma.close();
			if (findCfacil != null)
				findCfacil.close();
			if (findMitwhl != null)
				findMitwhl.close();
			if (findCsytab != null)
				findCsytab.close();
			if (findOcusad != null)
				findOcusad.close();
			if (findCsyper != null)
				findCsyper.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsZ100003 != null)
				rsZ100003.close();
			if (rsOcusma != null)
				rsOcusma.close();
			if (rsCfacil != null)
				rsCfacil.close();
			if (rsMitwhl != null)
				rsMitwhl.close();
			if (rsCsytab != null)
				rsCsytab.close();
			if (rsOcusad != null)
				rsOcusad.close();
			if (rsCsyper != null)
				rsCsyper.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateOrderLevelDetail(");
		throwError.append("Connection).");
		throw new Exception(throwError.toString());
	}

  return conn;
}



/**
 * Update Invoice Sequence Level Detail in file Z100003
 * 
 * @param  Connection
 * @return Connection.
 * 
 * @throws Exception
 */
private static Connection updateSequenceLevelDetail(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsZ100003            = null;
    ResultSet rsOagrln             = null;
    ResultSet rsCsytab             = null;
    ResultSet rsOcusma			   = null;
    ResultSet rsMitmas             = null;
	PreparedStatement findZ100003  = null;
	PreparedStatement findCsytab   = null;
	PreparedStatement findOcusma   = null;
	PreparedStatement findMitmas   = null;
	PreparedStatement findOagrln   = null;
	PreparedStatement updateIt     = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
	
		//get the Detail File Z100003 (A) with joined OSBSTD (B) data.
		try //get the records
		{
			Vector parmClass = new Vector();
			requestType = "getZ100003WithNoItem";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at sql build of (getZ100003WithNoItem). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findZ100003 = conn.prepareStatement(sqlString);
				rsZ100003   = findZ100003.executeQuery();
				
				
				// keep track of invoice and sequence numbers.
				String invoiceNo  = "xx";
				String sequenceNo = "xx";
				String itno       = "xx";
				String delvNo     = "xx";
				Vector dtlFile = new Vector();
				
				//define the output vector elements.
				dtlFile.addElement("");// - 
				dtlFile.addElement("0");// - 1
				dtlFile.addElement("0");// - 2
				dtlFile.addElement("");// - 3
				dtlFile.addElement("0");// - 4
				dtlFile.addElement("");// - 5
				dtlFile.addElement("");// - 6
				dtlFile.addElement("");// - 7
				dtlFile.addElement("");// - 8
				dtlFile.addElement("");// - 9
				dtlFile.addElement("");// - 10
				dtlFile.addElement("");// - 11
				dtlFile.addElement("");// - 12
				dtlFile.addElement("");// - 13
				dtlFile.addElement("");// - 14
				dtlFile.addElement("");// - 15
				dtlFile.addElement("");// - 16
				dtlFile.addElement("");// - 17
				dtlFile.addElement("");// - 18
				dtlFile.addElement("");// - 19
				dtlFile.addElement("");// - 20
				dtlFile.addElement("");// - 21
				dtlFile.addElement("");// - 22
				dtlFile.addElement("");// - 23
				dtlFile.addElement("0");// - 24
				dtlFile.addElement("0");// - 25
				dtlFile.addElement("0");// - 26
				dtlFile.addElement("0");// - 27
				dtlFile.addElement("");// - 28
				dtlFile.addElement("");// - 29
				dtlFile.addElement("");// - 30
				dtlFile.addElement("");// - 31
				dtlFile.addElement("0");// - 32
				dtlFile.addElement("0");// - 33
				dtlFile.addElement("0");// - 34
				dtlFile.addElement("0");// - 35
				dtlFile.addElement("0");// - 36
				dtlFile.addElement("0");// - 37
				dtlFile.addElement("");// - 38
				dtlFile.addElement("");// - 39
				
				
				// for each record update the Sales File.
				while (rsZ100003.next() && throwError.toString().equals(""))
				{
					
					invoiceNo  = rsZ100003.getString("aUTIVNO").trim();
					sequenceNo = rsZ100003.getString("aUCPONR").trim();
					delvNo     = rsZ100003.getString("aUCDLIX").trim();
					
					//Load the vector with all other sequence level data.
					//vector to hold fields to update.
					
					dtlFile.setElementAt("Z100003", 0); 							//file name to update.
					dtlFile.setElementAt(rsZ100003.getString("bUCIVDT").trim(), 1);	//UCIVDT   - 1
					dtlFile.setElementAt(rsZ100003.getString("bUCDLIX").trim(), 2);	//UCDLIX   - 2
					dtlFile.setElementAt(rsZ100003.getString("bUCAGNO").trim(), 3);	//UCAGNO   - 3
					dtlFile.setElementAt(rsZ100003.getString("bUCITNO").trim(), 5);	//UCITNO   - 5
					dtlFile.setElementAt(rsZ100003.getString("bUCIVQT").trim(), 24);//UCIVQT   - 24
					dtlFile.setElementAt(rsZ100003.getString("bUCIVQA").trim(), 25);//UCIVQA   - 25
					dtlFile.setElementAt(rsZ100003.getString("bUCGRWE").trim(), 26);//UCGRWE   - 26
					dtlFile.setElementAt(rsZ100003.getString("bUCSAAM").trim(), 27);//UCSAAM   - 27
					dtlFile.setElementAt(rsZ100003.getString("bUCSTUN").trim(), 28);//UCSTUN   - 28
					dtlFile.setElementAt(rsZ100003.getString("bUCALUN").trim(), 29);//UCALUN   - 29
					dtlFile.setElementAt(rsZ100003.getString("bUCSPUN").trim(), 30);//UCSPUN   - 30
					dtlFile.setElementAt(rsZ100003.getString("bUCCMP1").trim(), 31);//UCCMP1   - 31
					dtlFile.setElementAt(rsZ100003.getString("bUCDIA1").trim(), 32);//UCDIA1   - 32
					dtlFile.setElementAt(rsZ100003.getString("bUCDIA2").trim(), 33);//UCDIA2   - 33
					dtlFile.setElementAt(rsZ100003.getString("bUCDIA3").trim(), 34);//UCDIA3   - 34
					dtlFile.setElementAt(rsZ100003.getString("bUCDIA4").trim(), 35);//UCDIA4   - 35
					dtlFile.setElementAt(rsZ100003.getString("bUCDIA5").trim(), 36);//UCDIA5   - 36
					dtlFile.setElementAt(rsZ100003.getString("bUCDIA6").trim(), 37);//UCDIA6   - 37
					
					
					//get Customer Master By Agent
					dtlFile.setElementAt("",  39);
					if (!rsZ100003.getString("bUCAGNO").trim().equals(""))
					{
						
						
						try {
							Vector parmClass = new Vector();
							parmClass.addElement(rsZ100003.getString("bUCAGNO").trim());
							requestType = "getCustomerMasterByCustomer";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
								throwError.append("Error at build sql Agent(getCustomerMasterByCustomer). " + e);
						}
						
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								findOcusma = conn.prepareStatement(sqlString);
								rsOcusma = findOcusma.executeQuery();
									
								// for each record add a sales detail file entry.
								if (rsOcusma.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsOcusma.getString("OKCUNM"),  39);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql Agent(getCustomerMasterByCustomer). " + e);
							}
								
							findOcusma.close();
							rsOcusma.close();
						}
					}
					
					//get agreement quantity.
					dtlFile.setElementAt("0",4);
					if (!rsZ100003.getString("bUCAGNO").trim().equals(""))
					{
						try {
						Vector parmClass = new Vector();
						parmClass.addElement(rsZ100003.getString("bUCCUNO").trim());
						parmClass.addElement(rsZ100003.getString("bUCAGNO").trim());
						parmClass.addElement(rsZ100003.getString("bUCITNO").trim());
						requestType = "getAgreementQuantity";
						sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getAgreementQuantity). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								findOagrln = conn.prepareStatement(sqlString);
								rsOagrln = findOagrln.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsOagrln.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsOagrln.getString("UWAGQT"),4);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getAgreementQuantity). " + e);
							}
							
							findOagrln.close();
							rsOagrln.close();
						}
					}

					//get Item Master
					if (!rsZ100003.getString("bUCITNO").trim().equals("") &&
						!rsZ100003.getString("bUCITNO").trim().equals(itno) )
					{
						dtlFile.setElementAt("", 6);
						dtlFile.setElementAt("", 7);
						dtlFile.setElementAt("", 8);
						dtlFile.setElementAt("", 9);
						dtlFile.setElementAt("", 12);
						dtlFile.setElementAt("", 15);
						dtlFile.setElementAt("", 18);
						dtlFile.setElementAt("", 21);
						dtlFile.setElementAt("", 38);
						itno = rsZ100003.getString("bUCITNO").trim();
						
						try {
							Vector parmClass = new Vector();
							parmClass.addElement(rsZ100003.getString("bUCITNO").trim());
							requestType = "getItemMaster";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
								throwError.append("Error at build sql (getItemMaster). " + e);
						}
						
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								findMitmas = conn.prepareStatement(sqlString);
								rsMitmas = findMitmas.executeQuery();
									
								// for each record add a sales detail file entry.
								if (rsMitmas.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsMitmas.getString("MMITDS"), 6);
									dtlFile.setElementAt(rsMitmas.getString("MMFUDS"), 7);
									dtlFile.setElementAt(rsMitmas.getString("MMUNMS"), 8);
									dtlFile.setElementAt(rsMitmas.getString("MMACRF"), 9);
									dtlFile.setElementAt(rsMitmas.getString("MMITTY"), 12);
									dtlFile.setElementAt(rsMitmas.getString("MMITCL"), 15);
									dtlFile.setElementAt(rsMitmas.getString("MMITGR"), 18);
									dtlFile.setElementAt(rsMitmas.getString("MMCFI1"), 21);
									dtlFile.setElementAt(rsMitmas.getString("MMBUAR"), 38);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getItemMaster). " + e);
							}
								
							findMitmas.close();
							rsMitmas.close();
						}
						
						//Accounting Control Object
						dtlFile.setElementAt("", 10);
						dtlFile.setElementAt("", 11);
						String ctstky = (String) dtlFile.elementAt(9);
						String ctstco = "ACRF";
						if (!ctstky.trim().equals(""))
						{
							try 
							{
								Vector parmClass = new Vector();
								parmClass.addElement(ctstco);
								parmClass.addElement(ctstky);
								requestType = "getCsytab";
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch(Exception e) {
								throwError.append("Error at build sql (getCsytab-#9). " + e);
							}
						
							// execute sql.
							if (throwError.toString().equals(""))
							{
								try 
								{
									findCsytab = conn.prepareStatement(sqlString);
									rsCsytab = findCsytab.executeQuery();
									
									// for each record add a sales detail file entry.
									if (rsCsytab.next() && throwError.toString().equals(""))
									{			
										dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 10);
										dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 11);
									}
								} catch (Exception e) {
									throwError.append(" Error at execute the sql (getCsytab-#9). " + e);
								}
								
								findCsytab.close();
								rsCsytab.close();
							}
						}
						
						
						//Item Type
						dtlFile.setElementAt("", 13);
						dtlFile.setElementAt("", 14);
						ctstky = (String) dtlFile.elementAt(12);
						ctstco = "ITTY";
						if (!ctstky.trim().equals(""))
						{
							try 
							{
								Vector parmClass = new Vector();
								parmClass.addElement(ctstco);
								parmClass.addElement(ctstky);
								requestType = "getCsytab";
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch(Exception e) {
								throwError.append("Error at build sql (getCsytab-#12). " + e);
							}
						
							// execute sql.
							if (throwError.toString().equals(""))
							{
								try 
								{
									findCsytab = conn.prepareStatement(sqlString);
									rsCsytab = findCsytab.executeQuery();
									
									// for each record add a sales detail file entry.
									if (rsCsytab.next() && throwError.toString().equals(""))
									{			
										dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 13);
										dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 14);
									}
								} catch (Exception e) {
									throwError.append(" Error at execute the sql (getCsytab-#12). " + e);
								}
								
								findCsytab.close();
								rsCsytab.close();
							}
						}
						
						
						//Product Group
						dtlFile.setElementAt("", 16);
						dtlFile.setElementAt("", 17);
						ctstky = (String) dtlFile.elementAt(15);
						ctstco = "ITCL";
						if (!ctstky.trim().equals(""))
						{
							try 
							{
								Vector parmClass = new Vector();
								parmClass.addElement(ctstco);
								parmClass.addElement(ctstky);
								requestType = "getCsytab";
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch(Exception e) {
								throwError.append("Error at build sql (getCsytab-#15). " + e);
							}
						
							// execute sql.
							if (throwError.toString().equals(""))
							{
								try 
								{
									findCsytab = conn.prepareStatement(sqlString);
									rsCsytab = findCsytab.executeQuery();
									
									// for each record add a sales detail file entry.
									if (rsCsytab.next() && throwError.toString().equals(""))
									{			
										dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 16);
										dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 17);
									}
								} catch (Exception e) {
									throwError.append(" Error at execute the sql (getCsytab-#15). " + e);
								}
								
								findCsytab.close();
								rsCsytab.close();
							}
						}
						
						
						//Item Group
						dtlFile.setElementAt("", 19);
						dtlFile.setElementAt("", 20);
						ctstky = (String) dtlFile.elementAt(18);
						ctstco = "ITGR";
						if (!ctstky.trim().equals(""))
						{
							try 
							{
								Vector parmClass = new Vector();
								parmClass.addElement(ctstco);
								parmClass.addElement(ctstky);
								requestType = "getCsytab";
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch(Exception e) {
								throwError.append("Error at build sql (getCsytab-#18). " + e);
							}
						
							// execute sql.
							if (throwError.toString().equals(""))
							{
								try 
								{
									findCsytab = conn.prepareStatement(sqlString);
									rsCsytab = findCsytab.executeQuery();
									
									// for each record add a sales detail file entry.
									if (rsCsytab.next() && throwError.toString().equals(""))
									{			
										dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 19);
										dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 20);
									}
								} catch (Exception e) {
									throwError.append(" Error at execute the sql (getCsytab-#18). " + e);
								}
								
								findCsytab.close();
								rsCsytab.close();
							}
						}
						
						
						//Size/Piece Size
						dtlFile.setElementAt("", 22);
						dtlFile.setElementAt("", 23);
						ctstky = (String) dtlFile.elementAt(21);
						ctstco = "CFI1";
						if (!ctstky.trim().equals(""))
						{
							try 
							{
								Vector parmClass = new Vector();
								parmClass.addElement(ctstco);
								parmClass.addElement(ctstky);
								requestType = "getCsytab";
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch(Exception e) {
								throwError.append("Error at build sql (getCsytab-#21). " + e);
							}
						
							// execute sql.
							if (throwError.toString().equals(""))
							{
								try 
								{
									findCsytab = conn.prepareStatement(sqlString);
									rsCsytab = findCsytab.executeQuery();
									
									// for each record add a sales detail file entry.
									if (rsCsytab.next() && throwError.toString().equals(""))
									{			
										dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 22);
										dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 23);
									}
								} catch (Exception e) {
									throwError.append(" Error at execute the sql (getCsytab-#21). " + e);
								}
								
								findCsytab.close();
								rsCsytab.close();
							}
						}
					}
					
					

					
					
					//update the record.
					try
					{
						if (throwError.toString().trim().equals(""))
						{
							requestType = "updateZ100003SequenceDetail";
							Vector parmClass = new Vector();
							parmClass.addElement(invoiceNo);
							parmClass.addElement(sequenceNo);
							parmClass.addElement(delvNo);
							parmClass.addElement(dtlFile);
							sqlString = buildSqlStatement(requestType, parmClass);
							updateIt = conn.prepareStatement(sqlString);
							updateIt.executeUpdate();
							updateIt.close();
						}
					} catch(Exception e) {
						throwError.append(" Error at execute the sql update Z100003. " + e);
						String stopHere = "x";
					}
				}	
				
				rsZ100003.close();
				findZ100003.close();
				
			} catch (Exception e) {
				throwError.append("Error at execute sql (getZ100003WithNoItemNo). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findZ100003 != null)
				findZ100003.close();
			if (findOcusma != null)
				findOcusma.close();
			if (findMitmas != null)
				findMitmas.close();
			if (findCsytab != null)
				findCsytab.close();
			if (findOagrln != null)
				findOagrln.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsZ100003 != null)
				rsZ100003.close();
			if (rsOcusma != null)
				rsOcusma.close();
			if (rsMitmas != null)
				rsMitmas.close();
			if (rsCsytab != null)
				rsCsytab.close();
			if (rsOagrln != null)
				rsOagrln.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateSequenceLevelDetail(");
		throwError.append("Connection).");
		throw new Exception(throwError.toString());
	}

  return conn;
}



/**
 * Update Detail Lines where line number is zero
 * with Customer Information from the same Invoice with
 * a non zero detail line. 
 * @param conn, fiscal year, fiscal period.
 * @return Connection.
 */
private static Connection updateZeroInvoiceLineDetail(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsZ100003AtZero             = null;
    ResultSet rsZ100003NotZero            = null;
	PreparedStatement findZ100003AtZero   = null;
	PreparedStatement findZ100003NotZero  = null;
	PreparedStatement updateIt            = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
	
		//get the Detail File Z100003 (only if lineNo = 0).
		try //get the record
		{
			Vector parmClass = new Vector();
			requestType = "getZ100003AtZero";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("getZ100003AtZero). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findZ100003AtZero = conn.prepareStatement(sqlString);
				rsZ100003AtZero   = findZ100003AtZero.executeQuery();
				
				// for each record update the Sales File.
				while (rsZ100003AtZero.next() && throwError.toString().equals(""))
				{
					//extract keys used for update.
					String invoiceNo    = rsZ100003AtZero.getString("UCIVNO");
					
					try //build the sql
					{
						Vector parmClass = new Vector();
						parmClass.addElement(invoiceNo);
						requestType = "getZ100003NotZero";
						sqlString = buildSqlStatement(requestType, parmClass);
					} catch (Exception e) {
						throwError.append("Error at build sql (getZ100003NotZero). " + e);
					}
					
					try //execute the sql
					{
						findZ100003NotZero = conn.prepareStatement(sqlString);
						rsZ100003NotZero   = findZ100003NotZero.executeQuery();
						
						if (rsZ100003NotZero.next() && throwError.toString().equals(""))
						{

							try //build the update sql
							{
								Vector parmClass = new Vector();
								parmClass.addElement(rsZ100003NotZero);
								requestType = "updateZeroLineDetail";
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch (Exception e) {
								throwError.append("Error at build sql (updateZeroLineDetail). " + e);
							}
					
							try //execute the sql
							{
								updateIt = conn.prepareStatement(sqlString);
								updateIt.executeUpdate();
								updateIt.close();
							} catch (Exception e) {
								throwError.append("Error at execute sql (updateZeroLineDetail). " + e);
							}
						}
						
						rsZ100003NotZero.close();
						findZ100003NotZero.close();
					} catch (Exception e) {
						throwError.append("Error at execute sql (getZ100003NotZero). " + e);
					} 
				}
				
				rsZ100003AtZero.close();
				findZ100003AtZero.close();
				
			} catch (Exception e) {
				throwError.append("Error at execute sql (getZ100003AtZero). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findZ100003AtZero != null)
				findZ100003AtZero.close();
			if (findZ100003NotZero != null)
				findZ100003NotZero.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsZ100003AtZero != null)
				rsZ100003AtZero.close();
			if (rsZ100003NotZero != null)
				rsZ100003NotZero.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateZeroInvoiceLineDetail(");
		throwError.append("Connection, ");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}



/**
 * Build the Sales Detail File. 
 * @param conn, fiscal year, fiscal period.
 * @return Connection.
 */
private static Connection buildOldSalesDetail(Connection conn, String fYear, String fPeriod)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsOsbstd  = null;
    ResultSet rsOagrln  = null;
    ResultSet rsOcusma  = null;
    ResultSet rsCsytab  = null;
    ResultSet rsMitmas  = null;
    ResultSet rsOcusad  = null;
    ResultSet rsMitwhl  = null;
    ResultSet rsCfacil  = null;
    
	PreparedStatement findOsbstd  = null;
	PreparedStatement findOagrln  = null;
	PreparedStatement findOcusma  = null;
	PreparedStatement findCsytab  = null;
	PreparedStatement findMitmas  = null;
	PreparedStatement findOcusad  = null;
	PreparedStatement findMitwhl  = null;
	PreparedStatement findCfacil  = null;
	
	PreparedStatement addIt       = null;
	String requestType = "";
	String sqlString = "";
	
	String periodDesc = "";
	
	// put these in a business object.
	String fromDate = "";
	String toDate   = "";
	
	try { //enable finally.
		
		//get the invoicing dates.
		try//build sql for from and to dates.
		{
			Vector parmClass = new Vector();
			parmClass.addElement(fYear);
			parmClass.addElement(fPeriod);
			requestType = "getFromToDates";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (getFromToDates). " + e);
		}
		
		//execute sql.
		if (throwError.toString().equals(""))
		{
			try {
				PreparedStatement findDates = conn.prepareStatement(sqlString);
				ResultSet rs = findDates.executeQuery();
				
				if (rs.next())
				{
					fromDate   = rs.getString("CPFDAT");
					toDate     = rs.getString("CPTDAT");
					periodDesc = rs.getString("CPTX15");
				}
			} catch (Exception e) {
				throwError.append("Error at execute sql (getFromToDates). " + e);
			}
		}
		
	
		//get the sales file OSBSTD.
		try //build sql of sales history.
		{
			Vector parmClass = new Vector();
			parmClass.addElement(fromDate);
			parmClass.addElement(toDate);
			requestType = "salesOSBSTDInvoiceDateRange";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (salesOSBSTDInvoiceDateRange). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findOsbstd = conn.prepareStatement(sqlString);
				rsOsbstd = findOsbstd.executeQuery();
				
				// for each record add a sales detail file entry.
				while (rsOsbstd.next() && throwError.toString().equals(""))
				{
					//build a vector of strings to use in the sql insert statement.
					Vector dtlFile = new Vector();
					dtlFile.addElement("Z100003");		//file name
					dtlFile.addElement(rsOsbstd.getString("UCORNO").trim());
					dtlFile.addElement(rsOsbstd.getString("UCPONR").trim());
					dtlFile.addElement(rsOsbstd.getString("UCIVNO").trim());
					dtlFile.addElement(rsOsbstd.getString("UCIVDT").trim());
					dtlFile.addElement(rsOsbstd.getString("UCIVDT").trim());
					dtlFile.addElement(rsOsbstd.getString("UCIVDT").trim());
					dtlFile.addElement(rsOsbstd.getString("UCDLIX").trim());
					dtlFile.addElement(rsOsbstd.getString("UCAGNO").trim());
					dtlFile.addElement("0"); //UWAGQT
					dtlFile.addElement(rsOsbstd.getString("UCCUNO").trim());
					dtlFile.addElement(""); //OKCUNM
					dtlFile.addElement(rsOsbstd.getString("UCADID").trim());
					dtlFile.addElement(""); //OPCUNM
					dtlFile.addElement(rsOsbstd.getString("UCORTP").trim());
					dtlFile.addElement(rsOsbstd.getString("UCWHLO").trim());
					dtlFile.addElement(""); //MWWHLO
					dtlFile.addElement(""); //OKFICI
					dtlFile.addElement(""); //CFFACN
					dtlFile.addElement(""); //OKCUCL
					dtlFile.addElement(""); //OKCUCL40
					dtlFile.addElement(""); //OKCUCL15
					dtlFile.addElement(""); //OKSMCD
					dtlFile.addElement(""); //OKSMCD40
					dtlFile.addElement(""); //OKSMCD15
					dtlFile.addElement(""); //OKSDST
					dtlFile.addElement(""); //OKSDST40
					dtlFile.addElement(""); //OKSDST15
					dtlFile.addElement(""); //OKCFC3
					dtlFile.addElement(""); //OKCFC340
					dtlFile.addElement(""); //OKCFC315
					dtlFile.addElement(""); //OKFRE1
					dtlFile.addElement(""); //OKFRE40
					dtlFile.addElement(""); //OKFRE15
					dtlFile.addElement(""); //OKFRE2
					dtlFile.addElement(""); //OKFRE240
					dtlFile.addElement(""); //OKFRE215
					dtlFile.addElement(""); //OKCFC5
					dtlFile.addElement(""); //OKCFC0
					dtlFile.addElement(""); //OKAGNT
					dtlFile.addElement(""); //OKAGNTNM
					dtlFile.addElement(""); //OKPYNO
					dtlFile.addElement(""); //OKPYNONM
					dtlFile.addElement(""); //OKINRC
					dtlFile.addElement(""); //OKINRCNM
					dtlFile.addElement(rsOsbstd.getString("UCITNO").trim());
					dtlFile.addElement(""); //MMITDS
					dtlFile.addElement(""); //MMFUDS
					dtlFile.addElement(""); //MMUNMS
					dtlFile.addElement(""); //MMACRF
					dtlFile.addElement(""); //MMACRF40
					dtlFile.addElement(""); //MMACRF15
					dtlFile.addElement(""); //MMITTY
					dtlFile.addElement(""); //MMITTY40
					dtlFile.addElement(""); //MMITTY15
					dtlFile.addElement(""); //MMITCL
					dtlFile.addElement(""); //MMITCL40
					dtlFile.addElement(""); //MMITCL15
					dtlFile.addElement(""); //MMITGR
					dtlFile.addElement(""); //MMITGR40
					dtlFile.addElement(""); //MMITGR15
					dtlFile.addElement(""); //MMCFI1
					dtlFile.addElement(""); //MMCFI140
					dtlFile.addElement(""); //MMCFI115
					dtlFile.addElement(rsOsbstd.getString("UCIVQT").trim());
					dtlFile.addElement(rsOsbstd.getString("UCIVQA").trim());
					dtlFile.addElement(rsOsbstd.getString("UCGRWE").trim());
					dtlFile.addElement(rsOsbstd.getString("UCSAAM").trim());
					dtlFile.addElement("0"); //SLAMT
					dtlFile.addElement(rsOsbstd.getString("UCSTUN").trim());
					dtlFile.addElement(rsOsbstd.getString("UCALUN").trim());
					dtlFile.addElement(rsOsbstd.getString("UCSPUN").trim());
					dtlFile.addElement(rsOsbstd.getString("UCCMP1").trim());
					dtlFile.addElement(rsOsbstd.getString("UCDIA1").trim());
					dtlFile.addElement(rsOsbstd.getString("UCDIA2").trim());
					dtlFile.addElement(rsOsbstd.getString("UCDIA3").trim());
					dtlFile.addElement(rsOsbstd.getString("UCDIA4").trim());
					dtlFile.addElement(rsOsbstd.getString("UCDIA5").trim());
					dtlFile.addElement(rsOsbstd.getString("UCDIA6").trim());
					dtlFile.addElement(""); //OPECAR
					dtlFile.addElement(""); //OPECAR40
					dtlFile.addElement(""); //OPECAR15
					dtlFile.addElement(""); //OPCSCD
					dtlFile.addElement(""); //OPCSCD40
					dtlFile.addElement(""); //OPCSCD15
					dtlFile.addElement("0"); //CPYEA4
					dtlFile.addElement("0"); //CPPERI
					dtlFile.addElement("");  //CPTX15
					dtlFile.addElement("0"); //FWEEK
					dtlFile.addElement(rsOsbstd.getString("UCFACI"));
					dtlFile.addElement(rsOsbstd.getString("UCCUCL"));
					dtlFile.addElement(rsOsbstd.getString("UCITCL"));
					dtlFile.addElement("0"); //UTAC11100
					dtlFile.addElement("0"); //UTAC24100
					dtlFile.addElement("0"); //UTAC24200
					dtlFile.addElement("0"); //UTAC40100
					dtlFile.addElement("0"); //UTAC40300
					dtlFile.addElement("0"); //UTAC40400
					dtlFile.addElement("0"); //UTAC40500
					dtlFile.addElement("0"); //UTAC40600
					dtlFile.addElement("0"); //UTAC40700
					dtlFile.addElement("0"); //UTAC40725
					dtlFile.addElement("0"); //UTAC40735
					dtlFile.addElement("0"); //UTAC40810
					dtlFile.addElement("0"); //UTAC40830
					dtlFile.addElement("0"); //UTAC58120
					dtlFile.addElement("0"); //UTAC58130
					dtlFile.addElement("0"); //UTAC58150
					dtlFile.addElement("0"); //UTAC58330
					dtlFile.addElement("0"); //UTAC58410
					dtlFile.addElement("0"); //UTAC58420
					dtlFile.addElement("0"); //UTAC58430
					dtlFile.addElement("0"); //UTAC60120
					dtlFile.addElement("0"); //UTAC60125
					dtlFile.addElement("0"); //UTAC60135
					dtlFile.addElement("0"); //UTAC60610
					dtlFile.addElement("0"); //UTAC60660
					dtlFile.addElement("0"); //UTAC61610
					dtlFile.addElement("0"); //UTAC61630
					dtlFile.addElement("0"); //UTAC64276
					dtlFile.addElement("0"); //UTAC64530
					dtlFile.addElement("0"); //UTAC64610
					dtlFile.addElement("0"); //UTAC65540
					dtlFile.addElement("0"); //UTAC65670
					dtlFile.addElement("0"); //UTAC65750
					dtlFile.addElement("0"); //UTAC66950
					dtlFile.addElement("0"); //UTAC67100
					dtlFile.addElement("0"); //UTAC99999
					dtlFile.addElement("0"); //UTACOTHER
					dtlFile.addElement("0"); //UTCL110
					dtlFile.addElement("0"); //UTCL230
					dtlFile.addElement("0"); //UTCL400
					dtlFile.addElement("0"); //UTCL410
					dtlFile.addElement("0"); //UTCL420
					dtlFile.addElement("0"); //UTCL430
					dtlFile.addElement("0"); //UTCL530
					dtlFile.addElement("0"); //UTCL600
					dtlFile.addElement("0"); //UTCLOTHER
					dtlFile.addElement(rsOsbstd.getString("UCIVNO").trim()); //UTIVNO
					dtlFile.addElement("");  //MMBUAR
					
					//get agreement quantity.
					if (!rsOsbstd.getString("UCAGNO").trim().equals(""))
					{
						try {
						Vector parmClass = new Vector();
						parmClass.addElement(rsOsbstd.getString("UCCUNO").trim());
						parmClass.addElement(rsOsbstd.getString("UCAGNO").trim());
						parmClass.addElement(rsOsbstd.getString("UCITNO").trim());
						requestType = "getAgreementQuantity";
						sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getAgreementQuantity). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								findOagrln = conn.prepareStatement(sqlString);
								rsOagrln = findOagrln.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsOagrln.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsOagrln.getString("UWAGQT"),9);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getAgreementQuantity). " + e);
							}
							
							findOagrln.close();
							rsOagrln.close();
						}
					}
					
						
					//get Customer Master By Customer
					if (!rsOsbstd.getString("UCCUNO").trim().equals(""))
					{
						try {
							Vector parmClass = new Vector();
							parmClass.addElement(rsOsbstd.getString("UCCUNO").trim());
							requestType = "getCustomerMasterByCustomer";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
								throwError.append("Error at build sql (getCustomerMasterByCustomer). " + e);
						}
						
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								findOcusma = conn.prepareStatement(sqlString);
								rsOcusma = findOcusma.executeQuery();
									
								// for each record add a sales detail file entry.
								if (rsOcusma.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsOcusma.getString("OKCUNM"), 11);
									dtlFile.setElementAt(rsOcusma.getString("OKFACI"), 17);
									dtlFile.setElementAt(rsOcusma.getString("OKCUCL"), 19);
									dtlFile.setElementAt(rsOcusma.getString("OKSMCD"), 22);
									dtlFile.setElementAt(rsOcusma.getString("OKSDST"), 25);
									dtlFile.setElementAt(rsOcusma.getString("OKCFC3"), 28);
									dtlFile.setElementAt(rsOcusma.getString("OKFRE1"), 31);
									dtlFile.setElementAt(rsOcusma.getString("OKFRE2"), 34);
									dtlFile.setElementAt(rsOcusma.getString("OKCFC5"), 37);
									dtlFile.setElementAt(rsOcusma.getString("OKCFC0"), 38);
									dtlFile.setElementAt(rsOcusma.getString("OKAGNT"), 39);
									dtlFile.setElementAt(rsOcusma.getString("OKPYNO"), 41);
									dtlFile.setElementAt(rsOcusma.getString("OKINRC"), 43);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCustomerMasterByCustomer). " + e);
							}
								
							findOcusma.close();
							rsOcusma.close();
						}
					}
					
					
					//get Customer Master By Agent
					if (!rsOsbstd.getString("UCAGNT").trim().equals(""))
					{
						try {
							Vector parmClass = new Vector();
							parmClass.addElement(rsOsbstd.getString("UCAGNT").trim());
							requestType = "getCustomerMasterByCustomer";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
								throwError.append("Error at build sql (getCustomerMasterByAgent). " + e);
						}
						
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								findOcusma = conn.prepareStatement(sqlString);
								rsOcusma = findOcusma.executeQuery();
									
								// for each record add a sales detail file entry.
								if (rsOcusma.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsOcusma.getString("OKCUNM"),  40);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCustomerMasterByAgent). " + e);
							}
								
							findOcusma.close();
							rsOcusma.close();
						}
					}
					
					//get Customer Master By Payer
					if (!rsOsbstd.getString("UCPYNO").trim().equals(""))
					{
						try {
							Vector parmClass = new Vector();
							parmClass.addElement(rsOsbstd.getString("UCPYNO").trim());
							requestType = "getCustomerMasterByCustomer";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
								throwError.append("Error at build sql (getCustomerMasterByPayer). " + e);
						}
						
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								findOcusma = conn.prepareStatement(sqlString);
								rsOcusma = findOcusma.executeQuery();
									
								// for each record add a sales detail file entry.
								if (rsOcusma.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsOcusma.getString("OKCUNM"),  42);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCustomerMasterByPayer). " + e);
							}
								
							findOcusma.close();
							rsOcusma.close();
						}
					}
					
					
					//get Customer Master By Invoice Recipient
					if (!rsOsbstd.getString("UCINRC").trim().equals(""))
					{
						try {
							Vector parmClass = new Vector();
							parmClass.addElement(rsOsbstd.getString("UCINRC").trim());
							requestType = "getCustomerMasterByCustomer";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
								throwError.append("Error at build sql (getCustomerMasterByInvoiceRecipient). " + e);
						}
						
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								findOcusma = conn.prepareStatement(sqlString);
								rsOcusma = findOcusma.executeQuery();
									
								// for each record add a sales detail file entry.
								if (rsOcusma.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsOcusma.getString("OKCUNM"),  44);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCustomerMasterByInvoiceRecipient). " + e);
							}
								
							findOcusma.close();
							rsOcusma.close();
						}
					}
					
					
					//get Facility master
					String facility = (String) dtlFile.elementAt(17);
					
					if (!facility.trim().equals(""))
					{
						try {
							Vector parmClass = new Vector();
							parmClass.addElement(facility);
							requestType = "getFacilityMaster";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
								throwError.append("Error at build sql (getFacilityMaster). " + e);
						}
						
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								findCfacil = conn.prepareStatement(sqlString);
								rsCfacil = findCfacil.executeQuery();
									
								// for each record add a sales detail file entry.
								if (rsCfacil.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCfacil.getString("CFFACN"), 18);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getFacilityMaster). " + e);
							}
								
							findCfacil.close();
							rsCfacil.close();
						}
					}
					
					//get Warehouse master
					if (!rsOsbstd.getString("UCWHLO").trim().equals(""))
					{
						try {
							Vector parmClass = new Vector();
							parmClass.addElement(rsOsbstd.getString("UCWHLO").trim());
							requestType = "getWarehouseMaster";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
								throwError.append("Error at build sql (getWarehouseMaster). " + e);
						}
						
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								findMitwhl = conn.prepareStatement(sqlString);
								rsMitwhl = findMitwhl.executeQuery();
									
								// for each record add a sales detail file entry.
								if (rsMitwhl.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsMitwhl.getString("MWWHNM"), 16);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getWarehouseMaster). " + e);
							}
								
							findMitwhl.close();
							rsMitwhl.close();
						}
					}
					
					
					//get Csytab data
					
					//Customer Group
					String ctstky = "";
					String ctstco = "";
					
					ctstky = (String) dtlFile.elementAt(19);
					ctstco = "CUCL";
					if (!ctstky.trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(ctstco);
							parmClass.addElement(ctstky);
							requestType = "getCsytab";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getCsytab-#19). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try 
							{
								findCsytab = conn.prepareStatement(sqlString);
								rsCsytab = findCsytab.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsCsytab.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 20);
									dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 21);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCsytab-#19). " + e);
							}
							
							findCsytab.close();
							rsCsytab.close();
						}
					}
					
					//Sales Person
					ctstky = (String) dtlFile.elementAt(22);
					ctstco = "SMCD";
					if (!ctstky.trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(ctstco);
							parmClass.addElement(ctstky);
							requestType = "getCsytab";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getCsytab-#22). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try 
							{
								findCsytab = conn.prepareStatement(sqlString);
								rsCsytab = findCsytab.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsCsytab.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 23);
									dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 24);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCsytab-#22). " + e);
							}
							
							findCsytab.close();
							rsCsytab.close();
						}
					}
					
					//Sales District
					ctstky = (String) dtlFile.elementAt(25);
					ctstco = "SDST";
					if (!ctstky.trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(ctstco);
							parmClass.addElement(ctstky);
							requestType = "getCsytab";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getCsytab-#25). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try 
							{
								findCsytab = conn.prepareStatement(sqlString);
								rsCsytab = findCsytab.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsCsytab.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 26);
									dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 27);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCsytab-#25). " + e);
							}
							
							findCsytab.close();
							rsCsytab.close();
						}
					}
					
					//Market
					ctstky = (String) dtlFile.elementAt(28);
					ctstco = "CFC3";
					if (!ctstky.trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(ctstco);
							parmClass.addElement(ctstky);
							requestType = "getCsytab";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getCsytab-#28). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try 
							{
								findCsytab = conn.prepareStatement(sqlString);
								rsCsytab = findCsytab.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsCsytab.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 29);
									dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 30);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCsytab-#28). " + e);
							}
							
							findCsytab.close();
							rsCsytab.close();
						}
					}
					
					//Plan To
					ctstky = (String) dtlFile.elementAt(31);
					ctstco = "FRE1";
					if (!ctstky.trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(ctstco);
							parmClass.addElement(ctstky);
							requestType = "getCsytab";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getCsytab-#31). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try 
							{
								findCsytab = conn.prepareStatement(sqlString);
								rsCsytab = findCsytab.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsCsytab.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 32);
									dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 33);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCsytab-#31). " + e);
							}
							
							findCsytab.close();
							rsCsytab.close();
						}
					}
					
					//Freight District
					ctstky = (String) dtlFile.elementAt(34);
					ctstco = "FRE2";
					if (!ctstky.trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(ctstco);
							parmClass.addElement(ctstky);
							requestType = "getCsytab";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getCsytab-#34). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try 
							{
								findCsytab = conn.prepareStatement(sqlString);
								rsCsytab = findCsytab.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsCsytab.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 35);
									dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 36);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCsytab-#34). " + e);
							}
							
							findCsytab.close();
							rsCsytab.close();
						}
					}
					
					
					//get Item Master
					if (!rsOsbstd.getString("UCITNO").trim().equals(""))
					{
						try {
							Vector parmClass = new Vector();
							parmClass.addElement(rsOsbstd.getString("UCITNO").trim());
							requestType = "getItemMaster";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
								throwError.append("Error at build sql (getItemMaster). " + e);
						}
						
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								findMitmas = conn.prepareStatement(sqlString);
								rsMitmas = findMitmas.executeQuery();
									
								// for each record add a sales detail file entry.
								if (rsMitmas.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsMitmas.getString("MMITDS"), 46);
									dtlFile.setElementAt(rsMitmas.getString("MMFUDS"), 47);
									dtlFile.setElementAt(rsMitmas.getString("MMUNMS"), 48);
									dtlFile.setElementAt(rsMitmas.getString("MMACRF"), 49);
									dtlFile.setElementAt(rsMitmas.getString("MMITTY"), 52);
									dtlFile.setElementAt(rsMitmas.getString("MMITCL"), 55);
									dtlFile.setElementAt(rsMitmas.getString("MMITGR"), 58);
									dtlFile.setElementAt(rsMitmas.getString("MMCFI1"), 61);
									dtlFile.setElementAt(rsMitmas.getString("MMBUAR"), 139);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getItemMaster). " + e);
							}
								
							findMitmas.close();
							rsMitmas.close();
						}
					}
					
					
					//Accounting Control Object
					ctstky = (String) dtlFile.elementAt(49);
					ctstco = "ACRF";
					if (!ctstky.trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(ctstco);
							parmClass.addElement(ctstky);
							requestType = "getCsytab";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getCsytab-#49). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try 
							{
								findCsytab = conn.prepareStatement(sqlString);
								rsCsytab = findCsytab.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsCsytab.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 50);
									dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 51);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCsytab-#49). " + e);
							}
							
							findCsytab.close();
							rsCsytab.close();
						}
					}
					
					
					//Item Type
					ctstky = (String) dtlFile.elementAt(52);
					ctstco = "ITTY";
					if (!ctstky.trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(ctstco);
							parmClass.addElement(ctstky);
							requestType = "getCsytab";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getCsytab-#52). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try 
							{
								findCsytab = conn.prepareStatement(sqlString);
								rsCsytab = findCsytab.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsCsytab.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 53);
									dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 54);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCsytab-#52). " + e);
							}
							
							findCsytab.close();
							rsCsytab.close();
						}
					}
					
					
					//Product Group
					ctstky = (String) dtlFile.elementAt(55);
					ctstco = "ITCL";
					if (!ctstky.trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(ctstco);
							parmClass.addElement(ctstky);
							requestType = "getCsytab";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getCsytab-#55). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try 
							{
								findCsytab = conn.prepareStatement(sqlString);
								rsCsytab = findCsytab.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsCsytab.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 56);
									dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 57);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCsytab-#55). " + e);
							}
							
							findCsytab.close();
							rsCsytab.close();
						}
					}
					
					
					//Item Group
					ctstky = (String) dtlFile.elementAt(58);
					ctstco = "ITGR";
					if (!ctstky.trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(ctstco);
							parmClass.addElement(ctstky);
							requestType = "getCsytab";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getCsytab-#58). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try 
							{
								findCsytab = conn.prepareStatement(sqlString);
								rsCsytab = findCsytab.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsCsytab.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 59);
									dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 60);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCsytab-#58). " + e);
							}
							
							findCsytab.close();
							rsCsytab.close();
						}
					}
					
					
					//Size/Piece Size
					ctstky = (String) dtlFile.elementAt(61);
					ctstco = "CFI1";
					if (!ctstky.trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(ctstco);
							parmClass.addElement(ctstky);
							requestType = "getCsytab";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getCsytab-#61). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try 
							{
								findCsytab = conn.prepareStatement(sqlString);
								rsCsytab = findCsytab.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsCsytab.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 62);
									dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 63);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCsytab-#61). " + e);
							}
							
							findCsytab.close();
							rsCsytab.close();
						}
					}
					
					
					//Invoice Customer ShipTo.
					if (!rsOsbstd.getString("UCADID").trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(rsOsbstd.getString("UCCUNO").trim());
							parmClass.addElement(rsOsbstd.getString("UCADID").trim());
							requestType = "getCustomerShipTo";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch (Exception e) {
							throwError.append("Error at build sql (getCustomerShipTo). " + e);
						}
						
						try //read the ShipTo record.
						{
							findOcusad = conn.prepareStatement(sqlString);
							rsOcusad   = findOcusad.executeQuery();
							
							if (rsOcusad.next() && throwError.toString().equals(""))
							{
								dtlFile.setElementAt(rsOcusad.getString("OPCUNM"), 13);
								dtlFile.setElementAt(rsOcusad.getString("OPECAR"), 79);
								dtlFile.setElementAt(rsOcusad.getString("OPCSCD"), 82);
							}
						} catch (Exception e) {
							throwError.append("Error at execute sql (getCustomerShipTo). " + e);
							String stopHere = "x";
						}
						
						findOcusad.close();
						rsOcusad.close();
					}
					
					
					//Ship To State
					ctstky = (String) dtlFile.elementAt(79);
					ctstco = "ECAR";
					if (!ctstky.trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(ctstco);
							parmClass.addElement(ctstky);
							requestType = "getCsytab";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getCsytab-#79). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try 
							{
								findCsytab = conn.prepareStatement(sqlString);
								rsCsytab = findCsytab.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsCsytab.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 80);
									dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 81);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCsytab-#79). " + e);
							}
							
							findCsytab.close();
							rsCsytab.close();
						}
					}
					
					
					//Country
					ctstky = (String) dtlFile.elementAt(82);
					ctstco = "EDES";
					if (!ctstky.trim().equals(""))
					{
						try 
						{
							Vector parmClass = new Vector();
							parmClass.addElement(ctstco);
							parmClass.addElement(ctstky);
							requestType = "getCsytab";
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (getCsytab-#82). " + e);
						}
					
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try 
							{
								findCsytab = conn.prepareStatement(sqlString);
								rsCsytab = findCsytab.executeQuery();
								
								// for each record add a sales detail file entry.
								if (rsCsytab.next() && throwError.toString().equals(""))
								{			
									dtlFile.setElementAt(rsCsytab.getString("CTTX40"), 83);
									dtlFile.setElementAt(rsCsytab.getString("CTTX15"), 84);
								}
							} catch (Exception e) {
								throwError.append(" Error at execute the sql (getCsytab-#82). " + e);
							}
							
							findCsytab.close();
							rsCsytab.close();
						}
					}
					
					//Add a record to the file.
					try
					{
						requestType = "addZ100003Record";
						Vector parmClass = new Vector();
						parmClass.addElement(dtlFile);
						sqlString = buildSqlStatement(requestType, parmClass);
						addIt = conn.prepareStatement(sqlString);
						addIt.executeUpdate();
						addIt.close();
					} catch(Exception e) {
						throwError.append(" Error at execute the sql update Z100003. " + e);
						String stopHere = "x";
					}
				}
				
				//findOsbstd = conn.prepareStatement(sqlString);
				//rsOsbstd = findOsbstd.executeQuery();			
				

			} catch (Exception e) {
				throwError.append(" Error at execute the sql (salesOSBSTDInvoiceDateRange). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
	finally
	{
		try
		{
			if (findOsbstd != null)
				findOsbstd.close();
			if (findOagrln != null)
				findOagrln.close();
			if (findOcusma != null)
				findOcusma.close();
			if (findCsytab != null)
				findCsytab.close();
			if (findMitmas != null)
				findMitmas.close();
			if (findOcusad != null)
				findOcusad.close();
			if (findMitwhl != null)
				findMitwhl.close();
			if (findCfacil != null)
				findCfacil.close();
			if (addIt != null)
				addIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsOsbstd != null)
				rsOsbstd.close();
			if (rsOagrln != null)
				rsOagrln.close();
			if (rsOcusma != null)
				rsOcusma.close();
			if (rsCsytab != null)
				rsCsytab.close();
			if (rsMitmas != null)
				rsMitmas.close();
			if (rsOcusad != null)
				rsOcusad.close();
			if (rsMitwhl != null)
				rsMitwhl.close();
			if (rsCfacil != null)
				rsCfacil.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildSalesDetail(");
		throwError.append("Connection, ");
		throwError.append(fYear + ", ");
		throwError.append(fPeriod + " ");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * update sale amount (SLAMT) in Z100003 file records
 * where SLAMT is zero. 
 * 
 * @throws Exception
 */
public static void updateSalesDollars()
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack4.getConnection();
		
		// execute the update method
		conn = updateSalesDollars(conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try
		{
			if (conn != null)
				ConnectionStack4.returnConnection(conn);
		} catch(Exception e){
			throwError.append("Error closing connection. " + e);
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateSalesDollars(");
		throwError.append("). ");
	}
	
	return;
}

/**
 * Update Sales Dollars (SLAMT) in file Z100003
 * 
 * @param  Connection
 * @return Connection.
 * 
 * @throws Exception
 */
private static Connection updateSalesDollars(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsZ100003            = null;
	PreparedStatement findZ100003  = null;
	PreparedStatement updateIt     = null;
	String requestType = "";
	String sqlString = "";
	BigDecimal zero = new BigDecimal("0");
	BigDecimal neg1 = new BigDecimal("-1");
	
	try { //enable finally.
	
		//get the Detail File Z100003 only with SLAMT = 0 and 40100... <> 0
		try //get the records
		{
			Vector parmClass = new Vector();
			requestType = "getZ100003WithNoSalesDollars";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at sql build of (getZ100003WithNoSalesDollars). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findZ100003 = conn.prepareStatement(sqlString);
				rsZ100003   = findZ100003.executeQuery();
				
				// for each record update the Sales File.
				while (rsZ100003.next() && throwError.toString().equals(""))
				{
					BigDecimal amount = new BigDecimal("0");
					int fromDate = 20080427;
					int invDate  = rsZ100003.getInt("UTACDT");
							
					// use only 40100 if GL trans date prior to 20080427
					if (invDate < fromDate)
					{
						amount = rsZ100003.getBigDecimal("UTAC40100");
						amount = amount.multiply(neg1);
					} 
					// use multiple account values to determine sales
					else
					{
						if (rsZ100003.getString("OKFACI").equals("100"))
						{
							amount = rsZ100003.getBigDecimal("UTAC40100");
							amount = amount.add(rsZ100003.getBigDecimal("UTAC40600"));
							amount = amount.add(rsZ100003.getBigDecimal("UTAC40725"));
							amount = amount.add(rsZ100003.getBigDecimal("UTAC40735"));
							amount = amount.add(rsZ100003.getBigDecimal("UTAC40830"));
							amount = amount.multiply(neg1);
						}
						if (rsZ100003.getString("OKFACI").equals("125") &&
							rsZ100003.getString("OKCUCL").equals("103"))
						{
							amount = rsZ100003.getBigDecimal("UTAC40100");
							amount = amount.add(rsZ100003.getBigDecimal("UTAC40600"));
							amount = amount.add(rsZ100003.getBigDecimal("UTAC40725"));
							amount = amount.add(rsZ100003.getBigDecimal("UTAC40735"));
							amount = amount.add(rsZ100003.getBigDecimal("UTAC40830"));
							amount = amount.multiply(neg1);
						}
						if (rsZ100003.getString("OKFACI").equals("125") &&
							rsZ100003.getString("OKCUCL").equals("109"))
						{
							amount = rsZ100003.getBigDecimal("UTAC40100");
							amount = amount.add(rsZ100003.getBigDecimal("UTAC40600"));
							amount = amount.multiply(neg1);
							}
						if (rsZ100003.getString("OKFACI").equals("150"))
						{
							amount = rsZ100003.getBigDecimal("UTAC40100");
							amount = amount.add(rsZ100003.getBigDecimal("UTAC40400"));
							amount = amount.add(rsZ100003.getBigDecimal("UTAC40600"));
							amount = amount.multiply(neg1);
						}
					}
					
					//test to see if amount is <> zero.
					int zeroValue = amount.compareTo(zero);
											
					if (zeroValue != 0)
					{
						//update the record.
						try
						{
							if (throwError.toString().trim().equals(""))
							{
								requestType = "updateZ100003SalesDollars";
								Vector parmClass = new Vector();
								parmClass.addElement(rsZ100003);
								parmClass.addElement(amount);
								sqlString = buildSqlStatement(requestType, parmClass);
								updateIt = conn.prepareStatement(sqlString);
								updateIt.executeUpdate();
								updateIt.close();
							}
						} catch(Exception e) {
							throwError.append(" Error at execute the sql update Z100003. " + e);
						}
					}
				}	
				
				rsZ100003.close();
				findZ100003.close();
				
			} catch (Exception e) {
				throwError.append("Error at execute sql (getZ100003WithNoSalesDollars). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findZ100003 != null)
				findZ100003.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsZ100003 != null)
				rsZ100003.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateSalesDollars(");
		throwError.append("Connection).");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * Load detail file with invoice 
 * sales data from file OEPVX (Old TTI Customer Sales History).
 * 
 * @param 
 * @return 
 * @throws Exception
 */

// 02/05/09
// This method executed against an AS400 that now does not exist.
// In order for this method to execute the file OEPVX must be restored 
// to a viable AS400 and a connect set up for access.

public static void buildSalesDetailOepvx(String fYear, String fPeriod)
{
	StringBuffer throwError = new StringBuffer();
	//ConnectionPool connPool = null;// 02/05/09
	Connection connNew = null;
	//Connection connOld = null; // 02/05/09
	
	//get connections here and execute private update method
	try {
		// get a connection to be sent to find methods
		Vector connections = new Vector();
		connNew = getDBConnection();
		connections.addElement(connNew);
		
		//connPool = new ConnectionPool();// 02/05/09
		//connOld = connPool.getConnection();// 02/05/09
		//connections.addElement(connOld);// 02/05/09
		
		// execute the update method
		connections = buildSalesDetailOepvx(connections, fYear, fPeriod);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (connNew != null)
		{
			try
			{
			   connNew.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
		//if (connOld != null)// 02/05/09
		//{// 02/05/09
			//try// 02/05/09
			//{// 02/05/09
				//connPool.returnConnection(connOld);// 02/05/09
			//} catch (Exception el) {// 02/05/09
				//el.printStackTrace();// 02/05/09
			//}// 02/05/09
		//}// 02/05/09
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildSalesDetailOepvx(");
		throwError.append("). ");
	}
	
	return;
}

/**
 * Update/Add Sales Detail File with OEPVX Dollars. 
 * @param conn, fiscal year, fiscal period.
 * @return Connection.
 */
private static Vector buildSalesDetailOepvx(Vector conns, String fYear, String fPeriod)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsOepvx  = null;
    ResultSet rsZ100003 = null;
	PreparedStatement findOepvx  = null;
	PreparedStatement findZ100003 = null;
	PreparedStatement addIt       = null;
	PreparedStatement updateIt    = null;
	String requestType = "";
	String sqlString = "";
	Connection connNew = (Connection) conns.elementAt(0);
	Connection connOld = (Connection) conns.elementAt(1);
	
	// put these in a business object.
	String fromDate = "";
	String toDate   = "";
	
	try { //enable finally.
		
		//get the invoicing dates.
		try//build sql for from and to dates.
		{
			Vector parmClass = new Vector();
			parmClass.addElement(fYear);
			parmClass.addElement(fPeriod);
			requestType = "getFromToDates";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (getFromToDates). " + e);
		}
		
		//execute sql.
		if (throwError.toString().equals(""))
		{
			try {
				PreparedStatement findDates = connNew.prepareStatement(sqlString);
				ResultSet rs = findDates.executeQuery();
				
				if (rs.next())
				{
					fromDate = rs.getString("CPFDAT");
					toDate   = rs.getString("CPTDAT");
				}
			} catch (Exception e) {
				throwError.append("Error at execute sql (getFromToDates). " + e);
			}
		}
		
	
		//get the sales OEPVX.
		try //build sql of sales history.
		{
			Vector parmClass = new Vector();
			parmClass.addElement(fYear);
			parmClass.addElement(fPeriod);
			requestType = "salesOEPVXInvoiceDateRangeAll";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (salesOEPVXInvoiceDateRangeAll). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findOepvx = connOld.prepareStatement(sqlString);
				rsOepvx = findOepvx.executeQuery();
				
				// for each record update/add Sales by File.
				while (rsOepvx.next() && throwError.toString().equals(""))
				{
					//extract the invoice number.
					String invoiceNo = rsOepvx.getString("VXINV#");
					
					//extract the line number.
					String lineNo    = rsOepvx.getString("VXSEQ#");
					
					//determine the delivery number.
					String delvNo = "1";
					
					if (lineNo.equals("0"))
						delvNo    = "0";
					
					
					
					try //sql to see if the record exists.
					{
						Vector parmClass = new Vector();
						parmClass.addElement(invoiceNo);
						parmClass.addElement(lineNo);
						parmClass.addElement(delvNo); //delvNo always 1 here.
						requestType = "getInvoiceLineRecord";
						sqlString = buildSqlStatement(requestType, parmClass);
					} catch (Exception e) {
						throwError.append("Error at build sql (getInvoiceLineRecord). " + e);
					}
					
					try //get the Sales Detail file record if it exists.
					{
						findZ100003 = connNew.prepareStatement(sqlString);
						rsZ100003   = findZ100003.executeQuery();
						
						if (!rsZ100003.next() && throwError.toString().equals(""))
						{
							// fix the date from mm/dd/yy to yyyymmdd
							String inDate = rsOepvx.getString("VXIDTF");
							if (inDate.length() == 7)
								inDate = "0" + inDate;
							String outDate = inDate.substring(4,8);
							outDate = outDate + inDate.substring(0,2);
							outDate = outDate + inDate.substring(2,4);
							
							requestType = "addSalesDetailOinacc";
							Vector parmClass = new Vector();
							parmClass.addElement(invoiceNo);
							parmClass.addElement(lineNo);
							parmClass.addElement(rsOepvx.getString("VXORD#"));
							parmClass.addElement(outDate);
							parmClass.addElement(outDate);
							parmClass.addElement(delvNo);
							
							sqlString = buildSqlStatement(requestType, parmClass);
							addIt = connNew.prepareStatement(sqlString);
							addIt.executeUpdate();
							addIt.close();
							parmClass = new Vector();
							parmClass.addElement(invoiceNo);
							parmClass.addElement(lineNo);
							parmClass.addElement("0");
							requestType = "getInvoiceLineRecord";
							sqlString = buildSqlStatement(requestType, parmClass);
							findZ100003 = connNew.prepareStatement(sqlString);
							rsZ100003   = findZ100003.executeQuery();
							rsZ100003.next();
						}
						
						
						// determine the fields to be modified.
						String ait1 = "";
						if (rsOepvx.getString("X1DIM1") != null)
							ait1 = rsOepvx.getString("X1DIM1").trim();
						
						BigDecimal curr1  = new BigDecimal("0");
						BigDecimal curr2  = new BigDecimal("0");
						String fieldName1 = "";
						String fieldName2 = "";
						
						
						
						if (ait1.equals("11100") || ait1.equals("24100") || ait1.equals("24200") ||
							ait1.equals("40100") || ait1.equals("40300") || ait1.equals("40400") ||
							ait1.equals("40500") || ait1.equals("40600") || ait1.equals("40700") ||
							ait1.equals("40810") || ait1.equals("40830") || ait1.equals("58120") ||
							ait1.equals("58130") || ait1.equals("58150") || ait1.equals("58330") ||
							ait1.equals("58410") || ait1.equals("58420") || ait1.equals("58430") ||
							ait1.equals("60120") || ait1.equals("60125") || ait1.equals("60135") ||
							ait1.equals("60610") || ait1.equals("60660") || ait1.equals("61610") ||
							ait1.equals("61630") || ait1.equals("64276") || ait1.equals("64530") ||
							ait1.equals("64610") || ait1.equals("65540") || ait1.equals("65670") ||
							ait1.equals("65750") || ait1.equals("66950") || ait1.equals("67100") ||
							ait1.equals("99999") || ait1.equals("47125") || ait1.equals("47135") )									
						{
							fieldName1 = "UTAC" + ait1;
							curr1 = rsZ100003.getBigDecimal(fieldName1);
						}
							
							
						if (fieldName1.trim().equals(""))
						{
							fieldName1 = "UTACOTHER";
							curr1 = rsZ100003.getBigDecimal("UTACOTHER");
						}
						
						//determine the class for the account.
						if (ait1.equals("11100"))
							fieldName2 = "UTCL110";
						if (ait1.equals("24100") || ait1.equals("24200"))
							fieldName2 = "UTCL230";
						if (ait1.equals("40100") || ait1.equals("40300") || ait1.equals("40400") ||
							ait1.equals("40500") || ait1.equals("40600") || ait1.equals("40700") ||	
							ait1.equals("40810") || ait1.equals("40830") || ait1.equals("47125") ||
							ait1.equals("47135"))
							fieldName2 = "UTCL400";
						if (ait1.equals("58120") || ait1.equals("58130") || ait1.equals("58150") ||
							ait1.equals("58330") || ait1.equals("58410") || ait1.equals("58420") ||	
							ait1.equals("58430") )
							fieldName2 = "UTCL530";
						if (ait1.equals("60120") || ait1.equals("60125"))
							fieldName2 = "UTCL410";
						if (ait1.equals("60135") || ait1.equals("60610") || ait1.equals("60660"))
							fieldName2 = "UTCL420";
						if (ait1.equals("61610") || ait1.equals("61630"))
							fieldName2 = "UTCL430";
						if (ait1.equals("65540") || ait1.equals("65670") || ait1.equals("65750"))
							fieldName2 = "UTCL600";
						if (fieldName2.equals(""))
							fieldName2 = "UTCLOTHER";
							
						curr2 = rsZ100003.getBigDecimal(fieldName2);
						
						BigDecimal toAdd = rsOepvx.getBigDecimal("VXHAMT");
						curr1 = curr1.add(toAdd);
						curr2 = curr2.add(toAdd);
						
						curr1.setScale(2);
						curr2.setScale(2);
						
						requestType = "updateSalesDetailOepvx";
						Vector parmClass = new Vector();
						parmClass.addElement(invoiceNo);
						parmClass.addElement(lineNo);
						parmClass.addElement(delvNo);
						parmClass.addElement(fieldName1);
						parmClass.addElement(curr1);
						parmClass.addElement(fieldName2);
						parmClass.addElement(curr2);
						sqlString = buildSqlStatement(requestType, parmClass);
						updateIt = connNew.prepareStatement(sqlString);
						updateIt.executeUpdate();
						
						updateIt.close();
						findZ100003.close();
						rsZ100003.close();
					} catch (Exception e) {
						throwError.append("Error at execute sql update/add SalesDetailOepvx). " + e);
					}
				}

			} catch (Exception e) {
				throwError.append(" Error at execute the sql (salesOEPVXInvoiceDateRangeAll). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findOepvx != null)
				findOepvx.close();
			if (findZ100003 != null)
				findZ100003.close();
			if (addIt != null)
				addIt.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsOepvx != null)
				rsOepvx.close();
			if (rsZ100003 != null)
				rsZ100003.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildSalesOinacc(");
		throwError.append("Connection, ");
		throwError.append(fYear + ", ");
		throwError.append(fPeriod + " ");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}

  return conns;
}

/**
 * update OSBSTD records with religned
 * Customer Master criteria.
 * 
 * @throws Exception
 */
public static void updateOsbstdAssociatedCustomerMasterValues()
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = updateOsbstdAssociatedCustomerMasterValues(conn);
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
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateOsbstdAssociatedCustomerMasterValues(");
		throwError.append("). ");
	}
	
	return;
}

/**
 * update OSBSTD records with religned
 * Customer Master criteria.
 * 
 * @param  Connection
 * @return Connection.
 * 
 * @throws Exception
 */
private static Connection updateOsbstdAssociatedCustomerMasterValues(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsOSBSTD             = null;
    ResultSet rsOCUSMA			   = null;
	PreparedStatement findOSBSTD   = null;
	PreparedStatement findOCUSMA   = null;
	PreparedStatement updateIt     = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
	
		//get OSBSTD records by Customer by Order
		try //get the records
		{
			Vector parmClass = new Vector();
			requestType = "getOsbstdByCustomer";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at sql build of (getOsbstdByCustomer). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findOSBSTD   = conn.prepareStatement(sqlString);
				rsOSBSTD     = findOSBSTD.executeQuery();
				String cuno  = "x";
				String cucl  = "x";
				String smcd  = "x";
				String found = "x";
				
				// for each record update the OSBSTD File if needed.
				while (rsOSBSTD.next() && throwError.toString().equals(""))
				{
					if (!rsOSBSTD.getString("UCCUNO").trim().equals(cuno.trim()))
					{
						cuno 				= rsOSBSTD.getString("UCCUNO").trim();
						Vector parmClass 	= new Vector();
						requestType 		= "getCustomerMasterByCustomer";
						parmClass.addElement(cuno);
						sqlString 			= buildSqlStatement(requestType, parmClass);
						findOCUSMA 			= conn.prepareStatement(sqlString);
						rsOCUSMA   			= findOCUSMA.executeQuery();
						found				= "no";
						
						if (rsOCUSMA.next())
						{
							cucl = rsOCUSMA.getString("OKCUCL").trim();
							smcd = rsOCUSMA.getString("OKSMCD").trim();
							found = "yes";
						}
						
						findOCUSMA.close();
						rsOCUSMA.close();


						// only update the record if the Customer master was found.
						if (found.equals("yes"))
						{
							requestType = "updateOsbstdCustomerData";
							parmClass = new Vector();
							parmClass.addElement(cuno);
							parmClass.addElement(cucl);
							parmClass.addElement(smcd);
							sqlString 	= buildSqlStatement(requestType, parmClass);
							updateIt = conn.prepareStatement(sqlString);
							updateIt.executeUpdate();
							updateIt.close();
						}
					}
				}	
				
				rsOSBSTD.close();
				findOSBSTD.close();
				
			} catch (Exception e) {
				throwError.append("Error at execute sql " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findOSBSTD != null)
				findOSBSTD.close();
			if (findOCUSMA != null)
				findOCUSMA.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsOSBSTD != null)
				rsOSBSTD.close();
			if (rsOCUSMA != null)
				rsOCUSMA.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateOsbstdAssociatedCustomerMasterValues(");
		throwError.append("Connection).");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * update OSBSTD records with religned
 * Item Master criteria.
 * 
 * @throws Exception
 */
public static void updateOsbstdAssociatedItemMasterValues()
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = updateOsbstdAssociatedItemMasterValues(conn);
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
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateOsbstdAssociatedItemMasterValues(");
		throwError.append("). ");
	}
	
	return;
}

/**
 * update OSBSTD records with religned
 * Item Master criteria.
 * 
 * @param  Connection
 * @return Connection.
 * 
 * @throws Exception
 */
private static Connection updateOsbstdAssociatedItemMasterValues(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsOSBSTD             = null;
    ResultSet rsMITMAS			   = null;
	PreparedStatement findOSBSTD   = null;
	PreparedStatement findMITMAS   = null;
	PreparedStatement updateIt     = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
	
		//get OSBSTD records by Item by Order.
		try //get the records
		{
			Vector parmClass = new Vector();
			requestType = "getOsbstdByItem";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at sql build of (getOsbstdByItem). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findOSBSTD   = conn.prepareStatement(sqlString);
				rsOSBSTD     = findOSBSTD.executeQuery();
				String itno  = "x";
				String itcl  = "x";
				String found = "x";
				
				// for each record update the OSBSTD File if needed.
				while (rsOSBSTD.next() && throwError.toString().equals(""))
				{
					if (!rsOSBSTD.getString("UCITNO").trim().equals(itno.trim()))
					{
						itno 				= rsOSBSTD.getString("UCITNO").trim();
						Vector parmClass 	= new Vector();
						requestType 		= "getItemMaster";
						parmClass.addElement(itno);
						sqlString 			= buildSqlStatement(requestType, parmClass);
						findMITMAS 			= conn.prepareStatement(sqlString);
						rsMITMAS   			= findMITMAS.executeQuery();
						found				= "no";
						
						if (rsMITMAS.next())
						{
							itcl = rsMITMAS.getString("MMITCL").trim();
							found = "yes";
						}
						
						findMITMAS.close();
						rsMITMAS.close();


						// only update the record if the Item Master was found.
						if (found.equals("yes"))
						{
							requestType = "updateOsbstdItemData";
							parmClass = new Vector();
							parmClass.addElement(itno);
							parmClass.addElement(itcl);
							sqlString 	= buildSqlStatement(requestType, parmClass);
							updateIt = conn.prepareStatement(sqlString);
							updateIt.executeUpdate();
							updateIt.close();
						}
						
					}
				}	
				
				rsOSBSTD.close();
				findOSBSTD.close();
				
			} catch (Exception e) {
				throwError.append("Error at execute sql " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findOSBSTD != null)
				findOSBSTD.close();
			if (findMITMAS != null)
				findMITMAS.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsOSBSTD != null)
				rsOSBSTD.close();
			if (rsMITMAS != null)
				rsMITMAS.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateOsbstdAssociatedItemMasterValues(");
		throwError.append("Connection).");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * re-align DMP dataset records
 * @param file name
 * @param fiscal year
 * @param fiscal period
 * @param from budget type
 * @param to budget type
 * @param from budget version
 * @param to budget version
 * @throws Exception
 */
public static void updateBudgetDataSet(String fileName,
									   String year,
									   String period,
									   String fromType,
									   String toType,
									   String fromVersion,
									   String toVersion)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = updateBudgetDataSet(fileName, year, period, fromType, toType, fromVersion, toVersion, conn);
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
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateBudgetDataSet(");
		throwError.append("). ");
	}
	
	return;
}

/**
 * update budget records type 34
 * Use budget file data in library BUDGTEMP
 * and update files in M3DJDPRD. This realigns
 * customer and item from last budget/forecast
 * to new(current) budget/forecast.
 * 
 * @param  []file access key data
 * @param  Connection
 * 
 * @return Connection.
 * 
 * @throws Exception
 */
private static Connection updateBudgetDataSet(String fileName, 
											  String year,
											  String period,
											  String fromType,
											  String toType,
											  String fromVersion,
											  String toVersion,
											  Connection conn)						
	throws Exception 
{
	
	StringBuffer throwError = new StringBuffer();
    ResultSet  rsOldFile          = null;
    ResultSet  rsNewFile		  = null;
	PreparedStatement findOldFile = null;
	PreparedStatement findNewFile = null;
	PreparedStatement updateIt    = null;
	PreparedStatement addIt		  = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
	
		//get old data set file in library BUDGTEMP
		//(outer join Customer and Item Masters)
		try //get the records
		{
			Vector parmClass = new Vector();
			parmClass.addElement(fileName);
			parmClass.addElement(year);
			parmClass.addElement(period);
			parmClass.addElement(fromType);
			parmClass.addElement(fromVersion);
			requestType = "getOldDataSetFile";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at sql build of (getOldDataSetFile). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findOldFile = conn.prepareStatement(sqlString);
				rsOldFile   = findOldFile.executeQuery();
				
				// obtain modifiable values for data set file fields.
				while (rsOldFile.next() && throwError.toString().equals(""))
				{
					//load variable fields.
					
					String smcd = "";
					if (rsOldFile.getString("cOKSMCD") == null)
						smcd = rsOldFile.getString("aUCSMCD");
					else
						smcd = rsOldFile.getString("cOKSMCD");
					
					String cfc3 = "";
					if (rsOldFile.getString("cOKCFC3") == null)
						cfc3 = rsOldFile.getString("aOKCFC3");
					else
						cfc3 = rsOldFile.getString("cOKCFC3");
					
					String itcl = "";
					if (rsOldFile.getString("bMMITCL") == null)
						itcl = rsOldFile.getString("aUCITCL");
					else
						itcl = rsOldFile.getString("bMMITCL");
					
					//not used in all data sets.
					String cfi1 = "";
					String cucl = "";
					
					if (fileName.equals("o100012") ||
						fileName.equals("o100013") ||
						fileName.equals("o100014") ||
						fileName.equals("o100015") ||
						fileName.equals("o100017") ||
						fileName.equals("o100018") ||
						fileName.equals("o100019") ||
						fileName.equals("o100020") ||
						fileName.equals("o100025") )
					{
						if (rsOldFile.getString("bMMCFI1") == null)
							cfi1 = rsOldFile.getString("aMMCFI1");
						else
							cfi1 = rsOldFile.getString("bMMCFI1");		
					} else 
					{
						if (rsOldFile.getString("cOKCUCL") == null)
							cucl = rsOldFile.getString("aUCCUCL");
						else
							cucl = rsOldFile.getString("cOKCUCL");
					}
					
					//see if entry exists with modified keys from old file.
					// If the entry exists add amounts to new file, 
					// else write a new entry in the new file.
					
					try //build the sql statement
					{
						Vector parmClass = new Vector();
						parmClass.addElement(fileName);
						parmClass.addElement(rsOldFile);
						parmClass.addElement(cucl);
						parmClass.addElement(smcd);
						parmClass.addElement(cfc3);
						parmClass.addElement(itcl);
						parmClass.addElement(cfi1);
						parmClass.addElement(toType);
						parmClass.addElement(toVersion);
						requestType = "getNewDataSetRecord";
						sqlString = buildSqlStatement(requestType, parmClass);
					} catch(Exception e) {
						throwError.append("Error at sql build of (getNewDataSetRecord). " + e);
					}
					
					try //get the New file record (if it exists)
					{
						findNewFile = conn.prepareStatement(sqlString);
						rsNewFile   = findNewFile.executeQuery();
					} catch(Exception e) {
						throwError.append("Error at sql execute of (getNewDataSetRecord). " + e);
					}
					
					try //add or update the the New Data Set file with Old Data Set criteria.
					{
						//see if there is a record to update.
						if (rsNewFile.next())
						{
							BigDecimal dia1    = new BigDecimal(rsOldFile.getString("aUCDIA1"));
							BigDecimal madj    = new BigDecimal(rsOldFile.getString("aMFMADJ"));
							BigDecimal oldMfor = new BigDecimal(rsOldFile.getString("aMFMFOR"));
							BigDecimal newMfor = new BigDecimal(rsNewFile.getString("MFMFOR"));
							BigDecimal mfor    = oldMfor.add(newMfor);
							BigDecimal oldIvqt = new BigDecimal(rsOldFile.getString("aUCIVQT"));
							BigDecimal newIvqt = new BigDecimal(rsNewFile.getString("UCIVQT"));
							BigDecimal ivqt    = oldIvqt.add(newIvqt);
							BigDecimal oldSaam = new BigDecimal(rsOldFile.getString("aUCSAAM"));
							BigDecimal newSaam = new BigDecimal(rsNewFile.getString("UCSAAM"));
							BigDecimal saam    = oldSaam.add(newSaam);
							
							//update the record.
							try
							{
								if (throwError.toString().trim().equals(""))
								{
									requestType = "updateDataSetBudgetRecord";
									Vector parmClass = new Vector();
									parmClass.addElement(fileName);
									parmClass.addElement(rsNewFile);
									parmClass.addElement(dia1);
									parmClass.addElement(madj);
									parmClass.addElement(mfor);
									parmClass.addElement(ivqt);
									parmClass.addElement(saam);
									sqlString = buildSqlStatement(requestType, parmClass);
									updateIt = conn.prepareStatement(sqlString);
									updateIt.executeUpdate();
									updateIt.close();
								}
							} catch(Exception e) {
								throwError.append(" Error at updateDataSetBudgetRecord. " + e);
							}
						} else
						{

							BigDecimal dia1    = new BigDecimal(rsOldFile.getString("aUCDIA1"));
							BigDecimal madj    = new BigDecimal(rsOldFile.getString("aMFMADJ"));
							BigDecimal mfor    = new BigDecimal(rsOldFile.getString("aMFMFOR"));
							BigDecimal ucos    = new BigDecimal(rsOldFile.getString("aUCUCOS"));

							//add the record.
							try
							{
								if (throwError.toString().trim().equals(""))
								{
									requestType = "addDataSetBudgetRecord";
									Vector parmClass = new Vector();
									parmClass.addElement(fileName);
									parmClass.addElement(rsOldFile);
									parmClass.addElement(dia1);
									parmClass.addElement(madj);
									parmClass.addElement(mfor);
									parmClass.addElement(ucos);
									parmClass.addElement(cucl);
									parmClass.addElement(smcd);
									parmClass.addElement(cfc3);
									parmClass.addElement(itcl);
									parmClass.addElement(cfi1);
									parmClass.addElement(toType);
									parmClass.addElement(toVersion);
									sqlString = buildSqlStatement(requestType, parmClass);
									addIt = conn.prepareStatement(sqlString);
									addIt.executeUpdate();
									addIt.close();
								}
							} catch(Exception e) {
								throwError.append(" Error at execute the sql (addDataSetBudgetRecord). " + e);
							}
						}
						
						rsNewFile.close();
						findNewFile.close();
						
					} catch(Exception e) {
						throwError.append(" Error at addDataSetBudgetRecord. " + e);
					}
				}	
				
				rsOldFile.close();
				findOldFile.close();
				
			} catch (Exception e) {
				throwError.append("Error at execute sql (getOldDataSetFile). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findOldFile != null)
				findOldFile.close();
			if (findNewFile != null)
				findNewFile.close();
			if (updateIt != null)
				updateIt.close();
			if (addIt != null)
				addIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsOldFile != null)
				rsOldFile.close();
			if (rsNewFile != null)
				rsNewFile.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateBudgetDataSet(");
		throwError.append("Connection).");
		throw new Exception(throwError.toString());
	}
  return conn;
}

/**
 * Build actual sales into an existing 
 * Data Set file from the Z Sales File.
 * October 31 2008.
 * 
 * @param String Start fiscal year
 * @param String Start fiscal period
 * @param String End fiscal year
 * @param String End fiscal period
 * @return 
 * @throws Exception
 */
public static void buildDataSetSalesFromZFile(String fileName, 
											  String sYear, String sPeriod,
											  String eYear, String ePeriod)
{
	StringBuffer throwError = new StringBuffer();
	Connection connA = null;
	Connection connB = null;
	
	//get connection here and execute private update method
	try {
		// get connections to be sent to find methods
		connA = getDBConnection();
		connB = getDBConnection();
		
		// execute the update method
		Vector vector = new Vector();
		vector.addElement(connA);
		vector.addElement(connB);
		vector.addElement(fileName);
		vector.addElement(sYear);
		vector.addElement(sPeriod);
		vector.addElement(eYear);
		vector.addElement(ePeriod);
		vector = buildDataSetSalesFromZFile(vector);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (connA != null)
		{
			try
			{
			   connA.close();
			} catch(Exception el){
				throwError.append("Error close connA." + el );
			}
		}
		if (connB != null)
		{
			try
			{
			   connB.close();
			} catch(Exception el){
				throwError.append("Error close connB." + el );
			}
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildDataSetSalesFromZFile(");
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
private static Vector buildDataSetSalesFromZFile(Vector vector)						
	throws Exception 
{
	Connection connA = (Connection) vector.elementAt(0);
	Connection connB = (Connection) vector.elementAt(1);
	String fileName  = (String) vector.elementAt(2);
	String sYear     = (String) vector.elementAt(3);
	String sPeriod   = (String) vector.elementAt(4);
	String eYear     = (String) vector.elementAt(5);
	String ePeriod   = (String) vector.elementAt(6);
	
	StringBuffer throwError = new StringBuffer();
    ResultSet rsZfile  = null;
    ResultSet rsDS     = null;
    ResultSet rsOcusma = null;
    ResultSet rsMitmas = null;
    
	Statement findZfile  = null;
	Statement findDS     = null;
	Statement addIt      = null;
	Statement updateIt   = null;
	Statement findOcusma = null;
	Statement findMitmas = null;
	String requestType = "";
	String sqlString = "";
		
	try { //enable finally.
		

		//get Z file data.
		try //build sql.
			{
			requestType = "getZfileSalesForDataSet";
			Vector parmClass = new Vector();
			parmClass.addElement(fileName);
			parmClass.addElement(sYear);
			parmClass.addElement(sPeriod);
			parmClass.addElement(eYear);
			parmClass.addElement(ePeriod);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (getZfileSalesForDataSet). " + e);
		}
			
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {
				findZfile = connA.createStatement();
				rsZfile   = findZfile.executeQuery(sqlString);
					
				// for each record add or update a data set detail file entry.
				while (rsZfile.next() && throwError.toString().equals(""))
				{
					//get the record in the data set if it exists
					//build the sql.
					
					String inv = rsZfile.getString("UCIVNO");
					
					try {
						requestType = "getDataSet32Record";
						Vector parmClass = new Vector();
						parmClass.addElement(fileName);
						parmClass.addElement(rsZfile);
						sqlString = buildSqlStatement(requestType, parmClass);
					} catch (Exception e) {
						throwError.append("Error at build sql (getDataSet32Record). " + e);
					}
					
					//execute sql.
					if (throwError.toString().equals(""))
					{
						try {
							findDS   = connB.createStatement();
							rsDS     = findDS.executeQuery(sqlString);
							
							//update existing record.
							if (rsDS.next() && throwError.toString().equals(""))
							{
								//the amt field will be used here fro accumulation into ivqt and saam.
								BigDecimal amt  = new BigDecimal("0");
								BigDecimal zero = new BigDecimal("0");
								
								BigDecimal ivqt = new BigDecimal(rsDS.getString("UCIVQT"));
								
								
								//Only load UCIVQT for Food Service (No alternate uom wanted for budget/forcast).
								if (!fileName.equals("o100006"))
								{
									amt = new BigDecimal(rsZfile.getString("UCIVQA"));
								} else {
									amt = new BigDecimal(rsZfile.getString("UCIVQT"));
								}
								ivqt = ivqt.add(amt);
								
								//BigDecimal ivqa = new BigDecimal(rsDS.getString("MFCFOR"));
								//amt             = new BigDecimal(rsZfile.getString("UCIVQA"));
								//ivqa = ivqa.add(amt);
								
								BigDecimal saam = new BigDecimal(rsDS.getString("UCSAAM"));
								amt             = new BigDecimal(rsZfile.getString("SLAMT"));
								saam = saam.add(amt);
								
								BigDecimal madj = new BigDecimal("0");
								madj.setScale(6);
							    int x = ivqt.compareTo(zero);
							      
							    if (x != 0)
							    {
							    	madj = saam.divide(ivqt, 6, 4);
							    	madj = madj.setScale(6,4);
							    }
							    
							    //build the sql
							    try {
							    	requestType = "updateDataSet32FromZfile";
							    	Vector parmClass = new Vector();
							    	parmClass.addElement(fileName);
							    	parmClass.addElement(rsDS);
							    	parmClass.addElement(ivqt);
							    	parmClass.addElement(saam);
							    	parmClass.addElement(madj);
							    	//parmClass.addElement(ivqa);
							    	sqlString = buildSqlStatement(requestType, parmClass);
							    } catch (Exception e) {
							    	throwError.append("Error at build sql (updateDataSet32FromZfile). " + e);
							    }
							    
							    if (throwError.toString().equals(""))
							    {
							    	try {
							    		updateIt = connA.createStatement();
										updateIt.executeUpdate(sqlString);
										updateIt.close();
							    	} catch (Exception e) {
							    		throwError.append("Error at execute sql (updateDataSet32FromZfile). " + e);
							    	}
							    }
							      
							} else //addIt
							{
								try { //build sql
									BigDecimal zero = new BigDecimal("0");
									BigDecimal ivqt = new BigDecimal("0");
									
									//Only load UCIVQT for Food Service (No alternate uom wanted for budget/forcast).
									if (!fileName.equals("o100006"))
									{
										ivqt = new BigDecimal(rsZfile.getString("UCIVQA"));
									} else {
										ivqt = new BigDecimal(rsZfile.getString("UCIVQT"));
									}
									
									
									BigDecimal saam = new BigDecimal(rsZfile.getString("SLAMT"));
									BigDecimal madj = new BigDecimal("0");
									madj.setScale(6);
								    int x = ivqt.compareTo(zero);
								      
								    if (x != 0)
								    {
								    	madj = saam.divide(ivqt, 6, 4);
								    	madj = madj.setScale(6,4);
								    }
								    
								    
								    //** Get Customer and Item Master data for record add.
								    String cfc3 = "";
								    String smcd = "";
								    String cucl = "";
								    
									try {
										Vector parmClass = new Vector();
										parmClass.addElement(rsZfile.getString("UCCUNO").trim());
										requestType = "getCustomerMasterByCustomer";
										sqlString = buildSqlStatement(requestType, parmClass);
									} catch(Exception e) {
											throwError.append("Error at build sql (getCustomerMasterByCustomer). " + e);
									}
									
									// execute sql.
									if (throwError.toString().equals(""))
									{
										try {
											findOcusma = connA.createStatement();
											rsOcusma = findOcusma.executeQuery(sqlString);
												
											// for each record add a sales detail file entry.
											if (rsOcusma.next() && throwError.toString().equals(""))
											{			
												cfc3 = rsOcusma.getString("OKCFC3").trim();
												smcd = rsOcusma.getString("OKSMCD").trim();
												cucl = rsOcusma.getString("OKCUCL").trim();
											} else
											{
												cfc3 = rsZfile.getString("OKCFC3").trim();
												smcd = rsZfile.getString("OKSMCD").trim();
												cucl = rsZfile.getString("OKCUCL").trim();
											}
										} catch (Exception e) {
											throwError.append(" Error at execute the sql (getCustomerMasterByCustomer). " + e);
										}
											
										findOcusma.close();
										rsOcusma.close();
									}
								    
									String cfi1 = "";
									String itcl = "";
									
									try {
										Vector parmClass = new Vector();
										parmClass.addElement(rsZfile.getString("UCITNO").trim());
										requestType = "getItemMaster";
										sqlString = buildSqlStatement(requestType, parmClass);
									} catch(Exception e) {
											throwError.append("Error at build sql (getItemMaster). " + e);
									}
									
									// execute sql.
									if (throwError.toString().equals(""))
									{
										try {
											findMitmas = connA.createStatement();
											rsMitmas = findMitmas.executeQuery(sqlString);
												
											// for each record add a sales detail file entry.
											if (rsMitmas.next() && throwError.toString().equals(""))
											{			
												cfi1 = rsMitmas.getString("MMCFI1").trim();
												itcl = rsMitmas.getString("MMITCL").trim();
											}
										} catch (Exception e) {
											throwError.append(" Error at execute the sql (getItemMaster). " + e);
										}
											
										findMitmas.close();
										rsMitmas.close();
									}
								    
								    
								    
									requestType = "addDataSet32FromZfile";
									Vector parmClass = new Vector();
									parmClass.addElement(fileName);
									parmClass.addElement(rsZfile);
									parmClass.addElement(madj);
									parmClass.addElement(ivqt);
									parmClass.addElement(cfi1);
									parmClass.addElement(itcl);
									parmClass.addElement(cfc3);
									parmClass.addElement(smcd);
									parmClass.addElement(cucl);
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch (Exception e) {
									throwError.append("Error ar build sql (addDataSet32FromZfile)");
								}
								
								if (throwError.toString().equals(""))
								{
									try {
										addIt = connB.createStatement();
										addIt.executeUpdate(sqlString);
										addIt.close();
									} catch (Exception e) {
										throwError.append("Error at execute sql (addDataSet32FromZfile");
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
						
				findZfile.close();
				rsZfile.close();
			} catch(Exception e) {
				throwError.append("Error at execute sql (getZfileSalesForDataSet). " + e);
			}
		}
					
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
		
				
	finally
	{
		try
		{
			if (findZfile != null)
				findZfile.close();
			if (findDS != null)
				findDS.close();
			if (addIt != null)
				addIt.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsZfile != null)
				rsZfile.close();
			if (rsDS != null)
				rsDS.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildDataSetSalesFromZFile(");
		throwError.append("Vector");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}
	
	return vector;

}

/**
 * update Z100003 records with religned
 * Customer Master criteria.
 * 
 * @throws Exception
 */
public static void updateZ100003AssociatedCustomerMasterValues()
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = updateZ100003AssociatedCustomerMasterValues(conn);
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
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateZ100003AssociatedCustomerMasterValues(");
		throwError.append("). ");
	}
	
	return;
}

/**
 * update Z100003 records with religned
 * Item Master criteria.
 * 
 * @param  Connection
 * @return Connection.
 * 
 * @throws Exception
 */
private static Connection updateZ100003AssociatedItemMasterValues(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsZ100003            = null;
    ResultSet rsMITMAS			   = null;
	PreparedStatement findZ100003  = null;
	PreparedStatement findMITMAS   = null;
	PreparedStatement updateIt     = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
	
		//get Z100003 records by Item by Order.
		try //get the records
		{
			Vector parmClass = new Vector();
			requestType = "getZ100003ByItem";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at sql build of (getZ100003ByItem). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findZ100003  = conn.prepareStatement(sqlString);
				rsZ100003    = findZ100003.executeQuery();
				String itno  = "x";
				String itcl  = "x";
				String cfi1  = "x";
				String found = "x";
				
				// for each item update the Z100003 File if needed.
				while (rsZ100003.next() && throwError.toString().equals(""))
				{
					if (!rsZ100003.getString("UCITNO").trim().equals(itno.trim()))
					{
						itno 				= rsZ100003.getString("UCITNO").trim();
						Vector parmClass 	= new Vector();
						requestType 		= "getItemMaster";
						parmClass.addElement(itno);
						sqlString 			= buildSqlStatement(requestType, parmClass);
						findMITMAS 			= conn.prepareStatement(sqlString);
						rsMITMAS   			= findMITMAS.executeQuery();
						found				= "no";
						
						if (rsMITMAS.next())
						{
							itcl = rsMITMAS.getString("MMITCL");
							cfi1 = rsMITMAS.getString("MMCFI1");
							found = "yes";
						}
						
						findMITMAS.close();
						rsMITMAS.close();


						// only update records if the Item Master was found.
						if (found.equals("yes"))
						{
							requestType = "updateZ100003ItemData";
							parmClass = new Vector();
							parmClass.addElement(itno);
							parmClass.addElement(itcl);
							parmClass.addElement(cfi1);
							sqlString 	= buildSqlStatement(requestType, parmClass);
							updateIt = conn.prepareStatement(sqlString);
							updateIt.executeUpdate();
							updateIt.close();
						}
						
					}
				}	
				
				rsZ100003.close();
				findZ100003.close();
				
			} catch (Exception e) {
				throwError.append("Error at execute sql " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findZ100003 != null)
				findZ100003.close();
			if (findMITMAS != null)
				findMITMAS.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsZ100003 != null)
				rsZ100003.close();
			if (rsMITMAS != null)
				rsMITMAS.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateZ100003AssociatedItemMasterValues(");
		throwError.append("Connection).");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * update Z100003 records with religned
 * Item Master criteria.
 * 
 * @throws Exception
 */
public static void updateZ100003AssociatedItemMasterValues()
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = updateZ100003AssociatedItemMasterValues(conn);
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
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateZ100003AssociatedItemMasterValues(");
		throwError.append("). ");
	}
	
	return;
}

/**
 * update Z100003 records with religned
 * Customer Master criteria.
 * 
 * @param  Connection
 * @return Connection.
 * 
 * @throws Exception
 */
private static Connection updateZ100003AssociatedCustomerMasterValues(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsZ100003            = null;
    ResultSet rsOCUSMA			   = null;
	PreparedStatement findZ100003  = null;
	PreparedStatement findOCUSMA   = null;
	PreparedStatement updateIt     = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
	
		//get Z100003 records by Customer
		try //get the records
		{
			Vector parmClass = new Vector();
			requestType = "getZ100003ByCustomer";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at sql build of (getZ100003ByCustomer). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findZ100003  = conn.prepareStatement(sqlString);
				rsZ100003    = findZ100003.executeQuery();
				String cuno  = "x";
				String cucl  = "x";
				String smcd  = "x";
				String cfc3  = "x";
				String found = "x";
				
				// for each customer update the Z100003 file.
				while (rsZ100003.next() && throwError.toString().equals(""))
				{
					if (!rsZ100003.getString("UCCUNO").trim().equals(cuno.trim()))
					{
						cuno 				= rsZ100003.getString("UCCUNO").trim();
						Vector parmClass 	= new Vector();
						requestType 		= "getCustomerMasterByCustomer";
						parmClass.addElement(cuno);
						sqlString 			= buildSqlStatement(requestType, parmClass);
						findOCUSMA 			= conn.prepareStatement(sqlString);
						rsOCUSMA   			= findOCUSMA.executeQuery();
						found				= "no";
						
						if (rsOCUSMA.next())
						{
							cucl = rsOCUSMA.getString("OKCUCL").trim();
							smcd = rsOCUSMA.getString("OKSMCD").trim();
							cfc3 = rsOCUSMA.getString("OKCFC3").trim();
							found = "yes";
						}
						
						findOCUSMA.close();
						rsOCUSMA.close();


						// only update the record if the Customer master was found.
						if (found.equals("yes"))
						{
							requestType = "updateZ100003CustomerData";
							parmClass = new Vector();
							parmClass.addElement(cuno);
							parmClass.addElement(cucl);
							parmClass.addElement(smcd);
							parmClass.addElement(cfc3);
							sqlString 	= buildSqlStatement(requestType, parmClass);
							updateIt = conn.prepareStatement(sqlString);
							updateIt.executeUpdate();
							updateIt.close();
						}
					}
				}	
				
				rsZ100003.close();
				findZ100003.close();
				
			} catch (Exception e) {
				throwError.append("Error at execute sql " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findZ100003 != null)
				findZ100003.close();
			if (findOCUSMA != null)
				findOCUSMA.close();
			if (updateIt != null)
				updateIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsZ100003 != null)
				rsZ100003.close();
			if (rsOCUSMA != null)
				rsOCUSMA.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateZ100003AssociatedCustomerMasterValues(");
		throwError.append("Connection).");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * Load detail file with invoiced sequence NON zero
 * sales data from file OINACC and OSBSTD.
 * 
 * @param 
 * @return 
 * @throws Exception
 */
public static void buildSalesDetailSequenceNonZero()
{
	StringBuffer throwError = new StringBuffer();
	Connection connA = null;
	Connection connB = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		
		connA = ConnectionStack4.getConnection();
		connB = ConnectionStack4.getConnection();
		
		// execute the update method
		Vector conns = new Vector();
		conns.addElement(connA);
		conns.addElement(connB);
		
		conns = buildSalesDetailSequenceNonZero(conns);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try
		{
			if (connA != null)
				ConnectionStack4.returnConnection(connA);
		} catch(Exception e){
			throwError.append("Error closing connection. " + e);
		}
		try
		{
			if (connB != null)
				ConnectionStack4.returnConnection(connB);
		} catch(Exception e){
			throwError.append("Error closing connection. " + e);
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildSalesDetailSequenceNonZero(). ");
	}
	
	return;
}

/**
 * Build the Sales Summary File. 
 * Z100005
 * @param Connection
 * @return Connection.
 * @throws Exception.
 */
private static Connection buildSalesSummary(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsZ100003 = null;
	PreparedStatement findZ100003 = null;
	PreparedStatement addIt       = null;
	PreparedStatement deleteIt    = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
		
		//delete all entries in the Summary file Z100005.
		try
		{
			Vector parmClass = new Vector();
			requestType = "deleteFromZ100005";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (deleteFromZ100005). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				deleteIt = conn.prepareStatement(sqlString);
				deleteIt.executeUpdate();
			} catch (Exception e) {
				throwError.append("Error at execute sql (deleteFromZ100005). " + e);
			}
		}
		
		//add Sales Summary file records.
		if (throwError.toString().equals(""))
		{
			//build sql
			try {
				Vector parmClass = new Vector();
				requestType = "getSalesForSummary";
				sqlString   = buildSqlStatement(requestType, parmClass);
			} catch (Exception e) {
				throwError.append("Error at build sql (getSalesForSummary). " + e);
			}
			
			//execute sql
			if (throwError.toString().equals(""))
			{
				try {
					findZ100003 = conn.prepareStatement(sqlString);
					rsZ100003   = findZ100003.executeQuery();
					
					String cuNbr  = "first";
					String itNbr  = "first";
					String year   = "";
					String period = "";
					BigDecimal qty = new BigDecimal("0");
					qty.setScale(6);
					BigDecimal dol = new BigDecimal("0");
					dol.setScale(2);
				
					// accumulate each record. Write if Customer, Item, Year, or Period changes.
					while (rsZ100003.next() && throwError.toString().equals(""))
					{
						//test to see if this is the first record.
						if (cuNbr.toString().equals("first"))
						{
							cuNbr = rsZ100003.getString("UCCUNO").trim();
							itNbr = rsZ100003.getString("UCITNO").trim();
							year  = rsZ100003.getString("CPYEA4").trim();
							period = rsZ100003.getString("CPPERI").trim();
						}
						
						if (!cuNbr.toString().trim().equals(rsZ100003.getString("UCCUNO").trim()) ||
							!itNbr.toString().trim().equals(rsZ100003.getString("UCITNO").trim()) ||
							!year.toString().trim().equals(rsZ100003.getString("CPYEA4").trim()) ||
							!period.toString().trim().equals(rsZ100003.getString("CPPERI").trim()) )
						{
							try { //add previous accumulation and reset.
								Vector parmClass = new Vector();
								requestType = "addSummaryFile";
								parmClass.addElement(cuNbr);
								parmClass.addElement(itNbr);
								parmClass.addElement(year);
								parmClass.addElement(period);
								parmClass.addElement(qty);
								parmClass.addElement(dol);
								sqlString   = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								//reset accumulators.
								cuNbr  = rsZ100003.getString("UCCUNO").trim();
								itNbr  = rsZ100003.getString("UCITNO").trim();
								year   = rsZ100003.getString("CPYEA4").trim();
								period = rsZ100003.getString("CPPERI").trim();
								qty    = rsZ100003.getBigDecimal("UCIVQA");
								dol    = rsZ100003.getBigDecimal("SLAMT");
							} catch (Exception e) {
								throwError.append("Error at (addSummaryFile). " + e);
							}
						} else {
							qty = qty.add(rsZ100003.getBigDecimal("UCIVQA"));
							dol = dol.add(rsZ100003.getBigDecimal("SLAMT"));
						}
					}
					
					if (!cuNbr.trim().equals("first"))
					{
						try { //update previous accumulation and reset.
							Vector parmClass = new Vector();
							requestType = "addSummaryFile";
							parmClass.addElement(cuNbr);
							parmClass.addElement(itNbr);
							parmClass.addElement(year);
							parmClass.addElement(period);
							parmClass.addElement(qty);
							parmClass.addElement(dol);
							sqlString   = buildSqlStatement(requestType, parmClass);
							addIt = conn.prepareStatement(sqlString);
							addIt.executeUpdate();
						} catch (Exception e) {
							throwError.append("Error at (addSummaryFile) tail end. " + e);
						}
					}
				} catch (Exception e) {
					throwError.append("Error at execute sql (getSalesForSummary). " + e);
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
			if (findZ100003 != null)
				findZ100003.close();
			if (addIt != null)
				addIt.close();
		} catch(Exception e){
			throwError.append("Error closing prepared statements. " + e);
		}
		try
		{
			if (rsZ100003 != null)
				rsZ100003.close();
		} catch(Exception e){
			throwError.append("Error closing result set. " + e);
		}
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildSalesSummary(");
		throwError.append("Connection). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * Clear and load Sales History Summary DataSet
 * o100022 and o100026
 * @param 
 * @return 
 * @throws Exception
 */
public static void buildSalesSummaryDataSet()
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		
		conn = getDBConnection();
		
		// execute the update methods
		
		//Modify the o100022 file.
		conn = buildSalesSummaryDataSet22(conn);
		
		if (throwError.toString().trim().equals(""))
		{
			conn = buildSalesSummaryDataSet26(conn);
		}
			
				
	
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
				throwError.append("Error closing connection - " + el.toString() + ". ");
			}
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildSalesSummaryDataSet(). ");
		Error e = new Error();
		System.out.println(throwError.toString());
		throw new Exception(throwError.toString());
	}
	
	return;
}

/**
 * Add/update the Sales Summary Data Set. 
 * o100022
 * @param Connection
 * @return Connection.
 * @throws Exception.
 */
private static Connection buildSalesSummaryDataSet22(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsZ100003   = null;
    ResultSet rsSum       = null;
	Statement findZ100003 = null;
	Statement addIt       = null;
	Statement clearIt     = null;
	Statement findSum     = null;
	Statement updateIt    = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
		
		String fiscalYear     = "";
		String fiscalPeriod   = "";
		String nextFiscalYear = "";
		
		//determine the current fiscal year and period from 10 days ago.
		try {
			DateTime dt = UtilityDateTime.getSystemDate();
			DateTime dt2 = UtilityDateTime.addDaysToDate(dt, -20);
			fiscalYear = dt2.getM3FiscalYear();
			fiscalPeriod = dt2.getM3FiscalPeriod();
			int f = Integer.parseInt(fiscalYear);
			f = f + 1;
			nextFiscalYear = "" + f;
		} catch (Exception e) {
			throwError.append("Error obtaining fiscal year and period. ");
		}
		
		//clear Sales History entries in the Summary file o100022 for selected year and period.
		try
		{
			if (throwError.toString().equals(""))
			{
				Vector parmClass = new Vector();
				parmClass.addElement(fiscalYear);
				parmClass.addElement(fiscalPeriod);
				requestType = "clearO100022SalesHistory";
				sqlString = buildSqlStatement(requestType, parmClass);

				clearIt = conn.createStatement();
				clearIt.executeUpdate(sqlString);
				clearIt.close();
			}
		} catch (Exception e) {
			throwError.append("Error clearing sales history (clearO100022SalesHistory). " + e);
		}
		
		
		//add Sales Summary file records.
		if (throwError.toString().equals(""))
		{
			//build sql
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(fiscalYear);
				parmClass.addElement(fiscalPeriod);
				parmClass.addElement(nextFiscalYear);
				requestType = "getSalesForSummary";
				sqlString   = buildSqlStatement(requestType, parmClass);
			} catch (Exception e) {
				throwError.append("Error at build sql (getSalesForSummary). " + e);
			}
			
			//execute sql
			if (throwError.toString().equals(""))
			{
				try {
					findZ100003 = conn.createStatement();
					rsZ100003   = findZ100003.executeQuery(sqlString);
					
					String cuNbr  = "first";
					String itNbr  = "first";
					String year   = "";
					String period = "";
					BigDecimal qty = new BigDecimal("0");
					qty.setScale(6);
					BigDecimal dol = new BigDecimal("0");
					dol.setScale(2);
				
					// accumulate each record. Write if Customer, Item, Year, or Period changes.
					while (rsZ100003.next() && throwError.toString().equals(""))
					{
						//test to see if this is the first record.
						if (cuNbr.toString().equals("first"))
						{
							cuNbr = rsZ100003.getString("UCCUNO").trim();
							itNbr = rsZ100003.getString("UCITNO").trim();
							year  = rsZ100003.getString("CPYEA4").trim();
							period = rsZ100003.getString("CPPERI").trim();
						}
						
						if (!cuNbr.toString().trim().equals(rsZ100003.getString("UCCUNO").trim()) ||
							!itNbr.toString().trim().equals(rsZ100003.getString("UCITNO").trim()) ||
							!year.toString().trim().equals(rsZ100003.getString("CPYEA4").trim()) ||
							!period.toString().trim().equals(rsZ100003.getString("CPPERI").trim()) )
						{
							try { //add or update previous accumulation and reset.
								
								//get exising o100022 file entry if there.
								try {
									Vector parmClass = new Vector();
									parmClass.addElement(cuNbr);
									parmClass.addElement(itNbr);
									parmClass.addElement(year);
									parmClass.addElement(period);
									requestType = "getLBIRecord";
									sqlString = buildSqlStatement(requestType, parmClass);

									findSum = conn.createStatement();
									rsSum   = findSum.executeQuery(sqlString);
								} catch (Exception e) {
									throwError.append("Error trying to find matching summary record. " + e);
								}
								
								if (rsSum.next() && throwError.toString().equals(""))
								{	
									try{ //update LBI file entry.
										Vector parmClass = new Vector();
										requestType = "updateSummaryDataSet";
										parmClass.addElement(rsSum);
										parmClass.addElement(qty);
										parmClass.addElement(dol);
										sqlString   = buildSqlStatement(requestType, parmClass);
										updateIt = conn.createStatement();
										updateIt.executeUpdate(sqlString);
										updateIt.close();	
									} catch (Exception e) {
										throwError.append("Error on LBI file update. " + e);
									}
								} else 
								{
									//add LBI file entry.
									if (throwError.toString().equals(""))
									{
										try {
											Vector parmClass = new Vector();
											requestType = "addSummaryDataSet";
											parmClass.addElement(cuNbr);
											parmClass.addElement(itNbr);
											parmClass.addElement(year);
											parmClass.addElement(period);
											parmClass.addElement(qty);
											parmClass.addElement(dol);
											sqlString   = buildSqlStatement(requestType, parmClass);
											addIt = conn.createStatement();
											addIt.executeUpdate(sqlString);
											addIt.close();
										} catch (Exception e) {
											throwError.append("Error on LBI file add. " + e);
										}
									}
								}
								
								//reset accumulators.
								cuNbr  = rsZ100003.getString("UCCUNO").trim();
								itNbr  = rsZ100003.getString("UCITNO").trim();
								year   = rsZ100003.getString("CPYEA4").trim();
								period = rsZ100003.getString("CPPERI").trim();
								
								//get the mmitcl values as int instead of String. set to zero if necessary.
								int mmitcl = 0;
								try {
									mmitcl = rsZ100003.getInt("MMITCL");
								} catch (Exception e) {
									//allow mmitcl to stay at zero.
								}
								
								//use cases vs. alternate uom for FoodService only.
								if (rsZ100003.getString("OKCUCL").trim().equals("103") &&
									((mmitcl >= 300 && mmitcl <= 800) ||
									(mmitcl >= 100 && mmitcl <= 170) )) {
									qty = rsZ100003.getBigDecimal("UCIVQT");
								} else {
									qty    = rsZ100003.getBigDecimal("UCIVQA");
								}
								
								dol    = rsZ100003.getBigDecimal("SLAMT");
							} catch (Exception e) {
								throwError.append("Error at (addSummaryDataSet). " + e);
							}
							
							//close the findSum statement here in case it was used.
							try {
								findSum.close();
							} catch(Exception e)
							{}
							try {
								rsSum.close();
							} catch(Exception e)
							{}
							
						} else {
							//get the mmitcl values as int instead of String. set to zero if necessary.
							int mmitcl = 0;
							try {
								mmitcl = rsZ100003.getInt("MMITCL");
							} catch (Exception e) {
								//allow mmitcl to stay at zero.
							}
							
							//use cases vs. alternate uom for FoodService only.
							if (rsZ100003.getString("OKCUCL").trim().equals("103") &&
								((mmitcl >= 300 && mmitcl <= 800) ||
								(mmitcl >= 100 && mmitcl <= 170) )) {
								qty = qty.add(rsZ100003.getBigDecimal("UCIVQT"));
							} else {
								qty = qty.add(rsZ100003.getBigDecimal("UCIVQA"));
							}
							//qty = qty.add(rsZ100003.getBigDecimal("UCIVQA"));
							dol = dol.add(rsZ100003.getBigDecimal("SLAMT"));
						}
					}
					
					if (!cuNbr.trim().equals("first"))
					{
							
						//get exising o100022 file entry if there.
						try {
							Vector parmClass = new Vector();
							parmClass.addElement(cuNbr);
							parmClass.addElement(itNbr);
							parmClass.addElement(year);
							parmClass.addElement(period);
							requestType = "getLBIRecord";
							sqlString = buildSqlStatement(requestType, parmClass);

							findSum = conn.createStatement();
							rsSum   = findSum.executeQuery(sqlString);
						} catch (Exception e) {
							throwError.append("Error trying to find matching summary record. " + e);
						}
							
						if (rsSum.next() && throwError.toString().equals(""))
						{	
							try{ //update LBI file entry.
								Vector parmClass = new Vector();
								requestType = "updateSummaryDataSet";
								parmClass.addElement(rsSum);
								parmClass.addElement(qty);
								parmClass.addElement(dol);
								sqlString   = buildSqlStatement(requestType, parmClass);
								updateIt = conn.createStatement();
								updateIt.executeUpdate(sqlString);
								updateIt.close();		
							} catch (Exception e) {
								throwError.append("Error on LBI file update tail end. " + e);
							}
						} else 
						{
							//add LBI file entry.
							if (throwError.toString().equals(""))
							{
								try {
									Vector parmClass = new Vector();
									requestType = "addSummaryDataSet";
									parmClass.addElement(cuNbr);
									parmClass.addElement(itNbr);
									parmClass.addElement(year);
									parmClass.addElement(period);
									parmClass.addElement(qty);
									parmClass.addElement(dol);
									sqlString   = buildSqlStatement(requestType, parmClass);
									addIt = conn.createStatement();
									addIt.executeUpdate(sqlString);
								} catch (Exception e) {
									throwError.append("Error at (addSummaryDataSet) tail end. " + e);
								}
							}
						}
					}
				} catch (Exception e) {
					throwError.append("Error at execute sql (getSalesForSummary). " + e);
				}
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
	finally
	{
		try {
			if (findZ100003 != null)
				findZ100003.close();
			if (rsZ100003 != null)
				rsZ100003.close();
		} catch(Exception e){}//skipit
		
		try {
			if (findSum != null)
				findSum.close();
			if (rsSum != null)
				rsSum.close();
		} catch(Exception e){}//skipit
		
		try
		{
			if (addIt != null)
				addIt.close();
		} catch(Exception e){}//skipit
		
		try {
			if (clearIt != null)
				clearIt.close();
		} catch (Exception e) {}//skipit
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildSalesSummaryDataSet(");
		throwError.append("Connection). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * update the OSBSTD Alternate Unit of Measure field.
 * For specific return orders modify the UCIVQA field value. 
 * 
 * @throws Exception
 */
public static void updateAlternateUomOsbstd()
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack4.getConnection();
		
		// execute the update method
		updateAlternateUomOsbstd(conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try
		{
			if (conn != null)
				ConnectionStack4.returnConnection(conn);
		} catch(Exception e){
			throwError.append("Error closing connection. " + e);
		}
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateAlternateUomOsbstd(");
		throwError.append("). ");
	}
	
	return;
}

/**
 * update the OSBSTD Alternate Unit of Measure field.
 * For specific return orders modify the UCIVQA field value. 
 * 
 * @param  Connection
 * 
 * @throws Exception
 */
private static Connection updateAlternateUomOsbstd(Connection conn)						
	throws Exception 
{
	
	StringBuffer throwError     = new StringBuffer();
    ResultSet  rs               = null;
	PreparedStatement findThem  = null;
	PreparedStatement updateIt  = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
	
		//get data records to be modified.
		try //build sql statement
		{
			requestType = "getOsbstdAltUomErrors";
			Vector parmClass = new Vector();
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at sql build of (getOsbstdAltUomErrors). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findThem = conn.prepareStatement(sqlString);
				rs       = findThem.executeQuery();
			} catch (Exception e) {
				throwError.append("Error executing sql (getOsbstdAltUomErrors). " + e);
			}
				
			//modify all selected records.
			while (rs.next() && throwError.toString().equals(""))
			{
				//build sql statement.
				try {
					requestType = "updateOsbstdQuantity";
					Vector parmClass = new Vector();
					parmClass.addElement(rs);
					sqlString = buildSqlStatement(requestType, parmClass);
				} catch (Exception e) {
					throwError.append("Error building sql statement (updateOsbstdQuantity). " + e);
				}
				
				try {//execute the sql
					updateIt = conn.prepareStatement(sqlString);
					updateIt.executeUpdate();
					updateIt.close();
				} catch (Exception e) {
					throwError.append("Error executing sql (updateOsbstdQuantity). " + e);
				}
			}
			
			rs.close();
			findThem.close();
		}

				
	} catch (Exception e) {
		throwError.append("Error caught in finally. "  + e);
	} finally {//close prepared statements and result sets.
		try {
			rs.close();
		} catch (Exception e) {}
		try {
			findThem.close();
		} catch (Exception e) {}
		try {
			updateIt.close();
		} catch (Exception e) {}
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("updateAlternateUomOsbstd(");
		throwError.append("Connection).");
		throw new Exception(throwError.toString());
	}
  return conn;
}

/**
 * update the Zfile quantities for any "KG" UOM entries.
 * Modify the UCIVQA value back to Lb from Kg. 
 * 
 */
public static void updateZfileKgToLb()
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack4.getConnection();
		
		// execute the update method
		updateZfileKgToLb(conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try
		{
			if (conn != null)
				ConnectionStack4.returnConnection(conn);
		} catch(Exception e){
			throwError.append("Error closing connection. " + e);
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceSalesWorkFiles.");
			throwError.append("updateZfileKgToLb(");
			throwError.append("). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	return;
}

/**
 * update the Zfile quantities for any "KG" UOM entries.
 * Modify the UCIVQA value back to Lb from Kg. 
 * 
 * @param  Connection
 * 
 * @throws Exception
 */
private static Connection updateZfileKgToLb(Connection conn)						
	throws Exception 
{
	
	StringBuffer throwError     = new StringBuffer();
	PreparedStatement updateIt  = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
	
		//get sql statement to modify Z file.
		try //build sql statement
		{
			requestType = "updateZfileKgToLb";
			Vector parmClass = new Vector();
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at sql build of (updateZfileKgToLb). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {//execute the sql
				updateIt = conn.prepareStatement(sqlString);
				updateIt.executeUpdate();
				updateIt.close();
			} catch (Exception e) {
				throwError.append("Error executing sql (updateZfileKgToLb). " + e);
			}
		}		
	} catch (Exception e) {
		throwError.append("Error caught in finally. "  + e);
	} finally {//close prepared statements and result sets.
		try {
			if (updateIt == null)
				updateIt.close();
		} catch (Exception e) {
			throwError.append("Error closing connection/prepared statement. " + e);
		}
		
		//Log any errors.
		if (!throwError.toString().trim().equals("")) 
		{
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceSalesWorkFiles.");
			throwError.append("updateZfileKgToLb(");
			throwError.append("Connection).");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}
	
	// return data.	
	return conn;
}

/**
 * Add/update the Sales Summary Data Set. 
 * o100026
 * @param Connection
 * @return Connection.
 * @throws Exception.
 */
private static Connection buildSalesSummaryDataSet26(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsZ100003   = null;
    ResultSet rsSum       = null;
	Statement findZ100003 = null;
	Statement addIt       = null;
	Statement clearIt     = null;
	Statement findSum     = null;
	Statement updateIt    = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
		
		String fiscalYear     = "";
		String fiscalPeriod   = "";
		String nextFiscalYear = "";
		
		//determine the current fiscal year and period from 10 days ago.
		try {
			DateTime dt = UtilityDateTime.getSystemDate();
			DateTime dt2 = UtilityDateTime.addDaysToDate(dt, -10);
			fiscalYear = dt2.getM3FiscalYear();
			fiscalPeriod = dt2.getM3FiscalPeriod();
			int f = Integer.parseInt(fiscalYear);
			f = f + 1;
			nextFiscalYear = "" + f;
		} catch (Exception e) {
			throwError.append("Error obtaining fiscal year and period. ");
		}
		
		//clear Sales History entries in the Summary file o100026 for selected year and period.
		try
		{
			if (throwError.toString().equals(""))
			{
				Vector parmClass = new Vector();
				parmClass.addElement(fiscalYear);
				parmClass.addElement(fiscalPeriod);
				requestType = "clearO100026SalesHistory";
				sqlString = buildSqlStatement(requestType, parmClass);

				clearIt = conn.createStatement();
				clearIt.executeUpdate(sqlString);
			}
		} catch (Exception e) {
			throwError.append("Error clearing sales history (clearO100026SalesHistory). " + e);
		}
		
		
		//add Sales Summary file records.
		if (throwError.toString().equals(""))
		{
			//build sql
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(fiscalYear);
				parmClass.addElement(fiscalPeriod);
				parmClass.addElement(nextFiscalYear);
				requestType = "getSalesForSummary26";
				sqlString   = buildSqlStatement(requestType, parmClass);
			} catch (Exception e) {
				throwError.append("Error at build sql (getSalesForSummary26). " + e);
			}
			
			//execute sql
			if (throwError.toString().equals(""))
			{
				try {
					findZ100003 = conn.createStatement();
					rsZ100003   = findZ100003.executeQuery(sqlString);
					
					String cuNbr  = "first";
					String itNbr  = "first";
					//String whlo   = "first";
					String year   = "";
					String period = "";
					BigDecimal qty = new BigDecimal("0");
					qty.setScale(6);
				
					// accumulate each record. Write if Customer, Item, Year, or Period changes.
					while (rsZ100003.next() && throwError.toString().equals(""))
					{
						//test to see if this is the first record.
						if (cuNbr.toString().equals("first"))
						{
							cuNbr = rsZ100003.getString("zUCCUNO").trim();
							itNbr = rsZ100003.getString("zUCITNO").trim();
							//whlo  = rsZ100003.getString("zUCWHLO").trim();
							year  = rsZ100003.getString("zCPYEA4").trim();
							period = rsZ100003.getString("zCPPERI").trim();
						}
						
						if (!cuNbr.toString().trim().equals(rsZ100003.getString("zUCCUNO").trim()) ||
							!itNbr.toString().trim().equals(rsZ100003.getString("zUCITNO").trim()) ||
							//!whlo.toString().trim().equals(rsZ100003.getString("zUCWHLO").trim()) ||
							!year.toString().trim().equals(rsZ100003.getString("zCPYEA4").trim()) ||
							!period.toString().trim().equals(rsZ100003.getString("zCPPERI").trim()) )
						{
							try { //add or update previous accumulation and reset.
								
								//get exising o100026 file entry if there.
								try {
									Vector parmClass = new Vector();
									parmClass.addElement(cuNbr);
									parmClass.addElement(itNbr);
									//parmClass.addElement(whlo);
									parmClass.addElement(year);
									parmClass.addElement(period);
									requestType = "getLBI26Record";
									sqlString = buildSqlStatement(requestType, parmClass);

									findSum = conn.createStatement();
									rsSum   = findSum.executeQuery(sqlString);
								} catch (Exception e) {
									throwError.append("Error trying to find matching summary record. " + e);
								}
								
								if (rsSum.next() && throwError.toString().equals(""))
								{	
									try{ //update LBI file entry.
										Vector parmClass = new Vector();
										requestType = "updateSummaryDataSet26";
										parmClass.addElement(rsSum);
										parmClass.addElement(qty);
										//parmClass.addElement(dol);
										sqlString   = buildSqlStatement(requestType, parmClass);
										updateIt = conn.createStatement();
										updateIt.executeUpdate(sqlString);
										updateIt.close();	
									} catch (Exception e) {
										throwError.append("Error on LBI file update. " + e);
									}
								} else 
								{
									//add LBI file entry.
									if (throwError.toString().equals(""))
									{
										try {
											Vector parmClass = new Vector();
											requestType = "addSummaryDataSet26";
											parmClass.addElement(cuNbr);
											parmClass.addElement(itNbr);
											parmClass.addElement(year);
											parmClass.addElement(period);
											parmClass.addElement(qty);
											sqlString   = buildSqlStatement(requestType, parmClass);
											addIt = conn.createStatement();
											addIt.executeUpdate(sqlString);
											addIt.close();
										} catch (Exception e) {
											throwError.append("Error on LBI file add. " + e);
										}
									}
								}
								
								//reset accumulators.
								cuNbr  = rsZ100003.getString("zUCCUNO").trim();
								itNbr  = rsZ100003.getString("zUCITNO").trim();
								year   = rsZ100003.getString("zCPYEA4").trim();
								period = rsZ100003.getString("zCPPERI").trim();
							
								
								//use alternate uom only.
								qty    = rsZ100003.getBigDecimal("zUCIVQA");							
		
							} catch (Exception e) {
								throwError.append("Error at (addSummaryDataSet26). " + e);
							}
							
							//close the findSum statement here in case it was used.
							try {
								findSum.close();
							} catch(Exception e)
							{}
							try {
								rsSum.close();
							} catch(Exception e)
							{}
							
						} else {
							
							//use alternate uom only.
							qty = qty.add(rsZ100003.getBigDecimal("zUCIVQA"));
							
						}
					}
					
					if (!cuNbr.trim().equals("first"))
					{
							
						//get exising o100026 file entry if there.
						try {
							Vector parmClass = new Vector();
							parmClass.addElement(cuNbr);
							parmClass.addElement(itNbr);
							//parmClass.addElement(whlo);
							parmClass.addElement(year);
							parmClass.addElement(period);
							requestType = "getLBI26Record";
							sqlString = buildSqlStatement(requestType, parmClass);

							findSum = conn.createStatement();
							rsSum   = findSum.executeQuery(sqlString);
						} catch (Exception e) {
							throwError.append("Error trying to find matching summary record. " + e);
						}
							
						if (rsSum.next() && throwError.toString().equals(""))
						{	
							try{ //update LBI file entry.
								Vector parmClass = new Vector();
								requestType = "updateSummaryDataSet26";
								parmClass.addElement(rsSum);
								parmClass.addElement(qty);
								sqlString   = buildSqlStatement(requestType, parmClass);
								updateIt = conn.createStatement();
								updateIt.executeUpdate(sqlString);
								updateIt.close();		
							} catch (Exception e) {
								throwError.append("Error on LBI file update tail end. " + e);
							}
						} else 
						{
							//add LBI file entry.
							if (throwError.toString().equals(""))
							{
								try {
									Vector parmClass = new Vector();
									requestType = "addSummaryDataSet26";
									parmClass.addElement(cuNbr);
									parmClass.addElement(itNbr);
									//parmClass.addElement(whlo);
									parmClass.addElement(year);
									parmClass.addElement(period);
									parmClass.addElement(qty);
									sqlString   = buildSqlStatement(requestType, parmClass);
									addIt = conn.createStatement();
									addIt.executeUpdate(sqlString);
								} catch (Exception e) {
									throwError.append("Error at (addSummaryDataSet26) tail end. " + e);
								}
							}
						}
					}
				} catch (Exception e) {
					throwError.append("Error at execute sql (getSalesForSummary). " + e);
				}
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
	finally
	{
		try {
			if (findZ100003 != null)
				findZ100003.close();
			if (rsZ100003 != null)
				rsZ100003.close();
		} catch(Exception e){}//skipit
		
		try {
			if (findSum != null)
				findSum.close();
			if (rsSum != null)
				rsSum.close();
		} catch(Exception e){}//skipit
		
		try
		{
			if (addIt != null)
				addIt.close();
		} catch(Exception e){}//skipit
		
		try {
			if (clearIt != null)
				clearIt.close();
		} catch (Exception e) {}//skipit
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceSalesWorkFiles.");
		throwError.append("buildSalesSummaryDataSet26(");
		throwError.append("Connection). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}


}
