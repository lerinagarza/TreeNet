/*
 * Created on April 27, 2011
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.app.descriptivecode;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.servlets.BaseServlet;
import com.treetop.services.ServiceDescriptiveCode;
import com.treetop.viewbeans.ParameterMessageBean;



/**
 * @author jhagle
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CtlDescCode extends BaseServlet {

	/*
	 * Process incoming HTTP GET requests Goes directly to the performTask
	 * @see com.treetop.servlets.BaseServlet#doGet()
	 */
	
	private boolean securityEnabled = false;
	
	public void doGet(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {
		performTask(request, response);
	}

	/*
	 * Process incoming HTTP POST requests Goes directly to the performTask
	 * @see com.treetop.servlets.BaseServlet#doPost()
	 */
	public void doPost(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {
		performTask(request, response);
	}
	/*
	 * Tests Parameters, Calls Security, and Directs Traffic between the pages
	 * @see com.treetop.servlets.BaseServlet#performTask(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void performTask(HttpServletRequest request,
			HttpServletResponse response) {

		String requestType = request.getParameter("requestType");
		if (requestType == null)
			requestType = "inqDescCode";
		
		request.setAttribute("requestType", requestType);
		
		//------------------------------------------------------------------
		// Call Security
		String urlAddress = "/web/CtlDescCode";
		
		//set roles and groups for testing
		SessionVariables.setSessionttiProfile(request, response, "TNUSER");
		String[] ttiUserRoles = {"1","8"};
		SessionVariables.setSessionttiUserRoles(request, response, ttiUserRoles);
		String[] ttiUserGroups = {""};
		SessionVariables.setSessionttiUserGroups(request, response, ttiUserGroups);

		
		if (securityEnabled) {
			if (!callSecurity(request, response, urlAddress).equals("")) {
				try {
					
					response.sendRedirect("/web/TreeNetInq?msg="
							+ SessionVariables.getSessionttiSecStatus(request,
									response));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		//------------------------------------------------------------------
		//Passed Security
		//*********************************
		// Default Screen
		String sendTo = "/APP/DescriptiveCode/inqDescCode.jsp";
		//*********************************
		//-----------------------------------------------------------------
		//   UPDATE PAGE To be dealt with BEFORE the List Pages
		//     this is because if there is a big problem it can send it back
		//     to the list or inquiry pages
		//  ADD / COPY / and REVISE all go to the Update Page
		//-----------------------------------------------------------------
		if (requestType.equals("updDescCode")) {
			try {
				InqDescCode dc = new InqDescCode();
				dc.populate(request);
				dc.validate(request, response);
				request.setAttribute("inqDescCode", dc);
				
				UpdDescCode uc = new UpdDescCode();
				uc.populate(request);
				uc.validate(request, response);
				request.setAttribute("updDescCode", uc);
				
				pageUpd(request, response);
				
				requestType = (String) request.getAttribute("requestType");
								
				sendTo = "/APP/DescriptiveCode/updDescCode.jsp";
				
			} catch (Exception e) {System.out.println("Error on reqType updDescCode:  " + e);}
		}
		
		if (requestType.equals("addNewEntry")) {
			try {
				InqDescCode dc = new InqDescCode();
				dc.populate(request);
				dc.validate(request, response);
				request.setAttribute("inqDescCode", dc);
				
				UpdDescCode uc = new UpdDescCode();
				uc.populate(request);
				uc.validate(request, response);
				request.setAttribute("updDescCode", uc);
				
				
				pageUpd(request, response);
				
				if (request.getAttribute("requestType").equals("listDescCode")) {
					requestType = (String) request.getAttribute("requestType");
				}
				
				sendTo = "/APP/DescriptiveCode/updDescCode.jsp";
				
			} catch (Exception e) {System.out.println("Error on reqType addNewEntry:  " + e);}
		}
		if (requestType.equals("delDescCode")) {
			try {
				InqDescCode dc = new InqDescCode();
				dc.populate(request);
				dc.validate(request, response);
				request.setAttribute("delDescCode", dc);
				
				pageUpd(request, response);
								
			} catch (Exception e) {System.out.println("Error on reqType delDescCode:  " + e);}
		}
		if (requestType.equals("updDescCodeHeader")) {
			InqDescCode dc = new InqDescCode();
			dc.populate(request);
			dc.validate(request, response);
			request.setAttribute("inqDescCode", dc);
			
			UpdDescCode uc = new UpdDescCode();
			uc.populate(request);
			uc.validate(request, response);
			request.setAttribute("updDescCode", uc);
			
			sendTo = "/APP/DescriptiveCode/updDescCode.jsp";
			
		}
		if (requestType.equals("updDescCodeSecurity")) {
			InqDescCode dc = new InqDescCode();
			dc.populate(request);
			dc.validate(request, response);
			request.setAttribute("inqDescCode", dc);
			
			UpdDescCode uc = new UpdDescCode();
			uc.populate(request);
			uc.validate(request, response);
			request.setAttribute("updDescCode", uc);
			
			sendTo = "/APP/DescriptiveCode/listDescCode.jsp";
			
		}
		
		//-----------------------------------------------------------------
		//   DETAIL PAGE
		//-----------------------------------------------------------------
		
		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//       Main Inquiry and List Pages
		//-----------------------------------------------------------------

		if (requestType.equals("inqDescCode")) {
			try {
				InqDescCode dc = new InqDescCode();
				dc.populate(request);
				dc.validate(request, response);
				request.setAttribute("inqDescCode", dc);
				
				pageInq(request, response);
				sendTo = "/APP/DescriptiveCode/inqDescCode.jsp";
			} catch (Exception e) {System.out.println("Error on reqType inqDescCode:  " + e);}
		}
		if (requestType.equals("listDescCode")) {
			try {
				InqDescCode dc = new InqDescCode();
				dc.populate(request);
				dc.validate(request, response);
				request.setAttribute("listDescCode", dc);

				pageList(request, response);

			} catch (Exception e) {System.out.println("error in reqType listDescCode:  " + e);}
			sendTo = "/APP/DescriptiveCode/listDescCode.jsp";
		}


		/* Go to the JSP */
		try { 
			getServletConfig().getServletContext().getRequestDispatcher(sendTo)
					.forward(request, response);
		} catch (Throwable theException) {
			
			System.out.println("---------------------");
			System.out.println("CtlDescCode: " + SessionVariables.getSessionttiProfile(request, response) + " : ");
			ParameterMessageBean.printParameters(request);
			System.out.println("---------------------");
			System.out.println(theException);
			theException.printStackTrace();
		}
		return;

	}

	/* Retrieve and Build everthing needed for the Inquiry Page */
	protected void pageInq(HttpServletRequest request, HttpServletResponse response) {
		String reqType = (String) request.getAttribute("requestType");
		try {
			InqDescCode dc = (InqDescCode) request.getAttribute("inqDescCode");
			if (request.getParameter("search") != null) {
				dc.setInqSearch(request.getParameter("search").trim());
			}
				Vector headers = ServiceDescriptiveCode.getDescriptiveCodeHeaders(dc);
				request.setAttribute("headers", headers);
				
		} catch (Exception e) {
			System.out.println("Error found in CtlDescCode.pageInq():  " + e);
		}
	}

	/* Retrieve and Build everthing needed for the List Page */
	protected void pageList(HttpServletRequest request, HttpServletResponse response) {
		try	{
			String reqType = (String) request.getAttribute("requestType");
			if (reqType.equals("listDescCode")) {
				  InqDescCode dc = (InqDescCode) request.getAttribute("listDescCode");
				  Vector details = ServiceDescriptiveCode.getDescriptiveCodeDetails(dc);
				  if (details.size() == 0) {
					  dc.setInqErrMsg("No records for " + dc.getInqKey00());
				  }
				  request.setAttribute("details", details);
			}
		  
		} catch(Exception e) { 
			System.out.println("Error found in CtlDescCode.pageList(): " + e);
		}	
	}

	/* Retrieve and Build everthing needed for the Detail Page */
	protected void pageDtl(HttpServletRequest request, HttpServletResponse response) {
		String requestType = (String) request.getAttribute("requestType");
		try {
			
		} catch(Exception e){
			System.out.println("Error found in CtlDescCode.pageDtl(): " + e);
		}
	}

	/* Guide the Update to the correct area */
	protected void pageUpd(HttpServletRequest request, HttpServletResponse response) {
		String requestType = (String) request.getAttribute("requestType");
		if (requestType.equals("updDescCode")) {
			try {
				InqDescCode dc = (InqDescCode) request.getAttribute("inqDescCode");
				UpdDescCode uc = (UpdDescCode) request.getAttribute("updDescCode");

				if (uc.getUpdSubmit() == null || uc.getUpdSubmit().equals("")) {
					dc = ServiceDescriptiveCode.getDescriptiveCodeDetailFromKeys(dc);
					request.setAttribute("listDescCode", dc);
				} else {
					ServiceDescriptiveCode.updateDescriptiveCodeDetails(uc, dc);
					if (dc.getInqType().equals("H")) {
						request.setAttribute("requestType", "inqDescCode");
						request.setAttribute("inqDescCode", dc);
					} else {
						request.setAttribute("requestType", "listDescCode");
						request.setAttribute("listDescCode", dc);
					}
					
					
				}
			} catch(Exception e){
				System.out.println("Error found in CtlDescCode.pageUpd(): " + e);
			}

		}
		

		if (requestType.equals("addNewEntry")) {
			UpdDescCode uc = (UpdDescCode) request.getAttribute("updDescCode");
			
			if (!uc.getUpdSubmit().equals("")) {
				//Call insert
				
				ServiceDescriptiveCode.insertDescriptiveCodeDetails(uc);
				
				request.setAttribute("requestType", "listDescCode");
				
				InqDescCode dc = new InqDescCode();
				dc.setInqType(uc.getUpdType());
				request.setAttribute("listDescCode", dc);
			}
			
		}
		if (requestType.equals("delDescCode")) {
			InqDescCode dc = (InqDescCode) request.getAttribute("delDescCode");
			ServiceDescriptiveCode.deleteDescriptiveCodeDetails(dc);
			if (dc.getInqType().equals("H")) {
				request.setAttribute("requestType", "inqDescCode");
				request.setAttribute("inqDescCode", dc);
			} else {
				request.setAttribute("requestType", "listDescCode");
				request.setAttribute("listDescCode", dc);
			}
		}
	}
}
