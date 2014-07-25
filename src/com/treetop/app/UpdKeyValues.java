/*
 * Created on Nov 17, 2005
 * 
 *  Currently used to House URL's -- for images and documents
 *  Future uses for Comments
 */
package com.treetop.app;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.businessobjects.KeyValue;
import com.treetop.viewbeans.BaseViewBeanR2;
import com.treetop.services.ServiceKeyValue;
import com.treetop.utilities.*;

/**
 * @author twalto
 *
 */
public class UpdKeyValues extends BaseViewBeanR2 {
	

	// Standard Fields - to be in Every View Bean
//	public String requestType = "";
//	public String environment = "";
	public List urlErrors = null;
	public List commentErrors = null;
	public String saveButton = null;
	public String submit = null;
	public String userProfile = "";
	
	// Fields Keys are Built From
	public String gtinNumber = "";
	public String resource = "";
	public String schedule = "";
	public String olsn = "";
	public String release = "";
	public String item = "";
	public String scaleTicket = "";
	public String scaleTicketCorrectionSequence = "";
	public String lotNumber = "";
	public String commentType = "";
	public String formulaNumber = "";
	public String specNumber = "";
	public String methodNumber = "";
	public String revisionDate = "";
	public String revisionTime = "";
	public String whseNo = "";
	public String locAddNo = "";
	public String loadNumber = "";
	public String sampleNumber = "";

	// Build this Vector of BusinessObject KeyValue for Update Delete
	public Vector urlKeyValuesUpdate = new Vector(); // KeyValue
	public Vector url1KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url2KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url3KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url4KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url5KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url6KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url7KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url8KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url9KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url10KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url11KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url12KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url13KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url14KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url15KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url16KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url17KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url18KeyValuesUpdate = new Vector(); // KeyValue
	public Vector url19KeyValuesUpdate = new Vector(); // KeyValue
	
	public Vector commentKeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment1KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment2KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment3KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment4KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment5KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment6KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment7KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment8KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment9KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment10KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment11KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment12KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment13KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment14KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment15KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment16KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment17KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment18KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment19KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment20KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment21KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment22KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment23KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment24KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment25KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment26KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment27KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment28KeyValuesUpdate = new Vector(); //KeyValue
	public Vector comment29KeyValuesUpdate = new Vector(); //KeyValue
	
	public Vector urlKeyValuesDelete = new Vector(); // KeyValue
	public Vector url1KeyValuesDelete = new Vector(); // KeyValue
	public Vector url2KeyValuesDelete = new Vector(); // KeyValue
	public Vector url3KeyValuesDelete = new Vector(); // KeyValue
	public Vector url4KeyValuesDelete = new Vector(); // KeyValue
	public Vector url5KeyValuesDelete = new Vector(); // KeyValue
	public Vector url6KeyValuesDelete = new Vector(); // KeyValue
	public Vector url7KeyValuesDelete = new Vector(); // KeyValue
	public Vector url8KeyValuesDelete = new Vector(); // KeyValue
	public Vector url9KeyValuesDelete = new Vector(); // KeyValue
	public Vector url10KeyValuesDelete = new Vector(); // KeyValue
	public Vector url11KeyValuesDelete = new Vector(); // KeyValue
	public Vector url12KeyValuesDelete = new Vector(); // KeyValue
	public Vector url13KeyValuesDelete = new Vector(); // KeyValue
	public Vector url14KeyValuesDelete = new Vector(); // KeyValue
	public Vector url15KeyValuesDelete = new Vector(); // KeyValue
	public Vector url16KeyValuesDelete = new Vector(); // KeyValue
	public Vector url17KeyValuesDelete = new Vector(); // KeyValue
	public Vector url18KeyValuesDelete = new Vector(); // KeyValue
	public Vector url19KeyValuesDelete = new Vector(); // KeyValue
	
	public Vector commentKeyValuesDelete = new Vector(); //KeyValue
	public Vector comment1KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment2KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment3KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment4KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment5KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment6KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment7KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment8KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment9KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment10KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment11KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment12KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment13KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment14KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment15KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment16KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment17KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment18KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment19KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment20KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment21KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment22KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment23KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment24KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment25KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment26KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment27KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment28KeyValuesDelete = new Vector(); //KeyValue
	public Vector comment29KeyValuesDelete = new Vector(); //KeyValue

	public KeyValue urlKeyValuesAdd = null;
	public KeyValue url1KeyValuesAdd = null;
	public KeyValue url2KeyValuesAdd = null;
	public KeyValue url3KeyValuesAdd = null;
	public KeyValue url4KeyValuesAdd = null;
	public KeyValue url5KeyValuesAdd = null;
	public KeyValue url6KeyValuesAdd = null;
	public KeyValue url7KeyValuesAdd = null;
	public KeyValue url8KeyValuesAdd = null;
	public KeyValue url9KeyValuesAdd = null;
	public KeyValue url10KeyValuesAdd = null;
	public KeyValue url11KeyValuesAdd = null;
	public KeyValue url12KeyValuesAdd = null;
	public KeyValue url13KeyValuesAdd = null;
	public KeyValue url14KeyValuesAdd = null;
	public KeyValue url15KeyValuesAdd = null;
	public KeyValue url16KeyValuesAdd = null;
	public KeyValue url17KeyValuesAdd = null;
	public KeyValue url18KeyValuesAdd = null;
	public KeyValue url19KeyValuesAdd = null;
	
	public KeyValue commentKeyValuesAdd = null;
	public KeyValue comment1KeyValuesAdd = null;
	public KeyValue comment2KeyValuesAdd = null;
	public KeyValue comment3KeyValuesAdd = null;
	public KeyValue comment4KeyValuesAdd = null;
	public KeyValue comment5KeyValuesAdd = null;
	public KeyValue comment6KeyValuesAdd = null;
	public KeyValue comment7KeyValuesAdd = null;
	public KeyValue comment8KeyValuesAdd = null;
	public KeyValue comment9KeyValuesAdd = null;
	public KeyValue comment10KeyValuesAdd = null;
	public KeyValue comment11KeyValuesAdd = null;
	public KeyValue comment12KeyValuesAdd = null;
	public KeyValue comment13KeyValuesAdd = null;
	public KeyValue comment14KeyValuesAdd = null;
	public KeyValue comment15KeyValuesAdd = null;
	public KeyValue comment16KeyValuesAdd = null;
	public KeyValue comment17KeyValuesAdd = null;
	public KeyValue comment18KeyValuesAdd = null;
	public KeyValue comment19KeyValuesAdd = null;
	public KeyValue comment20KeyValuesAdd = null;
	public KeyValue comment21KeyValuesAdd = null;
	public KeyValue comment22KeyValuesAdd = null;
	public KeyValue comment23KeyValuesAdd = null;
	public KeyValue comment24KeyValuesAdd = null;
	public KeyValue comment25KeyValuesAdd = null;
	public KeyValue comment26KeyValuesAdd = null;
	public KeyValue comment27KeyValuesAdd = null;
	public KeyValue comment28KeyValuesAdd = null;
	public KeyValue comment29KeyValuesAdd = null;
	
	//UPD JSP Fields
	public String urlSequence = "";
	public String urlLongInformation = "";
	public String urlDescription = "";
	public String urlKeyValuesCount = "0";
	
	public String url1Sequence = "";
	public String url1LongInformation = "";
	public String url1Description = "";
	public String url1KeyValuesCount = "0";
	
	public String url2Sequence = "";
	public String url2LongInformation = "";
	public String url2Description = "";
	public String url2KeyValuesCount = "0";
	
	public String url3Sequence = "";
	public String url3LongInformation = "";
	public String url3Description = "";
	public String url3KeyValuesCount = "0";
	
	public String url4Sequence = "";
	public String url4LongInformation = "";
	public String url4Description = "";
	public String url4KeyValuesCount = "0";
	
	public String url5Sequence = "";
	public String url5LongInformation = "";
	public String url5Description = "";
	public String url5KeyValuesCount = "0";
	
	public String url6Sequence = "";
	public String url6LongInformation = "";
	public String url6Description = "";
	public String url6KeyValuesCount = "0";
	
	public String url7Sequence = "";
	public String url7LongInformation = "";
	public String url7Description = "";
	public String url7KeyValuesCount = "0";
	
	public String url8Sequence = "";
	public String url8LongInformation = "";
	public String url8Description = "";
	public String url8KeyValuesCount = "0";
	
	public String url9Sequence = "";
	public String url9LongInformation = "";
	public String url9Description = "";
	public String url9KeyValuesCount = "0";
	
	public String url10Sequence = "";
	public String url10LongInformation = "";
	public String url10Description = "";
	public String url10KeyValuesCount = "0";
	
	public String url11Sequence = "";
	public String url11LongInformation = "";
	public String url11Description = "";
	public String url11KeyValuesCount = "0";
	
	public String url12Sequence = "";
	public String url12LongInformation = "";
	public String url12Description = "";
	public String url12KeyValuesCount = "0";
	
	public String url13Sequence = "";
	public String url13LongInformation = "";
	public String url13Description = "";
	public String url13KeyValuesCount = "0";
	
	public String url14Sequence = "";
	public String url14LongInformation = "";
	public String url14Description = "";
	public String url14KeyValuesCount = "0";
	
	public String url15Sequence = "";
	public String url15LongInformation = "";
	public String url15Description = "";
	public String url15KeyValuesCount = "0";
	
	public String url16Sequence = "";
	public String url16LongInformation = "";
	public String url16Description = "";
	public String url16KeyValuesCount = "0";
	
	public String url17Sequence = "";
	public String url17LongInformation = "";
	public String url17Description = "";
	public String url17KeyValuesCount = "0";
	
	public String url18Sequence = "";
	public String url18LongInformation = "";
	public String url18Description = "";
	public String url18KeyValuesCount = "0";
	
	public String url19Sequence = "";
	public String url19LongInformation = "";
	public String url19Description = "";
	public String url19KeyValuesCount = "0";
	
	public String commentSequence = "";
	public String commentLongInformation = "";
	public String commentDescription = "";
	public String commentKeyValuesCount = "0";
	
	public String comment1Sequence = "";
	public String comment1LongInformation = "";
	public String comment1Description = "";
	public String comment1KeyValuesCount = "0";
	
	public String comment2Sequence = "";
	public String comment2LongInformation = "";
	public String comment2Description = "";
	public String comment2KeyValuesCount = "0";
	
	public String comment3Sequence = "";
	public String comment3LongInformation = "";
	public String comment3Description = "";
	public String comment3KeyValuesCount = "0";
	
	public String comment4Sequence = "";
	public String comment4LongInformation = "";
	public String comment4Description = "";
	public String comment4KeyValuesCount = "0";
	
	public String comment5Sequence = "";
	public String comment5LongInformation = "";
	public String comment5Description = "";
	public String comment5KeyValuesCount = "0";
	
	public String comment6Sequence = "";
	public String comment6LongInformation = "";
	public String comment6Description = "";
	public String comment6KeyValuesCount = "0";
	
	public String comment7Sequence = "";
	public String comment7LongInformation = "";
	public String comment7Description = "";
	public String comment7KeyValuesCount = "0";
	
	public String comment8Sequence = "";
	public String comment8LongInformation = "";
	public String comment8Description = "";
	public String comment8KeyValuesCount = "0";
	
	public String comment9Sequence = "";
	public String comment9LongInformation = "";
	public String comment9Description = "";
	public String comment9KeyValuesCount = "0";
	
	public String comment10Sequence = "";
	public String comment10LongInformation = "";
	public String comment10Description = "";
	public String comment10KeyValuesCount = "0";
	
	public String comment11Sequence = "";
	public String comment11LongInformation = "";
	public String comment11Description = "";
	public String comment11KeyValuesCount = "0";
	
	public String comment12Sequence = "";
	public String comment12LongInformation = "";
	public String comment12Description = "";
	public String comment12KeyValuesCount = "0";
	
	public String comment13Sequence = "";
	public String comment13LongInformation = "";
	public String comment13Description = "";
	public String comment13KeyValuesCount = "0";
	
	public String comment14Sequence = "";
	public String comment14LongInformation = "";
	public String comment14Description = "";
	public String comment14KeyValuesCount = "0";
	
	public String comment15Sequence = "";
	public String comment15LongInformation = "";
	public String comment15Description = "";
	public String comment15KeyValuesCount = "0";
	
	public String comment16Sequence = "";
	public String comment16LongInformation = "";
	public String comment16Description = "";
	public String comment16KeyValuesCount = "0";
	
	public String comment17Sequence = "";
	public String comment17LongInformation = "";
	public String comment17Description = "";
	public String comment17KeyValuesCount = "0";
	
	public String comment18Sequence = "";
	public String comment18LongInformation = "";
	public String comment18Description = "";
	public String comment18KeyValuesCount = "0";
	
	public String comment19Sequence = "";
	public String comment19LongInformation = "";
	public String comment19Description = "";
	public String comment19KeyValuesCount = "0";
	
	public String comment20Sequence = "";
	public String comment20LongInformation = "";
	public String comment20Description = "";
	public String comment20KeyValuesCount = "0";
	
	public String comment21Sequence = "";
	public String comment21LongInformation = "";
	public String comment21Description = "";
	public String comment21KeyValuesCount = "0";
	
	public String comment22Sequence = "";
	public String comment22LongInformation = "";
	public String comment22Description = "";
	public String comment22KeyValuesCount = "0";
	
	public String comment23Sequence = "";
	public String comment23LongInformation = "";
	public String comment23Description = "";
	public String comment23KeyValuesCount = "0";
	
	public String comment24Sequence = "";
	public String comment24LongInformation = "";
	public String comment24Description = "";
	public String comment24KeyValuesCount = "0";
	
	public String comment25Sequence = "";
	public String comment25LongInformation = "";
	public String comment25Description = "";
	public String comment25KeyValuesCount = "0";
	
	public String comment26Sequence = "";
	public String comment26LongInformation = "";
	public String comment26Description = "";
	public String comment26KeyValuesCount = "0";

	public String comment27Sequence = "";
	public String comment27LongInformation = "";
	public String comment27Description = "";
	public String comment27KeyValuesCount = "0";
	
	public String comment28Sequence = "";
	public String comment28LongInformation = "";
	public String comment28Description = "";
	public String comment28KeyValuesCount = "0";
	
	public String comment29Sequence = "";
	public String comment29LongInformation = "";
	public String comment29Description = "";
	public String comment29KeyValuesCount = "0";
	
		/*
	 * Use the Other Validate's ...
	 *   Will also populate information
	 */
	public void validate() {

	}
	
	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!gtinNumber.equals("")) {
				eType = "GtinComment";
				key1 = gtinNumber;
			}
			if (!item.equals("") ||
				!resource.equals(""))
			{	
				eType = "ItemNewComment";
				key1 = item;
				if (!resource.equals(""))
				   key1 = resource;
			}
			if (!schedule.equals("") &&
			    !olsn.equals("") &&
				!release.equals("")) {
				eType = "ScheduleOlsnReleaseComment";
				key1 = schedule;
				key2 = olsn;
				key3 = release;
			}
			if (!scaleTicket.equals("") && 
				 lotNumber.equals(""))
			{
			  eType = "ScaleTicketComment";
			  key1 = scaleTicket;
			  key2 = scaleTicketCorrectionSequence;
			}
			if (!lotNumber.equals(""))
			{
			  eType = "LotComment";
			  key1 = lotNumber;
			}
			if (!formulaNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "FormulaRevisionComment";
				  key1 = formulaNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!loadNumber.equals(""))
			{
			  eType = "ScheduledLoadComment";
			  key1 = this.loadNumber;
			}
			if (!whseNo.equals("") &&
				!locAddNo.equals(""))
			{
			  eType = "AvailableFruitComment";
			  key1 = this.whseNo;
			  key2 = this.locAddNo;
			}
		//	if (request.getParameter("sampleNumber") != null &&
	//			!request.getParameter("sampleNumber").equals(""))
//			{
			if (!sampleNumber.equals(""))
			{
				  eType = "SampleComment";
				  key1 = this.sampleNumber;
			}
			//********************************************
			// ADD A NEW RECORD
