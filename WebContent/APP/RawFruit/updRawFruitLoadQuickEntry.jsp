<%@ page language="java" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "java.util.Vector" %>
<%@ page import = "com.treetop.utilities.html.HtmlSelect.DescriptionType" %>
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
  String updTitle = " Update a Raw Fruit Load - Quick Entry";
 // Bring in the Detail View Bean.
 // For this page the view bean could include.
 UpdRawFruitLoad ur = new UpdRawFruitLoad();
 UpdRawFruitPO updRFPO = new UpdRawFruitPO();
 UpdRawFruitLot updRFLot = new UpdRawFruitLot();
 Vector quickEntries = new Vector();
 Supplier carrier = new Supplier();
 RawFruitLoad rfl = new RawFruitLoad();
 
 try
 {
	ur = (UpdRawFruitLoad) request.getAttribute("updViewBean");
	quickEntries = ur.getListQuickEntries();
	carrier = ur.getUpdBean().getRfLoad().getCarrier();
	rfl = ur.getUpdBean().getRfLoad();
	
	updRFLot.buildDropDownVectors();
	
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
 <style>
 @media print {
    .print-only {
        display:block !important;
    }
    .no-print {
        display:none;
    }
    #details td {
        font-size:16px;
    }
    #details input, #details select {
        padding:.5em;
        width:100%;
    }

 }
 
 .print-only {
    display:none;
 }
 
 #details select {
    width:100%;
 }
 
 </style>
 <body>
   <jsp:include page="../../Include/heading.jsp"></jsp:include>
 <script type="text/javascript" src="/web/Include/js/libs/jquery-1.7.2.min.js"></script>
 <script src="/web/Include/js/script.min.js"></script>
 
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
<input type="hidden" name="quickEntry" value="Quick Entry">   
<table class="table00" cellspacing="0" cellpadding="2">
 <%
   if (!ur.getDisplayMessage().trim().equals("")) {
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
   
   <%= HTMLHelpersInput.inputBoxHidden("scheduledLoadNumber", ur.getScheduledLoadNumber()) %>
  
   <td class="td04160102">&nbsp;<b><%= ur.getScaleTicket().trim() %>
<%
   if (!ur.getScaleTicketCorrectionSequence().trim().equals("0"))
     out.println("-" + ur.getScaleTicketCorrectionSequence().trim());
%>   
   </b></td>
   <%= HTMLHelpersInput.inputBoxHidden("scaleTicketCorrectionSequence", ur.getScaleTicketCorrectionSequence()) %>
   <td class="td04140102">&nbsp;</td>
   
   <td class="td04140102" colspan="2" rowspan="2" style="text-align:right"><%= HTMLHelpers.buttonSubmit("saveButton", "Save Changes", "") %></td>
   
   <td style="width:2%">&nbsp;</td>
  </tr>
  
  
  
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Date Fruit is Received">Receiving Date/Time:</acronym></td>
   <td class="td03140102">&nbsp;<%= HTMLHelpersInput.inputBoxDate("receivingDate", ur.getReceivingDate(), "getReceivingDate", "N", "N") %></td>
   <td class="td03140102" style="text-align:left; width:9em;">
      <%= HTMLHelpersInput.inputBoxTime2Sections("receivingTime", "Receiving Time", ur.getReceivingTime()) %>&nbsp;<%= ur.getReceivingTimeError().trim() %>
   </td>
   <td class="td04140102">&nbsp;</td>
  </tr>	  


  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="Check ONLY if the load is a Bulk Load">Bulk Load:</acronym></td>
   <td class="td04140102"><%= HTMLHelpersInput.inputCheckBox("bulkLoad", ur.getBulkLoad(), readOnlyLoad) %>&nbsp;</td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">&nbsp;</td>
  </tr>	
  
  
  <tr>
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
   <td class="<%= nbClass1 %>"><acronym title="<%= ur.getLoadFullBinsTieError() %>"><b><%=ur.getLoadFullBins() %></b>&nbsp;<%= ur.getLoadFullBinsError() %></acronym></td>
   <td class="td04140102">&nbsp;</td>     
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">&nbsp;</td>
  </tr>	
  
  
  <!--  Facility -->
  <tr>
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
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102">&nbsp;</td>

  </tr>
  
<!--  Warehouse -->  	   
  <tr>
   <td class="td04140102">&nbsp;</td>
   <td class="td04140102"><acronym title="M3 - Warehouse -- To be used for the Freight PO">Warehouse:</acronym></td>
   <td class="td04140102" colspan="3"><b><%= ur.buildDropDownWarehouse("warehouse", ur.getWarehouse(), readOnlyLoad) %>
   <td class="td04140102">&nbsp;</td>
  </tr>	   

</table>

<div style="margin:1em;">


<table class="table00" cellspacing="0" cellpadding="0" id="details">

    <tr class="tr00">
        <td class="td04140102"><b>Lot #</b></td>
        <td class="td04140102"><b>Whse Override</b></td>
        <td class="td04140102" colspan="2"><b>Supplier</b></td>
        <td class="td04140102"><b>Item Number</b></td>
        <td class="td04140102"><b>Variety</b></td>
        <td class="td04140102"><b>Run</b></td>
        <td class="td04140102"><b># Bins</b></td>
    </tr>
    
<%  int i = 0;
    for (; i<10; i++) { 
    UpdRawFruitQuickEntry qe = null;
    try {
        qe = (UpdRawFruitQuickEntry) quickEntries.elementAt(i);
    } catch (Exception e) {}
    if (qe == null) {
        qe = new UpdRawFruitQuickEntry();
    }
    
    %>    
    <tr>
        <td class="td04140102" style="vertical-align:top; width:15%">
            <input type="text" name="<%=i%>lotNumber" style="width:100%" value="<%=qe.getLotNumber() %>">
        </td>
        
        <td class="td04140102" style="vertical-align:top; width:10%;">
            <%= ur.buildDropDownQEWarehouse(i + "warehouse", qe.getWarehouse(), "N") %>
        </td>
        
        <td class="td04140102" style="vertical-align:top; width:5%;">
            <input type="text" pattern="[0-9]*" name="<%=i %>supplier" style="width:4em" value="<%=qe.getSupplier() %>">
            <div style="color:#990000; font-size:.8em;"><%=qe.getSupplierError() %></div>
        </td>
        
        <td class="td04140102" id="<%=i %>supplierName" style="width:15%; font-size:.8em; vertical-align:top"></td>
        
        <td class="td04140102" style="vertical-align:top; width:20%">
            <%=UpdRawFruitLot.buildDropDownQEItem(i + "itemNumber", i + "itemNumber","",qe.getItemNumber(),"", false, DescriptionType.VALUE_DESCRIPTION) %>
            <div style="color:#990000; font-size:.8em;"><%=qe.getItemNumberError() %></div>
            <div style="display:none;">
            <%=UpdRawFruitLot.buildDropDownQEAttributeModel(i + "atmo", i + "atmo","","",null, false, DescriptionType.VALUE_ONLY) %>
            </div>
        </td>

        <td class="td04140102" style="vertical-align:top; width: 10%; min-width:120px;">
            <%=UpdRawFruitLot.buildDropDownQEVariety(i + "variety", i + "variety","",qe.getVariety(),"", false, DescriptionType.VALUE_ONLY) %>
        </td>
        
        <td class="td04140102" style="vertical-align:top; width: 10%; min-width:55px;">
            <%=UpdRawFruitLot.buildDropDownQERunType(i + "runType", i + "runType","",qe.getRunType(),"", false, DescriptionType.VALUE_ONLY) %>
        </td>

        <td class="td04140102" style="vertical-align:top; width: 10%;">
            <input type="text" pattern="[0-9]*" name="<%=i %>numberOfBins"  style="width:4em;" value="<%=qe.getNumberOfBins() %>">
            <div style="color:#990000; font-size:.8em;"><%=qe.getNumberOfBinsError() %></div>
        </td>

    </tr>
    
    <script>
    $('#<%=i%>atmo').chainedTo('#<%=i%>itemNumber');
    $('#<%=i%>variety').chainedTo('#<%=i%>atmo');
    $('#<%=i%>runType').chainedTo('#<%=i%>atmo');
    </script>
    
<%  } %>    

</table>
</div>

<input type="hidden" name="countQuickEntries" value="<%=i%>">
 

  </form>  		
  <%= JavascriptInfo.getCalendarFoot("updateLoad", "getReceivingDate", "receivingDate") %>
  <jsp:include page="../../Include/footer.jsp"></jsp:include>

<script type="text/javascript">
var supl;

function getSupplierName(number) {
    return supl[number];
}


$('input[name$=supplier]').change(function(){
  var number = $(this).val();
  var nameField = $(this).parents('tr').find('td[id$=supplierName]');
  
  if (number || number != '') {
      var name = getSupplierName(number);
      
      if (name) {
          $(nameField).html(name);
      } else {
          $(nameField).html('Invalid supplier');
      }
  } else {
       $(nameField).html('');
  }
});

$.getJSON('/web/CtlSupplier/listSupplier', function (data) {
    supl = data;
    
     $('input[name$=supplier]').each(function(i, el){
	    $(el).change();
	 });
    
});


 
 

  
</script>
  
  
   </body>
</html>