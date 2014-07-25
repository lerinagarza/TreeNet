/*
 * Created on Jul 26, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.app.gtin;

import java.util.List;

import com.treetop.viewbeans.BaseViewBean;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;

/**
 * @author twalto
 * 
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DtlGTIN extends BaseViewBean{
	// Standard Fields - to be in Every View Bean
	public String requestType       = "";
	public List   errors            = null;
	// Fields from the JSP to Request Information
	public String gtinNumber = "";
	public BeanGTIN detailClass = null;
	
	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public List validate() {
		
		return null;
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
	 * @return
	 */
	public String getGtinNumber() {
		return gtinNumber;
	}

	/**
	 * @param string
	 */
	public void setGtinNumber(String string) {
		gtinNumber = string;
	}

	/**
	 * @return
	 */
	public BeanGTIN getDetailClass() {
		return detailClass;
	}

	/**
	 * @param beanGTIN
	 */
	public void setDetailClass(BeanGTIN beanGTIN) {
		detailClass = beanGTIN;
	}

}
