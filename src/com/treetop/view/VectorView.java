package com.treetop.view;


import java.text.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import javax.sql.*;

/**
 * Access to view application information.
 *
 **/
public class VectorView {
	

	// Data base fields.
	
	public   Vector   viewName;	
	public   Vector   viewData;

/**
 * VectorView constructor.
 */
public VectorView() {
	
	super();
}
/**
 * Retrieve the vector containing the data (information) for the view.
 *
 * Creation date: (03/09/2004 10:53:28 AM)
 */
public Vector getViewData() 
{
	return viewData;
}
/**
 * Retrieve the vector containing the name (description) of the view.
 *
 * Creation date: (03/09/2004 10:53:28 AM)
 */
public Vector getViewName() 
{
	return viewName;
}
/**
 * Test the methods.
 *
 * Creation date: (03/09/2004 1:20:32 PM) 
 * @param args java.lang.String[]
 */
public static void main(String[] args) 
{

	try {


			
	    System.out.println("View: " + " successfull");
	}
	catch(Exception e)
	{
		System.out.println("VectorView error: " + e); 
	}	


}
/**
 * Set the vector containing the data (information) for the view.
 *
 * Creation date: (03/09/2004 10:53:28 AM)
 */
public void setViewData(Vector inViewData) {
		
	this.viewData = inViewData;
}
/**
 * Set the vector containing the name (description) of the view.
 *
 * Creation date: (03/09/2004 10:53:28 AM)
 */
public void setViewName(Vector inViewName) {
		
	this.viewName = inViewName;
}
}
