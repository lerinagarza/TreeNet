/*
 * Created on Nov 17, 2005
 * 
 *  Currently used to House URL's -- for images and documents
 *  Future uses for Comments
 */
package com.treetop.app.function;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.*;
import com.treetop.businessobjects.TicklerFunctionDetail;
import com.treetop.businessobjects.DateTime;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.viewbeans.BaseViewBean;
import com.treetop.data.UserFile;

/**
 * @author twalto
 *
 */
public class UpdFunctionDetail extends BaseViewBean {
	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	public String saveButton = null;
	public String userProfile = "";

	public Vector ticklerDetail = new Vector(); // TicklerFunctionDetail
	
	//UPD JSP Fields
	public String group = "";
	public String keyValue = "";
    public String functionCount = "0";
    // use this field to "START" the tickler list instead of today's date
    // filled in for testing
    public String estTargetDate = "";
	
	/**
	* Build drop down lists.
	*    Send in:
	* 		fieldName
	* 		fieldValue
	* 		readOnly
	*
	* Creation date: (12/5/2005 3:37:29 PM -- TWalton)
	*/
	public static String buildDropDownIDResponsiblePerson(
		String name,
		String value,
		String readOnly) {
			
		String returnString = "";
		try
		{	
		Vector ddList = new Vector();
		UserFile thisUser = new UserFile();
		Vector userList = thisUser.findUsersByGroup("38");
		for (int x = 0; x < userList.size(); x++)
		{
		    thisUser = (UserFile) userList.elementAt(x);
			DropDownSingle addOne = new DropDownSingle();
			addOne.setDescription(thisUser.getUserNameLong());
			addOne.setValue(thisUser.getUserName());
			ddList.add(addOne);
		}
		   returnString = DropDownSingle.buildDropDown(ddList, name, value, "Select One", "N", readOnly, "");
		}
		catch(Exception e)
		{
			System.out.println("Exception Occurred in UpdFunctionDetail.buildDropDownIDResponsiblePerson(name: " + 
			                   name + " value:" + value + " readOnly:" + readOnly);
		}
		if (returnString.equals(""))
		  returnString = "&nbsp;";
	    return returnString;
	}

	/*
	 * Use the Other Validate's ...
	 */
	public List validate() {
		Integer countOLD = new Integer(0);
		try {
			countOLD = new Integer(functionCount);
		} catch (Exception e) {
		}
		functionCount = countOLD.toString();

		return null;
	}
	
	/*
	 * Used to Populate the Tickler Information
	 */
	public void populateTickler(
	HttpServletRequest request,
	HttpServletResponse response) {
		if (saveButton != null) {
			//Get Today's Date
			DateTime todaysDate = UtilityDateTime.getSystemDate();
			//********************************************
			// ADD/Update Build Vector
			int countOLD = new Integer(functionCount).intValue();
			if (countOLD > 0) {
				ticklerDetail = new Vector();
				for (int x = 0; x < countOLD; x++) {
					TicklerFunctionDetail newElement = new TicklerFunctionDetail();					
					try
					{
					newElement.setGroup(group);
					newElement.setIdKeyValue(keyValue);
					if (request.getParameter("functionNumber" + x) != null)
					   newElement.setNumber(new Integer(request.getParameter("functionNumber" + x)));
					else
						newElement.setNumber(new Integer(0));
					if (request.getParameter("responsiblePerson" + x) != null)   
					   newElement.setRespPerson(request.getParameter("responsiblePerson" + x));
					else
						newElement.setRespPerson("");
					if (request.getParameter("targetDate" + x) != null)
					{
					   newElement.setTargetDate(request.getParameter("targetDate" + x));
					   try{
						   DateTime testDate = UtilityDateTime.getDateFromMMddyyyyWithSlash(newElement.getTargetDate());
						   if (!testDate.getDateErrorMessage().trim().equals(""))
							   newElement.setTargetDate(todaysDate.getDateFormatMMddyyyySlash());
						   
					   }catch(Exception e)
					   {
						   newElement.setTargetDate(todaysDate.getDateFormatMMddyyyySlash());
					   }
					}
					else
						newElement.setTargetDate("");
					if (request.getParameter("complete" + x) != null)
						newElement.setStatus("complete");
					else
					    newElement.setStatus("incomplete");					
					if (request.getParameter("completionDate" + x) != null &&
						!request.getParameter("completionDate" + x).equals("0"))
					{	
					    newElement.setCompletionDate(request.getParameter("completionDate" + x));
					    newElement.setStatus("complete");
					}    
					else
						newElement.setCompletionDate("");
					if (request.getParameter("completionTime" + x) != null)
					    newElement.setCompletionTime(new Integer(request.getParameter("completionTime" + x)));
					else
						newElement.setCompletionTime(new Integer(0));
					if (request.getParameter("completionUser" + x) != null)
					    newElement.setCompletionUser(request.getParameter("completionUser" + x));
					else
						newElement.setCompletionUser("");
					if ((newElement.getCompletionDate() == null ||
						 newElement.getCompletionDate().equals("0") ||
						 newElement.getCompletionDate().equals("")) &&
					    newElement.getStatus().equals("complete"))
					    newElement.setCompletionUser(SessionVariables.getSessionttiProfile(request, response));
					
					}
					catch(Exception e)
					{    
						System.out.println("Exception Occurred UpdFunctionDetail.PopulateTickler(request, response ): " +
						                   "Group: " + group + " KeyValue: " + keyValue + 
										   " FunctionNumber: " + request.getParameter("functionNumber" + x) + " :: " + e);
					}
					ticklerDetail.add(newElement);
				    
				}
			}
			//********************************************
		}

		return;
	}
	
