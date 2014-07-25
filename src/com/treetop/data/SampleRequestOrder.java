package com.treetop.data;

import java.text.*;
import java.sql.*;
import java.math.*;
import java.util.*;
import javax.sql.*;
import com.ibm.as400.access.*;
import com.treetop.*;
import com.treetop.utilities.ConnectionStack;

/**
 *   This class presents Sample Order Data. The Sample Order system is used
 * by TreeTop R&D technicians and sales reps to track special request orders
 * tieing together fomulas, shipping info, and customer feedback.
 *
 * 
 * Access to RDB files:  DBLIB/SRPBHEADER.
 *                       DBLIB/SRPCDETAIL.
 *                       DBLIB/SRPDCHECK.
 *                       DBLIB/SRPECOMENT.
 *                       DBLIB/SRPFREMARK.
 *         8/14/08 - Moved Files to NEW Box DBPRD Library
 *
 * Code used to generate the tables.
 *
 * CREATE TABLE DBLIB/SRPBHEADER (
 *  SRBNUMBER  INT           NOT NULL WITH DEFAULT, //sample order number
 *  SRBSTATUS  CHAR (20)     NOT NULL WITH DEFAULT, //status
 *  SRBTYPE    CHAR (20)     NOT NULL WITH DEFAULT, //type
 *  SRBAPP     CHAR (20)     NOT NULL WITH DEFAULT, //application
 *  SRBREP     CHAR (10)     NOT NULL WITH DEFAULT, //sales rep
 *  SRBTECH    CHAR (10)     NOT NULL WITH DEFAULT, //technician
 *  SRBMARKET  NUMERIC 3     NOT NULL WITH DEFAULT, //territory
 *  SRBRECDATE DATE          NOT NULL WITH DEFAULT, //received date
 *  SRBRECTIME TIME          NOT NULL WITH DEFAULT, //received time
 *  SRBDELDATE DATE          NOT NULL WITH DEFAULT, //delivery date
 *  SRBCUST    INT           NOT NULL WITH DEFAULT, //customer
 *  SRBCCONTCT CHAR (30)     NOT NULL WITH DEFAULT, //customer contact name
 *  SRBCPHONE  CHAR (30)     NOT NULL WITH DEFAULT, //customer contact phone#
 *  SRBCEMAIL  CHAR (100)    NOT NULL WITH DEFAULT, //customer contact email address
 *  SRBACONTCT CHAR (30)     NOT NULL WITH DEFAULT, //attention contact name
 *  SRBAPHONE  CHAR (30)     NOT NULL WITH DEFAULT, //attention contact phone#
 *  SRBAEMAIL  CHAR (100)    NOT NULL WITH DEFAULT, //attention contact email address
 *  SRBREQUEST CHAR (30)     NOT NULL WITH DEFAULT, //who requested 
 *  SRBRECREQ  CHAR (10)     NOT NULL WITH DEFAULT, //who received request
 *  SRBSHPCHG  CHAR (20)     NOT NULL WITH DEFAULT, //shipping charge type
 *  SRBSHIPVIA CHAR (20)     NOT NULL WITH DEFAULT, //ship via
 *  SRBSHIPHOW CHAR (20)     NOT NULL WITH DEFAULT, //ship how
 *  SRBTRACK   CHAR (30)     NOT NULL WITH DEFAULT, //tracking number
 *  SRBACCTNBR CHAR (36)     NOT NULL WITH DEFAULT, //gl account number
 *  SRBACCTMSC CHAR (10)     NOT NULL WITH DEFAULT, //gl account misc component
 *  SRBSHPDATE DATE          NOT NULL WITH DEFAULT, //shipping date
 *  SRBEMAIL1  CHAR (100)    NOT NULL WITH DEFAULT, //email when shipped 1
 *  SRBEMAIL2  CHAR (100)    NOT NULL WITH DEFAULT, //email when shipped 2
 *  SRBEMAIL3  CHAR (100)    NOT NULL WITH DEFAULT, //email when shipped 3
 *  SRBEMAIL4  CHAR (100)    NOT NULL WITH DEFAULT, //email when shipped 4
 *  SRBEMAIL5  CHAR (100)    NOT NULL WITH DEFAULT, //email when shipped 5
 *  SRBCRTDATE DATE          NOT NULL WITH DEFAULT, //create date
 *  SRBCRTTIME TIME          NOT NULL WITH DEFAULT, //create time
 *  SRBCRTUSER CHAR (10)     NOT NULL WITH DEFAULT, //create user
 *  SRBUPDDATE DATE          NOT NULL WITH DEFAULT, //update date
 *  SRBUPDTIME TIME          NOT NULL WITH DEFAULT, //update time
 *  SRBUPDUSER CHAR (10)     NOT NULL WITH DEFAULT, //update user
 *  SRBLOC     CHAR (2)      NOT NULL WITH DEFAULT) //ship from location.
 *  
 *
 * CREATE TABLE DBLIB/SRPDCHECK (
 *  SRDNUMBER  INT           NOT NULL WITH DEFAULT, //sample order number
 *  SRDDESCKEY CHAR (3)      NOT NULL WITH DEFAULT, //description code
 *  SRDKEYCODE CHAR (2)      NOT NULL WITH DEFAULT, //key code
 *  SRDKEYVAL  CHAR (2)      NOT NULL WITH DEFAULT, //check box value
 *  SRDDESC8   CHAR (8)      NOT NULL WITH DEFAULT, //8 long description
 *  SRDDESC20  CHAR (20)     NOT NULL WITH DEFAULT, //20 long description
 *  SRDSEQ     NUMERIC 3     NOT NULL WITH DEFAULT) //sequence number
 *
 *
 * CREATE TABLE DBLIB/SRPCDETAIL (
 *  SRCNUMBER  int           NOT NULL WITH DEFAULT, //sample order number
 *  SRCQTY     int           NOT NULL WITH DEFAULT, //quantity
 *  SRCCNTSIZE int           NOT NULL WITH DEFAULT, //container size
 *  SRCUOM     CHAR (8)      NOT NULL WITH DEFAULT, //unit of measure
 *  SRCPRDGRP  CHAR (2)      NOT NULL WITH DEFAULT, //product group
 *  SRCPRDTYPE CHAR (2)      NOT NULL WITH DEFAULT, //product type
 *  SRCCUTSIZE CHAR (2)      NOT NULL WITH DEFAULT, //cut size
 *  SRCCHEMADD CHAR (2)      NOT NULL WITH DEFAULT, //chemical additive
 *  SRCFRUTVAR CHAR (2)      NOT NULL WITH DEFAULT, //fruit variety
 *  SRCITMDESC CHAR (42)     NOT NULL WITH DEFAULT, //item description
 *  SRCADDDESC CHAR (30)     NOT NULL WITH DEFAULT, //additional description
 *  SRCPRESERV CHAR (20)     NOT NULL WITH DEFAULT, //preservative
 *  SRCRES     CHAR (15)     NOT NULL WITH DEFAULT, //resource
 *  SRCLOT     CHAR (15)     NOT NULL WITH DEFAULT, //lot number
 *  SRCFORMULA int           NOT NULL WITH DEFAULT, //fromula number
 *  SRCSPEC    CHAR (5)      NOT NULL WITH DEFAULT, //specification number
 *  SRCSEQ     NUMERIC 3,0   NOT NULL WITH DEFAULT, //sequence number
 *  SRCSHIPFV  CHAR (2)      NOT NULL WITH DEFAULT, //shipped fruit variety
 *  SRCFRTTYPE CHAR (2)      NOT NULL WITH DEFAULT, //fruit type
 *  SRCBRIXLVL NUMERIC 7,1   NOT NULL WITH DEFAULT, //brix level
 *  SRCCOLOR   CHAR (2)      NOT NULL WITH DEFAULT, //color
 *  SRCFLAVOR  CHAR (2)      NOT NULL WITH DEFAULT) //flavor
 *
 *
 * CREATE TABLE DBLIB/SRPECOMENT (
 *  SRENUMBER  int           NOT NULL WITH DEFAULT, //sample order number
 *  SRECOMMENT CHAR (60)     NOT NULL WITH DEFAULT, //comment
 *  SREDATE    DATE          NOT NULL WITH DEFAULT, //create date
 *  SRETIME    TIME          NOT NULL WITH DEFAULT, //create time
 *  SRESEQ     NUMERIC 3     NOT NULL WITH DEFAULT) //sequence number
 *
 *
 * CREATE TABLE DBLIB/SRPFREMARK (
 *  SRFNUMBER  int           NOT NULL WITH DEFAULT, //sample order number
 *  SRFREMARK  CHAR (60)     NOT NULL WITH DEFAULT, //remark
 *  SRFSEQ     NUMERIC 3     NOT NULL WITH DEFAULT) //sequence number
 *
 *
 * Be sure to set the environment (library) for this file . It
 *  should be "DBLIB." for the live environment and "WKLIB." 
 *  for the test environment.
 *   MAINTAINED PRIOR TO EVERY EXPORT:
 *   Make sure the library in this section is correct.
 *
 **/
public class SampleRequestOrder {

	//header file
	private Integer         sampleNumber;
	private String          status;
	private String          type;
	private String          application;
	private String          salesRep;
	private String          technician;
	private Integer         territory;
	private java.sql.Date   receivedDate;
	private java.sql.Time   receivedTime;
	private java.sql.Date   deliveryDate;
	private Integer         custNumber;
	private String          custContact;
	private String          custContactPhone;
	private String          custContactEmail;
	private String          attnContact;
	private String          attnContactPhone;
	private String          attnContactEmail;
	private String          whoRequested;
	private String          whoReceivedRequest;
	private String          shippingCharge;
	private String          shipVia;
	private String          shipHow;
	private String          trackingNumber;
	private String          glAccountNumber;
	private String          glAccountMisc;
	private java.sql.Date   shippingDate;
	private String          emailWhenShipped1;
	private String          emailWhenShipped2;
	private String          emailWhenShipped3;
	private String          emailWhenShipped4;
	private String          emailWhenShipped5;
	private java.sql.Date   createDate;
	private java.sql.Time   createTime;
	private String          createUser;
	private java.sql.Date   updateDate;
	private java.sql.Time   updateTime;
	private String          updateUser;
	private String          location;
	private String          viewLot;
	private String          viewVariety;

	//detail file	
	private BigDecimal[]    quantity;
	private BigDecimal[]    containerSize;
	private String[]        unitOfMeasure;
	private String[]        productGroup;
	private String[]        productType;
	private String[]        cutSize;
	private String[]        chemicalAdditive;
	private String[]        fruitVariety;
	private String[]        itemDescription;
	private String[]        additionalDescription;
	private String[]        preservative;
	private String[]        resource;
	private String[]        lotNumber;
	private Integer[]       formulaNumber;
	private String[]        specNumber;
	private Integer[]       dtlSeqNumber;
	private String[]        shippedFruitVariety;
	private String[]        fruitType;
	private BigDecimal[]    brixLevel;
	private String[]        color;
	private String[]        flavor;

	//comments
	private String[]        comment;
	private java.sql.Date[] commentDate;
	private java.sql.Time[] commentTime;
	private Integer[]       commentSeqNumber;

	//remarks
	private String[]        remark;
	private Integer[]       remarkSeqNumber;
	
	//check boxes
	private String[]        descKey;
	private String[]        keyCode;
	private String[]        keyValue;
	private String[]        checkBoxValue8;	
	private String[]        checkBoxValue20;
	private Integer[]       checkBoxSeqNumber;
	
	//Vectors to look for Comments and URLS
	private Vector	listComments = new Vector();
	private Vector  listURLs	 = new Vector();
	
	//live or test environment on the as400
	private static String library = "DBPRD.";
//    private static String library = "DBLIB.";
//	private static String library = "WKLIB.";
	
	//**** For use in Main Method,
    // Constructor Methods and 
    // Update, Add, Delete Methods  ****//
	private static PreparedStatement dltHeaderSql;
	private static PreparedStatement dltCheckBoxSql;
	private static PreparedStatement dltDetailSql;
	private static PreparedStatement dltCommentSql;
	private static PreparedStatement dltRemarkSql;
	
	private static PreparedStatement addHeaderSql;
	private static PreparedStatement addCheckBoxSql;
	private static PreparedStatement addDetailSql;
	private static PreparedStatement addCommentSql;
	private static PreparedStatement addRemarkSql;
	
	private static PreparedStatement updHeaderSql;
	private static PreparedStatement updCheckBoxSql;
	private static PreparedStatement updDetailSql;
	private static PreparedStatement updCommentSql;
	private static PreparedStatement updRemarkSql;

	private static PreparedStatement findHdrBySampleNumberSql;
	private static PreparedStatement findDtlBySampleNumberSql;
	private static PreparedStatement findCmtBySampleNumberSql;
	private static PreparedStatement findChkBySampleNumberSql;
	private static PreparedStatement findRmkBySampleNumberSql;


