package com.treetop.data;

import java.sql.*;
import java.util.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import com.treetop.utilities.ConnectionStack;

/**
 * Acces to RDB file TREENET/GNPVMENU.
 *
 * Code used to generate the table.
 *
 * CREATE TABLE WKLIB/GNPVMENU (
 *  GNVMAJNBR  INT        NOT NULL WITH DEFAULT, //majorNumber     
 *  GNVMINNBR  INT        NOT NULL WITH DEFAULT, //minorNumber 
 *  GNVURL     CHAR (340) NOT NULL WITH DEFAULT, //urlAddress     
 *  GNVURLNBR  INT        NOT NULL WITH DEFAULT, //urlNumber
 *  GNVNEWSTAT CHAR ( 5)  NOT NULL WITH DEFAULT, //newStatus             
 *  GNVNEWCMMT CHAR (340) NOT NULL WITH DEFAULT, //newComment 
 *  GNVKEYSRCH CHAR (340) NOT NULL WITH DEFAULT, //searchValues
 *  GNVLNKDSC  CHAR (502) NOT NULL WITH DEFAULT, //urlDescription              
 *  GNVORDERBY INT        NOT NULL WITH DEFAULT, //orderBy
 *  GNVAUTHLVL INT        NOT NULL WITH DEFAULT, //authorityLevel
 *  GNVDSPMAIN CHAR ( 1)  NOT NULL WITH DEFAULT, //displayOnMain
 *  GNVEXTRA   CHAR ( 10) NOT NULL WITH DEFAULT, //extra
 *  GNVTITLE   CHAR ( 52) NOT NULL WITH DEFAULT, //title
 *  GNVTYPE    CHAR ( 10) NOT NULL WITH DEFAULT, //type
 *  GNVPATH    CHAR (340) NOT NULL WITH DEFAULT, //path
 *  GNVNAME    CHAR (340) NOT NULL WITH DEFAULT, //name
 *  GNVCRTUSER CHAR ( 10) NOT NULL WITH DEFAULT, //createUser
 *  GNVCRTDATE DATE       NOT NULL WITH DEFAULT, //createDate
 *  GNVCRTTIME TIME       NOT NULL WITH DEFAULT, //createTime
 *  GNVUPDUSER CHAR ( 10) NOT NULL WITH DEFAULT, //updateUser
 *  GNVUPDDATE DATE       NOT NULL WITH DEFAULT, //updateDate
 *  GNVUPDTIME TIME       NOT NULL WITH DEFAULT, //updateTime
 *  GNVPATHSEC CHAR (  1) NOT NULL WITH DEFAULT, //pathSecurity
 *  GNVSECSYS1 CHAR (  2) NOT NULL WITH DEFAULT, //system security 1
 *  GNVSECREQ1 CHAR (  1) NOT NULL WITH DEFAULT, //security required 1
 *  GNVSECSYS2 CHAR (  2) NOT NULL WITH DEFAULT, //system security 2
 *  GNVSECREQ2 CHAR (  1) NOT NULL WITH DEFAULT, //security required 2
 *  GNVSECSYS3 CHAR (  2) NOT NULL WITH DEFAULT, //system security 3
 *  GNVSECREQ3 CHAR (  1) NOT NULL WITH DEFAULT, //security required 3
 *  GNVSECSYS4 CHAR (  2) NOT NULL WITH DEFAULT, //system security 4
 *  GNVSECREQ4 CHAR (  1) NOT NULL WITH DEFAULT, //security required 4
 *  GNVSECSYS5 CHAR (  2) NOT NULL WITH DEFAULT, //system security 5
 *  GNVSECREQ5 CHAR (  1) NOT NULL WITH DEFAULT, //security required 5
 *  GNVSECVAL1 CHAR ( 30) NOT NULL WITH DEFAULT, //security value 1
 *  GNVSEVVAL2 CHAR ( 30) NOT NULL WITH DEFAULT, //security value 2
 *  GNVSECVAL3 CHAR ( 30) NOT NULL WITH DEFAULT, //security value 3
 *  GNVSECVAL4 CHAR ( 30) NOT NULL WITH DEFAULT, //security value 4
 *  GNVSECVAL5 CHAR ( 30) NOT NULL WITH DEFAULT) //security value 5
 *
 *
 *
 * Be sure to set the environment (library) for this file . It
 *  should be "TREENET." for the live environment and "WKLIB." 
 *  for the test environment.
 *   METHODS TO BE MAINTAINED PRIOR TO EVERY EXPORT:
 *   :updateAuthorityFields(UrlFile,...)
 *   :findMajors(String[], String)
 *   :findMajorsbyMinor(String[],...)
 *   :findMinorsbyMajor(String[],...)
 *   :findUrlsbyMajMin(String[],...)
 *   :init()"
 *   :loadFields(ResultSet,String)
 * Creation date: (2/13/2003 9:11:44 AM)
 * @William T Haile: 
 */
public class UrlFile {
	
	// data base file "GNPVMENU"
	private Integer       majorNumber;			//GNVMAJNBR
	private Integer       minorNumber;			//GNVMINNBR
	private String        urlAddress;			//GNVURL
	private Integer       urlNumber;			//GNVURLNBR
	private String        newStatus;			//GNVNEWSTAT
	private String        newComment;			//GNVNEWCMMT
	private String        searchValues;			//GNVKEYSRCH
	private String        urlDescription;		//GNVLNKDSC
	private Integer       orderBy;				//GNVORDERBY
	private Integer       authorityLevel;		//GNVATUHLVL
	private String        displayOnMain;		//GNVDSPMAIN
	private String        extra;				//GNVEXTRA
	private String        title;				//GNVTITLE
	private String        type;					//GNVTYPE
	private String        path;					//GNVPATH
	private String        name;					//GNVNMAE
	private String        createUser;			//GNVCRTUSER
	private java.sql.Date createDate;			//GNVCRTDATE
	private java.sql.Time createTime;			//GNVCRTTIME
	private String        updateUser;			//GNVUPDUSER
	private java.sql.Date updateDate;			//GNVUPDDATE
	private java.sql.Time updateTime;			//GNVUPDTIME
	private String        pathSecurity;			//GNVPATHSEC
	private String		  securitySystem1;		//GNVSECSYS1
	private String		  securityRequired1;	//GNVSECReq1
	private String		  securitySystem2;		//GNVSECSYS2
	private String		  securityRequired2;	//GNVSECREQ2
	private String		  securitySystem3;		//GNVSECSYS3
	private String		  securityRequired3;	//GNVSECREQ3
	private String		  securitySystem4;		//GNVSECSYS4
	private String		  securityRequired4;	//GNVSECREQ4
	private String		  securitySystem5;		//GNVSECSYS5
	private String		  securityRequired5;	//GNVSECREQ5
	private String		  securityValue1;		//GNVSECVAL1
	private String		  securityValue2;		//GNVSECVAL2
	private String		  securityValue3;		//GNVSECVAL3
	private String		  securityValue4;		//GNVSECVAL4
	private String		  securityValue5;		//GNVSECVAL5	
	private String        canIUseIt;		
	private String        canIChangeIt;

	//live or test environment on the as400
	private String  library;

	//** Additional Fields **//
	private java.sql.Date newDate;
	private java.sql.Date revisedDate;
	
	private String  urlSplitPath;

	private static boolean persists = false;
	

	
//**** For use in Main Method and 
    // Constructor Methods and 
    // Update, Add, Delete Methods  ****//
 
