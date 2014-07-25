/*
 * Created on Feb 17, 2006
 *
 */
package com.treetop.businessobjects;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author thaile
 *
 * Resource information (number,description,etc)
 */
public class AccountData {
	
	private	String		dim1			= "";
	private String 		dim1Description	= "";
	private String 	    dim2			= "";
	private String 	    dim2Description	= "";
	private String 	    dim3			= "";
	private String 	    dim3Description	= "";
	private String 	    dim4			= "";
	private String 	    dim4Description	= "";
	private String 	    dim5			= "";
	private String 	    dim5Description	= "";
	private String 	    dim6			= "";
	private String 	    dim7			= "";
	private	String		entryText    	= "";
	private String		accountingDate  = "";
	private	String		transactionDate	= "";
	private	BigDecimal	amount1         = BigDecimal.ZERO;
	private BigDecimal  quantity1       = BigDecimal.ZERO;


	public String getDim1() {
		return dim1;
	}


	public void setDim1(String dim1) {
		this.dim1 = dim1;
	}


	public String getDim2() {
		return dim2;
	}


	public void setDim2(String dim2) {
		this.dim2 = dim2;
	}


	public String getDim3() {
		return dim3;
	}


	public void setDim3(String dim3) {
		this.dim3 = dim3;
	}


	public String getDim4() {
		return dim4;
	}


	public void setDim4(String dim4) {
		this.dim4 = dim4;
	}


	public String getDim5() {
		return dim5;
	}


	public void setDim5(String dim5) {
		this.dim5 = dim5;
	}


	public String getDim6() {
		return dim6;
	}


	public void setDim6(String dim6) {
		this.dim6 = dim6;
	}


	public String getDim7() {
		return dim7;
	}


	public void setDim7(String dim7) {
		this.dim7 = dim7;
	}


	public String getEntryText() {
		return entryText;
	}


	public void setEntryText(String entryText) {
		this.entryText = entryText;
	}


	public String getAccountingDate() {
		return accountingDate;
	}


	public void setAccountingDate(String accountingDate) {
		this.accountingDate = accountingDate;
	}


	public String getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}


	public BigDecimal getAmount1() {
		return amount1;
	}


	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}


	public BigDecimal getQuantity1() {
		return quantity1;
	}


	public void setQuantity1(BigDecimal quantiry1) {
		this.quantity1 = quantiry1;
	}


	public String getDim1Description() {
		return dim1Description;
	}


	public void setDim1Description(String dim1Description) {
		this.dim1Description = dim1Description;
	}


	public String getDim2Description() {
		return dim2Description;
	}


	public void setDim2Description(String dim2Description) {
		this.dim2Description = dim2Description;
	}


	public String getDim3Description() {
		return dim3Description;
	}


	public void setDim3Description(String dim3Description) {
		this.dim3Description = dim3Description;
	}


	public String getDim4Description() {
		return dim4Description;
	}


	public void setDim4Description(String dim4Description) {
		this.dim4Description = dim4Description;
	}


	public String getDim5Description() {
		return dim5Description;
	}


	public void setDim5Description(String dim5Description) {
		this.dim5Description = dim5Description;
	}


	
}

