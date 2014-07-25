package com.treetop.controller.operationsUtilities;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;


import com.treetop.SessionVariables;
import com.treetop.controller.BaseController;
import com.treetop.services.ServiceOperationsUtilities;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.viewbeans.CommonRequestBean;

public class CtlOperationsUtilities extends BaseController {

	private String dtlUtilities(HttpServletRequest request, HttpServletResponse response) {

		JSONObject data = null;
		
		try {
			
			InqOperationsUtilities iou = new InqOperationsUtilities(request);
			
			if (!iou.getErrorMessage().equals("")) {
				response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, iou.getErrorMessage());
			} else {
				
				CommonRequestBean crb = new CommonRequestBean("PRD",iou.getWarehouse(),iou.getWeekBeginningDate());
				
				data = ServiceOperationsUtilities.getUtilitiesByPlantYearWeek(crb);
				
				//Generate the calendar day and date
				JSONArray calendar = generateCalendar(iou.getWeekBeginningDate());
				data.put("calendar", calendar);
				
				String fiscalWeek = UtilityDateTime.getDateFormatM3Fiscal(iou.getWeekBeginningDate()).getM3FiscalWeek();
				data.put("fiscalWeek", fiscalWeek);
				
				
				
			}

		} catch (Exception e) {
			try {
				response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Unable to retrieve data at this time.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		

		return data.toString();
		
	}
	
	private String updUtilities(HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject respondData = new JSONObject();
		
		try {
			
			InqOperationsUtilities iou = new InqOperationsUtilities(request);
			
			if (!iou.getErrorMessage().equals("")) {
				response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, iou.getErrorMessage());
			} else {
				JSONObject updateData = new JSONObject(iou.getJson());

				
				
				CommonRequestBean crb = new CommonRequestBean(
						iou.getEnvironment(), 
						iou.getWarehouse(), 
						iou.getWeekBeginningDate());
				
				String user = SessionVariables.getSessionttiProfile(request, response);
				if (user == null || user.equals("")) {
					user = "TSTUSR";
				}
				
				crb.setUser(user);
				
				crb.setDate(UtilityDateTime.getSystemDate().getDateFormatyyyyMMdd());
				
				ServiceOperationsUtilities.updateUtilitiesByPlantYearWeek(crb, updateData);
				
			}

			
		} catch (Exception e) {
			try {
				response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Unable to update at this time.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return respondData.toString();
		
	}
	
	private JSONArray generateCalendar(String beginningDate) throws Exception {
		JSONArray calendar = new JSONArray();
		
		String[] weekdaysShort = new DateFormatSymbols().getShortWeekdays(); // Get day names
		String[] weekdaysLong = new DateFormatSymbols().getWeekdays(); // Get day names
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat dateShort = new SimpleDateFormat("M/d");
		SimpleDateFormat dateLong = new SimpleDateFormat("MM/dd/yyyy");
		c.setTime(sdf.parse(beginningDate));
		
		for (int i=0; i<7; i++) {
			
			JSONObject day = new JSONObject();
			
			day.put("day", weekdaysLong[c.get(Calendar.DAY_OF_WEEK)]);
			day.put("dayShort", weekdaysShort[c.get(Calendar.DAY_OF_WEEK)]);
			
			day.put("date", dateLong.format(c.getTime()));
			day.put("dateShort", dateShort.format(c.getTime()));
						
			calendar.put(day);
			
			c.add(Calendar.DATE, 1);
		}
		
		return calendar;
	}
	
	private String inqUtilities(HttpServletRequest request) {
		return "/view/operationsUtilites/updUtilities.jsp";
	}
	
	@Override
	protected boolean isSecurityEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String defaultRequest(HttpServletRequest request) {
		return inqUtilities(request);
	}

	@Override
	protected String securityUrl(HttpServletRequest request, String requestType) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
