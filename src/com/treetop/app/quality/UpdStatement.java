package com.treetop.app.quality;

import javax.servlet.http.HttpServletRequest;

import com.treetop.Security;
import com.treetop.businessobjects.KeyValue;
import com.treetop.controller.BaseViewBeanR4;
import com.treetop.controller.UrlPathMapping;
import com.treetop.controller.quality.CtlQualityStatement;

@UrlPathMapping({"requestType","id"})
public class UpdStatement extends BaseViewBeanR4 {
	
	private String		id			    = "";
	private String		title			= "";
	private String		statementText	= "";
	
	private String		userProfile		= "";
	
	private KeyValue	statement		= new KeyValue();
	
	private String		submit			= "";

	/**
	 * Default constructor
	 */
	public UpdStatement() {
		
	}
	
	/**
	 * Special constructor with HttpServlerRequest
	 * @param request
	 */
	public UpdStatement(HttpServletRequest request) {
		this.populate(request);
		String userProfile = Security.getProfile(Security.getUserAuthorization(request)).toUpperCase();
		this.setUserProfile(userProfile);
	}
	
	public KeyValue loadKeyValue() {
				
		KeyValue kv = new KeyValue();
		kv.setEntryType(CtlQualityStatement.ENTRY_TYPE);
		
		kv.setUniqueKey(this.getId());
		kv.setKey1(CtlQualityStatement.ENTRY_TYPE);
		kv.setKey2("");
		kv.setKey3("");
		kv.setKey4("");
		kv.setKey5("");
		kv.setDescription(this.getTitle());
		kv.setValue(this.getStatementText());
		kv.setStatus("");
		
		kv.setLastUpdateUser(this.getUserProfile());
		kv.setDeleteUser(this.getUserProfile());
		
		this.setStatement(kv);
		return kv;
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		if (this.getId().equals("")) {
			this.setErrorMessage("ID cannot be left blank.");
		}
	}

	public KeyValue getStatement() {
		return statement;
	}

	public void setStatement(KeyValue statement) {
		this.statement = statement;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatementText() {
		return statementText;
	}

	public void setStatementText(String statementText) {
		this.statementText = statementText;
	}

}
