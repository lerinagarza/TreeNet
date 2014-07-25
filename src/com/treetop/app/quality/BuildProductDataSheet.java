/*
 * Created on December 3, 2013
 * 
 */

package com.treetop.app.quality;

import java.util.Vector;

import com.treetop.businessobjectapplications.BeanQuality;
import com.treetop.controller.BaseViewBeanR4;

/**
 * @author twalto
 * 
 */
@SuppressWarnings("rawtypes")
public class BuildProductDataSheet extends BaseViewBeanR4 {
	
	public String submit						= ""; // Update of Specification
	
	// will only need to look for the spec number, because it will only bring up Active Spec information
	public String specNumber 					= "";
	public String revisionDate 					= "";
	public String revisionTime					= "";
	
	public String showRevisionDate 				= "";
	public String showSpecNumber				= "";
	
	// choose which Background Graphic should be used for this page
	public String graphicChosen					= "";
	
	// Fields Available to select and be displayed in a Product Data Sheet
	// on Screen as a text box to select for each piece
	public String specType 						= "";
	public String specTitle						= "";
	public String kosherSymbol					= "";
	public String kosherSymbolRequired			= "";
	public String countryOfOrigin				= "";
	public String inlineSock					= "";
	public String cipType						= "";
	public String cutSize						= "";
	public String screenSize					= "";
	public String foreignMaterialDetection		= "";	
	public String testBrix						= ""; 
	public String reconstitutionRatio			= "";
	public String revisionReason				= "";
	public String shelfLife						= "";
	public String storageRecommendation			= "";
	
	public String containerCodeLocation			= "";
	public String containerCodeFontSize			= "";
	
	public String caseShowBarCode				= "";
	public String caseCodeFontSize				= "";
	
	public String palletLabelType				= "";
	public String palletLabelLocation			= "";
	
	public String cartonCodeLocation			= "";
	public String cartonCodeFontSize			= "";
	
	public String palletRequirement				= "";
	
	
	// - Will allow to choose pieces from the original list, and/or the entire list
//	review if it should be updtestparameters, or not... don't think so
// decide if we need a new buildtestparameters,  to track just the small information required

	public Vector<BuildProductDataSheetAttributes> listAnalyticalTests 			= new Vector<BuildProductDataSheetAttributes>();
	public String countAnalyticalTests 											= "0";
	public Vector<BuildProductDataSheetAttributes> listMicroTests 				= new Vector<BuildProductDataSheetAttributes>();
	public String countMicroTests 												= "0";
	public Vector<BuildProductDataSheetAttributes> listProcessParameters 		= new Vector<BuildProductDataSheetAttributes>(); 
	public String countProcessParameters 										= "0";
	public Vector<BuildProductDataSheetAttributes> listAdditivesAndPreservatives= new Vector<BuildProductDataSheetAttributes>(); 
	public String countAdditivesAndPreservatives								= "0";	
	
	//Screen to read - Approved Fruit Varieties x,y,z
	public String varietiesIncluded				= "";
	//Screen to read - Excluded Fruit Varieties a,b,c
	public String varietiesExcluded 			= "";	
	
	// will be stored in the comment file with other URLS
	// -- Need to figure out how this may look, Show actual Image, or link to image?
	//   if Link to image, may have some security issues
	//public String nutritionPanel				= ""; //SpecRevisionUrl1
	//public String palletPattern					= ""; //SpecRevisionUrl2
	//public String exampleLabel					= ""; //SpecRevisionUrl3 

	// Comment sections - select to display all of the comment section
	public Vector listComments 							= new Vector(); // revision information
	public String[] linesComments						= new String[] {};
	
	public Vector listAnalyticalTestComments			= new Vector(); // comment1
	public String[] linesAnalyticalTestComments			= new String[] {};
	
	public Vector listProcessParameterComments			= new Vector(); // comment2
	public String[] linesProcessParameterComments		= new String[] {};
	
	public Vector listMicroTestComments					= new Vector(); // comment3
	public String[] linesMicroTestComments			= new String[] {};
	
