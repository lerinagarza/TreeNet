/*
 * Created on Feb 9, 2006
 *
 */
package com.treetop.viewbeans;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import com.treetop.utilities.html.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;


import com.treetop.CheckDate;

/**
 * @author twalto
 * @deprecated
 * This revision of the BaseViewBean has change:
 *     The abstract validate method will not be returning a List
 */
public abstract class BaseViewBeanR1 {
	
	// This is for making sure Validate is used in Every View Bean
	public abstract void validate();	
	
	public void populate(HttpServletRequest req) {

		// get an instance of our Class object
		Class cls = this.getClass();
			
		// get all of the declared fields of the derived ViewBean
		Field[] fields = cls.getDeclaredFields();
	
		int numFields = (fields == null) ? 0 : fields.length;
		Class[] params = {String.class};
	
		// loop through the fields and see if the name of the fields
		// match any of the Http parameters. If they match, then
		// set the value by calling setXXX (if the method exists).
		for (int i=0; i<numFields; i++) {
			String fieldName  = fields[i].getName();
			String paramValue = req.getParameter(fieldName);
			// One of the Http parameters matches the name of one of our fields
			if (paramValue != null &&
			    !paramValue.equals("*all")) {
				// make sure there is a setXXX method for theXXX field
				try {
					paramValue = URLDecoder.decode(paramValue, "ISO");
					// get the first letter and capitalize it
					String firstLetter = fieldName.substring(0, 1).toUpperCase();				
					StringBuffer methodName = new StringBuffer();
					methodName.append("set");
					methodName.append(firstLetter);
					methodName.append(fieldName.substring(1, fieldName.length()));
					Method method = cls.getDeclaredMethod(methodName.toString(), params);
					Object[] val = {paramValue};
					method.invoke(this, val);
				}
				catch (Exception e) {
					// the method doesn't exist, so don't worry about it
			//		System.out.println("STOP if Issue: " + e);
				}
			}
		}	
	}
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
}
