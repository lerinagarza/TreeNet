/*
 * Created on June 11, 2010 
 */

package com.treetop.businessobjects;

/**
 * @author deisen
 * 
 * Contains packaging instructions information - that ties directly to the Specification.
 *   11/16/11 TWalton - change to tie directly to the specification, change to use the Spec Header File	
 */
public class QaSpecificationPackaging extends QaSpecification {

							// from File 	dbprd/QAPGSPHD -- Spec Header
							//              dbprd/QAPMDESC	
							// 			 m3djdprd/MITMAS
							//			 m3djdprd/MITAUN
							//	         m3djdprd/MITPOP			
	
	protected	String		kosherStatus						= "";  //SHKSTS
	protected	String		kosherStatusDescription 			= "";
	protected	String		kosherSymbol						= "";  //SHSYMV
	protected	String		kosherSymbolRequired				= "";  //SHKSYM
	protected	String		inlineSockRequired					= "";  //SHINSK
	protected	String		inlineSockDescription				= "";
	protected	String		cipType								= "";  //SHCIPT
	protected	String		cipTypeDescription					= "";
	protected	String		testBrix							= "";
	protected	String		cutSizeCode							= "";  //SHCUTZ
	protected	String		cutSizeDescription					= "";
	protected	String		cutSizeCode2						= "";  //SHCUT2
	protected	String		cutSizeDescription2					= "";
	protected	String		screenSizeCode						= "";  //SHSRNZ
	protected	String		screenSizeDescription				= "";
	protected	String		foreignMaterialsDetectionCode		= "";  //SHFMAT
	protected	String		foreignMaterialsDetectionDescription = "";
	protected	String		storageRecommendation				= "";  //SHSREC
	protected	String		storageRecommendationDescription	= "";
	protected	String		m3StorageRecommendation				= ""; // MMSTCN -- this is found in the item master 
	protected	String		shelfLife							= "";	
	
	// Container / Label on the container, ect...
	protected	String		containerTamperSeal					= "";  //SHCTSL
	protected	String		containerCodeLocation				= "";  //SHCCDL
	protected	String		containerCodeLocationDescription	= "";
	protected	String		containerCodeFontSize				= "";  //SHCDFS
	protected	String		containerUPCNumber					= "";  //MPPOPN
	
	// Carton Information
	protected	String		cartonCodeLocation					= "";  //SHCCTL
	protected	String		cartonCodeLocationDescription		= "";
	protected	String		cartonCodeFontSize					= "";  //SHCTFS
	
	// Case / Labeling on the case
	protected	String		unitWeight							= "";   //MMGRWE
	protected	String		unitLength							= "";   //MSWDTH	
	protected	String		unitWidth							= "";	//MSWWTH
	protected	String		unitHeight							= "";	//MSWGWG
	protected	String		unitCube							= "";	//MMVOL3
	protected	String		unitUPCNumber						= "";	//MPPOPN
	protected	String		unitCodeFontSize					= "";   //SHCSFS
	protected	String 		unitShowBarCode						= "";   //SHCSBC
	
	// Pallet Information 
	protected	String		m3UnitsPerPallet					= "";	//MUCOFA
	protected	String		m3UnitsPerLayer						= "";	//MUCOFA
	protected	String		m3LayersPerPallet					= "";	//m3UnitsPerPallet / m3UnitsPerLayer	
	protected	String		unitsPerPallet						= "";	//SHUNTP
	protected	String		unitsPerLayer						= "";	//SHUNTL
	protected	String		layersPerPallet						= "";	//unitsPerPallet / unitsPerLayer	
	protected	String		palletStacking						= "";	//MMFRAG
	protected	String		palletHeight						= "";	//unitHeight * layersPerPallet + pallet frame height
	protected	String		palletGTINNumber					= "";	//MPPOPN
	protected	String		palletLabelType						= "";   //SHPLBT
	protected	String		palletLabelTypeDescription			= "";
	protected	String		palletLabelLocation					= "";   //SHPLBL
	protected	String		palletLabelLocationDescription		= "";
	
