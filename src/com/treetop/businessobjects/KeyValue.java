/*
 * Created on Nov 15, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.businessobjects;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

/**
 * @author thaile
 *
 * Hold values loaded by key entries. A Generic Value
 * object to store values and descriptions by multiple 
 * keys.
 */
public class KeyValue {
	
	String	    environment		= "";
	// class fields.
	
	String 		status;				//GMASTS
	String		entryType;			//GMATYP
	String		sequence;			//GMASEQ
	String		key1;				//GMAKY1
	String		key2;				//GMAKY2
	String		key3;				//GMAKY3
	String		key4;				//GMAKY4
	String		key5;				//GMAKY5
	String		uniqueKey;			//GMAUNQ
	String		value;				//GMAVAL
	String		description;		//GMADSC
	String		lastUpdateDate;		//GMALDT
	String		lastUpdateTime;		//GMALTM
	String		lastUpdateUser;		//GMALUR
	String		deleteDate;			//GMADDT
	String		deleteTime;			//GMADTM
	String		deleteUser;			//GMADUR
	
	String      orderBy;
	String		orderStyle;
	
	boolean		viewOnly			= true;
	boolean		managedByTT			= false;
	boolean		visibleOnLoad		= false;
	boolean		sequenced			= false;
	boolean		descriptionAsHeader = false;
	String		headerText			= "Comments";

	/**
	 * Simple constructor.
	 */
	public KeyValue() {
		super();
	}
	
	public KeyValue(HttpServletRequest request) {
		
		for (Field field : this.getClass().getDeclaredFields()) {
			String fieldName = field.getName();
			String value = "";
			try {
				value = request.getParameter(fieldName);
				if (value == null) {
					value = "";
				}
			} catch (Exception e) {
				//didn't find it, that's A-OK
			}
			try {
				
				if (field.getType().equals(boolean.class)) {
					field.set(this, Boolean.parseBoolean(value));
				} else {
					field.set(this, value);					
				}
				

				
			} catch (Exception e) {
				
			}
		}
		
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return
	 */
	public String getKey1() {
		return key1;
	}

	/**
	 * @return
	 */
	public String getKey2() {
		return key2;
	}

	/**
	 * @return
	 */
	public String getKey3() {
		return key3;
	}

	/**
	 * @return
	 */
	public String getKey4() {
		return key4;
	}

	/**
	 * @return
	 */
	public String getKey5() {
		return key5;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
	}

	/**
	 * @param string
	 */
	public void setKey1(String string) {
		key1 = string;
	}

	/**
	 * @param string
	 */
	public void setKey2(String string) {
		key2 = string;
	}

	/**
	 * @param string
	 */
	public void setKey3(String string) {
		key3 = string;
	}

	/**
	 * @param string
	 */
	public void setKey4(String string) {
		key4 = string;
	}

	/**
	 * @param string
	 */
	public void setKey5(String string) {
		key5 = string;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		status = string;
	}

	/**
	 * @param string
	 */
	public void setValue(String string) {
		value = string;
	}

	/**
	 * @return
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param string
	 */
	public void setOrderBy(String string) {
		orderBy = string;
	}

	/**
	 * @return
	 */
	public String getOrderStyle() {
		return orderStyle;
	}

	/**
	 * @param string
	 */
	public void setOrderStyle(String string) {
		orderStyle = string;
	}

	/**
	 * @return
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * @param string
	 */
	public void setSequence(String string) {
		sequence = string;
	}

	/**
	 * @return
	 */
	public String getDeleteDate() {
		return deleteDate;
	}

	/**
	 * @return
	 */
	public String getDeleteTime() {
		return deleteTime;
	}

	/**
	 * @return
	 */
	public String getDeleteUser() {
		return deleteUser;
	}

	/**
	 * @return
	 */
	public String getEntryType() {
		return entryType;
	}

	/**
	 * @return
	 */
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @return
	 */
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @return
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @return
	 */
	public String getUniqueKey() {
		return uniqueKey;
	}

	/**
	 * @param string
	 */
	public void setDeleteDate(String string) {
		deleteDate = string;
	}

	/**
	 * @param string
	 */
	public void setDeleteTime(String string) {
		deleteTime = string;
	}

	/**
	 * @param string
	 */
	public void setDeleteUser(String string) {
		deleteUser = string;
	}

	/**
	 * @param string
	 */
	public void setEntryType(String string) {
		entryType = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateDate(String string) {
		lastUpdateDate = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateTime(String string) {
		lastUpdateTime = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateUser(String string) {
		lastUpdateUser = string;
	}

	/**
	 * @param string
	 */
	public void setUniqueKey(String string) {
		uniqueKey = string;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public boolean isViewOnly() {
		return viewOnly;
	}

	public void setViewOnly(boolean viewOnly) {
		this.viewOnly = viewOnly;
	}

	public boolean isVisibleOnLoad() {
		return visibleOnLoad;
	}

	public void setVisibleOnLoad(boolean visibleOnLoad) {
		this.visibleOnLoad = visibleOnLoad;
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

	public boolean isManagedByTT() {
		return managedByTT;
	}

	public void setManagedByTT(boolean managedByTT) {
		this.managedByTT = managedByTT;
	}

	public boolean isSequenced() {
		return sequenced;
	}

	public void setSequenced(boolean sequenced) {
		this.sequenced = sequenced;
	}

	public boolean isDescriptionAsHeader() {
		return descriptionAsHeader;
	}

	public void setDescriptionAsHeader(boolean descriptionAsHeader) {
		this.descriptionAsHeader = descriptionAsHeader;
	}

}
