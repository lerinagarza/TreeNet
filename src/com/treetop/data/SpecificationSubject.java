package com.treetop.data;

import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import javax.sql.*;
import com.treetop.*;
/**
 * This object contains paragraph subjects for organizing documentation text, which
 * explain the specification standard for each manufactured lot of inventory.
 *
 * @author: David M Eisenheim
 *
 */
public class SpecificationSubject {
	
	// Data base fields. (IDP3SPSJ)
	
	protected   	String        	subjectCode;			// ID3SBJ
	protected		String			subjectDescription;		// ID3DSC	
	protected		Integer			subjectOrder;			// ID3ORD
	protected		boolean			subjectDeleted;			// ID3DLT

	
	// Define database environment (live or test) on the AS/400.	

	private static String library = "DBLIB."; 		// live environment
//	private static String library = "WKLIB."; 		// test environment
	

	// SQL prepared statements.

	private static Stack sqlDeleteSubject = null;
	private static Stack sqlInsertSubject = null;
	private static Stack sqlUpdateSubject = null;
	
	private static Stack findSubjectByCode = null;
	private static Stack findSubjectByAll  = null;
	
	private static Stack findSubjectForCode = null;

	
	// Additional fields.
	
	private static boolean persists   = false;
	
	
	/**
	 * Build drop down list for specification documentation paragraph subjects.
	 *
	 * Creation date: (4/01/2005 11:21:15 AM)
	 */
	public static String buildDropDownSubject(String inSubject, String inListName, 
											  String inFirstSelect) {		
	     
		String dropDownList = "";
		String selected     = "";
		String selectOption = "";
		
		try {
			
			if ((inListName == null) || (inListName.trim().equals ("")))
				inListName = "companyList";
			
			if ((inFirstSelect == null) || (inFirstSelect.trim().equals ("")))
				selectOption = "Select an Entry--->:";
			else {
				if (inFirstSelect.trim().equals ("*all") ||
					inFirstSelect.trim().equals ("*All") ||
					inFirstSelect.trim().equals ("*ALL"))
					selectOption = inFirstSelect.trim();
				else 
					selectOption = inFirstSelect.trim();			 	
			}	    
	
			try {	
	
				ResultSet rs = findSubjectForCode();

				try {

					while (rs.next()) 
					{
						String code = rs.getString("IDUSBJ");
						String desc = rs.getString("ID3DSC");
						
						String data = code.trim();						
						for (int x = data.length(); x < 2; x++) {
							data = data + "&nbsp;";
						}						     		 

						if ((inSubject != null) && (inSubject.trim().equals (code.trim())))						
							selected = "' selected='selected'>";
						else
							selected = "'>";
		   		    
						dropDownList = dropDownList + "<option value='" + 
						code.trim() + selected +
						data.trim() + " " + desc.trim() + "&nbsp;";
					}
				
					if (!dropDownList.equals("")) {
					  
						if ((inFirstSelect != null) && (inFirstSelect.toLowerCase().equals ("none")))					
							dropDownList = "<select name='" + inListName.trim() + "'>" +
										   dropDownList + "</select>";					
						else					
							dropDownList = "<select name='" + inListName.trim() + "'>" +
										   "<option value='None' selected>" + selectOption +
										   dropDownList + "</select>";					  	 
					}
  	  		
				}
				catch (Exception e) {
					System.out.println("Exception error processing a result set " +
									   "(SpecificationSubject.buildDropDownSubject): " + e);
				}	
		
				rs.close();	
		 
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(SpecificationSubject.buildDropDownSubject): " + e);
			}
		
		}
		catch (Exception e) {
			System.out.println("Exception error at com.treetop.data.SpecificationSubject." + 
							   "buildDropDownSubject(String String String): " + e);
		}	
	
		return dropDownList;  
	
	}
	
