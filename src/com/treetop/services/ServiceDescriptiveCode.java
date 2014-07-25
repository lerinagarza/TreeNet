/*
 * Created on October 19, 2007
 *
  * 
 */
package com.treetop.services;


import java.sql.*;
import java.util.*;
import com.treetop.app.descriptivecode.*;
import com.treetop.utilities.*;
import com.treetop.utilities.html.*;
import com.treetop.viewbeans.*;


public class ServiceDescriptiveCode extends BaseService {

	public static final String library = "M3DJDPRD.";

	public ServiceDescriptiveCode() {
		super();
	}
	

	public static void main(String[] args)
	{
		
		String stopHere = "First";
		
		try
		{
			if (1 == 0) {
				InqDescCode dc = new InqDescCode();
				
				String[] roles = {"1","8"};
				dc.setInqRoles(roles);
				String[] groups = {""};
				dc.setInqGroups(groups);
				//dc.setInqSearch("user fin");
				dc.setInqType("H");
				dc.setInqKey00("OUTQ");
				//Vector rtnObj = getDescriptiveCodeHeaders(dc);
				dc = getDescriptiveCodeDetailFromKeys(dc);
				String stop = "here";
			}
			if (1 == 0) {
				InqDescCode dc = new InqDescCode();
				dc.setInqKey00("FINSEGMT");
				getDescriptiveCodeHeaderDescr(dc);
				System.out.println(dc.getInqTypeDescr80() + " | " + dc.getInqTypeDescr40());
			}
			if (1 == 0) {
				//tryThis();
//				String initial = "!@#$%^&*()_+";
//				System.out.println(initial);
//				String encoded = URLEncoder.encode(initial, "UTF8");
//				System.out.println(encoded);
//				String decoded = URLDecoder.decode(encoded, "UTF8");
//				System.out.println(decoded);
				String decoded = "";
				try {
					//decoded = URLDecoder.decode("OxA1", "UTF-8");
				} catch (Exception e) {System.out.println("error");}
				System.out.println(decoded);
			}
			
			if (1 == 0) {
				CommonRequestBean crb = new CommonRequestBean ();
				crb.setEnvironment("PRD");
				crb.setIdLevel1("PCAT");
				crb.setIdLevel2("V");
				Vector dd = dropDownSingle(crb);
				String stop = "here";
			}
			
			//return a vector of values.
			if (1 == 0) { 
				Vector v = new Vector();
				InqDescCode dc = new InqDescCode();
				dc.setInqKey00("PEACH GROWER NAME");
				 v = getDescriptiveCodeDetails(dc);
				 
				 stopHere = "Please";
			}
			
			//getPeachGrowerNameKey(String,String)
			if (1 == 1) {
				String env = "PRD";
				String gn  = "Happy Gilmore xxxxxxxxxxxx";
				String gk  = getPeachGrowerNameKey(env,gn);
				stopHere = "YES";
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Returns header information
	 * @param dc
	 * @return Vector of InqDescCode objects
	 */
	public static Vector getDescriptiveCodeHeaders(InqDescCode dc) {
		Vector rtnObj = new Vector();
		Connection conn = null;
		Statement listIt = null;
		ResultSet rs = null;
		
		Vector keywords = null;
		if (!dc.getInqSearch().trim().equals("")) {
			keywords = Search.getKeywords(dc.getInqSearch());
		}
		
		
		
		Vector parms = new Vector();
		parms.addElement("PRD");
		parms.addElement(dc.getInqRoles());
		parms.addElement(dc.getInqGroups());
		parms.addElement(keywords);
				
		try {
	
			conn = ConnectionStack3.getConnection();
			listIt = conn.createStatement();
			String sql = buildSqlStatement("getInqHeaders", parms);
			rs = listIt.executeQuery(sql);
			boolean noRecords = true;
			while (rs.next()) {
				noRecords = false;
				InqDescCode dci = new InqDescCode();
				dci.setInqKey00(rs.getString("dcpk00").trim());
				dci.setInqTypeDescr80(rs.getString("dcpt80").trim());
				dci.setInqTypeDescr40(rs.getString("dcpt40").trim());
				rtnObj.addElement(dci);
			}
			if (noRecords) {
				InqDescCode dci = new InqDescCode();
				dci.setInqErrMsg("No results for \"" + dc.getInqSearch() + "\".  Please search again or click \"Show ALL\".");
				rtnObj.addElement(dci);
			}
		} catch (Exception e) {System.out.println(e);}
		finally {
			try {
				rs.close();
				listIt.close();
				ConnectionStack3.returnConnection(conn);
			} catch (Exception e) {System.out.println(e);}
		}
		
		return rtnObj;
	}
	
	public static void updateDescriptiveCodeDetails(UpdDescCode uc, InqDescCode dc) {
		Connection conn = null;
		PreparedStatement updateIt = null;
		
		Vector parms = new Vector();
		parms.addElement("PRD");
		parms.addElement(dc);
		parms.addElement(uc);
		
		try {
			
			conn = ConnectionStack3.getConnection();
			String sql = buildSqlStatement("updateDetails", parms);
			updateIt = conn.prepareStatement(sql);
			updateIt.setString(1, uc.getUpdUser());
			updateIt.setString(2, uc.getUpdKey01());
			updateIt.setString(3, uc.getUpdKey02());
			updateIt.setString(4, uc.getUpdKey03());
			updateIt.setString(5, uc.getUpdKey04());
			updateIt.setString(6, uc.getUpdKey05());
			updateIt.setString(7, uc.getUpdKey06());
			updateIt.setString(8, uc.getUpdKey07());
			updateIt.setString(9, uc.getUpdKey08());
			updateIt.setString(10, uc.getUpdKey09());
			updateIt.setString(11, uc.getUpdKey10());
			
			updateIt.setString(12, uc.getUpdDescrLong());
			updateIt.setString(13, uc.getUpdDescrShort());
			updateIt.setString(14, uc.getUpdMessage());
			
			updateIt.setString(15, uc.getUpdAlpha01());
			updateIt.setString(16, uc.getUpdAlpha02());
			updateIt.setString(17, uc.getUpdAlpha03());
			updateIt.setString(18, uc.getUpdAlpha04());
			updateIt.setString(19, uc.getUpdAlpha05());
			
			updateIt.setString(20, uc.getUpdNum01());
			updateIt.setString(21, uc.getUpdNum02());
			updateIt.setString(22, uc.getUpdNum03());
			updateIt.setString(23, uc.getUpdNum04());
			updateIt.setString(24, uc.getUpdNum05());
						
			updateIt.executeUpdate();
			
		} catch (Exception e) {System.out.println(e);}
		finally {
			try {
				if(conn != null) {
					ConnectionStack3.returnConnection(conn);
				}
				updateIt.close();
			} catch (Exception e) {System.out.println(e);}
		}
	}


	public static void insertDescriptiveCodeDetails(UpdDescCode uc) {
		Connection conn = null;
		PreparedStatement insertIt = null;
		
		Vector parms = new Vector();
		parms.addElement("PRD");
		parms.addElement(uc);
		
		try {
			conn = ConnectionStack3.getConnection();
			String sql = buildSqlStatement("insertDetails", parms);
			insertIt = conn.prepareStatement(sql);
			insertIt.setString(1, uc.getUpdMessage());
			insertIt.executeUpdate();
			
		} catch (Exception e) {System.out.println(e);}
		finally {
			try {
				if(conn != null) {
					ConnectionStack3.returnConnection(conn);
				}
				insertIt.close();
			} catch (Exception e) {System.out.println(e);}
		}
	}


	public static void deleteDescriptiveCodeDetails(InqDescCode dc) {
		Connection conn = null;
		Statement deleteIt = null;
		
		Vector parms = new Vector();
		parms.addElement("PRD");
		parms.addElement(dc);
		
		try {
			conn = ConnectionStack3.getConnection();
			deleteIt = conn.createStatement();
			String sql = "";
			if (dc.getInqType().equals("H")) {
				sql = buildSqlStatement("deleteHeader", parms);
			} else {
				sql = buildSqlStatement("deleteDetails", parms);
			}
			
			deleteIt.executeUpdate(sql);
			
		} catch (Exception e) {System.out.println(e);}
		finally {
			try {
				if(conn != null) {
					ConnectionStack3.returnConnection(conn);
				}
				deleteIt.close();
			} catch (Exception e) {System.out.println(e);}
		}
	}

/**
	 * Send in an InqDescCode Obj with security roles and groups, and record type filled out
	 * Puts out a vecotr ob InqDescCode objects
	 * @param dc
	 * @return Vector
	 */
		public static Vector getDescriptiveCodeDetails(InqDescCode dc) {
			Vector rtnObj = new Vector();
			Connection conn = null;
			Statement listIt = null;
			ResultSet rs = null;
					
			Vector parms = new Vector();
			parms.addElement("PRD");
			parms.addElement(dc.getInqRoles());
			parms.addElement(dc.getInqGroups());
			parms.addElement(dc.getInqKey00());
					
			try {
		
				conn = ConnectionStack3.getConnection();
				listIt = conn.createStatement();
				String sql = buildSqlStatement("getInqDetails", parms);
				rs = listIt.executeQuery(sql);
				while (rs.next()) {
					InqDescCode dci = new InqDescCode();
					dci.setInqKey00(rs.getString("h_dcpk00").trim());
					dci.setInqTypeDescr80(rs.getString("h_dcpt80").trim());
					dci.setInqTypeDescr40(rs.getString("h_dcpt40").trim());
					dci.setInqKey00(rs.getString("dcpk00").trim());
					
					Vector keys = new Vector();
					dci.setInqKey01(rs.getString("dcpk01").trim());
					keys.addElement(dci.getInqKey01());
					dci.setInqKey02(rs.getString("dcpk02").trim());
					keys.addElement(dci.getInqKey02());
					dci.setInqKey03(rs.getString("dcpk03").trim());
					keys.addElement(dci.getInqKey03());
					dci.setInqKey04(rs.getString("dcpk04").trim());
					keys.addElement(dci.getInqKey04());
					dci.setInqKey05(rs.getString("dcpk05").trim());
					keys.addElement(dci.getInqKey05());
					dci.setInqKey06(rs.getString("dcpk06").trim());
					keys.addElement(dci.getInqKey06());
					dci.setInqKey07(rs.getString("dcpk07").trim());
					keys.addElement(dci.getInqKey07());
					dci.setInqKey08(rs.getString("dcpk08").trim());
					keys.addElement(dci.getInqKey08());
					dci.setInqKey09(rs.getString("dcpk09").trim());
					keys.addElement(dci.getInqKey09());
					dci.setInqKey10(rs.getString("dcpk10").trim());
					keys.addElement(dci.getInqKey10());
					
					dci.setInqKeyStr(buildString(keys));
					
					dci.setInqDescrLong(rs.getString("dcpt80").trim());
					dci.setInqDescrShort(rs.getString("dcpt40").trim());
					dci.setInqMessage(rs.getString("dcpmsg").trim());
					
					Vector alphas = new Vector();
					dci.setInqAlpha01(rs.getString("dcpa01").trim());
					alphas.addElement(dci.getInqAlpha01());
					dci.setInqAlpha02(rs.getString("dcpa02").trim());
					alphas.addElement(dci.getInqAlpha02());
					dci.setInqAlpha03(rs.getString("dcpa03").trim());
					alphas.addElement(dci.getInqAlpha03());
					dci.setInqAlpha04(rs.getString("dcpa04").trim());
					alphas.addElement(dci.getInqAlpha04());
					dci.setInqAlpha05(rs.getString("dcpa05").trim());
					alphas.addElement(dci.getInqAlpha05());
					
					dci.setInqAlphaStr(buildString(alphas));
					
					Vector numbers = new Vector();
					dci.setInqNum01(rs.getString("dcpn01"));
					numbers.addElement(dci.getInqNum01());
					dci.setInqNum02(rs.getString("dcpn02"));
					numbers.addElement(dci.getInqNum02());
					dci.setInqNum03(rs.getString("dcpn03"));
					numbers.addElement(dci.getInqNum03());
					dci.setInqNum04(rs.getString("dcpn04"));
					numbers.addElement(dci.getInqNum04());
					dci.setInqNum05(rs.getString("dcpn05"));
					numbers.addElement(dci.getInqNum05());
									
					rtnObj.addElement(dci);
	
				}
				
			
			} catch (Exception e) {System.out.println(e);}
			finally {
				try {
					rs.close();
					listIt.close();
					ConnectionStack3.returnConnection(conn);
				} catch (Exception e) {System.out.println(e);}
			}
	
			return rtnObj;
		}


/**
 * Send in an InqDescCode Obj with security roles and groups, and record type filled out
 * Puts out a vecotr ob InqDescCode objects
 * @param dc
 * @return Vector
 */
	public static InqDescCode getDescriptiveCodeDetailFromKeys(InqDescCode dc) {
		
		Connection conn = null;
		Statement listIt = null;
		ResultSet rs = null;
				
		Vector parms = new Vector();
		parms.addElement("PRD");
		parms.addElement(dc.getInqRoles());
		parms.addElement(dc.getInqGroups());
		parms.addElement(dc.getInqKey00());
		parms.addElement(dc.getInqKey01());
		parms.addElement(dc.getInqKey02());
		parms.addElement(dc.getInqKey03());
		parms.addElement(dc.getInqKey04());
		parms.addElement(dc.getInqKey05());
		parms.addElement(dc.getInqKey06());
		parms.addElement(dc.getInqKey07());
		parms.addElement(dc.getInqKey08());
		parms.addElement(dc.getInqKey09());
		parms.addElement(dc.getInqKey10());
		
				
		try {
	
			conn = ConnectionStack3.getConnection();
			listIt = conn.createStatement();
			String sql = "";
			if (dc.getInqType().equals("H")) {
				sql = buildSqlStatement("getInqHeaderInfo", parms);
			} else if (dc.getInqType().equals("S")) {
				sql = buildSqlStatement("getInqSecurityInfo", parms);
			} else {
				sql = buildSqlStatement("getInqDetailFromKeys", parms);
			}
			rs = listIt.executeQuery(sql);

			if (rs.next()) {
				
				dc.setInqKey00(rs.getString("h_dcpk00").trim());
				dc.setInqTypeDescr80(rs.getString("h_dcpt80").trim());
				dc.setInqTypeDescr40(rs.getString("h_dcpt40").trim());
				
				dc.setInqDescrLong(rs.getString("dcpt80").trim());
				dc.setInqDescrShort(rs.getString("dcpt40").trim());
				dc.setInqMessage(rs.getString("dcpmsg").trim());
				
				dc.setInqAlpha01(rs.getString("dcpa01").trim());
				dc.setInqAlpha02(rs.getString("dcpa02").trim());
				dc.setInqAlpha03(rs.getString("dcpa03").trim());
				dc.setInqAlpha04(rs.getString("dcpa04").trim());
				dc.setInqAlpha05(rs.getString("dcpa05").trim());
						
				dc.setInqNum01(rs.getString("dcpn01"));
				dc.setInqNum02(rs.getString("dcpn02"));
				dc.setInqNum03(rs.getString("dcpn03"));
				dc.setInqNum04(rs.getString("dcpn04"));
				dc.setInqNum05(rs.getString("dcpn05"));
			}
			
		} catch (Exception e) {System.out.println(e);}
		finally {
			try {
				rs.close();
				listIt.close();
				ConnectionStack3.returnConnection(conn);
			} catch (Exception e) {System.out.println(e);}
		}

		return dc;
	}
	


	private static String buildString(Vector values) {
		StringBuffer rtn = new StringBuffer();
		boolean first = false;
		for (int i = values.size()-1; i>=0; i--) {
			String value = (String) values.elementAt(i);
			if (first || !value.equals("")) {
				if (value.equals(""))
					value = "  ";
				if (first) {
					rtn.insert(0, value + "-");
				} else {
					rtn.insert(0, value);
				}
				first = true;
			}
		}
		return rtn.toString();
	}
	
	public static UpdDescCode getDescriptiveCodeHeaderDescr(UpdDescCode dc) {
		String type = dc.getUpdKey00();
		if (!type.equals("")) {
			Connection conn = null;
			Statement listIt = null;
			ResultSet rs 	= null;

			
			Vector parms = new Vector();
			parms.addElement("PRD");
			parms.addElement(type);
			
			try {
				conn = ConnectionStack3.getConnection();
				listIt = conn.createStatement();
				String sql = buildSqlStatement("getInqHeaderDescriptions", parms);
				rs = listIt.executeQuery(sql);
				
				if (rs.next()) {
					dc.setUpdTypeDescr80(rs.getString("dcpt80").trim());
					dc.setUpdTypeDescr40(rs.getString("dcpt40").trim());
				}
				
			} catch (Exception e) {System.out.println(e);}
			finally {
				try {
					if (conn != null) {
						ConnectionStack3.returnConnection(conn);
					}
					listIt.close();
					rs.close();
				} catch (Exception e) {System.out.println(e);}
			}


		}
		return dc;
	}
	
	public static InqDescCode getDescriptiveCodeHeaderDescr(InqDescCode dc) {
		String type = dc.getInqKey00();
		if (!type.equals("")) {
			Connection conn = null;
			Statement listIt = null;
			ResultSet rs 	= null;

			
			Vector parms = new Vector();
			parms.addElement("PRD");
			parms.addElement(type);
			
			try {
				conn = ConnectionStack3.getConnection();
				listIt = conn.createStatement();
				String sql = buildSqlStatement("getInqHeaderDescriptions", parms);
				rs = listIt.executeQuery(sql);
				
				if (rs.next()) {
					dc.setInqTypeDescr80(rs.getString("dcpt80").trim());
					dc.setInqTypeDescr40(rs.getString("dcpt40").trim());
				}
				
			} catch (Exception e) {System.out.println(e);}
			finally {
				try {
					if (conn != null) {
						ConnectionStack3.returnConnection(conn);
					}
					listIt.close();
					rs.close();
				} catch (Exception e) {System.out.println(e);}
			}


		}
		return dc;
	}
	
	
	
	private static String buildSqlStatement(String requestType, Vector parms) {
		StringBuffer sql = new StringBuffer();
		String env = (String) parms.elementAt(0);
		if (requestType.equals("getInqHeaders")) {

			String[] 	roles		= (String[]) parms.elementAt(1);
			String[] 	groups		= (String[]) parms.elementAt(2);
			Vector	 	keywords 	= (Vector)parms.elementAt(3);
			
			sql.append("SELECT DISTINCT h.dcpk00, h.dcpt80, h.dcpt40 \r");
			sql.append("FROM db" + env + ".dcpall as h \r");
			sql.append("LEFT OUTER JOIN db" + env + ".dcpall as s on " +
					"s.dcptyp='S' and h.dcpk00=s.dcpk00 \r");
	
			sql.append("WHERE h.dcptyp='H' \r");
			sql.append("and (s.dcpa01 is null  \r");
			
			//validate role authority
			if (!roles[0].trim().equals("")) {
				sql.append("or (s.dcpa01='R' and (");
				for (int i=0; i<roles.length; i++) {
					if (i == 0) {
						sql.append("s.dcpa02='" + roles[i] + "'");
					} else {
						sql.append(" or s.dcpa02='" + roles[i] + "'");
					}
				}
				sql.append("))  \r");
			}
			
			
			//validate group authority
			if (!groups[0].trim().equals("")) {
				sql.append("or (s.dcpa01='G' and ( \r");
				for (int i=0; i<groups.length; i++) {
					if (i == 0) {
						sql.append("s.dcpa02='" + groups[i] + "'");
					} else {
						sql.append(" or s.dcpa02='" + groups[i] + "'");
					}
				}
				sql.append(")) \r");
			}
			sql.append(") \r");
			
			
			//Search keywords
			if (keywords != null) {
				sql.append(" and \r(");
				for (int i=0; i<keywords.size(); i++) {
					if (!keywords.elementAt(i).equals("")) {
						if (i == 0) {
							sql.append("ucase(h.dcpmsg) like '%" + keywords.elementAt(i).toString().toUpperCase() + "%'");
						} else {
							sql.append(" or \r ucase(h.dcpmsg) like '%" + keywords.elementAt(i).toString().toUpperCase() + "%'");
						}
					}
				}
				sql.append(") ");
			}
			sql.append("ORDER BY dcpk00");
		}
		
		if (requestType.equals("getInqDetails")) {

			String[] roles 		= (String[]) parms.elementAt(1);
			String[] groups		= (String[]) parms.elementAt(2);
			String 	 type 		= (String)parms.elementAt(3);
			
			
			sql.append("SELECT DISTINCT h.dcpk00 as h_dcpk00, h.dcpt80 as h_dcpt80, h.dcpt40 as h_dcpt40, v.* \r");
			sql.append("FROM db" + env + ".dcpall as h \r");
			sql.append("LEFT OUTER JOIN db" + env + ".dcpall as s on " +
					"s.dcptyp='S' and h.dcpk00=s.dcpk00 \r");
			sql.append("INNER JOIN db" + env + ".dcpall as v on " +
					"v.dcptyp='V' and h.dcpk00=v.dcpk00 \r");
	
			sql.append("WHERE h.dcptyp='H' and h.dcpk00='" + type + "' \r");
			sql.append("and (s.dcpa01 is null  \r");
			
			//validate role authority
			if (!roles[0].trim().equals("")) {
				sql.append("or (s.dcpa01='R' and (");
				for (int i=0; i<roles.length; i++) {
					if (i == 0) {
						sql.append("s.dcpa02='" + roles[i] + "'");
					} else {
						sql.append(" or s.dcpa02='" + roles[i] + "'");
					}
				}
				sql.append("))  \r");
			}
			
			
			//validate group authority
			if (!groups[0].trim().equals("")) {
				sql.append("or (s.dcpa01='G' and ( \r");
				for (int i=0; i<groups.length; i++) {
					if (i == 0) {
						sql.append("s.dcpa02='" + groups[i] + "'");
					} else {
						sql.append(" or s.dcpa02='" + groups[i] + "'");
					}
				}
				sql.append(")) \r");
			}
			sql.append(") \r");
			
			sql.append("ORDER BY v.dcpk00, v.dcpk01, v.dcpk02, v.dcpk03, v.dcpk04, v.dcpk05, v.dcpk06, v.dcpk07, v.dcpk08, v.dcpk09, v.dcpk10");
			
		}
		if (requestType.equals("getInqDetailFromKeys")) {

			String[] roles 	= (String[]) parms.elementAt(1);
			String[] groups = (String[]) parms.elementAt(2);
			String 	 type 	= (String)   parms.elementAt(3);
			String	 key01  = (String)   parms.elementAt(4);
			String	 key02  = (String)   parms.elementAt(5);
			String	 key03  = (String)   parms.elementAt(6);
			String	 key04  = (String)   parms.elementAt(7);
			String	 key05  = (String)   parms.elementAt(8);
			String	 key06  = (String)   parms.elementAt(9);
			String	 key07  = (String)   parms.elementAt(10);
			String	 key08  = (String)   parms.elementAt(11);
			String	 key09  = (String)   parms.elementAt(12);
			String	 key10  = (String)   parms.elementAt(13);
			
			
			sql.append("SELECT DISTINCT h.dcpk00 as h_dcpk00, h.dcpt80 as h_dcpt80, h.dcpt40 as h_dcpt40, v.* \r");
			sql.append("FROM db" + env + ".dcpall as h \r");
			sql.append("LEFT OUTER JOIN db" + env + ".dcpall as s on " +
					"s.dcptyp='S' and h.dcpk00=s.dcpk00 \r");
			sql.append("INNER JOIN db" + env + ".dcpall as v on " +
					"v.dcptyp='V' and h.dcpk00=v.dcpk00 \r");
	
			sql.append("WHERE h.dcptyp='H' and h.dcpk00='" + type + "' \r");
			
			sql.append("and v.dcpk01='" + key01 + "' ");
			sql.append("and v.dcpk02='" + key02 + "' ");
			sql.append("and v.dcpk03='" + key03 + "' ");
			sql.append("and v.dcpk04='" + key04 + "' ");
			sql.append("and v.dcpk05='" + key05 + "' ");
			sql.append("and v.dcpk06='" + key06 + "' ");
			sql.append("and v.dcpk07='" + key07 + "' ");
			sql.append("and v.dcpk08='" + key08 + "' ");
			sql.append("and v.dcpk09='" + key09 + "' ");
			sql.append("and v.dcpk10='" + key10 + "' ");
			
			sql.append("and (s.dcpa01 is null  \r");
			
			//validate role authority
			if (!roles[0].trim().equals("")) {
				sql.append("or (s.dcpa01='R' and (");
				for (int i=0; i<roles.length; i++) {
					if (i == 0) {
						sql.append("s.dcpa02='" + roles[i] + "'");
					} else {
						sql.append(" or s.dcpa02='" + roles[i] + "'");
					}
				}
				sql.append("))  \r");
			}
			
			
			//validate group authority
			if (!groups[0].trim().equals("")) {
				sql.append("or (s.dcpa01='G' and ( \r");
				for (int i=0; i<groups.length; i++) {
					if (i == 0) {
						sql.append("s.dcpa02='" + groups[i] + "'");
					} else {
						sql.append(" or s.dcpa02='" + groups[i] + "'");
					}
				}
				sql.append(")) \r");
			}
			sql.append(") \r");
			

		}
		if (requestType.equals("getInqHeaderInfo")) {

			String[] roles 	= (String[]) parms.elementAt(1);
			String[] groups = (String[]) parms.elementAt(2);
			String 	 type 	= (String)   parms.elementAt(3);
			String	 key01  = (String)   parms.elementAt(4);
			String	 key02  = (String)   parms.elementAt(5);
			String	 key03  = (String)   parms.elementAt(6);
			String	 key04  = (String)   parms.elementAt(7);
			String	 key05  = (String)   parms.elementAt(8);
			String	 key06  = (String)   parms.elementAt(9);
			String	 key07  = (String)   parms.elementAt(10);
			String	 key08  = (String)   parms.elementAt(11);
			String	 key09  = (String)   parms.elementAt(12);
			String	 key10  = (String)   parms.elementAt(13);
			
			
			sql.append("SELECT DISTINCT h.dcpk00 as h_dcpk00, h.dcpt80 as h_dcpt80, h.dcpt40 as h_dcpt40, h.* \r");
			sql.append("FROM db" + env + ".dcpall as h \r");
			sql.append("LEFT OUTER JOIN db" + env + ".dcpall as s on " +
					"s.dcptyp='S' and h.dcpk00=s.dcpk00 \r");
				
			sql.append("WHERE h.dcptyp='H' and h.dcpk00='" + type + "' \r");
			
			sql.append("and h.dcpk01='" + key01 + "' ");
			
			sql.append("and (s.dcpa01 is null  \r");
			
			//validate role authority
			if (!roles[0].trim().equals("")) {
				sql.append("or (s.dcpa01='R' and (");
				for (int i=0; i<roles.length; i++) {
					if (i == 0) {
						sql.append("s.dcpa02='" + roles[i] + "'");
					} else {
						sql.append(" or s.dcpa02='" + roles[i] + "'");
					}
				}
				sql.append("))  \r");
			}
			
			
			//validate group authority
			if (!groups[0].trim().equals("")) {
				sql.append("or (s.dcpa01='G' and ( \r");
				for (int i=0; i<groups.length; i++) {
					if (i == 0) {
						sql.append("s.dcpa02='" + groups[i] + "'");
					} else {
						sql.append(" or s.dcpa02='" + groups[i] + "'");
					}
				}
				sql.append(")) \r");
			}
			sql.append(") \r");
			

		}
		if (requestType.equals("getInqHeaderDescriptions")) {
			String type = (String) parms.elementAt(1);
			sql.append("SELECT dcpt80, dcpt40 \r");
			sql.append("FROM db" + env + ".dcpall \r");
			sql.append("WHERE dcptyp='H' \r");
			sql.append("and dcpk00='" + type +"' \r");
		}
		
		
		//Find an entry by Peach Grower Name.
		if (requestType.equals("findPeachGrowerName")) {
			String environment = (String) parms.elementAt(0);
			String growerName  = (String) parms.elementAt(1);
			String libraryTT  = GeneralUtility.getTTLibrary(environment);
			
			sql.append("SELECT DCPK01 ");
			sql.append("FROM " + libraryTT + ".DCPALL ");
			sql.append("WHERE DCPTYP = 'V' ");
			sql.append("AND DCPK00 = 'PEACH GROWER NAME' ");
			sql.append("AND DCPA01 = '" + growerName + "' ");
		}
		
		
		if (requestType.equals("updateDetails")) {
			InqDescCode dc = (InqDescCode) parms.elementAt(1);
			String date = UtilityDateTime.getDateFormatM3Fiscal("").getDateFormatyyyyMMdd();
			
			sql.append("UPDATE db" + env + ".dcpall \r");
			sql.append("set \r");
			
			sql.append("dcplmd='" + date + "',  \r");
			sql.append("dcplmu= ? ,  \r");
			
			sql.append("dcpk01= ? , \r");
			sql.append("dcpk02= ? , \r");
			sql.append("dcpk03= ? , \r");
			sql.append("dcpk04= ? , \r");
			sql.append("dcpk05= ? , \r");
			sql.append("dcpk06= ? , \r");
			sql.append("dcpk07= ? , \r");
			sql.append("dcpk08= ? , \r");
			sql.append("dcpk09= ? , \r");
			sql.append("dcpk10= ? , \r");
			
			sql.append("dcpt80= ? , \r");
			sql.append("dcpt40= ? , \r");
			sql.append("dcpmsg= ? ,  \r");
			
			sql.append("dcpa01= ? , \r");
			sql.append("dcpa02= ? , \r");
			sql.append("dcpa03= ? , \r");
			sql.append("dcpa04= ? , \r");
			sql.append("dcpa05= ? , \r");
			
			sql.append("dcpn01= ? , \r");
			sql.append("dcpn02= ? , \r");
			sql.append("dcpn03= ? , \r");
			sql.append("dcpn04= ? , \r");
			sql.append("dcpn05= ?   \r");
			
			
			sql.append("WHERE  \r");
			sql.append("dcptyp='" + dc.getInqType() + "' and \r");
			sql.append("dcpk00='" + dc.getInqKey00() + "' and \r");
			sql.append("dcpk01='" + dc.getInqKey01() + "' and \r");
			sql.append("dcpk02='" + dc.getInqKey02() + "' and \r");
			sql.append("dcpk03='" + dc.getInqKey03() + "' and \r");
			sql.append("dcpk04='" + dc.getInqKey04() + "' and \r");
			sql.append("dcpk05='" + dc.getInqKey05() + "' and \r");
			sql.append("dcpk06='" + dc.getInqKey06() + "' and \r");
			sql.append("dcpk07='" + dc.getInqKey07() + "' and \r");
			sql.append("dcpk08='" + dc.getInqKey08() + "' and \r");
			sql.append("dcpk09='" + dc.getInqKey09() + "' and \r");
			sql.append("dcpk10='" + dc.getInqKey10() + "' \r");
			

		}
		
		
		if (requestType.equals("insertDetails")) {
			
			UpdDescCode uc = (UpdDescCode) parms.elementAt(1);
			
			String date = UtilityDateTime.getDateFormatM3Fiscal("").getDateFormatyyyyMMdd();
			
			sql.append("INSERT INTO db" + env + ".dcpall  \r");
			sql.append("VALUES(  \r");
			sql.append("'V',  \r");
			sql.append("'" + date +"',  \r");
			sql.append("'" + uc.getUpdUser() +"',  \r");
			sql.append("'" + uc.getUpdKey00() + "',  \r");
			sql.append("'" + uc.getUpdKey01() +"',  \r");
			sql.append("'" + uc.getUpdKey02() +"',  \r");
			sql.append("'" + uc.getUpdKey03() +"',  \r");
			sql.append("'" + uc.getUpdKey04() +"',  \r");
			sql.append("'" + uc.getUpdKey05() +"',  \r");
			sql.append("'" + uc.getUpdKey06() +"',  \r");
			sql.append("'" + uc.getUpdKey07() +"',  \r");
			sql.append("'" + uc.getUpdKey08() +"',  \r");
			sql.append("'" + uc.getUpdKey09() +"',  \r");
			sql.append("'" + uc.getUpdKey10() +"',  \r");
			
			sql.append("'" + uc.getUpdDescrLong() +"',  \r");
			sql.append("'" + uc.getUpdDescrShort() +"',  \r");
			sql.append(" ? ,  \r");								//Prepared Stmt for Message w/ Spec Char
			
			sql.append("'" + uc.getUpdAlpha01() +"',  \r");
			sql.append("'" + uc.getUpdAlpha02() +"',  \r");
			sql.append("'" + uc.getUpdAlpha03() +"',  \r");
			sql.append("'" + uc.getUpdAlpha04() +"',  \r");
			sql.append("'" + uc.getUpdAlpha05() +"',  \r");
			
			sql.append("" + uc.getUpdNum01() +",  \r");
			sql.append("" + uc.getUpdNum02() +",  \r");
			sql.append("" + uc.getUpdNum03() +",  \r");
			sql.append("" + uc.getUpdNum04() +",  \r");
			sql.append("" + uc.getUpdNum05() +"  \r");
			
			sql.append(")  \r");
			
		}
		
		
		if (requestType.equals("insertPeachGrower")) {
			
			String environment = (String) parms.elementAt(0);
			String libraryTT  = GeneralUtility.getTTLibrary(environment);
			UpdDescCode uc = (UpdDescCode) parms.elementAt(1);
			
			String date = UtilityDateTime.getDateFormatM3Fiscal("").getDateFormatyyyyMMdd();
			
			sql.append("INSERT INTO db" + environment + ".dcpall  \r");
			sql.append("VALUES(  \r");
			sql.append("'V',  \r");
			sql.append("'" + date +"',  \r");
			sql.append("'" + uc.getUpdUser() +"',  \r");
			sql.append("'" + uc.getUpdKey00() + "',  \r");
			sql.append("'" + uc.getUpdKey01() +"',  \r");
			sql.append("'" + uc.getUpdKey02() +"',  \r");
			sql.append("'" + uc.getUpdKey03() +"',  \r");
			sql.append("'" + uc.getUpdKey04() +"',  \r");
			sql.append("'" + uc.getUpdKey05() +"',  \r");
			sql.append("'" + uc.getUpdKey06() +"',  \r");
			sql.append("'" + uc.getUpdKey07() +"',  \r");
			sql.append("'" + uc.getUpdKey08() +"',  \r");
			sql.append("'" + uc.getUpdKey09() +"',  \r");
			sql.append("'" + uc.getUpdKey10() +"',  \r");
			
			sql.append("'" + uc.getUpdDescrLong() +"',  \r");
			sql.append("'" + uc.getUpdDescrShort() +"',  \r");
			sql.append("'',  \r");								
			
			sql.append("'" + uc.getUpdAlpha01() +"',  \r");
			sql.append("'" + uc.getUpdAlpha02() +"',  \r");
			sql.append("'" + uc.getUpdAlpha03() +"',  \r");
			sql.append("'" + uc.getUpdAlpha04() +"',  \r");
			sql.append("'" + uc.getUpdAlpha05() +"',  \r");
			
			sql.append("" + uc.getUpdNum01() +"0,  \r");
			sql.append("" + uc.getUpdNum02() +"0,  \r");
			sql.append("" + uc.getUpdNum03() +"0,  \r");
			sql.append("" + uc.getUpdNum04() +"0,  \r");
			sql.append("" + uc.getUpdNum05() +"0  \r");
			
			sql.append(")  \r");
			
		}
		
		if (requestType.equals("deleteDetails")) {
			InqDescCode dc = (InqDescCode) parms.elementAt(1);
			
			sql.append("DELETE FROM db" + env + ".dcpall  \r");
			sql.append("WHERE  \r");
			sql.append("dcptyp='V' and \r");
			sql.append("dcpk00='" + dc.getInqKey00() +"' and \r");
			sql.append("dcpk01='" + dc.getInqKey01() + "' and \r");
			sql.append("dcpk02='" + dc.getInqKey02() + "' and \r");
			sql.append("dcpk03='" + dc.getInqKey03() + "' and \r");
			sql.append("dcpk04='" + dc.getInqKey04() + "' and \r");
			sql.append("dcpk05='" + dc.getInqKey05() + "' and \r");
			sql.append("dcpk06='" + dc.getInqKey06() + "' and \r");
			sql.append("dcpk07='" + dc.getInqKey07() + "' and \r");
			sql.append("dcpk08='" + dc.getInqKey08() + "' and \r");
			sql.append("dcpk09='" + dc.getInqKey09() + "' and \r");
			sql.append("dcpk10='" + dc.getInqKey10() + "' \r");
			
		}
		
		if (requestType.equals("deleteHeader")) {
			InqDescCode dc = (InqDescCode) parms.elementAt(1);
			
			sql.append("DELETE FROM db" + env + ".dcpall  \r");
			sql.append("WHERE  \r");
			sql.append("dcpk00='" + dc.getInqKey00() + "'");
		}
		
		if (requestType.equals("dropDownSingle")) {
				// 9/24/12 TW -- Added code so the drop down can include ALL instead of just the V ones
			CommonRequestBean crb = (CommonRequestBean) parms.elementAt(1);
			String dataType = crb.getIdLevel1();
			String orderBy = crb.getIdLevel2();
			sql.append("SELECT dcpk01, dcpt80 ");
			sql.append("FROM db" + env + ".dcpall ");
			sql.append("WHERE dcpk00='" + dataType + "' ");
			if (crb.getRebuildOption().trim().equals(""))
			  sql.append(" and dcptyp='V' ");
			if (orderBy.equals("V")){
				sql.append("ORDER BY dcpk00");
			}
			if (orderBy.equals("") || orderBy.equals("D")){
				sql.append("ORDER BY dcpt80");
			}
		}
		
		return sql.toString();
	}
	/**
	 * Fill out a CommonRequestBean with Environment and 
	 * IdLevel1 = data type and 
	 * IdLevel2 = order by (D=Description, V=Values) (Defaults to description) 
	 * @param CommonRequestBean crb
	 * @return Vector of DropDownSingle objects
	 */
	public static Vector dropDownSingle(CommonRequestBean crb) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector rtn = new Vector();

		Connection 	conn 	= null;
		Statement 	listIt 	= null;
		ResultSet 	rs 		= null;

		try {
			Vector parms = new Vector();
			if (crb.getEnvironment().equals("")) {
				crb.setEnvironment("PRD");
			}
			parms.addElement(crb.getEnvironment());
			parms.addElement(crb);
			String sql = buildSqlStatement("dropDownSingle", parms);

			conn = ConnectionStack3.getConnection();
			listIt = conn.createStatement();
			rs = listIt.executeQuery(sql);

			while (rs.next()) {
				DropDownSingle dds = new DropDownSingle();
				dds.setValue(rs.getString("dcpk01").trim());
				dds.setDescription(rs.getString("dcpt80").trim());
				rtn.addElement(dds);
			}

		} catch (Exception e) {
			throwError.append(e);
			System.out.println(e);
		}
		finally {
			try {
				if(conn != null) {ConnectionStack3.returnConnection(conn);}
				if(listIt != null) {listIt.close();}
				if(rs != null) {rs.close();}
			} catch (Exception e) {System.out.println(e);}
			if (!throwError.toString().equals("")) {
				throwError.append(" @ com.treetop.Services.");
				throwError.append("ServiceDescriptiveCode.");
				throwError.append("dropDownSingle(CommonRequestBean). ");
				throw new Exception("Error: " + throwError.toString());
			}
		}		
		return rtn;
	}
	
	public static String getPeachGrowerNameKey(String env, String growerName) throws Exception {
		
		StringBuffer throwError = new StringBuffer();
		String returnKey = null;
		
		Connection 	conn 	= null;
		Statement 	findIt 	= null;
		Statement   addIt   = null;
		ResultSet 	rs 		= null;

		try {
			Vector parms = new Vector();
			parms.addElement(env);
			parms.addElement(growerName);
			String sql = buildSqlStatement("findPeachGrowerName", parms);

			conn = ConnectionStack3.getConnection();
			findIt = conn.createStatement();
		
			rs = findIt.executeQuery(sql);

			if (rs.next()) {
				returnKey = rs.getString("DCPK01").trim();
				findIt.close();
			} else {
				UpdDescCode uc = new UpdDescCode();
				uc.setUpdKey00("PEACH GROWER NAME");
				
				String key1 = "";
				if (growerName.length() > 20)
					key1 = growerName.substring(0, 20);
				else
					key1 = growerName.trim();
				
				returnKey = key1;
				
				uc.setUpdKey01(key1);
				uc.setUpdAlpha01(growerName.trim());
				
				parms = new Vector();
				parms.addElement(env);
				parms.addElement(uc);
				sql = buildSqlStatement("insertPeachGrower", parms);
				
				addIt = conn.createStatement();
				addIt.executeUpdate(sql);
				addIt.close();
			}

		} catch (Exception e) {
			throwError.append(e);
			System.out.println(e);
		}
		finally {
			try {
				if(conn != null) {ConnectionStack3.returnConnection(conn);}
			} catch (Exception e) {
				throwError.append("Error cloasing Connection. " + e);
			}
			
			try {
				if(findIt != null) 
					findIt.close();
			} catch (Exception e) {}
			try {
				if(addIt != null) 
					addIt.close();
			} catch (Exception e) {}
			try {
				if(rs != null) 
					rs.close();
			} catch (Exception e) {}
				
			
			if (!throwError.toString().equals("")) {
				throwError.append(" @ com.treetop.Services.");
				throwError.append("ServiceDescriptiveCode.");
				throwError.append("getPeachGrowerNameKey(String,String). ");
				throw new Exception("Error: " + throwError.toString());
			}
		}
		
		return returnKey;
	}
	
}





