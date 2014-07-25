package com.treetop.controller.quality;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.treetop.Security;
import com.treetop.app.quality.UpdStatement;
import com.treetop.businessobjects.KeyValue;
import com.treetop.controller.BaseController;
import com.treetop.controller.UrlPathMapping;
import com.treetop.services.ServiceKeyValue;

@UrlPathMapping("requestType")
public class CtlQualityStatement extends BaseController {
	
	public static final String ENTRY_TYPE = "QualityStatements";
	
	private String inqStatement(HttpServletRequest request) {
		String url = "/view/quality/inqStatement.jsp";
		
		Vector<KeyValue> statements = new Vector<KeyValue>();			
		KeyValue kv = new KeyValue();
		kv.setEntryType(ENTRY_TYPE);
		kv.setKey1("");
		kv.setOrderBy("description");
		
		try {
			
			statements = ServiceKeyValue.buildKeyValueList(kv);
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		
		request.setAttribute("listStatements", statements);
		
		return url;
	}
	
	
	private String add(HttpServletRequest request) {
		String url = "/view/quality/updStatement.jsp";
		
		UpdStatement us = new UpdStatement(request);
		
		if (us.getSubmit().equals("")) {
			// go to update screen for data entry
			us.loadKeyValue();
			
		} else {
			//submit button was clicked, process add new statement
			KeyValue kv = us.loadKeyValue();
			
			try {
				ServiceKeyValue.addKeyValue(kv);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			url = inqStatement(request);
			
		}
		
		
		
		request.setAttribute("updViewBean", us);
		
		
		return url;
	}
	
	
	private String update(HttpServletRequest request) {
		String url = "/view/quality/updStatement.jsp";

		
		UpdStatement us = new UpdStatement(request);
		us.validate();
		
		if (!us.getErrorMessage().equals("")) {
			//If there is an issue, just go back to the inq screen
			
			url = inqStatement(request);
			
		} else if (us.getSubmit().equals("")) {
			//get the info to send to the screen
			KeyValue kv = new KeyValue();
			kv.setEntryType(ENTRY_TYPE);
			kv.setUniqueKey(us.getId());
			
			try {
				Vector<KeyValue> kvs = ServiceKeyValue.buildKeyValueList(kv);
				
				if (!kvs.isEmpty()) {
					us.setStatement(kvs.elementAt(0));
				}
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		
		} else {
			//process info for updating
			String userProfile = Security.getProfile(Security.getUserAuthorization(request)).toUpperCase();
			
			KeyValue kv = us.loadKeyValue();
			
			try {
				ServiceKeyValue.updateKeyValue(kv);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			url = inqStatement(request);
		}
		
		request.setAttribute("updViewBean", us);
		
		return url;	
	}
	
	
	private String delete(HttpServletRequest request) {
		
		UpdStatement us = new UpdStatement(request);
		
		KeyValue kv = us.loadKeyValue();
		
		try {
			ServiceKeyValue.deleteKeyValue(kv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return inqStatement(request);
	}

	@Override
	protected boolean isSecurityEnabled() {
		return true;
	}

	@Override
	protected String defaultRequest(HttpServletRequest request) {
		return inqStatement(request);
	}

	@Override
	protected String securityUrl(HttpServletRequest request, String requestType) {
		return "/web/CtlQualityStatement";
	}

}
