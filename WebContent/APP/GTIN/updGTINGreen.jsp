<%@ page import="com.treetop.utilities.html.*"%>
<%@ page import="com.treetop.app.gtin.*"%>
<%@ page import="java.util.*"%>
<%//---------------  updGTINGreen.jsp  ------------------------------------------//
  //   General Setup
	//   To Be included in the updGTIN Page
//
//   Author :  Teri Walton  9/8/05   
//   Changes:
//    Date        Name      Comments
//  ---------   --------   -------------
//  5/29/08   TWalton     Changed Stylesheet to NEW Look			
//-----------------------------------------------------------------------//
//********************************************************************
//********************************************************************
//  String errorPage = "/GTIN/updGTINGreen.jsp";
// Bring in the Detail View Bean.
// For this page the view bean could include.
//      defaulted values for fields
//		 drop down lists
	UpdGTIN updGTINGreen = new UpdGTIN();
	try {
		updGTINGreen = (UpdGTIN) request.getAttribute("updViewBean");
	} catch (Exception e) {
	// Turn on IF BIG Problem, generally will catch problem in Main JSP
	//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPageTable, e.toString()));
	//	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPageTable));
	}

	String[] canUpdateGreen = (String[]) request.getAttribute("canUpdate");
	String roGreen = "Y";
	if ((canUpdateGreen[0].equals("Y") || canUpdateGreen[1].equals("Y")) && updGTINGreen.getPublishToUCCNet().equals(""))
		roGreen = "N";
	int icGreen = ((Integer) request.getAttribute("imageCount"))
			.intValue();
	String mouseoverHelp = "Click here to see help documents.";
%>
<html>
<head>
</head>
<body>
<table class="table00" cellspacing="0" cellpadding="2">
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Publish to UCCNet"
			target="_blank" title="<%= mouseoverHelp %>">Load to UCCNet:</a></td>
		<td class="td04140102">&nbsp; 
<%  String yesNoUpdateReadOnly = "Y";
    if (canUpdateGreen[0].equals("Y"))
       yesNoUpdateReadOnly = "N";
%>		
		<%=HTMLHelpersInput.dropDownYesNo("publishToUCCNet",
							updGTINGreen.getPublishToUCCNet(), "Pending", yesNoUpdateReadOnly)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
<%
   if (updGTINGreen.getRequestType().trim().equals("update"))
   {
%>	
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><acronym title="Choose how this GTIN will be published, either Added or Modified">
		  How Published to UCCNet:</acronym></td>
		<td class="td04140102">&nbsp; 
		  <%=UpdGTIN.buildDropDown("publishTypeToUCCNet", updGTINGreen.getPublishTypeToUCCNet().trim(), yesNoUpdateReadOnly)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
<%
   }
%>	
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/EANUCC_Type"
			target="_blank" title="<%= mouseoverHelp %>">EANUCC Type:</a></td>
		<td class="td04140102">&nbsp; <%=HTMLHelpersInput.inputBoxHidden("eanUCCType",
							updGTINGreen.getEanUCCType())%>
		<%=updGTINGreen.getEanUCCType()%> 
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Additional_Trade_Item_Description"
			target="_blank" title="<%= mouseoverHelp %>">Additional Description:</a></td>
<%
   String atid = updGTINGreen.getAdditionalTradeItemDescription().trim();
   if (atid.equals("") )
      atid = updGTINGreen.getFunctionalName(); 
%>		
		<td class="td04140102">&nbsp; <%=HTMLHelpersInput.inputBoxText(
							"additionalTradeItemDescription", atid,
							"Additional Trade Item Description", 30, 30, "",
							roGreen)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Functional_Name"
			target="_blank" title="<%= mouseoverHelp %>">Functional Name:</a></td>
		<td class="td04140102">&nbsp; <%=UpdGTIN.buildDropDown("functionalName", updGTINGreen
							.getFunctionalName().trim(), roGreen)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Brand_Owner"
			target="_blank" title="<%= mouseoverHelp %>">Brand Owner:</a></td>
		<td class="td04140102">&nbsp; <%=HTMLHelpersInput.inputBoxText("informationProvider",
							updGTINGreen.getInformationProvider(),
							"Brand Owner", 13, 13, "", roGreen)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/EANUCC_Code"
			target="_blank" title="<%= mouseoverHelp %>">EANUCC Code:</a></td>
