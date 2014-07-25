package com.treetop.services;

import java.util.*;
import java.sql.*;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.SystemValue;
import com.treetop.businessobjects.*;
/**
 * Converted to use ConnQueue objects
 * Methods still reference "stack"
 * @author jhagle
 *
 */
public class ServiceConnection {

	private static final String HOST_NAME = "lawson.treetop.com";
	private static final String DRIVER = "jdbc:as400";
	private static final String DRIVER_CLASS = "com.ibm.as400.access.AS400JDBCDriver";

	//method access to get host name if needed anywhere else
	public static String getHostName() {
		return HOST_NAME;
	}

	//Declare each connection stack with
	//(User profile, Password, Initial stack size, Max allowed size)
	private static ConnQueue as400 = new ConnQueue("DAUSER","WEB230502",2,10);
	private static ConnQueue tncon1 = new ConnQueue("tncon1","tncon1",2,10); // use for ServicePlanning
	private static ConnQueue tncon2 = new ConnQueue("tncon2","tncon2",2,10);
	private static ConnQueue tncon3 = new ConnQueue("tncon3","tncon3",2,10);
	private static ConnQueue tncon4 = new ConnQueue("tncon4","tncon4",2,10);
	private static ConnQueue tncon5 = new ConnQueue("tncon5","tncon5",2,10);
	private static ConnQueue tncon6 = new ConnQueue("tncon6","tncon6",2,10);// USE for Timer/NGL/ON Demand only
	private static ConnQueue tncon7 = new ConnQueue("tncon7","tncon7",2,10);// USE for ENTER.TREETOP.COM only
	private static ConnQueue tncon8 = new ConnQueue("tncon8","tncon8",2,10);
	private static ConnQueue tncon9 = new ConnQueue("tncon9","tncon9",2,10);
	private static ConnQueue tncon10 = new ConnQueue("tncon10","tncon10",2,10);
	private static ConnQueue tncon11 = new ConnQueue("tncon11","tncon11",2,20); // use for ServiceContractManufacturing
	private static ConnQueue tncon12 = new ConnQueue("tncon12","tncon12",2,10); // use for ServiceItem (new Item process)
	//  11/6/12 TW Change -- the connection stack 13 - 14 --  from 2/10 -- to 5/20
	//       This is a stop gap to hopefully stop the connections from dropping
	private static ConnQueue tncon13 = new ConnQueue("tncon13","tncon13",5,20); // use for ServiceQuality (specs)
	private static ConnQueue tncon14 = new ConnQueue("tncon14","tncon14",10,75); // use for ServiceKeyValue
	private static ConnQueue tncon15 = new ConnQueue("tncon15","tncon15",2,10); // use for ServiceTickler
	
	// Do not Synch up, everyone should have their own username and password in here
	
//	private static ConnQueue as400 = new ConnQueue("twalto","shkkgk",2,10);
//	private static ConnQueue tncon1 = new ConnQueue("twalto","shkkgk",2,10);
//	private static ConnQueue tncon2 = new ConnQueue("twalto","shkkgk",2,10);
//	private static ConnQueue tncon3 = new ConnQueue("twalto","shkkgk",2,10);
//	private static ConnQueue tncon4 = new ConnQueue("twalto","shkkgk",2,10);
//	private static ConnQueue tncon5 = new ConnQueue("twalto","shkkgk",2,10);
//	private static ConnQueue tncon8 = new ConnQueue("twalto","shkkgk",2,10);
//	private static ConnQueue tncon9 = new ConnQueue("twalto","shkkgk",2,10);

	//********************************************************************
	//***          Methods for each connection stack			       ***
	//********************************************************************

	public static synchronized AS400 getAS400() throws Exception {
		return getAS400FromStack(as400);
	}

	public static synchronized void returnAS400(AS400 sys) throws Exception {
		returnAS400ToStack(as400, sys);
	}

	public static synchronized Connection getConnectionStack1() throws Exception {
		return getConnectionFromStack(tncon1);
	}

	public static synchronized void returnConnectionStack1(Connection conn) throws Exception {
		returnConnectionToStack(tncon1, conn);
	}

	public static synchronized Connection getConnectionStack2() throws Exception {
		return getConnectionFromStack(tncon2);
	}

