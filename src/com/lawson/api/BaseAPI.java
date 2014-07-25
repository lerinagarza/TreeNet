/*
 * Created on November 21, 2006
 *
 */
package com.lawson.api;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import com.treetop.Security;
import com.treetop.utilities.PropertyReader;

import MvxAPI.*;

/**
 * @author jhagle
 *
 */
public class BaseAPI {

	private static final String DEFAULT_AUTHORIZATION = "Basic ZGVpc2VuOmVleXlxcQ==";
	public static int timeouts = PropertyReader.getAPITimeoutAttempts();
	private static Hashtable<String,Integer> ports = PropertyReader.getAPIPorts();
	
	/**
	 * Build MvxSockJ object for an API by environment and uses a default authorization.
	 * @author jhagle
	 * @param apiName
	 * @param environment
	 * @param authorization
	 * @return
	 * @throws Exception
	 */
	public static MvxSockJ getSockEnv(String apiName, String environment) 
	throws Exception {

		//get a socket with a default authorization
		return getSockEnv(apiName, environment, DEFAULT_AUTHORIZATION);

	}

	/**
	 * Build MvxSockJ object for an API by environment and uses the request header authorization.
	 * This processes the API using the current user's profile and password
	 * @author jhagle
	 * @param apiName
	 * @param environment
	 * @param authorization
	 * @return
	 * @throws Exception
	 */
	public static MvxSockJ getSockEnv(String apiName, String environment, String authorization) 
	throws Exception {

		//if the authorization is null or empty, use a default authorization
		if (authorization == null || authorization.equals("")) {
			authorization = DEFAULT_AUTHORIZATION;
		}
		
		MvxSockJ socket;
		int i;

		/*  
		  Setup communication parameters:
		  FPW has host name intentia.se and is listening on port 6000.
		  We do NOT want encryption (0), last argument "" is ignored.
		 */

		//  Use property file to obtain host name
		String hostName = PropertyReader.getHostname();
		
		//  Go out and get the Port based on the Environment sent into this method
		//  Get port number from property file instead of utility class.
		//int port = Integer.parseInt(UtilityAPI.getPort(environment));
		int port = ports.get(environment.toUpperCase());  
		
		//Integer port = new Integer(UtilityAPI.getPort("TST"));
		// 10/28/11 Twalton - Change to point to new machine, and not use the IP address
		//obj2=new MvxSockJ("10.6.100.3",port.intValue(),"",0,"");
		socket=new MvxSockJ(hostName, port, "", 0, "");

		//  Use property file to obtain socket debug value (print in system log).
		boolean printDebug = PropertyReader.getAPIDebug();
		socket.DEBUG = printDebug;
		//socket.DEBUG = true; // Have MvxSockJ print out debug info

		/**
		 * For old FPW we give LOCALA as the system name, new FPW and NextGen does not care.
		 * We use the AS400 account USER with password PASSWORD
		 * We want to connect to program MMS200MI.
		 */
		//  Get the api system name used for for FPW from the property file 
		String apiSystemName = PropertyReader.getAPISystemName();

		String userProfile = Security.getProfile(authorization);
		String password = Security.getPassword(authorization);

		i = socket.mvxInit(apiSystemName, userProfile, password, apiName);
		//i = obj2.mvxInit("LOCALA", "DAUSER", "WEB230502", apiName);
		//   System.out.println(socket.toString());
		return socket;

	}
	
	
	/**
	 * test API methods. 
	 */
	public static void main(String[] args) 
	{	
		String debug = "Start Here";
		
		ports = PropertyReader.getAPIPorts();
		int port = ports.get("PRD"); 
		
		String hostName = PropertyReader.getHostname();
		
		debug = "Stop Here";
	}

}
