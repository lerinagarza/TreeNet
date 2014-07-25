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
 * Access to RDB file DBLIB/RDPAFRMHDR  (Header)
 *       and RDB file DBLIB/RDPBFRMDTL  (Detail)
 *
 * Code used to generate the table.
 *
 *  Header
 * CREATE TABLE DBPRD/RDPAFRMHDR (
 *  RDANUMBER  INT         NOT NULL WITH DEFAULT, Formula Number
 *  RDATYPE    CHAR (   2) NOT NULL WITH DEFAULT, Type
 *  RDANAME    CHAR (  50) NOT NULL WITH DEFAULT, Formula Name
 *  RDATECH    CHAR (  10) NOT NULL WITH DEFAULT, Technician
 *  RDAVARIETY CHAR (   2) NOT NULL WITH DEFAULT, Fruit Variety
 *  RDAPRESERV CHAR (  20) NOT NULL WITH DEFAULT, Preservative
 *  RDACRTDATE DATE        NOT NULL WITH DEFAULT, Creation Date
 *  RDACRTTIME TIME        NOT NULL WITH DEFAULT, Creation Time
 *  RDACRTUSER CHAR (  10) NOT NULL WITH DEFAULT, Creation User
 *  RDAUPDDATE DATE        NOT NULL WITH DEFAULT, Update Date
 *  RDAUPDTIME TIME        NOT NULL WITH DEFAULT, Update Time
 *  RDAUPDUSER CHAR (  10) NOT NULL WITH DEFAULT, Update User
 *  RDACOMMENT CHAR (2000) NOT NULL WITH DEFAULT, Comment
 *
 *  Detail
 * CREATE TABLE DBPRD/RDPAFRMDTL (
 *  RDBNUMBER  INT         NOT NULL WITH DEFAULT, Formula Number
 *  RDBSEQ     INT         NOT NULL WITH DEFAULT, Sequence Number
 *  RDBSUPLIER CHAR (  30) NOT NULL WITH DEFAULT, Supplier
 *  RDBSUPCODE CHAR (  20) NOT NULL WITH DEFAULT, Supplier Code
 *  RDBINGDESC CHAR (  40) NOT NULL WITH DEFAULT, Ingredient Description
 *  RDBRES     CHAR (  15) NOT NULL WITH DEFAULT, Resource
 *  RDBUOM     CHAR (   8) NOT NULL WITH DEFAULT, Unit of Measure
 *  RDBQTY     INT  (12,4) NOT NULL WITH DEFAULT, Quantity
 *  RDBFRMPCT  INT  (12,4) NOT NULL WITH DEFAULT, formula Percent
 *  RDBDRYWGT  INT  (12,4) NOT NULL WITH DEFAULT, Dry Weight
 *  RDBWGTPCT  INT  (12,4) NOT NULL WITH DEFAULT, Dry Weight Percent
 *  RDBCSTLB   INT  (12,4) NOT NULL WITH DEFAULT, Cost Per Pound
 *
 * Be sure to set the environment (library) for this file . It
 *  should be "DBPRD." for the live environment 
 *   METHODS TO BE MAINTAINED PRIOR TO EVERY EXPORT:
 *   :init()
 *   :findFormulas(...)
 *   :buildDropDownOfAllFormulas(...)
 **/
public class RandDFormula {

	//  Header Information
	private Integer       formulaNumber;
	private String        type;
	private String        name;
	private String        technician;
	private String        variety;
	private String        preservative;
	private java.sql.Date creationDate;
	private java.sql.Time creationTime;
	private String        creationUser;
	private java.sql.Date updateDate;
	private java.sql.Time updateTime;
	private String        updateUser;
	private String        comment;

	//  Detail Information
	private Integer[]	  sequenceNumber;
	private String[]	  supplier;
	private String[]	  supplierCode;
	private String[]	  ingredientDescription;
	private String[]	  resource;
	private String[] 	  unitOfMeasure;
	private	BigDecimal[]  quantity;
	private BigDecimal[]  formulaPercent;
	private BigDecimal[]  dryWeight;
	private BigDecimal[]  weightPercent;
	private BigDecimal[]  costPerPound;
	

	//**** For use in Main Method,
    // Constructor Methods and 
    // Update, Add, Delete Methods  ****//
	private static PreparedStatement sqlAddHdr;
	private static PreparedStatement sqlAddDtl;
	private static PreparedStatement sqlDeleteHdr;
	private static PreparedStatement sqlDeleteDtl;
	private static PreparedStatement sqlUpdateHdr;
	private static PreparedStatement sqlUpdateDtl;
	private static PreparedStatement sqlFindHdrByFormulaNumber;
	private static PreparedStatement sqlFindDtlByFormulaNumber;

	// Additional fields.
	private boolean persists = false;
	private static Connection connection;
	//live or test environment on the as400
	private String  library = "DBPRD."; 
	
/**
 * Used to Instantiate the RandDFormula Class.
 *       All the fields will be null.
 *
 * Creation date: (6/13/2003 8:42:39 AM)
 */
public RandDFormula() 
{
	if (connection == null)
		init();

	loadFieldsEmpty();
}
/**
 * Used to Instantiate the RandDFormula Class.
 *    By sending in a Formula Number.
 *    The fields will be loaded with information from that Formula.
 *        Header Information will be 1 per field.
 *        Detail Information will be an array per field.
 *
 * Creation date: (6/11/2003 1:14:39 PM)
 */
public RandDFormula(Integer formulaNumberIn)

throws InstantiationException 
{ 
	if (connection == null)
		init();
	
	ResultSet rsHdr = null;
	ResultSet rsDtl = null;
	
	try 
	{
		sqlFindHdrByFormulaNumber.setInt(1, formulaNumberIn.intValue());
		rsHdr = sqlFindHdrByFormulaNumber.executeQuery();
		
		if (rsHdr.next() == false)
		{
			throw new InstantiationException("The Formula Header: " + 
	  										  formulaNumberIn + 
											 " was not found");
		}
		else
		{
			try 
			{
				sqlFindDtlByFormulaNumber.setInt(1, formulaNumberIn.intValue());
				rsDtl = sqlFindDtlByFormulaNumber.executeQuery();
				
			} 
			catch (SQLException eDtl) 
			{
				System.out.println("SQL error at " +
						   "com.treetop.data.RandDFormula.RandDFormula(Integer) --Detail Section-- : " + 
						   eDtl);
				return;
			}

		}
	} 
	catch (SQLException eHdr) 
	{
		System.out.println("SQL error at " +
						   "com.treetop.data.RandDFormula.RandDFormula(Integer) --Header Section-- : " + 
						   eHdr);
		return;
	}
	loadFields(rsHdr, rsDtl);
	
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
private RandDFormula(ResultSet rsHdr)

throws InstantiationException 
{ 
	if (connection == null)
		init();
	
	ResultSet rsDtl = null;
	
	try 
	{
		sqlFindDtlByFormulaNumber.setInt(1, rsHdr.getInt("RDANUMBER"));
		rsDtl = sqlFindDtlByFormulaNumber.executeQuery();
	} 
	catch (SQLException eDtl) 
	{
		System.out.println("SQL error at " +
				   "com.treetop.data.RandDFormula.RandDFormula(Integer) --Detail Section-- : " + 
				   eDtl);
		return;
	}

	loadFields(rsHdr, rsDtl);
}
/**
 * Add a record into the Header Formula File.
 *
 * Creation date: (6/13/2003 9:18:29 AM)
 */
public void add() 
{
	try 
	{
		// Header Information
		sqlAddHdr.setInt(1, formulaNumber.intValue());
		sqlAddHdr.setString(2, type);
		sqlAddHdr.setString(3, name);
		sqlAddHdr.setString(4, technician);
		sqlAddHdr.setString(5, variety);
		sqlAddHdr.setString(6, preservative);
		sqlAddHdr.setDate(7, creationDate);
		sqlAddHdr.setTime(8, creationTime);
		sqlAddHdr.setString(9, creationUser);
		sqlAddHdr.setDate(10, updateDate);
		sqlAddHdr.setTime(11, updateTime);
		sqlAddHdr.setString(12, updateUser);
		sqlAddHdr.setString(13, comment);
		
		sqlAddHdr.executeUpdate();

		int howManyDetailLines = sequenceNumber.length;

	    for (int x=0; x < howManyDetailLines; x++)
	    {
		   	try 
			{
				String newQuantity = quantity[x] + "";
				String newFormulaPercent = formulaPercent[x] + "";
				String newDryWeight = dryWeight[x] + "";
				String newWeightPercent = weightPercent[x] + "";
				String newCostPerPound = costPerPound[x] + "";
	
				// Detail Information
				sqlAddDtl.setInt(1, formulaNumber.intValue());
				sqlAddDtl.setInt(2, sequenceNumber[x].intValue());
				sqlAddDtl.setString(3, supplier[x]);
				sqlAddDtl.setString(4, supplierCode[x]);
				sqlAddDtl.setString(5, ingredientDescription[x]);
				sqlAddDtl.setString(6, resource[x]);
				sqlAddDtl.setString(7, unitOfMeasure[x]);
				sqlAddDtl.setString(8, newQuantity);
				sqlAddDtl.setString(9, newFormulaPercent);
				sqlAddDtl.setString(10, newDryWeight);
				sqlAddDtl.setString(11, newWeightPercent);
				sqlAddDtl.setString(12, newCostPerPound);
		
				sqlAddDtl.executeUpdate();

			} 
			catch (SQLException eDtl) 
			{
				System.out.println("SQL error at " +
					"com.treetop.data.RandDFormula.add() --Detail Section--: " + 
					eDtl);
			}	
		
	    }		
	} 
	catch (SQLException eHdr) 
	{
		System.out.println("SQL error at " +
					"com.treetop.data.RandDFormula.add() --Header Section--: " + 
					eHdr);
	}	
}
/**
 * Send in all the fields for the record to be added to the file.
 *
 * Creation date: (6/13/2003 9:40:29 AM)
 */
public static void addToRandDFormula(
										int formulaNumber,
										String type,
										String name,
										String technician,
						 			    String variety,
										String preservative,
										java.sql.Date creationDate,
										java.sql.Time creationTime,
										String creationUser,
										String comment,
										Integer[] sequenceNumber,
										String[] supplier,
										String[] supplierCode,
										String[] ingredientDescription,
										String[] resource,
										BigDecimal[] quantity,
										String[] unitOfMeasure,
										BigDecimal[] formulaPercent,
										BigDecimal[] dryWeight,
										BigDecimal[] weightPercent,
										BigDecimal[] costPerPound)
throws InvalidLengthException, Exception
{
	RandDFormula newRecord = new RandDFormula();
	//Header Information
	newRecord.setFormulaNumber(formulaNumber);
	newRecord.setType(type);
	newRecord.setName(name);
	newRecord.setTechnician(technician);
	newRecord.setVariety(variety);
	newRecord.setPreservative(preservative);
	newRecord.setCreationDate(creationDate);
	newRecord.setCreationTime(creationTime);
	newRecord.setCreationUser(creationUser);
	newRecord.setUpdateDate(creationDate);
	newRecord.setUpdateTime(creationTime);
	newRecord.setUpdateUser(creationUser);
	newRecord.setComment(comment);
	newRecord.setSequenceNumber(sequenceNumber);
	newRecord.setSupplier(supplier);
	newRecord.setSupplierCode(supplierCode);
	newRecord.setIngredientDescription(ingredientDescription);
	newRecord.setResource(resource);
	newRecord.setQuantity(quantity);
	newRecord.setUnitOfMeasure(unitOfMeasure);
	newRecord.setFormulaPercent(formulaPercent);
	newRecord.setDryWeight(dryWeight);
	newRecord.setWeightPercent(weightPercent);
	newRecord.setCostPerPound(costPerPound);
	
	newRecord.add();
}
/**
 * Send in the Class to be added to the file.
 *
 * Creation date: (7/23/2003 10:14:29 AM)
 */
public static void addToRandDFormula(
					RandDFormula newRecord)
throws InvalidLengthException, Exception
{
	newRecord.add();
}
/**
 * This method will get a list of all formulas,
 *    Will display on this list the Formula Names.
 *    Will use as the variable passed, the formula Number
 *
 *  Send in:  chosenFormulaNumber = The formula you would like selected.
 *            type                = the type of formula "RD".
 *            chosenName          = the name you want the drop down variable called, ie: formula
 *            selectStatement     = send in what you want the Select to say:
 *
 * Creation date: (8/6/2003 3:26:32 PM)
 * @return java.lang.String
 */
public String buildDropDownOfAllFormulas(Integer chosenFormulaNumber,
	                                     String type,
	                                     String chosenName, 
	                                     String selectStatement) 
{
	String dropDown     = "";
	String selected     = "";
	String selectOption = "";
	// Define Selection Information
	if (selectStatement.equals("") || 
		selectStatement.equals("null"))
	    selectOption = "Select a Formula--->:"; //Default
	else {
		 if (selectStatement.trim().equals("*all"))
		     selectOption = "*all";
	     else
	         selectOption = "Select a " + selectStatement.trim() + "--->";
	     }
	
	// Get list of all customers, build Drop Down List.

	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	//String library = "DBLIB."; // live environment
	String saveFormula = "";
	try 
	{
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(
			"SELECT * FROM " + library + "RDPAFRMHDR " +
			"WHERE RDATYPE = '" + type + "'" +
			"ORDER BY RDANUMBER, RDANAME");
		 
		while(rs.next())
		{
			if (!saveFormula.equals(rs.getString("RDANUMBER")))
			{
				saveFormula = rs.getString("RDANUMBER");

				if (rs.getString("RDANUMBER").trim().equals(chosenFormulaNumber.toString().trim()))
		   		    selected = "' selected='selected'>";
	 	  		    else
	  	 		    selected = "'>";
		   		    
	   			    dropDown = dropDown + "<option value='" + 
	   			    rs.getString("RDANUMBER").trim() + selected +
	   			    rs.getString("RDANUMBER").trim() + "&nbsp;-&nbsp;" +
	   		 	   rs.getString("RDANAME").trim() + "&nbsp;";
			}
		}
		rs.close();		
	} 
	catch (SQLException e) 
	{
		System.out.println("SQL error at " +
						   "com.treetop.data.RandDFormula.buildDropDownOfAllFormulas() " + 
						   e);
	}

	if (!dropDown.equals(""))
	{	   		    
 	    dropDown = "<select name='" + chosenName.trim() + "' >" +
 	    		   "<option value='None' selected>" + selectOption +
 	    		   dropDown + "</select>";  	 
    }
 	
	return dropDown;
}
/**
 * Delete a record from the Formula Header and Detail Files.
 *
 * Creation date: (6/13/2003 9:30:29 AM)
 */
public void delete() 
{
	try 
	{
		sqlDeleteHdr.setInt(1, formulaNumber.intValue());
		sqlDeleteHdr.executeUpdate();
	} 
	catch (SQLException eHdr) 
	{
		System.out.println("SQL Exception at " + 
					"com.treetop.data.RandDFormula.delete() -- Header Section --: " + 
					eHdr);
	}
	
	try 
	{
		sqlDeleteDtl.setInt(1, formulaNumber.intValue());
		sqlDeleteDtl.executeUpdate();
	} 
	catch (SQLException eDtl) 
	{
		System.out.println("SQL Exception at " + 
					"com.treetop.data.RandDFormula.delete() -- Detail Section --: " + 
					eDtl);
	}	
}
/**
 * Delete a records from the R & D Formula Files (Header & Detail)
 *    will delete based on the Formula Number sent into this method.
 *
 * Creation date: (6/13/2003 9:37:29 AM)
 */
public boolean deleteByFormulaNumber(int formulaNumberIn) 
{
	try 
	{
		sqlDeleteHdr.setInt(1, formulaNumberIn);
		sqlDeleteHdr.executeUpdate();
		try 
		{
			sqlDeleteDtl.setInt(1, formulaNumberIn);
			sqlDeleteDtl.executeUpdate();
			return true;
		} 
		catch (SQLException eDtl) 
		{
			System.out.println("SQL Exception at " + 
						"com.treetop.data.RandDFormula.delete() -- Detail Section --: " + 
						eDtl);
			return false;
		}	
	} 
	catch (SQLException eHdr) 
	{
		System.out.println("SQL Exception at " + 
					"com.treetop.data.RandDFormula.delete() -- Header Section --: " + 
					eHdr);
		return false;
	}
}
/**
 * Call this method will return a vector which will include R & D Formulas.
 *   Send in a bunch of parameters to define the SQL statement.
 *
 * Creation date: (6/18/2003 10:17:29 AM)
 */
public Vector findFormulas(String type,
						   Integer fromFormula,
						   Integer toFormula,
						   java.sql.Date fromCreateDate,
						   java.sql.Date toCreateDate,
						   java.sql.Date fromReviseDate,
						   java.sql.Date toReviseDate,
						   String formulaName,
						   String resource,
						   String ingredientDescription,
						   String supplier,
						   String variety,
						   String preservative,
						   String technician,
						   String comment,
						   String customerName,
						   String orderBy) 
{	
	// Define database environment prior to every export (live or test).
	//String library = "WKLIB."; // test environment
	//String library = "DBLIB."; // live environment - OLD BOX
	//String library = "DBPRD."; // New Box Live Environment

   Vector returnFormulas = new Vector();	
	
	String SQLRun = "SELECT * " +
                    "FROM " + library + "RDPAFRMHDR ";
    String SQLDetail = "";
    
    String SQLWhere = "";


	//************************************//
	//  Information from the Header File  //
	   SQLWhere = "WHERE RDATYPE = \'" + type + "\' ";
	int fromF = fromFormula.intValue();
	int toF = toFormula.intValue();
	if (fromF != 0 || toF != 999999999)
	{
		if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    SQLWhere = SQLWhere + "RDANUMBER BETWEEN " +
	                fromFormula + " and " + toFormula +
	                " ";
	}

    if (fromCreateDate != null || toCreateDate != null)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    if (fromCreateDate == null)
	       fromCreateDate = java.sql.Date.valueOf("1950-01-01");
	    if (toCreateDate == null)
	       toCreateDate = java.sql.Date.valueOf("2050-12-31");

	    SQLWhere = SQLWhere +
	    			"RDACRTDATE BETWEEN \'" + fromCreateDate +
	    			"\' AND \'"+ toCreateDate +
	                "\' ";
	  }

    if (fromReviseDate != null || toReviseDate != null)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    if (fromReviseDate == null)
	       fromReviseDate = java.sql.Date.valueOf("1950-01-01");
	    if (toReviseDate == null)
	       toReviseDate = java.sql.Date.valueOf("2050-12-31");

	    SQLWhere = SQLWhere +
	    			"RDAUPDDATE BETWEEN \'" +
	                fromReviseDate + "\' AND \'" + toReviseDate +
	                "\' ";
	  }

    if (technician != null && !technician.equals("None") && technician.trim().length() != 0)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

	    technician = technician.toUpperCase();
	    SQLWhere = SQLWhere +
	    		   "UPPER(RDATECH) = \'" + technician + "\' ";
    }
    
    if (formulaName != null && formulaName.trim().length() != 0)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

    	formulaName = formulaName.toUpperCase();
    	formulaName = "%" + formulaName + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(RDANAME) LIKE \'" + formulaName + "\' ";
    }

    if (variety != null && variety.trim().length() != 0 && !variety.equals("None"))
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
  	
	   		 SQLWhere = SQLWhere +
            "RDAVARIETY = \'" + variety + "\' ";
    }

    if (preservative != null && preservative.trim().length() != 0)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

    	preservative = preservative.toUpperCase();
    	preservative = "%" + preservative + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(RDAPRESERV) LIKE \'" + preservative + "\' ";
    }

    if (comment != null && comment.trim().length() != 0)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

    	comment = comment.toUpperCase();
    	comment = "%" + comment + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(RDACOMMENT) LIKE \'" + comment + "\' ";
    }
	//************************************//
	//  Information from the Detail File  //
    if (resource != null && resource.trim().length() != 0)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "RDPBFRMDTL " +
	       				"ON RDBNUMBER = RDANUMBER ";
	       
    	resource = resource.toUpperCase();
    	resource = "%" + resource + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(RDBRES) LIKE \'" + resource + "\' ";
    }

    if (ingredientDescription != null && ingredientDescription.trim().length() != 0)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "RDPBFRMDTL " +
	       				"ON RDBNUMBER = RDANUMBER ";
	       
    	ingredientDescription = ingredientDescription.toUpperCase();
    	ingredientDescription = "%" + ingredientDescription + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(RDBINGDESC) LIKE \'" + ingredientDescription + "\' ";
    }

    if (supplier != null && supplier.trim().length() != 0)
    {
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";
	       
	    if (SQLDetail.equals(""))
	       SQLDetail = "INNER JOIN " + library + "RDPBFRMDTL " +
	       				"ON RDBNUMBER = RDANUMBER ";
	       
    	supplier = supplier.toUpperCase();
    	supplier = "%" + supplier + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(RDBSUPLIER) LIKE \'" + supplier + "\' ";
    }

	//***************************************//
	//  Information from Sample Request File //
	//  by using the Customer File //
	if (customerName != null && customerName.trim().length() != 0)
	{
		SQLDetail = SQLDetail +
		         "INNER JOIN " + library + "SRPCDETAIL " +
		         	"ON RDANUMBER = SRCFORMULA " +
		         "INNER JOIN " + library + "SRPBHEADER " +
		            "ON SRCNUMBER = SRBNUMBER " + 
		         "INNER JOIN " + library + "SRPACUST " +
		            "ON SRBCUST = SRANUMBER ";
	    if (SQLWhere.equals(""))
	       SQLWhere = "WHERE ";
	    else
	       SQLWhere = SQLWhere + "AND ";

    	customerName = customerName.toUpperCase();
    	customerName = "%" + customerName + "%";
	    
	    SQLWhere = SQLWhere +
                  "UPPER(SRANAME) LIKE \'" + customerName + "\' ";
	       
	}
	
	//***********************************************//
	//***  Information for Order of Results   **********//
	//     Order by can be
	//        NumberA      = Formula Number Ascending
	//        NumberD      = Formula Number Descending
	//        NameA        = Formula Name Ascending (Default if Blank)
	//        NameD        = Formula Name Descending
	//        CreateDateA  = Creation Date Ascending
	//        CreateDateD  = Creation Date Descending
	//        ReviseDateA  = Revision Date Ascending
	//        ReviseDateD  = Revision Date Descending
	//***
	String SQLOrder = "ORDER BY UPPER(RDANAME)"; // Default NameA
	if (orderBy == null)
	   orderBy = "";
	if (orderBy.equals("NumberA"))
	   SQLOrder = "ORDER BY RDANUMBER";
	if (orderBy.equals("NumberD"))
	   SQLOrder = "ORDER BY RDANUMBER DESC";
	if (orderBy.equals("NameD"))
	   SQLOrder = "ORDER BY UPPER(RDANAME) DESC";
	if (orderBy.equals("CreateDateA"))
	   SQLOrder = "ORDER BY RDACRTDATE, RDACRTTIME, UPPER(RDANAME)";
	if (orderBy.equals("CreateDateD"))
	   SQLOrder = "ORDER BY RDACRTDATE DESC, RDACRTTIME DESC, UPPER(RDANAME)";
	if (orderBy.equals("ReviseDateA"))
	   SQLOrder = "ORDER BY RDAUPDDATE, RDAUPDTIME, UPPER(RDANAME)";
	if (orderBy.equals("ReviseDateD"))
	   SQLOrder = "ORDER BY RDAUPDDATE DESC, RDAUPDTIME DESC, UPPER(RDANAME)";

	//***********************************************//
	//***  Put SQL Statement Together      **********//
    SQLRun = SQLRun + SQLDetail + SQLWhere + SQLOrder;
	try
	{		 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQLRun);
		 
		try
		{
 		   	while (rs.next())
   		    {
	   		    RandDFormula buildVector = new RandDFormula(rs);
  	    		returnFormulas.addElement(buildVector);
		    }
 	       rs.close();
	 	}
		catch (Exception e)
		{
			System.out.println("Exception Error while Reading a result set (RandDFormula.findFormulas)" + e);
		}
	}
	catch (Exception e)
	{
		System.out.println("Exception on Running SQL (RandDFormula.findFormulas) " + e);
	}         
	
	return returnFormulas;
	
}
/**
 * Retrieve from the class the Comment,
 *      This field includes procedures on what to do with the ingredients.
 *
 * Creation date: (6/12/2003 2:26:28 PM)
 */