	// Additional fields.
	private boolean persists = false;
	private static Connection connection;
/**
 * UserRole constructor comment.
 */
public SampleRequestOrder() {
	
		init();

}
/**
 * Used to instantiate the SampleRequestOrder class.
 *
 *  This method receives in a Integer parameter for specific order.
 
 *  The class will be populated from data files on the mainframe.
 
 *  Header information is loaded as a single field value where detail information
 * is loaded into arrays. array lengths will vary from zero to many depending
 * upon the number of records obtained from the mainframe file associated to
 * fields. As an example cpmments may contain zero entries while resource may 
 * contain 10, and remark may contain two.
 * 
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public SampleRequestOrder(Integer sampleNumberIn) 
	throws InstantiationException { 

		init();
		
	ResultSet rsHdr = null;
	ResultSet rsDtl = null;
	ResultSet rsChk = null;
	ResultSet rsCmt = null;
	ResultSet rsRmk = null;
	
	try {
		findHdrBySampleNumberSql.setInt(1, sampleNumberIn.intValue());
		rsHdr = findHdrBySampleNumberSql.executeQuery();
		
		if (rsHdr.next() == false)
			throw new InstantiationException("The sample request order number: " + 
				                              sampleNumberIn + " not found");
			
	} catch (SQLException e) {
		System.out.println("Sql error (Header) at com.treetop.data." +
			               "SampleRequestOrder.SampleRequestOrder(Integer) " + e);
		return;
	}

	
	try {
		findDtlBySampleNumberSql.setInt(1, sampleNumberIn.intValue());
		rsDtl = findDtlBySampleNumberSql.executeQuery();

	} catch (SQLException e) {
		System.out.println("Sql error (Detail) at com.treetop.data." +
			               "SampleRequestOrder.SampleRequestOrder(Integer) " + e);
	}

		
	try {
		findCmtBySampleNumberSql.setInt(1, sampleNumberIn.intValue());
		rsCmt = findCmtBySampleNumberSql.executeQuery();

	} catch (SQLException e) {
		System.out.println("Sql error (Comment) at com.treetop.data." +
			               "SampleRequestOrder.SampleRequestOrder(Integer) " + e);
	}

	
	try {
		findChkBySampleNumberSql.setInt(1, sampleNumberIn.intValue());
		rsChk = findChkBySampleNumberSql.executeQuery();

	} catch (SQLException e) {
		System.out.println("Sql error (CheckBox) at com.treetop.data." +
			               "SampleRequestOrder.SampleRequestOrder(Integer) " + e);
	}

	
	try {
		findRmkBySampleNumberSql.setInt(1, sampleNumberIn.intValue());
		rsRmk = findRmkBySampleNumberSql.executeQuery();

	} catch (SQLException e) {
		System.out.println("Sql error (Remark) at com.treetop.data." +
			               "SampleRequestOrder.SampleRequestOrder(Integer) " + e);
	}	

	
	loadFields(rsHdr, rsDtl, rsCmt, rsChk, rsRmk);

	//close all possible result sets.	
	try {
		rsHdr.close();
	//	System.out.println("good Hdr close");
	} 
	catch (Exception eAny) 
	{System.out.println("bad Hdr close");}
	try {
		rsDtl.close();
	//	System.out.println("good Dtl close");
	} 
	catch (Exception eAny) 
	{System.out.println("bad Dtl close");}
	try {		
		rsCmt.close();
	//	System.out.println("good Cmt close");	
	} 
	catch (Exception eAny) 
	{System.out.println("bad Cmt close");}
	try {		
		rsChk.close();
	//	System.out.println("good Chk close");
	} 
	catch (Exception eAny) 
	{System.out.println("bad Chk close");}
	try {
		rsRmk.close();
	//	System.out.println("good Rmk close");
	} 
	catch (Exception eAny) 
	{System.out.println("bad Rmk close");}		
}
/**
 * Used to instantiate the SampleRequestOrder class.
 *
 *  This method receives in a Integer parameter for specific order.
 
 *  The class will be populated from data files on the mainframe.
 
 *  Header information is loaded as a single field value where detail information
 * is loaded into arrays. array lengths will vary from zero to many depending
 * upon the number of records obtained from the mainframe file associated to
 * fields. As an example cpmments may contain zero entries while resource may 
 * contain 10, and remark may contain two.
 * 
 * Creation date: (2/13/2003 10:36:39 AM)
 */
public SampleRequestOrder(BigDecimal sampleNumberIn) 
	throws InstantiationException { 

		init();
		
	ResultSet rsHdr = null;
	ResultSet rsDtl = null;
	ResultSet rsChk = null;
	ResultSet rsCmt = null;
	ResultSet rsRmk = null;
	
	try {
		Integer sampleNumber = new Integer(sampleNumberIn.setScale(0).toString());
		findHdrBySampleNumberSql.setInt(1, sampleNumber.intValue());
		rsHdr = findHdrBySampleNumberSql.executeQuery();
		
		if (rsHdr.next() == false)
			throw new InstantiationException("The sample request order number: " + 
											  sampleNumberIn + " not found");
			
	} catch (SQLException e) {
		System.out.println("Sql error (Header) at com.treetop.data." +
						   "SampleRequestOrder.SampleRequestOrder(BigDecimal) " + e);
		return;
	}

	
	try {
		Integer sampleNumber = new Integer(sampleNumberIn.setScale(0).toString());
		findDtlBySampleNumberSql.setInt(1, sampleNumber.intValue());
		rsDtl = findDtlBySampleNumberSql.executeQuery();

	} catch (SQLException e) {
		System.out.println("Sql error (Detail) at com.treetop.data." +
						   "SampleRequestOrder.SampleRequestOrder(BigDecimal) " + e);
	}

		
	try {
		Integer sampleNumber = new Integer(sampleNumberIn.setScale(0).toString());
		findCmtBySampleNumberSql.setInt(1, sampleNumber.intValue());
		rsCmt = findCmtBySampleNumberSql.executeQuery();

	} catch (SQLException e) {
		System.out.println("Sql error (Comment) at com.treetop.data." +
						   "SampleRequestOrder.SampleRequestOrder(BigDecimal) " + e);
	}

	
	try {
		Integer sampleNumber = new Integer(sampleNumberIn.setScale(0).toString());
		findChkBySampleNumberSql.setInt(1, sampleNumber.intValue());
		rsChk = findChkBySampleNumberSql.executeQuery();

	} catch (SQLException e) {
		System.out.println("Sql error (CheckBox) at com.treetop.data." +
						   "SampleRequestOrder.SampleRequestOrder(BigDecimal) " + e);
	}

	
	try {
		Integer sampleNumber = new Integer(sampleNumberIn.setScale(0).toString());
		findRmkBySampleNumberSql.setInt(1, sampleNumber.intValue());
		rsRmk = findRmkBySampleNumberSql.executeQuery();

	} catch (SQLException e) {
		System.out.println("Sql error (Remark) at com.treetop.data." +
						   "SampleRequestOrder.SampleRequestOrder(BigDecimal) " + e);
	}	

	
	loadFields(rsHdr, rsDtl, rsCmt, rsChk, rsRmk);

	//close all possible result sets.	
	try {
		rsHdr.close();
	//	System.out.println("good Hdr close");
	} 
	catch (Exception eAny) 
	{System.out.println("bad Hdr close");}
	try {
		rsDtl.close();
	//	System.out.println("good Dtl close");
	} 
	catch (Exception eAny) 
	{System.out.println("bad Dtl close");}
	try {		
		rsCmt.close();
	//	System.out.println("good Cmt close");	
	} 
	catch (Exception eAny) 
	{System.out.println("bad Cmt close");}
	try {		
		rsChk.close();
	//	System.out.println("good Chk close");
	} 
	catch (Exception eAny) 
	{System.out.println("bad Chk close");}
	try {
		rsRmk.close();
	//	System.out.println("good Rmk close");
	} 
	catch (Exception eAny) 
	{System.out.println("bad Rmk close");}		
}

/**
 * Used to Instantiate the RandDFormula Class.
 *    By sending in a result Set of Header Information
 *    The fields will be loaded with information from that Formula.
 *        Header Information will be 1 per field.
 *        Detail Information will be an array per field.
 *
 * Creation date: (6/18/2003 4:02:39 PM)
 */
private SampleRequestOrder(ResultSet rsHdr)

throws InstantiationException 
{ 
	if (connection == null)
		init();
	
	ResultSet rsDtl = null;
	ResultSet rsCmt = null;
	ResultSet rsChk = null;
	ResultSet rsRmk = null;

	//detail	
	try 
	{
		findDtlBySampleNumberSql.setInt(1, rsHdr.getInt("SRBNUMBER"));
		rsDtl = findDtlBySampleNumberSql.executeQuery();
	} 
	catch (SQLException eDtl) 
	{
		System.out.println("SQL error at " +
				   "com.treetop.data.SampleRequestOrder." +
				   "SampleRequestOrder(ResultSet) --Detail Section-- : " + eDtl);				   
		return;
	}

	//comments
	try 
	{
		findCmtBySampleNumberSql.setInt(1, rsHdr.getInt("SRBNUMBER"));
		rsCmt = findCmtBySampleNumberSql.executeQuery();
	} 
	catch (SQLException eCmt) 
	{
		System.out.println("SQL error at " +
				   "com.treetop.data.SampleRequestOrder." +
				   "SampleRequestOrder(ResultSet) --Comment Section-- : " + eCmt);				   
		return;
	}

	//check boxes
	try 
	{
		findChkBySampleNumberSql.setInt(1, rsHdr.getInt("SRBNUMBER"));
		rsChk = findChkBySampleNumberSql.executeQuery();
	} 
	catch (SQLException eChk) 
	{
		System.out.println("SQL error at " +
				   "com.treetop.data.SampleRequestOrder." +
				   "SampleRequestOrder(ResultSet) --Check Box Section-- : " + eChk);				   
		return;
	}

	//remarks
	try 
	{
		findRmkBySampleNumberSql.setInt(1, rsHdr.getInt("SRBNUMBER"));
		rsRmk = findRmkBySampleNumberSql.executeQuery();
	} 
	catch (SQLException eRmk) 
	{
		System.out.println("SQL error at " +
				   "com.treetop.data.SampleRequestOrder." +
				   "SampleRequestOrder(ResultSet) --Remark Section-- : " + eRmk);				   
		return;
	}
	loadFields(rsHdr, rsDtl, rsCmt, rsChk, rsRmk);

	try {
		rsDtl.close();
		rsCmt.close();
		rsChk.close();
		rsRmk.close();
	} 
	catch (SQLException eAny) 
	{
		System.out.println("SQL error at " +
				   "com.treetop.data.SampleRequestOrder." +
				   "SampleRequestOrder(ResultSet) --Close rs Section-- : " + eAny);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void add() {

	//header
	try { 
		if (shippingDate == null)
			shippingDate = java.sql.Date.valueOf("1900-01-01");
			
		addHeaderSql.setInt(1,     sampleNumber.intValue());
		addHeaderSql.setString(2,  status);
		addHeaderSql.setString(3,  type);
		addHeaderSql.setString(4,  application);
		addHeaderSql.setString(5,  salesRep);
		addHeaderSql.setString(6,  technician);
		addHeaderSql.setInt(7,     territory.intValue());
		addHeaderSql.setDate(8,    receivedDate);
		addHeaderSql.setTime(9,    receivedTime);
		addHeaderSql.setDate(10,   deliveryDate);
		addHeaderSql.setInt(11,    custNumber.intValue());
		addHeaderSql.setString(12, custContact);
		addHeaderSql.setString(13, custContactPhone);
		addHeaderSql.setString(14, custContactEmail);
		addHeaderSql.setString(15, attnContact);
		addHeaderSql.setString(16, attnContactPhone);
		addHeaderSql.setString(17, attnContactEmail);
		addHeaderSql.setString(18, whoRequested);
		addHeaderSql.setString(19, whoReceivedRequest);
		addHeaderSql.setString(20, (shippingCharge));
		addHeaderSql.setString(21, shipVia);
		addHeaderSql.setString(22, shipHow);
		addHeaderSql.setString(23, trackingNumber);
		addHeaderSql.setString(24, glAccountNumber);
		addHeaderSql.setString(25, glAccountMisc);
		addHeaderSql.setDate(26,   shippingDate);
		addHeaderSql.setString(27, emailWhenShipped1);
		addHeaderSql.setString(28, emailWhenShipped2);
		addHeaderSql.setString(29, emailWhenShipped3);
		addHeaderSql.setString(30, emailWhenShipped4);
		addHeaderSql.setString(31, emailWhenShipped5);
		addHeaderSql.setDate(32,   createDate);
		addHeaderSql.setTime(33,   createTime);
		addHeaderSql.setString(34, createUser);
		addHeaderSql.setDate(35,   updateDate);
		addHeaderSql.setTime(36,   updateTime);
		addHeaderSql.setString(37, updateUser);
		addHeaderSql.setString(38, location);
		addHeaderSql.setString(39, viewLot);
		addHeaderSql.setString(40, viewVariety);
				
		addHeaderSql.executeUpdate();

		//detail
		int howManyDetailLines = dtlSeqNumber.length;
		for (int x=0; x < howManyDetailLines; x++)
		{
			try
			{
				String newQuanity = quantity[x] + "";
				String newContainerSize = containerSize[x] + "";
				String newBrixLevel = brixLevel[x] + "";

				addDetailSql.setInt(1, sampleNumber.intValue());
				addDetailSql.setString(2, newQuanity);
				addDetailSql.setString(3, newContainerSize);
				addDetailSql.setString(4, unitOfMeasure[x]);
				addDetailSql.setString(5, productGroup[x]);
				addDetailSql.setString(6, productType[x]);
				addDetailSql.setString(7, cutSize[x]);
				addDetailSql.setString(8, chemicalAdditive[x]);
				addDetailSql.setString(9, fruitVariety[x]);
				addDetailSql.setString(10, itemDescription[x]);
				addDetailSql.setString(11, additionalDescription[x]);
				addDetailSql.setString(12, preservative[x]);
				addDetailSql.setString(13, resource[x]);
				addDetailSql.setString(14, lotNumber[x]);
				addDetailSql.setInt(15, formulaNumber[x].intValue());
				addDetailSql.setString(16, specNumber[x]);
				addDetailSql.setInt(17, dtlSeqNumber[x].intValue());
				addDetailSql.setString(18, shippedFruitVariety[x]);
				addDetailSql.setString(19, fruitType[x]);
				addDetailSql.setString(20, newBrixLevel);
				addDetailSql.setString(21, color[x]);
				addDetailSql.setString(22, flavor[x]);

				addDetailSql.executeUpdate();
			}
			catch (SQLException eDtl)
			{
				System.out.println("SQL error at " +
					"com.treetop.data.SampleRequestOrder.add() --Detail Section--:" +
					eDtl);
			}
		}

		//comments
		int howManyCommentLines = commentSeqNumber.length;
		for (int x=0; x < howManyCommentLines; x++)
		{
			try
			{
				addCommentSql.setInt(1, sampleNumber.intValue());
				addCommentSql.setString(2, comment[x]);
				addCommentSql.setDate(3, commentDate[x]);
				addCommentSql.setTime(4, commentTime[x]);
				addCommentSql.setInt(5, commentSeqNumber[x].intValue());

				addCommentSql.executeUpdate();
			}
			catch (SQLException eCmt)
			{
				System.out.println("SQL error at " +
					"com.treetop.data.SampleRequestOrder.add() --Comment Section--:" +
					eCmt);
			}
		}

		//check boxes
		int howManyCheckBoxLines = checkBoxValue20.length;
		for (int x=0; x < howManyCheckBoxLines; x++)
		{
			try
			{
				addCheckBoxSql.setInt(1, sampleNumber.intValue());
				addCheckBoxSql.setString(2, descKey[x]);
				addCheckBoxSql.setString(3, keyCode[x]);
				addCheckBoxSql.setString(4, keyValue[x]);
				addCheckBoxSql.setString(5, checkBoxValue8[x]);
				addCheckBoxSql.setString(6, checkBoxValue20[x]);
				addCheckBoxSql.setInt(7, checkBoxSeqNumber[x].intValue());

				addCheckBoxSql.executeUpdate();
			}
			catch (SQLException eCbx)
			{
				System.out.println("SQL error at " +
					"com.treetop.data.SampleRequestOrder.add() " +
					"--Checkbox Section--:" +
					eCbx);
			}
		}

		//remarks
		int howManyRemarkLines = remarkSeqNumber.length;
		for (int x=0; x < howManyRemarkLines; x++)
		{
			try
			{
				addRemarkSql.setInt(1, sampleNumber.intValue());
				addRemarkSql.setString(2, remark[x]);
				addRemarkSql.setInt(3, remarkSeqNumber[x].intValue());

				addRemarkSql.executeUpdate();
			}
			catch (SQLException eRmk)
			{
				System.out.println("SQL error at " +
					"com.treetop.data.SampleRequestOrder.add() --Remark Section--:" +
					eRmk);
			}
		}
		
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestOrder." +
			               "add(): " + e);
	}	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void addToSampleRequestOrder(Integer sampleNumber, 
								           String status) 
	throws InvalidLengthException, Exception
{
	SampleRequestOrder newSampleRequestOrder = new SampleRequestOrder();
	newSampleRequestOrder.setSampleNumber(sampleNumber);
	newSampleRequestOrder.setStatus(status);
	newSampleRequestOrder.add();
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void delete() {

	try {
		dltHeaderSql.setInt(1, sampleNumber.intValue());
		dltHeaderSql.executeUpdate();

		dltDetailSql.setInt(1, sampleNumber.intValue());
		dltDetailSql.executeUpdate();

		dltCommentSql.setInt(1, sampleNumber.intValue());
		dltCommentSql.executeUpdate();

		dltCheckBoxSql.setInt(1, sampleNumber.intValue());
		dltCheckBoxSql.executeUpdate();

		dltRemarkSql.setInt(1, sampleNumber.intValue());
		dltRemarkSql.executeUpdate();
		
	} catch (SQLException e) {
		System.out.println("SQL Exception at com.treetop.data.SampleRequestOrder" +
			               ".delete(): " + e);
	}	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public boolean deleteBySampleNumber(int sampleNumberIn) {

	try {
		dltHeaderSql.setInt(1, sampleNumberIn);
		dltHeaderSql.executeUpdate();
		
		dltDetailSql.setInt(1, sampleNumberIn);
		dltDetailSql.executeUpdate();

		dltCommentSql.setInt(1, sampleNumberIn);
		dltCommentSql.executeUpdate();

		dltCheckBoxSql.setInt(1, sampleNumberIn);
		dltCheckBoxSql.executeUpdate();

		dltRemarkSql.setInt(1, sampleNumberIn);
		dltRemarkSql.executeUpdate();
			
		return true;
		
	} catch (Exception e) {
		System.out.println("SQL Exception at com.treetop.data.SampleRequestOrder" +
			               ".delete(): " + e);
		return false;
	}	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static SampleRequestOrder findBySampleNumber(Integer sampleNumberIn) {

	ResultSet rsHdr = null;
	ResultSet rsDtl = null;
	ResultSet rsCmt = null;
	ResultSet rsChk = null;
	ResultSet rsRmk = null;
	
	try {
		findHdrBySampleNumberSql.setInt(1, sampleNumberIn.intValue());
		rsHdr = findHdrBySampleNumberSql.executeQuery();
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestOrder." +
			               "findBySampleNumber(Integer): " + e);
		return null;
	}
	
	
	try {
		if (rsHdr.next())
		{
			SampleRequestOrder aSampleRequestOrder = new SampleRequestOrder();
			aSampleRequestOrder.loadFields(rsHdr, rsDtl, rsCmt, rsChk, rsRmk);
			return aSampleRequestOrder;
		}
	} catch (SQLException e) {
		System.out.println("Sql error at com.treetop.data.SampleRequestOrder" +
			               ".findByPathNumber(Integer): " + e);
		return null;
	}
		// Close statement and result set
	try{
		 rsHdr.close();
	}
	catch(Exception e)
	{
		System.out.println("Problem closing rs in SampleRequestOrder.findBySampleNumber(): " + e);
	} 
	
	return null;	
}
/**
 * This method will return a vector of Sample Request Orders.
 *   Send in a bunch of parameters to define the SQL statement.
 *
 * Creation date: (7/01/2003 10:17:29 AM)
 */
public Vector findSamples(String[] status,
						  Integer fromSample,
						  Integer toSample,
						  java.sql.Date fromReceivedDate,
						  java.sql.Date toReceivedDate,
						  java.sql.Date fromShipDate,
						  java.sql.Date toShipDate,
						  java.sql.Date fromDeliveryDate,
						  java.sql.Date toDeliveryDate,
						  String customerName,
						  Integer customerNumber,
						  String formulaName,
						  Integer formulaNumber,
						  String resource,
						  String productDescription,
					   	  String broker,
						  String sampleType,
						  String technician,
						  String application,
						  String salesRep,
						  String lotNumber,
						  String fruitVariety,
						  String shippedFruitVariety,
						  String productGroup,
						  String productType,
						  String fruitType,
						  String cutSize,
						  String chemicalAdditive,
						  String color,
						  String flavor,
						  String location,
						  String orderBy) 						  
{
		

   	Vector returnSamples = new Vector();	

   	//build the sql statement	
	String SQLRun = "SELECT * " +
                    "FROM " + library + "SRPBHEADER ";
    String SQLDetail = "";
    String SQLExtra = ""; 
    String SQLWhere = "";
    
//STATUS FIELD SELECTIONS.
	if (status[0] != null && !status[0].equals("null"))
	{
		if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    String statusInfo = "";
		for (int x=0; x < status.length; x++)
		{
			if (status[x] != null && !status[x].equals("null"))
			{
				if (x != 0)
				   statusInfo = statusInfo + ",";

				statusInfo = statusInfo + "'" + status[x] + "'";
			}
		}
		SQLWhere = SQLWhere + "SRBSTATUS IN (" + statusInfo + ") ";
	}
    
//HEADER FILE FIELD SELECTIONS.
    
	//from and to sample numbers.
	int fsn = 0;
	int tsn = 999999999;
	
	if (fromSample != null)
		fsn = fromSample.intValue();

	if (toSample != null)
		tsn = toSample.intValue(); 
	
	if (fsn != 0 || tsn != 999999999)
	{
		if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    SQLWhere = SQLWhere + "SRBNUMBER BETWEEN " +
	                fromSample + " and " + toSample + " ";	                
	}

	//from and to received dates.
    if (fromReceivedDate != null || toReceivedDate != null)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    if (fromReceivedDate == null)
	       fromReceivedDate = java.sql.Date.valueOf("1950-01-01");
	    if (toReceivedDate == null)
	       toReceivedDate = java.sql.Date.valueOf("2050-12-31");

	    SQLWhere = SQLWhere +
	    			"SRBRECDATE BETWEEN \'" + fromReceivedDate +
	    			"\' AND \'"+ toReceivedDate +
	                "\' ";
    }

    //from and to ship dates.
	if (fromShipDate != null || toShipDate != null)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    if (fromShipDate == null)
	       fromShipDate = java.sql.Date.valueOf("1950-01-01");
	    if (toShipDate == null)
	       toShipDate = java.sql.Date.valueOf("2050-12-31");

	    SQLWhere = SQLWhere +
	    			"SRBSHPDATE BETWEEN \'" +
	                fromShipDate + "\' AND \'" + toShipDate +
	                "\' ";
	}

    //from and to delivery dates.
	if (fromDeliveryDate != null || toDeliveryDate != null)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    if (fromDeliveryDate == null)
	       fromDeliveryDate = java.sql.Date.valueOf("1950-01-01");
	    if (toDeliveryDate == null)
	       toDeliveryDate = java.sql.Date.valueOf("2050-12-31");

	    SQLWhere = SQLWhere +
	    			"SRBDELDATE BETWEEN \'" +
	                fromDeliveryDate + "\' AND \'" + toDeliveryDate +
	                "\' ";
	}
	//customer Number
    if (customerNumber != null)
    {
	    int custInt = customerNumber.intValue();
	    if (custInt > 0)
	    {
	       if (SQLWhere.equals(""))
	          SQLWhere = "WHERE ";
   	       else
	          SQLWhere = SQLWhere + "AND ";

	       SQLWhere = SQLWhere +
	    			"SRBCUST = " + custInt + " ";
	    }
    }    

	SQLExtra = "LEFT OUTER JOIN " + library + "SRPACUST " +
	       				"ON SRANUMBER = SRBCUST ";
    //customer name.
	if (customerName != null && !customerName.equals("all") 
		                     && customerName.trim().length() != 0)
    {
	    SQLExtra = "INNER JOIN " + library + "SRPACUST " +
	       				"ON SRANUMBER = SRBCUST ";
	       				
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    customerName = customerName.toUpperCase().trim();
	    customerName = "%" + customerName + "%";
	    
	    SQLWhere = SQLWhere +
	    		   "UPPER(SRANAME) LIKE \'" + customerName + "\' ";
    }

    //formula name.
	if (formulaName != null && !formulaName.equals("all") 
		                     && formulaName.trim().length() != 0)
    {
	    SQLExtra = SQLExtra +
	    			"INNER JOIN " + library + "RDPAFRMHDR " +
	       				"ON RDANUMBER = SRCFORMULA ";
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	       				
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    formulaName = formulaName.toUpperCase().trim();
	    formulaName = "%" + formulaName + "%";
	    
	    SQLWhere = SQLWhere +
	    		   "UPPER(RDANAME) LIKE \'" + formulaName + "\' ";
    }

	//formula Number
    if (formulaNumber != null)
    {
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	       				 
	    int formInt = formulaNumber.intValue();
	    if (formInt > 0)
	    {
	       if (SQLWhere.equals(""))
	          SQLWhere = "WHERE ";
   	       else
	          SQLWhere = SQLWhere + "AND ";

	       SQLWhere = SQLWhere +
	    			"SRCFORMULA = " + formInt + " ";
	    }
    }        

	//technician.
	if (technician != null && technician.trim().length() != 0
		                   && !technician.equals("None"))
	{
		if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    SQLWhere = SQLWhere +
	    		   "UPPER(SRBTECH) = \'" + technician + "\' ";
	}

	//broker.
	if (broker != null && broker.trim().length() != 0)
	{
		if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    SQLWhere = SQLWhere +
	    		   "SRBMARKET = " + broker + " ";
	}

		//sample type.
	if (sampleType != null && sampleType.trim().length() != 0
		                   && !sampleType.equals("None"))
	{
		if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    sampleType = sampleType.toUpperCase();
	    sampleType = "%" + sampleType + "%";
	    
	    SQLWhere = SQLWhere +
	    		   "UPPER(SRBTYPE) LIKE \'" + sampleType + "\' ";
	}

	//application.
	if (application != null && application.trim().length() != 0
		                    && !application.equals("None"))
	{
		if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    application = application.toUpperCase();
	    application = "%" + application + "%";

	    SQLWhere = SQLWhere +
	    		   "UPPER(SRBAPP) LIKE \'" + application + "\' ";
	}

	//sales representitives.
	if (salesRep != null && salesRep.trim().length() != 0
		                 && !salesRep.equals("None"))
	{
		if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    SQLWhere = SQLWhere +
	    		   "UPPER(SRBREP) = \'" + salesRep + "\' ";
	}


	//location.
	if (location != null && location.trim().length() != 0
		                 && !location.equals("None"))
	{
		if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    SQLWhere = SQLWhere +
	    		   "SRBLOC = \'" + location + "\' ";
	}
	
		
//DETAIL FILE FIELD SELECTIONS.

	//resource number.
    if (resource != null && resource.trim().length() != 0)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRCRES) LIKE \'" + resource + "%\' ";
    }

