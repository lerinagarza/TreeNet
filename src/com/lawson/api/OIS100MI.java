

package com.lawson.api;

import java.util.Vector;

import MvxAPI.MvxSockJ;

/**
 * Movex API OIS1000MI with related functions (methods).
 * 
 * Generated: 2/17/2012 by twalto
 * 
 */
public class OIS100MI extends BaseAPI{
   /**
	* Simple constructor. 
	*/
	public OIS100MI() {
		// Constructor for this Class
	} 
   /**
	* test API methods. 
	*/
	public static void main(String[] args) 
	{	
		try{

			OIS100MIAddBatchHead head = new OIS100MIAddBatchHead();
			head.setEnvironment("TST");
			head.setCompany("100");
			head.setCustomerNumber("34000");
			head.setOrderType("612");
			head.setRequestedDeliveryDate("20120217");
			head.setCustomersPODate("20120217");
			head.setOrderDate("20120217");
			head.setFacility("150");
			head.setCustomerOrderNumber("moNumber");
			head.setWarehouse("209");
		//	head.setCustomerAgreement("4000");
			
			Vector newList = new Vector();
			OIS100MIAddBatchLine line1 = new OIS100MIAddBatchLine();
			line1.setEnvironment("TST");
			line1.setItemNumber("181015");
			line1.setQuantity("100");
			line1.setWarehouse("209");
			line1.setUnitOfMeasure("CS");
			newList.addElement(line1);
			OIS100MIAddBatchLine line2 = new OIS100MIAddBatchLine();
			line2.setEnvironment("TST");
			line2.setItemNumber("181024");
			line2.setQuantity("100");
			line2.setWarehouse("209");
			line2.setUnitOfMeasure("CS");
			newList.addElement(line2);
			head.setListLines(newList);
			
			processBatchHeadLine(head);
			System.out.println("Stop and check");
		}catch(Exception e)
		{}
	}
	/**
	* Run the Lawson add API using the
	*   Business Object, OIS100MIAddBatchHead
	*  Also determine if the Socket sent in is valid, or if it needs to be retrieved (and returned)
	*   created TWalton 2/17/12
	* @param OIS100MIAddBatchHead
	* 				Within the AddBatchHead, there is a vector of lines, to process through
	* 				Will process the addBatchLine as well
	* 
	* 		Using methods addBatchHead
	* 					  addBatchLine
	* Returns the Temporary Order Number
	* @throws Exception
	*/
	public static String processBatchHeadLine(OIS100MIAddBatchHead incomingInfo)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		// Use the Incoming OIS100MIAddBatchHead to build the API String
		MvxSockJ socketObject = new MvxSockJ();
		String returnOrderNumber = "";
		try {
			 // build once EACH time this method is called
		   socketObject = BaseAPI.getSockEnv("OIS100MI", incomingInfo.getEnvironment());
		   System.out.println("socket - get");
			  // Process the Header Information:
				   // Within this process will also process any lines
			 returnOrderNumber = addBatchHead(incomingInfo, socketObject);
		
		 // Will then process through the Detail
    	 if (throwError.toString().trim().equals("") &&
    		 !incomingInfo.getListLines().isEmpty() &&
    		 !returnOrderNumber.trim().equals(""))
    	 {
    		for (int x = 0; x < incomingInfo.getListLines().size(); x++)
    		{
    			OIS100MIAddBatchLine thisLine = (OIS100MIAddBatchLine) incomingInfo.getListLines().elementAt(x);
    			try {
    				  // Process the Line Information:
    			   
    			   addBatchLine(returnOrderNumber, thisLine, socketObject);
    			 }
    			 catch(Exception e)
    			 {
    			 	//System.out.println("Error: Cannot Open Socket Connection: " + e);
    			 	throwError.append("Error: Cannot Open Socket Connection: " + e);
    			 }	
    		}
    	}
		 }
		 catch(Exception e)
		 {
		 	//System.out.println("Error: Cannot Open Socket Connection: " + e);
		 	throwError.append("Error: Cannot Open Socket Connection: " + e);
		 }	
		 finally{
			 try
			 {
		 	    socketObject.mvxClose();
			 }
			 catch(Exception e)
			 {
			//el.printStackTrace();
			 }
		 } 
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.lawson.api.OIS100MI.");
			throwError.append("processBatchHeadLine(OIS100MIAddBatchHead)");
			throw new Exception(throwError.toString());
		}
		return returnOrderNumber;
	}	   
   /**
    * Add a Header for a Temporary Customer Order 
    *    Return that Temporary Customer Order Number
	* Run and Log the API -- Log ONLY Problems found
	* -- TWalton 2/20/12
	*  Send in Data String and Open Socket
	*  Throw any Exception
	*/	   
	private static String addBatchHead(OIS100MIAddBatchHead inData,
								       MvxSockJ inputSocket) 	
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String tempOrder = "";
		try {
				
		 	StringBuffer rec = new StringBuffer(1024);
			rec.setLength(1024); 	
			for (int x = 0; x < 561; x++) {
			     rec.insert(0, " ");
			}
		 	// **************************************************
			//  Build the actual String which will be sent through the API
		 	try {
				  // Set Values into the String (from the OIS100AddBatchHead Class)	
				rec.insert(0, "AddBatchHead");
				rec.insert(15, inData.getCompany()); 
		        rec.insert(18, inData.getCustomerNumber()); 
				rec.insert(30, inData.getOrderType());
				rec.insert(33, inData.getRequestedDeliveryDate()); 
				rec.insert(43, inData.getFacility());
				rec.insert(46, inData.getCustomerOrderNumber());
				rec.insert(237, inData.getCustomersPODate());
				rec.insert(257, inData.getOrderDate());
				rec.insert(279, inData.getCustomerAgreement());
				rec.insert(293, inData.getRequestedDeliveryDate());			
				rec.insert(467, inData.getWarehouse());
		        rec.setLength(561); // Adjust the length
			}
			catch(Exception e)
			{
			 	throwError.append("Error: Building String to send to OIS100MI AddBatchHead API: " + e);
			}
		    if (throwError.toString().trim().equals(""))
			{
		       	// execute the API request using socket program.
		       	//String returnData = "OK";
		       	String returnData = inputSocket.mvxTrans(rec.toString());
		         	// This should return more than OK
		       	if (returnData.length() > 3 &&
		       		returnData.substring(0, 2).trim().equals("OK"))
		       	{
		       		tempOrder = returnData.substring(15, returnData.trim().length());
		      	}
		       	if (!returnData.substring(0, 2).trim().equals("OK"))
		       	{	
		       		// 	log ALL transaction attempts and results.
		       		APILog sendInfo = new APILog();	
		       		sendInfo.setApiName("OIS100MI");
		       		sendInfo.setApiMethod(rec.substring(0, 14));
		       		sendInfo.setEnvironment(inData.getEnvironment().trim());
		       		sendInfo.setSentFromProgram(inData.sentFromProgram.trim());
		       		sendInfo.setUserProfile(inData.getUserProfile().trim());
		       		sendInfo.setInputData(rec.toString());
		       		sendInfo.setOutputData(returnData);
//		       		ServiceAPILog.logAPITransaction(sendInfo);
		
   			// lets see the results on the console. -- For Debugging Purposes
			//   System.out.println("input string:" + inputData);
			//   System.out.println("return string:" + returnData);
		       	}
			}
		}
		catch (Exception e){
			throwError.append(e);
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.lawson.api.");
			throwError.append("OIS100MI.");
			throwError.append("addBatchHead(OIS100MIAddBatchHead, Obj(Socket))");
			throw new Exception(throwError.toString());
		}
		return tempOrder;
	}
