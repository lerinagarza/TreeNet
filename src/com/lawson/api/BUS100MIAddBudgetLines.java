package com.lawson.api;

public class BUS100MIAddBudgetLines {
	
	private String		response		= "";
	
	private String		authorization	= "";
	
	private String		userProfile		= "";
	private String		sentFromProgram = "";
	
	private String		environment 	= "TST";
	private String		company 		= "100";
	private String		division 		= "100";
	
	private String		budgetNumber 	= "";
	private String		budgetVersion 	= "";
	
	private String		dimension1 		= "";
	private String		dimension2 		= "";
	private String		dimension3 		= "";
	private String		dimension4 		= "";
	private String		dimension5 		= "";
	private String		dimension6 		= "";
	private String		dimension7 		= "";
	
	private String		currency 		= "USD";
	
	private String		amount1 		= "";
	private String		amount2 		= "";
	private String		amount3 		= "";
	private String		amount4 		= "";
	private String		amount5 		= "";
	private String		amount6 		= "";
	private String		amount7 		= "";
	private String		amount8 		= "";
	private String		amount9 		= "";
	private String		amount10 		= "";
	private String		amount11 		= "";
	private String		amount12 		= "";
	
	@Override
	public String toString() {

		StringBuffer rec = new StringBuffer(1024);
		rec.setLength(1024); 	
		for (int x = 0; x < 322; x++) {
			rec.insert(0, " ");
		}

		rec.insert(0, "AddBudgetLines");
		rec.insert(15, this.getCompany());
		rec.insert(18, this.getDivision());
		rec.insert(21, this.getBudgetNumber());
		rec.insert(24, this.getBudgetVersion());
		rec.insert(28, this.getDimension1());
		rec.insert(36, this.getDimension2());
		rec.insert(44, this.getDimension3());
		rec.insert(52, this.getDimension4());
		rec.insert(60, this.getDimension5());
		rec.insert(68, this.getDimension6());
		rec.insert(76, this.getDimension7());
		rec.insert(84, this.getCurrency());
		rec.insert(87, "");		//cuve
		rec.insert(90, "");		//total foreign currency amount - budget
		rec.insert(107, "");		//total budget quantity
		rec.insert(118, this.getAmount1());
		rec.insert(135, this.getAmount2());
		rec.insert(152, this.getAmount3());
		rec.insert(169, this.getAmount4());
		rec.insert(186, this.getAmount5());
		rec.insert(203, this.getAmount6());
		rec.insert(220, this.getAmount7());
		rec.insert(237, this.getAmount8());
		rec.insert(254, this.getAmount9());
		rec.insert(271, this.getAmount10());
		rec.insert(288, this.getAmount11());
		rec.insert(305, this.getAmount12());
		

		

		rec.setLength(322); // Adjust the length
			
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
	public String getAmount1() {
		return amount1;
	}
	public void setAmount1(String amount1) {
		this.amount1 = amount1;
	}
	public String getAmount2() {
		return amount2;
	}
	public void setAmount2(String amount2) {
		this.amount2 = amount2;
	}
	public String getAmount3() {
		return amount3;
	}
	public void setAmount3(String amount3) {
		this.amount3 = amount3;
	}
	public String getAmount4() {
		return amount4;
	}
	public void setAmount4(String amount4) {
		this.amount4 = amount4;
	}
	public String getAmount5() {
		return amount5;
	}
	public void setAmount5(String amount5) {
		this.amount5 = amount5;
	}
	public String getAmount6() {
		return amount6;
	}
	public void setAmount6(String amount6) {
		this.amount6 = amount6;
	}
	public String getAmount7() {
		return amount7;
	}
	public void setAmount7(String amount7) {
		this.amount7 = amount7;
	}
	public String getAmount8() {
		return amount8;
	}
	public void setAmount8(String amount8) {
		this.amount8 = amount8;
	}
	public String getAmount9() {
		return amount9;
	}
	public void setAmount9(String amount9) {
		this.amount9 = amount9;
	}
	public String getAmount10() {
		return amount10;
	}
	public void setAmount10(String amount10) {
		this.amount10 = amount10;
	}
	public String getAmount11() {
		return amount11;
	}
	public void setAmount11(String amount11) {
		this.amount11 = amount11;
	}
	public String getAmount12() {
		return amount12;
	}
	public void setAmount12(String amount12) {
		this.amount12 = amount12;
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
