package com.treetop.utilities;

/**
 * A connection pool class which distributes available
 * connections upon request.
 * Creation date: (3/12/2009)
 * @author: William T Haile 
 */

import java.util.Stack;
import java.sql.*;


public class ConnectionStack4 {
	
	private static Stack connStack = null;
	static {
		try {
			
			// Register the JDBC driver.
			Class.forName("com.ibm.as400.access.AS400JDBCDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("com.treetop.ConnectionStack4 - missing driver. " + e); 
		}
	}

/**
 * ConnectionStack constructor comment.
 */
public ConnectionStack4()
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
		// 10/28/11 TWalton - Change to use Lawson.treetop.com instead of IP Address	
		//	Connection c = DriverManager.getConnection(
		//		"jdbc:as400://10.6.100.3", "TNCON4", "tncon4");
			Connection c = DriverManager.getConnection(
					"jdbc:as400://lawson.treetop.com", "TNCON4", "tncon4");
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
          System.out.println("Error @ com.treetop.utilities.ConnectionStack4.closeAllConnections(): " + e);
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
		  ConnectionStack4 init = new ConnectionStack4();
  		
		  //get connection
		  if (connStack.empty()) {
			  //log the error and return a null connection.
			  System.out.println("Error @ com.treetop.utilities.ConnectionStack4.getConnection(): ");
			  System.out.println("No available connections. ");
			  Exception e = new Exception();
			  e.printStackTrace();
		  } else 
			  conn = (Connection) connStack.pop();
	  }
	  catch(Exception e) {
		  System.out.println("Error @ com.treetop.utilities.ConnectionStack4.getConnection(): " + e);
	  }
  	
	  return conn;
  }
  
/**
 * Return an connection back to the stack
 */
  public static synchronized void returnConnection(Connection c) {
  	try
	{  // Initialize this Class 
  		ConnectionStack4 init = new ConnectionStack4();
  		
  		//return connection
  		connStack.push(c);
	}
  	catch(Exception e)
	{
  		System.out.println("Error @ com.treetop.utilities.ConnectionStack4.returnConnection(): " + e);
  		e.printStackTrace();
	}	
  }
  

}
