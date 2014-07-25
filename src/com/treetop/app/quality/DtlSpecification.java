/*
 * Created on May 25, 2010
 * 
 */

package com.treetop.app.quality;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.app.item.InqCodeDate;
import com.treetop.businessobjectapplications.BeanGTIN;
import com.treetop.businessobjectapplications.BeanQuality;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.GTINChild;
import com.treetop.businessobjects.ItemWarehouse;
import com.treetop.businessobjects.ProductStructureMaterial;
import com.treetop.businessobjects.QaFormula;
import com.treetop.businessobjects.QaFormulaDetail;
import com.treetop.businessobjects.QaFruitVariety;
import com.treetop.businessobjects.QaSpecification;
import com.treetop.businessobjects.QaTestParameters;
import com.treetop.viewbeans.BaseViewBeanR2;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.services.ServiceItem;
import com.treetop.services.ServiceQuality;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.*;

/**
 * @author twalto
 * 
 * 
 */
public class DtlSpecification extends BaseViewBeanR2 {
	
	public String securityLevel			= "";

	public String specNumber 			= "";
	public String revisionDate 			= "";
	public String revisionTime 			= "";
	
	public BeanQuality dtlBean 			= new BeanQuality();
	
//	 Will be processed through the Comment Program
	  // Will be processed through the Comment program
	public Vector listSpecUrls 							= new Vector();
	public Vector listNutritionPanel					= new Vector();
	public Vector listPalletPattern						= new Vector();
	public Vector listExampleLabel						= new Vector();
	
	public Vector listComments 							= new Vector(); // revision information
	public Vector listAnalyticalTestComments			= new Vector(); // comment1
	public Vector listProcessParameterComments			= new Vector(); // comment2
	public Vector listMicroTestComments					= new Vector(); // comment3
	public Vector listAdditivesAndPreservativeComments	= new Vector(); // comment4
	public Vector listContainerPrintByLine				= new Vector(); // comment5
	public Vector listContainerPrintAdditional			= new Vector(); // comment6
	public Vector listCasePrintByLine					= new Vector(); // comment7
	public Vector listCasePrintAdditional				= new Vector(); // comment8
	public Vector listPalletPrintByLine					= new Vector(); // comment9
	public Vector listPalletPrintAdditional				= new Vector(); // comment10
	public Vector listLabelPrintByLine					= new Vector(); // comment11
	public Vector listLabelPrintAdditional				= new Vector(); // comment12
	public Vector listShelfLifeRequirements				= new Vector(); // comment13
	public Vector listStorageRequirements				= new Vector(); // comment14
	public Vector listFinishedPalletAdditional			= new Vector(); // comment15
	public Vector listFruitVarietiesAdditional			= new Vector(); // comment16
	public Vector listShippingRequirements				= new Vector(); // comment17
	public Vector listCOARequirements					= new Vector(); // comment18
	public Vector listCartonPrintByLine					= new Vector(); // comment19
	public Vector listCartonPrintAdditional				= new Vector(); // comment20
	public Vector listProductDescription				= new Vector(); // comment21
	public Vector listIngredientStatement				= new Vector(); // comment22
	public Vector listIntendedUse						= new Vector(); // comment23
	public Vector listForeignMatter						= new Vector(); // comment24
	public Vector listCodingRequirementsAdditional		= new Vector(); // comment25
	
	// Best By Date -- Choose a date,  see Julian Date as well
	public String chosenDate							= ""; // default in today
	public String bestByValue							= ""; 
	public String julianDate							= "";
	public String showCaseGTIN							= "";
	
