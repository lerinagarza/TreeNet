<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.math.*" %>
<%@ page import = "java.util.*" %>
<%
//---------------- updRawFruitLotGrape.jsp -------------------------------------------//
// // split off and used with updRawFruitLot.jsp
//    Author :  Teri Walton  8/3/09
//
//   CHANGES:
//
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 UpdRawFruitLot ulGrape = new UpdRawFruitLot();
 RawFruitLot rflGrape = new RawFruitLot();
 try
 {
	ulGrape = (UpdRawFruitLot) request.getAttribute("updViewBean");
	rflGrape = ulGrape.getUpdBean().getRfLot();
  }
 catch(Exception e)
 {
//    System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
// 	request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
 }    
//**************************************************************************//
 String readOnlyLotGrape = "N";
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
     for (int xyPayment = 0; xyPayment < ulGrape.getListPayment().size(); xyPayment++)
     {
        // UpdRawFruitPayment should be what is displayed in the variable fields.
       UpdRawFruitLotPayment rfPayment = (UpdRawFruitLotPayment) ulGrape.getListPayment().elementAt(xyPayment);
       if (!rfPayment.getPaymentWeight().trim().equals(""))
         addedPayment = addedPayment.add(new BigDecimal(rfPayment.getPaymentWeight()));
     }
     BigDecimal totalLot = new BigDecimal(ulGrape.getAcceptedWeight());
      
     if (totalLot.compareTo(addedPayment) != 0)
     {
       highlightWeight = "td0114050203";
       mouseoverWeight = "The total amount of Payment Weight, " + addedPayment + "  does not equal the Lot Weight " + totalLot;
     }
   } catch(Exception e) {
     System.out.println("Problem with Compare in updRawFruitLot.jsp::" + e);
   }
%>  
  <tr class="tr02">
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102" style="text-align:left"><%= HTMLHelpers.buttonSubmit("saveButton", "Save / Lot Data") %></td>
   <td class="td04140102" colspan="3" style="text-align: center"><b>Bins for this Lot</b></td> 
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title=" If a lot has a specific weight it needs to be entered here, AND the box needs to be checked(keep in mind the load weight should equal all the lots).  Otherwide leave this field alone.">
     <b>Weight</b></acronym></td>
   <td class="td04140124" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save / Lot Data") %></td> 
   <td class="td04140102">&nbsp;</td>
  </tr>	    
  <tr>
   <td class="td0414">&nbsp;</td>
   <td class="td0414"><acronym title="Total Accepted Bins for this Lot">Accepted Bins:</acronym></td>
   <td class="td0414" colspan="3" style="text-align: center"><%= HTMLHelpersInput.inputBoxNumber("acceptedBins25", ulGrape.getAcceptedBins25(), "Total Bins for this Lot", 3,3,"Y", readOnlyLotGrape) %>&nbsp;<%= ulGrape.getAcceptedBins25Error() %></td> 
   <td class="td0414">&nbsp;</td>
   <td class="<%= highlightWeight %>"><acronym title="<%= mouseoverWeight %> If a lot has a specific weight it needs to be entered here, AND the box needs to be checked(keep in mind the load weight should equal all the lots).  Otherwide leave this field alone.">
     <%= HTMLHelpersInput.inputCheckBox("acceptedWeightKeyed", ulGrape.getAcceptedWeightKeyed(), readOnlyLotGrape) %>&nbsp;
     <%= HTMLHelpersInput.inputBoxNumber("acceptedWeight", ulGrape.getAcceptedWeight(), "Total Accepted Weight of Lot", 8,8,"Y", readOnlyLotGrape) %>&nbsp;<%= ulGrape.getAcceptedWeightError() %></acronym></td>
  </tr>	  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Comments Related to the Accepted Bins for this Lot">Comment:</acronym></td>
   <td class="td04140102" colspan="6"><%= HTMLHelpersInput.inputBoxText("acceptedBinsComment", ulGrape.getAcceptedBinsComment(), "Comment", 100, 200, "N", readOnlyLotGrape) %></td>   
   <td class="td04140102">&nbsp;</td>
  </tr>	 
<% 
  //------------------------------------------------------------
  // Payment Section
  //------------------------------------------------------------
   Vector listPayment = ulGrape.getListPayment();
   int countPayments = 0;
%>
  <tr>
   <td class="td04140102" colspan = "9">
    <table class="table00" cellspacing="0" cellpadding="1">