	public Vector listAdditivesAndPreservativeComments		= new Vector(); // comment4
	public String[] linesAdditivesAndPreservativeComments	= new String[] {};
	
	public Vector listContainerPrintByLine				= new Vector(); // comment5
	public String[] linesContainerPrintByLine			= new String[] {};
	
	public Vector listContainerPrintAdditional			= new Vector(); // comment6
	public String[] linesContainerPrintAdditional		= new String[] {};
	
	public Vector listCasePrintByLine					= new Vector(); // comment7
	public String[] linesCasePrintByLine				= new String[] {};
	
	public Vector listCasePrintAdditional				= new Vector(); // comment8
	public String[] linesCasePrintAdditional			= new String[] {};
	
	public Vector listPalletPrintByLine					= new Vector(); // comment9
	public String[] linesPalletPrintByLine				= new String[] {};
	
	public Vector listPalletPrintAdditional				= new Vector(); // comment10
	public String[] linesPalletPrintAdditional			= new String[] {};
	
	public Vector listLabelPrintByLine					= new Vector(); // comment11
	public String[] linesLabelPrintByLine				= new String[] {};
	
	public Vector listLabelPrintAdditional				= new Vector(); // comment12
	public String[] linesLabelPrintAdditional			= new String[] {};
	
	public Vector listShelfLifeRequirements				= new Vector(); // comment13
	public String[] linesShelfLifeRequirements			= new String[] {};
	
	public Vector listStorageRequirements				= new Vector(); // comment14
	public String[] linesStorageRequirements			= new String[] {};
	
	public Vector listFinishedPalletAdditional			= new Vector(); // comment15
	public String[] linesFinishedPalletAdditional		= new String[] {};
	
	public Vector listFruitVarietiesAdditional			= new Vector(); // comment16
	public String[] linesFruitVarietiesAdditional		= new String[] {};
	
	public Vector listShippingRequirements				= new Vector(); // comment17
	public String[] linesShippingRequirements			= new String[] {};
	
	public Vector listCOARequirements					= new Vector(); // comment18
	public String[] linesCOARequirements				= new String[] {};
	
	public Vector listCartonPrintByLine					= new Vector(); // comment19
	public String[] linesCartonPrintByLine				= new String[] {};
	
	public Vector listCartonPrintAdditional				= new Vector(); // comment20
	public String[] linesCartonPrintAdditional			= new String[] {};
	
	public Vector listProductDescription				= new Vector(); // comment21
	public String[] linesProductDescription				= new String[] {};
	
	public Vector listIngredientStatement				= new Vector(); // comment22
	public String[] linesIngredientStatement			= new String[] {};
	
	public Vector listIntendedUse						= new Vector(); // comment23
	public String[] linesIntendedUse					= new String[] {};
	
	public Vector listForeignMatter						= new Vector(); // comment24
	public String[] linesForeignMatter					= new String[] {};
	
	public Vector listCodingRequirementsAdditional		= new Vector(); // comment25
	public String[] linesCodingRequirementsAdditional	= new String[] {};
	
	public Vector listStatements 						= new Vector(); // QualityStatement
	public String[] linesStatements					= new String[] {};
	
