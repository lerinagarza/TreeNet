<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.utilities.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.math.*" %>
<%
//---------------- dtlRawFruitLot.jsp -------------------------------------------//
//
//    Author :  Teri Walton  11/21/08
//       // ALWAYS Included in other JSP's:
//				updRawFruitPO
//
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//   9/23/13    TWalton		Add the Write Up Number Field
//--------------------------------------------------------------------------//
try
{ // Place Try Catch Around ENTIRE JSP -- That way the page will still display even if this section has problems
//**************************************************************************//
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
  RawFruitLot lotInfo = new RawFruitLot();
  try
  {
	lotInfo = (RawFruitLot) request.getAttribute("rfLot");
  }
  catch(Exception e)
  {
  //   System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
  //	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
  }    
%>
<html>
 <head>
   <%= JavascriptInfo.getExpandingSectionHead("Y", 5, "Y", 5) %>   
   <%= JavascriptInfo.getMoreButton() %>
 </head>
 <body>
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102"><acronym title="Scale Ticket Number - Load Number">Scale Ticket Number:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getScaleTicketNumber() %>
<%
   if (!lotInfo.getScaleTicketCorrectionSequenceNumber().trim().equals("0"))
     out.println("-" + lotInfo.getScaleTicketCorrectionSequenceNumber().trim());
%>      
   &nbsp;</b></td>
   <td class="td04140102" style="width:2%">&nbsp;</td>
   <td class="td04140102" colspan="2" style="text-align:right">
   <%= UpdRawFruitLoad.buildMoreButton("updateLot", lotInfo.getScaleTicketNumber(), lotInfo.getScaleTicketCorrectionSequenceNumber(), lotInfo.getReceivingDate(), lotInfo.getScaleSequence(), lotInfo.getPoNumber(), lotInfo.getLotSequence(), lotInfo.getLotNumber()) %>
   </td>
   <td class="td04140102" style="width:2%">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Scale Ticket Sequence Number - Ties with Scale Number to the PO Number">Scale Ticket Sequence Number:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getScaleSequence() %></b></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="PO Number - When Available">PO Number:</acronym></td>
   <td class="td04140102"><b>
<%
   if (lotInfo.getPoNumber().trim().equals("0"))
      out.println("PO Not Yet Created");
   else 
      out.println(lotInfo.getPoNumber());
%>   
    &nbsp;</b>
   </td>
   <td class="td04140102">&nbsp;</td> 
  </tr> 
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Lot Number(M3) = Raw Fruit Ticket Number">Lot Number:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getLotNumber() %>&nbsp;</b></td>
   <td class="td04140102">&nbsp;</td> 
   <td class="td04140102">PO Line Number:</td>
   <td class="td04140102"><b><%= lotInfo.getPoLineNumber() %>&nbsp;</b></td>
   <td class="td04140102">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Item Number">Item Number:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getLotInformation().getItemNumber() %> &nbsp;&nbsp;&nbsp;<%= lotInfo.getLotInformation().getItemDescription() %></b></td>
   <td class="td04140102">&nbsp;</td> 
   <td class="td04140102">Receiving Date:</td>
   <td class="td04140102"><b>
<%  
   String displayDate = "";
   try
   {
      DateTime dt = UtilityDateTime.getDateFromyyyyMMdd(lotInfo.getReceivingDate());
      displayDate = dt.getDateFormatMMddyySlash();
//      System.out.println("display date" + displayDate);
   }
   catch(Exception e)
   {
 //     System.out.println("show Info: " + lotInfo.getReceivingDate());
   }
%>   
   <%= displayDate %>
   &nbsp;</b></td>
   <td class="td04140102">&nbsp;</td>
  </tr>  
<%
   if (!lotInfo.getItemWhseNotValid().trim().equals(""))
   {
%>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td0316" colspan="5"><b><%= lotInfo.getItemWhseNotValid().trim() %></b></td>
   <td class="td04140102">&nbsp;</td>
  </tr> 
<%
   }
%>   
  <tr>
   <td class="td04140102">&nbsp;</td> 
   <td class="td04140102"><acronym title="M3 - Facility">Facility:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getLotInformation().getFacility() %>&nbsp;&nbsp;&nbsp;<%= lotInfo.getLotInformation().getFacilityName() %></b></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Harvest Year">Harvest Year:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getHarvestYear() %>&nbsp;</b></td>
   <td class="td04140102">&nbsp;</td>
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td> 
   <td class="td04140102"><acronym title="M3 - Warehouse">Warehouse:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getLotInformation().getWarehouse() %>&nbsp;&nbsp;&nbsp;<%= lotInfo.getLotInformation().getWarehouseName() %></b></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Conventional or Organic">M3 - Conventional OR Organic:</acronym></td>
   <td class="td04140102"><b>
<%   
      if (lotInfo.getLotInformation().getOrganicConventional().equals("OR"))
       out.println("Organic");
    else
       out.println("Conventional");
%>       
   &nbsp;</b></td>  
   <td class="td04140102">&nbsp;</td>    
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td> 
   <td class="td04140102"><acronym title="M3 - Location">Location:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getLotInformation().getLocation() %>&nbsp;</b></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Crop">M3 - Crop:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getCrop() %>&nbsp;</b></td>
   <td class="td04140102">&nbsp;</td>   
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td> 
   <td class="td04140102"><acronym title="Supplier - Packer - Who we received the Fruit From">Supplier:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getLotSupplier().getSupplierNumber() %>&nbsp;&nbsp;&nbsp;<%= lotInfo.getLotSupplier().getSupplierName() %></b></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Variety">M3 - Variety:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getVariety() %>&nbsp;</b></td> 
   <td class="td04140102">&nbsp;</td>   
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td> 
   <td class="td04140102"><acronym title="Supplier Load Number - Doc Number - Delivery Note">Supplier Delivery Note:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getSupplierLoadNumber() %>&nbsp;</b></td>
   <td class="td04140102">&nbsp;</td>
<%
   if (lotInfo.getCrop().equals("GRAPE"))
   {
%>
   <td class="td04140102"><acronym title="Brix of Received Fruit">Brix:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getLotInformation().getBrix() %></b>&nbsp;</td>
<%
   }else{
%>   
   <td class="td04140102"><acronym title="Machine Run or Orchard Run">M3 - Machine Run or Orchard Run:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getRunType() %>&nbsp;</b></td>
<%
   }
%>   
   <td class="td04140102">&nbsp;</td>   
  </tr>
  <tr>
   <td class="td04140102">&nbsp;</td> 
   <td class="td04140102"><acronym title="Orchard Run Write Up #">Write-Up #:</acronym></td>
   <td class="td04140102"><b><%= lotInfo.getLotWriteUpNumber() %>&nbsp;</b></td>
   <td class="td04140102" colspan = "4">&nbsp;</td>
  </tr>  
<%
   if (lotInfo.getCrop().equals("CHERRY") ||
       lotInfo.getCrop().equals("STRAWBERRY"))
   {
%>  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Culls - Enter a Percent and the Pounds will be Calculated">Percentage of Culls:</acronym></td>
   <td class="td04140102" style="text-align:right"><b><%= lotInfo.getCullsPercent() %></b>&nbsp;</td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Calculation Based upon Percentage of Culls">Pounds of Culls:</acronym></td>
   <td class="td04140102" style="text-align:right"><b><%= lotInfo.getCullsPounds() %></b>&nbsp;</td>     
   <td class="td04140102">&nbsp;</td>
  </tr>	
<%
   }
%>  
 </table>
 <table class="table00" cellspacing="0" cellpadding="2">
  <tr class="tr02">
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">&nbsp;</td>
<%
   if (lotInfo.getCrop().equals("CHERRY") ||
       lotInfo.getCrop().equals("STRAWBERRY") ||
       lotInfo.getCrop().equals("GRAPE"))
   {
%>     
   <td class="td04140102" style="text-align:center" colspan="3"><b>Crates</b></td>
<%
   }else{
%>   
   <td class="td04140102" style="text-align:center"><b>25 Box Bin</b></td>
   <td class="td04140102" style="text-align:center"><b>30 Box Bin</b></td>   
   <td class="td04140102" style="text-align:center"><b>Total Bins</b></td>
<%
   } 
%>   
   <td class="td04140102" style="text-align:center"><b>Weight of Fruit</b></td>
   <td class="td04140102" style="text-align:center"><b>Comment</b></td>
   <td class="td04140102">&nbsp;</td>
  </tr>	    
<%
   BigDecimal tBins = new BigDecimal("0");
   BigDecimal aBins = new BigDecimal("0");
   try
   {
      aBins = (new BigDecimal(lotInfo.getLotAcceptedBins25Box())).add(new BigDecimal(lotInfo.getLotAcceptedBins30Box()));
      tBins = tBins.add(aBins);
   }catch(Exception e)
   {}
   
   Vector listPayment = lotInfo.getListPayments();
   String highlightWeight = "td04140102";
   String mouseoverWeight = "";
   try
   {
      BigDecimal addedPayment = new BigDecimal("0");
      for (int xyPayment = 0; xyPayment < listPayment.size(); xyPayment++)
      {
        // UpdRawFruitPayment should be what is displayed in the variable fields.
         RawFruitPayment rfPayment = (RawFruitPayment) listPayment.elementAt(xyPayment);
         if (!rfPayment.getPaymentWeight().trim().equals(""))
            addedPayment = addedPayment.add(new BigDecimal(rfPayment.getPaymentWeight()));
      }
      BigDecimal totalLot = new BigDecimal(lotInfo.getLotAcceptedWeight());
      if (totalLot.compareTo(addedPayment) != 0)
      {
         highlightWeight = "td0114050203";
         mouseoverWeight = "The total amount of Payment Weight, " + addedPayment + "  does not equal the Lot Weight " + totalLot;
      }
    }
    catch(Exception e)
    {
       System.out.println("Problem with Compare in dtlRawFruitLot.jsp::" + e);
    }
%>
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><b>Accepted:</b></td>
<%
   if (lotInfo.getCrop().equals("CHERRY") ||
       lotInfo.getCrop().equals("STRAWBERRY") ||
       lotInfo.getCrop().equals("GRAPE"))
   {
%>     
   <td class="td04140102" style="text-align:center" colspan="3"><%= HTMLHelpersMasking.maskBigDecimal(lotInfo.getLotAcceptedBins30Box(), 0) %></td>
<%
   }else{
%>   
   <td class="td04140102" style="text-align:center"><%= HTMLHelpersMasking.maskBigDecimal(lotInfo.getLotAcceptedBins25Box(), 0) %></td>   
   <td class="td04140102" style="text-align:center"><%= HTMLHelpersMasking.maskBigDecimal(lotInfo.getLotAcceptedBins30Box(), 0) %></td>
   <td class="td04140102" style="text-align:center"><%= HTMLHelpersMasking.maskBigDecimal(aBins.toString(), 0) %></td>
<%
   }
%>         
   <td class="<%= highlightWeight %>" style="text-align:center"><acronym title="<%= mouseoverWeight %>"><%= HTMLHelpersMasking.maskBigDecimal(lotInfo.getLotAcceptedWeight(), 0) %></acronym></td>
   <td class="td04140102"><%= lotInfo.getLotAcceptedComments() %>&nbsp;</td>
   <td class="td04140102">&nbsp;</td>
  </tr>	  
<%
   BigDecimal rBins = new BigDecimal("0");
   try
   {
      rBins = (new BigDecimal(lotInfo.getLotRejectedBins25Box())).add(new BigDecimal(lotInfo.getLotRejectedBins30Box()));
      tBins = tBins.add(rBins);
   }catch(Exception e)
   {}
%>
  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><b>Rejected:</b></td>
<%
   if (lotInfo.getCrop().equals("CHERRY") ||
       lotInfo.getCrop().equals("STRAWBERRY") ||
       lotInfo.getCrop().equals("GRAPE"))
   {
%>     
   <td class="td04140102" style="text-align:center" colspan="3"><%= HTMLHelpersMasking.maskBigDecimal(lotInfo.getLotRejectedBins30Box(), 0) %></td>
<%
   }else{
%>   
   <td class="td04140102" style="text-align:center"><%= HTMLHelpersMasking.maskBigDecimal(lotInfo.getLotRejectedBins25Box(), 0) %></td>   
   <td class="td04140102" style="text-align:center"><%= HTMLHelpersMasking.maskBigDecimal(lotInfo.getLotRejectedBins30Box(), 0) %></td>
   <td class="td04140102" style="text-align:center"><%= HTMLHelpersMasking.maskBigDecimal(rBins.toString(), 0) %></td>  
<%
   }
%>      
   <td class="td04140102" style="text-align:center"><%= HTMLHelpersMasking.maskBigDecimal(lotInfo.getLotRejectedWeight(), 0) %></td>
   <td class="td04140102"><%= lotInfo.getLotRejectedComments() %>&nbsp;</td>
   <td class="td04140102">&nbsp;</td>
  </tr>	   
  <tr class="tr02">
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><b>TOTAL:</b></td>
<%
   if (lotInfo.getCrop().equals("CHERRY") ||
       lotInfo.getCrop().equals("STRAWBERRY") ||
       lotInfo.getCrop().equals("GRAPE"))
   {
%>     
   <td class="td04140102" style="text-align:center" colspan="3"><b><%= HTMLHelpersMasking.maskBigDecimal(lotInfo.getLotTotalBins30Box(), 0) %></b></td>  
<%
   }else{
%>      
   <td class="td04140102" style="text-align:center"><b><%= HTMLHelpersMasking.maskBigDecimal(lotInfo.getLotTotalBins25Box(), 0) %></b></td>
   <td class="td04140102" style="text-align:center"><b><%= HTMLHelpersMasking.maskBigDecimal(lotInfo.getLotTotalBins30Box(), 0) %></b></td>   
   <td class="td04140102" style="text-align:center"><b><%= HTMLHelpersMasking.maskBigDecimal(tBins.toString(), 0) %></b></td>
<%
   }
%>      
   <td class="td04140102" style="text-align:center"><b><%= HTMLHelpersMasking.maskBigDecimal(lotInfo.getLotTotalWeight(), 0) %></b></td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">&nbsp;</td>
  </tr>	      
 </table>  
<%
  // Payment Section
 
  if (listPayment.size() > 0)
  {
%>  
 <table class="table00" cellspacing="0" cellpadding="2">
<%
  // SHOW ALL THE PAYMENT LINES
     for (int xPayment = 0; xPayment < listPayment.size(); xPayment++)
     {
        RawFruitPayment rfPayment = (RawFruitPayment) listPayment.elementAt(xPayment);
        RawFruitPayCode rfpc = rfPayment.getPayCode();
%>   
  <tr class = "tr02">
   <td class="td05200424" style="text-align:center" colspan="10"><b>Payment Sequence:&nbsp;&nbsp;<%= rfPayment.getPaymentSequenceNumber() %></b></td>
  </tr>
  <tr class = "tr02">
   <td class="td04140424" style="text-align:center"><b>Payment<br>Type</b></td>
   <td class="td04140424" style="text-align:center"><b>Crop</b></td>
   <td class="td04140424" style="text-align:center"><b>Run<br>Type</b></td>
   <td class="td04140424" style="text-align:center"><b>Category</b></td>
   <td class="td04140424" style="text-align:center"><b>Conventional<br>/Organic</b></td>
   <td class="td04140424" style="text-align:center"><b>Variety</b></td>
   <td class="td04140424" style="text-align:center"><b>Keyed Price<br>Per Ton</b></td>     
   <td class="td04140424" style="text-align:right; width:10%"><b>Code</b></td>      
  </tr>
  <tr>
   <td class="td04140424" style="text-align:center"><%= rfpc.getPaymentType() %>&nbsp;</td>
   <td class="td04140424" style="text-align:center"><%= rfpc.getCrop() %>&nbsp;</td>
   <td class="td04140424" style="text-align:center"><%= rfpc.getRunType() %>&nbsp;</td>
   <td class="td04140424" style="text-align:center"><%= rfpc.getCategory() %>&nbsp;</td>
   <td class="td04140424" style="text-align:center"><%= rfpc.getConvOrganic() %>&nbsp;</td>
   <td class="td04140424" style="text-align:center"><%= rfpc.getVariety() %>&nbsp;</td>
   <td class="td04140424" style="text-align:center"><%= HTMLHelpersMasking.maskBigDecimal(rfPayment.getFruitPriceHandKeyed(),2) %>&nbsp;</td>  
<%
   if (rfpc.getPayCode().equals("") && !rfpc.getPaymentType().equals(""))
   {
       String displayTitle = "The combination chosen for Payment Type(" + rfpc.getPaymentType() +
                             "), Crop(" + rfpc.getCrop() +
                             "), Run Type(" + rfpc.getRunType() +
                             "), Conventional/Organic(" + rfpc.getConvOrganic() +
                             "), Variety(" + rfpc.getVariety() +
                             "), Item Number(" + lotInfo.getLotInformation().getItemNumber() + ") is not valid";
%>     
   <td class="td0114042403" style="text-align:right"><acronym title="<%= displayTitle %>"><b>Code for Payment has NOT been Found</b></acronym></td>  
<%
   } else{
%>  
   <td class="td04140424" style="text-align:right"><%= rfpc.getPayCode() %>&nbsp;</td>
<%
   }
%>         
  </tr>
  <tr> 
   <td class="td0514010201" style="text-align:center" colspan = "2"><b>&nbsp;</b></td> 
   <td class="td04140102" colspan = "6">
    <table class="table00" cellspacing="0" cellpadding="1">
     <tr class = "tr02">
      <td class="td04140102" style="width:2%">&nbsp;</td>
<%
   if (lotInfo.getCrop().equals("CHERRY") ||
       lotInfo.getCrop().equals("STRAWBERRY") ||
       lotInfo.getCrop().equals("GRAPE"))
   {
%>      
     <td class="td04140102" style="text-align:center" colspan="2"><b># Crates</b></td>  
<%
   } else{
%>       
      <td class="td04140102" style="text-align:center"><b># Bins @<br>25 Box<br>per Bin</b></td> 
      <td class="td04140102" style="text-align:center"><b># Bins @<br>30 Box<br>per Bin</b></td>  
<%
   }
%>  
      <td class="td04140102" style="text-align:center"><b>Write-Up<br>Number</b></td>      
      <td class="td04140102" style="text-align:center"><b>Payment<br>Weight</b></td>     
      <td class="td04140102" style="text-align:right; width=10%">&nbsp;
<%
   // Make sure to put Pay Code in IF there is a correction
      if (!rfPayment.getPayCodeHandKeyed().trim().equals(""))
      	out.println("<b>Override of the Payment Code</b>");
%>      
      </td>     
     </tr>
     <tr>
      <td class="td04140102">&nbsp;</td>
<%
   if (lotInfo.getCrop().equals("CHERRY") ||
       lotInfo.getCrop().equals("STRAWBERRY") ||
       lotInfo.getCrop().equals("GRAPE"))
   {
%>          
     <td class="td04140102" style="text-align:center" colspan="2"><%= rfPayment.getPaymentBins30Box() %>&nbsp;</td> 
<%
   } else{
%>        
      <td class="td04140102" style="text-align:center"><%= rfPayment.getPaymentBins25Box() %>&nbsp;</td>
      <td class="td04140102" style="text-align:center"><%= rfPayment.getPaymentBins30Box() %>&nbsp;</td> 
<%
   }
%>  
      <td class="td04140102" style="text-align:center"><%= rfPayment.getPaymentWriteUpNumber() %>&nbsp;</td> 
      <td class="td04140102" style="text-align:center"><%= HTMLHelpersMasking.maskBigDecimal(rfPayment.getPaymentWeight(),0) %>&nbsp;</td>
      <td class="td04140102" style="text-align:right">&nbsp;
<%
   // Make sure to put Pay Code in IF there is a correction
    if (!rfPayment.getPayCodeHandKeyed().trim().equals(""))
      	out.println(rfPayment.getPayCodeHandKeyed());
%>              
      </td>
     </tr>
    </table>
   </td>
  </tr>       
<%
  // Payment Section
%>  
       <tr> 
        <td class="td0514010201" style="text-align:center" colspan = "2"><b>Special<br>Charges</b></td> 
        <td class="td04140102" colspan = "6">
<%
    if (rfPayment.getListSpecialCharges().size() > 0)
    {
%>        
         <table class="table00" cellspacing="0" cellpadding="1">
          <tr class = "tr02">
           <td class="td04160102" style="width:2%">&nbsp;</td>
           <td class="td04160102" style="text-align:center">Supplier</td>
           <td class="td04160102" style="text-align:center"><acronym title="This rate should be Entered by Ton -- on the Invoice Page it will be Calculated to by Pound and used for the Other Charges Calculations">Rate ($'s per Ton)</acronym></td>
           <td class="td04160102" style="text-align:center">Code (M3 Code for the Charge-Accounting Option)</td>
           <td class="td04160102" style="width:2%">&nbsp;</td>       
          </tr>
<%
  // Display information that is given
     for (int yPay = 0; yPay < rfPayment.getListSpecialCharges().size(); yPay++)
     {
        RawFruitSpecialCharges rfsc = (RawFruitSpecialCharges) rfPayment.getListSpecialCharges().elementAt(yPay);
%>   
          <tr>
           <td class="td04140102">&nbsp;</td>
           <td class="td04140102" style="text-align:center"><%= rfsc.getSupplierSpecialCharges().getSupplierNumber() %>&nbsp;&nbsp;&nbsp;<%= rfsc.getSupplierSpecialCharges().getSupplierName() %></td>
           <td class="td04140102" style="text-align:center"><%= HTMLHelpersMasking.maskBigDecimal(rfsc.getRate(), 2) %></td>
           <td class="td04140102" style="text-align:center"><%= rfsc.getAccountingOption() %>&nbsp;-&nbsp;<%= rfsc.getAccountingOptionDescription() %></td>
           <td class="td04140102">&nbsp;</td> 
          </tr>
<%
      }
%>       
         </table>   
<%
    } // End of If there is any additional Charges Information
%>         
        </td>
        <td class="td04140102">&nbsp;</td>
       </tr>	         
<%
   } // END of the for loop for Payment
%>   
  </table>   
<%
  } // End of If there is any payment information for display
%>  
 </body>
</html>
<%
  }
  catch(Exception e)
  {
  }
%>