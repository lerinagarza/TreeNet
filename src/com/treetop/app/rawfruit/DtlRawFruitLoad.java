/*
 * Created on December 4, 2008
 */

package com.treetop.app.rawfruit;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanRawFruit;
import com.treetop.businessobjects.*;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.HTMLHelpersMasking;
import com.treetop.viewbeans.BaseViewBeanR1;

/**
 * @author twalto
 * 
 */
public class DtlRawFruitLoad extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	// Fields Available for Update
	public String scaleTicket = "";
	public String scaleTicketCorrectionSequence = "0";
	public String receivingDate = "";
	public String lotNumber = "";
	
	public Vector listPayments = new Vector();
	
	public BeanRawFruit dtlBean = new BeanRawFruit();
	
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
	 * @return Returns the scaleTicket.
	 */
	public String getScaleTicket() {
		return scaleTicket;
	}
	/**
	 * @param scaleTicket The scaleTicket to set.
	 */
	public void setScaleTicket(String scaleTicket) {
		this.scaleTicket = scaleTicket;
	}
	/**
	 * @return Returns the dtlBean.
	 */
	public BeanRawFruit getDtlBean() {
		return dtlBean;
	}
	/**
	 * @param dtlBean The dtlBean to set.
	 */
	public void setDtlBean(BeanRawFruit dtlBean) {
		this.dtlBean = dtlBean;
	}
	/**
	 * @return Returns the lotNumber.
	 */
	public String getLotNumber() {
		return lotNumber;
	}
	/**
	 * @param lotNumber The lotNumber to set.
	 */
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	/**
	 *    Will Generate the Vector of RawFruitPayments
	 *       This will include processing all the calculations AND formating the information for display
	 */
	public void buildInvoiceCalculatedFields() {
		this.listPayments = new Vector();
		try
		{
		   RawFruitLoad rfl = this.getDtlBean().getRfLoad();	
		   if (rfl.getListPOs().size() > 0)
		   {
			 for (int a = 0; a < rfl.getListPOs().size(); a++)
			 {
			   RawFruitPO rfp = (RawFruitPO) rfl.getListPOs().elementAt(a);
			   if (rfp.getListLots().size() > 0)
			   {
			    for (int b = 0; b < rfp.getListLots().size(); b++)
			    {
			      RawFruitLot rfLot = (RawFruitLot) rfp.getListLots().elementAt(b);
			     if (this.lotNumber.trim().equals("") ||
			     	 this.lotNumber.trim().equals(rfLot.getLotInformation().getLotNumber().trim()))
			     { 	
			      String displayReceivingDate = rfl.getReceivingDate();
			      try{
			        DateTime receivingDT = UtilityDateTime.getDateFromyyyyMMdd(rfl.getReceivingDate());
			        if (!receivingDT.getDateFormatMMddyySlash().equals(""))
			           displayReceivingDate = receivingDT.getDateFormatMMddyySlash();
			      } catch(Exception e)
			      {}
			      String newDate = "";
			      try{
			    	if (!rfLot.getGrapeDueDate().trim().equals("") &&
			    		rfLot.getGrapeDueDate().trim().length() == 8 ||
			    		rfLot.getGrapeDueDate().trim().length() == 7)
			    	{
			    		newDate = rfLot.getGrapeDueDate().trim().substring((rfLot.getGrapeDueDate().trim().length() - 4), rfLot.getGrapeDueDate().trim().length());
			    		if (rfLot.getGrapeDueDate().trim().length() == 7)
			    			newDate = newDate + "0";
			    		newDate = newDate + rfLot.getGrapeDueDate().trim().substring(0, (rfLot.getGrapeDueDate().trim().length() - 4));
			    		DateTime newDateDT = UtilityDateTime.getDateFromyyyyMMdd(newDate);
			    		newDate = newDateDT.getDateFormatMMddyyyySlash();
			    	}
			      } catch(Exception e)
			      {}
			       if (rfLot.getListPayments().size() > 0)
			        {
			          for (int c = 0; c < rfLot.getListPayments().size(); c++)
			          {
			          	// Build a DtlRawFruitPayment Object to put into the Vector
			          	DtlRawFruitPayment drfp = new DtlRawFruitPayment();
			          	RawFruitPayment rfPayment = (RawFruitPayment) rfLot.getListPayments().elementAt(c);
			          	drfp.setItemNumber(rfLot.getLotInformation().getItemNumber());
			          	drfp.setItemDescription(rfLot.getLotInformation().getItemDescription());
			          	drfp.setLotNumber(rfLot.getLotInformation().getLotNumber());
			          	drfp.setPaymentSequence(rfPayment.getPaymentSequenceNumber());
			          	drfp.setGrapeDueDate(newDate);
			          	drfp.setSupplier(rfLot.getLotSupplier().getSupplierNumber());
			          	drfp.setSupplierDescription(rfLot.getLotSupplier().getSupplierName());
			          	drfp.setSupplierInvoiceNumber(rfLot.getLotInformation().getLotNumber());
			          	drfp.setReceivingDate(displayReceivingDate);
			          	drfp.setPurchaseOrder(rfp.getPoNumber());
			          	drfp.setPaymentCode(rfPayment.getPayCode().getPayCode());
			          	drfp.setPayCodeHandKeyed(rfPayment.getPayCodeHandKeyed());
			          	drfp.setWhseTicket(rfLot.getSupplierLoadNumber());
			          	drfp.setCarrierBillOfLading(rfl.getCarrierBOL());
			          	drfp.setScaleTicketNumber(rfl.getScaleTicketNumber());
			          	drfp.setFacility(rfLot.getLotInformation().getFacility());
			          	drfp.setPaymentWeight(HTMLHelpersMasking.maskBigDecimal(rfPayment.getPaymentWeight(),0));
			          	drfp.setBrix(rfLot.getLotInformation().getBrix());
			          	drfp.setCrop(rfLot.getCrop());
			          	
			          	StringBuffer buildDescription = new StringBuffer();
			          	buildDescription.append(rfPayment.getPayCode().getPaymentType().trim() + "&nbsp;&nbsp;");
			          	buildDescription.append(rfPayment.getPayCode().getCrop().trim() + "&nbsp;&nbsp;");
			          	buildDescription.append(rfPayment.getPayCode().getRunType().trim() + "&nbsp;&nbsp;");
			          	buildDescription.append(rfPayment.getPayCode().getCategory().trim() + "&nbsp;&nbsp;");
			          	buildDescription.append(rfPayment.getPayCode().getConvOrganic().trim() + "&nbsp;&nbsp;");
			          	buildDescription.append(rfPayment.getPayCode().getVariety().trim());
			          	drfp.setCodeDescriptions(buildDescription.toString());
			          	
			          	// Deal with Calculations for Screen
			          	BigDecimal paymentWeight = new BigDecimal("0");
			            try
			            {
			               paymentWeight = new BigDecimal(rfPayment.getPaymentWeight());
			            }
			            catch(Exception e)
			            {}
			            BigDecimal calc = new BigDecimal("0");
			    		BigDecimal perTon = new BigDecimal("0");
			            BigDecimal perPound = new BigDecimal("0");
			            BigDecimal total = new BigDecimal("0");
			            BigDecimal totalPrice = new BigDecimal("0");
			            try
			            {
			               perTon = new BigDecimal(rfPayment.getFruitPriceHandKeyed());
			               perPound = perTon.divide(new BigDecimal("2000"), 4, BigDecimal.ROUND_HALF_UP); // 10/19/11 tw - change to 4 decimals
			               calc = paymentWeight.multiply(perPound);
			               totalPrice = new BigDecimal(HTMLHelpersMasking.maskNumber(calc.toString(), 2));
			               total = new BigDecimal(HTMLHelpersMasking.maskNumber(calc.toString(), 2));
			            }
			            catch(Exception e)
			            {
			            	System.out.println("Display Error" + e);
			            }
			            drfp.setFruitPricePerTon(HTMLHelpersMasking.maskBigDecimal(perTon.toString(), 2));
			            drfp.setFruitPricePerPound(HTMLHelpersMasking.maskBigDecimal(perPound.toString(), BigDecimal.ROUND_HALF_UP)); // 10/19/11 tw - change to 4 decimals
			            drfp.setFruitPriceTotal(HTMLHelpersMasking.mask2DecimalDollar(calc.toString()));
			            // Figure Out the Vectors for the special Charges
			            Vector listWithhold = new Vector();
			            Vector listCommission = new Vector();
			            Vector listOther = new Vector();
			            BigDecimal totalComm = new BigDecimal("0");
			            BigDecimal totalOther = new BigDecimal("0");
			            if (rfPayment.getListSpecialCharges().size() > 0)
			            {
			               for (int d = 0; d < rfPayment.getListSpecialCharges().size(); d++)
			               {
			               	  perTon = new BigDecimal("0");
			                  perPound = new BigDecimal("0");
			                  calc = new BigDecimal("0");
			                  RawFruitSpecialCharges rfsc = (RawFruitSpecialCharges) rfPayment.getListSpecialCharges().elementAt(d);
			                  int decimalsForDivision = 4; // tw 6/5/12 Changed to be 4 for the price and 5 decimal places for all else.  
			                  try
			                  {
			                     perTon   = new BigDecimal(rfsc.getRate());
			                    
			                     if (!rfsc.getAccountingOption().equals("30"))// tw 6/5/12 Changed to be 4 for the price and 5 decimal places for all else. 
			                    	decimalsForDivision = 5;// tw 6/5/12 Changed to be 4 for the price and 5 decimal places for all else. 
			                     
			                     perPound = perTon.divide(new BigDecimal("2000"), decimalsForDivision, BigDecimal.ROUND_HALF_UP); // 10/19/11 tw - change to 4 decimals
			                    // Did not want it Negative based on meeting from 1/7/09 
			                    // if (rfsc.getAccountingOption().equals("30"))
			                    //    perPound = perPound.multiply(new BigDecimal("-1"));
			                     calc = perPound.multiply(paymentWeight);   
			                  }
			                  catch(Exception e)
			                  {
			                    // Just Catch the Problem
			                  }  
			                    total = total.add(new BigDecimal(HTMLHelpersMasking.maskNumber(calc.toString(), 2)));
			                  DtlRawFruitSpecialCharges drfsc = new DtlRawFruitSpecialCharges();
			                  drfsc.setAccountingCode(rfsc.getAccountingOption());
			                  drfsc.setAccountingCodeDescription(rfsc.getAccountingOptionDescription());
			                  drfsc.setSupplier(rfsc.getSupplierSpecialCharges().getSupplierNumber());
			                  drfsc.setSupplierName(rfsc.getSupplierSpecialCharges().getSupplierName());
			                  drfsc.setPerTon(HTMLHelpersMasking.maskBigDecimal(perTon.toString(),2));
			                  drfsc.setPerPound(HTMLHelpersMasking.maskBigDecimal(perPound.toString(), decimalsForDivision)); // 10/19/11 tw - change to 4 decimals
			                  															// tw 6/5/12 Changed to be 4 for the price and 5 decimal places for all else. 
							  drfsc.setWeight(HTMLHelpersMasking.maskBigDecimal(paymentWeight.toString(),0));
							  drfsc.setTotalCost(HTMLHelpersMasking.maskNumber(calc.toString(), 2));                    
							   if (rfsc.getAccountingOption().equals("30"))
							   {	
							      listWithhold.addElement(drfsc);
							      totalPrice = totalPrice.add(new BigDecimal(drfsc.getTotalCost()));
							   }
							   else
							   {                   
			                     if (rfsc.getAccountingOption().equals("31") ||
			                         rfsc.getAccountingOption().equals("32") ||
			                         rfsc.getAccountingOption().equals("33") ||
			                         rfsc.getAccountingOption().equals("34") ||
			                         rfsc.getAccountingOption().equals("39") ||
			                         rfsc.getAccountingOption().equals("40") ||
			                         rfsc.getAccountingOption().equals("41"))
			                      {
			                     	listCommission.addElement(drfsc);
			                     	totalComm = totalComm.add(new BigDecimal(drfsc.getTotalCost()));
			                      } 
			                      else
			                      {
			                      	listOther.addElement(drfsc);
			                      	totalOther = totalOther.add(new BigDecimal(drfsc.getTotalCost()));
			                      }
			                  }
							  drfsc.setTotalCost(HTMLHelpersMasking.mask2DecimalDollar(drfsc.getTotalCost()));
			               }// End of the for Loop for Special Charges
			            } // end of the If there are Special Charges
			            drfp.setTotalCommission(HTMLHelpersMasking.mask2DecimalDollar(totalComm.toString()));
			            drfp.setTotalOtherCharges(HTMLHelpersMasking.mask2DecimalDollar(totalOther.toString()));
			            drfp.setTotalPrice(HTMLHelpersMasking.mask2DecimalDollar(totalPrice.toString()));
			            drfp.setTotalInvoiceAmount(HTMLHelpersMasking.mask2DecimalDollar(total.toString()));
			            drfp.setListWithhold(listWithhold);
			            drfp.setListCommission(listCommission);
			            drfp.setListOtherCharges(listOther);
			            this.listPayments.addElement(drfp);
			          }// End of the For Loop for Payments
			        }// End of IF there are Any Payments
			     }
			    } // end of the for loop -- lots
			   } // end of the IF lots
			 }// end of the for loop -- po's
		   } // end of the IF po's
		}
		catch(Exception e)
		{
			System.out.println("print error" + e);
		}
	return;
	}

	/**
	 * @return Returns the listPayments.
	 */
	public Vector getListPayments() {
		return listPayments;
	}
	/**
	 * @param listPayments The listPayments to set.
	 */
	public void setListPayments(Vector listPayments) {
		this.listPayments = listPayments;
	}
	public String getScaleTicketCorrectionSequence() {
		return scaleTicketCorrectionSequence;
	}
	public void setScaleTicketCorrectionSequence(
			String scaleTicketCorrectionSequence) {
		this.scaleTicketCorrectionSequence = scaleTicketCorrectionSequence;
	}
	/**
	 * Will Return the value Edited
	 */
	public static String maskField(String valueType,
								   String incomingValue) {
		StringBuffer sb = new StringBuffer();
		try
		{
		    if (valueType.equals("lot") &&
		    	incomingValue.length() == 6)
		    {
		    	sb.append(incomingValue.substring(0, 3));
		    	sb.append("&nbsp;");
		    	sb.append(incomingValue.substring(3, incomingValue.length()));
		    }
		    if ((valueType.equals("po") ||
		    	 valueType.equals("scaleTicket")) &&
		    	 incomingValue.length() == 7)
		    {
		    	sb.append(incomingValue.substring(0, 1));
		    	sb.append("&nbsp;");
		    	sb.append(incomingValue.substring(1, 4));
		    	sb.append("&nbsp;");
		    	sb.append(incomingValue.substring(4, incomingValue.length()));
		    }
		    if (valueType.equals("item") &&
		    	incomingValue.length() == 8)
		    {
		    	sb.append(incomingValue.substring(0, 5));
		    	sb.append("&nbsp;");
		    	sb.append(incomingValue.substring(5, incomingValue.length()));
		    }
		    if (valueType.equals("supplierInvoice"))
			{
		    	int findDash = incomingValue.indexOf("-");
		    	if (findDash == 0)
		    		sb.append(incomingValue);
		    	else
		    		sb.append(incomingValue.substring(0, findDash));
			}
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		if (sb.toString().equals(""))
			sb.append(incomingValue);
		return sb.toString();
	}
	public String getReceivingDate() {
		return receivingDate;
	}
	public void setReceivingDate(String receivingDate) {
		this.receivingDate = receivingDate;
	}
}