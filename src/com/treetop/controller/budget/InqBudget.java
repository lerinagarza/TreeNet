package com.treetop.controller.budget;

import java.util.Vector;

import com.treetop.businessobjectapplications.BeanBudget;
import com.treetop.controller.BaseViewBeanR3;
import com.treetop.services.ServiceBudget;
import com.treetop.utilities.html.DropDownDual;
import com.treetop.utilities.html.HtmlOption;

public class InqBudget extends BaseViewBeanR3 {
	
	private String budgetNumber		= "";
	private String budgetVersion	= "";
	private String department		= "";
	private String authorization	= "";
	private String filePath			= "";
	private String submit			= "";
	private String preview			= "";
	private String forecastMiss		= "";
	
	private BeanBudget bean			= new BeanBudget();
		
	public Vector<DropDownDual> buildDropDownBudget() {
		
		Vector<DropDownDual> items = new Vector<DropDownDual>();
		
		try {
			
			items = ServiceBudget.getDropDownBudget(this);
			
		} catch (Exception e) {
			
		}
		
		return items;
	}
	
	public Vector<HtmlOption> buildDropDownDepartments() {
		
		Vector<HtmlOption> items = new Vector<HtmlOption>();
		
		try {
			
			items = ServiceBudget.getDropDownDeparments(this);
			
		} catch (Exception e) {
			
		}
		
		return items;
	}
	

	@Override
	public void validate() {
		
		StringBuffer errorMessage = new StringBuffer();
		if (this.getEnvironment().trim().equals("")) {
			this.setEnvironment("PRD");
		}
		
		if (this.getDepartment().equals("")) {
			errorMessage.append("Budget department cannot be left blank.<br>");
		}
		
		if (this.getRequestType().equals("updBudget") || this.getRequestType().equals("updForecastMiss")) {
		
			
			
			
			if (this.getBudgetNumber().equals("")) {
				errorMessage.append("Budget number cannot be left blank.<br>");
			}
			
			if (this.getBudgetVersion().equals("")) {
				errorMessage.append("Budget version cannot be left blank.<br>");
			}
			
			
			
		}
		
		
		
		if (this.getRequestType().equals("updBudget")) {
			
			if (this.getFilePath().equals("")) {
				errorMessage.append("A file must be chosen to upload.<br>");
			}
			
		}
		
		if (this.getRequestType().equals("updForecastMiss")) {
			
			if (this.getForecastMiss().equals("")) {
				errorMessage.append("Period to Adjust cannot be left blank.<br>");
			}
			
		}
		
		if (!errorMessage.toString().equals("")) {
			this.setErrorMessage(errorMessage.toString().trim());
		}
		
	}


	public String getBudgetNumber() {
		return budgetNumber;
	}


	public void setBudgetNumber(String budgetNumber) {
		this.budgetNumber = budgetNumber;
	}


	public String getBudgetVersion() {
		return budgetVersion;
	}


	public void setBudgetVersion(String budgetVersion) {
		this.budgetVersion = budgetVersion;
	}




	public String getAuthorization() {
		return authorization;
	}


	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}


	public String getSubmit() {
		return submit;
	}


	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public BeanBudget getBean() {
		return bean;
	}

	public void setBean(BeanBudget bean) {
		this.bean = bean;
	}

	public String getForecastMiss() {
		return forecastMiss;
	}

	public void setForecastMiss(String forecastMiss) {
		this.forecastMiss = forecastMiss;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}
	

}
