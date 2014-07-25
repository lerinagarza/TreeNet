package com.treetop.servlets;

import java.math.*;
import com.ibm.as400.access.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.treetop.utilities.ConnectionStack;
/**
 * VerifyLogOn1 servlet - validate Grower pin and Sequence Numbers.
 * Creation date: (5/7/2002 3:53:56 PM)
 * @author: William T Haile
 */
public class VerifyLogOn1 extends javax.servlet.http.HttpServlet {

//	Connection conn;
//	Statement stmt = null;
/**
 * Process incoming HTTP GET requests 
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doGet(javax.servlet.http.HttpServletRequest request, 
	              javax.servlet.http.HttpServletResponse response)

       throws javax.servlet.ServletException, IOException {

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

       throws javax.servlet.ServletException, IOException {

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
	
   //************************************************
   // Establish a as400 connection
   //************************************************
//   try {
//	   Class.forName("com.ibm.as400.access.AS400JDBCDriver");
 //  }

  // catch (ClassNotFoundException e) {
//	   System.out.println(e);
 //  }

//   try {
	   //conn = DriverManager.getConnection("jdbc:as400:10.6.100.3","DAUSER","WEB230502");
//   	   conn = ConnectionStack.getConnection();
 //  }

 //  catch (Exception e) {
//	   System.out.println("Error retrieving connection: " + e);
 //  }

}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performTask(javax.servlet.http.HttpServletRequest request, 
	                    javax.servlet.http.HttpServletResponse response) 

       throws ServletException,
              IOException                                               
{

	try {
	   	//*****************************************************************
		// Set content type, get writer to output stream
		//*****************************************************************
		response.setContentType("text/html");
		HttpSession ssn = request.getSession(true);
		
		String destination = request.getParameter("destination");
		
		if (destination == null || destination.equals("0"))
			destination = "/web/JSP/GwrMktRts/inqGwrMktRts.jsp";
			
		if (destination.equals("1"))
			destination = "/web/JSP/GwrMktRts/dtlMktRtsReport.jsp";
			
		
		//*****************************************************************
		// if the servlet uses the RequestDispatcher on HTTP.response then
		// the response object must NOT be set to the getWriter method.
		//*****************************************************************
		//PrintWriter out = response.getWriter();

		//*****************************************************************
		// retrieve incoming parameters, validate values 
		//*****************************************************************

		ssn.setAttribute("EMAIL", "helpdesk@treetop.com");
		
		String sessionGRESEQ  = request.getParameter("seq");
		

        //********************************************************************
		// Remove dash "-" from password in position 5 if it exists
		//********************************************************************

		if (sessionGRESEQ.length() > 6)
		{
			if (sessionGRESEQ.substring(4,5).equals("-"))
			{
				String tempx = sessionGRESEQ.substring(0,4);
				String tempy = sessionGRESEQ.substring(5,7);
				sessionGRESEQ = tempx + tempy;
			}	
	    }

		
		java.math.BigDecimal bd = new java.math.BigDecimal("0");

		try {
			bd = new java.math.BigDecimal(sessionGRESEQ);
		} catch (NumberFormatException nfe) {
			bd = new java.math.BigDecimal("1");
		}

		String pinNumber = request.getParameter("pin");

		String fieldRepNumber = "22";

		//*****************************************************************
		// retrieve pin number and test
		//*****************************************************************
		
		if (pinNumber.equals("999999")) {
			ssn.setAttribute("USERTYPE", "FieldRep");
		} else {
			ssn.setAttribute("USERTYPE", "Grower");
		}

		
		if (pinNumber.equals(null))
			pinNumber = "x";

		if (pinNumber.trim().equals("")) 
			pinNumber = "x";
		
		
		//*****************************************************************
		// validate sign-on values
		//*****************************************************************
		Connection conn = null;
		try {
			boolean recordfound = false;
		    conn = ConnectionStack.getConnection();
		    
		    Statement stmt = conn.createStatement();

		    ResultSet rs = stmt.executeQuery(
			   "SELECT * FROM DBPRD.GRPEMAST WHERE GRESEQ =" + bd + 
			   " AND GREPIN =" + "'" + pinNumber + "'");
			                                 		      
		    if (rs.next()) 
		    {			    				    		    
				ssn.setAttribute("SEQUENCENBR", sessionGRESEQ);
			    ssn.setAttribute("FIELDREP", rs.getString("GREREP"));
			    fieldRepNumber = rs.getString("GREREP");
			    ssn.setAttribute("FIELDREPNBR", rs.getBigDecimal("GREREP"));
			    ssn.setAttribute("SEQNBR", rs.getBigDecimal("GRESEQ"));
			    ssn.setAttribute("PINNBR", rs.getString("GREPIN"));
			    
			    recordfound = true;
			     			    
		    } else {
		      
				if (pinNumber.equals("999999")) 
				{    
					ResultSet rs2 = stmt.executeQuery(
			          "SELECT * FROM DBPRD.GRPEMAST WHERE GRESEQ =" + bd ); 

					if (rs2.next()) 
					{	    
			       		ssn.setAttribute("SEQUENCENBR", sessionGRESEQ);
			        	ssn.setAttribute("FIELDREP", rs2.getString("GREREP"));
			        	fieldRepNumber = rs2.getString("GREREP");
			        	ssn.setAttribute("FIELDREPNBR", rs2.getBigDecimal("GREREP"));
			        	ssn.setAttribute("SEQNBR", rs2.getBigDecimal("GRESEQ"));
			        	ssn.setAttribute("PINNBR", "999999");
			    
						recordfound = true;			                 
					}
				   
				} else {
					
                	recordfound = false;       
			    }   

			}
		      
			if (recordfound) 
			{
				if ("x" == "y")
				{
				ResultSet rs3 = stmt.executeQuery(
			        "SELECT * FROM DBLIB.EMPDEHDR " +
			        "INNER JOIN DBLIB.EMPEEDTL ON EMEEVTUNIQ = EMDEVTUNIQ " +
			        "INNER JOIN DBLIB.EMPHIDTL ON EMHINDUNIQ = EMESENDTO " +
			        "WHERE EMDEVENT = 'MR_SALE_NOTIFY' " +  
			        " AND EMDKEY1 = " + "'" + fieldRepNumber + "'");

				String email  = "";
			    String email1 = "";
			    String email2 = "";
			    String email3 = "";
			        
			    if (rs3.next()) 
			    {
					email1 = rs3.getString("EMHEMAIL");
				    email = email1;
				    ssn.setAttribute("EMAIL", email);

				    if (rs3.next()) 
				    {
						email2 = rs3.getString("EMHEMAIL");
					    email = email1 + ";" + email2;
					    ssn.setAttribute("EMAIL", email);
					    
					    if (rs3.next()) 
					    {
							email3 = rs3.getString("EMHEMAIL");
					        email = email1 + ";" + email2 + ";" + email3;
					        ssn.setAttribute("EMAIL", email);
					    }
					}
				}
				}
			       
			    System.out.println("email = " + (String) ssn.getAttribute("EMAIL"));
			    response.sendRedirect(destination);
			    //response.sendRedirect("http://develop.treetop.com/Dev/JSP/GwrMktRts/inqGwrMktRts.jsp");
			    //response.sendRedirect("http://127.0.0.1:8080/JSP/GwrMktRts/inqGwrMktRts.jsp");
		 	    return;
		 	      
			} else 
			{      
				response.sendRedirect("/web/JSP/GwrMktRts/logOnGwrMktRts.jsp?failed=yes");
                //response.sendRedirect("http://develop.treetop.com/Dev/JSP/GwrMktRts/logOnGwrMktRts.jsp?failed=yes");
			    //response.sendRedirect("http://127.0.0.1:8080/JSP/GwrMktRts/logOnGwrMktRts.jsp?failed=yes");
			    return;
			}
  
		} catch(SQLException sqle) {
			System.err.println("An SQL exception occured: " + sqle);
		}
		finally
		{
			if (conn != null)
				ConnectionStack.returnConnection(conn);
		}
 
		 
	}
	catch(Throwable theException)
	{
		// uncomment the following line when unexpected exceptions
		// are occuring to aid in debugging the problem.
		theException.printStackTrace();
		System.err.println("An exception occured: ");
	}
}
}