<%
  String defaultOrganic = "";
  String defaultVariety = "";
  String defaultPrice   = HTMLHelpersMasking.maskBigDecimal(ulGrape.getBrixPrice(), 4);
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
      <td class="td05200424" style="text-align:center" colspan="7"><b>Payment Sequence:&nbsp;&nbsp;<%= rfPayment.getPaymentSequence() %></b></td>
     </tr>
     <tr class = "tr02">
      <td class="td04140424" style="text-align:center"><b>Conventional<br>/Organic</b></td>
      <td class="td04140424" style="text-align:center"><b>Variety</b></td>
      <td class="td04140424" style="text-align:center"><b>Brix</b></td>
      <td class="td04140424" style="text-align:center"><b>Bins</b></td>
      <td class="td04140424" style="text-align:center"><b>Payment<br>Weight</b></td>
      <td class="td04140424" style="text-align:center"><b>Keyed Price<br>Per Ton</b></td>     
      <td class="td04140424" style="text-align:right"><b>Code</b></td>      
     </tr>
     <tr>
<%
        defaultOrganic = rfPayment.getOrganic().trim();
        if (defaultOrganic.equals(""))
        {
          if (rflGrape.getLotInformation().getOrganicConventional().equals("OR"))
            defaultOrganic = "Organic";
          else
            defaultOrganic = "Conventional";  
        }
%>
      <td class="td04140502" style="text-align:center"><%= ulGrape.buildDropDownConvOrganic((xPayment + "organic"), defaultOrganic, "Y") %></td>
<%
        defaultVariety = rfPayment.getVariety().trim();
        if (defaultVariety.equals(""))
        {
          if (ulGrape.getVariety().equals("CONCORD"))
            defaultVariety = "Concord";
        }
%>     
      <td class="td04140502" style="text-align:center"><%= ulGrape.buildDropDownVariety((xPayment + "variety"), defaultVariety, "Y") %></td>
      <td class="td04160502" style="text-align:center"><%= ulGrape.getBrix() %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((xPayment + "numberAt25PerBin"), rfPayment.getNumberOfBins25Box(), "", 3,3,"N", readOnlyLotGrape) %>&nbsp;<%= rfPayment.getNumberOfBins25BoxError() %></td>
      <td class="td03140102" style="text-align:center"><acronym title="Click this box(and SAVE) if you want to manually Enter a Weight">
        <%= HTMLHelpersInput.inputCheckBox((xPayment + "paymentWeightManuallyEntered"), rfPayment.getPaymentWeightManuallyEntered(), readOnlyLotGrape) %></acronym>&nbsp;
<%
        String readOnlyForThisRecord = readOnlyLotGrape;
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
      <td class="td03140502" style="text-align:center">
<%
   // What to default in when the PRICE is 0
    String displayPrice = HTMLHelpersMasking.maskBigDecimal(rfPayment.getPrice(), 4);
   try
   {
     if (rfPayment.getPriceError().equals(""))
     {
       // test to see if it is 0
       if ((new BigDecimal(displayPrice)).compareTo(new BigDecimal("0")) == 0)
       {
       		displayPrice = defaultPrice;
       }
     }
   }
   catch(Exception e)
   {
     // Catch error
   }
%>      
      <%= HTMLHelpersInput.inputBoxNumber((xPayment + "price"), displayPrice, "", 10,10,"N", readOnlyLotGrape) %>&nbsp;<%= rfPayment.getPriceError() %></td>   
      <td class="td04160502" style="text-align:right">
<%
        if (defaultOrganic.equals("Organic"))
          out.println("3480");
        else
          out.println("3474");
%>
       &nbsp;</td>
     </tr>  
<%
     //--------------------------------------------------------------------------------
     // Special Charges -- at Least 2 Lines
%>    
     <tr> 
      <td class="td0514010201" style="text-align:center"><b>Special<br>Charges</b></td> 
      <td class="td04140102" colspan = "7">
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
         <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((xPayment + "supplier" + chg), updCharge.getSupplier(), "", 6,6,"N", readOnlyLotGrape) %>&nbsp;<%= updCharge.getSpecialChargesInfo().getSupplierSpecialCharges().getSupplierName() %></td>     
<%
              } else {
%>
         <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((xPayment + "supplier" + chg), updCharge.getSupplier(), "", 6,6,"N", readOnlyLotGrape) %>&nbsp;<%= updCharge.getSupplierError() %></td>
<%
              }
