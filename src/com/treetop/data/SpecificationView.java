package com.treetop.data;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import javax.sql.*;
import com.treetop.*;
/**
 * This object contains the view of manufacturing specification header controls, 
 * attribute test results, text documentation and inventory lot information.
 *
 * @author: David M Eisenheim
 *
 */
public class SpecificationView {
	
	
	// Data base view fields. 
	
	private   	Specification			specification;	
	private		Vector					analytical;
	private		Vector					documentation;	                
	private		Vector					inventory;


	// Additional fields.
	
	private static boolean persists   = false;
	
	/**
	 * Process all SQL results to retrieve manufacturing specification header controls,
	 * attribute test results, text documentation and inventory lot summary information  
	 * into a vector of view information.
	 *
	 * Creation date: (3/29/2005 11:02:14 AM)
	 */
	public static Vector findViewBySpec(SpecificationViewInquiry fromCriteria,
	                                    SpecificationViewInquiry toCriteria) {

		Vector specList    = new Vector();
		Vector noInventory = new Vector();
		Vector lotSummary  = new Vector();

		try {
			
			ResultSet specHeader = Specification.findSpecByRange(fromCriteria, toCriteria);
			
			if (specHeader != null) {
				
				// Process the SQL specification header result into the return view class. 
	          
				// Limit the count.
				int count = 0;
				
				try {
	        
					while (specHeader.next() && count < 50) {			
						count++;
						SpecificationView specView  = new SpecificationView();
	
						Specification spec = new Specification();
						spec.loadFields(specHeader);						
			
						String  specCode = spec.getSpecificationCode();
						Integer specDate = new Integer(specHeader.getInt("IDTRVD"));
												
						specView.setSpecification(spec);
						
						Vector anal = new Vector();
						Vector text = new Vector();
						
						if ((fromCriteria.getRequestType() != null) && 
						    (fromCriteria.getRequestType().toLowerCase().trim().equals ("detail"))) {						    							   
						  	anal = SpecificationAnalytical.findAnalBySpecByType(specCode, specDate);
							text = SpecificationDocumentation.findTextByCodeByDate(specCode, specDate);
						}
						
						specView.setAnalytical(anal);						
						specView.setDocumentation(text);
											
						//if (fromCriteria.getShowInventory() == null) //01/07/09 wth
							//specView.setInventory(noInventory); //01/07/09 wth
							
						//else { //01/07/09 wth
							
							//if (spec.getSpecificationType().toUpperCase().trim().equals ("DR")) { //01/07/09 wth
								//if ((fromCriteria.getShowAllRevisions() !=null) &&  //01/07/09 wth
									//(fromCriteria.getShowAllRevisions().toLowerCase().equals("show summary"))) //01/07/09 wth
									//lotSummary = InventoryLotLocationDetail. //01/07/09 wth
												 //findAllSumIngBySpecOnly(specCode); //01/07/09 wth
								//else //01/07/09 wth
									//lotSummary = InventoryLotLocationDetail. //01/07/09 wth
						                         //findAllSumIngBySpec(specCode, specDate); //01/07/09 wth
							//} //01/07/09 wth
						    //else { //01/07/09 wth
								//if ((fromCriteria.getShowAllRevisions() !=null) && //01/07/09 wth 
									//(fromCriteria.getShowAllRevisions().toLowerCase().equals("show summary"))) //01/07/09 wth
									//lotSummary = InventoryLotLocationDetail. //01/07/09 wth
								                 //findAllSumOthBySpecOnly(specCode); //01/07/09 wth
								//else //01/07/09 wth
									//lotSummary = InventoryLotLocationDetail. //01/07/09 wth
										         //findAllSumOthBySpec(specCode, specDate); //01/07/09 wth
						    //} //01/07/09 wth
						    
							//specView.setInventory(lotSummary); //01/07/09 wth
						//} //01/07/09 wth
						specView.setInventory(noInventory); //01/07/09 wth									
						specList.addElement(specView); 
					}
	    
				}
				catch (Exception e) {
					System.out.println("Exception error processing a result set " +
									   "(SpecificationView.findViewBySpec): " + e);
				}
				
				specHeader.close();       	    
			
			}	

		}
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data." + 
							   "SpecificationView.findViewBySpec(Class Class): " + e);
		}
		
		return specList;	                                     
	}
	
	/**
	 * Process all SQL results to retrieve manufacturing specification header controls,
	 * attribute test results, text documentation and inventory lot information into a 
	 * vector of view information.
	 *
	 * Creation date: (3/30/2005 2:34:02 PM)
	 */
	public static Vector findViewBySpecByLot(SpecificationViewInquiry fromCriteria) {

		Vector specList   = new Vector();
		Vector specHeader = new Vector();
		Vector inventory  = new Vector();
		
		SpecificationView specView  = new SpecificationView();

		try {
			
			if ((fromCriteria.getSpecificationCode() != null) &&
			    (fromCriteria.getSpecificationDate() != null)) {
			    	
				String  specCode = fromCriteria.getSpecificationCode();
			    Integer specDate = GetDate.formatSetDate(fromCriteria.getSpecificationDate());
			    
			    // Create specification class
			    
				specHeader = Specification.findSpecByCodeByDate(specCode, specDate);
				
				if (specHeader.size() > 0) {
					Specification spec = (Specification) specHeader.elementAt(0);					
					specView.setSpecification(spec);
					
					Vector anal = new Vector();
					Vector text = new Vector();
						
					if ((fromCriteria.getRequestType() != null) && 
						(fromCriteria.getRequestType().toLowerCase().trim().equals ("detail"))) {						    							   
						anal = SpecificationAnalytical.findAnalBySpecByType(specCode, specDate);
						text = SpecificationDocumentation.findTextByCodeByDate(specCode, specDate);
					}
						
					specView.setAnalytical(anal);						
					specView.setDocumentation(text);			
			    
			    	// Ingredient inventory
			    	
			    	if (spec.getSpecificationType().toUpperCase().trim().equals ("DR")) {
			    		
//			    		if (fromCriteria.getClassification() != null) {
//							String lotClass = fromCriteria.getClassification();
						// Inventory not valid at this time, may add later 2/2/09 TWalton	
//			    			if ((fromCriteria.getShowAllRevisions() !=null) && 
//			    			    (fromCriteria.getShowAllRevisions().toLowerCase().equals("show summary"))) 						
//								inventory = InventoryLotLocationDetail.findAllDtlIngLotBySpecOnlyByClass(
//						                                               specCode, lotClass);						
//						    else
//								inventory = InventoryLotLocationDetail.findAllDtlIngLotBySpecByClass(
//																	   specCode, specDate, lotClass); 					
//			    		}			    	
//			   	    	else {
//							if ((fromCriteria.getShowAllRevisions() !=null) && 
//				                (fromCriteria.getShowAllRevisions().toLowerCase().equals("show summary")))	
//								inventory = InventoryLotLocationDetail.findAllDtlIngLotBySpecOnly(specCode);
//							else
//								inventory = InventoryLotLocationDetail.findAllDtlIngLotBySpec(
//								                                       specCode, specDate);
//			   	    	}
			    	}
			    	
					// Other inventories (non-ingredient) 

			    	else {
			    		
//						if (fromCriteria.getClassification() != null) {
//							String lotClass = fromCriteria.getClassification();
							
//							if ((fromCriteria.getShowAllRevisions() !=null) && 
//								(fromCriteria.getShowAllRevisions().toLowerCase().equals("show summary")))						
//								inventory = InventoryLotLocationDetail.findAllDtlOthLotBySpecOnlyByClass(
//																       specCode, lotClass);
//							else
//								inventory = InventoryLotLocationDetail.findAllDtlOthLotBySpecByClass(
//														               specCode, specDate, lotClass);					
//						}			    	
//						else
//							if ((fromCriteria.getShowAllRevisions() !=null) && 
//								(fromCriteria.getShowAllRevisions().toLowerCase().equals("show summary")))						
//								inventory = InventoryLotLocationDetail.findAllDtlOthLotBySpecOnly(specCode);
//							else
//								inventory = InventoryLotLocationDetail.findAllDtlOthLotBySpec(
//																	   specCode, specDate);												       	
			    	}
						                                       
			      	specView.setInventory(inventory); 					
								
				specList.addElement(specView);
				
			    } 			    		
			}			

		}
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data." + 
							   "SpecificationView.findViewBySpecByLot(Class): " + e);
		}
		
		return specList;	                                     
	}		

	/**
	 * Instantiate the specification view.
	 * 
	 * Creation date: (3/29/2005 9:48:42 AM)
	 */
	public SpecificationView() {
		
		super();
		
		init();		
	}

	/**
	 * Main for testing methods.
	 *
	 * Creation date: (3/29/2005 19:53:18 AM)
	 */
	public static void main(String[] args) {
		
		try {

			SpecificationViewInquiry view1 = new SpecificationViewInquiry();
			SpecificationViewInquiry view2 = new SpecificationViewInquiry();					
			view1.setSpecificationCode("BAM02");
			view1.setSpecificationDate("04/07/2005");
			view1.setRequestType("detail");
			view1.setShowInventory("yes");
			view1.setShowAllRevisions("show Summary");
			Vector list = SpecificationView.findViewBySpec(view1, view2); 
			
			System.out.println("findViewBySpec successfull");
		}
		catch (Exception e) {
			System.out.println("Error: findViewBySpec: " + e);
		}
				

		try {

			SpecificationViewInquiry spec1 = new SpecificationViewInquiry();
			SpecificationViewInquiry spec2 = new SpecificationViewInquiry();				
			spec1.setSpecificationCode("OATS6");
			spec1.setSpecificationDate("03/25/2005");
			spec1.setShowInventory("yes");	
			spec1.setStatusCode("all");
			spec1.setCustomerStatus("all");
			spec1.setShowCustomers("all");
			spec1.setShowAllRevisions("all");
			spec1.setRequestType("detail");					
			Vector list = SpecificationView.findViewBySpec(spec1, spec2); 
			
			System.out.println("findViewBySpec successfull");
		}
		catch (Exception e) {
			System.out.println("Error: findViewBySpec: " + e);
		}
				
	}
	
	/**
	 * Retrieve the specification analytical attributes.
	 *
	 * Creation date: (3/31/2005 3:54:02 PM)
	 */
	public Vector getAnalytical() {

		return analytical;
	}
	
	/**
	 * Retrieve the specification text documentation.
	 *
	 * Creation date: (3/31/2005 4:16:11 PM)
	 */
	public Vector getDocumentation() {

		return documentation;
	}
	
	/**
	 * Retrieve the specification lot inventory.
	 *
	 * Creation date: (3/29/2005 10:03:05 AM)
	 */
	public Vector getInventory() {

		return inventory;
	}
	
	/**
	 * Retrieve the specification control header class.
	 *
	 * Creation date: (3/29/2005 10:02:45 AM)
	 */
	public Specification getSpecification() {

		return specification;
	}
	
	/**
	 * SQL definition and initialization processes.
	 *
	 * Creation date: (3/29/2005 9:55:27 AM)
	 */
	public void init() {	
	 
	
		// Test for prior initialization.
	
		if (persists == false) {	
			persists = true;	   
	        	
		}
	
	}
	
	/**
	 * Set the specification analytical attributes.
	 *
	 * Creation date: (3/31/2005 3:56:41 PM)
	 */
	public void setAnalytical(Vector inAnalytical) {
		
		this.analytical = inAnalytical;
	}
	
	/**
	 * Set the specification text documentation.
	 *
	 * Creation date: (3/31/2005 4:18:02 PM)
	 */
	public void setDocumentation(Vector inDocumentation) {
		
		this.documentation = inDocumentation;
	}
	
	/**
	 * Set the specification lot inventory.
	 *
	 * Creation date: (3/29/2005 10:06:45 AM)
	 */
	public void setInventory(Vector inInventory) {
		
		this.inventory = inInventory;
	}
	
	/**
	 * Set the specification control header class.
	 *
	 * Creation date: (3/29/2005 10:06:45 AM)
	 */
	public void setSpecification(Specification inSpecification) {
		
		this.specification = inSpecification;
	}

}
