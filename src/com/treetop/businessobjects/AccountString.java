package com.treetop.businessobjects;

public class AccountString {
	
	private AccountID dimension1 = new AccountID();
	private AccountID dimension2 = new AccountID();
	private AccountID dimension3 = new AccountID();
	private AccountID dimension4 = new AccountID();
	private AccountID dimension5 = new AccountID();
	private AccountID dimension6 = new AccountID();
	private AccountID dimension7 = new AccountID();
	
	private boolean valid 				= false;
	private String error 				= "";
	private String budgetDepartment 	= "";
	private String budgetResponsible 	= "";
	private String description			= "";
	private String comment				= "";
	
	@Override
	public String toString() {
		StringBuffer string = new StringBuffer();
		
		string.append(this.getDimension1().getAccountID() + " - ");
		string.append(this.getDimension2().getAccountID() + " - ");
		string.append(this.getDimension3().getAccountID() + " - ");
		string.append(this.getDimension4().getAccountID() + " - ");
		string.append(this.getDimension5().getAccountID() + " - ");
		string.append(this.getDimension6().getAccountID() + " - ");
		string.append(this.getDimension7().getAccountID());
		
		
		return string.toString();
	}
	
	public String getError() {
		return error;
	}



	public void setError(String error) {
		this.error = error;
	}



	public boolean isValid() {
		return valid;
	}



	public void setValid(boolean valid) {
		this.valid = valid;
	}



	public AccountString(){
		super();
	}



	public AccountID getDimension1() {
		return dimension1;
	}



	public void setDimension1(AccountID dimension1) {
		this.dimension1 = dimension1;
	}



	public AccountID getDimension2() {
		return dimension2;
	}



	public void setDimension2(AccountID dimension2) {
		this.dimension2 = dimension2;
	}



	public AccountID getDimension3() {
		return dimension3;
	}



	public void setDimension3(AccountID dimension3) {
		this.dimension3 = dimension3;
	}



	public AccountID getDimension4() {
		return dimension4;
	}



	public void setDimension4(AccountID dimension4) {
		this.dimension4 = dimension4;
	}



	public AccountID getDimension5() {
		return dimension5;
	}



	public void setDimension5(AccountID dimension5) {
		this.dimension5 = dimension5;
	}



	public AccountID getDimension6() {
		return dimension6;
	}



	public void setDimension6(AccountID dimension6) {
		this.dimension6 = dimension6;
	}



	public AccountID getDimension7() {
		return dimension7;
	}



	public void setDimension7(AccountID dimension7) {
		this.dimension7 = dimension7;
	}

	public String getBudgetDepartment() {
		return budgetDepartment;
	}

	public void setBudgetDepartment(String budgetDepartment) {
		this.budgetDepartment = budgetDepartment;
	}

	public String getBudgetResponsible() {
		return budgetResponsible;
	}

	public void setBudgetResponsible(String budgetResponsible) {
		this.budgetResponsible = budgetResponsible;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