	public BeanQuality dtlBean 							= new BeanQuality();
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		return;
	}

	public boolean showSection(String sectionName) {
		boolean showSection = false;
		
		Vector<BuildProductDataSheetAttributes> attributes = null;
		StringBuffer select = new StringBuffer();
		
		if (sectionName.equals("DEF")) {
			select.append(this.getSpecType());
			select.append(this.getSpecTitle());
			select.append(this.getKosherSymbol());
			select.append(this.getCountryOfOrigin());
			select.append(this.getInlineSock());
			select.append(this.getCipType());
			select.append(this.getCutSize());
			select.append(this.getScreenSize());
			select.append(this.getForeignMaterialDetection());
			select.append(this.getReconstitutionRatio());
			select.append(this.getTestBrix());
			
		} else if (sectionName.equals("PRODDESC")) {
			if (this.getLinesProductDescription().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("INGSTMT")) {
			if (this.getLinesIngredientStatement().length > 0) {
				select.append("Y");
			}
		
		} else if (sectionName.equals("FINPALLET")) {
			if (this.getLinesFinishedPalletAdditional().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("TEST")) {
			attributes = this.getListAnalyticalTests();
			if (this.getLinesAnalyticalTestComments().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("MICRO")) {
			attributes = this.getListMicroTests();
			if (this.getLinesMicroTestComments().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("PROC")) {
			attributes = this.getListProcessParameters();
			if (this.getLinesProcessParameterComments().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("ADD")) {
			attributes = this.getListAdditivesAndPreservatives();
			if (this.getLinesProcessParameterComments().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("VAR")) {
			select.append(this.getVarietiesIncluded());
			select.append(this.getVarietiesExcluded());
			if (this.getLinesFruitVarietiesAdditional().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("USE")) {
			if (this.getLinesIntendedUse().length > 0) {
				select.append("Y");
			}
		
		} else if (sectionName.equals("FOREIGN")) {
			if (this.getLinesForeignMatter().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("SHELF")) {
			select.append(this.getShelfLife());
			if (this.getLinesShelfLifeRequirements().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("STORAGE")) {
			select.append(this.getStorageRecommendation());
			if (this.getLinesStorageRequirements().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("CONTAINER")) {
			select.append(this.getContainerCodeLocation());
			select.append(this.getContainerCodeFontSize());
			
			if (this.getLinesContainerPrintByLine().length > 0) {
				select.append("Y");
			}
			if (this.getLinesContainerPrintAdditional().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("CASE")) {
			select.append(this.getCaseShowBarCode());
			select.append(this.getCaseCodeFontSize());
			
			if (this.getLinesCasePrintByLine().length > 0) {
				select.append("Y");
			}
			if (this.getLinesCasePrintAdditional().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("LABEL")) {
			select.append(this.getKosherSymbolRequired());
			
			if (this.getLinesLabelPrintByLine().length > 0) {
				select.append("Y");
			}
			if (this.getLinesLabelPrintAdditional().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("PALLET")) {
			select.append(this.getPalletLabelType());
			select.append(this.getPalletLabelLocation());
			
			if (this.getLinesPalletPrintByLine().length > 0) {
				select.append("Y");
			}
			if (this.getLinesPalletPrintAdditional().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("ADDCODING")) {
			if (this.getLinesCodingRequirementsAdditional().length > 0) {
				select.append("Y");
			}
				
		} else if (sectionName.equals("SHIPPING")) {
			if (this.getLinesShippingRequirements().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("COA")) {
			if (this.getLinesCOARequirements().length > 0) {
				select.append("Y");
			}
			
		} else if (sectionName.equals("PALLETREQ")) {
			select.append(this.getPalletRequirement());
			
		} else if (sectionName.equals("STATEMENTS")) {
			if (this.getLinesStatements().length > 0) {
				select.append("Y");
			}
			
		}	
		
		
		
		
		if (attributes != null) {
			for (BuildProductDataSheetAttributes attribute : attributes) {
				if (!attribute.getSelectAttribute().equals("")) {
					showSection = true;
					break;
				}
			}
		}
		
		
		if (!select.toString().equals("")) {
			showSection = true;
		}
		
		
	
		
		return showSection;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getSpecNumber() {
		return specNumber;
	}

	public void setSpecNumber(String specNumber) {
		this.specNumber = specNumber;
	}

	public String getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getRevisionTime() {
		return revisionTime;
	}

	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}

	public String getShowRevisionDate() {
		return showRevisionDate;
	}

	public void setShowRevisionDate(String showRevisionDate) {
		this.showRevisionDate = showRevisionDate;
	}

	public String getShowSpecNumber() {
		return showSpecNumber;
	}

	public void setShowSpecNumber(String showSpecNumber) {
		this.showSpecNumber = showSpecNumber;
	}

	public String getGraphicChosen() {
		return graphicChosen;
	}

	public void setGraphicChosen(String graphicChosen) {
		this.graphicChosen = graphicChosen;
	}

	public String getSpecType() {
		return specType;
	}

	public void setSpecType(String specType) {
		this.specType = specType;
	}

	public String getSpecTitle() {
		return specTitle;
	}

	public void setSpecTitle(String specTitle) {
		this.specTitle = specTitle;
	}

	public String getKosherSymbol() {
		return kosherSymbol;
	}

	public void setKosherSymbol(String kosherSymbol) {
		this.kosherSymbol = kosherSymbol;
	}

	public String getKosherSymbolRequired() {
		return kosherSymbolRequired;
	}

	public void setKosherSymbolRequired(String kosherSymbolRequired) {
		this.kosherSymbolRequired = kosherSymbolRequired;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public String getInlineSock() {
		return inlineSock;
	}

	public void setInlineSock(String inlineSock) {
		this.inlineSock = inlineSock;
	}

	public String getCipType() {
		return cipType;
	}

	public void setCipType(String cipType) {
		this.cipType = cipType;
	}

	public String getCutSize() {
		return cutSize;
	}

	public void setCutSize(String cutSize) {
		this.cutSize = cutSize;
	}

	public String getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}

	public String getForeignMaterialDetection() {
		return foreignMaterialDetection;
	}

	public void setForeignMaterialDetection(String foreignMaterialDetection) {
		this.foreignMaterialDetection = foreignMaterialDetection;
	}

	public String getTestBrix() {
		return testBrix;
	}

	public void setTestBrix(String testBrix) {
		this.testBrix = testBrix;
	}

	public String getReconstitutionRatio() {
		return reconstitutionRatio;
	}

	public void setReconstitutionRatio(String reconstitutionRatio) {
		this.reconstitutionRatio = reconstitutionRatio;
	}

	public String getRevisionReason() {
		return revisionReason;
	}

	public void setRevisionReason(String revisionReason) {
		this.revisionReason = revisionReason;
	}

	public String getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}

	public String getStorageRecommendation() {
		return storageRecommendation;
	}

	public void setStorageRecommendation(String storageRecommendation) {
		this.storageRecommendation = storageRecommendation;
	}

	public String getContainerCodeLocation() {
		return containerCodeLocation;
	}

	public void setContainerCodeLocation(String containerCodeLocation) {
		this.containerCodeLocation = containerCodeLocation;
	}

	public String getContainerCodeFontSize() {
		return containerCodeFontSize;
	}

	public void setContainerCodeFontSize(String containerCodeFontSize) {
		this.containerCodeFontSize = containerCodeFontSize;
	}

	public String getCaseShowBarCode() {
		return caseShowBarCode;
	}

	public void setCaseShowBarCode(String caseShowBarCode) {
		this.caseShowBarCode = caseShowBarCode;
	}

	public String getCaseCodeFontSize() {
		return caseCodeFontSize;
	}

	public void setCaseCodeFontSize(String caseCodeFontSize) {
		this.caseCodeFontSize = caseCodeFontSize;
	}

	public String getPalletLabelType() {
		return palletLabelType;
	}

	public void setPalletLabelType(String palletLabelType) {
		this.palletLabelType = palletLabelType;
	}

	public String getPalletLabelLocation() {
		return palletLabelLocation;
	}

	public void setPalletLabelLocation(String palletLabelLocation) {
		this.palletLabelLocation = palletLabelLocation;
	}

	public String getCartonCodeLocation() {
		return cartonCodeLocation;
	}

	public void setCartonCodeLocation(String cartonCodeLocation) {
		this.cartonCodeLocation = cartonCodeLocation;
	}

	public String getCartonCodeFontSize() {
		return cartonCodeFontSize;
	}

	public void setCartonCodeFontSize(String cartonCodeFontSize) {
		this.cartonCodeFontSize = cartonCodeFontSize;
	}

	public String getPalletRequirement() {
		return palletRequirement;
	}

	public void setPalletRequirement(String palletRequirement) {
		this.palletRequirement = palletRequirement;
	}

	public Vector<BuildProductDataSheetAttributes> getListAnalyticalTests() {
		return listAnalyticalTests;
	}

	public void setListAnalyticalTests(Vector<BuildProductDataSheetAttributes> listAnalyticalTests) {
		this.listAnalyticalTests = listAnalyticalTests;
	}

	public String getCountAnalyticalTests() {
		return countAnalyticalTests;
	}

	public void setCountAnalyticalTests(String countAnalyticalTests) {
		this.countAnalyticalTests = countAnalyticalTests;
	}

	public Vector<BuildProductDataSheetAttributes> getListMicroTests() {
		return listMicroTests;
	}

	public void setListMicroTests(Vector<BuildProductDataSheetAttributes> listMicroTests) {
		this.listMicroTests = listMicroTests;
	}

	public String getCountMicroTests() {
		return countMicroTests;
	}

	public void setCountMicroTests(String countMicroTests) {
		this.countMicroTests = countMicroTests;
	}

	public Vector<BuildProductDataSheetAttributes> getListProcessParameters() {
		return listProcessParameters;
	}

	public void setListProcessParameters(Vector<BuildProductDataSheetAttributes> listProcessParameters) {
		this.listProcessParameters = listProcessParameters;
	}

	public String getCountProcessParameters() {
		return countProcessParameters;
	}

	public void setCountProcessParameters(String countProcessParameters) {
		this.countProcessParameters = countProcessParameters;
	}

	public Vector<BuildProductDataSheetAttributes> getListAdditivesAndPreservatives() {
		return listAdditivesAndPreservatives;
	}

	public void setListAdditivesAndPreservatives(Vector<BuildProductDataSheetAttributes> listAdditivesAndPreservatives) {
		this.listAdditivesAndPreservatives = listAdditivesAndPreservatives;
	}

	public String getCountAdditivesAndPreservatives() {
		return countAdditivesAndPreservatives;
	}

	public void setCountAdditivesAndPreservatives(String countAdditivesAndPreservatives) {
		this.countAdditivesAndPreservatives = countAdditivesAndPreservatives;
	}

	public String getVarietiesIncluded() {
		return varietiesIncluded;
	}

	public void setVarietiesIncluded(String varietiesIncluded) {
		this.varietiesIncluded = varietiesIncluded;
	}

	public String getVarietiesExcluded() {
		return varietiesExcluded;
	}

	public void setVarietiesExcluded(String varietiesExcluded) {
		this.varietiesExcluded = varietiesExcluded;
	}

	public Vector getListComments() {
		return listComments;
	}

	public void setListComments(Vector listComments) {
		this.listComments = listComments;
	}

	public String[] getLinesComments() {
		return linesComments;
	}

	public void setLinesComments(String[] linesComments) {
		this.linesComments = linesComments;
	}

	public Vector getListAnalyticalTestComments() {
		return listAnalyticalTestComments;
	}

	public void setListAnalyticalTestComments(Vector listAnalyticalTestComments) {
		this.listAnalyticalTestComments = listAnalyticalTestComments;
	}

	public String[] getLinesAnalyticalTestComments() {
		return linesAnalyticalTestComments;
	}

	public void setLinesAnalyticalTestComments(String[] linesAnalyticalTestComments) {
		this.linesAnalyticalTestComments = linesAnalyticalTestComments;
	}

	public Vector getListProcessParameterComments() {
		return listProcessParameterComments;
	}

	public void setListProcessParameterComments(Vector listProcessParameterComments) {
		this.listProcessParameterComments = listProcessParameterComments;
	}

	public String[] getLinesProcessParameterComments() {
		return linesProcessParameterComments;
	}

	public void setLinesProcessParameterComments(String[] linesProcessParameterComments) {
		this.linesProcessParameterComments = linesProcessParameterComments;
	}

	public Vector getListMicroTestComments() {
		return listMicroTestComments;
	}

	public void setListMicroTestComments(Vector listMicroTestComments) {
		this.listMicroTestComments = listMicroTestComments;
	}

	public String[] getLinesMicroTestComments() {
		return linesMicroTestComments;
	}

	public void setLinesMicroTestComments(String[] linesMicroTestComments) {
		this.linesMicroTestComments = linesMicroTestComments;
	}

	public Vector getListAdditivesAndPreservativeComments() {
		return listAdditivesAndPreservativeComments;
	}

	public void setListAdditivesAndPreservativeComments(Vector listAdditivesAndPreservativeComments) {
		this.listAdditivesAndPreservativeComments = listAdditivesAndPreservativeComments;
	}

	public String[] getLinesAdditivesAndPreservativeComments() {
		return linesAdditivesAndPreservativeComments;
	}

	public void setLinesAdditivesAndPreservativeComments(String[] linesAdditivesAndPreservativeComments) {
		this.linesAdditivesAndPreservativeComments = linesAdditivesAndPreservativeComments;
	}

	public Vector getListContainerPrintByLine() {
		return listContainerPrintByLine;
	}

	public void setListContainerPrintByLine(Vector listContainerPrintByLine) {
		this.listContainerPrintByLine = listContainerPrintByLine;
	}

	public String[] getLinesContainerPrintByLine() {
		return linesContainerPrintByLine;
	}

	public void setLinesContainerPrintByLine(String[] linesContainerPrintByLine) {
		this.linesContainerPrintByLine = linesContainerPrintByLine;
	}

	public Vector getListContainerPrintAdditional() {
		return listContainerPrintAdditional;
	}

	public void setListContainerPrintAdditional(Vector listContainerPrintAdditional) {
		this.listContainerPrintAdditional = listContainerPrintAdditional;
	}

	public String[] getLinesContainerPrintAdditional() {
		return linesContainerPrintAdditional;
	}

	public void setLinesContainerPrintAdditional(String[] linesContainerPrintAdditional) {
		this.linesContainerPrintAdditional = linesContainerPrintAdditional;
	}

	public Vector getListCasePrintByLine() {
		return listCasePrintByLine;
	}

	public void setListCasePrintByLine(Vector listCasePrintByLine) {
		this.listCasePrintByLine = listCasePrintByLine;
	}

	public String[] getLinesCasePrintByLine() {
		return linesCasePrintByLine;
	}

	public void setLinesCasePrintByLine(String[] linesCasePrintByLine) {
		this.linesCasePrintByLine = linesCasePrintByLine;
	}

	public Vector getListCasePrintAdditional() {
		return listCasePrintAdditional;
	}

	public void setListCasePrintAdditional(Vector listCasePrintAdditional) {
		this.listCasePrintAdditional = listCasePrintAdditional;
	}

	public String[] getLinesCasePrintAdditional() {
		return linesCasePrintAdditional;
	}

	public void setLinesCasePrintAdditional(String[] linesCasePrintAdditional) {
		this.linesCasePrintAdditional = linesCasePrintAdditional;
	}

	public Vector getListPalletPrintByLine() {
		return listPalletPrintByLine;
	}

	public void setListPalletPrintByLine(Vector listPalletPrintByLine) {
		this.listPalletPrintByLine = listPalletPrintByLine;
	}

	public String[] getLinesPalletPrintByLine() {
		return linesPalletPrintByLine;
	}

	public void setLinesPalletPrintByLine(String[] linesPalletPrintByLine) {
		this.linesPalletPrintByLine = linesPalletPrintByLine;
	}

	public Vector getListPalletPrintAdditional() {
		return listPalletPrintAdditional;
	}

	public void setListPalletPrintAdditional(Vector listPalletPrintAdditional) {
		this.listPalletPrintAdditional = listPalletPrintAdditional;
	}

	public String[] getLinesPalletPrintAdditional() {
		return linesPalletPrintAdditional;
	}

	public void setLinesPalletPrintAdditional(String[] linesPalletPrintAdditional) {
		this.linesPalletPrintAdditional = linesPalletPrintAdditional;
	}

	public Vector getListLabelPrintByLine() {
		return listLabelPrintByLine;
	}

	public void setListLabelPrintByLine(Vector listLabelPrintByLine) {
		this.listLabelPrintByLine = listLabelPrintByLine;
	}

	public String[] getLinesLabelPrintByLine() {
		return linesLabelPrintByLine;
	}

	public void setLinesLabelPrintByLine(String[] linesLabelPrintByLine) {
		this.linesLabelPrintByLine = linesLabelPrintByLine;
	}

	public Vector getListLabelPrintAdditional() {
		return listLabelPrintAdditional;
	}

	public void setListLabelPrintAdditional(Vector listLabelPrintAdditional) {
		this.listLabelPrintAdditional = listLabelPrintAdditional;
	}

	public String[] getLinesLabelPrintAdditional() {
		return linesLabelPrintAdditional;
	}

	public void setLinesLabelPrintAdditional(String[] linesLabelPrintAdditional) {
		this.linesLabelPrintAdditional = linesLabelPrintAdditional;
	}

	public Vector getListShelfLifeRequirements() {
		return listShelfLifeRequirements;
	}

	public void setListShelfLifeRequirements(Vector listShelfLifeRequirements) {
		this.listShelfLifeRequirements = listShelfLifeRequirements;
	}

	public String[] getLinesShelfLifeRequirements() {
		return linesShelfLifeRequirements;
	}

	public void setLinesShelfLifeRequirements(String[] linesShelfLifeRequirements) {
		this.linesShelfLifeRequirements = linesShelfLifeRequirements;
	}

	public Vector getListStorageRequirements() {
		return listStorageRequirements;
	}

	public void setListStorageRequirements(Vector listStorageRequirements) {
		this.listStorageRequirements = listStorageRequirements;
	}

	public String[] getLinesStorageRequirements() {
		return linesStorageRequirements;
	}

	public void setLinesStorageRequirements(String[] linesStorageRequirements) {
		this.linesStorageRequirements = linesStorageRequirements;
	}

	public Vector getListFinishedPalletAdditional() {
		return listFinishedPalletAdditional;
	}

	public void setListFinishedPalletAdditional(Vector listFinishedPalletAdditional) {
		this.listFinishedPalletAdditional = listFinishedPalletAdditional;
	}

	public String[] getLinesFinishedPalletAdditional() {
		return linesFinishedPalletAdditional;
	}

	public void setLinesFinishedPalletAdditional(String[] linesFinishedPalletAdditional) {
		this.linesFinishedPalletAdditional = linesFinishedPalletAdditional;
	}

	public Vector getListFruitVarietiesAdditional() {
		return listFruitVarietiesAdditional;
	}

	public void setListFruitVarietiesAdditional(Vector listFruitVarietiesAdditional) {
		this.listFruitVarietiesAdditional = listFruitVarietiesAdditional;
	}

	public String[] getLinesFruitVarietiesAdditional() {
		return linesFruitVarietiesAdditional;
	}

	public void setLinesFruitVarietiesAdditional(String[] linesFruitVarietiesAdditional) {
		this.linesFruitVarietiesAdditional = linesFruitVarietiesAdditional;
	}

	public Vector getListShippingRequirements() {
		return listShippingRequirements;
	}

	public void setListShippingRequirements(Vector listShippingRequirements) {
		this.listShippingRequirements = listShippingRequirements;
	}

	public String[] getLinesShippingRequirements() {
		return linesShippingRequirements;
	}

	public void setLinesShippingRequirements(String[] linesShippingRequirements) {
		this.linesShippingRequirements = linesShippingRequirements;
	}

	public Vector getListCOARequirements() {
		return listCOARequirements;
	}

	public void setListCOARequirements(Vector listCOARequirements) {
		this.listCOARequirements = listCOARequirements;
	}

	public String[] getLinesCOARequirements() {
		return linesCOARequirements;
	}

	public void setLinesCOARequirements(String[] linesCOARequirements) {
		this.linesCOARequirements = linesCOARequirements;
	}

	public Vector getListCartonPrintByLine() {
		return listCartonPrintByLine;
	}

	public void setListCartonPrintByLine(Vector listCartonPrintByLine) {
		this.listCartonPrintByLine = listCartonPrintByLine;
	}

	public String[] getLinesCartonPrintByLine() {
		return linesCartonPrintByLine;
	}

	public void setLinesCartonPrintByLine(String[] linesCartonPrintByLine) {
		this.linesCartonPrintByLine = linesCartonPrintByLine;
	}

	public Vector getListCartonPrintAdditional() {
		return listCartonPrintAdditional;
	}

	public void setListCartonPrintAdditional(Vector listCartonPrintAdditional) {
		this.listCartonPrintAdditional = listCartonPrintAdditional;
	}

	public String[] getLinesCartonPrintAdditional() {
		return linesCartonPrintAdditional;
	}

	public void setLinesCartonPrintAdditional(String[] linesCartonPrintAdditional) {
		this.linesCartonPrintAdditional = linesCartonPrintAdditional;
	}

	public Vector getListProductDescription() {
		return listProductDescription;
	}

	public void setListProductDescription(Vector listProductDescription) {
		this.listProductDescription = listProductDescription;
	}

	public String[] getLinesProductDescription() {
		return linesProductDescription;
	}

	public void setLinesProductDescription(String[] linesProductDescription) {
		this.linesProductDescription = linesProductDescription;
	}

	public Vector getListIngredientStatement() {
		return listIngredientStatement;
	}

	public void setListIngredientStatement(Vector listIngredientStatement) {
		this.listIngredientStatement = listIngredientStatement;
	}

	public String[] getLinesIngredientStatement() {
		return linesIngredientStatement;
	}

	public void setLinesIngredientStatement(String[] linesIngredientStatement) {
		this.linesIngredientStatement = linesIngredientStatement;
	}

	public Vector getListIntendedUse() {
		return listIntendedUse;
	}

	public void setListIntendedUse(Vector listIntendedUse) {
		this.listIntendedUse = listIntendedUse;
	}

	public String[] getLinesIntendedUse() {
		return linesIntendedUse;
	}

	public void setLinesIntendedUse(String[] linesIntendedUse) {
		this.linesIntendedUse = linesIntendedUse;
	}

	public Vector getListForeignMatter() {
		return listForeignMatter;
	}

	public void setListForeignMatter(Vector listForeignMatter) {
		this.listForeignMatter = listForeignMatter;
	}

	public String[] getLinesForeignMatter() {
		return linesForeignMatter;
	}

	public void setLinesForeignMatter(String[] linesForeignMatter) {
		this.linesForeignMatter = linesForeignMatter;
	}

	public Vector getListCodingRequirementsAdditional() {
		return listCodingRequirementsAdditional;
	}

	public void setListCodingRequirementsAdditional(Vector listCodingRequirementsAdditional) {
		this.listCodingRequirementsAdditional = listCodingRequirementsAdditional;
	}

	public String[] getLinesCodingRequirementsAdditional() {
		return linesCodingRequirementsAdditional;
	}

	public void setLinesCodingRequirementsAdditional(String[] linesCodingRequirementsAdditional) {
		this.linesCodingRequirementsAdditional = linesCodingRequirementsAdditional;
	}

	public Vector getListStatements() {
		return listStatements;
	}

	public void setListStatements(Vector listStatements) {
		this.listStatements = listStatements;
	}

	public String[] getLinesStatements() {
		return linesStatements;
	}

	public void setLinesStatements(String[] linesStatements) {
		this.linesStatements = linesStatements;
	}

	public BeanQuality getDtlBean() {
		return dtlBean;
	}

	public void setDtlBean(BeanQuality dtlBean) {
		this.dtlBean = dtlBean;
	}
	
	
	


}


