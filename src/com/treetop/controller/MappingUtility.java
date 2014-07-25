package com.treetop.controller;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;


public class MappingUtility {
	
	public static Hashtable<String, String> getMappingValues(HttpServletRequest request, UrlPathMapping pathMapping) {
		Hashtable<String, String> ht = new Hashtable<String, String>();
		
		try {
			String pathInfo = request.getPathInfo();
			String[] requestPaths = new String[0];
			
			if (pathInfo != null) {
				String decoded = URLDecoder.decode(pathInfo, "UTF-8");
				requestPaths = pathInfo.split("/");
			}
			
			String[] paths = pathMapping.value();
			
			for (int i=0; paths != null && i<paths.length; i++) {
				
				String key = paths[i];

				try {
					
					String value = requestPaths[i+1];
					if (value == null) {
						value = "";
					}
					
					ht.put(key, value);
					
				} catch (Exception e) {}
				
				
			}

			
		} catch (Exception e) {
			System.err.println("Exception @ MappingUtility.getMappingValues(request, pathMapping).  " + e);
		}
		
		return ht;
	}

}
