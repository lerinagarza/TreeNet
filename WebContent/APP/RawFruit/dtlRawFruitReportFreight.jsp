<%@ page language="java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "com.treetop.app.rawfruit.DtlRawFruitLoad" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- dtlRawFruitReportFreight.jsp -------------------------------------------//
//
//    Author :  Teri Walton  12/09/08
//   CHANGES:
//
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
  String errorPage = "/RawFruit/dtlRawFruitReportFreight.jsp";
  String updTitle = " Report a Raw Fruit Load - Freight";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 DtlRawFruitLoad dr = new DtlRawFruitLoad();
 RawFruitLoad rfl = new RawFruitLoad();
 Vector binInfo = new Vector();
 try
 {
	dr = (DtlRawFruitLoad) request.getAttribute("dtlViewBean");
	rfl = dr.getDtlBean().getRfLoad();
	binInfo = dr.getDtlBean().getListRFBin();
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
   String displayReceivingDate = rfl.getReceivingDate();
   try{
       DateTime receivingDT = UtilityDateTime.getDateFromyyyyMMdd(rfl.getReceivingDate());
       if (!receivingDT.getDateFormatMMddyySlash().equals(""))
          displayReceivingDate = receivingDT.getDateFormatMMddyySlash();
     } catch(Exception e)
     {}
%>   
<table class="table00" cellspacing="0" cellpadding="2">
 <tr>
  <td class="td04120102" style="text-align:right">Scale Ticket #:</td>
  <td class="td04160605" style="text-align:center"><b><%= DtlRawFruitLoad.maskField("scaleTicket", rfl.getScaleTicketNumber()) %>
<%
   if (!rfl.getScaleTicketCorrectionSequenceNumber().trim().equals("0"))
     out.println("-" + rfl.getScaleTicketCorrectionSequenceNumber().trim());
%>     
  </b></td>
  <td style="width:5%" rowspan="5">&nbsp;</td>
  <td class="td04120102" style="text-align:right">Truck Gross:</td>
  <td class="td04140602" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(rfl.getLoadGrossWeight(), 0) %></b></td> 
  <td style="width:5%" rowspan="5">&nbsp;</td>
  <td class="td04120102" style="text-align:right">TT Receiving Facility:</td>
  <td class="td04140602" style="text-align:center"><b><%= rfl.getWarehouseFacility().getFacility() %>&nbsp;</b></td> 
  <td class="td04140102"><%= rfl.getWarehouseFacility().getFacilityDescription() %>&nbsp;</td>      
 </tr>
 <tr>
  <td class="td04120102" style="text-align:right">Date:</td>
  <td class="td04140602" style="text-align:center"><b><%= displayReceivingDate %></b></td>
  <td class="td04120102" style="text-align:right">Truck Tare:</td>
  <td class="td04140602" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(rfl.getLoadTareWeight(),0) %></b></td> 
  <td class="td04120102" style="text-align:right">TT Receiving Warehouse:</td>
  <td class="td04140602" style="text-align:center"><b><%= rfl.getWarehouseFacility().getWarehouse() %>&nbsp;</b></td> 
  <td class="td04140102"><%= rfl.getWarehouseFacility().getWarehouseDescription() %></td>      
 </tr> 
 <tr>
  <td class="td04120102" style="text-align:right"><b># of Bins on Load:</b></td>
  <td class="td04140602" style="text-align:center"><b><%= rfl.getLoadTotalBins() %></b></td>
  <td class="td04120102" style="text-align:right">Net (Fruit and Bins):</td>
  <td class="td04160605" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(rfl.getLoadNetWeight(),0) %></b></td> 
  <td class="td04120102" style="text-align:right">&nbsp;From Location:</td>
  <td class="td04140602"  style="text-align:center" colspan="2">&nbsp;<b>
  
<%
   if (rfl.getFromLocationLong().trim().equals(""))
     out.println(rfl.getFromLocation().trim());
   else
     out.println(rfl.getFromLocationLong().trim());
%>  
  </b>&nbsp;</td> 
 </tr> 
 <tr>
  <td class="td04120102">Load Type:</td>
  <td class="td04140602" style="text-align:center"><b>
<%
   if (rfl.getFlagBulkBin().equals(""))
      out.println("Bin");
   else
      out.println("Bulk");
%>  
  </b></td>
  <td colspan="2">&nbsp;</td>
  <td class="td04120102" style="text-align:right">&nbsp;Charge Facility:</td>
  <td class="td04140602" style="text-align:center" colspan="2"><acronym title="Field is HARD Coded for now, always will show as 200">&nbsp;<b>200</b>&nbsp;</acronym></td>  
 </tr>    
</table>
<table class="table00" cellspacing="0" cellpadding="2"> 
 <tr>
  <td style="width:40%">
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr>
     <td class="td0414060202" colspan = "3" style="text-align:center"><b>FREIGHT INFORMATION</b></td>
    </tr> 
    <tr>
     <td class="td04120602" colspan="2">Supplier # (Carrier):</td>
     <td class="td04140602" style="text-align:center"><b><%= rfl.getCarrier().getSupplierNumber() %></b>&nbsp;</td>
    </tr>
    <tr>
     <td class="td04120602" colspan="2">Carrier B/L#:</td>
     <td class="td04140602" style="text-align:center"><b><%= rfl.getCarrierBOL() %></b>&nbsp;</td>
     <td class="td0414">&nbsp;</td>
    </tr> 
    <tr>
     <td class="td04120602" colspan="2">Carrier Name:</td>
     <td class="td04120602" style="text-align:center"><%= rfl.getCarrier().getSupplierName() %>&nbsp;</td>
    </tr>    
    <tr>
     <td class="td04120602" colspan="2">Weight:</td>
     <td class="td04140602" style="text-align:right"><%= HTMLHelpersMasking.maskBigDecimal(rfl.getLoadNetWeight(),0) %></td>
    </tr>       
    <tr>
     <td class="td04120602" colspan="2">Less Bins:</td>
     <td class="td04140602" style="text-align:right">(<%= HTMLHelpersMasking.maskBigDecimal(rfl.getBinTareWeight(), 1) %>)</td>
    </tr>       
    <tr>
     <td class="td04120602" colspan="2">Net Fruit:</td>
     <td class="td04140602" style="text-align:right"><%= HTMLHelpersMasking.maskBigDecimal(rfl.getLoadFruitNetWeight(),1) %></td>
    </tr>    
    <tr>
     <td class="td04120602">Min Weight:</td>
     <td class="td04140602" style="text-align:center"><%= HTMLHelpersMasking.maskBigDecimal(rfl.getMinimumWeightValue(),0) %></td>    
     <td class="td04140602" style="text-align:center"><%= rfl.getMinimumWeightFlag() %></td>
     <td class="td0414060202" style="text-align:center"><b>Amounts</b></td>
    </tr> 
<%
  BigDecimal calcSurcharge = new BigDecimal("0");
  BigDecimal freightWeight = new BigDecimal(rfl.getMinimumWeightValue());
  // Freight Information Section
  BigDecimal rateAmount = new BigDecimal("0");
  BigDecimal surchargeAmount = new BigDecimal("0");
  BigDecimal freightTotal = new BigDecimal("0");
  String rateAmountToCalculate = "";
  String surchargeAmountToCalculate = "";
  try
	{
      // Figure out the Freight Weight for Calculations
      if (rfl.getMinimumWeightFlag().equals("N"))
         freightWeight = new BigDecimal(rfl.getLoadNetWeight());
  }
  catch(Exception e)
  { // Catch if problem
  }
  if (rfl.getFlatRateFreightFlag().equals(""))
  {
  	
    try
    {
      calcSurcharge = new BigDecimal(rfl.getFreightSurcharge()).divide((new BigDecimal("100")), 5);
    }
    catch(Exception e)
    { // Catch if problem
    }
    try
    {
      rateAmount = (((new BigDecimal(rfl.getFreightRate())).divide(new BigDecimal("100"), 5)).multiply(freightWeight)).setScale(2,5);
      surchargeAmount = ((calcSurcharge).multiply(rateAmount)).setScale(2,5);
      freightTotal = rateAmount.add(surchargeAmount);
    }
    catch(Exception e)
    { // Catch if a Calculation Problem 
    }
    rateAmountToCalculate = HTMLHelpersMasking.maskBigDecimal(rfl.getFreightRate(),2);
    surchargeAmountToCalculate = HTMLHelpersMasking.mask2DecimalPercent(calcSurcharge.toString());
    
  }
  else
  {
      rateAmount = (new BigDecimal(rfl.getFreightRate())).setScale(2,5);
      surchargeAmount = (new BigDecimal(rfl.getFreightSurcharge())).setScale(2,5);
      freightTotal = rateAmount.add(surchargeAmount);
  }
%>  
    
    <tr>
     <td class="td04120602" colspan="2">
<%
   if (rfl.getFlatRateFreightFlag().equals(""))
      out.println("Rate:");
   else
      out.println("Flat Rate:");
%>     
     </td>
     <td class="td04140602" style="text-align:center"><%= rateAmountToCalculate %></td>    
     <td class="td04140602" style="text-align:right"><%= HTMLHelpersMasking.mask2DecimalDollar(rateAmount.toString()) %></td>
    </tr> 
    <tr>
     <td class="td04120602" colspan="2">Fuel Surcharge %:</td>
     <td class="td04140602" style="text-align:center"><%= surchargeAmountToCalculate %></td>    
     <td class="td04140602" style="text-align:right"><%= HTMLHelpersMasking.mask2DecimalDollar(surchargeAmount.toString()) %></td>
    </tr>   
    <tr>
     <td class="td0414060202" style="text-align:right" colspan="3"><b>Freight Amount Total:</b></td>
     <td class="td0414060202" style="text-align:right"><b><%= HTMLHelpersMasking.mask2DecimalDollar(freightTotal.toString()) %></b></td>
    </tr>      
       
   </table>  
  </td>
  <td style="width:1%">&nbsp;</td>
  <td valign="top">
   <table class="table00" cellspacing="0" cellpadding="2" border = "1">
    <tr class = "tr02">
     <td class="td04120605" colspan="5"><b>&nbsp;</b></td>
     <td class="td04120605" style="text-align:center"><b><%= rfl.getFreightRateCode() %></b></td>
     <td class="td04120605" style="text-align:center"><b><%= rfl.getFreightSurchargeCode() %></b></td>    
     <td class="td04120605" style="text-align:center"><b>&nbsp;</b></td>    
    </tr>
    <tr class = "tr02">
     <td class="td04120605" style="text-align:center">Supplier # (Fruit on Load)</td>
     <td class="td04120605" style="text-align:center">PO# - REF NO</td>
     <td class="td04120605" style="text-align:center">Weight</td>
     <td class="td04120605" style="text-align:center">Split%</td>
     <td class="td04120605" style="text-align:center">Qty for M3</td>
     <td class="td04120605" style="text-align:center">Adjust Freight</td>
     <td class="td04120605" style="text-align:center">S/C</td>    
     <td class="td04120605" style="text-align:center">Total Amount</td>    
    </tr>
<%
// To get the listing of Special Charges we MUST go through the PO's, Lot's, Payment's and then get to the Special Charges
  BigDecimal totaladjFreight = new BigDecimal("0");
  BigDecimal totalsc = new BigDecimal("0");
  BigDecimal totallinetotal = new BigDecimal("0");
  BigDecimal totalSplitPercent = new BigDecimal("0.00");
 // BigDecimal totalPoAcceptedWeight = new BigDecimal("0");
 //10/14/10 - TWalton change to use Freight weight instead of Accepted weight
  BigDecimal totalPoFreightWeight = new BigDecimal("0");
 if (rfl.getListPOs().size() > 0)
 {
 for (int a = 0; a < rfl.getListPOs().size(); a++)
  { // One line for Each PO
    RawFruitPO rfp = (RawFruitPO) rfl.getListPOs().elementAt(a);
 //   totalPoAcceptedWeight = totalPoAcceptedWeight.add(new BigDecimal(rfp.getPoAcceptedWeight())); 
    totalPoFreightWeight = totalPoFreightWeight.add(new BigDecimal (rfp.getPoTotalWeight()));
  }     
 
  for (int a = 0; a < rfl.getListPOs().size(); a++)
  { // One line for Each PO
    RawFruitPO rfp = (RawFruitPO) rfl.getListPOs().elementAt(a);
%>    
    <tr class = "tr00">
     <td class="td04140602" style="text-align:center"><b><acronym title="<%= rfp.getPoSupplier().getSupplierName() %>"><%= rfp.getPoSupplier().getSupplierNumber() %>&nbsp;</acronym></b></td>
     <td class="td04140602" style="text-align:center"><b><%= DtlRawFruitLoad.maskField("po",rfp.getPoNumber()) %></b></td>
     <td class="td04140602" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(rfp.getPoTotalWeight(), 0) %></b></td>
<%  // Split percent is Accepted Weight (for the PO) divided by Net Weight of the Entire Load
    BigDecimal splitPercent = new BigDecimal("0.00");
    try
    { 
       if (a == (rfl.getListPOs().size() - 1))
       {
         splitPercent = new BigDecimal("1.00").subtract(totalSplitPercent);
       }
       else
       {
        //  splitPercent = (new BigDecimal(rfp.getPoTotalWeight())).divide(totalPoAcceptedWeight, 4,2);
          splitPercent = (new BigDecimal(rfp.getPoTotalWeight())).divide(totalPoFreightWeight, 4,2);
       }
    }
    catch(Exception e)
    {
    }
    BigDecimal qtyForM3 = new BigDecimal("0");
    BigDecimal adjFreight = new BigDecimal("0");
    BigDecimal sc = new BigDecimal("0");
    BigDecimal linetotal = new BigDecimal("0");
    try
    {
       if (splitPercent.compareTo(new BigDecimal("0")) != 0)
       {
         // Calculate all the Information
          qtyForM3 = freightWeight.multiply(splitPercent);
          adjFreight = rateAmount.multiply(splitPercent);
          sc = surchargeAmount.multiply(splitPercent);
          
          ((calcSurcharge).multiply(rateAmount)).setScale(2,5);
          linetotal = (adjFreight.setScale(2,5)).add(sc.setScale(2,5));
         // Add the Information to the Totals
         totaladjFreight = totaladjFreight.add(adjFreight);
         totalsc = totalsc.add(sc);
         totallinetotal = totallinetotal.add(linetotal);
         totalSplitPercent = totalSplitPercent.add(splitPercent);
       }
    }
    catch(Exception e)
    {
    }
%>     
     <td class="td04120602" style="text-align:right"><%= HTMLHelpersMasking.mask2DecimalPercent(splitPercent.toString()) %></td>
     <td class="td04140602" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(qtyForM3.toString(), 0) %></b></td>
     <td class="td04140602" style="text-align:right"><b><%= HTMLHelpersMasking.mask2DecimalDollar(adjFreight.toString()) %></b></td>
     <td class="td04140602" style="text-align:right"><b><%= HTMLHelpersMasking.mask2DecimalDollar(sc.toString()) %></b></td>    
     <td class="td04140602" style="text-align:right"><b><%= HTMLHelpersMasking.mask2DecimalDollar(linetotal.toString()) %></b></td>    
    </tr>    
<%
  }// END of the for Loop for PO's
 }// End of the IF there are ANY PO's
%>
    <tr class = "tr02">
     <td class="td04120605" colspan="2">TOTALS</td>
     <td class="td04120605" style="text-align:right"><%= HTMLHelpersMasking.maskBigDecimal(totalPoFreightWeight.toString(), 0) %></td>
     <td class="td04120605" style="text-align:right"><%= HTMLHelpersMasking.mask2DecimalPercent(totalSplitPercent.toString()) %></td>
     <td class="td04120605" style="text-align:right"><%= HTMLHelpersMasking.maskBigDecimal(freightWeight.toString(), 0) %></td>
     <td class="td04120605" style="text-align:right"><%= HTMLHelpersMasking.mask2DecimalDollar(totaladjFreight.toString()) %></td>
     <td class="td04120605" style="text-align:right"><%= HTMLHelpersMasking.mask2DecimalDollar(totalsc.toString()) %></td>    
     <td class="td04120605" style="text-align:right"><%= HTMLHelpersMasking.mask2DecimalDollar(totallinetotal.toString()) %></td>    
    </tr>
</table>  
  </td>
 </tr>
 <tr>
  <td>&nbsp;</td>
 </tr>
</table>
<table class="table00" cellspacing="0" cellpadding="2">
 <tr>
  <td style="width:60%">
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr class = "tr02">
     <td class="td04160602" style="text-align:center" colspan="6"><b>Invoicing in M3</b></td>
    </tr>  
    <tr class = "tr00">
     <td class="td04140602" style="text-align:right">Charge Facility:</td>
     <td class="td04160602" style="text-align:center"><acronym title="Field is HARD Coded for now, always will show as 200"><b>200</b></acronym></td>
     <td class="td04140602" style="text-align:right">Cost Center:</td>
     <td class="td04160602" style="text-align:center"><b><%= rfl.getCostCenter() %></b></td>
     <td class="td04140602" style="text-align:right"><acronym title="Accounting Dimension 5">Dim 5:</acronym></td>    
     <td class="td04160602" style="text-align:center"><b><%= rfl.getDim5() %></b></td>
    </tr>
    <tr class = "tr00">
     <td class="td04140602" style="text-align:right">BLANK2:</td>
     <td class="td04160602" colspan="5">&#160;&#160;&#160;&#160;&#160;<b><%= rfl.getWhseTicket() %>
<%
   if (!rfl.getScheduledLoadNumber().trim().equals(""))
   {
%>  
&#160;&#160;/&#160;&#160;<%= rfl.getScheduledLoadNumber().trim() %>
<%
   }
%>   
     </b></td>
    </tr>
    <tr class = "tr00">
     <td class="td04140602" style="text-align:right">BLANK3:</td>
     <td class="td04160602" colspan="5">&#160;&#160;&#160;&#160;&#160;<b><%= rfl.getScaleTicketNumber() %>&#160;&#160;/&#160;&#160;<%= rfl.getCarrierBOL() %></b></td>
    </tr>
   </table>
  </td>
  <td style="width:35%">
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr>
      <td>&nbsp;</td>
    </tr>
   </table> 
  </td>
 </tr>
 <tr>
  <td>&nbsp;</td>
 </tr>
</table>
<table class="table00" cellspacing="0" cellpadding="2">
 <tr>
  <td style="width:60%">
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr class = "tr02">
     <td class="td04160602" style="text-align:center"><b>Bin Type</b></td>
     <td class="td04160602" style="text-align:center"><b>Description</b></td>
     <td class="td04160602" style="text-align:center"><b>Tare Weight per Bin</b></td>
     <td class="td04160602" style="text-align:right"><b>Number of Bins per Type</b></td>
     <td class="td04160602" style="text-align:right"><b>Tare Weight</b></td>    
    </tr>
<%
 // Display all the Records that are currently in the File
  BigDecimal totalBins = new BigDecimal(0);
  BigDecimal totalWeight = new BigDecimal(0);
  if (binInfo.size() > 0)
  {
    for (int x = 0; x < binInfo.size(); x++)
    {
       RawFruitBin fileData = new RawFruitBin();
       try
       {
          fileData = (RawFruitBin) binInfo.elementAt(x);
          totalBins = totalBins.add(new BigDecimal(fileData.getBinQuantity()));
          totalWeight = totalWeight.add(new BigDecimal(fileData.getBinTotalWeight()));
       }
       catch(Exception e)
       {}
%>   
    <tr>
     <td class="td04140602">&nbsp;<%= fileData.getBinType() %></td>
     <td class="td04140602">&nbsp;<%= fileData.getBinDescription() %></td>
     <td class="td04140602" style="text-align:right">&nbsp;<%= HTMLHelpersMasking.maskBigDecimal(fileData.getBinWeight(),0) %></td>
     <td class="td04140602" style="text-align:right">&nbsp;<%= HTMLHelpersMasking.maskBigDecimal(fileData.getBinQuantity(),0) %></td>
     <td class="td04140602" style="text-align:right">&nbsp;<%= HTMLHelpersMasking.maskBigDecimal(fileData.getBinTotalWeight(), 0) %></td>
    </tr>
<%
      }
  }
%>
    <tr class = "tr02">
     <td class="td04160605" style="text-align:center" colspan="3"><b>TOTALS</b></td>
     <td class="td04160605" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(totalBins.toString(), 0) %></b></td>
     <td class="td04160605" style="text-align:right"><b><%= HTMLHelpersMasking.maskBigDecimal(totalWeight.toString(), 0) %></b></td>    
    </tr>
   </table>
  </td>
  <td style="width:35%">
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td class="td0416" style="text-align:right"><b>Voucher#:&nbsp;</b></td>
      <td class="td04160605" style="width:65%; height:35px">&nbsp;</td>
    </tr>
   </table> 
  </td>
 </tr>
 <tr><td>&nbsp;</td></tr>
</table>
<table class="table00" cellspacing="0" cellpadding="2">
 <tr>
  <td style="width:60%">
   <table class="table00" cellspacing="0" cellpadding="2">
    <tr class = "tr02">
     <td class="td04160602" style="text-align:center"><b>Sequence</b></td>
     <td class="td04160602" style="text-align:center"><b>Lot #</b></td>
     <td class="td04160602" style="text-align:center"><b>Name</b></td>
     <td class="td04160602" style="text-align:center"><b>Supplier #</b></td>
     <td class="td04160602" style="text-align:center"><b>Supplier Doc#</b></td>
    </tr>
<%    
 int countLots = 0;
 if (rfl.getListPOs().size() > 0)
 {
  for (int a = 0; a < rfl.getListPOs().size(); a++)
  { // One line for Each PO
    RawFruitPO rfp = (RawFruitPO) rfl.getListPOs().elementAt(a);   
    if (rfp.getListLots().size() > 0)
    { 
     for (int b = 0; b < rfp.getListLots().size(); b++)
     {
       RawFruitLot rfLot = (RawFruitLot) rfp.getListLots().elementAt(b);
       countLots++;
%>
    <tr class = "tr00">
     <td class="td04160602" style="text-align:center"><%= countLots %></td>
     <td class="td04160602" style="text-align:center"><%= rfLot.getLotInformation().getLotNumber() %>&nbsp;</td>
     <td class="td04160602" style="text-align:center"><%= rfLot.getLotSupplier().getSupplierName() %>&nbsp;</td>
     <td class="td04160602" style="text-align:center"><%= rfLot.getLotSupplier().getSupplierNumber() %>&nbsp;</td>
     <td class="td04160602" style="text-align:center"><%= rfLot.getSupplierLoadNumber() %>&nbsp;</td>
    </tr>
<%
     }// END of the for Loop for Lot's
    }// End of IF there are ANY Lot's
  }// END of the for Loop for PO's
 }// End of the IF there are ANY PO's
%>    
   </table>
  </td>
  <td>&nbsp;</td>
 </tr>
<%    
 if (dr.getDtlBean().getComments().size() > 0)
 { // Only display IF comments are found
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("listKeyValues", dr.getDtlBean().getComments());  
%>  
    <tr>
     <td colspan="3">&nbsp;</td>   
    </tr>	  
    <tr class="tr02">
     <td colspan="3">
      <%@ include file="../Utilities/updKeyValuesNew.jsp" %>   
     </td>   
    </tr>	     
<%
   } 
%>    
</table>
  <jsp:include page="../../Include/footer.jsp"></jsp:include>
   </body>
</html>