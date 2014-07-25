package com.treetop.controller.blending;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.treetop.businessobjectapplications.BeanBlending;
import com.treetop.businessobjects.DateTime;
import com.treetop.controller.BaseViewBeanR4;
import com.treetop.controller.UrlPathMapping;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.DropDownTriple;
import com.treetop.utilities.html.HtmlSelect.DescriptionType;

@UrlPathMapping("requestType")
public class InqBlending extends BaseViewBeanR4 {
	
	private String year = "";
	private String period = "";
	private String week = "";
	
	private String fromDate = "";
	private String toDate = "";
	private String weeks = "3";
	
	private DateTime dateTime = null;
	private Vector<DropDownTriple> yearPeriodWeek = null;
	
	private BeanBlending bean = new BeanBlending();
	
	
	private String submit = "";

	/**
	 * Default Constructor
	 */
	public InqBlending() {

	}
	
	/**
	 * Constructor with auto populate
	 * @param request
	 */
	public InqBlending(HttpServletRequest request) {

		this.populate(request);
		
	}
	
	private void getYearPeriodWeek() {
		
		dateTime = UtilityDateTime.getSystemDate();
		dateTime = UtilityDateTime.addDaysToDate(dateTime, 7);
		yearPeriodWeek = UtilityDateTime.dropDownYearPeriodWeek(Integer.parseInt(dateTime.getM3FiscalYear()), 2);
		
	}
	
	public String dropDownYear() {
		
		if (yearPeriodWeek == null || dateTime == null) {
			getYearPeriodWeek();
		}
		
		String html = "";
		String name = "year";
		String id = "year";
		String cssClass = "";
		String selectedValue = dateTime.getM3FiscalYear();
		String defaultValue = null;
		boolean readOnly = false;
		DescriptionType descriptionType = DescriptionType.VALUE_ONLY;
		
		html = DropDownTriple.buildChainedYear(
				yearPeriodWeek, 
				name, 
				id, 
				cssClass, 
				selectedValue, 
				defaultValue, 
				readOnly, 
				descriptionType);
		
		return html;
	}
	
	public String dropDownPeriod() {
		
		if (yearPeriodWeek == null || dateTime == null) {
			getYearPeriodWeek();
		}
		
		String html = "";
		
		String masterId = "year";
		String name = "period";
		String id = "period";
		String cssClass = "";
		String selectedValue = dateTime.getM3FiscalYear() + "-" + dateTime.getM3FiscalPeriod();
		String defaultValue = null;
		boolean readOnly = false;
		DescriptionType descriptionType = DescriptionType.DESCRIPTION_ONLY;
		
		html = DropDownTriple.buildChainedPeriod(
				yearPeriodWeek, 
				masterId, 
				name, 
				id, 
				cssClass, 
				selectedValue, 
				defaultValue, 
				readOnly, 
				descriptionType);
		
		return html;
	}
	
	public String dropDownWeek() {
		
		if (yearPeriodWeek == null || dateTime == null) {
			getYearPeriodWeek();
		}
		
		String html = "";
		
		String masterId = "period";
		String name = "week";
		String id = "week";
		String cssClass = "";
		String selectedValue = this.getWeek();
		if (this.getWeek().equals("")) {
			selectedValue = dateTime.getM3FiscalPeriod() + "-" + dateTime.getM3FiscalWeek();
		}
		
		
		String defaultValue = null;
		boolean readOnly = false;
		DescriptionType descriptionType = DescriptionType.DESCRIPTION_ONLY;
		
		html = DropDownTriple.buildChainedWeek(
				yearPeriodWeek, 
				masterId, 
				name, 
				id, 
				cssClass, 
				selectedValue, 
				defaultValue, 
				readOnly, 
				descriptionType);
		
		return html;
	}
	
	@Override
	public void validate() {
		
		if (this.getEnvironment().equals("")) {
			this.setEnvironment("PRD");
		}
		
		
		//transform yyyy-pp to pp
		String period = this.getPeriod();
		period = period.substring(period.lastIndexOf("-") + 1, period.length());
		
		//transform pp-ww to ww
		String week = this.getWeek();
		week = week.substring(week.lastIndexOf("-") + 1, week.length());
		
		//get start date yyyyMMdd
		Vector<DateTime> weekDates = UtilityDateTime.getDatesFromYearWeeklyPeriod(this.getYear(), week, null);
		DateTime from = weekDates.elementAt(0);
		this.setFromDate(from.getDateFormatyyyyMMdd());
		
		//determine number of weeks to add
		int weeks = 3;
		if (this.getWeeks() != null && !this.getWeeks().equals("")) {
			try {
			weeks = Integer.parseInt(this.getWeeks());
			} catch (Exception e) {}
		}
		
		//set to date yyyyMMdd
		DateTime to = UtilityDateTime.addDaysToDate(from, (7 * weeks) - 1);
		this.setToDate(to.getDateFormatyyyyMMdd());
		
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getYear() {
		return year;
	}

	public String getPeriod() {
		return period;
	}

	public String getWeek() {
		return week;
	}

	public BeanBlending getBean() {
		return bean;
	}

	public void setBean(BeanBlending bean) {
		this.bean = bean;
	}



	
	
}
