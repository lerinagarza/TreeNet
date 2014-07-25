/*
 * Created on October 9, 2007
 *
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author twalto
 *
 * Item information
 */
public class Item {
	
	protected	String		company						= "";
	protected	String		itemNumber					= "";
	protected	String		itemDescription				= "";	
	protected	String		itemLongDescription			= "";
	protected   String		itemType					= "";
	protected   String		itemGroup					= "";
	protected	String		itemGroupDescription		= "";
	protected	String		productGroup				= "";
	protected	String		attributeModel				= "";
	protected   String		attributeModelName			= "";
	protected	String		attributeModelDescription	= "";
	protected   String      basicUnitOfMeasure			= "";
	protected	String		responsible					= "";
	protected	String		status						= "";
	protected	String		statusDescription			= "";
	protected	String		productOwner				= ""; // Accounting Control Field
	protected	String		productOwnerDescription		= "";
	protected   String		bestByItem					= "";
	protected	String		grossWeight					= "";
	protected	String		palletStacking				= ""; // Fragility
	protected	String		organicConventional			= ""; // Environment Group
	protected	String		makeBuyCode					= ""; // is this item Made or Purchased
	protected	String		purchasePrice				= "";
	
	// New Item Application Fields *usually from file MSPWITRS
	protected	String		newItemLastUpdateDate		= "";
	protected	String		newItemLastUpdateTime		= "";
	protected	String		newItemLastUpdateUser		= "";
	protected	String		newItemCaseUPC				= "";
	protected	String		newItemLabelUPC				= "";
	protected	String		newItemPalletGTIN			= "";
	protected	String		newItemManufacturer			= "";
	protected	String		newItemLength				= "";
	protected	String		newItemWidth				= "";
	protected	String		newItemHeight				= "";
	
	protected	String		newItemCustServiceTeam		= "";
	protected	String		newItemSalesPerson			= "";
	protected	String		newItemCarrierUPC			= "";
	protected	String		newItemWrapUPC				= "";
	protected	String		newItemInitiatedDate		= "";
	protected	String		newItemReplacedItem			= "";
	protected	String		newItemSampleOrderDesc		= "";
	
	protected	String		supplierSummaryURL			= "";
	protected   String      pause						= "";
	
	// Information stored in M3 Alias File - relating to items
	protected	String		m3ItemAliasPopular			= "";
	protected	String		m3ItemAliasCaseUPC			= ""; //UPC
	protected	String		m3ItemAliasLabelUPC			= ""; //LBL
	protected	String		m3ItemAliasPalletGTIN		= ""; //GTIN
	protected	String		m3ItemAliasOpenOrders		= ""; //OPN
	protected	String		m3ItemAliasFreshPack		= ""; //FPK
	protected	String		m3ItemAlias100PercentJuice  = ""; //JCE
	protected	String		m3ItemAliasPlanner			= ""; //PLN
	protected	String		m3ItemAliasCAR				= ""; //CAR
	protected	String		m3ItemAliasSingleStrength	= ""; //SS
	protected	String		m3ItemAliasReport1			= ""; //RPT1
	protected	String		m3ItemAliasClubCustItem		= ""; //CCI
	protected	String		m3ItemAliasAllergen			= ""; //ALRG
	
	// Information stored in the Descriptive Code File, about the Alias' tied to the item
	protected	String		m3ItemAliasPlannerDesc		= "";
	protected	String		m3ItemAliasReport1Desc		= "";
	
	// Alternate Units of Measure -- Store certain information MITAUN
	protected	String		casesPerPallet				= ""; // PL
	protected	String		casesPerLayer				= ""; // TIE
	
	// Tickler File Function Group
	protected	String		functionGroup				= ""; // GMFGRP

