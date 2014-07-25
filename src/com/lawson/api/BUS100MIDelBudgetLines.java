package com.lawson.api;

public class BUS100MIDelBudgetLines {
	
	private String		response		= "";
	
	private String		authorization	= "";
	
	private String		userProfile		= "";
	private String		sentFromProgram = "";
	
	private String		environment 	= "TST";
	private String		company 		= "100";
	private String		division 		= "100";
	
	private String		budgetNumber 	= "";
	private String		budgetVersion 	= "";
	private String		budgetPeriod 	= "";
	
	private String		dimension1 		= "";
	private String		dimension2 		= "";
	private String		dimension3 		= "";
	private String		dimension4 		= "";
	private String		dimension5 		= "";
	private String		dimension6 		= "";
	private String		dimension7 		= "";
	
	private String		currency 		= "USD";
	
	@Override
	public String toString() {

		StringBuffer rec = new StringBuffer(1024);
		rec.setLength(1024); 	
		for (int x = 0; x < 97; x++) {
			rec.insert(0, " ");
		}

		rec.insert(0, "DelBudgetLines");
		rec.insert(15, this.getCompany());
		rec.insert(18, this.getDivision());
		rec.insert(21, this.getBudgetNumber());
		rec.insert(24, this.getBudgetVersion());
		rec.insert(28, "");
		rec.insert(34, this.getDimension1());
		rec.insert(42, this.getDimension2());
		rec.insert(50, this.getDimension3());
		rec.insert(58, this.getDimension4());
		rec.insert(66, this.getDimension5());
		rec.insert(74, this.getDimension6());
		rec.insert(82, this.getDimension7());
		rec.insert(90, this.getCurrency());
		rec.insert(93, "");		//curve

		

		rec.setLength(97); // Adjust the length
			
		return rec.toString();

	}
	
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
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
	public String getDimension1() {
		return dimension1;
	}
	public void setDimension1(String dimension1) {
		this.dimension1 = dimension1;
	}
	public String getDimension2() {
		return dimension2;
	}
	public void setDimension2(String dimension2) {
		this.dimension2 = dimension2;
	}
	public String getDimension3() {
		return dimension3;
	}
	public void setDimension3(String dimension3) {
		this.dimension3 = dimension3;
	}
	public String getDimension4() {
		return dimension4;
	}
	public void setDimension4(String dimension4) {
		this.dimension4 = dimension4;
	}
	public String getDimension5() {
		return dimension5;
	}
	public void setDimension5(String dimension5) {
		this.dimension5 = dimension5;
	}
	public String getDimension6() {
		return dimension6;
	}
	public void setDimension6(String dimension6) {
		this.dimension6 = dimension6;
	}
	public String getDimension7() {
		return dimension7;
	}
	public void setDimension7(String dimension7) {
		this.dimension7 = dimension7;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	public String getSentFromProgram() {
		return sentFromProgram;
	}
	public void setSentFromProgram(String sentFromProgram) {
		this.sentFromProgram = sentFromProgram;
	}
	public String getBudgetPeriod() {
		return budgetPeriod;
	}
	public void setBudgetPeriod(String budgetPeriod) {
		this.budgetPeriod = budgetPeriod;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
}
