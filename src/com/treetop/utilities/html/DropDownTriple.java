/*
 * Created on April 13, 2011
 *   @author twalto
 */
package com.treetop.utilities.html;

import java.util.Vector;

import com.treetop.utilities.html.HtmlSelect.DescriptionType;

/**
 * Class created to build Javascript and HTML Code for Triple Drop Down Lists.
 * 
 * Code was copied from originally : Triple Combo Script Credit By Philip M:
 * http://www.codingforums.com/member.php?u=186 Visit http://javascriptkit.com
 * for this and over 400+ other scripts
 * 
 * NOTE: Each Piece can only have max. 64 elements.
 */
public class DropDownTriple {

	private String list1Value = "";
	private String list1Description = "";
	private String list2Value = "";
	private String list2Description = "";
	private String list3Value = "";
	private String list3Description = "";

	public static void main(String[] args) {
	}

	/**
	 * 4/22/11 - TWalton Return a Vector: build from the vector sent in. With
	 * the default Values.
	 * 
	 * @param inData
	 *            : Vector of DropDownTriple Classes list1Name : Name of the
	 *            parameter in the First Drop Down List list1All : Y have an
	 *            *all value - (Default) N Do not have an *all value list1Code :
	 *            Currently cannot have the Value and Description be Different E
	 *            - show value after a "dash" at the end of the description N -
	 *            None (DEFAULT) only use the Description (will need to code a
	 *            way to retrieve the "value" or code list2Name : Same
	 *            Definition as 1 list2All : Same Definition as 1 list2Code :
	 *            Same Definition as 1 list3Name : Same Definition as 1 list3All
	 *            : Same Definition as 1 list3Code : Same Definition as 1
	 * @return 1 - String object - catagories that go in the Head section above
	 *         the Javascript code 2 - Code for List1 3 - Code for List2 4 -
	 *         Code for List3
	 */
	public static Vector buildTripleDropDown(Vector inData, String list1Name, String list1All, String list1Code,
			String list2Name, String list2All, String list2Code, String list3Name, String list3All, String list3Code) {
		Vector returnVector = new Vector();
		try {
			// Build the Category Section of the Drop Down Lists:
			// Accomodate all the values
			StringBuffer sb = new StringBuffer();
			sb.append("<script type=\"text/javascript\">");
			sb.append("var categories = []; ");
			if (inData.size() > 0) {
				// Roll through the inData to get all the "list1 Categories
				String saveList1 = "";
				sb.append("categories[\"startList\"] = [");
				for (int x = 0; x < inData.size(); x++) {
					DropDownTriple list1 = (DropDownTriple) inData.elementAt(x);
					if (!list1.list1Value.trim().equals(saveList1)) {
						if (!saveList1.trim().equals(""))
							sb.append(", ");
						saveList1 = list1.list1Value.trim();
						sb.append("\"" + list1.list1Value.trim() + "\"");
					}
				}
				sb.append("]; ");
				// end of List 1
				// Roll Through List 1 - Generate info for List 2
				saveList1 = "";
				String saveList2 = "";
				for (int x = 0; x < inData.size(); x++) {
					DropDownTriple list1 = (DropDownTriple) inData.elementAt(x);
					if (!list1.list1Value.trim().equals(saveList1)) {
						if (!saveList1.trim().equals(""))
							sb.append("]; ");
						sb.append("categories[\"" + list1.list1Value.trim() + "\"] = [");
						saveList1 = list1.list1Value.trim();
						saveList2 = "";
					}
					if (!list1.list2Description.trim().equals(saveList2)) {
						if (!saveList2.trim().equals(""))
							sb.append(", ");
						saveList2 = list1.list2Description.trim();
						sb.append("\"" + list1.list2Description.trim());
						if (list2Code.trim().equals("E")) {
							sb.append("-*-" + list1.list2Value.trim());
						}
						sb.append("\"");
					}
				}
				sb.append("]; ");
				// end of List 2
				// Roll Through List 2 - Generate info for List 3
				saveList2 = "";
				String saveList3 = "";
				for (int x = 0; x < inData.size(); x++) {
					DropDownTriple list1 = (DropDownTriple) inData.elementAt(x);
					if (!list1.list2Description.trim().equals(saveList2)) {
						if (!saveList2.trim().equals(""))
							sb.append("]; ");
						sb.append("categories[\"" + list1.list2Description.trim());
						if (list2Code.trim().equals("E")) {
							sb.append("-*-" + list1.list2Value.trim());
						}
						sb.append("\"] = [");
						saveList2 = list1.list2Description.trim();
						saveList3 = "";
					}
					if (!list1.list3Description.trim().equals(saveList3)) {
						if (!saveList3.trim().equals(""))
							sb.append(", ");
						saveList3 = list1.list3Description.trim();
						sb.append("\"" + list1.list3Description.trim());
						if (list3Code.trim().equals("E")) {
							sb.append("-*-" + list1.list3Value.trim());
						}
						sb.append("\"");
					}
				}
				sb.append("]; ");
				// end of List 3
			} // end of if there is data on the inData
			sb.append("</script>");
			returnVector.add(sb.toString());
			// Build the Display for the Screen for each piece, set each piece
			// into a separate vector element
			StringBuffer fieldInfo = new StringBuffer();
			fieldInfo.append("<select name='" + list1Name + "' ");
			fieldInfo.append("onchange=\"fillSelect(this.value,this.form['");
			fieldInfo.append(list2Name + "'])\">");
			if (list1All.trim().equals("")) {
				fieldInfo.append("<option selected>Make a selection</option>");
			}
			fieldInfo.append("</select>");
			returnVector.add(fieldInfo.toString());
			fieldInfo = new StringBuffer();
			fieldInfo.append("<select name='" + list2Name + "' ");
			fieldInfo.append("onchange=\"fillSelect(this.value,this.form['");
			fieldInfo.append(list3Name + "'])\">");
			if (list1All.trim().equals("")) {
				fieldInfo.append("<option selected>Make a selection</option>");
			}
			fieldInfo.append("</select>");
			returnVector.add(fieldInfo.toString());
			fieldInfo = new StringBuffer();
			fieldInfo.append("<select name='" + list3Name + "' >");
			if (list1All.trim().equals("")) {
				fieldInfo.append("<option selected>Make a selection</option>");
			}
			fieldInfo.append("</select>");
			returnVector.add(fieldInfo.toString());

			// <select name='inqRegion'
			// onchange="fillSelect(this.value,this.form['inqWhseNumber'])">
			// <option selected>Make a selection</option>
			// </select>
			// <br>
			// &nbsp;
			// <select name='inqWhseNumber'
			// onchange="fillSelect(this.value,this.form['inqLocNo'])">
			// <option selected>Make a selection</option>
			// </select>
			// <br>
			// &nbsp;
			// <select name='inqLocNo'">
			// <option selected >Make a selection</option>
			// </select>

		} catch (Exception e) {
			System.out
					.println("Error Could not Build Drop Down list: " + "com.treetop.utilities.DropDownTriple : " + e);
		}
		return returnVector;

	}
	
