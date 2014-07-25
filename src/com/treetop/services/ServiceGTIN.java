/*
 * Created on Sep 28, 2005
 *
 * Data files accessed.
 * 		- MSPRUCCN    Resource Master Companion (UCCnet fields).
 * 		- MSPOPARENT  Parent Child Combonation file.
 * 		- MSPHPARENT  Family tree of products.
 * 		- SAPGMFGR	  Manufactuer list.
 * 
 */
package com.treetop.services;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import javax.sql.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.BeanGTIN;
import com.treetop.app.gtin.*;
import com.treetop.utilities.ConnectionStack;
import com.treetop.utilities.html.DropDownSingle;

/**
 * @author thaile .
 *
 * Services class to obtain and return data 
 * to business objects.
 * Globel Trade Item Number Services Object.
 */
public class ServiceGTIN extends BaseService {

	//public static final String prisme01 = "PRISME01.";
	//public static final String dblib = "DBLIB.";
	//public static final String thaile = "THAILE.";
	public static final String dbprd  = "DBPRD.";
	public static final String library = "M3DJDPRD.";
//	public static final String dbprd  = "DBTST.";
//	public static final String library = "M3DJDTST.";

	/**
	 * Add a Parent Child Gtin entry.
	 * @param UpdTieToChildren (view bean)
	 * @return BeanGtin (business object view bean)
	 * @throws Exception
	 */
	
	public static BeanGTIN addChild(UpdTieToChildren updVb)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanGTIN beanGtin = new BeanGTIN();
		String requestType = "";
		String sqlExists = "";
		String sqlAddChild = "";
		Connection conn = null;
		PreparedStatement updateIt = null;
		ResultSet rs = null;
		
		// verify base class initialization.
		ServiceGTIN sg = new ServiceGTIN();
		
		// edit incoming data prior to add.	
		
		// if primary edits pass contiue update process.
		if (throwError.toString().equals(""))
		{	
			try {
				// Pass along the incoming view bean information. 
				Vector parmClass = new Vector();
				parmClass.addElement(updVb);
				
				// get the sql statements.
				requestType = "ParentChild";
				sqlExists = buildSqlStatement(requestType, parmClass);
				requestType = "addChild";
				sqlAddChild = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append(e);
			}
		}
		
		// get a connection, execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
				conn = ConnectionStack.getConnection();
				//conn = getDBConnection();
				updateIt = conn.prepareStatement(sqlExists);
				rs = updateIt.executeQuery();
				
