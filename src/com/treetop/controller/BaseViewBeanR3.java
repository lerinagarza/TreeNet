/*
 * Created on Feb 9, 2006
 *
 */
package com.treetop.controller;

import com.treetop.CheckDate;
import com.treetop.utilities.html.HtmlInput;
import com.treetop.utilities.html.HtmlInput.InputType;
import com.treetop.view.PopulatedVector;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * All view bean extend this class.  Has methods to populate fields 
 * in the view bean from a HttpServletRequest
 * to validate basic data types
 * 
 * <br />
 * 
 * <b>This revision of the BaseViewBean has changes:</b>
 * <ul>
 *  <li>Removed URLDecode for incoming data</li>
 * 	<li>Added populateVector() method</li>
 * </ul>
 * @author twalto
 * @author jhagle - Added mods 2012-01-12    
 *      -- 5/31/12 Created a new version of the BaseViewBean to work in conjunction with the new Controller style
 * @deprecated
 */
public abstract class BaseViewBeanR3 {


	private String requestType		= "";
	private String environment 		= "";
	private String company 			= "100";
	private String division 		= "100";

	private String displayMessage 	= "";
	private String errorMessage		= "";
	
	private enum MethodType {GET,SET}

	// This is for making sure Validate is used in Every View Bean
	public abstract void validate();	

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the division
	 */
	public String getDivision() {
		return division;
	}

	/**
	 * @param division the division to set
	 */
	public void setDivision(String division) {
		this.division = division;
	}

