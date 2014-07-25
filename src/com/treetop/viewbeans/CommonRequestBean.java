/*
 * Created on July 27, 2010
 *
 */
package com.treetop.viewbeans;

import java.util.Vector;

import com.treetop.businessobjects.DateTime;

/**
 * @author deisen
 *
 * Bean to provide common requests to methods returning information for any JSP.
 */
public class CommonRequestBean {	
		
	protected	String		environment         = "";
	protected	String		companyNumber		= "";
	protected	String		divisionNumber		= "";
	protected	String		idLevel1			= "";
	protected	String		idLevel2			= "";
	protected	String		idLevel3			= "";
	protected	String		idLevel4			= "";
	protected	String		idLevel5			= "";
	protected	String		idLevel6			= "";
	protected	String		idLevel7			= "";
	protected	String		grouping			= "";
	protected	String		rate				= "";
	protected	String		rateType			= "";
	protected	String		quantity			= "";
	protected	String		date				= "";
	protected	String		time				= "";
	protected	String		user				= "";
	protected	String		fiscalDateStart		= "";
	protected	String		fiscalDateEnd		= "";
	protected	String		fiscalYear			= "";
	protected	String		fiscalPeriod		= "";	
	protected	String		fiscalWeek			= "";
	protected	String		rebuildOption		= "";
	
	protected   DateTime	dateTime			= new DateTime();
	
	protected	String		whereInNotIn		= "";
	protected	Vector<String> filter = new Vector<String>();
		
	/**
	 *  // Constructor
	 */
	public CommonRequestBean() {
		super(); 
	}		

	/**
	 * Constructor with environment
	 * @param environment
	 */
	public CommonRequestBean(String environment) {
		super();
		this.setEnvironment(environment);
	}
	/**
	 * Constructor with with environment and 1 id level
	 * @param environment
	 * @param idLevel1
	 * @param idLevel2
	 * @param idLevel3
	 * @param idLevel4
	 */
	public CommonRequestBean(String environment,
			String idLevel1) {
		super();
		this.setEnvironment(environment);
		this.setIdLevel1(idLevel1);
	}
	/**
	 * Constructor with with environment and 2 id levels
	 * @param environment
	 * @param idLevel1
	 * @param idLevel2
	 * @param idLevel3
	 * @param idLevel4
	 */
	public CommonRequestBean(String environment,
			String idLevel1,
			String idLevel2) {
		super();
		this.setEnvironment(environment);
		this.setIdLevel1(idLevel1);
		this.setIdLevel2(idLevel2);
	}
	/**
	 * Constructor with with environment and 3 id levels
	 * @param environment
	 * @param idLevel1
	 * @param idLevel2
	 * @param idLevel3
	 * @param idLevel4
	 */
	public CommonRequestBean(String environment,
			String idLevel1,
			String idLevel2,
			String idLevel3) {
		super();
		this.setEnvironment(environment);
		this.setIdLevel1(idLevel1);
		this.setIdLevel2(idLevel2);
		this.setIdLevel3(idLevel3);
	}
	/**
	 * Constructor with with environment and 4 id levels
	 * @param environment
	 * @param idLevel1
	 * @param idLevel2
	 * @param idLevel3
	 * @param idLevel4
	 */
	public CommonRequestBean(String environment,
			String idLevel1,
			String idLevel2,
			String idLevel3,
			String idLevel4) {
		super();
		this.setEnvironment(environment);
		this.setIdLevel1(idLevel1);
		this.setIdLevel2(idLevel2);
		this.setIdLevel3(idLevel3);
		this.setIdLevel4(idLevel4);
	}
	/**
	 * Constructor with with environment and 5 id levels
	 * @param environment
	 * @param idLevel1
	 * @param idLevel2
	 * @param idLevel3
	 * @param idLevel4
	 * @param idLevel5
	 */
	public CommonRequestBean(String environment,
			String idLevel1,
			String idLevel2,
			String idLevel3,
			String idLevel4,
			String idLevel5) {
		super();
		this.setEnvironment(environment);
		this.setIdLevel1(idLevel1);
		this.setIdLevel2(idLevel2);
		this.setIdLevel3(idLevel3);
		this.setIdLevel4(idLevel4);
		this.setIdLevel5(idLevel5);
	}
	