	/**
	 * @return
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @return
	 */
	public String getSaveButton() {
		return saveButton;
	}

	/**
	 * @return
	 */
	public String getUserProfile() {
		return userProfile;
	}

	/**
	 * @param string
	 */
	public void setRequestType(String string) {
		requestType = string;
	}

	/**
	 * @param string
	 */
	public void setSaveButton(String string) {
		saveButton = string;
	}

	/**
	 * @param string
	 */
	public void setUserProfile(String string) {
		userProfile = string;
	}

	/**
	 * @return
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @return
	 */
	public String getKeyValue() {
		return keyValue;
	}

	/**
	 * @param string
	 */
	public void setGroup(String string) {
		group = string;
	}

	/**
	 * @param string
	 */
	public void setKeyValue(String string) {
		keyValue = string;
	}

	/**
	 * @return
	 */
	public String getFunctionCount() {
		return functionCount;
	}

	/**
	 * @return
	 */
	public Vector getTicklerDetail() {
		return ticklerDetail;
	}

	/**
	 * @param string
	 */
	public void setFunctionCount(String string) {
		functionCount = string;
	}

	/**
	 * @param vector
	 */
	public void setTicklerDetail(Vector vector) {
		ticklerDetail = vector;
	}

	/**
	* Build drop down lists.
	*    Send in:
	* 		formName -- Which will directly relate to application
	*       projectOwner -- Send in Profile of Project Owner -- if applicable
	* 		request
	* 		response
	*    Return:
	*       String[]
	*          [0] - is Administrator -- Y/N
	* 		   [1] - is ProjectOwner  -- Y/N
	*          [2] - UserProfile 
	*
	* Creation date: (12/21/2005 1:20:29 PM -- TWalton)
	*/
	public static String[] getSecurity(
			String formName,
			String projectOwner,
			HttpServletRequest request,
			HttpServletResponse response) {
			
		String[] returnString = new String[3];
		returnString[0] = "N";
		returnString[1] = "N";
		returnString[2] = SessionVariables.getSessionttiProfile(request, response);
		try
		{	
			if (returnString[2] == null)
			   returnString[2] = "";
			else
			{
				if (projectOwner != null &&
					!projectOwner.trim().equals("") &&
					projectOwner.toUpperCase().trim().equals(returnString[2].toUpperCase().trim()))
				    returnString[1] = "Y";	
				
				 try
				 {
				    String[] rolesR = SessionVariables.getSessionttiUserRoles(request, response);
				    for (int xr = 0; xr < rolesR.length; xr++)
				    {
				       if (rolesR[xr].equals("8"))
				          returnString[0] = "Y";
				    }
				 }
				 catch(Exception e)
				 {}
				 if (returnString[0].equals("N"))
				 {
				   try
				   {
				     String[] groupsR = SessionVariables.getSessionttiUserGroups(request, response);
				     for (int xr = 0; xr < groupsR.length; xr++)
				     {
				     	if (formName != null &&
				     		formName.trim().equals("updReservation"))
				     	{	
				           if (groupsR[xr].equals("39"))
				              returnString[0] = "Y";
				     	}   
				     }
				   }
				   catch(Exception e)
				   {}
				 }  				
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception Occurred in UpdFunctionDetail.getSecurity(formName: " + 
			                   formName + " projectOwner:" + projectOwner);
		}
	    return returnString;
	}

	public String getEstTargetDate() {
		return estTargetDate;
	}

	public void setEstTargetDate(String estTargetDate) {
		this.estTargetDate = estTargetDate;
	}

}