// 1/17/12 TWalton no longer needed using base viewbeanr2			
//			if (commentLongInformation.trim().equals(""))
//			{
//				this.setCommentLongInformation(request.getParameter("commentLongInformation"));
//				if (commentLongInformation == null)
//				   this.setCommentLongInformation("");
//			}
			if (!commentLongInformation.equals("")) {
				commentKeyValuesAdd = new KeyValue();
				commentKeyValuesAdd.setStatus("");
				commentKeyValuesAdd.setEnvironment(this.getEnvironment());
				commentKeyValuesAdd.setEntryType(eType);
				commentKeyValuesAdd.setSequence(this.commentSequence.trim());
				commentKeyValuesAdd.setKey1(key1);
				commentKeyValuesAdd.setKey2(key2);
				commentKeyValuesAdd.setKey3(key3);
				commentKeyValuesAdd.setKey4(key4);
				commentKeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				// 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				commentKeyValuesAdd.setValue(this.commentLongInformation.trim());
				//commentKeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.commentLongInformation.trim()));
				commentKeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				commentKeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (commentKeyValuesAdd.getLastUpdateUser() == null)
					commentKeyValuesAdd.setLastUpdateUser("TreeNet");
				commentKeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(commentKeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				commentKeyValuesDelete = new Vector();
				commentKeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("commentStatus" + x));
					newElement.setEnvironment(this.getEnvironment());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("commentSequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("commentUniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("commentLongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("commentLongInformationOld" + x) != null &&
					    !request.getParameter("commentLongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("commentLongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("commentDeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("commentDeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("commentDeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("commentDelete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
					   commentKeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request,
							   response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						commentKeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null ||
			submit != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!gtinNumber.equals("")) {
				eType = "GtinUrl";
				key1 = gtinNumber;
			}
			if (!item.equals(""))
			{
				eType = "ItemUrl";
				key1 = item;
			}
			if (!formulaNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "FormulaRevisionUrl";
				  key1 = formulaNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (request.getParameter("sampleNumber") != null &&
				!request.getParameter("sampleNumber").equals(""))
			{
				  eType = "SampleUrl";
				  key1 = request.getParameter("sampleNumber").trim();
			}
			//********************************************
			
			// ADD A NEW RECORD
			if (!urlLongInformation.equals("")) {
				urlKeyValuesAdd = new KeyValue();
				urlKeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   urlKeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				urlKeyValuesAdd.setEntryType(eType);
				urlKeyValuesAdd.setSequence(this.urlSequence.trim());
				urlKeyValuesAdd.setKey1(key1);
				urlKeyValuesAdd.setKey2(key2);
				urlKeyValuesAdd.setKey3(key3);
				urlKeyValuesAdd.setKey4(key4);
				urlKeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				urlKeyValuesAdd.setValue(this.urlLongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				urlKeyValuesAdd.setDescription(this.urlDescription.trim());
				// Last Update Date & Time will be filled in by the Service
				urlKeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (urlKeyValuesAdd.getLastUpdateUser() == null)
					urlKeyValuesAdd.setLastUpdateUser("TreeNet");
				urlKeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(urlKeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				urlKeyValuesDelete = new Vector();
				urlKeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("urlStatus" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("urlSequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("urlUniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("urlLongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("urlLongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("urlLongInformationOld" + x));
					newElement.setDescription(request.getParameter("urlDescription" + x));
					newElement.setDeleteDate(request.getParameter("urlDeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("urlDeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("urlDeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("urlDelete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   urlKeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   urlKeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}

		return;
	}
	/**
	 * @return
	 */
	public String getGtinNumber() {
		return gtinNumber;
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
	public void setGtinNumber(String string) {
		gtinNumber = string;
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
	public String getCommentDescription() {
		return commentDescription;
	}

	/**
	 * @return
	 */
	public List getCommentErrors() {
		return commentErrors;
	}

	/**
	 * @return
	 */
	public String getCommentLongInformation() {
		return commentLongInformation;
	}

	/**
	 * @return
	 */
	public String getCommentSequence() {
		return commentSequence;
	}

	/**
	 * @return
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * @return
	 */
	public String getUrlDescription() {
		return urlDescription;
	}

	/**
	 * @return
	 */
	public List getUrlErrors() {
		return urlErrors;
	}

	/**
	 * @return
	 */
	public String getUrlLongInformation() {
		return urlLongInformation;
	}

	/**
	 * @return
	 */
	public String getUrlSequence() {
		return urlSequence;
	}

	/**
	 * @param string
	 */
	public void setCommentDescription(String string) {
		commentDescription = string;
	}

	/**
	 * @param list
	 */
	public void setCommentErrors(List list) {
		commentErrors = list;
	}

	/**
	 * @param string
	 */
	public void setCommentLongInformation(String string) {
		commentLongInformation = string;
	}

	/**
	 * @param string
	 */
	public void setCommentSequence(String string) {
		commentSequence = string;
	}

	/**
	 * @param string
	 */
	public void setResource(String string) {
		resource = string;
	}

	/**
	 * @param string
	 */
	public void setUrlDescription(String string) {
		urlDescription = string;
	}

	/**
	 * @param list
	 */
	public void setUrlErrors(List list) {
		urlErrors = list;
	}

	/**
	 * @param string
	 */
	public void setUrlLongInformation(String string) {
		urlLongInformation = string;
	}

	/**
	 * @param string
	 */
	public void setUrlSequence(String string) {
		urlSequence = string;
	}

	/**
	 * @return
	 */
	public KeyValue getCommentKeyValuesAdd() {
		return commentKeyValuesAdd;
	}

	/**
	 * @return
	 */
	public Vector getCommentKeyValuesDelete() {
		return commentKeyValuesDelete;
	}

	/**
	 * @return
	 */
	public Vector getCommentKeyValuesUpdate() {
		return commentKeyValuesUpdate;
	}

	/**
	 * @return
	 */
	public KeyValue getUrlKeyValuesAdd() {
		return urlKeyValuesAdd;
	}

	/**
	 * @return
	 */
	public Vector getUrlKeyValuesDelete() {
		return urlKeyValuesDelete;
	}

	/**
	 * @return
	 */
	public Vector getUrlKeyValuesUpdate() {
		return urlKeyValuesUpdate;
	}

	/**
	 * @param value
	 */
	public void setCommentKeyValuesAdd(KeyValue value) {
		commentKeyValuesAdd = value;
	}

	/**
	 * @param vector
	 */
	public void setCommentKeyValuesDelete(Vector vector) {
		commentKeyValuesDelete = vector;
	}

	/**
	 * @param vector
	 */
	public void setCommentKeyValuesUpdate(Vector vector) {
		commentKeyValuesUpdate = vector;
	}

	/**
	 * @param value
	 */
	public void setUrlKeyValuesAdd(KeyValue value) {
		urlKeyValuesAdd = value;
	}

	/**
	 * @param vector
	 */
	public void setUrlKeyValuesDelete(Vector vector) {
		urlKeyValuesDelete = vector;
	}

	/**
	 * @param vector
	 */
	public void setUrlKeyValuesUpdate(Vector vector) {
		urlKeyValuesUpdate = vector;
	}

	/**
	 * @return
	 */
	public String getCommentKeyValuesCount() {
		return commentKeyValuesCount;
	}

	/**
	 * @return
	 */
	public String getUrlKeyValuesCount() {
		return urlKeyValuesCount;
	}

	/**
	 * @param string
	 */
	public void setCommentKeyValuesCount(String string) {
		commentKeyValuesCount = string;
	}

	/**
	 * @param string
	 */
	public void setUrlKeyValuesCount(String string) {
		urlKeyValuesCount = string;
	}

	/**
	 * @return Returns the olsn.
	 */
	public String getOlsn() {
		return olsn;
	}
	/**
	 * @param olsn The olsn to set.
	 */
	public void setOlsn(String olsn) {
		this.olsn = olsn;
	}
	/**
	 * @return Returns the release.
	 */
	public String getRelease() {
		return release;
	}
	/**
	 * @param release The release to set.
	 */
	public void setRelease(String release) {
		this.release = release;
	}
	/**
	 * @return Returns the schedule.
	 */
	public String getSchedule() {
		return schedule;
	}
	/**
	 * @param schedule The schedule to set.
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	/**
	 * @return Returns the item.
	 */
	public String getItem() {
		return item;
	}
	/**
	 * @param item The item to set.
	 */
	public void setItem(String item) {
		this.item = item;
	}
	/**
	 * @return Returns the lotNumber.
	 */
	public String getLotNumber() {
		return lotNumber;
	}
	/**
	 * @param lotNumber The lotNumber to set.
	 */
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	/**
	 * @return Returns the scaleTicket.
	 */
	public String getScaleTicket() {
		return scaleTicket;
	}
	/**
	 * @param scaleTicket The scaleTicket to set.
	 */
	public void setScaleTicket(String scaleTicket) {
		this.scaleTicket = scaleTicket;
	}

	public String getScaleTicketCorrectionSequence() {
		return scaleTicketCorrectionSequence;
	}

	public void setScaleTicketCorrectionSequence(
			String scaleTicketCorrectionSequence) {
		this.scaleTicketCorrectionSequence = scaleTicketCorrectionSequence;
	}

	public String getFormulaNumber() {
		return formulaNumber;
	}

	public void setFormulaNumber(String formulaNumber) {
		this.formulaNumber = formulaNumber;
	}

	public String getMethodNumber() {
		return methodNumber;
	}

	public void setMethodNumber(String methodNumber) {
		this.methodNumber = methodNumber;
	}

	public String getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getRevisionTime() {
		return revisionTime;
	}

	public void setRevisionTime(String revisionTime) {
		this.revisionTime = revisionTime;
	}

	public String getSpecNumber() {
		return specNumber;
	}

	public void setSpecNumber(String specNumber) {
		this.specNumber = specNumber;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	public String getComment1Description() {
		return comment1Description;
	}

	public void setComment1Description(String comment1Description) {
		this.comment1Description = comment1Description;
	}

	public String getComment1KeyValuesCount() {
		return comment1KeyValuesCount;
	}

	public void setComment1KeyValuesCount(String comment1KeyValuesCount) {
		this.comment1KeyValuesCount = comment1KeyValuesCount;
	}

	public String getComment1LongInformation() {
		return comment1LongInformation;
	}

	public void setComment1LongInformation(String comment1LongInformation) {
		this.comment1LongInformation = comment1LongInformation;
	}

	public String getComment1Sequence() {
		return comment1Sequence;
	}

	public void setComment1Sequence(String comment1Sequence) {
		this.comment1Sequence = comment1Sequence;
	}

	public String getComment2Description() {
		return comment2Description;
	}

	public void setComment2Description(String comment2Description) {
		this.comment2Description = comment2Description;
	}

	public String getComment2KeyValuesCount() {
		return comment2KeyValuesCount;
	}

	public void setComment2KeyValuesCount(String comment2KeyValuesCount) {
		this.comment2KeyValuesCount = comment2KeyValuesCount;
	}

	public String getComment2LongInformation() {
		return comment2LongInformation;
	}

	public void setComment2LongInformation(String comment2LongInformation) {
		this.comment2LongInformation = comment2LongInformation;
	}

	public String getComment2Sequence() {
		return comment2Sequence;
	}

	public void setComment2Sequence(String comment2Sequence) {
		this.comment2Sequence = comment2Sequence;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment1(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!formulaNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "FormulaDetailsComment";
				  key1 = formulaNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment1";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment1";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!whseNo.equals("") &&
				!locAddNo.equals(""))
				{
				  eType = "ToPurchaseFruitComment";
				  key1 = this.whseNo;
				  key2 = this.locAddNo;
				}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment1LongInformation.trim().equals(""))
//			{
//				this.setComment1LongInformation(request.getParameter("comment1LongInformation"));
//				if (comment1LongInformation == null)
//				   this.setComment1LongInformation("");
//			}
			if (!comment1LongInformation.equals("")) {
				comment1KeyValuesAdd = new KeyValue();
				comment1KeyValuesAdd.setStatus("");
				comment1KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment1KeyValuesAdd.setEntryType(eType);
				comment1KeyValuesAdd.setSequence(this.getComment1Sequence().trim());
				comment1KeyValuesAdd.setKey1(key1);
				comment1KeyValuesAdd.setKey2(key2);
				comment1KeyValuesAdd.setKey3(key3);
				comment1KeyValuesAdd.setKey4(key4);
				comment1KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment1KeyValuesAdd.setValue(this.comment1LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
				//10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment1KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment1LongInformation.trim()));
				comment1KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment1KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (comment1KeyValuesAdd.getLastUpdateUser() == null)
					comment1KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment1KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment1KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment1KeyValuesDelete = new Vector();
				comment1KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment1Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment1Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment1UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment1LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment1LongInformationOld" + x) != null &&
					    !request.getParameter("comment1LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment1LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment1DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment1DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment1DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("comment1Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
					   comment1KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request,
							   response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment1KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	public KeyValue getComment1KeyValuesAdd() {
		return comment1KeyValuesAdd;
	}

	public void setComment1KeyValuesAdd(KeyValue comment1KeyValuesAdd) {
		this.comment1KeyValuesAdd = comment1KeyValuesAdd;
	}

	public Vector getComment1KeyValuesDelete() {
		return comment1KeyValuesDelete;
	}

	public void setComment1KeyValuesDelete(Vector comment1KeyValuesDelete) {
		this.comment1KeyValuesDelete = comment1KeyValuesDelete;
	}

	public Vector getComment1KeyValuesUpdate() {
		return comment1KeyValuesUpdate;
	}

	public void setComment1KeyValuesUpdate(Vector comment1KeyValuesUpdate) {
		this.comment1KeyValuesUpdate = comment1KeyValuesUpdate;
	}

	public KeyValue getComment2KeyValuesAdd() {
		return comment2KeyValuesAdd;
	}

	public void setComment2KeyValuesAdd(KeyValue comment2KeyValuesAdd) {
		this.comment2KeyValuesAdd = comment2KeyValuesAdd;
	}

	public Vector getComment2KeyValuesDelete() {
		return comment2KeyValuesDelete;
	}

	public void setComment2KeyValuesDelete(Vector comment2KeyValuesDelete) {
		this.comment2KeyValuesDelete = comment2KeyValuesDelete;
	}

	public Vector getComment2KeyValuesUpdate() {
		return comment2KeyValuesUpdate;
	}

	public void setComment2KeyValuesUpdate(Vector comment2KeyValuesUpdate) {
		this.comment2KeyValuesUpdate = comment2KeyValuesUpdate;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment2(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!formulaNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "FormulaCalculationsComment";
				  key1 = formulaNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!methodNumber.equals("") && 
					!revisionDate.equals("") &&
					!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment2";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment2";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment2LongInformation.trim().equals(""))
//			{
//				this.setComment2LongInformation(request.getParameter("comment2LongInformation"));
//				if (comment2LongInformation == null)
//				   this.setComment2LongInformation("");
//			}
			if (!comment2LongInformation.equals("")) {
				comment2KeyValuesAdd = new KeyValue();
				comment2KeyValuesAdd.setStatus("");
				comment2KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment2KeyValuesAdd.setEntryType(eType);
				comment2KeyValuesAdd.setSequence(this.getComment2Sequence().trim());
				comment2KeyValuesAdd.setKey1(key1);
				comment2KeyValuesAdd.setKey2(key2);
				comment2KeyValuesAdd.setKey3(key3);
				comment2KeyValuesAdd.setKey4(key4);
				comment2KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment2KeyValuesAdd.setValue(this.comment2LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment2KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment2LongInformation.trim()));
				comment2KeyValuesAdd.setDescription("");
			// Last Update Date & Time will be filled in by the Service
				comment2KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (comment2KeyValuesAdd.getLastUpdateUser() == null)
					comment2KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment2KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment2KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment2KeyValuesDelete = new Vector();
				comment2KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment2Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment2Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment2UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment2LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment2LongInformationOld" + x) != null &&
					    !request.getParameter("comment2LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment2LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment2DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment2DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment2DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("comment2Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
					   comment2KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request,
							   response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment2KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	public String getComment3Description() {
		return comment3Description;
	}

	public void setComment3Description(String comment3Description) {
		this.comment3Description = comment3Description;
	}

	public KeyValue getComment3KeyValuesAdd() {
		return comment3KeyValuesAdd;
	}

	public void setComment3KeyValuesAdd(KeyValue comment3KeyValuesAdd) {
		this.comment3KeyValuesAdd = comment3KeyValuesAdd;
	}

	public String getComment3KeyValuesCount() {
		return comment3KeyValuesCount;
	}

	public void setComment3KeyValuesCount(String comment3KeyValuesCount) {
		this.comment3KeyValuesCount = comment3KeyValuesCount;
	}

	public Vector getComment3KeyValuesDelete() {
		return comment3KeyValuesDelete;
	}

	public void setComment3KeyValuesDelete(Vector comment3KeyValuesDelete) {
		this.comment3KeyValuesDelete = comment3KeyValuesDelete;
	}

	public Vector getComment3KeyValuesUpdate() {
		return comment3KeyValuesUpdate;
	}

	public void setComment3KeyValuesUpdate(Vector comment3KeyValuesUpdate) {
		this.comment3KeyValuesUpdate = comment3KeyValuesUpdate;
	}

	public String getComment3LongInformation() {
		return comment3LongInformation;
	}

	public void setComment3LongInformation(String comment3LongInformation) {
		this.comment3LongInformation = comment3LongInformation;
	}

	public String getComment3Sequence() {
		return comment3Sequence;
	}

	public void setComment3Sequence(String comment3Sequence) {
		this.comment3Sequence = comment3Sequence;
	}

	public String getComment4Description() {
		return comment4Description;
	}

	public void setComment4Description(String comment4Description) {
		this.comment4Description = comment4Description;
	}

	public KeyValue getComment4KeyValuesAdd() {
		return comment4KeyValuesAdd;
	}

	public void setComment4KeyValuesAdd(KeyValue comment4KeyValuesAdd) {
		this.comment4KeyValuesAdd = comment4KeyValuesAdd;
	}

	public String getComment4KeyValuesCount() {
		return comment4KeyValuesCount;
	}

	public void setComment4KeyValuesCount(String comment4KeyValuesCount) {
		this.comment4KeyValuesCount = comment4KeyValuesCount;
	}

	public Vector getComment4KeyValuesDelete() {
		return comment4KeyValuesDelete;
	}

	public void setComment4KeyValuesDelete(Vector comment4KeyValuesDelete) {
		this.comment4KeyValuesDelete = comment4KeyValuesDelete;
	}

	public Vector getComment4KeyValuesUpdate() {
		return comment4KeyValuesUpdate;
	}

	public void setComment4KeyValuesUpdate(Vector comment4KeyValuesUpdate) {
		this.comment4KeyValuesUpdate = comment4KeyValuesUpdate;
	}

	public String getComment4LongInformation() {
		return comment4LongInformation;
	}

	public void setComment4LongInformation(String comment4LongInformation) {
		this.comment4LongInformation = comment4LongInformation;
	}

	public String getComment4Sequence() {
		return comment4Sequence;
	}

	public void setComment4Sequence(String comment4Sequence) {
		this.comment4Sequence = comment4Sequence;
	}

	public String getComment5Description() {
		return comment5Description;
	}

	public void setComment5Description(String comment5Description) {
		this.comment5Description = comment5Description;
	}

	public KeyValue getComment5KeyValuesAdd() {
		return comment5KeyValuesAdd;
	}

	public void setComment5KeyValuesAdd(KeyValue comment5KeyValuesAdd) {
		this.comment5KeyValuesAdd = comment5KeyValuesAdd;
	}

	public String getComment5KeyValuesCount() {
		return comment5KeyValuesCount;
	}

	public void setComment5KeyValuesCount(String comment5KeyValuesCount) {
		this.comment5KeyValuesCount = comment5KeyValuesCount;
	}

	public Vector getComment5KeyValuesDelete() {
		return comment5KeyValuesDelete;
	}

	public void setComment5KeyValuesDelete(Vector comment5KeyValuesDelete) {
		this.comment5KeyValuesDelete = comment5KeyValuesDelete;
	}

	public Vector getComment5KeyValuesUpdate() {
		return comment5KeyValuesUpdate;
	}

	public void setComment5KeyValuesUpdate(Vector comment5KeyValuesUpdate) {
		this.comment5KeyValuesUpdate = comment5KeyValuesUpdate;
	}

	public String getComment5LongInformation() {
		return comment5LongInformation;
	}

	public void setComment5LongInformation(String comment5LongInformation) {
		this.comment5LongInformation = comment5LongInformation;
	}

	public String getComment5Sequence() {
		return comment5Sequence;
	}

	public void setComment5Sequence(String comment5Sequence) {
		this.comment5Sequence = comment5Sequence;
	}

	public String getComment6Description() {
		return comment6Description;
	}

	public void setComment6Description(String comment6Description) {
		this.comment6Description = comment6Description;
	}

	public KeyValue getComment6KeyValuesAdd() {
		return comment6KeyValuesAdd;
	}

	public void setComment6KeyValuesAdd(KeyValue comment6KeyValuesAdd) {
		this.comment6KeyValuesAdd = comment6KeyValuesAdd;
	}

	public String getComment6KeyValuesCount() {
		return comment6KeyValuesCount;
	}

	public void setComment6KeyValuesCount(String comment6KeyValuesCount) {
		this.comment6KeyValuesCount = comment6KeyValuesCount;
	}

	public Vector getComment6KeyValuesDelete() {
		return comment6KeyValuesDelete;
	}

	public void setComment6KeyValuesDelete(Vector comment6KeyValuesDelete) {
		this.comment6KeyValuesDelete = comment6KeyValuesDelete;
	}

	public Vector getComment6KeyValuesUpdate() {
		return comment6KeyValuesUpdate;
	}

	public void setComment6KeyValuesUpdate(Vector comment6KeyValuesUpdate) {
		this.comment6KeyValuesUpdate = comment6KeyValuesUpdate;
	}

	public String getComment6LongInformation() {
		return comment6LongInformation;
	}

	public void setComment6LongInformation(String comment6LongInformation) {
		this.comment6LongInformation = comment6LongInformation;
	}

	public String getComment6Sequence() {
		return comment6Sequence;
	}

	public void setComment6Sequence(String comment6Sequence) {
		this.comment6Sequence = comment6Sequence;
	}

	public String getComment7Description() {
		return comment7Description;
	}

	public void setComment7Description(String comment7Description) {
		this.comment7Description = comment7Description;
	}

	public KeyValue getComment7KeyValuesAdd() {
		return comment7KeyValuesAdd;
	}

	public void setComment7KeyValuesAdd(KeyValue comment7KeyValuesAdd) {
		this.comment7KeyValuesAdd = comment7KeyValuesAdd;
	}

	public String getComment7KeyValuesCount() {
		return comment7KeyValuesCount;
	}

	public void setComment7KeyValuesCount(String comment7KeyValuesCount) {
		this.comment7KeyValuesCount = comment7KeyValuesCount;
	}

	public Vector getComment7KeyValuesDelete() {
		return comment7KeyValuesDelete;
	}

	public void setComment7KeyValuesDelete(Vector comment7KeyValuesDelete) {
		this.comment7KeyValuesDelete = comment7KeyValuesDelete;
	}

	public Vector getComment7KeyValuesUpdate() {
		return comment7KeyValuesUpdate;
	}

	public void setComment7KeyValuesUpdate(Vector comment7KeyValuesUpdate) {
		this.comment7KeyValuesUpdate = comment7KeyValuesUpdate;
	}

	public String getComment7LongInformation() {
		return comment7LongInformation;
	}

	public void setComment7LongInformation(String comment7LongInformation) {
		this.comment7LongInformation = comment7LongInformation;
	}

	public String getComment7Sequence() {
		return comment7Sequence;
	}

	public void setComment7Sequence(String comment7Sequence) {
		this.comment7Sequence = comment7Sequence;
	}

	public String getComment8Description() {
		return comment8Description;
	}

	public void setComment8Description(String comment8Description) {
		this.comment8Description = comment8Description;
	}

	public KeyValue getComment8KeyValuesAdd() {
		return comment8KeyValuesAdd;
	}

	public void setComment8KeyValuesAdd(KeyValue comment8KeyValuesAdd) {
		this.comment8KeyValuesAdd = comment8KeyValuesAdd;
	}

	public String getComment8KeyValuesCount() {
		return comment8KeyValuesCount;
	}

	public void setComment8KeyValuesCount(String comment8KeyValuesCount) {
		this.comment8KeyValuesCount = comment8KeyValuesCount;
	}

	public Vector getComment8KeyValuesDelete() {
		return comment8KeyValuesDelete;
	}

	public void setComment8KeyValuesDelete(Vector comment8KeyValuesDelete) {
		this.comment8KeyValuesDelete = comment8KeyValuesDelete;
	}

	public Vector getComment8KeyValuesUpdate() {
		return comment8KeyValuesUpdate;
	}

	public void setComment8KeyValuesUpdate(Vector comment8KeyValuesUpdate) {
		this.comment8KeyValuesUpdate = comment8KeyValuesUpdate;
	}

	public String getComment8LongInformation() {
		return comment8LongInformation;
	}

	public void setComment8LongInformation(String comment8LongInformation) {
		this.comment8LongInformation = comment8LongInformation;
	}

	public String getComment8Sequence() {
		return comment8Sequence;
	}

	public void setComment8Sequence(String comment8Sequence) {
		this.comment8Sequence = comment8Sequence;
	}

	public String getComment9Description() {
		return comment9Description;
	}

	public void setComment9Description(String comment9Description) {
		this.comment9Description = comment9Description;
	}

	public KeyValue getComment9KeyValuesAdd() {
		return comment9KeyValuesAdd;
	}

	public void setComment9KeyValuesAdd(KeyValue comment9KeyValuesAdd) {
		this.comment9KeyValuesAdd = comment9KeyValuesAdd;
	}

	public String getComment9KeyValuesCount() {
		return comment9KeyValuesCount;
	}

	public void setComment9KeyValuesCount(String comment9KeyValuesCount) {
		this.comment9KeyValuesCount = comment9KeyValuesCount;
	}

	public Vector getComment9KeyValuesDelete() {
		return comment9KeyValuesDelete;
	}

	public void setComment9KeyValuesDelete(Vector comment9KeyValuesDelete) {
		this.comment9KeyValuesDelete = comment9KeyValuesDelete;
	}

	public Vector getComment9KeyValuesUpdate() {
		return comment9KeyValuesUpdate;
	}

	public void setComment9KeyValuesUpdate(Vector comment9KeyValuesUpdate) {
		this.comment9KeyValuesUpdate = comment9KeyValuesUpdate;
	}

	public String getComment9LongInformation() {
		return comment9LongInformation;
	}

	public void setComment9LongInformation(String comment9LongInformation) {
		this.comment9LongInformation = comment9LongInformation;
	}

	public String getComment9Sequence() {
		return comment9Sequence;
	}

	public void setComment9Sequence(String comment9Sequence) {
		this.comment9Sequence = comment9Sequence;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment3(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!formulaNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "FormulaBlendingInstructionsComment";
				  key1 = formulaNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				eType = "MethodRevisionComment3";
				key1 = methodNumber;
			    key2 = revisionDate;
				key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment3";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment3LongInformation.trim().equals(""))
//			{
//				this.setComment3LongInformation(request.getParameter("comment3LongInformation"));
//				if (comment3LongInformation == null)
//				   this.setComment3LongInformation("");
//			}
			if (!comment3LongInformation.equals("")) {
				comment3KeyValuesAdd = new KeyValue();
				comment3KeyValuesAdd.setStatus("");
				comment3KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment3KeyValuesAdd.setEntryType(eType);
				comment3KeyValuesAdd.setSequence(this.getComment3Sequence().trim());
				comment3KeyValuesAdd.setKey1(key1);
				comment3KeyValuesAdd.setKey2(key2);
				comment3KeyValuesAdd.setKey3(key3);
				comment3KeyValuesAdd.setKey4(key4);
				comment3KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment3KeyValuesAdd.setValue(this.comment3LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment3KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment3LongInformation.trim()));
				comment3KeyValuesAdd.setDescription("");
			// Last Update Date & Time will be filled in by the Service
				comment3KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (comment3KeyValuesAdd.getLastUpdateUser() == null)
					comment3KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment3KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment3KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment3KeyValuesDelete = new Vector();
				comment3KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment3Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment3Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment3UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment3LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment3LongInformationOld" + x) != null &&
					    !request.getParameter("comment3LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment3LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment3DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment3DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment3DeleteUser" + x));
						
					// DELETE RECORD	
					if (request.getParameter("comment3Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
					   comment3KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request,
							   response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment3KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment4(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!formulaNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "FormulaKeyLabelStatementsComment";
				  key1 = formulaNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment4";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment4";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment4LongInformation.trim().equals(""))
//			{
//				this.setComment4LongInformation(request.getParameter("comment4LongInformation"));
//				if (comment4LongInformation == null)
//				   this.setComment4LongInformation("");
//			}
			if (!comment4LongInformation.equals("")) {
				comment4KeyValuesAdd = new KeyValue();
				comment4KeyValuesAdd.setStatus("");
				comment4KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment4KeyValuesAdd.setEntryType(eType);
				comment4KeyValuesAdd.setSequence(this.getComment4Sequence().trim());
				comment4KeyValuesAdd.setKey1(key1);
				comment4KeyValuesAdd.setKey2(key2);
				comment4KeyValuesAdd.setKey3(key3);
				comment4KeyValuesAdd.setKey4(key4);
				comment4KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment4KeyValuesAdd.setValue(this.comment4LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment4KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment4LongInformation.trim()));
				comment4KeyValuesAdd.setDescription("");
			// Last Update Date & Time will be filled in by the Service
				comment4KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (comment4KeyValuesAdd.getLastUpdateUser() == null)
					comment4KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment4KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment4KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment4KeyValuesDelete = new Vector();
				comment4KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment4Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment4Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment4UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment4LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment4LongInformationOld" + x) != null &&
						!request.getParameter("comment4LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment4LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment4DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment4DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment4DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("comment4Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
					   comment4KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request,
							   response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment4KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment5(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!formulaNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "FormulaIngredientStatementComment";
				  key1 = formulaNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment5";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment5";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment5LongInformation.trim().equals(""))
//			{
//				this.setComment5LongInformation(request.getParameter("comment5LongInformation"));
//				if (comment5LongInformation == null)
//				   this.setComment5LongInformation("");
//			}
			if (!comment5LongInformation.equals("")) {
				comment5KeyValuesAdd = new KeyValue();
				comment5KeyValuesAdd.setStatus("");
				comment5KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment5KeyValuesAdd.setEntryType(eType);
				comment5KeyValuesAdd.setSequence(this.getComment5Sequence().trim());
				comment5KeyValuesAdd.setKey1(key1);
				comment5KeyValuesAdd.setKey2(key2);
				comment5KeyValuesAdd.setKey3(key3);
				comment5KeyValuesAdd.setKey4(key4);
				comment5KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment5KeyValuesAdd.setValue(this.comment5LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment5KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment5LongInformation.trim()));
				comment5KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment5KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request, response));
				if (comment5KeyValuesAdd.getLastUpdateUser() == null)
					comment5KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment5KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment5KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment5KeyValuesDelete = new Vector();
				comment5KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment5Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment5Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment5UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment5LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment5LongInformationOld" + x) != null &&
					    !request.getParameter("comment5LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment5LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment5DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment5DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment5DeleteUser" + x));
						
					// DELETE RECORD	
					if (request.getParameter("comment5Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
					   comment5KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request, response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment5KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment6(HttpServletRequest request,
				HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!formulaNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "FormulaRFInfoComment";
				  key1 = formulaNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment6";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment6";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment6LongInformation.trim().equals(""))
//			{
//				this.setComment6LongInformation(request.getParameter("comment6LongInformation"));
//				if (comment6LongInformation == null)
//				   this.setComment6LongInformation("");
//			}
			if (!comment6LongInformation.equals("")) {
				comment6KeyValuesAdd = new KeyValue();
				comment6KeyValuesAdd.setStatus("");
				comment6KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment6KeyValuesAdd.setEntryType(eType);
				comment6KeyValuesAdd.setSequence(this.getComment6Sequence().trim());
				comment6KeyValuesAdd.setKey1(key1);
				comment6KeyValuesAdd.setKey2(key2);
				comment6KeyValuesAdd.setKey3(key3);
				comment6KeyValuesAdd.setKey4(key4);
				comment6KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment6KeyValuesAdd.setValue(this.comment6LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment6KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment6LongInformation.trim()));
				comment6KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment6KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
										request,response));
				if (comment6KeyValuesAdd.getLastUpdateUser() == null)
					comment6KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment6KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment6KeyValuesCount).intValue();
			} catch (Exception e) {}
			if (countOLD > 0) {
				comment6KeyValuesDelete = new Vector();
				comment6KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment6Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment6Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment6UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment6LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment6LongInformationOld" + x) != null &&
						!request.getParameter("comment6LongInformationOld" + x).equals(""))
						newElement.setValue(request.getParameter("comment6LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment6DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment6DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment6DeleteUser" + x));
									
					// DELETE RECORD	
					if (request.getParameter("comment6Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
									request,response));
					   comment6KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request, response));
					   if (newElement.getLastUpdateUser() == null)
						   newElement.setLastUpdateUser("TreeNet");
					   	  comment6KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment7(HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
	//		if (!formulaNumber.equals("") && 
	//			!revisionDate.equals("") &&
	//			!revisionTime.equals(""))
	//		{
	//			eType = "FormulaCalculationsComment";
	//			key1 = formulaNumber;
	//		    key2 = revisionDate;
	//			key3 = revisionTime;
	//		}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment7";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment7";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment7LongInformation.trim().equals(""))
//			{
//				this.setComment7LongInformation(request.getParameter("comment7LongInformation"));
//				if (comment7LongInformation == null)
//				   this.setComment7LongInformation("");
//			}
			if (!comment7LongInformation.equals("")) {
				comment7KeyValuesAdd = new KeyValue();
				comment7KeyValuesAdd.setStatus("");
				comment7KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment7KeyValuesAdd.setEntryType(eType);
				comment7KeyValuesAdd.setSequence(this.getComment7Sequence().trim());
				comment7KeyValuesAdd.setKey1(key1);
				comment7KeyValuesAdd.setKey2(key2);
				comment7KeyValuesAdd.setKey3(key3);
				comment7KeyValuesAdd.setKey4(key4);
				comment7KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment7KeyValuesAdd.setValue(this.comment7LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment7KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment7LongInformation.trim()));
				comment7KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment7KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,response));
				if (comment7KeyValuesAdd.getLastUpdateUser() == null)
					comment7KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment7KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment7KeyValuesCount).intValue();
			} catch (Exception e) {}
			if (countOLD > 0) {
				comment7KeyValuesDelete = new Vector();
				comment7KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment7Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment7Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment7UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment7LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment7LongInformationOld" + x) != null &&
					    !request.getParameter("comment7LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment7LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment7DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment7DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment7DeleteUser" + x));
										
					// DELETE RECORD	
					if (request.getParameter("comment7Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,response));
					   comment7KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request, response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment7KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment8(HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
	//		if (!formulaNumber.equals("") && 
	//			!revisionDate.equals("") &&
	//			!revisionTime.equals(""))
	//		{
	//			  eType = "FormulaCalculationsComment";
	//			  key1 = formulaNumber;
	//			  key2 = revisionDate;
	//			  key3 = revisionTime;
	//		}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment8";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment8";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment8LongInformation.trim().equals(""))
//			{
//				this.setComment8LongInformation(request.getParameter("comment8LongInformation"));
//				if (comment8LongInformation == null)
//				   this.setComment8LongInformation("");
//			}
			if (!comment8LongInformation.equals("")) {
				comment8KeyValuesAdd = new KeyValue();
				comment8KeyValuesAdd.setStatus("");
				comment8KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment8KeyValuesAdd.setEntryType(eType);
				comment8KeyValuesAdd.setSequence(this.getComment8Sequence().trim());
				comment8KeyValuesAdd.setKey1(key1);
				comment8KeyValuesAdd.setKey2(key2);
				comment8KeyValuesAdd.setKey3(key3);
				comment8KeyValuesAdd.setKey4(key4);
				comment8KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment8KeyValuesAdd.setValue(this.comment8LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment8KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment8LongInformation.trim()));
				comment8KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment8KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
							request,response));
				if (comment8KeyValuesAdd.getLastUpdateUser() == null)
					comment8KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment8KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment8KeyValuesCount).intValue();
				} catch (Exception e) {}
			if (countOLD > 0) {
				comment8KeyValuesDelete = new Vector();
				comment8KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment8Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment8Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment8UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment8LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment8LongInformationOld" + x) != null &&
					    !request.getParameter("comment8LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment8LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment8DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment8DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment8DeleteUser" + x));
									
					// DELETE RECORD	
					if (request.getParameter("comment8Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
									request,response));
					    comment8KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request,response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
							comment8KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment9(
			HttpServletRequest request,
			HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
	//		if (!formulaNumber.equals("") && 
	//			!revisionDate.equals("") &&
	//			!revisionTime.equals(""))
	//		{
	//			  eType = "FormulaCalculationsComment";
	//			  key1 = formulaNumber;
	//			  key2 = revisionDate;
	//			  key3 = revisionTime;
	//		}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment9";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment9";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment9LongInformation.trim().equals(""))
//			{
//				this.setComment9LongInformation(request.getParameter("comment9LongInformation"));
//				if (comment9LongInformation == null)
//				   this.setComment9LongInformation("");
//			}
			if (!comment9LongInformation.equals("")) {
				comment9KeyValuesAdd = new KeyValue();
				comment9KeyValuesAdd.setStatus("");
				comment9KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment9KeyValuesAdd.setEntryType(eType);
				comment9KeyValuesAdd.setSequence(this.getComment9Sequence().trim());
				comment9KeyValuesAdd.setKey1(key1);
				comment9KeyValuesAdd.setKey2(key2);
				comment9KeyValuesAdd.setKey3(key3);
				comment9KeyValuesAdd.setKey4(key4);
				comment9KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment9KeyValuesAdd.setValue(this.comment9LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment9KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment9LongInformation.trim()));
				comment9KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment9KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,response));
				if (comment9KeyValuesAdd.getLastUpdateUser() == null)
					comment9KeyValuesAdd.setLastUpdateUser("TreeNet");
					comment9KeyValuesAdd.setDeleteUser("");
				}
				//********************************************
				// BUILD UPDATE AND DELETE VECTORS
				int countOLD = 0;
				try {
					countOLD = new Integer(comment9KeyValuesCount).intValue();
				} catch (Exception e) {}
				if (countOLD > 0) {
					comment9KeyValuesDelete = new Vector();
					comment9KeyValuesUpdate = new Vector();
					for (int x = 0; x < countOLD; x++) {
						KeyValue newElement = new KeyValue();
						newElement.setStatus(request.getParameter("comment9Status" + x));
						newElement.setEnvironment(this.getEnvironment().trim());
						newElement.setEntryType(eType);
						newElement.setSequence(request.getParameter("comment9Sequence" + x));
						newElement.setKey1(key1);
						newElement.setKey2(key2);
						newElement.setKey3(key3);
						newElement.setKey4(key4);
						newElement.setKey5(key5);
						newElement.setUniqueKey(request.getParameter("comment9UniqueNumber" + x));//Unique Field
						newElement.setValue(request.getParameter("comment9LongInformation" + x));
						if (newElement.getValue().trim().equals("") &&
							request.getParameter("comment9LongInformationOld" + x) != null &&
						    !request.getParameter("comment9LongInformationOld" + x).equals(""))
						   newElement.setValue(request.getParameter("comment9LongInformationOld" + x));
						newElement.setDescription("");
						newElement.setDeleteDate(request.getParameter("comment9DeleteDate" + x));
						newElement.setDeleteTime(request.getParameter("comment9DeleteTime" + x));
						newElement.setDeleteUser(request.getParameter("comment9DeleteUser" + x));
									
						// DELETE RECORD	
						if (request.getParameter("comment9Delete" + x) != null)
						{
							newElement.setDeleteUser(
								com.treetop.SessionVariables.getSessionttiProfile(
										request,response));
							comment9KeyValuesDelete.add(newElement);
						}   
						else // Update RECORD
						{
						   newElement.setLastUpdateUser(
							   com.treetop.SessionVariables.getSessionttiProfile(
								   request,response));
						   if (newElement.getLastUpdateUser() == null)
								newElement.setLastUpdateUser("TreeNet");
						   comment9KeyValuesUpdate.add(newElement);		   
						}
					}
				}
				//********************************************
		}
		return;
	}

	public String getComment10Description() {
		return comment10Description;
	}

	public void setComment10Description(String comment10Description) {
		this.comment10Description = comment10Description;
	}

	public KeyValue getComment10KeyValuesAdd() {
		return comment10KeyValuesAdd;
	}

	public void setComment10KeyValuesAdd(KeyValue comment10KeyValuesAdd) {
		this.comment10KeyValuesAdd = comment10KeyValuesAdd;
	}

	public String getComment10KeyValuesCount() {
		return comment10KeyValuesCount;
	}

	public void setComment10KeyValuesCount(String comment10KeyValuesCount) {
		this.comment10KeyValuesCount = comment10KeyValuesCount;
	}

	public Vector getComment10KeyValuesDelete() {
		return comment10KeyValuesDelete;
	}

	public void setComment10KeyValuesDelete(Vector comment10KeyValuesDelete) {
		this.comment10KeyValuesDelete = comment10KeyValuesDelete;
	}

	public Vector getComment10KeyValuesUpdate() {
		return comment10KeyValuesUpdate;
	}

	public void setComment10KeyValuesUpdate(Vector comment10KeyValuesUpdate) {
		this.comment10KeyValuesUpdate = comment10KeyValuesUpdate;
	}

	public String getComment10LongInformation() {
		return comment10LongInformation;
	}

	public void setComment10LongInformation(String comment10LongInformation) {
		this.comment10LongInformation = comment10LongInformation;
	}

	public String getComment10Sequence() {
		return comment10Sequence;
	}

	public void setComment10Sequence(String comment10Sequence) {
		this.comment10Sequence = comment10Sequence;
	}

	public String getComment11Description() {
		return comment11Description;
	}

	public void setComment11Description(String comment11Description) {
		this.comment11Description = comment11Description;
	}

	public KeyValue getComment11KeyValuesAdd() {
		return comment11KeyValuesAdd;
	}

	public void setComment11KeyValuesAdd(KeyValue comment11KeyValuesAdd) {
		this.comment11KeyValuesAdd = comment11KeyValuesAdd;
	}

	public String getComment11KeyValuesCount() {
		return comment11KeyValuesCount;
	}

	public void setComment11KeyValuesCount(String comment11KeyValuesCount) {
		this.comment11KeyValuesCount = comment11KeyValuesCount;
	}

	public Vector getComment11KeyValuesDelete() {
		return comment11KeyValuesDelete;
	}

	public void setComment11KeyValuesDelete(Vector comment11KeyValuesDelete) {
		this.comment11KeyValuesDelete = comment11KeyValuesDelete;
	}

	public Vector getComment11KeyValuesUpdate() {
		return comment11KeyValuesUpdate;
	}

	public void setComment11KeyValuesUpdate(Vector comment11KeyValuesUpdate) {
		this.comment11KeyValuesUpdate = comment11KeyValuesUpdate;
	}

	public String getComment11LongInformation() {
		return comment11LongInformation;
	}

	public void setComment11LongInformation(String comment11LongInformation) {
		this.comment11LongInformation = comment11LongInformation;
	}

	public String getComment11Sequence() {
		return comment11Sequence;
	}

	public void setComment11Sequence(String comment11Sequence) {
		this.comment11Sequence = comment11Sequence;
	}

	public String getComment12Description() {
		return comment12Description;
	}

	public void setComment12Description(String comment12Description) {
		this.comment12Description = comment12Description;
	}

	public KeyValue getComment12KeyValuesAdd() {
		return comment12KeyValuesAdd;
	}

	public void setComment12KeyValuesAdd(KeyValue comment12KeyValuesAdd) {
		this.comment12KeyValuesAdd = comment12KeyValuesAdd;
	}

	public String getComment12KeyValuesCount() {
		return comment12KeyValuesCount;
	}

	public void setComment12KeyValuesCount(String comment12KeyValuesCount) {
		this.comment12KeyValuesCount = comment12KeyValuesCount;
	}

	public Vector getComment12KeyValuesDelete() {
		return comment12KeyValuesDelete;
	}

	public void setComment12KeyValuesDelete(Vector comment12KeyValuesDelete) {
		this.comment12KeyValuesDelete = comment12KeyValuesDelete;
	}

	public Vector getComment12KeyValuesUpdate() {
		return comment12KeyValuesUpdate;
	}

	public void setComment12KeyValuesUpdate(Vector comment12KeyValuesUpdate) {
		this.comment12KeyValuesUpdate = comment12KeyValuesUpdate;
	}

	public String getComment12LongInformation() {
		return comment12LongInformation;
	}

	public void setComment12LongInformation(String comment12LongInformation) {
		this.comment12LongInformation = comment12LongInformation;
	}

	public String getComment12Sequence() {
		return comment12Sequence;
	}

	public void setComment12Sequence(String comment12Sequence) {
		this.comment12Sequence = comment12Sequence;
	}

	public String getComment13Description() {
		return comment13Description;
	}

	public void setComment13Description(String comment13Description) {
		this.comment13Description = comment13Description;
	}

	public KeyValue getComment13KeyValuesAdd() {
		return comment13KeyValuesAdd;
	}

	public void setComment13KeyValuesAdd(KeyValue comment13KeyValuesAdd) {
		this.comment13KeyValuesAdd = comment13KeyValuesAdd;
	}

	public String getComment13KeyValuesCount() {
		return comment13KeyValuesCount;
	}

	public void setComment13KeyValuesCount(String comment13KeyValuesCount) {
		this.comment13KeyValuesCount = comment13KeyValuesCount;
	}

	public Vector getComment13KeyValuesDelete() {
		return comment13KeyValuesDelete;
	}

	public void setComment13KeyValuesDelete(Vector comment13KeyValuesDelete) {
		this.comment13KeyValuesDelete = comment13KeyValuesDelete;
	}

	public Vector getComment13KeyValuesUpdate() {
		return comment13KeyValuesUpdate;
	}

	public void setComment13KeyValuesUpdate(Vector comment13KeyValuesUpdate) {
		this.comment13KeyValuesUpdate = comment13KeyValuesUpdate;
	}

	public String getComment13LongInformation() {
		return comment13LongInformation;
	}

	public void setComment13LongInformation(String comment13LongInformation) {
		this.comment13LongInformation = comment13LongInformation;
	}

	public String getComment13Sequence() {
		return comment13Sequence;
	}

	public void setComment13Sequence(String comment13Sequence) {
		this.comment13Sequence = comment13Sequence;
	}

	public String getComment14Description() {
		return comment14Description;
	}

	public void setComment14Description(String comment14Description) {
		this.comment14Description = comment14Description;
	}

	public KeyValue getComment14KeyValuesAdd() {
		return comment14KeyValuesAdd;
	}

	public void setComment14KeyValuesAdd(KeyValue comment14KeyValuesAdd) {
		this.comment14KeyValuesAdd = comment14KeyValuesAdd;
	}

	public String getComment14KeyValuesCount() {
		return comment14KeyValuesCount;
	}

	public void setComment14KeyValuesCount(String comment14KeyValuesCount) {
		this.comment14KeyValuesCount = comment14KeyValuesCount;
	}

	public Vector getComment14KeyValuesDelete() {
		return comment14KeyValuesDelete;
	}

	public void setComment14KeyValuesDelete(Vector comment14KeyValuesDelete) {
		this.comment14KeyValuesDelete = comment14KeyValuesDelete;
	}

	public Vector getComment14KeyValuesUpdate() {
		return comment14KeyValuesUpdate;
	}

	public void setComment14KeyValuesUpdate(Vector comment14KeyValuesUpdate) {
		this.comment14KeyValuesUpdate = comment14KeyValuesUpdate;
	}

	public String getComment14LongInformation() {
		return comment14LongInformation;
	}

	public void setComment14LongInformation(String comment14LongInformation) {
		this.comment14LongInformation = comment14LongInformation;
	}

	public String getComment14Sequence() {
		return comment14Sequence;
	}

	public void setComment14Sequence(String comment14Sequence) {
		this.comment14Sequence = comment14Sequence;
	}

	public String getComment15Description() {
		return comment15Description;
	}

	public void setComment15Description(String comment15Description) {
		this.comment15Description = comment15Description;
	}

	public KeyValue getComment15KeyValuesAdd() {
		return comment15KeyValuesAdd;
	}

	public void setComment15KeyValuesAdd(KeyValue comment15KeyValuesAdd) {
		this.comment15KeyValuesAdd = comment15KeyValuesAdd;
	}

	public String getComment15KeyValuesCount() {
		return comment15KeyValuesCount;
	}

	public void setComment15KeyValuesCount(String comment15KeyValuesCount) {
		this.comment15KeyValuesCount = comment15KeyValuesCount;
	}

	public Vector getComment15KeyValuesDelete() {
		return comment15KeyValuesDelete;
	}

	public void setComment15KeyValuesDelete(Vector comment15KeyValuesDelete) {
		this.comment15KeyValuesDelete = comment15KeyValuesDelete;
	}

	public Vector getComment15KeyValuesUpdate() {
		return comment15KeyValuesUpdate;
	}

	public void setComment15KeyValuesUpdate(Vector comment15KeyValuesUpdate) {
		this.comment15KeyValuesUpdate = comment15KeyValuesUpdate;
	}

	public String getComment15LongInformation() {
		return comment15LongInformation;
	}

	public void setComment15LongInformation(String comment15LongInformation) {
		this.comment15LongInformation = comment15LongInformation;
	}

	public String getComment15Sequence() {
		return comment15Sequence;
	}

	public void setComment15Sequence(String comment15Sequence) {
		this.comment15Sequence = comment15Sequence;
	}

	public String getComment16Description() {
		return comment16Description;
	}

	public void setComment16Description(String comment16Description) {
		this.comment16Description = comment16Description;
	}

	public KeyValue getComment16KeyValuesAdd() {
		return comment16KeyValuesAdd;
	}

	public void setComment16KeyValuesAdd(KeyValue comment16KeyValuesAdd) {
		this.comment16KeyValuesAdd = comment16KeyValuesAdd;
	}

	public String getComment16KeyValuesCount() {
		return comment16KeyValuesCount;
	}

	public void setComment16KeyValuesCount(String comment16KeyValuesCount) {
		this.comment16KeyValuesCount = comment16KeyValuesCount;
	}

	public Vector getComment16KeyValuesDelete() {
		return comment16KeyValuesDelete;
	}

	public void setComment16KeyValuesDelete(Vector comment16KeyValuesDelete) {
		this.comment16KeyValuesDelete = comment16KeyValuesDelete;
	}

	public Vector getComment16KeyValuesUpdate() {
		return comment16KeyValuesUpdate;
	}

	public void setComment16KeyValuesUpdate(Vector comment16KeyValuesUpdate) {
		this.comment16KeyValuesUpdate = comment16KeyValuesUpdate;
	}

	public String getComment16LongInformation() {
		return comment16LongInformation;
	}

	public void setComment16LongInformation(String comment16LongInformation) {
		this.comment16LongInformation = comment16LongInformation;
	}

	public String getComment16Sequence() {
		return comment16Sequence;
	}

	public void setComment16Sequence(String comment16Sequence) {
		this.comment16Sequence = comment16Sequence;
	}

	public String getComment17Description() {
		return comment17Description;
	}

	public void setComment17Description(String comment17Description) {
		this.comment17Description = comment17Description;
	}

	public KeyValue getComment17KeyValuesAdd() {
		return comment17KeyValuesAdd;
	}

	public void setComment17KeyValuesAdd(KeyValue comment17KeyValuesAdd) {
		this.comment17KeyValuesAdd = comment17KeyValuesAdd;
	}

	public String getComment17KeyValuesCount() {
		return comment17KeyValuesCount;
	}

	public void setComment17KeyValuesCount(String comment17KeyValuesCount) {
		this.comment17KeyValuesCount = comment17KeyValuesCount;
	}

	public Vector getComment17KeyValuesDelete() {
		return comment17KeyValuesDelete;
	}

	public void setComment17KeyValuesDelete(Vector comment17KeyValuesDelete) {
		this.comment17KeyValuesDelete = comment17KeyValuesDelete;
	}

	public Vector getComment17KeyValuesUpdate() {
		return comment17KeyValuesUpdate;
	}

	public void setComment17KeyValuesUpdate(Vector comment17KeyValuesUpdate) {
		this.comment17KeyValuesUpdate = comment17KeyValuesUpdate;
	}

	public String getComment17LongInformation() {
		return comment17LongInformation;
	}

	public void setComment17LongInformation(String comment17LongInformation) {
		this.comment17LongInformation = comment17LongInformation;
	}

	public String getComment17Sequence() {
		return comment17Sequence;
	}

	public void setComment17Sequence(String comment17Sequence) {
		this.comment17Sequence = comment17Sequence;
	}

	public String getComment18Description() {
		return comment18Description;
	}

	public void setComment18Description(String comment18Description) {
		this.comment18Description = comment18Description;
	}

	public KeyValue getComment18KeyValuesAdd() {
		return comment18KeyValuesAdd;
	}

	public void setComment18KeyValuesAdd(KeyValue comment18KeyValuesAdd) {
		this.comment18KeyValuesAdd = comment18KeyValuesAdd;
	}

	public String getComment18KeyValuesCount() {
		return comment18KeyValuesCount;
	}

	public void setComment18KeyValuesCount(String comment18KeyValuesCount) {
		this.comment18KeyValuesCount = comment18KeyValuesCount;
	}

	public Vector getComment18KeyValuesDelete() {
		return comment18KeyValuesDelete;
	}

	public void setComment18KeyValuesDelete(Vector comment18KeyValuesDelete) {
		this.comment18KeyValuesDelete = comment18KeyValuesDelete;
	}

	public Vector getComment18KeyValuesUpdate() {
		return comment18KeyValuesUpdate;
	}

	public void setComment18KeyValuesUpdate(Vector comment18KeyValuesUpdate) {
		this.comment18KeyValuesUpdate = comment18KeyValuesUpdate;
	}

	public String getComment18LongInformation() {
		return comment18LongInformation;
	}

	public void setComment18LongInformation(String comment18LongInformation) {
		this.comment18LongInformation = comment18LongInformation;
	}

	public String getComment18Sequence() {
		return comment18Sequence;
	}

	public void setComment18Sequence(String comment18Sequence) {
		this.comment18Sequence = comment18Sequence;
	}

	public String getComment19Description() {
		return comment19Description;
	}

	public void setComment19Description(String comment19Description) {
		this.comment19Description = comment19Description;
	}

	public KeyValue getComment19KeyValuesAdd() {
		return comment19KeyValuesAdd;
	}

	public void setComment19KeyValuesAdd(KeyValue comment19KeyValuesAdd) {
		this.comment19KeyValuesAdd = comment19KeyValuesAdd;
	}

	public String getComment19KeyValuesCount() {
		return comment19KeyValuesCount;
	}

	public void setComment19KeyValuesCount(String comment19KeyValuesCount) {
		this.comment19KeyValuesCount = comment19KeyValuesCount;
	}

	public Vector getComment19KeyValuesDelete() {
		return comment19KeyValuesDelete;
	}

	public void setComment19KeyValuesDelete(Vector comment19KeyValuesDelete) {
		this.comment19KeyValuesDelete = comment19KeyValuesDelete;
	}

	public Vector getComment19KeyValuesUpdate() {
		return comment19KeyValuesUpdate;
	}

	public void setComment19KeyValuesUpdate(Vector comment19KeyValuesUpdate) {
		this.comment19KeyValuesUpdate = comment19KeyValuesUpdate;
	}

	public String getComment19LongInformation() {
		return comment19LongInformation;
	}

	public void setComment19LongInformation(String comment19LongInformation) {
		this.comment19LongInformation = comment19LongInformation;
	}

	public String getComment19Sequence() {
		return comment19Sequence;
	}

	public void setComment19Sequence(String comment19Sequence) {
		this.comment19Sequence = comment19Sequence;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment10(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment10";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment10";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment10LongInformation.trim().equals(""))
//			{
//				this.setComment10LongInformation(request.getParameter("comment10LongInformation"));
//				if (comment10LongInformation == null)
//				   this.setComment10LongInformation("");
//			}
			if (!comment10LongInformation.equals("")) {
				comment10KeyValuesAdd = new KeyValue();
				comment10KeyValuesAdd.setStatus("");
				comment10KeyValuesAdd.setEnvironment(this.getEnvironment());
				comment10KeyValuesAdd.setEntryType(eType);
				comment10KeyValuesAdd.setSequence(this.comment10Sequence.trim());
				comment10KeyValuesAdd.setKey1(key1);
				comment10KeyValuesAdd.setKey2(key2);
				comment10KeyValuesAdd.setKey3(key3);
				comment10KeyValuesAdd.setKey4(key4);
				comment10KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment10KeyValuesAdd.setValue(this.comment10LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment10KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment10LongInformation.trim()));
				comment10KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment10KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (comment10KeyValuesAdd.getLastUpdateUser() == null)
					comment10KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment10KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment10KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment10KeyValuesDelete = new Vector();
				comment10KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment10Status" + x));
					newElement.setEnvironment(this.getEnvironment());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment10Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment10UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment10LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment10LongInformationOld" + x) != null &&
					    !request.getParameter("comment10LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment10LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment10DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment10DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment10DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("comment10Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
					   comment10KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request,
							   response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment10KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment11(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
		
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment11";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment11";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2	
//			if (comment11LongInformation.trim().equals(""))
//			{
//				this.setComment11LongInformation(request.getParameter("comment11LongInformation"));
//				if (comment11LongInformation == null)
//				   this.setComment11LongInformation("");
//			}
			if (!comment11LongInformation.equals("")) {
				comment11KeyValuesAdd = new KeyValue();
				comment11KeyValuesAdd.setStatus("");
				comment11KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment11KeyValuesAdd.setEntryType(eType);
				comment11KeyValuesAdd.setSequence(this.getComment11Sequence().trim());
				comment11KeyValuesAdd.setKey1(key1);
				comment11KeyValuesAdd.setKey2(key2);
				comment11KeyValuesAdd.setKey3(key3);
				comment11KeyValuesAdd.setKey4(key4);
				comment11KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment11KeyValuesAdd.setValue(this.comment11LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment11KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment11LongInformation.trim()));
				comment11KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment11KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (comment11KeyValuesAdd.getLastUpdateUser() == null)
					comment11KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment11KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment11KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment11KeyValuesDelete = new Vector();
				comment11KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment11Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment11Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment11UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment11LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment11LongInformationOld" + x) != null &&
					    !request.getParameter("comment11LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment11LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment11DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment11DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment11DeleteUser" + x));
						
					// DELETE RECORD	
					if (request.getParameter("comment11Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
					   comment11KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request,
							   response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment11KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment12(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment12";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment12";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment12LongInformation.trim().equals(""))
//			{
//				this.setComment12LongInformation(request.getParameter("comment12LongInformation"));
//				if (comment12LongInformation == null)
//				   this.setComment12LongInformation("");
//			}
			if (!comment12LongInformation.equals("")) {
				comment12KeyValuesAdd = new KeyValue();
				comment12KeyValuesAdd.setStatus("");
				comment12KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment12KeyValuesAdd.setEntryType(eType);
				comment12KeyValuesAdd.setSequence(this.getComment12Sequence().trim());
				comment12KeyValuesAdd.setKey1(key1);
				comment12KeyValuesAdd.setKey2(key2);
				comment12KeyValuesAdd.setKey3(key3);
				comment12KeyValuesAdd.setKey4(key4);
				comment12KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment12KeyValuesAdd.setValue(this.comment12LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment12KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment12LongInformation.trim()));
				comment12KeyValuesAdd.setDescription("");
			// Last Update Date & Time will be filled in by the Service
				comment12KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (comment12KeyValuesAdd.getLastUpdateUser() == null)
					comment12KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment12KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment12KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment12KeyValuesDelete = new Vector();
				comment12KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment12Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment12Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment12UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment12LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment12LongInformationOld" + x) != null &&
					    !request.getParameter("comment12LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment12LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment12DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment12DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment12DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("comment12Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
					   comment12KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request,
							   response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment12KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment13(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
			      eType = "MethodRevisionComment13";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment13";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment13LongInformation.trim().equals(""))
//			{
//				this.setComment13LongInformation(request.getParameter("comment13LongInformation"));
//				if (comment13LongInformation == null)
//				   this.setComment13LongInformation("");
//			}
			if (!comment13LongInformation.equals("")) {
				comment13KeyValuesAdd = new KeyValue();
				comment13KeyValuesAdd.setStatus("");
				comment13KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment13KeyValuesAdd.setEntryType(eType);
				comment13KeyValuesAdd.setSequence(this.getComment13Sequence().trim());
				comment13KeyValuesAdd.setKey1(key1);
				comment13KeyValuesAdd.setKey2(key2);
				comment13KeyValuesAdd.setKey3(key3);
				comment13KeyValuesAdd.setKey4(key4);
				comment13KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment13KeyValuesAdd.setValue(this.comment13LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment13KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment13LongInformation.trim()));
				comment13KeyValuesAdd.setDescription("");
			// Last Update Date & Time will be filled in by the Service
				comment13KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (comment13KeyValuesAdd.getLastUpdateUser() == null)
					comment13KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment13KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment13KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment13KeyValuesDelete = new Vector();
				comment13KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment13Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment13Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment13UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment13LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment13LongInformationOld" + x) != null &&
					    !request.getParameter("comment13LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment13LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment13DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment13DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment13DeleteUser" + x));
						
					// DELETE RECORD	
					if (request.getParameter("comment13Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
					   comment13KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request,
							   response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment13KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment14(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
		
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment14";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment14";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment14LongInformation.trim().equals(""))
//			{
//				this.setComment14LongInformation(request.getParameter("comment14LongInformation"));
//				if (comment14LongInformation == null)
//				   this.setComment14LongInformation("");
//			}
			if (!comment14LongInformation.equals("")) {
				comment14KeyValuesAdd = new KeyValue();
				comment14KeyValuesAdd.setStatus("");
				comment14KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment14KeyValuesAdd.setEntryType(eType);
				comment14KeyValuesAdd.setSequence(this.getComment14Sequence().trim());
				comment14KeyValuesAdd.setKey1(key1);
				comment14KeyValuesAdd.setKey2(key2);
				comment14KeyValuesAdd.setKey3(key3);
				comment14KeyValuesAdd.setKey4(key4);
				comment14KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment14KeyValuesAdd.setValue(this.comment14LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment14KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment14LongInformation.trim()));
				comment14KeyValuesAdd.setDescription("");
			// Last Update Date & Time will be filled in by the Service
				comment14KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (comment14KeyValuesAdd.getLastUpdateUser() == null)
					comment14KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment14KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment14KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment14KeyValuesDelete = new Vector();
				comment14KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment14Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment14Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment14UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment14LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment14LongInformationOld" + x) != null &&
						!request.getParameter("comment14LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment14LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment14DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment14DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment14DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("comment14Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
					   comment14KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request,
							   response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment14KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment15(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
		
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment15";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment15";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment15LongInformation.trim().equals(""))
//			{
//				this.setComment15LongInformation(request.getParameter("comment15LongInformation"));
//				if (comment15LongInformation == null)
//				   this.setComment15LongInformation("");
//			}
			if (!comment15LongInformation.equals("")) {
				comment15KeyValuesAdd = new KeyValue();
				comment15KeyValuesAdd.setStatus("");
				comment15KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment15KeyValuesAdd.setEntryType(eType);
				comment15KeyValuesAdd.setSequence(this.getComment15Sequence().trim());
				comment15KeyValuesAdd.setKey1(key1);
				comment15KeyValuesAdd.setKey2(key2);
				comment15KeyValuesAdd.setKey3(key3);
				comment15KeyValuesAdd.setKey4(key4);
				comment15KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment15KeyValuesAdd.setValue(this.comment15LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment15KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment15LongInformation.trim()));
				comment15KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment15KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request, response));
				if (comment15KeyValuesAdd.getLastUpdateUser() == null)
					comment15KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment15KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment15KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment15KeyValuesDelete = new Vector();
				comment15KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment15Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment15Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment15UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment15LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment15LongInformationOld" + x) != null &&
					    !request.getParameter("comment15LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment15LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment15DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment15DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment15DeleteUser" + x));
						
					// DELETE RECORD	
					if (request.getParameter("comment15Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
					   comment15KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request, response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment15KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment16(HttpServletRequest request,
				HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment16";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment16";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment16LongInformation.trim().equals(""))
//			{
//				this.setComment16LongInformation(request.getParameter("comment16LongInformation"));
//				if (comment16LongInformation == null)
//				   this.setComment16LongInformation("");
//			}
			
			if (!comment16LongInformation.equals("")) {
				comment16KeyValuesAdd = new KeyValue();
				comment16KeyValuesAdd.setStatus("");
				comment16KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment16KeyValuesAdd.setEntryType(eType);
				comment16KeyValuesAdd.setSequence(this.getComment16Sequence().trim());
				comment16KeyValuesAdd.setKey1(key1);
				comment16KeyValuesAdd.setKey2(key2);
				comment16KeyValuesAdd.setKey3(key3);
				comment16KeyValuesAdd.setKey4(key4);
				comment16KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment16KeyValuesAdd.setValue(this.comment16LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment16KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment16LongInformation.trim()));
				comment16KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment16KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
										request,response));
				if (comment16KeyValuesAdd.getLastUpdateUser() == null)
					comment16KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment16KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment16KeyValuesCount).intValue();
			} catch (Exception e) {}
			if (countOLD > 0) {
				comment16KeyValuesDelete = new Vector();
				comment16KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment16Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment16Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment16UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment16LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment16LongInformationOld" + x) != null &&
						!request.getParameter("comment16LongInformationOld" + x).equals(""))
						newElement.setValue(request.getParameter("comment16LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment16DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment16DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment16DeleteUser" + x));
										
					// DELETE RECORD	
					if (request.getParameter("comment16Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
									request,response));
					   comment16KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request, response));
					   if (newElement.getLastUpdateUser() == null)
						   newElement.setLastUpdateUser("TreeNet");
					   	  comment16KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment17(HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment17";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment17";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment17LongInformation.trim().equals(""))
//			{
//				this.setComment17LongInformation(request.getParameter("comment17LongInformation"));
//				if (comment17LongInformation == null)
//				   this.setComment17LongInformation("");
//			}
			if (!comment17LongInformation.equals("")) {
				comment17KeyValuesAdd = new KeyValue();
				comment17KeyValuesAdd.setStatus("");
				comment17KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment17KeyValuesAdd.setEntryType(eType);
				comment17KeyValuesAdd.setSequence(this.getComment17Sequence().trim());
				comment17KeyValuesAdd.setKey1(key1);
				comment17KeyValuesAdd.setKey2(key2);
				comment17KeyValuesAdd.setKey3(key3);
				comment17KeyValuesAdd.setKey4(key4);
				comment17KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment17KeyValuesAdd.setValue(this.comment17LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment17KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment17LongInformation.trim()));
				comment17KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment17KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,response));
				if (comment17KeyValuesAdd.getLastUpdateUser() == null)
					comment17KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment17KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment17KeyValuesCount).intValue();
			} catch (Exception e) {}
			if (countOLD > 0) {
				comment17KeyValuesDelete = new Vector();
				comment17KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment17Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment17Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment17UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment17LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment17LongInformationOld" + x) != null &&
					    !request.getParameter("comment17LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment17LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment17DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment17DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment17DeleteUser" + x));
										
					// DELETE RECORD	
					if (request.getParameter("comment17Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,response));
					   comment17KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request, response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment17KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment18(HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment18";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment18";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2				
//			if (comment18LongInformation.trim().equals(""))
//			{
//				this.setComment18LongInformation(request.getParameter("comment18LongInformation"));
//				if (comment18LongInformation == null)
//				   this.setComment18LongInformation("");
//			}
			if (!comment18LongInformation.equals("")) {
				comment18KeyValuesAdd = new KeyValue();
				comment18KeyValuesAdd.setStatus("");
				comment18KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment18KeyValuesAdd.setEntryType(eType);
				comment18KeyValuesAdd.setSequence(this.getComment18Sequence().trim());
				comment18KeyValuesAdd.setKey1(key1);
				comment18KeyValuesAdd.setKey2(key2);
				comment18KeyValuesAdd.setKey3(key3);
				comment18KeyValuesAdd.setKey4(key4);
				comment18KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment18KeyValuesAdd.setValue(this.comment18LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment18KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment18LongInformation.trim()));
				comment18KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment18KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
							request,response));
				if (comment18KeyValuesAdd.getLastUpdateUser() == null)
					comment18KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment18KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment18KeyValuesCount).intValue();
				} catch (Exception e) {}
			if (countOLD > 0) {
				comment18KeyValuesDelete = new Vector();
				comment18KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment18Status" + x));
					newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment18Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment18UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment18LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment18LongInformationOld" + x) != null &&
					    !request.getParameter("comment18LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment18LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment18DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment18DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment18DeleteUser" + x));
									
					// DELETE RECORD	
					if (request.getParameter("comment18Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
									request,response));
					    comment18KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
							   request,response));
					   if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
							comment18KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateComment19(
			HttpServletRequest request,
			HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
		
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionComment19";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment19";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			// 1/17/12 TWalton no longer needed using base viewbeanr2	
//			if (comment19LongInformation.trim().equals(""))
//			{
//				this.setComment19LongInformation(request.getParameter("comment19LongInformation"));
//				if (comment19LongInformation == null)
//				   this.setComment19LongInformation("");
//			}
			if (!comment19LongInformation.equals("")) {
				comment19KeyValuesAdd = new KeyValue();
				comment19KeyValuesAdd.setStatus("");
				comment19KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				comment19KeyValuesAdd.setEntryType(eType);
				comment19KeyValuesAdd.setSequence(this.getComment19Sequence().trim());
				comment19KeyValuesAdd.setKey1(key1);
				comment19KeyValuesAdd.setKey2(key2);
				comment19KeyValuesAdd.setKey3(key3);
				comment19KeyValuesAdd.setKey4(key4);
				comment19KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment19KeyValuesAdd.setValue(this.comment19LongInformation.trim());
//				 5/11/11 -- Added information for apostrophe
//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
				//comment19KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment19LongInformation.trim()));
				comment19KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment19KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,response));
				if (comment19KeyValuesAdd.getLastUpdateUser() == null)
					comment19KeyValuesAdd.setLastUpdateUser("TreeNet");
					comment19KeyValuesAdd.setDeleteUser("");
				}
				//********************************************
				// BUILD UPDATE AND DELETE VECTORS
				int countOLD = 0;
				try {
					countOLD = new Integer(comment19KeyValuesCount).intValue();
				} catch (Exception e) {}
				if (countOLD > 0) {
					comment19KeyValuesDelete = new Vector();
					comment19KeyValuesUpdate = new Vector();
					for (int x = 0; x < countOLD; x++) {
						KeyValue newElement = new KeyValue();
						newElement.setStatus(request.getParameter("comment19Status" + x));
						newElement.setEnvironment(this.getEnvironment().trim());
						newElement.setEntryType(eType);
						newElement.setSequence(request.getParameter("comment19Sequence" + x));
						newElement.setKey1(key1);
						newElement.setKey2(key2);
						newElement.setKey3(key3);
						newElement.setKey4(key4);
						newElement.setKey5(key5);
						newElement.setUniqueKey(request.getParameter("comment19UniqueNumber" + x));//Unique Field
						newElement.setValue(request.getParameter("comment19LongInformation" + x));
						if (newElement.getValue().trim().equals("") &&
							request.getParameter("comment19LongInformationOld" + x) != null &&
						    !request.getParameter("comment19LongInformationOld" + x).equals(""))
						   newElement.setValue(request.getParameter("comment19LongInformationOld" + x));
						newElement.setDescription("");
						newElement.setDeleteDate(request.getParameter("comment19DeleteDate" + x));
						newElement.setDeleteTime(request.getParameter("comment19DeleteTime" + x));
						newElement.setDeleteUser(request.getParameter("comment19DeleteUser" + x));
									
						// DELETE RECORD	
						if (request.getParameter("comment19Delete" + x) != null)
						{
							newElement.setDeleteUser(
								com.treetop.SessionVariables.getSessionttiProfile(
										request,response));
							comment19KeyValuesDelete.add(newElement);
						}   
						else // Update RECORD
						{
						   newElement.setLastUpdateUser(
							   com.treetop.SessionVariables.getSessionttiProfile(
								   request,response));
						   if (newElement.getLastUpdateUser() == null)
								newElement.setLastUpdateUser("TreeNet");
						   comment19KeyValuesUpdate.add(newElement);		   
						}
					}
				}
				//********************************************
		}
		return;
	}

	public String getLoadNumber() {
		return loadNumber;
	}
	public void setLoadNumber(String loadNumber) {
		this.loadNumber = loadNumber;
	}

	public String getLocAddNo() {
		return locAddNo;
	}

	public void setLocAddNo(String locAddNo) {
		this.locAddNo = locAddNo;
	}

	public String getWhseNo() {
		return whseNo;
	}

	public void setWhseNo(String whseNo) {
		this.whseNo = whseNo;
	}

	public String getUrl10Description() {
		return url10Description;
	}

	public void setUrl10Description(String url10Description) {
		this.url10Description = url10Description;
	}

	public KeyValue getUrl10KeyValuesAdd() {
		return url10KeyValuesAdd;
	}

	public void setUrl10KeyValuesAdd(KeyValue url10KeyValuesAdd) {
		this.url10KeyValuesAdd = url10KeyValuesAdd;
	}

	public String getUrl10KeyValuesCount() {
		return url10KeyValuesCount;
	}

	public void setUrl10KeyValuesCount(String url10KeyValuesCount) {
		this.url10KeyValuesCount = url10KeyValuesCount;
	}

	public Vector getUrl10KeyValuesDelete() {
		return url10KeyValuesDelete;
	}

	public void setUrl10KeyValuesDelete(Vector url10KeyValuesDelete) {
		this.url10KeyValuesDelete = url10KeyValuesDelete;
	}

	public Vector getUrl10KeyValuesUpdate() {
		return url10KeyValuesUpdate;
	}

	public void setUrl10KeyValuesUpdate(Vector url10KeyValuesUpdate) {
		this.url10KeyValuesUpdate = url10KeyValuesUpdate;
	}

	public String getUrl10LongInformation() {
		return url10LongInformation;
	}

	public void setUrl10LongInformation(String url10LongInformation) {
		this.url10LongInformation = url10LongInformation;
	}

	public String getUrl10Sequence() {
		return url10Sequence;
	}

	public void setUrl10Sequence(String url10Sequence) {
		this.url10Sequence = url10Sequence;
	}

	public String getUrl11Description() {
		return url11Description;
	}

	public void setUrl11Description(String url11Description) {
		this.url11Description = url11Description;
	}

	public KeyValue getUrl11KeyValuesAdd() {
		return url11KeyValuesAdd;
	}

	public void setUrl11KeyValuesAdd(KeyValue url11KeyValuesAdd) {
		this.url11KeyValuesAdd = url11KeyValuesAdd;
	}

	public String getUrl11KeyValuesCount() {
		return url11KeyValuesCount;
	}

	public void setUrl11KeyValuesCount(String url11KeyValuesCount) {
		this.url11KeyValuesCount = url11KeyValuesCount;
	}

	public Vector getUrl11KeyValuesDelete() {
		return url11KeyValuesDelete;
	}

	public void setUrl11KeyValuesDelete(Vector url11KeyValuesDelete) {
		this.url11KeyValuesDelete = url11KeyValuesDelete;
	}

	public Vector getUrl11KeyValuesUpdate() {
		return url11KeyValuesUpdate;
	}

	public void setUrl11KeyValuesUpdate(Vector url11KeyValuesUpdate) {
		this.url11KeyValuesUpdate = url11KeyValuesUpdate;
	}

	public String getUrl11LongInformation() {
		return url11LongInformation;
	}

	public void setUrl11LongInformation(String url11LongInformation) {
		this.url11LongInformation = url11LongInformation;
	}

	public String getUrl11Sequence() {
		return url11Sequence;
	}

	public void setUrl11Sequence(String url11Sequence) {
		this.url11Sequence = url11Sequence;
	}

	public String getUrl12Description() {
		return url12Description;
	}

	public void setUrl12Description(String url12Description) {
		this.url12Description = url12Description;
	}

	public KeyValue getUrl12KeyValuesAdd() {
		return url12KeyValuesAdd;
	}

	public void setUrl12KeyValuesAdd(KeyValue url12KeyValuesAdd) {
		this.url12KeyValuesAdd = url12KeyValuesAdd;
	}

	public String getUrl12KeyValuesCount() {
		return url12KeyValuesCount;
	}

	public void setUrl12KeyValuesCount(String url12KeyValuesCount) {
		this.url12KeyValuesCount = url12KeyValuesCount;
	}

	public Vector getUrl12KeyValuesDelete() {
		return url12KeyValuesDelete;
	}

	public void setUrl12KeyValuesDelete(Vector url12KeyValuesDelete) {
		this.url12KeyValuesDelete = url12KeyValuesDelete;
	}

	public Vector getUrl12KeyValuesUpdate() {
		return url12KeyValuesUpdate;
	}

	public void setUrl12KeyValuesUpdate(Vector url12KeyValuesUpdate) {
		this.url12KeyValuesUpdate = url12KeyValuesUpdate;
	}

	public String getUrl12LongInformation() {
		return url12LongInformation;
	}

	public void setUrl12LongInformation(String url12LongInformation) {
		this.url12LongInformation = url12LongInformation;
	}

	public String getUrl12Sequence() {
		return url12Sequence;
	}

	public void setUrl12Sequence(String url12Sequence) {
		this.url12Sequence = url12Sequence;
	}

	public String getUrl13Description() {
		return url13Description;
	}

	public void setUrl13Description(String url13Description) {
		this.url13Description = url13Description;
	}

	public KeyValue getUrl13KeyValuesAdd() {
		return url13KeyValuesAdd;
	}

	public void setUrl13KeyValuesAdd(KeyValue url13KeyValuesAdd) {
		this.url13KeyValuesAdd = url13KeyValuesAdd;
	}

	public String getUrl13KeyValuesCount() {
		return url13KeyValuesCount;
	}

	public void setUrl13KeyValuesCount(String url13KeyValuesCount) {
		this.url13KeyValuesCount = url13KeyValuesCount;
	}

	public Vector getUrl13KeyValuesDelete() {
		return url13KeyValuesDelete;
	}

	public void setUrl13KeyValuesDelete(Vector url13KeyValuesDelete) {
		this.url13KeyValuesDelete = url13KeyValuesDelete;
	}

	public Vector getUrl13KeyValuesUpdate() {
		return url13KeyValuesUpdate;
	}

	public void setUrl13KeyValuesUpdate(Vector url13KeyValuesUpdate) {
		this.url13KeyValuesUpdate = url13KeyValuesUpdate;
	}

	public String getUrl13LongInformation() {
		return url13LongInformation;
	}

	public void setUrl13LongInformation(String url13LongInformation) {
		this.url13LongInformation = url13LongInformation;
	}

	public String getUrl13Sequence() {
		return url13Sequence;
	}

	public void setUrl13Sequence(String url13Sequence) {
		this.url13Sequence = url13Sequence;
	}

	public String getUrl14Description() {
		return url14Description;
	}

	public void setUrl14Description(String url14Description) {
		this.url14Description = url14Description;
	}

	public KeyValue getUrl14KeyValuesAdd() {
		return url14KeyValuesAdd;
	}

	public void setUrl14KeyValuesAdd(KeyValue url14KeyValuesAdd) {
		this.url14KeyValuesAdd = url14KeyValuesAdd;
	}

	public String getUrl14KeyValuesCount() {
		return url14KeyValuesCount;
	}

	public void setUrl14KeyValuesCount(String url14KeyValuesCount) {
		this.url14KeyValuesCount = url14KeyValuesCount;
	}

	public Vector getUrl14KeyValuesDelete() {
		return url14KeyValuesDelete;
	}

	public void setUrl14KeyValuesDelete(Vector url14KeyValuesDelete) {
		this.url14KeyValuesDelete = url14KeyValuesDelete;
	}

	public Vector getUrl14KeyValuesUpdate() {
		return url14KeyValuesUpdate;
	}

	public void setUrl14KeyValuesUpdate(Vector url14KeyValuesUpdate) {
		this.url14KeyValuesUpdate = url14KeyValuesUpdate;
	}

	public String getUrl14LongInformation() {
		return url14LongInformation;
	}

	public void setUrl14LongInformation(String url14LongInformation) {
		this.url14LongInformation = url14LongInformation;
	}

	public String getUrl14Sequence() {
		return url14Sequence;
	}

	public void setUrl14Sequence(String url14Sequence) {
		this.url14Sequence = url14Sequence;
	}

	public String getUrl15Description() {
		return url15Description;
	}

	public void setUrl15Description(String url15Description) {
		this.url15Description = url15Description;
	}

	public KeyValue getUrl15KeyValuesAdd() {
		return url15KeyValuesAdd;
	}

	public void setUrl15KeyValuesAdd(KeyValue url15KeyValuesAdd) {
		this.url15KeyValuesAdd = url15KeyValuesAdd;
	}

	public String getUrl15KeyValuesCount() {
		return url15KeyValuesCount;
	}

	public void setUrl15KeyValuesCount(String url15KeyValuesCount) {
		this.url15KeyValuesCount = url15KeyValuesCount;
	}

	public Vector getUrl15KeyValuesDelete() {
		return url15KeyValuesDelete;
	}

	public void setUrl15KeyValuesDelete(Vector url15KeyValuesDelete) {
		this.url15KeyValuesDelete = url15KeyValuesDelete;
	}

	public Vector getUrl15KeyValuesUpdate() {
		return url15KeyValuesUpdate;
	}

	public void setUrl15KeyValuesUpdate(Vector url15KeyValuesUpdate) {
		this.url15KeyValuesUpdate = url15KeyValuesUpdate;
	}

	public String getUrl15LongInformation() {
		return url15LongInformation;
	}

	public void setUrl15LongInformation(String url15LongInformation) {
		this.url15LongInformation = url15LongInformation;
	}

	public String getUrl15Sequence() {
		return url15Sequence;
	}

	public void setUrl15Sequence(String url15Sequence) {
		this.url15Sequence = url15Sequence;
	}

	public String getUrl16Description() {
		return url16Description;
	}

	public void setUrl16Description(String url16Description) {
		this.url16Description = url16Description;
	}

	public KeyValue getUrl16KeyValuesAdd() {
		return url16KeyValuesAdd;
	}

	public void setUrl16KeyValuesAdd(KeyValue url16KeyValuesAdd) {
		this.url16KeyValuesAdd = url16KeyValuesAdd;
	}

	public String getUrl16KeyValuesCount() {
		return url16KeyValuesCount;
	}

	public void setUrl16KeyValuesCount(String url16KeyValuesCount) {
		this.url16KeyValuesCount = url16KeyValuesCount;
	}

	public Vector getUrl16KeyValuesDelete() {
		return url16KeyValuesDelete;
	}

	public void setUrl16KeyValuesDelete(Vector url16KeyValuesDelete) {
		this.url16KeyValuesDelete = url16KeyValuesDelete;
	}

	public Vector getUrl16KeyValuesUpdate() {
		return url16KeyValuesUpdate;
	}

	public void setUrl16KeyValuesUpdate(Vector url16KeyValuesUpdate) {
		this.url16KeyValuesUpdate = url16KeyValuesUpdate;
	}

	public String getUrl16LongInformation() {
		return url16LongInformation;
	}

	public void setUrl16LongInformation(String url16LongInformation) {
		this.url16LongInformation = url16LongInformation;
	}

	public String getUrl16Sequence() {
		return url16Sequence;
	}

	public void setUrl16Sequence(String url16Sequence) {
		this.url16Sequence = url16Sequence;
	}

	public String getUrl17Description() {
		return url17Description;
	}

	public void setUrl17Description(String url17Description) {
		this.url17Description = url17Description;
	}

	public KeyValue getUrl17KeyValuesAdd() {
		return url17KeyValuesAdd;
	}

	public void setUrl17KeyValuesAdd(KeyValue url17KeyValuesAdd) {
		this.url17KeyValuesAdd = url17KeyValuesAdd;
	}

	public String getUrl17KeyValuesCount() {
		return url17KeyValuesCount;
	}

	public void setUrl17KeyValuesCount(String url17KeyValuesCount) {
		this.url17KeyValuesCount = url17KeyValuesCount;
	}

	public Vector getUrl17KeyValuesDelete() {
		return url17KeyValuesDelete;
	}

	public void setUrl17KeyValuesDelete(Vector url17KeyValuesDelete) {
		this.url17KeyValuesDelete = url17KeyValuesDelete;
	}

	public Vector getUrl17KeyValuesUpdate() {
		return url17KeyValuesUpdate;
	}

	public void setUrl17KeyValuesUpdate(Vector url17KeyValuesUpdate) {
		this.url17KeyValuesUpdate = url17KeyValuesUpdate;
	}

	public String getUrl17LongInformation() {
		return url17LongInformation;
	}

	public void setUrl17LongInformation(String url17LongInformation) {
		this.url17LongInformation = url17LongInformation;
	}

	public String getUrl17Sequence() {
		return url17Sequence;
	}

	public void setUrl17Sequence(String url17Sequence) {
		this.url17Sequence = url17Sequence;
	}

	public String getUrl18Description() {
		return url18Description;
	}

	public void setUrl18Description(String url18Description) {
		this.url18Description = url18Description;
	}

	public KeyValue getUrl18KeyValuesAdd() {
		return url18KeyValuesAdd;
	}

	public void setUrl18KeyValuesAdd(KeyValue url18KeyValuesAdd) {
		this.url18KeyValuesAdd = url18KeyValuesAdd;
	}

	public String getUrl18KeyValuesCount() {
		return url18KeyValuesCount;
	}

	public void setUrl18KeyValuesCount(String url18KeyValuesCount) {
		this.url18KeyValuesCount = url18KeyValuesCount;
	}

	public Vector getUrl18KeyValuesDelete() {
		return url18KeyValuesDelete;
	}

	public void setUrl18KeyValuesDelete(Vector url18KeyValuesDelete) {
		this.url18KeyValuesDelete = url18KeyValuesDelete;
	}

	public Vector getUrl18KeyValuesUpdate() {
		return url18KeyValuesUpdate;
	}

	public void setUrl18KeyValuesUpdate(Vector url18KeyValuesUpdate) {
		this.url18KeyValuesUpdate = url18KeyValuesUpdate;
	}

	public String getUrl18LongInformation() {
		return url18LongInformation;
	}

	public void setUrl18LongInformation(String url18LongInformation) {
		this.url18LongInformation = url18LongInformation;
	}

	public String getUrl18Sequence() {
		return url18Sequence;
	}

	public void setUrl18Sequence(String url18Sequence) {
		this.url18Sequence = url18Sequence;
	}

	public String getUrl19Description() {
		return url19Description;
	}

	public void setUrl19Description(String url19Description) {
		this.url19Description = url19Description;
	}

	public KeyValue getUrl19KeyValuesAdd() {
		return url19KeyValuesAdd;
	}

	public void setUrl19KeyValuesAdd(KeyValue url19KeyValuesAdd) {
		this.url19KeyValuesAdd = url19KeyValuesAdd;
	}

	public String getUrl19KeyValuesCount() {
		return url19KeyValuesCount;
	}

	public void setUrl19KeyValuesCount(String url19KeyValuesCount) {
		this.url19KeyValuesCount = url19KeyValuesCount;
	}

	public Vector getUrl19KeyValuesDelete() {
		return url19KeyValuesDelete;
	}

	public void setUrl19KeyValuesDelete(Vector url19KeyValuesDelete) {
		this.url19KeyValuesDelete = url19KeyValuesDelete;
	}

	public Vector getUrl19KeyValuesUpdate() {
		return url19KeyValuesUpdate;
	}

	public void setUrl19KeyValuesUpdate(Vector url19KeyValuesUpdate) {
		this.url19KeyValuesUpdate = url19KeyValuesUpdate;
	}

	public String getUrl19LongInformation() {
		return url19LongInformation;
	}

	public void setUrl19LongInformation(String url19LongInformation) {
		this.url19LongInformation = url19LongInformation;
	}

	public String getUrl19Sequence() {
		return url19Sequence;
	}

	public void setUrl19Sequence(String url19Sequence) {
		this.url19Sequence = url19Sequence;
	}

	public String getUrl1Description() {
		return url1Description;
	}

	public void setUrl1Description(String url1Description) {
		this.url1Description = url1Description;
	}

	public KeyValue getUrl1KeyValuesAdd() {
		return url1KeyValuesAdd;
	}

	public void setUrl1KeyValuesAdd(KeyValue url1KeyValuesAdd) {
		this.url1KeyValuesAdd = url1KeyValuesAdd;
	}

	public String getUrl1KeyValuesCount() {
		return url1KeyValuesCount;
	}

	public void setUrl1KeyValuesCount(String url1KeyValuesCount) {
		this.url1KeyValuesCount = url1KeyValuesCount;
	}

	public Vector getUrl1KeyValuesDelete() {
		return url1KeyValuesDelete;
	}

	public void setUrl1KeyValuesDelete(Vector url1KeyValuesDelete) {
		this.url1KeyValuesDelete = url1KeyValuesDelete;
	}

	public Vector getUrl1KeyValuesUpdate() {
		return url1KeyValuesUpdate;
	}

	public void setUrl1KeyValuesUpdate(Vector url1KeyValuesUpdate) {
		this.url1KeyValuesUpdate = url1KeyValuesUpdate;
	}

	public String getUrl1LongInformation() {
		return url1LongInformation;
	}

	public void setUrl1LongInformation(String url1LongInformation) {
		this.url1LongInformation = url1LongInformation;
	}

	public String getUrl1Sequence() {
		return url1Sequence;
	}

	public void setUrl1Sequence(String url1Sequence) {
		this.url1Sequence = url1Sequence;
	}

	public String getUrl2Description() {
		return url2Description;
	}

	public void setUrl2Description(String url2Description) {
		this.url2Description = url2Description;
	}

	public KeyValue getUrl2KeyValuesAdd() {
		return url2KeyValuesAdd;
	}

	public void setUrl2KeyValuesAdd(KeyValue url2KeyValuesAdd) {
		this.url2KeyValuesAdd = url2KeyValuesAdd;
	}

	public String getUrl2KeyValuesCount() {
		return url2KeyValuesCount;
	}

	public void setUrl2KeyValuesCount(String url2KeyValuesCount) {
		this.url2KeyValuesCount = url2KeyValuesCount;
	}

	public Vector getUrl2KeyValuesDelete() {
		return url2KeyValuesDelete;
	}

	public void setUrl2KeyValuesDelete(Vector url2KeyValuesDelete) {
		this.url2KeyValuesDelete = url2KeyValuesDelete;
	}

	public Vector getUrl2KeyValuesUpdate() {
		return url2KeyValuesUpdate;
	}

	public void setUrl2KeyValuesUpdate(Vector url2KeyValuesUpdate) {
		this.url2KeyValuesUpdate = url2KeyValuesUpdate;
	}

	public String getUrl2LongInformation() {
		return url2LongInformation;
	}

	public void setUrl2LongInformation(String url2LongInformation) {
		this.url2LongInformation = url2LongInformation;
	}

	public String getUrl2Sequence() {
		return url2Sequence;
	}

	public void setUrl2Sequence(String url2Sequence) {
		this.url2Sequence = url2Sequence;
	}

	public String getUrl3Description() {
		return url3Description;
	}

	public void setUrl3Description(String url3Description) {
		this.url3Description = url3Description;
	}

	public KeyValue getUrl3KeyValuesAdd() {
		return url3KeyValuesAdd;
	}

	public void setUrl3KeyValuesAdd(KeyValue url3KeyValuesAdd) {
		this.url3KeyValuesAdd = url3KeyValuesAdd;
	}

	public String getUrl3KeyValuesCount() {
		return url3KeyValuesCount;
	}

	public void setUrl3KeyValuesCount(String url3KeyValuesCount) {
		this.url3KeyValuesCount = url3KeyValuesCount;
	}

	public Vector getUrl3KeyValuesDelete() {
		return url3KeyValuesDelete;
	}

	public void setUrl3KeyValuesDelete(Vector url3KeyValuesDelete) {
		this.url3KeyValuesDelete = url3KeyValuesDelete;
	}

	public Vector getUrl3KeyValuesUpdate() {
		return url3KeyValuesUpdate;
	}

	public void setUrl3KeyValuesUpdate(Vector url3KeyValuesUpdate) {
		this.url3KeyValuesUpdate = url3KeyValuesUpdate;
	}

	public String getUrl3LongInformation() {
		return url3LongInformation;
	}

	public void setUrl3LongInformation(String url3LongInformation) {
		this.url3LongInformation = url3LongInformation;
	}

	public String getUrl3Sequence() {
		return url3Sequence;
	}

	public void setUrl3Sequence(String url3Sequence) {
		this.url3Sequence = url3Sequence;
	}

	public String getUrl4Description() {
		return url4Description;
	}

	public void setUrl4Description(String url4Description) {
		this.url4Description = url4Description;
	}

	public KeyValue getUrl4KeyValuesAdd() {
		return url4KeyValuesAdd;
	}

	public void setUrl4KeyValuesAdd(KeyValue url4KeyValuesAdd) {
		this.url4KeyValuesAdd = url4KeyValuesAdd;
	}

	public String getUrl4KeyValuesCount() {
		return url4KeyValuesCount;
	}

	public void setUrl4KeyValuesCount(String url4KeyValuesCount) {
		this.url4KeyValuesCount = url4KeyValuesCount;
	}

	public Vector getUrl4KeyValuesDelete() {
		return url4KeyValuesDelete;
	}

	public void setUrl4KeyValuesDelete(Vector url4KeyValuesDelete) {
		this.url4KeyValuesDelete = url4KeyValuesDelete;
	}

	public Vector getUrl4KeyValuesUpdate() {
		return url4KeyValuesUpdate;
	}

	public void setUrl4KeyValuesUpdate(Vector url4KeyValuesUpdate) {
		this.url4KeyValuesUpdate = url4KeyValuesUpdate;
	}

	public String getUrl4LongInformation() {
		return url4LongInformation;
	}

	public void setUrl4LongInformation(String url4LongInformation) {
		this.url4LongInformation = url4LongInformation;
	}

	public String getUrl4Sequence() {
		return url4Sequence;
	}

	public void setUrl4Sequence(String url4Sequence) {
		this.url4Sequence = url4Sequence;
	}

	public String getUrl5Description() {
		return url5Description;
	}

	public void setUrl5Description(String url5Description) {
		this.url5Description = url5Description;
	}

	public KeyValue getUrl5KeyValuesAdd() {
		return url5KeyValuesAdd;
	}

	public void setUrl5KeyValuesAdd(KeyValue url5KeyValuesAdd) {
		this.url5KeyValuesAdd = url5KeyValuesAdd;
	}

	public String getUrl5KeyValuesCount() {
		return url5KeyValuesCount;
	}

	public void setUrl5KeyValuesCount(String url5KeyValuesCount) {
		this.url5KeyValuesCount = url5KeyValuesCount;
	}

	public Vector getUrl5KeyValuesDelete() {
		return url5KeyValuesDelete;
	}

	public void setUrl5KeyValuesDelete(Vector url5KeyValuesDelete) {
		this.url5KeyValuesDelete = url5KeyValuesDelete;
	}

	public Vector getUrl5KeyValuesUpdate() {
		return url5KeyValuesUpdate;
	}

	public void setUrl5KeyValuesUpdate(Vector url5KeyValuesUpdate) {
		this.url5KeyValuesUpdate = url5KeyValuesUpdate;
	}

	public String getUrl5LongInformation() {
		return url5LongInformation;
	}

	public void setUrl5LongInformation(String url5LongInformation) {
		this.url5LongInformation = url5LongInformation;
	}

	public String getUrl5Sequence() {
		return url5Sequence;
	}

	public void setUrl5Sequence(String url5Sequence) {
		this.url5Sequence = url5Sequence;
	}

	public String getUrl6Description() {
		return url6Description;
	}

	public void setUrl6Description(String url6Description) {
		this.url6Description = url6Description;
	}

	public KeyValue getUrl6KeyValuesAdd() {
		return url6KeyValuesAdd;
	}

	public void setUrl6KeyValuesAdd(KeyValue url6KeyValuesAdd) {
		this.url6KeyValuesAdd = url6KeyValuesAdd;
	}

	public String getUrl6KeyValuesCount() {
		return url6KeyValuesCount;
	}

	public void setUrl6KeyValuesCount(String url6KeyValuesCount) {
		this.url6KeyValuesCount = url6KeyValuesCount;
	}

	public Vector getUrl6KeyValuesDelete() {
		return url6KeyValuesDelete;
	}

	public void setUrl6KeyValuesDelete(Vector url6KeyValuesDelete) {
		this.url6KeyValuesDelete = url6KeyValuesDelete;
	}

	public Vector getUrl6KeyValuesUpdate() {
		return url6KeyValuesUpdate;
	}

	public void setUrl6KeyValuesUpdate(Vector url6KeyValuesUpdate) {
		this.url6KeyValuesUpdate = url6KeyValuesUpdate;
	}

	public String getUrl6LongInformation() {
		return url6LongInformation;
	}

	public void setUrl6LongInformation(String url6LongInformation) {
		this.url6LongInformation = url6LongInformation;
	}

	public String getUrl6Sequence() {
		return url6Sequence;
	}

	public void setUrl6Sequence(String url6Sequence) {
		this.url6Sequence = url6Sequence;
	}

	public String getUrl7Description() {
		return url7Description;
	}

	public void setUrl7Description(String url7Description) {
		this.url7Description = url7Description;
	}

	public KeyValue getUrl7KeyValuesAdd() {
		return url7KeyValuesAdd;
	}

	public void setUrl7KeyValuesAdd(KeyValue url7KeyValuesAdd) {
		this.url7KeyValuesAdd = url7KeyValuesAdd;
	}

	public String getUrl7KeyValuesCount() {
		return url7KeyValuesCount;
	}

	public void setUrl7KeyValuesCount(String url7KeyValuesCount) {
		this.url7KeyValuesCount = url7KeyValuesCount;
	}

	public Vector getUrl7KeyValuesDelete() {
		return url7KeyValuesDelete;
	}

	public void setUrl7KeyValuesDelete(Vector url7KeyValuesDelete) {
		this.url7KeyValuesDelete = url7KeyValuesDelete;
	}

	public Vector getUrl7KeyValuesUpdate() {
		return url7KeyValuesUpdate;
	}

	public void setUrl7KeyValuesUpdate(Vector url7KeyValuesUpdate) {
		this.url7KeyValuesUpdate = url7KeyValuesUpdate;
	}

	public String getUrl7LongInformation() {
		return url7LongInformation;
	}

	public void setUrl7LongInformation(String url7LongInformation) {
		this.url7LongInformation = url7LongInformation;
	}

	public String getUrl7Sequence() {
		return url7Sequence;
	}

	public void setUrl7Sequence(String url7Sequence) {
		this.url7Sequence = url7Sequence;
	}

	public String getUrl8Description() {
		return url8Description;
	}

	public void setUrl8Description(String url8Description) {
		this.url8Description = url8Description;
	}

	public KeyValue getUrl8KeyValuesAdd() {
		return url8KeyValuesAdd;
	}

	public void setUrl8KeyValuesAdd(KeyValue url8KeyValuesAdd) {
		this.url8KeyValuesAdd = url8KeyValuesAdd;
	}

	public String getUrl8KeyValuesCount() {
		return url8KeyValuesCount;
	}

	public void setUrl8KeyValuesCount(String url8KeyValuesCount) {
		this.url8KeyValuesCount = url8KeyValuesCount;
	}

	public Vector getUrl8KeyValuesDelete() {
		return url8KeyValuesDelete;
	}

	public void setUrl8KeyValuesDelete(Vector url8KeyValuesDelete) {
		this.url8KeyValuesDelete = url8KeyValuesDelete;
	}

	public Vector getUrl8KeyValuesUpdate() {
		return url8KeyValuesUpdate;
	}

	public void setUrl8KeyValuesUpdate(Vector url8KeyValuesUpdate) {
		this.url8KeyValuesUpdate = url8KeyValuesUpdate;
	}

	public String getUrl8LongInformation() {
		return url8LongInformation;
	}

	public void setUrl8LongInformation(String url8LongInformation) {
		this.url8LongInformation = url8LongInformation;
	}

	public String getUrl8Sequence() {
		return url8Sequence;
	}

	public void setUrl8Sequence(String url8Sequence) {
		this.url8Sequence = url8Sequence;
	}

	public String getUrl9Description() {
		return url9Description;
	}

	public void setUrl9Description(String url9Description) {
		this.url9Description = url9Description;
	}

	public KeyValue getUrl9KeyValuesAdd() {
		return url9KeyValuesAdd;
	}

	public void setUrl9KeyValuesAdd(KeyValue url9KeyValuesAdd) {
		this.url9KeyValuesAdd = url9KeyValuesAdd;
	}

	public String getUrl9KeyValuesCount() {
		return url9KeyValuesCount;
	}

	public void setUrl9KeyValuesCount(String url9KeyValuesCount) {
		this.url9KeyValuesCount = url9KeyValuesCount;
	}

	public Vector getUrl9KeyValuesDelete() {
		return url9KeyValuesDelete;
	}

	public void setUrl9KeyValuesDelete(Vector url9KeyValuesDelete) {
		this.url9KeyValuesDelete = url9KeyValuesDelete;
	}

	public Vector getUrl9KeyValuesUpdate() {
		return url9KeyValuesUpdate;
	}

	public void setUrl9KeyValuesUpdate(Vector url9KeyValuesUpdate) {
		this.url9KeyValuesUpdate = url9KeyValuesUpdate;
	}

	public String getUrl9LongInformation() {
		return url9LongInformation;
	}

	public void setUrl9LongInformation(String url9LongInformation) {
		this.url9LongInformation = url9LongInformation;
	}

	public String getUrl9Sequence() {
		return url9Sequence;
	}

	public void setUrl9Sequence(String url9Sequence) {
		this.url9Sequence = url9Sequence;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL1(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null ||
				submit != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!item.equals(""))
			{
				eType = "ItemUrl1";
				key1 = item;
			}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl1";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				// Only allow 1 record per spec/revision date and time
				eType = "SpecRevisionUrl1";
				key1 = specNumber;
				key2 = revisionDate;
				key3 = revisionTime;
				if (request.getParameter("nutritionPanelURLRemove") == null ||
					request.getParameter("nutritionPanelURLRemove").trim().equals(""))
				{
					// If it is NOT removed -- then it must not already have a record in the file
					 // Test for the ONE Record:
					  Vector testRecord = new Vector();
					  try{
						  KeyValue kv = new KeyValue();
						  kv.setEnvironment(this.getEnvironment().trim());
						  kv.setKey1(key1);
						  kv.setKey2(key2);
						  kv.setKey3(key3);
						  kv.setEntryType(eType);
						  testRecord = ServiceKeyValue.buildKeyValueList(kv);
					  }catch(Exception e)
					  {}
					  if (testRecord != null &&
						  testRecord.size() == 0)
					  {
						  String nutritionPanel = request.getParameter("nutritionPanelURL");
						  if (nutritionPanel == null)
							  nutritionPanel = "";
						  this.setUrl1LongInformation(nutritionPanel);
						  this.setUrl11Description("Nutrition Panel");
					  }
				}
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url1LongInformation.equals("")) {
				url1KeyValuesAdd = new KeyValue();
				url1KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url1KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url1KeyValuesAdd.setEntryType(eType);
				url1KeyValuesAdd.setSequence(url1Sequence.trim());
				url1KeyValuesAdd.setKey1(key1);
				url1KeyValuesAdd.setKey2(key2);
				url1KeyValuesAdd.setKey3(key3);
				url1KeyValuesAdd.setKey4(key4);
				url1KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url1KeyValuesAdd.setValue(this.url1LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url1KeyValuesAdd.setDescription(this.url1Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url1KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url1KeyValuesAdd.getLastUpdateUser() == null)
					url1KeyValuesAdd.setLastUpdateUser("TreeNet");
				url1KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url1KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url1KeyValuesDelete = new Vector();
				url1KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url1Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url1Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url1UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url1LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url1LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url1LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url1Description" + x));
					newElement.setDeleteDate(request.getParameter("url1DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url1DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url1DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url1Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url1KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url1KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL10(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl10";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl10";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url10LongInformation.equals("")) {
				url10KeyValuesAdd = new KeyValue();
				url10KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url10KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url10KeyValuesAdd.setEntryType(eType);
				url10KeyValuesAdd.setSequence(url10Sequence.trim());
				url10KeyValuesAdd.setKey1(key1);
				url10KeyValuesAdd.setKey2(key2);
				url10KeyValuesAdd.setKey3(key3);
				url10KeyValuesAdd.setKey4(key4);
				url10KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url10KeyValuesAdd.setValue(this.url10LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url10KeyValuesAdd.setDescription(this.url10Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url10KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url10KeyValuesAdd.getLastUpdateUser() == null)
					url10KeyValuesAdd.setLastUpdateUser("TreeNet");
				url10KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url10KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url10KeyValuesDelete = new Vector();
				url10KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url10Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url10Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url10UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url10LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url10LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url10LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url10Description" + x));
					newElement.setDeleteDate(request.getParameter("url10DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url10DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url10DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url10Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url10KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url10KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL2(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null ||
				submit != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl2";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{ 
			  // only allow ONE SPECIFIC Link	
			  // Section will be used specificially for palletPattern.  which will only have ONE Record
			  eType = "SpecRevisionUrl2";
			  key1 = specNumber;
			  key2 = revisionDate;
			  key3 = revisionTime;
			  if (request.getParameter("palletPatternURLRemove") == null ||
				  request.getParameter("palletPatternURLRemove").trim().equals(""))
			  {
				 // If it is NOT removed -- then it must not already have a record in the file
				 // Test for the ONE Record:
				  Vector testRecord = new Vector();
				  try{
					  KeyValue kv = new KeyValue();
					  kv.setEnvironment(this.getEnvironment().trim());
					  kv.setKey1(key1);
					  kv.setKey2(key2);
					  kv.setKey3(key3);
					  kv.setEntryType(eType);
					  testRecord = ServiceKeyValue.buildKeyValueList(kv);
				  }catch(Exception e)
				  {}
				  if (testRecord != null &&
					  testRecord.size() == 0)
				  {
			          String palletPattern = request.getParameter("palletPatternURL");
			          if (palletPattern == null)
				         palletPattern = "";
			          this.setUrl2LongInformation(palletPattern);
			          this.setUrl2Description("Pallet Pattern");
				  }
			  }
			}
			if (!item.equals(""))
			{ // only allow SPECIFIC ONE link
			  // this section will be used specifically for Pallet Pattern -- which will only have ONE Record	
				eType = "ItemUrl2";
				key1 = item;
				if (request.getParameter("supplierSummaryURLRemove") == null ||
					request.getParameter("supplierSummaryURLRemove").trim().equals(""))
				{
					// If it is NOT removed -- then it must not already have a record in the file
					// Test for the ONE Record:
				  Vector testRecord = new Vector();
				  try{
					  KeyValue kv = new KeyValue();
					  kv.setEnvironment(this.getEnvironment().trim());
					  kv.setKey1(key1);
					  kv.setEntryType(eType);
					  testRecord = ServiceKeyValue.buildKeyValueList(kv);
				  }catch(Exception e)
				  {}
				  if (testRecord != null &&
					  testRecord.size() == 0)
				  {
					  String supplierSummaryURL = request.getParameter("supplierSummaryURL");
					  if (supplierSummaryURL == null)
						  supplierSummaryURL = "";
					  this.setUrl2LongInformation(supplierSummaryURL);
					  this.setUrl2Description("Supplier Summary");
				  }
				}
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url2LongInformation.equals("")) {
				url2KeyValuesAdd = new KeyValue();
				url2KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url2KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url2KeyValuesAdd.setEntryType(eType);
				url2KeyValuesAdd.setSequence(url2Sequence.trim());
				url2KeyValuesAdd.setKey1(key1);
				url2KeyValuesAdd.setKey2(key2);
				url2KeyValuesAdd.setKey3(key3);
				url2KeyValuesAdd.setKey4(key4);
				url2KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url2KeyValuesAdd.setValue(this.url2LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url2KeyValuesAdd.setDescription(this.url2Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url2KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url2KeyValuesAdd.getLastUpdateUser() == null)
					url2KeyValuesAdd.setLastUpdateUser("TreeNet");
				url2KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url2KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url2KeyValuesDelete = new Vector();
				url2KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url2Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url2Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url2UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url2LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url2LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url2LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url2Description" + x));
					newElement.setDeleteDate(request.getParameter("url2DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url2DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url2DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url2Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url2KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url2KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL3(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null ||
				submit != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl3";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
				{ 
				  // only allow ONE SPECIFIC Link	
				  // Section will be used specificially for Label Example.  which will only have ONE Record
				  eType = "SpecRevisionUrl3";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
				  if (request.getParameter("exampleLabelURLRemove") == null ||
					  request.getParameter("exampleLabelURLRemove").trim().equals(""))
				  {
					 // If it is NOT removed -- then it must not already have a record in the file
					 // Test for the ONE Record:
					  Vector testRecord = new Vector();
					  try{
						  KeyValue kv = new KeyValue();
						  kv.setEnvironment(this.getEnvironment().trim());
						  kv.setKey1(key1);
						  kv.setKey2(key2);
						  kv.setKey3(key3);
						  kv.setEntryType(eType);
						  testRecord = ServiceKeyValue.buildKeyValueList(kv);
					  }catch(Exception e)
					  {}
					  if (testRecord != null &&
						  testRecord.size() == 0)
					  {
				          String exampleLabel = request.getParameter("exampleLabelURL");
				          if (exampleLabel == null)
					         exampleLabel = "";
				          this.setUrl3LongInformation(exampleLabel);
				          this.setUrl3Description("Example Label");
					  }
				  }
				}
			//********************************************
			// ADD A NEW RECORD
			if (!url3LongInformation.equals("")) {
				url3KeyValuesAdd = new KeyValue();
				url3KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url3KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url3KeyValuesAdd.setEntryType(eType);
				url3KeyValuesAdd.setSequence(url3Sequence.trim());
				url3KeyValuesAdd.setKey1(key1);
				url3KeyValuesAdd.setKey2(key2);
				url3KeyValuesAdd.setKey3(key3);
				url3KeyValuesAdd.setKey4(key4);
				url3KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url3KeyValuesAdd.setValue(this.url3LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url3KeyValuesAdd.setDescription(this.url3Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url3KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url3KeyValuesAdd.getLastUpdateUser() == null)
					url3KeyValuesAdd.setLastUpdateUser("TreeNet");
				url3KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url3KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url3KeyValuesDelete = new Vector();
				url3KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url3Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url3Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url3UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url3LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url3LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url3LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url3Description" + x));
					newElement.setDeleteDate(request.getParameter("url3DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url3DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url3DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url3Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url3KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url3KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL4(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null ||
				submit != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl4";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl14";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url4LongInformation.equals("")) {
				url4KeyValuesAdd = new KeyValue();
				url4KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url4KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url4KeyValuesAdd.setEntryType(eType);
				url4KeyValuesAdd.setSequence(url4Sequence.trim());
				url4KeyValuesAdd.setKey1(key1);
				url4KeyValuesAdd.setKey2(key2);
				url4KeyValuesAdd.setKey3(key3);
				url4KeyValuesAdd.setKey4(key4);
				url4KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url4KeyValuesAdd.setValue(this.url4LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url4KeyValuesAdd.setDescription(this.url4Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url4KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url4KeyValuesAdd.getLastUpdateUser() == null)
					url4KeyValuesAdd.setLastUpdateUser("TreeNet");
				url4KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url4KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url4KeyValuesDelete = new Vector();
				url4KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url4Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url4Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url4UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url4LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url4LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url4LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url4Description" + x));
					newElement.setDeleteDate(request.getParameter("url4DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url4DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url4DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url4Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url4KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url4KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL5(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null ||
				submit != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl5";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl5";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url5LongInformation.equals("")) {
				url5KeyValuesAdd = new KeyValue();
				url5KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url5KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url5KeyValuesAdd.setEntryType(eType);
				url5KeyValuesAdd.setSequence(url5Sequence.trim());
				url5KeyValuesAdd.setKey1(key1);
				url5KeyValuesAdd.setKey2(key2);
				url5KeyValuesAdd.setKey3(key3);
				url5KeyValuesAdd.setKey4(key4);
				url5KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url5KeyValuesAdd.setValue(this.url5LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url5KeyValuesAdd.setDescription(this.url5Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url5KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url5KeyValuesAdd.getLastUpdateUser() == null)
					url5KeyValuesAdd.setLastUpdateUser("TreeNet");
				url5KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url5KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url5KeyValuesDelete = new Vector();
				url5KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url5Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url5Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url5UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url5LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url5LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url5LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url5Description" + x));
					newElement.setDeleteDate(request.getParameter("url5DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url5DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url5DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url5Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url5KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url5KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL6(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl6";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl6";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url6LongInformation.equals("")) {
				url6KeyValuesAdd = new KeyValue();
				url6KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url6KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url6KeyValuesAdd.setEntryType(eType);
				url6KeyValuesAdd.setSequence(url6Sequence.trim());
				url6KeyValuesAdd.setKey1(key1);
				url6KeyValuesAdd.setKey2(key2);
				url6KeyValuesAdd.setKey3(key3);
				url6KeyValuesAdd.setKey4(key4);
				url6KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url6KeyValuesAdd.setValue(this.url6LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url6KeyValuesAdd.setDescription(this.url6Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url6KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url6KeyValuesAdd.getLastUpdateUser() == null)
					url6KeyValuesAdd.setLastUpdateUser("TreeNet");
				url6KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url6KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url6KeyValuesDelete = new Vector();
				url6KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url6Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url6Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url6UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url6LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url6LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url6LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url6Description" + x));
					newElement.setDeleteDate(request.getParameter("url6DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url6DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url6DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url6Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url6KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url6KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL7(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl7";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl7";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url7LongInformation.equals("")) {
				url7KeyValuesAdd = new KeyValue();
				url7KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url7KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url7KeyValuesAdd.setEntryType(eType);
				url7KeyValuesAdd.setSequence(url7Sequence.trim());
				url7KeyValuesAdd.setKey1(key1);
				url7KeyValuesAdd.setKey2(key2);
				url7KeyValuesAdd.setKey3(key3);
				url7KeyValuesAdd.setKey4(key4);
				url7KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url7KeyValuesAdd.setValue(this.url7LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url7KeyValuesAdd.setDescription(this.url7Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url7KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url7KeyValuesAdd.getLastUpdateUser() == null)
					url7KeyValuesAdd.setLastUpdateUser("TreeNet");
				url7KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url7KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url7KeyValuesDelete = new Vector();
				url7KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url7Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url7Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url7UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url7LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url7LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url7LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url7Description" + x));
					newElement.setDeleteDate(request.getParameter("url7DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url7DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url7DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url7Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url7KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url7KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL8(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl8";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl8";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url8LongInformation.equals("")) {
				url8KeyValuesAdd = new KeyValue();
				url8KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url8KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url8KeyValuesAdd.setEntryType(eType);
				url8KeyValuesAdd.setSequence(url8Sequence.trim());
				url8KeyValuesAdd.setKey1(key1);
				url8KeyValuesAdd.setKey2(key2);
				url8KeyValuesAdd.setKey3(key3);
				url8KeyValuesAdd.setKey4(key4);
				url8KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url8KeyValuesAdd.setValue(this.url8LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url8KeyValuesAdd.setDescription(this.url8Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url8KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url8KeyValuesAdd.getLastUpdateUser() == null)
					url8KeyValuesAdd.setLastUpdateUser("TreeNet");
				url8KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url8KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url8KeyValuesDelete = new Vector();
				url8KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url8Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url8Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url8UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url8LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url8LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url8LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url8Description" + x));
					newElement.setDeleteDate(request.getParameter("url8DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url8DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url8DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url8Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url8KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url8KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL9(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl9";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl9";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url9LongInformation.equals("")) {
				url9KeyValuesAdd = new KeyValue();
				url9KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url9KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url9KeyValuesAdd.setEntryType(eType);
				url9KeyValuesAdd.setSequence(url9Sequence.trim());
				url9KeyValuesAdd.setKey1(key1);
				url9KeyValuesAdd.setKey2(key2);
				url9KeyValuesAdd.setKey3(key3);
				url9KeyValuesAdd.setKey4(key4);
				url9KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url9KeyValuesAdd.setValue(this.url9LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url9KeyValuesAdd.setDescription(this.url9Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url9KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url9KeyValuesAdd.getLastUpdateUser() == null)
					url9KeyValuesAdd.setLastUpdateUser("TreeNet");
				url9KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url9KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url9KeyValuesDelete = new Vector();
				url9KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url9Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url9Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url9UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url9LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url9LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url9LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url9Description" + x));
					newElement.setDeleteDate(request.getParameter("url9DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url9DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url9DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url9Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url9KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url9KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL11(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl11";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl11";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url11LongInformation.equals("")) {
				url11KeyValuesAdd = new KeyValue();
				url11KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url11KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url11KeyValuesAdd.setEntryType(eType);
				url11KeyValuesAdd.setSequence(url11Sequence.trim());
				url11KeyValuesAdd.setKey1(key1);
				url11KeyValuesAdd.setKey2(key2);
				url11KeyValuesAdd.setKey3(key3);
				url11KeyValuesAdd.setKey4(key4);
				url11KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url11KeyValuesAdd.setValue(this.url11LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url11KeyValuesAdd.setDescription(this.url11Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url11KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url11KeyValuesAdd.getLastUpdateUser() == null)
					url11KeyValuesAdd.setLastUpdateUser("TreeNet");
				url11KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url11KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url11KeyValuesDelete = new Vector();
				url11KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url11Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url11Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url11UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url11LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url11LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url11LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url11Description" + x));
					newElement.setDeleteDate(request.getParameter("url11DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url11DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url11DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url11Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url11KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url11KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL12(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl12";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl12";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url12LongInformation.equals("")) {
				url12KeyValuesAdd = new KeyValue();
				url12KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url12KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url12KeyValuesAdd.setEntryType(eType);
				url12KeyValuesAdd.setSequence(url12Sequence.trim());
				url12KeyValuesAdd.setKey1(key1);
				url12KeyValuesAdd.setKey2(key2);
				url12KeyValuesAdd.setKey3(key3);
				url12KeyValuesAdd.setKey4(key4);
				url12KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url12KeyValuesAdd.setValue(this.url12LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url12KeyValuesAdd.setDescription(this.url12Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url12KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url12KeyValuesAdd.getLastUpdateUser() == null)
					url12KeyValuesAdd.setLastUpdateUser("TreeNet");
				url12KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url12KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url12KeyValuesDelete = new Vector();
				url12KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url12Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url12Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url12UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url12LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url12LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url12LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url12Description" + x));
					newElement.setDeleteDate(request.getParameter("url12DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url12DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url12DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url12Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url12KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url12KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL13(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl13";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl13";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url13LongInformation.equals("")) {
				url13KeyValuesAdd = new KeyValue();
				url13KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url13KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url13KeyValuesAdd.setEntryType(eType);
				url13KeyValuesAdd.setSequence(url13Sequence.trim());
				url13KeyValuesAdd.setKey1(key1);
				url13KeyValuesAdd.setKey2(key2);
				url13KeyValuesAdd.setKey3(key3);
				url13KeyValuesAdd.setKey4(key4);
				url13KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url13KeyValuesAdd.setValue(this.url13LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url13KeyValuesAdd.setDescription(this.url13Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url13KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url13KeyValuesAdd.getLastUpdateUser() == null)
					url13KeyValuesAdd.setLastUpdateUser("TreeNet");
				url13KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url13KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url13KeyValuesDelete = new Vector();
				url13KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url13Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url13Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url13UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url13LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url13LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url13LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url13Description" + x));
					newElement.setDeleteDate(request.getParameter("url13DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url13DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url13DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url13Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url13KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url13KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL14(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl14";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl14";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url14LongInformation.equals("")) {
				url14KeyValuesAdd = new KeyValue();
				url14KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url14KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url14KeyValuesAdd.setEntryType(eType);
				url14KeyValuesAdd.setSequence(url14Sequence.trim());
				url14KeyValuesAdd.setKey1(key1);
				url14KeyValuesAdd.setKey2(key2);
				url14KeyValuesAdd.setKey3(key3);
				url14KeyValuesAdd.setKey4(key4);
				url14KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url14KeyValuesAdd.setValue(this.url14LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url14KeyValuesAdd.setDescription(this.url14Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url14KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url14KeyValuesAdd.getLastUpdateUser() == null)
					url14KeyValuesAdd.setLastUpdateUser("TreeNet");
				url14KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url14KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url14KeyValuesDelete = new Vector();
				url14KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url14Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url14Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url14UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url14LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url14LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url14LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url14Description" + x));
					newElement.setDeleteDate(request.getParameter("url14DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url14DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url14DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url14Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url14KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url14KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL15(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl15";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl15";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url15LongInformation.equals("")) {
				url15KeyValuesAdd = new KeyValue();
				url15KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url15KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url15KeyValuesAdd.setEntryType(eType);
				url15KeyValuesAdd.setSequence(url15Sequence.trim());
				url15KeyValuesAdd.setKey1(key1);
				url15KeyValuesAdd.setKey2(key2);
				url15KeyValuesAdd.setKey3(key3);
				url15KeyValuesAdd.setKey4(key4);
				url15KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url15KeyValuesAdd.setValue(this.url15LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url15KeyValuesAdd.setDescription(this.url15Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url15KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url15KeyValuesAdd.getLastUpdateUser() == null)
					url15KeyValuesAdd.setLastUpdateUser("TreeNet");
				url15KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url15KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url15KeyValuesDelete = new Vector();
				url15KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url15Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url15Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url15UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url15LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url15LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url15LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url15Description" + x));
					newElement.setDeleteDate(request.getParameter("url15DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url15DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url15DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url15Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url15KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url15KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL16(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl16";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl16";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url16LongInformation.equals("")) {
				url16KeyValuesAdd = new KeyValue();
				url16KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url16KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url16KeyValuesAdd.setEntryType(eType);
				url16KeyValuesAdd.setSequence(url16Sequence.trim());
				url16KeyValuesAdd.setKey1(key1);
				url16KeyValuesAdd.setKey2(key2);
				url16KeyValuesAdd.setKey3(key3);
				url16KeyValuesAdd.setKey4(key4);
				url16KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url16KeyValuesAdd.setValue(this.url16LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url16KeyValuesAdd.setDescription(this.url16Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url16KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url16KeyValuesAdd.getLastUpdateUser() == null)
					url16KeyValuesAdd.setLastUpdateUser("TreeNet");
				url16KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url16KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url16KeyValuesDelete = new Vector();
				url16KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url16Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url16Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url16UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url16LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url16LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url16LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url16Description" + x));
					newElement.setDeleteDate(request.getParameter("url16DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url16DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url16DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url16Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url16KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url16KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL17(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl17";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl17";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url17LongInformation.equals("")) {
				url17KeyValuesAdd = new KeyValue();
				url17KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url17KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url17KeyValuesAdd.setEntryType(eType);
				url17KeyValuesAdd.setSequence(url17Sequence.trim());
				url17KeyValuesAdd.setKey1(key1);
				url17KeyValuesAdd.setKey2(key2);
				url17KeyValuesAdd.setKey3(key3);
				url17KeyValuesAdd.setKey4(key4);
				url17KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url17KeyValuesAdd.setValue(this.url17LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url17KeyValuesAdd.setDescription(this.url17Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url17KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url17KeyValuesAdd.getLastUpdateUser() == null)
					url17KeyValuesAdd.setLastUpdateUser("TreeNet");
				url17KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url17KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url17KeyValuesDelete = new Vector();
				url17KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url17Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url17Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url17UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url17LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url17LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url17LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url17Description" + x));
					newElement.setDeleteDate(request.getParameter("url17DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url17DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url17DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url17Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url17KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url17KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL18(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl18";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl18";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url18LongInformation.equals("")) {
				url18KeyValuesAdd = new KeyValue();
				url18KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url18KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url18KeyValuesAdd.setEntryType(eType);
				url18KeyValuesAdd.setSequence(url18Sequence.trim());
				url18KeyValuesAdd.setKey1(key1);
				url18KeyValuesAdd.setKey2(key2);
				url18KeyValuesAdd.setKey3(key3);
				url18KeyValuesAdd.setKey4(key4);
				url18KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url18KeyValuesAdd.setValue(this.url18LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url18KeyValuesAdd.setDescription(this.url18Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url18KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url18KeyValuesAdd.getLastUpdateUser() == null)
					url18KeyValuesAdd.setLastUpdateUser("TreeNet");
				url18KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url18KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url18KeyValuesDelete = new Vector();
				url18KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url18Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url18Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url18UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url18LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url18LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url18LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url18Description" + x));
					newElement.setDeleteDate(request.getParameter("url18DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url18DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url18DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url18Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url18KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url18KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added URL's
	 *    Build Vector for Updated URL's
	 *    Build Vector for Deleted URL's
	 * 
	 *   Set into the correct field, and then dealt 
	 *   with in the Servlet
	 */
	public void populateURL19(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			//if (!formulaNumber.equals("") && 
		//		!revisionDate.equals("") &&
		//		!revisionTime.equals(""))
		//	{
		//		  eType = "FormulaRevisionUrl";
		//		  key1 = formulaNumber;
		//		  key2 = revisionDate;
		//		  key3 = revisionTime;
		//	}
			if (!methodNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "MethodRevisionUrl19";
				  key1 = methodNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionUrl19";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!url19LongInformation.equals("")) {
				url19KeyValuesAdd = new KeyValue();
				url19KeyValuesAdd.setStatus("");
				if (!this.getEnvironment().trim().equals(""))
				   url19KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
				url19KeyValuesAdd.setEntryType(eType);
				url19KeyValuesAdd.setSequence(url19Sequence.trim());
				url19KeyValuesAdd.setKey1(key1);
				url19KeyValuesAdd.setKey2(key2);
				url19KeyValuesAdd.setKey3(key3);
				url19KeyValuesAdd.setKey4(key4);
				url19KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				url19KeyValuesAdd.setValue(this.url19LongInformation.trim());
	//			System.out.println("ON the Retrieve of the URL: " + urlLongInformation.trim());
				url19KeyValuesAdd.setDescription(this.url19Description.trim());
				// Last Update Date & Time will be filled in by the Service
				url19KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(
						request,
						response));
				if (url19KeyValuesAdd.getLastUpdateUser() == null)
					url19KeyValuesAdd.setLastUpdateUser("TreeNet");
				url19KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(url19KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				url19KeyValuesDelete = new Vector();
				url19KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("url19Status" + x));
					if (!this.getEnvironment().trim().equals(""))
					   newElement.setEnvironment(this.getEnvironment().trim());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("url19Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("url19UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("url19LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						!request.getParameter("url19LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("url19LongInformationOld" + x));
					newElement.setDescription(request.getParameter("url19Description" + x));
					newElement.setDeleteDate(request.getParameter("url19DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("url19DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("url19DeleteUser" + x));
					
					// DELETE RECORD	
					if (request.getParameter("url19Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						
					   url19KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
						newElement.setLastUpdateUser(
							com.treetop.SessionVariables.getSessionttiProfile(
								request,
								response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
					   url19KeyValuesUpdate.add(newElement);
					}
				}
			}
			//********************************************
		}
	
		return;
	}

	public String getSampleNumber() {
		return sampleNumber;
	}

	public void setSampleNumber(String sampleNumber) {
		this.sampleNumber = sampleNumber;
	}

	public Vector getComment20KeyValuesUpdate() {
		return comment20KeyValuesUpdate;
	}

	public void setComment20KeyValuesUpdate(Vector comment20KeyValuesUpdate) {
		this.comment20KeyValuesUpdate = comment20KeyValuesUpdate;
	}

	public Vector getComment21KeyValuesUpdate() {
		return comment21KeyValuesUpdate;
	}

	public void setComment21KeyValuesUpdate(Vector comment21KeyValuesUpdate) {
		this.comment21KeyValuesUpdate = comment21KeyValuesUpdate;
	}

	public Vector getComment22KeyValuesUpdate() {
		return comment22KeyValuesUpdate;
	}

	public void setComment22KeyValuesUpdate(Vector comment22KeyValuesUpdate) {
		this.comment22KeyValuesUpdate = comment22KeyValuesUpdate;
	}

	public Vector getComment23KeyValuesUpdate() {
		return comment23KeyValuesUpdate;
	}

	public void setComment23KeyValuesUpdate(Vector comment23KeyValuesUpdate) {
		this.comment23KeyValuesUpdate = comment23KeyValuesUpdate;
	}

	public Vector getComment24KeyValuesUpdate() {
		return comment24KeyValuesUpdate;
	}

	public void setComment24KeyValuesUpdate(Vector comment24KeyValuesUpdate) {
		this.comment24KeyValuesUpdate = comment24KeyValuesUpdate;
	}

	public Vector getComment20KeyValuesDelete() {
		return comment20KeyValuesDelete;
	}

	public void setComment20KeyValuesDelete(Vector comment20KeyValuesDelete) {
		this.comment20KeyValuesDelete = comment20KeyValuesDelete;
	}

	public Vector getComment21KeyValuesDelete() {
		return comment21KeyValuesDelete;
	}

	public void setComment21KeyValuesDelete(Vector comment21KeyValuesDelete) {
		this.comment21KeyValuesDelete = comment21KeyValuesDelete;
	}

	public Vector getComment22KeyValuesDelete() {
		return comment22KeyValuesDelete;
	}

	public void setComment22KeyValuesDelete(Vector comment22KeyValuesDelete) {
		this.comment22KeyValuesDelete = comment22KeyValuesDelete;
	}

	public Vector getComment23KeyValuesDelete() {
		return comment23KeyValuesDelete;
	}

	public void setComment23KeyValuesDelete(Vector comment23KeyValuesDelete) {
		this.comment23KeyValuesDelete = comment23KeyValuesDelete;
	}

	public Vector getComment24KeyValuesDelete() {
		return comment24KeyValuesDelete;
	}

	public void setComment24KeyValuesDelete(Vector comment24KeyValuesDelete) {
		this.comment24KeyValuesDelete = comment24KeyValuesDelete;
	}

	public KeyValue getComment20KeyValuesAdd() {
		return comment20KeyValuesAdd;
	}

	public void setComment20KeyValuesAdd(KeyValue comment20KeyValuesAdd) {
		this.comment20KeyValuesAdd = comment20KeyValuesAdd;
	}

	public KeyValue getComment21KeyValuesAdd() {
		return comment21KeyValuesAdd;
	}

	public void setComment21KeyValuesAdd(KeyValue comment21KeyValuesAdd) {
		this.comment21KeyValuesAdd = comment21KeyValuesAdd;
	}

	public KeyValue getComment22KeyValuesAdd() {
		return comment22KeyValuesAdd;
	}

	public void setComment22KeyValuesAdd(KeyValue comment22KeyValuesAdd) {
		this.comment22KeyValuesAdd = comment22KeyValuesAdd;
	}

	public KeyValue getComment23KeyValuesAdd() {
		return comment23KeyValuesAdd;
	}

	public void setComment23KeyValuesAdd(KeyValue comment23KeyValuesAdd) {
		this.comment23KeyValuesAdd = comment23KeyValuesAdd;
	}

	public KeyValue getComment24KeyValuesAdd() {
		return comment24KeyValuesAdd;
	}

	public void setComment24KeyValuesAdd(KeyValue comment24KeyValuesAdd) {
		this.comment24KeyValuesAdd = comment24KeyValuesAdd;
	}

	public String getComment20Sequence() {
		return comment20Sequence;
	}

	public void setComment20Sequence(String comment20Sequence) {
		this.comment20Sequence = comment20Sequence;
	}

	public String getComment20LongInformation() {
		return comment20LongInformation;
	}

	public void setComment20LongInformation(String comment20LongInformation) {
		this.comment20LongInformation = comment20LongInformation;
	}

	public String getComment20Description() {
		return comment20Description;
	}

	public void setComment20Description(String comment20Description) {
		this.comment20Description = comment20Description;
	}

	public String getComment20KeyValuesCount() {
		return comment20KeyValuesCount;
	}

	public void setComment20KeyValuesCount(String comment20KeyValuesCount) {
		this.comment20KeyValuesCount = comment20KeyValuesCount;
	}

	public String getComment21Sequence() {
		return comment21Sequence;
	}

	public void setComment21Sequence(String comment21Sequence) {
		this.comment21Sequence = comment21Sequence;
	}

	public String getComment21LongInformation() {
		return comment21LongInformation;
	}

	public void setComment21LongInformation(String comment21LongInformation) {
		this.comment21LongInformation = comment21LongInformation;
	}

	public String getComment21Description() {
		return comment21Description;
	}

	public void setComment21Description(String comment21Description) {
		this.comment21Description = comment21Description;
	}

	public String getComment21KeyValuesCount() {
		return comment21KeyValuesCount;
	}

	public void setComment21KeyValuesCount(String comment21KeyValuesCount) {
		this.comment21KeyValuesCount = comment21KeyValuesCount;
	}

	public String getComment22Sequence() {
		return comment22Sequence;
	}

	public void setComment22Sequence(String comment22Sequence) {
		this.comment22Sequence = comment22Sequence;
	}

	public String getComment22LongInformation() {
		return comment22LongInformation;
	}

	public void setComment22LongInformation(String comment22LongInformation) {
		this.comment22LongInformation = comment22LongInformation;
	}

	public String getComment22Description() {
		return comment22Description;
	}

	public void setComment22Description(String comment22Description) {
		this.comment22Description = comment22Description;
	}

	public String getComment22KeyValuesCount() {
		return comment22KeyValuesCount;
	}

	public void setComment22KeyValuesCount(String comment22KeyValuesCount) {
		this.comment22KeyValuesCount = comment22KeyValuesCount;
	}

	public String getComment23Sequence() {
		return comment23Sequence;
	}

	public void setComment23Sequence(String comment23Sequence) {
		this.comment23Sequence = comment23Sequence;
	}

	public String getComment23LongInformation() {
		return comment23LongInformation;
	}

	public void setComment23LongInformation(String comment23LongInformation) {
		this.comment23LongInformation = comment23LongInformation;
	}

	public String getComment23Description() {
		return comment23Description;
	}

	public void setComment23Description(String comment23Description) {
		this.comment23Description = comment23Description;
	}

	public String getComment23KeyValuesCount() {
		return comment23KeyValuesCount;
	}

	public void setComment23KeyValuesCount(String comment23KeyValuesCount) {
		this.comment23KeyValuesCount = comment23KeyValuesCount;
	}

	public String getComment24Sequence() {
		return comment24Sequence;
	}

	public void setComment24Sequence(String comment24Sequence) {
		this.comment24Sequence = comment24Sequence;
	}

	public String getComment24LongInformation() {
		return comment24LongInformation;
	}

	public void setComment24LongInformation(String comment24LongInformation) {
		this.comment24LongInformation = comment24LongInformation;
	}

	public String getComment24Description() {
		return comment24Description;
	}

	public void setComment24Description(String comment24Description) {
		this.comment24Description = comment24Description;
	}

	public String getComment24KeyValuesCount() {
		return comment24KeyValuesCount;
	}

	public void setComment24KeyValuesCount(String comment24KeyValuesCount) {
		this.comment24KeyValuesCount = comment24KeyValuesCount;
	}

	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then deal 
	 *   with in the Servlet
	 *   Added 2/14/13 TWalton
	 */
	public void populateComment20(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment20";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!comment20LongInformation.equals("")) {
				comment20KeyValuesAdd = new KeyValue();
				comment20KeyValuesAdd.setStatus("");
				comment20KeyValuesAdd.setEnvironment(this.getEnvironment());
				comment20KeyValuesAdd.setEntryType(eType);
				comment20KeyValuesAdd.setSequence(this.comment20Sequence.trim());
				comment20KeyValuesAdd.setKey1(key1);
				comment20KeyValuesAdd.setKey2(key2);
				comment20KeyValuesAdd.setKey3(key3);
				comment20KeyValuesAdd.setKey4(key4);
				comment20KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment20KeyValuesAdd.setValue(this.comment20LongInformation.trim());
				comment20KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment20KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(request,response));
				if (comment20KeyValuesAdd.getLastUpdateUser() == null)
					comment20KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment20KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment20KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment20KeyValuesDelete = new Vector();
				comment20KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment20Status" + x));
					newElement.setEnvironment(this.getEnvironment());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment20Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment20UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment20LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment20LongInformationOld" + x) != null &&
					    !request.getParameter("comment20LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment20LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment20DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment20DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment20DeleteUser" + x));
						
					// DELETE RECORD	
					if (request.getParameter("comment20Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(request, response));
					   comment20KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
								   request,  response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment20KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		
		return;
	}
	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then deal 
	 *   with in the Servlet
	 *   Added 2/14/13 TWalton
	 */
	public void populateComment21(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment21";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!comment21LongInformation.equals("")) {
				comment21KeyValuesAdd = new KeyValue();
				comment21KeyValuesAdd.setStatus("");
				comment21KeyValuesAdd.setEnvironment(this.getEnvironment());
				comment21KeyValuesAdd.setEntryType(eType);
				comment21KeyValuesAdd.setSequence(this.comment21Sequence.trim());
				comment21KeyValuesAdd.setKey1(key1);
				comment21KeyValuesAdd.setKey2(key2);
				comment21KeyValuesAdd.setKey3(key3);
				comment21KeyValuesAdd.setKey4(key4);
				comment21KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment21KeyValuesAdd.setValue(this.comment21LongInformation.trim());
				comment21KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment21KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(request,response));
				if (comment21KeyValuesAdd.getLastUpdateUser() == null)
					comment21KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment21KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment21KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment21KeyValuesDelete = new Vector();
				comment21KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment21Status" + x));
					newElement.setEnvironment(this.getEnvironment());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment21Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment21UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment21LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment21LongInformationOld" + x) != null &&
					    !request.getParameter("comment21LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment21LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment21DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment21DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment21DeleteUser" + x));
						
					// DELETE RECORD	
					if (request.getParameter("comment21Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(request, response));
					   comment21KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
								   request,  response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment21KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		
		return;
	}
	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then deal 
	 *   with in the Servlet
	 *   Added 2/14/13 TWalton
	 */
	public void populateComment22(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment22";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!comment22LongInformation.equals("")) {
				comment22KeyValuesAdd = new KeyValue();
				comment22KeyValuesAdd.setStatus("");
				comment22KeyValuesAdd.setEnvironment(this.getEnvironment());
				comment22KeyValuesAdd.setEntryType(eType);
				comment22KeyValuesAdd.setSequence(this.comment22Sequence.trim());
				comment22KeyValuesAdd.setKey1(key1);
				comment22KeyValuesAdd.setKey2(key2);
				comment22KeyValuesAdd.setKey3(key3);
				comment22KeyValuesAdd.setKey4(key4);
				comment22KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment22KeyValuesAdd.setValue(this.comment22LongInformation.trim());
				comment22KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment22KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(request,response));
				if (comment22KeyValuesAdd.getLastUpdateUser() == null)
					comment22KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment22KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment22KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment22KeyValuesDelete = new Vector();
				comment22KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment22Status" + x));
					newElement.setEnvironment(this.getEnvironment());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment22Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment22UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment22LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment22LongInformationOld" + x) != null &&
					    !request.getParameter("comment22LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment22LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment22DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment22DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment22DeleteUser" + x));
						
					// DELETE RECORD	
					if (request.getParameter("comment22Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(request, response));
					   comment22KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
								   request,  response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment22KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		
		return;
	}
	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then deal 
	 *   with in the Servlet
	 *   Added 2/14/13 TWalton
	 */
	public void populateComment23(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment23";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!comment23LongInformation.equals("")) {
				comment23KeyValuesAdd = new KeyValue();
				comment23KeyValuesAdd.setStatus("");
				comment23KeyValuesAdd.setEnvironment(this.getEnvironment());
				comment23KeyValuesAdd.setEntryType(eType);
				comment23KeyValuesAdd.setSequence(this.comment23Sequence.trim());
				comment23KeyValuesAdd.setKey1(key1);
				comment23KeyValuesAdd.setKey2(key2);
				comment23KeyValuesAdd.setKey3(key3);
				comment23KeyValuesAdd.setKey4(key4);
				comment23KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment23KeyValuesAdd.setValue(this.comment23LongInformation.trim());
				comment23KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment23KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(request,response));
				if (comment23KeyValuesAdd.getLastUpdateUser() == null)
					comment23KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment23KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment23KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment23KeyValuesDelete = new Vector();
				comment23KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment23Status" + x));
					newElement.setEnvironment(this.getEnvironment());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment23Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment23UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment23LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment23LongInformationOld" + x) != null &&
					    !request.getParameter("comment23LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment23LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment23DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment23DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment23DeleteUser" + x));
						
					// DELETE RECORD	
					if (request.getParameter("comment23Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(request, response));
					   comment23KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
								   request,  response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment23KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		
		return;
	}
	/*
	 * Put information into the CORRECT AREA
	 *    Build Class for Added Comments's
	 *    Build Vector for Updated Comment's
	 *    Build Vector for Deleted Comments's
	 * 
	 *   Set into the correct field, and then deal 
	 *   with in the Servlet
	 *   Added 2/14/13 TWalton
	 */
	public void populateComment24(
		HttpServletRequest request,
		HttpServletResponse response) {
		if (saveButton != null) {
			String eType = "";
			String key1 = "";
			String key2 = "";
			String key3 = "";
			String key4 = "";
			String key5 = "";
			if (!specNumber.equals("") && 
				!revisionDate.equals("") &&
				!revisionTime.equals(""))
			{
				  eType = "SpecRevisionComment24";
				  key1 = specNumber;
				  key2 = revisionDate;
				  key3 = revisionTime;
			}
			//********************************************
			// ADD A NEW RECORD
			if (!comment24LongInformation.equals("")) {
				comment24KeyValuesAdd = new KeyValue();
				comment24KeyValuesAdd.setStatus("");
				comment24KeyValuesAdd.setEnvironment(this.getEnvironment());
				comment24KeyValuesAdd.setEntryType(eType);
				comment24KeyValuesAdd.setSequence(this.comment20Sequence.trim());
				comment24KeyValuesAdd.setKey1(key1);
				comment24KeyValuesAdd.setKey2(key2);
				comment24KeyValuesAdd.setKey3(key3);
				comment24KeyValuesAdd.setKey4(key4);
				comment24KeyValuesAdd.setKey5(key5);
				// Unique Key is loaded in the Add method of the Service
				comment24KeyValuesAdd.setValue(this.comment24LongInformation.trim());
				comment24KeyValuesAdd.setDescription("");
				// Last Update Date & Time will be filled in by the Service
				comment24KeyValuesAdd.setLastUpdateUser(
					com.treetop.SessionVariables.getSessionttiProfile(request,response));
				if (comment24KeyValuesAdd.getLastUpdateUser() == null)
					comment24KeyValuesAdd.setLastUpdateUser("TreeNet");
				comment24KeyValuesAdd.setDeleteUser("");
			}
			//********************************************
			// BUILD UPDATE AND DELETE VECTORS
			int countOLD = 0;
			try {
				countOLD = new Integer(comment24KeyValuesCount).intValue();
			} catch (Exception e) {
			}
			if (countOLD > 0) {
				comment24KeyValuesDelete = new Vector();
				comment24KeyValuesUpdate = new Vector();
				for (int x = 0; x < countOLD; x++) {
					KeyValue newElement = new KeyValue();
					newElement.setStatus(request.getParameter("comment24Status" + x));
					newElement.setEnvironment(this.getEnvironment());
					newElement.setEntryType(eType);
					newElement.setSequence(request.getParameter("comment24Sequence" + x));
					newElement.setKey1(key1);
					newElement.setKey2(key2);
					newElement.setKey3(key3);
					newElement.setKey4(key4);
					newElement.setKey5(key5);
					newElement.setUniqueKey(request.getParameter("comment24UniqueNumber" + x));//Unique Field
					newElement.setValue(request.getParameter("comment24LongInformation" + x));
					if (newElement.getValue().trim().equals("") &&
						request.getParameter("comment24LongInformationOld" + x) != null &&
					    !request.getParameter("comment24LongInformationOld" + x).equals(""))
					   newElement.setValue(request.getParameter("comment24LongInformationOld" + x));
					newElement.setDescription("");
					newElement.setDeleteDate(request.getParameter("comment24DeleteDate" + x));
					newElement.setDeleteTime(request.getParameter("comment24DeleteTime" + x));
					newElement.setDeleteUser(request.getParameter("comment24DeleteUser" + x));
						
					// DELETE RECORD	
					if (request.getParameter("comment24Delete" + x) != null)
					{
						newElement.setDeleteUser(
							com.treetop.SessionVariables.getSessionttiProfile(request, response));
					   comment20KeyValuesDelete.add(newElement);
					}   
					else // Update RECORD
					{
					   newElement.setLastUpdateUser(
						   com.treetop.SessionVariables.getSessionttiProfile(
								   request,  response));
						if (newElement.getLastUpdateUser() == null)
							newElement.setLastUpdateUser("TreeNet");
						comment24KeyValuesUpdate.add(newElement);		   
					}
				}
			}
			//********************************************
		}
		
		return;
	}

	public String getComment25Sequence() {
		return comment25Sequence;
	}

	public void setComment25Sequence(String comment25Sequence) {
		this.comment25Sequence = comment25Sequence;
	}

	public String getComment25LongInformation() {
		return comment25LongInformation;
	}

	public void setComment25LongInformation(String comment25LongInformation) {
		this.comment25LongInformation = comment25LongInformation;
	}

	public String getComment25Description() {
		return comment25Description;
	}

	public void setComment25Description(String comment25Description) {
		this.comment25Description = comment25Description;
	}

	public String getComment25KeyValuesCount() {
		return comment25KeyValuesCount;
	}

	public void setComment25KeyValuesCount(String comment25KeyValuesCount) {
		this.comment25KeyValuesCount = comment25KeyValuesCount;
	}

	public String getComment26Sequence() {
		return comment26Sequence;
	}

	public void setComment26Sequence(String comment26Sequence) {
		this.comment26Sequence = comment26Sequence;
	}

	public String getComment26LongInformation() {
		return comment26LongInformation;
	}

	public void setComment26LongInformation(String comment26LongInformation) {
		this.comment26LongInformation = comment26LongInformation;
	}

	public String getComment26Description() {
		return comment26Description;
	}

	public void setComment26Description(String comment26Description) {
		this.comment26Description = comment26Description;
	}

	public String getComment26KeyValuesCount() {
		return comment26KeyValuesCount;
	}

	public void setComment26KeyValuesCount(String comment26KeyValuesCount) {
		this.comment26KeyValuesCount = comment26KeyValuesCount;
	}

	public String getComment27Sequence() {
		return comment27Sequence;
	}

	public void setComment27Sequence(String comment27Sequence) {
		this.comment27Sequence = comment27Sequence;
	}

	public String getComment27LongInformation() {
		return comment27LongInformation;
	}

	public void setComment27LongInformation(String comment27LongInformation) {
		this.comment27LongInformation = comment27LongInformation;
	}

	public String getComment27Description() {
		return comment27Description;
	}

	public void setComment27Description(String comment27Description) {
		this.comment27Description = comment27Description;
	}

	public String getComment27KeyValuesCount() {
		return comment27KeyValuesCount;
	}

	public void setComment27KeyValuesCount(String comment27KeyValuesCount) {
		this.comment27KeyValuesCount = comment27KeyValuesCount;
	}

	public String getComment28Sequence() {
		return comment28Sequence;
	}

	public void setComment28Sequence(String comment28Sequence) {
		this.comment28Sequence = comment28Sequence;
	}

	public String getComment28LongInformation() {
		return comment28LongInformation;
	}

	public void setComment28LongInformation(String comment28LongInformation) {
		this.comment28LongInformation = comment28LongInformation;
	}

	public String getComment28Description() {
		return comment28Description;
	}

	public void setComment28Description(String comment28Description) {
		this.comment28Description = comment28Description;
	}

	public String getComment28KeyValuesCount() {
		return comment28KeyValuesCount;
	}

	public void setComment28KeyValuesCount(String comment28KeyValuesCount) {
		this.comment28KeyValuesCount = comment28KeyValuesCount;
	}

	public String getComment29Sequence() {
		return comment29Sequence;
	}

	public void setComment29Sequence(String comment29Sequence) {
		this.comment29Sequence = comment29Sequence;
	}

	public String getComment29LongInformation() {
		return comment29LongInformation;
	}

	public void setComment29LongInformation(String comment29LongInformation) {
		this.comment29LongInformation = comment29LongInformation;
	}

	public String getComment29Description() {
		return comment29Description;
	}

	public void setComment29Description(String comment29Description) {
		this.comment29Description = comment29Description;
	}

	public String getComment29KeyValuesCount() {
		return comment29KeyValuesCount;
	}

	public void setComment29KeyValuesCount(String comment29KeyValuesCount) {
		this.comment29KeyValuesCount = comment29KeyValuesCount;
	}

	/*
		 * Put information into the CORRECT AREA
		 *    Build Class for Added Comments's
		 *    Build Vector for Updated Comment's
		 *    Build Vector for Deleted Comments's
		 * 
		 *   Set into the correct field, and then dealt 
		 *   with in the Servlet
		 */
		public void populateComment25(
			HttpServletRequest request,
			HttpServletResponse response) {
			if (saveButton != null) {
				String eType = "";
				String key1 = "";
				String key2 = "";
				String key3 = "";
				String key4 = "";
				String key5 = "";
			
				if (!methodNumber.equals("") && 
					!revisionDate.equals("") &&
					!revisionTime.equals(""))
				{
					  eType = "MethodRevisionComment25";
					  key1 = methodNumber;
					  key2 = revisionDate;
					  key3 = revisionTime;
				}
				if (!specNumber.equals("") && 
					!revisionDate.equals("") &&
					!revisionTime.equals(""))
				{
					  eType = "SpecRevisionComment25";
					  key1 = specNumber;
					  key2 = revisionDate;
					  key3 = revisionTime;
				}
				//********************************************
				// ADD A NEW RECORD
	// 1/17/12 TWalton no longer needed using base viewbeanr2				
	//			if (comment15LongInformation.trim().equals(""))
	//			{
	//				this.setComment15LongInformation(request.getParameter("comment15LongInformation"));
	//				if (comment15LongInformation == null)
	//				   this.setComment15LongInformation("");
	//			}
				if (!comment25LongInformation.equals("")) {
					comment25KeyValuesAdd = new KeyValue();
					comment25KeyValuesAdd.setStatus("");
					comment25KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
					comment25KeyValuesAdd.setEntryType(eType);
					comment25KeyValuesAdd.setSequence(this.getComment25Sequence().trim());
					comment25KeyValuesAdd.setKey1(key1);
					comment25KeyValuesAdd.setKey2(key2);
					comment25KeyValuesAdd.setKey3(key3);
					comment25KeyValuesAdd.setKey4(key4);
					comment25KeyValuesAdd.setKey5(key5);
					// Unique Key is loaded in the Add method of the Service
					comment25KeyValuesAdd.setValue(this.comment25LongInformation.trim());
	//				 5/11/11 -- Added information for apostrophe
	//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
					//comment25KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment25LongInformation.trim()));
					comment25KeyValuesAdd.setDescription("");
					// Last Update Date & Time will be filled in by the Service
					comment25KeyValuesAdd.setLastUpdateUser(
						com.treetop.SessionVariables.getSessionttiProfile(
							request, response));
					if (comment25KeyValuesAdd.getLastUpdateUser() == null)
						comment25KeyValuesAdd.setLastUpdateUser("TreeNet");
					comment25KeyValuesAdd.setDeleteUser("");
				}
				//********************************************
				// BUILD UPDATE AND DELETE VECTORS
				int countOLD = 0;
				try {
					countOLD = new Integer(comment25KeyValuesCount).intValue();
				} catch (Exception e) {
				}
				if (countOLD > 0) {
					comment25KeyValuesDelete = new Vector();
					comment25KeyValuesUpdate = new Vector();
					for (int x = 0; x < countOLD; x++) {
						KeyValue newElement = new KeyValue();
						newElement.setStatus(request.getParameter("comment25Status" + x));
						newElement.setEnvironment(this.getEnvironment().trim());
						newElement.setEntryType(eType);
						newElement.setSequence(request.getParameter("comment25Sequence" + x));
						newElement.setKey1(key1);
						newElement.setKey2(key2);
						newElement.setKey3(key3);
						newElement.setKey4(key4);
						newElement.setKey5(key5);
						newElement.setUniqueKey(request.getParameter("comment25UniqueNumber" + x));//Unique Field
						newElement.setValue(request.getParameter("comment25LongInformation" + x));
						if (newElement.getValue().trim().equals("") &&
							request.getParameter("comment25LongInformationOld" + x) != null &&
						    !request.getParameter("comment25LongInformationOld" + x).equals(""))
						   newElement.setValue(request.getParameter("comment25LongInformationOld" + x));
						newElement.setDescription("");
						newElement.setDeleteDate(request.getParameter("comment25DeleteDate" + x));
						newElement.setDeleteTime(request.getParameter("comment25DeleteTime" + x));
						newElement.setDeleteUser(request.getParameter("comment25DeleteUser" + x));
							
						// DELETE RECORD	
						if (request.getParameter("comment25Delete" + x) != null)
						{
							newElement.setDeleteUser(
								com.treetop.SessionVariables.getSessionttiProfile(
									request,
									response));
						   comment25KeyValuesDelete.add(newElement);
						}   
						else // Update RECORD
						{
						   newElement.setLastUpdateUser(
							   com.treetop.SessionVariables.getSessionttiProfile(
								   request, response));
						   if (newElement.getLastUpdateUser() == null)
								newElement.setLastUpdateUser("TreeNet");
							comment25KeyValuesUpdate.add(newElement);		   
						}
					}
				}
				//********************************************
			}
			return;
		}

	/*
		 * Put information into the CORRECT AREA
		 *    Build Class for Added Comments's
		 *    Build Vector for Updated Comment's
		 *    Build Vector for Deleted Comments's
		 * 
		 *   Set into the correct field, and then dealt 
		 *   with in the Servlet
		 */
		public void populateComment26(HttpServletRequest request,
					HttpServletResponse response) {
			if (saveButton != null) {
				String eType = "";
				String key1 = "";
				String key2 = "";
				String key3 = "";
				String key4 = "";
				String key5 = "";
				
				if (!methodNumber.equals("") && 
					!revisionDate.equals("") &&
					!revisionTime.equals(""))
				{
					  eType = "MethodRevisionComment26";
					  key1 = methodNumber;
					  key2 = revisionDate;
					  key3 = revisionTime;
				}
				if (!specNumber.equals("") && 
					!revisionDate.equals("") &&
					!revisionTime.equals(""))
				{
					  eType = "SpecRevisionComment26";
					  key1 = specNumber;
					  key2 = revisionDate;
					  key3 = revisionTime;
				}
				//********************************************
				// ADD A NEW RECORD
				// 1/17/12 TWalton no longer needed using base viewbeanr2				
	//			if (comment26LongInformation.trim().equals(""))
	//			{
	//				this.setComment26LongInformation(request.getParameter("comment26LongInformation"));
	//				if (comment26LongInformation == null)
	//				   this.setComment26LongInformation("");
	//			}
				
				if (!comment26LongInformation.equals("")) {
					comment26KeyValuesAdd = new KeyValue();
					comment26KeyValuesAdd.setStatus("");
					comment26KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
					comment26KeyValuesAdd.setEntryType(eType);
					comment26KeyValuesAdd.setSequence(this.getComment26Sequence().trim());
					comment26KeyValuesAdd.setKey1(key1);
					comment26KeyValuesAdd.setKey2(key2);
					comment26KeyValuesAdd.setKey3(key3);
					comment26KeyValuesAdd.setKey4(key4);
					comment26KeyValuesAdd.setKey5(key5);
					// Unique Key is loaded in the Add method of the Service
					comment26KeyValuesAdd.setValue(this.comment26LongInformation.trim());
	//				 5/11/11 -- Added information for apostrophe
	//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
					//comment26KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment26LongInformation.trim()));
					comment26KeyValuesAdd.setDescription("");
					// Last Update Date & Time will be filled in by the Service
					comment26KeyValuesAdd.setLastUpdateUser(
						com.treetop.SessionVariables.getSessionttiProfile(
											request,response));
					if (comment26KeyValuesAdd.getLastUpdateUser() == null)
						comment26KeyValuesAdd.setLastUpdateUser("TreeNet");
					comment26KeyValuesAdd.setDeleteUser("");
				}
				//********************************************
				// BUILD UPDATE AND DELETE VECTORS
				int countOLD = 0;
				try {
					countOLD = new Integer(comment26KeyValuesCount).intValue();
				} catch (Exception e) {}
				if (countOLD > 0) {
					comment26KeyValuesDelete = new Vector();
					comment26KeyValuesUpdate = new Vector();
					for (int x = 0; x < countOLD; x++) {
						KeyValue newElement = new KeyValue();
						newElement.setStatus(request.getParameter("comment26Status" + x));
						newElement.setEnvironment(this.getEnvironment().trim());
						newElement.setEntryType(eType);
						newElement.setSequence(request.getParameter("comment26Sequence" + x));
						newElement.setKey1(key1);
						newElement.setKey2(key2);
						newElement.setKey3(key3);
						newElement.setKey4(key4);
						newElement.setKey5(key5);
						newElement.setUniqueKey(request.getParameter("comment26UniqueNumber" + x));//Unique Field
						newElement.setValue(request.getParameter("comment26LongInformation" + x));
						if (newElement.getValue().trim().equals("") &&
							request.getParameter("comment26LongInformationOld" + x) != null &&
							!request.getParameter("comment26LongInformationOld" + x).equals(""))
							newElement.setValue(request.getParameter("comment26LongInformationOld" + x));
						newElement.setDescription("");
						newElement.setDeleteDate(request.getParameter("comment26DeleteDate" + x));
						newElement.setDeleteTime(request.getParameter("comment26DeleteTime" + x));
						newElement.setDeleteUser(request.getParameter("comment26DeleteUser" + x));
											
						// DELETE RECORD	
						if (request.getParameter("comment26Delete" + x) != null)
						{
							newElement.setDeleteUser(
								com.treetop.SessionVariables.getSessionttiProfile(
										request,response));
						   comment26KeyValuesDelete.add(newElement);
						}   
						else // Update RECORD
						{
						   newElement.setLastUpdateUser(
							   com.treetop.SessionVariables.getSessionttiProfile(
								   request, response));
						   if (newElement.getLastUpdateUser() == null)
							   newElement.setLastUpdateUser("TreeNet");
						   	  comment26KeyValuesUpdate.add(newElement);		   
						}
					}
				}
				//********************************************
			}
			return;
		}

	/*
		 * Put information into the CORRECT AREA
		 *    Build Class for Added Comments's
		 *    Build Vector for Updated Comment's
		 *    Build Vector for Deleted Comments's
		 * 
		 *   Set into the correct field, and then dealt 
		 *   with in the Servlet
		 */
		public void populateComment27(HttpServletRequest request,
			HttpServletResponse response) {
			if (saveButton != null) {
				String eType = "";
				String key1 = "";
				String key2 = "";
				String key3 = "";
				String key4 = "";
				String key5 = "";
				
				if (!methodNumber.equals("") && 
					!revisionDate.equals("") &&
					!revisionTime.equals(""))
				{
					  eType = "MethodRevisionComment27";
					  key1 = methodNumber;
					  key2 = revisionDate;
					  key3 = revisionTime;
				}
				if (!specNumber.equals("") && 
					!revisionDate.equals("") &&
					!revisionTime.equals(""))
				{
					  eType = "SpecRevisionComment27";
					  key1 = specNumber;
					  key2 = revisionDate;
					  key3 = revisionTime;
				}
				//********************************************
				// ADD A NEW RECORD
				// 1/27/12 TWalton no longer needed using base viewbeanr2				
	//			if (comment27LongInformation.trim().equals(""))
	//			{
	//				this.setComment27LongInformation(request.getParameter("comment27LongInformation"));
	//				if (comment27LongInformation == null)
	//				   this.setComment27LongInformation("");
	//			}
				if (!comment27LongInformation.equals("")) {
					comment27KeyValuesAdd = new KeyValue();
					comment27KeyValuesAdd.setStatus("");
					comment27KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
					comment27KeyValuesAdd.setEntryType(eType);
					comment27KeyValuesAdd.setSequence(this.getComment27Sequence().trim());
					comment27KeyValuesAdd.setKey1(key1);
					comment27KeyValuesAdd.setKey2(key2);
					comment27KeyValuesAdd.setKey3(key3);
					comment27KeyValuesAdd.setKey4(key4);
					comment27KeyValuesAdd.setKey5(key5);
					// Unique Key is loaded in the Add method of the Service
					comment27KeyValuesAdd.setValue(this.comment27LongInformation.trim());
	//				 5/11/11 -- Added information for apostrophe
	//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
					//comment27KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment27LongInformation.trim()));
					comment27KeyValuesAdd.setDescription("");
					// Last Update Date & Time will be filled in by the Service
					comment27KeyValuesAdd.setLastUpdateUser(
						com.treetop.SessionVariables.getSessionttiProfile(
							request,response));
					if (comment27KeyValuesAdd.getLastUpdateUser() == null)
						comment27KeyValuesAdd.setLastUpdateUser("TreeNet");
					comment27KeyValuesAdd.setDeleteUser("");
				}
				//********************************************
				// BUILD UPDATE AND DELETE VECTORS
				int countOLD = 0;
				try {
					countOLD = new Integer(comment27KeyValuesCount).intValue();
				} catch (Exception e) {}
				if (countOLD > 0) {
					comment27KeyValuesDelete = new Vector();
					comment27KeyValuesUpdate = new Vector();
					for (int x = 0; x < countOLD; x++) {
						KeyValue newElement = new KeyValue();
						newElement.setStatus(request.getParameter("comment27Status" + x));
						newElement.setEnvironment(this.getEnvironment().trim());
						newElement.setEntryType(eType);
						newElement.setSequence(request.getParameter("comment27Sequence" + x));
						newElement.setKey1(key1);
						newElement.setKey2(key2);
						newElement.setKey3(key3);
						newElement.setKey4(key4);
						newElement.setKey5(key5);
						newElement.setUniqueKey(request.getParameter("comment27UniqueNumber" + x));//Unique Field
						newElement.setValue(request.getParameter("comment27LongInformation" + x));
						if (newElement.getValue().trim().equals("") &&
							request.getParameter("comment27LongInformationOld" + x) != null &&
						    !request.getParameter("comment27LongInformationOld" + x).equals(""))
						   newElement.setValue(request.getParameter("comment27LongInformationOld" + x));
						newElement.setDescription("");
						newElement.setDeleteDate(request.getParameter("comment27DeleteDate" + x));
						newElement.setDeleteTime(request.getParameter("comment27DeleteTime" + x));
						newElement.setDeleteUser(request.getParameter("comment27DeleteUser" + x));
											
						// DELETE RECORD	
						if (request.getParameter("comment27Delete" + x) != null)
						{
							newElement.setDeleteUser(
								com.treetop.SessionVariables.getSessionttiProfile(
									request,response));
						   comment27KeyValuesDelete.add(newElement);
						}   
						else // Update RECORD
						{
						   newElement.setLastUpdateUser(
							   com.treetop.SessionVariables.getSessionttiProfile(
								   request, response));
						   if (newElement.getLastUpdateUser() == null)
								newElement.setLastUpdateUser("TreeNet");
							comment27KeyValuesUpdate.add(newElement);		   
						}
					}
				}
				//********************************************
			}
			return;
		}

	/*
		 * Put information into the CORRECT AREA
		 *    Build Class for Added Comments's
		 *    Build Vector for Updated Comment's
		 *    Build Vector for Deleted Comments's
		 * 
		 *   Set into the correct field, and then dealt 
		 *   with in the Servlet
		 */
		public void populateComment28(HttpServletRequest request,
			HttpServletResponse response) {
			if (saveButton != null) {
				String eType = "";
				String key1 = "";
				String key2 = "";
				String key3 = "";
				String key4 = "";
				String key5 = "";
				
				if (!methodNumber.equals("") && 
					!revisionDate.equals("") &&
					!revisionTime.equals(""))
				{
					  eType = "MethodRevisionComment28";
					  key1 = methodNumber;
					  key2 = revisionDate;
					  key3 = revisionTime;
				}
				if (!specNumber.equals("") && 
					!revisionDate.equals("") &&
					!revisionTime.equals(""))
				{
					  eType = "SpecRevisionComment28";
					  key1 = specNumber;
					  key2 = revisionDate;
					  key3 = revisionTime;
				}
				//********************************************
				// ADD A NEW RECORD
				// 1/27/12 TWalton no longer needed using base viewbeanr2				
	//			if (comment28LongInformation.trim().equals(""))
	//			{
	//				this.setComment28LongInformation(request.getParameter("comment28LongInformation"));
	//				if (comment28LongInformation == null)
	//				   this.setComment28LongInformation("");
	//			}
				if (!comment28LongInformation.equals("")) {
					comment28KeyValuesAdd = new KeyValue();
					comment28KeyValuesAdd.setStatus("");
					comment28KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
					comment28KeyValuesAdd.setEntryType(eType);
					comment28KeyValuesAdd.setSequence(this.getComment28Sequence().trim());
					comment28KeyValuesAdd.setKey1(key1);
					comment28KeyValuesAdd.setKey2(key2);
					comment28KeyValuesAdd.setKey3(key3);
					comment28KeyValuesAdd.setKey4(key4);
					comment28KeyValuesAdd.setKey5(key5);
					// Unique Key is loaded in the Add method of the Service
					comment28KeyValuesAdd.setValue(this.comment28LongInformation.trim());
	//				 5/11/11 -- Added information for apostrophe
	//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
					//comment28KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment28LongInformation.trim()));
					comment28KeyValuesAdd.setDescription("");
					// Last Update Date & Time will be filled in by the Service
					comment28KeyValuesAdd.setLastUpdateUser(
						com.treetop.SessionVariables.getSessionttiProfile(
								request,response));
					if (comment28KeyValuesAdd.getLastUpdateUser() == null)
						comment28KeyValuesAdd.setLastUpdateUser("TreeNet");
					comment28KeyValuesAdd.setDeleteUser("");
				}
				//********************************************
				// BUILD UPDATE AND DELETE VECTORS
				int countOLD = 0;
				try {
					countOLD = new Integer(comment28KeyValuesCount).intValue();
					} catch (Exception e) {}
				if (countOLD > 0) {
					comment28KeyValuesDelete = new Vector();
					comment28KeyValuesUpdate = new Vector();
					for (int x = 0; x < countOLD; x++) {
						KeyValue newElement = new KeyValue();
						newElement.setStatus(request.getParameter("comment28Status" + x));
						newElement.setEnvironment(this.getEnvironment().trim());
						newElement.setEntryType(eType);
						newElement.setSequence(request.getParameter("comment28Sequence" + x));
						newElement.setKey1(key1);
						newElement.setKey2(key2);
						newElement.setKey3(key3);
						newElement.setKey4(key4);
						newElement.setKey5(key5);
						newElement.setUniqueKey(request.getParameter("comment28UniqueNumber" + x));//Unique Field
						newElement.setValue(request.getParameter("comment28LongInformation" + x));
						if (newElement.getValue().trim().equals("") &&
							request.getParameter("comment28LongInformationOld" + x) != null &&
						    !request.getParameter("comment28LongInformationOld" + x).equals(""))
						   newElement.setValue(request.getParameter("comment28LongInformationOld" + x));
						newElement.setDescription("");
						newElement.setDeleteDate(request.getParameter("comment28DeleteDate" + x));
						newElement.setDeleteTime(request.getParameter("comment28DeleteTime" + x));
						newElement.setDeleteUser(request.getParameter("comment28DeleteUser" + x));
										
						// DELETE RECORD	
						if (request.getParameter("comment28Delete" + x) != null)
						{
							newElement.setDeleteUser(
								com.treetop.SessionVariables.getSessionttiProfile(
										request,response));
						    comment28KeyValuesDelete.add(newElement);
						}   
						else // Update RECORD
						{
						   newElement.setLastUpdateUser(
							   com.treetop.SessionVariables.getSessionttiProfile(
								   request,response));
						   if (newElement.getLastUpdateUser() == null)
								newElement.setLastUpdateUser("TreeNet");
								comment28KeyValuesUpdate.add(newElement);		   
						}
					}
				}
				//********************************************
			}
			return;
		}

	/*
		 * Put information into the CORRECT AREA
		 *    Build Class for Added Comments's
		 *    Build Vector for Updated Comment's
		 *    Build Vector for Deleted Comments's
		 * 
		 *   Set into the correct field, and then dealt 
		 *   with in the Servlet
		 */
		public void populateComment29(
				HttpServletRequest request,
				HttpServletResponse response) {
			if (saveButton != null) {
				String eType = "";
				String key1 = "";
				String key2 = "";
				String key3 = "";
				String key4 = "";
				String key5 = "";
			
				if (!methodNumber.equals("") && 
					!revisionDate.equals("") &&
					!revisionTime.equals(""))
				{
					  eType = "MethodRevisionComment29";
					  key1 = methodNumber;
					  key2 = revisionDate;
					  key3 = revisionTime;
				}
				if (!specNumber.equals("") && 
					!revisionDate.equals("") &&
					!revisionTime.equals(""))
				{
					  eType = "SpecRevisionComment29";
					  key1 = specNumber;
					  key2 = revisionDate;
					  key3 = revisionTime;
				}
				//********************************************
				// ADD A NEW RECORD
				// 1/17/12 TWalton no longer needed using base viewbeanr2	
	//			if (comment29LongInformation.trim().equals(""))
	//			{
	//				this.setComment29LongInformation(request.getParameter("comment29LongInformation"));
	//				if (comment29LongInformation == null)
	//				   this.setComment29LongInformation("");
	//			}
				if (!comment29LongInformation.equals("")) {
					comment29KeyValuesAdd = new KeyValue();
					comment29KeyValuesAdd.setStatus("");
					comment29KeyValuesAdd.setEnvironment(this.getEnvironment().trim());
					comment29KeyValuesAdd.setEntryType(eType);
					comment29KeyValuesAdd.setSequence(this.getComment29Sequence().trim());
					comment29KeyValuesAdd.setKey1(key1);
					comment29KeyValuesAdd.setKey2(key2);
					comment29KeyValuesAdd.setKey3(key3);
					comment29KeyValuesAdd.setKey4(key4);
					comment29KeyValuesAdd.setKey5(key5);
					// Unique Key is loaded in the Add method of the Service
					comment29KeyValuesAdd.setValue(this.comment29LongInformation.trim());
	//				 5/11/11 -- Added information for apostrophe
	//				10/5/11 -- took out the apostrophe test information using a prepared statement on the insert and update
					//comment29KeyValuesAdd.setValue(FindAndReplace.replaceApostrophe(this.comment29LongInformation.trim()));
					comment29KeyValuesAdd.setDescription("");
					// Last Update Date & Time will be filled in by the Service
					comment29KeyValuesAdd.setLastUpdateUser(
						com.treetop.SessionVariables.getSessionttiProfile(
							request,response));
					if (comment29KeyValuesAdd.getLastUpdateUser() == null)
						comment29KeyValuesAdd.setLastUpdateUser("TreeNet");
						comment29KeyValuesAdd.setDeleteUser("");
					}
					//********************************************
					// BUILD UPDATE AND DELETE VECTORS
					int countOLD = 0;
					try {
						countOLD = new Integer(comment29KeyValuesCount).intValue();
					} catch (Exception e) {}
					if (countOLD > 0) {
						comment29KeyValuesDelete = new Vector();
						comment29KeyValuesUpdate = new Vector();
						for (int x = 0; x < countOLD; x++) {
							KeyValue newElement = new KeyValue();
							newElement.setStatus(request.getParameter("comment29Status" + x));
							newElement.setEnvironment(this.getEnvironment().trim());
							newElement.setEntryType(eType);
							newElement.setSequence(request.getParameter("comment29Sequence" + x));
							newElement.setKey1(key1);
							newElement.setKey2(key2);
							newElement.setKey3(key3);
							newElement.setKey4(key4);
							newElement.setKey5(key5);
							newElement.setUniqueKey(request.getParameter("comment29UniqueNumber" + x));//Unique Field
							newElement.setValue(request.getParameter("comment29LongInformation" + x));
							if (newElement.getValue().trim().equals("") &&
								request.getParameter("comment29LongInformationOld" + x) != null &&
							    !request.getParameter("comment29LongInformationOld" + x).equals(""))
							   newElement.setValue(request.getParameter("comment29LongInformationOld" + x));
							newElement.setDescription("");
							newElement.setDeleteDate(request.getParameter("comment29DeleteDate" + x));
							newElement.setDeleteTime(request.getParameter("comment29DeleteTime" + x));
							newElement.setDeleteUser(request.getParameter("comment29DeleteUser" + x));
										
							// DELETE RECORD	
							if (request.getParameter("comment29Delete" + x) != null)
							{
								newElement.setDeleteUser(
									com.treetop.SessionVariables.getSessionttiProfile(
											request,response));
								comment29KeyValuesDelete.add(newElement);
							}   
							else // Update RECORD
							{
							   newElement.setLastUpdateUser(
								   com.treetop.SessionVariables.getSessionttiProfile(
									   request,response));
							   if (newElement.getLastUpdateUser() == null)
									newElement.setLastUpdateUser("TreeNet");
							   comment29KeyValuesUpdate.add(newElement);		   
							}
						}
					}
					//********************************************
			}
			return;
		}

		public Vector getComment25KeyValuesDelete() {
			return comment25KeyValuesDelete;
		}

		public void setComment25KeyValuesDelete(Vector comment25KeyValuesDelete) {
			this.comment25KeyValuesDelete = comment25KeyValuesDelete;
		}

		public Vector getComment26KeyValuesDelete() {
			return comment26KeyValuesDelete;
		}

		public void setComment26KeyValuesDelete(Vector comment26KeyValuesDelete) {
			this.comment26KeyValuesDelete = comment26KeyValuesDelete;
		}

		public Vector getComment27KeyValuesDelete() {
			return comment27KeyValuesDelete;
		}

		public void setComment27KeyValuesDelete(Vector comment27KeyValuesDelete) {
			this.comment27KeyValuesDelete = comment27KeyValuesDelete;
		}

		public Vector getComment28KeyValuesDelete() {
			return comment28KeyValuesDelete;
		}

		public void setComment28KeyValuesDelete(Vector comment28KeyValuesDelete) {
			this.comment28KeyValuesDelete = comment28KeyValuesDelete;
		}

		public Vector getComment29KeyValuesDelete() {
			return comment29KeyValuesDelete;
		}

		public void setComment29KeyValuesDelete(Vector comment29KeyValuesDelete) {
			this.comment29KeyValuesDelete = comment29KeyValuesDelete;
		}

		public KeyValue getComment25KeyValuesAdd() {
			return comment25KeyValuesAdd;
		}

		public void setComment25KeyValuesAdd(KeyValue comment25KeyValuesAdd) {
			this.comment25KeyValuesAdd = comment25KeyValuesAdd;
		}

		public KeyValue getComment26KeyValuesAdd() {
			return comment26KeyValuesAdd;
		}

		public void setComment26KeyValuesAdd(KeyValue comment26KeyValuesAdd) {
			this.comment26KeyValuesAdd = comment26KeyValuesAdd;
		}

		public KeyValue getComment27KeyValuesAdd() {
			return comment27KeyValuesAdd;
		}

		public void setComment27KeyValuesAdd(KeyValue comment27KeyValuesAdd) {
			this.comment27KeyValuesAdd = comment27KeyValuesAdd;
		}

		public KeyValue getComment28KeyValuesAdd() {
			return comment28KeyValuesAdd;
		}

		public void setComment28KeyValuesAdd(KeyValue comment28KeyValuesAdd) {
			this.comment28KeyValuesAdd = comment28KeyValuesAdd;
		}

		public KeyValue getComment29KeyValuesAdd() {
			return comment29KeyValuesAdd;
		}

		public void setComment29KeyValuesAdd(KeyValue comment29KeyValuesAdd) {
			this.comment29KeyValuesAdd = comment29KeyValuesAdd;
		}

		public Vector getComment25KeyValuesUpdate() {
			return comment25KeyValuesUpdate;
		}

		public void setComment25KeyValuesUpdate(Vector comment25KeyValuesUpdate) {
			this.comment25KeyValuesUpdate = comment25KeyValuesUpdate;
		}

		public Vector getComment26KeyValuesUpdate() {
			return comment26KeyValuesUpdate;
		}

		public void setComment26KeyValuesUpdate(Vector comment26KeyValuesUpdate) {
			this.comment26KeyValuesUpdate = comment26KeyValuesUpdate;
		}

		public Vector getComment27KeyValuesUpdate() {
			return comment27KeyValuesUpdate;
		}

		public void setComment27KeyValuesUpdate(Vector comment27KeyValuesUpdate) {
			this.comment27KeyValuesUpdate = comment27KeyValuesUpdate;
		}

		public Vector getComment28KeyValuesUpdate() {
			return comment28KeyValuesUpdate;
		}

		public void setComment28KeyValuesUpdate(Vector comment28KeyValuesUpdate) {
			this.comment28KeyValuesUpdate = comment28KeyValuesUpdate;
		}

		public Vector getComment29KeyValuesUpdate() {
			return comment29KeyValuesUpdate;
		}

		public void setComment29KeyValuesUpdate(Vector comment29KeyValuesUpdate) {
			this.comment29KeyValuesUpdate = comment29KeyValuesUpdate;
		}

		public String getSubmit() {
			return submit;
		}

		public void setSubmit(String submit) {
			this.submit = submit;
		}
}
