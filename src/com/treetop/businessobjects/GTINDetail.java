/*
 * Created on Oct 25, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author teri Walton
 *
 * Detail of the GTIN -- Additional information from the GTIN Master
 */
public class GTINDetail extends GTIN{
	
		// from File 	prisme01/MSPRUCCN
	String	targetMarketCountryCode;				//MSRCNY
	String	catalogItemState;						//MSRCIS
	String	informationProvider;					//MSRBRO
	String	nameOfInformationProvider;				//MSRBRN
	String	eanUCCType;								//MSRUCT
	String	eanUCCCode;								//MSRUCC
	String	isInformationPrivate;					//MSRPVT (Y/N)
	String  isConsumerUnit;							//MSRCNU (Y/N)
	String  classificationCategoryCode;				//MSRCCC
	String	netContent;								//MSRNCT
	String  netContentUnitOfMeasure;		 		//MSRCUM
	String	height;									//MSRHGT
	String	width;									//MSRWTH
	String	depth;									//MSRDTH
	String  linearUnitOfMeasure;					//MSRLUM
	String	netWeight;								//MSRNWG
	String	grossWeight;							//MSRGWG
	String	weightUnitOfMeasure;					//MSRWUM
	String	volume;									//MSRVOL
	String	volumeUnitOfMeasure;					//MSRVUM
	String  qtyOfNextLowerLevelTradeItem;   		//MSRNLL
	String	effectiveDate;							//MSREDT
	String	additionalTradeItemDescription;			//MSRADS
	String	qtyChildren; 			  				//MSRCQT
	String  additionalClassificationAgencyName;		//MSRACA
	String  additionalClassificationCategoryCode; 	//MSRACC
	String  additionalClassificationCategoryDesc; 	//MSRACD
	String  functionalName;							//MSRFUN
	String  isDispatchUnit;							//MSRDSU (Y/N)
	String  isInvoiceUnit;							//MSRIVU (Y/N)
	String  isVariableUnit;							//MSRVRU (Y/N)
	String	isPackagingMarkedRecyclable;			//MSRPRY (Y/N)
	String  isPackagingMarkedReturnable;			//MSRPRT (Y/N)
	String  isPackagingMarkedWithExpirationDate;	//MSRPEX (Y/N)
	String  isPackagingMarkedWithGreenDot;			//MSRPGD (Y/N)
	String	isPackagingMarkedWithIngredients;		//MSRPIG (Y/N)
	String  countryOfOrigin;						//MSRCOR
	String	lastChangeDate;							//MSRLCD
	String	publicationDate;						//MSRPBD
	String	startAvailabilityDate;				 	//MSRPBD
	String	hasBatchNumber;							//MSRBTC (Y/N)
	String  isNonSoldReturnable;					//MSRRET (Y/N)
	String	isItemMarkedRecyclable;					//MSRRCY (Y/N)
	String  isNetContentDeclarationIndicated; 	 	//MSRNCI (Y/N)
	String  deliveryMethodIndicator;				//MSRDMI
	String  isBarcodeSymbologyDerivable;			//MSRBSD
	String  lastUpdateUser;			         	    //MSRUSR
	String  lastUpdateWorkstation; 					//MSRWSN
	String	lastUpdateDate;							//MSRDTE
	String	lastUpdateTime;    	   					//MSRTME
	String  subBrand;          						//MSRSBR
	String  variant;   				              	//MSRVAR
	String  couponFamilyCode;       				//MSRCFC
	String	qtyCompleteLayers;  					//MSRHI
	String  qtyItemsPerCompleteLayer;   			//MSRTI
	String	targetMarketCode;						//MSRTGC
	String	targetMarketSubdivCode;					//MSRMSB
	String	tradeItemEffectiveDate;					//MSRTED
	String	qtyChildrenUnits;						//MSRCLQ
	String	classificationCategoryDefinition;		//MSRCCD
	String	classificationCategoryName;				//MSRCCN
	Vector  comments	= new Vector(); // cast as GTINComments.
	Vector	urls		= new Vector(); // cast as KeyValue.	
	
	

	/**
	 * @return
	 */
	public String getAdditionalClassificationAgencyName() {
		return additionalClassificationAgencyName;
	}

	/**
	 * @return
	 */
	public String getAdditionalClassificationCategoryCode() {
		return additionalClassificationCategoryCode;
	}

	/**
	 * @return
	 */
	public String getAdditionalClassificationCategoryDesc() {
		return additionalClassificationCategoryDesc;
	}

