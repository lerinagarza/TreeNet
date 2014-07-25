/*
 * Created on April 4, 2012
 */
package com.treetop.services;

import java.sql.*;
import java.util.*;
import java.math.*;

import com.lawson.api.*;
import com.treetop.app.inventory.UpdAttribute;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.controller.contractmanufacturing.*;
import com.treetop.utilities.*;
import com.treetop.utilities.html.*;


/**
 * @author twalto.
 *
 * Services class to obtain and return data 
 * to business objects for Contract Manufacturing
 * 
 */
public class ServiceContractManufacturing extends BaseService {	

/**
 * @author twalto
 * Main testing. 
 */
	
@SuppressWarnings("unused")
public static void main(String[] args) 
{
	int	 activeTest = 1;
	
	try {
		
		if (activeTest == 0) 
		{
			InqBilling ib = new InqBilling();
			ib.setEnvironment("TST");
			ib.setCompany("100");
			ib.setBillingType("BOP");
			Vector<DropDownSingle> bean = new Vector<DropDownSingle>();
			bean= dropDownMOs(ib);
			
			String breakPoint = "stopHere";
		}
		
		if (activeTest == 0) 
		{
			InqBilling ib = new InqBilling();
			ib.setEnvironment("TST");
			ib.setCompany("100");
			ib.setBillingType("BOP");
			ib.setManufacturingOrderNumber("8014221");
			BeanContractManufacturing bean = new BeanContractManufacturing();
			bean= listLotsFromMO(ib);
			
			String breakPoint = "stopHere";
		}
		
		if (activeTest == 0) 
		{
			UpdBilling ub = new UpdBilling();
			ub.setEnvironment("TST");
			ub.setCompany("100");
			ub.setBillingType("BOP");			
			UpdBilling bean = new UpdBilling();
			bean= listCustomLotsWOAttribute(ub);
			
			String breakPoint = "stopHere";
		}
		
		if (activeTest == 0) 
		{
			UpdBilling ub = new UpdBilling();
			ub.setEnvironment("TST");
			ub.setCompany("100");
			ub.setBillingType("BOP");			
			UpdBilling bean = new UpdBilling();
			bean= listModelsWOAttribute(ub);
			
			if (activeTest == 0) 
			{				
				bean= insertModelAttribute(ub);				
				String breakPoint = "stopHere";
			}		
			

			String breakPoint = "stopHere";
		}		

		if (activeTest == 1)							// Main process
		{
			InqBilling ib = new InqBilling();
			ib.setEnvironment("PRD");
			ib = getEnvironment(ib);
			if (!ib.getEnvironment().trim().equals(""))
			{
				ib.setCompany("100");
				ib.setBillingType("BOP");
				Vector<BeanContractManufacturing> bean = new Vector<BeanContractManufacturing>();
				bean = processBillingForCustomMO(ib);
				String breakPoint = "stopHere";
			}			
		}
				
	} catch (Exception e) {
		System.out.println(e);	
	}
}

/**
 * @author twalto 4/6/12
 * Build an SQL statement for Contract Manufacturing information.
 */
private static class BuildSQL {

	/**
	 * @author twalto  4/6/12
	 * Build an SQL statement to retrieve Lot information
	 * Send in InqBilling -
	 *      Environment 
	 * 		Manufacturing Order Number
	 */
	private static String sqlListLotsFromMO(InqBilling inqBilling)
	throws Exception {
		StringBuffer sqlString       = new StringBuffer();	
		StringBuffer throwError      = new StringBuffer();
		try {
			
			// CURRENTLY HARD CODED 
	//		System.out.println("ServiceContractManufacturing.buildSQLStatmement:listLotsFromMO - to Review data by MO and Adjust off inventory");
				
			// Determine Library
			String library = GeneralUtility.getLibrary(inqBilling.getEnvironment()) + ".";
				
			sqlString.append("SELECT ");
			// MWOHED File - Manufacturing Order Header
			sqlString.append("VHCONO, VHFACI, VHPRNO, VHMFNO, VHWHST, VHRSDT, ");
			sqlString.append("VHOROQ, VHMAQT, ");
			// MITMAS File - Item Master
			sqlString.append("MMITDS, MMUNMS, ");
			// MITTRA File - Transactions
			sqlString.append("MTBANO, MTWHLO, sum(MTTRQT) as transactionQty, ");
			// MILOMA File -- Lot Master  
			sqlString.append("IFNULL(LMATNR, ' ') as LOTATNR, IFNULL(LMMFDT, '0') as LOTMFDT, ");
			sqlString.append("IFNULL(LMMFIT, ' ') as LOTOS1, IFNULL(LMMFSN, ' ') as LOTOS2, ");
			// MIATTR File -- Attributes  
			sqlString.append("IFNULL(AGATVA, ' ') as ATVALUE, ");
			// MITLOC File -- Balance ID
			sqlString.append("IFNULL(MLCONO, ' ') as BIDCONO, IFNULL(MLFACI, ' ') as BIDFACI, IFNULL(MLITNO, ' ') as BIDITNO, ");
			sqlString.append("IFNULL(MLBANO, ' ') as BIDBANO, IFNULL(MLWHLO, ' ') as BIDWHLO, IFNULL(MLWHSL, ' ') as BIDWHSL, ");
			sqlString.append("IFNULL(MLSTQT, '0') as BIDSTQT, IFNULL(MLSTAS, '0') as BIDSTAS, ");
			// MITWHL file -- Warehouse Master
			
			sqlString.append("IFNULL(MWWHNM, ' ') as WHSENAME, ");
			// CFACIL file -- Facility Master			
				sqlString.append("IFNULL(CFFACN, ' ') as FACINAME, ");
	
			// MITPOP file -- Item Alias			
			if (inqBilling.getBillingType().trim().equals("OSA"))
				sqlString.append("IFNULL(MPPOPN, ' ') as OSITEM ");
			if (inqBilling.getBillingType().trim().equals("BOP"))
				sqlString.append("' ' as OSITEM ");
			
			sqlString.append("FROM " + library + "MWOHED ");
			
			sqlString.append("  INNER JOIN " + library + "MITMAS ");
			sqlString.append("     ON VHCONO = MMCONO AND VHPRNO = MMITNO ");
			
			if (inqBilling.getBillingType().trim().equals("OSA"))
				sqlString.append("  AND MMITTY = '105' ");
			if (inqBilling.getBillingType().trim().equals("BOP"))
				sqlString.append("  AND MMITTY = '110' and MMITGR not like 'LT%' ");
			
			sqlString.append("  INNER JOIN " + library + "MITTRA ");
			sqlString.append("     ON VHCONO = MTCONO AND VHWHLO = MTWHLO ");
			sqlString.append("    AND VHPRNO = MMITNO AND VHMFNO = MTPRMF ");
			sqlString.append("    AND MTTTYP = 10 ");
			
			sqlString.append("  LEFT OUTER JOIN " + library + "MITLOC ");
			sqlString.append("     ON VHCONO = MLCONO AND VHPRNO = MLITNO ");
			sqlString.append("    AND MTBANO = MLBANO ");
			
			sqlString.append("  INNER JOIN " + library + "MILOMA ");
			sqlString.append("     ON VHCONO = LMCONO AND VHPRNO = LMITNO ");
			sqlString.append("    AND MTBANO = LMBANO ");
			
			sqlString.append("  LEFT OUTER JOIN " + library + "MIATTR ");
			sqlString.append("     ON VHCONO = AGCONO AND VHPRNO = AGITNO ");
			sqlString.append("    AND MTBANO = AGBANO AND LMATNR = AGATNR ");
			sqlString.append("    AND AGATID = 'TEMPORARY ORDER' ");
			
			sqlString.append("  LEFT OUTER JOIN " + library + "MITWHL ");
			sqlString.append("     ON MLCONO = MWCONO AND MWWHLO = MLWHLO ");
				
			sqlString.append("  LEFT OUTER JOIN " + library + "CFACIL ");
			sqlString.append("     ON MLCONO = CFCONO AND MLFACI = CFFACI ");
			
			if (inqBilling.getBillingType().trim().equals("OSA"))
			{
				sqlString.append("  LEFT OUTER JOIN " + library + "MITPOP ");
				sqlString.append("     ON MPCONO = VHCONO AND MPITNO = VHPRNO AND MPALWT = '3' ");
				sqlString.append("     AND MPALWQ = 'OS' ");
			}
				
			sqlString.append("WHERE ");
			sqlString.append(" VHWHST > '20' ");
			sqlString.append(" AND VHMFNO = " + inqBilling.getManufacturingOrderNumber().trim() + " ");
			
			sqlString.append("GROUP BY ");
			sqlString.append("VHCONO, VHFACI, VHPRNO, VHMFNO, VHWHST, VHRSDT, ");
			sqlString.append("VHOROQ, VHMAQT, ");
			sqlString.append("MMITDS, MMUNMS, ");
			sqlString.append("MLCONO, MLFACI, MLITNO, ");
			sqlString.append("MTBANO, MTWHLO, ");
			sqlString.append("LMATNR, LMMFDT, LMMFIT, LMMFSN, AGATVA, ");
			sqlString.append("MLBANO, MLWHLO, MLWHSL, MLSTQT, MLSTAS, ");
			sqlString.append("MWWHNM, ");			
			
			if (inqBilling.getBillingType().trim().equals("OSA"))
				sqlString.append("CFFACN, ");
			if (inqBilling.getBillingType().trim().equals("BOP"))
				sqlString.append("CFFACN ");
			
			if (inqBilling.getBillingType().trim().equals("OSA"))
				sqlString.append("MPPOPN ");
			
			sqlString.append("ORDER BY VHCONO, VHRSDT, VHMFNO, VHPRNO, MTBANO, MTWHLO, MLWHSL ");

			//	*********************************************************************************
		} catch (Exception e) {
				throwError.append(" Error building sqlListLotsFromMO statement. " + e);
		}
			
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceContractManufacturing.");
			throwError.append("BuildSQL.sqlListLotsFromMO(InqBilling)");
			throw new Exception(throwError.toString());
		}
		return sqlString.toString();
	}

