/*
 * Created on Jul 27, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.utilities.html;

/**
 * @author twalto
 * Create Methods to standardize the Errors displayed in the JSP's
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JSPExceptionMessages {
	
	/* Use this method to Get a Standard Exception message for 
	 * any problem retrieving a VIEW Bean
	 */
	public static String ViewBeanExceptSystem(String jspPath, 
											  String caughtException) {
	    //Returns String for the System Out Println								
		StringBuffer returnString = new StringBuffer();
		    returnString.append(jspPath);
			returnString.append(": an Exception occurred when retrieving the view bean: ");
			returnString.append(caughtException);
			
		return returnString.toString();
	}	
	
	/* Use this method Get a Standard Exception to be used
	 * in the alert msg:
	 *    When problem is retrieving a VIEW Bean
	 */
	public static String ViewBeanExceptMsg(String jspPath) {
		//Returns String for the msg display on the page.								
		StringBuffer returnString = new StringBuffer();
		
			returnString.append("There is a problem retrieving data to display on this page(");
			returnString.append(jspPath);
			returnString.append("). \\n Please contact the help desk at ext. 1425.");
		
		return returnString.toString();
	}	
		
	/* Use this method to Get a Standard Exception message for 
	 * any problem displaying entire page
	 */
	public static String PageExceptSystem(String jspPath, 
										  String caughtException) {
		//Returns String for the System Out Println								
		StringBuffer returnString = new StringBuffer();
			returnString.append(jspPath);
			returnString.append(": an Exception occurred when trying to display this page: ");
			returnString.append(caughtException);
			
		return returnString.toString();
	}	
	
	/* Use this method Get a Standard Exception to be used
	 * in the alert msg:
	 *    When problem is with displaying the entire Page
	 */
	public static String PageExceptMsg(String jspPath) {
		//Returns String for the msg display on the page.								
		StringBuffer returnString = new StringBuffer();
		
			returnString.append("There is a problem displaying this page(");
			returnString.append(jspPath);
			returnString.append("). \\n Please contact the help desk at ext. 1425.");
		
		return returnString.toString();
	}		
}
