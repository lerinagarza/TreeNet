

package com.lawson.api;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import MvxAPI.MvxSockJ;

/**
 * Movex API MMS850MI with related functions (methods).
 * 
 * Generated: 1/22/2007 by twalto
 * 
 */
public class MMS850MI extends BaseAPI{
	/**
	 * Simple constructor. 
	 */
	public MMS850MI() {
		// Constructor for this Class
	} 
	/**
	 * test API mehods. 
	 */
	public static void main(String[] args) 
	{	
		// test the AddItemBasic method.
		if (1 == 1)
		{
			try {
				// a vector to hold the input data.
				Vector data = new Vector();

				// build a input string for testing.
				String x = "70120NONBAL        " +
				" NON-BALANCE RESOURCE          " +
				"NON-BALANCE RESOURCE              " + 
				"                                  " + "" +
				"            MARORE0   LB U001    " + "" +
				"W002    01 1 1                    ";
				// add the new string to the API input vector.
				data.addElement(x);

				// run the command.
				MMS850MI mi = new MMS850MI();
				//				mi.runAddAdjust(data);

				// let the console know your done.
				System.out.println("finished");
			}
			catch (Exception e) {
			}
		}
		if (1 == 1)
		{
			try {
				MMS850MIAddAdjust runData = new MMS850MIAddAdjust();
				runData.setEnvironment("TST");
				runData.setCompany("100");
				runData.setWarehouse("209");
				runData.setLocation("04631");
				runData.setItemNumber("181002");
				runData.setLotNumber("F5442569");
				runData.setQuantity("-60");
				Vector listTrans = new Vector();
				listTrans.addElement(runData);
			//	runData = new MMS850MIAddAdjust();
			//	runData.setEnvironment("TST");
			//	runData.setCompany("100");
			//	runData.setWarehouse("209");
			//	runData.setLocation("04279");
			//	runData.setItemNumber("181014");
			//	runData.setLotNumber("F5895291");
			//	runData.setQuantity("-1");
			//	listTrans.addElement(runData);
				addAdjust(listTrans);
				// let the console know your done.
				System.out.println("finished");
			}
			catch (Exception e) {
			}
		}

	}
	/**
	 * Add Reclassify Lot Status
	 * Run and Log the API
	 * -- TWalton 6/11/08
	 *  Send in Data String and Open Socket
	 *  Throw any Exception
	 */	   
	private static String addRclLotSts(String inputData, 
			MMS850MIAddRclLotSts inData,
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
				sendInfo.setApiName("MMS850MI");
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
			throwError.append("MMS850MI.");
			throwError.append("addRclLotSts(String, Obj(Socket))");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}

	/**
	 * Overloading addRclLotSts(MMS850MIAddRclLotSts incomingInfo) to execute with default authorization 
	 * @param incomingInfo
	 * @return
	 * @throws Exception
	 */
	public static String addRclLotSts(MMS850MIAddRclLotSts incomingInfo)
	throws Exception 
	{
		return addRclLotSts(incomingInfo, null);
	}


