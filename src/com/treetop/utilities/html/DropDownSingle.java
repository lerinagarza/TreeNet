/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.utilities.html;

import java.util.Vector;

import com.treetop.utilities.html.HtmlSelect.DescriptionType;

/**
 * @author twalto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DropDownSingle {
	/**
	 * Return a Vector: build the Options Section for the 
	 *                Drop Down List.
	 * @param inData   : Vector of DropDownSingle Classes
	 *        inValue  : Value to Begin on.
	 *        valueDescLoc: B = Beginning with a Dash After (Default)
	 * 					 	E = Ending with a Dash Before
	 * 						N = None - Only display the description
	 * 						V = Show Values Only
	 * 		  
	 * @return
	 */
	private static String buildOptions(Vector inData,
									   String inValue,
									   String valueDescLoc) {
		StringBuffer returnString = new StringBuffer();
		try
		{
			if (inData != null &&
			    inData.size() > 0)
			{
				for (int x = 0; x < inData.size(); x++)
				{
					DropDownSingle dds = (DropDownSingle) inData.elementAt(x);
					String selected = "";
					if (inValue.trim().equals(dds.getValue().trim()))
						selected = "selected='selected'";
					returnString.append("<option value='");
					returnString.append(dds.getValue());
					returnString.append("' ");
					returnString.append(selected);
					returnString.append(">");
					if (valueDescLoc.equals("V"))
					{
						returnString.append(dds.getValue());
					}
					else
					{
						if (!valueDescLoc.equals("N") &&
							!valueDescLoc.equals("E"))
						{
							returnString.append(dds.getValue());
							returnString.append(" - ");
						}
						returnString.append(dds.getDescription());
						if (valueDescLoc.equals("E"))
						{
							returnString.append(" - ");
							returnString.append(dds.getValue());
						}
					}
					returnString.append("</option>");
					returnString.append("&nbsp;");	
				}   
			}
			
		} catch (Exception e) {
			System.out.println(
				"Error Could not Build Drop Down list: "
					+ "com.treetop.utilities."
					+ "DropDownSingle.buildOptions : "
					+ e);
		}
		return returnString.toString();
	
	}
	private String value = "";
	private String description = "";
	
	/**
	 * @param inData   : Vector of DropDownSingle Classes
	 *        inName   : Name of the Drop Down List
	 *        inValue  : Value to Begin on.
	 * 		  inSelect : What the selection should say, default *all
	 * 					  None IF you want to start with Data 
	 *        valueDescLoc: B = Beginning with a Dash After (Default)
	 * 					 	E = Ending with a Dash Before
	 * 						N = None - Only display the description
	 *                      V = Show only Value
	 *        readOnly  : Read Only Option
	 * 
	 * @return
	 * @deprecated
	 *    String that is the HTML Code
	 */
	public static String buildDropDown(
		Vector inData,
		String inName,
		String inValue,
		String inSelect,
		String valueDescLoc,
		String readOnly) {
		StringBuffer returnString = new StringBuffer();
		if (inSelect.equals(""))
		  inSelect = "*all";
		try
		{
			String options = buildOptions(inData, inValue, valueDescLoc);
			if (!options.trim().equals(""))
			{
				returnString.append("<select name='");
				returnString.append(inName.trim());
				returnString.append("'");
				
				returnString.append(" id='" + inName.trim() + "' ");
				
				if (readOnly != null && readOnly.toUpperCase().trim().equals("Y"))
				returnString.append(" DISABLED");		
				returnString.append(" style=\"width:100%\">");				
				if (!inSelect.toLowerCase().equals("none"))
				{
					returnString.append("<option value=''>");
					returnString.append(inSelect);
				}
				returnString.append(options);
				returnString.append("</select>"); 
				if (inValue != null &&
					!inValue.trim().equals("") &&
					readOnly != null &&
					readOnly.toUpperCase().trim().equals("Y"))
				   returnString.append(HTMLHelpersInput.inputBoxHidden(inName, inValue));	
			}
			else
			  returnString.append("&nbsp;");
			
		} catch (Exception e) {
			System.out.println(
				"Error Could not Build Drop Down list: "
					+ "com.treetop.utilities."
					+ "DropDownSingle : "
					+ e);
		}
		return returnString.toString();
	}

	/**
	 * @param inData   : Vector of DropDownSingle Classes
	 *        inName   : Name of the Drop Down List
	 *        inValue  : Value to Begin on.
	 * 		  inSelect : What the selection should say, default *all
	 * 					  None IF you want to start with Data 
	 *        valueDescLoc: B = Beginning with a Dash After (Default)
	 * 					 	E = Ending with a Dash Before
	 * 						N = None - Only display the description
	 *                      V = Show only Value
	 *        readOnly  : Read Only Option
	 *        % width:  if blank, will default to content size
	 * 
	 * @return
	 *    String that is the HTML Code
	 */
	public static String buildDropDown(
		Vector inData,
		String inName,
		String inValue,
		String inSelect,
		String valueDescLoc,
		String readOnly,
		String width) {
		StringBuffer returnString = new StringBuffer();
		if (inSelect.equals(""))
		  inSelect = "*all";
		try
		{
			String options = buildOptions(inData, inValue, valueDescLoc);
			if (!options.trim().equals(""))
			{
				returnString.append("<select name='");
				returnString.append(inName.trim());
				returnString.append("' id='");
				returnString.append(inName.trim());
				returnString.append("'");
				if (readOnly != null && readOnly.toUpperCase().trim().equals("Y"))
				returnString.append(" disabled='disabled'");
				
				if (!width.equals("")) {
					returnString.append(" style=\"width:" + width +"%\"");
				}
				
				returnString.append(">");
				
				if (!inSelect.toLowerCase().equals("none"))
				{
					returnString.append("<option value=''>");
					returnString.append(inSelect);
					returnString.append("</option>");
				}
				returnString.append(options);
				returnString.append("</select>");
				if (inValue != null &&
					!inValue.trim().equals("") &&
					readOnly != null &&
					readOnly.toUpperCase().trim().equals("Y"))
				   returnString.append(HTMLHelpersInput.inputBoxHidden(inName, inValue));	
			}
			else
			  returnString.append("&nbsp;");
			
		} catch (Exception e) {
			System.out.println(
				"Error Could not Build Drop Down list: "
					+ "com.treetop.utilities."
					+ "DropDownSingle : "
					+ e);
		}
		return returnString.toString();
	}
	
	public static String buildDropDown(HtmlSelect select, Vector<HtmlOption> options) throws Exception {
		return buildDropDown(options, 
				select.getName(), 
				select.getId(), 
				select.getSelectedValue(), 
				select.getDefaultValue(),
				select.isReadOnly(),
				select.getCssClass(),
				select.getDescriptionType()
				)
				;
	}
	
	public static String buildDropDown(
			Vector<HtmlOption> options, 
			String name, 
			String id, 
			String selectedValue,
			String defaultValue,
			boolean readOnly,
			String cssClass,
			DescriptionType descriptionType) throws Exception {
		
		StringBuffer html = new StringBuffer();
		
		if (name.trim().equals("")) {
			throw new Exception ("Name cannot be blank.");
		}
		
		//begin opening select tag
		html.append("<select");
		
		//Name
		html.append(" name=\"" + name + "\"");
		//ID
		if (!id.trim().equals("")) {
			html.append(" id=\"" + id + "\"");
		}
		//CSS Class
		if (!cssClass.trim().equals("")) {
			html.append(" class=\"" + cssClass + "\"");
		}
		//Read only
		if (readOnly) {
			html.append(" disabled=\"disabled\"");
		}
					
		html.append(">");
		//end opening select tag
		
		
		//default option
		if (defaultValue != null) {
			html.append("<option value=\"" + defaultValue + "\">");
			html.append(defaultValue);
			html.append("</option>");
		}//end default option
		
		//vector of options
		for (int i=0; !options.isEmpty() && i<options.size(); i++) {
			HtmlOption option = options.elementAt(i);
			
			html.append("<option");
			
			//set CSS class
			if (!option.getCssClass().trim().equals("")) {
				html.append(" class=\"" + option.getCssClass() + "\"");
			}
			
			//if the value of this options is the same as the "selectedValue" passed in, make it pre-selected
			if (option.getValue().trim().toUpperCase().equals(selectedValue.toUpperCase())) {
				html.append(" selected=\"selected\"");
			}
			//set the value of this option
			html.append(" value=\"" + option.getValue() + "\"");
			html.append(">");
			
			//choose how to display the descriptions
			if (descriptionType.equals(DescriptionType.VALUE_ONLY)) {
				html.append(option.getValue());
			} else if (descriptionType.equals(DescriptionType.DESCRIPTION_ONLY)) {
				html.append(option.getDescription());
			} else if (descriptionType.equals(DescriptionType.DESCRIPTION_VALUE)) {
				html.append(option.getDescription() + " - " + option.getValue());
			} else if (descriptionType.equals(DescriptionType.LONG_DESCRIPTION_ONLY)) {
				html.append(option.getDescriptionLong());
			} else if (descriptionType.equals(DescriptionType.VALUE_DESCRIPTION)) {
				html.append(option.getValue() + " - " + option.getDescription());
			} else if (descriptionType.equals(DescriptionType.VALUE_LONG_DESCRIPTION)) {
				html.append(option.getValue() + " - " + option.getDescriptionLong());
			} 
			
			html.append("</option>");
			
		}  //end vector of options
		
		//closing select tag
		html.append("</select>");
		
		
		return html.toString();
	}

	public static void main(String[] args) {
		
		String html = "";
		HtmlSelect select = new HtmlSelect(
			"event","event", "myClass", "1", null, false, DescriptionType.VALUE_LONG_DESCRIPTION);
				
		
		Vector<HtmlOption> options = new Vector<HtmlOption>();
		HtmlOption option1 = new HtmlOption();
		option1.setValue("1");
		option1.setDescription("One");
		option1.setDescriptionLong("Number One");
		options.addElement(option1);
		
		HtmlOption option2 = new HtmlOption();
		option2.setValue("2");
		option2.setDescription("Two");
		option2.setDescriptionLong("Number Two");
		options.addElement(option2);
		
		
		try {
		html = buildDropDown(select, options);
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(html);
		
	}
	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
	}

	/**
	 * @param string
	 */
	public void setValue(String string) {
		value = string;
	}

}
