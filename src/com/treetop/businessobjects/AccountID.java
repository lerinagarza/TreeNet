package com.treetop.businessobjects;

import java.sql.Connection;

public class AccountID {
	
	protected String dimension = "";
	
	protected String accountID = "";
	protected String accountDescriptionShort = "";
	protected String accountDescriptionLong = "";
	
	protected String accountGroup = "";
	protected String accountGroupDescriptionShort = "";
	protected String accountGroupDescriptionLong = "";
	
	protected String crossReferenceGLDim2 = "";
	protected String crossReferenceGLDim3 = "";
	protected String crossReferenceGLDim4 = "";
	protected String crossReferenceGLDim5 = "";
	protected String crossReferenceGLDim6 = "";
	protected String crossReferenceGLDim7 = "";
	
	protected String crossReferenceBudDim2 = "";
	protected String crossReferenceBudDim3 = "";
	protected String crossReferenceBudDim4 = "";
	protected String crossReferenceBudDim5 = "";
	protected String crossReferenceBudDim6 = "";
	protected String crossReferenceBudDim7 = "";
	
	protected boolean valid = false;
	protected String error = "";
	
	
	
	
	public AccountID(){
		
	}

	public AccountID(String dimension, String accountID){
		this.setDimension(dimension);
		this.setAccountID(accountID);
	}
	
	public AccountID(String dimension, String accountId, String accountGroup) {
		this.setAccountID(accountId);
		this.setAccountGroup(accountGroup);
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


	
	

	public String getAccountDescriptionLong() {
		return accountDescriptionLong;
	}

	public void setAccountDescriptionLong(String accountDescriptionLong) {
		this.accountDescriptionLong = accountDescriptionLong;
	}

	public String getAccountDescriptionShort() {
		return accountDescriptionShort;
	}

	public void setAccountDescriptionShort(String accountDescriptionShort) {
		this.accountDescriptionShort = accountDescriptionShort;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getCrossReferenceBudDim2() {
		return crossReferenceBudDim2;
	}

	public void setCrossReferenceBudDim2(String crossReferenceBudDim2) {
		this.crossReferenceBudDim2 = crossReferenceBudDim2;
	}

	public String getCrossReferenceBudDim3() {
		return crossReferenceBudDim3;
	}

	public void setCrossReferenceBudDim3(String crossReferenceBudDim3) {
		this.crossReferenceBudDim3 = crossReferenceBudDim3;
	}

	public String getCrossReferenceBudDim4() {
		return crossReferenceBudDim4;
	}

	public void setCrossReferenceBudDim4(String crossReferenceBudDim4) {
		this.crossReferenceBudDim4 = crossReferenceBudDim4;
	}

	public String getCrossReferenceBudDim5() {
		return crossReferenceBudDim5;
	}

	public void setCrossReferenceBudDim5(String crossReferenceBudDim5) {
		this.crossReferenceBudDim5 = crossReferenceBudDim5;
	}

	public String getCrossReferenceBudDim6() {
		return crossReferenceBudDim6;
	}

	public void setCrossReferenceBudDim6(String crossReferenceBudDim6) {
		this.crossReferenceBudDim6 = crossReferenceBudDim6;
	}

	public String getCrossReferenceBudDim7() {
		return crossReferenceBudDim7;
	}

	public void setCrossReferenceBudDim7(String crossReferenceBudDim7) {
		this.crossReferenceBudDim7 = crossReferenceBudDim7;
	}

	public String getCrossReferenceGLDim2() {
		return crossReferenceGLDim2;
	}

	public void setCrossReferenceGLDim2(String crossReferenceGLDim2) {
		this.crossReferenceGLDim2 = crossReferenceGLDim2;
	}

	public String getCrossReferenceGLDim3() {
		return crossReferenceGLDim3;
	}

	public void setCrossReferenceGLDim3(String crossReferenceGLDim3) {
		this.crossReferenceGLDim3 = crossReferenceGLDim3;
	}

	public String getCrossReferenceGLDim4() {
		return crossReferenceGLDim4;
	}

	public void setCrossReferenceGLDim4(String crossReferenceGLDim4) {
		this.crossReferenceGLDim4 = crossReferenceGLDim4;
	}

	public String getCrossReferenceGLDim5() {
		return crossReferenceGLDim5;
	}

	public void setCrossReferenceGLDim5(String crossReferenceGLDim5) {
		this.crossReferenceGLDim5 = crossReferenceGLDim5;
	}

	public String getCrossReferenceGLDim6() {
		return crossReferenceGLDim6;
	}

	public void setCrossReferenceGLDim6(String crossReferenceGLDim6) {
		this.crossReferenceGLDim6 = crossReferenceGLDim6;
	}

	public String getCrossReferenceGLDim7() {
		return crossReferenceGLDim7;
	}

	public void setCrossReferenceGLDim7(String crossReferenceGLDim7) {
		this.crossReferenceGLDim7 = crossReferenceGLDim7;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getAccountGroup() {
		return accountGroup;
	}

	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}

	public String getAccountGroupDescriptionLong() {
		return accountGroupDescriptionLong;
	}

	public void setAccountGroupDescriptionLong(String accountGroupDescriptionLong) {
		this.accountGroupDescriptionLong = accountGroupDescriptionLong;
	}

	public String getAccountGroupDescriptionShort() {
		return accountGroupDescriptionShort;
	}

	public void setAccountGroupDescriptionShort(String accountGroupDescriptionShort) {
		this.accountGroupDescriptionShort = accountGroupDescriptionShort;
	}
}