    //product description.
    if (productDescription != null && productDescription.trim().length() != 0
	                               && !productDescription.equals("all"))
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	       
    	productDescription = productDescription.toUpperCase();
    	productDescription = "%" + productDescription + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRCITMDESC) LIKE \'" + productDescription + "\' ";
    }

    //fruit variety.
    if (fruitVariety != null && fruitVariety.trim().length() != 0
	                         && !fruitVariety.equals("None"))
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	       
    	fruitVariety = fruitVariety.toUpperCase();
    	fruitVariety = "%" + fruitVariety + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRCFRUTVAR) LIKE \'" + fruitVariety + "\' ";
    }

    //product group.
    if (productGroup != null && productGroup.trim().length() != 0
	                         && !productGroup.equals("None"))
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	       
    	productGroup = productGroup.toUpperCase();
    	productGroup = "%" + productGroup + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRCPRDGRP) LIKE \'" + productGroup + "\' ";
    }
    //lot number
    if (lotNumber != null && lotNumber.trim().length() != 0)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	       
    	lotNumber = "%" + lotNumber + "%";
	    
	    SQLWhere = SQLWhere +
                  "SRCLOT LIKE \'" + lotNumber + "\' ";
    }
    //product type.
    if (productType != null && productType.trim().length() != 0
	                        && !productType.equals("None"))
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	       
    	productType = productType.toUpperCase();
    	productType = "%" + productType + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRCPRDTYPE) LIKE \'" + productType + "\' ";
    }

    //cut size.
    if (cutSize != null && cutSize.trim().length() != 0
	                    && !cutSize.equals("None"))
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	       
    	cutSize = cutSize.toUpperCase();
    	cutSize = "%" + cutSize + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRCCUTSIZE) LIKE \'" + cutSize + "\' ";
    }

    //chemical additive.
    if (chemicalAdditive != null && chemicalAdditive.trim().length() != 0
	                             && !chemicalAdditive.equals("None"))
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	       
    	chemicalAdditive = chemicalAdditive.toUpperCase();
    	chemicalAdditive = "%" + chemicalAdditive + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRCCHEMADD) LIKE \'" + chemicalAdditive + "\' ";
    }

    //fruit type.
    if (fruitType != null && 
	    fruitType.trim().length() != 0 &&
        !fruitType.equals("None"))
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	       
    	fruitType = fruitType.toUpperCase();
    	fruitType = "%" + fruitType + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRCFRTTYPE) LIKE \'" + fruitType + "\' ";
    }
    
    //shipped fruit variety.
    if (shippedFruitVariety != null && 
	    shippedFruitVariety.trim().length() != 0 &&
	    !shippedFruitVariety.equals("None"))
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	       
    	shippedFruitVariety = shippedFruitVariety.toUpperCase();
    	shippedFruitVariety = "%" + shippedFruitVariety + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRCSHIPFV) LIKE \'" + shippedFruitVariety + "\' ";
    }
    
    //color.
    if (color != null && 
	    color.trim().length() != 0 &&
	    !color.equals("None"))
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	       
    	color = color.toUpperCase();
    	color = "%" + color + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRCCOLOR) LIKE \'" + color + "\' ";
    }

    //flavor.
    if (flavor != null && 
	    flavor.trim().length() != 0 &&
	    !flavor.equals("None"))
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "SRPCDETAIL " +
	       				"ON SRCNUMBER = SRBNUMBER ";
	       
    	flavor = flavor.toUpperCase();
    	flavor = "%" + flavor + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRCFLAVOR) LIKE \'" + flavor + "\' ";
    }


	//***********************************************//
	//***  Information for Order of Results   **********//
	//     Order by can be
	//        NumberA       = Sample Number Ascending (Default if Blank)
	//        NumberD       = Sample Number Descending
	//        StatusA       = Sample Status Ascending 
	//        StatusD       = Sample Status Descending
	//        TypeA         = Type Ascending
	//        TypeD         = Type Descending
	//        ReceivedDateA = Received Date Ascending
	//        ReceivedDateD = Received Date Descending
	//        SalesRepA     = Sales Rep Ascending
	//        SalesRepB     = Sales Rep Decending
	//        ShippedDateA  = Shipped Date Ascending
	//        ShippedDateD  = Shipped Date Decending
	//        CustomerNameA = Customer Name Ascending
	//        CustomerNameD = Customer Name Decending
	//        DeliveryDateA = Delivery Date Ascending
	//        DeliveryDateB = Delivery Date Decending
	//***
	String SQLOrder = "ORDER BY SRBNUMBER"; // Default NameA
	
	if (orderBy.equals("NumberD"))
	   SQLOrder = "ORDER BY SRBNUMBER DESC";
	   
	if (orderBy.equals("StatusA"))
	   SQLOrder = "ORDER BY UPPER(SRBSTATUS), SRBNUMBER";
	if (orderBy.equals("StatusD"))
	   SQLOrder = "ORDER BY UPPER(SRBSTATUS) DESC, SRBNUMBER";
	
	if (orderBy.equals("TypeA"))
	   SQLOrder = "ORDER BY UPPER(SRBTYPE), SRBNUMBER";
	if (orderBy.equals("TypeD"))
	   SQLOrder = "ORDER BY UPPER(SRBTYPE) DESC, SRBNUMBER";
	    
	if (orderBy.equals("ReceivedDateA"))
	   SQLOrder = "ORDER BY SRBRECDATE, SRBRECTIME, SRBNUMBER";
	if (orderBy.equals("ReceivedDateD"))
	   SQLOrder = "ORDER BY SRBRECDATE DESC, SRBRECTIME DESC, SRBNUMBER";

	if (orderBy.equals("SalesRepA"))
	   SQLOrder = "ORDER BY UPPER(SRBREP), SRBNUMBER";
	if (orderBy.equals("SalesRepD"))
	   SQLOrder = "ORDER BY UPPER(SRBREP) DESC, SRBNUMBER";
	   
	if (orderBy.equals("ShippedDateA"))
	   SQLOrder = "ORDER BY SRBSHPDATE, SRBNUMBER";
	if (orderBy.equals("ShippedDateD"))
	   SQLOrder = "ORDER BY SRBSHPDATE DESC, SRBNUMBER";

	if (orderBy.equals("CustomerNameA"))
	   SQLOrder = "ORDER BY UPPER(SRANAME), SRBNUMBER";
	if (orderBy.equals("CustomerNameD"))
	   SQLOrder = "ORDER BY UPPER(SRANAME) DESC, SRBNUMBER";

	if (orderBy.equals("DeliveryDateA"))
	   SQLOrder = "ORDER BY SRBDELDATE, SRBNUMBER";
	if (orderBy.equals("DeliveryDateD"))
	   SQLOrder = "ORDER BY SRBDELDATE DESC, SRBNUMBER";

	//***********************************************//
	//***  Put SQL Statement Together      **********//
    SQLRun = SQLRun + SQLDetail + SQLExtra + SQLWhere + SQLOrder;
    String    saveSample = "";
    Statement stmt       = null;
    ResultSet rs         = null;
    int recordCount = 0;
	try
	{		 
		if (connection == null) 
		{
		   connection = ConnectionStack.getConnection();
		}
		stmt = connection.createStatement();
		rs = stmt.executeQuery(SQLRun);
		 
		try
		{
 		   	while (rs.next() && recordCount < 250 )
   		    {
	   		    if (!saveSample.equals(rs.getString("SRBNUMBER")))
				{
					saveSample = rs.getString("SRBNUMBER"); 
		   		    SampleRequestOrder buildVector = new SampleRequestOrder(rs);
 	 	    		returnSamples.addElement(buildVector);
				}
				recordCount++;
		    }
 		}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set " +
				              "(SampleRequestOrder.findSamples)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL " +
			               "(SampleRequestOrder.findSamples) " + e);
	}
	finally
	{
		if (connection != null)
		{
		   try
		   {
			ConnectionStack.returnConnection(connection);
			connection = null;
		   }
		   catch(Exception e)
		   {}
		}
	   // Close statement and result set
	   try{
	    stmt.close();	
	    rs.close();
	   }
	   catch(Exception e)
	   {
   		   //System.out.println("Problem closing rs in SampleRequestOrder.findSamples(): " + e);
	   }
	}
	
	return returnSamples;
	
}
/**
 * This method will return a vector of Sample Request Orders.
 *   Based on the Customer Number which is sent in.
 *
 * Creation date: (7/21/2003 1:01:29 PM)
 */
