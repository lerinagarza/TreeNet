/*
 * Created on Aug 16, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.businessobjects;

import java.util.*;
import java.math.*;

/**
 * @author thaile
 *
 * Contains values specific to the Resource Reservation 
 * System.
 */
public class ResourceNew extends Resource {

	String	setupDueDate;
	String	tentative1stProductionDate;
	String	projectOwner;
	String	lastUpdateDate;
	String	lastUpdateTime;
	String	lastUpdateUser;
	String	lastUpdateWorkStation;
	String	caseUpcNumber;
	String	itemUpcNumber;
	String	resourceDescriptionLong;
	String	palletGTIN;
	String	gs1CompanyPrefix;
	String	orderEntryDescription;
	String	billOfLadingDescription;
	String	announced;
	String	stackingHeight;
	String	bestByNonSaleableHiLimit;
	String	bestByNonSaleableLowLimit;
	String	bestBySalvageHiLimit;
	String	bestBySalvageLowLimit;
	String	bestByCriticalHiLimit;
	String	bestByCriticalLowLimit;
	String	bestByWatchHiLimit;
	String	bestByWatchLowLimit;
	String	bestByOtherHiLimit;
	String	bestByOtherLowLimit;
	String	nonBestByExtraHiLimit;
	String	nonBestByExtraLowLimit;
	String	nonBestBySalvageHiLimit;
	String	nonBestBySalvageLowLimit;
	String	nonBestByCriticalHiLimit;
	String	nonBestByCriticalLowLimit;
	String	nonBestByWatchHiLimit;
	String	nonBestByWatchLowLimit;
	String	nonBestByOtherHiLimit;
	String	nonBestByOtherLowLimit;
	String	bestByDaysToQahd;
	String	nonBestByDaysToQahd;
	String	shelfLifeFlag;
	String	shelfLifeDays;
	String	bestBuyStartDate;
	
	Vector comments	= new Vector();	//cast as KeyValue
	Vector functions = new Vector(); //cast as TicklerFunctionDetail
	Vector resourceUrls = new Vector(); //cast as KeyValue
	
	// fields for resources w/out gtin info.
	String  qtyItemsPerCompleteLayer;
	String  qtyOfNextLowerLevelTradeItem;
	String	depth;
	String	width;
	String	grossWeight;
	String	weightUnitOfMeasure;
	String	netContent;
	String  netContentUnitOfMeasure;

	/**
	 * 
	 */
	public ResourceNew() {
		super();
	}

