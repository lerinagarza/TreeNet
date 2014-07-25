/*
 * Created on January 18, 2008
 *
 */
package com.treetop.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;
import com.treetop.businessobjectapplications.*;
import com.treetop.app.promotion.*;

/**
 * @author twalto
 *
 * Services class to obtain and return data 
 * to business objects.
 * Lot Services Object.
 */
public class ServicePromotion extends BaseService{

	public static final String dblib   = "DBPRD";
	public static final String library = "M3DJDPRD";
	
//	private static AS400 system = null; 
//	private static ProgramCall pgm = null;
	
	/**
	 *  Construction of the Base inforamtion
	 */
	public ServicePromotion() {
		super();
	}
	
	/**
	 * Return a Bean business object. Use the incoming
	 * InqPromotion object for the selection.
	 * @param InqPromotion bus object.
	 * @return BeanPromotion business object.
	 * @throws Exception
	 */
	public static BeanPromotion buildPromotionDetail(InqPromotion inValues)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		BeanPromotion bp = new BeanPromotion();
		try {
			// execute find method.
			bp = findPromotionDetail(inValues);
			
		} catch (Exception e)
		{
			throwError.append(e);
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServicePromotion.");
			throwError.append("buildPromotionDetail(");
			throwError.append("InqPromotion). ");
			throw new Exception(throwError.toString());
		}

		// return value
		return bp;
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
		
		// qualify file fields for use.
		StringBuffer fieldList = new StringBuffer();
		
		try { 
			if (inRequestType.equals("promoDetail"))
			{
				// cast the incoming parameter class.
				InqPromotion fromVb = (InqPromotion) requestClass.elementAt(0);
				
				// build the sql statement.
				fieldList.append("SELECT "); 
				// PAPEORDR - Promotion Work File
				fieldList.append("PARECD, PACONO, PADIVI, PAORNO, PAORDT, ");
				fieldList.append("PAITNO, PASAPR, PAORQT, PADIP1, PADIA1, ");
				fieldList.append("PACMP1, PATEDS, PAFVDT, PALVDT, PAPNBR, ");
				fieldList.append("PAPKEY, PAPNTO, PATYPE, PAPTYP, PAPDSC, ");
				fieldList.append("PAPYMT, PACODE, PACDDS, PAMPAY, PAMPDS, ");
				fieldList.append("PAMERC, PAMRDS, PAFUND, PARATE, PADOLL, ");
				fieldList.append("PAFEDT, PAFXDT, ");
				sqlString.append("FROM " + dblib + ".PAPEORDR ");
				// OOHEAD -- Customer Order Header
				fieldList.append("OAORNO, OACUOR, OACUNO, OAORTP, OAORDT, ");
				sqlString.append("INNER JOIN " + library + ".OOHEAD ");
				sqlString.append("   ON OAORNO = PAORNO ");
				// OCUSMA -- Customer Master
				fieldList.append("OKCUNM, OKCFC3, OKFRE1 ");
				sqlString.append("LEFT OUTER JOIN " + library + ".OCUSMA ");
				sqlString.append("   ON OKCUNO = OACUNO ");
				// DSVOPRIC -- View of Orders with invalid price.
				fieldList.append(", ifnull(\"Order No\",'') as badPrice ");
				sqlString.append("LEFT OUTER JOIN " + dblib + ".DSVOPRIC ");
				sqlString.append("ON OAORNO = \"Order No\" ");
				sqlString.append("AND PAITNO = \"Item\" ");
				   
				// add the WHERE Information	
				sqlString.append(" WHERE PACONO = " + fromVb.getInqCompany());
				sqlString.append("   AND PAORNO = '" + editLengthSalesOrder(fromVb.getInqSalesOrder().trim())+ "' ");	
				if (fromVb.getInqShowAll().trim().equals(""))
					sqlString.append(" AND PARECD <> '5S' ");
				// orderby
					sqlString.append(" ORDER BY PACONO, PAORNO, PAITNO, PARECD, PAFVDT, PALVDT ");
				
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement");
				throwError.append(" for request type " + inRequestType + ". ");
		}
		
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServicePromotion.");
			throwError.append("buildSqlStatement(String, Vector)");
			throw new Exception(throwError.toString());
		}

		return fieldList.toString() + sqlString.toString();
	}
	
	
	/**
	 * Return a Vector of PromotionDetail business objects. 
	 * Use the incoming InqPromotion business object
	 * for selection criteria.
	 * @param InqPromotion business object.
	 * @return Vector (PromotionDetail business objects).
	 */
