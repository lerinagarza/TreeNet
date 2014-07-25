package com.treetop.app.treenet;
import java.util.*;
import javax.servlet.*;
import com.treetop.utilities.*;


/**
 * Determine Results Sets upon request. 
 * Creation date: (04/2/2009)
 * William T Haile: 
 */
public class ServerStartUp extends javax.servlet.http.HttpServlet {

	
/**
 * Process incoming HTTP GET requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doGet(javax.servlet.http.HttpServletRequest request, 
	              javax.servlet.http.HttpServletResponse response)
	              
	
	   throws javax.servlet.ServletException, java.io.IOException {

	performTask(request, response);

}

/**
 * Process incoming HTTP POST requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doPost(javax.servlet.http.HttpServletRequest request, 
	               javax.servlet.http.HttpServletResponse response)
	               

       throws javax.servlet.ServletException, java.io.IOException {

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
public void init() 
	throws ServletException { 
	
   //***** *******************************************
   // Establish a as400 connection
   //************************************************
   try {
	   System.out.println("Starting ServerStartUp Servlet");
	   NetSecurity ns = new NetSecurity();
   }

   catch (Exception e) {
	   System.out.println(e);
   }
   
}
   

/**
 * 
 */
public void performTask(javax.servlet.http.HttpServletRequest request,
	                    javax.servlet.http.HttpServletResponse response)
{
	
	try
	{


	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		 theException.printStackTrace();
	
	}
	return;
}
	
	
}


