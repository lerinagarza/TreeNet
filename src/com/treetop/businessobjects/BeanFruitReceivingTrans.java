/*
 * Created on Jan 31, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.businessobjects;

import java.util.*;

/**
 * @author thaile
 *
 * Container class to hold Grower Fruit Receiving 
 * Transactions Screen Application data.
 * 
 */
public class BeanFruitReceivingTrans {
	
	private 	FruitReceivingTicketTrans		trans; 
	private		FruitReceivingTicketDetail		detail;
	private		Resource						resource;
	private		Carrier							carrier;
	
	private		Vector			payments;	//FrutiReceivingPaymentTrans
	private		Vector			paymentsGLAccount;	//GL Account info for payemnt account
	private		Vector			bins;		//FruitReceivingBinTrans
	
	// extra line

	/**
	 * 
	 */
	public BeanFruitReceivingTrans() {
		super();
	}

	/**
	 * @return
	 */
	public FruitReceivingTicketTrans getTrans() {
		return trans;
	}

	/**
	 * @param resource
	 */
	public void setTrans(FruitReceivingTicketTrans transIn) {
		trans = transIn;
	}

	/**
	 * @return
	 */
	public Vector getPayments() {
		return payments;
	}

	/**
	 * @return
	 */
	public Vector getBins() {
		return bins;
	}

	/**
	 * @param vector
	 */
	public void setBins(Vector vector) {
		bins = vector;
	}

	/**
	 * @return
	 */
	public FruitReceivingTicketDetail getDetail() {
		return detail;
	}
	/**
	 * @param detail
	 */
	public void setDetail(FruitReceivingTicketDetail detailIn) {
		detail = detailIn;
	}
	/**
	 * @param payments The payments to set.
	 */
	public void setPayments(Vector payments) {
		this.payments = payments;
	}

	/**
	 * @return Returns the carrier.
	 */
	public Carrier getCarrier() {
		return carrier;
	}
	/**
	 * @param carrier The carrier to set.
	 */
	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}
	/**
	 * @return Returns the paymentsGLAccount.
	 */
	public Vector getPaymentsGLAccount() {
		return paymentsGLAccount;
	}
	/**
	 * @param paymentsGLAccount The paymentsGLAccount to set.
	 */
	public void setPaymentsGLAccount(Vector paymentsGLAccount) {
		this.paymentsGLAccount = paymentsGLAccount;
	}
	/**
	 * @return Returns the resource.
	 */
	public Resource getResource() {
		return resource;
	}
	/**
	 * @param resource The resource to set.
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
