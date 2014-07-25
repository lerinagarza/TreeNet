/*
 * Created on September 21, 2009
 *
 *  Will use to search for any Transaction
 */

package com.treetop.app.transaction;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.businessobjectapplications.BeanTransaction;
import com.treetop.services.*;
import com.treetop.viewbeans.*;
import com.treetop.utilities.html.*;
import com.treetop.utilities.html.HtmlSelect.DescriptionType;
import com.treetop.utilities.*;
import com.treetop.businessobjects.*;

/**
 * @author twalto
 * 
 *         Use to search for Transactions 9/21/09 - Looking only for
 *         Transactions that Errored on the way to M3
 */
public class InqTransaction extends BaseViewBeanR1 {
	// Standard Fields - to be in Every View Bean
	public String requestType = "";
	public String displayMessage = "";

	public String orderBy = "transactionDate";
	public String orderStyle = "";
	public String startRecord = "";

	// Must have in Update View Bean
	public String updateUser = "";

	public String inqItem = "";
	public String inqItemError = "";

	public String inqFromDate = "";
	public String inqToDate = "";
	public String inqTransactionType = "";
	public String inqUser = "";

	public String inqLot = "";
	public String inqLotError = "";

	public String inqWhse = ""; // From Drop Down
	public String inqFacility = ""; // From Drop Down
	public String inqFacilityError = "";

	public String inqDate = "";
	public String inqDateYYYYMMDD = "";
	public String inqDateError = "";

	public String inqOrder = "";
	public String inqOrderError = "";

	public String inqManufactureLabel = "";
	public String inqManufactureLabelError = "";

	public String inqEnvironment = "";

	public String displayErrors = "";
	public String goButton = "";

	public String outQ = "";
	public String outQDescr = "";

	public String sendOutput = "";

	public Vector listReport = null;
	public BeanTransaction bean = new BeanTransaction();

