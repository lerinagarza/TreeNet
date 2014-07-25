package com.treetop.servlets;

import com.treetop.data.*;
import com.treetop.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 * This Servlet will receive in:
 *    An Integer - template Number *(Associated to a Label Page)*
 *    An Integer = label Number to start on *(Associated to a Label Page)*
 *    A Vector of Strings - Strings may have tables in them, to
 *                              align and change font if wanted.
 *  
 * Creation date: (12/17/2003 11:44:38 AM)
 * @author: Teri Walton 
 */
public class CtlLabelTemplates extends javax.servlet.http.HttpServlet
{
	

/**
 * Determine if the information is correct.
 *  and what to do with it.  // Default in
 *   things if needed.
 *
 * Creation date: (12/17/2003 1:28:46 PM)
 * @return java.lang.String
 */
public static String displayTemplate(javax.servlet.http.HttpServletRequest request, 
	                                 javax.servlet.http.HttpServletResponse response,
	                                 Vector inLabels) 
{
	String message = "";
	try
	{
		HttpSession sess = request.getSession(true);
		sess.setMaxInactiveInterval(600);
        sess.setAttribute("labelInfo", inLabels);

        Labels thisInformation = new Labels();
	
	    thisInformation = (Labels) inLabels.elementAt(0);

	    if (thisInformation.getTemplateNumber().intValue() == 1)
	    {
			message = preview10PerSheet(request,
				                        response,
				                        inLabels);	
		    
	    }
    
        
		

	}
	catch(Exception e)
	{
		message = "There is a problem when trying to print these labels.\\n" +
		          "Please contact the help desk at ext. 1425.\\n" +
		          "CtlLabelTemplates(displayLabels(Vector): " + e;
		System.out.println("There is a problem when trying to print these labels.\\n" +
		          "Please contact the help desk at ext. 1425.\\n" +
		          "CtlLabelTemplates(displayLabels(Vector): " + e);
	}	
	return message;
}
/**
 * Process incoming HTTP GET requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

	performTask(request, response);

}
/**
 * Process incoming HTTP POST requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

	performTask(request, response);

}
/**
 * Returns the servlet info string.
 */
public String getServletInfo() {

	return super.getServletInfo();

}
/**
 * Initializes the servlet.
 */
public void init() {
	// insert code to initialize the servlet here

}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performTask(javax.servlet.http.HttpServletRequest request, 
	                    javax.servlet.http.HttpServletResponse response) 
{
	try
	{
		
		// Insert user code from here.

	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		//theException.printStackTrace();
	}
}
/**
 * Decides based upon the Template number which JSP to go to.
 *
 * Creation date: (12/17/2003 1:28:46 PM)
 * @return java.lang.String
 */
private static String preview10PerSheet(javax.servlet.http.HttpServletRequest request, 
	                                    javax.servlet.http.HttpServletResponse response,
	                                    Vector inLabels) 
{
	String message = "";
	try
	{

        //***** Go to the JSP *****//
        response.sendRedirect("/web/JSP/Label/preview10PerSheet.jsp");		

	}
	catch(Exception e)
	{
		System.out.println("There is a problem when trying to view these labels.\\n" +
		          "Please contact the help desk at ext. 1425.\\n" +
		          "CtlLabelTemplates.preview10PerSheet(Vector): " + e);
	}	
	return message;
}
}
