/*
 * Created on October 9, 2007
 *
 */
package com.treetop.businessobjectapplications;

import java.util.*;

/**
 * @author twalto
 *
 * Store Attribute Related Information --
 *     Could include Attribute Model Information Also
 */
public class COA {

	protected   String		coaType					= "";
	protected	String		orderNumber				= "";
	protected	String		lotNumber				= "";
	
	protected	String		email1					= "";
	protected	String		email2					= "";
	protected	String		email3					= "";
	protected	String		email4					= "";
	protected	String		email5					= "";
	protected	String		email6					= "";
	protected	String		email7					= "";
	protected	String		email8					= "";
	protected	String		attention1				= "";
	protected	String		attention2				= "";
	protected	String		attention3				= "";
	protected	String		attention4				= "";
	protected	String		fax1					= "";
	protected	String		fax2					= "";
	protected	String		fax3					= "";
	protected	String		fax4					= "";
	protected	String		signatureQA				= "";
	protected	String		dateFormat				= "";
	protected	String		countryOfOrigin			= "";
	protected	String		showAverage				= "";
	protected	String		showUnits				= "";
	protected	String		showMinMax				= "";
	protected	String		showTarget				= "";
	protected	String		showSpec				= "";
	protected   String      showAttributeModel		= "";
	protected   String      comment					= "";
	
	/**
	 *  // Constructor
	 */
	public COA() {
		super();

	}
	/**
	 * @return Returns the attention1.
	 */
	public String getAttention1() {
		return attention1;
	}
	/**
	 * @param attention1 The attention1 to set.
	 */
	public void setAttention1(String attention1) {
		this.attention1 = attention1;
	}
	/**
	 * @return Returns the attention2.
	 */
	public String getAttention2() {
		return attention2;
	}
	/**
	 * @param attention2 The attention2 to set.
	 */
	public void setAttention2(String attention2) {
		this.attention2 = attention2;
	}
	/**
	 * @return Returns the attention3.
	 */
	public String getAttention3() {
		return attention3;
	}
	/**
	 * @param attention3 The attention3 to set.
	 */
	public void setAttention3(String attention3) {
		this.attention3 = attention3;
	}
	/**
	 * @return Returns the attention4.
	 */
	public String getAttention4() {
		return attention4;
	}
	/**
	 * @param attention4 The attention4 to set.
	 */
	public void setAttention4(String attention4) {
		this.attention4 = attention4;
	}
	/**
	 * @return Returns the countryOfOrigin.
	 */
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}
	/**
	 * @param countryOfOrigin The countryOfOrigin to set.
	 */
	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}
	/**
	 * @return Returns the dateFormat.
	 */
	public String getDateFormat() {
		return dateFormat;
	}
	/**
	 * @param dateFormat The dateFormat to set.
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	/**
	 * @return Returns the email1.
	 */
	public String getEmail1() {
		return email1;
	}
	/**
	 * @param email1 The email1 to set.
	 */
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	/**
	 * @return Returns the email2.
	 */
	public String getEmail2() {
		return email2;
	}
	/**
	 * @param email2 The email2 to set.
	 */
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	/**
	 * @return Returns the email3.
	 */
	public String getEmail3() {
		return email3;
	}
	/**
	 * @param email3 The email3 to set.
	 */
	public void setEmail3(String email3) {
		this.email3 = email3;
	}
	/**
	 * @return Returns the email4.
	 */
	public String getEmail4() {
		return email4;
	}
	/**
	 * @param email4 The email4 to set.
	 */
	public void setEmail4(String email4) {
		this.email4 = email4;
	}
	/**
	 * @return Returns the email5.
	 */
	public String getEmail5() {
		return email5;
	}
	/**
	 * @param email5 The email5 to set.
	 */
	public void setEmail5(String email5) {
		this.email5 = email5;
	}
	/**
	 * @return Returns the email6.
	 */
	public String getEmail6() {
		return email6;
	}
	/**
	 * @param email6 The email6 to set.
	 */
	public void setEmail6(String email6) {
		this.email6 = email6;
	}
	/**
	 * @return Returns the email7.
	 */
	public String getEmail7() {
		return email7;
	}
	/**
	 * @param email7 The email7 to set.
	 */
	public void setEmail7(String email7) {
		this.email7 = email7;
	}
	/**
	 * @return Returns the email8.
	 */
	public String getEmail8() {
		return email8;
	}
	/**
	 * @param email8 The email8 to set.
	 */
	public void setEmail8(String email8) {
		this.email8 = email8;
	}
	/**
	 * @return Returns the fax1.
	 */
	public String getFax1() {
		return fax1;
	}
	/**
	 * @param fax1 The fax1 to set.
	 */
	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}
	/**
	 * @return Returns the fax2.
	 */
	public String getFax2() {
		return fax2;
	}
	/**
	 * @param fax2 The fax2 to set.
	 */
	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}
	/**
	 * @return Returns the fax3.
	 */
	public String getFax3() {
		return fax3;
	}
	/**
	 * @param fax3 The fax3 to set.
	 */
	public void setFax3(String fax3) {
		this.fax3 = fax3;
	}
	/**
	 * @return Returns the fax4.
	 */
	public String getFax4() {
		return fax4;
	}
	/**
	 * @param fax4 The fax4 to set.
	 */
	public void setFax4(String fax4) {
		this.fax4 = fax4;
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
	 * @return Returns the orderNumber.
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber The orderNumber to set.
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return Returns the showAverage.
	 */
	public String getShowAverage() {
		return showAverage;
	}
	/**
	 * @param showAverage The showAverage to set.
	 */
	public void setShowAverage(String showAverage) {
		this.showAverage = showAverage;
	}
	/**
	 * @return Returns the showMinMax.
	 */
	public String getShowMinMax() {
		return showMinMax;
	}
	/**
	 * @param showMinMax The showMinMax to set.
	 */
	public void setShowMinMax(String showMinMax) {
		this.showMinMax = showMinMax;
	}
	/**
	 * @return Returns the showSpec.
	 */
	public String getShowSpec() {
		return showSpec;
	}
	/**
	 * @param showSpec The showSpec to set.
	 */
	public void setShowSpec(String showSpec) {
		this.showSpec = showSpec;
	}
	/**
	 * @return Returns the showTarget.
	 */
	public String getShowTarget() {
		return showTarget;
	}
	/**
	 * @param showTarget The showTarget to set.
	 */
	public void setShowTarget(String showTarget) {
		this.showTarget = showTarget;
	}
	/**
	 * @return Returns the showUnits.
	 */
	public String getShowUnits() {
		return showUnits;
	}
	/**
	 * @param showUnits The showUnits to set.
	 */
	public void setShowUnits(String showUnits) {
		this.showUnits = showUnits;
	}
	/**
	 * @return Returns the signatureQA.
	 */
	public String getSignatureQA() {
		return signatureQA;
	}
	/**
	 * @param signatureQA The signatureQA to set.
	 */
	public void setSignatureQA(String signatureQA) {
		this.signatureQA = signatureQA;
	}
	/**
	 * @return Returns the comment.
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return Returns the coaType.
	 */
	public String getCoaType() {
		return coaType;
	}
	/**
	 * @param coaType The coaType to set.
	 */
	public void setCoaType(String coaType) {
		this.coaType = coaType;
	}
	/**
	 * @return Returns the showAttributeModel.
	 */
	public String getShowAttributeModel() {
		return showAttributeModel;
	}
	/**
	 * @param showAttributeModel The showAttributeModel to set.
	 */
	public void setShowAttributeModel(String showAttributeModel) {
		this.showAttributeModel = showAttributeModel;
	}
}