				if (rs.next())
				{
					throwError.append(" Unable to add the ");
					throwError.append("GTIN Parent Child. The ");
					throwError.append("combonation is already defined.");	
				} else
				{
					updateIt = conn.prepareStatement(sqlAddChild);
					updateIt.executeUpdate();
				}
			} catch(Exception e)
			{
				throwError.append("error occured executing a ");
				throwError.append("add sql statement. " + e);
				
			// return connection.
			} finally {
				if (conn != null)
				{
					try {
						ConnectionStack.returnConnection(conn);
						//conn.close();
						rs.close();
						updateIt.close();
					} catch(Exception el){
						el.printStackTrace();
					}
				}
			}
		}
		
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("addChild(UpdTieToChildren)");
			throw new Exception(throwError.toString());
		}
		
		return beanGtin;
	}


	/**
	 * Add GTIN file data on the Enterprise data base.
	 * @param UpdGTIN fromVb
	 * @return
	 * @throws Exception
	 */
	public static void addGTIN(UpdGTIN updVb)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String requestType = "";
		String sqlAddGtin = "";
		Connection conn = null;
		Statement addIt = null;
		
		// verify base class initialization.
		ServiceGTIN gt = new ServiceGTIN();
		
		// edit incoming data prior to add.	
		//throwError.append(editData(updVb));

		try
		{
		// if primary edits pass contiue update process.
		if (throwError.toString().equals(""))
		{	
			try {
				// Pass along the incoming view bean information. 
				Vector parmClass = new Vector();
				parmClass.addElement(updVb);
				
				// get the sql statement.
				requestType = "addGtin";
				sqlAddGtin = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append(e);
			}
		}
		
		// get a connection, execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
				conn = ConnectionStack.getConnection();
				addIt = conn.createStatement();
				addIt.executeUpdate(sqlAddGtin);
			} catch(Exception e)
			{
				throwError.append("error occured executing a ");
				throwError.append("add sql statement. " + e);
				
			// return connection.
			} 
		}
		}catch(Exception e)
		{
			
		}finally {
			try {
				if (addIt != null)
					addIt.close();
				if (conn != null)
					ConnectionStack.returnConnection(conn);
			} catch(Exception el){
					el.printStackTrace();
			}
		}
		
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("addGTIN(UpdGTIN)");
			throw new Exception(throwError.toString());
		}
		return;
		
	}
	
	
	/**
	 * Build an sql statement.
	 * @param InqReserveNumbers
	 * @return sql string
	 * @throws Exception
	 */

	private static String buildSqlStatement(String inRequestType,
											Vector requestClass)
		throws Exception 
	{
		StringBuffer sqlString = new StringBuffer();
		String whereClause = "";
		StringBuffer throwError = new StringBuffer();
		String thisDropDown = "";
		
		
		try { // A GTIN record form file MSPRUCCN.
			
			if (inRequestType.equals("uccNetOnly"))
			{
				// cast the incoming parameter class.
				String gtinNbr = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT MSRRCD, MSRSTS, MSRGTN, MSRSDS, ");
				sqlString.append("MSRHLV, MSRBRD, MSRLDS, MSRPUB, MSRBSU, MSRORU ");
				sqlString.append(" FROM " + dbprd + "MSPRUCCN ");
				sqlString.append("WHERE MSRGTN = '");
				sqlString.append(gtinNbr + "' ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement ");
			throwError.append("for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // All GTIN records that are parents.
			
			if (inRequestType.equals("findAllParents"))
			{
				
				// build the sql statement.
				sqlString.append("SELECT * FROM " + dbprd + "MSPOPARENT ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement ");
			throwError.append("for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // A GTIN record from file MSPRUCCN.
			
			if (inRequestType.equals("gtinDetail"))
			{
				// cast the incoming parameter class.
				String gtinNbr = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT * ");
				sqlString.append(" FROM " + dbprd + "MSPRUCCN ");
				
				// simple where clause
				sqlString.append("WHERE MSRGTN = '");
				sqlString.append(gtinNbr + "' ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement ");
			throwError.append("for requestType " + inRequestType + ". " + e);
		}
		
		
			
		try { // List for GS1 Company Prefix Dropdown
			
			if (inRequestType.equals("dropdown")) 
			{
				thisDropDown = (String) requestClass.elementAt(0);
			
				if (thisDropDown.equals("GS1CompanyPrefix")) 
				{
					sqlString.append("Select SAGCDE, SAGNME FROM ");
					sqlString.append(dbprd + "SAPGMFGR ");
					sqlString.append("WHERE SAGSTS <> 'D ' ");
					sqlString.append("ORDER BY SAGNME ");
				}
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for drop down )");
			throwError.append(thisDropDown);
			throwError.append(") vector request. ");
		}
		
		
		
		try { // List of MSPRUCCN records that match selection criteria.
			if(inRequestType.equals("gtinListPage"))
			{
				// cast the incoming parameter class.
				InqGTIN inqGtin = (InqGTIN) requestClass.elementAt(0);
				Vector treeList = (Vector) requestClass.elementAt(1);
				
				// build the sql statement.
				sqlString.append("SELECT * FROM ");
				sqlString.append(dbprd + "MSPRUCCN ");
				sqlString.append("LEFT OUTER JOIN " + dbprd + "MSPWITRS ");
				sqlString.append(" ON MSRGTN = MSWPGT ");
				sqlString.append("LEFT OUTER JOIN " + library + "MITMAS ");
				sqlString.append(" ON MSWRSC = MMITNO ");
				String sqlWhere = "";
				
				sqlWhere = sqlWhere + sqlWhereBrand(inqGtin.getInqBrandName(), sqlWhere);

				if (inqGtin.getInqShowTree() == null ||
					inqGtin.getInqShowTree().trim().equals(""))
					sqlWhere = sqlWhere + 
					sqlWhereGtinNumber(inqGtin.getInqGTIN(), sqlWhere);
				
				sqlWhere = sqlWhere + sqlWhereResource(inqGtin.getInqResource(), sqlWhere);
				sqlWhere = sqlWhere + sqlWhereTiud(inqGtin.getInqTIUD(), sqlWhere);
				sqlWhere = sqlWhere + sqlWhereUpcCode(inqGtin.getInqUPCCode(), sqlWhere);
				sqlWhere = sqlWhere + sqlWhereGtinLongDescription(inqGtin.getInqDescriptionLong(), sqlWhere);
				sqlWhere = sqlWhere + sqlWherePublish(inqGtin.getInqPublish(), sqlWhere);
				sqlWhere = sqlWhere + sqlWhereShowTree(inqGtin.getInqShowTree(), sqlWhere, treeList);
				
				if (inqGtin.getInqPublishType() != null &&
					!inqGtin.getInqPublishType().trim().equals(""))
					sqlWhere = sqlWhere + "(MSRSTS = '" + inqGtin.getInqPublishType() + "') ";
				
				if (!sqlWhere.equals(""))
						sqlString.append(" WHERE " + sqlWhere);
				
				String sqlOrderBy = sqlOrderBy(inqGtin);
				sqlString.append(sqlOrderBy);
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for the GTIN List Page.");
			throwError.append(" requestType " + inRequestType + ". " + e);
		}
			
		try { // Get a specific Parent Child.
			if (inRequestType.equals("ParentChild"))
			{
				// cast the incoming parameter class.
				UpdTieToChildren fromVb = (UpdTieToChildren) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT * FROM ");
				sqlString.append(dbprd + "MSPOPARENT ");
				sqlString.append("LEFT OUTER JOIN " + dbprd + "MSPRUCCN");
				sqlString.append(" ON MSOCHILD = MSRGTN ");
				sqlString.append("WHERE MSOPARENT = '");
				sqlString.append(fromVb.getParentGTIN() + "' ");
				sqlString.append("AND MSOCHILD = '");
				sqlString.append(fromVb.getChildGTIN() + "' ");
			}
		} catch(Exception e) {
			throwError.append(" Error building sql statement ");
			throwError.append("for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // Get the tree for a specific parent
			if (inRequestType.equals("viewOfParent"))
			{
				// cast the incoming parameter class.
				String gtinNbr = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT * ");
				sqlString.append("FROM " + dbprd + "MSPHPARENT ");
				sqlString.append("LEFT OUTER JOIN " + dbprd );
				sqlString.append("MSPRUCCN ON MSHCHILD = MSRGTN ");
				sqlString.append("WHERE MSHPARENT = '");
				sqlString.append(gtinNbr + "' ");
				sqlString.append(" ORDER BY MSHSEQ ");
			}
		} catch(Exception e) {
			throwError.append(" Error building sql statement ");
			throwError.append("for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // Get children of parent and UCCNET info.
			if (inRequestType.equals("parentChildren") ||
				inRequestType.equals("viewChildrenOfParent"))
			{
				String gtinNbr = "";
				// cast the incoming parameter class.
				if (inRequestType.equals("parentChildren"))
				{
					InqGTIN fromVb = (InqGTIN) requestClass.elementAt(0);
					gtinNbr = fromVb.getInqGTIN();
				}
					
				if (inRequestType.equals("viewChildrenOfParent"))
					gtinNbr = (String) requestClass.elementAt(0);
				
				
				// build the sql statement.
				sqlString.append("SELECT MSOSTS, MSOPARENT, ");
				sqlString.append("MSOCHILD, MSOSEQ, MSOQTY, ");
				sqlString.append("MSOUDT, MSOUTM, MSOUSR, ");
				sqlString.append("parent.MSRGTN AS parentMSRGTN, ");
				sqlString.append("parent.MSRHLV AS parentMSRHLV, ");
				sqlString.append("parent.MSRSDS AS parentMSRSDS, ");
				sqlString.append("parent.MSRRCD AS parentMSRRCD, ");
				sqlString.append("parent.MSRSTS AS parentMSRSTS, ");
				sqlString.append("parent.MSRBRD AS parentMSRBRD, ");
				sqlString.append("parent.MSRLDS AS parentMSRLDS, ");
				sqlString.append("parent.MSRPUB AS parentMSRPUB, ");
				sqlString.append("parent.MSRBSU AS parentMSRBSU, ");
				sqlString.append("child.MSRGTN AS childMSRGTN, ");
				sqlString.append("child.MSRHLV AS childMSRHLV, ");
				sqlString.append("child.MSRSDS AS childMSRSDS, ");
				sqlString.append("child.MSRRCD AS childMSRRCD, ");
				sqlString.append("child.MSRSTS AS childMSRSTS, ");
				sqlString.append("child.MSRBRD AS childMSRBRD, ");
				sqlString.append("child.MSRLDS AS childMSRLDS, ");
				sqlString.append("child.MSRPUB AS childMSRPUB, ");
				sqlString.append("child.MSRBSU AS childMSRBSU ");
				sqlString.append("FROM " + dbprd + "MSPOPARENT ");
				sqlString.append("LEFT OUTER JOIN " + dbprd);
				sqlString.append("MSPRUCCN parent ON MSOPARENT = parent.MSRGTN ");
				sqlString.append("LEFT OUTER JOIN " + dbprd);
				sqlString.append("MSPRUCCN child ON MSOCHILD = child.MSRGTN ");
				sqlString.append("WHERE MSOPARENT = '");
				sqlString.append(gtinNbr + "' ");
				sqlString.append(" ORDER BY MSOPARENT, MSOSEQ ");
			}
		} catch(Exception e) {
		throwError.append(" Error building sql statement ");
		throwError.append("for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // Get View Parents of gtin number.
			if (inRequestType.equals("parentsOfChild"))
			{
				// cast the incoming parameter class.
				String parent = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT * ");
				sqlString.append("FROM " + dbprd + "MSPOPARENT ");
				sqlString.append("WHERE MSOCHILD = '");
				sqlString.append(parent + "' ");
			}
		} catch(Exception e) {
			throwError.append(" Error building sql statement ");
			throwError.append("for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // Add a New Parent Child Entry.
			if (inRequestType.equals("addChild"))
			{
				// cast the incoming parameter class.
				UpdTieToChildren fromVb = (UpdTieToChildren) requestClass.elementAt(0);
				
				// get current system date and time.
				String[] sysDates = SystemDate.getSystemDate();
				
				// build the sql statement.
				sqlString.append("INSERT  INTO  " + dbprd + "MSPOPARENT ");
				sqlString.append("VALUES(");
				sqlString.append("'  ', "); //Status
				sqlString.append("'" + fromVb.getParentGTIN().trim() + "', ");
				sqlString.append("'" + fromVb.getChildGTIN().trim() + "', ");
				sqlString.append( new Integer(fromVb.getChildSequence()).intValue() + ", ");
				sqlString.append( new BigDecimal(fromVb.getChildQuantity()) + ", ");
				sqlString.append( new Integer(sysDates[3]).intValue() + ", ");
				sqlString.append( new Integer(sysDates[0]).intValue() + ", ");
				sqlString.append("'" + fromVb.getUserProfile().trim() + "') ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType. " + inRequestType + ". " + e);
		}
		
		
		
		try {// Add a View entry of a Parent and Child.
			if (inRequestType.equals("insertViewRecord"))
			{
				// cast the incoming parameter class.
				GTINView fromVb = (GTINView) requestClass.elementAt(0);
				
				// get current system date and time.
				String[] sysDates = SystemDate.getSystemDate();
				
				// build the sql statement.
				sqlString.append("INSERT  INTO  " + dbprd + "MSPHPARENT ");
				sqlString.append("VALUES(");
				sqlString.append("' ', "); //Status
				sqlString.append("'" + fromVb.getGtinNumber().trim() + "', ");
				sqlString.append( new Integer(fromVb.getViewSeq()).intValue() + ", ");
				sqlString.append( new Integer(fromVb.getViewLevel()).intValue() + ", ");
				sqlString.append("'" + fromVb.getViewChild().trim() + "', ");
				sqlString.append( new Integer(sysDates[3]).intValue() + ", ");
				sqlString.append( new Integer(sysDates[0]).intValue() + ", ");
				//sqlString.append("'" + fromVb.trim() + "') ");
				sqlString.append("'  ') ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType. " + inRequestType + ". " + e);
		}



		try { // Add a Gtin Detail Entry.
			if (inRequestType.equals("addGtin"))
			{
				// try routine variable.
				String editDate = "";
				
				// cast the incoming parameter class.
				UpdGTIN fromVb = (UpdGTIN) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("INSERT  INTO  " + dbprd + "MSPRUCCN ");
				sqlString.append("VALUES(");
				sqlString.append("'MR', "); //MSRRCD 
				sqlString.append("' ', "); //MSRSTS
				sqlString.append("'" + fromVb.getTargetMarketCountryCode().trim() + "', "); //MSRCNY
				sqlString.append("'" + fromVb.getCatalogueItemState().trim() + "', "); //MSRCIS
				sqlString.append("'" + fromVb.getGtinNumber().trim() + "', "); //MSRGTN
				sqlString.append("'" + fromVb.getInformationProvider().trim() + "', "); //MSRBRO
				sqlString.append("'" + fromVb.getInformationProviderName().trim() + "', "); //MSRBRN
				sqlString.append("'" + fromVb.getShortDescription().trim() + "', "); //MSRSDS
				sqlString.append("'" + fromVb.getEanUCCType().trim() + "', "); //MSRUCT
				sqlString.append("'" + fromVb.getEanUCCCode().trim() + "', "); //MSRUCC
				sqlString.append("'" + fromVb.getTiud().trim() + "', "); //MSRHLV
				sqlString.append("'" + fromVb.getBrandName().trim() + "', "); //MSRBRD
				sqlString.append("'" + fromVb.getIsInformationPrivate().trim() + "', "); //MSRPVT
				sqlString.append("'" + fromVb.getIsRecyclable().trim() + "', "); //MSRCNU
				sqlString.append("'" + fromVb.getClassificationCategoryCode().trim() + "', "); //MSRCCC
				sqlString.append(new BigDecimal(fromVb.getNetContent().trim()) + ", "); //MSRNCT
				sqlString.append("'" + fromVb.getNetContentUOM().trim() + "', "); //MSRCUM
				sqlString.append(new BigDecimal(fromVb.getHeight().trim()) + ", "); //MSRHGT
				sqlString.append(new BigDecimal(fromVb.getWidth().trim()) + ", "); //MSRWTH
				sqlString.append(new BigDecimal(fromVb.getDepth().trim()) + ", "); //MSRDTH
				sqlString.append("'" + fromVb.getLinearUOM().trim() + "', "); //MSRLUM
				sqlString.append(new BigDecimal(fromVb.getNetWeight().trim()) + ", "); //MSRNWG
				sqlString.append(new BigDecimal(fromVb.getGrossWeight().trim()) + ", "); //MSRGWG
				sqlString.append("'" + fromVb.getWeightUOM().trim() + "', "); //MSRWUM
				sqlString.append(new BigDecimal(fromVb.getVolume().trim()) + ", "); //MSRVOL
				sqlString.append("'" + fromVb.getVolumeUOM().trim() + "', "); //MSRVUM
				sqlString.append( new Integer(fromVb.getQtyNextLowerLevelTradeItem().trim()) + ", "); //MSRNLL
				sqlString.append("'" + fromVb.isOrderableUnit.trim() + "', "); //MSRORU
				
				// edit Effective Date.
				editDate = "0";
				if (!fromVb.getEffectiveDate().equals("0") &&
					!fromVb.getEffectiveDate().trim().equals(""))
				{
					String[] ckDates = CheckDate.validateDate(fromVb.getEffectiveDate().trim());
					if (ckDates[6].equals(""))
						editDate = ckDates[4];
				}
				sqlString.append(new Integer(editDate) + ","); //MSREDT	
				
				sqlString.append("'" + fromVb.getLongDescription().trim() + "', "); //MSRLDS
				sqlString.append("'" + fromVb.getAdditionalTradeItemDescription().trim() + "', "); //MSRADS
				sqlString.append(new Integer(fromVb.getQtyChildren().trim()) + ", "); //MSRCQT
				sqlString.append("'" + fromVb.getAdditionalClassificationAgencyName().trim() + "', "); //MSRACA
				sqlString.append("'" + fromVb.getAdditionalClassCategoryCode().trim() + "', "); //MSRACC
				sqlString.append("'" + fromVb.getAdditionalClassCategoryDesc().trim() + "', "); //MSRACD
				sqlString.append("'" + fromVb.getFunctionalName().trim() + "', "); //MSRFUN
				sqlString.append("'" + fromVb.getIsBaseUnit().trim() + "', "); //MSRBSU
				sqlString.append("'" + fromVb.getIsDispatchUnit().trim() + "', "); //MSRDSU
				sqlString.append("'" + fromVb.getIsInvoiceUnit().trim() + "', "); //MSRIVU
				sqlString.append("'" + fromVb.getIsVariableUnit().trim() + "', "); //MSRVRU
				sqlString.append("'" + fromVb.getIsRecyclable().trim() + "', "); //MSRPRY
				sqlString.append("'" + fromVb.getIsReturnable().trim() + "', "); //MSRPRT
				sqlString.append("'" + fromVb.getHasExpireDate().trim() + "', "); //MSRPEX
				sqlString.append("'" + fromVb.getHasGreenDot().trim() + "', "); //MSRPGD
				sqlString.append("'" + fromVb.getHasIngredients().trim() + "', "); //MSRPIG
				sqlString.append("'" + fromVb.getCountryOfOrigin().trim() + "', "); //MSRCOR
				
				// edit Trade Item Last Change Date.
				editDate = "0";
				if (!fromVb.getLastChangeDate().equals("0") &&
					!fromVb.getLastChangeDate().trim().equals(""))
				{
					String[] ckDates = CheckDate.validateDate(fromVb.getLastChangeDate().trim());
					if (ckDates[6].equals(""))
						editDate = ckDates[4];
				}
				sqlString.append(new Integer(editDate.trim()) + ", "); //MSRLCD
				
				// edit Trade Item Last Publication Date.
				editDate = "0";
				if (!fromVb.getPublicationDate().equals("0") &&
					!fromVb.getPublicationDate().trim().equals(""))
				{
					String[] ckDates = CheckDate.validateDate(fromVb.getPublicationDate().trim());
					if (ckDates[6].equals(""))
						editDate = ckDates[4];
				}
				sqlString.append(new Integer(editDate.trim()) + ", "); //MSRPBD
				
				// edit Item Start Availability Date.
				editDate = "0";
				if (!fromVb.getStartAvailabilityDate().equals("0") &&
					!fromVb.getStartAvailabilityDate().trim().equals(""))
				{
					String[] ckDates = CheckDate.validateDate(fromVb.getStartAvailabilityDate().trim());
					if (ckDates[6].equals(""))
						editDate = ckDates[4];
				}
				sqlString.append(new Integer(editDate.trim()) + ", "); //MSRSAD
				
				sqlString.append("'" + fromVb.getHasBatchNumber().trim() + "', "); //MSRBTC
				sqlString.append("'" + fromVb.getIsNonSoldReturnable().trim() + "', "); //MSRRET
				sqlString.append("'" + fromVb.getIsItemRecyclable().trim() + "', "); //MSRRCY
				sqlString.append("'" + fromVb.getIsNetContentDeclarationIndicated().trim() + "', "); //MSRNCI
				sqlString.append("'" + fromVb.getDeliveryMethodIndicator().trim() + "', "); //MSRDMI
				sqlString.append("'" + fromVb.getBarcodeSymbology().trim() + "', "); //MSRBSD
				sqlString.append("'" + fromVb.getUserProfile().trim() + "', "); //MSRUSR
				sqlString.append("'" + fromVb.getLastUpdateWorkstation().trim() + "', "); //MSRWSN
				
				// get current system date and time.
				String[] sysDates = SystemDate.getSystemDate();
				sqlString.append(new Integer(sysDates[3]).intValue() + ", "); //MSRDTE
				sqlString.append(new Integer(sysDates[0]).intValue() + ", "); //MSRTME
				
				sqlString.append("'" + fromVb.getSubBrand().trim() + "', "); //MSRSBR
				sqlString.append("'" + fromVb.getVariant().trim() + "', "); //MSRVAR
				sqlString.append("'" + fromVb.getCouponFamilyCode().trim() + "', "); //MSRCFC
				sqlString.append(new Integer(fromVb.getQtyCompleteLayers().trim()) + ", "); //MSRHI
				sqlString.append(new Integer(fromVb.getQtyItemsPerCompleteLayer().trim()) + ", "); //MSRTI
				sqlString.append("'" + fromVb.getTargetMarketCode().trim() + "', "); //MSRTGC
				sqlString.append("'" + fromVb.getTargetMarketSubDivCode().trim() + "', "); //MSRMSB
				
				// edit Trade Item Effective Date.
				editDate = "0";
				if (!fromVb.getTradeItemEffectiveDate().equals("0") &&
					!fromVb.getTradeItemEffectiveDate().trim().equals(""))
				{
					String[] ckDates = CheckDate.validateDate(fromVb.getTradeItemEffectiveDate().trim());
					if (ckDates[6].equals(""))
						editDate = ckDates[4];
				}
				sqlString.append(new Integer(editDate) + ", "); //MSRTED
				
				sqlString.append(new BigDecimal(fromVb.getQtyChildrenUnits().trim()) + ", "); //MSRCLQ
				sqlString.append("'" + fromVb.getClassificationCategoryDefinition().trim() + "', "); //MSRCCD
				sqlString.append("'" + fromVb.getClassificationCategoryName().trim() + "', "); //MSRCCN
				sqlString.append("'" + fromVb.getPublishToUCCNet().trim() + "' )"); //MSRPUB
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement"); 
			throwError.append(" for requestType " + inRequestType + ". " + e);
		}

		
		
		try { // Delete Parent Children combonations.
			if (inRequestType.equals("deleteChildren"))
			{
				// cast the incoming parameter class.
				String parent = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("DELETE FROM " + dbprd + "MSPOPARENT ");
				sqlString.append("WHERE MSOPARENT = '" + parent.trim());
				sqlString.append("' ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // Delete View of Gtin.
			if (inRequestType.equals("deleteView"))
			{
				// cast the incoming parameter class.
				String gtin = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("DELETE FROM " + dbprd + "MSPHPARENT ");
				sqlString.append("WHERE MSHPARENT = '" + gtin.trim());
				sqlString.append("' ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // Delete All Views.
			if (inRequestType.equals("deleteAllViews"))
			{
				// cast the incoming parameter class.
				String gtin = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("DELETE FROM " + dbprd + "MSPHPARENT ");
				sqlString.append("WHERE MSHSTS = '  '");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // Update a Gtin detail Entry.  
			if (inRequestType.equals("updateGtinDetail"))
			{
				// cast the incoming parameter class.
				UpdGTIN fromVb = (UpdGTIN) requestClass.elementAt(0);
				
				String editDate = "";

				// build the sql statement.
				sqlString.append("UPDATE " + dbprd + "MSPRUCCN ");
				//sqlString.append(" SET MSRRCD = 'MR', ");
				sqlString.append(" SET MSRSTS = '" + fromVb.getPublishTypeToUCCNet() + "',");
				sqlString.append(" MSRCNY = '" + fromVb.getTargetMarketCountryCode().trim() + "',");
				sqlString.append(" MSRCIS = '" + fromVb.getCatalogueItemState().trim() + "',");
				sqlString.append(" MSRGTN = '" + fromVb.getGtinNumber().trim() + "',");
				sqlString.append(" MSRBRO = '" + fromVb.getInformationProvider().trim() + "',");
				sqlString.append(" MSRBRN = '" + fromVb.getInformationProviderName().trim() + "',");
				sqlString.append(" MSRSDS = '" + fromVb.getShortDescription().trim() + "',");
				sqlString.append(" MSRUCT = '" + fromVb.getEanUCCType().trim() + "',");
				sqlString.append(" MSRUCC = '" + fromVb.getEanUCCCode().trim() + "',");
				sqlString.append(" MSRHLV = '" + fromVb.getTiud().trim() + "',");
				sqlString.append(" MSRBRD = '" + fromVb.getBrandName().trim() + "',");
				sqlString.append(" MSRPVT = '" + fromVb.getIsInformationPrivate().trim() + "',");
				sqlString.append(" MSRCNU = '" + fromVb.getIsConsumerUnit().trim() + "',");
				sqlString.append(" MSRCCC = '" + fromVb.getClassificationCategoryCode().trim() + "',");
				sqlString.append(" MSRNCT = " + new BigDecimal(fromVb.getNetContent().trim()) + ",");
				sqlString.append(" MSRCUM = '" + fromVb.getNetContentUOM().trim() + "',");
				sqlString.append(" MSRHGT = " + new BigDecimal(fromVb.getHeight().trim()) + ",");
				sqlString.append(" MSRWTH = " + new BigDecimal(fromVb.getWidth().trim()) + ",");
				sqlString.append(" MSRDTH = " + new BigDecimal(fromVb.getDepth().trim()) + ",");
				sqlString.append(" MSRLUM = '" + fromVb.getLinearUOM().trim() + "',");
				sqlString.append(" MSRNWG = " + new BigDecimal(fromVb.getNetWeight().trim()) + ",");
				sqlString.append(" MSRGWG = " + new BigDecimal(fromVb.getGrossWeight().trim()) + ",");
				sqlString.append(" MSRWUM = '" + fromVb.getWeightUOM().trim() + "',");
				sqlString.append(" MSRVOL = " + new BigDecimal(fromVb.getVolume().trim()) + ",");
				sqlString.append(" MSRVUM = '" + fromVb.getVolumeUOM().trim() + "',");
				sqlString.append(" MSRNLL = " + new BigDecimal(fromVb.getQtyNextLowerLevelTradeItem().trim()) + ",");
				sqlString.append(" MSRORU = '" + fromVb.getIsOrderableUnit().trim() + "',");
				
				// edit Effective Date.
				editDate = "0";
				if (!fromVb.getEffectiveDate().equals("0") &&
					!fromVb.getEffectiveDate().trim().equals(""))
				{
					String[] ckDates = CheckDate.validateDate(fromVb.getEffectiveDate());
					if (ckDates[6].equals(""))
						editDate = ckDates[4];
				}
				sqlString.append(" MSREDT = " + new Integer(editDate) + ",");				
				
				sqlString.append(" MSRLDS = '" + fromVb.getLongDescription().trim() + "',");
				sqlString.append(" MSRADS = '" + fromVb.getAdditionalTradeItemDescription().trim() + "',");
				sqlString.append(" MSRCQT = " + new BigDecimal(fromVb.getQtyChildren().trim()) + ",");
				sqlString.append(" MSRACA = '" + fromVb.getAdditionalClassificationAgencyName().trim() + "',");
				sqlString.append(" MSRACC = '" + fromVb.getAdditionalClassCategoryCode().trim() + "',");
				sqlString.append(" MSRACD = '" + fromVb.getAdditionalClassCategoryDesc().trim() + "',");
				sqlString.append(" MSRFUN = '" + fromVb.getFunctionalName().trim() + "',");
				sqlString.append(" MSRBSU = '" + fromVb.getIsBaseUnit().trim() + "',");
				sqlString.append(" MSRDSU = '" + fromVb.getIsDispatchUnit().trim() + "',");
				sqlString.append(" MSRIVU = '" + fromVb.getIsInvoiceUnit().trim() + "',");
				sqlString.append(" MSRVRU = '" + fromVb.getIsVariableUnit().trim() + "',");
				sqlString.append(" MSRPRY = '" + fromVb.getIsRecyclable().trim() + "',");
				sqlString.append(" MSRPRT = '" + fromVb.getIsReturnable().trim() + "',");
				sqlString.append(" MSRPEX = '" + fromVb.getHasExpireDate().trim() + "',");
				sqlString.append(" MSRPGD = '" + fromVb.getHasGreenDot().trim() + "',");
				sqlString.append(" MSRPIG = '" + fromVb.getHasIngredients().trim() + "',");
				sqlString.append(" MSRCOR = '" + fromVb.getCountryOfOrigin().trim() + "',");
				
				// edit Trade Item Last Change Date.
				editDate = "0";
				if (!fromVb.getLastChangeDate().equals("0") &&
					!fromVb.getLastChangeDate().trim().equals(""))
				{
					String[] ckDates = CheckDate.validateDate(fromVb.getLastChangeDate());
					if (ckDates[6].equals(""))
						editDate = ckDates[4];
				}
				sqlString.append(" MSRLCD = " + new Integer(editDate) + ",");
				
				// edit Trade Item Last Publication Date.
				editDate = "0";
				if (!fromVb.getPublicationDate().equals("0") &&
					!fromVb.getPublicationDate().trim().equals(""))
				{
					String[] ckDates = CheckDate.validateDate(fromVb.getPublicationDate());
					if (ckDates[6].equals(""))
						editDate = ckDates[4];
				}
				sqlString.append(" MSRPBD = " + new Integer(editDate) + ",");
				
				// edit Item Start Availability Date.
				editDate = "0";
				if (!fromVb.getStartAvailabilityDate().equals("0") &&
					!fromVb.getStartAvailabilityDate().trim().equals(""))
				{
					String[] ckDates = CheckDate.validateDate(fromVb.getStartAvailabilityDate());
					if (ckDates[6].equals(""))
						editDate = ckDates[4];
				}
				sqlString.append(" MSRSAD = " + new Integer(editDate) + ",");
				
				sqlString.append(" MSRBTC = '" + fromVb.getHasBatchNumber().trim() + "',");
				sqlString.append(" MSRRET = '" + fromVb.getIsNonSoldReturnable().trim() + "',");
				sqlString.append(" MSRRCY = '" + fromVb.getIsItemRecyclable().trim() + "',");
				sqlString.append(" MSRNCI = '" + fromVb.getIsNetContentDeclarationIndicated().trim() + "',");
				sqlString.append(" MSRDMI = '" + fromVb.getDeliveryMethodIndicator().trim() + "',");
				sqlString.append(" MSRBSD = '" + fromVb.getBarcodeSymbology().trim() + "',");
				sqlString.append(" MSRUSR = '" + fromVb.getLastUpdateUser().trim() + "',");
				sqlString.append(" MSRWSN = '" + fromVb.getLastUpdateWorkstation().trim() + "',");
				
				// get current system date and time.
				String[] sysDates = SystemDate.getSystemDate();
				sqlString.append(" MSRDTE = " + new Integer(sysDates[3]).intValue() + ",");
				sqlString.append(" MSRTME = " + new Integer(sysDates[0]).intValue() + ",");
				
				sqlString.append(" MSRSBR = '" + fromVb.getSubBrand().trim() + "',");
				sqlString.append(" MSRVAR = '" + fromVb.getVariant().trim() + "',");
				sqlString.append(" MSRCFC = '" + fromVb.getCouponFamilyCode().trim() + "',");
				sqlString.append(" MSRHI  = " + new BigDecimal(fromVb.getQtyCompleteLayers().trim()) + ",");
				sqlString.append(" MSRTI  = " + new BigDecimal(fromVb.getQtyItemsPerCompleteLayer().trim()) + ",");
				sqlString.append(" MSRTGC = '" + fromVb.getTargetMarketCode().trim() + "',");
				sqlString.append(" MSRMSB = '" + fromVb.getTargetMarketSubDivCode().trim() + "',");
				
				// edit Trade Item Effective Date.
				editDate = "0";
				if (!fromVb.getTradeItemEffectiveDate().equals("0") &&
					!fromVb.getTradeItemEffectiveDate().trim().equals(""))
				{
					String[] ckDates = CheckDate.validateDate(fromVb.getTradeItemEffectiveDate());
					if (ckDates[6].equals(""))
						editDate = ckDates[4];
				}
				sqlString.append(" MSRTED = " + new Integer(editDate) + ",");
				
				sqlString.append(" MSRCLQ = " + new BigDecimal(fromVb.getQtyChildrenUnits().trim()) + ",");
				sqlString.append(" MSRCCD = '" + fromVb.getClassificationCategoryDefinition().trim() + "',");
				sqlString.append(" MSRCCN = '" + fromVb.getClassificationCategoryName().trim() + "',");
				sqlString.append(" MSRPUB = '" + fromVb.getPublishToUCCNet().trim() + "' ");
				
				sqlString.append(" WHERE MSRGTN = '" + fromVb.getGtinNumber() + "' ");
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement");
				throwError.append(" for requestType " + inRequestType + ". " + e);
		}
		
		
		
		try { // Gtin family relations for list page request.
			if (inRequestType.equals("familyRelations"))
			{
				// cast the incoming parameter class.
				Vector treeList = (Vector) requestClass.elementAt(0);
				StringBuffer inList = new StringBuffer();
				inList.append("");
				
				for (int x = 0; treeList.size() > x; x++)
				{
					if (inList.length() > 1)
						inList.append(", ");
					String element = (String) treeList.elementAt(x);
					inList.append("'" + element + "'");
				}
				
				
				// build the sql statement.
				sqlString.append("SELECT * ");
				sqlString.append("FROM " + dbprd + "MSPHPARENT ");
				sqlString.append("WHERE (MSHPARENT IN(");
				sqlString.append(inList.toString() + "))");
			}
		} catch(Exception e) {
			throwError.append(" Error building sql statement ");
			throwError.append("for requestType " + inRequestType + ". " + e);
		}


		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGtin.");
			throwError.append("buildSqlStatement(String, Vector)");
			throw new Exception(throwError.toString());
		}

		return sqlString.toString();
	}


	/**
	 * Rebuild (delete/build) all views for file MSPHPARENT.
	 * 
	 * @throws Exception
	 * 
	 *  First delete all View entries in file MSPHPARENT. 
	 * Next obtain all gtins at the highest parent level.
	 * Then build a View for each gtin found. 
	 */
	
	public static void rebuildViews()
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector views = new Vector();
		
		try {
		
			// verify base class initialization.
			ServiceGTIN sg = new ServiceGTIN();
			
			// delete all views from View file.
			deleteAllViews("deleteAllViews");
		
			// get the list of current lowest level parents.
			Vector parents = findAllParents();
			
			// Rebuild the View for each parent found.
			for (int x = 0; parents.size() > x; x++)
			{
				rebuildViews((String) parents.elementAt(x));
			}
			
		} catch(Exception e){
			throwError.append(" Error executing buildViews. ");
		}
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("rebuildViews()");
			throw new Exception(throwError.toString());
		}
	}


	/**
	 * Return all pallet views for a specific gtin number.
	 * @param gtin number
	 * @return Vector of BeanGTIN (views)
	 * @throws Exception
	 * 
	 *  Receive in a gtin number and return all views 
	 * for that gtin under any pallet. 
	 */
	
	public static Vector buildViews(String gtinIn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector views = new Vector();
		
		try {
		
			// verify base class initialization.
			ServiceGTIN sg = new ServiceGTIN();
		
			// get the list of current lowest level parents.
			Vector parents = findParents(gtinIn);
			
			// Get a vector of the views.
			views = findViews(parents);
			
		} catch(Exception e){
			throwError.append(" Error executing buildViews. ");
		}
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("buildViews(gtin:");
			throwError.append(gtinIn + ")");
			throw new Exception(throwError.toString());
		}
		
		return views;
	}
	
	
	/**
	 * return all pallet views for a specific gtin number.
	 * @param gtin number
	 * @return Vector of BeanGTIN (views)
	 * @throws Exception
	 * 
	 *  Receive in a gtin number and return all views 
	 * for that gtin under any pallet. 
	 */
	
	public static Vector rebuildViews(String gtinIn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector views = new Vector();
		
		try {
		
			// verify base class initialization.
			ServiceGTIN sg = new ServiceGTIN();
		
			// get the list of current lowest level parents.
			Vector parents = findParents(gtinIn);
		
			// delete all parent level views for parents.
			for (int x = 0; parents.size() > x; x++)
			{
				deleteViewLevels((String) parents.elementAt(x));
			}
		
			// add all parent level views for parents.
			for (int x = 0; parents.size() > x; x++)
			{
				addViewLevels((String) parents.elementAt(x));
			}
			
			// Get a vector of the views.
			views = findViews(parents);
			
		} catch(Exception e){
			throwError.append(" Error executing rebuildViews. ");
		}
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("rebuildViews(gtin:");
			throwError.append(gtinIn + ")");
			throw new Exception(throwError.toString());
		}
		
		return views;
	}
	
	
	/**
	 * Delete all parent child combonations for 
	 * the incoming parent.
	 * @param String - Parent Value.
	 * @throws Exception
	 */
	public static void deleteChildren(String parentIn)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String requestType = "";
		String sqlDeleteChildren = "";
		Connection conn = null;
		PreparedStatement deleteIt = null;
		
		// verify base class initialization.
		ServiceGTIN sg = new ServiceGTIN();
		
		// edit incoming data prior to add.
		if (parentIn == null || parentIn.equals(""))
		{
			throwError.append(" Parent value of null or ");
			throwError.append("empty is invalid. ");
		}
		
		// if primary edits pass contiue update process.
		if (throwError.toString().equals(""))
		{	
			try {
				// Pass along the incoming parameter. 
				Vector parmClass = new Vector();
				parmClass.addElement(parentIn);
				
				// get the sql statements.
				requestType = "deleteChildren";
				sqlDeleteChildren = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append(" Error getting sql statement. ");
				throwError.append(e);
			}
		}
		
		// get a connection, execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
				conn = ConnectionStack.getConnection();
				deleteIt = conn.prepareStatement(sqlDeleteChildren);
				deleteIt.executeUpdate();
			} catch(Exception e)
			{
				throwError.append("error occured executing a ");
				throwError.append("delete sql statement. " + e);
				
			// return connection.
			} finally {
				if (conn != null)
				{
					try
					{
					   ConnectionStack.returnConnection(conn);
					} catch(Exception el){
						el.printStackTrace();
					}
				}
				
				if (deleteIt != null)
					deleteIt.close();
			}
		}
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("deletChildren(parent:");
			throwError.append(parentIn + ")");
			throw new Exception(throwError.toString());
		}
		return;	
	}
	
	
	/**
	 * Delete all entries where the incoming gtin 
	 * is the parent entry in the View file.
	 * @param String - Parent Value.
	 * @throws Exception
	 */
	public static void deleteViewLevels(String gtinIn)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String requestType = "";
		String sqlDeleteView = "";
		Connection conn = null;
		PreparedStatement deleteIt = null;
		
		// verify base class initialization.
		ServiceGTIN sg = new ServiceGTIN();
		
		// edit incoming data prior to add.
		if (gtinIn == null || gtinIn.equals(""))
		{
			throwError.append(" gtin value of null or ");
			throwError.append("empty is invalid. ");
		}
		
		// if primary edits pass contiue update process.
		if (throwError.toString().equals(""))
		{	
			try {
				// Pass along the incoming parameter. 
				Vector parmClass = new Vector();
				parmClass.addElement(gtinIn);
				
				// get the sql statements.
				requestType = "deleteView";
				sqlDeleteView = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append(" Error getting sql statement. ");
				throwError.append(e);
			}
		}
		
		// get a connection, execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
				conn = ConnectionStack.getConnection();
				deleteIt = conn.prepareStatement(sqlDeleteView);
				deleteIt.executeUpdate();
			} catch(Exception e)
			{
				throwError.append("error occured executing a ");
				throwError.append("delete sql statement. " + e);
				
			// return connection.
			} finally {
				if (conn != null)
				{
					try {
						ConnectionStack.returnConnection(conn);
					} catch(Exception el){
						el.printStackTrace();
					}
				}
			}
		}
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("deletView(gtin:");
			throwError.append(gtinIn + ")");
			throw new Exception(throwError.toString());
		}
		return;	
	}
	

	/**
	 * Delete ALL entries from the view file. 
	 * @param String - "everything".
	 * @throws Exception
	 */
	public static void deleteAllViews(String testWord)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String requestType = "";
		String sqlString = "";
		Connection conn = null;
		PreparedStatement deleteAll = null;
		
		// verify base class initialization.
		ServiceGTIN sg = new ServiceGTIN();
		
		// edit incoming data prior to add.
		if (testWord == null || !testWord.equals("deleteAllViews"))
		{
			throwError.append(" incoming parameter value must be ");
			throwError.append("'deleteAllViews'. ");
		}
		
		// if primary edits pass contiue update process.
		if (throwError.toString().equals(""))
		{	
			try {
				// Pass along the incoming parameter. 
				Vector parmClass = new Vector();
				parmClass.addElement(testWord);
				
				// get the sql statements.
				requestType = "deleteAllViews";
				sqlString = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append(" Error getting sql statement. ");
				throwError.append(e);
			}
		}
		
		// get a connection, execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
				conn = ConnectionStack.getConnection();
				deleteAll = conn.prepareStatement(sqlString);
				deleteAll.executeUpdate();
			} catch(Exception e)
			{
				throwError.append("error occured executing a ");
				throwError.append("delete sql statement. " + e);
				
			// return connection.
			} finally {
				if (conn != null)
				{
					try {
						ConnectionStack.returnConnection(conn);
					} catch(Exception el){
						el.printStackTrace();
					}
				}
			}
		}
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("deletAllViews(testWord:");
			throwError.append(testWord + ")");
			throw new Exception(throwError.toString());
		}
		return;	
	}
	
	
	/**
	 * Return a Vector of DropDownSingle classes.
	 * @param String resourceNumber.
	 * @return BeanResource business object.
	 * @throws Exception
	 */

	public static Vector buildDropDownGS1CompanyPrefix() throws Exception {
		StringBuffer throwError = new StringBuffer();

		Vector vectorList = new Vector();

		try {
			// Resource file lib/MSPWITRS.
			vectorList = findDropDownGS1CompanyPrefix();
		} catch (Exception e) {
			throwError.append(e);
		}

		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceGTIN.");
			throwError.append("buildDropDownGS1CompanyPrefix() ");
			throw new Exception(throwError.toString());
		}

		// return value
		return vectorList;
	}
	
	/**
	 * Return a BeanGTIN business object using the
	 * InqGTIN class for selection criteria.
	 * Return Parent and Children combonations.
	 * @param InqReserveNumbers.
	 * @return BeanGTIN business object.
	 * @throws Exception
	 */
	
	public static BeanGTIN buildParentChildren(
									InqGTIN fromVb)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		BeanGTIN beanGtin = new BeanGTIN();
		
		try {
			// Parent file lib/MSPOPARENT.
			beanGtin = findParentChildren(fromVb);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceGTIN.");
			throwError.append("buildParentChildren(");
			throwError.append("InqGTIN). ");
			throw new Exception(throwError.toString());
		}

		// return value
		return beanGtin;
	}

	/**
	 * Return a GTIN business object using a
	 * gtin number.
	 * @param String gtinNumber.
	 * @return GTIN business object.
	 * @throws Exception
	 */
	
	public static GTIN buildGtin(String gtinIn)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		GTIN gtin = new GTIN();
		
		try {
			// build gtin from UCCNET file lib/MSPRUCCN.
			gtin = findGtin(gtinIn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceGTIN.");
			throwError.append("buildGtin(gtin:" + gtinIn);
			throwError.append("). ");
			throw new Exception(throwError.toString());
		}
	
		// return value
		return gtin;
	}
	
	/**
	 * Return a BeanGTIN business object using a
	 * gtin number.
	 * @param String gtinNumber.
	 * @return BeanGTIN business object.
	 * @throws Exception
	 */
	
	public static BeanGTIN buildGtinDetail(String gtinIn)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		BeanGTIN beanGtin = new BeanGTIN();
		
		try {
			beanGtin = findGtinDetail(gtinIn);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceGTIN.");
			throwError.append("buildGtinDetail(gtin:" + gtinIn);
			throwError.append("). ");
			throw new Exception(throwError.toString());
		}
	
		// return value
		return beanGtin;
	}

	/**
	 * Return a vector of BeanGTIN business objects using
	 * the inqGTIN class for selection criteria.
	 * @param InqGTIN inqGtin.
	 * @return Vector of BeanGTIN objects of which
	 * 	 the GTIN business object is used for now.
	 * @throws Exception
	 */
	
	public static Vector buildGtinList(InqGTIN inqGtin)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnVector = new Vector();
		
		try {
			rtnVector = findGtinList(inqGtin);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceGTIN.");
			throwError.append("buildListGtin(InqGTIN). ");
			throw new Exception(throwError.toString());
		}
	
		// return value
		return rtnVector;
	}
	

	/**
	 * @return
	 */
	private static Vector findDropDownGS1CompanyPrefix() throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector returnVector = new Vector();
		String sqlString = "";
		Connection conn = null;
		PreparedStatement findThem = null;
		ResultSet rs = null;

		// verify base class initialization.
		ServiceGTIN sg = new ServiceGTIN();

		// get the sql statement.
		try {
			String requestType = "dropdown";
			Vector parmClass = new Vector();
			parmClass.addElement("GS1CompanyPrefix");
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch (Exception e) {
			throwError.append(e);
		}
		// get a connection. execute sql, build return vector.
		if (throwError.toString().equals("")) {
			try {
				conn = ConnectionStack.getConnection();
				findThem = conn.prepareStatement(sqlString);
				rs = findThem.executeQuery();

				while (rs.next()) {
					// add a vector element if resource changes.
					DropDownSingle newElement = new DropDownSingle();
					newElement.setValue(rs.getString("SAGCDE"));
					newElement.setDescription(rs.getString("SAGNME").trim());
					returnVector.addElement(newElement);
				}
			//	System.out.println("Stop and Test");
			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. ");
				throwError.append(e);
				// return connection.
			} finally {
				if (conn != null) {
					try {
						ConnectionStack.returnConnection(conn);
						rs.close();
						findThem.close();
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}

		// return data.

		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("findDropDownGS1CompanyPrefix()");
			throw new Exception(throwError.toString());
		}
		return returnVector;
	}
	
	
	/**
	 * Return a GTIN business object for incoming string
	 * from file MSPRUCCN if it exists.
	 * @param gtinIn
	 * @return GTIN
	 * @throws Exception
	 */
	private static GTIN findGtin(String gtinIn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		GTIN gtin = new GTIN();
		String sqlString = "";
		Connection conn = null;
		PreparedStatement findIt = null;
		ResultSet rs = null;
		
		// verify base class initialization.
		ServiceGTIN sg = new ServiceGTIN();
		
		// verify incoming gtin.
		if (gtinIn == null || gtinIn.trim().equals(""))
		{
			throwError.append(" The requested Gtin Number can not ");
			throwError.append("be null or empty. ");
		}
		
		// get gtin number.
		if (throwError.toString().equals(""))
		{
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(gtinIn.trim());
				sqlString = buildSqlStatement("uccNetOnly", parmClass);
			} catch (Exception e) {
			throwError.append(" error trying to get sqlString. ");
			}
		}
		
		// get a connection. execute sql, build return vector.
		if (throwError.toString().equals("")) {
			try {
				conn = ConnectionStack.getConnection();
				findIt = conn.prepareStatement(sqlString);
				rs = findIt.executeQuery();
				
				if (rs.next())
				{
					gtin = loadFieldsGTIN(rs, "uccNetOnly");
					gtin.setGtinNumber(gtinIn);
				}
				
			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. " + e);

				// return connection.
			} finally {
				if (conn != null) {
					try {
						ConnectionStack.returnConnection(conn);
						rs.close();
						findIt.close();
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}
		
		// return data.

		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("findGtin(gtin:");
			throwError.append(gtinIn + ")");
			throw new Exception(throwError.toString());
		}
		return gtin;
	}
	
	
	/**
	 * Return a BeanGTIN business object from incoming string
	 * use files (MSPRUCCN/MRPCMTGTIN) if they exist.
	 * @param String gtin number
	 * @return BeanGTIN
	 * @throws Exception
	 */
	private static BeanGTIN findGtinDetail(String gtinIn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanGTIN bg = new BeanGTIN();
		String sqlString = "";
		Connection conn = null;
		PreparedStatement findIt = null;
		ResultSet rs = null;
		GTINDetail gd = new GTINDetail();
		
		// verify base class initialization.
		ServiceGTIN sg = new ServiceGTIN();
		
		// verify incoming gtin.
		if (gtinIn == null || gtinIn.trim().equals(""))
		{
			throwError.append(" The requested Gtin Number can not ");
			throwError.append("be null or empty. ");
		}
		
		// get sql statement for gtin detail.
		if (throwError.toString().equals(""))
		{
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(gtinIn.trim());
				sqlString = buildSqlStatement("gtinDetail", parmClass);
			} catch (Exception e) {
			throwError.append(" error trying to get sqlString. ");
			}
		}
		
		// get a connection. execute sql, build return object.
		if (throwError.toString().equals("")) {
			try {
				conn = ConnectionStack.getConnection();
				findIt = conn.prepareStatement(sqlString);
				rs = findIt.executeQuery();
				
				if (rs.next())
				{
					gd = loadFieldsGTINDetail(rs, "gtinDetail");
				
					if (throwError.toString().equals("")) {
						if (gd.getGtinNumber() != null &&
							!gd.getGtinNumber().equals(""))
						{
							try {
								//load url vector for gtin.
								KeyValue kv = new KeyValue();
								Vector vector = new Vector();
								kv.setEntryType("GtinUrl");
								kv.setKey1(gd.getGtinNumber().trim());
								vector = ServiceKeyValue.buildKeyValueList(kv);
								gd.setUrls(vector);
							} catch (Exception e) {
								// skip it. allowed to be empty
								gd.setUrls(new Vector());
							}
						}
					}
				
					if (throwError.toString().equals("")) {
						if (gd.getGtinNumber() != null &&
							!gd.getGtinNumber().equals(""))
						{
							try {
								//load comment vector for gtin.
								KeyValue kv = new KeyValue();
								Vector vector = new Vector();
								kv.setEntryType("GtinComment");
								kv.setKey1(gd.getGtinNumber().trim());
								vector = ServiceKeyValue.buildKeyValueList(kv);
								gd.setComments(vector);
							} catch (Exception e) {
								// skip it. allowed to be empty
								gd.setComments(new Vector());
							}
						}
					}
					
					bg.setGtinDetail(gd);
				}
			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. " + e);

				// return connection.
			} finally {
				if (conn != null) {
					try {
						ConnectionStack.returnConnection(conn);
						rs.close();
						findIt.close();
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}
		
		
		// return data.

		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("findGtinDetail(gtin:");
			throwError.append(gtinIn + ")");
			throw new Exception(throwError.toString());
		}
		return bg;
	}

	/**
	 * Return a vector of BeanGTIN business objects.
	 *  use the incoming InqGTIN class for selection
	 * criteria against file MSPRUCCN.
	 * @param InqGTIN inqGtin
	 * @return Vector of BeanGTIN objects of which
	 * 	 the GTIN business object is used for now.
	 * @throws Exception
	 */
	private static Vector findGtinList(InqGTIN inqGtin)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnVector = new Vector();
		String sqlString = "";
		Connection conn = null;
		PreparedStatement findIt = null;
		PreparedStatement findThem = null;
		ResultSet rs = null;
		Vector treeList = new Vector();
		BeanGTIN bean = new BeanGTIN();
		
		// verify base class initialization.
		ServiceGTIN sg = new ServiceGTIN();
		
		try {
			// get family tree gtins if requested.
			if (inqGtin.getInqShowTree() != null &&
					!inqGtin.getInqShowTree().equals("") &&
					inqGtin.getInqGTIN() != null &&
					!inqGtin.getInqGTIN().equals(""))
			{
				//get the parents of the incoming gtin.
				treeList = findParents(inqGtin.getInqGTIN().trim());
			
				//load the vector with garbage if no parents exists.
				if (treeList.size() == 0)
					treeList.addElement("GotNutin");
				else
				{
					Vector kids = new Vector(); //will hold all parents childern
					
					// get the sql statement for parents family tree.
					try {
						Vector parmClass = new Vector();
						parmClass.addElement(treeList);
						sqlString = buildSqlStatement("familyRelations", parmClass);
					} catch(Exception e) {
						throwError.append("error on sql build. " + e);
						throw new Exception(throwError.toString());
					}
					
					try {
						conn = ConnectionStack.getConnection();
						findThem = conn.prepareStatement(sqlString);
						rs = findThem.executeQuery();
					} catch(Exception e) {
						throwError.append("error on conn/executeQuery. " + e);
						throw new Exception(throwError.toString());
					}
					
					while (rs.next())
					{
						GTINView gtinView = loadFieldsGTINView(rs, "familyRelations");
						String addIt = "yes";
						
						for (int x = 0; kids.size() > x && addIt.equals("yes"); x++)
						{
							String element = (String) kids.elementAt(x);
							if(element.equals(gtinView.getViewChild()))
								addIt = "no";
						}
						
						if (addIt.equals("yes"))
							kids.addElement(gtinView.getViewChild());
					}
					
					// add the kids vector to the treeList vector.
					for (int x = 0; kids.size() > x; x++)
					{
						String addIt = "yes";
						String kid = (String) kids.elementAt(x);
					
						for (int y = 0; treeList.size() > y; y++)
						{
							String element = (String) treeList.elementAt(y);
						
							if (kid.equals(element))
								addIt = "no";
						}
					
						if (addIt.equals("yes"))
							treeList.addElement(kid);
					}
				}
			}
		} catch(Exception e) {
			throwError.append(" Error bulding family tree for List page. ");
			throwError.append("gtin requested(" + inqGtin.getInqGTIN() + ").");
		}
		finally {
			if (conn != null) {
				try {
					ConnectionStack.returnConnection(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
			if (rs != null)
			{
			   try
			   {
				  rs.close();
			   } catch (Exception el) {
				  el.printStackTrace();
			  }
		   }
			if (findIt != null)
			{
			   try
			   {
				  findIt.close();
			   } catch (Exception el) {
				  el.printStackTrace();
			  }
		   }
		}
		
		
		// get sql statement for gtin List Page.
		if (throwError.toString().equals(""))
		{
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(inqGtin);
				parmClass.addElement(treeList);
				sqlString = buildSqlStatement("gtinListPage", parmClass);
			} catch (Exception e) {
			throwError.append(" error trying to get sqlString. " + e);
			}
		}
		
		// get a connection. execute sql, build return object.
		if (throwError.toString().equals("")) {
			try {
				conn = ConnectionStack.getConnection();
				findIt = conn.prepareStatement(sqlString);
				rs = findIt.executeQuery();
				
				while (rs.next())
				{
					BeanGTIN bg = loadFieldsGTINList(rs, "gtinListPage");
					
					
					// load the resource class for BeanGTIN
					// uses New Item File MSPWITRS.
// Old Code, can Delete	//Resource resource = new Resource();
					  Item item = new Item();
					try {
						item = ServiceItem.loadFieldsItem("loadItem", rs);
// Old Code, can Delete//resource = ServiceResource.buildResource(rs, "gtinListPage");
					} catch (Exception e) {
						// allow empty class.
					}
// Old Code, can Delete	//bg.setResource(resource);
					bg.setItem(item);
					rtnVector.addElement(bg);
				}
				
			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. " + e);
	
				// return connection.
			} finally {
				if (conn != null) {
					try {
						ConnectionStack.returnConnection(conn);
						rs.close();
						findIt.close();
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}
		
		// return data.
	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("findListGtin(InqGTIN). ");
			throw new Exception(throwError.toString());
		}
		return rtnVector;
	}
	
	
	/**
	 * Return a list of parents associated to the gtin
	 * @param String gtin Number
	 * @return Vector 
	 */
	private static Vector findParents(String gtinIn) 
		throws Exception 
	{
		Vector parents = new Vector();
		Vector temp = new Vector();
		String parent = "";
		StringBuffer throwError = new StringBuffer();
		String requestType = "parentsOfChild";
		String sqlString = "";
		Connection conn = null;
		PreparedStatement findThem = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ResultSet rs5 = null;
		ResultSet rs6 = null;
		ResultSet rs7 = null;
		ResultSet rs8 = null;
		ResultSet rs9 = null;
		String addIt = "";
		// verify base class initialization.
		ServiceGTIN sg = new ServiceGTIN();
		
		// verify incoming gtin.
		if (gtinIn == null || gtinIn.trim().equals(""))
		{
			throwError.append(" The requested Gtin Number can not ");
			throwError.append("be null or empty. ");
		}
		
		// get all parents for incoming gtin number 
		if (throwError.toString().equals(""))
		{
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(gtinIn.trim());
				sqlString = buildSqlStatement(requestType, parmClass);
				conn = ConnectionStack.getConnection();
				findThem = conn.prepareStatement(sqlString);
				rs1 = findThem.executeQuery();
				
				if (rs1.next()) { 

				  //assume multiple hits. re-execute the sql.
				  rs1 = findThem.executeQuery();
				  
				  //get all parents for gtin parent.
				  while (rs1.next()) {// all parents of gtinIn.
				  	temp = loadFieldsMisc(rs1, requestType);
				  	parent = (String) temp.elementAt(0);
				  	parmClass = new Vector();
				  	parmClass.addElement(parent);
				  	sqlString = buildSqlStatement(requestType, parmClass);
				  	findThem = conn.prepareStatement(sqlString);
				  	rs2 = findThem.executeQuery();
				  	
				  	if (rs2.next()){
				  	  //assume multiple hits. re-execute sql.
				  	  rs2 = findThem.executeQuery();
				  	  
				  	  //get all parents for gtin parent.
				  	  while (rs2.next()) {// all parents of gtinIn.
					  	temp = loadFieldsMisc(rs2, requestType);
					  	parent = (String) temp.elementAt(0);
					  	parmClass = new Vector();
					  	parmClass.addElement(parent);
					  	sqlString = buildSqlStatement(requestType, parmClass);
					  	findThem = conn.prepareStatement(sqlString);
					  	rs3 = findThem.executeQuery();
					  	
					  	if (rs3.next()){
					  	  //assume multiple hits. re-execute sql.
					  	  rs3 = findThem.executeQuery();
					  	  
					  	  //get all parents for gtin parent.
					  	  while (rs3.next()) {// all parents of gtinIn.
						  	temp = loadFieldsMisc(rs3, requestType);
						  	parent = (String) temp.elementAt(0);
						  	parmClass = new Vector();
						  	parmClass.addElement(parent);
						  	sqlString = buildSqlStatement(requestType, parmClass);
						  	findThem = conn.prepareStatement(sqlString);
						  	rs4 = findThem.executeQuery();
						  	
						  	if (rs4.next()){
						  	  //assume multiple hits. re-execute sql.
						  	  rs4 = findThem.executeQuery();
						  	  
						  	  //get all parents for gtin parent.
						  	  while (rs4.next()) {// all parents of gtinIn.
							  	temp = loadFieldsMisc(rs4, requestType);
							  	parent = (String) temp.elementAt(0);
							  	parmClass = new Vector();
							  	parmClass.addElement(parent);
							  	sqlString = buildSqlStatement(requestType, parmClass);
							  	findThem = conn.prepareStatement(sqlString);
							  	rs5 = findThem.executeQuery();
							  	
							  	if (rs5.next()){
							  	  //assume multiple hits. re-execute sql.
							  	  rs5 = findThem.executeQuery();
							  	  
							  	  //get all parents for gtin parent.
							  	  while (rs5.next()) {// all parents of gtinIn.
								  	temp = loadFieldsMisc(rs5, requestType);
								  	parent = (String) temp.elementAt(0);
								  	parmClass = new Vector();
								  	parmClass.addElement(parent);
								  	sqlString = buildSqlStatement(requestType, parmClass);
								  	findThem = conn.prepareStatement(sqlString);
								  	rs6 = findThem.executeQuery();
								  	
								  	if (rs6.next()){
								  	  //assume multiple hits. re-execute sql.
								  	  rs6 = findThem.executeQuery();
								  	  
								  	  //get all parents for gtin parent.
								  	  while (rs6.next()) {// all parents of gtinIn.
									  	temp = loadFieldsMisc(rs6, requestType);
									  	parent = (String) temp.elementAt(0);
									  	parmClass = new Vector();
									  	parmClass.addElement(parent);
									  	sqlString = buildSqlStatement(requestType, parmClass);
									  	findThem = conn.prepareStatement(sqlString);
									  	rs7 = findThem.executeQuery();
									  	
									  	if (rs7.next()){
									  	  //assume multiple hits. re-execute sql.
									  	  rs7 = findThem.executeQuery();
									  	  
									  	  //get all parents for gtin parent.
									  	  while (rs7.next()) {// all parents of gtinIn.
										  	temp = loadFieldsMisc(rs7, requestType);
										  	parent = (String) temp.elementAt(0);
										  	parmClass = new Vector();
										  	parmClass.addElement(parent);
										  	sqlString = buildSqlStatement(requestType, parmClass);
										  	findThem = conn.prepareStatement(sqlString);
										  	rs8 = findThem.executeQuery();
										  	
										  	if (rs8.next()){
										  	  //assume multiple hits. re-execute sql.
										  	  rs8 = findThem.executeQuery();
										  	  
										  	  //get all parents for gtin parent.
										  	  while (rs8.next()) {// all parents of gtinIn.
											  	temp = loadFieldsMisc(rs8, requestType);
											  	parent = (String) temp.elementAt(0);
											  	parmClass = new Vector();
											  	parmClass.addElement(parent);
											  	sqlString = buildSqlStatement(requestType, parmClass);
											  	findThem = conn.prepareStatement(sqlString);
											  	rs9 = findThem.executeQuery();
											  	
											  	if (rs9.next()){
											  	  //assume multiple hits. re-execute sql.
											  	} else {//@9
											  	  addIt = "yes";
											  		
											  	  //verify element not already in vector.
											  	  for (int x = 0; parents.size() > x; x++) {
											  	    String theElement = (String) parents.elementAt(x);
											  		  
											  		if (theElement.equals(parent))
											  		  addIt = "no";
											  	  }
											  		
											  	  if (addIt.equals("yes"))
											  	    parents.addElement(parent);
											  	}
										  	  }
										  	} else {//@8
										  	  addIt = "yes";
										  		
										  	  //verify element not already in vector.
										  	  for (int x = 0; parents.size() > x; x++) {
										  	  	String theElement = (String) parents.elementAt(x);
										  		  
										  	  	if (theElement.equals(parent))
										  	      addIt = "no";
										  	  }
										  		
										  	  if (addIt.equals("yes"))
										  	    parents.addElement(parent);
										  	}
									  	  }
									  	} else {//@7
									  	  addIt = "yes";
									  		
									  	  //verify element not already in vector.
									  	  for (int x = 0; parents.size() > x; x++) {
									  	    String theElement = (String) parents.elementAt(x);
									  		  
									  		if (theElement.equals(parent))
									  		  addIt = "no";
									  	  }
									  		
									  	  if (addIt.equals("yes"))
									  	    parents.addElement(parent);
									  	  }
									  	}
								  	  } else {//@6
									    addIt = "yes";
									  		
									  	//verify element not already in vector.
									  	for (int x = 0; parents.size() > x; x++) {
									  	  String theElement = (String) parents.elementAt(x);
									  		  
									  	  if (theElement.equals(parent))
									  	  	addIt = "no";
									  	}
									  		
									  	if (addIt.equals("yes"))
									  	  parents.addElement(parent);
									  }
									}
							  	  } else {//@5
								    addIt = "yes";
								  		
								  	//verify element not already in vector.
								  	for (int x = 0; parents.size() > x; x++) {
								  	  String theElement = (String) parents.elementAt(x);
								  		  
								  	  if (theElement.equals(parent))
								  	  	addIt = "no";
								  	}
								  		
								  	if (addIt.equals("yes"))
								  	  parents.addElement(parent);
								  }
								}
						  	  } else {//@4
							    addIt = "yes";
							  		
							  	//verify element not already in vector.
							  	for (int x = 0; parents.size() > x; x++) {
							  	  String theElement = (String) parents.elementAt(x);
							  		  
							  	  if (theElement.equals(parent))
							  	  	addIt = "no";
							  	}
							  		
							  	if (addIt.equals("yes"))
							  	  parents.addElement(parent);
							  }
							}
					  	  } else {//@3
						    addIt = "yes";
						  		
						  	//verify element not already in vector.
						  	for (int x = 0; parents.size() > x; x++) {
						  	  String theElement = (String) parents.elementAt(x);
						  		  
						  	  if (theElement.equals(parent))
						  	  	addIt = "no";
						  	}
						  		
						  	if (addIt.equals("yes"))
						  	  parents.addElement(parent);
						  }
						}
				  	  } else {//@2
					    addIt = "yes";
					  		
					  	//verify element not already in vector.
					  	for (int x = 0; parents.size() > x; x++) {
					  	  String theElement = (String) parents.elementAt(x);
					  		  
					  	  if (theElement.equals(parent))
					  	  	addIt = "no";
					  	}
					  		
					  	if (addIt.equals("yes"))
					  	  parents.addElement(parent);
					  }
					}
			  	} else {//@1
			  	
			  	  // only available parent.
			  		parents.addElement(gtinIn);
				  		  
				}
			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. " + e);

			// return connection.
			} finally {
				if (conn != null) {
					try {
						if (conn != null)
							ConnectionStack.returnConnection(conn);
						if (rs1 != null)
							rs1.close();
						if (rs2 != null)
							rs2.close();
						if (rs3 != null)
							rs3.close();
						if (rs4 != null)
							rs4.close();
						if (rs5 != null)
							rs5.close();
						if (rs6 != null)
							rs6.close();
						if (rs7 != null)
							rs7.close();
						if (rs8 != null)
							rs8.close();
						if (rs9 != null)
							rs9.close();
						if (findThem != null)
							findThem.close();
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}


	
		// return data.

		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("findLowestParents(gtin:");
			throwError.append(gtinIn + ")");
			throw new Exception(throwError.toString());
		}
		return parents;
	}
	
	
	/**
	 * Return a list of All Highest Level Parents.
	 * @return Vector (String top end parents)
	 */
	private static Vector findAllParents() 
		throws Exception 
	{
		Vector parents = new Vector();
		Vector temp = new Vector();
		String parent = "";
		StringBuffer throwError = new StringBuffer();
		String sqlString = "";
		Connection conn = null;
		PreparedStatement findThem = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		// get all parent Gitns.
		try {
			Vector parmClass = new Vector();
			sqlString = buildSqlStatement("findAllParents", parmClass);
		} catch (Exception e) {
		throwError.append(" error trying to get sqlString. " + e);
		}
		
		// get a connection. execute sql, build return vector.
		if (throwError.toString().equals("")) {
			try {
				conn = ConnectionStack.getConnection();
				findThem = conn.prepareStatement(sqlString);
				rs1 = findThem.executeQuery();

				while (rs1.next()) {
					// get the Child from the result set.
					temp = loadFieldsMisc(rs1, "findAllParents");
					parent = (String) temp.elementAt(0);
					
					// verify current Gtin has no parent.
					Vector parmClass = new Vector();
					parmClass.addElement(parent);
					sqlString = buildSqlStatement("parentsOfChild", parmClass);
					findThem = conn.prepareStatement(sqlString);
					rs2 = findThem.executeQuery();
					
					if (rs2.next()) {
						// dont add the gtin if its also a child.
					} else {
					
						// set flag prior to edit.
						String addIt = "yes";
					
						for (int x = 0; parents.size() > x && addIt.equals("yes"); x++)
						{
							String loadedParent = (String) parents.elementAt(x);
						
							// verify element no already in vector.
							if (parent.equals(loadedParent))
								addIt = "no";
						}
					
						if (addIt.equals("yes"))
							parents.addElement(parent);
					}
				}

			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. " + e);

			// return connection.
			} finally {
				if (conn != null) {
					try {
						if (conn != null)
							ConnectionStack.returnConnection(conn);
						if (rs1 != null)
							rs1.close();
						if (rs2 != null)
							rs2.close();
						if (findThem != null)
							findThem.close();
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}

		
		// return data.

		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("findAllParents()");
			throw new Exception(throwError.toString());
		}
		return parents;
	}
	
	
	/**
	 * 
	 * @param String GtinNumber.
	 * @return Void.
	 * @throws Exception
	 * 
	 * 	This method builds a set of view file (MSPHPARENT) records
	 * for the incoming GTIN number. The incoming GTIN number
	 * is expected to be a level 1 (lowest level) value.
	 * 	First the incoming GTIN number is loaded into the 
	 * view file. Next all child of the incoming GTIN number
	 * are retrieved into a resultset. Then each resultset entry 
	 * is loaded into the view file. 
	 * 	At load the resultset entry is checked and any 
	 * childern of that entry are accessed and loaded.
	 * This task of checking and loading children will be 
	 * initially set up at five levels deep.
	 */
	private static void addViewLevels(String gtinIn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String sqlInsert = "";
		String sqlFindThem = "";
		Connection conn = null;
		PreparedStatement findThem = null;
		PreparedStatement addIt;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		String requestType = "";
		Vector parmClass = new Vector();
		GTINView gtinView = new GTINView();
		GTINChild gtinChild = new GTINChild();
		int sequence = 1; 
		int level = 1;
		
		// validate incoming gtin number.
		if (gtinIn == null || gtinIn.trim().equals(""))
		{
			throwError.append(" error: Incoming Gtin number ");
			throwError.append("can not be null or empty. ");
		}
		
		if (throwError.toString().equals(""))
		{
			// verify base class initialization.
			ServiceGTIN sg = new ServiceGTIN();


			// get the sql statements.
			try {// build sql insert for parent level 1
				gtinView.setGtinNumber(gtinIn);
				gtinView.setViewChild(gtinIn);
				gtinView.setViewSeq(new Integer(sequence).toString());
				gtinView.setViewLevel(new Integer(level).toString());
				requestType = "insertViewRecord";
				parmClass = new Vector();
				parmClass.addElement(gtinView);
				sqlInsert = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append("error getting level " + level);
				throwError.append(" sequence " + sequence + " sql statement. " + e);
			}
		}

		// get a connection. execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
				conn = ConnectionStack.getConnection();			
				addIt = conn.prepareStatement(sqlInsert);
				addIt.executeUpdate();
				gtinChild = new GTINChild();
				gtinChild.setGtinNumber(gtinIn);
				
				// get and load all the children
				requestType = "viewChildrenOfParent";
				parmClass = new Vector();
				parmClass.addElement(gtinIn);
				sqlFindThem = buildSqlStatement(requestType, parmClass);
				findThem = conn.prepareStatement(sqlFindThem);
				rs1 = findThem.executeQuery();
				
				while (rs1.next()) //any children of incoming parent
				{
					gtinChild = new GTINChild();
					gtinChild = loadFieldsGTINChild(rs1, "child");
					sequence = sequence + 1;
					level = 2;
					gtinView = new GTINView();
					gtinView.setGtinNumber(gtinIn);
					gtinView.setViewChild(gtinChild.getGtinNumber());
					gtinView.setViewSeq(new Integer(sequence).toString());
					gtinView.setViewLevel(new Integer(level).toString());
					requestType = "insertViewRecord";
					parmClass = new Vector();
					parmClass.addElement(gtinView);
					sqlInsert = buildSqlStatement(requestType, parmClass);
					addIt = conn.prepareStatement(sqlInsert);
					addIt.executeUpdate();
					
					// get and load all the children
					requestType = "viewChildrenOfParent";
					parmClass = new Vector();
					parmClass.addElement(gtinChild.getGtinNumber());
					sqlFindThem = buildSqlStatement(requestType, parmClass);
					findThem = conn.prepareStatement(sqlFindThem);
					rs2 = findThem.executeQuery();
					
					while (rs2.next()) //any children of incoming parent
					{
						gtinChild = new GTINChild();
						gtinChild = loadFieldsGTINChild(rs2, "child");
						sequence = sequence + 1;
						level = 3;
						gtinView = new GTINView();
						gtinView.setGtinNumber(gtinIn);
						gtinView.setViewChild(gtinChild.getGtinNumber());
						gtinView.setViewSeq(new Integer(sequence).toString());
						gtinView.setViewLevel(new Integer(level).toString());
						requestType = "insertViewRecord";
						parmClass = new Vector();
						parmClass.addElement(gtinView);
						sqlInsert = buildSqlStatement(requestType, parmClass);
						addIt = conn.prepareStatement(sqlInsert);
						addIt.executeUpdate();
						
						// get and load all the children
						requestType = "viewChildrenOfParent";
						parmClass = new Vector();
						parmClass.addElement(gtinChild.getGtinNumber());
						sqlFindThem = buildSqlStatement(requestType, parmClass);
						findThem = conn.prepareStatement(sqlFindThem);
						rs3 = findThem.executeQuery();
					
						while (rs3.next()) //any children of incoming parent
						{
							gtinChild = new GTINChild();
							gtinChild = loadFieldsGTINChild(rs3, "child");
							sequence = sequence + 1;
							level = 4;
							gtinView = new GTINView();
							gtinView.setGtinNumber(gtinIn);
							gtinView.setViewChild(gtinChild.getGtinNumber());
							gtinView.setViewSeq(new Integer(sequence).toString());
							gtinView.setViewLevel(new Integer(level).toString());
							requestType = "insertViewRecord";
							parmClass = new Vector();
							parmClass.addElement(gtinView);
							sqlInsert = buildSqlStatement(requestType, parmClass);
							addIt = conn.prepareStatement(sqlInsert);
							addIt.executeUpdate();
						}
						
						// get and load all the children
						requestType = "viewChildrenOfParent";
						parmClass = new Vector();
						parmClass.addElement(gtinChild.getGtinNumber());
						sqlFindThem = buildSqlStatement(requestType, parmClass);
						findThem = conn.prepareStatement(sqlFindThem);
						rs4 = findThem.executeQuery();
					
						while (rs4.next()) //any children of incoming parent
						{
							gtinChild = new GTINChild();
							gtinChild = loadFieldsGTINChild(rs4, "child");
							sequence = sequence + 1;
							level = 5;
							gtinView = new GTINView();
							gtinView.setGtinNumber(gtinIn);
							gtinView.setViewChild(gtinChild.getGtinNumber());
							gtinView.setViewSeq(new Integer(sequence).toString());
							gtinView.setViewLevel(new Integer(level).toString());
							requestType = "insertViewRecord";
							parmClass = new Vector();
							parmClass.addElement(gtinView);
							sqlInsert = buildSqlStatement(requestType, parmClass);
							addIt = conn.prepareStatement(sqlInsert);
							addIt.executeUpdate();
						}
					}
				}
			} catch(Exception e)
			{
				throwError.append(" error occured executing a sql ");
				throwError.append("statement. " + e);
				
			// return connection.
			} finally {
				if (conn != null)
				{
					try {
						ConnectionStack.returnConnection(conn);
						findThem.close();
						if (rs1 != null)
							rs1.close();
						if (rs2 != null)
							rs2.close();
						if (rs3 != null)
							rs3.close();
						if (rs4 != null)
							rs4.close();
					} catch(Exception el){
						el.printStackTrace();
					}
				}
			}
		}

		// return data.
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services."); 
			throwError.append("ServiceGTIN.");
			throwError.append("addParentView(" + gtinIn + ") ");
			throw new Exception(throwError.toString());
		}
		return;
	}
	
	
	/**
	 * Return a BeanGtin class using the
	 * InqReserveNumber class for selection criteria.
	 * @param InqGTIN.
	 * @return BeanGTIN.
	 * The BeanGtin class: 
	 * GTIN  gtin
	 * 	will contain specific UCCNet file info for the
	 *  incoming gtin number (parent of the list).
	 * Vector children (GTINChild)
	 * 	will contain all children information for the 
	 *  specific incoming gtin number.
	 * 
	 */
	private static BeanGTIN findParentChildren(
								InqGTIN fromVb)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		BeanGTIN beanGtin = new BeanGTIN();
		String sqlString = "";
		Connection conn = null;
		PreparedStatement findThem = null;
		ResultSet rs = null;
		
		// verify base class initialization.
		ServiceGTIN sg = new ServiceGTIN();

		// get the sql statements.
		try {
			String requestType = "parentChildren";
			Vector parmClass = new Vector();
			parmClass.addElement(fromVb);
			sqlString = buildSqlStatement(requestType, parmClass);
		} catch(Exception e) {
			throwError.append("error getting sql statement. " + e);
		}

		// get a connection. execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
				conn = ConnectionStack.getConnection();			
				findThem = conn.prepareStatement(sqlString);
				rs = findThem.executeQuery();
				String saveGtin = "";
				Vector addIt = new Vector();
				
				while (rs.next())
				{
					// add parent GTIN to BeanGTIN.
					if (saveGtin.equals(""))
					{
						beanGtin.setGtin(loadFieldsGTIN(rs, "parent"));
						GTIN gtinParent = beanGtin.getGtin();
						saveGtin = gtinParent.getGtinNumber();
					}
					
					// use resultset to load BeanGTIN children vector.
					GTINChild gtinChild = new GTINChild();
					gtinChild = loadFieldsGTINChild(rs, "child");
					addIt.addElement(gtinChild);
					beanGtin.setChildren(addIt);
				}
			} catch(Exception e)
			{
				throwError.append(" error occured executing a sql ");
				throwError.append("statement. " + e);
				
			// return connection.
			} finally {
				if (conn != null)
				{
					try {
						ConnectionStack.returnConnection(conn);
						rs.close();
						findThem.close();
					} catch(Exception el){
						el.printStackTrace();
					}
				}
			}
		}

		// return data.
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services."); 
			throwError.append("ServiceGTIN.");
			throwError.append("findParentChildren(InqGTIN)");
			throw new Exception(throwError.toString());
		}
		return beanGtin;
	}
	
	
	/**
	 * Build a vector of BeanGTIN objects that will allow
	 * a browser presentation of a view (tree) for Gtins.
	 * 
	 * After the vector of BeanGTIN objects are built, roll
	 * through and add a vector for resource within the same
	 * bean prior to returning.
	 * 
	 * @param Vector of Gtin parents (Strings).
	 * @return Vector of BeanGtin class objects
	 * @throws Exception
	 */
	
	private static Vector findViews(Vector parents) 
		throws Exception
	{
		Vector beans = new Vector();
		StringBuffer throwError = new StringBuffer();
		String sqlString = "";
		Connection conn = null;
		PreparedStatement findIt = null;
		PreparedStatement findTheViews = null;
		PreparedStatement findTheKids = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		// verify base class initialization.
		ServiceGTIN sg = new ServiceGTIN();
				
		// read each incoming parent and build a tree.
		for (int x = 0; parents.size() > x; x++)
		{
		// 
			try {
			// Load GTIN info for parent (incoming vector element).
				BeanGTIN beanGtin = new BeanGTIN();
				GTIN aGtin = new GTIN();
				
				Vector parmClass = new Vector();
				parmClass.addElement(parents.elementAt(x));
				sqlString = buildSqlStatement("uccNetOnly", parmClass);
				conn = ConnectionStack.getConnection();
				findIt = conn.prepareStatement(sqlString);
				rs1 = findIt.executeQuery();
				
				if (rs1.next())
				{
					aGtin = loadFieldsGTIN(rs1, "uccNetOnly");
				} else
				{
					aGtin.setGtinDescription("");
					aGtin.setTradeItemUnitDescriptor("");
				}
				
				aGtin.setGtinNumber((String) parents.elementAt(x));
				
				beanGtin.setGtin(aGtin);
				
			// load view and children vectors.
				Vector views = new Vector();
				GTINView aView = new GTINView();
				Vector children = new Vector();
				GTINChild aChild = new GTINChild();
				
				parmClass = new Vector();
				parmClass.addElement(parents.elementAt(x));
				sqlString = buildSqlStatement("viewOfParent", parmClass);
				findTheViews = conn.prepareStatement(sqlString);
				rs2 = findTheViews.executeQuery();
				
				// track parent levels
				Vector levelChild = new Vector();
				for (int z = 0; z < 6; z++)
				{
					levelChild.addElement("");
				}
				
				int lvl = 0;

				while (rs2.next())
				{
				// Load a GTINView class into he view vector.
					aView = new GTINView();
					aChild = new GTINChild();
					aView = loadFieldsGTINView(rs2, "");
					
					// set array with parents for levels.
					lvl = new Integer(aView.getViewLevel()).intValue();
					levelChild.setElementAt(aView.getViewChild(), lvl);
					
					
					if (aView.getGtinNumber().trim().equals(aView.getViewChild().trim()))
					{
						aChild = new GTINChild();
						//views.addElement(aView);
					}
					else
					{
						parmClass = new Vector();
						UpdTieToChildren uttc = new UpdTieToChildren();
						
						// parent of current level.
						lvl =  new Integer(aView.getViewLevel()).intValue();
						if (lvl > 1)
							lvl = lvl - 1;
						uttc.setParentGTIN( (String) levelChild.elementAt(lvl));
									  
						uttc.setChildGTIN(aView.getViewChild()); 
						parmClass.addElement(uttc);
						sqlString = buildSqlStatement("ParentChild", parmClass);
						findTheKids = conn.prepareStatement(sqlString);
						rs3 = findTheKids.executeQuery();
						
						if (rs3.next())
							aChild = loadFieldsGTINChild(rs3, "child");
						else
							aChild = new GTINChild();
					}
					
					views.addElement(aView);
					children.addElement(aChild);
				}
				
				beanGtin.setViews(views);
				beanGtin.setChildren(children);
				beans.addElement(beanGtin);
				
			} catch (Exception e) {
				throwError.append(" Error executing sql while building ");
				throwError.append("a return of a View for display. ");
			
			// return connection.
			} finally {
				if (conn != null)
				{
					try {
						if (conn != null)
							ConnectionStack.returnConnection(conn);
						if (findIt != null)
							findIt.close();
						if (rs1 != null)
							rs1.close();
						if (findTheViews != null)
							findTheViews.close();
						if (rs2 != null)
							rs2.close();
						if (findTheKids != null)
							findTheKids.close();
						if (rs3 != null)
							rs3.close();
					} catch(Exception el){
						el.printStackTrace();
					}
				}
			}
		}
		
		// Add the Resource class for each bean child entry.
// Old Code, can Delete //Vector resources = new Vector();
		Vector items = new Vector();
		
		if (throwError.toString().equals("")) {
			for (int x = 0; x < beans.size(); x++)
			{
				BeanGTIN bean = (BeanGTIN) beans.elementAt(x);
				
				for (int y = 0; y < bean.getChildren().size(); y++)
				{
// Old Code, can Delete //	Resource resource = new Resource();
					Item item = new Item();
					String gtinNbr = "";
					
					if (y == 0)
						gtinNbr = bean.getGtin().getGtinNumber();
					else {
						GTINChild gtinChild = (GTINChild) bean.getChildren().elementAt(y);
						gtinNbr = gtinChild.getGtinNumber();
					}
					try {
// Old Code, can Delete // resource = ServiceResource.buildResourceFromGtin(gtinNbr);
					   item = ServiceItem.buildItemFromGtin("", gtinNbr);
					} catch (Exception e) {
						//allow empty Resource class.
					}
					if (y == 0)
					{
//	Old Code					Resource firstOne = new Resource();
						Item firstOne = new Item();
//	Old Code					resources.addElement(firstOne);
						items.addElement(firstOne);
//	Old Code					bean.setResource(resource);
						bean.setItem(item);
					} else
//	Old Code					resources.addElement(resource);
						items.addElement(item);
				}
// Old Code				bean.setChildResources(resources);
					bean.setChildItems(items);
			}
		}
		
		
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("findViews(Vector)");
			throw new Exception(throwError.toString());
		}
			
		return beans;
	}
	
	
	/**
	 * Return a Gtin (Globel Trade Item Number) with
	 * a calculated check digit.
	 * @param String Gtin 13 positions.
	 * @return String Gtin 14 positions (13 + check digit).
	 * @throws Exception
	 * 
	 * formula
	 * 		odds = Add the numbers in positions One,
	 * 		Three, Five, Seven, Nine, Eleven, and
	 * 		Thirteen.
	 * 
	 * 		evens = Add the number in positions Two,
	 * 				Four, Six, Eight, Ten, and 
	 * 				Twelve.
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
	
	public static String checkDigit14(String gtinIn) 
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String gtinOut = gtinIn;
		
		// verify incoming gtin number is exactly 13 long.
		if (gtinIn == null ||
			gtinIn.length() != 13)
		{
			throwError.append(" The Gtin number received ");
			throwError.append("must not be null, and must be ");
			throwError.append("exactly 13 long. ");
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
				for (int x = 0; x < 13; x++)
				{
					// acumulate odd digits.
					if (x == 0 || x == 2 || x == 4 || x == 6 ||
						x == 8 || x == 10 || x == 12)
					{
						odds = new Integer(gtinIn.substring(x, x + 1)).intValue();
						totalOdds = totalOdds + odds;
					}
					
					// accumulate even digits.
					if (x == 1 || x == 3 || x == 5 || x == 7 ||
						x == 9 || x == 11)
					{
						evens = new Integer(gtinIn.substring(x, x + 1)).intValue();
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
		
				gtinOut = gtinIn + checkDigit;
			}
		} catch(Exception e){
			throwError.append("Error on check digit determination. " + e);
		}
		
		// return data.
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("checkDigit14(gtin:");
			throwError.append(gtinIn + "). ");
			throw new Exception(throwError.toString());
		}
			
		return gtinOut;
	}
	
	
	/**
	 * Load class fields from result set.
	 * 
	 */
	
	private static GTIN loadFieldsGTIN(ResultSet rs, 
									   String type)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		GTIN gtin = new GTIN();

		try{ // sqlStatement("parentChildren")
		// load Gtin Number from Parent Child file.
			if (type.equals("parent"))
			{
				gtin.setGtinNumber(rs.getString("MSOPARENT").trim());
					
				if (rs.getString("parentMSRHLV") != null)
				{
					gtin.setTradeItemUnitDescriptor(rs.getString("parentMSRHLV").trim());
					gtin.setGtinDescription(rs.getString("parentMSRSDS").trim());
					gtin.setRecordID(rs.getString("parentMSRRCD").trim());
					gtin.setStatus(rs.getString("parentMSRSTS").trim());
					gtin.setBrandName(rs.getString("parentMSRBRD").trim());
					gtin.setGtinLongDescription(rs.getString("parentMSRLDS").trim());
					gtin.setPublishToUCCNet(rs.getString("parentMSRPUB").trim());
					gtin.setIsBaseUnit(rs.getString("parentMSRBSU").trim());
				} else
				{
					gtin.setTradeItemUnitDescriptor("");
					gtin.setGtinDescription("");
					gtin.setRecordID("");
					gtin.setStatus("");
					gtin.setBrandName("");
					gtin.setGtinLongDescription("");
					gtin.setPublishToUCCNet("");
					gtin.setIsBaseUnit("");
				}
			}
			
			if (type.equals("uccNetOnly") ||
				type.equals("gtinListPage") ||
				type.equals("findAllGtinByTuid") )
			{
				if (rs.getString("MSRGTN") != null)
					gtin.setGtinNumber(rs.getString("MSRGTN").trim());
					
				if (rs.getString("MSRHLV") != null)
				{
					gtin.setTradeItemUnitDescriptor(rs.getString("MSRHLV").trim());
					gtin.setGtinDescription(rs.getString("MSRSDS").trim());
					gtin.setBrandName(rs.getString("MSRBRD").trim());
					gtin.setRecordID(rs.getString("MSRRCD").trim());
					gtin.setStatus(rs.getString("MSRSTS").trim());
					gtin.setGtinLongDescription(rs.getString("MSRLDS").trim());
					gtin.setPublishToUCCNet(rs.getString("MSRPUB").trim());
					gtin.setIsBaseUnit(rs.getString("MSRBSU").trim());
				}
			}

		} catch(Exception e)
		{
			throwError.append(" Problem loading the class ");
			throwError.append("from the result set. ");
		}
		
		// return data.
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("loadFieldsGTIN(rs, type:");
			throwError.append(type + "). ");
			throw new Exception(throwError.toString());
		}
		return gtin;
	}

	/**
	 * Load class fields from result set.
	 * 
	 */
	
	private static GTINDetail loadFieldsGTINDetail(ResultSet rs, 
									   String type)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		GTINDetail gd = new GTINDetail();
	
		try{ // sqlStatement("gtinDetail")
		// load Gtin detail from file MSPRUCCN.
			
			if (type.equals("gtinDetail"))
			{
				gd.setRecordID(rs.getString("MSRRCD").trim());
				gd.setStatus(rs.getString("MSRSTS").trim());
				gd.setGtinNumber(rs.getString("MSRGTN").trim());
				gd.setGtinDescription(rs.getString("MSRSDS").trim());
				gd.setTradeItemUnitDescriptor(rs.getString("MSRHLV").trim());
				gd.setBrandName(rs.getString("MSRBRD").trim());
				gd.setGtinLongDescription(rs.getString("MSRLDS").trim());
				gd.setPublishToUCCNet(rs.getString("MSRPUB").trim());
				gd.setIsBaseUnit(rs.getString("MSRBSU").trim());
				gd.setTargetMarketCountryCode(rs.getString("MSRCNY").trim());
				gd.setCatalogItemState(rs.getString("MSRCIS").trim());
				gd.setInformationProvider(rs.getString("MSRBRO").trim());
				gd.setNameOfInformationProvider(rs.getString("MSRBRN").trim());
				gd.setEanUCCType(rs.getString("MSRUCT").trim());
				gd.setEanUCCCode(rs.getString("MSRUCC").trim());
				gd.setIsInformationPrivate(rs.getString("MSRPVT").trim());
				gd.setIsConsumerUnit(rs.getString("MSRCNU").trim());
				gd.setClassificationCategoryCode(rs.getString("MSRCCC").trim());
				gd.setNetContent(rs.getString("MSRNCT").trim());
				gd.setNetContentUnitOfMeasure(rs.getString("MSRCUM").trim());
				gd.setHeight(rs.getString("MSRHGT").trim());
				gd.setWidth(rs.getString("MSRWTH").trim());
				gd.setDepth(rs.getString("MSRDTH").trim());
				gd.setLinearUnitOfMeasure(rs.getString("MSRLUM").trim());
				gd.setNetWeight(rs.getString("MSRNWG").trim());
				gd.setGrossWeight(rs.getString("MSRGWG").trim());
				gd.setWeightUnitOfMeasure(rs.getString("MSRWUM").trim());
				gd.setVolume(rs.getString("MSRVOL").trim());
				gd.setVolumeUnitOfMeasure(rs.getString("MSRVUM").trim());
				gd.setQtyOfNextLowerLevelTradeItem(rs.getString("MSRNLL").trim());
				gd.setIsOrderableUnit(rs.getString("MSRORU").trim());
				
				// convert date for display.
				gd.setEffectiveDate("");
				
				if (rs.getInt("MSREDT") != 0) 
				{
					//convert file date from yyyy/mm/dd to mm/dd/yyyy.
					String fileDate = rs.getString("MSREDT");
					String date = fileDate.substring(4, 6) + "/";
					date = date + fileDate.substring(6, 8) + "/";
					date = date + fileDate.substring(0, 4);
					
					//validate date.
					String[] ckDates = CheckDate.validateDate(date);

					if (ckDates[6].equals(""))
						gd.setEffectiveDate(ckDates[1]);

				} else
					gd.setEffectiveDate("0");
				
				gd.setAdditionalTradeItemDescription(rs.getString("MSRADS").trim());
				gd.setQtyChildren(rs.getString("MSRCQT").trim());
				gd.setAdditionalClassificationAgencyName(rs.getString("MSRACA").trim());
				gd.setAdditionalClassificationCategoryCode(rs.getString("MSRACC").trim());
				gd.setAdditionalClassificationCategoryDesc(rs.getString("MSRACD").trim());
				gd.setFunctionalName(rs.getString("MSRFUN").trim());
				gd.setIsDispatchUnit(rs.getString("MSRDSU").trim());
				gd.setIsInvoiceUnit(rs.getString("MSRIVU").trim());
				gd.setIsVariableUnit(rs.getString("MSRVRU").trim());
				gd.setIsPackagingMarkedRecyclable(rs.getString("MSRPRY").trim());
				gd.setIsPackagingMarkedReturnable(rs.getString("MSRPRT").trim());
				gd.setIsPackagingMarkedWithExpirationDate(rs.getString("MSRPEX").trim());
				gd.setIsPackagingMarkedWithGreenDot(rs.getString("MSRPGD").trim());
				gd.setIsPackagingMarkedWithIngredients(rs.getString("MSRPIG").trim());
				gd.setCountryOfOrigin(rs.getString("MSRCOR").trim());
				
				// convert date for display.
				gd.setLastChangeDate("");
				
				if (rs.getInt("MSRLCD") != 0) 
				{
					//convert file date from yyyy/mm/dd to mm/dd/yyyy.
					String fileDate = rs.getString("MSRLCD");
					String date = fileDate.substring(4, 6) + "/";
					date = date + fileDate.substring(6, 8) + "/";
					date = date + fileDate.substring(0, 4);
					
					//validate date.
					String[] ckDates = CheckDate.validateDate(date);
					
					if (ckDates[6].equals(""))
						gd.setLastChangeDate(ckDates[1]);
				} else
					gd.setLastChangeDate("0");
				
				// convert date for display.
				gd.setPublicationDate("");
				
				if (rs.getInt("MSRPBD") != 0) 
				{
					//convert file date from yyyy/mm/dd to mm/dd/yyyy.
					String fileDate = rs.getString("MSRPBD");
					String date = fileDate.substring(4, 6) + "/";
					date = date + fileDate.substring(6, 8) + "/";
					date = date + fileDate.substring(0, 4);
					
					//validate date.
					String[] ckDates = CheckDate.validateDate(date);
					
					if (ckDates[6].equals(""))
						gd.setPublicationDate(ckDates[1]);
				} else
					gd.setPublicationDate("0");
				
				// convert date for display.
				gd.setStartAvailabilityDate("");
				
				if (rs.getInt("MSRSAD") != 0) 
				{
					//convert file date from yyyy/mm/dd to mm/dd/yyyy.
					String fileDate = rs.getString("MSRSAD");
					String date = fileDate.substring(4, 6) + "/";
					date = date + fileDate.substring(6, 8) + "/";
					date = date + fileDate.substring(0, 4);
					
					//validate date.
					String[] ckDates = CheckDate.validateDate(date);
					
					if (ckDates[6].equals(""))
						gd.setStartAvailabilityDate(ckDates[1]);
				} else
					gd.setStartAvailabilityDate("0");

				gd.setHasBatchNumber(rs.getString("MSRBTC").trim());
				gd.setIsNonSoldReturnable(rs.getString("MSRRET").trim());
				gd.setIsItemMarkedRecyclable(rs.getString("MSRRCY").trim());
				gd.setIsNetContentDeclarationIndicated(rs.getString("MSRNCI").trim());
				gd.setDeliveryMethodIndicator(rs.getString("MSRDMI").trim());
				gd.setIsBarcodeSymbologyDerivable(rs.getString("MSRBSD").trim());
				gd.setLastUpdateUser(rs.getString("MSRUSR").trim());
				gd.setLastUpdateWorkstation(rs.getString("MSRWSN").trim());
				
				// convert date for display.
				if (rs.getInt("MSRDTE") != 0)
				{
					//convert file date from yyyy/mm/dd to mm/dd/yyyy.
					String fileDate = rs.getString("MSRDTE");
					String date = fileDate.substring(4, 6) + "/";
					date = date + fileDate.substring(6, 8) + "/";
					date = date + fileDate.substring(0, 4);
					
					//validate date.
					String[] ckDates = CheckDate.validateDate(date);
					
					if (ckDates[6].equals(""))
						gd.setLastUpdateDate(ckDates[1]);
				} else
					gd.setLastUpdateDate("0");
				
				gd.setLastUpdateTime(rs.getString("MSRTME").trim());
				gd.setSubBrand(rs.getString("MSRSBR").trim());
				gd.setVariant(rs.getString("MSRVAR").trim());
				gd.setCouponFamilyCode(rs.getString("MSRCFC").trim());
				gd.setQtyCompleteLayers(rs.getString("MSRHI").trim());
				gd.setQtyItemsPerCompleteLayer(rs.getString("MSRTI").trim());
				gd.setTargetMarketCode(rs.getString("MSRTGC").trim());
				gd.setTargetMarketSubdivCode(rs.getString("MSRMSB").trim());
				gd.setTradeItemEffectiveDate(rs.getString("MSRTED").trim());
				gd.setQtyChildrenUnits(rs.getString("MSRCLQ").trim());
				gd.setClassificationCategoryDefinition(rs.getString("MSRCCD").trim());
				gd.setClassificationCategoryName(rs.getString("MSRCCN").trim());
			}
	
		} catch(Exception e)
		{
			throwError.append(" Problem loading the class ");
			throwError.append("from the result set. ");
		}
		
		// return data.
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("loadFieldsGTINDetail(rs, type:");
			throwError.append(type + "). ");
			throw new Exception(throwError.toString());
		}
		return gd;
	}
	
	
	/**
	 * Load class fields from result set.
	 * 
	 * For request type "gtinListPage"
	 * 	- GTIN class from file 
	 */
	
	private static BeanGTIN loadFieldsGTINList(ResultSet rs, 
												String type)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		BeanGTIN bg = new BeanGTIN();
		
		GTINDetail gtinDetail = new GTINDetail();

		try{ // sqlStatement "gtinListPage" uses file MSPRUCCN.
			gtinDetail = loadFieldsGTINDetail(rs, "gtinDetail");
		} catch(Exception e)
		{
			throwError.append(" Problem loading the class ");
			throwError.append("from the result set. ");
		}
		
		bg.setGtinDetail(gtinDetail);
		
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("loadFieldsGTINList(rs, type:");
			throwError.append(type + "). ");
			throw new Exception(throwError.toString());
		}
		
		return bg;
	}
	
	
	/**
	 * Load class fields from result set.
	 * 
	 */
	
	private static GTINChild loadFieldsGTINChild(ResultSet rs, 
									   			 String type)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		GTINChild gtinChild = new GTINChild();

		try{ //sqlRequest("ParentChild").
		// load GtinChild from Parent Child file.	 
			if (type.equals("child"))
			{
				if (rs.getString("MSOCHILD") != null)
				{
					gtinChild.setGtinNumber(rs.getString("MSOCHILD"));
					gtinChild.setChildSeq(rs.getString("MSOSEQ"));
					gtinChild.setChildQty(rs.getString("MSOQTY"));
					gtinChild.setLastUpdateDate(CheckDate.convertYMDtoMDY(
												rs.getString("MSOUDT").trim()));
					gtinChild.setLastUpdateTime(rs.getString("MSOUTM").trim());
					gtinChild.setLastUpdateUser(rs.getString("MSOUSR").trim());
				}
				
				// The resultset may or may not contain MSPRUCCN fields.
				try { //sqlRequest("ParentChildren", "viewChildrenOfParent").		
					if (rs.getString("childMSRHLV") != null)
					{
						gtinChild.setTradeItemUnitDescriptor(rs.getString("childMSRHLV").trim());
						gtinChild.setGtinDescription(rs.getString("childMSRSDS").trim());
						gtinChild.setRecordID(rs.getString("childMSRRCD").trim());
						gtinChild.setStatus(rs.getString("childMSRSTS").trim());
						gtinChild.setBrandName(rs.getString("childMSRBRD").trim());
						gtinChild.setGtinLongDescription(rs.getString("childMSRLDS").trim());
						gtinChild.setPublishToUCCNet(rs.getString("childMSRPUB").trim());
						gtinChild.setIsBaseUnit(rs.getString("childMSRBSU").trim());
					}
				} catch (Exception skipIt)
				{}
				
				try { // sqlrequest("ParentChild",
					
					if (rs.getString("MSRHLV") != null)
					{
						gtinChild.setTradeItemUnitDescriptor(rs.getString("MSRHLV").trim());
						gtinChild.setGtinDescription(rs.getString("MSRSDS").trim());
						gtinChild.setRecordID(rs.getString("MSRRCD").trim());
						gtinChild.setStatus(rs.getString("MSRSTS").trim());
						gtinChild.setBrandName(rs.getString("MSRBRD").trim());
						gtinChild.setGtinLongDescription(rs.getString("MSRLDS").trim());
						gtinChild.setPublishToUCCNet(rs.getString("MSRPUB").trim());
						gtinChild.setGtinNumber(rs.getString("MSRGTN").trim());
						gtinChild.setIsBaseUnit(rs.getString("MSRBSU").trim());
						gtinChild.setIsOrderableUnit(rs.getString("MSRORU").trim());
					}
				} catch (Exception skipIt)
				{}
			}
			
			// load GtinChild from Parent Child file.	 
			if (type.equals("parent"))
			{
				if (rs.getString("MSOPARENT") != null)
				{
					gtinChild.setGtinNumber(rs.getString("MSOPARENT"));
					gtinChild.setChildSeq("0");
					gtinChild.setChildQty("0");
					gtinChild.setLastUpdateDate("0");
					gtinChild.setLastUpdateTime("0");
					gtinChild.setLastUpdateUser(rs.getString("MSOUSR").trim());
				}
							
				if (rs.getString("parentMSRHLV") != null)
				{
					gtinChild.setTradeItemUnitDescriptor(rs.getString("parentMSRHLV").trim());
					gtinChild.setGtinDescription(rs.getString("parentMSRSDS").trim());
					gtinChild.setIsBaseUnit(rs.getString("parentMSRBSU").trim());
				}
			}

		} catch(Exception e)
		{
			throwError.append(" Problem loading the class ");
			throwError.append("from the result set. ");
		}
		
		// return data.
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("loadFieldsGTINChild(rs, type:");
			throwError.append(type + "). ");
			throw new Exception(throwError.toString());
		}
		return gtinChild;
	}
	
	
	/**
	 * Load class fields from result set.
	 * 
	 */
	
	private static Vector loadFieldsMisc(ResultSet rs, 
										 String type)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnVector = new Vector();

		try{
			
			if (type.equals("parentsOfChild")){
				// load parent from file MSPOPARENT into return string.
				String parent = rs.getString("MSOPARENT").trim();
				rtnVector.addElement(parent);
			}
			
			if (type.equals("findAllParents")) {
				// load child from file MSPOPARENT into return string.
				String child = rs.getString("MSOPARENT").trim();
				rtnVector.addElement(child);
			}
			
		} catch(Exception e)
		{
			throwError.append(" Problem loading the class ");
			throwError.append("from the result set. ");
		}
		
		// return data.
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("loadFieldsMisc(rs, type:");
			throwError.append(type + "). ");
			throw new Exception(throwError.toString());
		}
		return rtnVector;
	}
	
	
	/**
	 * Load class fields from result set.
	 * 
	 */
	
	private static GTINView loadFieldsGTINView(ResultSet rs, 
												String type)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		GTINView gtinView = new GTINView();

		try{
			// load GtinView from Parent Child file.
			gtinView.setGtinNumber(rs.getString("MSHPARENT").trim());
			gtinView.setViewSeq(rs.getString("MSHSEQ").trim());
			gtinView.setViewLevel(rs.getString("MSHLVL").trim());
			gtinView.setViewChild(rs.getString("MSHCHILD").trim());
			gtinView.setLastUpdateDate(CheckDate.convertYMDtoMDY(
									rs.getString("MSHUDT").trim()));
			gtinView.setLastUpdateTime(rs.getString("MSHUTM").trim());
			gtinView.setLastUpdateUser(rs.getString("MSHUSR").trim());
		
			try {
				if (rs.getString("MSRSDS") != null)
				{
					gtinView.setGtinDescription(rs.getString("MSRSDS"));
					gtinView.setTradeItemUnitDescriptor(rs.getString("MSRHLV"));
					gtinView.setRecordID(rs.getString("MSRRCD").trim());
					gtinView.setStatus(rs.getString("MSRSTS").trim());
					gtinView.setBrandName(rs.getString("MSRBRD").trim());
					gtinView.setGtinLongDescription(rs.getString("MSRLDS").trim());
					gtinView.setPublishToUCCNet(rs.getString("MSRPUB").trim());
					gtinView.setIsBaseUnit(rs.getString("MSRBSU").trim());
					gtinView.setIsOrderableUnit(rs.getString("MSRORU").trim());
				}
			} catch (Exception doNothing) {}
			
		} catch(Exception e)
		{
			throwError.append(" Problem loading the class ");
			throwError.append("from the result set. ");
		}
		
		// return data.
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("loadFieldsGTINView(rs, type:");
			throwError.append(type + "). ");
			throw new Exception(throwError.toString());
		}
		return gtinView;
	}
	

	/**
	 * 
	 */
	public ServiceGTIN() {
		super();
	}
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		// Test all that you can.
		String stophere = "";		
		Vector vector = null;
		DropDownSingle dds = new DropDownSingle();
		UpdTieToChildren uttc = new UpdTieToChildren();
		
		try {
		//*** TEST "DropDownGS1CompanyPrefix()".
			if ("x".equals("y"))
			{
				vector = findDropDownGS1CompanyPrefix();
				dds = (DropDownSingle) vector.elementAt(0);
				System.out.println(dds.getValue().toString());
				System.out.println(dds.getDescription().toString());
				stophere = "x";
			}

		//*** TEST "addChild().	
			if ("x".equals("y"))
			{	
				uttc.setParentGTIN("Parent21");
				uttc.setChildGTIN("child1Of21");
				uttc.setChildSequence("01");
				uttc.setChildQuantity("11.11");
				addChild(uttc);
				stophere = "x";
			}
			
		//*** TEST deleteChildren.
			if ("x".equals("y"))
			{
				deleteChildren("Parent21");
				stophere = "x";
			}
			
		//*** Test buildParentChildren(InqGTIN).
			if ("x".equals("y"))
			{
				InqGTIN inqGtin = new InqGTIN();
				inqGtin.setInqGTIN("00028700900154");
				BeanGTIN gtin = buildParentChildren(inqGtin);
				stophere = "x";
			}
			
		//*** Test buildGtin(String).
			if ("x".equals("x"))
			{
				GTIN gtin = buildGtin("00028700809433");
				stophere = "x";
				BeanGTIN bg = buildGtinDetail("00028700809433");
				stophere = "x";
				vector = buildViews("00028700809433");
				stophere = "x";
			}
			
		//*** Test addParentView(String)
			if ("x".equals("y"))
			{
				addViewLevels("00028700809433");
				stophere = "x";
			}
			
		//*** Test deleteView(String)
			if ("x".equals("y"))
			{
				deleteViewLevels("00028700809433");
				stophere = "x";
			}
			
			
		//*** find Parents(String)
			if ("x".equals("y"))
			{
				Vector parents = new Vector();
				parents = findParents("00028700000014");
				stophere = "x";
				parents = findParents("00028700100011");
				stophere = "x";
				parents = findParents("00028700800010");
				stophere = "x";
				parents = findParents("00028700102718");
				stophere = "x";
			}
			
		//*** rebuildViews(gtin number)
			if ("x".equals("y"))
			{
				//Vector parents = findAllParents();
				Vector view = rebuildViews("00028700800010");
				//Vector views = findViews(parents);
				//Vector views = buildViews("00028700800010");
				//stophere = "x";
				//Vector views2 = buildViews("00028700900154");
				//stophere = "x";
				
				// this will rebuild (delete/build) the entire MSPHPARENT file.
				//rebuildViews();
				stophere = "x";
				
			}
			
		//*** test checkDigit14(String).
			if ("x".equals("y"))
			{
				String gtin = checkDigit14("9101454121022");
				stophere = "x";
				//should die here.
				gtin = checkDigit14("911454121022");
			}
			
		//*** test buildGtinList(InqGTIN)
			if ("x".equals("Y"))
			{	
				// test lookup by Gtin Number.
				InqGTIN inqGtin = new InqGTIN();
				inqGtin.setInqGTIN("00028700102718");
				inqGtin.setInqShowTree("y");
				vector = buildGtinList(inqGtin);
				stophere = "x";
				inqGtin.setInqGTIN("");
				inqGtin.setInqShowTree("");
				inqGtin.setInqDescriptionLong("64");
				vector = buildGtinList(inqGtin);
				stophere = "x";
				
			}
			
		//*** test buildGtinDetail(String).
		//    test updateGtinDetail(UpdGTIN).
		//    test addGtin(updGTIN).
			if ("x".equals("y"))
			{
				BeanGTIN bg = buildGtinDetail("00028700102718");
				stophere = "x";
// FOR TESTING PURPOSES				
//				if ("x".equals("y"))
//				{
//					UpdGTIN ug = new UpdGTIN();
//					ug.loadUpdGTINFromBeanGTIN(bg);
					
//					String aca = ug.getAdditionalClassificationAgencyName();
//					String lu  = ug.getLastUpdateUser();
//					String lw  = ug.getLastUpdateWorkstation();
//					String ld  = ug.getLastUpdateDate();
//					String lt  = ug.getLastUpdateTime();
//					ug.setAdditionalClassificationAgencyName("test Agency");
//					ug.setComment("main comment test#1.");
//					updateGtinDetail(ug);
//					stophere = "x";
//					ug.setAdditionalClassificationAgencyName(aca);
//					ug.setLastUpdateUser(lu);
//					ug.setLastUpdateWorkstation(lw);
//					ug.setLastUpdateDate(ld);
//					ug.setLastUpdateTime(lt);
//					ug.setComment("main comment test#2.");
//					updateGtinDetail(ug);
//					stophere = "x";
//				}
// FOR TESTING PURPOSES				
//				if ("x".equals("y")) 
//				{
//					UpdGTIN ug = new UpdGTIN();
//					ug.loadUpdGTINFromBeanGTIN(bg);
//					ug.setGtinNumber("111111111111");
//					ug.setComment("add comment");
//					ServiceGTIN.addGTIN(ug);
//					stophere = "x";
//				}
			}	
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	/**
	 * Return a sql where clause.
	 *
	 * Creation date: (7/13/2004 1:36:19 PM)
	 */
	private static String sqlOrderBy(InqGTIN inqGtin)
										 
			throws Exception 
	{
		String testOrderBy = inqGtin.getOrderBy();
		String testOrderStyle = inqGtin.getOrderStyle();
		String sqlOrderBy = "";
		StringBuffer throwError = new StringBuffer();
		
		try 
		{	
			if (testOrderBy == null || 
				testOrderBy.trim().equals(""))
				testOrderBy = "gtinNumber";
			
			if (testOrderStyle == null)
			   testOrderStyle = "";   	
		
			if (testOrderBy.toLowerCase().equals("gtinnumber"))
			   sqlOrderBy = " ORDER BY MSRGTN " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("gtindescription"))
			   sqlOrderBy = " ORDER BY MSRSDS " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("resourcenumber"))
			   sqlOrderBy = " ORDER BY MSWRSC " + testOrderStyle + " ";
		
			if (testOrderBy.toLowerCase().equals("tradeitemunitdescriptor"))
			   sqlOrderBy = " ORDER BY MSRHLV " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("brandname"))
			   sqlOrderBy = " ORDER BY MSRBRD " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("longdescription"))
			   sqlOrderBy = " ORDER BY MSRLDS " + testOrderStyle + " ";
		
			if (testOrderBy.toLowerCase().equals("isbaseunit"))
			   sqlOrderBy = " ORDER BY MSRBSU " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("load"))
			   sqlOrderBy = " ORDER BY MSRPUB " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("eanucccode"))
			   sqlOrderBy = " ORDER BY MSRUCC " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("eanucctype"))
			   sqlOrderBy = " ORDER BY MSRUCT " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("additionaltradeitemdescription"))
			   sqlOrderBy = " ORDER BY MSRADS " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("functionalname"))
			   sqlOrderBy = " ORDER BY MSRFUN " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("classificationcategorycode"))
			   sqlOrderBy = " ORDER BY MSRCCC " + testOrderStyle + " ";
				
			if (testOrderBy.toLowerCase().equals("informationprovidername"))
			   sqlOrderBy = " ORDER BY MSRBRN " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("informationprovider"))
			   sqlOrderBy = " ORDER BY MSRBRO " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("height"))
			   sqlOrderBy = " ORDER BY MSRHGT " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("width"))
			   sqlOrderBy = " ORDER BY MSRWTH " + testOrderStyle + " ";
				
			if (testOrderBy.toLowerCase().equals("depth"))
			   sqlOrderBy = " ORDER BY MSRDTH " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("linearunits"))
			   sqlOrderBy = " ORDER BY MSRLUM " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("volume"))
			   sqlOrderBy = " ORDER BY MSRVOL " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("volumeunits"))
			   sqlOrderBy = " ORDER BY MSRVUM " + testOrderStyle + " ";
				
			if (testOrderBy.toLowerCase().equals("tradeitemeffectivedate"))
			   sqlOrderBy = " ORDER BY MSREDT " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("catalogueitemstate"))
			   sqlOrderBy = " ORDER BY MSRCIS " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("countryoforigin"))
			   sqlOrderBy = " ORDER BY MSRCOR " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("isinformationprivate"))
			   sqlOrderBy = " ORDER BY MSRPVT " + testOrderStyle + " ";
				
			if (testOrderBy.toLowerCase().equals("targetmarketcountrycode"))
			   sqlOrderBy = " ORDER BY MSRCNY " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("publicationdate"))
			   sqlOrderBy = " ORDER BY MSRPBD " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("startavailabilitydate"))
			   sqlOrderBy = " ORDER BY MSRSAD " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("qtynextlowerleveltradeitem"))
			   sqlOrderBy = " ORDER BY MSRNLL " + testOrderStyle + " ";
				
			if (testOrderBy.toLowerCase().equals("qtychildren"))
			   sqlOrderBy = " ORDER BY MSRCQT " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("qtycompletelayers"))
			   sqlOrderBy = " ORDER BY MSRHI " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("qtyitemspercompletelayer"))
			   sqlOrderBy = " ORDER BY MSRTI " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("isconsumerunit"))
			   sqlOrderBy = " ORDER BY MSRCNU " + testOrderStyle + " ";
				
			if (testOrderBy.toLowerCase().equals("isorderableunit"))
			   sqlOrderBy = " ORDER BY MSRORU " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("isdispatchunit"))
			   sqlOrderBy = " ORDER BY MSRDSU " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("isinvoiceunit"))
			   sqlOrderBy = " ORDER BY MSRIVU " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("isvariableunit"))
			   sqlOrderBy = " ORDER BY MSRVRU " + testOrderStyle + " ";
				
			if (testOrderBy.toLowerCase().equals("isrecyclable"))
			   sqlOrderBy = " ORDER BY MSRPRY " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("isreturnable"))
			   sqlOrderBy = " ORDER BY MSRPRT " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("hasexpiredate"))
			   sqlOrderBy = " ORDER BY MSRPEX " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("hasgreendot"))
			   sqlOrderBy = " ORDER BY MSRPGD " + testOrderStyle + " ";
				
			if (testOrderBy.toLowerCase().equals("hasingredients"))
			   sqlOrderBy = " ORDER BY MSRPIG " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("isnetcontentdeclarationindicated"))
			   sqlOrderBy = " ORDER BY MSRNCI " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("hasbatchnumber"))
			   sqlOrderBy = " ORDER BY MSRBTC " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("isnonsoldreturnable"))
			   sqlOrderBy = " ORDER BY MSRRET " + testOrderStyle + " ";
				
			if (testOrderBy.toLowerCase().equals("isitemrecyclable"))
			   sqlOrderBy = " ORDER BY MSRRCY " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("netcontent"))
			   sqlOrderBy = " ORDER BY MSRNCT " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("netcontentunits"))
			   sqlOrderBy = " ORDER BY MSRCUM " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("netweight"))
			   sqlOrderBy = " ORDER BY MSRNWG " + testOrderStyle + " ";
				
			if (testOrderBy.toLowerCase().equals("grossweight"))
			   sqlOrderBy = " ORDER BY MSRGWG " + testOrderStyle + " ";

			if (testOrderBy.toLowerCase().equals("weightunits"))
			   sqlOrderBy = " ORDER BY MSRWUM " + testOrderStyle + " ";
			
			if (testOrderBy.toLowerCase().equals("lastupdatedate"))
			   sqlOrderBy = " ORDER BY MSRDTE " + testOrderStyle + " ";
			
		}
		catch (Exception e) {
			throwError.append("Error setting sql ORDER BY. " + e);
		}
		
		if (!throwError.toString().equals(""))
		{
			throwError.append(" - Exception error at com.treetop.services.");
			throwError.append("ServiceGTIN.sqlOrderBy(InqGTIN). "); 
			throw new Exception(throwError.toString());
		}
		
		return sqlOrderBy; 	
	
	}
	
	
	/**
	 * Update GTIN Detail file from app view bean. 
	 * @param UpdGTIN viewbean.
	 * @return void.
	 */
	public static void updateGtinDetail(UpdGTIN updVb)	
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Vector parmClass = new Vector();
		String requestType = "";
		String sqlExists = "";
		String sqlUpdate = "";
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		
		// verify base class initialization.
		ServiceItem sr = new ServiceItem();
			

		// edit incoming data prior to update.			
		//throwError.append(editData(updVb));

		// Contiue update process.
		if (throwError.toString().equals(""))
		{	
			try {
				// Pass along the incoming view bean information. 
				parmClass = new Vector();
				parmClass.addElement(updVb.getGtinNumber());
				
				// get the sql statements.
				requestType = "uccNetOnly";
				sqlExists = buildSqlStatement(requestType, parmClass);
				
				// Pass along the incoming view bean information. 
				parmClass = new Vector();
				parmClass.addElement(updVb);
				
				requestType = "updateGtinDetail";
				sqlUpdate = buildSqlStatement(requestType, parmClass);
				
			} catch(Exception e) {
				throwError.append(e);
			}
		}
		
		// get a connection. execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
				conn = ConnectionStack.getConnection();
				preStmt = conn.prepareStatement(sqlExists);
				rs = preStmt.executeQuery();
				
				if (rs.next())
				{
					preStmt = conn.prepareStatement(sqlUpdate);
					preStmt.executeUpdate();
				} else
				{
					throwError.append(" Unable to update the ");
					throwError.append("Gtin. The Gtin is not ");
					throwError.append("currently defined (not there).");	
				}
			} catch(Exception e)
			{
				throwError.append("error occured executing a ");
				throwError.append("update sql statement. " + e);
				
			// return connection.
			} finally {
				if (conn != null)
				{
					try {
						ConnectionStack.returnConnection(conn);
						rs.close();
						preStmt.close();
					} catch(Exception el){
						el.printStackTrace();
					}
				}
			}
			
		}
		
		
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceGTIN.");
			throwError.append("updateGtinDetail(UpdGTIN)");
			throwError.append(" gtin#:" + updVb.getGtinNumber() + ". ");
			throw new Exception(throwError.toString());
		}
		
	}
	
	
	/**
	 * Add the brand name "WHERE" clause.
	 *
	 */
	private static String sqlWhereBrand(String brand,
										String whereClause)
							throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((brand != null) && 
				(!brand.trim().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0))
					sqlWhere = sqlWhere + "AND ";		
			
				sqlWhere = sqlWhere +
							"(MSRBRD = '" + brand + "') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceGTIN.sqlWhereBrand" +
						 "(dsec: " + brand +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}
	
	
	/**
	 * Add the Gtin Long Description "WHERE" clause.
	 *
	 */
	private static String sqlWhereGtinLongDescription(String longDesc,
										  			  String whereClause)
	throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((longDesc != null) && 
				(!longDesc.equals("")))
			{
				
				if (whereClause.length() >= 1)  
					sqlWhere = sqlWhere + "AND ";		
			
				sqlWhere = sqlWhere +
							"(UPPER(MSRLDS) LIKE '%" + longDesc.toUpperCase() + "%') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceGTIN.sqlWhereGtinLongDescription" +
						 "(longDesc: " + longDesc +
						 " whereClause:" + whereClause + ")";
		}
		
		return sqlWhere; 	 
	
	}
	
	
	/**
	 * Add the Gtin Description "WHERE" clause.
	 *
	 */
	private static String sqlWhereGtinDescription(String gtinDesc,
												  String whereClause)
							throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((gtinDesc != null) && 
				(!gtinDesc.trim().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0))
					sqlWhere = sqlWhere + "AND ";		
			
				sqlWhere = sqlWhere +
							"(UPPER(MSRSDS) LIKE '%" + gtinDesc.toUpperCase() + "%') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceGTIN.sqlWhereGtinDescription" +
						 "(dsec: " + gtinDesc +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}
	
	
	/**
	 * Add the Gtin Number "WHERE" clause.
	 *
	 */
	private static String sqlWhereGtinNumber(String gtinNumber,
											 String whereClause)
							throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((gtinNumber != null) && 
				(!gtinNumber.equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0))
					sqlWhere = sqlWhere + "AND ";		
			
				sqlWhere = sqlWhere +
							"(MSRGTN LIKE '%" + gtinNumber + "%') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceGTIN.sqlWhereGtinNumber" +
						 "(gtin: " + gtinNumber +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}
	
	/**
	 * Add the Resource "WHERE" clause.
	 *
	 */
	private static String sqlWhereResource(String resourceNumber,
										  String whereClause)
							throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((resourceNumber != null) && 
				(!resourceNumber.equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0))
					sqlWhere = sqlWhere + "AND ";		
			
				sqlWhere = sqlWhere +
							"(MSWRSC LIKE '%" + resourceNumber + "%') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceGTIN.sqlWhereResource" +
						 "(resc: " + resourceNumber +
						 " whereClause:" + whereClause + ")";
		}
		
		return sqlWhere; 	 
	
	}
	
	
	/**
	 * Add the Show Tree "WHERE" clause.
	 *
	 */
	private static String sqlWhereShowTree(String showTree,
										   String whereClause,
										   Vector treeList)
	throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {
		
			if ((showTree != null) && 
				!showTree.trim().equals("") &&
				treeList.size() > 0)
			{					   		

				if (whereClause.length() > 1 )
					sqlWhere = sqlWhere + "AND ";
				
				StringBuffer inList = new StringBuffer();
				inList.append("");
				
				for (int x = 0; treeList.size() > x; x++)
				{
					if (inList.length() > 1)
						inList.append(", ");
					String element = (String) treeList.elementAt(x);
					inList.append("'" + element + "'");
				}
			
				sqlWhere = sqlWhere +
							" (MSRGTN IN (" + inList + ")) ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceGTIN.sqlWhereShowTree" +
						 "(whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}
	
	
	/**
	 * Add the tiud "WHERE" clause.
	 *
	 */
	private static String sqlWhereTiud(String tiud,
									String whereClause)
							throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((tiud != null) && 
				(!tiud.trim().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0))
					sqlWhere = sqlWhere + "AND ";		
			
				sqlWhere = sqlWhere +
							"(MSRHLV = '" + tiud.trim() + "') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceGTIN.sqlWhereTiud" +
						 "(tiud: " + tiud +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}
	
	
	/**
	 * Add the EAN/UCC CODE "WHERE" clause.
	 *
	 */
	private static String sqlWhereUpcCode(String upcCode,
										  String whereClause)
							throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((upcCode != null) && 
				(!upcCode.equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0))
					sqlWhere = sqlWhere + "AND ";		
			
				sqlWhere = sqlWhere +
							"(MSRUCC LIKE '%" + upcCode + "%') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceGTIN.sqlWhereResource" +
						 "(upcCode: " + upcCode +
						 " whereClause:" + whereClause + ")";
		}
		
		return sqlWhere; 	 
	
	}


	/**
	 * Add the publish "WHERE" clause.
	 *
	 */
	private static String sqlWherePublish(String publish,
									      String whereClause)
							throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
			if (publish != null &&
				!publish.equals("all")) 
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0))
					sqlWhere = sqlWhere + "AND ";		
			    if (publish.equals(""))
			    	publish = " ";
				sqlWhere = sqlWhere +
							"(MSRPUB = '" + publish + "') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceGTIN.sqlWherePublish" +
						 "(publish: " + publish +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}
	
	
}

