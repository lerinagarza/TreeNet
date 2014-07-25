 package com.treetop.data;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import javax.sql.*;
import com.treetop.*;
import java.net.URLEncoder;
import com.treetop.utilities.ConnectionStack;
/**
 * This object contains analytical attribute values, defining the
 * specification standard for each manufactured lot of inventory.
 *
 * @author: David M Eisenheim
 *
 */
public class SpecificationAnalytical extends AnalyticalCode {
	
	// Data base fields. (IDPVSPDA)
	
	private   	String        	specificationCode;			// IDVSPC
	private		Integer			specificationDate;			// IDVRVD	
	private		Integer			supersededDate;				// IDVSRV
	private		String			attributeType;				// IDVTYP
	private		String			attributeGroup1;			// IDVGP1
	private		String			attributeGroup2;			// IDVGP2
	private		String			attributeGroup3;			// IDVGP3
	private		Integer			attributeSequence;			// IDVSEQ
	private		BigDecimal		targetValue;				// IDVDTA
	private		BigDecimal		standardUpperLimit;			// IDVSHI
	private		BigDecimal		standardLowerLimit;			// IDVSLO
	private		BigDecimal		acceptableUpperLimit;		// IDVAHI
	private		BigDecimal		acceptableLowerLimit;		// IDVALO
	private		boolean			certificateOfAnalysis;		// IDVCOA
	private		Integer			requiredLevelSequence;		// IDVREQ
	private		String			attributeComment;			// IDVCMT
	private		Integer			marketNumber;				// IDTBRK
	private		Integer			marketCustomer;				// IDTCUS
	private   	String        	updateAnalUser;				// IDVUUS	
	private   	String        	updateAnalUserName;
	private   	Integer		 	updateAnalDate;				// IDVUDT
	private   	Integer		 	updateAnalTime;				// IDVUTM
	private		String			updateAnalWorkstation;		// IDVUWS
	
	
	// Define database environment (live or test) on the AS/400.	

	private static String library = "DBPRD."; 		// live environment
//	private static String library = "WKLIB."; 		// test environment


	// SQL prepared statements.

	private static Stack findAnalByCodeByDate = null;
	private static Stack findAnalBySpecByDate = null;
	
	private static Stack findAnalForAttribute = null;
	
	private static Stack sqlDeleteSpec = null;
	private static Stack sqlInsertSpec = null;
	private static Stack sqlUpdateSpec = null;	

	
	// Additional fields.
	
	private static boolean persists   = false;	