	/**
	 * @author DEisen  2/25/13
	 * Build an SQL statement to retrieve MO's that need to be billed
	 * Send in InqBilling -
	 *      Environment 
	 * 		Billing Type
	 */
	private static String sqlBillingCustomPackMO(UpdBilling updBilling)
	throws Exception {
		StringBuffer sqlString       = new StringBuffer();	
		StringBuffer throwError      = new StringBuffer();
		try {

			String library = GeneralUtility.getLibrary(updBilling.getEnvironment()) + ".";
			String libraryTT = GeneralUtility.getTTLibrary(updBilling.getEnvironment()) + ".";
				
			sqlString.append("SELECT ");
			// MWOHED File - Manufacturing Order Header
			sqlString.append("VHMFNO, VHITNO, VHWHLO, VHRSDT, VHMAQT, VHMAUN,");
			// OCUSMA	 File - Customer Master
			sqlString.append("OKCUNO, OKFACI, OKORTP, OKRESP, MMDCCD, MMITDS ");
			
			sqlString.append("FROM " + library + "MWOHED ");

			sqlString.append("INNER JOIN " + library + "MITMAS ON VHCONO = MMCONO ");
			sqlString.append("   and VHITNO = MMITNO and MMITTY = '110' and MMITGR not like 'LT%' ");
				
			sqlString.append("INNER JOIN " + library + "OCUSMA ON VHCONO = OKCONO ");
			sqlString.append("   and MMACRF = OKACRF ");
				
			sqlString.append("INNER JOIN " + libraryTT + "GNPADESC ON GNACMP = 1 ");
			sqlString.append("   and GNATYP = '" + updBilling.getBillingType().trim() + "' ");
			sqlString.append("   and MMACRF = GNAKY1 ");
			
			sqlString.append("INNER JOIN " + libraryTT + "INPRCMST ON MMCONO = CMCONO ");
			
			sqlString.append("WHERE MMCONO = " + updBilling.getCompany().trim());
			sqlString.append("   and VHMFNO = '" + updBilling.getManufacturingOrderNumber().trim() + "' ");
			sqlString.append("   and VHRSDT >= CMSTRT ");
			sqlString.append("ORDER BY VHCONO, VHMFNO ");
			
			//	*********************************************************************************
		} catch (Exception e) {
				throwError.append(" Error building sqlBillingCustomPackMO statement. " + e);
		}
			
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceContractManufacturing.");
			throwError.append("BuildSQL.sqlBillingCustomPackMO(UpdBilling)");
			throw new Exception(throwError.toString());
		}
		return sqlString.toString();
	}

	/**
	 * @author twalto  5/1/12
	 * Build an SQL statement to retrieve MO's that need to be billed
	 * Send in InqBilling -
	 *      Environment 
	 * 		Billing Type
	 */
	private static String sqlDropDownMOs(InqBilling inqBilling)
	throws Exception {
		StringBuffer sqlString       = new StringBuffer();	
		StringBuffer throwError      = new StringBuffer();
		try {
			
			// CURRENTLY HARD CODED 
		//	System.out.println("ServiceContractManufacturing.buildSQLStatmement:DropDownMOs - to Retrive list of MO's which need to be billed");
				
			// Determine Library
			String library = GeneralUtility.getLibrary(inqBilling.getEnvironment()) + ".";
			String libraryTT = GeneralUtility.getTTLibrary(inqBilling.getEnvironment()) + ".";
				
			sqlString.append("SELECT ");
			// MWOHED File - Manufacturing Order Header
			sqlString.append("VHMFNO, ");
			// MITMAS File - Item Master
			sqlString.append("MMITNO, MMITDS ");
			
			sqlString.append("FROM " + library + "MITMAS ");
			
			// OCEAN SPRAY SPECIFIC List
			if ((inqBilling.getBillingType().trim().equals("OSA")) ||
				(inqBilling.getBillingType().trim().equals("BOP")))
			{	// must have inventory
				sqlString.append("INNER JOIN " + library + "MITLOC ON MLCONO = MMCONO ");
				sqlString.append("   and MMITNO = MLITNO ");
				
				sqlString.append("INNER JOIN " + library + "MILOMA ON LMCONO = MMCONO ");
				sqlString.append("   and LMITNO = MMITNO and LMBANO = MLBANO ");
				
				sqlString.append("INNER JOIN " + library + "MIATTR ON AGCONO = MMCONO ");
				sqlString.append("   and AGITNO = MMITNO and AGBANO = MLBANO and AGATNR = LMATNR ");
				sqlString.append("   and AGATID = 'TEMPORARY ORDER' and AGATVA = ' ' ");
				
				if (inqBilling.getBillingType().trim().equals("OSA"))
				{
					sqlString.append("INNER JOIN " + library + "MWOHED ON VHCONO = MMCONO ");
					sqlString.append("   and VHPRNO = MMITNO and CAST(VHMFNO AS CHAR(7)) = LMRORN ");
					sqlString.append("   and VHWHST > '20' ");
				}
				if (inqBilling.getBillingType().trim().equals("BOP"))
				{
					sqlString.append("INNER JOIN " + library + "MWOHED ON VHCONO = MMCONO ");
					sqlString.append("   and VHPRNO = MMITNO and CAST(VHMFNO AS CHAR(7)) = LMRORN ");
					sqlString.append("   and VHWHST = '90' ");
					sqlString.append("INNER JOIN " + libraryTT + "GNPADESC ON GNACMP = 1 ");					
					sqlString.append("   and GNATYP = 'BOP' and MMACRF = GNAKY1 ");
					sqlString.append("INNER JOIN " + libraryTT + "INPRCMST ON MMCONO = CMCONO ");
				}
			}
			
			// For non Ocean Spray, may need to tie to the Transactions to get Lots
			// will need the lots... tied to Temporary orders attributes, to figure if billed
			
			sqlString.append("WHERE MMCONO = '100' ");
			
			if (inqBilling.getBillingType().trim().equals("OSA"))			
				sqlString.append(" and MMITTY = '105' ");
			
			if (inqBilling.getBillingType().trim().equals("BOP"))	
			{
				sqlString.append(" and MMITTY = '110' and MMITGR not like 'LT%' and MMATMO <> ' ' ");
				sqlString.append(" and VHRSDT >= CMSTRT ");				
			}
			
			sqlString.append("GROUP BY ");
			sqlString.append("VHMFNO, MMITNO, MMITDS ");
			
			sqlString.append("ORDER BY MMITNO ");
	
			//	*********************************************************************************
		} catch (Exception e) {
				throwError.append(" Error building sqlDropDownMOs statement. " + e);
		}
			
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceContractManufacturing.");
			throwError.append("BuildSQL.sqlDropDownMOs(InqBilling)");
			throw new Exception(throwError.toString());
		}
		return sqlString.toString();
	}

	/**
	 * @author deisen  6/20/13
	 * Build an SQL statement to retrieve item models without 'TEMPORARY ORDER' attribute.
	 * Send in InqBilling -
	 *      Environment 
	 * 		Billing Type
	 */
	private static String sqlListModelsWOAttribute(UpdBilling updBilling)
	throws Exception {
		StringBuffer sqlString       = new StringBuffer();	
		StringBuffer throwError      = new StringBuffer();
		try {
		
			// Determine Library
			String library = GeneralUtility.getLibrary(updBilling.getEnvironment()) + ".";
			String libraryTT = GeneralUtility.getTTLibrary(updBilling.getEnvironment()) + ".";
				
			sqlString.append("SELECT VHCONO, im.MMATMO, ");
			
			sqlString.append("(SELECT CAST((AEANSQ + 10) as NUMERIC(4,0)) ");
			sqlString.append(" FROM " + library + "MAMOLI ");
			sqlString.append(" WHERE AEATMO = im.MMATMO ");
			sqlString.append(" ORDER BY AECONO, AEANSQ DESC FETCH FIRST 1 ROWS ONLY) as MMNXSQ ");			
			
			sqlString.append("FROM " + library + "MWOHED ");
			
			sqlString.append("INNER JOIN " + library + "MITMAS im ON VHCONO = im.MMCONO ");
			sqlString.append("   and VHPRNO = im.MMITNO ");
			sqlString.append("INNER JOIN " + libraryTT + "GNPADESC ON GNACMP = 1 ");
			sqlString.append("   and GNATYP = 'BOP' and im.MMACRF = GNAKY1 ");	
			sqlString.append("INNER JOIN " + libraryTT + "INPRCMST ON VHCONO = CMCONO ");
			
			sqlString.append("LEFT EXCEPTION JOIN " + library + "MAMOLI ON im.MMCONO = AECONO ");
			sqlString.append("   and VHPRNO = im.MMITNO and im.MMATMO = AEATMO ");
			sqlString.append("   and AEATID = 'TEMPORARY ORDER' ");

			sqlString.append("WHERE VHCONO = " + updBilling.getCompany().trim());
			sqlString.append("   and VHWHST = '90' ");
			sqlString.append("   and VHRSDT >= CMSTRT ");			
			sqlString.append("   and im.MMITTY = '110' and im.MMITGR not like 'LT%' ");
			sqlString.append("   and im.MMATMO <> ' ' ");
		
			sqlString.append("GROUP BY VHCONO, im.MMATMO ");
			sqlString.append("ORDER BY VHCONO, im.MMATMO ");
	
			//	*********************************************************************************
		} catch (Exception e) {
				throwError.append(" Error building sqlListCustomLotsWOAttr statement. " + e);
		}
			
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceContractManufacturing.");
			throwError.append("BuildSQL.sqlListCustomLotsWOAttribute(UpdBilling)");
			throw new Exception(throwError.toString());
		}
		return sqlString.toString();
	}

	/**
		 * @author deisen  6/20/13
		 * Build an SQL statement to retrieve MO lots without 'TEMPORARY ORDER' attribute.
		 * Send in InqBilling -
		 *      Environment 
		 * 		Billing Type
		 */
		private static String sqlListCustomLotsWOAttribute(UpdBilling updBilling)
		throws Exception {
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
			
				// Determine Library
				String library = GeneralUtility.getLibrary(updBilling.getEnvironment()) + ".";
				String libraryTT = GeneralUtility.getTTLibrary(updBilling.getEnvironment()) + ".";
					
				sqlString.append("SELECT VHCONO, VHMFNO, VHPRNO, LMBANO, LMATNR, MMACRF, MMITDS, MMUNMS ");
				
				sqlString.append("FROM " + library + "MWOHED ");
				
				sqlString.append("INNER JOIN " + library + "MITMAS ON VHCONO = MMCONO and VHPRNO = MMITNO ");			
				sqlString.append("INNER JOIN " + libraryTT + "GNPADESC ON GNACMP = 1 ");
				sqlString.append("   and GNATYP = 'BOP' and MMACRF = GNAKY1 ");
				
				sqlString.append("INNER JOIN " + library + "MILOMA ON VHCONO = LMCONO and VHPRNO = LMITNO ");
				sqlString.append("   and CAST(VHMFNO AS CHAR(7)) = LMRORN and LMBANO <> ' '");	
				sqlString.append("INNER JOIN " + libraryTT + "INPRCMST ON VHCONO = CMCONO ");
				sqlString.append("LEFT EXCEPTION JOIN " + library + "MIATTR ON LMCONO = AGCONO ");
				sqlString.append("   and LMITNO = AGITNO and LMBANO = AGBANO and LMATNR = AGATNR ");
				sqlString.append("   and AGATID = 'TEMPORARY ORDER' ");
	
				sqlString.append("WHERE VHCONO = " + updBilling.getCompany().trim());
				sqlString.append("   and VHWHST = '90' ");
				sqlString.append("   and VHRSDT >= CMSTRT ");				
				sqlString.append("   and MMITTY = '110' and MMITGR not like 'LT%' and MMATMO <> ' ' ");
				sqlString.append("ORDER BY MMACRF, VHMFNO, VHPRNO, LMBANO ");
	//			sqlString.append("FETCH FIRST 100 ROWS ONLY ");
		
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building sqlListCustomLotsWOAttr statement. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceContractManufacturing.");
				throwError.append("BuildSQL.sqlListCustomLotsWOAttribute(UpdBilling)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

	/**
	 * @author deisen  5/26/14
	 * Build an SQL statement to retrieve the enviroment for processing.		
	 */
		private static String sqlGetEnvironment(InqBilling inValues)
		throws Exception {
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
			
				// Determine Library
				String libraryTT = GeneralUtility.getTTLibrary(inValues.getEnvironment()) + ".";
					
				sqlString.append("SELECT CMENVR ");
				
				sqlString.append("FROM " + libraryTT + "INPRCMST ");				
				
				sqlString.append("FETCH FIRST 1 ROWS ONLY ");
		
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building sqlGetEnvironment statement. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceContractManufacturing.");
				throwError.append("BuildSQL.sqlGetEnvironment(InqBilling)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}
	
} // END of the Build SQL class OBJECT

/**
 * @author twalto 4/6/12
 * Load fields retrieved from Result Sets, running SQL statements.
 */
private static class LoadFields {

	/**
	 * @author twalto  4/6/12
	 * Build an ManufacturingOrder Object from a Result Set
	 * Send in Result Set
	 */
	private static ManufacturingOrder loadMO(ResultSet rs)
	throws Exception {
		ManufacturingOrder returnObject     = new ManufacturingOrder();	
		StringBuffer throwError      = new StringBuffer();
		try {
			//	 MO Header -- MWOHED --
			returnObject.setCompanyNo(rs.getString("VHCONO").trim());
			
			Warehouse newWhse = new Warehouse();
			newWhse.setFacility(rs.getString("VHFACI").trim());
			newWhse.setFacilityDescription(rs.getString("FACINAME").trim());
			newWhse.setWarehouse(rs.getString("MTWHLO").trim());
			newWhse.setWarehouseDescription(rs.getString("WHSENAME").trim());
			returnObject.setWarehouse(newWhse);
			
			Item newItem = new Item();
			newItem.setItemNumber(rs.getString("VHPRNO").trim());
			newItem.setItemDescription(rs.getString("MMITDS").trim());
			newItem.setBasicUnitOfMeasure(rs.getString("MMUNMS").trim());
			newItem.setM3ItemAliasPopular(rs.getString("OSITEM").trim());
			returnObject.setItem(newItem);
			
			returnObject.setOrderNumber(rs.getString("VHMFNO").trim());
			returnObject.setOrderStatus(rs.getString("VHWHST").trim());
			returnObject.setActualStartDate(UtilityDateTime.getDateFromyyyyMMdd(rs.getString("VHRSDT").trim()));
			
			returnObject.setOrderQuantity(rs.getBigDecimal("VHOROQ"));
			returnObject.setProduction(rs.getBigDecimal("VHMAQT"));
		//	*********************************************************************************
		} catch (Exception e) {
			    throwError.append("Error at com.treetop.services.ServiceContractManufacturing.");
				throwError.append(" Error in LoadFields.loadMO(rs). " + e);
				throw new Exception(throwError.toString());
		}
		return returnObject;
	}

	/**
	 * @author twalto  4/6/12
	 * Build an Lot Object from a Result Set
	 * Send in Result Set
	 */
	private static Lot loadLot(ResultSet rs)
	throws Exception {
		Lot returnObject     = new Lot();	
		StringBuffer throwError      = new StringBuffer();
		try {
			//	 Inventory -- Location Detail - Balance ID File -- MITLOC --

			returnObject.setCompany(rs.getString("BIDCONO"));
			returnObject.setFacility(rs.getString("BIDFACI").trim());
			returnObject.setFacilityName(rs.getString("FACINAME").trim());
			returnObject.setWarehouse(rs.getString("BIDWHLO").trim());
			returnObject.setWarehouseName(rs.getString("WHSENAME").trim());
			returnObject.setLocation(rs.getString("BIDWHSL").trim());
			returnObject.setItemNumber(rs.getString("VHPRNO").trim());
			returnObject.setItemDescription(rs.getString("MMITDS").trim());
			returnObject.setBasicUnitOfMeasure(rs.getString("MMUNMS").trim());
			returnObject.setLotNumber(rs.getString("MTBANO").trim());
			returnObject.setStatus(rs.getString("BIDSTAS").trim());
			returnObject.setQuantity(rs.getString("BIDSTQT").trim());
			
			returnObject.setAttributeNumber(rs.getString("LOTATNR").trim());
			
			returnObject.setTempOrder(rs.getString("ATVALUE").trim());
			
			returnObject.setOriginalQuantity(rs.getString("transactionQty").trim());
			returnObject.setManufactureDate(rs.getString("LOTMFDT"));
			returnObject.setOSLot(rs.getString("LOTOS1") + " " + rs.getString("LOTOS2"));
			
				
//				*********************************************************************************
		} catch (Exception e) {
			    throwError.append("Error at com.treetop.services.ServiceContractManufacturing.");
				throwError.append(" Error in LoadFields.loadLot(rs). " + e);
				throw new Exception(throwError.toString());
		}
	return returnObject;
	}

	/**
	 * @author twalto  5/1/12
	 * Build a Drop Down Single Object from a Result Set
	 * Send in Result Set
	 */
	private static DropDownSingle loadDropDownMO(ResultSet rs)
		throws Exception {
		DropDownSingle returnObject = new DropDownSingle();	
		StringBuffer throwError     = new StringBuffer();
		try {
			returnObject.setValue(rs.getString("VHMFNO"));
			returnObject.setDescription("Item: " + rs.getString("MMITNO").trim() + " - " + rs.getString("MMITDS").trim());
						
//		*********************************************************************************
		} catch (Exception e) {
			    throwError.append("Error at com.treetop.services.ServiceContractManufacturing.");
				throwError.append(" Error in LoadFields.loadLot(rs). " + e);
				throw new Exception(throwError.toString());
		}
	return returnObject;
	}

	/**
	 * @author deisen  2/26/13
	 * Update an UpdBilling Object from a Result Set
	 * Send in Result Set with UpdBilling
	 */
	private static UpdBilling loadCustomPackMO(UpdBilling updBilling, ResultSet rs)
	throws Exception {

		StringBuffer throwError = new StringBuffer();
		
		try {
	
			updBilling.setWarehouseNumber(rs.getString("VHWHLO").trim());
			updBilling.setItemNumber(rs.getString("VHITNO").trim());		
			updBilling.setUnitOfMeasure(rs.getString("VHMAUN").trim());
			updBilling.setProductionDate(rs.getString("VHRSDT").trim());		
			updBilling.setProductionQuantity(rs.getString("VHMAQT").trim());
			updBilling.setCustomerNumber(rs.getString("OKCUNO").trim());
			updBilling.setOrderFacility(rs.getString("OKFACI").trim());
			updBilling.setOrderType(rs.getString("OKORTP").trim());
			updBilling.setUpdateUser(rs.getString("OKRESP").trim());
			updBilling.setItemDecimals(Integer.valueOf(rs.getString("MMDCCD").trim()));
			updBilling.setItemDescription(rs.getString("MMITDS").trim());
			
			BigDecimal quantity = BigDecimal.ZERO.setScale(updBilling.getItemDecimals());
			quantity = new BigDecimal(updBilling.getProductionQuantity());
			updBilling.setProductionQuantity(quantity.toString());
		
		//	*********************************************************************************
		} catch (Exception e) {
			    throwError.append("Error at com.treetop.services.ServiceContractManufacturing.");
				throwError.append(" Error in LoadFields.loadCustomPackMO(UpdBilling, rs). " + e);
				throw new Exception(throwError.toString());
		}
		return updBilling;
	}

	/**
		 * @author deisen  6/20/13
		 * Build an Lot Object from a Result Set
		 * Send in Result Set
		 */
		private static Lot loadLotCustom(ResultSet rs)
		throws Exception {
			
			Lot returnObject = new Lot();	
			StringBuffer throwError = new StringBuffer();
			
			try {
				
				returnObject.setCompany(rs.getString("VHCONO"));				
				returnObject.setItemNumber(rs.getString("VHPRNO").trim());
				returnObject.setItemDescription(rs.getString("MMITDS").trim());
				returnObject.setBasicUnitOfMeasure(rs.getString("MMUNMS").trim());
				returnObject.setLotNumber(rs.getString("LMBANO").trim());
				returnObject.setAttributeNumber(rs.getString("LMATNR").trim());
				returnObject.setProductOwner(rs.getString("MMACRF").trim());
								
	//				*********************************************************************************
			} catch (Exception e) {
				    throwError.append("Error at com.treetop.services.ServiceContractManufacturing.");
					throwError.append(" Error in LoadFields.loadLotCustom(rs). " + e);
					throw new Exception(throwError.toString());
			}
		return returnObject;
		}

	/**
		 * @author deisen  6/20/13
		 * Build an Object from a Result Set
		 * Send in Result Set
		 */
		private static Lot loadModelCustom(ResultSet rs)
		throws Exception {
			
			Lot returnObject = new Lot();	
			StringBuffer throwError = new StringBuffer();
			
			try {
				
				returnObject.setCompany(rs.getString("VHCONO"));			
				returnObject.setAttributeModel(rs.getString("MMATMO").trim());
				returnObject.setAttributeNumber(rs.getString("MMNXSQ").trim());
												
	//				*********************************************************************************
			} catch (Exception e) {
				    throwError.append("Error at com.treetop.services.ServiceContractManufacturing.");
					throwError.append(" Error in LoadFields.loadModelCustom(rs). " + e);
					throw new Exception(throwError.toString());
			}
		return returnObject;
		}
	
} // END of the Load Fields class OBJECT

/**
 *  5/1/12 - TWalton
 *
 *   //  Return the list of Drop Down Single's
 *   //    Will use the billing Type to help determine what is ready to be billed
 *   
 * @param -- Send in the InqBilling Object for use in the SQL statement
 * @return Vector of Drop Down Single
 * @throws Exception
 */
private static Vector<DropDownSingle> dropDownMOs(InqBilling inValues, Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector<DropDownSingle> returnValue = new Vector<DropDownSingle>();
	ResultSet rs = null;
	PreparedStatement listThem = null;

	try
	{
	   if ((inValues.getBillingType().trim().equals("OSA")) ||
		   (inValues.getBillingType().trim().equals("BOP")))
	   {
		   try {
			   listThem = conn.prepareStatement(BuildSQL.sqlDropDownMOs(inValues));
			   rs = listThem.executeQuery();
		   } catch(Exception e){
			   throwError.append("Error occured Building or Executing a sql statement. " + e);
		   }
		   if (throwError.toString().equals(""))
		   {
			   try
			   {
				   while (rs.next())
				   {
					   returnValue.addElement(LoadFields.loadDropDownMO(rs));
				   }
			   } catch(Exception e){
				   throwError.append("Error occured Reading the Executed a sql statement. " + e);
			   }
		   } // END of the While Statement
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
		throwError.append("ServiceContractManufacturing.");
		throwError.append("listLotsFromMO(");
		throwError.append("InqBilling, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 *   5/1/12 - TWalton
 *
 *   //  Return the list of Drop Down Single's
 *   //    Will use the billing Type to help determine what is ready to be billed
 *   
 * @param -- Send in the InqBilling Object for use in the SQL statement
 * @return Vector of Drop Down Single
 * @throws Exception
 */
public static Vector<DropDownSingle> dropDownMOs(InqBilling inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector<DropDownSingle> returnValue = new Vector<DropDownSingle>();
	Connection conn = null;
	
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack11();
		returnValue = dropDownMOs(inValues, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				ServiceConnection.returnConnectionStack11(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceContractManufacturing.");
		throwError.append("dropDownMOs(Vector). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 *   3/23/12 - TWalton
 *   // Need to determine and update the valued information
 *   // Currently coded as Quick and Dirty... to get item adjusted
 *   
 *   All code needs reviewed
 *   
 * @param -- Send in the Selection Criteria from InqInventory
 * @return BeanInventory
 * @throws Exception
 */
private static BeanContractManufacturing listLotsFromMO(InqBilling inValues, Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	BeanContractManufacturing returnValue = new BeanContractManufacturing();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	// Request Type will be used for building the SQL statement AND for loading the fields.

	try
	{
		try {
			listThem = conn.prepareStatement(BuildSQL.sqlListLotsFromMO(inValues));
			rs = listThem.executeQuery();
		} catch(Exception e)
		{
			throwError.append("Error occured Building or Executing a sql statement. " + e);
		}
		if (throwError.toString().equals(""))
		{
			try
			{
				Vector<Lot> listLots = new Vector<Lot>();
				BigDecimal totalManufacturedQty = new BigDecimal(0);
				BigDecimal totalInventoryQty    = new BigDecimal(0);
				String saveLot = "";
				while (rs.next())
				{
					try {
						if (returnValue.getMoHeader().getOrderNumber().trim().equals(""))
						{ // if this had not been loaded, load this one
							returnValue.setMoHeader(LoadFields.loadMO(rs));
						}
						Lot thisrow = LoadFields.loadLot(rs);
						if (!saveLot.trim().equals(thisrow.getLotNumber()))
						{
						   totalManufacturedQty = totalManufacturedQty.add(new BigDecimal(thisrow.getOriginalQuantity()));
						   saveLot = thisrow.getLotNumber();
						}
						totalInventoryQty = totalInventoryQty.add(new BigDecimal(thisrow.getQuantity()));
						listLots.addElement(thisrow);
						
					} catch(Exception e)
					{
						throwError.append("Error occured Reading the Executed a sql statement. " + e);
					}
				}// END of the While Statement
				Lot totalLine = new Lot();
				totalLine.setOriginalQuantity(totalManufacturedQty.toString());
				totalLine.setQuantity(totalInventoryQty.toString());
				listLots.addElement(totalLine);
				returnValue.setListLots(listLots);
				
				// Build a TOTAL Record for the last in the VECTOR
				
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
		throwError.append("ServiceContractManufacturing.");
		throwError.append("listLotsFromMO(");
		throwError.append("InqBilling, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 *   5/2/12 - TWalton
 *   // Process the Billing For Ocean Spray
 *    -- Use methods to 
 *          create a Temporary PO
 *          adjust the attributes to put the temporary order number in the value
 *          adjust the inventory off -- specific to OSA   
 *   
 * @param -- Send in the UpdBilling
 * @return BeanInventory
 * @throws Exception
 */
private static BeanContractManufacturing processBillingForOceanSpray(UpdBilling inValues, Connection conn)
throws Exception
{
	BeanContractManufacturing returnValue = new BeanContractManufacturing();
	StringBuffer throwError = new StringBuffer();
	try{
	// go out and get the list of lots, and the MO information needed to process the billing
		
		InqBilling ib = new InqBilling();
		ib.setEnvironment(inValues.getEnvironment());
		ib.setBillingType(inValues.getBillingType());
		ib.setManufacturingOrderNumber(inValues.getManufacturingOrderNumber());
		
		returnValue = listLotsFromMO(ib, conn);
		if (!returnValue.getListLots().isEmpty())
		{
			OIS100MIAddBatchHead abh = new OIS100MIAddBatchHead();
			//---------------------------------------------------------------------------
			// Create the Temporary Customer Order from the MO information retrieved.
			// built this within this method because it will be Ocean Spray Customer Specific
			//    Information will be Hard Coded Specific to what is needed for Ocean Spray
			try{
				abh.setSentFromProgram("Ocean Spray Billing");
				abh.setEnvironment(inValues.getEnvironment());
				//abh.setEnvironment("TST");
		//		abh.setUserProfile("DPERAL");
		//		  System.out.println("Set User to DPERAL");
		//		abh.setUserProfile(inValues.ib.get)
				abh.setCompany("100");
				abh.setCustomerNumber("34000"); // Ocean Spray Alliance Customer Number
				abh.setOrderType("612");
				abh.setRequestedDeliveryDate(returnValue.getMoHeader().getActualStartDate().getDateFormatyyyyMMdd());
				abh.setFacility("150");
				abh.setCustomerOrderNumber(returnValue.getMoHeader().getOrderNumber());
				abh.setCustomersPODate(abh.getRequestedDeliveryDate());
				abh.setOrderDate(abh.getRequestedDeliveryDate());
				abh.setWarehouse("209");
			
				// 	ONE LINE PER ORDER
				Vector<OIS100MIAddBatchLine> listLines = new Vector<OIS100MIAddBatchLine>();
				OIS100MIAddBatchLine abl = new OIS100MIAddBatchLine();
				abl.setEnvironment(abh.getEnvironment());
				abl.setCompany(abh.getCompany());
				abl.setWarehouse(abh.getWarehouse());
				abl.setUnitOfMeasure("CS");
				abl.setItemNumber(returnValue.getMoHeader().getItem().getItemNumber());
				// Quantity
				Lot totalLine = (Lot) returnValue.getListLots().elementAt((returnValue.getListLots().size()-1));
				abl.setQuantity(totalLine.getQuantity());
				listLines.addElement(abl);
				abh.setListLines(listLines);
			
				returnValue.setTempCONumber(OIS100MI.processBatchHeadLine(abh));
				
			}catch(Exception e){
				throwError.append("Exception caught when creating of a Temporary Customer Order: " + e);
			}
	  	  	if (throwError.toString().trim().equals(""))
	  		{
				// At this point the Temporary Customer Order is Input Into M3
				// 	The Temporary Customer Order Number needs to be entered into each LOT Attribute
		    
				// Read Through each of the lots, update the attribute 
	  	  		   // ALSO adjust the inventory off as we go
	  	  		try{
  	  				// Build Vector of new Attribute Values
	  	  			Vector<ATS101MISetAttrValue> listNewAttr = new Vector<ATS101MISetAttrValue>();
	  	  			
  	  				// Build Vector of Lots to Adjust Off
  	  				Vector<MMS850MIAddAdjust> lotsToAdjust = new Vector<MMS850MIAddAdjust>();
  	  				
  	  			System.out.println("will need to Send in the Date of the adjustment");
  	  			
	  	  			for (int x = 0; x < (returnValue.getListLots().size() - 1); x++)
	  	  			{
	  	  				Lot thisOne = (Lot) returnValue.getListLots().elementAt(x);
	  	  				// Only process the records that have inventory
	  	  				if (!HTMLHelpersMasking.maskNumber(thisOne.getQuantity(), 0).trim().equals("0"))
	  	  				{
	  	  					// Build the Attribute update piece
	  	  					ATS101MISetAttrValue sav = new ATS101MISetAttrValue();
	  	  					sav.setSentFromProgram(abh.getSentFromProgram());
	  	  					sav.setEnvironment(abh.getEnvironment());
	  	  					sav.setCompany(abh.getCompany());
	  	  					sav.setAttributeID("TEMPORARY ORDER");
	  	  					sav.setAttributeValue(returnValue.getTempCONumber());
	  	  					sav.setAttributeNumber(thisOne.getAttributeNumber());
	  	  					listNewAttr.addElement(sav);
	  	  					// Build the Item/Qty inventory adjustment
	  	  					MMS850MIAddAdjust aAdj = new MMS850MIAddAdjust();
	  	  					aAdj.setSentFromProgram(abh.getSentFromProgram());
	  	  					aAdj.setEnvironment(abh.getEnvironment());
	  	  					aAdj.setCompany(abh.getCompany());
	  	  					aAdj.setItemNumber(returnValue.getMoHeader().getItem().getItemNumber());
	  	  					aAdj.setUnitOfMeasure("CS");
	  	  					aAdj.setDateGenerated(inValues.getTransactionDate());
	  	  					aAdj.setWarehouse(thisOne.getWarehouse().trim());
	  	  					aAdj.setLocation(thisOne.getLocation().trim());
	  	  					aAdj.setLotNumber(thisOne.getLotNumber().trim());
	  	  					aAdj.setQuantity("-" + HTMLHelpersMasking.maskNumber(thisOne.getQuantity(), 0));
	  	  					lotsToAdjust.addElement(aAdj);
	  	  				}
	  	  			}
	  	  			if (!listNewAttr.isEmpty())
	  	  			{
	  	  			//  Vector<String> returnvalues = ATS101MI.setAttrValue(listNewAttr);
	  	  			//	System.out.println("Send in listing of Attributes");
	  	  			}
	  	  			if (!lotsToAdjust.isEmpty())
	  	  			{
	  	  				MMS850MI.addAdjust(lotsToAdjust);
	  	  			//	System.out.println("Send in Listing of Lots to Adjust Off");
	  	  			}
	  	  		}catch(Exception e){
					throwError.append("Exception caught when Updating Lot Attributes with Temporary Customer Order: " + e);
				}
	  		}
		}// Has Lot numbers to Process
		
	}catch(Exception e)
	{
		System.out.println(" Error occured while Processing Billing for Ocean Spray. " + e);
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceContractManufacturing.");
		throwError.append("processBillingForOceanSpray(");
		throwError.append("UpdBilling, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 *   2/22/13 - DEisen
 *
 *   Work through the process applicable to the billing type
 *   
 * @param -- Send in the inqBilling Object for use in the SQL statement to retrieve MOs.
 * @return Business Object bean (BeanContractManufacturing)
 * @throws Exception
 */
public static Vector<BeanContractManufacturing> processBillingForCustomMO(InqBilling inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();	
	Vector<BeanContractManufacturing> customPackList = new Vector<BeanContractManufacturing>();
	Connection conn = null;
	
	try {
		
		conn = ServiceConnection.getConnectionStack11();
		
		UpdBilling billing = new UpdBilling();
		billing.setEnvironment(inValues.getEnvironment());
		billing.setCompany(inValues.getCompany());
		billing = listModelsWOAttribute(billing, conn);
		billing = insertModelAttribute(billing, conn);
		billing = listCustomLotsWOAttribute(billing, conn);
		billing = insertCustomLotAttribute(billing);
		
		// BOP - Custom Pack billing on production
		
		if (inValues.getBillingType().trim().equals("BOP"))
		{
			Vector<DropDownSingle> listOfMOs = new Vector<DropDownSingle>();
			listOfMOs = dropDownMOs(inValues, conn);

			if (listOfMOs.isEmpty() == false)
			{			
				for (int y = 0; listOfMOs.size() > y; y++)
				{
					DropDownSingle thisMO = listOfMOs.elementAt(y);
					inValues.setManufacturingOrderNumber(thisMO.getValue());
					
					UpdBilling oneMO = new UpdBilling();
					oneMO.setEnvironment(inValues.getEnvironment());
					oneMO.setCompany(inValues.getCompany());
					oneMO.setDivision(inValues.getDivision());
					oneMO.setBillingType(inValues.getBillingType());
					oneMO.setManufacturingOrderNumber(thisMO.getValue());
					
					oneMO = buildBillingForCustomMO(oneMO, conn);	
					
					BeanContractManufacturing lotList = new BeanContractManufacturing();
					lotList = listLotsFromMO(inValues, conn);
					oneMO.setListLots(lotList.getListLots());
					
					Vector<String> listAttr = processBillingForCustomPack(oneMO, conn);
					
					ManufacturingOrder production = new ManufacturingOrder();
					production.setCompanyNo(oneMO.getCompany());
					production.setOrderNumber(oneMO.getManufacturingOrderNumber());
					
					Warehouse warehouse = new Warehouse();					
					warehouse.setWarehouse(oneMO.getWarehouseNumber());
					production.setWarehouse(warehouse);
					
					Item item = new Item();
					item.setItemNumber(oneMO.getItemNumber());
					production.setItem(item);					
					
					BigDecimal quantity = BigDecimal.ZERO.setScale(oneMO.getItemDecimals());
					quantity = new BigDecimal(oneMO.getProductionQuantity());
					production.setOrderQuantity(quantity);
					
					DateTime dateTime = new DateTime();
					dateTime = UtilityDateTime.getDateFromyyyyMMdd(oneMO.getProductionDate());
					production.setActualStartDate(dateTime);
					
					BeanContractManufacturing customPack = new BeanContractManufacturing();
					customPack.setMoHeader(production);
					customPack.setTempCONumber(oneMO.getTempCONumber());
					customPack.setListLots(oneMO.getListLots());
					customPack.setListAttr(listAttr);
					customPackList.addElement(customPack);					
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
				ServiceConnection.returnConnectionStack11(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceContractManufacturing.");
		throwError.append("processBillingForCustomMO(InqBilling). ");
		throw new Exception(throwError.toString());
	}

	return customPackList;
}

/**
 *   2/26/13 - DEisen
 *   // Process the Billing For Custom Pack Customers
 *    -- Use methods to 
 *          create a Temporary Order
 *          adjust the attributes to put the temporary order number in the value
 *   
 * @param -- Send in the UpdBilling
 * @throws Exception
 */
private static Vector<String> processBillingForCustomPack(UpdBilling inValues, Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Vector<String> returnValues = new Vector<String>();
	
	try {		

		if (!inValues.getManufacturingOrderNumber().trim().equals(""))
		{
			OIS100MIAddBatchHead abh = new OIS100MIAddBatchHead();
			OIS100MIAddBatchLine abl = new OIS100MIAddBatchLine();		
			
			try {
				
				//---------------------------------------------------------------------------
				// Create the Temporary Customer Order Header from the MO information retrieved.
				
				abh.setSentFromProgram("Custom Pack Billing");
				abh.setEnvironment(inValues.getEnvironment());
				abh.setCompany(inValues.getCompany());
				abh.setCustomerNumber(inValues.getCustomerNumber());				
				abh.setOrderType(inValues.getOrderType());	
				abh.setRequestedDeliveryDate(inValues.getProductionDate());	
				abh.setFacility(inValues.getOrderFacility());	
				abh.setCustomerOrderNumber(inValues.getManufacturingOrderNumber());
				abh.setCustomersPODate(inValues.getProductionDate());	
				abh.setOrderDate(inValues.getProductionDate());	
				abh.setWarehouse(inValues.getWarehouseNumber());
				abh.setUserProfile(inValues.getUpdateUser());
										
				//---------------------------------------------------------------------------
				// Create the Temporary Customer Order Line from the MO information retrieved.
				
				Vector<OIS100MIAddBatchLine> orderLines = new Vector<OIS100MIAddBatchLine>();
				
				abl.setSentFromProgram("Custom Pack Billing");
				abl.setEnvironment(abh.getEnvironment());
				abl.setCompany(abh.getCompany());
				abl.setWarehouse(abh.getWarehouse());
				abl.setUnitOfMeasure(inValues.getUnitOfMeasure());	
				abl.setItemNumber(inValues.getItemNumber());
				abl.setItemDescription(inValues.getItemDescription());
				abl.setQuantity(inValues.getProductionQuantity());
				abl.setUserProfile(inValues.getUpdateUser());
				abl.setDescription1("Bill On Production");
				
				orderLines.addElement(abl);
				abh.setListLines(orderLines);
				
				inValues.setTempCONumber(OIS100MI.processBatchHeadLine(abh));
				
			}catch(Exception e){
				throwError.append("Exception caught when creating of a Temporary Customer Order: " + e);
			}
	  	  	if (throwError.toString().trim().equals(""))
	  		{
				// At this point the Temporary Customer Order is Entered Into M3
				// 	The Temporary Customer Order Number needs to be entered into each LOT Attribute
		    
	  	  		try {
	  	  			
	  	  			// Read Through each of the lots, update the attribute 

  	  				// Build Vector of new Attribute Values
	  	  			Vector<ATS101MISetAttrValue> listNewAttr = new Vector<ATS101MISetAttrValue>();	  	  			
  	  			
	  	  			for (int x = 0; x < (inValues.getListLots().size() - 1); x++)
	  	  			{
	  	  				Lot thisOne = (Lot) inValues.getListLots().elementAt(x);
	  	  		
  	  					// Build the Attribute update piece
  	  					ATS101MISetAttrValue sav = new ATS101MISetAttrValue();
  	  					sav.setSentFromProgram(abh.getSentFromProgram());
  	  					sav.setEnvironment(abh.getEnvironment());
  	  					sav.setCompany(abh.getCompany());
  	  					sav.setAttributeID("TEMPORARY ORDER");
  	  					sav.setAttributeValue(inValues.getTempCONumber());
  	  					sav.setAttributeNumber(thisOne.getAttributeNumber());
  	  					listNewAttr.addElement(sav);  	  				
	  	  			}
	  	  			if (!listNewAttr.isEmpty())
	  	  			{	  	  			  
	  	  			  returnValues = ATS101MI.setAttrValue(listNewAttr);	  	  			
	  	  			}
	  	  			
	  	  		}catch(Exception e){
					throwError.append("Exception caught when Updating Lot Attributes with Temporary Customer Order: " + e);
				}
	  		}
		}// Has Lot numbers to Process
		
	}catch(Exception e)
	{
		System.out.println(" Error occured while Processing Billing for Custom Pack. " + e);
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceContractManufacturing.");
		throwError.append("processBillingForOceanSpray(");
		throwError.append("UpdBilling, conn). ");
		throw new Exception(throwError.toString());
	}

	return returnValues;
}

/**
 *   5/2/12 - TWalton
 *
 *   Work through the process applicable to the billing type
 *   
 * @param -- Send in the UpdBilling Object for use in the SQL statement
 *      MUST include MO and Billing Type
 * @return Vector of Business Object DataSet.
 * @throws Exception
 */
public static BeanContractManufacturing processBillingForMO(UpdBilling inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	BeanContractManufacturing returnValue = new BeanContractManufacturing();
	Connection conn = null;
	
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack11();
		
		// OSA - Ocean Spray Alliance billing for Production at Selah Plant
		if (inValues.getBillingType().trim().equals("OSA"))
		   returnValue = processBillingForOceanSpray(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		if (conn != null)
		{
			try
			{
				ServiceConnection.returnConnectionStack11(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceContractManufacturing.");
		throwError.append("processBillingForMO(UpdBilling). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 *   4/4/12 - TWalton
 *
 *   //  Return the list of Lot objects for display on the screen
 *   
 * @param -- Send in the InqBilling Object for use in the SQL statement
 * @return Vector of Business Object DataSet.
 * @throws Exception
 */
public static BeanContractManufacturing listLotsFromMO(InqBilling inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	BeanContractManufacturing returnValue = new BeanContractManufacturing();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack11();
		returnValue = listLotsFromMO(inValues, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				ServiceConnection.returnConnectionStack11(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceContractManufacturing.");
		throwError.append("listLotsFromMO(Vector). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 *   2/25/13 - DEisen
 *   
 * @param -- Send in the Selection Criteria from UpdBilling
 * @return updated Selection Criteria UpdBilling
 * @throws Exception
 */
private static UpdBilling buildBillingForCustomMO(UpdBilling inValues, Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();	
	ResultSet rs = null;
	PreparedStatement listThem = null;

	try
	{
		try {
			listThem = conn.prepareStatement(BuildSQL.sqlBillingCustomPackMO(inValues));
			rs = listThem.executeQuery();
			
		} catch(Exception e)
		{
			throwError.append("Error occured Building or Executing a sql statement. " + e);
		}
		if (throwError.toString().equals(""))
		{
			try
			{
				if (rs.next())
				{
					try {
						
						inValues = LoadFields.loadCustomPackMO(inValues, rs);						
						
					} catch(Exception e)
					{
						throwError.append("Error occured Reading the Executed a sql statement. " + e);
					}
				}
				
			} catch(Exception e)
			{
				throwError.append(" Error occured while Building UpdBilling from sql statement. " + e);
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
		throwError.append("ServiceContractManufacturing.");
		throwError.append("buildBillingForCustomMO(");
		throwError.append("UpdBilling, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return inValues;
}

/**
 *   2/25/13 - DEisen
 *   
 * @param -- Send in the UpdBilling Object for use in the SQL statement
 * @return UpdBilling of Business Object DataSet.
 * @throws Exception
 */
public static UpdBilling buildBillingForCustomMO(UpdBilling inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack11();
		inValues = buildBillingForCustomMO(inValues, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				ServiceConnection.returnConnectionStack11(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceContractManufacturing.");
		throwError.append("buildBillingForCustomMO(UpdBilling). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return inValues;
}

/**
 *   6/20/13 - DEisen
 *   
 * @param -- Send in the Selection Criteria from UpdBilling
 * @return updated Selection Criteria UpdBilling
 * @throws Exception
 */
private static UpdBilling listModelsWOAttribute(UpdBilling inValues, Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();	
	ResultSet rs = null;
	PreparedStatement listThem = null;
	
	Vector<Lot>	listLots = new Vector<Lot>();

	try
	{
		try {
			
			listThem = conn.prepareStatement(BuildSQL.sqlListModelsWOAttribute(inValues));
			rs = listThem.executeQuery();			
			
		} catch(Exception e)
		{
			throwError.append("Error occured Building or Executing a sql statement. " + e);
		}
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next())
				{
					try {
						
						listLots.addElement(LoadFields.loadModelCustom(rs));
						inValues.setListLots(listLots);
						
					} catch(Exception e)
					{
						throwError.append("Error occured Reading the Executed a sql statement. " + e);
					}
				}
				
			} catch(Exception e)
			{
				throwError.append(" Error occured while Building Models Without Attribute from sql statement. " + e);
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
		throwError.append("ServiceContractManufacturing.");
		throwError.append("listModelsWOAttribute(");
		throwError.append("UpdBilling, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return inValues;
}

/**
 *   7/26/13 - DEisen
 *   
 * @param -- Send in the UpdBilling Object for use in the SQL statement
 * @return UpdBilling of Business Object DataSet.
 * @throws Exception
 */
public static UpdBilling insertModelAttribute(UpdBilling inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack11();
		inValues = insertModelAttribute(inValues, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				ServiceConnection.returnConnectionStack11(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceContractManufacturing.");
		throwError.append("insertModelAttribute(UpdBilling). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return inValues;
}

/**
 *   6/20/13 - DEisen
 *   
 * @param -- Send in the Selection Criteria from UpdBilling
 * @return updated Selection Criteria UpdBilling
 * @throws Exception
 */
private static UpdBilling insertCustomLotAttribute(UpdBilling inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();	
	Vector<ATS101MIAddAttr> lots = new Vector<ATS101MIAddAttr>();
	UpdAttribute requestBean = new UpdAttribute();
	
	try {
		
		try {
			
			if (inValues.getListLots().isEmpty() == false)
			{			
				for (int y = 0; inValues.getListLots().size() > y; y++)
				{
					Lot thisLot = (Lot) inValues.getListLots().elementAt(y);
					
					ATS101MIAddAttr attribute = new ATS101MIAddAttr();
					attribute.setSentFromProgram("insertCustomLotAttribute");
					attribute.setEnvironment(inValues.getEnvironment());
					attribute.setUserProfile("M3SRVADM");
					attribute.setCompany(inValues.getCompany());
					attribute.setAttributeNumber(thisLot.getAttributeNumber());
					attribute.setAttributeID("TEMPORARY ORDER");								
					lots.addElement(attribute);
				}
				
				requestBean.setEnvironment(inValues.getEnvironment());
				requestBean.setAuthorization("Basic bTNzcnZhZG06dXkzdHlr");
				ServiceAttribute.addAttribute(lots, requestBean);  
			}
			
		} catch(Exception e)
		{
			throwError.append("Error occured processing lot for attribute insert. " + e);
		}
		if (throwError.toString().equals(""))
		{
			
		}		

	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceContractManufacturing.");
		throwError.append("insertCustomLotAttribute(");
		throwError.append("UpdBilling, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return inValues;
}

/**
 *   6/20/13 - DEisen 
 *   
 * @param -- Send in the UpdBilling Object for use in the SQL statement
 * @return UpdBilling of Business Object DataSet.
 * @throws Exception
 */
public static InqBilling getEnvironment(InqBilling inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	try {		
		conn = ServiceConnection.getConnectionStack11();
		inValues = getEnvironment(inValues, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				ServiceConnection.returnConnectionStack11(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceContractManufacturing.");
		throwError.append("getEnvironment(InqBilling). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return inValues;
}

/**
 *   5/26/14 - DEisen
 *   
 * @param -- Send in the Selection Criteria from UpdBilling
 * @return updated Selection Criteria UpdBilling
 * @throws Exception
 */
private static InqBilling getEnvironment(InqBilling inValues, Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();	
	ResultSet rs = null;
	PreparedStatement listThem = null;
	
	try
	{
		try {
			
			listThem = conn.prepareStatement(BuildSQL.sqlGetEnvironment(inValues));
			rs = listThem.executeQuery();
			
		} catch(Exception e)
		{
			throwError.append("Error occured Building or Executing a sql statement. " + e);
		}
		if (throwError.toString().equals(""))
		{
			try
			{
				if (rs.next())
				{
					try {
						
						inValues.setEnvironment(rs.getString("CMENVR"));	
						
					} catch(Exception e)
					{
						throwError.append("Error occured Reading the Executed a sql statement. " + e);
					}
				}
				
			} catch(Exception e)
			{
				throwError.append(" Error occured while Building Environment from sql statement. " + e);
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
		throwError.append("ServiceContractManufacturing.");
		throwError.append("getEnvironment(");
		throwError.append("InqBilling, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return inValues;
}
/**
 *   7/26/13 - DEisen
 *   
 * @param -- Send in the Selection Criteria from UpdBilling
 * @return insert Attribute into model
 * @throws Exception
 */
private static UpdBilling insertModelAttribute(UpdBilling inValues, Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement insertIt = null;

	try {
		
		DateTime dateToday = new DateTime();
		dateToday = UtilityDateTime.getSystemDate();
		
		String library = GeneralUtility.getLibrary(inValues.getEnvironment()) + ".";
		insertIt = conn.createStatement();
		
		try {
			
			if (inValues.getListLots().isEmpty() == false)
			{			
				for (int y = 0; inValues.getListLots().size() > y; y++)
				{
					Lot thisLot = (Lot) inValues.getListLots().elementAt(y);
					
					StringBuffer sqlString = new StringBuffer();					  
					sqlString.append("INSERT INTO " + library + "MAMOLI ");
					sqlString.append("VALUES(");	
					sqlString.append(inValues.getCompany().trim() + ",");
					sqlString.append("'" + thisLot.getAttributeModel().trim() + "',");	
					sqlString.append("'TEMPORARY ORDER',");
					sqlString.append(thisLot.getAttributeNumber().trim() + ",");					
					sqlString.append("2,");
					sqlString.append("' ',");
					sqlString.append("' ',");
					sqlString.append("' ',");
					sqlString.append("' ',");
					sqlString.append("0,");
					sqlString.append("0,");
					sqlString.append("0,");
					sqlString.append("0,");
					sqlString.append("0,");
					sqlString.append("0,");
					sqlString.append("0,");
					sqlString.append("0,");
					sqlString.append("1,");
					sqlString.append("0,");
					sqlString.append("0,");
					sqlString.append("0,");
					sqlString.append("0,");
					sqlString.append("' ',");
					sqlString.append("' ',");
					sqlString.append("' ',");
					sqlString.append("0,");
					sqlString.append("0,");
					sqlString.append(dateToday.getDateFormatyyyyMMdd().trim() + ",");
					sqlString.append(dateToday.getTimeFormathhmmss().trim() + ",");
					sqlString.append(dateToday.getDateFormatyyyyMMdd().trim() + ",");
					sqlString.append("1,");							   
					sqlString.append("'M3SRVADM',");
					sqlString.append("0,");
					sqlString.append("0,");
					sqlString.append("0");
					sqlString.append(")");
				  
					insertIt.executeUpdate(sqlString.toString());					
				}
				
			}
			
		} catch(Exception e)
		{
			throwError.append("Error occured processing model for attribute insert. " + e);
		}
		if (throwError.toString().equals(""))
		{
			
		}		

	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		try {								  
			if (insertIt != null)		  
				insertIt.close();					
		} catch(Exception el){
			el.printStackTrace();
		}				
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceContractManufacturing.");
		throwError.append("insertModelAttribute(");
		throwError.append("UpdBilling, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return inValues;
}

/**
 *   6/20/13 - DEisen
 *   
 * @param -- Send in the UpdBilling Object for use in the SQL statement
 * @return UpdBilling of Business Object DataSet.
 * @throws Exception
 */
public static UpdBilling listModelsWOAttribute(UpdBilling inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack11();
		inValues = listModelsWOAttribute(inValues, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				ServiceConnection.returnConnectionStack11(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceContractManufacturing.");
		throwError.append("listModelsWOAttribute(UpdBilling). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return inValues;
}

/**
 *   6/20/13 - DEisen
 *   
 * @param -- Send in the UpdBilling Object for use in the SQL statement
 * @return UpdBilling of Business Object DataSet.
 * @throws Exception
 */
public static UpdBilling listCustomLotsWOAttribute(UpdBilling inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack11();
		inValues = listCustomLotsWOAttribute(inValues, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
				ServiceConnection.returnConnectionStack11(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceContractManufacturing.");
		throwError.append("listCustomLotsWOAttribute(UpdBilling). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return inValues;
}

/**
 *   6/20/13 - DEisen
 *   
 * @param -- Send in the Selection Criteria from UpdBilling
 * @return updated Selection Criteria UpdBilling
 * @throws Exception
 */
private static UpdBilling listCustomLotsWOAttribute(UpdBilling inValues, Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();	
	ResultSet rs = null;
	PreparedStatement listThem = null;
	
	Vector<Lot>	listLots = new Vector<Lot>();

	try
	{
		try {
			
			listThem = conn.prepareStatement(BuildSQL.sqlListCustomLotsWOAttribute(inValues));
			rs = listThem.executeQuery();
			
		} catch(Exception e)
		{
			throwError.append("Error occured Building or Executing a sql statement. " + e);
		}
		if (throwError.toString().equals(""))
		{
			try
			{
				while (rs.next())
				{
					try {
						
						listLots.addElement(LoadFields.loadLotCustom(rs));
						inValues.setListLots(listLots);
						
					} catch(Exception e)
					{
						throwError.append("Error occured Reading the Executed a sql statement. " + e);
					}
				}
				
			} catch(Exception e)
			{
				throwError.append(" Error occured while Building Lots Without Attribute from sql statement. " + e);
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
		throwError.append("ServiceContractManufacturing.");
		throwError.append("listCustomLotsWOAttribute(");
		throwError.append("UpdBilling, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return inValues;
}

}