	protected	String		stretchWrapRequired					= "";   //SHWREQ
	protected	String 		stretchWrapType						= "";   //SHWTYP
	protected	String		stretchWrapTypeDescription			= "";
	protected	String		stretchWrapWidth					= "";	//SHWWDH
	protected	String		stretchWrapWidthUOM					= "";
	protected	String		stretchWrapWidthUOMDescription		= "";
	protected	String		stretchWrapGauge					= "";   //SHWGAU
	protected	String		stretchWrapGaugeUOM					= "";
	protected	String		stretchWrapGaugeUOMDescription		= "";
	protected	String		shrinkWrapRequired					= "";   //SHKREQ
	protected	String		shrinkWrapType						= "";   //SHKTYP
	protected	String		shrinkWrapTypeDescription			= "";
	protected	String		shrinkWrapWidth						= ""; 	//SHKWDH
	protected	String		shrinkWrapWidthUOM					= "";
	protected	String		shrinkWrapWidthUOMDescription		= "";
	protected	String		shrinkWrapThickness					= "";   //SHKTHK
	protected	String		shrinkWrapThicknessUOM				= "";
	protected	String		shrinkWrapThicknessUOMDescription	= "";
	protected	String		slipSheetRequired					= "";   //SHSREQ
	protected	String		slipSheetBottom						= "";   //SHSLOC 
	protected	String		slipSheetLayer1						= "";
	protected	String		slipSheetLayer2						= "";
	protected	String		slipSheetLayer3						= "";
	protected	String		slipSheetLayer4						= "";
	protected	String		slipSheetLayer5						= "";
	protected	String		slipSheetLayer6						= "";
	protected	String		slipSheetLayer7						= "";
	protected	String		slipSheetLayer8						= "";
	protected	String		slipSheetLayer9						= "";
	protected	String		slipSheetLayer10					= "";
	protected	String 		slipSheetTop						= "";
	protected	String		palletRequirement					= "";	//SHPREQ
	protected	String		palletRequirementDescription		= "";
		
	/**
	 *  // Constructor
	 */
	public QaSpecificationPackaging() {
		super();
	}