%>        
         <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((xPayment + "ratePerPound" + chg), updCharge.getRatePerPound(), "", 6,6,"N", readOnlyLotGrape) %>&nbsp;<%= updCharge.getRatePerPoundError() %></td>
         <td class="td04140102" style="text-align:center"><%= ulGrape.buildDropDownChargeCode((xPayment + "accountingOption" + chg), updCharge.getAccountingOption(), readOnlyLotGrape) %></td>
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
         <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((xPayment + "supplier" + xChg), rflGrape.getLotSupplier().getSupplierNumber(), "", 6,6,"N", readOnlyLotGrape) %></td>
         <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((xPayment + "ratePerPound" + xChg), "", "", 6,6,"N", readOnlyLotGrape) %></td>
         <td class="td04140102" style="text-align:center"><%= ulGrape.buildDropDownChargeCode((xPayment + "accountingOption" + xChg), "", readOnlyLotGrape) %></td>
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
      <td class="td05200424" style="text-align:center" colspan="7"><b>New Payment Sequence:&nbsp;&nbsp;</b></td>
     </tr>
     <tr class = "tr02">
      <td class="td04140424" style="text-align:center"><b>Conventional<br>/Organic</b></td>
      <td class="td04140424" style="text-align:center"><b>Variety</b></td>
      <td class="td04140424" style="text-align:center"><b>Brix</b></td>
      <td class="td04140424" style="text-align:center"><b>Bins</b></td>
      <td class="td04140424" style="text-align:center"><b>Payment<br>Weight</b></td>
      <td class="td04140424" style="text-align:center"><b>Keyed Price<br>Per Ton</b></td>     
      <td class="td04140424" style="text-align:right"><b>Code</b></td>      
     </tr>
     <tr>
      <td class="td04140502" style="text-align:center"><%= ulGrape.buildDropDownConvOrganic((x + "organic"), defaultOrganic, "Y") %></td>
      <td class="td04140502" style="text-align:center"><%= ulGrape.buildDropDownVariety((x + "variety"), defaultVariety, "Y") %></td>
      <td class="td04160502" style="text-align:center"><%= ulGrape.getBrix() %></td>
      <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((x + "numberAt25PerBin"), "", "", 3,3,"N", readOnlyLotGrape) %>&nbsp;</td>
      <td class="td03140102" style="text-align:center"><acronym title="Click this box(and SAVE) if you want to manually Enter a Weight">
       <%= HTMLHelpersInput.inputCheckBox((x + "paymentWeightManuallyEntered"), "", readOnlyLotGrape) %></acronym>&nbsp;
       <acronym title="Weight is Calculated: Bins is calculated into Boxes and then the number of boxes is multiplied by the Average Weight per Box">
         <%= HTMLHelpersInput.inputBoxNumber((x + "paymentWeight"), "", "Payment Weight for this Payment", 8,8,"Y", readOnlyLotGrape) %>&nbsp;<b></b>
       </acronym>
      </td>     
      <td class="td03140502" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((x + "price"), defaultPrice, "", 10,10,"N", readOnlyLotGrape) %>&nbsp;</td>   
      <td class="td04160502" style="text-align:right">&nbsp;</td>
     </tr> 
<%
    // Special Charges -- SHOW 2 Lines -- BLANK
%>    
     <tr> 
      <td class="td0514010201" style="text-align:center"><b>Special<br>Charges</b></td> 
      <td class="td04140102" colspan = "7">
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
         <td class="td03140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxText((x + "supplier" + y), rflGrape.getLotSupplier().getSupplierNumber(), "", 6,6,"N", readOnlyLotGrape) %></td>
         <td class="td04140102" style="text-align:center"><%= HTMLHelpersInput.inputBoxNumber((x + "ratePerPound" + y), "", "", 6,6,"N", readOnlyLotGrape) %></td>
         <td class="td04140102" style="text-align:center"><%= ulGrape.buildDropDownChargeCode((x + "chargeCode" + y), "", readOnlyLotGrape) %></td>
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
      <td class="td0414" colspan="3"style="text-align:left"><%= HTMLHelpers.buttonSubmit("saveButton", "Save / Lot Data") %>&nbsp;</td>
      <td class="td0414" colspan="4" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save / Lot Data") %></td>
      <td class="td0414">&nbsp;</td>
     </tr>	    
      <%= HTMLHelpersInput.inputBoxHidden("countPayments", (countPayments + "")) %>
    </table>   
   </td>
  </tr>	
 </table>
 </body>
</html>