	public static String buildList1(Vector<DropDownTriple> values, String name, String id, String cssClass,
			String selectedValue, String defaultValue, boolean readOnly, DescriptionType descriptionType) {
		return buildList1(values, name, id, cssClass, selectedValue, defaultValue, readOnly, descriptionType, false);
	}

	public static String buildList1(Vector<DropDownTriple> values, String name, String id, String cssClass,
			String selectedValue, String defaultValue, boolean readOnly, DescriptionType descriptionType, boolean autofocus) {

		Vector<String> list1Values = new Vector<String>();
		Vector<HtmlOption> list1Options = new Vector<HtmlOption>();

		for (DropDownTriple value : values) {

			if (list1Values.contains(value.getList1Value())) {
				// already added, do nothing
			} else {
				// add it
				list1Values.addElement(value.getList1Value());

				HtmlOption option = new HtmlOption();
				option.setValue(value.getList1Value());
				option.setDescription(value.getList1Description());
				option.setDescriptionLong(value.getList1Description());
				list1Options.addElement(option);
			}
		}

		HtmlSelect select = new HtmlSelect(name, id, cssClass, selectedValue, defaultValue, 
				readOnly, descriptionType, autofocus);
		select.setOptions(list1Options);

		return select.toString();
	}

	public static String buildList2(Vector<DropDownTriple> values, String name, String id, String cssClass,
			String selectedValue, String defaultValue, boolean readOnly, DescriptionType descriptionType) {

		Vector<String> list2Values = new Vector<String>();
		Vector<HtmlOption> list2Options = new Vector<HtmlOption>();

		for (DropDownTriple value : values) {

			if (list2Values.contains(value.getList2Value())) {
				// already added, do nothing
			} else {
				// add it
				list2Values.addElement(value.getList2Value());

				HtmlOption option = new HtmlOption();

				option.setCssClass(value.getList1Value());

				option.setValue(value.getList2Value());
				option.setDescription(value.getList2Description());
				option.setDescriptionLong(value.getList2Description());
				list2Options.addElement(option);
			}
		}

		HtmlSelect select = new HtmlSelect(name, id, cssClass, selectedValue, defaultValue, readOnly, descriptionType);
		select.setOptions(list2Options);

		return select.toString();
	}

	public static String buildList3(
			Vector<DropDownTriple> values, 
			String list1Id,
			String list2Id,
			String name, 
			String list3Id,
			String cssClass, 
			String selectedValue, 
			String defaultValue, 
			boolean readOnly,
			DescriptionType descriptionType) {

		Vector<HtmlOption> list3Options = new Vector<HtmlOption>();

		for (DropDownTriple value : values) {

			HtmlOption option = new HtmlOption();

			// set the associated master value as the cssClass value (works with
			// jQuery plugin)
			option.setCssClass(value.getList2Value());

			option.setValue(value.getList3Value());
			option.setDescription(value.getList3Description());
			option.setDescriptionLong(value.getList3Description());
			list3Options.addElement(option);

		}

		HtmlSelect select = new HtmlSelect(name, list3Id, cssClass, selectedValue, defaultValue, readOnly, descriptionType);
		select.setOptions(list3Options);

		StringBuffer html = new StringBuffer();
		html.append(select.toString() + "\r");
		html.append("<script type='text/javascript'>$('#" + list3Id + "').chainedTo('#" + list2Id + "');</script>" + "\r");
		html.append("<script type='text/javascript'>$('#" + list2Id + "').chainedTo('#" + list1Id + "');</script>" + "\r");
		return html.toString();
	}
	
