package com.treetop.utilities.html;

import java.util.Vector;

public class SelectionCriteria {

	private String name = "";
	private String value = "";

	private Vector<SelectionCriteria> values = new Vector<SelectionCriteria>();

	private SelectionCriteria(String name, String value) {
		this.setName(name);
		this.setValue(value);
	}

	public SelectionCriteria() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Adds a name value pair to the selection criteria collection<br />
	 * Both the name and value must be non-null and non-empty
	 * @param name
	 * @param value
	 */
	public void addValue(String name, String value) {
		if (name == null || name.trim().equals("")) {
			return;
		}
		if (value == null || value.trim().equals("")) {
			return;
		}
		this.getValues().addElement(new SelectionCriteria(name, value));
	}

	@Override
	public String toString() {
		return buildSelectionCriteriaHTML();
	}
	
	public String buildSelectionCriteriaHTML() {
		StringBuffer html = new StringBuffer();

		if (!this.getValues().isEmpty()) {
			html.append("<div>");
			html.append("<div class='selection-criteria ui-widget-content ui-corner-all' style='float:left'>");
			html.append("<h3 class='expand'>Selection Criteria</h3>");
			html.append("<div class='collapse'>");

			html.append("<table>");

			for (SelectionCriteria element : this.getValues()) {
					html.append("<tr>");

					html.append("<td>");
					html.append(element.getName() + ":");
					html.append("</td>");

					html.append("<td>");
					html.append(element.getValue());
					html.append("</td>");

					html.append("</tr>");
			}

			html.append("</table>");
			html.append("</div>");
			html.append("</div>");
			html.append("</div>");
			html.append("<div style='clear:both'></div>");
		}
		return html.toString();
	}

	public Vector<SelectionCriteria> getValues() {
		return values;
	}

	public void setValues(Vector<SelectionCriteria> values) {
		this.values = values;
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

}
