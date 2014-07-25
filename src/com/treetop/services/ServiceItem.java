/*
 * Created on October 19, 2007
 *
  * 
 */
package com.treetop.services;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;

import javax.sql.*;
import com.ibm.as400.access.*;
import com.lawson.api.MMS025MIDeleteAlias;
import com.lawson.api.MMS850MI;
import com.lawson.api.MMS850MIAddRclLotSts;
import com.lawson.api.MMS025MI;
import com.lawson.api.MMS025MIAddAlias;

//import com.lawson.m3.conversion.ConversionNotesFileData;
import com.treetop.*;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.controller.operations.InqOperations;
import com.treetop.app.coa.*;
import com.treetop.app.item.InqItem;
import com.treetop.app.item.UpdItem;
import com.treetop.app.item.UpdItemVariance;
import com.treetop.app.item.InqCodeDate;
import com.treetop.app.quality.UpdSpecification;
import com.treetop.app.gtin.UpdGTIN;
import com.treetop.viewbeans.*;
import com.treetop.utilities.*;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HTMLHelpersMasking;
import com.treetop.utilities.html.HtmlOption;

/**
 * @author twalto .
 *
 * Services class to obtain and return data 
 * to business objects.
 * 
 * Certificate of Analysis 
 */
public class ServiceItem extends BaseService {
	
	public static final String library = "M3DJDPRD";
	public static final String ttlib = "DBPRD";
	//TST version currently
//	public static final String library = "M3DJDTST";
//	public static final String ttlib = "DBTST";
	
