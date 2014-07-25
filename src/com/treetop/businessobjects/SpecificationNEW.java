/*
 * Created on October 22, 2008
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Specification information - currently only CPG
 */
public class SpecificationNEW {
	
	protected	ItemWarehouse	itemWhse				= new ItemWarehouse();
	protected	String			specStatus				= "";	
	protected   String			revisionDate			= "";
	protected   String			supercedesDate			= "";
	protected	String			formulaNumber			= "";
	protected	String			headComment				= "";
	protected	String			labBookID				= "";
	protected	String			recordStatus			= "";
	protected	String			lastUpdateDate			= "";
	protected	String			lastUpdateTime			= "";
	protected	String			lastUpdateUser			= "";
	
	// from the Packing Section -- 1 to 1 with the Header
	protected	String			length					= "";
	protected	String			width					= "";
	protected	String			height					= "";
	protected	String			slipSheetInfo			= "";
	protected	String			stretchWrap				= "";
	protected	String			stretchWrapInfo			= "";
	protected	String			shrinkWrap				= "";
	protected	String			shrinkWrapInfo			= "";
	protected	String			codingInfo				= "";
	protected	String			caseCodePrint			= "";
	protected	String			casePrintLine1			= "";
	protected	String			casePrintLine2			= "";
	protected	String			casePrintGeneral		= "";
	protected	String			storageConditions		= "";
	protected	String			specialRequirements		= "";
	protected	String			packComment				= "";

