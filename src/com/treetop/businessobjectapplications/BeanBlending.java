package com.treetop.businessobjectapplications;

import java.util.Vector;

import com.treetop.businessobjects.ManufacturingOrder;

public class BeanBlending {
	
	private Vector<ManufacturingOrder> listProduction = new Vector<ManufacturingOrder>();

	public Vector<ManufacturingOrder> getListProduction() {
		return listProduction;
	}

	public void setListProduction(Vector<ManufacturingOrder> listProduction) {
		this.listProduction = listProduction;
	}

}
