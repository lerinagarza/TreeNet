package com.treetop.controller.sublot;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.treetop.businessobjectapplications.BeanPeach;
import com.treetop.businessobjects.KeyValue;
import com.treetop.controller.BaseViewBeanR4;
import com.treetop.controller.UrlPathMapping;

@UrlPathMapping({"requestType","id","parameter"})
public class DtlSubLot extends BaseViewBeanR4 {
	
	private String itemNumber		= "";
	private String lotNumber		= "";
	
	public Vector<KeyValue> listComments 		= new Vector<KeyValue>(); 
	
	private BeanPeach beanPeach 	= new BeanPeach();
	
	@Override
	public void validate() {
			// TODO validate Item Number
	}
	
	/**
	 * Default Constructor
	 */
	public DtlSubLot() {
	
	}
	
	/**
	 * Constructor with auto-populate
	 * @param request
	 */
	public DtlSubLot(HttpServletRequest request) {
		this.populate(request);

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

	public Vector<KeyValue> getListComments() {
		return listComments;
	}

	public void setListComments(Vector<KeyValue> listComments) {
		this.listComments = listComments;
	}

	public BeanPeach getBeanPeach() {
		return beanPeach;
	}

	public void setBeanPeach(BeanPeach beanPeach) {
		this.beanPeach = beanPeach;
	}	
	
}
