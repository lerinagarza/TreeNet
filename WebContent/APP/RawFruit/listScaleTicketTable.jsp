<%@ page import = "com.treetop.utilities.html.*" %>
<%@ page import = "com.treetop.utilities.UtilityDateTime" %>
<%@ page import = "com.treetop.app.specification.*" %>
<%@ page import = "com.treetop.businessobjects.RawFruitLoad" %>
<%@ page import = "com.treetop.businessobjects.DateTime" %>
<%@ page import = "com.treetop.businessobjectapplications.BeanRawFruit" %>
<%@ page import = "com.treetop.app.rawfruit.*" %>
<%@ page import = "java.util.Vector" %>
<% 

//---------------  listScaleTicketTable.jsp  ------------------------------------------//
//
//    Author :  Teri Walton  2/20/09
//   CHANGES:
//      Date       Name        Comments
//    --------    ------      --------
//-----------------------------------------------------------------------//
//********************************************************************
 // Bring in the Inquiry View Bean.
 // For this page the view bean could include.
 //      defaulted values for fields
 //		 drop down lists
 InqRawFruit irTable = new InqRawFruit();
 Vector getData = new Vector();
 try
 {
	irTable = (InqRawFruit) request.getAttribute("inqViewBean");
	BeanRawFruit beanData = (BeanRawFruit) irTable.getListReport().elementAt(0);
	getData = beanData.getListLoads();
 }
 catch(Exception e)
 {
    // should not have a problem, if it gets here it should have 
    //   been caught in the listExample
 }    
//***********************************************************
// Set the heading Information for sorting
//***********************************************************
 String displayViewStandard = request.getParameter("displayView");
 if (displayViewStandard == null)
   displayViewStandard = "";

   String columnHeadingTo = "/web/CtlRawFruit?requestType=listScaleTicket&" +
                            irTable.buildParameterResend();
   String[] sortImage = new String[13];
   String[] sortStyle = new String[13];
   String[] sortOrder = new String[13];
   sortOrder[0] = "scaleTicketNumber";
   sortOrder[1] = "scaleTicketCorrectionSequenceNumber";
   sortOrder[2] = "receivingDate";
   sortOrder[3] = "loadNetWeight";
   sortOrder[4] = "loadFruitNetWeight";
   sortOrder[5] = "loadFullBins";
   sortOrder[6] = "facility";
   sortOrder[7] = "carrierBOL";
   sortOrder[8] = "carrier";
   sortOrder[9] = "fromLocation";
   sortOrder[10] = "whseTicket";  
   sortOrder[11] = "warehouse";
  //************
  //Set Defaults
   for (int x = 0; x < 12; x++)
   {
      sortImage[x] = "";
      sortStyle[x] = "";
   }
  //************
   String orderBy = irTable.getOrderBy();
   if (orderBy.trim().equals(""))
      orderBy = "scaleTicketNumber";
   for (int x = 0; x < 12; x++)
   {
     if (orderBy.trim().equals(sortOrder[x].trim()))
     {
        if (irTable.getOrderStyle().trim().equals(""))
        {
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/TreeNetImages/arrowUpDark.gif\">";
           sortStyle[x] = "DESC";
        }
        else
           sortImage[x] = "<img src=\"https://image.treetop.com/webapp/TreeNetImages/arrowDownDark.gif\">";
     }
   }   
   int imageCount = 4;
   int expandCount = 0;
%>

<html>
  <head>
     <%= JavascriptInfo.getMoreButton() %>
     <%= JavascriptInfo.getExpandingSectionHead("", 0, "Y", 50) %>   
  </head>
 <body>
  <table class="table00" cellspacing="0" style="width:100%" align="center">
<%
  //----------------------------------------------------------------------------
  // HEADING SECTION