public Vector findSamplesbyCustomer(Integer custNumber) 						  
{

   	Vector returnSamples = new Vector();	

   	//build the sql statement	
	String SQLRun = "SELECT * " +
                    "FROM " + library + "SRPBHEADER " +
                    "WHERE SRBCUST = " + custNumber + " " +
                    "ORDER BY SRBNUMBER ";
                    
	//***********************************************//
	Statement stmt = null;
	ResultSet rs   = null;
	try
	{		 
		stmt = connection.createStatement();
		rs = stmt.executeQuery(SQLRun);
		try
		{
 		   	while (rs.next())
   		    {
	   		    SampleRequestOrder buildVector = new SampleRequestOrder(rs);
  	    		returnSamples.addElement(buildVector);
		    }
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set " +
				              "(SampleRequestOrder.findSamplesbyCustomer)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL " +
			               "(SampleRequestOrder.findSamplesbyCustomer) " + e);
	}         
	// Close statement and result set
	try{
	 stmt.close();	
	 rs.close();
	}
	catch(Exception e)
	{
		System.out.println("Problem closing rs in SampleRequestOrder.findSamplesbyCustomer(): " + e);
	} 	
	return returnSamples;
	
}
/**
 * This method will return a vector of Sample Request Orders.
 *   Based on the Formula Number which is sent in.
 *
 * Creation date: (7/21/2003 2:10:29 PM)
 */
public Vector findSamplesbyFormula(Integer formulaNumber) 						  
{
		
   	Vector returnSamples = new Vector();	

   	//build the sql statement	
	String SQLRun = "SELECT * " +
                    "FROM " + library + "SRPBHEADER " +
                    "INNER JOIN " + library + "SRPCDETAIL " + 
                    "ON SRBNUMBER = SRCNUMBER " +
                    "WHERE SRCFORMULA = " + formulaNumber + " " +
                    "ORDER BY SRBNUMBER ";
                    
	//***********************************************//
	Statement stmt = null;
	ResultSet rs   = null;

	try
	{		 
		stmt = connection.createStatement();
		rs = stmt.executeQuery(SQLRun);
		String saveSample = "";
		try
		{
 		   	while (rs.next())
   		    {
	   		    if(!saveSample.equals(rs.getString("SRBNUMBER")))
		 		{
			 		saveSample = rs.getString("SRBNUMBER"); 
				    SampleRequestOrder buildVector = new SampleRequestOrder(rs);
 	 	    		returnSamples.addElement(buildVector);
		 		}
		    }
 	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set " +
				              "(SampleRequestOrder.findSamplesbyFormula)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL " +
			               "(SampleRequestOrder.findSamplesbyFormula) " + e);
	}  
	// Close statement and result set
	try{
	 stmt.close();	
	 rs.close();
	}
	catch(Exception e)
	{
		System.out.println("Problem closing rs in SampleRequestOrder.findSamplesbyFormula(): " + e);
	}  
	
	return returnSamples;
	
}
/**
 * This method will return a vector of sample request orders,
 * based on the user type and profile.
 *
 * Creation date: (7/25/2003 1:01:29 PM)
 */
