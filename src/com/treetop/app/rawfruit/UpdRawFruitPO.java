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
import com.treetop.businessobjects.RawFruitLoad;
import com.treetop.businessobjects.RawFruitPO;
import com.treetop.businessobjects.Supplier;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.services.ServiceSupplier;
import com.treetop.utilities.*;
import com.treetop.utilities.html.DropDownSingle;

/**
 * @author twalto
 * 
 */
public class UpdRawFruitPO extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	// Must have in Update View Bean
	public String updateUser = "";
	
	// Fields Available for Update
	public String scaleTicket = "";
	public String scaleTicketCorrectionSequence = "0";
	public String sequenceNumber = "";
	public String poNumber = "";
	public String poWarehouse = "";
	public String supplier = "";
	public String supplierError = "";
	
	public Vector listLots 	= new Vector();

	//Button Values
	public String addPO = "";
	public String updLoad = "";
	
	public RawFruitPO poInfo = new RawFruitPO();
		
	public Vector listReport = null;

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
	 * @return Returns the listReport.
	 */
	public Vector getListReport() {
		return listReport;
	}
	/**
	 * @param listReport The listReport to set.
	 */
	public void setListReport(Vector listReport) {
		this.listReport = listReport;
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
	 * @return Returns the addPO.
	 */
	public String getAddPO() {
		return addPO;
	}
	/**
	 * @param addPO The addPO to set.
	 */
	public void setAddPO(String addPO) {
		this.addPO = addPO;
	}
	/**
	 * @return Returns the updLoad.
	 */
	public String getUpdLoad() {
		return updLoad;
	}
	/**
	 * @param updLoad The updLoad to set.
	 */
	public void setUpdLoad(String updLoad) {
		this.updLoad = updLoad;
	}
	/**
	 * @return Returns the listLots.
	 */
	public Vector getListLots() {
		return listLots;
	}
	/**
	 * @param listLots The listLots to set.
	 */
	public void setListLots(Vector listLots) {
		this.listLots = listLots;
	}
	/**
	 * @return Returns the poNumber.
	 */
	public String getPoNumber() {
		return poNumber;
	}
	/**
	 * @param poNumber The poNumber to set.
	 */
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	/**
	 * @return Returns the sequenceNumber.
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return Returns the supplier.
	 */
	public String getSupplier() {
		return supplier;
	}
	/**
	 * @param supplier The supplier to set.
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	/**
	 * @return Returns the poWarehouse.
	 */
	public String getPoWarehouse() {
		return poWarehouse;
	}
	/**
	 * @param poWarehouse The poWarehouse to set.
	 */
	public void setPoWarehouse(String poWarehouse) {
		this.poWarehouse = poWarehouse;
	}
	/**
	 * @return Returns the supplierError.
	 */
	public String getSupplierError() {
		return supplierError;
	}
	/**
	 * @param supplierError The supplierError to set.
	 */
	public void setSupplierError(String supplierError) {
		this.supplierError = supplierError;
	}
	/**
	 * @return Returns the poInfo.
	 */
	public RawFruitPO getPoInfo() {
		return poInfo;
	}
	/**
	 * @param poInfo The poInfo to set.
	 */
	public void setPoInfo(RawFruitPO poInfo) {
		this.poInfo = poInfo;
	}
	public String getScaleTicketCorrectionSequence() {
		return scaleTicketCorrectionSequence;
	}
	public void setScaleTicketCorrectionSequence(
			String scaleTicketCorrectionSequence) {
		this.scaleTicketCorrectionSequence = scaleTicketCorrectionSequence;
	}
}
