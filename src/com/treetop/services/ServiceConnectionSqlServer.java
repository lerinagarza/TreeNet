package com.treetop.services;

import com.treetop.businessobjects.ConnQueue;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertEquals;

public class ServiceConnectionSqlServer {

	private static final String HOST_NAME = "IS-SQL3";
	private static final String DRIVER = "jdbc:microsoft:sqlserver";
	private static final String PORT = "1433";
	private static final String DRIVER_CLASS = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	
	private static ConnQueue sql3 = new ConnQueue("BASIC c2E6cmVkaG91c2UyMQ==", 2, 5);
	
	public static synchronized Connection getConnection() throws Exception {
		return getConnectionFromStack(sql3);
	}
	public static synchronized void returnConnection(Connection conn) throws Exception {
		returnConnectionToStack(sql3, conn);
	}

	
	// Register the JDBC driver.	
	static {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
	}	
	
	@Test
	public void testGetConnection() {
		
		try {

			
			
			Connection conn = getConnection();
			Connection conn2 = getConnection();
			Connection conn3 = getConnection();
			returnConnection(conn3);
			
			returnConnection(conn2);
			
			
			Statement stmt = null;
			ResultSet rs = null;
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from TTDemo.dbo.health_incidents");
			
			ResultSetMetaData rsm = rs.getMetaData();
			
			while (rs.next()) {
				for (int i=1; i<=rsm.getColumnCount(); i++) {
					StringBuffer line = new StringBuffer();
					for (int j=0; j<512; j++) {
						line.append(" ");
					}
					line.insert(0, rsm.getColumnName(i));
					line.insert(30, "=");
					line.insert(34, rs.getString(i));
					
					System.out.println(line.toString().trim());
				}
			}
			
			
			assertEquals("Connection is null.  ",conn != null,true);
			
			rs.close();
			stmt.close();
			returnConnection(conn);
			
			
		} catch (Exception e) {
			System.err.println(e);
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
			conn = DriverManager.getConnection(DRIVER + "://" + HOST_NAME + ":" + PORT, user, password);	
		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;
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
	
}
