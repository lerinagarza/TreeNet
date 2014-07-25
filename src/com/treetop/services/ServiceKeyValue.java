/*
 * Created on Nov 15, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import org.junit.Test;

import com.treetop.businessobjects.DateTime;
import com.treetop.businessobjects.KeyValue;
import com.treetop.utilities.FindAndReplace;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.RandomGUID;
import com.treetop.utilities.UtilityDateTime;

/**
 * @author thaile
 *
 * Hold values loaded by key entries. A Generic Value
 * object to maintain values and descriptions by multiple 
 * keys.
 * Data file used for starters is DBPRD/GMPAKEY.
 */
public class ServiceKeyValue extends BaseService {
	
	private static final int VALUE_MAX_CHARACTERS = 500;
	private static final int DESCRIPTION_MAX_CHARACTERS = 500;

	/**
	 * 
	 */
	public ServiceKeyValue() {
		super();
	}
	
	
	/**
	 * Add Key Value file data on the Enterprise data base.
	 * @param KeyValue fromVb
	 * @return
	 * @throws Exception
	 */
	public static String addKeyValue(KeyValue updVb)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String requestType = "";
		String sqlAddKeyValue = "";
		String guid = "";
		
		Connection conn = null;
		PreparedStatement addIt = null;
		
		// verify base class initialization.
		ServiceKeyValue sk = new ServiceKeyValue();
		
		// edit incoming data prior to add.	
		try {
			updVb = edit(updVb, "add");
		} catch (Exception e) {
			throwError.append(e);
		}
		


		// if primary edits pass continue update process.
		if (throwError.toString().equals(""))
		{	
			try {
				// Pass along the incoming view bean information. 
				Vector parmClass = new Vector();
				parmClass.addElement(updVb);
				
				// get the sql statement.
				requestType = "addKeyValue";
				sqlAddKeyValue = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append(e);
			}
		}
		
		// get a connection, execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
			// 10/5/11 TWalton - Changed to use Prepared Statements instead of dynamic build of insert	
				DateTime todaysDate = UtilityDateTime.getSystemDate();
				RandomGUID myGUID = new RandomGUID();
				guid = myGUID.toString();
				
				//9/24/08 TWalton - Changed to use Connection Stack and Point to NEW Box
				//conn = getDBConnection();
				//1/16/12 TWalton - change to use ServiceConnection
				//conn = ConnectionStack.getConnection();
				conn = ServiceConnection.getConnectionStack14();
				addIt = conn.prepareStatement(sqlAddKeyValue);
				addIt.setString(1, updVb.getStatus().trim());
				addIt.setString(2, updVb.getEntryType().trim());
				addIt.setString(3, updVb.getSequence().trim());
				addIt.setString(4, updVb.getKey1().trim());
				addIt.setString(5, updVb.getKey2().trim());
				addIt.setString(6, updVb.getKey3().trim());
				addIt.setString(7, updVb.getKey4().trim());
				addIt.setString(8, updVb.getKey5().trim());
				addIt.setString(9, guid.trim());
				
				String value = FindAndReplace.sanitizeEncoding(updVb.getValue().trim());
				int valueLength = value.length();
				if (valueLength > VALUE_MAX_CHARACTERS) {
					value = value.substring(0,VALUE_MAX_CHARACTERS);
				}
				addIt.setString(10, value);
				
				
				String description = FindAndReplace.sanitizeEncoding(updVb.getDescription().trim());
				int descriptionLength = value.length();
				if (descriptionLength > DESCRIPTION_MAX_CHARACTERS) {
					description = description.substring(0,DESCRIPTION_MAX_CHARACTERS);
				}
				addIt.setString(11, description);
				
				
				
				addIt.setInt(12, new Integer(todaysDate.getDateFormatyyyyMMdd()).intValue());
				addIt.setInt(13, new Integer(todaysDate.getTimeFormathhmmss()).intValue());
				addIt.setString(14, updVb.getLastUpdateUser().trim());
				addIt.setInt(15, 0);
				addIt.setInt(16, 0);
				addIt.setString(17, updVb.getDeleteUser().trim());
				
