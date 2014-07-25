package com.lawson.api;

import java.util.Vector;

import MvxAPI.MvxSockJ;

public class BUS100MI {
	
	
	/**
	 * Run the Lawson add API using the
	 *   Business Object, BUS100MIDelBudgetLines
	 * @param BUS100MIAddBudgetLines
	 * @throws Exception
	 */
	public static String addBudgetLines(BUS100MIAddBudgetLines incomingInfo)
	throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		// Use the Incoming MMS024MIAddAlias to build the API String
		MvxSockJ socketObject = null;
		try {
			// build once EACH time this method is called
			socketObject= BaseAPI.getSockEnv("BUS100MI", incomingInfo.getEnvironment(), incomingInfo.getAuthorization());

		}
		catch(Exception e)
		{
			//System.out.println("Error: Cannot Open Socket Connection: " + e);
			throwError.append("Error: Cannot Open Socket Connection: " + e);
		}	
		
			
		if (throwError.toString().trim().equals("")) {
			
			try {
				
				returnData = addBudgetLines(incomingInfo.toString(), incomingInfo, socketObject);
				incomingInfo.setResponse(returnData);
				
			} catch (Exception e) {
				//			 		System.out.println("Error: Running the API: " + e);					
				throwError.append("Error: Running the API: " + e);
			}
			

			
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
			throwError.append(" @ com.lawson.api.BUS100MI.");
			throwError.append("addBudgetLines(BUS100MIAddBudgetLines)");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}	 
	
	
	
	public static Vector<BUS100MIAddBudgetLines> addBudgetLines(Vector<BUS100MIAddBudgetLines> objects) throws Exception {
		Vector<String> returnData = new Vector<String>();
		StringBuffer throwError = new StringBuffer();
		MvxSockJ socket = null;
		try {

			//do not process if the vector is empty
			if (!objects.isEmpty()) {
		
				//get the first object so you can get the environment
				BUS100MIAddBudgetLines firstObject = objects.elementAt(0);
				String environment =  firstObject.getEnvironment();
				if (environment.equals("")) {
					environment = "PRD";
				}
				
				socket = BaseAPI.getSockEnv("BUS100MI", environment, firstObject.getAuthorization());

				for (BUS100MIAddBudgetLines object : objects) {
					try {
						String returnString = "";
						
						returnString = addBudgetLines(object.toString(), object, socket);
						
						object.setResponse(returnString);
						
						returnData.addElement(returnString);
					} catch (Exception e) {
						throwError.append(object.toString() + "\n" + e);
					}
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
				throwError.append(" @ com.lawson.api.BUS100MI.");
				throwError.append("addBudgetLines(Vector<BUS100MIAddBudgetLines>)");
				throw new Exception(throwError.toString());
			}
		}


		return objects;
	}
	
	
	
	/**
	 * Delete Budget Lines
	 * Run and Log the API
	 * -- JHAGLE 2/27/2013
	 *  Send in Data String and Open Socket
	 *  Throw any Exception
	 */	   
	private static String addBudgetLines(String inputData, 
			BUS100MIAddBudgetLines inData,
			MvxSockJ inputSocket) 	
	throws Exception
	{
		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		try {
			// execute the API request using socket program.
			returnData = inputSocket.mvxTrans(inputData);
			
			if (returnData.startsWith("NOK")) {
				returnData = returnData + "\n\t" + inputData ;
			}
			
			//String returnData = "test API Insert Information";
			if (!returnData.trim().equals("OK") &&
					!returnData.trim().equals(""))
			{
				// COMMENTED OUT, if needed can uncomment and the log will run
				// log ALL transaction attempts and results.
				APILog sendInfo = new APILog();	
				sendInfo.setApiName("BUS100MI");
				sendInfo.setApiMethod(inputData.trim().substring(0, 14));
				sendInfo.setEnvironment(inData.getEnvironment().trim());
				sendInfo.setSentFromProgram(inData.getSentFromProgram().trim());
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
			throwError.append("BUS100MI.");
			throwError.append("addBudgetLines(String, Obj(Socket))");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}
	
	
	/**
	 * Run the Lawson add API using the
	 *   Business Object, BUS100MIDelBudgetLines
	 * @param BUS100MIAddBudgetLines
	 * @throws Exception
	 */
	public static String delBudgetLines(BUS100MIDelBudgetLines incomingInfo)
	throws Exception 
	{
		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		// Use the Incoming MMS024MIAddAlias to build the API String
		MvxSockJ socketObject = null;
		try {
			// build once EACH time this method is called
			socketObject= BaseAPI.getSockEnv("BUS100MI", incomingInfo.getEnvironment(), incomingInfo.getAuthorization());
	
		}
		catch(Exception e)
		{
			//System.out.println("Error: Cannot Open Socket Connection: " + e);
			throwError.append("Error: Cannot Open Socket Connection: " + e);
		}	
		
			
		if (throwError.toString().trim().equals("")) {
			
			try {
				returnData = delBudgetLines(incomingInfo.toString(), incomingInfo, socketObject);
			} catch (Exception e) {
				//			 		System.out.println("Error: Running the API: " + e);					
				throwError.append("Error: Running the API: " + e);
			}
			
	
			
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
			throwError.append(" @ com.lawson.api.BUS100MI.");
			throwError.append("addBudgetLines(BUS100MIDelBudgetLines)");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}



	/**
	 * Delete Budget Lines
	 * Run and Log the API
	 * -- JHAGLE 2/27/2013
	 *  Send in Data String and Open Socket
	 *  Throw any Exception
	 */	   
	private static String delBudgetLines(String inputData, 
			BUS100MIDelBudgetLines inData,
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
				sendInfo.setApiName("BUS100MI");
				sendInfo.setApiMethod(inputData.trim().substring(0, 14));
				sendInfo.setEnvironment(inData.getEnvironment().trim());
				sendInfo.setSentFromProgram(inData.getSentFromProgram().trim());
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
			throwError.append("BUS100MI.");
			throwError.append("delBudgetLines(String, Obj(Socket))");
			throw new Exception(throwError.toString());
		}
		return returnData;
	}



	public static Vector<BUS100MIDelBudgetLines> delBudgetLines(Vector<BUS100MIDelBudgetLines> objects) throws Exception {
		Vector<String> returnData = new Vector<String>();
		StringBuffer throwError = new StringBuffer();
		MvxSockJ socket = null;
		try {
	
			//do not process if the vector is empty
			if (!objects.isEmpty()) {
		
				//get the first object so you can get the environment
				BUS100MIDelBudgetLines firstObject = objects.elementAt(0);
				String environment =  firstObject.getEnvironment();
				if (environment.equals("")) {
					environment = "PRD";
				}
				
				socket = BaseAPI.getSockEnv("BUS100MI", environment, firstObject.getAuthorization());
	
				for (BUS100MIDelBudgetLines object : objects) {
					
					String returnString = delBudgetLines(object.toString(), object, socket);
					
					object.setResponse(returnString);
					
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
	
	
		return objects;
	}
	

}