public String getComment() 
{
	return comment;	
}
/**
 * Retrieve an array from the class for Cost Per Pound,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 2:33:28 PM)
 */
public BigDecimal[] getCostPerPound() 
{
	return costPerPound;	
}
/**
 * Retrieve from the class the Date the Record was created,
 *
 * Creation date: (6/12/2003 2:17:28 PM)
 */
public java.sql.Date getCreationDate() 
{
	return creationDate;	
}
/**
 * Retrieve from the class the Time the Record was created,
 *
 * Creation date: (6/12/2003 2:19:28 PM)
 */
public java.sql.Time getCreationTime() 
{
	return creationTime;	
}
/**
 * Retrieve from the class the User who created the Record,
 *
 * Creation date: (6/12/2003 2:20:28 PM)
 */
public String getCreationUser() 
{
	return creationUser;	
}
/**
 * Retrieve an array from the class for Dry Weight,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 2:32:28 PM)
 */
public BigDecimal[] getDryWeight() 
{
	return dryWeight;	
}
/**
 * Retrieve from the class the Formula Number,
 *
 * Creation date: (6/12/2003 2:21:28 PM)
 */
public int getFormulaNumber() 
{
	return formulaNumber.intValue();	
}
/**
 * Retrieve an array from the class for Formula Percent,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 2:31:28 PM)
 */
