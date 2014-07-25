/*
 * Created on April 2, 2012
 *    Author Teri Walton
 * 
 * Will be used to control any reports needed Billing for contract manufacturing
 * 
 */
package com.treetop.controller.contractmanufacturing;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanContractManufacturing;
import com.treetop.controller.BaseController;
import com.treetop.services.ServiceContractManufacturing;

/**
 * 5/31/12 -- Will use the "new" BaseController version
 * @author twalto
 * 
 */
public class CtlBilling extends BaseController {

	private static final HttpServletResponse HttpServletResponse = null;

	private String billingCustomPackMO(HttpServletRequest request) {
		
		String message = new String();
		
		try {
			
			InqBilling ib = new InqBilling();
			ib.populate(request);
			
			if (ib.getEnvironment().equals(""))
				ib.setEnvironment("PRD");
			
			ib.setEnvironment("TST");
			ib.setCompany("100");
			ib.setBillingType("BOP");
			@SuppressWarnings("unused")
			Vector<BeanContractManufacturing> bean = new Vector<BeanContractManufacturing>();		
			bean = ServiceContractManufacturing.processBillingForCustomMO(ib);
			message = "ok";
			
		} catch (Exception e) {		
			message = e.toString();			
		}
		
		return message;
	}
	/*
	 * Deal With anything relating to the inqBilling requestType
	 *   This will return the Path and JSP name that should be displayed
	 * 
	 */
	private String inqBilling(HttpServletRequest request) {
		
		try{
			InqBilling ib = new InqBilling();
			ib.populate(request);
			ib.validate();
			
			// Retrieve a list of Valid MO's that need to be billed:
			// use the billingType field, to help determine what is included on the list
			if (ib.getRequestType().trim().equals("") ||
				ib.getRequestType().trim().equals("inqBilling"))
			   ib.setListMOs(ServiceContractManufacturing.dropDownMOs(ib));
			
			request.setAttribute("viewBean", ib);	
			
		}catch(Exception e)
		{
			System.out.println("Error Found/Caught in CtlBilling.inqBilling:" + e);
		}

		return "/view/contractmanufacturing/inqBilling.jsp";
	}

	/*
	 * Retrieve and Build everything needed for the Update Page
	 *    Process any updates as well
	 * 
	 */
	private String updBilling(HttpServletRequest request) {
		String returnValue = "";
		try{
			UpdBilling ub = new UpdBilling();
			ub.populate(request);
			ub.validate();
			ub.setUpdateUser(SessionVariables.getSessionttiProfile(request, null));
	//		ub.setManufacturingOrderNumber("1015306");
			if (!ub.getSubmit().trim().equals("") && 
				ub.getDisplayMessage().trim().equals(""))
			{ // need to process the MO
			
				try {
			//		System.out.println("Processing Manufacturing Order " + ub.getManufacturingOrderNumber());
					ServiceContractManufacturing.processBillingForMO(ub);
			//		System.out.println("Will need to send in the date for the transactions.");
						
				} catch (Exception e) {
					ub.setStatus("Error executing service.  " + e);
				}
				returnValue = listBilling(request);
				InqBilling ib = (InqBilling) request.getAttribute("viewBean");
				if (ub.getStatus().trim().equals(""))
				   ib.setDisplayMessage("Manufacturing Order " + ub.getManufacturingOrderNumber() + " has been sucessfully submitted.");
				else
				   ib.setDisplayMessage(ub.getStatus());
				request.setAttribute("viewBean", ib);
			}
			if (returnValue.trim().equals(""))
				returnValue = defaultRequest(request);
		}catch(Exception e)
		{
			System.out.println("Error Found/Caught in CtlBilling.updBilling:" + e);
		}
		return returnValue;
	}

	/*
	 * Retrieve and Build everything needed for the billing List Page
	 * 
	 */
	private String listBilling(HttpServletRequest request) {
		
		String returnValue = inqBilling(request);
		try{
			InqBilling ib = (InqBilling) request.getAttribute("viewBean");
			
			ib.setBeanInfo(ServiceContractManufacturing.listLotsFromMO(ib));
			
			request.setAttribute("viewBean", ib);	
			
			returnValue = "/view/contractmanufacturing/listBilling.jsp";
			
		}catch(Exception e)
		{
			System.out.println("Error Found/Caught in CtlBilling.listBilling:" + e);
		}
		
		return returnValue;
	}

	@Override
	protected boolean isSecurityEnabled() {
		// Enable Security
		return true;
		//return false;
	}

	@Override
	protected String defaultRequest(HttpServletRequest request) {
		
		return inqBilling(request);
		
	}

	@Override
	protected String securityUrl(HttpServletRequest request, String requestType) {
		// Currently Coded ONLY to deal with Ocean Spray -- at another time, this may change
		return "/web/CtlBilling";
	}
}