public Vector findSamplesbyUser(String inType, String inProfile) 						  
{
		
   	Vector returnSamples = new Vector();
   	String SQLRun = "";	

   	// Build the sql statement
   	if (inType.equals("sales"))	
	    SQLRun = "SELECT * " +
                 "FROM " + library + "SRPBHEADER " +
                 "WHERE SRBREP = '" + inProfile + "' " +
                 "ORDER BY SRBNUMBER ";
                        
     if (inType.equals("tech"))	
	    SQLRun = "SELECT * " +
                 "FROM " + library + "SRPBHEADER " +
                 "WHERE SRBTECH = '" + inProfile + "' " +
                 "ORDER BY SRBNUMBER ";      
                          
     if (inType.equals("received"))	
	    SQLRun = "SELECT * " +
                 "FROM " + library + "SRPBHEADER " +
                 "WHERE SRBRECREQ = '" + inProfile + "' " + 
                 "ORDER BY SRBNUMBER ";                    
                    
	//***********************************************//
	Statement stmt = null;
	ResultSet rs   = null;
	try
	{		 
		stmt = connection.createStatement();
		rs = stmt.executeQuery(SQLRun);
		try
		{
 		   	while (rs.next())
   		    {
	   		    SampleRequestOrder buildVector = new SampleRequestOrder(rs);
  	    		returnSamples.addElement(buildVector);
		    }
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while reading a result set " +
				              "(SampleRequestOrder.findSamplesbyUser)" + e);
		}
	}
	catch (Exception e)
	{
//		System.out.println("Exception on running SQL " +
//			               "(SampleRequestOrder.findSamplesbyUser) " + e);
	}  
	finally
	{
		// Close statement and result set
		try{
			stmt.close();	
		}
		catch(Exception e)
		{}
		try{
			rs.close();
		}
		catch(Exception e)
		{}
	}
	
	return returnSamples;
	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getAdditionalDescription() {

	return additionalDescription;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getApplication() {

	return application;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getAttnContact() {

	return attnContact.trim();	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getAttnContactEmail() {

	return attnContactEmail.trim();	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getAttnContactPhone() {

	return attnContactPhone.trim();	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public BigDecimal[] getBrixLevel() {

	return brixLevel;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public Integer[] getCheckBoxSeqNumber() {

	return checkBoxSeqNumber;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getCheckBoxValue20() {

	return checkBoxValue20;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getCheckBoxValue8() {

	return checkBoxValue8;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getChemicalAdditive() {

	return chemicalAdditive;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getColor() {

	return color;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getComment() {

	return comment;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public java.sql.Date[] getCommentDate() {

	return commentDate;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public Integer[] getCommentSeqNumber() {

	return commentSeqNumber;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public java.sql.Time[] getCommentTime() {

	return commentTime;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public BigDecimal[] getContainerSize() {

	return containerSize;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public java.sql.Date getCreateDate() {

	return createDate;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public java.sql.Time getCreateTime() {

	return createTime;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getCreateUser() {

	return createUser;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getCustContact() {

	return custContact;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getCustContactEmail() {

	return custContactEmail.trim();	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getCustContactPhone() {

	return custContactPhone;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public Integer getCustNumber() {

	return custNumber;	
}
/**
 * Insert the method's description here.
 * Creation date: (7/15/2003 10:45:28 AM)
 */
public String[] getCutSize() {

	return cutSize;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public java.sql.Date getDeliveryDate() {

	return deliveryDate;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getDescKey() {

	return descKey;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public Integer[] getDtlSeqNumber() {

	return dtlSeqNumber;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getEmailWhenShipped1() {

	return emailWhenShipped1;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getEmailWhenShipped2() {

	return emailWhenShipped2;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getEmailWhenShipped3() {

	return emailWhenShipped3;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getEmailWhenShipped4() {

	return emailWhenShipped4;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getEmailWhenShipped5() {

	return emailWhenShipped5;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getFlavor() {

	return flavor;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public Integer[] getFormulaNumber() {

	return formulaNumber;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getFruitType() {

	return fruitType;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getFruitVariety() {

	return fruitVariety;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getGlAccountMisc() {

	return glAccountMisc.trim();	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getGlAccountNumber() {

	return glAccountNumber;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getItemDescription() {

	return itemDescription;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getKeyCode() {

	return keyCode;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getKeyValue() {

	return keyValue;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getLocation() {

	return location;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getLotNumber() {

	return lotNumber;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getPreservative() {

	return preservative;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getProductGroup() {

	return productGroup;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getProductType() {

	return productType;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public BigDecimal[] getQuantity() {

	return quantity;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public java.sql.Date getReceivedDate() {

	return receivedDate;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public java.sql.Time getReceivedTime() {

	return receivedTime;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getRemark() {

	return remark;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public Integer[] getRemarkSeqNumber() {

	return remarkSeqNumber;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getResource() {

	return resource;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getSalesRep() {

	return salesRep;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public Integer getSampleNumber() {

	return sampleNumber;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getShipHow() {

	return shipHow;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getShippedFruitVariety() {

	return shippedFruitVariety;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getShippingCharge() {

	return shippingCharge;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public java.sql.Date getShippingDate() {

	return shippingDate;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getShipVia() {

	return shipVia;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getSpecNumber() {

	return specNumber;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getStatus() {

	return status;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getTechnician() {

	return technician;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public Integer getTerritory() {

	return territory;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getTrackingNumber() {

	return trackingNumber.trim();	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getType() {

	return type;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String[] getUnitOfMeasure() {

	return unitOfMeasure;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public java.sql.Date getUpdateDate() {

	return updateDate;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public java.sql.Time getUpdateTime() {

	return updateTime;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getUpdateUser() {

	return updateUser;	
}
/**
 * Y or N -- View the Lot number on printing.
 * Creation date: (12/16/2003 9:13:28 AM)
 */
public String getViewLot() {

	return viewLot;	
}
/**
 * Y or N -- View the Fruit Variety on printing.
 * Creation date: (12/16/2003 9:13:28 AM)
 */
public String getViewVariety() {

	return viewVariety;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getWhoReceivedRequest() {

	return whoReceivedRequest;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public String getWhoRequested() {

	return whoRequested;	
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void init() {
	
	// Test for initial connection.
	
	if (connection == null) 
	{
	    //connection = SQLConnect.connection;
		connection = ConnectionStack.getConnection();
	    this.persists = true;
	}

	try {
		if (dltHeaderSql == null)
		{
		dltHeaderSql = connection.prepareStatement(			
			"DELETE FROM " + library + "SRPBHEADER " +
			" WHERE SRBNUMBER = ?");
		dltDetailSql = connection.prepareStatement(
			"DELETE FROM " + library + "SRPCDETAIL " +
			" WHERE SRCNUMBER = ?");
		dltCommentSql = connection.prepareStatement(
			"DELETE FROM " + library + "SRPECOMENT " +
			" WHERE SRENUMBER = ?");
		dltCheckBoxSql = connection.prepareStatement(
			"DELETE FROM " + library + "SRPDCHECK " +
			" WHERE SRDNUMBER = ?");
		dltRemarkSql = connection.prepareStatement(
			"DELETE FROM " + library + "SRPFREMARK " +
			" WHERE SRFNUMBER = ?");

		addHeaderSql = connection.prepareStatement(
			"INSERT INTO " + library + "SRPBHEADER " +
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			         "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			         "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			         "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		addDetailSql = connection.prepareStatement(
			"INSERT INTO " + library + "SRPCDETAIL " +
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			         "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			         "?, ?)");
		
		addCommentSql = connection.prepareStatement(
			"INSERT INTO " + library + "SRPECOMENT " +
			" VALUES (?, ?, ?, ?, ?)");
		
		addCheckBoxSql = connection.prepareStatement(
			"INSERT INTO " + library + "SRPDCHECK " +
			" VALUES (?, ?, ?, ?, ?, ?, ?)");
		
		addRemarkSql = connection.prepareStatement(
			"INSERT INTO " + library + "SRPFREMARK " +
			" VALUES (?, ?, ?)"); 
			         
		updHeaderSql = connection.prepareStatement(
			"UPDATE " + library + "SRPBHEADER " +
			" SET SRBNUMBER  = ?,    SRBSTATUS = ?, " +
			" SRBTYPE = ?,           SRBAPP = ?, " +
			" SRBREP = ?,            SRBTECH = ?, " +
			" SRBMARKET = ?,         SRBRECDATE = ?, " +
			" SRBRECTIME = ?,        SRBDELDATE = ?, " +
			" SRBCUST = ?,           SRBCCONTCT = ?, " +
			" SRBCPHONE = ?,         SRBCEMAIL = ?, " +
			" SRBACONTCT = ?,        SRBAPHONE = ?, " +
			" SRBAEMAIL = ?,         SRBREQUEST = ?, " +
			" SRBRECREQ = ?,         SRBSHPCHG = ?, " +
			" SRBSHIPVIA = ?,        SRBSHIPHOW = ?, " +
			" SRBTRACK = ?,          SRBACCTNBR = ?, " +
			" SRBACCTMSC = ?,        SRBSHPDATE = ?, " +
			" SRBEMAIL1 = ?,         SRBEMAIL2 = ?, " +
			" SRBEMAIL3 = ?,         SRBEMAIL4 = ?, " +
			" SRBEMAIL5 = ?,         SRBCRTDATE = ?, " +
			" SRBCRTTIME = ?,        SRBCRTUSER = ?, " +
			" SRBUPDDATE = ?,        SRBUPDTIME = ?, " +
			" SRBUPDUSER = ?,        SRBLOC = ?,  " +
			" SRBVIEWLOT = ?,        SRBVIEWVAR = ? " +
			" WHERE SRBNUMBER = ?");

		updDetailSql = connection.prepareStatement(
			"UPDATE " + library + "SRPCDETAIL " +
			" SET SRCNUMBER  = ?,    SRCQTY = ?, " +
			" SRCCNTSIZE = ?,        SRCUOM = ?, " +
			" SRCPRDGRP = ?,         SRCPRDTYPE = ?, " +
			" SRCCUTSIZE = ?,        SRCCHEMADD = ?, " +
			" SRCFRUTVAR = ?,        SRCITMDESC = ?, " +
			" SRCADDDESC = ?,        SRCPRESERV = ?, " +
			" SRCRES = ?,            SRCLOT = ?,     " +
			" SRCFORMULA = ?,        SRCSPEC = ?,    " +
			" SRCSEQ = ?,            SRCSHIPFV = ?,  " +
			" SRCFRTTYPE = ?,        SRCBRIXLVL = ?, " +
			" SRCCOLOR = ?,          SRCFLAVOR = ?   " +
			" WHERE SRCNUMBER = ? AND SRCSEQ = ?" );

		updCommentSql = connection.prepareStatement(
			"UPDATE " + library + "SRPECOMENT " +
			" SET SRENUMBER  = ?,    SRECOMMENT = ?, " +
			" SREDATE = ?,           SRETIME = ?, " +
			" SRESEQ = ?                          " +
			" WHERE SRENUMBER = ? AND SRESEQ = ?");
		
		updCheckBoxSql = connection.prepareStatement(
			"UPDATE " + library + "SRPDCHECK " +
			" SET SRDNUMBER  = ?,    SRDDESCKEY = ?, " +
			" SRDKEYCODE = ?,        SRDKEYVAL = ?, " +
			" SRDDESC8 = ?,          SRDDESC20 = ?, " +
			" SRDSEQ = ?                            " +
			" WHERE SRDNUMBER = ? AND SRDSEQ = ?");

		updRemarkSql = connection.prepareStatement(
			"UPDATE " + library + "SRPFREMARK " +
			" SET SRFNUMBER = ?,     SRFREMARK = ?, " +
			" SRFSEQ = ?                            " +
			" WHERE SRFNUMBER = ? AND SRFSEQ = ?");
			
		findHdrBySampleNumberSql = connection.prepareStatement(
			"SELECT * FROM " + library + "SRPBHEADER " +
			" WHERE SRBNUMBER = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
			                        ResultSet.CONCUR_UPDATABLE);
		
		findDtlBySampleNumberSql = connection.prepareStatement(
			"SELECT * FROM " + library + "SRPCDETAIL " +
			" WHERE SRCNUMBER = ?");
		
		findChkBySampleNumberSql = connection.prepareStatement(
			"SELECT * FROM " + library + "SRPDCHECK " +
			" WHERE SRDNUMBER = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
			                        ResultSet.CONCUR_UPDATABLE);

		findCmtBySampleNumberSql = connection.prepareStatement(
			"SELECT * FROM " + library + "SRPECOMENT " +
			" WHERE SRENUMBER = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
			                        ResultSet.CONCUR_UPDATABLE);

		findRmkBySampleNumberSql = connection.prepareStatement(
			"SELECT * FROM " + library + "SRPFREMARK " +
			" WHERE SRFNUMBER = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
			                        ResultSet.CONCUR_UPDATABLE);
		}
		ConnectionStack.returnConnection(connection);
		connection = null;
		
	} catch (SQLException e) {
		System.out.println("SQL exception occured at com.treetop.data." + 
			               "SampleRequestOrder.init()" + e);
			               
	}
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
protected void loadFields(ResultSet rsHdr, 
	                      ResultSet rsDtl,
	                      ResultSet rsCmt,
	                      ResultSet rsChk,
	                      ResultSet rsRmk) 
{
	
	//header
	try {
		sampleNumber       = new Integer(rsHdr.getInt("SRBNUMBER"));
		status             = rsHdr.getString("SRBSTATUS");
		type               = rsHdr.getString("SRBTYPE");
		application        = rsHdr.getString("SRBAPP");
		salesRep           = rsHdr.getString("SRBREP");
		technician         = rsHdr.getString("SRBTECH");
		territory          = new Integer(rsHdr.getInt("SRBMARKET"));
		receivedDate       = rsHdr.getDate("SRBRECDATE");
		receivedTime       = rsHdr.getTime("SRBRECTIME");
		deliveryDate       = rsHdr.getDate("SRBDELDATE");
		custNumber         = new Integer(rsHdr.getInt("SRBCUST"));
		custContact        = rsHdr.getString("SRBCCONTCT");
		custContactPhone   = rsHdr.getString("SRBCPHONE");
		custContactEmail   = rsHdr.getString("SRBCEMAIL");
		attnContact        = rsHdr.getString("SRBACONTCT");
		attnContactPhone   = rsHdr.getString("SRBAPHONE");
		attnContactEmail   = rsHdr.getString("SRBAEMAIL");
		whoRequested       = rsHdr.getString("SRBREQUEST");
		whoReceivedRequest = rsHdr.getString("SRBRECREQ");
		shippingCharge     = rsHdr.getString("SRBSHPCHG");
		shipVia            = rsHdr.getString("SRBSHIPVIA");
		shipHow            = rsHdr.getString("SRBSHIPHOW");
		trackingNumber     = rsHdr.getString("SRBTRACK");
		glAccountNumber    = rsHdr.getString("SRBACCTNBR");
		glAccountMisc      = rsHdr.getString("SRBACCTMSC");
		shippingDate       = rsHdr.getDate("SRBSHPDATE");
		if (shippingDate == null)
			shippingDate = java.sql.Date.valueOf("1900-01-01");
		emailWhenShipped1  = rsHdr.getString("SRBEMAIL1");
		emailWhenShipped2  = rsHdr.getString("SRBEMAIL2");
		emailWhenShipped3  = rsHdr.getString("SRBEMAIL3");
		emailWhenShipped4  = rsHdr.getString("SRBEMAIL4");
		emailWhenShipped5  = rsHdr.getString("SRBEMAIL5");
		createDate         = rsHdr.getDate("SRBCRTDATE");
		createTime         = rsHdr.getTime("SRBCRTTIME");
		createUser         = rsHdr.getString("SRBCRTUSER");
		updateDate         = rsHdr.getDate("SRBUPDDATE");
		updateTime         = rsHdr.getTime("SRBUPDTIME");
		updateUser	       = rsHdr.getString("SRBUPDUSER");
		location           = rsHdr.getString("SRBLOC");
        viewLot            = rsHdr.getString("SRBVIEWLOT");
        viewVariety        = rsHdr.getString("SRBVIEWVAR");

		//detail
		try
		{
			BigDecimal[] tempQuanity               = new BigDecimal[100];
			BigDecimal[] tempContainerSize         = new BigDecimal[100];
			String[]     tempUnitOfMeasure         = new String [100];
			String[]     tempProductGroup          = new String [100];
	    	String[]     tempProductType           = new String [100];
	    	String[]     tempCutSize               = new String [100];
	    	String[]     tempChemicalAdditive      = new String [100];
	    	String[]     tempFruitVariety          = new String [100];
	    	String[]     tempItemDescription       = new String [100];
	    	String[]     tempAdditionalDescription = new String [100]; 
	    	String[]     tempPreservative          = new String [100];
	    	String[]     tempResource              = new String [100];
	    	String[]     tempLotNumber             = new String [100];
	    	Integer[] 	 tempFormulaNumber         = new Integer[100];
	    	String[]     tempSpecNumber            = new String [100];
	    	Integer[]    tempDtlSeqNumber          = new Integer[100];
	    	String[]     tempShippedFruitVariety   = new String [100];
	    	String[]     tempFruitType             = new String [100];
	    	BigDecimal[] tempBrixLevel             = new BigDecimal[100];
	    	String[]     tempColor                 = new String [100];
	    	String[]     tempFlavor                = new String [100];

	   		int x = 0;
	    	while (rsDtl.next())
			{
				tempQuanity[x]        = new BigDecimal(rsDtl.getString("SRCQTY"));
				tempContainerSize[x]  = new BigDecimal(rsDtl.getString("SRCCNTSIZE"));
				tempUnitOfMeasure[x]         = rsDtl.getString("SRCUOM");
				tempProductGroup[x]          = rsDtl.getString("SRCPRDGRP");
				tempProductType[x]           = rsDtl.getString("SRCPRDTYPE");
				tempCutSize[x]               = rsDtl.getString("SRCCUTSIZE");
				tempChemicalAdditive[x]      = rsDtl.getString("SRCCHEMADD");
				tempFruitVariety[x]          = rsDtl.getString("SRCFRUTVAR");
				tempItemDescription[x]       = rsDtl.getString("SRCITMDESC");
				tempAdditionalDescription[x] = rsDtl.getString("SRCADDDESC");
				tempPreservative[x]          = rsDtl.getString("SRCPRESERV");
				tempResource[x]              = rsDtl.getString("SRCRES");
				tempLotNumber[x]             = rsDtl.getString("SRCLOT");
				tempFormulaNumber[x]  = new Integer(rsDtl.getInt("SRCFORMULA"));
				tempSpecNumber[x]            = rsDtl.getString("SRCSPEC");
				tempDtlSeqNumber[x]   = new Integer(rsDtl.getInt("SRCSEQ"));
				tempShippedFruitVariety[x]   = rsDtl.getString("SRCSHIPFV");
				tempFruitType[x]             = rsDtl.getString("SRCFRTTYPE");
				tempBrixLevel[x]      = new BigDecimal(rsDtl.getString("SRCBRIXLVL"));
				tempColor[x]                 = rsDtl.getString("SRCCOLOR");
				tempFlavor[x]                = rsDtl.getString("SRCFLAVOR");
				x++;
			}
			

			quantity              = new BigDecimal[x];
			containerSize         = new BigDecimal[x];
			unitOfMeasure         = new String [x];
			productGroup          = new String [x];
	    	productType           = new String [x];
	    	cutSize               = new String [x];
	    	chemicalAdditive      = new String [x];
	    	fruitVariety          = new String [x];
	    	itemDescription       = new String [x];
	    	additionalDescription = new String [x]; 
	    	preservative          = new String [x];
	    	resource              = new String [x];
	    	lotNumber             = new String [x];
	    	formulaNumber         = new Integer[x];
	    	specNumber            = new String [x];
	    	dtlSeqNumber          = new Integer[x];
	    	shippedFruitVariety   = new String[x];
	    	fruitType             = new String[x];
	    	brixLevel             = new BigDecimal[x];
	    	color                 = new String[x];
	    	flavor                = new String[x];

			for (int i = 0; i < x; i++)
			{
				quantity[i]              = tempQuanity[i];
				containerSize[i]         = tempContainerSize[i];
				unitOfMeasure[i]         = tempUnitOfMeasure[i];
				productGroup[i]          = tempProductGroup[i];
				productType[i]           = tempProductType[i];
				cutSize[i]               = tempCutSize[i];
				chemicalAdditive[i]      = tempChemicalAdditive[i];
				fruitVariety[i]          = tempFruitVariety[i];
				itemDescription[i]       = tempItemDescription[i];
				additionalDescription[i] = tempAdditionalDescription[i];
				preservative[i]          = tempPreservative[i];
				resource[i]              = tempResource[i];
				lotNumber[i]             = tempLotNumber[i];
				formulaNumber[i]         = tempFormulaNumber[i];
				specNumber[i]            = tempSpecNumber[i];
				dtlSeqNumber[i]          = tempDtlSeqNumber[i];
				shippedFruitVariety[i]   = tempShippedFruitVariety[i];
				fruitType[i]             = tempFruitType[i];
				brixLevel[i]             = tempBrixLevel[i];
				color[i]                 = tempColor[i];
				flavor[i]                = tempFlavor[i];
			}

		} catch(Exception e)
		{
			System.out.println("Error at " +
				"com.treetop.data.SampleRequestOrder.loadFields() " +
				"--Detail Section--:" + e);
			if (sampleNumber != null)
			   System.out.println(" SAMPLE NUMBER: " + sampleNumber);
		}

		//comments
		try
		{
			String[] tempComment            = new String [100];
			java.sql.Date[] tempCommentDate = new java.sql.Date [100];
			java.sql.Time[] tempCommentTime = new java.sql.Time [100];
			Integer[] tempCommentSeqNumber  = new Integer[100];

			int x = 0; 
			while (rsCmt.next())
			{
				tempComment[x]        	  = rsCmt.getString("SRECOMMENT");
				tempCommentDate[x]        = rsCmt.getDate("SREDATE");
				tempCommentTime[x]        = rsCmt.getTime("SRETIME");
				tempCommentSeqNumber[x]   = new Integer(rsCmt.getInt("SRESEQ"));
				x++;
			}
			

			comment          = new String[x];
			commentDate      = new java.sql.Date[x];
			commentTime      = new java.sql.Time[x];
			commentSeqNumber = new Integer[x];

			for (int i = 0; i < x; i++)
			{
				comment[i]               = tempComment[i];
				commentDate[i]           = tempCommentDate[i];
				commentTime[i]           = tempCommentTime[i];
				commentSeqNumber[i]      = tempCommentSeqNumber[i];
			}
			
		} catch(Exception e)
		{
			System.out.println("Error at " +
				"com.treetop.data.SampleRequestOrder.loadFields() " +
				"--Comment Section--:" + e);
			if (sampleNumber != null)
			   System.out.println(" SAMPLE NUMBER: " + sampleNumber);
		}

		//check boxes
		try
		{
			String[] tempDescKey            = new String [100];
	    	String[] tempKeyCode            = new String [100];
	    	String[] tempKeyValue           = new String [100]; 
	    	String[] tempCheckBoxValue8     = new String [100];	
	    	String[] tempCheckBoxValue20    = new String [100];
	    	Integer[] tempCheckBoxSeqNumber = new Integer[100];

	    	int x = 0;
			while (rsChk.next())
			{
				tempDescKey[x]          = rsChk.getString("SRDDESCKEY");
				tempKeyCode[x]          = rsChk.getString("SRDKEYCODE");
				tempKeyValue[x]         = rsChk.getString("SRDKEYVAL");
				tempCheckBoxValue8[x]   = rsChk.getString("SRDDESC8");
				tempCheckBoxValue20[x]  = rsChk.getString("SRDDESC20");
				tempCheckBoxSeqNumber[x] = new Integer(rsChk.getInt("SRDSEQ"));
	    		x++;
			}
			

			descKey           = new String[x];
			keyCode           = new String[x];
			keyValue          = new String[x];
			checkBoxValue8    = new String[x];
			checkBoxValue20   = new String[x];
			checkBoxSeqNumber = new Integer[x];

			for (int i = 0; i < x; i++)
			{
				descKey[i]           = tempDescKey[i];
				keyCode[i]           = tempKeyCode[i];
				keyValue[i]          = tempKeyValue[i];
				checkBoxValue8[i]    = tempCheckBoxValue8[i];
				checkBoxValue20[i]   = tempCheckBoxValue20[i];
				checkBoxSeqNumber[i] = tempCheckBoxSeqNumber[i];
			}
			
		} catch(Exception e)
		{
			System.out.println("Error at " +
				"com.treetop.dataSampleRequestOrder.loadFields() " +
				"--CheckBox Section--:" + e);
			if (sampleNumber != null)
			   System.out.println(" SAMPLE NUMBER: " + sampleNumber);
		}

		//remarks
		try
		{			
			String[]  tempRemark           = new String [100];
        	Integer[] tempRemarkSeqNumber  = new Integer[100];
        	
			int x = 0;
			while (rsRmk.next())
			{
	    		tempRemark[x]          = rsRmk.getString("SRFREMARK");
	    		tempRemarkSeqNumber[x] = new Integer(rsRmk.getInt("SRFSEQ"));
        		x++;
			}
			

			remark          = new String[x];
			remarkSeqNumber = new Integer[x];

			for (int i = 0; i < x; i++)
			{
				remark[i]           = tempRemark[i];
				remarkSeqNumber[i]  = tempRemarkSeqNumber[i];
			}
			
		} catch(Exception e)
		{
			System.out.println("Error at " +
				"com.treetop.dataSampleRequestOrder.loadFields() " +
				"--Remark Section--:" + e);
			if (sampleNumber != null)
			   System.out.println(" SAMPLE NUMBER: " + sampleNumber);
		}		
	}
	catch (Exception e)
	{
		System.out.println("SQL Exception at " +
			"com.treetop.data.SampleRequestOrder.loadFields();" + e);
		if (sampleNumber != null)
		   System.out.println(" SAMPLE NUMBER: " + sampleNumber);
			
	}
				
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public static void main(String[] args) {

	// add a few paths.
	java.sql.Date theDate = java.sql.Date.valueOf("2003-01-17");
	java.sql.Time theTime = java.sql.Time.valueOf("12:13:14");
	
	if ("x" == "y")
	{
		try {
			Integer sampleNbr1 = new Integer(999991);
			Integer territory1 = new Integer(111);
			Integer customer1 = new Integer(11111);

			BigDecimal[] quanity1 = new BigDecimal[1];
			quanity1[0] = new BigDecimal("1");

			BigDecimal[] container1 = new BigDecimal[1];
			container1[0] = new BigDecimal("1");

			String[] uom1 = new String[1];
			uom1[0] = "lbs";

			String[] pg1 = new String[1];
			pg1[0] = "pg";

			String[] pt1 = new String[1];
			pt1[0] = "pt";

			String[] cs1 = new String[1];
			cs1[0] = "cs";

			String[] ca1 = new String[1];
			ca1[0] = "ca";

			String[] fv1 = new String[1];
			fv1[0] = "fv";

			String[] id1 = new String[1];
			id1[0] = "id";

			String[] ad1 = new String[1];
			ad1[0] = "ad";

			String[] p1 = new String[1];
			p1[0] = "p";

			String[] rs1 = new String[1];
			rs1[0] = "rs";

			String[] ln1 = new String[1];
			ln1[0] = "ln";

			Integer[] formula1 = new Integer[1];
			formula1[0] = new Integer(1);

			String[] sn1 = new String[1];
			sn1[0] = "sn";

			Integer[] dtlsq1 = new Integer[1];
			dtlsq1[0] = new Integer(1);

			String[] shipfv1 = new String[1];
			shipfv1[0] = "s1";

			String[] fruit1 = new String[1];
			fruit1[0] = "f1";

			BigDecimal[] brix1 = new BigDecimal[1];
			brix1[0] = new BigDecimal("91.1");

			String[] color1 = new String[1];
			color1[0] = "c1";

			String[] flavor1 = new String[1];
			flavor1[0] = "f1";

			String[] comment1 = new String[1];
			comment1[0] = "comment1";

			java.sql.Date[] cdate1 = new java.sql.Date[1];
			cdate1[0] = theDate;

			java.sql.Time[] ctime1 = new java.sql.Time[1];
			ctime1[0] = theTime;

			Integer[] cmtsq1 = new Integer[1];
			cmtsq1[0] = new Integer(1);

			String[] dk1 = new String[1];
			dk1[0] = "dk";

			String[] kc1 = new String[1];
			kc1[0] = "kc";

			String[] kv1 = new String[1];
			kv1[0] = "kv";

			String[] cba = new String[1];
			cba[0] = "cb8";

			String[] cbb = new String[1];
			cbb[0] = "cb20";

			Integer[] cbsq1 = new Integer[1];
			cbsq1[0] = new Integer(1);

			String[] rm1 = new String[1];
			rm1[0] = "remark";

			Integer[] rmksq1 = new Integer[1];
			rmksq1[0] = new Integer(1);

		SampleRequestOrder one = mainAddSampleRequestOrder(sampleNbr1, 
								 "open", "Dried", "application", "sales rep",
								 "tech-e", territory1, theDate, theTime, theDate, 
					customer1,
								 "contact name", "111-111-1111", "emailAdd@1",
								 "attn contact name", "111-111-1111", "emailAtt@1",
								 "who requested it", "whoRec it", "prepaid",
								 "ship via", "ship how", "tracking number",
								 "111-111-111-1111", "11111", theDate,
								 "email when shipped 1", "email when shipped 2",
								 "email when shipped 2", "email when shipped 4",
								 "email when shipped 5", theDate, theTime,
								 "crtuser1", theDate, theTime, "upduser1", "SP",
								 "Y", "Y", 
								 quanity1, container1, uom1, pg1, pt1, cs1, ca1, fv1,
								 id1, ad1, p1, rs1, ln1, formula1, sn1, dtlsq1,
								 shipfv1, fruit1, brix1, color1, flavor1,
								 comment1, cdate1, ctime1, cmtsq1, dk1, kc1, kv1,
								 cba, cbb, cbsq1, rm1, rmksq1
								 );

			System.out.println("one: " + one);

		} catch (Exception e) {
		System.out.println("error adding one com.treetop.data" +
			               ".SampleRequestOrder.main()" + e);
		}

		try {
			Integer sampleNbr2 = new Integer(999992);
			Integer territory2 = new Integer(222);
			Integer customer2 = new Integer(22222);

			BigDecimal[] quanity2 = new BigDecimal[2];
			quanity2[0] = new BigDecimal("1");
			quanity2[1] = new BigDecimal("2");

			BigDecimal[] container2 = new BigDecimal[2];
			container2[0] = new BigDecimal("1");
			container2[1] = new BigDecimal("2");

			String[] uom2 = new String[2];
			uom2[0] = "lbs";
			uom2[1] = "fs ";

			String[] pg2 = new String[2];
			pg2[0] = "pg";
			pg2[1] = "pg";

			String[] pt2 = new String[2];
			pt2[0] = "pt";
			pt2[1] = "pt";

			String[] cs2 = new String[2];
			cs2[0] = "cs";
			cs2[1] = "cs";

			String[] ca2 = new String[2];
			ca2[0] = "ca";
			ca2[1] = "ca";

			String[] fv2 = new String[2];
			fv2[0] = "fv";
			fv2[1] = "fv";

			String[] id2 = new String[2];
			id2[0] = "id";
			id2[1] = "id";

			String[] ad2 = new String[2];
			ad2[0] = "ad";
			ad2[1] = "ad";

			String[] p2 = new String[2];
			p2[0] = "p";
			p2[1] = "p";

			String[] rs2 = new String[2];
			rs2[0] = "rs";
			rs2[1] = "rs";

			String[] ln2 = new String[2];
			ln2[0] = "ln";
			ln2[1] = "ln";

			Integer[] formula2 = new Integer[2];
			formula2[0] = new Integer(1);
			formula2[1] = new Integer(2);

			String[] sn2 = new String[2];
			sn2[0] = "sn";
			sn2[1] = "sn";

			Integer[] dtlsq2 = new Integer[2];
			dtlsq2[0] = new Integer(1);
			dtlsq2[1] = new Integer(2);

			String[] shipfv2 = new String[2];
			shipfv2[0] = "s1";
			shipfv2[1] = "s2";

			String[] fruit2 = new String[2];
			fruit2[0] = "f1";
			fruit2[1] = "f2";

			BigDecimal[] brix2 = new BigDecimal[2];
			brix2[0] = new BigDecimal("92.1");
			brix2[1] = new BigDecimal("92.2");

			String[] color2 = new String[2];
			color2[0] = "c1";
			color2[1] = "c2";

			String[] flavor2 = new String[2];
			flavor2[0] = "f1";
			flavor2[1] = "f2";

			String[] comment2 = new String[2];
			comment2[0] = "comment2";
			comment2[1] = "comment2";

			java.sql.Date[] cdate2 = new java.sql.Date[2];
			cdate2[0] = theDate;
			cdate2[1] = theDate;

			java.sql.Time[] ctime2 = new java.sql.Time[2];
			ctime2[0] = theTime;
			ctime2[1] = theTime;

			Integer[] cmtsq2 = new Integer[2];
			cmtsq2[0] = new Integer(1);
			cmtsq2[1] = new Integer(2);

			String[] dk2 = new String[2];
			dk2[0] = "dk";
			dk2[1] = "dk";

			String[] kc2 = new String[2];
			kc2[0] = "kc";
			kc2[1] = "kc";

			String[] kv2 = new String[2];
			kv2[0] = "kv";
			kv2[1] = "kv";

			String[] cba = new String[2];
			cba[0] = "cb8";
			cba[1] = "cb8";

			String[] cbb = new String[2];
			cbb[0] = "cb20";
			cbb[1] = "cb20";

			Integer[] cbsq2 = new Integer[2];
			cbsq2[0] = new Integer(1);
			cbsq2[1] = new Integer(2);

			String[] rm2 = new String[2];
			rm2[0] = "remark";
			rm2[1] = "remark";

			Integer[] rmksq2 = new Integer[2];
			rmksq2[0] = new Integer(1);
			rmksq2[1] = new Integer(2);

		SampleRequestOrder two = mainAddSampleRequestOrder(sampleNbr2, 
								 "open", "Dried", "application", "sales rep",
								 "tech-e", territory2, theDate, theTime, theDate, 
					customer2,
								 "contact name", "222-222-2222", "emailAdd@2",
								 "attn contact name", "222-222-2222", "emailAtt@2",
								 "who requested it", "whoRec it", "prepaid",
								 "ship via", "ship how", "tracking number",
								 "222-222-222-2222", "22222", theDate,
								 "email when shipped 1", "email when shipped 2",
								 "email when shipped 2", "email when shipped 4",
								 "email when shipped 5", theDate, theTime,
								 "crtuser2", theDate, theTime, "upduser2", "RP",
								 "Y", "Y", 
								 quanity2, container2, uom2, pg2, pt2, cs2, ca2, fv2,
								 id2, ad2, p2, rs2, ln2, formula2, sn2, dtlsq2,
								 shipfv2, fruit2, brix2, color2, flavor2,
								 comment2, cdate2, ctime2, cmtsq2, dk2, kc2, kv2,
								 cba, cbb, cbsq2, rm2, rmksq2
								 );

			System.out.println("two: " + two);

		} catch (Exception e) {
		System.out.println("error adding two com.treetop.data" +
			               ".SampleRequestOrder.main()" + e);
		}

		try {
			Integer sampleNbr3 = new Integer(999993);
			Integer territory3 = new Integer(333);
			Integer customer3 = new Integer(33333);

			BigDecimal[] quanity3 = new BigDecimal[3];
			quanity3[0] = new BigDecimal("1");
			quanity3[1] = new BigDecimal("2");
			quanity3[2] = new BigDecimal("3");

			BigDecimal[] container3 = new BigDecimal[3];
			container3[0] = new BigDecimal("1");
			container3[1] = new BigDecimal("2");
			container3[2] = new BigDecimal("3");

			String[] uom3 = new String[3];
			uom3[0] = "lbs";
			uom3[1] = "fs ";
			uom3[2] = "gal";

			String[] pg3 = new String[3];
			pg3[0] = "pg";
			pg3[1] = "pg";
			pg3[2] = "pg";

			String[] pt3 = new String[3];
			pt3[0] = "pt";
			pt3[1] = "pt";
			pt3[2] = "pt";

			String[] cs3 = new String[3];
			cs3[0] = "cs";
			cs3[1] = "cs";
			cs3[2] = "cs";

			String[] ca3 = new String[3];
			ca3[0] = "ca";
			ca3[1] = "ca";
			ca3[2] = "ca";

			String[] fv3 = new String[3];
			fv3[0] = "fv";
			fv3[1] = "fv";
			fv3[2] = "fv";

			String[] id3 = new String[3];
			id3[0] = "id";
			id3[1] = "id";
			id3[2] = "id";

			String[] ad3 = new String[3];
			ad3[0] = "ad";
			ad3[1] = "ad";
			ad3[2] = "ad";

			String[] p3 = new String[3];
			p3[0] = "p";
			p3[1] = "p";
			p3[2] = "p";

			String[] rs3 = new String[3];
			rs3[0] = "rs";
			rs3[1] = "rs";
			rs3[2] = "rs";

			String[] ln3 = new String[3];
			ln3[0] = "ln";
			ln3[1] = "ln";
			ln3[2] = "ln";

			Integer[] formula3 = new Integer[3];
			formula3[0] = new Integer(1);
			formula3[1] = new Integer(2);
			formula3[2] = new Integer(3);

			String[] sn3 = new String[3];
			sn3[0] = "sn";
			sn3[1] = "sn";
			sn3[2] = "sn";

			Integer[] dtlsq3 = new Integer[3];
			dtlsq3[0] = new Integer(1);
			dtlsq3[1] = new Integer(2);
			dtlsq3[2] = new Integer(3);

			String[] shipfv3 = new String[3];
			shipfv3[0] = "s1";
			shipfv3[1] = "s2";
			shipfv3[2] = "s3";

			String[] fruit3 = new String[3];
			fruit3[0] = "f1";
			fruit3[1] = "f2";
			fruit3[2] = "f3";

			BigDecimal[] brix3 = new BigDecimal[3];
			brix3[0] = new BigDecimal("93.1");
			brix3[1] = new BigDecimal("93.2");
			brix3[2] = new BigDecimal("93.3");

			String[] color3 = new String[3];
			color3[0] = "c1";
			color3[1] = "c2";
			color3[2] = "c3";

			String[] flavor3 = new String[3];
			flavor3[0] = "f1";
			flavor3[1] = "f2";
			flavor3[2] = "f3";

			String[] comment3 = new String[3];
			comment3[0] = "comment3";
			comment3[1] = "comment3";
			comment3[2] = "comment3";

			java.sql.Date[] cdate3 = new java.sql.Date[3];
			cdate3[0] = theDate;
			cdate3[1] = theDate;
			cdate3[2] = theDate;

			java.sql.Time[] ctime3 = new java.sql.Time[3];
			ctime3[0] = theTime;
			ctime3[1] = theTime;
			ctime3[2] = theTime;

			Integer[] cmtsq3 = new Integer[3];
			cmtsq3[0] = new Integer(1);
			cmtsq3[1] = new Integer(2);
			cmtsq3[2] = new Integer(3);

			String[] dk3 = new String[3];
			dk3[0] = "dk";
			dk3[1] = "dk";
			dk3[2] = "dk";

			String[] kc3 = new String[3];
			kc3[0] = "kc";
			kc3[1] = "kc";
			kc3[2] = "kc";

			String[] kv3 = new String[3];
			kv3[0] = "kv";
			kv3[1] = "kv";
			kv3[2] = "kv";

			String[] cba = new String[3];
			cba[0] = "cb8";
			cba[1] = "cb8";
			cba[2] = "cb8";

			String[] cbb = new String[3];
			cbb[0] = "cb20";
			cbb[1] = "cb20";
			cbb[2] = "cb20";

			Integer[] cbsq3 = new Integer[3];
			cbsq3[0] = new Integer(1);
			cbsq3[1] = new Integer(2);
			cbsq3[2] = new Integer(3);

			String[] rm3 = new String[3];
			rm3[0] = "remark";
			rm3[1] = "remark";
			rm3[2] = "remark";

			Integer[] rmksq3 = new Integer[3];
			rmksq3[0] = new Integer(1);
			rmksq3[1] = new Integer(2);
			rmksq3[2] = new Integer(3);

		SampleRequestOrder three = mainAddSampleRequestOrder(sampleNbr3, 
								 "open", "Dried", "application", "sales rep",
								 "tech-e", territory3, theDate, theTime, theDate, 
					customer3,
								 "contact name", "333-333-333", "emailAdd@3",
								 "attn contact name", "333-333-3333", "emailAtt@3",
								 "who requested it", "whoRec it", "prepaid",
								 "ship via", "ship how", "tracking number",
								 "333-333-333-3333", "33333", theDate,
								 "email when shipped 1", "email when shipped 2",
								 "email when shipped 2", "email when shipped 4",
								 "email when shipped 5", theDate, theTime,
								 "crtuser3", theDate, theTime, "upduser3", "SP",
								 "Y", "Y", 
								 quanity3, container3, uom3, pg3, pt3, cs3, ca3, fv3,
								 id3, ad3, p3, rs3, ln3, formula3, sn3, dtlsq3,
								 shipfv3, fruit3, brix3, color3, flavor3,
								 comment3, cdate3, ctime3, cmtsq3, dk3, kc3, kv3,
								 cba, cbb, cbsq3, rm3, rmksq3
								 ); 					 
			System.out.println("three: " + three);

		} catch (Exception e) {
		System.out.println("error adding three com.treetop.data" +
			               ".SampleRequestOrder.main()" + e);
		}

	// find by sample number.
	Integer numberTwo = new Integer(999992);
	try {
		SampleRequestOrder two = new SampleRequestOrder(numberTwo);
		System.out.println("find two: " + two);
		// update a role.
		try {
			two.setStatus("changed");
		} catch (Exception e) {
			System.out.println("error updating two com.treetop.data" +
				               ".SampleRequestOrder.Main()" + e);      
		}

		two.update();

		//delete
		two.delete();
	} catch (Exception e) {
		System.out.println("error deleting two after update com.treetop.data" +
						   ".SampleRequestOrder.Main(). " + e);
	}

	// delete one and three.
	try {
		Integer numberOne = new Integer(999991);
		SampleRequestOrder one = new SampleRequestOrder(numberOne);
		one.delete();
	} catch (Exception e) {
		System.out.println("com.treetop,data.SampleRequestOrder.Main(). " + 
			               "delete problem with one: " + e);
	}

	try {
		Integer numberThree = new Integer(999993);
		SampleRequestOrder three = new SampleRequestOrder(numberThree);
		three.delete();
	} catch (Exception e) {
		System.out.println("com.treetop,data.SampleRequestOrder.Main(). " + 
			               "delete problem with three: " + e);
	}
	}
	
	if ("x" == "x")
	{
		int x = 0;
		x = nextSampleNumber();
		String stophere = "x";
	}
	}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
protected static SampleRequestOrder mainAddSampleRequestOrder(
										Integer 		sampleNumber,
										String 			status,
										String          type,
										String          application,
										String          salesRep,
										String          technician,
										Integer         territory,
										java.sql.Date   receivedDate,
										java.sql.Time   receivedTime,
										java.sql.Date   deliveryDate,
										Integer         custNumber,
										String          custContact,
										String          custContactPhone,
										String          custContactEmail,
										String          attnContact,
										String          attnContactPhone,
										String          attnContactEmail,
										String          whoRequested,
										String          whoReceivedRequest,
										String          shippingCharge,
										String          shipVia,
										String          shipHow,
										String          trackingNumber,
										String          glAccountNumber,
										String          glAccountMisc,
										java.sql.Date   shippingDate,
										String          emailWhenShipped1,
										String          emailWhenShipped2,
										String          emailWhenShipped3,
										String          emailWhenShipped4,
										String          emailWhenShipped5,
										java.sql.Date   createDate,
										java.sql.Time   createTime,
										String          createUser,
										java.sql.Date   updateDate,
										java.sql.Time   updateTime,
										String          updateUser,
										String          location,
										String          viewLot,
										String          viewVariety,
										BigDecimal[]    quantity,
										BigDecimal[]    containerSize,
										String[]        unitOfMeasure,
										String[]        productGroup,
										String[]        productType,
										String[]        cutSize,
										String[]        chemicalAdditive,
										String[]        fruitVariety,
										String[]        itemDescription,
										String[]        additionalDescription,
										String[]        preservative,
										String[]        resource,
										String[]        lotNumber,
										Integer[]       formulaNumber,
										String[]        specNumber,
										Integer[]       dtlSeqNumber,
										String[]        shippedFruitVariety,
										String[]        fruitType,
										BigDecimal[]    brixLevel,
										String[]        color,
										String[]        flavor,
										String[]        comment,
										java.sql.Date[] commentDate,
										java.sql.Time[] commentTime,
										Integer[]       commentSeqNumber,
										String[]        descKey,
										String[]        keyCode,
										String[]        keyValue,
										String[]        checkBoxValue8,
										String[]        checkBoxValue20,
										Integer[]       checkBoxSeqNumber,
										String[]        remark,
										Integer[]       remarkSeqNumber
										)
									 
	throws InvalidLengthException, Exception
{
	SampleRequestOrder newSampleRequestOrder = new SampleRequestOrder();
	newSampleRequestOrder.setSampleNumber(sampleNumber);
	newSampleRequestOrder.setStatus(status);
	newSampleRequestOrder.setType(type);
	newSampleRequestOrder.setApplication(application);
	newSampleRequestOrder.setSalesRep(salesRep);
	newSampleRequestOrder.setTechnician(technician);
	newSampleRequestOrder.setTerritory(territory);
	newSampleRequestOrder.setReceivedDate(receivedDate);
	newSampleRequestOrder.setReceivedTime(receivedTime);
	newSampleRequestOrder.setDeliveryDate(deliveryDate);
	newSampleRequestOrder.setCustNumber(custNumber);
	newSampleRequestOrder.setCustContact(custContact);
	newSampleRequestOrder.setCustContactPhone(custContactPhone);
	newSampleRequestOrder.setCustContactEmail(custContactEmail);
	newSampleRequestOrder.setAttnContact(attnContact);
	newSampleRequestOrder.setAttnContactPhone(attnContactPhone);
	newSampleRequestOrder.setAttnContactEmail(attnContactEmail);
	newSampleRequestOrder.setWhoRequested(whoRequested);
	newSampleRequestOrder.setWhoReceivedRequest(whoReceivedRequest);
	newSampleRequestOrder.setShippingCharge(shippingCharge);
	newSampleRequestOrder.setShipVia(shipVia);
	newSampleRequestOrder.setShipHow(shipHow);
	newSampleRequestOrder.setTrackingNumber(trackingNumber);
	newSampleRequestOrder.setGlAccountNumber(glAccountNumber);
	newSampleRequestOrder.setGlAccountMisc(glAccountMisc);
	newSampleRequestOrder.setShippingDate(shippingDate);
	newSampleRequestOrder.setEmailWhenShipped1(emailWhenShipped1);
	newSampleRequestOrder.setEmailWhenShipped2(emailWhenShipped2);
	newSampleRequestOrder.setEmailWhenShipped3(emailWhenShipped3);
	newSampleRequestOrder.setEmailWhenShipped4(emailWhenShipped4);
	newSampleRequestOrder.setEmailWhenShipped5(emailWhenShipped5);
	newSampleRequestOrder.setCreateDate(createDate);
	newSampleRequestOrder.setCreateTime(createTime);
	newSampleRequestOrder.setCreateUser(createUser);
	newSampleRequestOrder.setUpdateDate(updateDate);
	newSampleRequestOrder.setUpdateTime(updateTime);
	newSampleRequestOrder.setUpdateUser(updateUser);
	newSampleRequestOrder.setLocation(location);
	newSampleRequestOrder.setQuantity(quantity);
	newSampleRequestOrder.setContainerSize(containerSize);
	newSampleRequestOrder.setUnitOfMeasure(unitOfMeasure);
	newSampleRequestOrder.setProductGroup(productGroup);
	newSampleRequestOrder.setProductType(productType);
	newSampleRequestOrder.setCutSize(cutSize);
	newSampleRequestOrder.setChemicalAdditive(chemicalAdditive);
	newSampleRequestOrder.setFruitVariety(fruitVariety);
	newSampleRequestOrder.setItemDescription(itemDescription);
	newSampleRequestOrder.setAdditionalDescription(additionalDescription);
	newSampleRequestOrder.setPreservative(preservative);
	newSampleRequestOrder.setResource(resource);
	newSampleRequestOrder.setLotNumber(lotNumber);
	newSampleRequestOrder.setFormulaNumber(formulaNumber);
	newSampleRequestOrder.setSpecNumber(specNumber);
	newSampleRequestOrder.setDtlSeqNumber(dtlSeqNumber);
	newSampleRequestOrder.setShippedFruitVariety(shippedFruitVariety);
	newSampleRequestOrder.setFruitType(fruitType);
	newSampleRequestOrder.setBrixLevel(brixLevel);
	newSampleRequestOrder.setColor(color);
	newSampleRequestOrder.setFlavor(flavor);
	newSampleRequestOrder.setComment(comment);
	newSampleRequestOrder.setCommentDate(commentDate);
	newSampleRequestOrder.setCommentTime(commentTime);
	newSampleRequestOrder.setCommentSeqNumber(commentSeqNumber);
	newSampleRequestOrder.setDescKey(descKey);
	newSampleRequestOrder.setKeyCode(keyCode);
	newSampleRequestOrder.setKeyValue(keyValue);
	newSampleRequestOrder.setCheckBoxValue8(checkBoxValue8);
	newSampleRequestOrder.setCheckBoxValue20(checkBoxValue20);
	newSampleRequestOrder.setCheckBoxSeqNumber(checkBoxSeqNumber);
	newSampleRequestOrder.setRemark(remark);
	newSampleRequestOrder.setRemarkSeqNumber(remarkSeqNumber);
	
	
	newSampleRequestOrder.add();
	return newSampleRequestOrder;
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void newMethod() {}
/**
 * Return the next available sample number.
 * Creation date: (6/18/2003 8:24:29 AM)
 */
public static int nextSampleNumber() 
{

	AS400 as400 = null;
	
	try {
		// create a AS400 object
		as400 = ConnectionStack.getAS400Object();
		//AS400 as400 = new AS400("10.6.100.1", "DAUSER", "web230502");
		ProgramCall pgm = new ProgramCall(as400);

		ProgramParameter[] parmList = new ProgramParameter[1];
		parmList[0] = new ProgramParameter(100);
		pgm = new ProgramCall(as400, "/QSYS.LIB/MOVEX.LIB/CLSAMPNBR.PGM", parmList);

		if (pgm.run() != true)
		{
			return 0;
		} else {
			AS400PackedDecimal pd = new AS400PackedDecimal(9, 0);
			byte[] data = parmList[0].getOutputData();
			double dd = pd.toDouble(data, 0);
			int i = (int) dd;
			as400.disconnectService(AS400.COMMAND);
			return i;
		}

	} catch (Exception e) {
		return 0;
	}
			
	finally {
		if (as400 != null)
			ConnectionStack.returnAS400Object(as400);
	}

}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setAdditionalDescription(String[] additionalDescriptionIn) {

	this.additionalDescription = additionalDescriptionIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setApplication(String applicationIn) throws InvalidLengthException {

	if (applicationIn.length() > 20)
		throw new InvalidLengthException(
				"applicationIn", applicationIn.length(), 20);

	this.application =  applicationIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setAttnContact(String attnContactIn) throws InvalidLengthException {

	if (attnContactIn.length() > 30)
		throw new InvalidLengthException(
				"attnContactIn", attnContactIn.length(), 30);

	this.attnContact =  attnContactIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setAttnContactEmail(String attnContactEmailIn) 	
	throws InvalidLengthException {

	if (attnContactEmailIn.length() > 100)
		throw new InvalidLengthException(
				"attnContactEmailIn", attnContactEmailIn.length(), 100);

	this.attnContactEmail =  attnContactEmailIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setAttnContactPhone(String attnContactPhoneIn) 	
	throws InvalidLengthException {

	if (attnContactPhoneIn.length() > 30)
		throw new InvalidLengthException(
				"attnContactPhoneIn", attnContactPhoneIn.length(), 30);

	this.attnContactPhone =  attnContactPhoneIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setBrixLevel(BigDecimal[] brixLevelIn) {

	this.brixLevel =  brixLevelIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCheckBoxSeqNumber(Integer[] checkBoxSeqNumberIn) {

	this.checkBoxSeqNumber = checkBoxSeqNumberIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCheckBoxValue20(String[] checkBoxValue20In) {

	this.checkBoxValue20 = checkBoxValue20In;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCheckBoxValue8(String[] checkBoxValue8In) {

	this.checkBoxValue8 = checkBoxValue8In;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setChemicalAdditive(String[] chemicalAdditiveIn) {

	this.chemicalAdditive = chemicalAdditiveIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setColor(String[] colorIn) {

	this.color = colorIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setComment(String[] commentIn) {

	this.comment = commentIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCommentDate(java.sql.Date[] commentDateIn) {

	this.commentDate = commentDateIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCommentSeqNumber(Integer[] commentSeqNumberIn) {

	this.commentSeqNumber = commentSeqNumberIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCommentTime(java.sql.Time[] commentTimeIn) {

	this.commentTime = commentTimeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setContainerSize(BigDecimal[] containerSizeIn) {

	this.containerSize = containerSizeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCreateDate(java.sql.Date createDateIn) {

	this.createDate = createDateIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCreateTime(java.sql.Time createTimeIn) {

	this.createTime = createTimeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCreateUser(String createUserIn) throws InvalidLengthException {

	if (createUserIn.length() > 10)
		throw new InvalidLengthException(
				"createUserIn", createUserIn.length(), 10);

	this.createUser =  createUserIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCustContact(String custContactIn) throws InvalidLengthException {

	if (custContactIn.length() > 30)
		throw new InvalidLengthException(
				"custContactIn", custContactIn.length(), 30);

	this.custContact =  custContactIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCustContactEmail(String custContactEmailIn) 
	throws InvalidLengthException {

	if (custContactEmailIn.length() > 100)
		throw new InvalidLengthException(
				"custContactEmailIn", custContactEmailIn.length(), 100);

	this.custContactEmail =  custContactEmailIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCustContactPhone(String custContactPhoneIn) 
	throws InvalidLengthException {

	if (custContactPhoneIn.length() > 30)
		throw new InvalidLengthException(
				"custContactPhoneIn", custContactPhoneIn.length(), 30);

	this.custContactPhone =  custContactPhoneIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCustNumber(Integer custNumberIn) {

	this.custNumber = custNumberIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setCutSize(String[] cutSizeIn) {

	this.cutSize = cutSizeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setDeliveryDate(java.sql.Date deliveryDateIn) {

	this.deliveryDate = deliveryDateIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setDescKey(String[] descKeyIn) {

	this.descKey = descKeyIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setDtlSeqNumber(Integer[] dtlSeqNumberIn) {

	this.dtlSeqNumber = dtlSeqNumberIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setEmailWhenShipped1(String emailWhenShipped1In) 
	throws InvalidLengthException {

	if (emailWhenShipped1In.length() > 100)
		throw new InvalidLengthException(
				"emailWhenShipped1In", emailWhenShipped1In.length(), 100);

	this.emailWhenShipped1 =  emailWhenShipped1In;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setEmailWhenShipped2(String emailWhenShipped2In) 
	throws InvalidLengthException {

	if (emailWhenShipped2In.length() > 100)
		throw new InvalidLengthException(
				"emailWhenShipped2In", emailWhenShipped2In.length(), 100);

	this.emailWhenShipped2 =  emailWhenShipped2In;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setEmailWhenShipped3(String emailWhenShipped3In) 
	throws InvalidLengthException {

	if (emailWhenShipped3In.length() > 100)
		throw new InvalidLengthException(
				"emailWhenShipped3In", emailWhenShipped3In.length(), 100);

	this.emailWhenShipped3 =  emailWhenShipped3In;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setEmailWhenShipped4(String emailWhenShipped4In) 
	throws InvalidLengthException {

	if (emailWhenShipped4In.length() > 100)
		throw new InvalidLengthException(
				"emailWhenShipped4In", emailWhenShipped4In.length(), 100);

	this.emailWhenShipped4 =  emailWhenShipped4In;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setEmailWhenShipped5(String emailWhenShipped5In) 
	throws InvalidLengthException {

	if (emailWhenShipped5In.length() > 100)
		throw new InvalidLengthException(
				"emailWhenShipped5In", emailWhenShipped5In.length(), 100);

	this.emailWhenShipped5 =  emailWhenShipped5In;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setFlavor(String[] flavorIn) {

	this.flavor = flavorIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setFormulaNumber(Integer[] formulaNumberIn) {

	this.formulaNumber = formulaNumberIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setFruitType(String[] fruitTypeIn) {

	this.fruitType = fruitTypeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setFruitVariety(String[] fruitVarietyIn) {

	this.fruitVariety = fruitVarietyIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setGlAccountMisc(String glAccountMiscIn) 	
	throws InvalidLengthException {

	if (glAccountMiscIn.length() > 10)
		throw new InvalidLengthException(
				"glAccountMiscIn", glAccountMiscIn.length(), 10);

	this.glAccountMisc =  glAccountMiscIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setGlAccountNumber(String glAccountNumberIn) 	
	throws InvalidLengthException {

	if (glAccountNumberIn.length() > 36)
		throw new InvalidLengthException(
				"glAccountNumberIn", glAccountNumberIn.length(), 36);

	this.glAccountNumber =  glAccountNumberIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setItemDescription(String[] itemDescriptionIn) {

	this.itemDescription = itemDescriptionIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setKeyCode(String[] keyCodeIn) {

	this.keyCode = keyCodeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setKeyValue(String[] keyValueIn) {

	this.keyValue = keyValueIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setLocation(String locationIn) throws InvalidLengthException {

	if (locationIn.length() > 2)
		throw new InvalidLengthException(
				"location", locationIn.length(), 2);

	this.location = locationIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setLotNumber(String[] lotNumberIn) {

	this.lotNumber = lotNumberIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setPreservative(String[] preservativeIn) {

	this.preservative = preservativeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setProductGroup(String[] productGroupIn) {

	this.productGroup = productGroupIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setProductType(String[] productTypeIn) {

	this.productType = productTypeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setQuantity(BigDecimal[] quantityIn) {

	this.quantity =  quantityIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setReceivedDate(java.sql.Date receivedDateIn) {

	this.receivedDate = receivedDateIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setReceivedTime(java.sql.Time receivedTimeIn) {

	this.receivedTime = receivedTimeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setRemark(String[] remarkIn) {

	this.remark = remarkIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setRemarkSeqNumber(Integer[] remarkSeqNumberIn) {

	this.remarkSeqNumber =  remarkSeqNumberIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setResource(String[] resourceIn) {

	this.resource = resourceIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setSalesRep(String salesRepIn) throws InvalidLengthException {

	if (salesRepIn.length() > 10)
		throw new InvalidLengthException(
				"salesRepIn", salesRepIn.length(), 20);

	this.salesRep =  salesRepIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setSampleNumber(Integer sampleNumberIn) {

	this.sampleNumber =  sampleNumberIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setShipHow(String shipHowIn) 	
	throws InvalidLengthException {

	if (shipHowIn.length() > 20)
		throw new InvalidLengthException(
				"shipHowIn", shipHowIn.length(), 20);

	this.shipHow =  shipHowIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setShippedFruitVariety(String[] shippedFruitVarietyIn) {

	this.shippedFruitVariety = shippedFruitVarietyIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setShippingCharge(String shippingChargeIn) 	
	throws InvalidLengthException {

	this.shippingCharge =  shippingChargeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setShippingDate(java.sql.Date shippingDateIn) {

	this.shippingDate = shippingDateIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setShipVia(String shipViaIn) 	
	throws InvalidLengthException {

	if (shipViaIn.length() > 20)
		throw new InvalidLengthException(
				"shipViaIn", shipViaIn.length(), 20);

	this.shipVia =  shipViaIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setSpecNumber(String[] specNumberIn) {

	this.specNumber = specNumberIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setStatus(String statusIn) throws InvalidLengthException {

	if (statusIn.length() > 20)
		throw new InvalidLengthException(
				"statusIn", statusIn.length(), 20);

	this.status =  statusIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setTechnician(String technicianIn) throws InvalidLengthException {

	if (technicianIn.length() > 10)
		throw new InvalidLengthException(
				"technicianIn", technicianIn.length(), 10);

	this.technician =  technicianIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setTerritory(Integer territoryIn) {

	this.territory =  territoryIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setTrackingNumber(String trackingNumberIn) 	
	throws InvalidLengthException {

	if (trackingNumberIn.length() > 30)
		throw new InvalidLengthException(
				"trackingNumberIn", trackingNumberIn.length(), 30);

	this.trackingNumber =  trackingNumberIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setType(String typeIn) throws InvalidLengthException {

	if (typeIn.length() > 20)
		throw new InvalidLengthException(
				"typeIn", typeIn.length(), 20);

	this.type =  typeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setUnitOfMeasure(String[] unitOfMeasureIn) {

	this.unitOfMeasure = unitOfMeasureIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setUpdateDate(java.sql.Date updateDateIn) {

	this.updateDate = updateDateIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setUpdateTime(java.sql.Time updateTimeIn) {

	this.updateTime = updateTimeIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setUpdateUser(String updateUserIn) throws InvalidLengthException {

	if (updateUserIn.length() > 10)
		throw new InvalidLengthException(
				"updateUserIn", updateUserIn.length(), 10);

	this.updateUser =  updateUserIn;
}
/**
 * Y or N for if you can View the Lot number when Printing
 * Creation date: (12/16/2003 9:14:28 AM)
 */
public void setViewLot(String viewLotIn) throws InvalidLengthException {

	if (viewLotIn.length() > 1)
		throw new InvalidLengthException(
				"viewLot", viewLotIn.length(), 1);

	this.viewLot = viewLotIn;
}
/**
 * Y or N for if you can View the Fruit Variety when Printing
 * Creation date: (12/16/2003 9:14:28 AM)
 */
public void setViewVariety(String viewVarietyIn) throws InvalidLengthException {

	if (viewVarietyIn.length() > 1)
		throw new InvalidLengthException(
				"viewVariety", viewVarietyIn.length(), 1);

	this.viewVariety = viewVarietyIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setWhoReceivedRequest(String whoReceivedRequestIn) 	
	throws InvalidLengthException {

	if (whoReceivedRequestIn.length() > 10)
		throw new InvalidLengthException(
				"whoReceivedRequestIn", whoReceivedRequestIn.length(), 10);

	this.whoReceivedRequest =  whoReceivedRequestIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/28/2003 4:45:28 PM)
 */
public void setWhoRequested(String whoRequestedIn) 	throws InvalidLengthException {

	if (whoRequestedIn.length() > 30)
		throw new InvalidLengthException(
				"whoRequestedIn", whoRequestedIn.length(), 30);

	this.whoRequested =  whoRequestedIn;
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public String toString() {

	return new String(
		"sampleNumber: " + sampleNumber + "\n" +
		"status: " + status + "\n" +
		"type:  " + type + "\n" +
		"application:  " + application + "\n" +
		"salesRep:  " + salesRep + "\n" +
		"technician:  " + technician + "\n" +
		"territory:  " + territory + "\n" +
		"receivedDate:  " + receivedDate + "\n" +
		"receivedTime:  " + receivedTime + "\n" +
		"deliveryDate:  " + deliveryDate + "\n" +
		"custNumber:  " + custNumber + "\n" +
		"custContact:  " + custContact + "\n" +
		"custContactPhone:  " + custContactPhone + "\n" +
		"custContactEmail:  " + custContactEmail + "\n" +
		"attnContact:  " + attnContact + "\n" +
		"attnContactPhone:  " + attnContactPhone + "\n" +
		"attnContactEmail:  " + attnContactEmail + "\n" +
		"whoRequested:  " + whoRequested + "\n" +
		"whoReceivedRequest:  " + whoReceivedRequest + "\n" +
		"shippingCharge:  " + shippingCharge + "\n" +
		"shipVia:  " + shipVia + "\n" +
		"shipHow:  " + shipHow + "\n" +
		"trackingNumber:  " + trackingNumber + "\n" +
		"glAccountNumber:  " + glAccountNumber + "\n" +
		"glAccountMisc:  " + glAccountMisc + "\n" +
		"shippingDate:  " + shippingDate + "\n" +
		"emailWhenShipped1:  " + emailWhenShipped1 + "\n" +
		"emailWhenShipped2:  " + emailWhenShipped2 + "\n" +
		"emailWhenShipped3:  " + emailWhenShipped3 + "\n" +
		"emailWhenShipped4:  " + emailWhenShipped4 + "\n" +
		"emailWhenShipped5:  " + emailWhenShipped5 + "\n" +
		"createDate:  " + createDate + "\n" +
		"createTime:  " + createTime + "\n" +
		"createUser:  " + createUser + "\n" +
		"updateDate:  " + updateDate + "\n" +
		"updateTime:  " + updateTime + "\n" +
		"updateUser:  " + updateUser + "\n" +
		"location:    " + location + "\n" +
		"viewLot:     " + viewLot        + "\n" +
		"viewVariety: " + viewVariety + "\n" +		
		"quantity:  " + quantity + "\n" +
		"containerSize:  " + containerSize + "\n" +
		"unitOfMeasure:  " + unitOfMeasure + "\n" +
		"productGroup:  " + productGroup + "\n" +
		"productType:  " + productType + "\n" +
		"cutSize:  " + cutSize + "\n" +
		"chemicalAdditive:  " + chemicalAdditive + "\n" +
		"fruitVariety:  " + fruitVariety + "\n" +
		"itemDescription:  " + itemDescription + "\n" +
		"additionalDescription:  " + additionalDescription + "\n" +
		"preservative:  " + preservative + "\n" +
		"resource:  " + resource + "\n" +
		"lotNumber:  " + lotNumber + "\n" +
		"formulaNumber:  " + formulaNumber + "\n" +
		"specNumber:  " + specNumber + "\n" +
		"dtlSeqNumber:  " + dtlSeqNumber + "\n" +
		"shippedFruitVariety:  " + shippedFruitVariety + "\n" +
		"fruitType:  " + fruitType + "\n" +
		"brixLevel:  " + brixLevel + "\n" +
		"color:  " + color + "\n" +
		"flavor:  " + flavor + "\n" +
		"comment:  " + comment + "\n" +
		"commentDate:  " + commentDate + "\n" +
		"commentTime:  " + commentTime + "\n" +
		"commentSeqNumber:  " + commentSeqNumber + "\n" +
		"descKey:  " + descKey + "\n" +
		"keyCode:  " + keyCode + "\n" +
		"keyValue:  " + keyValue + "\n" +
		"checkBoxValue8:  " + checkBoxValue8 + "\n" +
		"checkBoxValue20:  " + checkBoxValue20 + "\n" +
		"checkBoxSeqNumber:  " + checkBoxSeqNumber + "\n" +
		"remark:  " + remark + "\n" +
		"remarkSeqNumber:  " + remarkSeqNumber + "\n");
}
/**
 * Insert the method's description here.
 * Creation date: (4/29/2003 8:24:29 AM)
 */
public void update() {

	try {
		//header
		updHeaderSql.setInt(1,     sampleNumber.intValue());
		updHeaderSql.setString(2,  status);
		updHeaderSql.setString(3,  type);
		updHeaderSql.setString(4,  application);
		updHeaderSql.setString(5,  salesRep);
		updHeaderSql.setString(6,  technician);
		updHeaderSql.setInt(7,     territory.intValue());
		updHeaderSql.setDate(8,    receivedDate);
		updHeaderSql.setTime(9,    receivedTime);
		updHeaderSql.setDate(10,   deliveryDate);
		updHeaderSql.setInt(11,    custNumber.intValue());
		updHeaderSql.setString(12, custContact);
		updHeaderSql.setString(13, custContactPhone);
		updHeaderSql.setString(14, custContactEmail);
		updHeaderSql.setString(15, attnContact);
		updHeaderSql.setString(16, attnContactPhone);
		updHeaderSql.setString(17, attnContactEmail);
		updHeaderSql.setString(18, whoRequested);
		updHeaderSql.setString(19, whoReceivedRequest);
		updHeaderSql.setString(20, shippingCharge);
		updHeaderSql.setString(21, shipVia);
		updHeaderSql.setString(22, shipHow);
		updHeaderSql.setString(23, trackingNumber);
		updHeaderSql.setString(24, glAccountNumber);
		updHeaderSql.setString(25, glAccountMisc);
		if (shippingDate == null)
		   shippingDate = java.sql.Date.valueOf("1900-01-01");
		updHeaderSql.setDate(26,   shippingDate);
		updHeaderSql.setString(27, emailWhenShipped1);
		updHeaderSql.setString(28, emailWhenShipped2);
		updHeaderSql.setString(29, emailWhenShipped3);
		updHeaderSql.setString(30, emailWhenShipped4);
		updHeaderSql.setString(31, emailWhenShipped5);
		updHeaderSql.setDate(32,   createDate);
		updHeaderSql.setTime(33,   createTime);
		updHeaderSql.setString(34, createUser);
		updHeaderSql.setDate(35,   updateDate);
		updHeaderSql.setTime(36,   updateTime);
		updHeaderSql.setString(37, updateUser);
		updHeaderSql.setString(38, location);
		updHeaderSql.setString(39, viewLot);
		updHeaderSql.setString(40, viewVariety);
		updHeaderSql.setInt(41,    sampleNumber.intValue());//where of sql
		updHeaderSql.executeUpdate();

		//detail
		try
		{
			int totalDetailLines = dtlSeqNumber.length;

			for (int x=0; x < totalDetailLines; x++)
			{
				updDetailSql.setInt(1,     sampleNumber.intValue());
				updDetailSql.setString(2,  (quantity[x] + ""));
				updDetailSql.setString(3,  (containerSize[x] + ""));
				updDetailSql.setString(4,  unitOfMeasure[x]);
				updDetailSql.setString(5,  productGroup[x]);
				updDetailSql.setString(6,  productType[x]);
				updDetailSql.setString(7,  cutSize[x]);
				updDetailSql.setString(8,  chemicalAdditive[x]);
				updDetailSql.setString(9,  fruitVariety[x]);
				updDetailSql.setString(10,  itemDescription[x]);
				updDetailSql.setString(11, additionalDescription[x]);
				updDetailSql.setString(12, preservative[x]);
				updDetailSql.setString(13, resource[x]);
				updDetailSql.setString(14, lotNumber[x]);
				updDetailSql.setInt(15,    formulaNumber[x].intValue());
				updDetailSql.setString(16, specNumber[x]);
				updDetailSql.setInt(17,    dtlSeqNumber[x].intValue());
				updDetailSql.setString(18, shippedFruitVariety[x]);
				updDetailSql.setString(19, fruitType[x]);
				updDetailSql.setString(20, (brixLevel[x] + ""));
				updDetailSql.setString(21, color[x]);
				updDetailSql.setString(22, flavor[x]);
				updDetailSql.setInt(23,    sampleNumber.intValue());   //where
				updDetailSql.setInt(24,    dtlSeqNumber[x].intValue());//sql
			}
		} catch (Exception e)
		{
			System.out.println("Error at " +
				"com.treetop.data.SampleRequestOrder.update() " +
				"--Detail Secion--:" + e);
		}

		//comments
		try
		{
			int totalCommentLines = commentSeqNumber.length;

			for (int x=0; x < totalCommentLines; x++)
			{
				updCommentSql.setInt(1,    sampleNumber.intValue());
				updCommentSql.setString(2, comment[x]);
				updCommentSql.setDate(3,   commentDate[x]);
				updCommentSql.setTime(4,   commentTime[x]);
				updCommentSql.setInt(5,    commentSeqNumber[x].intValue());
				updCommentSql.setInt(6,    sampleNumber.intValue());       //where
				updCommentSql.setInt(7,    commentSeqNumber[x].intValue());//sql
			}
		} catch (Exception e)
		{
			System.out.println("Error at " +
				"com.treetop.data.SampleRequestOrder.update() " +
				"--Comment Secion--:" + e);
		}

		//check boxes
		try
		{
			int totalCheckBoxLines = checkBoxValue20.length;

			for (int x=0; x < totalCheckBoxLines; x++)
			{
				updCheckBoxSql.setInt(1,    sampleNumber.intValue());
				updCheckBoxSql.setString(2, descKey[x]);
				updCheckBoxSql.setString(3, keyCode[x]);
				updCheckBoxSql.setString(4, keyValue[x]);
				updCheckBoxSql.setString(5, checkBoxValue8[x]);
				updCheckBoxSql.setString(6, checkBoxValue20[x]);
				updCheckBoxSql.setInt(7,    checkBoxSeqNumber[x].intValue());
				updCheckBoxSql.setInt(8,    sampleNumber.intValue());        //where
				updCheckBoxSql.setInt(9,    checkBoxSeqNumber[x].intValue());//sql 
			}
		} catch (Exception e)
		{
			System.out.println("Error at " +
				"com.treetop.data.SampleRequestOrder.update() " +
				"--CheckBox Section--:" + e);
		}

		//remarks
		try
		{
			int totalRemarkLines = remarkSeqNumber.length;

			for (int x=0; x < totalRemarkLines; x++)
			{
				updRemarkSql.setInt(1,    sampleNumber.intValue());
				updRemarkSql.setString(2, remark[x]);
				updRemarkSql.setInt(3,    remarkSeqNumber[x].intValue());
				updRemarkSql.setInt(4,    sampleNumber.intValue());      //where
				updRemarkSql.setInt(5,    remarkSeqNumber[x].intValue());//sql
			}
		} catch (Exception e)
		{
			System.out.println("Error at " +
				"com.treetop.data.SampleRequestOrder.update() " +
				"--Remark Secion--:" + e);
		}
		
	} catch (SQLException e) {
		System.out.println("Sql error at " +
			"com.treetop.data.SampleRequestOrder.update(): " + 
			"--Header Section--:" + e);
	}		
}
public Vector getListComments() {
	return listComments;
}
public void setListComments(Vector listComments) {
	this.listComments = listComments;
}
public Vector getListURLs() {
	return listURLs;
}
public void setListURLs(Vector listURLs) {
	this.listURLs = listURLs;
}
}
