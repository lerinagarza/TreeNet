/*
 * Created on Jul 28, 2005
 */
package com.treetop.utilities.html;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjects.DateTime;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.GeneralUtility;

/**
 * @author twalto
 * Use this class to put Standard HTML Code into, for use in JSP's
 *
 */
public class HTMLHelpers {
	/**
	 *   GOOGLE STYLE OBJECT
	 * Receive In:
	 * 		* Which Record to Start With
	 * 		* How Many Total Records
	 * 		* How Many Records On a Page
	 * 		*  Selection URL
	 *
	 * Return:
	 *    String Array with 2 Elements
	 *      1 - Top of page : Example: Results 1 - 10 of about 5,000
	 *      2 - Bottom of page: Example: Result Page: Previous 11 12 13 14 15 Next     
	 *
	 * Creation date: (12/21/2004 9:22:37 AM)
	 * Update date:  11/28/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 */
	public static String[] resultSubFile(
		BigDecimal startRecord,
		BigDecimal totalRecords,
		BigDecimal recordsOnPage,
		String inUrl) {
		StringBuffer stringA = new StringBuffer();
		StringBuffer stringB = new StringBuffer();

		String stylePreviousNext =
			" style=\"font-family:arial; font-size:10pt; color:#006400\" ";
		String styleResultPage =
			" style=\"font-family:arial; font-size:8pt; color:#000000\" ";
		String stylePageLink =
			" style=\"font-family:arial; font-size:8pt; color:#000000; font-weight: normal \" ";
		String stylePageOn =
			" style=\"font-family:arial; font-size:8pt; color:#990000;"
				+ " font-weight:bold\" ";

		if (startRecord == null
			|| startRecord.compareTo(new BigDecimal("0")) == 0)
			startRecord = new BigDecimal("0");
		if (totalRecords == null
			|| totalRecords.compareTo(new BigDecimal("0")) == 0)
			totalRecords = new BigDecimal("0");
		if (recordsOnPage == null
			|| recordsOnPage.compareTo(new BigDecimal("0")) == 0)
			recordsOnPage = new BigDecimal("0");

		//-----------------------------------------  
		try {
			// Bottom part of Page                 
			BigDecimal howManyPages = new BigDecimal("1");
			if (totalRecords.compareTo(recordsOnPage) > 0)
				howManyPages = totalRecords.divide(recordsOnPage, 0, 0);

			BigDecimal pageYouAreOn = new BigDecimal("1");
			if (startRecord.compareTo(new BigDecimal("0")) > 0)
				pageYouAreOn = startRecord.divide(recordsOnPage, 0, 0);

			BigDecimal firstPageLink = new BigDecimal("1");
			BigDecimal lastPageLink = howManyPages;
			if (howManyPages.compareTo(new BigDecimal("10")) > 0) {
				if (pageYouAreOn.compareTo(new BigDecimal("10")) > 0)
					firstPageLink = pageYouAreOn.subtract(new BigDecimal("10"));
				if (howManyPages
					.compareTo(pageYouAreOn.add(new BigDecimal("9")))
					> 0)
					lastPageLink = pageYouAreOn.add(new BigDecimal("9"));
			}

			stringB.append(" <font ");
			stringB.append(styleResultPage);
			stringB.append(">Result Page:</font> ");
			if (pageYouAreOn.compareTo(new BigDecimal("1")) > 0) {
				stringB.append("<a");
				stringB.append(stylePreviousNext);
				stringB.append(" href=\"");
				stringB.append(inUrl);
				stringB.append("&startRecord=");
				stringB.append(startRecord.subtract(recordsOnPage).toString());
				stringB.append("\">Previous</a>&nbsp;");
			}
			int pageLink = firstPageLink.intValue();
			for (int x = 0;
				x
					< (lastPageLink
						.subtract(firstPageLink.subtract(new BigDecimal("1"))))
						.intValue();
				x++) {
				if (new BigDecimal(pageLink + "").compareTo(pageYouAreOn)
					== 0) {
					stringB.append(" <font ");
					stringB.append(stylePageOn);
					stringB.append(">");
					stringB.append(pageLink);
					stringB.append("</font>&nbsp;");
				} else {
					stringB.append("<a");
					stringB.append(stylePageLink);
					stringB.append(" href=\"");
					stringB.append(inUrl);
					stringB.append("&startRecord=");
					stringB.append(
						(new BigDecimal(pageLink).multiply(recordsOnPage))
							.subtract(
								(recordsOnPage.subtract(new BigDecimal("1"))))
							.toString());
					stringB.append("\">");
					stringB.append(pageLink);
					stringB.append("</a>&nbsp;");
				}
				pageLink++;
			}

			if (pageYouAreOn.compareTo(howManyPages) < 0) {
				stringB.append("<a");
				stringB.append(stylePreviousNext);
				stringB.append(" href=\"");
				stringB.append(inUrl);
				stringB.append("&startRecord=");
				stringB.append(startRecord.add(recordsOnPage).toString());
				stringB.append("\">Next</a>&nbsp;");
			}

			//	Top part of Page

			String endOfPage =
				displayNumber(
					(startRecord
						.add((recordsOnPage.subtract(new BigDecimal("1"))))),
					0);
			if (pageYouAreOn.compareTo(howManyPages) == 0)
				endOfPage = totalRecords.toString();
			stringA.append("<font ");
			stringA.append(styleResultPage);
			stringA.append(">Results <b>");
			stringA.append(displayNumber(startRecord, 0));
			stringA.append(" - ");
			stringA.append(endOfPage);
			stringA.append("</b> of about <b>");
			stringA.append(displayNumber(totalRecords, 0));
			stringA.append("</b></font>");

		} catch (Exception e) {
		}
		if (stringA.toString().equals(""))
			stringA.append("&nbsp;");
		if (stringB.toString().equals(""))
			stringB.append("&nbsp;");
		String[] returnString = new String[2];
		returnString[0] = stringA.toString();
		returnString[1] = stringB.toString();

		return returnString;
	}
	/**
	 * To Use this button you MUST in the Head section of the JSP
	 *    get from the JavaScriptInfo Class
	 *          getChangeSubmitButton
	 *   and    getClickButtonOnlyOnce
	 * 
	 * Receive In:
	 *     buttonName -- name to access the parameter
	 *   Default buttonName = submit.
	 *     buttonValue -- Words to Appear on the button
	 *   Default buttonValue = update.
	 *
	 * Creation date: (1/18/2005 TWalton)
	 * Update date:  7/29/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 * @deprecated
	 */
	public static String buttonSubmitRight(String buttonName, String buttonValue) {
		
		if (buttonName == null || buttonName.trim().equals(""))
			buttonName = "submit";
		if (buttonValue == null || buttonValue.trim().equals(""))
			buttonValue = "update";
	
		StringBuffer returnString = new StringBuffer();
	
		returnString.append("<input name=\"");
		returnString.append(buttonName);
		returnString.append("\"");
		returnString.append("align=\"right\" ");
		returnString.append("onClick=\"submitForm(this); return countClicks()\" ");
		returnString.append("type=\"Submit\" value=\"");
		returnString.append(buttonValue);
		returnString.append("\">");
	
		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     BigDecimal -- number
	 *     int        -- number of decimal positions to be displayed.
	 *
	 * Creation date: (9/12/2003 2:39:37 PM)
	 */
	public static String displayNumber(BigDecimal number,
		                               int decimalPositions) 
	{
		StringBuffer returnString = new StringBuffer();
	try
	{
		try
		{	
			Integer test = new Integer(decimalPositions);
		}
		catch(Exception e)
		{  // Set to 0 if error is caught
			decimalPositions = 0;
		}
		if (number != null)
		{   
			BigDecimal displayNumber = number.setScale(decimalPositions, BigDecimal.ROUND_HALF_UP);
			returnString.append(displayNumber);
		}
	}
	catch(Exception e)
	{
		System.out.println("Problem in HTMLCode.displayNumber(BigDecimal, int): " +
		                    e);	
	}
	   if (returnString.toString().equals(""))
	      returnString.append("&nbsp;");
			
		return returnString.toString();
	}
	/**
	 * DO NOT USE, ***********
	 * To Use this button you MUST in the Head section of the JSP
	 *    get from the JavaScriptInfo Class
	 *          getChangeSubmitButton
	 *   and    getClickButtonOnlyOnce
	 * 
	 * Receive In:
	 *     buttonName -- name to access the parameter
	 *   Default buttonName = submit.
	 *     buttonValue -- Words to Appear on the button
	 *   Default buttonValue = update.
	 *
	 * Creation date: (1/18/2005 TWalton)
	 * Update date:  7/29/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 * @deprecated
	 * 
	 */
	public static String buttonSubmit(String buttonName, 
									  String buttonValue) {
		
		if (buttonName == null || buttonName.trim().equals(""))
			buttonName = "submit";
		if (buttonValue == null || buttonValue.trim().equals(""))
			buttonValue = "update";

		StringBuffer returnString = new StringBuffer();

		returnString.append("<input name=\"");
		returnString.append(buttonName);
		returnString.append("\"");
	    returnString.append("onClick=\"submitForm(this); return countClicks()\" ");
		returnString.append("type=\"Submit\" value=\"");
		returnString.append(buttonValue);
		returnString.append("\">");

		return returnString.toString();
	}
	
	public static String buttonMoreV3(
			String[] linkUrls,
			String[] nameOfLinks,
			String[] newPage) {
	
		StringBuffer html = new StringBuffer();
		String target = "target='_blank'";
		html.append(" <ul class='tooltip right'> \n");
		int i=0;
		for (String url : linkUrls) {
		
			if (!url.equals("")) {
				html.append(" <li> \n");
				html.append(" <a href='" + url + "'" + target + "> \n");
				html.append(" " + nameOfLinks[i] + " \n");
				html.append(" </a> \n");
				html.append(" </li> \n");
			}
			i++;
		}
		
		html.append(" </ul> \n");
		html.append("  \n");
		
		return html.toString();
	}
	
	/**
	 * Receive In:
	 *     String[] -- urls, to be put into Links
	 *     String[] -- name of Links (What displays on the Page
	 *     String[] -- for each one (throw a new page Y or N (Default Y)
	 *
	 * Creation date: (7/20/2004 TWalton)
	 * Update date:  7/28/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 */
	public static String buttonMore(
		String[] linkUrls,
		String[] nameOfLinks,
		String[] newPage) {

		StringBuffer returnString = new StringBuffer();

		returnString.append("<div>");

		returnString.append("&nbsp;&nbsp;");
		returnString.append(
			"<input style=\"font-family:arial; font-size:8pt\" type=\"button\" value=\"More\" ");
		returnString.append(
			"onClick=\"viewmenu(document.all[this.sourceIndex+1]);\"");

		returnString.append("</div>");
		returnString.append(
			"<span class=\"spanstyle\" style=\"display:none\" style=&{head1};>");
		if (linkUrls.length > 0) {
			String lineBreak = "";
			for (int x = 0; x < linkUrls.length; x++) {
				String target = "";
				if (linkUrls[x] != null
					&& nameOfLinks[x] != null
					&& newPage[x] != null
					&& !linkUrls[x].equals("")
					&& !nameOfLinks[x].equals("")
					&& !newPage[x].equals("")) {
					if (newPage[x].toUpperCase().equals("Y"))
						target = "target=\"_blank\"";

					returnString.append(lineBreak);
					returnString.append("&nbsp;");
					returnString.append("<a href=\"");
					returnString.append(linkUrls[x]);
					returnString.append("\" ");
					returnString.append(target);
					returnString.append(">");
					returnString.append(nameOfLinks[x].trim());

					if (x == 0)
						returnString.append("&nbsp;-->");
					else
						returnString.append("&nbsp;&nbsp;&nbsp;&nbsp;");

					lineBreak = "<br>";
				}
			}
		}
		returnString.append("</span>");

		return returnString.toString();
	}
	public static String buttonMoreNew(
			String[] linkUrls,
			String[] nameOfLinks,
			String[] newPage) {

			StringBuffer returnString = new StringBuffer();

			returnString.append("<div class=\"dropdown\" style=\"text-align:left;\" >");

			returnString.append("<h3>More:</h3> <select class=\"more\" ><option value=\"#\"></option>");

			if (linkUrls.length > 0) {
				String lineBreak = "";
				for (int x = 0; x < linkUrls.length; x++) {
					String target = "";
					if (linkUrls[x] != null
						&& nameOfLinks[x] != null
						&& newPage[x] != null
						&& !linkUrls[x].equals("")
						&& !nameOfLinks[x].equals("")
						&& !newPage[x].equals("")) {
						if (newPage[x].toUpperCase().equals("Y"))
							target = "target=\"_blank\"";

						returnString.append("<option value=\"" + linkUrls[x] + "\">");

						returnString.append(nameOfLinks[x].trim());

						returnString.append("</option>");
					}
				}
			}
			returnString.append("</select></div>");

			return returnString.toString();
		}
	/**
	 * Receive In:
	 *     String[] -- urls, to be put into Links
	 *     String[] -- name of Links (What displays on the Page
	 *     String[] -- for each one (throw a new page Y or N (Default Y)
	 *
	 * Creation date: (3/20/2006 TWalton)
	 */
	public static String buttonMoreOneLine(
		String[] linkUrls,
		String[] nameOfLinks,
		String[] newPage) {

		StringBuffer returnString = new StringBuffer();

		returnString.append("<div>");

		returnString.append("&nbsp;&nbsp;");
		returnString.append(
			"<input style=\"font-family:arial; font-size:8pt\" type=\"button\" value=\"More\" ");
		returnString.append(
			"onClick=\"viewmenu(document.all[this.sourceIndex+1]);\"");

		returnString.append("</div>");
		returnString.append(
			"<span class=\"spanstyle\" style=\"display:none\" style=&{head1};>");
		if (linkUrls.length > 0) {
			String lineBreak = "";
			for (int x = 0; x < linkUrls.length; x++) {
				String target = "";
				if (linkUrls[x] != null
					&& nameOfLinks[x] != null
					&& newPage[x] != null
					&& !linkUrls[x].equals("")
					&& !nameOfLinks[x].equals("")
					&& !newPage[x].equals("")) {
					if (newPage[x].toUpperCase().equals("Y"))
						target = "target=\"_blank\"";

					returnString.append(lineBreak);
					returnString.append("&nbsp;");
					returnString.append("<a href=\"");
					returnString.append(linkUrls[x]);
					returnString.append("\" ");
					returnString.append(target);
					returnString.append(">");
					returnString.append(nameOfLinks[x].trim());

					if (x == (linkUrls.length - 1))
						returnString.append("&nbsp;--->");
					else
						returnString.append("&nbsp;&nbsp;/&nbsp;&nbsp;");
				}
			}
		}
		returnString.append("</span>");

		return returnString.toString();
	}	
	/**
	 * To Use this button you MUST in the Head section of the JSP
	 *    get from the JavaScriptInfo Class
	 *          getChangeSubmitButton
	 *   and    getClickButtonOnlyOnce
	 * 
	 * Receive In:
	 *     buttonName -- name to access the parameter
	 *   Default buttonName = submit.
	 *    This is ALWAYS a Go Button
	 *
	 * Creation date: (12/28/2004 TWalton)
	 * Update date:  7/28/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 * @deprecated
	 */
	public static String buttonGo(String buttonName) {
		if (buttonName == null || buttonName.trim().equals(""))
			buttonName = "submit";

		StringBuffer returnString = new StringBuffer();

		returnString.append("<input name=\"");
		returnString.append(buttonName);
		returnString.append("\"");
		returnString.append(
			"onClick=\"submitForm(this); return countClicks()\" ");
		returnString.append("type=\"Submit\" value=\"Go\">");

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     hour, minute and second
	 *    Will check for nulls and string them together.
	 *
	 * Creation date: (9/12/2003 TWalton)
	 * Update date:  2/28/06 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 */
	public static String combineTime3Sections(String hour,
		                                      String minute,
		                                      String second,
											  String showColons) 
	{
		StringBuffer returnString = new StringBuffer();
		String colon = ":";
	   //** hour
		if (hour == null ||
		    hour.trim().length() == 0 ||
		    hour.trim().equals("null"))
		   returnString.append("00");
		else
		{
		   if (hour.length() == 1)
		   	 returnString.append("0");
		   if (hour.length() <= 2)
		   	returnString.append(hour);
		   else
		   	returnString.append(hour.substring(0, 2));
		}
		if (showColons.equals("Y"))
			returnString.append(colon);
	   // minute
		if (minute == null ||
		    minute.trim().length() == 0 ||
		    minute.trim().equals("null"))
		   returnString.append("00");
		else
		{
		   if (minute.length() == 1)
		     returnString.append("0");
		   if (minute.length() <= 2)
		     returnString.append(minute);
		   else
		   	 returnString.append(minute.substring(0, 2));
		}
		if (showColons.equals("Y"))
			returnString.append(colon);
	   // second
		if (second == null ||
		    second.trim().length() == 0 ||
		    second.trim().equals("null"))
		   returnString.append("00");
		else
		{
		   if (second.length() == 1)
		   	 returnString.append("0");
		   returnString.append(second);
		}
	
		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     total Time
	 *    Seperate it into 3 pieces.  
	 *      hour - 2 long
	 *      min  - 2 long
	 *      sec  - 2 long
	 *   Return as a String array
	 *
	 * Creation date: (2/28/2006 TWalton)
	 */
	public static String[] separateTime3Sections(String time) 
	{
		String[] returnString = new String[3];
		returnString[0] = "00";
		returnString[1] = "00";
		returnString[2] = "00";
		
		if (time == null)
	      time = "";
		
		int    findColon       = time.indexOf(":");
		if (findColon != 0)
		{
		   try
		   {
		   	   String noColonsInTime = time.substring(0, findColon);
		       String manipulateValue = time.substring((findColon + 1), time.length());
		       findColon       = manipulateValue.indexOf(":");
		       noColonsInTime = noColonsInTime + manipulateValue.substring(0, findColon);
		       noColonsInTime = noColonsInTime + manipulateValue.substring((findColon + 1), manipulateValue.length());
		       time = noColonsInTime;
		   }
		   catch(Exception e)
		   {}
		}

		if (time.length() == 1)
		  returnString[2] = "0" + time.trim();
		if (time.length() > 1)
		  returnString[2] = time.substring((time.length() - 2), time.length());
		if (time.length() == 3)
		  returnString[1] = "0" + time.substring(0, 1);
		if (time.length() > 3)
		  returnString[1] = time.substring((time.length() - 4), (time.length() - 2));
		if (time.length() == 5)
		  returnString[0] = "0" + time.substring(0, 1);
	 	if (time.length() > 5)
	 	  returnString[0] = time.substring((time.length() - 6), (time.length() - 4));
		
		return returnString;
	}
	/**
	 * This method builds a stylesheet for use in the Header
	 *  section of Print type pages.
	 * The default background color is WHITE &
	 * The default font color is BLACK
	 *******************************************************************************
	 *               MAIN COLOR PALATE   >>     R G B     <<                       *
	 *******************************************************************************
	 *
	 * td - color - alignment - fontCode - borderCode = bold or not
	 *   
	 *  OLD     01 #fff7e7 - Off White    - 255.247.231 
	 *  OLD     02 #ffffcc - Pale Yellow  - 255.255.204
	 *  OLD     03 #cccc99 - Light Olive  - 204.204.153
	 *  OLD     04 #d6b364 - Gold         - 214.179.100 
	 *  OLD      05 #006400 - Dark Green   - 000.100.000 
	 *       06 #990000 - Burgandy Red - 153.000.000 
	 *       07 #003366 - Navy         - 000.051.102 
	 *       08 #000000 - Black        - 000.000.000 - Default Text Color
	 * 
	 * td - color - alignment - fontCode - borderCode = bold or not
	 *                             01 -  7 font size
	 *                             02 -  8 font size
	 *                             03 -  9 font size
	 *                             04 - 10 font size
	 *                             05 - 12 font size
	 *                             06 - 14 font size                           
	 *
	 * td - color - alignment - fontCode - borderCode = bold or not
	 *                                         08B  - black - border-bottom
	 *                                         08T  - black - border-top
	 *                                         08L  - black - border-left
	 *                                         08R  - black - border-right
	 *                                         08BT - black - border-bottom & top
	 *
	 * Creation date: (11/12/2003 10:32:24 AM)
	 * @return java.lang.String
	 */
	public static String styleSheetHeadSection() 
	{
	   String styleInfo = 
	          "<style type=\"text/css\">" +
	            "tr.tr00001 {background-color:#fff7e7}" +
	            "tr.tr01001 {background-color:#ffffcc}" +          
	            "tr.tr02001 {background-color:#cccc99}" +
	 
	            "td.td05R0308Bb {color=#006400;" +
	                            "font-family:arial;" +
	                            "font-size:9pt;" +
	                            "font-weight:bold;" +
	                            "text-align:right;" +
	                            "border-bottom: 1px solid #000000}" +                  
	            "td.td05C0308Bb {color=#006400;" +
	                            "font-family:arial;" +
	                            "font-size:9pt;" +
	                            "font-weight:bold;" +
	                            "text-align:center;" +
	                            "border-bottom: 1px solid #000000}" +                  
	             
	            "td.td06L05b   {color=#990000;" +
	                            "font-family:arial;" +
	                            "font-size:12pt;" +
	                            "font-weight:bold;" +
	                            "text-align:left}" +
				"td.td08C01    {font-family:arial;" +
								"font-size:7pt;" +
								"text-align:center}" +
				"td.td08L01    {font-family:arial;" +
								"font-size:7pt;" +
								"text-align:left}" +
				"td.td08R01    {font-family:arial;" +
								"font-size:7pt;" +
								"text-align:right}" +                               
	            "td.td08C03    {font-family:arial;" +
	                            "font-size:9pt;" +
	                            "text-align:center}" +
	            "td.td08L03    {font-family:arial;" +
	                            "font-size:9pt;" +
	                            "text-align:left}" +
	            "td.td08R03    {font-family:arial;" +
	                            "font-size:9pt;" +
	                            "text-align:right}" +                            
	            "td.td08C03b    {font-family:arial;" +
	                            "font-size:9pt;" +
	                            "font-weight:bold;" +
	                            "text-align:center}" +                              
	            "td.td08C0308B  {font-family:arial;" +
	                            "font-size:9pt;" +
	                            "text-align:center;" +
	                            "border-bottom: 1px solid #000000}" +    
	            "td.td08R0308B  {font-family:arial;" +
	                            "font-size:9pt;" +
	                            "text-align:right;" +
	                            "border-bottom: 1px solid #000000}" +    
				"td.td08R0108Bb {font-family:arial;" +
								"font-size:7pt;" +
								"font-weight:bold;" +
								"text-align:right;" +
								"border-bottom: 1px solid #000000}" +     
				"td.td08L0108Bb {font-family:arial;" +
								"font-size:7pt;" +
								"font-weight:bold;" +
								"text-align:left;" +
								"border-bottom: 1px solid #000000}" +                                  
				"td.td08C0108Bb {font-family:arial;" +
								"font-size:7pt;" +
								"font-weight:bold;" +
								"text-align:center;" +
								"border-bottom: 1px solid #000000}" +                              
	            "td.td08R0308Bb {font-family:arial;" +
	                            "font-size:9pt;" +
	                            "font-weight:bold;" +
	                            "text-align:right;" +
	                            "border-bottom: 1px solid #000000}" +     
	            "td.td08L0308Bb {font-family:arial;" +
	                            "font-size:9pt;" +
	                            "font-weight:bold;" +
	                            "text-align:left;" +
	                            "border-bottom: 1px solid #000000}" +                                  
	            "td.td08C0308Bb {font-family:arial;" +
	                            "font-size:9pt;" +
	                            "font-weight:bold;" +
	                            "text-align:center;" +
	                            "border-bottom: 1px solid #000000}" +                                  
	            "td.td08L04    {font-family:arial;" +
	                            "font-size:10pt;" +
	                            "text-align:left}" +      
	            "td.td08C04b   {font-family:arial;" +
	                            "font-size:10pt;" +
	                            "font-weight:bold;" +
	                            "text-align:center}" +
	            "td.td08C0408Bb {font-family:arial;" +
	                            "font-size:10pt;" +
	                            "font-weight:bold;" +
	                            "text-align:center;" +
	                            "border-bottom: 1px solid #000000}" +          
	            "td.td08C0408Tb {font-family:arial;" +
	                            "font-size:10pt;" +
	                            "font-weight:bold;" +
	                            "text-align:center;" +
	                            "border-top: 2px solid #000000}" +               
	            "td.td08L04b   {font-family:arial;" +
	                            "font-size:10pt;" +
	                            "font-weight:bold;" +
	                            "text-align:left}" +                  
	            "td.td08L0408B  {font-family:arial;" +
	                            "font-size:10pt;" +
	                            "text-align:left;" +
	                            "border-bottom: 1px solid #000000}" +                
	            "td.td08L0408Bb {font-family:arial;" +
	                            "font-size:10pt;" +
	                            "font-weight:bold;" +
	                            "text-align:left;" +
	                            "border-bottom: 1px solid #000000}" +          
	            
	            "td.td08C06b {font-family:arial;" +
	                            "font-size:14pt;" +
	                            "font-weight:bold;" +
	                            "text-align:center}" +
	                            
	         "</style>";
		
		return styleInfo;
	}
	/**
	 * To Use this button you MUST in the Head section of the JSP
	 *    get from the JavaScriptInfo Class
	 *          getChangeSubmitButton
	 *   and    getClickButtonOnlyOnce
	 * 
	 * Receive In:
	 *     buttonName -- name to access the parameter
	 *   Default buttonName = submit.
	 *     buttonValue -- Words to Appear on the button
	 *   Default buttonValue = update.
	 *
	 * Creation date: (1/18/2005 TWalton)
	 * Update date:  7/29/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 * @deprecated
	 */
	public static String buttonSubmit(String buttonName, 
									  String buttonValue,
			                          String noScript) {
		
		if (buttonName == null || buttonName.trim().equals(""))
			buttonName = "submit";
		if (buttonValue == null || buttonValue.trim().equals(""))
			buttonValue = "update";
	
		StringBuffer returnString = new StringBuffer();
	
		returnString.append("<input name=\"");
		returnString.append(buttonName);
		returnString.append("\"");
		if (!noScript.equals("Y"))
		  returnString.append("onClick=\"submitForm(this); return countClicks()\" ");
		returnString.append("type=\"Submit\" value=\"");
		returnString.append(buttonValue);
		returnString.append("\">");
	
		return returnString.toString();
	}
	/**
	 * Use this to retrieve the list of Links which are displayed 
	 *     on the Top right corner of TreeNet
	 * 
	 * Creation date: (3/19/2007 TWalton)
	 * 
	 */
	public static String dropDownHeadingLinks() {
		
		StringBuffer returnString = new StringBuffer();
	
		returnString.append("<select style=\"font-family:arial; font-size:8pt\" ");
		returnString.append("onChange=\"JumpToIt(this)\"> ");
		returnString.append("<option value=\"None\" selected>Select a link ");
		returnString.append("<option value=\"http://www.treetop.org\">Grower Site ");
		//returnString.append("<option value=\"http://intranet.treetop.com\">Tree Top Intranet ");
		returnString.append("<option value=\"http://www.treetop.com\">Tree Top Site ");
		//returnString.append("<option value=\"/web/JSP/DataWarehouse/inqDataWarehouse.jsp\">Data Warehouse Site ");
		//returnString.append("<option value=\"http://marketeer.treetop.com\">Marketeer ");
	  returnString.append("<option value=\"http://www.nwnaturals.com\">Northwest Naturals ");
		returnString.append("</select>");
		
		return returnString.toString();
	}
	/**
	 * This method builds a stylesheet for use in the Header
	 *  section of Print type pages.
	 *    THIS information can be taken directly from the Stylesheet used on the Image server
	 * The default background color is WHITE &
	 * The default font color is BLACK
	 *******************************************************************************
	 *      MAIN COLOR PALATE for New Stylesheet Version   R G B                  **
	 *******************************************************************************
	 *   
	 *       00 #FFFFFF - White        - 255.255.255
	 *       01 #F3FAFF - Pale Blue    - 243.250.255    
	 *       02 #DCDCDC - Light Grey   - 220.220.220 
	 *       03 #990000 - Burgandy Red - 153.000.000 
	 *       04 #000000 - Black        - 000.000.000 - Default Text Color
	 *       05 #003366 - Navy         - 000.051.102
	 *
	 * Creation date: (11/12/2003 10:32:24 AM)
	 * @return java.lang.String
	 */
	public static String styleSheetHeadSectionV2() 
	{
	   StringBuffer sb = new StringBuffer();
	   sb.append("<style type=\"text/css\">");
	   // Table Rows
	   sb.append("tr.tr00 {background-color:#FFFFFF}");
	   sb.append("tr.tr01 {background-color:#F3FAFF}");          
	   sb.append("tr.tr02 {background-color:#DCDCDC}");
	   sb.append("tr.tr05 {background-color:#003366}");
	   // Table Data Elements
	   // White 00
	     sb.append("td.td0014 {font-size:9pt; font-family:arial; ");
	     sb.append("color:#FFFFFF; text-align:left} ");
	   // Red 03
	   sb.append("td.td0320 {font-size:12pt; font-family:arial; ");
	     sb.append("color:#990000; text-align:left} ");
	   // Black 04
	   sb.append("td.td0410 {font-size:7pt; font-family:arial; ");
	     sb.append("color:#000000; text-align:left} ");
	   sb.append("td.td0412 {font-size:8pt; font-family:arial; ");                              
	     sb.append("color:#000000; text-align:left} ");
	   sb.append("td.td0414 {font-size:9pt; font-family:arial; ");                              
	     sb.append("color:#000000; text-align:left} ");
	   sb.append("td.td0416 {font-size:10pt; font-family:arial; ");                              
	     sb.append("color:#000000; text-align:left} ");
	   sb.append("td.td0424 {font-size:14pt; font-family:arial; ");
	     sb.append("color:#000000; text-align:left} ");
       // Black Font, Navy Border
	   sb.append("td.td04100105 {font-size:7pt; font-family:arial; ");                              
	     sb.append("color:#000000; text-align:left; ");
	     sb.append("border-bottom:1px solid #003366 } ");	     
	   sb.append("td.td04140105 {font-size:9pt; font-family:arial; ");                              
	     sb.append("color:#000000; text-align:left; ");
	     sb.append("border-bottom:1px solid #003366 } ");
	   sb.append("td.td04160105 {font-size:10pt; font-family:arial; ");                              
	     sb.append("color:#000000; text-align:left; ");
	     sb.append("border-bottom:1px solid #003366 } ");
	      // top border
	   sb.append("td.td04161405 {font-size:10pt; font-family:arial; ");                              
	     sb.append("color:#000000; text-align:left; ");
	     sb.append("border-top:1px solid #003366 } ");
	    
	   sb.append("td.td04166405 {font-size:10pt; font-family:arial; ");                              
		 sb.append("color:#000000; text-align:left; ");
		 sb.append("border-bottom:2px solid #003366 } ");	     
	 // Navy Text 05
	   sb.append("td.td05140105 {font-size:9pt; font-family:arial; ");                              
	     sb.append("color:#003366; text-align:left; ");
	     sb.append("border-bottom:1px solid #003366 } ");	     
		 
       sb.append("</style>");
		
		return sb.toString();
	}
	/**
	 * Code in the code for the end of a SPAN section
	 * 
	 * Creation date: (8/25/2009 - TWalton)
	 * @return java.lang.String
	 */
	public static String endSpan() 
	{
		return "</span>";
	}
	/**
	 * Receive In:
	 *     Integer -- number
	 *     int        -- number of decimal positions to be displayed.
	 *     
	 *     The Number is WHOLE and the Decimals were stored in another field.
	 *        Example... the number is 702  with 1 decimal Position
	 *        the Displayed value should be 70.2
	 *
	 * Creation date: (9/28/2009 TWalton)
	 */
	public static String addDecimalToInteger(Integer number,
		                                   int decimalPositions) 
	{
		StringBuffer returnString = new StringBuffer();
	try
	{
		try
		{	
			if (decimalPositions > 0)
			{
				String x = number.toString();
				String returnx = x.substring(0, (x.length() - decimalPositions));
				String returny = x.substring((x.length() - decimalPositions), x.length());
				returnString.append(returnx + "." + returny);
			}
			Integer test = new Integer(number);
		}
		catch(Exception e)
		{  // Set to 0 if error is caught
		}
	}
	catch(Exception e)
	{
		System.out.println("Problem in HTMLCode.addDecimalToInteger(Integer, int): " + e);	
	}
		return returnString.toString();
	}
	/**
	 * Build the Footer to be used on EVERY page
	 *    By using this Helpers Class, I can update the footer
	 *    and EVERY page will reflect the change
	 *    Will replace the Include on every page for the footer
	 * 
	 * Receive In:
	 *     pageType -- currently this is Blank, but as we have different pages this may change
	 *
	 * Creation date: (5/25/2010 TWalton)
	 * Update date: 
	 */
	public static String pageFooterTable(String pageType) {
		StringBuffer returnString = new StringBuffer();
	
		returnString.append("<table class=\"footerold\" cellspacing=\"0\" ");
		returnString.append("style=\"width:100%\"> ");
		returnString.append("<tr class=\"tr02\"> ");
		returnString.append("<td class=\"td0410\" style=\"text-align:center\"> ");
		returnString.append("Confidential Property of Tree Top, Inc. </td>");
		returnString.append("<td class=\"td0410\" style=\"text-align:center\"> ");
		DateTime dateValues = UtilityDateTime.getSystemDate();
		returnString.append(dateValues.getDayOfWeek().trim() + ", ");
		returnString.append(dateValues.getDateFormatMonthNameddyyyy());
		returnString.append("</td></tr></table>");
		return returnString.toString();
	}
	/**
	 * Build the Header to be used on EVERY page
	 *    By using this Helpers Class, I can update the header
	 *    and EVERY page will reflect the change
	 *    Will replace the Include on every page for the header
	 * 
	 * See the include/heading.jsp file for header info
	 * Receive In:
	 *     Vector
	 *     	[0] - Environment
	 *      [1] - Title
	 *      [2] - Extra Options
	 *
	 * Creation date: (5/26/2010 TWalton)
	 * Update date: 
	 */
	public static String pageHeaderTable(HttpServletRequest request,
										 HttpServletResponse response,
										 Vector pageInfo) {
		StringBuffer returnString = new StringBuffer();
		
		String ttUser = "N";
		try{
		   String[] roles = SessionVariables.getSessionttiUserRoles(request, response);
		   for (int x = 0; x < roles.length ; x++)
		   {
				if (roles[x].equals("1") || roles.equals("8"))
					ttUser = "Y";
		   }		   
		   
		} catch(Exception e)
		{}
		
		returnString.append("<table cellspacing=\"0\" style=\"width:100%;\" background=\"https://image.treetop.com/webapp/TreeNetImages/headerTableBgV2.jpg\"> ");
		 returnString.append("<tr>");
		  returnString.append("<td class=\"td04100905\" style=\"width:125px; text-align:center;\" align=\"center\" rowspan=\"2\">");
		   returnString.append("<img src=\"/web/Include/images/TT_logo2C-2013.png\" style='height:40px;'>");
		  returnString.append("</td>");
		  returnString.append("<td class=\"td04240405\" style=\"text-align:center; vertical-align:middle;\">");
	       returnString.append("<b>" + pageInfo.elementAt(1));
	       if (!((String) pageInfo.elementAt(0)).trim().equals("") && 
	    	   !((String) pageInfo.elementAt(0)).trim().equals("PRD"))
	    	   returnString.append("<br> *** " + pageInfo.elementAt(0) + " ENVIRONMENT *** ");
	       returnString.append("</b>");
		  returnString.append("</td>");
		  returnString.append("<td class=\"td04101005\" style=\"width:125px; text-align:center;\" align=\"center\" rowspan=\"2\">");
		   returnString.append("<img style=\"vertical-align:text-bottom; width:115px;\" src=\"https://image.treetop.com/webapp/TreeNetImages/treenet.gif\">");
		  returnString.append("</td>");		  
         returnString.append("</tr>");
         
         returnString.append("<tr class=\"tr00\">");
          returnString.append("<td class=\"td04100505\">&nbsp;");
          //---------------Inner Table ---------------------------------
           returnString.append("<table style=\"width:100%\">");
           //-- Row 1 --
            returnString.append("<tr class=\"tr00\">");
             returnString.append("<td class=\"td0412\" style=\"width:33%\">");
             if (ttUser.trim().equals("Y"))
               returnString.append("<a class=\"a0412\" href=\"TreeNetInq\">TreeNet Home</a>");
             returnString.append("&nbsp;");
             returnString.append("</td>");
             returnString.append("<td class=\"td0412\" style=\"text-align:center;\">");
  
             // -- Call for Visited
             String profile = SessionVariables.getSessionttiProfile(request, response);
                   // get Server
          	 String theHost = request.getHeader("Host");
             		// get URI
       		 String theLongUrl = theHost + request.getRequestURI();
          	 int xxx = theLongUrl.indexOf("?");
       		 String theUrl = "";
      		 if (xxx == -1)
       			theUrl = theLongUrl;
       		 else
       			theUrl = theLongUrl.substring(0,xxx);
       		 Vector sendHit = new Vector();
       		 sendHit.addElement(theUrl);
       		 sendHit.addElement(profile);
             GeneralUtility.runCounter(sendHit);
    		 String hitsPageLink = theHost + "/web/JSP/countResults.jsp?theurl=" + theLongUrl;
             returnString.append("<a class=\"a0412\" href=http://");
             returnString.append(hitsPageLink);
             returnString.append(" target=\"_blank\"> Who Visited? </a>");
             
             returnString.append("&nbsp;</td>");
             returnString.append("<td class=\"td0412\" style=\"text-align:right; width:33%;\">");
              returnString.append("<a class=\"a0412\" href=\"mailto:helpdesk@treetop.com?Subject=Application Home Menu\">Help</a>&nbsp;");
             returnString.append("</td>");
            returnString.append("</tr>");
            //--Row 2
            returnString.append("<tr class=\"tr00\">");
            if (ttUser.trim().equals("Y"))
            {	
               returnString.append("<form action=\"/web/TreeNetList?type=search\" method=\"get\">");
               returnString.append("<td class=\"td0412\">");
               returnString.append("<input style=\"font-family:arial; font-size:8pt;\" size = \"20\" type=\"text\" name=\"typevalue\">");
               returnString.append("<input style=\"font-family:arial; font-size:8pt;\" type=\"submit\" value=\"Search\">");
               returnString.append("</td>");
               returnString.append("</form>");
            }else{
            	returnString.append("<td class=\"td0412\">&nbsp;</td>");
            }
             returnString.append("<td class=\"td0412\" style=\"text-align:center;\">&nbsp;");
             if (pageInfo.size() > 2 && !((String) pageInfo.elementAt(2)).trim().equals(""))
             {
            	returnString.append("<select style=\"font-family:arial; font-size:8pt;\" onChange=\"JumpToIt1(this)\">");
            	returnString.append("<option value=\"None\" selected>Select an Option");
            	returnString.append((String) pageInfo.elementAt(2));
            	returnString.append("</select>");
             }
             returnString.append("&nbsp;</td>");
            returnString.append("<td class=\"td0412\" style=\"text-align:right;\">");
             returnString.append(HTMLHelpers.dropDownHeadingLinks());
             returnString.append("</td>");
            returnString.append("</tr>");
           returnString.append("</table>");
           //----------------------------------------------------------------------------------
          returnString.append("</td>");
         returnString.append("</tr>");
	    returnString.append("</table>");
		return returnString.toString();

	     
//	                 <td style="text-align:center">
	//	<%
		//   if (!pageType.equals("logon") &&
		  //     !pageType.equals("secureSite"))
//		   {    
	//	%>        
		//       <select style="font-family:arial; font-size:7pt" onChange="JumpToIt1(this)">
		  //      <option value="None" selected>Select an Option
		    //     <%= extraOptions %>
//		       </select>
	//	<%
		//   }
//		%>    
	//	       &nbsp;        
		//      </td>
		
		
	}
	/**
	 * Build the Header to be used on EVERY page
	 *    By using this Helpers Class, I can update the Head section of the Heading
	 *    and EVERY page will reflect the change
	 *    Will replace the Include on every page for the header
	 * @deprecated
	 * -please refer to Include/heading.jsp for header
	 * Receive In:
	 *     pageType -- currently this is Blank, but as we have different pages this may change
	 *
	 * Creation date: (5/26/2010 TWalton)
	 * Update date: 
	 */
	public static String pageHeaderHeadSection(String pageType, String newWindow) {
		
		if (newWindow == null || newWindow.equals(""))
		   newWindow = "location.href=newPage1";
		if (newWindow.equals("throwNew"))
		   newWindow = "window.open(newPage1)";   
		
		StringBuffer returnString = new StringBuffer();
		returnString.append("<script language=\"JavaScript1.2\">");
		returnString.append(" function JumpToIt(list) { ");
		returnString.append("var newPage = list.options[list.selectedIndex].value; ");
		returnString.append("if (newPage != \"None\") {");
		returnString.append("location.href=newPage; }");
		returnString.append("}");
		returnString.append(" function JumpToIt1(list) { ");
		returnString.append("var newPage1 = list.options[list.selectedIndex].value; ");
		returnString.append("if (newPage1 != \"None\") {");
		returnString.append(newWindow + "; }");
		returnString.append("}");
        returnString.append("</script>");
        
        returnString.append(JavascriptInfo.getClickButtonOnlyOnce());
        returnString.append(JavascriptInfo.getChangeSubmitButton());
	         
	    returnString.append("<link rel=\"stylesheet\" type=\"text/css\" ");
	    returnString.append("href=\"https://image.treetop.com/webapp/Stylesheetv2.css\" />");
	    
	    return returnString.toString();
}
}
