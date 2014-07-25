/*
 * Created on September 29, 2005
 */
package com.treetop.utilities.html;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

import com.treetop.*;
/**
 * @author twalto
 * Use this class to put Help Mask Fields, for use in JSP's
 *
 */
public class HTMLHelpersMasking {

	/* 
	 * Send in a String which is a number.
	 *   Convert it to BigDecimal, and then mask with decimal Positions
	 * Created October 6, 2005  TWalton
	 */
	public static String maskNumber(String numberIn, int decPositions) {
		StringBuffer returnString = new StringBuffer();
		try
		{
			BigDecimal convertThis = new BigDecimal(numberIn);
			BigDecimal displayNumber = convertThis.setScale(decPositions, BigDecimal.ROUND_HALF_UP);
			returnString.append(displayNumber.toString());
		}
		catch(Exception e)
		{
		}
		return returnString.toString();
	}
	/* 
	 * Send in any GTIN Number and will return a display with the
	 * Spaces in the Correct area.
	 */
	public static String maskGTINNumber(String gtinNumberIn) {
		StringBuffer returnString = new StringBuffer();
		if (gtinNumberIn.length() == 14)
		{
			returnString.append(gtinNumberIn.substring(0, 2));
			returnString.append(" ");
			returnString.append(gtinNumberIn.substring(2, 8));
			returnString.append(" ");
			returnString.append(gtinNumberIn.substring(8, 13));
			returnString.append(" ");
			returnString.append(gtinNumberIn.substring(13, 14));
		}
		if (returnString.toString().length() == 0)
			returnString.append(gtinNumberIn);
		return returnString.toString();
	}
	/* 
	 * Send in any UPC Code and will return a display with the
	 * Spaces in the Correct area.
	 */
	public static String maskUPCCode(String upcCodeIn) {
		StringBuffer returnString = new StringBuffer();
		if (upcCodeIn.length() == 12)
		{
			returnString.append(upcCodeIn.substring(0, 1));
			returnString.append(" ");
			returnString.append(upcCodeIn.substring(1, 6));
			returnString.append(" ");
			returnString.append(upcCodeIn.substring(6, 11));
			returnString.append(" ");
			returnString.append(upcCodeIn.substring(11, 12));
		}
		if (upcCodeIn.length() == 10)
		{
			returnString.append(upcCodeIn.substring(0, 5));
			returnString.append(" ");
			returnString.append(upcCodeIn.substring(5, 10));
		}	
		if (returnString.toString().length() == 0)
			returnString.append(upcCodeIn);
		return returnString.toString();
	}
	/* 
	 * Send in any Time, String.  
	 *   From 0 - 6 characters
	 * Will return a time with colon's in it.
	 */
	public static String maskTime(String timeIn) {
		StringBuffer returnString = new StringBuffer();
		if (timeIn.length() < 6)
		{
			for (int l = 0; l < 6; l++)
			{
				if (timeIn.length() == l)
					timeIn = "0" + timeIn;
			}
		}
		if (timeIn.length() == 6)
		{
			if (new Integer(timeIn.substring(0, 2)).intValue() >= 12)
			{
				int timeInHour = (new Integer(timeIn.substring(0, 2)).intValue() - 12);
				if (timeInHour < 10)
					returnString.append("0" + timeInHour);
				else
				   returnString.append(timeInHour);
			}
			else
			   returnString.append(timeIn.substring(0, 2));
			returnString.append(":");
			returnString.append(timeIn.substring(2, 4));
			returnString.append(":");
			returnString.append(timeIn.substring(4, 6));
			if (new Integer(timeIn.substring(0, 2)).intValue() >= 12)
				returnString.append(" pm");
			else
				returnString.append(" am");
		}
		return returnString.toString();
	}
	/* 
	 * Send in a String which is a number.
	 *   Convert it to BigDecimal, and then mask with comma
	 * Created February 7, 2006  TWalton
	 */
	public static String maskInteger(String numberIn) {
 	   DecimalFormat integerf = new DecimalFormat("#,###,##0");  
		StringBuffer returnString = new StringBuffer();
		try
		{
			BigDecimal convertThis = new BigDecimal(numberIn);
			returnString.append(integerf.format(convertThis));
		}
		catch(Exception e)
		{
		}
		if (returnString.toString().equals(""))
			returnString.append(numberIn);
		
		return returnString.toString();
	}
	
	
	public static String maskAccountingFormat(BigDecimal number) {
		return maskAccountingFormat(number, 0, false);
	}
	
	public static String maskAccountingFormat(BigDecimal number, int decimalPlaces, boolean displayZero) {
		
		if (!displayZero && number.compareTo(BigDecimal.ZERO) == 0) {
			return "";
		}
		
		String result = "";
		String decimals = "";
		
		String format = "#,###,##0";
		
		for (int i=0; i<decimalPlaces; i++) {
			if (i == 0) {
				format += ".";
			}
			format += "0";
		}
		
		DecimalFormat df = new DecimalFormat(format + ";(" + format + ")");
		
		result = df.format(number);
		
		
		result = "<div class='left'>" +
				"<span style='position:absolute;'>$</span>" + 
			"<span style='margin-left:.5em; float:right;'>" + 
			result + 
			"</span>" + 
			"</div>";
		
		return result;
	}
	
