package com.treetop;

import java.math.*;
import com.treetop.data.*;
/**
 * Insert the type's description here.
 * Creation date: (12/18/2002 1:14:28 PM)
 * @author: 
 */
public class ValidateFields {
	/**
	 *  Validates an eMail address. 
	 *  Returns an error message in the form of a String,
	 *  Otherwise the error message is empty for a valid address.
	 *
	 *  Creation date: (7/27/2004 8:29:37 AM)
	 */
	public static String validateEMail(String inEMail) {
	
	    String returnMessage = "";
		String errorMessage  = " The eMail address of (" + inEMail +
							   "), is invalid.  " +
							   " Please try again. \n";
	    try {
	    	
	    	if ((inEMail == null) || (inEMail.equals ("")))
				returnMessage = " The eMail address is empty.  " +										
							    " Please try again. \n";	    
	    	else {
				int a = inEMail.trim().indexOf("@");		        
				if (a >= 1) {	
						
					String afterAt = inEMail.trim().substring(a, inEMail.trim().length());									// At least one char. in front of the @ sign
					int d = afterAt.trim().indexOf(".") + a;
					if (d >= a+2) {	
															// Dot required and must follow the @ sign	
						if (d+1 < inEMail.trim().length())	// At least one character following the . (dot)
							returnMessage = "";	
						
						else
							returnMessage = errorMessage;
					}
					else
						returnMessage = errorMessage;
				}				
	    		else
	    			returnMessage = errorMessage;	    	
	    	}
				
	    }
		catch(Exception e) {	
			returnMessage = " The eMail address of (" + inEMail +
			   	            "), has a value that can not be edited.  " +
			       	        " Please try again. \n";
		}		   
	    		
		return returnMessage;
	}
	/**
	 * Test the methods.
	 *
	 * Creation date: (07/27/2004 1:20:32 PM)	
	 */
	public static void main(String[] args) {
	
		try {	
	
			Integer number = new Integer("100");					
	//		String  msg    = ValidateFields.validateBroker(number);
				
	//		System.out.println("Broker: " + number + "  " + msg + " validated");	    
		}
		
		catch(Exception e) {
			System.out.println("validateBroker error: " + e); 
		}				
		
	}
	/**
	 *  Test to see if a String Value is a valid Integer
	 *  Returns an Error Message in the form of a String.
	 *   "" if OK OR if null is sent in.
	 *
	 * Creation date: (7/22/2004 8:29:37 AM)
	 */
	public static String validateInteger(String inNumber) 
	{
	    String returnMessage = "";
	    if (inNumber != null)
	    {
	    	try
			{
				Integer testNumber  = new Integer(inNumber);
			}
			catch(Exception e)
			{
				// Not a valid number
				returnMessage = " The Value " + inNumber +
			    	            " is not a whole number.  " +
			        	        " Please try again. \n";
			}
	    }		
		return returnMessage;
	}
	/**
	 *  Validate the sample request number. 
	 *  Returns an error message in the form of a String,
	 *  Otherwise the error message is empty for a valid sample number.
	 *
	 *  Creation date: (11/16/2004 8:29:37 AM)
	 */
	public static String validateSampleNumber(Integer inSampleNumber) {

		String returnMessage  = "";	   
		String errorMessage   = " The sample request number of (" + inSampleNumber +
								"), is invalid.  " +
								" Please try again. \n";		
		try {

			if (inSampleNumber == null)
				returnMessage = " The sample request number is empty.  " +										
								" Please try again. \n";	    
			else {		
				
				try {							
												
					SampleRequestOrder sampleRequest = new SampleRequestOrder(inSampleNumber);
					if (sampleRequest.getSampleNumber() == null)					
						returnMessage = " The sample request number of (" + inSampleNumber +
										"), is not setup in a sample request order.  " +
										" Please try again. \n";									 
				}
				catch (Exception e) {
					returnMessage = errorMessage;
				}					

			}				
						
		}
		catch(Exception e) {	
			returnMessage = " The sample request number of (" + inSampleNumber +
							"), has a value that can not be edited.  " +
							" Please try again. \n";					  
		}		   	    
		
		return returnMessage;
	}
	/**
	 *  Test to see if the From Value is less than the To Value
	 *  Returns an Error Message in the form of a String.
	 *
	 * Creation date: (5/21/2004 10:49:37 AM)
	 */
	public static String testRangeInteger(Integer fromValue,
		                                  Integer toValue) 
	{
	    String returnMessage = "";
	    
		if (fromValue == null ||
		    toValue == null)
		   returnMessage = "Not Enough Information to test the range. \n" +
		                   " fromValue = " + fromValue + "  " +
		                   " toValue = " + toValue + ". ";	
		else
		{     
			//**** RANGE TEST ****//	
			 if (fromValue.intValue() > toValue.intValue())
			 {
				 returnMessage = "The From Number - " + fromValue +
								 " must to be less than or equal to " +
								 "the To Number - " +
								 toValue + ". \n";
			 }
		}
			
		return returnMessage;
	}
	/**
	 *  Test to see if the From Value is less than the To Value
	 *  Returns an Error Message in the form of a String.
	 *
	 * Creation date: (5/21/2004 11:00:37 AM)
	 */
	public static String testRangeDate(java.sql.Date fromValue,
									   java.sql.Date toValue) 
	{
	    String returnMessage = "";
	    
		if (fromValue == null ||
		    toValue == null)
		   returnMessage = "Not Enough Information to test the range. \n" +
		                   " fromValue = " + fromValue + "  " +
		                   " toValue = " + toValue + ". ";	
		else
		{     
			//**** RANGE TEST ****//
			int testDate = fromValue.compareTo(toValue);
	
			if (testDate > 0) //** Error if True	
			{
				returnMessage = "To Date must be greater than " +
					  			"or equal to From Date. " +
					   			" From Date of " + fromValue +  " " +
					   			" To Date of " + toValue +  ". \n ";	
			}			
		}
			
		return returnMessage;
	}
	/**
	 *  Test to see if the From Value is less than the To Value
	 *  Returns an Error Message in the form of a String.
	 *
	 * Creation date: (5/21/2004 10:55:37 AM)
	 */
	public static String testRangeBigDecimal(BigDecimal fromValue,
		                                     BigDecimal toValue) 
	{
	    String returnMessage = "";
	    
		if (fromValue == null ||
		    toValue == null)
		   returnMessage = "Not Enough Information to test the range. \n" +
		                   " fromValue = " + fromValue + "  " +
		                   " toValue = " + toValue + ". ";	
		else
		{     
			//**** RANGE TEST ****//
			int testBigDecimal = fromValue.compareTo(toValue);
			if (testBigDecimal > 0)
			{				
			 	 returnMessage = "The From Number - " + fromValue +
								 " must to be less than or equal to " +
								 "the To Number - " +
								 toValue + ". \n";
			 }
		}
					
		return returnMessage;
	}
	/**
	 *  Test to see if the From Value is less than the To Value
	 *  Returns an Error Message in the form of a String.
	 *
	 * Creation date: (5/21/2004 11030:37 AM)
	 */
	public static String testRangeTime(java.sql.Time fromValue,
									   java.sql.Time toValue) 
	{
	    String returnMessage = "";
	    
		if (fromValue == null ||
		    toValue == null)
		   returnMessage = "Not Enough Information to test the range. \n" +
		                   " fromValue = " + fromValue + "  " +
		                   " toValue = " + toValue + ". ";	
		else
		{     
			//**** RANGE TEST ****//
			int testTime = fromValue.compareTo(toValue);
	  
			if (testTime > 0) //** Error if True	
			{
				returnMessage = "To Time must be greater than " +
					  			"or equal to From Time. " +
					   			" From Time of " + fromValue +  " " +
					   			" To Time of " + toValue +  ". \n ";	
			}			
		}
			
		return returnMessage;
	}
	/**
	 *  Test to see if the From Value is less than the To Value
	 *  Returns an Error Message in the form of a String.
	 *
	 * Creation date: (5/21/2004 10:39:37 AM)
	 */
	public static String testRangeString(String fromValue,
		                                 String toValue) 
	{
	    String returnMessage = "";
	    
		if (fromValue == null ||
		    fromValue.trim().equals("") ||
		    toValue == null ||
		    toValue.trim().equals(""))
		   returnMessage = "Not Enough Information to test the range. \n" +
		                   " fromValue = " + fromValue + "  " +
		                   " toValue = " + toValue + ". ";	
		else
		{                       
		  //**** RANGE TEST****//		
		  int testString = fromValue.toUpperCase().compareTo(toValue.toUpperCase());
	
		  if (testString > 0) //** Error if True	
	   	 	returnMessage = "To Value must be greater than " +
			                "or equal to From Value. " +
			    		    " From Value of " + fromValue +  " " +
			 				" To Value of " + toValue +  ". \n";	
		}
			
		return returnMessage;
	}
	/**
	 *  Test to see if a String Value is a valid BigDecimal
	 *  Returns an Error Message in the form of a String.
	 *   "" if OK OR if null is sent in.
	 *
	 * Creation date: (7/22/2004 8:29:37 AM)
	 */
	public static String validateBigDecimal(String inNumber) 
	{
	    String returnMessage = "";
	    if (inNumber != null)
	    {
	    	try
			{
				BigDecimal testNumber  = new BigDecimal(inNumber);
			}
			catch(Exception e)
			{
				// Not a valid number
				returnMessage = " The Value " + inNumber +
			    	            " is not a number.  " +
			        	        " Please try again. \n";
			}
	    }
	    		
		return returnMessage;
	}
	/**
	 * ValidateFields constructor comment.
	 */
	public ValidateFields() {
		super();
	}

}
