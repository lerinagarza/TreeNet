package com.treetop.data;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import javax.sql.*;
import com.treetop.*;
import com.treetop.utilities.ConnectionStack;
/**
 * This object contains general specification values, defining the header
 * type of information needed to establish a manufacturing specification.
 *
 * @author: David M Eisenheim
 *
 */
public class Specification {

	
	// Data base fields. (IDPTSPHD)
	
	private   	String        	specificationCode;			// IDTSPC
	protected	Integer			specificationDate;			// IDTRVD
	private		String			specificationType;			// IDTTYP
	private		Integer			supersededDate;				// IDTSRV
	private		boolean			currentVersion;				// IDTCRV
	private		boolean			deletedRecord;				// IDTDLT
	private		String			statusCode;					// IDTSTS
	private		Integer			marketNumber;				// IDTBRK
	private		Integer			marketCustomer;				// IDTCUS
	private		String			companyNumber;				// IDTCCO
	private		String			customerNumber;				// IDTCNO
	private		String			customerSpecification;		// IDTCSP
	private		String			generalDescription;			// IDTDSC
	private		String			resourceNumber;				// IDTRSC
	private		String			itemDescription;			//MMITDS - Movex Item Master
	private		Integer			plantNumber;				// IDTPLT
	private		String			referenceTextCode;			// IDTTR#
	private		String			referenceAnalCode;			// IDTDR#
	private		String			rawFruitSpecCode;			// IDTRFS
	private		Integer			rawFruitSpecDate;			// IDTRFD
	private		String			fruitVarietyEdit;			// IDTVEC
	private		String			fruitVariety;				// IDTVAR
	private		String[]		varietiesAcceptable;
	private		String[]		varietiesExcluded;
	private		String			wipClassification;			// IDTWIP
	private   	String        	updateSpecUser;				// IDTUUR				
	private   	String        	updateSpecUserName;
	private   	Integer		 	updateSpecDate;				// IDTUPD
	private   	Integer		 	updateSpecTime;				// IDTUTM
	private		String			updateSpecWorkstation;		// IDTUWS
	private   	String        	createSpecUser;				// IDTCUR				
	private   	String        	createSpecUserName;
	private   	Integer 		createSpecDate;				// IDTCRT
	private   	Integer		 	createSpecTime;				// IDTCTM
	private		String			createSpecWorkstation;		// IDTCWS							   			
	

	// Define database environment (live or test) on the AS/400.
	

	private static String library   = "DBPRD."; 	// live environment
	private static String movex     = "M3DJDPRD.";	// live environment
	//private static String financial = "ARDBFA.";	// live environment
//	private static String library   = "WKLIB."; 	// test environment


	// SQL prepared statements.

	private static Stack sqlDeleteSpec = null;
	private static Stack sqlInsertSpec = null;
	private static Stack sqlUpdateSpec = null;
	
	private static Stack findSpecByCodeByCurr = null;
	private static Stack findSpecByCodeByDate = null;
	private static Stack findSpecByAttribute  = null;
	
	//Exclude-Change to new AS400 12/23/08 wth
	//private static Stack findSpecForCompany  = null;
	//private static Stack findSpecForCustomer = null;
	//private static Stack findSpecForResource = null;
	
	
	// Additional fields.
	
	private static boolean persists   = false;
	
	private static int include = 10;
	private static int exclude = 5;


