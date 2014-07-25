package com.treetop.services;


import com.treetop.utilities.GeneralUtility;
import com.treetop.viewbeans.CommonRequestBean;
import org.json.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class ServiceOperationsUtilities {
	
	
	
	private static class BuildSQL {
		
		/**
		 * 
		 */
	
		private static String sqlGetUtilitiesByPlantYearWeek(CommonRequestBean crb, String endDate ) throws Exception {
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			try {
		
				// Determine Library
				String library = GeneralUtility.getLibrary(crb.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(crb.getEnvironment()) + ".";
				
		
				sqlString.append("SELECT ");
				// CSYCAL   - Days in a year
				// DCPALL   - Descriptive Code File (Utility definitions)
				// GNPGUTIL - Utility data
		
				sqlString.append("CDYMD8, DCPK01, DCPT40, ifnull(GNGVL1,0) AS GNGVL1, ifnull(GNGVL2,0) AS GNGVL2 ");
		
				sqlString.append("FROM " + library + "CSYCAL ");
		
				sqlString.append("INNER JOIN " + ttLibrary + "DCPALL ");
				sqlString.append("ON DCPK00 = 'UTIL' ");
				sqlString.append("AND DCPK01 = '" + crb.getIdLevel1() + "' ");
				
				sqlString.append("LEFT OUTER JOIN " + ttLibrary + "GNPGUTIL ");
				sqlString.append("ON DCPK00 = GNGK00 AND DCPK01 = GNGK01 ");
				sqlString.append("AND DCPT40 = GNGK02 AND GNGDTE = CDYMD8 ");
		
				sqlString.append("WHERE ");
				sqlString.append("CDCONO = " + crb.getCompanyNumber() + " ");
				sqlString.append("AND CDDIVI = '" + crb.getDivisionNumber() + "' ");
				sqlString.append("AND CDYMD8 >= " + crb.getIdLevel2().trim() + " ");
				sqlString.append("AND CDYMD8 <= " + endDate + " ");
				sqlString.append("ORDER BY CDYMD8, DCPK01, DCPN01 ");
		
			} catch (Exception e) {
				throwError.append(" Error building SQL statement. " + e);
			}
		
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsUtilities.");
				throwError.append("BuildSQL.sqlGetUtilitiesByPlantYearWeek(CommonRequestBean) ");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * 
		 */
		
		private static String sqlGetLatestAvailableRate(CommonRequestBean crb, ResultSet rs, String endDate ) throws Exception {
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			
			try {
		
				// Determine Library
				String library = GeneralUtility.getLibrary(crb.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(crb.getEnvironment()) + ".";
				
				sqlString.append("SELECT ");	
				sqlString.append(" B.GNGVL2 ");
		
				sqlString.append(" \r ");				
				sqlString.append(" FROM ( ");
				sqlString.append(" \r ");
				
				//get the max date and keys
				sqlString.append(" SELECT GNGK00, GNGK01, GNGK02, MAX(GNGDTE) AS GNGDTE ");
				sqlString.append(" \r ");
				sqlString.append(" FROM " + ttLibrary + "GNPGUTIL ");
				sqlString.append(" \r ");
				sqlString.append(" WHERE GNGK00 = 'UTIL' ");
				sqlString.append(" AND GNGK01 = '" + rs.getString("DCPK01").trim() + "' ");
				sqlString.append(" AND GNGK02 = '" + rs.getString("DCPT40").trim() + "' ");
				//sqlString.append(" AND GNGDTE <= " + rs.getString("CDYMD8").trim() + " " );
				sqlString.append(" AND GNGDTE <= " + endDate + " " );
				sqlString.append(" AND GNGVL2 <> 0 ");
				sqlString.append(" \r ");
				sqlString.append(" GROUP BY GNGK00, GNGK01, GNGK02 ");
				
				sqlString.append(" \r ");
				sqlString.append(" ) AS A ");
				
				sqlString.append(" \r ");
				
				//get the value associated with those keys and max date
				sqlString.append(" LEFT OUTER JOIN " + ttLibrary + "GNPGUTIL AS B ON ");
				sqlString.append(" A.GNGK00=B.GNGK00 AND A.GNGK01=B.GNGK01 AND A.GNGK02=B.GNGK02 AND A.GNGDTE=B.GNGDTE ");
				
		
			} catch (Exception e) {
				throwError.append(" Error building SQL statement. " + e);
			}
		
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsUtilities.");
				throwError.append("BuildSQL.sqlGetLatestAvailablerate(CommonRequestBean, ResultSet ) ");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * 
		 */
		
		private static String sqlFindUtilitiesByPlantYearDay(CommonRequestBean crb, String date, String utility) throws Exception {
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			
			try {
		
				// Determine Library
				String library = GeneralUtility.getLibrary(crb.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(crb.getEnvironment()) + ".";
		
				sqlString.append("SELECT ");
				sqlString.append("GNGK00, GNGK01, GNGK02, GNGDTE ");
		
				sqlString.append("FROM " + ttLibrary + "GNPGUTIL ");
		
				sqlString.append("WHERE ");
				sqlString.append("GNGK00 = 'UTIL' ");
				sqlString.append("AND GNGK01 = '" + crb.getIdLevel1().trim() + "' ");
				sqlString.append("AND GNGK02 = '" + utility + "' " );
				sqlString.append("AND GNGDTE = " + date);
		
			} catch (Exception e) {
				throwError.append(" Error building SQL statement. " + e);
			}
		
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsUtilities.");
				throwError.append("BuildSQL.sqlFindUtilitiesByPlantYearWeek(CommonRequestBean, ");
				throwError.append("String, String ");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * 
		 */
		
		private static String sqlAddUtilitiesByPlantYearDay(CommonRequestBean crb, String date, 
				                                            String utility, String value1, String value2) 
		        throws Exception {
			
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			
			try {
		
				// Determine Library
				String library = GeneralUtility.getLibrary(crb.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(crb.getEnvironment()) + ".";
		
				sqlString.append("INSERT INTO " + ttLibrary + "GNPGUTIL ");
				sqlString.append("(GNGTYP, GNGLMD, GNGLMU, GNGK00, GNGK01, ");
				sqlString.append("GNGK02, GNGK03, GNGK04, GNGK05, GNGVL1, ");
				sqlString.append("GNGVL2, GNGAL1, GNGDTE) ");
				
				sqlString.append("Values(");
				sqlString.append("'',");//GNGTYP
				sqlString.append(crb.getDate() + ", ");//GNGLMD
				sqlString.append("'" + crb.getUser() + "', ");//GNGLMU
				sqlString.append("'UTIL', ");//GNGK00
				sqlString.append("'" + crb.getIdLevel1().trim() + "', ");//GNGK01
				sqlString.append("'" + utility.trim() + "', ");//GNGF02
				sqlString.append("'', ");//GNGK03
				sqlString.append("'', ");//GNGK04
				sqlString.append("'', ");//GNGK05
				sqlString.append(value1 + ", ");//GNGVL1
				sqlString.append(value2 + ", ");//GNGVL2
				sqlString.append("'', ");//GNGAL1
				sqlString.append(date + " ");//GNGDTE
				sqlString.append(") ");//end of values
		
			} catch (Exception e) {
				throwError.append(" Error building SQL statement. " + e);
			}
		
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsUtilities.");
				throwError.append("BuildSQL.sqlAddUtilitiesByPlantYearWeek(CommonRequestBean, ");
				throwError.append("String, String, String, String) ");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}

		/**
		 * 
		 */
		
		private static String sqlUpdateUtilitiesByPlantYearDay(CommonRequestBean crb, ResultSet rs, 
				                                               String value1, String value2) 
		        throws Exception {
			
			StringBuffer sqlString = new StringBuffer();
			StringBuffer throwError = new StringBuffer();
			
			try {
		
				// Determine Library
				String library = GeneralUtility.getLibrary(crb.getEnvironment()) + ".";
				String ttLibrary = GeneralUtility.getTTLibrary(crb.getEnvironment()) + ".";
		
				sqlString.append("UPDATE "); 
				sqlString.append(ttLibrary + "GNPGUTIL ");
				sqlString.append("SET GNGVL1 = " + value1 + ", ");
				sqlString.append("GNGVL2 = " + value2 + " ");
				sqlString.append("WHERE GNGK00 = '" + rs.getString("GNGK00").trim() + "' ");
				sqlString.append("AND GNGK01 = '" + rs.getString("GNGK01").trim() + "' ");
				sqlString.append("AND GNGK02 = '" + rs.getString("GNGK02").trim() + "' ");
				sqlString.append("AND GNGDTE = " + rs.getString("GNGDTE") + " ");
				
		
			} catch (Exception e) {
				throwError.append(" Error building SQL statement. " + e);
			}
		
			if (!throwError.toString().trim().equals("")) {
				throwError.append("Error at com.treetop.services.ServiceOperatonsUtilities.");
				throwError.append("BuildSQL.sqlUpdateUtilitiesByPlantYearWeek(CommonRequestBean, ");
				throwError.append("ResultSet, String, String) ");
				throw new Exception(throwError.toString());
			}
			return sqlString.toString();
		}
		
		
		
	}
	
	/**
	 * Return A Week of Utilities For a Plant. 
	 * 
	 * @param CommonRequestBean
	 * @return JSONObject (easy presentation)
	 * @throws Exception
	 */
	public static JSONObject getUtilitiesByPlantYearWeek(CommonRequestBean crb) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		JSONObject data = null;
		
		
		if (crb == null) {
			throwError.append("Inquiry Bean cannot be null.  ");
		} else {
			if (crb.getEnvironment().equals("")) {
				throwError.append("Environment cannot be empty.  ");
			}
			if (crb.getIdLevel1().equals("")) {
				throwError.append("Warehouse cannot be empty.  ");
			}
			if (crb.getIdLevel2().equals("")) {
				throwError.append("Fiscal week start date cannot be empty.  ");
			}
			if (crb.getCompanyNumber().equals("")){
				crb.setCompanyNumber("100");
			}
			if (crb.getDivisionNumber().equals("")) {
				crb.setDivisionNumber("100");
			}
		}
		
		if (throwError.toString().trim().equals("")) {
	
			try {
				// get a connection
				conn = ServiceConnection.getConnectionStack11();
				
				data = getUtilitiesByPlantYearWeek(crb, conn);
				
	
			} catch (Exception e) {
				throwError.append(e);
			}
	
			finally {
				if (conn != null) {
					try {
						ServiceConnection.returnConnectionStack11(conn);
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsUtilities.");
			throwError.append("getUtilitiesByPlantYearWeek(CommonRequestBean). ");
			throw new Exception(throwError.toString());
		}
		
		return data;
	
	}
	
	
	
	/**
	 * Return Processing Report Data for Plant by Groups Will include Forecast,
	 * Planned and Actual
	 * 
	 * @param InqOperations
	 * @return Will not be sending anything back
	 * @throws Exception
	 */
	private static JSONObject getUtilitiesByPlantYearWeek(CommonRequestBean crb, Connection conn) throws Exception {
		StringBuffer throwError = new StringBuffer();
		
		Statement getThem = null;
		ResultSet rsQty   = null;
		
		Statement getIt   = null;
		ResultSet rsRate  = null;
		
		String sqlString = "";
		JSONObject data = new JSONObject();
		JSONObject rates = new JSONObject();
		JSONObject usage = new JSONObject();
		
		JSONObject monday = new JSONObject();
		JSONObject tuesday = new JSONObject();
		JSONObject wednesday = new JSONObject();
		JSONObject thursday = new JSONObject();
		JSONObject friday = new JSONObject();
		JSONObject saturday = new JSONObject();
		JSONObject sunday = new JSONObject();
		BigDecimal x = null;
		
		//add 6 more days to incoming date.
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(crb.getIdLevel2().trim()));
		c.add(Calendar.DATE, 6);  // number of days to add
		String endDate = sdf.format(c.getTime()); 

		
		try // catch all
		{
			
			//SQL for daily values
			try {
				sqlString = BuildSQL.sqlGetUtilitiesByPlantYearWeek(crb, endDate);
				getThem = conn.createStatement();
				rsQty = getThem.executeQuery(sqlString);
			} catch (Exception e) {
				throwError.append("Error with sql statement. " + e);
			}
			
			if (throwError.toString().equals("")) {
				try {
					String firstDate = "";
					String currentDate = "";
					String theDay = "";
					String rate = "";
						
					while (rsQty.next() && throwError.toString().equals("")) {
						
						//for every entry at start of week date add a rate entry.
						if (firstDate.equals("") || firstDate.equals(rsQty.getString("CDYMD8")))
						{
							rate = rsQty.getString("GNGVL2".trim());
							firstDate = rsQty.getString("CDYMD8");
							
							if (rate.equals("0.000000"))
							{
								try {
									sqlString = BuildSQL.sqlGetLatestAvailableRate(crb, rsQty, endDate);
									getIt = conn.createStatement();
									rsRate = getIt.executeQuery(sqlString);
									
									if (rsRate.next())
										rate = rsRate.getString("GNGVL2");
								} catch (Exception e) {
									throwError.append("Error retrieving rate. " + e);
								}
							}
							
							//load a rate entry.
							rates.put(rsQty.getString("DCPT40").trim(), new BigDecimal(rate).setScale(6, BigDecimal.ROUND_HALF_UP).toString());
							
						}
						
						//Load return object;
						
						if (currentDate.equals("") || !currentDate.equals(rsQty.getString("CDYMD8")))
						{
							String dt = rsQty.getString("CDYMD8");
							SimpleDateFormat format1=new SimpleDateFormat("yyyyMMdd");
							Date dt1=format1.parse(dt);
							SimpleDateFormat format2=new SimpleDateFormat("EEEE"); 
							theDay = format2.format(dt1);
						}
						
						if (theDay.equals("Monday"))
							monday.put(rsQty.getString("DCPT40").trim(), new BigDecimal(rsQty.getString("GNGVL1")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
						
						if (theDay.equals("Tuesday"))
							tuesday.put(rsQty.getString("DCPT40").trim(), new BigDecimal(rsQty.getString("GNGVL1")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
						
						if (theDay.equals("Wednesday"))
							wednesday.put(rsQty.getString("DCPT40").trim(), new BigDecimal(rsQty.getString("GNGVL1")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
						
						if (theDay.equals("Thursday"))
							thursday.put(rsQty.getString("DCPT40").trim(), new BigDecimal(rsQty.getString("GNGVL1")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
						
						if (theDay.equals("Friday"))
							friday.put(rsQty.getString("DCPT40").trim(), new BigDecimal(rsQty.getString("GNGVL1")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
						
						if (theDay.equals("Saturday"))
							saturday.put(rsQty.getString("DCPT40").trim(), new BigDecimal(rsQty.getString("GNGVL1")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
						
						if (theDay.equals("Sunday"))
							sunday.put(rsQty.getString("DCPT40").trim(), new BigDecimal(rsQty.getString("GNGVL1")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
						
					
					}
					
					if(throwError.toString().equals(""))
					{
						data.put("rate", rates);
						usage.put("Monday", monday);
						usage.put("Tuesday", tuesday);
						usage.put("Wednesday", wednesday);
						usage.put("Thursday", thursday);
						usage.put("Friday", friday);
						usage.put("Saturday", saturday);
						usage.put("Sunday", sunday);
						data.put("usage", usage);
						
					}

				} catch (Exception e) {
					throwError.append(" Error occurred loading Actual Processing data. " + e);
				}

			}
			
			
		} catch (Exception e) {
			throwError.append(e);
		}
		
		finally {
			try {
				if (getThem != null)
					getThem.close();
				if (rsQty != null)
					rsQty.close();
			} catch (Exception e) {
			}
			
			try {
				if (getIt != null)
					getIt.close();
				if (rsRate != null)
					rsRate.close();
			} catch (Exception e) {
			}
			
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsUtilities.");
			throwError.append("getUtilitiesByPlantYearWeek(");
			throwError.append("CommonRequestBean, conn). ");
			throw new Exception(throwError.toString());
		}
		
		return data;
	}
	
	

	/**
	 * Update Utilities by week
	 * 
	 * @param CommonRequestBean
	 * @param JSONObject
	 * @param Connection
	 * @throws Exception
	 */
	private static void updateUtilitiesByPlantYearWeek(CommonRequestBean crb, JSONObject data, Connection conn) 
		throws Exception {
		
		StringBuffer throwError = new StringBuffer();
		
		Statement findIt   = null;
		ResultSet rsFindIt = null;
		
		Statement updateIt = null;
		
		String sqlString = "";

		JSONObject rates = new JSONObject();
		JSONObject usage = new JSONObject();
		
		
		try // catch all
		{
			//Iterate through the Usage and update the Utilities data base.
			try {
				rates = data.getJSONObject("rate");

		        usage = data.getJSONObject("usage");
		        Iterator<?> usageKeys = usage.keys();
		        
		        findIt = conn.createStatement();
		        updateIt = conn.createStatement();

		        while( usageKeys.hasNext() && throwError.toString().equals("") ){
		            String dayName = (String)usageKeys.next();
		            
		            JSONObject day = usage.getJSONObject(dayName);
		            Iterator<?> dayKeys = day.keys();
		            
		            while( dayKeys.hasNext() && throwError.toString().equals("") ){
		            	String utility = (String)dayKeys.next();
		            	String val1 = (String) day.getString(utility);
		            	String val2 = (String) rates.get(utility);
			            	
		            	//update data file here.
		            	if (throwError.toString().equals(""))
		            	{
		            		//determine date and get the record if it exists.
		            		String dayDate = getDateFromDay(crb.getIdLevel2().trim(), dayName);
		            		sqlString = BuildSQL.sqlFindUtilitiesByPlantYearDay(crb, dayDate, utility);
		    				rsFindIt = findIt.executeQuery(sqlString);
		    				
		    				//update an existing one.
		    				if (rsFindIt.next() && throwError.toString().equals(""))
		    				{
		    					sqlString = BuildSQL.sqlUpdateUtilitiesByPlantYearDay(crb, rsFindIt, val1, val2);
		    					updateIt.executeUpdate(sqlString);
		    					
		    				//write a new one.
		    				} else {
		    					sqlString = BuildSQL.sqlAddUtilitiesByPlantYearDay(crb, dayDate, utility, val1, val2);
		    					updateIt.executeUpdate(sqlString);
		    				}
		    					
		            	}
		            	
		            }
		        }
				
			} catch (Exception e) {
				throwError.append("Error at catch all. " + e);
			}
			
			
			
			
		} catch (Exception e) {
			throwError.append(e);
		}
		
		finally {
			try {
				if (findIt != null)
					findIt.close();
				if (rsFindIt != null)
					rsFindIt.close();
			} catch (Exception e) {
			}
			
			try {
				if (updateIt != null)
					updateIt.close();
			} catch (Exception e) {
			}
			
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsUtilities.");
			throwError.append("updateUtilitiesByPlantYearWeek(");
			throwError.append("CommonRequestBean, JASONObject, conn). ");
			throw new Exception(throwError.toString());
		}
		
	}



	/**
	 * Upadte A Week of Utilities For a Plant. 
	 * 
	 * @param CommonRequestBean
	 * @param JSONObject 
	 * @throws Exception
	 */
	public static void updateUtilitiesByPlantYearWeek(CommonRequestBean crb, JSONObject data) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Connection conn = null;
		
		
		if (crb == null) {
			throwError.append("Inquiry Bean cannot be null.  ");
		} else {
			if (crb.getEnvironment().equals("")) {
				throwError.append("Environment cannot be empty.  ");
			}
			if (crb.getIdLevel1().equals("")) {
				throwError.append("Warehouse cannot be empty.  ");
			}
			if (crb.getIdLevel2().equals("")) {
				throwError.append("Fiscal week start date cannot be empty.  ");
			}
			if (crb.getCompanyNumber().equals("")){
				crb.setCompanyNumber("100");
			}
			if (crb.getDivisionNumber().equals("")) {
				crb.setDivisionNumber("100");
			}
		}
		
		if (throwError.toString().trim().equals("")) {
	
			try {
				// get a connection
				conn = ServiceConnection.getConnectionStack11();
				
				updateUtilitiesByPlantYearWeek(crb, data, conn);
				
	
			} catch (Exception e) {
				throwError.append(e);
			}
	
			finally {
				if (conn != null) {
					try {
						ServiceConnection.returnConnectionStack11(conn);
					} catch (Exception el) {
						el.printStackTrace();
					}
				}
			}
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsUtilities.");
			throwError.append("updateUtilitiesByPlantYearWeek(CommonRequestBean, JSONObject). ");
			throw new Exception(throwError.toString());
		}
		
	
	}
	
	
	
	private static String getDateFromDay(String date, String day) throws Exception {
		StringBuffer throwError = new StringBuffer();
		
		String dayDate = "01019999";
		int x = 0;
		
		try {
			if (day.equals("Monday"))
				x = 0;
			else if (day.equals("Tuesday"))
				x = 1;
			else if (day.equals("Wednesday"))
				x = 2;
			else if (day.equals("Thursday"))
				x = 3;
			else if (day.equals("Friday"))
				x = 4;
			else if (day.equals("Saturday"))
				x = 5;
			else if (day.equals("Sunday"))
				x = 6;
			
			//add necessary days to week start date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(date));
			c.add(Calendar.DATE, x);  // number of days to add
			dayDate = sdf.format(c.getTime());  
			
		} catch (Exception e) {
			throwError.append("Error determining day. " + e);
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals("")) {
			throwError.append(" @ com.treetop.Services.");
			throwError.append("ServiceOperationsUtilities.");
			throwError.append("getDateFromDay(String: " + date + ", ");
			throwError.append("String: " + day + ") ");
			throw new Exception(throwError.toString());
		}
		
		return dayDate;
	
	}
	
	public static class UnitTests {
		
		

		@Test
		public void testMethod() throws Exception  {
			
			String jsonString = "";
			
			JSONObject data = new JSONObject();
		
		
			JSONObject rates = new JSONObject();
			rates.put("fuel",".142");
			rates.put("water","1.865");
			rates.put("power",".0024");
			
			data.put("rate",rates);
			
			
			JSONObject usage = new JSONObject();
			JSONObject monday = new JSONObject();
			monday.put("fuel","456");
			monday.put("water","4689");
			monday.put("power","1234");
			
			
			usage.put("Monday", monday);
			
			JSONObject tuesday = new JSONObject();
			tuesday.put("fuel","456");
			tuesday.put("water","4689");
			tuesday.put("power","1234");
			
			usage.put("Tuesdsay", tuesday);
			
			data.put("usage", usage);
			
			jsonString = data.toString();
			
			JSONObject therates = data.getJSONObject("rate");
		    Iterator<?> keys = therates.keys();
		
		    while( keys.hasNext() ){
		        String key = (String)keys.next();
		        String val = (String) therates.getString(key);
		        
		        String here = "stop";
		    }
		    
		    JSONObject theusage = data.getJSONObject("usage");
		    keys = theusage.keys();
		
		    while( keys.hasNext() ){
		        String key = (String)keys.next();
		        
		        JSONObject theday = usage.getJSONObject(key);
		        Iterator<?> keys2 = theday.keys();
		        
		        while( keys2.hasNext() ){
		        	String key2 = (String)keys2.next();
		        	String val = (String) theday.getString(key2);
		        	String val2 = (String) therates.get(key2);
		        	String here = "stop";
		        }
		        
		        String here = "stop";
		    }
		    
		    
		    
		    //test the get date from day method.
		    String date = "20121203";
		    String day  = "Monday";
		    String dayDate = getDateFromDay(date, day);
		    
		    
		    day  = "Wednesday";
		    dayDate = getDateFromDay(date, day);
		    
		    day  = "Sunday";
		    dayDate = getDateFromDay(date, day);
			
			
			String stop = "here";
			
		}
		
		
		
		@Test
		public void testMethodGetUtilitiesByPlantYearWeek() throws Exception  {
			
			JSONObject rtn = null;
			CommonRequestBean crb = new CommonRequestBean();
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			crb.setEnvironment("PRD");
			crb.setIdLevel1("209");
			crb.setIdLevel2("20120730");
			
			rtn = getUtilitiesByPlantYearWeek(crb);
			
			
			
			String stop = "here";
			
		}
		
		
		
		@Test
		public void testMethodUpdateUtilitiesByPlantYearWeek() throws Exception  {
			
			// Using 2012 week 2 for testing. Mondays date is 20110815. 
			// For full testing delete these records first. 
			
			CommonRequestBean crb = new CommonRequestBean();
			crb.setCompanyNumber("100");
			crb.setDivisionNumber("100");
			crb.setEnvironment("PRD");
			crb.setIdLevel1("209");
			crb.setIdLevel2("20110815");
			crb.setDate("20121212");
			crb.setUser("TESTUPDATE");
			
			
			JSONObject data = new JSONObject();
			
			
			JSONObject rates = new JSONObject();
			rates.put("Fuel",".142");
			rates.put("Water","1.865");
			rates.put("Power",".0024");
			
			data.put("rate",rates);
			
			
			JSONObject usage = new JSONObject();
			JSONObject monday = new JSONObject();
			monday.put("Fuel","222");
			monday.put("Water","333");
			monday.put("Power","444");
			
			
			usage.put("Monday", monday);
			
			JSONObject tuesday = new JSONObject();
			tuesday.put("Fuel","555");
			tuesday.put("Water","666");
			tuesday.put("Power","777");
			
			usage.put("Tuesday", tuesday);
			
			data.put("usage", usage);
			
			updateUtilitiesByPlantYearWeek(crb, data);
			
			
			
			String stop = "here";
			
		}
		
		
		
	}
	
}