	public static String buildChainedYear(
			Vector<DropDownTriple> values, 
			String name, 
			String id, 
			String cssClass, 
			String selectedValue, 
			String defaultValue, 
			boolean readOnly, 
			DescriptionType descriptionType) {
		
		Vector<String> level1Values = new Vector<String>();
		Vector<HtmlOption> level1Options = new Vector<HtmlOption>();
		
		for (DropDownTriple value : values) {
			
			if (level1Values.contains(value.getList1Value())) {
				//already added, do nothing
			} else {
				//add it
				level1Values.addElement(value.getList1Value());
				
				HtmlOption option = new HtmlOption();
				option.setValue(value.getList1Value());
				option.setDescription(value.getList1Description());
				option.setDescriptionLong(value.getList1Description());
				level1Options.addElement(option);
			}
		}

		HtmlSelect select = new HtmlSelect(name, id, cssClass, selectedValue, defaultValue, readOnly, descriptionType);
		select.setOptions(level1Options);
		
		return select.toString();
	}
	
	public static String buildChainedPeriod(
			Vector<DropDownTriple> values, 
			String masterId,
			String name, 
			String id, 
			String cssClass, 
			String selectedValue, 
			String defaultValue, 
			boolean readOnly, 
			DescriptionType descriptionType) {
		
		Vector<String> level2Values = new Vector<String>();
		Vector<HtmlOption> level2Options = new Vector<HtmlOption>();
		
		for (DropDownTriple value : values) {

				if (level2Values.contains(value.getList1Value() + "-" + value.getList2Value())) {
					//already added, do nothing
				} else {
					//add it
					level2Values.addElement(value.getList1Value() + "-" + value.getList2Value());
					
					HtmlOption option = new HtmlOption();
					//set the associated master value as the cssClass value (works with jQuery plugin)
					option.setCssClass(value.getList1Value());
					
					option.setValue(value.getList1Value() + "-" + value.getList2Value());
					option.setDescription(value.getList2Description());
					option.setDescriptionLong(value.getList2Description());
					level2Options.addElement(option);
				}
			
		}

		HtmlSelect select = new HtmlSelect(name, id, cssClass, selectedValue, defaultValue, readOnly, descriptionType);
		select.setOptions(level2Options);
		
		StringBuffer html = new StringBuffer();
		html.append(select.toString() + "\r");
		html.append("<script type='text/javascript'>$('#" + id + "').chainedTo('#" + masterId + "');</script>" + "\r");
		
		
		
		return html.toString();
	}
	
	public static String buildChainedWeek(
			Vector<DropDownTriple> values, 
			String masterId,
			String name, 
			String id, 
			String cssClass, 
			String selectedValue, 
			String defaultValue, 
			boolean readOnly, 
			DescriptionType descriptionType) {
		
		Vector<HtmlOption> level3Options = new Vector<HtmlOption>();
		
		for (DropDownTriple value : values) {
				
				HtmlOption option = new HtmlOption();
				
				//set the associated master value as the cssClass value (works with jQuery plugin)
				option.setCssClass(value.getList1Value() + "-" + value.getList2Value());
				
				option.setValue(value.getList3Value());
				option.setDescription(value.getList3Description());
				option.setDescriptionLong(value.getList3Description());
				level3Options.addElement(option);
			
		}

		HtmlSelect select = new HtmlSelect(name, id, cssClass, selectedValue, defaultValue, readOnly, descriptionType);
		select.setOptions(level3Options);
		
		StringBuffer html = new StringBuffer();
		html.append(select.toString() + "\r");
		html.append("<script type='text/javascript'>$('#" + id + "').chainedTo('#" + masterId + "');" + "\r");
		html.append("$('#" + id + " option[selected]').prop('selected',true);</script>" + "\r");
		
		return html.toString();
	}

	public String getList1Description() {
		return list1Description;
	}

	public void setList1Description(String list1Description) {
		this.list1Description = list1Description;
	}

	public String getList1Value() {
		return list1Value;
	}

	public void setList1Value(String list1Value) {
		this.list1Value = list1Value;
	}

	public String getList2Description() {
		return list2Description;
	}

	public void setList2Description(String list2Description) {
		this.list2Description = list2Description;
	}

	public String getList2Value() {
		return list2Value;
	}

	public void setList2Value(String list2Value) {
		this.list2Value = list2Value;
	}

	public String getList3Description() {
		return list3Description;
	}

	public void setList3Description(String list3Description) {
		this.list3Description = list3Description;
	}

	public String getList3Value() {
		return list3Value;
	}

	public void setList3Value(String list3Value) {
		this.list3Value = list3Value;
	}

}
