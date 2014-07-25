package com.treetop.controller.operations;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanOperationsReporting;
import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.KeyValue;
import com.treetop.businessobjects.ManufacturingOrderDetail;
import com.treetop.controller.BaseViewBeanR4;
import com.treetop.controller.UrlPathMapping;
import com.treetop.services.ServiceOperationsReporting;
import com.treetop.services.ServiceWarehouse;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HtmlOption;
import com.treetop.utilities.html.HtmlSelect.DescriptionType;
import com.treetop.utilities.html.SelectionCriteria;

@UrlPathMapping({"requestType","warehouse","yearPeriod"})
public class InqOperations extends BaseViewBeanR4 {

	private String forecastLabel = "";
	
	private String warehouse = "";
	private String warehouseError = "";
	
	private String dateError = "";
	
	private String yearPeriod = "";
	
	private String fiscalYear = "";
	private String fiscalPeriodStart = "";
	private String fiscalPeriodEnd = "";
	private String fiscalWeekStart = "";
	private String fiscalWeekEnd = "";
	
	private String fiscalYearStartDate = "";
	private String startDate = "";
	private String endDate = "";
	
	private String startDateFormatted = "";
	private String endDateFormatted = "";
	
	private String submit = "";
	
	private String userProfile = "";
	
	private BeanOperationsReporting bean = new BeanOperationsReporting();
	private KeyValue commentKeys = new KeyValue();
	private List<String> weeksInMonth = new ArrayList<String>();
	
	private String benefitRate = "";
	
	private static Vector<HtmlOption> options = null;
		
	/**
	 * Default constructor
	 */
	public InqOperations() {
		
	}
	
	
	/**
	 * Constructor with request, runs populate()
	 * @param request
	 */
	public InqOperations(HttpServletRequest request) throws Exception {

		this.populate(request);
		
		String userProfile = SessionVariables.getSessionttiProfile(request);
		this.setUserProfile(userProfile);
		
		if (this.getRequestType().equals("") && this.getFiscalWeekStart().equals("")) {
			this.setRequestType("weekly");
		}
		
		
		this.populateDates();
		
		
		
		this.validate();
	}
	
	public void storeTotalAmount(BigDecimal amount) {
		
		try {
			
			//do not store monthly totals
			if (!this.getRequestType().equals("monthly")) {
				ServiceOperationsReporting.storeTotalAmount(this, amount);
			}
			
		} catch (Exception e) {
			System.out.println("com.treetop.controller.operations.InqOperations.storeTotalAmount():  " +
					"Error storing total amount: " + e);
		}
		
	}
	
	@Override
	public void validate() {
		
		if (this.getEnvironment() == null || this.getEnvironment().equals("")) {
			this.setEnvironment("PRD");
		}
		
	}
	
	public String buildSelectionCriteria() {
		SelectionCriteria sc = new SelectionCriteria();
		sc.addValue("Warehouse", this.getWarehouse());
		sc.addValue("Fiscal Year", this.getFiscalYear());
		sc.addValue("Fiscal Period", this.getFiscalPeriodStart());
		sc.addValue("Fiscal Week", this.getFiscalWeekStart());
		return sc.toString();
	}
	
	public String buildResend() {
		return buildParameterResendInputs(new String[] {"submit"});
	}
	
