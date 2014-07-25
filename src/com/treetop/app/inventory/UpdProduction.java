/*
 * Created on September 15, 2009
 *
 */

package com.treetop.app.inventory;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.treetop.businessobjectapplications.BeanInventory;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.ManufacturingOrder;
import com.treetop.services.ServiceItem;
import com.treetop.services.ServiceManufacturingOrder;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.HTMLHelpersMasking;
import com.treetop.viewbeans.BaseViewBeanR1;

/**
 * @author twalto
 * 
 */
public class UpdProduction extends BaseViewBeanR1  {
	
	public String requestType   = "";
	public String displayMessage = "";

	// Must have in Update View Bean
	public String updateUser = "";
	public String authorization = "";
	public String environment = "";

	
	// Fields on JSP
	
	public String manufacturingOrder	  = "";
	public String manufacturingOrderError = "";
	public String manufactureDate		  = "";
	public String manufactureDateError	  = "";
	public String itemNumber			  = "";
	public String itemNumberError		  = "";
	public String moItemError			  = "";
	public String defaultWarehouse		  = "";
	public String defaultLocation		  = "";
	public String chepPallets			  = "";
	public String chepPalletsError		  = "";
	public String pecoPallets			  = "";
	public String pecoPalletsError        = "";
	public String billOfLading			  = "";
	public String billOfLadingError       = "";
	
	
	public String lotSeriesBeginning	  = "";
	public String lotSeriesBeginningError = "";
	public String lotSeriesEnding		  = "";
	public String lotSeriesEndingError    = "";
	public String lotPrefix				  = "";
	
	// Button's on JSP
	public String getMOButton 		= "";
	public String getMOError 		= "";
	public String processLotsButton = "";
	public String processLotsError 	= "";
	
	public BeanInventory updBean = new BeanInventory();
	
	public Vector listLots = new Vector(); // Vector of UpdProductionLot
	// Determine the number of records to be displayed on the JSP
	private int recordCount = 28;
	
	
	
	
	public String buildParameterResend() {
		
		StringBuffer parms = new StringBuffer();
		parms.append("&environment=" + this.getEnvironment());
		parms.append("&manufacturingOrder=" + this.getManufacturingOrder());
		parms.append("&itemNumber=" + this.getItemNumber());
		parms.append("&lotSeriesBeginning=" + this.getLotSeriesBeginning());
		parms.append("&lotSeriesEnding=" + this.getLotSeriesEnding());
		parms.append("&lotPrefix=" + this.getLotPrefix());
		parms.append("&manufactureDate=" + this.getManufactureDate());

		
		return parms.toString();
	}
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		//Default Environment
		
		if (this.getEnvironment() == null || this.getEnvironment().equals("")) {
			this.setEnvironment("PRD");
		}
		
		//-------------------------------------------------------------------
		// ManufacturingOrder
		if (this.manufacturingOrder.trim().equals("") 
				|| this.manufacturingOrder.trim().equals("0")) {

			if (!this.getMOButton.equals("")) {
				this.setManufacturingOrderError("Manufacturing Order MUST be Chosen");
			}

		} else {

			this.setManufacturingOrderError(validateInteger(this.manufacturingOrder));
			if (this.getManufacturingOrderError().trim().equals("")) {
				try {
					this.setManufacturingOrderError(ServiceManufacturingOrder.verifyActiveOrderNumber(this.environment,
							this.manufacturingOrder));
				} catch (Exception mo) {
					this.setManufacturingOrderError(mo.toString());
				}
			}

			if (this.getManufacturingOrderError().trim().equals("")) {
				try {
					this.setManufacturingOrderError(ServiceManufacturingOrder.verifyPartner(this.environment,
							this.manufacturingOrder));
				} catch (Exception mo) {
					this.setManufacturingOrderError(mo.toString());
				}
			}

		}
//		-------------------------------------------------------------------
		// Item Number
		if (this.itemNumber.trim().equals("")) {
			
			if (!this.getMOButton.equals("")) {
				this.setItemNumberError("Item Number MUST be Chosen");
			}
			
		} else {
			
			if (this.getItemNumberError().trim().equals("")) {
				try {
					this.setItemNumberError(ServiceItem.verifyItem(this.environment, this.itemNumber));
				} catch (Exception itm) {
					this.setItemNumberError(itm.toString());
				}
			}
			
		}
//		-------------------------------------------------------------------
		// test if the Manufacturing Number and the Item Number Together are Valid
		if (this.getManufacturingOrderError().trim().equals("") &&
			this.getItemNumberError().trim().equals("")) {
			try {
				this.setMoItemError(ServiceManufacturingOrder.verifyActiveOrderNumberWithItem(this.environment, this.manufacturingOrder, this.itemNumber));
			} catch(Exception bth) {
				this.setMoItemError(bth.toString());
			}
			
			if (this.getMoItemError().trim().equals("")) {
				try {
				   this.setManufacturingOrderError(ServiceManufacturingOrder.verifyLocationOnOrder(this.environment, this.manufacturingOrder, this.itemNumber));
				} catch(Exception loc)	{
					this.setManufacturingOrderError(loc.toString());
				}
			}
		}
		
