/*
 * Created on October 19, 2007
 *
  * 
 */
package com.treetop.services;


import java.sql.*;
import java.util.*;

import com.treetop.businessobjects.*;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.html.*;



/**
 * @author twalto .
 *
 * Services class to obtain and return data 
 * to business objects.
 * 
 * Certificate of Analysis 
 */
public class ServiceUser extends BaseService {

	public static final String library = "M3DJDPRD.";

	/**
	 * 
	 */
	public ServiceUser() {
		super();
	}
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		try
		{
			if ("x" == "x") {
				User u = new User("JGREEN");
				u = loadUserOutQ(u);
				u = loadUserName(u);
				System.out.println("User:" + u.getUserID() + "  Name:" + u.getUserName() + "  OutQ:" + u.getOutQ());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Send in a Movex M3 User -- Return the Long Name of the Person
	 * 		  If no person is found, it will return the sent in User
	 */
	public static String returnNameFromM3User(String environment, String m3User)
	{
//		 Changes to Method
		// 2/2/09 TWalton - Change from Connection Stack to dbConnection
		if (m3User == null)
		   m3User = "";
		if (!m3User.equals(""))
		{
		try
		{
			Connection conn = null; // New Lawson Box - Lawson Database
			PreparedStatement findIt = null;
			ResultSet rs = null;
			try { 	
				String requestType = "returnLongName";
				String sqlString = "";
				
				// verify base class initialization.
				ServiceUser a = new ServiceUser();
							
				if (environment == null || environment.trim().equals(""))
					environment = "PRD";
				String keepGoing = "Y";
				// get Sales Order Info.
					try {
						Vector parmClass = new Vector();
						parmClass.addElement(m3User);
						sqlString = buildSqlStatement(environment, requestType, parmClass);
					} catch (Exception e) {
						keepGoing = "N";
					}
				
				// get a connection. execute sql, build return object.
				if (keepGoing.equals("Y")) {
					try {
						conn = com.treetop.utilities.ConnectionStack3.getConnection();
						
						findIt = conn.prepareStatement(sqlString);
						rs = findIt.executeQuery();
						
						if (rs.next())
						{
//							 it exists and all is good.
							if (rs.getString("JUTX40") != null)
								m3User = rs.getString("JUTX40").trim();
						} 
					} catch (Exception e) {
					}				
				}
			} catch(Exception e)
			{
			// return connection.
			} finally {
				try{
					if (conn != null)
						com.treetop.utilities.ConnectionStack3.returnConnection(conn);
				}catch(Exception e){
					System.out.println("Error closing a connection for Stack 3-- ServiceUser.returnNameFromM3User: " + e);
				}
				try {
					if (rs != null)
						rs.close();
					//	if (findIt != null)
					//		findIt.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}catch(Exception e)
		{}
		}
	  return m3User;
	}

	/**
	 * Gets OUTQ based on UserID
	 * @param User Business Object - Fill out userID or use constructor first!
	 * @return User 
	 */
	public static User loadUserOutQ(User u) {
		
		if (!u.getUserID().trim().equals("")) {
			Vector parm = new Vector();
			parm.addElement(u.getUserID());			
			
			Connection conn = null;
			Statement getIt = null;
			ResultSet rs = null;
						
			try {
				String sql = buildSqlStatement("PRD", "getOutQ", parm);
				conn = com.treetop.utilities.ConnectionStack3.getConnection();
				getIt = conn.createStatement();
				rs = getIt.executeQuery(sql);
				while (rs.next()) {
					u.setOutQ(rs.getString("SFDEV").trim());
					u.setOutQDescr(rs.getString("CTTX40").trim());
				}

			} catch (Exception e) {System.out.println(e);}
			finally {
				try {
					rs.close();
					getIt.close();
					com.treetop.utilities.ConnectionStack3.returnConnection(conn);
				} catch (Exception e) {System.out.println(e);}
			}
			
		}
		return u;
	}
	/**
	 * Gets OUTQ based on UserID
	 * @param User Business Object - Fill out userID or use constructor first!
	 * @return User 
	 */
	public static User loadUserName(User u) {
		
		if (!u.getUserID().trim().equals("")) {
			Vector parm = new Vector();
			parm.addElement(u.getUserID());			
			
			Connection conn = null;
			Statement getIt = null;
			ResultSet rs = null;
						
			try {
				String sql = buildSqlStatement("PRD", "returnLongName", parm);
				conn = com.treetop.utilities.ConnectionStack3.getConnection();
				getIt = conn.createStatement();
				rs = getIt.executeQuery(sql);
				while (rs.next()) {
					u.setUserName(rs.getString("JUTX40").trim());
				}

			} catch (Exception e) {System.out.println(e);}
			finally {
				try {
					rs.close();
					getIt.close();
					com.treetop.utilities.ConnectionStack3.returnConnection(conn);
				} catch (Exception e) {System.out.println(e);}
			}
			
		}
		return u;
	}

	/**
	 * Build an sql statement. - that returns data for a Drop Down List
	 * @param String request type
	 * @param Vector request class
	 * @return sql string
	 * @throws Exception
	 */
	
	private static String buildSqlDropDown(CommonRequestBean crb)
		throws Exception 
	{
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();
		
		try { // Get and Return Drop Down List Information
			String tnLibrary = "TREENET.";
			
			
			if (crb.getIdLevel1().trim().equals("dropDownUsersByGroup")) {
			  sqlString.append("SELECT DPNUSER, DPNUSRNAME ");
			  sqlString.append("FROM " + tnLibrary + "DPPNUSER ");
			  sqlString.append("INNER JOIN " + tnLibrary + "DPPMUSERG ");
			  sqlString.append("  ON DPNUSERNBR = DPMUSERNBR ");
			  sqlString.append("WHERE DPMGRPNBR = '" + crb.getIdLevel2().trim() + "' ");
			  sqlString.append("ORDER BY DPNUSRNAME, DPNUSER");
			}
		} catch (Exception e) {
				throwError.append(" Error building sql statement"
							 + " for request type " + crb.getIdLevel1() + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceUser.");
			throwError.append("buildSqlDropDown(CommonRequestBean)");
			throw new Exception(throwError.toString());
		}
		return sqlString.toString();
	}

	/**
	 * @author twalto 8/8/11
	 * Return a drop down single list of users by Group.
	 */
	
	private static Vector dropDownByGroup(CommonRequestBean requestBean,
	                                      Connection conn)
	throws Exception
	{
		StringBuffer           throwError  = new StringBuffer();
		ResultSet              rs          = null;
		Statement              listThem    = null;
		Vector<DropDownSingle> dropDownBox = new Vector<DropDownSingle>();
			
		try {
			
			try {
				
				String sql = new String();
				sql = buildSqlDropDown(requestBean);
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);
			   
			 } catch(Exception e)
			 {
			   	throwError.append("Error occured retrieving or executing a sql statement to build a drop down list. " + e);
			 }	
			 if (throwError.toString().trim().equals(""))
			 {
				 try {			 
					
					 while (rs.next())
					 {			 		
						 DropDownSingle oneGenericCode = loadFieldsDropDown(requestBean.getIdLevel1(), rs);					
						 dropDownBox.addElement(oneGenericCode);					
					 }				 
				 	
		     	 } catch(Exception e)
				 {
					throwError.append(" Error occured while building drop down vector from sql statement. " + e);
				 } 		
			 }
	
		} catch (Exception e)
		{
			throwError.append(e);
		}
		
		finally {
			
		   if (rs != null)
		   {
			   try {
				  rs.close();
				} catch(Exception el){
					el.printStackTrace();
				}
		    }
		   if (listThem != null)
		   {
			   try {
				  listThem.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
	
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceUser.dropDownByGroup(");
			throwError.append("CommonRequestBean, conn). ");
			throw new Exception(throwError.toString());
		}
	
		return dropDownBox;
	}

	/**
	 * Return a drop down single box for The Specific Group of Users
	 * Added 8/8/11 - TWalton
	 */
	
	public static Vector dropDownByGroup(CommonRequestBean requestBean)
	throws Exception
	{
				
		StringBuffer throwError  = new StringBuffer();
		Vector       dropDownBox = new Vector();
		Connection   conn        = null;
		
		try {
			conn = com.treetop.utilities.ConnectionStack3.getConnection();
			requestBean.setIdLevel1("dropDownUsersByGroup");
			dropDownBox = dropDownByGroup(requestBean, conn);		
			
		} catch (Exception e)
		{
			throwError.append(e);
		}

		finally {
			
			if (conn != null)
			{
				try {
					com.treetop.utilities.ConnectionStack3.returnConnection(conn);
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceUser.");
			throwError.append("dropDownByGroup(");
			throwError.append("CommonRequestBean). ");
			throw new Exception(throwError.toString());
		}
		
		return dropDownBox;
	}

	/**
	 * @author deisen.
	 * Load class fields from result set for a drop down box. (single)
	 */
	
	private static DropDownSingle loadFieldsDropDown(String requestType, 
							          		  		 ResultSet rs)
	throws Exception
	{
		StringBuffer   throwError  = new StringBuffer();
		DropDownSingle returnValue = new DropDownSingle();
		
		try { 
			
			if (requestType.trim().equals("dropDownUsersByGroup"))
			{
				returnValue.setValue(rs.getString("DPNUSER").trim());
				returnValue.setDescription(rs.getString("DPNUSRNAME").trim());
			}
		} catch(Exception e)
		{
			throwError.append(" Problem loading the Drop Down Single class");
			throwError.append(" from the result set. " + e) ;
		}
	
	//  *************************************************************************************				
		if (!throwError.toString().trim().equals("")) 
		{
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceUser.");
			throwError.append("loadFieldsDropDownSingle(String, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}
	
		return returnValue;
	}

	/**
	 * Build an sql statement.
	 * @param String request type
	 * @param Vector request class
	 * @return sql string
	 * @throws Exception
	 */
	
	private static String buildSqlStatement(String environment,
											String inRequestType,
									 	    Vector requestClass)
		throws Exception 
	{
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();
		
		try { // Get and Return User's Name
			// Use the GeneralUtility class to obtain library.
			String library = GeneralUtility.getLibrary(environment);
			
			if (inRequestType.equals("returnLongName"))
			{
				// cast the incoming parameter class.
				String userID = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT  JUUSID, JUTX40  ");
				sqlString.append("FROM " + library + ".CMNUSR ");
				sqlString.append(" WHERE JUCONO = 0 and JUDIVI = '' ");
				sqlString.append("and JUUSID = '" + userID.trim() + "' ");
			}
			
			if (inRequestType.equals("getOutQ"))
			{
				// cast the incoming parameter class.
				String userID = (String) requestClass.elementAt(0);
				
				// build the sql statement.
				sqlString.append("SELECT SFDEV, CTTX40  ");
				sqlString.append("FROM " + library + ".csfdef ");
				sqlString.append("INNER JOIN " + library + ".csytab on sfcono=ctcono and ctstco='DEV' and sfdev=ctstky ");
				sqlString.append("WHERE sfcono=100 and sfdivi='100' and sfprtf='' and sfseqn=1 and sfusid='" + userID + "'");
			}
			
		} catch (Exception e) {
				throwError.append(" Error building sql statement"
							 + " for request type " + inRequestType + ". " + e);
		}
		// return data.	
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceUser.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}
		
		
		return sqlString.toString();
	}
}