	public String buildResendParams() {
		return buildParameterResendString();
	}
	
	
	public Integer[] getWeekInPeriod(int fiscalPeriod) {
		Integer[] weeks = null;
		
		int weeksInPeriod = 0;
		int lastWeekInPeriod = 0;
		
		for (int i=1; i<=fiscalPeriod; i++) {
			weeksInPeriod = i % 3 == 0 ? 5 : 4;
			lastWeekInPeriod +=  weeksInPeriod;
		}
		
		weeks = new Integer[weeksInPeriod];
		for (int i=weeksInPeriod; i > 0; i--) {
			weeks[i-1] = lastWeekInPeriod--;
		}
		
		
		return weeks;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String buildDropDownFiscalYear(String selectedValue) {
		
		try {
			Vector<String> inValues = new Vector<String>();
			inValues.addElement("listAllYears");
			
			if (selectedValue == null || selectedValue.trim().equals("")) {
				selectedValue = UtilityDateTime.getSystemDate().getM3FiscalYear();
			}

			Vector dds = UtilityDateTime.dropDownYear(inValues);
			Vector<HtmlOption> options = HtmlOption.convertDropDownSingleVector(dds);
			return DropDownSingle.buildDropDown(options, 
					"fiscalYear", 				//name
					"fiscalYear", 				//id
					selectedValue, 		//selected value
					"",			 				//default value
					false,	 					//readonly
					"",		 					//css class
					DescriptionType.VALUE_ONLY	//description type
					);
		} catch (Exception e) {
			System.err.println("Exception @ InqOperations.buildDropDownFiscalYear().  " + e);
		}
		return null;
	}
	
	
	public String buildDropDownWarehouse(String selectedValue) {
		try {
		
			// If the cache is null, use the service to build the list
			if (options == null) {
				options = new Vector<HtmlOption>();
				Vector<HtmlOption> buildOptions = ServiceWarehouse.dropDownProductionWarehouse(this.getEnvironment());
				
				options.addElement(new HtmlOption("EXEC","Executive Summary"));
				
				for (HtmlOption buildOption : buildOptions) {
					// only use the following warehouses at this time
					// as more warehouses are setup for this application, add them here
					if (buildOption.getValue().equals("209")
							|| buildOption.getValue().equals("230")
							|| buildOption.getValue().equals("240")
							|| buildOption.getValue().equals("251")
							|| buildOption.getValue().equals("280")
							|| buildOption.getValue().equals("290")
							
							|| buildOption.getValue().equals("342")
							|| buildOption.getValue().equals("343")
							|| buildOption.getValue().equals("345")
							|| buildOption.getValue().equals("346")
							|| buildOption.getValue().equals("350")
							|| buildOption.getValue().equals("380")
							|| buildOption.getValue().equals("384")
							
							|| buildOption.getValue().equals("469")
							|| buildOption.getValue().equals("490")

							) {
						options.addElement(buildOption);
					}
				}
			}

		
		return DropDownSingle.buildDropDown(options, 
				"warehouse", 	//name
				"warehouse", 	//id
				selectedValue, 	//selected value
				"",			 	//default value
				false,	 		//readonly
				"",		 		//css class
				DescriptionType.VALUE_DESCRIPTION	//description type
				);
		} catch (Exception e) {
			System.err.println("Exception @ InqOperations.buildDropDownFacility(selectedValue).  " + e);
		}
		return "";
	}
	
	public void populateDates() throws Exception {
		Vector<DateTime> dts = null;
		DateTime dt = null;
		
		if (this.getFiscalYear().equals("") 
				&& this.getFiscalPeriodStart().equals("") 
				) {
			// no year or starting fiscal period

			if (this.getYearPeriod().length() == 6) {
				try {

					String year = this.getYearPeriod().substring(0,4);
					String period = this.getYearPeriod().substring(4,6);
					
					if (this.getRequestType().equals("") || this.getRequestType().equals("weekly")) {
						dts = UtilityDateTime.getDatesFromYearWeeklyPeriod(year, period, null);
					} else if (this.getRequestType().equals("monthly")) {
						dts = UtilityDateTime.getDatesFromYearMonthlyPeriod(year, period, null);
					}
					
					if (dts != null && dts.isEmpty()) {
						if (this.getRequestType().equals("") || this.getRequestType().equals("weekly")) {
							this.setDateError("Year: " + year + " and Week: " + period + " is not valid");
						} else if (this.getRequestType().equals("monthly")) {
							this.setDateError("Year: " + year + " and Period: " + period + " is not valid");
						}
					} else {
						dt = UtilityDateTime.addDaysToDate(dts.elementAt(0), 0);
					}
					
					
					
				} catch (Exception e) {}
				
			} else {

					//use today minus 7 days as default date
					dt = UtilityDateTime.getSystemDate();
					if (this.getRequestType().equals("") || this.getRequestType().equals("weekly")) {
						//use today minus 7 days as default date
						dt = UtilityDateTime.addDaysToDate(dt, -7);
					}
					
					if (this.getRequestType().equals("monthly")) {
						//use today minus 30 days as default date
						dt = UtilityDateTime.addDaysToDate(dt, -30);
					}

			}

			if (dt != null) {
				this.setFiscalYear(dt.getM3FiscalYear());
				this.setFiscalPeriodStart(dt.getM3FiscalPeriod());
				this.setFiscalWeekStart(dt.getM3FiscalWeek());
			}
			
		}
		
		 if (!this.getFiscalYear().equals("") 
				 && !this.getFiscalPeriodStart().equals("") 
				 && this.getFiscalWeekStart().equals("")){
			
			 this.setRequestType("monthly");
			
		 }

		if (this.getDateError().equals("")) {
			
			if (this.getRequestType().equals("") || this.getRequestType().equals("weekly")) {
				this.buildDateRangeFromWeek();
	
			}
			if (this.getRequestType().equals("monthly")) {
				this.buildDateRangeFromMonth();
			}
			
		}
		
	}
	
	public void buildDateRangeFromWeek() throws Exception {
		buildDateRange("week");	
	}
	
	private void buildDateRangeFromMonth() throws Exception {
		buildDateRange("month");
	}
	
	private void buildDateRange(String type) throws Exception {
		StringBuffer throwError = new StringBuffer();
		
		try {
			Vector<DateTime> starts = null;
			Vector<DateTime> years = null;
			
			if (type.equals("week")) {
				starts = UtilityDateTime.getDatesFromYearWeeklyPeriod(this.getFiscalYear(),
						this.getFiscalWeekStart(), null);
			}
			
			if (type.equals("month")) {
				starts = UtilityDateTime.getDatesFromYearMonthlyPeriod(this.getFiscalYear(),
						this.getFiscalPeriodStart(), null);
			}
			
			years = UtilityDateTime.getDatesFromYearMonthlyPeriod(this.getFiscalYear(), "1", null);
			
			DateTime start = starts.elementAt(0);
			start = UtilityDateTime.addDaysToDate(start, 0);
			
			DateTime end = starts.elementAt(1);
			end = UtilityDateTime.addDaysToDate(end, 0);
			
			DateTime year = years.elementAt(0);
			
			this.setFiscalPeriodStart(start.getM3FiscalPeriod());
			this.setFiscalPeriodEnd(end.getM3FiscalPeriod());
			
			this.setFiscalWeekStart(start.getM3FiscalWeek());
			this.setFiscalWeekEnd(end.getM3FiscalWeek());
			
			//Allow for previous weeks of the month to be presented for Executive Summary.
			if (type.equals("week") && this.getWarehouse().trim().equals("EXEC")) {
				Connection conn = null;
				Vector vector = UtilityDateTime.getDatesFromYearMonthlyPeriod(this.fiscalYear, this.fiscalPeriodStart, conn);
				DateTime cheatDate = (DateTime) vector.elementAt(0);
				start.setDateFormatyyyyMMdd(cheatDate.getDateFormatyyyyMMdd());
				start.setDateFormatMonthNameddyyyy(cheatDate.getDateFormatMonthNameddyyyy());
			}
			
			this.setStartDate(start.getDateFormatyyyyMMdd());
			this.setStartDateFormatted(start.getDateFormatMonthNameddyyyy());
			
			this.setEndDate(end.getDateFormatyyyyMMdd());
			this.setEndDateFormatted(end.getDateFormatMonthNameddyyyy());
			
			this.setFiscalYearStartDate(year.getDateFormatyyyyMMdd());
		
			
		} catch (Exception e) {
			throwError.append("Error getting date range from fiscal year/week.  " + e);
		}
		
		if (!throwError.toString().trim().equals("")) {
			throw new Exception(throwError.toString() + "@ InqOperations.setDateRangeFromWeek()");
		}		
		
	}
	
	public static String buildOrderNumberList(ManufacturingOrderDetail order) {
		StringBuffer result = new StringBuffer();
		
		int i=0;
		for (String orderNumber : order.getOrderNumbers()) {
			if (i > 0) {
				result.append(", ");
			}
			result.append(orderNumber);
			i++;
		}
		
		return result.toString();
	}
	
	
	/**
	 * Formats a string to display on two lines.<br>
	 * Replaces all spaces with <code>&amp;nbsp;</code><br>
	 * except for the middle space
	 * @param s
	 * @return
	 */
	public static String formatRowLabel(String s) {
		StringBuffer result = new StringBuffer();
		
		String[] words = s.split(" ");
		int mid = (words.length / 2) -1;
		
		for (int i=0; i<words.length; i++) {
			result.append(words[i]);
			if (i < words.length-1) {
				if (i == mid) {
					result.append(" ");
				} else {
					result.append("&nbsp;");
				}
			}
			
		}

		return result.toString();
	}
	

	public static void main(String[] args) {
		String s = "This sentence has four spaces.";
		s = formatRowLabel(s);
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}


	public String getFiscalWeekStart() {
		return fiscalWeekStart;
	}


	public void setFiscalWeekStart(String fiscalWeekStart) {
		this.fiscalWeekStart = fiscalWeekStart;
	}


	public String getFiscalWeekEnd() {
		return fiscalWeekEnd;
	}


	public void setFiscalWeekEnd(String fiscalWeekEnd) {
		this.fiscalWeekEnd = fiscalWeekEnd;
	}


	public BeanOperationsReporting getBean() {
		return bean;
	}


	public void setBean(BeanOperationsReporting bean) {
		this.bean = bean;
	}


	public KeyValue getCommentKeys() {
		return commentKeys;
	}


	public void setCommentKeys(KeyValue commentKeys) {
		this.commentKeys = commentKeys;
	}

	public String getSubmit() {
		return submit;
	}


	public void setSubmit(String submit) {
		this.submit = submit;
	}


	public String getFiscalPeriodStart() {
		return fiscalPeriodStart;
	}


	public void setFiscalPeriodStart(String fiscalPeriodStart) {
		this.fiscalPeriodStart = fiscalPeriodStart;
	}


	public String getFiscalPeriodEnd() {
		return fiscalPeriodEnd;
	}


	public void setFiscalPeriodEnd(String fiscalPeriodEnd) {
		this.fiscalPeriodEnd = fiscalPeriodEnd;
	}


	public String getBenefitRate() {
		return benefitRate;
	}


	public void setBenefitRate(String benefitRate) {
		this.benefitRate = benefitRate;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getStartDateFormatted() {
		return startDateFormatted;
	}


	public void setStartDateFormatted(String startDateFormatted) {
		this.startDateFormatted = startDateFormatted;
	}


	public String getEndDateFormatted() {
		return endDateFormatted;
	}


	public void setEndDateFormatted(String endDateFormatted) {
		this.endDateFormatted = endDateFormatted;
	}


	public String getFiscalYearStartDate() {
		return fiscalYearStartDate;
	}


	public void setFiscalYearStartDate(String fiscalYearStartDate) {
		this.fiscalYearStartDate = fiscalYearStartDate;
	}


	public String getWarehouseError() {
		return warehouseError;
	}


	public void setWarehouseError(String warehouseError) {
		this.warehouseError = warehouseError;
	}


	public String getDateError() {
		return dateError;
	}


	public void setDateError(String dateError) {
		this.dateError = dateError;
	}


	public String getYearPeriod() {
		return yearPeriod;
	}


	public void setYearPeriod(String yearPeriod) {
		this.yearPeriod = yearPeriod;
	}


	public String getUserProfile() {
		return userProfile;
	}


	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}


	public List<String> getWeeksInMonth() {
		return weeksInMonth;
	}


	public void setWeeksInMonth(List<String> weeksInMonth) {
		this.weeksInMonth = weeksInMonth;
	}


	public String getForecastLabel() {
		return forecastLabel;
	}


	public void setForecastLabel(String forecastLabel) {
		this.forecastLabel = forecastLabel;
	}
	
	
}
