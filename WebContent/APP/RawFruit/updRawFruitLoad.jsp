<%@ page language="java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- updRawFruitLoad.jsp -------------------------------------------//
//
//    Author :  Teri Walton  11/05/08
//   CHANGES:
//
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
  String errorPage = "/RawFruit/updRawFruitLoad.jsp";
  String updTitle = " Update a Raw Fruit Load";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 UpdRawFruitLoad ur = new UpdRawFruitLoad();
 Supplier carrier = new Supplier();
 RawFruitLoad rfl = new RawFruitLoad();
 try
 {
	ur = (UpdRawFruitLoad) request.getAttribute("updViewBean");
	carrier = ur.getUpdBean().getRfLoad().getCarrier();
	rfl = ur.getUpdBean().getRfLoad();
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",updTitle);
   StringBuffer setExtraOptions = new StringBuffer();
   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=update\">Add a NEW Load");
   setExtraOptions.append("<option value=\"/web/CtlRawFruit?requestType=inqScaleTicket\">Search for a Load (to update)");
   request.setAttribute("extraOptions", setExtraOptions.toString());       
//*****************************************************************************   
String readOnlyLoad = "N";

%>
<html>
 <head>
   <title><%= updTitle %></title>
   <%= JavascriptInfo.getExpandingSectionHead("Y", 10, "Y", 10) %>   
   <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getRequiredField() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>   
   <%= JavascriptInfo.getCalendarHead() %>  
   <%= JavascriptInfo.getMoreButton() %>
 </head>
 <body>
   <jsp:include page="../../Include/heading.jsp"></jsp:include>
 <table class="table00" cellspacing="0" cellpadding="2">
 <tr>
   <td style="width:2%">&nbsp;</td>
   <td class="td0414" style="text-align:right">  
     <%= UpdRawFruitLoad.buildReportButton(ur.getScaleTicket(), ur.getScaleTicketCorrectionSequence(), "") %>
   </td>
   <td style="width:2%">&nbsp;</td>
  </tr>
 </table> 
<hr>  
 <form name="updateLoad" action="/web/CtlRawFruit?requestType=update" method="post">   
 <table class="table00" cellspacing="0" cellpadding="2">
 <%
   if (!ur.getDisplayMessage().trim().equals(""))
   {
%>      
       <tr class="tr00">
        <td class="td03200102">&nbsp;</td>
        <td class="td03200102" colspan = "4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <b><%= ur.getDisplayMessage().trim() %></b></td>
        <td class="td03200102">&nbsp;</td>
       </tr>    
<%
   }
%>    
  <tr>
   <td class="td04160102" style="width:2%">&nbsp;</td>
   <td class="td04160102"><acronym title="Scale Ticket Number: is based on ONE load across the scale">Scale Ticket Number</acronym></td>
   <%= HTMLHelpersInput.inputBoxHidden("scaleTicket", ur.getScaleTicket()) %>
   <td class="td04160102">&nbsp;<b><%= ur.getScaleTicket().trim() %>
<%
   if (!ur.getScaleTicketCorrectionSequence().trim().equals("0"))
     out.println("-" + ur.getScaleTicketCorrectionSequence().trim());
