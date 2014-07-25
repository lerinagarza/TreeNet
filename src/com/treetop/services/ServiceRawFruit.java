/*
 * Created on November 10, 2008
 *
 */
package com.treetop.services;

import java.sql.*;
import java.util.*;
import java.math.*;

import com.lawson.api.*;
import com.treetop.app.rawfruit.*;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.controller.contractmanufacturing.InqBilling;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.ConnectionStack3;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HtmlOption;
import com.treetop.viewbeans.CommonRequestBean;
/**
 * @author twalto .
 *
 * Services class to obtain and return data 
 * to business objects.
 * 
 * Certificate of Analysis.
 */
public class ServiceRawFruit extends BaseService {

	/**
	 * @author twalto 8/2012 ** NEW STYLE **
	 * Build all SQL statements for Raw Fruit (going forward)
	 */
	private static class BuildSQL {
	
		/**
		 * @author twalto  8/20/12
		 * Build an SQL statement to retrieve List of Locations
		 * Send in CommonRequestBean -
		 *      Environment 
		 *      Level 1 - Key0 for the code
		 *      Level 2 - if filled in will narrow the list to a Zone
		 *      Level 3 - Order By ??? example, Long Description
		 */
		private static String sqlDropDownDescriptiveCode(CommonRequestBean crb)
		throws Exception {
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
				
				// Determine Library
				String library = GeneralUtility.getTTLibrary(crb.getEnvironment()) + ".";
					
				sqlString.append("SELECT ");
				// NEW Descriptive Code File
				sqlString.append("DCPK01, DCPT80 ");
				
				sqlString.append("FROM " + library + "DCPALL ");
				
				sqlString.append("WHERE DCPK00 = '" + crb.getIdLevel1() + "' ");
				
				if (crb.getIdLevel3().trim().equals(""))
				   sqlString.append("ORDER BY DCPK01 ");
				else
				{
				   if (crb.getIdLevel3().trim().equals("Long Description"))
				      sqlString.append("ORDER BY DCPT80 ");
				}
		
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building sqlDropDownDescriptiveCode statement. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceRawFruit.");
				throwError.append("BuildSQL.sqlDropDownDescriptiveCode(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * @author twalto  9/12/13
		 *   -- moved from the buildSqlStatement section of this Service
		 * Build an SQL statement to copy a PO from an different one
		 * Send in CommonRequestBean -
		 *      Environment 
		 *      Level 1 - Scale Ticket Number
		 *      Level 2 - Scale Ticket Correction Sequence Number
		 *      Level 3 - NEW: Scale Ticket Correction Sequence Number
		 *      User - Last Change User
		 *      Date - Last Change Date
		 *      Time - Last Change Time
		 *      RebuildOption - to copy Positive information or Negative  
		 */
		private static String copyPO(CommonRequestBean crb)
		throws Exception {
			
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {

				sqlString.append("INSERT INTO " + ttlib + ".GRPCPO ");
				sqlString.append("(SELECT G3SCALE#, " + crb.getIdLevel3() + ", G3SEQ#, G3PO#, G3PLIN#, ");
				sqlString.append(" G3CPO, ");
				if (crb.getRebuildOption().trim().equals("copyNegative"))
				{
					sqlString.append("(G3AWGT * -1), (G3RWGT * -1), (G3TWGT * -1), ");     
					sqlString.append("(G3ABINS * -1), (G3RBINS * -1), (G3TBINS * -1), ");         
				}
				else
				{
					sqlString.append("G3AWGT, G3RWGT, G3TWGT, G3ABINS, G3RBINS, G3TBINS, ");     
				}
				sqlString.append("G3COMP, G3FACI, G3WHSE, G3SUPP, ");
				sqlString.append("'" + crb.getUser() + "', "); 
				sqlString.append(crb.getDate() + ", " + crb.getTime() + ",");
				sqlString.append("'0000000000' ");
				sqlString.append("FROM " + ttlib + ".GRPCPO ");
				sqlString.append(" WHERE G3SCALE# = '" + crb.getIdLevel1() + "' ");
				sqlString.append("   AND G3CSEQ1 = " + crb.getIdLevel2() + " ) ");
		
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building SQL statement for copyPO. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceRawFruit.");
				throwError.append("BuildSQL.copyPO(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * @author twalto  9/12/13
		 * Build an SQL statement to Update the PO information
		 * Send in CommonRequestBean -
		 *      Environment
		 *      DateTime = Enter a Date Time Object
		 *  and UpdRawFruitPO object 
		 */
		private static String updatePO(CommonRequestBean crb, UpdRawFruitPO urfp)
		throws Exception {
			
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
				// build the sql statement.
					sqlString.append("UPDATE " + ttlib + ".GRPCPO ");
					sqlString.append(" SET G3PO# = " + urfp.getPoNumber() + ",");
					String facility = "";
					if (!urfp.getPoWarehouse().trim().equals(""))
					{
					   try
					   {
						  Vector sendValue = new Vector();
						  sendValue.addElement(urfp.getPoWarehouse());
						  Warehouse whse = ServiceWarehouse.findWarehouse("PRD", sendValue);
				          if (!whse.getFacility().trim().equals(""))
						    facility = whse.getFacility();
					   }
					   catch(Exception e)
					   {}
					}
					sqlString.append("   G3FACI = '" + facility + "',");
				    sqlString.append("   G3WHSE = '" + urfp.getPoWarehouse() + "',");
					sqlString.append("   G3SUPP = '" + urfp.getSupplier() + "',");
					sqlString.append(" G3USER = '" + urfp.getUpdateUser() + "',");
					sqlString.append(" G3DATE = " + crb.getDateTime().getDateFormatyyyyMMdd() + ",");
					sqlString.append(" G3TIME = " + crb.getDateTime().getTimeFormathhmmss() + " ");		
					sqlString.append(" WHERE G3SCALE# = '" + urfp.getScaleTicket() + "' ");
					sqlString.append("   AND G3CSEQ1 = " + urfp.getScaleTicketCorrectionSequence() + " ");
					sqlString.append("   AND G3SEQ# = " + urfp.getSequenceNumber() + " ");
			
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building SQL for Update of PO - GRPCPO file. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceRawFruit.");
				throwError.append("BuildSQL.updatePO(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * @author twalto  9/12/13
		 *   -- moved from the buildSqlStatement section of this Service
		 * Build an SQL statement to copy a PO from an different one
		 * Send in CommonRequestBean -
		 *      Environment 
		 *      Level 1 - Scale Ticket Number
		 *      Level 2 - Scale Ticket Correction Sequence Number
		 *      Level 3 - NEW: Scale Ticket Correction Sequence Number
		 *      User - Last Change User
		 *      Date - Last Change Date
		 *      Time - Last Change Time
		 *      RebuildOption - to copy Positive information or Negative  
		 */
		private static String copyTicket(CommonRequestBean crb)
		throws Exception {
			
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
		
				sqlString.append("INSERT INTO " + ttlib + ".GRPCTICK ");
				sqlString.append("(SELECT G4SCALE#, " + crb.getIdLevel3() + ", G4SEQ#, G4PO#, G4PLIN#, ");
				sqlString.append(" G4RDATE, G4FACI, G4WHSE, G4LOT, G4TSEQ#, G4LOC, ");
				sqlString.append(" G4HYEAR, G4SUPP, G4SLOAD, G4CROP, G4CNVOR, G4RUNT, ");
				sqlString.append(" G4IUSE, G4VAR, G4ITEM, G4CULLP, G4CULLW, G4BRIX, ");
				sqlString.append(" G4BRIXP, ");
				
				if (crb.getRebuildOption().trim().equals("copyNegative"))
				{
					sqlString.append("(G4A25B * -1), (G4A30B * -1), G4ABINC, (G4ABW * -1), G4ABWO, ");     
					sqlString.append("(G4R25B * -1), (G4R30B * -1), G4RBINC, G4RBWO, (G4RBW * -1), ");   
					sqlString.append(" G4CTYO, G4AVAR, (G4T25B * -1), (G4T30B * -1), (G4TBW * -1), ");
					sqlString.append(" (G4AVGW * -1), ");
				}
				else
				{
					sqlString.append("G4A25B, G4A30B, G4ABINC, G4ABW, G4ABWO, ");     
					sqlString.append("G4R25B, G4R30B, G4RBINC, G4RBWO, G4RBW, ");   
					sqlString.append("G4CTYO, G4AVAR, G4T25B, G4T30B, G4TBW, ");
					sqlString.append("G4AVGW, ");
				}
				sqlString.append("'" + crb.getUser() + "', "); 
				sqlString.append(crb.getDate() + ", " + crb.getTime() + ", "); 
				sqlString.append("'0000000000', G4WUP "); 
				sqlString.append("FROM " + ttlib + ".GRPCTICK ");
				sqlString.append(" WHERE G4SCALE# = '" + crb.getIdLevel1() + "' ");
				sqlString.append("   AND G4CSEQ1 = " + crb.getIdLevel2() + " ) ");
		
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building SQL statement for copyTicket (or Lot). " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceRawFruit.");
				throwError.append("BuildSQL.copyTicket(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * @author twalto  9/12/13
		 * Build an SQL statement to Update the PO information
		 * Send in CommonRequestBean -
		 *      Environment
		 *      DateTime = Enter a Date Time Object
		 *  and the UpdRawFruitLot object 
		 */
		private static String updateLot(CommonRequestBean crb, UpdRawFruitLot urfl)
		throws Exception {
			
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
				// get current system date and time.
				DateTime dt = crb.getDateTime();
				DateTime dt1 = UtilityDateTime.getDateFromMMddyyWithSlash(urfl.getReceivingDate());
				
				// build the sql statement.
				sqlString.append("UPDATE " + ttlib + ".GRPCTICK ");
				sqlString.append(" SET ");
				sqlString.append(" G4RDATE = " + dt1.getDateFormatyyyyMMdd() + ", ");
//				sqlString.append(" G4FACI = '" + urfl.getFacility().trim() + "', "); no longer allowing update of FACI -- Update at the PO Level
//				sqlString.append(" G4WHSE = '" + urfl.getWarehouse().trim() + "', "); no longer allowing update of WHSE -- update at the PO Level
				sqlString.append(" G4LOT = '" + urfl.getLotNumber().trim() + "',");
				sqlString.append(" G4LOC = '" + urfl.getLocation().trim() + "',");
				sqlString.append(" G4HYEAR = " + urfl.getHarvestYear() + ",");
//				sqlString.append(" G4SUPP = '" + urfl.getSupplierNumber().trim() + "',"); no longer allowing update of the SUPP -- update at the PO Level
				sqlString.append(" G4SLOAD = '" + urfl.getSupplierDeliveryNote().trim() + "',");
				sqlString.append(" G4CROP = '" + urfl.getCrop().trim() + "',");
				try
				{
					if (!urfl.getItemNumber().equals("") &&
					    !urfl.getItemNumber().equals("75000") &&
						urfl.getOrganic().equals(""))
					{
						BeanItem bi = ServiceItem.buildNewItem("PRD", urfl.getItemNumber());
				        sqlString.append(" G4CNVOR = '" + bi.getItemClass().getOrganicConventional() + "',");
					}
				}
				catch(Exception e)
				{}
				sqlString.append(" G4RUNT = '" + urfl.getRunType() + "',");
//				sqlString.append(" G4IUSE = '" + urfl.getIntendedUse().trim() + "',");
				sqlString.append(" G4VAR = '" + urfl.getVariety().trim() + "',");
//				sqlString.append(" G4AVAR = '" + urfl.getAdditionalVariety() + "',");
				sqlString.append(" G4CTYO = '" + urfl.getCountryOfOrigin() + "', ");
				sqlString.append(" G4ITEM = '" + urfl.getItemNumber().trim() + "',");
				sqlString.append(" G4CULLW = " + urfl.getCullsPounds() + ",");
				sqlString.append(" G4BRIX = " + urfl.getBrix() + ",");
				sqlString.append(" G4A25B = " + urfl.getAcceptedBins25() + ",");
				sqlString.append(" G4A30B = " + urfl.getAcceptedBins30() + ",");
				sqlString.append(" G4ABINC = '" + urfl.getAcceptedBinsComment() + "',");
				BigDecimal totalWeight = new BigDecimal("0");
				if (!urfl.getAcceptedWeightKeyed().trim().equals(""))
				{   
					sqlString.append(" G4ABWO = 'Y', ");
					totalWeight = new BigDecimal(urfl.getAcceptedWeight());
				}
				else
					sqlString.append(" G4ABWO = ' ', ");
				
				sqlString.append(" G4ABW = " + urfl.getAcceptedWeight() + ",");
				sqlString.append(" G4R25B = " + urfl.getRejectedBins25() + ",");
				sqlString.append(" G4R30B = " + urfl.getRejectedBins30() + ",");
				sqlString.append(" G4RBINC = '" + urfl.getRejectedBinsComment() + "',");
				if (!urfl.getRejectedWeightKeyed().trim().equals(""))
				{
					sqlString.append(" G4RBWO = 'Y', ");
					totalWeight = totalWeight.add(new BigDecimal(urfl.getRejectedWeight()));
				}
				else
					sqlString.append(" G4RBWO = '', ");
				sqlString.append(" G4RBW = " + urfl.getRejectedWeight() + ",");
				int total25Box = (new Integer(urfl.getAcceptedBins25())).intValue() + (new Integer(urfl.getRejectedBins25())).intValue();
				sqlString.append(" G4T25B = " + total25Box + ","); // CALCULATION
				int total30Box = (new Integer(urfl.getAcceptedBins30())).intValue() + (new Integer(urfl.getRejectedBins30())).intValue();
				sqlString.append(" G4T30B = " + total30Box + ","); // CALCULATION
				if (totalWeight.compareTo(new BigDecimal("0")) != 0)
				{
					sqlString.append(" G4TBW = " + totalWeight + ",");
				}
				sqlString.append(" G4USER = '" + urfl.getUpdateUser().trim() + "',");
				sqlString.append(" G4DATE = " + dt.getDateFormatyyyyMMdd().trim() + ",");
				sqlString.append(" G4TIME = " + dt.getTimeFormathhmmss().trim() + ", ");
				sqlString.append(" G4WUP = '" + urfl.getWriteUpNumber().trim() + "' ");
				sqlString.append(" WHERE G4SCALE# = '" + urfl.getScaleTicket().trim() + "' ");
				sqlString.append("   AND G4CSEQ1 = " + urfl.getScaleTicketCorrectionSequence() + " ");
				sqlString.append("   AND G4SEQ# = " + urfl.getScaleSequence() + " ");
				sqlString.append("   AND G4TSEQ# = " + urfl.getLotSequenceNumber() + " ");
						
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building SQL for Update of LOT - GRPCTICK file. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceRawFruit.");
				throwError.append("BuildSQL.updateLot(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * @author twalto  9/13/13
		 *   -- moved from the buildSqlStatement section of this Service
		 * Build an SQL statement to find a Load
		 * Send in CommonRequestBean -
		 *      Environment 
		 *      Level 1 - Scale Ticket Number
		 *      Level 2 - Scale Ticket Correction Sequence Number
		 */
		private static String findLoad(CommonRequestBean crb)
		throws Exception {
			
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {

				sqlString.append("SELECT ");
				// GRPCSCALE  -- 43 Fields
				sqlString.append(" G1SCALE#, G1CSEQ1,  G1RDATE,  G1LOADT, G1GRWGT, ");
				sqlString.append(" G1LWGT,   G1NLWGT,  G1BWGT,   G1AFWGT, G1FBINS, ");
				sqlString.append(" G1EBINS,  G1TBINS,  G1TBOXES, G1LBSBX, G1BNBLK, ");
				sqlString.append(" G1CBOL,   G1SCSUPP, G1FRATE,  G1FRTFL, G1FSCHG, ");
				sqlString.append(" G1FRLOC,  G1WHTIK,  G1MIMW,   G1MNLB,  G1COMP,  ");
				sqlString.append(" G1FTCCD,  G1FLCCD,  G1FACI,   G1WHSE,  G1CCNT,  ");
				sqlString.append(" G1USER,   G1DATE,   G1TIME,   G1VONO,  G1JRNO,");
				sqlString.append(" G1JSNO,   G1HCOD,   G1DIM5,   G1CCTR, G1POSTF, ");
				sqlString.append(" G1TRACK,  G1RTIME,  G1INSP, ");
				// GRPCPO   -- 20 Fields
				sqlString.append(" G3SCALE#, G3CSEQ1,  G3SEQ#,   G3PO#,   G3PLIN#, ");
				sqlString.append(" G3CPO,    G3AWGT,   G3RWGT,   G3TWGT,  G3ABINS, ");
				sqlString.append(" G3RBINS,  G3TBINS,  G3COMP,   G3FACI,  G3WHSE,  ");
				sqlString.append(" G3SUPP,   G3USER,   G3DATE,   G3TIME,  G3POSTF, ");
				// FGRECL
				sqlString.append(" F2PUNO, F2PNLI, F2IMDT, ");
				// DCPALL -- 1 field Descriptive Code - used for Long Description for From Location
				sqlString.append("DCPT80, ");
				// CIDMAS - M3 - Supply Master -- 4 more fields
				sqlString.append(" A.IDSUNO as AIDSUNO, A.IDSUNM as AIDSUNM, "); // for LOAD // Field to Use for the Name
				sqlString.append(" B.IDSUNO as BIDSUNO, B.IDSUNM as BIDSUNM, "); // for PO   // Field to Use for the Name
				// MITWHL  - M3 - Warehouse Master -- 4 more fields
				sqlString.append(" C.MWWHLO as CMWWHLO, C.MWWHNM as CMWWHNM, "); // for LOAD // Warehouse Name
				sqlString.append(" D.MWWHLO as DMWWHLO, D.MWWHNM as DMWWHNM, "); // for PO   // Warehouse Name
				// CFACIL - M3 - Facility Master -- 4 more fields
				sqlString.append(" E.CFFACI as ECFFACI, E.CFFACN as ECFFACN, "); // for LOAD // Facility Name
				sqlString.append(" F.CFFACI as FCFFACI, F.CFFACN as FCFFACN  "); // for PO   // Facility Name
				// File Connections			
				sqlString.append("FROM " + ttlib + ".GRPCSCALE ");
				sqlString.append(" LEFT OUTER JOIN " + ttlib + ".GRPCPO ");
				sqlString.append("   ON G1SCALE# = G3SCALE# AND G1CSEQ1 = G3CSEQ1 ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".FGRECL ");
				sqlString.append("   ON F2CONO = 100 and F2DIVI = '100' and F2PUNO = G3PO# and F2PNLI = G3PLIN# ");
				sqlString.append(" LEFT OUTER JOIN " + ttlib + ".DCPALL ");
				sqlString.append("   ON DCPTYP = 'V' and DCPK00 = 'RFLOCATION' ");
				sqlString.append("      and DCPK01 = G1FRLOC ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".CIDMAS A ");
				sqlString.append("   ON G1SCSUPP = A.IDSUNO ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".CIDMAS B ");
				sqlString.append("   ON G3SUPP = B.IDSUNO ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".MITWHL C ");
				sqlString.append("   ON G1WHSE = C.MWWHLO ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".MITWHL D ");
				sqlString.append("   ON G3WHSE = D.MWWHLO ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".CFACIL E ");
				sqlString.append("   ON G1FACI = E.CFFACI ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".CFACIL F ");
				sqlString.append("   ON G3FACI = F.CFFACI ");
				sqlString.append(" WHERE G1SCALE# = '" + crb.getIdLevel1() + "' ");
				sqlString.append("  AND G1CSEQ1 = " + crb.getIdLevel2() + " ");
				sqlString.append(" ORDER BY G1SCALE#, G1CSEQ1, G3SEQ#, G3PO# ");
		
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building SQL statement for findLoad. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceRawFruit.");
				throwError.append("BuildSQL.findLoad(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * @author twalto  9/12/13
		 *   -- moved from the buildSqlStatement section of this Service
		 * Build an SQL statement to copy a PO from an different one
		 * Send in CommonRequestBean -
		 *      Environment 
		 *      Level 1 - Scale Ticket Number
		 *      Level 2 - Scale Ticket Correction Sequence Number
		 *      Level 3 - Scale Sequence
		 *      Level 4 - Purchase Order Number
		 *      Level 5 - Ticket Sequence
		 *      Level 6 - Lot Number
		 */
		private static String findLot(CommonRequestBean crb)
		throws Exception {
			
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
		
				// GRPCTICK   -- 45 Fields
				sqlString.append("SELECT G4SCALE#, G4CSEQ1, G4SEQ#,  G4PO#,    G4PLIN#, ");
				sqlString.append("       G4RDATE,  G4FACI,  G4WHSE,  G4LOT,    G4TSEQ#, ");
				sqlString.append("       G4LOC,    G4HYEAR, G4SUPP,  G4SLOAD,  G4CROP,  ");
				sqlString.append("       G4CNVOR,  G4RUNT,  G4IUSE,  G4VAR,    G4ITEM,  ");
				sqlString.append("       G4CULLP,  G4CULLW, G4BRIX,  G4BRIXP,  G4A25B,  ");
				sqlString.append("       G4A30B,   G4ABINC, G4ABW,   G4ABWO,   G4R25B,  ");
				sqlString.append("       G4R30B,   G4RBINC, G4RBWO,  G4RBW,    G4CTYO,  ");
				sqlString.append("       G4AVAR,   G4T25B,  G4T30B,  G4TBW,    G4AVGW,  ");
				sqlString.append("       G4USER,   G4DATE,  G4TIME,  G4POSTF,  G4WUP,  ");
				// GRPCPMT    -- 33 Fields 
				sqlString.append("       G5SCALE#, G5CSEQ1, G5SEQ#,  G5PO#,    G5PLIN#, ");
				sqlString.append("       G5LOT,    G5TSEQ#, G5YSEQ#, G5CPTP,   G5CRTP,  ");
				sqlString.append("       G5CAT,    G525B,   G530B,   G5TBW,    G5TBWO,  ");
				sqlString.append("       G5PCODE,  G5M3PCD, G5CMV,   G5PRICE,  G5ADJP,  ");
				sqlString.append("       G5PRICEM, G5CROP,  G5CNVOR, G5VAR,    G5INVC,  ");
				sqlString.append("       G5INV#,   G5USER,  G5DATE,  G5TIME,   G5VONO,  ");
				sqlString.append("       G5JRNO,   G5JSNO,  G5POSTF, G5WUP, ");
				// GRPCOTHC   -- 19 Fields
				sqlString.append("       G6SCALE#, G6CSEQ1, G6SEQ#,  G6PO#,    G6PLIN#, ");
				sqlString.append("       G6LOT,    G6TSEQ#, G6YSEQ#, G6OSEQ#,  G6SUPP,  ");
				sqlString.append("       G6RATE,   G6CCODE, G6USER,  G6DATE,   G6TIME, ");
				sqlString.append("       G6VONO,   G6JRNO,  G6JSNO,  G6POSTF, ");
				// CIDMAS  -- Service Supplier will need to be changed for the Load fields if additional fields are added 
				sqlString.append("       A.IDSUNO as AIDSUNO, A.IDSUNM as AIDSUNM, ");  // for TICKET - for first name
				sqlString.append("       B.IDSUNO as BIDSUNO, B.IDSUNM as BIDSUNM, ");  // for Other Charge - - for the second name
				// FPLOPT  -- 1 Field
				sqlString.append("       FUTX40, "); // Number 87 - Accounting Option Long Description
				// MITMAS  -- 3 Fields
				sqlString.append("       MMITDS, MMATMO, MMEVGR, ");
				// MITWHL    -- 2 Fields
				sqlString.append("       MWWHLO, MWWHNM, ");
				// CFACIL   -- 2 fields
				sqlString.append("       CFFACI, CFFACN, ");
				// GRPCSCALE -- 1 Field
				sqlString.append("       G1CCNT, ");// need to know the correction count for editing purposes
				// GRPSCONTR -- 1 Field
				sqlString.append("       GRSCVR1 ");
					
				sqlString.append("FROM " + ttlib + ".GRPCTICK ");
				sqlString.append(" LEFT OUTER JOIN " + ttlib + ".GRPCPMT ");
				sqlString.append("   ON  G4SCALE# = G5SCALE# AND G4CSEQ1 = G5CSEQ1 ");
				sqlString.append("   AND G4SEQ#   = G5SEQ#   AND G4PO#   = G5PO# ");
				sqlString.append("   AND G4PLIN#  = G5PLIN#  AND G4LOT   = G5LOT ");
				sqlString.append("   AND G4TSEQ#  = G5TSEQ# ");
				sqlString.append(" LEFT OUTER JOIN " + ttlib + ".GRPCOTHC ");
				sqlString.append("   ON  G5SCALE# = G6SCALE# AND G5CSEQ1 = G6CSEQ1 ");
				sqlString.append("   AND G5SEQ#   = G6SEQ#   AND G5PO#   = G6PO# ");
				sqlString.append("   AND G5PLIN#  = G6PLIN#  AND G5LOT   = G6LOT ");
				sqlString.append("   AND G5TSEQ#  = G6TSEQ#  AND G5YSEQ# = G6YSEQ# ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".CIDMAS A ");
				sqlString.append("   ON G4SUPP = A.IDSUNO ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".CIDMAS B ");
				sqlString.append("   ON G6SUPP = B.IDSUNO ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".FPLOPT ");
				sqlString.append("   ON FUCONO = 100 AND FUDIVI = '100' ");
				sqlString.append("   AND FUPLOP = G6CCODE ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".MITMAS ");
				sqlString.append("   ON MMITNO = G4ITEM ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".MITWHL ");
				sqlString.append("   ON MWWHLO = G4WHSE ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".CFACIL ");
				sqlString.append("   ON CFFACI = G4FACI ");
				sqlString.append(" LEFT OUTER JOIN " + ttlib + ".GRPCSCALE ");
				sqlString.append("   ON G4SCALE# = G1SCALE# AND G4CSEQ1 = G1CSEQ1 ");
				sqlString.append(" LEFT OUTER JOIN " + ttlib + ".GRPSCONTR ");
				sqlString.append("   ON GRSCYR = G4HYEAR AND GRSCUSG = 'G' ");
				sqlString.append("   AND GRSCDSC = 'Grape Due Date' ");
				sqlString.append(" WHERE G4SCALE# = '" + crb.getIdLevel1().trim() + "' ");
				sqlString.append("   AND G4CSEQ1 = " + crb.getIdLevel2().trim() + " ");
				sqlString.append("   AND G4SEQ# = " + crb.getIdLevel3().trim() + " ");
				sqlString.append("   AND G4PO# = " + crb.getIdLevel4().trim() + " ");
				sqlString.append("   AND G4TSEQ# = " + crb.getIdLevel5().trim() + " ");
				sqlString.append("   AND G4LOT = '" + crb.getIdLevel6().trim() + "' ");
						
				sqlString.append(" ORDER BY G4SCALE#, G4SEQ#, G4TSEQ#, G4LOT, G5YSEQ#, G6OSEQ# ");

			//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building SQL statement for findLot -- Lot and Ticket are the same thing. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceRawFruit.");
				throwError.append("BuildSQL.findLot(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * @author twalto  9/19/13
		 *   -- moved from the buildSqlStatement section of this Service
		 * Build an SQL statement to copy a Payment from an different one
		 * Send in CommonRequestBean -
		 *      Environment 
		 *      Level 1 - Scale Ticket Number
		 *      Level 2 - Scale Ticket Correction Sequence Number
		 *      Level 3 - NEW: Scale Ticket Correction Sequence Number
		 *      User - Last Change User
		 *      Date - Last Change Date
		 *      Time - Last Change Time
		 *      RebuildOption - to copy Positive information or Negative  
		 */
		private static String copyPayment(CommonRequestBean crb)
		throws Exception {
			
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
				
				sqlString.append("INSERT INTO " + ttlib + ".GRPCPMT ");
				sqlString.append("(SELECT G5SCALE#, " + crb.getIdLevel3() + ", G5SEQ#, G5PO#, G5PLIN#, ");
				sqlString.append(" G5LOT, G5TSEQ#, G5YSEQ#, G5CPTP, G5CRTP, ");
				sqlString.append(" G5CAT, ");
				
				if (crb.getRebuildOption().trim().equals("copyNegative"))
				{
					sqlString.append("(G525B * -1), (G530B * -1), (G5TBW * -1), ");     
				}else{
					sqlString.append("G525B, G530B, G5TBW, ");  
				}
				sqlString.append(" G5TBWO, G5PCODE,  G5M3PCD, G5CMV,   G5PRICE,  ");
				sqlString.append(" G5ADJP, G5PRICEM, G5CROP,  G5CNVOR, G5VAR,    ");
				sqlString.append(" G5INVC, G5INV#, '" + crb.getUser() + "', "); 
				sqlString.append(crb.getDate() + ", " + crb.getTime() + ", ");
				sqlString.append("0,0,0,'0000000000', G5WUP ");
				sqlString.append("FROM " + ttlib + ".GRPCPMT ");
				sqlString.append(" WHERE G5SCALE# = '" + crb.getIdLevel1() + "' ");
				sqlString.append("   AND G5CSEQ1 = " + crb.getIdLevel2() + " ) ");
		
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building SQL statement for copyPayment. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceRawFruit.");
				throwError.append("BuildSQL.copyPayment(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * @author twalto  9/19/13
		 *   -- moved from the buildSqlStatement section of this Service
		 * Build an SQL statement to return a statement to delete the Payment Record
		 * Send in CommonRequestBean -
		 *      Environment 
		 *      Level 1 - Scale Ticket Number
		 *      Level 2 - Scale Ticket Correction Sequence Number
		 *      Level 3 - Scale Sequence Number
		 *      Level 4 - Purchase Order Number
		 *      Level 5 - Lot Sequence
		 *      Level 6 - Lot Number
		 */
		private static String deletePayment(CommonRequestBean crb)
		throws Exception {
			
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
				sqlString.append("DELETE FROM " + ttlib + ".GRPCPMT ");
				sqlString.append(" WHERE G5SCALE# = '" + crb.getIdLevel1().trim() + "' ");
				sqlString.append("   AND G5CSEQ1 = " + crb.getIdLevel2().trim() + " ");
				sqlString.append("   AND G5SEQ# = " + crb.getIdLevel3().trim() + " ");
				sqlString.append("   AND G5PO# = " + crb.getIdLevel4().trim() + " ");
				sqlString.append("   AND G5TSEQ# = " + crb.getIdLevel5().trim() + " ");
				sqlString.append("   AND G5LOT = '" + crb.getIdLevel6().trim() + "' ");
		
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building SQL statement for deletePayment. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceRawFruit.");
				throwError.append("BuildSQL.deletePayment(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * @author twalto  9/19/13
		 *   -- moved from the buildSqlStatement section of this Service
		 * Build an SQL statement to return a statement to add a Payment Record
		 * Send in CommonRequestBean -
		 *      Environment 
		 *      DateTime = Enter a Date Time Object
		 *      
		 *  and the UpdRawFruitLotPayment object 
		 */
		private static String addPayment(CommonRequestBean crb, UpdRawFruitLotPayment rflp)
		throws Exception {
			
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
				DateTime dt1 = UtilityDateTime.getDateFromMMddyyWithSlash(rflp.getReceivingDate());
					
				sqlString.append("INSERT INTO " + ttlib + ".GRPCPMT ");
				sqlString.append("(G5SCALE#, G5CSEQ1, G5SEQ#,  G5PO#,   G5PLIN#, ");
				sqlString.append(" G5LOT,    G5TSEQ#, G5YSEQ#, G5CPTP,  G5CRTP, ");
				sqlString.append(" G5CAT,    G525B,   G530B,   G5TBW,   G5TBWO,  ");
				sqlString.append(" G5PCODE,  G5M3PCD, G5CMV,   G5PRICE, G5ADJP,  ");
				sqlString.append(" G5PRICEM, G5CROP,  G5CNVOR, G5VAR,   G5INVC,  ");
				sqlString.append(" G5INV#,   G5USER,  G5DATE,  G5TIME,  G5VONO, ");
				sqlString.append(" G5JRNO,   G5JSNO,  G5POSTF, G5WUP ) ");
				sqlString.append(" VALUES( ");
				sqlString.append("'" + rflp.getScaleTicket().trim() + "',"); // Scale Ticket
				sqlString.append(rflp.getScaleTicketCorrectionSequence() + ","); // Scale Ticket Correction Sequence
				sqlString.append(rflp.getScaleSequence() + ","); // Scale Sequence
				sqlString.append(rflp.getPoNumber() + ","); // PO Number
				sqlString.append(rflp.getPoLineNumber() + ","); // PO Line Number
				sqlString.append("'" + rflp.getLotNumber() + "',"); // Lot Number
				sqlString.append(rflp.getLotSequenceNumber() + ","); // Lot Sequence Number
				// Payment Sequence Number
				   // Number +1 times 10
				BigDecimal paymentSequence = new BigDecimal("10");
				try
				{
				   	BigDecimal ps = new BigDecimal(rflp.getPaymentSequence());
				   	paymentSequence = (ps.add(new BigDecimal("1"))).multiply(new BigDecimal("10"));
				}catch(Exception e)	{}
				
				sqlString.append(paymentSequence + ","); // Payment Sequence Number
				sqlString.append("'" + rflp.getPaymentType() + "',"); // Payment Type
				sqlString.append("'" + rflp.getRunType() + "',"); // Run Type
				sqlString.append("'" + rflp.getCategory() + "',"); // Category
				sqlString.append(rflp.getNumberOfBins25Box() + ","); // Number of Bins 25 Box
				sqlString.append(rflp.getNumberOfBins30Box() + ","); // Number of Bins 30 Box
				if (rflp.getPaymentWeightManuallyEntered().equals(""))
				   sqlString.append("0,"); // Fruit Weight -- Will need to be Calculated
				else
				   sqlString.append(rflp.getPaymentWeight() + ", ");
				if (!rflp.getPaymentWeightManuallyEntered().trim().equals(""))
					sqlString.append("'Y', ");
				else
					sqlString.append("' ', ");
				RawFruitPayCode rfpc = new RawFruitPayCode();
				try
				{
					 Vector sendParms = new Vector();
					 if (rflp.getCrop().equals("GRAPE") &&
					     rflp.getOrganic().length() > 1)
					 {
						sendParms.addElement("grape");
					    sendParms.addElement(rflp.getOrganic().substring(0,1));
					    sendParms.addElement(rflp.getBrix());
					 }else{
						sendParms.addElement(rflp.getVariety());
						sendParms.addElement(rflp.getOrganic());
						sendParms.addElement(rflp.getCrop());
						sendParms.addElement(rflp.getRunType());
						sendParms.addElement(rflp.getCategory());
						sendParms.addElement(rflp.getPaymentType());
						sendParms.addElement(rflp.getItemNumber());
					 }
				     sendParms.addElement(dt1.getDateFormatyyyyMMdd());
				     rfpc = findPayCode(sendParms);
				}catch(Exception e)	{
					System.out.println("Debug the Payment Code Retrieval: " + e);
				}
				sqlString.append("'" + rfpc.getPayCode().trim() + "',"); // Payment Code -- Calculated - Generated Code
				sqlString.append("'" + rflp.getPayCodeHandKeyed() + "', ");
				sqlString.append("0,"); // CMV - Standard Fruit Cost // Retrieved
				sqlString.append(rfpc.getPricePerTon() + ","); // Default - Fruit Price // Retrieved
				sqlString.append("0,"); // Calculated Price Adjustment
				String price = rflp.getPrice();
				try
				{
//					System.out.println(new BigDecimal(price));
//					System.out.println(new BigDecimal("0"));
//					System.out.println((new BigDecimal(price)).compareTo(new BigDecimal("0")));
//					System.out.println((new BigDecimal("0")).compareTo(new BigDecimal("0")));
					if ((new BigDecimal(price)).compareTo(new BigDecimal("0")) == 0)
					   price = rfpc.getPricePerTon();
				}catch(Exception e)
				{}
				sqlString.append(price + ","); // Hand Keyed Price
				sqlString.append("'" + rflp.getCrop() + "',"); // Crop for Payment
				sqlString.append("'" + rflp.getOrganic() + "',"); // Organic / Conventional for Payment
				sqlString.append("'" + rflp.getVariety() + "',"); // Variety for Payment
				sqlString.append("'',"); // Invoice Created Flag 
				sqlString.append("'',"); // Invoice Number
				sqlString.append("'" + rflp.getUpdateUser().trim() + "',"); // User
				sqlString.append(crb.getDateTime().getDateFormatyyyyMMdd() + ","); // Date
				sqlString.append(crb.getDateTime().getTimeFormathhmmss() + ","); // Time
				sqlString.append("0,"); // Movex Voucher Number
				sqlString.append("0,"); // Movex Journal Number
				sqlString.append("0,"); // Movex Journal Sequence Number
				sqlString.append("'0000000000', "); // Posting Flags
				sqlString.append("'" + rflp.getWriteUpNumber().trim() + "' "); // Write Up Number
				sqlString.append(") ");
				
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building SQL statement for addPayment. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceRawFruit.");
				throwError.append("BuildSQL.addPayment(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * @author twalto  9/23/13
		 *   -- moved from the buildSqlStatement section of this Service
		 * Build an SQL statement to return a statement to delete the Special Charges Record
		 * Send in CommonRequestBean -
		 *      Environment 
		 *      Level 1 - Scale Ticket Number
		 *      Level 2 - Scale Ticket Correction Sequence Number
		 *      Level 3 - Scale Sequence Number
		 *      Level 4 - Purchase Order Number
		 *      Level 5 - Lot Sequence
		 *      Level 6 - Lot Number
		 */
		private static String deleteSpecialCharges(CommonRequestBean crb)
		throws Exception {
			
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
				
				sqlString.append("DELETE FROM " + ttlib + ".GRPCOTHC ");
				sqlString.append(" WHERE G6SCALE# = '" + crb.getIdLevel1().trim() + "' ");
				sqlString.append(" AND G6CSEQ1 = " + crb.getIdLevel2() + " ");			
				sqlString.append("   AND G6SEQ# = " + crb.getIdLevel3() + " ");
				sqlString.append("   AND G6PO# = " + crb.getIdLevel4() + " ");
				sqlString.append("   AND G6TSEQ# = " + crb.getIdLevel5() + " ");
				sqlString.append("   AND G6LOT = '" + crb.getIdLevel6().trim() + "' ");
				
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building SQL statement for deleteSpecialCharges. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceRawFruit.");
				throwError.append("BuildSQL.deleteSpecialCharges(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * @author twalto  9/23/13
		 *   -- moved from the buildSqlStatement section of this Service
		 * Build an SQL statement to return a statement to add a Special Charges Record
		 * Send in CommonRequestBean -
		 *      Environment 
		 *      DateTime = Enter a Date Time Object
		 *      
		 *  and the UpdRawFruitLotPaymentSpecialCharges object 
		 */
		private static String addSpecialCharges(CommonRequestBean crb, UpdRawFruitLotPaymentSpecialCharges rflpsc)
		throws Exception {
					
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
			//		UpdRawFruitLotPaymentSpecialCharges sc = (UpdRawFruitLotPaymentSpecialCharges) requestClass.elementAt(0);
					// get current system date and time.
			//		DateTime dt = (DateTime) requestClass.elementAt(1);
							
							
							
				sqlString.append("INSERT INTO " + ttlib + ".GRPCOTHC ");
				sqlString.append("(G6SCALE#, G6CSEQ1, G6SEQ#,  G6PO#,   G6PLIN#, ");
				sqlString.append(" G6LOT,    G6TSEQ#, G6YSEQ#, G6OSEQ#, G6SUPP, ");
				sqlString.append(" G6RATE,   G6CCODE, G6USER,  G6DATE,  G6TIME, ");
				sqlString.append(" G6VONO,   G6JRNO,  G6JSNO,  G6POSTF ) ");
				sqlString.append(" VALUES( ");
				sqlString.append("'" + rflpsc.getScaleTicket().trim() + "',"); // Scale Ticket
				sqlString.append(rflpsc.getScaleTicketCorrectionSequence() + ","); // Scale Ticket Correction Sequence
				sqlString.append(rflpsc.getScaleSequence() + ","); // Scale Sequence
				sqlString.append(rflpsc.getPoNumber() + ","); // PO Number
				sqlString.append(rflpsc.getPoLineNumber() + ","); // PO Line Number
				sqlString.append("'" + rflpsc.getLotNumber() + "',"); // Lot Number
				sqlString.append(rflpsc.getLotSequenceNumber() + ","); // Lot Sequence Number
//				Payment Sequence Number
				 // Number +1 times 10
				BigDecimal paymentSequence = new BigDecimal("10");
				try	{
				   	BigDecimal ps = new BigDecimal(rflpsc.getPaymentSequence());
				   	paymentSequence = (ps.add(new BigDecimal("1"))).multiply(new BigDecimal("10"));
				}catch(Exception e)	{}
				sqlString.append(paymentSequence + ","); // Payment Sequence Number
				sqlString.append(rflpsc.getPaymentSpecialChargesSequence() + ","); // Special Charges Sequence Number
				sqlString.append("'" + rflpsc.getSupplier() + "',"); // Supplier
				sqlString.append(new BigDecimal(rflpsc.getRatePerPound()) + ","); // Rater Per Pound
				sqlString.append(rflpsc.getAccountingOption() + ","); // Accounting Option - Charge Code
				sqlString.append("'" + rflpsc.getUpdateUser().trim() + "',"); // User
				sqlString.append(crb.getDateTime().getDateFormatyyyyMMdd() + ","); // Date
				sqlString.append(crb.getDateTime().getTimeFormathhmmss() + ", "); // Time
				sqlString.append("0,"); // Movex Voucher Number
				sqlString.append("0,"); // Movex Journal Number
				sqlString.append("0,"); // Movex Journal Sequence Number
				sqlString.append("'' "); // Posting Flags
				sqlString.append(") ");
											
				//	*********************************************************************************
			} catch (Exception e) {
				throwError.append(" Error building SQL statement for addSpecialCharges. " + e);
			}
						
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceRawFruit.");
				throwError.append("BuildSQL.addSpecialCharges(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * @author twalto  9/23/13
		 *   -- moved from the buildSqlStatement section of this Service
		 * Build an SQL statement to Find out if the Item Whse combination has a Record in M3
		 * Send in CommonRequestBean -
		 *      Environment 
		 *      Level 1 - Item Number
		 *      Level 2 - Warehouse Number
		 */
		private static String verifyItemWhse(CommonRequestBean crb)
		throws Exception {
			
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
		
				sqlString.append("SELECT MBITNO, MBWHLO ");
				sqlString.append("FROM " + library + ".MITBAL ");
				sqlString.append(" WHERE MBCONO = 100 ");
				sqlString.append("  AND MBITNO = '" + crb.getIdLevel1().trim() + "' ");
				sqlString.append("  AND MBWHLO = '" + crb.getIdLevel2().trim() + "' ");
				sqlString.append(" ORDER BY MBCONO, MBWHLO, MBITNO ");
		
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building SQL statement for verifyItemWhse. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceRawFruit.");
				throwError.append("BuildSQL.verifyItemWhse(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}
		
	} // END of the Build SQL class OBJECT

	/**
	 * @author twalto 8/2012 ** NEW STYLE **
	 * Load fields retrieved from Result Sets, running SQL statements.
	 */
	private static class LoadFields {
	
		/**
		 * @author twalto  8/20/12
		 * Build a Drop Down Single Object from a Result Set
		 * Send in Result Set
		 */
		private static DropDownSingle loadDropDownDescriptiveCode(ResultSet rs)
			throws Exception {
			DropDownSingle returnObject = new DropDownSingle();	
			StringBuffer throwError     = new StringBuffer();
			try {
				returnObject.setValue(rs.getString("DCPK01"));
				returnObject.setDescription(rs.getString("DCPT80").trim());
							
	//		*********************************************************************************
			} catch (Exception e) {
				    throwError.append("Error at com.treetop.services.ServiceRawFruit.");
					throwError.append(" Error in LoadFields.loadDropDownDescriptiveCode(rs). " + e);
					throw new Exception(throwError.toString());
			}
		return returnObject;
		}
		
	} // END of the Load Fields class OBJECT

	public static final String library = "M3DJDPRD";
	public static final String ttlib = "DBPRD";
	
	// For Testing change to TST
	//public static final String library = "M3DJDTST";
	//public static final String ttlib = "DBTST";
	
	/**
	 * 
	 */
	public ServiceRawFruit() {
		super();
	}
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		String start = "yes";
		
		try
		{
			if (1 == 2)
			{
				Vector v = new Vector();
				v = dropDownBinType(v);
				String x = "stop here";
			}
			
			//test quick entry screen requirements.
			if (1 == 2)
			{
				CommonRequestBean crb = new CommonRequestBean();
				crb.setEnvironment("PRD");
				Vector list = buildLotItemVarietyRuntype(crb);
				String stopHere = "please";
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Load class fields from result set.
	 */
	
	public static RawFruitPayCode loadFieldsRawFruitPayCode(String requestType,
							          					    ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		RawFruitPayCode returnValue = new RawFruitPayCode();
	
		try{ 
			if (requestType.equals("payCode"))
			{
				try
				{
//					 Grower Pay Information - GRPKCODE 
					 returnValue.setPayCode(rs.getString("GRKCODE").trim());
					 returnValue.setVariety(rs.getString("GRKVTY").trim());
					 returnValue.setConvOrganic(rs.getString("GRKCNVOR").trim());
					 returnValue.setCrop(rs.getString("GRKCROP").trim());
					 returnValue.setRunType(rs.getString("GRKRUN").trim());
					 returnValue.setCategory(rs.getString("GRKCAT").trim());
					 returnValue.setPaymentType(rs.getString("GRKPTP").trim());
					 returnValue.setItemNumber(rs.getString("GRKITEM").trim());
					 returnValue.setWithholdPerPound(rs.getString("GRKWHP"));
					 returnValue.setWithholdPerTon(rs.getString("GRKWHT"));
					 
					// Pricing Information based on Code
					 if (rs.getString("GRJPRAM") == null)
					   returnValue.setPricePerTon("0");
					 else
					   returnValue.setPricePerTon(rs.getString("GRJPRAM"));
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}
			}
			if (requestType.equals("loadRFPayment"))
			{
				try
				{
					returnValue.setPaymentType(rs.getString("G5CPTP").trim());
					returnValue.setRunType(rs.getString("G5CRTP").trim());
					returnValue.setCategory(rs.getString("G5CAT").trim());
					returnValue.setPayCode(rs.getString("G5PCODE").trim());
					returnValue.setCrop(rs.getString("G5CROP").trim());
					returnValue.setConvOrganic(rs.getString("G5CNVOR").trim());
					returnValue.setVariety(rs.getString("G5VAR").trim());
				}
				catch(Exception e)
				{
					// problem Caught
				}
			}
			if (requestType.equals("grapePayCode"))
			{
				try
				{
					returnValue.setPaymentType("grape");
					returnValue.setCrop("GRAPE");
					returnValue.setConvOrganic(rs.getString("GBJORCNV").trim());
					if (rs.getString("GBJORCNV").trim().equals("O"))
					  returnValue.setPayCode("3480");
				    else
				      returnValue.setPayCode("3474");
					returnValue.setBrix(rs.getString("GBJBRIX").trim());
					returnValue.setAsOfDate(rs.getString("GBJAOD").trim());
					returnValue.setPricePerTon(rs.getString("GBJTON$"));
					returnValue.setPricePerPound(rs.getString("GBJLB$"));
				}
				catch(Exception e)
				{
					// problem Caught
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
			throwError.append("ServiceRawFruit.");
			throwError.append("loadFieldsRawFruitPayCode(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
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
		
	try { 
	//-------------------------------------------------------	
	// Verify Specific Raw Fruit Information
	//--------------------------------------------------------
		if (inRequestType.equals("verifyBinType"))
		{
			sqlString.append("SELECT GRATYP  ");
			sqlString.append("FROM " + ttlib + ".GRPABNMS ");
			sqlString.append(" WHERE GRATYP = '" + ((String) requestClass.elementAt(0)).toUpperCase()  + "' ");
			sqlString.append(" ORDER BY GRATYP ");
		}
		
		if (inRequestType.equals("getItemCrop"))
		{
			String item = (String) requestClass.elementAt(0);
			
			sqlString.append("SELECT MMITGR ");
			sqlString.append("FROM " + library + ".MITMAS ");
			sqlString.append("WHERE MMCONO = 100 ");
			sqlString.append("AND MMITNO = '" + item.trim() + "' ");
		}
		
		if (inRequestType.equals("verifyLoad"))
		{
			sqlString.append("SELECT G1SCALE#  ");
			sqlString.append("FROM " + ttlib + ".GRPCSCALE ");
			sqlString.append(" WHERE G1SCALE# = '" + (String) requestClass.elementAt(0)  + "' ");
			sqlString.append(" ORDER BY G1SCALE# ");
		}
		if (inRequestType.equals("verifyLot"))
		{
			sqlString.append("SELECT G4LOT  ");
			sqlString.append("FROM " + ttlib + ".GRPCTICK ");
			sqlString.append(" WHERE G4LOT = '" + (String) requestClass.elementAt(0)  + "' ");
			sqlString.append(" ORDER BY G4LOT ");
		}			
		if (inRequestType.equals("verifyPayCode"))
		{
			sqlString.append("SELECT GRKCODE  ");
			sqlString.append("FROM " + ttlib + ".GRPKCODE ");
			sqlString.append(" WHERE GRKCODE = '" + (String) requestClass.elementAt(0)  + "' ");
			sqlString.append(" ORDER BY GRKCODE ");
		}	
	//-------------------------------------------------------	
	// Drop Down Lists
	//--------------------------------------------------------
		if (inRequestType.equals("payCodeVariety"))
		{
			// build the sql statement.
			sqlString.append("SELECT GRKVTY  ");
			sqlString.append("FROM " + ttlib + ".GRPKCODE ");
			sqlString.append(" GROUP BY GRKVTY ");
			sqlString.append(" ORDER BY GRKVTY ");
		}
		if (inRequestType.equals("payCodeConvOrganic"))
		{
			// build the sql statement.
			sqlString.append("SELECT GRKCNVOR  ");
			sqlString.append("FROM " + ttlib + ".GRPKCODE ");
			sqlString.append(" GROUP BY GRKCNVOR ");
			sqlString.append(" ORDER BY GRKCNVOR ");
		}
		if (inRequestType.equals("payCodeCrop"))
		{
			// build the sql statement.
			sqlString.append("SELECT GRKCROP  ");
			sqlString.append("FROM " + ttlib + ".GRPKCODE ");
			sqlString.append(" GROUP BY GRKCROP ");
			sqlString.append(" ORDER BY GRKCROP ");
		}
		if (inRequestType.equals("payCodeMachineOrchard"))
		{
			// build the sql statement.
			sqlString.append("SELECT GRKRUN  ");
			sqlString.append("FROM " + ttlib + ".GRPKCODE ");
			sqlString.append(" WHERE GRKRUN <> ' ' ");
			sqlString.append(" GROUP BY GRKRUN ");
			sqlString.append(" ORDER BY GRKRUN ");
		}
		if (inRequestType.equals("payCodeCategory"))
		{
			// build the sql statement.
			sqlString.append("SELECT GRKCAT  ");
			sqlString.append("FROM " + ttlib + ".GRPKCODE ");
			sqlString.append(" WHERE GRKCAT <> ' ' ");
			sqlString.append(" GROUP BY GRKCAT ");
			sqlString.append(" ORDER BY GRKCAT ");
		}
		if (inRequestType.equals("payCodePaymentType"))
		{
			// build the sql statement.
			sqlString.append("SELECT GRKPTP  ");
			sqlString.append("FROM " + ttlib + ".GRPKCODE ");
			sqlString.append(" WHERE GRKPTP <> ' ' ");
			sqlString.append(" GROUP BY GRKPTP ");
			sqlString.append(" ORDER BY GRKPTP ");
		}
		if (inRequestType.equals("bins"))
		{
			// build the sql statement.
			sqlString.append("SELECT GRATYP, GRADSC  ");
			sqlString.append("FROM " + ttlib + ".GRPABNMS ");
			sqlString.append(" WHERE GRATYP <> ' ' ");
			sqlString.append(" GROUP BY GRATYP, GRADSC ");
			sqlString.append(" ORDER BY GRATYP, GRADSC ");
		}
		if (inRequestType.equals("ddHandlingCode"))
		{
			// build the sql statement.
			sqlString.append("SELECT GNAD20  ");
			sqlString.append("FROM " + ttlib + ".GNPADESC ");
			sqlString.append(" WHERE GNATYP = 'RFH' AND GNASTS = '' ");
			sqlString.append(" ORDER BY GNAD20 ");
		}
		if (inRequestType.equals("ddGrapeBrix"))
		{
			// build the sql statement.
			sqlString.append("SELECT GBJBRIX  ");
			sqlString.append("FROM " + ttlib + ".GRPJBPRC ");
			sqlString.append(" WHERE GBJORCNV = '" + ((String) requestClass.elementAt(0)).substring(0,1) + "' ");
			sqlString.append("   AND SUBSTRING(GBJAOD,1,4) = '2011' ");
			sqlString.append(" GROUP BY GBJBRIX ");
			sqlString.append(" ORDER BY GBJBRIX ");
		}
		
		if (inRequestType.equals("itemWithDescription"))
		{
			sqlString.append("SELECT MMITNO ,MMITDS ");
			sqlString.append("from " + library + ".mitmas ");
			sqlString.append("where mmitty = '200' and mmstat = 20 and mmatmo <> '' ");
			sqlString.append("order by mmitno ");
		}
		
		if (inRequestType.equals("itemModelListItems"))
		{
			sqlString.append("SELECT mmatmo ,mmitno ");
			sqlString.append("from " + library + ".mitmas ");
			sqlString.append("where mmitty = '200' and mmstat = 20 and mmatmo <> '' ");
			sqlString.append("order by mmatmo ");
		}
		
		if (inRequestType.equals("itemVarietyListModels"))
		{
			sqlString.append("SELECT AJAALF ,mmatmo ");
			sqlString.append("from " + library + ".mitmas ");
			sqlString.append("inner join m3djdprd.matvav on ajatid = 'VAR' and mmatmo = ajobv1 ");
			sqlString.append("LEFT OUTER JOIN m3djdprd.MPDOPT ON AJAALF = PFOPTN ");
			sqlString.append("where mmitty = '200' and mmstat = 20 and mmatmo <> '' ");
			sqlString.append("GROUP BY AJAALF ,mmatmo ");
			sqlString.append("ORDER BY AJAALF ");
		}
		
		if (inRequestType.equals("itemRunTypeListModels"))
		{
			sqlString.append("SELECT AJAALF ,mmatmo ");
			sqlString.append("from " + library + ".mitmas ");
			sqlString.append("inner join m3djdprd.matvav on ajatid = 'RUN' and mmatmo = ajobv1 ");
			sqlString.append("LEFT OUTER JOIN m3djdprd.MPDOPT ON AJAALF = PFOPTN ");
			sqlString.append("where mmitty = '200' and mmstat = 20 and mmatmo <> '' ");
			sqlString.append("GROUP BY AJAALF ,mmatmo ");
			sqlString.append("ORDER BY AJAALF ");
		}
		
		//-------------------------------------------------------	
		// Find - 1
		//--------------------------------------------------------
		if (inRequestType.equals("payCode"))
		{
			// Sent in the KEYS...
			// GRPKCODE -- Keys to come up with the PayCode
			sqlString.append("SELECT GRKCODE, GRKVTY, GRKCNVOR, GRKCROP, GRKRUN, ");
			sqlString.append("  GRKCAT, GRKPTP, GRKITEM, GRKAODT, GRKWHT, GRKWHP, ");
			// GRPJPRIC
			sqlString.append("  GRJCODE, GRJPRDT, GRJPRAM ");
			sqlString.append("FROM " + ttlib + ".GRPKCODE ");
			sqlString.append("LEFT OUTER JOIN " + ttlib + ".GRPJPRIC ");
			sqlString.append("  ON GRKCODE = GRJCODE");
			sqlString.append("   AND GRJPRDT <= " + (String) requestClass.elementAt(7) + " ");
			sqlString.append(" WHERE GRKVTY = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND GRKCNVOR = '" + (String) requestClass.elementAt(1) + "' ");
			sqlString.append("   AND GRKCROP = '" + (String) requestClass.elementAt(2) + "' ");
			sqlString.append("   AND GRKRUN = '" + (String) requestClass.elementAt(3) + "' ");
			sqlString.append("   AND GRKCAT = '" + (String) requestClass.elementAt(4) + "' ");
			sqlString.append("   AND GRKPTP = '" + (String) requestClass.elementAt(5) + "' ");
			if (!((String) requestClass.elementAt(6)).trim().equals(""))
				sqlString.append("   AND GRKITEM = '" + (String) requestClass.elementAt(6) + "' ");
   		    sqlString.append(" ORDER BY GRKCODE, GRJPRDT DESC, GRKAODT DESC ");
		}
		if (inRequestType.equals("handlingCode"))
		{
			// Sent in the Code, get the other information
			// GNPADESC -- Keys to come up with the Handling Code Inforamtion
			sqlString.append("SELECT GNAD20, GNADM5, GNADM3 ");
			sqlString.append("FROM " + ttlib + ".GNPADESC ");
			sqlString.append(" WHERE GNATYP = 'RFH' ");
			sqlString.append("   AND GNAD20 = '" + (String) requestClass.elementAt(0) + "' ");
   		    sqlString.append(" ORDER BY GNAD20 ");
		}
		if (inRequestType.equals("grapePayCode"))
		{
			sqlString.append("SELECT * ");
			sqlString.append("FROM " + ttlib + ".GRPJBPRC ");
			sqlString.append("WHERE GBJORCNV = '" + (String) requestClass.elementAt(1) + "' ");
			sqlString.append("  AND GBJBRIX = " + (String) requestClass.elementAt(2) + " ");
			sqlString.append("  AND GBJAOD <= " + (String) requestClass.elementAt(3) + " ");
			sqlString.append(" ORDER BY GBJAOD DESC ");
		}
		// 9/13/13 - TWalton Moved to BuildSQL
//		if (inRequestType.equals("findLoad"))
//		{
			// SEND IN THE Vector 
			//   Scale Ticket Number AND Scale Ticket Correction Sequence Number
			//
			//** If fields are Added to the retrieval then 
			//**   this is because you must use a NUMBER when retrieving records from files that 
			//**   you use in an SQL statement more than once..
			//**   Must also change the load information in the 
			//**      Supplier Information Fields 56-59
			//**      --  ServiceSupplier.loadFieldsSupplier
			//**      Warehouse - Facility Information 
			//**      --  ServiceWarehouse.loadFieldsWarehouse
//			sqlString.append("SELECT ");
			// GRPCSCALE  -- 42 Fields
//			sqlString.append(" G1SCALE#, G1CSEQ1,  G1RDATE,  G1LOADT, G1GRWGT, ");
///			sqlString.append(" G1LWGT,   G1NLWGT,  G1BWGT,   G1AFWGT, G1FBINS, ");
//			sqlString.append(" G1EBINS,  G1TBINS,  G1TBOXES, G1LBSBX, G1BNBLK, ");
//			sqlString.append(" G1CBOL,   G1SCSUPP, G1FRATE,  G1FRTFL, G1FSCHG, ");
//			sqlString.append(" G1FRLOC,  G1WHTIK,  G1MIMW,   G1MNLB,  G1COMP,  ");
//			sqlString.append(" G1FTCCD,  G1FLCCD,  G1FACI,   G1WHSE,  G1CCNT,  ");
//			sqlString.append(" G1USER,   G1DATE,   G1TIME,   G1VONO,  G1JRNO,");
//			sqlString.append(" G1JSNO,   G1HCOD,   G1DIM5,   G1CCTR, G1POSTF, ");
//			sqlString.append(" G1TRACK,  G1RTIME,  G1INSP, ");
//			// GRPCPO   -- 20 Fields
//			sqlString.append(" G3SCALE#, G3CSEQ1,  G3SEQ#,   G3PO#,   G3PLIN#, ");
//			sqlString.append(" G3CPO,    G3AWGT,   G3RWGT,   G3TWGT,  G3ABINS, ");
//			sqlString.append(" G3RBINS,  G3TBINS,  G3COMP,   G3FACI,  G3WHSE,  ");
//			sqlString.append(" G3SUPP,   G3USER,   G3DATE,   G3TIME,  G3POSTF, ");
//			// FGRECL
//			sqlString.append(" F2PUNO, F2PNLI, F2IMDT, ");
//			// DCPALL -- 1 field Descriptive Code - used for Long Description for From Location
//			sqlString.append("DCPT80, ");
//			// CIDMAS - M3 - Supply Master -- 4 more fields
//			sqlString.append(" A.IDSUNO as AIDSUNO, A.IDSUNM as AIDSUNM, "); // for LOAD // Field to Use for the Name
//			sqlString.append(" B.IDSUNO as BIDSUNO, B.IDSUNM as BIDSUNM, "); // for PO   // Field to Use for the Name
//			// MITWHL  - M3 - Warehouse Master -- 4 more fields
//			sqlString.append(" C.MWWHLO as CMWWHLO, C.MWWHNM as CMWWHNM, "); // for LOAD // Warehouse Name
//			sqlString.append(" D.MWWHLO as DMWWHLO, D.MWWHNM as DMWWHNM, "); // for PO   // Warehouse Name
//			// CFACIL - M3 - Facility Master -- 4 more fields
//			sqlString.append(" E.CFFACI as ECFFACI, E.CFFACN as ECFFACN, "); // for LOAD // Facility Name
//			sqlString.append(" F.CFFACI as FCFFACI, F.CFFACN as FCFFACN  "); // for PO   // Facility Name
//			// File Connections			
//			sqlString.append("FROM " + ttlib + ".GRPCSCALE ");
//			sqlString.append(" LEFT OUTER JOIN " + ttlib + ".GRPCPO ");
//			sqlString.append("   ON G1SCALE# = G3SCALE# AND G1CSEQ1 = G3CSEQ1 ");
//			sqlString.append(" LEFT OUTER JOIN " + library + ".FGRECL ");
//			sqlString.append("   ON F2CONO = 100 and F2DIVI = '100' and F2PUNO = G3PO# and F2PNLI = G3PLIN# ");
//			sqlString.append(" LEFT OUTER JOIN " + ttlib + ".DCPALL ");
//			sqlString.append("   ON DCPTYP = 'V' and DCPK00 = 'RFLOCATION' ");
//			sqlString.append("      and DCPK01 = G1FRLOC ");
//			sqlString.append(" LEFT OUTER JOIN " + library + ".CIDMAS A ");
//			sqlString.append("   ON G1SCSUPP = A.IDSUNO ");
//			sqlString.append(" LEFT OUTER JOIN " + library + ".CIDMAS B ");
//			sqlString.append("   ON G3SUPP = B.IDSUNO ");
//			sqlString.append(" LEFT OUTER JOIN " + library + ".MITWHL C ");
//			sqlString.append("   ON G1WHSE = C.MWWHLO ");
//			sqlString.append(" LEFT OUTER JOIN " + library + ".MITWHL D ");
//			sqlString.append("   ON G3WHSE = D.MWWHLO ");
//			sqlString.append(" LEFT OUTER JOIN " + library + ".CFACIL E ");
//			sqlString.append("   ON G1FACI = E.CFFACI ");
//			sqlString.append(" LEFT OUTER JOIN " + library + ".CFACIL F ");
//			sqlString.append("   ON G3FACI = F.CFFACI ");
//			sqlString.append(" WHERE G1SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
//			sqlString.append("  AND G1CSEQ1 = " + (String) requestClass.elementAt(1) + " ");
//	/		sqlString.append(" ORDER BY G1SCALE#, G1CSEQ1, G3SEQ#, G3PO# ");
//		}
		// 9/13/13 - TWalton Moved to BuildSQL
//		if (inRequestType.equals("findLot"))
//		{
//			// GRPCTICK   -- 45 Fields
//			sqlString.append("SELECT G4SCALE#, G4CSEQ1, G4SEQ#,  G4PO#,    G4PLIN#, ");
//			sqlString.append("       G4RDATE,  G4FACI,  G4WHSE,  G4LOT,    G4TSEQ#, ");
//			sqlString.append("       G4LOC,    G4HYEAR, G4SUPP,  G4SLOAD,  G4CROP,  ");
//			sqlString.append("       G4CNVOR,  G4RUNT,  G4IUSE,  G4VAR,    G4ITEM,  ");
//			sqlString.append("       G4CULLP,  G4CULLW, G4BRIX,  G4BRIXP,  G4A25B,  ");
//			sqlString.append("       G4A30B,   G4ABINC, G4ABW,   G4ABWO,   G4R25B,  ");
//			sqlString.append("       G4R30B,   G4RBINC, G4RBWO,  G4RBW,    G4CTYO,  ");
//			sqlString.append("       G4AVAR,   G4T25B,  G4T30B,  G4TBW,    G4AVGW,  ");
//			sqlString.append("       G4USER,   G4DATE,  G4TIME,  G4POSTF,   "); // should add G4RTIME
//			// GRPCPMT    -- 33 Fields 
//			sqlString.append("       G5SCALE#, G5CSEQ1, G5SEQ#,  G5PO#,    G5PLIN#, ");
//			sqlString.append("       G5LOT,    G5TSEQ#, G5YSEQ#, G5CPTP,   G5CRTP,  ");
//			sqlString.append("       G5CAT,    G525B,   G530B,   G5TBW,    G5TBWO,  ");
//			sqlString.append("       G5PCODE,  G5M3PCD, G5CMV,   G5PRICE,  G5ADJP,  ");
//			sqlString.append("       G5PRICEM, G5CROP,  G5CNVOR, G5VAR,    G5INVC,  ");
//			sqlString.append("       G5INV#,   G5USER,  G5DATE,  G5TIME,   G5VONO,  ");
//			sqlString.append("       G5JRNO,   G5JSNO,  G5POSTF, ");
//			// GRPCOTHC   -- 19 Fields
//			sqlString.append("       G6SCALE#, G6CSEQ1, G6SEQ#,  G6PO#,    G6PLIN#, ");
//			sqlString.append("       G6LOT,    G6TSEQ#, G6YSEQ#, G6OSEQ#,  G6SUPP,  ");
//			sqlString.append("       G6RATE,   G6CCODE, G6USER,  G6DATE,   G6TIME, ");
//			sqlString.append("       G6VONO,   G6JRNO,  G6JSNO,  G6POSTF, ");
//			// CIDMAS  -- Service Supplier will need to be changed for the Load fields if additional fields are added 
//			sqlString.append("       A.IDSUNO as AIDSUNO, A.IDSUNM as AIDSUNM, ");  // for TICKET - for first name
//			sqlString.append("       B.IDSUNO as BIDSUNO, B.IDSUNM as BIDSUNM, ");  // for Other Charge - - for the second name
//			// FPLOPT  -- 1 Field
//			sqlString.append("       FUTX40, "); // Number 87 - Accounting Option Long Description
//			// MITMAS  -- 3 Fields
//			sqlString.append("       MMITDS, MMATMO, MMEVGR, ");
////			 MITWHL    -- 2 Fields
//			sqlString.append("       MWWHLO, MWWHNM, ");
//			// CFACIL   -- 2 fields
//			sqlString.append("       CFFACI, CFFACN, ");
//			// GRPCSCALE -- 1 Field
//			sqlString.append("       G1CCNT, ");// need to know the correction count for editing purposes
//			// GRPSCONTR -- 1 Field
//			sqlString.append("       GRSCVR1 ");
//			
//			sqlString.append("FROM " + ttlib + ".GRPCTICK ");
//			sqlString.append(" LEFT OUTER JOIN " + ttlib + ".GRPCPMT ");
//			sqlString.append("   ON  G4SCALE# = G5SCALE# AND G4CSEQ1 = G5CSEQ1 ");
///			sqlString.append("   AND G4SEQ#   = G5SEQ#   AND G4PO#   = G5PO# ");
//			sqlString.append("   AND G4PLIN#  = G5PLIN#  AND G4LOT   = G5LOT ");
//			sqlString.append("   AND G4TSEQ#  = G5TSEQ# ");
//			sqlString.append(" LEFT OUTER JOIN " + ttlib + ".GRPCOTHC ");
//			sqlString.append("   ON  G5SCALE# = G6SCALE# AND G5CSEQ1 = G6CSEQ1 ");
//			sqlString.append("   AND G5SEQ#   = G6SEQ#   AND G5PO#   = G6PO# ");
//			sqlString.append("   AND G5PLIN#  = G6PLIN#  AND G5LOT   = G6LOT ");
//			sqlString.append("   AND G5TSEQ#  = G6TSEQ#  AND G5YSEQ# = G6YSEQ# ");
//			sqlString.append(" LEFT OUTER JOIN " + library + ".CIDMAS A ");
//			sqlString.append("   ON G4SUPP = A.IDSUNO ");
//			sqlString.append(" LEFT OUTER JOIN " + library + ".CIDMAS B ");
//			sqlString.append("   ON G6SUPP = B.IDSUNO ");
//			sqlString.append(" LEFT OUTER JOIN " + library + ".FPLOPT ");
//			sqlString.append("   ON FUCONO = 100 AND FUDIVI = '100' ");
//			sqlString.append("   AND FUPLOP = G6CCODE ");
//			sqlString.append(" LEFT OUTER JOIN " + library + ".MITMAS ");
//			sqlString.append("   ON MMITNO = G4ITEM ");
//			sqlString.append(" LEFT OUTER JOIN " + library + ".MITWHL ");
//			sqlString.append("   ON MWWHLO = G4WHSE ");
//			sqlString.append(" LEFT OUTER JOIN " + library + ".CFACIL ");
//			sqlString.append("   ON CFFACI = G4FACI ");
//			sqlString.append(" LEFT OUTER JOIN " + ttlib + ".GRPCSCALE ");
//			sqlString.append("   ON G4SCALE# = G1SCALE# AND G4CSEQ1 = G1CSEQ1 ");
//			sqlString.append(" LEFT OUTER JOIN " + ttlib + ".GRPSCONTR ");
//			sqlString.append("   ON GRSCYR = G4HYEAR AND GRSCUSG = 'G' ");
//			sqlString.append("   AND GRSCDSC = 'Grape Due Date' ");
//			sqlString.append(" WHERE G4SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
//			sqlString.append("   AND G4CSEQ1 = " + (String) requestClass.elementAt(1) + " ");
///			sqlString.append("   AND G4SEQ# = " + (String) requestClass.elementAt(2) + " ");
//			sqlString.append("   AND G4PO# = " + (String) requestClass.elementAt(3) + " ");
//			sqlString.append("   AND G4TSEQ# = " + (String) requestClass.elementAt(4) + " ");
//			sqlString.append("   AND G4LOT = '" + (String) requestClass.elementAt(5) + "' ");
//				
//			sqlString.append(" ORDER BY G4SCALE#, G4SEQ#, G4TSEQ#, G4LOT, G5YSEQ#, G6OSEQ# ");
//		}
		if (inRequestType.equals("findPOQuantities"))
		{
			// Retrieve information Summarized from Lot Number to be put into the PO
			sqlString.append(" SELECT (SUM(G4A25B) + SUM(G4A30B)), "); // Field 1 = Total Accepted Bins for Scale Ticket and Sequence
			sqlString.append("        (SUM(G4R25B) + SUM(G4R30B)), "); // Field 2 = Total Rejected Bins for Scale Ticket and Sequence
			sqlString.append("        (SUM(G4T25B) + SUM(G4T30B)), "); // Field 3 = Total Bins for Scale Ticket and Sequence
			sqlString.append("        SUM(G4ABW), "); // Field 4 = Weight of Accepted Bins
			sqlString.append("        SUM(G4RBW), "); // Field 5 = Weight of Rejected Bins
			sqlString.append("        SUM(G4TBW),  "); // Field 6 = Weight of Total Bins
			sqlString.append("        G4SCALE#, G4CSEQ1, G4SEQ# ");
			sqlString.append(" FROM " + ttlib + ".GRPCTICK ");
			sqlString.append(" WHERE G4SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("  AND G4CSEQ1 = " + (String) requestClass.elementAt(1) + " ");
			sqlString.append(" GROUP BY G4SCALE#, G4CSEQ1, G4SEQ# ");
			sqlString.append(" ORDER BY G4SCALE#, G4CSEQ1, G4SEQ# ");
		}
		if (inRequestType.equals("findCorrectionSequence"))
		{
			InqRawFruit scaleInfo = (InqRawFruit) requestClass.elementAt(0);
			sqlString.append("SELECT G1SCALE#, G1CSEQ1 ");
			sqlString.append("FROM " + ttlib + ".GRPCSCALE ");
			sqlString.append(" WHERE G1SCALE# = '" + scaleInfo.getScaleTicket().trim() + "' ");
			sqlString.append(" ORDER BY G1SCALE#, G1CSEQ1 DESC ");
		}
		if (inRequestType.equals("findLotAcceptedWeight"))
		{
			sqlString.append("SELECT G4SCALE#, G4CSEQ1, G4SEQ#, G4LOT, G4ABW ");
			sqlString.append("FROM " + ttlib + ".GRPCTICK ");
			sqlString.append(" WHERE G4SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G4CSEQ1 = " + (String) requestClass.elementAt(1) + " ");
			sqlString.append("   AND G4SEQ# = " + (String) requestClass.elementAt(2) + " ");
			sqlString.append("   AND G4LOT = '" + (String) requestClass.elementAt(3) + "' ");
			sqlString.append(" ORDER BY G4SCALE#, G4CSEQ1 ");
		}
		
		if (inRequestType.equals("scaleTicketExists"))
		{
			sqlString.append("SELECT G1SCALE# ");
			sqlString.append("FROM " + ttlib + ".GRPCSCALE ");
			sqlString.append("WHERE G1SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
		}
		
		if (inRequestType.equals("scaleTicketAvailable"))
		{
			sqlString.append("SELECT G1SCALE# ");
			sqlString.append("FROM " + ttlib + ".GRPCSCALE ");
			sqlString.append("WHERE G1SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("and G1USER = 'QUICKENTRY' ");
			sqlString.append("and not exists (select g1scale# from dbprd.grpcscale ");
			sqlString.append("where g1scale# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("and g1cseq1 <> 0) ");
			sqlString.append("and not exists (select g3scale# from dbprd.grpcpo ");
			sqlString.append("where g3scale# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("and g3cseq1 <> 0) ");
			sqlString.append("and not exists (select g4ctyo from dbprd.grpctick ");
			sqlString.append("where g4scale# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("and g4ctyo <> '') ");
		}
		
//		-------------------------------------------------------	
		// List Many
		//--------------------------------------------------------
		if (inRequestType.equals("listBins"))
		{
			sqlString.append("SELECT * ");
			sqlString.append("FROM " + ttlib + ".GRPCBINS ");
			sqlString.append(" WHERE G2SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("  AND G2CSEQ1 = " + (String) requestClass.elementAt(1) + " ");			
			sqlString.append(" ORDER BY G2SCALE#, G2BINTYP ");
		}
		if (inRequestType.equals("listLots"))
		{
			// GRPCTICK
			sqlString.append("SELECT G4TSEQ#, G4LOT ");
			sqlString.append("FROM " + ttlib + ".GRPCTICK ");
			sqlString.append(" WHERE G4SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("  AND G4CSEQ1 = " + (String) requestClass.elementAt(1) + " ");			
			sqlString.append("   AND G4SEQ# = " + (String) requestClass.elementAt(2) + " ");
			sqlString.append("   AND G4PO# = " + (String) requestClass.elementAt(3) + " ");
			sqlString.append(" ORDER BY G4TSEQ#, G4LOT ");
		}
		if (inRequestType.equals("listLotCopy"))
		{
			sqlString.append("SELECT G4SCALE#, G4CSEQ1, G4LOT ");
			sqlString.append(" FROM " + ttlib + ".GRPCTICK ");
			sqlString.append(" WHERE G4SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("  AND G4CSEQ1 = " + (String) requestClass.elementAt(1) + " ");		
			sqlString.append(" ORDER BY G4LOT ");
		}
		if (inRequestType.equals("listCalcTickWeight"))
		{
			// GRPCSCALE
			sqlString.append("SELECT G1SCALE#, G1CSEQ1, G1AFWGT, G1TBOXES, ");
			// GRPCTICK
			sqlString.append("   G4SCALE#, G4CSEQ1, G4SEQ#, G4LOT, G4TSEQ#, ");
			sqlString.append("   G4A25B, G4A30B, G4ABW, G4ABWO, ");
			sqlString.append("   G4R25B, G4R30B, G4RBW, G4RBWO ");
			sqlString.append("FROM " + ttlib + ".GRPCSCALE ");
			sqlString.append("  LEFT OUTER JOIN " + ttlib + ".GRPCTICK ");
			sqlString.append("    ON G1SCALE# = G4SCALE# ");
			sqlString.append("    AND G1CSEQ1 = G4CSEQ1 ");
			sqlString.append(" WHERE G1SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("  AND G1CSEQ1 = " + (String) requestClass.elementAt(1) + " ");			
			sqlString.append(" ORDER BY G1SCALE#, G1CSEQ1, G4SEQ#, G4TSEQ#, G4LOT ");
		}
		if (inRequestType.equals("listCalcPaymentWeight"))
		{
			// GRPCPMT
			sqlString.append("SELECT G5YSEQ#, G525B, G530B, G4ABW, G5TBW, G5TBWO ");
			sqlString.append("FROM " + ttlib + ".GRPCPMT ");
			sqlString.append(" LEFT OUTER JOIN " + ttlib + ".GRPCTICK ");
			sqlString.append("   ON G4SCALE# = G5SCALE# ");
			sqlString.append("  AND G4CSEQ1 = G5CSEQ1 ");
			sqlString.append("  AND G4SEQ# = G5SEQ# ");
			sqlString.append("  AND G4TSEQ# = G5TSEQ# ");
			sqlString.append("  AND G4LOT = G5LOT ");
			sqlString.append(" WHERE G5SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("  AND G5CSEQ1 = " + (String) requestClass.elementAt(1) + " ");			
			sqlString.append("   AND G5SEQ# = " + (String) requestClass.elementAt(2) + " ");
			sqlString.append("   AND G5TSEQ# = " + (String) requestClass.elementAt(3) + " ");
			sqlString.append("   AND G5LOT  = '" + (String) requestClass.elementAt(4) + "' ");
			sqlString.append(" ORDER BY G5YSEQ# ");
		}
		if (inRequestType.equals("listLotsUpdatePO"))
		{ // Used to go retrieve the PO Number, for Update of Files
		  //  Will only display if PO# = 0	
		    sqlString.append("SELECT G4SCALE#, G4CSEQ1, G4SEQ#, G4PO#, G4PLIN#, ");
		    sqlString.append("       G4ITEM, G4LOT ");
		    sqlString.append("FROM " + ttlib + ".GRPCTICK ");
		    sqlString.append(" WHERE G4SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G4CSEQ1 = " + (String) requestClass.elementAt(1) + " ");	
			sqlString.append("   AND G4PO# = 0 ");
			sqlString.append(" ORDER BY G4SEQ#, G4ITEM, G4LOT ");
		}
		if (inRequestType.equals("listLoads"))
		{ // Used Criteria sent in the InqRawFruit object to build SQL Statement
			InqRawFruit irf = (InqRawFruit) requestClass.elementAt(0);
			// GRPCSCALE - Load Master File
		    sqlString.append("SELECT G1SCALE#, G1CSEQ1, G1RDATE, G1NLWGT,  G1AFWGT, ");
		    sqlString.append("       G1FBINS,  G1BNBLK, G1CBOL,  G1SCSUPP, G1FRLOC, ");
		    sqlString.append("       G1WHTIK,  G1FACI,  G1WHSE,  G1CCNT, ");
		 // DCPALL -- 1 field Descriptive Code - used for Long Description for From Location
			sqlString.append("DCPT80, ");
		    // CIDMAS - M3 - Supplier Master
		    sqlString.append("       IDSUNO, IDSUNM, ");
		    // MITWHL    -- Warehouse Master
			sqlString.append("       MWWHLO, MWWHNM, MWFACI, ");
			// CFACIL   -- Facility Master
			sqlString.append("       CFFACI, CFFACN ");
			sqlString.append("FROM " + ttlib + ".GRPCSCALE ");
			sqlString.append(" LEFT OUTER JOIN " + ttlib + ".DCPALL ");
			sqlString.append("   ON DCPTYP = 'V' and DCPK00 = 'RFLOCATION' ");
			sqlString.append("      and DCPK01 = G1FRLOC ");
			sqlString.append(" LEFT OUTER JOIN " + library + ".CIDMAS ");
			sqlString.append("   ON G1SCSUPP = IDSUNO ");  
			sqlString.append(" LEFT OUTER JOIN " + library + ".MITWHL ");
			sqlString.append("   ON MWWHLO = G1WHSE ");
			sqlString.append(" LEFT OUTER JOIN " + library + ".CFACIL ");
			sqlString.append("   ON CFFACI = MWFACI ");
			
		    // Where Section if applicable
		    StringBuffer buildWhereSection = new StringBuffer();
		    if (!irf.getInqScaleTicketFrom().trim().equals(""))
		       buildWhereSection.append(" UPPER(G1SCALE#) >= UPPER('" + irf.getInqScaleTicketFrom() + "') ");
		    if (!irf.getInqScaleTicketTo().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" UPPER(G1SCALE#) <= UPPER('" + irf.getInqScaleTicketTo() + "') ");
		    }
		    if (!irf.getInqReceivingDateFromyyyymmdd().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" G1RDATE >= " + irf.getInqReceivingDateFromyyyymmdd() + " ");
		    }	
		    if (!irf.getInqReceivingDateToyyyymmdd().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" G1RDATE <= " + irf.getInqReceivingDateToyyyymmdd() + " ");
		    }		
		    if (!irf.getInqWarehouseTicketFrom().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" UPPER(G1WHTIK) >= UPPER('" + irf.getInqWarehouseTicketFrom() + "') ");
		    }	
		    if (!irf.getInqWarehouseTicketTo().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" UPPER(G1WHTIK) <= UPPER('" + irf.getInqWarehouseTicketTo() + "') ");
		    }	 	
		    if (!irf.getInqCarrierFrom().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" G1SCSUPP >= '" + irf.getInqCarrierFrom() + "' ");
		    }	
		    if (!irf.getInqCarrierTo().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" G1SCSUPP <= '" + irf.getInqCarrierTo() + "' ");
		    }	 	 
		    if (!irf.getInqCarrierBOLFrom().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" UPPER(G1CBOL) >= UPPER('" + irf.getInqCarrierBOLFrom() + "') ");
		    }	
		    if (!irf.getInqCarrierBOLTo().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" UPPER(G1CBOL) <= UPPER('" + irf.getInqCarrierBOLTo() + "') ");
		    }	 	 
		    if (!irf.getInqFacility().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" G1FACI = '" + irf.getInqFacility() + "' ");
		    }
		    if (!irf.getInqWarehouse().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" G1WHSE = '" + irf.getInqWarehouse() + "' ");
		    }	 	 
		    if (!irf.getInqFromLocation().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" UPPER(G1FRLOC) = UPPER('" + irf.getInqFromLocation() + "') ");
		    }	
		    if (!irf.getInqBinBulk().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	if (irf.getInqBinBulk().trim().equals("Bulk"))
		    	  buildWhereSection.append(" G1BNBLK = 'Y' ");
		    	else
		    	  buildWhereSection.append(" G1BNBLK = '' ");
		    }	
		    // 1/21/13 TWalton - Added the Scheduled Load Number
		    if (!irf.getInqScheduledLoadNumber().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" G1TRACK = '" + irf.getInqScheduledLoadNumber() + "' ");
		    }
		    if (!irf.getInqLot().trim().equals("") || 
		    	!irf.getInqSupplier().trim().equals(""))
			{
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
				buildWhereSection.append(" EXISTS (SELECT * FROM " + ttlib + ".GRPCTICK ");
				buildWhereSection.append("   WHERE G1SCALE# = G4SCALE# ");
				buildWhereSection.append("  AND G1CSEQ1 = G4CSEQ1 ");
				if (!irf.getInqLot().trim().equals(""))
				   buildWhereSection.append("  AND G4LOT = '" + irf.getInqLot() + "' ");
				if (!irf.getInqSupplier().trim().equals(""))
				   buildWhereSection.append("  AND G4SUPP = '" + irf.getInqSupplier() + "' ");
				buildWhereSection.append(") ");
			}
		    if (!irf.getInqWriteUpNumber().trim().equals(""))
		    {
		    	if (!buildWhereSection.toString().trim().equals(""))
		    		buildWhereSection.append(" AND ");
		    	buildWhereSection.append(" (EXISTS (SELECT * FROM " + ttlib + ".GRPCTICK ");
				buildWhereSection.append("   WHERE G1SCALE# = G4SCALE# ");
				buildWhereSection.append("  AND G1CSEQ1 = G4CSEQ1 ");
			    buildWhereSection.append("  AND G4WUP = '" + irf.getInqWriteUpNumber().trim() + "') ");
			    buildWhereSection.append(" OR EXISTS (SELECT * FROM " + ttlib + ".GRPCPMT ");
				buildWhereSection.append("   WHERE G1SCALE# = G5SCALE# ");
				buildWhereSection.append("  AND G1CSEQ1 = G5CSEQ1 ");
			    buildWhereSection.append("  AND G5WUP = '" + irf.getInqWriteUpNumber().trim() + "') ");
			    buildWhereSection.append(") ");
		    }
	        if (!buildWhereSection.toString().trim().equals(""))
		    {
		       sqlString.append(" WHERE ");
		       sqlString.append(buildWhereSection.toString());
		    }
			// Create Order By Based on the Order by Selection in the InqRawFruit Object
			if (irf.getOrderBy().trim().equals("scaleTicketNumber"))
			   sqlString.append(" ORDER BY G1SCALE# " + irf.getOrderStyle().trim() + ", G1CSEQ1, G1RDATE ");
			if (irf.getOrderBy().trim().equals("scaleTicketCorrectionSequenceNumber"))
			   sqlString.append(" ORDER BY G1CSEQ1 " + irf.getOrderStyle().trim() + ", G1SCALE#, G1RDATE ");
			if (irf.getOrderBy().trim().equals("receivingDate"))
			   sqlString.append(" ORDER BY G1RDATE " + irf.getOrderStyle().trim() + ", G1SCALE#, G1CSEQ1 ");
			if (irf.getOrderBy().trim().equals("loadNetWeight"))
			   sqlString.append(" ORDER BY G1NLWGT " + irf.getOrderStyle().trim() + ", G1SCALE#, G1CSEQ1, G1RDATE ");
			if (irf.getOrderBy().trim().equals("loadFruitNetWeight"))
			   sqlString.append(" ORDER BY G1AFWGT " + irf.getOrderStyle().trim() + ", G1SCALE#, G1CSEQ1, G1RDATE ");
			if (irf.getOrderBy().trim().equals("loadFullBins"))
			   sqlString.append(" ORDER BY G1BNBLK, G1FBINS " + irf.getOrderStyle().trim() + ", G1SCALE#, G1CSEQ1, G1RDATE ");
			if (irf.getOrderBy().trim().equals("carrierBOL"))
			   sqlString.append(" ORDER BY G1CBOL " + irf.getOrderStyle().trim() + ", G1SCALE#, G1CSEQ1, G1RDATE ");
			if (irf.getOrderBy().trim().equals("carrier"))
			   sqlString.append(" ORDER BY G1SCSUPP " + irf.getOrderStyle().trim() + ", G1SCALE#, G1CSEQ1, G1RDATE ");
			if (irf.getOrderBy().trim().equals("facility"))
			   sqlString.append(" ORDER BY G1FACI " + irf.getOrderStyle().trim() + ", G1SCALE#, G1CSEQ1, G1RDATE ");
			if (irf.getOrderBy().trim().equals("warehouse"))
			   sqlString.append(" ORDER BY G1WHSE " + irf.getOrderStyle().trim() + ", G1SCALE#, G1CSEQ1, G1RDATE ");
			if (irf.getOrderBy().trim().equals("fromLocation"))
			   sqlString.append(" ORDER BY G1FRLOC " + irf.getOrderStyle().trim() + ", G1SCALE#, G1CSEQ1, G1RDATE ");
			if (irf.getOrderBy().trim().equals("whseTicket"))
			   sqlString.append(" ORDER BY G1WHTIK " + irf.getOrderStyle().trim() + ", G1SCALE#, G1CSEQ1, G1RDATE ");
			
		}
		//-----------------------------------------------------------------------------
		//  INSERT -- Add Records
		if (inRequestType.equals("addScale"))
		{ // add a basic record to the Scale File -- 
			// cast the incoming parameter class.
			UpdRawFruitLoad load = (UpdRawFruitLoad) requestClass.elementAt(0);
			
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlib + ".GRPCSCALE ");
			sqlString.append("(G1SCALE#, G1CSEQ1, G1RDATE, G1FTCCD, G1FLCCD, ");
			sqlString.append(" G1MNLB,   G1USER,  G1DATE,  G1TIME,  G1HCOD, ");
			sqlString.append(" G1DIM5,   G1CCTR,  G1POSTF, G1TRACK, G1TRLINE, ");
			sqlString.append(" G1RTIME,  G1INSP) ");
			sqlString.append(" VALUES( ");
			sqlString.append("'" + load.getScaleTicket().trim() + "',"); // Scale Ticket
			sqlString.append(load.getScaleTicketCorrectionSequence() + ", "); // Scale Ticket Correction Sequence Number
			
			if (!load.getQuickEntry().trim().equals("") && !load.getReceivingDate().trim().equals(""))
			{
				DateTime dt1 = UtilityDateTime.getDateFromMMddyyWithSlash(load.getReceivingDate());
				sqlString.append(dt1.getDateFormatyyyyMMdd() + ",");
			}
			else
				sqlString.append(dt.getDateFormatyyyyMMdd() + ","); // Receiving Date
			
			sqlString.append("45,"); // Default in Code 45
			sqlString.append("46,"); // Default in Code 46
			sqlString.append("55000, "); // Default in Value 55000
			
			if (load.getQuickEntry().trim().equals(""))
				sqlString.append("'" + load.getUpdateUser().trim() + "',"); // User
			else
				sqlString.append("'QUICKENTRY',"); // User
				
			sqlString.append(dt.getDateFormatyyyyMMdd() + ","); // Date
			sqlString.append(dt.getTimeFormathhmmss() + ","); // Time
			sqlString.append("'" + load.getHandlingCodeLong().trim() + "',"); // raw product Handling Code
			sqlString.append("'" + load.getHandlingCode().trim() + "',");
			sqlString.append("'" + load.getCostCenter().trim()  + "',");
			sqlString.append("'0000000000', "); // Posting Flags
			if (load.getScheduledLoadNumber().trim().equals("")) // ScheduleTracking Number
				sqlString.append("0, "); 
			else
				sqlString.append(load.getScheduledLoadNumber() + ", ");
			sqlString.append("0, "); // schedule Tracking Number for the Line
			sqlString.append(dt.getTimeFormathhmmss() + ", "); // Receiving Time
			sqlString.append("'" + load.getInspectedBy().trim() + "' "); //  Inspected By
			sqlString.append(") ");
		}
		
		
		if (inRequestType.equals("addLoadQuickEntry"))
		{ 
			UpdRawFruitLoad load = (UpdRawFruitLoad) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			
			String bulk = "";
			
			if (load.getBulkLoad() != null && load.getBulkLoad().equals("on"))
				bulk = "Y";
			
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlib + ".GRPCSCALE ");
			sqlString.append("(G1SCALE#, G1CSEQ1, G1RDATE, G1FBINS,  G1BNBLK, ");
			sqlString.append(" G1FTCCD, G1FLCCD,  G1FACI,   G1WHSE, ");
			sqlString.append(" G1MNLB,   G1USER,  G1DATE,  G1TIME,  G1HCOD, ");
			sqlString.append(" G1DIM5,   G1CCTR,  G1POSTF, G1TRACK, G1TRLINE, ");
			sqlString.append(" G1RTIME,  G1INSP) ");
			sqlString.append(" VALUES( ");
			sqlString.append("'" + load.getScaleTicket().trim() + "',"); // Scale Ticket
			sqlString.append(load.getScaleTicketCorrectionSequence() + ", "); // Scale Ticket Correction Sequence Number
			
			DateTime dt1 = UtilityDateTime.getDateFromMMddyyWithSlash(load.getReceivingDate());
			sqlString.append(dt1.getDateFormatyyyyMMdd() + ",");
			
			if (load.getLoadFullBins().trim().equals(""))
				sqlString.append("0,");
			else
				sqlString.append(load.getLoadFullBins().trim() + ",");
			
			sqlString.append("'" + bulk + "',");
			sqlString.append("45,"); // Default in Code 45
			sqlString.append("46,"); // Default in Code 46
			
			if (load.getWarehouse().trim().equals(""))
			{
				String facility = "";
				
				if (!load.getWarehouse().trim().equals(""))
				{
				   try
				   {
					  Vector sendValue = new Vector();
					  sendValue.addElement(load.getWarehouse());
					  Warehouse whse = ServiceWarehouse.findWarehouse("PRD", sendValue);
					  
			          if (!whse.getFacility().trim().equals(""))
					    facility = whse.getFacility();
				   }
				   catch(Exception e)
				   {}
				}
				
				sqlString.append("'" + facility + "',"); // Facility
				sqlString.append("'" + load.getWarehouse() + "',"); // Warehouse
			} else {
				sqlString.append("'" + load.getFacility() + "',"); // Facility
				sqlString.append("'" + load.getWarehouse() + "',"); // Warehouse
			}
			
			sqlString.append("55000, "); // Default in Value 55000
			
			sqlString.append("'QUICKENTRY',"); // User
				
			sqlString.append(dt.getDateFormatyyyyMMdd() + ","); // Date
			sqlString.append(dt.getTimeFormathhmmss() + ","); // Time
			sqlString.append("'" + load.getHandlingCodeLong().trim() + "',"); // raw product Handling Code
			sqlString.append("'" + load.getHandlingCode().trim() + "',");
			sqlString.append("'" + load.getCostCenter().trim()  + "',");
			sqlString.append("'0000000000', "); // Posting Flags
			if (load.getScheduledLoadNumber().trim().equals("")) // ScheduleTracking Number
				sqlString.append("0, "); 
			else
				sqlString.append(load.getScheduledLoadNumber() + ", ");
			sqlString.append("0, "); // schedule Tracking Number for the Line
			sqlString.append(dt.getTimeFormathhmmss() + ", "); // Receiving Time
			sqlString.append("'" + load.getInspectedBy().trim() + "' "); //  Inspected By
			sqlString.append(") ");
		}
		
		
		if (inRequestType.equals("addPO"))
		{ // add a basic record to the PO -- 
			// cast the incoming parameter class.
			UpdRawFruitLoad load = (UpdRawFruitLoad) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
		
			sqlString.append("INSERT INTO " + ttlib + ".GRPCPO ");
			sqlString.append("(G3SCALE#, G3CSEQ1, G3SEQ#, G3FACI, G3WHSE, ");
			sqlString.append(" G3USER,   G3DATE,  G3TIME, G3POSTF) ");
			sqlString.append(" VALUES( ");
			sqlString.append("'" + load.getScaleTicket().trim() + "',"); // Scale Ticket
			sqlString.append(load.getScaleTicketCorrectionSequence() + ","); // Scale Ticket Correction Sequence Number
			sqlString.append((String) requestClass.elementAt(2) + ","); // Sequence Number
			sqlString.append("'" + load.getFacility() + "',"); // Facility
			sqlString.append("'" + load.getWarehouse() + "',"); // Warehouse
			// 3/2/09 tw -- no longer want the supplier to DEFAULT in
			//sqlString.append("'" + load.getCarrierSupplier() + "',"); // Supplier
			sqlString.append("'" + load.getUpdateUser().trim() + "',"); // User
			sqlString.append(dt.getDateFormatyyyyMMdd() + ","); // Date
			sqlString.append(dt.getTimeFormathhmmss() + ","); // Time
			sqlString.append("'0000000000' "); // Posting Flags
			sqlString.append(") ");
		}
		
		if (inRequestType.equals("addQuickEntryPO"))
		{
			UpdRawFruitLoad load = (UpdRawFruitLoad) requestClass.elementAt(0);
			DateTime dt = (DateTime) requestClass.elementAt(1);
			String poSeq = (String) requestClass.elementAt(2);
			UpdRawFruitQuickEntry qe = (UpdRawFruitQuickEntry) requestClass.elementAt(3);
			
			sqlString.append("INSERT INTO " + ttlib + ".GRPCPO ");
			sqlString.append("(G3SCALE#, G3CSEQ1, G3SEQ#, G3FACI, G3WHSE, ");
			sqlString.append(" G3USER,   G3DATE,  G3TIME, G3SUPP, G3POSTF) ");
			sqlString.append(" VALUES( ");
			sqlString.append("'" + load.getScaleTicket().trim() + "',"); // Scale Ticket
			sqlString.append(load.getScaleTicketCorrectionSequence() + ","); // Scale Ticket Correction Sequence Number
			sqlString.append((String) requestClass.elementAt(2) + ","); // Sequence Number
			sqlString.append("'" + load.getFacility() + "',"); // Facility
			
			if (qe.getWarehouse().trim().equals(""))
				sqlString.append("'" + load.getWarehouse() + "',"); // Warehouse
			else
				sqlString.append("'" + qe.getWarehouse() + "',"); // Warehouse
			
			sqlString.append("'QUICKENTRY',"); // User
			sqlString.append(dt.getDateFormatyyyyMMdd() + ","); // Date
			sqlString.append(dt.getTimeFormathhmmss() + ","); // Time
			sqlString.append("'" + qe.getSupplier().trim() + "', ");
			sqlString.append("'0000000000' "); // Posting Flags
			sqlString.append(") ");
		}
		
		if (inRequestType.equals("addBins"))
		{  
			// cast the incoming parameter class.
			UpdRawFruitBins bin = (UpdRawFruitBins) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlib + ".GRPCBINS ");
			sqlString.append("(G2SCALE#, G2CSEQ1, G2BINTYP, G2BINCNT, ");
			sqlString.append(" G2USER,   G2DATE,  G2TIME,   G2POSTF) ");
			sqlString.append(" VALUES( ");
			sqlString.append("'" + bin.getScaleTicket().trim() + "',"); // Scale Ticket
			sqlString.append(bin.getScaleTicketCorrectionSequence() + ","); // Scale Ticket Correction Sequence #
			sqlString.append("'" + bin.getBinType().trim().toUpperCase() + "',"); // Bin Type
			sqlString.append(bin.getNumberOfBins() + ","); // Number of Bins
			sqlString.append("'" + bin.getUpdateUser().trim() + "',"); // User
			sqlString.append(dt.getDateFormatyyyyMMdd() + ","); // Date
			sqlString.append(dt.getTimeFormathhmmss() + ", "); // Time
			sqlString.append("'0000000000' "); // Posting Flags 
			sqlString.append(") ");
		}
		
		if (inRequestType.equals("addQuickEntryLot"))
		{ 
			UpdRawFruitLoad urf = (UpdRawFruitLoad) requestClass.elementAt(0);
			DateTime dt = (DateTime) requestClass.elementAt(1);
			String poSeq = (String) requestClass.elementAt(2);
			UpdRawFruitQuickEntry qe = (UpdRawFruitQuickEntry) requestClass.elementAt(3);
			
			//default current date as receiving date - use Bean Receiving Date if available.
			DateTime dt1 = (DateTime) requestClass.elementAt(1);
			
			//use the current fiscal year minus 1 for harvest year default.
			int hyear = Integer.parseInt(dt.getM3FiscalYear());
			hyear = hyear - 1;

			if(!urf.getReceivingDate().trim().equals(""))
			{
				dt1 = UtilityDateTime.getDateFromMMddyyWithSlash(urf.getReceivingDate());
			}
				
			sqlString.append("INSERT INTO " + ttlib + ".GRPCTICK ");
			sqlString.append("(G4SCALE#, G4SEQ#, G4RDATE, G4FACI, G4WHSE, ");
			sqlString.append("G4LOT, G4HYEAR, G4SUPP, G4CROP, G4RUNT, G4VAR, G4ITEM, G4A25B, ");
			sqlString.append("G4USER, G4DATE, G4TIME) ");
			sqlString.append(" VALUES( ");
			sqlString.append("'" + urf.getScaleTicket().trim() + "',"); // Scale Ticket
			sqlString.append(poSeq + ","); // Scale Sequence Number (Scale Ticket and Sequence Equals PO
			sqlString.append(dt1.getDateFormatyyyyMMdd() + ","); // Receiving Date
			
			if (!qe.getWarehouse().trim().equals(""))
			{
				String facility = "";
				if (!qe.getWarehouse().trim().equals(""))
				{
				   try
				   {
					  Vector sendValue = new Vector();
					  sendValue.addElement(qe.getWarehouse());
					  Warehouse whse = ServiceWarehouse.findWarehouse("PRD", sendValue);
					  
			          if (!whse.getFacility().trim().equals(""))
					    facility = whse.getFacility();
				   }
				   catch(Exception e)
				   {}
				}
				
				sqlString.append("'" + facility + "',"); // Facility
				sqlString.append("'" + qe.getWarehouse() + "',"); // Warehouse
			} else {
				sqlString.append("'" + urf.getFacility() + "',"); // Facility
				sqlString.append("'" + urf.getWarehouse() + "',"); // Warehouse
			}
			
			sqlString.append("'" + qe.getLotNumber() + "',"); // Lot Number
			sqlString.append(hyear + ", "); //Harvest Year - defaults fiscal year
			sqlString.append("'" + qe.getSupplier() + "',"); // Supplier
			sqlString.append("'" + qe.getItemCrop().trim() + "', ");
			sqlString.append("'" + qe.getRunType() + "',"); // Run Type Machine/Orchard
			sqlString.append("'" + qe.getVariety() + "',"); // variety
			sqlString.append("'" + qe.getItemNumber() + "',"); // Item
			sqlString.append(qe.getNumberOfBins() + ","); // Accepted 25 Box Bins
			sqlString.append("'QUICKENTRY',"); // User
			sqlString.append(dt.getDateFormatyyyyMMdd() + ","); // Date
			sqlString.append(dt.getTimeFormathhmmss() + ") "); // Time
		}
		
		if (inRequestType.equals("addLot"))
		{  
			// cast the incoming parameter class.
			UpdRawFruitLot lot = (UpdRawFruitLot) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			DateTime dt1 = UtilityDateTime.getDateFromMMddyyWithSlash(lot.getReceivingDate());
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlib + ".GRPCTICK ");
			sqlString.append("(G4SCALE#, G4CSEQ1, G4SEQ#, G4PO#,   G4PLIN#, ");
			sqlString.append(" G4RDATE,  G4FACI,  G4WHSE, G4LOT,   G4TSEQ#, ");
			sqlString.append(" G4LOC,    G4HYEAR, G4SUPP, G4SLOAD, G4CROP, ");
			sqlString.append(" G4CNVOR,  G4RUNT,  G4IUSE, G4VAR,   G4ITEM, ");
			sqlString.append(" G4CULLP,  G4CULLW, G4BRIX, G4BRIXP, G4A25B, ");
			sqlString.append(" G4A30B,   G4ABINC, G4ABW,  G4ABWO,  G4R25B, ");
			sqlString.append(" G4R30B,   G4RBINC, G4RBWO, G4RBW,   G4CTYO, ");
			sqlString.append(" G4AVAR,   G4T25B,  G4T30B, G4TBW,   G4AVGW, ");
			sqlString.append(" G4USER,   G4DATE,  G4TIME, G4POSTF ) "); //G4RTIME
			sqlString.append(" VALUES( ");
			sqlString.append("'" + lot.getScaleTicket().trim() + "',"); // Scale Ticket
			sqlString.append(lot.getScaleTicketCorrectionSequence().trim() + ", "); // Scale Ticket Correction Sequence
			sqlString.append(lot.getScaleSequence() + ","); // Scale Sequence Number (Scale Ticket and Sequence Equals PO
			sqlString.append(lot.getPoNumber() + ", "); // PO Number
			sqlString.append("0, "); // PO Line Number
			sqlString.append(dt1.getDateFormatyyyyMMdd() + ","); // Receiving Date
			sqlString.append("'" + lot.getFacility() + "',"); // Facility
			sqlString.append("'" + lot.getWarehouse() + "',"); // Warehouse
			sqlString.append("'" + lot.getLotNumber() + "',"); // Lot Number
			sqlString.append(lot.getLotSequenceNumber() + ","); // Lot Sequence Number
			sqlString.append("'" + lot.getLocation() + "',"); // Location
			sqlString.append(lot.getHarvestYear() + ","); // Harvest Year
			sqlString.append("'" + lot.getSupplierNumber() + "',"); // Supplier
			sqlString.append("'" + lot.getSupplierDeliveryNote() + "',"); // Supplier Load Number
			sqlString.append("'" + lot.getCrop() + "',"); // Crop
			sqlString.append("'" + lot.getOrganic() + "',"); // Conventional/Organic
			sqlString.append("'" + lot.getRunType() + "',"); // Run Type Machine/Orchard
			sqlString.append("'" + lot.getIntendedUse() + "',"); // Intended Use
			sqlString.append("'" + lot.getVariety() + "',"); // variety
			sqlString.append("'" + lot.getItemNumber() + "',"); // Item
			sqlString.append("0,"); // Cull Percent
			sqlString.append(lot.getCullsPounds() + ","); // Cull Weight
			sqlString.append(lot.getBrix() + ","); // Brix Level
			sqlString.append("0,"); // Brix Price
			sqlString.append(lot.getAcceptedBins25() + ","); // Accepted 25 Box Bins
			sqlString.append(lot.getAcceptedBins30() + ","); // Accepted 30 Box BinsScale Sequence Number
			sqlString.append("'" + lot.getAcceptedBinsComment() + "',"); // Accepted Bins Comment
			sqlString.append(lot.getAcceptedWeight() + ","); // Accepted Weight
			sqlString.append("'" + lot.getAcceptedWeightKeyed() + "',"); // Accepted Weight Hand Keyed (FLAG)
			sqlString.append(lot.getRejectedBins25() + ","); // Rejected 25 Box Bins
			sqlString.append(lot.getRejectedBins30() + ","); // Rejected 30 Box BinsScale Sequence Number
			sqlString.append("'" + lot.getRejectedBinsComment() + "',"); // Rejected Bins Comment
			sqlString.append("'" + lot.getRejectedWeightKeyed() + "',"); // Rejected Weight Hand Keyed (FLAG)
			sqlString.append(lot.getRejectedWeight() + ","); // Rejected Weight
			sqlString.append("'" + lot.getCountryOfOrigin() + "',"); // Country of Origin
			sqlString.append("'" + lot.getAdditionalVariety() + "',"); // Additional Variety
			int total25Box = (new Integer(lot.getAcceptedBins25())).intValue() + (new Integer(lot.getRejectedBins25())).intValue();
			sqlString.append(total25Box + ","); // Total 25 Box Bins ** CALCULATE
			int total30Box = (new Integer(lot.getAcceptedBins30())).intValue() + (new Integer(lot.getRejectedBins30())).intValue();
			sqlString.append(total30Box + ","); // Total 30 Box Bins ** CALCULATE
			sqlString.append("0,"); // Fruit Weight of All Bins
			sqlString.append("0,"); // Average Weight per Bin (Accepted Fruit)
			sqlString.append("'" + lot.getUpdateUser().trim() + "',"); // User
			sqlString.append(dt.getDateFormatyyyyMMdd() + ","); // Date
			sqlString.append(dt.getTimeFormathhmmss() + ", "); // Time
			sqlString.append("'0000000000') "); // Posting Flags
			//sqlString.append(dt.getTimeFormathhmmss() + ") "); // Receiving Time
		}	
//		9/19/13 Twalton Moved to BuildSQL
//		if (inRequestType.equals("addPayment"))
//		{
//			 cast the incoming parameter class.
//			UpdRawFruitLotPayment payment = (UpdRawFruitLotPayment) requestClass.elementAt(0);
//			// get current system date and time.
//			DateTime dt = (DateTime) requestClass.elementAt(1);
//			DateTime dt1 = UtilityDateTime.getDateFromMMddyyWithSlash(payment.getReceivingDate());
///			// build the sql statement.
//			sqlString.append("INSERT INTO " + ttlib + ".GRPCPMT ");
//			sqlString.append("(G5SCALE#, G5CSEQ1, G5SEQ#,  G5PO#,   G5PLIN#, ");
//			sqlString.append(" G5LOT,    G5TSEQ#, G5YSEQ#, G5CPTP,  G5CRTP, ");
//			sqlString.append(" G5CAT,    G525B,   G530B,   G5TBW,   G5TBWO,  ");
//			sqlString.append(" G5PCODE,  G5M3PCD, G5CMV,   G5PRICE, G5ADJP,  ");
//			sqlString.append(" G5PRICEM, G5CROP,  G5CNVOR, G5VAR,   G5INVC,  ");
//			sqlString.append(" G5INV#,   G5USER,  G5DATE,  G5TIME,  G5VONO, ");
//			sqlString.append(" G5JRNO,   G5JSNO,  G5POSTF ) ");
//			sqlString.append(" VALUES( ");
//			sqlString.append("'" + payment.getScaleTicket().trim() + "',"); // Scale Ticket
//			sqlString.append(payment.getScaleTicketCorrectionSequence() + ","); // Scale Ticket Correction Sequence
//			sqlString.append(payment.getScaleSequence() + ","); // Scale Sequence
//			sqlString.append(payment.getPoNumber() + ","); // PO Number
//			sqlString.append(payment.getPoLineNumber() + ","); // PO Line Number
//			sqlString.append("'" + payment.getLotNumber() + "',"); // Lot Number
//			sqlString.append(payment.getLotSequenceNumber() + ","); // Lot Sequence Number
			// Payment Sequence Number
			   // Number +1 times 10
//			BigDecimal paymentSequence = new BigDecimal("10");
//			try
//			{
//			   	BigDecimal ps = new BigDecimal(payment.getPaymentSequence());
//			   	paymentSequence = (ps.add(new BigDecimal("1"))).multiply(new BigDecimal("10"));
//			}
//			catch(Exception e)
	//		{}
//			sqlString.append(paymentSequence + ","); // Payment Sequence Number
//			sqlString.append("'" + payment.getPaymentType() + "',"); // Payment Type
//			sqlString.append("'" + payment.getRunType() + "',"); // Run Type
//			sqlString.append("'" + payment.getCategory() + "',"); // Category
//			sqlString.append(payment.getNumberOfBins25Box() + ","); // Number of Bins 25 Box
//			sqlString.append(payment.getNumberOfBins30Box() + ","); // Number of Bins 30 Box
//			if (payment.getPaymentWeightManuallyEntered().equals(""))
//			   sqlString.append("0,"); // Fruit Weight -- Will need to be Calculated
//			else
//			   sqlString.append(payment.getPaymentWeight() + ", ");
//			if (!payment.getPaymentWeightManuallyEntered().trim().equals(""))
//			{   
//				sqlString.append("'Y', ");
//			}
//			else
//				sqlString.append("' ', ");
//			RawFruitPayCode rfpc = new RawFruitPayCode();
///			try
//			{
//				 Vector sendParms = new Vector();
//				 if (payment.getCrop().equals("GRAPE") &&
//				     payment.getOrganic().length() > 1)
//				 {
//				   sendParms.addElement("grape");
//				   sendParms.addElement(payment.getOrganic().substring(0,1));
//				   sendParms.addElement(payment.getBrix());
//				 }else{
//				   sendParms.addElement(payment.getVariety());
//				   sendParms.addElement(payment.getOrganic());
//				   sendParms.addElement(payment.getCrop());
//				   sendParms.addElement(payment.getRunType());
//				   sendParms.addElement(payment.getCategory());
//				   sendParms.addElement(payment.getPaymentType());
//				   sendParms.addElement(payment.getItemNumber());
//				 }
//			     sendParms.addElement(dt1.getDateFormatyyyyMMdd());
//				 rfpc = findPayCode(sendParms);
//			}
//			catch(Exception e)
//			{
//				System.out.println("Debug the Payment Code Retrieval: " + e);
//			}
//			sqlString.append("'" + rfpc.getPayCode().trim() + "',"); // Payment Code -- Calculated - Generated Code
//			sqlString.append("'" + payment.getPayCodeHandKeyed() + "', ");
//			sqlString.append("0,"); // CMV - Standard Fruit Cost // Retrieved
//			sqlString.append(rfpc.getPricePerTon() + ","); // Default - Fruit Price // Retrieved
//			sqlString.append("0,"); // Calculated Price Adjustment
//			String price = payment.getPrice();
//			try
//			{
//				System.out.println(new BigDecimal(price));
//				System.out.println(new BigDecimal("0"));
//				System.out.println((new BigDecimal(price)).compareTo(new BigDecimal("0")));
//				System.out.println((new BigDecimal("0")).compareTo(new BigDecimal("0")));
//				if ((new BigDecimal(price)).compareTo(new BigDecimal("0")) == 0)
//				   price = rfpc.getPricePerTon();
//			}catch(Exception e)
//			{}
//			sqlString.append(price + ","); // Hand Keyed Price
//			sqlString.append("'" + payment.getCrop() + "',"); // Crop for Payment
//			sqlString.append("'" + payment.getOrganic() + "',"); // Organic / Conventional for Payment
//			sqlString.append("'" + payment.getVariety() + "',"); // Variety for Payment
//			sqlString.append("'',"); // Invoice Created Flag 
//			sqlString.append("'',"); // Invoice Number
//			sqlString.append("'" + payment.getUpdateUser().trim() + "',"); // User
//			sqlString.append(dt.getDateFormatyyyyMMdd() + ","); // Date
//			sqlString.append(dt.getTimeFormathhmmss() + ","); // Time
//			sqlString.append("0,"); // Movex Voucher Number
//			sqlString.append("0,"); // Movex Journal Number
//			sqlString.append("0,"); // Movex Journal Sequence Number
//			sqlString.append("'0000000000' "); // Posting Flags
//			sqlString.append(") ");
//		}
//		9/23/13 TWalton - moved into BuildSQL		
//		if (inRequestType.equals("addSpecialCharges"))
//		{
//			 cast the incoming parameter class.
//			UpdRawFruitLotPaymentSpecialCharges sc = (UpdRawFruitLotPaymentSpecialCharges) requestClass.elementAt(0);
			// get current system date and time.
//			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
//			sqlString.append("INSERT INTO " + ttlib + ".GRPCOTHC ");
//			sqlString.append("(G6SCALE#, G6CSEQ1, G6SEQ#,  G6PO#,   G6PLIN#, ");
//			sqlString.append(" G6LOT,    G6TSEQ#, G6YSEQ#, G6OSEQ#, G6SUPP, ");
//			sqlString.append(" G6RATE,   G6CCODE, G6USER,  G6DATE,  G6TIME, ");
//			sqlString.append(" G6VONO,   G6JRNO,  G6JSNO,  G6POSTF ) ");
//			sqlString.append(" VALUES( ");
//			sqlString.append("'" + sc.getScaleTicket().trim() + "',"); // Scale Ticket
//			sqlString.append(sc.getScaleTicketCorrectionSequence() + ","); // Scale Ticket Correction Sequence
//			sqlString.append(sc.getScaleSequence() + ","); // Scale Sequence
//			sqlString.append(sc.getPoNumber() + ","); // PO Number
//			sqlString.append(sc.getPoLineNumber() + ","); // PO Line Number
//			sqlString.append("'" + sc.getLotNumber() + "',"); // Lot Number
//			sqlString.append(sc.getLotSequenceNumber() + ","); // Lot Sequence Number
//			 Payment Sequence Number
			   // Number +1 times 10
//			BigDecimal paymentSequence = new BigDecimal("10");
//			try
//			{
//			   	BigDecimal ps = new BigDecimal(sc.getPaymentSequence());
//			   	paymentSequence = (ps.add(new BigDecimal("1"))).multiply(new BigDecimal("10"));
//			}
//			catch(Exception e)
//			{}
//			sqlString.append(paymentSequence + ","); // Payment Sequence Number
//			sqlString.append(sc.getPaymentSpecialChargesSequence() + ","); // Special Charges Sequence Number
//			sqlString.append("'" + sc.getSupplier() + "',"); // Supplier
//			sqlString.append(new BigDecimal(sc.getRatePerPound()) + ","); // Rater Per Pound
//			sqlString.append(sc.getAccountingOption() + ","); // Accounting Option - Charge Code
//			sqlString.append("'" + sc.getUpdateUser().trim() + "',"); // User
//			sqlString.append(dt.getDateFormatyyyyMMdd() + ","); // Date
//			sqlString.append(dt.getTimeFormathhmmss() + ", "); // Time
//			sqlString.append("0,"); // Movex Voucher Number
//			sqlString.append("0,"); // Movex Journal Number
//			sqlString.append("0,"); // Movex Journal Sequence Number
//			sqlString.append("'' "); // Posting Flags
//			sqlString.append(") ");
//		}
		if (inRequestType.equals("copyScale"))
		{
			sqlString.append("INSERT INTO " + ttlib + ".GRPCSCALE ");
			sqlString.append("(SELECT G1SCALE#, " + requestClass.elementAt(6) + ", G1RDATE, G1LOADT, ");
			if (((String) requestClass.elementAt(0)).trim().equals("copyNegative"))
			{
				sqlString.append("(G1GRWGT * -1), (G1LWGT * -1), (G1NLWGT * -1), ");
				sqlString.append("(G1BWGT * -1), (G1AFWGT * -1), (G1FBINS * -1), ");                
				sqlString.append("(G1EBINS * -1), (G1TBINS * -1), (G1TBOXES * -1), ");              
				sqlString.append("(G1LBSBX * -1), ");                                               
			}
			else
			{
				sqlString.append("G1GRWGT, G1LWGT, G1NLWGT, G1BWGT, G1AFWGT, ");
				sqlString.append("G1FBINS, G1EBINS, G1TBINS, G1TBOXES, G1LBSBX, ");
			}
			sqlString.append("G1BNBLK, G1CBOL,  G1SCSUPP, ");
			if (((String) requestClass.elementAt(0)).trim().equals("copyNegative"))
			{
				sqlString.append(" CASE WHEN G1FRTFL = 'Y' THEN (G1FRATE * -1) ");
				sqlString.append("     ELSE G1FRATE END, ");                              
			}
			else
			{
				sqlString.append("G1FRATE, ");
			}
			sqlString.append(" G1FRTFL, ");
			if (((String) requestClass.elementAt(0)).trim().equals("copyNegative"))
			{
				sqlString.append(" CASE WHEN G1FRTFL = 'Y' THEN (G1FSCHG * -1) ");
				sqlString.append("     ELSE G1FSCHG END, ");                              
			}
			else
			{
				sqlString.append("G1FSCHG, ");
			}
			sqlString.append("G1FRLOC, G1WHTIK,  G1MIMW, ");
			if (((String) requestClass.elementAt(0)).trim().equals("copyNegative"))
			{
				sqlString.append(" (G1MNLB * -1), ");                              
			}
			else
			{
				sqlString.append("G1MNLB, ");
			}
			sqlString.append("G1COMP,  G1FTCCD, G1FLCCD,  G1FACI,  G1WHSE, ");
			sqlString.append("G1CCNT, '" + requestClass.elementAt(3) + "', "); 
			sqlString.append(requestClass.elementAt(4) + ", " + requestClass.elementAt(5) + ", ");
			sqlString.append("0,0,0,G1HCOD,G1DIM5,G1CCTR,'0000000000', G1TRACK, G1TRLINE, G1RTIME, G1INSP ");
			sqlString.append("FROM " + ttlib + ".GRPCSCALE ");
			sqlString.append(" WHERE G1SCALE# = '" + requestClass.elementAt(1) + "' ");
			sqlString.append("   AND G1CSEQ1 = " + requestClass.elementAt(2) + " ) ");
		}
		if (inRequestType.equals("copyBins"))
		{
			sqlString.append("INSERT INTO " + ttlib + ".GRPCBINS ");
			sqlString.append("(SELECT G2SCALE#, " + requestClass.elementAt(6) + ", G2BINTYP, G2BINDSC, ");
			sqlString.append(" G2BINWGT, ");
			if (((String) requestClass.elementAt(0)).trim().equals("copyNegative"))
			{
				sqlString.append("(G2BINCNT * -1), (G2TWBIN * -1), ");                                               
			}
			else
			{
				sqlString.append("G2BINCNT, G2TWBIN, ");
			}
			sqlString.append("'" + requestClass.elementAt(3) + "', "); 
			sqlString.append(requestClass.elementAt(4) + ", " + requestClass.elementAt(5) + ", '0000000000' ");
			sqlString.append("FROM " + ttlib + ".GRPCBINS ");
			sqlString.append(" WHERE G2SCALE# = '" + requestClass.elementAt(1) + "' ");
			sqlString.append("   AND G2CSEQ1 = " + requestClass.elementAt(2) + " ) ");
		}
		// 9/12/13 TWalton - moved to BuildSQL 
//		if (inRequestType.equals("copyPO"))
//		{
//			sqlString.append("INSERT INTO " + ttlib + ".GRPCPO ");
//			sqlString.append("(SELECT G3SCALE#, " + requestClass.elementAt(6) + ", G3SEQ#, G3PO#, G3PLIN#, ");
//			sqlString.append(" G3CPO, ");
//			if (((String) requestClass.elementAt(0)).trim().equals("copyNegative"))
//			{
//				sqlString.append("(G3AWGT * -1), (G3RWGT * -1), (G3TWGT * -1), ");     
//				sqlString.append("(G3ABINS * -1), (G3RBINS * -1), (G3TBINS * -1), ");         
//			}
//			else
//			{
//				sqlString.append("G3AWGT, G3RWGT, G3TWGT, G3ABINS, G3RBINS, G3TBINS, ");     
//			}
//			sqlString.append("G3COMP, G3FACI, G3WHSE, G3SUPP, ");
//			sqlString.append("'" + requestClass.elementAt(3) + "', "); 
//			sqlString.append(requestClass.elementAt(4) + ", " + requestClass.elementAt(5) + ", '0000000000' ");
//			sqlString.append("FROM " + ttlib + ".GRPCPO ");
//			sqlString.append(" WHERE G3SCALE# = '" + requestClass.elementAt(1) + "' ");
//			sqlString.append("   AND G3CSEQ1 = " + requestClass.elementAt(2) + " ) ");
//		}
		if (inRequestType.equals("copyTicket"))
		{
			sqlString.append("INSERT INTO " + ttlib + ".GRPCTICK ");
			sqlString.append("(SELECT G4SCALE#, " + requestClass.elementAt(6) + ", G4SEQ#, G4PO#, G4PLIN#, ");
			sqlString.append(" G4RDATE, G4FACI, G4WHSE, G4LOT, G4TSEQ#, G4LOC, ");
			sqlString.append(" G4HYEAR, G4SUPP, G4SLOAD, G4CROP, G4CNVOR, G4RUNT, ");
			sqlString.append(" G4IUSE, G4VAR, G4ITEM, G4CULLP, G4CULLW, G4BRIX, ");
			sqlString.append(" G4BRIXP, ");
			
			if (((String) requestClass.elementAt(0)).trim().equals("copyNegative"))
			{
				sqlString.append("(G4A25B * -1), (G4A30B * -1), G4ABINC, (G4ABW * -1), G4ABWO, ");     
				sqlString.append("(G4R25B * -1), (G4R30B * -1), G4RBINC, G4RBWO, (G4RBW * -1), ");   
				sqlString.append(" G4CTYO, G4AVAR, (G4T25B * -1), (G4T30B * -1), (G4TBW * -1), ");
				sqlString.append(" (G4AVGW * -1), ");
			}
			else
			{
				sqlString.append("G4A25B, G4A30B, G4ABINC, G4ABW, G4ABWO, ");     
				sqlString.append("G4R25B, G4R30B, G4RBINC, G4RBWO, G4RBW, ");   
				sqlString.append("G4CTYO, G4AVAR, G4T25B, G4T30B, G4TBW, ");
				sqlString.append("G4AVGW, ");
			}
			sqlString.append("'" + requestClass.elementAt(3) + "', "); 
			sqlString.append(requestClass.elementAt(4) + ", " + requestClass.elementAt(5) + ", '0000000000' "); // G4RTIME not needed
			sqlString.append("FROM " + ttlib + ".GRPCTICK ");
			sqlString.append(" WHERE G4SCALE# = '" + requestClass.elementAt(1) + "' ");
			sqlString.append("   AND G4CSEQ1 = " + requestClass.elementAt(2) + " ) ");
		}
		// 9/29/13 TWalton - Changed to use BuildSQL section
//		if (inRequestType.equals("copyPayment"))
//		{
//			sqlString.append("INSERT INTO " + ttlib + ".GRPCPMT ");
//			sqlString.append("(SELECT G5SCALE#, " + requestClass.elementAt(6) + ", G5SEQ#, G5PO#, G5PLIN#, ");
//			sqlString.append(" G5LOT, G5TSEQ#, G5YSEQ#, G5CPTP, G5CRTP, ");
//			sqlString.append(" G5CAT, ");
//			
//	/		if (((String) requestClass.elementAt(0)).trim().equals("copyNegative"))
//			{
//				sqlString.append("(G525B * -1), (G530B * -1), (G5TBW * -1), ");     
//			}
//			else
//			{
//				sqlString.append("G525B, G530B, G5TBW, ");  
//			}
//			sqlString.append(" G5TBWO, G5PCODE,  G5M3PCD, G5CMV,   G5PRICE,  ");
//			sqlString.append(" G5ADJP, G5PRICEM, G5CROP,  G5CNVOR, G5VAR,    ");
//			sqlString.append(" G5INVC, G5INV#, '" + requestClass.elementAt(3) + "', "); 
//			sqlString.append(requestClass.elementAt(4) + ", " + requestClass.elementAt(5) + ", ");
//			sqlString.append("0,0,0,'0000000000' ");
//			sqlString.append("FROM " + ttlib + ".GRPCPMT ");
//			sqlString.append(" WHERE G5SCALE# = '" + requestClass.elementAt(1) + "' ");
//			sqlString.append("   AND G5CSEQ1 = " + requestClass.elementAt(2) + " ) ");
//		}
		if (inRequestType.equals("copyOther"))
		{
			sqlString.append("INSERT INTO " + ttlib + ".GRPCOTHC ");
			sqlString.append("(SELECT G6SCALE#, " + requestClass.elementAt(6) + ", G6SEQ#, G6PO#, G6PLIN#, ");
			sqlString.append(" G6LOT, G6TSEQ#, G6YSEQ#, G6OSEQ#, G6SUPP, G6RATE, G6CCODE, ");
			sqlString.append("'" + requestClass.elementAt(3) + "', "); 
			sqlString.append(requestClass.elementAt(4) + ", " + requestClass.elementAt(5) + ", ");
			sqlString.append("0,0,0,'0000000000' ");
			sqlString.append("FROM " + ttlib + ".GRPCOTHC ");
			sqlString.append(" WHERE G6SCALE# = '" + requestClass.elementAt(1) + "' ");
			sqlString.append("   AND G6CSEQ1 = " + requestClass.elementAt(2) + " ) ");
		}
		// ------------------------------------------------------------------------------------
		//  UPDATE
		
		if (inRequestType.equals("updateLoadQuickEntry"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			DateTime dt = (DateTime) requestClass.elementAt(1);
			DateTime dt1 = UtilityDateTime.getDateFromMMddyyWithSlash(fromVb.getReceivingDate());
			String bulk = "";
			
			if (fromVb.getBulkLoad() != null && fromVb.getBulkLoad().equals("on"))
				bulk = "Y";
			
			sqlString.append("UPDATE " + ttlib + ".GRPCSCALE ");
			sqlString.append("SET ");
			sqlString.append(" G1RDATE = " + dt1.getDateFormatyyyyMMdd() + ", ");
			sqlString.append("G1RTIME = " + fromVb.getReceivingTime() + ", ");
			sqlString.append("G1FBINS = " + fromVb.getLoadFullBins().trim() + ",");
			sqlString.append("G1BNBLK = '" + bulk + "', ");
			String facility = "";
			
			if (!fromVb.getWarehouse().trim().equals(""))
			{
			   try
			   {
				  Vector sendValue = new Vector();
				  sendValue.addElement(fromVb.getWarehouse());
				  Warehouse whse = ServiceWarehouse.findWarehouse("PRD", sendValue);
		          if (!whse.getFacility().trim().equals(""))
				    facility = whse.getFacility();
			   }
			   catch(Exception e)
			   {}
			}
			
			sqlString.append(" G1FACI = '" + facility + "', ");
			sqlString.append(" G1WHSE = '" + fromVb.getWarehouse() + "', ");
			sqlString.append(" G1USER = 'QUICKENTRY',");
			sqlString.append(" G1DATE = " + dt.getDateFormatyyyyMMdd().trim() + ",");
			sqlString.append(" G1TIME = " + dt.getTimeFormathhmmss().trim() + " ");
			sqlString.append(" WHERE G1SCALE# = '" + fromVb.getScaleTicket() + "' ");
			sqlString.append(" AND G1CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence() + " ");
		}
		
		if (inRequestType.equals("updateLoad"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			DateTime dt1 = UtilityDateTime.getDateFromMMddyyWithSlash(fromVb.getReceivingDate());
			// build the sql statement.
			sqlString.append("UPDATE " + ttlib + ".GRPCSCALE ");
			sqlString.append(" SET ");
			sqlString.append(" G1RDATE = " + dt1.getDateFormatyyyyMMdd() + ", ");
			sqlString.append(" G1RTIME = " + fromVb.getReceivingTime() + ", ");
			sqlString.append(" G1LOADT = '" + fromVb.getLoadType().trim() + "', ");
			sqlString.append(" G1GRWGT = " + fromVb.getGrossWeight().trim() + ",");
			sqlString.append(" G1LWGT = " + fromVb.getTareWeight().trim() + ",");
			BigDecimal netWgt = new BigDecimal("0");
			try
			{
				netWgt = (new BigDecimal(fromVb.getGrossWeight())).subtract(new BigDecimal(fromVb.getTareWeight()));
			}
			catch(Exception e)
			{}
			sqlString.append(" G1NLWGT = " + netWgt.toString() + ",");
			sqlString.append(" G1FBINS = " + fromVb.getLoadFullBins().trim() + ",");
			sqlString.append(" G1EBINS = " + fromVb.getLoadEmptyBins().trim() + ",");
			BigDecimal totBins = new BigDecimal("0");
			try
			{
				totBins = (new BigDecimal(fromVb.getLoadFullBins())).add(new BigDecimal(fromVb.getLoadEmptyBins()));
			}
			catch(Exception e)
			{}
			sqlString.append(" G1TBINS = " + totBins.toString() + ",");
			if (!fromVb.getBulkLoad().trim().equals(""))
			  sqlString.append(" G1BNBLK = 'Y', ");
			else
			  sqlString.append(" G1BNBLK = '', ");
			sqlString.append(" G1CBOL = '" + fromVb.getCarrierBillOfLading().trim() + "',");
			sqlString.append(" G1SCSUPP = '" + fromVb.getCarrierSupplier() + "',");
			sqlString.append(" G1FRATE = " + fromVb.getLoadFreightRate() + ",");
			String setSurcharge = fromVb.getLoadSurcharge();
			if (!fromVb.getFlatRateFlag().trim().equals(""))
			{
			  sqlString.append(" G1FRTFL = 'Y', ");
			  try
				{ // Test to see if the value == 0
					BigDecimal testSurcharge = new BigDecimal(fromVb.getLoadSurcharge());
					if (testSurcharge.compareTo(new BigDecimal("0")) == 0)
						setSurcharge = "1";
				}
				catch(Exception e)
				{}
			}
			else
				sqlString.append(" G1FRTFL = '', ");
			sqlString.append(" G1FSCHG = " + setSurcharge + ",");  	
			sqlString.append(" G1FRLOC = '" + fromVb.getFromLocation() + "',");
			sqlString.append(" G1WHTIK = '" + fromVb.getWhseTicket() + "',");
			sqlString.append(" G1MIMW = '" + fromVb.getMinimumWeightFlag().trim() + "',");
			sqlString.append(" G1MNLB = " + fromVb.getMinimumWeightValue().trim() + ", ");
			sqlString.append(" G1FTCCD = " + fromVb.getLoadFreightRateCode() + ", ");
			sqlString.append(" G1FLCCD = " + fromVb.getLoadSurchargeCode() + ", ");
			sqlString.append(" G1INSP = '" + fromVb.getInspectedBy().trim() + "', "); //  Inspected By
			String facility = "";
			if (!fromVb.getWarehouse().trim().equals(""))
			{
			   try
			   {
				  Vector sendValue = new Vector();
				  sendValue.addElement(fromVb.getWarehouse());
				  Warehouse whse = ServiceWarehouse.findWarehouse("PRD", sendValue);
		          if (!whse.getFacility().trim().equals(""))
				    facility = whse.getFacility();
			   }
			   catch(Exception e)
			   {}
			}
			sqlString.append(" G1FACI = '" + facility + "', ");
			sqlString.append(" G1WHSE = '" + fromVb.getWarehouse() + "', ");
			sqlString.append(" G1USER = '" + fromVb.getUpdateUser().trim() + "',");
			sqlString.append(" G1DATE = " + dt.getDateFormatyyyyMMdd().trim() + ",");
			sqlString.append(" G1TIME = " + dt.getTimeFormathhmmss().trim() + ", ");
			
			if (!fromVb.getScheduledLoadNumber().trim().equals(""))
			   sqlString.append(" G1TRACK = " + fromVb.getScheduledLoadNumber() + ", ");
			
			sqlString.append(" G1HCOD = '" + fromVb.getHandlingCodeLong().trim() + "',");
			sqlString.append(" G1DIM5 = '" + fromVb.getHandlingCode().trim() + "',");
			sqlString.append(" G1CCTR = '" + fromVb.getCostCenter().trim() + "' ");

			sqlString.append(" WHERE G1SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G1CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence() + " ");
		}
		if (inRequestType.equals("updateCorrectionCount"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCSCALE ");
			sqlString.append("  SET G1CCNT = " + requestClass.elementAt(7) + " ");
			sqlString.append(" WHERE G1SCALE# = '" + requestClass.elementAt(1) + "' ");
		}
		if (inRequestType.equals("updateLoadPO"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("UPDATE " + ttlib + ".GRPCPO ");
			sqlString.append(" SET ");
			sqlString.append("  G3SUPP = '" + fromVb.getCarrierSupplier() + "', ");
			sqlString.append("  G3USER = '" + fromVb.getUpdateUser() + "',");
			sqlString.append("  G3DATE = " + dt.getDateFormatyyyyMMdd() + ",");
			sqlString.append("  G3TIME = " + dt.getTimeFormathhmmss() + " ");
			sqlString.append(" WHERE G3SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G3CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence().trim() + " ");
			sqlString.append("   AND G3SUPP = '' ");
		}
		if (inRequestType.equals("updateLoadLot"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
			// build the sql statement.
			sqlString.append("UPDATE " + ttlib + ".GRPCTICK ");
			sqlString.append(" SET ");
			sqlString.append("  G4SUPP = '" + fromVb.getCarrierSupplier() + "', ");
			sqlString.append("  G4USER = '" + fromVb.getUpdateUser() + "',");
			sqlString.append("  G4DATE = " + dt.getDateFormatyyyyMMdd() + ",");
			sqlString.append("  G4TIME = " + dt.getTimeFormathhmmss() + " ");
			sqlString.append(" WHERE G4SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G4CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence().trim() + " ");
			sqlString.append("   AND G4SUPP = '' ");
		}
		if (inRequestType.equals("updateScaleBinTare"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCSCALE ");
			sqlString.append(" SET G1BWGT = CASE ");
			sqlString.append("   WHEN (SELECT count(*) FROM " + ttlib + ".GRPCBINS ");
			sqlString.append("     WHERE G1SCALE# = G2SCALE# ");
			sqlString.append("       AND G1CSEQ1 = G2CSEQ1) = 0 then 0 ");
			sqlString.append("   ELSE (SELECT SUM(G2TWBIN) FROM " + ttlib + ".GRPCBINS ");
			sqlString.append("     WHERE G1SCALE# = G2SCALE# ");
			sqlString.append("       AND G1CSEQ1 = G2CSEQ1) ");
			sqlString.append("    END ");
			sqlString.append(" WHERE G1SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G1CSEQ1 = " + (String) requestClass.elementAt(1) + " ");
		}
		if (inRequestType.equals("updateTotalBoxes"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCSCALE ");
			sqlString.append(" SET G1TBOXES = ");
			// Calculation of Total boxes should include Accepted AND Rejected -- from 1/7/09 meeting with Field
			sqlString.append(" (SELECT ((SUM(G4A25B) * 25) + (sum(G4A30B) * 30) ");
			sqlString.append("        + (SUM(G4R25B) * 25) + (sum(G4R30B) * 30))");
			sqlString.append("     FROM " + ttlib + ".GRPCTICK ");
			sqlString.append("       WHERE G1SCALE# = G4SCALE# ");
			sqlString.append("         AND G1CSEQ1 = G4CSEQ1) ");
			sqlString.append(" WHERE G1SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G1CSEQ1 = " + (String) requestClass.elementAt(1) + " ");
		}
		if (inRequestType.equals("updateScaleTotal"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCSCALE ");
			sqlString.append(" SET G1AFWGT = ");
			sqlString.append(" (G1NLWGT - G1BWGT), ");
			sqlString.append(" G1LBSBX = CASE ");
			sqlString.append("         WHEN G1TBOXES = 0 THEN 0 ");
			//sqlString.append("         ELSE (G1NLWGT - G1BWGT) = 0 then 0 ");
			sqlString.append("         ELSE ((G1NLWGT - G1BWGT) / G1TBOXES) ");
			sqlString.append("         END ");
			sqlString.append(" WHERE G1SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G1CSEQ1 = " + (String) requestClass.elementAt(1) + " ");
		}
		if (inRequestType.equals("updateScaleAveWeightPerBox"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCSCALE ");
			sqlString.append(" SET G1LBSBX = ");
			sqlString.append("     CASE WHEN G1TBOXES = 0 then 0 ");
			//sqlString.append("     ELSE (G1NLWGT - G1BWGT) = 0 then 0 ");
			sqlString.append("     ELSE ((G1NLWGT - G1BWGT) / G1TBOXES) ");
			sqlString.append("     END ");
			sqlString.append(" WHERE G1SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G1CSEQ1 = " + (String) requestClass.elementAt(1) + " ");
		}
		if (inRequestType.equals("updateBinDescription"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("UPDATE " + ttlib + ".GRPCBINS ");
			sqlString.append(" SET G2BINDSC = ");
			sqlString.append(" (SELECT GRADSC FROM " + ttlib + ".GRPABNMS ");
			sqlString.append("     WHERE GRATYP = G2BINTYP), ");
			sqlString.append(" G2BINWGT = ");
			sqlString.append(" (SELECT GRAWGT FROM " + ttlib + ".GRPABNMS ");
			sqlString.append("     WHERE GRATYP = G2BINTYP) ");
			sqlString.append(" WHERE G2SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G2CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence() + " ");
		}
		if (inRequestType.equals("updateBinCalc"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("UPDATE " + ttlib + ".GRPCBINS ");
			sqlString.append(" SET G2TWBIN = ");
			sqlString.append(" (G2BINWGT * G2BINCNT) ");
			sqlString.append(" WHERE G2SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G2CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence().trim() + " ");
		}
		if (inRequestType.equals("updateRecDate"))
		{
			DateTime dt1 = UtilityDateTime.getDateFromMMddyyWithSlash((String) requestClass.elementAt(2));
			
			sqlString.append("UPDATE " + ttlib + ".GRPCTICK ");
			sqlString.append(" SET G4RDATE = " + dt1.getDateFormatyyyyMMdd() + " ");
			sqlString.append(" WHERE G4SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append(" AND G4CSEQ1 = " + (String) requestClass.elementAt(1) + " ");
		}
		if (inRequestType.equals("updateLotCalcWgt"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCTICK ");
			sqlString.append(" SET G4ABW = " + (String) requestClass.elementAt(5) + ", ");
			sqlString.append("     G4RBW = " + (String) requestClass.elementAt(6) + ", ");
			sqlString.append("     G4TBW = " + (String) requestClass.elementAt(7) + " "); 
			sqlString.append(" WHERE G4SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G4CSEQ1 = " + (String) requestClass.elementAt(1) + " ");
			sqlString.append("   AND G4SEQ# = " + (String) requestClass.elementAt(2) + " ");
			sqlString.append("   AND G4TSEQ# = " + (String) requestClass.elementAt(3) + " ");
			sqlString.append("   AND G4LOT = '" + (String) requestClass.elementAt(4) + "' ");
		}
		if (inRequestType.equals("updateLotCalc"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCTICK ");
			sqlString.append(" SET G4AVGW = ");
			sqlString.append("     CASE WHEN (G4A25B + G4A30B) = 0 THEN 0 ");
			sqlString.append("     ELSE G4ABW/(G4A25B + G4A30B) ");
			sqlString.append("     END, ");
			sqlString.append("     G4CULLP = ");
			sqlString.append("     CASE WHEN G4TBW = 0 THEN 0 ");
			sqlString.append("          WHEN G4CULLW = 0 THEN 0 ");
			sqlString.append("          ELSE G4CULLW / G4TBW ");
			sqlString.append("          END ");
			sqlString.append(" WHERE G4SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G4CSEQ1 = " + (String) requestClass.elementAt(1) + " "); 
		}
		if (inRequestType.equals("updateLotBrixPrice"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCTICK ");
			sqlString.append(" SET G4BRIXP = (SELECT GBJTON$ ");
			sqlString.append("                FROM " + ttlib + ".GRPJBPRC ");
			sqlString.append("                WHERE G4BRIX = GBJBRIX ");
			sqlString.append("                AND SUBSTRING(G4CNVOR, 1,1) = GBJORCNV ");
			sqlString.append("                AND SUBSTRING(GBJAOD, 1,4) = '2009') ");
			sqlString.append(" WHERE G4SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G4CSEQ1 = " + (String) requestClass.elementAt(1) + " "); 
			sqlString.append("   AND G4BRIX <> 0 ");
			sqlString.append("   AND G4CROP = 'GRAPE' ");
			sqlString.append("   AND EXISTS (SELECT * ");
			sqlString.append("                FROM " + ttlib + ".GRPJBPRC ");
			sqlString.append("                WHERE G4BRIX = GBJBRIX ");
			sqlString.append("                AND SUBSTRING(G4CNVOR, 1,1) = GBJORCNV ");
			sqlString.append("                AND SUBSTRING(GBJAOD, 1,4) = '2009') ");
		}
	// 9/12/13 Twalton - moved to buildSQL
//		if (inRequestType.equals("updatePO"))
//		{
//			// Send ini the UpdRawFruitPO
//			UpdRawFruitPO urfp = (UpdRawFruitPO) requestClass.elementAt(0);
//			 get current system date and time.
//			DateTime dt = (DateTime) requestClass.elementAt(1);
				// build the sql statement.
//			sqlString.append("UPDATE " + ttlib + ".GRPCPO ");
//			sqlString.append(" SET G3PO# = " + urfp.getPoNumber() + ",");
//			String facility = "";
//			if (!urfp.getPoWarehouse().trim().equals(""))
//			{
//			   try
//			   {
//				  Vector sendValue = new Vector();
//				  sendValue.addElement(urfp.getPoWarehouse());
//				  Warehouse whse = ServiceWarehouse.findWarehouse("PRD", sendValue);
//		          if (!whse.getFacility().trim().equals(""))
//				    facility = whse.getFacility();
//			   }
//			   catch(Exception e)
//			   {}
//			}
//			sqlString.append("     G3FACI = '" + facility + "',");
//		    sqlString.append("     G3WHSE = '" + urfp.getPoWarehouse() + "',");
//			sqlString.append("     G3SUPP = '" + urfp.getSupplier() + "',");
//			sqlString.append(" G3USER = '" + urfp.getUpdateUser() + "',");
//			sqlString.append(" G3DATE = " + dt.getDateFormatyyyyMMdd() + ",");
//			sqlString.append(" G3TIME = " + dt.getTimeFormathhmmss() + " ");		
//			sqlString.append(" WHERE G3SCALE# = '" + urfp.getScaleTicket() + "' ");
//			sqlString.append("   ANd G3CSEQ1 = " + urfp.getScaleTicketCorrectionSequence() + " ");
//			sqlString.append("   AND G3SEQ# = " + urfp.getSequenceNumber() + " ");
//		}
		if (inRequestType.equals("updatePOLot"))
		{
			// Send ini the UpdRawFruitPO
			UpdRawFruitPO urfp = (UpdRawFruitPO) requestClass.elementAt(0);
//			 get current system date and time.
			DateTime dt = (DateTime) requestClass.elementAt(1);
				// build the sql statement.
			sqlString.append("UPDATE " + ttlib + ".GRPCTICK ");
			sqlString.append(" SET G4PO# = " + urfp.getPoNumber() + ",");
			String facility = "";
			if (!urfp.getPoWarehouse().trim().equals(""))
			{
			   try
			   {
				  Vector sendValue = new Vector();
				  sendValue.addElement(urfp.getPoWarehouse());
				  Warehouse whse = ServiceWarehouse.findWarehouse("PRD", sendValue);
		          if (!whse.getFacility().trim().equals(""))
				    facility = whse.getFacility();
			   }
			   catch(Exception e)
			   {}
			}
			sqlString.append("     G4FACI = '" + facility + "',");
		    sqlString.append("     G4WHSE = '" + urfp.getPoWarehouse() + "',");
			sqlString.append("     G4SUPP = '" + urfp.getSupplier() + "',");
			sqlString.append(" G4USER = '" + urfp.getUpdateUser() + "',");
			sqlString.append(" G4DATE = " + dt.getDateFormatyyyyMMdd() + ",");
			sqlString.append(" G4TIME = " + dt.getTimeFormathhmmss() + " ");		
			sqlString.append(" WHERE G4SCALE# = '" + urfp.getScaleTicket() + "' ");
			sqlString.append("   AND G4CSEQ1 = " + urfp.getScaleTicketCorrectionSequence() + " ");
			sqlString.append("   AND G4SEQ# = " + urfp.getSequenceNumber() + " ");
		}
		if (inRequestType.equals("updatePOFromLot"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCPO ");
			sqlString.append(" SET G3ABINS = " + (String) requestClass.elementAt(0) + ",");
		    sqlString.append("     G3RBINS = " + (String) requestClass.elementAt(1) + ",");
		    sqlString.append("     G3TBINS = " + (String) requestClass.elementAt(2) + ",");
			sqlString.append("     G3AWGT = " + (String) requestClass.elementAt(3) + ",");
			sqlString.append("     G3RWGT = " + (String) requestClass.elementAt(4) + ",");
			sqlString.append("     G3TWGT = " + (String) requestClass.elementAt(5) + ",");
			sqlString.append(" G3USER = '" + (String) requestClass.elementAt(9) + "',");
			sqlString.append(" G3DATE = " + (String) requestClass.elementAt(10) + ",");
			sqlString.append(" G3TIME = " + (String) requestClass.elementAt(11) + " ");		
			sqlString.append(" WHERE G3SCALE# = '" + (String) requestClass.elementAt(6) + "' ");
			sqlString.append("   AND G3CSEQ1 = " + (String) requestClass.elementAt(7) + " ");
			sqlString.append("   AND G3SEQ# = " + (String) requestClass.elementAt(8) + " ");
		}
		// 9/13/13 Twalton - moved to buildSQL		
//		if (inRequestType.equals("updateLot"))
//		{
			// cast the incoming parameter class.
//			UpdRawFruitLot fromVb = (UpdRawFruitLot) requestClass.elementAt(0);
			// get current system date and time.
//			DateTime dt = (DateTime) requestClass.elementAt(1);
//			DateTime dt1 = UtilityDateTime.getDateFromMMddyyWithSlash(fromVb.getReceivingDate());
			// build the sql statement.
//			sqlString.append("UPDATE " + ttlib + ".GRPCTICK ");
//			sqlString.append(" SET ");
//			sqlString.append(" G4RDATE = " + dt1.getDateFormatyyyyMMdd() + ", ");
//			sqlString.append(" G4FACI = '" + fromVb.getFacility().trim() + "', "); no longer allowing update of FACI -- Update at the PO Level
//			sqlString.append(" G4WHSE = '" + fromVb.getWarehouse().trim() + "', "); no longer allowing update of WHSE -- update at the PO Level
//			sqlString.append(" G4LOT = '" + fromVb.getLotNumber().trim() + "',");
//			sqlString.append(" G4LOC = '" + fromVb.getLocation().trim() + "',");
//			sqlString.append(" G4HYEAR = " + fromVb.getHarvestYear() + ",");
//			sqlString.append(" G4SUPP = '" + fromVb.getSupplierNumber().trim() + "',"); no longer allowing update of the SUPP -- update at the PO Level
//			sqlString.append(" G4SLOAD = '" + fromVb.getSupplierDeliveryNote().trim() + "',");
//			sqlString.append(" G4CROP = '" + fromVb.getCrop().trim() + "',");
//			try
//			{
//				if (!fromVb.getItemNumber().equals("") &&
//				    !fromVb.getItemNumber().equals("75000") &&
//					fromVb.getOrganic().equals(""))
//				{
//					BeanItem bi = ServiceItem.buildNewItem("PRD", fromVb.getItemNumber());
//			        sqlString.append(" G4CNVOR = '" + bi.getItemClass().getOrganicConventional() + "',");
//				}
//			}
//			catch(Exception e)
//			{}
//			sqlString.append(" G4RUNT = '" + fromVb.getRunType() + "',");
//			sqlString.append(" G4IUSE = '" + fromVb.getIntendedUse().trim() + "',");
//			sqlString.append(" G4VAR = '" + fromVb.getVariety().trim() + "',");
//			sqlString.append(" G4AVAR = '" + fromVb.getAdditionalVariety() + "',");
//			sqlString.append(" G4CTYO = '" + fromVb.getCountryOfOrigin() + "', ");
//			sqlString.append(" G4ITEM = '" + fromVb.getItemNumber().trim() + "',");
//			sqlString.append(" G4CULLW = " + fromVb.getCullsPounds() + ",");
//			sqlString.append(" G4BRIX = " + fromVb.getBrix() + ",");
//			sqlString.append(" G4A25B = " + fromVb.getAcceptedBins25() + ",");
//			sqlString.append(" G4A30B = " + fromVb.getAcceptedBins30() + ",");
//			sqlString.append(" G4ABINC = '" + fromVb.getAcceptedBinsComment() + "',");
//			BigDecimal totalWeight = new BigDecimal("0");
//			if (!fromVb.getAcceptedWeightKeyed().trim().equals(""))
//			{   
//				sqlString.append(" G4ABWO = 'Y', ");
//				totalWeight = new BigDecimal(fromVb.getAcceptedWeight());
//			}
//			else
//				sqlString.append(" G4ABWO = ' ', ");
//			sqlString.append(" G4ABW = " + fromVb.getAcceptedWeight() + ",");
//			sqlString.append(" G4R25B = " + fromVb.getRejectedBins25() + ",");
//			sqlString.append(" G4R30B = " + fromVb.getRejectedBins30() + ",");
//			sqlString.append(" G4RBINC = '" + fromVb.getRejectedBinsComment() + "',");
//			if (!fromVb.getRejectedWeightKeyed().trim().equals(""))
//			{
//				sqlString.append(" G4RBWO = 'Y', ");
//				totalWeight = totalWeight.add(new BigDecimal(fromVb.getRejectedWeight()));
//			}
//			else
//				sqlString.append(" G4RBWO = '', ");
//			sqlString.append(" G4RBW = " + fromVb.getRejectedWeight() + ",");
//			int total25Box = (new Integer(fromVb.getAcceptedBins25())).intValue() + (new Integer(fromVb.getRejectedBins25())).intValue();
//			sqlString.append(" G4T25B = " + total25Box + ","); // CALCULATION
//			int total30Box = (new Integer(fromVb.getAcceptedBins30())).intValue() + (new Integer(fromVb.getRejectedBins30())).intValue();
//			sqlString.append(" G4T30B = " + total30Box + ","); // CALCULATION
//			if (totalWeight.compareTo(new BigDecimal("0")) != 0)
//			{
//				sqlString.append(" G4TBW = " + totalWeight + ",");
//			}
//			sqlString.append(" G4USER = '" + fromVb.getUpdateUser().trim() + "',");
//			sqlString.append(" G4DATE = " + dt.getDateFormatyyyyMMdd().trim() + ",");
//			sqlString.append(" G4TIME = " + dt.getTimeFormathhmmss().trim() + " ");
//			sqlString.append(" WHERE G4SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
//			sqlString.append("   AND G4CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence() + " ");
//			sqlString.append("   AND G4SEQ# = " + fromVb.getScaleSequence() + " ");
//			sqlString.append("   AND G4TSEQ# = " + fromVb.getLotSequenceNumber() + " ");
//		}
		if (inRequestType.equals("updatePaymentWgt"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCPMT ");
			sqlString.append(" SET G5TBW = " + (String) requestClass.elementAt(6) + " ");
			sqlString.append(" WHERE G5SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G5CSEQ1 = " + (String) requestClass.elementAt(1) + " "); 
			sqlString.append("   AND G5SEQ# = " + (String) requestClass.elementAt(2) + " ");
			sqlString.append("   AND G5TSEQ# = " + (String) requestClass.elementAt(3) + " ");
			sqlString.append("   AND G5LOT = '" + (String) requestClass.elementAt(4) + "' ");
			sqlString.append("   AND G5YSEQ# = " + (String) requestClass.elementAt(5) + " ");
			sqlString.append("   AND G5TBWO = ' ' ");
		}
		if (inRequestType.equals("updatePONumberOnPO"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCPO ");
			sqlString.append(" SET G3PO# = " + (String) requestClass.elementAt(5) + " ");
			sqlString.append(" WHERE G3SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G3CSEQ1 = " + (String) requestClass.elementAt(1) + " "); 
			sqlString.append("   AND G3SEQ# = " + (String) requestClass.elementAt(2) + " ");
		}
		if (inRequestType.equals("updatePONumberOnLot"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCTICK ");
			sqlString.append(" SET G4PO# = " + (String) requestClass.elementAt(5) + ", ");
			sqlString.append("     G4PLIN# = " + (String) requestClass.elementAt(6) + " ");
			sqlString.append(" WHERE G4SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G4CSEQ1 = " + (String) requestClass.elementAt(1) + " "); 
			sqlString.append("   AND G4SEQ# = " + (String) requestClass.elementAt(2) + " ");
			sqlString.append("   AND G4LOT  = '" + (String) requestClass.elementAt(4) + "' ");
		}
		if (inRequestType.equals("updatePONumberOnPayment"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCPMT ");
			sqlString.append(" SET G5PO# = " + (String) requestClass.elementAt(5) + ", ");
			sqlString.append("     G5PLIN# = " + (String) requestClass.elementAt(6) + " ");
			sqlString.append(" WHERE G5SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G5CSEQ1 = " + (String) requestClass.elementAt(1) + " "); 
			sqlString.append("   AND G5SEQ# = " + (String) requestClass.elementAt(2) + " ");
			sqlString.append("   AND G5LOT  = '" + (String) requestClass.elementAt(4) + "' ");
		}
		if (inRequestType.equals("updatePONumberOnSpecialCharge"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCOTHC ");
			sqlString.append(" SET G6PO# = " + (String) requestClass.elementAt(5) + ", ");
			sqlString.append("     G6PLIN# = " + (String) requestClass.elementAt(6) + " ");
			sqlString.append(" WHERE G6SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G6CSEQ1 = " + (String) requestClass.elementAt(1) + " "); 
			sqlString.append("   AND G6SEQ# = " + (String) requestClass.elementAt(2) + " ");
			sqlString.append("   AND G6LOT  = '" + (String) requestClass.elementAt(4) + "' ");
		}
		if (inRequestType.equals("updateLotCopyTicket"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCTICK ");
			sqlString.append(" SET G4LOT = '" + (String) requestClass.elementAt(3) + "' ");
			sqlString.append(" WHERE G4SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G4CSEQ1 = " + (String) requestClass.elementAt(1) + " "); 
			sqlString.append("   AND G4LOT  = '" + (String) requestClass.elementAt(2) + "' ");
		}
		if (inRequestType.equals("updateLotCopyPayment"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCPMT ");
			sqlString.append(" SET G5LOT = '" + (String) requestClass.elementAt(3) + "' ");
			sqlString.append(" WHERE G5SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G5CSEQ1 = " + (String) requestClass.elementAt(1) + " "); 
			sqlString.append("   AND G5LOT  = '" + (String) requestClass.elementAt(2) + "' ");
		}
		if (inRequestType.equals("updateLotCopyOther"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCOTHC ");
			sqlString.append(" SET G6LOT = '" + (String) requestClass.elementAt(3) + "' ");
			sqlString.append(" WHERE G6SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G6CSEQ1 = " + (String) requestClass.elementAt(1) + " "); 
			sqlString.append("   AND G6LOT  = '" + (String) requestClass.elementAt(2) + "' ");
		}
		if (inRequestType.equals("updateCulls"))
		{
			sqlString.append("UPDATE " + ttlib + ".GRPCTICK ");
			sqlString.append(" SET G4CULLP = " + (String) requestClass.elementAt(5) + ", ");
			sqlString.append("     G4CULLW = " + (String) requestClass.elementAt(4) + " ");
			sqlString.append(" WHERE G4SCALE# = '" + (String) requestClass.elementAt(0) + "' ");
			sqlString.append("   AND G4CSEQ1 = " + (String) requestClass.elementAt(1) + " ");
			sqlString.append("   AND G4SEQ# = " + (String) requestClass.elementAt(2) + " ");
			sqlString.append("   AND G4LOT  = '" + (String) requestClass.elementAt(3) + "' ");
		}
		// ------------------------------------------------------------------------------------
		//  DELETE
		if (inRequestType.equals("deleteBins"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCBINS ");
			sqlString.append(" WHERE G2SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G2CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence().trim() + " ");
		}	
//		9/19/13 Twalton Moved over to BuildSQL
//		if (inRequestType.equals("deletePayment"))
//		{
//			// Cast the Fields all Strings to a Vector
			// Element 0=Scale Ticket
			//         1=Scale Ticket Correction Sequence Number
			//         2=Scale Sequence
			//         3=PO Number
			//         4=Lot Sequence
			//         5=Lot Number (sent in the Original Lot in case the lot number changed)

//			sqlString.append("DELETE FROM " + ttlib + ".GRPCPMT ");
//			sqlString.append(" WHERE G5SCALE# = '" + ((String)requestClass.elementAt(0)).trim() + "' ");
//			sqlString.append("   AND G5CSEQ1 = " + (String) requestClass.elementAt(1) + " ");
//			sqlString.append("   AND G5SEQ# = " + (String) requestClass.elementAt(2) + " ");
//			sqlString.append("   AND G5PO# = " + (String) requestClass.elementAt(3) + " ");
//			sqlString.append("   AND G5TSEQ# = " + (String) requestClass.elementAt(4) + " ");
//			sqlString.append("   AND G5LOT = '" + ((String)requestClass.elementAt(5)).trim() + "' ");
//		}
//		9/23/13 TWalton Moved over to BuildSQL
//		if (inRequestType.equals("deleteSpecialCharges"))
//		{
			// Cast the Fields all Strings to a Vector
			// Element 0=Scale Ticket
//	         1=Scale Ticket Correction Sequence Number
//			//         2=Scale Sequence
//			//         3=PO Number
			//         4=Lot Sequence
			//         5=Lot Number (sent in the Original Lot in case the lot number changed)
			
//			sqlString.append("DELETE FROM " + ttlib + ".GRPCOTHC ");
//			sqlString.append(" WHERE G6SCALE# = '" + ((String)requestClass.elementAt(0)).trim() + "' ");
//			sqlString.append(" AND G6CSEQ1 = " + (String) requestClass.elementAt(1) + " ");			
//			sqlString.append("   AND G6SEQ# = " + (String) requestClass.elementAt(2) + " ");
//			sqlString.append("   AND G6PO# = " + (String) requestClass.elementAt(3) + " ");
//			sqlString.append("   AND G6TSEQ# = " + (String) requestClass.elementAt(4) + " ");
//			sqlString.append("   AND G6LOT = '" + ((String)requestClass.elementAt(5)).trim() + "' ");
//		}	
		if (inRequestType.equals("deletePO"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCPO ");
			sqlString.append(" WHERE G3SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G3CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence() + " ");
			sqlString.append("   AND G3SEQ# = " + fromVb.getScaleSequence() + " ");
//			sqlString.append("   AND G3PO# = " + fromVb.getPoNumber() + " ");
		}
		
		if (inRequestType.equals("deleteAllPOsForScaleTicketSequence"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCPO ");
			sqlString.append(" WHERE G3SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G3CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence() + " ");
		}
		
		if (inRequestType.equals("deletePOOtherCharges"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCOTHC ");
			sqlString.append(" WHERE G6SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G6CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence().trim() + " ");
			sqlString.append("   AND G6SEQ# = " + fromVb.getScaleSequence() + " ");
//			sqlString.append("   AND G6PO# = " + fromVb.getPoNumber() + " ");
		}
		if (inRequestType.equals("deletePOPayment"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCPMT ");
			sqlString.append(" WHERE G5SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G5CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence() + " ");
			sqlString.append("   AND G5SEQ# = " + fromVb.getScaleSequence() + " ");
//			sqlString.append("   AND G5PO# = " + fromVb.getPoNumber() + " ");
		}
		if (inRequestType.equals("deletePOLot"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCTICK ");
			sqlString.append(" WHERE G4SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G4CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence() + " ");
			sqlString.append("   AND G4SEQ# = " + fromVb.getScaleSequence() + " ");
//			sqlString.append("   AND G4PO# = " + fromVb.getPoNumber() + " ");
		}
		if (inRequestType.equals("deleteLot"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCTICK ");
			sqlString.append(" WHERE G4SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   ANd G4CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence().trim() + " ");
			sqlString.append("   AND G4SEQ# = " + fromVb.getScaleSequence() + " ");
//			sqlString.append("   AND G4PO# = " + fromVb.getPoNumber() + " ");
			sqlString.append("   AND G4TSEQ# = " + fromVb.getLotSequenceNumber() + " ");
			sqlString.append("   AND G4LOT = '" + fromVb.getLotNumber() + "' ");
		}
		
		if (inRequestType.equals("deleteAllLotsForScaleTicketSequence"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCTICK ");
			sqlString.append(" WHERE G4SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   ANd G4CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence().trim() + " ");
		}
		
		if (inRequestType.equals("deleteLotPayment"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCPMT ");
			sqlString.append(" WHERE G5SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   ANd G5CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence().trim() + " ");
			sqlString.append("   AND G5SEQ# = " + fromVb.getScaleSequence() + " ");
//			sqlString.append("   AND G5PO# = " + fromVb.getPoNumber() + " ");
			sqlString.append("   AND G5TSEQ# = " + fromVb.getLotSequenceNumber() + " ");
			sqlString.append("   AND G5LOT = '" + fromVb.getLotNumber() + "' ");
		}
		if (inRequestType.equals("deleteLotOtherCharges"))
		{
			// cast the incoming parameter class.
			UpdRawFruitLoad fromVb = (UpdRawFruitLoad) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCOTHC ");
			sqlString.append(" WHERE G6SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   ANd G6CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence().trim() + " ");
			sqlString.append("   AND G6SEQ# = " + fromVb.getScaleSequence() + " ");
//			sqlString.append("   AND G6PO# = " + fromVb.getPoNumber() + " ");
			sqlString.append("   AND G6TSEQ# = " + fromVb.getLotSequenceNumber() + " ");
			sqlString.append("   AND G6LOT = '" + fromVb.getLotNumber() + "' ");
		}
		if (inRequestType.equals("deleteLoadOtherCharges"))
		{
			// cast the incoming parameter class.
			InqRawFruit fromVb = (InqRawFruit) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCOTHC ");
			sqlString.append(" WHERE G6SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G6CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence().trim() + " ");
		}
		if (inRequestType.equals("deleteLoadPayment"))
		{
			// cast the incoming parameter class.
			InqRawFruit fromVb = (InqRawFruit) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCPMT ");
			sqlString.append(" WHERE G5SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G5CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence() + " ");
		}
		if (inRequestType.equals("deleteLoadLot"))
		{
			// cast the incoming parameter class.
			InqRawFruit fromVb = (InqRawFruit) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCTICK ");
			sqlString.append(" WHERE G4SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G4CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence() + " ");
		}
		if (inRequestType.equals("deleteLoadPO"))
		{
			// cast the incoming parameter class.
			InqRawFruit fromVb = (InqRawFruit) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCPO ");
			sqlString.append(" WHERE G3SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G3CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence() + " ");
		}
		if (inRequestType.equals("deleteLoadBin"))
		{
			// cast the incoming parameter class.
			InqRawFruit fromVb = (InqRawFruit) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCBINS ");
			sqlString.append(" WHERE G2SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G2CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence().trim() + " ");
		}	
		if (inRequestType.equals("deleteLoad"))
		{
			// cast the incoming parameter class.
			InqRawFruit fromVb = (InqRawFruit) requestClass.elementAt(0);
			// build the sql statement.
			sqlString.append("DELETE FROM " + ttlib + ".GRPCSCALE ");
			sqlString.append(" WHERE G1SCALE# = '" + fromVb.getScaleTicket().trim() + "' ");
			sqlString.append("   AND G1CSEQ1 = " + fromVb.getScaleTicketCorrectionSequence().trim() + " ");
		}	
	} catch (Exception e) {
			throwError.append(" Error building sql statement"
						 + " for request type " + inRequestType + ". " + e);
	}
	// return data.	
	if (!throwError.toString().trim().equals("")) {
		throwError.append("Error at com.treetop.services.ServiceRawFruit.");
		throwError.append("buildSqlStatement(String,Vector)");
		throw new Exception(throwError.toString());
	}
	return sqlString.toString();
}

/**
 *   Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector of Strings:
 * 					Variety
 * 					Conventional or Organic
 * 					Crop Description
 * 					Run Type
 * 					Category
 * 					Payment Type
 * 					Item
 *     IF the first element of the vector = 'grape' then will retrieve GRAPE PRICING DATA
 * @return RawFruitPayCode
 * @throws Exception
 */
public static RawFruitPayCode findPayCode(Vector inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	RawFruitPayCode returnValue = new RawFruitPayCode();
	Connection conn = null;
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		returnValue = findPayCode(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		//return connection.
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.findPayCode: " + e);
			throwError.append("Error closing connection. " + e);
		}
		
		// log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("findPayCode(");
			throwError.append("Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
 *    Method Created 10/15/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the RawFruitPayCode Object for use in the SQL statement
 * @return ItemWarehouse
 * @throws Exception
 */
private static RawFruitPayCode findPayCode(Vector inValues, 
										  Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	RawFruitPayCode returnValue = new RawFruitPayCode();
	ResultSet rs = null;
	Statement listThem = null;
	String requestType = "payCode";
	try
	{
		if (((String) inValues.elementAt(0)).equals("grape"))
			requestType = "grapePayCode"; // use file GRPJBPRC
		try {
		   listThem = conn.createStatement();
		   rs = listThem.executeQuery(buildSqlStatement(requestType, inValues));
		  
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }
		 
		 try
		 {
			 if (rs.next() && throwError.toString().equals(""))
			 {
				 returnValue = loadFieldsRawFruitPayCode(requestType, rs);
			 }// END of the IF Statement
		 } catch(Exception e) {
			throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
	   
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing result set (rs). " + e);
		}
	   
		try
		{
			if (listThem != null)
				listThem.close();
		} catch(Exception e){
			throwError.append("Error closing statement (listThem). " + e);
		}
	   
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("findPayCode(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
* Load class fields from result set.
*/
	public static RawFruitLot loadFieldsRawFruitLot(String requestType,
							          			    ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		RawFruitLot returnValue = new RawFruitLot();
	
		try{ 
			if (requestType.equals("lot"))
			{ 
				returnValue.setScaleTicketNumber(rs.getString("G4SCALE#").trim());
				returnValue.setScaleTicketCorrectionSequenceNumber(rs.getString("G4CSEQ1"));
				returnValue.setScaleSequence(rs.getString("G4SEQ#").trim());
				returnValue.setPoNumber(rs.getString("G4PO#").trim());
				returnValue.setPoLineNumber(rs.getString("G4PLIN#").trim());
				returnValue.setReceivingDate(rs.getString("G4RDATE").trim());
				returnValue.setLotNumber(rs.getString("G4LOT").trim());
				returnValue.setLotSequence(rs.getString("G4TSEQ#").trim());
				returnValue.setHarvestYear(rs.getString("G4HYEAR").trim());
				returnValue.setSupplierLoadNumber(rs.getString("G4SLOAD").trim());
				returnValue.setCrop(rs.getString("G4CROP").trim());
				returnValue.setOrganicConventional(rs.getString("G4CNVOR").trim());
				returnValue.setRunType(rs.getString("G4RUNT").trim());
				returnValue.setIntendedUse(rs.getString("G4IUSE").trim());
				returnValue.setVariety(rs.getString("G4VAR").trim());
				returnValue.setAdditionalVariety(rs.getString("G4AVAR").trim());
				returnValue.setCountryOfOrigin(rs.getString("G4CTYO").trim());
				returnValue.setCullsPercent(rs.getString("G4CULLP").trim());
				returnValue.setCullsPounds(rs.getString("G4CULLW").trim());
				returnValue.setCalculatedBrixPrice(rs.getString("G4BRIXP").trim());
				returnValue.setLotAcceptedBins25Box(rs.getString("G4A25B").trim());
				returnValue.setLotAcceptedBins30Box(rs.getString("G4A30B").trim());
				returnValue.setLotAcceptedComments(rs.getString("G4ABINC").trim());
				returnValue.setLotAcceptedWeight(rs.getString("G4ABW").trim());
				returnValue.setFlagAcceptedWeightManual(rs.getString("G4ABWO").trim());
				returnValue.setLotRejectedBins25Box(rs.getString("G4R25B").trim());
				returnValue.setLotRejectedBins30Box(rs.getString("G4R30B").trim());
				returnValue.setLotRejectedComments(rs.getString("G4RBINC").trim());
				returnValue.setLotRejectedWeight(rs.getString("G4RBW").trim());
				returnValue.setFlagRejectedWeightManual(rs.getString("G4RBWO").trim());
				returnValue.setLotTotalBins25Box(rs.getString("G4T25B").trim());
				returnValue.setLotTotalBins30Box(rs.getString("G4T30B").trim());
				returnValue.setLotTotalWeight(rs.getString("G4TBW").trim());
				returnValue.setAverageWeightPerBin(rs.getString("G4AVGW").trim());
				returnValue.setLotUser(rs.getString("G4USER").trim());
				returnValue.setLotDate(rs.getString("G4DATE").trim());
				returnValue.setLotTime(rs.getString("G4TIME").trim());
				returnValue.setLotWriteUpNumber(rs.getString("G4WUP").trim());
				
				returnValue.setLotInformation(ServiceLot.loadFieldsM3Lot(rs, "rfEntryLot"));
				// Includes
							// Facility:  	G4FACI
							// Warehouse: 	G4WHSE
							// Lot:			G4LOT
							// Location:	G4LOC
							// Item:		G4ITEM
							// Item Desc: 	MMITDS
							// Attribute Model: MMATMO
							// Organic:		MMEVGR
							// Brix:		G4BRIX
				returnValue.setLotSupplier(ServiceSupplier.loadFieldsSupplier("rfEntryLot", rs));
				returnValue.setWarehouseFacility(ServiceWarehouse.loadFieldsWarehouse("rfEntryLot", rs));
				try
				{
				  returnValue.setCorrectionCount(rs.getString("G1CCNT"));	
				}
				catch(Exception e)
				{
					// Could not find the Scale Information
				}
				try
				{
				   returnValue.setGrapeDueDate(rs.getString("GRSCVR1").trim());
				}
				catch(Exception e)
				{
					// could not find Grape Due Date
				}
				
			}
			if (requestType.equals("lotCalc"))
			{
				returnValue.setScaleTicketNumber(rs.getString("G1SCALE#").trim());
				returnValue.setScaleTicketCorrectionSequenceNumber(rs.getString("G1CSEQ1").trim());
				returnValue.setScaleSequence(rs.getString("G4SEQ#").trim());
				returnValue.setLotNumber(rs.getString("G4LOT").trim());
				returnValue.setLotSequence(rs.getString("G4TSEQ#").trim());
				returnValue.setLotAcceptedBins25Box(rs.getString("G4A25B").trim());
				returnValue.setLotAcceptedBins30Box(rs.getString("G4A30B").trim());
				returnValue.setLotAcceptedWeight(rs.getString("G4ABW").trim());
				returnValue.setFlagAcceptedWeightManual(rs.getString("G4ABWO").trim());
				returnValue.setLotRejectedBins25Box(rs.getString("G4R25B").trim());
				returnValue.setLotRejectedBins30Box(rs.getString("G4R30B").trim());
				returnValue.setLotRejectedWeight(rs.getString("G4RBW").trim());
				returnValue.setFlagRejectedWeightManual(rs.getString("G4RBWO").trim());
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the RawFruitLot class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("loadFieldsRawFruitLot(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

/**
 *   Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle -
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				payCode - Means it will use the file, GRPKCODE to retrieve Data
 */
public static Vector dropDownCrop(Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		ddit = dropDownCrop(inValues,conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.dropDownCrop: " + e);
			throwError.append("Error closing connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownCrop(Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	// return value
	return ddit;
}

/**
 * Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 */
private static Vector dropDownCrop(Vector inValues, 
								   Connection conn)
	throws Exception
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	Statement listThem = null;
	StringBuffer throwError = new StringBuffer();
	
	try
	{
		try {
			// Get the list of Item Type Values - Along with Descriptions
			listThem = conn.createStatement();
			rs = listThem.executeQuery(buildSqlStatement(((String) inValues.elementAt(0) + "Crop"), inValues));
		} catch(Exception e) {
		   	throwError.append("Error occured Retrieving or Executing sql (Crop). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next() && throwError.toString().equals(""))
				{
					DropDownSingle dds = loadFieldsDropDownSingle(((String) inValues.elementAt(0) + "Crop"), rs);
					ddit.addElement(dds);
				}
			} catch(Exception e) {
				throwError.append(" Error occured while Building Vector from sql (Crop). " + e);
			}
		}
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		//verify result sets closed.
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing result set (rs). " + e);
		}
		
		//verify statements closed.
		try
		{
			if (listThem != null)
				listThem.close();
		} catch(Exception e){
			throwError.append("Error closing statement (listThem). " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownCrop(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return ddit;
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
	  if (requestType.equals("payCodeVariety"))
	  {
		returnValue.setValue(rs.getString("GRKVTY").trim());
		returnValue.setDescription(rs.getString("GRKVTY").trim());
	  }
	  if (requestType.equals("payCodeConvOrganic"))
	  {
	  	returnValue.setValue(rs.getString("GRKCNVOR").trim());
		returnValue.setDescription(rs.getString("GRKCNVOR").trim());
	  }
	  if (requestType.equals("payCodeCrop"))
	  {
	  	returnValue.setValue(rs.getString("GRKCROP").trim());
		returnValue.setDescription(rs.getString("GRKCROP").trim());
	  }
	  if (requestType.equals("payCodeMachineOrchard"))
	  {
	  	returnValue.setValue(rs.getString("GRKRUN").trim());
		returnValue.setDescription(rs.getString("GRKRUN").trim());
	  }
	  if (requestType.equals("payCodeCategory"))
	  {
	  	returnValue.setValue(rs.getString("GRKCAT").trim());
		returnValue.setDescription(rs.getString("GRKCAT").trim());
	  }
	  if (requestType.equals("payCodePaymentType"))
	  {
	  	returnValue.setValue(rs.getString("GRKPTP").trim());
		returnValue.setDescription(rs.getString("GRKPTP").trim());
	  }
	  if (requestType.equals("bins"))
	  {
		  returnValue.setValue(rs.getString("GRATYP").trim());
		  returnValue.setDescription(rs.getString("GRADSC").trim());
	  }
	  if (requestType.equals("ddHandlingCode"))
	  {
		  returnValue.setValue(rs.getString("GNAD20").trim());
		  returnValue.setDescription(rs.getString("GNAD20").trim());
	  }
	  if (requestType.equals("grapeBrix"))
	  {
		  returnValue.setValue(rs.getString("GBJBRIX").trim());
		  returnValue.setDescription(rs.getString("GBJBRIX").trim());
	  }
	} catch(Exception e)
	{
		throwError.append(" Problem loading the Drop Down Single class ");
		throwError.append("from the result set. " + e) ;
	}
	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceRawFruit.");
		throwError.append("loadFieldsDropDownSingle(requestType, rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}
	return returnValue;
}

/**
 *   Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				payCode - Means it will use the file, GRPKCODE to retrieve Data
 */
public static Vector dropDownConvOrganic(Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		ddit = dropDownConvOrganic(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.dropDownConvOrganic: " + e);
			throwError.append("Error closing connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownConvOrganic(Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	// return value
	return ddit;
}

/**
 * Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownConvOrganic(Vector inValues, 
									   	   Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector ddit = new Vector();
	ResultSet rs = null;
	Statement listThem = null;
	
	try
	{
		try {// Get the list of Item Type Values - Along with Descriptions
			listThem = conn.createStatement();
			rs = listThem.executeQuery(buildSqlStatement(((String) inValues.elementAt(0) + "ConvOrganic"), inValues));
		} catch(Exception e) {
		   	throwError.append("Error occured Retrieving or Executing sql (ConvOrganic). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next() && throwError.toString().equals(""))
				{
					DropDownSingle dds = loadFieldsDropDownSingle(((String) inValues.elementAt(0) + "ConvOrganic"), rs);
					ddit.addElement(dds);
				}
			} catch(Exception e) {
				throwError.append(" Error occured while Building Vector from sql (ConvOrganic). " + e);
			}
		}
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		//verify result sets closed.
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing result set (rs)" + e);
		}
		
		//verify statements closed.
		try
		{
			if (listThem != null)
				listThem.close();
		} catch(Exception e){
			throwError.append("Error closing statement (listThem). " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownConvOrganic(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return ddit;
}

/**
 *   Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				payCode - Means it will use the file, GRPKCODE to retrieve Data
 */
public static Vector dropDownVariety(Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		ddit = dropDownVariety(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		//close connection.
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.dropDownVariety: " + e);
			throwError.append("Error closing connection. " + e);
		}
			
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownVariety(Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
		
	}
	// return value
	return ddit;
}

/**
 * Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownMachineOrchard(Vector inValues, 
									         Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector ddit = new Vector();
	ResultSet rs = null;
	Statement listThem = null;
	
	try
	{
		try {
			// Get the list of Item Type Values - Along with Descriptions
			listThem = conn.createStatement();
			rs = listThem.executeQuery(buildSqlStatement(((String) inValues.elementAt(0) + "MachineOrchard"), inValues));
		} catch(Exception e) {
		   	throwError.append("Error occured Retrieving or Executing sql (MachineOrchard). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next() && throwError.toString().equals(""))
				{
					DropDownSingle dds = loadFieldsDropDownSingle(((String) inValues.elementAt(0) + "MachineOrchard"), rs);
					ddit.addElement(dds);
				}
			} catch(Exception e) {
				throwError.append(" Error occured while Building Vector from sql (Machine Orchard). " + e);
			}
		}
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		//verify result sets closed.
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing result set (rs). " + e);
		}
		
		//verify statements closed.
		try
		{
			if (listThem != null)
				listThem.close();
		} catch(Exception e){
			throwError.append("Error closing statement (listThem). " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownMachineOrchard(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return ddit;
}

/**
 *   Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				payCode - Means it will use the file, GRPKCODE to retrieve Data
 */
public static Vector dropDownMachineOrchard(Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		ddit = dropDownMachineOrchard(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.dropDownMachineOrchard: " + e);
			throwError.append("Error closing connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownMachineOrchard(Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	// return value
	return ddit;
}

/**
 * Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownVariety(Vector inValues, 
									  Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector ddit = new Vector();
	ResultSet rs = null;
	Statement listThem = null;
	
	try
	{
		try {
			// Get the list of Item Type Values - Along with Descriptions
			listThem = conn.createStatement();
			rs = listThem.executeQuery(buildSqlStatement(((String) inValues.elementAt(0) + "Variety"), inValues));
		} catch(Exception e) {
		   	throwError.append("Error occured Retrieving or Executing sql (Variety). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next() && throwError.toString().equals(""))
				{
					DropDownSingle dds = loadFieldsDropDownSingle(((String) inValues.elementAt(0) + "Variety"), rs);
					ddit.addElement(dds);
				}
			} catch(Exception e) {
				throwError.append(" Error occured while Building Vector from sql (Variety). " + e);
			}
		}
		
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		//verify result sets are closed.
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing result set (rs). " + e);
		}
		
		//verify statements are closed.
		try
		{
			if (listThem != null)
				listThem.close();
		} catch(Exception e) {
			throwError.append("Error closing statement (listThem). " + e);
		}
	   
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownVariety(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return ddit;
}

/**
 *   Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				payCode - Means it will use the file, GRPKCODE to retrieve Data
 */
public static Vector dropDownCategory(Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();
	
	try { // get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		ddit = dropDownCategory(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.dropDownCategory: " + e);
			throwError.append("Error retunring connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownCategory(Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	// return value
	return ddit;
}

/**
 * Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownCategory(Vector inValues, 
									   Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector ddit = new Vector();
	ResultSet rs = null;
	Statement listThem = null;
	
	try
	{
		try { // Get the list of Item Type Values - Along with Descriptions
			listThem = conn.createStatement();
			rs = listThem.executeQuery(buildSqlStatement(((String) inValues.elementAt(0) + "Category"), inValues));
		} catch(Exception e) {
			throwError.append("Error occured Retrieving or Executing sql (Category). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next() && throwError.toString().equals(""))
				{
					DropDownSingle dds = loadFieldsDropDownSingle(((String) inValues.elementAt(0) + "Category"), rs);
					ddit.addElement(dds);
				}
			} catch(Exception e)
			{
				throwError.append(" Error occured while Building Vector from sql (Category). " + e);
			}
		}
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		
		//close result sets / statements.
		try
		{
			if (rs != null)
				rs.close();
			
			if (listThem != null)
				listThem.close();
			
		} catch(Exception e){
			throwError.append("Error closing result set / statement." + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownCategory(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return ddit;
}

/**
 *   Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				payCode - Means it will use the file, GRPKCODE to retrieve Data
 */
public static Vector dropDownPaymentType(Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		ddit = dropDownPaymentType(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		//return connection.
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.dropDownPayment: " + e);
			throwError.append("Error closing connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownPaymentType(Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	// return value
	return ddit;
}

/**
 * Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownPaymentType(Vector inValues, 
									      Connection conn)
	throws Exception
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	Statement listThem = null;
	StringBuffer throwError = new StringBuffer();
	
	try
	{
		try {
			// Get the list of Item Type Values - Along with Descriptions
			listThem = conn.createStatement();
			rs = listThem.executeQuery(buildSqlStatement(((String) inValues.elementAt(0) + "PaymentType"), inValues));
		} catch(Exception e) {
		   	throwError.append("Error occured Retrieving or Executing sql (PaymentType). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next() && throwError.toString().equals(""))
				{
					DropDownSingle dds = loadFieldsDropDownSingle(((String) inValues.elementAt(0) + "PaymentType"), rs);
					ddit.addElement(dds);
				}
			} catch(Exception e) {
				throwError.append(" Error occured while Building Vector from sql (PaymentType). " + e);
			}
		}
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		//close result sets.
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing result set (rs). " + e);
		}
		
		//close statements.
		try
		{
			if (listThem != null)
				listThem.close();
		} catch(Exception e){
			throwError.append("Error closing statement (listThem). " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownPaymentType(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return ddit;
}

/**
 * Use to Validate a Sent in Bin Type
 *    // Check in file GRPABNMS
 */
public static String verifyBinType(String binTypeCode)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	StringBuffer rtnProblem = new StringBuffer();
	Connection conn = null; // New Lawson Box - Lawson Database
	Statement findIt = null;
	ResultSet rs = null;
	
	try { 
		String requestType = "verifyBinType";
		String sqlString = "";
			
		// verify incoming Specification
		if (binTypeCode == null || binTypeCode.trim().equals(""))
			rtnProblem.append(" Bin Type must not be null or empty.");
		
		if (throwError.toString().equals(""))
		{
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(binTypeCode);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
				rtnProblem.append(" Problem when building Test for Bin Type Code: ");
				rtnProblem.append(binTypeCode + " PrintScreen this message and send to Information Services. ");
			}
		}
		
		// get a connection. execute sql, build return object.
		if (rtnProblem.toString().equals("")) {
			try {
				conn = ConnectionStack3.getConnection();
				findIt = conn.createStatement();
				rs = findIt.executeQuery(sqlString);
					
				if (rs.next() && throwError.toString().equals(""))
				{
					// it exists and all is good.
				} else {
					rtnProblem.append(" Bin Type Code '" + binTypeCode + "' ");
					rtnProblem.append("does not exist. ");
				}
					
			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. " + e);
				rtnProblem.append(" Problem when finding Bin Type Code: ");
				rtnProblem.append(binTypeCode + " PrintScreen this message and send to Information Services. ");
			}
		}
	} catch(Exception e)
	{
		throwError.append(" Problem verifying the Bin Type Code; " + binTypeCode);
	// return connection.
	} finally {
		try {
			if (findIt != null)
				findIt.close();
		} catch (Exception e) {
			throwError.append("Error closing connection/result set/statement. " + e);
		}
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			throwError.append("Error closing connection/result set/statement. " + e);
		}
		try {
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch (Exception e) {
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.verifyBinType: " + e);
			throwError.append("Error closing connection/result set/statement. " + e);
		}
		
		//Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("verifyBinType(String: binTypeCode)");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}

	// return data.
	return rtnProblem.toString();
}

/**
* Load class fields from result set.
*/
	
	public static RawFruitLoad loadFieldsRawFruitLoad(String requestType,
							          					 ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		RawFruitLoad returnValue = new RawFruitLoad();
	
		try{ 
			if (requestType.equals("load"))
			{
				 returnValue.setScaleTicketNumber(rs.getString("G1SCALE#").trim());
				 returnValue.setScaleTicketCorrectionSequenceNumber(rs.getString("G1CSEQ1").trim());
				 returnValue.setReceivingDate(rs.getString("G1RDATE").trim());
				 returnValue.setReceivingTime(rs.getString("G1RTIME"));
				 returnValue.setLoadType(rs.getString("G1LOADT").trim());
				 returnValue.setLoadGrossWeight(rs.getString("G1GRWGT").trim());
				 returnValue.setLoadTareWeight(rs.getString("G1LWGT").trim());
				 returnValue.setLoadNetWeight(rs.getString("G1NLWGT").trim());
				 returnValue.setBinTareWeight(rs.getString("G1BWGT").trim());
				 returnValue.setLoadFruitNetWeight(rs.getString("G1AFWGT").trim());
				 returnValue.setLoadFullBins(rs.getString("G1FBINS").trim());
				 returnValue.setLoadEmptyBins(rs.getString("G1EBINS").trim());
				 returnValue.setLoadTotalBins(rs.getString("G1TBINS").trim());
				 returnValue.setLoadTotalBoxes(rs.getString("G1TBOXES").trim());
				 returnValue.setLoadAveWeightPerBox(rs.getString("G1LBSBX").trim());
				 returnValue.setFlagBulkBin(rs.getString("G1BNBLK").trim());
				 returnValue.setCarrierBOL(rs.getString("G1CBOL").trim());
				 returnValue.setFreightRate(rs.getString("G1FRATE").trim());
				 returnValue.setFlatRateFreightFlag(rs.getString("G1FRTFL").trim());
				 returnValue.setFreightSurcharge(rs.getString("G1FSCHG").trim());
				 if (rs.getString("DCPT80") != null &&
					 !rs.getString("DCPT80").trim().equals(""))
				 {
					returnValue.setFromLocationLong(rs.getString("DCPT80").trim()); 
				 }
				 returnValue.setFromLocation(rs.getString("G1FRLOC").trim());
				 returnValue.setWhseTicket(rs.getString("G1WHTIK").trim());
				 returnValue.setMinimumWeightFlag(rs.getString("G1MIMW").trim());
				 returnValue.setMinimumWeightValue(rs.getString("G1MNLB").trim());
				 returnValue.setLoadComplete(rs.getString("G1COMP").trim());
				 returnValue.setFreightRateCode(rs.getString("G1FTCCD").trim());
				 returnValue.setFreightSurchargeCode(rs.getString("G1FLCCD").trim());
				 returnValue.setCorrectionCount(rs.getString("G1CCNT").trim());
				 returnValue.setLoadUser(rs.getString("G1USER").trim());
				 returnValue.setLoadDate(rs.getString("G1DATE").trim());
				 returnValue.setLoadTime(rs.getString("G1TIME").trim());
				 returnValue.setM3FreightVoucher(rs.getString("G1VONO").trim());
				 returnValue.setM3FreightJournal(rs.getString("G1JRNO").trim());
				 returnValue.setM3FreightJournalSeq(rs.getString("G1JSNO").trim());
				 returnValue.setPostingFlags(rs.getString("G1POSTF").trim());
				 returnValue.setHandlingCode(rs.getString("G1HCOD").trim());
				 returnValue.setDim5(rs.getString("G1DIM5").trim());
				 returnValue.setCostCenter(rs.getString("G1CCTR").trim());
				 returnValue.setScheduledLoadNumber(rs.getString("G1TRACK").trim());
				 returnValue.setInspectedBy(rs.getString("G1INSP").trim());
				 
				 returnValue.setCarrier(ServiceSupplier.loadFieldsSupplier("rfLoad", rs)); // Make sure to use field #57
				 returnValue.setWarehouseFacility(ServiceWarehouse.loadFieldsWarehouse("rfLoad", rs)); // Make sure to use fields 60-61 and 64-65
			}
			if (requestType.equals("listLoads"))
			{
				 returnValue.setScaleTicketNumber(rs.getString("G1SCALE#").trim());
				 returnValue.setScaleTicketCorrectionSequenceNumber(rs.getString("G1CSEQ1").trim());
				 returnValue.setReceivingDate(rs.getString("G1RDATE").trim());
				 //returnValue.setLoadType(rs.getString("G1LOADT").trim());
				 //returnValue.setLoadGrossWeight(rs.getString("G1GRWGT").trim());
				 //returnValue.setLoadTareWeight(rs.getString("G1LWGT").trim());
				 returnValue.setLoadNetWeight(rs.getString("G1NLWGT").trim());
				 //returnValue.setBinTareWeight(rs.getString("G1BWGT").trim());
				 returnValue.setLoadFruitNetWeight(rs.getString("G1AFWGT").trim());
				 returnValue.setLoadFullBins(rs.getString("G1FBINS").trim());
				 //returnValue.setLoadEmptyBins(rs.getString("G1EBINS").trim());
				 //returnValue.setLoadTotalBins(rs.getString("G1TBINS").trim());
				 //returnValue.setLoadTotalBoxes(rs.getString("G1TBOXES").trim());
				 //returnValue.setLoadAveWeightPerBox(rs.getString("G1LBSBX").trim());
				 returnValue.setFlagBulkBin(rs.getString("G1BNBLK").trim());
				 returnValue.setCarrierBOL(rs.getString("G1CBOL").trim());
				 //returnValue.setFreightRate(rs.getString("G1FRATE").trim());
				 //returnValue.setFlatRateFreightFlag(rs.getString("G1FRTFL").trim());
				 //returnValue.setFreightSurcharge(rs.getString("G1FSCHG").trim());
				 returnValue.setFromLocation(rs.getString("G1FRLOC").trim());
				 if (rs.getString("DCPT80") != null &&
					 !rs.getString("DCPT80").trim().equals(""))
				 {
						returnValue.setFromLocationLong(rs.getString("DCPT80").trim()); 
				 }
				 returnValue.setWhseTicket(rs.getString("G1WHTIK").trim());
				 //returnValue.setMinimumWeightFlag(rs.getString("G1MIMW").trim());
				 //returnValue.setMinimumWeightValue(rs.getString("G1MNLB").trim());
				 //returnValue.setLoadComplete(rs.getString("G1COMP").trim());
				 //returnValue.setFreightRateCode(rs.getString("G1FTCCD").trim());
				 //returnValue.setFreightSurchargeCode(rs.getString("G1FLCCD").trim());
				 //returnValue.setLoadUser(rs.getString("G1USER").trim());
				 //returnValue.setLoadDate(rs.getString("G1DATE").trim());
				 //returnValue.setLoadTime(rs.getString("G1TIME").trim());
				 //returnValue.setM3FreightVoucher(rs.getString("G1VONO").trim());
				 //returnValue.setM3FreightJournal(rs.getString("G1JRNO").trim());
				 //returnValue.setM3FreightJournalSeq(rs.getString("G1JSNO").trim());
				 //returnValue.setPostingFlags(rs.getString("G1POSTF").trim());
				 
				 returnValue.setCarrier(ServiceSupplier.loadFieldsSupplier("basic", rs)); 
				 returnValue.setWarehouseFacility(ServiceWarehouse.loadFieldsWarehouse("basicWarehouse", rs));
			}
			if (requestType.equals("handlingCode"))
			{
				returnValue.setHandlingCode(rs.getString("GNAD20").trim());
				returnValue.setDim5(rs.getString("GNADM5").trim());
				returnValue.setCostCenter(rs.getString("GNADM3").trim());
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the RawFruitLoad class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("loadFieldsRawFruitLoad(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

/**
* Load class fields from result set.
*/
	
	public static RawFruitBin loadFieldsRawFruitBin(String requestType,
							          			    ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		RawFruitBin returnValue = new RawFruitBin();
	
		try{ 
			if (requestType.equals("bin"))
			{
				 returnValue.setScaleTicketNumber(rs.getString("G2SCALE#").trim());
				 returnValue.setBinType(rs.getString("G2BINTYP").trim());
				 returnValue.setBinDescription(rs.getString("G2BINDSC").trim());
				 returnValue.setBinWeight(rs.getString("G2BINWGT").trim());
				 returnValue.setBinQuantity(rs.getString("G2BINCNT").trim());
				 returnValue.setBinTotalWeight(rs.getString("G2TWBIN").trim());
				 returnValue.setBinUser(rs.getString("G2USER").trim());
				 returnValue.setBinDate(rs.getString("G2DATE").trim());
				 returnValue.setBinTime(rs.getString("G2TIME").trim());
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the RawFruitBin class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("loadFieldsRawFruitBin(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

/**
* Load class fields from result set.
*/
	
	public static RawFruitPO loadFieldsRawFruitPO(String requestType,
							         		      ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		RawFruitPO returnValue = new RawFruitPO();
	
		try{ 
			if (requestType.equals("po") &&
				rs.getString("G3SCALE#") != null)
			{
				 returnValue.setScaleTicketNumber(rs.getString("G3SCALE#").trim());
				 returnValue.setScaleTicketCorrectionSequenceNumber(rs.getString("G3CSEQ1"));
				 returnValue.setScaleSequence(rs.getString("G3SEQ#").trim());
				 returnValue.setPoNumber(rs.getString("G3PO#").trim());
				 returnValue.setPoLineNumber(rs.getString("G3PLIN#").trim());
				 returnValue.setPoCreate(rs.getString("G3CPO").trim());
				 returnValue.setPoAcceptedWeight(rs.getString("G3AWGT").trim());
				 returnValue.setPoRejectedWeight(rs.getString("G3RWGT").trim());
				 returnValue.setPoTotalWeight(rs.getString("G3TWGT").trim());
				 returnValue.setPoAcceptedBins(rs.getString("G3ABINS").trim());
				 returnValue.setPoRejectedBins(rs.getString("G3RBINS").trim());
				 returnValue.setPoTotalBins(rs.getString("G3TBINS").trim());
				 returnValue.setPoComplete(rs.getString("G3COMP").trim());
				 returnValue.setPoUser(rs.getString("G3USER").trim());
				 returnValue.setPoDate(rs.getString("G3DATE").trim());
				 returnValue.setPoTime(rs.getString("G3TIME").trim());
				 
				 returnValue.setPoSupplier(ServiceSupplier.loadFieldsSupplier("rfPO", rs));
				 returnValue.setWarehouseFacility(ServiceWarehouse.loadFieldsWarehouse("rfPO", rs));
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the RawFruitPO class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("loadFieldsRawFruitPO(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

/**
 *   Method Created 11/13/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the 
 *              Vector which includes 2 Strings
 *              1.  Scale Ticket Number
 *              2.  Scale Ticket Correction Sequence Number
 * @return BeanRawFruit
 * @throws Exception
 */
public static BeanRawFruit findLoad(Vector inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	BeanRawFruit returnValue = new BeanRawFruit();
	Connection conn = null;
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		returnValue = findLoad(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		//close connection.
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.findLoad: " + e);
			throwError.append("Error closing connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("findLoad(");
			throwError.append("Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
 *    Method Created 11/13/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the 
 *              Vector which includes 2 Strings
 *              1.  Scale Ticket Number
 *              2.  Scale Ticket Correction Sequence Number
 * @return BeanRawFruit
 * @throws Exception
 */
private static BeanRawFruit findLoad(Vector inValues, 
									 Connection conn)
throws Exception
{
	// Load All information Relating to the LOAD -- Scale Ticket Number
	StringBuffer throwError = new StringBuffer();
	BeanRawFruit returnValue = new BeanRawFruit();
	ResultSet rs = null; // FOR LOAD
	ResultSet rsBin = null;
	ResultSet rsLot = null;
	Statement listThem = null; // FOR LOAD
	Statement listBins = null;
	Statement listLots = null;
	StringBuffer itemWhseError = new StringBuffer();
	
	try
	{
		updatePONumber(inValues, conn);
		try {
			// Get the Load Related Information -- This SQL will retrieve LOAD and PO Information	   
			// Bin, and Lot Information will Have to be Done separately
			listThem = conn.createStatement();
			// 9/16/13 - TWalton adjust to use CommonRequestBean and BuildSQL
			CommonRequestBean crb = new CommonRequestBean();
			crb.setIdLevel1((String) inValues.elementAt(0));
			crb.setIdLevel2((String) inValues.elementAt(1));
			//rs = listThem.executeQuery(buildSqlStatement("findLoad", inValues));
			rs = listThem.executeQuery(BuildSQL.findLoad(crb));
			
		} catch(Exception e)
		{
			throwError.append("Error occured Retrieving or Executing sql (findLoad). " + e);
		}
		
		try
		{
			RawFruitLoad rfl = new RawFruitLoad();
		 	int first = 0;
		 	Vector getPO = new Vector();
		 	while (rs.next() && throwError.toString().equals(""))
		    {
		 		if (first == 0)
		 		{ // ONLY NEED FIRST TIME THROUGH
		 			//-----------------------------------------------------------------------------------------------	
		 			//Build the MAIN Load Information
		 			rfl = loadFieldsRawFruitLoad("load", rs);
		 			returnValue.setRfLoad(rfl);
		 			
		 			// Build the BIN Information Relating to the Load
		 			Vector getBins = new Vector();
		 			
		 			try
					{
		 				try {
		 				   // Get the Bin Related Information -- This SQL will retrieve only BIN Information directly related to a LOAD
		 					Vector sendInfo = new Vector();
		 					sendInfo.addElement(rfl.getScaleTicketNumber());
		 					sendInfo.addElement(rfl.getScaleTicketCorrectionSequenceNumber());
		 				    listBins = conn.createStatement();
		 				    rsBin = listBins.executeQuery(buildSqlStatement("listBins", sendInfo));
		 				    
		 				 } catch(Exception e) {
		 					 throwError.append("Error occured Retrieving or Executing sql (ServiceRawFruit.findLoad()). BIN: " + e);
		 				 }
		 				 
		 				 try {
		 					 while (rsBin.next() && throwError.toString().equals(""))
		 					 {
		 						 //Build the BIN Information - for Specific LOAD
		 						 getBins.addElement(loadFieldsRawFruitBin("bin", rsBin));
		 					 }
		 				 } catch(Exception e) {
		 					 throwError.append(" Error occured while Building Vector from sql statement(ServiceRawFruit.findLoad()). BIN: " + e);
						 }
					}
		 			catch(Exception eBin)
					{
		 				System.out.println("Error Found Retrieving Bins: " + eBin);
		 				// Just Catch the Error, display if problem needs debugged
		 			}
		 			
		 			rsBin.close();
		 			listBins.close();
		 			
		 			rfl.setListBins(getBins);
		 			returnValue.setRfLoad(rfl);
		 			returnValue.setListRFBin(getBins);
		 		} // end of the FIRST RECORD SECTION
		 		
		 		
		 		//---------------------------------------------------------------------------------------------------------
		 		first++;
		 		// Load Up the PO Information
		 		if (rs.getString("G3SCALE#") != null && throwError.toString().equals(""))
		 		{
		 			RawFruitPO onePO = loadFieldsRawFruitPO("po", rs);
		 			// Build the LOT Information Relating to the PO
		 			Vector getLots = new Vector();
		 			
		 			try
		 			{
		 				if (!onePO.getScaleSequence().equals("") &&
		 					!onePO.getPoNumber().equals(""))
		 				{
		 					Vector sendInfo = new Vector();
		 					sendInfo.addElement(rfl.getScaleTicketNumber());
		 					sendInfo.addElement(rfl.getScaleTicketCorrectionSequenceNumber());
		 					sendInfo.addElement(onePO.getScaleSequence());
		 					sendInfo.addElement(onePO.getPoNumber());
		 					
		 					// Go get the List of Lots Assigned to the PO
		 					try {
		 						// Get the Bin Related Information -- This SQL will retrieve only BIN Information directly related to a LOAD
		 						listLots = conn.createStatement();
		 						rsLot = listLots.executeQuery(buildSqlStatement("listLots", sendInfo));
		 						
		 					} catch(Exception e) {
		 						throwError.append("Error occured Retrieving or Executing a sql statement(ServiceRawFruit.findLoad()). LOT: " + e);
		 					}
		 					
		 					try {
		 						while (rsLot.next() && throwError.toString().equals(""))
		 						{
//		 							System.out.println(rsLot.getString("G4LOT"));
		 							Vector sendInfoForLot = new Vector();
		 							sendInfoForLot.addElement(sendInfo.elementAt(0));
		 							sendInfoForLot.addElement(sendInfo.elementAt(1));
		 							sendInfoForLot.addElement(sendInfo.elementAt(2));
		 							sendInfoForLot.addElement(sendInfo.elementAt(3));
		 							sendInfoForLot.addElement(rsLot.getString("G4TSEQ#"));
		 							sendInfoForLot.addElement(rsLot.getString("G4LOT").trim());
		 							//Build the LOT Information - for Specific LOAD
		 							BeanRawFruit brf = findLot(sendInfoForLot, conn);
		 							if (!brf.getRfLot().getItemWhseNotValid().trim().equals(""))
		 							{
		 								itemWhseError.append(brf.getRfLot().getItemWhseNotValid() + "::");
		 							}
		 							getLots.addElement(brf.getRfLot());
		 						}
		 					} catch(Exception e) {
		 						throwError.append(" Error occured while Building Vector from sql statement(ServiceRawFruit.findLoad()). LOT rs: " + e);
		 					}
		 					
		 					onePO.setListLots(getLots);
		 				}
		 			}
		 			catch(Exception eLot) {
		 				throwError.append("Error Found Retrieving Lot Information: " + eLot);
		 			}
	 			
		 			rsLot.close();
		 			listLots.close();
		 			getPO.addElement(onePO);
		 		}
		    }// END of the WHILE Statement
		 	rfl.setListPOs(getPO);
		 	rfl.setMissingItemWhse(itemWhseError.toString());
 			returnValue.setRfLoad(rfl);
 			returnValue.setListRFPO(getPO);
		} catch(Exception e) {
			throwError.append(" Error occured while Building Vector from sql statement. " + e);
		}
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		//close result sets.
		try
		{
			if (rs != null)
				rs.close();
			
			if (rsBin != null)
				rsBin.close();
			
			if (rsLot != null)
				rsLot.close();
		} catch(Exception e) {
			throwError.append("Error closing result set. " + e);
		}
		
		//close statements.
		try
		{
			if (listThem != null)
				listThem.close();
			
			if (listBins != null)
				listBins.close();
			
			if (listLots != null)
				listLots.close();
		} catch(Exception e ) {
			throwError.append("Error closing statements. ");
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("findLoad(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
* Load class fields from result set.
*/
	public static RawFruitPayment loadFieldsRawFruitPayment(String requestType,
							          			    	ResultSet rs)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		RawFruitPayment returnValue = new RawFruitPayment();
	
		try{ 
			if (requestType.equals("payment"))
			{
				returnValue.setScaleTicketNumber(rs.getString("G5SCALE#").trim());
				returnValue.setScaleTicketCorrectionSequenceNumber(rs.getString("G5CSEQ1"));
				returnValue.setScaleSequence(rs.getString("G5SEQ#").trim());
				returnValue.setPoNumber(rs.getString("G5PO#").trim());
				returnValue.setPoLineNumber(rs.getString("G5PLIN#").trim());
				returnValue.setLotNumber(rs.getString("G5LOT").trim());
				returnValue.setLotSequence(rs.getString("G5TSEQ#").trim());
				returnValue.setPaymentSequenceNumber(rs.getString("G5YSEQ#").trim());
				returnValue.setPaymentBins25Box(rs.getString("G525B").trim());
				returnValue.setPaymentBins30Box(rs.getString("G530B").trim());
				returnValue.setPaymentWeight(rs.getString("G5TBW").trim());
				returnValue.setPaymentWeightHandKeyed(rs.getString("G5TBWO").trim());
				returnValue.setPayCodeHandKeyed(rs.getString("G5M3PCD").trim());
				returnValue.setCmvFruitPrice(rs.getString("G5CMV").trim());
				returnValue.setFruitPrice(rs.getString("G5PRICE").trim());
				returnValue.setFruitPriceHandKeyed(rs.getString("G5PRICEM").trim());
				returnValue.setFruitPriceAdjustment(rs.getString("G5ADJP").trim());
				returnValue.setPaymentUser(rs.getString("G5USER"));
				returnValue.setPaymentDate(rs.getString("G5DATE"));
				returnValue.setPaymentTime(rs.getString("G5TIME"));
				returnValue.setPaymentMovexVoucherNumber(rs.getString("G5VONO").trim());
				returnValue.setPaymentMovexJournalNumber(rs.getString("G5JRNO").trim());
				returnValue.setPaymentMovexJournalNumberSequence(rs.getString("G5JSNO").trim());
				returnValue.setPaymentPostingFlags(rs.getString("G5POSTF").trim());
				returnValue.setPaymentWriteUpNumber(rs.getString("G5WUP").trim());
				
				returnValue.setPayCode(ServiceRawFruit.loadFieldsRawFruitPayCode("loadRFPayment", rs));
					// Includes:
						//Payment Type:	G5CPTP
						//Run Type:		G5CRTP
						//Category:		G5CAT
						//Payment Code	G5PCODE			
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the RawFruitPayment class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("loadFieldsRawFruitPayment(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

/**
* Load class fields from result set.
*/
	public static RawFruitSpecialCharges loadFieldsRawFruitSpecialCharges(String requestType,
																		  ResultSet rs)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		RawFruitSpecialCharges returnValue = new RawFruitSpecialCharges();
	
		try{ 
			if (requestType.equals("payment"))
			{
				returnValue.setScaleTicketNumber(rs.getString("G6SCALE#").trim());
				returnValue.setScaleTicketCorrectionSequenceNumber(rs.getString("G6CSEQ1").trim());
				returnValue.setScaleSequence(rs.getString("G6SEQ#").trim());
				returnValue.setPoNumber(rs.getString("G6PO#").trim());
				returnValue.setPoLineNumber(rs.getString("G6PLIN#").trim());
				returnValue.setLotNumber(rs.getString("G6LOT").trim());
				returnValue.setLotSequence(rs.getString("G6TSEQ#").trim());
				returnValue.setPaymentSequenceNumber(rs.getString("G6YSEQ#").trim());
				returnValue.setSpecialChargesSequenceNumber(rs.getString("G6OSEQ#").trim());
				returnValue.setRate(rs.getString("G6RATE").trim());
				returnValue.setAccountingOption(rs.getString("G6CCODE").trim());
				
				returnValue.setPaymentUser(rs.getString("G6USER"));
				returnValue.setPaymentDate(rs.getString("G6DATE"));
				returnValue.setPaymentTime(rs.getString("G6TIME"));
				returnValue.setSpecialChargesMovexVoucherNumber(rs.getString("G6VONO").trim());
				returnValue.setSpecialChargesMovexJournalNumber(rs.getString("G6JRNO").trim());
				returnValue.setSpecialChargesMovexJournalNumberSequence(rs.getString("G6JSNO").trim());
				returnValue.setSpecialChargesPostingFlags(rs.getString("G6POSTF").trim());
				
				returnValue.setSupplierSpecialCharges(ServiceSupplier.loadFieldsSupplier("rfEntrySpecialCharges", rs));
					// Includes:
						//Supplier 		G6SUPP
				// Description for the Accounting Option
				try
				{
					returnValue.setAccountingOptionDescription(rs.getString("FUTX40").trim());
				}
				catch(Exception e)
				{}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the RawFruitSpecialCharges class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("loadFieldsRawFruitSpecialCharges(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

/**
 * Use to Add a record into the GRPCSCALE File
 * 			along with a record into the GRPCPO File
 */
	
private static void addLoad(Vector incomingParms,  // UptRawFruitLoad
							Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement addIt = null;
	
	// 	build SQL Statement, Execute SQL Statement -- ADD the Scale Record
	try {
		addIt = conn.createStatement();
		addIt.executeUpdate(buildSqlStatement("addScale", incomingParms));
	} catch (Exception e) {
		throwError.append("Error occured building or executing a sql (addScale). " + e);
	} finally {
		try {
			if (addIt != null)
				addIt.close();
		} catch (Exception e) {
			throwError.append("Error closing Statement (addIt). " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("addLoad(");
			throwError.append("String requestType, Vector, Connection)");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}
	

	return;
}

/**
 * Use to Add a record into the GRPCPO File
 */
	
private static void addPO(Vector incomingParms,  // UptRawFruitLoad
						  Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement addIt = null;
	
	// 	build SQL Statement, Execute SQL Statement -- ADD 1 PO Record
	try {
		addIt = conn.createStatement();
		addIt.executeUpdate(buildSqlStatement("addPO", incomingParms));
	} catch (Exception e) {
		throwError.append("Error occured building or executing sql (addPO). " + e);
	} finally {
		try {
			if (addIt != null)
				addIt.close();
		} catch (Exception e) {
			throwError.append("Error closing Statement (addPO). " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("addPO(");
			throwError.append("String requestType, Vector, Connection)");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}
	
	return;
}

/**
 * Determine what needs to be done to Update a record for a Raw Fruit Load
 *    // Process Information to ADD/UPDATE/DELETE the file: GRPCSCALE
 *                      also to ADD/UPDATE/DELETE the file: GRPCBINS
 * 						also to ADD/UPDATE/DELETE the file: GRPCPO
 */
	public static void processLoad(Vector incomingParms) // Send in UpdRawFruitLoad
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		
		try { 
			conn = ConnectionStack3.getConnection();
			// Determine which methods need to be processed
			UpdRawFruitLoad ip = (UpdRawFruitLoad) incomingParms.elementAt(0);
			String loadValue = ip.getScaleTicket().trim();
			
			if (loadValue == null)
				loadValue = "";
			
			// MUST have a Scale Ticket Number
			if (!loadValue.trim().equals(""))
			{
				DateTime dtCurrent = UtilityDateTime.getSystemDate();
				incomingParms.addElement(dtCurrent);
				String testLoad = verifyLoad(loadValue.trim());
				// Should the Scale Ticket Number (Record) be added?
				//     Does a Record Currently Exist?  VerifyLoad
				if (!testLoad.equals(""))
				{ // Add a Basic Load Record to be updated later
					try
					{
						addLoad(incomingParms, conn);
						// Add the Element that will be the Sequence Number for the PO's on a Load
			   	  		// incomingParms.addElement("1");
						// addPO(incomingParms, conn);
					}
					catch(Exception e) {
						// Catch a Problem
						throwError.append("Problem occurred when trying to Add a record for Scale Ticket Number: " + loadValue + "::" + e);
					}
				}
				else
				{ // Update the Load INFORMATION
			   	   
					try
					{
						if (!ip.getSaveButton().equals(""))
						{
							// Update the PO Information 
							updatePO(incomingParms, conn);
							// PROCESS Update Information for the LOAD
			   	  			// Update the Load HEADER information
			   	  	    	updateLoad(incomingParms, conn); 
			   	  	    	// Update the Bins on the Load Information -- Delete and Insert the Records
			   	  	    	updateBins(incomingParms, conn);
			   	  	    	//---------------------------------------------------------------
			   	  	    	// Calculate and Update Fields
			   	  	    	calculateAndUpdate("load", incomingParms, conn);
						}
						
						if (!ip.getAddPO().equals(""))
						{
							int nextPo = 1;
							try
							{
								nextPo = (new Integer(ip.getCountPO()).intValue()) + 1;
							}
							catch(Exception e)
							{	}
							incomingParms.addElement((nextPo + ""));
							addPO(incomingParms, conn);
						}
					}
					catch(Exception e)
					{
						// Catch a Problem
						throwError.append("Problem occurred when trying to Update a record for Scale Ticket Number: " + loadValue + "::" + e);
					}	
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the New Item. " + e) ;
		} finally {
			try {
				if (conn != null)
					ConnectionStack3.returnConnection(conn);
			} catch (Exception e) {
				System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.processLoad: " + e);
				throwError.append("Error closing connsection. " + e);
			}
			
			if (!throwError.toString().equals("")) {
				throwError.append("Error @ com.treetop.services.");
				throwError.append("ServiceRawFruit.");
				throwError.append("processLoad(Vector)");
				System.out.println(throwError.toString());
				Exception e = new Exception();
				e.printStackTrace();
				throw new Exception(throwError.toString());
			}
		}
		
		// return data.
		return;
	}

/**
 * Use to Update the Load File:
 *    GRPCSCALE -- Load Header File
 */
private static void updateLoad(Vector incomingParms,  // UpdRawFruitLoad
							   Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement updIt = null;
	
	try { 
		//System.out.println("UpdateLoad");
	    try {
			updIt = conn.createStatement();
			updIt.executeUpdate(buildSqlStatement("updateLoad", incomingParms));
			updIt.close();
		} catch (Exception e) {
			throwError.append(" error occured executing an sql statement to update a Load (Header) Record. " + e);
		}
		
		// Update the PO Default from the Load for Supplier
		try {
			updIt = conn.createStatement();
			updIt.executeUpdate(buildSqlStatement("updateLoadPO", incomingParms));
			updIt.close();
		} catch (Exception e) {
			throwError.append(" error occured executing an sql statement to update a Load (Header) Record. " + e);
		}
		
		// Update the Lot Default from the Load for Supplier
		try {
			updIt = conn.createStatement();
			updIt.executeUpdate(buildSqlStatement("updateLoadLot", incomingParms));
			updIt.close();
		} catch (Exception e) {
			throwError.append(" error occured executing an sql statement to update a Load (Header) Record. " + e);
		}
		
	} catch(Exception e)
	{
		throwError.append(" Problem Updating the Load Record. " + e) ;
	} finally {
		// close Statement
		try {
			if (updIt != null)
				updIt.close();
		} catch (Exception e) {
			throwError.append("Error closing statement. " + e);
		}
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("updateLoad(");
			throwError.append("Vector, Connection)");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}
	
	// return data.
	return;
}

/**
 * Use to Validate a Sent in Scale Ticket Number
 *    // Check in file GRPCSCALE
 */
public static String verifyLoad(String scaleTicketNumber)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	StringBuffer rtnProblem = new StringBuffer();
	Connection conn = null; // New Lawson Box - Lawson Database
	Statement findIt = null;
	ResultSet rs = null;
	
	try { 
		String requestType = "verifyLoad";
		String sqlString = "";
			
		// verify base class initialization.
		// verify incoming Load - Scale Ticket Number
		if (scaleTicketNumber == null || scaleTicketNumber.trim().equals(""))
			rtnProblem.append(" Scale Ticket Number must not be null or empty.");
		
		if (throwError.toString().equals(""))
		{
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(scaleTicketNumber);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
				rtnProblem.append(" Problem when building Test for Load: ");
				rtnProblem.append(scaleTicketNumber + " PrintScreen this message and send to Information Services. ");
			}
		}
		
		// get a connection. execute sql, build return object.
		if (rtnProblem.toString().equals("")) 
		{
			try {
				conn = ConnectionStack3.getConnection();	
				findIt = conn.createStatement();
				rs = findIt.executeQuery(sqlString);
					
				if (rs.next())
				{
					// it exists and all is good.
				} else {
					rtnProblem.append(" Scale Ticket '" + scaleTicketNumber + "' ");
					rtnProblem.append("does not exist. ");
				}
			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. " + e);
				rtnProblem.append(" Problem when finding Load: ");
				rtnProblem.append(scaleTicketNumber + " PrintScreen this message and send to Information Services. ");
			}
		}
	} catch(Exception e)
	{
		throwError.append(" Problem verifying the Load; " + scaleTicketNumber);
	// return connection.
	} finally {
		try{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		}catch(Exception e)	{
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.verifyLoad: " + e);
		}
		try {
			

			if (rs != null)
				rs.close();
			
			if (findIt != null)
				findIt.close();
			
			//Log any errors.
			if (!throwError.toString().equals("")) {
				throwError.append("Error @ com.treetop.services.");
				throwError.append("ServiceRawFruit.");
				throwError.append("verifyLoad(String: scaleTicketNumber)");
				System.out.println(throwError.toString());
				Exception e = new Exception();
				e.printStackTrace();
				throw new Exception(throwError.toString());
			}
		} catch (Exception e) {
			throwError.append("Error closing connection/result set/statement. " + e);
		}
	}

	// return data.
	return rtnProblem.toString();
}

/**
 * Use to Update the Bin File:
 *    GRPCBINS -- Bin File for the Load
 * 
 *   // Update Process includes:
 *      	Deleting All Records with the Scale Ticket on them
 *   		Inserting the Valid NEW Records
 */
private static void updateBins(Vector incomingParms,  // UpdRawFruitLoad
							   Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement deleteIt = null;
	Statement addIt = null;
	Statement updateDesc = null;
	Statement updateCalc = null;
	
	// Delete all the Records with the Scale Number
	try { 
	    try {
			deleteIt = conn.createStatement();
			deleteIt.executeUpdate(buildSqlStatement("deleteBins", incomingParms));
			deleteIt.close();
		} catch (Exception e) {
			throwError.append(" error occured executing an sql statement to delete the Bin Records. " + e);
		}
	
		// Go through Vector and ADD records
		try { 
			Vector listBins = ((UpdRawFruitLoad) incomingParms.elementAt(0)).getListBins();
			
			if (listBins.size() > 0)
			{
				for (int x = 0; x < listBins.size(); x++)
				{
					try {
						Vector sendParms = new Vector();
						sendParms.addElement(listBins.elementAt(x));
						sendParms.addElement(incomingParms.elementAt(1));
						addIt = conn.createStatement();
						addIt.executeUpdate(buildSqlStatement("addBins", sendParms));
						addIt.close();
					} catch (Exception e) {
						throwError.append(" error occured executing an sql statement to add a Bin Record. " + e);
					}
				}
			}
		} catch(Exception e) {
			throwError.append(" Problem Adding the Bin Record. " + e) ;
		}
		
		// Update the Records to Add Description
	    try {
			updateDesc = conn.createStatement();
			updateDesc.executeUpdate(buildSqlStatement("updateBinDescription", incomingParms));
			updateDesc.close();
		} catch (Exception e) {
			throwError.append(" error occured executing an sql statement to Update Descriptions on the Bin Records. " + e);
		}
	
		// Update the Records to Calculate the Bin Tare Weights
	    try {
			updateCalc = conn.createStatement();
			updateCalc.executeUpdate(buildSqlStatement("updateBinCalc", incomingParms));
			updateCalc.close();
		} catch (Exception e) {
			throwError.append(" error occured executing an sql statement to Update Calculated information on the Bin Records. " + e);
		}
		
	} catch(Exception e)
	{
		throwError.append(" Problem Adding Calulcated Information to the Bin Records. " + e) ;
	} finally {
		//close statements.
		try {
			if (deleteIt != null)
				deleteIt.close();	
		} catch (Exception e) {
				throwError.append("Error closing Statement (deleteIt). " + e);
		}
		
		try {
			if (addIt != null)
				addIt.close();
		} catch (Exception e) {
			throwError.append("Error closing Statement (addIt). " + e);
		}
		
		try {
			if (updateDesc != null)
				updateDesc.close();
		} catch (Exception e) {
			throwError.append("Error closing Statement (updateDesc). " + e);
		}
		
		try {
			if (updateCalc != null)
				updateCalc.close();
		} catch (Exception e) {
			throwError.append("Error closing Statement (updateClac). " + e);
		}
		
		//Logs any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("updateBins(");
			throwError.append("Vector, Connection)");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}
	
	// return data.
	return;
}

/**
 * Determine what needs to be done to Update a record for a Raw Fruit Lot
 * 	Each Lot is associated to a Scale Ticket/Sequence = PO and has a Lot Sequence Number
 *    // Process Information to ADD/UPDATE/DELETE the file: GRPCTICK
 *                      also to ADD/UPDATE/DELETE the file: GRPC
 * 						also to ADD/UPDATE/DELETE the file: GRPC
 */
	public static void processLot(Vector incomingParms) // Send in UpdRawFruitLot
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		
		try { 
			conn = ConnectionStack3.getConnection();
			// Determine which methods need to be processed
			UpdRawFruitLot ip = (UpdRawFruitLot) incomingParms.elementAt(0);
			String lotValue = ip.getLotNumber().trim();
			
			if (lotValue == null)
				lotValue = "";
			
			// MUST have a Scale Ticket Number
			if (!lotValue.trim().equals(""))
			{
				DateTime dtCurrent = UtilityDateTime.getSystemDate();
				incomingParms.addElement(dtCurrent);
				String testLot = verifyLot(lotValue.trim());
				// Should the Lot Number (Record) be added?
				//     Does a Record Currently Exist?  
				// System.out.println("Lot Number: " + lotValue.trim());
				// System.out.println("Original Lot Number: " + ip.getOriginalLotNumber().trim());
				
				if (!testLot.equals("") && ip.getOriginalLotNumber().trim().equals(""))
				{ // Add a Record for the Lot / can then be updated
					try
					{
			   	  		addLot(incomingParms, conn);
					}
					catch(Exception e)
					{
						// Catch a Problem
						throwError.append("Problem occurred when trying to Add a record for Lot Number: " + lotValue + "::" + e);
					}
				}
				
		   	  	try
		   	  	{
			   	  	if (!ip.getSaveButton().equals(""))
			   	  	{
			   	  		// PROCESS Update Information for the Lot
			   	  		// Update the Lot Information
			   	  	    updateLot(incomingParms, conn); 
			   	  	    //--------------------------------------------------
			   	  	    // Use to Delete and Re-Add Payment Information
			   	  	    updatePayment(incomingParms, conn);
			   	  	    //---------------------------------------------------------------
			   	  	    // Calculate and Update Fields
			   	  	    calculateAndUpdate("lot", incomingParms, conn);
			   	  	    //---------------------------------------------------------------
			   	  	    // Calculate and Update Cull Fields
			   	  	    if (ip.getCrop().equals("CHERRY") ||
			   	  	    	ip.getCrop().equals("STRAWBERRY"))	
			   	  	       calculateCulls("lot", incomingParms, conn);
			   	  	}
		   	  	}
		   	  	catch(Exception e)
		   	  	{
			   	  	// Catch a Problem
				  	throwError.append("Problem occurred when trying to Update a record for a Lot (Ticket) Number: " + lotValue + "::" + e);
		   	  	}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the Lot. " + e) ;
		} finally {
			try {
				if (conn != null)
					ConnectionStack3.returnConnection(conn);
			} catch (Exception e) {
				System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.processLot: " + e);
				throwError.append("Error closing connection. " + e);
			}
			
			//Log any errors.
			if (!throwError.toString().equals("")) {
				throwError.append("Error @ com.treetop.services.");
				throwError.append("ServiceRawFruit.");
				throwError.append("processLot(Vector)");
				System.out.println(throwError.toString());
				Exception e = new Exception();
				e.printStackTrace();
				throw new Exception(throwError.toString());
			}
		}
		
		// return data.
		return;
	}

/**
 * Use to Validate a Sent in Lot Number
 *    // Check in file GRPCTICK
 */
public static String verifyLot(String lotNumber)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	StringBuffer rtnProblem = new StringBuffer();
	Connection conn = null; // New Lawson Box - Lawson Database
	Statement findIt = null;
	ResultSet rs = null;
	
	try { 
		String requestType = "verifyLot";
		String sqlString = "";
			
		// verify base class initialization.
		// verify incoming Lot
		if (lotNumber == null || lotNumber.trim().equals(""))
			rtnProblem.append(" Lot Number must not be null or empty.");
		
		if (throwError.toString().equals(""))
		{
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(lotNumber);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch (Exception e) {
			throwError.append(" error trying to build sqlString. ");
			rtnProblem.append(" Problem when building Test for Lot: ");
			rtnProblem.append(lotNumber + " PrintScreen this message and send to Information Services. ");
			}
		}
		
		// get a connection. execute sql, build return object.
		if (rtnProblem.toString().equals("")) {
			try {
				conn = ConnectionStack3.getConnection();	
				findIt = conn.createStatement();
				rs = findIt.executeQuery(sqlString);
				
				if (rs.next())
				{
					// it exists and all is good.
				} else {
					rtnProblem.append(" Lot Number '" + lotNumber + "' ");
					rtnProblem.append("does not exist. ");
				}
					
			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. " + e);
				rtnProblem.append(" Problem when finding Lot: ");
				rtnProblem.append(lotNumber + " PrintScreen this message and send to Information Services. ");
			}
		}
	} catch(Exception e)
	{
		throwError.append(" Problem verifying the Lot; " + lotNumber);
	// return connection.
	} finally {
		try {
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		}catch(Exception e)	{
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.verifyLot: " + e);
		}
		try {
			if (rs != null)
				rs.close();
			
			if (findIt != null)
				findIt.close();
			
		} catch (Exception e) {
			throwError.append("Error closing connection/result set/statement. " + e);
		}
		
		//Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("verifyLot(String: lotNumber)");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}
	
	// return data.
	return rtnProblem.toString();
}

/**
 * Use to Add a record into the GRPCTICK File
 */
	
private static void addLot(Vector incomingParms,  // UpdRawFruitLot
							Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement addIt = null;
	
	// 	build SQL Statement, Execute SQL Statement -- ADD the Lot Record
	try {
		addIt = conn.createStatement();
		addIt.executeUpdate(buildSqlStatement("addLot", incomingParms));
	} catch (Exception e) {
		throwError.append("Error occured building or executing sql (addLot). " + e);
	} finally {
		try {
			if (addIt != null)
				addIt.close();
		} catch (Exception e) {
			throwError.append("Error at closing Statement (addIt). " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("addLot(");
			throwError.append("String requestType, Vector, Connection)");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}
	
	return;
}

/**
 * Use to Update the Load File:
 *    GRPCTICK -- Lot File
 */
private static void updateLot(Vector incomingParms,  // UpdRawFruitLoad
							  Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement updIt = null;
	
	try { 
	//	System.out.println("UpdateLot");
		// 9/12/13 Twalton -- Moving away from Vector and to CommonRequestBean
		//   Along with Calling BuildSQL.copyPO instead of buildSqlStatement
		CommonRequestBean crb = new CommonRequestBean();
		crb.setDateTime((DateTime) incomingParms.elementAt(1));
    	
	    try {
			updIt = conn.createStatement();
			// Change to point to the BuildSQL statement
			//updIt.executeUpdate(buildSqlStatement("updateLot", incomingParms));
			updIt.executeUpdate(BuildSQL.updateLot(crb, (UpdRawFruitLot) incomingParms.elementAt(0)));
		} catch (Exception e) {
			throwError.append(" error occured executing an sql statement to update a Lot. " + e);
		}
	} catch(Exception e)
	{
		throwError.append(" Problem Updating the Lot Record. " + e) ;
		// close statement
	} finally {
		try {
			if (updIt != null)
				updIt.close();
		} catch (Exception e) {
			throwError.append("Error closing statement. " + e);
		}
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("updateLot(");
			throwError.append("Vector, Connection)");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}
	
	// return data.
	return;
}

/**
 *   Method Created 11/20/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the UpdRawFruitLot Object for use in the SQL statement
 * @return BeanRawFruit
 * @throws Exception
 */
public static BeanRawFruit findLot(Vector inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	BeanRawFruit returnValue = new BeanRawFruit();
	Connection conn = null;
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		returnValue = findLot(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		//close connection.
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.findLot: " + e);
			throwError.append("Error closing connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("findLot(");
			throwError.append("Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
 *    Method Created 11/20/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the UpdRawFruitLot Object for use in the SQL statement
 * @return BeanRawFruit
 * @throws Exception
 */
private static BeanRawFruit findLot(Vector inValues, 
									 Connection conn)
throws Exception
{
	// Load All information Relating to the LOT 
			//-- Scale Ticket Number
			//-- Scale Ticket Correction Sequence Number
			//-- Scale Ticket Sequence
			//-- PO Sequence
			//-- Lot Sequence Number
			//-- Lot Number 
	StringBuffer throwError = new StringBuffer();
	BeanRawFruit returnValue = new BeanRawFruit();
	ResultSet rs = null; // FOR LOT
	Statement listThem = null; // FOR LOT
	
	ResultSet rsItemWhse = null;
	Statement listItemWhse = null;
	
	try
	{
		try {
			// Get the Lot Related Information -- This SQL will retrieve LOT	   
			// Payment, and Special Charge Information will Be Done separately
			listThem = conn.createStatement();
			//9/16/13 TWalton - change to commonrequestBean
			
			CommonRequestBean crb = new CommonRequestBean();
			crb.setIdLevel1((String) inValues.elementAt(0));
			crb.setIdLevel2((String) inValues.elementAt(1));
			crb.setIdLevel3((String) inValues.elementAt(2));
			crb.setIdLevel4((String) inValues.elementAt(3));
			crb.setIdLevel5((String) inValues.elementAt(4));
			crb.setIdLevel6((String) inValues.elementAt(5));
			
			//rs = listThem.executeQuery(buildSqlStatement("findLot", inValues));
			rs = listThem.executeQuery(BuildSQL.findLot(crb));
			
		} catch(Exception e) {
			throwError.append("Error occured Retrieving or Executing sql (findLot). " + e);
		}
		
		try
		{
			RawFruitLot rfl = new RawFruitLot();
		 	int first = 0;
		 	Vector listPayments = new Vector();
		 	RawFruitPayment rfp = new RawFruitPayment();
		 	RawFruitPayment saveRFP = new RawFruitPayment();
		 	Vector listSpecialCharges = new Vector();
		 	
		 	while (rs.next() && throwError.toString().equals(""))
		    {
		 	//	System.out.println("Scale" + rs.getString("G5SCALE#"));
		 		if (first == 0)
		 		{ // ONLY NEED FIRST TIME THROUGH
		 			//-----------------------------------------------------------------------------------------------	
		 			//Build the MAIN Lot Information
		 			rfl = loadFieldsRawFruitLot("lot", rs);
		 			returnValue.setRfLot(rfl);
		 			try
					{  // load the payment for the 1st time through
			 			//Load Data into the PAYMENT -- Must ONLY do it for a NEW Payment
			 			if (rs.getString("G5SCALE#") != null)
			 			  saveRFP = loadFieldsRawFruitPayment("payment", rs);
					}
			 			catch(Exception e) {
			 				throwError.append("Error loading fields for (Payment (first time)). " + e);
			 		}
		 			first++;
		 		}
		 		else
		 		{
		 			try
					{  // load the payment for the 1st time through
			 			//Save a Copy of the Information
			 			if (rs.getString("G5SCALE#") != null)
			 			   rfp = loadFieldsRawFruitPayment("payment", rs);
					} catch(Exception e) {
						throwError.append("Error loading fields for (payment (every Time)). " + e);
					}

			 	//	System.out.println("reg:" + rfp.getPaymentSequenceNumber());
			 	//	System.out.println("save:" + saveRFP.getPaymentSequenceNumber());
			 		
		 			// Should this Object Be Saved
		 			//	 Skip the Saving of information
			 		if (!rfp.getPaymentSequenceNumber().equals("") &&
			 			!saveRFP.getPaymentSequenceNumber().equals("") &&
			 			!rfp.getPaymentSequenceNumber().equals(saveRFP.getPaymentSequenceNumber()))
			 		{ // Save the Information into the List of Payments
			 			 saveRFP.setListSpecialCharges(listSpecialCharges);
			 		     listPayments.addElement(saveRFP);
			 		    try
						{  // load the payment for the 1st time through
				 			//Save a Copy of the Information
				 			if (rs.getString("G5SCALE#") != null)
				 			   saveRFP = loadFieldsRawFruitPayment("payment", rs);
						} catch(Exception e) {
							throwError.append("Error loading fields for (payment (every Time)). " + e);
						}    
			 		    listSpecialCharges = new Vector();
			 		}	
		 		}
				try
				{ // Build a Special CHARGES EVERY TIME
				   RawFruitSpecialCharges rfsc = new RawFruitSpecialCharges();
				   if (rs.getString("G6SCALE#") != null)
		 		      rfsc = loadFieldsRawFruitSpecialCharges("payment", rs);
				   if (!rfsc.getRate().trim().equals(""))
			 		   listSpecialCharges.addElement(rfsc);
				}
				catch(Exception e) {
					throwError.append("Error loading fields for (payment Special Charges). " + e);
				}
		    }// END of the WHILE Statement
		 	
		 	saveRFP.setListSpecialCharges(listSpecialCharges);
	 		listPayments.addElement(saveRFP);
	 		rfl.setListPayments(listPayments);
	 		// 9/23/13 TWalton - add in a test for the Item Whse, so we can edit it
				// Test to see if the Item Warehouse Record is in M3 and Ready to be used
				try{
					listItemWhse = conn.createStatement();
					CommonRequestBean crbItemWhse = new CommonRequestBean();
					crbItemWhse.setIdLevel1(rfl.getLotInformation().getItemNumber());
					crbItemWhse.setIdLevel2(rfl.getWarehouseFacility().getWarehouse());
					rsItemWhse = listItemWhse.executeQuery(BuildSQL.verifyItemWhse(crbItemWhse));
					int countItemWhse = 0;
					if (rsItemWhse.next())
					{
						countItemWhse++;
						// This means there is a record and all is good
					}
					if (countItemWhse == 0){
						rfl.setItemWhseNotValid(" item:" + rfl.getLotInformation().getItemNumber() +
												" whse:" + rfl.getWarehouseFacility().getWarehouse() +
												" DOES NOT have a record in M3 (MMS002) ");
					}
					rsItemWhse.close();
					listItemWhse.close();
					
				}catch(Exception e)
				{
					
				}finally{
					try{
						if (rsItemWhse != null)
							rsItemWhse.close();
						if (listItemWhse != null)
							listItemWhse.close();
					}catch(Exception e)
					{}
				}
	 		
		 	returnValue.setRfLot(rfl);
		 			 	
		} catch(Exception e) {
			throwError.append(" Error occured while Building Vector from sql statement. " + e);
		}	
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		//verify result sets are closed.
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing a result set (rs). " + e);
		}
		
		//verify statements are closed.
		try
		{
			if (listThem != null)
				listThem.close();
		} catch(Exception e){
			throwError.append("Error closing a statement (listThem). " + e);
		}
		
		// log any error.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("findLot(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
 * Use to Update the Payment Files:
 *    GRPCPMT -- Payment Information
 *    GRPCOTHC -- Special Charges Associated to each Payment
 * 
 *   // Update Process includes:
 *      	Deleting All Records with the Scale Ticket/Scale Sequence/PO/Lot Sequence/Lot Number
 *   		Inserting the Valid NEW Records
 */
private static void updatePayment(Vector incomingParms,  // UpdRawFruitLot
							      Connection conn)
	throws Exception
{
    //****************************************************************************************************
	// Delete all the Records with the Scale Number/Sequence/PO/Lot Sequence and Lot Number
	//System.out.println("Delete Payment");
	
	StringBuffer throwError = new StringBuffer();
	Statement deleteIt = null;
	Statement addIt = null;
	Statement updateDesc = null;
	Statement updateCalc = null;
	CommonRequestBean crb = new CommonRequestBean();
	
	try {
		try{//delete 
			//Get the parms Ready for Deletion of Records
			// 9/23/13 TWalton - moved to BuildSQL 
			UpdRawFruitLot urfl = (UpdRawFruitLot) incomingParms.elementAt(0);
			
			crb.setIdLevel1(urfl.getScaleTicket());
			crb.setIdLevel2(urfl.getScaleTicketCorrectionSequence());
			crb.setIdLevel3(urfl.getScaleSequence());
			crb.setIdLevel4(urfl.getPoNumber());
			crb.setIdLevel5(urfl.getLotSequenceNumber());
			crb.setIdLevel6(urfl.getLotNumber());
			crb.setDateTime((DateTime) incomingParms.elementAt(1));
			
//			Vector deleteParms = new Vector();
//			deleteParms.addElement(urfl.getScaleTicket());
//			deleteParms.addElement(urfl.getScaleTicketCorrectionSequence());
//			deleteParms.addElement(urfl.getScaleSequence());
//			deleteParms.addElement(urfl.getPoNumber());
//			deleteParms.addElement(urfl.getLotSequenceNumber());
//			deleteParms.addElement(urfl.getOriginalLotNumber());
         
			try {
				// Delete All the Payment Records - file GRPCPMT	
				deleteIt = conn.createStatement();
				//deleteIt.executeUpdate(buildSqlStatement("deletePayment", deleteParms));
				deleteIt.executeUpdate(BuildSQL.deletePayment(crb));
				deleteIt.close();
			} catch (Exception ePayment) {
				throwError.append(" error occured executing an sql statement to delete the Payment Records. " + ePayment);
			}
         
			try {
				// Delete All the Special Charges Records - file GRPCOTHC	
				deleteIt = conn.createStatement();
				//deleteIt.executeUpdate(buildSqlStatement("deleteSpecialCharges", deleteParms));
				deleteIt.executeUpdate(BuildSQL.deleteSpecialCharges(crb));
				deleteIt.close();
			} catch (Exception ePayment) {
				throwError.append(" error occured executing an sql statement to delete the Special Charges Records. " + ePayment);
			}
		} catch(Exception e) {
			throwError.append(" Problem Deleting the Payment Records. " + e) ;
		}
	
		//****************************************************************************************************
		// Go through Vector and ADD records
	
		try { 
			Vector listPayments = ((UpdRawFruitLot) incomingParms.elementAt(0)).getListPayment();
		
			if (listPayments.size() > 0)
			{
				for (int x = 0; x < listPayments.size(); x++)
				{
					UpdRawFruitLotPayment urflp = (UpdRawFruitLotPayment) listPayments.elementAt(x);
				   
					try {  // ADD Records into File: GRPCPMT
//						Vector sendParms = new Vector();
//						sendParms.addElement(urflp);
//						sendParms.addElement(incomingParms.elementAt(1)); // Use to get Current Date
						addIt = conn.createStatement();
						//addIt.executeUpdate(buildSqlStatement("addPayment", sendParms));
						addIt.executeUpdate(BuildSQL.addPayment(crb, urflp));
						addIt.close();
					} catch (Exception e) {
						throwError.append(" error occured executing an sql statement to add a Payment Record. " + e);
					}
					
					//--------------------------------------------------
					// Within each Payment add appropriate Special Charges
					Vector listSC = urflp.getListSpecialCharges();
						
					if (listSC.size() > 0)
					{ 
						for (int y = 0; y < listSC.size(); y++)
						{
							try
							{   // ADD Records into File: GRPCOTHC
//								Vector sendParms = new Vector();
//								sendParms.addElement(listSC.elementAt(y));
//								sendParms.addElement(incomingParms.elementAt(1)); // Use to get Current Date
								addIt = conn.createStatement();
//								addIt.executeUpdate(buildSqlStatement("addSpecialCharges", sendParms));
								addIt.executeUpdate(BuildSQL.addSpecialCharges(crb, (UpdRawFruitLotPaymentSpecialCharges) listSC.elementAt(y)));
								addIt.close();
							} catch (Exception e){
								throwError.append(" error occured executing an sql statement to add a Special Charges Record. " + e);
							}
						}
					}

				}
			}
		} catch(Exception e) {
			throwError.append(" Problem adding special charge record. " + e) ;
		} 

	} catch (Exception e) {
		throwError.append("Error updating payment. " + e);
	}

	finally {
		try {
	    	if (deleteIt != null)
	    		deleteIt.close();
	    	
	    	if (addIt != null)
	    		addIt.close();
	    	
	    	if (updateDesc != null)
	    		updateDesc.close();
			
	    	if (updateCalc != null)
	    		updateCalc.close();
	    		
	    } catch (Exception e) {
	    	throwError.append("Error closing result set / statements, " + e);
	    }
	    	
	    //Log any errors.
	    if (!throwError.toString().equals("")) {
	    	throwError.append("Error @ com.treetop.services.");
	    	throwError.append("ServiceRawFruit.");
	    	throwError.append("updatePayment(");
	    	throwError.append("Vector, Connection)");
	    	System.out.println(throwError.toString());
	    	throw new Exception(throwError.toString());
	    }
	}
	    
	// return data.
	return;
}

/**
 * Use to Updated the Calculated Information 
 *   in files GRPCSCALE
 *            GRPCPO
 *            GRPCTICK
 * 			  
 */
	
private static void calculateAndUpdate(String saveType, // Lot or Load
									   Vector incomingParms,  // UpdRawFruitLoad OR UpdRawFruitLot
									   Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement updIt = null;
	ResultSet rsLot = null;
	Statement listLots = null;
	Statement listPayment = null;
	ResultSet rsPay = null;
 	ResultSet rs = null;
   	Statement listPO = null;
	String scaleTicket = "";
	String scaleTicketCorrectionSequence = "";
	String updateUser = "";
	
	// 	build SQL Statement, Execute SQL Statement -- UPDATE records
	try {
		// Going to Update More than one File - Usually...
		if (saveType.equals("load"))
		{ 	// Means that Save / Recalculate was pressed on the LOAD screen
			UpdRawFruitLoad rf = (UpdRawFruitLoad) incomingParms.elementAt(0);
			updateUser = rf.getUpdateUser();
			scaleTicket = rf.getScaleTicket();
			scaleTicketCorrectionSequence = rf.getScaleTicketCorrectionSequence();
			// Update the Tare Weight of the Bins and the Accepted Fruit Weight
			Vector sendForUpdate = new Vector();
			sendForUpdate.addElement(scaleTicket);
			sendForUpdate.addElement(scaleTicketCorrectionSequence);
			
			try
			{ 
				// Update GRPCSCALE -- with the Bin Tare Weights
				updIt = conn.createStatement();
				updIt.executeUpdate(buildSqlStatement("updateScaleBinTare", sendForUpdate));
				updIt.close();
			} catch(Exception e)	{
				//Catch will happen when there are NO bins
				throwError.append("Error at (updateScaleBinTare). " + e);
			}
			
			try
			{ 
				// Update GRPCSCALE -- with the Accepted Fruit Amount
				updIt = conn.createStatement();
			    updIt.executeUpdate(buildSqlStatement("updateScaleTotal", sendForUpdate));
			    updIt.close();
			} catch(Exception e) {
				throwError.append("Error at (updateScaleTotal). " + e);
			}
			
			try
			{ 
				// Update GRPCTICK -- with the Receiving Date
				sendForUpdate.addElement(rf.getReceivingDate());
				updIt = conn.createStatement();
				updIt.executeUpdate(buildSqlStatement("updateRecDate", sendForUpdate));
				updIt.close();
			} catch(Exception e) {
				throwError.append("Error at (updateRecDate). " + e);
			}
		}
		
		if (saveType.equals("lot"))
		{ // Means that Save / Recalculate was pressed on the LOAD screen
			UpdRawFruitLot rf = (UpdRawFruitLot) incomingParms.elementAt(0);
			updateUser = rf.getUpdateUser();
			// Update the Tare Weight of the Bins and the Accepted Fruit Weight
			scaleTicket = rf.getScaleTicket();
			scaleTicketCorrectionSequence = rf.getScaleTicketCorrectionSequence();
			Vector sendForUpdate = new Vector();
			sendForUpdate.addElement(scaleTicket);
			sendForUpdate.addElement(scaleTicketCorrectionSequence);
			
			try
			{ 
				// Update GRPCSCALE -- with the Number of TOTAL Boxes for this Scale Ticket
				updIt = conn.createStatement();
				updIt.executeUpdate(buildSqlStatement("updateTotalBoxes", sendForUpdate));
				updIt.close();
			} catch(Exception e) {
				// Catch will happen when there are NO bins
				throwError.append("Error at (updateTotalBoxes). " + e);
			}
			
			try
			{ 
				// Update GRPCSCALE -- ONLY Average Weight Per Box
				updIt = conn.createStatement();
				updIt.executeUpdate(buildSqlStatement("updateScaleAveWeightPerBox", sendForUpdate));
				updIt.close();
			} catch(Exception e) {
				throwError.append("Error at (updateScaleAveWeightPerBox). " + e);
			}
		}
		
		// FOR BOTH
		//----------------------------------------------------------------------------------
		//1st you have to put the Weight down to the LOT -- By Accepted and Rejected Bin Type
		//    Update the Weight of the Fruit in the TICK File
	    // Read Through AND PROCESS ALL the Lots for the Scale Ticket
		
		//Build/Run SQL for getting Calculated List
		try {
			// Get the list of Quantities Added up from the Lots for the Scale and Sequence
			Vector getList = new Vector();
			getList.addElement(scaleTicket);
			getList.addElement(scaleTicketCorrectionSequence);
			listLots = conn.createStatement();
			rsLot = listLots.executeQuery(buildSqlStatement("listCalcTickWeight", getList));
		
		} catch(Exception e) {
			throwError.append("Error at (listCalcTickWeight). " + e);
		}
			 
		try {
			Vector listOfLots = new Vector();
			BigDecimal loadWeight = new BigDecimal(0);
			BigDecimal entireLoadWeight = new BigDecimal(0);
			BigDecimal loadBoxes = new BigDecimal(0);
			int firstRecord = 0;
			int countManual = 0;
			
			while (rsLot.next())
			{
				//this section needs review
				if (firstRecord == 0)
				{	
					// Start with the Load Accepted Fruit Weight
					loadWeight = rsLot.getBigDecimal("G1AFWGT");
			 		entireLoadWeight = rsLot.getBigDecimal("G1AFWGT");
					// AND the Load Number of Boxes
			 		loadBoxes = rsLot.getBigDecimal("G1TBOXES");
				}
				
				firstRecord++;
				
				if (rsLot.getString("G4SCALE#") != null)
			 	{
					RawFruitLot rfl = loadFieldsRawFruitLot("lotCalc", rsLot);
					
					if (!rfl.getLotNumber().trim().equals(""))
					{
						//test this flagging manual information section
						if ((!rfl.getFlagAcceptedWeightManual().equals("") &&
			 			   !rfl.getFlagRejectedWeightManual().equals("")) ||
						   (!rfl.getFlagAcceptedWeightManual().equals("") &&
						     rfl.getLotRejectedBins25Box().equals("0") &&
						     rfl.getLotRejectedBins30Box().equals("0")) ||
						   (!rfl.getFlagRejectedWeightManual().equals("") &&
						     rfl.getLotAcceptedBins25Box().equals("0") &&
						     rfl.getLotAcceptedBins30Box().equals("0"))) 
						{
							// Update this Lot -- WILL NOT Include in the list to update
							//Only thing that needs to be done on the quantities for Weight and Boxes
							// Needs to be removed - Subtracted from the loadWeight and loadBoxes
							loadWeight = loadWeight.subtract(new BigDecimal(rfl.getLotAcceptedWeight()));
							loadBoxes = loadBoxes.subtract(new BigDecimal(rfl.getLotAcceptedBins25Box()).multiply(new BigDecimal("25")));
							loadBoxes = loadBoxes.subtract(new BigDecimal(rfl.getLotAcceptedBins30Box()).multiply(new BigDecimal("30")));
							loadBoxes = loadBoxes.subtract(new BigDecimal(rfl.getLotRejectedBins25Box()).multiply(new BigDecimal("25")));
							loadBoxes = loadBoxes.subtract(new BigDecimal(rfl.getLotRejectedBins30Box()).multiply(new BigDecimal("30")));
							listOfLots.addElement(rfl);
						}
						else
						{
							if (!rfl.getFlagAcceptedWeightManual().equals("") ||
				 			   !rfl.getFlagRejectedWeightManual().equals(""))
							{
								if (!rfl.getFlagAcceptedWeightManual().equals(""))
								{
									loadWeight = loadWeight.subtract(new BigDecimal(rfl.getLotAcceptedWeight()));
									loadBoxes = loadBoxes.subtract(new BigDecimal(rfl.getLotAcceptedBins25Box()).multiply(new BigDecimal("25")));
									loadBoxes = loadBoxes.subtract(new BigDecimal(rfl.getLotAcceptedBins30Box()).multiply(new BigDecimal("30")));
				 		   		}
								
								if (!rfl.getFlagRejectedWeightManual().equals(""))
								{
									loadBoxes = loadBoxes.subtract(new BigDecimal(rfl.getLotRejectedBins25Box()).multiply(new BigDecimal("25")));
									loadBoxes = loadBoxes.subtract(new BigDecimal(rfl.getLotRejectedBins30Box()).multiply(new BigDecimal("30")));
								}
								
								countManual++;
							}
							
							listOfLots.addElement(loadFieldsRawFruitLot("lotCalc", rsLot));
						}
					}
			 	}
			}// END of the WHILE Statement
			 	
			rsLot.close();
			listLots.close();
			 	
			if (listOfLots.size() > 0)
			{
				// Read Through the listOfLots Vector and UPDATE the Quantities for 
				// Accepted and Rejected Weight
				BigDecimal avePerBox = new BigDecimal("0");
				
				if (loadBoxes.compareTo(new BigDecimal("0")) != 0)
					avePerBox = loadWeight.divide(loadBoxes, 9, 5);
				
			 	BigDecimal totalWeight = new BigDecimal("0");
			 	
			 	for (int x = 0; x < listOfLots.size(); x++)
			 	{
			 		RawFruitLot rfLot = (RawFruitLot) listOfLots.elementAt(x);
			 	    	
			 		// Send into SQL Statement:
			 		Vector sendIn = new Vector();
			 	    sendIn.addElement(rfLot.getScaleTicketNumber());
			 	    sendIn.addElement(rfLot.getScaleTicketCorrectionSequenceNumber());
			 	    sendIn.addElement(rfLot.getScaleSequence());
			 	    sendIn.addElement(rfLot.getLotSequence());
			 	    sendIn.addElement(rfLot.getLotNumber());
			 	    BigDecimal aWgt = new BigDecimal("0");
			 	    
			 	    if (rfLot.getFlagAcceptedWeightManual().equals(""))
			 	    {
			 	    	// Value for Raw Fruit Accepted Bins Weight
			 	    	BigDecimal aBox = new BigDecimal(rfLot.getLotAcceptedBins25Box()).multiply(new BigDecimal("25"));
			 	    	aBox = aBox.add(new BigDecimal(rfLot.getLotAcceptedBins30Box()).multiply(new BigDecimal("30")));
			 	    	aWgt = avePerBox.multiply(aBox);
//			 	    	System.out.println("-----------aWgt" + aWgt);
			 	    	aWgt = aWgt.setScale(0, 5);
//			 	    	System.out.println("aBox" + aBox);
//			 	    	System.out.println("aWgt" + aWgt);
//			 	    	System.out.println("avePerBox" + avePerBox);
			 	    }
			 	    else
			 	    	aWgt = new BigDecimal(rfLot.getLotAcceptedWeight());
			 	    
			 	    BigDecimal rWgt = new BigDecimal("0");
			 	    
			 	    if (rfLot.getFlagRejectedWeightManual().equals(""))
			 	    {
			 	    	// Value for Raw Fruit Rejected Bins Weight
			 	    	BigDecimal rBox = new BigDecimal(rfLot.getLotRejectedBins25Box()).multiply(new BigDecimal("25"));
			 	    	rBox = rBox.add(new BigDecimal(rfLot.getLotRejectedBins30Box()).multiply(new BigDecimal("30")));
			 	    	rWgt = avePerBox.multiply(rBox);
			 	    	rWgt = rWgt.setScale(0,5);
			 	    }
			 	    else
			 	    	rWgt = new BigDecimal(rfLot.getLotRejectedWeight());
			 	    
			 	    // Value for Total Weight
			 	    BigDecimal tWgt = aWgt.add(rWgt);
			 	    totalWeight = totalWeight.add(tWgt);
			 	    if (rfLot.getFlagAcceptedWeightManual().equals(""))
			 	    {
			 	    	if (x == (listOfLots.size() - 1))
			 	    	{
			 	    		// System.out.println("loadWeight:" + loadWeight);
			 	    		//System.out.println("totalWeight:" + totalWeight);
			 	    	
			 	    		if (entireLoadWeight.compareTo(totalWeight) != 0)
			 	    		{
			 	    			BigDecimal diff = entireLoadWeight.subtract(totalWeight);
			 	    		
			 	    			if (diff.compareTo(new BigDecimal("0")) != 0)
			 	    			{
			 	    				tWgt = tWgt.add(diff);
			 	    			
			 	    				if (aWgt.compareTo(new BigDecimal("0")) != 0)
			 	    				{
			 	    					aWgt = aWgt.add(diff);
			 	    				}
			 	    				else
			 	    				{
			 	    					if (rWgt.compareTo(new BigDecimal("0")) != 0)
			 	    						rWgt = rWgt.add(diff);
			 	    				}
			 	    			}
			 	    		}
			 	    	}
			 	    }
			 	    
			 	    if (aWgt.compareTo(new BigDecimal("0")) == 0 &&
			 	    	rWgt.compareTo(new BigDecimal("0")) == 0)
			 	    	tWgt = new BigDecimal("0");
			 	    
			 	    // System.out.println("aWgt:" + aWgt);
			 	    sendIn.addElement(aWgt.toString());
			 	    sendIn.addElement(rWgt.toString());
			 	    sendIn.addElement(tWgt.toString());

			 	    // THEN run an update of the Average Accept Weight per Bin
			 	    try
			 	    {
			 	    	updIt = conn.createStatement();
			 	    	updIt.executeUpdate(buildSqlStatement("updateLotCalcWgt", sendIn));
			 	    	updIt.close();
					} catch(Exception e) {
						throwError.append("Error at (updatelotCalcWgt). " + e);
			 	    }
					
			 	    //----------------------------------------------------------------------
			 	    // Go deal with the Payment within the Lot -- the Weight for the Payment
		 	    	    
					try {
						Vector getPaymentList = new Vector();
						getPaymentList.addElement(rfLot.getScaleTicketNumber());
						getPaymentList.addElement(rfLot.getScaleTicketCorrectionSequenceNumber());
				 	    getPaymentList.addElement(rfLot.getScaleSequence());
				 	    getPaymentList.addElement(rfLot.getLotSequence());
				 	    getPaymentList.addElement(rfLot.getLotNumber());
						listPayment = conn.createStatement();
						rsPay = listPayment.executeQuery(buildSqlStatement("listCalcPaymentWeight", getPaymentList));
					} catch(Exception e) {
						throwError.append("Error at (listCalcPaymentWeight). " + e);
					}
					
					try
					{
						BigDecimal lotWeight = new BigDecimal("0");
						String firstTime = "Y";
						// Read Through the Lot Payments
						while (rsPay.next())
						{
							if (firstTime.equals("Y"))
							{
								firstTime = "N";
								lotWeight = rsPay.getBigDecimal("G4ABW");
							}
							Vector sendInPayment = new Vector();
					 	    sendInPayment.addElement(rfLot.getScaleTicketNumber());
					 	    sendInPayment.addElement(rfLot.getScaleTicketCorrectionSequenceNumber());
					 	    sendInPayment.addElement(rfLot.getScaleSequence());
					 	    sendInPayment.addElement(rfLot.getLotSequence());
					 	    sendInPayment.addElement(rfLot.getLotNumber());	
					 	    sendInPayment.addElement(rsPay.getString("G5YSEQ#"));
				 	    	BigDecimal pWgt = new BigDecimal(aWgt.toString());
				 	    	try
				 	    	{
				 	    		BigDecimal pBox = rsPay.getBigDecimal("G525B").multiply(new BigDecimal("25"));
		 	    	      		pBox = pBox.add(rsPay.getBigDecimal("G530B").multiply(new BigDecimal("30")));
		 	    	      	    if (rsPay.getString("G5TBWO").trim().equals(""))
				 	    	    {
		 	    	      			pWgt = avePerBox.multiply(pBox);
//		 	    	      			System.out.println("pWgt" + pWgt);
		 				 	    	pWgt = pWgt.setScale(0, 5);
//		 				 	    	System.out.println("pBox" + pBox);
//		 				 	    	System.out.println("---------");
				 	    	    }
				 	    	    else
				 	    	    { // Accepted Weight Flag was set to Manual
			 	    	    		pWgt = rsPay.getBigDecimal("G5TBW");
			 	    	    		pWgt = pWgt.setScale(0, 5);
				 	    	    }
		 	    	      		pWgt = pWgt.setScale(0,1);
//		 	    	      		System.out.println("pWgt" + pWgt);
		 	    	      		lotWeight = lotWeight.subtract(pWgt);
//		 	    	      		System.out.println("LotWeight" + lotWeight);
		 	    	      		if (lotWeight.compareTo(new BigDecimal("1")) == 0 ||
		 	    	      			lotWeight.compareTo(new BigDecimal("-1")) == 0 ||
		 	    	      			lotWeight.compareTo(new BigDecimal("0")) == -1)	
		 	    	      		{
		 	    	      			pWgt = pWgt.add(lotWeight);
		 	    	      		}
				 	    	} catch(Exception e) {
				 	    		throwError.append("Error at (updatePaymentWgt). " + e);
				 	    	}
				 	    	
				 	    	try
				 	    	{
//				 	    		System.out.println("PaymentWeight update:" + pWgt);
				 	    		sendInPayment.addElement(pWgt.toString());
//				 	    		System.out.println(pWgt.toString());
				 	    	    updIt = conn.createStatement();
				 			    updIt.executeUpdate(buildSqlStatement("updatePaymentWgt", sendInPayment));
				 			    updIt.close();
				 	    	} catch(Exception e) {
				 	    		throwError.append("Error at execute sql (updatePaymentWgt). " + e);
				 	    	}
						}// End of the While}
						 	 
						rsPay.close();
						listPayment.close();
					} catch(Exception e) { 
						throwError.append("Error within try (updatePaymentWgt). " + e);
					}    
			 	}
			}
			
			Vector sendIn = new Vector();
	 	    sendIn.addElement(scaleTicket);
	 	    sendIn.addElement(scaleTicketCorrectionSequence);
	 	    
	 	    try
			{
	 	    	updIt = conn.createStatement();
	 	    	updIt.executeUpdate(buildSqlStatement("updateLotCalc", sendIn));
	 	    	updIt.close();
			} catch(Exception e) {
				throwError.append("Error at (updateLotCalc). " + e);
			}
			
	 	    try
			{
	 	    	updIt = conn.createStatement();
	 			updIt.executeUpdate(buildSqlStatement("updateLotBrixPrice", sendIn));
	 			updIt.close();
			} catch(Exception e) {
				throwError.append("Error within try (updateLotBrixPrice). " + e);
			}

		} catch(Exception e) {
			throwError.append("Error processing the lot list. " + e);
		}

		// PROCESS ALL the PO's for the ENTIRE SCALE TICKET		
		// Update the PO File, (Scale Sequence) read through all the Lot Numbers for the Scale Ticket	
 
		Vector buildVector = new Vector(6);
		// Must go to the Ticket Information and Retrieve the Information to be displayed on the PO


		try {
			// Get the list of Quantities Added up from the Lots for the Scale and Sequence
			Vector getList = new Vector();
			getList.addElement(scaleTicket);
			getList.addElement(scaleTicketCorrectionSequence);
			listPO = conn.createStatement();
			rs = listPO.executeQuery(buildSqlStatement("findPOQuantities", getList));
		
		} catch(Exception e) {
			throwError.append("Error at (findPOQuantities). " + e);
		}
		
		try
		{
			DateTime dt = (DateTime) incomingParms.elementAt(1);
			
			while (rs.next())
			{
				buildVector = new Vector();
			 	buildVector.addElement(rs.getString(1));
			 	buildVector.addElement(rs.getString(2));
			 	buildVector.addElement(rs.getString(3));
			 	buildVector.addElement(rs.getString(4));
			 	buildVector.addElement(rs.getString(5));
			 	buildVector.addElement(rs.getString(6));
			 	buildVector.addElement(rs.getString("G4SCALE#"));
			 	buildVector.addElement(rs.getString("G4CSEQ1"));
			 	buildVector.addElement(rs.getString("G4SEQ#"));
			 	buildVector.addElement(updateUser);
			 	buildVector.addElement(dt.getDateFormatyyyyMMdd());
			 	buildVector.addElement(dt.getTimeFormathhmmss());
			 	
			 	// Update GRPCPO -- Every Time Lot is Updated - Recalculate the information on the PO
				updIt = conn.createStatement();
				updIt.executeUpdate(buildSqlStatement("updatePOFromLot", buildVector));
				updIt.close();
			}
		} catch(Exception e) {
			throwError.append("Error at (updatePOFromLot). " + e);
		}
	   	  
		rs.close();
	   	listPO.close();
	 
	} catch (Exception e) {
		throwError.append(" error occured building or executing a sql statement to add Load. " + e);
	} finally {
		
		//close result set / statements.
		try {
			if (updIt != null)
				updIt.close();
			
			if (listLots != null)
				listLots.close();
			
			if (listPayment != null)
				listPayment.close();
			
			if (listPO != null)
				listPO.close();
			
			if (rsLot != null)
				rsLot.close();
			
			if (rs != null)
				rs.close();
			
			if (rsPay != null)
				rsPay.close();
			
		} catch (Exception e) {
			throwError.append("Error at close of result set / Statments. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) 
		{
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("calculateAndUpdate(");
			throwError.append("String requestType, Vector, Connection). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}
	
	return;
}

/**
 * Use to Update the PO File:
 *    GRPCPO -- PO File for the Load
 * 
 *   // Update Process includes:
 *      	Updating the Values of the PO Records
 */
private static void updatePO(Vector incomingParms,  // UpdRawFruitLoad
							 Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement updateIt = null;
	
	// Go through Vector and ADD records
	try { 
		Vector listPOs = ((UpdRawFruitLoad) incomingParms.elementAt(0)).getListPOs();
		
		if (listPOs.size() > 0)
		{
			for (int x = 0; x < listPOs.size(); x++)
			{  
			    try {
			    	
			    	// 9/12/13 Twalton -- Moving away from Vector and to CommonRequestBean
					//   Along with Calling BuildSQL.copyPO instead of buildSqlStatement
					CommonRequestBean crb = new CommonRequestBean();
					crb.setDateTime((DateTime) incomingParms.elementAt(1));
			    	
//			    	sendParms.addElement(incomingParms.elementAt(1));
					updateIt = conn.createStatement();
//					updateIt.executeUpdate(buildSqlStatement("updatePO", sendParms));
					updateIt.executeUpdate(BuildSQL.updatePO(crb, (UpdRawFruitPO) listPOs.elementAt(x)));
					updateIt.close();
					
				    try {
				    	Vector sendParms = new Vector();
				    	sendParms.addElement(listPOs.elementAt(x));
				    	sendParms.addElement(incomingParms.elementAt(1));
				    	
						updateIt = conn.createStatement();
						updateIt.executeUpdate(buildSqlStatement("updatePOLot", sendParms));
						updateIt.close();
					} catch (Exception e) {
						throwError.append(" error occured executing an sql statement to update a Lot Record - With Warehouse Facility and Supplier. " + e);
					}
					
				} catch (Exception e) {
					throwError.append(" error occured executing an sql statement to update a PO Record. " + e);
				}
			}
		}
	} catch(Exception e)
	{
		throwError.append(" Problem Updating the PO Record. " + e) ;
		// close Statement
	} finally {
		try {
			if (updateIt != null)
				updateIt.close();
		} catch (Exception e) {
			throwError.append("Error closing result set / statement. " + e);
		}
		
		//Log any errors
	    if (!throwError.toString().equals("")) {
	    	throwError.append("Error @ com.treetop.services.");
	    	throwError.append("ServiceRawFruit.");
	    	throwError.append("updatePO(");
	    	throwError.append("Vector, Connection)");
	    	System.out.println(throwError.toString());
	    	throw new Exception(throwError.toString());
	    }
	}

	// return data.
	return;
}

/**
 *   Method Created 3/18/09 TWalton
 *   // Use to Copy the ENTIRE LOAD
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return void 
 * 
 *  Incoming copyType:
 *         - copyPositive (MUST HAVE Scale Ticket & Correction Sequence Number)
 *         - copyNegative (MUST HAVE Scale Ticket & Correction Sequence Number)
 */
public static void copy(String copyType, Vector inValues)
{
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		
		copy(copyType, inValues, conn);
		
	} catch (Exception e) {
		throwError.append("Error running copy methods. " + e);
	}
	finally {

		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.copy: " + e);
			throwError.append("Error closing Connection. " + e);
		}
			
		// Log any errors.
		if (!throwError.toString().equals(""))
		{
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("copy(");
			throwError.append("String (" + copyType + "),");
			throwError.append("Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}

	// return value
	return;
}

/**
 * Method Created 1/5/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector of UpdRawFruitLoad Objects
 * @return Nothing
 */
private static void deletePO(Vector inValues, 
							 Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement deletePO = null;
	
	try
	{
		try {// Get the list of Item Type Values - Along with Descriptions
			deletePO = conn.createStatement();
			deletePO.executeUpdate(buildSqlStatement("deletePOOtherCharges", inValues));
			deletePO.close();
		} catch(Exception e) {
			throwError.append("Error occured Retrieving or Executing sql (deletePOOtherCharges). " + e);
		}
		
		try {// Get the list of Item Type Values - Along with Descriptions
			deletePO = conn.createStatement();
			deletePO.executeUpdate(buildSqlStatement("deletePOPayment", inValues));
			deletePO.close();
		} catch(Exception e) {
			throwError.append("Error occured Retrieving or Executing sql (deletePOPayment). " + e);
		}
		
		try {// Get the list of Item Type Values - Along with Descriptions
			deletePO = conn.createStatement();
			deletePO.executeUpdate(buildSqlStatement("deletePOLot", inValues));
			deletePO.close();
		} catch(Exception e) {
			throwError.append("Error occured Retrieving or Executing sql (deletePOPayment). " + e);
		}
		
		try {// Get the list of Item Type Values - Along with Descriptions
			deletePO = conn.createStatement();
			deletePO.executeUpdate(buildSqlStatement("deletePO", inValues));
			deletePO.close();
		} catch(Exception e) {
			throwError.append("Error occured Retrieving or Executing sql (deletePO). " + e);
		}
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		
		//close result set / Statements.
		try {
			if (deletePO != null)
				deletePO.close();
		} catch(Exception e){
			throwError.append("Error closing a Statement. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("deletePO(");
			throwError.append("Vector, Connection). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}
	
	return;
}

/**
 * Method Created 1/5/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector of UpdRawFruitLoad Objects
 * @return Nothing
 */
private static void deleteLot(Vector inValues, 
							  Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement deleteLot = null;
	
	try
	{
		try {// Get the list of Item Type Values - Along with Descriptions
			deleteLot = conn.createStatement();
			deleteLot.executeUpdate(buildSqlStatement("deleteLotOtherCharges", inValues));
			deleteLot.close();
		} catch(Exception e) {
		   	throwError.append("Error occured Retrieving or Executing sql (deleteOtherCharges). " + e);
		}
		
		try {// Get the list of Item Type Values - Along with Descriptions
			deleteLot = conn.createStatement();
			deleteLot.executeUpdate(buildSqlStatement("deleteLotPayment", inValues));
			deleteLot.close();
		} catch(Exception e) {
		   	throwError.append("Error occured Retrieving or Executing sql (deleteLotPayment). " + e);
		}
		
		try {// Get the list of Item Type Values - Along with Descriptions
			deleteLot = conn.createStatement();
			deleteLot.executeUpdate(buildSqlStatement("deleteLot", inValues));
			deleteLot.close();
		} catch(Exception e) {
			throwError.append("Error occured Retrieving or Executing sql (deleteLot). " + e);
		}
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally
	{
		//verify all Statements are closed. 
		try
		{
			if (deleteLot != null)
				deleteLot.close();
		} catch(Exception e){
			throwError.append("Error closing Statement. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("deleteLot(");
			throwError.append("Vector, Connection). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	return;
}

/**
 *   Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				payCode - Means it will use the file, GRPKCODE to retrieve Data
 */
public static Vector dropDownBinType(Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		ddit = dropDownBinType(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		
		try {
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.dropDownBinType: " + e);
			throwError.append("Error closing a connection with Stack 3. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownBinType(Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	// return value
	return ddit;
}

/**
 * Method Created 11/10/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * 						// Default would be to select ALL
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownBinType(Vector inValues, 
									  Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector ddit = new Vector();
	ResultSet rs = null;
	Statement listThem = null;
	
	try
	{
		try {// Get the list of Item Type Values - Along with Descriptions
			listThem = conn.createStatement();
			rs = listThem.executeQuery(buildSqlStatement("bins", inValues));
		} catch(Exception e) {
		 	throwError.append("Error occured Retrieving or Executing sql (bins). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next() && throwError.toString().equals(""))
				{
					DropDownSingle dds = loadFieldsDropDownSingle("bins", rs);
					ddit.addElement(dds);
				}
			} catch(Exception e) {
				throwError.append(" Error occured while Building Vector from sql statement. " + e);
			}
		} 		
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		
		//close result set / statement.
		try
		{
			if (rs != null)
				rs.close();
			
			if (listThem != null)
				listThem.close();
			
		} catch(Exception e) {
			throwError.append("Error closing result set /statement. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownBinType(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}


	// return value
	return ddit;
}

/**
 * First find all the Lot Numbers that are not attached to a PO
 * Find the PO Number, from a Lot Number / Item Number - in the Transaction File
 * Use to Update these PO Files with the PO Number:
 *    GRPCPO   -- PO File for the Load
 *    GRPCTICK -- Ticket File for the Load
 *    GRPCPMT  -- Payment of Ticket for the Load
 *    GRPCOTHC -- Other Charges for Payment of the Ticket
 * 
 *   // Update Process includes:
 *        Updating the PO Number Values in all records
 *        Look into the PO Line number where appropriate  
 */
private static void updatePONumber(Vector incomingParms,  // Scale Ticket and the Scale Correction Sequence #
							       Connection conn)
{
	StringBuffer throwError = new StringBuffer();
	Statement updIt = null;
	ResultSet rsLots = null;
	Statement listLots = null;
	
	// get a list of Item/Lots -- to go look up the PO Number
	// will ONLY get list IF the PO is 0
	// Fields to return Scale Sequence, PO#, PO Line #, Item, Lot 
	// Based on the Scale Ticket / Correction Number
	
	try
	{
		try {
			   // Get the list of Item Type Values - Along with Descriptions
		   listLots = conn.createStatement();
		   rsLots = listLots.executeQuery(buildSqlStatement("listLotsUpdatePO", incomingParms));
		  
		 } catch(Exception e)
		 {
			 throwError.append("Error occured Retrieving or Executing a sql statement - listLotsUpdatePO. " + e);
		 }
		 
		 try
		 {
			String savePO = "";
		 	while (rsLots.next())
		    {
	 			try
	 			{
                     // Figure out if there is a Transaction for this Item/Lot
	 			   Transaction tranValues = new Transaction();
	 			   tranValues.setTransactionType("25"); // Transaction type is Purcahse Order Reciept
	 			   tranValues.setItemNumber(rsLots.getString("G4ITEM"));
	 			   tranValues.setLotNumber(rsLots.getString("G4LOT"));
	 			   Vector sendValues = new Vector();
	 			   sendValues.addElement(tranValues);
	 			   Transaction returnValues = ServiceTransaction.findTransactionByItemLot(sendValues);
		 			   
	 			   if (!returnValues.getOrderNumber().trim().equals(""))
	 			   {
	 				   // Update Each of the Files, with the PO Number
	 				   Vector sendForUpdate = new Vector();
	 				   sendForUpdate.addElement(incomingParms.elementAt(0));
	 				   sendForUpdate.addElement(incomingParms.elementAt(1));
	 				   sendForUpdate.addElement(rsLots.getString("G4SEQ#"));
	 				   sendForUpdate.addElement(rsLots.getString("G4ITEM"));
	 				   sendForUpdate.addElement(rsLots.getString("G4LOT"));
	 				   sendForUpdate.addElement(returnValues.getOrderNumber().trim());
	 				   sendForUpdate.addElement(returnValues.getOrderLineNumber().trim());
	 				   //--------------------------------------------------------------------------------
	 				   // Update the PO Number in the PO File
	 				   if (!savePO.trim().equals(rsLots.getString("G4SEQ#")))
	 				   {
	 					   try
	 					   {
	 						   //Update GRPCPO -- with the PO Number
	 						   updIt = conn.createStatement();
	 						   updIt.executeUpdate(buildSqlStatement("updatePONumberOnPO", sendForUpdate));
	 						   updIt.close();
	 					   }
	 					   catch(Exception ePO) {
	 						   throwError.append("Error Caught when updateing the PO File with the PO number: " + ePO);
	 					   }
	 				   }
	 				   //--------------------------------------------------------------------------------
	 				   // Update the PO Number AND Line number in the Ticket File
	 				   try
	 				   {
	 					   //Update GRPCTICK -- with the PO Number
	 					   updIt = conn.createStatement();
	 					   updIt.executeUpdate(buildSqlStatement("updatePONumberOnLot", sendForUpdate));
	 					   updIt.close();
	 				   }
	 				   catch(Exception eLot) {
	 					   throwError.append("Error Caught when updateing the Lot File with the PO number: " + eLot);
	 				   }
	 				   //--------------------------------------------------------------------------------
	 				   // Update the PO Number AND Line number in the Payment File   
	 				   try
	 				   {
	 					   //Update GRPCPMT -- with the PO Number
	 					   updIt = conn.createStatement();
	 					   updIt.executeUpdate(buildSqlStatement("updatePONumberOnPayment", sendForUpdate));
	 					   updIt.close();
	 				   }
	 				   catch(Exception ePayment) {
	 					   throwError.append("Error Caught when updateing the Payment File with the PO number: " + ePayment);
	 				   }
	 				   //--------------------------------------------------------------------------------
	 				   // Update the PO Number AND Line number in the Special Charges File
	 				   try
	 				   {
	 					   //Update GRPCOTHC -- with the PO Number
	 					   updIt = conn.createStatement();
	 					   updIt.executeUpdate(buildSqlStatement("updatePONumberOnSpecialCharge", sendForUpdate));
	 					   updIt.close();
	 				   }
	 				   catch(Exception eSpecialCharge) {
	 					   throwError.append("Error Caught when updateing the SpecialCharge File with the PO number: " + eSpecialCharge);
	 				   }
	 			      
	 				   savePO = rsLots.getString("G4SEQ#").trim();
	 			   }
	 			}
	 		    catch(Exception e) {
	 		    	throwError.append("Error found when trying to Retrieve Transaction:" + e);
	 		    }
  		    }
		 } catch(Exception e) {
			 throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 			
	}
	catch(Exception e) {
		throwError.append("Problem with updatePONumber: " + e);
	}
	finally
	{
		try
		{
		  if (rsLots != null)
			 rsLots.close();
		  
		  if (listLots != null)
				 listLots.close();
		  
		  if (updIt != null)
				 updIt.close();
		}
		catch(Exception e)
		{
			throwError.append("Error closing result set / statement. " + e);
		}

		//Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("updatePONumber(");
			throwError.append("Vector, Connection)");
			Exception e = new Exception();
			e.printStackTrace();
			System.out.println(throwError.toString());
		}
	}
	return;
}

/**
 *   Method Created 2/20/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the 
 *              Vector which includes InqRawFruit for Selection Criteria
 * @return BeanRawFruit -- loaded with a Vector of RawFruitLoad Objects
 * @throws Exception
 */
public static BeanRawFruit listLoads(Vector inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	BeanRawFruit returnValue = new BeanRawFruit();
	Connection conn = null;
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		returnValue = listLoads(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {

		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.listLoads: " + e);
			throwError.append("Error closing connection. " + e);
		}
		
		// Log any errors..
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("listLoads(");
			throwError.append("Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
 *   Method Created 2/20/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the 
 *              Vector which includes InqRawFruit for Selection Criteria
 * @return BeanRawFruit -- loaded with a Vector of RawFruitLoad Objects
 */
private static BeanRawFruit listLoads(Vector inValues, 
									 Connection conn)
throws Exception
{
	// Load All information Relating to the LOAD -- Scale Ticket Number
	StringBuffer throwError = new StringBuffer();
	BeanRawFruit returnValue = new BeanRawFruit();
	ResultSet rs = null; // FOR LOAD
	Statement listThem = null; // FOR LOAD
	
	try
	{
		try {
		   // Get a list of Load (Scale Tickets) based on inquiry criteria
		   listThem = conn.createStatement();
		   rs = listThem.executeQuery(buildSqlStatement("listLoads", inValues));
		 
		} catch(Exception e) {
		   	throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}
		 
		Vector listLoads = new Vector();
		 
		try
		{
			int recordCount = 0;
		 	while (rs.next() && recordCount < 52 && throwError.toString().equals(""))
		    {
		 		listLoads.addElement(loadFieldsRawFruitLoad("listLoads", rs));
		 		recordCount++;
     		}// END of the WHILE Statement
		 	
		} catch(Exception e) {
			throwError.append(" Error occured while Building Vector from sql statement. " + e);
		}
		
		 returnValue.setListLoads(listLoads);
		 
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing result set (rs). " + e);
		}
		
		
		try
		{
			if (listThem != null)
				listThem.close();
		} catch(Exception e){
			throwError.append("Error closing statement (listThem). " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("listLoads(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
 * Method Created 2/25/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector of InqRawFruit Objects
 * @return Nothing
 */
private static void deleteLoad(Vector inValues, 
							   Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement deleteLoad = null;
	
	try
	{
		try {// Delete the Other Charges for A Load
			deleteLoad = conn.createStatement();
			deleteLoad.executeUpdate(buildSqlStatement("deleteLoadOtherCharges", inValues));
			deleteLoad.close();
		} catch(Exception e) {
		   	throwError.append("Error Retrieving or Executing sql (deleteLoadOtherCharges). " + e);
		}
		
		try {// Delete the Payment fields for A Load  
			deleteLoad = conn.createStatement();
			deleteLoad.executeUpdate(buildSqlStatement("deleteLoadPayment", inValues));
			deleteLoad.close();
		} catch(Exception e) {
			throwError.append("Error Retrieving or Executing sql (deleteLoadPayment). " + e);
		}
		
		try {// Delete the Lot Fields for a Load
			deleteLoad = conn.createStatement();
			deleteLoad.executeUpdate(buildSqlStatement("deleteLoadLot", inValues));
			deleteLoad.close();
		} catch(Exception e) {
			throwError.append("Error Retrieving or Executing sql (deleteLoadLot). " + e);
		}
		
		try {// Delete the PO Fields for a Load  
			deleteLoad = conn.createStatement();
			deleteLoad.executeUpdate(buildSqlStatement("deleteLoadPO", inValues));
			deleteLoad.close();
		} catch(Exception e) {
			throwError.append("Error Retrieving or Executing sql (deleteLoadPO). " + e);
		}
		
		try {// Delete the Bin Fields for a Load 
			deleteLoad = conn.createStatement();
			deleteLoad.executeUpdate(buildSqlStatement("deleteLoadBin", inValues));
			deleteLoad.close();
		} catch(Exception e) {
			throwError.append("Error Retrieving or Executing sql (deleteLoadBin). " + e);
		}
		
		try {// Delete the Load from the HEADER
			deleteLoad = conn.createStatement();
			deleteLoad.executeUpdate(buildSqlStatement("deleteLoad", inValues));
			deleteLoad.close();
		} catch(Exception e) {
			throwError.append("Error Retrieving or Executing sql (deleteLoad). " + e);
		}
	} catch (Exception e)
	{
		throwError.append("Error in the deleteLoad method." + e);
	}
	finally {
		
		//verify all Statements are closed.
		try {
			if (deleteLoad != null)
				deleteLoad.close();
		} catch(Exception e){
			throwError.append("Error closing Statement (deleteLoad). " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("deleteLoad(");
			throwError.append("Vector, Connection). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}
	
	return;
}

/**
 *   Method Created 1/5/09 TWalton
 *   // Use to Delete a specific PO
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				payCode - Means it will use the file, GRPKCODE to retrieve Data
 */
public static void delete(String deleteType, Vector inValues)
{
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		
		if (deleteType.equals("deleteLoad"))
		  deleteLoad(inValues, conn);
		
		if (deleteType.equals("deletePO"))
		  deletePO(inValues, conn);
		
		if (deleteType.equals("deleteLot"))
		  deleteLot(inValues, conn);
		
	} catch (Exception e) {
		throwError.append("Error running delete methods. " + e);
	}
	finally {

		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.delete: " + e);
			throwError.append("Error closing Connection. " + e);
		}
			
		// Log any errors.
		if (!throwError.toString().equals(""))
		{
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("delete(");
			throwError.append("String (" + deleteType + "),");
			throwError.append("Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}

	// return value
	return;
}

/**
 * Method Created 3/18/09 TWalton
 *   // Use to control the information retrieval
 *   //  and Copy Functionality
 * @param -- type of Copy, in values, inqRawFruit object 
 * @return Nothing
 */
private static void copy(String copyType,
						 Vector inValues, 
						 Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement copyData = null;
	Statement findSeq = null;
	ResultSet rs = null;
	BigDecimal correctionSequence = new BigDecimal("0");
	Vector findRecords = new Vector();
	findRecords.add(copyType);
	try
	{
		int recordCount = 0;
		try {// Figure out what the NEXT sequence number is
			InqRawFruit irf = (InqRawFruit) inValues.elementAt(0);
			findRecords.add(irf.getScaleTicket().trim());
			findRecords.add(irf.getScaleTicketCorrectionSequence().trim());
			findRecords.add(irf.getUpdateUser().trim());
			DateTime dt = UtilityDateTime.getSystemDate();
			findRecords.add(dt.getDateFormatyyyyMMdd());
			findRecords.add(dt.getTimeFormathhmmss());
			findSeq = conn.createStatement();
			rs = findSeq.executeQuery(buildSqlStatement("findCorrectionSequence", inValues));
			
			while (rs.next())
			{
				try
				{
					if (recordCount == 0)
					{
					   BigDecimal previousSeq = rs.getBigDecimal("G1CSEQ1");
					   correctionSequence = previousSeq.add(new BigDecimal("10"));
					}
				}
				catch(Exception e)
				{
					System.out.println("ERROR Determining Scale Correction Sequence Number: " + e);
				}
				recordCount++;
			}
			findRecords.add(correctionSequence.toString());
			findSeq.close();
			rs.close();
		} catch(Exception e) {
			throwError.append("Error occured Retrieving or Executing sql (Category). " + e);
		}
		// 9/12/13 Twalton -- Moving away from Vector and to CommonRequestBean
		//   Along with Calling BuildSQL.copyPO instead of buildSqlStatement
		CommonRequestBean crb = new CommonRequestBean();
		crb.setRebuildOption((String) findRecords.elementAt(0)); // Copy Type
		crb.setIdLevel1((String) findRecords.elementAt(1)); // Scale Number
		crb.setIdLevel2((String) findRecords.elementAt(2)); // Scale Correction Sequence Number
		crb.setIdLevel3((String) findRecords.elementAt(6)); // new Scale Correction Sequence Number
		crb.setUser((String) findRecords.elementAt(3)); // Last Change User
		crb.setDate((String) findRecords.elementAt(4)); // Last Change Date
		crb.setTime((String) findRecords.elementAt(5)); // Last Change Time
		
		if (throwError.toString().trim().equals(""))
		{
			try
			{	// Copy the Header	
				copyData = conn.createStatement();
				copyData.executeUpdate(buildSqlStatement("copyScale", findRecords));
				copyData.close();
				recordCount++;
			} catch(Exception e) {
				throwError.append("Error Retrieving or Executing sql (copyScale). " + e);
			}	
			try
			{	// Copy the Bins
				copyData = conn.createStatement();
				copyData.executeUpdate(buildSqlStatement("copyBins", findRecords));
				copyData.close();
			} catch(Exception e) {
				throwError.append("Error Retrieving or Executing sql (copyBins). " + e);
			}	
			try
			{	// Copy the PO
				copyData = conn.createStatement();
				// Changed 9/12/13 - moved to BuildSQL Section
				//copyData.executeUpdate(buildSqlStatement("copyPO", findRecords));
				copyData.executeUpdate(BuildSQL.copyPO(crb));
				copyData.close();
			} catch(Exception e) {
				throwError.append("Error Retrieving or Executing sql (copyPO). " + e);
			}	
			try
			{	// Copy the Ticket
				copyData = conn.createStatement();
				// Changed 9/13/13 - moved to BuildSQL Section
				//copyData.executeUpdate(buildSqlStatement("copyTicket", findRecords));
				copyData.executeUpdate(BuildSQL.copyTicket(crb));
				copyData.close();
			} catch(Exception e) {
				throwError.append("Error Retrieving or Executing sql (copyTicket). " + e);
			}	
			try
			{	// Copy the Payment
				copyData = conn.createStatement();
				// Changed 9/23/13 - Moved to BuildSQL Section
				//copyData.executeUpdate(buildSqlStatement("copyPayment", findRecords));
				copyData.executeUpdate(BuildSQL.copyPayment(crb));
				copyData.close();
			} catch(Exception e) {
				throwError.append("Error Retrieving or Executing sql (copyPayment). " + e);
			}	
			try
			{	// Copy the Other Charges
				copyData = conn.createStatement();
				copyData.executeUpdate(buildSqlStatement("copyOther", findRecords));
				copyData.close();
			} catch(Exception e) {
				throwError.append("Error Retrieving or Executing sql (copyOther). " + e);
			}	
			try
			{	// UpdateCorrectionCount
				findRecords.addElement(recordCount + "");
				copyData = conn.createStatement();
				copyData.executeUpdate(buildSqlStatement("updateCorrectionCount", findRecords));
				copyData.close();
			} catch(Exception e) {
				throwError.append("Error Retrieving or Executing sql (updateCorrectionCount). " + e);
			}				
		}	
		try {// Get list of Lot Numbers for update of the Lot number on the Copied Records
			InqRawFruit irf = (InqRawFruit) inValues.elementAt(0);
			findRecords = new Vector();
			findRecords.add(irf.getScaleTicket().trim());
			findRecords.add(correctionSequence.toString());
			findSeq = conn.createStatement();
			rs = findSeq.executeQuery(buildSqlStatement("listLotCopy", findRecords));
			while (rs.next())
			{
				try
				{
					String lot = rs.getString("G4LOT").trim();
	//				System.out.println("Old Lot: " + lot);
					String seq = rs.getString("G4CSEQ1").trim();
					int findDash = lot.indexOf("-");
					if (findDash > 0)
						lot = lot.substring(0, findDash);
					lot = lot + "-" + seq;
	//				System.out.println("New Lot: " + lot);
					Vector sendInfo = new Vector();
					sendInfo.addElement(findRecords.elementAt(0));
					sendInfo.addElement(findRecords.elementAt(1));
					sendInfo.addElement(rs.getString("G4LOT").trim());
					sendInfo.addElement(lot);
					try
					{	// Update the Ticket with valid Lot
						copyData = conn.createStatement();
						copyData.executeUpdate(buildSqlStatement("updateLotCopyTicket", sendInfo));
						copyData.close();
					} catch(Exception e) {
						throwError.append("Error Retrieving or Executing sql (updateLotCopyTicket). " + e);
					}	
					try
					{	// Update the Payment with a Valid Lot
						copyData = conn.createStatement();
						copyData.executeUpdate(buildSqlStatement("updateLotCopyPayment", sendInfo));
						copyData.close();
					} catch(Exception e) {
						throwError.append("Error Retrieving or Executing sql (updateLotCopyPayment). " + e);
					}	
					try
					{	// Update the Additional Charges with a Valid Lot
						copyData = conn.createStatement();
						copyData.executeUpdate(buildSqlStatement("updateLotCopyOther", sendInfo));
						copyData.close();
					} catch(Exception e) {
						throwError.append("Error Retrieving or Executing sql (updateLotCopyOther). " + e);
					}		  
				}
				catch(Exception e)
				{
					System.out.println("Update the Lot Number Value for the Correction: " + e);
				}
				recordCount++;
			}
			findSeq.close();
			rs.close();
		} catch(Exception e) {
			throwError.append("Error occured Retrieving or Executing sql (Category). " + e);
		}
	} catch (Exception e)
	{
		throwError.append("Error in the copy method." + e);
	}
	finally {
		
		//verify all Statements are closed.
		try {
			if (copyData != null)
				copyData.close();
			if (rs != null)
				rs.close();
			if (findSeq != null)
				findSeq.close();
		} catch(Exception e){
			throwError.append("Error closing Statement (findSequence). " + e);
		}
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("copy(copyType: " + copyType + " ");
			throwError.append("Vector, Connection). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}
	return;
}

/**
 * Use to Validate a Sent in Pay Code
 *    // Check in file GRPKCODE
 */
public static String verifyPayCode(String payCode)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	StringBuffer rtnProblem = new StringBuffer();
	Connection conn = null; // New Lawson Box - Lawson Database
	Statement findIt = null;
	ResultSet rs = null;
	
	try { 
		String requestType = "verifyPayCode";
		String sqlString = "";
			
		// verify incoming Value
		if (payCode == null || payCode.trim().equals(""))
			rtnProblem.append(" Pay Code must not be null or empty.");
		if (throwError.toString().equals(""))
		{
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(payCode);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
				rtnProblem.append(" Problem when building Test for Raw Fruit Pay Code: ");
				rtnProblem.append(payCode + " PrintScreen this message and send to Information Services. ");
			}
		}
		
		// get a connection. execute sql, build return object.
		if (rtnProblem.toString().equals("")) {
			try {
				conn = ConnectionStack3.getConnection();
				findIt = conn.createStatement();
				rs = findIt.executeQuery(sqlString);
					
				if (rs.next() && throwError.toString().equals(""))
				{
					// it exists and all is good.
				} else {
					rtnProblem.append(" Pay Code '" + payCode + "' ");
					rtnProblem.append("does not exist. ");
				}
					
			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. " + e);
				rtnProblem.append(" Problem when finding Raw Fruit Pay Code: ");
				rtnProblem.append(payCode + " PrintScreen this message and send to Information Services. ");
			}
		}
	} catch(Exception e)
	{
		throwError.append(" Problem verifying the Raw Fruit Payment Code: " + payCode);
	// return connection.
	} finally {
		try {
			if (findIt != null)
				findIt.close();
			if (rs != null)
				rs.close();
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch (Exception e) {
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.PayCode: " + e);
			throwError.append("Error closing connection/result set/statement. " + e);
		}
		
		//Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("verifyPayCode(String: payCode)");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}

	// return data.
	return rtnProblem.toString();
}

/**
 * Use to Updated the Cull Information 
 *   in file  GRPCTICK
 * 			  
 */
private static void calculateCulls(String saveType, // Lot or Load
							       Vector incomingParms,  // UpdRawFruitLoad OR UpdRawFruitLot
								   Connection conn)
{
  // Will not throw an error, will not update if there is a problem.
  // Will put out a System.out.println
	Statement findLot = null;
	ResultSet rs = null;
	Statement updIt = null;
	String scaleTicket = "";
	String scaleTicketCorrectionSequence = "";
	String updateUser = "";
	try
	{
	  if (saveType.equals("lot"))
	  { // Means that Save / Recalculate was pressed on the LOAD screen
		UpdRawFruitLot rf = (UpdRawFruitLot) incomingParms.elementAt(0);
		// First Test to see if the calculation is needed
		BigDecimal cullPounds = new BigDecimal("0");
		BigDecimal cullPercent = new BigDecimal("0");
		try
		{
			cullPounds = new BigDecimal(rf.getCullsPounds());
		}
		catch(Exception e)
		{
			cullPounds = new BigDecimal("0");
		}
		try
		{
			cullPercent = new BigDecimal(rf.getCullsPercent());
		}
		catch(Exception e)
		{
			cullPercent = new BigDecimal("0");
		}
		String updateRecord = "N";
		try
		{
			if ((cullPounds.compareTo(new BigDecimal("0")) != 0 &&
				 cullPercent.compareTo(new BigDecimal("0")) == 0) ||
				(cullPercent.compareTo(new BigDecimal("0")) != 0) &&
				 cullPounds.compareTo(new BigDecimal("0")) == 0)
			   updateRecord = "Y";
		}
		catch(Exception e)
		{
			System.out.println("Problem with Calculation of Culls");
			System.out.println("Cull Pounds:" + cullPounds);
			System.out.println("Cull Percent:" + cullPercent);
		}
		if (updateRecord.equals("Y"))
		{
			updateUser = rf.getUpdateUser();
			Vector sendForUpdate = new Vector();
			sendForUpdate.addElement(rf.getScaleTicket());
			sendForUpdate.addElement(rf.getScaleTicketCorrectionSequence());
			sendForUpdate.addElement(rf.getScaleSequence()); // Sequence of the PO's
			sendForUpdate.addElement(rf.getLotNumber()); 
			// 	Find the Accepted Weight of the Cull Bins
			try {
				findLot = conn.createStatement();
				rs = findLot.executeQuery(buildSqlStatement("findLotAcceptedWeight", sendForUpdate));
			} catch(Exception e) {
				//throwError.append("Error at (listCalcTickWeight). " + e);
			}	
			try {		
				if (rs.next())
				{	
					try
					{
		//				System.out.println("Cull Pounds:" + cullPounds);
		//				System.out.println("Cull Percent:" + cullPercent);
		//				System.out.println("accepted Weight" + rs.getString("G4ABW"));
		//				System.out.println("scaleTicket" + rs.getString("G4SCALE#"));
		//				System.out.println("lot" + rs.getString("G4LOT"));
						BigDecimal acceptedWeight = rs.getBigDecimal("G4ABW");
						if (cullPounds.compareTo(new BigDecimal("0")) != 0)
						{ // Will need to Calculate the Percent
							try
							{
								if (acceptedWeight.compareTo(new BigDecimal("0")) != 0)
								{
			//						System.out.println(cullPounds.divide(acceptedWeight, 6, 5).toString());
								   cullPercent = (cullPounds.divide(acceptedWeight, 6, 5).multiply(new BigDecimal(100)));
								   cullPercent.setScale(3, 5);
								}
							}
							catch(Exception e)
							{
								System.out.println("Problem with Calculation of Culls Percent");
								System.out.println("Cull Pounds:" + cullPounds);
								System.out.println("Cull Percent:" + cullPercent);
								System.out.println("accepted Weight:" + acceptedWeight);
							}
							
						}
						else
						{ // will need to calculate the Pounds
							try
							{
								if (acceptedWeight.compareTo(new BigDecimal("0")) != 0)
								{
								   cullPounds = cullPercent.divide(new BigDecimal("100")).multiply(acceptedWeight);
								   cullPounds.setScale(0);
								}								
							}
							catch(Exception e)
							{
								System.out.println("Problem with Calculation of Culls Pounds");
								System.out.println("Cull Pounds:" + cullPounds);
								System.out.println("Cull Percent:" + cullPercent);
								System.out.println("accepted Weight:" + acceptedWeight);
							}
						}
						//System.out.println("Cull Pounds:" + cullPounds);
						//System.out.println("Cull Percent:" + cullPercent);
						try
						{ 
							// Update GRPCTICK -- with the Cull Information
							sendForUpdate.addElement(cullPounds.toString());
							sendForUpdate.addElement(cullPercent.toString());
							updIt = conn.createStatement();
							updIt.executeUpdate(buildSqlStatement("updateCulls", sendForUpdate));
							updIt.close();
						} catch(Exception e)	{
							//Catch will happen when there are NO bins
							System.out.println("Error at (updateCulls). " + e);
						}	
					}
					catch(Exception e)
					{
						System.out.println("Problem with Calculation of Culls");
						System.out.println("Cull Pounds:" + cullPounds);
						System.out.println("Cull Percent:" + cullPercent);
					}					
				}// END of the WHILE Statement
				rs.close();
				findLot.close();			
			} catch(Exception e) {
				//throwError.append("Error processing the lot list. " + e);
			}	
		}
	  }
	} catch(Exception e) {
//		throwError.append("Error processing the lot list. " + e);
	}	
	finally
	{
	  try
	  {
		rs.close();
		findLot.close();
		updIt.close();
	  }
	  catch(Exception e)
	  {}
	}
		// Log any errors.
//		if (!throwError.toString().equals("")) 
//		{
//			throwError.append("Error at com.treetop.services.");
//			throwError.append("ServiceRawFruit.");
//			throwError.append("calculateAndUpdate(");
//			throwError.append("String requestType, Vector, Connection). ");
//			System.out.println(throwError.toString());
//			throw new Exception(throwError.toString());
//		}
//	}
	
	return;
}

/**
 *   Method Created 2/25/13 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the 
 *              Vector which includes 5 Strings
 *              1.  Scale Ticket Number
 *              2.  Scale Ticket Correction Sequence Number
 *              3.  Environment
 *              4.  Profile
 *              5.  Authorization
 *              6.  Date 
 * @return BeanRawFruit
 * @throws Exception
 */
public static BeanRawFruit generatePO(Vector inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	BeanRawFruit returnValue = new BeanRawFruit();
	Connection conn = null;
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		BeanRawFruit processLoad = findLoad(inValues, conn);
		returnValue = generatePO(inValues, processLoad);
		
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		//close connection.
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.generatePO: " + e);
			throwError.append("Error closing connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("findLoad(");
			throwError.append("Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}
/**
 *   Method Created 2/25/13 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the 
 *              Vector which includes 5 Strings
 *              1.  Scale Ticket Number
 *              2.  Scale Ticket Correction Sequence Number
 *              3.  Environment
 *              4.  Profile
 *              5.  Authorization
 *              6.  Date 
 * @return BeanRawFruit
 * @throws Exception
 */
private static BeanRawFruit generatePO(Vector inValues, BeanRawFruit processLoad)
throws Exception
{
	StringBuffer throwError = new StringBuffer();;
	try {
		//Read Through how many PO's are needed:
		Vector listPO = processLoad.getListRFPO();
		Vector listRFPO = new Vector();
		if (!listPO.isEmpty())
		{
			for (int x = 0; x < listPO.size(); x++)
			{
				RawFruitPO rfp = (RawFruitPO) listPO.elementAt(x);
				
				PPS370MIStartEntry startEntry = new PPS370MIStartEntry();
				startEntry.setBatchOrigin("RFPO");
				startEntry.setEnvironment((String) inValues.elementAt(2));
				startEntry.setUserProfile((String) inValues.elementAt(3));
				
				PPS370MIStartEntry endOfStartEntry = PPS370MI.startEntry(startEntry, (String) inValues.elementAt(4));
				
				PPS370MIAddHead addHead = new PPS370MIAddHead();
				addHead.setEnvironment((String) inValues.elementAt(2));
				addHead.setUserProfile((String) inValues.elementAt(3));
				addHead.setMessageNumber(endOfStartEntry.getMessageNumber());
				addHead.setFacility(rfp.getWarehouseFacility().getFacility());
				addHead.setWarehouse(rfp.getWarehouseFacility().getWarehouse());
				addHead.setSupplierNumber(rfp.getPoSupplier().getSupplierNumber());
				DateTime dtNow = UtilityDateTime.getSystemDate();
				addHead.setRequestedDeliveryDate(dtNow.getDateFormatyyyyMMdd());
				try{
					addHead.setOrderDate((String) inValues.elementAt(5));
					int testBD = new Integer((String) inValues.elementAt(5)).intValue();
					int testToday = new Integer (dtNow.getDateFormatyyyyMMdd()).intValue();
					if (testBD > testToday)
						addHead.setRequestedDeliveryDate((String) inValues.elementAt(5));
				}catch (Exception e)
				{
				}
				PPS370MIAddHead endOfAddHead = PPS370MI.addHead(addHead, (String) inValues.elementAt(4));
				
				//Run Through one Line Record for each Lot
				if (!rfp.getListLots().isEmpty())
				{
					for (int y = 0; y < rfp.getListLots().size(); y++)
					{
						RawFruitLot rfl = (RawFruitLot) rfp.getListLots().elementAt(y);
						PPS370MIAddLine addLine = new PPS370MIAddLine();
						addLine.setEnvironment((String) inValues.elementAt(2));
						addLine.setUserProfile((String) inValues.elementAt(3));
						addLine.setMessageNumber(endOfStartEntry.getMessageNumber());
						addLine.setPurchaseOrderNumber(endOfAddHead.getPurchaseOrderNumber());
						addLine.setItemNumber(rfl.getLotInformation().getItemNumber());
						addLine.setOrderQuantityAltUOM(rfl.getLotTotalWeight());
						addLine.setRequestedDeliveryDate(addHead.getOrderDate());
						
						PPS370MIAddLine endOfAddLine = PPS370MI.addLine(addLine, (String) inValues.elementAt(4));
					}
				}
				
				PPS370MIFinishEntry finishEntry = new PPS370MIFinishEntry();
				finishEntry.setMessageNumber(endOfStartEntry.getMessageNumber());
				finishEntry.setEnvironment((String) inValues.elementAt(2));
				finishEntry.setUserProfile((String) inValues.elementAt(3));
				
				PPS370MIFinishEntry endOfFinishEntry = PPS370MI.finishEntry(finishEntry, (String) inValues.elementAt(4));
				
				rfp.setPoNumber(endOfAddHead.getPurchaseOrderNumber());
				listRFPO.addElement(rfp);
								
			} // end of For Loop
		}
		processLoad.setListRFPO(listRFPO);
		
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}

	// return value
	return processLoad;
}
/**
 *   Method Created 7/30/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Vector:
 *    Element 1 = whether the grapes are Organic or Conventional
 */
public static Vector dropDownGrapeBrix(Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();
	
	try { // get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		ddit = dropDownGrapeBrix(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.dropDownGrapeBrix: " + e);
			throwError.append("Error returning connection. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownGrapeBrix(Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	// return value
	return ddit;
}

/**
 * Method Created 7/30/09 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector - whether the grapes are Organic or Conventional
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownGrapeBrix(Vector inValues, 
									   Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector ddit = new Vector();
	ResultSet rs = null;
	Statement listThem = null;
	
	try
	{
		try { // Get the list of Item Type Values - Along with Descriptions
			listThem = conn.createStatement();
			rs = listThem.executeQuery(buildSqlStatement("ddGrapeBrix", inValues));
		} catch(Exception e) {
			throwError.append("Error occured Retrieving or Executing sql (Grape Brix) " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next() && throwError.toString().equals(""))
				{
					DropDownSingle dds = loadFieldsDropDownSingle("grapeBrix", rs);
					ddit.addElement(dds);
				}
			} catch(Exception e)
			{
				throwError.append(" Error occured while Building Vector from sql (Grape Brix). " + e);
			}
		}
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		
		//close result sets / statements.
		try
		{
			if (rs != null)
				rs.close();
			
			if (listThem != null)
				listThem.close();
			
		} catch(Exception e){
			throwError.append("Error closing result set / statement." + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownGrapeBrix(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return ddit;
}

/**
 *   Method Created 12/13/10 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Vector:
 *    
 */
public static Vector dropDownHandlingCode(Vector inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		ddit = dropDownHandlingCode(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		
		try {
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.dropDownHandlingCode: " + e);
			throwError.append("Error closing a connection with Stack 3. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownHandlingCode(Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	// return value
	return ddit;
}

/**
 * Method Created 12/13/10 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * 						// Default would be to select ALL
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownHandlingCode(Vector inValues, 
									  	   Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector ddit = new Vector();
	ResultSet rs = null;
	Statement listThem = null;
	
	try
	{
		try {// Get the list of Item Type Values - Along with Descriptions
			listThem = conn.createStatement();
			rs = listThem.executeQuery(buildSqlStatement("ddHandlingCode", inValues));
		} catch(Exception e) {
		 	throwError.append("Error occured Retrieving or Executing sql (bins). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next() && throwError.toString().equals(""))
				{
					DropDownSingle dds = loadFieldsDropDownSingle("ddHandlingCode", rs);
					ddit.addElement(dds);
				}
			} catch(Exception e) {
				throwError.append(" Error occured while Building Vector from sql statement. " + e);
			}
		} 		
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		
		//close result set / statement.
		try
		{
			if (rs != null)
				rs.close();
			
			if (listThem != null)
				listThem.close();
			
		} catch(Exception e) {
			throwError.append("Error closing result set /statement. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownHandlingCode(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}


	// return value
	return ddit;
}

/**
 *   Method Created 12/13/10 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector of Strings:
 * 					Handling Code Long
 * 
 * @return RawFruitLoad
 * @throws Exception
 */
public static RawFruitLoad findHandlingCode(Vector inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	RawFruitLoad returnValue = new RawFruitLoad();
	Connection conn = null;
	
	try {
		// get a connection to be sent to find methods
		conn = ConnectionStack3.getConnection();
		returnValue = findHandlingCode(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		//return connection.
		try
		{
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.findHandlingCode: " + e);
			throwError.append("Error closing connection. " + e);
		}
		
		// log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("findHandlingCode(");
			throwError.append("Vector). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
 *    Method Created 12/13/10 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the RawFruitLoad Object for use in the SQL statement
 * @return ItemWarehouse
 * @throws Exception
 */
private static RawFruitLoad findHandlingCode(Vector inValues, 
									  		 Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	RawFruitLoad returnValue = new RawFruitLoad();
	ResultSet rs = null;
	Statement listThem = null;
	String requestType = "handlingCode";
	try
	{
		try {
		   listThem = conn.createStatement();
		   rs = listThem.executeQuery(buildSqlStatement(requestType, inValues));
		  
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }
		 
		 try
		 {
			 if (rs.next() && throwError.toString().equals(""))
			 {
				 returnValue = loadFieldsRawFruitLoad(requestType, rs);
			 }// END of the IF Statement
		 } catch(Exception e) {
			throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
	   
		try
		{
			if (rs != null)
				rs.close();
		} catch(Exception e){
			throwError.append("Error closing result set (rs). " + e);
		}
	   
		try
		{
			if (listThem != null)
				listThem.close();
		} catch(Exception e){
			throwError.append("Error closing statement (listThem). " + e);
		}
	   
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("findHandlingCode(");
			throwError.append("Vector, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return returnValue;
}

/**
 *   Method Created 8/20/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Common Request Bean
 * 
 * @return Vector - of DropDownSingle 
 * 
 * Incoming CommonRequestBeanEnvironment, needs:
 *      Environment 
 *      Level 1 - -- Will be coded in this method -- always RFLOCATION
 *      Level 2 - Fill in a ZONE if needed
 *      Level 3 - Order By example, 'Long Description'
 * 
 */
public static Vector dropDownLocation(CommonRequestBean inValues)
{
	Vector ddit = new Vector();
	Connection conn = null;
	StringBuffer throwError = new StringBuffer();
	
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack3();
		ddit = dropDownLocation(inValues, conn);
	} catch (Exception e)
	{
		throwError.append("Error executing method. " + e);
	}
	finally {
		
		try {
			if (conn != null)
				ServiceConnection.returnConnectionStack3(conn);
		} catch(Exception e){
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.dropDownLocation: " + e);
			throwError.append("Error closing a connection with Stack 3. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownLocation(CommonRequestBean). ");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	// return value
	return ddit;
}

/**
 * Method Created 8/20/12 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Common Request Bean
 * 
 * @return Vector - of DropDownSingle 
 * 
 * Incoming CommonRequestBeanEnvironment, needs:
 *      Environment 
 *      Level 1 - -- Will be coded in this method -- always RFLOCATION
 *      Level 2 - Fill in a ZONE if needed
 *      Level 3 - Order By example, 'Long Description'
 */
private static Vector dropDownLocation(CommonRequestBean inValues, 
									   Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector ddit = new Vector();
	ResultSet rs = null;
	Statement listThem = null;
	
	try
	{
		try {// Get the list of Item Type Values - Along with Descriptions
			inValues.setIdLevel1("RFLOCATION");
			
			listThem = conn.createStatement();
			rs = listThem.executeQuery(BuildSQL.sqlDropDownDescriptiveCode(inValues));
		} catch(Exception e) {
		 	throwError.append("Error occured Retrieving or Executing sql (bins). " + e);
		}
		
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next() && throwError.toString().equals(""))
				{
					DropDownSingle dds = LoadFields.loadDropDownDescriptiveCode(rs);
					ddit.addElement(dds);
				}
			} catch(Exception e) {
				throwError.append(" Error occured while Building Vector from sql statement. " + e);
			}
		} 		
	} catch (Exception e)
	{
		//throwError.append(e);
	}
	finally {
		
		//close result set / statement.
		try
		{
			if (rs != null)
				rs.close();
			
			if (listThem != null)
				listThem.close();
			
		} catch(Exception e) {
			throwError.append("Error closing result set /statement. " + e);
		}
		
		// Log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("dropDownLocation(");
			throwError.append("CommonRequestBean, conn). ");
			System.out.println(throwError.toString());
			throw new Exception(throwError.toString());
		}
	}

	// return value
	return ddit;
}

/**
 * Use to Validate a Quick Entry Scale Ticket
 *    // Check in file GRPCSCALE GRPCPO GRPCTICK
 */
public static String verifyQuickEntry(String scaleTicketNo)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	StringBuffer rtnProblem = new StringBuffer();
	Connection conn = null; // New Lawson Box - Lawson Database
	Statement findIt = null;
	Statement checkIt = null;
	ResultSet rsFindIt = null;
	ResultSet rsCheckIt = null;
	
	try { 
		String requestType = "scaleTicketExists";
		String sqlString = "";
			
		// verify incoming Specification
		if (scaleTicketNo == null || scaleTicketNo.trim().equals(""))
			throwError.append(" Scale Ticket must not be null or empty.");
		
		if (throwError.toString().equals(""))
		{
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(scaleTicketNo);
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch (Exception e) {
				throwError.append(" error trying to build first sqlString. ");
			}
		}
		
		// get a connection. execute sql, build return object.
		if (throwError.toString().equals("")) {
			try {
				conn = ConnectionStack3.getConnection();
				findIt = conn.createStatement();
				rsFindIt = findIt.executeQuery(sqlString);
					
				if (rsFindIt.next() && throwError.toString().equals(""))
				{
					//Check to see if it is still available for quick entry.
					try {
						requestType = "scaleTicketAvailable";
						Vector parmClass = new Vector();
						parmClass.addElement(scaleTicketNo);
						sqlString = buildSqlStatement(requestType, parmClass);
						
						checkIt = conn.createStatement();
						rsCheckIt = checkIt.executeQuery(sqlString);
						
						if (rsCheckIt.next())
						{
							//Passes the test and can be used for quick entry
						} else {
							rtnProblem.append("This Ticket is not available for quick entry. ");
						}
					} catch (Exception e) {
						throwError.append(" error trying to check existing ticket. " + e);
					}
				}
					
			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. " + e);
			}
		}
	} catch(Exception e)
	{
		throwError.append(" Problem checking scale ticket for quick entry. " + e);
	// return connection.
	} finally {
		try {
			if (findIt != null)
				findIt.close();
		} catch (Exception e) {}
		
		try {
			if (checkIt != null)
				checkIt.close();
		} catch (Exception e) {}
			
		try {
			if (rsFindIt != null)
				rsFindIt.close();
		} catch (Exception e) {}
		
		try {
			if (rsCheckIt != null)
				rsCheckIt.close();
		} catch (Exception e) {}
			
		try {
			if (conn != null)
				ConnectionStack3.returnConnection(conn);
		} catch (Exception e) {
			System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.verifyBinType: " + e);
			throwError.append("Error closing connection/result set/statement. " + e);
		}
		
		//Log any errors.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceRawFruit.");
			throwError.append("verifyQuickEntry(String:" +  scaleTicketNo + ")");
			System.out.println(throwError.toString());
			Exception e = new Exception();
			e.printStackTrace();
			throw new Exception(throwError.toString());
		}
	}

	// return data.
	return rtnProblem.toString();
}

/**
 * Update data base 
 * for a Raw Fruit Quick Entry Load
 * Process Information to maintain 
 * GRPCSCALE ,GRPCPO ,GRPCTICK
 */
	public static void processLoadQuickEntry(Vector incomingParms) // Send in UpdRawFruitLoad
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		String sqlString = "";
		Statement deletePOs = null;
		Statement deleteLots = null;
		Statement addLoad = null;
		Statement addStuff = null;
		Statement updateLoad = null;
		Statement getCrop = null;
		ResultSet rsGetCrop = null;
		
		
		try { 
			conn = ConnectionStack3.getConnection();
			// Determine which methods need to be processed
			UpdRawFruitLoad ip = (UpdRawFruitLoad) incomingParms.elementAt(0);
			String scaleTicket = ip.getScaleTicket().trim();
			
			//determine bin count from quick entry lots. Update the the UpdRawFruitLoad 
			//class with bin count total.
			int totalBins = 0;
			
			for (int x = 0; x < ip.listQuickEntries.size(); x++)
			{
				UpdRawFruitQuickEntry qe = (UpdRawFruitQuickEntry) ip.listQuickEntries.elementAt(x);
				int bins = 0;
				
				//if it converts then add to bin count.
				try {
					bins = Integer.parseInt(qe.getNumberOfBins());
				} catch (Exception e) { //skip if not numeric
				}
				
				totalBins = totalBins + bins;
			}
			
			ip.setLoadFullBins("" + totalBins);
			
			if (scaleTicket == null)
				scaleTicket = "";
			
			// MUST have a Scale Ticket Number
			if (!scaleTicket.trim().equals(""))
			{
				DateTime dtCurrent = UtilityDateTime.getSystemDate();
				incomingParms.addElement(dtCurrent);
				
				//determine scale ticket facility from scale ticket warehouse.
				if (ip.getWarehouse().trim().equals(""))
				{
					ip.setFacility("");
				} else
				{
					try
					{
						Vector sendValue = new Vector();
						sendValue.addElement(ip.getWarehouse());
						Warehouse whse = ServiceWarehouse.findWarehouse("PRD", sendValue);
						  
				        if (!whse.getFacility().trim().equals(""))
				        	ip.setFacility(whse.getFacility());
					   }
					catch(Exception e)
					{}
				}
				
				String testLoad = verifyLoad(scaleTicket.trim());
				// Should the Scale Ticket Number (Record) be added?
				//     Does a Record Currently Exist?  VerifyLoad
				
				if (testLoad.equals(" Scale Ticket '" + scaleTicket.trim() + "' " + "does not exist. "))
				{
					//clear the message to allow further processing.
					testLoad = "";
					
					// Add a Basic Load Record to be updated later
					try
					{
						Vector parms = new Vector();
						parms.addElement(ip);
						parms.addElement(dtCurrent);
						sqlString = buildSqlStatement("addLoadQuickEntry",parms);
						addLoad = conn.createStatement();
						addLoad.executeUpdate(sqlString);
						addLoad.close();
					}
					catch(Exception e) {
						// Catch a Problem
						throwError.append("Problem occurred when trying to Add a record for Scale Ticket Number: " + scaleTicket + "::" + e);
					}
				} else {
					if (testLoad.equals(""))
						try {
							Vector parms = new Vector();
							parms.addElement(ip);
							parms.addElement(dtCurrent);
							sqlString = buildSqlStatement("updateLoadQuickEntry",parms);
							updateLoad = conn.createStatement();
							updateLoad.executeUpdate(sqlString);
							updateLoad.close();
						} catch (Exception e) {
							throwError.append(" Error using updateLoad. " + e);
						}
						
				}
				
				//continue if no errors and scale ticket is ok.
				if(throwError.toString().trim().equals("") && testLoad.equals("")
				   &&throwError.toString().trim().equals(""))
				{ 
			   	   
					try
					{
						//delete current quick entry lots and po's.
						Vector parms = new Vector();
						parms.addElement(ip);
						sqlString = buildSqlStatement("deleteAllPOsForScaleTicketSequence" ,parms);
						deletePOs = conn.createStatement();
						deletePOs.executeUpdate(sqlString);
						deletePOs.close();
						
						sqlString = buildSqlStatement("deleteAllLotsForScaleTicketSequence" ,parms);
						deleteLots = conn.createStatement();
						deleteLots.executeUpdate(sqlString);
						deleteLots.close();
						
						//roll through all 10 quick entries.
						//track supplier number and lot number in case of duplication
						Vector checkPO = new Vector();
						
						//track PO existence and sequence.
						int poSeq = 0;
						boolean poExists = false;
						
						addStuff = conn.createStatement();
						getCrop  = conn.createStatement();
						
						for (int x = 0; x < ip.listQuickEntries.size(); x++)
						{
							UpdRawFruitQuickEntry qe = (UpdRawFruitQuickEntry) ip.listQuickEntries.elementAt(x);
							
							//Verify a supplier and lot exists in quick entry line.
							if (qe.getSupplier() != null && !qe.getSupplier().trim().equals("") &&
								qe.getLotNumber() != null && !qe.getLotNumber().trim().equals(""))
							{
								//verify PO supplier has not already been added.
								poExists = checkPO.contains(qe.getSupplier().trim());
								
								if (poExists == false)
									checkPO.addElement(qe.getSupplier().trim());
								
								//verify the lot does not exist in the Scale Ticket Lot file.
								String testLot = verifyLot(qe.getLotNumber().trim());
								String rtnMsg = " Lot Number '" + qe.getLotNumber().trim() + "' " + "does not exist. ";
								
								if (testLot.equals(rtnMsg))
								{
									//determine the po sequence number.
									poSeq = checkPO.indexOf(qe.getSupplier().trim());
									
									//add a PO record for this quick entry if needed.
									if (poExists == false)
									{
										parms = new Vector();
										parms.addElement(ip);
										parms.addElement(dtCurrent);
										parms.addElement(Integer.toString(poSeq));
										parms.addElement(qe);
										parms.addElement("209");
										sqlString = buildSqlStatement("addQuickEntryPO", parms);
										addStuff.executeUpdate(sqlString);
									}
									
									//add a Lot record for this quick entry.
									
									//get Item crop if available
									try {
										parms = new Vector();
										parms.addElement(qe.getItemNumber().trim());
										sqlString = buildSqlStatement("getItemCrop", parms);
										rsGetCrop = getCrop.executeQuery(sqlString);
										qe.setItemCrop("");
										
										if (rsGetCrop.next())
										{
											String crop = rsGetCrop.getString("MMITGR").trim();
											
											if (!crop.equals("") && crop.length() > 3 
												&& crop.substring(0, 3).equals("RF-"))
											{
												qe.setItemCrop(crop.substring(3));
											}
										}
									} catch (Exception e) {
										//skip this - no need to stop the insert at this point
									}
									
									
									parms = new Vector();
									parms.addElement(ip);
									parms.addElement(dtCurrent);
									parms.addElement(Integer.toString(poSeq));
									parms.addElement(qe);
									sqlString = buildSqlStatement("addQuickEntryLot", parms);
									addStuff.executeUpdate(sqlString);
								}
							}
						}
						
						addStuff.close();
					}
					catch(Exception e)
					{
						// Catch a Problem
						throwError.append("Problem occurred when trying to Update a record for Scale Ticket Number: " + scaleTicket + "::" + e);
					}	
				}
			}
		} catch(Exception e)
		{
			throwError.append("Error processing Quick Entry. " + e) ;
		} finally {
			try {
				if (conn != null)
					ConnectionStack3.returnConnection(conn);
			} catch (Exception e) {
				System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.processLoadQuickEntry: " + e);
				throwError.append("Error closing connection. " + e);
			}
			
			try { deletePOs.close(); } catch (Exception e) {}
			try { deleteLots.close(); } catch (Exception e) {}
			try { addStuff.close(); } catch (Exception e) {}
			try { addLoad.close(); } catch (Exception e) {}
			try { updateLoad.close(); } catch (Exception e) {}
			try { getCrop.close(); } catch (Exception e) {}
			try { rsGetCrop.close(); } catch (Exception e) {}
			
			if (!throwError.toString().equals("")) {
				throwError.append("Error @ com.treetop.services.");
				throwError.append("ServiceRawFruit.");
				throwError.append("processLoadQuickEntry(Vector)");
				System.out.println(throwError.toString());
				Exception e = new Exception();
				e.printStackTrace();
				throw new Exception(throwError.toString());
			}
		}
		
		// return data.
		return;
	}
	
	
	/**
	 * @param CommonRequestBean - requires envinorment 
	 * @return: Vector of four vectors all HtmlOption objects.
	 * 
	 * Element(0) Items and description
	 * Element(1) Item Models and Items list.
	 * Element(2) Varieties and Model list.
	 * Element(3) Run Types and Model list. 
	 */
		public static Vector buildLotItemVarietyRuntype(CommonRequestBean crb) 
		throws Exception
		{
			Vector ddit = new Vector();
			Connection conn = null;
			StringBuffer throwError = new StringBuffer();
			
			try { // get a connection to be sent to find methods
				conn = ConnectionStack3.getConnection();
				ddit = buildLotItemVarietyRuntype(crb, conn);
			} catch (Exception e)
			{
				throwError.append("Error executing method. " + e);
			}
			finally {
				
				try
				{
					if (conn != null)
						ConnectionStack3.returnConnection(conn);
				} catch(Exception e){
					System.out.println("Error closing a connection with Stack 3 -- ServiceRawFruit.dropDownCategory: " + e);
					throwError.append("Error retunring connection. " + e);
				}
				
				// Log any errors.
				if (!throwError.toString().equals("")) {
					throwError.append("Error @ com.treetop.services.");
					throwError.append("ServiceRawFruit.");
					throwError.append("buildLotItemVarietyRuntype(Vector). ");
					System.out.println(throwError.toString());
					Exception e = new Exception();
					e.printStackTrace();
				}
			}
			
			// return value
			return ddit;
		}
		
		
		
		/**
		 * Return a Vector of HtmlOption objects 
		 * to be used for building drop down lists
		 * in Quick Enry update
		 */
		private static Vector buildLotItemVarietyRuntype(CommonRequestBean crb ,Connection conn)
		throws Exception
		{
			StringBuffer throwError = new StringBuffer();
			Vector htmlOptionLists = new Vector();
			String requestType = "";
			String sqlString = "";
			ResultSet rs = null;
			Statement listThem = null;
			
			try
			{
				listThem = conn.createStatement();
				Vector parms = new Vector();
				parms.addElement(crb);
				
				// Get the list of Item and Descriptions
				try {
					requestType = "itemWithDescription";
					sqlString = buildSqlStatement(requestType, parms);
					rs = listThem.executeQuery(sqlString);
					
					//process rs and build vector of items with descriptions
					Vector hoItems = new Vector();
					
					while (rs.next() && throwError.toString().equals(""))
					{
						HtmlOption ho = new HtmlOption();
						ho.setValue(rs.getString("MMITNO").trim());
						ho.setDescription(rs.getString("MMITDS").trim());
						hoItems.addElement(ho);
					}
					
					htmlOptionLists.addElement(hoItems);
				} catch(Exception e) {
					throwError.append("Error occured itemWithDescription. " + e);
				}
				
				// Get the list of item models with items
				if (throwError.toString().equals(""))
				{
					try
					{
						requestType = "itemModelListItems";
						sqlString = buildSqlStatement(requestType, parms);
						rs = listThem.executeQuery(sqlString);
						Vector hoModels = new Vector();
						HtmlOption ho = new HtmlOption();
						String lastModel = "first";
						String items = "";
						
						//process rs and build vector of models and items.
						while (rs.next() && throwError.toString().equals(""))
						{
							if (lastModel.equals("first"))
							{
								lastModel = rs.getString("mmatmo").trim();
								ho.setValue(rs.getString("mmatmo").trim());
							}
							
							if (rs.getString("mmatmo").trim().equals(lastModel))
							{
								items = ho.getCssClass();
								items = items + rs.getString("MMITNO").trim() + " ";
								ho.setCssClass(items);
							} else {
								hoModels.addElement(ho);
								ho = new HtmlOption();
								ho.setValue(rs.getString("mmatmo").trim());
								ho.setCssClass(rs.getString("MMITNO").trim() + " ") ;
								lastModel = rs.getString("mmatmo").trim();
							}
						}
						
						//pop the last model to the vector.
						hoModels.addElement(ho);
						htmlOptionLists.addElement(hoModels);
						
					} catch(Exception e)
					{
						throwError.append(" Error while building Vector of models. " + e);
					}
				}
				
				//Get the Variety List
				if (throwError.toString().equals(""))
				{
					try
					{
						requestType = "itemVarietyListModels";
						sqlString = buildSqlStatement(requestType, parms);
						rs = listThem.executeQuery(sqlString);
						Vector hoVarieties = new Vector();
						HtmlOption ho = new HtmlOption();
						String lastVariety = "first";
						String models = "";
						
						//process rs and build vector of varities and models.
						while (rs.next() && throwError.toString().equals(""))
						{
							if (lastVariety.equals("first"))
							{
								lastVariety = rs.getString("AJAALF").trim();
								ho.setValue(rs.getString("AJAALF").trim());
							}
							
							if (rs.getString("AJAALF").trim().equals(lastVariety))
							{
								models = ho.getCssClass();
								models = models + rs.getString("MMATMO").trim() + " ";
								ho.setCssClass(models);
							} else {
								hoVarieties.addElement(ho);
								ho = new HtmlOption();
								ho.setValue(rs.getString("AJAALF").trim());
								ho.setCssClass(rs.getString("MMATMO").trim() + " ") ;
								lastVariety = rs.getString("AJAALF").trim();
							}
						}
						
						//pop the last model to the vector.
						hoVarieties.addElement(ho);
						htmlOptionLists.addElement(hoVarieties);
						
					} catch(Exception e)
					{
						throwError.append(" Error while building Vector of Varieties. " + e);
					}
				}
				
				//Get the Run Type List
				if (throwError.toString().equals(""))
				{
					try
					{
						requestType = "itemRunTypeListModels";
						sqlString = buildSqlStatement(requestType, parms);
						rs = listThem.executeQuery(sqlString);
						Vector hoRunTypes = new Vector();
						HtmlOption ho = new HtmlOption();
						String lastRunType = "first";
						String runTypes = "";
						
						//process rs and build vector of varities and models.
						while (rs.next() && throwError.toString().equals(""))
						{
							if (lastRunType.equals("first"))
							{
								lastRunType = rs.getString("AJAALF").trim();
								ho.setValue(rs.getString("AJAALF").trim());
							}
							
							if (rs.getString("AJAALF").trim().equals(lastRunType))
							{
								runTypes = ho.getCssClass();
								runTypes = runTypes + rs.getString("MMATMO").trim() + " ";
								ho.setCssClass(runTypes);
							} else {
								hoRunTypes.addElement(ho);
								ho = new HtmlOption();
								ho.setValue(rs.getString("AJAALF").trim());
								ho.setCssClass(rs.getString("MMATMO").trim() + " ") ;
								lastRunType = rs.getString("AJAALF").trim();
							}
						}
						
						//pop the last model to the vector.
						hoRunTypes.addElement(ho);
						htmlOptionLists.addElement(hoRunTypes);
						
					} catch(Exception e)
					{
						throwError.append(" Error while building Vector of Run Types. " + e);
					}
				}
				
			} catch (Exception e)
			{
				throwError.append("Error in the main try catch. " + e);
			}
			finally {
				
				//close result sets / statements.
				try { listThem.close(); } catch(Exception e) {}
				try { rs.close(); } catch(Exception e) {}
				
				// Log any errors.
				if (!throwError.toString().trim().equals(""))
				{
					throwError.append(" @ com.treetop.Services.");
					throwError.append("ServiceRawFruit.");
					throwError.append("buildLotItemVarietyRuntype(");
					throwError.append("Vector, conn). ");
					System.out.println(throwError.toString());
					throw new Exception(throwError.toString());
				}
			}

			// return value
			return htmlOptionLists;
		}
	
}

