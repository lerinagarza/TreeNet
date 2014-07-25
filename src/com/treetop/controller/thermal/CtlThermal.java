package com.treetop.controller.thermal;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Vector;
import com.treetop.controller.BaseController;
import com.treetop.controller.UrlPathMapping;
import com.treetop.services.ServiceThermalProcess;

@UrlPathMapping("requestType")

public class CtlThermal extends BaseController {

	/**
	 * P1 Discrete Process Calculation
	 * @param request
	 * @return String url.
	 */
	private String inqP1(HttpServletRequest request) {
	
		String url = "/view/thermal/inqThermalP1.jsp";
		InqThermal inqThermal = new InqThermal();
		
		//retrieve request object parameters and list
		Enumeration e = request.getParameterNames();
		
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = request.getParameter(key);
			System.out.println(key + ":" + value);
		}
		
		//load request parameters into view bean.
		inqThermal.populate(request);
		
		//test for calculation request.
		
		if (!inqThermal.getSubmit().equals("") )
		{
			//validate incoming fields from screen.
			inqThermal.validateP1();
			
			//execute service if no errors exist.
			if (inqThermal.getErrorMessage().equals(""))
			{
				try {
					if (inqThermal.getTempValue().equals("0")){
						ServiceThermalProcess.discreteProcessCalcTemp(inqThermal);
						
						if(inqThermal.getP1UnitType().equals("F")) {
							inqThermal.setcTempValue(inqThermal.getcTempValue());
							double c = new Double(inqThermal.getTempValue());
							c = c * 9;
							c = c / 5;
							c = c + 32;
							inqThermal.setfTempValue(new BigDecimal(Double.toString(c)).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
						 }
					}
					
				} catch (Exception ex) {
					inqThermal.setErrorMessage("Error attempting to calculate value. ");
				}
				
				try {
					if (inqThermal.getDeeValue().equals("0")){
						ServiceThermalProcess.discreteProcessCalcD(inqThermal);
					}
					
				} catch (Exception ex) {
					inqThermal.setErrorMessage("Error attempting to calculate value. ");
				}
				
				try {
					if (inqThermal.getZeeValue().equals("0")){
						ServiceThermalProcess.discreteProcessCalcZ(inqThermal);
					}
					
				} catch (Exception ex) {
					inqThermal.setErrorMessage("Error attempting to calculate value. ");
				}
				
				try {
					if (inqThermal.getReductionValue().equals("0")){
						ServiceThermalProcess.discreteProcessCalcLogKill(inqThermal);
					}
					
				} catch (Exception ex) {
					inqThermal.setErrorMessage("Error attempting to calculate value. ");
				}
				
				try {
					if (inqThermal.getSecondsValue().equals("0")){
						ServiceThermalProcess.discreteProcessCalcSeconds(inqThermal);
					}
					
				} catch (Exception ex) {
					inqThermal.setErrorMessage("Error attempting to calculate value. ");
				}
			}
		}
		
		
		//last thing
		request.setAttribute("inqThermal", inqThermal);
			
		return url;
		
	}
	
	
	
	/**
	 * P2 Accumulative Process Calculation
	 * @param request
	 * @return String url.
	 */
	private String inqP2(HttpServletRequest request) {
	
		String url = "/view/thermal/inqThermalP2.jsp";
		InqThermal inqThermal = new InqThermal();
		
		//retrieve request object parameters and list
		Enumeration e = request.getParameterNames();
		
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = request.getParameter(key);
			System.out.println(key + ":" + value);
		}
		
		//load request parameters into view bean.
		inqThermal.populate(request);
		
		//test for calculation request.
		
		if (!inqThermal.getSubmit().equals("") )
		{
			//validate incoming fields from screen.
			inqThermal.validateP2();
			
			//execute service if no errors exist.
			if (inqThermal.getErrorMessage().equals(""))
			{
				try {
					//process calculation
					ServiceThermalProcess.accumulatedProcessCalculation(inqThermal);
					
				} catch (Exception ex) {
					inqThermal.setErrorMessage("Error attempting to calculate value. ");
				}
			}
		}
		
		//last thing
		request.setAttribute("inqThermal", inqThermal);
			
		return url;
		
	}
	
	@Override
	protected boolean isSecurityEnabled() {

		return true;
	}

	@Override
	protected String defaultRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		//return inqP1(request);
		return inqP1(request);
	}

	@Override
	protected String securityUrl(HttpServletRequest request, String requestType) {
		
		return "/web/CtlThermal";
	}



	/**
	 * P3 Variation of R With Temperature
	 * @param request
	 * @return String url.
	 */
	private String inqP3(HttpServletRequest request) {
	
		String url = "/view/thermal/inqThermalP3.jsp";
		InqThermal inqThermal = new InqThermal();
		
		//retrieve request object parameters and list
		Enumeration e = request.getParameterNames();
		
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = request.getParameter(key);
			System.out.println(key + ":" + value);
		}
		
		//load request parameters into view bean.
		inqThermal.populate(request);
		
		//test for calculation request.
		
		if (!inqThermal.getSubmit().equals("") )
		{
			//validate incoming fields from screen.
			inqThermal.validateP3();
			
			//execute service if no errors exist.
			if (inqThermal.getErrorMessage().equals(""))
			{
				try {
					//process calculation
					ServiceThermalProcess.calculateVariationOfRWithTemperatures(inqThermal);
					
				} catch (Exception ex) {
					inqThermal.setErrorMessage("Error attempting to calculate value. ");
				}
			}
		}
		
		//last thing
		request.setAttribute("inqThermal", inqThermal);
			
		return url;
		
	}

}