%>   
   </b></td>
   <%= HTMLHelpersInput.inputBoxHidden("scaleTicketCorrectionSequence", ur.getScaleTicketCorrectionSequence()) %>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102" colspan="2" rowspan="2" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></td>
   <td style="width:2%">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Date Fruit is Received">Receiving Date/Time:</acronym></td>
   <td class="td03140102">&nbsp;<%= HTMLHelpersInput.inputBoxDate("receivingDate", ur.getReceivingDate(), "getReceivingDate", "N", "N") %></td>
   <td class="td03140102" style="text-align:left"><%= HTMLHelpersInput.inputBoxTime2Sections("receivingTime", "Receiving Time", ur.getReceivingTime()) %>&nbsp;<%= ur.getReceivingTimeError().trim() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	  

  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Gross Weight of the Entire Truck including Truck Tare Weight, Bins and Fruit)">Truck Gross Weight:</acronym></td>
   <td class="td03140102">&nbsp;<%= HTMLHelpersInput.inputBoxNumber("grossWeight", ur.getGrossWeight(), "Gross Weight", 10, 10, "Y", readOnlyLoad) %>&nbsp;<%= ur.getGrossWeightError().trim() %></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Weight of the Bins and the Fruit ONLY (Subtract Tare Weight from Gross Weight)">Net Weight (Bins and Fruit):</acronym></td>
   <td class="td04140102" style="text-align:right">&nbsp;<b><%= HTMLHelpersMasking.maskBigDecimal(rfl.getLoadNetWeight(), 0) %></b></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Tare Weight of the Truck ONLY">Truck Tare Weight:</acronym></td>
   <td class="td03140102">&nbsp;<%= HTMLHelpersInput.inputBoxNumber("tareWeight", ur.getTareWeight(), "Truck Tare Weight", 10, 10, "Y", readOnlyLoad) %>&nbsp;<%= ur.getTareWeightError().trim() %></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Tare Weight of ALL the Bins -- Located in the Bin Information Section">Tare Weight of All Bins:</acronym></td>
   <td class="td04140102" style="text-align:right">&nbsp;<b><%= HTMLHelpersMasking.maskBigDecimal(rfl.getBinTareWeight(), 1) %></b></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	 
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Check ONLY if the load is a Bulk Load">Bulk Load:</acronym></td>
   <td class="td04140102"><%= HTMLHelpersInput.inputCheckBox("bulkLoad", ur.getBulkLoad(), readOnlyLoad) %>&nbsp;</td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Weight of the FRUIT ONLY -- Net Weight Minus Tare Weight of All Bins">Accepted Fruit Weight:</acronym></td>
   <td class="td04140102" style="text-align:right">&nbsp;<b><%= HTMLHelpersMasking.maskBigDecimal(rfl.getLoadFruitNetWeight(), 1) %></b></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Carrier, must be a valid Movex Supplier">Carrier (Supplier) Number:</acronym>&nbsp;</td>
<%
   String classtype = "td04140102";
   if (!ur.getCarrierSupplierError().trim().equals(""))
      classtype = "td03140102";
%>      
   <td class="<%= classtype %>"><%= HTMLHelpersInput.inputBoxText("carrierSupplier", ur.getCarrierSupplier(), "Carrier-SupplierNumber", 10,10,"Y", readOnlyLoad) %>&nbsp;<%= ur.getCarrierSupplierError() %>&nbsp;<b><%= carrier.getSupplierName() %></b></td>
   <td class="td04140102">&nbsp;</td>
<%
   String nbClass1 = "td04140102";
   String nbClass2 = "td03140102";
   if (!ur.getLoadFullBinsTieError().equals(""))
   {
     nbClass1 = "td0114010203";
     nbClass2 = "td0114010203";
   }
%>   
   <td class="<%= nbClass1 %>"><acronym title="Number of Full Bins related to this PO">Number of Full Bins:&nbsp;&nbsp;<%= ur.getLoadFullBinsDetail() %></acronym></td>
   <td class="<%= nbClass2 %>"><acronym title="<%= ur.getLoadFullBinsTieError() %>"><%= HTMLHelpersInput.inputBoxNumber("loadFullBins", ur.getLoadFullBins(), "Full Bins", 5,5,"Y", readOnlyLoad) %>&nbsp;<%= ur.getLoadFullBinsError() %></acronym></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Carrier Bill of Lading   *** Enter Carrier Bill of Lading, if not available, enter the following:   -- A truck hauling for their own warehouse (Auvil, Double Diamond, CFC, etc.), enter the warehouse ticket number, if they don't have a Bill of Lading.  -- IF they are not hauling for a warehouse and don't have a Bill of Lading use the Scale Ticket Number. Ex: Hannah">Carrier Bill of Lading:</acronym></td>
   <td class="td03140102"><%= HTMLHelpersInput.inputBoxText("carrierBillOfLading", ur.getCarrierBillOfLading(), "Carrier Bill Of Lading", 10,10,"Y", readOnlyLoad) %>&nbsp;</td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Number of Empty Bins related to this PO">Number of Empty Bins:</acronym></td>
   <td class="td03140102"><%= HTMLHelpersInput.inputBoxNumber("loadEmptyBins", ur.getLoadEmptyBins(), "empty Bins", 5,5,"Y", readOnlyLoad) %>&nbsp;<%= ur.getLoadEmptyBinsError() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Number on the Fruit Document that comes with the Load">Warehouse Document Number:</acronym></td>
   <td class="td03140102"><%= HTMLHelpersInput.inputBoxText("whseTicket", ur.getWhseTicket(), "Warehouse Ticket Number", 10,10,"N", readOnlyLoad) %>&nbsp;</td>
   <%= HTMLHelpersInput.inputBoxHidden("supplierDeliveryNote", ur.getWhseTicket()) %>
   <td class="td04140102">&nbsp;</td>
