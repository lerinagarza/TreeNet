/*
 * Created on November 30, 2005 TWALTON
 *
 * Responsibility is a Ctl (Servlet to access URL's and Comments)
 * 
 * Currently this servlet does NOT go directly TO or FROM Any JSP's
 *   IF it changes and the Functions are NOT ezviewed this may change
 *   be called from anything.
 * 
 * Called from OTHER servlets
 * 
 */
 
package com.treetop.app.function;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.businessobjects.TicklerFunctionDetail;
import com.treetop.services.ServiceTickler;
import com.treetop.servlets.BaseServlet;

/**
 * @author twalto
 *
 */
public class CtlFunction extends BaseServlet {
	/* 
	 * ADD - UPDATE - DELETE RECORDS
	 */
	protected void pageUpd(
		HttpServletRequest request,
		HttpServletResponse response) {

		UpdFunctionDetail ufd =	(UpdFunctionDetail) request.getAttribute("updFunctionViewBean");

		if (ufd.getRequestType().equals("addList"))
		{
			// Add the list of Tickler Responsibilities to the File
			try{
				ServiceTickler.addDetailAll(ufd);
			}catch(Exception e ){
				System.out.println("Problem when Adding listing of Responsibilities to the Tickler: " + e);
			}
		}
		if (ufd.getSaveButton() != null
			&& ufd.getTicklerDetail() != null
			&& ufd.getTicklerDetail().size() > 0) {
			try {
				ServiceTickler.updateDetail(ufd.getTicklerDetail());
			} catch (Exception e) {
				// Should Not be a Problem, everything is String
				System.out.println("Exception " + e);
			}
		}

	} 
	/* (non-Javadoc)
		 * @see com.treetop.servlets.BaseServlet#pageList(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
		 */
	protected void pageList(
		HttpServletRequest request,
		HttpServletResponse response) {
			
		
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

	/* 
	 * Used as a Utility - Will not Directly Call the JSP
	 */
	public void performTask(
		HttpServletRequest request,
		HttpServletResponse response) {

		String requestType = request.getParameter("requestType");
		if (requestType == null)
			requestType = "update";
		
		//-----------------------------------------------------------------
		//   UPDATE INFORMATION
		//-----------------------------------------------------------------
		if (requestType.equals("update")
  		    || requestType.equals("add")
			|| requestType.equals("copy")) {
			UpdFunctionDetail ufd = new UpdFunctionDetail();
			ufd = (UpdFunctionDetail) request.getAttribute("addFunction");
			if (ufd == null)
			{
			   ufd = new UpdFunctionDetail();
			   ufd.populate(request);
			   ufd.populateTickler(request, response);
			}
			request.setAttribute("updFunctionViewBean", ufd);
			pageUpd(request, response);
		}
 	    //-----------------------------------------------------------------
		//  LIST INFORMATION
		//-----------------------------------------------------------------
		if (requestType.equals("detail")) {
			DtlFunction dtlF = new DtlFunction();
			dtlF.populate(request);
			//dtlF.setGroup((String) request.getAttribute("group"));
			request.setAttribute("dtlFunctionViewBean", dtlF);
			pageDtl(request, response);
		}
		//-----------------------------------------------------------------
		// USE to Test Section and Page
		//  Go to the JSP
		//-----------------------------------------------------------------
		if (requestType.equals("detail")) {
		
		String sendTo = "/APP/Function/dtlFunction.jsp";
		
		try { // Send down to the JSP
			getServletConfig().getServletContext().getRequestDispatcher(
				sendTo).forward(
				request,
				response);
		} catch (Throwable theException) {
			theException.printStackTrace();
		}
			}
		return;
	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageInq(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageInq(
		HttpServletRequest request,
		HttpServletResponse response) {

	}

	/* (non-Javadoc)
	 * @see com.treetop.servlets.BaseServlet#pageDtl(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void pageDtl(
		HttpServletRequest request,
		HttpServletResponse response) {
		try
		{
		    DtlFunction dtlF = (DtlFunction) request.getAttribute("dtlFunctionViewBean");	
		   	
			TicklerFunctionDetail tfd = new TicklerFunctionDetail();
			tfd.setGroup(dtlF.getGroup());
			tfd.setNumber(new Integer(dtlF.getFunctionNumber()));
			tfd.setIdKeyValue(dtlF.getKeyValue());
			
			TicklerFunctionDetail detailOneFunction = ServiceTickler.buildTicklerDetail(tfd);
			dtlF.setDtlInformation(detailOneFunction);
			request.setAttribute("dtlFunctionViewBean", dtlF);			
		}
		catch(Exception e)
		{	
			System.out.println("Exception Caught in CtlFunction.pageDtl(request, response): " + e);		
		}
	}

}