<%
//  11/3/11 TWalton - Change to use same Strategy as Label and Case UPC Codes
%>			
		<td class="td04140102">&nbsp; 
		 <%= HTMLHelpersInput.inputBoxText("eanUCCCodeA", updGTINGreen.getEanUCCCodeA(), "EAN UCC Code", 1, 1, "N", roGreen) %>&nbsp;
         <%= HTMLHelpersInput.inputBoxText("eanUCCCodeB", updGTINGreen.getEanUCCCodeB(), "EAN UCC Code", 5, 5, "N", roGreen) %>&nbsp;		
         <%= HTMLHelpersInput.inputBoxText("eanUCCCodeC", updGTINGreen.getEanUCCCodeC(), "EAN UCC Code", 5, 5, "N", roGreen) %>&nbsp;			
         <%= HTMLHelpersInput.inputBoxText("eanUCCCodeD", updGTINGreen.getEanUCCCodeD(), "EAN UCC Code", 1, 1, "N", "Y") %>	
		 <%=updGTINGreen.getEanUCCCodeError()%></td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Short_Description"
			target="_blank" title="<%= mouseoverHelp %>">Short Description:</a></td>
		<td class="td04140102">&nbsp; <%=UpdGTIN.buildDropDown("shortDescription", updGTINGreen
							.getShortDescription().trim(), roGreen)%>
