package com.treetop.controller.rawfruitagreements;

import javax.servlet.http.HttpServletRequest;

import com.treetop.controller.BaseController;
import com.treetop.controller.UrlPathMapping;
import com.treetop.services.ServiceRawFruitAgreement;

@UrlPathMapping("requestType")
public class CtlRawFruitAgreements extends BaseController{

	@Override
	protected boolean isSecurityEnabled() {
	
		return false;
	}

	@Override
	protected String defaultRequest(HttpServletRequest request) {

		return detail(request);
	}

	@Override
	protected String securityUrl(HttpServletRequest request, String requestType) {

		return null;
	}

	public String detail(HttpServletRequest request) {
		InqRawFruitAgreements rawFruit = new InqRawFruitAgreements(request);
		
		ServiceRawFruitAgreement.getAgreement(rawFruit);
		
		request.setAttribute("DarthVader", rawFruit);
		
		return "/view/rawFruit/agreements/rawFruitAgreementDetail.jsp";
	}
	
	public String editContract(HttpServletRequest request) {
		InqRawFruitAgreements rawFruit = new InqRawFruitAgreements(request);
		String path = "";
		
		if (request.getMethod().equals("GET")) {		
			// go to the edit page
			ServiceRawFruitAgreement.getAgreement(rawFruit);
			path = "/view/rawFruit/agreements/rawFruitAgreementEditContract.jsp";
			request.setAttribute("DarthVader", rawFruit);
			
		} else {
			// update the data
			
			UpdContract contract = new UpdContract(request);
			
			//TODO add service call
			
			path = detail(request);
			
		}
		
		
		
		return path;
	}
	
	public String editCropInfo(HttpServletRequest request) {
		InqRawFruitAgreements rawFruit = new InqRawFruitAgreements(request);
		String path = "";
		
		if (request.getMethod().equals("GET")) {		
			// go to the edit page
			request.setAttribute("agreementLine",ServiceRawFruitAgreement.getAgreementLine());
            request.setAttribute("DarthVader", rawFruit);
            path = "/view/rawFruit/agreements/rawFruitAgreementEditCropInfo.jsp";
			
		} else {
			// update the data
			
			UpdCropInfo contract = new UpdCropInfo(request);
			
			//TODO add service call
			
			path = detail(request);
			
		}
		return path;
	}
	
	public String editLocation(HttpServletRequest request) {
		InqRawFruitAgreements rawFruit = new InqRawFruitAgreements(request);
		
		ServiceRawFruitAgreement.getAgreement(rawFruit);
		
		request.setAttribute("DarthVader", rawFruit);
		
		return "/view/rawFruit/agreements/rawFruitAgreementEditLocation.jsp";
	}
	
	public String editContact(HttpServletRequest request) {
		InqRawFruitAgreements rawFruit = new InqRawFruitAgreements(request);
		
		ServiceRawFruitAgreement.getAgreement(rawFruit);
		
		request.setAttribute("DarthVader", rawFruit);
		
		return "/view/rawFruit/agreements/rawFruitAgreementEditContact.jsp";
	}
	
}
