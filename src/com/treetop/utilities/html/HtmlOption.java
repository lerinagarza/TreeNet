package com.treetop.utilities.html;

import java.util.*;


public class HtmlOption {

	private String id				= "";
	private String value 			= "";
	private String description 		= "";
	private String descriptionLong 	= "";
	private String sequence 		= "";
	private String cssClass	 		= "";
		
	/**
	 * Default constructor
	 */
	public HtmlOption() {
		
	}
	
	/**
	 * Build HtmlOption with value and description
	 * @param value
	 * @param description
	 */
	public HtmlOption(String value, String description) {
		this.setValue(value);
		this.setDescription(description);
	}
	
	/**
	 * Convert DropDownSingle into HtmlOption
	 * @param dds
	 */
	public HtmlOption(DropDownSingle dds) {
		this.setValue(dds.getValue());
		this.setDescription(dds.getDescription());
	}
	
	/**
	 * Convert vector of DropDownSingle objects to Vector of HtmlOption objects
	 * @param dds
	 * @return
	 */
	public static Vector<HtmlOption> convertDropDownSingleVector(Vector<DropDownSingle> dds) {
		Vector<HtmlOption> returnData = new Vector<HtmlOption>();
		
		for (int i=0; !dds.isEmpty() && i<dds.size(); i++) {
			HtmlOption option = new HtmlOption((DropDownSingle)dds.elementAt(i));
			returnData.addElement(option);
		}
				
		return returnData;
	}
	
	@Override
	public String toString() {
		StringBuffer html = new StringBuffer();
		
		html.append("<option");
		if (!this.getId().trim().equals("")) {
			html.append(" id=\"" + this.getId() + "\"");
		}
		
		html.append(" value=\"" + this.getValue() + "\"");
		if (!this.getCssClass().trim().equals("")) {
			html.append(" class=\"" + this.getCssClass() + "\"");	
		}
		
		html.append(">");
		html.append(this.getDescription());
		html.append("</option>");
		
		return html.toString();
	}
	
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
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
	
	
}
