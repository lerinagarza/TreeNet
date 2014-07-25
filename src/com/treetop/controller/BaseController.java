package com.treetop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.treetop.SessionVariables;
import com.treetop.utilities.GeneralUtility;


public abstract class BaseController  extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		requestDirector(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		requestDirector(request, response);
	}

	private void requestDirector(HttpServletRequest request, HttpServletResponse response) {

		String 		requestType = getRequestType(request);

		
		
		if (isSecurityEnabled()) {
			String securityUrl = getSecurityUrl(request, requestType);

			String securityResponse = callSecurity(request, response, securityUrl);
			if (securityResponse.equals("")) {
				//security passed
			} else {
				//security failed, send to authorization failed page
				try {
					response.sendRedirect(request.getContextPath() + 
							"/view/unauthorized.jsp" + 
							"?msg=" + securityResponse +
							"&url=" + securityUrl);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		} else {
			//no security enabled, manually call security servlet 
			//to set the user session variable
			
			
			String user = SessionVariables.getSessionttiUserID(request, response);
			if (user == null) {
				// Call the security Servlet
				try {
					getServletConfig()
						.getServletContext()
						.getRequestDispatcher("/TTISecurity")
						.include(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
		}

		//this string will be the JSP to send to for JSP requests
		//for AJAX requests this will be the JSON to return
		String send = "";

		//JSON request handlers use both request and response objects
		Class[] parameterTypes = null;
		Object[] arguments = null;
		

		try {
			//Get the method in the servlet class that matches the requestType
			Method method = null;

			
			try {
				//try finding the method with the requestType name with only a HttpServletRequest parameter
				parameterTypes = new Class[]{HttpServletRequest.class};
				arguments = new Object[] {request};
				method = this.getClass().getDeclaredMethod(requestType, parameterTypes);

			} catch (NoSuchMethodException e) {
				//if no method exists, try with both HttpServletRequest and HttpServletResponse as parameters
				parameterTypes = new Class[]{HttpServletRequest.class, HttpServletResponse.class};
				arguments = new Object[] {request,response};
				method = this.getClass().getDeclaredMethod(requestType, parameterTypes);
				
			}
			
			
			
			
			method.setAccessible(true);
			send = (String) method.invoke(this, arguments);

			//execute the method
				

		} catch (NoSuchMethodException e) {
			//method does not exist, use default
			try {

				send = defaultRequest(request);

			} catch (Exception ex) {
				System.out.println("defaultRequest() not defined in servlet.  " + ex);
			}

		} catch (Exception e) {
			//catch all other exceptions
			System.out.println(e);

		}  finally {

			response.setCharacterEncoding("UTF-8");
			String accept = request.getHeader("Accept");
			String responseContentType = response.getContentType();
			if (responseContentType != null && responseContentType.contains("octet-stream")) {
				
				//content type set as a file download
				//do not attempt to print out any data
				
			} else if (accept.contains("json")) {

				//print out the JSON string
				PrintWriter out = null;
				try {

					response.setContentType("application/json");
					
					out = response.getWriter();
					out.print(send);

				} catch (Exception e) {
					System.out.println("Error printing JSON.  " + e);
				} finally {
					if (out != null) {
						out.close();
					}
				}

			} else {

				//direct to the JSP
				try {
					
					
					if (!send.equals("")) {
						
						
						getServletConfig()
							.getServletContext()
							.getRequestDispatcher(send)
							.forward(request, response);
						
						
					}
					
				} catch (Exception e) {
					System.out.println("---------------------");
					System.out.println(request.getServletPath());
					GeneralUtility.printRequestParameters(request);
					System.out.println("---------------------");
				}
			} 
			
			
		}

	}
	
	

	protected String callSecurity(HttpServletRequest request,
			HttpServletResponse response,
			String securityUrl) {
		HttpSession sess = request.getSession(true);

		// Set the Status
		SessionVariables.setSessionttiSecStatus(request, response, "");
		// Decide which URL to use, based on Request Type.

		SessionVariables.setSessionttiTheURL(request, response, securityUrl);

		// Call the security Servlet
		try {
			getServletConfig()
			.getServletContext()
			.getRequestDispatcher("/TTISecurity")
			.include(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Decision of whether or not to use the Inq, List or Detail
		String thisStatus =
			SessionVariables.getSessionttiSecStatus(request, response);
		// remove the Status and the Url
		sess.removeAttribute("ttiTheURL");
		sess.removeAttribute("ttiSecStatus");

		return thisStatus;
	}


	/**
	 * Returns the a string of the request type.<br />
	 * If no requestType is specified, "defaultRequest" will be set<br />
	 * This method also sets the parsed requestType as a request attribute<br />
	 * @param request
	 * @return
	 */
	private String getRequestType(HttpServletRequest request) {
		
		
		String requestType = request.getParameter("requestType");

		if (requestType == null || requestType.equals("")) {
			
			UrlPathMapping pathMapping = (UrlPathMapping) this.getClass().getAnnotation(UrlPathMapping.class);
			if (pathMapping != null) {
				Hashtable<String, String> pathParms = MappingUtility.getMappingValues(request, pathMapping);
				requestType = pathParms.get("requestType");
			}
		}
		
		if (requestType == null || requestType.equals("")) {
			requestType = "defaultRequest";
		}
		
		request.setAttribute("requestType", requestType);
		return requestType;
	}


	/**
	 * Executes the implemented method in the servlet to determine the URL to 
	 * pass to security
	 * @param request
	 * @param requestType
	 * @return
	 */
	private String getSecurityUrl(HttpServletRequest request, String requestType) {
		String securityUrl = "";
		try {

			securityUrl = securityUrl(request, requestType);	

		} catch (Exception e) {
			System.out.println("Error getting security url.  " + e);
		}
		return securityUrl;
	}
	
	
	/**
	 * Return true or false to run security
	 *    If False the Session Variables do not get set
	 * @return
	 */
	protected abstract boolean isSecurityEnabled();

	
	/**
	 * Each servlet must define how to handle a "default" (blank) 
	 *       Which JSP should it go to?
	 * @param request
	 * @return
	 */
	protected abstract String defaultRequest(HttpServletRequest request);
	
	/**
	 * Returns the URL to test security against.
	 * Use the requsetType to determine which URL to send in.
	 * @param request
	 * @param requestType
	 * @return
	 */
	protected abstract String securityUrl(HttpServletRequest request, String requestType);

}
