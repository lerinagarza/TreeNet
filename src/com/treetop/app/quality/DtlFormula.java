/*
 * Created on May 25, 2010
 */

package com.treetop.app.quality;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.QaFormulaDetail;
import com.treetop.businessobjects.QaFruitVariety;
import com.treetop.businessobjects.QaSpecificationPackaging;
import com.treetop.businessobjects.QaTestParameters;
import com.treetop.businessobjectapplications.BeanQuality;
import com.treetop.viewbeans.BaseViewBeanR2;
import com.treetop.utilities.*;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HTMLHelpers;
import com.treetop.utilities.html.HTMLHelpersMasking;

/**
 * @author twalto
 * 
 * Retrieve and Display a SPECIFIC Formula
 */
public class DtlFormula extends BaseViewBeanR2 {
	// Standard Fields - to be in Every View Bean
	
	public String securityLevel				= "";

	public String formulaNumber      		= "";
	public String revisionDate       		= "";
	public String revisionTime       		= "";
	
	public BeanQuality dtlBean = new BeanQuality();
	public String saucePreBlendIngredient3	= ""; // if there is a N in this field, can remove the third section for the sauce preblend
	
//	 Will be processed through the Comment Program
	public Vector listComments				= new Vector();
	public Vector listDetails				= new Vector();
	public Vector listCalculations		    = new Vector();
	public Vector listBlendingInstructions	= new Vector();
	public Vector listKeyLabelStatements	= new Vector();
	public Vector listIngredientStatement	= new Vector();
	public Vector listRFAdditionalInfo		= new Vector();
	