<%
   String fbClass = "td04140102";
   if (!ur.getLoadBinsTieError().equals(""))
   {
     fbClass = "td0114010203";
   }
%>      
   <td class="<%= fbClass %>"><acronym title="Number of Total Bins for this Load (Add Full and Empty)">Number of Total Bins:</acronym></td>
   <td class="<%= fbClass %>" style="text-align:right"><acronym title="<%= ur.getLoadBinsTieError() %>">&nbsp;<b><%= HTMLHelpersMasking.maskBigDecimal(rfl.getLoadTotalBins(), 0) %></b></acronym></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	
<%  
  //add in section for handling code.
%>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Handling Code -- use the Handling code to retrive the Dim5 and the Cost Center.">Handling Code:</acronym></td>
   <td class="td04140102"><%= ur.buildDropDownHandlingCode("handlingCodeLong", ur.getHandlingCodeLong(), readOnlyLoad) %>&nbsp;</td>
   <td class="td04140102">&nbsp;</td> 
   <td class="td04140102"><acronym title="Total Number of Boxes associated to this Scale Ticket - Sequence (PO Number) - Information found at the LOT Level - Used to Calculate weight per bin at the Lot Level">Total Number Of Boxes:</acronym></td>
   <td class="td04140102" style="text-align:right">&nbsp;<b><%= HTMLHelpersMasking.maskBigDecimal(rfl.getLoadTotalBoxes(), 0) %></b></td>
   <td class="td04140102">&nbsp;</td> 
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">&nbsp;&nbsp;&nbsp;
<%
   if (!ur.getHandlingCode().trim().equals(""))
     out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>DIM5:</b>&nbsp;&nbsp;&nbsp;" + ur.getHandlingCode());
%>   
   </td>
   <td class="td04140102">&nbsp;&nbsp;&nbsp;
<%
   if (!ur.getCostCenter().trim().equals(""))
     out.println("<b>Cost Center:</b>&nbsp;&nbsp;&nbsp;" + ur.getCostCenter());
