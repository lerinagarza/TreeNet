package com.treetop.utilities;

/**
 * A connection pool class which distributes available
 * connections upon request.
 * Creation date: (3/12/2009)
 * @author: William T Haile 
 */

import java.util.Stack;
import java.sql.*;


public class ConnectionStackSecurity {
	
	private static Stack connStack = null;
	static {
		try {
			
			// Register the JDBC driver.
			Class.forName("com.ibm.as400.access.AS400JDBCDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("com.treetop.Connection - missing driver. " + e); 
		}
	}

/**
 * ConnectionStack constructor comment.
 */
public ConnectionStackSecurity()
	throws java.sql.SQLException 
{
	init();
}

/**
 * Initialize the Stacks (build them)
 */
private void init()
	throws java.sql.SQLException 
{
	//  Build a Connection STACK.
	if (connStack == null)
	{
		connStack = new Stack();
		// Build Connections
		
		for (int i = 0; i < 5; i++) {
		// 10/28/11 TWalton - Change from IP address to lawson.treetop.com	
			//Connection c = DriverManager.getConnection(
			//	"jdbc:as400://10.6.100.3", "TNCON9", "tncon9");
			Connection c = DriverManager.getConnection(
					"jdbc:as400://lawson.treetop.com", "TNCON9", "tncon9");
			connStack.push(c);
		}
	}
}

/**
 * Shutdown the connection pool.
 **/
public static synchronized void closeAllConnections() {
   	
	Connection c;
	
    while (!connStack.empty()) {
       try {
          c = (Connection) connStack.pop();
          c.close();
          System.out.println("closed one from the Connection Stack");
       } catch (Exception e) {
          System.out.println("Error @ com.treetop.utilities.ConnectionStackSecurity.closeAllConnections(): " + e);
       }
    }      	
}
/**
 * Return a connection from the stack.
 * 
 */
  public static synchronized Connection getConnection() {
	  
	  Connection conn = null;

	  try
	  {	
		  // Initialize this Class 
		  ConnectionStackSecurity init = new ConnectionStackSecurity();
  		
		  //get connection
		  if (connStack.empty()) {
			  //log the error and return a null connection.
			  System.out.println("Error @ com.treetop.utilities.ConnectionStackSecurity.getConnection(): ");
			  System.out.println("No available connections. ");
			  Exception e = new Exception();
			  e.printStackTrace();
		  } else 
			  conn = (Connection) connStack.pop();
	  }
	  catch(Exception e) {
		  System.out.println("Error @ com.treetop.utilities.ConnectionStackSecurity.getConnection(): " + e);
	  }
  	
	  return conn;
  }
  
/**
 * Return an connection back to the stack
 */
  public static synchronized void returnConnection(Connection c) {
  	try
	{  // Initialize this Class 
  		ConnectionStackSecurity init = new ConnectionStackSecurity();
  		
  		//return connection
  		connStack.push(c);
	}
  	catch(Exception e)
	{
  		System.out.println("Error @ com.treetop.utilities.ConnectionStackSecurity.returnConnection(): " + e);
  		e.printStackTrace();
	}	
  }
  

}