	public static synchronized void returnConnectionStack2(Connection conn) throws Exception {
		returnConnectionToStack(tncon2, conn);
	}

	public static synchronized Connection getConnectionStack3() throws Exception {
		return getConnectionFromStack(tncon3);
	}

	public static synchronized void returnConnectionStack3(Connection conn) throws Exception {
		returnConnectionToStack(tncon3, conn);
	}

	public static synchronized Connection getConnectionStack4() throws Exception {
		return getConnectionFromStack(tncon4);
	}

	public static synchronized void returnConnectionStack4(Connection conn) throws Exception {
		returnConnectionToStack(tncon4, conn);
	}

	public static synchronized Connection getConnectionStack5() throws Exception {
		return getConnectionFromStack(tncon5);
	}

	public static synchronized void returnConnectionStack5(Connection conn) throws Exception {
		returnConnectionToStack(tncon5, conn);
	}

	public static synchronized Connection getConnectionStack8() throws Exception {
		return getConnectionFromStack(tncon8);
	}

	public static synchronized void returnConnectionStack8(Connection conn) throws Exception {
		returnConnectionToStack(tncon8, conn);
	}

	public static synchronized Connection getConnectionStack9() throws Exception {
		return getConnectionFromStack(tncon9);
	}

	public static synchronized void returnConnectionStack9(Connection conn) throws Exception {
		returnConnectionToStack(tncon9, conn);
	}

	public static synchronized Connection getConnectionStack10() throws Exception {
		return getConnectionFromStack(tncon10);
	}

	public static synchronized void returnConnectionStack10(Connection conn) throws Exception {
		returnConnectionToStack(tncon10, conn);
	}
	public static synchronized Connection getConnectionStack11() throws Exception {
		return getConnectionFromStack(tncon11);
	}

	public static synchronized void returnConnectionStack11(Connection conn) throws Exception {
		returnConnectionToStack(tncon11, conn);
	}
	public static synchronized Connection getConnectionStack12() throws Exception {
		return getConnectionFromStack(tncon12);
	}

	public static synchronized void returnConnectionStack12(Connection conn) throws Exception {
		returnConnectionToStack(tncon12, conn);
	}
	public static synchronized Connection getConnectionStack13() throws Exception {
		return getConnectionFromStack(tncon13);
	}

	public static synchronized void returnConnectionStack13(Connection conn) throws Exception {
		returnConnectionToStack(tncon13, conn);
	}
	public static synchronized Connection getConnectionStack14() throws Exception {
		return getConnectionFromStack(tncon14);
	}

	public static synchronized void returnConnectionStack14(Connection conn) throws Exception {
		returnConnectionToStack(tncon14, conn);
	}
	public static synchronized Connection getConnectionStack15() throws Exception {
		return getConnectionFromStack(tncon15);
	}

