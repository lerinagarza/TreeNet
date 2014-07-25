package com.treetop.controller.comments;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.treetop.SessionVariables;
import com.treetop.businessobjects.KeyValue;
import com.treetop.controller.BaseController;
import com.treetop.services.ServiceKeyValue;

public class CtlComments extends BaseController {

	@Override
	protected boolean isSecurityEnabled() {
		return false;
	}
	
	@Override
	protected String defaultRequest(HttpServletRequest request) {
		return listComments(request);
	}

	@Override
	protected String securityUrl(HttpServletRequest request, String requestType) {
		return null;
	}

	private String deleteComment(HttpServletRequest request, HttpServletResponse response) {
		JSONObject o = new JSONObject();
		try {
	
			KeyValue kv = new KeyValue(request);
			String userId = SessionVariables.getSessionttiUserID(request, null);
			kv.setDeleteUser(userId);
	
			if (kv.getUniqueKey().trim().equals("")) {
				// no key, just ignore
			} else {
				// valid comment to delete
				ServiceKeyValue.deleteKeyValue(kv);
			}
	
			o.put("success", true);
	
		} catch (Exception e) {
			System.err.println("Exception @ CtlOperations.addComment(request).  " + e);
			try {
				o.put("error", "Error deleting comment.");
			} catch (Exception e1) {
			}
		}
	
		return o.toString();
	}

	@SuppressWarnings("unchecked")
		private String listComments(HttpServletRequest request) {
	
			try {
	
				KeyValue kv = new KeyValue(request);
				kv.setOrderBy("keysdate");  //sort by keys then date/time
				if (kv.isSequenced()) {
					kv.setOrderBy("keysseq");
				}
				Vector<KeyValue> comments = ServiceKeyValue.buildKeyValueList(kv);
	
				request.setAttribute("keys", kv);
				request.setAttribute("listComments", comments);
	
			} catch (Exception e) {
				System.err.println("Exception @ CtlOperations.listComments(request).  " + e);
			}
	
			return "/view/utilities/commentData.jsp";
		}

	private String updateComment(HttpServletRequest request, HttpServletResponse response) {
		JSONObject o = new JSONObject();
		try {
			String uuid = "";
			KeyValue kv = new KeyValue(request);
	
			if (kv.getValue().trim().equals("")) {
				o.put("success",false);
				o.put("error","Nothing to save");
			} else {
				
				String userId = SessionVariables.getSessionttiUserID(request, null);
				kv.setLastUpdateUser(userId);
	
				
				if (kv.getUniqueKey().trim().equals("")) {
					// no unique key set, this is a new comment
					uuid = ServiceKeyValue.addKeyValue(kv);
					o.put("isNew", true);
				} else {
					// already has a unique key, this is an update
					ServiceKeyValue.updateKeyValue(kv);
					o.put("isNew", false);
				}
				o.put("success", true);
				o.put("uuid", uuid);
				
				if (!kv.getSequence().equals("")) {
					ServiceKeyValue.resort(kv);
				}
				
				
				//resort
				
				
			}
	
		} catch (Exception e) {
			System.err.println("Exception @ CtlOperations.addComment(request).  " + e);
			try {
				o.put("error", "Error adding comment.");
			} catch (Exception e1) {
			}
		}
	
		return o.toString();
	}

	private String getDefinition(HttpServletRequest request, HttpServletResponse response) {
		JSONObject o = new JSONObject();
		try {
			
			KeyValue kv = new KeyValue(request);
			kv.setEntryType("definition");			

			Vector<KeyValue> definitions = ServiceKeyValue.buildKeyValueList(kv);

			if (definitions.isEmpty()) {
				o.put("success",false);
			} else {
				o.put("success",true);
				o.put("definitions", definitions);
			}
	
		} catch (Exception e) {
			System.err.println("Exception @ CtlOperations.addComment(request).  " + e);
			try {
				o.put("success",false);
				o.put("error", "Error getting definition.  " + e.toString());
			} catch (Exception e1) {
			}
		}
	
		return o.toString();
	}


}
