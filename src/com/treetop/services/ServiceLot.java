/*
 * Created on Feb 22, 2006
 *
 */
package com.treetop.services;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import com.treetop.CheckDate;
import com.treetop.businessobjects.*;
import com.treetop.app.resource.InqReserveNumbers;
import com.treetop.app.inventory.UpdInventory;
import com.treetop.utilities.ConnectionStack;

import com.treetop.viewbeans.*;

/**
 * @author thaile
 *
 * Services class to obtain and return data 
 * to business objects.
 * Lot Services Object.
 */
public class ServiceLot extends BaseService{

	// 9/18/09 - TWalton Changed the Library to only be:
	// 2012-01-12	jhagle  changed connections to use ServiceConnection
	//   M3DJD, the Environment is being sent into anywhere the 
	//  Library is used, the Environment will be Tacked onto the 
	//   End of the Library Values
	//public static final String library = "M3DJDPRD.";
	//public static final String library = "M3DJDTST.";
	  public static final String library = "M3DJD";
	//public static final String ttlib = "DBPRD.";
	//public static final String ttlib = "DBTST.";
	  public static final String ttlib = "DB";

	/**
	 * 
	 */
	public ServiceLot() {
		super();
	}
	
	
	/**
	 * Load class fields from result set.
	 * Added Method: 2/13/08 TWalton
	 */
	
	public static Lot loadFieldsM3Lot(ResultSet rs, 
									  String type)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Lot lotValues = new Lot();

