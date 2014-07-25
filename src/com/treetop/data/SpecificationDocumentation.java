package com.treetop.data;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import javax.sql.*;
import com.treetop.*;
import com.treetop.utilities.*;
/**
 * This object contains documentation text, explaining the
 * specification standard for each manufactured lot of inventory.
 *
 * @author: David M Eisenheim
 * modified 01/07/2009 (access new AS400 conns and files)
 */
public class SpecificationDocumentation extends SpecificationSubject {
	
	// Data base fields. (IDPUSPTX)
	
	private   	String        	specificationCode;		// IDUSPC
	private		Integer			specificationDate;		// IDURVD
	private		Integer			textSubjectOrder;		// IDUORD
	private		BigDecimal		textLineSequence;		// IDULN#
	private		String			textAnalyticalCode;		// IDUCDE
	private		boolean			textHighlighted;		// IDUHLT
	private		String			textSpecification;		// IDUTXT
	private		String			specReferenceCode;		// IDUREF
	private		Integer			supersededDate;			// IDUSRV
	private		Integer			marketNumber;			// IDUBRK
	private		Integer			marketCustomer;			// IDUCUS

	
	// Define database environment (live or test) on the AS/400.	

	private static String library = "DBPRD."; 		// live environment
//	private static String library = "WKLIB."; 		// test environment


	// SQL prepared statements.

	private static Stack sqlDeleteText = null;
	private static Stack sqlInsertText = null;
	private static Stack sqlUpdateText = null;
	
	private static Stack findTextByCodeByDate = null;

	
	// Additional fields.
	
	private static boolean persists   = false;
	

