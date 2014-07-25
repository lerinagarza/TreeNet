/*
 * Created on October 9, 2007
 * 
 */
package com.treetop.services;

import java.sql.*;
import java.util.*;
import java.math.*;
import com.treetop.*;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.app.coa.*;
import com.treetop.utilities.*;

/**
 * @author twalto.
 *
 * Services class to obtain and return data 
 * to business objects.
 * 
 * Certificate of Analysis 
 */
public class ServiceCOA extends BaseService {

	/**
	 * 
	 */
	public ServiceCOA() {
		super();
	}
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		String startHere = "x";
		
		try
		{
			
			// I don't know.
			if (1 == 2)
			{
				BeanCOA bean = new BeanCOA();
				InqCOA inqCOA = new InqCOA();
				inqCOA.setInqDistributionOrder("0001416756");
				inqCOA.setUpdateUser("THAILE");
				inqCOA.setRequestType("goToUpdate");
				bean = buildCOA(inqCOA);
				String stopHere = "x";
			}
			
			// test to see if the get by lot option works.
			if (1 == 1)
			{
				BeanCOA bean = new BeanCOA();
				InqCOA inqCOA = new InqCOA();
				inqCOA.setInqLot("9272091002");
				inqCOA.setUpdateUser("THAILE");
				inqCOA.setRequestType("goToUpdate");
				bean = buildCOA(inqCOA);
				String stopHere = "x";
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		String endHere = "x";
	}

	/**
	 * Return a BeanCOA business object using
	 * the inqCOA class for selection criteria.
	 * @param InqCOA inqCOA.
	 * @return BeanCOA object which includes
	 * 	 the COA Information.
	 * @throws Exception
	 */
	
	public static BeanCOA buildCOA(InqCOA inqCOA)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		BeanCOA returnBean = new BeanCOA();
		
		try {
			returnBean = findCOA(inqCOA);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceCOA.");
			throwError.append("buildCOA(InqCOA). ");
			throw new Exception(throwError.toString());
		}
	
		// return value
		return returnBean;
	}

	/**
	 * Return a BeanCOA business object.
	 *  use the incoming InqCOA class for selection
	 * 
	 *  This Method will make the decisions as to 
	 *  what is loaded into the Bean.
	 * 
	 * @param InqCOA inqCOA
	 * @return BeanCOA business object.
	 * @throws Exception
	 */
	private static BeanCOA findCOA(InqCOA inqCOA)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		BeanCOA bean = new BeanCOA();
		
		// verify base class initialization.
		ServiceCOA sc = new ServiceCOA();
		try {
			//  Get Connection to attach to files
			conn = ConnectionStack.getConnection();
			
			// Get COA Information by Sales Order -- All Lots Associated to the Sales Order 
			if (inqCOA.getInqSalesOrder() != null &&
				 !inqCOA.getInqSalesOrder().equals(""))
			{
			   bean = findCOABySalesOrder(inqCOA, conn);
			}
			
			// Get COA Information by Distribution Order -- All Lots Associated to the Distribution Order 
			if (inqCOA.getInqDistributionOrder() != null &&
				 !inqCOA.getInqDistributionOrder().equals(""))
			{
				bean = findCOAByDistributionOrder(inqCOA, conn);
			}
			
			// Get COA Information directly relating to 1 Lot Number only
			if (inqCOA.getInqLot() != null &&
				 !inqCOA.getInqLot().equals(""))
			{
				bean = findCOAByLot(inqCOA, conn);
			}
			
		} catch (Exception e) {
			throwError.append(" error occured executing a sql statement. " + e);

		} finally {
			//return connection.
			if (conn != null) {
				try {
				   ConnectionStack.returnConnection(conn);
				} catch (Exception e) {
					throwError.append("error returning connection. " + e);
				}
			}
			
			//log any error in system out file.
			if (!throwError.toString().equals("")) {
				throwError.append("Error at com.treetop.services.");
				throwError.append("ServiceCOA.");
				throwError.append("findCOAInfo(InqCOA). ");
				System.out.println(throwError.toString());
				Exception e = new Exception();
				e.printStackTrace();
				throw new Exception(throwError.toString());
			}
		}
		
