package com.lawson.api;

import java.util.*;

import MvxAPI.MvxSockJ;

/**
 * Movex API PPS370MI with related functions (methods).
 * 
 * Generated: 6/25/09 by twalto
 * 
 */
public class PPS370MI extends BaseAPI{
   /**
	* Simple constructor. 
	*/
	public PPS370MI() {
		// Constructor for this Class
	} 
   /**
	* test API mehods. 
	*/
	public static void main(String[] args) 
	{	
	}
   /**
    * Start Entry
    *   used to create a batch to enter in all the pieces of a PO
	* Run and Log the API
	* -- TWalton 6/25/09
	*  Send in Data String and Open Socket
	*  Throw any Exception
	*/	   
	private static PPS370MIStartEntry startEntry(String inputData, 
									              PPS370MIStartEntry returnData,
									              MvxSockJ inputSocket) 	
		throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		try {
			// execute the API request using sockit program.
			String programReturnData = inputSocket.mvxTrans(inputData);
			if (programReturnData.substring(0, 3).equals("NOK"))
			{
				returnData.setErrorMessage(programReturnData);
				//	 COMMENTED OUT, if needed can uncomment and the log will run
			   // log ALL transaction attempts and results.
			   APILog sendInfo = new APILog();	
			   sendInfo.setApiName("PPS370MI");
			   sendInfo.setApiMethod(inputData.trim().substring(0, 14));
			   sendInfo.setEnvironment(returnData.getEnvironment().trim());
			   sendInfo.setSentFromProgram(returnData.sentFromProgram.trim());
			   sendInfo.setUserProfile(returnData.getUserProfile().trim());
			   sendInfo.setInputData(inputData);
			   sendInfo.setOutputData(programReturnData);
			   ServiceAPILog.logAPITransaction(sendInfo);	
			}
			else
			{
				returnData.setMessageNumber(programReturnData.trim().substring(15));
			}
		}
		catch (Exception e){
			throwError.append(e);
		}
		// test and throw error if needed.
		if (!throwError.toString().trim().equals(""))
		{
			throwError.append(" @ com.lawson.api.");
			throwError.append("PPS370MI.");
			throwError.append("startEntry(String, PPS370MIStartEntry, Obj(Socket))");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
   /**
	* Run the Lawson add API using the
	*   Business Object, PPS370MIAddHead
	* @param PPS370MIAddHead
	* @throws Exception
	*/
	public static PPS370MIAddHead addHead(PPS370MIAddHead incomingInfo, String authorization)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		PPS370MIAddHead returnData = new PPS370MIAddHead();
		// Use the Incoming PPS370MIAddHead to build the API String
		MvxSockJ socketObject = null;
		try {
			 // build once EACH time this method is called
			socketObject= BaseAPI.getSockEnv("PPS370MI", incomingInfo.getEnvironment(), authorization);			
			//socketObject = BaseAPI.getSockEnv("PPS370MI", "TST", authorization);
		 }
		 catch(Exception e)
		 {
		 	System.out.println("Error: Cannot Open Socket Connection: " + e);
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
		 	  // Set Values into the String (from the PPS370MIAddHead Class)	
				rec.insert(0, "AddHead");
	            rec.insert(15, incomingInfo.getMessageNumber()); 				// MSGN 
	            rec.insert(30, incomingInfo.getFacility()); 					// FACI
	            rec.insert(33, incomingInfo.getWarehouse()); 					// WHLO 
	            rec.insert(36, incomingInfo.getSupplierNumber()); 				// SUNO 
	            rec.insert(46, incomingInfo.getRequestedDeliveryDate()); 		// DWDT
	            //rec.insert(56, incomingInfo.getPurchaseOrderHeadReference()); 	// HREF
	            //rec.insert(76, incomingInfo.getOrderType()); 					// ORTY
	            //rec.insert(79, incomingInfo.getCommunicationCode()); 			// CMCO
	            rec.insert(81, incomingInfo.getOrderDate()); 					// PUDT
	            //rec.insert(91, incomingInfo.getLanguage()); 					// LNCD
	            //rec.insert(93, incomingInfo.getCurrency()); 					// CUCD
	            //rec.insert(96, incomingInfo.getPaymentTerms()); 				// TEPY
	            //rec.insert(99, incomingInfo.getPaymentMethodAP()); 				// PYME
	            //rec.insert(102, incomingInfo.getDeliveryMethod()); 				// MODL
	            //rec.insert(105, incomingInfo.getDeliveryTerms()); 				// TEDL
	            //rec.insert(108, incomingInfo.getFreightTerms()); 				// TEAF
	            //rec.insert(111, incomingInfo.getPackagingTerms()); 				// TEPA
	            //rec.insert(114, incomingInfo.getYourReference()); 				// YRE1
	            //rec.insert(150, incomingInfo.getPayee()); 						// PRSU
	            //rec.insert(160, incomingInfo.getOurReferenceNumber()); 			// OURR
	            //rec.insert(170, incomingInfo.getReferenceType()); 				// OURT
	            //rec.insert(171, incomingInfo.getRecipientAgreementType1()); 	// AGNT
	            //rec.insert(181, incomingInfo.getRequisitionBy()); 				// PURC
	            //rec.insert(191, incomingInfo.getBuyer()); 						// BUYE
	            //rec.insert(201, incomingInfo.getMonitoringActivityList()); 		// FUSC
	            //rec.insert(204, incomingInfo.getFacimileTransmissionNumber()); 	// TFNO
	            //rec.insert(220, incomingInfo.getLastReplyDate()); 				// LRED
	            //rec.insert(230, incomingInfo.getTermsText()); 					// TEL1
	            //rec.insert(266, incomingInfo.getDueDate()); 					// DUDT
	            //rec.insert(276, incomingInfo.getCurrencyTerms()); 				// CUTE
	            //rec.insert(277, incomingInfo.getAgreedRate()); 					// AGRA
	            //rec.insert(290, incomingInfo.getProjectNumber()); 				// PROJ
	            //rec.insert(297, incomingInfo.getProjectElement()); 				// ELNO
	            //rec.insert(305, incomingInfo.getHarborOrAirport()); 			// HAFE
	            //rec.insert(311, incomingInfo.getUserDefined1()); 				// USD1
	            //rec.insert(326, incomingInfo.getUserDefined2());			 	// USD2
	            //rec.insert(341, incomingInfo.getUserDefined3()); 				// USD3
	            //rec.insert(356, incomingInfo.getUserDefined4()); 				// USD4
	            //rec.insert(371, incomingInfo.getUserDefined5()); 				// USD5
	            rec.setLength(386); // Adjust the length
		 	}
		 	catch(Exception e)
			{
		 		System.out.println("Error: Building String to send to PPS370MI AddHead API: " + e);
			 	throwError.append("Error: Building String to send to PPS370MI AddHead API: " + e);
			}
	        if (throwError.toString().trim().equals(""))
			{
				try {
					returnData = addHead(rec.toString(), incomingInfo, socketObject);
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
			throwError.append(" @ com.lawson.api.PPS370MI.");
			throwError.append("AddHead(PPS370MIAddHead)");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
/**
	* Run the Lawson add API using the
	*   Business Object, PPS370MIStartEntry
	* @param PPS370MIStartEntry
	* @throws Exception
	*/
	public static PPS370MIStartEntry startEntry(PPS370MIStartEntry incomingInfo, String authorization)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		PPS370MIStartEntry returnData = new PPS370MIStartEntry();
		// Use the Incoming PPS370MIStartEntry to build the API String
		MvxSockJ socketObject = null;
		try {
			 // build once EACH time this method is called
			socketObject= BaseAPI.getSockEnv("PPS370MI", incomingInfo.getEnvironment(), authorization);			
			//socketObject = BaseAPI.getSockEnv("PPS370MI", "TST", authorization);
		 }
		 catch(Exception e)
		 {
		 	System.out.println("Error: Cannot Open Socket Connection: " + e);
		 	throwError.append("Error: Cannot Open Socket Connection: " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
		   // Set up the String	
		 	StringBuffer rec = new StringBuffer(1024);
			rec.setLength(1024); 	
			for (int x = 0; x < 30; x++) {
			     rec.insert(0, " ");
			}
		 	// **************************************************
		 	//  Build the actual String which will be sent through the API
		 	try {
		 	  // Set Values into the String (from the PPS370MIStartEntry Class)	
				rec.insert(0, "StartEntry");
	            rec.insert(15, incomingInfo.getBatchOrigin()); // BAOR - Batch Origin
	            rec.setLength(25); // Adjust the length
		 	}
		 	catch(Exception e)
			{
		 		System.out.println("Error: Building String to send to PPS370MI StartEntry API: " + e);
			 	throwError.append("Error: Building String to send to PPS370MI StartEntry API: " + e);
			}
	        if (throwError.toString().trim().equals(""))
			{
				try {
					returnData = startEntry(rec.toString(), incomingInfo, socketObject);
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
			throwError.append(" @ com.lawson.api.PPS370MI.");
			throwError.append("StartEntry(PPS370MIStartEntry)");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
/**
* Add Head
*   used to create a Header Record in M3
* Run and Log the API
* -- TWalton 6/26/09
*  Send in Data String and Open Socket
*  Throw any Exception
*/	   
private static PPS370MIAddHead addHead(String inputData, 
								          PPS370MIAddHead returnData,
								          MvxSockJ inputSocket) 	
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	try {
		// execute the API request using sockit program.
		String programReturnData = inputSocket.mvxTrans(inputData);
		if (programReturnData.substring(0, 3).equals("NOK"))
		{
			returnData.setErrorMessage(programReturnData);
			//	 COMMENTED OUT, if needed can uncomment and the log will run
		   // log ALL transaction attempts and results.
		   APILog sendInfo = new APILog();	
		   sendInfo.setApiName("PPS370MI");
		   sendInfo.setApiMethod(inputData.trim().substring(0, 14));
		   sendInfo.setEnvironment(returnData.getEnvironment().trim());
		   sendInfo.setSentFromProgram(returnData.sentFromProgram.trim());
		   sendInfo.setUserProfile(returnData.getUserProfile().trim());
		   sendInfo.setInputData(inputData);
		   sendInfo.setOutputData(programReturnData);
		   ServiceAPILog.logAPITransaction(sendInfo);	
		}
		else
		{
			returnData.setPurchaseOrderNumber(programReturnData.trim().substring(15));
		}
	}
	catch (Exception e){
		throwError.append(e);
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.lawson.api.");
		throwError.append("PPS370MI.");
		throwError.append("addHead(String, PPS370MIAddHead, Obj(Socket))");
		throw new Exception(throwError.toString());
	}
	return returnData;
}
/**
	* Run the Lawson add API using the
	*   Business Object, PPS370MIAddLine
	* @param PPS370MIAddLine
	* @throws Exception
	*/
	public static PPS370MIAddLine addLine(PPS370MIAddLine incomingInfo, String authorization)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		PPS370MIAddLine returnData = new PPS370MIAddLine();
		// Use the Incoming PPS370MIAddHead to build the API String
		MvxSockJ socketObject = null;
		try {
			 // build once EACH time this method is called
			socketObject= BaseAPI.getSockEnv("PPS370MI", incomingInfo.getEnvironment(), authorization);			
			//socketObject = BaseAPI.getSockEnv("PPS370MI", "TST", authorization);
		 }
		 catch(Exception e)
		 {
		 	System.out.println("Error: Cannot Open Socket Connection: " + e);
		 	throwError.append("Error: Cannot Open Socket Connection: " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
		   // Set up the String	
		 	StringBuffer rec = new StringBuffer(1024);
			rec.setLength(1024); 	
			for (int x = 0; x < 600; x++) {
			     rec.insert(0, " ");
			}
		 	// **************************************************
		 	//  Build the actual String which will be sent through the API
		 	try {
		 	  // Set Values into the String (from the PPS370MIAddLine Class)	
				rec.insert(0, "AddLine");
	            rec.insert(15, incomingInfo.getMessageNumber()); 				// MSGN 
	            rec.insert(30, incomingInfo.getPurchaseOrderNumber());			// PUNO
	            //rec.insert(37, incomingInfo.getPurchaseOrderHeadReference());	// HREF 
	            //rec.insert(57, incomingInfo.getPurchaseOrderLine());			// PNLI 
	            //rec.insert(60, incomingInfo.getPurchaseOrderLineReference()); 	// LREF
	            rec.insert(80, incomingInfo.getItemNumber()); 					// ITNO
	            rec.insert(95, incomingInfo.getOrderQuantityAltUOM());			// ORQA
	            //rec.insert(112, incomingInfo.getFacility()); 					// FACI
	            //rec.insert(115, incomingInfo.getWarehouse()); 					// WHLO
	            //rec.insert(118, incomingInfo.getSupplierNumber());				// SUNO
	            rec.insert(128, incomingInfo.getRequestedDeliveryDate()); 		// DWDT
	            //rec.insert(138, incomingInfo.getSupplierItemNumber());			// SITE
	            //rec.insert(168, incomingInfo.getPurchaseOrderItemName());		// PITD
	            //rec.insert(198, incomingInfo.getPurchaseOrderItemDescripion());	// PITT
	            //rec.insert(258, incomingInfo.getManufacturer()); 				// PROD
	            //rec.insert(268, incomingInfo.getRevisionNumber1());				// ECVE
	            //rec.insert(272, incomingInfo.getRevisionNumber2()); 			// REVN
	            //rec.insert(274, incomingInfo.getExternalInstruction()); 		// ETRF
	            //rec.insert(277, incomingInfo.getPurchasePrice());				// PUPR
	            //rec.insert(296, incomingInfo.getDiscount1());		 			// ODI1
	            //rec.insert(303, incomingInfo.getDiscount2()); 					// ODI2
	            //rec.insert(310, incomingInfo.getDiscount3()); 					// ODI3
	            //rec.insert(317, incomingInfo.getPurchaseOrderUOM());			// PUUN
	            //rec.insert(320, incomingInfo.getPurchasePriceUOM());			// PPUN
	            //rec.insert(323, incomingInfo.getPurchasePriceQuantity()); 		// PUCD
	            //rec.insert(328, incomingInfo.getPurchasePriceText()); 			// PTCD
	            //rec.insert(329, incomingInfo.getReferenceOrderCategory());		// RORC
	            //rec.insert(330, incomingInfo.getReferenceOrderNumber());		// RORN
	            //rec.insert(340, incomingInfo.getReferenceOrderLine());			// RORL
	            //rec.insert(346, incomingInfo.getLineSuffix()); 					// RORX
	            //rec.insert(349, incomingInfo.getOurReferenceNumber());			// OURR
	            //rec.insert(359, incomingInfo.getReferenceType()); 				// OURT
	            //rec.insert(360, incomingInfo.getPriority());	 				// PRIP
	            //rec.insert(361, incomingInfo.getMonitoringActivityList());		// FUSC
	            //rec.insert(364, incomingInfo.getRequisitonBy()); 				// PURC
	            //rec.insert(374, incomingInfo.getBuyer());			 			// BUYE
	            //rec.insert(384, incomingInfo.getTechnicalSupervisor());			// TERE
	            //rec.insert(394, incomingInfo.getGoodsReceivingMethod());		// GRMT
	            //rec.insert(397, incomingInfo.getRecipient()); 					// IRCV
	            //rec.insert(417, incomingInfo.getPackaging()); 					// PACT
	            //rec.insert(423, incomingInfo.getVatCode());						// VTCD
	            //rec.insert(425, incomingInfo.getUserDefinedAccountingControlObject()); 	// ACRF
	            //rec.insert(433, incomingInfo.getCostCenter()); 					// COCE
	            //rec.insert(441, incomingInfo.getCustomsStatisticalNumber());	// CSNO
	            //rec.insert(457, incomingInfo.getLaborCodeTradeStatistics());	// ECLC
	            //rec.insert(459, incomingInfo.getBusinessTypeTradeStatistics());	// VRCD
	            //rec.insert(461, incomingInfo.getProjectNumber()); 				// PROJ
	            //rec.insert(468, incomingInfo.getProjectElement());				// ELNO
	            //rec.insert(476, incomingInfo.getCustomsProcedureImport());		// CPRI
	            //rec.insert(481, incomingInfo.getHarborOrAirport()); 			// HAFE
	            //rec.insert(487, incomingInfo.getTaxCodeCustomerAddress());		// TAXC
	            //rec.insert(490, incomingInfo.getTimeHoursAndMinutes());			// TIHM
	            //rec.insert(494, incomingInfo.getMilestoneChain());				// MSTN
	            //rec.insert(504, incomingInfo.getUnpack());						// UPCK
	            //rec.insert(505, incomingInfo.getUserDefinedField1()); 			// USD1
	            //rec.insert(520, incomingInfo.getUserDefinedField2()); 			// USD2
	            //rec.insert(535, incomingInfo.getUserDefinedField3()); 			// USD3
	            //rec.insert(550, incomingInfo.getUserDefinedField4()); 			// USD4
	            //rec.insert(565, incomingInfo.getUserDefinedField5()); 			// USD5
	            
	            rec.setLength(580); // Adjust the length
		 	}
		 	catch(Exception e)
			{
		 		System.out.println("Error: Building String to send to PPS370MI AddHead API: " + e);
			 	throwError.append("Error: Building String to send to PPS370MI AddHead API: " + e);
			}
	        if (throwError.toString().trim().equals(""))
			{
				try {
					returnData = addLine(rec.toString(), incomingInfo, socketObject);
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
			throwError.append(" @ com.lawson.api.PPS370MI.");
			throwError.append("AddHead(PPS370MIAddHead)");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
/**
* Add Line
*   used to create a Line Record in M3
* Run and Log the API
* -- TWalton 6/26/09
*  Send in Data String and Open Socket
*  Throw any Exception
*/	   
private static PPS370MIAddLine addLine(String inputData, 
								       PPS370MIAddLine returnData,
								       MvxSockJ inputSocket) 	
throws Exception
{
	StringBuffer throwError = new StringBuffer();
	try {
		// execute the API request using sockit program.
		String programReturnData = inputSocket.mvxTrans(inputData);
		if (programReturnData.substring(0, 3).equals("NOK"))
		{
			returnData.setErrorMessage(programReturnData);
			//	 COMMENTED OUT, if needed can uncomment and the log will run
		   // log ALL transaction attempts and results.
		   APILog sendInfo = new APILog();	
		   sendInfo.setApiName("PPS370MI");
		   sendInfo.setApiMethod(inputData.trim().substring(0, 14));
		   sendInfo.setEnvironment(returnData.getEnvironment().trim());
		   sendInfo.setSentFromProgram(returnData.sentFromProgram.trim());
		   sendInfo.setUserProfile(returnData.getUserProfile().trim());
		   sendInfo.setInputData(inputData);
		   sendInfo.setOutputData(programReturnData);
		   ServiceAPILog.logAPITransaction(sendInfo);	
		}
		else
		{
			returnData.setPurchaseOrderNumberOUT(programReturnData.trim().substring(15, 22));
			returnData.setPurchaseOrderLineOUT(programReturnData.trim().substring(22));
		}
	}
	catch (Exception e){
		throwError.append(e);
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.lawson.api.");
		throwError.append("PPS370MI.");
		throwError.append("addLine(String, PPS370MIAddLine, Obj(Socket))");
		throw new Exception(throwError.toString());
	}
	return returnData;
}
/**
	* Run the Lawson add API using the
	*   Business Object, PPS370MIFinishEntry
	* @param PPS370MIFinishEntry
	* @throws Exception
	*/
	public static PPS370MIFinishEntry finishEntry(PPS370MIFinishEntry incomingInfo, String authorization)
		throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		PPS370MIFinishEntry returnData = new PPS370MIFinishEntry();
		// Use the Incoming PPS370MIStartEntry to build the API String
		MvxSockJ socketObject = null;
		try {
			 // build once EACH time this method is called
			socketObject= BaseAPI.getSockEnv("PPS370MI", incomingInfo.getEnvironment(), authorization);			
			//socketObject = BaseAPI.getSockEnv("PPS370MI", "TST", authorization);
		 }
		 catch(Exception e)
		 {
		 	System.out.println("Error: Cannot Open Socket Connection: " + e);
		 	throwError.append("Error: Cannot Open Socket Connection: " + e);
		 }	
		 if (throwError.toString().trim().equals(""))
		 {
		   // Set up the String	
		 	StringBuffer rec = new StringBuffer(1024);
			rec.setLength(1024); 	
			for (int x = 0; x < 30; x++) {
			     rec.insert(0, " ");
			}
		 	// **************************************************
		 	//  Build the actual String which will be sent through the API
		 	try {
		 	  // Set Values into the String (from the PPS370MIFinishEntry Class)	
				rec.insert(0, "FinishEntry");
	            rec.insert(15, incomingInfo.getMessageNumber()); // MSGN 
	            rec.setLength(25); // Adjust the length
		 	}
		 	catch(Exception e)
			{
		 		System.out.println("Error: Building String to send to PPS370MI FinishEntry API: " + e);
			 	throwError.append("Error: Building String to send to PPS370MI FinishEntry API: " + e);
			}
	        if (throwError.toString().trim().equals(""))
			{
				try {
					returnData = finishEntry(rec.toString(), incomingInfo, socketObject);
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
			throwError.append(" @ com.lawson.api.PPS370MI.");
			throwError.append("FinishEntry(PPS370MIFinishEntry)");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
/**
* Finish Entry
*   used to finalize and process a batch entered with all the pieces of a PO
* Run and Log the API
* -- TWalton 6/26/09
*  Send in Data String and Open Socket
*  Throw any Exception
*/	   
private static PPS370MIFinishEntry finishEntry(String inputData, 
								              PPS370MIFinishEntry returnData,
								              MvxSockJ inputSocket) 	
	throws Exception
{
	StringBuffer throwError = new StringBuffer();
	try {
		// execute the API request using socket program.
		String programReturnData = inputSocket.mvxTrans(inputData);
		if (programReturnData.substring(0, 3).equals("NOK"))
		{
			returnData.setErrorMessage(programReturnData);
			//	 COMMENTED OUT, if needed can uncomment and the log will run
		   // log ALL transaction attempts and results.
		   APILog sendInfo = new APILog();	
		   sendInfo.setApiName("PPS370MI");
		   sendInfo.setApiMethod(inputData.trim().substring(0, 14));
		   sendInfo.setEnvironment(returnData.getEnvironment().trim());
		   sendInfo.setSentFromProgram(returnData.sentFromProgram.trim());
		   sendInfo.setUserProfile(returnData.getUserProfile().trim());
		   sendInfo.setInputData(inputData);
		   sendInfo.setOutputData(programReturnData);
		   ServiceAPILog.logAPITransaction(sendInfo);	
		}
		else
			returnData.setMessageNumber(programReturnData.trim());
	}
	catch (Exception e){
		throwError.append(e);
	}
	// test and throw error if needed.
	if (!throwError.toString().trim().equals(""))
	{
		throwError.append(" @ com.lawson.api.");
		throwError.append("PPS370MI.");
		throwError.append("finishEntry(String, PPS370MIFinishEntry, Obj(Socket))");
		throw new Exception(throwError.toString());
	}
	return returnData;
}	   
}
