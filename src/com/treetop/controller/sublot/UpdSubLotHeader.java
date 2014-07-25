package com.treetop.controller.sublot;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.app.quality.UpdFormulaDetail;
import com.treetop.app.quality.UpdTestParameters;
import com.treetop.app.quality.UpdVariety;
import com.treetop.businessobjectapplications.BeanPeach;
import com.treetop.businessobjectapplications.BeanQuality;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.PeachTicket;
import com.treetop.businessobjects.TicketDetail;
import com.treetop.businessobjects.QaFormula;
import com.treetop.businessobjects.QaFormulaDetail;
import com.treetop.businessobjects.QaFruitVariety;
import com.treetop.businessobjects.QaTestParameters;
import com.treetop.controller.BaseViewBeanR4;
import com.treetop.controller.UrlPathMapping;
import com.treetop.services.ServiceDescriptiveCode;
import com.treetop.utilities.UtilityDateTime;

@UrlPathMapping({"requestType","id","parameter"})
public class UpdSubLotHeader extends BaseViewBeanR4 {
	
	private String receivingDate					= "";
	private String receivingDateError				= "";
	private String receivingTime					= ""; 
	private String updateUser						= "";
	private String submitButton						= "";
	
	private String receivingWarehouseNumber			= "";
	private String itemNumber						= "";
	private String lotNumber						= "";
	private String supplierNumber					= ""; //M3Supplier
	private String loadNumber						= "";
	private String receivingUser					= "";
	
	private String comment							= "";
	
	private int    unitCount						= 52;
	
	private	Vector<UpdSubLotDetail>	listLotUnits    = new Vector<UpdSubLotDetail>();
	
	private BeanPeach beanPeach 					= new BeanPeach();
	
	/**
	 * Default Constructor
	 */
	public UpdSubLotHeader() {
	
	}
	
	/**
	 * Constructor with auto-populate
	 * @param request
	 */
	public UpdSubLotHeader(HttpServletRequest request) {
		this.populate(request);
		try{ // get user information
			this.setUpdateUser(SessionVariables.getSessionttiProfile(request));
			if (this.getReceivingUser().trim().equals(""))
				this.setReceivingUser(this.getUpdateUser());
			if (this.getEnvironment().trim().equals(""))
				this.setEnvironment("PRD");
			DateTime dt = UtilityDateTime.getSystemDate();
			this.setReceivingDate(dt.getDateFormatyyyyMMdd());
			this.setReceivingTime(dt.getTimeFormathhmmss());
			
		}catch(Exception e){
			this.setErrorMessage(e.toString());
		}
		
		if (!this.getSubmitButton().trim().equals(""))
		{
			this.setListLotUnits(new Vector<UpdSubLotDetail>());
			for (int x = 0; x < this.unitCount; x++)
			{
				try{
					UpdSubLotDetail usld = new UpdSubLotDetail();
					usld.setEnvironment(this.getEnvironment());
					usld.setItemNumber(this.getItemNumber());
					usld.setLotNumber(this.getLotNumber());
					usld.setUnitNumber((new Integer(x + 1)).toString());
					usld.setGrowerName(request.getParameter("grower" + x));
					if (usld.getGrowerName() != null &&
						!usld.getGrowerName().trim().equals(""))
					{
						usld.setGrowerCode(ServiceDescriptiveCode.getPeachGrowerNameKey(this.getEnvironment(), usld.getGrowerName()));
						this.listLotUnits.addElement(usld);
					}
				}catch(Exception e)
				{
					this.setErrorMessage(e.toString());
				}
			}
		}
	}
	
	@Override
	public void validate() {
		if (!this.getSubmitButton().trim().equals(""))
		{
			// TODO validate Item Number
			// TODO validate Supplier Number
			// TODO validate Receiving User -- must be a Valid User
			
			DateTime dt = UtilityDateTime.getSystemDate();
			this.setReceivingDate(dt.getDateFormatyyyyMMdd());
			
		}
	}

	public String getReceivingDate() {
		return receivingDate;
	}

	public void setReceivingDate(String receivingDate) {
		this.receivingDate = receivingDate;
	}

	public String getReceivingDateError() {
		return receivingDateError;
	}

	public void setReceivingDateError(String receivingDateError) {
		this.receivingDateError = receivingDateError;
	}

	public String getReceivingTime() {
		return receivingTime;
	}

	public void setReceivingTime(String receivingTime) {
		this.receivingTime = receivingTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getReceivingWarehouseNumber() {
		return receivingWarehouseNumber;
	}

	public void setReceivingWarehouseNumber(String receivingWarehouseNumber) {
		this.receivingWarehouseNumber = receivingWarehouseNumber;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getLoadNumber() {
		return loadNumber;
	}

	public void setLoadNumber(String loadNumber) {
		this.loadNumber = loadNumber;
	}

	public String getReceivingUser() {
		return receivingUser;
	}

	public void setReceivingUser(String receivingUser) {
		this.receivingUser = receivingUser;
	}

	public String getSubmitButton() {
		return submitButton;
	}

	public void setSubmitButton(String submitButton) {
		this.submitButton = submitButton;
	}

	public BeanPeach getBeanPeach() {
		return beanPeach;
	}

	public void setBeanPeach(BeanPeach beanPeach) {
		this.beanPeach = beanPeach;
	}

	public int getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(int unitCount) {
		this.unitCount = unitCount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/*
	 * Send in a Business Object Use the fields from this Object to set into
	 * a View Bean for Update 
	 *    Creation date: (5/15/2013 TWalton)
	 */
	public void loadFromBeanPeach(BeanPeach bp) {
		try {
		    this.beanPeach						= bp;
		    PeachTicket pt = this.beanPeach.getTicket();
		    
		    this.itemNumber = pt.getItemNumber();
		    this.lotNumber = pt.getLotNumber();
		    this.loadNumber = pt.getLoadNumber();
		    this.supplierNumber = pt.getSupplierNumber();
		    this.receivingUser = pt.getReceivingUser();

		    //  DETAILS //
			  Vector<UpdSubLotDetail> details = new Vector<UpdSubLotDetail>();
			  if (!pt.getTagDetail().isEmpty()){
				 for (int x = 0; x < pt.getTagDetail().size(); x++){
					UpdSubLotDetail updDetail = new UpdSubLotDetail();
					try{
						TicketDetail td =  (TicketDetail) pt.getTagDetail().elementAt(x);
						updDetail.setItemNumber(this.itemNumber);
						updDetail.setLotNumber(this.lotNumber);
						updDetail.setUnitNumber(td.getTagNumber());
						updDetail.setGrowerName(td.getGrowerName());
					}catch(Exception e)
					{}
					details.addElement(updDetail);
				 }
			  }
			  this.setListLotUnits(details);
				
			} catch (Exception e) {
			   System.out.println("Error Caught in UpdSubLotHeader.loadFromBeanPeach(BeanPeach: " + e);
			}
			return;
		}

	public Vector<UpdSubLotDetail> getListLotUnits() {
		return listLotUnits;
	}

	public void setListLotUnits(Vector<UpdSubLotDetail> listLotUnits) {
		this.listLotUnits = listLotUnits;
	}

}