/**
 * Add Line of a Temporary Order - file OXLine - review using OIS275  
 * Run and Log the API -- Log ONLY Problems found
 * -- TWalton 2/20/12
 *  Send in Data String and Open Socket
 *  Throw any Exception
 */	   
	private static void addBatchLine(String orderNumber,
									 OIS100MIAddBatchLine inData,
								     MvxSockJ inputSocket) 	
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		try {
			StringBuffer line = new StringBuffer(1024);
			line.setLength(1024); 	
			for (int y = 0; y < 653; y++) {
			     line.insert(0, " ");
			}
		 	// **************************************************
			//  Build the actual String which will be sent through the API
			try {
				 // build once EACH time this method is called
		 		  // Set Values into the String (from the OIS100MIAddBatchLine Class)	
				line.insert(0, "AddBatchLine");
				line.insert(15, inData.getCompany()); 
		        line.insert(18, orderNumber); 
				line.insert(28, inData.getItemNumber());
				line.insert(43, inData.getQuantity()); 
				line.insert(59, inData.getWarehouse());
				line.insert(256, inData.getUnitOfMeasure());
				line.insert(269, inData.getItemDescription());
				line.insert(561, inData.getDescription1());
		        line.setLength(653); // Adjust the length
		        
		        String returnValue = inputSocket.mvxTrans(line.toString());
			
			}
			catch(Exception e)
			{
			 	throwError.append("Error: Building String to send to OIS100MI AddBatchLine API: " + e);
			}
			
		}
		catch (Exception e){
			throwError.append(e);
		}
			
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.lawson.api.");
			throwError.append("OIS100MI.addBatchLine(String, OIS100MIAddBatchLine, Obj(Socket))");
			throw new Exception(throwError.toString());
		}
		return;
	}
	
}
