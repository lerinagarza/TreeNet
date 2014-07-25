package com.lawson.api;

import java.util.Vector;

import MvxAPI.MvxSockJ;
import com.treetop.businessobjects.*;

/**
 * Movex API MHS850MI with related functions (methods).
 * 
 * Generated: 7/15/2011 by deisen
 * 
 */
public class MHS850MI extends BaseAPI{

	
	
	
	
/**
* Run the Lawson addMOReceipt API 	
*/
public static Vector<String> addMOReceipt(Vector<MHS850MIAddMOReceipt> transactions, String authorization)
throws Exception
{
	StringBuffer throwError   = new StringBuffer();
	Vector<String>       returnData   =  new Vector<String>();		
	MvxSockJ     socketObject = null;
	
	if (transactions.isEmpty()) {
		return returnData;
	}
		
	try {	
		
		MHS850MIAddMOReceipt first = transactions.elementAt(0);
		socketObject= BaseAPI.getSockEnv("MHS850MI", first.getEnvironment(), authorization);	
		
	}
	catch(Exception e) {	
		
	 	throwError.append("Error: Cannot Open Socket Connection: " + e);
	}	
	
	
	
	
	for (MHS850MIAddMOReceipt transaction : transactions) {
	
	    if (throwError.toString().trim().equals(""))
		{
		   	StringBuffer production = new StringBuffer(1024);
			production.setLength(1024); 	
			int transactionLength = 397;
				
			for (int x = 0; x < transactionLength; x++) {
			     production.insert(0, " ");
			}
			 	
		 	//  Build the actual String which will be sent to the API
			
		 	try {
			 	    
				production.insert(0, "AddMOReceipt");
		        production.insert(15, transaction.getProcess().trim()); 
		        production.insert(19, transaction.getCompany().trim()); 
		        production.insert(22, transaction.getWarehouse().trim());
	//	        production.insert(25, transaction.getMessageNumber());
	//	        production.insert(40, transaction.getPackageNumber());	
	//	        production.insert(57, transaction.getDateGenerated());	
	//	        production.insert(67, transaction.getTimeGenerated());	
		        production.insert(73, transaction.getPartner().trim());
		        production.insert(90, transaction.getMessageType().trim());
	//	        production.insert(96, transaction.getFromWarehouse());	
	//	        production.insert(99, transaction.getAdressNumber());
		        production.insert(105, transaction.getItemNumber().trim());
	//	        production.insert(120, transaction.getAliasNumber());
	//	        production.insert(150, transaction.getAliasQuailifier());	 
	//	        production.insert(154, transaction.getAliasType());	
		        production.insert(156, transaction.getLocation().trim());
		        production.insert(166, transaction.getLotNumber().trim());
	//	        production.insert(178, transaction.getContainer());
		        production.insert(190, transaction.getQuantityReceived().trim());
		        production.insert(207, transaction.getOrderNumber().trim());
	//	        production.insert(217, transaction.getOrderOperation());
	//	        production.insert(223, transaction.getOrderLine());
	//	        production.insert(229, transaction.getOrderLineSuffix());
	//	        production.insert(232, transaction.getOrderIndex());
	//	        production.insert(243, transaction.getPickListSuffix());
	//	        production.insert(246, transaction.getUserDefined1());
	//	        production.insert(261, transaction.getUserDefined2());
	//	        production.insert(276, transaction.getUserDefined3());  
	//	        production.insert(291, transaction.getUserDefined4()); 
	//	        production.insert(306, transaction.getUserDefined5());  
	//	        production.insert(321, transaction.getCatchWeight());
	//	        production.insert(338, transaction.getExtMessageNumber());
		        production.insert(355, transaction.getLotReference1().trim());
		        production.insert(367, transaction.getLotReference2().trim());
	//	        production.insert(379, transaction.getFlagAsComplete());
		        production.insert(380, transaction.getReportingDate().trim());
		        production.insert(390, transaction.getReportingTime().trim());
	//	        production.insert(396, transaction.getBalanceIdStatus());`
		         
		        production.setLength(transactionLength);
		 	}
		 	catch(Exception e)
			{	 		
			 	throwError.append("Error: Building String to send to MHS850MI AddMOReceipt API: " + e);
			}
		 	
		    if (throwError.toString().trim().equals(""))
			{
				try {
					
					String response = addMOReceipt(production.toString(), transaction, socketObject);
					if (!response.equals("")) {
						returnData.addElement(
								"Production =>&nbsp;&nbsp;&nbsp; " +
								"Item number: " + transaction.getItemNumber() + "&nbsp;&nbsp;&nbsp; " + 
								"Lot number: " + transaction.getLotNumber() +  "&nbsp;&nbsp;&nbsp; " + 
								response.trim());
					}
				} 
				catch (Exception e)
				{	
					throwError.append("Error: Running the API: " + e);
				}			
			} 	    
		} 
	}
	 
    try {			 	
    	socketObject.mvxClose();		
	}
	catch(Exception e)
	{
	 //e.printStackTrace();
	}
		
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.lawson.api.MHS850MI.");
		throwError.append("addMOReceipt(MHS850MIAddMOReceipt, UserDefaults)");
		throw new Exception(throwError.toString());
	}
		return returnData;
}
/**
* Create production for manufacturing order
* Run and Log the API
* -- DEisen 7/15/2011
*  Send in Data String and Open Socket
*  Throw any Exception
*/	   
private static String addMOReceipt(String production, 
							       MHS850MIAddMOReceipt transaction,
							       MvxSockJ inputSocket) 	
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	String       returnData = "";
			
	try {							
				
		returnData = inputSocket.mvxTrans(production);				// execute the API request using socket program
		String response = returnData.trim().substring(0,2).toUpperCase();
				
		if (!response.equals("OK")) 								// API response, log if not OK			
		{
		   // COMMENTED OUT, if needed can uncomment and the log will run
		   // log ALL transaction attempts and results.
					
		   APILog sendInfo = new APILog();	
		   sendInfo.setApiName("MHS850MI");
		   sendInfo.setApiMethod(production.trim().substring(0, 14));
		   sendInfo.setEnvironment(transaction.getEnvironment().trim());
		   sendInfo.setSentFromProgram(transaction.sentFromProgram.trim());
		   sendInfo.setUserProfile(transaction.getUserProfile().trim());
		   sendInfo.setInputData(production);
		   sendInfo.setOutputData(returnData);
		   ServiceAPILog.logAPITransaction(sendInfo);
		   
		   String failed = returnData.trim().substring(0,3).toUpperCase();
		   if (!failed.equals("NOK")) 								// API response, Movex error message			
		   {
			   returnData = "";										// Not NOK message, therefore disregard
		   }
		
   		   // See the results on the console. -- For Debugging Purposes
		   // System.out.println("input string:" + production);
		   // System.out.println("return string:" + returnData);		  
		}
		else {
			returnData = "";										// OK response, then no message (errors only)
		}
		
	}
	catch (Exception e){
		throwError.append(e);
	}		
	
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.lawson.api.");
		throwError.append("MHS850MI.");
		throwError.append("addMOReceipt(String, MHS850MIAddMOReceipt, Obj(Socket))");
		throw new Exception(throwError.toString());
	}
		
	return returnData;
}	   
}
