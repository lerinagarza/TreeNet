/*
 * Created on October 22, 2008
 *
 * Data files accessed.
 * 		
 * 		
 */
package com.treetop.services;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.BeanSpecification;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.app.specification.*;

/**
 * @author twalton
 *
 * Service to maintain Specification Information.
 */
public class ServiceSpecification extends BaseService {
	
	public static final String library = "M3DJDPRD";
	public static final String ttlib = "DBPRD";
	//public static final String ttlib = "TWALTO";
	/**
	 * 
	 */
	public ServiceSpecification() {
		super();
	}
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		try
		{
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
 private static String buildSqlStatement(String inRequestType,
								 	     Vector requestClass)
	throws Exception 
 {
	StringBuffer sqlString = new StringBuffer();
	StringBuffer throwError = new StringBuffer();
	
	try { // Verify Specification
		if (inRequestType.equals("verifyCPGSpec") ||
			inRequestType.equals("verifyCPGSpecStatus"))
		{
			
			// build the sql statement.
			sqlString.append("SELECT  SPPRESNO  ");
			sqlString.append("FROM " + ttlib + ".SPPPHEAD ");
			sqlString.append(" WHERE SPPRESNO = '" + (String) requestClass.elementAt(0) + "' ");
			if (inRequestType.equals("verifyCPGSpecStatus"))
			{
				sqlString.append(" AND SPPRECST = '" + (String) requestClass.elementAt(1)+ "' ");
			}
		}
		
//	**********************************************************************************
//  LIST CPG Specs		
		if (inRequestType.equals("listCPGSpecs"))
		{
			InqSpecification is = (InqSpecification) requestClass.elementAt(0);
			// SPPPHEAD - CPG Specification HEADER
			sqlString.append("SELECT SPPRESNO, SPPREVIS, SPPRECST, SPPFORM, ");
			// MITMAS - Item Master
			sqlString.append("MMITNO, MMITDS, ");
			// MITBAL - Item Warehouse Record
			sqlString.append("MBSLDY ");
			sqlString.append(" FROM " + ttlib + ".SPPPHEAD ");
			sqlString.append(" INNER JOIN " + library + ".MITMAS ");
			sqlString.append("    ON MMITNO = SPPRESNO ");
			sqlString.append(" LEFT OUTER JOIN " + library + ".MITBAL ");
			sqlString.append("    ON MBITNO = MMITNO ");
			if (!is.getInqFormulaName().equals(""))
			{
				sqlString.append(" INNER JOIN " + ttlib + ".SPPLFORM ");
				sqlString.append("    ON SPLFORM = SPPFORM ");
			}
			// WHERE SECTION
			StringBuffer whereString = new StringBuffer();
			if(!is.getInqItemNumber().equals("") ||
			   !is.getInqItemDescription().equals("") ||
			   !is.getInqItemType().equals("") ||
			   !is.getInqProductSize().equals("") ||
			   !is.getInqProductGroup().equals("") ||
			   !is.getInqFormula().equals("") ||
			   !is.getInqFormulaName().equals("") ||
			   !is.getInqRecordStatus().equals(""))
			{ // BUILD THE WHERE SECTION
				if (!is.getInqItemNumber().equals(""))
					whereString.append(" SPPRESNO LIKE '%" + is.getInqItemNumber() + "%' ");
				if (!is.getInqItemDescription().equals(""))
				{
					if (!whereString.toString().trim().equals(""))
						whereString.append(" AND ");
					whereString.append(" UPPER(MMITDS) LIKE '%" + is.getInqItemDescription().toUpperCase() + "%' ");
				}
				if (!is.getInqItemType().equals(""))
				{
					if (!whereString.toString().trim().equals(""))
						whereString.append(" AND ");
					whereString.append(" MMITTY = '" + is.getInqItemType() + "' ");
				}
				if (!is.getInqProductSize().equals(""))
				{
					if (!whereString.toString().trim().equals(""))
						whereString.append(" AND ");
					whereString.append(" MMCFI1 = '" + is.getInqProductSize().trim() + "' ");
				}
				if (!is.getInqProductGroup().equals(""))
				{
					if (!whereString.toString().trim().equals(""))
						whereString.append(" AND ");
					whereString.append(" MMITCL = '" + is.getInqProductGroup() + "' ");
				}
				if (!is.getInqFormula().equals(""))
				{
					if (!whereString.toString().trim().equals(""))
						whereString.append(" AND ");
					whereString.append(" SPPFORM = '" + is.getInqFormula() + "' ");
				}
				if (!is.getInqFormulaName().equals(""))
				{
					if (!whereString.toString().trim().equals(""))
						whereString.append(" AND ");
					whereString.append(" UPPER(SPLNAME) LIKE '%" + is.getInqFormulaName().toUpperCase() + "%' ");
				}
				if (!whereString.toString().trim().equals(""))
					whereString.append(" AND ");
				if (!is.getInqRecordStatus().equals(""))
					whereString.append(" UPPER(SPPRECST) = '" + is.getInqRecordStatus().toUpperCase() + "' ");
				else
					whereString.append(" SPPRECST <> 'DELETE' ");
			}					
			sqlString.append(" WHERE " + whereString.toString());
			// GROUP BY
			sqlString.append(" GROUP BY SPPRESNO, SPPREVIS, SPPRECST, SPPFORM, ");
			sqlString.append("     MMITNO, MMITDS, MBSLDY ");
			// ORDER BY
			sqlString.append(" ORDER BY ");
			if (is.getOrderBy().equals("recordStatus"))
			   sqlString.append("SPPRECST " + is.getOrderStyle() + ", SPPRESNO, SPPREVIS DESC, MBSLDY DESC ");
			if (is.getOrderBy().equals("itemNumber") ||
				is.getOrderBy().equals(""))
				sqlString.append("SPPRESNO " + is.getOrderStyle() + ", SPPREVIS DESC, MBSLDY DESC ");
			if (is.getOrderBy().equals("itemDescription"))
				sqlString.append("MMITDS " + is.getOrderStyle() + ", SPPREVIS DESC, MBSLDY DESC ");
			if (is.getOrderBy().equals("formulaNumber"))
				  sqlString.append("SPPFORM " + is.getOrderStyle() + ", SPPRESNO, SPPREVIS DESC ");
			//if (is.getOrderBy().equals("shelfLife"))
			//	   sqlString.append("SPPRECST " + is.getOrderStyle() + ", SPPRESNO, SPPREVIS DESC ");
			if (is.getOrderBy().equals("revisionDate"))
				sqlString.append("SPPREVIS " + is.getOrderStyle() + ", SPPRESNO, MBSLDY DESC ");
//			if (is.getOrderBy().equals("supercedesDate"))
//				   sqlString.append("SPPRECST " + is.getOrderStyle() + ", SPPRESNO, SPPREVIS DESC ");
		}
		if (inRequestType.equals("listTEST"))
		{
			sqlString.append(" SELECT SPRRESNO, SPRREVIS, SPRTEST, SPRTARGET, SPRMIN, SPRMAX, ");
			sqlString.append("        SPRMETH, SPRSEQ, SPRTVAL, SPRTUOM, SPNTITLE, ");
			sqlString.append("        IDSUNT, IDSDSC, IDSUDS ");
			sqlString.append("  FROM " + ttlib + ".SPPRTEST ");
			sqlString.append("  LEFT OUTER JOIN " + ttlib + ".SPPNMETH ");
			sqlString.append("        ON SPRMETH = SPNMETH AND SPNRECST = 'ACTIVE' ");
			sqlString.append("  LEFT OUTER JOIN " + ttlib + ".IDPSANAL ");
			sqlString.append("        ON SPRTEST = IDSCDE ");
			sqlString.append("  WHERE SPRRESNO = '" + (String) requestClass.elementAt(1) + "' ");
			sqlString.append("    AND SPRREVIS = " + (String) requestClass.elementAt(2));
			sqlString.append("  ORDER BY SPRSEQ, SPRTEST ");
		}
		if (inRequestType.equals("listPROCESS"))
		{
			sqlString.append(" SELECT SPSRESNO, SPSREVIS, SPSPROC, SPSTARGET, SPSMIN, SPSMAX, ");
			sqlString.append("        SPSMETH, SPSSEQ, SPNTITLE, ");
			sqlString.append("        IDSUNT, IDSDSC, IDSUDS ");
			sqlString.append("  FROM " + ttlib + ".SPPSPROC ");
			sqlString.append("  LEFT OUTER JOIN " + ttlib + ".SPPNMETH ");
			sqlString.append("        ON SPSMETH = SPNMETH AND SPNRECST = 'ACTIVE' ");
			sqlString.append("  LEFT OUTER JOIN " + ttlib + ".IDPSANAL ");
			sqlString.append("        ON SPSPROC = IDSCDE ");
			sqlString.append("  WHERE SPSRESNO = '" + (String) requestClass.elementAt(1) + "' ");
			sqlString.append("    AND SPSREVIS = " + (String) requestClass.elementAt(2));
			sqlString.append("  ORDER BY SPSSEQ, SPSPROC ");
		}
//**********************************************************************************
// DROP DOWN LISTS		
//**********************************************************************************
		if (inRequestType.equals("ddTEST") ||
			inRequestType.equals("ddPROCESS"))
		{
			sqlString.append(" SELECT IDSCDE, IDSDSC, IDSUDS ");
			sqlString.append("  FROM " + ttlib + ".IDPSANAL ");
			sqlString.append("  WHERE IDSDLT = ' ' ");
			if (inRequestType.equals("ddTEST"))
				sqlString.append(" AND SUBSTRING(IDSCDE, 1,1) = '1' ");
			else
				sqlString.append(" AND SUBSTRING(IDSCDE, 1,1) = '0' ");
			sqlString.append("  ORDER BY IDSDSC, IDSUDS ");
		}		
// **********************************************************************************
// FIND CPG Specs - ONE		
//	**********************************************************************************
		if (inRequestType.equals("dtlCPGSpecs"))
		{
			DtlSpecification ds = (DtlSpecification) requestClass.elementAt(0);
			// SPPPHEAD - CPG Specification HEADER
			sqlString.append("SELECT SPPRESNO, SPPCOMENT, SPPREF, SPPREVIS, SPPFORM, ");
			sqlString.append("   SPPRECST, ");
			// SPPTPACK -- CPG Specification Packing Information
			sqlString.append(" SPTLEN, SPTWID, SPTHGT, SPTSLIP, SPTSTRW, SPTSTRETCH, ");
			sqlString.append(" SPTSHRW, SPTSHRINK, SPTCODE, SPTCDPRT, SPTCSPRT1, ");
			sqlString.append(" SPTCSPRT2, SPTPRTG, SPTSTORE, SPTSPECI, SPTCOMENT, ");
			// MITMAS - Item Master
			sqlString.append(" MMITNO, MMITDS, MMFUDS, MMITTY, MMITGR, MMITCL, ");
			sqlString.append(" MMATMO, MMUNMS, MMRESP, MMSTAT, MMGRWE, MMFRAG, ");
			// MITBAL - Item Warehouse Record
			sqlString.append(" MBSLDY, ");
			// MSPWITRS -- New Item 
			sqlString.append("MSWCSE, MSWITM, MSWPGT, MSWDTE, MSWTME, MSWUSR, MSWMCO, ");
			// MITAUN -- Alternate Unit of Measure
			sqlString.append("A.MUCOFA, "); // Cases Per Pallet Field number 43 (ServiceItem.LoadFieldsItemWarehouse)
			sqlString.append("B.MUCOFA ");  // Cases Per Layer Field number 44 (ServiceItem.LoadFieldsItemWarehouse)
			sqlString.append(" FROM " + ttlib + ".SPPPHEAD ");
			sqlString.append(" INNER JOIN " + library + ".MITMAS ");
			sqlString.append("    ON MMITNO = SPPRESNO ");
			sqlString.append(" INNER JOIN " + ttlib + ".SPPTPACK ");
			sqlString.append("    ON SPPRESNO = SPTRESNO ");
			sqlString.append("   AND SPPREVIS = SPTREVIS ");
			sqlString.append(" LEFT OUTER JOIN " + library + ".MITBAL ");
			sqlString.append("    ON MBITNO = MMITNO ");
			sqlString.append(" LEFT OUTER JOIN " + ttlib + ".MSPWITRS ");
			sqlString.append("    ON MSWRSC = SPPRESNO ");
			sqlString.append(" LEFT OUTER JOIN " + library + ".MITAUN A ");
			sqlString.append("    ON MMCONO = A.MUCONO AND SPPRESNO = A.MUITNO ");
			sqlString.append("   AND A.MUAUTP = 1 AND A.MUALUN = 'PL' ");
			sqlString.append(" LEFT OUTER JOIN " + library + ".MITAUN B ");
			sqlString.append("    ON MMCONO = B.MUCONO AND SPPRESNO = B.MUITNO ");
			sqlString.append("   AND B.MUAUTP = 1 AND B.MUALUN = 'TIE' ");
			// WHERE SECTION
			sqlString.append(" WHERE SPPRESNO = '" + ds.getItemNumber() + "' ");
			if (!ds.getRevisionDate().equals("") &&
				!ds.getRevisionDate().equals("0"))
			{
			   try
			   {
				   int findSlash = ds.getRevisionDate().indexOf("/");
				   if (findSlash == -1)
					  sqlString.append(" AND SPPREVIS <= " + ds.getRevisionDate() + " ");
				   else
				   {
				      DateTime dtRevision = UtilityDateTime.getDateFromMMddyyWithSlash(ds.getRevisionDate());
			          sqlString.append(" AND SPPREVIS <= " + dtRevision.getDateFormatyyyyMMdd() + " ");
				   }
			   }
			   catch(Exception e)
			   {}
			}
			// ORDER BY
			sqlString.append(" ORDER BY SPPREVIS DESC ");
		}
 // **********************************************************************************
 //  UPDATE
		if (inRequestType.equals("updateCPGSpecHead"))
		{
			// cast the incoming parameter class.
			UpdSpecification fromVb = (UpdSpecification) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("UPDATE " + ttlib + ".SPPPHEAD ");
			sqlString.append(" SET ");
			sqlString.append(" SPPCOMENT = '" + fromVb.getComments().trim() + "', ");
			sqlString.append(" SPPREF = '" + fromVb.getLabBookID().trim() + "', ");
			try
			{
				DateTime dt1 = UtilityDateTime.getDateFromMMddyyyyWithSlash(fromVb.getRevisionDate());
				sqlString.append(" SPPREVIS = " + dt1.getDateFormatyyyyMMdd() + ",");
			}
			catch(Exception e)
			{}
			sqlString.append(" SPPFORM = '" + fromVb.getFormulaNumber().trim() + "',");
			sqlString.append(" SPPRECST = '" + fromVb.getRecordStatus().trim() + "',");
			sqlString.append(" SPPPROG = 'TreeNet Update',");
			sqlString.append(" SPPDATE = " + dt.getDateFormatyyyyMMdd().trim() + ",");
			sqlString.append(" SPPTIME = " + dt.getTimeFormathhmmss() + ",");
			sqlString.append(" SPPUSER = '" + fromVb.getUpdateUser() + "' ");
			sqlString.append(" WHERE SPPRESNO = '" + fromVb.getItemNumber().trim() + "' ");
			sqlString.append("   AND SPPREVIS = ");
			try
			{
				DateTime dt1 = UtilityDateTime.getDateFromMMddyyyyWithSlash(fromVb.getRevisionDateOriginal());
				sqlString.append(dt1.getDateFormatyyyyMMdd() + " ");
			}
			catch(Exception e)
			{}			
		}
		if (inRequestType.equals("updateCPGSpecPack"))
		{
			// cast the incoming parameter class.
			UpdSpecification fromVb = (UpdSpecification) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("UPDATE " + ttlib + ".SPPTPACK ");
			sqlString.append(" SET ");
			try
			{
				DateTime dt1 = UtilityDateTime.getDateFromMMddyyyyWithSlash(fromVb.getRevisionDate());
				sqlString.append(" SPTREVIS = " + dt1.getDateFormatyyyyMMdd() + ",");
			}
			catch(Exception e)
			{}
			sqlString.append(" SPTLEN = " + fromVb.getLength() + ",");
			sqlString.append(" SPTWID = " + fromVb.getWidth() + ",");
			sqlString.append(" SPTHGT = " + fromVb.getHeight() + ",");
			sqlString.append(" SPTSLIP = '" + fromVb.getSlipSheetInformation().trim() + "'," );
			sqlString.append(" SPTSTRW = '" + fromVb.getStretchWrap().trim() + "',");
			sqlString.append(" SPTSTRETCH = '" + fromVb.getStretchWrapDescription().trim() + "',");
			sqlString.append(" SPTSHRW = '" + fromVb.getShrinkWrap().trim() + "',");
			sqlString.append(" SPTSHRINK = '" + fromVb.getShrinkWrapDescription().trim() + "',");
			sqlString.append(" SPTCODE = '" + fromVb.getCodingInformation().trim() + "',");
			sqlString.append(" SPTCDPRT = '" + fromVb.getCaseCodePrint().trim() + "',");
			sqlString.append(" SPTCSPRT1 = '" + fromVb.getCasePrintLine1() + "',");
			sqlString.append(" SPTCSPRT2 = '" + fromVb.getCasePrintLine2() + "',");
			sqlString.append(" SPTPRTG = '" + fromVb.getCasePrintGeneral() + "',");
			sqlString.append(" SPTSTORE = '" + fromVb.getStorageConditions() + "',");
			sqlString.append(" SPTSPECI = '" + fromVb.getSpecialRequirements() + "',");
			sqlString.append(" SPTCOMENT = '" + fromVb.getComments() + "',");
			sqlString.append(" SPTPROG = 'TreeNet Update',");
			sqlString.append(" SPTDATE = " + dt.getDateFormatyyyyMMdd().trim() + ",");
			sqlString.append(" SPTTIME = " + dt.getTimeFormathhmmss() + ",");
			sqlString.append(" SPTUSER = '" + fromVb.getUpdateUser() + "' ");
			sqlString.append(" WHERE SPTRESNO = '" + fromVb.getItemNumber().trim() + "' ");
			sqlString.append("   AND SPTREVIS = ");
			try
			{
				DateTime dt1 = UtilityDateTime.getDateFromMMddyyyyWithSlash(fromVb.getRevisionDateOriginal());
				sqlString.append(dt1.getDateFormatyyyyMMdd() + " ");
			}
			catch(Exception e)
			{}			
		}
		if (inRequestType.equals("updateCPGStatus"))
		{
			// cast the incoming parameter class.
			UpdSpecification fromVb = (UpdSpecification) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("UPDATE " + ttlib + ".SPPPHEAD ");
			sqlString.append(" SET ");
			sqlString.append(" SPPRECST = 'INACTIVE',");
			sqlString.append(" SPPPROG = 'TreeNet Update',");
			sqlString.append(" SPPDATE = " + dt.getDateFormatyyyyMMdd().trim() + ",");
			sqlString.append(" SPPTIME = " + dt.getTimeFormathhmmss() + ",");
			sqlString.append(" SPPUSER = '" + fromVb.getUpdateUser() + "' ");
			sqlString.append(" WHERE SPPRESNO = '" + fromVb.getItemNumber().trim() + "' ");
			sqlString.append(" AND SPPRECST = 'ACTIVE' ");
		}
 // **********************************************************************************
 //  INSERT
		if (inRequestType.equals("insertCPGSpecHead"))
		{
			UpdSpecification fromVb = (UpdSpecification) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlib + ".SPPPHEAD ");
			sqlString.append("(SPPRESNO, SPPREVIS, SPPRECST, SPPODATE, SPPOTIME, ");
			sqlString.append(" SPPPROG, SPPDATE,  SPPTIME,  SPPUSER, SPPWSN) ");
			sqlString.append(" VALUES( ");
			sqlString.append("'" + fromVb.getItemNumber() + "', "); //Item Number - CPGSpec Number
			sqlString.append(fromVb.getRevisionDate() + ","); // Revision Date
			sqlString.append("'PENDING',"); // Value of the ID (attribute)
			sqlString.append(dt.getDateFormatyyyyMMdd() + ",");
			sqlString.append(dt.getTimeFormathhmmss() + ",");
			sqlString.append("'TreeNet - CPG Spec Add',"); // Last Update Program
			sqlString.append(dt.getDateFormatyyyyMMdd() + ",");
			sqlString.append(dt.getTimeFormathhmmss() + ",");
			sqlString.append("'" + fromVb.getUpdateUser() + "',");
			sqlString.append("'Browser' ");
			sqlString.append(") ");	
		}
		if (inRequestType.equals("insertCPGSpecPack"))
		{
			UpdSpecification fromVb = (UpdSpecification) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlib + ".SPPTPACK ");
			sqlString.append("(SPTRESNO, SPTREVIS, SPTSTRW, SPTSHRW, ");
			sqlString.append(" SPTPROG, SPTDATE,  SPTTIME,  SPTUSER, SPTWSN) ");
			sqlString.append(" VALUES( ");
			sqlString.append("'" + fromVb.getItemNumber() + "', "); //Item Number - CPGSpec Number
			sqlString.append(fromVb.getRevisionDate() + ","); // Revision Date
			sqlString.append("'N',"); // Stretch Wrap Default
			sqlString.append("'N',"); // Shrink Wrap Default
			sqlString.append("'TreeNet - CPG Spec Add',"); // Last Update Program
			sqlString.append(dt.getDateFormatyyyyMMdd() + ",");
			sqlString.append(dt.getTimeFormathhmmss() + ",");
			sqlString.append("'" + fromVb.getUpdateUser() + "',");
			sqlString.append("'Browser' ");
			sqlString.append(") ");	
		}
		if (inRequestType.equals("insertProcessParameter"))
		{
			// cast the incoming parameter class.
			UpdSpecificationTestProcess fromVb = (UpdSpecificationTestProcess) requestClass.elementAt(0);
			// get the Date and Time Information
			DateTime revDT = UtilityDateTime.getDateFromMMddyyWithSlash(fromVb.getRevisionDate());
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlib + ".SPPSPROC ");
			sqlString.append("(SPSRESNO, SPSREVIS, SPSPROC, SPSTARGET, SPSMIN, ");
			sqlString.append(" SPSMAX,   SPSMETH,  SPSSEQ,  SPSPROG,  ");
			sqlString.append(" SPSDATE,  SPSTIME,  SPSUSER, SPSWSN) ");
			sqlString.append(" VALUES( ");
			sqlString.append("'" + fromVb.getItemNumber() + "', "); //Item Number - CPGSpec Number
			sqlString.append(revDT.getDateFormatyyyyMMdd() + ","); // Revision Date
			sqlString.append("'" + fromVb.getValueID() + "',"); // Value of the ID (attribute)
			sqlString.append(fromVb.getTarget() + ","); // Target
			sqlString.append(fromVb.getMinimum() + ","); // Minimum
			sqlString.append(fromVb.getMaximum() + ","); // Maximum
			sqlString.append(fromVb.getMethod() + ","); // Method
			sqlString.append(fromVb.getIdSequence() + ","); // Sequence of the Attribute
			sqlString.append("'TreeNet - CPG Spec Update',"); // Last Update Program
			sqlString.append(dt.getDateFormatyyyyMMdd() + ",");
			sqlString.append(dt.getTimeFormathhmmss() + ",");
			sqlString.append("'" + fromVb.getUpdateUser() + "',");
			sqlString.append("'Browser' ");
			sqlString.append(") ");	
		}
		if (inRequestType.equals("insertAnalyticalTest"))
		{
			// cast the incoming parameter class.
			UpdSpecificationTestProcess fromVb = (UpdSpecificationTestProcess) requestClass.elementAt(0);
			// get the Date and Time Information
			DateTime revDT = UtilityDateTime.getDateFromMMddyyWithSlash(fromVb.getRevisionDate());
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlib + ".SPPRTEST ");
			sqlString.append("(SPRRESNO, SPRREVIS, SPRTEST, SPRTARGET, SPRMIN, ");
			sqlString.append(" SPRMAX,   SPRMETH,  SPRSEQ,  SPRPROG,  ");
			sqlString.append(" SPRDATE,  SPRTIME,  SPRUSER, SPRWSN, SPRTVAL, SPRTUOM) ");
			sqlString.append(" VALUES( ");
			sqlString.append("'" + fromVb.getItemNumber() + "', "); //Item Number - CPGSpec Number
			sqlString.append(revDT.getDateFormatyyyyMMdd() + ","); // Revision Date
			sqlString.append("'" + fromVb.getValueID() + "',"); // Value of the ID (attribute)
			sqlString.append(fromVb.getTarget() + ","); // Target
			sqlString.append(fromVb.getMinimum() + ","); // Minimum
			sqlString.append(fromVb.getMaximum() + ","); // Maximum
			sqlString.append(fromVb.getMethod() + ","); // Method
			sqlString.append(fromVb.getIdSequence() + ","); // Sequence of the Attribute
			sqlString.append("'TreeNet - CPG Spec Update',"); // Last Update Program
			sqlString.append(dt.getDateFormatyyyyMMdd() + ",");
			sqlString.append(dt.getTimeFormathhmmss() + ",");
			sqlString.append("'" + fromVb.getUpdateUser() + "',");
			sqlString.append("'Browser',");
			sqlString.append(fromVb.getTestValue() + ",");
			sqlString.append("'" + fromVb.getTestValueUOM() + "' ");
			sqlString.append(") ");	
		}
	// *************
	// Insert from a COPY
		if (inRequestType.equals("copyCPGSpecHead"))
		{
			UpdSpecification fromVb = (UpdSpecification) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlib + ".SPPPHEAD ");
			sqlString.append("(SELECT ");
			sqlString.append("'" + fromVb.getItemNumber() + "', "); //Item Number - Sent in NEW Number
			sqlString.append("SPPCOMENT, SPPREF, ");
			sqlString.append(fromVb.getRevisionDate() + ","); // Revision Date
			sqlString.append("SPPFORM, SPPALUP, ");
			sqlString.append("'PENDING',"); // Value of the ID (attribute)
			sqlString.append(dt.getDateFormatyyyyMMdd() + ",");
			sqlString.append(dt.getTimeFormathhmmss() + ",");			
			sqlString.append("'TreeNet - CPG Spec Add',"); // Last Update Program
			sqlString.append(dt.getDateFormatyyyyMMdd() + ",");
			sqlString.append(dt.getTimeFormathhmmss() + ",");
			sqlString.append("'" + fromVb.getUpdateUser() + "',");
			sqlString.append("'Browser' ");
			sqlString.append(" FROM " + ttlib + ".SPPPHEAD ");
			sqlString.append(" WHERE SPPRESNO = '" + fromVb.getItemNumberCopy().trim() + "' ");
			sqlString.append("   AND SPPREVIS = " + fromVb.getRevisionDateCopy() + " ");
			sqlString.append(") ");	
		}
		if (inRequestType.equals("copyCPGSpecPack"))
		{
			UpdSpecification fromVb = (UpdSpecification) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlib + ".SPPTPACK ");
			sqlString.append("(SELECT ");
			sqlString.append("'" + fromVb.getItemNumber() + "', "); //Item Number - Sent in NEW Number
			sqlString.append(fromVb.getRevisionDate() + ","); // Revision Date
			sqlString.append("SPTLEN, SPTWID, SPTHGT, SPTSLIP, SPTSTRW, SPTSTRETCH, ");
			sqlString.append("SPTSHRW, SPTSHRINK, SPTCODE, SPTCDPRT, SPTCSPRT1, ");
			sqlString.append("SPTCSPRT2, SPTPRTG, SPTSTORE, SPTSPECI, SPTCOMENT, ");
			sqlString.append("'TreeNet - CPG Spec Add',"); // Last Update Program
			sqlString.append(dt.getDateFormatyyyyMMdd() + ",");
			sqlString.append(dt.getTimeFormathhmmss() + ",");
			sqlString.append("'" + fromVb.getUpdateUser() + "',");
			sqlString.append("'Browser' ");
			sqlString.append(" FROM " + ttlib + ".SPPTPACK ");
			sqlString.append(" WHERE SPTRESNO = '" + fromVb.getItemNumberCopy().trim() + "' ");
			sqlString.append("   AND SPTREVIS = " + fromVb.getRevisionDateCopy() + " ");
			sqlString.append(") ");	
		}
		if (inRequestType.equals("copyCPGSpecTest"))
		{
			UpdSpecification fromVb = (UpdSpecification) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlib + ".SPPRTEST ");
			sqlString.append("(SELECT ");
			sqlString.append("'" + fromVb.getItemNumber() + "', "); //Item Number - Sent in NEW Number
			sqlString.append(fromVb.getRevisionDate() + ", "); // Revision Date
			sqlString.append("SPRTEST, SPRTARGET, SPRMIN, SPRMAX, SPRMETH, SPRSEQ, ");
			sqlString.append("'TreeNet - CPG Spec Add', "); // Last Update Program
			sqlString.append(dt.getDateFormatyyyyMMdd() + ",");
			sqlString.append(dt.getTimeFormathhmmss() + ",");
			sqlString.append("'" + fromVb.getUpdateUser() + "',");
			sqlString.append("'Browser', ");
			sqlString.append("SPRTVAL, SPRTUOM ");
			sqlString.append(" FROM " + ttlib + ".SPPRTEST ");
			sqlString.append(" WHERE SPRRESNO = '" + fromVb.getItemNumberCopy().trim() + "' ");
			sqlString.append("   AND SPRREVIS = " + fromVb.getRevisionDateCopy() + " ");
			sqlString.append(") ");	
		}
		if (inRequestType.equals("copyCPGSpecProcess"))
		{
			UpdSpecification fromVb = (UpdSpecification) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlib + ".SPPSPROC ");
			sqlString.append("(SELECT ");
			sqlString.append("'" + fromVb.getItemNumber() + "', "); //Item Number - Sent in NEW Number
			sqlString.append(fromVb.getRevisionDate() + ", "); // Revision Date
			sqlString.append("SPSPROC, SPSTARGET, SPSMIN, SPSMAX, SPSMETH, SPSSEQ, ");
			sqlString.append("'TreeNet - CPG Spec Add', "); // Last Update Program
			sqlString.append(dt.getDateFormatyyyyMMdd() + ",");
			sqlString.append(dt.getTimeFormathhmmss() + ",");
			sqlString.append("'" + fromVb.getUpdateUser() + "',");
			sqlString.append("'Browser' ");
			sqlString.append(" FROM " + ttlib + ".SPPSPROC ");
			sqlString.append(" WHERE SPSRESNO = '" + fromVb.getItemNumberCopy().trim() + "' ");
			sqlString.append("   AND SPSREVIS = " + fromVb.getRevisionDateCopy() + " ");
			sqlString.append(") ");	
		}
 // **********************************************************************************
 //  DELETE
		if (inRequestType.equals("deleteCPGTests"))
		{ // Delete Records for CPG Analytical Tests -- 
		  //  ONLY delete record if the spec is in PENDING Status --
//			 get the Date and Time Information
//			DateTime origRevDT = UtilityDateTime.getDateFromMMddyyWithSlash(fromVb.getRevisionDateOriginal());
		    // build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".SPPRTEST ");
			sqlString.append(" WHERE EXISTS (");
			sqlString.append(" SELECT * FROM " + ttlib + ".SPPPHEAD ");
			sqlString.append(" WHERE SPPRECST = 'PENDING' ");
			sqlString.append("   AND SPPRESNO = SPRRESNO ");
			sqlString.append("   AND SPPREVIS = SPRREVIS ");
			sqlString.append(") ");
			sqlString.append("AND SPRRESNO = '" + (String) requestClass.elementAt(0) + "' "); //Item Number - CPGSpec Number
			sqlString.append("   AND SPRREVIS = " + (String) requestClass.elementAt(1) + " "); // Revision Date
		}	
		if (inRequestType.equals("deleteCPGProcess"))
		{ // Delete Records for CPG Process Parameters -- 
		  //  ONLY delete record if the spec is in PENDING Status --
//			 get the Date and Time Information
//			DateTime origRevDT = UtilityDateTime.getDateFromMMddyyWithSlash(fromVb.getRevisionDateOriginal());
		    // build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".SPPSPROC ");
			sqlString.append(" WHERE EXISTS (");
			sqlString.append(" SELECT * FROM " + ttlib + ".SPPPHEAD ");
			sqlString.append(" WHERE SPPRECST = 'PENDING' ");
			sqlString.append("   AND SPPRESNO = SPSRESNO ");
			sqlString.append("   AND SPPREVIS = SPSREVIS ");
			sqlString.append(") ");
			sqlString.append("AND SPSRESNO = '" + (String) requestClass.elementAt(0) + "' "); //Item Number - CPGSpec Number
			sqlString.append("   AND SPSREVIS = " + (String) requestClass.elementAt(1) + " "); // Revision Date
		}	
		if (inRequestType.equals("deleteCPGPack"))
		{ // Delete Records for CPG Packing Information -- 
		  //  ONLY delete record if the spec is in PENDING Status --
//			 get the Date and Time Information
//			DateTime origRevDT = UtilityDateTime.getDateFromMMddyyWithSlash(fromVb.getRevisionDateOriginal());
		    // build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".SPPTPACK ");
			sqlString.append(" WHERE EXISTS (");
			sqlString.append(" SELECT * FROM " + ttlib + ".SPPPHEAD ");
			sqlString.append(" WHERE SPPRECST = 'PENDING' ");
			sqlString.append("   AND SPPRESNO = SPTRESNO ");
			sqlString.append("   AND SPPREVIS = SPTREVIS ");
			sqlString.append(") ");
			sqlString.append("AND SPTRESNO = '" + (String) requestClass.elementAt(0) + "' "); //Item Number - CPGSpec Number
			sqlString.append("   AND SPTREVIS = " + (String) requestClass.elementAt(1) + " "); // Revision Date
		}	
		if (inRequestType.equals("deleteCPGHead"))
		{ // Delete Records for CPG Main Specification Header Information -- 
		  //  ONLY delete record if the spec is in PENDING Status --
//			 get the Date and Time Information
//			DateTime origRevDT = UtilityDateTime.getDateFromMMddyyWithSlash(fromVb.getRevisionDateOriginal());
		    // build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".SPPPHEAD ");
			sqlString.append(" WHERE SPPRECST = 'PENDING' ");
			sqlString.append("AND SPPRESNO = '" + (String) requestClass.elementAt(0) + "' "); //Item Number - CPGSpec Number
			sqlString.append("   AND SPPREVIS = " + (String) requestClass.elementAt(1) + " "); // Revision Date
		}	
		if (inRequestType.equals("deleteProcessParameters"))
		{
			// cast the incoming parameter class.
			UpdSpecification fromVb = (UpdSpecification) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".SPPSPROC ");
			sqlString.append(" WHERE SPSRESNO = '" + fromVb.getItemNumber() + "' "); //Item Number - CPGSpec Number
