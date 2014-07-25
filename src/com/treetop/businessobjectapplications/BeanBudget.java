package com.treetop.businessobjectapplications;

import java.util.Vector;

import com.lawson.api.BUS100MIAddBudgetLines;
import com.lawson.api.BUS100MIDelBudgetLines;
import com.treetop.businessobjects.AccountString;

public class BeanBudget {
	
	private Vector<BUS100MIAddBudgetLines> 	addBudgetLines	 = new Vector<BUS100MIAddBudgetLines>();
	private Vector<BUS100MIDelBudgetLines> 	delBudgetLines	 = new Vector<BUS100MIDelBudgetLines>();
		
	private Vector<String>					apiResponses = new Vector<String>();
	private Vector<AccountString>			invalidAccountStrings = new Vector<AccountString>();
	
	private Vector<AccountString>			departmentDefinitions = new Vector<AccountString>();
	
	

	public Vector<String> getApiResponses() {
		return apiResponses;
	}
	public void setApiResponses(Vector<String> apiResponses) {
		this.apiResponses = apiResponses;
	}
	public Vector<AccountString> getInvalidAccountStrings() {
		return invalidAccountStrings;
	}
	public void setInvalidAccountStrings(Vector<AccountString> invalidAccountStrings) {
		this.invalidAccountStrings = invalidAccountStrings;
	}
	public Vector<BUS100MIAddBudgetLines> getAddBudgetLines() {
		return addBudgetLines;
	}
	public void setAddBudgetLines(Vector<BUS100MIAddBudgetLines> addBudgetLines) {
		this.addBudgetLines = addBudgetLines;
	}
	public Vector<BUS100MIDelBudgetLines> getDelBudgetLines() {
		return delBudgetLines;
	}
	public void setDelBudgetLines(Vector<BUS100MIDelBudgetLines> delBudgetLines) {
		this.delBudgetLines = delBudgetLines;
	}
	public Vector<AccountString> getDepartmentDefinitions() {
		return departmentDefinitions;
	}
	public void setDepartmentDefinitions(Vector<AccountString> departmentDefinitions) {
		this.departmentDefinitions = departmentDefinitions;
	}
	
	

}
