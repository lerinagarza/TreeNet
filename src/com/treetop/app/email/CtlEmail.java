/*
 * Created on Aug 16, 2005 
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */ 
 
package com.treetop.app.email;
import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;

import com.treetop.servlets.*;
import com.treetop.Email;

/**
 * @author twalto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CtlEmail  extends BaseServlet {
	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageDetail(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageDtl(HttpServletRequest request, HttpServletResponse response) {
		
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		performTask(request, response);
		
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {
		performTask(request, response);
		
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#performTask(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void performTask(HttpServletRequest request, HttpServletResponse response) {
		try
		{
			//****** Main Parameters to always test for ********
			String requestType = request.getParameter("requestType");
			if (requestType == null)
			  requestType = "";
		
		//------------------------------------------------------------------  
		// Will Not Call Security, Anyone can Email
		//*********************************		
		//********** Decide which methods need to be called ***********
		//**********     call those methods                 ***********
		  //String sendTo = "/APP/Email/sendEmail.jsp?";
		  String sendTo = "/view/email/sendEmail.jsp?";
	     
  		  SendEmail se = new SendEmail();
		  se.populate(request);
		  if (se.emailFrom.equals(""))
		     se.setEmailFrom(SessionVariables.getSessionttiProfile(request, response));
		  List errors = se.validate();
		  request.setAttribute("sendViewBean", se);
		  if (se.getSaveButton() != null &&
		      se.getEmailFromError().equals("") &&
		      se.getEmailToError().equals(""))
		  {
			String problemSendingEmail = sendEmail(request, response);
			if (problemSendingEmail.equals(""))
			   sendTo = "/JSP/TreeNet/closeWindow.jsp";		  	
		  }

			//***** Go to the JSP *****//
			getServletConfig().getServletContext().
				getRequestDispatcher(sendTo).
				forward(request, response);
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		theException.printStackTrace();
	}
		
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageInquiry(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageInq(HttpServletRequest request, HttpServletResponse response) {
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageList(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageList(HttpServletRequest request, HttpServletResponse response) 
	{
		
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageUpdate(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageUpd(HttpServletRequest request, HttpServletResponse response) {
		
		
	}

	/* 
	 * Use the Email Class and Send out the email.
	 */
	protected String sendEmail(HttpServletRequest request, HttpServletResponse response) {
		String returnFromEmail = "";
		try
		{
		   SendEmail se = (SendEmail) request.getAttribute("sendViewBean");
		   String[] to = new String[1];
		   String[] cc = new String[0];
		   String[] bcc = new String[1];
		   to[0] = se.getEmailTo();
		   bcc[0] = se.getEmailFrom();
		   Email setEmail = new Email();	
		   returnFromEmail = setEmail.sendEmail(to, cc, bcc, se.getEmailFrom(), se.getEmailSubject(), (se.getBody() + "<br><br>" + se.getAdditionalBody()));
		}
		catch(Exception e)
		{
		}
		return returnFromEmail;
	}
}