	/**
	 * 
	 */
	public ServiceItem() {
		super();
	}
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		try
		{
			//testing listProductStructureMaterials
			if (false) {
				InqItem item = new InqItem();
				item.setInqItem("102166");
				item.setEnvironment("PRD");
				BeanItem myBean;
				myBean = listProductStructureMaterials(item);
				String x = "All Done";
			}
			
			if (false) {
				CommonRequestBean crb = new CommonRequestBean();
				Vector dd = dropDownItemTypeHtmlOption(crb);
				System.out.println(dd);
			}
			
			if (false) {
				CommonRequestBean crb = new CommonRequestBean();
				crb.setEnvironment("PRD");
				crb.setCompanyNumber("100");
				crb.setIdLevel1("200");
				Vector dds = dropDownItemsByType(crb);
				String x = "All Done";
			}
			if (false){
				CommonRequestBean crb = new CommonRequestBean();
				crb.setCompanyNumber("100");
				crb.setDivisionNumber("100");
				crb.setEnvironment("PRD");
				crb.setIdLevel1("rawFruit");
				Vector dds = dropDownItemGroup(crb);
				String x = "All Done";
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	private static class BuildSQL {
		/**
		 * 
		 */
		private static String getListItemsByType(CommonRequestBean inBean)
		throws Exception {
			StringBuffer sqlString       = new StringBuffer();	
			StringBuffer throwError      = new StringBuffer();
			try {
								
				// Determine Library
				String library = GeneralUtility.getLibrary(inBean.getEnvironment()) + ".";
					
				sqlString.append("SELECT MMITNO, MMITDS ");
				
				sqlString.append("FROM " + library + "MITMAS ");

				sqlString.append("WHERE MMCONO = " + inBean.getCompanyNumber().trim() + " ");
				sqlString.append("  AND MMITTY = '" + inBean.getIdLevel1().trim() + "' ");
				if (!inBean.getIdLevel2().trim().equals("")) //Item Responsible Profile
					sqlString.append(" AND MMRESP = '" + inBean.getIdLevel2().trim() +"' ");
				if (!inBean.getIdLevel3().trim().equals("")) //Item Status
					sqlString.append(" AND MMSTAT = '" + inBean.getIdLevel3().trim() +"' ");
				
				sqlString.append("ORDER BY MMITNO ");
				
				//	*********************************************************************************
			} catch (Exception e) {
					throwError.append(" Error building getListItemsByType. " + e);
			}
				
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceItem.");
				throwError.append("BuildSQL.getListItemsByType(CommonRequestBean)");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}
		
	}

	/**
	 * Load Packaging Data
	 * @param ResultSet
	 * @param BeanOperationsReporting
	 * @return BeanOperationsReporting
	 */
	private static class LoadFields {
		/**
		 */
		private static DropDownSingle loadDropDownSingleItemsByType(ResultSet rs,
																	DropDownSingle dds)
		throws Exception {
			
			StringBuffer throwError      = new StringBuffer();
			try {
				dds.setDescription(rs.getString("MMITDS").trim());
				dds.setValue(rs.getString("MMITNO").trim());
				
			} catch (Exception e) {	
			    throwError.append("Error at com.treetop.services.ServiceItem.");
				throwError.append("loadDropDownSingleItemsByType(ResultSet, DropDownSingle. " + e);
				throw new Exception(throwError.toString());
			}
			return dds;
		}
	}


	/**
	 * Load class fields from result set.
	 */
	
	public static Item loadFieldsItem(String requestType,
							          ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Item returnItem = new Item();
	
		try{ 
			if (requestType.equals("loadForCOA"))
			{
				try
				{
//					 Item Master file -- MITMAS -- 
					 returnItem.setItemNumber(rs.getString("MMITNO").trim());
					 returnItem.setItemDescription(rs.getString("MMITDS").trim());
					 returnItem.setItemType(rs.getString("MMITTY").trim());
					 returnItem.setItemGroup(rs.getString("MMITGR").trim());
					 returnItem.setProductGroup(rs.getString("MMITCL").trim());
					 returnItem.setAttributeModel(rs.getString("MMATMO").trim());
					 returnItem.setBasicUnitOfMeasure(rs.getString("MMUNMS").trim());
					 returnItem.setStatus(rs.getString("MMSTAT").trim());
					
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
					
				}
				try
				{
//					 Use the Description from the Sales Line Item File - OOLINE
					returnItem.setItemDescription(rs.getString("OBITDS").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}
				try
				{
//					 Use the Description from the Distribution Order Line Item File - MGLINE
					returnItem.setItemDescription(rs.getString("MRITDS").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
				}		
			}
			if (requestType.equals("loadItem"))
			{
				try
				{
//					 Item Master file -- MITMAS -- 
					 returnItem.setItemNumber(rs.getString("MMITNO").trim());
					 returnItem.setItemDescription(rs.getString("MMITDS").trim());
					 returnItem.setItemLongDescription(rs.getString("MMFUDS").trim());
					 returnItem.setItemType(rs.getString("MMITTY").trim());
					 returnItem.setItemGroup(rs.getString("MMITGR").trim());
					 returnItem.setProductGroup(rs.getString("MMITCL").trim());
					 returnItem.setAttributeModel(rs.getString("MMATMO").trim());
					 returnItem.setBasicUnitOfMeasure(rs.getString("MMUNMS").trim());
					 returnItem.setResponsible(rs.getString("MMRESP").trim());
					 returnItem.setStatus(rs.getString("MMSTAT").trim());
					 returnItem.setOrganicConventional(rs.getString("MMEVGR").trim());
					 returnItem.setMakeBuyCode(rs.getString("MMMABU").trim());
					 returnItem.setPurchasePrice(rs.getString("MMPUPR").trim());
				}
				catch(Exception e)
				{
					// 
					//System.out.println(e);
				}
				try
				{
//					 Item Master file -- MSPWITRS- 
					returnItem.setFunctionGroup(rs.getString("MSWTYP").trim());
					returnItem.setNewItemReplacedItem(rs.getString("MSWDSC").trim());
					returnItem.setNewItemSampleOrderDesc(rs.getString("MSWDOE").trim());
					returnItem.setNewItemInitiatedDate(rs.getString("MSWDDT").trim());
					returnItem.setNewItemLastUpdateDate(rs.getString("MSWDTE").trim());
					returnItem.setNewItemLastUpdateTime(rs.getString("MSWTME").trim());
					returnItem.setNewItemLastUpdateUser(rs.getString("MSWUSR").trim());
					returnItem.setNewItemCaseUPC(rs.getString("MSWCSE").trim());
					returnItem.setNewItemLabelUPC(rs.getString("MSWITM").trim());
					returnItem.setNewItemCarrierUPC(rs.getString("MSWCAR").trim());
					returnItem.setNewItemWrapUPC(rs.getString("MSWWRP").trim());
					returnItem.setNewItemPalletGTIN(rs.getString("MSWPGT").trim());
					returnItem.setNewItemManufacturer(rs.getString("MSWMCO").trim());
					returnItem.setNewItemSalesPerson(rs.getString("MSWSLS").trim());
					returnItem.setNewItemCustServiceTeam(rs.getString("MSWCSTEAM").trim());
					returnItem.setNewItemLength(rs.getString("MSWDTH").trim());
					returnItem.setNewItemWidth(rs.getString("MSWWTH").trim());
					returnItem.setNewItemHeight(rs.getString("MSWGWG").trim());
					returnItem.setPause(rs.getString("MSWPAU").trim());
				}
				catch(Exception e)
				{
					// 
				}
				try
				{
//					 Code File -- CSYTAB- 
					 returnItem.setItemGroupDescription(rs.getString("CTTX40").trim());
				}
				catch(Exception e)
				{
					// 
				}
			}
			if (requestType.equals("loadItemAlias"))
			{
				try
				{
					if (rs.getString("MPALWT").trim().equals("1"))
						returnItem.setM3ItemAliasPopular(rs.getString("MPPOPN").trim());
					if (rs.getString("MPALWT").trim().equals("2"))
					{
						if (rs.getString("MPALWQ").trim().equals("UPC"))
							returnItem.setM3ItemAliasCaseUPC(rs.getString("MPPOPN").trim());
						if (rs.getString("MPALWQ").trim().equals("GTIN"))
							returnItem.setM3ItemAliasPalletGTIN(rs.getString("MPPOPN").trim());
					}
					if (rs.getString("MPALWT").trim().equals("3"))
					{	
						if (rs.getString("MPALWQ").trim().equals("LBL"))
						   returnItem.setM3ItemAliasLabelUPC(rs.getString("MPPOPN").trim());
						if (rs.getString("MPALWQ").trim().equals("OPN"))
						   returnItem.setM3ItemAliasOpenOrders("Y");
						if (rs.getString("MPALWQ").trim().equals("FPK"))
						   returnItem.setM3ItemAliasFreshPack("Y");
						if (rs.getString("MPALWQ").trim().equals("JCE"))
						   returnItem.setM3ItemAlias100PercentJuice("Y");
						if (rs.getString("MPALWQ").trim().equals("PLN"))
						{
						   returnItem.setM3ItemAliasPlanner(rs.getString("MPPOPN").trim());
						   if (rs.getString("ADCPT80") != null)
						     returnItem.setM3ItemAliasPlannerDesc(rs.getString("ADCPT80").trim());
						}
						if (rs.getString("MPALWQ").trim().equals("CAR"))
						   returnItem.setM3ItemAliasCAR(rs.getString("MPPOPN").trim());
						//  12/20/12 --TW  Added RPT1, SS and CCI 
						if (rs.getString("MPALWQ").trim().equals("RPT1"))
						{
						   returnItem.setM3ItemAliasReport1(rs.getString("MPPOPN").trim());
						   if (rs.getString("BDCPT80") != null)
						     returnItem.setM3ItemAliasReport1Desc(rs.getString("BDCPT80").trim());
						}
						if (rs.getString("MPALWQ").trim().equals("SS")) // Single Strength
							returnItem.setM3ItemAliasSingleStrength("Y");
						if (rs.getString("MPALWQ").trim().equals("CCI")) // Club Item
							returnItem.setM3ItemAliasClubCustItem(rs.getString("MPPOPN").trim());
						if (rs.getString("MPALWQ").trim().equals("ALRG")) // Allergen
							returnItem.setM3ItemAliasAllergen("Y");
					}
				}
				catch(Exception e)
				{
					//System.out.println(e);
				}
			}
			if (requestType.equals("loadMO"))
			{
				returnItem.setItemNumber(rs.getString("VHITNO").trim());
				returnItem.setItemDescription(rs.getString("MMITDS").trim());
				returnItem.setBasicUnitOfMeasure(rs.getString("MMUNMS").trim());
				returnItem.setProductOwner(rs.getString("MMACRF").trim());
				returnItem.setProductOwnerDescription(rs.getString("CTTX40").trim());
				if (rs.getString("MUCOFA") != null)
				  returnItem.setCasesPerPallet(rs.getString("MUCOFA"));
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Item class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceItem.");
			throwError.append("loadFieldsItem(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnItem;
	}

	/**
	 * Use to validate an Item, return message if item is not found.
	 * 
	 * 1/26/12 TWalton - added code to return an ERROR if User is not the Responsible person
	 *    for that specific Item.
	 */
	
	public static String verifyItem(CommonRequestBean requestBean)
			throws Exception
	{
		StringBuffer throwError  = new StringBuffer();
		StringBuffer rtnProblem  = new StringBuffer();
		Connection conn          = null; // New Lawson Box - Lawson Database
		Statement findIt 		 = null;
		ResultSet rs             = null;
		
		try { 
			
			String requestType = "verifyItemIdLevel";
			String sqlString = "";
			
			// verify base class initialization.
			ServiceItem a = new ServiceItem();
						
			if (requestBean.getEnvironment().trim() == null || requestBean.getEnvironment().trim().equals(""))
				requestBean.setEnvironment("PRD");
			// verify incoming Item
			if (requestBean.getIdLevel1().trim() == null || requestBean.getIdLevel1().trim().equals(""))
				rtnProblem.append(" Item number must not be null or empty.");
			
			// get Item Info.
			if (throwError.toString().equals(""))
			{
				try {
					
					Vector<CommonRequestBean> parmClass = new Vector<CommonRequestBean>();
					parmClass.addElement(requestBean);
					sqlString = buildSqlStatement(requestBean.getEnvironment().trim(), requestType, parmClass);
					
				} catch (Exception e) {
					
					throwError.append(" Error trying to build sqlString. ");
					rtnProblem.append(" Problem when building verify for item: ");
					rtnProblem.append(requestBean.getIdLevel1().trim());
					rtnProblem.append(" PrintScreen this message and send to Information Services. ");
				}
			}
			
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				
				try {
					// 1/25/12 TWalton - Change to use ServiceConnection
					//conn = com.treetop.utilities.ConnectionStack5.getConnection();
					conn = ServiceConnection.getConnectionStack12();
					
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
					
					if (rs.next())
					{
						if (!requestBean.getUser().trim().equals("") &&
							!requestBean.getUser().trim().equals(rs.getString("MMRESP").trim()))
						{
							rtnProblem.append(" This Item has not been Built in TreeNet. ");
							rtnProblem.append(" The Item Responsible is the only person allowed to Built in TreeNet. ");
							rtnProblem.append(" Contact " + rs.getString("MMRESP").trim() + " to start the process for item ");
							rtnProblem.append(requestBean.getIdLevel1().trim());
						}
					} else {
						rtnProblem.append(" Item number '" + requestBean.getIdLevel1().trim() + "' ");
						rtnProblem.append("does not exist. ");
					}
					
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding item number: ");
					rtnProblem.append(requestBean.getIdLevel1().trim());
					rtnProblem.append(" PrintScreen this message and send to Information Services. ");
				}
			}
			
		} catch(Exception e)
		{
			throwError.append(" Problem loading the item number class");
			throwError.append(" from the result set. " + e) ;
			
		// return connection.
		} finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack12(conn);
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
			throwError.append("ServiceItem.");
			throwError.append("verifyItem(CommonRequestBean)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

	/**
	 * Build an sql statement.
	 * @param String request type
	 * @param Vector request class
	 * @return sql string
	 * @throws Exception
	 */
	
	private static String buildSqlStatement(String environment,
											String inRequestType,
									 	    Vector requestClass)
		throws Exception 
	{
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();
		
		try { // Verify Item
			
			if (inRequestType.equals("verifyItem"))
			{
				// cast the incoming parameter class.
				String itemNumber = (String) requestClass.elementAt(0);
								
				// build the sql statement.
				sqlString.append("SELECT  MMITNO  ");
				sqlString.append("FROM " + library + ".MITMAS ");
				sqlString.append(" WHERE MMCONO = '100' AND MMITNO = '" + itemNumber.trim() + "' ");
			}
			if (inRequestType.equals("verifyItemIdLevel"))
			{
				// cast the incoming parameter class.
				CommonRequestBean commonRequest = (CommonRequestBean) requestClass.elementAt(0);
				String libraryM3  = GeneralUtility.getLibrary(commonRequest.getEnvironment().trim());
				
				// build the sql statement.
				sqlString.append("SELECT  MMITNO, MMRESP  ");
				sqlString.append("FROM " + libraryM3 + ".MITMAS ");
				sqlString.append("WHERE MMCONO = " + commonRequest.getCompanyNumber().trim() + " ");
				sqlString.append("AND MMITNO = '" + commonRequest.getIdLevel1().trim() + "' ");
			}
			if (inRequestType.equals("verifyNewItem"))
			{
				// cast the incoming parameter class.
				String itemNumber = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT  MSWRSC  ");
				sqlString.append("FROM " + ttlib + ".MSPWITRS ");
				sqlString.append(" WHERE MSWRSC = '" + itemNumber + "' ");
			}			
			
		// Get Alternate Unit of Measure Information
			if (inRequestType.equals("getAltUOM"))
			{
				// cast the incoming parameter class.
				String itemNumber = (String) requestClass.elementAt(0);
				String altUOM     = (String) requestClass.elementAt(1);
				// build the sql statement.
				sqlString.append("SELECT  MUALUN, MUDCCD, MUCOFA, MUDMCF  ");
				sqlString.append("FROM " + library + ".MITAUN ");
				sqlString.append(" WHERE MUCONO = '100' ");
				sqlString.append("   AND MUITNO = '" + itemNumber + "' ");
				sqlString.append("   AND MUALUN = '" + altUOM + "' ");
				sqlString.append("   AND MUAUTP = 1 ");
			}						
			// Get Basic Item Master Information
			if (inRequestType.equals("findItem"))
			{
				// cast the incoming parameter class.
				String itemNumber = (String) requestClass.elementAt(0);
				// build the sql statement.
				// MITMAS
				sqlString.append("SELECT  MMITNO, MMITDS, MMITTY, MMITGR, MMMABU, ");
				sqlString.append("MMITCL, MMATMO, MMUNMS, MMFUDS, MMRESP, MMSTAT, ");
				sqlString.append("MMEVGR, MMPUPR, ");
				// MSPWITRS
				sqlString.append("MSWTYP, MSWDSC, MSWDOE, MSWDDT, MSWDTE, MSWTME, ");
				sqlString.append("MSWUSR, MSWCSE, MSWITM, MSWCAR, MSWWRP, MSWPGT, ");
				sqlString.append("MSWMCO, MSWSLS, MSWCSTEAM, MSWDTH, MSWWTH, MSWGWG, MSWPAU, ");
				// CSYTAB -- get Item Group Description
				sqlString.append("CTTX40 ");
				sqlString.append("FROM " + library + ".MITMAS ");
				
				sqlString.append(" LEFT OUTER JOIN " + ttlib + ".MSPWITRS ");
				sqlString.append("      ON MMITNO = MSWRSC ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".CSYTAB ");
				sqlString.append("      ON MMCONO = CTCONO AND CTDIVI = '' AND CTSTCO = 'ITGR' ");
				sqlString.append("      AND CTSTKY = MMITGR ");
				sqlString.append(" WHERE MMCONO = '100' AND MMITNO = '" + itemNumber + "' ");
			}
//			 Get Item alias  Information - GTIN, and UPC Code information
			if (inRequestType.equals("findItemAlias"))
			{ // 9/20/12 TW -- Added RPT1 Alias
				// cast the incoming parameter class.
				String itemNumber = (String) requestClass.elementAt(0);
				// build the sql statement.
				// MITPOP
				sqlString.append("SELECT  MPITNO, MPALWT, MPALWQ, MPPOPN, a.DCPT80 as ADCPT80, b.DCPT80 as BDCPT80 ");
				sqlString.append("FROM " + library + ".MITPOP ");
				sqlString.append("LEFT OUTER JOIN " + ttlib + ".DCPALL a ON a.DCPK00 = 'PLN' ");
				sqlString.append("   AND a.DCPK01 = MPPOPN ");
				sqlString.append("LEFT OUTER JOIN " + ttlib + ".DCPALL b ON b.DCPK00 = 'RPT1' ");
				sqlString.append("   AND b.DCPK01 = MPPOPN ");
				sqlString.append(" WHERE MPCONO = '100' AND MPITNO = '" + itemNumber + "' ");
				sqlString.append("ORDER BY MPALWT, MPALWQ, MPPOPN ");
			}
			if (inRequestType.equals("itemFromGtin"))
			{
				// cast the incoming parameter class.
				String gtinNbr = (String) requestClass.elementAt(0);
				// build the sql statement.
				// MITMAS
				sqlString.append("SELECT  MMITNO, MMITDS, MMITTY, MMITGR, MMMABU, ");
				sqlString.append("MMITCL, MMATMO, MMUNMS, MMFUDS, MMRESP, MMSTAT, ");
				// MSPWITRS
				sqlString.append("MSWTYP, MSWDSC, MSWDOE, MSWDDT, MSWDTE, MSWTME, ");
				sqlString.append("MSWUSR, MSWCSE, MSWITM, MSWCAR, MSWWRP, MSWPGT, ");
				sqlString.append("MSWMCO, MSWSLS, MSWCSTEAM, MSWDTH, MSWWTH, MSWGWG, ");
				sqlString.append("FROM " + ttlib + ".MSPRUCCN ");
				sqlString.append(" LEFT OUTER JOIN " + ttlib + ".MSPWITRS ");
				sqlString.append("      ON MSRGTN = MSWPGT ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".MITMAS ");
				sqlString.append("      ON MMCONO = '100' AND MSWRSC = MMITNO ");
				// simple where clause.
				sqlString.append("WHERE MSRGTN = '" + gtinNbr + "' ");
			}
			if (inRequestType.equals("listVariance0") ||
				inRequestType.equals("listVariance1") ||
				inRequestType.equals("listVariance2"))
			{
				InqItem ii = (InqItem) requestClass.elementAt(0);
				// SPPMVARI - Item Variance File
				sqlString.append("SELECT SPMDETAIL, SPMISSUE, SPMEXPIRE, SPMCOMENT, ");
				sqlString.append("SPMALUP, SPMRECST, SPMDATE, SPMTIME, SPMUSER, ");
				// MITMAS - Item Master
				sqlString.append("MMITNO, MMITDS ");
				sqlString.append("FROM " + ttlib + ".SPPMVARI ");
				sqlString.append(" INNER JOIN " + library + ".MITMAS ");
				sqlString.append("    ON MMCONO = '100' AND MMITNO = SPMRESNO ");
				// WHERE SECTION
				sqlString.append("WHERE SPMRESNO = '" + ii.getInqItem() + "' ");
				if (!ii.getAllowUpdate().equals("Y") ||
					ii.getShowPending().equals(""))
					sqlString.append("AND SPMRECST = 'ACTIVE' ");
				else
					sqlString.append("AND SPMRECST <> 'DELETE' ");
				// Get Today's Date
				DateTime dt = UtilityDateTime.getSystemDate();
				if (inRequestType.equals("listVariance0"))
				{  // Currently Active Variances
					sqlString.append("AND " + dt.getDateFormatyyyyMMdd());
					sqlString.append(" BETWEEN SPMISSUE AND SPMEXPIRE ");
				}
				if (inRequestType.equals("listVariance1"))
				{  // Past Variances
					sqlString.append("AND SPMEXPIRE < " + dt.getDateFormatyyyyMMdd() + " ");
				}
				if (inRequestType.equals("listVariance2"))
				{  // Future Variances
					sqlString.append("AND SPMISSUE > " + dt.getDateFormatyyyyMMdd() + " ");
				}
				sqlString.append("ORDER BY SPMEXPIRE DESC, SPMISSUE DESC ");
			}
			if (inRequestType.equals("findVariance"))
			{
				UpdItemVariance uiv = (UpdItemVariance) requestClass.elementAt(0);
				// SPPMVARI - Item Variance File
				sqlString.append("SELECT SPMDETAIL, SPMISSUE, SPMEXPIRE, SPMCOMENT, ");
				sqlString.append("SPMALUP, SPMRECST, SPMDATE, SPMTIME, SPMUSER, ");
				// MITMAS - Item Master
				sqlString.append("MMITNO, MMITDS ");
				sqlString.append("FROM " + ttlib + ".SPPMVARI ");
				sqlString.append(" INNER JOIN " + library + ".MITMAS ");
				sqlString.append("    ON MMCONO = '100' AND MMITNO = SPMRESNO ");
				// WHERE SECTION
				sqlString.append("WHERE SPMRESNO = '" + uiv.getItem() + "' ");
				sqlString.append(" AND SPMISSUE = " + uiv.getDateIssued() + " ");
				sqlString.append(" AND SPMEXPIRE = " + uiv.getDateExpired() + " ");
				sqlString.append(" AND SPMDATE = " + uiv.getUpdDate() + " ");
				sqlString.append(" AND SPMTIME = " + uiv.getUpdTime() + " ");
				sqlString.append(" AND SPMUSER = '" + uiv.getUpdUser().trim() + "' ");
			}
			if (inRequestType.equals("codeDate"))
			{
				InqCodeDate icd = (InqCodeDate) requestClass.elementAt(0);
				// MITMAS - Item Master
				sqlString.append("SELECT MMITNO, MMITDS, MMITTY, ");
				// MITBAL - Item Warehouse
				sqlString.append("MBFACI, MBWHLO, MBSLDY, ");
				// MITWHL -- Warehouse Master
				sqlString.append("MWWHNM, ");
				// CFACIL -- Facility Master
				sqlString.append("CFFACN, ");
				// MAMOLI -- Attribute Model
				sqlString.append("AEATID, ");
				// MSPWITRS -- New Item File
				sqlString.append("MSWPGT ");
				sqlString.append("FROM " + library + ".MITMAS ");
				sqlString.append(" INNER JOIN " + library + ".MITBAL ");
				sqlString.append("    ON MMCONO = MBCONO AND MMITNO = MBITNO ");
				sqlString.append("   AND MBFACI = '" + icd.getInqFacility().trim() + "' ");
				sqlString.append("   AND MBWHLO = '" + icd.getInqWarehouse().trim() + "' ");
				sqlString.append(" INNER JOIN " + library + ".MITWHL ");
				sqlString.append("    ON MMCONO = MWCONO AND MBFACI = MWFACI AND MBWHLO = MWWHLO ");
				sqlString.append(" INNER JOIN " + library + ".CFACIL ");
				sqlString.append("    ON CFCONO = MMCONO AND MBFACI = CFFACI ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".MAMOLI ");
				sqlString.append("    ON AECONO = MMCONO AND MMATMO = AEATMO AND AEATID = 'BEST BY STATUS' ");
				sqlString.append(" LEFT OUTER JOIN " + ttlib + ".MSPWITRS ");
				sqlString.append("    ON MSWRSC = MMITNO ");
				// WHERE SECTION
				sqlString.append("WHERE MMITNO = '" + icd.getInqItem() + "' ");
			}
			if (inRequestType.equals("listShelfLife"))
			{
				String item = (String) requestClass.elementAt(0);
				// MITBAL - Item Warehouse
				sqlString.append("SELECT MBITNO, MBSLDY ");
				sqlString.append("FROM " + library + ".MITBAL ");
				// WHERE SECTION
				sqlString.append("WHERE MBCONO = '100' AND MBITNO = '" + item + "' ");
				sqlString.append("GROUP BY MBITNO, MBSLDY ");
				sqlString.append("ORDER BY MBITNO, MBSLDY ");
			}
	// **********************************************************************************
//			 DROP DOWN LISTS FROM ITEM MASTER INFORMATION
		if (inRequestType.equals("ddItemType"))
		{
			String type = (String) requestClass.elementAt(0);
			
//			 Always show the Item Type and the Description
			sqlString.append(" SELECT TYITTY, TYTX40 ");
			sqlString.append(" FROM " + library + ".MITTTY ");
			if (type.equals("inqCPGSpecs") ||
				type.equals("inqSpec"))
			{
				sqlString.append(" INNER JOIN " + library + ".MITMAS ");
				sqlString.append("    ON TYCONO = MMCONO AND TYITTY = MMITTY ");
				if (type.equals("inqCPGSpecs"))
				{
					sqlString.append(" INNER JOIN " + ttlib + ".SPPPHEAD ");
					sqlString.append("    ON MMITNO = SPPRESNO ");
				}else{
					sqlString.append(" INNER JOIN " + ttlib + ".QAPGSPHD ");
					sqlString.append("    ON TYCONO = SHCONO AND MMITNO = SHITNO ");
				}
			}
			sqlString.append(" WHERE TYCONO = '100' ");
			sqlString.append(" GROUP BY TYITTY, TYTX40 ");
			sqlString.append(" ORDER BY TYITTY ");
		}
		
		if (inRequestType.equals("ddItemInqIngSpec"))
		{
			String type = (String) requestClass.elementAt(0);
			
			//Always show the Item Type and the Description
			sqlString.append("SELECT IDTRSC, MMITDS ");
			sqlString.append("FROM DBPRD.IDPTSPHD ");
			if (type.equals("inqINGSpecs"))
			{
				sqlString.append("INNER JOIN " + library + ".MITMAS ");
				sqlString.append("ON MMCONO = '100' AND IDTRSC = MMITNO ");
			}
			sqlString.append(" GROUP BY IDTRSC, MMITDS ");
			sqlString.append(" ORDER BY IDTRSC ");
		}
		
		if (inRequestType.equals("ddProductGroup"))
		{
			String type = (String) requestClass.elementAt(0);
			
//			 Always show the Item Type and the Description
			sqlString.append(" SELECT CTSTKY, CTTX40 ");
			sqlString.append(" FROM " + library + ".CSYTAB ");
			if (type.equals("inqCPGSpecs") ||
				type.equals("inqSpec"))
			{
				sqlString.append(" INNER JOIN " + library + ".MITMAS ");
				sqlString.append("    ON CTCONO = MMCONO AND CTSTKY = MMITCL ");
				if (type.equals("inqCPGSpecs"))
				{
					sqlString.append(" INNER JOIN " + ttlib + ".SPPPHEAD ");
					sqlString.append("    ON MMITNO = SPPRESNO ");
				}else{
					sqlString.append(" INNER JOIN " + ttlib + ".QAPGSPHD ");
					sqlString.append("    ON CTCONO = SHCONO AND MMITNO = SHITNO ");
				}
			}
			sqlString.append(" WHERE CTCONO = 100 AND CTDIVI = '' ");
			sqlString.append("   AND CTSTCO = 'ITCL' ");
			sqlString.append(" GROUP BY CTSTKY, CTTX40 ");
			sqlString.append(" ORDER BY CTSTKY ");
		}	
		if (inRequestType.equals("ddItemGroup"))
		{
			String type = (String) requestClass.elementAt(0);
			
//			 Always show the Item Type and the Description
			sqlString.append(" SELECT CTSTKY, CTTX40 ");
			sqlString.append(" FROM " + library + ".CSYTAB ");
			if (type.equals("inqSpec"))
			{
				sqlString.append(" INNER JOIN " + library + ".MITMAS ");
				sqlString.append("    ON CTCONO = MMCONO AND CTSTKY = MMITCL ");
				sqlString.append(" INNER JOIN " + ttlib + ".QAPGSPHD ");
				sqlString.append("    ON CTCONO = SHCONO AND MMITNO = SHITNO ");
			}
			if (type.equals("rawFruit"))
			{
				sqlString.append(" INNER JOIN " + library + ".MITMAS ");
				sqlString.append("    ON CTCONO = MMCONO AND CTSTKY = MMITGR ");
				sqlString.append("   AND CTSTKY LIKE 'RF-%' ");
			}
			sqlString.append(" WHERE CTCONO = 100 AND CTDIVI = '' ");
			sqlString.append("   AND CTSTCO = 'ITGR' ");
			sqlString.append(" GROUP BY CTSTKY, CTTX40 ");
			sqlString.append(" ORDER BY CTSTKY ");
		}	
if (inRequestType.equals("ddUser1"))
		{
			String type = (String) requestClass.elementAt(0);
//			 Always show the Item Type and the Description
			sqlString.append(" SELECT CTSTKY, CTTX40 ");
			sqlString.append(" FROM " + library + ".CSYTAB ");
			if (type.equals("inqCPGSpecs") ||
				type.equals("inqSpec"))
			{
				sqlString.append(" INNER JOIN " + library + ".MITMAS ");
				sqlString.append("    ON CTCONO = MMCONO AND CTSTKY = MMCFI1 ");
				if (type.equals("inqCPGSpecs"))
				{
					sqlString.append(" INNER JOIN " + ttlib + ".SPPPHEAD ");
					sqlString.append("    ON MMITNO = SPPRESNO ");
				}else{
					sqlString.append(" INNER JOIN " + ttlib + ".QAPGSPHD ");
					sqlString.append("    ON CTCONO = SHCONO AND MMITNO = SHITNO ");
				}
			}
			sqlString.append(" WHERE CTCONO = 100 AND CTDIVI = '' ");
			sqlString.append("   AND CTSTCO = 'CFI1' ");
			sqlString.append(" GROUP BY CTSTKY, CTTX40 ");
			sqlString.append(" ORDER BY CTSTKY ");
		}	
		if (inRequestType.equals("rfGroups"))
		{
			String type = (String) requestClass.elementAt(0);
			
//			 Always show the Item Type and the Description
			sqlString.append(" SELECT CTSTKY, CTTX40 ");
			sqlString.append(" FROM " + library + ".CSYTAB ");
			sqlString.append(" WHERE CTCONO = 100 AND CTDIVI = '' ");
			sqlString.append("   AND CTSTCO = 'ITGR' ");
			sqlString.append("   AND SUBSTRING(CTSTKY, 1,2) = 'RF' ");
			sqlString.append(" GROUP BY CTSTKY, CTTX40 ");
			sqlString.append(" ORDER BY CTSTKY ");
		}	
		if (inRequestType.equals("rfGroup"))
		{
			String type = (String) requestClass.elementAt(0);
			
//			 Always show the Item Type and the Description
			sqlString.append(" SELECT CTSTKY, CTTX40 ");
			sqlString.append(" FROM " + library + ".CSYTAB ");
			sqlString.append(" WHERE CTCONO = 100 AND CTDIVI = '' ");
			sqlString.append("   AND CTSTCO = 'EVGR' ");
			sqlString.append("   AND CTSTKY IN ('CN', 'OR', 'BF') ");
			sqlString.append(" GROUP BY CTSTKY, CTTX40 ");
			sqlString.append(" ORDER BY CTSTKY ");
		}	
		
		
		if (inRequestType.equals("findNextNumberforMFG"))
		{
			String mfg = (String) requestClass.elementAt(0);
			sqlString.append(" SELECT MSWCSE ");
			sqlString.append(" FROM " + ttlib + ".MSPWITRS ");
			sqlString.append(" WHERE MSWMCO = '" + mfg.trim() + "' ");
			sqlString.append(" ORDER BY MSWCSE DESC ");
		}
		
		
	// **********************************************************************************
	//  UPDATE
		if (inRequestType.equals("updateNewItem"))
		{
			
			 //1/26/12 TWalton - change to use Prepared Statement
			//   		Also added options for Height, Length and width
			sqlString.append("UPDATE  " + ttlib + ".MSPWITRS ");
			sqlString.append("SET ");
				// Only update a limited number of fields
			sqlString.append(" MSWDSC = ?,"); // Replaced Which Item
			sqlString.append(" MSWDOE = ?,"); // Sample Order Description
			sqlString.append(" MSWDDT = ?,"); // Initiated Date
			sqlString.append(" MSWDTE = ?,"); // Date
			sqlString.append(" MSWTME = ?,"); // Time
			sqlString.append(" MSWUSR = ?,"); // User
			sqlString.append(" MSWCSE = ?,"); // Case UPC
			sqlString.append(" MSWITM = ?,"); // Label UPC
			sqlString.append(" MSWCAR = ?,"); // Carrier UPC
			sqlString.append(" MSWWRP = ?,"); // Wrap UPC
			sqlString.append(" MSWPGT = ?,"); // Pallet GTIN
			sqlString.append(" MSWMCO = ?,"); // Manufacturer
			sqlString.append(" MSWSLS = ?,"); // Sales Person
			sqlString.append(" MSWCSTEAM = ?,"); // Customer Service Team
			sqlString.append(" MSWDTH = ?,"); // Length
			sqlString.append(" MSWWTH = ?,"); // Width
			sqlString.append(" MSWGWG = ?,"); // Height
			sqlString.append(" MSWPAU = ?"); // Pause email
			sqlString.append(" WHERE MSWRSC = ? ");
				
		}
		
		
		
		if (inRequestType.equals("updatePauseField"))
		{
			String ttiLibrary = GeneralUtility.getTTLibrary(environment);
			UpdItem ui = (UpdItem) requestClass.elementAt(0);
			DateTime dt = UtilityDateTime.getSystemDate();
			
			sqlString.append("UPDATE  " + ttlib + ".MSPWITRS ");
			sqlString.append("SET MSWPAU = '" + ui.getPause().trim() + "' ");
			sqlString.append(", MSWDTE = " + dt.getDateFormatyyyyMMdd() + " ");
			sqlString.append(", MSWUSR = '" + ui.getUpdateUser() + "' ");
			sqlString.append(" WHERE MSWRSC = '" + ui.getItem().trim() + "' ");
		}
		
		
		
			if (inRequestType.equals("pendVariance"))
			{
				// cast the incoming parameter class.
				UpdItemVariance fromVb = (UpdItemVariance) requestClass.elementAt(0);
//				 get current system date and time.
				DateTime dt = (DateTime) requestClass.elementAt(1);
				// build the sql statement.
				sqlString.append("UPDATE " + ttlib + ".SPPMVARI ");
				sqlString.append(" SET SPMRECST = 'PENDING', ");
				sqlString.append(" SPMPROG = 'TreeNet App', ");
				sqlString.append(" SPMDATE = " + dt.getDateFormatyyyyMMdd() + ", "); // Date
				sqlString.append(" SPMTIME = " + dt.getTimeFormathhmmss() + ", "); // Time
				sqlString.append(" SPMUSER = '" + fromVb.getUpdateUser().trim() + "'"); // User
				sqlString.append(" WHERE SPMRESNO = '" + fromVb.getItem().trim() + "' "); // Item
				sqlString.append(" AND SPMISSUE = " + fromVb.getDateIssued() + " "); // Issue Date
				sqlString.append(" AND SPMEXPIRE = " + fromVb.getDateExpired() + " "); // Expire Date
				sqlString.append(" AND SPMDATE = " + fromVb.getUpdDate() + " "); // Date Updated Last
				sqlString.append(" AND SPMTIME = " + fromVb.getUpdTime() + " "); // Time Updated Last
				sqlString.append(" AND SPMUSER = '" + fromVb.getUpdUser() + "' "); // User Updated Last
			}			
			if (inRequestType.equals("updVariance"))
			{
				// cast the incoming parameter class.
				UpdItemVariance fromVb = (UpdItemVariance) requestClass.elementAt(0);
//				 get current system date and time.
				DateTime dt = (DateTime) requestClass.elementAt(1);
				// build the sql statement.
				sqlString.append("UPDATE " + ttlib + ".SPPMVARI ");
				sqlString.append(" SET SPMDETAIL = '" + fromVb.getVarianceText() + "', "); // Variance Text
				sqlString.append(" SPMISSUE = " + fromVb.getDateIssued() + ", "); // Issue Date
				sqlString.append(" SPMEXPIRE = " + fromVb.getDateExpired() + ", "); // Expire Date
				sqlString.append(" SPMCOMENT = '" + fromVb.getVarianceComment() + "', "); // Comment
				sqlString.append(" SPMRECST = '" + fromVb.getRecordStatus() + "', ");
				sqlString.append(" SPMPROG = 'TreeNet App', ");
				sqlString.append(" SPMDATE = " + dt.getDateFormatyyyyMMdd() + ","); // Date
				sqlString.append(" SPMTIME = " + dt.getTimeFormathhmmss() + ", "); // Time
				sqlString.append(" SPMUSER = '" + fromVb.getUpdateUser().trim() + "'"); // User
				sqlString.append(" WHERE SPMRESNO = '" + fromVb.getItem().trim() + "' "); // Item
				sqlString.append(" AND SPMISSUE = " + fromVb.getOriginalDateIssued() + " "); // Issue Date
				sqlString.append(" AND SPMEXPIRE = " + fromVb.getOriginalDateExpired() + " "); // Expire Date
				sqlString.append(" AND SPMDATE = " + fromVb.getUpdDate() + " "); // Date Updated Last
				sqlString.append(" AND SPMTIME = " + fromVb.getUpdTime() + " "); // Time Updated Last
				sqlString.append(" AND SPMUSER = '" + fromVb.getUpdUser() + "' "); // User Updated Last
			}		
			
			if (inRequestType.equals("listProductStructureMaterials"))
			{
				// cast the incoming parameter class.
				String itemNumber = (String) requestClass.elementAt(0);
				String date = (String) requestClass.elementAt(1);
								
				// build the sql statement.
				sqlString.append("SELECT phfaci, phstrt, phstat, phresp, phlmdt, phprno, ");
				sqlString.append("pr.mmitno as pritno, pr.mmitds as pritds, pr.mmfuds as prfuds, pr.mmitty as pritty, pr.mmitgr as pritgr, pr.mmitcl as pritcl, ");
				sqlString.append("pr.mmunms as prunms, pr.mmresp as prresp, pr.mmstat as prstat, pr.mmacrf as pracrf, pr.mmevgr as prevgr, mt.mmmabu as prmabu, ");
				sqlString.append("pmmseq, pmopno, pmfdat, pmtdat, pmcnqt, pmwapc, mt.mmwapc as mtwapc, pmpeun, pmbypr, pmlmdt, pmchid, ");
				sqlString.append("mt.mmitno as mtitno, mt.mmitds as mtitds, mt.mmfuds as mtfuds, mt.mmitty as mtitty, mt.mmitgr as mtitgr, mt.mmitcl as mtitcl, ");
				sqlString.append("mt.mmunms as mtunms, mt.mmresp as mtresp, mt.mmstat as mtstat, mt.mmacrf as mtacrf, mt.mmevgr as mtevgr, mt.mmmabu as mtmabu, ");
				sqlString.append("cfwhlo "); // 12/20/11 TW - Added Main Warehouse

				sqlString.append("FROM m3djd" + environment + ".mpdhed ");
				sqlString.append("INNER JOIN m3djd" + environment + ".mpdmat on phcono=pmcono and phfaci=pmfaci and phprno=pmprno ");
				sqlString.append("INNER JOIN m3djd" + environment + ".mitmas as pr on phcono=pr.mmcono and phprno=pr.mmitno ");
				sqlString.append("INNER JOIN m3djd" + environment + ".mitmas as mt on phcono=mt.mmcono and pmmtno=mt.mmitno ");
				sqlString.append("INNER JOIN m3djd" + environment + ".cfacil on phcono = cfcono and phfaci = cffaci "); // 12/20/11 TW - Added Main Warehouse

				sqlString.append("WHERE phcono=100 and phprno='" + itemNumber + "' and pmfdat<=" + date + " and pmtdat>=" + date + " "); // tw 6/25/12 - adjusted code to be based on date not 99999999
				// adjusted to be larger date than today.... 
				//sqlString.append("  AND PMTDAT = 99999999 AND PHSTAT = '20' "); // tw 6/25/12 - adjusted code to be based on date not 99999999
				sqlString.append("  AND PHSTAT = '20' "); // tw 11/8/11 - adjusted code
				sqlString.append("ORDER BY phprno, phfaci, pmmseq");

			}
			
// **********************************************************************************
//  INSERT
			if (inRequestType.equals("insertNewItem"))
			{
				//1/26/12 TWalton Change the way the Insert is done
				
				// 10/4/12 TWalton - clean up *remove* old code, adjust the fields to reflect what is actually used
				
				sqlString.append("INSERT  INTO  " + ttlib + ".MSPWITRS ");
				sqlString.append("(MSWCONO, MSWRSC, MSWTYP, MSWDSC, MSWDOE,");
				sqlString.append("  MSWDDT, MSWDTE, MSWTME, MSWUSR, MSWCSE,");
				sqlString.append("  MSWITM, MSWCAR, MSWWRP, MSWPGT, MSWMCO,");
				sqlString.append("  MSWSLS, MSWCSTEAM,");
				sqlString.append("  MSWDTH, MSWWTH, MSWGWG, MSWPAU ) ");
				
				sqlString.append(" VALUES(?,?,?,?,?, ?,?,?,?,?,");
				sqlString.append("?,?,?,?,?, ?,?,?,?,?,?)");
			
			}
			if (inRequestType.equals("addVariance"))
			{
				// cast the incoming parameter class.
				UpdItemVariance fromVb = (UpdItemVariance) requestClass.elementAt(0);
				// get current system date and time.
				DateTime dt = (DateTime) requestClass.elementAt(1);
				// build the sql statement.
				sqlString.append("INSERT INTO " + ttlib + ".SPPMVARI ");
				sqlString.append(" VALUES( ");
				sqlString.append("'" + fromVb.getItem().trim() + "',"); // Item
				sqlString.append("'UPDATE VARIANCE INFORMATION',"); 
				sqlString.append(dt.getDateFormatyyyyMMdd() + ","); // Issue Date
				sqlString.append(dt.getDateFormatyyyyMMdd() + ","); // Expire Date
				sqlString.append("' ',"); // Comments
				sqlString.append("'YES',"); // Allow Update
				sqlString.append("'PENDING',"); // Record Status
				sqlString.append("'TreeNet App', "); // Update Program
				sqlString.append(dt.getDateFormatyyyyMMdd() + ","); // Date
				sqlString.append(dt.getTimeFormathhmmss() + ","); // Time
				sqlString.append("'" + fromVb.getUpdateUser().trim() + "',"); // User
				sqlString.append("'TreeNet' "); // Workstation
				sqlString.append(") ");
			}
// **********************************************************************************
//  DELETE
			if (inRequestType.equals("deleteVariance"))
			{
				// cast the incoming parameter class.
				UpdItemVariance fromVb = (UpdItemVariance) requestClass.elementAt(0);
				// build the sql statement.
//				 get current system date and time.
				DateTime dt = (DateTime) requestClass.elementAt(1);
				// Instead of Actually Deleting Record, update the Status Field to DELETE
				//sqlString.append("DELETE FROM " + ttlib + ".SPPMVARI ");
				sqlString.append("UPDATE " + ttlib + ".SPPMVARI ");
				sqlString.append(" SET SPMRECST = 'DELETE', ");
				sqlString.append(" SPMPROG = 'TreeNet App', ");
				sqlString.append(" SPMDATE = " + dt.getDateFormatyyyyMMdd() + ", "); // Date
				sqlString.append(" SPMTIME = " + dt.getTimeFormathhmmss() + ", "); // Time
				sqlString.append(" SPMUSER = '" + fromVb.getUpdateUser().trim() + "' "); // User
				
				sqlString.append(" WHERE SPMRESNO = '" + fromVb.getItem().trim() + "' "); // Item
				sqlString.append(" AND SPMISSUE = " + fromVb.getDateIssued() + " "); // Issue Date
				sqlString.append(" AND SPMEXPIRE = " + fromVb.getDateExpired() + " "); // Expire Date
				sqlString.append(" AND SPMDATE = " + fromVb.getUpdDate() + " "); // Date Updated Last
				sqlString.append(" AND SPMTIME = " + fromVb.getUpdTime() + " "); // Time Updated Last
				sqlString.append(" AND SPMUSER = '" + fromVb.getUpdUser() + "' "); // User Updated Last
			}			

		} catch (Exception e) {
				throwError.append(" Error building sql statement"
							 + " for request type " + inRequestType + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceItem.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		
		return sqlString.toString();
	}

	/**
	 * Send in Environment, Item Number, and the UOM you would like to see the inventory in.
	 * 	   The UOM will be directly related to the Base UOM
	 * 
	 *    Return the Quantity of the To UOM in a string
	 */
		
		public static String convertItemUOM(String environment, 
											String itemNumber,
											String uomTo,
											String quantity)
				throws Exception
		{
			// Changes to Method
			// 2/2/09 TWalton change from Connection Pool to DBConnection
			StringBuffer throwError = new StringBuffer();
			String rtnValue = new String();
			Connection conn = null; // New Lawson Box - Lawson Database
			PreparedStatement findIt = null;
			ResultSet rs = null;
			try { 
				String requestType = "getAltUOM";
				String sqlString = "";
				
				// verify base class initialization.
				ServiceItem a = new ServiceItem();
							
				if (environment == null || environment.trim().equals(""))
					environment = "PRD";
				// verify incoming Item
				if (itemNumber == null || itemNumber.trim().equals(""))
				   throwError.append(" Item Number must not be null or empty.");
				
				// get Item Info.
				if (throwError.toString().equals(""))
				{
					try {
						Vector parmClass = new Vector();
						parmClass.addElement(itemNumber);
						parmClass.addElement(uomTo);
						sqlString = buildSqlStatement(environment, requestType, parmClass);
					} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnValue = "0";
					}
				}
				
				// get a connection. execute sql, build return object.
				if (throwError.toString().equals("")) {
					try {
						conn = ServiceConnection.getConnectionStack12();
						
						findIt = conn.prepareStatement(sqlString);
						rs = findIt.executeQuery();
						rtnValue = "0";
						if (rs.next())
						{
	//						 it exists and all is good.
							try
							{
								BigDecimal qty = new BigDecimal(quantity);
								BigDecimal value = new BigDecimal(0);
								if (rs.getString("MUDMCF").equals("1"))
								{ // on the screen it says Multiply
									value = qty.divide(rs.getBigDecimal("MUCOFA"), 4, 4);  //JH 2011-03-17 Changed to allow for 4 decimal places
								}
								if (rs.getString("MUDMCF").equals("2"))
								{ // on the screen it says Divide
									value = qty.multiply(rs.getBigDecimal("MUCOFA"));
								}		
								rtnValue = HTMLHelpersMasking.maskBigDecimal(value.toString(), rs.getInt("MUDCCD"));
							}catch(Exception e)
							{}
						} 
					} catch (Exception e) {
						throwError.append(" error occured executing a sql statement. " + e);
						rtnValue = "0";
					}
				}
			} catch(Exception e)
			{
				throwError.append(" Problem Converting the UOM for the Item Number ");
				throwError.append("from the result set. " + e) ;
			// return connection.
			} finally {
				try {
					if (conn != null)
						ServiceConnection.returnConnectionStack12(conn);
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
				throwError.append("ServiceItem.");
				throwError.append("convertItemUOM(String environment: ");
				throwError.append("String item, String UOM, String Qty)");
				throw new Exception(throwError.toString());
			}
			return rtnValue;
		}

	/**
	 * Send in Environment, Item Number, 
	 *    Return an Item Class 
	 */
		
	public static BeanItem buildNewItem(String environment, 
								        String itemNumber)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanItem rtnValue = new BeanItem();
		//ConnectionStack5 cn5 = new ConnectionStack5();
		Connection conn = null; 
		try { 
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			if (itemNumber == null || itemNumber.trim().equals(""))
			   throwError.append(" Item Number must not be null or empty.");
			// 1/25/12 TWalton - Change to use ServiceConnection
			//conn = ConnectionStack5.getConnection();
			conn = ServiceConnection.getConnectionStack12();
			
			// get Item Info.
			if (throwError.toString().equals(""))
			{
				try {
					rtnValue.setItemClass(findItem(environment, itemNumber, conn));
				} catch (Exception e) {
				throwError.append(" error trying to retrieve Item Data. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Retrieving Item Number Information. " + e);
		// return connection.
		} finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack12(conn);
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
			// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceItem.");
			throwError.append("buildNewItem(String environment: ");
			throwError.append("String item)");
			throw new Exception(throwError.toString());
		}
		return rtnValue;
	}
	
	/**
	 * Returns a vector of objects, use BeanItem.getProductStructure()
	 * @author jhagle
	 * @param InqItem
	 * @return BeanItem
	 * @throws Exception
	 */
	public static BeanItem listProductStructureMaterials(InqItem inq) throws Exception {
		StringBuffer 	throwError 	= new StringBuffer();
		BeanItem 		rtnValue 	= new BeanItem();
		Connection 		conn 		= null;
		
		try {
			if (inq.getEnvironment() == null || inq.getEnvironment().trim().equals("")) {
				inq.setEnvironment("PRD");
			}
			if (inq.getInqItem() == null || inq.getInqItem().trim().equals("")) {
				throwError.append("Item cannot be blank.  ");
			}

			if (throwError.toString().trim().equals("")) {
				// 1/25/12 TWalton - Change to use ServiceConnection
				//conn = ConnectionStack5.getConnection();
				conn = ServiceConnection.getConnectionStack12();
				
				rtnValue = listProductStructureMaterials(inq, conn);
			}
			
		} catch (Exception e) {throwError.append("Error running private listProductStructureMaterials(). " + e);}
		finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack12(conn);
			} catch (Exception e) {e.printStackTrace();}
			if (!throwError.toString().equals("")) {
				throwError.append("Error @ com.treetop.services.");
				throwError.append("ServiceItem.");
				throwError.append("listProductStructureMaterials(InqItem inq)");
				throw new Exception(throwError.toString());
			}
		}
		return rtnValue;
	}
	
	/**
	 * Send in InqItem object with connection
	 *    Return an BeanItem Class 
	 */
		
	private static BeanItem listProductStructureMaterials(InqItem inq, Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanItem rtnValue = new BeanItem();
		Statement listIt = null;
		ResultSet rs = null;
		String item = inq.getInqItem();
		String env = inq.getEnvironment();

		try {

			//Get today's date
			String date = UtilityDateTime.getDateFormatM3Fiscal("").getDateFormatyyyyMMdd();
			Vector<String> inValues = new Vector<String>();
			//load in item number and date for buildSqlStatement()
			inValues.addElement(item);
			inValues.addElement(date);

			listIt = conn.createStatement();
			rs = listIt.executeQuery(buildSqlStatement(env , "listProductStructureMaterials", inValues));
			
			String faci = "";
			
			Vector<ProductStructureMaterial> material = new Vector<ProductStructureMaterial>();
			Vector<Vector> structure = new Vector<Vector>();
			
			while (rs.next()) {
				if (!faci.equals(rs.getString("phfaci").trim())) {	
					if (!faci.equals("")) {
						structure.addElement(material);
						material = new Vector<ProductStructureMaterial>();
					}
					faci = rs.getString("phfaci").trim();
				}
				material.addElement(loadFieldsProdStrtMat(rs));
				
				
				
			}
			//Add the last one to structure
			structure.addElement(material);
			
			rtnValue.setProductStructure(structure);
			
		} catch (Exception e) {
		throwError.append(" error trying to retrieve product structure material data. ");
		}
		finally {
			try {
				if (listIt != null)
					listIt.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceItem.");
			throwError.append("listProductStructureMaterials(InqItem inq, Connection conn)");
			throw new Exception(throwError.toString());
		}
		return rtnValue;
	}
	
	/**
	 * Load fields for listProductStructuerMaterials()
	 * @param rs ResultSet
	 * @return pds ProductStructureMaterial
	 */
	private static ProductStructureMaterial loadFieldsProdStrtMat(ResultSet rs) {
		StringBuffer throwError = new StringBuffer();
		ProductStructureMaterial pds  = new ProductStructureMaterial();
		Item prItem = new Item();
		Item mtItem = new Item();
		
		try {
			pds.setFacility(rs.getString("phfaci").trim());
			pds.setStructureType(rs.getString("phstrt").trim());
			pds.setStructureStatus(rs.getString("phstat").trim());
			pds.setStructureLastModifiedDate(rs.getString("phlmdt").trim());
			
			prItem.setItemNumber(rs.getString("phprno").trim());
			prItem.setItemDescription(rs.getString("pritds").trim());
			prItem.setItemLongDescription(rs.getString("prfuds").trim());
			prItem.setItemType(rs.getString("pritty").trim());
			prItem.setItemGroup(rs.getString("pritgr").trim());
			prItem.setProductGroup(rs.getString("pritcl").trim());
			prItem.setBasicUnitOfMeasure(rs.getString("prunms").trim());
			prItem.setResponsible(rs.getString("prresp").trim());
			prItem.setStatus(rs.getString("prstat").trim());
			prItem.setProductOwner(rs.getString("pracrf").trim());
			prItem.setOrganicConventional(rs.getString("prevgr").trim());
			prItem.setMakeBuyCode(rs.getString("prmabu").trim());
			
			pds.setProducedItem(prItem);
			
			pds.setMaterialSequence(rs.getString("pmmseq").trim());
			pds.setOperationNumber(rs.getString("pmopno").trim());
			pds.setToDate(rs.getString("pmtdat").trim());
			pds.setFromDate(rs.getString("pmfdat").trim());
			pds.setConsumedQuantity(rs.getString("pmcnqt"));
			
			//Compute extended consumed quantity (with waste)
			//Add waste factor from item master to additional waste on product structure
			//Divide by 100 and add 1
			//Multiply by the consumed quantity on product structure
			BigDecimal stdWaste = rs.getBigDecimal("mtwapc");
			BigDecimal pdsWaste = rs.getBigDecimal("pmwapc");
			
			BigDecimal waste = stdWaste.add(pdsWaste);
			waste = waste.divide(new BigDecimal(100),4,4);
			waste = waste.add(BigDecimal.ONE);
			
			BigDecimal cnsWithWaste = rs.getBigDecimal("pmcnqt");
			cnsWithWaste = cnsWithWaste.multiply(waste);
			
			pds.setConsumedQuantityWithWaste(cnsWithWaste.toString());
			
			pds.setConsumedUnitOfMeasure(rs.getString("pmpeun"));
			pds.setByProduct(rs.getString("pmbypr"));
			pds.setMaterialLastModifiedDate(rs.getString("pmlmdt"));
			pds.setMaterialLastModifiedUser(rs.getString("pmchid"));
			
			mtItem.setItemNumber(rs.getString("mtitno").trim());
			mtItem.setItemDescription(rs.getString("mtitds").trim());
			mtItem.setItemLongDescription(rs.getString("mtfuds").trim());
			mtItem.setItemType(rs.getString("mtitty").trim());
			mtItem.setItemGroup(rs.getString("mtitgr").trim());
			mtItem.setProductGroup(rs.getString("mtitcl").trim());
			mtItem.setBasicUnitOfMeasure(rs.getString("mtunms").trim());
			mtItem.setResponsible(rs.getString("mtresp").trim());
			mtItem.setStatus(rs.getString("mtstat").trim());
			mtItem.setProductOwner(rs.getString("mtacrf").trim());
			mtItem.setOrganicConventional(rs.getString("mtevgr").trim());
			mtItem.setMakeBuyCode(rs.getString("mtmabu").trim());
			try{
				pds.setMainWarehouse(rs.getString("cfwhlo").trim());
			}catch(Exception e){}
			
		//  Retrieve the Supplier Summary Link from the New Item Screen
			if (!mtItem.getItemNumber().trim().equals(""))
			{
				try {
					KeyValue kv = new KeyValue();
					kv.setEntryType("ItemUrl2");
					kv.setKey1(mtItem.getItemNumber().trim());
					Vector listSupplierSummary = ServiceKeyValue.buildKeyValueList(kv);
					if (!listSupplierSummary.isEmpty())
					{
						KeyValue newKV = (KeyValue) listSupplierSummary.elementAt(0);
						mtItem.setSupplierSummaryURL(newKV.getValue());
					}
				} catch(Exception e){}
			}
		} catch (Exception e) {throwError.append("Error getting data from ResultSet. " + e);}
		
		pds.setMaterialItem(mtItem);
		return pds;
	}

	/**
	 * Send in Environment, Item Number, 
	 *    Return an Item Class 
	 */
	private static Item findItem(String environment, 
								 String itemNumber,
								 Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Item rtnValue = new Item();
		PreparedStatement findIt = null;
		ResultSet rs = null;
		String sqlString = new String();
		try { 
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(itemNumber);
				sqlString = buildSqlStatement(environment, "findItem", parmClass);
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
					if (rs.next())
					{
						// need to Load the information into the Item Class
						try
						{
							rtnValue = loadFieldsItem("loadItem", rs);
							
						} catch (Exception e) {
							throwError.append(" error occured loading Fields from an sql statement. " + e);
						}
					}
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
				}
			}
			if (throwError.toString().equals(""))
			{
				try{
					rtnValue = findItemAlias(environment, rtnValue, conn);
				}catch(Exception e)
				{
					throwError.append(" error occured when returning Alias information");
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
			throwError.append("ServiceItem.");
			throwError.append("findItem(String environment: ");
			throwError.append("String item, Connection conn)");
			throw new Exception(throwError.toString());
		}
		return rtnValue;
	}

	/**
	 * Use to Update New item File:
	 *       MSPWITRS -- Detail File
	 */
	private static void updateNewItem(String environment,
								      Vector incomingParms,  // UpdItem
									  Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		// 1/26/12 TWalton - Change to use Prepared Statement senario
		PreparedStatement updateIt = null;
		try { 
			UpdItem ui = (UpdItem) incomingParms.elementAt(0);
			if (!ui.getItem().trim().equals(""))
			{
			  // Update the Record
				try {
				    String sqlString = buildSqlStatement(environment, "updateNewItem", incomingParms);
				    
				    DateTime dt = UtilityDateTime.getSystemDate();
				    
				    updateIt = conn.prepareStatement(sqlString);
					
				    updateIt.setString(1, ui.getItemReplacing().trim()); // Old item the NEW item is replacing
				    updateIt.setString(2, ui.getSampleOrderDesc().trim()); // Sample Order Description
				    updateIt.setInt(3, new Integer(ui.getDateInitiated()).intValue()); // date Item was initiated
				    updateIt.setInt(4, new Integer(dt.getDateFormatyyyyMMdd()).intValue()); // date
					updateIt.setInt(5, new Integer(dt.getTimeFormathhmmss()).intValue()); // time
					updateIt.setString(6, ui.getUpdateUser().trim()); // User
					updateIt.setString(7, ui.getCaseUPC().trim()); // Case UPC
					updateIt.setString(8, ui.getLabelUPC().trim()); // Label UPC
					updateIt.setString(9, ui.getCarrierUPC().trim()); // Carrier UPC 
					updateIt.setString(10, ui.getWrapUPC().trim()); // Wrap UPC
					updateIt.setString(11, ui.getPalletGTIN().trim()); // Pallet GTIN
					updateIt.setString(12, ui.getManufacturer().trim()); // Manufacturer
					updateIt.setString(13, ui.getSalesPerson().trim()); // Sales Person
					updateIt.setInt(14, 0); // Customer Service Team
					updateIt.setBigDecimal(15, new BigDecimal(ui.getLength())); // Length
					updateIt.setBigDecimal(16, new BigDecimal(ui.getWidth())); // Width
					updateIt.setBigDecimal(17, new BigDecimal(ui.getHeight())); // Height
					updateIt.setString(18, ui.getPause()); //Pause emails on=1
					updateIt.setString(19, ui.getItem().trim()); // Item Number (for the Where)
					
					updateIt.executeUpdate();
				    
				} catch (Exception e) {
					throwError.append(" error occured executing an sql statement to update a New Item Record. " + e);
				}
			   if (!ui.getPalletGTIN().trim().equals(""))
			   {
			   	 // If pallet GTIN is not already Created, must create Pallet GTIN BLANK
			   	 try
				 {
			   	 	String rv = UpdGTIN.testAndAddGTIN(ui.getPalletGTIN());
			   	 //	System.out.println("stop" + rv);
				 }
			   	 catch(Exception e)
				 {
			   	 	System.out.println("Problem Found: " + e);
			   	 }
			   }
			}// End of if you have an item number
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the New Item Records. " + e) ;
			// return Prepared Statement
		} finally {
			try {
				if (updateIt != null)
					updateIt.close();
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceItem.updateNewItem(");
			throwError.append("String:Environment,Vector, Connection)");
			throw new Exception(throwError.toString());
		}
		return;
	}
	
	
	
	/**
	 * Update New item Pause Field:
	 *       MSPWITRS -- Detail File
	 */
	private static void updateNewItemPause(String environment,
								           UpdItem ui,  
								           Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Statement updateIt = null;
		String sqlString = "";
		
		try {
			
			if (!ui.getItem().trim().equals(""))
			{
			
				//build sql statement
				Vector parms = new Vector();
				parms.addElement(ui);
				sqlString = buildSqlStatement(environment, "updatePauseField", parms);
				updateIt = conn.createStatement();
				updateIt.executeUpdate(sqlString);
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the New Item Pause Field. " + e) ;
			// return Prepared Statement
		} finally {
			try {
				if (updateIt != null)
					updateIt.close();
			} catch (Exception e) {}
			
		}
		
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceItem.updateNewItemPause(");
			throwError.append("String:Environment,Vector, Connection)");
			throw new Exception(throwError.toString());
		}
		
		return;
	}

/**
 * Determine what needs to be done to Update a record for the new item Process
 *      File: MSPWITRS
 */
	public static void updateNewItem(String environment, 
									 Vector incomingParms) // Send in UpdItem
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		//ConnectionStack5 cn5 = new ConnectionStack5();
		Connection conn = null; // New Lawson Box - Lawson Database
		try {
			// 1/25/12 TWalton - Change to use ServiceConnection
			//conn = ConnectionStack5.getConnection();
			conn = ServiceConnection.getConnectionStack12();
			
			// Process Anything that needs to be processed within the Process Method
			updateNewItem(environment, incomingParms, conn);
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the New Item. " + e) ;
		// return connection.
		} finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack12(conn);
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceItem.");
			throwError.append("updateNewItem(String: Environment, Vector)");
			throw new Exception(throwError.toString());
		}
		return;
	}
	
	/**
	 * Update the Pause field record for the new item Process
	 *      File: MSPWITRS
	 */
		public static void updateNewItemPause(String environment, 
										      UpdItem ui) 
				throws Exception
		{
			StringBuffer throwError = new StringBuffer();
			Connection conn = null; 
			
			try {
				conn = ServiceConnection.getConnectionStack12();
				updateNewItemPause(environment, ui, conn);
			} catch(Exception e)
			{
				throwError.append(" Problem Updating the New Item. " + e) ;
			// return connection.
			} 
			
			finally {
				try {
					if (conn != null)
						ServiceConnection.returnConnectionStack12(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
			
			// return data.
			if (!throwError.toString().equals("")) {
				throwError.append("Error @ com.treetop.services.");
				throwError.append("ServiceItem.");
				throwError.append("updateNewItemPause(String: Environment, Vector)");
				throw new Exception(throwError.toString());
			}
			
			return;
		}

/**
 * Use to Validate a Send in Item, Return Message if Item is not found
 *    Will test to see if there is a record in the New Item File MSPWITRS
 */
   public static String verifyNewItem(String environment, String itemNumber)
		  throws Exception
	{
	   // Changes to the Method
	   // 2/2/09 TWalton changed from Connection Pool to DBConnection
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		Statement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyNewItem";
			String sqlString = "";
			
			// verify base class initialization.
			ServiceItem a = new ServiceItem();
						
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Item
			if (itemNumber == null || itemNumber.trim().equals(""))
				rtnProblem.append(" Item Number must not be null or empty.");
			
			// get Item Info.
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(itemNumber);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
				rtnProblem.append(" Problem when building Test for Item: ");
				rtnProblem.append(itemNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
			
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					// 1/25/12 TWalton - Change to use ServiceConnection
					//conn = ConnectionStack5.getConnection();
					conn = ServiceConnection.getConnectionStack12();
					
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
					
					if (rs.next())
					{
//						 it exists and all is good.
					} else {
						rtnProblem.append(" Item Number '" + itemNumber + "' ");
						rtnProblem.append("does not exist, in the New Item File. ");
					}
					
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Item Number: ");
					rtnProblem.append(itemNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Item Number class ");
			throwError.append("from the result set. " + e) ;
		// return connection.
		} finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack12(conn);
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
			throwError.append("ServiceItem.");
			throwError.append("verifyItem(String: itemNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

/**
 * Return a Item business object using a
 * Gtin number.
 * @param String Gtin Number.
 * @return Item business object.
 * @throws Exception
 */

public static Item buildItemFromGtin(String environment, 
									 String gtinIn)
	throws Exception 
{
	// Changes to Method
	// 2/2/09 TWalton - Changed from Connection Stack to DBConnection
	StringBuffer throwError = new StringBuffer();
	Item rtnValue = new Item();
	Connection conn = null; // New Lawson Box - Lawson Database
	try { 
		if (environment == null || environment.trim().equals(""))
			environment = "PRD";
		if (gtinIn == null || gtinIn.trim().equals(""))
		   throwError.append(" GTIN Number must not be null or empty.");
		
		conn = ServiceConnection.getConnectionStack12();
		
		// get Item Info.
		if (throwError.toString().equals(""))
		{
			try {
				rtnValue = findItemFromGtin(environment, gtinIn, conn);
			} catch (Exception e) {
			throwError.append(" error trying to retrieve Item Data. ");
			}
		}
	} catch(Exception e)
	{
		throwError.append(" Problem Retrieving GTIN Number Information. " + e);
	// return connection.
	} finally {
		if (conn != null) {
			try {
				ServiceConnection.returnConnectionStack12(conn);
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
	}	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceItem.");
		throwError.append("buildItemFromGtin(gtin:" + gtinIn);
		throwError.append("). ");
		throw new Exception(throwError.toString());
	}

	// return value
	return rtnValue;
}

/**
 * Return a Item business object for incoming 
 * gtin number.
 * @param String gtin number
 * @return Item business object
 * @throws Exception
 */
private static Item findItemFromGtin(String environment, 
									 String inGtin, 
									 Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Item rtnValue = new Item();
	PreparedStatement findIt = null;
	ResultSet rs = null;
	String sqlString = new String();
	try { 
		try {
			Vector parmClass = new Vector();
			parmClass.addElement(inGtin);
			sqlString = buildSqlStatement(environment, "itemFromGtin", parmClass);
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
				if (rs.next())
				{
					// need to Load the information into the Item Class
					try
					{
						rtnValue = loadFieldsItem("loadItem", rs);
						
					} catch (Exception e) {
						throwError.append(" error occured loading Fields from an sql statement. " + e);
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
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceItem.");
		throwError.append("findItemFromGtin(gtin:");
		throwError.append(inGtin + ")");
		throw new Exception(throwError.toString());
	}
	
	return rtnValue;
}

/**
 *   Method Created 9/12/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the InqItem Object for use in the SQL statement
 * @return BeanItem -- Filled with Vectors of ItemVariances.
 * @throws Exception
 */
public static BeanItem listVariancesByItem(Vector inValues)
throws Exception
{
	// Changes to Method
	// 2/2/09 TWalton - Changed from ConnectionStack to DBConnection
	StringBuffer throwError = new StringBuffer();
	BeanItem returnValue = new BeanItem();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack12();
		returnValue = listVariancesByItem(inValues, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceItem.");
		throwError.append("listVariancesByItem(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 *    Method Created 9/12/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the InqIItem Object for use in the SQL statement
 * @return BeanItem -- Filled with Vectors of ItemVariances.
 * @throws Exception
 */
private static BeanItem listVariancesByItem(Vector inValues, 
								  		 	Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	BeanItem returnValue = new BeanItem();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
	  // Process SQL 3 times - one for MAIN, one for Future and one for Past	
	  for (int x = 0; x < 3; x++)
	  { 
	  	Vector rtnList = new Vector();
		try {
		   // Get the list of DataSet File Names - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", ("listVariance" + x), inValues));
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
	     					rtnList.addElement(loadFieldsItemVariance("", rs));
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
	     if (x == 0)
	     	returnValue.setItemVariance(rtnList);
	     if (x == 1)
	     	returnValue.setItemVariancePast(rtnList);
	     if (x == 2)
	     	returnValue.setItemVarianceFuture(rtnList);
	  } // END OF THE For Loop -- 3 Times Through
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
		throwError.append("ServiceItem.");
		throwError.append("listVariancesByItem(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
	 * Load class fields from result set.
	 */
	
	public static ItemVariance loadFieldsItemVariance(String requestType,
							          		  ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		ItemVariance returnItem = new ItemVariance();
		try{ 
			returnItem.setItemNumber(rs.getString("MMITNO").trim());
			returnItem.setItemDescription(rs.getString("MMITDS").trim());
			returnItem.setVarianceText(rs.getString("SPMDETAIL").trim());
			returnItem.setDateIssued(rs.getString("SPMISSUE").trim());
			returnItem.setDateExpired(rs.getString("SPMEXPIRE").trim());
			returnItem.setVarianceComment(rs.getString("SPMCOMENT").trim());
			returnItem.setAllowUpdate(rs.getString("SPMALUP").trim());
			returnItem.setRecordStatus(rs.getString("SPMRECST").trim());
			returnItem.setUpdDate(rs.getString("SPMDATE"));
			returnItem.setUpdTime(rs.getString("SPMTIME"));
			returnItem.setUpdUser(rs.getString("SPMUSER"));

		} catch(Exception e)
		{
			throwError.append(" Problem loading the Item Variance class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceItem.");
			throwError.append("loadFieldsItemVariance(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnItem;
	}

/**
 * Determine what needs to be done to Update/Add/Delete a record for Item Variances
 *      File: SPPMVARI
 *   Created 9/29/08 - Twalton
 */
	public static ItemVariance updateItemVariance(String environment, 
									      		  UpdItemVariance incomingClass)
	throws Exception
	{
		// Changes to Method
		// 2/2/09 TWalton - Changed from ConnectionStack to DBConnection
		StringBuffer throwError = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		ItemVariance returnValue = new ItemVariance();
		try { 
			conn = ServiceConnection.getConnectionStack12();
			
//			 Process Anything that needs to be processed within the Process Method
			//updateProcessNewItem(environment, incomingParms, conn);
			
			DateTime dt = UtilityDateTime.getSystemDate();
			Vector sendParms = new Vector();
			sendParms.addElement(incomingClass);
			sendParms.addElement(dt);
			
			// ADD PENDING RECORD (NEW VARIANCE for this Item)
			if (incomingClass.getRequestType().trim().equals("addVariance"))
			{
				try
				{
			   	   addItemVariance("", sendParms, conn);
				}
			   	catch(Exception e)
				{
			   	}
			   	incomingClass.setUpdDate(dt.getDateFormatyyyyMMdd());
			   	incomingClass.setUpdTime(dt.getTimeFormathhmmss());
			    incomingClass.setUpdUser(incomingClass.getUpdateUser().trim());
			    incomingClass.setDateExpired(dt.getDateFormatyyyyMMdd());
			    incomingClass.setDateIssued(dt.getDateFormatyyyyMMdd());
			 }
			 // DELETE PENDING RECORD
			 if (incomingClass.getRequestType().trim().equals("deleteVariance"))
			 {
			   	try
				{
			   		deleteItemVariance("", sendParms, conn);
				}
			   	catch(Exception e)
				{
			   	  	
				}
			 }   
			 // UPDATE PENDING RECORD
			 if (incomingClass.getRequestType().trim().equals("updVariance") &&
			 	 !incomingClass.getGoButton().trim().equals(""))
			 {
			   	try
				{
			   		updItemVariance("", sendParms, conn);
				}
			   	catch(Exception e)
				{
			   	  	
				}
			 }  
			 // CHANGE FROM ACTIVE TO PENDING
			 if (incomingClass.getRequestType().trim().equals("pendVariance"))
			 {
			   	try
				{
			   		updItemVariance("", sendParms, conn);
				}
			   	catch(Exception e)
				{
			   	  	
				}
			 }	
			 if (incomingClass.getRequestType().trim().equals("addVariance") ||
			 	 incomingClass.getRequestType().trim().equals("updVariance"))
			 { // Go get the ItemVariance Information to return
			   	try
				{
			   	   Vector sendParms2 = new Vector();
			   	   sendParms2.addElement(incomingClass); 
			   	   returnValue = findVariance(sendParms2, conn);
				}
			   	catch(Exception e)
				{
			   	}
			 }
			
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the New Item Variance. " + e) ;
		// return connection.
		} finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack12(conn);
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceItem.");
			throwError.append("updateItemVariance(String: Environment, UpdItemVariance)");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

/**
 *    Method Created 9/12/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the InqIItem Object for use in the SQL statement
 * @return BeanItem -- Filled with Vectors of ItemVariances.
 * @throws Exception
 */
private static ItemVariance findVariance(Vector inValues, 
								  			Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	ItemVariance returnValue = new ItemVariance();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
	    try {
		   // Get the list of DataSet File Names - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", "findVariance", inValues));
		   rs = listThem.executeQuery();
	     } catch(Exception e)
		 {
	     	throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }
	     if (throwError.toString().equals(""))
	     {
	     	try
			{
	     		if (rs.next())
	     		{
	     			// Build a Vector of Classes to Return
	     			try {
	     				returnValue = loadFieldsItemVariance("", rs);
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
		throwError.append("ServiceItem.");
		throwError.append("listVariancesByItem(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 * Use to Add a record into the SPPMVARI File
 */
	
private static void addItemVariance(String environment,
									Vector incomingParms,  // UptItemVariance
									Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement addIt = null;
	// 	build SQL Statement, Execute SQL Statement
	try {
		addIt = conn.createStatement();
		addIt.executeUpdate(buildSqlStatement(environment, "addVariance", incomingParms));
	} catch (Exception e) {
		throwError.append(" error occured building or executing a sql statement to add a variance. " + e);
	} finally {
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
		throwError.append("ServiceItem.");
		throwError.append("addItemVariance(");
		throwError.append("String:Environment,String requestType, Vector, Connection)");
		throw new Exception(throwError.toString());
	}
	return;
}

/**
 * Use to Delete a Pending Record from the SPPMVARI File
 *      (Actually put the word Delete in the Status)
 */
	
private static void deleteItemVariance(String environment,
									   Vector incomingParms,  // UptItemVariance
									   Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement deleteIt = null;
	// 	build SQL Statement, Execute SQL Statement
	try {
		deleteIt = conn.createStatement();
		deleteIt.executeUpdate(buildSqlStatement(environment, "deleteVariance", incomingParms));
	} catch (Exception e) {
		throwError.append(" error occured building or executing a sql statement to add a variance. " + e);
	} finally {
		try {
			if (deleteIt != null)
				deleteIt.close();
		} catch (Exception el) {
			el.printStackTrace();
		}
	}
	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceItem.");
		throwError.append("deleteItemVariance(");
		throwError.append("String:Environment,String requestType, Vector, Connection)");
		throw new Exception(throwError.toString());
	}
	return;
}

/**
 * Use to Update the Item Variance File SPPMVARI
 */
	
private static void updItemVariance(String environment,
									Vector incomingParms,  // UptItemVariance
									Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Statement updateIt = null;
	// 	build SQL Statement, Execute SQL Statement
	try {
		UpdItemVariance fromVb = (UpdItemVariance) incomingParms.elementAt(0);
		updateIt = conn.createStatement();
		updateIt.executeUpdate(buildSqlStatement(environment, fromVb.getRequestType().trim(), incomingParms));
	} catch (Exception e) {
		throwError.append(" error occured building or executing a sql statement to update a variance. " + e);
	} finally {
		try {
			if (updateIt != null)
				updateIt.close();
		} catch (Exception el) {
			el.printStackTrace();
		}
	}
	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceItem.");
		throwError.append("updItemVariance(");
		throwError.append("String:Environment,String requestType, Vector, Connection)");
		throw new Exception(throwError.toString());
	}
	return;
}

/**
 *   Method Created 10/15/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the InqCodeDate Object for use in the SQL statement
 * @return ItemWarehouse 
 * @throws Exception
 */
public static ItemWarehouse findItemCodeDate(Vector inValues)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	ItemWarehouse returnValue = new ItemWarehouse();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		// 1/25/12 TWalton - Change to use ServiceConnection
		//conn = ConnectionStack5.getConnection();
		conn = ServiceConnection.getConnectionStack12();
		
		returnValue = findItemCodeDate(inValues, conn);
	} catch (Exception e)
	{
		throwError.append(e);
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceItem.");
		throwError.append("findItemCodeDate(");
		throwError.append("Vector). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 *    Method Created 10/15/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in the InqCodeDate Object for use in the SQL statement
 * @return ItemWarehouse
 * @throws Exception
 */
private static ItemWarehouse findItemCodeDate(Vector inValues, 
											  Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	ItemWarehouse returnValue = new ItemWarehouse();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of DataSet File Names - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", "codeDate", inValues));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		   	throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }		
		 try
		 {
		 	if (rs.next())
		    {
				returnValue = loadFieldsItemWarehouse("codeDate", rs);
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
		throwError.append("ServiceItem.");
		throwError.append("findItemCodeDate(");
		throwError.append("Vector, conn). ");
		throw new Exception(throwError.toString());
	}
	// return value
	return returnValue;
}

/**
 * Load class fields from result set.
 */

public static ItemWarehouse loadFieldsItemWarehouse(String requestType,
						          		  		    ResultSet rs)
		throws Exception
{
	StringBuffer throwError = new StringBuffer();
	ItemWarehouse returnValue = new ItemWarehouse();
	try{
	  if (requestType.equals("codeDate") ||
	  	  requestType.equals("listCPGSpecs") ||
	  	  requestType.equals("dtlCPGSpecs"))
	  {
	  	returnValue.setItemNumber(rs.getString("MMITNO").trim());
		returnValue.setItemDescription(rs.getString("MMITDS").trim());
		try
		{
		   returnValue.setDaysShelfLife(rs.getString("MBSLDY"));
		}
		catch(Exception e)
		{}
	  }
		
	  if (requestType.equals("codeDate"))
	  {
		returnValue.setItemType(rs.getString("MMITTY").trim());
		returnValue.setFacilityNumber(rs.getString("MBFACI").trim());
		returnValue.setFacilityName(rs.getString("CFFACN").trim());
		returnValue.setWarehouseNumber(rs.getString("MBWHLO").trim());
		returnValue.setWarehouseName(rs.getString("MWWHNM").trim());
		if (rs.getString("AEATID") != null)
			returnValue.setBestByItem("Yes");
		if (rs.getString("MSWPGT") != null)
			returnValue.setNewItemPalletGTIN(rs.getString("MSWPGT").trim());
	  }
	  if (requestType.equals("listCPGSpecs") ||
		  requestType.equals("dtlCPGSpecs"))
	  {
	  	  try
		  {
		  	returnValue.setGrossWeight(rs.getString("MMGRWE").trim());
			returnValue.setPalletStacking(rs.getString("MMFRAG").trim());
		  }
	  	  catch(Exception e)
		  {}
	  }
	  	if (requestType.equals("listCPGSpecs"))
		  {	  
			try
			{
			  // returnValue.setCasesPerPallet(rs.getString("A.MUCOFA"));
				returnValue.setCasesPerPallet(rs.getString(46));
			}
			catch(Exception e)
			{}
			try
			{
			   // returnValue.setCasesPerLayer(rs.getString("B.MUCOFA"));
				returnValue.setCasesPerLayer(rs.getString(47));
			}
			catch(Exception e)
			{
	//		  System.out.println("stop" + e);	
			}
	   }
	  if (requestType.equals("dtlCPGSpecs"))
	  {
	  		try
			{
			  // returnValue.setCasesPerPallet(rs.getString("A.MUCOFA"));
				returnValue.setCasesPerPallet(rs.getString(43));
			}
			catch(Exception e)
			{}
			try
			{
			   // returnValue.setCasesPerLayer(rs.getString("B.MUCOFA"));
				returnValue.setCasesPerLayer(rs.getString(44));
			}
			catch(Exception e)
			{
	//		  System.out.println("stop" + e);	
			}
	   }
	  if (requestType.equals("listShelfLife"))
	  {
		  returnValue.setItemNumber(rs.getString("MBITNO"));
		  returnValue.setDaysShelfLife(rs.getString("MBSLDY"));
	  }

	} catch(Exception e)
	{
		throwError.append(" Problem loading the Item Warehouse class ");
		throwError.append("from the result set. " + e) ;
	}
	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceItem.");
		throwError.append("loadFieldsItemWarehouse(requestType, rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}
	return returnValue;
}

/**
 *   Method Created 10/20/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				inqCPGSpecs - Means it will tie by item to the CPG Spec files to return only valid entries 
 */
public static Vector dropDownItemType(Vector inValues)
{
	// Changes to Method
	// 2/2/09 TWalton - Changed from Connection Stack to DBConnection			
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack12();
		ddit = dropDownItemType(inValues, conn);
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// return value
	return ddit;
}

/**
 * Method Created 10/20/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 */
private static Vector dropDownItemType(Vector inValues, 
									   Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Item Type Values - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", "ddItemType", inValues));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		 	System.out.println("error" + e);
		   	//throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }		
		 try
		 {
		 	while (rs.next())
		    {
		 		DropDownSingle dds = loadFieldsDropDownSingle("ddItemType", rs);
		 		ddit.addElement(dds);
     		}// END of the IF Statement
		 } catch(Exception e)
		 {
			//throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		//throwError.append(e);
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
//	if (!throwError.toString().trim().equals(""))
//	{
//		throwError.append(" @ com.treetop.Services.");
//		throwError.append("ServiceItem.");
//		throwError.append("findItemCodeDate(");
//		throwError.append("Vector, conn). ");
//		throw new Exception(throwError.toString());
//	}
	// return value
	return ddit;
}

/**
 *   Method Created 12/19/08 THaile
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 * 
 *  Incoming Vector:
 *    Element 0 = String - Values:
 * 				  inqIngSpecs - Means it will tie by item to the ING Spec files to return only valid entries 
 */
public static Vector dropDownItemInqIngSpec(Vector inValues)
{
	// Changes to Method
	// 2/2/09 TWalton - Changed from ConnectionStack to DBConnection
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack12();
		ddit = dropDownItemInqIngSpec(inValues, conn);
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// return value
	return ddit;
}

/**
 * Method Created 12/19/08 THaile
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 */
private static Vector dropDownItemInqIngSpec(Vector inValues, 
									         Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Items in the Specification Header file - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", "ddItemInqIngSpec", inValues));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		 	System.out.println("error" + e);
		   	//throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }		
		 try
		 {
		 	while (rs.next())
		    {
		 		DropDownSingle dds = loadFieldsDropDownSingle("ddItemInqIngSpec", rs);
		 		ddit.addElement(dds);
     		}// END of the IF Statement
		 } catch(Exception e)
		 {
			//throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		//throwError.append(e);
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
//	if (!throwError.toString().trim().equals(""))
//	{
//		throwError.append(" @ com.treetop.Services.");
//		throwError.append("ServiceItem.");
//		throwError.append("findItemCodeDate(");
//		throwError.append("Vector, conn). ");
//		throw new Exception(throwError.toString());
//	}
	// return value
	return ddit;
}


/**
 *   Method Created 10/20/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				inqCPGSpecs - Means it will tie by item to the CPG Spec files to return only valid entries 
 */
public static Vector dropDownProductGroup(Vector inValues)
{
	// Changes to Method
	// 2/2/09 TWalton - Change from ConnectionStack to DBConnection
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack12();
		ddit = dropDownProductGroup(inValues, conn);
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// return value
	return ddit;
}

/**
 * Method Created 10/20/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 */
private static Vector dropDownProductGroup(Vector inValues, 
									   	   Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Item Type Values - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", "ddProductGroup", inValues));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		 	System.out.println("error" + e);
		   	//throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }		
		 try
		 {
		 	while (rs.next())
		    {
		 		DropDownSingle dds = loadFieldsDropDownSingle("ddProductGroup", rs);
		 		ddit.addElement(dds);
     		}// END of the IF Statement
		 } catch(Exception e)
		 {
			//throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		//throwError.append(e);
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
//	if (!throwError.toString().trim().equals(""))
//	{
//		throwError.append(" @ com.treetop.Services.");
//		throwError.append("ServiceItem.");
//		throwError.append("findItemCodeDate(");
//		throwError.append("Vector, conn). ");
//		throw new Exception(throwError.toString());
//	}
	// return value
	return ddit;
}
/**
 *   Method Created 10/20/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				inqCPGSpecs - Means it will tie by item to the CPG Spec files to return only valid entries 
 */
public static Vector dropDownUser1(Vector inValues)
{
	// Changes to Method
	// 2/2/09 TWalton - Change from Connection Stack to dbConnection
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack12();
		ddit = dropDownUser1(inValues, conn);
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// return value
	return ddit;
}

/**
 * Method Created 10/20/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle - Utility
 */
private static Vector dropDownUser1(Vector inValues, 
									Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Item Type Values - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", "ddUser1", inValues));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		 	System.out.println("error" + e);
		   	//throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }		
		 try
		 {
		 	while (rs.next())
		    {
		 		DropDownSingle dds = loadFieldsDropDownSingle("ddUser1", rs);
		 		ddit.addElement(dds);
     		}// END of the IF Statement
		 } catch(Exception e)
		 {
			//throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		//throwError.append(e);
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
//	if (!throwError.toString().trim().equals(""))
//	{
//		throwError.append(" @ com.treetop.Services.");
//		throwError.append("ServiceItem.");
//		throwError.append("findItemCodeDate(");
//		throwError.append("Vector, conn). ");
//		throw new Exception(throwError.toString());
//	}
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
	  if (requestType.equals("ddItemType"))
	  {
		returnValue.setValue(rs.getString("TYITTY").trim());
		returnValue.setDescription(rs.getString("TYTX40").trim());
	  }
	  if (requestType.equals("ddProductGroup") 
	  	  || requestType.equals("ddUser1")
		  || requestType.equals("rfGroups")
		  || requestType.equals("rfGroup")
		  || requestType.equals("ddItemGroup"))
	  {
		returnValue.setValue(rs.getString("CTSTKY").trim());
		returnValue.setDescription(rs.getString("CTTX40").trim());
	  }
	  
	  if (requestType.equals("ddItemInqIngSpec"))
	  {
		returnValue.setValue(rs.getString("IDTRSC").trim());
		returnValue.setDescription(rs.getString("MMITDS").trim());
	  }
	  
	} catch(Exception e)
	{
		throwError.append(" Error loading dropdown fields ");
		throwError.append("from the result set. " + e) ;
	}
	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceItem.");
		throwError.append("loadFieldsDropDownSingle(requestType(");
		throwError.append(requestType + "), rs: ");
		throwError.append(requestType + "). ");
		throw new Exception(throwError.toString());
	}
	return returnValue;
}

/**
 *   Method Created 11/7/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				rfGroups - Means it will show the whole list that begins with RF 
 */
public static Vector dropDownItemGroup(Vector inValues)
{
	// Changes to Method
	// 2/2/09 TWalton - Changed from Connection Stack to dbconnection
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack12();
		ddit = dropDownItemGroup(inValues, conn);
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// return value
	return ddit;
}

/**
 * See Public Method dropDownItemsByType
 */
private static Vector dropDownItemsByType(CommonRequestBean inBean, Connection conn)
{
	
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Item Type Values - Along with Descriptions
		   listThem = conn.prepareStatement(BuildSQL.getListItemsByType(inBean));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		 	System.out.println("Error Found in ServiceItem.dropDownItemsByType " + e);
		 }		
		 try
		 {
		 	while (rs.next())
		    {
		 		ddit.addElement(LoadFields.loadDropDownSingleItemsByType(rs, new DropDownSingle()));
     		}// END of the IF Statement
		 } catch(Exception e)
		 {
			//
		 } 		
	} catch (Exception e)
	{
		//throwError.append(e);
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
	
	return ddit;
}

/**
 *   Method Created 11/7/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Vector:
 *    Element 1 = String - Values:
 * 				rfGroup - Means it will show CN and OR
 */
public static Vector dropDownEnvironmentGroup(Vector inValues)
{
//	 Changes to Method
	// 2/2/09 TWalton - Change from Connection Stack to dbConnection
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack12();
		ddit = dropDownEnvironmentGroup(inValues, conn);
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// return value
	return ddit;
}

/**
 * Method Created 11/7/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownEnvironmentGroup(Vector inValues, 
									   	       Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Item Type Values - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", (String) inValues.elementAt(0), inValues));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		 	System.out.println("error" + e);
		   	//throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }		
		 try
		 {
		 	while (rs.next())
		    {
		 		DropDownSingle dds = loadFieldsDropDownSingle((String) inValues.elementAt(0), rs);
		 		ddit.addElement(dds);
     		}// END of the IF Statement
		 } catch(Exception e)
		 {
			//throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		//throwError.append(e);
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
//	if (!throwError.toString().trim().equals(""))
//	{
//		throwError.append(" @ com.treetop.Services.");
//		throwError.append("ServiceItem.");
//		throwError.append("dropDownEnvironmentGroup(");
//		throwError.append("Vector, conn). ");
//		throw new Exception(throwError.toString());
//	}
	// return value
	return ddit;
}

/**
 * Return a UPC (Universal Product Code) with
 * a calculated check digit.
 * @param String UPC 11 positions.
 * @return String UPC 12 positions (11 + check digit).
 * @throws Exception
 * 
 * formula
 * 		if incoming upc is only 10 positions long 
 *      add a "0" to the start of the number. This
 *      due some old file constrants that have field
 *      lengths set at 10 positions long.
 * 
 * 		odds = Add the numbers in positions One,
 * 		Three, Five, Seven, Nine, and Eleven.
 * 
 * 		evens = Add the number in positions Two,
 * 				Four, Six, Eight, and Ten. 
 * 
 * 		odds = odds * 3
 * 
 * 		total = odds + evens.
 * 
 * 		The check digit is the smallest number needed
 * 		to round the result (total) UP to a multiple 
 * 		of ten. 
 * 
 * 		Example "9101454121022"
 * 		odds = 9 + 0 + 4 + 4 + 2 + 0 + 2 = 21.
 * 		odds = 21 * 3 = 63.
 * 		evens = 1 + 1 + 5 + 1 + 1 + 2 = 11.
 * 		total = 63 + 11 = 74.
 * 		check digit = 80 - 74 = 6.
 * 		check digit = 6.
 * 
 */

public static String checkDigitUPC(String upcIn) 
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	String upcError = upcIn;
	
	// If the incoming upc value is only ten long add
	// "0" to the front of the value.
	if (upcIn != null &&
		upcIn.length() == 10)
		upcIn = "0" + upcIn;
	
	String upcOut = upcIn;
	
	// verify incoming upc number is exactly 11 long.
	if (upcIn == null ||
		upcIn.length() != 11)
	{
		throwError.append(" The UPC number received ");
		throwError.append("must not be null, and must be ");
		throwError.append("10 to 11 long. ");
	}
	
	try {
		if (throwError.toString().equals(""))
		{
			int total      = 0;
			int totalEvens = 0;
			int totalOdds  = 0;
			int odds       = 0;
			int evens      = 0;
		
			//Remember arrays start at zero not one.
			for (int x = 0; x < 11; x++)
			{
				// acumulate odd digits.
				if (x == 0 || x == 2 || x == 4 || x == 6 ||
					x == 8 || x == 10)
				{
					odds = new Integer(upcIn.substring(x, x + 1)).intValue();
					totalOdds = totalOdds + odds;
				}
				
				// accumulate even digits.
				if (x == 1 || x == 3 || x == 5 || x == 7 ||
					x == 9)
				{
					evens = new Integer(upcIn.substring(x, x + 1)).intValue();
					totalEvens = totalEvens + evens;
				}
			}
		
			// multiply odds times 3, then add odds and evens.
			total = totalOdds * 3;
			total = total + totalEvens;
			
			// determine the last digit value.
			String someNumber = new Integer(total).toString().trim();
			int size = someNumber.length();
			
			String lastDigit = someNumber.substring(size -1, size);
			
			// determine the check digit value.
			String checkDigit = "";
	
			if (lastDigit.equals("0"))
				checkDigit = "0";
			if (lastDigit.equals("1"))
				checkDigit = "9";
			if (lastDigit.equals("2"))
				checkDigit = "8";
			if (lastDigit.equals("3"))
				checkDigit = "7";
			if (lastDigit.equals("4"))
				checkDigit = "6";
			if (lastDigit.equals("5"))
				checkDigit = "5";
			if (lastDigit.equals("6"))
				checkDigit = "4";
			if (lastDigit.equals("7"))
				checkDigit = "3";
			if (lastDigit.equals("8"))
				checkDigit = "2";
			if (lastDigit.equals("9"))
				checkDigit = "1";
	
			upcOut = upcIn + checkDigit;
		}
	} catch(Exception e){
		throwError.append("Error on check digit determination. " + e);
	}
	
	// return data.
	
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceGTIN.");
		throwError.append("checkDigitUPC(upc:");
		throwError.append(upcError + "). ");
		throw new Exception(throwError.toString());
	}
		
	return upcOut;
}

	/**
	 * Use to Validate a Send in Item, Return Message if Item is not found
	 * @deprecated
	 * 1/26/12 TWalton - use the verifyItem(commonRequestBean)
	 */
	public static String verifyItem(String environment, String itemNumber)
			throws Exception
	{
		// Changes to Method
		// 2/2/09 - Changed from Connection Pool
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		Statement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyItem";
			String sqlString = "";
			
			// verify base class initialization.
			ServiceItem a = new ServiceItem();
						
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Item
			if (itemNumber == null || itemNumber.trim().equals(""))
				rtnProblem.append(" Item Number must not be null or empty.");
			
			// get Item Info.
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(itemNumber);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
				rtnProblem.append(" Problem when building Test for Item: ");
				rtnProblem.append(itemNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
			
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					// 1/25/12 TWalton - Change to use ServiceConnection
					//conn = ConnectionStack5.getConnection();
					conn = ServiceConnection.getConnectionStack12();
					
					findIt = conn.createStatement();
					rs = findIt.executeQuery(sqlString);
					
					if (rs.next())
					{
//						 it exists and all is good.
					} else {
						rtnProblem.append(" Item Number '" + itemNumber + "' ");
						rtnProblem.append("does not exist. ");
					}
					
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Item Number: ");
					rtnProblem.append(itemNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Item Number class ");
			throwError.append("from the result set. " + e) ;
		// return connection.
		} finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack12(conn);
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
			throwError.append("ServiceItem.");
			throwError.append("verifyItem(String: itemNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}

/**
 *   Method Created 1/6/11 TWalton
 * @param -- Send in a UpdItem
 * @return void
 *    Based on Sent in information - determine the NEED to populate additional Alias' 
 */
public static void addAlias(UpdItem inValues)
{
	try {
		
		MMS025MIAddAlias maa = new MMS025MIAddAlias();
		maa.setSentFromProgram("Update Item");
		maa.setEnvironment(inValues.getEnvironment());
		//maa.setEnvironment("TST");
		maa.setUserProfile(inValues.getUpdateUser());
	  	maa.setCompany("100");
	  	maa.setItemNumber(inValues.getItem());
		
	  	if (!inValues.getAliasCheckBox().trim().equals("") &&
	  		!inValues.getCaseUPC().trim().equals(""))
		{ // only move forward IF their is an alias to be created
			// Figure out the Alias Value
			//   Take the UPC Case and add a 0 to the front and do NOT include the check digit	
  	  	    maa.setAliasCategory("01");
	  	    maa.setAliasNumber("0" + inValues.getCaseUPC().substring(0, (inValues.getCaseUPC().length() - 1)));
 	  	    MMS025MI.addAlias(maa);
  	  	    
 	  	    if (!inValues.getPalletGTIN().trim().equals(""))
 	  	    {
 	  	    	maa.setAliasCategory("02");
 	  	    	maa.setAliasQualifier("GTIN");
 	  	    	maa.setAliasNumber(inValues.getPalletGTIN());
 	  	    	MMS025MI.addAlias(maa);
 	  	    }
 	  	      
 	  	    maa.setAliasQualifier("UPC");
	  	    maa.setAliasNumber(inValues.getCaseUPC());
	  	    MMS025MI.addAlias(maa);
 	  	     
	  	    if (!inValues.getLabelUPC().trim().equals(""))
	  	    {
	  	    	maa.setAliasCategory("03");
	  	    	maa.setAliasQualifier("LBL");
	  	    	maa.setAliasNumber(inValues.getLabelUPC());
	  	    	MMS025MI.addAlias(maa);
	  	    }
	    }
		if (!inValues.getFlagFPK().trim().equals(""))
		{
			maa.setAliasCategory("03");
	  	    maa.setAliasQualifier("FPK");
	  	    maa.setAliasNumber("Y");
  	        MMS025MI.addAlias(maa);
		}
		if (!inValues.getFlagOPN().trim().equals(""))
		{
			maa.setAliasCategory("03");
	  	    maa.setAliasQualifier("OPN");
	  	    maa.setAliasNumber("Y");
  	        MMS025MI.addAlias(maa);
		}
		if (!inValues.getFlagJCE().trim().equals(""))
		{
			maa.setAliasCategory("03");
	  	    maa.setAliasQualifier("JCE");
	  	    maa.setAliasNumber("100% JUICE");
  	        MMS025MI.addAlias(maa);
		}
		if (!inValues.getPlanner().trim().equals(""))
		{
			maa.setAliasCategory("03");
	  	    maa.setAliasQualifier("PLN");
	  	    maa.setAliasNumber(inValues.getPlanner());
  	        MMS025MI.addAlias(maa);
		}
		if (!inValues.getFlagCAR().trim().equals("") &&
			!inValues.getItemGroup().trim().equals(""))
		{
			maa.setAliasCategory("03");
			maa.setAliasQualifier("CAR");
			maa.setAliasNumber(inValues.getItemGroup().trim());
			MMS025MI.addAlias(maa);
		}
		//9/20/12 - TW - Add Information for SS, RPT1 and CCI 
		if (!inValues.getFlagSingleStrength().trim().equals(""))
		{
			maa.setAliasCategory("03");
	  	    maa.setAliasQualifier("SS");
	  	    maa.setAliasNumber("Y");
  	        MMS025MI.addAlias(maa);
		}
		if (!inValues.getAliasRPT1().trim().equals(""))
		{
			maa.setAliasCategory("03");
	  	    maa.setAliasQualifier("RPT1");
	  	    maa.setAliasNumber(inValues.getAliasRPT1().trim());
  	        MMS025MI.addAlias(maa);
		}
		if (!inValues.getFlagClub().trim().equals(""))
		{
			maa.setAliasCategory("03");
	  	    maa.setAliasQualifier("CCI");
	  	    maa.setAliasNumber("Y");
  	        MMS025MI.addAlias(maa);
		}
		if (!inValues.getClubItemNumber().trim().equals(""))
		{
			// Delete the CCI Record (that had Y in it)
			MMS025MIDeleteAlias mda = new MMS025MIDeleteAlias();
			mda.setSentFromProgram("Update Item");
			mda.setEnvironment(inValues.getEnvironment());
			//maa.setEnvironment("TST");
			mda.setUserProfile(inValues.getUpdateUser());
		  	mda.setCompany("100");
		  	mda.setItemNumber(inValues.getItem());
		  	mda.setAliasCategory("03");
		  	mda.setAliasQualifier("CCI");
		  	mda.setAliasNumber("Y");
		  	MMS025MI.deleteAlias(mda);
		  	// Then add a NEW record
		  	maa.setAliasCategory("03");
	  	    maa.setAliasQualifier("CCI");
	  	    maa.setAliasNumber(inValues.getClubItemNumber().trim());
  	        MMS025MI.addAlias(maa);
		}
		if (!inValues.getFlagAllergen().trim().equals(""))
		{
			maa.setAliasCategory("03");
	  	    maa.setAliasQualifier("ALRG");
	  	    maa.setAliasNumber("Y");
  	        MMS025MI.addAlias(maa);
		}
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
		System.out.println("exception caught when creating alias': " + e);
	}
	return;
}

/**
 *   Method Created 1/12/11 TWalton
 *   // Use to control the information retrieval
 * @param -- CommonRequestBean -- it has Environment and everything
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Bean:
 *    IDlevel1 = String - Values:
 * 				rfGroups - Means it will show the whole list that begins with RF 
 * 				inqSpec - Means this will return only a list of items groups that have items assigned to specs
 */
public static Vector dropDownItemGroup(CommonRequestBean requestBean)
{
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find Item Groups
		conn = ServiceConnection.getConnectionStack12();
		ddit = dropDownItemGroup(requestBean, conn);
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// return value
	return ddit;
}

/**
 * Method Created 1/12/11 TWalton
 *   // Use to control the information retrieval
 * @param -- CommonRequestBean - has all values needed to return the List of Item Groups
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownItemGroup(CommonRequestBean requestBean, 
									   	Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Item Type Values - Along with Descriptions
			// this should be replaced when the buildSqlStatement is redone
			Vector sendIn = new Vector();
			sendIn.addElement(requestBean.getIdLevel1());
			sendIn.addElement(requestBean);
		   listThem = conn.prepareStatement(buildSqlStatement(requestBean.getEnvironment(), "ddItemGroup", sendIn));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		 	System.out.println("error" + e);
		   	//throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }		
		 try
		 {
		 	while (rs.next())
		    {
		 		DropDownSingle dds = loadFieldsDropDownSingle("ddItemGroup", rs);
		 		ddit.addElement(dds);
     		}// END of the IF Statement
		 } catch(Exception e)
		 {
			//throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		//throwError.append(e);
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
	return ddit;
}

public static Vector<HtmlOption> dropDownItemTypeHtmlOption(CommonRequestBean requestBean) {
	Vector<HtmlOption> data = new Vector<HtmlOption>();
	Vector dropDown = new Vector();
	
	dropDown = dropDownItemType(requestBean);
	
	for (int i=0; !dropDown.isEmpty() && i<dropDown.size(); i++) {
		HtmlOption option = new HtmlOption((DropDownSingle) dropDown.elementAt(i));
		data.addElement(option);
	}
	
	return data;
}

/**
 *   Method Created 1/12/11 TWalton
 *   // Use to control the information retrieval
 * @param -- CommonRequestBean -- it has Environment and everything
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Bean: - for Specs
 *    IDlevel1 = String - Values:
 * 				inqSpec - Means this will return only a list of items types that have items assigned to specs
 * 
 */
public static Vector dropDownItemType(CommonRequestBean requestBean)
{
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find Item Groups
		conn = ServiceConnection.getConnectionStack12();
		ddit = dropDownItemType(requestBean, conn);
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// return value
	return ddit;
}

/**
 * Method Created 1/12/11 TWalton
 *   // Use to control the information retrieval
 * @param -- CommonRequestBean - has all values needed to return the List of Item Types
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownItemType(CommonRequestBean requestBean, 
									   	Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Item Type Values - Along with Descriptions
			// this should be replaced when the buildSqlStatement is redone
			Vector sendIn = new Vector();
			sendIn.addElement(requestBean.getIdLevel1());
			sendIn.addElement(requestBean);
		   listThem = conn.prepareStatement(buildSqlStatement(requestBean.getEnvironment(), "ddItemType", sendIn));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		 	System.out.println("error" + e);
		   	//throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }		
		 try
		 {
		 	while (rs.next())
		    {
		 		DropDownSingle dds = loadFieldsDropDownSingle("ddItemType", rs);
		 		ddit.addElement(dds);
     		}// END of the IF Statement
		 } catch(Exception e)
		 {
			//throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		//throwError.append(e);
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

	return ddit;
}

/**
 *   Method Created 1/12/11 TWalton
 *   // Use to control the information retrieval
 * @param -- CommonRequestBean -- it has Environment and everything
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Bean:
 *    IDlevel1 = String - Values:
 * 				inqSpec - Means this will return only a list of items groups that have items assigned to specs
 */
public static Vector dropDownProductGroup(CommonRequestBean requestBean)
{
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find Item Groups
		conn = ServiceConnection.getConnectionStack12();
		ddit = dropDownProductGroup(requestBean, conn);
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// return value
	return ddit;
}

/**
 * Method Created 1/12/11 TWalton
 *   // Use to control the information retrieval
 * @param -- CommonRequestBean - has all values needed to return the List of Item Groups
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownProductGroup(CommonRequestBean requestBean, 
									   	Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Item Type Values - Along with Descriptions
			// this should be replaced when the buildSqlStatement is redone
			Vector sendIn = new Vector();
			sendIn.addElement(requestBean.getIdLevel1());
			sendIn.addElement(requestBean);
		   listThem = conn.prepareStatement(buildSqlStatement(requestBean.getEnvironment(), "ddProductGroup", sendIn));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		 	System.out.println("error" + e);
		   	//throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }		
		 try
		 {
		 	while (rs.next())
		    {
		 		DropDownSingle dds = loadFieldsDropDownSingle("ddProductGroup", rs);
		 		ddit.addElement(dds);
     		}// END of the IF Statement
		 } catch(Exception e)
		 {
			//throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		//throwError.append(e);
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
	return ddit;
}

/**
 *   Method Created 1/12/11 TWalton
 *   // Use to control the information retrieval
 * @param -- CommonRequestBean -- it has Environment and everything
 * @return Vector - of DropDownSingle 
 * 
 *  Incoming Bean:
 *    IDlevel1 = String - Values:
 * 				inqSpec - Means this will return only a list of whatever was in the User 1 Field
 */
public static Vector dropDownUser1(CommonRequestBean requestBean)
{
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find Item Groups
		conn = ServiceConnection.getConnectionStack12();
		ddit = dropDownUser1(requestBean, conn);
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// return value
	return ddit;
}

/**
 * Method Created 1/12/11 TWalton
 *   // Use to control the information retrieval
 * @param -- CommonRequestBean - has all values needed to return the List of User 1 Field
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownUser1(CommonRequestBean requestBean, 
									Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of User 1 Field Values - Along with Descriptions
			// this should be replaced when the buildSqlStatement is redone
			Vector sendIn = new Vector();
			sendIn.addElement(requestBean.getIdLevel1());
			sendIn.addElement(requestBean);
		   listThem = conn.prepareStatement(buildSqlStatement(requestBean.getEnvironment(), "ddUser1", sendIn));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		 	System.out.println("error" + e);
		   	//throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }		
		 try
		 {
		 	while (rs.next())
		    {
		 		DropDownSingle dds = loadFieldsDropDownSingle("ddUser1", rs);
		 		ddit.addElement(dds);
     		}// END of the IF Statement
		 } catch(Exception e)
		 {
			//throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		//throwError.append(e);
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
	return ddit;
}

/**
 * Send in Environment, Item Number, 
 *    Return an Item Class 
 */
private static Item findItemAlias(String environment,
								  Item inObject,
							      Connection conn)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	Item rtnValue = new Item();
	PreparedStatement findIt = null;
	ResultSet rs = null;
	String sqlString = new String();
	try { 
		try {
			Vector parmClass = new Vector();
			parmClass.addElement(inObject.getItemNumber());
			sqlString = buildSqlStatement(environment, "findItemAlias", parmClass);
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
				while (rs.next())
				{
					// need to Load the information into the Item Class
					try
					{
						rtnValue = loadFieldsItem("loadItemAlias", rs);
						
					} catch (Exception e) {
						throwError.append(" error occured loading Fields from an sql statement. " + e);
					}
					if (!rtnValue.getM3ItemAliasPopular().trim().equals(""))
						inObject.setM3ItemAliasPopular(rtnValue.getM3ItemAliasPopular());
					if (!rtnValue.getM3ItemAliasCaseUPC().trim().equals(""))
						inObject.setM3ItemAliasCaseUPC(rtnValue.getM3ItemAliasCaseUPC());
					if (!rtnValue.getM3ItemAliasLabelUPC().trim().equals(""))
						inObject.setM3ItemAliasLabelUPC(rtnValue.getM3ItemAliasLabelUPC());
					if (!rtnValue.getM3ItemAliasPalletGTIN().trim().equals(""))
						inObject.setM3ItemAliasPalletGTIN(rtnValue.getM3ItemAliasPalletGTIN());
					if (!rtnValue.getM3ItemAliasFreshPack().trim().equals(""))
						inObject.setM3ItemAliasFreshPack(rtnValue.getM3ItemAliasFreshPack());
					if (!rtnValue.getM3ItemAliasOpenOrders().trim().equals(""))
						inObject.setM3ItemAliasOpenOrders(rtnValue.getM3ItemAliasOpenOrders());
					if (!rtnValue.getM3ItemAlias100PercentJuice().trim().equals(""))
						inObject.setM3ItemAlias100PercentJuice(rtnValue.getM3ItemAlias100PercentJuice());
					if (!rtnValue.getM3ItemAliasPlanner().trim().equals(""))
						inObject.setM3ItemAliasPlanner(rtnValue.getM3ItemAliasPlanner());
					if (!rtnValue.getM3ItemAliasPlannerDesc().trim().equals(""))
						inObject.setM3ItemAliasPlannerDesc(rtnValue.getM3ItemAliasPlannerDesc());
					if (!rtnValue.getM3ItemAliasCAR().trim().equals(""))
						inObject.setM3ItemAliasCAR(rtnValue.getM3ItemAliasCAR());
					// 10/17/12 TW -- added additional alias information
					if (!rtnValue.getM3ItemAliasReport1().trim().equals(""))
						inObject.setM3ItemAliasReport1(rtnValue.getM3ItemAliasReport1());
					if (!rtnValue.getM3ItemAliasReport1Desc().trim().equals(""))
						inObject.setM3ItemAliasReport1Desc(rtnValue.getM3ItemAliasReport1Desc());
					if (!rtnValue.getM3ItemAliasClubCustItem().trim().equals(""))
						inObject.setM3ItemAliasClubCustItem(rtnValue.getM3ItemAliasClubCustItem());
					if (!rtnValue.getM3ItemAliasSingleStrength().trim().equals(""))
						inObject.setM3ItemAliasSingleStrength(rtnValue.getM3ItemAliasSingleStrength());
				}
			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. " + e);
			}
		}
	} catch(Exception e)
	{
		throwError.append(" Problem Retrieving Data for Item Class. " + e) ;
	
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
		throwError.append("ServiceItem.");
		throwError.append("findItem(String environment: ");
		throwError.append("String item, Connection conn)");
		throw new Exception(throwError.toString());
	}
	return inObject;
}

/**
 * Determine what needs to be done to Add a record for the new item Process
 *      File: MSPWITRS
 */
	public static void addNewItem(String environment, 
								 Vector incomingParms) // Send in UpdItem
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		//ConnectionStack5 cn5 = new ConnectionStack5();
		Connection conn = null; // New Lawson Box - Lawson Database
		try {
			// 1/25/12 TWalton - Change to use ServiceConnection
			//conn = ConnectionStack5.getConnection();
			conn = ServiceConnection.getConnectionStack12();
			
			// Process Anything that needs to be processed within the Process Method
			addNewItem(environment, incomingParms, conn);
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the New Item. " + e) ;
		// return connection.
		} finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack12(conn);
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceItem.addNewItem(String: Environment, Vector)");
			throw new Exception(throwError.toString());
		}
		return;
	}

/**
 * Use to Add New item File:
 *       MSPWITRS -- Detail File
 */
private static void addNewItem(String environment,
							   Vector incomingParms,  // UpdItem
							   Connection conn)
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	// 1/26/12 TWALTON - Change to use alternative version of Insert
	//		using preparedStatement
	//Statement updIt = null;
	PreparedStatement   insertIt    = null;
	try { 
		String sqlString = "";
		// Test to see if it is already in the File.  IF NOT, then Add It if yes then Update it
		UpdItem ui = (UpdItem) incomingParms.elementAt(0);
		if (!ui.getItem().trim().equals(""))
		{
		  try {
			    sqlString = buildSqlStatement(environment, "insertNewItem", incomingParms);

			    DateTime dt = UtilityDateTime.getSystemDate();
			    
			    // 10/4/12 TWalton - Adjusted code to clean it up,  and reflect only the need fields in the file structure
			    
			    insertIt = conn.prepareStatement(sqlString);
				insertIt.setInt(1, 100); // Company
				insertIt.setString(2, ui.getItem().trim()); // Item Number
				insertIt.setString(3, ui.getGroup().trim()); // Item Type
				insertIt.setString(4, ""); // Replaced Item
				insertIt.setString(5, ""); // Sample Order Description
				insertIt.setInt(6, 0); // Initiated Date
				insertIt.setInt(7, new Integer(dt.getDateFormatyyyyMMdd()).intValue()); // Date
				insertIt.setInt(8, new Integer(dt.getTimeFormathhmmss()).intValue()); // Time
				insertIt.setString(9, ui.getUpdateUser()); // User
				insertIt.setString(10, ui.getCaseUPC().trim()); // Case UPC
				insertIt.setString(11, ui.getLabelUPC().trim()); // Label UPC
				insertIt.setString(12, ""); // Carrier (Dog Bone) UPC
				insertIt.setString(13, ""); // Wrap UPC 
				insertIt.setString(14, ui.getPalletGTIN().trim()); // Pallet GTIN
				insertIt.setString(15, ui.getManufacturer().trim()); // Manufacturer
				insertIt.setString(16, ""); // Sales Person
				insertIt.setInt(17, 0); // Customer Service Team
				insertIt.setBigDecimal(18, new BigDecimal(ui.getLength().trim())); // Length 
				insertIt.setBigDecimal(19, new BigDecimal(ui.getWidth().trim())); // Width
				insertIt.setBigDecimal(20, new BigDecimal(ui.getHeight().trim())); // Height
				insertIt.setString(21, ""); // Pause email
				
				insertIt.executeUpdate();
			    
		    } catch (Exception e) {
				throwError.append(" error occured executing an sql statement to insert a New Item Record. " + e);
			}
	    }// End of if you have an item number
	} catch(Exception e)
	{
		throwError.append(" Problem Updating the New Item Records. " + e) ;
		// return Prepared Statement
	} finally {
		try {
			if (insertIt != null)
				insertIt.close();
		} catch (Exception el) {
			el.printStackTrace();
		}
	}
	// return data.
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceItem.addNewItem(");
		throwError.append("String:Environment,Vector, Connection)");
		throw new Exception(throwError.toString());
	}
	return;
}

/**
 * @author twalto.
 * Return a Vector of Item/Whse Records.
 *         Only values filled in will be Item and Shelf Life
 */

public static Vector findShelfLife(Vector inValues)
throws Exception
{			
	StringBuffer throwError  = new StringBuffer();
	Vector  returnValue 	 = new Vector();
	Connection   conn        = null;
	
	try {
		// 1/25/12 TWalton - Change to use ServiceConnection
		//conn = com.treetop.utilities.ConnectionStack7.getConnection();
		conn = ServiceConnection.getConnectionStack12();

		returnValue = findShelfLife(inValues, conn);
		
	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
		if (conn != null)
		{
			try {
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceItem.");
		throwError.append("findShelfLife(");
		throwError.append("Vector). ");
		
		throw new Exception(throwError.toString());
	}
	
	return returnValue;
}

/**
 *  @author twalto.
 * Return a Vector of Item/Whse Records.
 *         Only values filled in will be Item and Shelf Life
 */

private static Vector findShelfLife(Vector inValues, 
							        Connection conn)
throws Exception
{
	StringBuffer            throwError         = new StringBuffer();
	ResultSet               rs                 = null;
	Statement               listThem           = null;
	Vector<ItemWarehouse>   shelfLifeList      = new Vector<ItemWarehouse>();
	try {
		try {
			
			String sql = new String();
			sql = buildSqlStatement("PRD", "listShelfLife", inValues);
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
					 shelfLifeList.addElement(loadFieldsItemWarehouse("listShelfLife", rs));
				 }
				
	     	 } catch(Exception e)
			 {
				throwError.append(" Error occured while building class from sql statement. " + e);
			 } 		
		 }

	} catch (Exception e)
	{
		throwError.append(e);
	}
	
	finally {
		
	   if (rs != null)
	   {
		   try {
			  rs.close();
			} catch(Exception el){
				el.printStackTrace();
			}
	    }
	   if (listThem != null)
	   {
		   try {
			  listThem.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}

	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.treetop.Services.");
		throwError.append("ServiceItem.");
		throwError.append("findShelfLife(");
		throwError.append("Vector, conn). ");
		
		throw new Exception(throwError.toString());		
	}
	return shelfLifeList;
}

/**
 * Use retrieve the next available numeric value for use in 
 *   Building default UPC and GTIN codes for the new item Screen
 *   
 *   Return 1 if there is a problem
 *   -------------------------------------------------------------
 *   Method Created 6/27/12 TWalton
 */
   public static String nextUPCNumber(String environment, String manufacturer)
	{
	   int nextNumber = 1;
	   Connection conn = null; // New Lawson Box - Lawson Database
	   Statement findIt = null;
	   ResultSet rs = null;
	   try { 
		   if (environment == null || environment.trim().equals(""))
				environment = "PRD";
		   
			Vector parmClass = new Vector();
			parmClass.addElement(manufacturer);
			String sqlString = buildSqlStatement(environment, "findNextNumberforMFG", parmClass);
			
			conn = ServiceConnection.getConnectionStack12();
			
			findIt = conn.createStatement();
			rs = findIt.executeQuery(sqlString);
			
			if (rs.next())
			{
				String caseUPC = rs.getString("MSWCSE");
				if (caseUPC.length() == 12)
				{
					String oldNumber = caseUPC.substring(8, 11);
					nextNumber = new Integer(oldNumber).intValue();
					nextNumber++;
				}
			}
		   
	   } catch(Exception e){
			// if Problem, the return default is still 1
		   System.out.println("Stop for Testing");
		} finally {
			try {
				if (conn != null)
					ServiceConnection.returnConnectionStack12(conn);
				if (rs != null)
					rs.close();
				if (findIt != null)
					findIt.close();
			} catch (Exception el) {
				el.printStackTrace();
			}
		}
		return (new Integer(nextNumber).toString());
	}

   /**
    *   Method Created 10/26/12 TWalton
    *   Build a Drop Down List of Items
    *   Narrow down the listing by Item Type - or other Criteria
    *   
    * @param CommonRequestBean inBean.environment
    * @param CommonRequestBean inBean.companyNumber
    * @param CommonRequestBean inBean.IdLevel1 = ItemType
    * @param CommonRequestBean inBean.IdLevel2 = Item Responsible (optional)
    * @param CommonRequestBean inBean.IdLevel3 = Item Status (optional)
    * 
    * @return Vector - of DropDownSingle 
    * 
 */
public static Vector dropDownItemsByType(CommonRequestBean inBean)
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	if (inBean == null)
		throwError.append("Bean is Null");
	if (inBean.getEnvironment().trim().equals(""))
		throwError.append("Environment MUST be sent in");
	if (inBean.getCompanyNumber().trim().equals(""))
		throwError.append("Company Number MUST be sent in");
	if (inBean.getIdLevel1().trim().equals(""))
		throwError.append("Item Type MUST be sent in");
	
	if (!throwError.toString().equals("")) {
		throwError.append("Error @ com.treetop.services.");
		throwError.append("ServiceItem.");
		throwError.append("dropDownItemsByType(CommonRequestBean inBean: ");
		throw new Exception(throwError.toString());
	}
	
	Vector ddit = new Vector();
	Connection conn = null;
	try {
		// get a connection to be sent to find methods
		conn = ServiceConnection.getConnectionStack12();
		ddit = dropDownItemsByType(inBean, conn);
	} catch (Exception e)
	{
		// Don't really need to worry about any exceptions
	}
	finally {
		if (conn != null)
		{
			try
			{
			   ServiceConnection.returnConnectionStack12(conn);
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// return value
	return ddit;
}

/**
 * Method Created 11/7/08 TWalton
 *   // Use to control the information retrieval
 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
 * @return Vector - of DropDownSingle
 */
private static Vector dropDownItemGroup(Vector inValues, 
									   	   Connection conn)
{
	Vector ddit = new Vector();
	ResultSet rs = null;
	PreparedStatement listThem = null;
	try
	{
		try {
			   // Get the list of Item Type Values - Along with Descriptions
		   listThem = conn.prepareStatement(buildSqlStatement("PRD", (String) inValues.elementAt(0), inValues));
		   rs = listThem.executeQuery();
		 } catch(Exception e)
		 {
		 	System.out.println("error" + e);
		   	//throwError.append("Error occured Retrieving or Executing a sql statement. " + e);
		 }		
		 try
		 {
		 	while (rs.next())
		    {
		 		DropDownSingle dds = loadFieldsDropDownSingle((String) inValues.elementAt(0), rs);
		 		ddit.addElement(dds);
     		}// END of the IF Statement
		 } catch(Exception e)
		 {
			//throwError.append(" Error occured while Building Vector from sql statement. " + e);
		 } 		
	} catch (Exception e)
	{
		//throwError.append(e);
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
	
	return ddit;
}
}

