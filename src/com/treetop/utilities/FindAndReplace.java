/*
 * Created on Feb 7, 2007
 *
 */
package com.treetop.utilities;

import java.util.*;
/**
 * @author twalto
 * Use this class to Find and Replace values 
 *     within a String, number, whatever
 *
 */
public class FindAndReplace {

	/**
	 * Use this method to replace 
	 *   one value with another --
	 * 
	 * Send in String value to search and remove
	 *    the Value to Search For and What to Replace it With 
	 * 
	 * Will return a String   
	 * Creation date: TWalton 2/7/2007
	 */
	public static String remove(String inData, String inSearchFor) {

		StringBuffer returnValue = new StringBuffer();
		if (inData != null) // Return if inData is null
		{
			if (inSearchFor == null) // if the inSearchFor or inReplaceWith is null return inData Value
				returnValue.append(inData);
			else
			{
				int totalLength = inData.length();
				int startSpot = 0; // Beginning of where to search for the inSearchFor Value
				int foundAt = 0;   // Where the inSearchFor is found in the inData String
				
				// As long as the startSpot is less than the totalLength
				while (startSpot < totalLength)
				{
					if ((foundAt = inData.indexOf(inSearchFor, startSpot)) == -1) 
					{ // if the inSearchFor is not found, 
						// add the rest of the inData String to the returnValue	
						returnValue.append(inData.substring(startSpot)); // Add from the startSpot to the END
						startSpot = totalLength; // Did not find inSearchFor in the String, so return
					}	
					else
					{	
						// Substring to the point the inSearchFor value is found
						returnValue.append(inData.substring(startSpot, foundAt));
						
					   startSpot = startSpot + foundAt + inSearchFor.length();
					}
				}
			}
		}
		return returnValue.toString();
	} // END of the replace method
	
	/**
	 * Use this method to replace 
	 *   an apostrophe with a double apostrophe --
	 * 
	 * Send in String value to search and replace 
	 * 
	 * Will return a String   
	 * Creation date: TWalton 2/7/2007
	 */
	public static String replaceApostrophe(String inData) {

		return replace(inData, "'", "''");
	}	// END of the replaceApostrophe method

	/**
	 * Use this method to replace 
	 *   one value with another --
	 * 
	 * Send in String value to search and replace
	 *    the Value to Search For and What to Replace it With 
	 * 
	 * Will return a String   
	 * Creation date: TWalton 2/7/2007
	 */
	public static String replace(String inData, String inSearchFor, String inReplaceWith) {
	
		StringBuffer returnValue = new StringBuffer();
		if (inData != null) // Return if inData is null
		{
			if (inSearchFor == null ||
				 inReplaceWith == null) // if the inSearchFor or inReplaceWith is null return inData Value
				returnValue.append(inData);
			else
			{
				int totalLength = inData.length();
				int startSpot = 0; // Beginning of where to search for the inSearchFor Value
				int foundAt = 0;   // Where the inSearchFor is found in the inData String
				
				// As long as the startSpot is less than the totalLength
				while (startSpot < totalLength)
				{
					if ((foundAt = inData.indexOf(inSearchFor, startSpot)) == -1) 
					{ // if the inSearchFor is not found, 
						// add the rest of the inData String to the returnValue	
						returnValue.append(inData.substring(startSpot)); // Add from the startSpot to the END
						startSpot = totalLength; // Did not find inSearchFor in the String, so return
					}	
					else
					{	
						// Substring to the point the inSearchFor value is found
						returnValue.append(inData.substring(startSpot, foundAt));
						// Replace with
						returnValue.append("''");
					   startSpot = startSpot + foundAt + inSearchFor.length();
					}
				}
			}
		}
		return returnValue.toString();
	} // END of the replace method

	/**
	 * Use this method to replace 
	 *   a quote with the word quote 
	 * 
	 * Send in String value to search and replace 
	 * 
	 * Will return a String   
	 * Creation date: TWalton 7/29/2010
	 */
	public static String replaceQuoteWithWord(String inData) {
	
		return inData.replace("\"", "quote");
	}	// END of the replaceQuote method
	/**
	 * Use this method to replace 
	 *   the word quote with a /" 
	 * 
	 * Send in String value to search and replace 
	 * 
	 * Will return a String   
	 * Creation date: TWalton 7/29/2010
	 */
	public static String replaceWordWithQuote(String inData) {
	
		return inData.replace("quote", "\"");
	}	// END of the replaceQuote method
	
	
	/**
	 * Send in the string to be modified and a vector of the characters to be removed.
	 * This method will remove the characters.  You will not get them back!
	 * @param String originalString
	 * @param Vector chars
	 * @return String modifiedString
	 */
	public static String removeCharacters(String inData, Vector chars) {
		if (inData != null && chars != null) {
			for (int i=0; i<chars.size(); i++) {
				inData = inData.replaceAll((String) chars.elementAt(i), "");
			}
		}
		return inData;
	}
	
	public static String sanitizeEncoding(String input) {

		if (input == null || input.equals("")) {
			return "";
		}
		try {
			input = java.net.URLEncoder.encode(input, "UTF-8");
			
			input = input.replaceAll("%C3%82%", "%");
			input = input.replaceAll("%83%C2%", "%");
					
			input = java.net.URLDecoder.decode(input, "UTF-8");
		} catch (Exception e) {
			
		}
		return input;

	}
	
} // END of ENTIRE CLASS