	/**
	 * Execute an SQL statement to retrieve all document paragraph subject definitions.
	 *
	 * Creation date: (4/01/2005 4:25:59 PM)
	 */
	public static Vector findSubjectByAll() {

		Vector               subjectList = new Vector();
		SpecificationSubject subject     = new SpecificationSubject();

		try {
			
			PreparedStatement subjectByAll = (PreparedStatement) 
											  SpecificationSubject.findSubjectByAll.pop();
			ResultSet rs = null;
		
			try {				
								
				rs = subjectByAll.executeQuery();
				
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(SpecificationSubject.findSubjectByAll): " + e);
			}
		
			SpecificationSubject.findSubjectByAll.push(subjectByAll);        	

              
			try {
	        
				while (rs.next()) {			
				
					SpecificationSubject subjectInfo = new SpecificationSubject();
					subjectInfo.loadFields(rs);
					subjectList.addElement(subjectInfo); 
				}
        
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set " +
								   "(SpecificationSubject.findSubjectByAll): " + e);
			}
			
			rs.close();            
        
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "SpecificationSubject.findSubjectByAll(): " + e);
		}
		
		return subjectList;	                                     
	}
	
	/**
	 * Execute an SQL statement to retrieve one document paragraph definition 
	 * using the subject code.
	 *
	 * Creation date: (4/01/2005 3:59:24 PM)
	 */
	public static Vector findSubjectByCode(String inCode) {

		Vector               subjectList = new Vector();
		SpecificationSubject subject     = new SpecificationSubject();

		try {
			
			PreparedStatement subjectByCode = (PreparedStatement) 
											   SpecificationSubject.findSubjectByCode.pop();
			ResultSet rs = null;
		
			try {
				
				subjectByCode.setString(1, inCode.toUpperCase());						
				rs = subjectByCode.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(SpecificationSubject.findSubjectByCode): " + e);
			}
		
			SpecificationSubject.findSubjectByCode.push(subjectByCode);        	

              
			try {
	        
				while (rs.next()) {			
				
					SpecificationSubject subjectInfo = new SpecificationSubject();
					subjectInfo.loadFields(rs);
					subjectList.addElement(subjectInfo); 
				}
        
			}
			catch (Exception e) {
				System.out.println("Exception error processing a result set " +
								   "(SpecificationSubject.findSubjectByCode): " + e);
			}
			
			rs.close();            
        
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "SpecificationSubject.findSubjectByCode(String): " + e);
		}
		
		return subjectList;	                                     
	}

	/**
	 * Execute an SQL statement to retrieve each unique subject code used
	 * in the specification documentation text.
	 *
	 * Creation date: (4/01/2005 10:58:31 AM)
	 */
	public static ResultSet findSubjectForCode() {

		ResultSet            subjectList = null;
		SpecificationSubject subject     = new SpecificationSubject();

		try {
			
			PreparedStatement specForSubject = (PreparedStatement) 
												SpecificationSubject.findSubjectForCode.pop();			
		
			try {
			
				subjectList = specForSubject.executeQuery();
			}
			catch (Exception e) {
				System.out.println("Exception error creating a result set " +
								   "(SpecificationSubject.findSubjectForCode): " + e);
				try {
					
					subjectList.close();
				}
				catch (Exception x) {
				}
			}
		
			SpecificationSubject.findSubjectForCode.push(specForSubject);
 
		}
		catch (Exception e) {
			System.out.println("Exception error processing SQL at com.treetop.data." + 
							   "SpecificationSubject.findSubjectForCode(): " + e);
		}
		
		return subjectList;	                                     
	}
	
	/**
	 * Main for testing methods.
	 *
	 * Creation date: (3/31/2005 4:12:43 PM)
	 */
	public static void main(String[] args) {
		
		try {		

			Vector list = SpecificationSubject.findSubjectByAll();
													 
			System.out.println("findSubjectByAll successfull");
		}
		catch (Exception e) {
			System.out.println("Error: SpecificationSubject.findSubjectByAll(): " + e);
		}
		
		
		try {	
	
			String code = "XX";
			Vector list = SpecificationSubject.findSubjectByCode(code);
													 
			System.out.println("findSubjectByCode: " + code + " successfull");
		}
		catch (Exception e) {
			System.out.println("Error: SpecificationSubject.findSubjectByCode(String): " + e);
		}
		

		try {

			String data    = "      CC  ";
			String name    = "subjects";
			String select  = "pick from this list:  ";
			String list = SpecificationSubject.buildDropDownSubject(data, name, select);
													 
			System.out.println("buildDropDownSubject successfull");
		}
		catch (Exception e) {
			System.out.println("Error: buildDropDownSubject: " + e);
		}
	}

	/**
	 * Instantiate the specification text documentation.
	 * 
	 * Creation date: (3/31/2005 4:14:48 PM)
	 */
	public SpecificationSubject() {
		
		super();
		
		init();

	}
	
	/**
	 * Delete a document paragraph subject.
	 *
	 * Creation date: (4/01/2005 3:04:14 PM)
	 */
	private boolean delete(String inSubjectCode) {

		try {
	
			PreparedStatement deleteSpec = (PreparedStatement) sqlDeleteSubject.pop();
			
			deleteSpec.setString(1, inSubjectCode);
			
			try {
				deleteSpec.executeUpdate();
			}
			catch (Exception e) {
				System.out.println("Exception at com.treetop.data.Specification" +
								   "Subject.update: " + inSubjectCode + ", Error: " + e);	
			}					
						
			sqlDeleteSubject.push(deleteSpec);
			
			return true;
					
		} 

		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data." +
							   "SpecificationSubject.delete(String): " + e);
			return false;
		}	
	
	}
	
	/**
	 * Retrieve the paragraph subject code.
	 *
	 * Creation date: (4/01/2005 1:30:23 PM)
	 */
	public String getSubjectCode() {

		return subjectCode;
	}
	
	/**
	 * Retrieve the deleted record switch.
	 *
	 * Creation date: (4/01/2005 1:34:05 PM)
	 */
	public boolean getSubjectDeleted() {

		return subjectDeleted;
	}
	
	/**
	 * Retrieve the paragraph subject description.
	 *
	 * Creation date: (4/01/2005 1:32:11 PM)
	 */
	public String getSubjectDescription() {

		return subjectDescription;
	}
	
	/**
	 * Retrieve the paragraph subject "order by" sequence number.
	 *
	 * Creation date: (4/01/2005 1:39:38 PM)
	 */
	public Integer getSubjectOrder() {
		
		return subjectOrder;	
	}
	
	/**
	 * SQL definitions.
	 *
	 * Creation date: (4/01/2005 10:27:21 AM)
	 */
	public void init() {	
	 
	
		// Test for prior initialization.
	
		if (persists == false) {	
			persists = true;	   
	    

		// Perform initialization.
	 
		try {

			Connection conn1 = null;
			Connection conn2 = null;
			Connection conn3 = null;
			Connection conn4 = null;
			Connection conn5 = null;	
			Connection conn6 = null;
			
			
			// SQL for data base changes --------		
			
			String deleteSubject = 
				  "DELETE FROM " + library + "IDP3SPSJ " +
				  "WHERE ID3SBJ = ?";			 

			PreparedStatement deleteSubject1 = conn1.prepareStatement(deleteSubject);		
			PreparedStatement deleteSubject2 = conn2.prepareStatement(deleteSubject);		 
			PreparedStatement deleteSubject3 = conn3.prepareStatement(deleteSubject);		 
			PreparedStatement deleteSubject4 = conn4.prepareStatement(deleteSubject);		 
			PreparedStatement deleteSubject5 = conn5.prepareStatement(deleteSubject);	
			PreparedStatement deleteSubject6 = conn6.prepareStatement(deleteSubject);	 

			sqlDeleteSubject = new Stack();		
			sqlDeleteSubject.push(deleteSubject1);		
			sqlDeleteSubject.push(deleteSubject2);
			sqlDeleteSubject.push(deleteSubject3);
			sqlDeleteSubject.push(deleteSubject4);
			sqlDeleteSubject.push(deleteSubject5);
			sqlDeleteSubject.push(deleteSubject6);
			
			
			String insertSubject =     			
				  "INSERT INTO " + library + "IDP3SPSJ " +
				  "VALUES (?, ?, ?, ?, ?)";

			PreparedStatement insertSubject1 = conn1.prepareStatement(insertSubject);
			PreparedStatement insertSubject2 = conn2.prepareStatement(insertSubject);
			PreparedStatement insertSubject3 = conn3.prepareStatement(insertSubject);
			PreparedStatement insertSubject4 = conn4.prepareStatement(insertSubject);
			PreparedStatement insertSubject5 = conn5.prepareStatement(insertSubject);
			PreparedStatement insertSubject6 = conn6.prepareStatement(insertSubject);

			sqlInsertSubject = new Stack();
			sqlInsertSubject.push(insertSubject1);
			sqlInsertSubject.push(insertSubject2);
			sqlInsertSubject.push(insertSubject3);
			sqlInsertSubject.push(insertSubject4);
			sqlInsertSubject.push(insertSubject5);
			sqlInsertSubject.push(insertSubject6);
			
			
			String updateSubject = 
				  "UPDATE " + library + "IDP3SPSJ " +
				  "SET ID3REC = ?, ID3DLT = ?, ID3SBJ = ?, ID3ORD = ?, ID3DSC = ? " +
				  "WHERE ID3SBJ = ?";
			
			PreparedStatement updateSubject1 = conn1.prepareStatement(updateSubject);
			PreparedStatement updateSubject2 = conn2.prepareStatement(updateSubject);
			PreparedStatement updateSubject3 = conn3.prepareStatement(updateSubject);
			PreparedStatement updateSubject4 = conn4.prepareStatement(updateSubject);
			PreparedStatement updateSubject5 = conn5.prepareStatement(updateSubject);
			PreparedStatement updateSubject6 = conn6.prepareStatement(updateSubject);

			sqlUpdateSubject = new Stack();
			sqlUpdateSubject.push(updateSubject1);
			sqlUpdateSubject.push(updateSubject2);
			sqlUpdateSubject.push(updateSubject3);
			sqlUpdateSubject.push(updateSubject4);
			sqlUpdateSubject.push(updateSubject5);
			sqlUpdateSubject.push(updateSubject6);
			
			
			// SQL selection --------			

			String subjectByCode = 						
				  "SELECT *  " +
				  "FROM " + library + "IDP3SPSJ " +					
				  "WHERE ID3SBJ = ? " +
				  "ORDER BY ID3SBJ";

			PreparedStatement subjectByCode1 = conn1.prepareStatement(subjectByCode);
			PreparedStatement subjectByCode2 = conn2.prepareStatement(subjectByCode);
			PreparedStatement subjectByCode3 = conn3.prepareStatement(subjectByCode);
			PreparedStatement subjectByCode4 = conn4.prepareStatement(subjectByCode);
			PreparedStatement subjectByCode5 = conn5.prepareStatement(subjectByCode);
			PreparedStatement subjectByCode6 = conn6.prepareStatement(subjectByCode);

			findSubjectByCode = new Stack();
			findSubjectByCode.push(subjectByCode1);
			findSubjectByCode.push(subjectByCode2);
			findSubjectByCode.push(subjectByCode3);
			findSubjectByCode.push(subjectByCode4);
			findSubjectByCode.push(subjectByCode5);
			findSubjectByCode.push(subjectByCode6);

			String subjectByAll = 						
				  "SELECT *  " +
				  "FROM " + library + "IDP3SPSJ " +	
				  "ORDER BY ID3SBJ";

			PreparedStatement subjectByAll1 = conn1.prepareStatement(subjectByAll);
			PreparedStatement subjectByAll2 = conn2.prepareStatement(subjectByAll);
			PreparedStatement subjectByAll3 = conn3.prepareStatement(subjectByAll);
			PreparedStatement subjectByAll4 = conn4.prepareStatement(subjectByAll);
			PreparedStatement subjectByAll5 = conn5.prepareStatement(subjectByAll);
			PreparedStatement subjectByAll6 = conn6.prepareStatement(subjectByAll);

			findSubjectByAll = new Stack();
			findSubjectByAll.push(subjectByAll1);
			findSubjectByAll.push(subjectByAll2);
			findSubjectByAll.push(subjectByAll3);
			findSubjectByAll.push(subjectByAll4);
			findSubjectByAll.push(subjectByAll5);
			findSubjectByAll.push(subjectByAll6);

			
			// SQL selection (drop down lists) --------
			
			String subjectForCode =
				   "SELECT IDUSBJ, ID3DSC " + 
				   "FROM " + library + "IDPUSPTX " +
				   "JOIN " + library + "IDP3SPSJ " +
						 "ON IDUSBJ = ID3SBJ " +						  
				   "GROUP BY IDUSBJ, ID3DSC " +                         
				   "ORDER BY IDUSBJ, ID3DSC ";
						  
			PreparedStatement subjectForCode1 = conn1.prepareStatement(subjectForCode);
			PreparedStatement subjectForCode2 = conn2.prepareStatement(subjectForCode);
			PreparedStatement subjectForCode3 = conn3.prepareStatement(subjectForCode);
			PreparedStatement subjectForCode4 = conn4.prepareStatement(subjectForCode);
			PreparedStatement subjectForCode5 = conn5.prepareStatement(subjectForCode);
			PreparedStatement subjectForCode6 = conn6.prepareStatement(subjectForCode);

			findSubjectForCode = new Stack();
			findSubjectForCode.push(subjectForCode1);
			findSubjectForCode.push(subjectForCode2);
			findSubjectForCode.push(subjectForCode3);
			findSubjectForCode.push(subjectForCode4);
			findSubjectForCode.push(subjectForCode5);
			findSubjectForCode.push(subjectForCode6);
			

			// Return the connections back to the pool.
		
		//	connectionPool.returnConnection(conn1);
		//	connectionPool.returnConnection(conn2);
		//	connectionPool.returnConnection(conn3);
		//	connectionPool.returnConnection(conn4);
		//	connectionPool.returnConnection(conn5);
		//	connectionPool.returnConnection(conn6);			
		
		}				

		catch (SQLException e) {
			System.out.println("SQL exception occured at com.treetop.data." + "" +
							   "SpecificationSubject.init()" + e);
		}    
    	
		}
	
	}
	
	/**
	 * Insert a document paragraph subject.
	 *
	 * Creation date: (4/01/2005 2:33:27 PM)
	 */
	public Exception insert(Vector inInsertInfo) {

		try {	
					
			for (int x = 0; x < inInsertInfo.size(); x ++) {				  	
			 
				SpecificationSubject insertInfo = (SpecificationSubject) inInsertInfo.elementAt(x);
				                                      	  
				if ((insertInfo.subjectCode != null) && 		// Subject code required
				   (!insertInfo.subjectCode.equals (""))) {
					    	
					PreparedStatement insertSubject = (PreparedStatement) sqlInsertSubject.pop();
		
					insertSubject.setString(1, "SS");
					
					if (insertInfo.subjectDeleted)								
						insertSubject.setString(2, "D");
					else
						insertSubject.setString(2, " ");
						
					insertSubject.setString(3, insertInfo.subjectCode);									
					insertSubject.setInt(4, insertInfo.subjectOrder.intValue());
					insertSubject.setString(5, insertInfo.subjectDescription);

					try {
						insertSubject.executeUpdate();
					}
					catch (Exception e) {
						System.out.println("Exception at com.treetop.data.Specification" +
										   "Subject.insert: " + insertInfo.getSubjectCode() +
										   ", Error: " + e);	
					}

					sqlInsertSubject.push(insertSubject);	
				}	
			}							
		}		
		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data." +
							   "SpecificationSubject.insert(Vector): " + e);
			return e;
		}
		
		return null;	
	}
	
	/**
	 * Load fields from data base record.
	 *
	 * Creation date: (4/1/2005 4:14:07 PM)
	 */
	protected void loadFields(ResultSet rs) {

		try {
		
			subjectCode				= rs.getString("ID3SBJ");
			subjectDescription		= rs.getString("ID3DSC");			
			subjectOrder	  		= new Integer(rs.getInt("ID3ORD"));

			String deleted			= rs.getString("ID3DLT");
			if (deleted.equals ("D"))
				subjectDeleted      = true;
			else
				subjectDeleted		= false;
	
		}
		catch (Exception e) {	
			System.out.println("SQL Exception at com.treetop.data.SpecificationSubject" +
							   ".loadFields(RS): " + e);
		}
					
	}	

	/**
	 * Set the paragraph subject code.
	 *
	 * Creation date: (4/01/2005 1:50:03 PM)
	 */
	public void setSubjectCode(String inSubjectCode) throws InvalidLengthException {
	
		if (inSubjectCode.length() > 2)
			throw new InvalidLengthException(
					"inSubjectCode", inSubjectCode.length(), 2);

		this.subjectCode = inSubjectCode;
	}
	
	/**
	 * Set the deleted record switch.
	 *
	 * Creation date: (4/01/2005 1:56:26 PM)
	 */
	public void setSubjectDeleted(boolean inSubjectDeleted) {
		
		this.subjectDeleted = inSubjectDeleted;
	}
	
	/**
	 * Set the paragraph subject description.
	 *
	 * Creation date: (4/01/2005 1:32:11 PM)
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
	 * Creation date: (4/01/2005 1:32:11 PM)
	 */
	public void setSubjectOrder(Integer inSubjectOrder) {
		
		this.subjectOrder = inSubjectOrder;
	}
	
	/**
	 * Update a document paragraph subject.
	 *
	 * Creation date: (4/01/2005 2:04:15 PM)
	 */
	public Exception update(Vector inUpdateInfo) {

		try {	
					
			for (int x = 0; x < inUpdateInfo.size(); x ++) {				  	
			 
				SpecificationSubject updateInfo = (SpecificationSubject) inUpdateInfo.elementAt(x);
				                                      	  
				if ((updateInfo.subjectCode != null) && 		// Subject code required
				   (!updateInfo.subjectCode.equals (""))) {
					    	
					PreparedStatement updateSubject = (PreparedStatement) sqlUpdateSubject.pop();
		
					updateSubject.setString(1, "SS");
					
					if (updateInfo.subjectDeleted)								
						updateSubject.setString(2, "D");
					else
						updateSubject.setString(2, " ");
						
					updateSubject.setString(3, updateInfo.subjectCode);									
					updateSubject.setInt(4, updateInfo.subjectOrder.intValue());
					updateSubject.setString(5, updateInfo.subjectDescription);

					updateSubject.setString(6, updateInfo.subjectCode);
					
					try {
						updateSubject.executeUpdate();
					}
					catch (Exception e) {
						System.out.println("Exception at com.treetop.data.Specification" +
										   "Subject.update: " + updateInfo.getSubjectCode() +
										   ", Error: " + e);	
					}

					sqlUpdateSubject.push(updateSubject);	
				}	
			}							
		}		
		catch (Exception e) {	
			System.out.println("SQL error at com.treetop.data." +
							   "SpecificationSubject.update(Vector): " + e);
			return e;
		}
		
		return null;	
	}
	
}


