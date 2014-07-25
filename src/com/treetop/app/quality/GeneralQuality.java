/*
 * Created on August 2, 2011
 */
package com.treetop.app.quality;

import java.math.BigDecimal;
import java.util.Vector;

import com.treetop.businessobjects.DateTime;
import com.treetop.data.UserFile;
import com.treetop.services.ServiceAttribute;
import com.treetop.services.ServiceUser;
import com.treetop.services.ServiceQuality;
import com.treetop.utilities.GeneralUtility;
import com.treetop.utilities.UtilityDateTime;
import com.treetop.utilities.html.DropDownSingle;
import com.treetop.utilities.html.DropDownDual;
import com.treetop.utilities.html.HTMLHelpersMasking;
import com.treetop.viewbeans.CommonRequestBean;

/**
 * @author twalto
 * 
 * Use for Standard Information Throughout the CtlQuality Application
 */
public class GeneralQuality {
	
	private static Vector<DropDownSingle> listType	 		  = new Vector<DropDownSingle>();
	private static Vector<DropDownSingle> listApprovedByUser  = new Vector<DropDownSingle>();
	private static Vector<DropDownSingle> listOrigination	  = new Vector<DropDownSingle>();
	private static Vector<DropDownSingle> listScope			  = new Vector<DropDownSingle>();
	private static Vector<DropDownSingle> listStatus		  = new Vector<DropDownSingle>();
	private static Vector<DropDownSingle> listUOM			  = new Vector<DropDownSingle>();
	private static Vector<DropDownDual>   listCropVariety	  = new Vector<DropDownDual>();
	private static Vector<DropDownSingle> listCustomerCode	  = new Vector<DropDownSingle>();
	
