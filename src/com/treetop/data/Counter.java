package com.treetop.data;

import java.util.*;
import java.math.*;
import java.sql.*;
import java.io.*;
import com.treetop.*;
import com.treetop.data.*;
import com.treetop.utilities.*;

/**
 * Counter table encapsulation.
 * Creation date: (6/11/2004 3:50:31 PM)
 * @ William T Haile: 
 */
public class Counter 
{
	private String url;
	private String as400User;
	private java.sql.Date date;
	private Integer hits;
	private String securitySystem;
	private String securityValue;
	private String securityUser;
	
	private static boolean persists = false;
	
//	private static String library = "WKLIB.";	//test environmemnt
	//private static String library = "DBLIB.";	//live environment
	
	private static String library = "DBPRD.";
	private static String treeNetLibrary = "TREENET";
	
	private static Stack findByAll = null;
	private static Stack deleteByUrlByUserByDate = null;
	private static Stack insertCounter = null;
	private static Stack updateByUrlByUserByDate = null;
	private static Stack sumOfHitsByUrl = null;
	private static Stack countOfHitsByDateByUrl = null;
	private static Stack countOfHitsByDateByUrlByUser = null;
	private static Stack countOfUsersByDateByUrl = null;
	
	private static Stack addLastDateHitByUser = null;
	
/**
 * CounterBean constructor comment.
*/
	public Counter() {
		super();
		init();
	}
	
	
/**
 * ReportAged constructor comment.
 */
	public Counter(String urlIn,
				   String as400UserIn,
				   java.sql.Date dateIn,
				   String securitySystemIn,
				   String securityValueIn,
				   String securityUserIn)				  		  
		throws InstantiationException 
	{		
		init();

		String errorMessage = "";
		String popped = "no";
		PreparedStatement findIt = null;

		try
		{
			findIt = (PreparedStatement) findByAll.pop();
			popped = "yes";
									   
			ResultSet rs = null;
			try
			{
				findIt.setString(1, urlIn);
				findIt.setString(2, as400UserIn);
				findIt.setDate(3, dateIn);
				findIt.setString(4, securitySystemIn);
				findIt.setString(5, securityValueIn);
				findIt.setString(6, securityUserIn);
				
				rs = findIt.executeQuery();
				if (rs.next())
					loadFields(rs);
				else
					errorMessage = "Exception error finding a result set.  " +
								"Instantiation error at - com.treetop.data." +
								"Counter(url:" + urlIn + 
								" as400User:" + as400UserIn +
								" date:" + dateIn +
								" securitySystem:" + securitySystemIn +
								" securityValue:" + securityValueIn +
								" securityUser:" + securityUserIn + ")";
							
				findByAll.push(findIt);
				popped = "no";									
			}
			catch(Exception e)
			{
				errorMessage = "error @ excute query.  " +
							   "Instantiation error at - com.treetop.data." +
							   "Counter(url:" + urlIn + 
								" as400User:" + as400UserIn +
								" date:" + dateIn +
								" securitySystem:" + securitySystemIn +
								" securityValue:" + securityValueIn +
								" securityUser:" + securityUserIn + ")";
			}
		
			if (popped.equals("yes"))
				findByAll.push(findIt);
		
			popped = "no";		
			rs.close();		
		
		}
		catch(Exception e)
		{
			errorMessage = "Instantiation error at - com.treetop.data." +
						   "Counter(url:" + urlIn + 
							" as400User:" + as400UserIn +
							" date:" + dateIn +
							" securitySystem:" + securitySystemIn +
							" securityValue:" + securityValueIn +
							" securityUser:" + securityUserIn + ")";	
		}
	
		if (!errorMessage.equals(""))
		   throw new InstantiationException(errorMessage);

		return;
	}



/**
 * Insert the method's description here.
 * Creation date: (06/14/2004 8:24:29 AM)
*/
	public void add() 
		throws Exception {

		String popped = "no";
		PreparedStatement addIt = null;
		String errorMessage = "";		
		checkCounterFields();			

		// Add a Counter record.
		try {
			addIt = (PreparedStatement) insertCounter.pop();
			popped = "yes";
			addIt.setString(1, url);
			addIt.setString(2, as400User);				
			addIt.setInt(3, hits.intValue());				
			addIt.setDate(4, date);
			addIt.setString(5, securitySystem);
			addIt.setString(6, securityValue);
			addIt.setString(7, securityUser);
		
			addIt.executeUpdate();
			insertCounter.push(addIt);
			popped = "no";

		} catch (Exception e) {
			errorMessage = ("error @ com.treetop.data.Counter." +
							"add():" + e);
		}
		
		if (popped.equals("yes"))
			insertCounter.push(addIt);
			
		if (!errorMessage.equals(""))
			throw new Exception(errorMessage);

		return;	
	}
	
	
/**
 * Set any class fields that are null to empty or zero.
 *
*/
	
