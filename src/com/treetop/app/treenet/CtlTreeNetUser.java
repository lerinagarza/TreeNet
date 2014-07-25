/*
 * Created on Oct 5, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
 
package com.treetop.app.treenet;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.data.UserFile;
import com.treetop.servlets.BaseServlet;

/**
 * @author twalto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CtlTreeNetUser  extends BaseServlet {

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
		
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
		
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#performTask(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void performTask(HttpServletRequest request, HttpServletResponse response) {
		String requestType = request.getParameter("requestType");
		if (requestType == null)
			requestType = "update";
		request.setAttribute("msg", "");
		//------------------------------------------------------------------  
		// Call Security		
		String urlAddress = "/web/CtlTreeNetUser";

		if (!callSecurity(request, response, urlAddress).equals("")) {
			try {
				response.sendRedirect(
					"/web/TreeNetInq?msg="
						+ SessionVariables.getSessionttiSecStatus(
							request,
							response));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		//------------------------------------------------------------------
		//Passed Security
		//*********************************
		// Default Screen
		String sendTo = "/APP/TreeNet/updTreeNetUser.jsp";
		//*********************************
		//-----------------------------------------------------------------
		//   INQUIRY / LIST PAGES
		//-----------------------------------------------------------------
		if (requestType.equals("inquiry") || requestType.equals("list")) {
		}
		//-----------------------------------------------------------------
		//   DETAIL PAGE
		//-----------------------------------------------------------------
		if (requestType.equals("detail")) {
		}
		//-----------------------------------------------------------------
		//   UPDATE PAGE
		//-----------------------------------------------------------------
		if (requestType.equals("update") ||
			requestType.equals("add")) {
			UpdTreeNetUser urn = new UpdTreeNetUser();
			urn.populate(request);
			List errors = urn.validate();
			urn.setErrors(errors);
			request.setAttribute("updViewBean", urn);
			if (errors.size() == 0)
			  pageUpd(request, response);
			else
			  request.setAttribute("msg", errors.toArray()[0].toString().trim());
			sendTo = "/APP/TreeNet/updTreeNetUser.jsp";
		}
		//-----------------------------------------------------------------
		//  Go to the JSP
		//-----------------------------------------------------------------
		try { // Send down to the JSP
			getServletConfig().getServletContext().getRequestDispatcher(
				sendTo).forward(
				request,
				response);
		} catch (Throwable theException) {
			theException.printStackTrace();
		}
		return;

	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageInq(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageInq(HttpServletRequest request, HttpServletResponse response) {
		
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageList(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageList(HttpServletRequest request, HttpServletResponse response) {
		
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageUpd(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageUpd(
		HttpServletRequest request,
		HttpServletResponse response) {
		UpdTreeNetUser utnu =
			(UpdTreeNetUser) request.getAttribute("updViewBean");
		if (utnu.getUserAS400ID() != null
			&& !utnu.getUserAS400ID().equals("")) {
			if (utnu.getSaveValue() != null
				&& !utnu.getSaveValue().equals("")) {
				try {
					UserFile updateUser = new UserFile(utnu.getUserAS400ID());
					if (updateUser.getUserEmail() == null
						|| !updateUser.getUserEmail().trim().equals(
							utnu.getUserEmail().trim())) {
						updateUser.setUserEmail(utnu.getUserEmail().trim());
						updateUser.update();
						request.setAttribute(
							"msg",
							"This Email Address Has been Changed or Added");
					}
				} catch (Exception e) {
				}
			}

			UserFile thisUser = new UserFile();
			try {
				thisUser = new UserFile(utnu.getUserAS400ID());
				utnu.setUserNumber(
					new Integer(thisUser.getUserNumber()).toString());
				utnu.setUserLongName(thisUser.getUserNameLong());
				utnu.setUserEmail(thisUser.getUserEmail());
			} catch (Exception e) {
				request.setAttribute(
					"msg",
					(utnu.getUserAS400ID() + " is not a Valid Tree Net User"));
				utnu.setUserAS400ID("");	

			}
		}
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageDtl(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageDtl(HttpServletRequest request, HttpServletResponse response) {
		
	}
}
