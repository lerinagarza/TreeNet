/*
 * Created on May 9, 2008
 *
 */
package com.treetop.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.lawson.api.ATS101MI;
import com.lawson.api.ATS101MIaddItmLotAttrTx;
import com.lawson.api.MMS850MI;
import com.lawson.api.MMS850MIAddRclLotSts;
import com.treetop.app.inventory.InqInventory;
import com.treetop.app.inventory.UpdInventory;
import com.treetop.businessobjectapplications.BeanInventory;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.Inventory;
import com.treetop.businessobjects.SalesOrderLineItem;
import com.treetop.businessobjects.Supplier;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.viewbeans.CommonRequestBean;
/**
 * @author twalto .
 *
 * Retrieve Inventory Related Information
 * 
 * 9/24/13 - TWalton Removed Methods updateSystemAgedRemark -- these were moved into CronTimer
 * 
 */
public class ServiceInventory extends BaseService {
	// Make sure the Library is correct
	public static final String library = "M3DJDPRD.";
	//	public static final String library = "M3DJDTST.";
	public static final String ttlib = "DBPRD.";
	//	public static final String ttlib = "DBTST.";
	/**
	 * 
	 */
	public ServiceInventory() {
		super();
	}

	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		try {
			
			// testing user profile defaults.
			
			if (1 == 1)  
			{
				CommonRequestBean commonBean = new CommonRequestBean();
				commonBean.setEnvironment("TST");
				commonBean.setCompanyNumber("100");
				commonBean.setIdLevel1("346");

				BeanInventory result = new BeanInventory();	
				result = updateInspectionStatus(commonBean);
				String breakPoint = "stopHere";			
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Load class fields from result set.
	 */

	public static Inventory loadFields(String requestType,
			ResultSet rs)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Inventory returnClass = new Inventory();

		try{ 
			if (requestType.equals("loadFIFO") ||
				requestType.equals("listLotReclass") ||
				requestType.equals("listLotAttribute") )
			{
				try
				{
					//					 Inventory -- Location Detail - Balance ID File -- MITLOC -- 
					returnClass.setCompany(rs.getString("MLCONO").trim());
					returnClass.setFacility(rs.getString("MLFACI").trim());
					returnClass.setWarehouse(rs.getString("MLWHLO").trim());
					returnClass.setLocation(rs.getString("MLWHSL").trim());
					returnClass.setItemNumber(rs.getString("MLITNO").trim());
					returnClass.setLotNumber(rs.getString("MLBANO").trim());
					returnClass.setLotRef1(rs.getString("MLBREF").trim());
					returnClass.setLotRef2(rs.getString("MLBRE2").trim());
					returnClass.setQuantityOnHand(rs.getString("MLSTQT").trim());
					returnClass.setLastSalesDate(rs.getString("MLSEDT").trim());
					returnClass.setPriorityDate(rs.getString("MLPRDT").trim());
					returnClass.setStatus(rs.getString("MLSTAS").trim());
					returnClass.setCheckedValue("Y");
					
					// Lot master file
					returnClass.setAttributeNumber(rs.getString("LMATNR"));
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}
				try
				{
					//					Item Master file -- MITMAS -- 
					returnClass.setItemDescription(rs.getString("MMITDS").trim());
					if (requestType.equals("listLotReclass") || requestType.equals("listLotAttribute"))
					{
						returnClass.setItemGroup(rs.getString("MMITGR").trim());
						returnClass.setItemType(rs.getString("MMITTY").trim());
					}
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}		
				try
				{
					//					Warehouse Master file -- MITWHL -- 
					returnClass.setWarehouseDescription(rs.getString("MWWHNM").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}
				if (requestType.equals("listLotReclass")|| requestType.equals("listLotAttribute"))
				{
					try
					{
						//						Facility Master file -- CFACIL -- 
						returnClass.setFacilityDescription(rs.getString("CFFACN").trim());
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
					}
				}
			}
			if (requestType.equals("listRF"))
			{
				try
				{
					//					 Inventory -- Location Detail - Balance ID File -- MITLOC -- 
					returnClass.setItemNumber(rs.getString("MLITNO").trim());
					returnClass.setLotNumber(rs.getString("MLBANO").trim());
					returnClass.setWarehouse(rs.getString("MLWHLO").trim());
					returnClass.setLocation(rs.getString("MLWHSL").trim());
					returnClass.setQuantityOnHand(rs.getString(12));
					returnClass.setQuantityInTons(rs.getString(13));
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}
				try
				{
					//						Item Master file -- MITMAS -- 
					returnClass.setItemDescription(rs.getString("MMITDS").trim());
					if (rs.getString("MMEVGR").trim().equals("OR"))
					   returnClass.setConventionalOrganic("ORG");
					else
					   returnClass.setConventionalOrganic(rs.getString("MMEVGR").trim());
				}
				catch(Exception e)
				{
					//			// Problem Caught when loading the SalesOrderHeader/Detail information
				}	
				try
				{  // MILOMA
					returnClass.setReceiptDate(rs.getString("LMREDA"));
				}
				catch(Exception e)
				{}
				try
				{  // CIDMAS
					if (rs.getString("IDSUNO") == null ||
							rs.getString("IDSUNM") == null)
						returnClass.setSupplier(new Supplier());
					else
						returnClass.setSupplier(ServiceSupplier.loadFieldsSupplier("basic", rs));
				}
				catch(Exception e)
				{}
				try
				{  // MIATTR --TGRAD
					returnClass.setGrade(rs.getString(6).trim());
				}
				catch(Exception e)
				{}
				try
				{  // MIATTR --VAR
					returnClass.setVariety(rs.getString(8).trim());
				}
				catch(Exception e)
				{}
				try
				{  // MIATTR --Number of Bins Avg. Weight per Bin to Calculate Number of Bins
					BigDecimal numberOfBins = new BigDecimal("0");
					try
					{
						BigDecimal avgWgtPerBin = rs.getBigDecimal(14);
						if (avgWgtPerBin.compareTo(new BigDecimal("0")) != 0)
							numberOfBins = new BigDecimal(returnClass.getQuantityOnHand()).divide(avgWgtPerBin, 0);
					}
					catch(Exception e)
					{}
					returnClass.setNumberOfBins(numberOfBins.toString());
				}
				catch(Exception e)
				{}
				try
				{  // MIATTR --Run -- Run Type
					returnClass.setRunType(rs.getString(15).trim());
				}
				catch(Exception e)
				{}
				try
				{  // MIATTR --COO - Country of Origin
					returnClass.setCountryOfOrigin(rs.getString(16).trim());
				}
				catch(Exception e)
				{}
			}
			if (requestType.equals("listRFSummary"))
			{
				try
				{
					//					 Inventory -- Location Detail - Balance ID File -- MITLOC --
					returnClass.setQuantityOnHand(rs.getString(4));
					returnClass.setQuantityInTons(rs.getString(5));
					if (rs.getString("MMEVGR").trim().equals("OR"))
					   returnClass.setConventionalOrganic("ORG");
					else
					   returnClass.setConventionalOrganic(rs.getString("MMEVGR").trim());

				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}
				try
				{  // MIATTR --TGRAD
					returnClass.setGrade(rs.getString(1).trim());
				}
				catch(Exception e)
				{}
				try
				{  // MIATTR --VAR
					returnClass.setVariety(rs.getString(3).trim());
				}
				catch(Exception e)
				{}
			}
			if (requestType.equals("ddFacility"))
			{
				returnClass.setFacility(rs.getString("CFFACI").trim());
				returnClass.setFacilityDescription(rs.getString("CFFACN").trim());
			}
			if (requestType.equals("ddWarehouse"))
			{
				returnClass.setWarehouse(rs.getString("MWWHLO").trim());
				returnClass.setWarehouseDescription(rs.getString("MWWHNM").trim());
			}
			if (requestType.equals("ddItemType"))
			{
				returnClass.setItemType(rs.getString("CTSTKY").trim());
				returnClass.setItemTypeDescription(rs.getString("CTTX40").trim());
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Inventory class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceInventory.");
			throwError.append("loadFields(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnClass;
	}

	/**
	 * Build an sql statement.
	 * @param String environment
	 * @param String request type
	 * @param Vector request class
	 * @return sql string -- the SQL Statement
	 * @throws Exception
	 */

	private static String buildSqlStatement(String environment,
			String inRequestType,
			Vector inParms)
	throws Exception 
	{
		StringBuffer sqlString = new StringBuffer();
		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer throwError = new StringBuffer();

		try {
			//****************************************************************************
			//** FIND
			if (inRequestType.equals("findFIFO"))
			{
				// Send in Item, and Warehouse to get listing
				String itemNumber = (String) inParms.elementAt(0);
				String whseNumber = (String) inParms.elementAt(1);
				String plannedShipDate = (String) inParms.elementAt(2);

				// build the sql statement.
				// MITLOC file
				sqlString.append("SELECT MLCONO, MLFACI, MLWHLO, MLWHSL, MLITNO, ");
				sqlString.append("  MLBANO, MLBREF, MLBRE2, MLSTQT, MLSEDT, MLPRDT, ");
				sqlString.append("  MLSTAS, MLALOC, ");
				// MITMAS file
				sqlString.append("  MMITDS, ");
				// MITWHL file
				sqlString.append("  MWWHNM ");
				//FROM STATEMENT	
				sqlString.append("FROM " + library + "MITLOC ");
				sqlString.append("  INNER JOIN " + library + "MITMAS ");
				sqlString.append("     ON MMITNO = MLITNO ");
				sqlString.append("  INNER JOIN " + library + "MITWHL ");
				sqlString.append("     ON MWWHLO = MLWHLO ");
				// WHERE STATEMENT
				sqlString.append(" WHERE MLSTAS = '2' "); // Status is Released
				sqlString.append("   AND MLALOC = '1' "); // Balance ID can be Automatically Allocated
				sqlString.append("   AND MLWHLO = '" + whseNumber + "' "); // Warehouse Specific
				sqlString.append("   AND MLITNO = '" + itemNumber + "' "); // Item Number Specific
				//			if (plannedShipDate != null && !plannedShipDate.trim().equals("0") && !plannedShipDate.trim().equals(""))
				//				sqlString.append("   AND MLSEDT <= " + plannedShipDate + " ");
				// ORDER BY
				sqlString.append(" ORDER BY MLPRDT "); // Order by Priority Date
			}
			if (inRequestType.equals("findLotsChangeStatus1"))
			{
				// MILOMA FILE --- Lot Master
				sqlString.append("SELECT LMITNO, LMBANO, MLWHLO ");
				// Can Add Additional fields if needed
				sqlString.append("FROM " + library + "MILOMA ");
				//				// MITLOC FILE --- Balance ID File  -- Not currently using
				sqlString.append("INNER JOIN " + library + "MITLOC ");
				sqlString.append("   ON  LMCONO = MLCONO ");
				sqlString.append("   AND LMITNO = MLITNO ");
				sqlString.append("   AND LMBANO = MLBANO");
				// MITMAS FILE --- Item Master
				sqlString.append(" INNER JOIN " + library + "MITMAS ");
				sqlString.append("    ON  LMCONO = MMCONO ");
				sqlString.append("    AND LMITNO = MMITNO ");
				sqlString.append("    AND MMITTY IN ('100', '110', '120') ");
				// MITBAL FILE --- Item Warehouse Master
				sqlString.append(" INNER JOIN " + library + "MITBAL ");
				sqlString.append("    ON  LMCONO = MBCONO ");
				sqlString.append("    AND LMITNO = MBITNO ");
				sqlString.append("    AND MBWHLO IN ('209', '469', '240', '490', '230', '290') ");
				sqlString.append("    AND MBAGDY <> 0 ");
				//WHERE
				sqlString.append(" WHERE LMSTAS = '1' ");
				int dateToday = 0;
				try
				{
					DateTime dt = UtilityDateTime.getSystemDate();
					//    If today's date is Less than start date for the chosen week, include the "RFF"
					dateToday = (new Integer(dt.getDateFormatyyyyMMdd())).intValue();
				}
				catch(Exception e)
				{}
				sqlString.append("   AND LMRCLS < " + dateToday + " ");
				// WHERE EXISTS
				//  MITTRA -- Transaction Detail
				sqlString.append("   AND NOT EXISTS (SELECT * FROM " + library + "MITTRA ");
				sqlString.append("         WHERE LMCONO = MTCONO ");
				sqlString.append("           AND LMITNO = MTITNO ");
				sqlString.append("           AND LMBANO = MTBANO ");
				sqlString.append("           AND MTTTID = 'REC') ");
				//			sqlString.append("  AND LMITNO = '100943' ");
				// GROUP BY
				sqlString.append("GROUP BY LMITNO, LMBANO, MLWHLO ");
				sqlString.append("ORDER BY LMITNO, LMBANO, MLWHLO ");
			}
			
			if (inRequestType.equals("findLotsChangeStatus2"))
			{
				CommonRequestBean reclassify = (CommonRequestBean) inParms.elementAt(0);				
				String libraryM3 = GeneralUtility.getLibrary(environment.trim()); 
				
				sqlString.append("SELECT LMCONO, LMFACI, LMBANO, LMITNO, LMSTAS, LMQIAD, CFWHLO, CFFACN, MWWHNM ");
				
				sqlString.append("FROM " + libraryM3 + ".MILOMA ");
				
				sqlString.append("JOIN " + libraryM3 + ".CFACIL ");
				sqlString.append("ON LMCONO = CFCONO ");
				sqlString.append("   AND LMFACI = CFFACI ");
				
				sqlString.append("JOIN " + libraryM3 + ".MITLOC ");
				sqlString.append("ON LMCONO = MLCONO ");
				sqlString.append("   AND CFWHLO <> MLWHLO");
				sqlString.append("   AND LMBANO = MLBANO");
				sqlString.append("   AND LMITNO = MLITNO ");
				sqlString.append("   AND LMSTAS = MLSTAS ");
				sqlString.append("   AND SUBSTRING(MLWHSL,1,5) <> '=>' ");
				
				sqlString.append("JOIN " + libraryM3 + ".MITWHL ");
				sqlString.append("ON MLCONO = MWCONO ");
				sqlString.append("   AND MLWHLO = MWWHLO ");
			
				sqlString.append("WHERE");
				sqlString.append(" LMCONO = '" + reclassify.getCompanyNumber().trim() + "' AND ");
				sqlString.append(" LMFACI = '" + reclassify.getIdLevel1().trim() + "' AND ");
				sqlString.append(" LMSTAS = '2' AND ");				
				sqlString.append(" LMQIAD > 0 AND ");
				
				sqlString.append("ORDER BY MLCONO, MLWHLO, MLITNO, MLBANO ");
			}

			//****************************************************************************
			//** LIST
			if (inRequestType.equals("acidList"))
			{
				// Currently not using anything sent in
				// build the sql statement.
				// MITLOC file - Balance ID
				sqlSelect.append("SELECT MLCONO, MLFACI, MLWHLO, MLWHSL, MLITNO, ");
				sqlSelect.append("  MLBANO, MLSTAS, SUM(MLSTQT), ");
				sqlString.append("FROM " + library + "MITLOC ");
				// MITMAS file -- Item Master
				sqlSelect.append("  MMITDS, MMITGR, ");
				sqlString.append("  INNER JOIN " + library + "MITMAS ");
				sqlString.append("     ON MMCONO = MLCONO AND MMITNO = MLITNO ");
				sqlString.append("    AND MMITTY = '150' "); // Concentrate
				sqlString.append("    AND MMITCL = '600' "); // Apple Concentrate
				// MITWHL file -- Warehouse Master
				sqlSelect.append("  MWWHNM, ");
				sqlString.append("  INNER JOIN " + library + "MITWHL ");
				sqlString.append("     ON MWCONO = MLCONO AND MWWHLO = MLWHLO ");
				// CFACIL file -- Facility Master
				sqlSelect.append("  CFFACN, ");
				sqlString.append("  INNER JOIN " + library + "CFACIL ");
				sqlString.append("     ON CFCONO = MLCONO AND MLFACI = CFFACI ");
				// MIATTR file -- Attributes tied to Lots
				sqlSelect.append("  AGATVA, AGATVN, AGATID, ");
				sqlString.append("  LEFT OUTER JOIN " + library + "MIATTR ");
				sqlString.append("     ON MLCONO = AGCONO AND MLITNO = AGITNO ");
				sqlString.append("    AND MLBANO = AGBANO ");
				sqlString.append("    AND (AGATID = 'BRIX' OR AGATID = 'ACID MAL W/V' OR AGATID = 'ACID MAL W/W') ");
				// MATRMA file -- Attribute Master
				sqlSelect.append("  AATX15, AATX30, AAATVC, AADCCD, ");
				sqlString.append("  LEFT OUTER JOIN " + library + "MATRMA ");
				sqlString.append("    ON MLCONO = AACONO AND AGATID = AAATID ");
				// BRIXFILE file -- Brix Table
				sqlSelect.append("  GNBBRX, GNBFSG ");
				sqlString.append("  LEFT OUTER JOIN " + ttlib + "BRIXFILE ");
				sqlString.append("    ON AGATVN = GNBBRX ");
				sqlString.append("   AND AGATID = 'BRIX' ");
				// WHERE STATEMENT
				// Not currently needed
				//	sqlString.append(" WHERE MLFACI = '305' ");
				//11/24/10 - TWalton -- Add select by company 100 -- should make it go a bit faster as well
				sqlString.append (" WHERE MLCONO = '100' ");
				// GROUP / ORDER STATEMENTS	
				sqlString.append(" GROUP BY MLCONO, MLFACI, MLWHLO, MLWHSL, MLITNO, MLBANO, MLSTAS, ");
				sqlString.append("          MMITDS, MMITGR, MWWHNM, CFFACN, AGATVA, AGATVN, AGATID, ");
				sqlString.append("          AATX15, AATX30, AAATVC, AADCCD, GNBBRX, GNBFSG ");
				//11/24/10 TWalton -- change the order by per conversation with the concentrate Planner (Ronnie Butler)
				//sqlString.append(" ORDER BY MLCONO, MLFACI, MLWHLO, MLITNO, MLWHSL, MLBANO, AGATID "); 
				sqlString.append(" ORDER BY MLCONO, MLITNO, MLFACI, MLWHLO, MLWHSL, MLBANO, AGATID "); 
			}
			if (inRequestType.equals("listLotReclass") || inRequestType.equals("listLotAttribute"))
			{
				InqInventory ii = (InqInventory) inParms.elementAt(0);
				String library = GeneralUtility.getLibrary(ii.getEnvironment()) + ".";
				
				
				
				// MITLOC file - Balance ID
				sqlSelect.append("SELECT MLCONO, MLFACI, MLWHLO, MLWHSL, MLITNO, ");
				sqlSelect.append("  MLBANO, MLSTAS, MLSTQT, MLBREF, MLBRE2, MLSEDT, MLPRDT,  ");
				sqlString.append("FROM " + library + "MITLOC ");
				// MITMAS file -- Item Master
				sqlSelect.append("  MMITDS, MMITGR, MMITTY, ");
				sqlString.append("  INNER JOIN " + library + "MITMAS ");
				sqlString.append("     ON MLCONO = MMCONO AND MMITNO = MLITNO ");
				sqlString.append("    AND MMINDI = 3 "); // Lot Controlled
				// MILOMA file -- Lot Master
				sqlSelect.append(" LMATNR, ");
				sqlString.append("  INNER JOIN " + library + "MILOMA ");
				sqlString.append("     ON MLCONO = LMCONO AND LMITNO = MLITNO ");
				sqlString.append("    AND LMBANO = MLBANO "); // Lot In Lot Master
				
				// MSYTXLVHC view -- Holds comment view
				sqlSelect.append("  ifNull(HCTX60,'') as HCTX60, ");
				sqlString.append("  LEFT OUTER JOIN " + library + "MSYTXLVHC ");
				sqlString.append("     ON LMITNO=HCITNO AND LMBANO=HCBANO AND LMATNR=HCATNR ");
				
				// MITWHL file -- Warehouse Master
				sqlSelect.append("  MWWHNM, ");
				sqlString.append("  INNER JOIN " + library + "MITWHL ");
				sqlString.append("     ON MLCONO = MWCONO AND MWWHLO = MLWHLO ");
				// CFACIL file -- Facility Master
				sqlSelect.append("  CFFACN ");
				sqlString.append("  INNER JOIN " + library + "CFACIL ");
				sqlString.append("     ON MLCONO = CFCONO AND MLFACI = CFFACI ");


				// WHERE STATEMENT
				sqlString.append("WHERE ");
				sqlString.append(" MLBANO <> ' ' ");
				
				
				if(inRequestType.equals("listLotReclass")) {
					sqlString.append(" AND MLSTAS = '" + ii.getInqStatusFrom().trim() + "' ");
				}
				
				
				if(inRequestType.equals("listLotAttribute")) {
					if (!ii.getInqStatus().trim().equals("")) {
						sqlString.append(" AND MLSTAS = '" + ii.getInqStatus().trim() + "' ");
					}
				}
				
			
				if (!ii.getInqFacility().trim().equals(""))
					sqlString.append("AND MLFACI = '" + ii.getInqFacility().trim() + "' ");
				if (!ii.getInqWhse().trim().equals(""))
					sqlString.append("AND MLWHLO = '" + ii.getInqWhse().trim() + "' ");
				if (!ii.getInqItemType().trim().equals(""))
					sqlString.append("AND MMITTY = '" + ii.getInqItemType().trim() + "' ");
				if (!ii.getInqItem().trim().equals(""))
					sqlString.append("AND MLITNO = '" + ii.getInqItem().trim() + "' ");
				
				
				//Remark  - Hold Comments
				if (!ii.getInqRemark().trim().equals("")) {
					sqlString.append("AND HCTX60 LIKE '%" + ii.getInqRemark().trim() + "%' ");
				}
				
				//if (!ii.getInqRemark().trim().equals(""))
				//	sqlString.append("AND MLBREM LIKE '%" + ii.getInqRemark().trim() + "%' ");

				
				
				if (!ii.getInqLotRef1().trim().equals(""))
					sqlString.append("AND LMBREF = '" + ii.getInqLotRef1().trim() + "' ");
				if (!ii.getInqLotRef2().trim().equals(""))
					sqlString.append("AND LMBRE2 = '" + ii.getInqLotRef2().trim() + "' ");
				if (!ii.getInqOwnerCode().trim().equals(""))
					sqlString.append("AND LMOWNC = '" + ii.getInqOwnerCode().trim() + "' ");
				if (!ii.getInqTaggingType().trim().equals(""))
					sqlString.append("AND LMIDET = '" + ii.getInqTaggingType().trim() + "' ");

				if (!ii.getInqLot1().trim().equals("") ||
						!ii.getInqLot2().trim().equals("") ||
						!ii.getInqLot3().trim().equals("") ||
						!ii.getInqLot4().trim().equals("") ||
						!ii.getInqLot5().trim().equals(""))
				{
					// Specific Chosen Lots
					sqlString.append("AND MLBANO IN(");
					StringBuffer chosenLots = new StringBuffer();
					if (!ii.getInqLot1().trim().equals(""))
						chosenLots.append("'" + ii.getInqLot1().trim() + "'");
					if (!ii.getInqLot2().trim().equals(""))
					{
						if (!chosenLots.toString().trim().equals(""))
							chosenLots.append(", ");
						chosenLots.append("'" + ii.getInqLot2().trim() + "'");
					}
					if (!ii.getInqLot3().trim().equals(""))
					{
						if (!chosenLots.toString().trim().equals(""))
							chosenLots.append(", ");
						chosenLots.append("'" + ii.getInqLot3().trim() + "'");
					}
					if (!ii.getInqLot4().trim().equals(""))
					{
						if (!chosenLots.toString().trim().equals(""))
							chosenLots.append(", ");
						chosenLots.append("'" + ii.getInqLot4().trim() + "'");
					}
					if (!ii.getInqLot5().trim().equals(""))
					{
						if (!chosenLots.toString().trim().equals(""))
							chosenLots.append(", ");
						chosenLots.append("'" + ii.getInqLot5().trim() + "'");
					}
					sqlString.append(chosenLots);
					sqlString.append(") ");
				}
				else
				{
					// Range of Dates
					if (!ii.getInqLotFrom().trim().equals(""))
						sqlString.append("AND MLBANO >= '" + ii.getInqLotFrom().trim() + "' ");
					if (!ii.getInqLotTo().trim().equals(""))
						sqlString.append("AND MLBANO <= '" + ii.getInqLotTo().trim() + "' ");
				}
				// GROUP / ORDER STATEMENTS	
				sqlString.append(" ORDER BY MLCONO, MLFACI, MLWHLO, MLITNO, MLWHSL, MLBANO "); 
			}



			if (inRequestType.equals("listRF"))
			{
				InqInventory ii = (InqInventory) inParms.elementAt(0);
				sqlString.append("SELECT MLWHLO, MLITNO, MLWHSL, MMITDS, MLBANO, A.AGATVA, MMEVGR, ");
				sqlString.append("       B.AGATVA, LMREDA, IDSUNO, IDSUNM, SUM(MLSTQT), ");
				sqlString.append("       (SUM(MLSTQT)/2000), C.AGATVN, D.AGATVA, E.AGATVA ");
				//				sqlString.append("         CASE ");
				//				sqlString.append("         WHEN C.AGATVN = 0 THEN 0 ");
				//				sqlString.append("         ELSE (SUM(MLSTQT)/C.AGATVN) ");
				//				sqlString.append("         END ");

				sqlString.append("FROM " + library + "MITLOC ");
				sqlString.append("INNER JOIN " + library + "MITMAS ");
						// 9/19/12 -TW- Added Zero Cost Item Type 205 
				sqlString.append("   ON MLCONO = MMCONO AND MLITNO = MMITNO AND (MMITTY = '200' OR MMITTY = '205') "); 
				sqlString.append("INNER JOIN " + library + "MILOMA ");
				sqlString.append("   ON MLCONO = LMCONO AND MLITNO = LMITNO AND MLBANO = LMBANO ");
				sqlString.append("LEFT OUTER JOIN " + library + "MIATTR A ");
				sqlString.append("   ON MLCONO = A.AGCONO AND MLITNO = A.AGITNO AND A.AGBANO = MLBANO ");
				sqlString.append("  AND A.AGATNR = LMATNR AND A.AGATID = 'TGRAD' ");
				sqlString.append("LEFT OUTER JOIN " + library + "MIATTR B ");
				sqlString.append("   ON MLCONO = B.AGCONO AND MLITNO = B.AGITNO AND B.AGBANO = MLBANO ");
				sqlString.append("  AND B.AGATNR = LMATNR AND B.AGATID = 'VAR' ");
				sqlString.append("LEFT OUTER JOIN " + library + "MIATTR C ");
				sqlString.append("   ON MLCONO = C.AGCONO AND MLITNO = C.AGITNO AND C.AGBANO = MLBANO ");
				sqlString.append("  AND C.AGATNR = LMATNR AND C.AGATID = 'AVG WT CTNR' ");
				sqlString.append("LEFT OUTER JOIN " + library + "MIATTR D ");
				sqlString.append("   ON MLCONO = D.AGCONO AND MLITNO = D.AGITNO AND D.AGBANO = MLBANO ");
				sqlString.append("  AND D.AGATNR = LMATNR AND D.AGATID = 'RUN' ");
				sqlString.append("LEFT OUTER JOIN " + library + "MIATTR E ");
				sqlString.append("   ON MLCONO = E.AGCONO AND MLITNO = E.AGITNO AND E.AGBANO = MLBANO ");
				sqlString.append("  AND E.AGATNR = LMATNR AND E.AGATID = 'COO' ");
				sqlString.append("LEFT OUTER JOIN " + library + "CIDMAS ");
				sqlString.append("   ON MLCONO = IDCONO AND LMSUNO = IDSUNO ");
				sqlString.append("WHERE MLCONO = '100' ");
				if (!ii.getInqFacility().equals(""))
				{
					sqlString.append(" AND MLFACI = '" + ii.getInqFacility() + "' ");
					if (!ii.getInqWhse().equals(""))
						sqlString.append(" AND MLWHLO = '" + ii.getInqWhse() + "' ");
				}
				if (!ii.getInqOrganicConventional().equals(""))
				{
					sqlString.append(" AND MMEVGR = '" + ii.getInqOrganicConventional().trim() + "' ");
				}
				if (!ii.getInqTGRADE().trim().equals(""))
				{
					sqlString.append(" AND A.AGATVA = '" + ii.getInqTGRADE().trim() + "' ");
				}
				if (!ii.getInqVariety().trim().equals(""))
				{
					sqlString.append(" AND B.AGATVA = '" + ii.getInqVariety().trim() + "' ");
				}
				if (!ii.getInqRunType().equals(""))
				{
					sqlString.append("  AND D.AGATVA = '" + ii.getInqRunType().trim() + "' ");
				}
				if (!ii.getInqCOO().equals(""))
				{
					sqlString.append("  AND E.AGATVA = '" + ii.getInqCOO().trim() + "' ");
				}
				if (!ii.getInqRFItemGroup().equals("")) // added 1/29/13 TWalton
				{
					sqlString.append("  AND MMITGR = '" + ii.getInqRFItemGroup().trim() + "' ");
				}
				if (!ii.getInqLocation().trim().equals(""))
				{
					sqlString.append("  AND MLWHSL LIKE '" + ii.getInqLocation().trim() + "%' ");
				}
				//				sqlString.append(" WHERE MLBANO = '114664' ");
				sqlString.append(" GROUP BY MLWHLO, MLITNO, MLWHSL, MMITDS, MLBANO, LMREDA, IDSUNO, IDSUNM, ");
				sqlString.append("       A.AGATVA, MMEVGR, B.AGATVA, C.AGATVN, D.AGATVA, E.AGATVA ");
				sqlString.append(" ORDER BY A.AGATVA, MMEVGR, B.AGATVA, MLITNO, LMREDA ");
			}
			if (inRequestType.equals("listRFSummary"))
			{
				InqInventory ii = (InqInventory) inParms.elementAt(0);
				sqlString.append("SELECT A.AGATVA, MMEVGR, B.AGATVA, SUM(MLSTQT), (SUM(MLSTQT)/2000) ");
				sqlString.append("FROM " + library + "MITLOC ");
				sqlString.append("INNER JOIN " + library + "MITMAS ");
				// 9/19/12 -TW- Added Zero Cost Item Type 205
				sqlString.append("   ON MLCONO = MMCONO AND MLITNO = MMITNO AND (MMITTY = '200' OR MMITTY = '205') ");
				sqlString.append("INNER JOIN " + library + "MILOMA ");
				sqlString.append("   ON MLCONO = LMCONO AND MLITNO = LMITNO AND MLBANO = LMBANO ");
				sqlString.append("LEFT OUTER JOIN " + library + "MIATTR A ");
				sqlString.append("   ON MLCONO = A.AGCONO AND MLITNO = A.AGITNO AND A.AGBANO = MLBANO ");
				sqlString.append("  AND A.AGATNR = LMATNR AND A.AGATID = 'TGRAD' ");
				sqlString.append("LEFT OUTER JOIN " + library + "MIATTR B ");
				sqlString.append("   ON MLCONO = B.AGCONO AND MLITNO = B.AGITNO AND B.AGBANO = MLBANO ");
				sqlString.append("  AND B.AGATNR = LMATNR AND B.AGATID = 'VAR' ");
				sqlString.append("LEFT OUTER JOIN " + library + "MIATTR D ");
				sqlString.append("   ON MLCONO = D.AGCONO AND MLITNO = D.AGITNO AND D.AGBANO = MLBANO ");
				sqlString.append("  AND D.AGATNR = LMATNR AND D.AGATID = 'RUN' ");
				sqlString.append("LEFT OUTER JOIN " + library + "MIATTR E ");
				sqlString.append("   ON MLCONO = E.AGCONO AND MLITNO = E.AGITNO AND E.AGBANO = MLBANO ");
				sqlString.append("  AND E.AGATNR = LMATNR AND E.AGATID = 'COO' ");
				sqlString.append("WHERE MLCONO = '100' ");
				if (!ii.getInqFacility().equals(""))
				{
					sqlString.append(" AND MLFACI = '" + ii.getInqFacility() + "' ");
					if (!ii.getInqWhse().equals(""))
						sqlString.append(" AND MLWHLO = '" + ii.getInqWhse() + "' ");
				}
				if (!ii.getInqOrganicConventional().equals(""))
				{
					sqlString.append(" AND MMEVGR = '" + ii.getInqOrganicConventional().trim() + "' ");
				}
				if (!ii.getInqTGRADE().trim().equals(""))
				{
					sqlString.append(" AND A.AGATVA = '" + ii.getInqTGRADE().trim() + "' ");
				}
				if (!ii.getInqVariety().trim().equals(""))
				{
					sqlString.append(" AND B.AGATVA = '" + ii.getInqVariety().trim() + "' ");
				}
				if (!ii.getInqRunType().equals(""))
				{
					sqlString.append("  AND D.AGATVA = '" + ii.getInqRunType().trim() + "' ");
				}
				if (!ii.getInqCOO().equals(""))
				{
					sqlString.append("  AND E.AGATVA = '" + ii.getInqCOO().trim() + "' ");
				}
				if (!ii.getInqRFItemGroup().equals("")) // added 1/29/13 TWalton
				{
					sqlString.append("  AND MMITGR = '" + ii.getInqRFItemGroup().trim() + "' ");
				}
				sqlString.append(" GROUP BY A.AGATVA, MMEVGR, B.AGATVA ");
				sqlString.append(" ORDER BY A.AGATVA, MMEVGR, B.AGATVA ");
			}
			//********************************************************************
			// List Drop Down's
			if (inRequestType.equals("ddFacility"))
			{
				InqInventory ii = (InqInventory) inParms.elementAt(1);
				sqlString.append("SELECT CFFACI, CFFACN ");
				sqlString.append("FROM " + library + "MITLOC ");
				sqlString.append("INNER JOIN " + library + "CFACIL ");
				sqlString.append("   ON MLCONO=CFCONO AND MLFACI = CFFACI ");
				if (ii.getRequestType().trim().equals("inqLotReclass"))
				{
					// then only show Inventory with Lot Numbers 
					//	- MITMAS FILE  --MMINDI Field = 3
					sqlString.append("INNER JOIN " + library + "MITMAS ");
					sqlString.append("   ON  MLCONO=MMCONO AND MLITNO = MMITNO ");
					sqlString.append("   AND MMINDI = 3 ");
				}
				sqlString.append(" WHERE MLCONO=100 ");
				// TWalton 11/20/12 -- Added filter for Facility Specific 
				if (!ii.getFacilityFilter().trim().equals(""))
				{
					sqlString.append("AND MLFACI = '" + ii.getFacilityFilter().trim() + "' ");
				}
				sqlString.append(" GROUP BY CFFACI, CFFACN ");
				sqlString.append(" ORDER BY CFFACI, CFFACN ");
			}
			if (inRequestType.equals("ddWarehouse"))
			{
				InqInventory ii = (InqInventory) inParms.elementAt(1);
				sqlString.append("SELECT MWWHLO, MWWHNM ");
				sqlString.append("FROM " + library + "MITLOC ");
				sqlString.append("INNER JOIN " + library + "MITWHL ");
				sqlString.append("   ON MLCONO=MWCONO AND MLWHLO = MWWHLO ");
				if (ii.getRequestType().trim().equals("inqLotReclass"))
				{
					// then only show Inventory with Lot Numbers 
					//	- MITMAS FILE  --MMINDI Field = 3
					sqlString.append("INNER JOIN " + library + "MITMAS ");
					sqlString.append("   ON  MLCONO=MMCONO AND MLITNO = MMITNO ");
					sqlString.append("   AND MMINDI = 3 ");
				}
				sqlString.append(" WHERE MLCONO=100 ");
				// TWalton 11/20/12 -- Added filter for Facility Specific 
				if (!ii.getFacilityFilter().trim().equals(""))
				{
					sqlString.append("AND MLFACI = '" + ii.getFacilityFilter().trim() + "' ");
				}
				sqlString.append(" GROUP BY MWWHLO, MWWHNM ");
				sqlString.append(" ORDER BY MWWHLO, MWWHNM ");
			}
			if (inRequestType.equals("ddItemType"))
			{
				InqInventory ii = (InqInventory) inParms.elementAt(1);
				sqlString.append("SELECT CTSTKY, CTTX40 ");
				sqlString.append("FROM " + library + "MITLOC ");
				sqlString.append("INNER JOIN " + library + "MITMAS ");
				sqlString.append("   ON MLCONO=MMCONO AND MLITNO = MMITNO ");
				if (ii.getRequestType().trim().equals("inqLotReclass"))
				{
					// then only show Inventory with Lot Numbers 
					//	- MITMAS FILE  --MMINDI Field = 3
					sqlString.append("   AND MMINDI = 3 ");
				}
				
				sqlString.append("INNER JOIN " + library + "CSYTAB ");
				sqlString.append("   ON  MLCONO=CTCONO AND CTSTCO = 'ITTY' ");
				sqlString.append("   AND MMITTY = CTSTKY ");
				
				sqlString.append(" WHERE MLCONO=100 ");
				// TWalton 11/20/12 -- Added filter for Facility Specific 
				if (!ii.getFacilityFilter().trim().equals(""))
				{
					sqlString.append("AND MLFACI = '" + ii.getFacilityFilter().trim() + "' ");
				}
				sqlString.append(" GROUP BY CTSTKY, CTTX40 ");
				sqlString.append(" ORDER BY CTSTKY, CTTX40 ");
			}
			if (inRequestType.equals("ddTransactionReason") ||
					inRequestType.equals("ddTaggingType") || // Tagging Type is the Disposition 4/28/10
					inRequestType.equals("ddTaggingTypeLimit"))
			{
				//				 Always show the Type and the Description
				sqlString.append(" SELECT CTSTKY, CTTX40 ");
				sqlString.append(" FROM " + library + "CSYTAB ");
				if (inRequestType.equals("ddTaggingTypeLimit"))
				{
					sqlString.append(" INNER JOIN " + library + "MILOMA ON CTCONO = LMCONO AND CTSTKY = LMIDET ");
					sqlString.append(" INNER JOIN " + library + "MITLOC ON CTCONO = MLCONO AND LMITNO = MLITNO AND LMBANO = MLBANO ");	
				}

				sqlString.append(" WHERE CTCONO = 100 AND CTDIVI = '   ' ");
				if (inRequestType.equals("ddTransactionReason"))
					sqlString.append("   AND CTSTCO = 'RSCD' ");
				else
					sqlString.append("   AND CTSTCO = 'IDET' ");
				sqlString.append(" GROUP BY CTSTKY, CTTX40 ");
				sqlString.append(" ORDER BY CTSTKY ");
			}	
			if (inRequestType.equals("ddDisposition")) // Using the Owner Code -- Marked as the Hold Reason 4/28/10
			{
				//				 Always show the Code and the Description
				sqlString.append(" SELECT UWOWNC, UWTX40 ");
				sqlString.append(" FROM " + library + "COWNID ");
				sqlString.append(" WHERE UWCONO = 100 ");
				sqlString.append(" ORDER BY UWOWNC ");
			}	
			if (inRequestType.equals("ddOwner")) // Using the Owner Code -- Marked as the Hold Reason 5/13/10
			{        // Will only return values that currently are in inventory
				//				 Always show the Code and the Description
				sqlString.append(" SELECT UWOWNC, UWTX40 ");
				sqlString.append(" FROM " + library + "COWNID ");
				sqlString.append(" INNER JOIN " + library + "MILOMA ON UWCONO = LMCONO AND UWOWNC = LMOWNC ");
				sqlString.append(" INNER JOIN " + library + "MITLOC ON UWCONO = MLCONO AND LMITNO = MLITNO AND LMBANO = MLBANO ");
				sqlString.append(" WHERE UWCONO = 100 ");
				sqlString.append(" GROUP BY UWOWNC, UWTX40 ");
				sqlString.append(" ORDER BY UWOWNC ");
			}
			
		    if (inRequestType.equals("ddOrganic")) // Specific to Raw fruit Inventory
			{// Organic is found based on Item Number 
				sqlString.append("SELECT MMEVGR as DDVALUE, CTTX40 as DDDESC ");
				sqlString.append("FROM " + library + "MITLOC ");
				sqlString.append("INNER JOIN " + library + "MITMAS ");
				// 9/19/12 -TW- Added Zero Cost Item Type 205
				sqlString.append(" ON MLCONO = MMCONO and MLITNO = MMITNO and (MMITTY = '200' OR MMITTY = '205') ");
				sqlString.append("INNER JOIN " + library + "CSYTAB ");
				sqlString.append(" ON MLCONO = CTCONO and CTDIVI = ' ' and CTSTCO = 'EVGR' ");
				sqlString.append("  AND MMEVGR = CTSTKY ");
				sqlString.append("WHERE MMEVGR <> ' ' ");
				sqlString.append("GROUP BY MMEVGR, CTTX40 ");
				sqlString.append("ORDER BY MMEVGR, CTTX40 ");
			}
			if (inRequestType.equals("ddRunType") || // Using Attributes specific to Raw Fruit Inventory
				inRequestType.equals("ddCOO") ||
				inRequestType.equals("ddTGRADE") ||
				inRequestType.equals("ddVariety"))
			{
				sqlString.append("SELECT AGATVA as DDVALUE, IFNULL(PFTX30,' ') as DDDESC ");
				sqlString.append("FROM " + library + "MITLOC ");
				sqlString.append("INNER JOIN " + library + "MITMAS ");
				// 9/19/12 -TW- Added Zero Cost Item Type 205
				sqlString.append(" ON MLCONO = MMCONO and MLITNO = MMITNO and (MMITTY = '200' OR MMITTY = '205') ");
				sqlString.append("INNER JOIN " + library + "MILOMA ");
				sqlString.append(" ON MLCONO = LMCONO and MLITNO = LMITNO and MLBANO = LMBANO ");
				sqlString.append("INNER JOIN " + library + "MIATTR ");
				sqlString.append(" ON MLCONO = AGCONO and LMATNR = AGATNR and LMITNO = AGITNO ");
				sqlString.append(" AND MLBANO = AGBANO and AGATID = ");
				if (inRequestType.equals("ddRunType"))
					sqlString.append("'RUN' ");
				if (inRequestType.equals("ddCOO"))
					sqlString.append("'COO' ");
				if (inRequestType.equals("ddTGRADE"))
					sqlString.append("'TGRAD' ");
				if (inRequestType.equals("ddVariety"))
					sqlString.append("'VAR' ");
				sqlString.append("LEFT OUTER JOIN " + library + "MPDOPT ");
				sqlString.append(" ON MLCONO = PFCONO and AGATVA = PFOPTN ");
				sqlString.append("WHERE AGATVA <> ' ' ");
				sqlString.append("GROUP BY AGATVA, PFTX30 ");
				sqlString.append("ORDER BY AGATVA, PFTX30 ");
			}
			//9/19/12 - TW - No Longer Needed
			//if (inRequestType.equals("snapshotDates"))
		//	{
		//		InqInventory ii = (InqInventory) inParms.elementAt(0);
		//		sqlString.append("SELECT SNACUSTPK, SNASSDATE, SNASSTIME ");
		//		sqlString.append(" FROM " + ttlib + "CPPAINV ");
		//		sqlString.append(" WHERE SNACUSTPK = '" + ii.getInqCustomPackCode() + "' ");
		//		sqlString.append(" GROUP BY SNACUSTPK, SNASSDATE, SNASSTIME ");
		//		sqlString.append(" ORDER BY SNACUSTPK, SNASSDATE DESC, SNASSTIME DESC ");
		//	}
			
			//****************************************************************************
			//** INSERT
			//9/19/12 - TW - No Longer Needed
			//if (inRequestType.equals("insertInventorySnapshot"))
			//{
				// Send in Co-Pack Code, in the Accounting Control of the Item master
				//    Also Date and Time to put as the Snapshot Date and Time
			//	String copackCode = (String) inParms.elementAt(0);
			//	String snapshotDate = (String) inParms.elementAt(1);
			//	String snapshotTime = (String) inParms.elementAt(2);

				// build the sql statement.
		//		sqlString.append("INSERT INTO " + ttlib + "CPPAINV ");
		//		sqlString.append(" (SELECT '" + copackCode + "', " );
		//		sqlString.append(snapshotDate + ", ");
		//		sqlString.append(snapshotTime + ", ");
		//		sqlString.append("MLCONO, MLFACI, MLWHLO, MLSLTP, MLWHSL, ");
		//		sqlString.append("MLITNO, MLBANO, MLBREF, MLBRE2, MLSTAS, ");
		//		sqlString.append("MLSTQT, MLALOC, MLPLQT, MLPRDT, MLIDDT, ");
		//		sqlString.append("MLODDT, MLINDT, MLRCLS, MLSEDT, MLRGDT, ");
		//		sqlString.append("MLRGTM, MLLMDT, MLCHNO, MLCHID ");
		//		sqlString.append(" FROM " + library + "MITLOC ");
		//		sqlString.append("  INNER JOIN " + library + "MITMAS ");
		//		sqlString.append("     ON MLITNO = MMITNO ");
		//		sqlString.append("   WHERE MMACRF = '" + copackCode+ "' ");
		//		sqlString.append(" ) ");
		//	}
			//****************************************************************************
			//** UPDATE

		} catch (Exception e) {
			throwError.append(" Error building sql statement"
					+ " for request type " + inRequestType + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceInventory.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		return sqlSelect.toString().trim() + " " +  sqlString.toString().trim();
	}

	/**
	 * Send in Environment, Item Number, 
	 *    Return an Item Class 
	 */

	public static BeanInventory buildFIFOList(String environment, 
			InqInventory inqInv)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanInventory rtnValue = new BeanInventory();
		Connection conn = null; // New Lawson Box - Lawson Database
		Vector buildLines = new Vector();
		try { 
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			if (inqInv.getInqCustomerOrder() == null || inqInv.getInqCustomerOrder().trim().equals(""))
				throwError.append(" Customer Order Number must not be null or empty.");
			conn = ServiceConnection.getConnectionStack5();

			// get Item Info.
			if (throwError.toString().equals(""))
			{
				try {
					rtnValue.setListSOLineItems(ServiceSalesOrder.buildSalesOrderBasic(environment, inqInv.getInqCustomerOrder().trim()));
					if (rtnValue.getListSOLineItems() != null && 
							rtnValue.getListSOLineItems().size() > 0)
					{
						for (int x = 0; x < rtnValue.getListSOLineItems().size(); x++)
						{
							Vector sendParms = new Vector();
							SalesOrderLineItem soli = (SalesOrderLineItem) rtnValue.getListSOLineItems().elementAt(x);
							sendParms.addElement(soli.getItemClass().getItemNumber());
							sendParms.addElement(soli.getShippingWarehouse());
							sendParms.addElement(soli.getPlannedShipDate());
							buildLines.addElement(findFIFOList(environment, sendParms, conn));
						}
					}
				} catch (Exception e) {
					throwError.append(" error trying to retrieve Data. " + e);
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Retrieving Information. " + e);
			// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						ServiceConnection.returnConnectionStack5(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		rtnValue.setByItemVectorOfInventory(buildLines);
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceInventory.");
			throwError.append("buildFIFOList(String environment: ");
			throwError.append("InqInventory)");
			throw new Exception(throwError.toString());
		}
		return rtnValue;
	}

	/**
	 * Send in Environment, 
	 * 			Vector:  Item Number, Warehouse 
	 *    Return an Vector of Inventory Class 
	 */
	private static Vector findFIFOList(String environment, 
			Vector inValues,
			Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnValue = new Vector();
		PreparedStatement findIt = null;
		ResultSet rs = null;
		String sqlString = new String();
		try { 
			try {
				sqlString = buildSqlStatement(environment, "findFIFO", inValues);
			} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
			}
			// get a execute sql, build return object.
			if (throwError.toString().equals("")) {
				try {
					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();
				} catch (Exception e) {
					throwError.append(" error trying to execute sqlString. ");
				}
			}
			if (throwError.toString().equals("")) {
				try {		
					int setCount = 0;
					String location = "";
					String ref1 = "";
					while (rs.next())
					{
						if (setCount < 6) // Only return 6 of Each item/Whse
						{
							// need to Load the information into the Item Class
							try
							{
								if (!location.equals(rs.getString("MLWHSL").trim()) ||
										!ref1.equals(rs.getString("MLBREF").trim()))
								{	
									rtnValue.addElement(loadFields("loadFIFO", rs));
									setCount++;	
								}
							} catch (Exception e) {
								throwError.append(" error occured loading Fields from an sql statement. " + e);
							}
						}
					}
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Retrieving Data for Item Class. " + e) ;
			// return connection.
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (findIt != null)
					findIt.close();
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceInventory.");
			throwError.append("findFIFOList(String environment: ");
			throwError.append("Vector, Connection conn)");
			throw new Exception(throwError.toString());
		}
		return rtnValue;
	}

	/**
	 *   // Run an SQL to get the lots, and then one by one
	 *   //     change the status of the lots from 2-Approved		
	 *   //     to 1-Incubation, also change the allocatable
	 *   //     for each one, write out to an API Log File 
	 * @param -- CommonRequestBean
	 * @return BeanInventory -- Only return a message *count Status's have been changed.
	 * @throws Exception
	 */
	public static BeanInventory updateInspectionStatus(CommonRequestBean inViewBean)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanInventory returnValue = new BeanInventory();
		Connection conn = null;
		
		try {
			
			conn = ServiceConnection.getConnectionStack5();
			returnValue = updateInspectionStatus(inViewBean, conn);
			
		} catch (Exception e)
		{
			throwError.append("Update inspection status setup process failed. " + e);		
		}
		
		finally {
			
			try {
			
				if (conn != null)						
			        ServiceConnection.returnConnectionStack5(conn);	
				
			} catch(Exception e)
			{
				throwError.append("Connection failed return. " + e);
			}
		}	
		
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("updateInspectionStatus(");
			throwError.append("CommonRequestBean). ");
			
			throw new Exception(throwError.toString());
		}
		
		return returnValue;
	}

	/**
	 *   Get a list of AttributeValue Classes
	 * @param -- currently not using. 
	 * @return BeanInventory
	 * @throws Exception
	 */
	private static BeanInventory listAverageAcid(Vector inValues, 
			Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnList = new Vector();
		BeanInventory returnValue = new BeanInventory();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try
		{
			try {
				// Get the list of DataSet File Names - Along with Descriptions
				listThem = conn.prepareStatement(buildSqlStatement("PRD", "acidList", inValues));
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
							rtnList.addElement(ServiceAttribute.loadFieldsAttributeValue("acidList", rs));
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
		returnValue.setByItemVectorOfInventory(rtnList);
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("listAverageAcid(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   // Use to control the information retrieval
	 * @param -- currently not using. 
	 * @return Vector of Business Object DataSet.
	 * @throws Exception
	 */
	public static BeanInventory listAverageAcid(Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanInventory returnValue = new BeanInventory();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack5();
			returnValue = listAverageAcid(inValues, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
					ServiceConnection.returnConnectionStack5(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("listAverageAcid(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *  Get a list of Lots to Reclassify
	 * @param  UpdInventory, Connection
	 * @return BeanInventory -- Just filling in a message
	 * @throws Exception
	 */
	private static BeanInventory updateIncubationStatus(UpdInventory inViewBean, 
			Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		//Vector rtnList = new Vector();
		BeanInventory returnValue = new BeanInventory();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try
		{
			try {
				// Get the list of DataSet File Names - Along with Descriptions
				Vector inValues = new Vector();
				inValues.addElement(inViewBean);
				listThem = conn.prepareStatement(buildSqlStatement("PRD", "findLotsChangeStatus1", inValues));
				rs = listThem.executeQuery();
			} catch(Exception e)
			{
				throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
			}
			if (throwError.toString().equals(""))
			{
				try
				{ 
					String saveItem = "";
					String saveLot  = "";
					String saveWhse = "";
					int countLots = 0;
					int countRecords = 0;
					StringBuffer listLots = new StringBuffer();
					while (rs.next())
					{
						// Build a Vector of Classes to Return
						try { 
							//  	  System.out.println(rs.getString("LMITNO"));
							// 	  System.out.println(rs.getString("LMBANO"));
							//  	  System.out.println(rs.getString("MLWHLO"));
							if (!saveItem.trim().equals(rs.getString("LMITNO").trim()) ||
									!saveLot.trim().equals(rs.getString("LMBANO").trim()) ||
									!saveWhse.trim().equals(rs.getString("MLWHLO").trim()))
							{
								// Run the API to Update the Status
								MMS850MIAddRclLotSts marls = new MMS850MIAddRclLotSts();
								marls.setSentFromProgram("Update Incubation Status");
								marls.setEnvironment(ttlib.substring(2,5));
								//	marls.setUserProfile(inViewBean.getUpdateUser());
								marls.setUserProfile("M3SRVADM");
								marls.setMessageType("INCUB"); // differentiate between other reclassifications
								marls.setWarehouse(rs.getString("MLWHLO").trim());
								marls.setItemNumber(rs.getString("LMITNO").trim());
								marls.setLotNumber(rs.getString("LMBANO"));
								marls.setAllocatable("1");
								marls.setStatusBalanceID("2");
								countLots++;
								if (!listLots.toString().trim().equals(""))
									listLots.append(", ");
								listLots.append(rs.getString("LMBANO").trim());
								
								
								MMS850MI.addRclLotSts(marls, inViewBean.getUserAuthorization());
							}
							saveItem = rs.getString("LMITNO");
							saveLot = rs.getString("LMBANO");
							saveWhse = rs.getString("MLWHLO");
							countRecords++;
						} catch(Exception e)
						{
							throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
						}
					}// END of the While Statement
					//System.out.println("Count Lots: " + countLots);
					//System.out.println("Count Records: " + countRecords);
					returnValue.setReturnMessage("Number of Lots Reclassified: " + countLots + " -- Lot Numbers Include: " + listLots.toString());
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
		//	returnValue.setByItemVectorOfInventory(rtnList);

		//Set a message into the BEAN saying that X number of Lots have been updated


		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("updateIncubationStatus(");
			throwError.append("UpdInventory, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   // Run an SQL to Load the information from MITLOC into CPPAINV
	 * @param -- UpdInventory
	 * @return Void -- will throw back information if problems occur
	 * @throws Exception
	 * @deprecated -20120919 - TW - No longer used,,, last used 12/3/2008
	 */
	public static void inventorySnapshot(UpdInventory inViewBean)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack5();
			inventorySnapshot(inViewBean, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
					ServiceConnection.returnConnectionStack5(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("inventorySnapshot(");
			throwError.append("UpdInventory). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return;
	}

	/**
	 *  Insert Records into CPPAINV, based on 
	 *        the sent in CoPack Code -- insert with Current Date and Time
	 * @param  UpdInventory, Connection
	 * @return void -- only Throw back problems
	 * @throws Exception
	 * @deprecated -20120919 - TW - No longer used,,, last used 12/3/2008
	 */
	private static void inventorySnapshot(UpdInventory inViewBean, 
			Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		//Vector rtnList = new Vector();
		PreparedStatement insertList = null;
		try {
			// Get the list of DataSet File Names - Along with Descriptions
			Vector inValues = new Vector();
			inValues.addElement(inViewBean.getCustomPackCode());
			//Get Today's Current Date and Time
			DateTime dt = UtilityDateTime.getSystemDate();
			inValues.addElement(dt.getDateFormatyyyyMMdd());
			inValues.addElement(dt.getTimeFormathhmmss());

			insertList = conn.prepareStatement(buildSqlStatement("PRD", "insertInventorySnapshot", inValues));
			insertList.executeUpdate();
		} catch(Exception e)
		{
			throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		}
		finally {
			if (insertList != null)
			{
				try
				{
					insertList.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("inventorySnapshot(");
			throwError.append("UpdInventory, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return;
	}

	/**
	 *   // Return a Vector of all the Dates of all the Snapshots associated to the Custom Pack Code
	 * @param -- InqInventory 
	 * @return Vector of Vectors (3 elements on the inside: CustomPack Code, Date, Time).
	 * @throws Exception
	 */
	public static Vector inventorySnapshotDates(InqInventory inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector returnValue = new Vector();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack5();
			returnValue = inventorySnapshotDates(inValues, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
					ServiceConnection.returnConnectionStack5(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("inventorySnapshotDates(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   // Return a Vector of all the Dates of all the Snapshots associated to the Custom Pack Code
	 * @param -- InqInventory 
	 * @return Vector of Vectors (3 elements on the inside: String CustomPackCode, DateTime Class).
	 * @throws Exception
	 */
	private static Vector inventorySnapshotDates(InqInventory inValues, 
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
				Vector sendValues = new Vector();
				sendValues.addElement(inValues);
				listThem = conn.prepareStatement(buildSqlStatement("PRD", "snapshotDates", sendValues));
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
						// Build a Vector of Vectors to Return
						try {
							Vector buildReturn = new Vector();
							buildReturn.addElement(rs.getString("SNACUSTPK"));
							// 	 System.out.println(rs.getString("SNASSDATE"));
							DateTime date = UtilityDateTime.getDateFromyyyyMMdd(rs.getString("SNASSDATE"));
							// 	 System.out.println(rs.getString("SNASSTIME"));
							DateTime time = UtilityDateTime.getTimeFromhhmmss(rs.getString("SNASSTIME"));
							date.setTimeFormatAMPM(time.getTimeFormatAMPM());
							date.setTimeFormathhmmss(rs.getString("SNASSTIME"));
							date.setTimeFormathhmmssColon(time.getTimeFormathhmmssColon());
							buildReturn.addElement(date);
							returnValue.addElement(buildReturn);
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
			throwError.append("ServiceInventory.");
			throwError.append("inventorySnapshotDates(");
			throwError.append("InqInventory, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *  Use to control the information retrieval
	 * 		Codes to send in as the 1st Element of the Vector
	 *         ddFacility == Build a Facility Drop Down List
	 * 		   ddWarehouse == Build a Warehouse Drop Down List
	 * 		   ddItemType == Build an Item Type Drop Down List
	 * 
	 * @param -- Vector to control what information to NARROW Down
	 * @return Vector of Objects Inventory with only Facility and Description filled in
	 * @throws Exception
	 */
	public static Vector listDropDown(Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector returnValue = new Vector();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack5();
			returnValue = listDropDown(inValues, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
					ServiceConnection.returnConnectionStack5(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("listDropDown(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   Get a list of AttributeValue Classes
	 * @param -- Vector to control what information to NARROW Down
	 * 			 Connection
	 * @return Vector of Objects Inventory with only Facility and Description filled in
	 * @throws Exception
	 */
	private static Vector listDropDown(Vector inValues, 
			Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector returnList = new Vector();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		String listType = "ddFacility";
		try
		{
			listType = (String) inValues.elementAt(0);
			if (listType == null ||
					listType.trim().equals(""))
				listType = "ddFacility";
			try {
				// Get the list of Facilities Names - Along with Descriptions
				listThem = conn.prepareStatement(buildSqlStatement("PRD", listType, inValues));
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
							if (listType.equals("ddTransactionReason") ||
									listType.equals("ddTaggingType") ||
									listType.equals("ddTaggingTypeLimit") ||
									listType.equals("ddDisposition") ||
									listType.equals("ddOwner") ||
									listType.equals("ddRunType") || // Raw Fruit Attribute Related
									listType.equals("ddOrganic") || // from Item Master
									listType.equals("ddCOO") || // Raw Fruit Attribute Related
									listType.equals("ddTGRADE") || // Raw Fruit Attribute Related
									listType.equals("ddVariety")) // Raw Fruit Attribute Related
								returnList.addElement(loadFieldsDropDownSingle(listType, rs));
							else
								returnList.addElement(loadFields(listType, rs));
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
			throwError.append("ServiceInventory.");
			throwError.append("listDropDown(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnList;
	}

	/**
	 * Returns list of lots to update attributes on
	 * @param inValues
	 * @return
	 * @throws Exception
	 */
	public static BeanInventory listLotsToUpdateAttributes(Vector inValues)
	throws Exception {
		StringBuffer throwError = new StringBuffer();
		BeanInventory returnValue = new BeanInventory();
		Connection conn = null;

		try {
			conn = ServiceConnection.getConnectionStack5();

			returnValue = listLotsToUpdateAttributes(inValues, conn);


		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (conn != null) {
				try {
					ServiceConnection.returnConnectionStack5(conn);
				} catch (Exception e) {
					throwError.append(e);
				}
			}
			if (!throwError.toString().trim().equals("")) {
				throwError.insert(0, "Error @ com.treetop.services.ServiceInventory.listLotsToUpdateAttributes():  ");
				throw new Exception(throwError.toString());
			}
		}

		return returnValue;
	}

	private static BeanInventory listLotsToUpdateAttributes(Vector inValues, Connection conn) 
	throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector rtnList = new Vector();
		BeanInventory returnValue = new BeanInventory();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		int recordCount = 0;
		try
		{
			try {
				// Get the list of DataSet File Names - Along with Descriptions
				InqInventory ii = (InqInventory) inValues.elementAt(0);
				listThem = conn.prepareStatement(buildSqlStatement(ii.getEnvironment(), "listLotAttribute", inValues));
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
							recordCount++;
							if (recordCount < 50)
								rtnList.addElement(loadFields("listLotAttribute", rs));
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
		returnValue.setByItemVectorOfInventory(rtnList);
		if (recordCount >= 50)
			returnValue.setReturnMessage("More than 50 Records were found.  The first 50 are being displayed.");
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("listAverageAcid(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   // Use to control the information retrieval
	 * @param -- Send in the InqInventory Object for use in the SQL statement
	 * @return Vector of Business Object DataSet.
	 * @throws Exception
	 */
	public static BeanInventory listLotsToReclassify(Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanInventory returnValue = new BeanInventory();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack5();
			returnValue = listLotsToReclassify(inValues, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
					ServiceConnection.returnConnectionStack5(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("listLotsToReclassify(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   Get a list of Inventory Classes
	 * @param -- Send in the Selection Criteria from InqInventory
	 * @return BeanInventory
	 * @throws Exception
	 */
	private static BeanInventory listLotsToReclassify(Vector inValues, 
			Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnList = new Vector();
		BeanInventory returnValue = new BeanInventory();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		int recordCount = 0;
		try
		{
			try {
				// Get the list of DataSet File Names - Along with Descriptions
				listThem = conn.prepareStatement(buildSqlStatement("PRD", "listLotReclass", inValues));
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
							recordCount++;
							if (recordCount < 50)
								rtnList.addElement(loadFields("listLotReclass", rs));
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
		returnValue.setByItemVectorOfInventory(rtnList);
		if (recordCount >= 50)
			returnValue.setReturnMessage("More than 50 Records were found.  The first 50 are being displayed.");
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("listAverageAcid(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   //     Change the status of the lots from Whatever the Lot is 
	 *   //     to the requested Status, also change the allocatable
	 *   //     For each one write out to an API Log File 
	 * @param -- Vector (User, fromStatus, toStatus, Inventory Business Object)
	 * @return Inventory -- Return the Inventory Class sent in, with the comment filled in.
	 * @throws Exception
	 */
	public static Inventory updateInventoryStatus(Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Inventory returnValue = new Inventory();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack5();
			returnValue = updateInventoryStatus(inValues, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
					ServiceConnection.returnConnectionStack5(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("updateInventoryStatus(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *  Process Lots to Reclassify
	 * @param  Vector (User, fromStatus, toStatus and Inventory Business Object), Connection
	 * @return Inventory -- Return all values with comment filled in.
	 * @throws Exception
	 */
	private static Inventory updateInventoryStatus(Vector inValues, 
			Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();

		Inventory returnValue = new Inventory();
		try
		{
			// Run the API to Update the Status
			MMS850MIAddRclLotSts marls = new MMS850MIAddRclLotSts();
			marls.setSentFromProgram("Update Inventory Status");
			marls.setEnvironment(ttlib.substring(2,5));
			marls.setUserProfile((String) inValues.elementAt(0));
			//marls.setMessageType("MASS"); // differentiate between other reclassifications
			returnValue = (Inventory) inValues.elementAt(3);
			marls.setWarehouse(returnValue.getWarehouse().trim());
			marls.setItemNumber(returnValue.getItemNumber().trim());
			marls.setLotNumber(returnValue.getLotNumber().trim());
			UpdInventory ui = (UpdInventory) inValues.elementAt(4);
			marls.setTransactionReason(ui.getTransactionReason().trim());
			marls.setLotRef2(ui.getLotRef2().trim());
			
			//Remark is now a hold comment set as an attribute
			//marls.setRemark(ui.getRemark().trim());
			
			if (!ui.getExpirationDate().trim().equals(""))
			{
				DateTime expire = UtilityDateTime.getDateFromMMddyyWithSlash(ui.getExpirationDate());
				marls.setExpirationDate(expire.getDateFormatyyyyMMdd());
			}
			if (!ui.getFollowUpDate().trim().equals(""))
			{
				DateTime follow = UtilityDateTime.getDateFromMMddyyWithSlash(ui.getFollowUpDate());
				marls.setFollowUpDate(follow.getDateFormatyyyyMMdd());
			}
			if (!ui.getSalesDate().trim().equals(""))
			{
				DateTime sales = UtilityDateTime.getDateFromMMddyyWithSlash(ui.getSalesDate());
				marls.setSalesDate(sales.getDateFormatyyyyMMdd());
			}
			// Allocatable -- review information
			// -- Only set to 1 if Status is going to be 2
			String toStatus = (String) inValues.elementAt(2);
			//	if (!toStatus.trim().equals("3"))
			marls.setAllocatable("1");
			//else
			//   marls.setAllocatable(" ");
			marls.setStatusBalanceID(toStatus);
			//  	System.out.println("uncomment, in updateInventoryStatus - Fix Library");
			
			String userAuthorization = (String) inValues.elementAt(5);
			
			returnValue.setComment(MMS850MI.addRclLotSts(marls, userAuthorization));

		} catch (Exception e)
		{
			// Just Catch the ProblemthrowError.append(e);
			returnValue.setComment("Please Try Again!");
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("updateInventoryStatus(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}
	
	/**
	 *   //     Change the status of the lots from Whatever the Lot is 
	 *   //     to the requested Status, also change the allocatable
	 *   //     For each one write out to an API Log File 
	 * @param -- Vector (User, fromStatus, toStatus, Inventory Business Object)
	 * @return Inventory -- Return the Inventory Class sent in, with the comment filled in.
	 * @throws Exception
	 */
	public static Inventory updateInventoryLotComment(Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Inventory returnValue = new Inventory();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack5();
			returnValue = updateInventoryLotComment(inValues, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
					ServiceConnection.returnConnectionStack5(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("updateInventoryLotComment(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *  Process Lots to Reclassify
	 * @param  Vector (User, fromStatus, toStatus and Inventory Business Object), Connection
	 * @return Inventory -- Return all values with comment filled in.
	 * @throws Exception
	 */
	private static Inventory updateInventoryLotComment(Vector inValues, 
			Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		
		String userProfile = (String) inValues.elementAt(0);
		Inventory returnValue = (Inventory) inValues.elementAt(3);
		UpdInventory ui = (UpdInventory) inValues.elementAt(4);
		String userAuthorization = (String) inValues.elementAt(5);
		
		
		
		try
		{
			
			ATS101MIaddItmLotAttrTx a = new ATS101MIaddItmLotAttrTx();
			a.setSentFromProgram("Update Inventory Status");
			if (ui.getEnvironment().trim().equals(""))
				a.setEnvironment("PRD");
			else
			    a.setEnvironment(ui.getEnvironment());
			
			
			a.setUserProfile(userProfile);
			
			a.setCompany("100");
			a.setAttributeID("LOT COMMENTS");
			a.setItemNumber(returnValue.getItemNumber().trim());
			a.setLotNumber(returnValue.getLotNumber().trim());
			
			
			String holdComments = ui.getRemark().trim();
			if (holdComments.length() > 60) {
				holdComments = holdComments.substring(0,60);
			}
			a.setAdditionalTextLine(holdComments);
			
			
			returnValue.setComment(ATS101MI.addItmLotAttrTx(a, userAuthorization));

		} catch (Exception e)
		{
			// Just Catch the ProblemthrowError.append(e);
			returnValue.setComment("Please Try Again!");
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("updateInventoryLotComment(");
			throwError.append("Vector, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   // Use to control the information retrieval
	 * @param -- currently not using. 
	 * @return Vector of BeanInventory - Business Object.
	 * @throws Exception
	 */
	public static BeanInventory listRawFruit(Vector inValues)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanInventory returnValue = new BeanInventory();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack5();
			returnValue = listRawFruit(inValues, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
					ServiceConnection.returnConnectionStack5(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("listRawFruit(");
			throwError.append("Vector). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *   Get a list of Inventory Information for Raw Fruit
	 * @param -- currently not using. 
	 * @return BeanInventory
	 * @throws Exception
	 */
	private static BeanInventory listRawFruit(Vector inValues, 
			Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnList = new Vector();
		BeanInventory returnValue = new BeanInventory();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		String listType = "listRF";
		try
		{
			InqInventory ii = (InqInventory) inValues.elementAt(0);
			if (ii.getInqShowDetails().equals(""))
				listType = "listRFSummary";
			try {
				// Get the list of DataSet File Names - Along with Descriptions
				listThem = conn.prepareStatement(buildSqlStatement("PRD", listType, inValues));
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
							rtnList.addElement(loadFields(listType, rs));
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
		returnValue.setListOfInventory(rtnList);
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("listRawFruit(");
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
			if (requestType.equals("ddTransactionReason") ||
					requestType.equals("ddTaggingType") ||
					requestType.equals("ddTaggingTypeLimit"))
			{
				returnValue.setValue(rs.getString("CTSTKY").trim());
				returnValue.setDescription(rs.getString("CTTX40").trim());
			}
			if (requestType.equals("ddDisposition") ||
					requestType.equals("ddOwner"))
			{
				returnValue.setValue(rs.getString("UWOWNC").trim());
				returnValue.setDescription(rs.getString("UWTX40").trim());
			}
			if (requestType.equals("ddRunType") || 
			    requestType.equals("ddOrganic") || 
			    requestType.equals("ddCOO") || 
			    requestType.equals("ddTGRADE") || 
			    requestType.equals("ddVariety"))  
			{
				returnValue.setValue(rs.getString("DDVALUE").trim());
				returnValue.setDescription(rs.getString("DDDESC").trim());
			}
		} catch(Exception e)
		{
			throwError.append(" Error loading dropdown fields ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceInventory.");
			throwError.append("loadFieldsDropDownSingle(requestType(");
			throwError.append(requestType + "), rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

	/**
	 *   // Run an SQL to get the lots, and then one by one
	 *   //     Change the status of the lots from 1-Incubation
	 *   //     to 2 - released, also change the allocatable
	 *   //     For each one write out to an API Log File 
	 * @param -- UpdInventory
	 * @return BeanInventory -- Only return a message *count Status's have been changed.
	 * @throws Exception
	 */
	public static BeanInventory updateIncubationStatus(UpdInventory inViewBean)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanInventory returnValue = new BeanInventory();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack5();
			returnValue = updateIncubationStatus(inViewBean, conn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		finally {
			if (conn != null)
			{
				try
				{
					ServiceConnection.returnConnectionStack5(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceInventory.");
			throwError.append("updateIncubationStatus(");
			throwError.append("UpdInventory). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return returnValue;
	}

	/**
	 *  Get a list of Lots to Reclassify
	 * @param  CommonRequestBean, Connection
	 * @return BeanInventory
	 * @throws Exception
	 */
private static BeanInventory updateInspectionStatus(CommonRequestBean inViewBean, 
		                                            Connection conn)
throws Exception
{
	StringBuffer  throwError   = new StringBuffer();		
	BeanInventory returnValue  = new BeanInventory();
	String        sql          = new String();
	ResultSet     rs           = null;
	Statement     listThem     = null;
	String		  requestType  = new String();
	String		  environment  = new String();
	
	try
	{
		Vector<Inventory> lotList = new Vector<Inventory> ();
		
		try {
			
			Vector<CommonRequestBean> transactionBean = new Vector<CommonRequestBean>();
			transactionBean.addElement(inViewBean);
			requestType = "findLotsChangeStatus2";
			environment = inViewBean.getEnvironment();
			sql = buildSqlStatement(environment, requestType, transactionBean);
			listThem = conn.createStatement();
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
					 Inventory oneLot = new Inventory();
					 oneLot = loadFields("listLotReclass", rs);									 
										 
					 lotList.addElement(oneLot);
					 returnValue.setListOfLots(lotList);
				 }				
					 
	     	 } catch(Exception e)
			 {
	     		throwError.append("Error occured while processing a result set. " + e);
			 }
		 }		 		

	} catch (Exception e)
	{
		throwError.append("Update inspection status processing failed. " + e);		
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
		throwError.append("ServiceInventory.");
		throwError.append("updateInspectionStatus(");
		throwError.append("CommonRequestBean, conn). ");
		
		throw new Exception(throwError.toString());
	}
		
	return returnValue;
}
}