	/**
	 * Build drop down list for specification customer company numbers.
	 *
	 * Creation date: (03/31/2005 2:15:54 PM)
	 */
	public static String buildDropDownCompany(String inCompany, String inListName, 
										      String inFirstSelect) {		
	     
		String dropDownList = "";
		String selected     = "";
		String selectOption = "";
		
		try {
			
			if ((inListName == null) || (inListName.trim().equals ("")))
				inListName = "companyList";
			
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
	
				ResultSet rs = findSpecForCompany();

				try {

					while (rs.next()) 
					{
						String comp = rs.getString("IDTCCO");
						String desc = rs.getString("CONAME");     		 

						if ((inCompany != null) && (inCompany.trim().equals (comp.trim())))						
							selected = "' selected='selected'>";
						else
							selected = "'>";
		   		    
						dropDownList = dropDownList + "<option value='" + 
						comp.trim() + selected +
						comp.trim() + " " + desc.trim() + "&nbsp;";
					}
				
					if (!dropDownList.equals("")) {
					  
						if ((inFirstSelect != null) && (inFirstSelect.toLowerCase().equals ("none")))					
							dropDownList = "<select name='" + inListName.trim() + "'>" +
								       	   dropDownList + "</select>";					
						else					
							dropDownList = "<select name='" + inListName.trim() + "'>" +
								           "<option value='None' selected>" + selectOption +
								           dropDownList + "</select>";					  	 
					}
  	  		
				}
				catch (Exception e) {
					System.out.println("Exception error processing a result set " +
								       "(Specification.buildDropDownCompany): " + e);
				}	
		
				rs.close();	
		 
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
							       "(Specification.buildDropDownCompany): " + e);
			}
		
		}
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data." + 
							   "Specification.buildDropDownCompany(String String String): " + e);
		}	
	
		return dropDownList;  
	
	}
	
	/**
	 * Build drop down list for specification customer numbers.
	 *
	 * Creation date: (03/31/2005 1:55:33 PM)
	 */
	public static String buildDropDownCustomer(String inCustomer, String inListName, 
											   String inFirstSelect) {		
	     
		String dropDownList = "";
		String selected     = "";
		String selectOption = "";
		
		try {
			
			if ((inListName == null) || (inListName.trim().equals ("")))
				inListName = "customerList";
			
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
	
				ResultSet rs = findSpecForCustomer();

				try {

					while (rs.next()) 
					{
						String cust = rs.getString("IDTCNO");
						String desc = rs.getString("CUNAME");    		 

						if ((inCustomer != null) && (inCustomer.trim().equals (cust.trim())))						
							selected = "' selected='selected'>";
						else
							selected = "'>";
		   		    
						dropDownList = dropDownList + "<option value='" + 
						cust.trim() + selected +
						cust.trim() + " " + desc.trim() + "&nbsp;";
					}
				
					if (!dropDownList.equals("")) {
					  
						if ((inFirstSelect != null) && (inFirstSelect.toLowerCase().equals ("none")))					
							dropDownList = "<select name='" + inListName.trim() + "'>" +
										   dropDownList + "</select>";					
						else					
							dropDownList = "<select name='" + inListName.trim() + "'>" +
										   "<option value='None' selected>" + selectOption +
										   dropDownList + "</select>";					  	 
					}
  	  		
				}
				catch (Exception e) {
					System.out.println("Exception error processing a result set " +
								       "(Specification.buildDropDownCustomer): " + e);
				}	
		
				rs.close();	
		 
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
							       "(Specification.buildDropDownCustomer): " + e);
			}
		
		}
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data." + 
							   "Specification.buildDropDownCustomer(String String String): " + e);
		}	
	
		return dropDownList;  
	
	}
	
	/**
	 * Build drop down list for specification resource numbers.
	 *
	 * Creation date: (03/31/2005 1:54:24 PM)
	 */
	public static String buildDropDownResource(String inResource, String inListName, 
											   String inFirstSelect) {		
	     
		String dropDownList = "";
		String selected     = "";
		String selectOption = "";
		
		try {
			
			if ((inListName == null) || (inListName.trim().equals ("")))
				inListName = "resourceList";
			
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
	
				ResultSet rs = findSpecForResource();

				try {

					while (rs.next()) 
					{
						String resc = rs.getString("RMRESC");
						String desc = rs.getString("RMDESC");
						
						String item = resc.trim();						
						for (int x = item.length(); x < 10; x++) {
							item = item + "&nbsp;";
						}						

						if ((inResource != null) && (inResource.trim().equals (resc.trim())))						
							selected = "' selected='selected'>";
						else
							selected = "'>";
		   		    
						dropDownList = dropDownList + "<option value='" + 
						resc.trim() + selected +
						item + " " + desc.trim() + "&nbsp;";
					}
				
					if (!dropDownList.equals("")) {
					  
						if ((inFirstSelect != null) && (inFirstSelect.toLowerCase().equals ("none")))					
							dropDownList = "<select name='" + inListName.trim() + "'>" +
										   dropDownList + "</select>";					
						else					
							dropDownList = "<select name='" + inListName.trim() + "'>" +
										   "<option value='None' selected>" + selectOption +
										   dropDownList + "</select>";					  	 
					}
  	  		
				}
				catch (Exception e) {
					System.out.println("Exception error processing a result set " +
								       "(Specification.buildDropDownResource): " + e);
				}	
		
				rs.close();	
		 
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
							       "(Specification.buildDropDownResource): " + e);
			}
		
		}
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data." + 
							   "Specification.buildDropDownResource(String String String): " + e);
		}	
	
		return dropDownList;  
	
	}
	
	/**
	 * Process a result set into a vector of specification classes.
	 *
	 * Creation date: (3/29/2005 1:22:14 PM)
	 */
	public static Vector buildSpecFromFind(ResultSet inSpec) {

		Vector specList = new Vector();
	
		try {
			
			if (inSpec != null) {
	        
				while (inSpec.next()) {			
				
					Specification specHeader = new Specification();
				 	specHeader.loadFields(inSpec);
					specList.addElement(specHeader); 
				}
				
				inSpec.close();
			}        

		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set at com.treetop.data." + 
							   "Specification.buildSpecFromFind(ResultSet): " + e);
		}
		
		return specList;	                                     
	}
	
	/**
	 * Execute an SQL statement to retrieve all specifications which use a selected attribute.
	 *
	 * Creation date: (4/07/2005 3:22:24 PM)
	 */
	public static Vector findSpecByAttribute(SpecificationViewInquiry fromCriteria) {

		Vector        specList  = new Vector();
		Specification spec      = new Specification();
		String        sqlSelect = "";

		try {
			
			if ((fromCriteria.getAttributeCodes() != null) &&
				(fromCriteria.getAttributeCodes().size() > 0)) {  
			
				// Initial SQL statement for selecting specifications.    
	
				String sqlStatement = "SELECT *  " +
				  	   "FROM " + library + "IDPVSPDA " +	
				       "INNER JOIN " + library + "IDPTSPHD " +
							       "ON IDVSPC = IDTSPC AND IDVRVD = IDTRVD ";
							  
				// Build the "WHERE" SQL function for each attribute selected.
															
				if (fromCriteria.getAttributeCodes() != null)
					sqlSelect = sqlListAttribute(fromCriteria, sqlStatement);
					
				if (!sqlSelect.equals ("")) { 					
					sqlStatement = sqlStatement + sqlSelect;				
				
					// Add the sort sequence to the "ORDER BY" clause.	
			
					sqlStatement = sqlStatement + "ORDER BY IDVSPC, IDVRVD DESC";
											
					// Execute the SQL statement using a connection pool.
		
//					com.treetop.ConnectionPool connectionPool = new com.treetop.ConnectionPool();        
//					Connection conn = connectionPool.getConnection();    
	
//					Statement stmt = conn.createStatement();    
//					ResultSet rs   = stmt.executeQuery(sqlStatement);                
	     
//					connectionPool.returnConnection(conn);		    
			
					// Process the SQL result into the return data class vector elements.
              
			 		try {
	        
//						while (rs.next())
//				 		{
//							Specification specHeader = new Specification();
//				    		specHeader.loadFields(rs);
//				    		specList.addElement(specHeader); 
//				 		}		
						
			 		}
			 		catch (Exception e) {
						System.out.println("Exception error processing a result set " +
									       "(Specification.findSpecByAttribute): " + e);
			 		}

//			 		rs.close();
				}
			}        
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
					           "Specification.findSpecByAttribute(Class String): " + e);
		}
		
		return specList;	                                     
	}
	
	/**
	 * Execute an SQL statement to retrieve one specification using the code for
	 * the most current revision date.
	 *
	 * Creation date: (3/25/2005 11:14:41 AM)
	 */
	public static Vector findSpecByCodeByCurr(String inCode) {

		Vector        specList = new Vector();
		Specification spec     = new Specification();

		try {
			
			PreparedStatement specByCodeByCurr = (PreparedStatement) 
												  Specification.findSpecByCodeByCurr.pop();
			ResultSet rs = null;
		
			try {
				specByCodeByCurr.setString(1, inCode.toUpperCase());				
				rs = specByCodeByCurr.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(Specification.findSpecByCodeByCurr): " + e);
			}
		
			Specification.findSpecByCodeByCurr.push(specByCodeByCurr);        	

              
			try {
	        
				while (rs.next()) {			
				
					Specification specHeader = new Specification();
					specHeader.loadFields(rs);
					specList.addElement(specHeader); 
				}
        
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set " +
								   "(Specification.findSpecByCodeByCurr): " + e);
			}
			
			rs.close();        
        
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "Specification.findSpecByCodeByCurr(String): " + e);
		}
		
		return specList;	                                     
	}
	
	/**
	 * Execute an SQL statement to retrieve one specification using the code and date.
	 *
	 * Creation date: (3/25/2005 11:24:17 AM)
	 */
	public static Vector findSpecByCodeByDate(String inCode, Integer inDate) {

		Vector        specList = new Vector();
		Specification spec     = new Specification();

		try {
			
			PreparedStatement specByCodeByDate = (PreparedStatement) 
												  Specification.findSpecByCodeByDate.pop();
			ResultSet rs = null;
		
			try {
				specByCodeByDate.setString(1, inCode.toUpperCase());
				specByCodeByDate.setInt(2, inDate.intValue());				
				rs = specByCodeByDate.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(Specification.findSpecByCodeByDate): " + e);
			}
		
			Specification.findSpecByCodeByDate.push(specByCodeByDate);        	

              
			try {
	        
				while (rs.next()) {			
				
					Specification specHeader = new Specification();
					specHeader.loadFields(rs);
					specList.addElement(specHeader); 
				}
        
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set " +
								   "(Specification.findSpecByCodeByDate): " + e);
			}
			
			rs.close();            
        
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "Specification.findSpecByCodeByDate(String Integer): " + e);
		}
		
		return specList;	                                     
	}
	
	/**
	 * Execute an SQL statement to retrieve one specification using the code and date.
	 *
	 * Creation date: (3/25/2005 11:24:17 AM)
	 */
	public static Vector findSpecByCodeByDate(String inCode, String inDate) {

		Vector        specList = new Vector();
		Specification spec     = new Specification();

		try {
			
			PreparedStatement specByCodeByDate = (PreparedStatement) 
												  Specification.findSpecByCodeByDate.pop();
			ResultSet rs = null;
		
			try {
				Integer revisionDate = GetDate.formatSetDate(inDate);
				specByCodeByDate.setString(1, inCode.toUpperCase());
				specByCodeByDate.setInt(2, revisionDate.intValue());		
				rs = specByCodeByDate.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(Specification.findSpecByCodeByDate): " + e);
			}
		
			Specification.findSpecByCodeByDate.push(specByCodeByDate);        	

              
			try {
	        
				while (rs.next()) {			
				
					Specification specHeader = new Specification();
					specHeader.loadFields(rs);
					specList.addElement(specHeader); 
				}
        
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set " +
								   "(Specification.findSpecByCodeByDate): " + e);
			}
			
			rs.close();            
        
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "Specification.findSpecByCodeByDate(String String): " + e);
		}
		
		return specList;	                                     
	}
	
	/**
	 * Create an SQL statement to retrieve specifications based on the from/to 
	 * criteria selected.
	 *
	 * Creation date: (3/24/2005 10:31:25 AM)
	 */
	public static ResultSet findSpecByRange(SpecificationViewInquiry fromCriteria,
											SpecificationViewInquiry toCriteria) {
	
		ResultSet spec    = null;
		String    orderBy = ""; 
		Connection conn = null;
	
		try { //use finally to return conn.
		        	
			// Initial SQL statement for selecting specifications.  
	
			String sqlStatement = "SELECT * " + 
						   "FROM " + library + "IDPTSPHD ";				    
			    
			
			// Build left outer join files into the SQL statement.			
			
			sqlStatement = sqlStatement + sqlJoinCustomer(fromCriteria);				    
			sqlStatement = sqlStatement + sqlJoinItem(fromCriteria);
	            	           
			// Build the "WHERE" SQL function for each from/to data selection.
			
			if (fromCriteria.getSpecificationCode() != null) {
				if (toCriteria.getSpecificationCode() != null)
					sqlStatement = sqlStatement + sqlRangeSpecCode(fromCriteria, toCriteria,
																   sqlStatement);
				else
					sqlStatement = sqlStatement + sqlSingleSpecCode(fromCriteria, sqlStatement);
			}
			if (fromCriteria.getGeneralDescription() != null) {
				sqlStatement = sqlStatement + sqlSingleSpecDescription(fromCriteria, sqlStatement);
			}
			
			if (fromCriteria.getSpecificationType() != null) {
				if (toCriteria.getSpecificationType() != null)
					sqlStatement = sqlStatement + sqlRangeSpecType(fromCriteria, toCriteria,
																   sqlStatement);
				else
					sqlStatement = sqlStatement + sqlSingleSpecType(fromCriteria, sqlStatement);
			}
			if (fromCriteria.getResourceNumber() != null) {
				if (toCriteria.getResourceNumber() != null)
					sqlStatement = sqlStatement + sqlRangeResource(fromCriteria, toCriteria,
																   sqlStatement);
				else
					sqlStatement = sqlStatement + sqlSingleResource(fromCriteria, sqlStatement);
			}
			if (fromCriteria.getCompanyNumber() != null) {
				if (toCriteria.getCompanyNumber() != null)
					sqlStatement = sqlStatement + sqlRangeCustomer(fromCriteria, toCriteria,
																   sqlStatement);
				else
					sqlStatement = sqlStatement + sqlSingleCustomer(fromCriteria, sqlStatement);
			}
			if (fromCriteria.getCustomerNumber() != null) {
				if (toCriteria.getCustomerNumber() != null)
					sqlStatement = sqlStatement + sqlRangeCustomer(fromCriteria, toCriteria,
																   sqlStatement);
				else
					sqlStatement = sqlStatement + sqlSingleCustomer(fromCriteria, sqlStatement);
			}
			if (fromCriteria.getFruitVarietyList() != null) {
			if (fromCriteria.getFruitVariety() != null) {
				if (toCriteria.getFruitVariety() != null)
					sqlStatement = sqlStatement + sqlRangeVariety(fromCriteria, toCriteria,
																  sqlStatement);
				else
					sqlStatement = sqlStatement + sqlSingleVariety(fromCriteria, sqlStatement);
			}
			}
			
			
			// Build the "WHERE" SQL function for each single drop down option selected.
															
			if (fromCriteria.getStatusCode() != null) 					
				sqlStatement = sqlStatement + sqlSingleSpecStatus(fromCriteria, sqlStatement);
			if (fromCriteria.getCustomerStatus() != null) 					
				sqlStatement = sqlStatement + sqlSingleCustStatus(fromCriteria, sqlStatement);
			if (fromCriteria.getShowAllRevisions() != null) 					
				sqlStatement = sqlStatement + sqlSingleRevisions(fromCriteria, sqlStatement);
				
				
			// Build the "WHERE" SQL function for drop down selection by attribute.
				
			if ((fromCriteria.getAttributeCodes() != null) &&
				(fromCriteria.getAttributeCodes().size() > 0)) 
				sqlStatement = sqlStatement + sqlSingleAttribute(fromCriteria, sqlStatement);							
						
		
			// Add the sort sequence to the "ORDER BY" clause.
	
			orderBy      = sqlOrderBy(fromCriteria.getOrderByField(), 
									  fromCriteria.getOrderByStyle());
			sqlStatement = sqlStatement + orderBy;                   
	
	
			// Execute the SQL statement using a connection pool.
		
			conn = ConnectionStack.getConnection();
			
			try
			{
			   Statement stmt  = conn.createStatement();    
			             spec  = stmt.executeQuery(sqlStatement);
			             //stmt.close();
			}
			catch(Exception e)
			{
				String stop = "now";
			}
			
			ConnectionStack.returnConnection(conn);
			
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "Specification.findSpecByRange(Class Class): " + e);
		} finally {
			
		}
		
		return spec;	                                     
	}
	
	/**
	 * Execute an SQL statement to retrieve each unique company number used
	 * in the specification headers.
	 *
	 * Creation date: (3/31/2005 1:33:18 PM)
	 * Exclude-Change to new AS400 12/23/08 wth
	 */
	public static ResultSet findSpecForCompany() {

		ResultSet     companyList  = null;
		Specification specification = new Specification();

		try {
			
			//PreparedStatement specForCompany = (PreparedStatement) 
												//Specification.findSpecForCompany.pop();			
		
			try {
			
				//companyList = specForCompany.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(Specification.findSpecForCompany): " + e);
				try {
					
					companyList.close();
				}
				catch (Exception x) {
				}
			}
		
			//Specification.findSpecForCompany.push(specForCompany);
 
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "Specification.findSpecForCompany(): " + e);
		}
		
		return companyList;	                                     
	}
	
	/**
	 * Execute an SQL statement to retrieve each unique customer number used
	 * in the specification headers.
	 *
	 * Creation date: (3/31/2005 1:35:26 PM)
	 * Exclude-Change to new AS400 12/23/08 wth
	 */
	public static ResultSet findSpecForCustomer() {

		ResultSet     customerList  = null;
		Specification specification = new Specification();

		try {
			
			//PreparedStatement specForCustomer = (PreparedStatement) 
												 //Specification.findSpecForCustomer.pop();			
		
			try {
			
				//customerList = specForCustomer.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(Specification.findSpecForCustomer): " + e);
				try {
					
					customerList.close();
				}
				catch (Exception x) {
				}
			}
		
			//Specification.findSpecForCustomer.push(specForCustomer);
 
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "Specification.findSpecForCustomer(): " + e);
		}
		
		return customerList;	                                     
	}
	
	/**
	 * Execute an SQL statement to retrieve each unique resource number used
	 * in the specification headers.
	 *
	 * Creation date: (3/31/2005 11:33:14 AM)
	 * Exclude-Change to new AS400 12/23/08 wth
	 */
	public static ResultSet findSpecForResource() {

		ResultSet     resourceList  = null;
		Specification specification = new Specification();

		try {
			
			//PreparedStatement specForResource = (PreparedStatement) 
												 //Specification.findSpecForResource.pop();			
		
			try {
			
				//resourceList = specForResource.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(Specification.findSpecForResource): " + e);
				try {
					
					resourceList.close();
				}
				catch (Exception x) {
				}
			}
		
			//Specification.findSpecForResource.push(specForResource);
 
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "Specification.findSpecForResource(): " + e);
		}
		
		return resourceList;	                                     
	}	

	/**
	 * Create SQL join statement for the customer masters.
	 *
	 * Creation date: (3/28/2005 9:33:05 AM)
	 * Modified date: (12/23/2008) wth
	 */
	public static String sqlJoinCustomer(SpecificationViewInquiry fromCriteria) {

		String sqlJoin = "";
		String lawson  = "M3DJDPRD.";	
		
		String sqlJoinCustomer   = "LEFT OUTER JOIN " + lawson + "OCUSMA " + 
								   "ON SUBSTRING(IDTCNO,10,5) = OKCUNO ";
		
		try {			
			
			sqlJoin = sqlJoinCustomer;			
						
		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlJoinCustomer(SpecificationViewInquiry): " + e);								
		}	
	
		return sqlJoin;	
	}
	
	/**
	 * Create SQL statement to select a list of attributes requested.
	 *
	 * Creation date: (4/07/2005 3:31:37 PM)
	 */
	public static String sqlListAttribute(SpecificationViewInquiry fromCriteria, String sqlStatement) {

		String  sqlSelect  = "";
		boolean priorWhere = false;	
	
		try {		

			if ((fromCriteria.getAttributeCodes() != null) &&
				(fromCriteria.getAttributeCodes().size() > 0)) {   
				
				int where = sqlStatement.indexOf("WHERE");		        
				if (where >= 0)
					sqlSelect = sqlSelect + "AND ";
				else	
					sqlSelect = sqlSelect + "WHERE ";
					
				for (int x = 0; x < fromCriteria.getAttributeCodes().size(); x ++) {			 
					String attribute = (String) fromCriteria.getAttributeCodes().elementAt(x);
					
					if ((attribute != null) && (!attribute.trim().equals (""))) {
						
						if (priorWhere)
							sqlSelect = sqlSelect + "AND ";
															
						sqlSelect = sqlSelect + "(IDVCDE = '" + attribute.toUpperCase() + "') ";
						priorWhere = true;					
					}						
				}
				
				if (!priorWhere)			// No attributes processed
					sqlSelect = "";		
			}
		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlListAttribute(Class String): " + e);
		}	
	
		return sqlSelect;	
	}
	
	/**
	 * Build the SQL order by (sort).
	 *
	 * Creation date: (3/24/2005 3:44:24 PM)
	 */
	public static String sqlOrderBy(String inSortField, String inSortStyle) {

		String orderBySql = "ORDER BY ";
		String orderBy    = "";
		String orderStyle = "";		
	
		try {

			if (inSortField != null) {
		
				if ((inSortStyle == null) || (inSortStyle.equals("")))
					orderStyle = orderStyle + ", ";
				else
					orderStyle = orderStyle + " " + inSortStyle.toUpperCase() + ", ";		

				if (inSortField.toLowerCase().equals("specificationcode"))
					orderBy = orderBy + orderBySql + "IDTSPC" + orderStyle + "IDTRVD DESC";
				if (inSortField.toLowerCase().equals("specificationdate"))
					orderBy = orderBy + orderBySql + "IDTRVD" + orderStyle + "IDTSPC";
				if (inSortField.toLowerCase().equals("specificationtype"))
					orderBy = orderBy + orderBySql + "IDTTYP" + orderStyle + "IDTSPC, IDTRVD DESC";
				if (inSortField.toLowerCase().equals("resourcenumber"))
					orderBy = orderBy + orderBySql + "IDTRSC" + orderStyle + "IDTSPC, IDTRVD DESC";
				if (inSortField.toLowerCase().equals("companynumber"))
					orderBy = orderBy + orderBySql + "IDTCCO" + orderStyle + "IDTSPC, IDTRVD DESC";
				if (inSortField.toLowerCase().equals("customernumber"))
					orderBy = orderBy + orderBySql + "IDTCNO" + orderStyle + "IDTSPC, IDTRVD DESC";
				if (inSortField.toLowerCase().equals("customername"))
					orderBy = orderBy + orderBySql + "CUNAME" + orderStyle + "IDTSPC, IDTRVD DESC";
				if (inSortField.toLowerCase().equals("generaldescription"))
					orderBy = orderBy + orderBySql + "IDTDSC" + orderStyle + "IDTSPC, IDTRVD DESC";
				if (inSortField.toLowerCase().equals("customerspecification"))
					orderBy = orderBy + orderBySql + "IDTCSP" + orderStyle + "IDTSPC, IDTRVD DESC";
				if (inSortField.toLowerCase().equals("marketnumber"))
					orderBy = orderBy + orderBySql + "IDTBRK" + orderStyle + "IDTSPC, IDTRVD DESC";
				if (inSortField.toLowerCase().equals("marketcustomer"))
					orderBy = orderBy + orderBySql + "IDTCUS" + orderStyle + "IDTSPC, IDTRVD DESC";											
			} 
				       
			if (orderBy.equals(""))
				orderBy = orderBySql + "IDTSPC, IDTRVD DESC";  
				
		}	 
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data.Specification." +
							   "sqlOrderBy(String String): " + e);
		}	
			
		return orderBy; 
	}
	
	/**
	 * Create SQL statement to select a range of customer numbers (includes company number).
	 *
	 * Creation date: (3/25/2005 8:34:05 AM)
	 */
	public static String sqlRangeCustomer(SpecificationViewInquiry fromCriteria,
										  SpecificationViewInquiry toCriteria, 
										  String sqlStatement) {
		String  sqlSelect = "";

		try {
			// Customer and Company work differently in the new system... 
			// Commenting out until review can be done. TWalton - 2/2/09
			
			// Process COMPANY number.			

//			if ((fromCriteria.getCompanyNumber() != null) && 
//				(toCriteria.getCompanyNumber() != null)) {
			    
//				int where = sqlStatement.indexOf("WHERE");		        
//				if (where >= 0)
//					sqlSelect = sqlSelect + "AND ";
//				else	
//					sqlSelect = sqlSelect + "WHERE ";				
//				
//				String companyFrom = CustomerBillTo.buildCompanyNumber(fromCriteria.getCompanyNumber());
//				String companyTo   = CustomerBillTo.buildCompanyNumber(toCriteria.getCompanyNumber());		
//				sqlSelect = sqlSelect + "(IDTCCO BETWEEN '" + companyFrom + "' AND '" + companyTo + "') ";               
//			}
			
			// Process CUSTOMER number.	
			
//			if ((fromCriteria.getCustomerNumber() != null) && 
//				(toCriteria.getCustomerNumber() != null)) {
//			    
//				int where = sqlStatement.indexOf("WHERE");		        
//				if (where >= 0)
//					sqlSelect = sqlSelect + "AND ";
//				else	
//					sqlSelect = sqlSelect + "WHERE ";					
//				
//				String customerFrom = CustomerBillTo.buildCustomerNumber(fromCriteria.getCustomerNumber());
//				String customerTo   = CustomerBillTo.buildCustomerNumber(toCriteria.getCustomerNumber());	
//				sqlSelect = sqlSelect + "(IDTCNO BETWEEN '" + customerFrom + "' AND '" + customerTo + "') ";
//			}

		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlRangeCustomer" +
                               "(Class Class String): " + e);
		}	
	
		return sqlSelect;	
	}
	
	/**
	 * Create SQL statement to select a range of resource numbers.
	 *
	 * Creation date: (3/25/2005 8:17:16 AM)
	 */
	public static String sqlRangeResource(SpecificationViewInquiry fromCriteria,
										  SpecificationViewInquiry toCriteria, 
										  String sqlStatement) {
		String  sqlSelect = "";

		try {		

			if ((fromCriteria.getResourceNumber() != null) && 
				(toCriteria.getResourceNumber() != null)) {
			    
				int where = sqlStatement.indexOf("WHERE");		        
				if (where >= 0)
					sqlSelect = sqlSelect + "AND ";
				else	
					sqlSelect = sqlSelect + "WHERE ";				
				
				sqlSelect = sqlSelect + "(IDTRSC BETWEEN '" + fromCriteria.getResourceNumber() + 
                                        "' AND '" + toCriteria.getResourceNumber() + "') ";
			}

		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlRangeResource" + 
                               "(Class Class String): " + e);
		}	
	
		return sqlSelect;	
	}
	
	/**	
	 * Create SQL statement to select a range of specification codes.
	 *
	 * Creation date: (3/25/2005 8:24:29 AM)
	 */
	public static String sqlRangeSpecCode(SpecificationViewInquiry fromCriteria,
										  SpecificationViewInquiry toCriteria, 
										  String sqlStatement) {
		String  sqlSelect = "";

		try {		

			if ((fromCriteria.getSpecificationCode() != null) && 
				(toCriteria.getSpecificationCode() != null)) {
			    
				int where = sqlStatement.indexOf("WHERE");		        
				if (where >= 0)
					sqlSelect = sqlSelect + "AND ";
				else	
					sqlSelect = sqlSelect + "WHERE ";
					
				String fromCode = fromCriteria.getSpecificationCode();
				String toCode   = toCriteria.getSpecificationCode();
				
				int f = fromCode.indexOf("%");
				if (f >= 0)
					fromCode = fromCode.substring(0,f);
				int t = toCode.indexOf("%");
				if (t >= 0)
					toCode = toCode.substring(0,t);						
				
				sqlSelect = sqlSelect + "(IDTSPC BETWEEN '" + fromCode + "' AND '" + toCode + "') ";
			}

		}
		catch (Exception e) {
			System.out.println("Exception error Specification." + 
                               "sqlRangeSpecCode(Class Class String): " + e);
		}	
	
		return sqlSelect;	
	}
		
	/**
	 * Create SQL statement to select a range of specification types.
	 *
	 * Creation date: (3/25/2005 4:10:17 AM)
	 */
	public static String sqlRangeSpecType(SpecificationViewInquiry fromCriteria,
									  SpecificationViewInquiry toCriteria, 
									  String sqlStatement) {
		String  sqlSelect = "";

		try {		

			if ((fromCriteria.getSpecificationType() != null) && 
				(toCriteria.getSpecificationType() != null)) {
			    
				int where = sqlStatement.indexOf("WHERE");		        
				if (where >= 0)
					sqlSelect = sqlSelect + "AND ";
				else	
					sqlSelect = sqlSelect + "WHERE ";				
				
				sqlSelect = sqlSelect + "(IDTTYP BETWEEN '" + fromCriteria.getSpecificationType() + 
										"' AND '" + toCriteria.getSpecificationType() + "') ";
			}

		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlRangeSpecType" + 
							   "(Class Class String): " + e);
		}	
	
		return sqlSelect;	
	}
		
	/**
	 * Create SQL statement to select a range of fruit varieties.
	 *
	 * Creation date: (3/25/2005 8:58:33 AM)
	 */
	public static String sqlRangeVariety(SpecificationViewInquiry fromCriteria,
										 SpecificationViewInquiry toCriteria, String sqlStatement) {
		String  sqlSelect = "";

		try {		

			if ((fromCriteria.getFruitVariety() != null) && 
				(toCriteria.getFruitVariety() != null)) {
			if (fromCriteria.getFruitVarietyList() != null) {
			    
				int where = sqlStatement.indexOf("WHERE");		        
				if (where >= 0)
					sqlSelect = sqlSelect + "AND ";
				else	
					sqlSelect = sqlSelect + "WHERE ";				
				
				if (fromCriteria.getFruitVarietyList().toLowerCase().trim().equals ("all")) {
					sqlSelect = sqlSelect + "(";	
					sqlSelect = sqlSelect + "(IDTVAR BETWEEN '" + fromCriteria.getFruitVariety() + 
										    "' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCA BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCB BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCC BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCD BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCE BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCF BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCG BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCH BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCI BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCJ BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTVA1 BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTVA2 BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTVA3 BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTVA4 BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTVA5 BETWEEN '" + fromCriteria.getFruitVariety() + 
						                    "' AND '" + toCriteria.getFruitVariety() + "')";
					sqlSelect = sqlSelect + ") ";
				}
				if (fromCriteria.getFruitVarietyList().toLowerCase().trim().equals ("primary")) {
					sqlSelect = sqlSelect + "(IDTVAR BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') ";
				}
				if (fromCriteria.getFruitVarietyList().toLowerCase().trim().equals ("included")) {
					sqlSelect = sqlSelect + "(";
					sqlSelect = sqlSelect + "(IDTSCA BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCB BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCC BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCD BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCE BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCF BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCG BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCH BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCI BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTSCJ BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "')";
					sqlSelect = sqlSelect + ") ";
				}
				if (fromCriteria.getFruitVarietyList().toLowerCase().trim().equals ("excluded")) {
					sqlSelect = sqlSelect + "(";
					sqlSelect = sqlSelect + "(IDTVA1 BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTVA2 BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTVA3 BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTVA4 BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "') OR ";
					sqlSelect = sqlSelect + "(IDTVA5 BETWEEN '" + fromCriteria.getFruitVariety() + 
											"' AND '" + toCriteria.getFruitVariety() + "')";
					sqlSelect = sqlSelect + ") ";
				}
			}
			}

		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlRangeVariety" + 
                               "(Class Class String): " + e);
		}	
	
		return sqlSelect;	
	}
	
	/**
	 * Create SQL statement to select each attribute requested.
	 *
	 * Creation date: (4/18/2005 2:46:12 PM)
	 */
	public static String sqlSingleAttribute(SpecificationViewInquiry fromCriteria, String sqlStatement) {

		String  sqlSelect  = "";
		boolean priorWhere = false;	
	
		try {		

			if ((fromCriteria.getAttributeCodes() != null) &&
				(fromCriteria.getAttributeCodes().size() > 0)) {   
				
				int where = sqlStatement.indexOf("WHERE");		        
				if (where >= 0)
					sqlSelect = sqlSelect + "AND ";
				else	
					sqlSelect = sqlSelect + "WHERE ";					
				
				for (int x = 0; x < fromCriteria.getAttributeCodes().size(); x ++) {			 
					String attribute = (String) fromCriteria.getAttributeCodes().elementAt(x);
					
					if ((attribute != null) && (!attribute.trim().equals (""))) {						
											
						if (priorWhere)
							sqlSelect = sqlSelect + "AND ";
							
						sqlSelect = sqlSelect + "(IDTSPC CONCAT CHAR(IDTRVD) IN " +
												"(SELECT IDVSPC CONCAT CHAR(IDVRVD) " +
													 	"FROM " + library + "IDPVSPDA WHERE ";	
															
						sqlSelect = sqlSelect + "IDVCDE = '" + attribute.toUpperCase() + "')) ";
						priorWhere = true;					
					}						
				}				
						
				if (!priorWhere)									// No attributes processed
					sqlSelect = "";		
			}
		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlSingleAttribute(Class String): " + e);
		}	
	
		return sqlSelect;	
	}
	
	/**
	 * Create SQL statement to select a single customer number (includes company number).
	 *
	 * Creation date: (3/25/2005 8:46:11 AM)
	 */
	public static String sqlSingleCustomer(SpecificationViewInquiry fromCriteria,
	                                       String sqlStatement) {

		String sqlSelect = "";	
	
		try {	
			// Company works differently on new machine
			// Comment out until Review can be done TWalton 2/2/09
			
			// Process COMPANY number.	

//			if (fromCriteria.getCompanyNumber() != null) {
				
//				int where = sqlStatement.indexOf("WHERE");		        
//				if (where >= 0)
//					sqlSelect = sqlSelect + "AND ";
//				else	
//					sqlSelect = sqlSelect + "WHERE ";		
				
//				String company = CustomerBillTo.buildCompanyNumber(fromCriteria.getCompanyNumber());	
//				sqlSelect = sqlSelect + "(IDTCCO = '" + company + "') ";					
//			}
			
			// Process CUSTOMER number.
			
//			if (fromCriteria.getCustomerNumber() != null) {
				
//				int where = sqlStatement.indexOf("WHERE");		        
//				if (where >= 0)
//					sqlSelect = sqlSelect + "AND ";
//				else	
//					sqlSelect = sqlSelect + "WHERE ";					
				
//				String customer = CustomerBillTo.buildCustomerNumber(fromCriteria.getCustomerNumber());
//				sqlSelect = sqlSelect + "(IDTCNO = '" + customer + "') ";			
//			}

		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlSingleCustomer" + 
                               "(Class String): " + e);
		}	
	
		return sqlSelect;	
	}
	
	/**
	 * Create SQL statement to for selection of customer records based on customer status.
	 *
	 * Creation date: (3/28/2005 8:36:46 AM)
	 */
	public static String sqlSingleCustStatus(SpecificationViewInquiry fromCriteria,
										     String sqlStatement) {

		String sqlSelect = "";	
	
		try {		

			if (fromCriteria.getCustomerStatus() != null) {
				
				if (!fromCriteria.getCustomerStatus().toLowerCase().trim().equals ("all")) {
					
					int where = sqlStatement.indexOf("WHERE");		        
					if (where >= 0)
						sqlSelect = sqlSelect + "AND ";
					else	
						sqlSelect = sqlSelect + "WHERE ";					
					 	
					if (fromCriteria.getCustomerStatus().toLowerCase().trim().equals ("active")) 				
						sqlSelect = sqlSelect + "(OKSTAT = '20') ";						
					if (fromCriteria.getCustomerStatus().toLowerCase().trim().equals ("deleted")) 	
						sqlSelect = sqlSelect + "(OKSTAT = '90') ";	
					if (fromCriteria.getCustomerStatus().toLowerCase().trim().equals ("inactive")) 	
						sqlSelect = sqlSelect + "(OKSTAT = '10') ";	
				}									
									
			}			

		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlSingleCustStatus" + 
                               "(Class, String): " + e);
		}	
	
		return sqlSelect;	
	}
	
	/**
	 * Create SQL statement to select a single resource number.
	 *
	 * Creation date: (3/25/2005 8:26:50 AM)
	 */
	public static String sqlSingleResource(SpecificationViewInquiry fromCriteria,
	                                       String sqlStatement) {

		String sqlSelect = "";	
	
		try {		

			if (fromCriteria.getResourceNumber() != null) {
				
				int where = sqlStatement.indexOf("WHERE");		        
				if (where >= 0)
					sqlSelect = sqlSelect + "AND ";
				else	
					sqlSelect = sqlSelect + "WHERE ";		
					
				sqlSelect = sqlSelect + "(IDTRSC = '" + 
										fromCriteria.getResourceNumber() + "') ";		
			}

		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlSingleResource" + 
                               "(Class String): " + e);
		}	
	
		return sqlSelect;	
	}
	
	/**
	 * Create SQL statement to for selection of the most current revision date or all
	 * revisions dates for the specification.
	 *
	 * Creation date: (3/28/2005 8:47:25 AM)
	 */
	public static String sqlSingleRevisions(SpecificationViewInquiry fromCriteria, 
	                                        String sqlStatement) {

		String sqlSelect = "";	
	
		try {		

			if ((fromCriteria.getShowAllRevisions() != null) && 
			   (!fromCriteria.getShowAllRevisions().toLowerCase().trim().equals ("all"))) {
				
				int where = sqlStatement.indexOf("WHERE");		        
				if (where >= 0)
					sqlSelect = sqlSelect + "AND ";
				else	
					sqlSelect = sqlSelect + "WHERE ";					
					 	
				if (fromCriteria.getShowAllRevisions().toLowerCase().trim()
					                                  .equals ("current revision only")) 				
					sqlSelect = sqlSelect + "(IDTCRV = 1) ";						
				if (fromCriteria.getShowAllRevisions().toLowerCase().trim()
					                                  .equals ("non-current revisions")) 	
					sqlSelect = sqlSelect + "(IDTCRV = 0) ";
				if (fromCriteria.getShowAllRevisions().toLowerCase().trim()
													  .equals ("show summary")) 				
					sqlSelect = sqlSelect + "(IDTCRV = 1) ";																		
												
			}			

		}
		catch (Exception e) {
			System.out.println("Exception error Specification." + 
                               "sqlSingleRevisions(Class String): " + e);
		}	
	
		return sqlSelect;	
	}
	
	/**
	 * Create SQL statement to select a single specification code.
	 *
	 * Creation date: (3/24/2005 11:50:24 AM)
	 */
	public static String sqlSingleSpecCode(SpecificationViewInquiry fromCriteria,
										   String sqlStatement) {
	
		String sqlSelect = "";	
	
		try {		
	
			if (fromCriteria.getSpecificationCode() != null) {
				
				int where = sqlStatement.indexOf("WHERE");		        
				if (where >= 0)
					sqlSelect = sqlSelect + "AND ";
				else	
					sqlSelect = sqlSelect + "WHERE ";				
				
				String specCode = fromCriteria.getSpecificationCode();
				
				if (fromCriteria.getSpecificationDate() !=null)
					sqlSelect = sqlSelect + "(IDTSPC = '" + specCode + "') ";					
				
				else {
					specCode = "%" + specCode + "%";						
					sqlSelect = sqlSelect + "(IDTSPC LIKE '" + specCode + "') ";	 
				}
						
			}
	
		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlSingleSpecCode" + 
							   "(Class String): " + e);
		}	
	
		return sqlSelect;	
	}
		                                     
	/**
	 * Create SQL statement to select a single specification description.
	 *
	 * Creation date: (6/2/2005 10:08:24 AM) TW
	 */
	public static String sqlSingleSpecDescription(SpecificationViewInquiry fromCriteria,
	                                              String sqlStatement) {
	
		String sqlSelect = "";	
	
		try {		
	
			if (fromCriteria.getGeneralDescription() != null) {
				
				int where = sqlStatement.indexOf("WHERE");		        
				if (where >= 0)
					sqlSelect = sqlSelect + "AND ";
				else	
					sqlSelect = sqlSelect + "WHERE ";				
				
				String specDesc = fromCriteria.getGeneralDescription();
				
				if (fromCriteria.getGeneralDescription() != null)
				{
					specDesc = "%" + specDesc.trim() + "%";						
					sqlSelect = sqlSelect + "(IDTDSC LIKE '" + specDesc.trim().toUpperCase() + "') ";	 
				}
						
			}
	
		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlSingleSpecDescription" + 
	                           "(Class String): " + e);
		}	
	
		return sqlSelect;	
	}
	
	/**
	 * Create SQL statement to select a single specification status.
	 *
	 * Creation date: (3/25/2005 9:00:45 AM)
	 */
	public static String sqlSingleSpecStatus(SpecificationViewInquiry fromCriteria, 
	                                         String sqlStatement) {

		String sqlSelect = "";

		try {		

			if ((fromCriteria.getStatusCode() != null) &&
			   (!fromCriteria.getStatusCode().toLowerCase().trim().equals ("all"))) {	
				
				int where = sqlStatement.indexOf("WHERE");		        
				if (where >= 0)
					sqlSelect = sqlSelect + "AND ";
				else	
					sqlSelect = sqlSelect + "WHERE ";					
				
				if (fromCriteria.getStatusCode().toLowerCase().trim().equals ("closed")) 				
					sqlSelect = sqlSelect + "(IDTSTS = 'CO') ";					
				if (fromCriteria.getStatusCode().toLowerCase().trim().equals ("open")) 	
					sqlSelect = sqlSelect + "(IDTSTS = 'OP') ";								
			}
			
		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlSingleSpecStatus" + 
                               "(Class String): " + e);
		}	
	
		return sqlSelect;	
	}
	
	/**
	 * Create SQL statement to select a single specification type.
	 *
	 * Creation date: (3/25/2005 4:01:26 PM)
	 */
	public static String sqlSingleSpecType(SpecificationViewInquiry fromCriteria, String sqlStatement) {

		String sqlSelect = "";	
	
		try {		

			if (fromCriteria.getSpecificationType() != null) {
				
				int where = sqlStatement.indexOf("WHERE");		        
				if (where >= 0)
					sqlSelect = sqlSelect + "AND ";
				else	
					sqlSelect = sqlSelect + "WHERE ";		
					
				sqlSelect = sqlSelect + "(IDTTYP = '" + 
										fromCriteria.getSpecificationType() + "') ";		
			}

		}
		catch (Exception e) {
			System.out.println("Exception error Specification." + 
							   "sqlSingleSpecType(Class String): " + e);
		}	
	
		return sqlSelect;	
	}	
	
	/**
	 * Create SQL statement to select a single fruit variety.
	 *
	 * Creation date: (3/25/2005 9:00:45 AM)
	 */
	public static String sqlSingleVariety(SpecificationViewInquiry fromCriteria, 
										  String sqlStatement) {

		String sqlSelect = "";	
	
		try {		

			if (fromCriteria.getFruitVariety() != null) {
			if (fromCriteria.getFruitVarietyList() != null) {
				
				int where = sqlStatement.indexOf("WHERE");		        
				if (where >= 0)
					sqlSelect = sqlSelect + "AND ";
				else	
					sqlSelect = sqlSelect + "WHERE ";											
				
				if (fromCriteria.getFruitVarietyList().toLowerCase().trim().equals ("all")) {
					sqlSelect = sqlSelect + "(";	
					sqlSelect = sqlSelect + "IDTVAR = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCA = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCB = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCC = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCD = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCE = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCF = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCG = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCH = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCI = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCJ = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTVA1 = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTVA2 = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTVA3 = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTVA4 = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTVA5 = '" + fromCriteria.getFruitVariety() + "'";
					sqlSelect = sqlSelect + ") ";
				}
				if (fromCriteria.getFruitVarietyList().toLowerCase().trim().equals ("primary")) {	
					sqlSelect = sqlSelect + "(IDTVAR = '" + fromCriteria.getFruitVariety() + "') ";
				}
				if (fromCriteria.getFruitVarietyList().toLowerCase().trim().equals ("included")) {
					sqlSelect = sqlSelect + "(";
					sqlSelect = sqlSelect + "IDTSCA = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCB = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCC = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCD = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCE = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCF = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCG = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCH = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCI = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTSCJ = '" + fromCriteria.getFruitVariety() + "'";
					sqlSelect = sqlSelect + ") ";					
				}
				if (fromCriteria.getFruitVarietyList().toLowerCase().trim().equals ("excluded")) {
					sqlSelect = sqlSelect + "(";
					sqlSelect = sqlSelect + "IDTVA1 = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTVA2 = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTVA3 = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTVA4 = '" + fromCriteria.getFruitVariety() + "' OR ";
					sqlSelect = sqlSelect + "IDTVA5 = '" + fromCriteria.getFruitVariety() + "'";
					sqlSelect = sqlSelect + ") ";
				}						
			}
			}

		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlSingleVariety" + 
							   "(Class String): " + e);
		}	
	
		return sqlSelect;	
	}
	
	/**
	 * Main for testing methods.
	 *
	 * Creation date: (2/23/2005 11:45:39 AM)
	 */
	public static void main(String[] args) {
		
		try {

			SpecificationViewInquiry spec1 = new SpecificationViewInquiry();
			SpecificationViewInquiry spec2 = new SpecificationViewInquiry();
			Vector codes = new Vector();
			codes.addElement("");
			codes.addElement("brixr");
			codes.addElement("dsdeg");
			spec1.setAttributeCodes(codes);
//			spec1.setStatusCode("open");
			ResultSet spec = Specification.findSpecByRange(spec1, spec2);
			Vector    list = Specification.buildSpecFromFind(spec); 
			
			System.out.println("findSpecByRange successfull");
		}
		catch (Exception e) {
			System.out.println("Error: findSpecByRange: " + e);
		}



		try {

			SpecificationViewInquiry spec1 = new SpecificationViewInquiry();
			SpecificationViewInquiry spec2 = new SpecificationViewInquiry();
			Specification header  = new Specification();
			spec1.setSpecificationCode("RF010");
			spec1.setSpecificationDate("04/07/2005");
			spec1.setRequestType("detail");
//			spec1.setCustomerStatus("active");
//			spec1.setOrderByField("customernumber");
//			spec1.setOrderByStyle("");	
			ResultSet spec = Specification.findSpecByRange(spec1, spec2);
			Vector    list = Specification.buildSpecFromFind(spec); 
			
			System.out.println("findSpecByRange successfull");
		}
		catch (Exception e) {
			System.out.println("Error: findSpecByRange: " + e);
		}
						
		
		try {

			String time           = "01:45:23";
			Specification header  = new Specification(); 
			header.setCreateSpecTime(time);
									 
			System.out.println("setCreateSpecTime: " + header.createSpecTime + " successfull");
		}
		catch (Exception e) {
			System.out.println("Error: setCreateTime: " + e);
		}				

		
		try {

			Specification header  = new Specification(); 
			header.createSpecTime = new Integer("2345");
			String time = header.getCreateSpecTime();
			 
			System.out.println("getCreateSpecTime: " + time + " successfull");
		}
		catch (Exception e) {
			System.out.println("Error: getCreateTime: " + e);
		}		
		
	}
	
	/**
	 * Receive in a lot number and lot type and return 
	 * a Specification object, if one is assigned to the lot.
	 *
	 * Creation date: (5/05/2005)
	 */
	public static Specification rtnSpecificationForLot(String lotNumberIn,
													   String lotTypeIn) 
	{
		// Should Look at inventory on NEW Box - if still Valid
		// Will have to be redone... TWalton 2/2/09
		Specification spec = new Specification();	
		//InventoryLot  lot  = new InventoryLot();
		
		//try {
		//	lot = new InventoryLot(lotNumberIn, lotTypeIn);
			
		//	if (!lot.getSpecification().trim().equals("") &&
		//		lot.getSpecRevisionDate().trim().equals("0"))
			
		//		spec = new Specification(
		//				lot.getSpecification().trim());			
			
		//	if (!lot.getSpecification().trim().equals("") &&
		//		!lot.getSpecRevisionDate().trim().equals("0"))
			
		//		spec = new Specification(lot.getSpecification().trim(),
		//				                 lot.getSpecRevisionDate());			
		//}/ 
	   //catch(Exception e){
		//}
		
		return spec;	                                     
	}
		
	/**
	 * Instantiate the specification.
	 * 
	 * Creation date: (2/23/2005 11:42:39 AM)
	 */
	public Specification() {
		
		super();
		
		init();		
	}
	
	/**
	 * Instantiate the specification.
	 * 
	 * Creation date: (3/25/2005 1:14:42 PM)
	 */
	public Specification(String inCode)
	                     throws InstantiationException {
	                     	
		String errorMessage = "";
	                     	
		if (persists == false) 				
			init();
		
		
		// Retrieve data using the specification code.
		
		try {
			
			PreparedStatement specByCodeByCurr = (PreparedStatement) 
												  Specification.findSpecByCodeByCurr.pop();
			ResultSet rs = null;		

			specByCodeByCurr.setString(1, inCode.toUpperCase());				
			rs = specByCodeByCurr.executeQuery();			

			Specification.findSpecByCodeByCurr.push(specByCodeByCurr);              
			
			if (rs.next() == true)				
				this.loadFields(rs);
									
			else {	
				//System.out.println("Instantiation error com.treetop.data." + 
								   //"Specification(String=" + inCode + ")");	
				errorMessage = "Exception: No current specification for spec: " + inCode;
			}   			
		
		}
		catch (Exception e) {
			//System.out.println("Exception error processing SQL at com.treetop.data." + 
							   //"Specification(String): " + e);
			errorMessage = "Exception: No current specification for spec: " + inCode;
		}
		
		if (!errorMessage.equals (""))
			throw new InstantiationException(errorMessage);
		                                     	
	}
	
	/**
	 * Instantiate the specification.
	 * 
	 * Creation date: (3/25/2005 1:53:14 PM)
	 */
	public Specification(String inCode, Integer inDate)
						 throws InstantiationException {
						 	
		String errorMessage = "";
	                     	
		if (persists == false) 				
			init();
		
		
		// Retrieve data using the specification code and revision date.
		
		try {
			
			PreparedStatement specByCodeByDate = (PreparedStatement) 
												  Specification.findSpecByCodeByDate.pop();
			ResultSet rs = null;			
			
			specByCodeByDate.setString(1, inCode.toUpperCase());
			specByCodeByDate.setInt(2, inDate.intValue());				
			rs = specByCodeByDate.executeQuery();			

			Specification.findSpecByCodeByDate.push(specByCodeByDate);              
			
			if (rs.next() == true)				
				this.loadFields(rs);
									
			else {	
				System.out.println("Instantiation error com.treetop.data." + 
								   "Specification(String=" + inCode + ", String=" + inDate + ")");	
				errorMessage = "Exception: No specification " +
		                       "for spec: " + inCode + " " + inDate;
			}                  
        
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "Specification(String Integer): " + e);
			errorMessage = "Exception: No specification " +
						   "for spec: " + inCode + " " + inDate;		
		}
		
		if (!errorMessage.equals (""))
			throw new InstantiationException(errorMessage);
					                                     	
	}
	
	/**
	 * Instantiate the specification.
	 * 
	 * Creation date: (3/25/2005 1:53:14 PM)
	 */
	public Specification(String inCode, String inDate)
						 throws InstantiationException {
						 	
		String errorMessage = "";
	                     	
		if (persists == false) 				
			init();
		
		
		// Retrieve data using the specification code and revision date.
		
		try {
			
			PreparedStatement specByCodeByDate = (PreparedStatement) 
												  Specification.findSpecByCodeByDate.pop();
			ResultSet rs = null;
			
			Integer revisionDate = GetDate.formatSetDate(inDate);
			specByCodeByDate.setString(1, inCode.toUpperCase());
			specByCodeByDate.setInt(2, revisionDate.intValue());				
			rs = specByCodeByDate.executeQuery();			

			Specification.findSpecByCodeByDate.push(specByCodeByDate);              
			
			if (rs.next() == true)				
				this.loadFields(rs);
									
			else {	
				System.out.println("Instantiation error com.treetop.data." + 
								   "Specification(String=" + inCode + ", String=" + inDate + ")");	
				errorMessage = "Exception: No specification " +
							   "for spec: " + inCode + " " + inDate;			 	
			}			
			
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "Specification(String String): " + e);
			errorMessage = "Exception: No specification " +
						   "for spec: " + inCode + " " + inDate;			 	
		}
		
		if (!errorMessage.equals (""))
			throw new InstantiationException(errorMessage);
		                                     	
	}
	
	/**
	 * Create a new revision for each specification component.
	 *
	 * Creation date: (4/05/2005 9:13:02 AM)
	 */
	public Exception buildSpecRevision(String inCode, String inDate, String inRevision) {

		Specification specCurrent = new Specification();
		Vector listCurrent = new Vector();
		Vector listRevised = new Vector();
		
		try {
			
			try {
				
				// Current specification code and date MUST exist.
				
				specCurrent = new Specification(inCode, inDate);
			}
			catch (Exception e) {				
				return e;
			}

			try {
				
				// New revised specification MUST NOT exist. (expecting to insert)
				
				Specification specRevision = new Specification(inCode, inRevision);
			}
			catch (Exception e) {				
				System.out.println("Instantiation error corrected with specification revision " + 
					               "processing at com.treetop.data.Specification.buildSpec" + 
								   "Revision(Spec: " + inCode + ", Revision: " + inRevision + ")");
								   
				// Update the current specification and create the new revision.
								   
				specCurrent.setCurrentVersion(false);
				specCurrent.update(specCurrent);				
				
				specCurrent.setCurrentVersion(true);				
				specCurrent.setSupersededDate(specCurrent.getSpecificationDate());
				specCurrent.setSpecificationDate(inRevision);				
				Specification specRevised = new Specification();				
				specRevised.insert(specCurrent);
				
				// Update the each current specification attribute and create the new revision.
				
				listCurrent = SpecificationAnalytical.findAnalByCodeByDate(inCode, inDate);
				listRevised = new Vector();
				
				for (int x = 0; x < listCurrent.size(); x ++) {			 
					SpecificationAnalytical analUpdate = (SpecificationAnalytical) 
														  listCurrent.elementAt(x);
					analUpdate.setSupersededDate(specCurrent.getSupersededDate());
					analUpdate.setSpecificationDate(specCurrent.getSpecificationDate());
					analUpdate.setMarketNumber(specCurrent.getMarketNumber());
					analUpdate.setMarketCustomer(specCurrent.getMarketCustomer());
					
					analUpdate.setTargetValue(SpecificationAnalytical.decimalPositionRemove
					          (analUpdate.getTargetValue(), analUpdate.getDecimalPositions().intValue()));
					analUpdate.setStandardUpperLimit(SpecificationAnalytical.decimalPositionRemove
							  (analUpdate.getStandardUpperLimit(), analUpdate.getDecimalPositions().intValue()));
					analUpdate.setStandardLowerLimit(SpecificationAnalytical.decimalPositionRemove
							  (analUpdate.getStandardLowerLimit(), analUpdate.getDecimalPositions().intValue()));
					analUpdate.setAcceptableUpperLimit(SpecificationAnalytical.decimalPositionRemove
							  (analUpdate.getAcceptableUpperLimit(), analUpdate.getDecimalPositions().intValue()));
					analUpdate.setAcceptableLowerLimit(SpecificationAnalytical.decimalPositionRemove
							  (analUpdate.getAcceptableLowerLimit(), analUpdate.getDecimalPositions().intValue()));
					          
					listRevised.addElement(analUpdate);					 
				}
				SpecificationAnalytical analRevised = new SpecificationAnalytical();
				analRevised.insert(listRevised);
				
				// Update the each current specification document text line and create the new revision.
				
				listCurrent = SpecificationDocumentation.findTextByCodeByDate(inCode, inDate);
				listRevised = new Vector();
				
				for (int x = 0; x < listCurrent.size(); x ++) {			 
					SpecificationDocumentation textUpdate = (SpecificationDocumentation) 
														     listCurrent.elementAt(x);
					textUpdate.setSupersededDate(specCurrent.getSupersededDate());
					textUpdate.setSpecificationDate(specCurrent.getSpecificationDate());
					textUpdate.setMarketNumber(specCurrent.getMarketNumber());
					textUpdate.setMarketCustomer(specCurrent.getMarketCustomer());
					
					listRevised.addElement(textUpdate);
				}
				SpecificationDocumentation textRevised = new SpecificationDocumentation();
				textRevised.insert(listRevised);
			}

		}
		catch (Exception e) {
			System.out.println("Exception error processing a result set at com.treetop.data." + 
							   "Specification.buildSpecRevision(String String String): " + e);
			return e;
		}
		
		return null;	                                     
	}
	
	/**
	 * Delete a specification.
	 *
	 * Creation date: (2/25/2005 3:13:48 PM)
	 */
	private boolean delete(String inCode, String inDate) {

		try {
	
			PreparedStatement deleteSpec = (PreparedStatement) sqlDeleteSpec.pop();
			
			Integer revisionDate = GetDate.formatSetDate(inDate);
			deleteSpec.setString(1, inCode);
			deleteSpec.setInt(2, revisionDate.intValue());			
			deleteSpec.executeUpdate();
			sqlDeleteSpec.push(deleteSpec);
			
			return true;
					
		} 

		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data." +
							   "Specification.delete(String String): " + e);
			return false;
		}	
	
	}
	
	/**
	 * Retrieve the company number per the customer number.
	 *
	 * Creation date: (2/23/2005 1:55:42 PM)
	 */
	public String getCompanyNumber() {

		return companyNumber;
	}
	
	/**
	 * Retrieve the date the record was created.
	 *
	 * Creation date: (2/23/2005 2:06:00 PM)
	 */
	public String getCreateSpecDate() {
		
		String dateFormated = GetDate.formatGetDate(createSpecDate);
	
		return dateFormated;
	}
	
	/**
	 * Retrieve the time of day the record was created.
	 *
	 * Creation date: (2/23/2005 2:06:17 PM)
	 */
	public String getCreateSpecTime() {
		
		String timeFormated = TimeUtilities.formatGetTime(createSpecTime);
	
		return timeFormated;	
	}
	
	/**
	 * Retrieve the user profile that created the record.
	 *
	 * Creation date: (2/23/2005 2:15:55 PM)
	 */
	public String getCreateSpecUser() {

		return createSpecUser;
	}
	
	/**
	 * Retrieve the name of the user that created the record.
	 *
	 * Creation date: (2/23/2005 2:15:36 PM)
	 */
	public String getCreateSpecUserName() {

		return createSpecUserName;
	}
	
	/**
	 * Retrieve the workstation name used to create the record.
	 *
	 * Creation date: (2/23/2005 2:17:05 PM)
	 */
	public String getCreateSpecWorkstation() {

		return createSpecWorkstation;
	}
	
	/**
	 * Retrieve the current version code, set by the most current revision date.
	 *
	 * Creation date: (2/23/2005 2:18:22 PM)
	 */
	public boolean getCurrentVersion() {

		return currentVersion;
	}
	
	/**
	 * Retrieve the customer number.
	 *
	 * Creation date: (2/24/2005 8:51:05 AM)
	 */
	public String getCustomerNumber() {

		return customerNumber;
	}
	
	/**
	 * Retrieve the customer specification.
	 *
	 * Creation date: (2/24/2005 8:52:15 AM)
	 */
	public String getCustomerSpecification() {

		return customerSpecification;
	}
	
	/**
	 * Retrieve the deleted record switch.
	 *
	 * Creation date: (2/24/2005 8:53:22 PM)
	 */
	public boolean getDeletedRecord() {

		return deletedRecord;
	}
	
	/**
	 * Retrieve the primary fruit variety.
	 *
	 * Creation date: (2/24/2005 8:54:00 AM)
	 */
	public String getFruitVariety() {

		return fruitVariety;
	}
	
	/**
	 * Retrieve the fruit variety editing code.
	 *
	 * Creation date: (2/24/2005 8:56:26 AM)
	 */
	public String getFruitVarietyEdit() {

		return fruitVarietyEdit;
	}
	
	/**
	 * Retrieve the general specification description.
	 *
	 * Creation date: (2/24/2005 8:56:26 AM)
	 */
	public String getGeneralDescription() {

		return generalDescription;
	}
	
	/**
	 * Retrieve the market number.
	 *
	 * Creation date: (2/24/2005 10:06:17 AM)
	 */
	public Integer getMarketNumber() {
		
		return marketNumber;	
	}
	
	/**
	 * Retrieve the customer number portion of the market/customer combination.
	 *
	 * Creation date: (2/24/2005 10:08:22 AM)
	 */
	public Integer getMarketCustomer() {
		
		return marketCustomer;	
	}
	
	/**
	 * Retrieve the plant number.
	 *
	 * Creation date: (2/24/2005 10:13:42 AM)
	 */
	public Integer getPlantNumber() {
		
		return plantNumber;	
	}
	
	/**
	 * Retrieve the raw fruit specification code.
	 *
	 * Creation date: (2/24/2005 10:14:24 AM)
	 */
	public String getRawFruitSpecCode() {

		return rawFruitSpecCode;
	}
	
	/**
	 * Retrieve the raw fruit specification revision date.
	 *
	 * Creation date: (2/24/2005 10:15:17 AM)
	 */
	public String getRawFruitSpecDate() {
		
		String dateFormated = GetDate.formatGetDate(rawFruitSpecDate);
	
		return dateFormated;
	}
	
	/**
	 * Retrieve the specification analytical reference code.
	 *
	 * Creation date: (2/24/2005 10:18:02 AM)
	 */
	public String getReferenceAnalCode() {

		return referenceAnalCode;
	}
	
	/**
	 * Retrieve the specification statement text reference code.
	 *
	 * Creation date: (2/24/2005 10:18:34 AM)
	 */
	public String getReferenceTextCode() {

		return referenceTextCode;
	}
	
	/**
	 * Retrieve the resource number.
	 *
	 * Creation date: (2/24/2005 10:21:06 AM)
	 */
	public String getResourceNumber() {

		return resourceNumber;
	}
	
	/**
	 * Retrieve the specification code.
	 *
	 * Creation date: (2/24/2005 10:22:21 AM)
	 */
	public String getSpecificationCode() {

		return specificationCode;
	}
	
	/**
	 * Retrieve the specification revision date.
	 *
	 * Creation date: (2/24/2005 10:21:18 AM)
	 */
	public String getSpecificationDate() {
		
		String dateFormated = GetDate.formatGetDate(specificationDate);
	
		return dateFormated;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getSpecificationDateInteger() {
		return specificationDate;
	}
	
	/**
	 * Retrieve the specification type.
	 *
	 * Creation date: (2/24/2005 10:24:34 AM)
	 */
	public String getSpecificationType() {

		return specificationType;
	}
	
	/**
	 * Retrieve the specification status code.
	 *
	 * Creation date: (2/24/2005 10:25:04 AM)
	 */
	public String getStatusCode() {

		return statusCode;
	}
	
	/**
	 * Retrieve the superseded revision date.
	 *
	 * Creation date: (2/24/2005 10:27:34 AM)
	 */
	public String getSupersededDate() {
		
		String dateFormated = GetDate.formatGetDate(supersededDate);
	
		return dateFormated;
	}
	
	/**
	 * Retrieve the date the record was last updated.
	 *
	 * Creation date: (2/24/2005 10:28:37 AM)
	 */
	public String getUpdateSpecDate() {
		
		String dateFormated = GetDate.formatGetDate(updateSpecDate);
	
		return dateFormated;
	}
	
	/**
	 * Retrieve the time of day the record was last updated.
	 *
	 * Creation date: (2/24/2005 10:29:07 AM)
	 */
	public String getUpdateSpecTime() {
		
		String timeFormated = TimeUtilities.formatGetTime(updateSpecTime);
	
		return timeFormated;	
	}
	
	/**
	 * Retrieve the last user profile that updated the record.
	 *
	 * Creation date: (2/24/2005 10:29:55 PM)
	 */
	public String getUpdateSpecUser() {

		return updateSpecUser;
	}
	
	/**
	 * Retrieve the name of the last user that updated the record.
	 *
	 * Creation date: (2/24/2005 10:30:36 PM)
	 */
	public String getUpdateSpecUserName() {

		return updateSpecUserName;
	}
	
	/**
	 * Retrieve the last workstation name used to update the record.
	 *
	 * Creation date: (2/24/2005 10:31:05 PM)
	 */
	public String getUpdateSpecWorkstation() {

		return updateSpecWorkstation;
	}
	
	/**
	 * Retrieve the array of acceptable fruit varieties for manufacturing.
	 *
	 * Creation date: (2/23/2005 1:55:42 PM)
	 */
	public String[] getVarietiesAcceptable() {

		return varietiesAcceptable;
	}
	
	/**
	 * Retrieve the array of fruit varieties excluded from manufacturing.
	 *
	 * Creation date: (2/23/2005 1:58:12 PM)
	 */
	public String[] getVarietiesExcluded() {

		return varietiesExcluded;
	}
	
	/**
	 * Retrieve the classification code for work-in-progress.
	 *
	 * Creation date: (2/23/2005 2:35:00 PM)
	 */
	public String getWIPClassification() {

		return wipClassification;
	}
	
	/**
	 * SQL definitions.
	 *
	 * Creation date: (2/23/2005 1:27:29 PM)
	 */
	public void init() {	
	 
	
		// Test for prior initialization.
	
		if (persists == false) {	
			persists = true;	
			
			String dbprd = "DBPRD.";
	    

		// Perform initialization.
	 
		try {
			
			Connection conn1 = ConnectionStack.getConnection();
			Connection conn2 = ConnectionStack.getConnection();
			Connection conn3 = ConnectionStack.getConnection();
			Connection conn4 = ConnectionStack.getConnection();
			Connection conn5 = ConnectionStack.getConnection();	
			Connection conn6 = ConnectionStack.getConnection();
			
			
			// SQL for data base changes --------		
			
			String deleteSpec = 
				  "DELETE FROM " + dbprd + "IDPTSPHD " +
				  "WHERE IDTSPC = ? AND IDTRVD = ?";			 

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
				  "INSERT INTO " + dbprd + "IDPTSPHD " +
				  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			              "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			              "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			              "?, ?, ?, ?, ?, ?)";

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
				  "UPDATE " + dbprd + "IDPTSPHD " +
				  "SET IDTREC = ?, IDTDLT = ?, IDTSTS = ?, IDTTYP = ?, IDTBRK = ?," +
					 " IDTCUS = ?, IDTSPC = ?, IDTCSP = ?, IDTCCO = ?, IDTCNO = ?," +
			         " IDTDSC = ?, IDTRSC = ?, IDTADV = ?, IDTPLT = ?, IDTVAR = ?," +
			         " IDTVA1 = ?, IDTVA2 = ?, IDTVA3 = ?, IDTVA4 = ?, IDTVA5 = ?," +
				     " IDTGRD = ?, IDTGD2 = ?, IDTGA1 = ?, IDTGA2 = ?, IDTSCA = ?," +
			         " IDTSCB = ?, IDTSCC = ?, IDTSCD = ?, IDTSCE = ?, IDTSCF = ?," +
			         " IDTSCG = ?, IDTSCH = ?, IDTSCI = ?, IDTSCJ = ?, IDTUUR = ?," +
					 " IDTUWS = ?, IDTUPD = ?, IDTUTM = ?, IDTCUR = ?, IDTCWS = ?," +
					 " IDTCRT = ?, IDTCTM = ?, IDTTR# = ?, IDTDR# = ?, IDTRVD = ?," +
					 " IDTSRV = ?, IDTCRV = ?, IDTWIP = ?, IDTVEC = ?, IDTRFS = ?, IDTRFD = ? " +					
				  "WHERE IDTSPC = ? AND IDTRVD = ?";
			
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
			
			
			// SQL selection --------			

			String specByCodeByCurr = 						
				"SELECT *  " +
						   "FROM " + dbprd + "IDPTSPHD " +
				   "LEFT OUTER JOIN " + movex + "MITMAS " +
				      "ON MMITNO = IDTRSC " +
				" WHERE IDTSPC = ? AND IDTCRV = 1" +
				" ORDER BY IDTSPC, IDTRVD DESC";

			PreparedStatement specByCodeByCurr1 = conn1.prepareStatement(specByCodeByCurr);
			PreparedStatement specByCodeByCurr2 = conn2.prepareStatement(specByCodeByCurr);
			PreparedStatement specByCodeByCurr3 = conn3.prepareStatement(specByCodeByCurr);
			PreparedStatement specByCodeByCurr4 = conn4.prepareStatement(specByCodeByCurr);
			PreparedStatement specByCodeByCurr5 = conn5.prepareStatement(specByCodeByCurr);
			PreparedStatement specByCodeByCurr6 = conn6.prepareStatement(specByCodeByCurr);

			findSpecByCodeByCurr = new Stack();
			findSpecByCodeByCurr.push(specByCodeByCurr1);
			findSpecByCodeByCurr.push(specByCodeByCurr2);
			findSpecByCodeByCurr.push(specByCodeByCurr3);
			findSpecByCodeByCurr.push(specByCodeByCurr4);
			findSpecByCodeByCurr.push(specByCodeByCurr5);
			findSpecByCodeByCurr.push(specByCodeByCurr6);
				 
				 
			String specByCodeByDate = 						
				"SELECT *  " +
						   "FROM " + dbprd + "IDPTSPHD " +	
					"LEFT OUTER JOIN " + movex + "MITMAS " +
						      "ON MMITNO = IDTRSC " +
				" WHERE IDTSPC = ? AND IDTRVD = ?" +
				" ORDER BY IDTSPC, IDTRVD DESC";

			PreparedStatement specByCodeByDate1 = conn1.prepareStatement(specByCodeByDate);
			PreparedStatement specByCodeByDate2 = conn2.prepareStatement(specByCodeByDate);
			PreparedStatement specByCodeByDate3 = conn3.prepareStatement(specByCodeByDate);
			PreparedStatement specByCodeByDate4 = conn4.prepareStatement(specByCodeByDate);
			PreparedStatement specByCodeByDate5 = conn5.prepareStatement(specByCodeByDate);
			PreparedStatement specByCodeByDate6 = conn6.prepareStatement(specByCodeByDate);

			findSpecByCodeByDate = new Stack();
			findSpecByCodeByDate.push(specByCodeByDate1);
			findSpecByCodeByDate.push(specByCodeByDate2);
			findSpecByCodeByDate.push(specByCodeByDate3);
			findSpecByCodeByDate.push(specByCodeByDate4);
			findSpecByCodeByDate.push(specByCodeByDate5);
			findSpecByCodeByDate.push(specByCodeByDate6);
			
			String specByAttribute = 						
				  "SELECT *  " +
				  "FROM " + dbprd + "IDPVSPDA " +	
				  "INNER JOIN " + library + "IDPTSPHD " +
							  "ON IDVSPC = IDTSPC AND IDVRVD = IDTRVD " +				
				  "WHERE IDVCDE = ? " +
				  "ORDER BY IDVSPC, IDVRVD DESC";

			PreparedStatement specByAttribute1 = conn1.prepareStatement(specByAttribute);
			PreparedStatement specByAttribute2 = conn2.prepareStatement(specByAttribute);
			PreparedStatement specByAttribute3 = conn3.prepareStatement(specByAttribute);
			PreparedStatement specByAttribute4 = conn4.prepareStatement(specByAttribute);
			PreparedStatement specByAttribute5 = conn5.prepareStatement(specByAttribute);
			PreparedStatement specByAttribute6 = conn6.prepareStatement(specByAttribute);

			findSpecByAttribute = new Stack();
			findSpecByAttribute.push(specByAttribute1);
			findSpecByAttribute.push(specByAttribute2);
			findSpecByAttribute.push(specByAttribute3);
			findSpecByAttribute.push(specByAttribute4);
			findSpecByAttribute.push(specByAttribute5);
			findSpecByAttribute.push(specByAttribute6);
			
			
			// SQL selection (drop down lists) --------
			
			//Exclude-Change to new AS400 12/23/08 wth
			//String specForCompany =
						  //"SELECT IDTCCO, CONAME " + 
						  //"FROM " + library + "IDLTHDRM " +
						  //"JOIN " + financial + "ARLCO " +
								//"ON IDTCCO = COCO " +
						  //"WHERE IDTCCO <> '     ' " +
						  //"GROUP BY IDTCCO, CONAME " +                         
						  //"ORDER BY IDTCCO, CONAME ";
						  
			//PreparedStatement specForCompany1 = conn1.prepareStatement(specForCompany);
			//PreparedStatement specForCompany2 = conn2.prepareStatement(specForCompany);
			//PreparedStatement specForCompany3 = conn3.prepareStatement(specForCompany);
			//PreparedStatement specForCompany4 = conn4.prepareStatement(specForCompany);
			//PreparedStatement specForCompany5 = conn5.prepareStatement(specForCompany);
			//PreparedStatement specForCompany6 = conn6.prepareStatement(specForCompany);

			//findSpecForCompany = new Stack();
			//findSpecForCompany.push(specForCompany1);
			//findSpecForCompany.push(specForCompany2);
			//findSpecForCompany.push(specForCompany3);
			//findSpecForCompany.push(specForCompany4);
			//findSpecForCompany.push(specForCompany5);
			//findSpecForCompany.push(specForCompany6);
			
			//Exclude-Change to new AS400 12/23/08 wth
			//String specForCustomer =
						  //"SELECT IDTCCO, IDTCNO, CUNAME " + 
						  //"FROM " + library + "IDLTHDRM " +
						  //"JOIN " + financial + "ARLCU " +
								//"ON IDTCCO = CUCO AND IDTCNO = CUCUNO " +
						  //"WHERE IDTCNO <> '              ' " +
						  //"GROUP BY IDTCCO, IDTCNO, CUNAME " +                         
						  //"ORDER BY IDTCCO, IDTCNO, CUNAME ";
						  
			//PreparedStatement specForCustomer1 = conn1.prepareStatement(specForCustomer);
			//PreparedStatement specForCustomer2 = conn2.prepareStatement(specForCustomer);
			//PreparedStatement specForCustomer3 = conn3.prepareStatement(specForCustomer);
			//PreparedStatement specForCustomer4 = conn4.prepareStatement(specForCustomer);
			//PreparedStatement specForCustomer5 = conn5.prepareStatement(specForCustomer);
			//PreparedStatement specForCustomer6 = conn6.prepareStatement(specForCustomer);

			//findSpecForCustomer = new Stack();
			//findSpecForCustomer.push(specForCustomer1);
			//findSpecForCustomer.push(specForCustomer2);
			//findSpecForCustomer.push(specForCustomer3);
			//findSpecForCustomer.push(specForCustomer4);
			//findSpecForCustomer.push(specForCustomer5);
			//findSpecForCustomer.push(specForCustomer6);			
		 				         						
			//Exclude-Change to new AS400 12/23/08 wth										
			//String specForResource =
						  //"SELECT RMRESC, RMDESC " + 
						  //"FROM " + library + "IDLTHDRL " +
						  //"JOIN " + prism +   "RESMST " +
								//"ON IDTRSC = RMRESC " +
						  //"WHERE IDTRSC <> '               ' " +
						  //"GROUP BY RMRESC, RMDESC " +                         
						  //"ORDER BY RMRESC, RMDESC ";
						  
			//PreparedStatement specForResource1 = conn1.prepareStatement(specForResource);
			//PreparedStatement specForResource2 = conn2.prepareStatement(specForResource);
			//PreparedStatement specForResource3 = conn3.prepareStatement(specForResource);
			//PreparedStatement specForResource4 = conn4.prepareStatement(specForResource);
			//PreparedStatement specForResource5 = conn5.prepareStatement(specForResource);
			//PreparedStatement specForResource6 = conn6.prepareStatement(specForResource);

			//findSpecForResource = new Stack();
			//findSpecForResource.push(specForResource1);
			//findSpecForResource.push(specForResource2);
			//findSpecForResource.push(specForResource3);
			//findSpecForResource.push(specForResource4);
			//findSpecForResource.push(specForResource5);
			//findSpecForResource.push(specForResource6); 				         						
			

			
			// Return the connections back to the pool.
			ConnectionStack.returnConnection(conn1);
			ConnectionStack.returnConnection(conn2);
			ConnectionStack.returnConnection(conn3);
			ConnectionStack.returnConnection(conn4);
			ConnectionStack.returnConnection(conn5);
			ConnectionStack.returnConnection(conn6);			
		
		}				

		catch (SQLException e) {
			System.out.println("SQL exception occured at com.treetop.data.Specification.init()" + e);
		}    
    	
		}
	
	}
	
	/**
	 * Insert a revised specification.
	 *
	 * Creation date: (4/05/2005 9:55:19 AM)
	 */
	public Exception insert(Specification inSpec) {

		try {
					
			PreparedStatement insertSpec = (PreparedStatement) sqlInsertSpec.pop();
		
			insertSpec.setString(1, "SH");
					
			if (inSpec.deletedRecord)								
				insertSpec.setString(2, "D");
			else
				insertSpec.setString(2, " ");
						
			insertSpec.setString(3, "OP");
			insertSpec.setString(4, inSpec.specificationType);					
			insertSpec.setInt(5, inSpec.marketNumber.intValue());
			insertSpec.setInt(6, inSpec.marketCustomer.intValue());
			insertSpec.setString(7, inSpec.specificationCode);
			insertSpec.setString(8, inSpec.customerSpecification);
			insertSpec.setString(9, inSpec.companyNumber);
			insertSpec.setString(10, inSpec.customerNumber);
			insertSpec.setString(11, inSpec.generalDescription);
			insertSpec.setString(12, inSpec.resourceNumber);
			insertSpec.setString(13, "  ");
			insertSpec.setInt(14, inSpec.plantNumber.intValue());					
			insertSpec.setString(15, inSpec.fruitVariety);
			insertSpec.setString(16, inSpec.varietiesExcluded[0]);
			insertSpec.setString(17, inSpec.varietiesExcluded[1]);
			insertSpec.setString(18, inSpec.varietiesExcluded[2]);
			insertSpec.setString(19, inSpec.varietiesExcluded[3]);
			insertSpec.setString(20, inSpec.varietiesExcluded[4]);
			insertSpec.setString(21, " ");
			insertSpec.setString(22, " ");
			insertSpec.setString(23, "  ");
			insertSpec.setString(24, "  ");
			insertSpec.setString(25, inSpec.varietiesAcceptable[0]);
			insertSpec.setString(26, inSpec.varietiesAcceptable[1]);
			insertSpec.setString(27, inSpec.varietiesAcceptable[2]);
			insertSpec.setString(28, inSpec.varietiesAcceptable[3]);
			insertSpec.setString(29, inSpec.varietiesAcceptable[4]);
			insertSpec.setString(30, inSpec.varietiesAcceptable[5]);
			insertSpec.setString(31, inSpec.varietiesAcceptable[6]);
			insertSpec.setString(32, inSpec.varietiesAcceptable[7]);
			insertSpec.setString(33, inSpec.varietiesAcceptable[8]);
			insertSpec.setString(34, inSpec.varietiesAcceptable[9]);					
			insertSpec.setString(35, inSpec.updateSpecUser);
			insertSpec.setString(36, inSpec.updateSpecWorkstation);
			insertSpec.setInt(37, inSpec.updateSpecDate.intValue());
			insertSpec.setInt(38, inSpec.updateSpecTime.intValue());
			insertSpec.setString(39, inSpec.createSpecUser);
			insertSpec.setString(40, inSpec.createSpecWorkstation);
			insertSpec.setInt(41, inSpec.createSpecDate.intValue());
			insertSpec.setInt(42, inSpec.createSpecTime.intValue());
			insertSpec.setString(43, inSpec.referenceTextCode);
			insertSpec.setString(44, inSpec.referenceAnalCode);					
			insertSpec.setInt(45, inSpec.specificationDate.intValue());
			insertSpec.setInt(46, inSpec.supersededDate.intValue());					
					
			if (inSpec.currentVersion)								
				insertSpec.setInt(47, new Integer("1").intValue());
			else
				insertSpec.setInt(47, new Integer("0").intValue());
						
			insertSpec.setString(48, inSpec.wipClassification);
			insertSpec.setString(49, inSpec.fruitVarietyEdit);
			insertSpec.setString(50, inSpec.rawFruitSpecCode);
			insertSpec.setInt(51, inSpec.rawFruitSpecDate.intValue());															

			insertSpec.executeUpdate();

			sqlInsertSpec.push(insertSpec);				
										
		}		
		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data." +
							   "Specification.insert(Class): " + e);
			return e;
		}
		
		return null;	
	}
	
	/**
	 * Insert a specification.
	 *
	 * Creation date: (2/25/2005 3:15:00 PM)
	 */
	public Exception insert(Vector inInsertInfo) {

		try {	
					
			for (int x = 0; x < inInsertInfo.size(); x ++) {				  	
			 
				Specification insertInfo = (Specification) inInsertInfo.elementAt(x);
				                                      	  
				if ((insertInfo.specificationCode != null) && 		// Spec code required
				   (!insertInfo.specificationCode.equals(""))) {
					    	
					PreparedStatement insertSpec = (PreparedStatement) sqlInsertSpec.pop();
		
					insertSpec.setString(1, "SH");
					
					if (insertInfo.deletedRecord)								
						insertSpec.setString(2, "D");
					else
						insertSpec.setString(2, " ");
						
					insertSpec.setString(3, insertInfo.statusCode);
					insertSpec.setString(4, insertInfo.specificationType);					
					insertSpec.setInt(5, insertInfo.marketNumber.intValue());
					insertSpec.setInt(6, insertInfo.marketCustomer.intValue());
					insertSpec.setString(7, insertInfo.specificationCode);
					insertSpec.setString(8, insertInfo.customerSpecification);
					insertSpec.setString(9, insertInfo.companyNumber);
					insertSpec.setString(10, insertInfo.customerNumber);
					insertSpec.setString(11, insertInfo.generalDescription);
					insertSpec.setString(12, insertInfo.resourceNumber);
					insertSpec.setString(13, "  ");
					insertSpec.setInt(14, insertInfo.plantNumber.intValue());					
					insertSpec.setString(15, insertInfo.fruitVariety);
					insertSpec.setString(16, insertInfo.varietiesExcluded[0]);
					insertSpec.setString(17, insertInfo.varietiesExcluded[1]);
					insertSpec.setString(18, insertInfo.varietiesExcluded[2]);
					insertSpec.setString(19, insertInfo.varietiesExcluded[3]);
					insertSpec.setString(20, insertInfo.varietiesExcluded[4]);
					insertSpec.setString(21, " ");
					insertSpec.setString(22, " ");
					insertSpec.setString(23, "  ");
					insertSpec.setString(24, "  ");
					insertSpec.setString(25, insertInfo.varietiesAcceptable[0]);
					insertSpec.setString(26, insertInfo.varietiesAcceptable[1]);
					insertSpec.setString(27, insertInfo.varietiesAcceptable[2]);
					insertSpec.setString(28, insertInfo.varietiesAcceptable[3]);
					insertSpec.setString(29, insertInfo.varietiesAcceptable[4]);
					insertSpec.setString(30, insertInfo.varietiesAcceptable[5]);
					insertSpec.setString(31, insertInfo.varietiesAcceptable[6]);
					insertSpec.setString(32, insertInfo.varietiesAcceptable[7]);
					insertSpec.setString(33, insertInfo.varietiesAcceptable[8]);
					insertSpec.setString(34, insertInfo.varietiesAcceptable[9]);					
					insertSpec.setString(35, insertInfo.updateSpecUser);
					insertSpec.setString(36, insertInfo.updateSpecWorkstation);
					insertSpec.setInt(37, insertInfo.updateSpecDate.intValue());
					insertSpec.setInt(38, insertInfo.updateSpecTime.intValue());
					insertSpec.setString(39, insertInfo.createSpecUser);
					insertSpec.setString(40, insertInfo.createSpecWorkstation);
					insertSpec.setInt(41, insertInfo.createSpecDate.intValue());
					insertSpec.setInt(42, insertInfo.createSpecTime.intValue());
					insertSpec.setString(43, insertInfo.referenceTextCode);
					insertSpec.setString(44, insertInfo.referenceAnalCode);					
					insertSpec.setInt(45, insertInfo.specificationDate.intValue());
					insertSpec.setInt(46, insertInfo.supersededDate.intValue());				
					
					
					if (insertInfo.currentVersion)								
						insertSpec.setInt(47, new Integer("1").intValue());
					else
						insertSpec.setInt(47, new Integer("0").intValue());
						
					insertSpec.setString(48, insertInfo.wipClassification);
					insertSpec.setString(49, insertInfo.fruitVarietyEdit);
					insertSpec.setString(50, insertInfo.rawFruitSpecCode);
					insertSpec.setInt(51, insertInfo.rawFruitSpecDate.intValue());															

					insertSpec.executeUpdate();

					sqlInsertSpec.push(insertSpec);	
				}	
			}							
		}		
		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data." +
							   "Specification.insert(Vector): " + e);
			return e;
		}
		
		return null;	
	}
	
	/**
	 * Load fields from data base record.
	 *
	 * Creation date: (3/14/2005 3:04:29 PM)
	 */
	protected void loadFields(ResultSet rs) {

		try {
			
			varietiesAcceptable = new String[include];
			varietiesExcluded   = new String[exclude];
		
			specificationCode		= rs.getString("IDTSPC");			
			specificationDate       = new Integer(rs.getInt("IDTRVD"));											
			
			specificationType		= rs.getString("IDTTYP");
			supersededDate  		= new Integer(rs.getInt("IDTSRV"));
			statusCode				= rs.getString("IDTSTS");
			marketNumber			= new Integer(rs.getInt("IDTBRK"));
			marketCustomer			= new Integer(rs.getInt("IDTCUS"));
			companyNumber			= rs.getString("IDTCCO");
			customerNumber			= rs.getString("IDTCNO");
			customerSpecification	= rs.getString("IDTCSP");
			generalDescription		= rs.getString("IDTDSC");
			resourceNumber			= rs.getString("IDTRSC");
			plantNumber				= new Integer(rs.getInt("IDTPLT"));
			referenceTextCode		= rs.getString("IDTTR#");
			referenceAnalCode		= rs.getString("IDTDR#");
			rawFruitSpecCode		= rs.getString("IDTRFS");
			rawFruitSpecDate		= new Integer(rs.getInt("IDTRFD"));
			fruitVarietyEdit		= rs.getString("IDTVEC");
			fruitVariety    		= rs.getString("IDTVAR");
			varietiesAcceptable[0]	= rs.getString("IDTSCA");
			varietiesAcceptable[1]	= rs.getString("IDTSCB");
			varietiesAcceptable[2]	= rs.getString("IDTSCC");
			varietiesAcceptable[3]	= rs.getString("IDTSCD");
			varietiesAcceptable[4]	= rs.getString("IDTSCE");
			varietiesAcceptable[5]	= rs.getString("IDTSCF");
			varietiesAcceptable[6]	= rs.getString("IDTSCG");
			varietiesAcceptable[7]	= rs.getString("IDTSCH");
			varietiesAcceptable[8]	= rs.getString("IDTSCI");
			varietiesAcceptable[9]	= rs.getString("IDTSCJ");
			varietiesExcluded[0]	= rs.getString("IDTVA1");
			varietiesExcluded[1]	= rs.getString("IDTVA2");
			varietiesExcluded[2]	= rs.getString("IDTVA3");
			varietiesExcluded[3]	= rs.getString("IDTVA4");
			varietiesExcluded[4]	= rs.getString("IDTVA5");						
			wipClassification		= rs.getString("IDTWIP");
			updateSpecUser			= rs.getString("IDTUUR");
			updateSpecUserName		= " ";
			updateSpecDate			= new Integer(rs.getInt("IDTUPD"));
			updateSpecTime			= new Integer(rs.getInt("IDTUTM"));
			updateSpecWorkstation	= rs.getString("IDTUWS");
			createSpecUser			= rs.getString("IDTCUR");
			createSpecUserName		= " ";
			createSpecDate			= new Integer(rs.getInt("IDTCRT"));
			createSpecTime			= new Integer(rs.getInt("IDTCTM"));
			createSpecWorkstation	= rs.getString("IDTCWS");
				
			Integer version			= new Integer(rs.getInt("IDTCRV"));
			if (version.intValue() == 1)
				currentVersion      = true;
			else
				currentVersion		= false;
				
			String deleted			= rs.getString("IDTDLT");
			if (deleted.equals ("D"))
				deletedRecord       = true;
			else
				deletedRecord		= false;
			try
			{
				if (rs.getString("MMITDS") != null)
				   itemDescription 	= rs.getString("MMITDS").trim();
				else
				   itemDescription = "&nbsp;";
			}
			catch(Exception e)
			{
				// Just catch it if the item Description is NOT found
			}
	
		}
		catch (Exception e) {	
			System.out.println("SQL Exception at com.treetop.data.Specification" +
							   ".loadFields(RS): " + e);
		}
					
	}
	
	/**
	 * Set the company number per the customer number.
	 *
	 * Creation date: (2/24/2005 11:52:16 PM)
	 */
	public void setCompanyNumber(String inCompanyNumber) throws InvalidLengthException {
	
		if (inCompanyNumber.length() > 5)
			throw new InvalidLengthException(
					"inCompanyNumber", inCompanyNumber.length(), 5);

		this.companyNumber = inCompanyNumber;
	}
	
	/**
	 * Set the date the record was created.
	 *
	 * Creation date: (2/24/2005 11:56:11 PM)
	 */
	public void setCreateSpecDate(String inCreateDate) {		

		this.createSpecDate = GetDate.formatSetDate(inCreateDate);
	}
	
	/**
	 * Set the time of day the record was created.
	 *
	 * Creation date: (2/25/2005 11:23:24 AM)
	 */
	public void setCreateSpecTime(String inCreateTime) {
		
		this.createSpecTime = TimeUtilities.formatSetTime(inCreateTime);
	}
	
	/**
	 * Set the user profile that created the record.
	 *
	 * Creation date: (2/25/2005 11:25:55 AM)
	 */
	public void setCreateSpecUser(String inCreateUser) throws InvalidLengthException {
	
		if (inCreateUser.length() > 10)
			throw new InvalidLengthException(
					"inCreateUser", inCreateUser.length(), 10);

		this.createSpecUser = inCreateUser;
	}
	
	/**
	 * Set the user name that created the record.
	 *
	 * Creation date: (2/25/2005 11:28:36 AM)
	 */
	public void setCreateSpecUserName(String inCreateUserName) throws InvalidLengthException {
	
		if (inCreateUserName.length() > 50)
			throw new InvalidLengthException(
					"inCreateUser", inCreateUserName.length(), 50);

		this.createSpecUserName = inCreateUserName;
	}
	
	/**
	 * Set the workstation name that created the record.
	 *
	 * Creation date: (2/25/2005 11:31:49 AM)
	 */
	public void setCreateSpecWorkstation(String inCreateWorkstation) throws InvalidLengthException {
	
		if (inCreateWorkstation.length() > 10)
			throw new InvalidLengthException(
					"inCreateWorkstation", inCreateWorkstation.length(), 10);

		this.createSpecWorkstation = inCreateWorkstation;
	}
	
	/**
	 * Set the current version code, set by the most current revision date.
	 *
	 * Creation date: (2/25/2005 11:35:10 AM)
	 */
	public void setCurrentVersion(boolean inCurrentVersion) {
		
		this.currentVersion = inCurrentVersion;
	}
	
	/**
	 * Set the customer number.
	 *
	 * Creation date: (2/25/2005 11:38:38 AM)
	 */
	public void setCustomerNumber(String inCustomerNumber) throws InvalidLengthException {
	
		if (inCustomerNumber.length() > 14)
			throw new InvalidLengthException(
					"inCustomerNumber", inCustomerNumber.length(), 14);

		this.customerNumber = inCustomerNumber;
	}
	
	/**
	 * Set the customer specification.
	 *
	 * Creation date: (2/25/2005 11:39:54 AM)
	 */
	public void setCustomerSpecification(String inCustomerSpecification) throws InvalidLengthException {
	
		if (inCustomerSpecification.length() > 10)
			throw new InvalidLengthException(
					"inCustomerSpecification", inCustomerSpecification.length(), 10);

		this.customerSpecification = inCustomerSpecification;
	}
	
	/**
	 * Set the deleted record switch.
	 *
	 * Creation date: (2/25/2005 11:42:28 AM)
	 */
	public void setDeletedRecord(boolean inDeletedRecord) {
		
		this.deletedRecord = inDeletedRecord;
	}
	
	/**
	 * Set the primary fruit variety.
	 *
	 * Creation date: (2/25/2005 11:44:57 AM)
	 */
	public void setFruitVariety(String inFruitVariety) throws InvalidLengthException {
	
		if (inFruitVariety.length() > 2)
			throw new InvalidLengthException(
					"inFruitVariety", inFruitVariety.length(), 2);

		this.fruitVariety = inFruitVariety;
	}
	
	/**
	 * Set the fruit variety editing code.
	 *
	 * Creation date: (2/25/2005 11:46:22 AM)
	 */
	public void setFruitVarietyEdit(String inFruitVarietyEdit) throws InvalidLengthException {
	
		if (inFruitVarietyEdit.length() > 1)
			throw new InvalidLengthException(
					"inFruitVarietyEdit", inFruitVarietyEdit.length(), 1);

		this.fruitVarietyEdit = inFruitVarietyEdit;
	}
	
	/**
	 * Set the general specification description.
	 *
	 * Creation date: (2/25/2005 11:47:34 AM)
	 */
	public void setGeneralDescription(String inDescription) throws InvalidLengthException {
	
		if (inDescription.length() > 30)
			throw new InvalidLengthException(
					"inDescription", inDescription.length(), 30);

		this.generalDescription = inDescription;
	}
	
	/**
	 * Set the customer number portion of the market/customer combination.
	 *
	 * Creation date: (2/25/2005 11:50:24 AM)
	 */
	public void setMarketCustomer(Integer inMarketCustomer) {
		
		this.marketCustomer = inMarketCustomer;
	}
	
	/**
	 * Set the market number.
	 *
	 * Creation date: (2/25/2005 11:52:11 AM)
	 */
	public void setMarketNumber(Integer inMarketNumber) {
		
		this.marketNumber = inMarketNumber;
	}
	
	/**
	 * Set the plant number.
	 *
	 * Creation date: (2/25/2005 2:14:56 PM)
	 */
	public void setPlantNumber(Integer inPlantNumber) {
		
		this.plantNumber = inPlantNumber;
	}
	
	/**
	 * Set the raw fruit specification code.
	 *
	 * Creation date: (2/25/2005 11:54:26 AM)
	 */
	public void setRawFruitSpecCode(String inRawFruitSpecCode) throws InvalidLengthException {
	
		if (inRawFruitSpecCode.length() > 5)
			throw new InvalidLengthException(
					"inRawFruitSpecCode", inRawFruitSpecCode.length(), 5);

		this.rawFruitSpecCode = inRawFruitSpecCode;
	}
	
	/**
	 * Set the raw fruit specification revision date.
	 *
	 * Creation date: (2/25/2005 11:54:57 AM)
	 */
	public void setRawFruitSpecDate(String inRawFruitSpecDate) {
		
		this.rawFruitSpecDate = GetDate.formatSetDate(inRawFruitSpecDate);
	}	
	
	/**
	 * Set the specification analytical reference code.
	 *
	 * Creation date: (2/25/2005 1:23:27 AM)
	 */
	public void setReferenceAnalCode(String inReferenceCode) throws InvalidLengthException {
	
		if (inReferenceCode.length() > 5)
			throw new InvalidLengthException(
					"inReferenceCode", inReferenceCode.length(), 5);

		this.referenceAnalCode = inReferenceCode;
	}
	
	/**
	 * Set the specification statement text reference code.
	 *
	 * Creation date: (2/25/2005 1:26:18 AM)
	 */
	public void setReferenceTextCode(String inReferenceCode) throws InvalidLengthException {
	
		if (inReferenceCode.length() > 5)
			throw new InvalidLengthException(
					"inReferenceCode", inReferenceCode.length(), 5);

		this.referenceTextCode = inReferenceCode;
	}
	
	/**
	 * Set the resource number.
	 *
	 * Creation date: (2/25/2005 1:28:00 AM)
	 */
	public void setResourceNumber(String inResourceNumber) throws InvalidLengthException {
	
		if (inResourceNumber.length() > 15)
			throw new InvalidLengthException(
					"inResourceNumber", inResourceNumber.length(), 15);

		this.resourceNumber = inResourceNumber;
	}
	
	/**
	 * Set the specification code.
	 *
	 * Creation date: (2/25/2005 1:30:55 AM)
	 */
	public void setSpecificationCode(String inSpecificationCode) throws InvalidLengthException {
	
		if (inSpecificationCode.length() > 5)
			throw new InvalidLengthException(
					"inSpecificationCode", inSpecificationCode.length(), 5);

		this.specificationCode = inSpecificationCode;
	}
	
	/**
	 * Set the specification revision date.
	 *
	 * Creation date: (2/25/2005 1:34:21 PM)
	 */
	public void setSpecificationDate(String inSpecificationDate) {
		
		this.specificationDate = GetDate.formatSetDate(inSpecificationDate);
	}
	
	/**
	 * Set the specification type.
	 *
	 * Creation date: (2/25/2005 1:39:09 AM)
	 */
	public void setSpecificationType(String inSpecificationType) throws InvalidLengthException {
	
		if (inSpecificationType.length() > 10)
			throw new InvalidLengthException(
					"inSpecificationType", inSpecificationType.length(), 10);

		this.specificationType = inSpecificationType;
	}	

	/**
	 * Set the specification status code.
	 *
	 * Creation date: (2/25/2005 1:44:57 AM)
	 */
	public void setStatusCode(String inStatusCode) throws InvalidLengthException {
	
		if (inStatusCode.length() > 2)
			throw new InvalidLengthException(
					"inStatusCode", inStatusCode.length(), 2);

		this.statusCode = inStatusCode;
	}
	
	/**
	 * Set the superseded revision date.
	 *
	 * Creation date: (2/25/2005 1:41:31 PM)
	 */
	public void setSupersededDate(String inSupersededDate) {
		
		this.supersededDate = GetDate.formatSetDate(inSupersededDate);
	}
	
	/**
	 * Set the date the record was last updated.
	 *
	 * Creation date: (2/25/2005 1:49:46 PM)
	 */
	public void setUpdateSpecDate(String inUpdateDate) {
		
		this.updateSpecDate = GetDate.formatSetDate(inUpdateDate);
	}
	
	/**
	 * Set the time of day the record was last updated.
	 *
	 * Creation date: (2/25/2005 1:50:00 PM)
	 */
	public void setUpdateSpecTime(String inUpdateTime) {
		
		this.updateSpecTime = TimeUtilities.formatSetTime(inUpdateTime);
	}
	
	/**
	 * Set the last user profile that updated the record.
	 *
	 * Creation date: (2/25/2005 1:51:25 PM)
	 */
	public void setUpdateSpecUser(String inUpdateUser) throws InvalidLengthException {
	
		if (inUpdateUser.length() > 10)
			throw new InvalidLengthException(
					"inUpdateUser", inUpdateUser.length(), 10);

		this.updateSpecUser = inUpdateUser;
	}
	
	/**
	 * Set the last user name that updated the record.
	 *
	 * Creation date: (2/25/2005 1:53:28 PM)
	 */
	public void setUpdateSpecUserName(String inUpdateUserName) throws InvalidLengthException {
	
		if (inUpdateUserName.length() > 50)
			throw new InvalidLengthException(
					"inUpdateUser", inUpdateUserName.length(), 50);

		this.updateSpecUserName = inUpdateUserName;
	}
	
	/**
	 * Set the workstation name that last updated the record.
	 *
	 * Creation date: (2/25/2005 1:56:31 AM)
	 */
	public void setUpdateSpecWorkstation(String inUpdateWorkstation) throws InvalidLengthException {
	
		if (inUpdateWorkstation.length() > 10)
			throw new InvalidLengthException(
					"inUpdateWorkstation", inUpdateWorkstation.length(), 10);

		this.updateSpecWorkstation = inUpdateWorkstation;
	}
	
	/**
	 * Set the array of acceptable fruit varieties for manufacturing.
	 *
	 * Creation date: (2/25/2005 2:04:55 PM)
	 */
	public void setVarietiesAcceptable(String[] inVarietiesAcceptable) {
		
		this.varietiesAcceptable = inVarietiesAcceptable;
	}
	
	/**
	 * Set the array of excluded fruit varieties for manufacturing.
	 *
	 * Creation date: (2/25/2005 2:05:24 PM)
	 */
	public void setVarietiesExcluded(String[] inVarietiesExcluded) {
		
		this.varietiesExcluded = inVarietiesExcluded;
	}
	
	/**
	 * Set the last user profile that updated the record.
	 *
	 * Creation date: (2/25/2005 2:08:44 PM)
	 */
	public void setWIPClassification(String inWIPClassification) throws InvalidLengthException {
	
		if (inWIPClassification.length() > 2)
			throw new InvalidLengthException(
					"inWIPClassification", inWIPClassification.length(), 2);

		this.wipClassification = inWIPClassification;
	}
	
	/**
	 * Update a revised specification.
	 *
	 * Creation date: (2/25/2005 3:15:00 PM)
	 */
	public Exception update(Specification updateInfo) {

		try {
					    	
			PreparedStatement updateSpec = (PreparedStatement) sqlUpdateSpec.pop();
		
			updateSpec.setString(1, "SH");
					
			if (updateInfo.deletedRecord)								
				updateSpec.setString(2, "D");
			else
				updateSpec.setString(2, " ");
						
			updateSpec.setString(3, updateInfo.statusCode);
			updateSpec.setString(4, updateInfo.specificationType);					
			updateSpec.setInt(5, updateInfo.marketNumber.intValue());
			updateSpec.setInt(6, updateInfo.marketCustomer.intValue());
			updateSpec.setString(7, updateInfo.specificationCode);
			updateSpec.setString(8, updateInfo.customerSpecification);
			updateSpec.setString(9, updateInfo.companyNumber);
			updateSpec.setString(10, updateInfo.customerNumber);
			updateSpec.setString(11, updateInfo.generalDescription);
			updateSpec.setString(12, updateInfo.resourceNumber);
			updateSpec.setString(13, "  ");
			updateSpec.setInt(14, updateInfo.plantNumber.intValue());					
			updateSpec.setString(15, updateInfo.fruitVariety);
			updateSpec.setString(16, updateInfo.varietiesExcluded[0]);
			updateSpec.setString(17, updateInfo.varietiesExcluded[1]);
			updateSpec.setString(18, updateInfo.varietiesExcluded[2]);
			updateSpec.setString(19, updateInfo.varietiesExcluded[3]);
			updateSpec.setString(20, updateInfo.varietiesExcluded[4]);
			updateSpec.setString(21, " ");
			updateSpec.setString(22, " ");
			updateSpec.setString(23, "  ");
			updateSpec.setString(24, "  ");
			updateSpec.setString(25, updateInfo.varietiesAcceptable[0]);
			updateSpec.setString(26, updateInfo.varietiesAcceptable[1]);
			updateSpec.setString(27, updateInfo.varietiesAcceptable[2]);
			updateSpec.setString(28, updateInfo.varietiesAcceptable[3]);
			updateSpec.setString(29, updateInfo.varietiesAcceptable[4]);
			updateSpec.setString(30, updateInfo.varietiesAcceptable[5]);
			updateSpec.setString(31, updateInfo.varietiesAcceptable[6]);
			updateSpec.setString(32, updateInfo.varietiesAcceptable[7]);
			updateSpec.setString(33, updateInfo.varietiesAcceptable[8]);
			updateSpec.setString(34, updateInfo.varietiesAcceptable[9]);					
			updateSpec.setString(35, updateInfo.updateSpecUser);
			updateSpec.setString(36, updateInfo.updateSpecWorkstation);
			updateSpec.setInt(37, updateInfo.updateSpecDate.intValue());
			updateSpec.setInt(38, updateInfo.updateSpecTime.intValue());
			updateSpec.setString(39, updateInfo.createSpecUser);
			updateSpec.setString(40, updateInfo.createSpecWorkstation);
			updateSpec.setInt(41, updateInfo.createSpecDate.intValue());
			updateSpec.setInt(42, updateInfo.createSpecTime.intValue());
			updateSpec.setString(43, updateInfo.referenceTextCode);
			updateSpec.setString(44, updateInfo.referenceAnalCode);					
			updateSpec.setInt(45, updateInfo.specificationDate.intValue());
			updateSpec.setInt(46, updateInfo.supersededDate.intValue());			
					
			if (updateInfo.currentVersion)								
				updateSpec.setInt(47, new Integer("1").intValue());
			else
				updateSpec.setInt(47, new Integer("0").intValue());
						
			updateSpec.setString(48, updateInfo.wipClassification);
			updateSpec.setString(49, updateInfo.fruitVarietyEdit);
			updateSpec.setString(50, updateInfo.rawFruitSpecCode);
			updateSpec.setInt(51, updateInfo.rawFruitSpecDate.intValue());					
					
			updateSpec.setString(52, updateInfo.specificationCode);
			updateSpec.setInt(53, updateInfo.specificationDate.intValue());							

			updateSpec.executeUpdate();

			sqlUpdateSpec.push(updateSpec);	
								
		}		
		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data.Specification.update(Class): " + e);
			return e;
		}
		
		return null;	
	}
	
	/**
	 * Update a specification.
	 *
	 * Creation date: (2/25/2005 3:15:00 PM)
	 */
	public Exception update(Vector inUpdateInfo) {

		try {	
					
			for (int x = 0; x < inUpdateInfo.size(); x ++) {				  	
			 
				Specification updateInfo = (Specification) inUpdateInfo.elementAt(x);
				                                      	  
				if ((updateInfo.specificationCode != null) && 		// Spec code required
				   (!updateInfo.specificationCode.equals(""))) {
					    	
					PreparedStatement updateSpec = (PreparedStatement) sqlUpdateSpec.pop();
		
					updateSpec.setString(1, "SH");
					
					if (updateInfo.deletedRecord)								
						updateSpec.setString(2, "D");
					else
						updateSpec.setString(2, " ");
						
					updateSpec.setString(3, updateInfo.statusCode);
					updateSpec.setString(4, updateInfo.specificationType);					
					updateSpec.setInt(5, updateInfo.marketNumber.intValue());
					updateSpec.setInt(6, updateInfo.marketCustomer.intValue());
					updateSpec.setString(7, updateInfo.specificationCode);
					updateSpec.setString(8, updateInfo.customerSpecification);
					updateSpec.setString(9, updateInfo.companyNumber);
					updateSpec.setString(10, updateInfo.customerNumber);
					updateSpec.setString(11, updateInfo.generalDescription);
					updateSpec.setString(12, updateInfo.resourceNumber);
					updateSpec.setString(13, "  ");
					updateSpec.setInt(14, updateInfo.plantNumber.intValue());					
					updateSpec.setString(15, updateInfo.fruitVariety);
					updateSpec.setString(16, updateInfo.varietiesExcluded[0]);
					updateSpec.setString(17, updateInfo.varietiesExcluded[1]);
					updateSpec.setString(18, updateInfo.varietiesExcluded[2]);
					updateSpec.setString(19, updateInfo.varietiesExcluded[3]);
					updateSpec.setString(20, updateInfo.varietiesExcluded[4]);
					updateSpec.setString(21, " ");
					updateSpec.setString(22, " ");
					updateSpec.setString(23, "  ");
					updateSpec.setString(24, "  ");
					updateSpec.setString(25, updateInfo.varietiesAcceptable[0]);
					updateSpec.setString(26, updateInfo.varietiesAcceptable[1]);
					updateSpec.setString(27, updateInfo.varietiesAcceptable[2]);
					updateSpec.setString(28, updateInfo.varietiesAcceptable[3]);
					updateSpec.setString(29, updateInfo.varietiesAcceptable[4]);
					updateSpec.setString(30, updateInfo.varietiesAcceptable[5]);
					updateSpec.setString(31, updateInfo.varietiesAcceptable[6]);
					updateSpec.setString(32, updateInfo.varietiesAcceptable[7]);
					updateSpec.setString(33, updateInfo.varietiesAcceptable[8]);
					updateSpec.setString(34, updateInfo.varietiesAcceptable[9]);					
					updateSpec.setString(35, updateInfo.updateSpecUser);
					updateSpec.setString(36, updateInfo.updateSpecWorkstation);
					updateSpec.setInt(37, updateInfo.updateSpecDate.intValue());
					updateSpec.setInt(38, updateInfo.updateSpecTime.intValue());
					updateSpec.setString(39, updateInfo.createSpecUser);
					updateSpec.setString(40, updateInfo.createSpecWorkstation);
					updateSpec.setInt(41, updateInfo.createSpecDate.intValue());
					updateSpec.setInt(42, updateInfo.createSpecTime.intValue());
					updateSpec.setString(43, updateInfo.referenceTextCode);
					updateSpec.setString(44, updateInfo.referenceAnalCode);					
					updateSpec.setInt(45, updateInfo.specificationDate.intValue());
					updateSpec.setInt(46, updateInfo.supersededDate.intValue());			
					
					if (updateInfo.currentVersion)								
						updateSpec.setInt(47, new Integer("1").intValue());
					else
						updateSpec.setInt(47, new Integer("0").intValue());
						
					updateSpec.setString(48, updateInfo.wipClassification);
					updateSpec.setString(49, updateInfo.fruitVarietyEdit);
					updateSpec.setString(50, updateInfo.rawFruitSpecCode);
					updateSpec.setInt(51, updateInfo.rawFruitSpecDate.intValue());					
					
					updateSpec.setString(52, updateInfo.specificationCode);
					updateSpec.setInt(53, updateInfo.specificationDate.intValue());							

					updateSpec.executeUpdate();

					sqlUpdateSpec.push(updateSpec);	
				}	
			}							
		}		
		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data.Specification.update(Vector): " + e);
			return e;
		}
		
		return null;	
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	/**
	 * Create SQL join statement for the Movex Item master.
	 *
	 * Creation date: (2/25/2009) tw
	 */
	public static String sqlJoinItem(SpecificationViewInquiry fromCriteria) {
	
		String sqlJoin = "";
		String lawson  = "M3DJDPRD.";	
		
		String sqlJoinCustomer   = "LEFT OUTER JOIN " + lawson + "MITMAS " + 
								   "ON IDTRSC = MMITNO ";
		
		try {			
			
			sqlJoin = sqlJoinCustomer;			
						
		}
		catch (Exception e) {
			System.out.println("Exception error Specification.sqlJoinCustomer(SpecificationViewInquiry): " + e);								
		}	
	
		return sqlJoin;	
	}		
	
}
