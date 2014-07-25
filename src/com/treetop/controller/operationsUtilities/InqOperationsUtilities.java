package com.treetop.controller.operationsUtilities;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;


import com.treetop.businessobjects.DateTime;
import com.treetop.controller.BaseViewBeanR3;
import com.treetop.services.ServiceWarehouse;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.HtmlOption;
import com.treetop.utilities.html.HtmlSelect.DescriptionType;

public class InqOperationsUtilities extends BaseViewBeanR3{
	
	private String	warehouse			= "";
	private String	weekBeginningDate	= "";
	private String	fiscalYear			= "";
	
	
	private String json					= "";
	
	private static Vector<String> types			= new Vector<String>();
	private static Vector<String> units			= new Vector<String>();
	private static Vector<String> days			= new Vector<String>();
	
	private static Vector<HtmlOption> options = null;
	
	/**
	 * Default constructor
	 */
	public InqOperationsUtilities() {
		
	}
		
	/**
	 * Constructor with request, runs populate()
	 * @param request
	 */
	public InqOperationsUtilities(HttpServletRequest request) {
		this.populate(request);
		this.validate();
	}
	
	@Override
	public void validate() {
		StringBuffer errorMessage = new StringBuffer();
		
		if (this.getEnvironment() == null || this.getEnvironment().equals("") || this.getEnvironment().equals("null")) {
			this.setEnvironment("PRD");
		}
		
		DateTime dt = UtilityDateTime.getDateFromMMddyyyyWithSlash(this.getWeekBeginningDate());
		if (dt.getDateErrorMessage().equals("")) {
			
			// take the incoming date and adjust it to the previous Monday date
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(dt.getDateFormatyyyyMMdd()));
				
				int adjustDays = 0;
				
				if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					//if the day chosen is Sunday (int value = 1),
					//then force the subtraction of 6 days
					
					adjustDays = -6;
					
				} else {
					adjustDays = Calendar.MONDAY - c.get(Calendar.DAY_OF_WEEK);
				}
				
				//adjust the date to the previous Monday
				c.add(Calendar.DATE, adjustDays);
				
				this.setWeekBeginningDate(sdf.format(c.getTime()));
				
				
			} catch (Exception e) {
				System.err.println("Exception @ InqOperationsUtilities.validate().  " + e);
			}
			
			
		} else {
			errorMessage.append(("Date " + this.getWeekBeginningDate() + " is not valid.  "));
		}
		
		if (this.getWarehouse() == null || this.getWarehouse().equals("")) {
			errorMessage.append("Warehouse cannot be blank.  ");
		}
		
		if (this.getJson() != null && !this.getJson().equals("")) {
			
			//validate numeric values
			try {
				JSONObject data = new JSONObject(this.getJson());
				
				String error = validateNumericJsonValues(data);
				
				if (!error.equals("")) {
					errorMessage.append(error);
				}
			} catch (Exception e) {
				errorMessage.append(e);
			}
			
		}
		
		if (!errorMessage.toString().trim().equals("")) {
			this.setErrorMessage(errorMessage.toString());
		}
		
	}
	
	private String validateNumericJsonValues(JSONObject data) throws Exception {
		StringBuffer errorMessage = new StringBuffer();
		JSONObject rates = (JSONObject) data.get("rate");
		
		for (String type : JSONObject.getNames(rates)) {
			String value = (String) rates.get(type);
			
			if (value == null || value.equals("")) {
				rates.put(type, "0");
			} else {
				try {
					BigDecimal bd = new BigDecimal(value);
				} catch (Exception e) {
					errorMessage.append("");
				}
			}
			
		}
		
		JSONObject usage = (JSONObject) data.get("usage");
		for (String day : JSONObject.getNames(usage)) {
			
			JSONObject amounts = (JSONObject) usage.get(day);
			for (String type : JSONObject.getNames(amounts)) {
				String value = (String) amounts.get(type);
				
				if (value == null || value.equals("")) {
					amounts.put(type, "0");
				} else {
					try {
						BigDecimal bd = new BigDecimal(value);
					} catch (Exception e) {
						errorMessage.append(e);
					}
				}
			}//end loop types
		}//end loop days
		
		//set the json string from the modified JSONObject
		this.setJson(data.toString());
		
		return errorMessage.toString().trim();
	}
	
	
	public static String buildDropDownWarehouse(String selectedValue) {
		try {
		
			// If the cache is null, use the service to build the list
			if (options == null) {
				options = new Vector<HtmlOption>();
				Vector<HtmlOption> buildOptions = ServiceWarehouse.dropDownProductionWarehouse("PRD");
				
				for (HtmlOption buildOption : buildOptions) {
					// only use the following warehouses at this time
					// as more warehouses are setup for this application, add them here
					if (buildOption.getValue().equals("209")
							|| buildOption.getValue().equals("230")
							|| buildOption.getValue().equals("240")
							|| buildOption.getValue().equals("251")
							|| buildOption.getValue().equals("280")
							|| buildOption.getValue().equals("290")
							|| buildOption.getValue().equals("469")
							|| buildOption.getValue().equals("490")) {
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
	
	
	public static Vector<String> getUtilitiesTypes() {
		
		if (types.isEmpty()) {
			types.add("Fuel");
			types.add("Water");
			types.add("Power");
			types.add("Sewer");
			types.add("Nitrogen");
			types.add("Garbage");
			types.add("Pomace");
			types.add("Propane");
			types.add("Process Aid Waste");
		}
		
		return types;
	}
	
	public static Vector<String> getUtilitiesUnits() {
		
		if (units.isEmpty()) {
			units.add("thm");	//Fuel
			units.add("Gal");		//Water
			units.add("kWh");	//Electric
			units.add("Unit?");
			units.add("ft<sup>3</sup>");	//Nitrogen
			units.add("LB");	//Garbage
			units.add("LB");	//Pomace
			units.add("ft<sup>3</sup>");	//Propane
			units.add("LB");	//Process Aid Waste
		}
		
		return units;
	}
	
	public static Vector<String> getDaysOfWeek() {
		
		if (days.isEmpty()) {
			days.add("Monday");
			days.add("Tuesday");
			days.add("Wednesday");
			days.add("Thursday");
			days.add("Friday");
			days.add("Saturday");
			days.add("Sunday");
		}
		return days;
	}


	public String getWarehouse() {
		return warehouse;
	}


	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}


	public String getWeekBeginningDate() {
		return weekBeginningDate;
	}


	public void setWeekBeginningDate(String weekBeginningDate) {
		this.weekBeginningDate = weekBeginningDate;
	}


	public String getJson() {
		return json;
	}


	public void setJson(String json) {
		this.json = json;
	}	
}
