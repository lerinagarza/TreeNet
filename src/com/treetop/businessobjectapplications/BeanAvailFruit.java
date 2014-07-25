/*
 * Created on July 19, 2010
 *
 */
package com.treetop.businessobjectapplications;

import com.treetop.businessobjects.*;
import java.util.*;

/**
 * @author thaile
 *
 * Bean to combine everything to send to the servlet
 */
public class BeanAvailFruit {
	
	protected   AvailFruitByWhse availFruitByWhse = new AvailFruitByWhse();	

	protected	Vector	availFruitByWhseDetail    = new Vector(); // class AvailFruitbyWhseDetail.
	
	protected   Vector  scheduledLoadDetail       = new Vector(); // class ScheduledLoadDetail.
	
	protected   String  throwError 	      		  = ""; 		  // the throw error from class objects - abnormal termination.
	
	protected   String  environment       		  = new String();
	
	protected   String  statusMessage     		  = new String();
	
	/**
	 *  // Constructor
	 */
	public BeanAvailFruit() {
		super();

	}



	public AvailFruitByWhse getAvailFruitByWhse() {
		return availFruitByWhse;
	}



	public void setAvailFruitByWhse(AvailFruitByWhse availFruitByWhse) {
		this.availFruitByWhse = availFruitByWhse;
	}



	public Vector getAvailFruitByWhseDetail() {
		return availFruitByWhseDetail;
	}



	public void setAvailFruitByWhseDetail(Vector availFruitByWhseDetail) {
		this.availFruitByWhseDetail = availFruitByWhseDetail;
	}



	public String getThrowError() {
		return throwError;
	}

	public void setThrowError(String throwError) {
		this.throwError = throwError;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}



	public Vector getScheduledLoadDetail() {
		return scheduledLoadDetail;
	}



	public void setScheduledLoadDetail(Vector scheduledLoadDetail) {
		this.scheduledLoadDetail = scheduledLoadDetail;
	}
	

}