				addIt.executeUpdate();
			} catch(Exception e)
			{
				throwError.append("error occured executing a ");
				throwError.append("add sql statement. " + e);
				
			// return connection.
			} finally {
				try {
					if (conn != null)
						ServiceConnection.returnConnectionStack14(conn);
					if (addIt != null)
						addIt.close();
				} catch(Exception el){
						el.printStackTrace();
				}
			}
		}
		

		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceKeyValue.");
			throwError.append("addKeyValue(KeyValue) keys(");
			throwError.append("key1:" + updVb.getKey1() +", ");
			throwError.append("key2:" + updVb.getKey2() +", ");
			throwError.append("key3:" + updVb.getKey3() +", ");
			throwError.append("key4:" + updVb.getKey4() +", ");
			throwError.append("key5:" + updVb.getKey5() +", ");
			throwError.append("val:" + updVb.getValue() +", ");
			throwError.append("desc:" + updVb.getDescription() +", ");
			throwError.append("user:" + updVb.getLastUpdateUser() +") ");
			throw new Exception(throwError.toString());
		}
		return guid;
	}
	
	
	/**
	 * Return a vector of KeyValue business objects using
	 * the KeyValue class for selection criteria.
	 * @param KeyValue keyValue.
	 * @return Vector of KeyValue objects 
	 * @throws Exception
	 */
	
	public static Vector buildKeyValueList(KeyValue keyValue)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnVector = new Vector();
		
		try {
			rtnVector = findKeyValueList(keyValue);
		} catch (Exception e)
		{
			throwError.append(e);
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("KeyValue.");
			throwError.append("buildKeyValueList(KeyValue). ");
			throw new Exception(throwError.toString());
		}
	
		// return value
		return rtnVector;
	}
	
	@Test
	public void testResort() throws Exception {
		
		KeyValue kv = new KeyValue();
		kv.setEnvironment("TST");
		kv.setStatus("");
		kv.setUniqueKey("A69EA539-198D-A131-1224-383CF043E3D8");
		kv.setEntryType("SpecRevisionComment5");
		kv.setSequence("30");
		kv.setKey1("78629");
		kv.setKey2("20120620");
		kv.setKey3("104559");
		kv.setKey4("");
		kv.setKey5("");
		kv.setDescription("");
		kv.setLastUpdateDate("");
		kv.setLastUpdateTime("");
		kv.setLastUpdateUser("JHAGLE");
		kv.setDeleteUser("");
		kv.setValue("Add");
		
		updateKeyValue(kv);
		
		resort(kv);
		
	}
	public static void resort(KeyValue kv) throws Exception {
		StringBuffer throwError = new StringBuffer();
		try
		{
			String thisUUID = kv.getUniqueKey();
			kv.setUniqueKey("");
			
			String thisValue = kv.getValue();
			kv.setValue("");
			
			String thisDescription = kv.getDescription();
			kv.setDescription("");
			
			String thisEnv = kv.getEnvironment();
			
			Vector<KeyValue> comments = buildKeyValueList(kv);
			
			//sort by sequence, date/time
			Collections.sort(comments, new Comparator<KeyValue>() {

				public int compare(KeyValue o1, KeyValue o2) {
					int c = 0;
					int seq1 = Integer.parseInt(o1.getSequence());
					int seq2 = Integer.parseInt(o2.getSequence());
					c = seq1 - seq2;
					
					if (c == 0) {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hhmmss");
							
							String time1 = "000000" + o1.getLastUpdateTime();
							time1 = time1.substring(time1.length()-6,time1.length());
							String dateStr1 = o1.getLastUpdateDate() + " " + time1;
							Date date1 = sdf.parse(dateStr1);
							
							String time2 = "000000" + o2.getLastUpdateTime();
							time2 = time2.substring(time2.length()-6,time2.length());
							String dateStr2 = o2.getLastUpdateDate() + " " + time2;
							Date date2 = sdf.parse(dateStr2);
							//sort descending (newest comments first)
							c = date2.compareTo(date1);
							
						} catch (Exception e) {}
					}
					
					return c;
				}
				
			});
			
			//reassign sequences
			int sequence = 10;
			for (KeyValue comment : comments) {
				comment.setEnvironment(thisEnv);
				
				int thisSeq = Integer.parseInt(comment.getSequence());
				if (thisSeq != sequence) {
					comment.setSequence(String.valueOf(sequence));
					updateSequence(comment);
				}

				sequence += 10;
			}
			
		} catch (Exception e)
		{
			throwError.append(e);
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("KeyValue.");
			throwError.append("resort(KeyValue). ");
			throw new Exception(throwError.toString());
		}
	
	}
	
	
	private static void updateSequence(KeyValue kv) throws Exception {
		StringBuffer throwError = new StringBuffer();
		
		Connection conn = null;
		Statement stmt = null;
		try
		{
			
			conn = ServiceConnection.getConnectionStack14();
			stmt = conn.createStatement();
			Vector parms = new Vector();
			parms.addElement(kv);
			String sql = buildSqlStatement("updateSequence", parms);
			stmt.executeUpdate(sql);
			
		} 
		
		catch (Exception e)
		{
			throwError.append(e);
		} 
		
		finally 
		{
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {}
			}
			if (conn != null) {
				try {
					ServiceConnection.returnConnectionStack14(conn);
				} catch (Exception e) {}
			}
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.Services.");
			throwError.append("KeyValue.");
			throwError.append("resort(KeyValue). ");
			throw new Exception(throwError.toString());
		}
			
		}
	
	/**
	 * Build an sql statement.
	 * @param InqReserveNumbers
	 * @return sql string
	 * @throws Exception
	 */

	private static String buildSqlStatement(String inRequestType,
											Vector requestClass)
		throws Exception 
	{
		StringBuffer sqlString = new StringBuffer();
		StringBuffer throwError = new StringBuffer();
		String thisDropDown = "";
		String library = "DBPRD";
		KeyValue keyValue = new KeyValue();
		try
		{
		   keyValue = (KeyValue) requestClass.elementAt(0);
		   library = GeneralUtility.getTTLibrary(keyValue.getEnvironment());
		   if (library == null ||
			   library.trim().equals(""))
			  library = "DBPRD";
		   // ******************************************
		   // Single entry selection criteria
		   try { 
			  if(inRequestType.equals("singleKeyValue"))
			  {
				// cast the incoming parameter class.
				// build the sql statement.
				sqlString.append("SELECT * FROM ");
				sqlString.append(library + ".GMPAKEY ");
				
				StringBuffer sqlWhere = new StringBuffer();
				sqlWhere.append(" WHERE GMASTS <> 'DL' ");
				sqlWhere.append(sqlWhereUniqueKey(keyValue.getUniqueKey().trim(), sqlWhere.toString()));

				sqlString.append(sqlWhere.toString());
			  }
			} catch (Exception e) {
				throwError.append(" Error building sql statement");
				throwError.append(" for a KeyValue entry.");
				throwError.append(" requestType singleKeyValue. " + e);
			}  
		   
			try { // List of GMPAKEY records that match selection criteria.
			  if(inRequestType.equals("keyValueList"))
			  {
					// build the sql statement.
				sqlString.append("SELECT * FROM ");
				sqlString.append(library + ".GMPAKEY ");
				
				StringBuffer sqlWhere = new StringBuffer();
				sqlWhere.append(" WHERE GMASTS <> 'DL' ");
				sqlWhere.append("AND GMATYP = '" + keyValue.getEntryType().trim() + "' ");
				
				sqlWhere.append(sqlWhereId(keyValue.getUniqueKey(), sqlWhere.toString()));
				
				sqlWhere.append(sqlWhereKey1(keyValue.getKey1(), sqlWhere.toString()));
				sqlWhere.append(sqlWhereKey2(keyValue.getKey2(), sqlWhere.toString()));
				sqlWhere.append(sqlWhereKey3(keyValue.getKey3(), sqlWhere.toString()));
				sqlWhere.append(sqlWhereKey4(keyValue.getKey4(), sqlWhere.toString()));
				sqlWhere.append(sqlWhereKey5(keyValue.getKey5(), sqlWhere.toString()));
				sqlWhere.append(sqlWhereDescription(keyValue.getDescription(), sqlWhere.toString()));
				sqlWhere.append(sqlWhereValue(keyValue.getValue(), sqlWhere.toString()));
				
				sqlString.append(sqlWhere.toString());
					
				String sqlOrderBy = sqlOrderBy(keyValue);
				sqlString.append(sqlOrderBy);
			  }
			} catch (Exception e) {
				throwError.append(" Error building sql statement");
				throwError.append(" for the KeyValue List.");
				throwError.append(" requestType keyValueList. " +e);
			}
			
			try { // Add a Key Value Entry.
			  if (inRequestType.equals("addKeyValue"))
			  {
				 //10/5/11 - TWalton - change to use a prepared Statement 
				// get a unique key value.
//				RandomGUID myGUID = new RandomGUID();
								
				// get current system date and time.
//				DateTime todaysDate = UtilityDateTime.getSystemDate();
				
				// build the sql statement.
//				sqlString.append("INSERT  INTO  " + library + ".GMPAKEY ");
//				sqlString.append("VALUES(");
//				sqlString.append("'" + keyValue.getStatus().trim() + "', ");
//				sqlString.append("'" + keyValue.getEntryType().trim() + "', "); 
//				sqlString.append("'" + keyValue.getSequence().trim() + "', ");
//				sqlString.append("'" + keyValue.getKey1().trim() + "', ");
//				sqlString.append("'" + keyValue.getKey2().trim() + "', ");
//				sqlString.append("'" + keyValue.getKey3().trim() + "', ");
//				sqlString.append("'" + keyValue.getKey4().trim() + "', ");
//				sqlString.append("'" + keyValue.getKey5().trim() + "', ");
//				sqlString.append("'" + myGUID.toString().trim() + "', ");
//				sqlString.append("'" + keyValue.getValue().trim() + "', ");
//				sqlString.append("'" + keyValue.getDescription().trim() + "', ");
//				sqlString.append(todaysDate.getDateFormatyyyyMMdd() + ", ");
//				sqlString.append(todaysDate.getTimeFormathhmmss() + ", ");
//				sqlString.append("'" + keyValue.getLastUpdateUser().trim() + "', ");
//				sqlString.append(0 + ", ");
//				sqlString.append(0 + ", ");
//				sqlString.append("'" + keyValue.getDeleteUser().trim() + "') ");
				sqlString.append("INSERT  INTO  " + library + ".GMPAKEY ");
				sqlString.append("(GMASTS, GMATYP, GMASEQ, GMAKY1,");
				sqlString.append(" GMAKY2, GMAKY3, GMAKY4, GMAKY5,");
				sqlString.append(" GMAUNQ, GMAVAL, GMADSC, GMALDT,");
				sqlString.append(" GMALTM, GMALUR, GMADDT, GMADTM,");
				sqlString.append(" GMADUR) VALUES(?,?,?,?,");
				sqlString.append("?,?,?,?, ?,?,?,?, ?,?,?,?, ?)");
			  }
			} catch (Exception e) {
				throwError.append(" Error building sql statement");
				throwError.append(" for addKeyValue request. " + e);
			}
			
			try { // Delete Key Value.
			  if (inRequestType.equals("deleteKeyValue"))
			  {
//				 get current system date and time.
				 DateTime todaysDate = UtilityDateTime.getSystemDate();
									
				// build the sql statement.
				sqlString.append("UPDATE " + library + ".GMPAKEY ");
				sqlString.append("SET GMASTS = 'DL', ");
				sqlString.append("GMADDT = " + todaysDate.getDateFormatyyyyMMdd() + ", ");
				sqlString.append("GMADTM = " + todaysDate.getTimeFormathhmmss() + ", ");
				sqlString.append("GMADUR = '" + keyValue.getDeleteUser().trim() + "' ");
				sqlString.append("WHERE GMAUNQ = '" + keyValue.getUniqueKey().trim() + "' ");
			  }
			} catch (Exception e) {
				throwError.append(" Error building sql statement");
				throwError.append(" for deleteKeyValue request. " + e);
			}
			
			try { // Update KeyValue Entries.  
			  if (inRequestType.equals("updateKeyValue"))
			  {
				  //10/5/11 - TWalton - change to use a prepared Statement 
				// get current system date and time.
//				 DateTime todaysDate = UtilityDateTime.getSystemDate();

				// build the sql statement.
//				sqlString.append("UPDATE " + library + ".GMPAKEY ");
//				sqlString.append(" SET GMASTS = '" + keyValue.getStatus().trim() + "',");
//				sqlString.append(" GMATYP = '" + keyValue.getEntryType().trim() + "',");
//				sqlString.append(" GMASEQ = '" + keyValue.getSequence().trim() + "',");
//				sqlString.append(" GMAKY1 = '" + keyValue.getKey1().trim() + "',");
//				sqlString.append(" GMAKY2 = '" + keyValue.getKey2().trim() + "',");
//				sqlString.append(" GMAKY3 = '" + keyValue.getKey3().trim() + "',");
//				sqlString.append(" GMAKY4 = '" + keyValue.getKey4().trim() + "',");
//				sqlString.append(" GMAKY5 = '" + keyValue.getKey5().trim() + "',");
//				sqlString.append(" GMAUNQ = '" + keyValue.getUniqueKey().trim() + "',");
//				sqlString.append(" GMAVAL = '" + keyValue.getValue().trim() + "',");
//				sqlString.append(" GMADSC = '" + keyValue.getDescription().trim() + "',");
					// get current system date and time.
//				sqlString.append(" GMALDT = " + todaysDate.getDateFormatyyyyMMdd() + ",");
//				sqlString.append(" GMALTM = " + todaysDate.getTimeFormathhmmss() + ",");
//				sqlString.append(" GMALUR = '" + keyValue.getLastUpdateUser().trim() + "',");
//				sqlString.append(" GMADDT = " + new Integer(keyValue.getDeleteDate().trim()) + ",");
//				sqlString.append(" GMADTM = " + new Integer(keyValue.getDeleteTime().trim()) + ",");
//				sqlString.append(" GMADUR = '" + keyValue.getDeleteUser().trim() + "'");
					
//				sqlString.append(" WHERE GMAUNQ = '" + keyValue.getUniqueKey().trim() + "' ");
				
				sqlString.append("UPDATE  " + library + ".GMPAKEY ");
				sqlString.append("SET ");
				//GMASTS = ?,");
				//sqlString.append(" GMATYP = ?,"); // Type will not change
				sqlString.append(" GMASEQ = ?,");
				//sqlString.append(" GMAKY1 = ?,"); // Keys will not change
				//sqlString.append(" GMAKY2 = ?,"); // Keys will not change
				//sqlString.append(" GMAKY3 = ?,"); // Keys will not change
				//sqlString.append(" GMAKY4 = ?,"); // Keys will not change
				//sqlString.append(" GMAKY5 = ?,"); // Keys will not change
				//sqlString.append(" GMAUNQ = ?,"); // Unique Number will not change
				sqlString.append(" GMAVAL = ?,");
				sqlString.append(" GMADSC = ?,");
				sqlString.append(" GMALDT = ?,");
				sqlString.append(" GMALTM = ?,");
				sqlString.append(" GMALUR = ?");
				//sqlString.append(" GMADDT = ?,"); // Delete Date will not change
				//sqlString.append(" GMADTM = ?,"); // Delete Time will not change
				//sqlString.append(" GMADUR = ? "); // Delete User will not change
				sqlString.append(" WHERE GMAUNQ = ? ");
				
			  }
			} catch (Exception e) {
					throwError.append(" Error building sql statement");
					throwError.append(" for updateKeyValue request. " +e ); 
			}
			
			try { // Replace the Keys for a Specific KeyValue Entries.  
				  if (inRequestType.equals("replaceKeys"))
				  {

					  KeyValue newKeys = (KeyValue) requestClass.elementAt(1);
					  
					// build the sql statement.
					sqlString.append("UPDATE " + library + ".GMPAKEY ");
					sqlString.append(" SET GMAKY1 = '" + newKeys.getKey1().trim() + "',");
					sqlString.append(" GMAKY2 = '" + newKeys.getKey2().trim() + "',");
					sqlString.append(" GMAKY3 = '" + newKeys.getKey3().trim() + "',");
					sqlString.append(" GMAKY4 = '" + newKeys.getKey4().trim() + "',");
					sqlString.append(" GMAKY5 = '" + newKeys.getKey5().trim() + "',");
					sqlString.append(" GMALDT = " + newKeys.getLastUpdateDate() + ",");
					sqlString.append(" GMALTM = " + newKeys.getLastUpdateTime() + ",");
					sqlString.append(" GMALUR = '" + newKeys.getLastUpdateUser().trim() + "' ");
						
					sqlString.append(" WHERE GMATYP LIKE '" + keyValue.getEntryType().trim() + "%' ");
					sqlString.append("   AND GMAKY1 = '" + keyValue.getKey1().trim() + "' ");
					sqlString.append("   AND GMAKY2 = '" + keyValue.getKey2().trim() + "' ");
					sqlString.append("   AND GMAKY3 = '" + keyValue.getKey3().trim() + "' ");
					sqlString.append("   AND GMAKY4 = '" + keyValue.getKey4().trim() + "' ");
					sqlString.append("   AND GMAKY5 = '" + keyValue.getKey5().trim() + "' ");
				  }
				} catch (Exception e) {
						throwError.append(" Error building sql statement");
						throwError.append(" for replaceKeys request. " +e ); 
				}
		}catch(Exception e){
		   throwError.append("Error Caught ServiceKeyValue.buildSQLStatement" + e);
		}
		
		try { //Set the sequence value based on UUID
			  if(inRequestType.equals("updateSequence"))
			  {

				sqlString.append(" UPDATE ");
				sqlString.append(  library + ".GMPAKEY ");
				sqlString.append(" SET GMASEQ='" + keyValue.getSequence() + "' ");
				sqlString.append(" WHERE GMAUNQ='" + keyValue.getUniqueKey() + "' ");
				
			  }
			} catch (Exception e) {
				throwError.append(" Error building sql statement");
				throwError.append(" for the KeyValue List.");
				throwError.append(" requestType updateSequence. " +e);
			}
		
		
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceKeyValue.");
			throwError.append("buildSqlStatement(String, Vector)");
			throw new Exception(throwError.toString());
		}
		return sqlString.toString();
	}
	
	
	/**
	 * Delete Key Value combonations for 
	 * the incoming Key Value business object.
	 * @param KeyValue.
	 * @throws Exception
	 */
	public static void deleteKeyValue(KeyValue keyValue)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String requestType = "";
		String sqlDelete = "";
		Connection conn = null;
		PreparedStatement deleteIt = null;
		
		// verify base class initialization.
		ServiceKeyValue sk = new ServiceKeyValue();
		
		// edit incoming data prior to add.
		if (keyValue.getUniqueKey() == null ||
			keyValue.getUniqueKey().trim().equals(""))
		{
			throwError.append(" Unable to complete the delete ");
			throwError.append("request. Invalid key value. ");
		}
			
		
		// if primary edits pass contiue update process.
		if (throwError.toString().equals(""))
		{	
			try {
				// Pass along the incoming parameter. 
				Vector parmClass = new Vector();
				parmClass.addElement(keyValue);
				
				// get the sql statements.
				requestType = "deleteKeyValue";
				sqlDelete = buildSqlStatement(requestType, parmClass);
			} catch(Exception e) {
				throwError.append(" Error getting sql statement. ");
				throwError.append(e);
			}
		}
		
		// get a connection, execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
				// 9/24/08 TWalton Change to use connection Stack
				//conn = getDBConnection();
				// 1/16/12 TWalton change to user ServiceConnection
				//conn = ConnectionStack.getConnection();
				conn = ServiceConnection.getConnectionStack14();
				deleteIt = conn.prepareStatement(sqlDelete);
				deleteIt.executeUpdate();
			} catch(Exception e)
			{
				throwError.append("error occured executing a ");
				throwError.append("delete sql statement. " + e);
				
			// return connection.
			} finally {
				try {
					if (conn != null)	
						ServiceConnection.returnConnectionStack14(conn);
					if (deleteIt != null)
						deleteIt.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceKeyValue.");
			throwError.append("deletKeyValue(key:" + keyValue.getUniqueKey().trim() +") ");
			throw new Exception(throwError.toString());
		}
		return;	
	}
	
	
	/**
	 * 
	 * @param keyValue
	 * @return 
	 * @throws Exception
	 */
	private static KeyValue edit(KeyValue updVb, String fromWhere)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		
		if (updVb.getEntryType() == null ||
			updVb.getEntryType().trim().equals("") ||
			updVb.getKey1() == null ||
			updVb.getKey1().trim().equals(""))
		{
			throwError.append(" Edits failed.");
			throwError.append(" Invalid key values. ");
		}
		
		if (fromWhere.equals("update"))
		{
			if (updVb.getUniqueKey() == null ||
				updVb.getUniqueKey().trim().equals(""))
			{
				throwError.append(" Edits failed.");
				throwError.append(" Invalid Unique Key value. ");
			}
		}
		
		if (updVb.getSequence() == null ||
			updVb.getSequence().trim().equals(""))
			updVb.setSequence("0");
		
		if (!throwError.toString().equals(""))
			throw new Exception(throwError.toString());
		
		return updVb;
	}
	
	
	/**
	 *  Return a vector of KeyValue business objects.
	 *  Use the incoming KeyValue class for selection
	 * criteria against file DBPRD/GMPAKEY.
	 * @param KeyValue
	 * @return Vector of KeyValue objects.
	 * @throws Exception
	 */
	private static Vector findKeyValueList(KeyValue keyValue)
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		Vector rtnVector = new Vector();
		String sqlString = "";
		Connection conn = null;
		PreparedStatement findIt = null;
		ResultSet rs = null;
		
		// verify base class initialization.
		ServiceKeyValue sk = new ServiceKeyValue();
		
		
		// get sql statement for KeyValue List.
		if (throwError.toString().equals(""))
		{
			try {
				Vector parmClass = new Vector();
				parmClass.addElement(keyValue);
				sqlString = buildSqlStatement("keyValueList", parmClass);
			} catch (Exception e) {
			throwError.append(" error trying to get sqlString. " + e);
			}
		}
		
		// get a connection. execute sql, build return object.
		if (throwError.toString().equals("")) {
			try {
				// 9/24/08 TWalton Change to use connection stack
				//conn = getDBConnection();
				// 1/16/12 TWalton change to user connection stack
				//conn = ConnectionStack.getConnection();
				conn = ServiceConnection.getConnectionStack14();
				findIt = conn.prepareStatement(sqlString);
				rs = findIt.executeQuery();
				
				while (rs.next())
				{
					KeyValue kv = new KeyValue();
					kv = loadFieldsKeyValue(rs, "keyValueList");
					rtnVector.addElement(kv);
				}
				
			} catch (Exception e) {
				throwError.append(" error occured executing a sql statement. " + e);
	
				// return connection.
			} finally {
				try {
					if (conn != null) 
						ServiceConnection.returnConnectionStack14(conn);
					if (findIt != null)
						findIt.close();
					if (rs != null)
						rs.close();
				} catch (Exception el) {
					el.printStackTrace();
				}
			}
		}
		
		// return data.
	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceKeyValue.");
			throwError.append("findKeyValueList(KeyValue). ");
			throw new Exception(throwError.toString());
		}
		return rtnVector;
	}
	
	
	/**
	 * Load class fields from result set.
	 * 
	 */
	
	public static KeyValue loadFieldsKeyValue(ResultSet rs, 
											  String type)
			throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		KeyValue kv = new KeyValue();

		try{ // sqlStatement("keyValueList")
			
			if (type.equals("keyValueList"))
			{
				kv.setStatus(rs.getString("GMASTS").trim());
				kv.setEntryType(rs.getString("GMATYP").trim());
				kv.setSequence(rs.getString("GMASEQ").trim());
				kv.setKey1(rs.getString("GMAKY1").trim());
				kv.setKey2(rs.getString("GMAKY2").trim());
				kv.setKey3(rs.getString("GMAKY3").trim());
				kv.setKey4(rs.getString("GMAKY4").trim());
				kv.setKey5(rs.getString("GMAKY5").trim());
				kv.setUniqueKey(rs.getString("GMAUNQ").trim());
				kv.setValue(rs.getString("GMAVAL").trim());
				kv.setDescription(rs.getString("GMADSC").trim());
				
				// convert last update date for display.
				String date = "0";
				String field = "";
				
				if (rs.getInt("GMALDT") != 0)
				{
					field = rs.getString("GMALDT");
					date = field.substring(4,6) + "/"; //mm
					date = date + field.substring(6,8) + "/"; //dd
					date = date + field.substring(0,4);
				}
				kv.setLastUpdateDate(date);
				
				kv.setLastUpdateTime(rs.getString("GMALTM").trim());
				kv.setLastUpdateUser(rs.getString("GMALUR").trim());
				kv.setDeleteDate(rs.getString("GMADDT").trim());
				kv.setDeleteTime(rs.getString("GMADTM").trim());
				kv.setDeleteUser(rs.getString("GMADUR").trim());
			}

		} catch(Exception e)
		{
			throwError.append(" Problem loading the class ");
			throwError.append("from the result set. ");
		}
		
		// return data.
		
		if (!throwError.toString().equals("")) {
			throwError.append("Error @ com.treetop.services.");
			throwError.append("ServiceKeyValue.");
			throwError.append("loadFieldsKeyValue(rs, type:");
			throwError.append(type + "). ");
			throw new Exception(throwError.toString());
		}
		return kv;
	}
	
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		// Test all that you can.
		String stophere = "";		
		Vector vector = null;
		
		try {
			
			
			
			if (true) {
				
				KeyValue kv = new KeyValue();
				kv.setStatus("");
				kv.setEntryType("ItemNewComment");
				kv.setSequence("0");
				kv.setKey1("100001");
				kv.setKey2("");
				kv.setKey3("");
				kv.setKey4("");
				kv.setKey5("");
				kv.setDescription("");
				kv.setLastUpdateDate("");
				kv.setLastUpdateTime("");
				kv.setLastUpdateUser("JHAGLE");
				kv.setDeleteUser("");
				kv.setValue("other new comments");

				
				addKeyValue(kv);
				
				vector = buildKeyValueList(kv);
				
				//KeyValue comment = (KeyValue) vector.elementAt(0);
				//deleteKeyValue(comment);
				
			}
			
			
		//*** test buildKeyValueList(KeyValue)
			if ("x".equals("x"))
			{
				KeyValue kv = new KeyValue();	
				// test list access.
				kv.setEntryType("RESOURCE");
				vector = buildKeyValueList(kv);
				stophere = "x";
			}
			
		//*** test add of a key value entry
		//	  also duplicate key error if run twice w/o delete.
			if ("x".equals("y"))
			{
				KeyValue kv = new KeyValue();
				kv.setStatus("");
				kv.setEntryType("RESOURCE");
				kv.setSequence("1");
				kv.setKey1("addKey1");
				kv.setKey2("addKey2");
				kv.setKey3("addKey3");
				kv.setKey4("addKey4");
				kv.setKey5("addKey5");
				kv.setUniqueKey("");
				kv.setValue("add test value");
				kv.setDescription("add test description");
				kv.setLastUpdateDate("11-28-2005");
				kv.setLastUpdateTime("120001");
				kv.setLastUpdateUser("THAILE");
				kv.setDeleteDate("0");
				kv.setDeleteTime("0");
				kv.setDeleteUser("");

				kv.setOrderBy("");
				kv.setOrderStyle("");

				ServiceKeyValue.addKeyValue(kv);
				stophere = "x";
				
				// get the list of just added entries.
				Vector blastThem = ServiceKeyValue.buildKeyValueList(kv);
				
				for (int z = 0; z < blastThem.size(); z++)
				{
					KeyValue goodBye = (KeyValue) blastThem.elementAt(z);
					goodBye.setDescription("updated description");
					ServiceKeyValue.updateKeyValue(goodBye);
					stophere = "x";
					ServiceKeyValue.deleteKeyValue(goodBye);
					stophere = "x";
				}
			}
				
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	

	/**
	 * Return a sql order by clause.
	 *
	 * Creation date: (11/16/2005 1:36:19 PM)
	 */
	public static String sqlOrderBy(KeyValue keyValue)
										 
			throws Exception 
	{
		String testOrderBy = keyValue.getOrderBy();
		String testOrderStyle = keyValue.getOrderStyle();
		String sqlOrderBy = "";
		StringBuffer throwError = new StringBuffer();
		
		try 
		{
			if (keyValue.getEntryType() != null 
					&& keyValue.getEntryType().toLowerCase().equals("resourcenewcomment")) {
				
				sqlOrderBy = " ORDER BY " + " GMALDT DESC, " + " GMALTM DESC ";
				
			} else if (keyValue.getOrderBy() != null && keyValue.getOrderBy().toLowerCase().equals("keysdate") ) {
				sqlOrderBy = " ORDER BY GMAKY1, GMAKY2, GMAKY3, GMAKY4, GMAKY5, GMALDT||RIGHT('000000'||GMALTM,6) ";
			} else if (keyValue.getOrderBy() != null && keyValue.getOrderBy().toLowerCase().equals("keysseq") ) {
				//sqlOrderBy = " ORDER BY GMAKY1, GMAKY2, GMAKY3, GMAKY4, GMAKY5, GMASEQ, GMALDT||RIGHT('000000'||GMALTM,6) ";
				sqlOrderBy = " ORDER BY GMAKY1, GMAKY2, GMAKY3, GMAKY4, GMAKY5, cast(gmaseq as int), GMALDT||RIGHT('000000'||GMALTM,6) ";
			} else if (keyValue.getOrderBy() != null && keyValue.getOrderBy().toLowerCase().equals("description") ) {
				sqlOrderBy = " ORDER BY GMADSC, GMAVAL, GMALDT||RIGHT('000000'||GMALTM,6) ";
			} else {
				
				if (testOrderBy == null || testOrderBy.trim().equals("")) {
					testOrderBy = "keyFields";
				}

				if (testOrderStyle == null) {
					testOrderStyle = "";
				}

				if (testOrderBy.toLowerCase().equals("keyfields")) {
					//sqlOrderBy = " Order BY " + "  GMASEQ " + testOrderStyle + " " + ", GMAKY1 " + testOrderStyle + " "
					sqlOrderBy = " Order BY " + "  cast(gmaseq as int) " + testOrderStyle + " " + ", GMAKY1 " + testOrderStyle + " "
							+ ", GMAKY2 " + testOrderStyle + " " + ", GMAKY3 " + testOrderStyle + " " + ", GMAKY4 "
							+ testOrderStyle + " ";
				}
			}

		}
		catch (Exception e) {
			throwError.append("Error setting sql ORDER BY. " + e);
		}
		
		if (!throwError.toString().equals(""))
		{
			throwError.append(" - Exception error at com.treetop.services.");
			throwError.append("ServiceKeyValue.sqlOrderBy(keyValue). "); 
			throw new Exception(throwError.toString());
		}
		
		return sqlOrderBy; 	
	
	}

	
	/**
	 * Add the Key Value Description "WHERE" clause.
	 *
	 */
	private static String sqlWhereDescription(String description,
											  String whereClause)
							throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((description != null) && 
				(!description.trim().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
				int foundNotEql  = whereClause.indexOf("<>");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0) || (foundNotEql >= 0))
					sqlWhere = sqlWhere + "AND ";
			
				sqlWhere = sqlWhere +
							"(GMADSC LIKE '%" + description.trim() + "%') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceKeyValue.sqlWhereDescription" +
						 "(dsec: " + description.trim() +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}
	
	/**
	 * Add the key1 "WHERE" clause.
	 *
	 */
	private static String sqlWhereId(String id,
									   String whereClause)
		throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((id != null) && 
				(!id.trim().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
				int foundNotEql  = whereClause.indexOf("<>");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0) || (foundNotEql >= 0))
					sqlWhere = sqlWhere + "AND ";		
			
				sqlWhere = sqlWhere +
							"(GMAUNQ = '" + id.trim() + "') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceKeyValue.sqlWhereKey1" +
						 "(key1: " + id.trim() +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}
	
	/**
	 * Add the key1 "WHERE" clause.
	 *
	 */
	private static String sqlWhereKey1(String key1,
									   String whereClause)
		throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((key1 != null) && 
				(!key1.trim().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
				int foundNotEql  = whereClause.indexOf("<>");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0) || (foundNotEql >= 0))
					sqlWhere = sqlWhere + "AND ";		
			
				sqlWhere = sqlWhere +
							"(GMAKY1 = '" + key1.trim() + "') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceKeyValue.sqlWhereKey1" +
						 "(key1: " + key1.trim() +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}

	/**
	 * Add the key2 "WHERE" clause.
	 *
	 */
	private static String sqlWhereKey2(String key2,
									   String whereClause)
		throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((key2 != null) && 
				(!key2.trim().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
				int foundNotEql  = whereClause.indexOf("<>");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0) || (foundNotEql >= 0))
					sqlWhere = sqlWhere + "AND ";
			
				sqlWhere = sqlWhere +
							"(GMAKY2 = '" + key2.trim() + "') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceKeyValue.sqlWherekey2" +
						 "(key2: " + key2.trim() +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}

	/**
	 * Add the key3 "WHERE" clause.
	 *
	 */
	private static String sqlWhereKey3(String key3,
									   String whereClause)
		throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((key3 != null) && 
				(!key3.trim().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
				int foundNotEql  = whereClause.indexOf("<>");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0) || (foundNotEql >= 0))
					sqlWhere = sqlWhere + "AND ";
			
				sqlWhere = sqlWhere +
							"(GMAKY3 = '" + key3.trim() + "') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceKeyValue.sqlWherekey3" +
						 "(key3: " + key3.trim() +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}

	/**
	 * Add the key4 "WHERE" clause.
	 *
	 */
	private static String sqlWhereKey4(String key4,
									   String whereClause)
		throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((key4 != null) && 
				(!key4.trim().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
				int foundNotEql  = whereClause.indexOf("<>");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0) || (foundNotEql >= 0))
					sqlWhere = sqlWhere + "AND ";
			
				sqlWhere = sqlWhere +
							"(GMAKY4 = '" + key4.trim() + "') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceKeyValue.sqlWherekey4" +
						 "(key4: " + key4.trim() +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}

	/**
	 * Add the key5 "WHERE" clause.
	 *
	 */
	private static String sqlWhereKey5(String key5,
									   String whereClause)
		throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((key5 != null) && 
				(!key5.trim().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
				int foundNotEql  = whereClause.indexOf("<>");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0) || (foundNotEql >= 0))
					sqlWhere = sqlWhere + "AND ";
			
				sqlWhere = sqlWhere +
							"(GMAKY5 = '" + key5.trim() + "') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceKeyValue.sqlWherekey5" +
						 "(key5: " + key5.trim() +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}
	
	
	/**
	 * Add the key5 "WHERE" clause.
	 *
	 */
	private static String sqlWhereUniqueKey(String uniqueKey,
									   String whereClause)
		throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((uniqueKey != null) && 
				(!uniqueKey.trim().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
				int foundNotEql  = whereClause.indexOf("<>");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0) || (foundNotEql >= 0))
					sqlWhere = sqlWhere + "AND ";
			
				sqlWhere = sqlWhere +
							"(GMAUNQ = '" + uniqueKey.trim() + "') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceKeyValue.sqlWhereUniqueKey" +
						 "(uniqueKey: " + uniqueKey.trim() +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}
	
	
	
	/**
	 * Add the key1 "WHERE" clause.
	 *
	 */
	private static String sqlWhereValue(String value,
									   String whereClause)
		throws Exception
	{
		String  sqlWhere = "";
		String	throwError = "";
		
		try {			
		
			if ((value != null) && 
				(!value.trim().equals("")))
			{					   		
				int foundWhere   = whereClause.indexOf("=");
				int foundLike    = whereClause.indexOf("LIKE");
				int foundBetween = whereClause.indexOf("BETWEEN");
				int foundNotEql  = whereClause.indexOf("<>");
			 
				if ((foundWhere >= 0) || (foundLike >= 0) || 
					(foundBetween >= 0) || (foundNotEql >= 0))
					sqlWhere = sqlWhere + "AND ";
			
				sqlWhere = sqlWhere +
							"(GMAVAL = '" + value.trim() + "') ";
			}		 
		}
	
		catch (Exception e) {
			throwError = "error adding to the where clause. " + e;
		}
	
		if (!throwError.equals(""))
		{
			throwError = throwError +
						 " - Exception error at com.treetop.services." +
						 "ServiceKeyValue.sqlWhereValue" +
						 "(value: " + value.trim() +
						 " whereClause:" + whereClause + ")";
		}
		return sqlWhere; 	 
	}
	
	
	/**
	 * Update KeyValue file using KeyValue bean. 
	 * @param KeyValue.
	 * @return void.
	 */
	public static void updateKeyValue(KeyValue updVb)	
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Vector parmClass = new Vector();
		String requestType = "";
		String sqlExists = "";
		String sqlUpdate = "";
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		
		// verify base class initialization.
		ServiceKeyValue sk = new ServiceKeyValue();
			
		// edit incoming data prior to update.
		try {
			updVb = edit(updVb, "update");
		} catch (Exception e) {
			throwError.append(e);
		}

		// Continue update process.
		if (throwError.toString().equals(""))
		{	
			try {
				// Pass along the incoming view bean information. 
				parmClass = new Vector();
				parmClass.addElement(updVb);
				
				// get the sql statement.
				requestType = "singleKeyValue";
				sqlExists = buildSqlStatement(requestType, parmClass);
				
				// get the sql statement.
				requestType = "updateKeyValue";
				sqlUpdate = buildSqlStatement(requestType, parmClass);
				
			} catch(Exception e) {
				throwError.append(e);
			}
		}
		
		// get a connection. execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
				// 9/24/08 TWalton Change to use connection stack and point to NEW Box
				//conn = getDBConnection();
				// 1/16/12 TWalton - change to use ServiceConnection
				//conn = ConnectionStack.getConnection();
				conn = ServiceConnection.getConnectionStack14();
