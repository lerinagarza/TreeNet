/*
 * Created on February 9, 2008
 * 
 */
package com.treetop.services;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;

import javax.sql.*;
import com.ibm.as400.access.*;
import com.lawson.api.*;
//import com.lawson.m3.conversion.ConversionNotesFileData;
import com.treetop.*;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.app.coa.*;
import com.treetop.app.inventory.*;
import com.treetop.utilities.*;
import com.treetop.utilities.html.*;
import com.treetop.viewbeans.CommonRequestBean;

/**
 * @author twalto .
 *
 * Services class to obtain and return data 
 * to business objects.
 * 
 * Certificate of Analysis 
 */
public class ServiceAttribute extends BaseService {

	public static final String library = "M3DJDPRD.";

	/**
	 * 
	 */
	public ServiceAttribute() {
		super();
	}
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		if (1 == 0)
		{
			try	{
	 			Lot lot = new Lot();
	 			lot.setItemNumber("201717");
	 			lot.setWarehouse("209");
	 			lot.setLocation("70270");
	 			lot.setLotNumber("113240401026");
				Vector attributes = findAttributeValues("PRD", lot);
				
				System.out.println("stop");
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		//test drop down attributes 
		if (1 == 0)
		{
			CommonRequestBean crb = new CommonRequestBean();
			Vector returnVector = new Vector();
			crb.setIdLevel1("PROC");
			returnVector = dropDownAttributes(crb);
			String stopHere = "";
			
			crb.setIdLevel1("MICRO");
			returnVector = dropDownAttributes(crb);
			stopHere = "";
			
		}
	}

	/**
	 * Load class fields from result set.
	 */
	
	public static Attribute loadFieldsAttribute(String requestType,
												ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Attribute returnAttribute = new Attribute();
	
		try{ 
			if (requestType.equals("loadSingle"))
			{
				try
				{
//					 Use the Description from the Attribute File - MATRMA
					returnAttribute.setAttribute(rs.getString("AAATID").trim());
					returnAttribute.setAttributeName(rs.getString("AATX15").trim());
					returnAttribute.setAttributeDescription(rs.getString("AATX30").trim());
					returnAttribute.setDecimalPlaces(rs.getString("AADCCD").trim());
					if (rs.getString("AAATVC").equals("1"))
						returnAttribute.setFieldType("alpha");
					if (rs.getString("AAATVC").equals("2"))
						returnAttribute.setFieldType("numeric");
					if (rs.getString("AAATVC").equals("3"))
						returnAttribute.setFieldType("date");
					if (rs.getString("AAATVC").equals("4"))
						returnAttribute.setFieldType("text");
					returnAttribute.setAttributeUOM(rs.getString("AAUNMS").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
					// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
				}	
				try
				{
//					 Use the Description of the Unit of Measure - CSYTAB
					returnAttribute.setAttributeUOMDescription(rs.getString("CTTX15").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
					// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
				}								
			}
			if (requestType.equals("loadSingleValue"))
			{
				try
				{
//					 Use the Description from the Attribute File - MPDOPT
					returnAttribute.setAttributeName(rs.getString("PFTX15").trim());
					returnAttribute.setAttributeDescription(rs.getString("PFTX30").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
					// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
				}	
			}
			if (requestType.equals("loadAttributeLine"))
			{
				try
				{
//					 Attribute Model Header -- MAMOHE -- 
					 returnAttribute.setAttributeModel(rs.getString("ADATMO").trim());
					 returnAttribute.setAttributeModelName(rs.getString("ADTX15").trim());
					 returnAttribute.setAttributeModelDescription(rs.getString("ADTX40").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Attribute Model Header Information
					// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute Model Header: " + e);
				}
				try
				{
//					 Use the Description from the Attribute Model Line File - MAMOLI
					returnAttribute.setAttributeSequence(rs.getString("AEANSQ"));
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
					// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute Model Line: " + e);
				}
				try
				{
//					 Use the Description from the Attribute File - MATRMA
					returnAttribute.setAttribute(rs.getString("AAATID").trim());
					returnAttribute.setAttributeName(rs.getString("AATX15").trim());
					returnAttribute.setAttributeDescription(rs.getString("AATX30").trim());
					returnAttribute.setDecimalPlaces(rs.getString("AADCCD").trim());
					if (rs.getString("AAATVC").equals("1"))
						returnAttribute.setFieldType("alpha");
					if (rs.getString("AAATVC").equals("2"))
						returnAttribute.setFieldType("numeric");
					if (rs.getString("AAATVC").equals("3"))
						returnAttribute.setFieldType("date");
					if (rs.getString("AAATVC").equals("4"))
						returnAttribute.setFieldType("text");
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
					// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
				}	
				try
				{
//					 Use the Description from the Attribute Hi/Low - MATVAV
//					 called From value
					returnAttribute.setLowValue(rs.getString("AJANUF"));
					// called To value
					returnAttribute.setHighValue(rs.getString("AJANUT"));
				}
				catch(Exception e)
				{
					// Problem Caught when loading the SalesOrderHeader/Detail information
					// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
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
			throwError.append("ServiceItem.");
			throwError.append("loadFieldsItem(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnAttribute;
	}

	/**
	 * Use to Validate a Sent in Attribute ID, Return Message if Item is not found
	 */
	
	public static String verifyAttribute(String environment, String attributeID)
			throws Exception
	{ 
		// Comments about Changes to Method
		//  2/2/09 - TWalton -- Removed Connection Pool, change to DBConnection
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyAttribute";
			String sqlString = "";
			
			// verify base class initialization.
			ServiceItem a = new ServiceItem();
						
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Attribute ID
			if (attributeID == null || attributeID.trim().equals(""))
				rtnProblem.append(" Attribute ID must not be null or empty.");
			
			// get Attribute ID Info
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(attributeID);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
				throwError.append(" error trying to build sqlString. ");
				rtnProblem.append(" Problem when building Test for Attribute: ");
				rtnProblem.append(attributeID + " PrintScreen this message and send to Information Services. ");
				}
			}
			
			// get a connection. execute sql, build return object.
			if (rtnProblem.toString().equals("")) {
				try {
					conn = ServiceConnection.getConnectionStack5();
					
					findIt = conn.prepareStatement(sqlString);
					rs = findIt.executeQuery();
					
					if (rs.next())
					{
//						 it exists and all is good.
					} else {
						rtnProblem.append(" Attribute ID '" + attributeID + "' ");
						rtnProblem.append("does not exist. ");
					}
					
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Attribute ID: ");
					rtnProblem.append(attributeID + " PrintScreen this message and send to Information Services. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Item Number class ");
			throwError.append("from the result set. " + e) ;
		// return connection.
		} finally {
			if (conn != null) {
				try {
					if (conn != null)
						ServiceConnection.returnConnectionStack5(conn);
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
			throwError.append("ServiceAttribute.");
			throwError.append("verifyAttribute(String: attributeID)");
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
		
		try { 
			String library = GeneralUtility.getLibrary(environment);
			// TEST - Verify ATTRIBUTE
			if (inRequestType.equals("verifyAttribute"))
			{
				// cast the incoming parameter class.
				String attributeID = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT  AAATID  ");
				sqlString.append("FROM " + library + ".MATRMA ");
				sqlString.append(" WHERE AAATID = '" + attributeID + "' ");
			}
//			 TEST - Verify ATTRIBUTE MODEL
			if (inRequestType.equals("verifyAttributeModel"))
			{
				// cast the incoming parameter class.
				String attributeModel = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT  ADATMO  ");
				sqlString.append("FROM " + library + ".MAMOHE ");
				sqlString.append(" WHERE ADATMO = '" + attributeModel + "' ");
			}
			// DROP DOWN LISTS
			if (inRequestType.equals("ddAttributeValues"))
			{
				// cast the incoming parameter class
				 //(0) -- Attribute Model
				 //(1) -- Attribute ID
				// build the SQL Statement
				sqlString.append("SELECT AJAALF, PFTX30  ");
				sqlString.append("FROM " + library + ".MATVAV ");
				sqlString.append(" LEFT OUTER JOIN " + library + ".MPDOPT ");
				sqlString.append("   ON AJAALF = PFOPTN ");
				sqlString.append(" WHERE AJOBV1 = '" + requestClass.elementAt(0) + "' ");
				sqlString.append("   AND AJATID = '" + requestClass.elementAt(1) + "' ");
				sqlString.append(" GROUP BY AJAALF, PFTX30 ");
				sqlString.append(" ORDER BY AJAALF");
			}
			if (inRequestType.equals("ddAttributes"))
			{
				// cast the incoming parameter class
				 // ID Level 1 -- Attribute Group
				CommonRequestBean crb = (CommonRequestBean) requestClass.elementAt(0);
				sqlString.append("SELECT AAATVC, AAATID, AATX30, IFNULL(CTTX40,'') as CTTX40 "); 
				sqlString.append("FROM " + library + ".MATRMA ");
				sqlString.append("LEFT OUTER JOIN " + library + ".CSYTAB ");
				sqlString.append("ON AACONO = CTCONO AND CTDIVI = '' ");
				sqlString.append("AND CTSTCO = 'UNIT' AND AAUNMS = CTSTKY ");
				sqlString.append("WHERE AACONO = 100 ");
				// 11/18/13 TWalton Put code in place for conversion
				//  ONCE conversion is complete, will revert to the ELSE
				if (crb.getIdLevel1().trim().equals("ANLYT"))
				{
					sqlString.append("AND (AAATGR = '" + crb.getIdLevel1().trim() + "' or AAATGR = '') ");
				}else{
				   sqlString.append("AND AAATGR = '" + crb.getIdLevel1().trim() + "' ");
				}
				sqlString.append(" AND (AAATVC = 1 or AAATVC = 2) ");
				sqlString.append(" ORDER BY AATX30, AAATID");
				
			}
			
			if (inRequestType.equals("ddCrops")) 				
			{
				sqlString.append("SELECT ADATMO, SUBSTRING(ADTX40,3,37) as ADTEXT ");
				sqlString.append("FROM " + library + ".MAMOHE ");
				sqlString.append("WHERE UPPER(ADATMO) LIKE ('QA%') AND ");
				sqlString.append("UPPER(ADATMO) NOT LIKE ('%ENTERS%') ");
				sqlString.append("ORDER BY ADATMO, ADTX40");
			}		
			
			if ((inRequestType.equals("ddFruitVarieties")) ||
				(inRequestType.equals("ddCropVarieties")))
			{
				sqlString.append("SELECT ADATMO, SUBSTRING(ADTX40,3,37) as ADTEXT, ");
				sqlString.append("AJAALF, PFTX30 "); 
				sqlString.append("FROM " + library + ".MAMOHE ");
				sqlString.append("JOIN " + library + ".MAMOLI ");
				sqlString.append(" ON ADCONO = AECONO AND ADATMO = AEATMO ");
				sqlString.append("JOIN " + library + ".MATVAV ");
				sqlString.append(" ON ADCONO = AJCONO AND AEATID = AJATID ");
				sqlString.append("JOIN " + library + ".MPDOPT ");
				sqlString.append(" ON ADCONO = PFCONO AND AJAALF = PFOPTN ");
				sqlString.append("WHERE UPPER(ADATMO) LIKE ('QA%') AND ");
				sqlString.append("UPPER(ADATMO) NOT LIKE ('%ENTERS%') AND AJOBV1 = ADATMO ");
				
				if (inRequestType.equals("ddCropVarieties")) 				
				{
					String oneCrop = (String) requestClass.elementAt(0);
					sqlString.append("AND UPPER(ADATMO) LIKE UPPER('%" + oneCrop.toUpperCase() + "%') ");				
				}
				
				sqlString.append("ORDER BY ADATMO, AJAALF");					
			}
			//-----------------------------------------------------------
			// FIND
			//-----------------------------------------------------------
			if (inRequestType.equals("singleAttribute"))
			{
				// cast the incoming parameter class.
				String attributeID = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT  AAATID, AATX15, AATX30, AADCCD, AAATVC, AAUNMS,  ");
				sqlString.append("  CTTX15 ");
				sqlString.append("FROM " + library + ".MATRMA ");
				sqlString.append("LEFT OUTER JOIN " + library + ".CSYTAB ON CTCONO = AACONO ");
				sqlString.append("  AND CTSTCO = 'UNIT'  AND CTSTKY = AAUNMS ");
				sqlString.append(" WHERE AACONO = 100 AND AAATID = '" + attributeID + "' ");
			}
			if (inRequestType.equals("singleAttributeValue"))
			{
				// cast the incoming parameter class.
				String attributeValue = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT  PFOPTN, PFTX30, PFTX15  ");
				sqlString.append("FROM " + library + ".MPDOPT ");
				sqlString.append(" WHERE PFCONO = 100 AND PFOPTN = '" + attributeValue + "' ");
			}
			if (inRequestType.equals("attributeText"))
			{
				sqlString.append("SELECT TLCONO, TLDIVI, TLTXID, TLTXVR, TLLINO, TLTX60 ");
				sqlString.append("FROM " + library + ".MSYTXL ");
				sqlString.append("WHERE TLCONO = 100 AND TLTXID = '" + ((String) requestClass.elementAt(0)).trim() + "' ");
				sqlString.append("ORDER BY TLLINO ");
			}
			
			
			if (inRequestType.equals("loadAttributeValues")) {
				CommonRequestBean crb = (CommonRequestBean) requestClass.elementAt(0);
				String itemNumber 	= crb.getIdLevel1();
				String warehouse 	= crb.getIdLevel2();
				String location 	= crb.getIdLevel3();
				String lotNumber 	= crb.getIdLevel4();
				
				sqlString.append(" SELECT ");
				sqlString.append(" ifNull(MLITNO,LMITNO) as MLITNO, ");
				sqlString.append(" ifNull(MLBANO,LMBANO) as MLBANO, ");
				sqlString.append(" MMITDS, ");
				sqlString.append(" LMATNR, ADATMO, ADTX15, ADTX40, AGANSQ,  ");
				sqlString.append(" AAATID, AATX15, AATX30, AADCCD, AAATVC,");
				sqlString.append(" AAUNMS, IFNULL(CTTX15,'') AS CTTX15, ");
				sqlString.append(" AGITNO, AGATVA, AGATVN,  ");
				sqlString.append(" IFNULL(AJANUF,'') AS AJANUF, IFNULL(AJANUT,'') AS AJANUT, ");
				sqlString.append(" IFNULL(HCTX60,'') AS HCTX60 ");
				
				
				sqlString.append(" FROM " + library + ".MILOMA ");
				
				sqlString.append(" LEFT OUTER JOIN " + library + ".MITLOC ON ");
				sqlString.append(" LMCONO=LMCONO AND LMITNO=MLITNO AND LMBANO=MLBANO ");
				
				sqlString.append(" INNER JOIN " + library + ".MITMAS ON ");
				sqlString.append(" LMCONO=MMCONO AND LMITNO=MMITNO ");
														
				sqlString.append(" INNER JOIN " + library + ".MIATTR ON ");
				sqlString.append(" LMCONO=AGCONO AND LMATNR=AGATNR AND LMITNO=AGITNO AND LMBANO=AGBANO ");
				
				
				
				sqlString.append(" INNER JOIN " + library + ".MAMOHE ON ");
				sqlString.append(" LMCONO=ADCONO AND AGATMO=ADATMO ");
				
				sqlString.append(" INNER JOIN " + library + ".MATRMA ON ");
				sqlString.append(" LMCONO=AACONO AND AGATID=AAATID ");
				
				//do not get attribute type 4's (text)
				//there is a problem with the API when updating them
				//only ATID='HOLD COMMENTS' can be handled right now using view MSYTXLVHC
				sqlString.append(" AND (AAATVC<>4 OR AAATID='LOT COMMENTS') ");
				
				sqlString.append(" LEFT OUTER JOIN " + library + ".MATVAV ON ");
				sqlString.append(" LMCONO=AJCONO AND AGATID=AJATID AND AGATMO=AJOBV1 AND AAATVC=2 ");
								
				sqlString.append(" LEFT OUTER JOIN " + library + ".CSYTAB AS UNMS ON ");
				sqlString.append(" LMCONO=UNMS.CTCONO AND UNMS.CTSTCO='UNIT' AND AAUNMS=UNMS.CTSTKY ");
								
				//view setup for hold comments - returns only the highest text block line
				sqlString.append(" LEFT OUTER JOIN " + library + ".MSYTXLVHC ON ");
				sqlString.append(" AAATVC=4 AND AAATID='LOT COMMENTS' ");
				sqlString.append(" AND LMITNO=HCITNO AND LMBANO=HCBANO AND LMATNR=HCATNR ");
				
				sqlString.append(" WHERE LMCONO=100 ");
				
				if (!itemNumber.trim().equals("")) {
					sqlString.append(" AND LMITNO='" + itemNumber + "' ");
				}
				if (!warehouse.trim().equals("")) {
					sqlString.append(" AND MLWHLO='" + warehouse + "' ");
				}
				if (!location.trim().equals("")) {
					sqlString.append(" AND MLWHSL='" + location + "' ");
				}
				if (!lotNumber.trim().equals("")) {
					sqlString.append(" AND LMBANO='" + lotNumber + "' ");
				}
								
				sqlString.append(" ORDER BY LMITNO, LMBANO, AGANSQ ");
			}
			
		} catch (Exception e) {
				throwError.append(" Error building sql statement"
							 + " for request type " + inRequestType + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceAttribute.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		
		return sqlString.toString();
	}

	/**
	 * Use to Validate a Send in an Attribute Model, Return Message if Item is not found
	 */
		
		public static String verifyAttributeModel(String environment, String attributeModel)
				throws Exception
		{
			// Update Comments for Method
			// 2/2/09 TWalton - Changed from Connection pool to DBConnection.
			StringBuffer throwError = new StringBuffer();
			StringBuffer rtnProblem = new StringBuffer();
			Connection conn = null; // New Lawson Box - Lawson Database
			PreparedStatement findIt = null;
			ResultSet rs = null;
			try { 
				String requestType = "verifyAttributeModel";
				String sqlString = "";
				
				// verify base class initialization.
				ServiceItem a = new ServiceItem();
							
				if (environment == null || environment.trim().equals(""))
					environment = "PRD";
				// verify incoming Attribute Model
				if (attributeModel == null || attributeModel.trim().equals(""))
					rtnProblem.append(" Attribute Model must not be null or empty.");
				
				// get Attribute Model Info.
				if (throwError.toString().equals(""))
				{
					try {
						Vector parmClass = new Vector();
						parmClass.addElement(attributeModel);
						sqlString = buildSqlStatement(environment, requestType, parmClass);
					} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnProblem.append(" Problem when building Test for Attribute Model: ");
					rtnProblem.append(attributeModel + " PrintScreen this message and send to Information Services. ");
					}
				}
				
				// get a connection. execute sql, build return object.
				if (rtnProblem.toString().equals("")) {
					try {
						
						conn = ServiceConnection.getConnectionStack5();
						
						findIt = conn.prepareStatement(sqlString);
						rs = findIt.executeQuery();
						
						if (rs.next())
						{
	//						 it exists and all is good.
						} else {
							rtnProblem.append(" Attribute Model '" + attributeModel + "' ");
							rtnProblem.append("does not exist. ");
						}
						
					} catch (Exception e) {
						throwError.append(" error occured executing a sql statement. " + e);
						rtnProblem.append(" Problem when finding AttributeModel: ");
						rtnProblem.append(attributeModel + " PrintScreen this message and send to Information Services. ");
					}
				}
			} catch(Exception e)
			{
				throwError.append(" Problem loading the Item Number class ");
				throwError.append("from the result set. " + e) ;
			// return connection.
			} finally {
				if (conn != null) {
					try {
						if (conn != null)
							ServiceConnection.returnConnectionStack5(conn);
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
				throwError.append("ServiceAttribute.");
				throwError.append("verifyAttributeModel(String: attributeModel)");
				throw new Exception(throwError.toString());
			}
			return rtnProblem.toString();
		}

	/**
	 * Load class fields from result set.
	 */	
		public static AttributeValue loadFieldsAttributeValue(String requestType,
													     	  ResultSet rs)
				throws Exception
		{
			StringBuffer throwError = new StringBuffer();
			AttributeValue returnAttribute = new AttributeValue();
		
			try{ 
				if (requestType.equals("loadAttributeValueLine") || 
					requestType.equals("loadAttributeValues"))
				{
					try
					{
	//					 Attribute Model Header -- MAMOHE -- 
						 returnAttribute.setAttributeModel(rs.getString("ADATMO").trim());
						 returnAttribute.setAttributeModelName(rs.getString("ADTX15").trim());
						 returnAttribute.setAttributeModelDescription(rs.getString("ADTX40").trim());
					}
					catch(Exception e)
					{
						// Problem Caught when loading the Attribute Model Header Information
						// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute Model Header: " + e);
					}
					try
					{
	//					 Use the Description from the Attribute Model Line File - MAMOLI
						returnAttribute.setAttributeSequence(rs.getString("AEANSQ"));
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
						// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute Model Line: " + e);
					}
					try
					{
	//					 Use the Description from the Attribute File - MATRMA
						returnAttribute.setAttribute(rs.getString("AAATID").trim());
						returnAttribute.setAttributeName(rs.getString("AATX15").trim());
						returnAttribute.setAttributeDescription(rs.getString("AATX30").trim());
						returnAttribute.setDecimalPlaces(rs.getString("AADCCD").trim());
						if (rs.getString("AAATVC").equals("1"))
							returnAttribute.setFieldType("alpha");
						if (rs.getString("AAATVC").equals("2"))
							returnAttribute.setFieldType("numeric");
						if (rs.getString("AAATVC").equals("3"))
							returnAttribute.setFieldType("date");
						if (rs.getString("AAATVC").equals("4"))
							returnAttribute.setFieldType("text");
						returnAttribute.setAttributeUOM(rs.getString("AAUNMS").trim());
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
						// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
					}	
					try
					{
	//					 Use the Description of the Unit of Measure
						returnAttribute.setAttributeUOM(rs.getString("AAUNMS"));
						returnAttribute.setAttributeUOMDescription(rs.getString("CTTX15").trim());
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
						// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
					}	
					try
					{
	//					 Use the Description from the Attribute Hi/Low - MATVAV
	//					 called From value
						if (rs.getString("AJANUF") != null)
						{
						  returnAttribute.setLowValue(rs.getString("AJANUF"));
						  // called To value
						  returnAttribute.setHighValue(rs.getString("AJANUT"));
						}
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
						// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
					}	
					try
					{
					   // From File MIATTR
						returnAttribute.setItemNumber(rs.getString("AGITNO").trim());
						try {
						   if (rs.getString("AAATVC").equals("2")) {
							  returnAttribute.setValue(rs.getString("AGATVN").trim());
							  
						   } else if (rs.getString("AAATVC").equals("3")) {
								 String bldDate = rs.getString("AGATVA").trim();
								 try {
									 if (!bldDate.trim().equals("") &&
									     bldDate.trim().length() == 8)
									 {
										 bldDate = bldDate.substring(6,8) + "/" + bldDate.substring(2,4) + "/" + bldDate.substring(4,6); 
									 }
								 }catch(Exception e){ }
								 returnAttribute.setValue(bldDate);
								 
						  } else if (rs.getString("AAATVC").equals("4")) {
							  //Set the text id
							  if (returnAttribute.getAttribute().equals("LOT COMMENTS")) {
								  returnAttribute.setValue(rs.getString("HCTX60").trim());
							  } else {
								  returnAttribute.setValue(rs.getString("AGTXID").trim());
							  }
							  
						  } else {
							   returnAttribute.setValue(rs.getString("AGATVA").trim());
						  
						   }
						} catch(Exception ei) {
							
						}
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
						// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
					}
					
					if (requestType.equals("loadAttributeValueLine")) {
						try {
							//Build a LOT Class for the Lot - MILOMA
							returnAttribute.setLotObject(ServiceLot.loadFieldsM3Lot(rs, "coaLot"));
						} catch(Exception e){
							// Problem Caught when loading the Lot information
							// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
						}	
					}
					
					if (requestType.equals("loadAttributeValues")) {
						try {
							//Build a LOT Class for the Lot - MILOMA
							returnAttribute.setLotObject(ServiceLot.loadFieldsM3Lot(rs, "attributeValuesLot"));
						} catch(Exception e){
							// Problem Caught when loading the Lot information
							// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
						}	
					}
					
				}
				
				
				
				
				if (requestType.equals("acidList"))
				{
					try
					{
	//					 Use the Description from the Attribute File - MATRMA
						returnAttribute.setAttributeName(rs.getString("AATX15").trim());
						returnAttribute.setAttributeDescription(rs.getString("AATX30").trim());
						returnAttribute.setDecimalPlaces(rs.getString("AADCCD").trim());
						if (rs.getString("AAATVC").equals("1"))
							returnAttribute.setFieldType("alpha");
						if (rs.getString("AAATVC").equals("2"))
							returnAttribute.setFieldType("numeric");
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
						// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
					}	
					try
					{
						returnAttribute.setItemNumber(rs.getString("MLITNO").trim());
					}
					catch(Exception e)
					{}
					try
					{
					   // From File MIATTR
						returnAttribute.setAttribute(rs.getString("AGATID").trim());
						try
						{
							if (rs.getString("AAATVC").equals("2"))
								returnAttribute.setValue(rs.getString("AGATVN"));
							else
								returnAttribute.setValue(rs.getString("AGATVA"));
						}
						catch(Exception ei)
						{}
					}
					catch(Exception e)
					{
						// Problem Caught when loading the SalesOrderHeader/Detail information
						// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
					}
					try
					{
	//					 Build a LOT Class for the Lot -  MITLOC -- BRIX FILE
						returnAttribute.setLotObject(ServiceLot.loadFieldsM3Lot(rs, "acidList"));
					}
					catch(Exception e)
					{
						// Problem Caught when loading the Lot information
						// System.out.println("Error Occurred ServiceAttribute.loadFieldsAttribute() Attribute: " + e);
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
				throwError.append("ServiceItem.");
				throwError.append("loadFieldsItem(requestType, rs: ");
				throwError.append(requestType + "). ");
				throw new Exception(throwError.toString());
			}
			return returnAttribute;
		}

	/**
	 *   Method Created 11/10/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
	 * @return Vector - of DropDownSingle 
	 * 
	 *  Incoming Vector:
	 *    Element 1 = String - Attribute Model
	 * 	  Element 2 = String - Attribute ID
	 */
	public static Vector dropDownAttributeValues(Vector inValues)
	{
//		 Changes to Method
		// 2/2/09 TWalton - Change from Connection Stack to dbConnection
		Vector ddit = new Vector();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ServiceConnection.getConnectionStack5();
			ddit = dropDownAttributeValues(inValues, conn);
		} catch (Exception e)
		{
			// Don't really need to worry about any exceptions
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
		// return value
		return ddit;
	}

	/**
	 * Method Created 11/7/08 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in a Vector (TYPE of Select -- will KNOW if need to see ALL or just certain ones)
	 * @return Vector - of DropDownSingle
	 */
	private static Vector dropDownAttributeValues(Vector inValues, 
										   	      Connection conn)
	{
		Vector ddit = new Vector();
		ResultSet rs = null;
		PreparedStatement listThem = null;
		try
		{
			try {
				   // Get the list of Item Type Values - Along with Descriptions
			   listThem = conn.prepareStatement(buildSqlStatement("PRD", "ddAttributeValues", inValues));
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
			 		DropDownSingle dds = loadFieldsDropDownSingle("ddAttributeValues", rs);
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
	//		throwError.append("dropDownItemGroup(");
	//		throwError.append("Vector, conn). ");
	//		throw new Exception(throwError.toString());
	//	}
		// return value
		return ddit;
	}

	/**
	 * Load class fields from result set.
	 */
	
	public static DropDownDual loadFieldsDropDownDual(String requestType,
							          		  	      ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		DropDownDual returnValue = new DropDownDual();
		
		try{ 
			
		  if ((requestType.equals("ddFruitVarieties")) ||
			  (requestType.equals("ddCropVarieties")))			
		  {
			returnValue.setMasterValue(rs.getString("ADATMO").trim());
			returnValue.setMasterDescription(rs.getString("ADTEXT").trim());
			returnValue.setSlaveValue(rs.getString("AJAALF").trim());
			returnValue.setSlaveDescription(rs.getString("PFTX30").trim());
		  }
		  		  
		} catch(Exception e)
		{
			throwError.append(" Problem loading the DropDownDual class ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceAttribute.");
			throwError.append("loadFieldsDropDownDual(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

	/**
	 * Return an Attribute business object using a
	 * Gtin number.
	 * @param String Attribute
	 * @return Attribute business object.
	 * @throws Exception
	 */
	
	public static Attribute findAttribute(String environment, 
										  String attributeID)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		Attribute returnValue = new Attribute();
		try {
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			if (attributeID == null || attributeID.trim().equals(""))
				   throwError.append(" Attribute ID must not be null or empty.");
			//  Get Connection to attach to files
			conn = ConnectionStack.getConnection();	
			// get Attribute Info.
			if (throwError.toString().equals(""))
			{
				try {
					returnValue = findAttribute(environment, attributeID, conn);
				} catch (Exception e) {
				throwError.append(" error trying to retrieve Item Data. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Retrieving Attribute Information. " + e);
		// return connection.
		} finally {
			if (conn != null) {
				try {
					ConnectionStack.returnConnection(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}	
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceAttribute.");
			throwError.append("findAttribute(attributeID:" + attributeID);
			throwError.append("). ");
			throw new Exception(throwError.toString());
		}
	
		// return value
		return returnValue;
	}

	/**
	 * Return a drop down Single box object for crops. 	
	 * @return dropDownSingle business object
	 * @throws Exception
	 */
	private static Vector dropDownCrop(String environment, 							                 
									   Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector<DropDownSingle> returnValue = new Vector<DropDownSingle>();
		PreparedStatement findIt = null;
		ResultSet rs = null;
		String sqlString = new String();
		String requestType = new String("ddCrops");		
		
		try { 
			
			try {
				Vector<String> parmClass = new Vector<String>();
				parmClass.addElement(requestType);
				sqlString = buildSqlStatement(environment, requestType, parmClass);	
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
						try
						{
							DropDownSingle oneCrop = loadFieldsDropDownSingle(requestType, rs);
							returnValue.addElement(oneCrop);								
							
						} catch (Exception e) {
							throwError.append(" error occured loading fields from an sql statement. " + e);
						}
					}
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem retrieving data for crops. " + e) ;
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
			throwError.append("ServiceAttribute.");
			throwError.append("dropDownCrop(requestType:");
			throwError.append(requestType + ")");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

	/**	 	 
	 * @return DropDownSingle
	 * @throws Exception
	 *
	 *  Change Method:  DEisen 7/20/10 -- Copied and change for crop drop down box.
	 */
	
	public static Vector dropDownCrop(String environment)										               
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		Vector returnValue = new Vector();
		
		try {
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";			
			//  Get Connection to attach to files
			conn = com.treetop.utilities.ConnectionStack7.getConnection();
			
			if (throwError.toString().equals(""))
			{
				try {
					returnValue = dropDownCrop(environment, conn);
				} catch (Exception e) {
				throwError.append(" error trying to retrieve information about crops. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Retrieving information about crops. " + e);
		// return connection.
		} finally {
			if (conn != null) {
				try {
					com.treetop.utilities.ConnectionStack7.returnConnection(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}	
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceAttribute.");
			throwError.append("DropDownCrop.");		
			throw new Exception(throwError.toString());
		}
	
		// return value
		return returnValue;
	}

	/**
	 * Return a String that will be the Text Information
	 *    will put a <br> in between lines for display purposes
	 * @param String Text ID to find the infomation
	 * @return String
	 * @throws Exception
	 *
	 *  Change Method:  TWalton 6/10/10 -- Added Method
	 */
	private static String findAttributeTextValue(String environment, 
										         String textID, 
										         Connection conn)
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnValue = new StringBuffer();
		PreparedStatement findIt = null;
		ResultSet rs = null;
		String sqlString = new String();
		try { 
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(textID);
				sqlString = buildSqlStatement(environment, "attributeText", parmClass);
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
						// Build a String for display with <br> in between lines
						try
						{
							if (!rtnValue.toString().trim().equals(""))
							   rtnValue.append("</br>");
							rtnValue.append(rs.getString("TLTX60").trim());
							
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
			throwError.append(" Problem Retrieving Data for Attribute Class. " + e) ;
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
			throwError.append("ServiceAttribute.");
			throwError.append("findAttributeTextValue(textID:");
			throwError.append(textID + ")");
			throw new Exception(throwError.toString());
		}
		return rtnValue.toString();
	}

	/**
	 * Return an Attribute business object 
	 * @param String Attribute Value
	 * @return Attribute business object.
	 * @throws Exception
	 */
	
	public static Attribute findAttributeValueDescription(String environment, 
										                  String attributeValue)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		Attribute returnValue = new Attribute();
		try {
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			if (attributeValue == null || attributeValue.trim().equals(""))
				   throwError.append(" Attribute Value must not be null or empty.");
			//  Get Connection to attach to files
			conn = ConnectionStack.getConnection();	
			// get Attribute Info.
			if (throwError.toString().equals(""))
			{
				try {
					returnValue = findAttributeValueDescription(environment, attributeValue, conn);
				} catch (Exception e) {
				throwError.append(" error trying to retrieve Data. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Retrieving Attribute Information. " + e);
		// return connection.
		} finally {
			if (conn != null) {
				try {
					ConnectionStack.returnConnection(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}	
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceAttribute.");
			throwError.append("findAttribute(attributeValue:" + attributeValue);
			throwError.append("). ");
			throw new Exception(throwError.toString());
		}
	
		// return value
		return returnValue;
	}

	/**
	 * Return a Attribute business object for incoming 
	 * attributeValue.
	 * @param String attribute Value
	 * @return Attribute business object
	 * @throws Exception
	 */
	private static Attribute findAttributeValueDescription(String environment, 
										                   String attributeValue, 
										                   Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Attribute rtnValue = new Attribute();
		PreparedStatement findIt = null;
		ResultSet rs = null;
		String sqlString = new String();
		try { 
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(attributeValue);
				sqlString = buildSqlStatement(environment, "singleAttributeValue", parmClass);
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
							rtnValue = loadFieldsAttribute("loadSingleValue", rs);
							
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
			throwError.append(" Problem Retrieving Data for Attribute Class. " + e) ;
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
			throwError.append("ServiceAttribute.");
			throwError.append("findAttribute(AttributeValue:");
			throwError.append(attributeValue + ")");
			throw new Exception(throwError.toString());
		}
		return rtnValue;
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
			
		  if (requestType.equals("ddAttributeValues"))
		  {
			returnValue.setValue(rs.getString("AJAALF").trim());
			returnValue.setDescription(rs.getString("PFTX30").trim());
		  }
		  
		  if (requestType.equals("ddAttributes"))
		  {
			returnValue.setValue(rs.getString("AAATID").trim());
			returnValue.setDescription(rs.getString("AATX30").trim() + "-" + rs.getString("CTTX40").trim() + "-" + rs.getString("AAATVC").trim());
		  }
		  
		  if (requestType.equals("ddCrops"))
		  {
			  returnValue.setValue(rs.getString("ADATMO").trim());
				returnValue.setDescription(rs.getString("ADTEXT").trim());
		  }
		  
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Drop Down Single class ");
			throwError.append("from the result set. " + e) ;
		}
		
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceAttribute.");
			throwError.append("loadFieldsDropDownSingle(requestType, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

	/**
	 * Return a String that will be the Text Information
	 *    will put a <br> in between lines for display purposes
	 * @param String Text ID to find the infomation
	 * @return String
	 * @throws Exception
	 *
	 *  Change Method:  TWalton 6/10/10 -- Added Method
	 */
	
	public static String findAttributeTextValue(String environment, 
										        String textID)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		String returnValue = new String();
		try {
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			if (textID == null || textID.trim().equals(""))
				throwError.append(" Text ID must not be null or empty.");
			//  Get Connection to attach to files
			conn = ConnectionStack.getConnection();	
			// get Attribute Info.
			if (throwError.toString().equals(""))
			{
				try {
					returnValue = findAttributeTextValue(environment, textID, conn);
				} catch (Exception e) {
				throwError.append(" error trying to retrieve text information about an text Attribute. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Retrieving information about a text Attribute. " + e);
		// return connection.
		} finally {
			if (conn != null) {
				try {
					ConnectionStack.returnConnection(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}	
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceAttribute.");
			throwError.append("findAttributeTextValue(textID:" + textID);
			throwError.append("). ");
			throw new Exception(throwError.toString());
		}
	
		// return value
		return returnValue;
	}

	/**
	 * Return a Attribute business object for incoming 
	 * attributeID.
	 * @param String attribute ID
	 * @return Attribute business object
	 * @throws Exception
	 */
	private static Attribute findAttribute(String environment, 
										   String attributeID, 
										   Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Attribute rtnValue = new Attribute();
		PreparedStatement findIt = null;
		ResultSet rs = null;
		String sqlString = new String();
		try { 
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(attributeID);
				sqlString = buildSqlStatement(environment, "singleAttribute", parmClass);
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
							rtnValue = loadFieldsAttribute("loadSingle", rs);
							
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
			throwError.append(" Problem Retrieving Data for Attribute Class. " + e) ;
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
			throwError.append("ServiceAttribute.");
			throwError.append("findAttribute(AttributeID:");
			throwError.append(attributeID + ")");
			throw new Exception(throwError.toString());
		}
		return rtnValue;
	}

	/**	 
	 * @return DropDownDual
	 * @throws Exception
	 *
	 *  Change Method:  DEisen 7/20/10 -- Copied and change for fruit variety drop down box.
	 */
	
	public static Vector dropDownFruitVariety(String environment)										               
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		Vector returnValue = new Vector();
		
		try {
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";			
			//  Get Connection to attach to files
			conn = com.treetop.utilities.ConnectionStack7.getConnection();
			
			if (throwError.toString().equals(""))
			{
				try {
					returnValue = dropDownFruitVariety(environment, conn);
				} catch (Exception e) {
				throwError.append(" error trying to retrieve information about fruit varieties. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Retrieving information about fruit varieties. " + e);
		// return connection.
		} finally {
			if (conn != null) {
				try {
					com.treetop.utilities.ConnectionStack7.returnConnection(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}	
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceAttribute.");
			throwError.append("DropDownFruitVariety.");		
			throw new Exception(throwError.toString());
		}
	
		// return value
		return returnValue;
	}

	/**
	 * Return a drop down dual box object for fruit varieties. 	
	 * @return dropDownDual business object
	 * @throws Exception
	 */
	private static Vector dropDownFruitVariety(String environment, 	// string crop									                 
										       Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector<DropDownDual> returnValue = new Vector<DropDownDual>();
		PreparedStatement findIt = null;
		ResultSet rs = null;
		String sqlString = new String();
		String requestType = new String("ddFruitVarieties");		
		
		try { 
			
			try {
				Vector<String> parmClass = new Vector<String>();
				parmClass.addElement(requestType);
				sqlString = buildSqlStatement(environment, requestType, parmClass);	//parmClass =  crop for ddCropVariety
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
						try
						{
							DropDownDual oneVariety = loadFieldsDropDownDual(requestType, rs);
							returnValue.addElement(oneVariety);								
							
						} catch (Exception e) {
							throwError.append(" error occured loading fields from an sql statement. " + e);
						}
					}
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem retrieving data for fruit varieties. " + e) ;
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
			throwError.append("ServiceAttribute.");
			throwError.append("dropDownFruitVariety(requestType:");
			throwError.append(requestType + ")");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

	/**
	 * Return a drop down Dual box object for one crop varieties.	
	 * @return dropDownDual business object
	 * @throws Exception
	 */
	private static Vector dropDownCropVariety(String environment, String crop,								                 
										      Connection conn)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector<DropDownDual> returnValue = new Vector<DropDownDual>();
		PreparedStatement findIt = null;
		ResultSet rs = null;
		String sqlString = new String();
		String requestType = new String("ddCropVarieties");		
		
		try { 
			
			try {
				Vector<String> parmClass = new Vector<String>();
				parmClass.addElement(crop);
				sqlString = buildSqlStatement(environment, requestType, parmClass);
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
						try
						{
							DropDownDual oneVariety = loadFieldsDropDownDual(requestType, rs);
							returnValue.addElement(oneVariety);								
							
						} catch (Exception e) {
							throwError.append(" error occured loading fields from an sql statement. " + e);
						}
					}
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem retrieving data for crop varieties. " + e) ;
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
			throwError.append("ServiceAttribute.");
			throwError.append("dropDownCropVariety(requestType:");
			throwError.append(requestType + ")");
			throw new Exception(throwError.toString());
		}
		return returnValue;
	}

	/**	 
	 * @return DropDownDual
	 * @throws Exception
	 *
	 *  Change Method:  DEisen 7/20/10 -- Copied and change for one crop variety drop down box.
	 */
	
	public static Vector dropDownCropVariety(String environment, String crop)										               
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		Vector returnValue = new Vector();
		
		try {
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";			
			//  Get Connection to attach to files
			conn = com.treetop.utilities.ConnectionStack7.getConnection();
			
			if (throwError.toString().equals(""))
			{
				try {
					returnValue = dropDownCropVariety(environment, crop, conn);
				} catch (Exception e) {
				throwError.append(" error trying to retrieve information about crop varieties. ");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Retrieving information about crop varieties. " + e);
		// return connection.
		} finally {
			if (conn != null) {
				try {
					com.treetop.utilities.ConnectionStack7.returnConnection(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}	
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceAttribute.");
			throwError.append("DropDownCropVariety.");		
			throw new Exception(throwError.toString());
		}
	
		// return value
		return returnValue;
	}

	/**
	 *   Method Created 2/15/11 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in CommonRequestBean
	 *          		CategoryID1 - Attribute Group (or Blank is valid)
	 * @return Vector - of DropDownSingle 
	 */
	public static Vector dropDownAttributes(CommonRequestBean requestBean)
	{
		Vector ddit = new Vector();
		Connection conn = null;
		try {
			// get a connection to be sent to find methods
			conn = ConnectionStack5.getConnection();
			ddit = dropDownAttributes(requestBean, conn);
		} catch (Exception e)
		{
			// Don't really need to worry about any exceptions
		}
		finally {
			if (conn != null)
			{
				try
				{
				   ConnectionStack5.returnConnection(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		// return value
		return ddit;
	}

	/**
	 * Method Created 2/15/11 TWalton
	 *   // Use to control the information retrieval
	 * @param -- Send in CommonRequestBean
	 *          		CategoryID1 - Attribute Group (or Blank is valid)
	 * @return Vector - of DropDownSingle
	 */
	private static Vector dropDownAttributes(CommonRequestBean requestBean, 
									   	     Connection conn)
	{
		Vector<DropDownSingle> ddit = new Vector<DropDownSingle>();
		ResultSet rs = null;
		Statement listThem = null;
		try
		{
			try {
				   // Get the list of Item Type Values - Along with Descriptions
			   listThem = conn.createStatement();
			   
			   Vector inValues = new Vector();
			   inValues.addElement(requestBean);
			   rs = listThem.executeQuery(buildSqlStatement("PRD", "ddAttributes", inValues));
			 } catch(Exception e)
			 {
			 	System.out.println("ServiceAttribute.dropDownAttributes() Error found when retreiving Attributes" + e);
			   	//throwError.append("Error occurred Retrieving or Executing a sql statement. " + e);
			 }		
			 try
			 {
			 	while (rs.next())
			    {
			 		DropDownSingle dds = loadFieldsDropDownSingle("ddAttributes", rs);
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
	
	public static Vector<AttributeValue> findAttributeValues (String environment, Inventory invt) throws Exception {
		CommonRequestBean crb = new CommonRequestBean(environment);
		crb.setIdLevel1(invt.getItemNumber());
		crb.setIdLevel2(invt.getWarehouse());
		crb.setIdLevel3(invt.getLocation());
		crb.setIdLevel4(invt.getLotNumber());
		
		return findAttributeValues(crb);
	}
	
	
	public static Vector<AttributeValue> findAttributeValues (String environment, Lot lot) throws Exception {
		CommonRequestBean crb = new CommonRequestBean(environment);
		crb.setIdLevel1(lot.getItemNumber());
		crb.setIdLevel2(lot.getWarehouse());
		crb.setIdLevel3(lot.getLocation());
		crb.setIdLevel4(lot.getLotNumber());
		
		return findAttributeValues(crb);
	}
	
	public static Vector<AttributeValue> findAttributeValues (CommonRequestBean crb) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;

		Vector<AttributeValue> returnData = new Vector<AttributeValue>();
		
		try {
			
			conn = ServiceConnection.getConnectionStack5();
			
			returnData = findAttributeValues(crb, conn);
			
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (conn != null) {
				ServiceConnection.returnConnectionStack5(conn);
			}
			if (!throwError.toString().trim().equals("")) {
				throw new Exception(throwError.toString());
			}
		}
		
		return returnData;
	}
	
	private static Vector<AttributeValue> findAttributeValues(CommonRequestBean crb, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		Statement stmt = null;
		Vector<AttributeValue> returnData = new Vector<AttributeValue>();
		
		try {
			String requestType = "loadAttributeValues";
			Vector requestClass = new Vector();
			requestClass.addElement(crb);
			String sql = buildSqlStatement(crb.getEnvironment(), requestType, requestClass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				//get the attribute value
				AttributeValue value = loadFieldsAttributeValue(requestType, rs);
				
				//get the attribute options for alpha values
				if (value.getFieldType().equals("alpha")) {
					Vector inValues = new Vector();
					inValues.addElement(value.getAttributeModel());
					inValues.addElement(value.getAttribute());
					Vector options = dropDownAttributeValues(inValues, conn);
					
					//convert drop down singles to HtmlOptions and set in Attribute object
					value.setAttributeOptions(HtmlOption.convertDropDownSingleVector(options));
				}
				//get the attribute text value
				if (value.getFieldType().equals("text")) {
					
					if (!value.getAttribute().equals("LOT COMMENTS")) {
					
						String textID = value.getValue();
						if (!textID.trim().equals("")) {
							String textValue = findAttributeTextValue(crb.getEnvironment(), textID, conn);
							value.setValue(textValue);
						}
						
					}
					
					
				}
				
				returnData.addElement(value);
			}
					
			
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (!throwError.toString().trim().equals("")) {
				throw new Exception(throwError.toString());
			}
		}
		
		return returnData;
	}
	
	
	/**
	 * Update attributes
	 * @param requestBean
	 * @throws Exception
	 */
	public static BeanInventory updateAttributes(UpdAttribute requestBean) throws Exception {
		StringBuffer throwError = new StringBuffer();
		BeanInventory returnBean = new BeanInventory();
		try {
			
			//test to see if the attribute has changed
			Vector allAttributes = requestBean.getAttributeDetails();
			Vector<UpdAttributeDetail> attributes = new Vector<UpdAttributeDetail>();
			
			Vector<String> holdCommentReturnValues = new Vector<String>();
			
			for (int i=0; !allAttributes.isEmpty() && i<allAttributes.size(); i++) {
				UpdAttributeDetail uad = (UpdAttributeDetail) allAttributes.elementAt(i);

				String fieldType = uad.getFieldType();
				
				if (fieldType.equals("numeric")) {
					//compare numeric values
					BigDecimal from = null;
					BigDecimal to = null;
					try {
						from = new BigDecimal(uad.getValueFrom());
					} catch (Exception e) {}
					try {
						to = new BigDecimal(uad.getValueTo());
					} catch (Exception e) {}
					
					if (from != null && to != null) {
						if (from.compareTo(to) != 0) {
							attributes.addElement(uad);
						}
					}
					//end numeric comparison
				} else {
					//compare string values
					if (!uad.getValueFrom().equals(uad.getValueTo())) {
						attributes.addElement(uad);
					}
				}//end string comparison
					
					
				
			}//end for loop
			
			//create vector of API objects
			Vector<ATS101MISetAttrValue> apis = new Vector<ATS101MISetAttrValue>();
			Vector<ATS101MIaddItmLotAttrTx> holdComments = new Vector<ATS101MIaddItmLotAttrTx>();
			
			Vector<UpdAttributeDetail> attributeNumbers = requestBean.getAttributeNumbers();
			String environment = requestBean.getEnvironment();
			for (int i=0; !attributes.isEmpty() && i<attributes.size(); i++) {
				UpdAttributeDetail attribute = attributes.elementAt(i);
				
				for (int j=0; !attributeNumbers.isEmpty() && j<attributeNumbers.size(); j++) {
					UpdAttributeDetail attributeNumber = attributeNumbers.elementAt(j);
					
					
					if (attribute.getAttributeID().equals("LOT COMMENTS")) {
						
						//TODO  need to finish update API

						ATS101MIaddItmLotAttrTx api = new ATS101MIaddItmLotAttrTx();
						api.setSentFromProgram("Update Inventory Status");
						api.setEnvironment(requestBean.getEnvironment());
						
						api.setUserProfile(requestBean.getUserProfile());
						
						api.setCompany("100");
						api.setAttributeID("LOT COMMENTS");
						api.setItemNumber(attributeNumber.getItemNumber());
						api.setLotNumber(attributeNumber.getLotNumber());
						api.setAttributeNumber(attributeNumber.getAttributeNumber());
						
						
						String holdComment = attribute.getValueTo();
						if (holdComment.length() > 60) {
							holdComment = holdComment.substring(0,60);
						}
						api.setAdditionalTextLine(holdComment);
						
						holdComments.addElement(api);

						
					} else {
					
					
						ATS101MISetAttrValue api = new ATS101MISetAttrValue(attribute, attributeNumber.getAttributeNumber());
						api.setEnvironment(environment);
						api.setSentFromProgram("MassUpdateLotAttributes");
						apis.addElement(api);
					
					}
					
					
					
				}//end inner loop
			}//end outer loop
			
			updateAttributes(apis, requestBean);
			updateLotComments(holdComments, requestBean);
			
		} catch (Exception e) {
			throwError.append(e.toString());
		} finally {
			if (!throwError.toString().trim().equals("")) {
				throw new Exception(throwError.toString());
			}
		}
		return returnBean;
	}
	
	private static void updateAttributes(Vector<ATS101MISetAttrValue> apis, UpdAttribute requestBean) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector<String> responses = new Vector<String>();
		StringBuffer errors = new StringBuffer();
		try {			
			
			responses = ATS101MI.setAttrValue(apis, requestBean.getAuthorization());
			
			for (String response : responses) {
				if (response.contains("NOK")) {
					errors.append(response + "<br>");
				}
			}
			requestBean.setErrorMessage(requestBean.getErrorMessage() + errors.toString());
			
						
		} catch (Exception e) {
			throwError.append(e.toString());
		} finally {
			if (!throwError.toString().trim().equals("")) {
				throw new Exception(throwError.toString());
			}
		}
	
	}
	
	private static void updateLotComments(Vector<ATS101MIaddItmLotAttrTx> apis, UpdAttribute requestBean) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector<String> responses = new Vector<String>();
		StringBuffer errors = new StringBuffer();
		try {
			
			
			for (ATS101MIaddItmLotAttrTx api : apis) {
				String response = ATS101MI.addItmLotAttrTx(api, requestBean.getAuthorization());
				responses.addElement(response);
			}
			
			for (String response : responses) {
				if (response.contains("NOK")) {
					errors.append(response + "<br>");
				}
			}
			requestBean.setErrorMessage(requestBean.getErrorMessage() + errors.toString());
		
		} catch (Exception e) {
			throwError.append(e.toString());
		} finally {
			if (!throwError.toString().trim().equals("")) {
				throw new Exception(throwError.toString());
			}
		}

	}

	public static void addAttribute(Vector<ATS101MIAddAttr> apis, UpdAttribute requestBean) 
		throws Exception {
			
			StringBuffer throwError = new StringBuffer();
			String response = new String();
			StringBuffer errors = new StringBuffer();
			
			try {
				
				if (apis.isEmpty() == false)
				{			
					for (int y = 0; apis.size() > y; y++)
					{
						ATS101MIAddAttr attribute = (ATS101MIAddAttr) apis.elementAt(y);					
						response = ATS101MI.addAttr(attribute, requestBean.getAuthorization());
						if (response.contains("NOK"))
							errors.append(response + "<br>");					
					}			
				
				}
				
				requestBean.setErrorMessage(requestBean.getErrorMessage() + errors.toString());			
							
			} catch (Exception e) {
				throwError.append(e.toString());
			} finally {
				if (!throwError.toString().trim().equals("")) {
					throw new Exception(throwError.toString());
				}
			}
		
		}
	
}