	/**
	 * @return
	 */
	public String getAdditionalTradeItemDescription() {
		return additionalTradeItemDescription;
	}

	/**
	 * @return
	 */
	public String getCatalogItemState() {
		return catalogItemState;
	}

	/**
	 * @return
	 */
	public String getClassificationCategoryCode() {
		return classificationCategoryCode;
	}

	/**
	 * @return
	 */
	public String getClassificationCategoryDefinition() {
		return classificationCategoryDefinition;
	}

	/**
	 * @return
	 */
	public String getClassificationCategoryName() {
		return classificationCategoryName;
	}

	/**
	 * @return
	 */
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	/**
	 * @return
	 */
	public String getCouponFamilyCode() {
		return couponFamilyCode;
	}

	/**
	 * @return
	 */
	public String getDeliveryMethodIndicator() {
		return deliveryMethodIndicator;
	}

	/**
	 * @return
	 */
	public String getDepth() {
		return depth;
	}

	/**
	 * @return
	 */
	public String getEanUCCCode() {
		return eanUCCCode;
	}

	/**
	 * @return
	 */
	public String getEanUCCType() {
		return eanUCCType;
	}

	/**
	 * @return
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @return
	 */
	public String getFunctionalName() {
		return functionalName;
	}

	/**
	 * @return
	 */
	public String getGrossWeight() {
		return grossWeight;
	}

	/**
	 * @return
	 */
	public String getHasBatchNumber() {
		return hasBatchNumber;
	}

	/**
	 * @return
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @return
	 */
	public String getInformationProvider() {
		return informationProvider;
	}

	/**
	 * @return
	 */
	public String getIsBarcodeSymbologyDerivable() {
		return isBarcodeSymbologyDerivable;
	}

	/**
	 * @return
	 */
	public String getIsConsumerUnit() {
		return isConsumerUnit;
	}

	/**
	 * @return
	 */
	public String getIsDispatchUnit() {
		return isDispatchUnit;
	}

	/**
	 * @return
	 */
	public String getIsInformationPrivate() {
		return isInformationPrivate;
	}

	/**
	 * @return
	 */
	public String getIsInvoiceUnit() {
		return isInvoiceUnit;
	}

	/**
	 * @return
	 */
	public String getIsItemMarkedRecyclable() {
		return isItemMarkedRecyclable;
	}

	/**
	 * @return
	 */
	public String getIsNetContentDeclarationIndicated() {
		return isNetContentDeclarationIndicated;
	}

	/**
	 * @return
	 */
	public String getIsNonSoldReturnable() {
		return isNonSoldReturnable;
	}

	/**
	 * @return
	 */
	public String getIsPackagingMarkedRecyclable() {
		return isPackagingMarkedRecyclable;
	}

	/**
	 * @return
	 */
	public String getIsPackagingMarkedReturnable() {
		return isPackagingMarkedReturnable;
	}

	/**
	 * @return
	 */
	public String getIsPackagingMarkedWithExpirationDate() {
		return isPackagingMarkedWithExpirationDate;
	}

	/**
	 * @return
	 */
	public String getIsPackagingMarkedWithGreenDot() {
		return isPackagingMarkedWithGreenDot;
	}

	/**
	 * @return
	 */
	public String getIsPackagingMarkedWithIngredients() {
		return isPackagingMarkedWithIngredients;
	}

	/**
	 * @return
	 */
	public String getIsVariableUnit() {
		return isVariableUnit;
	}