private static BeanPromotion findPromotionDetail(InqPromotion inValues)						
	throws Exception 
{
	BeanPromotion bp = new BeanPromotion();
		Connection conn = null;
		StringBuffer throwError = new StringBuffer();
//		 Vector of PromotionDetail Records
		Vector rtnVector = new Vector();
		PreparedStatement findThem = null;
		ResultSet rs = null;
		
	try
	{
	 if (!inValues.getInqCompany().equals("") && !inValues.getInqSalesOrder().equals(""))
	 {	
		String sqlString = null;
		
		
		
		// verify base class initialization.
		ServiceLot sv = new ServiceLot();
		// Call Program to Build the Work File that the SQL statement will access.
		try {
			ServicePromotion thisTime = new ServicePromotion();
			Vector callParms = new Vector();
			callParms.addElement("PRD");
			callParms.addElement(inValues.getInqCompany());
			callParms.addElement(editLengthSalesOrder(inValues.getInqSalesOrder()));
			thisTime.callRPGLoadWorkFile(callParms);
		} catch(Exception e) {
			throwError.append("Error at sql request." + e);
		}		
		// get the sql statement.
		try {
			Vector parmClass = new Vector();
			parmClass.addElement(inValues);
			sqlString = buildSqlStatement("promoDetail", parmClass);
		} catch(Exception e) {
			throwError.append("Error at sql request." + e);
		}
		
		// get a connection. execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
				conn = ServiceConnection.getConnectionStack10();
				findThem = conn.prepareStatement(sqlString);
				rs = findThem.executeQuery();
			} catch(Exception e)
			{
				throwError.append(" error occured executing a sql statement. " + e);
			}
		}
		try
		{
			if (throwError.toString().equals(""))
			{
				int z = 0;
				while (rs.next())
				{
					if (z == 0)
					{
						z++;
						bp.setSoClass(ServiceSalesOrder.loadFieldsSalesOrderHeader("loadHeader", rs));
					}
					// load LotRawFruitPayment class data from result set.
					PromotionDetail pd = new PromotionDetail();
					pd = loadFields("", rs);
					rtnVector.addElement(pd);
				}
				bp.setListPromotionDetail(rtnVector);
			}
		} catch(Exception e)
		{
			throwError.append(" error occured while Building Vector from sql statement. " + e);
		} 
	 }

	} catch(Exception e)
	{
		throwError.append(" error occured while Building Vector from sql statement. " + e);
		// return connection.
	} finally {
		if (conn != null)
		{
			try {
				 ServiceConnection.returnConnectionStack10(conn);
			 
			 if (rs != null)
				rs.close();
			 
			 if (findThem != null)
				findThem.close();
			 
			} catch(Exception el){
				el.printStackTrace();
			}
		}
	}	
	// return data.	
	if (!throwError.toString().trim().equals("")) 
	{
		throwError.append("Error at com.treetop.services.");
		throwError.append("ServicePromotion.");
		throwError.append("findPromotionDetail(");
		throwError.append("InqPromotion). ");
		throw new Exception(throwError.toString());
	}
		
		return bp;
	}
	
	
	/**
	 * Load class fields from result set.
	 */
	
	private static PromotionDetail loadFields(String loadType,
											  ResultSet rs)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		PromotionDetail buildRecord = new PromotionDetail();

		// Not Currently using loadType
		try
		{
			buildRecord.setRecordID(rs.getString("PARECD").trim());
			buildRecord.setCompany(rs.getString("PACONO").trim());
			buildRecord.setDivision(rs.getString("PADIVI").trim());
			buildRecord.setOrderNumber(rs.getString("PAORNO").trim());
			buildRecord.setOrderDate(rs.getString("PAORDT").trim());
			buildRecord.setItemNumber(rs.getString("PAITNO").trim());
			buildRecord.setItemPrice(rs.getString("PASAPR").trim());
			buildRecord.setOrderQuantity(rs.getString("PAORQT").trim());
			buildRecord.setOrderDiscountRate(rs.getString("PADIP1").trim());
			buildRecord.setOrderDiscountAmount(rs.getString("PADIA1").trim());
			buildRecord.setOrderDiscountIdentity(rs.getString("PACMP1").trim());
			buildRecord.setItemDescription(rs.getString("PATEDS").trim());
			buildRecord.setPromotionDateFrom(rs.getString("PAFVDT").trim());
			buildRecord.setPromotionDateTo(rs.getString("PALVDT").trim());
			buildRecord.setPromotionNumber(rs.getString("PAPNBR").trim());
			buildRecord.setPlanKey(rs.getString("PAPKEY").trim());
			buildRecord.setPlanToNumber(rs.getString("PAPNTO").trim());
			buildRecord.setTypeOfPromotion(rs.getString("PATYPE").trim());
			buildRecord.setPlanType(rs.getString("PAPTYP").trim());
			buildRecord.setPlanTypeDescription(rs.getString("PAPDSC").trim());
			buildRecord.setTypeOfPayment(rs.getString("PAPYMT").trim());
			buildRecord.setAllowanceCode(rs.getString("PACODE").trim());
			buildRecord.setAllowanceCodeDescription(rs.getString("PACDDS").trim());
			buildRecord.setMethodOfPayment(rs.getString("PAMPAY").trim());
			buildRecord.setMethodOfPaymentDescription(rs.getString("PAMPDS").trim());
			buildRecord.setMerchandisingCode(rs.getString("PAMERC").trim());
			buildRecord.setMerchandisingDescription(rs.getString("PAMRDS").trim());
			buildRecord.setTypeOfFund(rs.getString("PAFUND").trim());
			buildRecord.setRatePerUnit(rs.getString("PARATE").trim());
			buildRecord.setLumpSumAmount(rs.getString("PADOLL").trim());
			buildRecord.setFiscalEffectiveDate(rs.getString("PAFEDT").trim());
			buildRecord.setFiscalExpirationDate(rs.getString("PAFXDT").trim());
			if(rs.getString("BADPRICE").trim().equals("")){
				buildRecord.setInvaildPrice("");
			}
			else {
					buildRecord.setInvaildPrice("1");
			}

		} catch(Exception e)
		{
			throwError.append(" Problem loading the class PromotionDetail ");
			throwError.append("from the result set. " + e) ;
		}
		// return data.
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServicePromotion.");
			throwError.append("loadFields(loadType:");
			throwError.append(loadType + ", rs). ");
			throw new Exception(throwError.toString());
		}
		return buildRecord;
	}
	
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		// Test all that you can.
		String startHere = "y";
		
		//test invalid price return.
		if("x" == "x")
		{
			BeanPromotion bp = new BeanPromotion();
			InqPromotion ip = new InqPromotion();
			ip.setInqCompany("100");
			ip.setInqSalesOrder("0000206987");
			
			try {
				bp = buildPromotionDetail(ip);
			} catch (Exception e) {
				String checkIt = "";
			}
		}
	}

	/**
	 * Call's RPG Program (PAC451O) - written by David Eisenheim 
	 *    Program builds the Work File
	 *    with Promotion Information for a specific Sales Order
	 * 
	 * 		Send RPG Program:
	 * 			Environment	- 0 Element of Vector
	 * 			Company		- 1 Element of Vector
	 * 			OrderNumber	- 2 Element of Vector
	 */
	
	private void callRPGLoadWorkFile(Vector inValues)
			throws Exception
	{
		AS400 system = null; 
		ProgramCall pgm = null;
		try
		{
		// Create an AS400 OBJECT - on the m3 machine :
			if (system == null)
			{
			   // 10/28/11 TWalton - Change to use ServiceConnection instead of base IP address	
		      //system = new AS400("10.6.100.3","MSRVADM","fsy0u6");
			  system = ServiceConnection.getAS400();
		      pgm = new ProgramCall(system);
			}
		// Display information for Testing	
		//	System.out.println(inValues.elementAt(0));
		//	System.out.println(inValues.elementAt(1));
		//	System.out.println(inValues.elementAt(2));
	
			ProgramParameter[] parmList = new ProgramParameter[3];
			
			// Initialize parameters.
			// create a AS400text object -- Define Length
			AS400Text environment = new AS400Text(3, system);
			AS400Text company     = new AS400Text(3, system);
			AS400Text salesOrder  = new AS400Text(10, system);
				
			// create a byte array to be used as a parameter and
			// load it with the AS400Text object with the incoming
			// string value.
			   // Hard Code Environment for now
			byte[] parmEnvironment = environment.toBytes(inValues.elementAt(0));
			byte[] parmCompany     = company.toBytes(inValues.elementAt(1));
			byte[] parmSalesOrder  = salesOrder.toBytes(inValues.elementAt(2));						
							
			// Set Parameters into the Parm List
			parmList[0]  = new ProgramParameter(parmEnvironment, 3);
			parmList[1]  = new ProgramParameter(parmCompany, 3);
			parmList[2]  = new ProgramParameter(parmSalesOrder, 10);
    //		 Define the Returned Parms with just length
			
			// Call the program.
			pgm = new ProgramCall(system,"/QSYS.LIB/MOVEX.LIB/PAC451O.PGM", parmList);			

			if (pgm.run() != true) {
				System.out.println(" Error occured while running RPG Program PAC451O. ");
				System.out.println(" Program call failed.  ");
			}
		}
		catch(Exception e)
		{
			System.out.println("Problem when building Work File: " + e);
		}	
		finally{
			try
			{
				if (system != null)
				{
					// 10/28/11 TWalton change to use ServiceConnection 
				   //system.disconnectAllServices();
					ServiceConnection.returnAS400(system);
				}
			}
			catch(Exception e)
			{}
		}
	
	}	
	/**
	 * Add "0"'s onto the Front of the Sales Order -- The length should be 10
	 */
	
	private static String editLengthSalesOrder(String salesOrder)
	{
		if (salesOrder.trim().length() < 10)
		{
			for (int z = salesOrder.trim().length(); z < 10; z++)
			{
				salesOrder = "0" + salesOrder;
			}
			//System.out.println("salesOrder: " + salesOrder);
		}
		return salesOrder;
	}

	/*
 	 * Retrieve and Display a New Promo
	 *    Calling an RPG program PAR343 to do this
	 */
	public InqPromotion callRPGPromotionNumber(InqPromotion ip) {
		
		AS400 system = null; 
		ProgramCall pgm = null;
		try
		{
		// Create an AS400 OBJECT - on the m3 machine :
			if (system == null)
			{
				//10/28/11 TWalton change to use ServiceConnection instead of base IP address
		      //system = new AS400("10.6.100.3","MSRVADM","fsy0u6");
			  system = ServiceConnection.getAS400();
		      pgm = new ProgramCall(system);
			}		   
		// Display information for Testing	
		//	System.out.println(ip.getInqCompany());
		//	System.out.println(ip.getInqDivision());
		//	System.out.println(ip.getInqPromotion());
		//	System.out.println(ip.getInqWarningMessage());
	
			ProgramParameter[] parmList = new ProgramParameter[5];
			
			// Initialize parameters.
			// create a AS400text object -- Define Length
			AS400Text environment = new AS400Text(3, system);
			AS400Text company     = new AS400Text(3, system);
			AS400Text division    = new AS400Text(3, system);
		//	AS400Text promotion   = new AS400Text(4, system); Not needed because it is a Returning Parm
		//	AS400Text message     = new AS400Text(40, system);	Not needed because it is a Returning Parm		
				
			// create a byte array to be used as a parameter and
			// load it with the AS400Text object with the incoming
			// string value.
			   // Hard Code Environment for now
			byte[] parmEnvironment = environment.toBytes("PRD");
			byte[] parmCompany     = company.toBytes(ip.getInqCompany().trim());
			byte[] parmDivision    = division.toBytes(ip.getInqDivision().trim());						
		//	byte[] parmPromotion   = promotion.toBytes(" "); Not needed because it is a Returning Parm
		//	byte[] parmMessage     = message.toBytes(" "); Not Needed because it is a Returning Parm
							
			// Set Parameters into the Parm List
			parmList[0]  = new ProgramParameter(parmEnvironment, 3);
			parmList[1]  = new ProgramParameter(parmCompany, 3);
			parmList[2]  = new ProgramParameter(parmDivision, 3);
	//		 Define the Returned Parms with just length
			parmList[3]  = new ProgramParameter(4); 
			parmList[4]  = new ProgramParameter(40);
			
			// Call the program.
			pgm = new ProgramCall(system,"/QSYS.LIB/MOVEX.LIB/PAC434.PGM", parmList);			
	
			if (pgm.run() != true) {
				System.out.println(" Error occured while running RPG Program PAC434 - Which Runs PAR434. ");
				System.out.println(" Program call failed.  ");
			}
			// to be used to retrieve the Promotion Number
			AS400Text length4  = new AS400Text(4, system);
			// to be used to retrieve ANY warning messages
			AS400Text length40 = new AS400Text(40, system);
					
			// Determine Return Values
	// Set them into the InqPromotion Class for Display Purposes
		// Retrieve from the Parm List and set the Promotion Number into the Class	
			ip.setInqPromotion((String) length4.toObject(parmList[3].getOutputData(),0));
		// Retrieve from the Parm List and set the Warning Message into the Class	
			ip.setInqWarningMessage((String) length40.toObject(parmList[4].getOutputData(),0));
		}
		catch(Exception e)
		{
			System.out.println("Problem when retreiving Promotion Number: " + e);
		}
		finally{
			try
			{
				// 10/28/11 Change to use ServiceConnection
				//system.disconnectAllServices();
				ServiceConnection.returnAS400(system);
			}
			catch(Exception e)
			{}
		}
		return ip;
	}
}
