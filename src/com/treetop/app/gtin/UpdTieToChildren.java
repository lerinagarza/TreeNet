/*
 * Created on Jul 26, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.treetop.app.gtin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import com.treetop.CheckDate;
import com.treetop.SystemDate;
import com.treetop.businessobjects.*;
import com.treetop.businessobjectapplications.*;
import com.treetop.viewbeans.BaseViewBean;
import com.treetop.services.ServiceGTIN;
import com.treetop.utilities.html.HTMLHelpers;
import com.treetop.utilities.html.HTMLHelpersInput;
import com.treetop.utilities.html.HTMLHelpersMasking;

/**
 * @author twalto
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UpdTieToChildren extends BaseViewBean {
	/**
	 *  Create the More button to be used with the UpdTieToChildren Page
	 * @return
	 *   Created 10/6/05 TWalton
	 */
	public static String buildMoreButton(
		String requestType,
		String gtin,
		String resend) {
			String[] urlLinks = new String[4];
			String[] urlNames = new String[4];
			String[] newPage = new String[4];
			for (int z = 0; z < 4; z++) {
				urlLinks[z] = "";
				urlNames[z] = "";
				newPage[z] = "";
			}
			
				urlLinks[0] =
					"/web/CtlGTIN?requestType=detail"
						+ "&gtinNumber="
						+ gtin.trim();
				urlNames[0] = "Details of " + gtin.trim();
				newPage[0] = "Y";
				
					urlLinks[1] =
						"/web/CtlGTIN?requestType=update"
							+ "&gtinNumber="
							+ gtin.trim();
					urlNames[1] = "Update " + gtin.trim();
					newPage[1] = "Y";
				urlLinks[2] =
					"/web/CtlGTIN?requestType=updateTies"
						+ "&parentGTIN="
						+ gtin.trim();
				urlNames[2] = "Maintain Children of " + gtin.trim();
				newPage[2] = "Y";
	
		return HTMLHelpers.buttonMore(urlLinks, urlNames, newPage);
	}
	
	/* Use this method to take information from
	 *   The Return Bean / the Business Bean and
	 *   Put it into the correct fields.
	 *  Created: 10/5/05 TWalton
	 */
	public void buildUpdViewBeanFromBusinessBean(BeanGTIN inBean) {

		parentInfo = inBean.getGtin();
		parentGTIN = parentInfo.getGtinNumber();
		for (int x = 0; x < inBean.getChildren().size(); x++) {
			GTINChild thisChild = (GTINChild) inBean.getChildren().elementAt(x);
			if (thisChild.getGtinNumber() != null) {
				if (x == 0) {
					child1Info = thisChild;
					childGTIN1 = child1Info.getGtinNumber();
					if (childGTIN1.length() == 14) {
						childGTIN1A = childGTIN1.substring(0, 2);
						childGTIN1B = childGTIN1.substring(2, 8);
						childGTIN1C = childGTIN1.substring(8, 13);
						childGTIN1D = childGTIN1.substring(13, 14);
					}
					childGTIN1Sequence    = child1Info.getChildSeq();
					childGTIN1QtyInParent = HTMLHelpersMasking.maskNumber(child1Info.getChildQty(), 0);
				}
				if (x == 1) {
					child2Info = thisChild;
					childGTIN2 = child2Info.getGtinNumber();
					if (childGTIN2.length() == 14) {
						childGTIN2A = childGTIN2.substring(0, 2);
						childGTIN2B = childGTIN2.substring(2, 8);
						childGTIN2C = childGTIN2.substring(8, 13);
						childGTIN2D = childGTIN2.substring(13, 14);
					}
					childGTIN2Sequence    = child2Info.getChildSeq();
					childGTIN2QtyInParent = HTMLHelpersMasking.maskNumber(child2Info.getChildQty(), 0);
				}
				if (x == 2) {
					child3Info = thisChild;
					childGTIN3 = child3Info.getGtinNumber();
					if (childGTIN3.length() == 14) {
						childGTIN3A = childGTIN3.substring(0, 2);
						childGTIN3B = childGTIN3.substring(2, 8);
						childGTIN3C = childGTIN3.substring(8, 13);
						childGTIN3D = childGTIN3.substring(13, 14);
					}
					childGTIN3Sequence    = child3Info.getChildSeq();
					childGTIN3QtyInParent = HTMLHelpersMasking.maskNumber(child3Info.getChildQty(), 0);
					
				}
				if (x == 3) {
					child4Info = thisChild;
					childGTIN4 = child4Info.getGtinNumber();
					if (childGTIN4.length() == 14) {
						childGTIN4A = childGTIN4.substring(0, 2);
						childGTIN4B = childGTIN4.substring(2, 8);
						childGTIN4C = childGTIN4.substring(8, 13);
						childGTIN4D = childGTIN4.substring(13, 14);
					}
					childGTIN4Sequence    = child4Info.getChildSeq();
					childGTIN4QtyInParent = HTMLHelpersMasking.maskNumber(child4Info.getChildQty(), 0);
					
				}
				if (x == 4) {
					child5Info = thisChild;
					childGTIN5 = child5Info.getGtinNumber();
					if (childGTIN5.length() == 14) {
						childGTIN5A = childGTIN5.substring(0, 2);
						childGTIN5B = childGTIN5.substring(2, 8);
						childGTIN5C = childGTIN5.substring(8, 13);
						childGTIN5D = childGTIN5.substring(13, 14);
					}
					childGTIN5Sequence    = child5Info.getChildSeq();
					childGTIN5QtyInParent = HTMLHelpersMasking.maskNumber(child5Info.getChildQty(), 0);
				}
				if (x == 5) {
					child6Info = thisChild;
					childGTIN6 = child6Info.getGtinNumber();
					if (childGTIN6.length() == 14) {
						childGTIN6A = childGTIN6.substring(0, 2);
						childGTIN6B = childGTIN6.substring(2, 8);
						childGTIN6C = childGTIN6.substring(8, 13);
						childGTIN6D = childGTIN6.substring(13, 14);
					}
					childGTIN6Sequence    = child6Info.getChildSeq();
					childGTIN6QtyInParent = HTMLHelpersMasking.maskNumber(child6Info.getChildQty(), 0);
				}
				if (x == 6) {
					child7Info = thisChild;
					childGTIN7 = child7Info.getGtinNumber();
					if (childGTIN7.length() == 14) {
						childGTIN7A = childGTIN7.substring(0, 2);
						childGTIN7B = childGTIN7.substring(2, 8);
						childGTIN7C = childGTIN7.substring(8, 13);
						childGTIN7D = childGTIN7.substring(13, 14);
					}
					childGTIN7Sequence    = child7Info.getChildSeq();
					childGTIN7QtyInParent = HTMLHelpersMasking.maskNumber(child7Info.getChildQty(), 0);
				}
			}
		}

		return;
	}

	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	public List errors = null;
	public String saveButton = null;
	// Fields from the JSP to Request Information
	//  Main JSP
	public GTIN parentInfo = null; // For JSP Display
	public String parentGTIN = "";
	public String childGTIN = ""; // for use when Adding
	public String childSequence = "0"; // for use when Adding
	public String childQuantity = "0"; // for use when Adding
	public String userProfile = "";

	public GTINChild child1Info = null; // For JSP Display
	public String childGTIN1Sequence = "0";
	public String childGTIN1QtyInParent = "0";
	public String childGTIN1 = "";
	public String childGTIN1A = ""; // 2 Long-JSP Use Only Not Stored in a File
	public String childGTIN1B = ""; // 6 Long-JSP Use Only Not Stored in a File
	public String childGTIN1C = "";
	// 5 Long-JSP Use Only Not Stored in the File
	public String childGTIN1D = "";
	// 1 Long-JSP Use Only Not Stored in the File

	public GTINChild child2Info = null; // For JSP Display
	public String childGTIN2Sequence = "0";
	public String childGTIN2QtyInParent = "0";
	public String childGTIN2 = "";
	public String childGTIN2A = ""; // 2 Long-JSP Use Only Not Stored in a File
	public String childGTIN2B = ""; // 6 Long-JSP Use Only Not Stored in a File
	public String childGTIN2C = "";
	// 5 Long-JSP Use Only Not Stored in the File
	public String childGTIN2D = "";
	// 1 Long-JSP Use Only Not Stored in the File

	public GTINChild child3Info = null; // For JSP Display
	public String childGTIN3Sequence = "0";
	public String childGTIN3QtyInParent = "0";
	public String childGTIN3 = "";
	public String childGTIN3A = ""; // 2 Long-JSP Use Only Not Stored in a File
	public String childGTIN3B = ""; // 6 Long-JSP Use Only Not Stored in a File
	public String childGTIN3C = "";
	// 5 Long-JSP Use Only Not Stored in the File
	public String childGTIN3D = "";
	// 1 Long-JSP Use Only Not Stored in the File

	public GTINChild child4Info = null; // For JSP Display
	public String childGTIN4Sequence = "0";
	public String childGTIN4QtyInParent = "0";
	public String childGTIN4 = "";
	public String childGTIN4A = ""; // 2 Long-JSP Use Only Not Stored in a File
	public String childGTIN4B = ""; // 6 Long-JSP Use Only Not Stored in a File
	public String childGTIN4C = "";
	// 5 Long-JSP Use Only Not Stored in the File
	public String childGTIN4D = "";
	// 1 Long-JSP Use Only Not Stored in the File

	public GTINChild child5Info = null; // For JSP Display
	public String childGTIN5Sequence = "0";
	public String childGTIN5QtyInParent = "0";
	public String childGTIN5 = "";
	public String childGTIN5A = ""; // 2 Long-JSP Use Only Not Stored in a File
	public String childGTIN5B = ""; // 6 Long-JSP Use Only Not Stored in a File
	public String childGTIN5C = "";
	// 5 Long-JSP Use Only Not Stored in the File
	public String childGTIN5D = "";
	// 1 Long-JSP Use Only Not Stored in the File

	public GTINChild child6Info = null; // For JSP Display
	public String childGTIN6Sequence = "0";
	public String childGTIN6QtyInParent = "0";
	public String childGTIN6 = "";
	public String childGTIN6A = ""; // 2 Long-JSP Use Only Not Stored in a File
	public String childGTIN6B = ""; // 6 Long-JSP Use Only Not Stored in a File
	public String childGTIN6C = "";
	// 5 Long-JSP Use Only Not Stored in the File
	public String childGTIN6D = "";
	// 1 Long-JSP Use Only Not Stored in the File

	public GTINChild child7Info = null; // For JSP Display
	public String childGTIN7Sequence = "0";
	public String childGTIN7QtyInParent = "0";
	public String childGTIN7 = "";
	public String childGTIN7A = ""; // 2 Long-JSP Use Only Not Stored in a File
	public String childGTIN7B = ""; // 6 Long-JSP Use Only Not Stored in a File
	public String childGTIN7C = "";
	// 5 Long-JSP Use Only Not Stored in the File
	public String childGTIN7D = "";
	// 1 Long-JSP Use Only Not Stored in the File

	public String lastChangeDate = "";
	public String lastUpdateDate = "";
	public String lastUpdateTime = "";
	public String lastUpdateUser = "";
	public String lastUpdateWorkstation = "";

	/* Use this method to take information from
	 *   All four sections and combine it into ONE section
	 *  Created: 10/5/05 TWalton
	 */
	public void buildGTINFromSeperated() {
		StringBuffer combinedGTIN = new StringBuffer();
		combinedGTIN.append(childGTIN1A);
		combinedGTIN.append(childGTIN1B);
		combinedGTIN.append(childGTIN1C);
		combinedGTIN.append(childGTIN1D);
		childGTIN1 = combinedGTIN.toString();

		combinedGTIN = new StringBuffer();
		combinedGTIN.append(childGTIN2A);
		combinedGTIN.append(childGTIN2B);
		combinedGTIN.append(childGTIN2C);
		combinedGTIN.append(childGTIN2D);
		childGTIN2 = combinedGTIN.toString();

		combinedGTIN = new StringBuffer();
		combinedGTIN.append(childGTIN3A);
		combinedGTIN.append(childGTIN3B);
		combinedGTIN.append(childGTIN3C);
		combinedGTIN.append(childGTIN3D);
		childGTIN3 = combinedGTIN.toString();

		combinedGTIN = new StringBuffer();
		combinedGTIN.append(childGTIN4A);
		combinedGTIN.append(childGTIN4B);
		combinedGTIN.append(childGTIN4C);
		combinedGTIN.append(childGTIN4D);
		childGTIN4 = combinedGTIN.toString();

		combinedGTIN = new StringBuffer();
		combinedGTIN.append(childGTIN5A);
		combinedGTIN.append(childGTIN5B);
		combinedGTIN.append(childGTIN5C);
		combinedGTIN.append(childGTIN5D);
		childGTIN5 = combinedGTIN.toString();

		combinedGTIN = new StringBuffer();
		combinedGTIN.append(childGTIN6A);
		combinedGTIN.append(childGTIN6B);
		combinedGTIN.append(childGTIN6C);
		combinedGTIN.append(childGTIN6D);
		childGTIN6 = combinedGTIN.toString();

		combinedGTIN = new StringBuffer();
		combinedGTIN.append(childGTIN7A);
		combinedGTIN.append(childGTIN7B);
		combinedGTIN.append(childGTIN7C);
		combinedGTIN.append(childGTIN7D);
		childGTIN7 = combinedGTIN.toString();

		return;
	}
	/* EVERY Field Must be tested to verify that it is a number.
	 * 
	 * (non-Javadoc)
	 * @see com.treetop.viewbeans.BaseViewBean#validate()
	 * Created 10/6/05 TWalton
	 */
	public List validate() {
		Vector returnList = new Vector();
		
		// ROW 1
		String test1 = validateInteger(childGTIN1Sequence);
		if (!test1.trim().equals(""))
		  returnList.addElement(test1);
        test1 = validateInteger(childGTIN1Sequence);
		if (!test1.trim().equals(""))
		  returnList.addElement(test1);
		test1 = validateInteger(childGTIN1A);
		if (!test1.trim().equals(""))
		  returnList.addElement(test1);
		test1 = validateInteger(childGTIN1B);
		if (!test1.trim().equals(""))
		  returnList.addElement(test1);
		test1 = validateInteger(childGTIN1C);
		if (!test1.trim().equals(""))
		  returnList.addElement(test1);
		String childGTIN1 = childGTIN1A + childGTIN1B + childGTIN1C;
		try
		{
			if (childGTIN1.length() == 13)
			{
			  childGTIN1 = ServiceGTIN.checkDigit14(childGTIN1);
			  childGTIN1D = childGTIN1.substring(13, 14);
			}
			if (childGTIN1.length() == 0)
			  childGTIN1D = "";
		}
		catch(Exception e)
		{
		}		  
		test1 = validateInteger(childGTIN1D);
		if (!test1.trim().equals(""))
		  returnList.addElement(test1);
		test1 = validateInteger(childGTIN1QtyInParent);
		if (!test1.trim().equals(""))
		  returnList.addElement(test1);
		else
		{	
			if (childGTIN1.length() == 14)
			{ // Test to see if this is already a GTIN 
				// IN the GTIN File, IF NOT, add it.
				String returnValue = UpdGTIN.testAndAddGTIN(childGTIN1);
			}
		}	
		// ROW 2
		String test2 = validateInteger(childGTIN2Sequence);
		if (!test2.trim().equals(""))
		  returnList.addElement(test2);
		test2 = validateInteger(childGTIN2Sequence);
		if (!test2.trim().equals(""))
		  returnList.addElement(test2);
		test2 = validateInteger(childGTIN2A);
		if (!test2.trim().equals(""))
		  returnList.addElement(test2);
		test2 = validateInteger(childGTIN2B);
		if (!test2.trim().equals(""))
		  returnList.addElement(test2);
		test2 = validateInteger(childGTIN2C);
		if (!test2.trim().equals(""))
		  returnList.addElement(test2);
		String childGTIN2 = childGTIN2A + childGTIN2B + childGTIN2C;
		try
		{
			if (childGTIN2.length() == 13)
			{
			  childGTIN2 = ServiceGTIN.checkDigit14(childGTIN2);
			  childGTIN2D = childGTIN2.substring(13, 14);
			}
			if (childGTIN2.length() == 0)
			  childGTIN2D = "";
		}
		catch(Exception e)
		{
		}		  		  
		test2 = validateInteger(childGTIN2D);
		if (!test2.trim().equals(""))
		  returnList.addElement(test2);
		test2 = validateInteger(childGTIN2QtyInParent);
		if (!test2.trim().equals(""))
		  returnList.addElement(test2);		
		else
		{	
			if (childGTIN2.length() == 14)
			{ // Test to see if this is already a GTIN 
				// IN the GTIN File, IF NOT, add it.
				String returnValue = UpdGTIN.testAndAddGTIN(childGTIN2);
			}
		}	
		// ROW 3
		String test3 = validateInteger(childGTIN3Sequence);
		if (!test3.trim().equals(""))
		  returnList.addElement(test3);
		test3 = validateInteger(childGTIN3Sequence);
		if (!test3.trim().equals(""))
		  returnList.addElement(test3);
		test3 = validateInteger(childGTIN3A);
		if (!test3.trim().equals(""))
		  returnList.addElement(test3);
		test3 = validateInteger(childGTIN3B);
		if (!test3.trim().equals(""))
		  returnList.addElement(test3);
		test3 = validateInteger(childGTIN3C);
		if (!test3.trim().equals(""))
		  returnList.addElement(test3);
		String childGTIN3 = childGTIN3A + childGTIN3B + childGTIN3C;
		try
		{
			if (childGTIN3.length() == 13)
			{
			  childGTIN3 = ServiceGTIN.checkDigit14(childGTIN3);
			  childGTIN3D = childGTIN3.substring(13, 14);
			}
			if (childGTIN3.length() == 0)
			  childGTIN3D = "";
		}
		catch(Exception e)
		{
		}		  		  
		test3 = validateInteger(childGTIN3D);
		if (!test3.trim().equals(""))
		  returnList.addElement(test3);
		test3 = validateInteger(childGTIN3QtyInParent);
		if (!test3.trim().equals(""))
		  returnList.addElement(test3);
		else
		{	
			if (childGTIN3.length() == 14)
			{ // Test to see if this is already a GTIN 
				// IN the GTIN File, IF NOT, add it.
				String returnValue = UpdGTIN.testAndAddGTIN(childGTIN3);
			}
		}			
		// ROW 4
		String test4 = validateInteger(childGTIN4Sequence);
		if (!test4.trim().equals(""))
		  returnList.addElement(test4);
		test4 = validateInteger(childGTIN4Sequence);
		if (!test4.trim().equals(""))
		  returnList.addElement(test4);
		test4 = validateInteger(childGTIN4A);
		if (!test4.trim().equals(""))
		  returnList.addElement(test4);
		test4 = validateInteger(childGTIN4B);
		if (!test4.trim().equals(""))
		  returnList.addElement(test4);
		test4 = validateInteger(childGTIN4C);
		if (!test4.trim().equals(""))
		  returnList.addElement(test4);
		String childGTIN4 = childGTIN4A + childGTIN4B + childGTIN4C;
		try
		{
			if (childGTIN4.length() == 13)
			{
			  childGTIN4 = ServiceGTIN.checkDigit14(childGTIN4);
			  childGTIN4D = childGTIN4.substring(13, 14);
			}
			if (childGTIN4.length() == 0)
			  childGTIN4D = "";
		}
		catch(Exception e)
		{
		}		  		  
		test4 = validateInteger(childGTIN4D);
		if (!test4.trim().equals(""))
		  returnList.addElement(test4);
		test4 = validateInteger(childGTIN4QtyInParent);
		if (!test4.trim().equals(""))
		  returnList.addElement(test4);		
		else
		{	
			if (childGTIN4.length() == 14)
			{ // Test to see if this is already a GTIN 
				// IN the GTIN File, IF NOT, add it.
				String returnValue = UpdGTIN.testAndAddGTIN(childGTIN4);
			}
		}			
		// ROW 5
		String test5 = validateInteger(childGTIN5Sequence);
		if (!test5.trim().equals(""))
		  returnList.addElement(test5);
		test5 = validateInteger(childGTIN5Sequence);
		if (!test5.trim().equals(""))
		  returnList.addElement(test5);
		test5 = validateInteger(childGTIN5A);
		if (!test5.trim().equals(""))
		  returnList.addElement(test5);
		test5 = validateInteger(childGTIN5B);
		if (!test5.trim().equals(""))
		  returnList.addElement(test5);
		test5 = validateInteger(childGTIN5C);
		if (!test5.trim().equals(""))
		  returnList.addElement(test5);
		String childGTIN5 = childGTIN5A + childGTIN5B + childGTIN5C;
		try
		{
			if (childGTIN5.length() == 13)
			{
			  childGTIN5 = ServiceGTIN.checkDigit14(childGTIN5);
			  childGTIN5D = childGTIN5.substring(13, 14);
			}
			if (childGTIN5.length() == 0)
			  childGTIN5D = "";
		}
		catch(Exception e)
		{
		}		  		  
		test5 = validateInteger(childGTIN5D);
		if (!test5.trim().equals(""))
		  returnList.addElement(test5);
		test5 = validateInteger(childGTIN5QtyInParent);
		if (!test5.trim().equals(""))
		  returnList.addElement(test5);
		else
		{	
			if (childGTIN5.length() == 14)
			{ // Test to see if this is already a GTIN 
				// IN the GTIN File, IF NOT, add it.
				String returnValue = UpdGTIN.testAndAddGTIN(childGTIN5);
			}
		}			
		// ROW 6
		String test6 = validateInteger(childGTIN6Sequence);
		if (!test6.trim().equals(""))
		  returnList.addElement(test6);
		test6 = validateInteger(childGTIN6Sequence);
		if (!test6.trim().equals(""))
		  returnList.addElement(test6);
		test6 = validateInteger(childGTIN6A);
		if (!test6.trim().equals(""))
		  returnList.addElement(test6);
		test6 = validateInteger(childGTIN6B);
		if (!test6.trim().equals(""))
		  returnList.addElement(test6);
		test6 = validateInteger(childGTIN6C);
		if (!test6.trim().equals(""))
		  returnList.addElement(test6);
		String childGTIN6 = childGTIN6A + childGTIN6B + childGTIN6C;
		try
		{
			if (childGTIN6.length() == 13)
			{
			  childGTIN6 = ServiceGTIN.checkDigit14(childGTIN6);
			  childGTIN6D = childGTIN6.substring(13, 14);
			}
			if (childGTIN6.length() == 0)
			  childGTIN6D = "";
		}
		catch(Exception e)
		{
		}		  		  
		test6 = validateInteger(childGTIN6D);
		if (!test6.trim().equals(""))
		  returnList.addElement(test6);
		test6 = validateInteger(childGTIN6QtyInParent);
		if (!test6.trim().equals(""))
		  returnList.addElement(test6);		
		else
		{	
			if (childGTIN6.length() == 14)
			{ // Test to see if this is already a GTIN 
				// IN the GTIN File, IF NOT, add it.
				String returnValue = UpdGTIN.testAndAddGTIN(childGTIN6);
			}
		}	
		// ROW 7
		String test7 = validateInteger(childGTIN7Sequence);
		if (!test7.trim().equals(""))
		  returnList.addElement(test7);
		test7 = validateInteger(childGTIN7Sequence);
		if (!test7.trim().equals(""))
		  returnList.addElement(test7);
		test7 = validateInteger(childGTIN7A);
		if (!test7.trim().equals(""))
		  returnList.addElement(test7);
		test7 = validateInteger(childGTIN7B);
		if (!test7.trim().equals(""))
		  returnList.addElement(test7);
		test7 = validateInteger(childGTIN7C);
		if (!test7.trim().equals(""))
		  returnList.addElement(test7);
		String childGTIN7 = childGTIN7A + childGTIN7B + childGTIN7C;
		try
		{
			if (childGTIN7.length() == 13)
			{
			  childGTIN7 = ServiceGTIN.checkDigit14(childGTIN7);
			  childGTIN7D = childGTIN7.substring(13, 14);
			}
			if (childGTIN7.length() == 0)
			  childGTIN7D = "";
		}
		catch(Exception e)
		{
		}		  		  
		test7 = validateInteger(childGTIN7D);
		if (!test7.trim().equals(""))
		  returnList.addElement(test7);
		test7 = validateInteger(childGTIN7QtyInParent);
		if (!test7.trim().equals(""))
		  returnList.addElement(test7);
		else
		{	
			if (childGTIN7.length() == 14)
			{ // Test to see if this is already a GTIN 
				// IN the GTIN File, IF NOT, add it.
				String returnValue = UpdGTIN.testAndAddGTIN(childGTIN7);
			}
		}					

		return returnList;
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
	 * @return
	 */
	public List getErrors() {
		return errors;
	}

	/**
	 * @param list
	 */
	public void setErrors(List list) {
		errors = list;
	}

	/**
	 * @return
	 */
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @return
	 */
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @return
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @return
	 */
	public String getLastUpdateWorkstation() {
		return lastUpdateWorkstation;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateDate(String string) {
		lastUpdateDate = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateTime(String string) {
		lastUpdateTime = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateUser(String string) {
		lastUpdateUser = string;
	}

	/**
	 * @param string
	 */
	public void setLastUpdateWorkstation(String string) {
		lastUpdateWorkstation = string;
	}

	/**
	 * @return
	 */
	public String getLastChangeDate() {
		return lastChangeDate;
	}

	/**
	 * @param string
	 */
	public void setLastChangeDate(String string) {
		lastChangeDate = string;
	}

	/**
	 * @return
	 */
	public String getChildGTIN1() {
		return childGTIN1;
	}

	/**
	 * @return
	 */
	public String getChildGTIN1A() {
		return childGTIN1A;
	}

	/**
	 * @return
	 */
	public String getChildGTIN1B() {
		return childGTIN1B;
	}

	/**
	 * @return
	 */
	public String getChildGTIN1C() {
		return childGTIN1C;
	}

	/**
	 * @return
	 */
	public String getChildGTIN1D() {
		return childGTIN1D;
	}

	/**
	 * @return
	 */
	public String getChildGTIN1QtyInParent() {
		return childGTIN1QtyInParent;
	}

	/**
	 * @return
	 */
	public String getChildGTIN1Sequence() {
		return childGTIN1Sequence;
	}

	/**
	 * @return
	 */
	public String getChildGTIN2() {
		return childGTIN2;
	}

	/**
	 * @return
	 */
	public String getChildGTIN2A() {
		return childGTIN2A;
	}

	/**
	 * @return
	 */
	public String getChildGTIN2B() {
		return childGTIN2B;
	}

	/**
	 * @return
	 */
	public String getChildGTIN2C() {
		return childGTIN2C;
	}

	/**
	 * @return
	 */
	public String getChildGTIN2D() {
		return childGTIN2D;
	}

	/**
	 * @return
	 */
	public String getChildGTIN2QtyInParent() {
		return childGTIN2QtyInParent;
	}

	/**
	 * @return
	 */
	public String getChildGTIN2Sequence() {
		return childGTIN2Sequence;
	}

	/**
	 * @return
	 */
	public String getChildGTIN3() {
		return childGTIN3;
	}

	/**
	 * @return
	 */
	public String getChildGTIN3A() {
		return childGTIN3A;
	}

	/**
	 * @return
	 */
	public String getChildGTIN3B() {
		return childGTIN3B;
	}

	/**
	 * @return
	 */
	public String getChildGTIN3C() {
		return childGTIN3C;
	}

	/**
	 * @return
	 */
	public String getChildGTIN3D() {
		return childGTIN3D;
	}

	/**
	 * @return
	 */
	public String getChildGTIN3QtyInParent() {
		return childGTIN3QtyInParent;
	}

	/**
	 * @return
	 */
	public String getChildGTIN3Sequence() {
		return childGTIN3Sequence;
	}

	/**
	 * @return
	 */
	public String getChildGTIN4() {
		return childGTIN4;
	}

	/**
	 * @return
	 */
	public String getChildGTIN4A() {
		return childGTIN4A;
	}

	/**
	 * @return
	 */
	public String getChildGTIN4B() {
		return childGTIN4B;
	}

	/**
	 * @return
	 */
	public String getChildGTIN4C() {
		return childGTIN4C;
	}

	/**
	 * @return
	 */
	public String getChildGTIN4D() {
		return childGTIN4D;
	}

	/**
	 * @return
	 */
	public String getChildGTIN4QtyInParent() {
		return childGTIN4QtyInParent;
	}

	/**
	 * @return
	 */
	public String getChildGTIN4Sequence() {
		return childGTIN4Sequence;
	}

	/**
	 * @return
	 */
	public String getChildGTIN5() {
		return childGTIN5;
	}

	/**
	 * @return
	 */
	public String getChildGTIN5A() {
		return childGTIN5A;
	}

	/**
	 * @return
	 */
	public String getChildGTIN5B() {
		return childGTIN5B;
	}

	/**
	 * @return
	 */
	public String getChildGTIN5C() {
		return childGTIN5C;
	}

	/**
	 * @return
	 */
	public String getChildGTIN5D() {
		return childGTIN5D;
	}

	/**
	 * @return
	 */
	public String getChildGTIN5QtyInParent() {
		return childGTIN5QtyInParent;
	}

	/**
	 * @return
	 */
	public String getChildGTIN5Sequence() {
		return childGTIN5Sequence;
	}

	/**
	 * @return
	 */
	public String getChildGTIN6() {
		return childGTIN6;
	}

	/**
	 * @return
	 */
	public String getChildGTIN6A() {
		return childGTIN6A;
	}

	/**
	 * @return
	 */
	public String getChildGTIN6B() {
		return childGTIN6B;
	}

	/**
	 * @return
	 */
	public String getChildGTIN6C() {
		return childGTIN6C;
	}

	/**
	 * @return
	 */
	public String getChildGTIN6D() {
		return childGTIN6D;
	}

	/**
	 * @return
	 */
	public String getChildGTIN6QtyInParent() {
		return childGTIN6QtyInParent;
	}

	/**
	 * @return
	 */
	public String getChildGTIN6Sequence() {
		return childGTIN6Sequence;
	}

	/**
	 * @return
	 */
	public String getChildGTIN7() {
		return childGTIN7;
	}

	/**
	 * @return
	 */
	public String getChildGTIN7A() {
		return childGTIN7A;
	}

	/**
	 * @return
	 */
	public String getChildGTIN7B() {
		return childGTIN7B;
	}

	/**
	 * @return
	 */
	public String getChildGTIN7C() {
		return childGTIN7C;
	}

	/**
	 * @return
	 */
	public String getChildGTIN7D() {
		return childGTIN7D;
	}

	/**
	 * @return
	 */
	public String getChildGTIN7QtyInParent() {
		return childGTIN7QtyInParent;
	}

	/**
	 * @return
	 */
	public String getChildGTIN7Sequence() {
		return childGTIN7Sequence;
	}

	/**
	 * @return
	 */
	public String getParentGTIN() {
		return parentGTIN;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN1(String string) {
		childGTIN1 = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN1A(String string) {
		childGTIN1A = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN1B(String string) {
		childGTIN1B = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN1C(String string) {
		childGTIN1C = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN1D(String string) {
		childGTIN1D = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN1QtyInParent(String string) {
		childGTIN1QtyInParent = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN1Sequence(String string) {
		childGTIN1Sequence = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN2(String string) {
		childGTIN2 = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN2A(String string) {
		childGTIN2A = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN2B(String string) {
		childGTIN2B = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN2C(String string) {
		childGTIN2C = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN2D(String string) {
		childGTIN2D = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN2QtyInParent(String string) {
		childGTIN2QtyInParent = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN2Sequence(String string) {
		childGTIN2Sequence = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN3(String string) {
		childGTIN3 = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN3A(String string) {
		childGTIN3A = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN3B(String string) {
		childGTIN3B = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN3C(String string) {
		childGTIN3C = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN3D(String string) {
		childGTIN3D = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN3QtyInParent(String string) {
		childGTIN3QtyInParent = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN3Sequence(String string) {
		childGTIN3Sequence = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN4(String string) {
		childGTIN4 = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN4A(String string) {
		childGTIN4A = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN4B(String string) {
		childGTIN4B = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN4C(String string) {
		childGTIN4C = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN4D(String string) {
		childGTIN4D = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN4QtyInParent(String string) {
		childGTIN4QtyInParent = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN4Sequence(String string) {
		childGTIN4Sequence = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN5(String string) {
		childGTIN5 = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN5A(String string) {
		childGTIN5A = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN5B(String string) {
		childGTIN5B = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN5C(String string) {
		childGTIN5C = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN5D(String string) {
		childGTIN5D = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN5QtyInParent(String string) {
		childGTIN5QtyInParent = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN5Sequence(String string) {
		childGTIN5Sequence = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN6(String string) {
		childGTIN6 = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN6A(String string) {
		childGTIN6A = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN6B(String string) {
		childGTIN6B = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN6C(String string) {
		childGTIN6C = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN6D(String string) {
		childGTIN6D = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN6QtyInParent(String string) {
		childGTIN6QtyInParent = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN6Sequence(String string) {
		childGTIN6Sequence = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN7(String string) {
		childGTIN7 = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN7A(String string) {
		childGTIN7A = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN7B(String string) {
		childGTIN7B = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN7C(String string) {
		childGTIN7C = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN7D(String string) {
		childGTIN7D = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN7QtyInParent(String string) {
		childGTIN7QtyInParent = string;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN7Sequence(String string) {
		childGTIN7Sequence = string;
	}

	/**
	 * @param string
	 */
	public void setParentGTIN(String string) {
		parentGTIN = string;
	}

	/**
	 * @return
	 */
	public String getSaveButton() {
		return saveButton;
	}

	/**
	 * @param string
	 */
	public void setSaveButton(String string) {
		saveButton = string;
	}

	/**
	 * @return
	 */
	public String getChildGTIN() {
		return childGTIN;
	}

	/**
	 * @return
	 */
	public String getChildQuantity() {
		return childQuantity;
	}

	/**
	 * @return
	 */
	public String getChildSequence() {
		return childSequence;
	}

	/**
	 * @param string
	 */
	public void setChildGTIN(String string) {
		childGTIN = string;
	}

	/**
	 * @param string
	 */
	public void setChildQuantity(String string) {
		childQuantity = string;
	}

	/**
	 * @param string
	 */
	public void setChildSequence(String string) {
		childSequence = string;
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
	public void setUserProfile(String string) {
		userProfile = string;
	}

	/**
	 * @return
	 */
	public GTIN getParentInfo() {
		return parentInfo;
	}

	/**
	 * @param gtin
	 */
	public void setParentInfo(GTIN gtin) {
		parentInfo = gtin;
	}

	/**
	 * @return
	 */
	public GTINChild getChild1Info() {
		return child1Info;
	}

	/**
	 * @return
	 */
	public GTINChild getChild2Info() {
		return child2Info;
	}

	/**
	 * @return
	 */
	public GTINChild getChild3Info() {
		return child3Info;
	}

	/**
	 * @return
	 */
	public GTINChild getChild4Info() {
		return child4Info;
	}

	/**
	 * @return
	 */
	public GTINChild getChild5Info() {
		return child5Info;
	}

	/**
	 * @return
	 */
	public GTINChild getChild6Info() {
		return child6Info;
	}

	/**
	 * @return
	 */
	public GTINChild getChild7Info() {
		return child7Info;
	}

	/**
	 * @param child
	 */
	public void setChild1Info(GTINChild child) {
		child1Info = child;
	}

	/**
	 * @param child
	 */
	public void setChild2Info(GTINChild child) {
		child2Info = child;
	}

	/**
	 * @param child
	 */
	public void setChild3Info(GTINChild child) {
		child3Info = child;
	}

	/**
	 * @param child
	 */
	public void setChild4Info(GTINChild child) {
		child4Info = child;
	}

	/**
	 * @param child
	 */
	public void setChild5Info(GTINChild child) {
		child5Info = child;
	}

	/**
	 * @param child
	 */
	public void setChild6Info(GTINChild child) {
		child6Info = child;
	}

	/**
	 * @param child
	 */
	public void setChild7Info(GTINChild child) {
		child7Info = child;
	}

}
