package com.treetop;

/**
 * Insert the type's description here.
 * Creation date: (12/18/2002 10:21:10 AM)
 * @author: 
 * 
 * @deprecated
 * 1/26/12 - TWalton - put this statement in each Service
 * 
 */
public class BuildSQLStatement {
/**
 * BuildSQLStatement constructor comment.
 * 
 * @deprecated
 * 1/26/12 - TWalton - put this statement in each Service
 */
public BuildSQLStatement() {
	super();
}
/**
 *  This method receives three values as incoming parameters.
 *  **INCOMING
 *  - trade
 *  - market
 *  - broker
 *
 *  These incoming parameters are validated and then used to build a dynamic sql
 * statement that will return as an outgoing parameter.
 *  **OUTGOING ARRAY
 *  [sql statement, error message]
 *  (0)            (1)
 *
 *  Test return array position [1] to determine if this method could build the
 * sql statement, or instead returned a error message.
 *
 * Creation date: (12/18/2002 10:21:24 AM)
 * @deprecated
 * 1/26/12 - TWalton - put this statement in each Service
 */
public String[] BuildMarketrs(String trade, String market, String broker) {

	// Return array
	String rtnArray[] = new String[2];
	rtnArray[0] = "";
	rtnArray[1] = "";
	
	String excludetrade = "no";
	String excludemarket = "no";
	String excludebroker = "no";
	
	String msg = "";
	ValidateFields val = new ValidateFields();


//******************************************
// Test incoming parameters 
//******************************************

	//**********************
	// Test incoming trade.
	//**********************
	
	// For null value.
	if (trade == null)
	{
		rtnArray[1] = "Incomimg trade value is null, " +
					  "the SQL statement could not be built. - " +
					  "com.treetop.BuildSQLStatement.BuildMarketrs(String,String,String)";
					  
		return rtnArray;
	}
	
	// For all trades.
	if (trade.equals("*all") || trade.equals("*All") || trade.equals("*ALL"))
		excludetrade = "yes";

	//***********************
	// Test incoming market.
	//***********************

	// For null value.
	if (!market.equals(null))
	{
		if (market.equals("*all") || market.equals("*All") || market.equals("*ALL"))
			excludemarket = "yes";
	}

	if (!excludemarket.equals("yes"))
	{
	//	msg = val.validateMarket(market);
		
		if (!msg.equals(""))
		{ 
		rtnArray[1] = msg + " The SQL statement could not be built" +
					  " - com.treetop.BuildSQLStatement.BuildMarketrs(String," + 
					  "String,String)"; 
		return rtnArray;
		}
	}
	
	
	//***********************
	// Test incoming broker.
	//***********************
	
	//For null value. 
	if (!broker.equals(null))
	{
		if (broker.equals("*all") || broker.equals("*All") || broker.equals("*ALL"))
		excludebroker = "yes";
	}

	if (!excludebroker.equals("yes"))
	{
//		msg = val.validateBroker(broker);

		if (!msg.equals(""))
		{
			rtnArray[1] = msg + " The SQL statement could not be built" +
						  " - com.treetop.BuildSQLStatement.BuildMarketrs(String," +
						  "String,String)";
						  
						  
			return rtnArray;
		}
	}
	

//**************************
// Build the SQL statement.
//**************************

	String sqlStatement = "";

//sql FROM portion.
	String sqlFrom = "SELECT * FROM DBLIB.ARPABRKR ";

//sql WHERE portion 
	String sqlWhere = "";
	
	
	// Add trade selection criteria.
	if (excludetrade.equals("no"))
		sqlWhere = "WHERE ARATYP = '" + trade + "' ";
		
	// Add market selection criteria.
	if (excludemarket.equals("no"))
	{
		if (sqlWhere.equals(""))
			sqlWhere = "WHERE ARABKR = " + market + " ";
		else
			sqlWhere = sqlWhere + "AND ARABKR = " + market + " ";
	}

	// Add broker selection criteria.
	if (excludebroker.equals("no"))
	{
		if (sqlWhere.equals(""))
			sqlWhere = "WHERE ARAMBK = " + broker + " ";
		else
			sqlWhere = sqlWhere + "AND ARAMBK = " + broker + " ";
	}

//sql ORDERBY
	String sqlOrderBy = "ORDER BY ARABKR";
	sqlStatement = sqlFrom + sqlWhere + sqlOrderBy;	
	rtnArray[0] = sqlStatement;		 
	System.out.println("sqlStatement:" + sqlStatement);
	return rtnArray;	
}
/**
 * Insert the method's description here.
 * Creation date: (12/18/2002 10:21:24 AM)
 * @deprecated
 * 1/26/12 - TWalton - put this statement in each Service
 */
public void newMethod() {}
}