%>  
   <tr class="tr02">
    <td class="td04100605" style="text-align:center">
     <%= sortImage[0] %>
     <a class="a0410" title="Ticket associated to the Load which came accross the scale" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[0] %>&orderStyle=<%= sortStyle[0] %>">
      <b>Tree Top<br>Scale<br>Ticket</b>
     </a>      
    </td>
    <td class="td04100605" style="text-align:center">
     <%= sortImage[2] %>
     <a class="a0410" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[2] %>&orderStyle=<%= sortStyle[2] %>">
      <b>Receiving<br>Date</b>
     </a>      
    </td>      
    <td class="td04100605" style="text-align:center">
     <%= sortImage[8] %>
     <a class="a0412" title="Supplier at the LOAD Level" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[8] %>&orderStyle=<%= sortStyle[8] %>">
      <b><br>Carrier</b>
     </a>      
    </td>      
    <td class="td04100605" style="text-align:center">
     <%= sortImage[10] %>
     <a class="a0410" title="Warehouse Document Number, which comes in on the Load" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[10] %>&orderStyle=<%= sortStyle[10] %>">
      <b>Warehouse<br>Document<br>Number</b>
     </a>      
    </td>   
    <td class="td04100605" style="text-align:center">
     <%= sortImage[7] %>
     <a class="a0410" title="Bill of Lading brought in by the Carrier" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[7] %>&orderStyle=<%= sortStyle[7] %>">
      <b>Carrier<br>BOL</b>
     </a>      
    </td>  
    <td class="td04100605" style="text-align:center">
     <%= sortImage[6] %>
     <a class="a0410" title="Facility LOAD was received at" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[6] %>&orderStyle=<%= sortStyle[6] %>">
      <b>Receiving<br>Facility</b>
     </a>      
    </td>  
    <td class="td04100605" style="text-align:center">
     <%= sortImage[11] %>
     <a class="a0410" title="Warehouse LOAD was received at" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[11] %>&orderStyle=<%= sortStyle[11] %>">
      <b>Receiving<br>Warehouse</b>
     </a>      
    </td>  
    <td class="td04100605" style="text-align:center">
     <%= sortImage[9] %>
     <a class="a0410" title="Location the Load Came From" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[9] %>&orderStyle=<%= sortStyle[9] %>">
      <b>From<br>Location</b>
     </a>      
    </td>   
    <td class="td04100605" style="text-align:center">
     <%= sortImage[5] %>
     <a class="a0410" title="" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[5] %>&orderStyle=<%= sortStyle[5] %>">
      <b>Full Bin<br>Count /<br>Bulk</b>
     </a>      
    </td>   
    <td class="td04100605" style="text-align:center">
     <%= sortImage[3] %>
     <a class="a0410" title="Gross Weight minus the Truck Weight = Freight Weight" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[3] %>&orderStyle=<%= sortStyle[3] %>">
      <b>Freight<br>Weight</b>
     </a>      
    </td>   
    <td class="td04100605" style="text-align:center">
     <%= sortImage[4] %>
     <a class="a0410" title="Net Weight of the Fruit" href="<%= columnHeadingTo %>&orderBy=<%= sortOrder[4] %>&orderStyle=<%= sortStyle[4] %>">
      <b>Fruit<br>Weight</b>
     </a>      
    </td>     
    <td class="td04100605" style="width:6%">&nbsp;</td> 
   </tr> 
 <%
   //------------------------------------------------------------------
   // Detail Section of the Table
   //------------------------------------------------------------------
  // DATA SECTION
  int dataCount = 0;
  if (getData.size() > 0)
  { // IF there are LOAD Records
    for (int x = 0; x < getData.size(); x++)
    {
      dataCount++;
      RawFruitLoad thisrow = (RawFruitLoad) getData.elementAt(x);
%>  
   <tr class="tr00">
    <td class="td04121524" style="text-align:center">&nbsp;<%= thisrow.getScaleTicketNumber() %>&nbsp;
<%
   if (!thisrow.getScaleTicketCorrectionSequenceNumber().trim().equals("0"))
     out.println("-" + thisrow.getScaleTicketCorrectionSequenceNumber().trim());
%>      
    </td>
    <td class="td04101524" style="text-align:center">&nbsp;
<%
  String recDate = thisrow.getReceivingDate();
  try
  {
     DateTime dt = UtilityDateTime.getDateFromyyyyMMdd(recDate);
     recDate = dt.getDateFormatMMddyySlash();
  }
  catch(Exception e)
  {}
%>    
    <%= recDate %>
    </td> 
    <td class="td04101524" style="text-align:right"><acronym title="Supplier: <%= thisrow.getCarrier().getSupplierName() %>">&nbsp;<%= thisrow.getCarrier().getSupplierNumber() %></acronym></td>   
    <td class="td04101524" style="text-align:right">&nbsp;<%= thisrow.getWhseTicket() %></td> 
    <td class="td04101524" style="text-align:right">&nbsp;<%= thisrow.getCarrierBOL() %></td>  
    <td class="td04101524" style="text-align:right"><acronym title="Facility: <%= thisrow.getWarehouseFacility().getFacilityDescription() %>">&nbsp;<%= thisrow.getWarehouseFacility().getFacility() %></acronym></td>   
    <td class="td04101524" style="text-align:right"><acronym title="Warehouse: <%= thisrow.getWarehouseFacility().getWarehouseDescription() %>">&nbsp;<%= thisrow.getWarehouseFacility().getWarehouse() %></acronym></td>   
    <td class="td04101524">&nbsp;
<%
   if (thisrow.getFromLocationLong().trim().equals(""))
     out.println(thisrow.getFromLocation());
   else
     out.println(thisrow.getFromLocationLong());
%>
   </td> 
    <td class="td04101524" style="text-align:center">&nbsp;
<%
   if (thisrow.getFlagBulkBin().trim().equals(""))
      out.println(thisrow.getLoadFullBins());
   else
      out.println("bulk");
%>    
    </td> 
    <td class="td04101524" style="text-align:right"><%= HTMLHelpersMasking.maskBigDecimal(thisrow.getLoadNetWeight(), 0) %></td> 
    <td class="td04101524" style="text-align:right"><%= HTMLHelpersMasking.maskBigDecimal(thisrow.getLoadFruitNetWeight(), 0) %></td> 
    <td class="td04101524" style="text-align:right">
     <%= InqRawFruit.buildMoreButton(irTable.getRequestType(), thisrow.getScaleTicketNumber(), thisrow.getScaleTicketCorrectionSequenceNumber(), recDate, irTable.buildParameterResend(), irTable.getAllowUpdate()) %>
    <td>
   </tr>   
<%  
               
       // Test to see if there are Corrections for this Scale Ticket
       int corrections = 0;
       if (getData.size() > (x + 1))
       {
         for (int y = (x + 1); y < getData.size(); y++)
         {
           RawFruitLoad thisrowCorrection = (RawFruitLoad) getData.elementAt(y);
           if (thisrow.getScaleTicketNumber().equals(thisrowCorrection.getScaleTicketNumber()))
           {
          
             if (corrections == 0)
             { // Need to create the EXPAND Part
                imageCount++;
                expandCount++;
%>  
  <tr class="tr01">
    <td colspan="12">     
  <%= JavascriptInfo.getExpandingSection("C", "Corrections", 10, expandCount, imageCount, 1, 0) %>
      <table class="table00" cellspacing="0" style="width:95%" align="right" border="1">
       <tr class="tr02">
        <td class="td0410" style="text-align:center"><b>Scale<br>Ticket</b></td>
        <td class="td0410" style="text-align:center"><b>Receiving<br>Date</b></td>      
        <td class="td0410" style="text-align:center"><b><br>Carrier</b></td>      
        <td class="td0410" style="text-align:center"><b><acronym title="Warehouse Document Number">Warehouse<br>Document</acronym></b></td>   
        <td class="td0410" style="text-align:center"><b>Carrier<br>BOL</b></td>  
        <td class="td0410" style="text-align:center"><b><acronym title="Receiving Facility"><br>Fac</acronym></b></td>  
        <td class="td0410" style="text-align:center"><b><acronym title="Receiving Warehouse"><br>Whse</acronym></b></td>  
        <td class="td0410" style="text-align:center"><b>From<br>Location</b></td>   
        <td class="td0410" style="text-align:center"><b><acronym title="Full Bin Count or Bulk Load?">Bins/<br>Bulk</acronym></b></td>   
        <td class="td0410" style="text-align:center"><b>Freight<br>Weight</b></td>   
        <td class="td0410" style="text-align:center"><b>Fruit<br>Weight</b></td>     
        <td class="td0410" style="width:6%">&nbsp;</td> 
       </tr>    
<%      
             }
             corrections++;
%>
   <tr class="tr00">
    <td class="td0410" style="text-align:right">&nbsp;<%= thisrowCorrection.getScaleTicketNumber() %>&nbsp;-<%=thisrowCorrection.getScaleTicketCorrectionSequenceNumber()%>
    </td>
    <td class="td0410" style="text-align:center">&nbsp;
<%
  String recDateCorrection = thisrowCorrection.getReceivingDate();
  try
  {
     DateTime dt = UtilityDateTime.getDateFromyyyyMMdd(recDateCorrection);
     recDateCorrection = dt.getDateFormatMMddyySlash();
  }
  catch(Exception e)
  {}
%>    
    <%= recDateCorrection %>
    </td> 
    <td class="td0410" style="text-align:right"><acronym title="Supplier: <%= thisrowCorrection.getCarrier().getSupplierName() %>">&nbsp;<%= thisrowCorrection.getCarrier().getSupplierNumber() %></acronym></td>   
    <td class="td0410" style="text-align:right">&nbsp;<%= thisrowCorrection.getWhseTicket() %></td> 
    <td class="td0410" style="text-align:right">&nbsp;<%= thisrowCorrection.getCarrierBOL() %></td>  
    <td class="td0410" style="text-align:right"><acronym title="Facility: <%= thisrowCorrection.getWarehouseFacility().getFacilityDescription() %>">&nbsp;<%= thisrowCorrection.getWarehouseFacility().getFacility() %></acronym></td>   
    <td class="td0410" style="text-align:right"><acronym title="Warehouse: <%= thisrowCorrection.getWarehouseFacility().getWarehouseDescription() %>">&nbsp;<%= thisrowCorrection.getWarehouseFacility().getWarehouse() %></acronym></td>   
    <td class="td0410">&nbsp;<%= thisrowCorrection.getFromLocationLong() %></td> 
    <td class="td0410" style="text-align:center">&nbsp;
<%
   if (thisrowCorrection.getFlagBulkBin().trim().equals(""))
      out.println(thisrowCorrection.getLoadFullBins());
   else
      out.println("bulk");
%>    
    </td> 
    <td class="td0410" style="text-align:right"><%= HTMLHelpersMasking.maskBigDecimal(thisrowCorrection.getLoadNetWeight(), 0) %></td> 
    <td class="td0410" style="text-align:right"><%= HTMLHelpersMasking.maskBigDecimal(thisrowCorrection.getLoadFruitNetWeight(), 0) %></td> 
    <td class="td0410" style="text-align:right">
     <%= InqRawFruit.buildMoreButton(irTable.getRequestType(), thisrowCorrection.getScaleTicketNumber(), thisrowCorrection.getScaleTicketCorrectionSequenceNumber(), recDate, irTable.buildParameterResend(), irTable.getAllowUpdate()) %>
    <td>
   </tr>   
<%            
         }
       }
       }
       if (corrections > 0)
       {
           x = x + corrections;
          // Close up the Expanding section
%>          
            </table>
               <%= HTMLHelpers.endSpan() %>
                </td>
                </tr>
<%
       }
       if (dataCount == 50)
          x = getData.size();
       
     } // end of the for loop
   } // end of the if no load records chosen
%>     
  </table>
 </body>
</html>