	public static synchronized void returnConnectionStack15(Connection conn) throws Exception {
		returnConnectionToStack(tncon15, conn);
	}
	// Register the JDBC driver.	
	static {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			System.out.println("com.treetop.dataaccess - missing driver. " + e);
		}
	}	


	/**
	 * Generic method to build connection
	 * @param user
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	private static synchronized Connection buildConnection(String user, String password) throws SQLException {
		Connection conn = null;
		try {
			//build a connection
			conn = DriverManager.getConnection(DRIVER + "://" + HOST_NAME, user, password);	
		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;
	} // end buildConnection()

	/**
	 * Generic method to build AS400 object
	 * @param user
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	private static synchronized AS400 buildAS400(String user, String password) throws SQLException {
		AS400 sys = null;
		try {
			//build a connection
			sys = new AS400(HOST_NAME, user, password);	
		} catch (Exception e) {
			System.out.println(e);
		}
		return sys;
	} // end buildConnection()

	/**
	 * Generic method to pull connection from a stack
	 * If there are no connections in the stack, a new one will be created until the max number
	 * of connections has been reached
	 * @param connStack
	 * @return A Connection from a specific stack.
	 * @throws Exception
	 */
	private static synchronized Connection getConnectionFromStack(ConnQueue connStack) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		try {

			//test to see if the stack has at least the initial number of connections has been created
			if (connStack.getTotalCreated() < connStack.getInitialSize()) {
				//need to created the initial number of connections
				for (int i=0; i<connStack.getInitialSize(); i++) {
					Connection newConn = buildConnection(connStack.getUser(), connStack.getPassword());
					connStack.addLast(newConn);

					//increment the connection stack count
					connStack.setTotalCreated(connStack.getTotalCreated()+1);
				}
			}

			//check to see if the max number of connections has been reached
			if (connStack.getTotalCreated() >= connStack.getMaxSize()) {	
				System.out.println("The max number of connections for " + connStack.getUser() + " has been reached.");
				throw new Exception("The max number of connections for " + connStack.getUser() + " has been reached.");
			} 

			//if the stack is empty and the number of connections created is less then the defined max,
			//build a new connection, otherwise, pop one from the stack;
			if (connStack.isEmpty()) {

				conn = buildConnection(connStack.getUser(), connStack.getPassword());

				//increment the connection stack count
				connStack.setTotalCreated(connStack.getTotalCreated()+1);
				System.out.println("New connection built for " + connStack.getUser() + " - " +
						connStack.getTotalCreated() + " active connections");
			} else  {
				//the stack is not empty, get a one from the stack
				conn = (Connection) connStack.poll();
			}

		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (!throwError.toString().trim().equals("")) {
				throw new Exception (throwError.toString().trim());
			}
		}

		return conn;
	} //end getConnectionFromStack()


	/**
	 * Generic method to pull AS400 from a stack
	 * @param connStack
	 * @return
	 * @throws Exception
	 */
	private static synchronized AS400 getAS400FromStack(ConnQueue connStack) throws Exception {
		StringBuffer throwError = new StringBuffer();
		AS400 sys = null;
		try {

			//test to see if the stack has at least the initial number of connections has been created
			if (connStack.getTotalCreated() < connStack.getInitialSize()) {
				//need to created the initial number of connections
				for (int i=0; i<connStack.getInitialSize(); i++) {
					AS400 newSys = buildAS400(connStack.getUser(), connStack.getPassword());
					connStack.addLast(newSys);

					//increment the connection stack count
					connStack.setTotalCreated(connStack.getTotalCreated()+1);
				}
			}

			//check to see if the max number of connections has been reached
			if (connStack.getTotalCreated() >= connStack.getMaxSize()) {	
				System.out.println("The max number of AS400 objects has been reached.");
				throw new Exception("The max number of AS400 objects has been reached.");
			} 

			//if the stack is empty and the number of connections created is less then the defined max,
			//build a new connection, otherwise, pop one from the stack;
			if (connStack.isEmpty()) {

				sys = buildAS400(connStack.getUser(), connStack.getPassword());

				//increment the connection stack count
				connStack.setTotalCreated(connStack.getTotalCreated()+1);
				System.out.println("New AS400 built.  " +
						connStack.getTotalCreated() + " active AS400 objects.");
			} else  {
				//the stack is not empty, get a one from the stack
				sys = (AS400) connStack.poll();
			}

		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (!throwError.toString().trim().equals("")) {
				throw new Exception (throwError.toString().trim());
			}
		}

		return sys;
	} //	end getAS400FromStack()


	/**
	 * Generic method to return a connection to a stack
	 * @param connStack
	 * @param conn
	 * @throws Exception
	 */
	private static synchronized void returnConnectionToStack(ConnQueue connStack, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		try {
			connStack.addLast(conn);
		} catch (Exception e) {
			throwError.append("Error returning connection to stack:  " + e);
		} finally {
			if (!throwError.toString().trim().equals("")) {
				throw new Exception (throwError.toString().trim());
			}
		}
	} //	end returnConnectionToStack()

	/**
	 * Generic method to return an AS400 object to the stack
	 * @param connStack
	 * @param conn
	 * @throws Exception
	 */
	private static synchronized void returnAS400ToStack(ConnQueue connStack, AS400 sys) throws Exception {
		StringBuffer throwError = new StringBuffer();
		try {
			connStack.addLast(sys);
		} catch (Exception e) {
			throwError.append("Error returning AS400 to stack:  " + e);
		} finally {
			if (!throwError.toString().trim().equals("")) {
				throw new Exception (throwError.toString().trim());
			}
		}
	} //	end returnAS400ToStack()

}


