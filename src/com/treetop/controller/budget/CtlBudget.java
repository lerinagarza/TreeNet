package com.treetop.controller.budget;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lawson.api.BUS100MIAddBudgetLines;
import com.lawson.api.BUS100MIDelBudgetLines;
import com.treetop.businessobjects.AccountString;
import com.treetop.controller.BaseController;
import com.treetop.services.ServiceBudget;
import com.treetop.utilities.GeneralUtility;

public class CtlBudget extends BaseController {
	
	private String updBudget(HttpServletRequest request) {
		
		InqBudget inqBudget = new InqBudget();
		inqBudget.populate(request);

		Hashtable<String,Object> params = new Hashtable<String,Object>();
		
		try {
			params = GeneralUtility.getParamsFromMultipart(request);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		if (!params.isEmpty()) {
			inqBudget.setEnvironment((params.get("environment")) 		== null ? "" : (String) params.get("environment"));
			inqBudget.setBudgetNumber((params.get("budgetNumber")) 		== null ? "" : (String) params.get("budgetNumber"));
			inqBudget.setBudgetVersion((params.get("budgetVersion"))	== null ? "" : (String) params.get("budgetVersion"));
			inqBudget.setDepartment((params.get("department")) 			== null ? "" : (String) params.get("department"));
			inqBudget.setSubmit((params.get("submit"))					== null ? "" : (String) params.get("submit"));
			inqBudget.setFilePath((params.get("uploadFile_Path"))		== null ? "" : (String) params.get("uploadFile_Path"));
		}
		
	
		
		if (!inqBudget.getSubmit().equals("")) {
			
			inqBudget.validate();
			
			if (!inqBudget.getErrorMessage().equals("")) {
				//invalid inputs
				
				//do nothing, just return
				
			} else {
				//inputs are valid
	
				try {
					@SuppressWarnings("unchecked")
					Vector<String> data = (Vector<String>) params.get("uploadFile");
					
					StringBuffer error = new StringBuffer();
					
					try {
					
						String authorization = request.getHeader("Authorization");
						if (authorization != null) {
							inqBudget.setAuthorization(authorization);
						}
						
						ServiceBudget.upload(inqBudget, data);
						
					} catch (Exception e) {
						error.append("Error in upload process  " + e);
					}
					
					
					
					Vector<AccountString> invalidAccountStrings = inqBudget.getBean().getInvalidAccountStrings();
					if (!invalidAccountStrings.isEmpty()) {
						if (!error.toString().equals("")) {
							error.append("<br>");
						}
						error.append("Error in account string");
					}
					
					String addError = "";
					Vector<BUS100MIAddBudgetLines> addLines = inqBudget.getBean().getAddBudgetLines();
					for (BUS100MIAddBudgetLines line : addLines) {
						if (line.getResponse().startsWith("NOK")) {
							addError = "Error adding budget lines";
						}
					}
					if (!addError.equals("")) {
						if (!error.toString().equals("")) {
							error.append("<br>");
						}
						error.append(addError);
					}
					
					String delError = "";
					Vector<BUS100MIDelBudgetLines> delLines = inqBudget.getBean().getDelBudgetLines();
					for (BUS100MIDelBudgetLines line : delLines) {
						if (line.getResponse().startsWith("NOK")) {
							delError = "Error deleting budget lines";
						}
					}
					if (!delError.equals("")) {
						if (!error.toString().equals("")) {
							error.append("<br>");
						}
						error.append(delError);
					}
					
					
					if (!error.toString().equals("")) {
						inqBudget.setErrorMessage(error.toString());
					}
	
					
				} catch (Exception e) {
					System.err.println(e);
				}
	
			}	// end if valid
			
		}	//end if sumbit
		
		request.setAttribute("inqBudget", inqBudget);
		return "view/budget/updBudget.jsp";
	}
	
	private static String updForecastMiss(HttpServletRequest request, HttpServletResponse response) {
		InqBudget inqBudget = new InqBudget();
		inqBudget.populate(request);
		

		if (!inqBudget.getSubmit().equals("")) {
			
			inqBudget.validate();
			
			if (!inqBudget.getErrorMessage().equals("")) {
				//invalid inputs
				
				//do nothing, just return
				
			} else {
				// inputs are valid
				
				try {
					ServiceBudget.adjustForecastMiss(inqBudget);
				} catch (Exception e) {
					inqBudget.setErrorMessage(e.toString());
					System.err.println(e);
				}
	
			}
		}
		
		
		
		request.setAttribute("inqBudget", inqBudget);
		return "view/budget/updBudget.jsp";
	}
	
	private static String inqDepartment(HttpServletRequest request) {
		InqBudget inqBudget = new InqBudget();
		inqBudget.populate(request);
		
		
		
			
		if (!inqBudget.getSubmit().equals("")) {
			
			inqBudget.validate();
			
			if (!inqBudget.getErrorMessage().equals("")) {
				//invalid inputs
				
				//do nothing, just return
				
			} else {
					//inputs are valid
				try {
					ServiceBudget.listBudgetDepartmentDefinitions(inqBudget);
				} catch (Exception e) {
					inqBudget.setErrorMessage("Error listing Budget Department Definitions.   " + e);
				}
				
			}
			
		}
		
		
		
		request.setAttribute("inqBudget", inqBudget);
		return "view/budget/inqDepartment.jsp";
	}
	
	private static void previewForecastMiss(HttpServletRequest request, HttpServletResponse response) {
		InqBudget inqBudget = new InqBudget();
		inqBudget.populate(request);
		inqBudget.validate();
		try {
			File file = ServiceBudget.previewForecastMiss(inqBudget);
			String fileName = "forecastMiss" + 
				"_F" + inqBudget.getBudgetNumber() +
				"_" + inqBudget.getBudgetVersion() +
				"_AdjFor" + inqBudget.getForecastMiss() +
				".csv";
			
			
			ServletOutputStream outStream = response.getOutputStream();
	        response.setContentType("application/octet-stream");
	        response.setContentLength((int)file.length());
	        
	        // sets HTTP header
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	        
	        byte[] byteBuffer = new byte[4096];
	        DataInputStream in = new DataInputStream(new FileInputStream(file));
	        
	        
	        // reads the file's bytes and writes them to the response stream
	        int length   = 0;
	        while ((in != null) && ((length = in.read(byteBuffer)) != -1))
	        {
	            outStream.write(byteBuffer,0,length);
	        }
	        
	        in.close();
	        outStream.close();
			
			
		} catch (Exception e) {
			System.err.println(e);
		}
		
		
	}
	
	private static void getUploadTemplate(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			StringBuffer data = new StringBuffer();
			
			data.append("DIM1,");
			data.append("DIM2,");
			data.append("DIM3,");
			data.append("DIM4,");
			data.append("DIM5,");
			data.append("DIM6,");
			data.append("DIM7,");
			
			data.append("PER01,");
			data.append("PER02,");
			data.append("PER03,");
			data.append("PER04,");
			data.append("PER05,");
			data.append("PER06,");
			data.append("PER07,");
			data.append("PER08,");
			data.append("PER09,");
			data.append("PER10,");
			data.append("PER11,");
			data.append("PER12");


			File file = File.createTempFile(String.valueOf(System.currentTimeMillis()), ".csv");
			
			String fileName = "budgetUploadTemplate.csv";
			
			FileWriter fw = new FileWriter(file);
			fw.append(data);
			fw.flush();
			

	        ServletOutputStream outStream = response.getOutputStream();
	        response.setContentType("application/octet-stream");
	        response.setContentLength((int)file.length());
	        
	        // sets HTTP header
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	        
	        byte[] byteBuffer = new byte[4096];
	        DataInputStream in = new DataInputStream(new FileInputStream(file));
	        
	        
	        // reads the file's bytes and writes them to the response stream
	        int length   = 0;
	        while ((in != null) && ((length = in.read(byteBuffer)) != -1))
	        {
	            outStream.write(byteBuffer,0,length);
	        }
	        
	        in.close();
	        outStream.close();
			
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}
	


	@Override
	protected boolean isSecurityEnabled() {
		return false;
	}

	@Override
	protected String defaultRequest(HttpServletRequest request) {
		return updBudget(request);
	}

	@Override
	protected String securityUrl(HttpServletRequest request, String requestType) {
		return null;
	}

}
