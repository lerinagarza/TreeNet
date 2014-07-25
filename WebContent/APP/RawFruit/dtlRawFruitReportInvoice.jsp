<%@ page language="java" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.math.*" %>
<%

//---------------- dtlRawFruitReportInvoice.jsp -------------------------------------------//
//
//    Author :  Teri Walton  12/12/08
//   CHANGES:
//
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
//**************************************************************************//
  String errorPage = "/RawFruit/dtlRawFruitReportInvoice.jsp";
  String updTitle = " Report a Raw Fruit Load - Invoice";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 DtlRawFruitLoad dr = new DtlRawFruitLoad();
 Vector listPayments = new Vector();
 try
 {
	dr = (DtlRawFruitLoad) request.getAttribute("dtlViewBean");
	listPayments = dr.getListPayments();
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
%>
 <table class="table00" cellspacing="0" cellpadding="2">   
<%   
  String saveLot = "";
  Vector lotComments = new Vector();
  if (listPayments.size() > 0)
  {
    for (int a = 0; a < listPayments.size(); a++)
    {
      DtlRawFruitPayment rfp = (DtlRawFruitPayment) listPayments.elementAt(a);
      String lotPageBreak = "";
      if (saveLot.trim().equals("") ||
          !saveLot.trim().equals(rfp.getLotNumber().trim()))
      {
         try {
		 if (!saveLot.equals("") ||
		     a == 0)
		 {
		 	KeyValue kv = new KeyValue();
		 	kv.setEntryType("LotComment");
		 	if (a == 0)
			 	kv.setKey1(rfp.getLotNumber().trim());
		 	else
			 	kv.setKey1(saveLot.trim());
		 	lotComments = com.treetop.services.ServiceKeyValue.buildKeyValueList(kv);
		 }
		 } catch(Exception e)
		 {
//		 	 skip it. allowed to be empty
		 	lotComments = new Vector();
		 }	
         
 if (lotComments.size() > 0 && a != 0)
 { // Only display IF comments are found
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("listKeyValues", lotComments);  
%>  
    <tr>
     <td class="td04160605" colspan="4">
      <table class="table00" cellspacing="0" cellpadding="2">  
       <tr class="tr02">  
        <td class="td0516"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;LOT&nbsp;<%= saveLot %>&nbsp;Comments</b></td>
       </tr>  
       <tr class="tr02">
        <td>
        <jsp:include page="../Utilities/updKeyValuesNew.jsp"></jsp:include>
        </td>   
       </tr>
      </table>
     </td>
    </tr>   
    <tr><td style="height:5px"></td></tr>   
<%
   } 
   saveLot = rfp.getLotNumber();
         if (a != 0)
           lotPageBreak   = " style=\"page-break-before:always\" "; 
%>    
  <tr class = "tr02" <%= lotPageBreak %>>
   <td class="td04160605" style="text-align:center"><b>Date</b></td>
   <td class="td04160605" style="text-align:center"><b>Item Number</b></td>
   <td class="td04160605" style="text-align:center"><b>Description</b></td>   
   <td class="td04160605" style="text-align:center"><b>Lot Number</b></td>
  </tr> 
  <tr class = "tr01">
   <td class="td05160605" style="text-align:center"><b><%= rfp.getReceivingDate() %></b></td>
   <td class="td05160605" style="text-align:center"><b><%= rfp.getItemNumber() %></b></td>
   <td class="td05160605" style="text-align:center"><b><%= rfp.getItemDescription() %></b></td>
   <td class="td05160605" style="text-align:center"><b><%= rfp.getLotNumber() %></b></td>
  </tr>   
<%
     } // IF it should be a new page
%>  
  <tr class = "tr02">
   <td class="td04160605" colspan="4"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     Payment - <%= rfp.getPaymentSequence() %></b></td>
  </tr> 
  <tr class = "tr02">
   <td class="td04160605" colspan="4">
    <table class="table00" cellspacing="0" cellpadding="2">
     <tr>
      <td style="width:2%" rowspan="10">&nbsp;</td>
      <td colspan="2">
<%
   // Left hand side of the Payment Invoice Page
%>      
       <table class="table00" cellspacing="0" cellpadding="2">
        <tr class = "tr00">
         <td class="td04140102" style="text-align:right">Supplier Number:</td>
         <td class="td04140102" style="text-align:center"><b><%= rfp.getSupplier() %></b>&nbsp;</td>
         <td style="width:1%" rowspan="13">&nbsp;</td>
        </tr>
        <tr class = "tr00">
         <td class="td04140102" style="text-align:right">Supplier Name:</td>
         <td class="td04140102" style="text-align:center"><b><%= rfp.getSupplierDescription() %></b>&nbsp;</td>
        </tr>       
        <tr class = "tr00">
         <td class="td04140102" style="text-align:right">Supplier Invoice Number:</td>
         <td class="td04140102" style="text-align:center"><b><%= DtlRawFruitLoad.maskField("supplierInvoice", rfp.getSupplierInvoiceNumber().trim()) %>&nbsp;-&nbsp;<%= rfp.getPaymentSequence() %>
<%
   StringBuffer displayCorrSeq = new StringBuffer();;
   try
   {
      BigDecimal corrSeq = new BigDecimal(dr.getDtlBean().getRfLoad().getScaleTicketCorrectionSequenceNumber());
      if (corrSeq.compareTo(new BigDecimal("0")) != 0)
      {
         corrSeq = corrSeq.divide(new BigDecimal("10"));
         displayCorrSeq.append("&nbsp;-&nbsp;");
         if (corrSeq.toString().length() == 1)
            displayCorrSeq.append("0");
         displayCorrSeq.append(corrSeq.toString().trim());
         out.println(displayCorrSeq.toString()); 
     }
   }
   catch(Exception e)
   {
   // Catch if the sequence is not currently a number
   }
//   System.out.println("Stop Here");
//   System.out.println(dr.getDtlBean().getRfLoad().getScaleTicketCorrectionSequenceNumber());
%>         
         </b></td>
        </tr>         
        <tr class = "tr00">
         <td class="td04140102" style="text-align:right">Invoice Amount:</td>
         <td class="td04140102" style="text-align:center"><b><%= rfp.getTotalInvoiceAmount() %></b></td>        
        </tr>
        <tr class = "tr00">
         <td class="td04140102" style="text-align:right">Receiving Date / Invoice Date:</td>
         <td class="td04140102" style="text-align:center"><b><%= rfp.getReceivingDate() %></b></td>
        </tr>
        <tr class = "tr00">
         <td class="td04140102" style="text-align:right">Purchase Order Number:</td>
         <td class="td04140102" style="text-align:center"><b><%= DtlRawFruitLoad.maskField("po", rfp.getPurchaseOrder()) %></b></td>
        </tr>
        <tr class = "tr00">
         <td class="td04140102" style="text-align:right">Lot Number / DIM 5:</td>
         <td class="td04140102" style="text-align:center"><b><%= DtlRawFruitLoad.maskField("lot", rfp.getLotNumber()) %></b></td>
        </tr>
        <tr class = "tr00">
         <td class="td04140102" style="text-align:right">PO # / Ref #:</td>
         <td class="td04140102" style="text-align:center"><b><%= DtlRawFruitLoad.maskField("po", rfp.getPurchaseOrder()) %></b></td>
        </tr>
        <tr class = "tr00">
         <td class="td04140102" style="text-align:right">Payment Code / Field 1:</td>
         <td class="td04140102" style="text-align:center">
<%
   if (rfp.getPayCodeHandKeyed().equals(""))
      out.println("<b>" + rfp.getPaymentCode() + "</b>");
   else
   {
      out.println("<s>" + rfp.getPaymentCode() + "</s>&nbsp;&nbsp;");
      out.println("<b>" + rfp.getPayCodeHandKeyed() + "</b>");
   }   
%>         
         &nbsp;</td>
        </tr>       
        <tr class = "tr00">
         <td class="td04140102" style="text-align:right">Warehouse Ticket /<br> Field 2:</td>
         <td class="td04140102" style="text-align:center"><b><%= rfp.getWhseTicket() %>&nbsp;/&nbsp;<%= rfp.getCarrierBillOfLading() %></b></td>
        </tr>
        <tr class = "tr00">
         <td class="td04140102" style="text-align:right">Freight # / Field 3:</td>
         <td class="td04140102" style="text-align:center"><b><%= DtlRawFruitLoad.maskField("scaleTicket",rfp.getScaleTicketNumber()) %><%= displayCorrSeq.toString() %>
<%
  // if (!dr.getScaleTicketCorrectionSequence().trim().equals("0"))
  //   out.println("-" + dr.getScaleTicketCorrectionSequence().trim());
%>           
         </b></td>
        </tr>
        <tr class = "tr00">
         <td class="td04140102" style="text-align:right">Facility:</td>
         <td class="td04140102" style="text-align:center"><b><%= rfp.getFacility() %>&nbsp;</b></td>
        </tr>       
       </table>
      </td>
     <td colspan="2"> 
      <table class="table00" cellspacing="0" cellpadding="2">
<%
   if (rfp.getCrop().equals("GRAPE") &&
       rfp.getPaymentSequence().trim().equals("20"))
   {
 %>      
       <tr class = "tr00">
        <td class="td04160102" style="text-align:right" colspan="3"><b>Due Date:</b></td>
        <td class="td04160102" style="text-align:left" colspan="2">&nbsp;&nbsp;<b><%= rfp.getGrapeDueDate() %></b></td>
       </tr>            
<%
   }
%>       
       <tr class = "tr00">
        <td style="width:1%" rowspan="50">&nbsp;</td>
        <td class="td04140102" style="text-align:right">&nbsp;</td>
        <td class="td04140102" style="text-align:right">per<br>Ton</td>
        <td class="td04140102" style="text-align:right">per<br>Pound</td>
<%
   if (rfp.getCrop().equals("GRAPE"))
   {
%>
        <td class="td04180102" style="text-align:right"><b>BRIX:</b></td>
        <td class="td04180605" style="text-align:center"><b><%= rfp.getBrix() %>&nbsp;</b></td>
<%
   }
   else
   {
%>        
        <td class="td04140102" style="text-align:right" colspan="2">&nbsp;</td>
<%
   }
%>        
       </tr>  
       <tr class = "tr00">
        <td class="td04140102" style="text-align:right">Price:</td>
        <td class="td04140102" style="text-align:right">$<%= rfp.getFruitPricePerTon() %></td>
        <td class="td04140102" style="text-align:right"><%= rfp.getFruitPricePerPound() %></td>
        <td class="td04140102" style="text-align:right"><%= rfp.getPaymentWeight() %></td>
        <td class="td04140102" style="text-align:right"><%= rfp.getFruitPriceTotal() %></td>
       </tr>             
<%
      // Display the Withhold Information
       if (rfp.getListWithhold().size() > 0)
       {
         for (int withhold = 0; withhold < rfp.getListWithhold().size(); withhold++)
         {
         	DtlRawFruitSpecialCharges dsc = (DtlRawFruitSpecialCharges) rfp.getListWithhold().elementAt(withhold);
%>       
       <tr class = "tr00">
        <td class="td04140102" style="text-align:right">W/H:</td>
        <td class="td04140102" style="text-align:right">$<%= dsc.getPerTon() %></td>
        <td class="td04140102" style="text-align:right"><%= dsc.getPerPound() %></td>
        <td class="td04140102" style="text-align:right"><%= dsc.getWeight() %></td>
        <td class="td04140102" style="text-align:right"><%= dsc.getTotalCost() %></td>
       </tr>         
<%
         } // End of the Withhold For Loop
        } // END of the List Withhold is > 0
        else
        { // Display a row with 0 values
%>
       <tr class = "tr00">
        <td class="td04140102" style="text-align:right">W/H:</td>
        <td class="td04140102" style="text-align:right">$<%= HTMLHelpersMasking.maskBigDecimal("0", 0) %></td>
        <td class="td04140102" style="text-align:right"><%= HTMLHelpersMasking.maskBigDecimal("0", 3) %></td>
        <td class="td04140102" style="text-align:right"><%= rfp.getPaymentWeight() %></td>
        <td class="td04140102" style="text-align:right"><%= HTMLHelpersMasking.mask2DecimalDollar("0") %></td>
       </tr>         
<%
        }
%>
       <tr class = "tr00">
        <td class="td04140405" style="text-align:center" colspan="4"><b>Price Paid:</b></td>
        <td class="td04140405" style="text-align:right"><b><%= rfp.getTotalPrice() %></b></td>
       </tr>      
       <tr class = "tr00"><td style="height:5px"></td></tr>          
       <tr class = "tr00"><td class="td0414" colspan="5"><i><b>Commission:</b></i></td></tr>  
<%
      // Display the Commission Information
       if (rfp.getListCommission().size() > 0)
       {
         for (int commission = 0; commission < rfp.getListCommission().size(); commission++)
         {
         	DtlRawFruitSpecialCharges dsc = (DtlRawFruitSpecialCharges) rfp.getListCommission().elementAt(commission);
%>       
       <tr class = "tr00">
        <td class="td04140102" style="text-align:center"><acronym title = "<%= dsc.getSupplierName() %>"><%= dsc.getSupplier() %></acronym>&nbsp;&nbsp;&nbsp;&nbsp;<%= dsc.getAccountingCode() %></td>
        <td class="td04140102" style="text-align:right">$<%= dsc.getPerTon() %></td>
        <td class="td04140102" style="text-align:right"><%= dsc.getPerPound() %></td>
        <td class="td04140102" style="text-align:right"><%= dsc.getWeight() %></td>
        <td class="td04140102" style="text-align:right"><%= dsc.getTotalCost() %></td>
       </tr>         
<%
         } // End of the Withhold For Loop
        } // END of the List Withhold is > 0
        else
        { // Display a row with 0 values
%>
       <tr class = "tr00">
        <td class="td04140102" style="text-align:right">&nbsp;</td>
        <td class="td04140102" style="text-align:right">$<%= HTMLHelpersMasking.maskBigDecimal("0", 0) %></td>
        <td class="td04140102" style="text-align:right"><%= HTMLHelpersMasking.maskBigDecimal("0", 3) %></td>
        <td class="td04140102" style="text-align:right"><%= rfp.getPaymentWeight() %></td>
        <td class="td04140102" style="text-align:right"><%= HTMLHelpersMasking.mask2DecimalDollar("0") %></td>
       </tr>         
<%
        }
%>     
       <tr class = "tr00">
        <td class="td04140405" style="text-align:center" colspan="4"><b>Commission Paid:</b></td>
        <td class="td04140405" style="text-align:right"><b><%= rfp.getTotalCommission() %></b></td>
       </tr>      
       <tr class = "tr00"><td style="height:5px"></td></tr>          
       <tr class = "tr00"><td class="td0414" colspan="5"><i><b>Other Charges:</b></i></td></tr>  
<%
      // Display the Other Charges Information
       if (rfp.getListOtherCharges().size() > 0)
       {
         for (int other = 0; other < rfp.getListOtherCharges().size(); other++)
         {
         	DtlRawFruitSpecialCharges dsc = (DtlRawFruitSpecialCharges) rfp.getListOtherCharges().elementAt(other);
%>       
       <tr class = "tr00">
        <td class="td04140102" style="text-align:center"><acronym title = "<%= dsc.getSupplierName() %>"><%= dsc.getSupplier() %></acronym>&nbsp;&nbsp;&nbsp;&nbsp;<%= dsc.getAccountingCode() %></td>
        <td class="td04140102" style="text-align:right">$<%= dsc.getPerTon() %></td>
        <td class="td04140102" style="text-align:right"><%= dsc.getPerPound() %></td>
        <td class="td04140102" style="text-align:right"><%= dsc.getWeight() %></td>
        <td class="td04140102" style="text-align:right"><%= dsc.getTotalCost() %></td>
       </tr>         
<%
         } // End of the Withhold For Loop
        } // END of the List Withhold is > 0
        else
        { // Display a row with 0 values
%>
       <tr class = "tr00">
        <td class="td04140102" style="text-align:right">&nbsp;</td>
        <td class="td04140102" style="text-align:right">$<%= HTMLHelpersMasking.maskBigDecimal("0", 0) %></td>
        <td class="td04140102" style="text-align:right"><%= HTMLHelpersMasking.maskBigDecimal("0", 3) %></td>
        <td class="td04140102" style="text-align:right"><%= rfp.getPaymentWeight() %></td>
        <td class="td04140102" style="text-align:right"><%= HTMLHelpersMasking.mask2DecimalDollar("0") %></td>
       </tr>         
<%
        }
%>          
       <tr class = "tr00">
        <td class="td04140405" style="text-align:center" colspan="4"><b>Other Charges Paid:</b></td>
        <td class="td04140405" style="text-align:right"><b><%= rfp.getTotalOtherCharges() %></b></td>
       </tr>           
       </table>
      </td>
      <td style="width:2%" rowspan="10">&nbsp;</td>
     </tr>
     <tr><td style="height:5px"></td></tr>
     <tr class="td0420"><td colspan="4" style="text-align:center"><b><%= rfp.getCodeDescriptions() %></b>&nbsp;</td></tr>
     <tr><td style="height:5px"></td></tr>   
     <tr>
      <td class="td0416" style="width:20%; text-align:right"><b>Voucher#:&nbsp;&nbsp;&nbsp;</b></td>
      <td class="td04160605" style="width:25%; height:35px">&nbsp;</td>     
      <td class="td0416" style="text-align:right"><b>Total Invoice Amount:&nbsp;&nbsp;&nbsp;</b></td>
      <td class="td04180605" style="text-align:right; height:35px"><b><%= rfp.getTotalInvoiceAmount() %></b></td> 
     </tr>
     <tr><td style="height:5px"></td></tr>  
    </table>
   </td>
<%       
     }// End of the For Loop for the PO's.
  }// End of the IF PO Size
     try {
	  if (!saveLot.equals(""))
	 {
	 	KeyValue kv = new KeyValue();
	 	kv.setEntryType("LotComment");
	 	kv.setKey1(saveLot.trim());
	 	lotComments = com.treetop.services.ServiceKeyValue.buildKeyValueList(kv);
	 }
	 } catch(Exception e)
	 {
//		 	 skip it. allowed to be empty
	 	lotComments = new Vector();
	 }	
 if (lotComments.size() > 0)
 { // Only display IF comments are found
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("listKeyValues", lotComments);  
%>  
    <tr>
     <td class="td04160605" colspan="6">
      <table class="table00" cellspacing="0" cellpadding="2">  
       <tr class="tr02">  
        <td class="td0516"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;LOT&nbsp;<%= saveLot %>&nbsp;Comments</b></td>
       </tr>  
       <tr class="tr02">
        <td><%@ include file="../Utilities/updKeyValuesNew.jsp" %></td>   
       </tr>
      </table>
     </td>
    </tr>   	
    <tr><td style="height:7px"></td></tr>        
<%
   } 
 if (dr.getDtlBean().getComments().size() > 0)
 { // Only display IF comments are found
  request.setAttribute("screenType", "detail");
  request.setAttribute("longFieldType", "comment");
  request.setAttribute("listKeyValues", dr.getDtlBean().getComments());  
%> 
    <tr>
     <td class="td04160605" colspan="6">
      <table class="table00" cellspacing="0" cellpadding="2">  
       <tr class="tr02">  
        <td class="td0516"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;LOAD&nbsp;<%= dr.getScaleTicket() %>&nbsp;Comments</b></td>
       </tr>  
       <tr class="tr02">
        <td><%@ include file="../Utilities/updKeyValuesNew.jsp" %></td>   
       </tr>
      </table>
     </td>
    </tr>   	
<%
   } 
%>    
 </table>   
 <jsp:include page="../../Include/footer.jsp"></jsp:include> 
   </body>
</html>