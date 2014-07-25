package com.lawson.api;

import javax.servlet.http.HttpServletRequest;

import MvxAPI.MvxSockJ;

public class PMS170MI extends BaseAPI {
	
	/**
	 * Movex API to mass delete planned manufacturing orders
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static String massDelete(PMS170MIMassDelete in, String authorization)  throws Exception {

		StringBuffer throwError = new StringBuffer();
		String returnData = "";
		MvxSockJ socketObject = null;
		String rec = "";
		
		//get socket
		try {
			// build once EACH time this method is called
		
			socketObject = BaseAPI.getSockEnv("PMS170MI", in.getEnvironment(), authorization);

			// *****************************************************************
			// FOR TESTING USE TST ENVIRONMENT ONLY
			// socketObject = BaseAPI.getSockEnv("PMS170MI", "TST", authorization);
			// *****************************************************************
			
		} catch(Exception e) {
			throwError.append("Error: Cannot Open Socket Connection: " + e);
		}	

		//Build API String
		if (throwError.toString().trim().equals("")) {
			rec = in.toString();
		}

		//Perform API
		if (throwError.toString().trim().equals("")) {
			try {
				returnData = massDelete(rec, in, socketObject);
			} catch(Exception e) {
				throwError.append("Error : " + e);
			}
		}

		//Close the socket
		if (throwError.toString().trim().equals("")) {
			try {
				socketObject.mvxClose();
			} catch(Exception e) {
				throwError.append("Error closing socket: " + e);
			}
		}

		//handle errors
		if (!throwError.toString().trim().equals("")) {
			throwError.insert(0, "Error @ com.lawson.api.PMS170MI.massDelete():   ");
			throw new Exception(throwError.toString());
		}

		return returnData;
	}

	
	/**
	 * Movex API to mass delete planned manufacturing orders
	 * Runs the API and logs errors
	 * @param inputData
	 * @param inData
	 * @param inputSocket
	 * @return
	 * @throws Exception
	 */
	private static String massDelete (
			String inputData, 
			PMS170MIMassDelete inData,
			MvxSockJ inputSocket) throws Exception {
		StringBuffer throwError = new StringBuffer();
		String returnData = "";

		try {
			//perform the API
			returnData = inputSocket.mvxTrans(inputData);

			//if the transaction fails, log the event
			if (!returnData.trim().equals("OK") &&
					!returnData.trim().equals("")) {
				APILog sendInfo = new APILog();	
				sendInfo.setApiName("PMS170MI");
				sendInfo.setApiMethod(inputData.trim().substring(0, 14));
				sendInfo.setEnvironment(inData.getEnvironment().trim());
				sendInfo.setSentFromProgram(inData.sentFromProgram.trim());
				sendInfo.setUserProfile(inData.getUserProfile().trim());
				sendInfo.setInputData(inputData);
				sendInfo.setOutputData(returnData);
				ServiceAPILog.logAPITransaction(sendInfo);
				
				throwError.append("Could not delete Planned MOs.  " + returnData);
			}


		} catch(Exception e) {
			throwError.append(e.getMessage());
		}


		//handle errors
		if (!throwError.toString().trim().equals("")) {
			throwError.insert(0, "Error @ com.lawson.api.PMS170MI.massDelete():   ");
			throw new Exception(throwError.toString());
		}

		return returnData;
	}


}