public BigDecimal[] getFormulaPercent() 
{
	return formulaPercent;	
}
/**
 * Retrieve an array from the class for Ingredient Description,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 2:29:28 PM)
 */
public String[] getIngredientDescription() 
{
	return ingredientDescription;	
}
/**
 * Retrieve from the class the Formula Name,
 *
 * Creation date: (6/12/2003 2:21:28 PM)
 */
public String getName() 
{
	return name;	
}
/**
 * Retrieve from the class the Preservative,
 *
 * Creation date: (6/12/2003 2:25:28 PM)
 */
public String getPreservative() 
{
	return preservative;	
}
/**
 * Retrieve an array from the class for Quantity,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 2:31:28 PM)
 */
public BigDecimal[] getQuantity() 
{
	return quantity;	
}
/**
 * Retrieve an array from the class for Resource,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 2:30:28 PM)
 */
public String[] getResource() 
{
	return resource;	
}
/**
 * Retrieve an array from the class for Sequence Number,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 2:27:28 PM)
 */
public Integer[] getSequenceNumber() 
{
	return sequenceNumber;	
}
/**
 * Retrieve an array from the class for Supplier,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 2:28:28 PM)
 */
public String[] getSupplier() 
{
	return supplier;	
}
/**
 * Retrieve an array from the class for Supplier Code,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 2:29:28 PM)
 */