		//---------------------------------------------------------------------
		// test to make sure the entered Bill of Lading is a NUMBER
		if (this.getBillOfLading() == null ||
			this.getBillOfLading().equals(""))
			this.setBillOfLading("0");
		if (!this.getBillOfLading().equals("0"))
			this.setBillOfLadingError(validateInteger(this.getBillOfLading()));
		
		

		//---------------------------------------------------------------------
		// Testing Lot Series Entry
		if (this.getLotSeriesBeginning() != null && !this.getLotSeriesBeginning().equals("")) {
			try {
				Integer.parseInt(this.getLotSeriesBeginning());
			} catch (Exception e) {
				this.setLotSeriesBeginningError("Lot Series beginning must be a number");
			}
		}
		if (this.getLotSeriesEnding() != null && !this.getLotSeriesEnding().equals("")) {
			try {
				Integer.parseInt(this.getLotSeriesEnding());
			} catch (Exception e) {
				this.setLotSeriesEndingError("Lot Series ending must be a number");

			}
		}
		if (this.getLotSeriesBeginningError().equals("")
				&& this.getLotSeriesEndingError().equals("")) {
			try {
				
				int start = Integer.parseInt(this.getLotSeriesBeginning());
				int end = Integer.parseInt(this.getLotSeriesEnding());
				if (end < start) {
					this.setLotSeriesEndingError("The ending lot series number must be larger than the starting number");
				}
				
			} catch (Exception e) {
				
			}
		}
		
		//---------------------------------------------------------------------
		// Testing Default Manufacture Date
		if (this.getManufactureDate() != null 
				&& !this.getManufactureDate().equals("")) {
			try {
				
				DateTime dt = UtilityDateTime.getDateFromMMddyyyyWithSlash(this.getManufactureDate());
				if (!dt.getDateErrorMessage().equals("")) {
					this.setManufactureDateError(dt.getDateErrorMessage());
				}
				
			} catch (Exception e) {
				
			}
		}
			
		
		
		
		//-----------------------------------------------------------------------
		// Set the Error of the MO Section
		StringBuffer moError = new StringBuffer();
		moError.append(this.getManufacturingOrderError());
		if (!moError.toString().trim().equals(""))
		   moError.append("<br>");
		moError.append(this.getItemNumberError());
		if (!moError.toString().trim().equals(""))
			   moError.append("<br>");
		moError.append(this.getMoItemError());
		if (!moError.toString().trim().equals(""))
			   moError.append("<br>");
		moError.append(this.getBillOfLadingError());
		if (!moError.toString().trim().equals(""))
			   moError.append("<br>");
		moError.append(this.getLotSeriesBeginningError());
		if (!moError.toString().trim().equals(""))
			   moError.append("<br>");
		moError.append(this.getLotSeriesEndingError());
		if (!moError.toString().trim().equals(""))
			   moError.append("<br>");
		moError.append(this.getManufactureDateError());
		
		this.setGetMOError(moError.toString().trim());
		
		
		
		
		//*********************************************************************
		// Additional Tests For When Processing Lots
		if (!this.getProcessLotsButton().equals(""))
		{
			//---------------------------------------------------------------------
			// Chep Pallet Quantity must be a Whole Integer
			if (this.getChepPallets() == null ||
				this.getChepPallets().equals(""))
				this.setChepPallets("0");
			if (!this.getChepPallets().equals("0"))
				this.setChepPalletsError(validateInteger(this.getChepPallets()));
			//--------------------------------------------------------------------
			// Peco Pallet Quantity must be a Whole Integer
			if (this.getPecoPallets() == null ||
				this.getPecoPallets().equals(""))
				this.setPecoPallets("0");
			if (!this.getPecoPallets().equals("0"))
				this.setPecoPalletsError(validateInteger(this.getPecoPallets()));
		   //---------------------------------------------------------------------
		   // Set the Error of the Lot Section
			StringBuffer palletError = new StringBuffer();
			palletError.append(this.getChepPalletsError());
			if (!palletError.toString().trim().equals(""))
			   palletError.append("<br>");
			palletError.append(this.getPecoPalletsError());
			this.setProcessLotsError(palletError.toString().trim()); 
		}  
		//**********************************************************************
		