	private static Stack dltSqlStack = null;      //delete sql.
	private static Stack addSqlStack = null;      //add sql.
	private static Stack updSqlStack = null;      //upd sql.
	private static Stack fbuAddressStack = null;  //find by url address.
	private static Stack fbuNumberStack = null;   //find by url number.
	private static Stack fbMajNumberStack = null; //find by major number.
	private static Stack fbMinNumberStack = null; //find by minor number.
	private static Stack addDppmurluseStack = null;
	private static Stack dltDppmurluseStack = null;
	private static Stack fluAddressStack = null;  //find like url address.
	
/**
 * UrlFile constructor comment.
 */
public UrlFile() {

		init();
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public UrlFile(Integer urlNumberIn) 
	throws InstantiationException { 

	if (persists == false)
		init();
		
	ResultSet rs = null;
	
	try {
		PreparedStatement fbuNumber = (PreparedStatement) fbuNumberStack.pop();
		fbuNumber.setInt(1, urlNumberIn.intValue());
		rs = fbuNumber.executeQuery();
		fbuNumberStack.push(fbuNumber);
		
		if (rs.next() == false)
		{
			System.out.println("instantiation error for urlNumber (" + 
				               urlNumberIn + ")" +
				               " - com.treetop.data.UrlFile(Integer). ");
			throw new InstantiationException("The url: " + urlNumberIn + " not found" +
				                             " - com.treetop.data.UrlFile(Integer). ");
		}
		
		loadFields(rs, "");
		rs.close();	
				
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.UrlFile(Integer) " + e);
	}
	
	return;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public UrlFile(String type,
			   Integer numberIn) 
	throws InstantiationException { 

	if (persists == false)
		init();
		
	ResultSet rs = null;

	if (type == null)
	   type = "url";

	if (type.equals("major"))
	{
	   try {
		   PreparedStatement fbMajNumber = (PreparedStatement) fbMajNumberStack.pop();
		   fbMajNumber.setInt(1, numberIn.intValue());
		   rs = fbMajNumber.executeQuery();
		   fbMajNumberStack.push(fbMajNumber);
		
		   if (rs.next() == false)
		   {
			   System.out.println("Instantiation Maj error on type(" + type + ") " +
				                  "urlNumber(" + numberIn + ") at" +
				                  " - com.treetop.data.UrlFile(String, Integer)");
			   throw new InstantiationException("The major: " + numberIn + " was not found");
		   }
   	   } catch (SQLException e) {
		   System.out.println("Sql error at com.treetop.data.UrlFile(String, Integer) " + e);
  		   return;
 	     }
	}

	if (type.equals("minor"))
	{
	   try {
		   PreparedStatement fbMinNumber = (PreparedStatement) fbMinNumberStack.pop();
		   fbMinNumber.setInt(1, numberIn.intValue());
		   rs = fbMinNumber.executeQuery();
		   fbMinNumberStack.push(fbMinNumber);
			
		   if (rs.next() == false)
		   {
			   System.out.println("Instantiation Min error on type(" + type + ") " +
				                  "urlNumber(" + numberIn + ") at" +
				                  " - com.treetop.data.UrlFile(String, Integer)");
			   throw new InstantiationException("The minor: " + numberIn + " was not found");
		   }
   	   } catch (SQLException e) {
		   System.out.println("Sql error at com.treetop.data.UrlFile.UrlFile(String) " + e);
  		   return;
 	     }
	}

	if (type.equals("url")) //Default
	{
	   try {
		   PreparedStatement fbuNumber = (PreparedStatement) fbuNumberStack.pop();
		   fbuNumber.setInt(1, numberIn.intValue());
		   rs = fbuNumber.executeQuery();
		   fbuNumberStack.push(fbuNumber);
		
		   if (rs.next() == false)
		   {
			   System.out.println("Instantiation error on urlNumber(" +
				                  numberIn + 
				                  " - com.treetop.data.UrlFile(String, Integer).");
			   throw new InstantiationException("The url: " + numberIn + " was not found");
		   }
 	   } catch (SQLException e) {
		   System.out.println("Sql error at com.treetop.data.UrlFile.UrlFile(String) " + e);
  		   return;
 	     }
	}
	
	loadFields(rs, "");
	try {
		rs.close();
	} catch(Exception e) {
		System.out.println("Error when closing cursor at - com.treetop.data." +
			               "UrlFile(String:" + type + ", Integer:" + numberIn + ")");
	}	
}
/**
 * Load a class from a URL
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public UrlFile(String urlAddress, String exactly) 
	throws InstantiationException { 

	if (persists == false)
		init();
		
	ResultSet rs = null;

	if (urlAddress == null)
	   urlAddress = "";

	if (exactly == null)
		exactly = "";

	if (urlAddress == "")
	{
		throw new InstantiationException("The Url: " + 
			                              urlAddress + " was not found");
	}
	
	try {
		if (!exactly.equals("yes"))
		{
			urlAddress = "%" + urlAddress + "%";
			PreparedStatement fluAddress = (PreparedStatement) fluAddressStack.pop();
			fluAddress.setString(1, urlAddress.trim());
	    	rs = fluAddress.executeQuery();
	    	fluAddressStack.push(fluAddress);
		}
		else
		{
			PreparedStatement fbuAddress = (PreparedStatement) fbuAddressStack.pop();
		    fbuAddress.setString(1, urlAddress.trim());
		   	rs = fbuAddress.executeQuery();
		   	fbuAddressStack.push(fbuAddress);
		}
	 	
	
	   	if (rs.next() == false)
		   	throw new InstantiationException("The Url: " + 
			   	                              urlAddress + " was not found");

		loadFields(rs, "");
		rs.close();
			
    } catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.UrlFile.UrlFile(String) " + e);
   	}
		
 	return;		
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void add() {

	try {
		PreparedStatement addSql = (PreparedStatement) addSqlStack.pop();
		addSql.setInt(1, majorNumber.intValue());
		addSql.setInt(2, minorNumber.intValue());
		addSql.setString(3, urlAddress);
		addSql.setInt(4, urlNumber.intValue());
		addSql.setString(5, newStatus);
		addSql.setString(6, newComment);
		addSql.setString(7, searchValues);
		addSql.setString(8, urlDescription);
		addSql.setInt(9, orderBy.intValue());
		addSql.setInt(10, authorityLevel.intValue());
		addSql.setString(11, displayOnMain);
		addSql.setString(12, extra);
		addSql.setString(13, title);
		addSql.setString(14, type);
		addSql.setString(15, path);
		addSql.setString(16, name);
		addSql.setString(17, createUser);
		addSql.setDate(18, createDate);
		addSql.setTime(19, createTime);
		addSql.setString(20, updateUser);
		addSql.setDate(21, updateDate);
		addSql.setTime(22, updateTime);
		addSql.setString(23, pathSecurity);
		addSql.setString(24, securitySystem1);
		addSql.setString(25, securityRequired1);
		addSql.setString(26, securitySystem2);
		addSql.setString(27, securityRequired2);
		addSql.setString(28, securitySystem3);
		addSql.setString(29, securityRequired3);
		addSql.setString(30, securitySystem4);
		addSql.setString(31, securityRequired4);
		addSql.setString(32, securitySystem5);
		addSql.setString(33, securityRequired5);
		addSql.setString(34, securityValue1);
		addSql.setString(35, securityValue2);
		addSql.setString(36, securityValue3);
		addSql.setString(37, securityValue4);
		addSql.setString(38, securityValue5);								
		addSql.executeUpdate();
		addSqlStack.push(addSql);

		PreparedStatement addDppmurluse = (PreparedStatement) addDppmurluseStack.pop();
		addDppmurluse.setString(1, "R");	
		addDppmurluse.setInt(2, urlNumber.intValue());
		addDppmurluse.setInt(3, 8);
		addDppmurluse.executeUpdate();

		addDppmurluse.setString(1, "R");
		addDppmurluse.setInt(2, urlNumber.intValue());
		addDppmurluse.setInt(3, 1);
		addDppmurluse.executeUpdate();
		addDppmurluseStack.push(addDppmurluse);
		
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.UrlFile.add(): " + e);
	}	
}
/**
 * Insert the method's description here.
 * Creation date: (5/13/2003 8:24:29 AM)
 */
public static void addRoleToUrlNumber(int urlNumber,
	                                  int roleNumber)  
	throws InvalidLengthException, Exception
{
	
	PreparedStatement addDppmurluse = (PreparedStatement) addDppmurluseStack.pop();
	addDppmurluse.setString(1, "R");
	addDppmurluse.setInt(2, urlNumber);
	addDppmurluse.setInt(3, roleNumber);
	addDppmurluse.executeUpdate();
	addDppmurluseStack.push(addDppmurluse);

}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public static void addToUrlFile(int majorNumber, 
								int minorNumber,
								String urlAddress, 
								int urlNumber, 
								String newStatus, 
								String newComment, 
								String searchValues,
								String urlDescription,
								int orderBy, 
								int authorityLevel, 
								String displayOnMain,
								String extra,
								String title,
								String type,
								String path,
								String name,
								String createUser,
								java.sql.Date createDate,
								java.sql.Time createTime,
								String updateUser,
								java.sql.Date updateDate,
								java.sql.Time updateTime,
								String pathSecurity,
								String securitySystem1,
								String securityRequired1,
								String securitySystem2,
								String securityRequired2,
								String securitySystem3,
								String securityRequired3,
								String securitySystem4,
								String securityRequired4,
								String securitySystem5,
								String securityRequired5,
								String securityValue1,
								String securityValue2,
								String securityValue3,
								String securityValue4,
								String securityValue5) 
	throws InvalidLengthException, Exception
{
	UrlFile newUrlRecord = new UrlFile();
	newUrlRecord.setMajorNumber(majorNumber);
	newUrlRecord.setMinorNumber(minorNumber);
	newUrlRecord.setUrlAddress(urlAddress);
	newUrlRecord.setUrlNumber(urlNumber);
	newUrlRecord.setNewStatus(newStatus);
	newUrlRecord.setNewComment(newComment);
	newUrlRecord.setSearchValues(searchValues);
	newUrlRecord.setUrlDescription(urlDescription);
	newUrlRecord.setOrderBy(orderBy);
	newUrlRecord.setAuthorityLevel(authorityLevel);
	newUrlRecord.setDisplayOnMain(displayOnMain);
	newUrlRecord.setExtra(extra);
	newUrlRecord.setTitle(title);
	newUrlRecord.setType(type);
	newUrlRecord.setPath(path);
	newUrlRecord.setName(name);
	newUrlRecord.setCreateUser(createUser);
	newUrlRecord.setCreateDate(createDate);
	newUrlRecord.setCreateTime(createTime);
	newUrlRecord.setUpdateUser(updateUser);
	newUrlRecord.setUpdateDate(updateDate);
	newUrlRecord.setUpdateTime(updateTime);
	newUrlRecord.setPathSecurity(pathSecurity);
	newUrlRecord.setSecuritySystem1(securitySystem1);
	newUrlRecord.setSecurityRequired1(securityRequired1);
	newUrlRecord.setSecuritySystem2(securitySystem2);
	newUrlRecord.setSecurityRequired2(securityRequired2);
	newUrlRecord.setSecuritySystem3(securitySystem3);
	newUrlRecord.setSecurityRequired3(securityRequired3);
	newUrlRecord.setSecuritySystem4(securitySystem4);
	newUrlRecord.setSecurityRequired4(securityRequired4);
	newUrlRecord.setSecuritySystem5(securitySystem5);
	newUrlRecord.setSecurityRequired5(securityRequired5);
	newUrlRecord.setSecurityValue1(securityValue1);
	newUrlRecord.setSecurityValue2(securityValue2);
	newUrlRecord.setSecurityValue3(securityValue3);
	newUrlRecord.setSecurityValue4(securityValue4);
	newUrlRecord.setSecurityValue5(securityValue5);				
	newUrlRecord.setCanIUseIt("");
	newUrlRecord.setCanIChangeIt("");
	newUrlRecord.add();

}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
protected static UrlFile addUrlFile(int majorNumber, 
									int minorNumber, 
									String urlAddress, 
									int urlNumber, 
									String newStatus,  
									String newComment, 
									String searchValues, 
									String urlDescription,
									int orderBy, 
									int authorityLevel, 
									String displayOnMain,
									String extra,
									String title,
									String type,
									String path,
									String name,
									String createUser,
									java.sql.Date createDate,
									java.sql.Time createTime,
									String updateUser,
									java.sql.Date updateDate,
									java.sql.Time updateTime,
									String pathSecurity,
									String securitySystem1,
									String securityRequired1,
									String securitySystem2,
									String securityRequired2,
									String securitySystem3,
									String securityRequired3,
									String securitySystem4,
									String securityRequired4,
									String securitySystem5,
									String securityRequired5,
									String securityValue1,
									String securityValue2,
									String securityValue3,
									String securityValue4,
									String securityValue5)  
	throws InvalidLengthException, Exception
{
	UrlFile newUrlRecord = new UrlFile();
	newUrlRecord.setMajorNumber(majorNumber);
	newUrlRecord.setMinorNumber(minorNumber);
	newUrlRecord.setUrlAddress(urlAddress);
	newUrlRecord.setUrlNumber(urlNumber);
	newUrlRecord.setNewStatus(newStatus);
	newUrlRecord.setNewComment(newComment);
	newUrlRecord.setSearchValues(searchValues);
	newUrlRecord.setUrlDescription(urlDescription);
	newUrlRecord.setOrderBy(orderBy);
	newUrlRecord.setAuthorityLevel(authorityLevel);
	newUrlRecord.setDisplayOnMain(displayOnMain);
	newUrlRecord.setExtra(extra);
	newUrlRecord.setTitle(title);
	newUrlRecord.setType(type);
	newUrlRecord.setPath(path);
	newUrlRecord.setName(name);
	newUrlRecord.setCreateUser(createUser);
	newUrlRecord.setCreateDate(createDate);
	newUrlRecord.setCreateTime(createTime);
	newUrlRecord.setUpdateUser(updateUser);
	newUrlRecord.setUpdateDate(updateDate);
	newUrlRecord.setUpdateTime(updateTime);
	newUrlRecord.setPathSecurity(pathSecurity);
	newUrlRecord.setSecuritySystem1(securitySystem1);
	newUrlRecord.setSecurityRequired1(securityRequired1);
	newUrlRecord.setSecuritySystem2(securitySystem2);
	newUrlRecord.setSecurityRequired2(securityRequired2);
	newUrlRecord.setSecuritySystem3(securitySystem3);
	newUrlRecord.setSecurityRequired3(securityRequired3);
	newUrlRecord.setSecuritySystem4(securitySystem4);
	newUrlRecord.setSecurityRequired4(securityRequired4);
	newUrlRecord.setSecuritySystem5(securitySystem5);
	newUrlRecord.setSecurityRequired5(securityRequired5);
	newUrlRecord.setSecurityValue1(securityValue1);
	newUrlRecord.setSecurityValue2(securityValue2);
	newUrlRecord.setSecurityValue3(securityValue3);
	newUrlRecord.setSecurityValue4(securityValue4);
	newUrlRecord.setSecurityValue5(securityValue5);
	newUrlRecord.setCanIUseIt("");
	newUrlRecord.setCanIChangeIt("");
	newUrlRecord.add();
	return newUrlRecord;
}
/**
 *  Section to Build/Load Array's with Path Sections and URL's to make them links
 * Creation date: (2/12/2003 1:53:01 PM)
 */
private String buildSplitPath(String path, 
						      String title,
						      String urlAddress,
						      String type)
{
	//****TESTING****
	//System.out.println("UrlFile.buildSplitPath");
	//System.out.println("Path = " + path);
   
   String getSplitPath = "";
   String testPath = path.trim();
   
   if (type.trim().equals("folder"))
   {
	   testPath = urlAddress.trim();
   }
   //*** Read through the path to figure out how many /'s there are.
   //--------------------------------------------------------------------------
   //-- May be replaced, could use Class PuString, method containsChars
   //-----------------------------------------

   String slashtest = testPath;
   int slashcount = 0;
   int findslash = 1;

   while (findslash > 0)
   {
      findslash = slashtest.lastIndexOf("/");
      if (findslash >= 0)
      {
         slashcount = slashcount + 1;
         if (findslash > 0)
         {
            slashtest = slashtest.substring(0, findslash);
         }
      }
   }

  
   slashtest = testPath;
   int pathLength = slashtest.length();
   findslash = 1;
   String savePath = "";

   if (pathLength > 3)
   {
 	  if (slashtest.substring(0, 3).equals("../"))
 	  {
    	  slashtest = slashtest.substring(3, slashtest.length());
 	      slashcount = slashcount - 1;
 	      pathLength = slashtest.length();
 	  }
   }
   else
   {
	   slashtest = "";
	   slashcount = 0;
   }
 		
	if (slashcount > 0)
	{
		for (int i = 0; i < slashcount; i++)
		{
			findslash = slashtest.indexOf("/");
	 		if (findslash > 0)
     		{
	   			if (findslash != pathLength)
	        	{
					try
					{ 
						String findFolder = "../" + savePath + slashtest.substring(0, findslash + 1);
						PreparedStatement fbuAddress = (PreparedStatement)
						                                 fbuAddressStack.pop();
 	                    fbuAddress.setString(1, findFolder.trim());
			            ResultSet rs = fbuAddress.executeQuery();
			            fbuAddressStack.push(fbuAddress);
						try
						{
					    	if (rs.next())
					        {
						    	 if (!getSplitPath.equals(""))
								   getSplitPath = getSplitPath + " / ";
   
					  		    getSplitPath = getSplitPath +
        	  	  				"<a class='a04002' href='TreeNetList?" +
        	  	  				"path=" + findFolder.substring(2, findFolder.length()) +          						
         						"&type=minor&typevalue=100\'>" +
           						rs.getString("GNVTITLE") + "</a>" ;

	       					 }
					         rs.close();
					    }
    				    catch(Exception e)
						{
							System.out.println("Exception within UrlFile.buildSplitPath: " + e);
						}
					}
					catch(SQLException sqle)
					{
						System.out.println("SQL Exception within UrlFile.buildSplitPath: " + sqle);
					}



		        }	
   	         
			}

 	   if (slashtest.substring(0, (findslash)).equals(".."))
 	   {
	   	   slashtest = slashtest.substring((findslash + 1), pathLength);
	   	   savePath = "/";
 	   }
 	   else
 	   {
	 	  if (findslash != 1)
 	 	     savePath = savePath + slashtest.substring(0, findslash) + "/";
 	 	     
	      slashtest = slashtest.substring((findslash + 1), pathLength);
 	   }
	   
       pathLength = slashtest.length(); 
      
	   }
  }
	
return getSplitPath;
}
/**
 *  Section to Build a string which has the title by section instead of the path section
 * Creation date: (2/12/2003 1:53:01 PM)
 */
public static String buildTitlePath(String path)
{
	//****TESTING****
	//  System.out.println("UrlFile.buildTitlePath");
	//  System.out.println("Path = " + path);
   
   String getTitlePath = "";
   
   
   //*** Read through the path to figure out how many /'s there are.
   //--------------------------------------------------------------------------
   //-- May be replaced, could use Class PuString, method containsChars
   //-----------------------------------------
   String slashtest = path.trim();
   int slashcount = 0;
   int findslash = 1;
   
   while (findslash > 0)
   {
      findslash = slashtest.lastIndexOf("/");
      if (findslash >= 0)
      {
         slashcount = slashcount + 1;
         if (findslash > 0)
         {
            slashtest = slashtest.substring(0, findslash);
         }
      }
   }

  
   slashtest = path.trim();
   int pathLength = slashtest.length();
   findslash = 1;
   String savePath = "";

   if (pathLength > 1)
   {
 	  if (slashtest.substring(0, 1).equals("/"))
 	  {
    	  slashtest = slashtest.substring(1, slashtest.length());
 	      slashcount = slashcount - 1;
 	      pathLength = slashtest.length();
 	  }
   }
   else
   {
	   slashtest = "";
	   slashcount = 0;
   }
 		
	if (slashcount > 0)
	{
		for (int i = 0; i < slashcount; i++)
		{
			findslash = slashtest.indexOf("/");
	 		if (findslash > 0)
     		{
	   			if (findslash != pathLength)
	        	{
					try
					{ 
						String findFolder = "../" + savePath + slashtest.substring(0, findslash + 1);
						PreparedStatement fbuAddress = (PreparedStatement)
						                                fbuAddressStack.pop();
 	                    fbuAddress.setString(1, findFolder.trim());
			            ResultSet rs = fbuAddress.executeQuery();
			            fbuAddressStack.push(fbuAddress);
						try
						{
					    	if (rs.next())
					        {
						    	 if (!getTitlePath.equals(""))
								   getTitlePath = getTitlePath + " / ";
 
					  		    getTitlePath = getTitlePath + rs.getString("GNVTITLE").trim();
	       					 }
					         rs.close();
					    }
    				    catch(Exception e)
						{
							System.out.println("Exception within UrlFile.buildSplitPath: " + e);
						}
					}
					catch(SQLException sqle)
					{
						System.out.println("SQL Exception within UrlFile.buildSplitPath: " + sqle);
					}



		        }	
   	         
			}

 	   if (slashtest.substring(0, (findslash)).equals(".."))
 	   {
	   	   slashtest = slashtest.substring((findslash + 1), pathLength);
	   	   savePath = "/";
 	   }
 	   else
 	   {
	 	  if (findslash != 1)
 	 	     savePath = savePath + slashtest.substring(0, findslash) + "/";
 	 	     
	      slashtest = slashtest.substring((findslash + 1), pathLength);
 	   }
	   
       pathLength = slashtest.length(); 
      
	   }
  }
	
return getTitlePath;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void delete() {

	try {
		PreparedStatement dltSql = (PreparedStatement) dltSqlStack.pop();
		dltSql.setInt(1, urlNumber.intValue());
		dltSql.executeUpdate();
		dltSqlStack.push(dltSql);
	} catch (SQLException e) {
		System.out.println("SQL Exception at com.treetop.data.delete(): " + e);
	}	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public boolean deleteByUrlNumber(int urlNumberIn) {

	try {
		PreparedStatement dltSql = (PreparedStatement) dltSqlStack.pop();
		dltSql.setInt(1, urlNumberIn);
		dltSql.executeUpdate();
		dltSqlStack.push(dltSql);

		PreparedStatement dltDppmurluse = (PreparedStatement) dltDppmurluseStack.pop();
		dltDppmurluse.setInt(1, urlNumberIn);
		dltDppmurluse.setString(2, "R"); 
		dltDppmurluse.executeUpdate();
		dltDppmurluseStack.push(dltDppmurluse);
		
		return true;
		
	} catch (Exception e) {
		System.out.println("SQL Exception at com.treetop.data.delete(): " + e);
		return false;
	}	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public boolean deleteByUrlPath(String urlPathIn) {

	ResultSet rs = null;
	
	try {
		PreparedStatement fluAddress = (PreparedStatement) fluAddressStack.pop();
		fluAddress.setString(1, urlPathIn + "%");
		rs = fluAddress.executeQuery();
		fluAddressStack.push(fluAddress);
	} catch (SQLException e) {
		System.out.println("Sql error at - com.treetop.data.UrlFile." +
			               "deleteByUrlPath(String:" + urlPathIn + "): " + e);
		return false;
	}
	
	
	try {
		
		while (rs.next())
		{
			boolean x = deleteByUrlNumber(rs.getInt("GNVURLNBR"));
		}
		rs.close();
		return true;
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.UrlFile." + 
			               "deleteByUrlPath(String:" + urlPathIn + "): " + e);
		return false;
	}
			
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public static boolean deleteRolesByUrlNumber(int urlNumberIn) {

	try {
		PreparedStatement dltDppmurluse = (PreparedStatement) dltDppmurluseStack.pop();
		dltDppmurluse.setInt(1, urlNumberIn);
		dltDppmurluse.setString(2, "R");
		dltDppmurluse.executeUpdate();
		dltDppmurluseStack.push(dltDppmurluse);
		
		return true;
		
	} catch (Exception e) {
		System.out.println("SQL Exception at com.treetop.data.delete(): " + e);
		return false;
	}	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public static ResultSet findByUrlAddress(String urlAddressIn) 
{
	ResultSet rs = null;

	try
	{
		PreparedStatement fbuAddress = (PreparedStatement) fbuAddressStack.pop();
		fbuAddress.setString(1, urlAddressIn.trim());
		rs = fbuAddress.executeQuery();
		fbuAddressStack.push(fbuAddress);
	}
	catch (SQLException e)
	{
		System.out.println("Sql error at com.treetop.data.UrlFile." +
			               "findByUrlAddress(String:" + urlAddressIn + "): " + e);
		return null;
	}
	
	return rs;
	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public static Vector findByUrlNumber(Integer urlNumberIn) {

	ResultSet rs = null;
	
	try {
		PreparedStatement fbuNumber = (PreparedStatement) fbuNumberStack.pop();
		fbuNumber.setInt(1, urlNumberIn.intValue());
		rs = fbuNumber.executeQuery();
		fbuNumberStack.push(fbuNumber);
	} catch (SQLException e) {
		System.out.println("Sql error on urlNumber(" + urlNumberIn + ") at" +
			               " - com.treetop.data.UrlFile.findByUrlNumber(" +
			               "Integer): " + e);
		return null;
	}
	
	Vector someUrls = new Vector();
	
	try {
		while (rs.next())
		{
			UrlFile aUrlFile = new UrlFile();
			aUrlFile.loadFields(rs, "");
			someUrls.addElement(aUrlFile);
		}
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.UrlFile.findByUrlNumber(): " + e);
		return null;
	}

	return someUrls;		
			
}
/**
 * Brings back a Vector (Class) of Majors based on:
 *    Role
 *    Can use the requestType to change if not to use Role security.
 * Creation date: (4/10/2003 1:53:01 PM)
 * Modified date: (5/14/2009) - closed the statement.
 */
public Vector findMajors(String[] roles,
 						 String requestType)
{
	try {
		// Define database environment prior to every export (live or test).
		//String library = "WKLIB."; // test environment
		String library = "TREENET."; // live environment
		//System.out.println("GNJMAJ - UrlFile - get findMajors()"); - 05/14/09 close the statement
		
		// Define the SQL statement.
		String SQLRun = "SELECT * " + 
                        "FROM " + library + "GNJMAJ ";
                        
        String roleList = "";
		int x = roles.length;
	
		if (x > 0)
		{
			roleList = "IN (" + roles[0];
		    for (int z = 1; z < x; z++)
   		    {
			    roleList = roleList + "," + roles[z];
   		   	}
   		 	roleList = roleList + ") ";
		}		
		
	    SQLRun = SQLRun + "WHERE DPMSECNBR " + roleList + " ";    			                   
		String SQLOrder = "ORDER BY GNVTITLE ";

		Vector listMajors = new Vector();
	
		Connection newConn = null;
		try{

			newConn = ConnectionStack.getConnection();
			Statement stmt = newConn.createStatement();
		 	ResultSet rs = stmt.executeQuery(SQLRun + " " + SQLOrder);
			
			int saveURL = 0;
			
			try {
    			while (rs.next())
        		{
	     		//   System.out.println(rs.getString("GNVTITLE"));
	        		if (saveURL != rs.getInt("GNVURLNBR"))
	        		{
		        		saveURL = rs.getInt("GNVURLNBR");
		        
  		        		UrlFile buildVector = new UrlFile();
   	    				buildVector.loadFields(rs, requestType);
   	    		
 		 	    		listMajors.addElement(buildVector);
	        		}
	    		}
        		rs.close();
        		stmt.close();
        
	 		}
			
	 		catch (Exception e) {
		 		System.out.println("Exception Error while Reading a result set (UrlFile.findMajors)" + e);
	 		}

 		}
		
		catch (Exception e) {
			System.out.println("Exception on Running SQL (UrlFile.findMajors) " + e);
		}
		finally
		{
			try
			{
				ConnectionStack.returnConnection(newConn);
			}
			catch(Exception e)
			{}
		}
		return listMajors;

	} catch (Exception x) {
		System.out.println("error: " + x);
	}
	return null;
}
/**
 * Brings back a Vector (Class) of Majors based on:
 *    Role
 *    Can use the requestType to change if not to use Role security.
 * Creation date: (4/10/2003 1:53:01 PM)
 * Modified       (5/14/2009) close stetement left open.
 */
public Vector findMajorsbyMinor(String[] roles,
	                     int minornum,
 						 String requestType)
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment
	//System.out.println("GNJMAJOR - UrlFile - get findMajorsbyMinor()"); - 5/14/09 closed the statement (stmt).
	// Define the SQL statement.
	String SQLRun = "SELECT * " + 
                    "FROM " + library + "GNJMAJOR ";                        
    String roleList = "";
	int x = roles.length;
	
	if (x > 0)
	{
		roleList = "IN (" + roles[0];
		
		for (int z = 1; z < x; z++)
   		{
			roleList = roleList + "," + roles[z];
   		}
   		roleList = roleList + ") ";
	}		
		
	SQLRun = SQLRun + 
	     	 "WHERE DPMSECNBR " + roleList + " ";	
    SQLRun = SQLRun + "AND DPMMINNBR = " + minornum + " ";
                           
	String SQLOrder = "ORDER BY GNVTITLE ";
	Vector listMinors = new Vector();

	Connection newConn = null;
	try{
		newConn = ConnectionStack.getConnection();
		Statement stmt = newConn.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun + " " + SQLOrder);
			            
		int saveURL = 0;
		
		try {
			
    		while (rs.next())
        	{
	      
	        	if (saveURL != rs.getInt("GNVURLNBR"))
	        	{
		        	saveURL = rs.getInt("GNVURLNBR");
		        
  		        	UrlFile buildVector = new UrlFile();
   	    			buildVector.loadFields(rs, requestType);
 		 	    	listMinors.addElement(buildVector);
	        	}
	    	}
        	rs.close();
        	stmt.close();
	 	}
		
	 	catch (Exception e) {
			System.out.println("Exception Error while Reading a result set (UrlFile.findMinorsbyMajor)" + e);
	 	}

 	} catch (Exception e) {
		System.out.println("Exception on Running SQL (UrlFile.findMinorsbyMajor) " + e);
	}
 	finally
	{
 		try
		{
 			ConnectionStack.returnConnection(newConn);
		}
 		catch(Exception e)
		{}
	}

	return listMinors;
}
/**
 * Brings back a Vector (Class) of Minors based on:
 *    Role
 *    Can use the requestType to change if not to use Role security.
 * Creation date: (4/10/2003 1:53:01 PM)
 * Mofified date: (05/14/2009) - Closed a statement left open.
 */
public Vector findMinorsbyMajor(String[] roles,
	                     int majornum,
 						 String requestType)
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment
	//System.out.println("GNJMINOR - UrlFile - get findMinorsbyMajor()");	- close a statement left open
	// Define the SQL Statement.
	String SQLRun = "SELECT * " + 
                    "FROM " + library + "GNJMINOR ";
                   
    String roleList = "";
	int x = roles.length;
	
	if (x > 0)
	{
		roleList = "IN (" + roles[0];
		
		for (int z = 1; z < x; z++)
   		{
			roleList = roleList + "," + roles[z];
   		}
   		
   		roleList = roleList + ") ";
	}		
		
	SQLRun = SQLRun + "WHERE DPMSECNBR " + roleList + " ";
	     			
    SQLRun = SQLRun + "AND DPMMAJNBR = " + majornum + " ";
                           
	String SQLOrder = "ORDER BY GNVTITLE ";

	Vector listMinors = new Vector();

	Connection newConn = null;
	try{
	    newConn = ConnectionStack.getConnection();
		Statement stmt = newConn.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun + " " + SQLOrder);
			            
		int saveURL = 0;
		
		try {
    		while (rs.next())
        	{
	        
	        	if (saveURL != rs.getInt("GNVURLNBR"))
	        	{
		        	saveURL = rs.getInt("GNVURLNBR");
		        
  		        	UrlFile buildVector = new UrlFile();
   	    			buildVector.loadFields(rs, requestType);
 		 	    	listMinors.addElement(buildVector);
	        	}
	    	}
        	rs.close();
        	stmt.close();
        
	 	} catch (Exception e)  {
			System.out.println("Exception Error while Reading a result set (UrlFile.findMinorsbyMajor)" + e);
	 	}

 	} catch (Exception e) {
		System.out.println("Exception on Running SQL (UrlFile.findMinorsbyMajor) " + e);
	}
 	finally
	{
 		try
		{
 			ConnectionStack.returnConnection(newConn);
		}
 		catch(Exception e)
		{}
	}

	return listMinors;
}
/**
 * Brings back a Vector (Class) of Url's based on:
 *    Major Number,
 *    Minor Number
 *    Filtering with security
 * Creation date: (4/10/2003 1:53:01 PM)
 * Cleaned up (statement close, etc.) (4/28/2009) -WTH
 */
public Vector findUrlsbyMajMin(String[] roles,
						 	   String[] groups,
						 	   String[] paths,
						 	   String[] pubPaths,
						 	   String addFolder,
						 	   String user,
	                     	   int majornum,
	                     	   int minornum,
	                     	   String orderby,
	                      	   String path,
	                     	   String typevalue,
 						 	   String requestType)
{
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment
	
	StringBuffer throwError = new StringBuffer();
	Statement stmt			= null;
	ResultSet rs			= null;
	Connection conn			= null;
	
	if (path == null || path.equals("null"))
		path = "";
	//System.out.println("DPPNURLUSE - UrlFile - get findUrlsbyMajMin()"); - this was used to identify statement leaks. 04/28/09
	
	//start build the sql statement here.
	String SQLRun = "SELECT " +
                    "GNVMAJNBR, GNVMINNBR, GNVURL, GNVURLNBR, " +
                    "GNVNEWSTAT, GNVNEWCMMT, GNVKEYSRCH, GNVLNKDSC, " +
                    "GNVORDERBY, GNVAUTHLVL, GNVDSPMAIN, GNVEXTRA, " +
                    "GNVTITLE, GNVTYPE, GNVPATH, GNVNAME, " + 
                    "GNVCRTUSER, GNVCRTDATE, GNVCRTTIME, " +
                    "GNVUPDUSER, GNVUPDDATE, GNVUPDTIME, " +
                    "GNVPATHSEC, GNVSECSYS1, GNVSECREQ1, " +
                    "GNVSECSYS2, GNVSECREQ2, GNVSECSYS3, " +
                    "GNVSECREQ3, GNVSECSYS4, GNVSECREQ4, " +
                    "GNVSECSYS5, GNVSECREQ5, GNVSECVAL1, " +
                    "GNVSECVAL2, GNVSECVAL3, GNVSECVAL4, " +
                    "GNVSECVAL5, " +
                    "(GNVCRTDATE + 2 Months) as NEWDATE, " +
                    "(GNVUPDDATE + 2 Months) as REVDATE " + 
                    "FROM " + library + "GNJURL ";
                         
 
	String dateArray[] = SystemDate.getSystemDate();
	
    SQLRun = SQLRun + "WHERE GNVMAJNBR = " + majornum + " "+ 
                      "AND GNVMINNBR = " + minornum + " ";

    int x = roles.length;
     
    if (path.equals("N"))
    {
		String roleList = "";
		
		if (x > 0)
		{
			roleList = "IN (" + roles[0];
			
			for (int z = 1; z < x; z++)
	  		{
				roleList = roleList + "," + roles[z];
  	 		}
   			roleList = roleList + ") ";
		}
		
		SQLRun = SQLRun + "AND DPMSECNBR " + roleList + " ";
	 	   		
	}
    else
    {
	    
		if (!path.equals(""))
 	 	{
  			path = path.trim();
    	    x = paths.length;
     	    String addPathInfo = "";
     	    
    		for (int z = 0; z < x; z++)
       		{
	       		
	       		if (paths[z].trim().equals(path) )
				{
	      			addPathInfo = "AND GNVPATH = \'" + path + "\' ";
         		}
			}    		
       		
       		if (addPathInfo.equals(""))
       		{
	       		addPathInfo = "AND GNVPATH = \'" + path + "\' " +
	       			  				"AND GNVTYPE = 'folder' ";
	       	}
       		
 	      	SQLRun = SQLRun + addPathInfo;
  		  	}
    		else
    		{
	   			String pathsList = "";
	   	 	 	x = paths.length;
	   	 	 	
			   	if (x > 0)
		   		{
				    pathsList = "IN ('" + paths[0];
				    
  		  		    for (int z = 1; z < x; z++)
   		   		    {
			 		    pathsList = pathsList + "','" + paths[z];
   				    }
   		   		    
   		 	 		pathsList = pathsList + "') ";
   		  		
   		  		    if (requestType.equals("ListNewDocs"))
   		   		    {	
	   		    		SQLRun = SQLRun + "AND GNVPATH " + pathsList + " " +
	   		     	             "AND GNVTYPE <> 'folder' ";
		   		    }
 	  		   	}
		   		
	 	  		if (requestType.equals("SearchDocs"))
		 		{
				    if (x > 0)
				    {
				    	SQLRun = SQLRun + "AND (GNVTYPE = 'folder' " +
				       			"OR GNVPATH " + pathsList + ") ";
			 	   	}
				    
	   			    else
	   			    {
		   			    SQLRun = SQLRun + "AND GNVTYPE = 'folder' ";
	   		  	  	}
   		    	}
		   	 }
				
        }
    
	 	if (requestType.equals("search") || requestType.equals("SearchDocs"))
	 	{
	  	    if (!typevalue.equals("%"))
	   	   	{
	    		typevalue = typevalue.toUpperCase();
	     	   	typevalue = "%" + typevalue + "%";
	     	}

	       	SQLRun = SQLRun +
                  "AND (UPPER(GNVKEYSRCH) LIKE \'" + typevalue + "\' " +
                  "OR UPPER(GNVLNKDSC) LIKE \'" + typevalue + "\' " +
                  "OR UPPER(GNVURL) LIKE \'" + typevalue + "\' " +
                  "OR UPPER(GNVTITLE) LIKE \'" + typevalue + "\' ) ";
 	    } 
	
        if (requestType.equals("ListNew") || requestType.equals("ListNewDocs"))
        {
	    	SQLRun = SQLRun + 
                           "AND ((\'" + dateArray[7] + 
                           "\' BETWEEN GNVCRTDATE AND (GNVCRTDATE + 2 Months)) OR (\'" +
    					   dateArray[7] +
                           "\' BETWEEN GNVUPDDATE AND (GNVUPDDATE + 2 Months))) ";
        }
              
   		//***************************************//                         
   		//*** Determine the Order By Section  ***//
   
		String SQLOrder = "ORDER BY GNVTITLE "; // Default Orderby "TitleA"
	
		if (orderby.equals("TypeA"))
	   		SQLOrder = "ORDER BY GNVTYPE, GNVPATH, GNVNAME ";
	 
		if (orderby.equals("TypeD"))
       		SQLOrder = "ORDER BY GNVTYPE Desc, GNVPATH Desc, GNVNAME Desc ";
       
		if (orderby.equals("TitleD"))
	   		SQLOrder = "ORDER BY GNVTITLE Desc";
	 
		if (orderby.equals("DescA"))
	   		SQLOrder = "ORDER BY GNVLNKDSC ";
	 
		if (orderby.equals("DescD"))
	   		SQLOrder = "ORDER BY GNVLNKDSC Desc ";
	
    	if (orderby.equals("DateA"))
	   		SQLOrder = "ORDER BY GNVUPDDATE, GNVTITLE ";
	
		if (orderby.equals("DateD"))
       		SQLOrder = "ORDER BY GNVUPDDATE Desc, GNVTITLE Desc";

       	Vector listUrls = new Vector();
        
		try{
			conn = ConnectionStack.getConnection();
			stmt = conn.createStatement();
		 	rs = stmt.executeQuery(SQLRun + " " + SQLOrder);
			            	 
			int saveURL = 0;
			
			try {
    			while (rs.next())
    			{
	        
	        		if (saveURL != rs.getInt("GNVURLNBR"))
	        		{
		        		saveURL = rs.getInt("GNVURLNBR");
		        
  		        		UrlFile buildVector = new UrlFile();
   	    				buildVector.loadFields(rs, "ListPage");
   	    				UrlFile.updateAuthorityFields(buildVector,
	        									    	  roles,
	        									    	  groups,
	        									    	  paths,
	        									    	  pubPaths,
	        									    	  addFolder,
	        									    	  user);
 		 	    		listUrls.addElement(buildVector);
	        		}
	    		}
        		rs.close();
        		stmt.close();
	 		} catch (Exception e)  {
		 		throwError.append("Exception Error while Reading a result set " + e) ;
	 		}

 		} catch (Exception e) { 
			throwError.append("Exception on Running SQL. " + e);
		}
 		finally
		{
 			try
			{
 				ConnectionStack.returnConnection(conn);
 				rs.close();
 				stmt.close();
			}
 			catch(Exception e)
			{
 				throwError.append("Error on connection return/resultset close/statement close. " + e);
			}
 			
 			//log any errors.
 			if (!throwError.toString().equals(""))
 			{
 				throwError.append("Error at com.treetop.data.UrlFile.findUrlsbyMajMin(String[]). ");
 				System.out.println(throwError.toString());
 				Exception e = new Exception();
 				e.printStackTrace();
 			}
		}
	
		return listUrls;
	}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public int getAuthorityLevel() {

	return authorityLevel.intValue();	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getCanIChangeIt() {

	return canIChangeIt;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getCanIUseIt() {

	return canIUseIt;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public java.sql.Date getCreateDate() {

	return createDate;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public java.sql.Time getCreateTime() {

	return createTime;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getCreateUser() {

	return createUser;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getDisplayOnMain() {

	return displayOnMain;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getExtra() {

	return extra;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public int getMajorNumber() {

	return majorNumber.intValue();	
	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public int getMinorNumber() {

	return minorNumber.intValue();	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getName() {

	return name;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getNewComment() {

	return newComment;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public java.sql.Date getNewDate() {

	return newDate;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getNewStatus() {

	return newStatus;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public int getOrderBy() {

	return orderBy.intValue();	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getPath() {

	return path;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getPathSecurity() {

	return pathSecurity;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public java.sql.Date getRevisedDate() {

	return revisedDate;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getSearchValues() {

	return searchValues;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public String getSecuritySystem1() {

	return securitySystem1;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public String getSecurityRequired1() {

	return securityRequired1;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public String getSecuritySystem2() {

	return securitySystem2;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public String getSecurityRequired2() {

	return securityRequired2;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public String getSecuritySystem3() {

	return securitySystem3;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public String getSecurityRequired3() {

	return securityRequired3;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public String getSecuritySystem4() {

	return securitySystem4;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public String getSecurityRequired4() {

	return securityRequired4;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public String getSecuritySystem5() {

	return securitySystem5;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public String getSecurityRequired5() {

	return securityRequired5;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/23/2004 10:36:39 AM)
 */
public String getSecurityValue1() {

	return securityValue1;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/23/2004 10:36:39 AM)
 */
public String getSecurityValue2() {

	return securityValue2;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/23/2004 10:36:39 AM)
 */
public String getSecurityValue3() {

	return securityValue3;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/23/2004 10:36:39 AM)
 */
public String getSecurityValue4() {

	return securityValue4;	
}
/**
 * Insert the method's description here.
 * Creation date: (8/23/2004 10:36:39 AM)
 */
public String getSecurityValue5() {

	return securityValue5;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getTitle() {

	return title;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getType() {

	return type;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public java.sql.Date getUpdateDate() {

	return updateDate;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public java.sql.Time getUpdateTime() {

	return updateTime;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getUpdateUser() {

	return updateUser;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getUrlAddress() {

	return urlAddress;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String getUrlDescription() {

	return urlDescription;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public int getUrlNumber() {

	return urlNumber.intValue();	
	
}
/**
 * Insert the method's description here.
 * Creation date: (2/21/2003 10:36:39 AM)
 */
public String getUrlSplitPath() {

	return urlSplitPath;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void init() {

	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	String library = "TREENET."; // live environment
	
	// Test for initial connection.

//	System.out.println("persists = " + persists);
	
	if (persists == false) 
	{
	    persists = true;
	//    System.out.println("UrlFile persists = " + persists);
	    Connection conn1  = null;
		Connection conn2  = null;
		Connection conn3  = null;
		Connection conn4  = null;
		Connection conn5  = null;
		Connection conn6  = null;
		Connection conn7  = null;
		Connection conn8  = null;
		Connection conn9  = null;
		Connection conn10 = null;
	    
	
	
		try {
			// Retrieve ten connections from the connection pool.
			// Do not return these connections to the pool.

			conn1  = ConnectionStack.getConnection();
			conn2  = ConnectionStack.getConnection();
			conn3  = ConnectionStack.getConnection();
			conn4  = ConnectionStack.getConnection();
			conn5  = ConnectionStack.getConnection();
			conn6  = ConnectionStack.getConnection();
			conn7  = ConnectionStack.getConnection();
			conn8  = ConnectionStack.getConnection();
			conn9  = ConnectionStack.getConnection();
			conn10  = ConnectionStack.getConnection();
			
		// Create and stack multiple prepared statements.

			
			// Find By Url Number.
			String fbUrlNbr = 
				"SELECT * FROM " + library + "GNPVMENU " +
				" WHERE GNVURLNBR = ?";
			PreparedStatement fbUrlNbr1 = conn1.prepareStatement(fbUrlNbr);				
			PreparedStatement fbUrlNbr2 = conn2.prepareStatement(fbUrlNbr);				
			PreparedStatement fbUrlNbr3 = conn3.prepareStatement(fbUrlNbr);				
			PreparedStatement fbUrlNbr4 = conn4.prepareStatement(fbUrlNbr);				
			PreparedStatement fbUrlNbr5 = conn5.prepareStatement(fbUrlNbr);				
			PreparedStatement fbUrlNbr6 = conn6.prepareStatement(fbUrlNbr);				
			PreparedStatement fbUrlNbr7 = conn7.prepareStatement(fbUrlNbr);				
			PreparedStatement fbUrlNbr8 = conn8.prepareStatement(fbUrlNbr);				
			PreparedStatement fbUrlNbr9 = conn9.prepareStatement(fbUrlNbr);				
			PreparedStatement fbUrlNbr10 = conn10.prepareStatement(fbUrlNbr);
				
			fbuNumberStack = new Stack();
			fbuNumberStack.push(fbUrlNbr1);
			fbuNumberStack.push(fbUrlNbr2);
			fbuNumberStack.push(fbUrlNbr3);
			fbuNumberStack.push(fbUrlNbr4);
			fbuNumberStack.push(fbUrlNbr5);
			fbuNumberStack.push(fbUrlNbr6);
			fbuNumberStack.push(fbUrlNbr7);
			fbuNumberStack.push(fbUrlNbr8);
			fbuNumberStack.push(fbUrlNbr9);
			fbuNumberStack.push(fbUrlNbr10);
			

			// Find By Major Number.
			String fbMajNbr =
			"SELECT * FROM " + library + "GNPVMENU " +
			" WHERE GNVMAJNBR = ?" +
			" AND GNVTYPE='major' ";
			
			PreparedStatement fbMajNbr1 = conn10.prepareStatement(fbMajNbr);				
			PreparedStatement fbMajNbr2 = conn9.prepareStatement(fbMajNbr);			
			PreparedStatement fbMajNbr3 = conn8.prepareStatement(fbMajNbr);				
			PreparedStatement fbMajNbr4 = conn7.prepareStatement(fbMajNbr);				
			PreparedStatement fbMajNbr5 = conn6.prepareStatement(fbMajNbr);				
			PreparedStatement fbMajNbr6 = conn5.prepareStatement(fbMajNbr);				
			PreparedStatement fbMajNbr7 = conn4.prepareStatement(fbMajNbr);				
			PreparedStatement fbMajNbr8 = conn3.prepareStatement(fbMajNbr);				
			PreparedStatement fbMajNbr9 = conn2.prepareStatement(fbMajNbr);				
			PreparedStatement fbMajNbr10 = conn1.prepareStatement(fbMajNbr);				

			fbMajNumberStack = new Stack();
			fbMajNumberStack.push(fbMajNbr1);
			fbMajNumberStack.push(fbMajNbr2);
			fbMajNumberStack.push(fbMajNbr3);
			fbMajNumberStack.push(fbMajNbr4);
			fbMajNumberStack.push(fbMajNbr5);
			fbMajNumberStack.push(fbMajNbr6);
			fbMajNumberStack.push(fbMajNbr7);
			fbMajNumberStack.push(fbMajNbr8);
			fbMajNumberStack.push(fbMajNbr9);
			fbMajNumberStack.push(fbMajNbr10);


			//Find By Minor Number.
			String fbMinNbr = 
				"SELECT * FROM " + library + "GNPVMENU " +
				" WHERE GNVMINNBR = ?" +
				" AND GNVTYPE='minor' ";
			PreparedStatement fbMinNbr1 = conn1.prepareStatement(fbMinNbr);				
			PreparedStatement fbMinNbr2 = conn2.prepareStatement(fbMinNbr);				
			PreparedStatement fbMinNbr3 = conn3.prepareStatement(fbMinNbr);				
			PreparedStatement fbMinNbr4 = conn4.prepareStatement(fbMinNbr);				
			PreparedStatement fbMinNbr5 = conn5.prepareStatement(fbMinNbr);			
			PreparedStatement fbMinNbr6 = conn6.prepareStatement(fbMinNbr);				
			PreparedStatement fbMinNbr7 = conn7.prepareStatement(fbMinNbr);				
			PreparedStatement fbMinNbr8 = conn8.prepareStatement(fbMinNbr);				
			PreparedStatement fbMinNbr9 = conn9.prepareStatement(fbMinNbr);				
			PreparedStatement fbMinNbr10 = conn10.prepareStatement(fbMinNbr);			

			fbMinNumberStack = new Stack();
			fbMinNumberStack.push(fbMinNbr1);
			fbMinNumberStack.push(fbMinNbr2);
			fbMinNumberStack.push(fbMinNbr3);
			fbMinNumberStack.push(fbMinNbr4);
			fbMinNumberStack.push(fbMinNbr5);
			fbMinNumberStack.push(fbMinNbr6);
			fbMinNumberStack.push(fbMinNbr7);
			fbMinNumberStack.push(fbMinNbr8);
			fbMinNumberStack.push(fbMinNbr9);
			fbMinNumberStack.push(fbMinNbr10);
			

			//Find Like Url Address.
			String flUrl =
				"SELECT * FROM " + library + "GNPVMENU " +
				" WHERE GNVURL LIKE ? ";
				
			PreparedStatement flUrl1 = conn10.prepareStatement(flUrl);	
			PreparedStatement flUrl2 = conn9.prepareStatement(flUrl);			
			PreparedStatement flUrl3 = conn8.prepareStatement(flUrl);				
			PreparedStatement flUrl4 = conn7.prepareStatement(flUrl);				
			PreparedStatement flUrl5 = conn6.prepareStatement(flUrl);				
			PreparedStatement flUrl6 = conn5.prepareStatement(flUrl);				
			PreparedStatement flUrl7 = conn4.prepareStatement(flUrl);				
			PreparedStatement flUrl8 = conn3.prepareStatement(flUrl);				
			PreparedStatement flUrl9 = conn2.prepareStatement(flUrl);				
			PreparedStatement flUrl10 = conn1.prepareStatement(flUrl);
				

			fluAddressStack = new Stack();
			fluAddressStack.push(flUrl1);
			fluAddressStack.push(flUrl2);
			fluAddressStack.push(flUrl3);
			fluAddressStack.push(flUrl4);
			fluAddressStack.push(flUrl5);
			fluAddressStack.push(flUrl6);
			fluAddressStack.push(flUrl7);
			fluAddressStack.push(flUrl8);
			fluAddressStack.push(flUrl9);
			fluAddressStack.push(flUrl10);


			//Find By Url Address.
			String fbUrl =
				"SELECT * FROM " + library + "GNPVMENU " +
				" WHERE GNVURL = ?";
			PreparedStatement fbUrl1 = conn1.prepareStatement(fbUrl);				
			PreparedStatement fbUrl2 = conn2.prepareStatement(fbUrl);				
			PreparedStatement fbUrl3 = conn3.prepareStatement(fbUrl);				
			PreparedStatement fbUrl4 = conn4.prepareStatement(fbUrl);				
			PreparedStatement fbUrl5 = conn5.prepareStatement(fbUrl);				
			PreparedStatement fbUrl6 = conn6.prepareStatement(fbUrl);				
			PreparedStatement fbUrl7 = conn7.prepareStatement(fbUrl);				
			PreparedStatement fbUrl8 = conn8.prepareStatement(fbUrl);				
			PreparedStatement fbUrl9 = conn9.prepareStatement(fbUrl);				
			PreparedStatement fbUrl10 = conn10.prepareStatement(fbUrl);				

			fbuAddressStack = new Stack();
			fbuAddressStack.push(fbUrl1);
			fbuAddressStack.push(fbUrl2);
			fbuAddressStack.push(fbUrl3);
			fbuAddressStack.push(fbUrl4);
			fbuAddressStack.push(fbUrl5);
			fbuAddressStack.push(fbUrl6);
			fbuAddressStack.push(fbUrl7);
			fbuAddressStack.push(fbUrl8);
			fbuAddressStack.push(fbUrl9);
			fbuAddressStack.push(fbUrl10);


			// Delete
			String dltByUrlNbr =
				"DELETE FROM " + library + "GNPVMENU " +
				" WHERE GNVURLNBR = ?";
			PreparedStatement dltByUrlNbr1 = conn10.prepareStatement(dltByUrlNbr);				
			PreparedStatement dltByUrlNbr2 = conn9.prepareStatement(dltByUrlNbr);				
			PreparedStatement dltByUrlNbr3 = conn8.prepareStatement(dltByUrlNbr);				
			PreparedStatement dltByUrlNbr4 = conn7.prepareStatement(dltByUrlNbr);				
			PreparedStatement dltByUrlNbr5 = conn6.prepareStatement(dltByUrlNbr);				
			PreparedStatement dltByUrlNbr6 = conn5.prepareStatement(dltByUrlNbr);				
			PreparedStatement dltByUrlNbr7 = conn4.prepareStatement(dltByUrlNbr);				
			PreparedStatement dltByUrlNbr8 = conn3.prepareStatement(dltByUrlNbr);				
			PreparedStatement dltByUrlNbr9 = conn2.prepareStatement(dltByUrlNbr);				
			PreparedStatement dltByUrlNbr10 = conn1.prepareStatement(dltByUrlNbr);				

			dltSqlStack = new Stack();
			dltSqlStack.push(dltByUrlNbr1);
			dltSqlStack.push(dltByUrlNbr2);
			dltSqlStack.push(dltByUrlNbr3);
			dltSqlStack.push(dltByUrlNbr4);
			dltSqlStack.push(dltByUrlNbr5);
			dltSqlStack.push(dltByUrlNbr6);
			dltSqlStack.push(dltByUrlNbr7);
			dltSqlStack.push(dltByUrlNbr8);
			dltSqlStack.push(dltByUrlNbr9);
			dltSqlStack.push(dltByUrlNbr10);


			// Add.
			String addUrl =
				"INSERT INTO " + library + "GNPVMENU " +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
						 "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
						 "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
						 "?, ?, ?, ?, ?, ?, ?, ? )";
			PreparedStatement addUrl1 = conn1.prepareStatement(addUrl);				
			PreparedStatement addUrl2 = conn2.prepareStatement(addUrl);				
			PreparedStatement addUrl3 = conn3.prepareStatement(addUrl);				
			PreparedStatement addUrl4 = conn4.prepareStatement(addUrl);				
			PreparedStatement addUrl5 = conn5.prepareStatement(addUrl);				
			PreparedStatement addUrl6 = conn6.prepareStatement(addUrl);			
			PreparedStatement addUrl7 = conn7.prepareStatement(addUrl);				
			PreparedStatement addUrl8 = conn8.prepareStatement(addUrl);				
			PreparedStatement addUrl9 = conn9.prepareStatement(addUrl);				
			PreparedStatement addUrl10 = conn10.prepareStatement(addUrl);				

			addSqlStack = new Stack();
			addSqlStack.push(addUrl1);
			addSqlStack.push(addUrl2);
			addSqlStack.push(addUrl3);
			addSqlStack.push(addUrl4);
			addSqlStack.push(addUrl5);
			addSqlStack.push(addUrl6);
			addSqlStack.push(addUrl7);
			addSqlStack.push(addUrl8);
			addSqlStack.push(addUrl9);
			addSqlStack.push(addUrl10);


			// Update.
			String updUrlByNbr = 
				"UPDATE " + library + "GNPVMENU " +
				" SET GNVMAJNBR  = ?, GNVMINNBR  = ?, GNVURL     = ?, GNVURLNBR  = ?, " +
					" GNVNEWSTAT = ?, GNVNEWCMMT = ?, GNVKEYSRCH = ?, GNVLNKDSC  = ?, " +
					" GNVORDERBY = ?, GNVAUTHLVL = ?, GNVDSPMAIN = ?, GNVEXTRA   = ?, " +
					" GNVTITLE   = ?, GNVTYPE    = ?, GNVPATH    = ?, GNVNAME    = ?, " +
					" GNVCRTUSER = ?, GNVCRTDATE = ?, GNVCRTTIME = ?, GNVUPDUSER = ?, " +
					" GNVUPDDATE = ?, GNVUPDTIME = ?, GNVPATHSEC = ?, GNVSECSYS1 = ?, " +
					" GNVSECREQ1 = ?, GNVSECSYS2 = ?, GNVSECREQ2 = ?, GNVSECSYS3 = ?, " +
					" GNVSECREQ3 = ?, GNVSECSYS4 = ?, GNVSECREQ4 = ?, GNVSECSYS5 = ?, " +
					" GNVSECREQ5 = ?, GNVSECVAL1 = ?, GNVSECVAL2 = ?, GNVSECVAL3 = ?, " +
					" GNVSECVAL4 = ?, GNVSECVAL5 = ? " +
				" WHERE GNVURLNBR = ?";
			PreparedStatement updUrlByNbr1 = conn10.prepareStatement(updUrlByNbr);				
			PreparedStatement updUrlByNbr2 = conn9.prepareStatement(updUrlByNbr);				
			PreparedStatement updUrlByNbr3 = conn8.prepareStatement(updUrlByNbr);				
			PreparedStatement updUrlByNbr4 = conn7.prepareStatement(updUrlByNbr);				
			PreparedStatement updUrlByNbr5 = conn6.prepareStatement(updUrlByNbr);				
			PreparedStatement updUrlByNbr6 = conn5.prepareStatement(updUrlByNbr);				
			PreparedStatement updUrlByNbr7 = conn4.prepareStatement(updUrlByNbr);			
			PreparedStatement updUrlByNbr8 = conn3.prepareStatement(updUrlByNbr);				
			PreparedStatement updUrlByNbr9 = conn2.prepareStatement(updUrlByNbr);				
			PreparedStatement updUrlByNbr10 = conn1.prepareStatement(updUrlByNbr);				

			updSqlStack = new Stack();
			updSqlStack.push(updUrlByNbr1);
			updSqlStack.push(updUrlByNbr2);
			updSqlStack.push(updUrlByNbr3);
			updSqlStack.push(updUrlByNbr4);
			updSqlStack.push(updUrlByNbr5);
			updSqlStack.push(updUrlByNbr6);
			updSqlStack.push(updUrlByNbr7);
			updSqlStack.push(updUrlByNbr8);
			updSqlStack.push(updUrlByNbr9);
			updSqlStack.push(updUrlByNbr10);


			// Add to file Dppmurluse.
			String addDppmurluse = 
				"INSERT INTO " + library + "DPPMURLUSE " +
				" VALUES (?, ?, ?)";
			PreparedStatement addDppmurluse1 = conn1.prepareStatement(addDppmurluse);				
			PreparedStatement addDppmurluse2 = conn2.prepareStatement(addDppmurluse);				
			PreparedStatement addDppmurluse3 = conn3.prepareStatement(addDppmurluse);				
			PreparedStatement addDppmurluse4 = conn4.prepareStatement(addDppmurluse);				
			PreparedStatement addDppmurluse5 = conn5.prepareStatement(addDppmurluse);				
			PreparedStatement addDppmurluse6 = conn6.prepareStatement(addDppmurluse);				
			PreparedStatement addDppmurluse7 = conn7.prepareStatement(addDppmurluse);				
			PreparedStatement addDppmurluse8 = conn8.prepareStatement(addDppmurluse);				
			PreparedStatement addDppmurluse9 = conn9.prepareStatement(addDppmurluse);				
			PreparedStatement addDppmurluse10 = conn10.prepareStatement(addDppmurluse);				

			addDppmurluseStack = new Stack();
			addDppmurluseStack.push(addDppmurluse1);
			addDppmurluseStack.push(addDppmurluse2);
			addDppmurluseStack.push(addDppmurluse3);
			addDppmurluseStack.push(addDppmurluse4);
			addDppmurluseStack.push(addDppmurluse5);
			addDppmurluseStack.push(addDppmurluse6);
			addDppmurluseStack.push(addDppmurluse7);
			addDppmurluseStack.push(addDppmurluse8);
			addDppmurluseStack.push(addDppmurluse9);
			addDppmurluseStack.push(addDppmurluse10);


			// Delete from file Dppmurluse.
			String dltDppmurluse =
				"DELETE FROM " + library + "DPPMURLUSE " +
				" WHERE DPMURLNBR = ? AND DPMRECTYPE = ? ";
			PreparedStatement dltDppmurluse1 = conn10.prepareStatement(dltDppmurluse);				
			PreparedStatement dltDppmurluse2 = conn9.prepareStatement(dltDppmurluse);				
			PreparedStatement dltDppmurluse3 = conn8.prepareStatement(dltDppmurluse);				
			PreparedStatement dltDppmurluse4 = conn7.prepareStatement(dltDppmurluse);				
			PreparedStatement dltDppmurluse5 = conn6.prepareStatement(dltDppmurluse);				
			PreparedStatement dltDppmurluse6 = conn5.prepareStatement(dltDppmurluse);				
			PreparedStatement dltDppmurluse7 = conn4.prepareStatement(dltDppmurluse);				
			PreparedStatement dltDppmurluse8 = conn3.prepareStatement(dltDppmurluse);				
			PreparedStatement dltDppmurluse9 = conn2.prepareStatement(dltDppmurluse);				
			PreparedStatement dltDppmurluse10 = conn1.prepareStatement(dltDppmurluse);				

			dltDppmurluseStack = new Stack();
			dltDppmurluseStack.push(dltDppmurluse1);
			dltDppmurluseStack.push(dltDppmurluse2);
			dltDppmurluseStack.push(dltDppmurluse3);
			dltDppmurluseStack.push(dltDppmurluse4);
			dltDppmurluseStack.push(dltDppmurluse5);
			dltDppmurluseStack.push(dltDppmurluse6);
			dltDppmurluseStack.push(dltDppmurluse7);
			dltDppmurluseStack.push(dltDppmurluse8);
			dltDppmurluseStack.push(dltDppmurluse9);
			dltDppmurluseStack.push(dltDppmurluse10);		

		} catch (SQLException e) {
			System.out.println("SQL exception occured at com.treetop.data." +
							   "UrlFile.init()" +  e);	              
		}
		finally
		{
			try
			{
				ConnectionStack.returnConnection(conn1);
				ConnectionStack.returnConnection(conn2);
				ConnectionStack.returnConnection(conn3);
				ConnectionStack.returnConnection(conn4);
				ConnectionStack.returnConnection(conn5);
				ConnectionStack.returnConnection(conn6);
				ConnectionStack.returnConnection(conn7);
				ConnectionStack.returnConnection(conn8);
				ConnectionStack.returnConnection(conn9);
				ConnectionStack.returnConnection(conn10);
			}
			catch(Exception e)
			{}
		}
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
protected void loadFields(ResultSet rs, String requestType) {

	try {
		// Define database environment prior to every export (live or test).
		//library = "WKLIB."; // test environment
		library = "TREENET."; // live environment
		
		majorNumber       = new Integer(rs.getInt("GNVMAJNBR"));
		minorNumber       = new Integer(rs.getInt("GNVMINNBR"));
		urlAddress        = rs.getString("GNVURL");
		urlNumber         = new Integer(rs.getInt("GNVURLNBR"));
		newStatus         = rs.getString("GNVNEWSTAT");
		newComment        = rs.getString("GNVNEWCMMT");
		searchValues      = rs.getString("GNVKEYSRCH");
		urlDescription    = rs.getString("GNVLNKDSC");
		orderBy           = new Integer(rs.getInt("GNVORDERBY"));
		authorityLevel    = new Integer(rs.getInt("GNVAUTHLVL"));
		displayOnMain     =	rs.getString("GNVDSPMAIN");
		extra			  = rs.getString("GNVEXTRA");
		title             = rs.getString("GNVTITLE");
		type              = rs.getString("GNVTYPE");
		path              = rs.getString("GNVPATH");
		name              = rs.getString("GNVNAME");
		createUser        = rs.getString("GNVCRTUSER");
		createDate        = rs.getDate("GNVCRTDATE");
		createTime        = rs.getTime("GNVCRTTIME");
		updateUser        = rs.getString("GNVUPDUSER");
		updateDate        = rs.getDate("GNVUPDDATE");
		updateTime        = rs.getTime("GNVUPDTIME");
		pathSecurity      = rs.getString("GNVPATHSEC");
		securitySystem1   = rs.getString("GNVSECSYS1");
		securityRequired1 = rs.getString("GNVSECREQ1");
		securitySystem2   = rs.getString("GNVSECSYS2");
		securityRequired2 = rs.getString("GNVSECREQ2");
		securitySystem3   = rs.getString("GNVSECSYS3");
		securityRequired3 = rs.getString("GNVSECREQ3");
		securitySystem4   = rs.getString("GNVSECSYS4");
		securityRequired4 = rs.getString("GNVSECREQ4");
		securitySystem5   = rs.getString("GNVSECSYS5");
		securityRequired5 = rs.getString("GNVSECREQ5");
		securityValue1	  = rs.getString("GNVSECVAL1");
		securityValue2	  = rs.getString("GNVSECVAL2");	
		securityValue3	  = rs.getString("GNVSECVAL3");	
		securityValue4	  = rs.getString("GNVSECVAL4");	
		securityValue5	  = rs.getString("GNVSECVAL5");																	
		canIUseIt         = "";
		canIChangeIt      = "";
		urlSplitPath      = "";
		
		if (requestType.equals("ListPage") || requestType.equals("ListDocs"))
		{
			newDate       = rs.getDate("NEWDATE");
			revisedDate   = rs.getDate("REVDATE");
		}
				
		if (pathSecurity.equals("Y"))
		{
			urlSplitPath  = buildSplitPath(path,
										   title,
										   urlAddress,
										   type);
		}

	}
	catch (Exception e)
	{

		System.out.println("SQL Exception at com.treetop.data.UrlFile.loadFields();" + e);
	}

	persists = true;
				
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public static void main(String[] args) {

	// add a few urls.

	//java.sql.Date theDate = null;

	java.sql.Date theDate = java.sql.Date.valueOf("2003-01-17");
	java.sql.Time theTime = java.sql.Time.valueOf("12:13:14"); 
	
	try {
		UrlFile one = addUrlFile(1, 7, 
								 "../WhoCares/WhereWe/GoFromHere/one.jsp",
			                     999991, "N", "This is test record one",
			                     "These are search values",
			                     "This is the link description",
			                     5, 91, "N", "extra","one Link Header", 
			                     "type", "path","name", "CrtUser",
			                     theDate, theTime, "UpdUser", theDate, theTime,
			                     "Y", "", "", "", "", "", "", "", "", "", "",
			                     "",  "", "", "", "");
		System.out.println("one: " + one);
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.UrlFile.main()");
	}
	
	try {
		UrlFile two = addUrlFile(1, 7,  
								 "../WhoCares/WhereWe/GoFromHere/two.jsp",
			                     999992, "N", "This is test record two", 
			                     "These are search values",
			                     "This is the link description",
			                     5, 91, "N", "extra","two Link Header",
			                     "type", "path","name", "CrtUser",
			                     theDate, theTime, "UpdUser", theDate, theTime,
			                     "Y", "", "", "", "", "", "", "", "", "", "",
			                     "",  "", "", "", "");
		System.out.println("two: " + two);
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.UrlFile.main()");
	}
	
	try {
		UrlFile three = addUrlFile(1, 7,  
								 "../WhoCares/WhereWe/GoFromHere/three.jsp",
			                     999993, "N", "This is test record three", 			                     "These are search values",
			                     "This is the link description",
			                     5, 91, "N", "extra","three Link Header",
			                     "type", "path","name", "CrtUser",
			                     theDate, theTime, "UpdUser", theDate, theTime,
			                     "Y", "", "", "", "", "", "", "", "", "", "",
			                     "",  "", "", "", "");
		System.out.println("three: " + three);
	} catch (Exception e) {
		System.out.println("error at com.treetop.data.UrlFile.main()");
	}
	
	// find by url number.
	Integer urlNbr = new Integer(999992);
	try {
		UrlFile two = new UrlFile(urlNbr);
		System.out.println("find two: " + two);

		try{
			two.setTitle("changed two title");
		} catch (Exception e) {
			System.out.println("Main - problem with link header change: " + e);
		}

		// update
		two.update();

		//delete
		two.delete();
	} catch (Exception e) {
		System.out.println("find/delete in main" + e);
	}


	// delete one and three.
	try {
		Integer urlNbr1 = new Integer(999991);
		UrlFile one = new UrlFile(urlNbr1);
		one.delete();
	} catch (Exception e) {
		System.out.println("delete problem with one: " + e);
	}

	try {
		Integer urlNbr3 = new Integer(999993);
		UrlFile three = new UrlFile(urlNbr3);
		three.delete();
	} catch (Exception e) {
		System.out.println("delete problem with three: " + e);
	}
		

	// find a url that dosent exist.
	Integer urlNbr2 = new Integer(999990);
	try {
		UrlFile notThereUrlFile = new UrlFile(urlNbr2);
		System.out.println("notThereUrlFile: " + notThereUrlFile);
	} catch (InstantiationException ie) {
		System.out.println("file not there: " + ie);
	}

	// find all by url.
	urlNbr = new Integer(999993);
	Vector urlFiles = UrlFile.findByUrlNumber(urlNbr);

	for (int i = 0; i < urlFiles.size(); i++)
	{
		UrlFile listOne = (UrlFile)urlFiles.elementAt(i);
		System.out.println("lookie here:" + listOne);
	}


	try {
    Integer z = new Integer(1);
	UrlFile one = new UrlFile("major", z);
	System.out.println("Major done");
	} catch (Exception e) {
		System.out.println("delete problem with three: " + e);
	}

}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void newMethod() {}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public static int nextUrlNumber() 
{
//	10/28/11 TWalton -- Not used, 10.6.100.1 points to a machine that has not been valid for 3+ years
//	try {
		// create a AS400 object
//		AS400 as400 = new AS400("10.6.100.1", "DAUSER", "web230502");
//		ProgramCall pgm = new ProgramCall(as400);

//		ProgramParameter[] parmList = new ProgramParameter[1];
//		parmList[0] = new ProgramParameter(100);
//		pgm = new ProgramCall(as400, "/QSYS.LIB/GNLIB.LIB/CLURLNBR.PGM", parmList);

//		if (pgm.run() != true)
//		{
//			return 0;
//		} else {
//			AS400PackedDecimal pd = new AS400PackedDecimal(9, 0);
//			byte[] data = parmList[0].getOutputData();
//			double dd = pd.toDouble(data, 0);
//			int i = (int) dd;
//			as400.disconnectService(AS400.COMMAND);
//			return i;
//		}

//	} catch (Exception e) {
		return 0;
//	}

}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setAuthorityLevel(int authorityLevelIn) {

	this.authorityLevel =  new Integer(authorityLevelIn);
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setCanIChangeIt(String canIChangeItIn) throws InvalidLengthException {

	if (canIChangeItIn.length() > 1)
		throw new InvalidLengthException(
				"canIChangeItIn", canIChangeItIn.length(), 1);

	this.canIChangeIt =  canIChangeItIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setCanIUseIt(String canIUseItIn) throws InvalidLengthException {

	if (canIUseItIn.length() > 1)
		throw new InvalidLengthException(
				"canIUseItIn", canIUseItIn.length(), 1);

	this.canIUseIt =  canIUseItIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setCreateDate(java.sql.Date createDateIn)  {

	this.createDate =  createDateIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setCreateTime(java.sql.Time createTimeIn)  {

	this.createTime =  createTimeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setCreateUser(String createUserIn) throws InvalidLengthException {

	if (createUserIn.length() > 10)
		throw new InvalidLengthException(
				"createUserIn", createUserIn.length(), 10);

	this.createUser =  createUserIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setDisplayOnMain(String displayOnMainIn) throws InvalidLengthException {

	if (displayOnMainIn.length() > 1)
		throw new InvalidLengthException(
				"displayOnMainIn", displayOnMainIn.length(), 1);

	this.displayOnMain =  displayOnMainIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setExtra(String extraIn) throws InvalidLengthException {

	if (extraIn.length() > 10)
		throw new InvalidLengthException(
				"extraIn", extraIn.length(), 10);

	this.extra =  extraIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setMajorNumber(int majorNumberIn) throws Exception {

	if (majorNumberIn < 1 || majorNumberIn > 100)
		throw new Exception(
				"majorNumber received is not valid");

	this.majorNumber =  new Integer(majorNumberIn);
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setMinorNumber(int minorNumberIn) throws Exception {

	if (minorNumberIn < 0 || minorNumberIn > 200)
		throw new Exception(
				"minorNumber received is not valid");

	this.minorNumber =  new Integer(minorNumberIn);
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setName(String nameIn) throws InvalidLengthException {

	if (nameIn.length() > 340)
		throw new InvalidLengthException(
				"nameIn", nameIn.length(), 340);

	this.name =  nameIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setNewComment(String newCommentIn) throws InvalidLengthException {

	if (newCommentIn.length() > 340)
		throw new InvalidLengthException(
				"newCommentIn", newCommentIn.length(), 340);

	this.newComment =  newCommentIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setNewDate(java.sql.Date newDateIn)  {

	this.newDate =  newDateIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setNewStatus(String newStatusIn) throws InvalidLengthException {

	if (newStatusIn.length() > 5)
		throw new InvalidLengthException(
				"newStatusIn", newStatusIn.length(), 5);

	this.newStatus =  newStatusIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setOrderBy(int orderByIn) {

	this.orderBy =  new Integer(orderByIn);
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setPath(String pathIn) throws InvalidLengthException {

	if (pathIn.length() > 340)
		throw new InvalidLengthException(
				"pathIn", pathIn.length(), 340);

	this.path =  pathIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setPathSecurity(String pathSecurityIn) throws InvalidLengthException {

	if (pathSecurityIn.length() > 1)
		throw new InvalidLengthException(
				"pathSecurityIn", pathSecurityIn.length(), 1);

	this.pathSecurity =  pathSecurityIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setRevisedDate(java.sql.Date revisedDateIn)  {

	this.revisedDate =  revisedDateIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setSearchValues(String searchValuesIn) throws InvalidLengthException {

	if (searchValuesIn.length() > 340)
		throw new InvalidLengthException(
				"searchValuesIn", searchValuesIn.length(), 340);

	this.searchValues =  searchValuesIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setTitle(String titleIn) throws InvalidLengthException {

	if (titleIn.length() > 52)
		throw new InvalidLengthException(
				"titleIn", titleIn.length(), 52);

	this.title =  titleIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setType(String typeIn) throws InvalidLengthException {

	if (typeIn.length() > 10)
		throw new InvalidLengthException(
				"typeIn", typeIn.length(), 10);

	this.type =  typeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public void setSecurityRequired1(String securityRequired1In) throws InvalidLengthException {

	if (securityRequired1In.length() > 1)
		throw new InvalidLengthException(
				"securityRequired1In", securityRequired1In.length(), 1);

	this.securityRequired1 =  securityRequired1In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public void setSecurityRequired2(String securityRequired2In) throws InvalidLengthException {

	if (securityRequired2In.length() > 1)
		throw new InvalidLengthException(
				"securityRequired2In", securityRequired2In.length(), 1);

	this.securityRequired2 =  securityRequired2In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public void setSecurityRequired3(String securityRequired3In) throws InvalidLengthException {

	if (securityRequired3In.length() > 1)
		throw new InvalidLengthException(
				"securityRequired3In", securityRequired3In.length(), 1);

	this.securityRequired3 =  securityRequired3In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public void setSecurityRequired4(String securityRequired4In) throws InvalidLengthException {

	if (securityRequired4In.length() > 1)
		throw new InvalidLengthException(
				"securityRequired4In", securityRequired4In.length(), 1);

	this.securityRequired4 =  securityRequired4In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public void setSecurityRequired5(String securityRequired5In) throws InvalidLengthException {

	if (securityRequired5In.length() > 1)
		throw new InvalidLengthException(
				"securityRequired5In", securityRequired5In.length(), 1);

	this.securityRequired5 =  securityRequired5In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public void setSecuritySystem1(String securitySystem1In) throws InvalidLengthException {

	if (securitySystem1In.length() > 2)
		throw new InvalidLengthException(
				"securitySystem1In", securitySystem1In.length(), 2);

	this.securitySystem1 =  securitySystem1In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public void setSecuritySystem2(String securitySystem2In) throws InvalidLengthException {

	if (securitySystem2In.length() > 2)
		throw new InvalidLengthException(
				"securitySystem2In", securitySystem2In.length(), 2);

	this.securitySystem2 =  securitySystem2In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public void setSecuritySystem3(String securitySystem3In) throws InvalidLengthException {

	if (securitySystem3In.length() > 2)
		throw new InvalidLengthException(
				"securitySystem3In", securitySystem3In.length(), 2);

	this.securitySystem3 =  securitySystem3In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public void setSecuritySystem4(String securitySystem4In) throws InvalidLengthException {

	if (securitySystem4In.length() > 2)
		throw new InvalidLengthException(
				"securitySystem4In", securitySystem4In.length(), 2);

	this.securitySystem4 =  securitySystem4In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/11/2004 10:36:39 AM)
 */
public void setSecuritySystem5(String securitySystem5In) throws InvalidLengthException {

	if (securitySystem5In.length() > 2)
		throw new InvalidLengthException(
				"securitySystem5In", securitySystem5In.length(), 2);

	this.securitySystem5 =  securitySystem5In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/23/2004 10:36:39 AM)
 */
public void setSecurityValue1(String securityValue1In) throws InvalidLengthException {

	if (securityValue1In.length() > 30)
		throw new InvalidLengthException(
				"securityValue1In", securityValue1In.length(), 30);

	this.securityValue1 =  securityValue1In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/23/2004 10:36:39 AM)
 */
public void setSecurityValue2(String securityValue2In) throws InvalidLengthException {

	if (securityValue2In.length() > 30)
		throw new InvalidLengthException(
				"securityValue2In", securityValue2In.length(), 30);

	this.securityValue2 =  securityValue2In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/23/2004 10:36:39 AM)
 */
public void setSecurityValue3(String securityValue3In) throws InvalidLengthException {

	if (securityValue3In.length() > 30)
		throw new InvalidLengthException(
				"securityValue3In", securityValue3In.length(), 30);

	this.securityValue3 =  securityValue3In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/23/2004 10:36:39 AM)
 */
public void setSecurityValue4(String securityValue4In) throws InvalidLengthException {

	if (securityValue4In.length() > 30)
		throw new InvalidLengthException(
				"securityValue4In", securityValue4In.length(), 30);

	this.securityValue4 =  securityValue4In;
}
/**
 * Insert the method's description here.
 * Creation date: (8/23/2004 10:36:39 AM)
 */
public void setSecurityValue5(String securityValue5In) throws InvalidLengthException {

	if (securityValue5In.length() > 30)
		throw new InvalidLengthException(
				"securityValue5In", securityValue5In.length(), 30);

	this.securityValue5 =  securityValue5In;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setUpdateDate(java.sql.Date updateDateIn)  {

	this.updateDate =  updateDateIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setUpdateTime(java.sql.Time updateTimeIn)  {

	this.updateTime =  updateTimeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setUpdateUser(String updateUserIn) throws InvalidLengthException {

	if (updateUserIn.length() > 10)
		throw new InvalidLengthException(
				"updateUserIn", updateUserIn.length(), 10);

	this.updateUser =  updateUserIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setUrlAddress(String urlAddressIn) throws InvalidLengthException {

	if (urlAddressIn.length() > 340)
		throw new InvalidLengthException(
				"urlAddressIn", urlAddressIn.length(), 340);

	this.urlAddress =  urlAddressIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setUrlDescription(String urlDescriptionIn) throws InvalidLengthException {

	if (urlDescriptionIn.length() > 502)
		throw new InvalidLengthException(
				"urlDescriptionIn", urlDescriptionIn.length(), 502);

	this.urlDescription =  urlDescriptionIn;
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void setUrlNumber(int urlNumberIn) throws Exception {

	if (urlNumberIn < 1 || urlNumberIn > 999999998)
		throw new Exception(
				"urlNumber received is not valid");

	this.urlNumber =  new Integer(urlNumberIn);
}
/**
 * Insert the method's description here.
 * Creation date: (2/21/2003 10:36:39 AM)
 */
public void setUrlSplitPath(String urlSplitPathIn)
{
	this.urlSplitPath =  urlSplitPathIn;
}
/**
 * This is used via the class main method for testing purposes.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public String toString() {

	return new String(
		"majorNumber: " + majorNumber + "\n" +
		"minorNumber: " + minorNumber + "\n" +
		"urlAddress: " + urlAddress + "\n" +
		"urlNumber: " + urlNumber + "\n" +
		"newStatus: " + newStatus + "\n" +
		"newComment: " + newComment + "\n" +
		"searchValues: " + searchValues + "\n" +
		"linkDescription: " + urlDescription + "\n" +
		"orderBy: " + orderBy + "\n" +
		"authorityLevel: " + authorityLevel + "\n" +
		"displayOnMainMenu: " + displayOnMain + "\n" +
		"extra: " + extra + "\n" +
		"title: " + title + "\n" +
		"type: " + type + "\n" +
		"path: " + path + "\n" +
		"name: " + name + "\n" +
		"createUser: " + createUser + "\n" +
		"createDate: " + createDate + "\n" +
		"createTime: " + createTime + "\n" +
		"updateUser: " + updateUser + "\n" +
		"updateDate: " + updateDate + "\n" +
		"updateTime: " + updateTime + "\n" +
		"pathSecurity: " + pathSecurity + "\n" +
		"securitySystem1:" + securitySystem1 + "\n" +
		"securityRequired1:" + securityRequired1 + "\n" +
		"securitySystem2:" + securitySystem2 + "\n" +
		"securityRequired2:" + securityRequired2 + "\n" +
		"securitySystem3:" + securitySystem3 + "\n" +
		"securityRequired3:" + securityRequired3 + "\n" +
		"securitySystem4:" + securitySystem4 + "\n" +
		"securityRequired4:" + securityRequired4 + "\n" +
		"securitySystem5:" + securitySystem5 + "\n" +
		"securityRequired5:" + securityRequired5 + "\n" +
		"securityValue1:" + securityValue1 + "\n" +
		"securityValue2:" + securityValue2 + "\n" +
		"securityValue3:" + securityValue3 + "\n" +
		"securityValue4:" + securityValue4 + "\n" +
		"securityValue5:" + securityValue5 + "\n" +		
	    "canIUseIt: " + canIUseIt + "\n" +
	    "canIChangeIt: " + canIChangeIt + "\n" +
	    "library: " + library + "\n");	
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public void update() {

	int y = 0;

	try {
		PreparedStatement updSql = (PreparedStatement) updSqlStack.pop();
		updSql.setInt(1, majorNumber.intValue());
		updSql.setInt(2, minorNumber.intValue());
		updSql.setString(3, urlAddress);
		updSql.setInt(4, urlNumber.intValue());
		updSql.setString(5, newStatus);					
		updSql.setString(6, newComment);
		updSql.setString(7, searchValues);
		updSql.setString(8, urlDescription);
		updSql.setInt(9, orderBy.intValue());
		updSql.setInt(10, authorityLevel.intValue());
		updSql.setString(11, displayOnMain);
		updSql.setString(12, extra);
		updSql.setString(13, title);
		updSql.setString(14, type);
		updSql.setString(15, path);
		updSql.setString(16, name);
		updSql.setString(17, createUser);
		updSql.setDate(18, createDate);
		updSql.setTime(19, createTime);
		updSql.setString(20, updateUser);
		updSql.setDate(21, updateDate);
		updSql.setTime(22, updateTime);
		updSql.setString(23, pathSecurity);
		updSql.setString(24, securitySystem1);
		updSql.setString(25, securityRequired1);
		updSql.setString(26, securitySystem2);
		updSql.setString(27, securityRequired2);
		updSql.setString(28, securitySystem3);
		updSql.setString(29, securityRequired3);
		updSql.setString(30, securitySystem4);
		updSql.setString(31, securityRequired4);
		updSql.setString(32, securitySystem5);
		updSql.setString(33, securityRequired5);
		updSql.setString(34, securityValue1);
		updSql.setString(35, securityValue2);
		updSql.setString(36, securityValue3);
		updSql.setString(37, securityValue4);
		updSql.setString(38, securityValue5);								
		updSql.setInt(39, urlNumber.intValue());
		updSql.executeUpdate();
		updSqlStack.push(updSql);
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.UrlFile.update(): " + e);
	}		
}
/**
 * Insert the method's description here.
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public static UrlFile updateAuthorityFields(UrlFile urlFile,
											String[] roles, // Numbers
											String[] groups, // Numbers
											String[] paths, // Paths
											String[] pubPaths, // Yes or No, can I Change It Associated to paths?
											String addFolder,
											String user) 
{
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs   = null;
	StringBuffer throwError = new StringBuffer();
	
	
	try { //activate finally
	
		// Define database environment prior to every export (live or test).
		//String library = "WKLIB."; // test environment
		String library = "TREENET."; // live environment
		String authPassed  = "no";
    
		//** Test if IS personnel
		int i = roles.length;
			
		if (i > 0)
		{
			for (int z = 0; z < i; z++)
			{
				if (roles[z].equals("8"))
				{
					urlFile.setCanIUseIt("Y");
					urlFile.setCanIChangeIt("Y");
					authPassed = "yes";
				}
			}
   	 	}
			
		//get a connection if authority not passed.
		if (authPassed.equals("no"))
			conn  = ConnectionStack.getConnection();
			
		//ship this if role is "8" (IS Personal).
		if (authPassed.equals("no"))
		{
			//*** Test Role, Group, User Authority
			//System.out.println("DPPNURLUSE - UrlFile - get updateAuthorityFields()");
			//********* Test Role *************// 
	                        
			String roleList = "= ' '";
			int x = roles.length;
				
			if (x > 0)
			{
				roleList = "IN (" + roles[0];
				
				for (int z = 1; z < x; z++)
				{
					roleList = roleList + "," + roles[z];
				}
				
				roleList = roleList + ") ";
			}
			
			StringBuffer sqlString = new StringBuffer();
			sqlString.append("SELECT * FROM " + library + "DPPMURLUSE ");
			sqlString.append("WHERE DPMRECTYPE = 'R' ");
	  	   	sqlString.append("AND DPMURLNBR = " + urlFile.getUrlNumber() + " ");
	    	sqlString.append("AND DPMSECNBR " + roleList + " ");

	    	try {
	     		stmt  = conn.createStatement();
	     		rs    = stmt.executeQuery(sqlString.toString());

	     		if (rs.next())
	     		{
	     			urlFile.setCanIUseIt("Y");
	     			urlFile.setCanIChangeIt("N");
	     			authPassed = "yes";
	     		}
	     		
	     		stmt.close();
	     		rs.close();
	     	} catch (Exception e) {
	     		throwError.append("Error checking role. ");
	     	}
		}
			
			
		//ship this if role is "8" (IS Personal) or role security found.
		if (authPassed.equals("no"))
		{
			//********* Test Group *************//
                       
			String groupList = "";
			int x = groups.length;
			if (x > 0 && !groups[0].equals(""))
			{
				groupList = "IN (" + groups[0];
				
				for (int z = 1; z < x; z++)
				{
					groupList = groupList + "," + groups[z];
				}
				
				groupList = groupList + ") ";
			
			   StringBuffer sqlString = new StringBuffer();
			   sqlString.append("SELECT * FROM " + library + "DPPMURLUSE ");
			   sqlString.append("WHERE DPMRECTYPE = 'G' "); 
	  		   sqlString.append("AND DPMURLNBR = " + urlFile.getUrlNumber() + " ");
	    	   sqlString.append("AND DPMSECNBR " + groupList + " ");
	    		
	     	   try {
	     		 stmt  = conn.createStatement();
	     		 rs    = stmt.executeQuery(sqlString.toString());
		 
	     		 if (rs.next())
	     		 {
	     			urlFile.setCanIUseIt("Y");
	     			urlFile.setCanIChangeIt("N");
	     			authPassed = "yes";
	     		 } 
	     		
	     		 stmt.close();
	     		 rs.close();
	     		
	     	   } catch (Exception e) {
	     		  throwError.append("Error checking group. ");
	     	   }
			}
		}
            
			
		//skip this if role is "8" (IS Personal), role,  or group security found.
		if (authPassed.equals("no"))
		{
			//********* Test User  *************//
			StringBuffer sqlString = new StringBuffer();
   	       	sqlString.append("SELECT * FROM " + library + "DPPMURLUSE ");
	        sqlString.append("INNER JOIN " + library + "DPPNUSER ON DPNUSERNBR = DPMSECNBR ");
	        sqlString.append("WHERE DPMRECTYPE = 'U' ");
	    	sqlString.append("AND DPMURLNBR = " + urlFile.getUrlNumber() + " "); 
	     	sqlString.append("AND DPNUSER = '" + user + "' ");
       
	     	stmt  = conn.createStatement();
	     	rs = stmt.executeQuery(sqlString.toString());
		 
	     	if (rs.next())
	     	{
	     		urlFile.setCanIUseIt("Y");
	     		urlFile.setCanIChangeIt("N");
	     		authPassed = "yes";
	     	}
	     		
	     	stmt.close();
	     	rs.close();
		}
	} catch (Exception e) {
		throwError.append("Problem in UrlFile.updateAuthorityFields. ");
	} finally {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				ConnectionStack.returnConnection(conn);
		} catch (Exception e) {
			throwError.append("Error closing resultset, statement, returning connection");
		}
		
		//log any errors.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.treetop.data.");
			throwError.append("UrlFile.updateAuthorityFields(");
			throwError.append("findLoad(");
			throwError.append("UrlFile,String[],String[],String[],String,String). ");
			System.out.println(throwError.toString());
			Exception x = new Exception();
			x.printStackTrace();
		}
	}
	
	return urlFile;
}
/**
 * Method to Load a String Array with information based on
 * parameters received.
 *   [0] for Requested Information (For use to display what was requested)
 *   [1] for Error Message
 * Creation date: (2/24/2003 1:53:01 PM)
 */
public String[] validateParameters(String type,
	                        String typevalue,
							String path)
{
	
	//****TESTING****
	//System.out.println("UrlFile.validateParameters");
	//System.out.println("Type = " + type);
	//System.out.println("TypeValue = " + typevalue);
	//System.out.println("Path =" + path + ":");
		
	String[] messages = new String[2];
	ResultSet getrs = null;
	String   whereFrom = "validate";
	
		if (type.equals("search"))
		{
			 messages[0] =  "You Searched On: " +
           			typevalue;
		}
		else
		{
			Integer sendValue = new Integer(typevalue);
  			if (type.equals("major"))
  		   {
	          try {
                  UrlFile MajorRecord = new UrlFile("major", sendValue);
				 // System.out.println("Major done");
				  messages[0] =  "Contents of: " +
            			MajorRecord.getTitle().trim();
		 	  
  			  } catch (Exception e) {
   				System.out.println("Problem when creating Class(Major) UrlFile.validateParameters" + e);
			  }
  		  }
		    if (type.equals("minor"))
			{  	
		 	   try {
                  UrlFile MinorRecord = new UrlFile("minor", sendValue);
				 // System.out.println("Minor done");
				  messages[0] =  "Contents of: " +
            			MinorRecord.getTitle().trim();
		 	  
  				  } catch (Exception e) {
   						System.out.println("Problem when creating Class(Minor) UrlFile.validateParameters" + e);
				  }
		    }
		}
		

	if (path != null)
	{	
		if (!path.equals("null") && !path.equals("..") && path.length() != 0)
		{			
			int pathLength = path.length();
	 		int findslash = path.lastIndexOf("/");
           	String newPath =  path.substring(0, findslash);
           	pathLength = newPath.length();
           	findslash = newPath.lastIndexOf("/");
           	newPath = newPath.substring((findslash + 1), pathLength);

          	messages[0] = "Contents of: " + newPath;
           	 
		}
	}
	
return messages;
}
}
