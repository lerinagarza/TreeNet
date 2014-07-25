/*
 * Created on Jul 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package com.treetop.app.item;

import java.math.BigDecimal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.app.gtin.UpdGTIN;
import com.treetop.businessobjectapplications.BeanItem;
import com.treetop.businessobjects.*;
import com.treetop.data.*;
import com.treetop.services.*;
import com.treetop.viewbeans.BaseViewBeanR2;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.utilities.html.*;
import com.treetop.utilities.*;

/**
 * @author twalto
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UpdItem extends BaseViewBeanR2 {

	// Must have in Update View Bean
	public String updateUser 		= "";
	
	public String item 				= "";
	public String itemError 		= "";
	
	//public String recordType		= "MW";
	//public String status            = "";
	
	public String aliasCheckBox		= "";

	public String manufacturer      = ""; //Produce under which code
	public String length			= "0";
	public String lengthError		= "";
	public String width				= "0";
	public String widthError		= "";
	public String height			= "0";
	public String heightError		= "";
	
	public String itemReplacing		= "";
	public String itemReplacingError = "";
	public String sampleOrderDesc	= "";
	
	public String pause             = "";
	
	
	//** UPC & GTIN **
	// This field will be used to determine whether or NOT to 
	//    Load the UPC and GTIN Fields
	public String announced       	= ""; 
	public String labelUPC        	= "";
	public String labelUPCA       	= ""; //Section of labelUPC
	public String labelUPCB       	= ""; //Section of labelUPC
	public String labelUPCC       	= ""; //Section of labelUPC
	public String labelUPCD       	= ""; //Section of labelUPC
	public String errorLabelUPC   	= ""; //must be NUMERIC
	public String caseUPC         	= "";
	public String caseUPCA        	= ""; //Section of caseUPC
	public String caseUPCB        	= ""; //Section of caseUPC
	public String caseUPCC        	= ""; //Section of caseUPC
	public String caseUPCD        	= ""; //Section of caseUPC
	public String errorCaseUPC    	= ""; // must be NUMERIC
	public String carrierUPC      	= ""; // UPC that is applied to the Dog Bone Carrier
	public String carrierUPCA     	= ""; //Section of carrierUPC
	public String carrierUPCB     	= ""; //Section of carrierUPC
	public String carrierUPCC     	= ""; //Section of carrierUPC
	public String carrierUPCD     	= ""; //Section of carrierUPC
	public String errorCarrierUPC 	= ""; // must be NUMERIC	
	public String wrapUPC    	  	= ""; // UPC that is applied to the Wrap
	public String wrapUPCA     		= ""; //Section of wrapUPC
	public String wrapUPCB     		= ""; //Section of wrapUPC
	public String wrapUPCC     		= ""; //Section of wrapUPC
	public String wrapUPCD     		= ""; //Section of wrapUPC
	public String errorWrapUPC		= ""; // must be NUMERIC
	public String palletGTIN      	= "";
	public String palletGTINDesc  	= "";
	public String palletGTINA     	= ""; //Section of palletGTIN
	public String palletGTINB     	= ""; //Section of palletGTIN
	public String palletGTINC     	= ""; //Section of palletGTIN
	public String palletGTIND     	= ""; //Section of palletGTIN
	public String errorPalletGTIN 	= ""; // must be NUMERIC
		
	public String flagOPN		  	= "";
	public String flagFPK		  	= "";
	public String flagJCE		  	= "";
	public String flagCAR			= "";
	public String flagSingleStrength= "";
	public String flagClub			= "";
	public String flagAllergen		= ""; 
	
	public String itemGroup			= "";
	public String aliasRPT1			= "";
	public String clubItemNumber	= "";
	
	public String dateInitiated		  = "0";
	public String salesPerson		  = "";
	public String customerServiceTeam = "";
	
	public String planner		  	= "";
	public String planningCategory	= ""; // Not currently using, switched thought to be Item Group
	
	public String supplierSummaryURL			= ""; // will be stored in the comment file with other URLS
	public String supplierSummaryURLRemove		= ""; 
	
	public String saveButton = "";
	public String pauseButton = "";
	public String unpauseButton = "";
	public String restartButton = "";
	public String updateType = "all"; // for use with security.
//	 responsibility
	public String group = "";
	public String areYouOwner = "";
	public String areEmailsComplete = "";
	
	//public String addNew = "";
	
	public Vector listReport = null;
	public Vector functions = null;
	
	public String estTargetDate = "";

	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		if (this.getEnvironment().trim().equals(""))
			this.setEnvironment("PRD");
		if (!item.trim().equals(""))
		{
			try
			{
				CommonRequestBean crb = new CommonRequestBean();
				crb.setCompanyNumber(this.getCompany().trim());
				crb.setEnvironment(this.getEnvironment());
				crb.setIdLevel1(this.getItem().trim());
				
				// test if it has a NEW Item record
				String noRecord = ServiceItem.verifyNewItem(this.getEnvironment(), this.getItem().trim());
				if (!noRecord.equals(""))
				  crb.setUser(this.getUpdateUser());
				
		   		//this.setItemError(ServiceItem.verifyItem(this.getEnvironment(), item.trim()));
				this.setItemError(ServiceItem.verifyItem(crb));
			}
		   	catch(Exception e)
			{
//		   	 Just Catch the error, do not need to do anything with it.
			}
		   	if (!itemError.trim().equals(""))
		   		this.setDisplayMessage(itemError.trim());
		
			//----------------------------------------------
			// LABEL UPC - Combination
			labelUPC  = labelUPCA + labelUPCB + labelUPCC;
			labelUPCD = "";
			if (labelUPC.length() >  0)
			{
				errorLabelUPC = addFormatToErrorForDisplay(validateInteger(labelUPC.trim()));
				try {
			    	if (labelUPC.length() == 11 || 
			    		labelUPC.length() == 10)
			    		labelUPC = ServiceItem.checkDigitUPC(labelUPC);
			    } catch (Exception e) {
			    	labelUPC = labelUPCA + labelUPCB + labelUPCC + labelUPCD;
			    }
			}
			//-----------------------------------------------
			// CASE UPC - Combination
			caseUPC  = caseUPCA + caseUPCB + caseUPCC;
			caseUPCD = "";
			if (caseUPC.length() >  0)
			{
				errorCaseUPC = addFormatToErrorForDisplay(validateInteger(caseUPC));
				try {
			    	if (caseUPC.length() == 11 || 
			    		caseUPC.length() == 10)
			    		caseUPC = ServiceItem.checkDigitUPC(caseUPC);
			    } catch (Exception e) {
			    	caseUPC = caseUPCA + caseUPCB + caseUPCC + caseUPCD;
			    }
			}
			//-----------------------------------------------
			// CARRIER UPC - Combination
			carrierUPC  = carrierUPCA + carrierUPCB + carrierUPCC;
			carrierUPCD = "";
			if (carrierUPC.length() >  0)
			{
				errorCarrierUPC = addFormatToErrorForDisplay(validateInteger(carrierUPC));
				try {
			    	if (carrierUPC.length() == 11 || 
			    		carrierUPC.length() == 10)
			    		carrierUPC = ServiceItem.checkDigitUPC(carrierUPC);
			    } catch (Exception e) {
			    	carrierUPC = carrierUPCA + carrierUPCB + carrierUPCC + carrierUPCD;
			    }
			}
			//-----------------------------------------------
			// WRAP UPC - Combination
			wrapUPC  = wrapUPCA + wrapUPCB + wrapUPCC;
			wrapUPCD = "";
			if (wrapUPC.length() >  0)
			{
				errorWrapUPC = addFormatToErrorForDisplay(validateInteger(wrapUPC));
				try {
			    	if (wrapUPC.length() == 11 || 
			    		wrapUPC.length() == 10)
			    		wrapUPC = ServiceItem.checkDigitUPC(wrapUPC);
			    } catch (Exception e) {
			    	wrapUPC = wrapUPCA + wrapUPCB + wrapUPCC + wrapUPCD;
			    }
			}
			//-----------------------------------------------
			// Pallet GTIN - Combination
			palletGTIN = palletGTINA + palletGTINB + palletGTINC;
			palletGTIND = "";
			if (palletGTIN.length() > 0)
			{
				errorPalletGTIN = addFormatToErrorForDisplay(validateInteger(palletGTIN));
				try {
					if (palletGTIN.length() == 13)
						palletGTIN = ServiceGTIN.checkDigit14(palletGTIN);
				} catch (Exception e) {
					palletGTIN = palletGTINA + palletGTINB + palletGTINC + palletGTIND;
				}
			}
			//-----------------------------------------------
			// Length
			if (this.getLength().trim().equals(""))
				this.setLength("0");
				
			if (!this.getLength().trim().equals("0") &&
				!this.getLength().trim().equals("0.00000"))
			{
				this.setLengthError(this.validateBigDecimal(this.getLength()));
				if (!this.getLengthError().trim().equals(""))
					this.setDisplayMessage(this.getDisplayMessage() + this.getLengthError());
			}
			//-----------------------------------------------
			// Width
			if (this.getWidth().trim().equals(""))
				this.setWidth("0");
				
			if (!this.getWidth().trim().equals("0") &&
				!this.getWidth().trim().equals("0.00000"))
			{
				this.setWidthError(this.validateBigDecimal(this.getWidth()));
				if (!this.getWidthError().trim().equals(""))
					this.setDisplayMessage(this.getDisplayMessage() + this.getWidthError());
			}
			//-----------------------------------------------
			// Height
			if (this.getHeight().trim().equals(""))
				this.setHeight("0");
				
			if (!this.getHeight().trim().equals("0") &&
				!this.getHeight().trim().equals("0.00000"))
			{
				this.setHeightError(this.validateBigDecimal(this.getHeight()));
				if (!this.getHeightError().trim().equals(""))
					this.setDisplayMessage(this.getDisplayMessage() + this.getHeightError());
			}
			//-----------------------------------------------
			// Replaced Item
			if (!this.getItemReplacing().trim().equals(""))
			{
				CommonRequestBean crb = new CommonRequestBean();
				crb.setEnvironment(this.getEnvironment());
				crb.setCompanyNumber(this.getCompany());
				crb.setIdLevel1(this.getItemReplacing());
				try{
					this.setItemReplacingError(ServiceItem.verifyItem(crb));
				}catch(Exception e)
				{}
				if (!this.getItemReplacingError().trim().equals(""))
					this.setDisplayMessage(this.getDisplayMessage() + this.getItemReplacingError());
			}
			
		 } // end of the only Validate on a SAVE	
		
		if (this.saveButton.trim().equals("") && this.getEstTargetDate().trim().equals(""))
		{
			DateTime dtNow = UtilityDateTime.getSystemDate();
			this.setEstTargetDate(dtNow.getDateFormatMMddyyyySlash());
		}
		return;
	}
	/**
	 * Determine which Group the Tickler File will Pull From
	 * 
	 * Creation date: (4/7/2008 -- TWalton)
	 * 6/20/11 TW-- added, 120,125,140,150,210
	 * 8/5/13  TW-- added, 180-188,500,105
	 */
	public static String determineGroup(Item inClass) {
		String returnValue = "";
		try {
			//** CPG Finished Good Items
			if (inClass.getItemType().equals("100"))
				returnValue = "100-CPGItem";
			
			//** Ocean Spray Finished Goods
			if (inClass.getItemType().equals("105"))
				returnValue = "105-OceanSpray";
						
			if (inClass.getItemType().equals("110"))
			{// Change from using product Group 400 to using product group 800
				// 8/5/13 - TWalton -- Added Make Buy code if
				if (inClass.getProductGroup().equals("800") ||
					inClass.getMakeBuyCode().equals("2"))	
				   returnValue = "110-CPACK-SUPPLY";
				else
				   returnValue = "110-CPACK-FG";
				  
			}
			if (inClass.getItemType().equals("120"))
				returnValue = "120-Dried-Frozen";
			if (inClass.getItemType().equals("125"))
				returnValue = "125-Formulated";
			if (inClass.getItemType().equals("130"))
				returnValue = "130-Fresh Slice";
			if (inClass.getItemType().equals("140"))
				returnValue = "140-Puree";
			//** Concentrate Items
			if (inClass.getItemType().equals("150"))
			{	
			   if (inClass.getMakeBuyCode().equals("2"))
			      returnValue = "150-Conc-Purch";
			   else
				  returnValue = "150-Conc-Manufactured";
			}
			//** Ocean Spray Concentrate Patronage Items
			if (inClass.getItemType().equals("152"))
		       returnValue = "150-Conc-Purch";
			
			// Line Tanks
			if (inClass.getItemType().equals("180") ||
				inClass.getItemType().equals("181") ||
				inClass.getItemType().equals("182") ||
				inClass.getItemType().equals("183") ||
				inClass.getItemType().equals("184") ||
				inClass.getItemType().equals("185") ||
				inClass.getItemType().equals("186") ||
				inClass.getItemType().equals("187") ||
				inClass.getItemType().equals("188")	)
			   returnValue = "180-188-LineTank";
			
			if (inClass.getItemType().equals("200") ||
				inClass.getItemType().equals("205"))
			   returnValue = "200-RawFruit";
			
			//** Intermediate (Processed Fruit) items
			if (inClass.getItemType().equals("210"))
			{
			   if (inClass.getMakeBuyCode().equals("2"))
			      returnValue = "210-ProcFruit-Purch";
			   else
				  returnValue = "210-ProcFruit-Manufactured";
			}
			//** Packaging Supply Items
			if (inClass.getItemType().equals("250"))
			   returnValue = "250-Packaging";
			//** other Supply Items
			if (inClass.getItemType().equals("260") ||
				inClass.getItemType().equals("270") ||
				inClass.getItemType().equals("280"))
			   returnValue = "260-290-Ingredients";
			
			if (inClass.getItemType().equals("290"))
			   returnValue = "110-CPACK-SUPPLY";
			
			if (inClass.getItemType().equals("500"))
				   returnValue = "500-TestItem";
			
			//** Plant Equipment 
			if (inClass.getItemType().equals("900"))
			   returnValue = "900-Equipment";
			//** Maintenance Parts
			if (inClass.getItemType().equals("950"))
			   returnValue = "950-Parts";
			
		}
		catch(Exception e)
		{}
		return returnValue;
	}
	/**
	 * Go to the ServiceUser and get the LONG Name of this user
	 * 
	 * Creation date: (4/7/2008 -- TWalton)
	 */
	public static String determineLongName(String environ, String profile) {
		String returnValue = "";
		try {
			returnValue = ServiceUser.returnNameFromM3User(environ, profile);
		}
		catch(Exception e)
		{}
		return returnValue;
	}
	/**
	 * @return
	 */
	public String getSaveButton() {
		return saveButton;
	}

	/**
	 * @param string
	 */
	public void setSaveButton(String string) {
		saveButton = string;
	}

	/**
	 * @return
	 */
	public String getUpdateType() {
		return updateType;
	}

	/**
	 * @param string
	 */
	public void setUpdateType(String string) {
		updateType = string;
	}

	/**
	 * @return Returns the group.
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            The group to set.
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Build drop down lists. Send in: request response Return: String -- Can
	 * Change == Y/N
	 * 
	 * Creation date: (12/21/2005 1:20:29 PM -- TWalton)
	 */
	public static String getSecurity(HttpServletRequest request,
									 HttpServletResponse response,
									 String itemResponsible) {

		String returnString = "N";
		try {
			String[] rolesR = SessionVariables.getSessionttiUserRoles(request,
					response);
			for (int xr = 0; xr < rolesR.length; xr++) {
				if (rolesR[xr].equals("8"))
					returnString = "Y";
			}
		} catch (Exception e) {
		}
		if (returnString.equals("N")) {
			try {
				String userProfile = SessionVariables.getSessionttiProfile(
						request, response);
				if (userProfile.trim().equals(itemResponsible))
					returnString = "Y";
				
				//UserFile thisUser = new UserFile();
				//Vector userList = thisUser.findUsersByGroup("40");
				// New Item Project Owner Group
				//for (int x = 0; x < userList.size(); x++) {
					//thisUser = (UserFile) userList.elementAt(x);
					//if (userProfile.trim().equals(thisUser.getUserName().trim()))
						//returnString = "Y";
				//}
			} catch (Exception e) {
			}
		}
		return returnString;
	}

	/**
	 * Find out if whoever is signed on has authority to change/add Aging
	 * Buckets RETURN ReadONLY Y or N Creation date: (1/13/2006 3:34:29 PM --
	 * TWalton)
	 */
	public static String getSecurityAging(HttpServletRequest request,
			HttpServletResponse response) {

		String returnString = "Y";
		try {
			String[] rolesR = SessionVariables.getSessionttiUserRoles(request,
					response);
			for (int xr = 0; xr < rolesR.length; xr++) {
				if (rolesR[xr].equals("8"))
					returnString = "N";
			}
		} catch (Exception e) {
		}
		if (returnString.equals("Y")) {
			try {

				String userProfile = SessionVariables.getSessionttiProfile(
						request, response);
				UserFile thisUser = new UserFile();
				Vector userList = thisUser.findUsersByGroup("45"); // New Item
				// Project
				// Owner
				// Group
				for (int x = 0; x < userList.size(); x++) {
					thisUser = (UserFile) userList.elementAt(x);
					if (userProfile.trim().equals(thisUser.getUserName().trim()))
						returnString = "N";
				}
			} catch (Exception e) {
			}
		}
		return returnString;
	}

	/**
	 * @return Returns the updateUser.
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * @param updateUser The updateUser to set.
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * @return Returns the item.
	 */
	public String getItem() {
		return item;
	}
	/**
	 * @param item The item to set.
	 */
	public void setItem(String item) {
		this.item = item;
	}
	/**
	 * @return Returns the itemError.
	 */
	public String getItemError() {
		return itemError;
	}
	/**
	 * @param itemError The itemError to set.
	 */
	public void setItemError(String itemError) {
		this.itemError = itemError;
	}
	/**
	 * @return Returns the listReport.
	 */
	public Vector getListReport() {
		return listReport;
	}
	/**
	 * @param listReport The listReport to set.
	 */
	public void setListReport(Vector listReport) {
		this.listReport = listReport;
	}


	/**
	 * @return Returns the announced.
	 */
	public String getAnnounced() {
		return announced;
	}
	/**
	 * @param announced The announced to set.
	 */
	public void setAnnounced(String announced) {
		this.announced = announced;
	}
	/**
	 * @return Returns the caseUPC.
	 */
	public String getCaseUPC() {
		return caseUPC;
	}
	/**
	 * @param caseUPC The caseUPC to set.
	 */
	public void setCaseUPC(String caseUPC) {
		this.caseUPC = caseUPC;
	}
	/**
	 * @return Returns the caseUPCA.
	 */
	public String getCaseUPCA() {
		return caseUPCA;
	}
	/**
	 * @param caseUPCA The caseUPCA to set.
	 */
	public void setCaseUPCA(String caseUPCA) {
		this.caseUPCA = caseUPCA;
	}
	/**
	 * @return Returns the caseUPCB.
	 */
	public String getCaseUPCB() {
		return caseUPCB;
	}
	/**
	 * @param caseUPCB The caseUPCB to set.
	 */
	public void setCaseUPCB(String caseUPCB) {
		this.caseUPCB = caseUPCB;
	}
	/**
	 * @return Returns the caseUPCC.
	 */
	public String getCaseUPCC() {
		return caseUPCC;
	}
	/**
	 * @param caseUPCC The caseUPCC to set.
	 */
	public void setCaseUPCC(String caseUPCC) {
		this.caseUPCC = caseUPCC;
	}
	/**
	 * @return Returns the caseUPCD.
	 */
	public String getCaseUPCD() {
		return caseUPCD;
	}
	/**
	 * @param caseUPCD The caseUPCD to set.
	 */
	public void setCaseUPCD(String caseUPCD) {
		this.caseUPCD = caseUPCD;
	}
	/**
	 * @return Returns the errorCaseUPC.
	 */
	public String getErrorCaseUPC() {
		return errorCaseUPC;
	}
	/**
	 * @param errorCaseUPC The errorCaseUPC to set.
	 */
	public void setErrorCaseUPC(String errorCaseUPC) {
		this.errorCaseUPC = errorCaseUPC;
	}
	/**
	 * @return Returns the errorLabelUPC.
	 */
	public String getErrorLabelUPC() {
		return errorLabelUPC;
	}
	/**
	 * @param errorLabelUPC The errorLabelUPC to set.
	 */
	public void setErrorLabelUPC(String errorLabelUPC) {
		this.errorLabelUPC = errorLabelUPC;
	}
	/**
	 * @return Returns the errorPalletGTIN.
	 */
	public String getErrorPalletGTIN() {
		return errorPalletGTIN;
	}
	/**
	 * @param errorPalletGTIN The errorPalletGTIN to set.
	 */
	public void setErrorPalletGTIN(String errorPalletGTIN) {
		this.errorPalletGTIN = errorPalletGTIN;
	}
	/**
	 * @return Returns the functions.
	 */
	public Vector getFunctions() {
		return functions;
	}
	/**
	 * @param functions The functions to set.
	 */
	public void setFunctions(Vector functions) {
		this.functions = functions;
	}
	/**
	 * @return Returns the labelUPC.
	 */
	public String getLabelUPC() {
		return labelUPC;
	}
	/**
	 * @param labelUPC The labelUPC to set.
	 */
	public void setLabelUPC(String labelUPC) {
		this.labelUPC = labelUPC;
	}
	/**
	 * @return Returns the labelUPCA.
	 */
	public String getLabelUPCA() {
		return labelUPCA;
	}
	/**
	 * @param labelUPCA The labelUPCA to set.
	 */
	public void setLabelUPCA(String labelUPCA) {
		this.labelUPCA = labelUPCA;
	}
	/**
	 * @return Returns the labelUPCB.
	 */
	public String getLabelUPCB() {
		return labelUPCB;
	}
	/**
	 * @param labelUPCB The labelUPCB to set.
	 */
	public void setLabelUPCB(String labelUPCB) {
		this.labelUPCB = labelUPCB;
	}
	/**
	 * @return Returns the labelUPCC.
	 */
	public String getLabelUPCC() {
		return labelUPCC;
	}
	/**
	 * @param labelUPCC The labelUPCC to set.
	 */
	public void setLabelUPCC(String labelUPCC) {
		this.labelUPCC = labelUPCC;
	}
	/**
	 * @return Returns the labelUPCD.
	 */
	public String getLabelUPCD() {
		return labelUPCD;
	}
	/**
	 * @param labelUPCD The labelUPCD to set.
	 */
	public void setLabelUPCD(String labelUPCD) {
		this.labelUPCD = labelUPCD;
	}
	/**
	 * @return Returns the manufacturer.
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer The manufacturer to set.
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	/**
	 * @return Returns the palletGTIN.
	 */
	public String getPalletGTIN() {
		return palletGTIN;
	}
	/**
	 * @param palletGTIN The palletGTIN to set.
	 */
	public void setPalletGTIN(String palletGTIN) {
		this.palletGTIN = palletGTIN;
	}
	/**
	 * @return Returns the palletGTINA.
	 */
	public String getPalletGTINA() {
		return palletGTINA;
	}
	/**
	 * @param palletGTINA The palletGTINA to set.
	 */
	public void setPalletGTINA(String palletGTINA) {
		this.palletGTINA = palletGTINA;
	}
	/**
	 * @return Returns the palletGTINB.
	 */
	public String getPalletGTINB() {
		return palletGTINB;
	}
	/**
	 * @param palletGTINB The palletGTINB to set.
	 */
	public void setPalletGTINB(String palletGTINB) {
		this.palletGTINB = palletGTINB;
	}
	/**
	 * @return Returns the palletGTINC.
	 */
	public String getPalletGTINC() {
		return palletGTINC;
	}
	/**
	 * @param palletGTINC The palletGTINC to set.
	 */
	public void setPalletGTINC(String palletGTINC) {
		this.palletGTINC = palletGTINC;
	}
	/**
	 * @return Returns the palletGTIND.
	 */
	public String getPalletGTIND() {
		return palletGTIND;
	}
	/**
	 * @param palletGTIND The palletGTIND to set.
	 */
	public void setPalletGTIND(String palletGTIND) {
		this.palletGTIND = palletGTIND;
	}
	/**
	 * @return Returns the palletGTINDesc.
	 */
	public String getPalletGTINDesc() {
		return palletGTINDesc;
	}
	/**
	 * @param palletGTINDesc The palletGTINDesc to set.
	 */
	public void setPalletGTINDesc(String palletGTINDesc) {
		this.palletGTINDesc = palletGTINDesc;
	}
	/**
	 * Build drop down  list of the Manufacturer
	 *    This information is displayed from a file on the OLD Box
	 * 
	 * Creation date: (4/20/2008 -- TWalton)
	 */
	public static String buildDropDownManufacturer(String inManufacturer, String readOnly) {
		String returnValue = "";
		try {
			Vector listOfManufacturers = ServiceGTIN.buildDropDownGS1CompanyPrefix();
			
			returnValue = DropDownSingle.buildDropDown(listOfManufacturers,
					"manufacturer", inManufacturer, "Select a Manufacturer: ", "E",
					readOnly, "50");
		}
		catch(Exception e)
		{}
		return returnValue;
	}
	/**
	* Format the Error Message to be displayd on the page.
	*
	* Creation date: (02/17/2006 TWalton)
	*/
	private static String addFormatToErrorForDisplay(String errorString) {
		StringBuffer returnString = new StringBuffer();
		if (!errorString.equals(""))
		{	
			returnString.append("<font style=\"color:#990000\"><b>");
			returnString.append(errorString);
			returnString.append("</b></font>");
		}	
	    return returnString.toString();
	}
	/**
	 * @param Send in an ITEM Class and the Values will be set into the UpdItem Fields
	 */
	public void setFieldsFromItem(Item inValue) {
		try
		{
		//	Set all the Values from the item Class
			this.caseUPC = inValue.getNewItemCaseUPC();
			this.labelUPC = inValue.getNewItemLabelUPC();
			this.palletGTIN = inValue.getNewItemPalletGTIN();
		}
		catch(Exception e)
		{}
	}
	/*
	 * Send in a Business Object Bean Use the fields from this Bean to set into
	 * a View Bean for Update Creation date: (4/29/2008 TWalton)
	 * 
	 *  Adjust code to account for MFG Numbers that are longer than 5
	 */
	public void loadUpdItemFromBeanItem(BeanItem inBean) {
		try {
			//String testAnnounced = "";
			//projectOwner                    = inBean.getResourceNewClass().getProjectOwner();
			manufacturer                    = inBean.getItemClass().getNewItemManufacturer();
			
			String nextAvailableValue = "0";
			if (manufacturer.length() > 6)
				nextAvailableValue = "1";
			String createNewUPC = "";
			//announced = inBean.getResourceNewClass().getAnnounced();
			//-------------------------------------------------------------
			//CASE UPC
			String caseUPCBase = inBean.getItemClass().getNewItemCaseUPC();
			if (caseUPCBase.equals("") && 
				!manufacturer.equals("") &&
				announced.trim().equals("")) {
				
				if (manufacturer.length() > 6)
				{
					createNewUPC = "Y";
					caseUPCBase = manufacturer.trim();
					//  Find Next "number" based on Manufacturer
					nextAvailableValue = ServiceItem.nextUPCNumber("PRD", manufacturer.trim());

					if (manufacturer.length() == 7)
					   caseUPCBase = caseUPCBase + "0" + HTMLHelpersMasking.addZerosToFrontOfField(nextAvailableValue, 3);
					if (manufacturer.length() == 8)
					   caseUPCBase = caseUPCBase + HTMLHelpersMasking.addZerosToFrontOfField(nextAvailableValue, 3);
				}else{
					caseUPCBase = "0" + manufacturer.trim();
					if (caseUPCBase.length() == 6 &&
						item.length() > 4)
						caseUPCBase = caseUPCBase + "0" + item.substring((item.length() - 4), item.length());
				}
//				testAnnounced = "Y";
			}
			if (caseUPCBase.length() == 11 || caseUPCBase.length() == 10) {
				caseUPCBase = ServiceItem.checkDigitUPC(caseUPCBase);
			}else{
			  if (caseUPCBase.length() < 12)
			  {
				 for (int x = caseUPCBase.length(); x < 12; x++)
				 {
				   caseUPCBase = caseUPCBase + " ";
				 }
			  }
			}
			if (caseUPCBase.length() == 12){
				caseUPC = caseUPCBase.substring(0,11);
				caseUPCA = caseUPCBase.substring(0, 1);
				caseUPCB = caseUPCBase.substring(1, 6);
				caseUPCC = caseUPCBase.substring(6, 11);
				caseUPCD = caseUPCBase.substring(11, 12);
			}
			//----------------------------------------------------------------------------
			// LABEL UPC
			String labelUPCBase = inBean.getItemClass().getNewItemLabelUPC();
			if (labelUPCBase.equals("") && 
				!manufacturer.equals("") &&
				announced.trim().equals("")) {
				
				if (manufacturer.length() > 6)
				{
					if (manufacturer.length() == 7)
						labelUPCBase = manufacturer.trim() + "1" + HTMLHelpersMasking.addZerosToFrontOfField(nextAvailableValue, 3);
					
					if (manufacturer.length() == 8)
					   labelUPCBase = caseUPCBase;
				}else{
					labelUPCBase = "0" + manufacturer.trim();
					if (labelUPCBase.length() == 6 &&
							item.length() > 4)
						labelUPCBase = labelUPCBase + "1" + item.substring((item.length() - 4), item.length());
				}
//				testAnnounced = "Y";
			}
			if (labelUPCBase.length() == 11 || labelUPCBase.length() == 10) {
				labelUPCBase = ServiceItem.checkDigitUPC(labelUPCBase);
			}else{
				if (labelUPCBase.length() < 12)
			    {
					for (int x = labelUPCBase.length(); x < 12; x++)
					{
					   labelUPCBase = labelUPCBase + " ";
					}
				}
			}
			if (labelUPCBase.length() == 12){
				labelUPC = labelUPCBase.substring(0,11);
				labelUPCA = labelUPCBase.substring(0, 1);
				labelUPCB = labelUPCBase.substring(1, 6);
				labelUPCC = labelUPCBase.substring(6, 11);
				labelUPCD = labelUPCBase.substring(11, 12);
			}
			//---------------------------------------------------------------------------
			// 10/8/12 TW -- added Carrier UPC and Wrap UPC
			//----------------------------------------------------------------------------
			// CARRIER UPC
			String carrierUPCBase = inBean.getItemClass().getNewItemCarrierUPC();
			if (carrierUPCBase.length() == 11 || carrierUPCBase.length() == 10) {
				carrierUPCBase = ServiceItem.checkDigitUPC(carrierUPCBase);
			}else{
				if (carrierUPCBase.length() < 12)
			    {
					for (int x = carrierUPCBase.length(); x < 12; x++)
					{
					   carrierUPCBase = carrierUPCBase + " ";
					}
				}
			}
			if (carrierUPCBase.length() == 12){
				this.setCarrierUPC(carrierUPCBase.substring(0,11));
				this.setCarrierUPCA(carrierUPCBase.substring(0, 1));
				this.setCarrierUPCB(carrierUPCBase.substring(1, 6));
				this.setCarrierUPCC(carrierUPCBase.substring(6, 11));
				this.setCarrierUPCD(carrierUPCBase.substring(11, 12));
			}
			//----------------------------------------------------------------------------
			// WRAP UPC
			String wrapUPCBase = inBean.getItemClass().getNewItemWrapUPC();
			if (wrapUPCBase.length() == 11 || wrapUPCBase.length() == 10) {
				wrapUPCBase = ServiceItem.checkDigitUPC(wrapUPCBase);
			}else{
				if (wrapUPCBase.length() < 12)
			    {
					for (int x = wrapUPCBase.length(); x < 12; x++)
					{
					   wrapUPCBase = wrapUPCBase + " ";
					}
				}
			}
			if (wrapUPCBase.length() == 12){
				this.setWrapUPC(wrapUPCBase.substring(0,11));
				this.setWrapUPCA(wrapUPCBase.substring(0, 1));
				this.setWrapUPCB(wrapUPCBase.substring(1, 6));
				this.setWrapUPCC(wrapUPCBase.substring(6, 11));
				this.setWrapUPCD(wrapUPCBase.substring(11, 12));
			}
			//---------------------------------------------------------------------------
			// Pallet GTIN
			String palletGTINBase = inBean.getItemClass().getNewItemPalletGTIN();
			if (palletGTINBase != null && !palletGTINBase.equals("")) {
				try {
					GTIN thisGTIN = ServiceGTIN.buildGtin(inBean.getItemClass().getNewItemPalletGTIN());
					palletGTINDesc = thisGTIN.getGtinDescription();
				} catch (Exception e) {
					// In case this GTIN is not valid YET..
					//	System.out.println("Problem" + e);
				}
			}
			if (palletGTINBase.equals("") && 
				!manufacturer.equals("") &&
				announced.equals("")) {
				if (manufacturer.length() > 6)
				{
					palletGTINBase = "50" + manufacturer.trim();
					if (manufacturer.length() == 7)
					   palletGTINBase = palletGTINBase + "0" + HTMLHelpersMasking.addZerosToFrontOfField(nextAvailableValue, 3);
					if (manufacturer.length() == 8)
					   palletGTINBase = palletGTINBase + HTMLHelpersMasking.addZerosToFrontOfField(nextAvailableValue, 3);
				}else{
					palletGTINBase = "000" + manufacturer.trim();
					try {
						if (palletGTINBase.length() == 8 && item.length() > 4) {
							palletGTINBase = palletGTINBase	+ "8"
								+ item.substring((item.length() - 4), item.length());
//						palletGTINBase = palletGTIN = ServiceGTIN
//							.checkDigit14(palletGTINBase);
						}
					} catch (Exception e) {
					}
				}
//				testAnnounced = "Y";
			}
			if (palletGTINBase.length() == 13) {
				palletGTINBase = ServiceGTIN.checkDigit14(palletGTINBase);
			}else{
				if (palletGTINBase.length() < 14)
			    {
					for (int x = palletGTINBase.length(); x < 14; x++)
					{
					   palletGTINBase = palletGTINBase + " ";
					}
				}
			}
			palletGTIN = palletGTINBase;
			if (palletGTINBase.length() == 14) {
				palletGTINA = palletGTINBase.substring(0, 2);
				palletGTINB = palletGTINBase.substring(2, 8);
				palletGTINC = palletGTINBase.substring(8, 13);
				palletGTIND = palletGTINBase.substring(13, 14);
			}
			
//					if (testAnnounced.equals("Y"))
//				announced = "Y";
			
		//---------------------------------------------------------
			this.setItemReplacing(inBean.getItemClass().getNewItemReplacedItem());
			this.setSampleOrderDesc(inBean.getItemClass().getNewItemSampleOrderDesc());
			this.setDateInitiated(inBean.getItemClass().getNewItemInitiatedDate());
			this.setSalesPerson(inBean.getItemClass().getNewItemSalesPerson());
			this.setCustomerServiceTeam(inBean.getItemClass().getNewItemCustServiceTeam());
			this.setLength(inBean.getItemClass().getNewItemLength());
			this.setWidth(inBean.getItemClass().getNewItemWidth());
			this.setHeight(inBean.getItemClass().getNewItemHeight());
			this.setPause(inBean.getItemClass().getPause());
					
	
		} catch (Exception e) {
	
		}
		return;
	}
	/**
	 * @return Returns the areYouOwner.
	 */
	public String getAreYouOwner() {
		return areYouOwner;
	}
	/**
	 * @param areYouOwner The areYouOwner to set.
	 */
	public void setAreYouOwner(String areYouOwner) {
		this.areYouOwner = areYouOwner;
	}
	public String getFlagFPK() {
		return flagFPK;
	}
	public void setFlagFPK(String flagFPK) {
		this.flagFPK = flagFPK;
	}
	public String getFlagOPN() {
		return flagOPN;
	}
	public void setFlagOPN(String flagOPN) {
		this.flagOPN = flagOPN;
	}
	public String getFlagJCE() {
		return flagJCE;
	}
	public void setFlagJCE(String flagJCE) {
		this.flagJCE = flagJCE;
	}
	public String getEstTargetDate() {
		return estTargetDate;
	}
	public void setEstTargetDate(String estTargetDate) {
		this.estTargetDate = estTargetDate;
	}
	public String getPlanner() {
		return planner;
	}
	public void setPlanner(String planner) {
		this.planner = planner;
	}
	public String getPlanningCategory() {
		return planningCategory;
	}
	public void setPlanningCategory(String planningCategory) {
		this.planningCategory = planningCategory;
	}
	/**
	 * Build drop down  list of the Planners
	 * 
	 * Creation date: (6/21/2011 -- TWalton)
	 */
	public static String buildDropDownPlanner(String inPlanner, String readOnly) {
		String returnValue = "";
		try {
			CommonRequestBean crb = new CommonRequestBean();
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			crb.setEnvironment("PRD");
			crb.setIdLevel1("PLN");
			crb.setIdLevel2("D");
			Vector listOfPlanners = ServiceDescriptiveCode.dropDownSingle(crb);
			
			returnValue = DropDownSingle.buildDropDown(listOfPlanners,
					"planner", inPlanner, "Select a Planner: ", "N",
					readOnly);
		}
		catch(Exception e)
		{}
		return returnValue;
	}
	/**
	 * Build drop down  list of the Report 1 Values
	 * 
	 * Creation date: (9/24/2011 -- TWalton)
	 */
	public static String buildDropDownReport1(String inRPT1, String readOnly) {
		String returnValue = "";
		try {
			CommonRequestBean crb = new CommonRequestBean();
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			crb.setEnvironment("PRD");
			crb.setIdLevel1("RPT1");
			crb.setIdLevel2("D");
			Vector listOfReportCodes = ServiceDescriptiveCode.dropDownSingle(crb);
			returnValue = DropDownSingle.buildDropDown(listOfReportCodes,
					"aliasRPT1", inRPT1, "Select a Report Code: ", "B",
					readOnly);
		}
		catch(Exception e)
		{}
		return returnValue;
	}
	/**
	 * Build drop down  list of the Planning Categories
	 * 
	 * Creation date: (6/21/2011 -- TWalton)
	 */
	public static String buildDropDownPlanningCategory(String inPlanCategory, String readOnly) {
		String returnValue = "";
		try {
			CommonRequestBean crb = new CommonRequestBean();
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			crb.setEnvironment("PRD");
			crb.setIdLevel1("PCAT");
			crb.setIdLevel2("D");
			Vector listOfPlanCategories = ServiceDescriptiveCode.dropDownSingle(crb);
			
			returnValue = DropDownSingle.buildDropDown(listOfPlanCategories,
					"planningCategory", inPlanCategory, "Select a Planning Category: ", "N",
					readOnly);
		}
		catch(Exception e)
		{}
		return returnValue;
	}
	public String getFlagCAR() {
		return flagCAR;
	}
	public void setFlagCAR(String flagCAR) {
		this.flagCAR = flagCAR;
	}
	public String getItemGroup() {
		return itemGroup;
	}
	public void setItemGroup(String itemGroup) {
		this.itemGroup = itemGroup;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getLengthError() {
		return lengthError;
	}
	public void setLengthError(String lengthError) {
		this.lengthError = lengthError;
	}
	public String getWidthError() {
		return widthError;
	}
	public void setWidthError(String widthError) {
		this.widthError = widthError;
	}
	public String getHeightError() {
		return heightError;
	}
	public void setHeightError(String heightError) {
		this.heightError = heightError;
	}
	public String getSupplierSummaryURL() {
		return supplierSummaryURL;
	}
	public void setSupplierSummaryURL(String supplierSummaryURL) {
		this.supplierSummaryURL = supplierSummaryURL;
	}
	public String getSupplierSummaryURLRemove() {
		return supplierSummaryURLRemove;
	}
	public void setSupplierSummaryURLRemove(String supplierSummaryURLRemove) {
		this.supplierSummaryURLRemove = supplierSummaryURLRemove;
	}
	public String getAliasCheckBox() {
		return aliasCheckBox;
	}
	public void setAliasCheckBox(String aliasCheckBox) {
		this.aliasCheckBox = aliasCheckBox;
	}
	public String getFlagSingleStrength() {
		return flagSingleStrength;
	}
	public void setFlagSingleStrength(String flagSingleStrength) {
		this.flagSingleStrength = flagSingleStrength;
	}
	public String getAliasRPT1() {
		return aliasRPT1;
	}
	public void setAliasRPT1(String aliasRPT1) {
		this.aliasRPT1 = aliasRPT1;
	}
	public String getFlagClub() {
		return flagClub;
	}
	public void setFlagClub(String flagClub) {
		this.flagClub = flagClub;
	}
	public String getCarrierUPC() {
		return carrierUPC;
	}
	public void setCarrierUPC(String carrierUPC) {
		this.carrierUPC = carrierUPC;
	}
	public String getCarrierUPCA() {
		return carrierUPCA;
	}
	public void setCarrierUPCA(String carrierUPCA) {
		this.carrierUPCA = carrierUPCA;
	}
	public String getCarrierUPCB() {
		return carrierUPCB;
	}
	public void setCarrierUPCB(String carrierUPCB) {
		this.carrierUPCB = carrierUPCB;
	}
	public String getCarrierUPCC() {
		return carrierUPCC;
	}
	public void setCarrierUPCC(String carrierUPCC) {
		this.carrierUPCC = carrierUPCC;
	}
	public String getCarrierUPCD() {
		return carrierUPCD;
	}
	public void setCarrierUPCD(String carrierUPCD) {
		this.carrierUPCD = carrierUPCD;
	}
	public String getErrorCarrierUPC() {
		return errorCarrierUPC;
	}
	public void setErrorCarrierUPC(String errorCarrierUPC) {
		this.errorCarrierUPC = errorCarrierUPC;
	}
	public String getWrapUPC() {
		return wrapUPC;
	}
	public void setWrapUPC(String wrapUPC) {
		this.wrapUPC = wrapUPC;
	}
	public String getWrapUPCA() {
		return wrapUPCA;
	}
	public void setWrapUPCA(String wrapUPCA) {
		this.wrapUPCA = wrapUPCA;
	}
	public String getWrapUPCB() {
		return wrapUPCB;
	}
	public void setWrapUPCB(String wrapUPCB) {
		this.wrapUPCB = wrapUPCB;
	}
	public String getWrapUPCC() {
		return wrapUPCC;
	}
	public void setWrapUPCC(String wrapUPCC) {
		this.wrapUPCC = wrapUPCC;
	}
	public String getWrapUPCD() {
		return wrapUPCD;
	}
	public void setWrapUPCD(String wrapUPCD) {
		this.wrapUPCD = wrapUPCD;
	}
	public String getDateInitiated() {
		return dateInitiated;
	}
	public void setDateInitiated(String dateInitiated) {
		this.dateInitiated = dateInitiated;
	}
	public String getSalesPerson() {
		return salesPerson;
	}
	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}
	public String getCustomerServiceTeam() {
		return customerServiceTeam;
	}
	public void setCustomerServiceTeam(String customerServiceTeam) {
		this.customerServiceTeam = customerServiceTeam;
	}
	public String getItemReplacing() {
		return itemReplacing;
	}
	public void setItemReplacing(String itemReplacing) {
		this.itemReplacing = itemReplacing;
	}
	public String getItemReplacingError() {
		return itemReplacingError;
	}
	public void setItemReplacingError(String itemReplacingError) {
		this.itemReplacingError = itemReplacingError;
	}
	public String getSampleOrderDesc() {
		return sampleOrderDesc;
	}
	public void setSampleOrderDesc(String sampleOrderDesc) {
		this.sampleOrderDesc = sampleOrderDesc;
	}
	public String getErrorWrapUPC() {
		return errorWrapUPC;
	}
	public void setErrorWrapUPC(String errorWrapUPC) {
		this.errorWrapUPC = errorWrapUPC;
	}
	public String getFlagAllergen() {
		return flagAllergen;
	}
	public void setFlagAllergen(String flagAllergen) {
		this.flagAllergen = flagAllergen;
	}
	public String getClubItemNumber() {
		return clubItemNumber;
	}
	public void setClubItemNumber(String clubItemNumber) {
		this.clubItemNumber = clubItemNumber;
	}
	public String getPause() {
		return pause;
	}
	public void setPause(String pause) {
		this.pause = pause;
	}
	public String getPauseButton() {
		return pauseButton;
	}
	public void setPauseButton(String pauseButton) {
		this.pauseButton = pauseButton;
	}
	public String getUnpauseButton() {
		return unpauseButton;
	}
	public void setUnpauseButton(String unpauseButton) {
		this.unpauseButton = unpauseButton;
	}
	public String getRestartButton() {
		return restartButton;
	}
	public void setRestartButton(String restartButton) {
		this.restartButton = restartButton;
	}
	public String getAreEmailsComplete() {
		return areEmailsComplete;
	}
	public void setAreEmailsComplete(String areEmailsComplete) {
		this.areEmailsComplete = areEmailsComplete;
	}
}