	/*
	 * Test and Validate fields, after loading them. Set Errors into the Error
	 * Fields of this View Bean IF there are any.
	 */
	public void validate() {

		if (this.getRequestType().equals("listTransactionError")) {
			if (this.getInqEnvironment() == null || this.getInqEnvironment().trim().equals("")) {
				this.setInqEnvironment("PRD");
			}

		}

		if (this.getRequestType().equals("inqProductionDayForwardOS")) {
			String label = this.getInqManufactureLabel();
			int len = label.length();
			String errorMsg = "Please enter a valid Ocean Spary Manufacture Label (eg. P53000000700031013)";
			
			if (len != 18 || label.trim().equals("") || !label.toUpperCase().startsWith("P")) {
				
				this.setInqManufactureLabelError(errorMsg);
				
			} else {
				
				try {
					
					Double.parseDouble(label.substring(1,len));
					
				} catch (Exception e) {
					this.setInqManufactureLabelError(errorMsg);
				}
				
			}
			
			//blank out unused fields
			this.setInqFacility("");
			this.setInqDate("");
			this.setInqItem("");
			this.setInqLot("");
			this.setInqOrder("");
			
			//default the leading "P" to be capitalized
			this.setInqManufactureLabel(label.substring(0,1).toUpperCase() + label.substring(1,len));
			
			this.setDisplayErrors(this.getInqManufactureLabelError());
		}

		if (this.getRequestType().equals("inqSingleIngredientForward")) {
			if (!this.getInqItem().equals("")) {
				CommonRequestBean crb = new CommonRequestBean();
				crb.setEnvironment(this.getInqEnvironment());
				crb.setCompanyNumber("100");
				crb.setIdLevel1(this.getInqItem());
				try {
					this.setInqItemError(ServiceItem.verifyItem(crb));
				} catch (Exception e) {
					System.out.println("Error verifying item " + this.getInqItem());
				}
			} else {
				this.setInqItemError("Please enter an Item Number.  ");
			}

			if (!this.getInqLot().equals("")) {
				CommonRequestBean crb = new CommonRequestBean();
				crb.setEnvironment(this.getInqEnvironment());
				crb.setCompanyNumber("100");
				crb.setIdLevel1(this.getInqItem());
				crb.setIdLevel2(this.getInqLot());
				try {
					this.setInqLotError(ServiceLot.verifyM3LotNumber(crb));
				} catch (Exception e) {
					System.out.println("Error verifying Item:Lot " + this.getInqItem() + ":" + this.getInqLot());
				}
			} else {
				this.setInqLotError("You must select an valid lot number");
			}
			
			
			//blank out unused fields
			this.setInqFacility("");
			this.setInqDate("");
			this.setInqOrder("");

			if (!this.getInqItemError().equals("") || !this.getInqLotError().equals("")) {
				this.setDisplayErrors(this.getInqItemError() + this.getInqLotError());
			}
			if (!this.getInqItemError().equals("") && !this.getInqLotError().equals("")) {
				this.setDisplayErrors(this.getInqItemError() + "<br />" + this.getInqLotError());
			}
		}

		if (this.getRequestType().equals("inqProductionDayBack")
				|| this.getRequestType().equals("inqProductionDayForward")
				|| this.getRequestType().equals("inqFruitToShipping")) {

			if (this.getInqOrder().trim().equals("")) {
				// validate for facility, date, and order
				
				if (this.getInqFacility().equals("")) {
					this.setInqFacilityError("You must select a facility");
				}

				String inqItemError = "";
				if (this.getInqItem().trim().equals("")) {
					inqItemError = "You must enter an item number";
					this.setInqItemError(inqItemError);
				} else {
					CommonRequestBean crb = new CommonRequestBean();
					crb.setEnvironment(this.getInqEnvironment());
					crb.setCompanyNumber("100");
					crb.setIdLevel1(this.getInqItem());
					try {
						inqItemError = ServiceItem.verifyItem(crb);
					} catch (Exception e) {
						System.out.println("Error verifying item " + this.getInqItem());
					}
					this.setInqItemError(inqItemError);
				}

				String inqDateError = "";
				if (this.getInqDate().trim().equals("")) {
					inqDateError = "You must choose a date.";
					this.setInqDateError(inqDateError);
				} else {
					DateTime dateFormat = UtilityDateTime.getDateFromMMddyyWithSlash(this.getInqDate());
					inqDateError = dateFormat.getDateErrorMessage();
					this.setInqDateError(inqDateError);

					this.setInqDateYYYYMMDD(dateFormat.getDateFormatyyyyMMdd());

				}
				
				
				String error = "";
				if (!this.getInqFacilityError().equals("") || !inqDateError.equals("") || !inqItemError.equals("")) {
					error = this.getInqFacilityError() + "<br />" + inqDateError + "<br />" + inqItemError;
				}
				
				this.setDisplayErrors(error);

			} else {
				
				String inqOrderError = "";
				try {
					inqOrderError = ServiceManufacturingOrder.verifyOrderNumber(this.getInqEnvironment(),
							this.getInqOrder());
				} catch (Exception e) {
					System.out.println("Error verifying item " + this.getInqItem());
				}
				this.setInqOrderError(inqOrderError);
				this.setDisplayErrors(inqOrderError);
				
				//blank out the facility, date, and item number
				this.setInqFacility("");
				this.setInqDate("");
				this.setInqItem("");
				this.setInqLot("");

				// using order number, blank out date and item
				this.setInqDate("");
				this.setInqItem("");
			}
			
			//
			// String inqItemError = "";
			// String inqOrderError = "";
			// //Verify Item Number
			// if (this.getInqItem() == null)
			// this.setInqItem("");
			// if (!this.getInqItem().equals("")) {
			// CommonRequestBean crb = new CommonRequestBean();
			// crb.setEnvironment(this.getInqEnvironment());
			// crb.setCompanyNumber("100");
			// crb.setIdLevel1(this.getInqItem());
			// try {
			// inqItemError = ServiceItem.verifyItem(crb);
			// } catch (Exception e) {System.out.println("Error verifying item "
			// + this.getInqItem());}
			// }
			//
			// //Validate Manufacturing Order Number
			// if (this.getInqOrder() == null)
			// this.setInqOrder("");
			// if (!this.getInqOrder().equals("")) {
			// try {
			// inqOrderError =
			// ServiceManufacturingOrder.verifyOrderNumber(this.getInqEnvironment(),
			// this.getInqOrder());
			// } catch (Exception e) {System.out.println("Error verifying item "
			// + this.getInqItem());}
			// }
			//
			// //Set date as YYYYMMDD from MM/DD/YY
			// if (this.getInqDate() == null)
			// this.setInqDate("");
			// if (!this.getInqDate().equals("")) {
			// DateTime dateFormat =
			// UtilityDateTime.getDateFromMMddyyWithSlash(this.getInqDate());
			// this.setInqDateYYYYMMDD(dateFormat.getDateFormatyyyyMMdd());
			// }
			//
			// if (this.getInqOrder().equals("")) {
			// this.setInqItemError(inqItemError);
			// this.setDisplayErrors(inqItemError);
			// } else {
			// this.setInqOrderError(inqOrderError);
			// this.setDisplayErrors(inqOrderError);
			// }
			//
			// //No order or faci, date, item entered
			// if (this.getInqOrder().equals("") &&
			// this.getInqItem().equals("")) {
			// this.setDisplayErrors("You must choose a Facility, Date, and Item; OR enter a Manufacturing Order Number.  ");
			// }
			//
			// if (this.getDisplayErrors().trim().equals("") &&
			// !this.getInqOrder().trim().equals("")) {
			// //if there were no validation errors and an Order was entered,
			// blank out the date and item fields
			// //Order number wins in server over item and date
			// this.setInqDate("");
			// this.setInqItem("");
			// }
		}
		return;
	}
	
	
	
