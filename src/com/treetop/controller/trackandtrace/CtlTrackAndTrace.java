package com.treetop.controller.trackandtrace;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.app.transaction.InqTransaction;
import com.treetop.controller.BaseController;
import com.treetop.services.ServiceTrackAndTrace;

public class CtlTrackAndTrace extends BaseController {

	
	private static String inqTrackAndTrace(HttpServletRequest request, HttpServletResponse response) {
		
		InqTransaction it = new InqTransaction();
		
		it.setInqUser(SessionVariables.getSessionttiProfile(request, response));
		it.populate(request);
	
		if (!it.getGoButton().equals("")) {
			//NOT the first time on this page  Do stuff here
			it.validate();

			if(it.getDisplayErrors().equals("")) {
				try {
					ServiceTrackAndTrace.submitTrackAndTrace(it);
					
					String fileName = it.buildOutputFileName() + ".pdf";
					
					ServletOutputStream outStream = response.getOutputStream();
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
					
					File f = it.getBean().getSpoolFileOutput();
					
					byte[] byteBuffer = new byte[4096];
			        DataInputStream in = new DataInputStream(new FileInputStream(f));
			        
			        
			        // reads the file's bytes and writes them to the response stream
			        int length   = 0;
			        while ((in != null) && ((length = in.read(byteBuffer)) != -1))
			        {
			            outStream.write(byteBuffer,0,length);
			        }
					
			        in.close();
			        outStream.close();
			        
			        //delete the file after it has been streamed out
			        f.delete();
			        
					return null;
				} catch (Exception e) {
					it.setDisplayMessage(e.toString());
				}
			}
			
		}
		
		request.setAttribute("inqViewBean", it);
		
		return "view/trackAndTrace/inqTrackAndTrace.jsp";
	}
	
	private static String inqFruitToShipping(HttpServletRequest request, HttpServletResponse response) {
		return inqTrackAndTrace(request, response);
	}
	private static String inqProductionDayBack(HttpServletRequest request, HttpServletResponse response) {
		return inqTrackAndTrace(request, response);
	}
	private static String inqProductionDayForward(HttpServletRequest request, HttpServletResponse response) {
		return inqTrackAndTrace(request, response);
	}
	private static String inqProductionDayForwardOS(HttpServletRequest request, HttpServletResponse response) {
		return inqTrackAndTrace(request, response);
	}
	private static String inqSingleIngredientForward(HttpServletRequest request, HttpServletResponse response) {
		return inqTrackAndTrace(request, response);
	}
	
	
	@Override
	protected boolean isSecurityEnabled() {
		return false;
	}

	@Override
	protected String defaultRequest(HttpServletRequest request) {
		return "view/trackAndTrace/inqTrackAndTrace.jsp";
	}

	@Override
	protected String securityUrl(HttpServletRequest request, String requestType) {
		String urlAddress = "";
		
		if (requestType.equals("inqSingleIngredientForward"))
				urlAddress = "/web/CtlTransaction?requestType=inqSingleIngredientForward";
		if (requestType.equals("inqProductionDayBack"))
				urlAddress = "/web/CtlTransaction?requestType=inqProductionDayBack";
		if (requestType.equals("inqProductionDayForward"))
				urlAddress = "/web/CtlTransaction?requestType=inqProductionDayForward";
		if (requestType.equals("inqProductionDayForwardOS"))
			urlAddress = "/web/CtlTransaction?requestType=inqProductionDayForwardOS";
		if (requestType.equals("inqFruitToShipping"))
			urlAddress = "/web/CtlTransaction?requestType=inqFruitToShipping";
		
		return urlAddress;
	}

}
