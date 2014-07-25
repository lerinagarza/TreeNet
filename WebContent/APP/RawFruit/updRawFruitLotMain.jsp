<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.math.*" %>
<%@ page import = "java.util.*" %>
<%
//---------------- updRawFruitLotMain.jsp -------------------------------------------//
//    Author :  Teri Walton  7/31/09
//       split off and used with updRawFruitLot.jsp
//     This is the Payment Section
//
//   CHANGES:
//
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
 UpdRawFruitLot ulMain = new UpdRawFruitLot();
 RawFruitLot rflMain = new RawFruitLot();
 try
 {
	ulMain = (UpdRawFruitLot) request.getAttribute("updViewBean");
	rflMain = ulMain.getUpdBean().getRfLot();
  }
 catch(Exception e)
 {
  //  System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
 	//request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    

 String readOnlyLotMain = "N";
%>
<html>
 <head>
   <%= JavascriptInfo.getNumericCheck() %>
   <%= JavascriptInfo.getRequiredField() %>
   <%= JavascriptInfo.getClickButtonOnlyOnce() %>
   <%= JavascriptInfo.getChangeSubmitButton() %>   
 </head>
 <body>
 <table class="table00" cellspacing="0" cellpadding="2">
<% 
   String highlightWeight = "td0414";
   String mouseoverWeight = "";
   try
   {
      BigDecimal addedPayment = new BigDecimal("0");
      for (int xyPayment = 0; xyPayment < ulMain.getListPayment().size(); xyPayment++)
      {
        // UpdRawFruitPayment should be what is displayed in the variable fields.
         UpdRawFruitLotPayment rfPayment = (UpdRawFruitLotPayment) ulMain.getListPayment().elementAt(xyPayment);
         if (!rfPayment.getPaymentWeight().trim().equals(""))
            addedPayment = addedPayment.add(new BigDecimal(rfPayment.getPaymentWeight()));
      }
      BigDecimal totalLot = new BigDecimal(ulMain.getAcceptedWeight());
      
      if (totalLot.compareTo(addedPayment) != 0)
      {
         highlightWeight = "td0114050203";
         mouseoverWeight = "The total amount of Payment Weight, " + addedPayment + "  does not equal the Lot Weight " + totalLot;
      }

    }
    catch(Exception e)
    {
       System.out.println("Problem with Compare in updRawFruitLot.jsp::" + e);
    }
        
%>  
  <tr class="tr02">
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102" style="text-align:left"><%= HTMLHelpers.buttonSubmit("saveButton", "Save / Lot Data") %></td>
<%   
   if (ulMain.getCrop().equals("CHERRY") ||
       ulMain.getCrop().equals("STRAWBERRY") ||
       ulMain.getCrop().equals("GRAPE"))
   {
%> 
   <td class="td04140102" colspan="3"><b>Crates</b></td>
<%
   }else{
%>   
   <td class="td04140102"><b>25 Box Bin</b></td>   
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><b>30 Box Bin</b></td>
<%
   }
%>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title=" If a lot has a specific weight it needs to be entered here, AND the box needs to be checked(keep in mind the load weight should equal all the lots).  Otherwide leave this field alone.">
     <b>Weight</b></acronym></td>
   <td class="td04140124" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save / Lot Data") %></td> 
   <td class="td04140102">&nbsp;</td>
  </tr>	    
  <tr>
   <td class="td0414">&nbsp;</td>
   
<%   
   if (ulMain.getCrop().equals("CHERRY") ||
       ulMain.getCrop().equals("STRAWBERRY") ||
       ulMain.getCrop().equals("GRAPE"))
   {
%> 
   <td class="td0414"><acronym title="Total Accepted Crates for this Lot">Accepted Crates:</acronym></td>
   <td class="td0414" colspan="3"><%= HTMLHelpersInput.inputBoxNumber("acceptedBins25", ulMain.getAcceptedBins25(), "Total Accepted Crates for this Lot", 5,5,"Y", readOnlyLotMain) %>&nbsp;<%= ulMain.getAcceptedBins25Error() %></td>
<%
   }else{
%>  
   <td class="td0414"><acronym title="Total Accepted Bins for this Lot">Accepted Bins:</acronym></td>    
   <td class="td0414"><%= HTMLHelpersInput.inputBoxNumber("acceptedBins25", ulMain.getAcceptedBins25(), "Total Accepted Bins (25 boxes per) for this Lot", 5,5,"Y", readOnlyLotMain) %>&nbsp;<%= ulMain.getAcceptedBins25Error() %></td>   
   <td class="td0414">&nbsp;</td>
   <td class="td0414"><%= HTMLHelpersInput.inputBoxNumber("acceptedBins30", ulMain.getAcceptedBins30(), "Total Accepted Bins (30 boxes per) for this Lot", 5,5,"Y", readOnlyLotMain) %>&nbsp;<%= ulMain.getAcceptedBins30Error() %></td>
<%
   }
%>         
   <td class="td0414">&nbsp;</td>
   <td class="<%= highlightWeight %>"><acronym title="<%= mouseoverWeight %> If a lot has a specific weight it needs to be entered here, AND the box needs to be checked(keep in mind the load weight should equal all the lots).  Otherwide leave this field alone.">
     <%= HTMLHelpersInput.inputCheckBox("acceptedWeightKeyed", ulMain.getAcceptedWeightKeyed(), readOnlyLotMain) %>&nbsp;
     <%= HTMLHelpersInput.inputBoxNumber("acceptedWeight", ulMain.getAcceptedWeight(), "Total Accepted Weight of Lot", 8,8,"Y", readOnlyLotMain) %>&nbsp;<%= ulMain.getAcceptedWeightError() %></acronym></td>
  </tr>	  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Comments Related to the Accepted Bins for this Lot">Comment:</acronym></td>
   <td class="td04140102" colspan="6"><%= HTMLHelpersInput.inputBoxText("acceptedBinsComment", ulMain.getAcceptedBinsComment(), "Comment", 100, 200, "N", readOnlyLotMain) %></td>   
   <td class="td04140102">&nbsp;</td>
  </tr>	    
  <tr>
   <td class="td0414">&nbsp;</td>
  
<%   
   if (ulMain.getCrop().equals("CHERRY") ||
       ulMain.getCrop().equals("STRAWBERRY") ||
       ulMain.getCrop().equals("GRAPE"))
   {
%> 
   <td class="td0414"><acronym title="Total Rejected Crates for this Lot">Rejected Crates:</acronym></td>
   <td class="td0414" colspan="3"><%= HTMLHelpersInput.inputBoxNumber("rejectedBins25", ulMain.getRejectedBins25(), "Total Rejected Crates for this Lot", 5,5,"Y", readOnlyLotMain) %>&nbsp;<%= ulMain.getRejectedBins25Error() %></td>
<%
   }else{
%>      
    <td class="td0414"><acronym title="Total Rejected Bins for this Lot">Rejected Bins:</acronym></td>
   <td class="td0414"><%= HTMLHelpersInput.inputBoxNumber("rejectedBins25", ulMain.getRejectedBins25(), "Total Rejected Bins (25 boxes per) for this Lot", 5,5,"Y", readOnlyLotMain) %>&nbsp;<%= ulMain.getRejectedBins25Error() %></td>   
   <td class="td0414">&nbsp;</td>
   <td class="td0414"><%= HTMLHelpersInput.inputBoxNumber("rejectedBins30", ulMain.getRejectedBins30(), "Total Rejected Bins (30 boxes per) for this Lot", 5,5,"Y", readOnlyLotMain) %>&nbsp;<%= ulMain.getRejectedBins30Error() %></td>
<%
   }
%>             
   <td class="td0414">&nbsp;</td>
   <td class="td0414"><acronym title=" If a lot has a specific weight it needs to be entered here, AND the box needs to be checked(keep in mind the load weight should equal all the lots).  Otherwide leave this field alone.">
     <%= HTMLHelpersInput.inputCheckBox("rejectedWeightKeyed", ulMain.getRejectedWeightKeyed(), readOnlyLotMain) %>&nbsp;
     <%= HTMLHelpersInput.inputBoxNumber("rejectedWeight", ulMain.getRejectedWeight(), "Total Rejected Weight of Lot", 8,8,"Y", readOnlyLotMain) %>&nbsp;<%= ulMain.getRejectedWeightError() %></acronym></td>   
  </tr>	 
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Comments Related to the Rejected Bins for this Lot">Comment:</acronym></td>
   <td class="td04140102" colspan="6"><%= HTMLHelpersInput.inputBoxText("rejectedBinsComment", ulMain.getRejectedBinsComment(), "Comment", 100, 200, "N", readOnlyLotMain) %></td>   
   <td class="td04140102">&nbsp;</td>
  </tr>	  
  <tr>
   <td class="td04140424">&nbsp;</td>
   <td class="td04140424"><acronym title="Total Bins for this Lot"><b>Total Bins:</b></acronym></td>
<%   
   if (ulMain.getCrop().equals("CHERRY") ||
       ulMain.getCrop().equals("STRAWBERRY") ||
       ulMain.getCrop().equals("GRAPE"))
   {
%> 
   <td class="td04140424"><b><%= rflMain.getLotTotalBins30Box() %></b>&nbsp;</td>  
<%
   }else{
%>      
   <td class="td04140424"><b><%= rflMain.getLotTotalBins25Box() %>&nbsp;</b></td>   
   <td class="td04140424">&nbsp;</td>
   <td class="td04140424"><b><%= rflMain.getLotTotalBins30Box() %></b>&nbsp;</td>  
<%
   }
%>            
   <td class="td04140424">&nbsp;</td>
   <td class="td04140424"><b><%= HTMLHelpersMasking.maskBigDecimal(rflMain.getLotTotalWeight(), 0) %>&nbsp;</b></td>
   <td class="td04140424"><b><%= HTMLHelpersMasking.maskBigDecimal(rflMain.getAverageWeightPerBin(),2) %></b>&nbsp;Pounds Per Bin</td>
   <td class="td04140424">&nbsp;</td>
  </tr>	   
  <tr>
   <td class="td0414">&nbsp;</td>
   <td class="td0414" colspan="3"style="text-align:left"><%= HTMLHelpers.buttonSubmit("saveButton", "Save / Lot Data") %>&nbsp;</td>
   <td class="td0414" colspan="4" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save / Lot Data") %></td>
   <td class="td0414">&nbsp;</td>
  </tr>	    
<%
  //------------------------------------------------------------
  // Payment Section
  //------------------------------------------------------------
   Vector listPayment = ulMain.getListPayment();
   int countPayments = 0;
%>
  <tr>
   <td class="td04140102" colspan = "9">
    <table class="table00" cellspacing="0" cellpadding="1">
<%
  if (listPayment.size() > 0)
  {
     for (int xPayment = 0; xPayment < listPayment.size(); xPayment++)
     {
        // UpdRawFruitPayment should be what is displayed in the variable fields.
        UpdRawFruitLotPayment rfPayment = (UpdRawFruitLotPayment) listPayment.elementAt(xPayment);
        RawFruitPayCode rfpc = rfPayment.getPaymentInfo().getPayCode();     
        countPayments++;
%>
    <tr class = "tr02">
     <td class="td05200424" style="text-align:center" colspan="8"><b>Payment Sequence:&nbsp;&nbsp;<%= rfPayment.getPaymentSequence() %></b></td>
    </tr>
    <tr class = "tr02">
     <td class="td04140424" style="text-align:center"><b>Payment<br>Type</b></td>
     <td class="td04140424" style="text-align:center"><b>Crop</b></td>
     <td class="td04140424" style="text-align:center"><b>Run<br>Type</b></td>
     <td class="td04140424" style="text-align:center"><b>Category</b></td>
     <td class="td04140424" style="text-align:center"><b>Conventional<br>/Organic</b></td>
     <td class="td04140424" style="text-align:center"><b>Variety</b></td>
     <td class="td04140424" style="text-align:center"><b>Keyed Price<br>Per Ton</b></td>     
     <td class="td04140424" style="text-align:right"><b>Code</b></td>      
    </tr>
    <tr>
     <td class="td04140502" style="text-align:center"><%= ulMain.buildDropDownPaymentType((xPayment + "paymentType"), rfPayment.getPaymentType(), readOnlyLotMain) %></td>
     <td class="td04140502" style="text-align:center"><%= ulMain.buildDropDownCrop((xPayment + "crop"), rfPayment.getCrop(), readOnlyLotMain) %></td>
     <td class="td04140502" style="text-align:center"><%= ulMain.buildDropDownRunType((xPayment + "runType"), rfPayment.getRunType(), readOnlyLotMain) %></td>
     <td class="td04140502" style="text-align:center"><%= ulMain.buildDropDownCategory((xPayment + "category"), rfPayment.getCategory(), readOnlyLotMain) %></td>
     <td class="td04140502" style="text-align:center"><%= ulMain.buildDropDownConvOrganic((xPayment + "organic"), rfPayment.getOrganic(), readOnlyLotMain) %></td>
     <td class="td04140502" style="text-align:center"><%= ulMain.buildDropDownVariety((xPayment + "variety"), rfPayment.getVariety(), readOnlyLotMain) %></td>
     <td class="td03140502" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((xPayment + "price"), rfPayment.getPrice(), "", 10,10,"N", readOnlyLotMain) %>&nbsp;<%= rfPayment.getPriceError() %></td>   
<%
   if (rfpc.getPayCode().equals("") && !rfPayment.getPaymentType().equals(""))
   {
       String displayTitle = "The combination chosen for Payment Type(" + rfPayment.getPaymentType() +
                             "), Crop(" + rfPayment.getCrop() +
                             "), Run Type(" + rfPayment.getRunType() +
                             "), Conventional/Organic(" + rfPayment.getOrganic() +
                             "), Variety(" + rfPayment.getVariety() +
                             "), Item Number(" + ulMain.getItemNumber() + ") is not valid";
%>     
   <td class="td0114050203" style="text-align:right"><acronym title="<%= displayTitle %>"><b>Code for Payment has NOT been Found</b></acronym></td>  
<%
   } else{
%>  
     <td class="td04140502" style="text-align:right"><%= rfpc.getPayCode() %>&nbsp;</td>
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
   if (ulMain.getCrop().equals("CHERRY") ||
       ulMain.getCrop().equals("STRAWBERRY") ||
       ulMain.getCrop().equals("GRAPE"))
   {
%> 
       <td class="td04140102" style="text-align:center" colspan="2"><b># Crates</b></td> 
<%
   }else{
%>
        <td class="td04140102" style="text-align:center"><b># Bins @<br>25 Box<br>per Bin</b></td> 
        <td class="td04140102" style="text-align:center"><b># Bins @<br>30 Box<br>per Bin</b></td>  
<%
   }
%>     
		<td class="td04140102" style="text-align:center"><b>Write-Up #</b>&nbsp;</td>    
        <td class="td04140102" style="text-align:center"><b>Payment<br>Weight</b></td>     
        <td class="td04140102" style="text-align:right">&nbsp;
<%
   // Make sure to put Pay Code in IF there is a correction
 //    System.out.println("Correction Count" + ulMain.getUpdBean().getrflMainot().getCorrectionCount());
      if (!ulMain.getUpdBean().getRfLot().getCorrectionCount().trim().equals("0"))
      	out.println("<b>Override of the Payment Code</b>");
%>      
        </td>     
       </tr>
       <tr>
        <td class="td04140102">&nbsp;</td>
<%   
   if (ulMain.getCrop().equals("CHERRY") ||
       ulMain.getCrop().equals("STRAWBERRY") ||
       ulMain.getCrop().equals("GRAPE"))
   {
%> 
       <td class="td03140102" style="text-align:center" colspan="2"><%= HTMLHelpersInput.inputBoxNumber((xPayment + "numberAt30PerBin"), rfPayment.getNumberOfBins30Box(), "", 5,5,"N", readOnlyLotMain) %>&nbsp;<%= rfPayment.getNumberOfBins30BoxError() %></td> 
<%
   }else{
%>       
        <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((xPayment + "numberAt25PerBin"), rfPayment.getNumberOfBins25Box(), "", 5,5,"N", readOnlyLotMain) %>&nbsp;<%= rfPayment.getNumberOfBins25BoxError() %></td>
        <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((xPayment + "numberAt30PerBin"), rfPayment.getNumberOfBins30Box(), "", 5,5,"N", readOnlyLotMain) %>&nbsp;<%= rfPayment.getNumberOfBins30BoxError() %></td>
<%
   }
%>       
        <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((xPayment + "writeUpNumber"), rfPayment.getWriteUpNumber(), "Orchard Run Write Up Number", 8,8,"N", readOnlyLotMain) %>&nbsp;<%= rfPayment.getWriteUpNumberError() %></td>
        <td class="td03140102" style="text-align:center"><acronym title="Click this box(and SAVE) if you want to manually Enter a Weight">
         <%= HTMLHelpersInput.inputCheckBox((xPayment + "paymentWeightManuallyEntered"), rfPayment.getPaymentWeightManuallyEntered(), readOnlyLotMain) %></acronym>&nbsp;
<%
   String readOnlyForThisRecord = readOnlyLotMain;
   String mouseoverValue = "";
   if (rfPayment.getPaymentWeightManuallyEntered().trim().equals(""))
   {
      readOnlyForThisRecord = "Y";
      mouseoverValue = "Weight is Calculated: Bins is calculated into Boxes and then the number of boxes is multiplied by the Average Weight per Box";
   }
%> 
         <acronym title="<%= mouseoverValue %>">
         <%= HTMLHelpersInput.inputBoxNumber((xPayment + "paymentWeight"), rfPayment.getPaymentWeight(), "Payment Weight for this Payment", 8,8,"Y", readOnlyForThisRecord) %>&nbsp;<b><%= rfPayment.getPaymentWeightError() %></b>
         </acronym>
        </td>
        <td class="td04140102" style="text-align:right">&nbsp;
<%
   // Make sure to put Pay Code in IF there is a correction
      if (!ulMain.getUpdBean().getRfLot().getCorrectionCount().trim().equals("0"))
      {
      	out.println(HTMLHelpersInput.inputBoxNumber((xPayment + "payCodeHandKeyed"), rfPayment.getPayCodeHandKeyed(), "",5,5,"N", readOnlyLotMain));
      	out.println("&nbsp;");
      	out.println(rfPayment.getPayCodeHandKeyedError());
      }
%>              
        </td>
       </tr>
      </table>
     </td>
    </tr>
<%
     // Special Charges -- at Least 2 Lines
%>    
    <tr> 
     <td class="td0514010201" style="text-align:center" colspan = "2"><b>Special<br>Charges</b></td> 
     <td class="td04140102" colspan = "6">
      <table class="table00" cellspacing="0" cellpadding="1">
       <tr class = "tr02">
        <td class="td04160102" style="width:2%">&nbsp;</td>
        <td class="td04160102" style="text-align:center">Supplier</td>
        <td class="td04160102" style="text-align:center"><acronym title="This rate should be Entered by Ton -- on the Invoice Page it will be Calculated to by Pound and used for the Other Charges Calculations">Rate ($'s per Ton)</acronym></td>
        <td class="td04160102" style="text-align:center">Code (M3 Code for the Charge)</td>
        <td class="td04160102" style="width:2%">&nbsp;</td>       
       </tr>
<%
    // Figure out how many special Charges have Already been entered..
    Vector specialCharges = rfPayment.getListSpecialCharges();
    int countCharges = 0;
    if (specialCharges.size() > 0)
    {
      for (int chg = 0; chg < specialCharges.size(); chg++)
      {
        countCharges++;
        UpdRawFruitLotPaymentSpecialCharges updCharge = (UpdRawFruitLotPaymentSpecialCharges) specialCharges.elementAt(chg);
%>   
       <tr>
        <td class="td04140102">&nbsp;</td>
<%
         if (updCharge.getSupplierError().trim().equals(""))
         {
%>   
        <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((xPayment + "supplier" + chg), updCharge.getSupplier(), "", 6,6,"N", readOnlyLotMain) %>&nbsp;<%= updCharge.getSpecialChargesInfo().getSupplierSpecialCharges().getSupplierName() %></td>     
<%
         }
         else
         {
%>
        <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((xPayment + "supplier" + chg), updCharge.getSupplier(), "", 6,6,"N", readOnlyLotMain) %>&nbsp;<%= updCharge.getSupplierError() %></td>
<%
         }
%>        
        <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((xPayment + "ratePerPound" + chg), updCharge.getRatePerPound(), "", 6,6,"N", readOnlyLotMain) %>&nbsp;<%= updCharge.getRatePerPoundError() %></td>
        <td class="td04140102" style="text-align:center"><%= ulMain.buildDropDownChargeCode((xPayment + "accountingOption" + chg), updCharge.getAccountingOption(), readOnlyLotMain) %></td>
        <td class="td04140102">&nbsp;</td> 
       </tr>
<%
        } // END of the For Loop
      } // END of if Special Charges  > 0
     
      int blankCountCharge = 2;
      if (countCharges >= 2)
        blankCountCharge = countCharges + 1;
      for (int xChg = countCharges; xChg < blankCountCharge; xChg++)
      { 
        countCharges++; 
%>   
       <tr>
        <td class="td04140102">&nbsp;</td>
        <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((xPayment + "supplier" + xChg), rflMain.getLotSupplier().getSupplierNumber(), "", 6,6,"N", readOnlyLotMain) %></td>
        <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((xPayment + "ratePerPound" + xChg), "", "", 6,6,"N", readOnlyLotMain) %></td>
        <td class="td04140102" style="text-align:center"><%= ulMain.buildDropDownChargeCode((xPayment + "accountingOption" + xChg), "", readOnlyLotMain) %></td>
        <td class="td04140102">&nbsp;</td> 
       </tr>
<%
      }
%>       
        <%= HTMLHelpersInput.inputBoxHidden((xPayment + "countSpecialCharges"), (countCharges + "")) %>
      </table>   
     </td>
    </tr>	             
<%
     } // End of the For Loop
  } // End of the IF Payment > 0
  //--------------------------------------------------------------
  // LOOP THROUGH AND ADD Blanks to the section   
  // BLANK PAYMENT SECTION
   int blankCount = 3;
   if (countPayments >= 3)
      blankCount = countPayments + 2;
   for (int x = countPayments; x < blankCount; x++)
   { 
     countPayments++;
%>  
    <tr class = "tr02">
     <td class="td05200424" style="text-align:center" colspan="8"><b>New Payment Sequence:&nbsp;</b></td>
    </tr>
    <tr class = "tr02">
     <td class="td04140424" style="text-align:center"><b>Payment<br>Type</b></td>
     <td class="td04140424" style="text-align:center"><b>Crop</b></td>
     <td class="td04140424" style="text-align:center"><b>Run<br>Type</b></td>
     <td class="td04140424" style="text-align:center"><b>Category</b></td>
     <td class="td04140424" style="text-align:center"><b>Conventional<br>/Organic</b></td>
     <td class="td04140424" style="text-align:center"><b>Variety</b></td>
     <td class="td04140424" style="text-align:center"><b>Keyed Price<br>Per Ton</b></td>     
     <td class="td04140424" style="text-align:center"><b>Code</b></td>      
    </tr>
    <tr>
     <td class="td04140424" style="text-align:center"><%= ulMain.buildDropDownPaymentType((x + "paymentType"), "", readOnlyLotMain) %></td>
     <td class="td04140424" style="text-align:center"><%= ulMain.buildDropDownCrop((x + "crop"), "", readOnlyLotMain) %></td>
     <td class="td04140424" style="text-align:center"><%= ulMain.buildDropDownRunType((x + "runType"), "", readOnlyLotMain) %></td>
     <td class="td04140424" style="text-align:center"><%= ulMain.buildDropDownCategory((x + "category"), "", readOnlyLotMain) %></td>
     <td class="td04140424" style="text-align:center"><%= ulMain.buildDropDownConvOrganic((x + "organic"), "", readOnlyLotMain) %></td>
     <td class="td04140424" style="text-align:center"><%= ulMain.buildDropDownVariety((x + "variety"), "", readOnlyLotMain) %></td>
     <td class="td03140424" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((x + "price"), "", "", 10,10,"N", readOnlyLotMain) %>&nbsp;</td>   
     <td class="td04140424" style="text-align:center">&nbsp;</td>           
    </tr>
    <tr> 
     <td class="td0514010201" style="text-align:center" colspan = "2"><b>&nbsp;</b></td> 
     <td class="td04140102" colspan = "6">
      <table class="table00" cellspacing="0" cellpadding="1">
       <tr class = "tr02">
        <td class="td04140102" style="width:2%">&nbsp;</td>
<%   
   if (ulMain.getCrop().equals("CHERRY") ||
       ulMain.getCrop().equals("STRAWBERRY") ||
       ulMain.getCrop().equals("GRAPE"))
   {
%> 
        <td class="td04140102" style="text-align:center" colspan="2"><b># Crates</b></td>  
<%
   }else{
%>      
        <td class="td04140102" style="text-align:center"><b># Bins @<br>25 Box<br>per Bin</b></td> 
        <td class="td04140102" style="text-align:center"><b># Bins @<br>30 Box<br>per Bin</b></td>
<%
   }
%>       
        <td class="td04140102" style="text-align:center"><b>Write-Up #</b>&nbsp;</td>       
        <td class="td04140102" style="text-align:center"><b>Payment<br>Weight</b></td>     
        <td class="td04140102" style="text-align:center">&nbsp;</td>     
       </tr>
       <tr>
        <td class="td04140102">&nbsp;</td>
<%   
   if (ulMain.getCrop().equals("CHERRY") ||
       ulMain.getCrop().equals("STRAWBERRY") ||
       ulMain.getCrop().equals("GRAPE"))
   {
%> 
       <td class="td03140102" style="text-align:center" colspan="2"><%= HTMLHelpersInput.inputBoxNumber((x + "numberAt30PerBin"), "", "", 5,5,"N", readOnlyLotMain) %>&nbsp;</td> 
<%
   }else{
%>           
        <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((x + "numberAt25PerBin"), "", "", 5,5,"N", readOnlyLotMain) %>&nbsp;</td>
        <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((x + "numberAt30PerBin"), "", "", 5,5,"N", readOnlyLotMain) %>&nbsp;</td>
<%
   }
%>  
        <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((x + "writeUpNumber"), "", "Orchard Run Write Up Number", 8,8,"N", readOnlyLotMain) %>&nbsp;</td>          
        <td class="td04140102" style="text-align:center">
         <%= HTMLHelpersInput.inputCheckBox((x + "paymentWeightManuallyEntered"), "", readOnlyLotMain) %>&nbsp;
         <%= HTMLHelpersInput.inputBoxNumber((x + "paymentWeight"), "", "Payment Weight for this Payment", 8,8,"Y", readOnlyLotMain) %>
        </td>
        <td class="td04140102" style="text-align:center">&nbsp;</td>
       </tr>
      </table>
     </td>
    </tr>   
<%
                  // Special Charges -- SHOW 2 Lines -- BLANK
%>    
    <tr> 
     <td class="td0514010201" style="text-align:center" colspan = "2"><b>Special<br>Charges</b></td> 
     <td class="td04140102" colspan = "6">
      <table class="table00" cellspacing="0" cellpadding="1">
       <tr class = "tr02">
        <td class="td04160102" style="text-align:center">Supplier</td>
        <td class="td04160102" style="text-align:center"><acronym title="This rate should be Entered by Ton -- on the Invoice Page it will be Calculated to by Pound and used for the Other Charges Calculations">Rate ($'s per Ton)</acronym></td>
        <td class="td04160102" style="text-align:center">Code (M3 Code for the Charge)</td>
       </tr>
<%
  // filter through -- show at least 2 lines -- IF 2 Lines get Filled -- add 1 more
     for (int y = 0; y < 2; y++)
     {
%>   
       <tr>
        <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((x + "supplier" + y), rflMain.getLotSupplier().getSupplierNumber(), "", 6,6,"N", readOnlyLotMain) %></td>
        <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((x + "ratePerPound" + y), "", "", 6,6,"N", readOnlyLotMain) %></td>
        <td class="td04140102" style="text-align:center"><%= ulMain.buildDropDownChargeCode((x + "chargeCode" + y), "", readOnlyLotMain) %></td>
       </tr>
<%
      }
%>       
       <%= HTMLHelpersInput.inputBoxHidden((x + "countSpecialCharges"), "2") %>
      </table>   
     </td>
    </tr>	         
<%
   } // END of the BLANK Section for Payment
%>   
    <tr>
      <td class="td0414">&nbsp;</td>
     <td class="td0414" colspan="5"style="text-align:left"><%= HTMLHelpers.buttonSubmit("saveButton", "Save / Lot Data") %>&nbsp;</td>
    
    <td class="td04140102" colspan="5" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save / Lot Data") %></td>
     <td>&nbsp;</td> 
    </tr>    
     <%= HTMLHelpersInput.inputBoxHidden("countPayments", (countPayments + "")) %>
   </table>   
  </td>
 </tr>	
</table>

   </body>
</html>