//			 get the Date and Time Information
			DateTime origRevDT = UtilityDateTime.getDateFromMMddyyWithSlash(fromVb.getRevisionDateOriginal());
			sqlString.append("   AND SPSREVIS = " + origRevDT.getDateFormatyyyyMMdd() + " "); // Revision Date
		}
		if (inRequestType.equals("deleteAnalyticalTests"))
		{
			// cast the incoming parameter class.
			UpdSpecification fromVb = (UpdSpecification) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".SPPRTEST ");
			sqlString.append(" WHERE SPRRESNO = '" + fromVb.getItemNumber() + "' "); //Item Number - CPGSpec Number
//			 get the Date and Time Information
			DateTime origRevDT = UtilityDateTime.getDateFromMMddyyWithSlash(fromVb.getRevisionDateOriginal());
			sqlString.append("   AND SPRREVIS = " + origRevDT.getDateFormatyyyyMMdd() + " "); // Revision Date
		}	
	} catch (Exception e) {
			throwError.append(" Error building sql statement for request type " + inRequestType + ". " + e);
	}
	// return data.	
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceSpecification.");
		throwError.append("buildSqlStatement(String,String,Vector)");
		throw new Exception(throwError.toString());
	}
			
	return sqlString.toString();
 }

	/**
	 *   Method Created 10/23/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in the InqSpecification Object for use in the SQL statement
	 * @return BeanSpecification
	 * @throws Exception
	 */
	public static BeanSpecification listCPGSpecs(Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanSpecification returnValue = new BeanSpecification();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = getDBConnection();
			returnValue = listCPGSpecs(inValues, conn);
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
			throwError.append("ServiceSpecification.");
			throwError.append("listCPGSpecs(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *    Method Created 10/15/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in the InqSpecification Object for use in the SQL statement
	 * @return BeanSpecification
	 * @throws Exception
	 */
	private static BeanSpecification listCPGSpecs(Vector inValues, 
											  	  Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanSpecification returnValue = new BeanSpecification();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try
		{
			try {
				   // Get the list of DataSet File Names - Along with Descriptions
			   listThem = conn.prepareStatement(buildSqlStatement("listCPGSpecs", inValues));
			   rs = listThem.executeQuery();
			 } catch(Exception e)
			 {
			   	throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			 }		
			Vector listOfRecords = new Vector();
			 try
			 {
			 	while (rs.next())
			    {
					SpecificationNEW sn = loadFieldsSpecification("listCPGSpecs", rs);
					listOfRecords.addElement(sn);
	     		}// END of the IF Statement
			 } catch(Exception e)
			 {
				throwError.append(" Error occured while Building Vector from sql statement. " + e);
			 } 		
			 returnValue.setSpecNEW(listOfRecords);
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
			throwError.append("ServiceSpecification.");
			throwError.append("listCPGSpecs(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 * Load class fields from result set.
	 */
		
	public static SpecificationNEW loadFieldsSpecification(String requestType,
														   ResultSet rs)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		SpecificationNEW returnValue = new SpecificationNEW();
	
		try{ 
			if (requestType.equals("listCPGSpecs") ||
				requestType.equals("dtlCPGSpecs"))
			{
				try
				{
					// CPG Specification File - SPPPHEAD
 					 returnValue.setFormulaNumber(rs.getString("SPPFORM").trim());
					 returnValue.setRevisionDate(rs.getString("SPPREVIS").trim());
					 returnValue.setSpecStatus(rs.getString("SPPRECST").trim());
					 if (requestType.equals("dtlCPGSpecs"))
					 {
					 	returnValue.setHeadComment(rs.getString("SPPCOMENT").trim());
					 	returnValue.setLabBookID(rs.getString("SPPREF").trim());
					 	// CPG Packing File for Specification -- SPPTPACK
					 	returnValue.setLength(rs.getString("SPTLEN"));
					 	returnValue.setWidth(rs.getString("SPTWID"));
					 	returnValue.setHeight(rs.getString("SPTHGT"));
					 	returnValue.setSlipSheetInfo(rs.getString("SPTSLIP").trim());
					 	returnValue.setStretchWrap(rs.getString("SPTSTRW"));
					 	returnValue.setStretchWrapInfo(rs.getString("SPTSTRETCH").trim());
					 	returnValue.setShrinkWrap(rs.getString("SPTSHRW"));
					 	returnValue.setShrinkWrapInfo(rs.getString("SPTSHRINK").trim());
					 	returnValue.setCodingInfo(rs.getString("SPTCODE").trim());
					 	returnValue.setCaseCodePrint(rs.getString("SPTCDPRT").trim());
					 	returnValue.setCasePrintLine1(rs.getString("SPTCSPRT1").trim());
					 	returnValue.setCasePrintLine2(rs.getString("SPTCSPRT2").trim());
					 	returnValue.setCasePrintGeneral(rs.getString("SPTPRTG").trim());
					 	returnValue.setStorageConditions(rs.getString("SPTSTORE").trim());
					 	returnValue.setSpecialRequirements(rs.getString("SPTSPECI").trim());
					 	returnValue.setPackComment(rs.getString("SPTCOMENT").trim());
					 }
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}
				try
				{
					// Load Information INTO the Item Warehouse Records
					returnValue.setItemWhse(ServiceItem.loadFieldsItemWarehouse(requestType, rs));
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
					}	
				try
				{
					if (returnValue.getItemWhse() == null ||
						returnValue.getItemWhse().getItemNumber() == null)
					{
						ItemWarehouse	itemWhse				= new ItemWarehouse();
						itemWhse.setItemNumber(rs.getString("SPPRESNO").trim());
						returnValue.setItemWhse(itemWhse);
	
					}
						
				}
				catch(Exception e)
				{
					
				}
				}
				} catch(Exception e)
			{
				throwError.append(" Problem loading the Item class ");
				throwError.append("from the result set. " + e) ;
			}
			// return data.
			if (!throwError.toString().equals("")) {
				throwError.append("Error @ com.treetop.services.");
				throwError.append("ServiceSpecification.");
				throwError.append("loadFieldsSpecification(requestType, rs: ");
				throwError.append(requestType + "). ");
				throw new Exception(throwError.toString());
			}
			return returnValue;
		}

	/**
	 * Use to Validate a Sent in CPG Specification Number Item, Return Message if Item is not found
	 *    // Check in file SPPPHEAD
	 */
	
	public static String verifyCPGSpec(String specNumber)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyCPGSpec";
			String sqlString = "";
				
			// verify base class initialization.
			// verify incoming Specification
			if (specNumber == null || specNumber.trim().equals(""))
				rtnProblem.append(" CPG Specification Number must not be null or empty.");
			
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(specNumber);
					sqlString = buildSqlStatement(requestType, parmClass);
				} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
				rtnProblem.append(" Problem when building Test for CPG Spec: ");
				rtnProblem.append(specNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					conn = getDBConnection();
						
					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();
						
					if (rs.next())
					{
	//					 it exists and all is good.
					} else {
						rtnProblem.append(" CPG Specification Number '" + specNumber + "' ");
						rtnProblem.append("does not exist. ");
					}
						
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding CPG Specification Number: ");
					rtnProblem.append(specNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem verifying the Specification Number; " + specNumber);
		// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						conn.close();
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
			throwError.append("ServiceSpecification.");
			throwError.append("verifyCPGSpec(String: specNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

	/**
	 *   Method Created 10/23/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in the DtlSpecification Object for use in the SQL statement
	 * @return BeanSpecification
	 * @throws Exception
	 */
	public static BeanSpecification findCPGSpec(Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanSpecification returnValue = new BeanSpecification();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = getDBConnection();
			returnValue = findCPGSpec(inValues, conn);
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
			throwError.append("ServiceSpecification.");
			throwError.append("findCPGSpec(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *    Method Created 10/23/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in the DtlSpecification Object for use in the SQL statement
	 * @return BeanSpecification
	 * @throws Exception
	 */
	private static BeanSpecification findCPGSpec(Vector inValues, 
											  	  Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanSpecification returnValue = new BeanSpecification();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try
		{
			try {
				   // Get the list of DataSet File Names - Along with Descriptions
			   listThem = conn.prepareStatement(buildSqlStatement("dtlCPGSpecs", inValues));
			   rs = listThem.executeQuery();
			 } catch(Exception e)
			 {
			   	throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			 }		
			 try
			 {
			 	int x = 0;
			 	SpecificationNEW sn = new SpecificationNEW();
			 	String saveRevision = "";
			 	while (rs.next() && x < 2)
			 	{
			 		if (x == 0)
			 		{
			 			returnValue.setItemClass(ServiceItem.loadFieldsItem("loadItem", rs));	
			 			sn = loadFieldsSpecification("dtlCPGSpecs", rs);
			 			try
						{
			 				DtlFormula df = new DtlFormula();
			 				df.setRecordStatus("ACTIVE");
			 				df.setFormulaNumber(sn.getFormulaNumber());
			 				Vector sendParms = new Vector();
			 				sendParms.add(df);
			 				returnValue.setFormulaClass(ServiceSpecificationFormula.findFormula(sendParms));
			 				sendParms = new Vector();
			 				sendParms.add("TEST");
			 				sendParms.add(sn.getItemWhse().getItemNumber());
			 				sendParms.add(sn.getRevisionDate());
			 				returnValue.setListTests(listCPGSpecsTestProcess(sendParms));
			 				sendParms = new Vector();
			 				sendParms.add("PROCESS");
			 				sendParms.add(sn.getItemWhse().getItemNumber());
			 				sendParms.add(sn.getRevisionDate());
			 				returnValue.setListProcesses(listCPGSpecsTestProcess(sendParms));
						}
			 			catch(Exception e)
						{}
			 			x++;
			 			saveRevision = rs.getString("SPPREVIS");
			 		}
			 		else
			 		{
			 			if (!saveRevision.equals(rs.getString("SPPREVIS")))
			 			{
			 				sn.setSupercedesDate(rs.getString("SPPREVIS"));
			 				x++;
			 			}
			 		}
	     		}// END of the IF Statement
			 	returnValue.setSpecClass(sn);
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
			throwError.append("ServiceSpecification.");
			throwError.append("listCPGSpecs(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   Method Created 10/28/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in:
	 * 				Strings:  type:  TEST or PROCESS
	 * 						  Spec:  Item Number for the Specification
	 *                        Revision: Revision Date of the Specification
	 * @return Vector:  of SpecificationTestProcess Objects
	 * @throws Exception
	 */
	public static Vector listCPGSpecsTestProcess(Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector returnValue = new Vector();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = getDBConnection();
			returnValue = listCPGSpecsTestProcess(inValues, conn);
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
			throwError.append("ServiceSpecification.");
			throwError.append("listCPGSpecsTestProcess(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *     Method Created 10/28/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in:
	 * 				Strings:  type:  TEST or PROCESS
	 * 						  Spec:  Item Number for the Specification
	 *                        Revision: Revision Date of the Specification
	 * @return Vector:  of SpecificationTestProcess Objects
	 */
	private static Vector listCPGSpecsTestProcess(Vector inValues, 
												  Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector returnValue = new Vector();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try
		{
			String requestType = "list" + ((String) inValues.elementAt(0)).trim();
			try {
				   // Get the list of DataSet File Names - Along with Descriptions
			   listThem = conn.prepareStatement(buildSqlStatement(requestType, inValues));
			   rs = listThem.executeQuery();
			 } catch(Exception e)
			 {
			   	throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			 }		
			 try
			 {
			 	while (rs.next())
			    {
					SpecificationTestProcess stp = loadFieldsSpecificationTestProcess(requestType, rs);
					returnValue.addElement(stp);
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
			throwError.append("ServiceSpecification.");
			throwError.append("listCPGSpecsTestProcess(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 * Load class fields from result set.
	 */
		
	public static SpecificationTestProcess loadFieldsSpecificationTestProcess(String requestType,
																			  ResultSet rs)
	throws Exception
	{
//		StringBuffer throwError = new StringBuffer();
		SpecificationTestProcess returnValue = new SpecificationTestProcess();
	
		try{ 
			if (requestType.equals("listTEST"))
			{	
				//System.out.println("SPRTEST:" + rs.getString("SPRTEST"));
				returnValue.setRevisionDate(rs.getString("SPRREVIS"));
				returnValue.setTestProcess(rs.getString("SPRTEST"));
				returnValue.setTarget(rs.getString("SPRTARGET"));
				returnValue.setMinValue(rs.getString("SPRMIN"));
				returnValue.setMaxValue(rs.getString("SPRMAX"));
				returnValue.setMethod(rs.getString("SPRMETH"));
				returnValue.setPrintSeqNumber(rs.getString("SPRSEQ"));
				returnValue.setTestValue(rs.getString("SPRTVAL"));
				returnValue.setTestUOM(rs.getString("SPRTUOM"));
				try
				{
				   returnValue.setMethodName(rs.getString("SPNTITLE").trim());
				}
				catch(Exception e)
				{
					// Caught for Null Pointer on Outer Join
				}
			}
			if (requestType.equals("listPROCESS"))
			{	
				returnValue.setRevisionDate(rs.getString("SPSREVIS"));
				returnValue.setTestProcess(rs.getString("SPSPROC"));
				returnValue.setTarget(rs.getString("SPSTARGET"));
				returnValue.setMinValue(rs.getString("SPSMIN"));
				returnValue.setMaxValue(rs.getString("SPSMAX"));
				returnValue.setMethod(rs.getString("SPSMETH"));
				returnValue.setPrintSeqNumber(rs.getString("SPSSEQ"));
				try
				{
				   returnValue.setMethodName(rs.getString("SPNTITLE").trim());
				}
				catch(Exception e)
				{
					// Caught for Null Pointer on an Outer Join
				}
			}
			try
			{
				String description = rs.getString("IDSDSC");
				if (!rs.getString("IDSUDS").trim().equals(""))
					description = description + " - " + rs.getString("IDSUDS").trim();
				returnValue.setTestProcessDescription(description);
				returnValue.setTestProcessUOM(rs.getString("IDSUNT"));
			}
			catch(Exception e)
			{}
		} catch(Exception e)
		{
			System.out.println("showerror = " + e);
//			throwError.append(" Problem loading the SpecificationTestProcess class ");
//			throwError.append("from the result set. " + e) ;
		}

		return returnValue;
	}

	/**
	 *   Method Created 1/8/09 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in:
	 * 				Strings:  type:  TEST OR PROCESS
	 * @return Vector:  of SpecificationTestProcess Objects
	 * @throws Exception
	 */
	public static Vector dropDownCPGSpecsTestProcess(Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector returnValue = new Vector();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = getDBConnection();
			returnValue = dropDownCPGSpecsTestProcess(inValues, conn);
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
			throwError.append("ServiceSpecification.");
			throwError.append("dropDownCPGSpecsTestProcess(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *     Method Created 10/28/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in:
	 * 				Strings:  type:  TEST OR PROCESS
	 * @return Vector:  of DropDownSingle Objects
	 */
	private static Vector dropDownCPGSpecsTestProcess(Vector inValues, 
												      Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector returnValue = new Vector();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try
		{
			String requestType = "dd" + ((String) inValues.elementAt(0)).trim();
			try {
				   // Get the list of DataSet File Names - Along with Descriptions
			   listThem = conn.prepareStatement(buildSqlStatement(requestType, inValues));
			   rs = listThem.executeQuery();
			 } catch(Exception e)
			 {
			   	throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			 }		
			 try
			 {
			 	while (rs.next())
			    {
					returnValue.addElement(loadFieldsDropDownSingle(requestType, rs));
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
			throwError.append("ServiceSpecification.");
			throwError.append("dropDownCPGSpecsTestProcess(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
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
		  if (requestType.equals("ddTEST") || requestType.equals("ddPROCESS"))
		  {
			returnValue.setValue(rs.getString("IDSCDE").trim());
			String description = rs.getString("IDSDSC").trim();
			if (!rs.getString("IDSUDS").trim().equals(""))
				description = description + " - " + rs.getString("IDSUDS").trim();
			returnValue.setDescription(description);
		  }
		  
		} catch(Exception e)
		{
			throwError.append(" Error loading dropdown fields ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceSpecification.");
			throwError.append("loadFieldsDropDownSingle(requestType(");
			throwError.append(requestType + "), rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

	/**
	 * Determine what needs to be done to Update a record for a CPG Specification
	 *    // Process Information to ADD/UPDATE/DELETE the file: SPPPHEAD
	 *                      also to ADD/UPDATE/DELETE the file: SPPRTEST
	 * 						also to ADD/UPDATE/DELETE the file: SPPSPROC
	 * 						also to ADD/UPDATE/DELETE the file: SPPTPACK
	 */
		public static void processCPGSpec(Vector incomingParms) // Send in UpdSpecification
		throws Exception
		{
			StringBuffer throwError = new StringBuffer();
			Connection conn = null; // New Lawson Box - Lawson Database
			try { 
				conn = getDBConnection();
				// Determine which methods need to be processed
				UpdSpecification us = (UpdSpecification) incomingParms.elementAt(0);
				String loadValue = us.getItemNumber().trim();
				if (loadValue == null)
					loadValue = "";
				// MUST have an ITEM Number
				if (!loadValue.trim().equals(""))
				{
				   DateTime dtCurrent = UtilityDateTime.getSystemDate();
				   incomingParms.addElement(dtCurrent);
				   if ((us.getRequestType().equals("addCPGSpec") ||
						us.getRequestType().equals("copyCPGSpec")) &&
					   !us.getSaveButton().equals(""))
				   { 
				   	  try
					  {
				   		  // addCPGSpec -- will ADD a basic record into the 
				   		  //   SPPPHEAD and the SPPTPACK files
				   		  // copyCPGSpec -- will ADD a records into 
				   		  //   SPPPHEAD, SPPTPACK, SPPRTEST and SPPSPROC files
				   		  //   based on the copied spec.
				   		  addCPGSpec(incomingParms, conn);
					  }
				   	  catch(Exception e)
					  {
				   	  	// Catch a Problem
				   	  	throwError.append("Problem occurred when trying to Add records for the Specification : " + loadValue + "::" + e);
					  }
				   }
				   else
				   { // Update the Specification INFORMATION
				   	   
				   	  try
					  {
				   		if (us.getRequestType().equals("deleteCPGSpec"))
				   		{
				   			deleteCPGSpec(incomingParms, conn);
				   		}
				   		else
				   		{
				   	  	  if (!us.getSaveButton().equals(""))
				   	  	  {
				   	  		// Process the information for the:
				   	  			// Spec Header 
				   	  			// Spec Packing Information
				   	  		 updateCPGSpec(incomingParms, conn);
				   	  			// Spec Process Parameters
				   	  		 updateCPGSpecProcessParameters(incomingParms, conn);
				   	  			// Spec Analytical Tests
				   	  		 updateCPGSpecAnalyticalTests(incomingParms, conn);
				   	  		
	//			   	  	---------------------------------------------------------------
						   	  // Calculate and Update Fields
//				   	  	    calculateAndUpdate("load", incomingParms, conn);
				   	  	   }
				   		}
					  }
				   	  catch(Exception e)
					  {
				   	  	// Catch a Problem
				   	  	throwError.append("Problem occurred when trying to Update a record for CPG Spec Number: " + loadValue + "::" + e);
					  }	
				   }
				}
			} catch(Exception e)
			{
				throwError.append(" Problem Updating the Specification. " + e) ;
			// return connection.
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
			// return data.
			if (!throwError.toString().equals("")) {
				throwError.append("Error @ com.treetop.services.");
				throwError.append("ServiceSpecification.");
				throwError.append("processLoad(Vector)");
				throw new Exception(throwError.toString());
			}
			return;
		}

	/**
	 * Use to Update the CPG Spec Process Parameter File:
	 *    SPPSPROC -- Process Parameters for Specifications
	 * 
	 *   // Update Process includes:
	 *      	Deleting All Records 
	 *   		Inserting the Valid NEW Records
	 */
	private static void updateCPGSpecProcessParameters(Vector incomingParms,  // UpdSpecification
								    				   Connection conn)
		throws Exception
	{
//		change code for spec
		StringBuffer throwError = new StringBuffer();
//		System.out.println("Update Process Parameters");
	
		// Delete all the Records with the Scale Number
		PreparedStatement deleteIt = null;
		try { 
		    try {
				deleteIt = conn.prepareStatement(buildSqlStatement("deleteProcessParameters", incomingParms));
				deleteIt.executeUpdate();
			} catch (Exception e) {
				throwError.append(" error occured executing an sql statement to delete the Process Parameters Records. " + e);
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Deleting the Process Parameters Records. " + e) ;
			// return Prepared Statement
		} finally {
			try {
				if (deleteIt != null)
					deleteIt.close();
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// Go through Vector and ADD records
		PreparedStatement addIt = null;
		try { 
			Vector listProcessParms = ((UpdSpecification) incomingParms.elementAt(0)).getListProcessParameters();
			
			if (listProcessParms.size() > 0)
			{
				for (int x = 0; x < listProcessParms.size(); x++)
				{
				    try {
				    	Vector sendParms = new Vector();
				    	sendParms.addElement(listProcessParms.elementAt(x));
				    	sendParms.addElement(incomingParms.elementAt(1));
						addIt = conn.prepareStatement(buildSqlStatement("insertProcessParameter", sendParms));
						addIt.executeUpdate();
					} catch (Exception e) {
						throwError.append(" error occured executing an sql statement to add a Process Parameter. " + e);
					}
				}
			}
		} catch(Exception e)
		{
//			throwError.append(" Problem Deleting the Bin Record. " + e) ;
			// return Prepared Statement
		} finally {
			try {
				if (addIt != null)
					addIt.close();
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceSpecification.");
			throwError.append("updateCPGSpecProcessParameter(");
			throwError.append("Vector, Connection)");
			throw new Exception(throwError.toString());
		}
		return;
	}

	/**
	 * Use to Update the Load File:
	 *    GRPCSCALE -- Load Header File
	 */
	private static void updateCPGSpec(Vector incomingParms,  // UpdSpecification
								      Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		PreparedStatement updIt = null;
		try { 
//			System.out.println("UpdateSpec");
			try
			{
				// Test to see if going FROM Pending to Active Status
				//  Can only have 1 record in Active Status
				UpdSpecification us = (UpdSpecification) incomingParms.elementAt(0);
				if (us.getRecordStatusOriginal().equals("PENDING") &&
					us.getRecordStatus().equals("ACTIVE"))
				{
					// Then must Take the ACTIVE Record (if applicable) 
					//     and set it to INACTIVE
					updIt = conn.prepareStatement(buildSqlStatement("updateCPGStatus", incomingParms));
					updIt.executeUpdate();
				}
			} catch (Exception e) {
				throwError.append(" error occured processing update Specification (Header) Record. " + e);
			}
		    try {
				updIt = conn.prepareStatement(buildSqlStatement("updateCPGSpecHead", incomingParms));
				updIt.executeUpdate();
			} catch (Exception e) {
				throwError.append(" error occured executing an sql statement to update a Specification (Header) Record. " + e);
			}
			// Update the Pack Section of the Specification
			try {
				updIt = conn.prepareStatement(buildSqlStatement("updateCPGSpecPack", incomingParms));
				updIt.executeUpdate();
			} catch (Exception e) {
				throwError.append(" error occured executing an sql statement to update a Pack Record. " + e);
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the Load Record. " + e) ;
			// return Prepared Statement
		} finally {
			try {
				if (updIt != null)
					updIt.close();
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceSpecification.");
			throwError.append("updateCPGSpec(");
			throwError.append("Vector, Connection)");
			throw new Exception(throwError.toString());
		}
		return;
	}

	/**
	 * Use to Add a records into the Specification Files
	 *     SPPPHEAD - CPG Spec Header
	 *     SPPTPACK - CPG Spec Packing Information
	 *     SPPRTEST - Test Values
	 *     SPPSPROC - Process Parameters
	 */
		
	private static void addCPGSpec(Vector incomingParms,  // UpdSpecification
								   Connection conn)
		throws Exception
	{
		
		UpdSpecification us = (UpdSpecification) incomingParms.elementAt(0);
		StringBuffer throwError = new StringBuffer();
		PreparedStatement addIt = null;
		try
		{
			if (us.getItemNumberCopy().trim().equals(""))
			{
				//addCPGSpec -- will ADD a basic record into the 
				//   SPPPHEAD and the SPPTPACK files
				try { // SPPPHEAD FILE
					addIt = conn.prepareStatement(buildSqlStatement("insertCPGSpecHead", incomingParms));
					addIt.executeUpdate();
				} catch (Exception e) {
					throwError.append(" error occured building or executing a sql statement to add to Header File. " + e);
				}
				try { // SPPTPACK FILE
					addIt = conn.prepareStatement(buildSqlStatement("insertCPGSpecPack", incomingParms));
					addIt.executeUpdate();
				} catch (Exception e) {
					throwError.append(" error occured building or executing a sql statement to add to Packaging Information File. " + e);
				}
			}
			else
			{
				//	 copyCPGSpec -- will ADD a records into 
		   		//   SPPPHEAD, SPPTPACK, SPPRTEST and SPPSPROC files
		   		//   based on the copied spec. 	
				try { // SPPPHEAD FILE
					addIt = conn.prepareStatement(buildSqlStatement("copyCPGSpecHead", incomingParms));
					addIt.executeUpdate();
				} catch (Exception e) {
					throwError.append(" error occured building or executing a sql statement to add to Header File. " + e);
				}
				try { // SPPTPACK FILE
					addIt = conn.prepareStatement(buildSqlStatement("copyCPGSpecPack", incomingParms));
					addIt.executeUpdate();
				} catch (Exception e) {
					throwError.append(" error occured building or executing a sql statement to add to Packaging Information File. " + e);
				}
				try { // SPPRTEST FILE
					addIt = conn.prepareStatement(buildSqlStatement("copyCPGSpecTest", incomingParms));
					addIt.executeUpdate();
				} catch (Exception e) {
					throwError.append(" error occured building or executing a sql statement to add to Analytical Tests Information File. " + e);
				}
				try { // SPPSPROC FILE
					addIt = conn.prepareStatement(buildSqlStatement("copyCPGSpecProcess", incomingParms));
					addIt.executeUpdate();
				} catch (Exception e) {
					throwError.append(" error occured building or executing a sql statement to add to Process Parameters Information File. " + e);
				}
			}
		}
		catch(Exception e){
			throwError.append(" error occured building or executing a sql statement to add Specification. " + e);
		}finally {
			try {
				if (addIt != null)
					addIt.close();
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceSpecification.");
			throwError.append("addCPGSpec(");
			throwError.append("Vector, Connection)");
			throw new Exception(throwError.toString());
		}
		return;
	}

	/**
	 * Method Created 1/5/09 TWalton
	 *   // Use to delete all records associated to a specific
	 *   //   Item - Revision Date Combination 
	 * @param -- Send in a Vector of UpdSpecification Objects
	 * 
	 * @return Nothing
	 */
	private static void deleteCPGSpec(Vector inValues, 
								      Connection conn)
	{
		PreparedStatement delete = null;
		try
		{
			// Must have Item and Revision Date for Deletion
			UpdSpecification us = (UpdSpecification) inValues.elementAt(0);
			Vector sendParms = new Vector();
			sendParms.add(us.getItemNumber());
			sendParms.add(us.getRevisionDate());
			//  Will ONLY delete PENDING STATUS
			// Delete ALL the Files FIRST and then the HEADER
			try { // DELETE THE ANALYTICAL TESTS
				delete = conn.prepareStatement(buildSqlStatement("deleteCPGTests", sendParms));
				delete.executeQuery();
			 } catch(Exception e)
			 {
			 	System.out.println("Error occured Deleting CPG Test Records. " + e);
			 }
			 try { // DELETE THE PROCESS PARAMETERS
				 delete = conn.prepareStatement(buildSqlStatement("deleteCPGProcess", sendParms));
				 delete.executeQuery();
			 } catch(Exception e)
			 {
			 	System.out.println("Error occured Deleting CPG Process Parameters Records. " + e);
			 }
			 try { // DELETE THE CPG Spec Packing Information
				 delete = conn.prepareStatement(buildSqlStatement("deleteCPGPack", sendParms));
				 delete.executeQuery();
			 } catch(Exception e)
			 {
			 	System.out.println("Error occured Deleting CPG Packing Information Records. " + e);
			 }
			 try { // DELETE THE MAIN RECORD - HEADER
				 delete = conn.prepareStatement(buildSqlStatement("deleteCPGHead", sendParms));
				 delete.executeQuery();
			 } catch(Exception e)
			 {
			 	System.out.println("Error occured Deleting CPG Test Records. " + e);
			 }
		} catch (Exception e)
		{
			//throwError.append(e);
		}
		finally
		{
			if (delete != null)
			{
				try
				{
				  delete.close();	
				}
				catch(Exception el)
				{
					el.printStackTrace();
				}
			}
		}
		return;
	}

	/**
	 * Use to Update the CPG Analytical Test File:
	 *    GRPCBINS -- Bin File for the Load
	 * 
	 *   // Update Process includes:
	 *      	Deleting All Records with the Scale Ticket on them
	 *   		Inserting the Valid NEW Records
	 */
	private static void updateCPGSpecAnalyticalTests(Vector incomingParms,  // UpdSpecification
								    				Connection conn)
		throws Exception
	{
//		change code for spec
		StringBuffer throwError = new StringBuffer();
//		System.out.println("Update Analytical Tests");
	
		// Delete all the Records with the Scale Number
		PreparedStatement deleteIt = null;
		try { 
		    try {
				deleteIt = conn.prepareStatement(buildSqlStatement("deleteAnalyticalTests", incomingParms));
				deleteIt.executeUpdate();
			} catch (Exception e) {
				throwError.append(" error occured executing an sql statement to delete the Analytical Tests Records. " + e);
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Deleting the Analytical Tests Records. " + e) ;
			// return Prepared Statement
		} finally {
			try {
				if (deleteIt != null)
					deleteIt.close();
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// Go through Vector and ADD records
		PreparedStatement addIt = null;
		try { 
			Vector listProcessParms = ((UpdSpecification) incomingParms.elementAt(0)).getListAnalyticalTests();
			
			if (listProcessParms.size() > 0)
			{
				for (int x = 0; x < listProcessParms.size(); x++)
				{
				    try {
				    	Vector sendParms = new Vector();
				    	sendParms.addElement(listProcessParms.elementAt(x));
				    	sendParms.addElement(incomingParms.elementAt(1));
						addIt = conn.prepareStatement(buildSqlStatement("insertAnalyticalTest", sendParms));
						addIt.executeUpdate();
					} catch (Exception e) {
						throwError.append(" error occured executing an sql statement to add a Analytical Test. " + e);
					}
				}
			}
		} catch(Exception e)
		{
//			throwError.append(" Problem Deleting the Bin Record. " + e) ;
			// return Prepared Statement
		} finally {
			try {
				if (addIt != null)
					addIt.close();
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceSpecification.");
			throwError.append("updateCPGSpecAnalyticalTests(");
			throwError.append("Vector, Connection)");
			throw new Exception(throwError.toString());
		}
		return;
	}

	/**
	 * Use to Validate a Sent in CPG Specification Number Item, Verify if here is a record with the specific sent in Status
	 *    // Check in file SPPPHEAD
	 */
	
	public static String verifyCPGSpec(String specNumber, String status)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyCPGSpecStatus";
			String sqlString = "";
				
			// verify base class initialization.
	//		ServiceSpecification a = new ServiceSpecification();
	//	    System.out.println("ServiceSpecification.verifyCPGSpecs -- (item, status)");					
			// verify incoming Specification
			if (specNumber == null || specNumber.trim().equals(""))
				rtnProblem.append(" CPG Specification Number must not be null or empty.");
			
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(specNumber);
					parmClass.addElement(status);
					sqlString = buildSqlStatement(requestType, parmClass);
				} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
				rtnProblem.append(" Problem when building Test for CPG Spec: ");
				rtnProblem.append(specNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					conn = getDBConnection();
						
					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();
						
					if (rs.next())
					{
	//					 it exists and all is good.
					} else {
						rtnProblem.append(" CPG Specification Number '" + specNumber + "' ");
						rtnProblem.append("  with Status '" + status + "' ");
						rtnProblem.append("does not exist. ");
					}
						
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding CPG Specification Number: ");
					rtnProblem.append(specNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem verifying the Specification Number; " + specNumber);
		// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						conn.close();
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
			throwError.append("ServiceSpecification.");
			throwError.append("verifyCPGSpec(String: specNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}	
}
