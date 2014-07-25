/*
 * Created on Jul 28, 2005
 */
package com.treetop.utilities.html;
import java.net.URLEncoder;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;

import com.treetop.SystemDate;
import com.treetop.data.GeneralInfo;

/**
 * @author twalto
 * Use this class to put Standard HTML Code into, for use in JSP's
 *
 */
public class HTMLHelpersInput {
	/**
	 * Receive In:
	 *     fieldName    -- name to access the parameter
	 *     dataValue    -- Data which should display to begin with.
	 *     fieldLongName -- Long Description of the Name
	 *     size          -- size that the input box should look like.
	 *     maxlength     -- maximum number of characters allowed for entry.
	 *     requiredEntry -- Y / N
	 *     readOnly      -- Y / N
	 *
	 * Creation date: (1/21/2005 9:14:37 AM)
	 * Update date:  10/12/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 * @deprecated
	 */
	public static String inputBoxUrl(
		String fieldName,
		String dataValue,
		String fieldLongName,
		int size,
		int maxlength,
		String requiredEntry,
		String readOnly) {
		StringBuffer returnString = new StringBuffer();
		returnString.append("<input type=\"file\" name=\"" + fieldName + "\" id=\"" + fieldName + "\" ");
		if (requiredEntry.trim().equals("Y")) {
			returnString.append("onBlur=\"verify(this, '");
			returnString.append(fieldLongName.trim());
			returnString.append("') \" ");
		}
		returnString.append("size=\"" + size + " \" ");
		returnString.append("maxlength=\"" + maxlength + "\" ");
		returnString.append("value=\"" + dataValue + "\" />");

		return returnString.toString();
	}
	
	/**
	 * Receive In:
	 *     fieldName    -- name to access the parameter
	 *     dataValue    -- Data which should display to begin with.
	 *     fieldLongName -- Long Description of the Name
	 *     size          -- size that the input box should look like.
	 *     maxlength     -- maximum number of characters allowed for entry.
	 *     requiredEntry -- Y / N
	 *     readOnly      -- Y / N
	 *
	 * Creation date: (1/21/2005 9:14:37 AM)
	 * Update date:  10/12/05 TWalton
	 * 					-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 * 				 02/23/12 JHagler
	 * 					-- add class="browse"  This hooks up with a jQuery script in util.js that
	 *                     hides the original file input and replaces it with a text box and browse button
	 *                     this lets users type in a URL path or browse to a document on their machine
	 */
	public static String inputBoxUrlBrowse(
		String fieldName,
		String dataValue,
		String fieldLongName,
		int size,
		int maxlength,
		String requiredEntry,
		String readOnly) {
		StringBuffer returnString = new StringBuffer();
		returnString.append("<input type=\"file\" name=\"" + fieldName + "\" id=\"" + fieldName + "\" ");
		if (requiredEntry.trim().equals("Y")) {
			returnString.append("onBlur=\"verify(this, '");
			returnString.append(fieldLongName.trim());
			returnString.append("') \" ");
		}
		returnString.append("size=\"" + size + "\" ");
		returnString.append("class=\"browse\" ");
		returnString.append("maxlength=\"" + maxlength + "\" ");
		returnString.append("value=\"" + dataValue + "\" />");

		return returnString.toString();
	}
	