	/**
	 * Build drop down list for specification analytical attributes.
	 *
	 * Creation date: (4/07/2005 1:16:51 PM)
	 * Added encode Section 6/1/05 TW
	 */
	public static Vector buildDropDownAttribute(String inAttribute, 
												Vector inListName, 
											    String inFirstSelect) {		
	    Vector attributeList = new Vector(); 
		String dropDownList  = "";
		String selected      = "";
		String selectOption  = "";
		
		try {			
			
			if ((inFirstSelect == null) || (inFirstSelect.trim().equals ("")))
				selectOption = "Select an Entry--->:";
			else {
				if (inFirstSelect.trim().equals ("*all") ||
					inFirstSelect.trim().equals ("*All") ||
					inFirstSelect.trim().equals ("*ALL"))
					selectOption = inFirstSelect.trim();
				else 
					selectOption = inFirstSelect.trim();			 	
			}	    
	
			try {	
	
				ResultSet rs = findAnalForAttribute();
	
				try {
	
					while (rs.next()) 
					{
						String code = rs.getString("IDVCDE");
						String desc = rs.getString("IDSDSC");
						
						String attr = code.trim();						
						for (int x = attr.length(); x < 5; x++) {
							attr = attr + "&nbsp;";
						}						
	
						if ((inAttribute != null) && (inAttribute.trim().equals (code.trim())))						
							selected = "' selected='selected'>";
						else
							selected = "'>";
		   		    
						dropDownList = dropDownList + "<option value='" + 
						URLEncoder.encode(code.trim()) + selected +
						attr + " - " + desc.trim() + "&nbsp;";
					}
					
					for (int x = 0; x < inListName.size(); x ++) {
						String listName = (String) inListName.elementAt(x);
						String dropDown = dropDownList;			 
				
						if ((listName == null) || (listName.trim().equals ("")))
							 listName = "attributeList" + (x+1);
				
						if (!dropDownList.equals("")) {
					  
							if ((inFirstSelect != null) && (inFirstSelect.toLowerCase().equals ("none")))					
								dropDown = "<select name='" + listName.trim() + "'>" +
										   dropDown + "</select>";					
							else					
								dropDown = "<select name='" + listName.trim() + "'>" +
										   "<option value='None' selected>" + selectOption +
										   dropDown + "</select>";
						}
						
						attributeList.addElement(dropDown);					  	 
					}  	  		
				}
				catch (Exception e) {
					System.out.println("Exception error processing a result set " +
									   "(SpecificationAnalytical.buildDropDownAttribute): " + e);
				}	
		
				rs.close();	
		 
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(SpecificationAnalytical.buildDropDownAttribute): " + e);
			}
		
		}
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data." + 
							   "SpecificationAnalytical.buildDropDownAttribute(String Vector String): " + e);
		}	
	
		return attributeList;  
	
	}
	/**
	 * Instantiate the specification analytical class
	 * from a result set.
	**/
	public SpecificationAnalytical(ResultSet rs) {
		
		super();
		
		init();
		
		loadFields(rs);	
	}
	

	/**
	 * Execute an SQL statement to retrieve all analytical attributes for a single
	 * specification using the code and date.
	 *
	 * Creation date: (3/31/2005 3:22:24 PM)
	 */
	public static BigDecimal decimalPositionRemove(BigDecimal numericValue, int decimalPositions) {

		try {
			
			if (decimalPositions > 0) {				
				
				BigDecimal multipler  = new BigDecimal("1");
				BigDecimal powerOfTen = new BigDecimal("10");
				
				for (int x = 0; x < decimalPositions; x++) {
					multipler = multipler.multiply(powerOfTen);
				}				
					
				numericValue = numericValue.multiply(multipler);
			}	

		}
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data.SpecificationAnalytical." + 
							   "decimalPositionRemove(BigDecimal int): " + e);
		}
		
		return numericValue;	                                     
	}

	/**
	 * Execute an SQL statement to retrieve all analytical attributes for a single
	 * specification using the code and date.
	 *
	 * Creation date: (3/31/2005 3:22:24 PM)
	 */
	public static BigDecimal decimalPositionSet(BigDecimal numericValue, int decimalPositions) {

		try {
			
			if (decimalPositions > 0) {
				
				int decimals = decimalPositions;
				BigDecimal divisor = new BigDecimal("1");
				BigDecimal powerOfTen = new BigDecimal("10");
				
				for (int x = 0; x < decimalPositions; x++) {
					divisor = divisor.multiply(powerOfTen);
				}				
					
				numericValue = numericValue.divide(divisor, decimals, decimals);
			}	

		}
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data.SpecificationAnalytical." + 
							   "decimalPositionSet(BigDecimal int): " + e);
		}
		
		return numericValue;	                                     
	}

	/**
	 * Execute an SQL statement to retrieve all analytical attributes for a single
	 * specification using the code and date.
	 *
	 * Creation date: (3/31/2005 3:22:24 PM)
	 */
	public static Vector findAnalByCodeByDate(String inCode, Integer inDate) {

		Vector                  analList = new Vector();
		SpecificationAnalytical anal     = new SpecificationAnalytical();

		try {
			
			PreparedStatement analByCodeByDate = (PreparedStatement) 
												  SpecificationAnalytical.findAnalByCodeByDate.pop();
			ResultSet rs = null;
		
			try {
				analByCodeByDate.setString(1, inCode.toUpperCase());
				analByCodeByDate.setInt(2, inDate.intValue());				
				rs = analByCodeByDate.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(SpecificationAnalytical.findAnalByCodeByDate): " + e);
			}
		
			SpecificationAnalytical.findAnalByCodeByDate.push(analByCodeByDate);        	

              
			try {
	        
				while (rs.next()) {			
				
					SpecificationAnalytical attribute = new SpecificationAnalytical();
					attribute.loadFields(rs);
					analList.addElement(attribute); 
				}
        
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set " +
								   "(SpecificationAnalytical.findAnalByCodeByDate): " + e);
			}
			
			rs.close();            
        
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "SpecificationAnalytical.findAnalByCodeByDate(String Integer): " + e);
		}
		
		return analList;	                                     
	}
	
	/**
	 * Execute an SQL statement to retrieve all analytical attributes for a single
	 * specification using the code and date.
	 *
	 * Creation date: (3/31/2005 3:22:24 PM)
	 */
	public static Vector findAnalByCodeByDate(String inCode, String inDate) {

		Vector                  analList = new Vector();
		SpecificationAnalytical anal     = new SpecificationAnalytical();

		try {
			
			PreparedStatement analByCodeByDate = (PreparedStatement) 
												  SpecificationAnalytical.findAnalByCodeByDate.pop();
			ResultSet rs = null;
		
			try {
				Integer revisionDate = GetDate.formatSetDate(inDate);
				analByCodeByDate.setString(1, inCode.toUpperCase());
				analByCodeByDate.setInt(2, revisionDate.intValue());				
				rs = analByCodeByDate.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(SpecificationAnalytical.findAnalByCodeByDate): " + e);
			}
		
			SpecificationAnalytical.findAnalByCodeByDate.push(analByCodeByDate);        	

              
			try {
	        
				while (rs.next()) {			
				
					SpecificationAnalytical attribute = new SpecificationAnalytical();
					attribute.loadFields(rs);
					analList.addElement(attribute); 
				}
        
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set " +
								   "(SpecificationAnalytical.findAnalByCodeByDate): " + e);
			}
			
			rs.close();            
        
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "SpecificationAnalytical.findAnalByCodeByDate(String String): " + e);
		}
		
		return analList;	                                     
	}
	
	/**
	 * Execute an SQL statement to retrieve all analytical attributes for a single
	 * specification using the specification code and date.
	 *
	 * Creation date: (5/11/2005 3:50:19 PM)
	 */
	public static Vector findAnalBySpecByDate(String inCode, Integer inDate) {

		Vector                  analList = new Vector();
		SpecificationAnalytical anal     = new SpecificationAnalytical();

		try {
			
			PreparedStatement analBySpecByDate = (PreparedStatement) 
												  SpecificationAnalytical.findAnalBySpecByDate.pop();
			ResultSet rs = null;
		
			try {
				analBySpecByDate.setString(1, inCode.toUpperCase());
				analBySpecByDate.setInt(2, inDate.intValue());				
				rs = analBySpecByDate.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(SpecificationAnalytical.findAnalBySpecByDate): " + e);
			}
		
			SpecificationAnalytical.findAnalBySpecByDate.push(analBySpecByDate);        	

              
			try {
	        
				while (rs.next()) {			
				
					SpecificationAnalytical attribute = new SpecificationAnalytical();
					attribute.loadFields(rs);
					analList.addElement(attribute); 
				}
        
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set " +
								   "(SpecificationAnalytical.findAnalBySpecByDate): " + e);
			}
			
			rs.close();            
        
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "SpecificationAnalytical.findAnalBySpecByDate(String Integer): " + e);
		}
		
		return analList;	                                     
	}
	
	/**
	 * Execute an SQL statement to retrieve all analytical attributes for a single
	 * specification using the specification code and date.
	 *
	 * Creation date: (5/11/2005 3:53:26 PM)
	 */
	public static Vector findAnalBySpecByDate(String inCode, String inDate) {

		Vector                  analList = new Vector();
		SpecificationAnalytical anal     = new SpecificationAnalytical();

		try {
			
			PreparedStatement analBySpecByDate = (PreparedStatement) 
												  SpecificationAnalytical.findAnalBySpecByDate.pop();
			ResultSet rs = null;
		
			try {
				Integer revisionDate = GetDate.formatSetDate(inDate);
				analBySpecByDate.setString(1, inCode.toUpperCase());
				analBySpecByDate.setInt(2, revisionDate.intValue());				
				rs = analBySpecByDate.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(SpecificationAnalytical.findAnalBySpecByDate): " + e);
			}
		
			SpecificationAnalytical.findAnalBySpecByDate.push(analBySpecByDate);        	

              
			try {
	        
				while (rs.next()) {			
				
					SpecificationAnalytical attribute = new SpecificationAnalytical();
					attribute.loadFields(rs);
					analList.addElement(attribute); 
				}
        
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set " +
								   "(SpecificationAnalytical.findAnalBySpecByDate): " + e);
			}
			
			rs.close();            
        
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "SpecificationAnalytical.findAnalBySpecByDate(String String): " + e);
		}
		
		return analList;	                                     
	}
	
	/**
	 * Retrieve the specification and test the type to determine the 
	 * proper SQL process for retrieving analytical specifications.
	 *
	 * Creation date: (5/11/2005 3:45:13 PM)
	 */
	public static Vector findAnalBySpecByType(String inCode, Integer inDate) {
		
		Vector analList = new Vector();

		try {
			
			Specification spec = new Specification(inCode, inDate);
			
			if (spec.getSpecificationType() != null) {
				
				// Raw fruit analytical specification processing.
				
			 	if (spec.getSpecificationType().trim().equals ("RF"))			    	
			    	analList = SpecificationAnalytical.findAnalByCodeByDate(inCode, inDate);		    

				// Non-raw fruit analytical specification processing.
			
				else			 				
					analList = SpecificationAnalytical.findAnalBySpecByDate(inCode, inDate);
			}			
        
		}	
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "SpecificationAnalytical.findAnalBySpecByType(String Integer): " + e);
		}
		
		return analList;	                                     
	}
	
	/**
	 * Retrieve the specification and test the type to determine the 
	 * proper SQL process for retrieving analytical specifications.
	 *
	 * Creation date: (5/11/2005 3:47:44 PM)
	 */
	public static Vector findAnalBySpecByType(String inCode, String inDate) {
		
		Vector analList = new Vector();

		try {
			
			Specification spec = new Specification(inCode, inDate);
			
			if (spec.getSpecificationType() != null) {
				
				// Raw fruit analytical specification processing.
				
				if (spec.getSpecificationType().trim().equals ("RF"))			    	
					analList = SpecificationAnalytical.findAnalByCodeByDate(inCode, inDate);		    

				// Non-raw fruit analytical specification processing.
			
				else			 				
					analList = SpecificationAnalytical.findAnalBySpecByDate(inCode, inDate);
			}			
        
		}	
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "SpecificationAnalytical.findAnalBySpecByType(String String): " + e);
		}
		
		return analList;	                                     
	}
	
	/**
	 * Execute an SQL statement to retrieve each unique attribute code used
	 * in the specification headers.
	 *
	 * Creation date: (4/07/2005 11:51:33 AM)
	 */
	public static ResultSet findAnalForAttribute() {

		ResultSet               analList   = null;
		SpecificationAnalytical analytical = new SpecificationAnalytical();

		try {
			
			PreparedStatement specForResource = (PreparedStatement) 
												 SpecificationAnalytical.findAnalForAttribute.pop();			
		
			try {
			
				analList = specForResource.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(Specification.findSpecForResource): " + e);
				try {
					
					analList.close();
				}
				catch (Exception x) {
				}
			}
		
			SpecificationAnalytical.findAnalForAttribute.push(specForResource);
 
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "Specification.findSpecForResource(): " + e);
		}
		
		return analList;	                                     
	}
	
	/**
	 * Main for testing methods.
	 *
	 * Creation date: (3/31/2005 11:45:39 AM)
	 */
	public static void main(String[] args) {
		
		try {

			SpecificationAnalytical anal = new SpecificationAnalytical();
			String  code = "David";
			String  date = "08/10/1955";
			String  test = "TEST";
			anal.delete(code, date, test);
												 
			System.out.println("Delete analytical successfull");
		}
		catch (Exception e) {
			System.out.println("Error: delete analytical: " + e);
		}						
		
		try {
			
			SpecificationAnalytical anal = new SpecificationAnalytical();
			Vector data = new Vector();
			anal.specificationCode = "David";
			anal.specificationDate = new Integer("12345678");
			anal.supersededDate = new Integer("87654321");
			anal.analyticalCode = "TEST";
			anal.attributeGroup1 = "AP";
			anal.attributeGroup2 = "PK";
			anal.attributeGroup3 = "XX";
			anal.attributeSequence = new Integer("777");
			anal.targetValue = new BigDecimal(500);
			anal.standardUpperLimit = new BigDecimal(1200);
			anal.standardLowerLimit = new BigDecimal(450);
			anal.acceptableUpperLimit = new BigDecimal(1400);
			anal.acceptableLowerLimit = new BigDecimal(350);
			anal.certificateOfAnalysis = true;
			anal.requiredLevelSequence = new Integer("8");
			anal.attributeComment = "comment data 2";
			anal.marketNumber = new Integer("125");
			anal.marketCustomer = new Integer("517");
			anal.updateAnalUser = "DEISEN";
			anal.updateAnalDate = new Integer("19540823");
			anal.updateAnalTime = new Integer("091500");
			anal.updateAnalWorkstation = "WSDP01";	
						
			data.addElement(anal);
			anal.update(data);
												 
			System.out.println("Insert analytical successfull");
		}
		catch (Exception e) {
			System.out.println("Error: insert analytical: " + e);
		}
		
		
		try {
			
			String code  = null;
			Vector name  = new Vector();
			String first = null;
			name.addElement("");
			name.addElement("code list");
			name.addElement("");
			Vector list  = SpecificationAnalytical.buildDropDownAttribute(code, name, first);
												 
			System.out.println("findAnalByCodeByDate successfull");
		}
		catch (Exception e) {
			System.out.println("Error: findAnalByCodeByDate: " + e);
		}
								
		
		try {

			String  code = "BER01";
			Integer date = new Integer("20011022");
			Vector  anal = SpecificationAnalytical.findAnalByCodeByDate(code, date);
												 
			System.out.println("findAnalByCodeByDate successfull");
		}
		catch (Exception e) {
			System.out.println("Error: findAnalByCodeByDate: " + e);
		}						
						
	}
	
	/**
	 * Instantiate the specification analytical attribute.
	 * 
	 * Creation date: (2/22/2005 11:42:39 AM)
	 */
	public SpecificationAnalytical() {
		
		super();
		
		init();		
	}
	
	/**
	 * Delete an analytical specification attribute.
	 *
	 * Creation date: (3/16/2005 4:37:29 PM)
	 */
	private boolean delete(String inSpecificationCode, String inSpecificationDate,
						   String inAttributeCode) {

		try {
	
			PreparedStatement deleteSpec = (PreparedStatement) sqlDeleteSpec.pop();
			
			Integer revisionDate = GetDate.formatSetDate(inSpecificationDate);
			deleteSpec.setString(1, inSpecificationCode);
			deleteSpec.setInt(2, revisionDate.intValue());
			deleteSpec.setString(3, inAttributeCode);		
			deleteSpec.executeUpdate();
			sqlDeleteSpec.push(deleteSpec);
			
			return true;
					
		} 

		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data." +
							   "SpecificationAnalytical.delete(String String String): " + e);
			return false;
		}	
	
	}
	
	/**
	 * Retrieve the acceptable lower limit value.
	 *
	 * Creation date: (2/22/2005 1:48:28 PM)
	 */
	public BigDecimal getAcceptableLowerLimit() {
		
		return acceptableLowerLimit;
	}
	
	/**
	 * Retrieve the acceptable upper limit value.
	 *
	 * Creation date: (2/22/2005 1:48:28 PM)
	 */
	public BigDecimal getAcceptableUpperLimit() {
		
		return acceptableUpperLimit;
	}
	
	/**
	 * Retrieve the analytical attribute code.
	 *
	 * Creation date: (2/22/2005 1:55:42 PM)
	 */
	public String getAnalyticalCode() {

		return analyticalCode;
	}
	
	/**
	 * Retrieve the attribute comment.
	 *
	 * Creation date: (2/22/2005 2:02:15 PM)
	 */
	public String getAttributeComment() {

		return attributeComment;
	}
	
	/**
	 * Retrieve the first attribute group code.
	 *
	 * Creation date: (6/27/2005 4:42:05 PM)
	 */
	public String getAttributeGroup1() {

		return attributeGroup1;
	}
	
	/**
	 * Retrieve the second attribute group code.
	 *
	 * Creation date: (6/27/2005 4:42:15 PM)
	 */
	public String getAttributeGroup2() {

		return attributeGroup2;
	}
	
	/**
	 * Retrieve the third attribute group code.
	 *
	 * Creation date: (6/27/2005 4:42:05 PM)
	 */
	public String getAttributeGroup3() {

		return attributeGroup3;
	}
	
	/**
	 * Retrieve the attribute sequencing number.
	 *
	 * Creation date: (2/22/2005 2:06:00 PM)
	 */
	public Integer getAttributeSequence() {
		
		return attributeSequence;	
	}
	
	/**
	 * Retrieve the attribute type.
	 *
	 * Creation date: (4/06/2005 11:02:23 AM)
	 */
	public String getAttributeType() {

		return attributeType;
	}
	
	/**
	 * Retrieve the certificate of analysis switch.
	 *
	 * Creation date: (2/22/2005 2:01:05 PM)
	 */
	public boolean getCertificateOfAnalysis() {

		return certificateOfAnalysis;
	}
	
	/**
	 * Retrieve the market number.
	 *
	 * Creation date: (4/06/2005 8:29:26 AM)
	 */
	public Integer getMarketNumber() {
		
		return marketNumber;	
	}
	
	/**
	 * Retrieve the customer number portion of the market/customer combination.
	 *
	 * Creation date: (4/06/2005 8:30:32 AM)
	 */
	public Integer getMarketCustomer() {
		
		return marketCustomer;	
	}
	
	/**
	 * Retrieve the attribute required level sequence.
	 *
	 * Creation date: (3/21/2005 1:56:13 PM)
	 */
	public Integer getRequiredLevelSequence() {
		
		return requiredLevelSequence;	
	}
	
	/**
	 * Retrieve the specification Code.
	 *
	 * Creation date: (2/22/2005 2:17:10 PM)
	 */
	public String getSpecificationCode() {

		return specificationCode;
	}
	
	/**
	 * Retrieve the specification Date.
	 *
	 * Creation date: (2/22/2005 2:21:00 PM)
	 */
	public String getSpecificationDate() {
		
		String dateFormated = GetDate.formatGetDate(specificationDate);
	
		return dateFormated;
	}
	
	/**
	 * Retrieve the standard lower limit value.
	 *
	 * Creation date: (2/22/2005 1:24:28 PM)
	 */
	public BigDecimal getStandardLowerLimit() {
		
		return standardLowerLimit;
	}
	
	/**
	 * Retrieve the standard upper limit value.
	 *
	 * Creation date: (2/22/2005 1:48:28 PM)
	 */
	public BigDecimal getStandardUpperLimit() {
		
		return standardUpperLimit;
	}
	
	/**
	 * Retrieve the superseded revision date.
	 *
	 * Creation date: (4/06/2005 10:47:26 AM)
	 */
	public String getSupersededDate() {
		
		String dateFormated = GetDate.formatGetDate(supersededDate);
	
		return dateFormated;
	}
	
	/**
	 * Retrieve the targeted value.
	 *
	 * Creation date: (2/22/2005 1:27:18 PM)
	 */
	public BigDecimal getTargetValue() {
		
		return targetValue;
	}
	
	/**
	 * Retrieve the last update date. 
	 *
	 * Creation date: (2/22/2005 2:29:35 PM)
	 */
	public String getUpdateAnalDate() {

		String dateFormated = GetDate.formatGetDate(updateAnalDate);
	
		return dateFormated;
	}
	
	/**
	 * Retrieve the last update time. 
	 *
	 * Creation date: (2/22/2005 2:30:00 PM)
	 */
	public String getUpdateAnalTime() {

		String timeFormated = TimeUtilities.formatGetTime(updateAnalTime);
	
		return timeFormated;	
	}
	
	/**
	 * Retrieve the last update user profile.
	 *
	 * Creation date: (2/22/2005 2:33:10 PM)
	 */
	public String getUpdateAnalUser() {

		return updateAnalUser;
	}
	
	/**
	 * Retrieve the last update user name.
	 *
	 * Creation date: (2/22/2005 2:33:30 PM)
	 */
	public String getUpdateAnalUserName() {

		return updateAnalUserName;
	}
	
	/**
	 * Retrieve the last update workstation name.
	 * 
	 * Creation date: (3/16/2005 2:46:33 PM)
	 */
	public String getUpdateAnalWorkstation() {

		return updateAnalWorkstation;
	}																

	/**
	 * SQL definitions.
	 *
	 * Creation date: (2/22/2005 1:27:29 PM)
	 * Modified date: (01/02/2009)
	 */
	public void init() {	
	 
	
		// Test for prior initialization.
	
		if (persists == false) {	
			persists = true;	   
	    

		// Perform initialization.
	 
		try {

			Connection conn1 = ConnectionStack.getConnection();
			Connection conn2 = ConnectionStack.getConnection();
			Connection conn3 = ConnectionStack.getConnection();
			Connection conn4 = ConnectionStack.getConnection();
			Connection conn5 = ConnectionStack.getConnection();	
			Connection conn6 = ConnectionStack.getConnection();
			

			// SQL selection --------									

			String analByCodeByDate = 						
				  "SELECT *  " +
				  "FROM " + library + "IDPVSPDA " +	
			      "     INNER JOIN " + library + "IDPTSPHD " +
							       "ON IDVSPC = IDTSPC AND IDVRVD = IDTRVD " +		     	      	
			      "     INNER JOIN " + library + "IDPSANAL " + 
			                       "ON IDVCDE = IDSCDE " +
			      "LEFT OUTER JOIN " + library + "INPCANAL " + 
							       "ON IDTTYP = INCTYP AND IDVCDE = INCCDE AND " +
							       "   IDVGP1 = INCGC1 AND IDVGP2 = INCGC2 AND " +
							       "   IDVGP3 = INCGC3 " +							
				  "WHERE IDVSPC = ? AND IDVRVD = ? " +
				  "ORDER BY IDVSPC, IDVRVD DESC, IDVREQ DESC, INCSEQ, IDVCDE";

			PreparedStatement analByCodeByDate1 = conn1.prepareStatement(analByCodeByDate);
			PreparedStatement analByCodeByDate2 = conn2.prepareStatement(analByCodeByDate);
			PreparedStatement analByCodeByDate3 = conn3.prepareStatement(analByCodeByDate);
			PreparedStatement analByCodeByDate4 = conn4.prepareStatement(analByCodeByDate);
			PreparedStatement analByCodeByDate5 = conn5.prepareStatement(analByCodeByDate);
			PreparedStatement analByCodeByDate6 = conn6.prepareStatement(analByCodeByDate);

			findAnalByCodeByDate = new Stack();
			findAnalByCodeByDate.push(analByCodeByDate1);
			findAnalByCodeByDate.push(analByCodeByDate2);
			findAnalByCodeByDate.push(analByCodeByDate3);
			findAnalByCodeByDate.push(analByCodeByDate4);
			findAnalByCodeByDate.push(analByCodeByDate5);
			findAnalByCodeByDate.push(analByCodeByDate6);
			
			String analBySpecByDate = 						
				  "SELECT *  " +
				  "FROM " + library + "IDPVSPDA " +	
				  "     INNER JOIN " + library + "IDPTSPHD " +
								   "ON IDVSPC = IDTSPC AND IDVRVD = IDTRVD " +		     	      	
				  "     INNER JOIN " + library + "IDPSANAL " + 
								   "ON IDVCDE = IDSCDE " +
				  "LEFT OUTER JOIN " + library + "INPCANAL " + 
								   "ON IDTTYP = INCTYP AND IDVCDE = INCCDE AND " +
							       "   IDVGP1 = INCGC1 AND IDVGP2 = INCGC2 AND " +
							       "   IDVGP3 = INCGC3 " +														
				  "WHERE IDVSPC = ? AND IDVRVD = ? " +
				  "ORDER BY IDVSPC, IDVRVD DESC, IDVREQ DESC, IDSSEQ, IDVCDE";

			PreparedStatement analBySpecByDate1 = conn1.prepareStatement(analBySpecByDate);
			PreparedStatement analBySpecByDate2 = conn2.prepareStatement(analBySpecByDate);
			PreparedStatement analBySpecByDate3 = conn3.prepareStatement(analBySpecByDate);
			PreparedStatement analBySpecByDate4 = conn4.prepareStatement(analBySpecByDate);
			PreparedStatement analBySpecByDate5 = conn5.prepareStatement(analBySpecByDate);
			PreparedStatement analBySpecByDate6 = conn6.prepareStatement(analBySpecByDate);

			findAnalBySpecByDate = new Stack();
			findAnalBySpecByDate.push(analBySpecByDate1);
			findAnalBySpecByDate.push(analBySpecByDate2);
			findAnalBySpecByDate.push(analBySpecByDate3);
			findAnalBySpecByDate.push(analBySpecByDate4);
			findAnalBySpecByDate.push(analBySpecByDate5);
			findAnalBySpecByDate.push(analBySpecByDate6);
			
			
			// SQL selection (drop down lists) --------
			
			String analForAttribute =
				  "SELECT IDVCDE, IDSDSC " + 
				  "FROM " + library + "IDLVDTAD " +
			      "     INNER JOIN " + library + "IDPTSPHD " +
						           "ON IDVSPC = IDTSPC AND IDVRVD = IDTRVD " +		     	      	
			      "     INNER JOIN " + library + "IDPSANAL " + 
						           "ON IDVCDE = IDSCDE " +
			      "LEFT OUTER JOIN " + library + "INPCANAL " + 
						           "ON IDTTYP = INCTYP AND IDVCDE = INCCDE AND " +
							       "   IDVGP1 = INCGC1 AND IDVGP2 = INCGC2 AND " +
							       "   IDVGP3 = INCGC3 " +									
						  "GROUP BY IDVCDE, IDSDSC " +                         
						  "ORDER BY IDVCDE, IDSDSC ";
						  
			PreparedStatement analForAttribute1 = conn1.prepareStatement(analForAttribute);
			PreparedStatement analForAttribute2 = conn2.prepareStatement(analForAttribute);
			PreparedStatement analForAttribute3 = conn3.prepareStatement(analForAttribute);
			PreparedStatement analForAttribute4 = conn4.prepareStatement(analForAttribute);
			PreparedStatement analForAttribute5 = conn5.prepareStatement(analForAttribute);
			PreparedStatement analForAttribute6 = conn6.prepareStatement(analForAttribute);

			findAnalForAttribute = new Stack();
			findAnalForAttribute.push(analForAttribute1);
			findAnalForAttribute.push(analForAttribute2);
			findAnalForAttribute.push(analForAttribute3);
			findAnalForAttribute.push(analForAttribute4);
			findAnalForAttribute.push(analForAttribute5);
			findAnalForAttribute.push(analForAttribute6);
			
			
			// SQL for data base changes --------		
					
			String deleteSpec = 
				  "DELETE FROM " + library + "IDPVSPDA " +
				  "WHERE IDVSPC = ? AND IDVRVD = ? AND IDVCDE = ?";			 

			PreparedStatement deleteSpec1 = conn1.prepareStatement(deleteSpec);		
			PreparedStatement deleteSpec2 = conn2.prepareStatement(deleteSpec);		 
			PreparedStatement deleteSpec3 = conn3.prepareStatement(deleteSpec);		 
			PreparedStatement deleteSpec4 = conn4.prepareStatement(deleteSpec);		 
			PreparedStatement deleteSpec5 = conn5.prepareStatement(deleteSpec);	
			PreparedStatement deleteSpec6 = conn6.prepareStatement(deleteSpec);	 

			sqlDeleteSpec = new Stack();		
			sqlDeleteSpec.push(deleteSpec1);		
			sqlDeleteSpec.push(deleteSpec2);
			sqlDeleteSpec.push(deleteSpec3);
			sqlDeleteSpec.push(deleteSpec4);
			sqlDeleteSpec.push(deleteSpec5);
			sqlDeleteSpec.push(deleteSpec6);
			
			
			String insertSpec =     			
				  "INSERT INTO " + library + "IDPVSPDA " +
				  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement insertSpec1 = conn1.prepareStatement(insertSpec);
			PreparedStatement insertSpec2 = conn2.prepareStatement(insertSpec);
			PreparedStatement insertSpec3 = conn3.prepareStatement(insertSpec);
			PreparedStatement insertSpec4 = conn4.prepareStatement(insertSpec);
			PreparedStatement insertSpec5 = conn5.prepareStatement(insertSpec);
			PreparedStatement insertSpec6 = conn6.prepareStatement(insertSpec);

			sqlInsertSpec = new Stack();
			sqlInsertSpec.push(insertSpec1);
			sqlInsertSpec.push(insertSpec2);
			sqlInsertSpec.push(insertSpec3);
			sqlInsertSpec.push(insertSpec4);
			sqlInsertSpec.push(insertSpec5);
			sqlInsertSpec.push(insertSpec6);
			
			
			String updateSpec = 
				  "UPDATE " + library + "IDPVSPDA " +
				  "SET IDVREC = ?, IDVTYP = ?, IDVBRK = ?, IDVCUS = ?, IDVSPC = ?," +
					 " IDVCDE = ?, IDVSEQ = ?, IDVDTA = ?, IDVSHI = ?, IDVSLO = ?," +
					 " IDVAHI = ?, IDVALO = ?, IDVRVD = ?, IDVSRV = ?, IDVCOA = ?," +
					 " IDVUUS = ?, IDVUWS = ?, IDVUDT = ?, IDVUTM = ?, IDVCMT = ?," +
					 " IDVREQ = ?, IDVGP1 = ?, IDVGP2 = ?, IDVGP3 = ? " +
				  "WHERE IDVSPC = ? AND IDVRVD = ? AND IDVCDE = ?";
			
			PreparedStatement updateSpec1 = conn1.prepareStatement(updateSpec);
			PreparedStatement updateSpec2 = conn2.prepareStatement(updateSpec);
			PreparedStatement updateSpec3 = conn3.prepareStatement(updateSpec);
			PreparedStatement updateSpec4 = conn4.prepareStatement(updateSpec);
			PreparedStatement updateSpec5 = conn5.prepareStatement(updateSpec);
			PreparedStatement updateSpec6 = conn6.prepareStatement(updateSpec);

			sqlUpdateSpec = new Stack();
			sqlUpdateSpec.push(updateSpec1);
			sqlUpdateSpec.push(updateSpec2);
			sqlUpdateSpec.push(updateSpec3);
			sqlUpdateSpec.push(updateSpec4);
			sqlUpdateSpec.push(updateSpec5);
			sqlUpdateSpec.push(updateSpec6);			
						
			
			// Return the connections back to the pool.
		
			ConnectionStack.returnConnection(conn1);
			ConnectionStack.returnConnection(conn2);
			ConnectionStack.returnConnection(conn3);
			ConnectionStack.returnConnection(conn4);
			ConnectionStack.returnConnection(conn5);
			ConnectionStack.returnConnection(conn6);
		
		}				

		catch (SQLException e) {
			System.out.println("SQL exception occured at com.treetop.data." +
				               "SpecificationAnalytical.init()" + e);
		}    
    	
		}
	
	}
		
	/**
	 * Insert an analytical specification attribute.
	 *
	 * Creation date: (3/16/2005 4:45:00 PM)
	 */
	public Exception insert(Vector inInsertInfo) {

		try {	
					
			for (int x = 0; x < inInsertInfo.size(); x ++) {				  	
			 
				SpecificationAnalytical insertInfo = (SpecificationAnalytical) 
													  inInsertInfo.elementAt(x);
				                                      	  
				if ((insertInfo.specificationCode != null) && 		// Spec code required
				   (!insertInfo.specificationCode.equals(""))) {
					    	
					PreparedStatement insertSpec = (PreparedStatement) sqlInsertSpec.pop();
		
					insertSpec.setString(1, "SA");
					insertSpec.setString(2, " ");	
					insertSpec.setInt(3, insertInfo.marketNumber.intValue());
					insertSpec.setInt(4, insertInfo.marketCustomer.intValue());
					insertSpec.setString(5, insertInfo.specificationCode);				
					insertSpec.setString(6, insertInfo.analyticalCode);
					insertSpec.setInt(7, insertInfo.attributeSequence.intValue());
					insertSpec.setBigDecimal(8, insertInfo.targetValue);
					insertSpec.setBigDecimal(9, insertInfo.standardUpperLimit);
					insertSpec.setBigDecimal(10, insertInfo.standardLowerLimit);
					insertSpec.setBigDecimal(11, insertInfo.acceptableUpperLimit);
					insertSpec.setBigDecimal(12, insertInfo.acceptableLowerLimit);
					insertSpec.setInt(13, insertInfo.specificationDate.intValue());
					insertSpec.setInt(14, insertInfo.supersededDate.intValue());					
					
					if (insertInfo.certificateOfAnalysis)								
						insertSpec.setString(15, "Y");
					else
						insertSpec.setString(15, "N");
						
					insertSpec.setString(16, insertInfo.updateAnalUser.toUpperCase());
					insertSpec.setString(17, insertInfo.updateAnalWorkstation.toUpperCase());
					insertSpec.setInt(18, insertInfo.updateAnalDate.intValue());
					insertSpec.setInt(19, insertInfo.updateAnalTime.intValue());
					insertSpec.setString(20, insertInfo.attributeComment);
					insertSpec.setInt(21, insertInfo.requiredLevelSequence.intValue());
					insertSpec.setString(22, insertInfo.attributeGroup1);
					insertSpec.setString(23, insertInfo.attributeGroup2);
					insertSpec.setString(24, insertInfo.attributeGroup3);
								
					insertSpec.executeUpdate();

					sqlInsertSpec.push(insertSpec);	
				}	
			}							
		}		
		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data." +
							   "SpecificationAnalytical.insert(Vector): " + e);
			return e;
		}
		
		return null;	
	}
	
	/**
	 * Load fields from data base record.
	 *
	 * Creation date: (3/16/2005 2:26:05 PM)
	 */
	protected void loadFields(ResultSet rs) {
			
		try {
			
			super.loadFields(rs);

			specificationCode		= rs.getString("IDVSPC");			
			specificationDate  		= new Integer(rs.getInt("IDVRVD"));
			supersededDate			= new Integer(rs.getInt("IDVSRV"));
			analyticalCode			= rs.getString("IDVCDE");
			attributeType			= rs.getString("IDVTYP");
			attributeSequence		= new Integer(rs.getInt("IDVSEQ"));
			targetValue				= rs.getBigDecimal("IDVDTA");
			standardUpperLimit		= rs.getBigDecimal("IDVSHI");
			standardLowerLimit		= rs.getBigDecimal("IDVSLO");
			acceptableUpperLimit	= rs.getBigDecimal("IDVAHI");
			acceptableLowerLimit	= rs.getBigDecimal("IDVALO");
			attributeComment		= rs.getString("IDVCMT");
			requiredLevelSequence	= new Integer(rs.getInt("IDVREQ"));
			marketNumber			= new Integer(rs.getInt("IDVBRK"));
			marketCustomer			= new Integer(rs.getInt("IDVCUS"));
			updateAnalUser			= rs.getString("IDVUUS");
			updateAnalUserName		= " ";
			updateAnalWorkstation   = rs.getString("IDVUWS");
			updateAnalDate			= new Integer(rs.getInt("IDVUDT"));
			updateAnalTime			= new Integer(rs.getInt("IDVUTM"));
						
			String certificate		= rs.getString("IDVCOA");
			if (certificate.equals ("Y"))
				certificateOfAnalysis = true;
			else
				certificateOfAnalysis = false;				
			
			int decimals = decimalPositions.intValue();	
			if (decimals > 0) {
				targetValue = SpecificationAnalytical.decimalPositionSet(targetValue, decimals);
				standardUpperLimit = SpecificationAnalytical.decimalPositionSet(standardUpperLimit, decimals);
				standardLowerLimit = SpecificationAnalytical.decimalPositionSet(standardLowerLimit, decimals);
				acceptableUpperLimit = SpecificationAnalytical.decimalPositionSet(acceptableUpperLimit, decimals);
				acceptableLowerLimit = SpecificationAnalytical.decimalPositionSet(acceptableLowerLimit, decimals);
			}
	
		}
		catch (Exception e) {	
			//System.out.println("SQL Exception at com.treetop.data.SpecificationAnalytical" +
							   //".loadFields(RS): " + e);
		}
					
	}

	/**
	 * Set the acceptable lower limit value.
	 *
	 * Creation date: (2/22/2005 2:42:27 PM)
	 */
	public void setAcceptableLowerLimit(BigDecimal inLowerLimit) {
		
		this.acceptableLowerLimit = inLowerLimit;
	}
	
	/**
	 * Set the acceptable upper limit value.
	 *
	 * Creation date: (2/22/2005 2:42:50 PM)
	 */
	public void setAcceptableUpperLimit(BigDecimal inUpperLimit) {
		
		this.acceptableUpperLimit = inUpperLimit;
	}
	
	/**
	 * Set the analytical attribute code.
	 *
	 * Creation date: (2/22/2005 3:11:22 PM)
	 */
	public void setAnalyticalCode(String inAnalyticalCode) {

		this.analyticalCode = inAnalyticalCode;
	}
	
	/**
	 * Set the attribute comment.
	 *
	 * Creation date: (2/22/2005 3:13:02 PM)
	 */
	public void setAttributeComment(String inAttributeComment) throws InvalidLengthException {
	
		if (inAttributeComment.length() > 500)
			throw new InvalidLengthException(
					"inAttributeComment", inAttributeComment.length(), 500);

		this.attributeComment = inAttributeComment;
	}
	
	/**
	 * Set the first attribute grouping code.
	 *
	 * Creation date: (6/27/2005 4:46:22 PM)
	 */
	public void setAttributeGroup1(String inAttributeGroup) throws InvalidLengthException {
	
		if (inAttributeGroup.length() > 2)
			throw new InvalidLengthException(
					"inAttributeGroup1", inAttributeGroup.length(), 2);

		this.attributeGroup1 = inAttributeGroup;
	}
	
	/**
	 * Set the second attribute grouping code.
	 *
	 * Creation date: (6/27/2005 4:46:32 PM)
	 */
	public void setAttributeGroup2(String inAttributeGroup) throws InvalidLengthException {
	
		if (inAttributeGroup.length() > 2)
			throw new InvalidLengthException(
					"inAttributeGroup2", inAttributeGroup.length(), 2);

		this.attributeGroup2 = inAttributeGroup;
	}
	
	/**
	 * Set the third attribute grouping code.
	 *
	 * Creation date: (6/27/2005 4:46:32 PM)
	 */
	public void setAttributeGroup3(String inAttributeGroup) throws InvalidLengthException {
	
		if (inAttributeGroup.length() > 2)
			throw new InvalidLengthException(
					"inAttributeGroup3", inAttributeGroup.length(), 2);

		this.attributeGroup3 = inAttributeGroup;
	}
	
	/**
	 * Set the attribute sequence number.
	 *
	 * Creation date: (2/22/2005 3:30:45 PM)
	 */
	public void setAttributeSequence(Integer inSequenceNumber) {
		
		this.attributeSequence = inSequenceNumber;
	}
	
	/**
	 * Set the certificate of analysis switch.
	 *
	 * Creation date: (2/22/2005 3:35:15 PM)
	 */
	public void setCertificateOfAnalysis(boolean inCertificateOfAnalysis) {
		
		this.certificateOfAnalysis = inCertificateOfAnalysis;
	}
	
	/**
	 * Set the customer number portion of the market/customer combination.
	 *
	 * Creation date: (4/06/2005 10:50:24 AM)
	 */
	public void setMarketCustomer(Integer inMarketCustomer) {
		
		this.marketCustomer = inMarketCustomer;
	}
	
	/**
	 * Set the market number.
	 *
	 * Creation date: (4/06/2005 10:52:11 AM)
	 */
	public void setMarketNumber(Integer inMarketNumber) {
		
		this.marketNumber = inMarketNumber;
	}
	
	/**
	 * Set the attribute required level sequence.
	 *
	 * Creation date: (3/21/2005 1:54:22 PM)
	 */
	public void setRequiredLevelSequence(Integer inRequiredLevelSequence) {
		
		this.requiredLevelSequence = inRequiredLevelSequence;
	}
	
	/**
	 * Set the specification type.
	 *
	 * Creation date: (4/06/2005 11:03:21 AM)
	 */
	public void setAttributeType(String inAttributeType) throws InvalidLengthException {
	
		if (inAttributeType.length() > 1)
			throw new InvalidLengthException(
					"inAttributeType", inAttributeType.length(), 1);

		this.attributeType = inAttributeType;
	}
	
	/**
	 * Set the specification Code.
	 *
	 * Creation date: (2/22/2005 3:47:00 PM)
	 */
	public void setSpecificationCode(String inSpecificationCode) throws InvalidLengthException {
	
		if (inSpecificationCode.length() > 5)
			throw new InvalidLengthException(
					"inSpecificationCode", inSpecificationCode.length(), 5);

		this.specificationCode = inSpecificationCode;
	}
	
	/**
	 * Set the specification Date.
	 *
	 * Creation date: (2/22/2005 3:45:45 PM)
	 */
	public void setSpecificationDate(String inSpecificationDate) {
		
		this.specificationDate = GetDate.formatSetDate(inSpecificationDate);
	}
	
	/**
	 * Set the standard lower limit value.
	 *
	 * Creation date: (2/22/2005 3:47:27 PM)
	 */
	public void setStandardLowerLimit(BigDecimal inLowerLimit) {
		
		this.standardLowerLimit = inLowerLimit;
	}
	
	/**
	 * Set the standard upper limit value.
	 *
	 * Creation date: (2/22/2005 3:47:50 PM)
	 */
	public void setStandardUpperLimit(BigDecimal inUpperLimit) {
		
		this.standardUpperLimit = inUpperLimit;
	}
	
	/**
	 * Set the superseded revision date.
	 *
	 * Creation date: (4/06/2005 10:48:24 PM)
	 */
	public void setSupersededDate(String inSupersededDate) {
		
		this.supersededDate = GetDate.formatSetDate(inSupersededDate);
	}
	
	/**
	 * Set the targeted value.
	 *
	 * Creation date: (2/22/2005 3:50:10 PM)
	 */
	public void setTargetValue(BigDecimal inTargetValue) {
		
		this.targetValue = inTargetValue;
	}
	
	/**
	 * Set the last update date. 
	 *
	 * Creation date: (2/22/2005 3:54:00 PM)
	 */
	public void setUpdateAnalDate(String inUpdateDate) {
		
		this.updateAnalDate = GetDate.formatSetDate(inUpdateDate);
	}
	
	/**
	 * Set the last update time. 
	 *
	 * Creation date: (2/22/2005 3:54:22 PM)
	 */
	public void setUpdateAnalTime(String inUpdateTime) {
		
		this.updateAnalTime = TimeUtilities.formatSetTime(inUpdateTime);
	}
	
	/**
	 * Set the last update user profile.
	 *
	 * Creation date: (2/22/2005 3:57:00 PM)
	 */
	public void setUpdateAnalUser(String inUpdateUser) throws InvalidLengthException {
	
		if (inUpdateUser.length() > 10)
			throw new InvalidLengthException(
					"inUpdateUser", inUpdateUser.length(), 10);

		this.updateAnalUser = inUpdateUser;
	}
	
	/**
	 * Set the last update user name.
	 *
	 * Creation date: (2/22/2005 3:59:14 PM)
	 */
	public void setUpdateAnalUserName(String inUpdateUserName) throws InvalidLengthException {
	
		if (inUpdateUserName.length() > 50)
			throw new InvalidLengthException(
					"inUpdateUserName", inUpdateUserName.length(), 50);

		this.updateAnalUserName = inUpdateUserName;
	}
	
	/**
	 * Set the last update workstation name.
	 *
	 * Creation date: (3/16/2005 2:48:59 PM)
	 */
	public void setUpdateAnalWorkstation(String inUpdateWorkstation) throws InvalidLengthException {
	
		if (inUpdateWorkstation.length() > 10)
			throw new InvalidLengthException(
					"inUpdateWorkstation", inUpdateWorkstation.length(), 10);

		this.updateAnalWorkstation = inUpdateWorkstation;
	}
	
	/**
	 * Update an analytical specification attribute.
	 *
	 * Creation date: (3/16/2005 8:45:15 AM)
	 */
	public Exception update(Vector inUpdateInfo) {
		
		try {	
					
			for (int x = 0; x < inUpdateInfo.size(); x ++) {				  	
			 
				SpecificationAnalytical updateInfo = (SpecificationAnalytical) 
													  inUpdateInfo.elementAt(x);
				                                      	  
				if ((updateInfo.specificationCode != null) && 		// Spec code required
				   (!updateInfo.specificationCode.equals(""))) {

					PreparedStatement updateSpec = (PreparedStatement) sqlUpdateSpec.pop();
		
					updateSpec.setString(1, "SA");
					updateSpec.setString(2, " ");					
					updateSpec.setInt(3, updateInfo.marketNumber.intValue());
					updateSpec.setInt(4, updateInfo.marketCustomer.intValue());
					updateSpec.setString(5, updateInfo.specificationCode);
					updateSpec.setString(6, updateInfo.analyticalCode);
					updateSpec.setInt(7, updateInfo.attributeSequence.intValue());
					updateSpec.setBigDecimal(8, updateInfo.targetValue);
					updateSpec.setBigDecimal(9, updateInfo.standardUpperLimit);
					updateSpec.setBigDecimal(10, updateInfo.standardLowerLimit);
					updateSpec.setBigDecimal(11, updateInfo.acceptableUpperLimit);
					updateSpec.setBigDecimal(12, updateInfo.acceptableLowerLimit);
					updateSpec.setInt(13, updateInfo.specificationDate.intValue());
					updateSpec.setInt(14, updateInfo.supersededDate.intValue());					
					
					if (updateInfo.certificateOfAnalysis)								
						updateSpec.setString(15, "Y");
					else
						updateSpec.setString(15, "N");
						
					updateSpec.setString(16, updateInfo.updateAnalUser.toUpperCase());
					updateSpec.setString(17, updateInfo.updateAnalWorkstation.toUpperCase());
					updateSpec.setInt(18, updateInfo.updateAnalDate.intValue());
					updateSpec.setInt(19, updateInfo.updateAnalTime.intValue());
					updateSpec.setString(20, updateInfo.attributeComment);
					updateSpec.setInt(21, updateInfo.requiredLevelSequence.intValue());
					updateSpec.setString(22, updateInfo.attributeGroup1);
					updateSpec.setString(23, updateInfo.attributeGroup2);
					updateSpec.setString(24, updateInfo.attributeGroup3);
								
					updateSpec.setString(25, updateInfo.specificationCode);
					updateSpec.setInt(26, updateInfo.specificationDate.intValue());
					updateSpec.setString(27, updateInfo.analyticalCode);
					updateSpec.executeUpdate();

					sqlUpdateSpec.push(updateSpec);		
				}
			}				
		}		
		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data." +
							   "SpecificationAnalytical.update(Vector): " + e);
			return e;
		}
		
		return null;	
	}	
	
}
