/*
 * Created on Jul 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package com.treetop.app.resource;

import java.math.BigDecimal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjects.BeanResource;
import com.treetop.businessobjects.GTIN;
import com.treetop.data.*;
import com.treetop.services.ServiceGTIN;
import com.treetop.services.ServiceItem;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.utilities.html.*;
import com.treetop.app.gtin.UpdGTIN;

/**
 * @author twalto
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UpdNewItem extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String errorOnScreen = "";

	// Must have in Update View Bean
	public String userProfile = "";
	public String saveButton = "";
	public String updateType = "all"; // for use with security.
	public String addNew = "";

	// FIELDS
	public String recordType                      = "MW";
	public String status                          = "";
	public String projectOwner                    = "";
	public String resource                        = "";
	public String resourceType                    = "";
	public String resourceDescription             = "";
	public String errorResourceDescription        = ""; // Cannot Be Blank
	public String resourceDescriptionLong         = "";
	public String resourceDescriptionOrderEntry   = ""; // specifically 24 LONG
	public String resourceDescriptionBillOfLading = "";
	public String productOwnerCode                = "";
	public String manufacturer                    = ""; //Produce under which code
	public String dueDate                         = "";
	public String errorDueDate                    = ""; //Valid Date
	public String productionDate                  = "";
	public String errorProductionDate             = ""; // Valid Date
	public String bestByStartDate                 = "";
	public String errorBestByStartDate            = ""; // Valid Date
	
	//** UPC & GTIN **
	// This field will be used to determine whether or NOT to 
	//    Load the UPC and GTIN Fields
	public String announced       = ""; 
	public String labelUPC        = "";
	public String labelUPCA       = ""; //Section of labelUPC
	public String labelUPCB       = ""; //Section of labelUPC
	public String labelUPCC       = ""; //Section of labelUPC
	public String labelUPCD       = ""; //Section of labelUPC
	public String errorLabelUPC   = ""; //must be NUMERIC
	public String caseUPC         = "";
	public String caseUPCA        = ""; //Section of caseUPC
	public String caseUPCB        = ""; //Section of caseUPC
	public String caseUPCC        = ""; //Section of caseUPC
	public String caseUPCD        = ""; //Section of caseUPC
	public String errorCaseUPC    = ""; // must be NUMERIC
	public String palletGTIN      = "";
	public String palletGTINDesc  = "";
	public String palletGTINA     = ""; //Section of palletGTIN
	public String palletGTINB     = ""; //Section of palletGTIN
	public String palletGTINC     = ""; //Section of palletGTIN
	public String palletGTIND     = ""; //Section of palletGTIN
	public String errorPalletGTIN = ""; // must be NUMERIC

	// Additional Fields
	public String stackingHeight = "";
	//for Certain Resource Types - Co-Pack and Private Label
	public String qtyItemsPerCompleteLayer          = "0";
	public String errorQtyItemsPerCompleteLayer     = ""; // Must be WHOLE NUMBER
	public String qtyOfNextLowerLevelTradeItem      = "0";
	public String errorQtyOfNextLowerLevelTradeItem = ""; // Must be WHOLE NUMBER
	public String depth                             = "0";
	public String errorDepth                        = ""; // Must be NUMERIC
	public String width                             = "0";
	public String errorWidth                        = ""; // Must be NUMERIC
	public String grossWeight                       = "0";
	public String errorGrossWeight                  = ""; // Must be NUMERIC
	public String weightUnitOfMeasure               = "";
	public String netContent                        = "0"; // Must be NUMERIC
	public String errorNetContent                   = "";
	public String netContentUnitOfMeasure           = "";
	
	// Shelf Life and AGING
	public String shelfLifeDays       = "0";
	public String shelfLifeFlag       = "";
	public Vector errorsAging         = new Vector(); // ALL fields MUST BE NUMERIC
	public String bestByDaysToQAHD    = "0";
	public String bestByNonSaleableHi = "0";
	public String bestByNonSaleableLo = "0";
	public String bestBySalvageHi     = "0";
	public String bestBySalvageLo     = "0";
	public String bestByCriticalHi    = "0";
	public String bestByCriticalLo    = "0";
	public String bestByWatchHi       = "0";
	public String bestByWatchLo       = "0";
	public String bestByOtherHi       = "0";
	public String bestByOtherLo       = "0";
	public String nonBestByDaysToQAHD = "0";
	public String nonBestByExtraHi    = "0";
	public String nonBestByExtraLo    = "0";
	public String nonBestBySalvageHi  = "0";
	public String nonBestBySalvageLo  = "0";
	public String nonBestByCriticalHi = "0";
	public String nonBestByCriticalLo = "0";
	public String nonBestByWatchHi    = "0";
	public String nonBestByWatchLo    = "0";
	public String nonBestByOtherHi    = "0";
	public String nonBestByOtherLo    = "0";
	
	public Vector listAllComments = null;
	public Vector listAllURLs = null;
	public Vector dropDownGS1CompanyPrefix = null;

	// responsibility
	public String group = "";

	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		//-------------------------------------------------------------
		// REQUIRED FIELDS
		//------------------------------------------------------------
		Vector returnList = new Vector();
		if (saveButton != null &&
			!saveButton.equals("") &&
			(resource == null ||
			 resource.equals("") ||
			 resourceDescription == null || 
			 resourceDescription.equals("")))
			errorResourceDescription = addFormatToErrorForDisplay("Please fill in ALL required fields.");
		
		//----------------------------------------------
		// RESOURCE TYPE
		if (resourceType.equals(""))
		{
			try
			{
//			  BeanResource thisOne = ServiceResource.buildResourceReservation(resource, "");
//			  resourceType = thisOne.getResourceNewClass().getResourceType();
			}
			catch(Exception e)
			{}
			//resourceType = group;
		}	
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
			if (resourceType.toLowerCase().equals("newcpgitem") &&
				palletGTIN.length() == 14)
			{ // Test to see if this is already a GTIN 
			  // IN the GTIN File, IF NOT, add it.
	          String returnValue = UpdGTIN.testAndAddGTIN(palletGTIN);
			}
		}
		// PRODUCT OWNER CODE
		if (productOwnerCode.toLowerCase().trim().equals("none"))
			productOwnerCode = "";
		
		validateDates();
		validateNumbers();
		
		// ARE there any errors?  If so create a message to display (Error on Screen).
		// Will The Update Go Through or NOT?
		// You may have errors and decide to update anyway.
		// This message will be the one that pops up that you have to click through.
		if (!errorResourceDescription.equals("") ||
			!errorLabelUPC.equals("") ||
			!errorCaseUPC.equals("") ||
			!errorPalletGTIN.equals("") ||
			!errorDueDate.equals("") ||
			!errorProductionDate.equals("") ||
			!errorBestByStartDate.equals("") ||
			!errorQtyItemsPerCompleteLayer.equals("") ||
			!errorQtyOfNextLowerLevelTradeItem.equals("") ||
			!errorDepth.equals("") ||
			!errorWidth.equals("") ||
			!errorGrossWeight.equals("") ||
			!errorNetContent.equals(""))
		   errorOnScreen = "** NOT SAVED ** There are problems with the information input.  Please resolve the problems and click save.";
		
		validateAgingBucketsBestBy();
		validateAgingBucketsNonBestBy();
		  		
		if (errorsAging.size() > 0) {
			if (errorOnScreen.equals(""))
			   errorOnScreen = "** NOT SAVED ** <br>";
			errorOnScreen = errorOnScreen + "  There is a problem with the Aging Information.";
		}
		
		return;
	}

	/**
	 * @return
	 */
	public String getCaseUPC() {
		return caseUPC;
	}

	/**
	 * @return
	 */
	public String getDueDate() {
		return dueDate;
	}

	/**
	 * @return
	 */
	public String getLabelUPC() {
		return labelUPC;
	}

	/**
	 * @return
	 */
	public String getProductionDate() {
		return productionDate;
	}

	/**
	 * @return
	 */
	public String getProjectOwner() {
		return projectOwner;
	}

	/**
	 * @return
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @return
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * @return
	 */
	public String getResourceDescription() {
		return resourceDescription;
	}

	/**
	 * @param string
	 */
	public void setCaseUPC(String string) {
		caseUPC = string;
	}

	/**
	 * @param string
	 */
	public void setDueDate(String string) {
		dueDate = string;
	}

	/**
	 * @param string
	 */
	public void setLabelUPC(String string) {
		labelUPC = string;
	}

	/**
	 * @param string
	 */
	public void setProductionDate(String string) {
		productionDate = string;
	}

	/**
	 * @param string
	 */
	public void setProjectOwner(String string) {
		projectOwner = string;
	}

	/**
	 * @param string
	 */
	public void setRequestType(String string) {
		requestType = string;
	}

	/**
	 * @param string
	 */
	public void setResource(String string) {
		resource = string;
	}

	/**
	 * @param string
	 */
	public void setResourceDescription(String string) {
		resourceDescription = string;
	}

	/**
	 * @return
	 */
	public String getUserProfile() {
		return userProfile;
	}

	/**
	 * @param string
	 */
	public void setUserProfile(String string) {
		userProfile = string;
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
	public String getResourceDescriptionLong() {
		return resourceDescriptionLong;
	}

	/**
	 * @param string
	 */
	public void setResourceDescriptionLong(String string) {
		resourceDescriptionLong = string;
	}

	/**
	 * @return
	 */
	public String getCaseUPCA() {
		return caseUPCA;
	}

	/**
	 * @return
	 */
	public String getCaseUPCB() {
		return caseUPCB;
	}

	/**
	 * @return
	 */
	public String getCaseUPCC() {
		return caseUPCC;
	}

	/**
	 * @return
	 */
	public String getCaseUPCD() {
		return caseUPCD;
	}

	/**
	 * @return
	 */
	public String getLabelUPCA() {
		return labelUPCA;
	}

	/**
	 * @return
	 */
	public String getLabelUPCB() {
		return labelUPCB;
	}

	/**
	 * @return
	 */
	public String getLabelUPCC() {
		return labelUPCC;
	}

	/**
	 * @return
	 */
	public String getLabelUPCD() {
		return labelUPCD;
	}

	/**
	 * @return
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @return
	 */
	public String getPalletGTIN() {
		return palletGTIN;
	}

	/**
	 * @return
	 */
	public String getPalletGTINA() {
		return palletGTINA;
	}

	/**
	 * @return
	 */
	public String getPalletGTINB() {
		return palletGTINB;
	}

	/**
	 * @return
	 */
	public String getPalletGTINC() {
		return palletGTINC;
	}

	/**
	 * @return
	 */
	public String getPalletGTIND() {
		return palletGTIND;
	}

	/**
	 * @return
	 */
	public String getPalletGTINDesc() {
		return palletGTINDesc;
	}

	/**
	 * @return
	 */
	public String getResourceDescriptionBillOfLading() {
		return resourceDescriptionBillOfLading;
	}

	/**
	 * @return
	 */
	public String getResourceDescriptionOrderEntry() {
		return resourceDescriptionOrderEntry;
	}

	/**
	 * @param string
	 */
	public void setCaseUPCA(String string) {
		caseUPCA = string;
	}

	/**
	 * @param string
	 */
	public void setCaseUPCB(String string) {
		caseUPCB = string;
	}

	/**
	 * @param string
	 */
	public void setCaseUPCC(String string) {
		caseUPCC = string;
	}

	/**
	 * @param string
	 */
	public void setCaseUPCD(String string) {
		caseUPCD = string;
	}

	/**
	 * @param string
	 */
	public void setLabelUPCA(String string) {
		labelUPCA = string;
	}

	/**
	 * @param string
	 */
	public void setLabelUPCB(String string) {
		labelUPCB = string;
	}

	/**
	 * @param string
	 */
	public void setLabelUPCC(String string) {
		labelUPCC = string;
	}

	/**
	 * @param string
	 */
	public void setLabelUPCD(String string) {
		labelUPCD = string;
	}

	/**
	 * @param string
	 */
	public void setManufacturer(String string) {
		manufacturer = string;
	}

	/**
	 * @param string
	 */
	public void setPalletGTIN(String string) {
		palletGTIN = string;
	}

	/**
	 * @param string
	 */
	public void setPalletGTINA(String string) {
		palletGTINA = string;
	}

	/**
	 * @param string
	 */
	public void setPalletGTINB(String string) {
		palletGTINB = string;
	}

	/**
	 * @param string
	 */
	public void setPalletGTINC(String string) {
		palletGTINC = string;
	}

	/**
	 * @param string
	 */
	public void setPalletGTIND(String string) {
		palletGTIND = string;
	}

	/**
	 * @param string
	 */
	public void setPalletGTINDesc(String string) {
		palletGTINDesc = string;
	}

	/**
	 * @param string
	 */
	public void setResourceDescriptionBillOfLading(String string) {
		resourceDescriptionBillOfLading = string;
	}

	/**
	 * @param string
	 */
	public void setResourceDescriptionOrderEntry(String string) {
		resourceDescriptionOrderEntry = string;
	}
	/**
	 * Will Return A String (Drop Down List), Takes the Vector and sends it AND
	 * the value chosen into the Helper To Build a Drop Down List
	 * 
	 * @return
	 */
	public String getDropDownGS1CompanyPrefix(String readOnly) {

		return DropDownSingle.buildDropDown(dropDownGS1CompanyPrefix,
				"manufacturer", manufacturer, "Select a Manufacturer: ", "E",
				readOnly);
	}

	/**
	 * @param vector
	 */
	public void setDropDownGS1CompanyPrefix(Vector vector) {
		dropDownGS1CompanyPrefix = vector;
	}

	/**
	 * @return
	 */
	public String getAnnounced() {
		return announced;
	}

	/**
	 * @param string
	 */
	public void setAnnounced(String string) {
		announced = string;
	}

	/**
	 * @return
	 */
	public Vector getListAllComments() {
		return listAllComments;
	}

	/**
	 * @param vector
	 */
	public void setListAllComments(Vector vector) {
		listAllComments = vector;
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
	 * @return Returns the listAllURLs.
	 */
	public Vector getListAllURLs() {
		return listAllURLs;
	}

	/**
	 * @param listAllURLs
	 *            The listAllURLs to set.
	 */
	public void setListAllURLs(Vector listAllURLs) {
		this.listAllURLs = listAllURLs;
	}

	/**
	 * Build drop down lists. Hard Code the Resource Type Send in: fieldName
	 * fieldValue readOnly
	 * 
	 * Creation date: (1/9/2006 4:17:29 PM -- TWalton)
	 */
	public static String buildDropDownResourceType(String name, String value, String startValue,
			String readOnly) {

		String returnString = "";
		try {
			Vector ddList = new Vector();
			DropDownSingle addOne = new DropDownSingle();
			addOne.setDescription("CPG Tree Top Item");
			addOne.setValue("NewCPGItem");
			ddList.add(addOne);
			addOne = new DropDownSingle();
			addOne.setDescription("Co-Pack");
			addOne.setValue("CoPack");
			ddList.add(addOne);
			addOne = new DropDownSingle();
			addOne.setDescription("Private Label");
			addOne.setValue("PrivateLabel");
			ddList.add(addOne);
			
			
			returnString = DropDownSingle.buildDropDown(ddList, name, value,
					startValue, "N", readOnly);
		} catch (Exception e) {
			System.out
					.println("Exception Occurred in UpdFunctionDetail.buildDropDownResourceType(name: "
							+ name
							+ " value:"
							+ value
							+ " readOnly:"
							+ readOnly);
		}
		if (returnString.equals(""))
			returnString = "&nbsp;";
		return returnString;
	}

	/**
	 * Build drop down lists. Send in: request response Return: String -- Can
	 * Change == Y/N
	 * 
	 * Creation date: (12/21/2005 1:20:29 PM -- TWalton)
	 */
	public static String getSecurity(HttpServletRequest request,
			HttpServletResponse response) {

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
//		if (returnString.equals("N")) {
//			try {
//				String userProfile = SessionVariables.getSessionttiProfile(
//						request, response);
//				UserFile thisUser = new UserFile();
//				Vector userList = thisUser.findUsersByGroup("40");
				// New Item Project Owner Group
//				for (int x = 0; x < userList.size(); x++) {
//					thisUser = (UserFile) userList.elementAt(x);
//					if (userProfile.trim().equals(thisUser.getUserName().trim()))
//						returnString = "Y";
//				}
//			} catch (Exception e) {
//			}
//		}
		return returnString;
	}

	/**
	 * @return Returns the bestByCriticalHi.
	 */
	public String getBestByCriticalHi() {
		return bestByCriticalHi;
	}

	/**
	 * @param bestByCriticalHi
	 *            The bestByCriticalHi to set.
	 */
	public void setBestByCriticalHi(String bestByCriticalHi) {
		this.bestByCriticalHi = bestByCriticalHi;
	}

	/**
	 * @return Returns the bestByCriticalLo.
	 */
	public String getBestByCriticalLo() {
		return bestByCriticalLo;
	}

	/**
	 * @param bestByCriticalLo
	 *            The bestByCriticalLo to set.
	 */
	public void setBestByCriticalLo(String bestByCriticalLo) {
		this.bestByCriticalLo = bestByCriticalLo;
	}

	/**
	 * @return Returns the bestByDaysToQAHD.
	 */
	public String getBestByDaysToQAHD() {
		return bestByDaysToQAHD;
	}

	/**
	 * @param bestByDaysToQAHD
	 *            The bestByDaysToQAHD to set.
	 */
	public void setBestByDaysToQAHD(String bestByDaysToQAHD) {
		this.bestByDaysToQAHD = bestByDaysToQAHD;
	}

	/**
	 * @return Returns the bestByNonSaleableHi.
	 */
	public String getBestByNonSaleableHi() {
		return bestByNonSaleableHi;
	}

	/**
	 * @param bestByNonSaleableHi
	 *            The bestByNonSaleableHi to set.
	 */
	public void setBestByNonSaleableHi(String bestByNonSaleableHi) {
		this.bestByNonSaleableHi = bestByNonSaleableHi;
	}

	/**
	 * @return Returns the bestByNonSaleableLo.
	 */
	public String getBestByNonSaleableLo() {
		return bestByNonSaleableLo;
	}

	/**
	 * @param bestByNonSaleableLo
	 *            The bestByNonSaleableLo to set.
	 */
	public void setBestByNonSaleableLo(String bestByNonSaleableLo) {
		this.bestByNonSaleableLo = bestByNonSaleableLo;
	}

	/**
	 * @return Returns the bestByOtherHi.
	 */
	public String getBestByOtherHi() {
		return bestByOtherHi;
	}

	/**
	 * @param bestByOtherHi
	 *            The bestByOtherHi to set.
	 */
	public void setBestByOtherHi(String bestByOtherHi) {
		this.bestByOtherHi = bestByOtherHi;
	}

	/**
	 * @return Returns the bestByOtherLo.
	 */
	public String getBestByOtherLo() {
		return bestByOtherLo;
	}

	/**
	 * @param bestByOtherLo
	 *            The bestByOtherLo to set.
	 */
	public void setBestByOtherLo(String bestByOtherLo) {
		this.bestByOtherLo = bestByOtherLo;
	}

	/**
	 * @return Returns the bestBySalvageHi.
	 */
	public String getBestBySalvageHi() {
		return bestBySalvageHi;
	}

	/**
	 * @param bestBySalvageHi
	 *            The bestBySalvageHi to set.
	 */
	public void setBestBySalvageHi(String bestBySalvageHi) {
		this.bestBySalvageHi = bestBySalvageHi;
	}

	/**
	 * @return Returns the bestBySalvageLo.
	 */
	public String getBestBySalvageLo() {
		return bestBySalvageLo;
	}

	/**
	 * @param bestBySalvageLo
	 *            The bestBySalvageLo to set.
	 */
	public void setBestBySalvageLo(String bestBySalvageLo) {
		this.bestBySalvageLo = bestBySalvageLo;
	}

	/**
	 * @return Returns the bestByWatchHi.
	 */
	public String getBestByWatchHi() {
		return bestByWatchHi;
	}

	/**
	 * @param bestByWatchHi
	 *            The bestByWatchHi to set.
	 */
	public void setBestByWatchHi(String bestByWatchHi) {
		this.bestByWatchHi = bestByWatchHi;
	}

	/**
	 * @return Returns the bestByWatchLo.
	 */
	public String getBestByWatchLo() {
		return bestByWatchLo;
	}

	/**
	 * @param bestByWatchLo
	 *            The bestByWatchLo to set.
	 */
	public void setBestByWatchLo(String bestByWatchLo) {
		this.bestByWatchLo = bestByWatchLo;
	}

	/**
	 * @return Returns the nonBestByCriticalHi.
	 */
	public String getNonBestByCriticalHi() {
		return nonBestByCriticalHi;
	}

	/**
	 * @param nonBestByCriticalHi
	 *            The nonBestByCriticalHi to set.
	 */
	public void setNonBestByCriticalHi(String nonBestByCriticalHi) {
		this.nonBestByCriticalHi = nonBestByCriticalHi;
	}

	/**
	 * @return Returns the nonBestByCriticalLo.
	 */
	public String getNonBestByCriticalLo() {
		return nonBestByCriticalLo;
	}

	/**
	 * @param nonBestByCriticalLo
	 *            The nonBestByCriticalLo to set.
	 */
	public void setNonBestByCriticalLo(String nonBestByCriticalLo) {
		this.nonBestByCriticalLo = nonBestByCriticalLo;
	}

	/**
	 * @return Returns the nonBestByDaysToQAHD.
	 */
	public String getNonBestByDaysToQAHD() {
		return nonBestByDaysToQAHD;
	}

	/**
	 * @param nonBestByDaysToQAHD
	 *            The nonBestByDaysToQAHD to set.
	 */
	public void setNonBestByDaysToQAHD(String nonBestByDaysToQAHD) {
		this.nonBestByDaysToQAHD = nonBestByDaysToQAHD;
	}

	/**
	 * @return Returns the nonBestByExtraHi.
	 */
	public String getNonBestByExtraHi() {
		return nonBestByExtraHi;
	}

	/**
	 * @param nonBestByExtraHi
	 *            The nonBestByExtraHi to set.
	 */
	public void setNonBestByExtraHi(String nonBestByExtraHi) {
		this.nonBestByExtraHi = nonBestByExtraHi;
	}

	/**
	 * @return Returns the nonBestByExtraLo.
	 */
	public String getNonBestByExtraLo() {
		return nonBestByExtraLo;
	}

	/**
	 * @param nonBestByExtraLo
	 *            The nonBestByExtraLo to set.
	 */
	public void setNonBestByExtraLo(String nonBestByExtraLo) {
		this.nonBestByExtraLo = nonBestByExtraLo;
	}

	/**
	 * @return Returns the nonBestByOtherHi.
	 */
	public String getNonBestByOtherHi() {
		return nonBestByOtherHi;
	}

	/**
	 * @param nonBestByOtherHi
	 *            The nonBestByOtherHi to set.
	 */
	public void setNonBestByOtherHi(String nonBestByOtherHi) {
		this.nonBestByOtherHi = nonBestByOtherHi;
	}

	/**
	 * @return Returns the nonBestByOtherLo.
	 */
	public String getNonBestByOtherLo() {
		return nonBestByOtherLo;
	}

	/**
	 * @param nonBestByOtherLo
	 *            The nonBestByOtherLo to set.
	 */
	public void setNonBestByOtherLo(String nonBestByOtherLo) {
		this.nonBestByOtherLo = nonBestByOtherLo;
	}

	/**
	 * @return Returns the nonBestBySalvageHi.
	 */
	public String getNonBestBySalvageHi() {
		return nonBestBySalvageHi;
	}

	/**
	 * @param nonBestBySalvageHi
	 *            The nonBestBySalvageHi to set.
	 */
	public void setNonBestBySalvageHi(String nonBestBySalvageHi) {
		this.nonBestBySalvageHi = nonBestBySalvageHi;
	}

	/**
	 * @return Returns the nonBestBySalvageLo.
	 */
	public String getNonBestBySalvageLo() {
		return nonBestBySalvageLo;
	}

	/**
	 * @param nonBestBySalvageLo
	 *            The nonBestBySalvageLo to set.
	 */
	public void setNonBestBySalvageLo(String nonBestBySalvageLo) {
		this.nonBestBySalvageLo = nonBestBySalvageLo;
	}

	/**
	 * @return Returns the nonBestByWatchHi.
	 */
	public String getNonBestByWatchHi() {
		return nonBestByWatchHi;
	}

	/**
	 * @param nonBestByWatchHi
	 *            The nonBestByWatchHi to set.
	 */
	public void setNonBestByWatchHi(String nonBestByWatchHi) {
		this.nonBestByWatchHi = nonBestByWatchHi;
	}

	/**
	 * @return Returns the nonBestByWatchLo.
	 */
	public String getNonBestByWatchLo() {
		return nonBestByWatchLo;
	}

	/**
	 * @param nonBestByWatchLo
	 *            The nonBestByWatchLo to set.
	 */
	public void setNonBestByWatchLo(String nonBestByWatchLo) {
		this.nonBestByWatchLo = nonBestByWatchLo;
	}

	/**
	 * @return Returns the productOwnerCode.
	 */
	public String getProductOwnerCode() {
		return productOwnerCode;
	}

	/**
	 * @param productOwnerCode
	 *            The productOwnerCode to set.
	 */
	public void setProductOwnerCode(String productOwnerCode) {
		this.productOwnerCode = productOwnerCode;
	}

	/**
	 * @return Returns the resourceType.
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType
	 *            The resourceType to set.
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @return Returns the shelfLifeDays.
	 */
	public String getShelfLifeDays() {
		return shelfLifeDays;
	}

	/**
	 * @param shelfLifeDays
	 *            The shelfLifeDays to set.
	 */
	public void setShelfLifeDays(String shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}

	/**
	 * @return Returns the shelfLifeFlag.
	 */
	public String getShelfLifeFlag() {
		return shelfLifeFlag;
	}

	/**
	 * @param shelfLifeFlag
	 *            The shelfLifeFlag to set.
	 */
	public void setShelfLifeFlag(String shelfLifeFlag) {
		this.shelfLifeFlag = shelfLifeFlag;
	}

	/**
	 * @return Returns the stackingHeight.
	 */
	public String getStackingHeight() {
		return stackingHeight;
	}

	/**
	 * @param stackingHeight
	 *            The stackingHeight to set.
	 */
	public void setStackingHeight(String stackingHeight) {
		this.stackingHeight = stackingHeight;
	}

	/**
	 * @return Returns the dropDownGS1CompanyPrefix.
	 */
	public Vector getDropDownGS1CompanyPrefix() {
		return dropDownGS1CompanyPrefix;
	}

	/**
	 * Build drop down lists of Project Owner Names. Send in: fieldName
	 * fieldValue readOnly
	 * 
	 * Creation date: (12/21/2005 11:16:29 AM -- TWalton)
	 */
	public static String buildDropDownProjectOwner(String name, String value,
			String readOnly) {

		String returnString = "";
		try {
			Vector ddList = new Vector();
			UserFile thisUser = new UserFile();
			Vector userList = thisUser.findUsersByGroup("40");
			for (int x = 0; x < userList.size(); x++) {
				thisUser = (UserFile) userList.elementAt(x);
				DropDownSingle addOne = new DropDownSingle();
				addOne.setDescription(thisUser.getUserNameLong());
				addOne.setValue(thisUser.getUserName());
				ddList.add(addOne);
			}
			returnString = DropDownSingle.buildDropDown(ddList, name, value,
					"Select One", "N", readOnly);
		} catch (Exception e) {
			System.out
					.println("Exception Occurred in UpdNewItem.buildDropDownProjectOwner(name: "
							+ name
							+ " value:"
							+ value
							+ " readOnly:"
							+ readOnly);
		}
		if (returnString.equals(""))
			returnString = "&nbsp;";
		return returnString;
	}

	/**
	 * Build drop down lists. Get from GeneralInfo the Owner Code Send in:
	 * fieldName fieldValue readOnly
	 * 
	 * Creation date: (1/9/2006 4:24:29 PM -- TWalton)
	 */
	public static String buildDropDownProductOwnerCode(String name,
			String value, String readOnly) {

		String returnString = "";
		try {
			if (value == null || value.equals("None"))
				value = "";
			Vector typeList = new Vector();
			typeList = GeneralInfo.findDescByFull("CPK");
			returnString = GeneralInfo.buildDropDownFullForKey1(typeList,
					value, name, "");
		} catch (Exception e) {
			System.out
					.println("Exception Occurred in UpdFunctionDetail.buildDropDownProductOwnerCode(name: "
							+ name
							+ " value:"
							+ value
							+ " readOnly:"
							+ readOnly);
		}
		if (returnString.equals(""))
			returnString = "&nbsp;";
		return returnString;
	}

	/**
	 * Build drop down lists. Get from GeneralInfo the Owner Code Send in:
	 * fieldName fieldValue readOnly
	 * 
	 * Creation date: (1/9/2006 4:24:29 PM -- TWalton)
	 */
	public static String buildDropDownStackingHeight(String name, String value,
			String readOnly) {

		String returnString = "";
		// Make sure Value is a Integer, no decimal's
		try {
			if (!value.equals("")) {
				BigDecimal takeAwayDecimals = new BigDecimal(value);
				value = HTMLHelpers.displayNumber(takeAwayDecimals, 0);
			}
		} catch (Exception e) {
			//   System.out.println("exception " + e);
		}
		try {
			Vector ddList = new Vector();
			for (int x = 0; x < 10; x++) {
				DropDownSingle addOne = new DropDownSingle();
				addOne.setDescription(x + "");
				addOne.setValue(x + "");
				ddList.add(addOne);
			}
			returnString = DropDownSingle.buildDropDown(ddList, name, value,
					"None", "N", readOnly);

		} catch (Exception e) {
			System.out
					.println("Exception Occurred in UpdFunctionDetail.buildDropDownStackingHeight(name: "
							+ name
							+ " value:"
							+ value
							+ " readOnly:"
							+ readOnly);
		}
		if (returnString.equals(""))
			returnString = "&nbsp;";
		return returnString;
	}

	/**
	 * Build drop down lists. For Shelf Life Send in: fieldName fieldValue
	 * readOnly
	 * 
	 * Creation date: (1/10/2006 9:03:29 AM -- TWalton)
	 */
	public static String buildDropDownShelfLife(String name, String value,
			String readOnly, String ownerCode, String resource) {

		String returnString = "";
		try {
			if (ownerCode == null || ownerCode.equals("TT"))
				ownerCode = "";
			String resourceClass = "";
			if (resource.length() > 2)
				resourceClass = resource.substring(0, 2);
			// will need to add at a later time 2/2/09 TWalton
			//Vector ddList = ServiceResource.buildDropDownShelfLife(ownerCode,
			//		resourceClass);
			Vector ddList = new Vector();
			returnString = DropDownSingle.buildDropDown(ddList, name, value,
					"None", "N", readOnly);

		} catch (Exception e) {
			System.out
					.println("Exception Occurred in UpdNewItem.buildDropDownShelfLife(name: "
							+ name
							+ " value:"
							+ value
							+ " readOnly:"
							+ readOnly
							+ " ownerCode:"
							+ ownerCode
							+ "resource:" + resource);
		}
		if (returnString.equals(""))
			returnString = "&nbsp;";
		return returnString;
	}

	/*
	 * Send in a Business Object Bean Use the fields from this Bean to set into
	 * a View Bean for Update Creation date: (1/10/2006 TWalton)
	 */
	public void loadUpdNewItemFromBeanResource(BeanResource inBean) {
		try {
			String testAnnounced = "";
			projectOwner                    = inBean.getResourceNewClass().getProjectOwner();
			manufacturer                    = inBean.getResourceNewClass().getGs1CompanyPrefix();
			productOwnerCode                = inBean.getResourceNewClass().getOwnerCode();
			resourceType                    = inBean.getResourceNewClass().getResourceType();
			resourceDescription             = inBean.getResourceNewClass().getResourceDescription();
			resourceDescriptionOrderEntry   = inBean.getResourceNewClass().getOrderEntryDescription();
			resourceDescriptionBillOfLading = inBean.getResourceNewClass().getBillOfLadingDescription();
			resourceDescriptionLong         = inBean.getResourceNewClass().getResourceDescriptionLong();
			dueDate                         = inBean.getResourceNewClass().getSetupDueDate();
			if (dueDate.trim().equals("0"))
			  dueDate = "";	
			productionDate                  = inBean.getResourceNewClass().getTentative1stProductionDate();
			if (productionDate.trim().equals("0"))
			  productionDate = "";
			bestByStartDate                 = inBean.getResourceNewClass().getBestBuyStartDate();
			if (bestByStartDate.trim().equals("0"))
			  bestByStartDate = "";
			
			announced = inBean.getResourceNewClass().getAnnounced();
			//CASE UPC
			String caseUPCBase = inBean.getResourceNewClass().getCaseUpcNumber();
			if (caseUPCBase.equals("") && 
				!manufacturer.equals("") &&
				announced.trim().equals("")) {
				caseUPCBase = "0" + manufacturer.trim();
				if (resource.length() > 4)
					caseUPCBase = caseUPCBase
							+ "0"
							+ resource.substring((resource.length() - 4),
									resource.length());
				testAnnounced = "Y";
			}
			caseUPC = caseUPCBase;
			if (caseUPCBase.length() == 6) {
				caseUPCA = caseUPCBase.substring(0, 1);
				caseUPCB = caseUPCBase.substring(1, 6);
			}
			if (caseUPCBase.length() == 11 || caseUPCBase.length() == 10) {
				caseUPCBase = ServiceItem.checkDigitUPC(caseUPCBase);
			}
			if (caseUPCBase.length() == 12) {
				caseUPCA = caseUPCBase.substring(0, 1);
				caseUPCB = caseUPCBase.substring(1, 6);
				caseUPCC = caseUPCBase.substring(6, 11);
				caseUPCD = caseUPCBase.substring(11, 12);
			}
			// LABEL UPC
			String labelUPCBase = inBean.getResourceNewClass()
					.getItemUpcNumber();
			if (labelUPCBase.equals("") && 
				!manufacturer.equals("") &&
				announced.trim().equals("")) {
				labelUPCBase = "0" + manufacturer.trim();
				if (resource.length() > 4)
					labelUPCBase = labelUPCBase
							+ "1"
							+ resource.substring((resource.length() - 4),
									resource.length());
				testAnnounced = "Y";
			}
			labelUPC = labelUPCBase;
			if (labelUPCBase.length() == 6) {
				labelUPCA = labelUPCBase.substring(0, 1);
				labelUPCB = labelUPCBase.substring(1, 6);
			}
			if (labelUPCBase.length() == 11 || labelUPCBase.length() == 10) {
				labelUPCBase = ServiceItem.checkDigitUPC(labelUPCBase);
			}
			if (labelUPCBase.length() == 12) {
				labelUPCA = labelUPCBase.substring(0, 1);
				labelUPCB = labelUPCBase.substring(1, 6);
				labelUPCC = labelUPCBase.substring(6, 11);
				labelUPCD = labelUPCBase.substring(11, 12);
			}
			
			String palletGTINBase = inBean.getResourceNewClass().getPalletGTIN();
			if (palletGTINBase != null && !palletGTINBase.equals("")) {
				try {
					GTIN thisGTIN = ServiceGTIN.buildGtin(inBean
							.getResourceNewClass().getPalletGTIN());
					palletGTINDesc = thisGTIN.getGtinDescription();
				} catch (Exception e) {
					// In case this GTIN is not valid YET..
					//	System.out.println("Problem" + e);
				}
			}
			if (palletGTINBase.equals("") && 
				!manufacturer.equals("") &&
				announced.equals("")) {
				palletGTINBase = "000" + manufacturer.trim();
				try {
					if (resource.length() > 4) {
						palletGTINBase = palletGTINBase
								+ "8"
								+ resource.substring((resource.length() - 4),
										resource.length());
						palletGTINBase = palletGTIN = ServiceGTIN
								.checkDigit14(palletGTINBase);
					}
				} catch (Exception e) {
				}
				testAnnounced = "Y";
			}
			palletGTIN = palletGTINBase;
			if (palletGTINBase.length() == 8) {
				palletGTINA = palletGTINBase.substring(0, 2);
				palletGTINB = palletGTINBase.substring(2, 8);
			}
			if (palletGTINBase.length() == 14) {
				palletGTINA = palletGTINBase.substring(0, 2);
				palletGTINB = palletGTINBase.substring(2, 8);
				palletGTINC = palletGTINBase.substring(8, 13);
				palletGTIND = palletGTINBase.substring(13, 14);
			}
			
			// Additional Fields -- & Fields that may be associated to GTIN Fields
			stackingHeight               = inBean.getResourceNewClass().getStackingHeight().toString();
			qtyItemsPerCompleteLayer     = inBean.getResourceNewClass().getQtyItemsPerCompleteLayer().toString();
			qtyOfNextLowerLevelTradeItem = inBean.getResourceNewClass().getQtyOfNextLowerLevelTradeItem().toString();
			depth                        = inBean.getResourceNewClass().getDepth().toString();
			width                        = inBean.getResourceNewClass().getWidth().toString();
			grossWeight                  = inBean.getResourceNewClass().getGrossWeight().toString();
			weightUnitOfMeasure          = inBean.getResourceNewClass().getWeightUnitOfMeasure();
			netContent                   = inBean.getResourceNewClass().getNetContent().toString();
			netContentUnitOfMeasure      = inBean.getResourceNewClass().getNetContentUnitOfMeasure();
			
			//SHELF LIFE AGING SECTION
			shelfLifeDays       = inBean.getResourceNewClass().getShelfLifeDays().toString();
			shelfLifeFlag       = inBean.getResourceNewClass().getShelfLifeFlag();
			bestByNonSaleableLo = inBean.getResourceNewClass().getBestByNonSaleableLowLimit().toString();
			bestByNonSaleableHi = inBean.getResourceNewClass().getBestByNonSaleableHiLimit().toString();
			bestBySalvageLo     = inBean.getResourceNewClass().getBestBySalvageLowLimit().toString();
			bestBySalvageHi     = inBean.getResourceNewClass().getBestBySalvageHiLimit().toString();
			bestByCriticalLo    = inBean.getResourceNewClass().getBestByCriticalLowLimit().toString();
			bestByCriticalHi    = inBean.getResourceNewClass().getBestByCriticalHiLimit().toString();
			bestByWatchLo       = inBean.getResourceNewClass().getBestByWatchLowLimit().toString();
			bestByWatchHi       = inBean.getResourceNewClass().getBestByWatchHiLimit().toString();
			bestByOtherLo       = inBean.getResourceNewClass().getBestByOtherLowLimit().toString();
			bestByOtherHi       = inBean.getResourceNewClass().getBestByOtherHiLimit().toString();
			bestByDaysToQAHD    = inBean.getResourceNewClass().getBestByDaysToQahd().toString();
			nonBestByExtraLo    = inBean.getResourceNewClass().getNonBestByExtraLowLimit().toString();
			nonBestByExtraHi    = inBean.getResourceNewClass().getNonBestByExtraHiLimit().toString();
			nonBestBySalvageLo  = inBean.getResourceNewClass().getNonBestBySalvageLowLimit().toString();
			nonBestBySalvageHi  = inBean.getResourceNewClass().getNonBestBySalvageHiLimit().toString();
			nonBestByCriticalLo = inBean.getResourceNewClass().getNonBestByCriticalLowLimit().toString();
			nonBestByCriticalHi = inBean.getResourceNewClass().getNonBestByCriticalHiLimit().toString();
			nonBestByWatchLo    = inBean.getResourceNewClass().getNonBestByWatchLowLimit().toString();
			nonBestByWatchHi    = inBean.getResourceNewClass().getNonBestByWatchHiLimit().toString();
			nonBestByOtherLo    = inBean.getResourceNewClass().getNonBestByOtherLowLimit().toString();
			nonBestByOtherHi    = inBean.getResourceNewClass().getNonBestByOtherHiLimit().toString();
			nonBestByDaysToQAHD = inBean.getResourceNewClass().getNonBestByDaysToQahd().toString();

			// LISTS
			listAllURLs = inBean.getResourceNewClass().getResourceUrls();
			listAllComments = inBean.getResourceNewClass().getComments();
			if (testAnnounced.equals("Y"))
				announced = "Y";

		} catch (Exception e) {

		}
		return;
	}

	/**
	 * Validate Aging Buckets for the BEST BY SIDE, Add to Vector errorsAging if
	 * problems. Test for Numeric AND Range Issues Creation date: (1/12/2006
	 * 10:20:28 AM TWalton)
	 */
	private void validateAgingBucketsBestBy() {
		//********************************************
		// BEST BY SECTION
		//********************************************
		//--------------------------------------------
		// Days To QAHD
		if (bestByDaysToQAHD.trim().equals(""))
			bestByDaysToQAHD = "0";
		try {
			Integer test = new Integer(bestByDaysToQAHD);
		} catch (Exception e) {
			errorsAging.add("The Best By Days to QAHD (" + bestByDaysToQAHD
					+ ") is not a valid Whole Number.");
		}
		//--------------------------------------------
		// NON SALEABLE
		Integer bbnslo = new Integer("0");
		Integer bbnshi = new Integer("0");
		if (bestByNonSaleableLo.trim().equals(""))
			bestByNonSaleableLo = "0";
		try {
			bbnslo = new Integer(bestByNonSaleableLo);
		} catch (Exception e) {
			errorsAging.add("The Best By Non-Saleable From ("
					+ bestByNonSaleableLo + ") is not a valid Whole Number.");
		}
		if (bestByNonSaleableHi.trim().equals(""))
			bestByNonSaleableHi = "0";
		try {
			bbnshi = new Integer(bestByNonSaleableHi);
		} catch (Exception e) {
			errorsAging.add("The Best By Non-Saleable To ("
					+ bestByNonSaleableHi + ") is not a valid Whole Number.");
		}
		if ((bbnslo.intValue() != 0 || bbnshi.intValue() != 0)) {
			if (bbnslo.intValue() >= bbnshi.intValue()
					&& errorsAging.size() == 0)
				errorsAging.add("The Best By Non-Saleable From ("
						+ bestByNonSaleableLo
						+ ") is Greater than the Best By Non-Saleable To ("
						+ bestByNonSaleableHi + ").");
		}
		//--------------------------------------------
		// SALVAGE
		Integer bbslo = new Integer("0");
		Integer bbshi = new Integer("0");
		if (bestBySalvageLo.trim().equals(""))
			bestBySalvageLo = "0";
		try {
			bbslo = new Integer(bestBySalvageLo);
		} catch (Exception e) {
			errorsAging.add("The Best By Salvage From (" + bestBySalvageLo
					+ ") is not a valid Whole Number.");
		}
		if (bestBySalvageHi.trim().equals(""))
			bestBySalvageHi = "0";
		try {
			bbshi = new Integer(bestBySalvageHi);
		} catch (Exception e) {
			errorsAging.add("The Best By Salvage To (" + bestBySalvageHi
					+ ") is not a valid Whole Number.");
		}
		if ((bbslo.intValue() != 0 || bbshi.intValue() != 0)) {
			if (bbslo.intValue() >= bbshi.intValue() && errorsAging.size() == 0)
				errorsAging.add("The Best By Salvage From (" + bestBySalvageLo
						+ ") is Greater than the Best By Salvage To ("
						+ bestBySalvageHi + ").");
		}
		//--------------------------------------------
		// NON SALEABLE/SALVAGE COMPARE
		if (errorsAging.size() == 0
				&& (bbnslo.intValue() != 0 || bbnshi.intValue() != 0)
				&& (bbslo.intValue() != 0 || bbshi.intValue() != 0)) {
			if (bbnshi.intValue() >= bbslo.intValue())
				errorsAging.add("The Best By Non-Saleable To ("
						+ bestByNonSaleableHi
						+ ") is Greater than the Best By Salvage From ("
						+ bestBySalvageLo + ").");
		}
		//--------------------------------------------
		// CRITICAL
		Integer bbclo = new Integer("0");
		Integer bbchi = new Integer("0");
		if (bestByCriticalLo.trim().equals(""))
			bestByCriticalLo = "0";
		try {
			bbclo = new Integer(bestByCriticalLo);
		} catch (Exception e) {
			errorsAging.add("The Best By Critical From (" + bestByCriticalLo
					+ ") is not a valid Whole Number.");
		}
		if (bestByCriticalHi.trim().equals(""))
			bestByCriticalHi = "0";
		try {
			bbchi = new Integer(bestByCriticalHi);
		} catch (Exception e) {
			errorsAging.add("The Best By Critical To (" + bestByCriticalHi
					+ ") is not a valid Whole Number.");
		}
		if ((bbclo.intValue() != 0 || bbchi.intValue() != 0)) {
			if (bbclo.intValue() >= bbchi.intValue() && errorsAging.size() == 0)
				errorsAging.add("The Best By Critical From ("
						+ bestByCriticalLo
						+ ") is Greater than the Best By Critical To ("
						+ bestByCriticalHi + ").");
		}
		//--------------------------------------------
		// SALVAGE/CRITICAL COMPARE
		if (errorsAging.size() == 0
				&& (bbclo.intValue() != 0 || bbchi.intValue() != 0)
				&& (bbslo.intValue() != 0 || bbshi.intValue() != 0)) {
			if (bbshi.intValue() >= bbclo.intValue())
				errorsAging.add("The Best By Salvage To (" + bestBySalvageHi
						+ ") is Greater than the Best By Critical From ("
						+ bestByCriticalLo + ").");
		}
		//--------------------------------------------
		// WATCH
		Integer bbwlo = new Integer("0");
		Integer bbwhi = new Integer("0");
		if (bestByWatchLo.trim().equals(""))
			bestByWatchLo = "0";
		try {
			bbwlo = new Integer(bestByWatchLo);
		} catch (Exception e) {
			errorsAging.add("The Best By Watch From (" + bestByWatchLo
					+ ") is not a valid Whole Number.");
		}
		if (bestByWatchHi.trim().equals(""))
			bestByWatchHi = "0";
		try {
			bbwhi = new Integer(bestByWatchHi);
		} catch (Exception e) {
			errorsAging.add("The Best By Watch To (" + bestByWatchHi
					+ ") is not a valid Whole Number.");
		}
		if ((bbwlo.intValue() != 0 || bbwhi.intValue() != 0)) {
			if (bbwlo.intValue() > bbwhi.intValue() && errorsAging.size() == 0)
				errorsAging.add("The Best By Watch From (" + bestByWatchLo
						+ ") is Greater than the Best By Watch To ("
						+ bestByWatchHi + ").");
		}
		//--------------------------------------------
		// CRITICAL/WATCH COMPARE
		if (errorsAging.size() == 0
				&& (bbclo.intValue() != 0 || bbchi.intValue() != 0)
				&& (bbwlo.intValue() != 0 || bbwhi.intValue() != 0)) {
			if (bbchi.intValue() >= bbwlo.intValue())
				errorsAging.add("The Best By Critical To (" + bbchi
						+ ") is Greater than the Best By Watch From (" + bbwlo
						+ ").");
		}
		//--------------------------------------------
		// OTHER
		Integer bbolo = new Integer("0");
		Integer bbohi = new Integer("0");
		if (bestByOtherLo.trim().equals(""))
			bestByOtherLo = "0";
		try {
			bbolo = new Integer(bestByOtherLo);
		} catch (Exception e) {
			errorsAging.add("The Best By Other From (" + bestByOtherLo
					+ ") is not a valid Whole Number.");
		}
		if (bestByOtherHi.trim().equals(""))
			bestByOtherHi = "0";
		try {
			bbohi = new Integer(bestByOtherHi);
		} catch (Exception e) {
			errorsAging.add("The Best By Other To (" + bestByOtherHi
					+ ") is not a valid Whole Number.");
		}
		if ((bbolo.intValue() != 0 || bbohi.intValue() != 0)) {
			if (bbolo.intValue() > bbohi.intValue() && errorsAging.size() == 0)
				errorsAging.add("The Best By Other From (" + bbolo
						+ ") is Greater than the Best By Other To (" + bbohi
						+ ").");
		}
		//--------------------------------------------
		// WATCH/OTHER COMPARE
		if (errorsAging.size() == 0
				&& (bbolo.intValue() != 0 || bbohi.intValue() != 0)
				&& (bbwlo.intValue() != 0 || bbwhi.intValue() != 0)) {
			if (bbwhi.intValue() >= bbolo.intValue())
				errorsAging.add("The Best By Watch To (" + bbwhi
						+ ") is Greater than the Best By Other From (" + bbolo
						+ ").");
		}
		return;
	}

	/**
	 * Validate Aging Buckets for the NON BEST BY SIDE, Add to Vector
	 * errorsAging if problems. Test for Numeric AND Range Issues Creation date:
	 * (1/12/2006 10:20:28 AM TWalton)
	 */
	private void validateAgingBucketsNonBestBy() {
		//********************************************
		// NON BEST BY SECTION
		//********************************************
		//--------------------------------------------
		// Days To QAHD
		if (nonBestByDaysToQAHD.trim().equals(""))
			nonBestByDaysToQAHD = "0";
		try {
			Integer test = new Integer(nonBestByDaysToQAHD);
		} catch (Exception e) {
			errorsAging.add("The Non Best By Days to QAHD ("
					+ nonBestByDaysToQAHD + ") is not a valid Whole Number.");
		}
		//--------------------------------------------
		// NON SALEABLE
		Integer nbbelo = new Integer("0");
		Integer nbbehi = new Integer("0");
		if (nonBestByExtraLo.trim().equals(""))
			nonBestByExtraLo = "0";
		try {
			nbbelo = new Integer(nonBestByExtraLo);
		} catch (Exception e) {
			errorsAging.add("The Non Best By Extra From (" + nonBestByExtraLo
					+ ") is not a valid Whole Number.");
		}
		if (nonBestByExtraHi.trim().equals(""))
			nonBestByExtraHi = "0";
		try {
			nbbehi = new Integer(nonBestByExtraHi);
		} catch (Exception e) {
			errorsAging.add("The Non Best By Extra To (" + nonBestByExtraHi
					+ ") is not a valid Whole Number.");
		}
		if ((nbbelo.intValue() != 0 || nbbehi.intValue() != 0)) {
			if (nbbelo.intValue() >= nbbehi.intValue()
					&& errorsAging.size() == 0)
				errorsAging.add("The Non Best By Extra From ("
						+ nonBestByExtraLo
						+ ") is Greater than the Non Best By Extra To ("
						+ nonBestByExtraHi + ").");
		}
		//--------------------------------------------
		// SALVAGE
		Integer nbbslo = new Integer("0");
		Integer nbbshi = new Integer("0");
		if (nonBestBySalvageLo.trim().equals(""))
			nonBestBySalvageLo = "0";
		try {
			nbbslo = new Integer(nonBestBySalvageLo);
		} catch (Exception e) {
			errorsAging.add("The Non Best By Salvage From ("
					+ nonBestBySalvageLo + ") is not a valid Whole Number.");
		}
		if (nonBestBySalvageHi.trim().equals(""))
			nonBestBySalvageHi = "0";
		try {
			nbbshi = new Integer(nonBestBySalvageHi);
		} catch (Exception e) {
			errorsAging.add("The Non Best By Salvage To (" + nonBestBySalvageHi
					+ ") is not a valid Whole Number.");
		}
		if ((nbbslo.intValue() != 0 || nbbshi.intValue() != 0)) {
			if (nbbslo.intValue() >= nbbshi.intValue()
					&& errorsAging.size() == 0)
				errorsAging.add("The Non Best By Salvage From ("
						+ nonBestBySalvageLo
						+ ") is Greater than the Non Best By Salvage To ("
						+ nonBestBySalvageHi + ").");
		}
		//--------------------------------------------
		// NON SALEABLE/SALVAGE COMPARE
		if (errorsAging.size() == 0
				&& (nbbelo.intValue() != 0 || nbbehi.intValue() != 0)
				&& (nbbslo.intValue() != 0 || nbbshi.intValue() != 0)) {
			if (nbbehi.intValue() >= nbbslo.intValue())
				errorsAging.add("The Non Best By Non-Saleable To ("
						+ nonBestByExtraHi
						+ ") is Greater than the Non Best By Salvage From ("
						+ nonBestBySalvageLo + ").");
		}
		//--------------------------------------------
		// CRITICAL
		Integer nbbclo = new Integer("0");
		Integer nbbchi = new Integer("0");
		if (nonBestByCriticalLo.trim().equals(""))
			nonBestByCriticalLo = "0";
		try {
			nbbclo = new Integer(nonBestByCriticalLo);
		} catch (Exception e) {
			errorsAging.add("The Non Best By Critical From ("
					+ nonBestByCriticalLo + ") is not a valid Whole Number.");
		}
		if (nonBestByCriticalHi.trim().equals(""))
			nonBestByCriticalHi = "0";
		try {
			nbbchi = new Integer(nonBestByCriticalHi);
		} catch (Exception e) {
			errorsAging.add("The Non Best By Critical To ("
					+ nonBestByCriticalHi + ") is not a valid Whole Number.");
		}
		if ((nbbclo.intValue() != 0 || nbbchi.intValue() != 0)) {
			if (nbbclo.intValue() >= nbbchi.intValue()
					&& errorsAging.size() == 0)
				errorsAging.add("The Non Best By Critical From ("
						+ nonBestByCriticalLo
						+ ") is Greater than the Non Best By Critical To ("
						+ nonBestByCriticalHi + ").");
		}
		//--------------------------------------------
		// SALVAGE/CRITICAL COMPARE
		if (errorsAging.size() == 0
				&& (nbbclo.intValue() != 0 || nbbchi.intValue() != 0)
				&& (nbbslo.intValue() != 0 || nbbshi.intValue() != 0)) {
			if (nbbshi.intValue() >= nbbclo.intValue())
				errorsAging.add("The Non Best By Salvage To ("
						+ nonBestBySalvageHi
						+ ") is Greater than the Non Best By Critical From ("
						+ nonBestByCriticalLo + ").");
		}
		//--------------------------------------------
		// WATCH
		Integer nbbwlo = new Integer("0");
		Integer nbbwhi = new Integer("0");
		if (nonBestByWatchLo.trim().equals(""))
			nonBestByWatchLo = "0";
		try {
			nbbwlo = new Integer(nonBestByWatchLo);
		} catch (Exception e) {
			errorsAging.add("The Non Best By Watch From (" + nonBestByWatchLo
					+ ") is not a valid Whole Number.");
		}
		if (nonBestByWatchHi.trim().equals(""))
			nonBestByWatchHi = "0";
		try {
			nbbwhi = new Integer(nonBestByWatchHi);
		} catch (Exception e) {
			errorsAging.add("The Non Best By Watch To (" + nonBestByWatchHi
					+ ") is not a valid Whole Number.");
		}
		if ((nbbwlo.intValue() != 0 || nbbwhi.intValue() != 0)) {
			if (nbbwlo.intValue() > nbbwhi.intValue()
					&& errorsAging.size() == 0)
				errorsAging.add("The Non Best By Watch From ("
						+ nonBestByWatchLo
						+ ") is Greater than the Non Best By Watch To ("
						+ nonBestByWatchHi + ").");
		}
		//--------------------------------------------
		// CRITICAL/WATCH COMPARE
		if (errorsAging.size() == 0
				&& (nbbclo.intValue() != 0 || nbbchi.intValue() != 0)
				&& (nbbwlo.intValue() != 0 || nbbwhi.intValue() != 0)) {
			if (nbbchi.intValue() >= nbbwlo.intValue())
				errorsAging.add("The Non Best By Critical To (" + nbbchi
						+ ") is Greater than the Non Best By Watch From ("
						+ nbbwlo + ").");
		}
		//--------------------------------------------
		// OTHER
		Integer nbbolo = new Integer("0");
		Integer nbbohi = new Integer("0");
		if (nonBestByOtherLo.trim().equals(""))
			nonBestByOtherLo = "0";
		try {
			nbbolo = new Integer(nonBestByOtherLo);
		} catch (Exception e) {
			errorsAging.add("The Non Best By Other From (" + nonBestByOtherLo
					+ ") is not a valid Whole Number.");
		}
		if (nonBestByOtherHi.trim().equals(""))
			nonBestByOtherHi = "0";
		try {
			nbbohi = new Integer(nonBestByOtherHi);
		} catch (Exception e) {
			errorsAging.add("The Non Best By Other To (" + nonBestByOtherHi
					+ ") is not a valid Whole Number.");
		}
		if ((nbbolo.intValue() != 0 || nbbohi.intValue() != 0)) {
			if (nbbolo.intValue() > nbbohi.intValue()
					&& errorsAging.size() == 0)
				errorsAging.add("The Non Best By Other From (" + nbbolo
						+ ") is Greater than the Non Best By Other To ("
						+ nbbohi + ").");
		}
		//--------------------------------------------
		// WATCH/OTHER COMPARE
		if (errorsAging.size() == 0
				&& (nbbolo.intValue() != 0 || nbbohi.intValue() != 0)
				&& (nbbwlo.intValue() != 0 || nbbwhi.intValue() != 0)) {
			if (nbbwhi.intValue() >= nbbolo.intValue())
				errorsAging.add("The Non Best By Watch To (" + nbbwhi
						+ ") is Greater than the Non Best By Other From ("
						+ nbbolo + ").");
		}
		return;
	}

	/**
	 * @return Returns the errorsAging.
	 */
	public Vector getErrorsAging() {
		return errorsAging;
	}

	/**
	 * @param errorsAging
	 *            The errorsAging to set.
	 */
	public void setErrorsAging(Vector errorsAging) {
		this.errorsAging = errorsAging;
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
//		if (returnString.equals("Y")) {
//			try {

//				String userProfile = SessionVariables.getSessionttiProfile(
//						request, response);
//				UserFile thisUser = new UserFile();
//				Vector userList = thisUser.findUsersByGroup("45"); // New Item
				// Project
				// Owner
				// Group
//				for (int x = 0; x < userList.size(); x++) {
//					thisUser = (UserFile) userList.elementAt(x);
//					if (userProfile.trim().equals(thisUser.getUserName().trim()))
//						returnString = "N";
//				}
//			} catch (Exception e) {
//			}
//		}
		return returnString;
	}

	/**
	 * @return Returns the bestByStartDate.
	 */
	public String getBestByStartDate() {
		return bestByStartDate;
	}

	/**
	 * @param bestByStartDate
	 *            The bestByStartDate to set.
	 */
	public void setBestByStartDate(String bestByStartDate) {
		this.bestByStartDate = bestByStartDate;
	}

	/**
	 * @return Returns the qtyPerRow.
	 */
	public String getQtyItemsPerCompleteLayer() {
		return qtyItemsPerCompleteLayer;
	}

	/**
	 * @param qtyPerRow
	 *            The qtyPerRow to set.
	 */
	public void setQtyItemsPerCompleteLayer(String string) {
		this.qtyItemsPerCompleteLayer = string;
	}

	/**
	 * @return Returns the depth.
	 */
	public String getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 *            The depth to set.
	 */
	public void setDepth(String depth) {
		this.depth = depth;
	}

	/**
	 * @return Returns the errorDepth.
	 */
	public String getErrorDepth() {
		return errorDepth;
	}

	/**
	 * @return Returns the errorGrossWeight.
	 */
	public String getErrorGrossWeight() {
		return errorGrossWeight;
	}

	/**
	 * @return Returns the errorNetContent.
	 */
	public String getErrorNetContent() {
		return errorNetContent;
	}

	/**
	 * @return Returns the errorQtyItemsPerCompleteLayer.
	 */
	public String getErrorQtyItemsPerCompleteLayer() {
		return errorQtyItemsPerCompleteLayer;
	}

	/**
	 * @return Returns the errorQtyOfNextLowerLevelTradeItem.
	 */
	public String getErrorQtyOfNextLowerLevelTradeItem() {
		return errorQtyOfNextLowerLevelTradeItem;
	}

	/**
	 * @return Returns the errorWidth.
	 */
	public String getErrorWidth() {
		return errorWidth;
	}

	/**
	 * @return Returns the grossWeight.
	 */
	public String getGrossWeight() {
		return grossWeight;
	}

	/**
	 * @param grossWeight
	 *            The grossWeight to set.
	 */
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	/**
	 * @return Returns the netContent.
	 */
	public String getNetContent() {
		return netContent;
	}

	/**
	 * @param netContent
	 *            The netContent to set.
	 */
	public void setNetContent(String netContent) {
		this.netContent = netContent;
	}

	/**
	 * @return Returns the netContentUnitOfMeasure.
	 */
	public String getNetContentUnitOfMeasure() {
		return netContentUnitOfMeasure;
	}

	/**
	 * @param netContentUnitOfMeasure
	 *            The netContentUnitOfMeasure to set.
	 */
	public void setNetContentUnitOfMeasure(String netContentUnitOfMeasure) {
		this.netContentUnitOfMeasure = netContentUnitOfMeasure;
	}

	/**
	 * @return Returns the qtyOfNextLowerLevelTradeItem.
	 */
	public String getQtyOfNextLowerLevelTradeItem() {
		return qtyOfNextLowerLevelTradeItem;
	}

	/**
	 * @param qtyOfNextLowerLevelTradeItem
	 *            The qtyOfNextLowerLevelTradeItem to set.
	 */
	public void setQtyOfNextLowerLevelTradeItem(
			String qtyOfNextLowerLevelTradeItem) {
		this.qtyOfNextLowerLevelTradeItem = qtyOfNextLowerLevelTradeItem;
	}

	/**
	 * @return Returns the weightUnitOfMeasure.
	 */
	public String getWeightUnitOfMeasure() {
		return weightUnitOfMeasure;
	}

	/**
	 * @param weightUnitOfMeasure
	 *            The weightUnitOfMeasure to set.
	 */
	public void setWeightUnitOfMeasure(String weightUnitOfMeasure) {
		this.weightUnitOfMeasure = weightUnitOfMeasure;
	}

	/**
	 * @return Returns the width.
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            The width to set.
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return Returns the recordType.
	 */
	public String getRecordType() {
		return recordType;
	}

	/**
	 * @param recordType
	 *            The recordType to set.
	 */
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	/**
	 * @return Returns the ststus.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param ststus
	 *            The ststus to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Send in the Value for the Resource Type, Return the Description for View
	 * on the Screen (Long Description)
	 * 
	 * Creation date: (2/16/2006 -- TWalton)
	 */
	public static String displayResourceType(String value) {

		String returnString = "&nbsp;";
		try {
			if (value.toLowerCase().equals("newcpgitem"))
				returnString = "CPG Tree Top Item";
			if (value.toLowerCase().equals("copack"))
				returnString = "Co-Pack";
			if (value.toLowerCase().equals("privatelabel"))
				returnString = "Private Label";
		} catch (Exception e) {
			System.out
					.println("Exception Occurred in UpdNewItem.displayResourceType(value: "
							+ value);
		}

		return returnString;
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
	 * Validate Dates input on the Update Screen
	 * Test each date, put error messages into the appropriate 
	 * places if there are problems.
	 * Creation date: (2/17/2006 TWalton)
	 */
	private void validateDates() {
		//--------------------------------------------
		// DUE DATE
		if (!dueDate.equals(""))
		   errorDueDate = addFormatToErrorForDisplay(validateDate(dueDate));
		//--------------------------------------------
		// PRODUCTION DATE
		if (!productionDate.equals(""))
		   errorProductionDate = addFormatToErrorForDisplay(validateDate(productionDate));
		//--------------------------------------------
		// BEST BY START DATE
		if (!bestByStartDate.equals(""))
		   errorBestByStartDate = addFormatToErrorForDisplay(validateDate(bestByStartDate));
			
		return;
	}

	/**
	 * Validate Dates input on the Update Screen
	 * Test each date, put error messages into the appropriate 
	 * places if there are problems.
	 * Creation date: (2/17/2006 TWalton)
	 */
	private void validateNumbers() {
	  //**********************************************
	  // INTEGER
	  //**********************************************	
		//--------------------------------------------
		// Stacking Height
		   //is drop down list so do not have to test
		//--------------------------------------------
		// Quantity Items Per Complete Layer
			errorQtyItemsPerCompleteLayer = addFormatToErrorForDisplay(validateInteger(qtyItemsPerCompleteLayer));
		//--------------------------------------------
		// Quantity Of Next Lower Level Trade Item
			errorQtyOfNextLowerLevelTradeItem = addFormatToErrorForDisplay(validateInteger(qtyOfNextLowerLevelTradeItem));
	  //**********************************************
	  // BIG DECIMAL
	  //**********************************************	
		//--------------------------------------------
		// Depth
			errorDepth = addFormatToErrorForDisplay(validateBigDecimal(depth));
		//--------------------------------------------
		// Width
			errorWidth = addFormatToErrorForDisplay(validateBigDecimal(width));
		//--------------------------------------------
		// Gross Weight
			errorGrossWeight = addFormatToErrorForDisplay(validateBigDecimal(grossWeight));
		//--------------------------------------------
		// Net Content
			errorNetContent = addFormatToErrorForDisplay(validateBigDecimal(netContent));
				
		return;
	}
	/**
	 * @return Returns the errorOnScreen.
	 */
	public String getErrorOnScreen() {
		return errorOnScreen;
	}
	/**
	 * @return Returns the errorBestByStartDate.
	 */
	public String getErrorBestByStartDate() {
		return errorBestByStartDate;
	}
	/**
	 * @return Returns the errorCaseUPC.
	 */
	public String getErrorCaseUPC() {
		return errorCaseUPC;
	}
	/**
	 * @return Returns the errorDueDate.
	 */
	public String getErrorDueDate() {
		return errorDueDate;
	}
	/**
	 * @return Returns the errorLabelUPC.
	 */
	public String getErrorLabelUPC() {
		return errorLabelUPC;
	}
	/**
	 * @return Returns the errorPalletGTIN.
	 */
	public String getErrorPalletGTIN() {
		return errorPalletGTIN;
	}
	/**
	 * @return Returns the errorProductionDate.
	 */
	public String getErrorProductionDate() {
		return errorProductionDate;
	}
	/**
	 * @return Returns the errorResourceDescription.
	 */
	public String getErrorResourceDescription() {
		return errorResourceDescription;
	}
	/**
	 * @param errorOnScreen The errorOnScreen to set.
	 */
	public void setErrorOnScreen(String errorOnScreen) {
		this.errorOnScreen = errorOnScreen;
	}
	/**
	 * @return Returns the addNew.
	 */
	public String getAddNew() {
		return addNew;
	}
	/**
	 * @param addNew The addNew to set.
	 */
	public void setAddNew(String addNew) {
		this.addNew = addNew;
	}
}
