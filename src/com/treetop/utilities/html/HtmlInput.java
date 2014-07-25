package com.treetop.utilities.html;

import java.util.*;


public class HtmlInput {

	private String id				= "";
	private String name				= "";
	private InputType type			= null;
	private String value 			= "";
	private String description 		= "";
	private String descriptionLong 	= "";
	private String sequence 		= "";
	private String cssClass	 		= "";
		
	public enum InputType {
		BUTTON("button"), 
		CHECKBOX("checkbox"), 
		FILE("file"), 
		HIDDEN("hidden"), 
		IMAGE("image"), 
		PASSWORD("password"), 
		RADIO("radio"), 
		RESET("reset"), 
		SUBMIT("submit"), 
		TEXT("text");
		
		private String value;
		
		private InputType(String value) {
			this.value = value;
		}
		
		private String getValue() {
			return this.value;
		}
	
	}
	
	public HtmlInput() {
		
	}
	
	public HtmlInput(String value, String description) {
		this.setValue(value);
		this.setDescription(description);
	}
	
	@Override
	/**
	 * Renders HTML input tag
	 */
	public String toString() {
		StringBuffer html = new StringBuffer();
	
		html.append("<input");
		
		if (this.getType() != null && !this.getType().getValue().equals("")) {
			html.append(" type=\"" + this.getType().getValue() + "\"");
		}
		
		if (this.getId() != null && !this.getId().equals("")) {
			html.append(" id=\"" + this.getId() + "\"");
		}
		
		if (this.getName() != null && !this.getName().equals("")) {
			html.append(" name=\"" + this.getName() + "\"");	
		}
				
		if (this.getValue() != null && !this.getValue().equals("")) {
			html.append(" value=\"" + this.getValue() + "\"");
		}
		
		if (this.getCssClass() != null && !this.getCssClass().equals("")) {
			html.append(" class=\"" + this.getCssClass() + "\"");
		}
				
		html.append(" />");

		
		return html.toString();
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescriptionLong() {
		return descriptionLong;
	}
	public void setDescriptionLong(String descriptionLong) {
		this.descriptionLong = descriptionLong;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	/**
	 * @return the type
	 */
	public InputType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(InputType type) {
		this.type = type;
	}
	
	
}
