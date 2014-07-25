<%@ page language="java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.math.*" %>
<%

//---------------- dtlRawFruitReportPO.jsp -------------------------------------------//
//
//    Author :  Teri Walton  12/05/08
//   CHANGES:
//
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
  String errorPage = "/RawFruit/dtlRawFruitReportPO.jsp";
  String updTitle = " Report a Raw Fruit Load - PO";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 DtlRawFruitLoad dr = new DtlRawFruitLoad();
 RawFruitLoad rfl = new RawFruitLoad();
 try
 {
	dr = (DtlRawFruitLoad) request.getAttribute("dtlViewBean");
	rfl = dr.getDtlBean().getRfLoad();
  }
 catch(Exception e)
 {
 //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
//**************************************************************************//
  // Allows the Title to display in the Gold Bar Area of the Page
   request.setAttribute("title",updTitle);
//*****************************************************************************   
%>
<html>
 <head>
   <title><%= updTitle %></title>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>   
 </head>
 <body>
  <jsp:include page="../../Include/heading.jsp"></jsp:include>
 <%
   if (!dr.getDisplayMessage().trim().equals(""))
   {
%>      
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr class="tr00">
   <td class="td03200102">&nbsp;</td>
   <td class="td03200102" colspan = "4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <b><%= dr.getDisplayMessage().trim() %></b></td>
   <td class="td03200102">&nbsp;</td>
  </tr>  
 </table>  
<%
   }
%>   
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr class = "tr01">
   <td class="td05167424"><b>PO - LOT ENTRY - PPS200&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Remove Panels E and G</b></td>
<%
   String displayReceivingDate = rfl.getReceivingDate();
   try{
       DateTime receivingDT = UtilityDateTime.getDateFromyyyyMMdd(rfl.getReceivingDate());
       if (!receivingDT.getDateFormatMMddyySlash().equals(""))
          displayReceivingDate = receivingDT.getDateFormatMMddyySlash();
     } catch(Exception e)
     {}
%>   
   <td class="td05167424">DATE:&nbsp;&nbsp;&nbsp;<b><%= displayReceivingDate %></b></td>
   <td class="td05167424" style="text-align:right"><acronym title="Scale Ticket Number">FREIGHT #:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%= DtlRawFruitLoad.maskField("scaleTicket", rfl.getScaleTicketNumber()) %>
<%
   if (!rfl.getScaleTicketCorrectionSequenceNumber().trim().equals("0"))
     out.println("-" + rfl.getScaleTicketCorrectionSequenceNumber().trim());
%>  
   </b></acronym></td>
  </tr>
 </table>
 <table class="table00" cellspacing="0" cellpadding="2" border = "1">
  <tr class = "tr02">
   <td class="td04120102" style="text-align:center">Lot</td>
   <td class="td04120102" style="text-align:center">Fac</td>
   <td class="td04120102" style="text-align:center">Whse</td>
   <td class="td04120102" style="text-align:center">Suppl #</td>
   <td class="td04120102" style="text-align:center">Supplier Name</td>
   <td class="td04120102" style="text-align:center">Rcvg Date</td>
   <td class="td04120102" style="text-align:center">PO Type</td>    
   <td class="td04120102" style="text-align:center">Raw Fruit Item</td>  
   <td class="td04120102" style="text-align:center">Description</td>   
   <td class="td04120102" style="text-align:center">Lot Quantity</td>  
   <td class="td04120102" style="text-align:center">Freight Weight</td>  
  </tr>
<%
  String poNumber = "";
  int countLot = 0;
  BigDecimal totalWeight = new BigDecimal("0");
  if (rfl.getListPOs().size() > 0)
  {
    for (int x = 0; x < rfl.getListPOs().size(); x++)
    {
       RawFruitPO rfp = (RawFruitPO) rfl.getListPOs().elementAt(x);
       poNumber = rfp.getPoNumber();
       if (rfp.getListLots().size() > 0)
       {
          for (int y = 0; y < rfp.getListLots().size(); y++)
          {
             RawFruitLot rfLot = (RawFruitLot) rfp.getListLots().elementAt(y);
             countLot++;
             try{
                totalWeight = totalWeight.add(new BigDecimal(rfLot.getLotTotalWeight()));
             }catch(Exception e)
             {}
%>  
  <tr class = "tr00">
   <td class="td04140102" style="text-align:center"><b><%= countLot %></b></td>
   <td class="td04140102" style="text-align:center"><b><%= rfLot.getLotInformation().getFacility() %></b></td>
   <td class="td04140102" style="text-align:center"><b><%= rfLot.getLotInformation().getWarehouse() %></b></td>
   <td class="td04140102" style="text-align:center"><b><%= rfLot.getLotSupplier().getSupplierNumber() %></b></td>
   <td class="td04100102"><%= rfLot.getLotSupplier().getSupplierName() %></td>
   <td class="td04140102" style="text-align:center"><b><%= displayReceivingDate %></b></td>
   <td class="td04140102" style="text-align:center"><b><acronym title="Currently Hard Coded">PO3</acronym></b></td>    
   <td class="td04140102" style="text-align:center"><b><%= DtlRawFruitLoad.maskField("item", rfLot.getLotInformation().getItemNumber()) %></b></td>  
   <td class="td04100102"><%= rfLot.getLotInformation().getItemDescription() %></td>   
   <td class="td04140102" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(rfLot.getLotTotalWeight(), 0) %></b></td>  
   <td class="td04140102" style="text-align:right"><acronym title="This field is the same as Lot Quantity"><b><%= HTMLHelpersMasking.maskBigDecimal(rfLot.getLotTotalWeight(),0) %></b></acronym></td>  
  </tr> 
<% 
       } // FOR the LOT
     } // If there is more than 1 lot for the PO
   } // FOR the PO
  } // IF there is more than 0 PO's
%> 
  <tr class = "tr02">
   <td class="td0414" colspan="10" style="text-align:center">Total Freight Weight</td>
   <td class="td04140102" style="text-align:right"><acronym title="This field is the Total of the Freight Weight Information"><b><%= HTMLHelpersMasking.maskBigDecimal(totalWeight.toString(), 0) %></b></acronym></td>  
  </tr>  
 </table>
<%
   //********************************************************************************************
   // SECOND TABLE for DISPLAY
%> 
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr class = "tr01">
   <td class="td05201405" style="text-align:right"><acronym title="Status the Record should be in AFTER LOT ENTRY AND BEFORE Entering the Goods Receiving"><b>STATUS:&nbsp;&nbsp;15</b></acronym></td>
  </tr> 
<%
   //Only Allow if you can update Raw Fruit Records
   try
   {
      if (InqRawFruit.getSecurity(request, response).equals("Y") &&
          rfl.getScaleTicketCorrectionSequenceNumber().trim().equals("0"))
      {
        if (poNumber.equals("0"))
        {
          if (rfl.getMissingItemWhse().trim().equals(""))
          {
%>
  <tr class = "tr01">
   <td style="text-align:right"> 
    <form action="/web/CtlRawFruit?requestType=generatePO" method="post">   
    <%= HTMLHelpers.buttonSubmit("generatePO", "Generate M3 PO", "") %>
    <%= HTMLHelpersInput.inputBoxHidden("scaleTicket", dr.getScaleTicket()) %>
    <%= HTMLHelpersInput.inputBoxHidden("scaleTicketCorrectionSequence", dr.getScaleTicketCorrectionSequence()) %>
    <%= HTMLHelpersInput.inputBoxHidden("receivingDate", rfl.getReceivingDate()) %>
    </form>
   </td>
  </tr>
<%          
          }else{
%>  
  <tr class = "tr01">
   <td class = "td0316" style="text-align:right"> 
    <b>	Missing Item Warehouse Records: <%= rfl.getMissingItemWhse().trim() %></b>
   </td>
  </tr>
<%
           }
        }
     }
   }
   catch(Exception e)
   {
      System.out.println("Error at PO Button on ReportPO page: " + e);
   }
   
%>  
  <tr class = "tr01">
   <td class="td05160105"><b>PO - GOODS RECEIVING - PPS300</b></td>
  </tr> 
 </table> 
 <table class="table00" cellspacing="0" cellpadding="2" border = "1">
<%
  countLot = 0;
  if (rfl.getListPOs().size() > 0)
  {
    for (int x = 0; x < rfl.getListPOs().size(); x++)
    {
       RawFruitPO rfp = (RawFruitPO) rfl.getListPOs().elementAt(x);
       if (rfp.getListLots().size() > 0)
       {
          for (int y = 0; y < rfp.getListLots().size(); y++)
          {
             RawFruitLot rfLot = (RawFruitLot) rfp.getListLots().elementAt(y);
             countLot++;
             if (y > 0)
             {
%>  
  <tr class = "tr00">
   <td colspan="9">&nbsp;</td>
  </tr>  
<%
             }
%>
  <tr class = "tr02">
   <td class="td04120102" style="text-align:center">Lot</td>
   <td class="td04120102" style="text-align:center">Whse</td>
   <td class="td04120102" style="text-align:center">PO</td>
   <td class="td04120102" style="text-align:center">Lot Wgt</td>
   <td class="td04120102" style="text-align:center">Location</td>
   <td class="td04120102" style="text-align:center">Dely Note</td>
   <td class="td04120102" style="text-align:center">Lot #</td>    
   <td class="td04120102" style="text-align:center">Lot Ref 1</td>  
   <td class="td04120102" style="text-align:center">Lot Ref 2</td>   
  </tr>  
  <tr class = "tr00">
   <td class="td0414" style="text-align:center"><b><%= countLot %></b></td>
   <td class="td0414" style="text-align:center"><b><%= rfLot.getLotInformation().getWarehouse() %></b></td>
   <td class="td0414" style="text-align:center"><b><%= DtlRawFruitLoad.maskField("po",rfp.getPoNumber()) %></b>&nbsp;</td>
   <td class="td0414" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(rfLot.getLotTotalWeight(),0) %></b></td>
   <td class="td0414" style="text-align:center"><b><%= rfLot.getLotInformation().getLocation() %></b>&nbsp;</td>
   <td class="td0414" style="text-align:center"><b><%= rfLot.getSupplierLoadNumber() %></b></td>
   <td class="td0414" style="text-align:center"><b><%= DtlRawFruitLoad.maskField("lot", rfLot.getLotInformation().getLotNumber()) %></b></td>    
   <td class="td0414" style="text-align:center"><b><%= rfLot.getSupplierLoadNumber() %></b></td>  
   <td class="td0414" style="text-align:center"><b><%= DtlRawFruitLoad.maskField("scaleTicket",rfp.getScaleTicketNumber()) %>
<%
   if (!rfp.getScaleTicketCorrectionSequenceNumber().trim().equals("0"))
     out.println("-" + rfp.getScaleTicketCorrectionSequenceNumber().trim());
%>     
   </b></td>   
  </tr> 
  <tr class = "tr00">
   <td class="td04120105" colspan="3">&nbsp;</td>
   <td class="td04120105" colspan="6">
    <table class="table00" cellspacing="0" cellpadding="2" border = "1">    
     <tr class = "tr02">
      <td class="td0410" style="text-align:center">Hvst Year</td>
      <td class="td0410" style="text-align:center">Cntr Type</td>
      <td class="td0410" style="text-align:center">Accpt # Cntr</td>
      <td class="td0410" style="text-align:center">Avg Wgt</td>
      <td class="td0410" style="text-align:center">Variety</td>
      <td class="td0410" style="text-align:center">Run Type</td>
      <td class="td0410" style="text-align:center">Cntry of Origin</td>
     </tr>
     <tr class = "tr00">
      <td class="td0414" style="text-align:center"><b><%= rfLot.getHarvestYear() %></b>&nbsp;</td>
      <td class="td0414" style="text-align:center"><b>
<%
    String displayBins = "0";
    String avgWeightPerBin = "0";
   if (rfl.getFlagBulkBin().equals(""))
   {
      out.println("Bin");
      try
      {
        BigDecimal totBins = (new BigDecimal(rfLot.getLotAcceptedBins25Box())).add(new BigDecimal(rfLot.getLotAcceptedBins30Box()));
        displayBins = totBins.toString();
        BigDecimal avgPerBin = (new BigDecimal(rfLot.getLotAcceptedWeight())).divide(totBins, 9, 5);
        avgWeightPerBin = HTMLHelpersMasking.maskBigDecimal(avgPerBin.toString(), 2);
      }catch(Exception e)
      {}	   
   }
   else
      out.println("Bulk");
%>    
       </b>  
      </td>
      <td class="td0414" style="text-align:right"><b><%= displayBins %></b></td>
      <td class="td0414" style="text-align:right"><b><%= avgWeightPerBin %></b></td>
      <td class="td0414" style="text-align:center"><b><%= rfLot.getVariety() %></b>&nbsp;</td>
      <td class="td0414" style="text-align:center"><b><%= rfLot.getRunType() %></b>&nbsp;</td>
      <td class="td0414" style="text-align:center"><b><acronym title="Country of Origin, should be in drop down list for Attribute"><%= rfLot.getCountryOfOrigin() %></acronym></b></td>     
     </tr>
    </table>
   </td>
  </tr>    
<% 
       } // FOR the LOT
     } // If there is more than 1 lot for the PO
   } // FOR the PO
  } // IF there is more than 0 PO's
%>  
  </table>
<%
   //********************************************************************************************
   // THIRD TABLE for DISPLAY
%> 
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr class = "tr01">
   <td class="td05201405" style="text-align:right"><acronym title="Status the Record should be in AFTER Entering the Goods Receiving AND BEFORE Entering the Inspect Goods"><b>STATUS:&nbsp;&nbsp;51</b></acronym></td>
  </tr> 
  <tr class = "tr01">
   <td class="td05160105"><b>PO - INSPECT GOODS - PPS310</b></td>
  </tr>   
 </table>
 <table class="table00" cellspacing="0" cellpadding="2" border = "1">
  <tr class = "tr02">
   <td class="td04120102" style="text-align:center">Lot</td>
   <td class="td04120102" style="text-align:center">Lot #</td>
   <td class="td04120102" style="text-align:center">Received Qty</td>
   <td class="td04120102" style="text-align:center">Accepted Remark</td>
   <td class="td04120102" style="text-align:center">Avg Bin Weight</td>
   <td class="td04120102" style="text-align:center">Rejected Weight</td>
   <td class="td04120102" style="text-align:center">Rejected Bins</td>
   <td class="td04120102" style="text-align:center">Rejected Remark</td>    
   <td class="td04120102" style="text-align:center">Claim #</td>  
  </tr>  
<%
  countLot = 0;
  if (rfl.getListPOs().size() > 0)
  {
    for (int x = 0; x < rfl.getListPOs().size(); x++)
    {
       RawFruitPO rfp = (RawFruitPO) rfl.getListPOs().elementAt(x);
       if (rfp.getListLots().size() > 0)
       {
          for (int y = 0; y < rfp.getListLots().size(); y++)
          {
             RawFruitLot rfLot = (RawFruitLot) rfp.getListLots().elementAt(y);
             countLot++;
%>  
  <tr class = "tr00">
   <td class="td0414" style="text-align:center"><b><%= countLot %></b></td>
   <td class="td0414" style="text-align:center"><b><%= DtlRawFruitLoad.maskField("lot", rfLot.getLotInformation().getLotNumber()) %></b></td>
   <td class="td0414" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(rfLot.getLotAcceptedWeight(), 0) %></b></td>
   <td class="td0412"><b><%= rfLot.getLotAcceptedComments() %></b>&nbsp;</td>
   <td class="td0414" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(rfLot.getAverageWeightPerBin(),2) %></b></td>
   <td class="td0414" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(rfLot.getLotRejectedWeight(), 0) %></b></td>
<%
    String totalRejectedBins = "0";
    try
    {
       BigDecimal totalRejBins = (new BigDecimal(rfLot.getLotRejectedBins25Box())).add(new BigDecimal(rfLot.getLotRejectedBins30Box()));
       totalRejectedBins = totalRejBins.toString();
    }catch(Exception e)
    {}
%>     
      
   <td class="td0414" style="text-align:right"><b><%= HTMLHelpersMasking.maskNumber(totalRejectedBins,0) %></b></td>  
   <td class="td0412"><b><%= rfLot.getLotRejectedComments() %></b>&nbsp;</td>   
   <td class="td0414">&nbsp;</td>  
  </tr> 
<% 
       } // FOR the LOT
     } // If there is more than 1 lot for the PO
   } // FOR the PO
  } // IF there is more than 0 PO's
%>  
 </table>
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr class = "tr01">
   <td class="td05201405" style="text-align:right"><acronym title="Status the Record should be in AFTER Entering the Inspec Goods"><b>STATUS:&nbsp;&nbsp;75</b></acronym></td>
  </tr> 

 </table>  
  <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>