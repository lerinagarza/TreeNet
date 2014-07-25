/*
 * Created on March 16, 2006
 *
 */
package com.treetop.viewbeans;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import com.treetop.utilities.html.*;

import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;


/**
 * @author twalto
 *
 * This revision of the BaseViewBean has change:
 *     The abstract validate method will not be returning a List
 */
public class ParameterMessageBean {
	
	public static void printParameters(HttpServletRequest req) {
       try
       {
	    Enumeration parameterNames = req.getParameterNames();
	    while (parameterNames.hasMoreElements()){
	        String parameterName = (String) parameterNames.nextElement();
	        System.out.println(parameterName + ":" + req.getParameter(parameterName));
	    }
       }
	    catch(Exception e)
		{}
       }

	public static String returnParameters(HttpServletRequest req) {
		StringBuffer sb = new StringBuffer();
	   try
	   {
	    Enumeration parameterNames = req.getParameterNames();
	    while (parameterNames.hasMoreElements()){
	        String parameterName = (String) parameterNames.nextElement();
	        sb.append(parameterName + ":" + req.getParameter(parameterName));
	    }
	   }
	    catch(Exception e)
		{}
	    return sb.toString();
	   }
	   
	}
