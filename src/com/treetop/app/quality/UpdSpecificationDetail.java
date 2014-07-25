/*
 * Created on May 25, 2010
 */

package com.treetop.app.quality;


import com.treetop.services.ServiceCustomer;
import com.treetop.services.ServiceItem;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.viewbeans.CommonRequestBean;

/**
 * @author twalto
 */

public class UpdSpecificationDetail extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   			= "";
	public String displayMessage 			= "";
	public String environment 				= "";

	// Must have in Update View Bean
	public String updateUser 				= "";
	
	public String companyNumber 			= "";
	public String divisionNumber 			= "";
	public String specNumber 				= "";
	public String revisionDate 				= "";
	public String revisionTime 				= "";
	
	// Only needed IF the packaging details are at the ITEM level
	public String itemNumber 				= "";
	public String itemNumberError 			= "";
	public String itemDescription 			= "";	
	public String componentSpecNumber 		= "0";
	public String componentSpecNumberError	= "";
	public String componentSpecRevisionDate = "0";
	public String componentSpecRevisionTime = "0";
	
	public String addPackaging				= "";// to be used to determine if we want a Packaging record or not for a given item
	public String sequenceNumber 			= "0";
		
	public String unitLength				= "0";
	public String unitLengthError			= "";
	public String unitWidth					= "0";
	public String unitWidthError			= "";
	public String unitHeight				= "0";
	public String unitHeightError			= "";
	
	// Not always displayed and updatable 
	public String unitWeight 				= "0";
	public String unitCube					= "0";
	public String unitsPerPallet			= "0";
	public String unitsPerLayer				= "0";
	public String layersPerPallet			= "0";
	public String palletStacking			= "0";
	public String palletHeight				= "0";
	public String unitUPCNumber				= "0";
	public String labelUPCNumber			= "0";
	public String palletGTINNumber			= "0";
	public String shelfLife					= "0"; // Red IF different warehouses have more than one shelf Life

	public String stretchWrap 				= "";
	public String stretchWrapDescription 	= "";
	public String shrinkWrap 				= "";
	public String shrinkWrapDescription 	= "";
	public String slipSheetInformation 		= "";
	public String dateCodingInformation 	= "";
	public String unitCodeText				= "";
	public String unitTextLine1				= "";
	public String unitTextLine2				= "";
	public String unitTextGeneral			= "";
	public String generalComments			= "";
	public String storageConditions 		= "";
	public String specialRequirements 		= "";
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		StringBuffer foundProblem = new StringBuffer();
		try
		{	
			// add Packaging *****************************************
			if (!this.getAddPackaging().trim().equals(""))
			   this.setAddPackaging("Y");
			// Item Number *******************************************
			if (!this.getItemNumber().trim().equals(""))
			{
			   CommonRequestBean crb = new CommonRequestBean();
			   crb.setEnvironment(this.getEnvironment());
			   crb.setCompanyNumber(this.getCompanyNumber());
			   crb.setDivisionNumber(this.getDivisionNumber());
			   crb.setIdLevel1(this.getItemNumber().trim());
			   this.setItemNumberError(ServiceItem.verifyItem(crb));
			   
			}
			// Length ************************************************
			try{
				if (!this.getUnitLength().trim().equals("") &&
					!this.getUnitLength().trim().equals("0"))
				{
					this.setUnitLengthError(validateBigDecimal(this.getUnitLength()));
					if (!this.getUnitLengthError().trim().equals(""))
					  foundProblem.append(this.getUnitLengthError().trim() + "<br>");
				}
			}catch(Exception e){
		  		this.setUnitLengthError("Problem Found with the Length. " + e);
		  		foundProblem.append("Problem Found with the Length<br>");
		  	}
			// Width *************************************************
			try{
				if (!this.getUnitWidth().trim().equals("") &&
					!this.getUnitWidth().trim().equals("0"))
				{
					this.setUnitWidthError(validateBigDecimal(this.getUnitWidth()));
					if (!this.getUnitWidthError().trim().equals(""))
					  foundProblem.append(this.getUnitWidthError().trim() + "<br>");
				}
			}catch(Exception e){
		  		this.setUnitWidthError("Problem Found with the Width. " + e);
		  		foundProblem.append("Problem Found with the Length<br>");
		  	}
			// Height ************************************************
			try{
				if (!this.getUnitHeight().trim().equals("") &&
					!this.getUnitHeight().trim().equals("0"))
				{
					this.setUnitHeightError(validateBigDecimal(this.getUnitHeight()));
					if (!this.getUnitHeightError().trim().equals(""))
					  foundProblem.append(this.getUnitHeightError().trim() + "<br>");
				}
			}catch(Exception e){
		  		this.setUnitLengthError("Problem Found with the Height. " + e);
		  		foundProblem.append("Problem Found with the Height<br>");
		  	}
			this.setDisplayMessage(foundProblem.toString().trim());		
		}
		catch(Exception e)
		{
			
		}
		return;
	}
	/**
	 * @return
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param string
	 */
	public void setRequestType(String string) {
		requestType = string;
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
	 * @return Returns the displayMessage.
	 */
	public String getDisplayMessage() {
		return displayMessage;
	}
	/**
	 * @param displayMessage The displayMessage to set.
	 */
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}
	/**
	 * @return Returns the revisionDate.
	 */
	public String getRevisionDate() {
		return revisionDate;
	}
	/**
	 * @param revisionDate The revisionDate to set.
	 */
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public String getRevisionTime() {
		return revisionTime;
	}
	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}
	public String getSpecNumber() {
		return specNumber;
	}
	public void setSpecNumber(String specNumber) {
		this.specNumber = specNumber;
	}
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getItemNumberError() {
		return itemNumberError;
	}
	public void setItemNumberError(String itemNumberError) {
		this.itemNumberError = itemNumberError;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getCompanyNumber() {
		return companyNumber;
	}	
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	public String getDivisionNumber() {
		return divisionNumber;
	}
	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;			
	}
	public String getShrinkWrap() {
		return shrinkWrap;
	}
	public void setShrinkWrap(String shrinkWrap) {
		this.shrinkWrap = shrinkWrap;
	}
	public String getShrinkWrapDescription() {
		return shrinkWrapDescription;
	}
	public void setShrinkWrapDescription(String shrinkWrapDescription) {
		this.shrinkWrapDescription = shrinkWrapDescription;
	}
	public String getSlipSheetInformation() {
		return slipSheetInformation;
	}
	public void setSlipSheetInformation(String slipSheetInformation) {
		this.slipSheetInformation = slipSheetInformation;
	}
	public String getSpecialRequirements() {
		return specialRequirements;
	}
	public void setSpecialRequirements(String specialRequirements) {
		this.specialRequirements = specialRequirements;
	}
	public String getStorageConditions() {
		return storageConditions;
	}
	public void setStorageConditions(String storageConditions) {
		this.storageConditions = storageConditions;
	}
	public String getStretchWrap() {
		return stretchWrap;
	}
	public void setStretchWrap(String stretchWrap) {
		this.stretchWrap = stretchWrap;
	}
	public String getStretchWrapDescription() {
		return stretchWrapDescription;
	}
	public void setStretchWrapDescription(String stretchWrapDescription) {
		this.stretchWrapDescription = stretchWrapDescription;
	}
	public String getComponentSpecNumber() {
		return componentSpecNumber;
	}
	public void setComponentSpecNumber(String componentSpecNumber) {
		this.componentSpecNumber = componentSpecNumber;
	}
	public String getComponentSpecNumberError() {
		return componentSpecNumberError;
	}
	public void setComponentSpecNumberError(String componentSpecNumberError) {
		this.componentSpecNumberError = componentSpecNumberError;
	}
	public String getComponentSpecRevisionDate() {
		return componentSpecRevisionDate;
	}
	public void setComponentSpecRevisionDate(String componentSpecRevisionDate) {
		this.componentSpecRevisionDate = componentSpecRevisionDate;
	}
	public String getComponentSpecRevisionTime() {
		return componentSpecRevisionTime;
	}
	public void setComponentSpecRevisionTime(String componentSpecRevisionTime) {
		this.componentSpecRevisionTime = componentSpecRevisionTime;
	}
	public String getDateCodingInformation() {
		return dateCodingInformation;
	}
	public void setDateCodingInformation(String dateCodingInformation) {
		this.dateCodingInformation = dateCodingInformation;
	}
	public String getGeneralComments() {
		return generalComments;
	}
	public void setGeneralComments(String generalComments) {
		this.generalComments = generalComments;
	}
	public String getLabelUPCNumber() {
		return labelUPCNumber;
	}
	public void setLabelUPCNumber(String labelUPCNumber) {
		this.labelUPCNumber = labelUPCNumber;
	}
	public String getLayersPerPallet() {
		return layersPerPallet;
	}
	public void setLayersPerPallet(String layersPerPallet) {
		this.layersPerPallet = layersPerPallet;
	}
	public String getPalletGTINNumber() {
		return palletGTINNumber;
	}
	public void setPalletGTINNumber(String palletGTINNumber) {
		this.palletGTINNumber = palletGTINNumber;
	}
	public String getPalletHeight() {
		return palletHeight;
	}
	public void setPalletHeight(String palletHeight) {
		this.palletHeight = palletHeight;
	}
	public String getPalletStacking() {
		return palletStacking;
	}
	public void setPalletStacking(String palletStacking) {
		this.palletStacking = palletStacking;
	}
	public String getShelfLife() {
		return shelfLife;
	}
	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}
	public String getUnitCodeText() {
		return unitCodeText;
	}
	public void setUnitCodeText(String unitCodeText) {
		this.unitCodeText = unitCodeText;
	}
	public String getUnitCube() {
		return unitCube;
	}
	public void setUnitCube(String unitCube) {
		this.unitCube = unitCube;
	}
	public String getUnitHeight() {
		return unitHeight;
	}
	public void setUnitHeight(String unitHeight) {
		this.unitHeight = unitHeight;
	}
	public String getUnitHeightError() {
		return unitHeightError;
	}
	public void setUnitHeightError(String unitHeightError) {
		this.unitHeightError = unitHeightError;
	}
	public String getUnitLength() {
		return unitLength;
	}
	public void setUnitLength(String unitLength) {
		this.unitLength = unitLength;
	}
	public String getUnitLengthError() {
		return unitLengthError;
	}
	public void setUnitLengthError(String unitLengthError) {
		this.unitLengthError = unitLengthError;
	}
	public String getUnitsPerLayer() {
		return unitsPerLayer;
	}
	public void setUnitsPerLayer(String unitsPerLayer) {
		this.unitsPerLayer = unitsPerLayer;
	}
	public String getUnitsPerPallet() {
		return unitsPerPallet;
	}
	public void setUnitsPerPallet(String unitsPerPallet) {
		this.unitsPerPallet = unitsPerPallet;
	}
	public String getUnitTextGeneral() {
		return unitTextGeneral;
	}
	public void setUnitTextGeneral(String unitTextGeneral) {
		this.unitTextGeneral = unitTextGeneral;
	}
	public String getUnitTextLine1() {
		return unitTextLine1;
	}
	public void setUnitTextLine1(String unitTextLine1) {
		this.unitTextLine1 = unitTextLine1;
	}
	public String getUnitTextLine2() {
		return unitTextLine2;
	}
	public void setUnitTextLine2(String unitTextLine2) {
		this.unitTextLine2 = unitTextLine2;
	}
	public String getUnitUPCNumber() {
		return unitUPCNumber;
	}
	public void setUnitUPCNumber(String unitUPCNumber) {
		this.unitUPCNumber = unitUPCNumber;
	}
	public String getUnitWeight() {
		return unitWeight;
	}
	public void setUnitWeight(String unitWeight) {
		this.unitWeight = unitWeight;
	}
	public String getUnitWidth() {
		return unitWidth;
	}
	public void setUnitWidth(String unitWidth) {
		this.unitWidth = unitWidth;
	}
	public String getUnitWidthError() {
		return unitWidthError;
	}
	public void setUnitWidthError(String unitWidthError) {
		this.unitWidthError = unitWidthError;
	}
	public String getAddPackaging() {
		return addPackaging;
	}
	public void setAddPackaging(String addPackaging) {
		this.addPackaging = addPackaging;
	}
	
}