%>      
   </td>
   <td class="td04140102">&nbsp;</td> 
   <td class="td04140102"><acronym title="Average Pounds Per Box Used to Calculate weight per bin at the Lot Level **(Divide Accepted Fruit Weight by Total Number of Accepted Boxes)">Average Weight per Box:</acronym></td>
   <td class="td04140102" style="text-align:right">&nbsp;<b><%= HTMLHelpersMasking.maskBigDecimal(rfl.getLoadAveWeightPerBox(), 2) %></b></td>    
   <td class="td04140102">&nbsp;</td> 
  </tr> 
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="If there should be a Flat Rate Used for the load then check this box.  IF this box is checked it will default a value of $1.00 into the Freight Surcharge (as a flat rate) if the Freight Surcharge is 0.">Flat Rate for Freight on Load:</acronym></td>
   <td class="td03140102"><%= HTMLHelpersInput.inputCheckBox("flatRateFlag", ur.getFlatRateFlag(), readOnlyLoad) %>&nbsp;</td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Minimum Weight">Use the Minimum Weight of <%= HTMLHelpersInput.inputBoxNumber("minimumWeightValue", ur.getMinimumWeightValue(), "Minimum Weight Value", 9,9,"Y", readOnlyLoad) %> Pounds:</acronym></td>
   <td class="td03140102"><%= HTMLHelpersInput.dropDownYesNo("minimumWeightFlag", ur.getMinimumWeightFlag(), "", readOnlyLoad) %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	  
  <tr>
   <td class="td0414">&nbsp;</td>
   <td class="td0414"><acronym title="Freight Rate Entered will be Divided by 100 and then Multiplied by the Net Weight to get an Amount">Freight Rate:</acronym></td>
   <td class="td0314"><%= HTMLHelpersInput.inputBoxNumber("loadFreightRate", ur.getLoadFreightRate(), "Freight Rate", 10,10,"Y", readOnlyLoad) %>&nbsp;<%= ur.getLoadFreightRateError() %></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Number assigned to the Scheduled Load">Scheduled Load Number:</acronym></td>
   <td class="td03140102">&nbsp;<%= HTMLHelpersInput.inputBoxText("scheduledLoadNumber", ur.getScheduledLoadNumber(), "Scheduled Load Number", 9, 9, "N", readOnlyLoad) %>&nbsp;<%= ur.getScheduledLoadNumberError().trim() %></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	 
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102" colspan="2">&nbsp;&nbsp;&nbsp;<%= ur.buildDropDownChargeCode("loadFreightRateCode", ur.getLoadFreightRateCode(), readOnlyLoad) %></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="M3 - Facility -- To be used for the Freight PO">Facility:</acronym></td>
   <td class="td04140102"><b>
<%
  if (!rfl.getWarehouseFacility().getFacility().trim().equals(""))
  {
     out.println(rfl.getWarehouseFacility().getFacility() + "&nbsp;-&nbsp;" + rfl.getWarehouseFacility().getFacilityDescription());
     out.println(HTMLHelpersInput.inputBoxHidden("facility", rfl.getWarehouseFacility().getFacility()));
  }
%>   
    &nbsp;</b></td>      
   <td class="td04140102">&nbsp;</td>
  </tr>	   
  <tr>
   <td class="td0414">&nbsp;</td>
   <td class="td0414"><acronym title="Freight Surcharge -- Percent of the Calculated Rate Amount ">Freight Surcharge:</acronym></td>
   <td class="td0314"><%= HTMLHelpersInput.inputBoxNumber("loadSurcharge", ur.getLoadSurcharge(), "Load Freight Surcharge", 10,10,"Y", readOnlyLoad) %>&nbsp;<%= ur.getLoadSurchargeError() %></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="M3 - Warehouse -- To be used for the Freight PO">Warehouse:</acronym></td>
   <td class="td04140102"><b><%= ur.buildDropDownWarehouse("warehouse", ur.getWarehouse(), readOnlyLoad) %>
<%
//  if (!rfl.getWarehouseFacilty().getWarehouse().equals(""))
//     out.println("&nbsp;&nbsp;" + rfl.getWarehouseFacilty().getWarehouseDescription());
%>      
    &nbsp;</b></td> 
   <td class="td04140102">&nbsp;</td>
  </tr>	   
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102" colspan="2">&nbsp;&nbsp;&nbsp;<%= ur.buildDropDownChargeCode("loadSurchargeCode", ur.getLoadSurchargeCode(), readOnlyLoad) %></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Person Who Inspected the Load">Load Inspected By:</acronym></td>
   <td class="td04140102"><b><%= ur.buildDropDownInspectedBy("inspectedBy", ur.getInspectedBy(), readOnlyLoad) %>
<%
//  if (!rfl.getWarehouseFacilty().getWarehouse().equals(""))
//     out.println("&nbsp;&nbsp;" + rfl.getWarehouseFacilty().getWarehouseDescription());
%>      
    &nbsp;</b></td> 
   <td class="td04140102">&nbsp;</td>
  </tr>	   
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="From Location to be Displayed on the Freight Calculation Page ">From Location:</acronym></td>
   <td class="td03140102"><%= ur.buildDropDownFromLocation("fromLocation", ur.getFromLocation(), readOnlyLoad) %></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102" colspan="2" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %>
   <td class="td04140102">&nbsp;</td>
  </tr>	   