	/**
	 * Run the Lawson add API using the
	 *   Business Object, MMS850MIAddRclLotSts
	 * @param MMS850MIAddRclLotSts
	 * @throws Exception
	 */
	public static String addRclLotSts(MMS850MIAddRclLotSts incomingInfo, String authorization)
	throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		// Use the Incoming MMS850MIAddRclLotSts to build the API String
		MvxSockJ socketObject = null;
		try {
			// build once EACH time this method is called
			
			
			socketObject= BaseAPI.getSockEnv("MMS850MI", incomingInfo.getEnvironment(), authorization);
			//socketObject= BaseAPI.getSockEnv("MMS850MI", "TST", authorization);
						
			//socketObject = BaseAPI.getSockEnv("MMS850MI", "PRD");
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
			for (int x = 0; x < 400; x++) {
				rec.insert(0, " ");
			}
			// **************************************************
			//  Build the actual String which will be sent through the API
			try {
				// Set Values into the String (from the MMS850MIAddRclLotSts Class)	
				rec.insert(0, "AddRclLotSts");
				rec.insert(15, incomingInfo.getProcessFlag()); // PRFL - Process Flag - Directly Execute or Not
				// - Not Currently Needed - rec.insert(19, ); // CONO - Company 
				// - Not Currently Needed - rec.insert(22, ); // MSNR - Message Number
				// - Not Currently Needed - rec.insert(37, ); // MSLN - Message Line Number
				// - Not Currently Needed - rec.insert(42, ); // MSGS - Sequence Number 
				// - Not Currently Needed - rec.insert(45, ); // GEDT - Date Generated 
				// - Not Currently Needed - rec.insert(55, ); // GETM - Time Generated 
				rec.insert(61, incomingInfo.getPartner()); // E0PA - Partner -- RECLASSIFY
				rec.insert(78, incomingInfo.getMessageType()); // E065 - Message Type  -- INCUB / RECLAS
				rec.insert(84, incomingInfo.getWarehouse()); // WHLO - Warehouse
				rec.insert(87, incomingInfo.getItemNumber()); // ITNO - Item Number
				rec.insert(102, incomingInfo.getLotNumber()); // BANO - Lot Number
				// - Not Currently Needed - rec.insert(114, ); // BREF - Lot Reference 1 
				rec.insert(126, incomingInfo.getLotRef2()); // BRE2 - Lot Reference 2
				rec.insert(138, incomingInfo.getRemark()); // BREM - Remark
				// - Not Currently Needed - rec.insert(158, ); // POPN - Sequence Number 
				// - Not Currently Needed - rec.insert(188, ); // ALWQ - Alias Qualifier 
				// - Not Currently Needed - rec.insert(192, ); // ALWT - Alias Type     
				// - Not Currently Needed - rec.insert(194, ); // QLQT - Quantity 
				// - Not Currently Needed - rec.insert(211, ); // QLUN - Qualifier Unit of Measure
				// - Not Currently Needed - rec.insert(214, ); // QLDT - Transaction Date
				rec.insert(224, incomingInfo.getAllocatable()); // ALOC - Allocatable         
				// - Not Currently Needed - rec.insert(225, ); // CAWE - Catch Weight 
				rec.insert(242, incomingInfo.getTransactionReason()); // RSCD - Transaction Reason
				rec.insert(245, incomingInfo.getStatusBalanceID()); // STAS - Status - Balance ID 
				// - Not Currently Needed - rec.insert(246, ); // USD1 - User Defined 1                
				// - Not Currently Needed - rec.insert(261, ); // USD2 - User Defined 2 
				// - Not Currently Needed - rec.insert(276, ); // USD3 - User Defined 3
				// - Not Currently Needed - rec.insert(291, ); // USD4 - User Defined 4
				// - Not Currently Needed - rec.insert(308, ); // USD5 - User Defined 5 
				// - Not Currently Needed - rec.insert(321, ); // PMSN - External Message Number 
				rec.insert(338, incomingInfo.getExpirationDate()); // EXPI - Expiration Date     
				// - Not Currently Needed - rec.insert(348, ); // LPCY - Potency 
				rec.insert(355, incomingInfo.getFollowUpDate()); // CNDT - Follow Up Date
				rec.insert(365, incomingInfo.getSalesDate()); // SEDT - Sales Date	            
				// - Not Currently Needed - rec.insert(375, ); // RESP - Responsible	      
				rec.setLength(385); // Adjust the length
			}
			catch(Exception e)
			{
				System.out.println("Error: Building String to send to MMS850MI AddRclLotSts API: " + e);
				throwError.append("Error: Building String to send to MMS850MI AddRclLotSts API: " + e);
			}
			if (throwError.toString().trim().equals(""))
			{
				try {
					returnData = addRclLotSts(rec.toString(), incomingInfo, socketObject);
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
			throwError.append(" @ com.lawson.api.MMS850MI.");
			throwError.append("addRclLotSts(MMS850MIAddRclLotSts)");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}	
	
	/**
	 * Run the Lawson add API using the
	 *   Business Object, MMS850MIAddRclLotSts
	 * @param MMS850MIAddRclLotSts
	 * @throws Exception
	 */
	public static Vector<String> addMove(Vector<MMS850MIAddMove> transactions, String authorization)
	throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		Vector<String> returnData = new Vector<String>();

		if (transactions.isEmpty()) {
			return returnData;
		}
		
		MvxSockJ socketObject = null;
		try {
			
			MMS850MIAddMove first = transactions.elementAt(0);
			socketObject= BaseAPI.getSockEnv("MMS850MI", first.getEnvironment(), authorization);
		
		} catch(Exception e) {

			throwError.append("Error: Cannot Open Socket Connection: " + e);
		}	
		
		if (throwError.toString().trim().equals("")) {
			for (MMS850MIAddMove transaction : transactions) {
			
			
				// Set up the String	
				StringBuffer rec = new StringBuffer(1024);
				rec.setLength(1024); 	
				int transactionLength = 413;
				
				for (int x = 0; x < 400; x++) {
					rec.insert(0, " ");
				}
				// **************************************************
				//  Build the actual String which will be sent through the API
				try {
					
			 		rec.insert(0, "AddMove");  
			 		rec.insert(15, transaction.getProcess().trim()); 
			 		rec.insert(19, transaction.getCompany().trim());	 		
//			        transfer.insert(22, transaction.getMessageNumber());
//			        transfer.insert(37, transaction.getMessageLine());
//			        transfer.insert(42, transaction.getMessageSequence());//	        
			        rec.insert(45, transaction.getTransactionDate().trim());	
			        rec.insert(55, transaction.getTransactionTime().trim());	 		
			 		rec.insert(61, transaction.getPartner().trim());
			 		rec.insert(78, transaction.getMessageType().trim());	 		
			 		rec.insert(84, transaction.getWarehouse().trim());
			 		rec.insert(87, transaction.getWarehouseLocation().trim());
			 		rec.insert(97, transaction.getItemNumber().trim());
			 		rec.insert(112, transaction.getLotNumber().trim());
//			        transfer.insert(124, transaction.getContainer());
			 		rec.insert(136, transaction.getReceivingNumber().trim());	 		
//			        transfer.insert(146, transaction.getAliasNumber());
//			        transfer.insert(176, transaction.getAliasQuailifier());	 
//			        transfer.insert(180, transaction.getAliasType());
			 		rec.insert(182, transaction.getQuantity().trim());
//			        transfer.insert(199, transaction.getQuailifierUOM());
//			        transfer.insert(202, transaction.getQuailifierDate());	 		
			 		rec.insert(212, transaction.getToLocation().trim());	 		
//			        transfer.insert(222, transaction.getToContainer());
//			        transfer.insert(234, transaction.getCatchWeight());
			        rec.insert(251, transaction.getRemark());
			 		rec.insert(271, transaction.getLotReference1().trim());
			 		rec.insert(283, transaction.getLotReference2().trim());
//			        transfer.insert(295, transaction.getUserDefined1());
//			        transfer.insert(310, transaction.getUserDefined2());
//			        transfer.insert(325, transaction.getUserDefined3());  
//			        transfer.insert(340, transaction.getUserDefined4()); 
//			        transfer.insert(355, transaction.getUserDefined5());  
//			        transfer.insert(370, transaction.getTaskNumber());
//			        transfer.insert(380, transaction.getExtMessageNumber());
//			        transfer.insert(397, transaction.getQuailifierTime());
//			        transfer.insert(403, transaction.getPersonResponsible());	 

			 		rec.setLength(transactionLength);
					
					
				} catch(Exception e) {
					System.out.println("Error: Building String to send to MMS850MI AddRclLotSts API: " + e);
					throwError.append("Error: Building String to send to MMS850MI AddRclLotSts API: " + e);
				}
				
				
				if (throwError.toString().trim().equals("")) {
					try {
						
						String response = addMove(rec.toString(), transaction, socketObject);
						if (!response.equals("")) {
							returnData.addElement(
									"Item number: " + transaction.getItemNumber() + "  " + 
									"Lot number:" + transaction.getLotNumber() +  "  " + 
									response.trim());
						}
						
					} catch (Exception e) {
						//			 		System.out.println("Error: Running the API: " + e);					
						throwError.append("Error: Running the API: " + e);
					}
				} // End of the second If there is an error			
				
				
			
			}	//end for loop
		} // End of the first If there is an error
		
		
		try {
			// Close any open Socket Connections
			socketObject.mvxClose();
		} catch(Exception e) {
			//el.printStackTrace();
		}
		
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.lawson.api.MMS850MI.");
			throwError.append("addRclLotSts(MMS850MIAddRclLotSts)");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}	
	
	
	
	/**
	 * Add Reclassify Lot Status
	 * Run and Log the API
	 * -- TWalton 6/11/08
	 *  Send in Data String and Open Socket
	 *  Throw any Exception
	 */	   
	private static String addMove(String inputData, 
			MMS850MIAddMove inData,
			MvxSockJ inputSocket) 	
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		try {
			// execute the API request using socket program.
			returnData = inputSocket.mvxTrans(inputData);
			//String returnData = "test API Insert Information";
			if (!returnData.trim().equals("OK") &&
					!returnData.trim().equals(""))
			{
				// COMMENTED OUT, if needed can uncomment and the log will run
				// log ALL transaction attempts and results.
				APILog sendInfo = new APILog();	
				sendInfo.setApiName("MMS850MI");
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
			throwError.append("MMS850MI.");
			throwError.append("addRclLotSts(String, Obj(Socket))");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
	
	
	
	
	/**
	 * Run the Lawson API
	 *    Process through the vector using ONE Socket Open
	 *    
	 *   Business Object - MMS850MIAddAdjust for each Vector Element
	 *   
	 * @param Vector
	 * @throws Exception
	 */
		public static void addAdjust(Vector<MMS850MIAddAdjust> incomingList)
			throws Exception 
		{
			StringBuffer throwError = new StringBuffer();
			if (incomingList.isEmpty())
			{
				throwError.append("The Vector sent into the MMS850MIAddAdjust API is Empty");
			}else{
				// Open the API Socket
				MvxSockJ socketObject = null;
				try{
					
					try{
						MMS850MIAddAdjust firstElement = (MMS850MIAddAdjust) incomingList.elementAt(0);
						socketObject = BaseAPI.getSockEnv("MMS850MI", firstElement.getEnvironment());
					}catch(Exception e){
						throwError.append("Error: Cannot Open Socket Connection: " + e);
					}
					if (throwError.toString().trim().equals(""))
					{
						for (int x = 0; x < incomingList.size(); x++)
			    		{
			    			MMS850MIAddAdjust thisLine = (MMS850MIAddAdjust) incomingList.elementAt(x);
			    			try {
			    			   addAdjust(thisLine, socketObject);
			    			 }
			    			 catch(Exception e)
			    			 {
			    			 	throwError.append("Error: Processing the AddAdjust Method: " + e);
			    			 }	
			    		}
					}
				}catch(Exception e){
					
				}finally{
					try{
						socketObject.mvxClose();
					}catch(Exception e)
					{}
				}
			}
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append(" @ com.lawson.api.MMS850MI.");
				throwError.append("addAdjust(MMS850MIAddAdjust)");
				throw new Exception(throwError.toString());
			}
			return;
		}
	/**
	 * Use the AddAdjust method to adjust Inventory
	 * Will use a Cycle Count Adjustment in M3 Transaction Type 90  
	 * Run and Log the API -- Log ONLY Problems found
	 * -- TWalton 2/20/12
	 *  Send in Data String and Open Socket
	 *  Throw any Exception
	 */	   
		private static void addAdjust(MMS850MIAddAdjust inData,
									  MvxSockJ inputSocket) 	
			throws Exception
		{
			StringBuffer throwError = new StringBuffer();
			String returnData = "";
			try {
				StringBuffer line = new StringBuffer(1024);
				line.setLength(1024); 	
				for (int y = 0; y < 395; y++) {
				     line.insert(0, " ");
				}
			 	// **************************************************
				//  Build the actual String which will be sent through the API
				try {
					line.insert(0, "AddAdjust");
					line.insert(15, inData.getProcessFlag());
					line.insert(19, inData.getCompany()); 
			        line.insert(45, inData.getDateGenerated()); 
					line.insert(61, inData.getPartner());
					line.insert(78, inData.getMessageType()); 
					line.insert(84, inData.getWarehouse());
					line.insert(87, inData.getLocation());
					line.insert(97, inData.getItemNumber());
					line.insert(112, inData.getLotNumber());
					line.insert(182, inData.getQuantity());
					line.insert(199, inData.getUnitOfMeasure());
					line.insert(324, inData.getQualifierDate());
					line.insert(334, inData.getQualifierTime());
					line.insert(374, inData.getStatus());
			        line.setLength(395); // Adjust the length
			        
			        returnData = inputSocket.mvxTrans(line.toString());
			            
				}
				catch(Exception e)
				{
				 	throwError.append("Error: Building String to send to OIS100MI AddBatchLine API: " + e);
				}
		        if (!returnData.trim().equals("OK") &&
					!returnData.trim().equals(""))
					{
					// COMMENTED OUT, if needed can un comment and the log will run
					   // log ALL transaction attempts and results.
					   APILog sendInfo = new APILog();	
					   sendInfo.setApiName("MMS850MI");
					   sendInfo.setApiMethod("AddAdjust");
					   sendInfo.setEnvironment(inData.getEnvironment().trim());
					   sendInfo.setSentFromProgram(inData.sentFromProgram.trim());
					   sendInfo.setUserProfile(inData.getUserProfile().trim());
					   sendInfo.setInputData(line.toString());
					   sendInfo.setOutputData(returnData);
					   ServiceAPILog.logAPITransaction(sendInfo);
					}
			}
			catch (Exception e){
				throwError.append(e);
			}
				
			// test and throw error if needed.
			if (!throwError.toString().trim().equals(""))
			{
				throwError.append(" @ com.lawson.api.");
				throwError.append("MMS850MI.addAdjust(MMS850MIAddAdjust, Obj(Socket))");
				throw new Exception(throwError.toString());
			}
			return;
		}	   

}
