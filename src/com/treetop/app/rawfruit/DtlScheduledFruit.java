/*
 * Created on Aug 10, 2010
 *
 */
package com.treetop.app.rawfruit;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.*;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.viewbeans.CommonRequestBean;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.services.ServiceAvailableFruit;
import com.treetop.services.ServiceWarehouse;

/**
 *   Search for Scheduled Raw Fruit Load Information
 * @author twalto
 *
 */
public class DtlScheduledFruit extends BaseViewBeanR1  {

	
	
	// Standard Fields - to be in Every View Bean
	public String requestType   					= "";
	public String displayMessage 					= "";
	public String environment 						= "";
	public String originalRequestType				= "";

//	 Fields to be Used to Filter Data by User 	
	public String userProfile						= "";
	public String[] roles							= new String[0];
	
// Fields to be Used for Search INQ to LIST	
	public String companyNumber 					= "100";
	public String divisionNumber 					= "100";
	public String loadNumber						= "";
	
// update to record	
	public String cancelLoad						= "";
	public String deleteLoad						= "";
	public String receiveLoad						= "";
	public String unreceiveLoad						= "";
	public String comment							= "";
	public String emptyBins							= "0";
	
// Return information to the Screen	
	public BeanAvailFruit beanAvailFruit 			= null;
	public InqScheduledFruit inqSched	   			= new InqScheduledFruit();
	public Vector listComments						= new Vector();

	/* (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 */
	public void validate() {
	  try{
		// Make sure the Load Number is Valid
		if (!this.loadNumber.trim().equals(""))
		{
//			DateTime tdFrom = UtilityDateTime.getDateFromMMddyyyyWithSlash(this.inqDeliveryDateFrom);
//			this.inqDeliveryDateFrom = tdFrom.getDateFormatyyyyMMdd();
		}
	  }catch(Exception e)
	  {}
	  ///------------------------------------------
	  //  test Empty Bins - must be a number
	  if (!this.getEmptyBins().trim().equals("") &&
		  !this.getEmptyBins().trim().equals("0"))
	  {
	    try{
		  String testBins = validateInteger(this.emptyBins);
		  if (!testBins.equals(""))
			 this.setEmptyBins("0");
		  
	    }catch(Exception e){
		  this.setEmptyBins("0");
	    }
	  }
	}
	public String getDisplayMessage() {
		return displayMessage;
	}


	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}


	public String getRequestType() {
		return requestType;
	}


	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}


	/**
	 *  This method should be in EVERY Inquiry View Bean
	 *   Will create the vectors and generate the code for
	 *    MORE Button.
	 * @return
	 */
	public static String buildMoreButton(String requestType,
										 String environment,
										 String loadNumber,
										 String allowUpd) {
		
		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[7];
		String[] urlNames = new String[7];
		String[] newPage = new String[7];
		for (int z = 0; z < 7; z++) {
			urlLinks[z] = "";
			urlNames[z] = "";
			newPage[z] = "";
		}
//		if (allowUpd.equals("Y"))
	//	{
		urlLinks[0] = "/web/CtlRawFruit?requestType=updSchedFruit"
					+ "&environment=" + environment 
					+ "&loadNumber=" + loadNumber;
	    urlNames[0] = "Update Scheduled Load " + loadNumber;
	    newPage[0] = "Y";
		//}
//		urlLinks[1] = "/web/CtlRawFruit?requestType=inqAvailFruit"
//			+ "&environment=" + environment;
//		urlNames[1] = "Schedule Available Fruit ";
//		newPage[1] = "Y";
//		urlLinks[2] = "/web/CtlRawFruit?requestType=updAvailFruit"
//			+ "&environment=" + environment 
//			+ "&whseNumber=" + whseNumber
//			+ "&whseAddressNumber=" + whseLocationNumber;
//		urlNames[2] = "Review Warehouse Available Fruit ";
//		newPage[2] = "Y";
		return HTMLHelpers.buttonMore(urlLinks, urlNames, newPage);
	}

	/**
	* Determine Security For Adjustments to the More Button
	*    Send in:
	* 		request
	* 		response
	*    Return:
	*       String N/Y
	* Creation date: (8/5/2009 -- TWalton)
	*/
	public static String getSecurity(HttpServletRequest request,
									  HttpServletResponse response) {
			
		String returnString = "N";  
		
		try
		{
			 try
			 {
//			    String[] rolesR = SessionVariables.getSessionttiUserRoles(request, response);
	//		    for (int xr = 0; xr < rolesR.length; xr++)
		//	    {
			//       if (rolesR[xr].equals("8"))
			  //        returnString = "Y";
			    //}
			 }
			 catch(Exception e)
			 {}
			 if (returnString.equals("N"))
			 {
				   try
				   {
//				     String[] groupsR = SessionVariables.getSessionttiUserGroups(request, response);
	//			     for (int xr = 0; xr < groupsR.length; xr++)
		//		     {
			//	           if (groupsR[xr].equals("112")) //Raw Fruit Receiving Update
				//              returnString = "Y";
				  //   }
				   }
				   catch(Exception e)
				   {}
			 }  
		}
		catch(Exception e)
		{
			System.out.println("Exception Occurred in InqRawFruit.getSecurity(request, response)" + e);
		}
	    return returnString;
	}

	public String getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String[] getRoles() {
		return roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	public BeanAvailFruit getBeanAvailFruit() {
		return beanAvailFruit;
	}
	public void setBeanAvailFruit(BeanAvailFruit beanAvailFruit) {
		this.beanAvailFruit = beanAvailFruit;
	}
	public String getCompanyNumber() {
		return companyNumber;
	}
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	public String getDivisionNumber() {
		return divisionNumber;
	}
	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}
	public String getLoadNumber() {
		return loadNumber;
	}
	public void setLoadNumber(String loadNumber) {
		this.loadNumber = loadNumber;
	}
	public String getCancelLoad() {
		return cancelLoad;
	}
	public void setCancelLoad(String cancelLoad) {
		this.cancelLoad = cancelLoad;
	}
	public String getDeleteLoad() {
		return deleteLoad;
	}
	public void setDeleteLoad(String deleteLoad) {
		this.deleteLoad = deleteLoad;
	}
	public String getReceiveLoad() {
		return receiveLoad;
	}
	public void setReceiveLoad(String receiveLoad) {
		this.receiveLoad = receiveLoad;
	}
	public String getOriginalRequestType() {
		return originalRequestType;
	}
	public void setOriginalRequestType(String originalRequestType) {
		this.originalRequestType = originalRequestType;
	}
	public InqScheduledFruit getInqSched() {
		return inqSched;
	}
	public void setInqSched(InqScheduledFruit inqSched) {
		this.inqSched = inqSched;
	}
	public String getUnreceiveLoad() {
		return unreceiveLoad;
	}
	public void setUnreceiveLoad(String unreceiveLoad) {
		this.unreceiveLoad = unreceiveLoad;
	}
	public Vector getListComments() {
		return listComments;
	}
	public void setListComments(Vector listComments) {
		this.listComments = listComments;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getEmptyBins() {
		return emptyBins;
	}
	public void setEmptyBins(String emptyBins) {
		this.emptyBins = emptyBins;
	}
	
}