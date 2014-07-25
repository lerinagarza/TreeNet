

package com.lawson.api;

import java.util.*;
import MvxAPI.MvxSockJ;

/**
 * Movex API MMS025MI with related functions (methods).
 * 
 * Generated: 1/6/2011 by twalto
 * 
 */
public class MMS025MI extends BaseAPI{
   /**
	* Simple constructor. 
	*/
	public MMS025MI() {
		// Constructor for this Class
	} 
   /**
	* test API mehods. 
	*/
	public static void main(String[] args) 
	{	
	
	}
   /**
    * Add Alias to MITPOP
	* Run and Log the API
	* -- TWalton 1/6/11
	*  Send in Data String and Open Socket
	*  Throw any Exception
	*/	   
	private static String addAlias(String inputData, 
								   MMS025MIAddAlias inData,
								   MvxSockJ inputSocket) 	
		throws Exception
	{
			StringBuffer throwError = new StringBuffer();
			String returnData = "";
			try {
				// execute the API request using sockit program.
				returnData = inputSocket.mvxTrans(inputData);
				//String returnData = "test API Insert Information";
				if (!returnData.trim().equals("OK") &&
					!returnData.trim().equals(""))
				{
				// COMMENTED OUT, if needed can uncomment and the log will run
				   // log ALL transaction attempts and results.
				   APILog sendInfo = new APILog();	
				   sendInfo.setApiName("MMS025MI");
				   sendInfo.setApiMethod(inputData.trim().substring(0, 14));
				   sendInfo.setEnvironment(inData.getEnvironment().trim());
				   sendInfo.setSentFromProgram(inData.sentFromProgram.trim());
				   sendInfo.setUserProfile(inData.getUserProfile().trim());
				   sendInfo.setInputData(inputData);
				   sendInfo.setOutputData(returnData);
				   ServiceAPILog.logAPITransaction(sendInfo);
		
   				// lets see the results on the console. -- For Debugging Purposes
				//   System.out.println("input string:" + inputData);
				//   System.out.println("return string:" + returnData);
				//}
			}
		}
		catch (Exception e){
			throwError.append(e);
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.lawson.api.");
			throwError.append("MMS025MI.");
			throwError.append("addAlias(String, Obj(Socket))");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
   /**
	* Run the Lawson add API using the
	*   Business Object, MMS025MIDeleteAlias
	* @param MMS025MIDeleteAlias
	* @throws Exception
	*/
	public static String deleteAlias(MMS025MIDeleteAlias incomingInfo)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		// Use the Incoming MMS024MIAddAlias to build the API String
		MvxSockJ socketObject = null;
		try {
			 // build once EACH time this method is called
			socketObject= BaseAPI.getSockEnv("MMS025MI", incomingInfo.getEnvironment());			
		 }
		 catch(Exception e)
		 {
		 	//System.out.println("Error: Cannot Open Socket Connection: " + e);
		 	throwError.append("Error: Cannot Open Socket Connection: " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
		   // Set up the String	
		 	StringBuffer rec = new StringBuffer(1024);
			rec.setLength(1024); 	
			for (int x = 0; x < 103; x++) {
			     rec.insert(0, " ");
			}
		 	// **************************************************
		 	//  Build the actual String which will be sent through the API
		 	try {
		 	  // Set Values into the String (from the MMS850MIAddRclLotSts Class)	
				rec.insert(0, "DltAlias");
	            rec.insert(15, incomingInfo.getCompany()); // CONO - Company 
	            rec.insert(18, incomingInfo.getAliasCategory()); // ALWT - Alias Category
	            rec.insert(20, incomingInfo.getAliasQualifier()); // ALWQ - Alias Qualifier
	            rec.insert(24, incomingInfo.getItemNumber()); // ITNO - Item Number
	            rec.insert(39, incomingInfo.getAliasNumber()); // POPN - Alias Number
	          // - Not Currently Needed - rec.insert(69, ); // EOPA - Partner
	          // - Not Currently Needed - rec.insert(86, ); // SEA1 - Season
	          // - Not Currently Needed - rec.insert(93, ); // VFDT - Valid From 
	            rec.setLength(103); // Adjust the length
		 	}
		 	catch(Exception e)
			{
		 		System.out.println("Error: Building String to send to MMS025MI DeleteAlias API: " + e);
			 	throwError.append("Error: Building String to send to MMS025MI DeleteAlias API: " + e);
			}
	        if (throwError.toString().trim().equals(""))
			{
				try {
					returnData = deleteAlias(rec.toString(), incomingInfo, socketObject);
				} catch (Exception e)
				{
//			 		System.out.println("Error: Running the API: " + e);					
					throwError.append("Error: Running the API: " + e);
				}
			} // End of the second If there is an error			
		 } // End of the first If there is an error
		 try
		 {
		 	// Close any open Socket Connections
			 socketObject.mvxClose();
		 }
		 catch(Exception e)
		 {
			//el.printStackTrace();
		 }
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.lawson.api.MMS025MI.");
			throwError.append("deleteAlias(MMS025MIDeleteAlias)");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
/**
	* Run the Lawson add API using the
	*   Business Object, MMS025MIAddAlias
	* @param MMS025MIAddAlias
	* @throws Exception
	*/
	public static String addAlias(MMS025MIAddAlias incomingInfo)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		// Use the Incoming MMS024MIAddAlias to build the API String
		MvxSockJ socketObject = null;
		try {
			 // build once EACH time this method is called
			socketObject= BaseAPI.getSockEnv("MMS025MI", incomingInfo.getEnvironment());			
		 }
		 catch(Exception e)
		 {
		 	//System.out.println("Error: Cannot Open Socket Connection: " + e);
		 	throwError.append("Error: Cannot Open Socket Connection: " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
		   // Set up the String	
		 	StringBuffer rec = new StringBuffer(1024);
			rec.setLength(1024); 	
			for (int x = 0; x < 210; x++) {
			     rec.insert(0, " ");
			}
		 	// **************************************************
		 	//  Build the actual String which will be sent through the API
		 	try {
		 	  // Set Values into the String (from the MMS850MIAddRclLotSts Class)	
				rec.insert(0, "AddAlias");
	            rec.insert(15, incomingInfo.getCompany()); // CONO - Company 
	            rec.insert(18, incomingInfo.getAliasCategory()); // ALWT - Alias Category
	            rec.insert(20, incomingInfo.getAliasQualifier()); // ALWQ - Alias Qualifier
	            rec.insert(24, incomingInfo.getItemNumber()); // ITNO - Item Number
	            rec.insert(39, incomingInfo.getAliasNumber()); // POPN - Alias Number
	          // - Not Currently Needed - rec.insert(69, ); // EOPA - Partner
	          // - Not Currently Needed - rec.insert(86, ); // VFDT - Valid From
	          // - Not Currently Needed - rec.insert(96, ); // LVDT - Valid To 
	          // - Not Currently Needed - rec.insert(106, ); // CNQT - Quantity 
	          // - Not Currently Needed - rec.insert(123, ); // ALUN - Alternate UOM 
	          // - Not Currently Needed - rec.insert(126, ); // ORCO - Country of Origin 
		      // - Not Currently Needed - rec.insert(129, ); // SEQN - Sequence Number 
		      // - Not Currently Needed - rec.insert(136, ); // REMK - Remark 
		      // - Not Currently Needed - rec.insert(166, ); // CFXX - Old CFIN     
	          // - Not Currently Needed - rec.insert(173, ); // SEA1 - Season 
			  // - Not Currently Needed - rec.insert(180, ); // ATPE - Alias Type
			  // - Not Currently Needed - rec.insert(188, ); // ATNR - Attribute Number
			  // - Not Currently Needed - rec.insert(200, ); // CFIN - Configuration Number 
	            rec.setLength(210); // Adjust the length
		 	}
		 	catch(Exception e)
			{
		 		System.out.println("Error: Building String to send to MMS025MI AddAlias API: " + e);
			 	throwError.append("Error: Building String to send to MMS025MI AddAlias API: " + e);
			}
	        if (throwError.toString().trim().equals(""))
			{
				try {
					returnData = addAlias(rec.toString(), incomingInfo, socketObject);
				} catch (Exception e)
				{
//			 		System.out.println("Error: Running the API: " + e);					
					throwError.append("Error: Running the API: " + e);
				}
			} // End of the second If there is an error			
		 } // End of the first If there is an error
		 try
		 {
		 	// Close any open Socket Connections
			 socketObject.mvxClose();
		 }
		 catch(Exception e)
		 {
			//el.printStackTrace();
		 }
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.lawson.api.MMS025MI.");
			throwError.append("addAlias(MMS025MIAddAlias)");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
/**
* Delete Alias from MITPOP
* Run and Log the API
* -- TWalton 10/31/12
*  Send in Data String and Open Socket
*  Throw any Exception
*/	   
private static String deleteAlias(String inputData, 
							      MMS025MIDeleteAlias inData,
							      MvxSockJ inputSocket) 	
	throws Exception
{
		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		try {
			// execute the API request using sockit program.
			returnData = inputSocket.mvxTrans(inputData);
			//String returnData = "test API Insert Information";
			if (!returnData.trim().equals("OK") &&
				!returnData.trim().equals(""))
			{
			// COMMENTED OUT, if needed can uncomment and the log will run
			   // log ALL transaction attempts and results.
			   APILog sendInfo = new APILog();	
			   sendInfo.setApiName("MMS025MI");
			   sendInfo.setApiMethod(inputData.trim().substring(0, 14));
			   sendInfo.setEnvironment(inData.getEnvironment().trim());
			   sendInfo.setSentFromProgram(inData.sentFromProgram.trim());
			   sendInfo.setUserProfile(inData.getUserProfile().trim());
			   sendInfo.setInputData(inputData);
			   sendInfo.setOutputData(returnData);
			   ServiceAPILog.logAPITransaction(sendInfo);
	
			// lets see the results on the console. -- For Debugging Purposes
			//   System.out.println("input string:" + inputData);
			//   System.out.println("return string:" + returnData);
			//}
		}
	}
	catch (Exception e){
		throwError.append(e);
	}
	
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.lawson.api.");
		throwError.append("MMS025MI.");
		throwError.append("deleteAlias(String, Obj(Socket))");
		throw new Exception(throwError.toString());
	}
	return returnData;
}	   
}