	public void checkCounterFields() 
	{
		if (url == null)
			url = "";
		if (as400User == null)
			as400User = "";
		if (hits == null)
			hits = new Integer("0");
		if (date == null)
			date = java.sql.Date.valueOf("1999-01-01");
		if (securitySystem == null)
			securitySystem = "";
		if (securityValue == null)
			securityValue = "";
		if (securityUser == null)
			securityUser = "";
			
		return;
	}
	
	
/**
 * Insert the method's description here.
 * Creation date: (06/14/2004 8:24:29 AM)
*/
	public void delete() 
		throws Exception{
	
		String popped = "no";
		PreparedStatement deleteIt = null;
	
		// Delete DPP2HITS record by Url, User, and Date.
		try {
			deleteIt = (PreparedStatement) deleteByUrlByUserByDate.pop();
			popped = "yes";
			deleteIt.setString(1, url); 
			deleteIt.setString(2, as400User);
			deleteIt.setDate(3, date);
			deleteIt.setString(4, securitySystem);
			deleteIt.setString(5, securityValue);
			deleteIt.setString(6, securityUser);
			
			deleteIt.executeUpdate();
			deleteByUrlByUserByDate.push(deleteIt);
			popped = "no";
	
		} catch (Exception e) {
			String error = ("error @ com.treetop.data." +
							"Counter.delete()" + e);
				            
			if (popped.equals("yes"))
				deleteByUrlByUserByDate.push(deleteIt);
				
			throw new Exception(error);
		}
		return;	
	}

/**
 * Return the total number of hits ecountered for this url.
 * Creation date: (06/24/2004 8:24:29 AM)
*/
	public static int findSumOfHitsByUrl(String urlIn)
	{
		int totalHits = 0;
			
		// Check out total url hits.
		String popped = "yes";
		PreparedStatement sumIt = null;
		try {
			sumIt = (PreparedStatement) sumOfHitsByUrl.pop();
			popped = "yes";
			sumIt.setString(1, urlIn);
			
			ResultSet rs = sumIt.executeQuery();
			if (rs.next())
			{
				totalHits = rs.getInt("hits");
			}
			sumOfHitsByUrl.push(sumIt);
			popped = "no";	

		} catch (Exception e) {
			System.out.println("error @ com.treetop.data.Counter." +
							   "findSumOfHitsByUrl(url:" +
							   urlIn + ") " + e); 
							   
			if (popped.equals("yes"))
				sumOfHitsByUrl.push(sumIt);
		}				
		
		return totalHits; 
	}
	
/**
 * Build a result set of url hit counts by date.
 *
 * Creation date: (06/25/2004 3:40:29 PM)
*/
	public static Integer findUsersByDate(String urlIn, java.sql.Date dateIn) {
		
		ResultSet rs        = null;
		Counter   setClass  = new Counter();
		Integer   userTotal = new Integer("0");
	    
		try {
		
			PreparedStatement findTotal = (PreparedStatement) 
										   countOfUsersByDateByUrl.pop();
		
			try {		
				findTotal.setString(1, urlIn);
				findTotal.setDate(2, dateIn);
				rs = findTotal.executeQuery();
				
				if (rs.next())
					userTotal = new Integer(rs.getString("TOTAL"));
				else
					userTotal = new Integer("0");
				
						
			}
			catch (Exception e){
				System.out.println("Exception error creating a result set " +
								   "@ com.treetop.data." +
								   "Counter.findUsersByDate(url:" + 
									urlIn + "date:" + dateIn +") " + e);
				try {
					rs.close();
				}
				catch (Exception x) 
				{
				}		               
			}
	
			countOfHitsByDateByUrl.push(findTotal);
				
		}
	 
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "@ com.treetop.data." +
							   "Counter.findUsersByDate(url:" + 
			                    urlIn + "date:" + dateIn +") " + e);
			try {
				rs.close();
				}
			catch (Exception x) 
			{
			}		               
		}

			return userTotal;   
	}