<%
  int imageCount  = 3;
  int expandCount = 0;
  // Only show this section if NOT a bulk load
  if (ur.getBulkLoad().trim().equals(""))
  { 
   // Create Expandable Section for Bin Type information Relating DIRECTLY to the EMPTY BINS
     String openClose = "C";
     if (ur.getListBins().size() == 0)
       openClose = "O";
 
     imageCount++;
     expandCount++;
  // Determine if NEW -- If NEW instead of Update then Have Expanding Section come in OPEN
%>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102" colspan = "5">
    <table class="table00" cellspacing="0" cellpadding="0">
     <tr class="tr02">
      <td class="td04121405">
       <%= JavascriptInfo.getExpandingSection(openClose, "Bin Information", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
         <jsp:include page="updRawFruitBin.jsp"></jsp:include>
       <%= HTMLHelpers.endSpan() %>
      </td>
     </tr>
    </table>   
   </td>   
   <td class="td04140102">&nbsp;</td>
  </tr>	 
<%
  } 
  imageCount++;
  expandCount++;
  request.setAttribute("screenType", "update");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("listKeyValues", ur.getUpdBean().getComments());  
%>  
 <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102" colspan = "5">
    <table class="table00" cellspacing="0" cellpadding="0">
     <tr class="tr02">
      <td class="td04121405">
      <%= JavascriptInfo.getExpandingSection("C", "Comments", 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
      <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
       <%= HTMLHelpers.endSpan() %>
      </td>
     </tr>
    </table>   
   </td>   
   <td class="td04140102">&nbsp;</td>
  </tr>	 
<%
  int poCount = 0;
  int lotCount = 0;
  request.setAttribute("lotCount", (lotCount + ""));
  try
  {
     Vector listPO = ur.getListPOs();
     poCount = listPO.size();
     // Loop through based on how many PO's are assigned to it.
     if (listPO.size() > 0)
     {
        for (int x = 0; x < listPO.size(); x++)
        { 
           UpdRawFruitPO urfp = (UpdRawFruitPO) ur.getListPOs().elementAt(x);
           request.setAttribute("updPOInfo", urfp);
   // Create Expandable Section for PO Section
  imageCount++;
  expandCount++;
  request.setAttribute("imageCount", (imageCount + ""));
  request.setAttribute("expandCount", (expandCount + ""));
%>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102" colspan = "5">
    <table class="table00" cellspacing="0" cellpadding="0">
     <tr class="tr02">
      <td colspan="4" class="td04121405">
       <%= JavascriptInfo.getExpandingSection("O", ("PO Sequence " + urfp.getSequenceNumber()), 0, expandCount, imageCount, 1, 0) %><div style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></div>
        <jsp:include page="updRawFruitPO.jsp"></jsp:include> 
       <%= HTMLHelpers.endSpan() %>
      </td>
     </tr>
    </table>   
   </td>   
   <td class="td04140102">&nbsp;</td>
  </tr>	 
  
<%
  imageCount = new Integer((String) request.getAttribute("imageCount")).intValue();
  expandCount = new Integer((String) request.getAttribute("expandCount")).intValue();
        } // end of the loop
     }
   }
   catch(Exception e)
   {  
   }
   lotCount = new Integer((String) request.getAttribute("lotCount")).intValue();
%> 
  <tr>
   <%= HTMLHelpersInput.inputBoxHidden("nextLotSequence", (lotCount + "")) %>
   <%= HTMLHelpersInput.inputBoxHidden("countPO", (poCount + "")) %>
   <td style="text-align:right" colspan="6"><%= HTMLHelpers.buttonSubmit("addPO", "Add PO (Sequence)") %>&nbsp;&nbsp;</td> 
   <td class="td04140102">&nbsp;</td>
  </tr>
 </table>
  </form>  		
  <%= JavascriptInfo.getCalendarFoot("updateLoad", "getReceivingDate", "receivingDate") %>
  <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>