/*
 * Created on Aug 10, 2005
 *
 */
package com.treetop.utilities.html;

import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Vector;

import com.treetop.utilities.html.HtmlSelect.DescriptionType;

/**
 * Class created to build Javascript and HTML Code for
 *   Dual Drop Down Lists.
 * NOTE:  Each Piece can only have max. 64 elements.
 * @author twalto
 */
public class DropDownDual {

	private String masterValue = "";
	private String masterDescription = "";
	private String slaveValue = "";
	private String slaveDescription = "";

	public static void main(String[] args) {

		Vector testVector = new Vector();
		testVector.addElement("one");
		Vector returnVector = new Vector();
//		returnVector = buildDualDropDown(testVector, "name1", "name2", "" );

	}
	/**
	 * @return
	 */
	public String getMasterValue() {
		return masterValue;
	}

	/**
	 * @return
	 */
	public String getSlaveValue() {
		return slaveValue;
	}

	/**
	 * @param string
	 */
	public void setMasterValue(String string) {
		masterValue = string;
	}

	/**
	 * @param string
	 */
	public void setSlaveValue(String string) {
		slaveValue = string;
	}

	/**
	 * @return
	 */
	public String getMasterDescription() {
		return masterDescription;
	}

	/**
	 * @return
	 */
	public String getSlaveDescription() {
		return slaveDescription;
	}

	/**
	 * @param string
	 */
	public void setMasterDescription(String string) {
		masterDescription = string;
	}