	/**
	 * @return Returns the environment code. (library)
	 */
	public String getEnvironment() {
		return environment;
	}
	/**
	 * @param Sets the environment code. (library)
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	/**
	 * @return Returns the company number.
	 */
	public String getCompanyNumber() {
		return companyNumber;
	}
	/**
	 * @param Sets the company number.
	 */
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	/**
	 * @return Returns the division number.
	 */
	public String getDivisionNumber() {
		return divisionNumber;
	}
	/**
	 * @param Sets the division number.
	 */
	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}
	/**
	 * @return Returns the identification level 1. (control/key information)
	 */
	public String getIdLevel1() {
		return idLevel1;
	}
	/**
	 * @param Sets the identification level 1. (control/key information)
	 */
	public void setIdLevel1(String idLevel1) {
		this.idLevel1 = idLevel1;
	}
	/**
	 * @return Returns the identification level 2. (control/key information)
	 */
	public String getIdLevel2() {
		return idLevel2;
	}
	/**
	 * @param Sets the identification level 2. (control/key information)
	 */
	public void setIdLevel2(String idLevel2) {
		this.idLevel2 = idLevel2;
	}
	/**
	 * @return Returns the identification level 3. (control/key information)
	 */
	public String getIdLevel3() {
		return idLevel3;
	}
	/**
	 * @param Sets the identification level 3. (control/key information)
	 */
	public void setIdLevel3(String idLevel3) {
		this.idLevel3 = idLevel3;
	}
	/**
	 * @return Returns the identification level 4. (control/key information)
	 */
	public String getIdLevel4() {
		return idLevel4;
	}
	/**
	 * @param Sets the identification level 4. (control/key information)
	 */
	public void setIdLevel4(String idLevel4) {
		this.idLevel4 = idLevel4;
	}
	/**
	 * @return Returns the identification level 5. (control/key information)
	 */
	public String getIdLevel5() {
		return idLevel5;
	}
	/**
	 * @param Sets the identification level 5. (control/key information)
	 */
	public void setIdLevel5(String idLevel5) {
		this.idLevel5 = idLevel5;
	}
	public String getGrouping() {
		return grouping;
	}

	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}

	/**
	 * @return Returns a rate.
	 */
	public String getRate() {
		return rate;
	}
	/**
	 * @param Sets the rate.
	 */
	public void setRate(String rate) { 
		this.rate = rate;
	}
	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	/**
	 * @return Returns a quantity.
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param Sets the quantity.
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param Sets the date.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return Returns the time.
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param Sets the time.
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return Returns the option to rebuild anything.
	 */
	public String getRebuildOption() {
		return rebuildOption;
	}
	/**
	 * @param Sets the option to rebuild anything.
	 */
	public void setRebuildOption(String rebuildOption) {
		this.rebuildOption = rebuildOption;
	}

	public String getWhereInNotIn() {
		return whereInNotIn;
	}

	public void setWhereInNotIn(String whereInNotIn) {
		this.whereInNotIn = whereInNotIn;
	}

	public Vector<String> getFilter() {
		return filter;
	}

	public void setFilter(Vector<String> filter) {
		this.filter = filter;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getFiscalDateStart() {
		return fiscalDateStart;
	}

	public void setFiscalDateStart(String fiscalDateStart) {
		this.fiscalDateStart = fiscalDateStart;
	}

	public String getFiscalDateEnd() {
		return fiscalDateEnd;
	}

	public void setFiscalDateEnd(String fiscalDateEnd) {
		this.fiscalDateEnd = fiscalDateEnd;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public String getFiscalPeriod() {
		return fiscalPeriod;
	}

	public void setFiscalPeriod(String fiscalPeriod) {
		this.fiscalPeriod = fiscalPeriod;
	}

	public String getFiscalWeek() {
		return fiscalWeek;
	}

	public void setFiscalWeek(String fiscalWeek) {
		this.fiscalWeek = fiscalWeek;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getIdLevel6() {
		return idLevel6;
	}

	public void setIdLevel6(String idLevel6) {
		this.idLevel6 = idLevel6;
	}

	public String getIdLevel7() {
		return idLevel7;
	}

	public void setIdLevel7(String idLevel7) {
		this.idLevel7 = idLevel7;
	}
}
