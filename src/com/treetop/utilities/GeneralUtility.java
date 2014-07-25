/*
 * Created on December 30, 2009
 */
package com.treetop.utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;
import com.treetop.services.ServiceConnection;
import com.treetop.utilities.html.DropDownSingle;

/**
 * @author twalto .
 * 
 *         This Utility will deal with any miscellaneous thing that is necessary
 *         Will deal with small odd things
 */
public class GeneralUtility {

	public static void main(String[] args) {

		String startHere = "Yes";

		try {

			// testing build spool file vector

			if (1 == 1) {
				String fileName = "I157693018";
				Vector<String> x = getSpoolFileData("PRD", fileName, true);
				String stopHere = "Stop Here";
			}
			// testing build spool file vector

			startHere = "yes";
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// static program level fields.
	private static AS400 sys = null;

	/**
	 * Constructor
	 */
	public GeneralUtility() {
		super();
	}

	/**
	 * Load the Hits file data queue.
	 * 
	 * @param not
	 *            sure yet
	 * @return String (error info)
	 */
	public synchronized static String runCounter(Vector inStuff) {
		StringBuffer throwError = new StringBuffer();
		String aProblem = "";
		String inData = "";

		try {// catch all

			// get the data.
			String url = (String) inStuff.elementAt(0);
			String usr = (String) inStuff.elementAt(1);

			// verify there are url and user values to load.
			if (url.trim().length() > 0 && usr.trim().length() > 0) {

				for (int x = 0; usr.length() < 10; x++) {
					usr = usr + ' ';
				}

				inData = usr + url;

				// build an AS400 object.
				if (sys == null) {
					// 10/28/11 TWalton - change from IP address to
					// lawson.treetop.com
					// sys = new AS400("10.6.100.3","DAUSER","WEB230502");
					sys = new AS400("lawson.treetop.com", "DAUSER", "WEB230502");
				}

				// Load the incoming parameter into a parameter list.
				AS400Text parm = new AS400Text(inData.length()); // define
				// the
				// length of
				// the text
				// field
				ProgramParameter[] parmList = new ProgramParameter[1]; // define
				// the
				// size
				// of
				// the
				// parameter
				// list
				byte[] bInStuff = parm.toBytes(inData); // place incomming parm
				// into text field as
				// bytes.
				parmList[0] = new ProgramParameter(bInStuff);

				// Load the data queue.
				ProgramCall pgm = new ProgramCall(sys, "/QSYS.LIB/MOVEX.LIB/TNHITSND.PGM", parmList);

				if (pgm.run() != true) {
					return aProblem;
				}
			}

			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceAnyThing.");
				throwError.append("upDateHitsFileDataQueue(String:" + inData.trim() + ")");
			}

		} catch (Exception e) {
			throwError.append("Error at the try catch all. " + e);
		}

		return throwError.toString();
	}

	/**
	 * Use this method to retrieve the valid Library for Movex (M3)
	 * 
	 * Send in the Environment (String) to be applied and a library created
	 * 
	 * Will return a String the Library for Movex (M3) Creation date: 12/30/2009
	 * - Teri Walton
	 */
	public static String getLibrary(String environment) {

		String returnValue = "";
		if (environment == null || environment.trim().equals(""))
			environment = "PRD";
		returnValue = "M3DJD" + environment.trim();
		return returnValue;
	} // END of the getLibrary method

	/**
	 * Use this method to retrieve the valid Library for Tree Top Main
	 * 
	 * Send in the Environment (String) to be applied and a library created
	 * 
	 * Will return a String the Library for Tree Top Main Creation date:
	 * 12/30/2009 - Teri Walton
	 */
	public static String getTTLibrary(String environment) {

		String returnValue = "";
		if (environment == null || environment.trim().equals(""))
			environment = "PRD";
		returnValue = "DB" + environment.trim();
		return returnValue;
	} // END of the getLibrary method

	/**
	 * Use this method to retrieve the valid library for Kronos payroll
	 * Timekeeper.
	 */
	public static String getPayrollLibraryTK(String environment) {

		if (environment.trim().equals("PRD"))
			return "CLOCFILE00";
		else
			return "CLOCFILE01";

	} // END of the getPayrollLibraryTK method

	/**
	 * Use this method to retrieve the valid library for Kronos custom payroll
	 * Timekeeper.
	 */
	public static String getPayrollLibraryHRMS(String environment) {

		if (environment.trim().equals("PRD"))
			return "HRPayrollDB.dbo";
		else
			return "TTDemo.dbo";

	} // END of the getPayrollLibraryHRMS method

	/**
	 * @author deisen. Build an SQL statement for drop down lists.
	 */

	private static String buildSqlDropDown(String environment, String company, String requestType) throws Exception {
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();

		try {

			if (requestType.trim().equals("dropDownUnitOfMeasure")) {
				String libraryM3 = GeneralUtility.getLibrary(environment.trim());

				if (company.trim().equals("")) {
					company = "100";
				}

				sqlString.append(" SELECT CTSTKY, CTTX40");
				sqlString.append(" FROM " + libraryM3 + ".CSYTAB");
				sqlString.append(" WHERE CTCONO = " + company + " AND CTSTCO = 'UNIT'");
				sqlString.append(" GROUP BY CTSTKY, CTTX40");
				sqlString.append(" ORDER BY CTSTKY, CTTX40");
			}

		} catch (Exception e) {
			throwError.append(" Error building sql statement" + " for drop down list. " + e);
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error at com.treetop.services.ServiceQuality.");
			throwError.append("buildSqlDropDown(String, String, String)");
			throw new Exception(throwError.toString());
		}

		return sqlString.toString();
	}

	/**
	 * @author deisen. Load class fields from result set for a drop down box.
	 *         (single)
	 */

	public static DropDownSingle loadFieldsDropDownSingle(String requestType, ResultSet rs) throws Exception {
		StringBuffer throwError = new StringBuffer();
		DropDownSingle returnValue = new DropDownSingle();

		try {

			if (requestType.trim().equals("dropDownUnitOfMeasure")) {
				returnValue.setValue(rs.getString("CTSTKY").trim());
				returnValue.setDescription(rs.getString("CTTX40").trim());
			}

		} catch (Exception e) {
			throwError.append(" Problem loading the Drop Down Single class");
			throwError.append(" from the result set. " + e);
		}

		// *************************************************************************************
		if (!throwError.toString().trim().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("GeneralUtility.");
			throwError.append("loadFieldsDropDownSingle(String, rs: ");
			throwError.append(requestType + "). ");
			throw new Exception(throwError.toString());
		}

		return returnValue;
	}

	/**
	 * @author deisen. Return a drop down single box for units of meausure.
	 */

	private static Vector dropDownUnitOfMeasure(String environment, String company, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		Statement listThem = null;
		Vector<DropDownSingle> dropDownBox = new Vector<DropDownSingle>();

		try {

			try {

				String sql = new String();
				sql = buildSqlDropDown(environment, company, "dropDownUnitOfMeasure");
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);

			} catch (Exception e) {
				throwError.append("Error occured retrieving or executing a sql statement. " + e);
			}
			if (throwError.toString().trim().equals("")) {
				try {

					while (rs.next()) {
						DropDownSingle oneUnitOfMeasure = loadFieldsDropDownSingle("dropDownUnitOfMeasure", rs);
						dropDownBox.addElement(oneUnitOfMeasure);
					}

				} catch (Exception e) {
					throwError.append(" Error occured while building vector from sql statement. " + e);
				}
			}

		} catch (Exception e) {
			throwError.append(e);
		}

		finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
			if (listThem != null) {
				try {
					listThem.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceGeneralUtility.");
			throwError.append("dropDownUnitOfMeasure(");
			throwError.append("String, String, conn). ");
			throw new Exception(throwError.toString());
		}

		return dropDownBox;
	}

	/**
	 * @author deisen. Return a drop down single box for units of meausure.
	 */

	public static Vector dropDownUnitOfMeasure(String environment, String company) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector dropDownBox = new Vector();
		Connection conn = null;

		try {

			conn = com.treetop.utilities.ConnectionStack7.getConnection();
			dropDownBox = dropDownUnitOfMeasure(environment, company, conn);

		} catch (Exception e) {
			throwError.append(e);
		}

		finally {

			if (conn != null) {
				try {
					com.treetop.utilities.ConnectionStack7.returnConnection(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceGeneralUtility.");
			throwError.append("dropDownUnitOfMeasure(");
			throwError.append("String, String). ");
			throw new Exception(throwError.toString());
		}

		return dropDownBox;
	}

	public static String printRequestParameters(HttpServletRequest request) {
		StringBuffer rtnStr = new StringBuffer();
		try {
			Enumeration parm = request.getParameterNames();
			rtnStr.append("?");
			for (int i = 0; parm.hasMoreElements(); i++) {
				String parameterName = (String) parm.nextElement();
				if (i > 0) {
					rtnStr.append("&");
				}
				rtnStr.append(parameterName + "=" + request.getParameter(parameterName));
			}
		} catch (Exception e) {
		}
		return rtnStr.toString();
	}

	/**
	 * Prints columns from ResultSet JH 2012-01-11
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static String getResultSetColumns(ResultSet rs) throws Exception {
		StringBuffer data = new StringBuffer();
		ResultSetMetaData rsm = rs.getMetaData();
		for (int i = 1; i <= rsm.getColumnCount(); i++) {
			if (i > 1) {
				data.append("\t");
			}
			data.append(rsm.getColumnName(i));
		}
		return data.toString();
	}

	/**
	 * Prints comma separated data values from ResultSet
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static String getResultSetData(ResultSet rs) throws Exception {
		StringBuffer data = new StringBuffer();

		ResultSetMetaData rsm = rs.getMetaData();
		for (int i = 1; i <= rsm.getColumnCount(); i++) {
			if (i > 1) {
				data.append("\t");
			}
			String value = rs.getString(i);
			if (value == null) {
				value = "null";
			}
			data.append(value.trim());
		}
		return data.toString();
	}

	public static Vector<String> getSpoolFileData(String environment, String tableName) throws Exception {
		return getSpoolFileData(environment, tableName, false);
	}

	/**
	 * @author kkeife. Return a vector containing spool file data.
	 */

	public static Vector<String> getSpoolFileData(String environment, String tableName, boolean dropTable)
			throws Exception {
		StringBuffer throwError = new StringBuffer();
		Vector<String> returnValue = null;
		Connection conn = null;

		try {
			conn = ServiceConnection.getConnectionStack13();
			returnValue = getSpoolFileData(environment, tableName, dropTable, conn);

		} catch (Exception e) {
			throwError.append(e);
		}

		finally {

			if (conn != null) {
				try {
					ServiceConnection.returnConnectionStack13(conn);
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceSpooledFiles.");
			throwError.append("getSpoolFileData(");
			throwError.append("String). ");
			throw new Exception(throwError.toString());
		}

		return returnValue;
	}

	/**
	 * @author kkeife. Return all records in a spool file.
	 */

	private static Vector<String> getSpoolFileData(String environment, String tableName, boolean dropTable,
			Connection conn) throws Exception {
		if (environment == null || environment.equals("")) {
			environment = "PRD";
		}
		String ttLibrary = getTTLibrary(environment);

		StringBuffer throwError = new StringBuffer();
		ResultSet rs = null;
		Statement listThem = null;

		Statement deleteThem = null;

		Vector<String> returnValue = new Vector<String>();

		try {

			try {
				String sql = ("SELECT * FROM " + ttLibrary + "." + tableName);
				listThem = conn.createStatement();
				rs = listThem.executeQuery(sql);

			} catch (Exception e) {
				throwError.append("Error occured retrieving or executing a sql statement. " + e);
			}

			returnValue.addElement("<pre>");
			boolean isNotFirst = false;

			while (rs.next() && throwError.toString().trim().equals("")) {

				String inuskb = rs.getString("INUSKB").trim();
				String inuspb = rs.getString("INUSPB").trim();

				if (isNotFirst && !inuskb.equals("")) {
					returnValue.addElement("<div class=\"page-break\"></div>");
				}

				if (inuspb.equals("2")) {
					returnValue.addElement("<br />");
				}

				if (inuspb.equals("3")) {
					returnValue.addElement("<br /><br />");
				}

				returnValue.addElement(rs.getString("INUDTA"));

				isNotFirst = true;

			}

			returnValue.addElement("</pre>");

		} catch (Exception e) {
			throwError.append(e);
		}

		try {
			if (dropTable) {
				String sql = "DROP TABLE " + ttLibrary + "." + tableName;
				deleteThem = conn.createStatement();
				deleteThem.executeUpdate(sql);
			}
		} catch (Exception e) {

		}

		finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}

			if (listThem != null) {
				try {
					listThem.close();
				} catch (Exception e) {
				}
			}
			if (deleteThem != null) {
				try {
					deleteThem.close();
				} catch (Exception e) {
				}
			}
		}

		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.utilities.");
			throwError.append("GeneralUtility.");
			throwError.append("getSpoolFileData(");
			throwError.append("String, conn). ");
			throw new Exception(throwError.toString());
		}

		return returnValue;
	}

	/**
	 * Use this method to retrieve the valid library for Kronos custom payroll
	 * Timekeeper.
	 */
	public static String getPayrollLibraryCustom(String environment) {

		if (environment.trim().equals("PRD"))
			return "CLOCCUST00";
		else
			return "CLOCCUST01";

	} // END of the getPayrollLibraryCustom method

	/**
	 * Returns a tab separated string of values of the field values
	 * 
	 * @param obj
	 * @return
	 */
	public static String getObjectFieldData(Object obj) {
		StringBuffer result = new StringBuffer();
		List<Field> fields = getAllFields(new LinkedList<Field>(), obj.getClass());

		for (Field field : fields) {
			try {
				if (field.getType() == String.class || field.getType() == Integer.class
						|| field.getType() == BigDecimal.class || field.getType() == Boolean.class) {
					field.setAccessible(true);
					result.append(field.get(obj) + "\t");
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}

		return result.toString();
	}

	/**
	 * Returns a tab separated string of values of the field names
	 * 
	 * @param obj
	 * @return
	 */
	public static String getObjectFieldNames(Object obj) {
		StringBuffer result = new StringBuffer();
		List<Field> fields = getAllFields(new LinkedList<Field>(), obj.getClass());

		for (Field field : fields) {
			try {
				if (field.getType() == String.class || field.getType() == Integer.class
						|| field.getType() == BigDecimal.class || field.getType() == Boolean.class) {
					result.append(field.getName() + "\t");
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}

		return result.toString();
	}
	

	/**
	 * Recursively calls .getSuperClass() to get all fields declared
	 * 
	 * @author jhagle
	 * @param fields
	 * @param c
	 * @return List list of fields
	 */
	private static List<Field> getAllFields(List<Field> fields, Class c) {

		for (Field field : c.getDeclaredFields()) {
			fields.add(field);
		}

		if (c.getSuperclass() != null) {
			fields = getAllFields(fields, c.getSuperclass());
		}

		Collections.sort(fields, new Comparator<Field>() {
			public int compare(Field object1, Field object2) {
				return object1.getName().compareTo(object2.getName());
			}
		});

		return fields;
	}
	
	public static Vector<String> getUploadFile(HttpServletRequest request, String fieldName) throws Exception {
		Vector<String> fileContents = new Vector<String>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (isMultipart) {
			ServletFileUpload upload = new ServletFileUpload();

			// Parse the request
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
			    FileItemStream item = iter.next();
			    String name = item.getFieldName();
			    			    
			    if (name.equals(fieldName) && !item.isFormField()) {
	
			    	
				    InputStream stream = item.openStream();
			    	
				    BufferedReader br = new BufferedReader(new InputStreamReader(stream));

				    String line = "";
				    while ((line = br.readLine()) != null) {
				    
				    	fileContents.addElement(line);
				    	
				    }
				    
				    br.close();
			        stream.close();
			    }
			    
			}
		}
		
		return fileContents;
	}
	
	public static Hashtable<String, Object> getParamsFromMultipart(HttpServletRequest request) throws Exception {
		Hashtable<String,Object> params = new Hashtable<String,Object>();
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (isMultipart) {
			ServletFileUpload upload = new ServletFileUpload();

			// Parse the request
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
			    FileItemStream item = iter.next();

			    			    
			    if (item.isFormField()) {
			    	
			    	String key = item.getFieldName();
			    	InputStream stream = item.openStream();
			    	String value =Streams.asString(stream);
			    	if (value != null) {
			    		params.put(key, value);
			    	}
			    	stream.close();
			        
			    } else {
			    	
			    	Vector<String> contents = new Vector<String>();
			    	InputStream stream = item.openStream();

			    	String path = item.getName();
			    	
			    	BufferedReader br = new BufferedReader(new InputStreamReader(stream));
				    
			    	String line = "";
				    while ((line = br.readLine()) != null) {
				    
				    	contents.addElement(line);
				    	
				    }
				    
				    br.close();
			        stream.close();
			    	
			        String key = item.getFieldName();
			        if (!contents.isEmpty()) {
			        	params.put(key, contents);
			        	params.put(key + "_Path", path);
			        }
			        
			    }
			}
			
		}
		
		return params;
	}
	
	public static void addObjects(Object value, Object addTo) throws Exception {
		
		if (value.getClass() == addTo.getClass()) {
			Class cls = value.getClass();
			List<Field> fields = new LinkedList<Field>();
			for (Field field : getAllFields(fields, cls)) {
				field.setAccessible(true);
				try {
					
					
					if (field.getType() == BigDecimal.class) {
						
						BigDecimal valueVal = (BigDecimal) field.get(value);
						BigDecimal addToVal = (BigDecimal) field.get(addTo);
						BigDecimal resultValue = addToVal.add(valueVal);
						if (resultValue.compareTo(BigDecimal.ZERO) != 0) {
							field.set(addTo, resultValue);
						}
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	


	public static String getPreparedStatementSql(Connection conn, String library, String table) throws Exception {
		//Convert library and table to upper-case to match system meta-data
		library = library.toUpperCase();
		table = table.toUpperCase();

		if (library.trim().equals("") || table.equals("")) {
			throw new Exception("Must specify a Library and Table");
		}
		
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		//Executing a statement to get the column count
		//table type must be physical and insertable 
		sql.append("SELECT COLUMN_COUNT FROM QSYS2.SYSTABLES WHERE TABLE_SCHEMA = '" + library +
				"' and TABLE_NAME = '" + table + "'" + 
				" and IS_INSERTABLE_INTO = 'YES'" +
		" and TABLE_TYPE = 'P'");
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
		} catch (Exception e) {
			throw new Exception("Error getting layout for " + library + "." + table);
		}

		int columnCount = 0;
		try {
			if (rs.next()) {
				columnCount = rs.getInt("COLUMN_COUNT");
			}
		} catch (Exception e) {
			throw new Exception("Error reading layout for " + library + "." + table);
		}

		rs.close();
		stmt.close();

		sql = new StringBuffer();
		sql.append("INSERT INTO " + library + "." + table + " VALUES (");
		for (int i=0; i<columnCount; i++) {
			if (i > 0) {
				sql.append(",");
			}
			sql.append("?");
		}
		sql.append(")");

		return sql.toString();
	}
	
	

} // End of the Entire Utility