		try{ 
			if (type.equals("coaLot"))
			{
				try
				{
//					 Lot Master -- MILOMA -- 
					 lotValues.setLotNumber(rs.getString("LMBANO"));
					 lotValues.setItemNumber(rs.getString("LMITNO").trim());
					 lotValues.setManufactureDate(rs.getString("LMMFDT"));
					 lotValues.setExpirationDate(rs.getString("LMEXPI"));
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}		
				try
				{
//					 Transaction Master -- MITTRA -- 
					 lotValues.setQuantity(rs.getString("MTTRQT").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}	
				try
				{
//					 Item Master -- MITMAS -- 
					 lotValues.setItemDescription(rs.getString("MMITDS").trim());
					 lotValues.setBasicUnitOfMeasure(rs.getString("MMUNMS").trim());
					 lotValues.setAttributeModel(rs.getString("MMATMO").trim());
					 lotValues.setItemType(rs.getString("MMITTY").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}		
				try
				{
//					 Attribute Master Header -- MAMOHE -- 
					if (rs.getString("ADTX15") != null)
					{
					   lotValues.setAttributeModelName(rs.getString("ADTX15").trim());
					   lotValues.setAttributeModelDescription(rs.getString("ADTX40").trim());
					}
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}		
			}
			
			if (type.equals("attributeValuesLot")) {
				lotValues.setItemNumber(rs.getString("MLITNO").trim());
				lotValues.setItemDescription(rs.getString("MMITDS").trim());
				lotValues.setLotNumber(rs.getString("MLBANO").trim());
								
			}
			
			if (type.equals("acidList"))
			{
				try
				{
//					 Balance ID -- MITLOC -- 
					 lotValues.setFacility(rs.getString("MLFACI").trim());
					 lotValues.setWarehouse(rs.getString("MLWHLO").trim());
					 lotValues.setItemNumber(rs.getString("MLITNO").trim());
					 lotValues.setLotNumber(rs.getString("MLBANO"));
					 lotValues.setLotStatus(rs.getString("MLSTAS").trim());
					 lotValues.setLocation(rs.getString("MLWHSL").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}		
				try
				{
//					 Item Master -- MITMAS -- 
					 lotValues.setItemDescription(rs.getString("MMITDS").trim());
					 lotValues.setItemGroup(rs.getString("MMITGR").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}	
				try
				{
//					 Balance ID -- MITLOC -- 
					 lotValues.setQuantity(rs.getString(8));
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}			
				try
				{
//					 Warehouse Master --  -- 
					 lotValues.setWarehouseName(rs.getString("MWWHNM").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}
				try
				{
//					 Facility Master -- CFACIL -- 
					 lotValues.setFacilityName(rs.getString("CFFACN").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}				
				try
				{
//					 Brix Table -- BRIXFILE --
					if (rs.getString("GNBBRX") != null)
					{	
					  lotValues.setBrix(rs.getString("GNBBRX").trim());
					  lotValues.setBrixConversion(rs.getString("GNBFSG").trim());
					}
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}		
			}
			if (type.equals("rfEntryLot"))
			{
				try
				{
					// Raw Fruit Lot Entry Information
					lotValues.setFacility(rs.getString("G4FACI").trim());
					lotValues.setWarehouse(rs.getString("G4WHSE").trim());
					lotValues.setLotNumber(rs.getString("G4LOT").trim());
					lotValues.setLocation(rs.getString("G4LOC").trim());
					lotValues.setItemNumber(rs.getString("G4ITEM").trim());
					lotValues.setBrix(rs.getString("G4BRIX").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}				
				try
				{
//					 Item Master -- MITMAS -- 
					 lotValues.setItemDescription(rs.getString("MMITDS").trim());
					 lotValues.setAttributeModel(rs.getString("MMATMO").trim());
					 lotValues.setOrganicConventional(rs.getString("MMEVGR").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}			
				try
				{
//					 Warehouse Master --  -- 
					 lotValues.setWarehouseName(rs.getString("MWWHNM").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}
				try
				{
//					 Facility Master -- CFACIL -- 
					 lotValues.setFacilityName(rs.getString("CFFACN").trim());
				}
				catch(Exception e)
				{
					// Problem Caught when loading the Lot Information
					// System.out.println("Error Occurred ServiceLot.loadFieldsM3Lot() : " + e);
				}	
				
			}
			
		} catch(Exception e)
		{
			throwError.append(" Problem loading the class ");
			throwError.append("from the result set. " + e) ;
		}
		
		// return data.
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceLot.");
			throwError.append("loadFieldsM3Lot(rs, type:");
			throwError.append(type + "). ");
			throw new Exception(throwError.toString());
		}
		return lotValues;
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
		//*** TEST "buildListLotPayment(String)".
			if ("x".equals("x"))
			{

				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	/**
	 * Add the Payment Crop Year Range "WHERE" clause.
	 *
	 */
	private static String sqlWherePaymentCropYearRange(String fmValue,
										 			    String toValue,
													    String whereClause)
	throws Exception
	{
		String  sqlWhere = "";
		StringBuffer throwError = new StringBuffer();
		
		// set from value to null if its empty.
		if (fmValue != null &&
			fmValue.trim().equals(""))
			fmValue = null;
		
		// set to value to null if its empry.
		if (toValue != null &&
			toValue.trim().equals(""))
			toValue = null;
		
		try {			
	
			if (fmValue != null || 
				toValue != null )
			{
				// add "AND" to sql statement if not empty.		
				if (whereClause.length() > 0)
					sqlWhere = sqlWhere + "AND ";
				
				// only from value entered.
				if (fmValue != null &&
					toValue == null )
					sqlWhere = sqlWhere + "(tkr.RGRDCRY >= " +
							   fmValue +
							   ") ";
				
				// both from and to values entered.
				if (fmValue != null &&
					toValue != null )
					sqlWhere = sqlWhere + "(tkr.RGRDCRY >= " +
							   fmValue +
							   " AND tkr.RGRDCRY <= " +
							   toValue +
							   ") ";
				
				// only to value entered.
				if (fmValue == null &&
					toValue != null)
					sqlWhere = sqlWhere + "(tkr.RGRDCRY <= " +
							   toValue +
							   ") ";    
			}		 
		}

		catch (Exception e) {
			throwError.append("Exception error at com.treetop.services.");
			throwError.append(" ServiceLot.sqlWherePaymentCropYearRange");
			throwError.append("(String:" + fmValue + ", String:" + toValue);
			throwError.append(", String:" + whereClause + "): " + e);
			throw new Exception(throwError.toString());
		}	
		return sqlWhere; 	 
	}	
	
	
	
	/**
	 * Add the Payment Hauler BOL "WHERE" clause.
	 *
	 */
	private static String sqlWherePaymentHaulerBOL(String inBOL,
												   String whereClause)
		throws Exception
	{
		String  sqlWhere = "";
		StringBuffer throwError = new StringBuffer();
		
		try {			
		
			if ((inBOL != null) && 
				(!inBOL.equals("")))
			{					   		
				if (whereClause.length() > 0)
					sqlWhere = sqlWhere + "AND ";	
			
				sqlWhere = sqlWhere +
							"(upper (tkr.RGRDHBL) = '" + inBOL.toUpperCase().trim() + "') ";
			}		 
		}

		catch (Exception e) {
			throwError.append("error adding to the where clause. " + e);
		}	
	
		if (!throwError.toString().equals(""))
		{
			throwError.append(" - Exception error at com.treetop.services.");
			throwError.append(" ServiceLot.sqlWherePaymentHaulerBOL");
			throwError.append("(bol: " + inBOL);
			throwError.append(" whereClause:" + whereClause + ")");
			throw new Exception(throwError.toString());
		}
		
		return sqlWhere; 	 
	
	}
	
	
	
	/**
	 * Add the Payment Carrier Number Range "WHERE" clause.
	 *
	 */
	private static String sqlWherePaymentCarrierNumberRange(String fmValue,
										 			    String toValue,
													    String whereClause)
	throws Exception
	{
		String  sqlWhere = "";
		StringBuffer throwError = new StringBuffer();
		
		// set from value to null if its empty.
		if (fmValue != null &&
			fmValue.trim().equals(""))
			fmValue = null;
		
		// set to value to null if its empry.
		if (toValue != null &&
			toValue.trim().equals(""))
			toValue = null;
		
		try {			
	
			if (fmValue != null || 
				toValue != null )
			{
				// add "AND" to sql statement if not empty.		
				if (whereClause.length() > 0)
					sqlWhere = sqlWhere + "AND ";
				
				// only from value entered.
				if (fmValue != null &&
					toValue == null )
					sqlWhere = sqlWhere + "(car.ARMNBR >= " +
							   fmValue +
							   ") ";
				
				// both from and to values entered.
				if (fmValue != null &&
					toValue != null )
					sqlWhere = sqlWhere + "(car.ARMNBR >= " +
							   fmValue +
							   " AND car.ARMNBR <= " +
							   toValue +
							   ") ";
				
				// only to value entered.
				if (fmValue == null &&
					toValue != null)
					sqlWhere = sqlWhere + "(car.ARMNBR <= " +
							   toValue +
							   ") ";    
			}		 
		}

		catch (Exception e) {
			throwError.append("Exception error at com.treetop.services.");
			throwError.append(" ServiceLot.sqlWherePaymentCarrierNumberRange");
			throwError.append("(String:" + fmValue + ", String:" + toValue);
			throwError.append(", String:" + whereClause + "): " + e);
			throw new Exception(throwError.toString());
		}	
		return sqlWhere; 	 
	}	
	
	
	
	/**
	 * Add the Payment Carrier Bill of Lading Range "WHERE" clause.
	 *
	 */
	private static String sqlWherePaymentCarrierBLRange(String fmValue,
										 			    String toValue,
													    String whereClause)
	throws Exception
	{
		String  sqlWhere = "";
		StringBuffer throwError = new StringBuffer();
		
		// set from value to null if its empty.
		if (fmValue != null &&
			fmValue.trim().equals(""))
			fmValue = null;
		
		// set to value to null if its empry.
		if (toValue != null &&
			toValue.trim().equals(""))
			toValue = null;
		
		try {			
	
			if (fmValue != null || 
				toValue != null )
			{
				// add "AND" to sql statement if not empty.		
				if (whereClause.length() > 0)
					sqlWhere = sqlWhere + "AND ";
				
				// only from value entered.
				if (fmValue != null &&
					toValue == null )
					sqlWhere = sqlWhere + "(tkr.RGRDHBL >= '" +
							   fmValue +
							   "') ";
				
				// both from and to values entered.
				if (fmValue != null &&
					toValue != null )
					sqlWhere = sqlWhere + "(tkr.RGRDHBL >= '" +
							   fmValue +
							   "' AND tkr.RGRDHBL <= '" +
							   toValue +
							   "') ";
				
				// only to value entered.
				if (fmValue == null &&
					toValue != null)
					sqlWhere = sqlWhere + "(tkr.RGRDHBL <= '" +
							   toValue +
							   "') ";    
			}		 
		}

		catch (Exception e) {
			throwError.append("Exception error at com.treetop.services.");
			throwError.append(" ServiceLot.sqlWherePaymentCarrierBLRange");
			throwError.append("(String:" + fmValue + ", String:" + toValue);
			throwError.append(", String:" + whereClause + "): " + e);
			throw new Exception(throwError.toString());
		}	
		return sqlWhere; 	 
	}
	
	
	/**
	 * Add the Payment Packer Warehouse "WHERE" clause.
	 *
	 */
	private static String sqlWherePaymentPackerWarehouse(String inWhse,
														 String whereClause)
	throws Exception
	{
		String  sqlWhere = "";
		StringBuffer throwError = new StringBuffer();
		
		try {			
		
			if ((inWhse != null) && 
				(!inWhse.equals("")))
			{					   		
				if (whereClause.length() > 0)
					sqlWhere = sqlWhere + "AND ";	
			
				sqlWhere = sqlWhere +
							" tkr.RGRDPCK = " + inWhse.trim() + " ";
			}		 
		}

		catch (Exception e) {
			throwError.append("error adding to the where clause. " + e);
		}	
	
		if (!throwError.toString().equals(""))
		{
			throwError.append(" - Exception error at com.treetop.services.");
			throwError.append(" ServiceLot.sqlWherePaymentPackerWarehouse");
			throwError.append("(whse: " + inWhse);
			throwError.append(" whereClause:" + whereClause + ")");
			throw new Exception(throwError.toString());
		}
		
		return sqlWhere; 	 
	
	}

	
	
	/**
	 * Add the Payment Plant "WHERE" clause.
	 *
	 */
	private static String sqlWherePaymentPlant(String inPlant,
										String whereClause)
							throws Exception
	{
		String  sqlWhere = "";
		StringBuffer throwError = new StringBuffer();
		
		try {			
		
			if ((inPlant != null) && 
				(!inPlant.equals("")))
			{					   		
				if (whereClause.length() > 0)
					sqlWhere = sqlWhere + "AND ";	
			
				sqlWhere = sqlWhere +
							"(upper (tkr.RGRDRWH) = '" + inPlant.toUpperCase().trim() + "') ";
			}		 
		}

		catch (Exception e) {
			throwError.append("error adding to the where clause. " + e);
		}	
	
		if (!throwError.toString().equals(""))
		{
			throwError.append(" - Exception error at com.treetop.services.");
			throwError.append(" ServiceLot.sqlWherePaymentPlant");
			throwError.append("(plant: " + inPlant);
			throwError.append(" whereClause:" + whereClause + ")");
			throw new Exception(throwError.toString());
		}
		
		return sqlWhere; 	 
	
	}
	
	
	
	/**
	 * Add the Payment Year Range "WHERE" clause.
	 *
	 */
	private static String sqlWherePaymentYearRange(String fmValue,
										 			    String toValue,
													    String whereClause)
	throws Exception
	{
		String  sqlWhere = "";
		StringBuffer throwError = new StringBuffer();
		
		// set from value to null if its empty.
		if (fmValue != null &&
			fmValue.trim().equals(""))
			fmValue = null;
		
		// set to value to null if its empry.
		if (toValue != null &&
			toValue.trim().equals(""))
			toValue = null;
		
		try {			
	
			if (fmValue != null || 
				toValue != null )
			{
				// add "AND" to sql statement if not empty.		
				if (whereClause.length() > 0)
					sqlWhere = sqlWhere + "AND ";
				
				// only from value entered.
				if (fmValue != null &&
					toValue == null )
					sqlWhere = sqlWhere + "(tkr.RGRDCPY >= " +
							   fmValue +
							   ") ";
				
				// both from and to values entered.
				if (fmValue != null &&
					toValue != null )
					sqlWhere = sqlWhere + "(tkr.RGRDCPY >= " +
							   fmValue +
							   " AND tkr.RGRDCPY <= " +
							   toValue +
							   ") ";
				
				// only to value entered.
				if (fmValue == null &&
					toValue != null)
					sqlWhere = sqlWhere + "(tkr.RGRDCPY <= " +
							   toValue +
							   ") ";    
			}		 
		}

		catch (Exception e) {
			throwError.append("Exception error at com.treetop.services.");
			throwError.append(" ServiceLot.sqlWherePaymentYearRange");
			throwError.append("(String:" + fmValue + ", String:" + toValue);
			throwError.append(", String:" + whereClause + "): " + e);
			throw new Exception(throwError.toString());
		}	
		return sqlWhere; 	 
	}	

	
	/**
	 * Add the Haul Paid Flag "WHERE" clause.
	 *
	 */
	private static String sqlWhereHaulPaidFlag(String inFlag,
										String whereClause)
							throws Exception
	{
		String  sqlWhere = "";
		StringBuffer throwError = new StringBuffer();
		
		try {			
		
			if ((inFlag != null) && 
				(!inFlag.equals("")))
			{					   		
				if (whereClause.length() > 0)
					sqlWhere = sqlWhere + "AND ";	
			
				if (inFlag.trim().toLowerCase().equals("unpaid"))
					sqlWhere = sqlWhere +
							" (tkr.RGRDFTR = 0 AND (upper (tkr.RGRDHPF) <> 'Y') ) ";
				
				if (inFlag.trim().toLowerCase().equals("pending"))
					sqlWhere = sqlWhere +
							" (tkr.RGRDFTR <> 0 AND (upper (tkr.RGRDHPF) <> 'Y') ) ";
				
				if (inFlag.trim().toLowerCase().equals("paid"))
					sqlWhere = sqlWhere +
							" (upper (tkr.RGRDHPF) = 'Y') ";
			}		 		 
		}

		catch (Exception e) {
			throwError.append("error adding to the where clause. " + e);
		}	
	
		if (!throwError.toString().equals(""))
		{
			throwError.append(" - Exception error at com.treetop.services.");
			throwError.append(" ServiceLot.sqlWhereHaulPaidFlag");
			throwError.append("(flag: " + inFlag);
			throwError.append(" whereClause:" + whereClause + ")");
			throw new Exception(throwError.toString());
		}
		
		return sqlWhere; 	 
	
	}
	
	
	/**
	 * Add the Project Owner "WHERE" clause.
	 *
	 */
	private static String sqlWhereFruitPaidFlag(String inFlag,
										String whereClause)
							throws Exception
	{
		String  sqlWhere = "";
		StringBuffer throwError = new StringBuffer();
		
		try {			
		
			if ((inFlag != null) && 
				(!inFlag.equals("")))
			{					   		
				if (whereClause.length() > 0)
					sqlWhere = sqlWhere + "AND ";	
			
				if (inFlag.trim().toLowerCase().equals("unpaid"))
					sqlWhere = sqlWhere +
							" (tkr.RGRDPTR = 0 AND (upper (tkr.RGRDFPF) <> 'Y') ) ";
				
				if (inFlag.trim().toLowerCase().equals("pending"))
					sqlWhere = sqlWhere +
							" (tkr.RGRDPTR <> 0 AND (upper (tkr.RGRDFPF) <> 'Y') ) ";
				
				if (inFlag.trim().toLowerCase().equals("paid"))
					sqlWhere = sqlWhere +
							" (upper (tkr.RGRDFPF) = 'Y') ";
			}		 
		}

		catch (Exception e) {
			throwError.append("error adding to the where clause. " + e);
		}	
	
		if (!throwError.toString().equals(""))
		{
			throwError.append(" - Exception error at com.treetop.services.");
			throwError.append(" ServiceLot.sqlWhereFruitPaidFlag");
			throwError.append("(flag: " + inFlag);
			throwError.append(" whereClause:" + whereClause + ")");
			throw new Exception(throwError.toString());
		}
		
		return sqlWhere; 	 
	
	}
	
	
	/**
	 * Add the Payment Receiving Date "WHERE" clause.
	 *
	 */
	private static String sqlWherePaymentReceivingDate(String fmDate,
										 			   String toDate,
													   String whereClause)
	throws Exception
	{
		String  sqlWhere = "";
		StringBuffer throwError = new StringBuffer();
		
		// set from date to null if its empty.
		if (fmDate != null &&
			fmDate.trim().equals(""))
			fmDate = null;
		
		// set to date to null if its empry.
		if (toDate != null &&
			toDate.trim().equals(""))
			toDate = null;
		
		try {			
	
			if (fmDate != null || 
				toDate != null )
			{
				Integer fromDate = null;
				Integer tooDate   = null;
			
				if (fmDate != null)
				{
					String[] dates = CheckDate.validateDate(fmDate);
					fromDate = new Integer(dates[4]);
				}
			
				if (toDate != null)
				{
					String[] dates = CheckDate.validateDate(toDate);
					tooDate = new Integer(dates[4]);
				}
				
				if (whereClause.length() > 0)
					sqlWhere = sqlWhere + "AND ";
				
				if (fmDate != null &&
					toDate == null )
					sqlWhere = sqlWhere + "(tkr.RGRDRDT >= " +
							   fromDate +
							   ") ";
				if (fmDate != null &&
					toDate != null )
					sqlWhere = sqlWhere + "(tkr.RGRDRDT >= " +
							   fromDate +
							   " AND tkr.RGRDRDT <= " +
							   tooDate +
							   ") ";
		
				if (fmDate == null &&
					toDate != null)
					sqlWhere = sqlWhere + "(tkr.RGRDRDT <= " +
							   tooDate +
							   ") ";    
			}		 
		}

		catch (Exception e) {
			throwError.append("Exception error at com.treetop.services.");
			throwError.append(" ServiceLot.sqlWherePaymentReceivingDate");
			throwError.append("(String, String, String): " + e);
			throw new Exception(throwError.toString());
		}	
		return sqlWhere; 	 
	}
	
	
	/**
	 * Add the Payment Lot "WHERE" clause.
	 *
	 */
	private static String sqlWherePaymentLot(String inLot,
										String whereClause)
							throws Exception
	{
		String  sqlWhere = "";
		StringBuffer throwError = new StringBuffer();
		
		try {			
		
			if ((inLot != null) && 
				(!inLot.equals("")))
			{					   		
				if (whereClause.length() > 0)
					sqlWhere = sqlWhere + "AND ";
			
				sqlWhere = sqlWhere +
							"(upper (tkr.RGRDLOT) = '" + inLot.toUpperCase().trim() + "') ";
			}		 
		}

		catch (Exception e) {
			throwError.append("error adding to the where clause. " + e);
		}	
	
		if (!throwError.toString().equals(""))
		{
			throwError.append(" - Exception error at com.treetop.services.");
			throwError.append(" ServiceLot.sqlWherePaymentLot");
			throwError.append("(lot: " + inLot);
			throwError.append(" whereClause:" + whereClause + ")");
			throw new Exception(throwError.toString());
		}
		
		return sqlWhere; 	 
	
	}
	
	

	/**
	 * Add the Payment Lot Number Range "WHERE" clause.
	 *
	 */
	private static String sqlWherePaymentLotNumberRange(String fmValue,
										 			    String toValue,
													    String whereClause)
	throws Exception
	{
		String  sqlWhere = "";
		StringBuffer throwError = new StringBuffer();
		
		// set from value to null if its empty.
		if (fmValue != null &&
			fmValue.trim().equals(""))
			fmValue = null;
		
		// set to value to null if its empry.
		if (toValue != null &&
			toValue.trim().equals(""))
			toValue = null;
		
		try {			
	
			if (fmValue != null || 
				toValue != null )
			{
				// add "AND" to sql statement if not empty.		
				if (whereClause.length() > 0)
					sqlWhere = sqlWhere + "AND ";
				
				// only from value entered.
				if (fmValue != null &&
					toValue == null )
					sqlWhere = sqlWhere + "(tkr.RGRDLOT >= '" +
							   fmValue +
							   "') ";
				
				// both from and to values entered.
				if (fmValue != null &&
					toValue != null )
					sqlWhere = sqlWhere + "(tkr.RGRDLOT >= '" +
							   fmValue +
							   "' AND tkr.RGRDLOT <= '" +
							   toValue +
							   "') ";
				
				// only to value entered.
				if (fmValue == null &&
					toValue != null)
					sqlWhere = sqlWhere + "(tkr.RGRDLOT <= '" +
							   toValue +
							   "') ";    
			}		 
		}

		catch (Exception e) {
			throwError.append("Exception error at com.treetop.services.");
			throwError.append(" ServiceLot.sqlWherePaymentLotNumberRange");
			throwError.append("(String:" + fmValue + ", String:" + toValue);
			throwError.append(", String:" + whereClause + "): " + e);
			throw new Exception(throwError.toString());
		}	
		return sqlWhere; 	 
	}


	/**
	 * Add the Variety "WHERE" clause
	 *   ** TWalton 7/21/06 (Copied sqlWherePaymentPlant)
	 */
	private static String sqlWhereVariety(String inVariety,
										           String whereClause)
							throws Exception
	{
		String  sqlWhere = "";
		StringBuffer throwError = new StringBuffer();
		
		try {			
		
			if ((inVariety != null) && 
				(!inVariety.equals("")))
			{					   		
				if (whereClause.length() > 0)
					sqlWhere = sqlWhere + "AND ";	
			
				sqlWhere = sqlWhere +
							"(upper (inv.INIVAR) = '" + inVariety.toUpperCase().trim() + "') ";
			}		 
		}
	
		catch (Exception e) {
			throwError.append("error adding to the where clause. " + e);
		}	
	
		if (!throwError.toString().equals(""))
		{
			throwError.append(" - Exception error at com.treetop.services.");
			throwError.append(" ServiceLot.sqlWhereVariety");
			throwError.append("(variety: " + inVariety);
			throwError.append(" whereClause:" + whereClause + ")");
			throw new Exception(throwError.toString());
		}
		
		return sqlWhere; 	 
	
	}

	/**
	 * Added Method: 1/22/08 - Teri Walton
	 * Use to Validate:
	 * 	 Send in Lot Number 
	 *   Return Message if Lot Number is not found - else Message would be Blank
	 */
	public static String verifyM3LotNumber(String environment, 
										   String lotNumber)
			throws Exception
	{
//		 Changes to Method
		// 2/2/09 TWalton - Change from Connection Pool to dbConnection
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyLotNumber";
			String sqlString = "";
				
			// verify base class initialization.
			ServiceSalesOrder a = new ServiceSalesOrder();
							
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Lot Number
			if (lotNumber == null || lotNumber.trim().equals(""))
				rtnProblem.append(" Lot Number must not be null or empty.");
				
			// get Test Lot Number Info.
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(lotNumber);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnProblem.append(" Problem when building Test for Lot Number: ");
					rtnProblem.append(lotNumber + " PrintScreen this message and send to Information Services. ");
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
	//					 it exists and all is good.
					} else {
						rtnProblem.append(" Lot Number '" + lotNumber + "' ");
						rtnProblem.append("does not exist. ");
					}
						
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Lot Number: ");
					rtnProblem.append(lotNumber + " PrintScreen this message and send to Information Services. ");
				}
			}
			} catch(Exception e)
			{
				throwError.append(" Problem Testing the Lot Number ");
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
				throwError.append("ServiceLot.");
				throwError.append("verifyM3LotNumber(String: Environment, String: LotNumber)");
				throw new Exception(throwError.toString());
			}
			return rtnProblem.toString();
		}


	/**
	 * Added Method: 1/22/08 - Teri Walton
	 * 
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
		
		try { // Verify Sales Order
			if (inRequestType.equals("verifyLotNumber"))
			{
				// cast the incoming parameter class.
				String lotNumber = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT LMBANO ");
				sqlString.append("FROM " + library + environment + ".MILOMA ");
				sqlString.append(" WHERE LMBANO = '" + lotNumber.trim() + "' ");
			}
			if (inRequestType.equals("verifyLotWithItem"))
			{
				// build the sql statement.
				sqlString.append("SELECT LMBANO ");
				sqlString.append("FROM " + library + environment + ".MILOMA ");
				sqlString.append(" WHERE LMBANO = '" + (String) requestClass.elementAt(0) + "' ");
				sqlString.append("  AND LMITNO = '" + (String) requestClass.elementAt(1) + "' ");
			}
			if (inRequestType.equals("verifyRawFruitLotNumber"))
			{
//				 cast the incoming parameter class.
				String lotNumber = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT G4SCALE#, G4SEQ#, G4TSEQ#, G4LOT ");
				sqlString.append("FROM " + ttlib + environment + ".GRPCTICK ");
				sqlString.append(" WHERE G4LOT = '" + lotNumber.trim() + "' ");
			}
			if (inRequestType.equals("updateLots"))
			{
//				 cast the incoming parameter class.
				UpdInventory updLot = (UpdInventory) requestClass.elementAt(0);
				
				sqlString.append("UPDATE " + library + environment + ".MILOMA ");
				sqlString.append(" SET ");
				if (!updLot.getTaggingType().trim().equals(""))
				{
				   sqlString.append(" LMIDET = '" + updLot.getTaggingType() + "' ");
				   if (!updLot.getDisposition().trim().equals(""))
					   sqlString.append(", ");
				}
				if (!updLot.getDisposition().trim().equals(""))
				   sqlString.append(" LMOWNC = '" + updLot.getDisposition() + "' ");
				sqlString.append(" WHERE LMCONO = 100 ");
				if (updLot.getBeanInventory().getByItemVectorOfInventory() != null &&
					updLot.getBeanInventory().getByItemVectorOfInventory().size() > 0)
				{
					StringBuffer lotSection = new StringBuffer();
					for (int x = 0; x < updLot.getBeanInventory().getByItemVectorOfInventory().size(); x++)
					{
					   Inventory i = (Inventory) updLot.getBeanInventory().getByItemVectorOfInventory().elementAt(x);
					   if (!lotSection.toString().trim().equals(""))
					     lotSection.append(" OR ");
					   lotSection.append(" (LMITNO = '" + i.getItemNumber().trim() + "' ");
					   lotSection.append("  AND LMBANO = '" + i.getLotNumber().trim() + "')");
					}
					sqlString.append("AND (" + lotSection.toString().trim() + ")");
				}				
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement"
							 + " for request type " + inRequestType + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceLot.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		return sqlString.toString();
	}
	/**
	 * Load class fields from result set.
	 * 
	 */
	
	private static LotRawFruitPayment loadFields(ResultSet rs, String type)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		LotRawFruitPayment payment = new LotRawFruitPayment();
	
		try{ // sqlStatement("listPayments")
			if (type.equals("lotOnly"))
				payment.setLotNumber(rs.getString("RGRDLOT").trim());
			if (type.equals("listPayments") ||
				 type.equals("listPaymentsEdit"))
			{
				payment.setLotNumber(rs.getString("RGRDLOT").trim());
				payment.setLotType(rs.getString("RGRDTYP").trim());	
				payment.setInvGroupCode1(rs.getString("RGRDGC1").trim());
				payment.setInvGroupCode2(rs.getString("RGRDGC2").trim());
				payment.setInvGroupCode3(rs.getString("RGRDGC3").trim());
				payment.setResource(rs.getString("RGRDRES").trim());
				payment.setSpec("");
				payment.setVariety(rs.getString("RGRDVAR").trim());
				payment.setPlant(rs.getString("RGRDRWH").trim());
				payment.setLocation("");
				payment.setQuantity("");
				payment.setHaulerBillOfLading(rs.getString("RGRDHBL").trim());
				payment.setTotalReceivingWeight(rs.getString("RGRDIWT").trim());
				payment.setTotalBinsReceived(rs.getString("RGRDBCT").trim());
				payment.setPaymentWeight(rs.getString("RGRDPWT").trim());
				payment.setPackerWarehouse(rs.getString("RGRDPCK").trim());
				payment.setPaymentCarrierNumber(rs.getString("RGRDCAR").trim());
				payment.setFreightWeight(rs.getString("RGRDFRW"));
				
				// convert date for display.
				payment.setPaymentReceivingDate("");
				
				if (rs.getInt("RGRDRDT") != 0) 
				{
					//convert file date from yyyy/mm/dd to mm/dd/yyyy.
					String fileDate = rs.getString("RGRDRDT");
					String date = fileDate.substring(4, 6) + "/";
					date = date + fileDate.substring(6, 8) + "/";
					date = date + fileDate.substring(0, 4);
					
					//validate date.
					String[] ckDates = CheckDate.validateDate(date);
	
					if (ckDates[6].equals(""))
						payment.setPaymentReceivingDate(ckDates[1]);
	
				} else
					payment.setPaymentReceivingDate("0");
				
				payment.setFruitPaidFlag(rs.getString("RGRDFPF").trim());
				payment.setHaulPaidFlag(rs.getString("RGRDHPF").trim());
				payment.setInvTransNumber(rs.getString("RGRDITR").trim());
				payment.setFreightTransNumber(rs.getString("RGRDFTR").trim());
				payment.setPaymentTransNumber(rs.getString("RGRDPTR").trim());
				payment.setPaymentIndustryGrade(rs.getString("RGRDIGD").trim());
				if (type.equals("listPaymentsEdit"))
				{
					try
					{
					   payment.setHauling100weight(rs.getString("GRDHRT").trim());
					   payment.setFuelSurcharge(rs.getString("GRDSCH").trim());
					}
					catch(Exception e)
					{  // Catch Exception if NO Record
					   payment.setHauling100weight("");
					   payment.setFuelSurcharge("");
					}
				}
			}
			if (type.equals("listPaymentsEdit") ||
				 type.equals("binWeights"))
			{
				try
				{
					BigDecimal byTypeBinQty = rs.getBigDecimal("GRICNT");
					BigDecimal byTypeBinWgt = rs.getBigDecimal("GRAWGT");
					payment.setBinWeight(byTypeBinQty.multiply(byTypeBinWgt).toString());
				}
				catch(Exception e)
				{
					payment.setBinWeight("0");
				}
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the class ");
			throwError.append("from the result set. " + e) ;
		}
		
		// return data.
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceLot.");
			throwError.append("loadFieldsRawFruitPayment(rs, type:");
			throwError.append(type + "). ");
			throw new Exception(throwError.toString());
		}
		return payment;
	}
	/**
	 * Added Method: 12/31/08 - Teri Walton
	 * Use to Validate:
	 * 	 Send in VECTOR:::  Lot Number, Scale Ticket Number, Scale Sequence Number, Ticket Sequence 
	 *   Return Message if Lot Number is not found - else Message would be Blank
	 */
	public static String verifyRawFruitLotNumber(String environment, 
										         Vector inParms)
	throws Exception
	{
//		 Changes to Method
		// 2/2/09 TWalton - Change from Connection Stack to dbConnection
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyRawFruitLotNumber";
			String sqlString = "";
				
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			if (inParms.size() != 4)
				rtnProblem.append(" Lot Number must not be null or empty. ");
			if (rtnProblem.toString().equals(""))
			{
				String lotNumber = (String) inParms.elementAt(0);
				// verify incoming Lot Number
				if (lotNumber == null || lotNumber.trim().equals(""))
					rtnProblem.append(" Lot Number must not be null or empty.");	
				String scaleTicket = (String) inParms.elementAt(1);
				// verify incoming Scale Ticket
				if (scaleTicket == null || scaleTicket.trim().equals(""))
					rtnProblem.append(" Scale Ticket must not be null or empty.");	
				String scaleTicketSequence = (String) inParms.elementAt(2);
				// verify incoming Lot Number
				if (scaleTicketSequence == null || scaleTicketSequence.trim().equals(""))
					rtnProblem.append(" Scale Ticket Sequence must not be null or empty.");	
				String lotSequence = (String) inParms.elementAt(3);
				// verify incoming Lot Number
				if (lotSequence == null || lotSequence.trim().equals(""))
					rtnProblem.append(" Lot Sequence must not be null or empty.");	
				if (rtnProblem.toString().equals(""))
				{
//					get Test Lot Number Info.
					try {
						sqlString = buildSqlStatement(environment, requestType, inParms);
					} catch (Exception e) {
						rtnProblem.append(" Problem when building Test for Lot Number: ");
						rtnProblem.append(lotNumber + " PrintScreen this message and send to Information Services. ");
					}
				}
				// get a connection. execute sql, build return object.
				if (rtnProblem.toString().equals("")) {
					try {
						conn = ServiceConnection.getConnectionStack5();
							
						findIt = conn.prepareStatement(sqlString);
						rs = findIt.executeQuery();
					
						// Test the While Statement
						int recordCount = 0;
						while (rs.next())
						{
							recordCount++;
							if (recordCount > 1)
								rtnProblem.append("Duplicate Lot Number Has Been Found");
							else
							{
								if (!rs.getString("G4SCALE#").trim().equals(scaleTicket.trim()) ||
									!rs.getString("G4SEQ#").trim().equals(scaleTicketSequence.trim()) ||
									!rs.getString("G4TSEQ#").trim().equals(lotSequence.trim()))
								{
									rtnProblem.append("This Lot Number is duplicated in Scale Ticket: " + rs.getString("G4SCALE#"));
									rtnProblem.append("  Sequence: " + rs.getString("G4SEQ#"));
								}
							}
						} 
													
					} catch (Exception e) {
						rtnProblem.append(" Problem when finding Lot Number: ");
						rtnProblem.append(lotNumber + " PrintScreen this message and send to Information Services. ");
					}
				}
			}
		} catch(Exception e)
		{
		// return connection.
		} finally {
			if (conn != null) {
				try {
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
//		if (!rtnProblem.toString().equals("")) {
//			throwError.append("Error @ com.treetop.services.");
//			throwError.append("ServiceLot.");
//			throwError.append("verifyM3LotNumber(String: Environment, String: LotNumber)");
//			throw new Exception(rtnProblem.toString());
	//	}
		return rtnProblem.toString();
	}
	/**
	 * Determine what needs to be done to Update a record for a Raw Fruit Load
	 */
	public static void updateFields(Vector incomingParms) // Send in UpdInventory
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		try { 
			// Determine which methods need to be processed
			UpdInventory ui = (UpdInventory) incomingParms.elementAt(0);
			if (!ui.getTaggingType().equals("") ||
				!ui.getDisposition().equals(""))
			{
				//conn = ServiceConnection.getConnectionStack5();
				conn = ConnectionStack.getConnection();
				 try
				  {
			   	  	  updateFields(incomingParms, conn);
				  }
			   	  catch(Exception e)
				  {
				   	  	// Catch a Problem
			   	  	throwError.append("Problem occurred when trying to Update the chosen lot records::" + e);
				  }
			}
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the Chosen Lots. " + e) ;
		// return connection.
		} finally {
		try {
			if (conn != null)
				ConnectionStack.returnConnection(conn);
				//ServiceConnection.returnConnectionStack5(conn);
		} catch (Exception el) {
			el.printStackTrace();
		}
		}
		// return data.
		if (!throwError.toString().equals("")) 
			System.out.println(throwError.toString().trim());
		//	throwError.append("Error @ com.treetop.services.");
		//	throwError.append("ServiceRawFruit.");
		//	throwError.append("processLoad(Vector)");
		//	throw new Exception(throwError.toString());
		//}
		return;
	}
	/**
	 * Use to Update the Lot Master:
	 *    Used to update the Lot Master
	 */
	private static void updateFields(Vector incomingParms,  // UpdInventory
								     Connection conn)
	throws Exception{
		StringBuffer throwError = new StringBuffer();
		PreparedStatement updIt = null;
		try { 
		  System.out.println("UpdateLots - fix Library");
		  UpdInventory ui = (UpdInventory) incomingParms.elementAt(0);

		  updIt = conn.prepareStatement(buildSqlStatement(ui.getEnvironment(), "updateLots", incomingParms));
		  updIt.executeUpdate();
		} catch(Exception e)
		{
			throwError.append(" Problem Updating the Chosen Lot Records. " + e) ;
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
			System.out.println("Error @ com.treetop.services.ServiceLot");
			System.out.println("updateFields(Vector, Connection) ");
			System.out.println(throwError.toString());
		}
		return;
	}


	/**
	 * Added Method: 1/22/08 - Teri Walton
	 * Use to Validate:
	 * 	 Send in Lot Number 
	 *   Return Message if Lot Number is not found - else Message would be Blank
	 */
	public static String verifyM3LotNumber(String environment, 
										   String lotNumber,
										   String itemNumber)
			throws Exception
	{
//		 Changes to Method
		// 2/2/09 TWalton - Change from Connection Pool to dbConnection
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyLotWithItem";
			String sqlString = "";
				
			// verify base class initialization.
			ServiceSalesOrder a = new ServiceSalesOrder();
							
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Lot Number
			if (lotNumber == null || lotNumber.trim().equals(""))
				rtnProblem.append(" Lot Number must not be null or empty.");
//			 verify incoming Item
			if (itemNumber == null || itemNumber.trim().equals(""))
				rtnProblem.append(" Item Number must not be null or empty.");
				
			// get Test Lot Number Info.
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(lotNumber);
					parmClass.addElement(itemNumber);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnProblem.append(" Problem when building Test for Lot Number: ");
					rtnProblem.append(lotNumber + " Item Number: " + itemNumber); 
					rtnProblem.append(" PrintScreen this message and send to Information Services. ");
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
	//					 it exists and all is good.
					} else {
						rtnProblem.append(" Lot Number '" + lotNumber + "' ");
						rtnProblem.append(" for Item Number '" + itemNumber + "' ");
						rtnProblem.append("does not exist. ");
					}
						
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Lot Number: ");
					rtnProblem.append(lotNumber + " Item Number: " + itemNumber); 
					rtnProblem.append(" PrintScreen this message and send to Information Services. ");
				}
			}
			} catch(Exception e)
			{
				throwError.append(" Problem Testing the Lot Number Along with the Item ");
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
			throwError.append("ServiceLot.");
			throwError.append("verifyM3LotNumber(String: Environment, String: LotNumber, String: ItemNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}	
	
	/**
	 * Added Method: 1/22/08 - Teri Walton
	 * Use to Validate:
	 * 	 @param CommonRequestBean
	 * 	Send in environment, Item Number in idLevel1, and Lot Number in idLevel2
	 *   Return Message if Lot Number is not found - else Message would be Blank
	 */
	public static String verifyM3LotNumber(CommonRequestBean crb)
			throws Exception
	{
//		 Changes to Method
		// 2/2/09 TWalton - Change from Connection Pool to dbConnection
		StringBuffer throwError = new StringBuffer();
		StringBuffer rtnProblem = new StringBuffer();
		
		String environment = crb.getEnvironment();
		String itemNumber = crb.getIdLevel1();
		String lotNumber = crb.getIdLevel2();
		
		Connection conn = null; // New Lawson Box - Lawson Database
		PreparedStatement findIt = null;
		ResultSet rs = null;
		try { 
			String requestType = "verifyLotWithItem";
			String sqlString = "";
				
			// verify base class initialization.
			ServiceSalesOrder a = new ServiceSalesOrder();
							
			if (environment == null || environment.trim().equals(""))
				environment = "PRD";
			// verify incoming Lot Number
			if (lotNumber == null || lotNumber.trim().equals(""))
				rtnProblem.append(" Lot Number must not be null or empty.");
//			 verify incoming Item
			if (itemNumber == null || itemNumber.trim().equals(""))
				rtnProblem.append(" Item Number must not be null or empty.");
				
			// get Test Lot Number Info.
			if (throwError.toString().equals(""))
			{
				try {
					Vector parmClass = new Vector();
					parmClass.addElement(lotNumber);
					parmClass.addElement(itemNumber);
					sqlString = buildSqlStatement(environment, requestType, parmClass);
				} catch (Exception e) {
					throwError.append(" error trying to build sqlString. ");
					rtnProblem.append(" Problem when building Test for Lot Number: ");
					rtnProblem.append(lotNumber + " Item Number: " + itemNumber); 
					rtnProblem.append(" PrintScreen this message and send to Information Services. ");
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
	//					 it exists and all is good.
					} else {
						rtnProblem.append(" Lot Number '" + lotNumber + "' ");
						rtnProblem.append(" for Item Number '" + itemNumber + "' ");
						rtnProblem.append("does not exist. ");
					}
						
				} catch (Exception e) {
					throwError.append(" error occured executing a sql statement. " + e);
					rtnProblem.append(" Problem when finding Lot Number: ");
					rtnProblem.append(lotNumber + " Item Number: " + itemNumber); 
					rtnProblem.append(" PrintScreen this message and send to Information Services. ");
				}
			}
			} catch(Exception e)
			{
				throwError.append(" Problem Testing the Lot Number Along with the Item ");
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
			throwError.append("ServiceLot.");
			throwError.append("verifyM3LotNumber(String: Environment, String: LotNumber, String: ItemNumber)");
			throw new Exception(throwError.toString());
		}
		return rtnProblem.toString();
	}	
}
