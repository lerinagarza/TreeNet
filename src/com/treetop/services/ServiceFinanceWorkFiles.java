 /*
 * Created on June 17, 2008
 * Author Tom Haile
 *
 */
package com.treetop.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import com.treetop.businessobjects.DateTime;
import com.treetop.utilities.*;
import java.math.RoundingMode;

/**
 * @author thaile
 *
 * Services class modify
 * Finance Work Files. 
 */
public class ServiceFinanceWorkFiles extends BaseService{

	public static final String dblib   = "DBPRD";
	public static final String library = "M3DJDPRD";
	//public static final String libraryx = "APDEV";
	
	/**
	 *  Construction of the Base inforamtion
	 */
	public ServiceFinanceWorkFiles() {
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

		   
			if (inRequestType.equals("GLCostingVariance"))
			{
				//sqlString.append("select * from " +  library + ".cpomat ");                                                                                            
				//sqlString.append("inner join m3djdprd.mitmas on nbcono = mmcono and nbmtno = mmitno ");  
				//sqlString.append("inner join m3djdprd.mittty on mmcono = tycono and mmitty = tyitty "); 
				//sqlString.append("LEFT OUTER JOIN " + library + ".CPOOPE ");
				//sqlString.append("ON NBCONO = NACONO AND NBDIVI = NADIVI AND NBFACI = NAFACI ");
				//sqlString.append("AND NBRIDN = NARIDN AND NBANBR = NAANBR AND NBSTSQ = NASTSQ ");
				sqlString.append("select ");
				sqlString.append("nbcono, nbfaci, nbridn, nbmtno, nbmseq, nbocnr, nbanbr, nbtoaf, ");
				sqlString.append("nbtosf, nbaiqt, nbsiqt, nbdept, nbdivi, nbplgr, nbopno, nbstsq, ");
				sqlString.append("naplgr, nadept, naopno, NASTSQ, NADEPT, ");
				sqlString.append("mmitty, mmitgr, ");
				sqlString.append("tycmre ");
				sqlString.append("from m3djdprd.cpomat ");
				sqlString.append("inner join m3djdprd.mitmas on nbcono = mmcono and nbmtno = mmitno ");
				sqlString.append("inner join m3djdprd.mittty on mmcono = tycono and mmitty = tyitty ");
				sqlString.append("LEFT OUTER JOIN m3djdprd.CPOOPE ");
				sqlString.append("ON NBCONO = NACONO AND NBDIVI = NADIVI AND NBFACI = NAFACI ");
				sqlString.append("and nbocat = naocat AND NBRIDN = NARIDN ");
				sqlString.append("AND NBANBR = NAANBR AND NBSTSQ = NASTSQ ");
				sqlString.append("order by ");
				sqlString.append("nbcono, nbdivi, nbfaci, nbocat, nbridn, nbridl, nbmseq, ");
				sqlString.append("nbocnr, nbmtno, nbwhlo");
				
			}
			
			
			
			if (inRequestType.equals("getVarianceCostEarnings"))
			{
				//extract incoming sql criteria.
				String begDate = (String) requestClass.elementAt(0);
				String endDate = (String) requestClass.elementAt(1);
		   	  
				sqlString.append("SELECT  KQCONO, KQFACI, KQITNO, KQSTRT, KQPCTP, KQPCDT, ");
				sqlString.append("KQCA01, KQCA02, KQCA03, KQCA04, KQCA05, KQCB01, KQCB02, ");
				sqlString.append("KQCB03, KQCB04, KQCB05, KQCB06, KQCB07, KQCB08, KQCB09, ");
				sqlString.append("KQCB10, KQCB11, KQCB12, KQCB13, KQCB14, KQCC01, KQCC02, ");
				sqlString.append("KQCD01, KQCD02, KQCE01, KQCE02, KQCE03, KQCE04, KQCE05, ");
				sqlString.append("KQCE06, KQCE07, KQCE08, KQCE09, KQCE10, KQSSU1, KQSSU2, ");
				sqlString.append("KQSSU3, MMITGR, MMITTY ");
				sqlString.append("FROM " + library + ".MITMAS ");
				sqlString.append("INNER JOIN " + library + ".MCCOML ");
				sqlString.append("ON MMCONO = KQCONO AND MMITNO = KQITNO ");
				
				sqlString.append("WHERE KQSTRT = 'STD' AND KQPCTP = '3' ");
				sqlString.append("AND KQCONO = 100 ");
				sqlString.append("AND KQPCDT >= " + begDate + " ");
				sqlString.append("AND KQPCDT <= " + endDate + " ");
				sqlString.append("ORDER BY KQFACI, KQITNO, KQPCDT DESC ");
			}
			
			
			
			if (inRequestType.equals("getVoucherAndDate"))
			{
				//extract incoming sql criteria.
				String cono = (String) requestClass.elementAt(0);
				String anbr = (String) requestClass.elementAt(1);
				String ait5 = (String) requestClass.elementAt(2);
				
				sqlString.append("Select EZCONO, EZANBR, EZAIT5, EZACDT, EZVONO ");
				sqlString.append("FROM " + library + ".CINACC ");
				sqlString.append("WHERE EZCONO = " + cono + " ");
				sqlString.append("AND EZANBR = " + anbr + " ");
				sqlString.append("AND EZAIT5 = '" + ait5 + "' ");
			}
			
			
			
			if (inRequestType.equals("getCostCenterFromDept"))
			{
				//extract incoming sql criteria.
				String cono = (String) requestClass.elementAt(0);
				String divi = (String) requestClass.elementAt(1);
				String dept = (String) requestClass.elementAt(2);
				
				sqlString.append("Select * ");
				sqlString.append("FROM " + library + ".CAREPL ");
				sqlString.append("WHERE CYCONO = " + cono + " ");
				sqlString.append("AND CYDIVI = '" + divi + "' ");
				sqlString.append("AND CYOBJC = 'PPDEPT' ");
				sqlString.append("AND CYOBJA >= '" + dept + "' ");
				sqlString.append("AND CYOBJB <= '" + dept + "' ");
			}
			
			
			
			if (inRequestType.equals("getDeptWithoutSTSQ"))
			{
				//extract incoming sql criteria.
				ResultSet rs = (ResultSet) requestClass.elementAt(0);
				
				sqlString.append("Select * ");
				sqlString.append("FROM " + library + ".CPOOPE ");
				sqlString.append("WHERE NACONO = " + rs.getString("NBCONO") + " ");
				sqlString.append("AND NADIVI = '" + rs.getString("NBDIVI") + "' ");
				sqlString.append("AND NAFACI = '" + rs.getString("NBFACI") + "' ");
				sqlString.append("AND NARIDN = '" + rs.getString("NBRIDN") + "' ");
				sqlString.append("AND NAANBR = " + rs.getString("NBANBR") + " ");
			}
			
			
			
			if (inRequestType.equals("getDeptWithoutANBR"))
			{
				//extract incoming sql criteria.
				ResultSet rs = (ResultSet) requestClass.elementAt(0);
				
				sqlString.append("Select * ");
				sqlString.append("FROM " + library + ".CPOOPE ");
				sqlString.append("WHERE NACONO = " + rs.getString("NBCONO") + " ");
				sqlString.append("AND NADIVI = '" + rs.getString("NBDIVI") + "' ");
				sqlString.append("AND NAFACI = '" + rs.getString("NBFACI") + "' ");
				sqlString.append("AND NARIDN = '" + rs.getString("NBRIDN") + "' ");
			}
			
			
			
			if (inRequestType.equals("getFgledgVariance"))
			{
				sqlString.append("SELECT * FROM " + library + ".FGLEDG ");
				sqlString.append("WHERE EGAIT1 = '53140' ");
				sqlString.append("AND EGAIT6 = '1001690' ");//temporary
				sqlString.append("ORDER BY EGVSER, EGAIT6, EGACDT ");
			}
			
			
			
			if (inRequestType.equals("getCinaccVariance"))
			{
				sqlString.append("SELECT * ");
				sqlString.append("FROM " + library + ".CINACC ");
				sqlString.append("WHERE EZAIT1 = '53140' ");
				sqlString.append("AND EZAIT6 = '1001690' ");//temporary
			}
			
			
			
			if (inRequestType.equals("getCinaccMittraRecords"))
			{
				String anbr = (String) requestClass.elementAt(0);
				String itno = (String) requestClass.elementAt(1);
				
				sqlString.append("SELECT * FROM " + library + ".MITTRA ");
				//sqlString.append("WHERE ")
			}
			
			
			
			if (inRequestType.equals("getFginaeVariance"))
			{
				sqlString.append("SELECT * FROM " + library + ".FGINAE ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MPURST ");
				sqlString.append("ON F9CONO = IHCONO AND F9PUNO = IHPUNO ");
				sqlString.append("AND F9PNLI = IHPNLI AND F9PNLS = IHPNLS ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MITAUN ");
				sqlString.append("ON IHCONO = MUCONO AND IHITNO = MUITNO ");
				sqlString.append("AND IHPPUN = MUALUN AND MUAUTP = 1 ");
				sqlString.append("WHERE F9AIT1 = '53140' ");
				sqlString.append("AND F9AIT6 = '1001690' ");//temporary
				sqlString.append("ORDER BY F9AIT6, F9ACDT, F9PNLI, F9PNLS ");
			}
			
			
			
			if (inRequestType.equals("getFginaeVariance2"))
			{
				sqlString.append("SELECT * FROM " + library + ".FGINAE ");
				sqlString.append("INNER JOIN " + library + ".MITTRA ");
				sqlString.append("ON F9REPN = MTREPN ");
				sqlString.append("WHERE F9AIT1 = '53140' ");
				sqlString.append("AND F9AIT6 = '1001690' ");//temporary
				sqlString.append("ORDER BY F9AIT6, F9ACDT, F9PNLI, F9PNLS ");
			}
			
			
			
			if (inRequestType.equals("getGlComparisionCosts"))
			{
				//extract incoming sql criteria.
				String fiscalYear = (String) requestClass.elementAt(0);
				String costDate   = (String) requestClass.elementAt(1);
				String costType   = (String) requestClass.elementAt(2);
				
				sqlString.append("SELECT  KQCONO, KQFACI, KQITNO, KQSTRT, KQPCTP, KQPCDT, ");
				sqlString.append("KQCA01, KQCA02, KQCA03, KQCA04, KQCA05, KQCB01, KQCB02, ");
				sqlString.append("KQCB03, KQCB04, KQCB05, KQCB06, KQCB07, KQCB08, KQCB09, ");
				sqlString.append("KQCB10, KQCB11, KQCB12, KQCB13, KQCB14, KQCC01, KQCC02, ");
				sqlString.append("KQCD01, KQCD02, KQCE01, KQCE02, KQCE03, KQCE04, KQCE05, ");
				sqlString.append("KQCE06, KQCE07, KQCE08, KQCE09, KQCE10, KQSSU1, KQSSU2, ");
				sqlString.append("KQSSU3 ");
				sqlString.append("FROM " + library + ".MCCOML ");
				sqlString.append("WHERE KQSTRT = 'STD' AND KQPCTP = '3' ");
				sqlString.append("AND KQCONO = 100 ");
				//////////////////////sqlString.append("AND KQPCDT >= " + begDate + " ");
				/////////////////////sqlString.append("AND KQPCDT <= " + endDate + " ");
				sqlString.append("ORDER BY KQFACI, KQITNO, KQPCTP, KQPCDT DESC ");
			}
			
			
			
			if (inRequestType.equals("getMccomlStartFiscalYear"))
			{
				//extract incoming sql criteria.
				String fiscalDate = (String) requestClass.elementAt(0);
				String costType   = (String) requestClass.elementAt(1);
				String company    = (String) requestClass.elementAt(2);
				
				sqlString.append("SELECT * FROM " + library + ".MCCOML ");
				sqlString.append("WHERE KQPCDT >= " + fiscalDate + " ");
				sqlString.append("AND KQCROC = 0 ");
				sqlString.append("AND KQPCTP = '" + costType + "' ");
				sqlString.append("AND KQCONO = " + company + " ");
				sqlString.append("AND KQITNO <> '' ");
				sqlString.append("ORDER BY KQCONO, KQFACI, KQITNO, KQPCDT, KQSTRT ");
			}
			
			
			
			if (inRequestType.equals("getMccomlAsOfDate"))
			{
				//extract incoming sql criteria.
				String currDate   = (String) requestClass.elementAt(0);
				String costType   = (String) requestClass.elementAt(1);
				String company    = (String) requestClass.elementAt(2);
				String startDate  = (String) requestClass.elementAt(3);
				
				sqlString.append("SELECT * FROM " + library + ".MCCOML ");
				sqlString.append("WHERE KQPCDT <= " + currDate + " ");
				sqlString.append("AND KQPCDT >= " + startDate + " ");
				sqlString.append("AND KQCROC = 0 ");
				sqlString.append("AND KQPCTP = '" + costType + "' ");
				sqlString.append("AND KQCONO = " + company + " ");
				sqlString.append("AND KQITNO <> '' ");
				sqlString.append("ORDER BY KQCONO, KQFACI, KQITNO, KQPCDT DESC, KQSTRT ");
			}
			
			
			
			if (inRequestType.equals("getMccomlIntoZtglccAsBlanks"))
			{
				//extract incoming sql criteria.
				String endDate    = (String) requestClass.elementAt(0);
				String costType   = (String) requestClass.elementAt(1);
				String company    = (String) requestClass.elementAt(2);
				String startDate  = (String) requestClass.elementAt(3);
				
				sqlString.append("SELECT * FROM " + library + ".MCCOML ");
				sqlString.append("WHERE (NOT EXISTS (SELECT * FROM DBPRD.ZTGLCC ");
				sqlString.append("where kqcono = cono and kqfaci = faci and kqitno = itno and kqstrt = strt ");
				sqlString.append("and kqpctp = pctp and kqpcdt = cstdte)) ");
				sqlString.append("AND KQCONO = " + company + " ");
				sqlString.append("AND KQCROC = 0 ");
				sqlString.append("AND KQPCDT <= " + endDate + " ");
				sqlString.append("AND KQPCDT >= " + startDate + " ");
				sqlString.append("AND KQPCTP = '" + costType + "' ");
				sqlString.append("AND KQITNO <> '' ");
				sqlString.append("ORDER BY KQCONO, KQFACI, KQITNO, KQPCDT DESC, KQSTRT ");
			}
			
			
			
			
			if (inRequestType.equals("getMcbomsStartFiscalYear"))
			{
				//extract incoming sql criteria.
				String fiscalDate = (String) requestClass.elementAt(0);
				String costType   = (String) requestClass.elementAt(1);
				String company    = (String) requestClass.elementAt(2);
				
				sqlString.append("SELECT  KUCONO, KUFACI, KUITNO, KUSTRT, KUPCTP, KUPCDT, ");
				sqlString.append("KUOPNO, KUMSEQ, KUMTNO, KUCNQT, KUSIQT, KUPEUN, KUWAPC, ");
				sqlString.append("a.MMITDS as aMMITDS, a.MMMABU as aMMMABU, ");
				sqlString.append("b.MMITDS as bMMITDS, b.MMMABU as bMMMABU, KRMATC, ");
				sqlString.append("a.MMITGR as aMMITGR, b.MMITGR as bMMITGR, ");
				sqlString.append("a.MMITCL as aMMITCL, b.MMITCL as bMMITCL, ");
				sqlString.append("KOPRCM, KMELCO, KRCA04, KRCA05, M9VAMT ");
				sqlString.append("FROM " + library + ".MCBOMS ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MITMAS a ");
				sqlString.append("ON KUCONO = a.MMCONO AND KUITNO = a.MMITNO ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MITMAS b ");
				sqlString.append("ON KUCONO = b.MMCONO AND KUMTNO = b.MMITNO ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MCCOPU ");
				sqlString.append("ON KUCONO = KRCONO AND KUFACI = KRFACI ");
				sqlString.append("AND KUMTNO = KRITNO AND KUPCTP = KRPCTP ");
				sqlString.append("AND KUPCDT = KRPCDT ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MCHEAD ");
				sqlString.append("ON KUCONO = KOCONO AND KUFACI = KOFACI ");
				sqlString.append("AND KUITNO = KOITNO AND KUSTRT = KOSTRT ");
				sqlString.append("AND KUPCTP = KOPCTP AND KUPCDT = KOPCDT ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MCCCDF ");
				sqlString.append("ON KOCONO = KMCONO AND KOPRCM = KMPRCM ");
				sqlString.append("AND (KMELCO = 'A05' or KMELCO = 'A04') AND KUPCDT >= KMFRDT ");
				sqlString.append("AND KUPCDT <= KMTODT ");
				sqlString.append("INNER JOIN " + library + ".MITFAC ");
				sqlString.append("ON KUCONO = M9CONO AND KUFACI = M9FACI ");
				sqlString.append("AND KUMTNO = M9ITNO ");
				sqlString.append("WHERE KUPCDT >= " + fiscalDate + " ");
				sqlString.append("AND KUSTRT = 'STD' ");
				sqlString.append("AND KUPCTP = '" + costType + "' ");
				sqlString.append("AND KUCONO = " + company + " ");
				sqlString.append("ORDER BY KUCONO, KUFACI, KUITNO, KUPCDT, KUOPNO, KUMSEQ ");
			}
			
			
			
			if (inRequestType.equals("getMcbomsAsOfDate"))
			{
				//extract incoming sql criteria.
				String currDate = (String) requestClass.elementAt(0);
				String costType   = (String) requestClass.elementAt(1);
				String company    = (String) requestClass.elementAt(2);
				String startDate  = (String) requestClass.elementAt(3);
				
				sqlString.append("SELECT  KUCONO, KUFACI, KUITNO, KUSTRT, KUPCTP, KUPCDT, ");
				sqlString.append("KUOPNO, KUMSEQ, KUMTNO, KUCNQT, KUSIQT, KUPEUN, KUWAPC, ");
				sqlString.append("a.MMITDS as aMMITDS, a.MMMABU as aMMMABU, ");
				sqlString.append("b.MMITDS as bMMITDS, b.MMMABU as bMMMABU, KRMATC, ");
				sqlString.append("a.MMITGR as aMMITGR, b.MMITGR as bMMITGR, ");
				sqlString.append("a.MMITCL as aMMITCL, b.MMITCL as bMMITCL, ");
				sqlString.append("KOPRCM, KMELCO, KRCA04, KRCA05, M9VAMT ");
				sqlString.append("FROM " + library + ".MCBOMS ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MITMAS a ");
				sqlString.append("ON KUCONO = a.MMCONO AND KUITNO = a.MMITNO ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MITMAS b ");
				sqlString.append("ON KUCONO = b.MMCONO AND KUMTNO = b.MMITNO ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MCCOPU ");
				sqlString.append("ON KUCONO = KRCONO AND KUFACI = KRFACI ");
				sqlString.append("AND KUMTNO = KRITNO AND KUPCTP = KRPCTP ");
				sqlString.append("AND KUPCDT = KRPCDT ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MCHEAD ");
				sqlString.append("ON KUCONO = KOCONO AND KUFACI = KOFACI ");
				sqlString.append("AND KUITNO = KOITNO AND KUSTRT = KOSTRT ");
				sqlString.append("AND KUPCTP = KOPCTP AND KUPCDT = KOPCDT ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MCCCDF ");
				sqlString.append("ON KOCONO = KMCONO AND KOPRCM = KMPRCM ");
				sqlString.append("AND (KMELCO = 'A05' or KMELCO = 'A04') AND KUPCDT >= KMFRDT ");
				sqlString.append("AND KUPCDT <= KMTODT ");
				sqlString.append("INNER JOIN " + library + ".MITFAC ");
				sqlString.append("ON KUCONO = M9CONO AND KUFACI = M9FACI ");
				sqlString.append("AND KUMTNO = M9ITNO ");
				sqlString.append("WHERE KUPCDT <= " + currDate + " ");
				sqlString.append("AND KUPCDT >= " + startDate + " ");
				sqlString.append("AND KUSTRT = 'STD' ");
				sqlString.append("AND KUPCTP = '" + costType + "' ");
				sqlString.append("AND KUCONO = " + company + " ");
				sqlString.append("ORDER BY KUCONO, KUFACI, KUITNO, KUPCDT DESC, KUOPNO, KUMSEQ ");
			}
			
			
			
			if (inRequestType.equals("getZtglccData"))
			{
				//extract incoming sql criteria.
				String company    = (String) requestClass.elementAt(0);
				String fiscalYear = (String) requestClass.elementAt(1);
				
				sqlString.append("SELECT * FROM " + dblib + ".ZTGLCC ");
				sqlString.append("WHERE CONO = " + company + " ");
				sqlString.append("AND FYEA = " + fiscalYear + " ");
				sqlString.append("AND POINT <> '' ");
				sqlString.append("ORDER BY CONO, FACI, ITNO, FYEA, PCTP ");
			}
			
			
			
			if (inRequestType.equals("getZtglccseData"))
			{
				//extract incoming sql criteria.
				String company    = (String) requestClass.elementAt(0);
				String fiscalYear = (String) requestClass.elementAt(1);
				
				sqlString.append("SELECT cono, faci, itno, fyea, fdate, point, cstdte, asfdte, strt, pctp, ");
				sqlString.append("compnm, compvl, tx15, tx40, cdate, ctime, mmitds, mmitgr, mmitcl, mmmabu ");
				sqlString.append("FROM " + dblib + ".ZTGLCCSE ");
				sqlString.append("inner join " + library + ".mchead on cono = kocono and faci = kofaci ");
				sqlString.append("and itno = koitno and strt = kostrt and pctp = kopctp ");
				sqlString.append("inner join " + library + ".mcccdf on kocono = kmcono and koprcm = kmprcm ");
				sqlString.append("and kmtodt >= kostrd and kmfrdt <= kostrd and compnm = kmelco " );
				sqlString.append("left outer join m3djdprd.mitmas on cono = mmcono ");
				sqlString.append("and itno = mmitno ");
				sqlString.append("WHERE CONO = " + company + " ");
				sqlString.append("AND FYEA = " + fiscalYear + " ");
				sqlString.append("AND POINT <> '' ");
				sqlString.append("and cstdte = kopcdt AND COMPNM > 'A99' ");
				sqlString.append("ORDER BY CONO, FACI, ITNO, FYEA, PCTP ");
			}
			
			
			
			if (inRequestType.equals("getZtglccData2"))
			{
				//extract incoming sql criteria.
				String company    = (String) requestClass.elementAt(0);
				String fiscalYear = (String) requestClass.elementAt(1);
				
				sqlString.append("SELECT * FROM " + dblib + ".ZTGLCC ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MCHEAD ");
				sqlString.append("ON CONO = KOCONO AND FACI = KOFACI ");
				sqlString.append("AND ITNO = KOITNO AND STRT = KOSTRT ");
				sqlString.append("AND PCTP = KOPCTP AND CSTDTE = KOPCDT ");
				sqlString.append("WHERE CONO = " + company + " ");
				sqlString.append("AND FYEA >= " + fiscalYear + " ");
			}
			
			
			
			if (inRequestType.equals("getMccompData"))
			{
				//extract incoming sql criteria.
				String company    = (String) requestClass.elementAt(0);
				
				sqlString.append("SELECT * FROM " + library + ".MCCOMP ");
				sqlString.append("WHERE KCCONO = " + company + " ");
				sqlString.append("ORDER BY KCCONO, KCCCOM ");
			}
			
			
			
			if (inRequestType.equals("getMcccdfComponents"))
			{
				//extract incoming sql criteria.
				ResultSet rs = (ResultSet) requestClass.elementAt(0);
				
				sqlString.append("SELECT * FROM " + library + ".MCCCDF ");
				sqlString.append("WHERE KMCONO = " + rs.getString("KOCONO") + " ");
				sqlString.append("AND KMPRCM = '" + rs.getString("KOPRCM") + "' ");
				sqlString.append("AND KMFRDT <= " + rs.getString("CSTDTE") + " ");
				sqlString.append("AND KMTODT >= " + rs.getString("CSTDTE") + " ");
			}
			
			
			
			if (inRequestType.equals("getZtfcstConsumedProduced"))
			{
				//extract incoming sql criteria.
				String cono  = (String) requestClass.elementAt(0);
				String fYear = (String) requestClass.elementAt(1);
				
				sqlString.append("SELECT a.CONO as aCONO, a.FACI as aFACI, "); 
				sqlString.append("a.ITEM as aITEM, a.STRT as aSTRT, a.PCTP as aPCTP, ");
				sqlString.append("a.CSTDTE as aCSTDTE, a.OPNBR as aOPNBR, a.SEQNBR as aSEQNBR, ");
				sqlString.append("a.FYEA as aFYEA, a.FDATE as aFDATE, a.POINT as aPOINT, ");
				sqlString.append("a.CUNBR as aCUNBR, a.CMABU as aCMABU, a.CNQT as aCNQT, ");
				sqlString.append("a.SIQT as aSIQT, a.CUWAPC as aCUWAPC, a.CUCOST as aCUCOST, ");
				sqlString.append("a.CUAMT as aCUAMT, a.DATA as aDATA, ");
				sqlString.append("b.CONO as bCONO, b.FACI as bFACI, b.ITNO as bITNO, ");
				sqlString.append("b.FYEA as bFYEA, b.FDATE as bFDATE, b.POINT as bPOINT, ");
				sqlString.append("b.CSTDTE as bCSTDTE, b.STRT as bSTRT, b.PCTP as bPCTP, ");
				sqlString.append("b.CMODEL as bCMODEL, b.CMTOT as bCMTOT, ");
				sqlString.append("M9VAMT, MMWAPC ");
				sqlString.append("FROM " + dblib + ".ZTFCST a ");
				sqlString.append("LEFT OUTER JOIN " + dblib + ".ZTGLCC b ");
				sqlString.append("ON a.CONO = b.CONO AND a.FACI = b.FACI ");
				sqlString.append("AND a.CUNBR = b.ITNO AND a.STRT = b.STRT ");
				sqlString.append("AND a.PCTP = b.PCTP AND a.FYEA = b.FYEA ");
				sqlString.append("AND a.POINT = b.POINT ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MITFAC ");
				sqlString.append("ON a.CONO = M9CONO and a.FACI = M9FACI and a.CUNBR = M9ITNO ");
				sqlString.append("LEFT OUTER JOIN " + library + ".MITMAS ");
				sqlString.append("ON a.CONO = MMCONO AND a.CUNBR = MMITNO ");
				sqlString.append("WHERE a.CONO = " + cono + " ");
				sqlString.append("AND a.CMABU = 1 AND M9VAMT = 1 ");
				sqlString.append("AND a.FYEA = " + fYear + " ");
			}
			
			
			
			if (inRequestType.equals("getZtfcstForOperationsUpdate"))
			{
				//extract incoming sql criteria.
				String cono  = (String) requestClass.elementAt(0);
				String fYear = (String) requestClass.elementAt(1);
				
				sqlString.append("select CONO,FACI, ITEM, STRT, PCTP, CSTDTE, FYEA, POINT, ");
				sqlString.append("kvctcd, kvprnp, kvplgr ");
				sqlString.append("from " + dblib + ".ZTFCST ");
				sqlString.append("left outer join " + library + ".mcrous on cono = kvcono and faci = kvfaci ");
				sqlString.append("and item = kvitno and strt = kvstrt and cstdte = kvpcdt and pctp = kvpctp ");
				sqlString.append("where fyea = " + fYear + " ");
				sqlString.append("group by cono, CSTDTE, FACI, FYEA, PCTP, ITEM, POINT, STRT, ");
				sqlString.append("kvplgr,kvprnp,kvctcd ");
			}
			
			
			
//20090806			if (inRequestType.equals("getMpdopeByZtfcst"))
//20090806			{
//20090806				//extract incoming sql criteria.
//20090806				ResultSet rs = (ResultSet) requestClass.elementAt(0);
//20090806				
//20090806				sqlString.append("select POCTCD, POPRNP, POFDAT, POTDAT ");
//20090806				sqlString.append("from " + library + ".MPDOPE ");
//20090806				sqlString.append("WHERE POCONO = " + rs.getString("CONO") + " ");
//20090806				sqlString.append("AND POFACI = '" + rs.getString("FACI") + "' ");
//20090806				sqlString.append("AND POPRNO = '" + rs.getString("ITEM") + "' ");
//20090806				sqlString.append("AND POSTRT = '" + rs.getString("STRT") + "' ");
//20090806				sqlString.append("order by pocono, pofaci, poprno, postrt, poopno, pomseq ");
//20090806			}
			
			
			
			if (inRequestType.equals("getDuplicateEntries"))
			{
				//extract incoming sql criteria.
				String co = (String) requestClass.elementAt(0);
				String yr = (String) requestClass.elementAt(1);
				
				sqlString.append("select a.cono as acono, a.faci as afaci, a.itno as aitno, ");
				sqlString.append("mmitds, a.fyea as afyea, a.point as apoint, a.cstdte as acstdte, ");
				sqlString.append("a.strt as astrt, a.pctp as apctp, a.cmodel as acmodel, ");
				sqlString.append("a.cmtot as acmtot, c.strt as cstrt, c.pctp as cpctp, ");
				sqlString.append("c.cmodel as ccmodel, c.cmtot as ccmtot, m9rewh, mbpuit, m9ucos ");
				sqlString.append("from " + dblib + ".ZTGLCC a ");
				sqlString.append("inner join dbprd.ztglcc c on a.cono = c.cono and a.faci = c.faci ");
				sqlString.append("and a.itno = c.itno and a.fyea = c.fyea and a.point = c.point ");
				sqlString.append("and a.cstdte = c.cstdte and a.pctp = c.pctp and a.strt <> c.strt ");
				sqlString.append("left outer join m3djdprd.mitfac on a.cono = m9cono and a.faci = m9faci ");
				sqlString.append("and a.itno = m9itno ");
				sqlString.append("left outer join m3djdprd.mitbal on m9cono = mbcono and m9rewh = mbwhlo ");
				sqlString.append("and m9itno = mbitno ");
				sqlString.append("left outer join m3djdprd.mitmas on a.cono = mmcono and a.itno = mmitno ");
				sqlString.append("where (EXISTS (select * from dbprd.ztglcc b ");
				sqlString.append("where a.cono = b.cono and a.faci = b.faci and a.itno = b.itno ");
				sqlString.append("and a.fyea = b.fyea and a.point = b.point and a.pctp = b.pctp ");
				sqlString.append("and a.strt <> b.strt and b.strt = '')) ");
				sqlString.append("and a.fyea = 2009 ");
				//sqlString.append("--and a.faci <> '302' ");
				sqlString.append("order by a.cono, a.itno, a.faci, a.point ");
			}
			
			
			
			if (inRequestType.equals("getCspafcst"))
			{
				//extract incoming sql criteria.
				String company = (String) requestClass.elementAt(0);
				String fYear   = (String) requestClass.elementAt(1);
				
				sqlString.append("select CSAYEAR, CSAFACI, CSAITNO, CSAFQTY ");
				sqlString.append("from " + dblib + ".CSPAFCST ");
				sqlString.append("WHERE CSAYEAR = " + fYear + " ");
				sqlString.append("order by CSAYEAR, CSAFACI, CSAITNO ");
			}
			
			
			
			if (inRequestType.equals("getZtfcstNonMatching"))
			{
				//extract incoming sql criteria.
				String pointNE     = (String) requestClass.elementAt(0); //point in file    (not exists)
				String nextFYearNE = (String) requestClass.elementAt(1); //next fiscal year (not exists)
				String costTypeNE  = (String) requestClass.elementAt(2); //cost type        (not exists)
				String pointE	   = (String) requestClass.elementAt(3); //point in file    (exists)
				String fYearE	   = (String) requestClass.elementAt(4); //requested year   (exists)
				String costTypeE   = (String) requestClass.elementAt(5); //cost type        (exists)
				String company	   = (String) requestClass.elementAt(6); //company			(exists)
				
				sqlString.append("select ");
				sqlString.append("a.cono as aCONO, a.faci as aFACI, a.item as aITEM, ");
				sqlString.append("a.fyea as aFYEA, a.cstdte as aCSTDTE, a.strt as aSTRT, ");
				sqlString.append("a.PCTP as aPCTP, a.fdate as aFDATE, a.point as aPOINT, ");
				sqlString.append("a.asfdte as aASFDTE, a.data as aDATA, a.cdate as aCDATE, ");
				sqlString.append("a.ctime as aCTIME, a.pitds as aPITDS, a.pitgr as aPITGR, ");
				sqlString.append("a.pitcl as aPITCL, a.pmabu as aPMABU, a.opnbr as aOPNBR, ");
				sqlString.append("a.seqnbr as aSEQNBR, a.ctcd as aCTCD, a.prnp as aPRNP, ");
				sqlString.append("a.cunbr as aCUNBR, a.citds as aCITDS, a.citgr as aCITGR, ");
				sqlString.append("a.citcl as aCITCL, a.cmabu as aCMABU, a.cnqt as aCNQT, ");
				sqlString.append("a.siqt as aSIQT, a.peun as aPEUN, a.cuwapc as aCUWAPC, ");
				sqlString.append("a.cucost as aCUCOST, a.cuamt as aCUAMT, a.compnm as aCOMPNM, ");
				sqlString.append("a.compvl as aCOMPVL, a.tx15 as aTX15, a.tx40 as aTX40, ");
				sqlString.append("a.name as aNAME, a.desc15 as aDESC15, a.desc40 as aDESC40, ");
				sqlString.append("a.rptamt as aRPTAMT, a.bdgqty as aBDGQTY, a.l1tot as aL1TOT, ");
				sqlString.append("a.l2cost as aL2COST, a.l2tot as aL2TOT, a.xrec as aXREC ");

				sqlString.append("from " + dblib + ".ZTFCST a ");
				sqlString.append("WHERE NOT EXISTS (SELECT * ");
				sqlString.append("FROM " + dblib + ".ZTFCST b ");
				sqlString.append("WHERE a.CONO = b.CONO ");
				sqlString.append("AND a.FACI = b.FACI ");
				sqlString.append("AND a.ITEM = b.ITEM ");
				sqlString.append("AND b.FYEA = " + nextFYearNE + " ");
				sqlString.append("AND b.PCTP = '" + costTypeNE + "' ");
				sqlString.append("AND a.DATA = b.DATA ");
				sqlString.append("AND a.CUNBR = b.CUNBR ");
				sqlString.append("AND a.COMPNM = b.COMPNM ");
				sqlString.append("AND b.POINT = '" + pointNE + "' ");
				sqlString.append(") ");
				sqlString.append("AND a.CONO = " + company + " ");
				//sqlString.append("AND a.FACI = '309' "); //temporary to reduce testing
				sqlString.append("AND a.PCTP = '" + costTypeE + "' ");
				sqlString.append("AND a.POINT = '" + pointE + "' ");
				sqlString.append("AND a.FYEA = " + fYearE + " ");
				sqlString.append("ORDER BY a.FACI , a.ITEM, a.DATA DESC, a.CUNBR, a.COMPNM ");
			}
			
			
			
			if (inRequestType.equals("getZtglccNonMatching"))
			{
				//extract incoming sql criteria.
				String pointNE     = (String) requestClass.elementAt(0); //point in file    (not exists)
				String nextFYearNE = (String) requestClass.elementAt(1); //next fiscal year (not exists)
				String costTypeNE  = (String) requestClass.elementAt(2); //cost type        (not exists)
				String pointE	   = (String) requestClass.elementAt(3); //point in file    (exists)
				String fYearE	   = (String) requestClass.elementAt(4); //requested year   (exists)
				String costTypeE   = (String) requestClass.elementAt(5); //cost type        (exists)
				String company	   = (String) requestClass.elementAt(6); //company			(exists)
				
				sqlString.append("select ");
				sqlString.append("a.cono as aCONO, a.faci as aFACI, a.itno as aITNO, ");
				sqlString.append("a.fyea as aFYEA, a.fdate as aFDATE, a.point as aPOINT, ");
				sqlString.append("a.cstdte as aCSTDTE, a.asfdte as aASFDTE, a.strt as aSTRT, ");
				sqlString.append("a.pctp as aPCTP, a.cmodel as aCMODEL, a.cdate as aCDATE, ");
				sqlString.append("a.ctime as aCTIME ");

				sqlString.append("from " + dblib + ".ZTGLCC a ");
				sqlString.append("WHERE NOT EXISTS (SELECT * ");
				sqlString.append("FROM " + dblib + ".ZTGLCC b ");
				sqlString.append("WHERE a.CONO = b.CONO ");
				sqlString.append("AND a.FACI = b.FACI ");
				sqlString.append("AND a.ITNO = b.ITNO ");
				sqlString.append("AND b.FYEA = " + nextFYearNE + " ");
				sqlString.append("AND b.PCTP = '" + costTypeNE + "' ");
				sqlString.append("AND b.POINT = '" + pointNE + "' ");
				sqlString.append(") ");
				sqlString.append("AND a.CONO = " + company + " ");
				sqlString.append("AND a.PCTP = '" + costTypeE + "' ");
				sqlString.append("AND a.POINT = '" + pointE + "' ");
				sqlString.append("AND a.FYEA = " + fYearE + " ");
				sqlString.append("ORDER BY a.cono, a.faci , a.itno ");
			}
			
			
			
			if (inRequestType.equals("getCspafcstNonMatching"))
			{
				//extract incoming sql criteria.
				String nextFYearNE = (String) requestClass.elementAt(0); //next fiscal year (not exists)
				String fYearE	   = (String) requestClass.elementAt(1); //requested year   (exists)
				
				sqlString.append("select ");
				sqlString.append("a.csayear as aCSAYEAR, a.csafaci as aCSAFACI, a.csaitno as aCSAITNO, ");
				sqlString.append("a.csadate as aCSADATE, a.csatime as aCSATIME ");

				sqlString.append("from " + dblib + ".CSPAFCST a ");
				sqlString.append("WHERE NOT EXISTS (SELECT * ");
				sqlString.append("FROM " + dblib + ".CSPAFCST b ");
				sqlString.append("WHERE a.csafaci = b.csafaci ");
				sqlString.append("AND a.csaitno = b.csaitno ");
				sqlString.append("AND b.csayear = " + nextFYearNE + " ");
				sqlString.append(") ");
				sqlString.append("AND a.csayear = " + fYearE + " ");
				sqlString.append("AND a.csafrom <> 'Extra For Comparison' ");
				sqlString.append("ORDER BY a.CSAYEAR , a.CSAFACI, a.CSAITNO ");
			}
			
			
			
			if (inRequestType.equals("getMccopuItemCost"))
			{
				//extract incoming sql criteria.
				String currDate  = (String) requestClass.elementAt(0); //current date
				String costType  = (String) requestClass.elementAt(1); //cost type
				String company   = (String) requestClass.elementAt(2); //company
				String startDate = (String) requestClass.elementAt(3); //start date
				String itno		 = (String) requestClass.elementAt(4); //item no
				String faci 	 = (String) requestClass.elementAt(5); //faci
				
				sqlString.append("select krmatc, krca04, krca05 ");
				sqlString.append("from " + library + ".MCCOPU ");
				sqlString.append("WHERE KRCONO = " + company + " ");
				sqlString.append("AND KRFACI = '" + faci + "' ");
				sqlString.append("AND KRITNO = '" + itno + "' ");
				sqlString.append("AND KRPCTP = '" + costType + "' ");
				sqlString.append("AND KRPCDT <= " + currDate + " ");
				sqlString.append("AND KRPCDT >= " + startDate + " ");
				sqlString.append("ORDER BY KRPCDT DESC ");
			}
			
			
			
			if (inRequestType.equals("getZtfcstByFiscalYear"))
			{
				//extract incoming sql criteria.
				String company   = (String) requestClass.elementAt(0); //company
				String fYear     = (String) requestClass.elementAt(1); //fiscal year
				
				sqlString.append("select cono, faci, item, fyea, cstdte, ");
				sqlString.append("strt, pctp, fdate, point, asfdte, rptamt, data ");
				sqlString.append("from dbprd.ZTFCST ");
				sqlString.append("WHERE CONO = " + company + " ");
				sqlString.append("AND FYEA = " + fYear + " ");
				sqlString.append("order by cono, faci, item, fyea, cstdte, strt, pctp, fdate, point, asfdte, data ");
			}
			
			
			
			//******************************************************************
			//*** INSERT SECTION
			//******************************************************************

			if (inRequestType.equals("addCostingVariance"))
			{
				ResultSet rs = (ResultSet) requestClass.elementAt(0);
				String st    = (String) requestClass.elementAt(1);
				String acdt  = (String) requestClass.elementAt(2);
				String vono  = (String) requestClass.elementAt(3);
				String coce  = (String) requestClass.elementAt(4);
				ResultSet rs2 = (ResultSet) requestClass.elementAt(5);
				String plgr = "";
				String dept = "";
				String opno = "0";
				String stsq = "0";

		   	  
				if (!rs.getString("NBDEPT").trim().equals(""))
				{
					plgr = rs.getString("NBPLGR").trim();
					dept = rs.getString("NBDEPT").trim();
					opno = rs.getString("NBOPNO");
					stsq = rs.getString("NBSTSQ");
				} else
				{
					if (rs.getString("NADEPT") != null)
					{
						plgr = rs.getString("NAPLGR").trim();
						dept = rs.getString("NADEPT").trim();
						opno = rs.getString("NAOPNO");
						stsq = rs.getString("NASTSQ");
					} else
					{
						try {
							if (rs2.getString("NADEPT") != null)
							{
								plgr = rs2.getString("NAPLGR").trim();
								dept = rs2.getString("NADEPT").trim();
								opno = rs2.getString("NAOPNO");
								stsq = rs2.getString("NASTSQ");
							}
						} catch (Exception e) {
							String x = "If it fails here don't stop. just keep going";
						}

					}
					
				}
				
				sqlString.append("INSERT INTO " + dblib + ".ZTGLCV ");
				sqlString.append("VALUES (");
				sqlString.append(rs.getString("NBCONO") + ", ");
				sqlString.append("'" + rs.getString("NBFACI").trim() + "', ");
				sqlString.append("'" + rs.getString("NBRIDN").trim() + "', ");
				sqlString.append("'" + rs.getString("NBMTNO").trim() + "', ");
				sqlString.append("'" + rs.getString("MMITTY").trim() + "', ");
				sqlString.append("'" + rs.getString("MMITGR").trim() + "', ");
				sqlString.append(rs.getBigDecimal("NBMSEQ") + ", ");
				sqlString.append(" " + opno + ", ");
				sqlString.append(rs.getBigDecimal("NBOCNR") + ", ");
				sqlString.append(rs.getBigDecimal("NBANBR") + ", ");
				sqlString.append(rs.getBigDecimal("NBTOAF") + ", ");
				sqlString.append(rs.getBigDecimal("NBTOSF") + ", ");

				BigDecimal zero = new BigDecimal("0");
				BigDecimal toaf = new BigDecimal(rs.getString("NBTOAF"));
				BigDecimal tosf = new BigDecimal(rs.getString("NBTOSF"));
				BigDecimal vari = toaf.subtract(tosf);
				BigDecimal pvri = new BigDecimal("0");

				int x = toaf.compareTo(zero);
 
				if (x != 0)
					pvri = vari.divide(toaf, 0);
				
				vari = vari.setScale(2,0);
				sqlString.append(vari.toString() + ", ");

				pvri = pvri.setScale(2,0);
				sqlString.append(pvri.toString() + ", ");

				sqlString.append(rs.getBigDecimal("TYCMRE") + ", ");
				sqlString.append("'" + st + "', ");
		      
				sqlString.append(rs.getBigDecimal("NBAIQT") + ", ");
				sqlString.append(rs.getBigDecimal("NBSIQT") + ", ");
	      
				BigDecimal aiqt = new BigDecimal(rs.getString("NBAIQT"));
				BigDecimal siqt = new BigDecimal(rs.getString("NBSIQT"));
				BigDecimal qvri = aiqt.subtract(siqt);
				sqlString.append(qvri.toString() + ", ");
		      
				sqlString.append(acdt + ", ");
				sqlString.append(vono + ", ");
				sqlString.append("'" + coce + "', ");
				sqlString.append("'" + plgr + "', ");
				sqlString.append("'" + dept + "', ");
				sqlString.append( stsq + " ");
				sqlString.append(")");
			}
			
			
			
			if (inRequestType.equals("addVarianceCostEarnings"))
			{
				ResultSet rs     = (ResultSet) requestClass.elementAt(0);
				String fYr       = (String) requestClass.elementAt(1);
				String type      = (String) requestClass.elementAt(2);
				BigDecimal value = (BigDecimal) requestClass.elementAt(3);
				
				sqlString.append("INSERT INTO " + dblib + ".ZTVCER ");
				sqlString.append("VALUES (");
				sqlString.append(rs.getString("KQCONO") + ", ");
				sqlString.append("'" + rs.getString("KQFACI").trim() + "', ");
				sqlString.append("'" + rs.getString("KQITNO").trim() + "', ");
				sqlString.append("'" + rs.getString("MMITTY").trim() + "', ");
				sqlString.append("'" + rs.getString("MMITGR").trim() + "', ");
				sqlString.append("'" + rs.getString("KQPCTP").trim() + "', ");
				sqlString.append(rs.getString("KQPCDT") + ", ");
				sqlString.append("'" + type.trim() + "', ");
				sqlString.append(value + ", ");
				sqlString.append("'" + fYr + "' ");
				
				sqlString.append(")");
			}
			
			if (inRequestType.equals("getCostingComponentLevelEntries"))
			{
				sqlString.append("SELECT * FROM " + library + ".MCCOML ");
				sqlString.append("INNER JOIN " + library + ".MITMAS ");
				sqlString.append("ON KQCONO = MMCONO ");
				sqlString.append("AND KQITNO = MMITNO ");
				sqlString.append("WHERE KQSTRT = 'STD' ");
				sqlString.append("AND KQPCTP = '3' ");
				sqlString.append("ORDER BY KQCONO, KQFACI, KQITNO, KQPCDT DESC ");
			}
			
			
			
			if (inRequestType.equals("addZTMCCOMLrecord"))
			{
				ResultSet rs     = (ResultSet) requestClass.elementAt(0);
				
				sqlString.append("INSERT INTO " + dblib + ".ZTMCCOML ");
				sqlString.append("VALUES (");
				sqlString.append(rs.getString("KQCONO") + ", ");
				sqlString.append("'" + rs.getString("KQFACI").trim() + "', ");
				sqlString.append("'" + rs.getString("KQITNO").trim() + "', ");
				sqlString.append("'" + rs.getString("MMITTY").trim() + "', ");
				sqlString.append("'" + rs.getString("MMITGR").trim() + "', ");
				sqlString.append("'" + rs.getString("KQSTRT").trim() + "', ");
				sqlString.append("'" + rs.getString("KQPCTP").trim() + "', ");
				sqlString.append(rs.getString("KQPCDT") + ", ");
				sqlString.append(rs.getString("KQCA01") + ", ");
				sqlString.append(rs.getString("KQCA02") + ", ");
				sqlString.append(rs.getString("KQCA03") + ", ");
				sqlString.append(rs.getString("KQCA04") + ", ");
				sqlString.append(rs.getString("KQCA05") + ", ");
				sqlString.append(rs.getString("KQCB01") + ", ");
				sqlString.append(rs.getString("KQCB02") + ", ");
				sqlString.append(rs.getString("KQCB03") + ", ");
				sqlString.append(rs.getString("KQCB04") + ", ");
				sqlString.append(rs.getString("KQCB05") + ", ");
				sqlString.append(rs.getString("KQCB06") + ", ");
				sqlString.append(rs.getString("KQCB07") + ", ");
				sqlString.append(rs.getString("KQCB08") + ", ");
				sqlString.append(rs.getString("KQCB09") + ", ");
				sqlString.append(rs.getString("KQCB10") + ", ");
				sqlString.append(rs.getString("KQCB11") + ", ");
				sqlString.append(rs.getString("KQCB12") + ", ");
				sqlString.append(rs.getString("KQCD01") + ", ");
				sqlString.append(rs.getString("KQCE01") + ", ");
				sqlString.append(rs.getString("KQCE02") + ", ");
				sqlString.append(rs.getString("KQCE03") + ", ");
				sqlString.append(rs.getString("KQCE04") + ", ");
				sqlString.append(rs.getString("KQCE05") + ", ");
				sqlString.append(rs.getString("KQCE06") + ", ");
				sqlString.append(rs.getString("KQCE07") + " ");
				
				sqlString.append(")");
			}
			
			
			
			if (inRequestType.equals("addVarianceEntryFGLEDG"))
			{
				ResultSet rsFGLEDG = (ResultSet) requestClass.elementAt(0);
				String level = (String) requestClass.elementAt(1);
				
				sqlString.append("INSERT INTO " + dblib + ".ZTPPVR ");
				sqlString.append("VALUES (");
				sqlString.append("'" + level + "', ");
				sqlString.append(rsFGLEDG.getString("EGCONO") + ", ");//cono
				sqlString.append("'" + rsFGLEDG.getString("EGAIT2").trim() + "', ");//faci
				sqlString.append(rsFGLEDG.getString("EGJRNO") + ", ");//jrno
				sqlString.append(rsFGLEDG.getString("EGJSNO") + ", ");//jsno
				sqlString.append("'" + rsFGLEDG.getString("EGVSER").trim() + "', ");//source
				sqlString.append("'" + rsFGLEDG.getString("EGAIT6").trim() + "', ");//puno
				sqlString.append("0, ");//plni
				sqlString.append("'" +  "', ");//itno
				sqlString.append("0, ");//pur price/unit
				sqlString.append("0, ");//inv price/unit
				sqlString.append("'" + rsFGLEDG.getString("EGAIT1").trim() + "', ");//dim1
				sqlString.append("'" + rsFGLEDG.getString("EGVTXT").trim() + "', ");//text
				sqlString.append("0, ");//accounting no
				sqlString.append(rsFGLEDG.getString("EGACDT") + ", ");//accounting date
				sqlString.append(rsFGLEDG.getString("EGCUAM") + ", ");//FGLEDG dollars
				sqlString.append("0, ");//FGINAE dollars
				sqlString.append("0, ");//CINNAC dollars
				sqlString.append("0, ");//receiving qty
				sqlString.append("0, ");//invoicing qty
				sqlString.append("0, ");//std cost/unit
				sqlString.append("0, ");//pur variance/unit
				sqlString.append("0, ");//inv variance/unit
				sqlString.append("0, ");//standard dollars
				sqlString.append("0, ");//invoice dollars
				sqlString.append("0 ");//purchase dollars
				sqlString.append(")");
			}
			
			
			
			if (inRequestType.equals("addVarianceEntryCINACC"))
			{
				ResultSet rsCINACC = (ResultSet) requestClass.elementAt(0);
				String level = (String) requestClass.elementAt(1);
				BigDecimal stdPrice = (BigDecimal) requestClass.elementAt(2);
				BigDecimal purPrice = (BigDecimal) requestClass.elementAt(3);
				BigDecimal difVar   = (BigDecimal) requestClass.elementAt(4);
				BigDecimal qty      = (BigDecimal) requestClass.elementAt(5);
				BigDecimal stdDlr	= (BigDecimal) requestClass.elementAt(6);
				BigDecimal purDlr	= (BigDecimal) requestClass.elementAt(7);
				
				sqlString.append("INSERT INTO " + dblib + ".ZTPPVR ");
				sqlString.append("VALUES (");
				sqlString.append("'" + level + "', ");
				sqlString.append(rsCINACC.getString("EZCONO") + ", ");//cono
				sqlString.append("'" + rsCINACC.getString("EZFACI").trim() + "', ");//faci
				sqlString.append("0, ");//jrno
				sqlString.append("0, ");//jsno
				sqlString.append("'', ");//source
				sqlString.append("'" + rsCINACC.getString("EZAIT6").trim() + "', ");//puno
				sqlString.append("'" + rsCINACC.getString("EZITNO").trim() + "', ");//itno
				sqlString.append(purPrice.toString() + ", ");//pur price/unit
				sqlString.append("0, ");//inv price/unit
				sqlString.append("'" + rsCINACC.getString("EZAIT1").trim() + "', ");//dim1
				sqlString.append("'" + "', ");//text
				sqlString.append(rsCINACC.getString("EZANBR") +  ", ");//accounting no
				sqlString.append(rsCINACC.getString("EZACDT") + ", ");//accounting date
				sqlString.append("0, ");//FGLEDG dollars
				sqlString.append("0, ");//FGINAE dollars
				sqlString.append(rsCINACC.getString("EZACAM") + ", ");//CINACC dollars
				sqlString.append(qty.toString() + ", ");//receiving qty
				sqlString.append("0, ");//invoicing qty
				sqlString.append(stdPrice.toString() + ", ");//std cost/unit
				sqlString.append(difVar.toString() + ", ");//pur variance/unit
				sqlString.append("0, ");//inv variance/unit
				sqlString.append(stdDlr.toString() + ", ");//standard dollars
				sqlString.append("0, ");//invoice dollars
				sqlString.append(purDlr.toString() + " ");
				sqlString.append(")");
			}
			
			
			
			if (inRequestType.equals("addVarianceEntryFGINAE"))
			{
				ResultSet rsFGINAE = (ResultSet) requestClass.elementAt(0);
				String level = (String) requestClass.elementAt(1);
				BigDecimal priceVar = (BigDecimal) requestClass.elementAt(2);
				BigDecimal invDlr	= (BigDecimal) requestClass.elementAt(3);
				BigDecimal invPrice = (BigDecimal) requestClass.elementAt(4);
				BigDecimal stdDlr	= (BigDecimal) requestClass.elementAt(5);
				BigDecimal stdPrice = (BigDecimal) requestClass.elementAt(6);
				
				sqlString.append("INSERT INTO " + dblib + ".ZTPPVR ");
				sqlString.append("VALUES (");
				sqlString.append("'" + level + "', ");
				sqlString.append(rsFGINAE.getString("F9CONO") + ", ");//cono
				sqlString.append("'" + rsFGINAE.getString("F9AIT2").trim() + "', ");//faci
				sqlString.append("0, ");//jrno
				sqlString.append("0, ");//jsno
				sqlString.append("'', ");//source
				sqlString.append("'" + rsFGINAE.getString("F9AIT6").trim() + "', ");//puno
				sqlString.append("'', ");//itno
				sqlString.append("0, ");//pur price/unit
				sqlString.append(invPrice.toString() + ", ");//inv price/unit
				sqlString.append("'" + rsFGINAE.getString("F9AIT1").trim() + "', ");//dim1
				sqlString.append("'', ");//text
				sqlString.append("0, ");//accounting no
				sqlString.append(rsFGINAE.getString("F9ACDT") + ", ");//accounting date
				sqlString.append("0, ");//FGLEDG dollars
				sqlString.append(rsFGINAE.getString("F9CUAM") + ", ");//FGINAE dollars
				sqlString.append("0, ");//CINNAC dollars
				sqlString.append("0, ");//receiving qty
				sqlString.append(rsFGINAE.getString("F9ACQT") + ", ");//invoicing qty
				sqlString.append(stdPrice.toString() + ", ");//std cost/unit
				sqlString.append("0, ");//pur variance/unit
				sqlString.append(priceVar.toString() + ", ");//inv variance/unit
				sqlString.append(stdDlr.toString() + ", ");//standard dollars
				sqlString.append(invDlr.toString() + " ");
				sqlString.append(")");
			}
			
			
			
			if (inRequestType.equals("addZtglcc"))
			{
				String fiscalYear = (String) requestClass.elementAt(0);
				String fiscalDate = (String) requestClass.elementAt(1);
				String startEnd   = (String) requestClass.elementAt(2);
				String asOfDate   = (String) requestClass.elementAt(3);
				ResultSet rs      = (ResultSet) requestClass.elementAt(4);
				String chgDate    = (String) requestClass.elementAt(5);
				String chgTime    = (String) requestClass.elementAt(6);
				
				sqlString.append("INSERT INTO " + dblib + ".ZTGLCC ");
				sqlString.append("VALUES (");
				sqlString.append(rs.getString("KQCONO") + ", ");//company
				sqlString.append("'" + rs.getString("KQFACI") + "', ");
				sqlString.append("'" + rs.getString("KQITNO") + "', ");//item
				sqlString.append(fiscalYear + ", ");//fiscal year yyyy
				sqlString.append(fiscalDate + ", ");//fiscal date yyyymmdd
				sqlString.append("'" + startEnd + "', ");//point (start/end)
				sqlString.append(rs.getString("KQPCDT") + ", ");//costing date
				sqlString.append(asOfDate + ", ");//as of date.
				sqlString.append("'" + rs.getString("KQSTRT") + "', ");//structure type
				sqlString.append("'" + rs.getString("KQPCTP") + "', ");//cost type
				sqlString.append("'', ");//cost model structure
				sqlString.append("0, ");//costing model total
				sqlString.append(rs.getString("KQCA01") + ", ");//cost component
				sqlString.append(rs.getString("KQCA02") + ", ");//cost component
				sqlString.append(rs.getString("KQCA03") + ", ");//cost component
				sqlString.append(rs.getString("KQCA04") + ", ");//cost component
				sqlString.append(rs.getString("KQCA05") + ", ");//cost component
				sqlString.append(rs.getString("KQCB01") + ", ");//cost component
				sqlString.append(rs.getString("KQCB02") + ", ");//cost component
				sqlString.append(rs.getString("KQCB03") + ", ");//cost component
				sqlString.append(rs.getString("KQCB04") + ", ");//cost component
				sqlString.append(rs.getString("KQCB05") + ", ");//cost component
				sqlString.append(rs.getString("KQCB06") + ", ");//cost component
				sqlString.append(rs.getString("KQCB07") + ", ");//cost component
				sqlString.append(rs.getString("KQCB08") + ", ");//cost component
				sqlString.append(rs.getString("KQCB09") + ", ");//cost component
				sqlString.append(rs.getString("KQCB10") + ", ");//cost component
				sqlString.append(rs.getString("KQCB11") + ", ");//cost component
				sqlString.append(rs.getString("KQCB12") + ", ");//cost component
				sqlString.append(rs.getString("KQCB13") + ", ");//cost component
				sqlString.append(rs.getString("KQCB14") + ", ");//cost component
				sqlString.append(rs.getString("KQCC01") + ", ");//cost component
				sqlString.append(rs.getString("KQCC02") + ", ");//cost component
				sqlString.append(rs.getString("KQCD01") + ", ");//cost component
				sqlString.append(rs.getString("KQCD02") + ", ");//cost component
				sqlString.append(rs.getString("KQCE01") + ", ");//cost component
				sqlString.append(rs.getString("KQCE02") + ", ");//cost component
				sqlString.append(rs.getString("KQCE03") + ", ");//cost component
				sqlString.append(rs.getString("KQCE04") + ", ");//cost component
				sqlString.append(rs.getString("KQCE05") + ", ");//cost component
				sqlString.append(rs.getString("KQCE06") + ", ");//cost component
				sqlString.append(rs.getString("KQCE07") + ", ");//cost component
				sqlString.append(rs.getString("KQCE08") + ", ");//cost component
				sqlString.append(rs.getString("KQCE09") + ", ");//cost component
				sqlString.append(rs.getString("KQCE10") + ", ");//cost component
				sqlString.append(rs.getString("KQSSU1") + ", ");//cost component
				sqlString.append(rs.getString("KQSSU2") + ", ");//cost component
				sqlString.append(rs.getString("KQSSU3") + ", ");//cost component
				sqlString.append("0, ");//total for all components in record
				sqlString.append(chgDate + ", ");//change date
				sqlString.append(chgTime + ") ");//change time
				
			}
			
			
			
			if (inRequestType.equals("addZtglccse"))
			{
				ResultSet rs = (ResultSet) requestClass.elementAt(0);
				StringBuffer firstPart = new StringBuffer();
				StringBuffer lastPart  = new StringBuffer();
				
				sqlString.append("INSERT INTO " + dblib + ".ZTGLCCSE ");
				sqlString.append("VALUES ");
				firstPart.append("(" + rs.getString("CONO") + ", ");//company
				firstPart.append("'" + rs.getString("FACI") + "', ");//facility
				firstPart.append("'" + rs.getString("ITNO") + "', ");//item
				firstPart.append(rs.getString("FYEA") + ", ");//fiscal year yyyy
				firstPart.append(rs.getString("FDATE") + ", ");//fiscal date yyyymmdd
				firstPart.append("'" + rs.getString("POINT") + "', ");//point (start/end)
				firstPart.append(rs.getString("CSTDTE") + ", ");//costing date
				firstPart.append(rs.getString("ASFDTE") + ", ");//as of date.
				firstPart.append("'" + rs.getString("STRT") + "', ");//structure type
				firstPart.append("'" + rs.getString("PCTP") + "', ");//cost type
				
				lastPart.append("'',");//component 15
				lastPart.append("'',");//component 40
				lastPart.append(rs.getString("CDATE") + ", ");//change date
				lastPart.append(rs.getString("CTIME") + ") ");//change time
				
				sqlString.append(firstPart.toString());
				sqlString.append("'A01', ");//component name
				sqlString.append(rs.getString("A01") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'A02', ");//component name
				sqlString.append(rs.getString("A02") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'A03', ");//component name
				sqlString.append(rs.getString("A03") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'A04', ");//component name
				sqlString.append(rs.getString("A04") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'A05', ");//component name
				sqlString.append(rs.getString("A05") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B01', ");//component name
				sqlString.append(rs.getString("B01") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B02', ");//component name
				sqlString.append(rs.getString("B02") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B03', ");//component name
				sqlString.append(rs.getString("B03") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B04', ");//component name
				sqlString.append(rs.getString("B04") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B05', ");//component name
				sqlString.append(rs.getString("B05") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B06', ");//component name
				sqlString.append(rs.getString("B06") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B07', ");//component name
				sqlString.append(rs.getString("B07") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B08', ");//component name
				sqlString.append(rs.getString("B08") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B09', ");//component name
				sqlString.append(rs.getString("B09") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B10', ");//component name
				sqlString.append(rs.getString("B10") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B11', ");//component name
				sqlString.append(rs.getString("B11") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B12', ");//component name
				sqlString.append(rs.getString("B12") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B13', ");//component name
				sqlString.append(rs.getString("B13") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'B14', ");//component name
				sqlString.append(rs.getString("B14") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'C01', ");//component name
				sqlString.append(rs.getString("C01") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'C02', ");//component name
				sqlString.append(rs.getString("C02") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'D01', ");//component name
				sqlString.append(rs.getString("D01") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'D02', ");//component name
				sqlString.append(rs.getString("D02") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'E01', ");//component name
				sqlString.append(rs.getString("E01") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'E02', ");//component name
				sqlString.append(rs.getString("E02") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'E03', ");//component name
				sqlString.append(rs.getString("E03") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'E04', ");//component name
				sqlString.append(rs.getString("E04") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'E05', ");//component name
				sqlString.append(rs.getString("E05") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'E06', ");//component name
				sqlString.append(rs.getString("E06") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'E07', ");//component name
				sqlString.append(rs.getString("E07") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'E08', ");//component name
				sqlString.append(rs.getString("E08") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'E09', ");//component name
				sqlString.append(rs.getString("E09") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'E10', ");//component name
				sqlString.append(rs.getString("E10") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'SU1', ");//component name
				sqlString.append(rs.getString("SU1") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'SU2', ");//component name
				sqlString.append(rs.getString("SU2") + ", ");
				sqlString.append(lastPart.toString() );
				sqlString.append(", ");
				
				sqlString.append(firstPart.toString());
				sqlString.append("'SU3', ");//component name
				sqlString.append(rs.getString("SU3") + ", ");
				sqlString.append(lastPart.toString() );
				
			}
			
			
			
			if (inRequestType.equals("addciToFcst"))
			{
				String fiscalYear = (String) requestClass.elementAt(0);
				String fiscalDate = (String) requestClass.elementAt(1);
				String startEnd   = (String) requestClass.elementAt(2);
				String asOfDate   = (String) requestClass.elementAt(3);
				ResultSet rs      = (ResultSet) requestClass.elementAt(4);
				String chgDate    = (String) requestClass.elementAt(5);
				String chgTime    = (String) requestClass.elementAt(6);
				String data       = (String) requestClass.elementAt(7);
				String krmatc	  = (String) requestClass.elementAt(8);
				
				sqlString.append("INSERT INTO " + dblib + ".ZTFCST ");
				sqlString.append("VALUES (");
				sqlString.append(rs.getString("KUCONO") + ", ");//company
				sqlString.append("'" + rs.getString("KUFACI") + "', ");//facility
				sqlString.append("'" + rs.getString("KUITNO") + "', ");//item
				sqlString.append(fiscalYear + ", ");//fiscal year yyyy
				sqlString.append(rs.getString("KUPCDT") + ", ");//costing date
				sqlString.append("'" + rs.getString("KUSTRT") + "', ");//structure type
				sqlString.append("'" + rs.getString("KUPCTP") + "', ");//cost type
				sqlString.append(fiscalDate + ", ");//fiscal date yyyymmdd
				sqlString.append("'" + startEnd + "', ");//point (start/end)
				sqlString.append(asOfDate + ", ");//as of date
				sqlString.append("'" + data + "', ");//data (CONSUMED/COMPONENT)
				sqlString.append(chgDate + ", ");//change date
				sqlString.append(chgTime + ", ");//change time
				
				//Produced Item Name PITDS.
				if (rs.getString("aMMITDS") == null)
				{
					sqlString.append("'', ");//Produced Item Description
					sqlString.append("'', ");//item group
					sqlString.append("'', ");//product group
					sqlString.append("0, ");//Make/buy code.
					
				} else {
					sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("aMMITDS").trim()) + "', ");//Produced Item Desc
					sqlString.append("'" + rs.getString("aMMITGR") + "', ");//item group
					sqlString.append("'" + rs.getString("aMMITCL") + "', ");//product group
					sqlString.append(rs.getString("aMMMABU") + ", ");//make/buy code
				}
				
				sqlString.append(rs.getString("KUOPNO") + ", ");//operation no
				sqlString.append(rs.getString("KUMSEQ") + ", ");//sequence no
				sqlString.append("0, ");//price and time qty
				sqlString.append("0, ");//planned no of workers
				sqlString.append("'" + rs.getString("KUMTNO").trim() + "', ");//consumed Item no
				
				//consumed Item Name CITDS.
				if (rs.getString("bMMITDS") == null)
				{
					sqlString.append("'', ");//consumed Item Description
					sqlString.append("'', ");//item group
					sqlString.append("'', ");//product group
					sqlString.append("0, ");//make/buy code
					
				} else {
					sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("bMMITDS").trim()) + "', ");//consumed Item Desc
					sqlString.append("'" + rs.getString("bMMITGR") + "', ");//item group
					sqlString.append("'" + rs.getString("bMMITCL") + "', ");//product group
					sqlString.append(rs.getString("bMMMABU") + ", ");//make/buy code
				}
				
				sqlString.append(rs.getString("KUCNQT") + ", ");//consumed qty
				sqlString.append(rs.getString("KUSIQT") + ", ");//consumed issued qty
				sqlString.append("'" + rs.getString("KUPEUN").trim() + "', "); 
				sqlString.append(rs.getString("KUWAPC") + ", ");//consumed Shrink
				
				//cost CUCOST
				sqlString.append(krmatc + ", ");//cost
				BigDecimal cst = new BigDecimal(krmatc);
				BigDecimal qty = new BigDecimal(rs.getString("KUSIQT"));
				BigDecimal amt = qty.multiply(cst);
				amt = amt.setScale(4, RoundingMode.HALF_UP);
				sqlString.append(amt.toString()+ ", ");//calc amount
				
								
				//empty component fields.
				sqlString.append("'', ");//component name
				sqlString.append("0, ");//component value
				sqlString.append("'', ");//component definition 15
				sqlString.append("'', ");//component definition 40
				
				//Report fields to share.
				sqlString.append("'" + rs.getString("KUMTNO").trim() + "', ");//consumed Item no
				
				//consumed Item Name CITDS.
				if (rs.getString("bMMITDS") == null)
				{
					sqlString.append("'', ");//consumed Item Description
					sqlString.append("'', ");//consumed Item Description
				} else {
					String miniMe = FindAndReplace.replaceApostrophe(rs.getString("bMMITDS"));
					miniMe = miniMe.replaceAll("'", " ");
					miniMe = miniMe.substring(0, 15);
					sqlString.append("'" + miniMe.trim() + "', ");//consumed Item Desc
					sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("bMMITDS").trim()) + "', ");//consumed Item Desc
				}
				
				//cost amount field also into report amount field.
				sqlString.append(amt.toString()+ ", ");
				
				
				//additional fileds.
				sqlString.append("0, 0, 0, 0, '') ");
			}
			
			
			
			if (inRequestType.equals("addseToFcst"))
			{
				ResultSet rs      = (ResultSet) requestClass.elementAt(0);
				String data       = (String) requestClass.elementAt(1);
				
				sqlString.append("INSERT INTO " + dblib + ".ZTFCST ");
				sqlString.append("VALUES (");
				sqlString.append(rs.getString("CONO") + ", ");//company
				sqlString.append("'" + rs.getString("FACI") + "', ");//facility
				sqlString.append("'" + rs.getString("ITNO") + "', ");//item
				sqlString.append(rs.getString("FYEA") + ", ");//fiscal year yyyy
				sqlString.append(rs.getString("CSTDTE") + ", ");//costing date
				sqlString.append("'" + rs.getString("STRT") + "', ");//structure type
				sqlString.append("'" + rs.getString("PCTP") + "', ");//cost type
				sqlString.append(rs.getString("FDATE") + ", ");//fiscal date yyyymmdd
				sqlString.append("'" + rs.getString("POINT") + "', ");//point (start/end)
				sqlString.append(rs.getString("ASFDTE") + ", ");//as of date
				sqlString.append("'" + data + "', ");//data (CONSUMED/COMPONENT)
				sqlString.append(rs.getString("CDATE") + ", ");//change date
				sqlString.append(rs.getString("CTIME") + ", ");//change time
				
				//Produced Item Name PITDS.
				if (rs.getString("MMITDS") == null)
				{
					sqlString.append("'', ");//Produced Item Description
					sqlString.append("'', ");//item group
					sqlString.append("'', ");//product group
					sqlString.append("0, ");//Make/buy code.
					
				} else {
					sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("MMITDS").trim()) + "', ");//Produced Item Desc
					sqlString.append("'" + rs.getString("MMITGR") + "', ");//item group
					sqlString.append("'" + rs.getString("MMITCL") + "', ");//product group
					sqlString.append(rs.getString("MMMABU") + ", ");//make/buy code
				}
				
				sqlString.append("0, ");//operation no
				sqlString.append("0, ");//sequence no
				sqlString.append("0, ");//price and time qty
				sqlString.append("0, ");//planned no of workers
				sqlString.append("'', ");//consumed Item no
				sqlString.append("'', ");//consumed Item Description
				sqlString.append("'', ");//item group
				sqlString.append("'', ");//product group
				sqlString.append("0, ");//make/buy code
				sqlString.append("0, ");//consumed qty
				sqlString.append("0, ");//consumed issued qty
				sqlString.append("'', "); 
				sqlString.append("0, ");//consumed Shrink
				sqlString.append("0, ");//cost
				sqlString.append("0, ");//calc amount
				
				sqlString.append("'" + rs.getString("COMPNM") + "', ");//component name
				sqlString.append(rs.getString("COMPVL") + ", ");//component value
				sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("TX15")) + "', ");//component definition 15
				sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("TX40")) + "', ");//component definition 40
				
				//Report fields to share.
				sqlString.append("'" + rs.getString("COMPNM").trim() + "', ");//component name
				sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("TX15").trim()) + "', ");//component desc 15
				sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("TX40").trim()) + "', ");//component desc 40
				sqlString.append(rs.getString("COMPVL") +  ", ");
				
				//additional fileds.
				sqlString.append("0, 0, 0, 0, '') ");
			}
			
			
			
			if (inRequestType.equals("addEmptyToZtfcst"))
			{
				ResultSet rs      = (ResultSet) requestClass.elementAt(0);
				String nextFYear  = (String) requestClass.elementAt(1);
				String point	  = (String) requestClass.elementAt(2);
				String fiscalDate = (String) requestClass.elementAt(3);
				String costType   = (String) requestClass.elementAt(4);
				
				sqlString.append("INSERT INTO " + dblib + ".ZTFCST ");
				sqlString.append("VALUES (");
				sqlString.append(rs.getString("aCONO") + ", ");//company
				sqlString.append("'" + rs.getString("aFACI") + "', ");//facility
				sqlString.append("'" + rs.getString("aITEM") + "', ");//item
				sqlString.append(nextFYear +  ", ");//fiscal year yyyy
				sqlString.append(fiscalDate + ", ");//costing date
				sqlString.append("'" + rs.getString("aSTRT") + "', ");//structure type
				sqlString.append("'" + costType + "', ");//cost type
				sqlString.append(fiscalDate + ", ");//fiscal date yyyymmdd
				sqlString.append("'" + point + "', ");//point (start/end)
				sqlString.append("0, ");//as of date
				sqlString.append("'" + rs.getString("aDATA") + "', ");//data (CONSUMED/COMPONENT)
				sqlString.append(rs.getString("aCDATE") + ", ");//change date
				sqlString.append(rs.getString("aCTIME") + ", ");//change time
				sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("aPITDS")) + "', ");//produced item name
				sqlString.append("'" + rs.getString("aPITGR") + "', ");//produced item group
				sqlString.append("'" + rs.getString("aPITCL") + "', ");//produced product group
				sqlString.append(rs.getString("aPMABU") + ", ");
				sqlString.append(rs.getString("aOPNBR") + ", ");//operation no
				sqlString.append(rs.getString("aSEQNBR") + ", ");//sequence no
				sqlString.append("0, ");//price and time qty
				sqlString.append("0, ");//planned no of workers
				sqlString.append("'" + rs.getString("aCUNBR") + "', ");//consumed Item no
				sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("aCITDS")) + "', ");//consumed Item Description
				sqlString.append("'" + rs.getString("aCITGR") + "', ");//item group
				sqlString.append("'" + rs.getString("aCITCL") + "', ");//product group
				sqlString.append(rs.getString("aCMABU") + ", ");//make/buy code
				sqlString.append("0, ");//consumed qty
				sqlString.append("0, ");//consumed issued qty
				sqlString.append("'" + rs.getString("aPEUN") + "', ");//consumed uom 
				sqlString.append(rs.getString("aCUWAPC") + ", ");//consumed Shrink
				sqlString.append("0, ");//cost
				sqlString.append("0, ");//calc amount
				sqlString.append("'" + rs.getString("aCOMPNM") + "', ");//component name
				sqlString.append("0, ");//component value
				sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("aTX15")) + "', ");//component definition 15
				sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("aTX40")) + "', ");//component definition 40
				
				//Report fields to share.
				sqlString.append("'" + rs.getString("aNAME").trim() + "', ");//component name
				sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("aDESC15").trim()) + "', ");//component desc 15
				sqlString.append("'" + FindAndReplace.replaceApostrophe(rs.getString("aDESC40").trim()) + "', ");//component desc 40
				sqlString.append("0, ");//report amount
				
				//additional fileds.
				sqlString.append("0, ");//budget quantity
				sqlString.append("0, ");//BDGQTY * RPTAMT
				sqlString.append("0, ");//item level cost
				sqlString.append("0, ");//BDGQTY * L2COST
				sqlString.append("1 )");//set to "1" as this extra record.
			}
			
			
			
			if (inRequestType.equals("addEmptyToZtglcc"))
			{
				ResultSet rs      = (ResultSet) requestClass.elementAt(0);
				String nextFYear  = (String) requestClass.elementAt(1);
				String point	  = (String) requestClass.elementAt(2);
				String fiscalDate = (String) requestClass.elementAt(3);
				String costType   = (String) requestClass.elementAt(4);
				
				sqlString.append("INSERT INTO " + dblib + ".ZTGLCC ");
				sqlString.append("VALUES (");
				sqlString.append(rs.getString("aCONO") + ", ");//company
				sqlString.append("'" + rs.getString("aFACI") + "', ");//facility
				sqlString.append("'" + rs.getString("aITNO") + "', ");//item
				sqlString.append(nextFYear +  ", ");//fiscal year yyyy
				sqlString.append(fiscalDate + ", ");//fiscal date yyyymmdd
				sqlString.append("'" + point + "', ");//point (start/end)
				sqlString.append("0, ");//costing date
				sqlString.append("0, ");//as of date
				sqlString.append("'" + rs.getString("aSTRT") + "', ");//structure type
				sqlString.append("'" + costType + "', ");//cost type
				sqlString.append("'" + rs.getString("aCMODEL") + "', ");
				sqlString.append("0, ");//costing model total
				sqlString.append("0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ");//cost component values
				sqlString.append("0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ");//cost component values
				sqlString.append("0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ");//cost component values
				sqlString.append("0, 0, 0, 0, 0, 0, ");//cost component values
				sqlString.append("0, ");//total all components
				sqlString.append("0, ");//change date
				sqlString.append("999991 )");//set to 999991 as this extra record.
			}
			
			
			
			if (inRequestType.equals("addEmptyToCspafcst"))
			{
				ResultSet rs      = (ResultSet) requestClass.elementAt(0);
				String nextFYear  = (String) requestClass.elementAt(1);
				
				sqlString.append("INSERT INTO " + dblib + ".CSPAFCST ");
				sqlString.append("VALUES (");
				sqlString.append(nextFYear + ", '', ");
				sqlString.append("'" + rs.getString("aCSAFACI") + "', ");//facility
				sqlString.append("'" + rs.getString("aCSAITNO") + "', ");//item
				sqlString.append("0, ");//quantity to zero
				sqlString.append(rs.getString("aCSADATE") + ", ");//date loaded
				sqlString.append(rs.getString("aCSATIME") + ", ");//time loaded
				sqlString.append("'Extra For Comparison') ");//date from where
			}
			
			
			
			if (inRequestType.equals("addZtfcsttl"))
			{
				String cono   = (String) requestClass.elementAt(0);
				String faci   = (String) requestClass.elementAt(1);
				String item	  = (String) requestClass.elementAt(2);
				String fyea   = (String) requestClass.elementAt(3);
				String cstdte = (String) requestClass.elementAt(4);
				String strt   = (String) requestClass.elementAt(5);
				String pctp   = (String) requestClass.elementAt(6);
				String fdate  = (String) requestClass.elementAt(7);
				String point  = (String) requestClass.elementAt(8);
				String asfdte = (String) requestClass.elementAt(9);
				BigDecimal tot = (BigDecimal) requestClass.elementAt(10);
				
				sqlString.append("INSERT INTO " + dblib + ".ZTFCSTTL ");
				sqlString.append("VALUES (");
				sqlString.append(cono + ", ");//company
				sqlString.append("'" + faci + "', ");//facility
				sqlString.append("'" + item + "', ");//item
				sqlString.append(fyea +  ", ");//fiscal year yyyy
				sqlString.append(cstdte + ", ");//costing date yyyymmdd
				sqlString.append("'" + strt + "', ");//product structure type
				sqlString.append("'" + pctp + "', ");//costing type
				sqlString.append(fdate + ", ");//fiscal date
				sqlString.append("'" + point + "', ");//point (start/end)
				sqlString.append(asfdte + ", ");//as of date
				sqlString.append(tot.toString() + ")");//rpt amount
			}
			
			
			
			//******************************************************************
			//*** UPDATE SECTION
			//******************************************************************
			
			
			
			if (inRequestType.equals("updateZtglccseText"))
			{
				ResultSet rs = (ResultSet) requestClass.elementAt(0);
				
				sqlString.append("UPDATE " + dblib + ".ZTGLCCSE ");
				sqlString.append("SET TX40 = '" + rs.getString("KCTX40") + "', ");
				sqlString.append("TX15 = '" + rs.getString("KCTX15") + "' ");
				sqlString.append("WHERE CONO = " + rs.getString("KCCONO") + " ");//company
				sqlString.append("AND COMPNM = '" + rs.getString("KCCCOM") + "' ");//component name
			}
			
			
			
			if (inRequestType.equals("updateZtglcc"))
			{
				ResultSet rs     = (ResultSet) requestClass.elementAt(0);
				BigDecimal mdlTl = (BigDecimal) requestClass.elementAt(1);
				BigDecimal all   = (BigDecimal) requestClass.elementAt(2);
				
				sqlString.append("UPDATE " + dblib + ".ZTGLCC ");
				sqlString.append("SET CMODEL = '" + rs.getString("KOPRCM").trim() + "', "); //Costing Model Structure used
				sqlString.append("CMTOT = " + mdlTl.toString() + ", ");//total for Costing Model Structure
				sqlString.append("TOTALL = " + all.toString() + " ");//total for all components
				sqlString.append("WHERE CONO = " + rs.getString("CONO") + " ");//company
				sqlString.append("AND FACI = '" + rs.getString("FACI") + "' ");//facility
				sqlString.append("AND ITNO = '" + rs.getString("ITNO") + "' ");//item no
				sqlString.append("AND FYEA = " + rs.getString("FYEA") + " ");//fiscal year
				sqlString.append("AND FDATE = " + rs.getString("FDATE") + " ");//fiscal date
				sqlString.append("AND POINT = '" + rs.getString("POINT") + "' ");//point start/end
				sqlString.append("AND CSTDTE = " + rs.getString("CSTDTE") + " ");//costing date
				sqlString.append("AND ASFDTE = " + rs.getString("ASFDTE") + " ");//as of date
				sqlString.append("AND STRT = '" + rs.getString("STRT") + "' ");//prod struct type
				sqlString.append("AND PCTP = '" + rs.getString("PCTP") + "' ");//costing type
			}
			
			
			
			if (inRequestType.equals("updateZtfcst"))
			{
				ResultSet rs     = (ResultSet) requestClass.elementAt(0);
				BigDecimal cost  = (BigDecimal) requestClass.elementAt(1);
				BigDecimal total = (BigDecimal) requestClass.elementAt(2);
				
				sqlString.append("UPDATE " + dblib + ".ZTFCST ");
				sqlString.append("SET CUCOST = " + cost.toString() + ", "); //consumed Cost
				sqlString.append("CUAMT = " + total.toString() + ", ");
				sqlString.append("RPTAMT = " + total.toString() + " ");
				sqlString.append("WHERE CONO = " + rs.getString("aCONO") + " ");//company
				sqlString.append("AND FACI = '" + rs.getString("aFACI") + "' ");//facility
				sqlString.append("AND ITEM = '" + rs.getString("aITEM") + "' ");//produced item no
				sqlString.append("AND STRT = '" + rs.getString("aSTRT") + "' ");//produced structure type
				sqlString.append("AND PCTP = '" + rs.getString("aPCTP") + "' ");//produced costing type
				sqlString.append("AND CSTDTE = " + rs.getString("aCSTDTE") + " ");//costing date
				sqlString.append("AND OPNBR = " + rs.getString("aOPNBR") + " ");//operation number
				sqlString.append("AND SEQNBR = " + rs.getString("aSEQNBR") + " ");//sequence number
				sqlString.append("AND FYEA = " + rs.getString("aFYEA") + " ");//fiscal year
				sqlString.append("AND FDATE = " + rs.getString("aFDATE") + " ");//fiscal date
				sqlString.append("AND POINT = '" + rs.getString("aPOINT") + "' ");//point (start/end)
				sqlString.append("AND CUNBR = '" + rs.getString("aCUNBR") + "' ");//consumed item no
			}
			
			
			
			if (inRequestType.equals("updateZtfcstOperationsData"))
			{
				ResultSet rs     = (ResultSet) requestClass.elementAt(0);
				BigDecimal ctcd  = (BigDecimal) requestClass.elementAt(1);
				BigDecimal prnp  = (BigDecimal) requestClass.elementAt(2);
				
				sqlString.append("UPDATE " + dblib + ".ZTFCST ");
				sqlString.append("SET CTCD = " + ctcd.toString() + ", "); //price and time qty
				sqlString.append("PRNP = " + prnp.toString() + " ");//planned no of workers
				sqlString.append("WHERE CONO = " + rs.getString("CONO") + " ");//company
				sqlString.append("AND FACI = '" + rs.getString("FACI") + "' ");//facility
				sqlString.append("AND ITEM = '" + rs.getString("ITEM") + "' ");//produced item no
				sqlString.append("AND STRT = '" + rs.getString("STRT") + "' ");//produced structure type
				sqlString.append("AND PCTP = '" + rs.getString("PCTP") + "' ");//produced costing type
				sqlString.append("AND CSTDTE = " + rs.getString("CSTDTE") + " ");//costing date
				sqlString.append("AND FYEA = " + rs.getString("FYEA") + " ");//fiscal year
				sqlString.append("AND POINT = '" + rs.getString("POINT") + "' ");//point (start/end)
			}
			
			
			
			if (inRequestType.equals("updateZtfcstBudgetData"))
			{
				String    company = (String) requestClass.elementAt(0);
				ResultSet rs      = (ResultSet) requestClass.elementAt(1);
				
				sqlString.append("UPDATE " + dblib + ".ZTFCST ");
				sqlString.append("SET BDGQTY = " + rs.getString("CSAFQTY") + " "); //budget quantity
				sqlString.append("WHERE CONO = " + company + " ");//company
				sqlString.append("AND FACI = '" + rs.getString("CSAFACI") + "' ");//facility
				sqlString.append("AND ITEM = '" + rs.getString("CSAITNO") + "' ");//produced item no
				sqlString.append("AND FYEA = " + rs.getString("CSAYEAR") + " ");
			}
			
			
			
			//******************************************************************
			//*** DELETE SECTION
			//******************************************************************
			
			
			
			if (inRequestType.equals("deleteCostingVarianceFile"))
			{
				sqlString.append("DELETE FROM " + dblib + ".ZTGLCV ");
			}
			
			
			
			if (inRequestType.equals("deletePurchasePriceVarianceFile"))
			{
				String level = (String) requestClass.elementAt(0);
				
				sqlString.append("DELETE FROM " + dblib + ".ZTPPVR ");
				sqlString.append("WHERE LEVEL = '" + level + "' ");
			}
			
			
			
			if (inRequestType.equals("deleteVarianceCostEarnings"))
			{
				sqlString.append("DELETE FROM " + dblib + ".ZTVCER ");
			}
			
			
			
			if (inRequestType.equals("deleteCostComponentEntries"))
			{
				String date     = (String) requestClass.elementAt(0);
				String costType = (String) requestClass.elementAt(1);
				String company  = (String) requestClass.elementAt(2);
				String point    = (String) requestClass.elementAt(3);
				
				sqlString.append("DELETE FROM " + dblib + ".ZTGLCC ");
				sqlString.append("WHERE FDATE = " + date + " ");
				sqlString.append("AND PCTP = '" + costType + "' ");
				sqlString.append("AND CONO = " + company + " ");
				sqlString.append("AND POINT = '" + point + "' ");
			}
			
			
			
			if (inRequestType.equals("deleteCostComponentZTFCST"))
			{
				String company    = (String) requestClass.elementAt(0);
				String fiscalYear = (String) requestClass.elementAt(1);
				
				sqlString.append("DELETE FROM " + dblib + ".ZTFCST ");
				sqlString.append("WHERE FYEA = " + fiscalYear + " ");
				sqlString.append("AND CONO = " + company + " ");
				sqlString.append("AND DATA = 'COMPONENT' ");
			}
			
			
			
			if (inRequestType.equals("deleteCostComponentZTGLCCSE"))
			{
				String company    = (String) requestClass.elementAt(0);
				String fiscalYear = (String) requestClass.elementAt(1);
				
				sqlString.append("DELETE FROM " + dblib + ".ZTGLCCSE ");
				sqlString.append("WHERE FYEA = " + fiscalYear + " ");
				sqlString.append("AND CONO = " + company + " ");
			}
			
			
			
			if (inRequestType.equals("deleteConsumedCostComponentEntries"))
			{
				String date     = (String) requestClass.elementAt(0);
				String costType = (String) requestClass.elementAt(1);
				String company  = (String) requestClass.elementAt(2);
				String point    = (String) requestClass.elementAt(3);
				
				sqlString.append("DELETE FROM " + dblib + ".ZTFCST ");
				sqlString.append("WHERE FDATE = " + date + " ");
				sqlString.append("AND PCTP = '" + costType + "' ");
				sqlString.append("AND CONO = " + company + " ");
				sqlString.append("AND POINT = '" + point + "' ");
				sqlString.append("AND DATA = 'CONSUMED' ");
			}
			
			
			
			if (inRequestType.equals("deleteDuplicateComponents"))
			{
				String strtValue = (String) requestClass.elementAt(0);
				ResultSet rs     = (ResultSet) requestClass.elementAt(1);
				
				sqlString.append("DELETE FROM " + dblib + ".ZTGLCC ");
				sqlString.append("WHERE CONO = " + rs.getString("acono") + " ");
				sqlString.append("AND FACI = '" + rs.getString("afaci") + "' ");
				sqlString.append("AND ITNO = '" + rs.getString("aitno") + "' ");
				sqlString.append("AND FYEA = " + rs.getString("afyea") + " ");
				//sqlString.append("AND FDATE = " + rs.getString("afdate") + " ");
				sqlString.append("AND POINT = '" + rs.getString("apoint") + "' ");
				sqlString.append("AND CSTDTE = " + rs.getString("acstdte") + " ");
				sqlString.append("AND PCTP = '" + rs.getString("apctp") + "' ");
				sqlString.append("AND STRT = '" + strtValue + "' ");
			}
			
			
			
			if (inRequestType.equals("deleteExtrasZTFCST"))
			{
				String company = (String) requestClass.elementAt(0);
				String year    = (String) requestClass.elementAt(1);
				
				sqlString.append("DELETE FROM " + dblib + ".ZTFCST ");
				sqlString.append("WHERE CONO = " + company + " ");
				sqlString.append("AND FYEA = " + year + " ");
				sqlString.append("AND XREC = '1' ");
			}
			
			
			
			if (inRequestType.equals("deleteExtrasZTGLCC"))
			{
				String company = (String) requestClass.elementAt(0);
				String year    = (String) requestClass.elementAt(1);
				
				sqlString.append("DELETE FROM " + dblib + ".ZTGLCC ");
				sqlString.append("WHERE CONO = " + company + " ");
				sqlString.append("AND FYEA = " + year + " ");
				sqlString.append("AND CTIME = 999991 ");
			}
			
			
			
			if (inRequestType.equals("deleteExtrasCSPAFCST"))
			{
				String year    = (String) requestClass.elementAt(0);
				
				sqlString.append("DELETE FROM " + dblib + ".CSPAFCST ");
				sqlString.append("WHERE CSAYEAR = " + year + " ");
				sqlString.append("AND CSAFROM = 'Extra For Comparison' ");
			}
			
			
			
			if (inRequestType.equals("deleteReportTotalsEntries"))
			{
				String company = (String) requestClass.elementAt(0);
				String year    = (String) requestClass.elementAt(1);
				
				sqlString.append("DELETE FROM " + dblib + ".ZTFCSTTL ");
				sqlString.append("WHERE TLCONO = " + company + " ");
				sqlString.append("AND TLFYEA = " + year + " ");
			}
			
			
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for request type " + inRequestType + ". ");
		}
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildSqlStatement(String, Vector)");
			throw new Exception(throwError.toString());
		}
		   
		return sqlString.toString();
	}
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		// Test all that you can.
		String stophere = "";
		Vector vector = null;
		
		try {

			//Build GL Costing Variance ZTGLCV
			// Clear file DBPRD/ZTGLCV before execution
			if ("x".equals("x"))
			{
				if (1 == 1)
				{
					buildCostingVariance();
				}
			}
			
			//Build Variance Cost Earnings ZTVCER
			// Clear file DBPRD/ZTVCER before execution.
			if ("x".equals("y"))
			{
				if (1 == 1)
				{
					buildVarianceCostEarnings("2009", "20080801", "20090731");
				}
			}
			
			
			//Build ZTMCCOML Work File.
			if ("x".equals("y"))
			{
				if (1 == 1)
				{
					buildZtmccoml();
				}
			}
			
			
			//Build ZTPPVR Work File (Purchase Price Variance).
			if ("x".equals("y"))
			{
				if (1 == 1)
				{
					//buildPurchasePriceVariance("1");
					//buildPurchasePriceVariance("2");
					//buildPurchasePriceVariance("3");
					buildPurchasePriceVariance("P");
				}
			}
			

		//*******************************************************************************************************
		// START COSTING WORK FILES NEEDED FOR REBUILD
		//*******************************************************************************************************
			//Rebuild ZTGLCC 
			if ("x".equals("y"))
			{
				if (1 == 1)
				{
					buildCostCompNextFiscalYear("2", "100");//Start (next fiscal year)
					buildCostCompNextFiscalYear("3", "100");//Start (next fiscal year)
//					updateCostCompTotalFields("100");//cost model total - ztglcc **only run here if not run later
//					removeDuplicateCosts("100");//multiple strt's - ztglcc       **only run here if not run later 

					
					buildCostCompCurrFiscalYear("2", "100");//Start (curr fiscal year)
					buildCostCompCurrFiscalYear("3", "100");//Start (curr fiscal year)
					buildCostCompCurrToDate("2", "100");//End (curr fiscal year)
					buildCostCompCurrToDate("3", "100");//End (curr fiscal year)
					buildCostCompCurrBlank("2", "100");//Blank (curr fiscal year)-not start or end
					buildCostCompCurrBlank("3", "100");//Blank (curr fiscal year)-not start or end
					updateCostCompTotalFields("100", "2010");//cost model total - ztglcc -added 2010 to fix error
					removeDuplicateCosts("100", "2010");//multiple strt's - ztglcc - added 2010 to fix error 

					
					//if 2010 or 2009 are rebuilt.
					// new entries for either year require extra's to be rebuilt. extras are needed for prior year.
					addComparisonExtrasCSPAFCST("100", "2009");
					addComparisonExtrasZTGLCC("100", "2009");
					
					//builds, totals, and extras need to run first. 
					buildCostCompSingles("100", "NEXT");
					buildCostCompSingles("100", "CURR");
				}
			}
			
			
			//Single method runs for testing
			if ("x".equals("y"))
			{
				if (1 == 1)
				{
					//rebuildCostingWorkFilesForNextYear("100");
					rebuildCostingWorkFilesForCurrentAndNextYear("100");
				}
			}
			
			
			//Rebuild Consumed (ci) Costing ZTFCST
			if ("x".equals("y"))
			{
				if (1 == 1)
				{
					//run the update methods by year
					//These needed if update is Next
					buildConsumedCostNextFiscalYear("2", "100");
					buildConsumedCostNextFiscalYear("3", "100");
					updateConsumedProducedItemCost("100", "NEXT");//consumed cost
					updateProductionItemData("100", "NEXT");//operations-labor /budg qty
					
					//buildConsumedCostCurrFiscalYear("2", "100");
					//buildConsumedCostCurrFiscalYear("3", "100");
					//buildConsumedCostCurrToDate("2", "100");
					//buildConsumedCostCurrToDate("3", "100");
					
					// if either curr or next is executed this must run.
					addComparisonExtrasZTFCST("100", "2009"); // additional 2009 entries against 2010
					
					//updateConsumedProducedItemCost("100", "CURR");//consumed cost 
					//updateProductionItemData("100", "CURR");//operations-labor/budg qty
					updateProductionItemData("100", "NEXT");//operations-labor /budg qty
					
				}
			}
			
			
			//Rebuild Report Totals File
			if ("x".equals("y"))
			{
				//rebuild Totals file for current year.
				buildReportTotalsFile("current", "100");
				String stopHere = "stop here";
			}
		//*********************************************************************************************
		// END START COSTING WORK FILES NEEDED FOR REBUILD
		//*********************************************************************************************
			
			if ("x".equals("y"))
			{
				DateTime dt = UtilityDateTime.getSystemDate();
				int fYear = Integer.parseInt(dt.getM3FiscalYear());
				String currentFiscalYear = "" + fYear;
				String stopHere = "here";
			}

			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
/**
 * Load work file with Variance  
 * Cost Information.
 * 
 * @param 
 * @return 
 * @throws Exception
 */
public static void buildVarianceCostEarnings(String fiscalYear,
											 String begDate,
											 String endDate)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildVarianceCostEarnings(conn, fiscalYear, begDate, endDate);
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
		throwError.append("ServiceFinanceWorkFiles.");
		throwError.append("buildVarianceCostEarnings(");
		throwError.append("). ");
	}
	
	return;
}

/**
 * Build GL Costing variance Work File. 
 * @param conn.
 * @return Connection.
 */
private static Connection buildCostingVariance(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsCPOMAT  = null;
    ResultSet rsCINACC  = null;
    ResultSet rsCAREPL  = null;
    ResultSet rsCPOOPE  = null;
	PreparedStatement findCPOMAT  = null;
	PreparedStatement findCINACC  = null;
	PreparedStatement findCAREPL  = null;
	PreparedStatement findCPOOPE  = null;
	PreparedStatement addIt       = null;
	PreparedStatement deleteIt    = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
		
		//delete all records in the work file with sql.
		try
		{
			Vector parmClass = new Vector();
			requestType = "deleteCostingVarianceFile";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (deleteCostingVarianceFile). " + e);
		}
		
		try
		{
			if (throwError.toString().equals(""))
			{
				deleteIt = conn.prepareStatement(sqlString);
				deleteIt.executeUpdate();
				deleteIt.close();
			}
		} catch (Exception e) {
			throwError.append("Error at execute sql (deleteCostingVarianceFile). " + e);
		}
		
		
		//get the Costing Varianve Work File Data.
		try //build sql.
		{
			if (throwError.toString().equals(""))
			{
				Vector parmClass = new Vector();
				requestType = "GLCostingVariance";
				sqlString = buildSqlStatement(requestType, parmClass);
			}
		} catch(Exception e) {
			throwError.append("Error at build sql (GLCostingVariance). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findCPOMAT = conn.prepareStatement(sqlString);
				rsCPOMAT = findCPOMAT.executeQuery();
				String vono = "0";
				String acdt = "0";
				
				int debug = 0;
				
				// for each record update/add Sales by File.
				while (rsCPOMAT.next() && throwError.toString().equals(""))
				{
					
					// Verify value fits the file field.
					BigDecimal aiqt = new BigDecimal(rsCPOMAT.getString("NBAIQT"));
					BigDecimal siqt = new BigDecimal(rsCPOMAT.getString("NBSIQT"));
					BigDecimal qvri = aiqt.subtract(siqt);
					BigDecimal bill = new BigDecimal("1000000000");
					int testForBillion = qvri.toBigInteger().compareTo(bill.toBigInteger());
					
					if(testForBillion != 1 )
					{
									
						try {
							requestType = "getVoucherAndDate";
							Vector parmClass = new Vector();
							parmClass.addElement(rsCPOMAT.getString("NBCONO"));
							parmClass.addElement(rsCPOMAT.getString("NBANBR"));
							String ct = "A0" + rsCPOMAT.getString("TYCMRE").trim();
							parmClass.addElement(ct);
							sqlString = buildSqlStatement(requestType, parmClass);
							findCINACC = conn.prepareStatement(sqlString);
							rsCINACC = findCINACC.executeQuery();
							
							if (rsCINACC.next())
							{
								vono = rsCINACC.getString("EZVONO");
								acdt = rsCINACC.getString("EZACDT");
							}
							
							findCINACC.close();
							rsCINACC.close();
							
						} catch(Exception e) {
							throwError.append("Error at execute sql getVoucherAndDate. " + e);
						}
						
						//test to see if left outer join to CPOOPE found a record at the 
						// Structure Sequence No. If not then get the CPOOPE record without
						// the Structure Sequence No.
						String coce = "";
						String dept = "";
						
						if (rsCPOMAT.getString("NASTSQ") == null && rsCPOMAT.getString("NBDEPT").trim().equals(""))
						{
							try {//build an sql statement for CPOOPE access.
								requestType = "getDeptWithoutSTSQ";
								Vector parmClass = new Vector();
								parmClass.addElement(rsCPOMAT);
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch (Exception e) {
								throwError.append("Error at build sql (getDeptWithoutSTSQ)" + e);
							}
							
							try { //execute the sql
								findCPOOPE = conn.prepareStatement(sqlString);
								rsCPOOPE = findCPOOPE.executeQuery();
							} catch (Exception e) {
								throwError.append("Error at execute sql (getDeptWithoutSTSQ)" + e);
							}
							
							if (rsCPOOPE.next())
							{
								dept = rsCPOOPE.getString("NADEPT");
							} else
							{
								//if we could not find it with without STSQ, get it without ANBR
								//get a hit without the accounting number.
								try {//build an sql statement for CPOOPE access.
									requestType = "getDeptWithoutANBR";
									Vector parmClass = new Vector();
									parmClass.addElement(rsCPOMAT);
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch (Exception e) {
									throwError.append("Error at build sql (getDeptWithoutANBR)" + e);
								}
								
								try { //execute the sql
									findCPOOPE = conn.prepareStatement(sqlString);
									rsCPOOPE = findCPOOPE.executeQuery();
								} catch (Exception e) {
									throwError.append("Error at execute sql (getDeptWithoutANBR)" + e);
								}
								
								if (rsCPOOPE.next())
								{
									dept = rsCPOOPE.getString("NADEPT");
								}
							}
						}
						
						
						
						
						// get the Cost Center using the Department value
						try 
						{
							//get the department value
							if (!rsCPOMAT.getString("NBDEPT").trim().equals(""))
							{
								dept = rsCPOMAT.getString("NBDEPT").trim();
							} else
							{
								if (rsCPOMAT.getString("NADEPT") != null)
								{
									dept = rsCPOMAT.getString("NADEPT").trim();
								}
							}
							
							// Only continue if the department value is not blank.
							if (!dept.trim().equals(""))
							{
								//use the department value to obtain the Cost Center.
								try{//build the sql
									requestType = "getCostCenterFromDept";
									Vector parmClass = new Vector();
									parmClass.addElement(rsCPOMAT.getString("NBCONO"));
									parmClass.addElement(rsCPOMAT.getString("NBDIVI"));
									parmClass.addElement(dept);
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch (Exception e) {
									throwError.append("Error building the sql (getCostCenterFromDept). " + e);
								}
							
								// execute sql.
								if (throwError.toString().equals(""))
								{
									try {		
										findCAREPL = conn.prepareStatement(sqlString);
										rsCAREPL = findCAREPL.executeQuery();
									} catch (Exception e) {
										throwError.append("Error at execute sql (getCostCenterFromDept). " + e);
									}
								}
								
								if (rsCAREPL.next() && throwError.toString().equals(""))
								{
									coce = rsCAREPL.getString("CYTRNV");
								}
								
								findCAREPL.close();
								rsCAREPL.close();
							}
							
						} catch (Exception e) {
							throwError.append("Error getting the Cost Center From Department. " + e);
						}
						
						//add a record to the work file.
						try {
							debug = debug + 1;
							if (debug == 3289)
							{
								String x = "stophere";
							}
							requestType = "addCostingVariance";
							Vector parmClass = new Vector();
							parmClass.addElement(rsCPOMAT);
							String ct = "A0" + rsCPOMAT.getString("TYCMRE").trim();
							parmClass.addElement(ct);
							parmClass.addElement(acdt);
							parmClass.addElement(vono);
							parmClass.addElement(coce);
							parmClass.addElement(rsCPOOPE);
							sqlString = buildSqlStatement(requestType, parmClass);
							addIt = conn.prepareStatement(sqlString);
							addIt.executeUpdate();
							addIt.close();
						} catch(Exception e) {
							throwError.append("Error at execute sql addCostingVariance). " + e);
						}
						
						//close statement and result set if used. Skip any error messages.
						try {
							findCPOOPE.close();
							rsCPOOPE.close();
						} catch (Exception e) {
							String skipIt = "skip it";
						}
					}
					
				}
						
				findCPOMAT.close();
				rsCPOMAT.close();
						
			} catch (Exception e) {
						throwError.append("Error at build of sql add). " + e);
			}
		}

	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findCPOMAT != null)
				findCPOMAT.close();
			if (findCINACC != null)
				findCINACC.close();
			if (findCAREPL != null)
				findCAREPL.close();
			if (addIt != null)
				addIt.close();
			if (findCPOOPE != null)
				findCPOOPE.close();
		} catch(Exception e){
			throwError.append("Error closing statements. " + e);
		}
		try
		{
			if (rsCPOMAT != null)
				rsCPOMAT.close();
			if (rsCINACC != null)
				rsCINACC.close();
			if (rsCAREPL != null)
				rsCAREPL.close();
			if (rsCPOOPE != null)
				rsCPOOPE.close();
		} catch(Exception e){
			throwError.append("Error closing result sets. " + e);
		}
		
		// log any errors.	
		if (!throwError.toString().trim().equals("")) 
		{
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildCostingVariance(");
			throwError.append("Connection ");
			throwError.append("). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * Load work file with invoice 
 * sales amounts from file FGLEDG.
 * 
 * @param 
 * @return 
 * @throws Exception
 */
public static void buildCostingVariance()
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildCostingVariance(conn);
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
		
		// log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildCostingVariance(");
			throwError.append("). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	return;
}

/**
 * Build Variance Cost Earnings Work File. 
 * @param conn.
 * @return Connection.
 */
private static Connection buildVarianceCostEarnings(Connection conn, String fiscalYear,
													String begDate,  String endDate)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rs               = null;
	PreparedStatement findIt   = null;
	PreparedStatement addIt    = null;
	PreparedStatement deleteIt = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
		
		//delete all records in the work file with sql.
		try
		{
			Vector parmClass = new Vector();
			requestType = "deleteVarianceCostEarnings";
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (deleteVarianceCostEarnings). " + e);
		}
		
		try
		{
			if (throwError.toString().equals(""))
			{
				deleteIt = conn.prepareStatement(sqlString);
				deleteIt.executeUpdate();
				deleteIt.close();
			}
		} catch (Exception e) {
			throwError.append("Error at execute sql (deleteVarianceCostEarnings). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			//get the Variance Cost Earnings Work File Data.
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getVarianceCostEarnings";
				parmClass.addElement(begDate);
				parmClass.addElement(endDate);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getVarianceCostEarnings). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();
					
					String lastItno = "";
					String lastFaci = "";
				
					// for each record with new Item add to the work File.
					while (rs.next() && throwError.toString().equals(""))
					{
						try {
							if (!rs.getString("KQITNO").trim().equals(lastItno) ||
								!rs.getString("KQFACI").trim().equals(lastFaci))
							{
								lastItno = rs.getString("KQITNO").trim();
								lastFaci = rs.getString("KQFACI").trim();
								requestType = "addVarianceCostEarnings";
								Vector parmClass = new Vector();
								parmClass.addElement(rs);
								parmClass.addElement(fiscalYear);
								parmClass.addElement("definition");
								parmClass.addElement("value");
								
								//single record for each defined cost component.
								parmClass.setElementAt("A01", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCA01"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("A02", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCA02"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("A03", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCA03"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("A04", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCA04"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("A05", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCA05"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B01", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB01"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B02", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB02"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B03", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB03"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B04", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB04"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B05", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB05"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B06", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB06"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B07", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB07"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B08", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB08"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B09", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB09"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B10", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB10"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B11", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB11"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B12", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB12"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B13", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB13"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("B14", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCB14"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								
								parmClass.setElementAt("C01", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCC01"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("C02", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCC02"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("D01", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCD01"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("D02", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCD02"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("E01", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCE01"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("E02", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCE02"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("E03", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCE03"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("E04", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCE04"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("E05", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCE05"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("E06", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCE06"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("E07", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCE07"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("E08", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCE08"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("E09", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCE09"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("E10", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQCE10"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("S01", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQSSU1"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("S02", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQSSU2"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
								
								parmClass.setElementAt("S03", 2);
								parmClass.setElementAt(rs.getBigDecimal("KQSSU3"), 3);
								sqlString = buildSqlStatement(requestType, parmClass);
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
							
							}
							
						} catch(Exception e) {
							throwError.append("Error at execute sql addCostingVariance). " + e);
						}
					}
							
					findIt.close();
					rs.close();
							
				} catch (Exception e) {
							throwError.append("Error at build of sql add). " + e);
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
			if (findIt != null)
				findIt.close();
			if (addIt != null)
				addIt.close();
			if (deleteIt != null)
				deleteIt.close();
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
		throwError.append("ServiceFinanceWorkFiles.");
		throwError.append("buildVarianceCostEarnings(");
		throwError.append("Connection, fiscalYear, begDate, endDate ");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * Load work file for MCCOML 
 * will be ZTMCCOML
 * 
 * @param 
 * @return 
 * @throws Exception
 */
public static void buildZtmccoml()
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildZtmccoml(conn);
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
		throwError.append("ServiceFinanceWorkFiles.");
		throwError.append("buildZtmccoml(");
		throwError.append("). ");
	}
	
	return;
}

/**
 * Build Work File ZTMCCOML. 
 * @param conn.
 * @return Connection.
 */
private static Connection buildZtmccoml(Connection conn)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsMCCOML      = null;
	PreparedStatement findMCCOML  = null;
	PreparedStatement addIt       = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
		
		//get the Costing Type Work File Data.
		try //build sql.
		{
			if (throwError.toString().equals(""))
			{
				Vector parmClass = new Vector();
				requestType = "getCostingComponentLevelEntries";
				sqlString = buildSqlStatement(requestType, parmClass);
			}
		} catch(Exception e) {
			throwError.append("Error at build sql (getCostingComponentLevelEntries). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findMCCOML = conn.prepareStatement(sqlString);
				rsMCCOML = findMCCOML.executeQuery();
				
				String cono = "";
				String faci = "";
				String itno = "";
				
				
				// for each record update/add Sales by File.
				while (rsMCCOML.next() && throwError.toString().equals(""))
				{
					if (!cono.equals(rsMCCOML.getString("KQCONO").trim())||
						!faci.equals(rsMCCOML.getString("KQFACI").trim())||
						!itno.equals(rsMCCOML.getString("KQITNO").trim()))
					{
						cono = rsMCCOML.getString("KQCONO").trim();
						faci = rsMCCOML.getString("KQFACI").trim();
						itno = rsMCCOML.getString("KQITNO").trim();
						
						try //add the work file record.
						{
							requestType = "addZTMCCOMLrecord";
							Vector parmClass = new Vector();
							parmClass.addElement((rsMCCOML));
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (addZTMCCOMLrecord). " + e );
						}
						
						try // execute the sql.
						{
							addIt = conn.prepareStatement(sqlString);
							addIt.executeUpdate();
							addIt.close();
						} catch (Exception e) {
							throwError.append("Error at execute sql (addZTMCCOMLrecord). " + e);
						}
					}
				}	
			} catch(Exception e) {
				throwError.append("Error at execute sql (getCostingComponentLevelEntries). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
					

	finally
	{
		try
		{
			if (findMCCOML!= null)
				findMCCOML.close();
			if (addIt != null)
				addIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsMCCOML != null)
				rsMCCOML.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceFinanceWorkFiles.");
		throwError.append("buildZtmccoml(");
		throwError.append("Connection ");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}


/**
 * Load work file Purchase Price Variance 
 * 
 * @param 
 * @return 
 * @throws Exception
 */
public static void buildPurchasePriceVariance(String level)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildPurchasePriceVariance(conn, level);
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
		throwError.append("ServiceFinanceWorkFiles.");
		throwError.append("buildPurchasePriceVariance(");
		throwError.append("). ");
	}
	
	return;
}

/**
 * Build Purchase Price Variance Work File. 
 * @param conn.
 * @return Connection.
 */
private static Connection buildPurchasePriceVariance(Connection conn, String level)						
	throws Exception 
{
	StringBuffer throwError = new StringBuffer();
    ResultSet rsMPLINE = null;
    ResultSet rsFGLEDG = null;
    ResultSet rsMITAUN = null;
    ResultSet rsMITTRA = null;
    ResultSet rsCINACC = null;
    ResultSet rsFGINAE = null;
	PreparedStatement findMPLINE = null;
	PreparedStatement findFGLEDG = null;
	PreparedStatement findMITAUN = null;
	PreparedStatement findMITTRA = null;
	PreparedStatement findCINACC = null;
	PreparedStatement findFGINAE = null;
	PreparedStatement addIt      = null;
	PreparedStatement updateIt	 = null;
	PreparedStatement deleteIt	 = null;
	String requestType = "";
	String sqlString = "";
	
	try { //enable finally.
		
		//delete all records in the work file with sql.
		try
		{
			requestType = "deletePurchasePriceVarianceFile";
			Vector parmClass = new Vector();
			parmClass.addElement(level);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (deletePurchasePriceVarianceFile). " + e);
		}
		
		try
		{
			if (throwError.toString().equals(""))
			{
				deleteIt = conn.prepareStatement(sqlString);
				deleteIt.executeUpdate();
				deleteIt.close();
			}
		} catch (Exception e) {
			throwError.append("Error at execute sql (deletePurchasePriceVarianceFile). " + e);
		}
		
		
		//Level "1" loads FGLEDG data.
		if (level.equals("1") && throwError.toString().equals(""))
		{	
			//get FGLEDG Variance
			try //build sql.
			{
				if (throwError.toString().equals(""))
				{
					Vector parmClass = new Vector();
					requestType = "getFgledgVariance";
					sqlString = buildSqlStatement(requestType, parmClass);
				}
			} catch(Exception e) {
				throwError.append("Error at build sql (getFgledgVariance). " + e);
			}
			
			try //execute sql
			{
				findFGLEDG = conn.prepareStatement(sqlString);
				rsFGLEDG = findFGLEDG.executeQuery();
			} catch (Exception e) {
				throwError.append("Error at execute sql (getFgledgVariance). " + e);
			}
			
			while (rsFGLEDG.next() && throwError.toString().equals(""))
			{
				try //build sql
				{
					requestType = "addVarianceEntryFGLEDG";
					Vector parmClass = new Vector();
					parmClass.addElement(rsFGLEDG);
					parmClass.addElement(level);
					sqlString = buildSqlStatement(requestType, parmClass);
				} catch (Exception e) {
					throwError.append("Error at build sql (addVarianceEntryFGLEDG). " + e);
				}
				
				//execute sql.
				try {
					addIt = conn.prepareStatement(sqlString);
					addIt.executeUpdate();
					addIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (addVarianceEntryFGLEDG). " + e );
				}
			}
		}
		
		
		//Level "2" load CINACC data.
		if (level.equals("2") && throwError.toString().equals(""))
		{	
			//get CINACC and MITTRA Variance entries.
			try //build sql.
			{
				if (throwError.toString().equals(""))
				{
					Vector parmClass = new Vector();
					requestType = "getCinaccVariance";
					sqlString = buildSqlStatement(requestType, parmClass);
				}
			} catch(Exception e) {
				throwError.append("Error at build sql (getCinaccVariance). " + e);
			}
			
			try //execute sql
			{
				findCINACC = conn.prepareStatement(sqlString);
				rsCINACC = findCINACC.executeQuery();
			} catch (Exception e) {
				throwError.append("Error at execute sql (getCinaccVariance). " + e);
			}
			
			while (rsCINACC.next() && throwError.toString().equals(""))
			{
				try //build sql
				{
					requestType = "addVarianceEntryCINACC";
					Vector parmClass = new Vector();
					parmClass.addElement(rsCINACC);
					parmClass.addElement(level);
					
					BigDecimal stdPrice = new BigDecimal("0");
					BigDecimal purPrice = new BigDecimal("0");
					BigDecimal priceVar = new BigDecimal("0");
					BigDecimal qty		= new BigDecimal("0");
					BigDecimal stdDlr	= new BigDecimal("0");
					
					if (rsCINACC.getString("MTTRPR") != null)
					{
						stdPrice = new BigDecimal(rsCINACC.getString("MTTRPR"));
						purPrice = new BigDecimal(rsCINACC.getString("MTMFCO"));
						priceVar = purPrice.subtract(stdPrice);
						qty		 = new BigDecimal(rsCINACC.getString("MTTRQT"));
						stdDlr	 = qty.multiply(stdPrice);
						stdDlr.setScale(2, 0);
					}
					
					parmClass.addElement(stdPrice);
					parmClass.addElement(purPrice);
					parmClass.addElement(priceVar);
					parmClass.addElement(qty);
					parmClass.addElement(stdDlr);
					sqlString = buildSqlStatement(requestType, parmClass);
				} catch (Exception e) {
					throwError.append("Error at build sql (addVarianceEntryCINACC). " + e);
				}
				
				//execute sql.
				try {
					addIt = conn.prepareStatement(sqlString);
					addIt.executeUpdate();
					addIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (addVarianceEntryCINACC). " + e );
				}
			}
		}
		
		
		
		
		//Level "P" load FGINAE data.
		if (level.equals("P") && throwError.toString().equals(""))
		{	
			//get FGINAE Variance entries.
			try //build sql.
			{
				if (throwError.toString().equals(""))
				{
					Vector parmClass = new Vector();
					requestType = "getFginaeVariance2";
					sqlString = buildSqlStatement(requestType, parmClass);
				}
			} catch(Exception e) {
				throwError.append("Error at build sql (getFginaeVariance). " + e);
			}
			
			try //execute sql
			{
				findFGINAE = conn.prepareStatement(sqlString);
				rsFGINAE = findFGINAE.executeQuery();
			} catch (Exception e) {
				throwError.append("Error at execute sql (getFginaeVariance). " + e);
			}
			
			while (rsFGINAE.next() && throwError.toString().equals(""))
			{
				try //build sql
				{
					requestType = "addVarianceEntryFGINAE";
					Vector parmClass = new Vector();
					parmClass.addElement(rsFGINAE);
					parmClass.addElement(level);
					
					BigDecimal zero		= new BigDecimal("0");
					BigDecimal priceVar = new BigDecimal("0");
					BigDecimal qty		= new BigDecimal(rsFGINAE.getString("F9ACQT"));
					BigDecimal dollars	= new BigDecimal(rsFGINAE.getString("F9CUAM"));
					BigDecimal invDlr	= new BigDecimal("0");
					BigDecimal invPrice = new BigDecimal("0");
					BigDecimal purPrice	= new BigDecimal("0");
					BigDecimal stdPrice = new BigDecimal("0");
					BigDecimal stdDlr	= new BigDecimal("0");
					
					//determine invoice price variance.
					int x = qty.compareTo(zero);
					
					if (x != 0)
					{
						priceVar = dollars.divide(qty, 6, 0);
					}
					
					parmClass.addElement(priceVar);
					
					//determine invoice total dollars.
					purPrice = new BigDecimal(rsFGINAE.getString("MTMFCO"));
					invPrice = purPrice.add(priceVar);
					invDlr	= invPrice.multiply(qty);
					
					
					//determine standard total dollars.
					stdPrice = new BigDecimal(rsFGINAE.getString("MTTRPR"));
					stdDlr	 = stdPrice.multiply(qty);
					
					
					parmClass.addElement(invDlr);
					parmClass.addElement(invPrice);
					parmClass.addElement(stdDlr);
					parmClass.addElement(stdPrice);
					
					sqlString = buildSqlStatement(requestType, parmClass);
				} catch (Exception e) {
					throwError.append("Error at build sql (addVarianceEntryFGINAE). " + e);
				}
				
				//execute sql.
				try {
					addIt = conn.prepareStatement(sqlString);
					addIt.executeUpdate();
					addIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (addVarianceEntryFGINAE). " + e );
				}
			}
		}
		
		
		
		
		//Level "3" load FGINAE data.
		if (level.equals("3") && throwError.toString().equals(""))
		{	
			//get FGINAE Variance entries.
			try //build sql.
			{
				if (throwError.toString().equals(""))
				{
					Vector parmClass = new Vector();
					requestType = "getFginaeVariance";
					sqlString = buildSqlStatement(requestType, parmClass);
				}
			} catch(Exception e) {
				throwError.append("Error at build sql (getFginaeVariance). " + e);
			}
			
			try //execute sql
			{
				findFGINAE = conn.prepareStatement(sqlString);
				rsFGINAE = findFGINAE.executeQuery();
			} catch (Exception e) {
				throwError.append("Error at execute sql (getFginaeVariance). " + e);
			}
			
			while (rsFGINAE.next() && throwError.toString().equals(""))
			{
				try //build sql
				{
					requestType = "addVarianceEntryFGINAE";
					Vector parmClass = new Vector();
					parmClass.addElement(rsFGINAE);
					parmClass.addElement(level);
					
					BigDecimal zero		= new BigDecimal("0");
					BigDecimal priceVar = new BigDecimal("0");
					BigDecimal qty		= new BigDecimal(rsFGINAE.getString("F9ACQT"));
					BigDecimal dollars	= new BigDecimal(rsFGINAE.getString("F9CUAM"));
					BigDecimal invDlr	= new BigDecimal("0");
					BigDecimal invPrice = new BigDecimal("0");
					BigDecimal cf		= new BigDecimal("1");//conversion factor
					
					//determine invoice price variance.
					int x = qty.compareTo(zero);
					
					if (x != 0)
					{
						priceVar = dollars.divide(qty, 6, 0);
					}
					
					parmClass.addElement(priceVar);
					
					//determine invoice total dollars.
					if (rsFGINAE.getString("IHIVPR") != null && 
						rsFGINAE.getString("MUCOFA") != null)
					{
						invPrice = new BigDecimal(rsFGINAE.getString("IHIVPR"));
						cf		 = new BigDecimal(rsFGINAE.getString("MUCOFA"));
						
						int compareInt = cf.compareTo(zero);
						
						if (compareInt != 0)
						{
							invPrice 	= invPrice.divide(cf, 6, 0);
							invDlr		= invPrice.multiply(qty);
						}
					}
					
					parmClass.addElement(invDlr);
					parmClass.addElement(invPrice);
					
					sqlString = buildSqlStatement(requestType, parmClass);
				} catch (Exception e) {
					throwError.append("Error at build sql (addVarianceEntryFGINAE). " + e);
				}
				
				//execute sql.
				try {
					addIt = conn.prepareStatement(sqlString);
					addIt.executeUpdate();
					addIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (addVarianceEntryFGINAE). " + e );
				}
			}
		}
		
		
		
		
		if (level.equals("9") && throwError.toString().equals(""))
		{
			try {		
				
			
				// for each record add to file.
				while (rsFGLEDG.next() && throwError.toString().equals(""))
				{
					try {
						requestType = "getMplineByPurnoRecpdate";
						Vector parmClass = new Vector();
						parmClass.addElement(rsFGLEDG.getString("EGAIT6"));
						parmClass.addElement(rsFGLEDG.getString("EGACDT"));
						sqlString = buildSqlStatement(requestType, parmClass);
					} catch (Exception e) {
						throwError.append("Error at build sql (getMplineByPurnoRecpdate). " + e);
					}
					
					try //execute sql. 
					{
						findMPLINE = conn.prepareStatement(sqlString);
						rsMPLINE = findMPLINE.executeQuery();
						String firstPass = "yes";
						String exists    = "no";
						
						while (rsMPLINE.next()) //add FGLEDG and MPLINE entries (CA1).
						{
						
							//confirm that the GL entry has at least on MPLINE entry.
							exists = "yes";
							String cnvFactor = "1";
								
							//get the conversion factor
							try //build sql
							{
								requestType = "getConversionFactor";
								Vector parmClass = new Vector();
								parmClass.addElement(rsMPLINE.getString("MPCONO"));
								parmClass.addElement(rsMPLINE.getString("IBITNO"));
								parmClass.addElement(rsMPLINE.getString("IBPPUN").trim());
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch (Exception e) {
								throwError.append("Error at build sql (getConversionFactor). " + e);
							}
							
							//execute sql
							try {
								findMITAUN = conn.prepareStatement(sqlString);
								rsMITAUN = findMITAUN.executeQuery();
							} catch (Exception e) {
								throwError.append("Error at build sql (getConversionFactor). " + e);
							}
							
							findMITAUN.close();
							rsMITAUN.close();
							
							//if (rsMITAUN)
								//cnvFactor = rsMITAUN.getString("MUCOFA");
							
							//calculate purchase price
							BigDecimal zero = new BigDecimal("0");
							BigDecimal cf   = new BigDecimal(cnvFactor);
							BigDecimal pupr = new BigDecimal(rsMPLINE.getString("IBPUPR")); 
							
							int compareInt = cf.compareTo(zero);
							
							if (compareInt != 0)
							{
								pupr = pupr.divide(cf, 6, 0);
							}
							
							try //build sql
							{
								requestType = "addCA1EntryWithMPLINE";
								Vector parmClass = new Vector();
								parmClass.addElement(rsFGLEDG);
								parmClass.addElement(rsMPLINE);
								parmClass.addElement(firstPass);
								parmClass.addElement(cnvFactor);
								parmClass.addElement(pupr);
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch (Exception e) {
								throwError.append("Error at build sql (addCA1EntryWithMPLINE). " + e);
							}
							
							//execute sql.
							try {
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
							} catch(Exception e) {
								throwError.append("Error at execute sql (addCA1EntryWithMPLINE). " + e );
							}
							
							//String firstPass = "no";
						}
						
						findMPLINE.close();
						rsMPLINE.close();
						
						if (exists.equals("no"))
						{
							try //build sql
							{
								requestType = "addCA1EntryWithOutMPLINE";
								Vector parmClass = new Vector();
								parmClass.addElement(rsFGLEDG);
								sqlString = buildSqlStatement(requestType, parmClass);
							} catch (Exception e) {
								throwError.append("Error at build sql (addCA1EntryWithOutMPLINE). " + e);
							}
							
							//execute sql.
							try {
								addIt = conn.prepareStatement(sqlString);
								addIt.executeUpdate();
								addIt.close();
							} catch(Exception e) {
								throwError.append("Error at execute sql (addCA1EntryWithOutMPLINE). " + e );
							}
						}
					} catch(Exception e) {
						throwError.append("Error at execute sql (addCA1EntryWithOutMPLINE). " + e );
					}
				}
			} catch(Exception e) {
				throwError.append("Error at execute sql (addCA1EntryWithOutMPLINE). " + e );
			}
		}

	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
				
			
	finally
	{
		try
		{
			if (findFGLEDG != null)
				findFGLEDG.close();
			if (findCINACC != null)
				findCINACC.close();
			if (findFGINAE != null)
				findFGINAE.close();
			if (addIt != null)
				addIt.close();
			if (deleteIt != null)
				deleteIt.close();
		} catch(Exception el){
			el.printStackTrace();
		}
		try
		{
			if (rsFGLEDG != null)
				rsFGLEDG.close();
			if (rsCINACC != null)
				rsCINACC.close();
			if (rsFGINAE != null)
				rsFGINAE.close();
		} catch(Exception el){
			el.printStackTrace();
		}	
	}
	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceFinanceWorkFiles.");
		throwError.append("buildPurchasePriceVariance(");
		throwError.append("Connection ");
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}

  return conn;
}

/**
 * Build Cost Component entries
 * one component per record. 
 * 
 * @param String: company
 * @return 
 * @throws Exception
 */
public static void buildCostCompSingles(String company, String fiscalYear)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildCostCompSingles(company, fiscalYear, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildCostCompSingles(" + company + "). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 * Build Cost Component Entries 
 * For Next Fiscal Year By Type
 * @param conn.
 * @param cost type.
 * @return Connection.
 */
private static Connection buildCostCompNextFiscalYear(String costType, 
													  String company,
													  Connection conn)

	throws Exception 
{
	StringBuffer throwError     = new StringBuffer();
    ResultSet rs                = null;
	java.sql.Statement findIt   = null;
	java.sql.Statement addIt    = null;
	java.sql.Statement deleteIt = null;
	String requestType          = "";
	String sqlString            = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		String nextFiscalYearDate = "";
		int fYear = Integer.parseInt(dt.getM3FiscalYear());
		nextFiscalYearDate = dt.getM3FiscalYear() + "0801";
		fYear = fYear + 1;
		String fiscalYear = "" + fYear;
		String chgDate = dt.getDateFormatyyyyMMdd();
		String chgTime = dt.getTimeFormathhmmss();
		String point   = "START";
		
		//delete matching records prior to build.
		try
		{
			//use date and cost type to remove prior entries. 
			Vector parmClass = new Vector();
			requestType = "deleteCostComponentEntries";
			parmClass.addElement(nextFiscalYearDate);
			parmClass.addElement(costType);
			parmClass.addElement(company);
			parmClass.addElement(point);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (deleteCostComponentEntries). " + e);
		}
		
		try
		{
			if (throwError.toString().equals(""))
			{
				deleteIt = conn.createStatement();
				deleteIt.executeUpdate(sqlString);
				deleteIt.close();
			}
		} catch (Exception e) {
			throwError.append("Error at execute sql (deleteCostComponentEntries). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			//Get Costing data.
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getMccomlStartFiscalYear";
				parmClass.addElement(nextFiscalYearDate);
				parmClass.addElement(costType);
				parmClass.addElement(company);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getMccomlStartFiscalYear). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
					
					String lastFaci = "";
					String lastItno = "";
				
					// for each record with new Item add to the work File.
					while (rs.next() && throwError.toString().equals(""))
					{
						try {//build the sql.
							if (!rs.getString("KQITNO").trim().equals(lastItno.trim()) ||
								!rs.getString("KQFACI").trim().equals(lastFaci.trim()))
							{
								lastItno = rs.getString("KQITNO").trim();
								lastFaci = rs.getString("KQFACI").trim();
								requestType = "addZtglcc";
								Vector parmClass = new Vector();
								parmClass.addElement(fiscalYear);
								parmClass.addElement(nextFiscalYearDate);
								parmClass.addElement(point);
								parmClass.addElement("0");
								parmClass.addElement(rs);
								parmClass.addElement(chgDate);
								parmClass.addElement(chgTime);
								sqlString = buildSqlStatement(requestType, parmClass);
								
								if (throwError.toString().equals(""))
								{
									try {
										addIt = conn.createStatement();
										addIt.executeUpdate(sqlString);
										addIt.close();
									} catch (Exception e) {
										throwError.append("Error executing sql (addZtglcc)" + e);
									}
								}
								
							}
						} catch (Exception e) {
							throwError.append("Error build sql (addZtglcc)" + e);
						}
					}
					
					rs.close();
					findIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (getMccomlStartFiscalYear). " + e);
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
			if (findIt != null)
				findIt.close();
			if (addIt != null)
				addIt.close();
			if (deleteIt != null)
				deleteIt.close();
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildCostCompNextFiscalYear(String,String,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * Build Cost Component Entries 
 * For Current Fiscal Year By Type
 * 
 * @param String: cost type
 * @return 
 * @throws Exception
 */
public static void buildCostCompCurrFiscalYear(String costType, String company)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildCostCompCurrFiscalYear(costType, company, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildCostCompCurrFiscalYear(" + costType + "). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 * Build Cost Component Entries 
 * For Current Fiscal Year By Type
 * @param conn.
 * @param cost type.
 * @return Connection.
 */
private static Connection buildCostCompCurrFiscalYear(String costType, 
													  String company,
													  Connection conn)

	throws Exception 
{
	StringBuffer throwError     = new StringBuffer();
    ResultSet rs                = null;
	java.sql.Statement findIt   = null;
	java.sql.Statement addIt    = null;
	java.sql.Statement deleteIt = null;
	String requestType          = "";
	String sqlString            = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		String currFiscalYearDate = "";
		int fYear = Integer.parseInt(dt.getM3FiscalYear());
		fYear = fYear - 1;
		currFiscalYearDate = "" + fYear + "0801";
		String chgDate = dt.getDateFormatyyyyMMdd();
		String chgTime = dt.getTimeFormathhmmss();
		String point   = "START";
		
		//delete matching records prior to build.
		try
		{
			//use date and cost type to remove prior entries. 
			Vector parmClass = new Vector();
			requestType = "deleteCostComponentEntries";
			parmClass.addElement(currFiscalYearDate);
			parmClass.addElement(costType);
			parmClass.addElement(company);
			parmClass.addElement(point);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (deleteCostComponentEntries). " + e);
		}
		
		try
		{
			if (throwError.toString().equals(""))
			{
				deleteIt = conn.createStatement();
				deleteIt.executeUpdate(sqlString);
				deleteIt.close();
			}
		} catch (Exception e) {
			throwError.append("Error at execute sql (deleteCostComponentEntries). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			//Get Costing data.
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getMccomlStartFiscalYear";
				parmClass.addElement(currFiscalYearDate);
				parmClass.addElement(costType);
				parmClass.addElement(company);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getMccomlStartFiscalYear). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
					
					String lastFaci = "";
					String lastItno = "";
					String lastStrt = "";
					
				
					// for each record with new Item add to the work File.
					while (rs.next() && throwError.toString().equals(""))
					{
						try {//build the sql.
							if (!rs.getString("KQITNO").trim().equals(lastItno.trim()) ||
								!rs.getString("KQFACI").trim().equals(lastFaci.trim()) ||
								!rs.getString("KQSTRT").equals(lastStrt.trim()))
							{
								lastItno = rs.getString("KQITNO").trim();
								lastFaci = rs.getString("KQFACI").trim();
								lastStrt = rs.getString("KQSTRT").trim();
								requestType = "addZtglcc";
								Vector parmClass = new Vector();
								parmClass.addElement(dt.getM3FiscalYear());
								parmClass.addElement(currFiscalYearDate);
								parmClass.addElement(point);
								parmClass.addElement("0");
								parmClass.addElement(rs);
								parmClass.addElement(chgDate);
								parmClass.addElement(chgTime);
								sqlString = buildSqlStatement(requestType, parmClass);
								
								if (throwError.toString().equals(""))
								{
									try {
										addIt = conn.createStatement();
										addIt.executeUpdate(sqlString);
										addIt.close();
									} catch (Exception e) {
										throwError.append("Error executing sql (addZtglcc)" + e);
									}
								}
								
							}
						} catch (Exception e) {
							throwError.append("Error build sql (addZtglcc)" + e);
						}
					}
					
					rs.close();
					findIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (getMccomlStartFiscalYear). " + e);
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
			if (findIt != null)
				findIt.close();
			if (addIt != null)
				addIt.close();
			if (deleteIt != null)
				deleteIt.close();
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildCostCompCurrFiscalYear(String,String,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * Build Cost Component Entries 
 * For Current Date By Type
 * 
 * @param String: cost type
 * @return 
 * @throws Exception
 */
public static void buildCostCompCurrToDate(String costType, String company)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildCostCompCurrToDate(costType, company, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildCostCompCurrToDate(" + costType + "). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 * Build Cost Component Entries 
 * For Current Date By Type
 * @param conn.
 * @param cost type.
 * @return Connection.
 */
private static Connection buildCostCompCurrToDate(String costType, 
												  String company,
												  Connection conn)

	throws Exception 
{
	StringBuffer throwError     = new StringBuffer();
    ResultSet rs                = null;
	java.sql.Statement findIt   = null;
	java.sql.Statement addIt    = null;
	java.sql.Statement deleteIt = null;
	String requestType          = "";
	String sqlString            = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		String chgDate = dt.getDateFormatyyyyMMdd();
		String chgTime = dt.getTimeFormathhmmss();
		String currFiscalYearEndDate = "";
		String currFiscalYearStartDate = "";
		currFiscalYearEndDate = dt.getM3FiscalYear() + "0731";
		BigDecimal startYear  = new BigDecimal(dt.getM3FiscalYear());
		startYear = startYear.subtract(new BigDecimal("1"));
		currFiscalYearStartDate = startYear.toString() + "0801";

		String currDate = "";
		
		if (dt.getMonth().equals("07") && dt.getDay().equals("31"))
		{
			currDate = dt.getDateFormatyyyyMMdd();
		} else {
			//dt = UtilityDateTime.addDaysToDate(dt, -1);
			currDate = dt.getDateFormatyyyyMMdd();
		}
		
		String point = "END";
		
		//delete matching records prior to build.
		try
		{
			//use date and cost type to remove prior entries. 
			Vector parmClass = new Vector();
			requestType = "deleteCostComponentEntries";
			parmClass.addElement(currFiscalYearEndDate);
			parmClass.addElement(costType);
			parmClass.addElement(company);
			parmClass.addElement(point);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (deleteCostComponentEntries). " + e);
		}
		
		try
		{
			if (throwError.toString().equals(""))
			{
				deleteIt = conn.createStatement();
				deleteIt.executeUpdate(sqlString);
				deleteIt.close();
			}
		} catch (Exception e) {
			throwError.append("Error at execute sql (deleteCostComponentEntries). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			//Get Consumed data.
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getMccomlAsOfDate";
				parmClass.addElement(currDate);
				parmClass.addElement(costType);
				parmClass.addElement(company);
				parmClass.addElement(currFiscalYearStartDate);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getMccomlAsOfDate). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
					
					String lastFaci = "";
					String lastItno = "";
					String lastStrt = "";
				
					// for each record with new Item add to the work File.
					while (rs.next() && throwError.toString().equals(""))
					{
						try {//build the sql.
							if (!rs.getString("KQITNO").trim().equals(lastItno.trim()) ||
								!rs.getString("KQFACI").trim().equals(lastFaci.trim()) ||
								!rs.getString("KQSTRT").trim().equals(lastStrt.trim()))
							{
								lastItno = rs.getString("KQITNO").trim();
								lastFaci = rs.getString("KQFACI").trim();
								lastStrt = rs.getString("KQSTRT").trim();
								requestType = "addZtglcc";
								Vector parmClass = new Vector();
								parmClass.addElement(dt.getM3FiscalYear());
								parmClass.addElement(currFiscalYearEndDate);
								parmClass.addElement(point);
								parmClass.addElement(currDate);
								parmClass.addElement(rs);
								parmClass.addElement(chgDate);
								parmClass.addElement(chgTime);
								sqlString = buildSqlStatement(requestType, parmClass);
								
								if (throwError.toString().equals(""))
								{
									try {
										addIt = conn.createStatement();
										addIt.executeUpdate(sqlString);
										addIt.close();
									} catch (Exception e) {
										throwError.append("Error executing sql (addZtglcc)" + e);
									}
								}
								
							}
						} catch (Exception e) {
							throwError.append("Error build sql (addZtglcc)" + e);
						}
					}
					
					rs.close();
					findIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (getMccomlAsOfDate). " + e);
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
			if (findIt != null)
				findIt.close();
			if (addIt != null)
				addIt.close();
			if (deleteIt != null)
				deleteIt.close();
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildCostCompCurrToDate(String,String,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * Build Cost Component Entries 
 * For Next Fiscal Year By Type
 * 
 * @param String: cost type
 * @return 
 * @throws Exception
 */
public static void buildCostCompNextFiscalYear(String costType, String company)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildCostCompNextFiscalYear(costType, company, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildCostCompNextFiscalYear(" + costType + "). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 Build Cost Component entries
 * one component per record. 
 * @param company.
 * @param connection.
 * @return Connection.
 */
private static Connection buildCostCompSingles(String company, 
											   String fiscalYear,
											   Connection conn)

	throws Exception 
{
	StringBuffer throwError     = new StringBuffer();
    ResultSet rs                = null;
	java.sql.Statement findIt   = null;
	java.sql.Statement addIt    = null;
	java.sql.Statement deleteIt = null;
	java.sql.Statement updateIt = null;
	String requestType          = "";
	String sqlString            = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		String fYear = "";
		
		if (fiscalYear.equals("NEXT"))
		{
			int fyr = Integer.parseInt(dt.getM3FiscalYear());
			fyr = fyr + 1;
			fYear = "" + fyr;
		} else
			fYear = dt.getM3FiscalYear();
		
		//delete records prior to build.
		try
		{
			//use date and cost type to remove prior entries. 
			Vector parmClass = new Vector();
			requestType = "deleteCostComponentZTGLCCSE";
			parmClass.addElement(company);
			parmClass.addElement(fYear);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (deleteCostComponentZTGLCCSE). " + e);
		}
		
		try
		{
			if (throwError.toString().equals(""))
			{

				deleteIt = conn.createStatement();
				deleteIt.executeUpdate(sqlString);
				deleteIt.close();
				
			}
		} catch (Exception e) {
			throwError.append("Error at execute sql (deleteCostComponentZTGLCCSE). " + e);
		}
		
		//delete records prior to build.
		try
		{
			//use date and cost type to remove prior entries. 
			Vector parmClass = new Vector();
			requestType = "deleteCostComponentZTFCST";
			parmClass.addElement(company);
			parmClass.addElement(fYear);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (deleteCostComponentZTFCST). " + e);
		}
		
		try
		{
			if (throwError.toString().equals(""))
			{
				deleteIt = conn.createStatement();
				deleteIt.executeUpdate(sqlString);
				deleteIt.close();
			}
		} catch (Exception e) {
			throwError.append("Error at execute sql (deleteCostComponentZTFCST). " + e);
		}
		
		if (throwError.toString().equals(""))
		{

			//Get Component file ZTGLCC.
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getZtglccData";
				parmClass.addElement(company);
				parmClass.addElement(fYear);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getZtglccData). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
				
					// for each record with new Item add to the work File.
					while (rs.next() && throwError.toString().equals(""))
					{
						try {//build the sql.
							requestType = "addZtglccse";
							Vector parmClass = new Vector();
							parmClass.addElement(rs);
							sqlString = buildSqlStatement(requestType, parmClass);
								
							if (throwError.toString().equals(""))
							{
								try {
									addIt = conn.createStatement();
									addIt.executeUpdate(sqlString);
									addIt.close();
								} catch (Exception e) {
									throwError.append("Error executing sql (addZtglccse)" + e);
								}
							}
						} catch (Exception e) {
							throwError.append("Error build sql (addZtglccse). " + e);
						}
					}
					
					rs.close();
					findIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (getZtglccData). " + e);
				}
			}
		
		}
		
		//Update Component definitions.
		try //build sql.
		{
			Vector parmClass = new Vector();
			requestType = "getMccompData";
			parmClass.addElement(company);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (getMccompData). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findIt = conn.createStatement();
				rs = findIt.executeQuery(sqlString);
			
				// for each cost component update the work File.
				while (rs.next() && throwError.toString().equals(""))
				{
					try {//build the sql.
						requestType = "updateZtglccseText";
						Vector parmClass = new Vector();
						parmClass.addElement(rs);
						sqlString = buildSqlStatement(requestType, parmClass);
							
						if (throwError.toString().equals(""))
						{
							try {
								updateIt = conn.createStatement();
								updateIt.executeUpdate(sqlString);
								updateIt.close();
							} catch (Exception e) {
								throwError.append("Error executing sql (updateZtglccseText)" + e);
							}
						}
					} catch (Exception e) {
						throwError.append("Error build sql (updateZtglccseText). " + e);
					}
				}
					
				rs.close();
				findIt.close();
			} catch(Exception e) {
				throwError.append("Error at execute sql (getMccompData). " + e);
			}
		}
		
		
		if (throwError.toString().equals(""))
		{
			//Get Component file ZTGLCCSE.
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getZtglccseData";
				parmClass.addElement(company);
				parmClass.addElement(fYear);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getZtglccseData). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
				
					// for each record with new Item add to the work File.
					while (rs.next() && throwError.toString().equals(""))
					{
						try {//build the sql.
							requestType = "addseToFcst";
							Vector parmClass = new Vector();
							parmClass.addElement(rs);
							parmClass.addElement("COMPONENT");
							sqlString = buildSqlStatement(requestType, parmClass);
								
							if (throwError.toString().equals(""))
							{
								try {
									addIt = conn.createStatement();
									addIt.executeUpdate(sqlString);
									addIt.close();
								} catch (Exception e) {
									throwError.append("Error executing sql (addseToFcst)" + e);
								}
							}
						} catch (Exception e) {
							throwError.append("Error build sql (addseToFcst). " + e);
						}
					}
					
					rs.close();
					findIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (getZtglccseData). " + e);
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
	
			if (findIt != null)
				findIt.close();
			if (addIt != null)
				addIt.close();
			if (deleteIt != null)
				deleteIt.close();
			if (updateIt != null)
				updateIt.close();
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildCostCompSingles(String,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * Build Consumed Cost Entries 
 * For Current Fiscal Year By Type
 * 
 * @param String: cost type
 * @return 
 * @throws Exception
 */
public static void buildConsumedCostCurrFiscalYear(String costType, String company)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildConsumedCostCurrFiscalYear(costType, company, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildConsumedCostCurrFiscalYear(" + costType + "). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 * Build Consumed Cost Entries 
 * For Current Fiscal Year By Type
 * @param conn.
 * @param cost type.
 * @return Connection.
 */
private static Connection buildConsumedCostCurrFiscalYear(String costType, 
													  String company,
													  Connection conn)

	throws Exception 
{
	StringBuffer throwError     = new StringBuffer();
    ResultSet rs                = null;
	java.sql.Statement findIt   = null;
	java.sql.Statement addIt    = null;
	java.sql.Statement deleteIt = null;
	String requestType          = "";
	String sqlString            = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		String currFiscalYearDate = "";
		int fYear = Integer.parseInt(dt.getM3FiscalYear());
		fYear = fYear - 1;
		currFiscalYearDate = "" + fYear + "0801";
		String chgDate = dt.getDateFormatyyyyMMdd();
		String chgTime = dt.getTimeFormathhmmss();
		String point   = "START";
		
		//delete matching records prior to build.
		try
		{
			//use date and cost type to remove prior entries. 
			Vector parmClass = new Vector();
			requestType = "deleteConsumedCostComponentEntries";
			parmClass.addElement(currFiscalYearDate);
			parmClass.addElement(costType);
			parmClass.addElement(company);
			parmClass.addElement(point);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (deleteConsumedCostComponentEntries). " + e);
		}
		
		try
		{
			if (throwError.toString().equals(""))
			{
				deleteIt = conn.createStatement();
				deleteIt.executeUpdate(sqlString);
				deleteIt.close();
			}
		} catch (Exception e) {
			throwError.append("Error at execute sql (deleteCunsummedCostComponentEntries). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			//Get Consumed data.
			// note: for this statement only one A04 or A05 component can be defined in file MCCCDF.
			//       The cumsummed Item will be built twice if they both exist. 
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getMcbomsStartFiscalYear";
				parmClass.addElement(currFiscalYearDate);
				parmClass.addElement(costType);
				parmClass.addElement(company);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getMcbomsStartFiscalYear). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
					
					String lastCono  = "";
					String lastFaci  = "";
					String lastPItno = "";
					String lastCDate = "";
				
					// for each record with new Produced Item add to the work File.
					while (rs.next() && throwError.toString().equals(""))
					{
						try {//build the sql.
							if (!rs.getString("KUCONO").trim().equals(lastCono.trim()) ||
								!rs.getString("KUITNO").trim().equals(lastPItno.trim()) ||
								!rs.getString("KUFACI").trim().equals(lastFaci.trim()) )
							{
								lastCono = rs.getString("KUCONO").trim();
								lastPItno = rs.getString("KUITNO").trim();
								lastFaci = rs.getString("KUFACI").trim();
								lastCDate = rs.getString("KUPCDT").trim();
							}
							
							if (lastCDate.equals(rs.getString("KUPCDT").trim()) )
							{
								requestType = "addciToFcst";
								Vector parmClass = new Vector();
								parmClass.addElement(dt.getM3FiscalYear());
								parmClass.addElement(currFiscalYearDate);
								parmClass.addElement(point);
								parmClass.addElement("0");
								parmClass.addElement(rs);
								parmClass.addElement(chgDate);
								parmClass.addElement(chgTime);
								parmClass.addElement("CONSUMED");
								
								//add element 'A05' amount to the item cost if the cost model contains it.
								
								// if cost type is zero then skip the cost retrival
								if (rs.getString("M9VAMT") != null &&
									rs.getString("M9VAMT").equals("0"))
								{
									parmClass.addElement("0");
								} else
								{
									if (rs.getString("KRMATC") == null)
										parmClass.addElement("0");
									else
									{
										if (rs.getString("KMELCO") == null) 
											parmClass.addElement(rs.getString("KRMATC"));
										else
										{
											BigDecimal krmatc = new BigDecimal(rs.getString("KRMATC"));
											BigDecimal krca05 = new BigDecimal(rs.getString("KRCA05"));
											BigDecimal krca04 = new BigDecimal(rs.getString("KRCA04"));
											krmatc = krmatc.add(krca04);
											krmatc = krmatc.add(krca05);
											parmClass.addElement(krmatc.toString());
										}
									}
								}
								
								sqlString = buildSqlStatement(requestType, parmClass);
								
								//
								
								if (throwError.toString().equals(""))
								{
									try {
										addIt = conn.createStatement();
										addIt.executeUpdate(sqlString);
										addIt.close();
									} catch (Exception e) {
										throwError.append("Error executing sql (addciToFcst)" + e);
									}
								}
								
							}
						} catch (Exception e) {
							throwError.append("Error build sql (addciToFcst)" + e);
						}
					}
					
					rs.close();
					findIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (getMcbomsStartFiscalYear). " + e);
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
			if (findIt != null)
				findIt.close();
			if (addIt != null)
				addIt.close();
			if (deleteIt != null)
				deleteIt.close();
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildConsumedCostCurrFiscalYear(String,String,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * Update produced item operations data
 * and budget quantity
 * 
 * @return 
 * @throws Exception
 */
public static void updateProductionItemData(String company, String timing)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = updateProductionItemData(company, timing, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("updateProducedItemOperationsData(). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}


/**
 * Update produced item operations data
 * and budget quantity
 * 
 * @param conn.
 * @return Connection.
 */
private static Connection updateProductionItemData(String company, 
												   String timing,
												   Connection conn)

	throws Exception 
{
	StringBuffer throwError         = new StringBuffer();
    ResultSet rsZtfcst              = null;
//20090806    ResultSet rsMpdope			    = null;
    ResultSet rsCspafcst		    = null;
	java.sql.Statement findZtfcst   = null;
//20090806	java.sql.Statement findMpdope   = null;
	java.sql.Statement findCspafcst = null;
	java.sql.Statement updateIt     = null;
	String requestType              = "";
	String sqlString                = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		int fYear = Integer.parseInt(dt.getM3FiscalYear());
		
		if (timing.equals("NEXT"))
				fYear = fYear + 1;
		
		String year = "" + fYear;
		
		//update operations data
		if (throwError.toString().equals(""))
		{
			//Get Costing data.
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getZtfcstForOperationsUpdate";
				parmClass.addElement(company);
				parmClass.addElement(year);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getZtfcstForOperationsUpdate). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findZtfcst = conn.createStatement();
					rsZtfcst   = findZtfcst.executeQuery(sqlString);
					
					// for each record update operations fields.
					while (rsZtfcst.next() && throwError.toString().equals(""))
					{
//20090806						//get the operations data.
//20090806						try {//build the sql.
//20090806							requestType = "getMpdopeByZtfcst";
//20090806							Vector parmClass = new Vector();
//20090806							parmClass.addElement(rsZtfcst);
//20090806							sqlString = buildSqlStatement(requestType, parmClass);
//20090806						} catch(Exception e) {
//20090806							throwError.append("Error at build sql (getMpdopeByZtfcst). " + e);
//20090806						}
								
//20090806						// execute sql.
//20090806						if (throwError.toString().equals(""))
//20090806						{
//20090806							try {		
//20090806								findMpdope = conn.createStatement();
//20090806								rsMpdope   = findMpdope.executeQuery(sqlString);
								
//20090806								BigDecimal ctcd = new BigDecimal("0");
//20090806								BigDecimal prnp = new BigDecimal("0");
//20090806								int cstDte      = rsZtfcst.getBigDecimal("CSTDTE").intValue();
								
								//accumulate any/all entries.
//20090806								while (rsMpdope.next() && throwError.toString().equals(""))
//20090806								{
//20090806									//verify from and to dates in the MPDOPE file.
//20090806									if (rsMpdope.getBigDecimal("POFDAT").intValue() == 0 ||
//20090806										rsMpdope.getBigDecimal("POFDAT").intValue() <= cstDte)
//20090806									{
//20090806										if (rsMpdope.getBigDecimal("POTDAT").intValue() >= cstDte)
//20090806										{
//20090806											ctcd = ctcd.add(rsMpdope.getBigDecimal("POCTCD"));
//20090806											prnp = prnp.add(rsMpdope.getBigDecimal("POPRNP"));
//20090806										}
//20090806									}
//20090806								}

						BigDecimal ctcd = new BigDecimal("0");
						BigDecimal prnp = new BigDecimal("0");
						
						if (rsZtfcst.getString("KVCTCD") != null)
							ctcd = new BigDecimal(rsZtfcst.getString("KVCTCD"));
						
						if (rsZtfcst.getString("KVCTCD") != null)
							prnp = new BigDecimal(rsZtfcst.getString("KVPRNP"));
							

								//update the ZTFCST record (operations fields).
								if (ctcd.intValue() != 0 ||
									prnp.intValue() != 0 )
								{
									//build the sql
									try {		
										requestType = "updateZtfcstOperationsData";
										Vector parmClass = new Vector();
										parmClass.addElement(rsZtfcst);
										parmClass.addElement(ctcd);
										parmClass.addElement(prnp);
										sqlString = buildSqlStatement(requestType, parmClass);
												
										if (throwError.toString().equals(""))
										{
											try {
												updateIt = conn.createStatement();
												updateIt.executeUpdate(sqlString);
												updateIt.close();
											} catch (Exception e) {
												throwError.append("Error executing sql (updateZtfcstOperationsData)" + e);
											}
										}
									} catch (Exception e) {
										throwError.append("Error build sql (updateZtfcstOperationsData)" + e);
									}
								}
//							} catch (Exception e) {
//								throwError.append("Error executing sql (getMpdopeByZtfcst). " + e);
//							}
//						}
									
//20090806						findMpdope.close();
//20090806						rsMpdope.close();
					}

					
					rsZtfcst.close();
					findZtfcst.close();
					
				} catch(Exception e) {
					throwError.append("Error at execute sql (getZtfcstForOperationsUpdate). " + e);
				}
			}
		}
		
		//update budget quantity
		if (throwError.toString().equals(""))
		{
			//Get Budget data.
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getCspafcst";
				parmClass.addElement(company);
				parmClass.addElement(year);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getCspafcst). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findCspafcst = conn.createStatement();
					rsCspafcst   = findCspafcst.executeQuery(sqlString);
					
					// for each record update operations fields.
					while (rsCspafcst.next() && throwError.toString().equals(""))
					{

						//update the ZTFCST record (budget fields).

						//build the sql
						try {		
							requestType = "updateZtfcstBudgetData";
							Vector parmClass = new Vector();
							parmClass.addElement(company);
							parmClass.addElement(rsCspafcst);
							sqlString = buildSqlStatement(requestType, parmClass);
												
							if (throwError.toString().equals(""))
							{
								try {
									updateIt = conn.createStatement();
									updateIt.executeUpdate(sqlString);
									updateIt.close();
								} catch (Exception e) {
									throwError.append("Error executing sql (updateZtfcstBudgetData)" + e);
								}
							}
						} catch (Exception e) {
							throwError.append("Error build sql (updateZtfcstBudgetData)" + e);
						}
					}
					
					rsCspafcst.close();
					findCspafcst.close();
					
				} catch(Exception e) {
					throwError.append("Error at execute sql (getCspafcst). " + e);
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
			if (findZtfcst != null)
				findZtfcst.close();
			if (updateIt != null)
				updateIt.close();
//20090806			if (findMpdope != null)
//20090806				findMpdope.close();
			if (findCspafcst != null)
				findCspafcst.close();
			if (rsZtfcst != null)
				rsZtfcst.close();
//20090806			if (rsMpdope != null)
//20090806				rsMpdope.close();
			if (rsCspafcst != null)
				rsCspafcst.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("updateProducedItemOperationsData(String,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * Update The Cost Field of
 * Consumed Items That Are 
 * Also Produced.
 * 
 * @param String: company
 * @return 
 * @throws Exception
 */
public static void updateConsumedProducedItemCost(String company, String timing)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = updateConsumedProducedItemCost(company, timing, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("updateConsumedProducedItemCost(" + company + "). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 * Update The Cost Field of
 * Consumed Items That Are 
 * Also Produced.
 * 
 * @param company.
 * @param connection.
 * @return Connection.
 */
private static Connection updateConsumedProducedItemCost(String company, 
											   			 String timing,
											   			 Connection conn)

	throws Exception 
{
	StringBuffer throwError     = new StringBuffer();
    ResultSet rs                = null;
	java.sql.Statement findIt   = null;
	java.sql.Statement updateIt = null;
	String requestType          = "";
	String sqlString            = "";
	
	try { //enable finally.

		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		int fYear = Integer.parseInt(dt.getM3FiscalYear());
		
		if (timing.equals("NEXT"))
				fYear = fYear + 1;
		
		String year = "" + fYear;
		
		//Get Component definitions.
		try //build sql.
		{
			Vector parmClass = new Vector();
			requestType = "getZtfcstConsumedProduced";
			parmClass.addElement(company);
			parmClass.addElement(year);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("Error at build sql (getZtfcstConsumedProduced). " + e);
		}
		
		// execute sql.
		if (throwError.toString().equals(""))
		{
			try {		
				findIt = conn.createStatement();
				rs = findIt.executeQuery(sqlString);
			
				//update cost for consumed Items that are produced.
				while (rs.next() && throwError.toString().equals(""))
				{
					//MITFAC (M9VAMT-Inventory Accounting method is set to 1.
					//An entry exists in ZTGLCC and MITMAS.
					if (rs.getString("bCONO")  != null &&
						rs.getString("M9VAMT") != null &&
						rs.getString("MMWAPC") != null)
					{
						BigDecimal amt = rs.getBigDecimal("aSIQT");
						BigDecimal cst = rs.getBigDecimal("bCMTOT");
						BigDecimal tot = amt.multiply(cst);
						tot = tot.setScale(4, RoundingMode.HALF_UP);

						try {//build the sql.
							requestType = "updateZtfcst";
							Vector parmClass = new Vector();
							parmClass.addElement(rs);
							parmClass.addElement(cst);
							parmClass.addElement(tot);
							sqlString = buildSqlStatement(requestType, parmClass);
								
							if (throwError.toString().equals(""))
							{
								try {
									updateIt = conn.createStatement();
									updateIt.executeUpdate(sqlString);
									updateIt.close();
								} catch (Exception e) {
									throwError.append("Error executing sql (updateZtfcst)" + e);
								}
							}
						} catch (Exception e) {
							throwError.append("Error build sql (updateZtfcst). " + e);
						}
					}
				}
					
				rs.close();
				findIt.close();
			} catch(Exception e) {
				throwError.append("Error at execute sql (getZtfcstConsumedProduced). " + e);
			}
		}
	} catch (Exception e){ //catch all
		throwError.append(" Catch all exception. " + e);
	}
					
	finally
	{
		try
		{
	
			if (findIt != null)
				findIt.close();
			if (updateIt != null)
				updateIt.close();
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("updateConsumedProducedItemCost(String,Conn). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}

  return conn;
}

/**
 * Update Cost Component Total Entries. 
 * 
 * @return 
 * @throws Exception
 */
public static void updateCostCompTotalFields(String company, String year)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = updateCostCompTotalFields(company, year, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("updateCostCompTotalFields(). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 * Update Cost Component Total Field Entries 
 * Total of all components and total of cost model for item.
 * @param conn.
 * @return Connection.
 */
private static Connection updateCostCompTotalFields(String company, String year, Connection conn)

	throws Exception 
{
	StringBuffer throwError       = new StringBuffer();
    ResultSet rsZtglcc            = null;
    ResultSet rsMcccdf			  = null;
	java.sql.Statement findZtglcc = null;
	java.sql.Statement findMcccdf = null;
	java.sql.Statement updateIt   = null;
	String requestType            = "";
	String sqlString              = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		
		
		
		if (throwError.toString().equals(""))
		{
			//Get Costing data.
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getZtglccData2";
				parmClass.addElement(company);
				parmClass.addElement(year);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getZtglccData2). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findZtglcc = conn.createStatement();
					rsZtglcc   = findZtglcc.executeQuery(sqlString);
					
					String lastCostingStruct = "";
					String lastStructDate    = "";
					Vector costingStructComponents = new Vector();
					BigDecimal totAll = new BigDecimal("0");
					BigDecimal totCostingStruct = new BigDecimal("0");
					
					// for each record update total fields.
					while (rsZtglcc.next() && throwError.toString().equals(""))
					{
						// verify theleft outer join to mcccdf was successful.
						if (rsZtglcc.getString("KOCONO") != null)
						{
							//get the associated costing structure components.
							String costingStruct = rsZtglcc.getString("KOPRCM").trim();
							String structDate    = rsZtglcc.getString("KOSTRD");
						
							if (!costingStruct.equals(lastCostingStruct) ||
									!structDate.equals(lastStructDate))
							{
								try {//build the sql.
									lastCostingStruct = costingStruct;
									lastStructDate    = structDate;
									requestType = "getMcccdfComponents";
									Vector parmClass = new Vector();
									parmClass.addElement(rsZtglcc); //use mchead fields
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch(Exception e) {
									throwError.append("Error at build sql (getMcccdfComponents). " + e);
								}
								
								// execute sql.
								if (throwError.toString().equals(""))
								{
									try {		
										findMcccdf = conn.createStatement();
										rsMcccdf   = findMcccdf.executeQuery(sqlString);
										costingStructComponents = new Vector();
										
										//for components to vector.
										while (rsMcccdf.next() && throwError.toString().equals(""))
										{
											costingStructComponents.addElement(rsMcccdf.getString("KMELCO").trim());
										}
									} catch (Exception e) {
										throwError.append("Error executing sql (getMcccdfComponents) for TTSTD. " + e);
									}
									
									findMcccdf.close();
									rsMcccdf.close();
								}
							}
							
							//accumlate total fields.
							if (throwError.toString().equals(""))
							{
								totAll = new BigDecimal("0");
								totCostingStruct = new BigDecimal("0");
								
								if (costingStructComponents.contains("A01"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("A01"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("A01"));
								
								if (costingStructComponents.contains("A02"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("A02"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("A02"));
								
								if (costingStructComponents.contains("A03"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("A03"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("A03"));
								
								if (costingStructComponents.contains("A04"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("A04"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("A04"));
								
								if (costingStructComponents.contains("A05"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("A05"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("A05"));
								
								if (costingStructComponents.contains("B01"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B01"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B01"));
								
								if (costingStructComponents.contains("B02"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B02"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B02"));
								
								if (costingStructComponents.contains("B03"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B03"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B03"));
								
								if (costingStructComponents.contains("B04"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B04"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B04"));
								
								if (costingStructComponents.contains("B05"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B05"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B05"));
								
								if (costingStructComponents.contains("B06"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B06"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B06"));
								
								if (costingStructComponents.contains("B07"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B07"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B07"));
								
								if (costingStructComponents.contains("B08"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B08"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B08"));
								
								if (costingStructComponents.contains("B09"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B09"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B09"));
								
								if (costingStructComponents.contains("B10"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B10"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B10"));
								
								if (costingStructComponents.contains("B11"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B11"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B11"));
								
								if (costingStructComponents.contains("B12"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B12"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B12"));
								
								if (costingStructComponents.contains("B13"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B13"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B13"));
								
								if (costingStructComponents.contains("B14"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("B14"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("B14"));
								
								if (costingStructComponents.contains("C01"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("C01"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("C01"));
								
								if (costingStructComponents.contains("C02"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("C02"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("C02"));
								
								if (costingStructComponents.contains("D01"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("D01"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("D01"));
								
								if (costingStructComponents.contains("D02"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("D02"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("D02"));
								
								if (costingStructComponents.contains("E01"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("E01"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("E01"));
								
								if (costingStructComponents.contains("E02"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("E02"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("E02"));
								
								if (costingStructComponents.contains("E03"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("E03"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("E03"));
								
								if (costingStructComponents.contains("E04"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("E04"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("E04"));
								
								if (costingStructComponents.contains("E05"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("E05"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("E05"));
								
								if (costingStructComponents.contains("E06"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("E06"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("E06"));
								
								if (costingStructComponents.contains("E07"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("E07"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("E07"));
								
								if (costingStructComponents.contains("E08"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("E08"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("E08"));
								
								if (costingStructComponents.contains("E09"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("E09"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("E09"));
								
								if (costingStructComponents.contains("E10"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("E10"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("E10"));
								
								if (costingStructComponents.contains("SU1"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("SU1"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("SU1"));
								
								if (costingStructComponents.contains("SU2"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("SU2"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("SU2"));
								
								if (costingStructComponents.contains("SU3"))
									totCostingStruct = totCostingStruct.add(rsZtglcc.getBigDecimal("SU3"));
								totAll = totAll.add(rsZtglcc.getBigDecimal("SU3"));
							}
							
							//update the ZTGLCC record (total fields).
							//build the sql
							try {		
								requestType = "updateZtglcc";
								Vector parmClass = new Vector();
								parmClass.addElement(rsZtglcc);
								parmClass.addElement(totCostingStruct);
								parmClass.addElement(totAll);
								sqlString = buildSqlStatement(requestType, parmClass);
									
								if (throwError.toString().equals(""))
								{
									try {
										updateIt = conn.createStatement();
										updateIt.executeUpdate(sqlString);
										updateIt.close();
									} catch (Exception e) {
										throwError.append("Error executing sql (updateZtglcc)" + e);
									}
								}
							} catch (Exception e) {
								throwError.append("Error build sql (updateZtglcc)" + e);
							}
						} else
							costingStructComponents = new Vector();
					}
					
					rsZtglcc.close();
					findZtglcc.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (getZtglccData2). " + e);
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
			if (findZtglcc != null)
				findZtglcc.close();
			if (updateIt != null)
				updateIt.close();
			if (findMcccdf != null)
				findMcccdf.close();
			if (rsZtglcc != null)
				rsZtglcc.close();
			if (rsMcccdf != null)
				rsMcccdf.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("updateCostCompTotalFields(String,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * Build Cost Component Entries 
 * For Current Blank (Not Start or End)
 * All Start and End builds must be complete
 * prior to this execution.
 * 
 * @param String: cost type
 * @return 
 * @throws Exception
 */
public static void buildCostCompCurrBlank(String costType, String company)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildCostCompCurrBlank(costType, company, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildCostCompCurrBlank(" + costType + "). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 * Build Cost Component Entries 
 * For Current Blank (Not Start or End)
 * All Start and End builds must be complete
 * prior to this execution.
 * 
 * @param conn.
 * @param cost type.
 * @return Connection.
 */
private static Connection buildCostCompCurrBlank(String costType, 
												  String company,
												  Connection conn)

	throws Exception 
{
	StringBuffer throwError     = new StringBuffer();
    ResultSet rs                = null;
	java.sql.Statement findIt   = null;
	java.sql.Statement addIt    = null;
	java.sql.Statement deleteIt = null;
	String requestType          = "";
	String sqlString            = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		String chgDate = dt.getDateFormatyyyyMMdd();
		String chgTime = dt.getTimeFormathhmmss();
		String currFiscalYearEndDate = "";
		String currFiscalYearStartDate = "";
		currFiscalYearEndDate = dt.getM3FiscalYear() + "0731";
		BigDecimal startYear  = new BigDecimal(dt.getM3FiscalYear());
		startYear = startYear.subtract(new BigDecimal("1"));
		currFiscalYearStartDate = startYear.toString() + "0801";

		String currDate = "";
		
		if (dt.getMonth().equals("07") && dt.getDay().equals("31"))
		{
			currDate = dt.getDateFormatyyyyMMdd();
		} else {
			//dt = UtilityDateTime.addDaysToDate(dt, -1);
			currDate = dt.getDateFormatyyyyMMdd();
		}
		
		String point = "";
		
		//delete matching records prior to build.
		try
		{
			//use date and cost type to remove prior entries. 
			Vector parmClass = new Vector();
			requestType = "deleteCostComponentEntries";
			parmClass.addElement(currFiscalYearEndDate);
			parmClass.addElement(costType);
			parmClass.addElement(company);
			parmClass.addElement(point);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (deleteCostComponentEntries). " + e);
		}
		
		try
		{
			if (throwError.toString().equals(""))
			{
				deleteIt = conn.createStatement();
				deleteIt.executeUpdate(sqlString);
				deleteIt.close();
			}
		} catch (Exception e) {
			throwError.append("Error at execute sql (deleteCostComponentEntries). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			//Get Costing data.
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getMccomlIntoZtglccAsBlanks";
				parmClass.addElement(currFiscalYearEndDate);
				parmClass.addElement(costType);
				parmClass.addElement(company);
				parmClass.addElement(currFiscalYearStartDate);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getMccomlIntoZtglccAsBlanks). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
				
				
					// for each record with new Item add to the work File.
					while (rs.next() && throwError.toString().equals(""))
					{
						try {//build the sql.
							requestType = "addZtglcc";
							Vector parmClass = new Vector();
							parmClass.addElement(dt.getM3FiscalYear());
							parmClass.addElement("0");
							parmClass.addElement(point);
							parmClass.addElement(currDate);
							parmClass.addElement(rs);
							parmClass.addElement(chgDate);
							parmClass.addElement(chgTime);
							sqlString = buildSqlStatement(requestType, parmClass);
								
							if (throwError.toString().equals(""))
							{
								try {
									addIt = conn.createStatement();
									addIt.executeUpdate(sqlString);
									addIt.close();
								} catch (Exception e) {
									throwError.append("Error executing sql (addZtglcc)" + e);
								}
							}
						} catch (Exception e) {
							throwError.append("Error build sql (addZtglcc)" + e);
						}
					}
					
					rs.close();
					findIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (getMccomlIntoZtglccAsBlanks). " + e);
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
			if (findIt != null)
				findIt.close();
			if (addIt != null)
				addIt.close();
			if (deleteIt != null)
				deleteIt.close();
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildCostCompCurrBlank(String,String,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * Remove Duplicate Costs. 
 * 
 * @return 
 * @throws Exception
 */
public static void removeDuplicateCosts(String company, String year)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = removeDuplicateCosts(company, year, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("removeDuplicateCosts(). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 * Remove Duplicate Costs 
 * 
 * Test to see if I have any duplicates in the ZTGLCC file for the combo of
 * Co, Faci, Itno, Fyear, cstdte, point (start,end,blank), pctp,
 * duplicate STRT entries like MIA, STD, or empty.
 * 
 * @param conn.
 * @return Connection.
 */
private static Connection removeDuplicateCosts(String company, String year, Connection conn)

	throws Exception 
{
	StringBuffer throwError       = new StringBuffer();
    ResultSet rsZtglcc            = null;
    ResultSet rsMcccdf			  = null;
	java.sql.Statement findZtglcc = null;
	java.sql.Statement deleteIt   = null;
	String requestType            = "";
	String sqlString              = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		
		if (throwError.toString().equals(""))
		{
			//Get Duplicate entries.
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getDuplicateEntries";
				parmClass.addElement(company);
				parmClass.addElement(year);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getDuplicateEntries). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findZtglcc = conn.createStatement();
					rsZtglcc   = findZtglcc.executeQuery(sqlString);
					
					// for each record delete the duplicate.
					while (rsZtglcc.next() && throwError.toString().equals(""))
					{
						// verify the left outer joins were successful.
						if (rsZtglcc.getString("M9UCOS") != null &&
							rsZtglcc.getString("MBPUIT") != null &&
							rsZtglcc.getString("MMITDS") != null)
						{
							//verify rule compliance.
							// mitbal- co/whse/itno contains the itno designation as manufactured or not (1 = manfu).
							int manfu = rsZtglcc.getInt("MBPUIT");
							BigDecimal mitfacCost = rsZtglcc.getBigDecimal("M9UCOS");
							BigDecimal strtManuf  = rsZtglcc.getBigDecimal("acmtot"); 
							BigDecimal strtDist   = rsZtglcc.getBigDecimal("ccmtot");
							String allowDelete    = "no";
							String strtValue      = "xxx";
							
							//if manufactured then the non blank strt entry must match the Mitfac cost.
							if (manfu ==1 )
							{
								strtValue = rsZtglcc.getString("cstrt").trim();
								int compare = strtManuf.compareTo(mitfacCost);
								
								if (compare == 0)
									allowDelete = "yes";
							} else {
								strtValue   = rsZtglcc.getString("astrt");
								int compare = strtDist.compareTo(mitfacCost);
								
								if (compare == 0)
									allowDelete = "yes";
							}
						
							//verify a delete can continue.
							if (allowDelete.equals("yes"))
							{
								//build the delete sql
								try {
									requestType = "deleteDuplicateComponents";
									Vector parmClass = new Vector();
									parmClass.addElement(strtValue);
									parmClass.addElement(rsZtglcc);
									sqlString = buildSqlStatement(requestType, parmClass);
								} catch(Exception e) {
									throwError.append("Error at build sql (deleteDuplicateComponents). " + e);
								}
									
								if (throwError.toString().equals(""))
								{
									try {
										deleteIt = conn.createStatement();
										deleteIt.executeUpdate(sqlString);
										deleteIt.close();
									} catch (Exception e) {
										throwError.append("Error executing sql (deleteDuplicateComponents)" + e);
									}
								}
							}
						} 
					}
					
					rsZtglcc.close();
					findZtglcc.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (getDuplicateEntries). " + e);
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
			if (findZtglcc != null)
				findZtglcc.close();
			if (deleteIt != null)
				deleteIt.close();
			if (rsZtglcc != null)
				rsZtglcc.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("removeDuplicateCosts(String,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	return conn;
}

/**
 * Build Consumed Cost Entries 
 * For Next Fiscal Year By Type
 * 
 * @param String: cost type
 * @return 
 * @throws Exception
 */
public static void buildConsumedCostNextFiscalYear(String costType, String company)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildConsumedCostNextFiscalYear(costType, company, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildConsumedCostNextFiscalYear(" + costType + "). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 * Build Consumed Cost Entries 
 * For Next Fiscal Year By Type
 * @param conn.
 * @param cost type.
 * @return Connection.
 */
private static Connection buildConsumedCostNextFiscalYear(String costType, 
													  String company,
													  Connection conn)

	throws Exception 
{
	StringBuffer throwError     = new StringBuffer();
    ResultSet rs                = null;
	java.sql.Statement findIt   = null;
	java.sql.Statement addIt    = null;
	java.sql.Statement deleteIt = null;
	String requestType          = "";
	String sqlString            = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		String nextFiscalYearDate = "";
		int fYear = Integer.parseInt(dt.getM3FiscalYear());
		fYear = fYear + 1;
		String fiscalYear = "" + fYear;
		nextFiscalYearDate = dt.getM3FiscalYear() + "0801";
		String chgDate = dt.getDateFormatyyyyMMdd();
		String chgTime = dt.getTimeFormathhmmss();
		String point   = "START";
		
		//delete matching records prior to build.
		try
		{
			//use date and cost type to remove prior entries. 
			Vector parmClass = new Vector();
			requestType = "deleteConsumedCostComponentEntries";
			parmClass.addElement(nextFiscalYearDate);
			parmClass.addElement(costType);
			parmClass.addElement(company);
			parmClass.addElement(point);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (deleteConsumedCostComponentEntries). " + e);
		}
		
		try
		{
			if (throwError.toString().equals(""))
			{
				deleteIt = conn.createStatement();
				deleteIt.executeUpdate(sqlString);
				deleteIt.close();
			}
		} catch (Exception e) {
			throwError.append("Error at execute sql (deleteCunsummedCostComponentEntries). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			//Get Costing data.
			// note: for this statement only one A04 or A05 component can be defined in file MCCCDF.
			//       The cumsummed Item will be built twice if they both exist. 
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getMcbomsStartFiscalYear";
				parmClass.addElement(nextFiscalYearDate);
				parmClass.addElement(costType);
				parmClass.addElement(company);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getMcbomsStartFiscalYear). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
					
					String lastCono  = "";
					String lastFaci  = "";
					String lastPItno = "";
					String lastCDate = "";
				
					// for each record with new Produced Item add to the work File.
					while (rs.next() && throwError.toString().equals(""))
					{
						try {//build the sql.
							if (!rs.getString("KUCONO").trim().equals(lastCono.trim()) ||
								!rs.getString("KUITNO").trim().equals(lastPItno.trim()) ||
								!rs.getString("KUFACI").trim().equals(lastFaci.trim()))
							{
								lastCono = rs.getString("KUCONO").trim();
								lastPItno = rs.getString("KUITNO").trim();
								lastFaci = rs.getString("KUFACI").trim();
								lastCDate = rs.getString("KUPCDT").trim();
							}
							
							if (lastCDate.equals(rs.getString("KUPCDT").trim()) )
							{
								requestType = "addciToFcst";
								Vector parmClass = new Vector();
								parmClass.addElement(fiscalYear);
								parmClass.addElement(nextFiscalYearDate);
								parmClass.addElement(point);
								parmClass.addElement("0");
								parmClass.addElement(rs);
								parmClass.addElement(chgDate);
								parmClass.addElement(chgTime);
								parmClass.addElement("CONSUMED");
									
								
								// if cost type is zero then skip the cost retrival
								if (rs.getString("M9VAMT") != null &&
									rs.getString("M9VAMT").trim().equals("0"))
								{
									parmClass.addElement("0");
								} else
								{
									if (rs.getString("KRMATC") == null)
										parmClass.addElement("0");
									else
									{
										if (rs.getString("KMELCO") == null) 
											parmClass.addElement(rs.getString("KRMATC"));
										else
										{
											BigDecimal krmatc = new BigDecimal(rs.getString("KRMATC"));
											BigDecimal krca04 = new BigDecimal(rs.getString("KRCA04"));
											BigDecimal krca05 = new BigDecimal(rs.getString("KRCA05"));
											krmatc = krmatc.add(krca04);
											krmatc = krmatc.add(krca05);
											parmClass.addElement(krmatc.toString());
										}
									}
								}

								sqlString = buildSqlStatement(requestType, parmClass);
								
								if (throwError.toString().equals(""))
								{
									try {
										addIt = conn.createStatement();
										addIt.executeUpdate(sqlString);
										addIt.close();
									} catch (Exception e) {
										throwError.append("Error executing sql (addciToFcst)" + e);
									}
								}
								
							}
						} catch (Exception e) {
							throwError.append("Error build sql (addciToFcst)" + e);
						}
					}
					
					rs.close();
					findIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (getMcbomsStartFiscalYear). " + e);
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
			if (findIt != null)
				findIt.close();
			if (addIt != null)
				addIt.close();
			if (deleteIt != null)
				deleteIt.close();
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildConsumedCostNextFiscalYear(String,String,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * Build Consumed Cost Entries 
 * For Current Date By Type
 * 
 * @param String: cost type
 * @return 
 * @throws Exception
 */
public static void buildConsumedCostCurrToDate(String costType, String company)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildConsumedCostCurrToDate(costType, company, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildConsumedCostCurrToDate(" + costType + "). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 * Build Consumed Cost Entries 
 * For Current Date By Type
 * @param cost type.
 * @param company.
 * @return Connection.
 */
private static Connection buildConsumedCostCurrToDate(String costType, 
												  String company,
												  Connection conn)

	throws Exception 
{
	StringBuffer throwError     	= new StringBuffer();
    ResultSet rs                	= null;
    ResultSet rsMccopu				= null;
	java.sql.Statement findIt   	= null;
	java.sql.Statement findMccopu 	= null;
	java.sql.Statement addIt    	= null;
	java.sql.Statement deleteIt 	= null;
	String requestType         		= "";
	String sqlString            	= "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		String chgDate = dt.getDateFormatyyyyMMdd();
		String chgTime = dt.getTimeFormathhmmss();
		String currFiscalYearEndDate = "";
		String currFiscalYearStartDate = "";
		currFiscalYearEndDate = dt.getM3FiscalYear() + "0731";
		BigDecimal startYear  = new BigDecimal(dt.getM3FiscalYear());
		startYear = startYear.subtract(new BigDecimal("1"));
		currFiscalYearStartDate = startYear.toString() + "0801";
		String currDate = dt.getDateFormatyyyyMMdd();
		String point = "END";
		
		//delete matching records prior to build.
		try
		{
			//use date and cost type to remove prior entries. 
			Vector parmClass = new Vector();
			requestType = "deleteConsumedCostComponentEntries";
			parmClass.addElement(currFiscalYearEndDate);
			parmClass.addElement(costType);
			parmClass.addElement(company);
			parmClass.addElement(point);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (deleteConsumedCostComponentEntries). " + e);
		}
		
		try
		{
			if (throwError.toString().equals(""))
			{
				deleteIt = conn.createStatement();
				deleteIt.executeUpdate(sqlString);
				deleteIt.close();
			}
		} catch (Exception e) {
			throwError.append("Error at execute sql (deleteCunsummedCostComponentEntries). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			//Get Costing data.
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getMcbomsAsOfDate";
				parmClass.addElement(currDate);
				parmClass.addElement(costType);
				parmClass.addElement(company);
				parmClass.addElement(currFiscalYearStartDate);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getMcbomsAsOfDate). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
					
					String lastCono = "";
					String lastFaci = "";
					String lastPItno = "";
					String lastCDate = "";
				
					// for each record with new Item add to the work File.
					while (rs.next() && throwError.toString().equals(""))
					{
						try {//build the sql.
							if (!rs.getString("KUCONO").trim().equals(lastCono.trim()) ||
								!rs.getString("KUITNO").trim().equals(lastPItno.trim()) ||
								!rs.getString("KUFACI").trim().equals(lastFaci.trim()))
							{
								lastCono = rs.getString("KUCONO").trim();
								lastPItno = rs.getString("KUITNO").trim();
								lastFaci = rs.getString("KUFACI").trim();
								lastCDate = rs.getString("KUPCDT").trim();
							}
							
							if (lastCDate.equals(rs.getString("KUPCDT").trim()) )
							{
								// get the consumed item cost entry for the most recent date
								// from the MCCOPU file if one was not found on the previous 
								// sql join statement.
								String krmatc = "0";
								String krca05 = "0";
								String krca04 = "0";
								
								if (lastPItno.equals("201555"))
								{
									String stophere = "yes";
								}
 
								if (rs.getString("KRMATC") == null)
								{
									try {//build the sql
										Vector parmClass = new Vector();
										requestType = "getMccopuItemCost";
										parmClass.addElement(currDate);
										parmClass.addElement(costType);
										parmClass.addElement(company);
										parmClass.addElement(currFiscalYearStartDate);
										parmClass.addElement(rs.getString("KUMTNO"));
										parmClass.addElement(rs.getString("KUFACI"));
										sqlString = buildSqlStatement(requestType, parmClass);
									} catch (Exception e) {
										throwError.append("Error executing sql (getMccopuItemCost)" + e);
									}
									
									try {//execute sql
										if (throwError.toString().equals(""))
										{
											findMccopu = conn.createStatement();
											rsMccopu   = findMccopu.executeQuery(sqlString);
										}
									} catch (Exception e) {
										throwError.append("Error at execute sql (getMccopuItemCost). " + e);
									}
									
									if (rsMccopu.next() && throwError.toString().equals(""))
									{
										krmatc = rsMccopu.getString("KRMATC");
										krca04 = rsMccopu.getString("KRCA04");
										krca05 = rsMccopu.getString("KRCA05");
									}
									
									try {
										findMccopu.close();
									} catch (Exception e) {
									}
								} else
								{
									krmatc = rs.getString("KRMATC");
									krca04 = rs.getString("KRCA04");
									krca05 = rs.getString("KRCA05");
								}
									
								if (rs.getString("KMELCO") != null)
								{
									BigDecimal matc = new BigDecimal(krmatc);
									BigDecimal ca05 = new BigDecimal(krca05);
									BigDecimal ca04 = new BigDecimal(krca04);
									matc = matc.add(ca04);
									matc = matc.add(ca05);
									krmatc = matc.toString();
								}
								
								if (rs.getString("M9VAMT") != null &&
									rs.getString("M9VAMT").equals("0"))
								{
									krmatc = "0";
								}
									
								requestType = "addciToFcst";
								Vector parmClass = new Vector();
								parmClass.addElement(dt.getM3FiscalYear());
								parmClass.addElement(currFiscalYearEndDate);
								parmClass.addElement(point);
								parmClass.addElement(currDate);
								parmClass.addElement(rs);
								parmClass.addElement(chgDate);
								parmClass.addElement(chgTime);
								parmClass.addElement("CONSUMED");
								parmClass.addElement(krmatc);
								sqlString = buildSqlStatement(requestType, parmClass);
								
								if (throwError.toString().equals(""))
								{
									try {
										addIt = conn.createStatement();
										addIt.executeUpdate(sqlString);
										addIt.close();
									} catch (Exception e) {
										throwError.append("Error executing sql (addciToFcst)" + e);
									}
								}
								
							}
						} catch (Exception e) {
							throwError.append("Error build sql (addciToFcst)" + e);
						}
					}
					
					rs.close();
					findIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (getMcbomsAsOfDate). " + e);
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
			if (findIt != null)
				findIt.close();
			if (addIt != null)
				addIt.close();
			if (deleteIt != null)
				deleteIt.close();
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildConsumedCostCurrToDate(String,String,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * Add extra records with zero quantities to 
 * allow comaprisions between years via 
 * left outer joins in sql.
 * 
 * @return 
 * @throws Exception
 */
public static void addComparisonExtrasZTFCST(String company, String fYear)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = addComparisonExtrasZTFCST(company, fYear, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("addComparisonExtrasZTFCST(). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 * Add extra records with zero quantities to 
 * allow comaprisions between years via 
 * left outer joins in sql.
 * 
 * This method will find records that exist in the file ZTFCST 
 * for a fiscal year that do not have matching entries in
 * the following fiscal year for the same key criteria in the 
 * same file. 
 * For all the records found a matching record is added to the file
 * with all quantity and amount fields set to zero and the fiscal
 * year changed from the current year to the next year.
 * 
 * The incoming year will be added to and then output.
 * 
 * @param conn.
 * @return Connection.
 */
private static Connection addComparisonExtrasZTFCST(String company, 
											  String fYear,
											  Connection conn)

	throws Exception 
{
	StringBuffer throwError         = new StringBuffer();
    ResultSet rsZtfcst              = null;
	java.sql.Statement findZtfcst   = null;
	java.sql.Statement addIt        = null;
	java.sql.Statement deleteIt		= null;
	String requestType              = "";
	String sqlString                = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		int intYear = Integer.parseInt(fYear);
		
		String nextFYear = "" + (intYear + 1); //current year plus one
		
		//delete extras prior to build.
		if (throwError.toString().equals(""))
		{
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "deleteExtrasZTFCST";
				parmClass.addElement(company);   //company          
				parmClass.addElement(nextFYear); //next fiscal year
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (deleteExtrasZTFCST). " + e);
			}
			
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {
					deleteIt = conn.createStatement();
					deleteIt.executeUpdate(sqlString);
					deleteIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (deleteExtrasZTFCST). " + e);
				}
			}
		}
		
		//find the non matching records.
		if (throwError.toString().equals(""))
		{
			//Get Costing data.
			//
			// Find the missing records in 2010 and put empties there.
			// END vs START
			// fYear vs. NextFYear
			// costType 3 vs costType 2
			try //build sql.
			{
				// really set these as variables to run multiple updates.
				// points, cost types,
				
				Vector parmClass = new Vector();
				requestType = "getZtfcstNonMatching";
				parmClass.addElement("START");   //point in file    (not exists)
				parmClass.addElement(nextFYear); //next fiscal year (not exists)
				parmClass.addElement("2");		 //cost type        (not exists)
				parmClass.addElement("END");     //point in file    (exists)
				parmClass.addElement(fYear);     //requested year   (exists)
				parmClass.addElement("3");       //cost type        (exists)
				parmClass.addElement(company);   //company          (exists)
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getZtfcstNonMatching). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findZtfcst = conn.createStatement();
					rsZtfcst   = findZtfcst.executeQuery(sqlString);
					
					// for each record add an emtry.
					while (rsZtfcst.next() && throwError.toString().equals(""))
					{
						//add the new zero record.
						try {//build the sql.
							requestType = "addEmptyToZtfcst";
							Vector parmClass = new Vector();
							parmClass.addElement(rsZtfcst);		  // old record into new
							parmClass.addElement(nextFYear);	  // new fiscal year
							parmClass.addElement("START"); 		  // new point
							parmClass.addElement(fYear + "0801"); // new fiscal date
							parmClass.addElement("2");			  // new cost type
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (addEmptyToZtfcst). " + e);
						}
								
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								addIt = conn.createStatement();
								addIt.executeUpdate(sqlString);
								addIt.close();
							} catch (Exception e) {
								throwError.append("Error executing sql (addEmptyToZtfcst)" + e);
							}
						}
					}
					
					rsZtfcst.close();
					findZtfcst.close();
					
				} catch(Exception e) {
					throwError.append("Error at execute sql (getZtfcstNonMatching). " + e);
				}
			}
		}
		
		//find the non matching records.
		if (throwError.toString().equals(""))
		{
			//Get Costing data.
			//
			// Find the missing records in 2010 and put empties there.
			// END vs START
			// fYear vs. NextFYear
			// costType 3 vs costType 3
			try //build sql.
			{
				// really set these as variables to run multiple updates.
				// points, cost types,
				
				Vector parmClass = new Vector();
				requestType = "getZtfcstNonMatching";
				parmClass.addElement("START");   //point in file    (not exists)
				parmClass.addElement(nextFYear); //next fiscal year (not exists)
				parmClass.addElement("3");		 //cost type        (not exists)
				parmClass.addElement("END");     //point in file    (exists)
				parmClass.addElement(fYear);     //requested year   (exists)
				parmClass.addElement("3");       //cost type        (exists)
				parmClass.addElement(company);   //company          (exists)
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getZtfcstNonMatching). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findZtfcst = conn.createStatement();
					rsZtfcst   = findZtfcst.executeQuery(sqlString);
					
					// for each record add an emtry.
					while (rsZtfcst.next() && throwError.toString().equals(""))
					{
						//add the new zero record.
						try {//build the sql.
							requestType = "addEmptyToZtfcst";
							Vector parmClass = new Vector();
							parmClass.addElement(rsZtfcst);		  // old record into new
							parmClass.addElement(nextFYear);	  // new fiscal year
							parmClass.addElement("START"); 		  // new point
							parmClass.addElement(fYear + "0801"); // new fiscal date
							parmClass.addElement("3");			  // new cost type
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (addEmptyToZtfcst). " + e);
						}
								
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								addIt = conn.createStatement();
								addIt.executeUpdate(sqlString);
								addIt.close();
							} catch (Exception e) {
								throwError.append("Error executing sql (addEmptyToZtfcst)" + e);
							}
						}
					}
					
					rsZtfcst.close();
					findZtfcst.close();
					
				} catch(Exception e) {
					throwError.append("Error at execute sql (getZtfcstNonMatching). " + e);
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
			if (findZtfcst != null)
				findZtfcst.close();
			if (addIt != null)
				addIt.close();
			if (rsZtfcst != null)
				rsZtfcst.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("addComparisonExtrasZTFCST(co#,fYear,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * Add extra records with zero quantities to 
 * allow comaprisions between years via 
 * left outer joins in sql.
 * 
 * @return 
 * @throws Exception
 */
public static void addComparisonExtrasCSPAFCST(String company, String fYear)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = addComparisonExtrasCSPAFCST(company, fYear, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("addComparisonExtrasCSPAFCST(). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 * Add extra records with zero quantities to 
 * allow comaprisions between years via 
 * left outer joins in sql.
 * 
 * This method will find records that exist in the file CSPAFCST
 * for a fiscal year that do not have matching entries in
 * the following fiscal year for the same key criteria in the 
 * same file. 
 * For all the records found a matching record is added to the file
 * with all quantity and amount fields set to zero and the fiscal
 * year changed from the current year to the next year.
 * 
 * The incoming year will be added to and then output.
 * 
 * @param conn.
 * @return Connection.
 */
private static Connection addComparisonExtrasCSPAFCST(String company, 
													  String fYear,
													  Connection conn)

	throws Exception 
{
	StringBuffer throwError         = new StringBuffer();
    ResultSet rsCspafcst             = null;
	java.sql.Statement findCspafcst  = null;
	java.sql.Statement addIt        = null;
	java.sql.Statement deleteIt		= null;
	String requestType              = "";
	String sqlString                = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		int intYear = Integer.parseInt(fYear);
		
		String nextFYear = "" + (intYear + 1); //current year plus one
		
		//delete extras prior to build.
		if (throwError.toString().equals(""))
		{
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "deleteExtrasCSPAFCST";         
				parmClass.addElement(nextFYear); //next fiscal year
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (deleteExtrasCSPAFCST). " + e);
			}
			
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {
					deleteIt = conn.createStatement();
					deleteIt.executeUpdate(sqlString);
					deleteIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (deleteExtrasCSPAFCST). " + e);
				}
			}
		}
		
		//find the non matching records.
		if (throwError.toString().equals(""))
		{
			//Get Budget Forecast Quantities.
			//
			// Find the missing records in 2010 and put empties there.
			// fYear vs. NextFYear
			try //build sql.
			{
				// really set these as variables to run multiple updates.
				// points, cost types,
				
				Vector parmClass = new Vector();
				requestType = "getCspafcstNonMatching";
				parmClass.addElement(nextFYear); //next fiscal year (not exists)
				parmClass.addElement(fYear);     //requested year   (exists)
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getCspafcstNonMatching). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findCspafcst = conn.createStatement();
					rsCspafcst   = findCspafcst.executeQuery(sqlString);
					
					// for each record add an emtry.
					while (rsCspafcst.next() && throwError.toString().equals(""))
					{
						//add the new zero record.
						try {//build the sql.
							requestType = "addEmptyToCspafcst";
							Vector parmClass = new Vector();
							parmClass.addElement(rsCspafcst);	  // old record into new
							parmClass.addElement(nextFYear);	  // new fiscal year
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (addEmptyToCspafcst). " + e);
						}
								
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								addIt = conn.createStatement();
								addIt.executeUpdate(sqlString);
								addIt.close();
							} catch (Exception e) {
								throwError.append("Error executing sql (addEmptyToCspafcst)" + e);
							}
						}
					}
					
					rsCspafcst.close();
					findCspafcst.close();
					
				} catch(Exception e) {
					throwError.append("Error at execute sql (getCspafcstNonMatching). " + e);
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
			if (findCspafcst != null)
				findCspafcst.close();
			if (addIt != null)
				addIt.close();
			if (rsCspafcst != null)
				rsCspafcst.close();
			if (deleteIt != null)
				deleteIt.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("addComparisonExtrasCSPAFCST(co#,fYear,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * Add extra records with zero quantities to 
 * allow comaprisions between years via 
 * left outer joins in sql.
 * 
 * @return 
 * @throws Exception
 */
public static void addComparisonExtrasZTGLCC(String company, String fYear)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = addComparisonExtrasZTGLCC(company, fYear, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("addComparisonExtrasZTGLCC(). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}

/**
 * Add extra records with zero quantities to 
 * allow comaprisions between years via 
 * left outer joins in sql.
 * 
 * This method will find records that exist in the file ZTGLCC 
 * for a fiscal year that do not have matching entries in
 * the following fiscal year for the same key criteria in the 
 * same file. 
 * For all the records found a matching record is added to the file
 * with all quantity and amount fields set to zero and the fiscal
 * year changed from the current year to the next year.
 * 
 * The incoming year will be added to and then output.
 * 
 * @param conn.
 * @return Connection.
 */
private static Connection addComparisonExtrasZTGLCC(String company, 
													String fYear,
													Connection conn)

	throws Exception 
{
	StringBuffer throwError         = new StringBuffer();
    ResultSet rsZtglcc              = null;
	java.sql.Statement findZtglcc   = null;
	java.sql.Statement addIt        = null;
	java.sql.Statement deleteIt		= null;
	String requestType              = "";
	String sqlString                = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		int intYear = Integer.parseInt(fYear);
		
		String nextFYear = "" + (intYear + 1); //current year plus one
		
		//delete extras prior to build.
		if (throwError.toString().equals(""))
		{
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "deleteExtrasZTGLCC";
				parmClass.addElement(company);   //company          
				parmClass.addElement(nextFYear); //next fiscal year
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (deleteExtrasZTGLCC). " + e);
			}
			
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {
					deleteIt = conn.createStatement();
					deleteIt.executeUpdate(sqlString);
					deleteIt.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (deleteExtrasZTGLCC). " + e);
				}
			}
		}
		
		//find the non matching records.
		if (throwError.toString().equals(""))
		{
			//Get Costing data.
			//
			// Find the missing records in 2010 and put empties there.
			// END vs START
			// fYear vs. NextFYear
			// costType 3 vs costType 2
			try //build sql.
			{
				// really set these as variables to run multiple updates.
				// points, cost types,
				
				Vector parmClass = new Vector();
				requestType = "getZtglccNonMatching";
				parmClass.addElement("START");   //point in file    (not exists)
				parmClass.addElement(nextFYear); //next fiscal year (not exists)
				parmClass.addElement("2");		 //cost type        (not exists)
				parmClass.addElement("END");     //point in file    (exists)
				parmClass.addElement(fYear);     //requested year   (exists)
				parmClass.addElement("3");       //cost type        (exists)
				parmClass.addElement(company);   //company          (exists)
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getZtglccNonMatching). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findZtglcc = conn.createStatement();
					rsZtglcc   = findZtglcc.executeQuery(sqlString);
					
					// for each record add an emtry.
					while (rsZtglcc.next() && throwError.toString().equals(""))
					{
						//add the new zero record.
						try {//build the sql.
							requestType = "addEmptyToZtglcc";
							Vector parmClass = new Vector();
							parmClass.addElement(rsZtglcc);		  // old record into new
							parmClass.addElement(nextFYear);	  // new fiscal year
							parmClass.addElement("START"); 		  // new point
							parmClass.addElement(fYear + "0801"); // new fiscal date
							parmClass.addElement("2");			  // new cost type
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (addEmptyToZtglcc). " + e);
						}
								
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								addIt = conn.createStatement();
								addIt.executeUpdate(sqlString);
								addIt.close();
							} catch (Exception e) {
								throwError.append("Error executing sql (addEmptyToZtfcst)" + e);
							}
						}
					}
					
					rsZtglcc.close();
					findZtglcc.close();
					
				} catch(Exception e) {
					throwError.append("Error at execute sql (getZtglccNonMatching). " + e);
				}
			}
		}
			
			
		//find the non matching records.
		if (throwError.toString().equals(""))
		{
			//Get Costing data.
			//
			// Find the missing records in 2010 and put empties there.
			// END vs START
			// fYear vs. NextFYear
			// costType 3 vs costType 3
			try //build sql.
			{
				// really set these as variables to run multiple updates.
				// points, cost types,
				
				Vector parmClass = new Vector();
				requestType = "getZtglccNonMatching";
				parmClass.addElement("START");   //point in file    (not exists)
				parmClass.addElement(nextFYear); //next fiscal year (not exists)
				parmClass.addElement("3");		 //cost type        (not exists)
				parmClass.addElement("END");     //point in file    (exists)
				parmClass.addElement(fYear);     //requested year   (exists)
				parmClass.addElement("3");       //cost type        (exists)
				parmClass.addElement(company);   //company          (exists)
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getZtglccNonMatching). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findZtglcc = conn.createStatement();
					rsZtglcc   = findZtglcc.executeQuery(sqlString);
					
					// for each record add an emtry.
					while (rsZtglcc.next() && throwError.toString().equals(""))
					{
						//add the new zero record.
						try {//build the sql.
							requestType = "addEmptyToZtglcc";
							Vector parmClass = new Vector();
							parmClass.addElement(rsZtglcc);		  // old record into new
							parmClass.addElement(nextFYear);	  // new fiscal year
							parmClass.addElement("START"); 		  // new point
							parmClass.addElement(fYear + "0801"); // new fiscal date
							parmClass.addElement("3");			  // new cost type
							sqlString = buildSqlStatement(requestType, parmClass);
						} catch(Exception e) {
							throwError.append("Error at build sql (addEmptyToZtglcc). " + e);
						}
								
						// execute sql.
						if (throwError.toString().equals(""))
						{
							try {
								addIt = conn.createStatement();
								addIt.executeUpdate(sqlString);
								addIt.close();
							} catch (Exception e) {
								throwError.append("Error executing sql (addEmptyToZtfcst)" + e);
							}
						}
					}
					
					rsZtglcc.close();
					findZtglcc.close();
					
				} catch(Exception e) {
					throwError.append("Error at execute sql (getZtglccNonMatching). " + e);
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
			if (findZtglcc != null)
				findZtglcc.close();
			if (addIt != null)
				addIt.close();
			if (deleteIt != null)
				deleteIt.close();
			if (rsZtglcc != null)
				rsZtglcc.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("addComparisonExtrasZTGLCC(co#,fYear,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}

/**
 * For next fiscal year runs the clear and 
 * rebuild of costing work files. 
 * 
 * @return 
 * @throws Exception
 */
public static void rebuildCostingWorkFilesForNextYear(String company)
{
	StringBuffer throwError = new StringBuffer();
	
	//methods to rebuild costing work files.
	try {
		//determine current and next fiscal year
		DateTime dt = UtilityDateTime.getSystemDate(); //Get current system date.
		int fYear = Integer.parseInt(dt.getM3FiscalYear());
		String currentFiscalYear = "" + fYear;
		fYear = fYear + 1;
		String nextFiscalYear = "" + fYear;
		
		//Rebuild ZTGLCC 
		buildCostCompNextFiscalYear("2", company);//Start (next fiscal year)
		buildCostCompNextFiscalYear("3", company);//Start (next fiscal year)
		updateCostCompTotalFields(company, nextFiscalYear);//cost model total - ztglcc
		removeDuplicateCosts(company, nextFiscalYear);//multiple strt's - ztglcc


		// delete/add extra records. 
		//	This allows sql to left outer join to an existing
		//  2009 entry if one did not exist in 2010.
		
		//if current or next ficsal year are rebuilt.
		// New entries for either year require extra's to be rebuilt.
		// This method will find records that exist in the file CSPAFCST
		// for a fiscal year that do not have matching entries in
		// the following fiscal year for the same key criteria in the 
		// same file. 
		// For all the records found a matching record is added to the file
		// with all quantity and amount fields set to zero and the fiscal
		// year changed from the current year to the next year.


		addComparisonExtrasCSPAFCST(company, currentFiscalYear);
		addComparisonExtrasZTGLCC(company, currentFiscalYear);
		
		//builds, totals, and extras need to run first. 
		buildCostCompSingles(company, "NEXT");

				
		
		//Rebuild Consumed (ci) Costing ZTFCST
		//run the update methods by year
		buildConsumedCostNextFiscalYear("2", company);
		buildConsumedCostNextFiscalYear("3", company);
		updateConsumedProducedItemCost(company, "NEXT");//consumed cost
		updateProductionItemData(company, "NEXT");//operations-labor /budg qty
						
		// if either current or next fiscal year is updated this must run.
		addComparisonExtrasZTFCST(company, currentFiscalYear); // additional 2009 entries against 2010
		
		
		//Clear and build the Totals file ZTFCSTTL for next year.
		buildReportTotalsFile("next", "100");
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("rebuildCostingWorkFilesForNextYear(co#). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	return;
}

/**
 * For current fiscal year runs the clear and 
 * rebuild of costing work files. 
 * 
 * @return 
 * @throws Exception
 */
public static void rebuildCostingWorkFilesForCurrentYear(String company)
{
	StringBuffer throwError = new StringBuffer();
	
	//methods to rebuild costing work files.
	try {
		//determine current and next fiscal year
		DateTime dt = UtilityDateTime.getSystemDate(); //Get current system date.
		int fYear = Integer.parseInt(dt.getM3FiscalYear());
		String currentFiscalYear = "" + fYear;
		fYear = fYear + 1;
		String nextFiscalYear = "" + fYear;

		buildCostCompCurrFiscalYear("2", company);//Start (curr fiscal year)
		buildCostCompCurrFiscalYear("3", company);//Start (curr fiscal year)
		buildCostCompCurrToDate("2", company);//End (curr fiscal year)
		buildCostCompCurrToDate("3", company);//End (curr fiscal year)
		buildCostCompCurrBlank("2", company);//Blank (curr fiscal year)-not start or end
		buildCostCompCurrBlank("3", company);//Blank (curr fiscal year)-not start or end
		updateCostCompTotalFields(company, currentFiscalYear);//cost model total - ztglcc
		removeDuplicateCosts(company, currentFiscalYear);//multiple strt's - ztglcc

				
		//if current or next ficsal year are rebuilt.
		// New entries for either year require extra's to be rebuilt.
		// This method will find records that exist in the file CSPAFCST
		// for a fiscal year that do not have matching entries in
		// the following fiscal year for the same key criteria in the 
		// same file. 
		// For all the records found a matching record is added to the file
		// with all quantity and amount fields set to zero and the fiscal
		// year changed from the current year to the next year.

		addComparisonExtrasCSPAFCST(company, currentFiscalYear);
		addComparisonExtrasZTGLCC(company, currentFiscalYear);
				
		//builds, totals, and extras need to run first. 
		//buildCostCompSingles(company, "NEXT");
		buildCostCompSingles(company, "CURR");
		
		
		//Rebuild Consumed (ci) Costing ZTFCST
	
		//run the update methods by year
		buildConsumedCostCurrFiscalYear("2", company);
		buildConsumedCostCurrFiscalYear("3", company);
		buildConsumedCostCurrToDate("2", company);
		buildConsumedCostCurrToDate("3", company);
				
		// if either curr or next is executed this must run.
		addComparisonExtrasZTFCST(company, currentFiscalYear); // additional curr entries against next
				
		updateConsumedProducedItemCost(company, "CURR");//consumed cost 
		updateProductionItemData(company, "CURR");//operations-labor/budg qty
		
		//Clear and build the Totals file ZTFCSTTL for current year.
		buildReportTotalsFile("current", "100");
	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("rebuildCostingWorkFilesForCurrentYear(co#). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	return;
}

/**
 * For next fiscal year runs the clear and 
 * rebuild of costing work files. 
 * 
 * @return 
 * @throws Exception
 */
public static void rebuildCostingWorkFilesForCurrentAndNextYear(String company)
{
	StringBuffer throwError = new StringBuffer();
	
	//methods to rebuild costing work files.
	try {
		//determine current and next fiscal year
		DateTime dt = UtilityDateTime.getSystemDate(); //Get current system date.
		int fYear = Integer.parseInt(dt.getM3FiscalYear());
		String currentFiscalYear = "" + fYear;
		fYear = fYear + 1;
		String nextFiscalYear = "" + fYear;
		
		//Rebuild ZTGLCC 
		buildCostCompNextFiscalYear("2", company);//Start (next fiscal year)
		buildCostCompNextFiscalYear("3", company);//Start (next fiscal year)
		
		buildCostCompCurrFiscalYear("2", company);//Start (curr fiscal year)
		buildCostCompCurrFiscalYear("3", company);//Start (curr fiscal year)
		buildCostCompCurrToDate("2", company);//End (curr fiscal year)
		buildCostCompCurrToDate("3", company);//End (curr fiscal year)
		buildCostCompCurrBlank("2", company);//Blank (curr fiscal year)-not start or end
		buildCostCompCurrBlank("3", company);//Blank (curr fiscal year)-not start or end
		updateCostCompTotalFields(company, currentFiscalYear);//cost model total - ztglcc
		updateCostCompTotalFields(company, nextFiscalYear);//cost model total - ztglcc
		removeDuplicateCosts(company, currentFiscalYear);//multiple strt's - ztglcc
		removeDuplicateCosts(company, nextFiscalYear);//multiple strt's - ztglcc

				
		//if current or next ficsal year are rebuilt.
		// New entries for either year require extra's to be rebuilt.
		// This method will find records that exist in the file CSPAFCST
		// for a fiscal year that do not have matching entries in
		// the following fiscal year for the same key criteria in the 
		// same file. 
		// For all the records found a matching record is added to the file
		// with all quantity and amount fields set to zero and the fiscal
		// year changed from the current year to the next year.
		
		addComparisonExtrasCSPAFCST(company, currentFiscalYear);
		addComparisonExtrasZTGLCC(company, currentFiscalYear);
				
		//builds, totals, and extras need to run first. 
		buildCostCompSingles(company, "NEXT");
		buildCostCompSingles(company, "CURR");

		
		//Rebuild Consumed (ci) Costing ZTFCST

		//run the update methods by year
		//These needed if updated if Next
		buildConsumedCostNextFiscalYear("2", company);
		buildConsumedCostNextFiscalYear("3", company);
		updateConsumedProducedItemCost(company, "NEXT");//consumed cost
		updateProductionItemData(company, "NEXT");//operations-labor /budg qty
				
		buildConsumedCostCurrFiscalYear("2", company);
		buildConsumedCostCurrFiscalYear("3", company);
		buildConsumedCostCurrToDate("2", company);
		buildConsumedCostCurrToDate("3", company);
				
		// if either curr or next is executed this must run.
		addComparisonExtrasZTFCST(company, currentFiscalYear); // additional current entries against next fiscal year.
				
		updateConsumedProducedItemCost(company, "CURR");//consumed cost 
		updateProductionItemData(company, "CURR");//operations-labor/budg qty
		updateProductionItemData(company, "NEXT");//operations-labor /budg qty
		
		
		//Clear and build the Totals file ZTFCSTTL for current and next years.
		buildReportTotalsFile("current", "100");
		buildReportTotalsFile("next", "100");

	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("rebuildCostingWorkFilesForCurrentAndNextYear(co#). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	return;
}


/**
 * Build Report Totals File 
 * For Current/Next Year.
 * 
 * @param String: cost type
 * @param String: company
 * @return 
 * @throws Exception
 */
public static void buildReportTotalsFile(String yearType, String company)
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	
	//get connection here and execute private update method
	try {
		// get a connection to be sent to find methods
		conn = getDBConnection();
		
		// execute the update method
		conn = buildReportTotalsFile(yearType, company, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {
			if (conn != null)
				conn.close();
		} catch(Exception e){
			throwError.append("Error closing a connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildReportTotalsFile(" + yearType + "). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	
	return;
}


/**
 * Build Report Totals File 
 * For Current/Next Year.
 * 
 * @param String: cost type
 * @param String: company
 * @param Connection: conn
 * @return 
 * @throws Exception
 */
private static Connection buildReportTotalsFile(String yearType, 
												String company,
												Connection conn)

	throws Exception 
{
	StringBuffer throwError     = new StringBuffer();
    ResultSet rs                = null;
	java.sql.Statement findIt   = null;
	java.sql.Statement addIt    = null;
	java.sql.Statement deleteIt = null;
	String requestType          = "";
	String sqlString            = "";
	
	try { //enable finally.
		
		//get the current system date and time (off the AS400).
		DateTime dt = UtilityDateTime.getSystemDate();
		String fiscalYear = "";
		
		if (yearType.equals("current"))
			fiscalYear = dt.getM3FiscalYear();
		
		if (yearType.equals("next"))
		{
			int fYear = Integer.parseInt(dt.getM3FiscalYear());
			fYear = fYear + 1;
			fiscalYear = "" + fYear;
		}
		
		//delete matching records prior to build.
		try
		{
			//use fiscal year to remove prior entries.
			Vector parmClass = new Vector();
			requestType = "deleteReportTotalsEntries";
			parmClass.addElement(company);
			parmClass.addElement(fiscalYear);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append("Error at build sql (deleteReportTotalsEntries). " + e);
		}
		
		try
		{
			if (throwError.toString().equals(""))
			{
				deleteIt = conn.createStatement();
				deleteIt.executeUpdate(sqlString);
				deleteIt.close();
			}
		} catch (Exception e) {
			throwError.append("Error at execute sql (deleteReportTotalsEntries). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			//Get Report File.
			try //build sql.
			{
				Vector parmClass = new Vector();
				requestType = "getZtfcstByFiscalYear";
				parmClass.addElement(company);
				parmClass.addElement(fiscalYear);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("Error at build sql (getZtfcstByFiscalYear). " + e);
			}
		
			// execute sql.
			if (throwError.toString().equals(""))
			{
				try {		
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
					
					String fpass  = "";
					String cono   = "";
					String faci   = "";
					String item   = "";
					String fyea   = "";
					String cstdte = "";
					String strt   = "";
					String pctp   = "";
					String fdate  = "";
					String point  = "";
					String asfdte = "";
					BigDecimal tot = new BigDecimal("0");
				
					// accumulate detail and write report total entries.
					while (rs.next() && throwError.toString().equals(""))
					{
						// first pass only.
						if (fpass.trim().equals(""))
						{
							fpass  = "no";
							cono   = rs.getString("CONO").trim();
							faci   = rs.getString("FACI").trim();
							item   = rs.getString("ITEM").trim();
							fyea   = rs.getString("FYEA").trim();
							cstdte = rs.getString("CSTDTE").trim();
							strt   = rs.getString("STRT").trim();
							pctp   = rs.getString("PCTP").trim();
							fdate  = rs.getString("FDATE").trim();
							point  = rs.getString("POINT").trim();
							asfdte = rs.getString("ASFDTE").trim();
						}
						
						//temp
						//if (item.equals("101798"))
						//{
								//String stopHere = "x";
						//}
						
						if (cono.trim().equals(rs.getString("CONO").trim() ) &&
							faci.trim().equals(rs.getString("FACI").trim() ) &&
							item.trim().equals(rs.getString("ITEM").trim() ) &&
							fyea.trim().equals(rs.getString("FYEA").trim() ) &&
							cstdte.trim().equals(rs.getString("CSTDTE").trim() ) &&
							strt.trim().equals(rs.getString("STRT").trim() ) &&
							pctp.trim().equals(rs.getString("PCTP").trim() ) &&
							fdate.trim().equals(rs.getString("FDATE").trim() ) &&
							point.trim().equals(rs.getString("POINT").trim() ) )
						{
							tot = tot.add(rs.getBigDecimal("RPTAMT"));
						} else
						{
							//write the prior record with total.
							try {//build the sql.
								requestType = "addZtfcsttl";
								Vector parmClass = new Vector();
								parmClass.addElement(cono);
								parmClass.addElement(faci);
								parmClass.addElement(item);
								parmClass.addElement(fyea);
								parmClass.addElement(cstdte);
								parmClass.addElement(strt);
								parmClass.addElement(pctp);
								parmClass.addElement(fdate);
								parmClass.addElement(point);
								parmClass.addElement(asfdte);
								parmClass.addElement(tot);
								sqlString = buildSqlStatement(requestType, parmClass);
									
								if (throwError.toString().equals(""))
								{
									try {
										addIt = conn.createStatement();
										addIt.executeUpdate(sqlString);
										addIt.close();
									} catch (Exception e) {
											throwError.append("Error executing sql (addZtfcsttl)" + e);
									}
								}	
							} catch (Exception e) {
								throwError.append("Error build sql (addZtfcstttl)" + e);
							}
							
							//set total to new total.
							tot    = rs.getBigDecimal("RPTAMT");
							cono   = rs.getString("CONO").trim();
							faci   = rs.getString("FACI").trim();
							item   = rs.getString("ITEM").trim();
							fyea   = rs.getString("FYEA").trim();
							cstdte = rs.getString("CSTDTE").trim();
							strt   = rs.getString("STRT").trim();
							pctp   = rs.getString("PCTP").trim();
							fdate  = rs.getString("FDATE").trim();
							point  = rs.getString("POINT").trim();
							asfdte = rs.getString("ASFDTE").trim();
						}
						
					}
					
					if (fpass.equals("no"))
					{
						//write the last record with total.
						try {//build the sql.
							requestType = "addZtfcsttl";
							Vector parmClass = new Vector();
							parmClass.addElement(cono);
							parmClass.addElement(faci);
							parmClass.addElement(item);
							parmClass.addElement(fyea);
							parmClass.addElement(cstdte);
							parmClass.addElement(strt);
							parmClass.addElement(pctp);
							parmClass.addElement(fdate);
							parmClass.addElement(point);
							parmClass.addElement(asfdte);
							parmClass.addElement(tot);
							sqlString = buildSqlStatement(requestType, parmClass);
								
							if (throwError.toString().equals(""))
							{
								try {
									addIt = conn.createStatement();
									addIt.executeUpdate(sqlString);
									addIt.close();
								} catch (Exception e) {
										throwError.append("Error executing sql (addZtfcsttl)" + e);
								}
							}	
						} catch (Exception e) {
							throwError.append("Error build sql (addZtfcstttl)" + e);
						}
					}
					
					findIt.close();
					rs.close();
				} catch(Exception e) {
					throwError.append("Error at execute sql (getZtfcstByFiscalYear). " + e);
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
			if (findIt != null)
				findIt.close();
			if (addIt != null)
				addIt.close();
			if (deleteIt != null)
				deleteIt.close();
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing statement/resultset. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceFinanceWorkFiles.");
			throwError.append("buildReportTotalsFile(String,String,Conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

  return conn;
}


}



