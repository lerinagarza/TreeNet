package com.treetop.utilities.html;

import java.util.*;


public class HtmlSelect {

	private String id 						= "";
	private String name 					= "";
	private String cssClass 				= "";
	private String selectedValue 			= "";
	private String defaultValue 			= "";
	private boolean readOnly 				= false;
	private DescriptionType descriptionType	= DescriptionType.VALUE_ONLY;
	private boolean autofocus				= false;
	
	private Vector<HtmlOption> options		= new Vector<HtmlOption>();
	

	public enum DescriptionType {
		VALUE_ONLY,
		DESCRIPTION_ONLY,
		DESCRIPTION_VALUE,
		LONG_DESCRIPTION_ONLY,
		VALUE_DESCRIPTION,
		VALUE_LONG_DESCRIPTION;
	}

	public HtmlSelect () {
		//Default constructor
	}


	
	public HtmlSelect (
			String name, 
			String id, 
			String cssClass, 
			String selectedValue, 
			String defaultValue, 
			boolean readonly, 
			DescriptionType descriptionType) {
		this.setName(name);
		this.setId(id);
		this.setCssClass(cssClass);
		this.setSelectedValue(selectedValue);
		this.setDefaultValue(defaultValue);
		this.setReadOnly(readonly);
		this.setDescriptionType(descriptionType);
	}
	
	public HtmlSelect (
			String name, 
			String id, 
			String cssClass, 
			String selectedValue, 
			String defaultValue, 
			boolean readonly, 
			DescriptionType descriptionType,
			boolean autofocus) {
		this.setName(name);
		this.setId(id);
		this.setCssClass(cssClass);
		this.setSelectedValue(selectedValue);
		this.setDefaultValue(defaultValue);
		this.setReadOnly(readonly);
		this.setDescriptionType(descriptionType);
		this.setAutofocus(autofocus);
	}

	@Override
	public String toString() {
		StringBuffer html = new StringBuffer();
		
		//begin opening select tag
		html.append("<select");
		
		//Name
		html.append(" name=\"" + this.getName() + "\"");
		//ID
		if (!id.trim().equals("")) {
			html.append(" id=\"" + this.getId() + "\"");
		}
		//CSS Class
		if (!cssClass.trim().equals("")) {
			html.append(" class=\"" + this.getCssClass() + "\"");
		}
		//Read only
		if (this.isReadOnly()) {
			html.append(" disabled=\"disabled\"");
		}
		
		if(!this.isReadOnly() && this.isAutofocus()) {
			html.append(" autofocus ");
		}
					
		html.append(">");
		//end opening select tag
		
		
		//default option
		if (this.getDefaultValue() != null) {
			html.append("<option value=''>");
			html.append(defaultValue);
			html.append("</option>");
		}//end default option
		
		//vector of options
		for (int i=0; !this.getOptions().isEmpty() && i<this.getOptions().size(); i++) {
			HtmlOption option = this.getOptions().elementAt(i);
			
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
	
	public DescriptionType getDescriptionType() {
		return descriptionType;
	}
	public void setDescriptionType(DescriptionType descriptionType) {
		this.descriptionType = descriptionType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getSelectedValue() {
		return selectedValue;
	}
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Vector<HtmlOption> getOptions() {
		return options;
	}

	public void setOptions(Vector<HtmlOption> options) {
		this.options = options;
	}



	public boolean isAutofocus() {
		return autofocus;
	}



	public void setAutofocus(boolean autofocus) {
		this.autofocus = autofocus;
	}






}