	/**
	 * @return
	 */
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @return
	 */
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @return
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @return
	 */
	public String getLastUpdateWorkStation() {
		return lastUpdateWorkStation;
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
	public String getSetupDueDate() {
		return setupDueDate;
	}

	/**
	 * @return
	 */
	public String getTentative1stProductionDate() {
		return tentative1stProductionDate;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateDate(String string) {
		lastUpdateDate = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateTime(String string) {
		lastUpdateTime = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateUser(String string) {
		lastUpdateUser = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateWorkStation(String string) {
		lastUpdateWorkStation = string;
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
	public void setSetupDueDate(String string) {
		setupDueDate = string;
	}

	/**
	 * @param string
	 */
	public void setTentative1stProductionDate(String string) {
		tentative1stProductionDate = string;
	}

	/**
	 * @return
	 */
	public String getCaseUpcNumber() {
		return caseUpcNumber;
	}

	/**
	 * @return
	 */
	public String getItemUpcNumber() {
		return itemUpcNumber;
	}

	/**
	 * @param string
	 */
	public void setCaseUpcNumber(String string) {
		caseUpcNumber = string;
	}

	/**
	 * @param string
	 */
	public void setItemUpcNumber(String string) {
		itemUpcNumber = string;
	}

	/**
	 * Used to test creation of class.
	**/
	public String toString() {

		return new String(
			"recordType: "
				+ recordType
				+ "\n"
				+ "status"
				+ status
				+ "\n"
				+ "resourceNumber: "
				+ resourceNumber
				+ "\n"
				+ "resourceDescription: "
				+ resourceDescription
				+ "\n"
				+ "setupDueDate: "
				+ setupDueDate
				+ "\n"
				+ "tentative1stProductionDate: "
				+ tentative1stProductionDate
				+ "\n"
				+ "projectOwner: "
				+ projectOwner
				+ "\n"
				+ "lastUpdateDate: "
				+ lastUpdateDate
				+ "\n"
				+ "lastUpdateTime: "
				+ lastUpdateTime
				+ "\n"
				+ "lastUpdateUser: "
				+ lastUpdateUser
				+ "\n"
				+ "lastUpdateWorkStation: "
				+ lastUpdateWorkStation
				+ "\n"
				+ "caseUpcNumber: "
				+ caseUpcNumber
				+ "\n"
				+ "itemUpcNumber: "
				+ itemUpcNumber
				+ "\n");
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
	public String getResourceDescriptionLong() {
		return resourceDescriptionLong;
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
	public void setResourceDescriptionLong(String string) {
		resourceDescriptionLong = string;
	}

	/**
	 * @return
	 */
	public String getBillOfLadingDescription() {
		return billOfLadingDescription;
	}

	/**
	 * @return
	 */
	public String getGs1CompanyPrefix() {
		return gs1CompanyPrefix;
	}

	/**
	 * @return
	 */
	public String getOrderEntryDescription() {
		return orderEntryDescription;
	}

	/**
	 * @param string
	 */
	public void setBillOfLadingDescription(String string) {
		billOfLadingDescription = string;
	}

	/**
	 * @param string
	 */
	public void setGs1CompanyPrefix(String string) {
		gs1CompanyPrefix = string;
	}

	/**
	 * @param string
	 */
	public void setOrderEntryDescription(String string) {
		orderEntryDescription = string;
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
	public Vector getComments() {
		return comments;
	}

	/**
	 * @param vector
	 */
	public void setComments(Vector vector) {
		comments = vector;
	}

	/**
	 * @return
	 */
	public Vector getFunctions() {
		return functions;
	}

	/**
	 * @param vector
	 */
	public void setFunctions(Vector vector) {
		functions = vector;
	}

	/**
	 * @return
	 */
	public Vector getResourceUrls() {
		return resourceUrls;
	}

	/**
	 * @param vector
	 */
	public void setResourceUrls(Vector vector) {
		resourceUrls = vector;
	}

	/**
	 * @return
	 */
	public String getBestByCriticalHiLimit() {
		return bestByCriticalHiLimit;
	}

	/**
	 * @return
	 */
	public String getBestByCriticalLowLimit() {
		return bestByCriticalLowLimit;
	}

	/**
	 * @return
	 */
	public String getBestByDaysToQahd() {
		return bestByDaysToQahd;
	}

	/**
	 * @return
	 */
	public String getBestByNonSaleableHiLimit() {
		return bestByNonSaleableHiLimit;
	}

	/**
	 * @return
	 */
	public String getBestByNonSaleableLowLimit() {
		return bestByNonSaleableLowLimit;
	}

	/**
	 * @return
	 */
	public String getBestByOtherHiLimit() {
		return bestByOtherHiLimit;
	}

	/**
	 * @return
	 */
	public String getBestByOtherLowLimit() {
		return bestByOtherLowLimit;
	}

	/**
	 * @return
	 */
	public String getBestBySalvageHiLimit() {
		return bestBySalvageHiLimit;
	}

	/**
	 * @return
	 */
	public String getBestBySalvageLowLimit() {
		return bestBySalvageLowLimit;
	}

	/**
	 * @return
	 */
	public String getBestByWatchHiLimit() {
		return bestByWatchHiLimit;
	}

	/**
	 * @return
	 */
	public String getBestByWatchLowLimit() {
		return bestByWatchLowLimit;
	}

	/**
	 * @return
	 */
	public String getNonBestByCriticalHiLimit() {
		return nonBestByCriticalHiLimit;
	}

	/**
	 * @return
	 */
	public String getNonBestByCriticalLowLimit() {
		return nonBestByCriticalLowLimit;
	}

	/**
	 * @return
	 */
	public String getNonBestByDaysToQahd() {
		return nonBestByDaysToQahd;
	}

	/**
	 * @return
	 */
	public String getNonBestByExtraHiLimit() {
		return nonBestByExtraHiLimit;
	}

	/**
	 * @return
	 */
	public String getNonBestByExtraLowLimit() {
		return nonBestByExtraLowLimit;
	}

	/**
	 * @return
	 */
	public String getNonBestByOtherHiLimit() {
		return nonBestByOtherHiLimit;
	}

	/**
	 * @return
	 */
	public String getNonBestByOtherLowLimit() {
		return nonBestByOtherLowLimit;
	}

	/**
	 * @return
	 */
	public String getNonBestBySalvageHiLimit() {
		return nonBestBySalvageHiLimit;
	}

	/**
	 * @return
	 */
	public String getNonBestBySalvageLowLimit() {
		return nonBestBySalvageLowLimit;
	}

	/**
	 * @return
	 */
	public String getNonBestByWatchHiLimit() {
		return nonBestByWatchHiLimit;
	}

	/**
	 * @return
	 */
	public String getNonBestByWatchLowLimit() {
		return nonBestByWatchLowLimit;
	}

	/**
	 * @return
	 */
	public String getShelfLifeDays() {
		return shelfLifeDays;
	}

	/**
	 * @return
	 */
	public String getShelfLifeFlag() {
		return shelfLifeFlag;
	}

	/**
	 * @return
	 */
	public String getStackingHeight() {
		return stackingHeight;
	}

	/**
	 * @param integer
	 */
	public void setBestByCriticalHiLimit(String string) {
		bestByCriticalHiLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setBestByCriticalLowLimit(String string) {
		bestByCriticalLowLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setBestByDaysToQahd(String string) {
		bestByDaysToQahd = string;
	}

	/**
	 * @param integer
	 */
	public void setBestByNonSaleableHiLimit(String string) {
		bestByNonSaleableHiLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setBestByNonSaleableLowLimit(String string) {
		bestByNonSaleableLowLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setBestByOtherHiLimit(String string) {
		bestByOtherHiLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setBestByOtherLowLimit(String string) {
		bestByOtherLowLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setBestBySalvageHiLimit(String string) {
		bestBySalvageHiLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setBestBySalvageLowLimit(String string) {
		bestBySalvageLowLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setBestByWatchHiLimit(String string) {
		bestByWatchHiLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setBestByWatchLowLimit(String string) {
		bestByWatchLowLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setNonBestByCriticalHiLimit(String string) {
		nonBestByCriticalHiLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setNonBestByCriticalLowLimit(String string) {
		nonBestByCriticalLowLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setNonBestByDaysToQahd(String string) {
		nonBestByDaysToQahd = string;
	}

	/**
	 * @param integer
	 */
	public void setNonBestByExtraHiLimit(String string) {
		nonBestByExtraHiLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setNonBestByExtraLowLimit(String string) {
		nonBestByExtraLowLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setNonBestByOtherHiLimit(String string) {
		nonBestByOtherHiLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setNonBestByOtherLowLimit(String string) {
		nonBestByOtherLowLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setNonBestBySalvageHiLimit(String string) {
		nonBestBySalvageHiLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setNonBestBySalvageLowLimit(String string) {
		nonBestBySalvageLowLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setNonBestByWatchHiLimit(String string) {
		nonBestByWatchHiLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setNonBestByWatchLowLimit(String string) {
		nonBestByWatchLowLimit = string;
	}

	/**
	 * @param integer
	 */
	public void setShelfLifeDays(String string) {
		shelfLifeDays = string;
	}

	/**
	 * @param string
	 */
	public void setShelfLifeFlag(String string) {
		shelfLifeFlag = string;
	}

	/**
	 * @param decimal
	 */
	public void setStackingHeight(String string) {
		stackingHeight = string;
	}

	/**
	 * @return Returns the depth.
	 */
	public String getDepth() {
		return depth;
	}
	/**
	 * @param depth The depth to set.
	 */
	public void setDepth(String depth) {
		this.depth = depth;
	}
	/**
	 * @return Returns the grossWeight.
	 */
	public String getGrossWeight() {
		return grossWeight;
	}
	/**
	 * @param grossWeight The grossWeight to set.
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
	 * @param netContent The netContent to set.
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
	 * @param netContentUnitOfMeasure The netContentUnitOfMeasure to set.
	 */
	public void setNetContentUnitOfMeasure(String netContentUnitOfMeasure) {
		this.netContentUnitOfMeasure = netContentUnitOfMeasure;
	}
	/**
	 * @return Returns the qtyItemsPerCompleteLayer.
	 */
	public String getQtyItemsPerCompleteLayer() {
		return qtyItemsPerCompleteLayer;
	}
	/**
	 * @param qtyItemsPerCompleteLayer The qtyItemsPerCompleteLayer to set.
	 */
	public void setQtyItemsPerCompleteLayer(String qtyItemsPerCompleteLayer) {
		this.qtyItemsPerCompleteLayer = qtyItemsPerCompleteLayer;
	}
	/**
	 * @return Returns the qtyOfNextLowerLevelTradeItem.
	 */
	public String getQtyOfNextLowerLevelTradeItem() {
		return qtyOfNextLowerLevelTradeItem;
	}
	/**
	 * @param qtyOfNextLowerLevelTradeItem The qtyOfNextLowerLevelTradeItem to set.
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
	 * @param weightUnitOfMeasure The weightUnitOfMeasure to set.
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
	 * @param width The width to set.
	 */
	public void setWidth(String width) {
		this.width = width;
	}
	
	/**
	 * @return Returns the bestBuyStartDate.
	 */
	public String getBestBuyStartDate() {
		return bestBuyStartDate;
	}
	/**
	 * @param bestBuyStartDate The bestBuyStartDate to set.
	 */
	public void setBestBuyStartDate(String bestBuyStartDate) {
		this.bestBuyStartDate = bestBuyStartDate;
	}
}
