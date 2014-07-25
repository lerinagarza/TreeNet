/*
 * Created on Nov 17, 2005
 *
 * Key Values is a Ctl (Servlet to access URL's and Comments)
 * 
 * Currently this servlet does NOT go directly TO or FROM Any JSP's
 * 
 * Called from OTHER servlets
 * 
 */
 
package com.treetop.app;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.businessobjects.KeyValue;
import com.treetop.services.ServiceKeyValue;
import com.treetop.servlets.BaseServlet;
import com.treetop.utilities.html.HTMLHelpersLinks;

/**
 * @author twalto
 *
 */
public class CtlKeyValues extends BaseServlet {
	/* 
	 * ADD RECORDS
	 */
	private void pageUpdAdd(HttpServletRequest request,
							  HttpServletResponse response) {

		UpdKeyValues ukv = (UpdKeyValues) request.getAttribute("updKeyValues");
		StringBuffer problemMessage = new StringBuffer();
		try {
			//*****************************************************************
			//  ADD
			//**------------------------------
			//**  URL
			if (ukv.getUrlKeyValuesAdd() != null
				&& ukv.getUrlKeyValuesAdd().getValue() != null
				&& !ukv.getUrlKeyValuesAdd().getValue().trim().equals("")) {
				try {
	//			System.out.println("Display URL before Add:" + ukv.getUrlKeyValuesAdd().getValue());
					ServiceKeyValue.addKeyValue(ukv.getUrlKeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
					System.out.println("Exception " + e);
				}
			}
			//**----------------------------------
			//**  URL 1
			if (ukv.getUrl1KeyValuesAdd() != null
				&& ukv.getUrl1KeyValuesAdd().getValue() != null
				&& !ukv.getUrl1KeyValuesAdd().getValue().trim().equals("")) {
				
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl1KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 2
			if (ukv.getUrl2KeyValuesAdd() != null
				&& ukv.getUrl2KeyValuesAdd().getValue() != null
				&& !ukv.getUrl2KeyValuesAdd().getValue().trim().equals("")) {
		//		Stop here... see what my options are... about NOT adding it
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl2KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 3
			if (ukv.getUrl3KeyValuesAdd() != null
				&& ukv.getUrl3KeyValuesAdd().getValue() != null
				&& !ukv.getUrl3KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl3KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 4
			if (ukv.getUrl4KeyValuesAdd() != null
				&& ukv.getUrl4KeyValuesAdd().getValue() != null
				&& !ukv.getUrl4KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl4KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 5
			if (ukv.getUrl5KeyValuesAdd() != null
				&& ukv.getUrl5KeyValuesAdd().getValue() != null
				&& !ukv.getUrl5KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl5KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 6
			if (ukv.getUrl6KeyValuesAdd() != null
				&& ukv.getUrl6KeyValuesAdd().getValue() != null
				&& !ukv.getUrl6KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl6KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 7
			if (ukv.getUrl7KeyValuesAdd() != null
				&& ukv.getUrl7KeyValuesAdd().getValue() != null
				&& !ukv.getUrl7KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl7KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 8
			if (ukv.getUrl8KeyValuesAdd() != null
				&& ukv.getUrl8KeyValuesAdd().getValue() != null
				&& !ukv.getUrl8KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl8KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 9
			if (ukv.getUrl9KeyValuesAdd() != null
				&& ukv.getUrl9KeyValuesAdd().getValue() != null
				&& !ukv.getUrl9KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl9KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 10
			if (ukv.getUrl10KeyValuesAdd() != null
				&& ukv.getUrl10KeyValuesAdd().getValue() != null
				&& !ukv.getUrl10KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl10KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 11
			if (ukv.getUrl11KeyValuesAdd() != null
				&& ukv.getUrl11KeyValuesAdd().getValue() != null
				&& !ukv.getUrl11KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl11KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 12
			if (ukv.getUrl12KeyValuesAdd() != null
				&& ukv.getUrl12KeyValuesAdd().getValue() != null
				&& !ukv.getUrl12KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl12KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 13
			if (ukv.getUrl13KeyValuesAdd() != null
				&& ukv.getUrl13KeyValuesAdd().getValue() != null
				&& !ukv.getUrl13KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl13KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 14
			if (ukv.getUrl14KeyValuesAdd() != null
				&& ukv.getUrl14KeyValuesAdd().getValue() != null
				&& !ukv.getUrl14KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl14KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 15
			if (ukv.getUrl15KeyValuesAdd() != null
				&& ukv.getUrl15KeyValuesAdd().getValue() != null
				&& !ukv.getUrl15KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl15KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 16
			if (ukv.getUrl16KeyValuesAdd() != null
				&& ukv.getUrl16KeyValuesAdd().getValue() != null
				&& !ukv.getUrl16KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl16KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 17
			if (ukv.getUrl17KeyValuesAdd() != null
				&& ukv.getUrl17KeyValuesAdd().getValue() != null
				&& !ukv.getUrl17KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl17KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 18
			if (ukv.getUrl18KeyValuesAdd() != null
				&& ukv.getUrl18KeyValuesAdd().getValue() != null
				&& !ukv.getUrl18KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl18KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**----------------------------------
			//**  URL 19
			if (ukv.getUrl19KeyValuesAdd() != null
				&& ukv.getUrl19KeyValuesAdd().getValue() != null
				&& !ukv.getUrl19KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getUrl19KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT
			if (ukv.getCommentKeyValuesAdd() != null
				&& ukv.getCommentKeyValuesAdd().getValue() != null
				&& !ukv.getCommentKeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getCommentKeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 1
			if (ukv.getComment1KeyValuesAdd() != null
				&& ukv.getComment1KeyValuesAdd().getValue() != null
				&& !ukv.getComment1KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment1KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 2
			if (ukv.getComment2KeyValuesAdd() != null
				&& ukv.getComment2KeyValuesAdd().getValue() != null
				&& !ukv.getComment2KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment2KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 3
			if (ukv.getComment3KeyValuesAdd() != null
				&& ukv.getComment3KeyValuesAdd().getValue() != null
				&& !ukv.getComment3KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment3KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 4
			if (ukv.getComment4KeyValuesAdd() != null
				&& ukv.getComment4KeyValuesAdd().getValue() != null
				&& !ukv.getComment4KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment4KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 5
			if (ukv.getComment5KeyValuesAdd() != null
				&& ukv.getComment5KeyValuesAdd().getValue() != null
				&& !ukv.getComment5KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment5KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 6
			if (ukv.getComment6KeyValuesAdd() != null
				&& ukv.getComment6KeyValuesAdd().getValue() != null
				&& !ukv.getComment6KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment6KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 7
			if (ukv.getComment7KeyValuesAdd() != null
				&& ukv.getComment7KeyValuesAdd().getValue() != null
				&& !ukv.getComment7KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment7KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 8
			if (ukv.getComment8KeyValuesAdd() != null
				&& ukv.getComment8KeyValuesAdd().getValue() != null
				&& !ukv.getComment8KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment8KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 9
			if (ukv.getComment9KeyValuesAdd() != null
				&& ukv.getComment9KeyValuesAdd().getValue() != null
				&& !ukv.getComment9KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment9KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 10
			if (ukv.getComment10KeyValuesAdd() != null
				&& ukv.getComment10KeyValuesAdd().getValue() != null
				&& !ukv.getComment10KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment10KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 11
			if (ukv.getComment11KeyValuesAdd() != null
				&& ukv.getComment11KeyValuesAdd().getValue() != null
				&& !ukv.getComment11KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment11KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 12
			if (ukv.getComment12KeyValuesAdd() != null
				&& ukv.getComment12KeyValuesAdd().getValue() != null
				&& !ukv.getComment12KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment12KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 13
			if (ukv.getComment13KeyValuesAdd() != null
				&& ukv.getComment13KeyValuesAdd().getValue() != null
				&& !ukv.getComment13KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment13KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 14
			if (ukv.getComment14KeyValuesAdd() != null
				&& ukv.getComment14KeyValuesAdd().getValue() != null
				&& !ukv.getComment14KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment14KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 15
			if (ukv.getComment15KeyValuesAdd() != null
				&& ukv.getComment15KeyValuesAdd().getValue() != null
				&& !ukv.getComment15KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment15KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 16
			if (ukv.getComment16KeyValuesAdd() != null
				&& ukv.getComment16KeyValuesAdd().getValue() != null
				&& !ukv.getComment16KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment16KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 17
			if (ukv.getComment17KeyValuesAdd() != null
				&& ukv.getComment17KeyValuesAdd().getValue() != null
				&& !ukv.getComment17KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment17KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 18
			if (ukv.getComment18KeyValuesAdd() != null
				&& ukv.getComment18KeyValuesAdd().getValue() != null
				&& !ukv.getComment18KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment18KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 19
			if (ukv.getComment19KeyValuesAdd() != null
				&& ukv.getComment19KeyValuesAdd().getValue() != null
				&& !ukv.getComment19KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment19KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 20
			if (ukv.getComment20KeyValuesAdd() != null
				&& ukv.getComment20KeyValuesAdd().getValue() != null
				&& !ukv.getComment20KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment20KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 21
			if (ukv.getComment21KeyValuesAdd() != null
				&& ukv.getComment21KeyValuesAdd().getValue() != null
				&& !ukv.getComment21KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment21KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 22
			if (ukv.getComment22KeyValuesAdd() != null
				&& ukv.getComment22KeyValuesAdd().getValue() != null
				&& !ukv.getComment22KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment22KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 23
			if (ukv.getComment23KeyValuesAdd() != null
				&& ukv.getComment23KeyValuesAdd().getValue() != null
				&& !ukv.getComment23KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment23KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 24
			if (ukv.getComment24KeyValuesAdd() != null
				&& ukv.getComment24KeyValuesAdd().getValue() != null
				&& !ukv.getComment24KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment24KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 25
			if (ukv.getComment25KeyValuesAdd() != null
				&& ukv.getComment25KeyValuesAdd().getValue() != null
				&& !ukv.getComment25KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment25KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 26
			if (ukv.getComment26KeyValuesAdd() != null
				&& ukv.getComment26KeyValuesAdd().getValue() != null
				&& !ukv.getComment26KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment26KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 27
			if (ukv.getComment27KeyValuesAdd() != null
				&& ukv.getComment27KeyValuesAdd().getValue() != null
				&& !ukv.getComment27KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment27KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 28
			if (ukv.getComment28KeyValuesAdd() != null
				&& ukv.getComment28KeyValuesAdd().getValue() != null
				&& !ukv.getComment28KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment28KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
			//**------------------------------
			//**  COMMENT 29
			if (ukv.getComment29KeyValuesAdd() != null
				&& ukv.getComment29KeyValuesAdd().getValue() != null
				&& !ukv.getComment29KeyValuesAdd().getValue().trim().equals("")) {
				try {
					ServiceKeyValue.addKeyValue(ukv.getComment29KeyValuesAdd());
				} catch (Exception e) {
					// Should Not be a Problem, everything is String
				}
			}
		} catch (Exception e) {
			System.out.println("CtlKeyValues.pageUpdAdd-Exception " + e);
		}
		request.setAttribute("updKeyValues", ukv);

	} /* (non-Javadoc)
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
			|| requestType.equals("copy")
			|| requestType.equals("updateLot")
			|| requestType.equals("updFormula") 
			|| requestType.equals("updMethod")
			|| requestType.equals("updProcedure")
			|| requestType.equals("updPolicy")
			|| requestType.equals("updSpec")
			|| requestType.equals("updAvailFruit")
			|| requestType.equals("updSchedFruit") 
			|| requestType.equals("updSchedTransfer")
			|| requestType.equals("updateFinish")) {
			UpdKeyValues ukv = new UpdKeyValues();
			ukv.populate(request);
			if (ukv.getEnvironment().trim().equals(""))
				ukv.setEnvironment("PRD");
			ukv.populateURL(request, response);
			ukv.populateComment(request, response);
			// Added code 6/27/12 - TWalton
			if (requestType.equals("updSchedFruit") ||
				requestType.equals("updSchedTransfer"))
			{
				if (ukv.getCommentKeyValuesAdd() != null &&
					ukv.getCommentKeyValuesAdd().getEntryType().trim().equals(""))
				{
					if (request.getAttribute("EntryType") != null &&
						!((String) request.getAttribute("EntryType")).trim().equals(""))
					{
					   KeyValue kv = ukv.getCommentKeyValuesAdd();
					   kv.setEntryType(((String) request.getAttribute("EntryType")).trim());
					   if (request.getAttribute("Key1") != null &&
							!((String) request.getAttribute("Key1")).trim().equals(""))
						   kv.setKey1(((String) request.getAttribute("Key1")).trim());
					   ukv.setCommentKeyValuesAdd(kv);
					}
//				}else{
//					System.out.println("Stop and see");
				}				
			}
			if (requestType.equals("updFormula"))
			{
				ukv.populateComment1(request, response);
				ukv.populateComment2(request, response);
				ukv.populateComment3(request, response);
				ukv.populateComment4(request, response);
				ukv.populateComment5(request, response);
				ukv.populateComment6(request, response);
			}
			if (
				requestType.equals("update") ||	
				requestType.equals("updMethod") ||
				requestType.equals("updProcedure") ||
				requestType.equals("updPolicy") ||
				requestType.equals("updSpec"))
			{
				ukv.populateComment1(request, response);
				ukv.populateComment2(request, response);
				ukv.populateComment3(request, response);
				ukv.populateComment4(request, response);
				ukv.populateComment5(request, response);
				ukv.populateComment6(request, response);
				ukv.populateComment7(request, response);
				ukv.populateComment8(request, response);
				ukv.populateComment9(request, response);
				ukv.populateComment10(request, response);
				ukv.populateComment11(request, response);
				ukv.populateComment12(request, response);
				ukv.populateComment13(request, response);
				ukv.populateComment14(request, response);
				ukv.populateComment15(request, response);
				ukv.populateComment16(request, response);
				ukv.populateComment17(request, response);
				ukv.populateComment18(request, response);
				ukv.populateComment19(request, response);
				ukv.populateComment20(request, response);
				ukv.populateComment21(request, response);
				ukv.populateComment22(request, response);
				ukv.populateComment23(request, response);
				ukv.populateComment24(request, response);
				ukv.populateComment25(request, response);
				ukv.populateComment26(request, response);
				ukv.populateComment27(request, response);
				ukv.populateComment28(request, response);
				ukv.populateComment29(request, response);
				
				ukv.populateURL1(request, response);
				ukv.populateURL2(request, response);
				ukv.populateURL3(request, response);
				ukv.populateURL4(request, response);
				ukv.populateURL5(request, response);
				ukv.populateURL6(request, response);
				ukv.populateURL7(request, response);
				ukv.populateURL8(request, response);
				ukv.populateURL9(request, response);
				ukv.populateURL10(request, response);
				ukv.populateURL11(request, response);
				ukv.populateURL12(request, response);
				ukv.populateURL13(request, response);
				ukv.populateURL14(request, response);
				ukv.populateURL15(request, response);
				ukv.populateURL16(request, response);
				ukv.populateURL17(request, response);
				ukv.populateURL18(request, response);
				ukv.populateURL19(request, response);
			}
		
			encodeHtmlEntities(ukv);
			request.setAttribute("updKeyValues", ukv);
			pageUpd(request, response);
		}
		//-----------------------------------------------------------------
		//   Display IFS Information ONLY
		//-----------------------------------------------------------------
		if (requestType.toLowerCase().equals("ifsonly")) {
			String folderPath = request.getParameter("folderPath");
			if (folderPath == null)
			  folderPath = "";
			//------Get Images in the folder for this GTIN
//** CHANGES			
//			String imageList[] = HTMLHelpersLinks.getDirectoryFromPath("\\\\10.6.100.6\\x\\ProductInfo\\" + folderPath + "");
//		 12/20/06 tw - Could not seem to make this work, will work on it at a later time
//       Changed the view to go to the Folder as a link instead of displaying what is in the folder.		
//			String imageList[] = HTMLHelpersLinks.getDirectoryFromPathAS400("/Network/ProductInfo/" + folderPath + "/");			
//			request.setAttribute("imageList", imageList);		
			request.setAttribute("displayImagePath", ("X:\\ProductInfo\\" + folderPath + "\\"));
			String sendTo = "/APP/Utilities/displayIFSOnly.jsp";
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
		}		
		return;
	}
	
	private void encodeHtmlEntities(UpdKeyValues ukv) {
		
		for (Field field : UpdKeyValues.class.getFields()) {
			
			if (field.getName().contains("comment")) {
				
				try {
					
					if (field.getType() == Vector.class) {
						Vector kvs = (Vector) field.get(ukv);
					
						for (Object obj : kvs) {
							KeyValue kv  = (KeyValue) obj;
							String value = kv.getValue().replaceAll("\u2122", "&trade;");
							kv.setValue(value);
						}
					}
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				
				
			}
			
		}
		
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

	}
	/* 
	 * ADD - UPDATE - DELETE RECORDS
	 */
	protected void pageUpd(
		HttpServletRequest request,
		HttpServletResponse response) {

		UpdKeyValues ukv = (UpdKeyValues) request.getAttribute("updKeyValues");
		StringBuffer problemMessage = new StringBuffer();
		try {
			if (ukv.getSaveButton() != null ||
				ukv.getSubmit() != null) {
				pageUpdAdd(request, response);
				pageUpdUpdate(request, response);
				pageUpdDelete(request, response);
				}
			} catch (Exception e) {
				System.out.println("Exception " + e);
			}
			request.setAttribute("updKeyValues", ukv);
	
		}
	/* 
	 * UPDATE RECORDS
	 */
	private void pageUpdUpdate(
		HttpServletRequest request,
		HttpServletResponse response) {
	
		UpdKeyValues ukv = (UpdKeyValues) request.getAttribute("updKeyValues");
		StringBuffer problemMessage = new StringBuffer();
		try {
			//*****************************************************************
			//  UPDATE
			//**------------------------------
			//**  URL
			if (ukv.getUrlKeyValuesCount() != null
				&& new Integer(ukv.getUrlKeyValuesCount()).intValue() > 0
				&& ukv.getUrlKeyValuesUpdate() != null
				&& ukv.getUrlKeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrlKeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrlKeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 1
			if (ukv.getUrl1KeyValuesCount() != null
				&& new Integer(ukv.getUrl1KeyValuesCount()).intValue() > 0
				&& ukv.getUrl1KeyValuesUpdate() != null
				&& ukv.getUrl1KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl1KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl1KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 2
			if (ukv.getUrl2KeyValuesCount() != null
				&& new Integer(ukv.getUrl2KeyValuesCount()).intValue() > 0
				&& ukv.getUrl2KeyValuesUpdate() != null
				&& ukv.getUrl2KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl2KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl2KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 3
			if (ukv.getUrl3KeyValuesCount() != null
				&& new Integer(ukv.getUrl3KeyValuesCount()).intValue() > 0
				&& ukv.getUrl3KeyValuesUpdate() != null
				&& ukv.getUrl3KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl3KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl3KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 4
			if (ukv.getUrl4KeyValuesCount() != null
				&& new Integer(ukv.getUrl4KeyValuesCount()).intValue() > 0
				&& ukv.getUrl4KeyValuesUpdate() != null
				&& ukv.getUrl4KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl4KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl4KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 5
			if (ukv.getUrl5KeyValuesCount() != null
				&& new Integer(ukv.getUrl5KeyValuesCount()).intValue() > 0
				&& ukv.getUrl5KeyValuesUpdate() != null
				&& ukv.getUrl5KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl5KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl5KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 6
			if (ukv.getUrl6KeyValuesCount() != null
				&& new Integer(ukv.getUrl6KeyValuesCount()).intValue() > 0
				&& ukv.getUrl6KeyValuesUpdate() != null
				&& ukv.getUrl6KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl6KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl6KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 7
			if (ukv.getUrl7KeyValuesCount() != null
				&& new Integer(ukv.getUrl7KeyValuesCount()).intValue() > 0
				&& ukv.getUrl7KeyValuesUpdate() != null
				&& ukv.getUrl7KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl7KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl7KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 8
			if (ukv.getUrl8KeyValuesCount() != null
				&& new Integer(ukv.getUrl8KeyValuesCount()).intValue() > 0
				&& ukv.getUrl8KeyValuesUpdate() != null
				&& ukv.getUrl8KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl8KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl8KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 9
			if (ukv.getUrl9KeyValuesCount() != null
				&& new Integer(ukv.getUrl9KeyValuesCount()).intValue() > 0
				&& ukv.getUrl9KeyValuesUpdate() != null
				&& ukv.getUrl9KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl9KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl9KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 10
			if (ukv.getUrl10KeyValuesCount() != null
				&& new Integer(ukv.getUrl10KeyValuesCount()).intValue() > 0
				&& ukv.getUrl10KeyValuesUpdate() != null
				&& ukv.getUrl10KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl10KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl10KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 11
			if (ukv.getUrl11KeyValuesCount() != null
				&& new Integer(ukv.getUrl11KeyValuesCount()).intValue() > 0
				&& ukv.getUrl11KeyValuesUpdate() != null
				&& ukv.getUrl11KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl11KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl11KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 12
			if (ukv.getUrl12KeyValuesCount() != null
				&& new Integer(ukv.getUrl12KeyValuesCount()).intValue() > 0
				&& ukv.getUrl12KeyValuesUpdate() != null
				&& ukv.getUrl12KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl12KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl12KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 13
			if (ukv.getUrl13KeyValuesCount() != null
				&& new Integer(ukv.getUrl13KeyValuesCount()).intValue() > 0
				&& ukv.getUrl13KeyValuesUpdate() != null
				&& ukv.getUrl13KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl13KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl13KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 14
			if (ukv.getUrl14KeyValuesCount() != null
				&& new Integer(ukv.getUrl14KeyValuesCount()).intValue() > 0
				&& ukv.getUrl14KeyValuesUpdate() != null
				&& ukv.getUrl14KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl14KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl14KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 15
			if (ukv.getUrl15KeyValuesCount() != null
				&& new Integer(ukv.getUrl15KeyValuesCount()).intValue() > 0
				&& ukv.getUrl15KeyValuesUpdate() != null
				&& ukv.getUrl15KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl15KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl15KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 16
			if (ukv.getUrl16KeyValuesCount() != null
				&& new Integer(ukv.getUrl16KeyValuesCount()).intValue() > 0
				&& ukv.getUrl16KeyValuesUpdate() != null
				&& ukv.getUrl16KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl16KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl16KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 17
			if (ukv.getUrl17KeyValuesCount() != null
				&& new Integer(ukv.getUrl17KeyValuesCount()).intValue() > 0
				&& ukv.getUrl17KeyValuesUpdate() != null
				&& ukv.getUrl17KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl17KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl17KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 18
			if (ukv.getUrl18KeyValuesCount() != null
				&& new Integer(ukv.getUrl18KeyValuesCount()).intValue() > 0
				&& ukv.getUrl18KeyValuesUpdate() != null
				&& ukv.getUrl18KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl18KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl18KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  URL 19
			if (ukv.getUrl19KeyValuesCount() != null
				&& new Integer(ukv.getUrl19KeyValuesCount()).intValue() > 0
				&& ukv.getUrl19KeyValuesUpdate() != null
				&& ukv.getUrl19KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getUrl19KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getUrl19KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					//	System.out.println("Exception " + e);
					}
				}
			}
			//**------------------------------
			//**  COMMENT
			if (ukv.getCommentKeyValuesCount() != null
				&& new Integer(ukv.getCommentKeyValuesCount()).intValue() > 0
				&& ukv.getCommentKeyValuesUpdate() != null
				&& ukv.getCommentKeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getCommentKeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getCommentKeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  COMMENT 1
			if (ukv.getComment1KeyValuesCount() != null
				&& new Integer(ukv.getComment1KeyValuesCount()).intValue() > 0
				&& ukv.getComment1KeyValuesUpdate() != null
				&& ukv.getComment1KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment1KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment1KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  COMMENT 2
			if (ukv.getComment2KeyValuesCount() != null
				&& new Integer(ukv.getComment2KeyValuesCount()).intValue() > 0
				&& ukv.getComment2KeyValuesUpdate() != null
				&& ukv.getComment2KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment2KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment2KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 3
			if (ukv.getComment3KeyValuesCount() != null
				&& new Integer(ukv.getComment3KeyValuesCount()).intValue() > 0
				&& ukv.getComment3KeyValuesUpdate() != null
				&& ukv.getComment3KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment3KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment3KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 4
			if (ukv.getComment4KeyValuesCount() != null
				&& new Integer(ukv.getComment4KeyValuesCount()).intValue() > 0
				&& ukv.getComment4KeyValuesUpdate() != null
				&& ukv.getComment4KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment4KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment4KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 5
			if (ukv.getComment5KeyValuesCount() != null
				&& new Integer(ukv.getComment5KeyValuesCount()).intValue() > 0
				&& ukv.getComment5KeyValuesUpdate() != null
				&& ukv.getComment5KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment5KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment5KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 6
			if (ukv.getComment6KeyValuesCount() != null
				&& new Integer(ukv.getComment6KeyValuesCount()).intValue() > 0
				&& ukv.getComment6KeyValuesUpdate() != null
				&& ukv.getComment6KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment6KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment6KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 7
			if (ukv.getComment7KeyValuesCount() != null
				&& new Integer(ukv.getComment7KeyValuesCount()).intValue() > 0
				&& ukv.getComment7KeyValuesUpdate() != null
				&& ukv.getComment7KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment7KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment7KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 8
			if (ukv.getComment8KeyValuesCount() != null
				&& new Integer(ukv.getComment8KeyValuesCount()).intValue() > 0
				&& ukv.getComment8KeyValuesUpdate() != null
				&& ukv.getComment8KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment8KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment8KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 9
			if (ukv.getComment9KeyValuesCount() != null
				&& new Integer(ukv.getComment9KeyValuesCount()).intValue() > 0
				&& ukv.getComment9KeyValuesUpdate() != null
				&& ukv.getComment9KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment9KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment9KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 10
			if (ukv.getComment10KeyValuesCount() != null
				&& new Integer(ukv.getComment10KeyValuesCount()).intValue() > 0
				&& ukv.getComment10KeyValuesUpdate() != null
				&& ukv.getComment10KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment10KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment10KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 11
			if (ukv.getComment11KeyValuesCount() != null
				&& new Integer(ukv.getComment11KeyValuesCount()).intValue() > 0
				&& ukv.getComment11KeyValuesUpdate() != null
				&& ukv.getComment11KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment11KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment11KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 12
			if (ukv.getComment12KeyValuesCount() != null
				&& new Integer(ukv.getComment12KeyValuesCount()).intValue() > 0
				&& ukv.getComment12KeyValuesUpdate() != null
				&& ukv.getComment12KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment12KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment12KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 13
			if (ukv.getComment13KeyValuesCount() != null
				&& new Integer(ukv.getComment13KeyValuesCount()).intValue() > 0
				&& ukv.getComment13KeyValuesUpdate() != null
				&& ukv.getComment13KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment13KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment13KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 14
			if (ukv.getComment14KeyValuesCount() != null
				&& new Integer(ukv.getComment14KeyValuesCount()).intValue() > 0
				&& ukv.getComment14KeyValuesUpdate() != null
				&& ukv.getComment14KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment14KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment14KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 15
			if (ukv.getComment15KeyValuesCount() != null
				&& new Integer(ukv.getComment15KeyValuesCount()).intValue() > 0
				&& ukv.getComment15KeyValuesUpdate() != null
				&& ukv.getComment15KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment15KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment15KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 16
			if (ukv.getComment16KeyValuesCount() != null
				&& new Integer(ukv.getComment16KeyValuesCount()).intValue() > 0
				&& ukv.getComment16KeyValuesUpdate() != null
				&& ukv.getComment16KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment16KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment16KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 17
			if (ukv.getComment17KeyValuesCount() != null
				&& new Integer(ukv.getComment17KeyValuesCount()).intValue() > 0
				&& ukv.getComment17KeyValuesUpdate() != null
				&& ukv.getComment17KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment17KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment17KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 18
			if (ukv.getComment18KeyValuesCount() != null
				&& new Integer(ukv.getComment18KeyValuesCount()).intValue() > 0
				&& ukv.getComment18KeyValuesUpdate() != null
				&& ukv.getComment18KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment18KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment18KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 19
			if (ukv.getComment19KeyValuesCount() != null
				&& new Integer(ukv.getComment19KeyValuesCount()).intValue() > 0
				&& ukv.getComment19KeyValuesUpdate() != null
				&& ukv.getComment19KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment19KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment19KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}	
			//**------------------------------
			//**  COMMENT 20
			if (ukv.getComment20KeyValuesCount() != null
				&& new Integer(ukv.getComment20KeyValuesCount()).intValue() > 0
				&& ukv.getComment20KeyValuesUpdate() != null
				&& ukv.getComment20KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment20KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment20KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 21
			if (ukv.getComment21KeyValuesCount() != null
				&& new Integer(ukv.getComment21KeyValuesCount()).intValue() > 0
				&& ukv.getComment21KeyValuesUpdate() != null
				&& ukv.getComment21KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment21KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment21KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 22
			if (ukv.getComment22KeyValuesCount() != null
				&& new Integer(ukv.getComment22KeyValuesCount()).intValue() > 0
				&& ukv.getComment22KeyValuesUpdate() != null
				&& ukv.getComment22KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment22KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment22KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 23
			if (ukv.getComment23KeyValuesCount() != null
				&& new Integer(ukv.getComment23KeyValuesCount()).intValue() > 0
				&& ukv.getComment23KeyValuesUpdate() != null
				&& ukv.getComment23KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment23KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment23KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 24
			if (ukv.getComment24KeyValuesCount() != null
				&& new Integer(ukv.getComment24KeyValuesCount()).intValue() > 0
				&& ukv.getComment24KeyValuesUpdate() != null
				&& ukv.getComment24KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment24KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment24KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}	
			//**------------------------------
			//**  COMMENT 25
			if (ukv.getComment25KeyValuesCount() != null
				&& new Integer(ukv.getComment25KeyValuesCount()).intValue() > 0
				&& ukv.getComment25KeyValuesUpdate() != null
				&& ukv.getComment25KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment25KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment25KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 26
			if (ukv.getComment26KeyValuesCount() != null
				&& new Integer(ukv.getComment26KeyValuesCount()).intValue() > 0
				&& ukv.getComment26KeyValuesUpdate() != null
				&& ukv.getComment26KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment26KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment26KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 27
			if (ukv.getComment27KeyValuesCount() != null
				&& new Integer(ukv.getComment27KeyValuesCount()).intValue() > 0
				&& ukv.getComment27KeyValuesUpdate() != null
				&& ukv.getComment27KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment27KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment27KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 28
			if (ukv.getComment28KeyValuesCount() != null
				&& new Integer(ukv.getComment28KeyValuesCount()).intValue() > 0
				&& ukv.getComment28KeyValuesUpdate() != null
				&& ukv.getComment28KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment28KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment28KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}		
			//**------------------------------
			//**  COMMENT 29
			if (ukv.getComment29KeyValuesCount() != null
				&& new Integer(ukv.getComment29KeyValuesCount()).intValue() > 0
				&& ukv.getComment29KeyValuesUpdate() != null
				&& ukv.getComment29KeyValuesUpdate().size() > 0) {
				for (int x = 0; x < ukv.getComment29KeyValuesUpdate().size(); x++) {
					try {
						ServiceKeyValue.updateKeyValue((KeyValue) ukv.getComment29KeyValuesUpdate().elementAt(x));
					} catch (Exception e) {
						System.out.println("Exception: " + e);
						// Should Not be a Problem, everything is String
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception " + e);
		}
		request.setAttribute("updKeyValues", ukv);
	}
	/* 
	 * DELETE RECORDS
	 */
	private void pageUpdDelete(
		HttpServletRequest request,
		HttpServletResponse response) {
	
		UpdKeyValues ukv = (UpdKeyValues) request.getAttribute("updKeyValues");
		StringBuffer problemMessage = new StringBuffer();
		try {
			//*****************************************************************
			//  DELETE
			//**------------------------------
			//**  URL
			if (ukv.getUrlKeyValuesCount() != null
				&& new Integer(ukv.getUrlKeyValuesCount()).intValue() > 0
				&& ukv.getUrlKeyValuesDelete() != null
				&& ukv.getUrlKeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrlKeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrlKeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 1
			if (ukv.getUrl1KeyValuesCount() != null
				&& new Integer(ukv.getUrl1KeyValuesCount()).intValue() > 0
				&& ukv.getUrl1KeyValuesDelete() != null
				&& ukv.getUrl1KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl1KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl1KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 2
			if (ukv.getUrl2KeyValuesCount() != null
				&& new Integer(ukv.getUrl2KeyValuesCount()).intValue() > 0
				&& ukv.getUrl2KeyValuesDelete() != null
				&& ukv.getUrl2KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl2KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl2KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 3
			if (ukv.getUrl3KeyValuesCount() != null
				&& new Integer(ukv.getUrl3KeyValuesCount()).intValue() > 0
				&& ukv.getUrl3KeyValuesDelete() != null
				&& ukv.getUrl3KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl3KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl3KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 4
			if (ukv.getUrl4KeyValuesCount() != null
				&& new Integer(ukv.getUrl4KeyValuesCount()).intValue() > 0
				&& ukv.getUrl4KeyValuesDelete() != null
				&& ukv.getUrl4KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl4KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl4KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 5
			if (ukv.getUrl5KeyValuesCount() != null
				&& new Integer(ukv.getUrl5KeyValuesCount()).intValue() > 0
				&& ukv.getUrl5KeyValuesDelete() != null
				&& ukv.getUrl5KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl5KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl5KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 6
			if (ukv.getUrl6KeyValuesCount() != null
				&& new Integer(ukv.getUrl6KeyValuesCount()).intValue() > 0
				&& ukv.getUrl6KeyValuesDelete() != null
				&& ukv.getUrl6KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl6KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl6KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 7
			if (ukv.getUrl7KeyValuesCount() != null
				&& new Integer(ukv.getUrl7KeyValuesCount()).intValue() > 0
				&& ukv.getUrl7KeyValuesDelete() != null
				&& ukv.getUrl7KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl7KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl7KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 8
			if (ukv.getUrl8KeyValuesCount() != null
				&& new Integer(ukv.getUrl8KeyValuesCount()).intValue() > 0
				&& ukv.getUrl8KeyValuesDelete() != null
				&& ukv.getUrl8KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl8KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl8KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 9
			if (ukv.getUrl9KeyValuesCount() != null
				&& new Integer(ukv.getUrl9KeyValuesCount()).intValue() > 0
				&& ukv.getUrl9KeyValuesDelete() != null
				&& ukv.getUrl9KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl9KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl9KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 10
			if (ukv.getUrl10KeyValuesCount() != null
				&& new Integer(ukv.getUrl10KeyValuesCount()).intValue() > 0
				&& ukv.getUrl10KeyValuesDelete() != null
				&& ukv.getUrl10KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl10KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl10KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 11
			if (ukv.getUrl11KeyValuesCount() != null
				&& new Integer(ukv.getUrl11KeyValuesCount()).intValue() > 0
				&& ukv.getUrl11KeyValuesDelete() != null
				&& ukv.getUrl11KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl11KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl11KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 12
			if (ukv.getUrl12KeyValuesCount() != null
				&& new Integer(ukv.getUrl12KeyValuesCount()).intValue() > 0
				&& ukv.getUrl12KeyValuesDelete() != null
				&& ukv.getUrl12KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl12KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl12KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 13
			if (ukv.getUrl13KeyValuesCount() != null
				&& new Integer(ukv.getUrl13KeyValuesCount()).intValue() > 0
				&& ukv.getUrl13KeyValuesDelete() != null
				&& ukv.getUrl13KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl13KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl13KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 14
			if (ukv.getUrl14KeyValuesCount() != null
				&& new Integer(ukv.getUrl14KeyValuesCount()).intValue() > 0
				&& ukv.getUrl14KeyValuesDelete() != null
				&& ukv.getUrl14KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl14KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl14KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 15
			if (ukv.getUrl15KeyValuesCount() != null
				&& new Integer(ukv.getUrl15KeyValuesCount()).intValue() > 0
				&& ukv.getUrl15KeyValuesDelete() != null
				&& ukv.getUrl15KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl15KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl15KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 16
			if (ukv.getUrl16KeyValuesCount() != null
				&& new Integer(ukv.getUrl16KeyValuesCount()).intValue() > 0
				&& ukv.getUrl16KeyValuesDelete() != null
				&& ukv.getUrl16KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl16KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl16KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
//			**------------------------------
			//**  URL 17
			if (ukv.getUrl17KeyValuesCount() != null
				&& new Integer(ukv.getUrl17KeyValuesCount()).intValue() > 0
				&& ukv.getUrl17KeyValuesDelete() != null
				&& ukv.getUrl17KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl17KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl17KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 18
			if (ukv.getUrl18KeyValuesCount() != null
				&& new Integer(ukv.getUrl18KeyValuesCount()).intValue() > 0
				&& ukv.getUrl18KeyValuesDelete() != null
				&& ukv.getUrl18KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl18KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl18KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  URL 19
			if (ukv.getUrl19KeyValuesCount() != null
				&& new Integer(ukv.getUrl19KeyValuesCount()).intValue() > 0
				&& ukv.getUrl19KeyValuesDelete() != null
				&& ukv.getUrl19KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getUrl19KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getUrl19KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//**------------------------------
			//**  COMMENT
			if (ukv.getCommentKeyValuesCount() != null
				&& new Integer(ukv.getCommentKeyValuesCount()).intValue() > 0
				&& ukv.getCommentKeyValuesDelete() != null
				&& ukv.getCommentKeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getCommentKeyValuesDelete().size();	x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getCommentKeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 1
			if (ukv.getComment1KeyValuesCount() != null
				&& new Integer(ukv.getComment1KeyValuesCount()).intValue() > 0
				&& ukv.getComment1KeyValuesDelete() != null
				&& ukv.getComment1KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment1KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment1KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 2
			if (ukv.getComment2KeyValuesCount() != null
				&& new Integer(ukv.getComment2KeyValuesCount()).intValue() > 0
				&& ukv.getComment2KeyValuesDelete() != null
				&& ukv.getComment2KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment2KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment2KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 3
			if (ukv.getComment3KeyValuesCount() != null
				&& new Integer(ukv.getComment3KeyValuesCount()).intValue() > 0
				&& ukv.getComment3KeyValuesDelete() != null
				&& ukv.getComment3KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment3KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment3KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 4
			if (ukv.getComment4KeyValuesCount() != null
				&& new Integer(ukv.getComment4KeyValuesCount()).intValue() > 0
				&& ukv.getComment4KeyValuesDelete() != null
				&& ukv.getComment4KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment4KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment4KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 5
			if (ukv.getComment5KeyValuesCount() != null
				&& new Integer(ukv.getComment5KeyValuesCount()).intValue() > 0
				&& ukv.getComment5KeyValuesDelete() != null
				&& ukv.getComment5KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment5KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment5KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 6
			if (ukv.getComment6KeyValuesCount() != null
				&& new Integer(ukv.getComment6KeyValuesCount()).intValue() > 0
				&& ukv.getComment6KeyValuesDelete() != null
				&& ukv.getComment6KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment6KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment6KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 7
			if (ukv.getComment7KeyValuesCount() != null
				&& new Integer(ukv.getComment7KeyValuesCount()).intValue() > 0
				&& ukv.getComment7KeyValuesDelete() != null
				&& ukv.getComment7KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment7KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment7KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 8
			if (ukv.getComment8KeyValuesCount() != null
				&& new Integer(ukv.getComment8KeyValuesCount()).intValue() > 0
				&& ukv.getComment8KeyValuesDelete() != null
				&& ukv.getComment8KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment8KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment8KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 9
			if (ukv.getComment9KeyValuesCount() != null
				&& new Integer(ukv.getComment9KeyValuesCount()).intValue() > 0
				&& ukv.getComment9KeyValuesDelete() != null
				&& ukv.getComment9KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment9KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment9KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 10
			if (ukv.getComment10KeyValuesCount() != null
				&& new Integer(ukv.getComment10KeyValuesCount()).intValue() > 0
				&& ukv.getComment10KeyValuesDelete() != null
				&& ukv.getComment10KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment10KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment10KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 11
			if (ukv.getComment11KeyValuesCount() != null
				&& new Integer(ukv.getComment11KeyValuesCount()).intValue() > 0
				&& ukv.getComment11KeyValuesDelete() != null
				&& ukv.getComment11KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment11KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment11KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 12
			if (ukv.getComment12KeyValuesCount() != null
				&& new Integer(ukv.getComment12KeyValuesCount()).intValue() > 0
				&& ukv.getComment12KeyValuesDelete() != null
				&& ukv.getComment12KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment12KeyValuesDelete().size();x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment12KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 13
			if (ukv.getComment13KeyValuesCount() != null
				&& new Integer(ukv.getComment13KeyValuesCount()).intValue() > 0
				&& ukv.getComment13KeyValuesDelete() != null
				&& ukv.getComment13KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment13KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment13KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 14
			if (ukv.getComment14KeyValuesCount() != null
				&& new Integer(ukv.getComment14KeyValuesCount()).intValue() > 0
				&& ukv.getComment14KeyValuesDelete() != null
				&& ukv.getComment14KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment14KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment14KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 15
			if (ukv.getComment15KeyValuesCount() != null
				&& new Integer(ukv.getComment15KeyValuesCount()).intValue() > 0
				&& ukv.getComment15KeyValuesDelete() != null
				&& ukv.getComment15KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment15KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment15KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 16
			if (ukv.getComment16KeyValuesCount() != null
				&& new Integer(ukv.getComment16KeyValuesCount()).intValue() > 0
				&& ukv.getComment16KeyValuesDelete() != null
				&& ukv.getComment16KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment16KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment16KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 17
			if (ukv.getComment17KeyValuesCount() != null
				&& new Integer(ukv.getComment17KeyValuesCount()).intValue() > 0
				&& ukv.getComment17KeyValuesDelete() != null
				&& ukv.getComment17KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment17KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment17KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 18
			if (ukv.getComment18KeyValuesCount() != null
				&& new Integer(ukv.getComment18KeyValuesCount()).intValue() > 0
				&& ukv.getComment18KeyValuesDelete() != null
				&& ukv.getComment18KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment18KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment18KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 19
			if (ukv.getComment19KeyValuesCount() != null
				&& new Integer(ukv.getComment19KeyValuesCount()).intValue() > 0
				&& ukv.getComment19KeyValuesDelete() != null
				&& ukv.getComment19KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment19KeyValuesDelete().size();x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment19KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 20
			if (ukv.getComment20KeyValuesCount() != null
				&& new Integer(ukv.getComment20KeyValuesCount()).intValue() > 0
				&& ukv.getComment20KeyValuesDelete() != null
				&& ukv.getComment20KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment20KeyValuesDelete().size();x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment20KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 21
			if (ukv.getComment21KeyValuesCount() != null
				&& new Integer(ukv.getComment21KeyValuesCount()).intValue() > 0
				&& ukv.getComment21KeyValuesDelete() != null
				&& ukv.getComment21KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment21KeyValuesDelete().size();x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment21KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 22
			if (ukv.getComment22KeyValuesCount() != null
				&& new Integer(ukv.getComment22KeyValuesCount()).intValue() > 0
				&& ukv.getComment22KeyValuesDelete() != null
				&& ukv.getComment22KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment22KeyValuesDelete().size();x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment22KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 23
			if (ukv.getComment23KeyValuesCount() != null
				&& new Integer(ukv.getComment23KeyValuesCount()).intValue() > 0
				&& ukv.getComment23KeyValuesDelete() != null
				&& ukv.getComment23KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment23KeyValuesDelete().size();x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment23KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 24
			if (ukv.getComment24KeyValuesCount() != null
				&& new Integer(ukv.getComment24KeyValuesCount()).intValue() > 0
				&& ukv.getComment24KeyValuesDelete() != null
				&& ukv.getComment24KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment24KeyValuesDelete().size();x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment24KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 25
			if (ukv.getComment25KeyValuesCount() != null
				&& new Integer(ukv.getComment25KeyValuesCount()).intValue() > 0
				&& ukv.getComment25KeyValuesDelete() != null
				&& ukv.getComment25KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment25KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment25KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 26
			if (ukv.getComment26KeyValuesCount() != null
				&& new Integer(ukv.getComment26KeyValuesCount()).intValue() > 0
				&& ukv.getComment26KeyValuesDelete() != null
				&& ukv.getComment26KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment26KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment26KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 27
			if (ukv.getComment27KeyValuesCount() != null
				&& new Integer(ukv.getComment27KeyValuesCount()).intValue() > 0
				&& ukv.getComment27KeyValuesDelete() != null
				&& ukv.getComment27KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment27KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment27KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 28
			if (ukv.getComment28KeyValuesCount() != null
				&& new Integer(ukv.getComment28KeyValuesCount()).intValue() > 0
				&& ukv.getComment28KeyValuesDelete() != null
				&& ukv.getComment28KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment28KeyValuesDelete().size(); x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment28KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//-----------------------------------------
			//**  COMMENT 29
			if (ukv.getComment29KeyValuesCount() != null
				&& new Integer(ukv.getComment29KeyValuesCount()).intValue() > 0
				&& ukv.getComment29KeyValuesDelete() != null
				&& ukv.getComment29KeyValuesDelete().size() > 0) {
				for (int x = 0; x < ukv.getComment29KeyValuesDelete().size();x++) {
					try {
						ServiceKeyValue.deleteKeyValue((KeyValue) ukv.getComment29KeyValuesDelete().elementAt(x));
					} catch (Exception e) {
						// Should Not be a Problem, everything is String
					}
				}
			}
			//*****************************************************************
		} catch (Exception e) {
			System.out.println("Exception " + e);
		}
		request.setAttribute("updKeyValues", ukv);

	}

}