	public static String maskCurrency(BigDecimal value) {
		return maskCurrency(value, 0, false);
	}
	
	public static String maskCurrency(BigDecimal value, int scale, boolean displayZero) {;
		String result = "";
		
		
		
		if (value.compareTo(BigDecimal.ZERO) != 0 || displayZero) {
			
			String currencySymbol = Currency.getInstance(Locale.getDefault()).getSymbol();
			String decimals = "";
			for (int i=0; i<scale; i++) {
				decimals += "0";
			}
			if (scale > 0) {
				decimals = "." + decimals;
			}
			String format = "#,###,###,##0" + decimals;
			
			DecimalFormat df = new DecimalFormat(currencySymbol + format + ";" + currencySymbol + "(" + format + ")");

			
			result = df.format(value);
			
		}

		
		
		return result;
	}
	
	/* 
	 * Send in a String which is a number.
	 *   Convert it to BigDecimal, and then mask with Dollar Sign 
	 * 			and TWO Decimal Positions
	 * Created February 8, 2006  TWalton
	 */
	public static String mask2DecimalDollar(String numberIn) {
	   DecimalFormat df = new DecimalFormat("$#,###,##0.00");  
		StringBuffer returnString = new StringBuffer();
		try
		{
			BigDecimal convertThis = new BigDecimal(numberIn);
			returnString.append(df.format(convertThis));
		}
		catch(Exception e)
		{
		}
		if (returnString.toString().equals(""))
			returnString.append(numberIn);
		
		return returnString.toString();
	}
	
	/**
	 * Method overload to accept BigDecimal
	 * Defaults to zero decimals and shows blank for zero value
	 * @param number
	 * @return formatted string
	 * @author jhagle
	 */
	public static String maskBigDecimal(BigDecimal number) {
		return maskBigDecimal(number, 0, false);
	}
	
	/**
	 * Method overload to accept BigDecimal and number of decimal places
	 * Shows blank for zero value
	 * @param number
	 * @return formatted string
	 * @author jhagle
	 */
	public static String maskBigDecimal(BigDecimal number, int decimalPositions) {

		return maskBigDecimal(number, decimalPositions, false);
		
	}
	
	/**
	 * Method overload to accept BigDecimal, number of decimal places, and optionally show blank for zero value
	 * @param number
	 * @return formatted string
	 * @author jhagle
	 */
	public static String maskBigDecimal(BigDecimal number, int decimalPositions, boolean displayZero) {
		
		if (!displayZero && number.compareTo(BigDecimal.ZERO) == 0) {
			return "";
		}

		return maskBigDecimal(number.toString(), decimalPositions);
		
	}
	
	/* 
	 * Send in a String which is a number.
	 *   Convert it to BigDecimal, and then mask with comma & So many decimal positions.
	 * Created March 3, 2006  TWalton
	 */
	public static String maskBigDecimal(String numberIn, int decimalPositions) {
	   DecimalFormat formatNumber = new DecimalFormat("#,###,##0.#");
	   if (decimalPositions == 1)	
	      formatNumber = new DecimalFormat("#,###,##0.0#");
	   if (decimalPositions == 2)
	   	  formatNumber = new DecimalFormat("#,###,##0.00#");
	   if (decimalPositions == 3)	
	      formatNumber = new DecimalFormat("#,###,##0.000#");
	   if (decimalPositions == 4)
	   	  formatNumber = new DecimalFormat("#,###,##0.0000#");
	   if (decimalPositions == 5)
		  formatNumber = new DecimalFormat("#,###,##0.00000#");
	   
	   StringBuffer returnString = new StringBuffer();
		try
		{
			numberIn = maskNumber(numberIn, decimalPositions);
			BigDecimal convertThis = new BigDecimal(numberIn);
			returnString.append(formatNumber.format(convertThis));
		}
		catch(Exception e)
		{
		}
		if (returnString.toString().equals(""))
			returnString.append(numberIn);
		
		return returnString.toString();
	}
	/* 
	 * Send in a String which is a Y or N (flag).
	 *   Convert it to Yes or No
	 * Created March 3, 2006  TWalton
	 */
	public static String maskYesNo(String valueIn) {
		StringBuffer returnValue = new StringBuffer();
		if (valueIn != null &&
			valueIn.equals("Y"))
			returnValue.append("Yes");
		if (valueIn != null &&
			valueIn.equals("N"))
			returnValue.append("No");
		
		return returnValue.toString();
	}
	
	/**
	 * Formats number as percent with zero decimals and does not display zero values
	 * @param number
	 * @param decimals
	 * @param showZero
	 * @return
	 */
	public static String maskPercent(BigDecimal number) {
		int decimals = 0;
		if (number.compareTo(new BigDecimal(0.1)) < 0
				&& number.compareTo(new BigDecimal(-0.1)) > 0) {
			decimals = 1;
		}
		if (number.compareTo(new BigDecimal(0.01)) < 0
				&& number.compareTo(new BigDecimal(-0.01)) > 0) {
			decimals = 2;
		}
		return maskPercent(number, decimals, false);
	}
	