	public DateTime  supersedesDate						= new DateTime();
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		
		return;
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
	*  Take in Information and then Calculate the Cubic Feet for the values
	* @return
	*/
	public static String calculateCaseCube(String length,
										   String width,
										   String height) {

		String returnValue = "0.000";
		try
		{
	    // Cubic Feet
	    //*********
			BigDecimal twelve      = new BigDecimal("12");
			BigDecimal lengthFeet  = ((new BigDecimal(length)).setScale(6)).divide(twelve,1);
			BigDecimal widthFeet   = ((new BigDecimal(width)).setScale(6)).divide(twelve,1);
			BigDecimal heightFeet  = ((new BigDecimal(height)).setScale(6)).divide(twelve,1);

	        BigDecimal cubicFeet = lengthFeet.multiply(widthFeet);
	         cubicFeet = cubicFeet.multiply(heightFeet);
	         cubicFeet = cubicFeet.setScale(3,0);
	        returnValue = cubicFeet.toString();
		}
		catch(Exception e)
		{}
		return returnValue;
	}
	/**
	*  Take in Information and then Calculate the Number of Layers on Each Pallet
	* @return
	*/
	public static String calculateLayersPerPallet(String casesPerPallet,
												   	  String casesPerLayer) {
				
		 String returnValue = "0";
		 try
		 {
		 //*********
		 // Layers Per Pallet
		 //*********
		 	returnValue = HTMLHelpersMasking.maskNumber((new BigDecimal(casesPerPallet).setScale(3)).divide(((new BigDecimal(casesPerLayer)).setScale(3)), 2).toString(), 0);
		 }
		 catch(Exception e)
		 {}
		 return returnValue;
	}
	/**
	*  Take in Information and then Calculate the Pallet Height
	*     Assumption that the pallet itself is 5 inches
	* @return
	*/
	public static String calculatePalletHeight(String layers,
											   String height) {
		String returnValue = "0";
		try
		{
//			*********
         // Pallet Height
         //*********
			BigDecimal heightInches = (new BigDecimal(height)).setScale(6);
			BigDecimal palletHeight = ((new BigDecimal(layers)).setScale(3)).multiply(heightInches);
	        palletHeight = (palletHeight.add(new BigDecimal("5.75"))).setScale(3, 0);
	        returnValue = HTMLHelpersMasking.maskNumber(palletHeight.toString(), 3);
	        
//	        palletHeight = layerPallet.multiply(heightInches);
//	         palletHeight = palletHeight.add(five);
//	         palletHeight = palletHeight.setScale(3,0);  
	        
		}
		catch(Exception e)
		{}
		return returnValue;
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
	public BeanQuality getDtlBean() {
		return dtlBean;
	}
	public void setDtlBean(BeanQuality dtlBean) {
		this.dtlBean = dtlBean;
	}
	public Vector getListComments() {
		return listComments;
	}
	public void setListComments(Vector listComments) {
		this.listComments = listComments;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	/**
	*  Take in Information and then Build and return the code to create the Link to Formula
	* @return
	*/
	public static String formulaLink(String environment,
									 String formulaNumber,
									 String revisionDate,
									 String revisionTime) {
				
		 String returnValue = formulaNumber;
		 try
		 {
			 if (!formulaNumber.trim().equals("") && !formulaNumber.trim().equals("0"))
			 {
				 returnValue = "<a href=\"/web/CtlQuality?requestType=dtlFormula&environment=" + 
				               environment + "&formulaNumber=" + formulaNumber;
				 if (!revisionDate.trim().equals("") && !revisionDate.trim().equals("0"))
				 {
					 returnValue = returnValue + "&revisionDate=" + revisionDate +
					 							 "&revisionTime=" + revisionTime;
				 }
				 returnValue = returnValue + "\">" + formulaNumber.trim() + "</a>";
			 }
		 }
		 catch(Exception e)
		 {}
		 return returnValue;
	}
	/**
	*  Take in Information and then Build and return the code to create the Line to Method
	* @return
	*/
	public static String methodLink(String environment,
								    String methodNumber,
								    String methodName,
								    String revisionDate,
									String revisionTime) {
				
		 String returnValue = methodNumber;
		 try
		 {
			 if (!methodNumber.trim().equals("") && !methodNumber.trim().equals("0"))
			 {
				 returnValue = "<a href=\"/web/CtlQuality?requestType=dtlMethod&environment=" + 
				 			   environment + "&methodNumber=" + methodNumber;
				 if (!revisionDate.trim().equals("") && !revisionDate.trim().equals("0"))
				 {
					 returnValue = returnValue + "&revisionDate=" + revisionDate +
					 							 "&revisionTime=" + revisionTime;
				 }
				 returnValue = returnValue + "\">" + methodName.trim() + "</a>";
			 }
		 }
		 catch(Exception e)
		 {}
		 return returnValue;
	}
	public Vector getListSpecUrls() {
		return listSpecUrls;
	}
	public void setListSpecUrls(Vector listSpecUrls) {
		this.listSpecUrls = listSpecUrls;
	}
	public Vector getListNutritionPanel() {
		return listNutritionPanel;
	}
	public void setListNutritionPanel(Vector listNutritionPanel) {
		this.listNutritionPanel = listNutritionPanel;
	}
	public Vector getListPalletPattern() {
		return listPalletPattern;
	}
	public void setListPalletPattern(Vector listPalletPattern) {
		this.listPalletPattern = listPalletPattern;
	}
	public Vector getListAnalyticalTestComments() {
		return listAnalyticalTestComments;
	}
	public void setListAnalyticalTestComments(Vector listAnalyticalTestComments) {
		this.listAnalyticalTestComments = listAnalyticalTestComments;
	}
	public Vector getListProcessParameterComments() {
		return listProcessParameterComments;
	}
	public void setListProcessParameterComments(Vector listProcessParameterComments) {
		this.listProcessParameterComments = listProcessParameterComments;
	}
	public Vector getListMicroTestComments() {
		return listMicroTestComments;
	}
	public void setListMicroTestComments(Vector listMicroTestComments) {
		this.listMicroTestComments = listMicroTestComments;
	}
	public Vector getListAdditivesAndPreservativeComments() {
		return listAdditivesAndPreservativeComments;
	}
	public void setListAdditivesAndPreservativeComments(
			Vector listAdditivesAndPreservativeComments) {
		this.listAdditivesAndPreservativeComments = listAdditivesAndPreservativeComments;
	}
	public Vector getListContainerPrintByLine() {
		return listContainerPrintByLine;
	}
	public void setListContainerPrintByLine(Vector listContainerPrintByLine) {
		this.listContainerPrintByLine = listContainerPrintByLine;
	}
	public Vector getListContainerPrintAdditional() {
		return listContainerPrintAdditional;
	}
	public void setListContainerPrintAdditional(Vector listContainerPrintAdditional) {
		this.listContainerPrintAdditional = listContainerPrintAdditional;
	}
	public Vector getListCasePrintByLine() {
		return listCasePrintByLine;
	}
	public void setListCasePrintByLine(Vector listCasePrintByLine) {
		this.listCasePrintByLine = listCasePrintByLine;
	}
	public Vector getListCasePrintAdditional() {
		return listCasePrintAdditional;
	}
	public void setListCasePrintAdditional(Vector listCasePrintAdditional) {
		this.listCasePrintAdditional = listCasePrintAdditional;
	}
	public Vector getListPalletPrintByLine() {
		return listPalletPrintByLine;
	}
	public void setListPalletPrintByLine(Vector listPalletPrintByLine) {
		this.listPalletPrintByLine = listPalletPrintByLine;
	}
	public Vector getListPalletPrintAdditional() {
		return listPalletPrintAdditional;
	}
	public void setListPalletPrintAdditional(Vector listPalletPrintAdditional) {
		this.listPalletPrintAdditional = listPalletPrintAdditional;
	}
	public Vector getListLabelPrintByLine() {
		return listLabelPrintByLine;
	}
	public void setListLabelPrintByLine(Vector listLabelPrintByLine) {
		this.listLabelPrintByLine = listLabelPrintByLine;
	}
	public Vector getListLabelPrintAdditional() {
		return listLabelPrintAdditional;
	}
	public void setListLabelPrintAdditional(Vector listLabelPrintAdditional) {
		this.listLabelPrintAdditional = listLabelPrintAdditional;
	}
	public Vector getListShelfLifeRequirements() {
		return listShelfLifeRequirements;
	}
	public void setListShelfLifeRequirements(Vector listShelfLifeRequirements) {
		this.listShelfLifeRequirements = listShelfLifeRequirements;
	}
	public Vector getListStorageRequirements() {
		return listStorageRequirements;
	}
	public void setListStorageRequirements(Vector listStorageRequirements) {
		this.listStorageRequirements = listStorageRequirements;
	}
	public String getChosenDate() {
		return chosenDate;
	}
	public void setChosenDate(String chosenDate) {
		this.chosenDate = chosenDate;
	}
	public String getBestByValue() {
		return bestByValue;
	}
	public void setBestByValue(String bestByValue) {
		this.bestByValue = bestByValue;
	}
	public String getJulianDate() {
		return julianDate;
	}
	public void setJulianDate(String julianDate) {
		this.julianDate = julianDate;
	}
	/*
	 * Use Data already in the fields to generate the Code Date information
	 *    Currently this information relates to Best By Date and the Julian Date
	 *    Creation date: (12/20/2011 TWalton)
	 */
	public void processCodeDateInformation() {
		try {
			if (!this.getDtlBean().getSpecification().getItemNumber().trim().equals("") &&
				!this.getDtlBean().getProductStructure().isEmpty() &&
				!(((Vector)((Vector) this.getDtlBean().getProductStructure()).elementAt(0)).isEmpty()))
			{
			   try{
				   ProductStructureMaterial psm = (ProductStructureMaterial) ((Vector) ((Vector) this.getDtlBean().getProductStructure()).elementAt(0)).elementAt(0);
				// Create a Code Date Inquiry Object
				   InqCodeDate icd = new InqCodeDate(); 
				   icd.setInqItem(this.getDtlBean().getSpecification().getItemNumber());
				   icd.setInqWarehouse(psm.getMainWarehouse());
				   icd.setInqFacility(psm.getFacility());
				   if (this.getChosenDate().equals(""))
				   {
					   // Default in the System Date
					   DateTime dt = UtilityDateTime.getSystemDate();
					   this.setChosenDate(dt.getDateFormatMMddyyyySlash());
					   this.setJulianDate(dt.getDateFormatJulian());
				   }else{
					   DateTime dt = UtilityDateTime.getDateFromMMddyyyyWithSlash(this.getChosenDate());
					   this.setJulianDate(dt.getDateFormatJulian());
				   }
				   icd.setInqDateSelected(this.getChosenDate());
				   Vector sendValues = new Vector();
				   sendValues.addElement(icd);
				   icd.setItemWhse(ServiceItem.findItemCodeDate(sendValues));
				   icd.buildBestByValue();
				   this.setBestByValue(icd.getBestByValue());
			   }catch(Exception e)
			   {
				   System.out.println("Error within processCodeDateInformation" + e);
			   }
			}
			
		} catch (Exception e) {
		   System.out.println("Error Caught in DtlSpecification.processCodeDateInformation: " + e);
		}
		return;
	}
	/*
	 * Read Through the vector to find the CASE Gtin
	 *    Creation date: (1/3/2012 TWalton)
	 */
	public void processCaseGTIN(Vector listGTIN) {
		try {
			if (!listGTIN.isEmpty())
			{
			   try{
				   String showGTIN = "";
				   for (int fam = 0; fam < listGTIN.size(); fam++)
				   {
					   BeanGTIN family     = (BeanGTIN) listGTIN.elementAt(fam);
				       if (!family.getChildren().isEmpty())
					   {  
						   for (int child = 0; child < family.getChildren().size(); child++)
						  {
						     GTINChild thisChild = (GTINChild) family.getChildren().elementAt(child);
						     if (thisChild.getGtinNumber() != null &&
						    	 thisChild.getTradeItemUnitDescriptor().equals("CA"))
						     {
						    	 if (showGTIN.trim().equals(""))
						    		 showGTIN = thisChild.getGtinNumber();
						    	 else
						    		 showGTIN = "This Item has more than one Case GTIN";
						     }
						  }
					   }   
				   }
				   this.setShowCaseGTIN(showGTIN);
			   }catch(Exception e)
			   {
				   System.out.println("Error within processCaseGTIN" + e);
			   }
			}
			
		} catch (Exception e) {
		   System.out.println("Error Caught in DtlSpecification.processCaseGTIN: " + e);
		}
		return;
	}
	public String getShowCaseGTIN() {
		return showCaseGTIN;
	}
	public void setShowCaseGTIN(String showCaseGTIN) {
		this.showCaseGTIN = showCaseGTIN;
	}
	public Vector getListFinishedPalletAdditional() {
		return listFinishedPalletAdditional;
	}
	public void setListFinishedPalletAdditional(Vector listFinishedPalletAdditional) {
		this.listFinishedPalletAdditional = listFinishedPalletAdditional;
	}
	public Vector getListFruitVarietiesAdditional() {
		return listFruitVarietiesAdditional;
	}
	public void setListFruitVarietiesAdditional(Vector listFruitVarietiesAdditional) {
		this.listFruitVarietiesAdditional = listFruitVarietiesAdditional;
	}
	public Vector getListShippingRequirements() {
		return listShippingRequirements;
	}
	public void setListShippingRequirements(Vector listShippingRequirements) {
		this.listShippingRequirements = listShippingRequirements;
	}
	public Vector getListCOARequirements() {
		return listCOARequirements;
	}
	public void setListCOARequirements(Vector listCOARequirements) {
		this.listCOARequirements = listCOARequirements;
	}
	public Vector getListCartonPrintByLine() {
		return listCartonPrintByLine;
	}
	public void setListCartonPrintByLine(Vector listCartonPrintByLine) {
		this.listCartonPrintByLine = listCartonPrintByLine;
	}
	public Vector getListCartonPrintAdditional() {
		return listCartonPrintAdditional;
	}
	public void setListCartonPrintAdditional(Vector listCartonPrintAdditional) {
		this.listCartonPrintAdditional = listCartonPrintAdditional;
	}
	public Vector getListExampleLabel() {
		return listExampleLabel;
	}
	public void setListExampleLabel(Vector listExampleLabel) {
		this.listExampleLabel = listExampleLabel;
	}
	public Vector getListProductDescription() {
		return listProductDescription;
	}
	public void setListProductDescription(Vector listProductDescription) {
		this.listProductDescription = listProductDescription;
	}
	public Vector getListIngredientStatement() {
		return listIngredientStatement;
	}
	public void setListIngredientStatement(Vector listIngredientStatement) {
		this.listIngredientStatement = listIngredientStatement;
	}
	public Vector getListIntendedUse() {
		return listIntendedUse;
	}
	public void setListIntendedUse(Vector listIntendedUse) {
		this.listIntendedUse = listIntendedUse;
	}
	public Vector getListForeignMatter() {
		return listForeignMatter;
	}
	public void setListForeignMatter(Vector listForeignMatter) {
		this.listForeignMatter = listForeignMatter;
	}
	public Vector getListCodingRequirementsAdditional() {
		return listCodingRequirementsAdditional;
	}
	public void setListCodingRequirementsAdditional(
			Vector listCodingRequirementsAdditional) {
		this.listCodingRequirementsAdditional = listCodingRequirementsAdditional;
	}
	public DateTime getSupersedesDate() {
		return supersedesDate;
	}
	public void setSupersedesDate(DateTime supersedesDate) {
		this.supersedesDate = supersedesDate;
	}
}