	/**
	 * @return the environment
	 */
	public String getEnvironment() {
		return environment;
	}

	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
	}


	/**
	 * Recursively calls .getSuperClass() to get all fields declared
	 * @author jhagle
	 * @param fields
	 * @param c
	 * @return List list of fields
	 */
	private List<Field> getAllFields(List<Field> fields, Class c) {

		for (Field field: c.getDeclaredFields()) {
			fields.add(field);
		}

		if (c.getSuperclass() != null) {
			fields = getAllFields(fields, c.getSuperclass());
		}

		return fields;
	}

	public void populate(HttpServletRequest request) {

		// get an instance of our Class object
		Class cls = this.getClass();

		// get all of the declared fields of the derived ViewBean
		// and all super classes
		List<Field> fields = new LinkedList<Field>();
		fields = getAllFields(fields, cls);

		
		// loop through the fields and see if the name of the fields
		// match any of the Http parameters. If they match, then
		// set the value by calling setXXX (if the method exists).

		for (Field field: fields) {
			String fieldName  = field.getName();

			// use populateVector method if the field type is PopulatedVector
			if (field.getType() == PopulatedVector.class) {
				populateVector(request, field.getName());
				continue;
			}


			String paramValue = request.getParameter(fieldName);
			// One of the Http parameters matches the name of one of our fields
			if (paramValue != null &&
					!paramValue.equals("*all")) {
				// make sure there is a setXXX method for theXXX field
				try {

					Method method = getMethod(cls, fieldName, MethodType.SET);
					method.invoke(this, paramValue);
				}
				catch (Exception e) {
					// the method doesn't exist, so don't worry about it
				}
			}
		}	
	}

	/**
	 * Populates a vector with business objects
	 * @author jhagle
	 * @param request
	 * @param field
	 */
	@SuppressWarnings("unchecked")
	private void populateVector(HttpServletRequest request, String field) {
		Object[] parms = null;

		Class c = this.getClass();
		Field f = null;
		try {
			f = c.getDeclaredField(field);
		} catch (Exception e) {
			//no such field
			return;
		}

		//get the PopulatedVector object
		PopulatedVector v = null;
		try {
			Method m = getMethod(c, field, MethodType.GET);
			v =(PopulatedVector) m.invoke(this, parms);
		} catch (Exception e) {
			//couldn't get the object, don't process any further
			return;
		}

		//get the parameter names and sort them
		//parameters must be sorted to process data coming in rows correctly
		Enumeration<String> parmNames = request.getParameterNames();
		List<String> listParms = Collections.list(parmNames);
		Collections.sort(listParms);

		//trim "s" off of end of vector field name
		field = field.substring(0, field.length()-1);



		Class innerCls = v.getContainingClass();
		//if not inner class type was set, assume string
		if (innerCls == String.class || innerCls == null) {
			//get all parameters that start with the field name
			//and return in a PopulatedVector
			PopulatedVector pv = populateVectorString(request, listParms, field);

			try {
				Class[] parmType = {PopulatedVector.class};
				Method m = getMethod(c, field + "s", parmType, MethodType.SET);
				m.invoke(this, pv);
			} catch (Exception e) {
				System.out.println(e);
			}
			return;
		}

		int thisRow = 0;
		int lastRow = 0;
		boolean first = true;
		Object o = null;


		for (Object obj : listParms) {
			String key = (String) obj;
			if (key.startsWith(field)) {
				if (first) {
					try {
						o = innerCls.newInstance();
					} catch (Exception e) {

					}
				}
				first = false;

				String value = request.getParameter(key);

				try {
				//key format => vectorFieldName_r10_busObjFieldName
					thisRow = Integer.parseInt(key.substring(key.indexOf("_r") + 2,key.lastIndexOf("_")));
				} catch (Exception e) {
					//key not properly formatted
					System.out.println(key + "  not properly formatted");
					continue;
				}
				
				//new row of data, output the object
				if (thisRow != lastRow) {
					v.addElement(o);
					try {
						o = innerCls.newInstance();
					} catch (Exception e) {

					}				
				}


				try {
					String fieldName = key.substring(key.lastIndexOf("_") + 1 ,key.length());
					Method m = getMethod(innerCls, fieldName, MethodType.SET);
					m.invoke(o, value);
				} catch (Exception e) {

				}

				lastRow = thisRow;
			}

		} //end loop

		//add the last object if it is not null
		if (o != null) {
			v.addElement(o);
		}

		//set the vector back into the object
		try {
			Class[] parmType = {PopulatedVector.class};
			Method m = getMethod(c, field + "s", parmType, MethodType.SET);
			m.invoke(this, v);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Populates vector with Strings
	 * @param request
	 * @param listParms
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private PopulatedVector populateVectorString(HttpServletRequest request, List<String> listParms, String field) {
		PopulatedVector returnValues = new PopulatedVector(String.class);

		for (Object obj : listParms) {
			String key = (String) obj;
			if (key.startsWith(field)) {
				String value = request.getParameter(key);
				if (value != null && !value.equals("")) {
					returnValues.addElement(value);
				}
			}
		} //end loop

		return returnValues;
	}


	
	/**
	 * Returns a Method object based on Class, field name, and MethodType (GET or SET) <br />
	 * The field name is converting into the standard getter/setter form <br />
	 * Ex. "lotNumber" becomes "getLotNumber" or "setLotNumber" <br />
	 * For setter method, assumes setting a String
	 * @param c
	 * @param fieldName
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private Method getMethod(Class c, String fieldName, MethodType type) throws Exception {
		Class[] parameterTypes = {String.class};
		return getMethod(c, fieldName, parameterTypes, type);
	}

	/**
	 * Returns a Method object based on Class, field name, Class parameter type, and MethodType (GET or SET) <br />
	 * The field name is converting into the standard getter/setter form <br />
	 * Ex. "lotNumber" becomes "getLotNumber" or "setLotNumber" <br />
	 * For setter method, uses parameter type for method argument types
	 * @param c
	 * @param fieldName
	 * @param parameterType
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private Method getMethod(Class c, String fieldName, Class[] parameterType, MethodType type) throws Exception {
		Method m = null;
		String methodName = "";
		String firstLetter 	= fieldName.substring(0,1).toUpperCase();
		String remainder 	= fieldName.substring(1,fieldName.length());

		Class[] empty = null;
		if (type == MethodType.GET) {
			methodName = "get" + firstLetter + remainder;
			m = c.getMethod(methodName, empty);
		}
		if (type == MethodType.SET) {
			methodName = "set" + firstLetter + remainder;
			m = c.getMethod(methodName, parameterType);
		}	
		return m;
	}

	/**
	 * Returns a query string for each declared field and it's value
	 * @author jhagle
	 * @return
	 */
	public String buildParameterResendString() {
		String[] excludeFields = {};
		return buildParameterResendString(excludeFields);
	}
	
	
	/**
	 * Returns a query string for each declared field and it's value
	 * @param String[] excludeFields
	 * @author jhagle
	 * @return
	 */
	public String buildParameterResendString(String[] excludeFields) {
		StringBuffer resend = new StringBuffer();
		try {
			Class c = this.getClass();
			List<Field> fields = getAllFields(new LinkedList<Field>(), c);		
			Object[] o = null;

			int i=0;
			for (Field field : fields) {

				boolean isExcluded = false;
				for (String excludeField : excludeFields) {
					if (excludeField.equals(field.getName())) {
						isExcluded = true;
					}
				}
				if (isExcluded) {
					continue;
				}
				
				//only process String fields
				if (field.getType() == String.class) {
					String fieldName = field.getName();
					Method m = getMethod(c, fieldName, MethodType.GET);
					String value = (String) m.invoke(this, o);

					if (value != null && !value.trim().equals("")) {
						if (i == 0) {
							resend.append("?");
						}

						if (i > 0) {
							resend.append("&");
						}

						resend.append(fieldName + "=" + value);
						i++;
					}
				}
			}//end loop

		} catch (Exception e) {
			System.out.println(e);
		}

		return resend.toString();
	}

	/**
	 * Returns a string containing HTML input tags for each request parameter and value
	 * @author jhagle
	 * @return
	 */
	public String buildParameterResendInputs() {
		StringBuffer resend = new StringBuffer();
		try {
			Class c = this.getClass();
			List<Field> fields = getAllFields(new LinkedList<Field>(), c);
			Object[] o = null;

			int i=0;
			for (Field field : fields) {
				//only process String fields
				if (field.getType() == String.class) {
					String fieldName = field.getName();
					Method m = getMethod(c, fieldName, MethodType.GET);
					String value = (String) m.invoke(this, o);

					if (value != null && !value.trim().equals("")) {

						HtmlInput input = new HtmlInput();
						input.setType(InputType.HIDDEN);
						input.setName(fieldName);
						input.setValue(value);
						resend.append(input.toString() + "\r");
					}
				}
			}//end loop

		} catch (Exception e) {
			System.out.println(e);
		}

		return resend.toString();
	}


	//************************************************************************************

	//		Validation Methods

	//************************************************************************************


	/**
	 * Test to see if String is an Integer
	 * @param string
	 * @return string
	 */
	public String validateInteger(String string) {
		StringBuffer errorMessage = new StringBuffer();
		if (string != null && !string.trim().equals("")) {
			try {
				//Integer testOne = new Integer(string);
				// Changed to use a Long to test if an Integer is a Whole Number.
				// BECAUSE Integer can only be 9 long is basically a Long Integer (same thing only longer)
				long    testTwo = Long.parseLong(string.trim());
			} catch (Exception e) {
				errorMessage.append(string);
				errorMessage.append(": is NOT a Valid Whole Number.");
			}
		}
		return errorMessage.toString();
	}
	/**
	 * Test to see if String is a BigDecimal
	 * @param string
	 * @return string
	 */
	public String validateBigDecimal(String string) {
		StringBuffer errorMessage = new StringBuffer();
		if (string != null && !string.trim().equals("")) {
			try {
				BigDecimal testOne = new BigDecimal(string);
			} catch (Exception e) {
				errorMessage.append(string);
				errorMessage.append(": is NOT a Valid Number.");
			}
		}
		return errorMessage.toString();	}	
	/**
	 * Test to see if String is a Valid Date
	 *   Sending in mm/dd/yyyy
	 *   or 0
	 * @param string
	 * @return string
	 */
	public String validateDate(String string) {
		StringBuffer errorMessage = new StringBuffer();

		// Test if a valid Date
		if (string != null
				&& !string.trim().equals("")
				&& !string.trim().equals("0")) {
			try {
				String dateArray[] = CheckDate.validateDate(string);
				if (!dateArray[6].equals("")) {
					errorMessage.append(string);
					errorMessage.append(": ");
					errorMessage.append(dateArray[6]);
				}
			} catch (Exception e) {
				errorMessage.append(string);
				errorMessage.append(":  is NOT a Valid Date.");
			}

		}
		return errorMessage.toString();
	}	
	/**
	 * Use this method to format the return messages the same every time.
	 *   Test the Time fields and make sure they are Valid
	 * @param string string string
	 * @return string
	 * Creation date: (02/28/2006 TWalton)
	 */
	public String validateTime(String hour, String minute, String second) {
		StringBuffer errorMessage = new StringBuffer();
		String valid = "Y";
		// Test if a valid Hour
		if (hour == null ||
				minute == null ||
				second == null)
			valid = "N";
		else
		{
			if (!validateInteger(hour).equals("") ||
					!validateInteger(minute).equals("") ||
					!validateInteger(second).equals(""))
				valid = "N";
		}
		if (valid.equals("N"))
		{
			errorMessage.append(hour);
			errorMessage.append(":");
			errorMessage.append(minute);
			errorMessage.append(":");
			errorMessage.append(second);
			errorMessage.append("::  is NOT a Valid Time.");
		}

		return errorMessage.toString();
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the displayMessage
	 */
	public String getDisplayMessage() {
		return displayMessage;
	}

	/**
	 * @param displayMessage the displayMessage to set
	 */
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