/**
 * Add a hit to the Counter file (DPP2HITS).
 * Creation date: (06/24/2004 8:24:29 AM)
*/
	public static void addHit(String urlIn,
							  String as400UserIn,
							  java.sql.Date dateIn,
							  String securitySystemIn,
							  String securityValueIn,
							  String securityUserIn)
	{	
		// See if this entry exists in the file.
		try {

			try {
				Counter counter = new Counter(urlIn,
											  as400UserIn,
											  dateIn,
											  securitySystemIn,
											  securityValueIn,
											  securityUserIn);
				int x = counter.getHits().intValue() + 1;
				Integer total = new Integer(x);
				counter.setHits(total);
				counter.update();
								
			} catch (InstantiationException x) {
				Counter counter = new Counter();
				counter.setUrl(urlIn);
				counter.setAs400User(as400UserIn);
				counter.setDate(dateIn);
				counter.setSecuritySystem(securitySystemIn);
				counter.setSecurityValue(securityValueIn);
				counter.setSecurityUser(securityUserIn);
				Integer one = new Integer(1);
					counter.setHits(one);
				try {
					counter.add();
				} catch (Exception z)
				{
					//skip.
				}
			}
			
		} catch (Exception e) {		  
			System.out.println("error @ com.treetop.data.Counter." +
							   "addHit(url:" + urlIn + 
							   " user:" + as400UserIn +
							   " date:" + dateIn + "" +
							   " secSys:" + securitySystemIn +
							   " secVal:" + securityValueIn +
							   " secUsr:" + securityUserIn + ") " + e);
							   
		}	
	}
	
	
/**
 * Build a result set of url hit counts by date.
 *
 * Creation date: (06/25/2004 3:40:29 PM)
 */
	public static ResultSet findCountByDate(String urlIn) {
		
		ResultSet rs                 = null;
		Counter  setClass = new Counter();
	    
		try {
		
			PreparedStatement findThem = (PreparedStatement) 
											countOfHitsByDateByUrl.pop();
		
			try {		
				findThem.setString(1, urlIn);
				rs = findThem.executeQuery();
						
			}
			catch (Exception e){
				System.out.println("Exception error creating a result set " +
								   "@ com.treetop.data." +
								   "Counter.findCountByDate(url:" + 
								   urlIn + ") " + e);
				try {
					rs.close();
					}
					catch (Exception x) 
					{
					}		               
			}
	
			countOfHitsByDateByUrl.push(findThem);
				
		}
	 
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "@ com.treetop.data." +
							   "Counter.findCountByDate(url:" + 
							   urlIn + ") " + e);
			try {
				rs.close();
				}
				catch (Exception x) 
				{
				}		               
		}

		return rs;   
	}

	
/**
 * Build a result set of url hit counts by date, user, and security info.
 *
 * Creation date: (06/25/2004 3:40:29 PM)
 */
	public static ResultSet findCountByUser(String urlIn) {
											
	
		ResultSet rs                 = null;
		Counter  setClass = new Counter();
    
		try {
			PreparedStatement findThem = (PreparedStatement) 
										  countOfHitsByDateByUrlByUser.pop();
	
			try {		
			//	urlIn = "treenet.treetop.com/web/TreeNetInq";
				findThem.setString(1, urlIn);
				rs = findThem.executeQuery();
					
			}
			catch (Exception e){
				System.out.println("Exception error creating a result set " +
								   "@ com.treetop.data." +
								   "Counter.findCountByUser(url:" + 
								   urlIn + ") " + e);
				try {
					rs.close();
					}
					catch (Exception x) 
					{
					}		               
			}

			countOfHitsByDateByUrlByUser.push(findThem);
			
		}
 
		catch (Exception e) {
			System.out.println("Exception error creating a result set " +
							   "@ com.treetop.data." +
							   "Counter.findCountByUser(url:" + 
							   urlIn + ") " + e);
			try {
				rs.close();
			}
				catch (Exception x) 
				{
				}		               
		}
			// Send the Result set to the JSP, cannot close at this point
		return rs;   
	}
	
	