	/**
	 * Execute an SQL statement to retrieve all documentation text lines for a single
	 * specification using the code, date, subject and text line number.
	 *
	 * Creation date: (4/04/2005 11:50:21 AM)
	 */
	public static Vector findTextByCodeByDate(String inCode, Integer inDate) {

		Vector                     textList = new Vector();
		SpecificationDocumentation text     = new SpecificationDocumentation();

		try {
			
			PreparedStatement textByCodeByDate = (PreparedStatement) 
												  SpecificationDocumentation.findTextByCodeByDate.pop();
			ResultSet rs = null;
		
			try {
				textByCodeByDate.setString(1, inCode.toUpperCase());
				textByCodeByDate.setInt(2, inDate.intValue());		
				rs = textByCodeByDate.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(SpecificationDocumentation.findTextByCodeByDate): " + e);
			}
		
			SpecificationDocumentation.findTextByCodeByDate.push(textByCodeByDate);        	

              
			try {
	        
				while (rs.next()) {			
				
					SpecificationDocumentation textLine = new SpecificationDocumentation();
					textLine.loadFields(rs);
					textList.addElement(textLine); 
				}
        
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set " +
								   "(SpecificationDocumentation.findTextByCodeByDate): " + e);
			}
			
			rs.close();            
        
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data.Specification" + 
							   "Documentation.findTextByCodeByDate(String Integer): " + e);
		}
		
		return textList;	                                     
	}
	
	/**
	 * Execute an SQL statement to retrieve all documentation text lines for a single
	 * specification using the code, date, subject and text line number.
	 *
	 * Creation date: (4/04/2005 11:50:21 AM)
	 */
	public static Vector findTextByCodeByDate(String inCode, String inDate) {

		Vector                     textList = new Vector();
		SpecificationDocumentation text     = new SpecificationDocumentation();

		try {
			
			PreparedStatement textByCodeByDate = (PreparedStatement) 
												  SpecificationDocumentation.findTextByCodeByDate.pop();
			ResultSet rs = null;
		
			try {
				Integer revisionDate = GetDate.formatSetDate(inDate);
				textByCodeByDate.setString(1, inCode.toUpperCase());
				textByCodeByDate.setInt(2, revisionDate.intValue());	
				rs = textByCodeByDate.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(SpecificationDocumentation.findTextByCodeByDate): " + e);
			}
		
			SpecificationDocumentation.findTextByCodeByDate.push(textByCodeByDate);        	

              
			try {
	        
				while (rs.next()) {			
				
					SpecificationDocumentation textLine = new SpecificationDocumentation();
					textLine.loadFields(rs);
					textList.addElement(textLine); 
				}
        
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set " +
								   "(SpecificationDocumentation.findTextByCodeByDate): " + e);
			}
			
			rs.close();            
        
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data.Specification" + 
							   "Documentation.findTextByCodeByDate(String Integer): " + e);
		}
		
		return textList;	                                     
	}

	/**
	 * Main for testing methods.
	 *
	 * Creation date: (3/31/2005 4:53:22 PM)
	 */
	public static void main(String[] args) {
		
		try {	
	
			String  code = "OATS6";
			Integer date = new Integer("20050325");
			Vector  list = SpecificationDocumentation.findTextByCodeByDate(code, date);
													 
			System.out.println("findTextByCodeByDate: " + code + " successfull");
		}
		catch (Exception e) {
			System.out.println("Error: SpecificationDocumentation." + 
                               "findTextByCodeByDate(String Integer): " + e);
		}
				
	}
	
	/**
	 * Instantiate the specification documentation subject.
	 * 
	 * Creation date: (3/31/2005 4:52:17 PM)
	 */
	public SpecificationDocumentation() {
		
		super();
		
		init();

	}
	
	/**
	 * Delete an specification documentation text line.
	 *
	 * Creation date: (4/06/2005 3:44:11 PM)
	 */
	private boolean delete(String inSpecificationCode, String inSpecificationDate,
						   String inSubjectCode, String inTextAnalyticalCode, 
						   Integer inTextLineSequence) {
						   	
		try {
	
			PreparedStatement deleteText = (PreparedStatement) sqlDeleteText.pop();
			
			Integer revisionDate = GetDate.formatSetDate(inSpecificationDate);
			deleteText.setString(1, inSpecificationCode);
			deleteText.setInt(2, revisionDate.intValue());
			deleteText.setString(3, inSubjectCode);
			deleteText.setString(4, inTextAnalyticalCode);
			deleteText.setInt(5, inTextLineSequence.intValue());	
			deleteText.executeUpdate();
			sqlDeleteText.push(deleteText);
			
			return true;
					
		} 

		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data.Specification" +
							   "Documentation.delete(String String String String Integer): " + e);
			return false;
		}	
	
	}
	
	/**
	 * Retrieve the market number.
	 *
	 * Creation date: (4/06/2005 3:17:08 PM)
	 */
	public Integer getMarketNumber() {
		
		return marketNumber;	
	}
	
	/**
	 * Retrieve the customer number portion of the market/customer combination.
	 *
	 * Creation date: (4/06/2005 3:17:48 PM)
	 */
	public Integer getMarketCustomer() {
		
		return marketCustomer;	
	}
	
	/**
	 * Retrieve the specification code.
	 *
	 * Creation date: (4/1/2005 4:47:24 PM)
	 */
	public String getSpecificationCode() {

		return specificationCode;
	}
	
	/**
	 * Retrieve the specification revision date.
	 *
	 * Creation date: (4/1/2005 4:48:16 PM)
	 */
	public String getSpecificationDate() {
		
		String dateFormated = GetDate.formatGetDate(specificationDate);
	
		return dateFormated;
	}
	
	/**
	 * Retrieve the referenced specification code.
	 *
	 * Creation date: (4/4/2005 10:29:13 AM)
	 */
	public String getSpecReferenceCode() {

		return specReferenceCode;
	}
	
	/**
	 * Retrieve the paragraph subject code.
	 *
	 * Creation date: (4/1/2005 4:52:20 PM)
	 */
	public String getSubjectCode() {

		return subjectCode;
	}
	
	/**
	 * Retrieve the deleted subject record switch.
	 *
	 * Creation date: (4/1/2005 4:53:05 PM)
	 */
	public boolean getSubjectDeleted() {

		return subjectDeleted;
	}
	
	/**
	 * Retrieve the paragraph subject description.
	 *
	 * Creation date: (4/01/2005 4:54:00 PM)
	 */
	public String getSubjectDescription() {

		return subjectDescription;
	}
	
	/**
	 * Retrieve the paragraph subject "order by" sequence number.
	 *
	 * Creation date: (4/01/2005 4:55:11 PM)
	 */
	public Integer getSubjectOrder() {
		
		return subjectOrder;	
	}
	
	/**
	 * Retrieve the sperseded specification date.
	 *
	 * Creation date: (4/4/2005 10:23:46 AM)
	 */
	public String getSupersededDate() {
		
		String dateFormated = GetDate.formatGetDate(supersededDate);
	
		return dateFormated;
	}
	
	/**
	 * Retrieve the specification analytical attribute code associated to the text line.
	 *
	 * Creation date: (4/04/2005 1:36:59 PM)
	 */
	public String getTextAnalyticalCode() {

		return textAnalyticalCode;
	}
	
	/**
	 * Retrieve the text highlighted switch.
	 *
	 * Creation date: (4/04/2005 10:11:04 AM)
	 */
	public boolean getTextHighlighted() {

		return textHighlighted;
	}
	
	/**
	 * Retrieve the text line sequence number.
	 *
	 * Creation date: (4/04/2005 9:49:08 AM)
	 */
	public BigDecimal getTextLineSequence() {
		
		return textLineSequence;	
	}
	
	/**
	 * Retrieve the specification text line.
	 *
	 * Creation date: (4/04/2005 9:59:00 AM)
	 */
	public String getTextSpecification() {

		return textSpecification;
	}
	
	/**
	 * Retrieve the text line subject order sequence number.
	 *
	 * Creation date: (4/04/2005 9:49:08 AM)
	 */
	public Integer getTextSubjectOrder() {
		
		return textSubjectOrder;	
	}
	
	/**
	 * SQL definitions.
	 *
	 * Creation date: (4/04/2005 10:40:28 AM)
	 */
	public void init() {	
	 
	
		// Test for prior initialization.
	
		if (persists == false) {	
			persists = true;	   
	    

		// Perform initialization.
	 
		try {

			Connection conn1 = ConnectionStack.getConnection();
			Connection conn2 = ConnectionStack.getConnection();
			Connection conn3 = ConnectionStack.getConnection();
			Connection conn4 = ConnectionStack.getConnection();
			Connection conn5 = ConnectionStack.getConnection();	
			Connection conn6 = ConnectionStack.getConnection();
			
			
			// SQL for data base changes --------		
			
			String deleteText = 
				  "DELETE FROM " + library + "IDPUSPTX " +
				  "WHERE IDUSPC = ? AND IDURVD = ? AND IDUSBJ = ? AND " +
				        "IDUCDE = ? AND IDULN# = ?";			 

			PreparedStatement deleteText1 = conn1.prepareStatement(deleteText);		
			PreparedStatement deleteText2 = conn2.prepareStatement(deleteText);		 
			PreparedStatement deleteText3 = conn3.prepareStatement(deleteText);		 
			PreparedStatement deleteText4 = conn4.prepareStatement(deleteText);		 
			PreparedStatement deleteText5 = conn5.prepareStatement(deleteText);	
			PreparedStatement deleteText6 = conn6.prepareStatement(deleteText);	 

			sqlDeleteText = new Stack();		
			sqlDeleteText.push(deleteText1);		
			sqlDeleteText.push(deleteText2);
			sqlDeleteText.push(deleteText3);
			sqlDeleteText.push(deleteText4);
			sqlDeleteText.push(deleteText5);
			sqlDeleteText.push(deleteText6);
			
			
			String insertText =     			
				  "INSERT INTO " + library + "IDPUSPTX " +
				  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement insertText1 = conn1.prepareStatement(insertText);
			PreparedStatement insertText2 = conn2.prepareStatement(insertText);
			PreparedStatement insertText3 = conn3.prepareStatement(insertText);
			PreparedStatement insertText4 = conn4.prepareStatement(insertText);
			PreparedStatement insertText5 = conn5.prepareStatement(insertText);
			PreparedStatement insertText6 = conn6.prepareStatement(insertText);

			sqlInsertText = new Stack();
			sqlInsertText.push(insertText1);
			sqlInsertText.push(insertText2);
			sqlInsertText.push(insertText3);
			sqlInsertText.push(insertText4);
			sqlInsertText.push(insertText5);
			sqlInsertText.push(insertText6);
			
			
			String updateText = 
				  "UPDATE " + library + "IDPUSPTX " +
				  "SET IDUREC = ?, IDUBRK = ?, IDUCUS = ?, IDUSPC = ?, IDUCDE = ?, " +
			          "IDUSBJ = ?, IDUORD = ?, IDULN# = ?, IDUTXT = ?, IDUHLT = ?, " +
			          "IDUREF = ?, IDURVD = ?, IDUSRV = ? " +
				  "WHERE IDUSPC = ? AND IDURVD = ? AND IDUSBJ = ? AND " +
				        "IDUCDE = ? AND IDULN# = ?";			 
			
			PreparedStatement updateText1 = conn1.prepareStatement(updateText);
			PreparedStatement updateText2 = conn2.prepareStatement(updateText);
			PreparedStatement updateText3 = conn3.prepareStatement(updateText);
			PreparedStatement updateText4 = conn4.prepareStatement(updateText);
			PreparedStatement updateText5 = conn5.prepareStatement(updateText);
			PreparedStatement updateText6 = conn6.prepareStatement(updateText);

			sqlUpdateText = new Stack();
			sqlUpdateText.push(updateText1);
			sqlUpdateText.push(updateText2);
			sqlUpdateText.push(updateText3);
			sqlUpdateText.push(updateText4);
			sqlUpdateText.push(updateText5);
			sqlUpdateText.push(updateText6);
			
			
			// SQL selection --------			

			String textByCodeByDate = 						
				  "SELECT *  " +
				  "FROM " + library + "IDPUSPTX " +
			"INNER JOIN " + library + "IDP3SPSJ " + 
						"ON IDUSBJ = ID3SBJ " +				
				  "WHERE IDUSPC = ? AND IDURVD = ? " +
				  "ORDER BY IDUSPC, IDURVD, IDUCDE, IDUORD, IDUSBJ, IDULN#";

			PreparedStatement textByCodeByDate1 = conn1.prepareStatement(textByCodeByDate);
			PreparedStatement textByCodeByDate2 = conn2.prepareStatement(textByCodeByDate);
			PreparedStatement textByCodeByDate3 = conn3.prepareStatement(textByCodeByDate);
			PreparedStatement textByCodeByDate4 = conn4.prepareStatement(textByCodeByDate);
			PreparedStatement textByCodeByDate5 = conn5.prepareStatement(textByCodeByDate);
			PreparedStatement textByCodeByDate6 = conn6.prepareStatement(textByCodeByDate);

			findTextByCodeByDate = new Stack();
			findTextByCodeByDate.push(textByCodeByDate1);
			findTextByCodeByDate.push(textByCodeByDate2);
			findTextByCodeByDate.push(textByCodeByDate3);
			findTextByCodeByDate.push(textByCodeByDate4);
			findTextByCodeByDate.push(textByCodeByDate5);
			findTextByCodeByDate.push(textByCodeByDate6);
			

			// Return the connections back to the pool.
		
			ConnectionStack.returnConnection(conn1);
			ConnectionStack.returnConnection(conn2);
			ConnectionStack.returnConnection(conn3);
			ConnectionStack.returnConnection(conn4);
			ConnectionStack.returnConnection(conn5);
			ConnectionStack.returnConnection(conn6);			
		
		}				

		catch (SQLException e) {
			System.out.println("SQL exception occured at com.treetop.data." + "" +
							   "SpecificationDocumentation.init()" + e);
		}    
    	
		}
	
	}
	
	/**
	 * Update an specification documentation text line.
	 *
	 * Creation date: (4/06/2005 3:39:17 AM)
	 */
	public Exception insert(Vector inInsertInfo) {
		
		try {	
					
			for (int x = 0; x < inInsertInfo.size(); x ++) {				  	
			 
				SpecificationDocumentation insertInfo = (SpecificationDocumentation) 
														 inInsertInfo.elementAt(x);
				                                      	  
				if ((insertInfo.specificationCode != null) && 		// Spec code required
				   (!insertInfo.specificationCode.equals(""))) {

					PreparedStatement insertText = (PreparedStatement) sqlInsertText.pop();
		
					insertText.setString(1, "ST");
					insertText.setInt(2, insertInfo.marketNumber.intValue());
					insertText.setInt(3, insertInfo.marketCustomer.intValue());
					insertText.setString(4, insertInfo.specificationCode);
					insertText.setString(5, insertInfo.textAnalyticalCode);
					insertText.setString(6, insertInfo.subjectCode);
					insertText.setInt(7, insertInfo.textSubjectOrder.intValue());
					insertText.setBigDecimal(8, insertInfo.textLineSequence);
					insertText.setString(9, insertInfo.textSpecification);
					
					if (insertInfo.textHighlighted)								
						insertText.setString(10, "Y");
					else
						insertText.setString(10, " ");
	
					insertText.setString(11, insertInfo.specReferenceCode);
					insertText.setInt(12, insertInfo.specificationDate.intValue());
					insertText.setInt(13, insertInfo.supersededDate.intValue());

					insertText.executeUpdate();

					sqlInsertText.push(insertText);		
				}
			}				
		}		
		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data." +
							   "SpecificationDocumentation.insert(Vector): " + e);
			return e;
		}
		
		return null;	
	}
	
	/**
	 * Load fields from data base record.
	 *
	 * Creation date: (4/4/2005 1:26:14 PM)
	 */
	protected void loadFields(ResultSet rs) {

		try {
			
			super.loadFields(rs);	
		
			specificationCode		= rs.getString("IDUSPC");		
			specificationDate  		= new Integer(rs.getInt("IDURVD"));
			textAnalyticalCode		= rs.getString("IDUCDE");
			textSubjectOrder		= new Integer(rs.getInt("IDUORD"));
			textLineSequence		= rs.getBigDecimal("IDULN#");
			textSpecification		= rs.getString("IDUTXT");
			specReferenceCode		= rs.getString("IDUREF");
			supersededDate			= new Integer(rs.getInt("IDUSRV"));
			marketNumber			= new Integer(rs.getInt("IDUBRK"));
			marketCustomer			= new Integer(rs.getInt("IDUCUS"));

			String highlighted		= rs.getString("IDUHLT");
			if (highlighted.equals ("H"))
				textHighlighted     = true;
			else
				textHighlighted		= false;
	
		}
		catch (Exception e) {	
			System.out.println("SQL Exception at com.treetop.data.SpecificationDocumentation" +
							   ".loadFields(RS): " + e);
		}
					
	}
	
	/**
	 * Set the customer number portion of the market/customer combination.
	 *
	 * Creation date: (4/06/2005 3:19:15 PM)
	 */
	public void setMarketCustomer(Integer inMarketCustomer) {
		
		this.marketCustomer = inMarketCustomer;
	}
	
	/**
	 * Set the market number.
	 *
	 * Creation date: (4/06/2005 3:19:55 PM)
	 */
	public void setMarketNumber(Integer inMarketNumber) {
		
		this.marketNumber = inMarketNumber;
	}
	
	/**
	 * Set the specification code.
	 *
	 * Creation date: (4/1/2005 4:49:23 PM)
	 */
	public void setSpecificationCode(String inSpecificationCode) throws InvalidLengthException {
	
		if (inSpecificationCode.length() > 5)
			throw new InvalidLengthException(
					"inSpecificationCode", inSpecificationCode.length(), 5);

		this.specificationCode = inSpecificationCode;
	}
	
	/**
	 * Set the specification revision date.
	 *
	 * Creation date: (4/1/2005 4:49:56 PM)
	 */
	public void setSpecificationDate(String inSpecificationDate) {
		
		this.specificationDate = GetDate.formatSetDate(inSpecificationDate);
	}
	
	/**
	 * Set the referenced specification code.
	 *
	 * Creation date: (4/4/2005 10:31:55 AM)
	 */
	public void setSpecReferenceCode(String inSpecReferenceCode) throws InvalidLengthException {
	
		if (inSpecReferenceCode.length() > 5)
			throw new InvalidLengthException(
					"inSpecReferenceCode", inSpecReferenceCode.length(), 5);

		this.specReferenceCode = inSpecReferenceCode;
	}
	
	/**
	 * Set the paragraph subject code.
	 *
	 * Creation date: (4/1/2005 4:56:11 PM)
	 */
	public void setSubjectCode(String inSubjectCode) throws InvalidLengthException {
	
		if (inSubjectCode.length() > 2)
			throw new InvalidLengthException(
					"inSubjectCode", inSubjectCode.length(), 2);

		this.subjectCode = inSubjectCode;
	}
	
	/**
	 * Set the deleted subject record switch.
	 *
	 * Creation date: (4/1/2005 4:56:52 PM)
	 */
	public void setSubjectDeleted(boolean inSubjectDeleted) {
		
		this.subjectDeleted = inSubjectDeleted;
	}
	
	/**
	 * Set the paragraph subject description.
	 *
	 * Creation date: (4/1/2005 4:57:22 PM)
	 */
	public void setSubjectDescription(String inSubjectDescription) throws InvalidLengthException {
	
		if (inSubjectDescription.length() > 30)
			throw new InvalidLengthException(
					"inSubjectDescription", inSubjectDescription.length(), 30);

		this.subjectDescription = inSubjectDescription;
	}
	
	/**
	 * Set the paragraph subject "order by" sequence number.
	 *
	 * Creation date: (4/1/2005 4:58:00 PM)
	 */
	public void setSubjectOrder(Integer inSubjectOrder) {
		
		this.subjectOrder = inSubjectOrder;
	}
	
	/**
	 * Set the superseded specification date.
	 *
	 * Creation date: (4/4/2005 10:27:41 AM)
	 */
	public void setSupersededDate(String inSupersededDate) {
		
		this.supersededDate = GetDate.formatSetDate(inSupersededDate);
	}
	
	/**
	 * Set the specification analytical attribute code associated to the text line.
	 *
	 * Creation date: (4/4/2005 1:38:15 PM)
	 */
	public void setTextAnalyticalCode(String inTextAnalyticalCode) throws InvalidLengthException {
	
		if (inTextAnalyticalCode.length() > 5)
			throw new InvalidLengthException(
					"inTextAnalyticalCode", inTextAnalyticalCode.length(), 5);

		this.textAnalyticalCode = inTextAnalyticalCode;
	}
	
	/**
	 * Set the text highlighted switch.
	 *
	 * Creation date: (4/4/2005 10:18:14 AM)
	 */
	public void setTextHighlighted(boolean inTextHighlighted) {
		
		this.textHighlighted = inTextHighlighted;
	}
	
	/**
	 * Set the text line sequence number.
	 *
	 * Creation date: (4/04/2005 10:02:49 AM)
	 */
	public BigDecimal setTextLineSequence() {
		
		return textLineSequence;	
	}
	
	/**
	 * Set the specification text line.
	 *
	 * Creation date: (4/04/2005 10:06:17 AM)
	 */
	public void setTextSpecification(String inTextSpecification) throws InvalidLengthException {
	
		if (inTextSpecification.length() > 72)
			throw new InvalidLengthException(
					"inTextSpecification", inTextSpecification.length(), 72);

		this.textSpecification = inTextSpecification;
	}
	
	/**
	 * Set the text line subject order sequence number.
	 *
	 * Creation date: (4/04/2005 10:02:49 AM)
	 */
	public Integer setTextSubjectOrder() {
		
		return textSubjectOrder;	
	}
	
	/**
	 * Update an specification documentation text line.
	 *
	 * Creation date: (4/06/2005 3:24:34 AM)
	 */
	public Exception update(Vector inUpdateInfo) {
		
		try {	
					
			for (int x = 0; x < inUpdateInfo.size(); x ++) {				  	
			 
				SpecificationDocumentation updateInfo = (SpecificationDocumentation) 
													     inUpdateInfo.elementAt(x);
				                                      	  
				if ((updateInfo.specificationCode != null) && 		// Spec code required
				   (!updateInfo.specificationCode.equals(""))) {

					PreparedStatement updateText = (PreparedStatement) sqlUpdateText.pop();
		
					updateText.setString(1, "ST");
					updateText.setInt(2, updateInfo.marketNumber.intValue());
					updateText.setInt(3, updateInfo.marketCustomer.intValue());
					updateText.setString(4, updateInfo.specificationCode);
					updateText.setString(5, updateInfo.textAnalyticalCode);
					updateText.setString(6, updateInfo.subjectCode);
					updateText.setInt(7, updateInfo.textSubjectOrder.intValue());
					updateText.setBigDecimal(8, updateInfo.textLineSequence);
					updateText.setString(9, updateInfo.textSpecification);
					
					if (updateInfo.textHighlighted)								
						updateText.setString(10, "Y");
					else
						updateText.setString(10, " ");
	
					updateText.setString(11, updateInfo.specReferenceCode);
					updateText.setInt(12, updateInfo.specificationDate.intValue());
					updateText.setInt(13, updateInfo.supersededDate.intValue());
													
					updateText.setString(14, updateInfo.specificationCode);
					updateText.setInt(15, updateInfo.specificationDate.intValue());
					updateText.setString(16, updateInfo.subjectCode);
					updateText.setString(17, updateInfo.textAnalyticalCode);
					updateText.setInt(18, updateInfo.textLineSequence.intValue());
					updateText.executeUpdate();

					sqlUpdateText.push(updateText);		
				}
			}				
		}		
		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data." +
							   "SpecificationDocumentation.update(Vector): " + e);
			return e;
		}
		
		return null;	
	}
	
}