	/*
	 * Format what the DATE looks like for the Screen
	 * 
	 */
	public static String formatDateForScreen(String inDate) {
		 try{
		       if (inDate.trim().equals("0"))
		    	  inDate = "";
		  	   if (!inDate.trim().equals(""))
			   {
			     DateTime dt1 = UtilityDateTime.getDateFromyyyyMMdd(inDate);
				 inDate = dt1.getDateFormatMMddyyyySlash();
			   }  	
		    }catch(Exception e)
		    {
		    	//Will return the inDate if an exception is caught
		    }
			return inDate;
	}
	/*
	 * Format what the TIME looks like for the Screen
	 * 
	 */
	public static String formatTimeForScreen(String inTime) {
		try{
		       if (inTime.trim().equals("0"))
		    	  inTime = "";
		  	   if (!inTime.trim().equals(""))
			   {
		  		 DateTime tm1 = UtilityDateTime.getTimeFromhhmmss(inTime);
				 inTime = tm1.getTimeFormathhmmssColon();
			   }  	
		    }catch(Exception e)
		    {
		    	//Will return the inDate if an exception is caught
		    }
			return inTime;
	}
	/*
	 * This method will return a Whole name for the screen instead of the Profile
	 *    -- specific for this Entire Application // anything to do with CtlQuality---
	 *    // Will be used by ALL screens in this Application
	 * 
	 */
	public static String findLongNameFromProfile(String environment, String formatUser) {
		 try{
			   if (environment == null || environment.equals(""))
				   environment = "PRD";
			   if (formatUser == null)
				   formatUser = "";
			   else
			   {
			 	  UserFile uf = new UserFile(formatUser.toUpperCase());
				  if (!uf.getUserNameLong().trim().equals(""))
				    formatUser = uf.getUserNameLong().trim();
				  else
				    formatUser = ServiceUser.returnNameFromM3User(environment, formatUser);
			   }
		    }catch(Exception e)
		    {
		    	//Will return the inDate if an exception is caught
		    }
			return formatUser;
	}
	/**
	 * Will build the Vectors to be used on the Screen
	 *    Will hold them as objects until -- the session releases them
	 */
	private void buildDropDown() {
		 
		CommonRequestBean crb = new CommonRequestBean();
		crb.setCompanyNumber("100");
		crb.setDivisionNumber("100");
		crb.setEnvironment("PRD");
		
		if (listType == null)
			listType = new Vector<DropDownSingle>();
		if (listType.isEmpty())
		{
//			System.out.println("needType");
			try{
			   listType = ServiceQuality.dropDownType(crb);
		    }catch(Exception e){
			   listType = new Vector<DropDownSingle>();
			}
		}
		if (listApprovedByUser == null)
		   listApprovedByUser = new Vector<DropDownSingle>();
		if (listApprovedByUser.isEmpty())
		{
//			System.out.println("needApprovedByUser");
			try{
			   crb.setIdLevel2("130"); // 130 is the list for the Approved By User
			   listApprovedByUser = ServiceUser.dropDownByGroup(crb);
			}catch(Exception e)
			{
			   listApprovedByUser = new Vector<DropDownSingle>();
			}
		}
		if (listOrigination == null)
		    listOrigination = new Vector<DropDownSingle>();
		if (listOrigination.isEmpty())
		{
//			System.out.println("needOriginaton");
			try{
				crb.setIdLevel2("131"); // 131 is the list for the Origination (Source)
			    listOrigination = ServiceUser.dropDownByGroup(crb);
			}catch(Exception e)
			{
			   listOrigination = new Vector<DropDownSingle>();
			}
		}
		if (listScope == null)
		    listScope = new Vector<DropDownSingle>();
		if (listScope.isEmpty())
		{
//			System.out.println("needScope");
			try{
				listScope = ServiceQuality.dropDownScope(crb);
			}catch(Exception e)
			{
			   listScope = new Vector<DropDownSingle>();
			}
		}
		if (listStatus == null)
		    listStatus = new Vector<DropDownSingle>();
		if (listStatus.isEmpty())
		{
//			System.out.println("needStatus");
			try{
				crb.setIdLevel1("dropDownStatus"); // 131 is the list for the Origination (Source)
			    listStatus = ServiceQuality.dropDownStatus(crb);
			}catch(Exception e)
			{
			   listStatus = new Vector<DropDownSingle>();
			}
		}
		if (listCropVariety == null)
		    listCropVariety = new Vector<DropDownDual>();
		if (listCropVariety.isEmpty())
		{
//			System.out.println("needCropVariety");
			try{
				
				listCropVariety = ServiceAttribute.dropDownCropVariety(crb.getEnvironment(), "");

			}catch(Exception e)
			{
			   listCropVariety = new Vector<DropDownDual>();
			}
		}
		if (listUOM == null)
		    listUOM = new Vector<DropDownSingle>();
		if (listUOM.isEmpty())
		{
//			System.out.println("needUOM");
			try{
				
				listUOM = GeneralUtility.dropDownUnitOfMeasure(crb.getEnvironment(), crb.getCompanyNumber());

			}catch(Exception e)
			{
			   listUOM = new Vector<DropDownSingle>();
			}
		}		
		if (listCustomerCode == null)
		    listCustomerCode = new Vector<DropDownSingle>();
		if (listCustomerCode.isEmpty())
		{
//			System.out.println("needCustomerCode");
			try{
				crb.setIdLevel1("Customer Code");
			    listCustomerCode	= ServiceQuality.dropDownGenericSingle(crb);
			}catch(Exception e)
			{
			   listCustomerCode = new Vector<DropDownSingle>();
			}
		}		
		return;
	}
	/**
	 * Will return a String -- Will allow to Approve the Object?
	 *    Is this person in Group 130??
	 *   Will allow to work with in ANY 
	 */
	public String securityToApprove(String userProfile) {
		 
		String allowApprove = "N";
		// Go through the list of Approved by User
		//   test to see if the user Profile is in the list
		//   if YES they have access to approve the object
		if (listApprovedByUser.size() > 0)
		{
			for (int x = 0; x < listApprovedByUser.size(); x++)
			{
				if (userProfile.trim().equals(listApprovedByUser.elementAt(x).getValue().trim()))
				{
					allowApprove = "Y";
					x = listApprovedByUser.size();
				}
			}
		}
		return allowApprove;
	}
	/**
	 * Will return a String -- Will allow to Approve the Object?
	 *    Is this person in Group 130??
	 *   Will allow to work with in ANY 
	 */
	public String securityForPending(String userProfile) {
		 
		String allowApprove = "N";
		// Go through the list of Approved by User
		//   test to see if the user Profile is in the list
		//   if YES they have access to approve the object
		if (listOrigination.size() > 0)
		{
			for (int x = 0; x < listOrigination.size(); x++)
			{
				if (userProfile.trim().equals(listOrigination.elementAt(x).getValue().trim()))
				{
					allowApprove = "Y";
					x = listOrigination.size();
				}
			}
		}
		return allowApprove;
	}
	/**
	 *  This method will return a formatted time for the screen
	 *    -- specific for this Entire Application ---
	 *    // Will be used by ALL screens in this Application
	 * @return
	 */
	public static String formatPercent(String inPercent) {
	    try{
	    	
	    	if (!inPercent.trim().equals("")) {
		       BigDecimal bdPct = new BigDecimal(inPercent);
		       inPercent = (bdPct.multiply(new BigDecimal("100"))).toString();
		       inPercent = HTMLHelpersMasking.mask2Decimal(inPercent);
	    	}
	    	
	    }catch(Exception e)
	    {
	    	//Will return the inDate if an exception is caught
	    }
		return inPercent;
	}
	/**
	 * Used to Instantiate the RandDFormula Class.
	 *       All the fields will be null.
	 *
	 * Creation date: (6/13/2003 8:42:39 AM)
	 */
	public GeneralQuality() 
	{
		if (listApprovedByUser == null ||
			listApprovedByUser.isEmpty() ||
			listOrigination == null ||
			listOrigination.isEmpty() ||
			listScope == null ||
			listScope.isEmpty() ||
			listStatus == null ||
			listStatus.isEmpty() ||
			listCropVariety == null ||
			listCropVariety.isEmpty() ||
			listUOM == null ||
			listUOM.isEmpty() ||
			listType == null ||
			listType.isEmpty() ||
			listCustomerCode == null ||
			listCustomerCode.isEmpty())
			buildDropDown();
		return;
	}
	public static Vector<DropDownSingle> getListApprovedByUser() {
		return listApprovedByUser;
	}
	public static Vector<DropDownSingle> getListOrigination() {
		return listOrigination;
	}
	public static Vector<DropDownSingle> getListScope() {
		return listScope;
	}
	public static Vector<DropDownSingle> getListStatus() {
		return listStatus;
	}
	public static Vector<DropDownDual> getListCropVariety() {
		return listCropVariety;
	}
	public static Vector<DropDownSingle> getListUOM() {
		return listUOM;
	}
	public static Vector<DropDownSingle> getListCustomerCode() {
		return listCustomerCode;
	}
	public static Vector<DropDownSingle> getListType() {
		return listType;
	}
	public static void setListType(Vector<DropDownSingle> listType) {
		GeneralQuality.listType = listType;
	}
	public static void setListApprovedByUser(
			Vector<DropDownSingle> listApprovedByUser) {
		GeneralQuality.listApprovedByUser = listApprovedByUser;
	}
	public static void setListOrigination(Vector<DropDownSingle> listOrigination) {
		GeneralQuality.listOrigination = listOrigination;
	}
	public static void setListScope(Vector<DropDownSingle> listScope) {
		GeneralQuality.listScope = listScope;
	}
	public static void setListStatus(Vector<DropDownSingle> listStatus) {
		GeneralQuality.listStatus = listStatus;
	}
	public static void setListUOM(Vector<DropDownSingle> listUOM) {
		GeneralQuality.listUOM = listUOM;
	}
	public static void setListCropVariety(Vector<DropDownDual> listCropVariety) {
		GeneralQuality.listCropVariety = listCropVariety;
	}
	public static void setListCustomerCode(Vector<DropDownSingle> listCustomerCode) {
		GeneralQuality.listCustomerCode = listCustomerCode;
	}
}