/**
 * 	 * Load the class fields from the incoming result set.
 * Creation date: (05/12/2004 11:40:10 AM)
*/

	protected void loadFields(ResultSet rs) {

		try {
				url				= rs.getString("DP2URL");
				as400User		= rs.getString("DP2USR");
				date			= rs.getDate("DP2DTE");
				hits			= new Integer(rs.getInt("DP2HIT"));
				securitySystem	= rs.getString("DP2SECSYS");
				securityValue	= rs.getString("DP2SECVAL");
				securityUser	= rs.getString("DP2SECUSR");
		}
		catch (Exception e)
		{
			System.out.println("error @ com.treetop.data." +
							   "Counter.loadFields(ResultSet);" + e);
		}
	}
	
	
/**
 * 	This method tests access to file "DPP2HITS". 
 * All class
 * fields should be tested and verified for access to and from the Enterprise 
 * database. A listing of records as they are accessed and updated should be 
 * generated. Also connections, prepared statements, and the loadFields method
 * are confirmed.
 * 
 * Creation date: (05/12/2004 11:40:10 AM)
 */

	public static void main(String[] args) {
	
		// Test Counter Sql Functions.
		
		try {
			java.sql.Date date = java.sql.Date.valueOf("2005-03-22");
			
			Integer total = Counter.findUsersByDate("prism access", date);		
			System.out.println(total);
							
		}
		catch (Exception e) {
			System.out.println("error at com.treetop.data.Counter.main()");
		}


		try {
			String[] dateAndTime = com.treetop.SystemDate.getSystemDate();
			Counter one = new Counter();
			
			ResultSet rs1 = Counter.findCountByDate("treenet.treetop.com/web/JSP/Production/listProduction.jsp");
					
			if (rs1.next())
			{
				System.out.println(rs1.getString("DP2URL"));
				System.out.println(rs1.getString("hits"));
			}
			
			
			// Check out total url hits.
			String popped = "yes";
			PreparedStatement sumIt = null;
			try {
				sumIt = (PreparedStatement) sumOfHitsByUrl.pop();
				popped = "yes";
				sumIt.setString(1, "goto.treetop.com/webapp/JSP/SalesOrder/inqSalesOrder.jsp");
			
				ResultSet rs = sumIt.executeQuery();
				if (rs.next())
				{
					int xx = rs.getInt("hits");
					String y = "yy";
				}
				sumOfHitsByUrl.push(sumIt);
				popped = "no";	

			} catch (Exception e) {
				System.out.println("error @ com.treetop.data.Counter." +
								   "update():" + e);
				if (popped.equals("yes"))
					sumOfHitsByUrl.push(sumIt);
			}				
		
			// Add a Counter class (one).
			one.setUrl("goto.treetop.com.FirstPage");
			one.setAs400User("THAILE");
			one.setHits(new Integer("24"));
			one.setDate(java.sql.Date.valueOf(dateAndTime[7]) );
			one.setSecuritySystem("TH");
			one.setSecurityValue("12345");
			one.setSecurityUser("Tom Haile");
			one.add();
											   					 
			System.out.println("one: " + one);


			// test the class constructor.			
			Counter oneAgain = new Counter(one.getUrl(),
										   one.getAs400User(),
										   one.getDate(),
										   one.getSecuritySystem(),
										   one.getSecurityValue(),
										   one.getSecurityUser());
		 														
			System.out.println("oneAgain: " + oneAgain);
			
			
			//Add a class (two)
			Counter two = new Counter();
			two.setUrl("goto.treetop.com.SecondPage");
			two.setAs400User("THAILE");
			two.setHits(new Integer("25"));
			two.add();
							   					 
			System.out.println("two: " + two);
			
			
			// Update a class.
			one.setHits(new Integer("29"));
			one.update();
			
			System.out.println("one: " + one);
			
			
			// Delete a class.
			one.delete();
			two.delete();
			
			java.sql.Date theDate = java.sql.Date.valueOf(dateAndTime[7]);
			String urlIn = "localHost:9080";
			urlIn = urlIn + "/web/JSP/Test/test.jsp";
			String userIn = "THAILE";
			Counter.addHit(urlIn,userIn,theDate,"","","");
			int totalHits = Counter.findSumOfHitsByUrl(urlIn);
			Counter.addHit(urlIn,userIn,theDate,"","","");
			totalHits = Counter.findSumOfHitsByUrl(urlIn);
			
			System.out.println("hits = " + totalHits);
			
		
			} catch (Exception e) {
				System.out.println("error at com.treetop.data.Counter.main()");
			}
	}
	
		
	public void init() {
		 
		if (persists == false) 
		{
			persists = true;
			
			// Perform initialization 
			try {
				Connection conn1 = ConnectionStack.getConnection();
				Connection conn2 = ConnectionStack.getConnection();
				Connection conn3 = ConnectionStack.getConnection();
				Connection conn4 = ConnectionStack.getConnection();
				Connection conn5 = ConnectionStack.getConnection();
				
				// Find By Url by User by Date.				
				String fbAll =
					"SELECT * " +
					" FROM " + library + "DPP2HITS " +
					" WHERE DP2URL = ? " +
					"   AND DP2USR = ? " +
					"   AND DP2DTE = ? " +
					"   AND DP2SECSYS = ? " +
					"   AND DP2SECVAL = ? " +
					"   AND DP2SECUSR = ? ";
					
				PreparedStatement fbUrlDteUsr1 = conn1.prepareStatement(fbAll);
				PreparedStatement fbUrlDteUsr2 = conn1.prepareStatement(fbAll);
				PreparedStatement fbUrlDteUsr3 = conn1.prepareStatement(fbAll);
				PreparedStatement fbUrlDteUsr4 = conn1.prepareStatement(fbAll);
				PreparedStatement fbUrlDteUsr5 = conn1.prepareStatement(fbAll);
				
				findByAll = new Stack();
				findByAll.push(fbUrlDteUsr1);
				findByAll.push(fbUrlDteUsr2);
				findByAll.push(fbUrlDteUsr3);
				findByAll.push(fbUrlDteUsr4);
				findByAll.push(fbUrlDteUsr5);
									
				
				// Delete Counter record DPP2HITS.				
				String deleteIt =		
					"DELETE FROM " + library + "DPP2HITS " +
					" WHERE DP2URL = ? " +
					" AND   DP2USR = ? " +
					" AND   DP2DTE = ? " +
					" AND   DP2SECSYS = ? " +
					" AND   DP2SECVAL = ? " +
					" AND   DP2SECUSR = ? ";
				
				PreparedStatement deleteIt1 = conn1.prepareStatement(deleteIt);
				PreparedStatement deleteIt2 = conn1.prepareStatement(deleteIt);
				PreparedStatement deleteIt3 = conn1.prepareStatement(deleteIt);
				PreparedStatement deleteIt4 = conn1.prepareStatement(deleteIt);
				PreparedStatement deleteIt5 = conn1.prepareStatement(deleteIt);
				
				deleteByUrlByUserByDate = new Stack();
				deleteByUrlByUserByDate.push(deleteIt1);
				deleteByUrlByUserByDate.push(deleteIt2);
				deleteByUrlByUserByDate.push(deleteIt3);
				deleteByUrlByUserByDate.push(deleteIt4);
				deleteByUrlByUserByDate.push(deleteIt5);
				
				
				// Insert Counter record DPP2HITS.
				String insertIt =
					"INSERT INTO " + library + "DPP2HITS " +
					" VALUES (?, ?, ?, ?, ?, ?, ?)";
					
				PreparedStatement insertIt1 = conn1.prepareStatement(insertIt);
				PreparedStatement insertIt2 = conn1.prepareStatement(insertIt);
				PreparedStatement insertIt3 = conn1.prepareStatement(insertIt);
				PreparedStatement insertIt4 = conn1.prepareStatement(insertIt);
				PreparedStatement insertIt5 = conn1.prepareStatement(insertIt);
				
				insertCounter = new Stack();
				insertCounter.push(insertIt1);
				insertCounter.push(insertIt2);
				insertCounter.push(insertIt3);
				insertCounter.push(insertIt4);
				insertCounter.push(insertIt5);
				
				
				// Update Counter by Url by User by Date.
				String updateIt =
					"UPDATE " + library + "DPP2HITS " +
					" SET DP2URL = ?, DP2USR = ?,    DP2HIT = ?, " +
					"     DP2DTE = ?, DP2SECSYS = ?, DP2SECVAL = ?, " +
					"     DP2SECUSR = ? " +				
					" WHERE DP2URL = ?    AND DP2USR = ? " +
					"   AND DP2DTE = ?    AND DP2SECSYS = ? " +
					"   AND DP2SECVAL = ? AND DP2SECUSR = ? ";
							 
				PreparedStatement updateRange1 = conn1.prepareStatement(updateIt);
				PreparedStatement updateRange2 = conn1.prepareStatement(updateIt);
				PreparedStatement updateRange3 = conn1.prepareStatement(updateIt);
				PreparedStatement updateRange4 = conn1.prepareStatement(updateIt);
				PreparedStatement updateRange5 = conn1.prepareStatement(updateIt);
				
				updateByUrlByUserByDate = new Stack();
				updateByUrlByUserByDate.push(updateRange1);
				updateByUrlByUserByDate.push(updateRange2);
				updateByUrlByUserByDate.push(updateRange3);
				updateByUrlByUserByDate.push(updateRange4);
				updateByUrlByUserByDate.push(updateRange5);

				
				// Sum of hits by url.
				String hitsByUrl =
					"SELECT SUM(DP2HIT) AS HITS " +
					" FROM " + library + "DPP2HITS" +			
					" WHERE DP2URL = ? ";
												 
				PreparedStatement hitsByUrl1 = conn1.prepareStatement(hitsByUrl);
				PreparedStatement hitsByUrl2 = conn1.prepareStatement(hitsByUrl);
				PreparedStatement hitsByUrl3 = conn1.prepareStatement(hitsByUrl);
				PreparedStatement hitsByUrl4 = conn1.prepareStatement(hitsByUrl);
				PreparedStatement hitsByUrl5 = conn1.prepareStatement(hitsByUrl);
				
				sumOfHitsByUrl = new Stack();
				sumOfHitsByUrl.push(hitsByUrl1);
				sumOfHitsByUrl.push(hitsByUrl2);
				sumOfHitsByUrl.push(hitsByUrl3);
				sumOfHitsByUrl.push(hitsByUrl4);
				sumOfHitsByUrl.push(hitsByUrl5);
				
				// Count of hits by date.
				String countByDate = 
					"SELECT SUM(DP2HIT) AS hits, DP2DTE, DP2URL " +
					"FROM " + library + "DPP2HITS " +
					"WHERE DP2URL = ? " +
					"GROUP BY DP2URL, DP2DTE " + 
					"ORDER BY DP2DTE DESC";
					
				PreparedStatement countByDate1 = conn1.prepareStatement(countByDate);
				PreparedStatement countByDate2 = conn1.prepareStatement(countByDate);
				PreparedStatement countByDate3 = conn1.prepareStatement(countByDate);
				PreparedStatement countByDate4 = conn1.prepareStatement(countByDate);
				PreparedStatement countByDate5 = conn1.prepareStatement(countByDate);
	
				countOfHitsByDateByUrl = new Stack();
				countOfHitsByDateByUrl.push(countByDate1);
				countOfHitsByDateByUrl.push(countByDate2);
				countOfHitsByDateByUrl.push(countByDate3);
				countOfHitsByDateByUrl.push(countByDate4);
				countOfHitsByDateByUrl.push(countByDate5);


				// Count of hits by date by user.
					String countByUser = 
						"SELECT DP2HIT, DP2USR, DP2DTE, DP2SECSYS, " +
							  " DP2SECVAL, DP2SECUSR, DPNUSRNAME " +
						"FROM " + library + "DPP2HITS " +
						"LEFT OUTER JOIN " + treeNetLibrary + ".DPPNUSER " +
						   "ON UPPER(DP2USR) = UPPER(DPNUSER) " +   
						"WHERE DP2URL = ? " +
						"ORDER BY DPNUSRNAME, DP2USR, DP2SECVAL, DP2SECUSR, DP2DTE ";
					
				PreparedStatement countByUser1 = conn1.prepareStatement(countByUser);
				PreparedStatement countByUser2 = conn1.prepareStatement(countByUser);
				PreparedStatement countByUser3 = conn1.prepareStatement(countByUser);
				PreparedStatement countByUser4 = conn1.prepareStatement(countByUser);
				PreparedStatement countByUser5 = conn1.prepareStatement(countByUser);
	
				countOfHitsByDateByUrlByUser = new Stack();
				countOfHitsByDateByUrlByUser.push(countByUser1);
				countOfHitsByDateByUrlByUser.push(countByUser2);
				countOfHitsByDateByUrlByUser.push(countByUser3);
				countOfHitsByDateByUrlByUser.push(countByUser4);
				countOfHitsByDateByUrlByUser.push(countByUser5);
				
				
				// Count of hits by date.
				String usersByDate = 
					"SELECT COUNT(*) AS TOTAL " +
					"FROM " + library + "DPP2HITS " +
					"WHERE DP2URL = ? AND DP2DTE = ?";					
					
				PreparedStatement usersByDate1 = conn1.prepareStatement(usersByDate);
				PreparedStatement usersByDate2 = conn1.prepareStatement(usersByDate);
				PreparedStatement usersByDate3 = conn1.prepareStatement(usersByDate);
				PreparedStatement usersByDate4 = conn1.prepareStatement(usersByDate);
				PreparedStatement usersByDate5 = conn1.prepareStatement(usersByDate);
	
				countOfUsersByDateByUrl = new Stack();
				countOfUsersByDateByUrl.push(usersByDate1);
				countOfUsersByDateByUrl.push(usersByDate2);
				countOfUsersByDateByUrl.push(usersByDate3);
				countOfUsersByDateByUrl.push(usersByDate4);
				countOfUsersByDateByUrl.push(usersByDate5);		
					
				
				// Return the connections back to the pool.
				ConnectionStack.returnConnection(conn1);
				ConnectionStack.returnConnection(conn2);
				ConnectionStack.returnConnection(conn3);
				ConnectionStack.returnConnection(conn4);
				ConnectionStack.returnConnection(conn5);
	
			} catch (Exception e) {
			System.out.println("error @ com.treetop.data" +
									   ".Counter.init()" + e );
			}	
		}
	}
	
	