	/**
	 *  // Constructor
	 */
	public Item() {
		super();

	}
	/**
	 * @return Returns the itemDescription.
	 */
	public String getItemDescription() {
		return itemDescription;
	}
	/**
	 * @param itemDescription The itemDescription to set.
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	/**
	 * @return Returns the itemGroup.
	 */
	public String getItemGroup() {
		return itemGroup;
	}
	/**
	 * @param itemGroup The itemGroup to set.
	 */
	public void setItemGroup(String itemGroup) {
		this.itemGroup = itemGroup;
	}
	/**
	 * @return Returns the itemNumber.
	 */
	public String getItemNumber() {
		return itemNumber;
	}
	/**
	 * @param itemNumber The itemNumber to set.
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	/**
	 * @return Returns the itemType.
	 */
	public String getItemType() {
		return itemType;
	}
	/**
	 * @param itemType The itemType to set.
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	/**
	 * @return Returns the productGroup.
	 */
	public String getProductGroup() {
		return productGroup;
	}
	/**
	 * @param productGroup The productGroup to set.
	 */
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	/**
	 * @return Returns the attributeModel.
	 */
	public String getAttributeModel() {
		return attributeModel;
	}
	/**
	 * @param attributeModel The attributeModel to set.
	 */
	public void setAttributeModel(String attributeModel) {
		this.attributeModel = attributeModel;
	}
	/**
	 * @return Returns the basicUnitOfMeasure.
	 */
	public String getBasicUnitOfMeasure() {
		return basicUnitOfMeasure;
	}
	/**
	 * @param basicUnitOfMeasure The basicUnitOfMeasure to set.
	 */
	public void setBasicUnitOfMeasure(String basicUnitOfMeasure) {
		this.basicUnitOfMeasure = basicUnitOfMeasure;
	}
	/**
	 * @return Returns the itemLongDescription.
	 */
	public String getItemLongDescription() {
		return itemLongDescription;
	}
	/**
	 * @param itemLongDescription The itemLongDescription to set.
	 */
	public void setItemLongDescription(String itemLongDescription) {
		this.itemLongDescription = itemLongDescription;
	}
	/**
	 * @return Returns the responsible.
	 */
	public String getResponsible() {
		return responsible;
	}
	/**
	 * @param responsible The responsible to set.
	 */
	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
		// 1/26/12 TWalton -- Pulled information from F1 screen on M3
		if (status.trim().equals("05"))
		   this.statusDescription = "Template item used to auto-create items and is connected to the item type in (CRS040)";
		if (status.trim().equals("10"))
		   this.statusDescription = "Preliminary item"; 
		if (status.trim().equals("15"))
		   this.statusDescription = "Replacement item. Status remains at 15 until the balance of the replaced item is zero. Then, status is automatically changed to 20"; 
		if (status.trim().equals("20"))
		   this.statusDescription = "Released item";
		if (status.trim().equals("30"))
		   this.statusDescription = "Alternate items available"; 
		if (status.trim().equals("40"))
		   this.statusDescription = "Item is released, but has low turnover. Mass update can be done in (MMS530)"; 
		if (status.trim().equals("50"))
		   this.statusDescription = "Item is to be used and then removed from the assortment. When available balance is zero, the status is automatically changed to 80";
		if (status.trim().equals("80"))
		   this.statusDescription = "Item no longer stocked but such transactions as returns are permitted"; 
		if (status.trim().equals("90"))
		   this.statusDescription = "Item no longer stocked. This status can be entered manually or by the Item filing/deletion program (MWS810)"; 
		if (status.trim().equals("99"))
		   this.statusDescription = "Item no longer stocked due to item number change. The item is only in the item file";
	}
	/**
	 * @return Returns the newItemCaseUPC.
	 */
	public String getNewItemCaseUPC() {
		return newItemCaseUPC;
	}
	/**
	 * @param newItemCaseUPC The newItemCaseUPC to set.
	 */
	public void setNewItemCaseUPC(String newItemCaseUPC) {
		this.newItemCaseUPC = newItemCaseUPC;
	}
	/**
	 * @return Returns the newItemLabelUPC.
	 */
	public String getNewItemLabelUPC() {
		return newItemLabelUPC;
	}
	/**
	 * @param newItemLabelUPC The newItemLabelUPC to set.
	 */
	public void setNewItemLabelUPC(String newItemLabelUPC) {
		this.newItemLabelUPC = newItemLabelUPC;
	}
	/**
	 * @return Returns the newItemLastUpdateDate.
	 */
	public String getNewItemLastUpdateDate() {
		return newItemLastUpdateDate;
	}
	/**
	 * @param newItemLastUpdateDate The newItemLastUpdateDate to set.
	 */
	public void setNewItemLastUpdateDate(String newItemLastUpdateDate) {
		this.newItemLastUpdateDate = newItemLastUpdateDate;
	}
	/**
	 * @return Returns the newItemLastUpdateTime.
	 */
	public String getNewItemLastUpdateTime() {
		return newItemLastUpdateTime;
	}
	/**
	 * @param newItemLastUpdateTime The newItemLastUpdateTime to set.
	 */
	public void setNewItemLastUpdateTime(String newItemLastUpdateTime) {
		this.newItemLastUpdateTime = newItemLastUpdateTime;
	}
	/**
	 * @return Returns the newItemManufacturer.
	 */
	public String getNewItemManufacturer() {
		return newItemManufacturer;
	}
	/**
	 * @param newItemManufacturer The newItemManufacturer to set.
	 */
	public void setNewItemManufacturer(String newItemManufacturer) {
		this.newItemManufacturer = newItemManufacturer;
	}
	/**
	 * @return Returns the newItemPalletGTIN.
	 */
	public String getNewItemPalletGTIN() {
		return newItemPalletGTIN;
	}
	/**
	 * @param newItemPalletGTIN The newItemPalletGTIN to set.
	 */
	public void setNewItemPalletGTIN(String newItemPalletGTIN) {
		this.newItemPalletGTIN = newItemPalletGTIN;
	}
	/**
	 * @return Returns the newItemLastUpdateUser.
	 */
	public String getNewItemLastUpdateUser() {
		return newItemLastUpdateUser;
	}
	/**
	 * @param newItemLastUpdateUser The newItemLastUpdateUser to set.
	 */
	public void setNewItemLastUpdateUser(String newItemLastUpdateUser) {
		this.newItemLastUpdateUser = newItemLastUpdateUser;
	}
	/**
	 * @return Returns the bestByItem.
	 */
	public String getBestByItem() {
		return bestByItem;
	}
	/**
	 * @param bestByItem The bestByItem to set.
	 */
	public void setBestByItem(String bestByItem) {
		this.bestByItem = bestByItem;
	}
	/**
	 * @return Returns the productOwner.
	 */
	public String getProductOwner() {
		return productOwner;
	}
	/**
	 * @param productOwner The productOwner to set.
	 */
	public void setProductOwner(String productOwner) {
		this.productOwner = productOwner;
	}
	/**
	 * @return Returns the productOwnerDescription.
	 */
	public String getProductOwnerDescription() {
		return productOwnerDescription;
	}
	/**
	 * @param productOwnerDescription The productOwnerDescription to set.
	 */
	public void setProductOwnerDescription(String productOwnerDescription) {
		this.productOwnerDescription = productOwnerDescription;
	}
	/**
	 * @return Returns the casesPerLayer.
	 */
	public String getCasesPerLayer() {
		return casesPerLayer;
	}
	/**
	 * @param casesPerLayer The casesPerLayer to set.
	 */
	public void setCasesPerLayer(String casesPerLayer) {
		this.casesPerLayer = casesPerLayer;
	}
	/**
	 * @return Returns the casesPerPallet.
	 */
	public String getCasesPerPallet() {
		return casesPerPallet;
	}
	/**
	 * @param casesPerPallet The casesPerPallet to set.
	 */
	public void setCasesPerPallet(String casesPerPallet) {
		this.casesPerPallet = casesPerPallet;
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
	 * @return Returns the palletStacking.
	 */
	public String getPalletStacking() {
		return palletStacking;
	}
	/**
	 * @param palletStacking The palletStacking to set.
	 */
	public void setPalletStacking(String palletStacking) {
		this.palletStacking = palletStacking;
	}
	/**
	 * @return Returns the functionGroup.
	 */
	public String getFunctionGroup() {
		return functionGroup;
	}
	/**
	 * @param functionGroup The functionGroup to set.
	 */
	public void setFunctionGroup(String functionGroup) {
		this.functionGroup = functionGroup;
	}
	/**
	 * @return Returns the organicConventional.
	 */
	public String getOrganicConventional() {
		return organicConventional;
	}
	/**
	 * @param organicConventional The organicConventional to set.
	 */
	public void setOrganicConventional(String organicConventional) {
		this.organicConventional = organicConventional;
	}
	public String getAttributeModelDescription() {
		return attributeModelDescription;
	}
	public void setAttributeModelDescription(String attributeModelDescription) {
		this.attributeModelDescription = attributeModelDescription;
	}
	public String getAttributeModelName() {
		return attributeModelName;
	}
	public void setAttributeModelName(String attributeModelName) {
		this.attributeModelName = attributeModelName;
	}
	public String getM3ItemAliasCaseUPC() {
		return m3ItemAliasCaseUPC;
	}
	public void setM3ItemAliasCaseUPC(String itemAliasCaseUPC) {
		m3ItemAliasCaseUPC = itemAliasCaseUPC;
	}
	public String getM3ItemAliasLabelUPC() {
		return m3ItemAliasLabelUPC;
	}
	public void setM3ItemAliasLabelUPC(String itemAliasLabelUPC) {
		m3ItemAliasLabelUPC = itemAliasLabelUPC;
	}
	public String getM3ItemAliasPopular() {
		return m3ItemAliasPopular;
	}
	public void setM3ItemAliasPopular(String itemAliasPopular) {
		m3ItemAliasPopular = itemAliasPopular;
	}
	public String getM3ItemAliasPalletGTIN() {
		return m3ItemAliasPalletGTIN;
	}
	public void setM3ItemAliasPalletGTIN(String itemAliasPalletGTIN) {
		m3ItemAliasPalletGTIN = itemAliasPalletGTIN;
	}
	public String getM3ItemAliasFreshPack() {
		return m3ItemAliasFreshPack;
	}
	public void setM3ItemAliasFreshPack(String itemAliasFreshPack) {
		m3ItemAliasFreshPack = itemAliasFreshPack;
	}
	public String getM3ItemAliasOpenOrders() {
		return m3ItemAliasOpenOrders;
	}
	public void setM3ItemAliasOpenOrders(String itemAliasOpenOrders) {
		m3ItemAliasOpenOrders = itemAliasOpenOrders;
	}
	public String getM3ItemAlias100PercentJuice() {
		return m3ItemAlias100PercentJuice;
	}
	public void setM3ItemAlias100PercentJuice(String itemAlias100PercentJuice) {
		m3ItemAlias100PercentJuice = itemAlias100PercentJuice;
	}
	public String getMakeBuyCode() {
		return makeBuyCode;
	}
	public void setMakeBuyCode(String makeBuyCode) {
		this.makeBuyCode = makeBuyCode;
	}
	public String getM3ItemAliasPlanner() {
		return m3ItemAliasPlanner;
	}
	public void setM3ItemAliasPlanner(String itemAliasPlanner) {
		m3ItemAliasPlanner = itemAliasPlanner;
	}
	public String getM3ItemAliasPlannerDesc() {
		return m3ItemAliasPlannerDesc;
	}
	public void setM3ItemAliasPlannerDesc(String itemAliasPlannerDesc) {
		m3ItemAliasPlannerDesc = itemAliasPlannerDesc;
	}
	public String getM3ItemAliasCAR() {
		return m3ItemAliasCAR;
	}
	public void setM3ItemAliasCAR(String itemAliasCAR) {
		m3ItemAliasCAR = itemAliasCAR;
	}
	public String getItemGroupDescription() {
		return itemGroupDescription;
	}
	public void setItemGroupDescription(String itemGroupDescription) {
		this.itemGroupDescription = itemGroupDescription;
	}
	public String getNewItemLength() {
		return newItemLength;
	}
	public void setNewItemLength(String newItemLength) {
		this.newItemLength = newItemLength;
	}
	public String getNewItemWidth() {
		return newItemWidth;
	}
	public void setNewItemWidth(String newItemWidth) {
		this.newItemWidth = newItemWidth;
	}
	public String getNewItemHeight() {
		return newItemHeight;
	}
	public void setNewItemHeight(String newItemHeight) {
		this.newItemHeight = newItemHeight;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	public String getSupplierSummaryURL() {
		return supplierSummaryURL;
	}
	public void setSupplierSummaryURL(String supplierSummaryURL) {
		this.supplierSummaryURL = supplierSummaryURL;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getM3ItemAliasSingleStrength() {
		return m3ItemAliasSingleStrength;
	}
	public void setM3ItemAliasSingleStrength(String m3ItemAliasSingleStrength) {
		this.m3ItemAliasSingleStrength = m3ItemAliasSingleStrength;
	}
	public String getM3ItemAliasReport1() {
		return m3ItemAliasReport1;
	}
	public void setM3ItemAliasReport1(String m3ItemAliasReport1) {
		this.m3ItemAliasReport1 = m3ItemAliasReport1;
	}
	public String getM3ItemAliasClubCustItem() {
		return m3ItemAliasClubCustItem;
	}
	public void setM3ItemAliasClubCustItem(String m3ItemAliasClubCustItem) {
		this.m3ItemAliasClubCustItem = m3ItemAliasClubCustItem;
	}
	public String getM3ItemAliasReport1Desc() {
		return m3ItemAliasReport1Desc;
	}
	public void setM3ItemAliasReport1Desc(String m3ItemAliasReport1Desc) {
		this.m3ItemAliasReport1Desc = m3ItemAliasReport1Desc;
	}
	public String getNewItemCustServiceTeam() {
		return newItemCustServiceTeam;
	}
	public void setNewItemCustServiceTeam(String newItemCustServiceTeam) {
		this.newItemCustServiceTeam = newItemCustServiceTeam;
	}
	public String getNewItemSalesPerson() {
		return newItemSalesPerson;
	}
	public void setNewItemSalesPerson(String newItemSalesPerson) {
		this.newItemSalesPerson = newItemSalesPerson;
	}
	public String getNewItemCarrierUPC() {
		return newItemCarrierUPC;
	}
	public void setNewItemCarrierUPC(String newItemCarrierUPC) {
		this.newItemCarrierUPC = newItemCarrierUPC;
	}
	public String getNewItemWrapUPC() {
		return newItemWrapUPC;
	}
	public void setNewItemWrapUPC(String newItemWrapUPC) {
		this.newItemWrapUPC = newItemWrapUPC;
	}
	public String getNewItemInitiatedDate() {
		return newItemInitiatedDate;
	}
	public void setNewItemInitiatedDate(String newItemInitiatedDate) {
		this.newItemInitiatedDate = newItemInitiatedDate;
	}
	public String getNewItemReplacedItem() {
		return newItemReplacedItem;
	}
	public void setNewItemReplacedItem(String newItemReplacedItem) {
		this.newItemReplacedItem = newItemReplacedItem;
	}
	public String getNewItemSampleOrderDesc() {
		return newItemSampleOrderDesc;
	}
	public void setNewItemSampleOrderDesc(String newItemSampleOrderDesc) {
		this.newItemSampleOrderDesc = newItemSampleOrderDesc;
	}
	public String getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getM3ItemAliasAllergen() {
		return m3ItemAliasAllergen;
	}
	public void setM3ItemAliasAllergen(String m3ItemAliasAllergen) {
		this.m3ItemAliasAllergen = m3ItemAliasAllergen;
	}
	public String getPause() {
		return pause;
	}
	public void setPause(String pause) {
		this.pause = pause;
	}
	
}