	/**
	 * Formats number as percent
	 * @param number
	 * @param decimals
	 * @param showZero
	 * @return
	 */
	public static String maskPercent(BigDecimal number, int decimals, boolean showZero) {
		String result = "";
		
		try {
			
			if (number != null && (number.compareTo(BigDecimal.ZERO) != 0 || showZero)) {
				String format = "#,##0";
				if (decimals > 0) {
					format += ".";
				}
				for (int i=0; i<decimals; i++) {
					format += "0";
				}
				
				format += "%";
				
				DecimalFormat df = new DecimalFormat(format);
				result = df.format(number);
			}
			
		} catch (Exception e) {
			System.err.println(e);
		}
				
		return result;
	}
	
	/**
	 * Method overload to format percent
	 * Accepts BigDecimal, does not show if value is zero
	 * @param number
	 * @return
	 * @author jhagle
	 */
	public static String mask2DecimalPercent(BigDecimal number) {
		return mask2DecimalPercent(number, false);
	}
	
	/**
	 * Method overload to format percent
	 * Accepts BigDecimal, option to not show if value is zero
	 * @param number
	 * @return
	 * @author jhagle
	 */
	public static String mask2DecimalPercent(BigDecimal number, boolean displayZero) {
		if (!displayZero && number.compareTo(BigDecimal.ZERO) == 0) {
			return "";
		}
		return mask2DecimalPercent(number.toString());
	}
	
	/* 
	 * Send in a String which is a number.
	 *   Convert it to BigDecimal,
	 *     Multiply it by 100 
	 *      and then mask with Percent Sign 
	 * 			and TWO Decimal Positions
	 * Created February 8, 2006  TWalton
	 */
	public static String mask2DecimalPercent(String numberIn) {
	   DecimalFormat df = new DecimalFormat("#,###,##0.00%");  
		StringBuffer returnString = new StringBuffer();
		try
		{
			BigDecimal convertThis = new BigDecimal(numberIn);
		// When it is formatted it automatically multiplies by 100 to create a percent
			returnString.append(df.format(convertThis));
		}
		catch(Exception e)
		{
		}
		if (returnString.toString().equals(""))
			returnString.append(numberIn);
		
		return returnString.toString();
	}
	/* 
	 * Send in a String which is a number.
	 *   Convert it to BigDecimal,
	 *    Will Mask with comma's and percent sign
	 *   AND 2 decimal positions
	 * Created March 28, 2006  TWalton
	 */
	public static String mask2Decimal(String numberIn) {
	   DecimalFormat df = new DecimalFormat("#,###,##0.00");  
		StringBuffer returnString = new StringBuffer();
		try
		{
			BigDecimal convertThis = new BigDecimal(numberIn);
		// When it is formatted it automatically multiplies by 100 to create a percent
			returnString.append(df.format(convertThis));
		}
		catch(Exception e)
		{
		}
		if (returnString.toString().equals(""))
			returnString.append(numberIn);
		
		return returnString.toString();
	}
	/**
	 *   Method Created 9/17/09 TWalton
	 *   // Add Zero's to the front of a number... 
	 * @param -- Send in the String (value) and int (LengthNeeded)
	 * @return String
	 */
	public static String addZerosToFrontOfField(String value, int lengthNeeded)
	{
		if (value.length() < lengthNeeded)
		{
			for (int x = value.length(); x < lengthNeeded; x++)
			{
				value = "0" + value.trim();
			}
		}
		// return value
		return value;
	}
	/* 
	 * Send in a String which is a number.
	 *   Convert it to BigDecimal, and then mask with comma & So many decimal positions.
	 * Created March 3, 2006  TWalton
	 */
	public static String maskBigDecimalNoComma(String numberIn, int decimalPositions) {
	   DecimalFormat formatNumber = new DecimalFormat("######0.#");
	   if (decimalPositions == 1)	
	      formatNumber = new DecimalFormat("######0.0#");
	   if (decimalPositions == 2)
	   	  formatNumber = new DecimalFormat("######0.00#");
	   if (decimalPositions == 3)	
	      formatNumber = new DecimalFormat("######0.000#");
	   if (decimalPositions == 4)
	   	  formatNumber = new DecimalFormat("######0.0000#");
	   if (decimalPositions == 5)
		  formatNumber = new DecimalFormat("######0.00000#");
	
	   StringBuffer returnString = new StringBuffer();
		try
		{
			numberIn = maskNumber(numberIn, decimalPositions);
			BigDecimal convertThis = new BigDecimal(numberIn);
			returnString.append(formatNumber.format(convertThis));
		}
		catch(Exception e)
		{
		}
		if (returnString.toString().equals(""))
			returnString.append(numberIn);
		
		return returnString.toString();
	}
}