	/**
	 * @return Returns the product unit and pallet information (Weight).
	 */
	public String getUnitWeight() {
		return unitWeight;
	}
	/**
	 * @param Sets the product unit and pallet information (Weight).
	 */
	public void setUnitWeight(String unitWeight) {
		this.unitWeight = unitWeight;
	}	
	/**
	 * @return Returns the product unit and pallet information (Length).
	 */
	public String getUnitLength() {
		return unitLength;
	}
	/**
	 * @param Sets the product unit and pallet information (Length).
	 */
	public void setUnitLength(String unitLength) {
		this.unitLength = unitLength;
	}
	/**
	 * @return Returns the product unit and pallet information (Width).
	 */
	public String getUnitWidth() {
		return unitWidth;
	}
	/**
	 * @param Sets the product unit and pallet information (Width).
	 */
	public void setUnitWidth(String unitWidth) {
		this.unitWidth = unitWidth;
	}
	/**
	 * @return Returns the product unit and pallet information (Height).
	 */
	public String getUnitHeight() {
		return unitHeight;
	}
	/**
	 * @param Sets the product unit and pallet information (Height).
	 */
	public void setUnitHeight(String unitHeight) {
		this.unitHeight = unitHeight;
	}
	/**
	 * @return Returns the product unit and pallet information (Cube).
	 */
	public String getUnitCube() {
		return unitCube;
	}
	/**
	 * @param Sets the product unit and pallet information (Cube).
	 */
	public void setUnitCube(String unitCube) {
		this.unitCube = unitCube;
	}
	/**
	 * @return Returns the product unit and pallet information (Units per Pallet).
	 */
	public String getUnitsPerPallet() {
		return unitsPerPallet;
	}
	/**
	 * @param Sets the product unit and pallet information (Units per Pallet).
	 */
	public void setUnitsPerPallet(String unitsPerPallet) {
		this.unitsPerPallet = unitsPerPallet;
	}
	/**
	 * @return Returns the product unit and pallet information (Units per Layer).
	 */
	public String getUnitsPerLayer() {
		return unitsPerLayer;
	}
	/**
	 * @param Sets the product unit and pallet information (Units per Layer).
	 */
	public void setUnitsPerLayer(String unitsPerLayer) {
		this.unitsPerLayer = unitsPerLayer;
	}
	/**
	 * @return Returns the product unit and pallet information (Layers per Pallet).
	 */
	public String getLayersPerPallet() {
		return layersPerPallet;
	}
	/**
	 * @param Sets the product unit and pallet information (Layers per Pallet).
	 */
	public void setLayersPerPallet(String layersPerPallet) {
		this.layersPerPallet = layersPerPallet;
	}
	/**
	 * @return Returns the product unit and pallet information (Pallet Stacking).
	 */
	public String getPalletStacking() {
		return palletStacking;
	}
	/**
	 * @param Sets the product unit and pallet information Pallet Stacking).
	 */
	public void setPalletStacking(String palletStacking) {
		this.palletStacking = palletStacking;
	}
	/**
	 * @return Returns the product unit and pallet information (Pallet Height).
	 */
	public String getPalletHeight() {
		return palletHeight;
	}
	/**
	 * @param Sets the product unit and pallet information (Pallet Height).
	 */
	public void setPalletHeight(String palletHeight) {
		this.palletHeight = palletHeight;
	}
	/**
	 * @return Returns the special packaging requirements (UPC Numbering).
	 */
	public String getUnitUPCNumber() {
		return unitUPCNumber;
	}
	/**
	 * @param Sets the special packaging requirements (UPC Numbering).
	 */
	public void setUnitUPCNumber(String unitUPCNumber) {
		this.unitUPCNumber = unitUPCNumber;
	}
	/**
	 * @return Returns the special packaging requirements (Pallet GTIN).
	 */
	public String getPalletGTINNumber() {
		return palletGTINNumber;
	}
	/**
	 * @param Sets the special packaging requirements (Pallet GTIN).
	 */
	public void setPalletGTINNumber(String palletGTINNumber) {
		this.palletGTINNumber = palletGTINNumber;
	}
	/**
	 * @return Returns the special packaging requirements (UPC Numbering).
	 */
	public String getShelfLife() {
		return shelfLife;
	}
	/**
	 * @param Sets the special packaging requirements (UPC Numbering).
	 */
	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}

	public String getCipType() {
		return cipType;
	}

	public void setCipType(String cipType) {
		this.cipType = cipType;
	}

	public String getCipTypeDescription() {
		return cipTypeDescription;
	}

	public void setCipTypeDescription(String cipTypeDescription) {
		this.cipTypeDescription = cipTypeDescription;
	}

	public String getContainerCodeFontSize() {
		return containerCodeFontSize;
	}

	public void setContainerCodeFontSize(String containerCodeFontSize) {
		this.containerCodeFontSize = containerCodeFontSize;
	}

	public String getContainerCodeLocation() {
		return containerCodeLocation;
	}

	public void setContainerCodeLocation(String containerCodeLocation) {
		this.containerCodeLocation = containerCodeLocation;
	}

	public String getContainerCodeLocationDescription() {
		return containerCodeLocationDescription;
	}

	public void setContainerCodeLocationDescription(
			String containerCodeLocationDescription) {
		this.containerCodeLocationDescription = containerCodeLocationDescription;
	}

	public String getContainerTamperSeal() {
		return containerTamperSeal;
	}

	public void setContainerTamperSeal(String containerTamperSeal) {
		this.containerTamperSeal = containerTamperSeal;
	}

	public String getContainerUPCNumber() {
		return containerUPCNumber;
	}

	public void setContainerUPCNumber(String containerUPCNumber) {
		this.containerUPCNumber = containerUPCNumber;
	}

	public String getInlineSockDescription() {
		return inlineSockDescription;
	}

	public void setInlineSockDescription(String inlineSockDescription) {
		this.inlineSockDescription = inlineSockDescription;
	}

	public String getInlineSockRequired() {
		return inlineSockRequired;
	}

	public void setInlineSockRequired(String inlineSockRequired) {
		this.inlineSockRequired = inlineSockRequired;
	}

	public String getKosherStatus() {
		return kosherStatus;
	}

	public void setKosherStatus(String kosherStatus) {
		this.kosherStatus = kosherStatus;
	}

	public String getKosherStatusDescription() {
		return kosherStatusDescription;
	}

	public void setKosherStatusDescription(String kosherStatusDescription) {
		this.kosherStatusDescription = kosherStatusDescription;
	}

	public String getKosherSymbolRequired() {
		return kosherSymbolRequired;
	}

	public void setKosherSymbolRequired(String kosherSymbolRequired) {
		this.kosherSymbolRequired = kosherSymbolRequired;
	}

	public String getPalletLabelLocation() {
		return palletLabelLocation;
	}

	public void setPalletLabelLocation(String palletLabelLocation) {
		this.palletLabelLocation = palletLabelLocation;
	}

	public String getPalletLabelLocationDescription() {
		return palletLabelLocationDescription;
	}

	public void setPalletLabelLocationDescription(
			String palletLabelLocationDescription) {
		this.palletLabelLocationDescription = palletLabelLocationDescription;
	}

	public String getPalletLabelType() {
		return palletLabelType;
	}

	public void setPalletLabelType(String palletLabelType) {
		this.palletLabelType = palletLabelType;
	}

	public String getPalletLabelTypeDescription() {
		return palletLabelTypeDescription;
	}

	public void setPalletLabelTypeDescription(String palletLabelTypeDescription) {
		this.palletLabelTypeDescription = palletLabelTypeDescription;
	}

	public String getPalletRequirement() {
		return palletRequirement;
	}

	public void setPalletRequirement(String palletRequirement) {
		this.palletRequirement = palletRequirement;
	}

	public String getPalletRequirementDescription() {
		return palletRequirementDescription;
	}

	public void setPalletRequirementDescription(String palletRequirementDescription) {
		this.palletRequirementDescription = palletRequirementDescription;
	}

	public String getShrinkWrapRequired() {
		return shrinkWrapRequired;
	}

	public void setShrinkWrapRequired(String shrinkWrapRequired) {
		this.shrinkWrapRequired = shrinkWrapRequired;
	}

	public String getShrinkWrapThickness() {
		return shrinkWrapThickness;
	}

	public void setShrinkWrapThickness(String shrinkWrapThickness) {
		this.shrinkWrapThickness = shrinkWrapThickness;
	}

	public String getShrinkWrapType() {
		return shrinkWrapType;
	}

	public void setShrinkWrapType(String shrinkWrapType) {
		this.shrinkWrapType = shrinkWrapType;
	}

	public String getShrinkWrapTypeDescription() {
		return shrinkWrapTypeDescription;
	}

	public void setShrinkWrapTypeDescription(String shrinkWrapTypeDescription) {
		this.shrinkWrapTypeDescription = shrinkWrapTypeDescription;
	}

	public String getShrinkWrapWidth() {
		return shrinkWrapWidth;
	}

	public void setShrinkWrapWidth(String shrinkWrapWidth) {
		this.shrinkWrapWidth = shrinkWrapWidth;
	}

	public String getSlipSheetRequired() {
		return slipSheetRequired;
	}

	public void setSlipSheetRequired(String slipSheetRequired) {
		this.slipSheetRequired = slipSheetRequired;
	}

	public String getStretchWrapGauge() {
		return stretchWrapGauge;
	}

	public void setStretchWrapGauge(String stretchWrapGauge) {
		this.stretchWrapGauge = stretchWrapGauge;
	}

	public String getStretchWrapRequired() {
		return stretchWrapRequired;
	}

	public void setStretchWrapRequired(String stretchWrapRequired) {
		this.stretchWrapRequired = stretchWrapRequired;
	}

	public String getStretchWrapType() {
		return stretchWrapType;
	}

	public void setStretchWrapType(String stretchWrapType) {
		this.stretchWrapType = stretchWrapType;
	}

	public String getStretchWrapTypeDescription() {
		return stretchWrapTypeDescription;
	}

	public void setStretchWrapTypeDescription(String stretchWrapTypeDescription) {
		this.stretchWrapTypeDescription = stretchWrapTypeDescription;
	}

	public String getStretchWrapWidth() {
		return stretchWrapWidth;
	}

	public void setStretchWrapWidth(String stretchWrapWidth) {
		this.stretchWrapWidth = stretchWrapWidth;
	}

	public String getUnitCodeFontSize() {
		return unitCodeFontSize;
	}

	public void setUnitCodeFontSize(String unitCodeFontSize) {
		this.unitCodeFontSize = unitCodeFontSize;
	}

	public String getUnitShowBarCode() {
		return unitShowBarCode;
	}

	public void setUnitShowBarCode(String unitShowBarCode) {
		this.unitShowBarCode = unitShowBarCode;
	}

	public String getStorageRecommendation() {
		return storageRecommendation;
	}

	public void setStorageRecommendation(String storageRecommendation) {
		this.storageRecommendation = storageRecommendation;
	}

	public String getStorageRecommendationDescription() {
		return storageRecommendationDescription;
	}

	public void setStorageRecommendationDescription(
			String storageRecommendationDescription) {
		this.storageRecommendationDescription = storageRecommendationDescription;
	}

	public String getTestBrix() {
		return testBrix;
	}

	public void setTestBrix(String testBrix) {
		this.testBrix = testBrix;
	}

	public String getKosherSymbol() {
		return kosherSymbol;
	}

	public void setKosherSymbol(String kosherSymbol) {
		this.kosherSymbol = kosherSymbol;
	}

	public String getSlipSheetBottom() {
		return slipSheetBottom;
	}

	public void setSlipSheetBottom(String slipSheetBottom) {
		this.slipSheetBottom = slipSheetBottom;
	}

	public String getSlipSheetLayer1() {
		return slipSheetLayer1;
	}

	public void setSlipSheetLayer1(String slipSheetLayer1) {
		this.slipSheetLayer1 = slipSheetLayer1;
	}

	public String getSlipSheetLayer2() {
		return slipSheetLayer2;
	}

	public void setSlipSheetLayer2(String slipSheetLayer2) {
		this.slipSheetLayer2 = slipSheetLayer2;
	}

	public String getSlipSheetLayer3() {
		return slipSheetLayer3;
	}

	public void setSlipSheetLayer3(String slipSheetLayer3) {
		this.slipSheetLayer3 = slipSheetLayer3;
	}

	public String getSlipSheetLayer4() {
		return slipSheetLayer4;
	}

	public void setSlipSheetLayer4(String slipSheetLayer4) {
		this.slipSheetLayer4 = slipSheetLayer4;
	}

	public String getSlipSheetLayer5() {
		return slipSheetLayer5;
	}

	public void setSlipSheetLayer5(String slipSheetLayer5) {
		this.slipSheetLayer5 = slipSheetLayer5;
	}

	public String getSlipSheetLayer6() {
		return slipSheetLayer6;
	}

	public void setSlipSheetLayer6(String slipSheetLayer6) {
		this.slipSheetLayer6 = slipSheetLayer6;
	}

	public String getSlipSheetTop() {
		return slipSheetTop;
	}

	public void setSlipSheetTop(String slipSheetTop) {
		this.slipSheetTop = slipSheetTop;
	}

	public String getM3StorageRecommendation() {
		return m3StorageRecommendation;
	}

	public void setM3StorageRecommendation(String m3StorageRecommendation) {
		this.m3StorageRecommendation = m3StorageRecommendation;
	}

	public String getStretchWrapWidthUOM() {
		return stretchWrapWidthUOM;
	}

	public void setStretchWrapWidthUOM(String stretchWrapWidthUOM) {
		this.stretchWrapWidthUOM = stretchWrapWidthUOM;
	}

	public String getStretchWrapWidthUOMDescription() {
		return stretchWrapWidthUOMDescription;
	}

	public void setStretchWrapWidthUOMDescription(
			String stretchWrapWidthUOMDescription) {
		this.stretchWrapWidthUOMDescription = stretchWrapWidthUOMDescription;
	}

	public String getStretchWrapGaugeUOM() {
		return stretchWrapGaugeUOM;
	}

	public void setStretchWrapGaugeUOM(String stretchWrapGaugeUOM) {
		this.stretchWrapGaugeUOM = stretchWrapGaugeUOM;
	}

	public String getStretchWrapGaugeUOMDescription() {
		return stretchWrapGaugeUOMDescription;
	}

	public void setStretchWrapGaugeUOMDescription(
			String stretchWrapGaugeUOMDescription) {
		this.stretchWrapGaugeUOMDescription = stretchWrapGaugeUOMDescription;
	}

	public String getShrinkWrapWidthUOM() {
		return shrinkWrapWidthUOM;
	}

	public void setShrinkWrapWidthUOM(String shrinkWrapWidthUOM) {
		this.shrinkWrapWidthUOM = shrinkWrapWidthUOM;
	}

	public String getShrinkWrapWidthUOMDescription() {
		return shrinkWrapWidthUOMDescription;
	}

	public void setShrinkWrapWidthUOMDescription(
			String shrinkWrapWidthUOMDescription) {
		this.shrinkWrapWidthUOMDescription = shrinkWrapWidthUOMDescription;
	}

	public String getShrinkWrapThicknessUOM() {
		return shrinkWrapThicknessUOM;
	}

	public void setShrinkWrapThicknessUOM(String shrinkWrapThicknessUOM) {
		this.shrinkWrapThicknessUOM = shrinkWrapThicknessUOM;
	}

	public String getShrinkWrapThicknessUOMDescription() {
		return shrinkWrapThicknessUOMDescription;
	}

	public void setShrinkWrapThicknessUOMDescription(
			String shrinkWrapThicknessUOMDescription) {
		this.shrinkWrapThicknessUOMDescription = shrinkWrapThicknessUOMDescription;
	}

	public String getM3UnitsPerPallet() {
		return m3UnitsPerPallet;
	}

	public void setM3UnitsPerPallet(String m3UnitsPerPallet) {
		this.m3UnitsPerPallet = m3UnitsPerPallet;
	}

	public String getM3UnitsPerLayer() {
		return m3UnitsPerLayer;
	}

	public void setM3UnitsPerLayer(String m3UnitsPerLayer) {
		this.m3UnitsPerLayer = m3UnitsPerLayer;
	}

	public String getM3LayersPerPallet() {
		return m3LayersPerPallet;
	}

	public void setM3LayersPerPallet(String m3LayersPerPallet) {
		this.m3LayersPerPallet = m3LayersPerPallet;
	}

	public String getCutSizeCode() {
		return cutSizeCode;
	}

	public void setCutSizeCode(String cutSizeCode) {
		this.cutSizeCode = cutSizeCode;
	}

	public String getCutSizeDescription() {
		return cutSizeDescription;
	}

	public void setCutSizeDescription(String cutSizeDescription) {
		this.cutSizeDescription = cutSizeDescription;
	}

	public String getScreenSizeCode() {
		return screenSizeCode;
	}

	public void setScreenSizeCode(String screenSizeCode) {
		this.screenSizeCode = screenSizeCode;
	}

	public String getScreenSizeDescription() {
		return screenSizeDescription;
	}

	public void setScreenSizeDescription(String screenSizeDescription) {
		this.screenSizeDescription = screenSizeDescription;
	}

	public String getForeignMaterialsDetectionCode() {
		return foreignMaterialsDetectionCode;
	}

	public void setForeignMaterialsDetectionCode(
			String foreignMaterialsDetectionCode) {
		this.foreignMaterialsDetectionCode = foreignMaterialsDetectionCode;
	}

	public String getForeignMaterialsDetectionDescription() {
		return foreignMaterialsDetectionDescription;
	}

	public void setForeignMaterialsDetectionDescription(
			String foreignMaterialsDetectionDescription) {
		this.foreignMaterialsDetectionDescription = foreignMaterialsDetectionDescription;
	}

	public String getCutSizeCode2() {
		return cutSizeCode2;
	}

	public void setCutSizeCode2(String cutSizeCode2) {
		this.cutSizeCode2 = cutSizeCode2;
	}

	public String getCutSizeDescription2() {
		return cutSizeDescription2;
	}

	public void setCutSizeDescription2(String cutSizeDescription2) {
		this.cutSizeDescription2 = cutSizeDescription2;
	}

	public String getCartonCodeLocation() {
		return cartonCodeLocation;
	}

	public void setCartonCodeLocation(String cartonCodeLocation) {
		this.cartonCodeLocation = cartonCodeLocation;
	}

	public String getCartonCodeLocationDescription() {
		return cartonCodeLocationDescription;
	}

	public void setCartonCodeLocationDescription(
			String cartonCodeLocationDescription) {
		this.cartonCodeLocationDescription = cartonCodeLocationDescription;
	}

	public String getCartonCodeFontSize() {
		return cartonCodeFontSize;
	}

	public void setCartonCodeFontSize(String cartonCodeFontSize) {
		this.cartonCodeFontSize = cartonCodeFontSize;
	}

	public String getSlipSheetLayer7() {
		return slipSheetLayer7;
	}

	public void setSlipSheetLayer7(String slipSheetLayer7) {
		this.slipSheetLayer7 = slipSheetLayer7;
	}

	public String getSlipSheetLayer8() {
		return slipSheetLayer8;
	}

	public void setSlipSheetLayer8(String slipSheetLayer8) {
		this.slipSheetLayer8 = slipSheetLayer8;
	}

	public String getSlipSheetLayer9() {
		return slipSheetLayer9;
	}

	public void setSlipSheetLayer9(String slipSheetLayer9) {
		this.slipSheetLayer9 = slipSheetLayer9;
	}

	public String getSlipSheetLayer10() {
		return slipSheetLayer10;
	}

	public void setSlipSheetLayer10(String slipSheetLayer10) {
		this.slipSheetLayer10 = slipSheetLayer10;
	}
	
}