	/**
	 * @param string
	 */
	public void setSlaveDescription(String string) {
		slaveDescription = string;
	}
	/**
	 * Return a Vector: build from the vector sent in.
	 *     With the default Values.
	 * @param inData     : Vector of DropDownDual Classes
	 *        masterName : Name of the First Drop Down List
	 * 		  masterAll  : Y have an *all value - (Default)
	 *                     N Do not have an *all value
	 *        masterValue: Value to Begin on.
	 *        slaveName  : Name of the Second Drop Down List
	 *        slaveAll   : Y have an *all value - (Default)
	 *                     N Do not have an *all value
	 * 		 slaveValue  : Value to add to beginning.
	 *        Codes for both valueDescLocMaster and valueDescLocSlave
	 *        			: B = Beginning with a Dash After (Default)
	 * 					 	E = Ending with a Dash Before
	 * 						N = None - Only display the description
	 * @return
	 */
	@Deprecated
	public static Vector buildDualDropDown(
		Vector inData,
		String masterName,
		String masterAll,
		String masterValue,
		String slaveName,
		String slaveAll,
		String slaveValue,
		String valueDescLocMaster,
		String valueDescLocSlave) {
		Vector returnVector = new Vector();
		if (valueDescLocMaster == null
			|| (!valueDescLocMaster.equals("E") && !valueDescLocMaster.equals("N")))
			valueDescLocMaster = "B";
		if (valueDescLocSlave == null
			|| (!valueDescLocSlave.equals("E") && !valueDescLocSlave.equals("N")))
				valueDescLocSlave = "B";
		if (slaveAll == null ||
		    !slaveAll.equals("N"))
		    slaveAll = "Y";
		if (masterAll == null ||
			!masterAll.equals("N"))
			masterAll = "Y";
		try {
			if (inData.size() > 0) {
				StringBuffer listMasterDisplay = new StringBuffer();
			    listMasterDisplay.append("'*all'");
	
				StringBuffer listMasterValue = new StringBuffer();
				listMasterValue.append("'*all'");
	
				// Build a slaveInfoList section tied to EACH Master
				//   Will go into infoLists
				StringBuffer slaveInfoLists = new StringBuffer();
				// *all section
				slaveInfoLists.append(" lists['*all'] = new Array(); ");
				slaveInfoLists.append(" lists['*all'][0]");
				slaveInfoLists.append(" = new Array( '*all'); ");
				slaveInfoLists.append(" lists['*all'][1]");
				slaveInfoLists.append(" = new Array( '*all'); ");
	
				StringBuffer infoLists = new StringBuffer();
				infoLists.append("<script language=\"javascript\"> ");
				infoLists.append("var lists = new Array();");
	
				StringBuffer masterDropDown = new StringBuffer();
				masterDropDown.append("<select name=\"");
				masterDropDown.append(masterName);
				masterDropDown.append("\" size=1 ");
				masterDropDown.append("onchange=\"change");
				masterDropDown.append(masterName);
				masterDropDown.append("(this)\">");
				if (masterAll.equals("Y"))
					masterDropDown.append("<option value=\"*all\">*all");
	
				String selected = "selected=\"selected\" ";
	
				String saveMasterValue = "";
				String saveMasterDescription = "";
				StringBuffer slaveValues = new StringBuffer();
				StringBuffer slaveDisplay = new StringBuffer();
				if (slaveAll.equals("Y"))
				{
				  slaveValues.append("'*all'");
				  slaveDisplay.append("'*all'");
				}
				for (int x = 0; x < inData.size(); x++) {
					DropDownDual thisElement =
						(DropDownDual) inData.elementAt(x);
					if (slaveValue.equals(thisElement.getSlaveValue()))
					{
						StringBuffer sv = new StringBuffer();
						StringBuffer sd = new StringBuffer();
						sv.append("'" + thisElement.getSlaveValue() + "'");
						sd.append("'");
						if (valueDescLocSlave.equals("B")) {
							sd.append(thisElement.getSlaveValue());
							sd.append(" - ");
						}
						sd.append(thisElement.getSlaveDescription());
						if (valueDescLocSlave.equals("E")) {
							sd.append(" - ");
							sd.append(thisElement.getSlaveValue());
						}
						sd.append("' ");
						if (!slaveValues.toString().equals(""))
						{
							sv.append(", ");
							sd.append(", ");
						}
						sv.append(slaveValues.toString());
						sd.append(slaveDisplay.toString());
						slaveValues = new StringBuffer();
						slaveDisplay = new StringBuffer();
						slaveValues = sv;
						slaveDisplay = sd;
					}
					if (!saveMasterValue.equals("")
						&& !thisElement.getMasterValue().equals(
							saveMasterValue)) {
						// Tie Slave Lists to Master Codes
						slaveInfoLists.append(" lists['");
						slaveInfoLists.append(saveMasterValue);
						slaveInfoLists.append("'] = new Array(); ");
						slaveInfoLists.append(" lists['");
						slaveInfoLists.append(saveMasterValue);
						slaveInfoLists.append("'][0]");
						slaveInfoLists.append(" = new Array( ");
						slaveInfoLists.append(slaveDisplay.toString());
						slaveInfoLists.append("); ");
						slaveInfoLists.append(" lists['");
						slaveInfoLists.append(saveMasterValue);
						slaveInfoLists.append("'][1]");
						slaveInfoLists.append(" = new Array(");
						slaveInfoLists.append(slaveValues.toString());
						slaveInfoLists.append("); ");
	
						selected = "";
	
						if (masterValue != null
							&& masterValue.equals(saveMasterValue))
							selected = "selected=\"selected\" ";
	
						if (!listMasterDisplay.toString().equals(""))
							listMasterDisplay.append(",");
	
						listMasterDisplay.append(" '");
						if (valueDescLocMaster.equals("B")) {
							listMasterDisplay.append(saveMasterValue);
							listMasterDisplay.append(" - ");
						}
						listMasterDisplay.append(saveMasterDescription);
						if (valueDescLocMaster.equals("E")) {
							listMasterDisplay.append(" - ");
							listMasterDisplay.append(saveMasterValue);
						}
						listMasterDisplay.append("' ");
	
						if (!listMasterValue.toString().equals(""))
							listMasterValue.append(",");
	
						listMasterValue.append(" '");
						listMasterValue.append(saveMasterValue);
						listMasterValue.append("' ");
	
						masterDropDown.append("<option value=\"");
						masterDropDown.append(saveMasterValue);
						masterDropDown.append("\" ");
						masterDropDown.append(selected);
						masterDropDown.append(">");
						if (valueDescLocMaster.equals("B")) {
							masterDropDown.append(saveMasterValue);
							masterDropDown.append(" - ");
						}
						masterDropDown.append(saveMasterDescription.trim());
						if (valueDescLocMaster.equals("E")) {
							masterDropDown.append(" - ");
							masterDropDown.append(saveMasterValue);
	
						}
	
						// RESET Slave Information
						slaveValues = new StringBuffer();
						slaveDisplay = new StringBuffer();
	
						if (slaveAll.equals("Y"))
						{
						  slaveValues.append("'*all'");
						  slaveDisplay.append("'*all'");
						}
						
					}
					saveMasterValue = thisElement.getMasterValue();
					saveMasterDescription = thisElement.getMasterDescription();
	
					// Build the list of Slave Codes and Descriptions to be used
					//   For EACH Master - Ties them Together
	
					if (!slaveDisplay.toString().equals(""))
						slaveDisplay.append(",");
					slaveDisplay.append("'");
					if (valueDescLocSlave.equals("B")) {
						slaveDisplay.append(thisElement.getSlaveValue());
						slaveDisplay.append(" - ");
					}
					slaveDisplay.append(thisElement.getSlaveDescription());
					if (valueDescLocSlave.equals("E")) {
						slaveDisplay.append(" - ");
						slaveDisplay.append(thisElement.getSlaveValue());
					}
					slaveDisplay.append("' ");
	
					if (!slaveValues.toString().equals(""))
						slaveValues.append(",");
					slaveValues.append("'");
					slaveValues.append(thisElement.getSlaveValue());
					slaveValues.append("' ");
				} // END of For Loop
	
				// Do the Final Master Setting
				if (!listMasterDisplay.toString().equals("")) {
					slaveInfoLists.append(" lists['");
					slaveInfoLists.append(saveMasterValue);
					slaveInfoLists.append("'] = new Array(); ");
					slaveInfoLists.append(" lists['");
					slaveInfoLists.append(saveMasterValue);
					slaveInfoLists.append("'][0]");
					slaveInfoLists.append(" = new Array( ");
					slaveInfoLists.append(slaveDisplay.toString());
					slaveInfoLists.append("); ");
					slaveInfoLists.append(" lists['");
					slaveInfoLists.append(saveMasterValue);
					slaveInfoLists.append("'][1]");
					slaveInfoLists.append(" = new Array(");
					slaveInfoLists.append(slaveValues.toString());
					slaveInfoLists.append("); ");
	
					selected = "";
	
					if (masterValue != null
						&& masterValue.equals(saveMasterValue))
						selected = "selected=\"selected\" ";
	
					if (!listMasterDisplay.toString().equals(""))
						listMasterDisplay.append(",");
	
					listMasterDisplay.append(" '");
					if (valueDescLocMaster.equals("B")) {
						listMasterDisplay.append(saveMasterValue);
						listMasterDisplay.append(" - ");
					}
					listMasterDisplay.append(saveMasterDescription);
					if (valueDescLocMaster.equals("E")) {
						listMasterDisplay.append(" - ");
						listMasterDisplay.append(saveMasterValue);
					}
					listMasterDisplay.append("' ");
	
					if (!listMasterValue.toString().equals(""))
						listMasterValue.append(",");
	
					listMasterValue.append(" '");
					listMasterValue.append(saveMasterValue);
					listMasterValue.append("' ");
	
					masterDropDown.append("<option value=\"");
					masterDropDown.append(saveMasterValue);
					masterDropDown.append("\" ");
					masterDropDown.append(selected);
					masterDropDown.append(">");
					if (valueDescLocMaster.equals("B")) {
						masterDropDown.append(saveMasterValue);
						masterDropDown.append(" - ");
					}
					masterDropDown.append(saveMasterDescription.trim());
					if (valueDescLocMaster.equals("E")) {
						masterDropDown.append(" - ");
						masterDropDown.append(saveMasterValue);
					}
					
					// Create the MASTER Display, and Master Value Section
					infoLists.append(" lists['*all'] = new Array(); ");
					infoLists.append(" lists['*all'][0]");
					infoLists.append(" = new Array( ");
					infoLists.append(listMasterDisplay.toString());
					infoLists.append("); ");
					infoLists.append(" lists['*all'][1]");
					infoLists.append(" = new Array( ");
					infoLists.append(listMasterValue.toString());
					infoLists.append("); ");
					infoLists.append(slaveInfoLists.toString());
				}
				infoLists.append("</script>");
				
				masterDropDown.append("</select>");
				// Add the section for the Body Tag.
				returnVector.addElement(masterDropDown.toString());
				returnVector.addElement(infoLists.toString());
	
			}
		} catch (Exception e) {
			System.out.println(
				"Error Could not Build Drop Down list: "
					+ "com.treetop.utilities."
					+ "DropDownDual : "
					+ e);
		}
		return returnVector;
	
	}
	/**
	 * Return a Vector: build from the vector sent in.
	 *     With the default Values.
	 * @param inData     : Vector of DropDownDual Classes
	 *        masterName : Name of the First Drop Down List
	 *        masterValue: Value to Begin on.
	 *        slaveName  : Name of the Second Drop Down List
	 *        slaveAll   : Y have an *all value - (Default)
	 *                     N Do not have an *all value
	 *        valueDescLoc: B = Beginning with a Dash After (Default)
	 * 					 	E = Ending with a Dash Before
	 * 						N = None - Only display the description
	 * @return
	 */
	@Deprecated
	public static Vector buildDualDropDown(
		Vector inData,
		String masterName,
		String masterValue,
		String slaveName,
		String slaveAll,
		String valueDescLoc) {
		Vector returnVector = new Vector();
		if (valueDescLoc == null
			|| (!valueDescLoc.equals("E") && !valueDescLoc.equals("N")))
			valueDescLoc = "B";
		if (slaveAll == null ||
		    !slaveAll.equals("N"))
		    slaveAll = "Y";
		try {
			if (inData.size() > 0) {
				StringBuffer listMasterDisplay = new StringBuffer();
				listMasterDisplay.append("'*all'");
	
				StringBuffer listMasterValue = new StringBuffer();
				listMasterValue.append("'*all'");
	
				// Build a slaveInfoList section tied to EACH Master
				//   Will go into infoLists
				StringBuffer slaveInfoLists = new StringBuffer();
				// *all section
				slaveInfoLists.append(" lists['*all'] = new Array(); ");
				slaveInfoLists.append(" lists['*all'][0]");
				slaveInfoLists.append(" = new Array( '*all'); ");
				slaveInfoLists.append(" lists['*all'][1]");
				slaveInfoLists.append(" = new Array( '*all'); ");
	
				StringBuffer infoLists = new StringBuffer();
				infoLists.append("<script language=\"javascript\"> ");
				infoLists.append("var lists = new Array();");
	
				StringBuffer masterDropDown = new StringBuffer();
				masterDropDown.append("<select name=\"");
				masterDropDown.append(masterName);
				masterDropDown.append("\" size=1 ");
				masterDropDown.append("onchange=\"change");
				masterDropDown.append(masterName);
				masterDropDown.append("(this)\">");
				masterDropDown.append("<option value=\"*all\">*all");
	
				String selected = "selected=\"selected\" ";
	
				String saveMasterValue = "";
				String saveMasterDescription = "";
				StringBuffer slaveValues = new StringBuffer();
				StringBuffer slaveDisplay = new StringBuffer();
				if (slaveAll.equals("Y"))
				{
				  slaveValues.append("'*all'");
				  slaveDisplay.append("'*all'");
				}
	
				for (int x = 0; x < inData.size(); x++) {
					DropDownDual thisElement =
						(DropDownDual) inData.elementAt(x);
					if (!saveMasterValue.equals("")
						&& !thisElement.getMasterValue().equals(
							saveMasterValue)) {
						// Tie Slave Lists to Master Codes
						slaveInfoLists.append(" lists['");
						slaveInfoLists.append(saveMasterValue);
						slaveInfoLists.append("'] = new Array(); ");
						slaveInfoLists.append(" lists['");
						slaveInfoLists.append(saveMasterValue);
						slaveInfoLists.append("'][0]");
						slaveInfoLists.append(" = new Array( ");
						slaveInfoLists.append(slaveDisplay.toString());
						slaveInfoLists.append("); ");
						slaveInfoLists.append(" lists['");
						slaveInfoLists.append(saveMasterValue);
						slaveInfoLists.append("'][1]");
						slaveInfoLists.append(" = new Array(");
						slaveInfoLists.append(slaveValues.toString());
						slaveInfoLists.append("); ");
	
						selected = "";
	
						if (masterValue != null
							&& masterValue.equals(saveMasterValue))
							selected = "selected=\"selected\" ";
	
						if (!listMasterDisplay.toString().equals(""))
							listMasterDisplay.append(",");
	
						listMasterDisplay.append(" '");
						if (valueDescLoc.equals("B")) {
							listMasterDisplay.append(saveMasterValue);
							listMasterDisplay.append(" - ");
						}
						listMasterDisplay.append(saveMasterDescription);
						if (valueDescLoc.equals("E")) {
							listMasterDisplay.append(" - ");
							listMasterDisplay.append(saveMasterValue);
						}
						listMasterDisplay.append("' ");
	
						if (!listMasterValue.toString().equals(""))
							listMasterValue.append(",");
	
						listMasterValue.append(" '");
						listMasterValue.append(saveMasterValue);
						listMasterValue.append("' ");
	
						masterDropDown.append("<option value=\"");
						masterDropDown.append(saveMasterValue);
						masterDropDown.append("\" ");
						masterDropDown.append(selected);
						masterDropDown.append(">");
						if (valueDescLoc.equals("B")) {
							masterDropDown.append(saveMasterValue);
							masterDropDown.append(" - ");
						}
						masterDropDown.append(saveMasterDescription.trim());
						if (valueDescLoc.equals("E")) {
							masterDropDown.append(" - ");
							masterDropDown.append(saveMasterValue);
	
						}
	
						// RESET Slave Information
						slaveValues = new StringBuffer();
						slaveDisplay = new StringBuffer();
						if (slaveAll.equals("Y"))
						{
						  slaveValues.append("'*all'");
						  slaveDisplay.append("'*all'");
						}
						
					}
					saveMasterValue = thisElement.getMasterValue();
					saveMasterDescription = thisElement.getMasterDescription();
	
					// Build the list of Slave Codes and Descriptions to be used
					//   For EACH Master - Ties them Together
	
					if (!slaveDisplay.toString().equals(""))
						slaveDisplay.append(",");
					slaveDisplay.append("'");
					if (valueDescLoc.equals("B")) {
						slaveDisplay.append(thisElement.getSlaveValue());
						slaveDisplay.append(" - ");
					}
					slaveDisplay.append(thisElement.getSlaveDescription());
					if (valueDescLoc.equals("E")) {
						slaveDisplay.append(" - ");
						slaveDisplay.append(thisElement.getSlaveValue());
					}
					slaveDisplay.append("' ");
	
					if (!slaveValues.toString().equals(""))
						slaveValues.append(",");
					slaveValues.append("'");
					slaveValues.append(thisElement.getSlaveValue());
					slaveValues.append("' ");
				} // END of For Loop
	
				// Do the Final Master Setting
				if (!listMasterDisplay.toString().equals("")) {
					slaveInfoLists.append(" lists['");
					slaveInfoLists.append(saveMasterValue);
					slaveInfoLists.append("'] = new Array(); ");
					slaveInfoLists.append(" lists['");
					slaveInfoLists.append(saveMasterValue);
					slaveInfoLists.append("'][0]");
					slaveInfoLists.append(" = new Array( ");
					slaveInfoLists.append(slaveDisplay.toString());
					slaveInfoLists.append("); ");
					slaveInfoLists.append(" lists['");
					slaveInfoLists.append(saveMasterValue);
					slaveInfoLists.append("'][1]");
					slaveInfoLists.append(" = new Array(");
					slaveInfoLists.append(slaveValues.toString());
					slaveInfoLists.append("); ");
	
					selected = "";
	
					if (masterValue != null
						&& masterValue.equals(saveMasterValue))
						selected = "selected=\"selected\" ";
	
					if (!listMasterDisplay.toString().equals(""))
						listMasterDisplay.append(",");
	
					listMasterDisplay.append(" '");
					if (valueDescLoc.equals("B")) {
						listMasterDisplay.append(saveMasterValue);
						listMasterDisplay.append(" - ");
					}
					listMasterDisplay.append(saveMasterDescription);
					if (valueDescLoc.equals("E")) {
						listMasterDisplay.append(" - ");
						listMasterDisplay.append(saveMasterValue);
					}
					listMasterDisplay.append("' ");
	
					if (!listMasterValue.toString().equals(""))
						listMasterValue.append(",");
	
					listMasterValue.append(" '");
					listMasterValue.append(saveMasterValue);
					listMasterValue.append("' ");
	
					masterDropDown.append("<option value=\"");
					masterDropDown.append(saveMasterValue);
					masterDropDown.append("\" ");
					masterDropDown.append(selected);
					masterDropDown.append(">");
					if (valueDescLoc.equals("B")) {
						masterDropDown.append(saveMasterValue);
						masterDropDown.append(" - ");
					}
					masterDropDown.append(saveMasterDescription.trim());
					if (valueDescLoc.equals("E")) {
						masterDropDown.append(" - ");
						masterDropDown.append(saveMasterValue);
					}
					
					// Create the MASTER Display, and Master Value Section
					infoLists.append(" lists['*all'] = new Array(); ");
					infoLists.append(" lists['*all'][0]");
					infoLists.append(" = new Array( ");
					infoLists.append(listMasterDisplay.toString());
					infoLists.append("); ");
					infoLists.append(" lists['*all'][1]");
					infoLists.append(" = new Array( ");
					infoLists.append(listMasterValue.toString());
					infoLists.append("); ");
					infoLists.append(slaveInfoLists.toString());
				}
				infoLists.append("</script>");
				
				masterDropDown.append("</select>");
				// Add the section for the Body Tag.
				returnVector.addElement(masterDropDown.toString());
				returnVector.addElement(infoLists.toString());
	
			}
		} catch (Exception e) {
			System.out.println(
				"Error Could not Build Drop Down list: "
					+ "com.treetop.utilities."
					+ "DropDownDual : "
					+ e);
		}
		return returnVector;
	
	}
	/**
	 * Return a Vector: build from the vector sent in.
	 *     With the default Values.
	 * @param inData     : Vector of DropDownDual Classes
	 *        masterName : Name of the First Drop Down List
	 * 		  masterAll  : Y have an *all value - (Default)
	 *                     N Do not have an *all value
	 *        masterValue: Value to Begin on.
	 *        slaveName  : Name of the Second Drop Down List
	 *        slaveAll   : Y have an *all value - (Default)
	 *                     N Do not have an *all value
	 *        Codes for both valueDescLocMaster and valueDescLocSlave
	 *        			: B = Beginning with a Dash After (Default)
	 * 					 	E = Ending with a Dash Before
	 * 						N = None - Only display the description
	 * @return
	 */
	@Deprecated
	public static Vector buildDualDropDown(
		Vector inData,
		String masterName,
		String masterAll,
		String masterValue,
		String slaveName,
		String slaveAll,
		String valueDescLocMaster,
		String valueDescLocSlave) {
		Vector returnVector = new Vector();
		if (valueDescLocMaster == null
			|| (!valueDescLocMaster.equals("E") && !valueDescLocMaster.equals("N")))
			valueDescLocMaster = "B";
		if (valueDescLocSlave == null
			|| (!valueDescLocSlave.equals("E") && !valueDescLocSlave.equals("N")))
				valueDescLocSlave = "B";
		if (slaveAll == null ||
		    !slaveAll.equals("N"))
		    slaveAll = "Y";
		if (masterAll == null ||
			!masterAll.equals("N"))
			masterAll = "Y";
		try {
			if (inData.size() > 0) {
				StringBuffer listMasterDisplay = new StringBuffer();
			    listMasterDisplay.append("'*all'");
	
				StringBuffer listMasterValue = new StringBuffer();
				listMasterValue.append("'*all'");
	
				// Build a slaveInfoList section tied to EACH Master
				//   Will go into infoLists
				StringBuffer slaveInfoLists = new StringBuffer();
				// *all section
				slaveInfoLists.append(" lists['*all'] = new Array(); ");
				slaveInfoLists.append(" lists['*all'][0]");
				slaveInfoLists.append(" = new Array( '*all'); ");
				slaveInfoLists.append(" lists['*all'][1]");
				slaveInfoLists.append(" = new Array( '*all'); ");
	
				StringBuffer infoLists = new StringBuffer();
				infoLists.append("<script language=\"javascript\"> ");
				infoLists.append("var lists = new Array();");
	
				StringBuffer masterDropDown = new StringBuffer();
				masterDropDown.append("<select name=\"");
				masterDropDown.append(masterName);
				masterDropDown.append("\" size=1 ");
				masterDropDown.append("onchange=\"change");
				masterDropDown.append(masterName);
				masterDropDown.append("(this)\">");
				if (masterAll.equals("Y"))
					masterDropDown.append("<option value=\"*all\">*all");
	
				String selected = "selected=\"selected\" ";
	
				String saveMasterValue = "";
				String saveMasterDescription = "";
				StringBuffer slaveValues = new StringBuffer();
				StringBuffer slaveDisplay = new StringBuffer();
				if (slaveAll.equals("Y"))
				{
				  slaveValues.append("'*all'");
				  slaveDisplay.append("'*all'");
				}
	
				for (int x = 0; x < inData.size(); x++) {
					DropDownDual thisElement =
						(DropDownDual) inData.elementAt(x);
					if (!saveMasterValue.equals("")
						&& !thisElement.getMasterValue().equals(
							saveMasterValue)) {
						// Tie Slave Lists to Master Codes
						slaveInfoLists.append(" lists['");
						slaveInfoLists.append(saveMasterValue);
						slaveInfoLists.append("'] = new Array(); ");
						slaveInfoLists.append(" lists['");
						slaveInfoLists.append(saveMasterValue);
						slaveInfoLists.append("'][0]");
						slaveInfoLists.append(" = new Array( ");
						slaveInfoLists.append(slaveDisplay.toString());
						slaveInfoLists.append("); ");
						slaveInfoLists.append(" lists['");
						slaveInfoLists.append(saveMasterValue);
						slaveInfoLists.append("'][1]");
						slaveInfoLists.append(" = new Array(");
						slaveInfoLists.append(slaveValues.toString());
						slaveInfoLists.append("); ");
	
						selected = "";
	
						if (masterValue != null
							&& masterValue.equals(saveMasterValue))
							selected = "selected=\"selected\" ";
	
						if (!listMasterDisplay.toString().equals(""))
							listMasterDisplay.append(",");
	
						listMasterDisplay.append(" '");
						if (valueDescLocMaster.equals("B")) {
							listMasterDisplay.append(saveMasterValue);
							listMasterDisplay.append(" - ");
						}
						listMasterDisplay.append(saveMasterDescription);
						if (valueDescLocMaster.equals("E")) {
							listMasterDisplay.append(" - ");
							listMasterDisplay.append(saveMasterValue);
						}
						listMasterDisplay.append("' ");
	
						if (!listMasterValue.toString().equals(""))
							listMasterValue.append(",");
	
						listMasterValue.append(" '");
						listMasterValue.append(saveMasterValue);
						listMasterValue.append("' ");
	
						masterDropDown.append("<option value=\"");
						masterDropDown.append(saveMasterValue);
						masterDropDown.append("\" ");
						masterDropDown.append(selected);
						masterDropDown.append(">");
						if (valueDescLocMaster.equals("B")) {
							masterDropDown.append(saveMasterValue);
							masterDropDown.append(" - ");
						}
						masterDropDown.append(saveMasterDescription.trim());
						if (valueDescLocMaster.equals("E")) {
							masterDropDown.append(" - ");
							masterDropDown.append(saveMasterValue);
	
						}
	
						// RESET Slave Information
						slaveValues = new StringBuffer();
						slaveDisplay = new StringBuffer();
						if (slaveAll.equals("Y"))
						{
						  slaveValues.append("'*all'");
						  slaveDisplay.append("'*all'");
						}
						
					}
					saveMasterValue = thisElement.getMasterValue();
					saveMasterDescription = thisElement.getMasterDescription();
	
					// Build the list of Slave Codes and Descriptions to be used
					//   For EACH Master - Ties them Together
	
					if (!slaveDisplay.toString().equals(""))
						slaveDisplay.append(",");
					slaveDisplay.append("'");
					if (valueDescLocSlave.equals("B")) {
						slaveDisplay.append(thisElement.getSlaveValue());
						slaveDisplay.append(" - ");
					}
					slaveDisplay.append(thisElement.getSlaveDescription());
					if (valueDescLocSlave.equals("E")) {
						slaveDisplay.append(" - ");
						slaveDisplay.append(thisElement.getSlaveValue());
					}
					slaveDisplay.append("' ");
	
					if (!slaveValues.toString().equals(""))
						slaveValues.append(",");
					slaveValues.append("'");
					slaveValues.append(thisElement.getSlaveValue());
					slaveValues.append("' ");
				} // END of For Loop
	
				// Do the Final Master Setting
				if (!listMasterDisplay.toString().equals("")) {
					slaveInfoLists.append(" lists['");
					slaveInfoLists.append(saveMasterValue);
					slaveInfoLists.append("'] = new Array(); ");
					slaveInfoLists.append(" lists['");
					slaveInfoLists.append(saveMasterValue);
					slaveInfoLists.append("'][0]");
					slaveInfoLists.append(" = new Array( ");
					slaveInfoLists.append(slaveDisplay.toString());
					slaveInfoLists.append("); ");
					slaveInfoLists.append(" lists['");
					slaveInfoLists.append(saveMasterValue);
					slaveInfoLists.append("'][1]");
					slaveInfoLists.append(" = new Array(");
					slaveInfoLists.append(slaveValues.toString());
					slaveInfoLists.append("); ");
	
					selected = "";
	
					if (masterValue != null
						&& masterValue.equals(saveMasterValue))
						selected = "selected=\"selected\" ";
	
					if (!listMasterDisplay.toString().equals(""))
						listMasterDisplay.append(",");
	
					listMasterDisplay.append(" '");
					if (valueDescLocMaster.equals("B")) {
						listMasterDisplay.append(saveMasterValue);
						listMasterDisplay.append(" - ");
					}
					listMasterDisplay.append(saveMasterDescription);
					if (valueDescLocMaster.equals("E")) {
						listMasterDisplay.append(" - ");
						listMasterDisplay.append(saveMasterValue);
					}
					listMasterDisplay.append("' ");
	
					if (!listMasterValue.toString().equals(""))
						listMasterValue.append(",");
	
					listMasterValue.append(" '");
					listMasterValue.append(saveMasterValue);
					listMasterValue.append("' ");
	
					masterDropDown.append("<option value=\"");
					masterDropDown.append(saveMasterValue);
					masterDropDown.append("\" ");
					masterDropDown.append(selected);
					masterDropDown.append(">");
					if (valueDescLocMaster.equals("B")) {
						masterDropDown.append(saveMasterValue);
						masterDropDown.append(" - ");
					}
					masterDropDown.append(saveMasterDescription.trim());
					if (valueDescLocMaster.equals("E")) {
						masterDropDown.append(" - ");
						masterDropDown.append(saveMasterValue);
					}
					
					// Create the MASTER Display, and Master Value Section
					infoLists.append(" lists['*all'] = new Array(); ");
					infoLists.append(" lists['*all'][0]");
					infoLists.append(" = new Array( ");
					infoLists.append(listMasterDisplay.toString());
					infoLists.append("); ");
					infoLists.append(" lists['*all'][1]");
					infoLists.append(" = new Array( ");
					infoLists.append(listMasterValue.toString());
					infoLists.append("); ");
					infoLists.append(slaveInfoLists.toString());
				}
				infoLists.append("</script>");
				
				masterDropDown.append("</select>");
				// Add the section for the Body Tag.
				returnVector.addElement(masterDropDown.toString());
				returnVector.addElement(infoLists.toString());
	
			}
		} catch (Exception e) {
			System.out.println(
				"Error Could not Build Drop Down list: "
					+ "com.treetop.utilities."
					+ "DropDownDual : "
					+ e);
		}
		return returnVector;
	
	}
	/**
	 * Return a Vector: build from the vector sent in.
	 *     With the default Values.
	 * @param inData     : Vector of DropDownDual Classes
	 *        masterName : Name of the First Drop Down List
	 * 		  masterAll  : Y have an *all value - (Default)
	 *                     N Do not have an *all value
	 *        masterValue: Value to Begin on.
	 *        slaveName  : Name of the Second Drop Down List
	 *        slaveAll   : Y have an *all value - (Default)
	 *                     N Do not have an *all value
	 * 		 slaveValue  : Value to add to beginning.
	 *        Codes for both valueDescLocMaster and valueDescLocSlave
	 *        			: B = Beginning with a Dash After (Default)
	 * 					 	E = Ending with a Dash Before
	 * 						N = None - Only display the description
	 * @return
	 */
	@Deprecated
	public static Vector buildDualDropDownNew(
		Vector inData,
		String masterName,
		String masterAll,
		String masterValue,
		String slaveName,
		String slaveAll,
		String slaveValue,
		String valueDescLocMaster,
		String valueDescLocSlave) {
		Vector returnVector = new Vector();
		if (valueDescLocMaster == null
			|| (!valueDescLocMaster.equals("E") && !valueDescLocMaster.equals("N")))
			valueDescLocMaster = "B";
		if (valueDescLocSlave == null
			|| (!valueDescLocSlave.equals("E") && !valueDescLocSlave.equals("N")))
				valueDescLocSlave = "B";
		if (slaveAll == null ||
		    !slaveAll.equals("N"))
		    slaveAll = "Y";
		if (masterAll == null ||
			!masterAll.equals("N"))
			masterAll = "Y";
		try {
			if (inData.size() > 0) {
				StringBuffer listMasterDisplay = new StringBuffer();
			    listMasterDisplay.append("'*all'");
	
				StringBuffer listMasterValue = new StringBuffer();
				listMasterValue.append("'*all'");
	
				// Build a slaveInfoList section tied to EACH Master
				//   Will go into infoLists
				StringBuffer slaveInfoLists = new StringBuffer();
				// *all section
				slaveInfoLists.append(" lists" + masterName + "['*all'] = new Array(); ");
				slaveInfoLists.append(" lists" + masterName + "['*all'][0]");
				slaveInfoLists.append(" = new Array( '*all'); ");
				slaveInfoLists.append(" lists" + masterName + "['*all'][1]");
				slaveInfoLists.append(" = new Array( '*all'); ");
	
				StringBuffer infoLists = new StringBuffer();
				infoLists.append("<script language=\"javascript\"> ");
				infoLists.append("var lists" + masterName + "= new Array();");
	
				StringBuffer masterDropDown = new StringBuffer();
				masterDropDown.append("<select name=\"");
				masterDropDown.append(masterName);
				masterDropDown.append("\" size=\"1\" ");
				masterDropDown.append("onchange=\"change");
				masterDropDown.append(masterName);
				masterDropDown.append("(this)\">");
				if (masterAll.equals("Y"))
					masterDropDown.append("<option value=\"*all\">*all");
	
				String selected = "selected=\"selected\" ";
	
				String saveMasterValue = "";
				String saveMasterDescription = "";
				StringBuffer slaveValues = new StringBuffer();
				StringBuffer slaveDisplay = new StringBuffer();
				if (slaveAll.equals("Y"))
				{
				  slaveValues.append("'*all'");
				  slaveDisplay.append("'*all'");
				}
				for (int x = 0; x < inData.size(); x++) {
					DropDownDual thisElement = (DropDownDual) inData.elementAt(x);
					if (slaveValue.equals(thisElement.getSlaveValue()))
					{
						StringBuffer sv = new StringBuffer();
						StringBuffer sd = new StringBuffer();
						sv.append("'" + thisElement.getSlaveValue() + "'");
						sd.append("'");
						if (valueDescLocSlave.equals("B")) {
							sd.append(thisElement.getSlaveValue());
							sd.append(" - ");
						}
						sd.append(thisElement.getSlaveDescription());
						if (valueDescLocSlave.equals("E")) {
							sd.append(" - ");
							sd.append(thisElement.getSlaveValue());
						}
						sd.append("' ");
						if (!slaveValues.toString().equals(""))
						{
							sv.append(", ");
							sd.append(", ");
						}
						sv.append(slaveValues.toString());
						sd.append(slaveDisplay.toString());
						slaveValues = new StringBuffer();
						slaveDisplay = new StringBuffer();
						slaveValues = sv;
						slaveDisplay = sd;
					}
					if (!saveMasterValue.equals("")
						&& !thisElement.getMasterValue().equals(
							saveMasterValue)) {
						// Tie Slave Lists to Master Codes
						slaveInfoLists.append(" lists" + masterName + "['");
						slaveInfoLists.append(saveMasterValue);
						slaveInfoLists.append("'] = new Array(); ");
						slaveInfoLists.append(" lists" + masterName + "['");
						slaveInfoLists.append(saveMasterValue);
						slaveInfoLists.append("'][0]");
						slaveInfoLists.append(" = new Array( ");
						slaveInfoLists.append(slaveDisplay.toString());
						slaveInfoLists.append("); ");
						slaveInfoLists.append(" lists" + masterName + "['");
						slaveInfoLists.append(saveMasterValue);
						slaveInfoLists.append("'][1]");
						slaveInfoLists.append(" = new Array(");
						slaveInfoLists.append(slaveValues.toString());
						slaveInfoLists.append("); ");
	
						selected = "";
	
						if (masterValue != null
							&& masterValue.equals(saveMasterValue))
							selected = "selected=\"selected\" ";
	
						if (!listMasterDisplay.toString().equals(""))
							listMasterDisplay.append(",");
	
						listMasterDisplay.append(" '");
						if (valueDescLocMaster.equals("B")) {
							listMasterDisplay.append(saveMasterValue);
							listMasterDisplay.append(" - ");
						}
						listMasterDisplay.append(saveMasterDescription);
						if (valueDescLocMaster.equals("E")) {
							listMasterDisplay.append(" - ");
							listMasterDisplay.append(saveMasterValue);
						}
						listMasterDisplay.append("' ");
	
						if (!listMasterValue.toString().equals(""))
							listMasterValue.append(",");
	
						listMasterValue.append(" '");
						listMasterValue.append(saveMasterValue);
						listMasterValue.append("' ");
	
						masterDropDown.append("<option value=\"");
						masterDropDown.append(saveMasterValue);
						masterDropDown.append("\" ");
						masterDropDown.append(selected);
						masterDropDown.append(">");
						if (valueDescLocMaster.equals("B")) {
							masterDropDown.append(saveMasterValue);
							masterDropDown.append(" - ");
						}
						masterDropDown.append(saveMasterDescription.trim());
						if (valueDescLocMaster.equals("E")) {
							masterDropDown.append(" - ");
							masterDropDown.append(saveMasterValue);
	
						}
	
						// RESET Slave Information
						slaveValues = new StringBuffer();
						slaveDisplay = new StringBuffer();

						if (slaveAll.equals("Y"))
						{
						  slaveValues.append("'*all'");
						  slaveDisplay.append("'*all'");
						}
						
					}
					saveMasterValue = thisElement.getMasterValue();
					saveMasterDescription = thisElement.getMasterDescription();
	
					// Build the list of Slave Codes and Descriptions to be used
					//   For EACH Master - Ties them Together
	
					if (!slaveDisplay.toString().equals(""))
						slaveDisplay.append(",");
					slaveDisplay.append("'");
					if (valueDescLocSlave.equals("B")) {
						slaveDisplay.append(thisElement.getSlaveValue());
						slaveDisplay.append(" - ");
					}
					slaveDisplay.append(thisElement.getSlaveDescription());
					if (valueDescLocSlave.equals("E")) {
						slaveDisplay.append(" - ");
						slaveDisplay.append(thisElement.getSlaveValue());
					}
					slaveDisplay.append("' ");
	
					if (!slaveValues.toString().equals(""))
						slaveValues.append(",");
					slaveValues.append("'");
					slaveValues.append(thisElement.getSlaveValue());
					slaveValues.append("' ");
				} // END of For Loop
	
				// Do the Final Master Setting
				if (!listMasterDisplay.toString().equals("")) {
					slaveInfoLists.append(" lists" + masterName + "['");
					slaveInfoLists.append(saveMasterValue);
					slaveInfoLists.append("'] = new Array(); ");
					slaveInfoLists.append(" lists" + masterName + "['");
					slaveInfoLists.append(saveMasterValue);
					slaveInfoLists.append("'][0]");
					slaveInfoLists.append(" = new Array( ");
					slaveInfoLists.append(slaveDisplay.toString());
					slaveInfoLists.append("); ");
					slaveInfoLists.append(" lists" + masterName + "['");
					slaveInfoLists.append(saveMasterValue);
					slaveInfoLists.append("'][1]");
					slaveInfoLists.append(" = new Array(");
					slaveInfoLists.append(slaveValues.toString());
					slaveInfoLists.append("); ");
	
					selected = "";
	
					if (masterValue != null
						&& masterValue.equals(saveMasterValue))
						selected = "selected=\"selected\" ";
	
					if (!listMasterDisplay.toString().equals(""))
						listMasterDisplay.append(",");
	
					listMasterDisplay.append(" '");
					if (valueDescLocMaster.equals("B")) {
						listMasterDisplay.append(saveMasterValue);
						listMasterDisplay.append(" - ");
					}
					listMasterDisplay.append(saveMasterDescription);
					if (valueDescLocMaster.equals("E")) {
						listMasterDisplay.append(" - ");
						listMasterDisplay.append(saveMasterValue);
					}
					listMasterDisplay.append("' ");
	
					if (!listMasterValue.toString().equals(""))
						listMasterValue.append(",");
	
					listMasterValue.append(" '");
					listMasterValue.append(saveMasterValue);
					listMasterValue.append("' ");
	
					masterDropDown.append("<option value=\"");
					masterDropDown.append(saveMasterValue);
					masterDropDown.append("\" ");
					masterDropDown.append(selected);
					masterDropDown.append(">");
					if (valueDescLocMaster.equals("B")) {
						masterDropDown.append(saveMasterValue);
						masterDropDown.append(" - ");
					}
					masterDropDown.append(saveMasterDescription.trim());
					if (valueDescLocMaster.equals("E")) {
						masterDropDown.append(" - ");
						masterDropDown.append(saveMasterValue);
					}
					
					// Create the MASTER Display, and Master Value Section
					infoLists.append(" lists" + masterName + "['*all'] = new Array(); ");
					infoLists.append(" lists" + masterName + "['*all'][0]");
					infoLists.append(" = new Array( ");
					infoLists.append(listMasterDisplay.toString());
					infoLists.append("); ");
					infoLists.append(" lists" + masterName + "['*all'][1]");
					infoLists.append(" = new Array( ");
					infoLists.append(listMasterValue.toString());
					infoLists.append("); ");
					infoLists.append(slaveInfoLists.toString());
				}
				infoLists.append("</script>");
				
				masterDropDown.append("</select>");
				// Add the section for the Body Tag.
				returnVector.addElement(masterDropDown.toString());
				returnVector.addElement(infoLists.toString());
	
			}
		} catch (Exception e) {
			System.out.println(
				"Error Could not Build Drop Down list: "
					+ "com.treetop.utilities."
					+ "DropDownDual : "
					+ e);
		}
		return returnVector;
	
	}
	