	/**
	 * Receive In:
	 *     fieldName     -- name to access the parameter
	 *     fieldValue    -- Data which should display to begin with.
	 *     rows          -- how many rows should the textarea box be.
	 *     columns       -- how many columns should the textarea box be.
	 *     maxCharLength -- The maximum allowed characters per field.
	 *     readonly      -- Yes or No.
	 * Must have Javascript.getCheckTextareaLength() in the Header section.
	 *
	 * Creation date: (11/7/2003 TWALTO)
	 * Update date:  9/15/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 */
	public static String inputBoxTextarea(
		String fieldName,
		String fieldValue,
		int rows,
		int columns,
		int maxCharLength,
		String readonly) // Y/N
	{
		try {
			fieldValue = StringEscapeUtils.escapeHtml(fieldValue.trim());
		} catch(Exception e) {}
		
		StringBuffer returnString = new StringBuffer();
		returnString.append(
			"<textarea style=\"font-family:arial; font-size:10pt\" ");
		returnString.append("name=\"");
		returnString.append(fieldName.trim());
		returnString.append("\" ");
		returnString.append("rows=\"");
		returnString.append(rows);
		returnString.append("\" ");
		returnString.append("cols=\"");
		returnString.append(columns);
		returnString.append("\" ");
		returnString.append("onblur=\"return CheckTextareaLength(");
		returnString.append(maxCharLength);
		returnString.append(");\" ");
		if (readonly.equals("Y"))
			returnString.append("readonly");
		returnString.append(" >");
		returnString.append(fieldValue.trim());
		returnString.append("</textarea>");

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     fieldName     -- name to access the parameter
	 *     fieldValue    -- Data which will be sent if this value is chosen
	 *     checkedField  == Y or N should this field start out on - Would then ALWAYS Send value??
	 *
	 * Creation date: (11/10/2003 TWalton)
	 * Update date:  9/8/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 */
	public static String inputRadioButton(
		String fieldName,
		String fieldValue,
		String checkedField,
		String readOnly) {
		StringBuffer returnString = new StringBuffer();
		returnString.append("<input type=\"radio\" ");
		returnString.append("name=\"");
		returnString.append(fieldName.trim());
		returnString.append("\" ");
		returnString.append("value=\"");
		returnString.append(fieldValue.trim());
		returnString.append("\" ");
		if (checkedField.equals("Y"))
			returnString.append("CHECKED ");
		if (readOnly != null && readOnly.toUpperCase().trim().equals("Y"))
			returnString.append("DISABLED ");
		returnString.append(">");
		if (checkedField.equals("Y") &&
				readOnly != null &&
				readOnly.toUpperCase().trim().equals("Y"))
			   returnString.append(HTMLHelpersInput.inputBoxHidden(fieldName, fieldValue));	

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     fieldName     -- name to access the parameter
	 *     checkedField  == Will default to blank
	 *
	 * Creation date: (11/11/2003 4:10:37 PM  - TWalton)
	 * Update date:  8/12/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 */
	public static String inputCheckBox(String fieldName, String checkedField, String readOnly) {
		StringBuffer returnString = new StringBuffer();

		returnString.append("<input ");
		returnString.append("type=\"checkbox\" ");
		returnString.append("name=\"");
		returnString.append(fieldName.trim());
		returnString.append("\" ");
		if (checkedField != null
			&& !checkedField.trim().equals("")
			&& !checkedField.equals("N"))
			returnString.append("CHECKED ");
		if (readOnly != null && readOnly.toUpperCase().trim().equals("Y"))
			returnString.append("DISABLED ");
		returnString.append(">");
		if (checkedField.equals("Y") &&
				readOnly != null &&
				readOnly.toUpperCase().trim().equals("Y"))
			   returnString.append(HTMLHelpersInput.inputBoxHidden(fieldName, checkedField));	
		

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     fieldName     -- name to access the parameter
	 *     fieldValue    -- Default Value, either Y or N
	 *     firstLine     -- Default will be Blank,
	 *                      IF blank will default directly to the first option (N)
	 *
	 * Creation date: (5/20/2004 TWalton)
	 * Update date:  7/28/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 */
	public static String dropDownYesNo(
		String fieldName,
		String fieldValue,
		String firstLine,
		String readOnly) {
		StringBuffer returnString = new StringBuffer();
		returnString.append("<select name=\"");
		returnString.append(fieldName);
		returnString.append("\"");
		if (readOnly != null && readOnly.toUpperCase().trim().equals("Y"))
		returnString.append(" DISABLED");		
		returnString.append(">");
		if (firstLine != null && !firstLine.equals("")) {
			returnString.append("<option value=\"\">");
			returnString.append(firstLine);
		}
		returnString.append("<option value=\"N\"");
		if (fieldValue.equals("N"))
			returnString.append(" selected=\"selected\"");
		returnString.append(">No");
		returnString.append("<option value=\"Y\"");
		if (fieldValue.equals("Y"))
			returnString.append(" selected=\"selected\"");
		returnString.append(">Yes </select>");
		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     fieldName    -- name to access the parameter
	 *     dataValue    -- Data which should display to begin with.
	 *     functionName -- javascript date calendar function.
	 *                    PLEASE remember to retrive (in the Servlet)
	 *                     the correct javascript foot information 
	 *                     for each calendar.
	 *     requiredEntry -- Y or N Only - Default N
	 * 	   readOnly		-- Y or N Only - if Required, readOnly == YES - Default N
	 *
	 * Creation date: (9/12/2003 TWalton)
	 * Update date:  7/28/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 */
	public static String inputBoxDate(
		String fieldName,
		String dataValue,
		String functionName,
		String requiredEntry,
		String readOnly) {
		if ((requiredEntry != null && requiredEntry.equals("Y"))
			&& (dataValue == null || dataValue.trim().equals("")))
			// Get Today's Date to Use
			dataValue = (SystemDate.getSystemDate())[5];
		StringBuffer returnString = new StringBuffer();

		returnString.append("<input ");
		returnString.append("type=\"text\" ");
		returnString.append("name=\"");
		returnString.append(fieldName);
		returnString.append("\" ");
		returnString.append("value=\"");
		returnString.append(dataValue);
		returnString.append("\" size=\"10\" readonly>");
		if (readOnly == null || !readOnly.toUpperCase().trim().equals("Y"))
		{
			returnString.append("<a href=\"javascript:");
			returnString.append(functionName);
			returnString.append(".popup();\">");
			returnString.append(
				"<img src=\"https://image.treetop.com/webapp/cal.gif\" width=\"16\" height=\"16\" ");
			returnString.append(
				"border=\"0\" alt=\"Click Here to Pick up the date\"></a>");
		}

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     fieldName     -- name to access the parameter
	 *     dataValue     -- Data which should display to begin with.
	 *     fieldLongName -- send into javascript to display if problem.
	 *                    PLEASE remember to retrive (in the Servlet)
	 *                     the checknumeric Javascript.
	 *     size          -- size that the input box should look like.
	 *     maxlength     -- maximum number of characters allowed for entry.
	 *     requiredEntry -- Y / N
	 *     readOnly      -- Y / N
	 *
	 * Creation date: (9/12/2003 TWalton)
	 * Update date:  7/28/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 */
	public static String inputBoxNumber(
		String fieldName,
		String dataValue,
		String fieldLongName,
		int size,
		int maxlength,
		String requiredEntry,
		String readOnly) {
		StringBuffer returnString = new StringBuffer();

		returnString.append("<input ");

		returnString.append("type=\"text\" ");
		returnString.append("style=\"text-align:right\" ");
		
		returnString.append("name=\"");
		returnString.append(fieldName.trim());
		returnString.append("\" ");
		
		returnString.append("id=\"");
		returnString.append(fieldName.trim());
		returnString.append("\" ");
		
		
		returnString.append("onBlur=\"checknumeric(this, '");
		returnString.append(fieldLongName.trim());
		returnString.append("')");
		
		if (requiredEntry != null && requiredEntry.trim().equals("Y")) {
			returnString.append("; verify(this, '");
			returnString.append(fieldLongName.trim());
			returnString.append("')");
		}
		
		returnString.append("\" ");
		
		
		
		returnString.append("size=\"");
		returnString.append(size);
		returnString.append("\" ");
		returnString.append("maxlength=\"");
		returnString.append(maxlength);
		returnString.append("\" ");
		returnString.append("value=\"");
		returnString.append(dataValue.trim());
		returnString.append("\" ");
		if (readOnly != null && readOnly.toUpperCase().trim().equals("Y"))
			returnString.append("readonly ");
		returnString.append(">");

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     fieldName       -- name to access the parameter
	 *     dataValue       -- Data which should display to begin with.
	 * 	   Field Long Name -- For Display if there is a problem.
	 *     size            -- size that the input box should look like.
	 *     maxlength       -- maximum number of characters allowed for entry.
	 *     requiredEntry   -- Y and it will call the Javascript to verify this.
	 * 			IF you choose Y, you must have JavascriptInfo.getRequiredField in the Head of the JSP
	 *     readOnly        -- Y, Will not allow you to change/input anything
	 *          IF you choose Y, the maxLength, and the required Entry will no longer be an issue.
	 *
	 * Creation date: (10/24/2005 TWalton)
	 */
	public static String inputBoxText(
		String fieldName,
		String dataValue,
		String fieldLongName,
		int size,
		int maxlength,
		String requiredEntry,
		String readOnly) {
		StringBuffer returnString = new StringBuffer();

		try {
		   dataValue = StringEscapeUtils.escapeHtml(dataValue.trim());
		} catch(Exception e) {}
		
		returnString.append("<input ");
		returnString.append("type=\"text\" ");
		if (fieldName != null && !fieldName.trim().equals("")) {
			returnString.append("name=\"");
			returnString.append(fieldName);
			returnString.append("\" ");
		}
		
		if (fieldName != null && !fieldName.trim().equals("")) {
			returnString.append("id=\"");
			returnString.append(fieldName);
			returnString.append("\" ");
		}
		
		if ((readOnly == null || !readOnly.toUpperCase().trim().equals("Y"))
			&& requiredEntry != null
			&& requiredEntry.trim().equals("Y")) {
			returnString.append("onBlur=\"verify(this, '");
			returnString.append(fieldLongName.trim());
			returnString.append("') \" ");
		}
		if (size != 0) {
			returnString.append("size=\"");
			returnString.append(size);
			returnString.append("\" ");
		}
		if ((readOnly == null || !readOnly.toUpperCase().trim().equals("Y")) && maxlength != 0) {
			returnString.append("maxlength=\"");
			returnString.append(maxlength);
			returnString.append("\" ");
		}
		if (dataValue != null && !dataValue.trim().equals("")) {
			returnString.append("value=\"");
			returnString.append(dataValue);
			returnString.append("\" ");
		}
		
		
		
		if (readOnly != null && readOnly.toUpperCase().trim().equals("Y")) {
			returnString.append("readonly ");
		}		
		returnString.append(">");

		return returnString.toString();
	}

	/**
	 * Receive In:
	 *     fieldName     -- name to access the parameter
	 *     dataValue     -- Data which should display to begin with.
	 *
	 * Creation date: (9/15/2003 TWalton)
	 * Update date:  7/28/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 * @deprecated
	 */
	public static String inputBoxHidden(String fieldName, String dataValue) {
		StringBuffer returnString = new StringBuffer();

		returnString.append("");

		try
		{
		   dataValue = 	URLEncoder.encode(dataValue.trim(), "ISO");
		}
		catch(Exception e)
		{}
		
		if (dataValue != null) {
			returnString.append("<input ");
			returnString.append("type=\"hidden\" ");
			returnString.append("name=\"");
			returnString.append(fieldName.trim());
			returnString.append("\" ");
			returnString.append("value=\"");
			returnString.append(dataValue.trim());
			returnString.append("\">");
		}

		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     fieldName    -- name to access the parameter
	 *         when this is complete there will be 3 names,
	 *         hour + fieldName
	 *         min  + fieldName
	 *         sec  + fieldName
	 *     fieldLongName -- To be used if there is an error
	 *     time    -- Value to load into the Boxes
	 * 
	 * Creation date: (9/24/2003 TWalton)
	 * Update date:  2/28/06 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 */
	public static String inputBoxTime3Sections(String fieldName,
		                                       String fieldLongName,
		                                       String time) 
	{
		String hourName        = "hour" + fieldName;
		String minName         = "min"  + fieldName;
		String secName         = "sec"  + fieldName;
	
		String[] separateTime = HTMLHelpers.separateTime3Sections(time);
			
	    String returnString = "<input " +
		   			  "type=\"text\" " + 
	   				  "name=\"" + hourName + "\" " +
	   				  "onBlur=\"checknumeric(this, '" + fieldLongName.trim() + " (Hour)')\" " +
	  				  "size=\"1\" " +
	   				  "maxlength=\"2\" " +
	   				  "value=\"" + separateTime[0] + "\"><b>:</b>" +
	   				  "<input " +
		   			  "type=\"text\" " + 
	   				  "name=\"" + minName + "\" " +
	   				  "onBlur=\"checknumeric(this, '" + fieldLongName.trim() + " (Minute)')\" " +
	  				  "size=\"1\" " +
	   				  "maxlength=\"2\" " +
	   				  "value=\"" + separateTime[1] + "\"><b>:</b>" +
	   				  "<input " +
		   			  "type=\"text\" " + 
	   				  "name=\"" + secName + "\" " +
	   				  "onBlur=\"checknumeric(this, '" + fieldLongName.trim() + " (Second)')\" " +
	  				  "size=\"1\" " +
	   				  "maxlength=\"2\" " +
	   				  "value=\"" + separateTime[2] + "\">";
	   				  
		return returnString;
	}
	/**
	 * Receive In:
	 *     fieldName     -- name to access the parameter
	 *     fieldValue    -- Default Value, So Start with
	 *     fieldSelect   -- What it says on the First Line, Default is:
	 *                        *all - 'None' IF you want to start with Data 
	 *     readOnly      -- 'Y' if you want it to NOT be updateable
	 *
	 * Creation date: (10/15/2004 4:17:37 PM)
	 * Update date:  4/17/06 TWalton
	 * 				-- Converted using the General Info Class AND 
	 *                  The DropDownSingle Class
	 */
	public static String dropDownStates(String fieldName,
		                                String fieldValue,
										String fieldSelect,
										String readOnly) 
	{
		String returnString = "";
		// GET LIST
			Vector ddList = new Vector(); //Vector of DropDownSingle Classes
			String display = "B"; //B = Beginning with a Dash After (Default)
			  					  //E = Ending with a Dash Before
				  				  //N = None - Only display the description
			// Get Filled Vector of DropDownSingle Class
			try
			{
				GeneralInfo setClass  = new GeneralInfo();
				Vector typeList              = GeneralInfo.findDescByFull("STP");
				if (typeList.size() > 0)
				{	
					for (int x = 0; x < typeList.size(); x++)
					{
						GeneralInfo nextDesc = (GeneralInfo) typeList.elementAt(x);
						DropDownSingle thisClass = new DropDownSingle();
						thisClass.setDescription(nextDesc.getDescFull());
						thisClass.setValue(nextDesc.getKey1Value());
						ddList.add(thisClass);
					}
				}			
			}
			catch(Exception e)
			{
					// Catch any problems, but do not need to take action on them.
			}					
		  
		  return DropDownSingle.buildDropDown(ddList, fieldName, fieldValue, fieldSelect, display, readOnly);	
	}
	/**
	 * Receive In:
	 *     fieldName     -- name to access the parameter
	 *     dataValue     -- Data which should display to begin with.
	 *     fieldLongName -- Long name of the field
	 *     size          -- size that the input box should look like.
	 *     maxlength     -- maximum number of characters allowed for entry.
	 *     required?     -- is the field required?
	 *     readOnly      -- is the field readOnly?
	 *
	 * Creation date: (11/25/2003 11:27:37 AM)
	 * Update date:  4/17/06 TWalton
	 * 			-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 */
	public static String inputBoxTextEmail(String fieldName,
		                                   String dataValue,
		                                   String fieldLongName,
		                                   int size,
		                                   int maxlength,
		                                   String requiredEntry,
										   String readOnly) 
	{

		StringBuffer returnString = new StringBuffer();
		returnString.append("<input type=\"text\" ");
		returnString.append("name=\"");
		returnString.append(fieldName);
		returnString.append("\" ");
		returnString.append("onBlur=\"");
		if (requiredEntry.trim().equals("Y"))
		{
			returnString.append("verify(this, '" + fieldLongName.trim() + "'); ");
		}   
		returnString.append("checkEmail(this, '" + fieldLongName.trim() + "')\" ");
		returnString.append("size=\"" + size + "\" ");
		returnString.append("maxlength=\"" + maxlength + "\" ");
		returnString.append("value=\"" + dataValue + "\" ");		
	   if (readOnly != null && readOnly.toUpperCase().trim().equals("Y")) {
			returnString.append("readonly ");
		}		
		returnString.append(">"); 
	   
		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     name    -- Default ""
	 *     action  -- Default "" -- where do you want the form to take you
	 *     method  -- Default post
	 *
	 * Creation date: (8/25/2009 TWalton)
	 */
	public static String startFormTag(String name,
									  String action,
									  String method) {
		StringBuffer returnString = new StringBuffer();
	
		if (method.trim().equals(""))
			method = "post";
		
		returnString.append("<form ");
		returnString.append("name=\"");
		returnString.append(name);
		returnString.append("\" ");
		returnString.append("action=\"");
		returnString.append(action);
		returnString.append("\" ");
		returnString.append("method=\"");
		returnString.append(method);
		returnString.append("\" ");
		returnString.append("\">");
	
		return returnString.toString();
	}
	/**
	 * Code in the code for the end of a SPAN section
	 * 
	 * Creation date: (8/25/2009 - TWalton)
	 * @return java.lang.String
	 */
	public static String endForm() 
	{
		return "</form>";
	}
	/**
	 * Receive In:
	 *     fieldName    -- name to access the parameter
	 *     dataValue    -- Data which should display to begin with.
	 *     functionName -- javascript date calendar function.
	 *                    PLEASE remember to retrive (in the Servlet)
	 *                     the correct javascript foot information 
	 *                     for each calendar.
	 *     requiredEntry -- Y or N Only - Default N
	 * 	   readOnly		-- Y or N Only - if Required, readOnly == YES - Default N
	 *
	 * Creation date: (9/12/2003 TWalton)
	 * Update date:  7/28/05 TWalton
	 * 				-- Converted to StringBuffer and moved from com.treetop.HTMLCode
	 */
	public static String inputBoxDateTypeOrChoose(
		String fieldName,
		String dataValue,
		String functionName,
		String requiredEntry,
		String readOnly) {
		if ((requiredEntry != null && requiredEntry.equals("Y"))
			&& (dataValue == null || dataValue.trim().equals("")))
			// Get Today's Date to Use
			dataValue = (SystemDate.getSystemDate())[5];
		StringBuffer returnString = new StringBuffer();
	
		returnString.append("<input ");
		returnString.append("type=\"text\" ");
		returnString.append("name=\"");
		returnString.append(fieldName);
		returnString.append("\" ");
		returnString.append("value=\"");
		returnString.append(dataValue);
		returnString.append("\" size=\"10\">");
		if (readOnly == null || !readOnly.toUpperCase().trim().equals("Y"))
		{
			returnString.append("<a href=\"javascript:");
			returnString.append(functionName);
			returnString.append(".popup();\">");
			returnString.append(
				"<img src=\"https://image.treetop.com/webapp/cal.gif\" width=\"16\" height=\"16\" ");
			returnString.append(
				"border=\"0\" alt=\"Click Here to Pick up the date\"></a>");
		}
	
		return returnString.toString();
	}
	/**
	 * Receive In:
	 *     fieldName    -- name to access the parameter
	 *         when this is complete there will be 2 names,
	 *         hour + fieldName
	 *         min  + fieldName
	 *     fieldLongName -- To be used if there is an error
	 *     time    -- Value to load into the Boxes
	 *     
	 * Creation date: (9/14/2010 TWalton)
	 */
	public static String inputBoxTime2Sections(String fieldName,
		                                       String fieldLongName,
		                                       String time) 
	{
		String hourName        = "hour" + fieldName;
		String minName         = "min"  + fieldName;
	
		String[] separateTime = HTMLHelpers.separateTime3Sections(time);
			
	    String returnString = "<input " +
		   			  "type=\"text\" " + 
	   				  "name=\"" + hourName + "\" " +
	   				  "onBlur=\"checknumeric(this, '" + fieldLongName.trim() + " (Hour)')\" " +
	  				  "size=\"1\" " +
	   				  "maxlength=\"2\" " +
	   				  "value=\"" + separateTime[0] + "\"><b>:</b>" +
	   				  "<input " +
		   			  "type=\"text\" " + 
	   				  "name=\"" + minName + "\" " +
	   				  "onBlur=\"checknumeric(this, '" + fieldLongName.trim() + " (Minute)')\" " +
	  				  "size=\"1\" " +
	   				  "maxlength=\"2\" " +
	   				  "value=\"" + separateTime[1] + "\">";
	   				  
		return returnString;
	}

}