public String[] getSupplierCode() 
{
	return supplierCode;	
}
/**
 * Retrieve from the class the Technician,
 *
 * Creation date: (6/12/2003 2:24:28 PM)
 */
public String getTechnician() 
{
	return technician;	
}
/**
 * Retrieve from the class the Formula Type,
 *
 * Creation date: (6/12/2003 2:23:28 PM)
 */
public String getType() 
{
	return type;	
}
/**
 * Retrieve an array from the class for Unit of Measure,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 2:30:28 PM)
 */
public String[] getUnitOfMeasure() 
{
	return unitOfMeasure;	
}
/**
 * Retrieve from the class the Date the Record was updated,
 *
 * Creation date: (6/12/2003 2:17:28 PM)
 */
public java.sql.Date getUpdateDate() 
{
	return updateDate;	
}
/**
 * Retrieve from the class the Time the Record was updated,
 *
 * Creation date: (6/12/2003 2:19:28 PM)
 */
public java.sql.Time getUpdateTime() 
{
	return updateTime;	
}
/**
 * Retrieve from the class the User who updated the Record,
 *
 * Creation date: (6/12/2003 2:20:28 PM)
 */
public String getUpdateUser() 
{
	return updateUser;	
}
/**
 * Retrieve from the class the Variety,
 *
 * Creation date: (6/12/2003 2:25:28 PM)
 */
public String getVariety() 
{
	return variety;	
}
/**
 * Retrieve an array from the class for Dry Weight Percent,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 2:33:28 PM)
 */
public BigDecimal[] getWeightPercent() 
{
	return weightPercent;	
}
/**
 * Used to build prepare Statements, define variables.
 * 
 * Creation date: (6/13/2003 8:43:29 AM)
 */
