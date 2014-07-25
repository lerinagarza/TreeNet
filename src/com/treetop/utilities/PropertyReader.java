package com.treetop.utilities;

import java.io.*;
import java.util.*;

/**
 * Property Reader
 * Reads property files stored under "src" folder
 * @author jhagle Oct 2011  revised Apr 2012
 * added to TreeNet Feb 2014 wth
 * 
 */

public class PropertyReader {
	
	private static Properties applicationProperties = new Properties();
	
	// initialization block
	static {
		try {
			loadApplicationProperties();
			loadDevelopmentProperties();			
			
		} catch (Exception e) {
			System.out.println("Error loading application properties into memory.  " + e);
		}
	}
	
	/**
	 * Returns Properties object from file name
	 * @param fileName (file stored in "src" folder)
	 * @return properties
	 */
	private static Properties getProperties(String fileName) throws Exception {
		StringBuffer throwError = new StringBuffer();
		Properties properties = new Properties();

		try {
			//check to make sure a file name was entered
			if (!fileName.trim().equals("")) {
				//load the properties file from the "src" folder
				properties.load(Thread.currentThread()
						.getContextClassLoader()
						.getResourceAsStream(fileName + ".properties"));
			} else {
				throwError.append("No file name was passed in.  ");
			}
		} catch (Exception e){
			throwError.append("Error reading \"" + fileName + ".properties\":  " + e);
		} finally {
			if (!throwError.toString().trim().equals("")) {
				throwError.insert(0, "Error @ com.treetop.utilities.PropertyReader  ");
				throw new Exception (throwError.toString());
			}
		}

		return properties;
	}

	public static Properties getApplicationProperties() throws Exception {
		if (applicationProperties != null) {
			return applicationProperties;
		} else {
			throw new Exception("Properties are null, may not have been intalized");
		}

	}
	
	public static void loadApplicationProperties() throws Exception {
		StringBuffer throwError = new StringBuffer();
		Properties properties = new Properties();

		String fileName = "application";
		try {

			properties = getProperties(fileName);

		} catch (Exception e){
			throwError.append("Error reading \"" + fileName + ".properties\":  " + e);
		} finally {
			if (!throwError.toString().trim().equals("")) {
				throwError.insert(0, "Error @ com.treetop.utilities.PropertyReader.getConnectionProperties()  ");
				throw new Exception (throwError.toString());
			}
		}

		applicationProperties = properties;
	}
	
	public static void loadDevelopmentProperties() throws Exception {
		Properties properties = null;

		String fileName = "development";
		try {

			properties = getProperties(fileName);
			
			if (properties != null) {
				
				//overwrite/add development properties into applicationProperties
				Enumeration keys = properties.keys();
				while (keys.hasMoreElements()) {
					String key = (String) keys.nextElement();
					applicationProperties.setProperty(key, properties.getProperty(key));
				}
								
			}

		} catch (Exception e){
			//couldn't read file, just use application properties
			
		}
		
	}

	public static String getReleaseLevel() {
		return applicationProperties.getProperty("RELEASE_LEVEL","");
	}
	
	public static String getHostname() {
		return applicationProperties.getProperty("HOSTNAME","");
	}
	
	public static String getConnectionDriver() {
		return applicationProperties.getProperty("DRIVER","");
	}
	
	public static String getConnectionDriverClass() {
		return applicationProperties.getProperty("DRIVER_CLASS","");
	}
	
	public static String getDefaultEnvironment() {
		return applicationProperties.getProperty("DEFAULT_ENVIRONMENT","");
	}
	
	public static String getAllPurposeLibrary() {
		return applicationProperties.getProperty("ALL_PURPOSE_LIBRARY","");
	}
	
	public static String getAPISystemName() {
		return applicationProperties.getProperty("API_SYSTEM_NAME","");
	}
	
	public static boolean getAPIDebug() {
		return Boolean.parseBoolean(applicationProperties.getProperty("API_DEBUG","false"));
	}
	
	public static int getAPITimeout() {
		return Integer.parseInt(applicationProperties.getProperty("API_TIMEOUT","0"));
	}
	
	public static int getAPITimeoutAttempts() {
		return Integer.parseInt(applicationProperties.getProperty("API_TIMEOUT_ATTEMPTS","0"));
	}
	
	public static String getAS400Authorization() {
		return applicationProperties.getProperty("AS400_AUTHORIZATION","");
	}
	
	public static String getConnectionAuthorization() {
		return applicationProperties.getProperty("CONNECTION_AUTHORIZATION","");
	}
	
	public static String getLocalhostAuthorization() {
		return applicationProperties.getProperty("LOCALHOST_AUTHORIZATION","");
	}
	
	public static String getErrorMessage() {
		return applicationProperties.getProperty("ERROR_MESSAGE","");
	}
	
	public static boolean getPrintDebugParms() {
		return Boolean.parseBoolean(applicationProperties.getProperty("PRINT_DEBUG_PARMS","false"));
	}
	
	public static boolean isStatsLogEnabled() {
		return Boolean.parseBoolean(applicationProperties.getProperty("ENABLE_STAT_LOG","false"));
	}
	
	public static long getMessageRefreshInterval() {
		return Long.parseLong(applicationProperties.getProperty("MESSAGE_REFRESH_INTERVAL","0"));
	}
	
	
	
	
	/**
	 * Returns a Hashtable of port assignments by environment
	 * @return
	 * @author jhagle
	 */
	public static Hashtable getAPIPorts() {

		Hashtable<String, Integer> ports = new Hashtable<String, Integer>();
		
		Enumeration props = applicationProperties.propertyNames();
		
		int prdPort = 0;
		while (props.hasMoreElements()) {
			String key = (String) props.nextElement();
			if (key.startsWith("API_PORT")) {
				String env = key.substring(key.length()-3, key.length());
				
				int port = Integer.parseInt(applicationProperties.getProperty(key));
				ports.put(env, port);
				
				if (env.equals("PRD")) {
					prdPort = port;
				}
				
			}
		}
		
		// set a "blank" key (environment) to the PRD port
		ports.put("", prdPort);
		
		return ports;
	}

}