		return;
	}

	/*
	 * Send in a Business Object Use the fields from this Object to set into
	 * a View Bean for Update
	 *   This section will build the vector of Lots. 
	 *    Creation date: (11/17/2008 TWalton)
	 */
	public void loadFromBeanInventory(BeanInventory bi) {
		try {
			
			int start = 0;
			int end = 0;
			
			String[] lotSeries = null;
			
			boolean series = false;
			if (this.getProcessLotsButton().equals("")) {
				//setup lot series only if not processing, just getting ready to
				if (!this.getLotSeriesBeginning().equals("")
						&& !this.getLotSeriesEnding().equals("")) {
					
					try {
						start = Integer.parseInt(this.getLotSeriesBeginning());
						end = Integer.parseInt(this.getLotSeriesEnding());
					} catch (Exception e) {
						
					}
	
					int lotSeriesCount = end - start + 1;
					if (lotSeriesCount > 0) {
						this.recordCount = lotSeriesCount;
						series = true;
					}
				}
			}

			
			Vector buildLots = new Vector();
			ManufacturingOrder mo = new ManufacturingOrder();
			try {
			  mo = bi.getMoHeader();	
			} catch(Exception e) {
				
			}
			
			int additionalBlankRecords = this.recordCount - this.getListLots().size();
			
			if (!this.getProcessLotsError().trim().equals("") 
					&& !this.getLotSeriesBeginning().equals("")
					&& !this.getLotSeriesEnding().equals("")) {
				//if a lot series was entered and there is an error, don't add additional blank records
				additionalBlankRecords = 0;
			}
			
			if (this.getListLots().size() > 0) {
				buildLots = this.getListLots();
				this.setListLots(new Vector());
			}
			
			if (this.getProcessLotsButton().trim().equals("") ||
				(!this.getProcessLotsButton().trim().equals("") &&
				 !this.getProcessLotsError().trim().equals("")))
			{
				if (additionalBlankRecords > 0)
				{
				   for (int z = 0; z < additionalBlankRecords; z++)
				   {
					  UpdProductionLot upl = new UpdProductionLot();
					  if (z == 0)
					  {
						  this.setDefaultWarehouse(mo.getWarehouse().getWarehouse().trim());
						  this.setDefaultLocation(mo.getDefaultLocation().trim());
					  }
					  try
					  {
						 upl.setManufactureDate(this.getManufactureDate());
						 
						 if (series) {
							 upl.setLot(this.getLotPrefix() + String.valueOf(start));
							 start++;
						 } 
					     upl.setManufacturingOrder(mo.getOrderNumber().trim());
					     upl.setProductionLine(mo.getLine().trim());
					     upl.setShift(mo.getShift());	
					     upl.setWarehouse(mo.getWarehouse().getWarehouse().trim());
					     if (!mo.getWarehouse().getWarehouse().trim().equals("360"))
					        upl.setLocation(mo.getDefaultLocation().trim());
					     
					     upl.setDefaultLocation(mo.getDefaultLocation().trim());
					     
					     if (upl.getLocation().equals("")) {
					    	 upl.setLocation(upl.getDefaultLocation());
					     }
					     
					     upl.setQuantity(HTMLHelpersMasking.maskNumber(mo.getItem().getCasesPerPallet(), 0));
					     // 2/2/12 TWalton - change set it to blank
					     //upl.setManufactureDate(mo.getActualStartDate().getDateFormatMMddyyyySlash());
					  }
					  catch(Exception e)
					  {
						  System.out.println("UpdProduction.loadFromBeanInventory error: " + e);
					  }
					  buildLots.addElement(upl);
					  
				   }
				}
			}
			this.setListLots(buildLots);

		} catch (Exception e) {	}
		return;
	}
	
	
	/*
	 * Test and Validate fields for Lots, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void populateLots(HttpServletRequest request) {

		Vector listOfLots = new Vector();
		if (this.recordCount > 0)
		{
			for (int i = 0; i < this.recordCount; i++)
			{
				try
				{
					UpdProductionLot upl = new UpdProductionLot();
					
					upl.setLot(request.getParameter("lot" + i).toUpperCase());
										
					if (upl.getLot() != null &&
						!upl.getLot().trim().equals(""))
					{
						upl.setManufacturingOrder(this.getManufacturingOrder().trim());
						upl.setItemNumber(this.getItemNumber());
						upl.setUpdateUser(this.getUpdateUser());
						upl.setEnvironment(this.getEnvironment());
						upl.setWarehouse(request.getParameter("warehouse" + i));
						upl.setDefaultWarehouse(this.getDefaultWarehouse().trim());
						upl.setLocation(request.getParameter("location" + i));
						upl.setDefaultLocation(this.getDefaultLocation().trim());
						upl.setQuantity(request.getParameter("quantity" + i));
						upl.setManufactureDate(request.getParameter("manufactureDate" + i));
						upl.setBillOfLading(this.getBillOfLading());
						upl.validate();
						listOfLots.addElement(upl);
						this.setProcessLotsError(this.getProcessLotsError() + upl.getDisplayMessage());
					}
				}
				catch(Exception e)
				{}
			}
		}
		this.setListLots(listOfLots);
		return;
	}
	public String getDisplayMessage() {
		return displayMessage;
	}
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}
	public String getGetMOButton() {
		return getMOButton;
	}
	public void setGetMOButton(String getMOButton) {
		this.getMOButton = getMOButton;
	}
	public Vector getListLots() {
		return listLots;
	}
	public void setListLots(Vector listLots) {
		this.listLots = listLots;
	}
	public String getManufacturingOrder() {
		return manufacturingOrder;
	}
	public void setManufacturingOrder(String manufacturingOrder) {
		this.manufacturingOrder = manufacturingOrder;
	}
	public String getProcessLotsButton() {
		return processLotsButton;
	}
	public void setProcessLotsButton(String processLotsButton) {
		this.processLotsButton = processLotsButton;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public BeanInventory getUpdBean() {
		return updBean;
	}
	public void setUpdBean(BeanInventory updBean) {
		this.updBean = updBean;
	}
	public String getManufacturingOrderError() {
		return manufacturingOrderError;
	}
	public void setManufacturingOrderError(String manufacturingOrderError) {
		this.manufacturingOrderError = manufacturingOrderError;
	}
	public String getGetMOError() {
		return getMOError;
	}
	public void setGetMOError(String getMOError) {
		this.getMOError = getMOError;
	}
	public String getProcessLotsError() {
		return processLotsError;
	}
	public void setProcessLotsError(String processLotsError) {
		this.processLotsError = processLotsError;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getDefaultLocation() {
		return defaultLocation;
	}
	public void setDefaultLocation(String defaultLocation) {
		this.defaultLocation = defaultLocation;
	}
	public String getDefaultWarehouse() {
		return defaultWarehouse;
	}
	public void setDefaultWarehouse(String defaultWarehouse) {
		this.defaultWarehouse = defaultWarehouse;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getItemNumberError() {
		return itemNumberError;
	}
	public void setItemNumberError(String itemNumberError) {
		this.itemNumberError = itemNumberError;
	}
	public String getMoItemError() {
		return moItemError;
	}
	public void setMoItemError(String moItemError) {
		this.moItemError = moItemError;
	}
	public String getChepPallets() {
		return chepPallets;
	}
	public void setChepPallets(String chepPallets) {
		this.chepPallets = chepPallets;
	}
	public String getChepPalletsError() {
		return chepPalletsError;
	}
	public void setChepPalletsError(String chepPalletsError) {
		this.chepPalletsError = chepPalletsError;
	}
	public String getPecoPallets() {
		return pecoPallets;
	}
	public void setPecoPallets(String pecoPallets) {
		this.pecoPallets = pecoPallets;
	}
	public String getPecoPalletsError() {
		return pecoPalletsError;
	}
	public void setPecoPalletsError(String pecoPalletsError) {
		this.pecoPalletsError = pecoPalletsError;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public String getBillOfLading() {
		return billOfLading;
	}
	public void setBillOfLading(String billOfLading) {
		this.billOfLading = billOfLading;
	}
	public String getBillOfLadingError() {
		return billOfLadingError;
	}
	public void setBillOfLadingError(String billOfLadingError) {
		this.billOfLadingError = billOfLadingError;
	}
	public String getLotSeriesBeginning() {
		return lotSeriesBeginning;
	}
	public void setLotSeriesBeginning(String lotSeriesBeginning) {
		this.lotSeriesBeginning = lotSeriesBeginning;
	}
	public String getLotSeriesEnding() {
		return lotSeriesEnding;
	}
	public void setLotSeriesEnding(String lotSeriesEnding) {
		this.lotSeriesEnding = lotSeriesEnding;
	}
	public String getLotSeriesBeginningError() {
		return lotSeriesBeginningError;
	}
	public void setLotSeriesBeginningError(String lotSeriesBeginningError) {
		this.lotSeriesBeginningError = lotSeriesBeginningError;
	}
	public String getLotSeriesEndingError() {
		return lotSeriesEndingError;
	}
	public void setLotSeriesEndingError(String lotSeriesEndingError) {
		this.lotSeriesEndingError = lotSeriesEndingError;
	}
	public String getManufactureDate() {
		return manufactureDate;
	}
	public void setManufactureDate(String manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	public String getManufactureDateError() {
		return manufactureDateError;
	}
	public void setManufactureDateError(String manufactureDateError) {
		this.manufactureDateError = manufactureDateError;
	}

	public String getLotPrefix() {
		return lotPrefix;
	}

	public void setLotPrefix(String lotPrefix) {
		this.lotPrefix = lotPrefix;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

}
