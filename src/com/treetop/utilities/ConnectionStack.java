package com.treetop.utilities;

/**
 * A connection pool class which distributes available
 * connections upon request.
 * Creation date: (9/26/2003 8:52:42 AM)
 * @author: William T Haile 
 * Added Stack 2 2/5/07 - TWalton
 * Changed 3/7/08 - TWalton
 * 		Renamed from ConnectionPool, moved from the com.treetop folder
 * 		Added AS400 stack
 * 		Made the get and return methods static
 */

import java.util.Stack;
import java.sql.*;

import com.ibm.as400.access.AS400;


public class ConnectionStack {
	// This one is the Movex BOX
	private static Stack connStack = null;
	
	private static Stack as400Stack = null;
	static {
		try {
			
			// Register the JDBC driver.
			Class.forName("com.ibm.as400.access.AS400JDBCDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("com.treetop.ConnectionStack - missing driver. " 
								+ e);
		}
	}

/**
 * ConnectionStack constructor comment.
 */
public ConnectionStack()
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
	//  Build an Connection STACK for the NEW Box
	if (connStack == null)
	{
		connStack = new Stack();
      // Build 25 Connections
		for (int i = 0; i < 25; i++) {
			// 10/28/11 TWalton Change to point to lawson.treetop.com instead of IP Address
		//	Connection c = DriverManager.getConnection(
		//		"jdbc:as400://10.6.100.3", "TNCON2", "tncon2");
			Connection c = DriverManager.getConnection(
					"jdbc:as400://lawson.treetop.com", "TNCON2", "tncon2");
			connStack.push(c);
		}
	}
	// Build Stack of AS400 Objects
	if (as400Stack == null)
	{
		as400Stack = new Stack();
		// Build 10 AS400 Objects
		for (int i = 0; i < 10; i++)
		{
//			 10/28/11 TWalton Change to point to lawson.treetop.com instead of IP Address
		//	AS400 sys = new AS400("10.6.100.3","DAUSER","WEB230502");
			AS400 sys = new AS400("lawson.treetop.com","DAUSER","WEB230502");
			as400Stack.push(sys);
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
       } catch (SQLException e) {
          // Log it somewhere...
       }
    }    
    AS400 as400;
    while (!as400Stack.empty()) {
      try {
         as400 = (AS400) as400Stack.pop();
         as400.disconnectService(AS400.COMMAND);
         System.out.println("closed one from as400Stack");
      } catch (Exception e) {
     //     Log it somewhere...
      }
   }        	
}
/**
 * ConnectionStack constructor comment.
 * Added 2/5/07 -- TWalton
 *    // Get Connection from the NEW Box
 */
  public static synchronized Connection getConnection() {

  	try
	{  // Initialize this Class 
  		ConnectionStack init = new ConnectionStack();
	}
  	catch(Exception e)
	{	
	}
  	
      if (connStack.empty()) {
         try {
            return DriverManager.getConnection("jdbc:db2://*LOCAL");
         } catch (SQLException e) {
            return null;
         }
       }
       else
          return (Connection) connStack.pop();
 }
/**
 * return an connection back to the pool.
 * Added 2/5/07 -- TWalton
 *    // Return Connection from the NEW Box
 */
  public static synchronized void returnConnection(Connection c) {
  	try
	{  // Initialize this Class 
  		ConnectionStack init = new ConnectionStack();
	}
  	catch(Exception e)
	{	
	}	
       connStack.push(c);
  }
/**
 * Get an AS400 Object
 * Added 3/6/08 -- TWalton
 */
 public static synchronized AS400 getAS400Object() {
  	try
	{  // Initialize this Class 
  		ConnectionStack init = new ConnectionStack();
	}
  	catch(Exception e)
	{	
	}
     return (AS400) as400Stack.pop();
}
/**
 * Return an AS400 Object back into the Stack
 * Added 3/6/08 -- TWalton
 */
 public static synchronized void returnAS400Object(AS400 as400) 
 {
  	try
	{  // Initialize this Class 
  		ConnectionStack init = new ConnectionStack();
	}
  	catch(Exception e)
	{	
	}
      as400Stack.push(as400);
 }

}