	/**
	 * @return
	 */
	public String getLastChangeDate() {
		return lastChangeDate;
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
	public String getLastUpdateWorkstation() {
		return lastUpdateWorkstation;
	}

	/**
	 * @return
	 */
	public String getLinearUnitOfMeasure() {
		return linearUnitOfMeasure;
	}

	/**
	 * @return
	 */
	public String getNameOfInformationProvider() {
		return nameOfInformationProvider;
	}

	/**
	 * @return
	 */
	public String getNetContent() {
		return netContent;
	}

	/**
	 * @return
	 */
	public String getNetContentUnitOfMeasure() {
		return netContentUnitOfMeasure;
	}

	/**
	 * @return
	 */
	public String getNetWeight() {
		return netWeight;
	}

	/**
	 * @return
	 */
	public String getPublicationDate() {
		return publicationDate;
	}

	/**
	 * @return
	 */
	public String getQtyChildren() {
		return qtyChildren;
	}

	/**
	 * @return
	 */
	public String getQtyChildrenUnits() {
		return qtyChildrenUnits;
	}

	/**
	 * @return
	 */
	public String getQtyCompleteLayers() {
		return qtyCompleteLayers;
	}

	/**
	 * @return
	 */
	public String getQtyItemsPerCompleteLayer() {
		return qtyItemsPerCompleteLayer;
	}

	/**
	 * @return
	 */
	public String getQtyOfNextLowerLevelTradeItem() {
		return qtyOfNextLowerLevelTradeItem;
	}

	/**
	 * @return
	 */
	public String getStartAvailabilityDate() {
		return startAvailabilityDate;
	}

	/**
	 * @return
	 */
	public String getSubBrand() {
		return subBrand;
	}

	/**
	 * @return
	 */
	public String getTargetMarketCode() {
		return targetMarketCode;
	}

		/**
		 * @return
		 */
		public String getTargetMarketCountryCode() {
			return targetMarketCountryCode;
		}

	/**
	 * @return
	 */
	public String getTargetMarketSubdivCode() {
		return targetMarketSubdivCode;
	}

	/**
	 * @return
	 */
	public String getTradeItemEffectiveDate() {
		return tradeItemEffectiveDate;
	}

	/**
	 * @return
	 */
	public String getVariant() {
		return variant;
	}

	/**
	 * @return
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * @return
	 */
	public String getVolumeUnitOfMeasure() {
		return volumeUnitOfMeasure;
	}

	/**
	 * @return
	 */
	public String getWeightUnitOfMeasure() {
		return weightUnitOfMeasure;
	}

	/**
	 * @return
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param string
	 */
	public void setAdditionalClassificationAgencyName(String string) {
		additionalClassificationAgencyName = string;
	}

	/**
	 * @param string
	 */
	public void setAdditionalClassificationCategoryCode(String string) {
		additionalClassificationCategoryCode = string;
	}

	/**
	 * @param string
	 */
	public void setAdditionalClassificationCategoryDesc(String string) {
		additionalClassificationCategoryDesc = string;
	}

	/**
	 * @param string
	 */
	public void setAdditionalTradeItemDescription(String string) {
		additionalTradeItemDescription = string;
	}

	/**
	 * @param string
	 */
	public void setCatalogItemState(String string) {
		catalogItemState = string;
	}

	/**
	 * @param string
	 */
	public void setClassificationCategoryCode(String string) {
		classificationCategoryCode = string;
	}

	/**
	 * @param string
	 */
	public void setClassificationCategoryDefinition(String string) {
		classificationCategoryDefinition = string;
	}

	/**
	 * @param string
	 */
	public void setClassificationCategoryName(String string) {
		classificationCategoryName = string;
	}

	/**
	 * @param string
	 */
	public void setCountryOfOrigin(String string) {
		countryOfOrigin = string;
	}

	/**
	 * @param string
	 */
	public void setCouponFamilyCode(String string) {
		couponFamilyCode = string;
	}

	/**
	 * @param string
	 */
	public void setDeliveryMethodIndicator(String string) {
		deliveryMethodIndicator = string;
	}

	/**
	 * @param string
	 */
	public void setDepth(String string) {
		depth = string;
	}

	/**
	 * @param string
	 */
	public void setEanUCCCode(String string) {
		eanUCCCode = string;
	}

	/**
	 * @param string
	 */
	public void setEanUCCType(String string) {
		eanUCCType = string;
	}

	/**
	 * @param string
	 */
	public void setEffectiveDate(String string) {
		effectiveDate = string;
	}

	/**
	 * @param string
	 */
	public void setFunctionalName(String string) {
		functionalName = string;
	}

	/**
	 * @param string
	 */
	public void setGrossWeight(String string) {
		grossWeight = string;
	}

	/**
	 * @param string
	 */
	public void setHasBatchNumber(String string) {
		hasBatchNumber = string;
	}

	/**
	 * @param string
	 */
	public void setHeight(String string) {
		height = string;
	}

	/**
	 * @param string
	 */
	public void setInformationProvider(String string) {
		informationProvider = string;
	}

	/**
	 * @param string
	 */
	public void setIsBarcodeSymbologyDerivable(String string) {
		isBarcodeSymbologyDerivable = string;
	}

	/**
	 * @param string
	 */
	public void setIsConsumerUnit(String string) {
		isConsumerUnit = string;
	}

	/**
	 * @param string
	 */
	public void setIsDispatchUnit(String string) {
		isDispatchUnit = string;
	}

	/**
	 * @param string
	 */
	public void setIsInformationPrivate(String string) {
		isInformationPrivate = string;
	}

	/**
	 * @param string
	 */
	public void setIsInvoiceUnit(String string) {
		isInvoiceUnit = string;
	}

	/**
	 * @param string
	 */
	public void setIsItemMarkedRecyclable(String string) {
		isItemMarkedRecyclable = string;
	}

	/**
	 * @param string
	 */
	public void setIsNetContentDeclarationIndicated(String string) {
		isNetContentDeclarationIndicated = string;
	}

	/**
	 * @param string
	 */
	public void setIsNonSoldReturnable(String string) {
		isNonSoldReturnable = string;
	}

	/**
	 * @param string
	 */
	public void setIsPackagingMarkedRecyclable(String string) {
		isPackagingMarkedRecyclable = string;
	}

	/**
	 * @param string
	 */
	public void setIsPackagingMarkedReturnable(String string) {
		isPackagingMarkedReturnable = string;
	}

	/**
	 * @param string
	 */
	public void setIsPackagingMarkedWithExpirationDate(String string) {
		isPackagingMarkedWithExpirationDate = string;
	}

	/**
	 * @param string
	 */
	public void setIsPackagingMarkedWithGreenDot(String string) {
		isPackagingMarkedWithGreenDot = string;
	}

	/**
	 * @param string
	 */
	public void setIsPackagingMarkedWithIngredients(String string) {
		isPackagingMarkedWithIngredients = string;
	}

	/**
	 * @param string
	 */
	public void setIsVariableUnit(String string) {
		isVariableUnit = string;
	}

	/**
	 * @param string
	 */
	public void setLastChangeDate(String string) {
		lastChangeDate = string;
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
	public void setLastUpdateWorkstation(String string) {
		lastUpdateWorkstation = string;
	}

	/**
	 * @param string
	 */
	public void setLinearUnitOfMeasure(String string) {
		linearUnitOfMeasure = string;
	}

	/**
	 * @param string
	 */
	public void setNameOfInformationProvider(String string) {
		nameOfInformationProvider = string;
	}

	/**
	 * @param string
	 */
	public void setNetContent(String string) {
		netContent = string;
	}

	/**
	 * @param string
	 */
	public void setNetContentUnitOfMeasure(String string) {
		netContentUnitOfMeasure = string;
	}

	/**
	 * @param string
	 */
	public void setNetWeight(String string) {
		netWeight = string;
	}

	/**
	 * @param string
	 */
	public void setPublicationDate(String string) {
		publicationDate = string;
	}

	/**
	 * @param string
	 */
	public void setQtyChildren(String string) {
		qtyChildren = string;
	}

	/**
	 * @param string
	 */
	public void setQtyChildrenUnits(String string) {
		qtyChildrenUnits = string;
	}

	/**
	 * @param string
	 */
	public void setQtyCompleteLayers(String string) {
		qtyCompleteLayers = string;
	}

	/**
	 * @param string
	 */
	public void setQtyItemsPerCompleteLayer(String string) {
		qtyItemsPerCompleteLayer = string;
	}

	/**
	 * @param string
	 */
	public void setQtyOfNextLowerLevelTradeItem(String string) {
		qtyOfNextLowerLevelTradeItem = string;
	}

	/**
	 * @param string
	 */
	public void setStartAvailabilityDate(String string) {
		startAvailabilityDate = string;
	}

	/**
	 * @param string
	 */
	public void setSubBrand(String string) {
		subBrand = string;
	}

	/**
	 * @param string
	 */
	public void setTargetMarketCode(String string) {
		targetMarketCode = string;
	}

		/**
		 * @param string
		 */
		public void setTargetMarketCountryCode(String string) {
			targetMarketCountryCode = string;
		}

	/**
	 * @param string
	 */
	public void setTargetMarketSubdivCode(String string) {
		targetMarketSubdivCode = string;
	}

	/**
	 * @param string
	 */
	public void setTradeItemEffectiveDate(String string) {
		tradeItemEffectiveDate = string;
	}

	/**
	 * @param string
	 */
	public void setVariant(String string) {
		variant = string;
	}

	/**
	 * @param string
	 */
	public void setVolume(String string) {
		volume = string;
	}

	/**
	 * @param string
	 */
	public void setVolumeUnitOfMeasure(String string) {
		volumeUnitOfMeasure = string;
	}

	/**
	 * @param string
	 */
	public void setWeightUnitOfMeasure(String string) {
		weightUnitOfMeasure = string;
	}

	/**
	 * @param string
	 */
	public void setWidth(String string) {
		width = string;
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
	public Vector getUrls() {
		return urls;
	}

	/**
	 * @param vector
	 */
	public void setUrls(Vector vector) {
		urls = vector;
	}

}
