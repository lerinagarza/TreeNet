package com.treetop.controller.sublot;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanPeach;
import com.treetop.businessobjects.DateTime;
import com.treetop.controller.BaseViewBeanR4;
import com.treetop.controller.UrlPathMapping;
import com.treetop.services.ServiceSupplier;
import com.treetop.services.ServiceRawFruitPeachReceiving;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HtmlOption;
import com.treetop.utilities.html.HtmlSelect;
import com.treetop.utilities.html.HtmlSelect.DescriptionType;

@UrlPathMapping({"requestType","id","parameter"})
public class InqSubLot extends BaseViewBeanR4 {
	
	private String itemNumber		= "";
	private String lotNumber		= "";
	private	String fromDate			= "";
	private String toDate			= "";
	private String growerName		= "";
	private String supplierNumber	= "";
	private String loadNumber		= "";
	
	private String submitButton		= "";
	
	private boolean allowUpdate		= false;
	private boolean allowAdd		= false;
	
	private BeanPeach beanPeach 	= new BeanPeach();
	
	@Override
	public void validate() {
		
		if (this.getEnvironment().equals("")) {
			this.setEnvironment("PRD");
		}
		
		if (this.getFromDate().equals("") && !this.getToDate().equals("")) {
			this.setFromDate(this.getToDate());
		}
			
		if (!this.getFromDate().equals("") && this.getToDate().equals("")) {
			this.setToDate(this.getFromDate());
		}
			
		DateTime from = UtilityDateTime.getDateFromMMddyyyyWithSlash(this.getFromDate());
		if (from.getDateErrorMessage().equals("")) {
			this.setFromDate(from.getDateFormatyyyyMMdd());
		}
			
		DateTime to = UtilityDateTime.getDateFromMMddyyyyWithSlash(this.getToDate());
		if (to.getDateErrorMessage().equals("")) {
			this.setToDate(to.getDateFormatyyyyMMdd());
		}
		
	}
	
	/**
	 * Default Constructor
	 */
	public InqSubLot() {
	
	}
	
	/**
	 * Constructor with auto-populate
	 * @param request
	 */
	public InqSubLot(HttpServletRequest request) {
		this.populate(request);

	}
	
	/**
	 *      
	 * Will Return to the Screen,String which is the code for the drop down list 
	 * 		of PEACH items
	 *  
	 */
	public static String buildDropDownPeachItems(String environment, String item) {
		String returnDD = new String();
		try
		{
			Vector listDD = ServiceRawFruitPeachReceiving.getDropDownSinglePeachItems(environment);
			
			Vector listOptions = HtmlOption.convertDropDownSingleVector(listDD);			
			
			HtmlSelect hs = new HtmlSelect("itemNumber", "itemNumber", "", item,
										   "", false, DescriptionType.VALUE_DESCRIPTION);
			
			returnDD = DropDownSingle.buildDropDown(hs, listOptions);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return returnDD;
	}
	
	/**
	 *      
	 * Will Return to the Screen,String which is the code for the drop down list 
	 * 		of PEACH suppliers
	 *  
	 */
	public static String buildDropDownPeachSuppliers(String environment, String supplierNumber) {
		String returnDD = new String();
		try
		{
			Vector listDD = ServiceSupplier.getDropDownSinglePeachSuppliers(environment);
			
			Vector listOptions = HtmlOption.convertDropDownSingleVector(listDD);			
			
			HtmlSelect hs = new HtmlSelect("supplierNumber", "supplierNumber", "",supplierNumber,
										   "", false, DescriptionType.DESCRIPTION_VALUE);
			
			returnDD = DropDownSingle.buildDropDown(hs, listOptions);
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return returnDD;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getGrowerName() {
		return growerName;
	}

	public void setGrowerName(String growerName) {
		this.growerName = growerName;
	}

	public BeanPeach getBeanPeach() {
		return beanPeach;
	}

	public void setBeanPeach(BeanPeach beanPeach) {
		this.beanPeach = beanPeach;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getLoadNumber() {
		return loadNumber;
	}

	public void setLoadNumber(String loadNumber) {
		this.loadNumber = loadNumber;
	}

	public String getSubmitButton() {
		return submitButton;
	}

	public void setSubmitButton(String submitButton) {
		this.submitButton = submitButton;
	}

	/**
	* Determine Security for Raw Fruit Peach Receiving
	*    Send in:
	* 		request
	* 		response
	*       Update's the Class to reflect the security
	*       	make allowAdd true if allowed to ADD
	*           make allowUpdate true if allowed to UPDATE
	*
	* Creation date: (5/14/2013 -- TWalton)
	*/
	public void determineSecurityPeachReceiving(HttpServletRequest request,
								  				HttpServletResponse response) {
		try
		{
			 try
			 {
			    String[] rolesR = SessionVariables.getSessionttiUserRoles(request, response);
			    for (int xr = 0; xr < rolesR.length; xr++)
			    {
			       if (rolesR[xr].equals("8"))
			       {
			    	   this.setAllowAdd(true);
			    	   this.setAllowUpdate(true);
			       }
			    }
			 }
			 catch(Exception e)
			 {}
			 if (this.isAllowAdd() == false ||
				 this.isAllowUpdate() == false)
			 {
				   try
				   {
				     String[] groupsR = SessionVariables.getSessionttiUserGroups(request, response);
				     for (int xr = 0; xr < groupsR.length; xr++)
				     {
				           if (groupsR[xr].equals("138"))
				           {
				        	  this.setAllowAdd(true);
					    	  this.setAllowUpdate(true); 
				           }
				     }
				   }
				   catch(Exception e)
				   {}
			 }  
			
		}
		catch(Exception e)
		{
			System.out.println("Exception Occurred in InqSubLot.determineSecurityPeachReceiving(request, response)" + e);
		}
	    return;
	}

	public boolean isAllowUpdate() {
		return allowUpdate;
	}

	public void setAllowUpdate(boolean allowUpdate) {
		this.allowUpdate = allowUpdate;
	}

	public boolean isAllowAdd() {
		return allowAdd;
	}

	public void setAllowAdd(boolean allowAdd) {
		this.allowAdd = allowAdd;
	}

	
}