	public static String buildMaster(
			Vector<DropDownDual> values, 
			String name, 
			String id, 
			String cssClass, 
			String selectedValue, 
			String defaultValue, 
			boolean readOnly, 
			DescriptionType descriptionType) {
		
		Vector<String> masterValues = new Vector<String>();
		Vector<HtmlOption> masterOptions = new Vector<HtmlOption>();
		
		for (DropDownDual value : values) {
			
			if (masterValues.contains(value.getMasterValue())) {
				//already added, do nothing
			} else {
				//add it
				masterValues.addElement(value.getMasterValue());
				
				HtmlOption option = new HtmlOption();
				option.setValue(value.getMasterValue());
				option.setDescription(value.getMasterDescription());
				option.setDescriptionLong(value.getMasterDescription());
				masterOptions.addElement(option);
			}
		}

		HtmlSelect select = new HtmlSelect(name, id, cssClass, selectedValue, defaultValue, readOnly, descriptionType);
		select.setOptions(masterOptions);
		
		return select.toString();
	}
	
	public static String buildSlave(
			Vector<DropDownDual> values, 
			String masterId,
			String name, 
			String id, 
			String cssClass, 
			String selectedValue, 
			String defaultValue, 
			boolean readOnly, 
			DescriptionType descriptionType) {
		
		Vector<HtmlOption> slaveOptions = new Vector<HtmlOption>();
		
		for (DropDownDual value : values) {
				
				HtmlOption option = new HtmlOption();
				
				//set the associated master value as the cssClass value (works with jQuery plugin)
				option.setCssClass(value.getMasterValue());
				
				option.setValue(value.getSlaveValue());
				option.setDescription(value.getSlaveDescription());
				option.setDescriptionLong(value.getSlaveDescription());
				slaveOptions.addElement(option);
			
		}

		HtmlSelect select = new HtmlSelect(name, id, cssClass, selectedValue, defaultValue, readOnly, descriptionType);
		select.setOptions(slaveOptions);
		
		StringBuffer html = new StringBuffer();
		html.append(select.toString() + "\r");
		html.append("<script type='text/javascript'>$('#" + id + "').chainedTo('#" + masterId + "');</script>" + "\r");
		
		
		return html.toString();
	}

}
