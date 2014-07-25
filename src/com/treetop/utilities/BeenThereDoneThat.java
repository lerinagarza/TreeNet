/*
 * Created on Mar 6, 2006
 *
 */
package com.treetop.utilities;

import java.util.*;

import com.treetop.businessobjects.Carrier;

/**
 * @author thaile
 *
 * This class can be used to track if functions where performed
 * by specific users at a specific date and time.
 */
public class BeenThereDoneThat {
	
	String		user;
	String		date;
	String		time;

	/**
	 * 
	 */
	public BeenThereDoneThat() {
		super();
	}

	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return date;
	}
	
	
	/**
	 * Main testing.
	 */
	public static void main(String[] args)
	{
		// Test all that you can.
		String stophere = "";		
		Vector vector = null;
		
		try {
		//*** TEST "thereyet(BeenThereDoneThat, Vector)".
			if ("x".equals("x"))
			{
				vector = new Vector();
				
				BeenThereDoneThat old1 = new BeenThereDoneThat();
				old1.setUser("me");
				old1.setDate("03/06/2006");
				old1.setTime("120001");
				vector.addElement(old1);
				
				BeenThereDoneThat old2 = new BeenThereDoneThat();
				old2.setUser("me");
				old2.setDate("03/06/2006");
				old2.setTime("120002");
				vector.addElement(old2);
				
				BeenThereDoneThat old3 = new BeenThereDoneThat();
				old3.setUser("me");
				old3.setDate("03/06/2006");
				old3.setTime("120003");
				vector.addElement(old3);
				
				BeenThereDoneThat newOne = new BeenThereDoneThat();
				newOne.setUser("me");
				newOne.setDate("03/06/2006");
				newOne.setTime("120000");
				
				String answer = thereYet(newOne, vector);
				stophere = "x";
				newOne.setTime("120002");
				answer = thereYet(newOne, vector);
				stophere = "x";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	/**
	 * 
	 * @param newOne (BeenThereDoneThat).
	 * @param oldOnes (Vector of eenThereDoneThat).
	 * @return String (empty if ok).
	 * @throws Exception
	 */
	
	public static String thereYet(BeenThereDoneThat newOne, Vector oldOnes)
			throws Exception {
		
		String rtnMessage = "";
		StringBuffer throwError = new StringBuffer();
		
		try {
			for (int x = 0; oldOnes.size() > x && rtnMessage.trim().equals(""); x++)
			{
				BeenThereDoneThat oldOne = (BeenThereDoneThat) oldOnes.elementAt(x);
				
				if (oldOne.getUser().trim().equals(newOne.getUser().trim()) &&
					oldOne.getDate().trim().equals(newOne.getDate().trim()) &&
					oldOne.getTime().trim().equals(newOne.getTime().trim()))
				{
					rtnMessage = ("found one");
				}
			}
			
		} catch (Exception e) {
			throwError.append(" error trying to find use in array. " + e); 
		}
		
		//		return data.	
		if (!throwError.toString().equals("")) {
			throwError.append("Error at com.treetop.");
			throwError.append("BeenThereDoneThat.");
			throwError.append("thereYet(BeenThereDoneThat, Vector)");
			throw new Exception(throwError.toString());
		}
		
		return rtnMessage;
	}
	
	/**
	 * @param date The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return Returns the time.
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time The time to set.
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return Returns the user.
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user The user to set.
	 */
	public void setUser(String user) {
		this.user = user;
	}
}

