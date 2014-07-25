/*
 * Created on September 18, 2012
 *
 */
package com.treetop.businessobjects;

import java.util.*;
import java.math.*;


/**
 * @author thaile
 * 
 * Manufacturing Finance Information
 */

/* modified
 * 
 */
public class ManufacturingFinance {
	
	private String		companyNo					= "";
	private String      division                    = "";
	private	String   	warehouse					= "";
	
	private	BigDecimal	wtdEarnings			        = BigDecimal.ZERO;
	private BigDecimal  mtdEarnings                 = BigDecimal.ZERO;
	private BigDecimal  ytdEarnings                 = BigDecimal.ZERO;
	
	private	BigDecimal	wtdActual				    = BigDecimal.ZERO;
	private BigDecimal  mtdActual	                = BigDecimal.ZERO;
	private BigDecimal  ytdActual                   = BigDecimal.ZERO;
	
	private	BigDecimal	wtdPlan          			= BigDecimal.ZERO;
	private BigDecimal  mtdPlan                     = BigDecimal.ZERO;
	private BigDecimal  ytdPlan                     = BigDecimal.ZERO;
	
	private	BigDecimal	wtdEarningsVar              = BigDecimal.ZERO;
	private	BigDecimal	mtdEarningsVar              = BigDecimal.ZERO;
	private	BigDecimal	ytdEarningsVar              = BigDecimal.ZERO;
	
	private	BigDecimal	wtdPlanVar                  = BigDecimal.ZERO;
	private	BigDecimal	mtdPlanVar                  = BigDecimal.ZERO;
	private	BigDecimal	ytdPlanVar                  = BigDecimal.ZERO;
	
	
	private BigDecimal wtdUnits						= BigDecimal.ZERO;
	private BigDecimal mtdUnits						= BigDecimal.ZERO;
	private BigDecimal ytdUnits						= BigDecimal.ZERO;
	
	
	
	/**
	 *  // Constructor
	 */
	public ManufacturingFinance() {
		super();

	}

	public String getCompanyNo() {
		return companyNo;
	}


	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public BigDecimal getWtdEarnings() {
		return wtdEarnings;
	}

	public void setWtdEarnings(BigDecimal wtdEarnings) {
		this.wtdEarnings = wtdEarnings;
	}

	public BigDecimal getMtdEarnings() {
		return mtdEarnings;
	}

	public void setMtdEarnings(BigDecimal mtdEarnings) {
		this.mtdEarnings = mtdEarnings;
	}

	public BigDecimal getYtdEarnings() {
		return ytdEarnings;
	}

	public void setYtdEarnings(BigDecimal ytdEarnings) {
		this.ytdEarnings = ytdEarnings;
	}

	public BigDecimal getWtdActual() {
		return wtdActual;
	}

	public void setWtdActual(BigDecimal wtdActual) {
		this.wtdActual = wtdActual;
	}

	public BigDecimal getMtdActual() {
		return mtdActual;
	}

	public void setMtdActual(BigDecimal mtdActual) {
		this.mtdActual = mtdActual;
	}

	public BigDecimal getYtdActual() {
		return ytdActual;
	}

	public void setYtdActual(BigDecimal ytdActual) {
		this.ytdActual = ytdActual;
	}

	public BigDecimal getWtdPlan() {
		return wtdPlan;
	}

	public void setWtdPlan(BigDecimal wtdPlan) {
		this.wtdPlan = wtdPlan;
	}

	public BigDecimal getMtdPlan() {
		return mtdPlan;
	}

	public void setMtdPlan(BigDecimal mtdPlan) {
		this.mtdPlan = mtdPlan;
	}

	public BigDecimal getYtdPlan() {
		return ytdPlan;
	}

	public void setYtdPlan(BigDecimal ytdPlan) {
		this.ytdPlan = ytdPlan;
	}

	public BigDecimal getWtdEarningsVar() {
		return wtdEarningsVar;
	}

	public void setWtdEarningsVar(BigDecimal wtdEarningsVar) {
		this.wtdEarningsVar = wtdEarningsVar;
	}

	public BigDecimal getMtdEarningsVar() {
		return mtdEarningsVar;
	}

	public void setMtdEarningsVar(BigDecimal mtdEarningsVar) {
		this.mtdEarningsVar = mtdEarningsVar;
	}

	public BigDecimal getYtdEarningsVar() {
		return ytdEarningsVar;
	}

	public void setYtdEarningsVar(BigDecimal ytdEarningsVar) {
		this.ytdEarningsVar = ytdEarningsVar;
	}

	public BigDecimal getWtdPlanVar() {
		return wtdPlanVar;
	}

	public void setWtdPlanVar(BigDecimal wtdPlanVar) {
		this.wtdPlanVar = wtdPlanVar;
	}

	public BigDecimal getMtdPlanVar() {
		return mtdPlanVar;
	}

	public void setMtdPlanVar(BigDecimal mtdPlanVar) {
		this.mtdPlanVar = mtdPlanVar;
	}

	public BigDecimal getYtdPlanVar() {
		return ytdPlanVar;
	}

	public void setYtdPlanVar(BigDecimal ytdPlanVar) {
		this.ytdPlanVar = ytdPlanVar;
	}

	public BigDecimal getWtdUnits() {
		return wtdUnits;
	}

	public void setWtdUnits(BigDecimal wtdUnits) {
		this.wtdUnits = wtdUnits;
	}

	public BigDecimal getMtdUnits() {
		return mtdUnits;
	}

	public void setMtdUnits(BigDecimal mtdUnits) {
		this.mtdUnits = mtdUnits;
	}

	public BigDecimal getYtdUnits() {
		return ytdUnits;
	}

	public void setYtdUnits(BigDecimal ytdUnits) {
		this.ytdUnits = ytdUnits;
	}

}