public void init() {
	
	// Test for initial connection.

//	System.out.println("persists = " + persists);	
	if (this.persists == false) 
	{
		connection = ConnectionStack.getConnection();
	    //connection = SQLConnect.connection; -- OLD BOX
	    this.persists = true;
	}
	
	// Define database environment prior to every export (live or test).
	//library = "WKLIB."; // test environment
	//library = "DBLIB."; // live environment (OLD BOX
	//library = "DBPRD."; // Live environment on the NEW Box

	try 
	{
		sqlAddHdr = connection.prepareStatement(
			"INSERT INTO " + library + "RDPAFRMHDR " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");

		sqlAddDtl = connection.prepareStatement(
			"INSERT INTO " + library + "RDPBFRMDTL " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
		
		sqlDeleteHdr = connection.prepareStatement(
			"DELETE FROM " + library + "RDPAFRMHDR " +
			" WHERE RDANUMBER = ?");

		sqlDeleteDtl = connection.prepareStatement(
			"DELETE FROM " + library + "RDPBFRMDTL " +
			" WHERE RDBNUMBER = ?");

		sqlUpdateHdr = connection.prepareStatement(
			"UPDATE " + library + "RDPAFRMHDR " +
			" SET RDANUMBER  = ?, RDATYPE    = ?, RDANAME    = ?, " +
			    " RDATECH    = ?, RDAVARIETY = ?, RDAPRESERV = ?, " +
			    " RDAUPDDATE = ?, RDAUPDTIME = ?, RDAUPDUSER = ?, " +
			    " RDACOMMENT = ? " + 
			" WHERE RDANUMBER = ?");

		sqlUpdateDtl = connection.prepareStatement(
			"UPDATE " + library + "RDPBFRMDTL " +
			" SET RDBNUMBER  = ?, RDBSEQ     = ?, RDBSUPLIER = ?, " +
			    " RDBSUPCODE = ?, RDBINGDESC = ?, RDBRES     = ?, " +
			    " RDBUOM     = ?, RDBQTY     = ?, RDBFRMPCT  = ?, " +
			    " RDBDRYWGT  = ?, RDBWGTPCT  = ?, RDBCSTLB   = ? " +
			" WHERE RDBNUMBER = ? AND RDBSEQ = ?");

		sqlFindHdrByFormulaNumber = connection.prepareStatement(
			"SELECT * FROM " + library + "RDPAFRMHDR " +
			" WHERE RDANUMBER = ?");

		sqlFindDtlByFormulaNumber = connection.prepareStatement(
			"SELECT * FROM " + library + "RDPBFRMDTL " +
			" WHERE RDBNUMBER = ?" +
			" ORDER BY RDBSEQ");

	} 
	catch (SQLException e) 
	{
		System.out.println("SQL exception occured at " + 
			 			"com.treetop.data.RandDFormula.init() " +
			               e);
	}
}
/**
 * Take a result set, (from an SQL Query) 
 *    one for Header information and One for Detail Information
 *    and set the information into the fields within this class.
 *
 * Creation date: (6/12/2003 3:29:29 PM)
 */
protected void loadFields(ResultSet rsHdr, ResultSet rsDtl) 
{
	try 
	{
		// Heading Information
		formulaNumber         =    new Integer(rsHdr.getInt("RDANUMBER"));
	    type      	          =                rsHdr.getString("RDATYPE");
	    name     	          =                rsHdr.getString("RDANAME");
	    technician            =                rsHdr.getString("RDATECH");
	    variety  	          =                rsHdr.getString("RDAVARIETY");
	    preservative          =                rsHdr.getString("RDAPRESERV");
		creationDate          =                rsHdr.getDate("RDACRTDATE");
		creationTime	      =                rsHdr.getTime("RDACRTTIME"); 
	    creationUser          =                rsHdr.getString("RDACRTUSER");
	    updateDate            = 			   rsHdr.getDate("RDAUPDDATE");
	    updateTime            = 			   rsHdr.getTime("RDAUPDTIME");
	    updateUser 		      =                rsHdr.getString("RDAUPDUSER");
	    comment    		      =                rsHdr.getString("RDACOMMENT");
	    // Detail Information / Building of Array's
	    Integer[] seqNumberArray            = new Integer[100];
	    String[] supplierArray              = new String[100];
	    String[] supplierCodeArray          = new String[100];
	    String[] ingredientDescriptionArray = new String[100];
	    String[] resourceArray              = new String[100];
	    BigDecimal[] quantityArray          = new BigDecimal[100];
	    String[] unitOfMeasureArray         = new String[100];
	    BigDecimal[] formulaPercentArray    = new BigDecimal[100];
	    BigDecimal[] dryWeightArray         = new BigDecimal[100];
	    BigDecimal[] weightPercentArray     = new BigDecimal[100];
	    BigDecimal[] costPerPoundArray      = new BigDecimal[100];
	  	int x = 0;  
	    try 
		{
			while (rsDtl.next())
		 	{
				seqNumberArray[x]             = new Integer(rsDtl.getInt("RDBSEQ"));
				supplierArray[x]              = rsDtl.getString("RDBSUPLIER");
				supplierCodeArray[x]          = rsDtl.getString("RDBSUPCODE");
				ingredientDescriptionArray[x] = rsDtl.getString("RDBINGDESC");
 				resourceArray[x]              = rsDtl.getString("RDBRES"); 
				quantityArray[x]              = new BigDecimal(rsDtl.getString("RDBQTY"));
				unitOfMeasureArray[x]         = rsDtl.getString("RDBUOM");
				formulaPercentArray[x]        = new BigDecimal(rsDtl.getString("RDBFRMPCT"));
				dryWeightArray[x]             = new BigDecimal(rsDtl.getString("RDBDRYWGT"));
				weightPercentArray[x]         = new BigDecimal(rsDtl.getString("RDBWGTPCT"));
				costPerPoundArray[x]          = new BigDecimal(rsDtl.getString("RDBCSTLB"));	
				
				x = x + 1;
			}
		 	
		}
		catch (Exception e) 
		{
			System.out.println("Error at " +
				"com.treetop.data.RandDFormula.loadFields() --Detail Section--: " + 
				e);
		}
	    // Correct the Size of the array;
	    Integer[] seqNumberArr            = new Integer[x];
	    String[] supplierArr              = new String[x];
	    String[] supplierCodeArr          = new String[x];
	    String[] ingredientDescriptionArr = new String[x];
	    String[] resourceArr              = new String[x];
	    BigDecimal[] quantityArr          = new BigDecimal[x];
	    String[] unitOfMeasureArr         = new String[x];
	    BigDecimal[] formulaPercentArr    = new BigDecimal[x];
	    BigDecimal[] dryWeightArr         = new BigDecimal[x];
	    BigDecimal[] weightPercentArr     = new BigDecimal[x];
	    BigDecimal[] costPerPoundArr      = new BigDecimal[x];

	for (int y = 0; y < x; y++)
	{
			seqNumberArr[y]             = seqNumberArray[y];
			supplierArr[y]              = supplierArray[y];
			supplierCodeArr[y]          = supplierCodeArray[y];
			ingredientDescriptionArr[y] = ingredientDescriptionArray[y];
			resourceArr[y]              = resourceArray[y];
			quantityArr[y]              = quantityArray[y];
			unitOfMeasureArr[y]         = unitOfMeasureArray[y];
			formulaPercentArr[y]        = formulaPercentArray[y];
			dryWeightArr[y]             = dryWeightArray[y];
			weightPercentArr[y]         = weightPercentArray[y];
			costPerPoundArr[y]          = costPerPoundArray[y];
			
	}
			
		sequenceNumber 			=	seqNumberArr;
		supplier                = 	supplierArr;
		supplierCode     		=	supplierCodeArr;
		ingredientDescription	=	ingredientDescriptionArr;
		resource				=	resourceArr;
		quantity				=	quantityArr;
		unitOfMeasure			=	unitOfMeasureArr;
		formulaPercent			=	formulaPercentArr;
		dryWeight				=	dryWeightArr;
		weightPercent			=	weightPercentArr;
		costPerPound			=	costPerPoundArr;
	
	}
	catch (Exception e)
	{
		System.out.println("SQL Exception at com.treetop.data.RandDFormula.loadFields();" + e);
	}

	persists = true;
				
}
/**
 * Load the class with empty and 0, not null.
 *
 * Creation date: (7/22/2003 2:29:29 PM)
 */
protected void loadFieldsEmpty() 
{
	try 
	{
		// Heading Information
		formulaNumber         = new Integer("0");
	    type      	          = "";
	    name     	          = "";
	    technician            = "";
	    variety  	          = "";
	    preservative          = "";
		creationDate          = java.sql.Date.valueOf("1900-01-01");
		creationTime	      = java.sql.Time.valueOf("00:00:00");
	    creationUser          = "";
	    updateDate            = java.sql.Date.valueOf("1900-01-01");
	    updateTime            = java.sql.Time.valueOf("00:00:00");
	    updateUser 		      = "";
	    comment    		      = "";
	    // Detail Information / Building of Array's
	    Integer[] seqNumberArray            = new Integer[1];
	    String[] supplierArray              = new String[1];
	    String[] supplierCodeArray          = new String[1];
	    String[] ingredientDescriptionArray = new String[1];
	    String[] resourceArray              = new String[1];
	    BigDecimal[] quantityArray          = new BigDecimal[1];
	    String[] unitOfMeasureArray         = new String[1];
	    BigDecimal[] formulaPercentArray    = new BigDecimal[1];
	    BigDecimal[] dryWeightArray         = new BigDecimal[1];
	    BigDecimal[] weightPercentArray     = new BigDecimal[1];
	    BigDecimal[] costPerPoundArray      = new BigDecimal[1];
	
			seqNumberArray[0]             = new Integer("0");
			supplierArray[0]              = "";
			supplierCodeArray[0]          = "";
			ingredientDescriptionArray[0] = "";
 			resourceArray[0]              = "";
			quantityArray[0]              = new BigDecimal("0");
			unitOfMeasureArray[0]         = "";
			formulaPercentArray[0]        = new BigDecimal("0");
			dryWeightArray[0]             = new BigDecimal("0");
			weightPercentArray[0]         = new BigDecimal("0");
			costPerPoundArray[0]          = new BigDecimal("0");	
				
		sequenceNumber 			=	seqNumberArray;
		supplier                = 	supplierArray;
		supplierCode     		=	supplierCodeArray;
		ingredientDescription	=	ingredientDescriptionArray;
		resource				=	resourceArray;
		quantity				=	quantityArray;
		unitOfMeasure			=	unitOfMeasureArray;
		formulaPercent			=	formulaPercentArray;
		dryWeight				=	dryWeightArray;
		weightPercent			=	weightPercentArray;
		costPerPound			=	costPerPoundArray;
	
	}
	catch (Exception e)
	{
		System.out.println("SQL Exception at com.treetop.data.RandDFormula.loadFields();" + e);
	}

	persists = true;
				
}
/**
 * Use to test the prepared statements,
 *                 Add, Delete, Update,
 *     and whatever else needs testing.
 *
 * Creation date: (6/13/2003 2:34:29 PM)
 */
public static void main(String[] args) 
{
	java.sql.Date theDate = java.sql.Date.valueOf("2003-01-17");
	java.sql.Time theTime = java.sql.Time.valueOf("12:13:14");

	if ("x" == "y")
	{
	Integer[] number = new Integer[5];
	String[] alpha = new String[5];
	BigDecimal[] decimalNumber = new BigDecimal[5];
// Test Array's
	number[0] = new Integer(0);
	number[1] = new Integer(1);
	number[2] = new Integer(2);
	number[3] = new Integer(3);
	number[4] = new Integer(4);

	alpha[0] = "a comp";
	alpha[1] = "b comp";
	alpha[2] = "c comp";
	alpha[3] = "d comp";
	alpha[4] = "e comp";

	decimalNumber[0] = new BigDecimal(0.00);
	decimalNumber[1] = new BigDecimal(1.11);
	decimalNumber[2] = new BigDecimal(2.22);
	decimalNumber[3] = new BigDecimal(3.33);
	decimalNumber[4] = new BigDecimal(4.44);
	
	// add a few formulas.
	try 
	{
		RandDFormula one = mainAddRandDFormula(
								1, "AB",
								"Formula Name", "Technician",
								"GS", "Preservative", 
								theDate, theTime, "User",
								theDate, theTime, "User",
								"Comment Section can be 2000 characters Long",
								number, alpha, alpha, alpha, alpha,
								decimalNumber, alpha, decimalNumber, 
								decimalNumber, decimalNumber, decimalNumber); 

		System.out.println("one: " + one);
	} 
	catch (Exception e) 
	{
		System.out.println("error Adding ONE " +
			         "com.treetop.data.RandDFormula.main()" + 
			         e);
	}
	
	try 
	{
		RandDFormula two = mainAddRandDFormula(
								2, "CD",
								"ABC Formula", "DFLORE",
								"GS", "No Preserves",
								theDate, theTime, "TWALTO",
								theDate, theTime, "TWALTO",
								"Mix all ingredients together in big pot, package", 
								number, alpha, alpha, alpha, alpha,
								decimalNumber, alpha, decimalNumber, 
								decimalNumber, decimalNumber, decimalNumber); 
		System.out.println("two: " + two);
	} 
	catch (Exception e) 
	{
		System.out.println("error Adding TWO " +
			         "com.treetop.data.RandDFormula.main()" + 
			         e);
	}
	
	try 
	{
		RandDFormula three = mainAddRandDFormula(
								3, "AB",
								"XYZ Formula", "New",
								"RD", "",
								theDate, theTime, "THAILE",
								theDate, theTime, "THAILE",
								"There is Nothing to do",
								number, alpha, alpha, alpha, alpha,
								decimalNumber, alpha, decimalNumber, 
								decimalNumber, decimalNumber, decimalNumber); 
		System.out.println("three: " + three);
	} 
	catch (Exception e) 
	{
		System.out.println("error Adding THREE " +
			         "com.treetop.data.RandDFormula.main()" + 
			         e);
	}

	
	// find (instatiate) Class by customer Number.
	Integer test2 = new Integer(2);
	try 
	{
		RandDFormula two = new RandDFormula(test2);
		System.out.println("find two: " + two);

		// update the Name
		try
		{
			two.setName("ABC Formula/Changed");
			BigDecimal[] newQty = decimalNumber;
			newQty[0] = new BigDecimal(999);
			two.setQuantity(newQty);
		}
		catch (Exception e) 
		{
			System.out.println("com.treetop.Data.RandDFormula.Main()" +
				               ". Problem with change test: " + e);
		}

		two.update();
		
		//delete 2nd Record
//		two.delete();
	} 
	catch (Exception e) 
	{
		System.out.println("com.treetop.data.RandDFormula.Main(). " +
			               "Error with find/delete in main" + e);
	}

	// delete one and three.
	try 
	{
		Integer test1 = new Integer(1);
		RandDFormula one = new RandDFormula(test1);
//		one.delete();
	} 
	catch (Exception e) 
	{
		System.out.println("com.treetop,data.RandDFormula.Main(). " + 
			               "delete problem with one: " + e);
	}

	try 
	{
		Integer test3 = new Integer(3);
		RandDFormula three = new RandDFormula(test3);
//		three.delete();
	} 
	catch (Exception e) 
	{
		System.out.println("com.treetop,data.RandDFormula.Main(). " + 
			               "delete problem with three: " + e);
	}
		
	// find a Formula that dosent exist.
	try
	{
		Integer test = new Integer(9999);
		RandDFormula notThere = new RandDFormula(test);
		System.out.println("notThere: " + notThere);
	} 
	catch (InstantiationException ie) 
	{
		System.out.println("record not there: " + ie);
	}

	}
	
	if ("x" == "x")
	{
		int x = 0;
		x = nextFormulaNumber();
		String stophere = "x";
	}

}
/**
 * Used to Test within the Class 
 *    Specifically used with the Main Method.
 *
 * Creation date: (6/13/2003 9:21:39 AM)
 */
protected static RandDFormula mainAddRandDFormula(
										int formulaNumber,
										String type,
										String name,
										String technician,
		  				 			    String variety,
					  					String preservative,
										java.sql.Date creationDate,
										java.sql.Time creationTime,
										String creationUser,
										java.sql.Date updateDate,
										java.sql.Time updateTime,
										String updateUser,
										String comment,
										Integer[] sequenceNumber,
										String[] supplier,
										String[] supplierCode,
										String[] ingredientDescription,
										String[] resource,
										BigDecimal[] quantity,
										String[] unitOfMeasure,
										BigDecimal[] formulaPercent,
										BigDecimal[] dryWeight,
										BigDecimal[] weightPercent,
										BigDecimal[] costPerPound)
throws InvalidLengthException, Exception
{
	RandDFormula newRecord = new RandDFormula();
	//Header Information
	newRecord.setFormulaNumber(formulaNumber);
	newRecord.setType(type);
	newRecord.setName(name);
	newRecord.setTechnician(technician);
	newRecord.setVariety(variety);
	newRecord.setPreservative(preservative);
	newRecord.setCreationDate(creationDate);
	newRecord.setCreationTime(creationTime);
	newRecord.setCreationUser(creationUser);
	newRecord.setUpdateDate(updateDate);
	newRecord.setUpdateTime(updateTime);
	newRecord.setUpdateUser(updateUser);
	newRecord.setComment(comment);
	newRecord.setSequenceNumber(sequenceNumber);
	newRecord.setSupplier(supplier);
	newRecord.setSupplierCode(supplierCode);
	newRecord.setIngredientDescription(ingredientDescription);
	newRecord.setResource(resource);
	newRecord.setQuantity(quantity);
	newRecord.setUnitOfMeasure(unitOfMeasure);
	newRecord.setFormulaPercent(formulaPercent);
	newRecord.setDryWeight(dryWeight);
	newRecord.setWeightPercent(weightPercent);
	newRecord.setCostPerPound(costPerPound);
	
	newRecord.add();
	
	return newRecord;
}
/**
 * Use this Method to get a new Formula Number for Add/Copy.
 * Creation date: (6/24/2003 4:36:39 PM)
 */
public static int nextFormulaNumber() 
{
	
	AS400 as400 = null;

	try {
		// create a AS400 object
		//AS400 as400 = new AS400("10.6.100.1", "DAUSER", "web230502");
		as400 = ConnectionStack.getAS400Object();
		ProgramCall pgm = new ProgramCall(as400);

		ProgramParameter[] parmList = new ProgramParameter[1];
		parmList[0] = new ProgramParameter(100);
		pgm = new ProgramCall(as400, "/QSYS.LIB/MOVEX.LIB/CLFORMNBR.PGM", parmList);

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
 * Set field into the class for Comments,
 *     Comments include procedure of what to do with the ingredients.
 *
 * Creation date: (6/12/2003 1:34:28 PM)
 */
public void setComment(String commentIn) 

throws InvalidLengthException 
{
	if (commentIn.length() > 2000)
		throw new InvalidLengthException(
				"commentIn", commentIn.length(), 2000);

	this.comment = commentIn;
}
/**
 * Set array into the class for Cost Per Pound,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 1:46:28 PM)
 */
public void setCostPerPound(BigDecimal[] costPerPoundIn) 
{
	this.costPerPound = costPerPoundIn;
}
/**
 * Set field into the class for Date record was created,
 *
 * Creation date: (6/12/2003 1:24:28 PM)
 */
public void setCreationDate(java.sql.Date creationDateIn)  
{
	this.creationDate =  creationDateIn;
}
/**
 * Set field into the class for Time record was created,
 *
 * Creation date: (6/12/2003 1:25:28 PM)
 */
public void setCreationTime(java.sql.Time creationTimeIn)  
{
	this.creationTime = creationTimeIn;
}
/**
 * Set field into the class for User who created the record,
 *
 * Creation date: (6/12/2003 1:27:28 PM)
 */
public void setCreationUser(String creationUserIn) 

throws InvalidLengthException 
{
	if (creationUserIn.length() > 10)
		throw new InvalidLengthException(
				"creationUserIn", creationUserIn.length(), 10);

	this.creationUser = creationUserIn;
}
/**
 * Set array into the class for Dry Weight,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 1:45:28 PM)
 */
public void setDryWeight(BigDecimal[] dryWeightIn) 
{
	this.dryWeight = dryWeightIn;
}
/**
 * Set field into the class for Formula Number,
 *
 * Creation date: (6/12/2003 11:56:28 AM)
 */
public void setFormulaNumber(int formulaNumberIn) 
{
	this.formulaNumber =  new Integer(formulaNumberIn);
}
/**
 * Set array into the class for Formula Percent,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 1:44:28 PM)
 */
public void setFormulaPercent(BigDecimal[] formulaPercentIn) 
{
	this.formulaPercent = formulaPercentIn;
}
/**
 * Set array into the class for Ingredient Description,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 1:40:28 PM)
 */
public void setIngredientDescription(String[] ingredientDescriptionIn) 
{
	this.ingredientDescription = ingredientDescriptionIn;
}
/**
 * Set field into the class for Formula Name,
 *
 * Creation date: (6/12/2003 11:59:28 AM)
 */
public void setName(String nameIn) 

throws InvalidLengthException 
{
	if (nameIn.length() > 50)
		throw new InvalidLengthException(
				"nameIn", nameIn.length(), 50);

	this.name = nameIn;
}
/**
 * Set field into the class for Preservative,
 *
 * Creation date: (6/12/2003 1:33:28 PM)
 */
public void setPreservative(String preservativeIn) 

throws InvalidLengthException 
{
	if (preservativeIn.length() > 20)
		throw new InvalidLengthException(
				"preservativeIn", preservativeIn.length(), 20);

	this.preservative = preservativeIn;
}
/**
 * Set array into the class for Quantity,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 1:43:28 PM)
 */
public void setQuantity(BigDecimal[] quantityIn) 
{
	this.quantity = quantityIn;
}
/**
 * Set array into the class for Resource,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 1:41:28 PM)
 */
public void setResource(String[] resourceIn) 
{
	this.resource = resourceIn;
}
/**
 * Set array into the class for Sequence Number,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 1:36:28 PM)
 */
public void setSequenceNumber(Integer[] sequenceNumberIn) 
{
	this.sequenceNumber =  sequenceNumberIn;
}
/**
 * Set array into the class for Supplier,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 1:38:28 PM)
 */
public void setSupplier(String[] supplierIn) 
{
	this.supplier =  supplierIn;
}
/**
 * Set array into the class for Supplier Code (lot, item code),
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 1:39:28 PM)
 */
public void setSupplierCode(String[] supplierCodeIn) 
{
	this.supplierCode =  supplierCodeIn;
}
/**
 * Set field into the class for Technician who created the Formula,
 *
 * Creation date: (6/12/2003 1:30:28 PM)
 */
public void setTechnician(String technicianIn) 

throws InvalidLengthException 
{
	this.technician = technicianIn;
}
/**
 * Set field into the class for Formula Type,
 *
 * Creation date: (6/12/2003 11:57:28 AM)
 */
public void setType(String typeIn) 

throws InvalidLengthException 
{
	if (typeIn.length() > 2)
		throw new InvalidLengthException(
				"typeIn", typeIn.length(), 2);

	this.type = typeIn;
}
/**
 * Set array into the class for Unit of Measure,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 1:42:28 PM)
 */
public void setUnitOfMeasure(String[] unitOfMeasureIn) 
{
	this.unitOfMeasure = unitOfMeasureIn;
}
/**
 * Set field into the class for Date record was updated,
 *
 * Creation date: (6/12/2003 10:48:28 AM)
 */
public void setUpdateDate(java.sql.Date updateDateIn)  
{
	this.updateDate =  updateDateIn;
}
/**
 * Set field into the class for Time record was updated,
 *
 * Creation date: (6/12/2003 10:50:28 AM)
 */
public void setUpdateTime(java.sql.Time updateTimeIn)  
{
	this.updateTime =  updateTimeIn;
}
/**
 * Set field into the class for User who updated the record,
 *
 * Creation date: (6/12/2003 10:51:28 AM)
 */
public void setUpdateUser(String updateUserIn) 

throws InvalidLengthException 
{
	if (updateUserIn.length() > 10)
		throw new InvalidLengthException(
				"updateUserIn", updateUserIn.length(), 10);

	this.updateUser = updateUserIn;
}
/**
 * Set field into the class for Variety,
 *
 * Creation date: (6/12/2003 1:32:28 PM)
 */
public void setVariety(String varietyIn) 

throws InvalidLengthException 
{
	if (varietyIn.length() > 2)
		throw new InvalidLengthException(
				"varietyIn", varietyIn.length(), 2);

	this.variety = varietyIn;
}
/**
 * Set array into the class for Dry Weight Percent,
 *       Used for the Detail Section.
 *
 * Creation date: (6/12/2003 1:45:28 PM)
 */
public void setWeightPercent(BigDecimal[] weightPercentIn) 
{
	this.weightPercent = weightPercentIn;
}
/**
 * String values for each variable.
 *
 * Creation date: (6/12/2003 2:57:29 PM)
 */
public String toString() 
{
	return new String(
		// Header Information
        "formulaNumber: " + formulaNumber + "\n" +
	    "type: "          + type          + "\n" +
	    "name: "          + name          + "\n" +
	    "technician: "    + technician    + "\n" +
	    "variety: "       + variety       + "\n" +
	    "preservative: "  + preservative  + "\n" +
		"creationDate: "  + creationDate  + "\n" +
		"creationTime: "  + creationTime  + "\n" +
	    "creationUser: "  + creationUser  + "\n" +
	    "updateDate: "    + updateDate    + "\n" +
	    "updateTime: "    + updateTime    + "\n" +
	    "updateUser: "	  + updateUser    + "\n" +
	    "comment: " 	  + comment       + "\n" +
		"library: "       + library       + "\n");
}
/**
 * Update a record from the Header & Detail Files for the Formula.
 *
 * Creation date: (6/13/2003 9:42:29 AM)
 */
public void update() 
{
	try 
	{
	// Header fields
		sqlUpdateHdr.setInt(1, formulaNumber.intValue());
		sqlUpdateHdr.setString(2, type);
		sqlUpdateHdr.setString(3, name);
		sqlUpdateHdr.setString(4, technician);
		sqlUpdateHdr.setString(5, variety);
		sqlUpdateHdr.setString(6, preservative);
		sqlUpdateHdr.setDate(7, updateDate);
		sqlUpdateHdr.setTime(8, updateTime);
		sqlUpdateHdr.setString(9, updateUser);
		sqlUpdateHdr.setString(10, comment);
	
    // Where section of SQL statement
		sqlUpdateHdr.setInt(11, formulaNumber.intValue());
		sqlUpdateHdr.executeUpdate();

	// First Delete the Current Detail Lines
		sqlDeleteDtl.setInt(1, formulaNumber.intValue());
		sqlDeleteDtl.executeUpdate();
		
	// Then Add the New Detail Lines
		int howManyDetailLines = sequenceNumber.length;

	    for (int x=0; x < howManyDetailLines && sequenceNumber[x] != null; x++)
	    {
		 
		   	try 
			{
				String newQuantity = quantity[x] + "";
				String newFormulaPercent = formulaPercent[x] + "";
				String newDryWeight = dryWeight[x] + "";
				String newWeightPercent = weightPercent[x] + "";
				String newCostPerPound = costPerPound[x] + "";
	
				// Detail Information
				sqlAddDtl.setInt(1, formulaNumber.intValue());
				sqlAddDtl.setInt(2, sequenceNumber[x].intValue());
				sqlAddDtl.setString(3, supplier[x]);
				sqlAddDtl.setString(4, supplierCode[x]);
				sqlAddDtl.setString(5, ingredientDescription[x]);
				sqlAddDtl.setString(6, resource[x]);
				sqlAddDtl.setString(7, unitOfMeasure[x]);
				sqlAddDtl.setString(8, newQuantity);
				sqlAddDtl.setString(9, newFormulaPercent);
				sqlAddDtl.setString(10, newDryWeight);
				sqlAddDtl.setString(11, newWeightPercent);
				sqlAddDtl.setString(12, newCostPerPound);
		
				sqlAddDtl.executeUpdate();
			} 
			catch (SQLException eDtl) 
			{
				System.out.println("SQL error at " +
					"com.treetop.data.RandDFormula.update() --Detail Section--: " + 
					eDtl);
			}
			catch (Exception e) 
			{
				System.out.println("Error at " +
					"com.treetop.data.RandDFormula.update() --Detail Section--: " + 
					e);
			}
		
		
	    }		
		
	} 
	catch (SQLException eHdr) 
	{
		System.out.println("Sql error at " +
					"com.treetop.data.RandDFormula.update(): " + 
					eHdr);
	}

}
/**
 * Send in all the fields for the record to be added to the file.
 *
 * Creation date: (6/13/2003 9:40:29 AM)
 */
public static void updateRandDFormula(
										int formulaNumber,
										String type,
										String name,
										String technician,
						 			    String variety,
										String preservative,
										java.sql.Date creationDate,
										java.sql.Time creationTime,
										String creationUser,
										String comment,
										Integer[] sequenceNumber,
										String[] supplier,
										String[] supplierCode,
										String[] ingredientDescription,
										String[] resource,
										BigDecimal[] quantity,
										String[] unitOfMeasure,
										BigDecimal[] formulaPercent,
										BigDecimal[] dryWeight,
										BigDecimal[] weightPercent,
										BigDecimal[] costPerPound)
throws InvalidLengthException, Exception
{
	RandDFormula newRecord = new RandDFormula();
	//Header Information
	newRecord.setFormulaNumber(formulaNumber);
	newRecord.setType(type);
	newRecord.setName(name);
	newRecord.setTechnician(technician);
	newRecord.setVariety(variety);
	newRecord.setPreservative(preservative);
	newRecord.setCreationDate(creationDate);
	newRecord.setCreationTime(creationTime);
	newRecord.setCreationUser(creationUser);
	newRecord.setUpdateDate(creationDate);
	newRecord.setUpdateTime(creationTime);
	newRecord.setUpdateUser(creationUser);
	newRecord.setComment(comment);
	newRecord.setSequenceNumber(sequenceNumber);
	newRecord.setSupplier(supplier);
	newRecord.setSupplierCode(supplierCode);
	newRecord.setIngredientDescription(ingredientDescription);
	newRecord.setResource(resource);
	newRecord.setQuantity(quantity);
	newRecord.setUnitOfMeasure(unitOfMeasure);
	newRecord.setFormulaPercent(formulaPercent);
	newRecord.setDryWeight(dryWeight);
	newRecord.setWeightPercent(weightPercent);
	newRecord.setCostPerPound(costPerPound);
	
	newRecord.update();
}
/**
 * Send in the Class for the record to be Updated in the file.
 *
 * Creation date: (7/23/2003 1:08:29 PM)
 */
public static void updateRandDFormula(RandDFormula newRecord)

throws InvalidLengthException, Exception
{
	newRecord.update();
}
}