<%
%>							
		<font style="color:#990000">* Required</font></td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Long_Description"
			target="_blank" title="<%= mouseoverHelp %>">GTIN Name:</a></td>
		<td class="td04140102">&nbsp; <%=HTMLHelpersInput.inputBoxText("longDescription",
							updGTINGreen.getLongDescription().trim(),
							"GTIN Name", 60, 100, "", roGreen)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Brand_Name"
			target="_blank" title="<%= mouseoverHelp %>">Brand Name:</a></td>
		<td class="td04140102">&nbsp; <%=UpdGTIN.buildDropDown("brandName", updGTINGreen
							.getBrandName().trim(), roGreen)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Brand_Owner_Name"
			target="_blank" title="<%= mouseoverHelp %>">Brand Owner Name:</a></td>
		<td class="td04140102">&nbsp; <%=HTMLHelpersInput.inputBoxText("informationProviderName",
							updGTINGreen.getInformationProviderName(),
							"Information Provider Name", 30, 30, "", roGreen)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102" style="width:2%">&nbsp;</td>
		<td class="td04140102" style="width:30%"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Trade_Item_Unit_Descriptor"
			target="_blank" title="<%= mouseoverHelp %>">Product Type:</a></td>
		<td class="td04140102">&nbsp; <%=UpdGTIN.buildDropDown("tiud", updGTINGreen.getTiud()
							.trim(), roGreen)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Quantity_Of_Complete_Layers_Contained_In_A_Trade_Item"
			target="_blank" title="<%= mouseoverHelp %>">Quantity Of Complete
		Layers Contained In A Trade Item (HI):</a></td>
		<td class="td04140102">&nbsp; <%=HTMLHelpersInput.inputBoxNumber("qtyCompleteLayers",
							updGTINGreen.getQtyCompleteLayers(),
							"Quantity of Layers Contained in Trade Item", 3, 3,
							"", roGreen)%>
		<%=updGTINGreen.getQtyCompleteLayersError()%></td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Total_Quantity_Of_Next_Lower_Level_Trade_Item"
			target="_blank" title="<%= mouseoverHelp %>">Total Quantity Of Next
		Lower Level Trade Item:</a></td>
		<td class="td04140102">&nbsp; <%=HTMLHelpersInput.inputBoxNumber(
							"qtyNextLowerLevelTradeItem", updGTINGreen
									.getQtyNextLowerLevelTradeItem(),
							"Quantity of Next Lower Level Trade Item", 6, 6,
							"", roGreen)%>
		<%=updGTINGreen.getQtyNextLowerLevelTradeItemError()%></td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Quantity_Of_Trade_ITems_Contained_In_A_Complete_Layer"
			target="_blank" title="<%= mouseoverHelp %>">Quantity Of Trade Items
		Contained In a Complete Layer (TI):</a></td>
		<td class="td04140102">&nbsp; <%=HTMLHelpersInput.inputBoxNumber(
							"qtyItemsPerCompleteLayer", updGTINGreen
									.getQtyItemsPerCompleteLayer(),
							"Quantity of Items in Completed Layer", 3, 3, "",
							roGreen)%>
		<%=updGTINGreen.getQtyItemsPerCompleteLayerError()%></td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Quantity_Of_Children"
			target="_blank" title="<%= mouseoverHelp %>">Quantity Of Children:</a>
		</td>
		<td class="td04140102">&nbsp; <%=HTMLHelpersInput.inputBoxNumber("qtyChildren",
							updGTINGreen.getQtyChildren(),
							"Quantity of Children", 3, 3, "", roGreen)%>
         <%= updGTINGreen.getQtyChildrenError() %>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102" style="width:2%">&nbsp;</td>
		<td class="td04140102" style="width:30%"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_A_Consumer_Unit"
			target="_blank" title="<%= mouseoverHelp %>">Is Trade Item A Consumer
		Unit:</a></td>
		<td class="td04140102">&nbsp; <%=UpdGTIN.buildRadioTrueFalse("isConsumerUnit",
							updGTINGreen.getIsConsumerUnit(), roGreen)%>
		<%=updGTINGreen.getIsConsumerUnitError()%></td>
		<td class="td04140102" style="width:2%">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_A_Dispatch_Unit"
			target="_blank" title="<%= mouseoverHelp %>">Is Trade Item A Dispatch
		Unit:</a></td>
		<td class="td04140102">&nbsp; <%=UpdGTIN.buildRadioTrueFalse("isDispatchUnit",
							updGTINGreen.getIsDispatchUnit(), roGreen)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_An_Orderable_Unit"
			target="_blank" title="<%= mouseoverHelp %>">Is Trade Item An
		Orderable Unit:</a></td>
		<td class="td04140102">&nbsp; <%=UpdGTIN.buildRadioTrueFalse("isOrderableUnit",
							updGTINGreen.getIsOrderableUnit(), roGreen)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_An_Invoice_Unit"
			target="_blank" title="<%= mouseoverHelp %>">Is Trade Item An Invoice
		Unit:</a></td>
		<td class="td04140102">&nbsp;' <%=UpdGTIN.buildRadioTrueFalse("isInvoiceUnit", updGTINGreen
							.getIsInvoiceUnit(), roGreen)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Packaging_Marked_As_Returnable"
			target="_blank" title="<%= mouseoverHelp %>">Is Packaging Marked As
		Returnable:</a></td>
		<td class="td04140102">&nbsp; <%=UpdGTIN.buildRadioTrueFalse("isReturnable", "F", "Y")%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Non_Sold_Trade_Item_Returnable"
			target="_blank" title="<%= mouseoverHelp %>">Is Non Sold Trade Item
		Returnable:</a></td>
		<td class="td04140102">&nbsp; <%=UpdGTIN.buildRadioTrueFalse("isNonSoldReturnable", "F", "Y")%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_A_Base_Unit"
			target="_blank" title="<%= mouseoverHelp %>">Is Trade Item A Base
		Unit:</a></td>
		<td class="td04140102">&nbsp; <%=UpdGTIN.buildRadioTrueFalse("isBaseUnit", updGTINGreen
							.getIsBaseUnit(), roGreen)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_A_Variable_Unit"
			target="_blank" title="<%= mouseoverHelp %>">Is Trade Item A Variable
		Unit:</a></td>
		<td class="td04140102">&nbsp; <%=UpdGTIN.buildRadioTrueFalse("isVariableUnit", "F", "Y")%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">
	  <a class="a0412" href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Packaging_Marked_As_Recyclable" 
	     target="_blank" title="<%= mouseoverHelp %>">Is Packaging Marked As Recyclable:</a>
   </td>
   <td class="td04140102">&nbsp;
     <%= UpdGTIN.buildRadioTrueFalse("isRecyclable", updGTINGreen.getIsRecyclable(), roGreen) %> 
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>   
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Is_Trade_Item_Information_Private"
			target="_blank" title="<%= mouseoverHelp %>">Is Trade Item
		Information Private:</a></td>
		<td class="td04140102">&nbsp; <%=UpdGTIN.buildRadioTrueFalse("isInformationPrivate", "T", "Y")%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