	public String buildSelectionCriteria() {
		SelectionCriteria sc = new SelectionCriteria();
		sc.addValue("Environment", this.getInqEnvironment());
		sc.addValue("User", this.getInqUser());
		sc.addValue("Warehouse", this.getInqWhse());
		sc.addValue("Item", this.getInqItem());
		sc.addValue("Lot", this.getInqLot());
		sc.addValue("Transaction Date From", this.getInqFromDate());
		sc.addValue("Transaction Date To", this.getInqToDate());
		return sc.toString();
	}
	
	public String buildOutputFileName() {
		StringBuffer fileName = new StringBuffer();
		
		if (this.getRequestType().equals("inqProductionDayForward")) {
			fileName.append("ProdToShipping");
		} else if (this.getRequestType().equals("inqProductionDayForwardOS")) {
			fileName.append("ProdToShippingOS");
		} else if (this.getRequestType().equals("inqProductionDayBack")) {
			fileName.append("ProdToIngredients");
		} else if (this.getRequestType().equals("inqSingleIngredientForward")) {
			fileName.append("ItemLotTrace");
		} else if (this.getRequestType().equals("inqFruitToShipping")) {
			fileName.append("FruitToShippingFS");
		}
		
		DateTime dt = UtilityDateTime.getSystemDate();
		fileName.append("_" + dt.getDateFormatyyyyMMdd());
		fileName.append("_" + dt.getTimeFormathhmmss());
		
		if (!this.getInqManufactureLabel().equals("")) {
			fileName.append("_MfgLbl-" + this.getInqManufactureLabel());
		}
		
		if (!this.getInqOrder().equals("")) {
			fileName.append("_MO-" + this.getInqOrder());
		}
		
		if (!this.getInqItem().equals("")) {
			fileName.append("_Item-" + this.getInqItem());
		}
		if (!this.getInqLot().equals("")) {
			fileName.append("_Lot-" + this.getInqLot());
		}
		
		if (!this.getInqDateYYYYMMDD().equals("")) {
			fileName.append("_StartDate-" + this.getInqDateYYYYMMDD());
		}
		
		if (!this.getInqFacility().equals("")) {
			fileName.append("_Facility-" + this.getInqFacility());
		}
		
		
		
		
		
		return fileName.toString();
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
	 * @param updateUser
	 *            The updateUser to set.
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
	 * @param displayMessage
	 *            The displayMessage to set.
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
	 * @param listReport
	 *            The listReport to set.
	 */
	public void setListReport(Vector listReport) {
		this.listReport = listReport;
	}

	/**
	 * @return Returns the displayErrors.
	 */
	public String getDisplayErrors() {
		return displayErrors;
	}

	/**
	 * @param displayErrors
	 *            The displayErrors to set.
	 */
	public void setDisplayErrors(String displayErrors) {
		this.displayErrors = displayErrors;
	}

	/**
	 * @return Returns the inqItem.
	 */
	public String getInqItem() {
		return inqItem;
	}

	/**
	 * @param inqItem
	 *            The inqItem to set.
	 */
	public void setInqItem(String inqItem) {
		this.inqItem = inqItem;
	}

	/**
	 * Method created 9/21/09 TWalton Will Return to the Screen,String which is
	 * the code for the drop down list
	 * 
	 */
	public static String buildDropDownTransactionType(String inType) {
		String dropDown = "";
		try {
			Vector buildList = new Vector();
			DropDownSingle dds = new DropDownSingle();
			dds.setDescription("Short Cut Manufacturing Transactions");
			dds.setValue("ME11");
			buildList.addElement(dds);
			// dds = new DropDownSingle();
			// dds.setDescription("Inspection(1) To Rejected(3)");
			// dds.setValue("13");
			// buildList1.addElement(dds);

			dropDown = DropDownSingle.buildDropDown(buildList, "inqTransactionType", inType, "None", "N", "N");

		} catch (Exception e) {
			// Catch any problems, and do not display them unless testing
			// System.out.println("Error" + e);
		}
		return dropDown;
	}

	/**
	 * @return Returns the inqWhse.
	 */
	public String getInqWhse() {
		return inqWhse;
	}

	/**
	 * @param inqWhse
	 *            The inqWhse to set.
	 */
	public void setInqWhse(String inqWhse) {
		this.inqWhse = inqWhse;
	}

	/*
	 * Use this method to Display -- however appropriate for the JSP the
	 * Parameters Chosen
	 */
	public String buildParameterDisplay() {
		StringBuffer returnString = new StringBuffer();
		returnString.append("");
		if (!inqEnvironment.equals("")) {
			returnString.append("Environment: ");
			returnString.append(inqEnvironment);
		}
		if (!inqItem.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Item Number: ");
			returnString.append(inqItem);
		}
		if (!inqTransactionType.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Transaction Type: ");
			returnString.append(inqTransactionType);
		}
		if (!inqUser.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("User: ");
			returnString.append(inqUser);
		}
		if (!inqLot.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Lot: ");
			returnString.append(inqLot);
		}
		if (!inqWhse.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Warehouse: ");
			returnString.append(inqWhse);
		}
		if (!inqFacility.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Facility: ");
			returnString.append(inqFacility);
		}
		if (!inqFromDate.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("From Transaction Date: ");
			returnString.append(inqFromDate);
		}
		if (!inqToDate.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("To Transaction Date: ");
			returnString.append(inqToDate);
		}
		if (!inqDate.equals("")) {
			if (!returnString.toString().equals(""))
				returnString.append("<br>");
			returnString.append("Inquiry Date: ");
			returnString.append(inqDate);
		}

		return returnString.toString();
	}

	/**
	 * This method should be in EVERY Inquiry View Bean Will return a String to
	 * Resend the parameters Mainly from the link on the heading Sort, OR if
	 * there is another list view.
	 * 
	 * @return
	 */
	public String buildParameterResend() {
		StringBuffer returnString = new StringBuffer();
		if (!inqEnvironment.equals("")) {// Environment
			returnString.append("&inqEnvironment=");
			returnString.append(inqEnvironment);
		}
		if (!inqItem.equals("")) {// Item Number
			returnString.append("&inqItem=");
			returnString.append(inqItem);
		}
		if (!inqTransactionType.equals("")) {
			returnString.append("&inqTransactionType=");
			returnString.append(inqTransactionType);
		}
		if (!inqUser.equals("")) {
			returnString.append("&inqUser=");
			returnString.append(inqUser);
		}
		if (!inqLot.equals("")) {// Lot
			returnString.append("&inqLot=");
			returnString.append(inqLot);
		}
		if (!inqWhse.equals("")) {// Warehouse
			returnString.append("&inqWhse=");
			returnString.append(inqWhse);
		}
		if (!inqFacility.equals("")) {// Warehouse
			returnString.append("&inqFacility=");
			returnString.append(inqFacility);
		}
		if (!inqFromDate.equals("")) {
			returnString.append("&inqFromDate=");
			returnString.append(inqFromDate);
		}
		if (!inqToDate.equals("")) {
			returnString.append("&inqToDate=");
			returnString.append(inqToDate);
		}
		if (!inqDate.equals("")) {
			returnString.append("&inqDate=");
			returnString.append(inqDate);
		}
		return returnString.toString();
	}

	/**
	 * Will Return to the Screen, a Drop Down Vector Which will allow a Drop
	 * Down on the Screen for then getting the Appropriate Users
	 */
	public static String buildDropDownUser(String environment, String inUser) {
		String dd = new String();
		try {
			Vector sentValues = new Vector();
			sentValues.addElement("ddShortCut");
			Vector getRecords = ServiceTransactionError.dropDownUser(environment, sentValues);

			// Call the Build utility to build the actual code for the drop
			// down.
			dd = DropDownSingle.buildDropDown(getRecords, "inqUser", inUser, "All Valid Users", "E", "N");
		} catch (Exception e) {
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}

	public String getInqFromDate() {
		return inqFromDate;
	}

	public void setInqFromDate(String inqFromDate) {
		this.inqFromDate = inqFromDate;
	}

	public String getInqLot() {
		return inqLot;
	}

	public void setInqLot(String inqLot) {
		this.inqLot = inqLot;
	}

	public String getInqToDate() {
		return inqToDate;
	}

	public void setInqToDate(String inqToDate) {
		this.inqToDate = inqToDate;
	}

	public String getInqTransactionType() {
		return inqTransactionType;
	}

	public void setInqTransactionType(String inqTransactionType) {
		this.inqTransactionType = inqTransactionType;
	}

	public String getInqUser() {
		return inqUser;
	}

	public void setInqUser(String inqUser) {
		this.inqUser = inqUser;
	}

	public String getInqEnvironment() {
		return inqEnvironment;
	}

	public void setInqEnvironment(String inqEnvironment) {
		this.inqEnvironment = inqEnvironment;
	}

	public BeanTransaction getBean() {
		return bean;
	}

	public void setBean(BeanTransaction bean) {
		this.bean = bean;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderStyle() {
		return orderStyle;
	}

	public void setOrderStyle(String orderStyle) {
		this.orderStyle = orderStyle;
	}

	public String getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(String startRecord) {
		this.startRecord = startRecord;
	}

	/**
	 * Will Return to the Screen, a Drop Down Vector Which will allow a Drop
	 * Down on the Screen for then getting the Appropriate Users
	 */
	public static String buildDropDownWarehouse(String environment, String inWhse) {
		String dd = new String();
		try {
			Vector sentValues = new Vector();
			sentValues.addElement("ddShortCut");
			Vector getRecords = ServiceTransactionError.dropDownWarehouse(environment, sentValues);

			// Call the Build utility to build the actual code for the drop
			// down.
			dd = DropDownSingle.buildDropDown(getRecords, "inqWhse", inWhse, "All", "B", "N");
		} catch (Exception e) {
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}

	/**
	 * Will Return to the Screen, a Drop Down Vector Which will allow a Drop
	 * Down on the Screen for then getting the Appropriate Users
	 */
	public static String buildDropDownFacility(String environment, String inFacility) {
		String dd = new String();
		try {
			Vector sentValues = new Vector();
			sentValues.addElement("ddShortCut");
			Vector getRecords = ServiceTransaction.dropDownFacility(environment, sentValues);
			
			// Call the Build utility to build the actual code for the drop
			// down.
			dd = DropDownSingle.buildDropDown(getRecords, "inqFacility", inFacility, "none", "B", "N", "");
		} catch (Exception e) {
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}
	
	/**
	 * Will Return to the Screen, a Drop Down Vector Which will allow a Drop
	 * Down on the Screen for then getting the Appropriate Users
	 */
	public static String buildDropDownFacilityTrackAndTrace(String environment, String inFacility) {
		String dd = new String();
		try {
			Vector sentValues = new Vector();
			sentValues.addElement("ddShortCut");
			Vector getRecords = ServiceTransaction.dropDownFacility(environment, sentValues);

			Vector<HtmlOption> options = new Vector<HtmlOption>();
			for (int i=0; i<getRecords.size(); i++) {
				DropDownSingle dds = (DropDownSingle) getRecords.elementAt(i);
				HtmlOption option = new HtmlOption(dds);
				options.addElement(option);
			}
			
			HtmlSelect select = new HtmlSelect("inqFacility", "", "", inFacility, "", false, DescriptionType.VALUE_DESCRIPTION);
			
			dd = DropDownSingle.buildDropDown(select, options);
			

		} catch (Exception e) {
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}

	/**
	 * Will Return to the Screen, a Drop Down Vector Which will allow a Drop
	 * Down on the Screen for then getting the Appropriate Users
	 */
	public static String buildDropDownItem(String environment, String inItem) {
		String dd = new String();
		try {
			Vector sentValues = new Vector();
			sentValues.addElement("ddShortCut");
			Vector getRecords = ServiceTransactionError.dropDownItem(environment, sentValues);

			// Call the Build utility to build the actual code for the drop
			// down.
			dd = DropDownSingle.buildDropDown(getRecords, "inqItem", inItem, "", "B", "N");
		} catch (Exception e) {
			// Catch any problems, and do not display them unless testing
		}
		return dd;
	}

	public String getGoButton() {
		return goButton;
	}

	public void setGoButton(String goButton) {
		this.goButton = goButton;
	}

	public String getInqItemError() {
		return inqItemError;
	}

	public void setInqItemError(String inqItemError) {
		this.inqItemError = inqItemError;
	}

	public String getInqLotError() {
		return inqLotError;
	}

	public void setInqLotError(String inqLotError) {
		this.inqLotError = inqLotError;
	}

	public String getInqFacility() {
		return inqFacility;
	}

	public void setInqFacility(String inqFacility) {
		this.inqFacility = inqFacility;
	}

	public String getInqDate() {
		return inqDate;
	}

	public void setInqDate(String inqDate) {
		this.inqDate = inqDate;
	}

	public String getInqDateYYYYMMDD() {
		return inqDateYYYYMMDD;
	}

	public void setInqDateYYYYMMDD(String inqDateYYYYMMDD) {
		this.inqDateYYYYMMDD = inqDateYYYYMMDD;
	}

	public String getOutQ() {
		return outQ;
	}

	public void setOutQ(String outQ) {
		this.outQ = outQ;
	}

	public String getOutQDescr() {
		return outQDescr;
	}

	public void setOutQDescr(String outQDescr) {
		this.outQDescr = outQDescr;
	}

	public String getInqOrder() {
		return inqOrder;
	}

	public void setInqOrder(String inqOrder) {
		this.inqOrder = inqOrder;
	}

	public String getInqOrderError() {
		return inqOrderError;
	}

	public void setInqOrderError(String inqOrderError) {
		this.inqOrderError = inqOrderError;
	}

	public String getSendOutput() {
		return sendOutput;
	}

	public void setSendOutput(String sendOutput) {
		this.sendOutput = sendOutput;
	}

	public String getInqDateError() {
		return inqDateError;
	}

	public void setInqDateError(String inqDateError) {
		this.inqDateError = inqDateError;
	}

	public String getInqManufactureLabel() {
		return inqManufactureLabel;
	}

	public void setInqManufactureLabel(String inqManufactureLabel) {
		this.inqManufactureLabel = inqManufactureLabel;
	}

	public String getInqManufactureLabelError() {
		return inqManufactureLabelError;
	}

	public void setInqManufactureLabelError(String inqManufactureLabelError) {
		this.inqManufactureLabelError = inqManufactureLabelError;
	}



	public String getInqFacilityError() {
		return inqFacilityError;
	}



	public void setInqFacilityError(String inqFacilityError) {
		this.inqFacilityError = inqFacilityError;
	}

}
