/*
 * Created on November 5, 2008
 */

package com.treetop.app.rawfruit;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanRawFruit;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.RawFruitBin;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.utilities.*;

/**
 * @author twalto
 * 
 */
public class UpdRawFruitBins extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	// Must have in Update View Bean
	public String updateUser = "";
	
	// Fields Available for Update
	public String scaleTicket = "";
	public String scaleTicketCorrectionSequence = "0";
	public String binType = "";
	public String binTypeError = "";
	public String numberOfBins = "";
	public String numberOfBinsError = "";
	
	public RawFruitBin binInfo = new RawFruitBin();
	
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
	 * @return Returns the binType.
	 */
	public String getBinType() {
		return binType;
	}
	/**
	 * @param binType The binType to set.
	 */
	public void setBinType(String binType) {
		this.binType = binType;
	}
	/**
	 * @return Returns the binTypeError.
	 */
	public String getBinTypeError() {
		return binTypeError;
	}
	/**
	 * @param binTypeError The binTypeError to set.
	 */
	public void setBinTypeError(String binTypeError) {
		this.binTypeError = binTypeError;
	}
	/**
	 * @return Returns the numberOfBins.
	 */
	public String getNumberOfBins() {
		return numberOfBins;
	}
	/**
	 * @param numberOfBins The numberOfBins to set.
	 */
	public void setNumberOfBins(String numberOfBins) {
		this.numberOfBins = numberOfBins;
	}
	/**
	 * @return Returns the binInfo.
	 */
	public RawFruitBin getBinInfo() {
		return binInfo;
	}
	/**
	 * @param binInfo The binInfo to set.
	 */
	public void setBinInfo(RawFruitBin binInfo) {
		this.binInfo = binInfo;
	}
	/**
	 * @return Returns the numberOfBinsError.
	 */
	public String getNumberOfBinsError() {
		return numberOfBinsError;
	}
	/**
	 * @param numberOfBinsError The numberOfBinsError to set.
	 */
	public void setNumberOfBinsError(String numberOfBinsError) {
		this.numberOfBinsError = numberOfBinsError;
	}
	public String getScaleTicketCorrectionSequence() {
		return scaleTicketCorrectionSequence;
	}
	public void setScaleTicketCorrectionSequence(
			String scaleTicketCorrectionSequence) {
		this.scaleTicketCorrectionSequence = scaleTicketCorrectionSequence;
	}
}