<%
  if (0 == 1)
  { // Hide, No longer use this field
%>	
	<tr>
		<td class="td04140102" style="width:2%">&nbsp;</td>
		<td class="td04140102" style="width:30%"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Delivery_Method_Indicator"
			target="_blank" title="<%= mouseoverHelp %>">Delivery Method
		Indicator:</a></td>
		<td class="td04140102">&nbsp; <%=HTMLHelpersInput.inputBoxText("deliveryMethodIndicator",
							updGTINGreen.getDeliveryMethodIndicator(),
							"Delivery Method Indicator", 3, 3, "", roGreen)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
<%
  }
%>	
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Effective_Date"
			target="_blank" title="<%= mouseoverHelp %>">Effective Date:</a></td>
		<td class="td04140102">&nbsp; 
<%
   if (!roGreen.equals("Y"))
		icGreen++;
%> 
<%=HTMLHelpersInput.inputBoxDate((String) request
							.getAttribute("date1Name"), updGTINGreen
							.getEffectiveDate(), (String) request
							.getAttribute("date1Function"), "N", roGreen)%>
		<%=updGTINGreen.getEffectiveDateError()%></td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Trade_Item_Publication_Date"
			target="_blank" title="<%= mouseoverHelp %>">Trade Item Publication
		Date:</a></td>
		<td class="td04140102">&nbsp; <%if (!roGreen.equals("Y"))
				icGreen++;
%> <%=HTMLHelpersInput.inputBoxDate((String) request
							.getAttribute("date2Name"), updGTINGreen
							.getPublicationDate(), (String) request
							.getAttribute("date2Function"), "N", roGreen)%>
		<%=updGTINGreen.getPublicationDateError()%></td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102">&nbsp;</td>
		<td class="td04140102"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Trade_Item_Start_Availability_Date"
			target="_blank" title="<%= mouseoverHelp %>">Trade Item Start
		Availability Date:</a></td>
		<td class="td04140102">&nbsp; <%if (!roGreen.equals("Y"))
				icGreen++;
%> <%=HTMLHelpersInput.inputBoxDate((String) request
							.getAttribute("date4Name"), updGTINGreen
							.getStartAvailabilityDate(), (String) request
							.getAttribute("date4Function"), "N", roGreen)%>
		<%=updGTINGreen.getStartAvailabilityDateError()%></td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102" style="width:2%">&nbsp;</td>
		<td class="td04140102" style="width:30%"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Catalogue_Item_State"
			target="_blank" title="<%= mouseoverHelp %>">Catalogue Item State:</a>
		</td>
		<td class="td04140102">&nbsp; <%=UpdGTIN.buildDropDown("catalogueItemState", updGTINGreen
							.getCatalogueItemState().trim(), roGreen)%>
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>
	<tr>
		<td class="td04140102" style="width:2%">&nbsp;</td>
		<td class="td04140102" style="width:30%"><a class="a0412"
			href="/web/CtlKeyValues?requestType=IFSonly&folderPath=GTIN/Field Help/Target_Market_Code"
			target="_blank" title="<%= mouseoverHelp %>">Target Market</a>
		</td>
		<td class="td04140102">&nbsp; <%=HTMLHelpersInput.inputBoxText("targetMarketCountryCode",
							updGTINGreen.getTargetMarketCountryCode(),
							"Target Market", 10, 10, "", roGreen)%>
<%= HTMLHelpersInput.inputBoxHidden("targetMarketCountryCode", updGTINGreen.getTargetMarketCountryCode()) %>							
		</td>
		<td class="td04140102">&nbsp;</td>
	</tr>	
</table>
<%request.setAttribute("imageCount", new Integer(icGreen));

		%>
</body>
</html>
