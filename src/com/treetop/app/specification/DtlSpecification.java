/*
 * Created on May 6, 2008
 * 
 */

package com.treetop.app.specification;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanSpecification;
import com.treetop.viewbeans.BaseViewBeanR1;
import com.treetop.utilities.html.*;

/**
 * @author twalto
 * 
 * 
 */
public class DtlSpecification extends BaseViewBeanR1 {
	
	// Standard Fields - to be in Every View Bean
	public String requestType   = "";
	public String displayMessage = "";

	public String itemNumber = "";
	public String revisionDate = "";
	
	// Are you personally allowed to UPDATE the Specifications?
	public String allowUpdate = "";
	
	public BeanSpecification beanSpec = new BeanSpecification();
	
	public Vector listReport = null;

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
	 * @return Returns the listReport.
	 */
	public Vector getListReport() {
		return listReport;
	}
	/**
	 * @param listReport The listReport to set.
	 */
	public void setListReport(Vector listReport) {
		this.listReport = listReport;
	}
	/**
	 * @return Returns the requestType.
	 */
	public String getRequestType() {
		return requestType;
	}
	/**
	 * @param requestType The requestType to set.
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	/**
	 * @return Returns the allowUpdate.
	 */
	public String getAllowUpdate() {
		return allowUpdate;
	}
	/**
	 * @param allowUpdate The allowUpdate to set.
	 */
	public void setAllowUpdate(String allowUpdate) {
		this.allowUpdate = allowUpdate;
	}
	/**
	* Determine Security for CPG Specifications
	*    Send in:
	* 		request
	* 		response
	*    Return:
	*       Set the information in allowUpdate Field 
	*
	* Creation date: (10/22/2008 -- TWalton)
	*/
	public void determineSecurity(HttpServletRequest request,
								  HttpServletResponse response) {
		try
		{
			 try
			 {
			    String[] rolesR = SessionVariables.getSessionttiUserRoles(request, response);
			    for (int xr = 0; xr < rolesR.length; xr++)
			    {
			       if (rolesR[xr].equals("8"))
			          allowUpdate = "Y";
			    }
			 }
			 catch(Exception e)
			 {}
			 if (!allowUpdate.equals("Y"))
			 {
				   try
				   {
				     String[] groupsR = SessionVariables.getSessionttiUserGroups(request, response);
				     for (int xr = 0; xr < groupsR.length; xr++)
				     {
				           if (groupsR[xr].equals("110"))
				              allowUpdate = "Y";
				     }
				   }
				   catch(Exception e)
				   {}
			 }  
		}
		catch(Exception e)
		{
			System.out.println("Exception Occurred in InqSpecification.determineSecurity(request, response)" + e);
		}
	    return;
	}
	/**
	*  This method should be in EVERY Inquiry View Bean
	*   Will create the vectors and generate the code for
	*    MORE Button.
	* @return
	*/
	public static String buildMoreButton(String requestType,
										 String item,
										 String revisionDate) {
		
		// BUILD Edit/More Button Section(Column)  
		String[] urlLinks = new String[6];
		String[] urlNames = new String[6];
		String[] newPage = new String[6];
		for (int z = 0; z < 6; z++) {
			urlLinks[z] = "";
			urlNames[z] = "";
			newPage[z] = "";
		}
		if (requestType.equals("listCPGSpec")) {
			urlLinks[0] = "/web/CtlGTIN?requestType=dtlCPGSpec&itemNumber="+ item.trim() + "&revisionDate=" + revisionDate;
				urlNames[0] = "See Detailed Spec";
				newPage[0] = "Y";
			}
//			if (requestType.equals("list") ||
//			    requestType.equals("detail")) 
//			{
//				urlLinks[1] =
//					"/web/CtlGTIN?requestType=update"
//						+ "&gtinNumber="
//						+ gtin.trim();
//				urlNames[1] = "Update " + gtin.trim();
//				newPage[1] = "Y";
//			}
//			if (requestType.equals("list") ||
//				requestType.equals("detail")) 
//			{
//				urlLinks[2] = "/web/CtlGTIN?requestType=add";
//				urlNames[2] = "Add a New GTIN";
//				newPage[2] = "Y";
//			}
//			if (requestType.equals("list") ||
//				requestType.equals("detail")) 
//			{
//	
//				urlLinks[3] =
//					"/web/CtlGTIN?requestType=updateTies"
//						+ "&parentGTIN="
//						+ gtin.trim();
//				urlNames[3] = "Maintain Children of " + gtin.trim();
//				newPage[3] = "Y";
//			}
	//		if (requestType.equals("list") ||
	//			requestType.equals("detail")) 
	//		{
	
	//			urlLinks[4] =
	//				"/web/CtlGTIN?requestType=copy" + "&gtinNumber=" + gtin.trim();
	//			urlNames[4] = "Copy " + gtin.trim() + " & Create New";
	//			newPage[4] = "Y";
	//		}
	//		if (requestType.equals("list")) 
	//		{
	//
	//			urlLinks[5] =
	//				"JavaScript:deleteTrans('/web/CtlGTIN?requestType=delete"
	//					+ "&gtinNumber="
	//					+ gtin.trim()
	//					+ resend
	//					+ "')";
	//			urlNames[5] = "Delete " + gtin.trim();
	//			newPage[5] = "N";
	
	//		}
	
			//		<a href="/web/servlet/com.treetop.servlets.CtlUCCNet?requestType=bindResource&globalTradeItemNumber=<%= thisrow.getGlobalTradeItemNumber().trim() %>&<%= parmResend %>">Add Resource to GTIN</a>
			//		 <br/>
			//		<a href="/web/servlet/com.treetop.servlets.CtlUCCNet?requestType=removeResource&globalTradeItemNumber=<%= thisrow.getGlobalTradeItemNumber().trim() %>&resource=<%= theresource.trim() %>&<%= parmResend %>">Remove Resource from GTIN</a>		
	
			return HTMLHelpers.buttonMore(urlLinks, urlNames, newPage);
		}
	/**
	 * @return Returns the beanSpec.
	 */
	public BeanSpecification getBeanSpec() {
		return beanSpec;
	}
	/**
	 * @param beanSpec The beanSpec to set.
	 */
	public void setBeanSpec(BeanSpecification beanSpec) {
		this.beanSpec = beanSpec;
	}
	/**
	 * @return Returns the itemNumber.
	 */
	public String getItemNumber() {
		return itemNumber;
	}
	/**
	 * @param itemNumber The itemNumber to set.
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	/**
	 * @return Returns the revisionDate.
	 */
	public String getRevisionDate() {
		return revisionDate;
	}
	/**
	 * @param revisionDate The revisionDate to set.
	 */
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	/**
	*  Take in Information and then Calculate the Cubic Feet for the values
	* @return
	*/
	public static String calculateCaseCube(String length,
										   String width,
										   String height) {

		String returnValue = "0.000";
		try
		{
	    // Cubic Feet
	    //*********
			BigDecimal twelve      = new BigDecimal("12");
			BigDecimal lengthFeet  = ((new BigDecimal(length)).setScale(6)).divide(twelve,1);
			BigDecimal widthFeet   = ((new BigDecimal(width)).setScale(6)).divide(twelve,1);
			BigDecimal heightFeet  = ((new BigDecimal(height)).setScale(6)).divide(twelve,1);

	        BigDecimal cubicFeet = lengthFeet.multiply(widthFeet);
	         cubicFeet = cubicFeet.multiply(heightFeet);
	         cubicFeet = cubicFeet.setScale(3,0);
	        returnValue = cubicFeet.toString();
		}
		catch(Exception e)
		{}
		return returnValue;
	}
	/**
	*  Take in Information and then Calculate the Number of Layers on Each Pallet
	* @return
	*/
	public static String calculateLayersPerPallet(String casesPerPallet,
												   	  String casesPerLayer) {
				
		 String returnValue = "0";
		 try
		 {
		 //*********
		 // Layers Per Pallet
		 //*********
		 	returnValue = HTMLHelpersMasking.maskNumber((new BigDecimal(casesPerPallet).setScale(3)).divide(((new BigDecimal(casesPerLayer)).setScale(3)), 2).toString(), 0);
		 }
		 catch(Exception e)
		 {}
		 return returnValue;
	}
	/**
	*  Take in Information and then Calculate the Pallet Height
	*     Assumption that the pallet itself is 5 inches
	* @return
	*/
	public static String calculatePalletHeight(String layers,
											   String height) {
		String returnValue = "0";
		try
		{
//			*********
         // Pallet Height
         //*********
			BigDecimal heightInches = (new BigDecimal(height)).setScale(6);
			BigDecimal palletHeight = ((new BigDecimal(layers)).setScale(3)).multiply(heightInches);
	        palletHeight = (palletHeight.add(new BigDecimal("5.75"))).setScale(3, 0);
	        returnValue = HTMLHelpersMasking.maskNumber(palletHeight.toString(), 3);
	        
//	        palletHeight = layerPallet.multiply(heightInches);
//	         palletHeight = palletHeight.add(five);
//	         palletHeight = palletHeight.setScale(3,0);  
	        
		}
		catch(Exception e)
		{}
		return returnValue;
	}
}
