<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "com.treetop.businessobjects.*" %>
<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "java.util.Vector" %>
<%
//---------------- dtlSchedFruitDetail.jsp -------------------------------------------//
//
//    Author :  Teri Walton  11/16/10
//   CHANGES:
//     Date       Name        Comments
//   --------    ------      --------
//--------------------------------------------------------------------------//
 Vector listLoadDetails = new Vector();
 int cntImageDetail = 0;
 String transferLoad = "";
 try
 {
    DtlScheduledFruit mainObject = (DtlScheduledFruit) request.getAttribute("dtlViewBean");
    listLoadDetails = mainObject.getBeanAvailFruit().getScheduledLoadDetail();
    cntImageDetail = new Integer((String) request.getAttribute("imageCount")).intValue();
    if (listLoadDetails.size() > 0)
       transferLoad = ((ScheduledLoadDetail) listLoadDetails.elementAt(0)).getTransferFlag();
  }
 catch(Exception e)
 {
   // System.out.println(JSPExceptionMessages.ViewBeanExceptSystem(errorPage, e.toString()));
   // request.setAttribute("msg", JSPExceptionMessages.ViewBeanExceptMsg(errorPage));
   System.out.println("Exception, " + e);
 }    
%>
<html>
 <head>
 </head>
 <body>
  <table class="table00">
   <tr class = "tr05">
    <td class="td00140102" style="width:4%">&nbsp;</td>
    <td class="td00140102" style="text-align:center"><acronym title="Type of Fruit"><b>Crop</b></acronym></td>
    <td class="td00140102" style="text-align:center"><acronym title="Variety of the Fruit"><b>Variety</b></acronym></td>
    <td class="td00140102" style="text-align:center"><acronym title="Grade - the grade of the fruit // how good the fruit quality is"><b>Grade</b></acronym></td>
    <td class="td00140102" style="text-align:center"><acronym title="Organic / Conventional / Baby Food"><b>Fruit Type</b></acronym></td>
    <td class="td00140102" style="text-align:center"><acronym title="Sticker Free Fruit"><b>Sticker<br>Free</b></acronym></td>
    <td class="td00140102" style="text-align:center"><acronym title="Orchard Run Fruit"><b>O/R</b></acronym></td>    
<%
   if (transferLoad.trim().equals("Y"))
   {
%>  
    <td class="td00140102" style="text-align:center" colspan="2"><acronym title="M3 Item Number"><b>Item</b></acronym></td>    
<%
   }else{
%>  
    <td class="td00140102" style="text-align:center"><acronym title="Cash Fruit - as Opposed to Pool Fruit"><b>Cash<br>Fruit</b></acronym></td>    
    <td class="td00140102" style="text-align:center"><acronym title="Cash Price - Entered at Price per Ton"><b>Cash<br>Price</b></acronym></td> 
<%
  } 
%>    
    <td class="td00140102" style="text-align:center" colspan="2"><acronym title="Number of Bins to Schedule for this load"><b>Schedule<br>Bins</b></acronym></td>   
   </tr>
