/*
 * Created on Jul 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.treetop.SessionVariables;

/**
 * @author twalto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class BaseServlet extends javax.servlet.http.HttpServlet {
	public abstract void doGet(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response)
		throws javax.servlet.ServletException, java.io.IOException;
	public abstract void doPost(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response)
		throws javax.servlet.ServletException, java.io.IOException;
	public abstract void performTask(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response);
	protected abstract void pageInq(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response);
	protected abstract void pageList(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response);
	protected abstract void pageUpd(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response);
	protected abstract void pageDtl(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response);
	protected String callSecurity(
		HttpServletRequest request,
		HttpServletResponse response,
		String urlAddress) {
		//********************************************************************
		// Execute security servlet. Not sure where security will come in.
		//********************************************************************
		// Allow Session Variable Access

		HttpSession sess = request.getSession(true);

		// Set the Status
		SessionVariables.setSessionttiSecStatus(request, response, "");
		// Decide which URL to use, based on Request Type.

		SessionVariables.setSessionttiTheURL(request, response, urlAddress);

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
}