	/**
	 *  // Constructor
	 */
	public SpecificationNEW() {
		super();
	}
	/**
	 * @return Returns the formulaNumber.
	 */
	public String getFormulaNumber() {
		return formulaNumber;
	}
	/**
	 * @param formulaNumber The formulaNumber to set.
	 */
	public void setFormulaNumber(String formulaNumber) {
		this.formulaNumber = formulaNumber;
	}
	/**
	 * @return Returns the itemWhse.
	 */
	public ItemWarehouse getItemWhse() {
		return itemWhse;
	}
	/**
	 * @param itemWhse The itemWhse to set.
	 */
	public void setItemWhse(ItemWarehouse itemWhse) {
		this.itemWhse = itemWhse;
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
	/**
	 * @return Returns the specStatus.
	 */
	public String getSpecStatus() {
		return specStatus;
	}
	/**
	 * @param specStatus The specStatus to set.
	 */
	public void setSpecStatus(String specStatus) {
		this.specStatus = specStatus;
	}
	/**
	 * @return Returns the supercedesDate.
	 */
	public String getSupercedesDate() {
		return supercedesDate;
	}
	/**
	 * @param supercedesDate The supercedesDate to set.
	 */
	public void setSupercedesDate(String supercedesDate) {
		this.supercedesDate = supercedesDate;
	}
	/**
	 * @return Returns the caseCodePrint.
	 */
	public String getCaseCodePrint() {
		return caseCodePrint;
	}
	/**
	 * @param caseCodePrint The caseCodePrint to set.
	 */
	public void setCaseCodePrint(String caseCodePrint) {
		this.caseCodePrint = caseCodePrint;
	}
	/**
	 * @return Returns the casePrintGeneral.
	 */
	public String getCasePrintGeneral() {
		return casePrintGeneral;
	}
	/**
	 * @param casePrintGeneral The casePrintGeneral to set.
	 */
	public void setCasePrintGeneral(String casePrintGeneral) {
		this.casePrintGeneral = casePrintGeneral;
	}
	/**
	 * @return Returns the casePrintLine1.
	 */
	public String getCasePrintLine1() {
		return casePrintLine1;
	}
	/**
	 * @param casePrintLine1 The casePrintLine1 to set.
	 */
	public void setCasePrintLine1(String casePrintLine1) {
		this.casePrintLine1 = casePrintLine1;
	}
	/**
	 * @return Returns the casePrintLine2.
	 */
	public String getCasePrintLine2() {
		return casePrintLine2;
	}
	/**
	 * @param casePrintLine2 The casePrintLine2 to set.
	 */
	public void setCasePrintLine2(String casePrintLine2) {
		this.casePrintLine2 = casePrintLine2;
	}
	/**
	 * @return Returns the codingInfo.
	 */
	public String getCodingInfo() {
		return codingInfo;
	}
	/**
	 * @param codingInfo The codingInfo to set.
	 */
	public void setCodingInfo(String codingInfo) {
		this.codingInfo = codingInfo;
	}
	/**
	 * @return Returns the headComment.
	 */
	public String getHeadComment() {
		return headComment;
	}
	/**
	 * @param headComment The headComment to set.
	 */
	public void setHeadComment(String headComment) {
		this.headComment = headComment;
	}
	/**
	 * @return Returns the height.
	 */
	public String getHeight() {
		return height;
	}
	/**
	 * @param height The height to set.
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	/**
	 * @return Returns the labBookID.
	 */
	public String getLabBookID() {
		return labBookID;
	}
	/**
	 * @param labBookID The labBookID to set.
	 */
	public void setLabBookID(String labBookID) {
		this.labBookID = labBookID;
	}
	/**
	 * @return Returns the length.
	 */
	public String getLength() {
		return length;
	}
	/**
	 * @param length The length to set.
	 */
	public void setLength(String length) {
		this.length = length;
	}
	/**
	 * @return Returns the packComment.
	 */
	public String getPackComment() {
		return packComment;
	}
	/**
	 * @param packComment The packComment to set.
	 */
	public void setPackComment(String packComment) {
		this.packComment = packComment;
	}
	/**
	 * @return Returns the recordStatus.
	 */
	public String getRecordStatus() {
		return recordStatus;
	}
	/**
	 * @param recordStatus The recordStatus to set.
	 */
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	/**
	 * @return Returns the shrinkWrap.
	 */
	public String getShrinkWrap() {
		return shrinkWrap;
	}
	/**
	 * @param shrinkWrap The shrinkWrap to set.
	 */
	public void setShrinkWrap(String shrinkWrap) {
		this.shrinkWrap = shrinkWrap;
	}
	/**
	 * @return Returns the shrinkWrapInfo.
	 */
	public String getShrinkWrapInfo() {
		return shrinkWrapInfo;
	}
	/**
	 * @param shrinkWrapInfo The shrinkWrapInfo to set.
	 */
	public void setShrinkWrapInfo(String shrinkWrapInfo) {
		this.shrinkWrapInfo = shrinkWrapInfo;
	}
	/**
	 * @return Returns the slipSheetInfo.
	 */
	public String getSlipSheetInfo() {
		return slipSheetInfo;
	}
	/**
	 * @param slipSheetInfo The slipSheetInfo to set.
	 */
	public void setSlipSheetInfo(String slipSheetInfo) {
		this.slipSheetInfo = slipSheetInfo;
	}
	/**
	 * @return Returns the specialRequirements.
	 */
	public String getSpecialRequirements() {
		return specialRequirements;
	}
	/**
	 * @param specialRequirements The specialRequirements to set.
	 */
	public void setSpecialRequirements(String specialRequirements) {
		this.specialRequirements = specialRequirements;
	}
	/**
	 * @return Returns the storageConditions.
	 */
	public String getStorageConditions() {
		return storageConditions;
	}
	/**
	 * @param storageConditions The storageConditions to set.
	 */
	public void setStorageConditions(String storageConditions) {
		this.storageConditions = storageConditions;
	}
	/**
	 * @return Returns the stretchWrap.
	 */
	public String getStretchWrap() {
		return stretchWrap;
	}
	/**
	 * @param stretchWrap The stretchWrap to set.
	 */
	public void setStretchWrap(String stretchWrap) {
		this.stretchWrap = stretchWrap;
	}
	/**
	 * @return Returns the stretchWrapInfo.
	 */
	public String getStretchWrapInfo() {
		return stretchWrapInfo;
	}
	/**
	 * @param stretchWrapInfo The stretchWrapInfo to set.
	 */
	public void setStretchWrapInfo(String stretchWrapInfo) {
		this.stretchWrapInfo = stretchWrapInfo;
	}
	/**
	 * @return Returns the width.
	 */
	public String getWidth() {
		return width;
	}
	/**
	 * @param width The width to set.
	 */
	public void setWidth(String width) {
		this.width = width;
	}
	/**
	 * @return Returns the lastUpdateDate.
	 */
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	/**
	 * @param lastUpdateDate The lastUpdateDate to set.
	 */
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	/**
	 * @return Returns the lastUpdateTime.
	 */
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * @param lastUpdateUser The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
}