<%
   // for loop on the details
   String saveWhseLoc = "";
   String saveLocDock = "";
   int binCount = 0;
   if (listLoadDetails.size() > 0)
   {
      for (int x = 0; x < listLoadDetails.size(); x++)
      {
        ScheduledLoadDetail thisRow = (ScheduledLoadDetail) listLoadDetails.elementAt(x);
        try{
           binCount = binCount + new Integer(thisRow.getBinCount()).intValue();
        }catch(Exception e)
        {}
        if (!saveWhseLoc.trim().equals(thisRow.getWhseNumber().trim() + thisRow.getWhseAddressNumber().trim()))
        { 
           saveWhseLoc = thisRow.getWhseNumber().trim() + thisRow.getWhseAddressNumber().trim();
%>
   <tr class = "tr02">   
<%
  // 5/11/11 - TW - Pick up date no longer used   
  //  <td class="td05140102" colspan="3">&nbsp;&nbsp;Pickup:&nbsp;<b> InqScheduledFruit.formatDate(thisRow.getScheduledPickupDate()) 
  //&nbsp;&nbsp; InqScheduledFruit.formatTime(thisRow.getScheduledPickupTime() + "00") </b></td>
%>    
    <td class="td05140102" colspan="3">
<%
   if (thisRow.getWhseNumber().trim().equals("ORCHARDRUN"))
   {
      out.println("Whse:&nbsp;<b>ORCHARD RUN</b>");
   }else{
%>  
    <acronym title = "M3 Supplier (Whse Number): <%= thisRow.getWhseNumber() %>">Whse:&nbsp;<b><%= thisRow.getWhseDescription() %></b></acronym>
<%
   }
%>    
    </td>
    <td class="td05140102" colspan="7"><acronym title = "Warehouse Location: <%= thisRow.getWhseAddressNumber() %>">Location:&nbsp;<b><%= thisRow.getWarehouse().getWarehouseDescription() %></b></acronym></td>
    
   </tr> 
<%
        }
        if (transferLoad.equals("Y") &&
            !saveLocDock.trim().equals(thisRow.getShippingLocationNo().trim() + thisRow.getShippingDockNo().trim()))
        { 
           saveLocDock = thisRow.getShippingLocationNo().trim() + thisRow.getShippingDockNo().trim();
%>
   <tr class = "tr02">   
    <td class="td05140102" colspan="3"><acronym title = "M3 Warehouse - product was shipped FROM">Sending Location:&nbsp;<b><%= thisRow.getShippingLocationDesc()%>&nbsp;</b>(<%= thisRow.getShippingLocationNo() %>)</acronym></td>
    <td class="td05140102" colspan="7">
<%
   if (!thisRow.getShippingDockNo().trim().equals(""))
   {
%>    
    <acronym title = "Dock Location">Dock:&nbsp;<b><%= thisRow.getShippingDockDesc()%>&nbsp;</b>(<%= thisRow.getShippingDockNo() %>)</acronym>
<%
   }
%>    
    &nbsp;</td>
   </tr> 
<%
        }
%>
   <tr class = "tr00">   
    <td class="td04140102">&nbsp;</td>
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getCropCodeDesc() %>&nbsp;</td>
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getVarietyDesc() %>&nbsp;</td>
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getGradeDesc() %>&nbsp;</td>
    <td class="td04140102" style="text-align:center">&nbsp;<%= thisRow.getOrganicDesc() %>&nbsp;</td>

    <td class="td04140102" style="text-align:center">
<%
   if (!thisRow.getStickerFreeFlag().trim().equals(""))
   {
     out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif\"/>"); 
     cntImageDetail++;
   }  
%>    
     &nbsp;
    </td>
    <td class="td04140102" style="text-align:center">
<%
   if (!thisRow.getOrchardRunDtlFlag().trim().equals(""))
   {
     out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif\"/>"); 
     cntImageDetail++;
   }
%>    
     &nbsp;
    </td>
<%
   if (transferLoad.trim().equals("Y"))
   {
%>  
    <td class="td04140102" style="text-align:center" colspan="2"><%= thisRow.getTreeTopItem() %>&nbsp;<%= thisRow.getTreeTopItemDesc() %></td>    
<%
   }else{
%>          
    <td class="td04140102" style="text-align:center">
<%
   if (!thisRow.getMemberOrCash().trim().equals(""))
   {
     out.println("<img src=\"https://image.treetop.com/webapp/TreeNetImages/checkmark_V3.gif\"/>"); 
     cntImageDetail++;
   }
%>    
     &nbsp;
    </td>
    <td class="td04140102" style="text-align:center"><%= HTMLHelpersMasking.mask2DecimalDollar(thisRow.getCashPrice()) %></td>  
<%
   }
%>     
    <td class="td04140102" style="text-align:right"><%= HTMLHelpersMasking.maskInteger(thisRow.getBinCount()) %></td> 
    <td class="td04140102" style="width:2%">&nbsp;</td>
   </tr>
<%
     } // end of the For Statement
     // Display a Total Line
%>
   <tr class = "tr02">   
    <td class="td04160405">&nbsp;</td>
    <td class="td04160405" colspan="8">&nbsp;<b>TOTAL BINS ON LOAD:&nbsp;</b></td>
    <td class="td04160405" style="text-align:right"><b><%= binCount %></b></td>
    <td class="td04160405">&nbsp;</td>
   </tr> 
<%    
   }
   request.setAttribute("imageCount", (cntImageDetail + ""));
%> 
 </table>

 </body>
</html>