/**
 * Insert the method's description here.
 * Creation date: (05/12/2004 8:24:29 AM)
*/
	public void update() {

		String popped = "no";
		PreparedStatement updateIt = null;
		String[] dateAndTime = com.treetop.SystemDate.getSystemDate();
		checkCounterFields();

		// Update a Counter record.
		try {
			updateIt = (PreparedStatement) updateByUrlByUserByDate.pop();
			popped = "yes";
			updateIt.setString(1, url);
			updateIt.setString(2, as400User);			
			updateIt.setInt(3, hits.intValue());
			updateIt.setDate(4, date);
			updateIt.setString(5, securitySystem);
			updateIt.setString(6, securityValue);
			updateIt.setString(7, securityUser);
			updateIt.setString(8, url);
			updateIt.setString(9, as400User);
			updateIt.setDate(10, date);
			updateIt.setString(11, securitySystem);
			updateIt.setString(12, securityValue);
			updateIt.setString(13, securityUser); 
			
			updateIt.executeUpdate();
			updateByUrlByUserByDate.push(updateIt);
			popped = "no";	

		} catch (Exception e) {
			System.out.println("error @ com.treetop.data.Counter." +
							   "update():" + e);
			if (popped.equals("yes"))
				updateByUrlByUserByDate.push(updateIt);
		}		
	}

	
	/**
	 * @return
	 */
	public java.sql.Date getDate() {
		return date;
	}

	/**
	 * @return
	 */
	public Integer getHits() {
		return hits;
	}

	/**
	 * @return
	 */
	public String getSecuritySystem() {
		return securitySystem;
	}
	
	/**
	 * @return
	 */
	public String getSecurityValue() {
		return securityValue;
	}
	
	/**
	 * @return
	 */
	public String getSecurityUser() {
		return securityUser;
	}
			
	/**
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return
	 */
	public String getAs400User() {
		return as400User;
	}

	/**
	 * @param date
	 */
	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	/**
	 * @param integer
	 */
	public void setHits(Integer integer) {
		hits = integer;
	}

	/**
	 * @param string
	 */
	public void setSecuritySystem(String string) {
		securitySystem = string;
	}
		
	/**
	 * @param string
	 */
	public void setSecurityValue(String string) {
		securityValue = string;
	}
			
	/**
	 * @param string
	 */
	public void setSecurityUser(String string) {
		securityUser = string;
	}
				
	/**
	 * @param string
	 */
	public void setUrl(String string) {
		url = string;
	}

	/**
	 * @param string
	 */
	public void setAs400User(String string) {
		as400User = string;
	}

	
	public String toString() {
	
		return new String(
		"url: " + url + "\n" +
		"as400User: " + as400User + "\n" +
		"date: " + date + "\n" +
		"hits: " + hits + "\n" +
		"securitySystem: " + securitySystem + "\n" +
		"securityValue: " + securityValue + "\n" +
		"securityUser: " + securityUser + "\n");	
		
	}

}