	public DateTime  supersedesDate			= new DateTime();
		
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
		try
		{	
		
		}
		catch(Exception e)
		{
			
		}
		return;
	}
	/**
	 *  This method should be in EVERY Inquiry View Bean
	 *   Will create the vectors and generate the code for
	 *    MORE Button.
	 * @return
	 */
	public static String buildMoreButton(String requestType,
										 String formulaNumber,
										 String revisionDate, 
										 String revisionTime) {
		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[3];
		String[] urlNames = new String[3];
		String[] newPage = new String[3];
		for (int z = 0; z < 3; z++) {
			urlLinks[z] = "";
			urlNames[z] = "";
			newPage[z] = "";
		}
//		if (requestType.equals("updateLot")) {
//			urlLinks[0] = "/web/CtlRawFruit?requestType=updateLot"
//					+ "&scaleTicket=" + scaleTicket
//					+ "&scaleTicketCorrectionSequence=" + scaleTicketCorrectionSequence
//					+ "&receivingDate=" + receivingDate
//					+ "&scaleSequence=" + scaleSequence
//					+ "&poNumber=" + poNumber
//					+ "&lotSequenceNumber=" + lotSequence
//					+ "&lotNumber=" + lotNumber;
//			urlNames[0] = "Update Lot Number " + lotNumber;
//			newPage[0] = "N";
//			urlLinks[1] = "/web/CtlRawFruit?requestType=reportInvoice"
//				+ "&scaleTicket=" + scaleTicket
//				+ "&scaleTicketCorrectionSequence=" + scaleTicketCorrectionSequence
//				+ "&lotNumber=" + lotNumber;
//			urlNames[1] = "Payment/Invoice Report ";
//			newPage[1] = "Y";	
//			urlLinks[2] = "/web/CtlRawFruit?requestType=deleteLot"
//				+ "&scaleTicket=" + scaleTicket
//				+ "&scaleTicketCorrectionSequence=" + scaleTicketCorrectionSequence
//				+ "&receivingDate=" + receivingDate
//				+ "&scaleSequence=" + scaleSequence
//				+ "&poNumber=" + poNumber
//				+ "&lotSequenceNumber=" + lotSequence
//				+ "&lotNumber=" + lotNumber;
//		urlNames[2] = "Delete Lot Number " + lotNumber;
//		newPage[2] = "N";
//		}
//		if (requestType.equals("updatePO")) {
//			urlLinks[0] = "/web/CtlRawFruit?requestType=deletePO"
//					+ "&scaleTicket=" + scaleTicket
//					+ "&scaleTicketCorrectionSequence=" + scaleTicketCorrectionSequence
//					+ "&receivingDate=" + receivingDate
//					+ "&scaleSequence=" + scaleSequence
//					+ "&poNumber=" + poNumber;
//			urlNames[0] = "Delete Sequence Number " + scaleSequence;
//			newPage[0] = "N";
//		}
//		return HTMLHelpers.buttonMore(urlLinks, urlNames, newPage);
		return requestType;
	}
	public String getFormulaNumber() {
		return formulaNumber;
	}
	public void setFormulaNumber(String formulaNumber) {
		this.formulaNumber = formulaNumber;
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
	public BeanQuality getDtlBean() {
		return dtlBean;
	}
	public void setDtlBean(BeanQuality dtlBean) {
		this.dtlBean = dtlBean;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	public Vector getListCalculations() {
		return listCalculations;
	}
	public void setListCalculations(Vector listCalculations) {
		this.listCalculations = listCalculations;
	}
	public Vector getListComments() {
		return listComments;
	}
	public void setListComments(Vector listComments) {
		this.listComments = listComments;
	}
	public Vector getListDetails() {
		return listDetails;
	}
	public void setListDetails(Vector listDetails) {
		this.listDetails = listDetails;
	}
	public Vector getListBlendingInstructions() {
		return listBlendingInstructions;
	}
	public void setListBlendingInstructions(Vector listBlendingInstructions) {
		this.listBlendingInstructions = listBlendingInstructions;
	}
	public Vector getListIngredientStatement() {
		return listIngredientStatement;
	}
	public void setListIngredientStatement(Vector listIngredientStatement) {
		this.listIngredientStatement = listIngredientStatement;
	}
	public Vector getListKeyLabelStatements() {
		return listKeyLabelStatements;
	}
	public void setListKeyLabelStatements(Vector listKeyLabelStatements) {
		this.listKeyLabelStatements = listKeyLabelStatements;
	}
	public Vector getListRFAdditionalInfo() {
		return listRFAdditionalInfo;
	}
	public void setListRFAdditionalInfo(Vector listRFAdditionalInfo) {
		this.listRFAdditionalInfo = listRFAdditionalInfo;
	}
	public String getSaucePreBlendIngredient3() {
		return saucePreBlendIngredient3;
	}
	public void setSaucePreBlendIngredient3(String saucePreBlendIngredient3) {
		this.saucePreBlendIngredient3 = saucePreBlendIngredient3;
	}
	/*
	 * Send in a Vector, read through the vector, determine if need to 
	 *    show the 3rd Ingredient Section
	 * 
	 *    1/12/12 TWalton 
	 */
	public void processSaucePreBlend(Vector listSaucePreBlendDetail) {
		try {
			this.setSaucePreBlendIngredient3("N");
			 if (!listSaucePreBlendDetail.isEmpty())
			 {
			    for (int x = 0; x < listSaucePreBlendDetail.size(); x++)
			    {
			       try
			       {
			          QaFormulaDetail dtlLine = (QaFormulaDetail) listSaucePreBlendDetail.elementAt(x);
			          if (!dtlLine.getItemNumber3().trim().equals("") || 
			        	  !dtlLine.getItemDescription3().trim().equals(""))
			          {
			        	  this.setSaucePreBlendIngredient3("");
			        	  x = listSaucePreBlendDetail.size();
			          }
			       }catch(Exception e)
			       {}
			    } // for loop
			 }// if the list is NOT empty
			
		} catch (Exception e) {
			System.out.println("Error Caught in DtlFormula.processSaucePreBlend(Vector): " + e);
		}
		return;
	}
	public DateTime getSupersedesDate() {
		return supersedesDate;
	}
	public void setSupersedesDate(DateTime supersedesDate) {
		this.supersedesDate = supersedesDate;
	}

}