		// return data.
		return bean;
	}

	/**
	* Return a BeanCOA business object.
	*  use the incoming InqCOA class for selection
	* 
	*  This Method will make the decisions as to 
	*  what is loaded into the Bean.
	*  Use for Distribution and Sales Order Based COA's
	* 
	* @param InqCOA inqCOA
	* @param Connection conn
	* @return BeanCOA business object.
	* @throws Exception
	*/
		private static BeanCOA findCOABySalesOrder(InqCOA inqCOA, 
												   Connection conn)
			throws Exception
		{
			StringBuffer throwError = new StringBuffer();
			Statement findIt1 = null;
			Statement findIt2 = null;
			Statement testIt = null;
			ResultSet rs1 = null;
			ResultSet rs2 = null;
			ResultSet testRS = null;
			String returnNotFound = "";
			BeanCOA bean = new BeanCOA();
			Vector sendValues = new Vector();
			
			try {
				sendValues.add(inqCOA);
				
				if (!inqCOA.getRequestType().equals("preview"))
				{	
					//------------------------------	
					// TEST if VALID SALES ORDER -- Must have record in the OOHEAD file	
					try {
						findIt1 = conn.createStatement();
						rs1 = findIt1.executeQuery(buildSqlStatement(inqCOA.getEnvironment(), "testByCO", sendValues));
					} catch(Exception e) {
						throwError.append(" error occured executing the Test Valid sql statement. " + e);
					}
					
					try {
						String buildCOAHeader = "";
						String saveLine = "";

						while (rs1.next()) 
						{
							// Test to see if a COA should be Built / Started
							if (rs1.getString("QAATYPE") == null &&
								buildCOAHeader.trim().equals(""))
							{
								// Build a beginning set of Records for the COA
								// Update the COA
								addInitialCOA(sendValues, rs1, conn);
								buildCOAHeader = "Y";
							}
							
							if (rs1.getString("QABTYPE") == null &&
								!(rs1.getString("OBPONR") + rs1.getString("OBPOSX")).equals(saveLine.trim()))
							{
								addInitialCOALine(sendValues, rs1, conn);
								saveLine = rs1.getString("OBPONR") + rs1.getString("OBPOSX");
							}
							
							if (rs1.getString("QACTYPE") == null)
							{
								addInitialCOALineAttribute(sendValues, rs1, conn);
							}
						}
					
						rs1.close();
						findIt1.close();
					}
					catch(Exception e) {
						throwError.append(" error occured reading of result set. " + e);
					} 
				}
			} catch(Exception e) {
				throwError.append(" error occured dealing with a result set. " + e);
			}
			
			//------------------------------	
			// Get actual Information / COA-Sales Order
			
			try
			{
				if (returnNotFound.equals(""))
				{
					String requestType = "coaByCO";
					try {
						if (inqCOA.getRequestType().equals("goToUpdate"))
							requestType = "coaByCOSeq";
						
						findIt2 = conn.createStatement();
						rs2 = findIt2.executeQuery(buildSqlStatement(inqCOA.getEnvironment(), requestType, sendValues));
					} catch(Exception e) {
						throwError.append(" error occured executing a sql statement. " + e);
					}
					
					// Read the Result Set, build Data
					Vector listCOA = new Vector();
					Vector listSO = new Vector();
					Vector listValue = new Vector();
					
					try
					{
						String saveLine = "";
						String saveAttributeSeq = "";
						String saveTransaction = "";
						String saveLot = "";
						String saveLotLine = "";
						BigDecimal qty = new BigDecimal("0");
						while (rs2.next())
						{
						    if (rs2.getString("LMBANO") != null && 
						    	(saveLot.equals("") || !saveLot.equals(rs2.getString("LMBANO").trim()) ||
						    	(saveLotLine.equals("") || !saveLotLine.equals(rs2.getString("OBPONR").trim()))	)	)
						    {
						    	qty = new BigDecimal("0");
						    	
						    	// Run SQL statement to see if this Lot Number should be included
						    	try {
						    		Vector testValues = new Vector();
							   	 	testValues.addElement(rs2.getString("OAORNO").trim());
							   	    testValues.addElement(rs2.getString("OBPONR").trim());
							   	    testValues.addElement(rs2.getString("OBITNO").trim());
							   	    testValues.addElement(rs2.getString("LMBANO"));
							   	    testValues.addElement(rs2.getString("QAATYPE"));//fix was QAATYP
							   	    testValues.addElement(rs2.getString("OACONO"));//fix was QAACONO
							   	    testValues.addElement(""); //used for type DO only for now.
							   	    testIt = conn.createStatement();
							   	    testRS = testIt.executeQuery(buildSqlStatement(inqCOA.getEnvironment(), "testQTY", testValues));
							   	   
							   	    // Add in code to Run through a result set and ADD up the Quantity Fields.
							   	    while (testRS.next())
							   	    {
							   	    	qty = qty.add(testRS.getBigDecimal("MTTRQT")); 
							   	    }

							   	    testIt.close();
							   	    testRS.close();
								 
							   	    saveLot = rs2.getString("LMBANO").trim();
							   	    saveLotLine = rs2.getString("OBPONR").trim();
						    	} catch(Exception e) {
						    		throwError.append(" error occured executing a sql statement. " + e);
						    	}					
						    }
						    
						    if (qty.compareTo(new BigDecimal("0")) != 0)
						    {	
						    	if (rs2.getString("OBPONR") != null && 
						    		!(rs2.getString("OBPONR") + rs2.getString("OBPOSX")).equals(saveLine))
						    		saveAttributeSeq = "";
						
						    	if (requestType.equals("coaByCOSeq"))
						    	{	
						    		if (rs2.getString("AAATID") != null &&
						    			!rs2.getString("AAATID").trim().equals(saveAttributeSeq.trim()))
						    		{	
						    			COADetailAttributes addOne = loadFieldsCOA("", rs2);
						    			
						    			if (rs2.getString("AAATID") != null)
						    				addOne.setAttributeClass(ServiceAttribute.loadFieldsAttribute("loadAttributeLine", rs2));
						    			
						    			listCOA.addElement(addOne);
						    			saveAttributeSeq = rs2.getString("AAATID").trim();
						    			AttributeValue addOne1 = ServiceAttribute.loadFieldsAttributeValue("loadAttributeValueLine", rs2);
						    			if (addOne1.getAttribute() != null 
								    		&& !addOne1.getAttribute().trim().equals("")
								    		&& !addOne1.getValue().trim().equals("")
									   		&& addOne1.getFieldType().trim().equals("alpha"))
								   		{
								   			try
								   			{
								   				Attribute attr = ServiceAttribute.findAttribute(inqCOA.getEnvironment(), addOne1.getValue());
								   				if (attr.getAttribute() != null && !attr.getAttribute().equals(""))
								   				{
									    			addOne1.setAttributeUOMDescription(attr.getAttributeDescription());
								   				}
								   			}
								   			catch(Exception e)
								   			{
								   				System.out.println("Problem with finding the Attribute");
								   			}
								   		}
								   		if (addOne1.getAttribute() != null 
								    		&& !addOne1.getAttribute().trim().equals("") 
								    		&& addOne1.getFieldType().trim().equals("text")
								    		&& rs2.getString("AGTXID") != null
								    		&& !rs2.getString("AGTXID").trim().equals("0"))
								    	{
								    		try
								    		{
								    				addOne1.setValue(ServiceAttribute.findAttributeTextValue(inqCOA.getEnvironment(), rs2.getString("AGTXID")));
								    		}
								    		catch(Exception e)
								    		{
								    			System.out.println("Problem with finding the Attribute Text Information");
								    		}
								    	}
								   		Lot lot1 = addOne1.getLotObject();
						    			lot1.setQuantity(qty.toString());
						    			addOne1.setLotObject(lot1);
						    			listValue.addElement(addOne1);
						    		}
						    	}
						    	
						    	if (requestType.equals("coaByCO"))
						    	{
						    		
						    		if (rs2.getString("QACATTRSEQ") != null && rs2.getString("QACATTRSEQ") != null &&
						    			!(rs2.getString("QACATTR").trim() + rs2.getString("QACATTRSEQ").trim()).equals(saveAttributeSeq.trim()))
						    		{	
						    			COADetailAttributes addOne = loadFieldsCOA("", rs2);
						    			
						    			if (rs2.getString("AAATID") != null)
						    				addOne.setAttributeClass(ServiceAttribute.loadFieldsAttribute("loadAttributeLine", rs2));
						    			
						    			listCOA.addElement(addOne);
						    			saveAttributeSeq = rs2.getString("QACATTR").trim() + rs2.getString("QACATTRSEQ").trim();
						    			AttributeValue addOne1 = ServiceAttribute.loadFieldsAttributeValue("loadAttributeValueLine", rs2);
						    			if (addOne1.getAttribute() != null 
							    			&& !addOne1.getAttribute().trim().equals("")
							    			&& !addOne1.getValue().trim().equals("")
								    		&& addOne1.getFieldType().trim().equals("alpha"))
							   			{
							   				try
							   				{
							   					Attribute attr = ServiceAttribute.findAttribute(inqCOA.getEnvironment(), addOne1.getValue());
							   					if (attr.getAttribute() != null && !attr.getAttribute().equals(""))
							   					{
									    			addOne1.setAttributeUOMDescription(attr.getAttributeDescription());
							   					}
							   				}
							   				catch(Exception e)
							   				{
							   					System.out.println("Problem with finding the Attribute");
							   				}
							   			}
							   			if (addOne1.getAttribute() != null 
							    			&& !addOne1.getAttribute().trim().equals("") 
							    			&& addOne1.getFieldType().trim().equals("text")
							    			&& rs2.getString("AGTXID") != null
							    			&& !rs2.getString("AGTXID").trim().equals("0"))
							    		{
							    			try
							    			{
							    					addOne1.setValue(ServiceAttribute.findAttributeTextValue(inqCOA.getEnvironment(), rs2.getString("AGTXID")));
							    			}
							    			catch(Exception e)
							    			{
							    				System.out.println("Problem with finding the Attribute Text Information");
							    			}
							    		}
							   			Lot lot1 = addOne1.getLotObject();
						    			lot1.setQuantity(qty.toString());
						    			addOne1.setLotObject(lot1);
						    			listValue.addElement(addOne1);
						    		}
						    	}
						    	
						    	if (rs2.getString("OBPONR") != null &&  
						    		!(rs2.getString("OBPONR") + rs2.getString("OBPOSX")).equals(saveLine))
						    	{	
						    		SalesOrderDetail addSO = ServiceSalesOrder.loadFieldsSalesOrderLineItem("loadLine", rs2);
						    		listSO.addElement(addSO);
						    		saveLine = rs2.getString("OBPONR") + rs2.getString("OBPOSX");
						    	}
						    }
						}
					} catch(Exception e) {
						throwError.append(" error occured reading of result set. " + e);
					}
					
					bean.setListCOADetailAttributes(listCOA);
					bean.setListAttrValues(listValue);
					bean.setListSOLineItems(listSO);
					
					try {
						findIt2.close();
						rs2.close();
					} catch (Exception e) {
					}
					
				} // END of the if Not Found
			} catch (Exception e) {
				throwError.append(" error occured processing information. " + e);
			}
			
			finally {
				if (rs1 != null) {
					try {
						rs1.close();
						findIt1.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (rs2 != null) {
					try {
						rs2.close();
						findIt2.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (testRS != null) {
					try {
						testRS.close();
						testIt.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			// return error data.
			if (!throwError.toString().equals("")) {
				throwError.append("Error at com.treetop.services.");
				throwError.append("ServiceCOA.");
				throwError.append("findCOAInfo(InqCOA). ");
				throw new Exception(throwError.toString());
			}
			
			return bean;
		}

/**
 * Build an sql statement.
 * @param request type
 * @param Vector selection criteria
 * @return sql string
 * @throws Exception
 */
	
private static String buildSqlStatement(String environment, 
										String inRequestType,
										Vector requestValues)
	throws Exception  
{
	StringBuffer sqlString = new StringBuffer();
	String whereClause = "";
	StringBuffer throwError = new StringBuffer();
	String thisDropDown = "";

	String library = GeneralUtility.getLibrary(environment);
	String ttlibrary = GeneralUtility.getTTLibrary(environment);
	
	try { 
//------------------------------------------------------------------------------
//   TEST		
//------------------------------------------------------------------------------
//	  TEST -- To see if a COA Exists -- Build one if NOT exists
//	  Also test to see if the incoming value is VALID
//	  This will also Retrieve Basic COA Data
//			From the Customer Master
//				 the Sales Order header
//				 the Distribution Order header
//				 the Lot Master
		if (inRequestType.equals("testByLot") || // by Lot Number
			inRequestType.equals("testByCO") ||  // by Sales Order
			inRequestType.equals("testByDO"))    // by Distribution Order 
		{
			InqCOA incomingValue = (InqCOA) requestValues.elementAt(0);
			// qualify file fields for use.
			StringBuffer fieldList = new StringBuffer();
			StringBuffer fromStatement = new StringBuffer();
			StringBuffer whereStatement = new StringBuffer();
			StringBuffer orderBy = new StringBuffer();
			// build the sql statement.
			// The FROM will be based on SO, DO or Lot ** Those main files
			//    if it is not found a record will need to be added
			if (inRequestType.equals("testByCO"))
			{
				// Sales Order  File -- OOHEAD
				fieldList.append("OACONO, OADIVI, OAORNO, OAFACI, OAWHLO, OACUNO, ");
				fieldList.append("OARLDT, OACUOR, OARESP, OALMDT, OACHID, OAADID, ");
				fromStatement.append("FROM " + library + ".OOHEAD ");
				
				// Join Customer Master File -- OCUSMA
				fieldList.append("OKCONO, OKDIVI, OKCUNO, OKCUNM, ");
				fromStatement.append("LEFT OUTER JOIN " + library + ".OCUSMA ");
				fromStatement.append("     ON OACONO = OKCONO AND OADIVI = OKDIVI ");
				fromStatement.append("    AND OACUNO = OKCUNO ");
				
				// Join Customer Address - OCUSAD
				fieldList.append("OPCONO, OPDIVI, OPCUNO, OPADID, OPCUA1, OPCUA2, ");
				fieldList.append("OPCUA3, OPCUA4, OPPONO, OPECAR, ");
				fromStatement.append("LEFT OUTER JOIN " + library + ".OCUSAD ");
				fromStatement.append("     ON OACONO = OPCONO AND OADIVI = OPDIVI ");
				fromStatement.append("    AND OACUNO = OPCUNO AND OAADID = OPADID ");
				
				// join to the COA Header -- COAHEAD
				fieldList.append("QAATYPE, ");
				fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QAPACOAH ");
				fromStatement.append(" ON    QAACONO = OACONO AND QAADIVI = OADIVI ");
				fromStatement.append("   AND QAAORDER = OAORNO AND QAATYPE = 'CO' ");
				
				// Sales Order Line Item File -- OOLINE
				fieldList.append("OBCONO, OBDIVI, OBORNO, OBPONR, OBPOSX, ");
				fieldList.append("OBITNO, OBITDS, ");
				fromStatement.append("LEFT OUTER JOIN " + library + ".OOLINE ");
				fromStatement.append(" ON   OACONO = OBCONO AND OADIVI = OBDIVI ");
				fromStatement.append("   AND OAORNO = OBORNO ");
				
				// join to the COA Line Values -- COALINE
				fieldList.append("QABTYPE, ");
				fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QAPBCOAL ");
				fromStatement.append(" ON   QABCONO = OACONO AND QABDIVI = OADIVI ");
				fromStatement.append("    AND QABTYPE = 'CO' AND QABORDER = OAORNO ");
				fromStatement.append("    AND QABPONR = OBPONR AND QABPOSX = OBPOSX ");
				
				// join to the Item Master File -- MITMAS
				fieldList.append("MMATMO, ");
				fromStatement.append("LEFT OUTER JOIN " + library + ".MITMAS ");
				fromStatement.append(" ON   MMCONO = OACONO AND MMITNO = OBITNO ");
				
				// Join to Attribute Model Lines - Attribute and Sequence -- MAMOLI
				fieldList.append("AEATMO, AEATID, AEANSQ, ");
				fromStatement.append("LEFT OUTER JOIN " + library + ".MAMOLI ");
				fromStatement.append(" ON   AECONO = OACONO AND AEATMO = MMATMO ");		
				
//				 Join to Attribute Master - MATRMA
				fieldList.append("AAATVC, ");
				fromStatement.append("LEFT OUTER JOIN " + library + ".MATRMA ");
				fromStatement.append("ON MMCONO = AACONO and AEATID = AAATID ");
				
				// join to the COA Attribute Lines -- COAATTR
				fieldList.append("QACTYPE ");
				fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QAPCCOAA ");
				fromStatement.append(" ON   QACCONO = OACONO AND QACDIVI = OADIVI ");
				fromStatement.append("    AND QACTYPE = 'CO' AND QACORDER = OAORNO ");
				fromStatement.append("    AND QACPONR = OBPONR AND QACPOSX = OBPOSX ");
				fromStatement.append("    AND QACATTR = AEATID ");
						
				//------------
				// WHERE
				whereStatement.append("WHERE OAORNO = '" + ServiceSalesOrder.editLengthSalesOrder(incomingValue.getInqSalesOrder()) + "' ");
				//------------
				// ORDER BY
				orderBy.append("ORDER BY OBPONR, OBPOSX, AEANSQ ");
			}
			
			//Distribution Order
			if (inRequestType.equals("testByDO"))
			{
				// Distribution Order  File -- MGHEAD
				fieldList.append("MGCONO, MGTRNR, MGFACI, MGWHLO, MGTWLO,  ");
				fieldList.append("MGRIDT, MGRESP, MGLMDT, MGCHID, MGTRDT, ");
				fromStatement.append("FROM " + library + ".MGHEAD ");
				
				// Join Warehouse Master File -- MITWHL
				fieldList.append("MWWHNM, MWDIVI, ");
				fromStatement.append("LEFT OUTER JOIN " + library + ".MITWHL ");
				fromStatement.append("ON MGCONO = MWCONO AND MGTWLO = MWWHLO ");
				
				// join to the COA Header -- COAHEAD
				fieldList.append("QAATYPE, ");
				fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QAPACOAH ");
				fromStatement.append("ON  QAACONO = MGCONO AND QAADIVI = ' ' ");
				fromStatement.append("AND QAATYPE = 'DO' AND QAAORDER = MGTRNR ");
				
				// Distribution Order Line Item File -- MGLINE
				fieldList.append("MRCONO, MRTRNR, MRPONR, MRPOSX, ");
				fieldList.append("MRITNO, MRITDS, ");
				fromStatement.append("LEFT OUTER JOIN " + library + ".MGLINE ");
				fromStatement.append("ON MGCONO = MRCONO AND MGTRNR = MRTRNR ");
				
				// join to the COA Line Values -- COALINE
				fieldList.append("QABTYPE, ");
				fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QAPBCOAL ");
				fromStatement.append("ON MRCONO = QABCONO AND QABDIVI = QAADIVI ");
				fromStatement.append("AND QABTYPE = QAATYPE AND MRTRNR = QABORDER ");
				fromStatement.append("AND MRPONR = QABPONR AND MRPOSX = QABPOSX ");
				
				// join to the Item Master File -- MITMAS
				fieldList.append("MMATMO, ");
				fromStatement.append("LEFT OUTER JOIN " + library + ".MITMAS ");
				fromStatement.append("ON MRCONO = MMCONO AND MRITNO = MMITNO ");
				
				// Join to Attribute Model Lines - Attribute and Sequence -- MAMOLI
				fieldList.append("AEATMO, AEATID, AEANSQ, ");
				fromStatement.append("LEFT OUTER JOIN " + library + ".MAMOLI ");
				fromStatement.append("ON MMCONO = AECONO AND MMATMO = AEATMO ");		
				
				// join to the COA Attribute Lines -- COAATTR
				fieldList.append("QACTYPE ");
				fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QAPCCOAA ");
				fromStatement.append("ON MRCONO = QACCONO AND QACDIVI = QAADIVI ");
				fromStatement.append("AND QACTYPE = QAATYPE AND MRTRNR = QACORDER ");
				fromStatement.append("AND MRPONR = QACPONR AND MRPOSX = QACPOSX ");
				fromStatement.append("AND AEATID = QACATTR ");
						
				//------------
				// WHERE
				whereStatement.append("WHERE MGCONO = '100' ");
				whereStatement.append("AND MGTRNR = '" + ServiceSalesOrder.editLengthSalesOrder(incomingValue.getInqDistributionOrder()) + "' ");
				//------------
				// ORDER BY
				orderBy.append("ORDER BY MRPONR, MRPOSX, AEANSQ ");
			}
			
			
			if (inRequestType.equals("testByLot"))
			{
				//Lot Master File -- MILOMA
				fieldList.append("LMCONO, LMITNO, LMBANO, ");
				fromStatement.append("FROM " + library + ".MILOMA ");
				
				// join to the Item Master File -- MITMAS
				fieldList.append("MMATMO, ");
				fromStatement.append("LEFT OUTER JOIN " + library + ".MITMAS ");
				fromStatement.append("ON LMCONO = MMCONO AND LMITNO = MMITNO ");
				
				// Join to Attribute Model Lines - Attribute and Sequence -- MAMOLI
				fieldList.append("AEATMO, AEATID, AEANSQ, ");
				fromStatement.append("LEFT OUTER JOIN " + library + ".MAMOLI ");
				fromStatement.append("ON MMCONO = AECONO AND MMATMO = AEATMO ");	
				
				// Join to Attribute Master - MATRMA
				fieldList.append("AAATVC, ");
				fromStatement.append("LEFT OUTER JOIN " + library + ".MATRMA ");
				fromStatement.append("ON MMCONO = AACONO and AEATID = AAATID ");
				
				// join to the COA Header -- COAHEAD
				fieldList.append("QAACONO, QAADIVI, QAATYPE, QAALOT, ");
				fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QAPACOAH ");
				fromStatement.append("ON  QAACONO = LMCONO ");
				fromStatement.append("AND QAALOT = LMBANO AND QAATYPE = 'LOT' ");
				
				// join to the COA Line Values -- COALINE
				fieldList.append("QABTYPE, ");
				fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QAPBCOAL ");
				fromStatement.append("ON LMCONO = QABCONO ");
				fromStatement.append("AND QABTYPE = QAATYPE AND LMBANO = QABLOT ");
				//fromStatement.append("AND MRPONR = QABPONR AND MRPOSX = QABPOSX ");
				
				// join to the COA Attribute Lines -- COAATTR
				fieldList.append("QACTYPE ");
				fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QAPCCOAA ");
				fromStatement.append("ON LMCONO = QACCONO ");
				fromStatement.append("AND QACTYPE = QAATYPE AND LMBANO = QACLOT ");
				//fromStatement.append("AND MRPONR = QACPONR AND MRPOSX = QACPOSX ");
				fromStatement.append("AND AEATID = QACATTR ");
				
				
				//------------
				// WHERE
				whereStatement.append("WHERE LMBANO = '" + incomingValue.getInqLot() + "' ");
				//------------
				// ORDER BY
				orderBy.append("ORDER BY AEANSQ ");
				
				
			}
			sqlString.append("SELECT " + fieldList.toString() + fromStatement.toString() + whereStatement.toString() + orderBy.toString());

		}
		
		
		
//		  TEST -- To see if the QTY for this Sales Order Lot is > 0
//		     Will add all the Transactions together, so if a lot is added to the Sales Order and Later Removed this should say 0
		if (inRequestType.equals("testQTY")) 
		{
			// Sent in Customer Order, Item, and Then LOT
			String orderNo = (String) requestValues.elementAt(0);
			String orderLineNo = (String) requestValues.elementAt(1);
			String itemNumber = (String) requestValues.elementAt(2);
			String lotNumber  = (String) requestValues.elementAt(3);
			String coaType     = (String) requestValues.elementAt(4);
			String cono		   = (String) requestValues.elementAt(5);
			String doType	   = (String) requestValues.elementAt(6);
			
			// Change from a Summed SQL statement to a Read and Add SQL
			//sqlString.append("SELECT MTBANO, SUM(MTTRQT) ");
			sqlString.append("SELECT MTBANO, MTTRQT ");
			sqlString.append("FROM " + library + ".MITTRA ");
			//fromStatement.append("  ON  MTCONO = OACONO AND MTRIDN = OAORNO ");
			//fromStatement.append(" AND  MTRIDL = OBPONR AND MTITNO = OBITNO ");
			//------------
			// WHERE
			sqlString.append("WHERE MTCONO = " + cono + " ");
			sqlString.append("AND MTRIDN = '" + orderNo + "' ");
//			sqlString.append("  AND MTRIDL = '" + customerOrderLine + "' ");
			sqlString.append("  AND MTITNO = '" + itemNumber + "' ");
			sqlString.append("  AND MTBANO = '" + lotNumber + "' ");
			
			if (coaType.trim().equals("DO"))
			{
				sqlString.append("AND MTTTYP = " + doType.trim() + " ");
				sqlString.append("AND MTSLTP <> ' ' ");
			}
				
			//------------
			// GROUP BY
			//sqlString.append("GROUP BY MTBANO ");
		}
//   END of the Test Section		
// ----------------------------------------------------------------------------------------------------
		
//-----------------------------------------------------------------------------------------------------
//  FIND		
//-----------------------------------------------------------------------------------------------------		
//    SQL To Retrieve the COA Information from the Files for Display on the screen
		
		if (inRequestType.equals("coaByCO") || // by Sales Order Order by COA Attribute Sequence -- LIVE Display
			inRequestType.equals("coaByCOSeq")) // by Sales Order Order by the Attribute Model Sequence -- Update Sequence
		{
			InqCOA incomingValue = (InqCOA) requestValues.elementAt(0);
			// qualify file fields for use.
			StringBuffer fieldList = new StringBuffer();
			StringBuffer fromStatement = new StringBuffer();
			StringBuffer whereStatement = new StringBuffer();
			StringBuffer orderBy = new StringBuffer();
			// build the sql statement.
			// The FROM will be based on SO, DO or Lot ** Those main files
			//    Because if it is not in the COAHEAD file, 
			//         we will NEED to add a record to this file
			
			// Sales Order  File -- OOHEAD
			fieldList.append("OACONO, OADIVI, OAORNO, OAFACI, OAWHLO, OACUNO, ");
			fieldList.append("OARLDT, OACUOR, OARESP, OALMDT, OACHID, OAORTP, ");
			fieldList.append("OASMCD, OACHID, OAORDT, ");
			fromStatement.append("FROM " + library + ".OOHEAD ");
				
			// Join to the Customer Master File -- OCUSMA
			fieldList.append("OKCONO, OKDIVI, OKCUNO, OKCUNM, ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".OCUSMA ");
			fromStatement.append(" ON    OACONO = OKCONO AND OADIVI = OKDIVI ");
			fromStatement.append("   AND OACUNO = OKCUNO ");
				
			// Join to the Customer Address File -- OCUSAD
			fieldList.append("OPCONO, OPDIVI, OPCUNO, OPADID, OPCUA1, OPCUA2, ");
			fieldList.append("OPCUA3, OPCUA4, OPPONO, OPECAR, OPTOWN, ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".OCUSAD ");
			fromStatement.append(" ON    OACONO = OPCONO AND OADIVI = OPDIVI ");
			fromStatement.append("   AND OACUNO = OPCUNO AND OAADID = OPADID ");
				
			// join to the COA Header -- COAHEAD
			fieldList.append("QAATYPE, QAAORDER, QAALOT, ");
			fieldList.append("QAACOMMENT, QAASIGN, QAADTFMT, ");
			fieldList.append("QAASUNIT, QAASAVE, QAASMINMAX, QAASTARGET, QAASMODEL, ");
			fieldList.append("QAASSPEC, ");
			fieldList.append("QAAEMAIL1, QAAEMAIL2, QAAEMAIL3, QAAEMAIL4, ");
			fieldList.append("QAAEMAIL5, QAAEMAIL6, QAAEMAIL7, QAAEMAIL8, ");
			fieldList.append("QAAFAX1, QAAFAX2, QAAFAX3, QAAFAX4, ");
			fieldList.append("QAAATTN1, QAAATTN2, QAAATTN3, QAAATTN4, ");
			fieldList.append("QAALSDATE, QAACRDATE, QAACRTIME, QAACRUSER, ");
			fieldList.append("QAAUPDATE, QAAUPTIME, QAAUPUSER, ");
			fromStatement.append("INNER JOIN " + ttlibrary + ".QALACOAH1 ");
			fromStatement.append(" ON    QAACONO = OACONO AND QAADIVI = OADIVI ");
			fromStatement.append("   AND QAAORDER = OAORNO AND QAATYPE = 'CO' ");
				
			// join to the Line Item for the Sales Order -- OOLINE
			fieldList.append("OBCONO, OBDIVI, OBORNO, OBPONR, OBPOSX, ");
			fieldList.append("OBFACI, OBWHLO, OBITNO, OBITDS, ");
			fromStatement.append("INNER JOIN " + library + ".OOLINE ");
			fromStatement.append(" ON  OACONO = OBCONO AND OADIVI = OBDIVI ");
			fromStatement.append(" AND OAORNO = OBORNO ");
				
			// join to the COA Line Item File -- COALINE
			fieldList.append("QABCONO, QABDIVI, QABTYPE, QABORDER, QABLOT, ");
			fieldList.append("QABCOMMENT, QABSIGN, QABSPEC, QABPONR, QABPOSX, ");
			fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QALBCOAL1 ");
			fromStatement.append(" ON  OBCONO = QABCONO AND OBDIVI = QABDIVI ");
			fromStatement.append(" AND OBORNO = QABORDER AND QABTYPE = 'CO' ");
			fromStatement.append(" AND OBPONR = QABPONR AND OBPOSX = QABPOSX ");
				
			// join to the Item Master File -- MITMAS
			fieldList.append("MMCONO, MMITNO, MMITDS, MMITGR, MMITCL, ");
			fieldList.append("MMITTY, MMATMO, MMUNMS, ");	
			fromStatement.append("LEFT OUTER JOIN " + library + ".MITMAS ");
			fromStatement.append(" ON OBCONO = MMCONO AND OBITNO = MMITNO ");
			
			// join to the Attributes listed based on the Line Item -- COAATTR
			fieldList.append("QACCONO, QACDIVI, QACTYPE, QACORDER, QACLOT, QACPONR, ");
			fieldList.append("QACPOSX, QACATTR, QACATTRSEQ, ");
			fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QALCCOAA1 ");
			fromStatement.append(" ON  OBCONO = QACCONO AND OBDIVI = QACDIVI ");
			fromStatement.append(" AND OBORNO = QACORDER AND QACTYPE = 'CO' ");
			fromStatement.append(" AND OBPONR = QACPONR AND OBPOSX = QACPOSX ");
			if (inRequestType.equals("coaByCO")) // by Sales Order Order by COA Attribute Sequence
			{	
				fromStatement.append("AND QACATTRSEQ <> 0 ");
			}
			
			// Join to the Attribute Model Header -- Through the Attribute Model on the Item Master -- MAMOHE
					// get the Attribute Model Description
			fieldList.append("ADCONO, ADATMO, ADTX40, ADTX15, ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".MAMOHE ");
			fromStatement.append(" ON  ADCONO = MMCONO AND ADATMO = MMATMO ");
				
			// Attribute Model Lines -- MAMOLI
			fieldList.append("AECONO, AEATMO, AEATID, AEANSQ, ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".MAMOLI ");
			fromStatement.append(" ON  AECONO = ADCONO AND ADATMO = AEATMO ");
			fromStatement.append("AND aeatid = qacattr "); //wth 20090708
//				
//			// Attributes -- MATRMA
			fieldList.append("AACONO, AAATID, AAATVC, AATX30, AATX15, AADCCD, ");
			fromStatement.append("INNER JOIN " + library + ".MATRMA ");
			fromStatement.append(" ON  AACONO = AECONO AND AAATID = QACATTR ");
			// TWalton - 20100610 - Commented out
		//	if (inRequestType.equals("coaByCO")) // by Sales Order Order by COA Attribute Sequence
		//	{	
		//	  fromStatement.append("AND (AAATVC = 1 OR AAATVC = 2) "); //twalton 20100105	
		//	}	
			
			// Unit of Measure Description CSYTAB
			fieldList.append("CTTX15, ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".CSYTAB ");
			fromStatement.append("  ON CTCONO = AACONO AND CTSTCO = 'UNIT' ");
			fromStatement.append(" AND CTSTKY = AAUNMS ");
			
			// Attribute Hi-Lo Values (setup) -- MATVAV
			fieldList.append("AJCONO, AJATID, AJOBV1, AJANUF, AJANUT, "); 
			fromStatement.append("LEFT OUTER JOIN " + library + ".MATVAV "); 
			fromStatement.append(" ON  AJCONO = AECONO AND AJATID = QACATTR "); 
			//fromStatement.append(" ON  AJCONO = AECONO AND AJATID = AEATID "); // already commented out
			fromStatement.append(" AND AJOBV1 = MMATMO "); 
			//fromStatement.append("AND ajaalf = 'USA' "); //wth 20090708 - file not needed
			//fromStatement.append(" AND AJOBV1 = AEATMO AND AAATVC = 2 "); // already commented out
			
			// join to the Shipping Transaction -- MITTRA -- to get the lot numbers
			fieldList.append("MTCONO, MTITNO, MTTTYP, MTBANO, MTRIDN, MTRIDL, MTLMTS,  ");
			fieldList.append("MTTRQT, MTTRDT, ");
			fromStatement.append("INNER JOIN " + library + ".MITTRAZ02 ");
			fromStatement.append("  ON  MTCONO = OACONO AND MTRIDN = OAORNO ");
			fromStatement.append(" AND  MTRIDL = OBPONR AND MTITNO = OBITNO ");
			
			// join to the Lot Master File -- MILOMA -- to get the Manufacture Date.
			fieldList.append("LMCONO, LMITNO, LMBANO, LMMFDT, LMEXPI, ");
			fromStatement.append("INNER JOIN " + library + ".MILOMA ");
			fromStatement.append("  ON LMCONO = OACONO AND LMITNO = OBITNO ");
			fromStatement.append(" AND LMBANO = MTBANO ");
			
			// join to the Lot Attribute File -- MIATTRX40
			fieldList.append("AGCONO, AGATID, AGITNO, AGBANO, AGATVA, AGATVN, AGTXID ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".MIATTR ");
			fromStatement.append("  ON AGCONO = OACONO AND AGITNO = OBITNO ");
			fromStatement.append(" AND AGBANO = MTBANO and AGATID = AAATID ");
			fromStatement.append(" AND AGATNR = LMATNR ");
			
			//------------
			// WHERE
			whereStatement.append("WHERE OAORNO = '" + ServiceSalesOrder.editLengthSalesOrder(incomingValue.getInqSalesOrder()) + "' ");
			if (inRequestType.equals("coaByCOSeq")) // by Sales Order Order by COA Attribute Sequence for ONLY one Line
			{
				whereStatement.append("AND QACPONR = " + incomingValue.getLineNumber() + " ");
				whereStatement.append("AND QACPOSX = " + incomingValue.getLineSuffix() + " ");
			}
			//-------------
			// ORDER
			if (inRequestType.equals("coaByCOSeq")) // by Sales Order Order by COA Attribute Sequence
			    orderBy.append("ORDER BY MTRIDN, MTRIDL, MTBANO, AAATID, AEANSQ  ");
			else   // by Sales Order Order by the Attribute Model Sequence
				orderBy.append("ORDER BY MTRIDN, MTRIDL, MTBANO, QACATTRSEQ, QACATTR ");
			
			sqlString.append("SELECT " + fieldList.toString() + fromStatement.toString() + whereStatement.toString() + orderBy.toString());
		}
		
		
		
		// SQL To Retrieve the COA Information from the Files for Display on the screen
		
		if (inRequestType.equals("coaByDO") ||  // by Distribution Order by COA Attribute Sequence -- LIVE Display
			inRequestType.equals("coaByDOSeq")) // by Distribution Order by the Attribute Model Sequence -- Update Sequence
		{
			InqCOA incomingValue = (InqCOA) requestValues.elementAt(0);
			// qualify file fields for use.
			StringBuffer fieldList = new StringBuffer();
			StringBuffer fromStatement = new StringBuffer();
			StringBuffer whereStatement = new StringBuffer();
			StringBuffer orderBy = new StringBuffer();
			// build the sql statement.
			// The FROM will be based on SO, DO or Lot ** Those main files
			//    Because if it is not in the COAHEAD file, 
			//         we will NEED to add a record to this file
			
			// Sales Order  File -- MGHEAD
			fieldList.append("MGCONO, MGTRNR, MGFACI, MGWHLO, MGTWLO, ");
			fieldList.append("MGRIDT, MGRESP, MGLMDT, MGCHID, MGTRDT, ");
			fromStatement.append("FROM " + library + ".MGHEAD ");
				
			// Join to the Customer Master File -- MITWHL
			fieldList.append("MWWHNM, ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".MITWHL ");
			fromStatement.append("ON MGCONO = MWCONO AND MGTWLO = MWWHLO ");
				
			// join to the COA Header -- COAHEAD
			fieldList.append("QAATYPE, QAAORDER, QAALOT, ");
			fieldList.append("QAACOMMENT, QAASIGN, QAADTFMT, ");
			fieldList.append("QAASUNIT, QAASAVE, QAASMINMAX, QAASTARGET, QAASMODEL, ");
			fieldList.append("QAASSPEC, ");
			fieldList.append("QAAEMAIL1, QAAEMAIL2, QAAEMAIL3, QAAEMAIL4, ");
			fieldList.append("QAAEMAIL5, QAAEMAIL6, QAAEMAIL7, QAAEMAIL8, ");
			fieldList.append("QAAFAX1, QAAFAX2, QAAFAX3, QAAFAX4, ");
			fieldList.append("QAAATTN1, QAAATTN2, QAAATTN3, QAAATTN4, ");
			fieldList.append("QAALSDATE, QAACRDATE, QAACRTIME, QAACRUSER, ");
			fieldList.append("QAAUPDATE, QAAUPTIME, QAAUPUSER, ");
			fromStatement.append("INNER JOIN " + ttlibrary + ".QAPACOAH ");
			fromStatement.append("ON MGCONO = QAACONO AND QAADIVI = ' ' ");
			fromStatement.append("AND QAATYPE = 'DO' AND MGTRNR = QAAORDER ");
				
			// join to the Line Item for the Sales Order -- MGLINE
			fieldList.append("MRCONO, MRTRNR, MRPONR, MRPOSX, ");
			fieldList.append("MRITNO, MRITDS, ");
			fromStatement.append("INNER JOIN " + library + ".MGLINE ");
			fromStatement.append(" ON  MGCONO = MRCONO AND MGTRNR = MRTRNR ");
				
			// join to the COA Line Item File -- COALINE
			fieldList.append("QABCONO, QABDIVI, QABTYPE, QABORDER, QABLOT, ");
			fieldList.append("QABCOMMENT, QABSIGN, QABSPEC, QABPONR, QABPOSX, ");
			fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QAPBCOAL ");
			fromStatement.append("ON  MRCONO = QABCONO and QABDIVI = QAADIVI ");
			fromStatement.append("AND QABTYPE = QAATYPE AND MRTRNR = QABORDER ");
			fromStatement.append("AND MRPONR = QABPONR AND MRPOSX = QABPOSX ");
				
			// join to the Item Master File -- MITMAS
			fieldList.append("MMCONO, MMITNO, MMITDS, MMITGR, MMITCL, ");
			fieldList.append("MMITTY, MMATMO, MMUNMS, ");	
			fromStatement.append("LEFT OUTER JOIN " + library + ".MITMAS ");
			fromStatement.append(" ON MRCONO = MMCONO AND MRITNO = MMITNO ");
			
			// join to the Attributes listed based on the Line Item -- COAATTR
			fieldList.append("QACCONO, QACDIVI, QACTYPE, QACORDER, QACLOT, QACPONR, ");
			fieldList.append("QACPOSX, QACATTR, QACATTRSEQ, ");
			fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QAPCCOAA ");
			fromStatement.append("ON  MRCONO = QACCONO AND QACDIVI = QAADIVI ");
			fromStatement.append("AND QACTYPE = QAATYPE AND MRTRNR = QACORDER ");
			fromStatement.append("AND MRPONR = QACPONR AND MRPOSX = QACPOSX ");
			
			if (inRequestType.equals("coaByDO")) // by Distribution Order by COA Attribute Sequence
				fromStatement.append("AND QACATTRSEQ <> 0 ");
			
			// Join to the Attribute Model Header -- Through the Attribute Model on the Item Master -- MAMOHE
					// get the Attribute Model Description
			fieldList.append("ADCONO, ADATMO, ADTX40, ADTX15, ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".MAMOHE ");
			fromStatement.append(" ON  ADCONO = MMCONO AND ADATMO = MMATMO ");
				
			// Attribute Model Lines -- MAMOLI
			fieldList.append("AECONO, AEATMO, AEATID, AEANSQ, ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".MAMOLI ");
			fromStatement.append(" ON AECONO = ADCONO AND ADATMO = AEATMO ");
			fromStatement.append("AND AEATID = QACATTR "); //wth 20090708 line up the attributes
//				
//			// Attributes -- MATRMA
			fieldList.append("AACONO, AAATID, AAATVC, AATX30, AATX15, AADCCD, ");
			fromStatement.append("INNER JOIN " + library + ".MATRMA ");
			fromStatement.append(" ON  AACONO = AECONO AND AAATID = QACATTR ");
			if (inRequestType.equals("coaByDO")) // by Sales Order Order by COA Attribute Sequence
			{	
			  fromStatement.append("AND (AAATVC = 1 OR AAATVC = 2) "); //twalton 20100107	
			}		
			// Attribute Hi-Lo Values (setup) -- MATVAV
			fieldList.append("AJCONO, AJATID, AJOBV1, AJANUF, AJANUT, "); //wth 20090708 - file not needed
			fromStatement.append("LEFT OUTER JOIN " + library + ".MATVAV "); //wth 20090708 - file not needed
			fromStatement.append(" ON  AJCONO = AECONO AND AJATID = QACATTR "); //wth 20090708 - file not needed
			//fromStatement.append(" ON  AJCONO = AECONO AND AJATID = AEATID "); // already commented out
			fromStatement.append(" AND AJOBV1 = MMATMO "); //wth 20090708 - file not needed
			//fromStatement.append("AND ajaalf = 'USA' "); //wth 20090708 - file not needed
			//fromStatement.append(" AND AJOBV1 = AEATMO AND AAATVC = 2 "); // already commented out
			
			// join to the Shipping Transaction -- MITTRA -- to get the lot numbers
			fieldList.append("MTCONO, MTITNO, MTTTYP, MTBANO, MTRIDN, MTRIDL, MTLMTS,  ");
			fieldList.append("MTTRQT, MTTRDT, ");
			fromStatement.append("INNER JOIN " + library + ".MITTRAZ04 ");
			fromStatement.append("ON  MRCONO = MTCONO AND MRTRNR = MTRIDN ");
			fromStatement.append("AND MRPONR = MTRIDL AND MRITNO = MTITNO ");
			fromStatement.append("AND MGWHLO = MTWHLO AND MTTTYP = 52 ");
			
			// join to the Lot Master File -- MILOMA -- to get the Manufacture Date.
			fieldList.append("LMCONO, LMITNO, LMBANO, LMMFDT, LMEXPI, ");
			fromStatement.append("INNER JOIN " + library + ".MILOMA ");
			fromStatement.append("ON  MRCONO = LMCONO AND MRITNO = LMITNO ");
			fromStatement.append("AND MTBANO = LMBANO ");
			
			// join to the Lot Attribute File -- MIATTRX40
			fieldList.append("AGCONO, AGATID, AGITNO, AGBANO, AGATVA, AGATVN ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".MIATTR ");
			fromStatement.append("ON  MRCONO = AGCONO AND MRITNO = AGITNO ");
			fromStatement.append("AND MTBANO = AGBANO and AAATID = AGATID ");
			fromStatement.append("AND LMATNR = AGATNR ");
			//------------
			// WHERE
			whereStatement.append("WHERE MGCONO = '100' ");
			whereStatement.append("AND MGTRNR = '" + ServiceSalesOrder.editLengthSalesOrder(incomingValue.getInqDistributionOrder()) + "' ");
			if (inRequestType.equals("coaByDOSeq")) // by Distribution Order by COA Attribute Sequence for ONLY one Line
			{
				whereStatement.append("AND QACPONR = " + incomingValue.getLineNumber() + " ");
				whereStatement.append("AND QACPOSX = " + incomingValue.getLineSuffix() + " ");
			}
			//-------------
			// ORDER
			if (inRequestType.equals("coaByDOSeq")) // by Distribution Order by COA Attribute Sequence
			    orderBy.append("ORDER BY MTRIDN, MTRIDL, MTBANO, AAATID, AEANSQ  ");
			else   // by Distribution Order by the Attribute Model Sequence
				orderBy.append("ORDER BY MTRIDN, MTRIDL, MTBANO, QACATTRSEQ, QACATTR ");
			
			sqlString.append("SELECT " + fieldList.toString() + fromStatement.toString() + whereStatement.toString() + orderBy.toString());
		}
		
		//get lot attributes for presentation.
		if (inRequestType.equals("coaByLot") ||  // by Lot Number by COA Attribute Sequence -- LIVE Display
			inRequestType.equals("coaByLotSeq")) // by Lot Number Order by the Attribute Model Sequence -- Update Sequence
		{	
			InqCOA incomingValue = (InqCOA) requestValues.elementAt(0);
			
			// qualify file fields for use.
			StringBuffer fieldList = new StringBuffer();
			StringBuffer fromStatement = new StringBuffer();
			StringBuffer whereStatement = new StringBuffer();
			StringBuffer orderBy = new StringBuffer();
			
			fieldList.append("LMCONO, LMITNO, LMBANO, LMMFDT, LMEXPI, ");
			fromStatement.append("FROM " + library + ".MILOMA ");
			
//			 join to the Item Master File -- MITMAS
			fieldList.append("MMCONO, MMITNO, MMITDS, MMITGR, MMITCL, ");
			fieldList.append("MMITTY, MMATMO, MMUNMS, ");	
			fromStatement.append("INNER JOIN " + library + ".MITMAS ");
			fromStatement.append(" ON LMCONO = MMCONO AND LMITNO = MMITNO ");
			
//			 join to the COA Header -- COAHEAD
			fieldList.append("QAATYPE, QAAORDER, QAALOT, ");
			fieldList.append("QAACOMMENT, QAASIGN, QAADTFMT, ");
			fieldList.append("QAASUNIT, QAASAVE, QAASMINMAX, QAASTARGET, QAASMODEL, ");
			fieldList.append("QAASSPEC, ");
			fieldList.append("QAAEMAIL1, QAAEMAIL2, QAAEMAIL3, QAAEMAIL4, ");
			fieldList.append("QAAEMAIL5, QAAEMAIL6, QAAEMAIL7, QAAEMAIL8, ");
			fieldList.append("QAAFAX1, QAAFAX2, QAAFAX3, QAAFAX4, ");
			fieldList.append("QAAATTN1, QAAATTN2, QAAATTN3, QAAATTN4, ");
			fieldList.append("QAALSDATE, QAACRDATE, QAACRTIME, QAACRUSER, ");
			fieldList.append("QAAUPDATE, QAAUPTIME, QAAUPUSER, ");
			fromStatement.append("INNER JOIN " + ttlibrary + ".QALACOAH1 ");
			fromStatement.append("ON LMCONO = QAACONO ");
			fromStatement.append("AND LMBANO = QAALOT AND QAATYPE = 'LOT' ");
			
//			 join to the COA Line Item File -- COALINE
			fieldList.append("QABCONO, QABDIVI, QABTYPE, QABORDER, QABLOT, ");
			fieldList.append("QABCOMMENT, QABSIGN, QABSPEC, QABPONR, QABPOSX, ");
			fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QALBCOAL1 ");
			fromStatement.append("ON  LMCONO = QABCONO ");
			fromStatement.append("AND LMBANO = QABLOT AND QABTYPE = QAATYPE ");
			
//			 join to the Attributes listed based on the Line Item -- COAATTR
			fieldList.append("QACCONO, QACDIVI, QACTYPE, QACORDER, QACLOT, QACPONR, ");
			fieldList.append("QACPOSX, QACATTR, QACATTRSEQ, ");
			fromStatement.append("LEFT OUTER JOIN " + ttlibrary + ".QALCCOAA1 ");
			fromStatement.append("ON  LMCONO = QACCONO ");
			fromStatement.append("AND LMBANO = QACLOT AND QACTYPE = QAATYPE ");
			
			if (inRequestType.equals("coaByLot")) // by Lot Number by COA Attribute Sequence -- Do not want to include the 0's
			   fromStatement.append("AND QACATTRSEQ <> 0 ");
			
			// Join to the Attribute Model Header -- Through the Attribute Model on the Item Master -- MAMOHE
					// get the Attribute Model Description
			fieldList.append("ADCONO, ADATMO, ADTX40, ADTX15, ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".MAMOHE ");
			fromStatement.append(" ON  ADCONO = MMCONO AND ADATMO = MMATMO ");
				
			// Attribute Model Lines -- MAMOLI
			fieldList.append("AECONO, AEATMO, AEATID, AEANSQ, ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".MAMOLI ");
			fromStatement.append(" ON  AECONO = ADCONO AND ADATMO = AEATMO ");
			fromStatement.append("AND aeatid = qacattr "); //wth 20090708 line up the attributes
//				
//			// Attributes -- MATRMA
			fieldList.append("AACONO, AAATID, AAATVC, AATX30, AATX15, AADCCD, AAUNMS, ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".MATRMA ");
			fromStatement.append(" ON  AACONO = AECONO AND AAATID = QACATTR ");
			
			// Unit of Measure Description CSYTAB
			fieldList.append("CTTX15, ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".CSYTAB ");
			fromStatement.append("  ON CTCONO = AACONO AND CTSTCO = 'UNIT' ");
			fromStatement.append(" AND CTSTKY = AAUNMS ");
			
			// Attribute Hi-Lo Values (setup) -- MATVAV
			fieldList.append("AJCONO, AJATID, AJOBV1, AJANUF, AJANUT, "); 
			fromStatement.append("LEFT OUTER JOIN " + library + ".MATVAV "); 
			fromStatement.append(" ON  AJCONO = AECONO AND AJATID = QACATTR "); 
			//fromStatement.append(" ON  AJCONO = AECONO AND AJATID = AEATID "); // already commented out
			fromStatement.append(" AND AJOBV1 = MMATMO "); 
			//fromStatement.append("AND ajaalf = 'USA' "); //wth 20090708 - file not needed
			//fromStatement.append(" AND AJOBV1 = AEATMO AND AAATVC = 2 "); // already commented out
			
//			// Attribute Values by Lot -- MIATTR			
			fieldList.append("AGCONO, AGATID, AGITNO, AGBANO, AGATVA, AGATVN, AGTXID ");
			fromStatement.append("LEFT OUTER JOIN " + library + ".MIATTR ");
			fromStatement.append("ON  LMCONO = AGCONO AND LMITNO = AGITNO ");
			fromStatement.append("AND LMBANO = AGBANO and AAATID = AGATID ");
//			fromStatement.append("AND LMATNR = AGATNR ");
			
//			------------
			// WHERE
			whereStatement.append("WHERE LMBANO = '" + incomingValue.getInqLot().trim() + "' ");
//			-------------
			// ORDER
			if (inRequestType.equals("coaByLotSeq")) // by Distribution Order by COA Attribute Sequence
			    orderBy.append("ORDER BY LMITNO, LMBANO, AAATID, AEANSQ, AGLMDT desc  ");
			else   // by Distribution Order by the Attribute Model Sequence
				orderBy.append("ORDER BY LMITNO, LMBANO, QACATTRSEQ, QACATTR, AGLMDT desc ");
			
			sqlString.append("SELECT " + fieldList.toString() + fromStatement.toString() + whereStatement.toString() + orderBy.toString());
			
		}
  // End of the Find the Sales Order Data Section		
//-----------------------------------------------------------------------------
		
//------------------------------------------------------------------------------
// INSERT -- Insert Methods 
//------------------------------------------------------------------------------  
//	 *** HEADER SECTION	********************************************************	
		if (inRequestType.equals("insertCOHead"))
		{
			ResultSet rs = (ResultSet) requestValues.elementAt(0);
			InqCOA  icoa = (InqCOA)    requestValues.elementAt(1);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlibrary + ".QAPACOAH ");
			sqlString.append("VALUES(");
			sqlString.append(rs.getString("OACONO").trim() + ", "); //QAACONO - Comapany
		  	sqlString.append("'" + rs.getString("OADIVI").trim() + "', "); // QAADIVI - Division
			//COA by Sales Order - Customer Order - CO
			sqlString.append("'CO', "); // QAATYPE - Certificate of Analysis Type
			sqlString.append("'" + rs.getString("OAORNO").trim() + "', "); // QAAORDER - Sales Order Number
			sqlString.append("' ', "); // QAALOT - Lot Number
			sqlString.append("' ', "); //QAACOMMENT - Comment
		  	sqlString.append("' ', "); //QAASIGN - Signature
			sqlString.append("' ', "); // Date Format
			sqlString.append("' ', "); // Show Units
			sqlString.append("' ', "); // Show Average
			sqlString.append("' ', "); // Show Min-Max
			sqlString.append("' ', "); // Show Target
			sqlString.append("' ', "); // Show Attribute Model Name
			sqlString.append("' ', "); // Show Spec
			sqlString.append("' ', "); // Email1
			sqlString.append("' ', "); // Email2
			sqlString.append("' ', "); // Email3
			sqlString.append("' ', "); // Email4
			sqlString.append("' ', "); // Email5
			sqlString.append("' ', "); // Email6	
			sqlString.append("' ', "); // Email7
			sqlString.append("' ', "); // Email8
			sqlString.append("' ', "); // Fax1
			sqlString.append("' ', "); // Fax2
			sqlString.append("' ', "); // Fax3
			sqlString.append("' ', "); // Fax4
			sqlString.append("' ', "); // Attention 1
			sqlString.append("' ', "); // Attention 2
			sqlString.append("' ', "); // Attention 3
			sqlString.append("' ', "); // Attention 4
			sqlString.append("' ', "); // Last Sent By Type
			sqlString.append("0, "); // Last Sent By Date
			String[] date = SystemDate.getSystemDate();
			String buildTime = "0";
			try
			{
				buildTime = date[8].substring(0, 2) + date[8].substring(3, 5) + date[8].substring(6, 8); 
				//System.out.println(buildTime);
			}
			catch(Exception e)
			{
				// Just catch the problem, and default the original 0 in as the time
				buildTime = "0";
			}
			sqlString.append(date[3] + ", "); // Creation Date
			sqlString.append(buildTime + ", "); // Creation Time
			sqlString.append("'" + icoa.getUpdateUser() + "', "); // Creation User
			sqlString.append(date[3] + ", "); // Update Date
			sqlString.append(buildTime + ", "); // Update Time
			sqlString.append("'" + icoa.getUpdateUser() + "' "); // Update User
			sqlString.append(") ");
		}
		
		
		
		if (inRequestType.equals("insertDOHead"))
		{
			ResultSet rs = (ResultSet) requestValues.elementAt(0);
			InqCOA  icoa = (InqCOA)    requestValues.elementAt(1);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlibrary + ".QAPACOAH ");
			sqlString.append("VALUES(");
			sqlString.append(rs.getString("MGCONO").trim() + ", "); //QAACONO - Comapany
		  	sqlString.append("' ', "); // QAADIVI - Division
			//COA by Distribution Order - DO
			sqlString.append("'DO', "); // QAATYPE - Certificate of Analysis Type
			sqlString.append("'" + rs.getString("MGTRNR").trim() + "', "); // QAAORDER - Order Number
			sqlString.append("' ', "); // QAALOT - Lot Number
			sqlString.append("' ', "); //QAACOMMENT - Comment
		  	sqlString.append("' ', "); //QAASIGN - Signature
			sqlString.append("' ', "); // Date Format
			sqlString.append("' ', "); // Show Units
			sqlString.append("' ', "); // Show Average
			sqlString.append("' ', "); // Show Min-Max
			sqlString.append("' ', "); // Show Target
			sqlString.append("' ', "); // Show Attribute Model Name
			sqlString.append("' ', "); // Show Spec
			sqlString.append("' ', "); // Email1
			sqlString.append("' ', "); // Email2
			sqlString.append("' ', "); // Email3
			sqlString.append("' ', "); // Email4
			sqlString.append("' ', "); // Email5
			sqlString.append("' ', "); // Email6	
			sqlString.append("' ', "); // Email7
			sqlString.append("' ', "); // Email8
			sqlString.append("' ', "); // Fax1
			sqlString.append("' ', "); // Fax2
			sqlString.append("' ', "); // Fax3
			sqlString.append("' ', "); // Fax4
			sqlString.append("' ', "); // Attention 1
			sqlString.append("' ', "); // Attention 2
			sqlString.append("' ', "); // Attention 3
			sqlString.append("' ', "); // Attention 4
			sqlString.append("' ', "); // Last Sent By Type
			sqlString.append("0, "); // Last Sent By Date
			String[] date = SystemDate.getSystemDate();
			String buildTime = "0";
			try
			{
				buildTime = date[8].substring(0, 2) + date[8].substring(3, 5) + date[8].substring(6, 8); 
				//System.out.println(buildTime);
			}
			catch(Exception e)
			{
				// Just catch the problem, and default the original 0 in as the time
				buildTime = "0";
			}
			sqlString.append(date[3] + ", "); // Creation Date
			sqlString.append(buildTime + ", "); // Creation Time
			sqlString.append("'" + icoa.getUpdateUser() + "', "); // Creation User
			sqlString.append(date[3] + ", "); // Update Date
			sqlString.append(buildTime + ", "); // Update Time
			sqlString.append("'" + icoa.getUpdateUser() + "' "); // Update User
			sqlString.append(") ");
		}
		
		
		
		if (inRequestType.equals("insertLotHead"))
		{
			ResultSet rs = (ResultSet) requestValues.elementAt(0);
			InqCOA  icoa = (InqCOA)    requestValues.elementAt(1);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlibrary + ".QAPACOAH ");
			sqlString.append("VALUES(");
			sqlString.append(rs.getString("LMCONO").trim() + ", "); //QAACONO - Comapany
		  	sqlString.append("' ', "); // QAADIVI - Division
		  	
			//COA by Lot - LOT
			sqlString.append("'LOT', "); // QAATYPE - Certificate of Analysis Type
			sqlString.append("' ', "); // QAAORDER - Order Number
			sqlString.append("'" + rs.getString("LMBANO").trim() + "', "); // QAALOT - Lot Number
			sqlString.append("' ', "); //QAACOMMENT - Comment
		  	sqlString.append("' ', "); //QAASIGN - Signature
			sqlString.append("' ', "); // Date Format
			sqlString.append("' ', "); // Show Units
			sqlString.append("' ', "); // Show Average
			sqlString.append("' ', "); // Show Min-Max
			sqlString.append("' ', "); // Show Target
			sqlString.append("' ', "); // Show Attribute Model Name
			sqlString.append("' ', "); // Show Spec
			sqlString.append("' ', "); // Email1
			sqlString.append("' ', "); // Email2
			sqlString.append("' ', "); // Email3
			sqlString.append("' ', "); // Email4
			sqlString.append("' ', "); // Email5
			sqlString.append("' ', "); // Email6	
			sqlString.append("' ', "); // Email7
			sqlString.append("' ', "); // Email8
			sqlString.append("' ', "); // Fax1
			sqlString.append("' ', "); // Fax2
			sqlString.append("' ', "); // Fax3
			sqlString.append("' ', "); // Fax4
			sqlString.append("' ', "); // Attention 1
			sqlString.append("' ', "); // Attention 2
			sqlString.append("' ', "); // Attention 3
			sqlString.append("' ', "); // Attention 4
			sqlString.append("' ', "); // Last Sent By Type
			sqlString.append("0, "); // Last Sent By Date
			String[] date = SystemDate.getSystemDate();
			String buildTime = "0";
			try
			{
				buildTime = date[8].substring(0, 2) + date[8].substring(3, 5) + date[8].substring(6, 8); 
				//System.out.println(buildTime);
			}
			catch(Exception e)
			{
				// Just catch the problem, and default the original 0 in as the time
				buildTime = "0";
			}
			sqlString.append(date[3] + ", "); // Creation Date
			sqlString.append(buildTime + ", "); // Creation Time
			sqlString.append("'" + icoa.getUpdateUser() + "', "); // Creation User
			sqlString.append(date[3] + ", "); // Update Date
			sqlString.append(buildTime + ", "); // Update Time
			sqlString.append("'" + icoa.getUpdateUser() + "' "); // Update User
			sqlString.append(") ");
		}
		
		
		
//		 *** LINE SECTION ************************************************************************		
		if (inRequestType.equals("insertCOLine"))
		{
			ResultSet rs = (ResultSet) requestValues.elementAt(0);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlibrary + ".QAPBCOAL ");
			sqlString.append("VALUES(");
			sqlString.append(rs.getString("OACONO").trim() + ", "); //QABCONO - Comapany
		  	sqlString.append("'" + rs.getString("OADIVI").trim() + "', "); // QABDIVI - Division
			//COA by Sales Order - Customer Order - CO
			sqlString.append("'CO', "); // QABTYPE - Certificate of Analysis Type
			sqlString.append("'" + rs.getString("OAORNO").trim() + "', "); // QABORDER - Sales Order Number
			sqlString.append("' ', "); // QABLOT - Lot Number
			sqlString.append("' ', "); //QABCOMMENT - Comment
		  	sqlString.append("' ', "); //QABSIGN - Signature
			sqlString.append("' ', "); //QABCPEC - Chosen Spec
			sqlString.append("'" + rs.getString("OBPONR").trim() + "', "); // QABPONR - Line Number
			sqlString.append("'" + rs.getString("OBPOSX").trim() + "' "); // QABPOSX - Line Suffix
			sqlString.append(") ");
		}				
		
		if (inRequestType.equals("insertDOLine"))
		{
			
			ResultSet rs = (ResultSet) requestValues.elementAt(0);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlibrary + ".QAPBCOAL ");
			sqlString.append("VALUES(");
			sqlString.append(rs.getString("MGCONO").trim() + ", "); //QABCONO - Comapany
		  	sqlString.append("' ', "); // QABDIVI - Division
			//COA by Distribution Order - Distribution Order - DO
			sqlString.append("'DO', "); // QABTYPE - Certificate of Analysis Type
			sqlString.append("'" + rs.getString("MGTRNR").trim() + "', "); // QABORDER - Distribution Order Number
			sqlString.append("' ', "); // QABLOT - Lot Number
			sqlString.append("' ', "); //QABCOMMENT - Comment
		  	sqlString.append("' ', "); //QABSIGN - Signature
			sqlString.append("' ', "); //QABCPEC - Chosen Spec
			sqlString.append("'" + rs.getString("MRPONR").trim() + "', "); // QABPONR - Line Number
			sqlString.append("'" + rs.getString("MRPOSX").trim() + "' "); // QABPOSX - Line Suffix
			sqlString.append(") ");
		}		
		
		if (inRequestType.equals("insertLotLine"))
		{
			ResultSet rs = (ResultSet) requestValues.elementAt(0);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlibrary + ".QAPBCOAL ");
			sqlString.append("VALUES(");
			sqlString.append(rs.getString("LMCONO").trim() + ", "); //QABCONO - Comapany
		  	sqlString.append("' ', "); // QABDIVI - Division
			//COA by Lot Number - LOT
			sqlString.append("'LOT', "); // QABTYPE - Certificate of Analysis Type
			sqlString.append("' ', "); // QABORDER - Sales Order Number
			sqlString.append("'" + rs.getString("LMBANO").trim() + "', "); // QABLOT - Lot Number
			sqlString.append("' ', "); //QABCOMMENT - Comment
		  	sqlString.append("' ', "); //QABSIGN - Signature
			sqlString.append("' ', "); //QABCPEC - Chosen Spec
			sqlString.append("'1', "); // QABPONR - Line Number
			sqlString.append("'0' "); // QABPOSX - Line Suffix
			sqlString.append(") ");
		}	
		
		
		
//		 *** ATTRIBUTE SECTION ************************************************************************		
		if (inRequestType.equals("insertCOAttribute"))
		{
			ResultSet rs = (ResultSet) requestValues.elementAt(0);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlibrary + ".QAPCCOAA ");
			sqlString.append("VALUES(");
			sqlString.append(rs.getString("OACONO").trim() + ", "); //QACCONO - Comapany
		  	sqlString.append("'" + rs.getString("OADIVI").trim() + "', "); // QACDIVI - Division
			//COA by Sales Order - Customer Order - CO
			sqlString.append("'CO', "); // QACTYPE - Certificate of Analysis Type
			sqlString.append("'" + rs.getString("OAORNO").trim() + "', "); // QACORDER - Sales Order Number
			sqlString.append("' ', "); // QACLOT - Lot Number
			sqlString.append("'" + rs.getString("OBPONR").trim() + "', "); // QACPONR - Line Number
			sqlString.append("'" + rs.getString("OBPOSX").trim() + "', "); // QACPOSX - Line Suffix
			sqlString.append("'" + rs.getString("AEATID").trim() + "', "); // QACATTR Attribute ID
			if (rs.getString("AAATVC") != null 
				&& (rs.getString("AAATVC").trim().equals("1")
			     || rs.getString("AAATVC").trim().equals("2")))
			   sqlString.append(rs.getString("AEANSQ") + " "); //QACATTRSEQ - Attribute Sequence Number
			else
			   sqlString.append("0 ");
			sqlString.append(") ");
		}		
		
		
		
		if (inRequestType.equals("insertDOAttribute"))
		{
			ResultSet rs = (ResultSet) requestValues.elementAt(0);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlibrary + ".QAPCCOAA ");
			sqlString.append("VALUES(");
			sqlString.append(rs.getString("MRCONO").trim() + ", "); //QACCONO - Comapany
		  	sqlString.append("' ', "); // QACDIVI - Division
			//COA by Distribution Order - DO
			sqlString.append("'DO', "); // QACTYPE - Certificate of Analysis Type
			sqlString.append("'" + rs.getString("MRTRNR").trim() + "', "); // QACORDER - Sales Order Number
			sqlString.append("' ', "); // QACLOT - Lot Number
			sqlString.append("'" + rs.getString("MRPONR").trim() + "', "); // QACPONR - Line Number
			sqlString.append("'" + rs.getString("MRPOSX").trim() + "', "); // QACPOSX - Line Suffix
			sqlString.append("'" + rs.getString("AEATID").trim() + "', "); // QACATTR Attribute ID
		  	sqlString.append(rs.getString("AEANSQ") + " "); //QACATTRSEQ - Attribute Sequence Number
			sqlString.append(") ");
		}				
		
		
		
		if (inRequestType.equals("insertLotAttribute"))
		{
			ResultSet rs = (ResultSet) requestValues.elementAt(0);
			// build the sql statement.
			sqlString.append("INSERT INTO " + ttlibrary + ".QAPCCOAA ");
			sqlString.append("VALUES(");
			sqlString.append(rs.getString("LMCONO").trim() + ", "); //QACCONO - Comapany
		  	sqlString.append("' ', "); // QACDIVI - Division
			//COA by Sales Order - Customer Order - CO
			sqlString.append("'LOT', "); // QACTYPE - Certificate of Analysis Type
			sqlString.append("' ', "); // QACORDER - Sales Order Number
			sqlString.append("'" + rs.getString("LMBANO").trim() + "', "); // QACLOT - Lot Number
			sqlString.append("'1', "); // QACPONR - Line Number
			sqlString.append("'0', "); // QACPOSX - Line Suffix
			sqlString.append("'" + rs.getString("AEATID").trim() + "', "); // QACATTR Attribute ID
			if (rs.getString("AAATVC") != null 
				&& (rs.getString("AAATVC").trim().equals("1")
			     || rs.getString("AAATVC").trim().equals("2")))
		  	   sqlString.append(rs.getString("AEANSQ") + " "); //QACATTRSEQ - Attribute Sequence Number
			else
			   sqlString.append("0 ");
			sqlString.append(") ");
		}		
		
		
		
	//------------------------------------------------------------------------------
	// UPDATE -- Update Methods 
	//  
	// *** HEADER SECTION		
		if (inRequestType.equals("updateCOA"))
		{
			BuildCOA  bcoa = (BuildCOA)    requestValues.elementAt(0);
			// build the sql statement.
			sqlString.append("UPDATE " + ttlibrary + ".QAPACOAH SET ");
			sqlString.append("  QAACOMMENT = '" + bcoa.getBuildCOAComment().trim() + "', ");
			sqlString.append("  QAASIGN = '" + bcoa.getBuildSignature().trim() + "', ");
			sqlString.append("  QAADTFMT = '" + bcoa.getBuildDateFormat().trim() + "', ");
			sqlString.append("  QAASUNIT = '" + bcoa.getBuildShowAmount().trim() + "', ");
			sqlString.append("  QAASAVE = '" + bcoa.getBuildShowAverage().trim() + "', ");
			sqlString.append("  QAASMINMAX = '" + bcoa.getBuildShowMinMax().trim() + "', ");
			sqlString.append("  QAASTARGET = '" + bcoa.getBuildShowTarget().trim() + "', ");
			sqlString.append("  QAASMODEL = '" + bcoa.getBuildShowAttributeModel().trim() + "', ");
			sqlString.append("  QAASSPEC = '" + bcoa.getBuildShowCustomerSpec().trim() + "', ");
			sqlString.append("  QAAEMAIL1 = '" + bcoa.getBuildEmail1().trim() + "', ");
			sqlString.append("  QAAEMAIL2 = '" + bcoa.getBuildEmail2().trim() + "', ");
			sqlString.append("  QAAEMAIL3 = '" + bcoa.getBuildEmail3().trim() + "', ");
			sqlString.append("  QAAEMAIL4 = '" + bcoa.getBuildEmail4().trim() + "', ");
			sqlString.append("  QAAEMAIL5 = '" + bcoa.getBuildEmail5().trim() + "', ");
			sqlString.append("  QAAEMAIL6 = '" + bcoa.getBuildEmail6().trim() + "', ");
			sqlString.append("  QAAEMAIL7 = '" + bcoa.getBuildEmail7().trim() + "', ");
			sqlString.append("  QAAEMAIL8 = '" + bcoa.getBuildEmail8().trim() + "', ");
			sqlString.append("  QAAFAX1 = '" + bcoa.getBuildFax1().trim() + "', ");
			sqlString.append("  QAAFAX2 = '" + bcoa.getBuildFax2().trim() + "', ");
			sqlString.append("  QAAFAX3 = '" + bcoa.getBuildFax3().trim() + "', ");
			sqlString.append("  QAAFAX4 = '" + bcoa.getBuildFax4().trim() + "', ");
			sqlString.append("  QAAATTN1 = '" + bcoa.getBuildAttn1().trim() + "', ");
			sqlString.append("  QAAATTN2 = '" + bcoa.getBuildAttn2().trim() + "', ");
			sqlString.append("  QAAATTN3 = '" + bcoa.getBuildAttn3().trim() + "', ");
			sqlString.append("  QAAATTN4 = '" + bcoa.getBuildAttn4().trim() + "', ");
			try
			{
			   String[] date = SystemDate.getSystemDate();
			   sqlString.append("  QAAUPDATE = " + date[3] + ", "); // Update Date
			   sqlString.append("  QAAUPTIME = " + date[0] + ", "); // Update Time
			}
			catch(Exception e)
			{}
			sqlString.append("  QAAUPUSER = '" + bcoa.getUpdateUser().trim() + "' "); // Update User
				// Where
			sqlString.append("WHERE QAACONO = " + bcoa.getCompany() + " ");
			sqlString.append("  AND QAATYPE = '" + bcoa.getCoaType() + "' ");
			if (!bcoa.getInqLot().trim().equals(""))
				sqlString.append("  AND QAALOT = '" + bcoa.getInqLot().trim() + "' ");
			else
			{	

				String setOrder = "";
				String setDivision = "";
				if (!bcoa.getInqSalesOrder().trim().equals(""))
				{
					setOrder = bcoa.getInqSalesOrder().trim();
					setDivision = bcoa.getDivision(); 
				}
				if (!bcoa.getInqDistributionOrder().trim().equals(""))
				{
					setOrder = bcoa.getInqDistributionOrder().trim();
					setDivision = "";
				}
				sqlString.append("  AND QAAORDER = '" + setOrder + "' ");
				sqlString.append("  AND QAADIVI = '" + setDivision + "' ");
			}
		}	
		if (inRequestType.equals("updateCOALine"))
		{
			UpdCOA  ucoa = (UpdCOA)    requestValues.elementAt(0);
			// build the sql statement.
			sqlString.append("UPDATE " + ttlibrary + ".QAPBCOAL SET ");
			sqlString.append("  QABCOMMENT = '" + ucoa.getLineComment().trim() + "', ");
			sqlString.append("  QABSIGN = '" + ucoa.getLineSignature().trim() + "', ");
			sqlString.append("  QABSPEC = '" + ucoa.getLineSpec().trim() + "' ");
				// Where
			sqlString.append("WHERE QABCONO = " + ucoa.getCompany() + " ");
			sqlString.append("  AND QABTYPE = '" + ucoa.getCoaType() + "' ");
			if (ucoa.getCoaType().equals("LOT"))
			   sqlString.append("  AND QABLOT = '" + ucoa.getInqLot().trim() + "' ");
			else
			{
			   String setOrder = "";
			   String setDivision = "";
			   if (!ucoa.getInqSalesOrder().trim().equals(""))
			   {
				  setOrder = ucoa.getInqSalesOrder().trim();
				  setDivision = ucoa.getDivision().trim();
			   }
			   if (!ucoa.getInqDistributionOrder().trim().equals(""))
			   {
				  setOrder = ucoa.getInqDistributionOrder().trim();
				  setDivision = "";
			   }
			   sqlString.append("  AND QABDIVI = '" + setDivision + "' ");	
			   sqlString.append("  AND QABORDER = '" + setOrder + "' ");
			   sqlString.append("  AND QABPONR = " + ucoa.getLineNumber() + " ");
			}
			sqlString.append("  AND QABPOSX = " + ucoa.getLineSuffix() + " ");
		}
		if (inRequestType.equals("updateCOAAttr"))
		{
			UpdCOA  ucoa = (UpdCOA)    requestValues.elementAt(0);
			// build the sql statement.
			sqlString.append("UPDATE " + ttlibrary + ".QAPCCOAA SET ");
			sqlString.append("  QACATTRSEQ = " + ucoa.getLineAttrSequence().trim() + " ");
				// Where
			sqlString.append("WHERE QACCONO = " + ucoa.getCompany() + " ");
			sqlString.append("  AND QACTYPE = '" + ucoa.getCoaType() + "' ");
			if (ucoa.getCoaType().equals("LOT"))
			   sqlString.append("  AND QACLOT = '" + ucoa.getInqLot().trim() + "' ");
			else
			{
			   
			   String setOrder = "";
			   String setDivision = "";
			   if (!ucoa.getInqSalesOrder().trim().equals(""))
			   {
				  setOrder = ucoa.getInqSalesOrder().trim();
				  setDivision = ucoa.getDivision().trim();
			   }
			   if (!ucoa.getInqDistributionOrder().trim().equals(""))
			   {
				  setOrder = ucoa.getInqDistributionOrder().trim();
				  setDivision = "";
			   }
			   sqlString.append("  AND QACDIVI = '" + setDivision + "' ");
			   sqlString.append("  AND QACORDER = '" + setOrder + "' ");
			   sqlString.append("  AND QACPONR = " + ucoa.getLineNumber() + " ");
			   sqlString.append("  AND QACPOSX = " + ucoa.getLineSuffix() + " ");
			}
			sqlString.append("  AND QACATTR = '" + FindAndReplace.replaceWordWithQuote(ucoa.getAttributeID()) + "' ");
//			sqlString.append("  AND QACATTR = '" + ucoa.getAttributeID() + "' ");
		}
		
		if (inRequestType.equals("updateSent"))
		{
			UpdCOA  ucoa = (UpdCOA)    requestValues.elementAt(0);
			// build the sql statement.
			sqlString.append("UPDATE " + ttlibrary + ".QAPACOAH SET ");
			sqlString.append("  QAALSBY = 'email-fax', ");
			String[] date = SystemDate.getSystemDate();
			sqlString.append("  QAALSDATE = " + date[3] + ", "); // Last Sent Date
		}			
	} catch (Exception e) {
			throwError.append(" Error building sql statement");
			throwError.append(" for request type " + inRequestType + ". ");
	}
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceCOA.");
			throwError.append("buildSqlStatement(String, Vector)");
			throw new Exception(throwError.toString());
		}

		return sqlString.toString();
	}

	/**
	 * Add Default Records into the COA Files
	 *
	 *    This Method will update COAHEAD
	 *    With Initialize records with Defaulted Information 
	 * 
	 * @param Vector inValues, ResultSet rs, Connection conn
	 * @return Void
	 * @throws Exception
	 */
	private static void addInitialCOA(Vector inValues,
									  ResultSet rs, 
									  Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		PreparedStatement addIt = null;
		String sqlStatement = "";
		if (conn != null)
      {	
		  	try { // GET SQL STATEMENT
		  		InqCOA inqCOA = (InqCOA) inValues.elementAt(0);
					// Pass along the incoming view bean information. 
				 Vector parameters = new Vector();
				 parameters.addElement(rs);
				 parameters.addElement(inqCOA);
				 String requestType = "";
				 if (!inqCOA.getInqLot().trim().equals(""))
				 	requestType = "insertLotHead";
				 if (!inqCOA.getInqSalesOrder().trim().equals(""))
				 	requestType = "insertCOHead";
				 if (!inqCOA.getInqDistributionOrder().trim().equals(""))
				 	requestType = "insertDOHead";
				 sqlStatement = buildSqlStatement(inqCOA.getEnvironment(), requestType, parameters);
			} catch(Exception e) {
				throwError.append(" error occured retrieving the SQL statement: " + e);
			}
			if (throwError.toString().equals(""))
			{ // Run the Update
				try {
					addIt = conn.prepareStatement(sqlStatement);
					addIt.executeUpdate();
				} catch(Exception e)
				{
					throwError.append(" error occured executing an ");
					throwError.append("add sql statement. " + e);
				} finally {
					try {
						addIt.close();
					} catch (Exception el) {
						el.printStackTrace();
					}
			   }
			// return error data	
			   if (!throwError.toString().equals("")) {
				   throwError.append("Error at com.treetop.services.");
				   throwError.append("ServiceCOA.");
				   throwError.append("addInitialCOA(InqCOA, rs, conn)");
				   throw new Exception(throwError.toString());
			   }		  	
		  	} // end of If there is no error		  	
      } // end of If there is a connection
		return;
	}

	/**
	 * Load class fields from result set.
	 *    for the COA Business Object.
	 */
	
	private static COADetailAttributes loadFieldsCOA(String requestType,
										   			 ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		COADetailAttributes returnCOA = new COADetailAttributes();
		try{ // COAHEAD
		  returnCOA.setCoaType(rs.getString("QAATYPE").trim());
		  returnCOA.setOrderNumber(rs.getString("QAAORDER").trim());
		  returnCOA.setLotNumber(rs.getString("QAALOT").trim());
		  returnCOA.setComment(rs.getString("QAACOMMENT").trim());

		  returnCOA.setEmail1(rs.getString("QAAEMAIL1").trim());
		  returnCOA.setEmail2(rs.getString("QAAEMAIL2").trim());
		  returnCOA.setEmail3(rs.getString("QAAEMAIL3").trim());
		  returnCOA.setEmail4(rs.getString("QAAEMAIL4").trim());
		  returnCOA.setEmail5(rs.getString("QAAEMAIL5").trim());
		  returnCOA.setEmail6(rs.getString("QAAEMAIL6").trim());
		  returnCOA.setEmail7(rs.getString("QAAEMAIL7").trim());
		  returnCOA.setEmail8(rs.getString("QAAEMAIL8").trim());
		  
		  returnCOA.setAttention1(rs.getString("QAAATTN1").trim());
		  returnCOA.setAttention2(rs.getString("QAAATTN2").trim());
		  returnCOA.setAttention3(rs.getString("QAAATTN3").trim());
		  returnCOA.setAttention4(rs.getString("QAAATTN4").trim());
		  
		  returnCOA.setFax1(rs.getString("QAAFAX1").trim());
		  returnCOA.setFax2(rs.getString("QAAFAX2").trim());
		  returnCOA.setFax3(rs.getString("QAAFAX3").trim());
		  returnCOA.setFax4(rs.getString("QAAFAX4").trim());
		  
		  returnCOA.setSignatureQA(rs.getString("QAASIGN").trim());
		  returnCOA.setDateFormat(rs.getString("QAADTFMT").trim());
		  returnCOA.setShowAverage(rs.getString("QAASAVE").trim());
		  returnCOA.setShowUnits(rs.getString("QAASUNIT").trim());
		  returnCOA.setShowMinMax(rs.getString("QAASMINMAX").trim());
		  returnCOA.setShowTarget(rs.getString("QAASTARGET").trim());
		  returnCOA.setShowAttributeModel(rs.getString("QAASMODEL").trim());
		  returnCOA.setShowSpec(rs.getString("QAASSPEC").trim());
		  
		} catch(Exception e)
		{
			throwError.append(" Problem loading the COA class ");
			throwError.append("from the result set. " + e) ;
		}
		// From the Line File
	try{ // COALINE
		  returnCOA.setItemComment(rs.getString("QABCOMMENT").trim());
		  returnCOA.setItemSignature(rs.getString("QABSIGN").trim());
		  returnCOA.setItemSpec(rs.getString("QABSPEC").trim());
		  returnCOA.setLineNumber(rs.getString("QABPONR"));
		  returnCOA.setLineSuffix(rs.getString("QABPOSX"));
		} catch(Exception e)
		{
			throwError.append(" Problem loading the COA class ");
			throwError.append("from the result set. " + e) ;
		}		
		// From the Line File
	try{ // COAATTR
		//System.out.println(rs.getString("QACATTRSEQ").trim() + " ::: " + rs.getString("QACATTR").trim());
		  returnCOA.setAttribute(rs.getString("QACATTR").trim());
		  returnCOA.setSequence(rs.getString("QACATTRSEQ").trim());
		} catch(Exception e)
		{
			throwError.append(" Problem loading the COA class ");
			throwError.append("from the result set. " + e) ;
		}				
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceCOA.");
			throwError.append("loadFields(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnCOA;
	}

	/**
	 * Update COA files from app view bean. 
	 * @param  String RequestType --
	 * 			updateCOA - for Main Header Information
	 * 			updateCOAByLineItem - for Each Line Item of the COA, you can Sequence the information 
	 *    Vector BuildCOA viewbean (for updateCOA)
	 * 			 UpdCOA   viewBean (for updateCOASequence)
	 * @return void.
	 */
	public static void updateCOA(String requestType, 
								 Vector inClass)	
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		try
		{
			conn = ConnectionStack.getConnection();
			if (requestType.equals("updateCOA"))
			{
				updateCOAHeader((BuildCOA) inClass.elementAt(0), conn);
			}
			if (requestType.equals("updateCOALine"))
			{
				updateCOALine((UpdCOA) inClass.elementAt(0), conn);
			}
			if (requestType.equals("updateCOAAttr"))
			{
				updateCOAAttr((UpdCOA) inClass.elementAt(0), conn);
			}
		}
		catch(Exception e)
		{
			throwError.append(e.toString());
		}
		finally {
			if (conn != null)
			{
				try {
					ConnectionStack.returnConnection(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		
		// return data.	
		if (!throwError.toString().equals("")) {
			throw new Exception(throwError.toString());
		}
	}
	/**
	 * Update Main COA file from app view bean. 
	 * @param  BuildCOA viewbean.
	 * @return void.
	 */
	private static void updateCOAHeader(BuildCOA updVb, Connection conn)	
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String sqlUpdate = "";
		PreparedStatement preStmt = null;
		Vector parmClass = new Vector();
		String requestType = "";
	try
	{
//	  Get Connection to attach to files
		try {
//  Build Update SQL	
			parmClass.addElement(updVb);
			// get the sql statements.
			requestType = "updateCOA";
			sqlUpdate = buildSqlStatement(updVb.getEnvironment(), requestType, parmClass);
		}
		catch(Exception e)
		{
			throwError.append(" Problem occurred building the SQL Statement to update the ");
			throwError.append("COA Header Information.  " + e);	
		}
		if (throwError.toString().equals(""))
		{	
		   try {
		 		preStmt = conn.prepareStatement(sqlUpdate);
			    preStmt.executeUpdate();
			}
			catch(Exception e)
			{
				throwError.append(" Problem occurred while preparing or running the ");
				throwError.append("Update of the COA Header Information.  " + e);	
			}		
		}
	} catch(Exception e)
	{
		throwError.append("Error occuring build or updating the Header Information ");
		throwError.append("on the COA. " + e);
	// return connection.
	} finally {
		if (preStmt != null)
		{
			try {
				preStmt.close();
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}
	// return data.	
	if (!throwError.toString().equals("")) {
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServiceCOA.");
		throwError.append("updateCOAHeader(BuildCOA)");
		throw new Exception(throwError.toString());
	}
	}

	/**
	 * Add Default Records into the COA Files
	 *
	 *    This Method will update COALINE
	 *    With Initialize records with Defaulted Information 
	 * 
	 * @param Vector inValues, ResultSet rs, Connection conn
	 * @return Void
	 * @throws Exception
	 */
	private static void addInitialCOALine(Vector inValues,
									      ResultSet rs, 
									      Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		PreparedStatement addIt = null;
		String sqlStatement = "";
		if (conn != null)
	  {	
		  	try { // GET SQL STATEMENT
		  		InqCOA inqCOA = (InqCOA) inValues.elementAt(0);
					// Pass along the incoming view bean information. 
				 Vector parameters = new Vector();
				 parameters.addElement(rs);
				 parameters.addElement(inqCOA);
				 String requestType = "";
				 if (!inqCOA.getInqLot().trim().equals(""))
				 	requestType = "insertLotLine";
				 if (!inqCOA.getInqSalesOrder().trim().equals(""))
				 	requestType = "insertCOLine";
				 if (!inqCOA.getInqDistributionOrder().trim().equals(""))
				 	requestType = "insertDOLine";
				 sqlStatement = buildSqlStatement(inqCOA.getEnvironment(), requestType, parameters);
			} catch(Exception e) {
				throwError.append(" error occured retrieving the SQL statement: " + e);
			}
			
			if (throwError.toString().equals(""))
			{ // Run the Update
				try {
					addIt = conn.prepareStatement(sqlStatement);
					addIt.executeUpdate();
				} catch(Exception e)
				{
					throwError.append(" error occured executing an ");
					throwError.append("add sql statement. " + e);
				} finally {
					try {
						addIt.close();
					} catch (Exception el) {
						el.printStackTrace();
					}
			   }
			// return error data	
			   if (!throwError.toString().equals("")) {
				   throwError.append("Error at com.treetop.services.");
				   throwError.append("ServiceCOA.");
				   throwError.append("addInitialCOALine(InqCOA, rs, conn)");
				   throw new Exception(throwError.toString());
			   }		  	
		  	} // end of If there is no error		  	
	  } // end of If there is a connection
		return;
	}

	/**
	 * Add Default Records into the COA Files
	 *
	 *    This Method will update COAHEAD
	 *    With Initialize records with Defaulted Information 
	 * 
	 * @param Vector inValues, ResultSet rs, Connection conn
	 * @return Void
	 * @throws Exception
	 */
	private static void addInitialCOALineAttribute(Vector inValues,
									               ResultSet rs, 
									               Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		PreparedStatement addIt = null;
		String sqlStatement = "";
		if (conn != null)
	  {	
		  	try { // GET SQL STATEMENT
		  		InqCOA inqCOA = (InqCOA) inValues.elementAt(0);
					// Pass along the incoming view bean information. 
				 Vector parameters = new Vector();
				 parameters.addElement(rs);
				 parameters.addElement(inqCOA);
				 String requestType = "";
				 if (!inqCOA.getInqLot().trim().equals(""))
				 	requestType = "insertLotAttribute";
				 if (!inqCOA.getInqSalesOrder().trim().equals(""))
				 	requestType = "insertCOAttribute";
				 if (!inqCOA.getInqDistributionOrder().trim().equals(""))
				 	requestType = "insertDOAttribute";
				 sqlStatement = buildSqlStatement(inqCOA.getEnvironment(), requestType, parameters);
			} catch(Exception e) {
				throwError.append(" error occured retrieving the SQL statement: " + e);
			}
			
			if (throwError.toString().equals(""))
			{ // Run the Update
				try {
					addIt = conn.prepareStatement(sqlStatement);
					addIt.executeUpdate();
				} catch(Exception e)
				{
					throwError.append(" error occured executing an ");
					throwError.append("add sql statement. " + e);
				} finally {
					try {
						addIt.close();
					} catch (Exception el) {
						el.printStackTrace();
					}
			   }
			// return error data	
			   if (!throwError.toString().equals("")) {
				   throwError.append("Error at com.treetop.services.");
				   throwError.append("ServiceCOA.");
				   throwError.append("addInitialCOALineAttribute(InqCOA, rs, conn)");
				   throw new Exception(throwError.toString());
			   }		  	
		  	} // end of If there is no error		  	
	  } // end of If there is a connection
		return;
	}

	/**
		 * Update Line Item COA file from app view bean. 
		 * @param  BuildCOA viewbean.
		 * @return void.
		 */
		private static void updateCOALine(UpdCOA updVb, Connection conn)	
			throws Exception 
		{
			StringBuffer throwError = new StringBuffer();
			String sqlUpdate = "";
			PreparedStatement preStmt = null;
			Vector parmClass = new Vector();
			String requestType = "";
		try
		{
			try {
	//  Build Update SQL	
				parmClass.addElement(updVb);
				// get the sql statements.
				requestType = "updateCOALine";
				sqlUpdate = buildSqlStatement(updVb.getEnvironment(), requestType, parmClass);
			}
			catch(Exception e)
			{
				throwError.append(" Problem occurred building the SQL Statement to update the ");
				throwError.append("COA Line Information.  " + e);	
			}
			if (throwError.toString().equals(""))
			{	
			   try {
			 		preStmt = conn.prepareStatement(sqlUpdate);
				    preStmt.executeUpdate();
				}
				catch(Exception e)
				{
					throwError.append(" Problem occurred while preparing or running the ");
					throwError.append("Update of the COA Line Information.  " + e);	
				}		
			}
		} catch(Exception e)
		{
			throwError.append("Error occuring build or updating the Line Information ");
			throwError.append("on the COA. " + e);
		// return connection.
		} finally {
			if (preStmt != null)
			{
				try {
					preStmt.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceCOA.");
			throwError.append("updateCOALine(BuildCOA)");
			throw new Exception(throwError.toString());
		}
		}

	/**
		 * Update Sequence of the Attribute COA file from app view bean. 
		 * @param  BuildCOA viewbean.
		 * @return void.
		 */
		private static void updateCOAAttr(UpdCOA updVb, Connection conn)	
			throws Exception 
		{
			StringBuffer throwError = new StringBuffer();
			String sqlUpdate = "";
			PreparedStatement preStmt = null;
			Vector parmClass = new Vector();
			String requestType = "";
		try
		{
	//	  Get Connection to attach to files
			try {
	//  Build Update SQL	
				parmClass.addElement(updVb);
				// get the sql statements.
				requestType = "updateCOAAttr";
				sqlUpdate = buildSqlStatement(updVb.getEnvironment(), requestType, parmClass);
			}
			catch(Exception e)
			{
				throwError.append(" Problem occurred building the SQL Statement to update the ");
				throwError.append("COA Attribute Sequence Information.  " + e);	
			}
			if (throwError.toString().equals(""))
			{	
			   try {
			 		preStmt = conn.prepareStatement(sqlUpdate);
				    preStmt.executeUpdate();
				}
				catch(Exception e)
				{
					throwError.append(" Problem occurred while preparing or running the ");
					throwError.append("Update of the COA Attribute Sequence Information.  " + e);	
				}		
			}
		} catch(Exception e)
		{
			throwError.append("Error occuring build or updating the Attribute Sequence Information ");
			throwError.append("on the COA. " + e);
		// return connection.
		} finally {
			if (preStmt != null)
			{
				try {
					preStmt.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceCOA.");
			throwError.append("updateCOAAttr(UpdCOA)");
			throw new Exception(throwError.toString());
		}
		}

	/**
		 * Use to Validate a Send in Sales Order, 
		 *      
		 * Return Return Message if Sales Order is not found
		 */
		
		public static String verifyCOA(String environment, 
									   String salesOrder,
									   String requestType)
			throws Exception
		{
			StringBuffer throwError = new StringBuffer();
			StringBuffer rtnProblem = new StringBuffer();
			Connection conn = null; // New Lawson Box - Lawson Database
			PreparedStatement findIt = null;
			ResultSet rs = null;
			try { 
				String sqlString = "";
				
				// verify base class initialization.
				ServiceSalesOrder a = new ServiceSalesOrder();
							
				if (environment == null || environment.trim().equals(""))
					environment = "PRD";
				// verify incoming Sales Order.
				if (salesOrder == null || salesOrder.trim().equals(""))
					rtnProblem.append(" Sales Order must not be null or empty.");
				
				// get Sales Order Info.
				if (throwError.toString().equals(""))
				{
					try {
						Vector parmClass = new Vector();
						parmClass.addElement(salesOrder);
						sqlString = buildSqlStatement(environment, "testByCO", parmClass);
					} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnProblem.append(" Problem when building Test for Sales Order: ");
					rtnProblem.append(salesOrder + " PrintScreen this message and send to Information Services. ");
					}
				}
				
				// get a connection. execute sql, build return object.
				if (rtnProblem.toString().equals("")) {
					try {
						
						conn = ConnectionStack.getConnection();
						
						findIt = conn.prepareStatement(sqlString);
						rs = findIt.executeQuery();
						
						if (rs.next())
						{
	//						 it exists and all is good.
						} else {
							rtnProblem.append(" COA for Customer Order '" + salesOrder + "' ");
							rtnProblem.append("does not exist. ");
						}
						
					} catch (Exception e) {
						throwError.append(" error occured executing a sql statement. " + e);
						rtnProblem.append(" Problem when finding Sales Order: ");
						rtnProblem.append(salesOrder + " PrintScreen this message and send to Information Services. ");
					}
				}
			} catch(Exception e)
			{
				throwError.append(" Problem loading the Sales Order class ");
				throwError.append("from the result set. " + e) ;
			// return connection.
			} finally {
				if (conn != null) {
					try {
						if (conn != null)
							ConnectionStack.returnConnection(conn);
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
				throwError.append("ServiceSalesOrder.");
				throwError.append("verifySalesOrder(String: SalesOrder)");
				throw new Exception(throwError.toString());
			}
			return rtnProblem.toString();
		}

	/**
			 * Return a BeanCOA business object.
			 *  use the incoming InqCOA class for selection
			 * 
			 *  This Method will make the decisions as to 
			 *  what is loaded into the Bean.
			 * 	Use for Distribution Order Based COA's
			 * 
			 * @param InqCOA inqCOA
			 * @param Connection conn
			 * @return BeanCOA business object.
			 * @throws Exception
			 */
			private static BeanCOA findCOAByDistributionOrder(InqCOA inqCOA, 
															  Connection conn)
				throws Exception
			{
				StringBuffer throwError = new StringBuffer();
				Statement findIt1 = null;
				Statement findIt2 = null;
				Statement testIt  = null;
				ResultSet rs1     = null;
				ResultSet rs2     = null;
				ResultSet testRS  = null;
				String returnNotFound = "";
				BeanCOA bean = new BeanCOA();
				Vector sendValues = new Vector();
				
				try {
					sendValues.add(inqCOA);
					
					if (!inqCOA.getRequestType().equals("preview"))
					{	
						//------------------------------	
						// TEST if VALID DISTRIBUTION ORDER -- Must have record in the MGHEAD file	
						try {
							findIt1 = conn.createStatement();
							rs1 = findIt1.executeQuery(buildSqlStatement(inqCOA.getEnvironment(), "testByDO", sendValues));
						} 
						catch(Exception e) {
							throwError.append(" error occured executing the Test Valid sql statement. " + e);
						}
						
						try {
						String buildCOAHeader = "";
						String saveLine = "";
							
							while (rs1.next()) 
							{
								// Test to see if a COA should be Built / Started
								if (rs1.getString("QAATYPE") == null &&
										buildCOAHeader.trim().equals(""))
								{
									// Build a beginning set of Records for the COA
									// Update the COA
									addInitialCOA(sendValues, rs1, conn);
									buildCOAHeader = "Y";
								}
								if (rs1.getString("QABTYPE") == null &&
									!(rs1.getString("MRPONR") + rs1.getString("MRPOSX") + "").equals(saveLine.trim()))
								{
									addInitialCOALine(sendValues, rs1, conn);
									saveLine = rs1.getString("MRPONR") + rs1.getString("MRPOSX") + "";
								}
								
								if (rs1.getString("QACTYPE") == null)
								{
									addInitialCOALineAttribute(sendValues, rs1, conn);
								}
							}
						
							rs1.close();
							findIt1.close();
						}
						catch(Exception e) {
							throwError.append(" error occured reading of result set. " + e);
						} 
					}
				}
				catch(Exception e) {
					throwError.append(" error occured dealing with a result set. " + e);
				}
				
				//------------------------------	
				// Get actual Information / COA-Distribution Order
				try
				{
					if (returnNotFound.equals(""))
					{
						String requestType = "coaByDO";
						try {
							if (inqCOA.getRequestType().equals("goToUpdate"))
								requestType = "coaByDOSeq";
							findIt2 = conn.createStatement();
							rs2 = findIt2.executeQuery(buildSqlStatement(inqCOA.getEnvironment(), requestType, sendValues));
						}
						catch(Exception e)
						{
							throwError.append(" error occured executing a sql statement. " + e);
						}
						
						// Read the Result Set, build Data
						Vector listCOA = new Vector();
						Vector listDO = new Vector();
						Vector listValue = new Vector();
						
						try
						{
							String saveLine = "";
							String saveAttributeSeq = "";
							String saveTransaction = "";
							String saveLot = "";
							String saveLotLine = "";
							BigDecimal qty = new BigDecimal("0");
							while (rs2.next())
							{
							    if (rs2.getString("LMBANO") != null && 
							    	(saveLot.equals("") || !saveLot.equals(rs2.getString("LMBANO").trim()) ||
							    	(saveLotLine.equals("") || !saveLotLine.equals(rs2.getString("MRPONR").trim()))	)	)
							    {	
							       qty = new BigDecimal("0");
							 	   // Run SQL statement to see if this Lot Number should be included
								   try {
								   	   Vector testValues = new Vector();
								   	   testValues.addElement(rs2.getString("MRTRNR").trim());
								   	   testValues.addElement(rs2.getString("MRPONR").trim());
								   	   testValues.addElement(rs2.getString("MRITNO").trim());
								   	   testValues.addElement(rs2.getString("LMBANO"));
								   	   testValues.addElement(rs2.getString("QAATYPE"));
								   	   testValues.addElement(rs2.getString("MRCONO"));
								   	   testValues.addElement(rs2.getString("MTTTYP"));
								   	   
									 testIt = conn.createStatement(); 
									 testRS = testIt.executeQuery(buildSqlStatement(inqCOA.getEnvironment(), "testQTY", testValues));
									 // Add in code to Run through a result set and ADD up the Quantity Fields.
									 while (testRS.next())
									 {
	//									 System.out.println(testRS.getString("MTTRQT"));
										qty = qty.add(testRS.getBigDecimal("MTTRQT")); 
									 }
									 testIt.close();
									 testRS.close();
									 saveLot = rs2.getString("LMBANO").trim();
									 saveLotLine = rs2.getString("MRPONR").trim();
	//								 if (saveLot.equals("81500411014"))
	//								 	System.out.println("HEREIS ONE");
								   }
								   catch(Exception e)
								   {
									  throwError.append(" error occured executing a sql statement. " + e);
								   }					
							    }
							  if (qty.compareTo(new BigDecimal("0")) != 0)
							  {	
								if (rs2.getString("MRPONR") != null && 
									!(rs2.getString("MRPONR") + rs2.getString("MRPOSX")).equals(saveLine))
									saveAttributeSeq = "";
								// Load Fields from Result Set
								//  List of Attribute Values by Lot
							//	if (rs.getString("AGBANO") != null &&
							//		!(rs.getString("AGBANO").trim() + rs.getString("AAATID").trim()).equals(saveTransaction.trim()))
							//	{	
							//		AttributeValue addOne = ServiceAttribute.loadFieldsAttributeValue("loadAttributeValueLine", rs);
							//		listValue.addElement(addOne);
							//		saveTransaction = rs.getString("AGBANO").trim() + rs.getString("AAATID").trim();
							//	}				
							
								if (requestType.equals("coaByDOSeq"))
								{	
								   if (rs2.getString("AAATID") != null &&
									   !rs2.getString("AAATID").trim().equals(saveAttributeSeq.trim()))
								   {	
									   COADetailAttributes addOne = loadFieldsCOA("", rs2);
									   if (rs2.getString("AAATID") != null)
									      addOne.setAttributeClass(ServiceAttribute.loadFieldsAttribute("loadAttributeLine", rs2));
									   listCOA.addElement(addOne);
									   saveAttributeSeq = rs2.getString("AAATID").trim();
									   AttributeValue addOne1 = ServiceAttribute.loadFieldsAttributeValue("loadAttributeValueLine", rs2);
									   Lot lot1 = addOne1.getLotObject();
									   lot1.setQuantity(qty.toString());
									   addOne1.setLotObject(lot1);
									   listValue.addElement(addOne1);
									   //saveTransaction = rs.getString("AGBANO").trim() + rs.getString("AAATID").trim();
								   }
								}
								
								if (requestType.equals("coaByDO"))
								{	
								   if (rs2.getString("QACATTRSEQ") != null && rs2.getString("QACATTRSEQ") != null &&
									   !(rs2.getString("QACATTR").trim() + rs2.getString("QACATTRSEQ").trim()).equals(saveAttributeSeq.trim()))
								   {	
									   COADetailAttributes addOne = loadFieldsCOA("", rs2);
									   if (rs2.getString("AAATID") != null)
									      addOne.setAttributeClass(ServiceAttribute.loadFieldsAttribute("loadAttributeLine", rs2));
									   listCOA.addElement(addOne);
									   saveAttributeSeq = rs2.getString("QACATTR").trim() + rs2.getString("QACATTRSEQ").trim();
									   AttributeValue addOne1 = ServiceAttribute.loadFieldsAttributeValue("loadAttributeValueLine", rs2);
									   Lot lot1 = addOne1.getLotObject();
									   lot1.setQuantity(qty.toString());
									   addOne1.setLotObject(lot1);
									   listValue.addElement(addOne1);
									   //saveTransaction = rs.getString("AGBANO").trim() + rs.getString("AAATID").trim();
								   }
								}
								
								if (rs2.getString("MRPONR") != null &&  
								  !(rs2.getString("MRPONR") + rs2.getString("MRPOSX")).equals(saveLine))
								{	
								   DistributionOrderDetail addDO = ServiceDistributionOrder.loadFieldsLineItems("loadFromCOA", rs2); 
								   //System.out.println("addSO");
								   listDO.addElement(addDO);
								   saveLine = rs2.getString("MRPONR") + rs2.getString("MRPOSX");
								}
							  }
							}
						}
						catch(Exception e)
						{
							throwError.append(" error occured reading of result set. " + e);
						} 
						bean.setListCOADetailAttributes(listCOA);
						bean.setListAttrValues(listValue);
						bean.setListDOLineItems(listDO);
						try	{
							findIt2.close();
							rs2.close();
						}catch (Exception e) {
						}
					} // END of the if Not Found
				} catch (Exception e) {
					throwError.append(" error occured processing information. " + e);
				} 
				finally {
					if (rs1 != null) {
						try {
							rs1.close();
							findIt1.close();
						} catch (Exception el) {
							el.printStackTrace();
						}
					}
					if (rs2 != null) {
						try {
							rs2.close();
							findIt2.close();
						} catch (Exception el) {
							el.printStackTrace();
						}
					}
					if (testRS != null) {
						try {
							testRS.close();
							testIt.close();
						} catch (Exception el) {
							el.printStackTrace();
						}
					}
				}
				// return error data.
				if (!throwError.toString().equals("")) {
					throwError.append("Error at com.treetop.services.");
					throwError.append("ServiceCOA.");
					throwError.append("findCOAByDistributionOrder(InqCOA). ");
					throw new Exception(throwError.toString());
				}
				return bean;
			}

	/**
	 * Return a BeanCOA business object.
	 *  use the incoming InqCOA class for selection
	 * 
	 *  This Method will make the decisions as to 
	 *  what is loaded into the Bean.
	 * 	Use for Lot Based COA's
	 * 
	 * @param InqCOA inqCOA
	 * @param Connection conn
	 * @return BeanCOA business object.
	 * @throws Exception
	 */
	private static BeanCOA findCOAByLot(InqCOA inqCOA, Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Statement findIt = null;
		ResultSet rs = null;
		String returnNotFound = "";
		BeanCOA bean = new BeanCOA();
		Vector sendValues = new Vector();
		
		try {
			sendValues.add(inqCOA);
			
			if (!inqCOA.getRequestType().equals("preview"))
			{	
				//------------------------------	
				// TEST if VALID LOT -- Must have record in the MGHEAD file	
				try {
					findIt = conn.createStatement();
					rs = findIt.executeQuery(buildSqlStatement(inqCOA.getEnvironment(), "testByLot", sendValues));
				} 
				catch(Exception e) {
					throwError.append(" error occured executing the Test Valid sql statement. " + e);
				}
				
				try {
				String buildCOAHeader = "";
				String buildCOADetail = "";
				String saveLine = "";
					
					while (rs.next()) 
					{
						// Test to see if a COA should be Built / Started
						if (rs.getString("QAATYPE") == null &&
								buildCOAHeader.trim().equals(""))
						{
							// Build a beginning set of Records for the COA
							// Update the COA
							addInitialCOA(sendValues, rs, conn);
							buildCOAHeader = "Y";
						}
						
						if (rs.getString("QABTYPE") == null && 
							buildCOADetail.trim().equals(""))
						{
							addInitialCOALine(sendValues, rs, conn);
							buildCOADetail = "Y";
						}

						
						if (rs.getString("QACTYPE") == null)
						{
							addInitialCOALineAttribute(sendValues, rs, conn);
						}
					}
				
					rs.close();
					findIt.close();
				}
				catch(Exception e) {
					throwError.append(" error occured reading of result set. " + e);
				} 
			}
		}
		catch(Exception e) {
			throwError.append(" error occured dealing with a result set. " + e);
		}
		
		//------------------------------	
		// Get actual Information / COA-Lot Number
		rs = null;
		findIt = null;
		
		try
		{
			if (returnNotFound.equals(""))
			{
				String requestType = "coaByLot";
				try {
					if (inqCOA.getRequestType().equals("goToUpdate"))
						requestType = "coaByLotSeq";
					findIt = conn.createStatement();
					rs = findIt.executeQuery(buildSqlStatement(inqCOA.getEnvironment(), requestType, sendValues));
				}
				catch(Exception e)
				{
					throwError.append(" error occured executing a sql statement. " + e);
				}
				
				// Read the Result Set, build Data
				Vector listCOA = new Vector();
				Vector listDO = new Vector();
				Vector listValue = new Vector();
				
				try
				{
					String saveLine = "";
					String saveAttributeSeq = "";
					String saveTransaction = "";
					String saveLot = "";
					String saveLotLine = "";
					BigDecimal qty = new BigDecimal("0");
					
					while (rs.next())
					{
					    if (rs.getString("LMBANO") != null && 
					    	(saveLot.equals("") || 
					    	(saveLotLine.equals("")) ))
					    {
					
					    	if (requestType.equals("coaByLotSeq"))
					    	{	
					    		if (rs.getString("AAATID") != null &&
					    			!rs.getString("AAATID").trim().equals(saveAttributeSeq.trim()))
					    		{	
					    			COADetailAttributes addOne = loadFieldsCOA("", rs);
					    			
					    			if (rs.getString("AAATID") != null)
					    			{
					    				addOne.setAttributeClass(ServiceAttribute.loadFieldsAttribute("loadAttributeLine", rs));
					    			}
					    			
					    			listCOA.addElement(addOne);
					    			saveAttributeSeq = rs.getString("AAATID").trim();
					    			AttributeValue addOne1 = ServiceAttribute.loadFieldsAttributeValue("loadAttributeValueLine", rs);
					    			if (addOne1.getAttribute() != null 
						    			&& !addOne1.getAttribute().trim().equals("")
						    			&& !addOne1.getValue().trim().equals("")
							    		&& addOne1.getFieldType().trim().equals("alpha"))
					    			{
					    				try
					    				{
					    					Attribute attr = ServiceAttribute.findAttributeValueDescription(inqCOA.getEnvironment(), addOne1.getValue());
					    					if (attr.getAttributeDescription() != null && !attr.getAttributeDescription().trim().equals(""))
					    					{
								    			addOne1.setAttributeUOMDescription(attr.getAttributeDescription());
					    					}
					    				}
					    				catch(Exception e)
					    				{
					    					System.out.println("Problem with finding the Attribute");
					    				}
					    			}
					    			if (addOne1.getAttribute() != null 
						    			&& !addOne1.getAttribute().trim().equals("") 
						    			&& addOne1.getFieldType().trim().equals("text")
						    			&& rs.getString("AGTXID") != null
						    			&& !rs.getString("AGTXID").trim().equals("0"))
						    			{
						    				try
						    				{
						    						addOne1.setValue(ServiceAttribute.findAttributeTextValue(inqCOA.getEnvironment(), rs.getString("AGTXID")));
						    				}
						    				catch(Exception e)
						    				{
						    					System.out.println("Problem with finding the Attribute Text Information");
						    				}
						    			}
					    			Lot lot1 = addOne1.getLotObject();
					    			lot1.setQuantity(qty.toString());
					    			addOne1.setLotObject(lot1);
					    			listValue.addElement(addOne1);
					    			//saveTransaction = rs.getString("AGBANO").trim() + rs.getString("AAATID").trim();
					    		}
					    	}
						
					    	if (requestType.equals("coaByLot"))
					    	{	
					    		if (rs.getString("QACATTRSEQ") != null && 
					    			!(rs.getString("QACATTR").trim() + rs.getString("QACATTRSEQ").trim()).equals(saveAttributeSeq.trim()))
					    		{	
					    			COADetailAttributes addOne = loadFieldsCOA("", rs);
					    			if (rs.getString("AAATID") != null)
					    				addOne.setAttributeClass(ServiceAttribute.loadFieldsAttribute("loadAttributeLine", rs));
					    			//if (rs.getString("AAATID").trim().equals("HOLD COMMENTS"))
				    				//	System.out.println("STOP at HOLD COMMENTS -- FindCOAByLot");
					    			listCOA.addElement(addOne);
					    			saveAttributeSeq = rs.getString("QACATTR").trim() + rs.getString("QACATTRSEQ").trim();
					    			AttributeValue addOne1 = ServiceAttribute.loadFieldsAttributeValue("loadAttributeValueLine", rs);
					    			Lot lot1 = addOne1.getLotObject();
					    			if (addOne1.getAttribute() != null 
					    				&& !addOne1.getAttribute().trim().equals("")
					    				&& !addOne1.getValue().trim().equals("")
						    			&& addOne1.getFieldType().trim().equals("alpha"))
						    		{
						    			try
						    			{
						    				Attribute attr =  ServiceAttribute.findAttributeValueDescription(inqCOA.getEnvironment(), addOne1.getValue());
						    				if (attr.getAttributeDescription() != null && !attr.getAttributeDescription().trim().equals(""))
						    				{
						    					addOne1.setAttributeUOMDescription(attr.getAttributeDescription());
						    				}
						    			}
						    			catch(Exception e)
						    			{
						    				System.out.println("Problem with finding the Attribute");
						    			}
						    		}
					    			if (addOne1.getAttribute() != null 
							    		&& !addOne1.getAttribute().trim().equals("") 
							    		&& addOne1.getFieldType().trim().equals("text")
							    		&& rs.getString("AGTXID") != null
							    		&& !rs.getString("AGTXID").trim().equals("0"))
							    	{
							    		try
							    		{
							    			addOne1.setValue(ServiceAttribute.findAttributeTextValue(inqCOA.getEnvironment(), rs.getString("AGTXID")));
							    		}
							    		catch(Exception e)
							    		{
							    			System.out.println("Problem with finding the Attribute Text Information");
							    		}
							    	}
							    	lot1.setQuantity(qty.toString());
					    			addOne1.setLotObject(lot1);
					    			listValue.addElement(addOne1);
					    			//saveTransaction = rs.getString("AGBANO").trim() + rs.getString("AAATID").trim();
					    		}
					    	}
					    }
					}
				}
				catch(Exception e)
				{
					throwError.append(" error occured reading of result set. " + e);
				} 
				bean.setListCOADetailAttributes(listCOA);
				bean.setListAttrValues(listValue);
				bean.setListDOLineItems(listDO);
			} // END of the if Not Found
		} catch (Exception e) {
			throwError.append(" error occured processing information. " + e);
		} 
		finally {
			if (rs != null) {
				try {
					rs.close();
					findIt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// return error data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceCOA.");
			throwError.append("findCOAByLot(InqCOA). ");
			throw new Exception(throwError.toString());
		}
		return bean;
	}
}

