/*
 * Created on Sept 25, 2008 
 *
 */

package com.treetop.app.transaction;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.utilities.html.DropDownDual;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.businessobjects.DateTime;
import com.treetop.services.ServiceFinance;
import com.treetop.services.ServiceTransaction;

/**
 * @author twalto
 * 
 * Will use for Update of Variances by Item
 *   ONE Variance at a time - to be updated
 */
public class UpdTransaction extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	// Must have in Update View Bean
	public String updateUser = "";
	
	public String goButton = "";
	public String goDailyButton = "";
	public String goAllButton = "";
	
	public String fiscalYear = "";
	public String fiscalWeek = "";
	
	public String securityType = "None";
	
	/*
	 * Test and Validate fields, after loading them.
	 *  Set Errors into the Error Fields of this View Bean
	 * IF there are any.
	 * 
	 */
	public void validate() {
			
		return;
	}
	/**
	 * @return
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param string
	 */
	public void setRequestType(String string) {
		requestType = string;
	}

	/**
	 * @return Returns the updateUser.
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * @param updateUser The updateUser to set.
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * @return Returns the displayMessage.
	 */
	public String getDisplayMessage() {
		return displayMessage;
	}
	/**
	 * @param displayMessage The displayMessage to set.
	 */
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}
	/**
	 * @return Returns the goButton.
	 */
	public String getGoButton() {
		return goButton;
	}
	/**
	 * @param goButton The goButton to set.
	 */
	public void setGoButton(String goButton) {
		this.goButton = goButton;
	}
	/**
	 *      Method created 9/25/08  TWalton
	 * Will Return to the Screen,String which is the code for the drop down list
	 *  
	 */
	public static Vector buildDropDownYearPeriod(String inYear, String inPeriod) {
		Vector dd = new Vector();
		try
		{
		   // Build the Vector of Utility - DropDownDual	
		   Vector dualDD = new Vector();
		   Vector returnDates = ServiceTransaction.listYearPeriodUsageReport("all");
		   
		   	  // Get the list of Dates
		   if (returnDates.size() > 0)
		   {
		       for (int y = 0; y < returnDates.size(); y++)
		   	   {
		   	      DateTime dt = (DateTime) returnDates.elementAt(y);
		   	      if (dt.getDateErrorMessage().equals(""))
		   	      {	
		   	   	     DropDownDual addOne = new DropDownDual();
		   	   	     addOne.setMasterValue(dt.getM3FiscalYear());
		   	   	     addOne.setMasterDescription(dt.getM3FiscalYear());
		   	   	     addOne.setSlaveValue(dt.getM3FiscalPeriod());
		   	   	     addOne.setSlaveDescription(dt.getM3FiscalPeriod());
		   	      	 dualDD.addElement(addOne);
		   	      }
		   	   	}
		     }
		   // Call the Build utility to build the actual code for the drop down.
		   dd = DropDownDual.buildDualDropDown(dualDD, "fiscalYear", "N", "", "fiscalWeek", "N", "N", "N");
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	/**
	 *      Method created 9/25/08  TWalton
	 * Process Security information and put value into Will Return to the Screen,String which is the code for the drop down list
	 *  
	 */
	public void getSecurity(HttpServletRequest request,
			 				HttpServletResponse response) {
		try
		{ 
			String[] rolesR = SessionVariables.getSessionttiUserRoles(request, response);
			for (int xr = 0; xr < rolesR.length; xr++) {
			  if (rolesR[xr].equals("8"))
				this.securityType = "IS"; // IS for Information Services
			}
		}
		catch(Exception e)
		{
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return;
	}
	/**
	 * @return Returns the fiscalWeek.
	 */
	public String getFiscalWeek() {
		return fiscalWeek;
	}
	/**
	 * @param fiscalWeek The fiscalWeek to set.
	 */
	public void setFiscalWeek(String fiscalWeek) {
		this.fiscalWeek = fiscalWeek;
	}
	/**
	 * @return Returns the fiscalYear.
	 */
	public String getFiscalYear() {
		return fiscalYear;
	}
	/**
	 * @param fiscalYear The fiscalYear to set.
	 */
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	/**
	 * @return Returns the goAllButton.
	 */
	public String getGoAllButton() {
		return goAllButton;
	}
	/**
	 * @param goAllButton The goAllButton to set.
	 */
	public void setGoAllButton(String goAllButton) {
		this.goAllButton = goAllButton;
	}
	/**
	 * @return Returns the goDailyButton.
	 */
	public String getGoDailyButton() {
		return goDailyButton;
	}
	/**
	 * @param goDailyButton The goDailyButton to set.
	 */
	public void setGoDailyButton(String goDailyButton) {
		this.goDailyButton = goDailyButton;
	}
	/**
	 * @return Returns the securityType.
	 */
	public String getSecurityType() {
		return securityType;
	}
	/**
	 * @param securityType The securityType to set.
	 */
	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}
}