//				 10/5/11 TWalton - Changed to use Prepared Statements instead of dynamic build of insert	
				DateTime todaysDate = UtilityDateTime.getSystemDate();
				
				preStmt = conn.prepareStatement(sqlExists);
				rs = preStmt.executeQuery();
				
				if (rs.next())
				{
					// Verify change occurred prior to update.
					KeyValue oldVb = loadFieldsKeyValue(rs, "keyValueList");
					
					if (!oldVb.getSequence().trim().equals(updVb.getSequence().trim()) ||
						!oldVb.getDescription().trim().equals(updVb.getDescription().trim()) ||
						!oldVb.getValue().trim().equals(updVb.getValue().trim()) )
					{
						preStmt = conn.prepareStatement(sqlUpdate);
						preStmt.setString(1, updVb.getSequence().trim());
						
						
						
						String value = FindAndReplace.sanitizeEncoding(updVb.getValue().trim());
						int valueLength = value.length();
						if (valueLength > VALUE_MAX_CHARACTERS) {
							value = value.substring(0,VALUE_MAX_CHARACTERS);
						}
						
						preStmt.setString(2, value);
						
						String description = FindAndReplace.sanitizeEncoding(updVb.getDescription().trim());
						int descriptionLength = value.length();
						if (descriptionLength > DESCRIPTION_MAX_CHARACTERS) {
							description = description.substring(0,DESCRIPTION_MAX_CHARACTERS);
						}
						
						preStmt.setString(3, description);
						
						preStmt.setInt(4, new Integer(todaysDate.getDateFormatyyyyMMdd()).intValue());
						preStmt.setInt(5, new Integer(todaysDate.getTimeFormathhmmss()).intValue());
						preStmt.setString(6, updVb.getLastUpdateUser().trim());
						preStmt.setString(7, updVb.getUniqueKey().trim());
						
						preStmt.executeUpdate();
					}
				} else
				{
					throwError.append(" Unable to update the ");
					throwError.append("KeyValue. The KeyValue is not ");
					throwError.append("currently defined (not there).");	
				}
			} catch(Exception e)
			{
				throwError.append("error occured executing a ");
				throwError.append("update sql statement. " + e);
				
			// return connection.
			} finally {
				try {
					if (conn != null)
					   ServiceConnection.returnConnectionStack14(conn);
					if (rs != null)
					   rs.close();
					if (preStmt != null)
					   preStmt.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		
		
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceKeyValue.");
			throwError.append("updateKeyValue(UpdKeyValue) keys(");
			throwError.append("unqKey:" + updVb.getUniqueKey().trim() + ", ");
			throwError.append("key1:" + updVb.getKey1() +", ");
			throwError.append("key2:" + updVb.getKey2() +", ");
			throwError.append("key3:" + updVb.getKey3() +", ");
			throwError.append("key4:" + updVb.getKey4() +", ");
			throwError.append("key5:" + updVb.getKey5() +", ");
			throwError.append("val:" + updVb.getValue() +", ");
			throwError.append("desc:" + updVb.getDescription() +", ");
			throwError.append("user:" + updVb.getLastUpdateUser() +") "); 
			throw new Exception(throwError.toString());
		}
		
	}
	/**
	 * Update KeyValue file using KeyValue bean. 
	 * @param KeyValue.
	 * @return void.
	 */
	public static void updateReplaceKeys(KeyValue fromKeys, KeyValue toKeys)	
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Vector parmClass = new Vector();
		String requestType = "replaceKeys";
		String sqlUpdate = "";
		Connection conn = null;
		PreparedStatement preStmt = null;
		
		// verify base class initialization.
		ServiceKeyValue sk = new ServiceKeyValue();
			
		try {
			// Pass along the incoming view bean information. 
			parmClass = new Vector();
			parmClass.addElement(fromKeys);
			parmClass.addElement(toKeys);
				
			// get the sql statement.
			sqlUpdate = buildSqlStatement(requestType, parmClass);
				
		} catch(Exception e) {
			throwError.append(e);
		}
		
		// get a connection. execute sql, build return vector.
		if (throwError.toString().equals(""))
		{
			try {
				// 9/24/08 TWalton Change to use connection stack and point to NEW Box
				//conn = getDBConnection();
				// 1/16/12 TWalton change to use Service Connection
				//conn = ConnectionStack.getConnection();
				conn = ServiceConnection.getConnectionStack14();
				preStmt = conn.prepareStatement(sqlUpdate);
				preStmt.executeUpdate();
			} catch(Exception e)
			{
				throwError.append("error occured executing an update sql statement. " + e);
				
			// return connection.
			} finally {
				try {
					if (conn != null)
						ServiceConnection.returnConnectionStack14(conn);
					if (preStmt != null)
						preStmt.close();
				} catch(Exception el){
					el.printStackTrace();
				}
			}
		}
		
		// return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.services.");
			throwError.append("ServiceKeyValue.updateReplaceKeys(KeyValue, KeyValue)");
			throw new Exception(throwError.toString());
		}
		
	}
	
	
}
