package com.treetop.controller.sublot;

import java.util.Vector;

import com.treetop.businessobjects.DateTime;
import com.treetop.controller.BaseViewBeanR4;
import com.treetop.utilities.UtilityDateTime;

public class UpdSubLotDetail extends BaseViewBeanR4 {

	private String receivingWarehouseNumber			= "";
	private String itemNumber						= "";
	private String lotNumber						= "";
	
	private String unitNumber						= "";
	private String growerCode						= "";
	private String growerName						= "";
	
	@Override
	public void validate() {
		// TODO validate Grower 
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

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getGrowerCode() {
		return growerCode;
	}

	public void setGrowerCode(String growerCode) {
		this.growerCode = growerCode;
	}

	public String getGrowerName() {
		return growerName;
	}

	public void setGrowerName(String growerName) {
		this.growerName = growerName;
	}	
	
}
