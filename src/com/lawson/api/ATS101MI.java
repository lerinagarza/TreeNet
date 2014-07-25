

package com.lawson.api;

import java.util.*;

import MvxAPI.MvxSockJ;

/**
 * Movex API ATS101MI with related functions (methods).
 * 
 * Generated: 2012-01-11 by JH
 * 
 */
public class ATS101MI extends BaseAPI{

	/**
	 * test API methods. 
	 */
	public static void main(String[] args) 
	{	

	}
	/**
	 * Add Reclassify Lot Status
	 * Run and Log the API
	 * -- TWalton 1/6/11
	 *  Send in Data String and Open Socket
	 *  Throw any Exception
	 */	   
	private static String setAttrValue(String inputData, 
			ATS101MISetAttrValue inData,
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
				sendInfo.setApiName("ATS101MI");
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
			throwError.append("ATS101MI.");
			throwError.append("setAttrValue(String, Obj(Socket))");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
	/**
	 * Run the Lawson API using the
	 *   Business Object, ATS101MISetAttrValue
	 * @param ATS101MISetAttrValue
	 * @throws Exception
	 */
	public static String setAttrValue(ATS101MISetAttrValue incomingInfo, String authorization)
	throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		
		MvxSockJ socketObject = null;
		try {
			// build once EACH time this method is called
			socketObject= BaseAPI.getSockEnv("ATS101MI", incomingInfo.getEnvironment(), authorization);

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
			for (int x = 0; x < 123; x++) {
				rec.insert(0, " ");
			}
			// **************************************************
			//  Build the actual String which will be sent through the API
			try {
	
				rec.insert(0, "SetAttrValue");
				rec.insert(15, incomingInfo.getCompany()); 				// CONO - Company 
				rec.insert(18, incomingInfo.getAttributeNumber()); 		// ATNR - Attribute Number
				rec.insert(35, incomingInfo.getAttributeID()); 			// ATID - Attribute ID
				//rec.insert(50, incomingInfo.getFromValue());			// AALF - From attribute value
				//rec.insert(65, incomingInfo.getToValue());			// AALT - To attribute value
				//rec.insert(80, incomingInfo.getTargeValue());			// ATAV - Target value
				rec.insert(95, incomingInfo.getAttributeValue());		// ATVA - Attribute value
				//rec.insert(110, incomingInfo.getRegistrationDate());	// RGDT - Registration date
				//rec.insert(118, incomingInfo.getRegistrationTime());	// RGTM - Registration time

				rec.setLength(123); // Adjust the length
			}
			catch(Exception e)
			{
				System.out.println("Error: Building String to send to ATS101MI SetAttrValue API: " + e);
				throwError.append("Error: Building String to send to ATS101MI SetAttrValue API: " + e);
			}
			if (throwError.toString().trim().equals(""))
			{
				try {
					returnData = setAttrValue(rec.toString(), incomingInfo, socketObject);
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
			throwError.append(" @ com.lawson.api.ATS101MI.");
			throwError.append("setAttrValue(ATS101MISetAttrValue)");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}	  

	
	public static Vector<String> setAttrValue(Vector<ATS101MISetAttrValue> objects) throws Exception {
		return setAttrValue(objects, "");
	}
	
	
	public static Vector<String> setAttrValue(Vector<ATS101MISetAttrValue> objects, String authorization) throws Exception {
		Vector<String> returnData = new Vector<String>();
		StringBuffer throwError = new StringBuffer();
		MvxSockJ socket = null;
		
		try {

			//do not process if the vector is empty
			if (!objects.isEmpty()) {
		
				//get the first object so you can get the environment
				ATS101MISetAttrValue firstObject = objects.elementAt(0);
				String environment =  firstObject.getEnvironment();
				if (environment.equals("")) {
					environment = "PRD";
				}
				//environment = "TST";
				socket = BaseAPI.getSockEnv("ATS101MI", environment, authorization);

				for (int i = 0; i < objects.size(); i++) {
					ATS101MISetAttrValue object = objects.elementAt(i);
					String returnString = "";
					returnString = "(" + object.attributeNumber.trim() + ")" +
								   "(" + object.attributeValue.trim() + ")" + 
					                setAttrValue(object.toString(), object, socket);
					returnData.addElement(returnString);
				}
			}
			
		} catch(Exception e) {
			throwError.append(e);
		} finally {
			if (socket != null) {
				try {
					socket.mvxClose();
				} catch (Exception e) {}
			}
			if (!throwError.toString().trim().equals("")) {
				throwError.append(" @ com.lawson.api.ATS101MI.");
				throwError.append("setAttrValue(Vector<ATS101MISetAttrValue>)");
				throw new Exception(throwError.toString());
			}
		}

		return returnData;
	}
	/**
	 * Add a comment into the text box for a 
	 *    Text Box Style Attribute
	 * Run and Log the API
	 * -- TWalton 5/8/13
	 *  Send in Data String and Open Socket
	 *  Throw any Exception
	 */	   
	private static String addItmLotAttrTx(String inputData, 
										  ATS101MIaddItmLotAttrTx inData,
										  MvxSockJ inputSocket) 	
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		try {
			// execute the API request using socket program.
			returnData = inputSocket.mvxTrans(inputData);
			
			if (!returnData.trim().equals("OK") &&
				!returnData.trim().equals(""))
			{
				// COMMENTED OUT, if needed can uncomment and the log will run
				// log ALL transaction attempts and results.
				APILog sendInfo = new APILog();	
				sendInfo.setApiName("ATS101MI");
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
			throwError.append("ATS101MI.");
			throwError.append("addItmLotAttrTx(String, ATS101MIaddItmLotAttrTx, Obj(Socket))");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
	/**
	 * Run the Lawson API using the
	 *   Business Object, ATS101MIAddAttr
	 * @param ATS101MIAddAttr
	 * @throws Exception
	 */
	public static String addAttr(ATS101MIAddAttr incomingInfo, String authorization)
	throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		
		MvxSockJ socketObject = null;
		
		try {
			// build once EACH time this method is called
			socketObject= BaseAPI.getSockEnv("ATS101MI", incomingInfo.getEnvironment(), authorization);
	
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
			for (int x = 0; x < 124; x++) {
				rec.insert(0, " ");
			}
			// **************************************************
			//  Build the actual String which will be sent through the API
			try {
				rec.insert(0, "AddAttr");
				rec.insert(15, incomingInfo.getCompany()); 				
				rec.insert(18, incomingInfo.getAttributeNumber());	
				rec.insert(35, incomingInfo.getAttributeID());				
					
				rec.setLength(124); 
			}
			catch(Exception e)
			{
				System.out.println("Error: Building String to send to ATS101MI addAttr API: " + e);
				throwError.append("Error: Building String to send to ATS101MI addAttr API: " + e);
			}
			if (throwError.toString().trim().equals(""))
			{
				try {
					returnData = addAttr(rec.toString(), incomingInfo, socketObject);
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
			throwError.append(" @ com.lawson.api.ATS101MI.");
			throwError.append("addAttr(ATS101MIaddAttr, String)");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
	/**
	 * Run the Lawson API using the
	 *   Business Object, ATS101MIaddItmLotAttrTx
	 * @param ATS101MIaddItmLotAttrTx
	 * @throws Exception
	 */
	public static String addItmLotAttrTx(ATS101MIaddItmLotAttrTx incomingInfo, String authorization)
	throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		
		MvxSockJ socketObject = null;
		try {
			// build once EACH time this method is called
			socketObject= BaseAPI.getSockEnv("ATS101MI", incomingInfo.getEnvironment(), authorization);
	
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
			for (int x = 0; x < 132; x++) {
				rec.insert(0, " ");
			}
			// **************************************************
			//  Build the actual String which will be sent through the API
			try {
				rec.insert(0, "addItmLotAttrTx");
				rec.insert(15, incomingInfo.getCompany()); 				// CONO - Company 
				rec.insert(18, incomingInfo.getAttributeID()); 			// ATID - Attribute ID
				rec.insert(33, incomingInfo.getItemNumber());			// ITNO - Item Number
				rec.insert(48, incomingInfo.getLotNumber());			// BANO - Lot Number
				//rec.insert(60, incomingInfo.getTextBlock());			// TXVR - Text Block
				//rec.insert(70, incomingInfo.getLanguage());			// LNCD - Language
				rec.insert(72, incomingInfo.getAdditionalTextLine());	// TX60 - Additional Text Line
	
				rec.setLength(132); // Adjust the length
			}
			catch(Exception e)
			{
				System.out.println("Error: Building String to send to ATS101MI addItmLotAttrTx API: " + e);
				throwError.append("Error: Building String to send to ATS101MI addItmLotAttrTx API: " + e);
			}
			if (throwError.toString().trim().equals(""))
			{
				try {
					returnData = addItmLotAttrTx(rec.toString(), incomingInfo, socketObject);
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
			throwError.append(" @ com.lawson.api.ATS101MI.");
			throwError.append("addItmLotAttrTx(ATS101MIaddItmLotAttrTx, String)");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
	/**
	 * Add an attribute to a lot
	 * Run and Log the API
	 * -- DEisen 6/24/13
	 *  Send in Data String and Open Socket
	 *  Throw any Exception
	 */	   
	private static String addAttr(String inputData, ATS101MIAddAttr inData, MvxSockJ inputSocket) 	
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		
		try {
			// execute the API request using socket program.
			returnData = inputSocket.mvxTrans(inputData);
			
			if (!returnData.trim().equals("OK") &&
				!returnData.trim().equals(""))
			{
				// COMMENTED OUT, if needed can uncomment and the log will run
				// log ALL transaction attempts and results.
				APILog sendInfo = new APILog();	
				sendInfo.setApiName("ATS101MI");
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
			throwError.append("ATS101MI.");
			throwError.append("addAttr(String, ATS101MIAddAttr, MvxSockJ");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}


}
