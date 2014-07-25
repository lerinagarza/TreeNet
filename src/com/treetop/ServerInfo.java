package com.treetop;

import javax.servlet.*;
import javax.servlet.http.*;
/**
 * This class is used to retrieve general server information.
 * serverInfo[0] = Server Name
 * serverInfo[1] = Server Path
 *
 * Creation date: (4/3/2003 1:07:08 PM)
 * @author: David M. Eisenheim
 */
 public class ServerInfo extends javax.servlet.http.HttpServlet {
	 	
/**
 * Retrieve server infomation. Server name and path.
 *
 * Creation date: (4/3/2003 1:20:45 PM)
 */
 public static String[] getNameAndPath(javax.servlet.http.HttpServletRequest request, 
						   			   javax.servlet.http.HttpServletResponse response) 
{
	String serverInfo[]  = new String[3];
		
	try	{
		
		//Retrieve server name and determine path.
		
		serverInfo[0] = request.getServerName();		
			serverInfo[1] = "web";
			serverInfo[2] = "TREENET.";
	
		if (serverInfo[0] == null)
			serverInfo[0] = "server1";
			
		
		if (serverInfo[0].equals("localhost"))
		{	
			serverInfo[1] = "web";
			serverInfo[2] = "TREENET.";
		}
			
	}

	catch (Exception e) {
		System.out.println("error at com.treetop.ServerInfo.getNameAndPath(req,rsp)" + e);
	}
	
	return serverInfo; 
}	
	
}
