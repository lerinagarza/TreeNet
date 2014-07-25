package com.treetop.controller.sublot;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;

import com.treetop.SessionVariables;
import com.treetop.app.descriptivecode.InqDescCode;
import com.treetop.businessobjects.KeyValue;
import com.treetop.controller.BaseController;
import com.treetop.controller.UrlPathMapping;
import com.treetop.services.ServiceDescriptiveCode;
import com.treetop.services.ServiceKeyValue;
import com.treetop.services.ServiceRawFruitPeachReceiving;

@UrlPathMapping("requestType")
public class CtlSubLot extends BaseController {
	
	private String updPeach(HttpServletRequest request) {
		String url = "view/sublot/updPeachReceiving.jsp";
		
		UpdSubLotHeader updPeachScreen = new UpdSubLotHeader(request);
		
		try {
			if (updPeachScreen.getSubmitButton().trim().equals(""))
			{
				DtlSubLot dtlPeachScreen = new DtlSubLot(request);
				dtlPeachScreen.setEnvironment("PRD");
				
				try{
					ServiceRawFruitPeachReceiving.findPeachTicket(dtlPeachScreen);
					updPeachScreen.loadFromBeanPeach(dtlPeachScreen.getBeanPeach());
					
				}catch(Exception e)
				{}
				
				try {
				// Go and get the comment field
					KeyValue kv = new KeyValue();
					kv.setEnvironment(dtlPeachScreen.getEnvironment().trim());
					kv.setKey1(dtlPeachScreen.getItemNumber().trim());
					kv.setKey2(dtlPeachScreen.getLotNumber().trim());
					kv.setEntryType("PeachReceiving");
				
					
					
					Vector<KeyValue> comments = ServiceKeyValue.buildKeyValueList(kv);
					dtlPeachScreen.setListComments(comments);
					
					for (KeyValue comment: comments) {
						
						updPeachScreen.setComment(comment.getValue());
						
					}
					
				} catch(Exception e) {
					dtlPeachScreen.setListComments(new Vector<KeyValue>());
				}	  		
				
			} else {
				updPeachScreen.validate();
				
				// DELETE and then insert CLEAN Update
				try{
				   ServiceRawFruitPeachReceiving.deletePeachTicket(updPeachScreen);
				} catch (Exception e) {
					updPeachScreen.setErrorMessage(e.toString());
				}
				
				try{
				   ServiceRawFruitPeachReceiving.insertPeachTicket(updPeachScreen);
				} catch (Exception e) {
					updPeachScreen.setErrorMessage(e.toString());
				}
				
				//Store the comment -- Delete the old, store the NEW
				try {
					// Go and get the comment field
						KeyValue kv = new KeyValue();
						kv.setEnvironment(updPeachScreen.getEnvironment().trim());
						kv.setKey1(updPeachScreen.getItemNumber().trim());
						kv.setKey2(updPeachScreen.getLotNumber().trim());
						kv.setEntryType("PeachReceiving");
						Vector listComments = ServiceKeyValue.buildKeyValueList(kv);
						if (listComments != null &
							!listComments.isEmpty())
						{
							KeyValue keyValue = (KeyValue) listComments.elementAt(0);
							kv.setDeleteUser(updPeachScreen.getReceivingUser());
							kv.setUniqueKey(keyValue.getUniqueKey());
							ServiceKeyValue.deleteKeyValue(kv);
						}
					}catch(Exception e){
						updPeachScreen.setErrorMessage(e.toString());
					}	  		
				
				if (!updPeachScreen.getComment().trim().equals("")) {
				  try{	
					KeyValue kv = new KeyValue();
					kv.setEntryType("PeachReceiving");
					kv.setKey1(updPeachScreen.getItemNumber());
					kv.setKey2(updPeachScreen.getLotNumber());
					kv.setKey3("");
					kv.setKey4("");
					kv.setKey5("");
					kv.setLastUpdateUser(updPeachScreen.getReceivingUser());
					kv.setDeleteUser("");
					kv.setStatus("");
					
					kv.setValue(updPeachScreen.getComment());
					kv.setDescription("");
					
					ServiceKeyValue.addKeyValue(kv);
				  }catch (Exception e){
					  updPeachScreen.setErrorMessage(e.toString()); 
				  }
				}
				updPeachScreen = new UpdSubLotHeader();
			}
			
		} catch (Exception e) {
			updPeachScreen.setErrorMessage(e.toString());
		}
		
		request.setAttribute("updPeach", updPeachScreen);
		
		return url;
	}
	
	private String dtlPeach(HttpServletRequest request) {
		String url = "view/sublot/dtlPeachReceiving.jsp";
		
		DtlSubLot dtlPeachScreen = new DtlSubLot(request);
		dtlPeachScreen.setEnvironment("PRD");
		
		try{
			ServiceRawFruitPeachReceiving.findPeachTicket(dtlPeachScreen);
		}catch(Exception e)
		{}
		
		try {
		// Go and get the comment field
			KeyValue kv = new KeyValue();
			kv.setEnvironment(dtlPeachScreen.getEnvironment().trim());
			kv.setKey1(dtlPeachScreen.getItemNumber().trim());
			kv.setKey2(dtlPeachScreen.getLotNumber().trim());
			kv.setEntryType("PeachReceiving");
		
			dtlPeachScreen.setListComments(ServiceKeyValue.buildKeyValueList(kv));
		}catch(Exception e){
			dtlPeachScreen.setListComments(new Vector());
		}	  		
		
		request.setAttribute("dtlPeach", dtlPeachScreen);
		
		return url;
	}

	private String inqPeach(HttpServletRequest request) {
		String url = "view/sublot/inqPeachReceiving.jsp";
		
		InqSubLot inqPeachScreen = new InqSubLot(request);
		inqPeachScreen.determineSecurityPeachReceiving(request, null);
		
		if (!inqPeachScreen.getSubmitButton().equals("")) {
			inqPeachScreen.validate();
			
			try {
				ServiceRawFruitPeachReceiving.listPeachTicket(inqPeachScreen);
			} catch (Exception e) {
				inqPeachScreen.setErrorMessage(e.toString());
			}
			
		}
		
		request.setAttribute("inqPeach", inqPeachScreen);
		
		return url;
	}
	
	private String listGrowers(HttpServletRequest request) {
		
		JSONArray arr = new JSONArray();
		
		
		Vector<InqDescCode> v = new Vector<InqDescCode>();
		InqDescCode dc = new InqDescCode();
		dc.setInqKey00("PEACH GROWER NAME");
		 v = ServiceDescriptiveCode.getDescriptiveCodeDetails(dc);
		 
		 for (InqDescCode dci : v) {
			 arr.put(dci.getInqAlpha01());
		 }
			
		return arr.toString();
		
	}
	
	@Override
	protected boolean isSecurityEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected String defaultRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return inqPeach(request);
	}

	@Override
	protected String securityUrl(HttpServletRequest request, String requestType) {
		// TODO Auto-generated method stub
		return "/web/CtlSubLot/inqPeach";
	}

}
