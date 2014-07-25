package com.treetop.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;
import com.treetop.utilities.*;
import com.treetop.businessobjects.UrlValidationData;

public class ServiceSecurity extends BaseService {

	static Hashtable urlAuths = null;
	
	public static Hashtable getUrlAuths() throws Exception {
		
		//only run this if urlAuths is null
		if (urlAuths == null)
		{
			StringBuffer throwError = new StringBuffer();
			Connection conn = null;
			
			try {
				// get a connection to be sent to find methods
				conn = ConnectionStackSecurity.getConnection();
				urlAuths = new Hashtable();
				urlAuths = getUrlAuths(urlAuths, conn);
			} catch (Exception e) {
				throwError.append(e);
			} finally {
				if (conn != null) {
					try {
						ConnectionStackSecurity.returnConnection(conn);
					} catch (Exception e) {
						throwError.append("Error returning connection. " + e);
					}
				}
				
				// log any errors.
				if (!throwError.toString().trim().equals("")) {
					throwError.append(" @ com.treetop.Services.");
					throwError.append("ServiceSecurity.");
					throwError.append("getUrlAuths(");
					throwError.append("). ");
					System.out.println(throwError.toString());
					Exception e = new Exception();
					e.printStackTrace();
				}
			}
		}

		return urlAuths;
	}

	/**
	 * Method Created 01/16/09 THAILE // Use to control the information
	 * retrieval
	 * 
	 * @param Hashtable
	 * @parm connection
	 * @return Hashtable
	 * @throws Exception
	 */
	private static Hashtable getUrlAuths(Hashtable urlAuths, Connection conn)
			throws Exception {
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			// Get the list of Url and Users
			Vector parmList = new Vector();
			ps = conn.prepareStatement(buildSqlStatement("PRD", "getUrlInfo",
					parmList));
			rs = ps.executeQuery();
			String priorUrl = null;
			UrlValidationData uvd = null;
			
			while (rs.next()) {
				String url = rs.getString("GNVURL").trim();
				if (!url.equals(priorUrl)) {
					uvd = new UrlValidationData();
					urlAuths.put(url, uvd);
				}
				String type = rs.getString("DPMRECTYPE").trim();
				String value = rs.getString("DPMSECNBR").trim();
				uvd.setSecurityRequired1(rs.getString("gnvsecreq1").trim());
				uvd.setSecurityRequired2(rs.getString("gnvsecreq2").trim());
				uvd.setSecurityRequired3(rs.getString("gnvsecreq3").trim());
				uvd.setSecurityRequired4(rs.getString("gnvsecreq4").trim());
				uvd.setSecurityRequired5(rs.getString("gnvsecreq5").trim());
				uvd.setSecuritySystem1(rs.getString("gnvsecsys1").trim());
				uvd.setSecuritySystem2(rs.getString("gnvsecsys2").trim());
				uvd.setSecuritySystem3(rs.getString("gnvsecsys3").trim());
				uvd.setSecuritySystem4(rs.getString("gnvsecsys4").trim());
				uvd.setSecuritySystem5(rs.getString("gnvsecsys5").trim());
				uvd.setSecurityValue1(rs.getString("gnvsecval1").trim());
				uvd.setSecurityValue2(rs.getString("gnvsecval2").trim());
				uvd.setSecurityValue3(rs.getString("gnvsecval3").trim());
				uvd.setSecurityValue4(rs.getString("gnvsecval4").trim());
				uvd.setSecurityValue5(rs.getString("gnvsecval5").trim());
				
				if (type.equals("G")) {
					uvd.getGroups().add(value);
				} else if (type.equals("R")) {
					uvd.getRoles().add(value);
				} else if (type.equals("U")) {
					uvd.getUsers().add(value);
				}
				priorUrl = url;
			}
		} catch (Exception e) {
			throwError.append(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceSecurity.");
			throwError.append("getUrlAuths(");
			throwError.append("Hashtable, conn). ");
			throw new Exception(throwError.toString());
		}
		// return value
		return urlAuths;
	}

	/**
	 * Build an sql statement.
	 * 
	 * @param String
	 *            request type
	 * @param Vector
	 *            request class
	 * @return sql string
	 * @throws Exception
	 */

	private static String buildSqlStatement(String environment,
			String inRequestType, Vector requestClass) throws Exception {
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();

		try { // Get the URL and User Files

			if (inRequestType.equals("getUrlInfo")) {
				// cast the incoming parameter class.

				// build the sql statement.
				sqlString.append("SELECT  gnvurl, dpmrectype, dpmsecnbr,  ");
				sqlString.append("gnvsecsys1, gnvsecsys2, gnvsecsys3, gnvsecsys4, gnvsecsys5, ");
				sqlString.append("gnvsecreq1, gnvsecreq2, gnvsecreq3, gnvsecreq4, gnvsecreq5, ");
				sqlString.append("gnvsecval1, gnvsecval2, gnvsecval3, gnvsecval4, gnvsecval5 ");
				sqlString.append("FROM " + "TREENET" + ".GNPVMENU ");
				sqlString.append("INNER JOIN " + "TREENET" + ".DPPMURLUSE ");
				sqlString.append("ON GNVURLNBR = DPMURLNBR ");
				sqlString.append("ORDER BY gnvurl, dpmrectype, dpmsecnbr  ");
			}
		} catch (Exception e) {
			throwError.append(" Error building sql statement"
					+ " for request type " + inRequestType + ". " + e);
		}
		// return data.
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceSecurity.");
			throwError.append("buildSqlStatement(String,String,Vector)");
			throw new Exception(throwError.toString());
		}

		return sqlString.toString();
	}
}
